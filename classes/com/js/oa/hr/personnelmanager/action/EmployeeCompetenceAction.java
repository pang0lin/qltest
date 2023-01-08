package com.js.oa.hr.personnelmanager.action;

import com.js.oa.hr.personnelmanager.service.EmployeeCompetenceBD;
import com.js.system.vo.usermanager.CompetenceVO;
import com.js.util.page.Page;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EmployeeCompetenceAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    EmployeeCompetenceActionForm employeeCompetenceActionForm = 
      (EmployeeCompetenceActionForm)actionForm;
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
      employeeCompetenceActionForm.setEmpID(new Long(empID));
    } else if (action.equals("close") || action.equals("continue")) {
      tag = "add";
      if (save(employeeCompetenceActionForm, httpServletRequest)) {
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
      Long id = new Long((httpServletRequest.getParameter("competenceId") == null) ? 
          "0" : 
          httpServletRequest.getParameter("competenceId"));
      CompetenceVO vo = (new EmployeeCompetenceBD()).load(id);
      employeeCompetenceActionForm.setCertificateName(vo.getCertificateName());
      employeeCompetenceActionForm.setAwardUnits(vo.getAwardUnits());
      employeeCompetenceActionForm.setId(id);
      employeeCompetenceActionForm.setEmpID(new Long(vo.getEmployeeVO()
            .getEmpId()));
      httpServletRequest.setAttribute("beginDate", vo.getBeginDate());
      httpServletRequest.setAttribute("endDate", vo.getEndDate());
    } else if (action.equals("update")) {
      tag = "modify";
      CompetenceVO vo = new CompetenceVO();
      vo.setBeginDate(new Date(httpServletRequest.getParameter(
              "beginDate")));
      vo.setEndDate(new Date(httpServletRequest.getParameter("endDate")));
      vo.setCertificateName(employeeCompetenceActionForm.getCertificateName());
      vo.setAwardUnits(employeeCompetenceActionForm.getAwardUnits());
      if ((new EmployeeCompetenceBD()).update(vo, 
          employeeCompetenceActionForm.getEmpID(), 
          employeeCompetenceActionForm.getId())) {
        httpServletRequest.setAttribute("close", "1");
      } else {
        httpServletRequest.setAttribute("close", "2");
      } 
    } else if (action.equals("delete")) {
      tag = "list";
      if (httpServletRequest.getParameter("competenceId") != null && 
        !"0".equals(httpServletRequest.getParameter("competenceId")))
        (new EmployeeCompetenceBD()).delete(new Long(httpServletRequest.getParameter("competenceId"))); 
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
      "po.id,po.certificateName,po.beginDate,po.endDate,po.awardUnits";
    String fromSQL = 
      "com.js.system.vo.usermanager.CompetenceVO po join po.employeeVO ppo";
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
    request.setAttribute("competenceList", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "");
  }
  
  private boolean save(EmployeeCompetenceActionForm form, HttpServletRequest request) {
    boolean retflag = false;
    CompetenceVO vo = new CompetenceVO();
    vo.setBeginDate(new Date(request.getParameter("beginDate")));
    vo.setEndDate(new Date(request.getParameter("endDate")));
    vo.setCertificateName(form.getCertificateName());
    vo.setAwardUnits(form.getAwardUnits());
    retflag = (new EmployeeCompetenceBD()).save(vo, form.getEmpID());
    return retflag;
  }
}
