package com.js.oa.hr.kq.szgt.action;

import com.js.oa.hr.kq.szgt.bean.KqDataImportBean;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.sql.Page;
import com.js.util.util.ParameterFilter;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class KqDataImportAcion extends Action {
  private Workbook workbook = null;
  
  private Sheet sheet = null;
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    UploadKqDataTemplateForm uploadForm = (UploadKqDataTemplateForm)actionForm;
    HttpSession session = request.getSession();
    String realPath = "", message = "", succeed = "0";
    String action = request.getParameter("action");
    String checkbox = request.getParameter("checkbox");
    String searchBeginDate = request.getParameter("searchBeginDate");
    String searchEndDate = request.getParameter("searchEndDate");
    String empName = request.getParameter("empName");
    String searchOrgName = request.getParameter("searchOrgName");
    String searchOrgId = request.getParameter("searchOrgId");
    String kqStatus = request.getParameter("kqStatus");
    String orderBy = request.getParameter("orderBy");
    String sortType = request.getParameter("sortType");
    if ("null".equals(sortType) || "".equals(sortType) || sortType == null)
      sortType = "desc"; 
    String savetype = request.getParameter("savetype");
    int row = 0;
    String newInputNum = "";
    String oldInputNum = "";
    String badInputNum = "";
    if ("kqDataImport".equals(action)) {
      if (!ParameterFilter.checkParameter(savetype))
        try {
          response.sendRedirect(String.valueOf(request.getContextPath()) + "/public/jsp/inputerror.jsp");
        } catch (IOException e) {
          e.printStackTrace();
        }  
      realPath = request.getRealPath("/uploadtemplate/kqdatatemplate.xls");
      try {
        uploadFile(uploadForm.getFile(), realPath);
        FileInputStream file = new FileInputStream(new File(realPath));
        this.workbook = Workbook.getWorkbook(file);
      } catch (Exception e) {
        message = String.valueOf(message) + "选择的模版不正确！<br>";
        if (!"1".equals(succeed))
          succeed = "1"; 
        request.setAttribute("message", message);
        request.setAttribute("succeed", succeed);
        return actionMapping.findForward("input");
      } 
      this.sheet = this.workbook.getSheet(0);
      if (this.sheet != null)
        row = this.sheet.getRows(); 
      KqDataImportBean kqimport = new KqDataImportBean();
      List<String[]> kqlist = getSheetData(this.workbook, request);
      List<E> record = kqimport.KqDataInsert(kqlist, savetype, row);
      newInputNum = record.get(0).toString();
      oldInputNum = record.get(1).toString();
      badInputNum = record.get(2).toString();
      if (!"0".equals(newInputNum))
        request.setAttribute("newInputNum", "有" + newInputNum + "条考勤记录新增<br/>"); 
      if (!"0".equals(oldInputNum) && "1".equals(savetype))
        request.setAttribute("oldInputNum", "有" + oldInputNum + "条考勤记录忽略<br/>"); 
      if (!"0".equals(oldInputNum) && "2".equals(savetype))
        request.setAttribute("oldInputNum", "有" + oldInputNum + "条考勤记录覆盖<br/>"); 
      if (!"0".equals(badInputNum))
        request.setAttribute("badInputNum", "有" + badInputNum + "条无效考勤记录<br/>"); 
    } 
    if ("kqdatalist".equals(action)) {
      String para = "", from = "", where = "";
      para = " a.rec_jobid,a.empName,b.orgname,a.rec_date,a.rec_onduty,a.rec_offduty,a.sign_onduty,a.sign_offduty,a.late_time,a.early_time,a.emp_id";
      from = " skq_record a left join org_organization b on a.org_id=b.org_id";
      String corpId = session.getAttribute("corpId").toString();
      String userId = session.getAttribute("userId").toString();
      String orgId = session.getAttribute("orgId").toString();
      ManagerService service = new ManagerService();
      String whereSQL = service.getRightFinalWhere(userId, orgId, 
          "07*55*55", "b.org_id", "a.emp_id");
      where = " where b.orgidstring like '%$" + corpId + "$%' and ((" + whereSQL + ") or a.emp_id=" + userId + ")";
      if (empName != null && !"".equals(empName) && !"null".equals(empName))
        where = String.valueOf(where) + " and a.empName like '%" + empName + "%'"; 
      if (searchOrgId != null && !"".equals(searchOrgId) && !"null".equals(searchOrgId))
        where = String.valueOf(where) + " and b.orgidstring like '%$" + searchOrgId + "$%'"; 
      where = String.valueOf(where) + " and ((a.sign_onduty is null or a.sign_onduty='' or a.sign_offduty is null or a.sign_offduty='') or (a.late_time is not null) or (a.early_time is not null))";
      if (checkbox != null && !"".equals(checkbox) && !"null".equals(checkbox))
        where = String.valueOf(where) + " and to_date(a.rec_date,'yyyy-mm-dd') >=to_date('" + searchBeginDate + "','yyyy-mm-dd') and to_date(a.rec_date,'yyyy-mm-dd')<=to_date('" + searchEndDate + "','yyyy-mm-dd')"; 
      where = String.valueOf(where) + " and not exists (select kqh.id from kq_holiday kqh where to_date(a.rec_date,'YYYY-MM-DD') between kqh.begin_date and kqh.end_date and type=0 and corp_id=" + corpId + ") ";
      where = String.valueOf(where) + " and not exists (select list_id from skq_whitelist where listuserid like '%$'||a.emp_id||'%$')";
      if ("null".equals(orderBy) || "".equals(orderBy) || orderBy == null)
        where = String.valueOf(where) + " order by a.rec_date desc"; 
      if ("date".equals(orderBy))
        where = String.valueOf(where) + " order by a.rec_date " + sortType; 
      where = String.valueOf(where) + ",a.rec_id";
      List list = list(request, para, from, where);
      request.setAttribute("empName", empName);
      request.setAttribute("searchOrgName", searchOrgName);
      request.setAttribute("searchOrgId", searchOrgId);
      request.setAttribute("checkbox", checkbox);
      request.setAttribute("searchBeginDate", searchBeginDate);
      request.setAttribute("searchEndDate", searchEndDate);
      request.setAttribute("kqdatalist", list);
    } 
    return actionMapping.findForward(action);
  }
  
  public static void uploadFile(FormFile file, String dir) throws IOException {
    InputStream in = null;
    OutputStream out = null;
    try {
      in = new BufferedInputStream(file.getInputStream());
      File file1 = new File(dir);
      if (file1.exists())
        file1.delete(); 
      out = new BufferedOutputStream(new FileOutputStream(file1));
      byte[] buffered = new byte[8192];
      int size = 0;
      while ((size = in.read(buffered, 0, 8192)) != -1)
        out.write(buffered, 0, size); 
      out.flush();
      in.close();
      out.close();
    } catch (Exception e) {
      in.close();
      out.close();
    } 
  }
  
  public List<String[]> getSheetData(Workbook workbook, HttpServletRequest request) {
    List<String[]> list = (List)new ArrayList<String>();
    List<String> orglist = new ArrayList<String>();
    int row = 0;
    String succeed = "0", message = "";
    Sheet sheet = workbook.getSheet(0);
    String kqnum = "";
    String kqName = "";
    String empid = "", orgid = "";
    String dkrq = "", dkrqtemp = null;
    String sbsj = "", sbsjtemp = null;
    String xbsj = "", xbsjtemp = null;
    String qdsj = "", qdsjtemp = null;
    String qtsj = "", qtsjtemp = null;
    String ztsj = "", ztsjtemp = null;
    String cdsj = "", cdsjtemp = null;
    Date dkdatetemp = null;
    Date sbtimetemp = null;
    Date xbtimetemp = null;
    Date qdtimetemp = null;
    Date qttimetemp = null;
    Date zttimetemp = null;
    Date cdtimetemp = null;
    KqDataImportBean kqimport = new KqDataImportBean();
    if (sheet != null) {
      row = sheet.getRows();
      for (int i = 1; i < row; i++) {
        succeed = "0";
        dkrqtemp = null;
        sbsjtemp = null;
        xbsjtemp = null;
        qdsjtemp = null;
        qtsjtemp = null;
        ztsjtemp = null;
        cdsjtemp = null;
        kqnum = sheet.getCell(2, i).getContents().trim();
        kqName = sheet.getCell(3, i).getContents().trim();
        dkrq = sheet.getCell(5, i).getContents().trim();
        sbsj = sheet.getCell(7, i).getContents().trim();
        xbsj = sheet.getCell(8, i).getContents().trim();
        qdsj = sheet.getCell(9, i).getContents().trim();
        qtsj = sheet.getCell(10, i).getContents().trim();
        ztsj = sheet.getCell(14, i).getContents().trim();
        cdsj = sheet.getCell(13, i).getContents().trim();
        if (kqnum == null || "".equals(kqnum) || "null".equals(kqnum)) {
          if (!"1".equals(succeed))
            succeed = "1"; 
          message = String.valueOf(message) + "第" + (i + 1) + "行,工号不能为空! </br>";
        } else {
          orglist = kqimport.empIdCheck(kqnum);
          if (orglist.size() < 1 || orglist == null) {
            message = String.valueOf(message) + "第" + (i + 1) + "行,工号对应的用户账号不存在! </br>";
            if (!"1".equals(succeed))
              succeed = "1"; 
          } else {
            empid = orglist.get(0);
            orgid = orglist.get(1);
          } 
        } 
        if (kqName == null || "".equals(kqName) || "null".equals(kqName)) {
          message = String.valueOf(message) + "第" + (i + 1) + "行,姓名不能为空! </br>";
          if (!"1".equals(succeed))
            succeed = "1"; 
        } 
        if (dkrq == null || "".equals(dkrq) || "null".equals(dkrq)) {
          message = String.valueOf(message) + "第" + (i + 1) + "行,打卡日期不能为空! </br>";
          if (!"1".equals(succeed))
            succeed = "1"; 
        } else {
          SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
          try {
            dkdatetemp = formatter.parse(dkrq);
            dkrqtemp = formatter.format(dkdatetemp);
          } catch (Exception e) {
            try {
              SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy/MM/dd");
              dkdatetemp = formatter1.parse(dkrq);
              dkrqtemp = formatter.format(dkdatetemp);
            } catch (Exception ex) {
              message = String.valueOf(message) + "第" + (i + 1) + "行,打卡日期格式不正确! " + dkrq + "</br>";
              if (!"1".equals(succeed))
                succeed = "1"; 
            } 
          } 
        } 
        if (sbsj == null && "".equals(sbsj) && "null".equals(sbsj)) {
          message = String.valueOf(message) + "第" + (i + 1) + "行,上班时间不能为空! </br>";
          if (!"1".equals(succeed))
            succeed = "1"; 
        } else {
          SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
          try {
            sbtimetemp = formatter.parse(sbsj);
            sbsjtemp = formatter.format(sbtimetemp);
          } catch (Exception e) {
            message = String.valueOf(message) + "第" + (i + 1) + "行,上班时间格式不正确! </br>";
            if (!"1".equals(succeed))
              succeed = "1"; 
          } 
        } 
        if (xbsj == null && "".equals(xbsj) && "null".equals(xbsj)) {
          message = String.valueOf(message) + "第" + (i + 1) + "行,下班时间不能为空! </br>";
          if (!"1".equals(succeed))
            succeed = "1"; 
        } else {
          SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
          try {
            xbtimetemp = formatter.parse(xbsj);
            xbsjtemp = formatter.format(xbtimetemp);
          } catch (Exception e) {
            message = String.valueOf(message) + "第" + (i + 1) + "行,下班时间格式不正确! </br>";
            if (!"1".equals(succeed))
              succeed = "1"; 
          } 
        } 
        if (qdsj != null && !"".equals(qdsj) && !"null".equals(qdsj)) {
          SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
          try {
            qdtimetemp = formatter.parse(qdsj);
            qdsjtemp = formatter.format(qdtimetemp);
          } catch (Exception e) {
            message = String.valueOf(message) + "第" + (i + 1) + "行,签到时间格式不正确! </br>";
            if (!"1".equals(succeed))
              succeed = "1"; 
          } 
        } 
        if (qtsj != null && !"".equals(qtsj) && !"null".equals(qtsj)) {
          SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
          ParsePosition pos = new ParsePosition(0);
          try {
            qttimetemp = formatter.parse(qtsj);
            qtsjtemp = formatter.format(qttimetemp);
          } catch (Exception e) {
            message = String.valueOf(message) + "第" + (i + 1) + "行,签退时间格式不正确! </br>";
            if (!"1".equals(succeed))
              succeed = "1"; 
          } 
        } 
        if (ztsj != null && !"".equals(ztsj) && !"null".equals(ztsj)) {
          SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
          try {
            zttimetemp = formatter.parse(ztsj);
            ztsjtemp = formatter.format(zttimetemp);
          } catch (Exception e) {
            message = String.valueOf(message) + "第" + (i + 1) + "行,早退时间格式不正确! </br>";
            if (!"1".equals(succeed))
              succeed = "1"; 
          } 
        } 
        if (cdsj != null && !"".equals(cdsj) && !"null".equals(cdsj)) {
          SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
          try {
            cdtimetemp = formatter.parse(cdsj);
            cdsjtemp = formatter.format(cdtimetemp);
          } catch (Exception e) {
            message = String.valueOf(message) + "第" + (i + 1) + "行,迟到时间格式不正确! </br>";
            if (!"1".equals(succeed))
              succeed = "1"; 
          } 
        } 
        if (!"1".equals(succeed))
          list.add(new String[] { 
                empid, orgid, kqnum, kqName, dkrqtemp, sbsjtemp, xbsjtemp, qdsjtemp, qtsjtemp, cdsjtemp, 
                ztsjtemp }); 
      } 
    } 
    request.setAttribute("message", message);
    return list;
  }
  
  private List list(HttpServletRequest request, String para, String from, String where) {
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(para, from, where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    int recordCount = page.getRecordCount();
    if (offset >= recordCount) {
      offset = (recordCount - pageSize) / pageSize;
      currentPage = offset + 1;
      offset *= pageSize;
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      recordCount = page.getRecordCount();
      request.setAttribute("pager.offset", 
          String.valueOf(offset));
      request.setAttribute("pager.realCurrent", 
          String.valueOf(currentPage));
    } 
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("recordCount", 
        String.valueOf(page.getRecordCount()));
    request.setAttribute("pageParameters", "action,empName,searchOrgName,searchOrgId,orderBy,sortType,checkbox,searchBeginDate,searchEndDate,date,kqStatus");
    return list;
  }
}
