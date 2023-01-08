package com.js.oa.hr.personnelmanager.action;

import com.js.oa.hr.personnelmanager.service.EmployeeBankCardBD;
import com.js.system.vo.usermanager.BankCardVO;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EmployeeBankCardAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    EmployeeBankCardActionForm employeeBankCardActionForm = (EmployeeBankCardActionForm)actionForm;
    if (employeeBankCardActionForm == null)
      employeeBankCardActionForm = new EmployeeBankCardActionForm(); 
    String action = (httpServletRequest.getParameter("action") == null) ? "list" : 
      httpServletRequest.getParameter("action");
    String tag = "list";
    String userId = httpServletRequest.getSession().getAttribute("userId")
      .toString();
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
      employeeBankCardActionForm.setEmpID(new Long(empID));
    } else if (action.equals("close") || action.equals("continue")) {
      tag = "add";
      if (save(employeeBankCardActionForm, httpServletRequest)) {
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
      Long id = new Long(
          (httpServletRequest.getParameter("bankCardId") == null) ? "0" : 
          httpServletRequest.getParameter("bankCardId"));
      BankCardVO vo = (new EmployeeBankCardBD()).load(id);
      employeeBankCardActionForm.setBankCardName(vo.getBankCardName());
      employeeBankCardActionForm.setBankCardNO(vo.getBankCardNO());
      employeeBankCardActionForm.setId(id);
      employeeBankCardActionForm.setEmpID(new Long(vo.getEmployeeVO()
            .getEmpId()));
    } else if (action.equals("update")) {
      tag = "modify";
      BankCardVO vo = new BankCardVO();
      vo.setBankCardName(employeeBankCardActionForm.getBankCardName());
      vo.setBankCardNO(employeeBankCardActionForm.getBankCardNO());
      if ((new EmployeeBankCardBD()).update(vo, 
          employeeBankCardActionForm.getEmpID(), 
          employeeBankCardActionForm.getId())) {
        httpServletRequest.setAttribute("close", "1");
      } else {
        httpServletRequest.setAttribute("close", "2");
      } 
    } else if (action.equals("delete")) {
      tag = "list";
      if (httpServletRequest.getParameter("bankCardId") != null && 
        !"0".equals(httpServletRequest.getParameter("bankCardId")))
        (new EmployeeBankCardBD()).delete(new Long(httpServletRequest
              .getParameter("bankCardId"))); 
      list(httpServletRequest);
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest request) {
    String userId = request.getSession().getAttribute("userId").toString();
    String empID = request.getParameter("empID");
    if (empID == null || "".equals(empID) || "null".equals(empID))
      empID = userId; 
    String viewSQL = "po.id,po.bankCardName,po.bankCardNO";
    String fromSQL = "com.js.system.vo.usermanager.BankCardVO po join po.employeeVO ppo";
    String whereSQL = "where ppo.empId=" + new Long(empID) + 
      " order by po.id desc ";
    int pageSize = 999999;
    int offset = 0;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    int currentPage = offset / pageSize + 1;
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    request.setAttribute("bankCardList", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "");
  }
  
  private boolean save(EmployeeBankCardActionForm form, HttpServletRequest request) {
    boolean retflag = false;
    BankCardVO vo = new BankCardVO();
    vo.setBankCardName(form.getBankCardName());
    vo.setBankCardNO(form.getBankCardNO());
    retflag = (new EmployeeBankCardBD()).save(vo, form.getEmpID());
    return retflag;
  }
}
