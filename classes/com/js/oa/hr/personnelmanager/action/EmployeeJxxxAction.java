package com.js.oa.hr.personnelmanager.action;

import com.js.oa.hr.personnelmanager.service.EmployeeJxxxBD;
import com.js.oa.userdb.util.DbOpt;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EmployeeJxxxAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    EmployeeJxxxActionForm employeeJxxxActionForm = (EmployeeJxxxActionForm)actionForm;
    String action = (httpServletRequest.getParameter("action") == null) ? "list" : 
      httpServletRequest.getParameter("action");
    String tag = "list";
    String userId = httpServletRequest.getSession().getAttribute("userId")
      .toString();
    String empID = httpServletRequest.getParameter("empID");
    String open = httpServletRequest.getParameter("open");
    if (empID == null || "".equals(empID) || "null".equals(empID))
      empID = userId; 
    String logonName = "";
    DbOpt db = new DbOpt();
    try {
      logonName = db.executeQueryToStr("select useraccounts from org_employee where emp_id='" + empID + "'");
      db.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    httpServletRequest.setAttribute("open", open);
    httpServletRequest.setAttribute("empID", empID);
    EmployeeJxxxBD bd = new EmployeeJxxxBD();
    if (action.equals("list")) {
      String[][] list = bd.list(logonName);
      httpServletRequest.setAttribute("jxxxList", list);
    } 
    return actionMapping.findForward(tag);
  }
}
