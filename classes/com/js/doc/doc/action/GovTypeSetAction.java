package com.js.doc.doc.action;

import com.js.doc.doc.po.GovTypeSetPO;
import com.js.doc.doc.service.GovTypeSetBD;
import com.js.doc.doc.service.SendFileBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.Page;
import com.js.util.util.StringSplit;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GovTypeSetAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
    GovTypeSetActionForm govTypeSetActionForm = (GovTypeSetActionForm)actionForm;
    String action = request.getParameter("action");
    GovTypeSetBD bd = new GovTypeSetBD();
    Object object1 = request.getSession(true).getAttribute("userId");
    Object object2 = request.getSession(true).getAttribute("orgId");
    String domainId = (request.getSession(true).getAttribute("domainId") == null) ? "0" : request.getSession(true).getAttribute("domainId").toString();
    if ("see".equals(action)) {
      getRedHeadList(request);
      return actionMapping.findForward("see");
    } 
    if ("add".equals(action)) {
      GovTypeSetPO po = new GovTypeSetPO();
      po.setCreatedEmp(Long.parseLong((String)object1));
      po.setCreatedOrg(Long.parseLong((String)object2));
      String userOrgGroup = govTypeSetActionForm.getSendToId();
      po.setSendToUser(StringSplit.splitWith(userOrgGroup, "$", "*@"));
      po.setSendToOrg(StringSplit.splitWith(userOrgGroup, "*", "@$"));
      po.setSendToGroup(StringSplit.splitWith(userOrgGroup, "@", "$*"));
      po.setSendToName(govTypeSetActionForm.getSendToName());
      po.setTypeSetName(govTypeSetActionForm.getTypeSetName());
      po.setTypeSetWordNumber(govTypeSetActionForm.getTypeSetWordNumber());
      po.setRedHeadId(govTypeSetActionForm.getRedHeadId());
      po.setDomainId(domainId);
      bd.add(po);
      getRedHeadList(request);
      govTypeSetActionForm.reset(actionMapping, request);
      return actionMapping.findForward("add");
    } 
    if ("update".equals(action)) {
      getRedHeadList(request);
      GovTypeSetPO lpo = new GovTypeSetPO();
      lpo.setTypeSetName(govTypeSetActionForm.getTypeSetName());
      lpo.setTypeSetWordNumber(govTypeSetActionForm.getTypeSetWordNumber());
      String userOrgGroup = govTypeSetActionForm.getSendToId();
      lpo.setSendToUser(StringSplit.splitWith(userOrgGroup, "$", "*@"));
      lpo.setSendToOrg(StringSplit.splitWith(userOrgGroup, "*", "@$"));
      lpo.setSendToGroup(StringSplit.splitWith(userOrgGroup, "@", "$*"));
      lpo.setSendToName(govTypeSetActionForm.getSendToName());
      lpo.setRedHeadId(govTypeSetActionForm.getRedHeadId());
      bd.update(request.getParameter("editId"), lpo);
      return actionMapping.findForward("modi");
    } 
    if ("load".equals(action)) {
      String editId = request.getParameter("editId");
      GovTypeSetPO form = bd.load(editId);
      getRedHeadList(request);
      govTypeSetActionForm.setTypeSetName(form.getTypeSetName());
      govTypeSetActionForm.setTypeSetWordNumber(form.getTypeSetWordNumber());
      govTypeSetActionForm.setSendToName(form.getSendToName());
      govTypeSetActionForm.setSendToId(String.valueOf(form.getSendToGroup()) + form.getSendToOrg() + form.getSendToUser());
      return actionMapping.findForward("modi");
    } 
    if ("delBatch".equals(action)) {
      bd.delBatch(request.getParameter("ids"));
      action = "list";
    } 
    if ("list".equals(action)) {
      ManagerService mbd = new ManagerService();
      request.setAttribute("hasRight", Boolean.valueOf(mbd.hasRight((String)object1, "03*03*01")));
      String wherePara = mbd.getRightFinalWhere((String)object1, (String)object2, "03*03*01", "po.createdOrg", "po.createdEmp");
      wherePara = String.valueOf(wherePara) + " and po.domainId=" + domainId;
      list(request, "where " + wherePara);
    } 
    return actionMapping.findForward("list");
  }
  
  private void list(HttpServletRequest request, String wherePara) {
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    try {
      Page page = new Page(" distinct po.id, po.typeSetName,po.typeSetWordNumber", 
          "com.js.doc.doc.po.GovTypeSetPO po ", 
          wherePara);
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
  
  private void getRedHeadList(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    String wherePara = (new ManagerService()).getScopeWhere(httpSession.getAttribute("userId").toString(), 
        httpSession.getAttribute("orgId").toString(), 
        httpSession.getAttribute("orgIdString").toString(), 
        "po.headUser", "po.headUseOrg", 
        "po.headUseGroup", 
        "po.createdEmp");
    wherePara = String.valueOf(wherePara) + " and po.domainId=" + domainId;
    httpServletRequest.setAttribute("redheadlist", (new SendFileBD()).getRedHeadList(wherePara));
  }
}
