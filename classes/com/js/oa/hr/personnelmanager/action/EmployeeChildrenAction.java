package com.js.oa.hr.personnelmanager.action;

import com.js.oa.hr.personnelmanager.service.EmployeeChildrenBD;
import com.js.system.vo.usermanager.ChildrenVO;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EmployeeChildrenAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    EmployeeChildrenActionForm employeeChildrenActionForm = (EmployeeChildrenActionForm)actionForm;
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
      employeeChildrenActionForm.setEmpID(new Long(empID));
    } else if (action.equals("close") || action.equals("continue")) {
      tag = "add";
      if (save(employeeChildrenActionForm, httpServletRequest)) {
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
          (httpServletRequest.getParameter("childrenId") == null) ? "0" : 
          httpServletRequest.getParameter("childrenId"));
      ChildrenVO vo = (new EmployeeChildrenBD()).load(id);
      employeeChildrenActionForm.setGx(vo.getGx());
      employeeChildrenActionForm.setXm(vo.getXm());
      employeeChildrenActionForm.setZzmm(vo.getZzmm());
      employeeChildrenActionForm.setGzdwjbm(vo.getGzdwjbm());
      employeeChildrenActionForm.setZw(vo.getZw());
      employeeChildrenActionForm.setBz(vo.getBz());
      employeeChildrenActionForm.setId(id);
      employeeChildrenActionForm.setSfzhm(vo.getSfzhm());
      employeeChildrenActionForm.setEmpID(new Long(vo.getEmployeeVO()
            .getEmpId()));
      httpServletRequest.setAttribute("csny", vo.getCsny());
    } else if (action.equals("update")) {
      tag = "modify";
      ChildrenVO vo = new ChildrenVO();
      vo.setGx(employeeChildrenActionForm.getGx());
      vo.setXm(employeeChildrenActionForm.getXm());
      vo.setCsny(employeeChildrenActionForm.getCsny());
      vo.setZzmm(employeeChildrenActionForm.getZzmm());
      vo.setGzdwjbm(employeeChildrenActionForm.getGzdwjbm());
      vo.setZw(employeeChildrenActionForm.getZw());
      vo.setBz(employeeChildrenActionForm.getBz());
      vo.setSfzhm(employeeChildrenActionForm.getSfzhm());
      if ((new EmployeeChildrenBD()).update(vo, 
          employeeChildrenActionForm.getEmpID(), 
          employeeChildrenActionForm.getId())) {
        httpServletRequest.setAttribute("close", "1");
      } else {
        httpServletRequest.setAttribute("close", "2");
      } 
    } else if (action.equals("delete")) {
      tag = "list";
      if (httpServletRequest.getParameter("childrenId") != null && 
        !"0".equals(httpServletRequest.getParameter("childrenId")))
        (new EmployeeChildrenBD()).delete(new Long(httpServletRequest
              .getParameter("childrenId"))); 
      list(httpServletRequest);
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest request) {
    String userId = request.getSession().getAttribute("userId").toString();
    String empID = request.getParameter("empID");
    if (empID == null || "".equals(empID) || "null".equals(empID))
      empID = userId; 
    String viewSQL = "po.id,po.gx,po.xm,po.csny,po.zzmm,po.gzdwjbm,po.zw,po.bz,po.sfzhm";
    String fromSQL = "com.js.system.vo.usermanager.ChildrenVO po join po.employeeVO ppo";
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
    request.setAttribute("childrenList", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "");
  }
  
  private boolean save(EmployeeChildrenActionForm form, HttpServletRequest request) {
    boolean retflag = false;
    ChildrenVO vo = new ChildrenVO();
    vo.setGx(form.getGx());
    vo.setXm(form.getXm());
    vo.setCsny(form.getCsny());
    vo.setZzmm(form.getZzmm());
    vo.setGzdwjbm(form.getGzdwjbm());
    vo.setZw(form.getZw());
    vo.setBz(form.getBz());
    vo.setSfzhm(form.getSfzhm());
    retflag = (new EmployeeChildrenBD()).save(vo, form.getEmpID());
    return retflag;
  }
}
