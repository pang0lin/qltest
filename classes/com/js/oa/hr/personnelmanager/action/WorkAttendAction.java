package com.js.oa.hr.personnelmanager.action;

import com.js.oa.hr.personnelmanager.po.WorkAttendancePO;
import com.js.oa.hr.personnelmanager.service.WorkAttendanceBD;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.Page;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WorkAttendAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    WorkAttendActionForm workAttendActionForm = (WorkAttendActionForm)actionForm;
    String flag = (httpServletRequest.getParameter("flag") == null) ? "view" : httpServletRequest.getParameter("flag");
    String tag = "view";
    if (flag.equals("view")) {
      view(httpServletRequest);
      tag = "view";
    } else if (flag.equals("close") || flag.equals("continue")) {
      if (save(workAttendActionForm, httpServletRequest)) {
        if (flag.equals("continue")) {
          httpServletRequest.setAttribute("close", "0");
          workAttendActionForm.setOrgName("");
          workAttendActionForm.setOrg("");
        } else {
          httpServletRequest.setAttribute("close", "1");
        } 
      } else {
        httpServletRequest.setAttribute("close", "2");
      } 
      tag = "add";
    } else if (flag.equals("search")) {
      search(httpServletRequest);
      if ("".equals(httpServletRequest.getParameter("searchOrg"))) {
        tag = "view";
      } else {
        tag = "stat";
      } 
    } else if (flag.equals("delete")) {
      delete(httpServletRequest);
      view(httpServletRequest);
      tag = "view";
    } else if (flag.equals("modify")) {
      tag = "modify";
      getSingle(workAttendActionForm, httpServletRequest);
    } else if (flag.equals("update")) {
      tag = "modify";
      if (update(workAttendActionForm, httpServletRequest)) {
        httpServletRequest.setAttribute("close", "1");
      } else {
        httpServletRequest.setAttribute("close", "2");
        getSingle(workAttendActionForm, httpServletRequest);
      } 
    } 
    return actionMapping.findForward(tag);
  }
  
  private boolean save(WorkAttendActionForm workAttendActionForm, HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    StringBuffer recordBuffer = new StringBuffer(31);
    for (int i = 1; i <= 31; i++)
      recordBuffer.append(httpServletRequest.getParameter("record" + i)); 
    WorkAttendancePO workAttendancePO = new WorkAttendancePO();
    workAttendancePO.setEmp(new Long(workAttendActionForm.getEmp()));
    String org_id = "";
    DbOpt opt = new DbOpt();
    try {
      String sql = "SELECT ORG_ID FROM ORG_ORGANIZATION_USER WHERE (EMP_ID = " + workAttendancePO.getEmp() + ")";
      org_id = opt.executeQueryToStr(sql);
      opt.close();
    } catch (Exception ex) {
      try {
        opt.close();
      } catch (SQLException sQLException) {}
      ex.printStackTrace();
    } 
    workAttendancePO.setOrg(new Long(org_id));
    workAttendancePO.setYear(new Integer(workAttendActionForm.getYear()));
    workAttendancePO.setMonth(new Integer(workAttendActionForm.getMonth()));
    workAttendancePO.setRecord(recordBuffer.toString());
    workAttendancePO.setFillDate(new Date(httpServletRequest.getParameter("fillDate")));
    workAttendancePO.setDomainId(domainId);
    return (new WorkAttendanceBD()).save(workAttendancePO).booleanValue();
  }
  
  private void view(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    Object object1 = httpServletRequest.getSession(true).getAttribute("userId");
    Object object2 = httpServletRequest.getSession(true)
      .getAttribute("orgId");
    Object object3 = httpServletRequest.getSession(true)
      .getAttribute("orgIdString");
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    String viewSql = "wa.id,o.orgNameString,e.empName,wa.year,wa.month";
    String fromSql = "com.js.oa.hr.personnelmanager.po.WorkAttendancePO wa,com.js.system.vo.usermanager.EmployeeVO e,com.js.system.vo.organizationmanager.OrganizationVO o";
    ManagerService mbd = new ManagerService();
    String rightwhere = " and " + mbd.getRightFinalWhere((String)object1, (String)object2, (String)object3, "人事信息-考勤管理", "查看", "wa.org", "wa.emp");
    String whereSql = "where wa.emp=e.empId and wa.org=o.orgId and wa.domainId=" + domainId + rightwhere + " order by wa.id desc";
    list(httpServletRequest, viewSql, fromSql, whereSql);
  }
  
  private void list(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    if (list.size() == 0 && offset >= 15) {
      offset -= 15;
      currentPage = offset / pageSize + 1;
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      httpServletRequest.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
      httpServletRequest.setAttribute("pager.realCurrent", (new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("workAttendList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "flag,searchEmp,searchOrg,searchYear,searchMonth");
  }
  
  private void search(HttpServletRequest httpServletRequest) {
    String searchOrg = httpServletRequest.getParameter("searchOrg");
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    if ("".equals(searchOrg)) {
      String viewSql = "wa.id,o.orgNameString,e.empName,wa.year,wa.month";
      String fromSql = "com.js.oa.hr.personnelmanager.po.WorkAttendancePO wa,com.js.system.vo.usermanager.EmployeeVO e,com.js.system.vo.organizationmanager.OrganizationVO o";
      String whereSql = "where wa.emp=e.empId and wa.org=o.orgId ";
      if (!httpServletRequest.getParameter("searchEmp").equals(""))
        whereSql = String.valueOf(whereSql) + " and e.empName like '%" + httpServletRequest.getParameter("searchEmp") + "%' "; 
      whereSql = String.valueOf(whereSql) + "and wa.year=" + httpServletRequest.getParameter("searchYear") + " and " + 
        "wa.month=" + httpServletRequest.getParameter("searchMonth") + " and wa.domainId=" + domainId + " order by wa.id desc";
      list(httpServletRequest, viewSql, fromSql, whereSql);
    } else {
      httpServletRequest.setAttribute("statList", (
          new WorkAttendanceBD()).stat(httpServletRequest.getParameter("searchOrg"), httpServletRequest.getParameter("searchYear"), httpServletRequest.getParameter("searchMonth")));
    } 
  }
  
  private void delete(HttpServletRequest httpServletRequest) {
    httpServletRequest.setAttribute("deleteSuccess", (
        new WorkAttendanceBD()).delete(httpServletRequest.getParameter("waId")));
  }
  
  private void getSingle(WorkAttendActionForm workAttendActionForm, HttpServletRequest httpServletRequest) {
    Object[] single = (new WorkAttendanceBD()).getSingle(httpServletRequest.getParameter("waId"));
    workAttendActionForm.setOrg(single[0].toString());
    workAttendActionForm.setOrgName(single[1].toString());
    workAttendActionForm.setEmpName(single[2].toString());
    workAttendActionForm.setEmp(single[3].toString());
    httpServletRequest.setAttribute("record", single[4]);
    httpServletRequest.setAttribute("fillDate", single[5]);
    workAttendActionForm.setYear(single[6].toString());
    workAttendActionForm.setMonth(single[7].toString());
  }
  
  private boolean update(WorkAttendActionForm workAttendActionForm, HttpServletRequest httpServletRequest) {
    StringBuffer recordBuffer = new StringBuffer(31);
    for (int i = 1; i <= 31; i++)
      recordBuffer.append(httpServletRequest.getParameter("record" + i)); 
    WorkAttendancePO workAttendancePO = new WorkAttendancePO();
    workAttendancePO.setId(new Long(httpServletRequest.getParameter("waId")));
    workAttendancePO.setEmp(new Long(workAttendActionForm.getEmp()));
    workAttendancePO.setOrg(new Long(workAttendActionForm.getOrg()));
    workAttendancePO.setYear(new Integer(workAttendActionForm.getYear()));
    workAttendancePO.setMonth(new Integer(workAttendActionForm.getMonth()));
    workAttendancePO.setRecord(recordBuffer.toString());
    workAttendancePO.setFillDate(new Date(httpServletRequest.getParameter("fillDate")));
    return (new WorkAttendanceBD()).update(workAttendancePO).booleanValue();
  }
}
