package com.js.oa.hr.personnelmanager.action;

import com.js.oa.hr.personnelmanager.service.EmployeeEdustoryBD;
import com.js.system.vo.usermanager.EdustoryVO;
import com.js.util.page.Page;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EmployeeEdustyAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    EmployeeEdustyActionForm employeeEdustyActionForm = 
      (EmployeeEdustyActionForm)actionForm;
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
      employeeEdustyActionForm.setEmpID(new Long(empID));
    } else if (action.equals("close") || action.equals("continue")) {
      tag = "add";
      if (save(employeeEdustyActionForm, httpServletRequest)) {
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
      Long id = new Long((httpServletRequest.getParameter("edustyId") == null) ? 
          "0" : 
          httpServletRequest.getParameter("edustyId"));
      EdustoryVO vo = (new EmployeeEdustoryBD()).load(id);
      employeeEdustyActionForm.setSchools(vo.getSchools());
      employeeEdustyActionForm.setSpeciality(vo.getSpeciality());
      employeeEdustyActionForm.setEducation(vo.getEducation());
      employeeEdustyActionForm.setId(id);
      employeeEdustyActionForm.setEmpID(new Long(vo.getEmployeeVO()
            .getEmpId()));
      httpServletRequest.setAttribute("beginDate", vo.getBeginDate());
      httpServletRequest.setAttribute("endDate", vo.getEndDate());
      httpServletRequest.setAttribute("education", vo.getEducation());
      httpServletRequest.setAttribute("degree", vo.getDegree());
    } else if (action.equals("update")) {
      tag = "modify";
      EdustoryVO vo = new EdustoryVO();
      vo.setBeginDate(new Date(httpServletRequest.getParameter(
              "beginDate")));
      vo.setEndDate(new Date(httpServletRequest.getParameter("endDate")));
      vo.setSchools(employeeEdustyActionForm.getSchools());
      vo.setSpeciality(employeeEdustyActionForm.getSpeciality());
      vo.setEducation(employeeEdustyActionForm.getEducation());
      vo.setDegree(employeeEdustyActionForm.getDegree());
      if ((new EmployeeEdustoryBD()).update(vo, 
          employeeEdustyActionForm.getEmpID(), 
          employeeEdustyActionForm.getId())) {
        httpServletRequest.setAttribute("close", "1");
      } else {
        httpServletRequest.setAttribute("close", "2");
      } 
    } else if (action.equals("delete")) {
      tag = "list";
      if (httpServletRequest.getParameter("edustyId") != null && 
        !"0".equals(httpServletRequest.getParameter("edustyId")))
        (new EmployeeEdustoryBD()).delete(new Long(httpServletRequest.getParameter("edustyId"))); 
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
      "po.id,po.beginDate,po.endDate,po.schools,po.speciality,po.education,po.degree";
    String fromSQL = 
      "com.js.system.vo.usermanager.EdustoryVO po join po.employeeVO ppo";
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
    request.setAttribute("edustyList", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "");
  }
  
  private boolean save(EmployeeEdustyActionForm form, HttpServletRequest request) {
    boolean retflag = false;
    EdustoryVO vo = new EdustoryVO();
    vo.setBeginDate(new Date(request.getParameter("beginDate")));
    vo.setEndDate(new Date(request.getParameter("endDate")));
    vo.setSchools(form.getSchools());
    vo.setSpeciality(form.getSpeciality());
    vo.setEducation(form.getEducation());
    vo.setDegree(form.getDegree());
    retflag = (new EmployeeEdustoryBD()).save(vo, form.getEmpID());
    return retflag;
  }
}
