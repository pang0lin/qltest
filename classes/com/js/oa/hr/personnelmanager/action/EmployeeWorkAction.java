package com.js.oa.hr.personnelmanager.action;

import com.js.oa.hr.personnelmanager.service.EmployeeWorkBD;
import com.js.system.vo.usermanager.WorkVO;
import com.js.util.page.Page;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EmployeeWorkAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    EmployeeWorkActionForm employeeWorkActionForm = 
      (EmployeeWorkActionForm)actionForm;
    String action = (httpServletRequest.getParameter("action") == null) ? 
      "list" : httpServletRequest.getParameter("action");
    String tag = "list";
    String userId = httpServletRequest.getSession().getAttribute("userId").toString();
    String empID = httpServletRequest.getParameter("empID");
    String open = httpServletRequest.getParameter("open");
    if (empID == null || "".equals(empID) || "null".equals(empID))
      empID = userId; 
    httpServletRequest.setAttribute("open", open);
    httpServletRequest.setAttribute("empID", empID);
    if (action.equals("list")) {
      list(httpServletRequest);
    } else if (action.equals("add")) {
      tag = "add";
      employeeWorkActionForm.setEmpID(new Long(empID));
    } else if (action.equals("close") || action.equals("continue")) {
      tag = "add";
      if (save(employeeWorkActionForm, httpServletRequest)) {
        if (action.equals("continue")) {
          httpServletRequest.setAttribute("close", "0");
        } else {
          httpServletRequest.setAttribute("close", "1");
        } 
      } else {
        httpServletRequest.setAttribute("close", "2");
      } 
    } else if (action.equals("modify")) {
      tag = "modify";
      Long id = new Long((httpServletRequest.getParameter("workId") == null) ? 
          "0" : 
          httpServletRequest.getParameter("workId"));
      WorkVO vo = (new EmployeeWorkBD()).load(id);
      employeeWorkActionForm.setWorkunit(vo.getWorkunit());
      employeeWorkActionForm.setWorkduty(vo.getWorkduty());
      employeeWorkActionForm.setProveperson(vo.getProveperson());
      employeeWorkActionForm.setTelephone(vo.getTelephone());
      employeeWorkActionForm.setWorkMemo(vo.getWorkMemo());
      employeeWorkActionForm.setId(id);
      employeeWorkActionForm.setEmpID(new Long(vo.getEmployeeVO()
            .getEmpId()));
      httpServletRequest.setAttribute("beginDate", vo.getBeginDate());
      httpServletRequest.setAttribute("endDate", vo.getEndDate());
      if (vo.getEndDate() == null) {
        httpServletRequest.setAttribute("ifEndNow", "1");
      } else {
        httpServletRequest.setAttribute("ifEndNow", "0");
      } 
    } else if (action.equals("update")) {
      tag = "modify";
      WorkVO vo = new WorkVO();
      vo.setBeginDate(new Date(httpServletRequest.getParameter(
              "beginDate")));
      vo.setEndDate(new Date(httpServletRequest.getParameter("endDate")));
      vo.setWorkunit(employeeWorkActionForm.getWorkunit());
      vo.setWorkduty(employeeWorkActionForm.getWorkduty());
      vo.setProveperson(employeeWorkActionForm.getProveperson());
      vo.setTelephone(employeeWorkActionForm.getTelephone());
      vo.setWorkMemo(employeeWorkActionForm.getWorkMemo());
      if ((new EmployeeWorkBD()).update(vo, 
          employeeWorkActionForm.getEmpID(), 
          employeeWorkActionForm.getId())) {
        httpServletRequest.setAttribute("close", "1");
      } else {
        httpServletRequest.setAttribute("close", "2");
      } 
    } else if (action.equals("delete")) {
      tag = "list";
      if (httpServletRequest.getParameter("workId") != null && 
        !"0".equals(httpServletRequest.getParameter("workId")))
        (new EmployeeWorkBD()).delete(new Long(httpServletRequest.getParameter("workId"))); 
      list(httpServletRequest);
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest request) {
    String userId = request.getSession().getAttribute("userId").toString();
    String empID = request.getParameter("empID");
    if (empID == null || "".equals(empID) || "null".equals(empID))
      empID = userId; 
    String viewSQL = 
      "po.id,po.beginDate,po.endDate,po.workunit,po.workduty,po.proveperson,po.telephone,po.workMemo";
    String fromSQL = 
      "com.js.system.vo.usermanager.WorkVO po join po.employeeVO ppo";
    String whereSQL = "where ppo.empId=" + new Long(empID) + 
      " order by po.beginDate desc ";
    int pageSize = 999999;
    int offset = 0;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    int currentPage = offset / pageSize + 1;
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    request.setAttribute("workList", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "");
  }
  
  private boolean save(EmployeeWorkActionForm form, HttpServletRequest request) {
    boolean retflag = false;
    WorkVO vo = new WorkVO();
    String ifEndNow = request.getParameter("ifEndNow");
    if (ifEndNow != null && "1".equals(ifEndNow)) {
      vo.setEndDate(null);
    } else {
      vo.setEndDate(new Date(request.getParameter("endDate")));
    } 
    vo.setBeginDate(new Date(request.getParameter("beginDate")));
    vo.setWorkunit(form.getWorkunit());
    vo.setWorkduty(form.getWorkduty());
    vo.setProveperson(form.getProveperson());
    vo.setTelephone(form.getTelephone());
    vo.setWorkMemo(form.getWorkMemo());
    retflag = (new EmployeeWorkBD()).save(vo, form.getEmpID());
    return retflag;
  }
}
