package com.js.system.action.usermanager;

import com.active.e_uc.user.po.TblUser;
import com.active.e_uc.user.service.TblUserBD;
import com.active.e_uc.user.service.TblUserStatusBD;
import com.js.oa.hr.officemanager.service.EmployeeBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.hibernate.HibernateBase;
import com.js.util.page.Page;
import com.js.util.util.ReadActiveXml;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import org.apache.poi2.hssf.usermodel.HSSFCell;
import org.apache.poi2.hssf.usermodel.HSSFCellStyle;
import org.apache.poi2.hssf.usermodel.HSSFDataFormat;
import org.apache.poi2.hssf.usermodel.HSSFFont;
import org.apache.poi2.hssf.usermodel.HSSFRow;
import org.apache.poi2.hssf.usermodel.HSSFSheet;
import org.apache.poi2.hssf.usermodel.HSSFWorkbook;
import org.apache.poi2.hssf.util.Region;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ListUserAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    String cnName, enName, orgName, isSuper, userAccount, empLeaderId;
    HttpSession session = httpServletRequest.getSession(true);
    String status = httpServletRequest.getParameter("status");
    if (status.equals("export")) {
      export(httpServletRequest, "");
      return actionMapping.findForward("export");
    } 
    if (status.equals("downtemplate")) {
      downWeiXinHaoExportTemplate(httpServletRequest, httpServletResponse);
      return null;
    } 
    String para = "user.empId,user.empName,user.empEnglishName,user.empSex,user.userAccounts,user.userIsSuper,organization.orgNameString,user.empDuty,user.empLeaderName,user.empMobilePhone,user.userOnline,user.empEmail,user.opinionRemind ";
    String from = "com.js.system.vo.usermanager.EmployeeVO AS user join user.organizations organization ";
    String where = "";
    UserSearchForm userSearchForm = (UserSearchForm)actionForm;
    if (userSearchForm.getCnName() != null) {
      cnName = userSearchForm.getCnName();
      enName = userSearchForm.getEnName();
      orgName = userSearchForm.getOrgName();
      isSuper = userSearchForm.getIsSuper();
      userAccount = userSearchForm.getUserAccount();
      empLeaderId = userSearchForm.getEmpLeaderId();
    } else {
      cnName = httpServletRequest.getParameter("cnName");
      enName = httpServletRequest.getParameter("enName");
      orgName = httpServletRequest.getParameter("orgName");
      isSuper = httpServletRequest.getParameter("isSuper");
      userAccount = httpServletRequest.getParameter("userAccount");
      empLeaderId = httpServletRequest.getParameter("empLeaderId");
    } 
    httpServletRequest.setAttribute("pageParameters", "status,cnName,enName,orgName,isSuper,userAccount,empLeaderId");
    if (cnName != null) {
      if (!"".equals(cnName) && !"".equals(enName)) {
        where = String.valueOf(where) + " and (user.empName like '%" + cnName + "%' or user.empEnglishName like '%" + enName + "%')";
      } else if (!"".equals(cnName)) {
        where = String.valueOf(where) + " and user.empName like '%" + cnName + "%'";
      } else if (!"".equals(enName)) {
        where = String.valueOf(where) + " and user.empEnglishName like '%" + enName + "%'";
      } 
      if (!"".equals(userAccount) && userAccount != null && !"null".equals(userAccount))
        where = String.valueOf(where) + " and user.userAccounts like '%" + userAccount + "%'"; 
      if (!orgName.equals(""))
        where = String.valueOf(where) + " and organization.orgNameString like '%" + orgName + "%'"; 
      if (isSuper != null && !isSuper.equals(""))
        where = String.valueOf(where) + " and user.userIsSuper=" + isSuper; 
      if (empLeaderId != null && !"".equals(empLeaderId))
        where = String.valueOf(where) + " and user.empLeaderId like '%" + empLeaderId + "%'"; 
    } 
    ManagerService managerBD = new ManagerService();
    String rightName = "00*01*02";
    if ("1".equals(session.getAttribute("sysManager").toString()))
      rightName = "00*01*01"; 
    String whereTmp = managerBD.getRightWhere(session.getAttribute("userId").toString(), 
        session.getAttribute("orgId").toString(), 
        rightName, 
        "organization.orgId", 
        "user.empId");
    if (whereTmp.equals(""))
      whereTmp = "1<1"; 
    if (whereTmp != null && !whereTmp.equals(""))
      where = String.valueOf(where) + " and " + whereTmp; 
    if ("active".equals(status)) {
      where = "where user.userIsActive=1 and user.userIsDeleted=0 and user.userAccounts is not null and user.userIsFormalUser=1 " + where;
      list(httpServletRequest, para, from, where);
      String username = (String)httpServletRequest.getSession().getAttribute("userAccount");
      if ("iactive".equals(ReadActiveXml.getReadActive().getUse())) {
        TblUserBD tblUserBD = new TblUserBD();
        TblUser tblUser = new TblUser();
        TblUserStatusBD tblUserStatusBD = new TblUserStatusBD();
        String status1 = "false";
        try {
          if ("admin".equals(username)) {
            status1 = "false";
          } else {
            tblUser = tblUserBD.findTblUser(username);
            if (tblUser != null)
              status1 = tblUserStatusBD.findstatus(tblUser.getId()); 
          } 
        } catch (HibernateException e) {
          e.printStackTrace();
        } 
        httpServletRequest.setAttribute("iactive", status1);
        try {
          List userOnlinList = tblUserStatusBD.findUserOnline();
          httpServletRequest.setAttribute("userOnlinList", userOnlinList);
        } catch (HibernateException e) {
          e.printStackTrace();
        } 
      } 
      return actionMapping.findForward("active");
    } 
    if ("disabled".equals(status)) {
      where = "where user.userIsActive=0 and user.userIsDeleted=0 and user.userAccounts is not null and user.userIsFormalUser=1 " + where;
      list(httpServletRequest, para, from, where);
      return actionMapping.findForward("disabled");
    } 
    if ("applied".equals(status)) {
      where = "where user.userIsFormalUser=0 " + where;
      list(httpServletRequest, para, from, where);
      return actionMapping.findForward("applied");
    } 
    return actionMapping.findForward("error");
  }
  
  private void list(HttpServletRequest httpServletRequest, String para, String from, String where) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(para, from, String.valueOf(where) + " and user.domainId=" + httpServletRequest.getSession(true).getAttribute("domainId") + " order by organization.orgIdString,user.userOrderCode,user.empDutyLevel,user.empName");
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
      httpServletRequest.setAttribute("pager.offset", 
          String.valueOf(offset));
      httpServletRequest.setAttribute("pager.realCurrent", 
          String.valueOf(currentPage));
    } 
    httpServletRequest.setAttribute("userList", list);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("recordCount", 
        String.valueOf(page.getRecordCount()));
  }
  
  private void export(HttpServletRequest httpServletRequest, String where) {
    ManagerService mbd = new ManagerService();
    String curUserId = httpServletRequest.getSession(true).getAttribute("userId").toString();
    String curOrgId = httpServletRequest.getSession(true).getAttribute("orgId").toString();
    String orgIdString = httpServletRequest.getSession(true).getAttribute("orgIdString").toString();
    String wherePara = "";
    if (httpServletRequest.getSession(true).getAttribute("sysManager").equals("1")) {
      wherePara = " 1=1 ";
    } else {
      wherePara = mbd.getRightFinalWhere(curUserId, curOrgId, orgIdString, "人事信息", "维护", "orgpo.id", "po.createdEmp");
    } 
    if ("".equals(where)) {
      where = String.valueOf(where) + wherePara;
    } else {
      where = String.valueOf(where) + " and " + wherePara;
    } 
    if (!where.equals("")) {
      where = where.trim();
      if (where.startsWith("and")) {
        where = "where po.userIsActive=1 and po.userIsDeleted=0 and po.userAccounts is not null and po.userIsFormalUser=1 " + where + " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName";
      } else {
        where = "where po.userIsActive=1 and po.userIsDeleted=0 and po.userAccounts is not null and po.userIsFormalUser=1 and " + where + " order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName";
      } 
    } else {
      where = "where po.userIsActive=1 and po.userIsDeleted=0 and po.userAccounts is not null and po.userIsFormalUser=1 order by orgpo.orgIdString,po.empDutyLevel,po.userOrderCode,po.empName";
    } 
    httpServletRequest.setAttribute("empList", (new EmployeeBD()).export(where));
  }
  
  public void downWeiXinHaoExportTemplate(HttpServletRequest request, HttpServletResponse response) {
    List<Object[]> emList = new ArrayList();
    HibernateBase hBase = new HibernateBase();
    Session session = null;
    String hql = "SELECT user.empId,user.empName,user.empEnglishName,user.empSex,user.userAccounts,user.userIsSuper,organization.orgNameString,user.empDuty,user.empLeaderName,user.empMobilePhone,user.empEmail FROM com.js.system.vo.usermanager.EmployeeVO AS user join user.organizations organization  where user.userIsActive=1 and user.userIsDeleted=0 and user.userAccounts is not null and user.userIsFormalUser=1  and ( 1=1 ) and user.domainId=0 order by organization.orgIdString,user.userOrderCode,user.empDutyLevel,user.empName";
    try {
      session = hBase.getSession();
      emList = session.createQuery(hql).list();
      HSSFWorkbook wb = new HSSFWorkbook();
      HSSFSheet sheet = wb.createSheet("微信号导入模板");
      HSSFDataFormat format = wb.createDataFormat();
      sheet.setColumnWidth(0, 10240);
      sheet.setColumnWidth(1, 10240);
      sheet.setColumnWidth(2, 10240);
      sheet.setColumnWidth(3, 10240);
      sheet.setColumnWidth(4, 10240);
      sheet.setDefaultRowHeight((short)200);
      HSSFCellStyle style = wb.createCellStyle();
      style.setVerticalAlignment((short)1);
      style.setAlignment((short)2);
      HSSFFont font = wb.createFont();
      font.setFontHeightInPoints((short)20);
      font.setFontName("宋体");
      font.setItalic(false);
      style.setFont(font);
      style.setWrapText(false);
      style.setBorderBottom((short)1);
      style.setBorderLeft((short)1);
      style.setBorderRight((short)1);
      style.setBorderTop((short)1);
      HSSFCellStyle style1 = wb.createCellStyle();
      style1.setVerticalAlignment((short)1);
      style1.setAlignment((short)1);
      HSSFFont font1 = wb.createFont();
      font1.setFontHeightInPoints((short)20);
      font1.setFontName("宋体");
      font1.setItalic(false);
      style1.setFont(font1);
      style1.setWrapText(false);
      style1.setBorderBottom((short)1);
      style1.setBorderLeft((short)1);
      style1.setBorderRight((short)1);
      style1.setBorderTop((short)1);
      HSSFRow row1 = sheet.createRow(0);
      sheet.addMergedRegion(new Region(0, (short)0, 0, (short)4));
      HSSFCell cell1 = row1.createCell((short)0);
      cell1.setCellStyle(style);
      cell1.setCellValue("微信号导入模板");
      HSSFRow row2 = sheet.createRow(1);
      HSSFCell cell2 = row2.createCell((short)0);
      cell2.setCellStyle(style);
      cell2.setCellValue("姓名");
      HSSFCell cell3 = row2.createCell((short)1);
      cell3.setCellStyle(style);
      cell3.setCellValue("账户");
      HSSFCell cell4 = row2.createCell((short)2);
      cell4.setCellStyle(style);
      cell4.setCellValue("邮箱");
      HSSFCell cell5 = row2.createCell((short)3);
      cell5.setCellStyle(style);
      cell5.setCellValue("手机号码");
      HSSFCell cell6 = row2.createCell((short)4);
      cell6.setCellStyle(style);
      cell6.setCellValue("微信号");
      for (int i = 0; i < emList.size(); i++) {
        Object[] vo = emList.get(i);
        HSSFRow row = sheet.createRow(2 + i);
        HSSFCell cellName = row.createCell((short)0);
        cellName.setCellStyle(style1);
        cellName.setCellValue(vo[1].toString());
        HSSFCell cellZh = row.createCell((short)1);
        cellZh.setCellStyle(style1);
        cellZh.setCellValue(vo[4].toString());
        HSSFCell cellEmail = row.createCell((short)2);
        cellEmail.setCellStyle(style1);
        cellEmail.setCellValue((vo[10] == null) ? "" : vo[10].toString());
        HSSFCell cellPhone = row.createCell((short)3);
        cellPhone.setCellStyle(style1);
        cellPhone.setCellValue((vo[9] == null) ? "" : vo[9].toString());
        HSSFCell cellEmp = row.createCell((short)4);
        cellEmp.setCellStyle(style1);
        cellEmp.setCellValue("");
      } 
      response.setContentType("application/vd.ms-excel");
      response.addHeader("Content-Disposition", "attachment;filename=" + new String("微信号导入模板.xls".getBytes("GBK"), "iso8859-1"));
      BufferedOutputStream bos = null;
      bos = new BufferedOutputStream((OutputStream)response.getOutputStream());
      wb.write(bos);
      bos.flush();
      bos.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (session != null)
        try {
          session.close();
        } catch (HibernateException e1) {
          e1.printStackTrace();
        }  
    } 
  }
}
