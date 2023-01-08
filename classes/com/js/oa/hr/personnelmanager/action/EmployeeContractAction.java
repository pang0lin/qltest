package com.js.oa.hr.personnelmanager.action;

import com.js.oa.hr.personnelmanager.service.EmployeeContractBD;
import com.js.system.vo.usermanager.ContractVO;
import com.js.util.page.Page;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EmployeeContractAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    EmployeeContractActionForm employeeContractActionForm = 
      (EmployeeContractActionForm)actionForm;
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
      employeeContractActionForm.setEmpID(new Long(empID));
      tag = "add";
    } else if (action.equals("close") || action.equals("continue")) {
      tag = "add";
      if (save(employeeContractActionForm, httpServletRequest)) {
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
      Long id = new Long((httpServletRequest.getParameter("contractId") == null) ? 
          "0" : 
          httpServletRequest.getParameter("contractId"));
      ContractVO vo = (new EmployeeContractBD()).load(id);
      employeeContractActionForm.setContractStyle(vo.getContractStyle());
      employeeContractActionForm.setEmpID(new Long(vo.getEmployeeVO()
            .getEmpId()));
      employeeContractActionForm.setId(vo.getId());
      employeeContractActionForm.setContractNO(vo.getContractNO());
      employeeContractActionForm.setContractType(vo.getContractType());
      if ("0".equals(vo.getContractType()))
        employeeContractActionForm.setContractLimit(vo.getContractLimit()); 
      httpServletRequest.setAttribute("contractType", vo.getContractType());
      httpServletRequest.setAttribute("beginDate", vo.getBeginDate());
      httpServletRequest.setAttribute("endDate", vo.getEndDate());
      httpServletRequest.setAttribute("givenDate", vo.getGivenDate());
    } else if (action.equals("update")) {
      tag = "modify";
      ContractVO po = new ContractVO();
      po.setContractStyle(employeeContractActionForm.getContractStyle());
      po.setContractNO(employeeContractActionForm.getContractNO());
      po.setGivenDate(new Date(httpServletRequest.getParameter(
              "givenDate")));
      po.setBeginDate(new Date(httpServletRequest.getParameter(
              "beginDate")));
      po.setEndDate(new Date(httpServletRequest.getParameter("endDate")));
      po.setContractType(employeeContractActionForm.getContractType());
      if ("0".equals(employeeContractActionForm.getContractType()))
        po.setContractLimit(employeeContractActionForm.getContractLimit()); 
      if ((new EmployeeContractBD()).update(po, 
          employeeContractActionForm
          .getEmpID(), 
          employeeContractActionForm
          .getId())) {
        httpServletRequest.setAttribute("close", "1");
      } else {
        httpServletRequest.setAttribute("close", "2");
      } 
    } else if (action.equals("delete")) {
      tag = "list";
      if (httpServletRequest.getParameter("contractId") != null && 
        !"0".equals(httpServletRequest.getParameter("contractId")))
        (new EmployeeContractBD()).delete(new Long(httpServletRequest.getParameter("contractId"))); 
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
      "po.id,po.givenDate,po.contractStyle,po.beginDate,po.endDate, po.contractType, po.contractLimit,po.contractNO";
    String fromSQL = 
      "com.js.system.vo.usermanager.ContractVO po join po.employeeVO ppo";
    String whereSQL = "where ppo.empId=" + new Long(empID) + " order by po.givenDate desc ";
    int pageSize = 999999;
    int offset = 0;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    int currentPage = offset / pageSize + 1;
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    request.setAttribute("contractList", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "");
  }
  
  private boolean save(EmployeeContractActionForm form, HttpServletRequest request) {
    ContractVO po = new ContractVO();
    po.setContractStyle(form.getContractStyle());
    po.setGivenDate(new Date(request.getParameter("givenDate")));
    po.setBeginDate(new Date(request.getParameter("beginDate")));
    po.setEndDate(new Date(request.getParameter("endDate")));
    po.setContractNO(form.getContractNO());
    po.setContractType(form.getContractType());
    if ("0".equals(form.getContractType()))
      po.setContractLimit(form.getContractLimit()); 
    return (new EmployeeContractBD()).save(po, form.getEmpID());
  }
}
