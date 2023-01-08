package com.js.oa.hr.personnelmanager.action;

import com.js.oa.hr.personnelmanager.service.EmployeeQtqsqkBD;
import com.js.system.vo.usermanager.QtqsqkVO;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EmployeeQtqsqkAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    EmployeeQtqsqkActionForm employeeQtqsqkActionForm = (EmployeeQtqsqkActionForm)actionForm;
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
      employeeQtqsqkActionForm.setEmpID(new Long(empID));
    } else if (action.equals("close") || action.equals("continue")) {
      tag = "add";
      if (save(employeeQtqsqkActionForm, httpServletRequest)) {
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
          (httpServletRequest.getParameter("qtqsqkId") == null) ? "0" : 
          httpServletRequest.getParameter("qtqsqkId"));
      QtqsqkVO vo = (new EmployeeQtqsqkBD()).load(id);
      employeeQtqsqkActionForm.setGx(vo.getGx());
      employeeQtqsqkActionForm.setXm(vo.getXm());
      employeeQtqsqkActionForm.setZzmm(vo.getZzmm());
      employeeQtqsqkActionForm.setGzdwjbm(vo.getGzdwjbm());
      employeeQtqsqkActionForm.setZw(vo.getZw());
      employeeQtqsqkActionForm.setBz(vo.getBz());
      employeeQtqsqkActionForm.setRsrz(vo.getRsrz());
      employeeQtqsqkActionForm.setFtjrz(vo.getFtjrz());
      employeeQtqsqkActionForm.setId(id);
      employeeQtqsqkActionForm.setEmpID(new Long(vo.getEmployeeVO()
            .getEmpId()));
      httpServletRequest.setAttribute("csny", vo.getCsny());
    } else if (action.equals("update")) {
      tag = "modify";
      QtqsqkVO vo = new QtqsqkVO();
      vo.setGx(employeeQtqsqkActionForm.getGx());
      vo.setXm(employeeQtqsqkActionForm.getXm());
      vo.setCsny(employeeQtqsqkActionForm.getCsny());
      vo.setZzmm(employeeQtqsqkActionForm.getZzmm());
      vo.setGzdwjbm(employeeQtqsqkActionForm.getGzdwjbm());
      vo.setZw(employeeQtqsqkActionForm.getZw());
      vo.setBz(employeeQtqsqkActionForm.getBz());
      vo.setRsrz(employeeQtqsqkActionForm.getRsrz());
      vo.setFtjrz(employeeQtqsqkActionForm.getFtjrz());
      if ((new EmployeeQtqsqkBD()).update(vo, 
          employeeQtqsqkActionForm.getEmpID(), 
          employeeQtqsqkActionForm.getId())) {
        httpServletRequest.setAttribute("close", "1");
      } else {
        httpServletRequest.setAttribute("close", "2");
      } 
    } else if (action.equals("delete")) {
      tag = "list";
      if (httpServletRequest.getParameter("qtqsqkId") != null && 
        !"0".equals(httpServletRequest.getParameter("qtqsqkId")))
        (new EmployeeQtqsqkBD()).delete(new Long(httpServletRequest
              .getParameter("qtqsqkId"))); 
      list(httpServletRequest);
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest request) {
    String userId = request.getSession().getAttribute("userId").toString();
    String empID = request.getParameter("empID");
    if (empID == null || "".equals(empID) || "null".equals(empID))
      empID = userId; 
    String viewSQL = "po.id,po.gx,po.xm,po.csny,po.zzmm,po.gzdwjbm,po.zw,po.bz,po.rsrz,po.ftjrz";
    String fromSQL = "com.js.system.vo.usermanager.QtqsqkVO po join po.employeeVO ppo";
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
    request.setAttribute("qtqsqkList", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "");
  }
  
  private boolean save(EmployeeQtqsqkActionForm form, HttpServletRequest request) {
    boolean retflag = false;
    QtqsqkVO vo = new QtqsqkVO();
    vo.setGx(form.getGx());
    vo.setXm(form.getXm());
    vo.setCsny(form.getCsny());
    vo.setZzmm(form.getZzmm());
    vo.setGzdwjbm(form.getGzdwjbm());
    vo.setZw(form.getZw());
    vo.setBz(form.getBz());
    vo.setRsrz(form.getRsrz());
    vo.setFtjrz(form.getFtjrz());
    retflag = (new EmployeeQtqsqkBD()).save(vo, form.getEmpID());
    return retflag;
  }
}
