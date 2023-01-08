package com.js.oa.audit.action;

import com.js.oa.audit.po.AuditManager;
import com.js.oa.audit.service.AuditManagerBD;
import com.js.util.page.Page;
import com.js.util.util.FillBean;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AuditManagerAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws NumberFormatException, Exception {
    String action = request.getParameter("action");
    AuditManagerActionForm auditManagerActionForm = (AuditManagerActionForm)actionForm;
    AuditManagerBD auditMangaerBD = new AuditManagerBD();
    if ("list".equals(action)) {
      list(request);
      return actionMapping.findForward("list");
    } 
    if ("forAdd".equals(action)) {
      auditManagerActionForm.setRightScopeType(Integer.valueOf(2));
      return actionMapping.findForward("add");
    } 
    if ("add".equals(action)) {
      try {
        AuditManager auditManager = (AuditManager)FillBean.transformOTO(auditManagerActionForm, AuditManager.class);
        auditMangaerBD.addAuditManager(auditManager);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("add");
    } 
    if ("delete".equals(action)) {
      String id = request.getParameter("id");
      if (id != null && !"".equals(id))
        auditMangaerBD.delAuditManager(id); 
      list(request);
      return actionMapping.findForward("list");
    } 
    if ("update".equals(action)) {
      String id = request.getParameter("id");
      String done = request.getParameter("done");
      if ("done".equals(done)) {
        AuditManager auditManager = (AuditManager)FillBean.transformOTO(auditManagerActionForm, AuditManager.class);
        auditMangaerBD.updateAuditManager(auditManager);
      } else {
        AuditManager po = null;
        if (id != null && !"".equals(id)) {
          po = auditMangaerBD.loadAuditManger(id);
          auditManagerActionForm.setManagerId(po.getManagerId());
          auditManagerActionForm.setEmpId(po.getEmpId());
          auditManagerActionForm.setEmpName(po.getEmpName());
          auditManagerActionForm.setRightScopeType(po.getRightScopeType());
          auditManagerActionForm.setRightScopeScope(po.getRightScopeScope());
          auditManagerActionForm.setRightScope(po.getRightScope());
          auditManagerActionForm.setCreatedEmp(po.getCreatedEmp());
          auditManagerActionForm.setCreatedEmpName(po.getCreatedEmpName());
          auditManagerActionForm.setCreatedOrg(po.getCreatedOrg());
        } 
      } 
      return actionMapping.findForward("update");
    } 
    return actionMapping.findForward("list");
  }
  
  public void list(HttpServletRequest request) {
    String wherePara = "";
    String empName = request.getParameter("empName");
    String createdEmpName = request.getParameter("createdEmpName");
    String createdOrgName = request.getParameter("createdOrgName");
    if (empName != null && !"".equals(empName))
      wherePara = String.valueOf(wherePara) + " and adt.empName like '%" + empName + "%' "; 
    if (createdEmpName != null && !"".equals(createdEmpName))
      wherePara = String.valueOf(wherePara) + " and adt.createdEmpName like '%" + createdEmpName + "%' "; 
    int pageSize = 15;
    int offset = 0;
    String s = request.getParameter("pager.offset");
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String where = " where 1=1 and adt.managerId!=-99";
    where = String.valueOf(where) + wherePara;
    Page page = new Page(" adt.managerId,adt.empName,adt.rightScope,adt.createdEmpName", 
        " com.js.oa.audit.po.AuditManager adt", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    request.setAttribute("empName", empName);
    request.setAttribute("createdEmpName", createdEmpName);
    request.setAttribute("createdOrgName", createdOrgName);
    request.setAttribute("mylist", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,empName,createdEmpName");
  }
}
