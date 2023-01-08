package com.js.oa.hr.personnelmanager.action;

import com.js.oa.hr.personnelmanager.service.EmployeeGnwgxBD;
import com.js.system.vo.usermanager.GnwgxVO;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EmployeeGnwgxAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    EmployeeGnwgxActionForm employeeGnwgxActionForm = (EmployeeGnwgxActionForm)actionForm;
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
      employeeGnwgxActionForm.setEmpID(new Long(empID));
    } else if (action.equals("close") || action.equals("continue")) {
      tag = "add";
      if (save(employeeGnwgxActionForm, httpServletRequest)) {
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
          (httpServletRequest.getParameter("gnwgxId") == null) ? "0" : 
          httpServletRequest.getParameter("gnwgxId"));
      GnwgxVO vo = (new EmployeeGnwgxBD()).load(id);
      employeeGnwgxActionForm.setGx(vo.getGx());
      employeeGnwgxActionForm.setXm(vo.getXm());
      employeeGnwgxActionForm.setZzmm(vo.getZzmm());
      employeeGnwgxActionForm.setGzdwjbm(vo.getGzdwjbm());
      employeeGnwgxActionForm.setZw(vo.getZw());
      employeeGnwgxActionForm.setBz(vo.getBz());
      employeeGnwgxActionForm.setId(id);
      employeeGnwgxActionForm.setEmpID(new Long(vo.getEmployeeVO()
            .getEmpId()));
      httpServletRequest.setAttribute("csny", vo.getCsny());
    } else if (action.equals("update")) {
      tag = "modify";
      GnwgxVO vo = new GnwgxVO();
      vo.setGx(employeeGnwgxActionForm.getGx());
      vo.setXm(employeeGnwgxActionForm.getXm());
      vo.setCsny(employeeGnwgxActionForm.getCsny());
      vo.setZzmm(employeeGnwgxActionForm.getZzmm());
      vo.setGzdwjbm(employeeGnwgxActionForm.getGzdwjbm());
      vo.setZw(employeeGnwgxActionForm.getZw());
      vo.setBz(employeeGnwgxActionForm.getBz());
      if ((new EmployeeGnwgxBD()).update(vo, 
          employeeGnwgxActionForm.getEmpID(), 
          employeeGnwgxActionForm.getId())) {
        httpServletRequest.setAttribute("close", "1");
      } else {
        httpServletRequest.setAttribute("close", "2");
      } 
    } else if (action.equals("delete")) {
      tag = "list";
      if (httpServletRequest.getParameter("gnwgxId") != null && 
        !"0".equals(httpServletRequest.getParameter("gnwgxId")))
        (new EmployeeGnwgxBD()).delete(new Long(httpServletRequest
              .getParameter("gnwgxId"))); 
      list(httpServletRequest);
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest request) {
    String userId = request.getSession().getAttribute("userId").toString();
    String empID = request.getParameter("empID");
    if (empID == null || "".equals(empID) || "null".equals(empID))
      empID = userId; 
    String viewSQL = "po.id,po.gx,po.xm,po.csny,po.zzmm,po.gzdwjbm,po.zw,po.bz";
    String fromSQL = "com.js.system.vo.usermanager.GnwgxVO po join po.employeeVO ppo";
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
    request.setAttribute("gnwgxList", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "");
  }
  
  private boolean save(EmployeeGnwgxActionForm form, HttpServletRequest request) {
    boolean retflag = false;
    GnwgxVO vo = new GnwgxVO();
    vo.setGx(form.getGx());
    vo.setXm(form.getXm());
    vo.setCsny(form.getCsny());
    vo.setZzmm(form.getZzmm());
    vo.setGzdwjbm(form.getGzdwjbm());
    vo.setZw(form.getZw());
    vo.setBz(form.getBz());
    retflag = (new EmployeeGnwgxBD()).save(vo, form.getEmpID());
    return retflag;
  }
}
