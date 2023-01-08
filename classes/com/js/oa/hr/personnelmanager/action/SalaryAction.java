package com.js.oa.hr.personnelmanager.action;

import com.js.oa.hr.personnelmanager.po.SalaryPO;
import com.js.oa.hr.personnelmanager.service.SalaryBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SalaryAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    String type = "list";
    String action = httpServletRequest.getParameter("action");
    if ("detale".equals(action)) {
      type = "detale";
      String salaryId = httpServletRequest.getParameter("id");
      SalaryPO salaryPO = new SalaryPO();
      SalaryBD salaryBD = new SalaryBD();
      salaryPO = salaryBD.searchById(Long.valueOf(Long.parseLong(salaryId)));
      httpServletRequest.setAttribute("salaryPO", salaryPO);
    } else {
      list(httpServletRequest);
    } 
    return actionMapping.findForward(type);
  }
  
  private void list(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String userName = request.getParameter("userName");
    String searchDate = request.getParameter("searchDate");
    String sendTimeBegin = request.getParameter("sendTimeBegin");
    String sendTimeEnd = request.getParameter("sendTimeEnd");
    Long userId = new Long(session.getAttribute("userId").toString());
    ManagerService mbd = new ManagerService();
    boolean scopeSQL = mbd.hasRight(userId.toString(), "07*55*07");
    String hasright = "0";
    String viewSQL = 
      "salary,organization.orgNameString";
    String fromSQL = 
      "com.js.oa.hr.personnelmanager.po.SalaryPO salary join salary.employeeVO.organizations organization";
    String whereSQL = " where 1=1 ";
    if (scopeSQL) {
      hasright = "1";
      ManagerService managerBD = new ManagerService();
      String answerWhere = managerBD.getRightFinalWhere(session
          .getAttribute("userId").toString(), 
          session.getAttribute("orgId").toString(), 
          session.getAttribute("orgIdString")
          .toString(), "人事管理-工资记录", "维护", 
          "salary.createdOrg", 
          "salary.createdEmp");
      whereSQL = String.valueOf(whereSQL) + " and " + answerWhere;
    } 
    request.setAttribute("hasright", hasright);
    if (!scopeSQL)
      whereSQL = String.valueOf(whereSQL) + " and salary.employeeVO.empId = " + userId; 
    if (userName != null && !"".equals(userName))
      whereSQL = String.valueOf(whereSQL) + " and salary.employeeVO.empName like '" + userName + "'"; 
    if (searchDate != null && !"".equals(searchDate))
      whereSQL = String.valueOf(whereSQL) + " and salary.sendTime >= '" + sendTimeBegin + "' and salary.sendTime <= '" + sendTimeEnd + "'"; 
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter(
            "pager.offset")); 
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
      request.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
      request.setAttribute("pager.realCurrent", (
          new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    request.setAttribute("list", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,userName,searchDate,sendTimeBegin,sendTimeEnd");
  }
}
