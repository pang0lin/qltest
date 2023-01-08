package com.js.oa.zky.action;

import com.js.oa.zky.bean.ZkyMedicalBean;
import com.js.oa.zky.po.ZkyMedicalStockPO;
import com.js.util.page.Page;
import com.js.util.page.sql.Page;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ZkyMedicalAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    String action = request.getParameter("action");
    HttpSession session = request.getSession();
    String userId = (String)session.getAttribute("userId");
    String userName = (String)session.getAttribute("userName");
    String username = request.getParameter("username");
    String drugId = request.getParameter("drugId");
    String medicalName = request.getParameter("medicalName");
    String userClass = request.getParameter("userClass");
    String org = request.getParameter("org");
    String num = request.getParameter("medicalNum");
    String checkbox = request.getParameter("checkbox");
    String searchBeginDate = request.getParameter("searchBeginDate");
    String searchEndDate = request.getParameter("searchEndDate");
    String empid = request.getParameter("empid");
    String orderBy = request.getParameter("orderBy");
    String sortType = request.getParameter("sortType");
    String export = request.getParameter("export");
    String nid = "";
    String[] obj = (String[])null;
    if ("null".equals(sortType) || "".equals(sortType) || sortType == null)
      sortType = "desc"; 
    String para = "", where = "", from = "", sql = "";
    if (action.equals("perInfor")) {
      List<String[]> list = (new ZkyMedicalBean()).perInformation(userId);
      obj = list.get(0);
      nid = obj[2];
      session.setAttribute("nid", nid);
      request.setAttribute("perInfor", list);
    } 
    if (action.equals("receiveDetail")) {
      ZkyMedicalBean mBean = new ZkyMedicalBean();
      nid = (String)session.getAttribute("nid");
      para = " b.InOutID,a.InOutDate,a.PatiName,b.stline,b.drugid,b.DrugName,b.DrugRegu,b.quan,b.LPrice,b.quan*b.LPrice score";
      from = " dbo.V_发药主表 a left join dbo.V_发药子表 b on a.inoutid=b.inoutid left join dbo.V_人员基本信息 c on a.patiID=c.patiID left join dbo.V_药品单位 d on b.drugid=d.drugid  ";
      where = "  where c.nid='" + nid + "'";
      if (medicalName != null && !"null".equals(medicalName) && !"".equals(medicalName))
        where = String.valueOf(where) + " and b.DrugName like '%" + medicalName + "%' "; 
      if (checkbox != null && !"null".equals(checkbox) && !"".equals(checkbox)) {
        where = String.valueOf(where) + " and a.InOutDate >='" + searchBeginDate + "' AND a.InOutDate <='" + searchEndDate + " 23:59:59'";
      } else {
        where = String.valueOf(where) + " and a.InOutDate >= DATEADD(yy, (DATEDIFF(yy,0,getdate()))-1, 0)";
      } 
      if ("null".equals(orderBy) || "".equals(orderBy) || orderBy == null)
        where = String.valueOf(where) + " order by a.InOutDate desc"; 
      if ("date".equals(orderBy))
        where = String.valueOf(where) + " order by a.InOutDate " + sortType; 
      if ("count".equals(orderBy))
        where = String.valueOf(where) + " order by b.quan " + sortType; 
      if ("price".equals(orderBy))
        where = String.valueOf(where) + " order by b.LPrice " + sortType; 
      if ("expense".equals(orderBy))
        where = String.valueOf(where) + " order by score " + sortType; 
      List list = list(request, para, from, where);
      double sum = mBean.getPerSum(medicalName, searchBeginDate, searchEndDate, checkbox, nid);
      if ("perMedical_list_export".equals(export)) {
        List<String[]> perMedical_list_export = mBean.exportList(para, from, where, export);
        request.setAttribute("perMedical_list_export", perMedical_list_export);
        request.setAttribute("export", export);
        return actionMapping.findForward("export");
      } 
      request.setAttribute("medicalName", medicalName);
      request.setAttribute("searchBeginDate", searchBeginDate);
      request.setAttribute("searchEndDate", searchEndDate);
      request.setAttribute("checkbox", checkbox);
      request.setAttribute("receiveDetail", list);
      request.setAttribute("sum", Double.valueOf(sum));
    } 
    if (action.equals("medicalStock")) {
      ZkyMedicalBean mBean = new ZkyMedicalBean();
      para = "drugid,drugname,drugregu,drugunit,quan ";
      from = " dbo.V_库存";
      where = " where 1=1";
      if (medicalName != null && !"null".equals(medicalName) && !"".equals(medicalName))
        where = String.valueOf(where) + " and drugname like '%" + medicalName + "%' "; 
      if (drugId != null && !"null".equals(drugId) && !"".equals(drugId))
        where = String.valueOf(where) + " and drugid like '%" + drugId + "%' "; 
      if ("null".equals(orderBy) || "".equals(orderBy) || orderBy == null)
        where = String.valueOf(where) + " order by quan desc"; 
      if ("count".equals(orderBy))
        where = String.valueOf(where) + " order by quan " + sortType; 
      List list = list(request, para, from, where);
      if ("stock_list_export".equals(export)) {
        List<String[]> stock_list_export = mBean.exportList(para, from, where, export);
        request.setAttribute("stock_list_export", stock_list_export);
        request.setAttribute("export", export);
        return actionMapping.findForward("export");
      } 
      request.setAttribute("medicalStock", list);
      request.setAttribute("medicalName", medicalName);
      request.setAttribute("medicalNum", num);
    } 
    if (action.equals("medicalPayout")) {
      ZkyMedicalBean mBean = new ZkyMedicalBean();
      para = " b.drugid,b.DrugName,b.DrugRegu,d.UnitDes,sum(b.quan) quan,sum(b.quan*b.LPrice) score";
      from = " dbo.V_发药主表  a left join dbo.V_发药子表  b on a.inoutid=b.inoutid left join dbo.V_药品单位  d  on b.drugid=d.drugid ";
      where = " where 1=1";
      if (medicalName != null && !"null".equals(medicalName) && !"".equals(medicalName))
        where = String.valueOf(where) + "  and b.DrugName like '%" + medicalName + "%' "; 
      if (checkbox != null && !"null".equals(checkbox) && !"".equals(checkbox))
        where = String.valueOf(where) + " and a.InOutDate >='" + searchBeginDate + "' AND a.InOutDate <='" + searchEndDate + " 23:59:59'"; 
      where = String.valueOf(where) + " group by b.DrugName,b.drugid,b.DrugRegu,d.UnitDes";
      String orderByTmp = "";
      if ("count".equals(orderBy))
        orderByTmp = String.valueOf(orderByTmp) + " order by quan " + sortType; 
      if ("expense".equals(orderBy))
        orderByTmp = String.valueOf(orderByTmp) + " order by score " + sortType; 
      if (orderByTmp.equals(""))
        orderByTmp = " order by score desc"; 
      where = String.valueOf(where) + orderByTmp;
      List list = list(request, para, from, where);
      if ("payout_list_export".equals(export)) {
        List<String[]> payout_list_export = mBean.exportList(para, from, where, export);
        request.setAttribute("payout_list_export", payout_list_export);
        request.setAttribute("export", export);
        return actionMapping.findForward("export");
      } 
      request.setAttribute("medicalPayout", list);
    } 
    if (action.equals("medicalReceiveDetail")) {
      ZkyMedicalBean mBean = new ZkyMedicalBean();
      para = " b.DrugName,a.PatiName,a.InOutDate,b.quan,b.quan*b.LPrice score";
      from = " dbo.V_发药主表  a left join dbo.V_发药子表  b on a.inoutid=b.inoutid left join dbo.V_药品单位  d  on b.drugid=d.drugid  ";
      where = " where b.drugid='" + drugId + "'";
      if (checkbox != null && !"null".equals(checkbox) && !"".equals(checkbox))
        where = String.valueOf(where) + " and a.InOutDate >='" + searchBeginDate + "' AND a.InOutDate <='" + searchEndDate + " 23:59:59'"; 
      if ("null".equals(orderBy) || "".equals(orderBy) || orderBy == null)
        where = String.valueOf(where) + " order by a.InOutDate desc"; 
      if ("date".equals(orderBy))
        where = String.valueOf(where) + " order by a.InOutDate " + sortType; 
      if ("count".equals(orderBy))
        where = String.valueOf(where) + " order by b.quan " + sortType; 
      if ("expense".equals(orderBy))
        where = String.valueOf(where) + " order by score " + sortType; 
      List list = list(request, para, from, where);
      request.setAttribute("drugId", drugId);
      request.setAttribute("medicalReceiveDetail", list);
      request.setAttribute("searchBeginDate", searchBeginDate);
      request.setAttribute("searchEndDate", searchEndDate);
      request.setAttribute("checkbox", checkbox);
      if ("detail_list_export".equals(export)) {
        List<String[]> detail_list_export = mBean.exportList(para, from, where, export);
        request.setAttribute("detail_list_export", detail_list_export);
        request.setAttribute("export", export);
        return actionMapping.findForward("export");
      } 
    } 
    if (action.equals("classStatistical")) {
      ZkyMedicalBean mBean = new ZkyMedicalBean();
      List<String> perclass = mBean.perClass();
      para = " b.DrugName,b.DrugRegu,c.patitypeid2,sum(b.quan) quan,sum(b.quan*b.LPrice) score";
      from = " dbo.V_发药主表 a left join dbo.V_发药子表 b on a.inoutid=b.inoutid left join dbo.V_人员基本信息 c on a.patiID=c.patiID";
      where = " where 1=1";
      if (medicalName != null && !"null".equals(medicalName) && !"".equals(medicalName))
        where = String.valueOf(where) + " and b.DrugName like '%" + medicalName + "%' "; 
      if (userClass != null && !"null".equals(userClass) && !"".equals(userClass))
        where = String.valueOf(where) + " and c.patitypeid2 like '%" + userClass + "%' "; 
      if (checkbox != null && !"null".equals(checkbox) && !"".equals(checkbox))
        where = String.valueOf(where) + " and a.InOutDate >='" + searchBeginDate + "' AND a.InOutDate <='" + searchEndDate + " 23:59:59'"; 
      where = String.valueOf(where) + " GROUP by b.DrugName, c.patitypeid2,b.DrugRegu";
      if ("null".equals(orderBy) || "".equals(orderBy) || orderBy == null)
        where = String.valueOf(where) + " order by b.DrugName,sum(b.quan) desc"; 
      if ("count".equals(orderBy))
        where = String.valueOf(where) + " order by b.DrugName,sum(b.quan) " + sortType; 
      if ("expense".equals(orderBy))
        where = String.valueOf(where) + " order by b.DrugName,sum(b.quan*b.LPrice) " + sortType; 
      List list = list(request, para, from, where);
      request.setAttribute("perclass", perclass);
      request.setAttribute("medicalName", medicalName);
      request.setAttribute("userClass", userClass);
      request.setAttribute("searchBeginDate", searchBeginDate);
      request.setAttribute("searchEndDate", searchEndDate);
      request.setAttribute("classStatistical", list);
      if ("stalical_list_export".equals(export)) {
        List<String[]> stalical_list_export = mBean.exportList(para, from, where, export);
        request.setAttribute("stalical_list_export", stalical_list_export);
        request.setAttribute("export", export);
        return actionMapping.findForward("export");
      } 
      Map<String, String> sumMap = mBean.getSum(medicalName, userClass, searchBeginDate, searchEndDate, checkbox);
      request.setAttribute("sum_stat", sumMap);
    } 
    if (action.equals("medicalMaintain")) {
      ZkyMedicalBean mBean = new ZkyMedicalBean();
      para = " a.empId,a.empName,b.orgName,a.guid";
      from = " com.js.system.vo.usermanager.EmployeeVO AS a join a.organizations b";
      where = " where a.userIsActive = 1 and a.userIsDeleted = 0 and a.userIsFormalUser = 1 and a.domainId = 0";
      if (username != null && !"null".equals(username) && !"".equals(username))
        where = String.valueOf(where) + "  and a.empName like '%" + username + "%' "; 
      if (org != null && !"null".equals(org) && !"".equals(org))
        where = String.valueOf(where) + "  and b.orgName like '%" + org + "%' "; 
      if ("null".equals(orderBy) || "".equals(orderBy) || orderBy == null)
        where = String.valueOf(where) + " order by a.guid"; 
      if ("expense".equals(orderBy))
        where = String.valueOf(where) + " order by a.guid " + sortType; 
      List list = listPer(request, para, from, where);
      request.setAttribute("medicalMaintain", list);
      request.setAttribute("username", username);
      request.setAttribute("org", org);
      if ("maintain_list_export".equals(export)) {
        para = " a.EMPNAME,c.ORGNAME,a.guid";
        from = " ORG_EMPLOYEE a inner join ORG_ORGANIZATION_USER b on a.EMP_ID = b.Emp_id inner join ORG_ORGANIZATION c on b.Org_Id = c.ORG_ID";
        where = " where a.USERISACTIVE = 1 and a.USERISDELETED = 0 and a.USERISFORMALUSER = 1 and a.DOMAIN_ID = 0 ";
        if (username != null && !"null".equals(username) && !"".equals(username))
          where = String.valueOf(where) + "  and a.empName like '%" + username + "%' "; 
        if (org != null && !"null".equals(org) && !"".equals(org))
          where = String.valueOf(where) + "  and c.orgName like '%" + org + "%' "; 
        if ("null".equals(orderBy) || "".equals(orderBy) || orderBy == null)
          where = String.valueOf(where) + " order by a.guid"; 
        if ("expense".equals(orderBy))
          where = String.valueOf(where) + " order by a.guid " + sortType; 
        List<String[]> maintain_list_export = mBean.exportList(para, from, where, export);
        request.setAttribute("maintain_list_export", maintain_list_export);
        request.setAttribute("export", export);
        return actionMapping.findForward("export");
      } 
    } 
    if (action.equals("update")) {
      int nu = (new ZkyMedicalBean()).medicalupdate(empid, num);
      request.setAttribute("nu", Integer.valueOf(nu));
    } 
    if (action.equals("DrugView")) {
      ZkyMedicalBean mBean = new ZkyMedicalBean();
      para = "drugname,drugregu,drugunit ";
      from = " dbo.V_库存";
      where = " where 1=1";
      if (medicalName != null && !"null".equals(medicalName) && !"".equals(medicalName))
        where = String.valueOf(where) + " and drugname like '%" + medicalName + "%' "; 
      List<ZkyMedicalStockPO> list = list(request, para, from, where);
      request.setAttribute("DrugView", list);
      request.setAttribute("medicalName", medicalName);
      request.setAttribute("medicalNum", num);
      if ("drugView_list_export".equals(export)) {
        List<String[]> drugView_list_export = mBean.exportList(para, from, where, export);
        request.setAttribute("drugView_list_export", drugView_list_export);
        request.setAttribute("export", export);
        return actionMapping.findForward("export");
      } 
    } 
    if (action.equals("tongbuMedical")) {
      ZkyMedicalBean mBean = new ZkyMedicalBean();
      List<String[]> tongbuList = mBean.tongbuMedicalNum();
      request.setAttribute("tongbuList", tongbuList);
    } 
    return actionMapping.findForward(action);
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
    List list = page.getResultList("jdbc/zhky");
    int recordCount = page.getRecordCount();
    if (offset >= recordCount) {
      offset = (recordCount - pageSize) / pageSize;
      currentPage = offset + 1;
      offset *= pageSize;
      page.setcurrentPage(currentPage);
      list = page.getResultList("jdbc/zhky");
      recordCount = page.getRecordCount();
      request.setAttribute("pager.offset", 
          String.valueOf(offset));
      request.setAttribute("pager.realCurrent", 
          String.valueOf(currentPage));
    } 
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("recordCount", 
        String.valueOf(page.getRecordCount()));
    request.setAttribute("pageParameters", "action,drugId,medicalName,userClass,orderBy,sortType,username,checkbox,searchBeginDate,searchEndDate,count,date,price,expense");
    return list;
  }
  
  private List listPer(HttpServletRequest request, String para, String from, String where) {
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
    request.setAttribute("pageParameters", "action,drugId,medicalName,userClass,orderBy,sortType,username,checkbox,searchBeginDate,searchEndDate,count,date,price,expense");
    return list;
  }
}
