package com.js.oa.hr.personnelmanager.action;

import com.js.oa.hr.personnelmanager.service.EmployeePxjlBD;
import com.js.system.vo.usermanager.PxjlVO;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EmployeePxjlAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    EmployeePxjlActionForm employeePxjlActionForm = (EmployeePxjlActionForm)actionForm;
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
      employeePxjlActionForm.setEmpID(new Long(empID));
    } else if (action.equals("close") || action.equals("continue")) {
      tag = "add";
      if (save(employeePxjlActionForm, httpServletRequest)) {
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
          (httpServletRequest.getParameter("pxjlId") == null) ? "0" : 
          httpServletRequest.getParameter("pxjlId"));
      PxjlVO vo = (new EmployeePxjlBD()).load(id);
      employeePxjlActionForm.setKssj(vo.getKssj());
      employeePxjlActionForm.setJssj(vo.getJssj());
      employeePxjlActionForm.setZxs(vo.getZxs());
      employeePxjlActionForm.setPxxz(vo.getPxxz());
      employeePxjlActionForm.setCjpxxm(vo.getCjpxxm());
      employeePxjlActionForm.setHdzs(vo.getHdzs());
      employeePxjlActionForm.setPxdd(vo.getPxdd());
      employeePxjlActionForm.setId(id);
      employeePxjlActionForm.setEmpID(new Long(vo.getEmployeeVO()
            .getEmpId()));
      httpServletRequest.setAttribute("kssj", vo.getKssj().split("/"));
      httpServletRequest.setAttribute("jssj", vo.getJssj().split("/"));
    } else if (action.equals("update")) {
      tag = "modify";
      PxjlVO vo = new PxjlVO();
      vo.setKssj(employeePxjlActionForm.getKssj());
      vo.setJssj(employeePxjlActionForm.getJssj());
      vo.setZxs(employeePxjlActionForm.getZxs());
      vo.setPxxz(employeePxjlActionForm.getPxxz());
      vo.setCjpxxm(employeePxjlActionForm.getCjpxxm());
      vo.setHdzs(employeePxjlActionForm.getHdzs());
      vo.setPxdd(employeePxjlActionForm.getPxdd());
      if ((new EmployeePxjlBD()).update(vo, 
          employeePxjlActionForm.getEmpID(), 
          employeePxjlActionForm.getId())) {
        httpServletRequest.setAttribute("close", "1");
      } else {
        httpServletRequest.setAttribute("close", "2");
      } 
    } else if (action.equals("delete")) {
      tag = "list";
      if (httpServletRequest.getParameter("pxjlId") != null && 
        !"0".equals(httpServletRequest.getParameter("pxjlId")))
        (new EmployeePxjlBD()).delete(new Long(httpServletRequest
              .getParameter("pxjlId"))); 
      list(httpServletRequest);
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest request) {
    String userId = request.getSession().getAttribute("userId").toString();
    String empID = request.getParameter("empID");
    if (empID == null || "".equals(empID) || "null".equals(empID))
      empID = userId; 
    String viewSQL = "po.id,po.kssj,po.jssj,po.zxs,po.pxxz,po.cjpxxm,po.hdzs,po.pxdd";
    String fromSQL = "com.js.system.vo.usermanager.PxjlVO po join po.employeeVO ppo";
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
    request.setAttribute("pxjlList", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "");
  }
  
  private boolean save(EmployeePxjlActionForm form, HttpServletRequest request) {
    boolean retflag = false;
    PxjlVO vo = new PxjlVO();
    vo.setKssj(form.getKssj());
    vo.setJssj(form.getJssj());
    vo.setZxs(form.getZxs());
    vo.setPxxz(form.getPxxz());
    vo.setCjpxxm(form.getCjpxxm());
    vo.setHdzs(form.getHdzs());
    vo.setPxdd(form.getPxdd());
    retflag = (new EmployeePxjlBD()).save(vo, form.getEmpID());
    return retflag;
  }
}
