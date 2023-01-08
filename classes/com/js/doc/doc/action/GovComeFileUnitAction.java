package com.js.doc.doc.action;

import com.js.doc.doc.po.GovComeFileUnitPO;
import com.js.doc.doc.service.GovComeFileUnitBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GovComeFileUnitAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
    GovComeFileUnitActionForm GovComeFileUnitActionForm = (GovComeFileUnitActionForm)actionForm;
    String action = request.getParameter("action");
    GovComeFileUnitBD bd = new GovComeFileUnitBD();
    Object object1 = request.getSession(true).getAttribute("userId");
    Object object2 = request.getSession(true).getAttribute("orgId");
    Object object3 = (request.getSession(true).getAttribute("domainId") == null) ? "0" : request.getSession(true).getAttribute("domainId");
    if ("add".equals(action)) {
      GovComeFileUnitPO po = new GovComeFileUnitPO();
      po.setComeFileUnitName(GovComeFileUnitActionForm.getComeFileUnitName());
      po.setCreatedEmp(Long.valueOf((String)object1));
      po.setCreatedOrg(Long.valueOf((String)object2));
      po.setDomainId((String)object3);
      bd.add(po);
      GovComeFileUnitActionForm.reset(actionMapping, request);
      return actionMapping.findForward("add");
    } 
    if ("update".equals(action)) {
      bd.update(request.getParameter("editId"), GovComeFileUnitActionForm.getComeFileUnitName());
      return actionMapping.findForward("modi");
    } 
    if ("load".equals(action)) {
      GovComeFileUnitPO form = bd.load(request.getParameter("editId"));
      GovComeFileUnitActionForm.setComeFileUnitName(form.getComeFileUnitName());
      return actionMapping.findForward("modi");
    } 
    if ("delBatch".equals(action)) {
      bd.delBatch(request.getParameter("ids"));
      action = "list";
    } 
    if ("list".equals(action)) {
      ManagerService mbd = new ManagerService();
      String wherePara = "where " + mbd.getRightFinalWhere((String)object1, (String)object2, "03*06*01", "po.createdOrg", "po.createdEmp");
      list(request, wherePara);
    } 
    return actionMapping.findForward("list");
  }
  
  private void list(HttpServletRequest request, String wherePara) {
    Object object = (request.getSession(true).getAttribute("domainId") == null) ? "0" : request.getSession(true).getAttribute("domainId");
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    try {
      Page page = new Page(" distinct po.id, po.comeFileUnitName", 
          "com.js.doc.doc.po.GovComeFileUnitPO po ", 
          String.valueOf(wherePara) + " and po.domainId=" + object);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
