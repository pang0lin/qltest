package com.js.doc.doc.action;

import com.js.doc.doc.po.GovTopicWordPO;
import com.js.doc.doc.service.GovTopicWordBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GovTopicWordAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
    GovTopicWordActionForm govTopicWordActionForm = (GovTopicWordActionForm)actionForm;
    String action = request.getParameter("action");
    GovTopicWordBD bd = new GovTopicWordBD();
    Object object1 = request.getSession(true).getAttribute("userId");
    Object object2 = request.getSession(true).getAttribute("orgId");
    String domainId = (request.getSession(true).getAttribute("domainId") == null) ? "0" : request.getSession(true).getAttribute("domainId").toString();
    if ("add".equals(action)) {
      GovTopicWordPO po = new GovTopicWordPO();
      po.setTopicWordName(govTopicWordActionForm.getTopicWordName());
      po.setCreatedEmp(Long.valueOf((String)object1));
      po.setCreatedOrg(Long.valueOf((String)object2));
      po.setDomainId(domainId);
      bd.add(po);
      govTopicWordActionForm.reset(actionMapping, request);
      return actionMapping.findForward("add");
    } 
    if ("update".equals(action)) {
      GovTopicWordPO po = new GovTopicWordPO();
      po.setTopicWordName(govTopicWordActionForm.getTopicWordName());
      bd.update(request.getParameter("editId"), po);
      return actionMapping.findForward("modi");
    } 
    if ("select".equals(action)) {
      String wherePara = "";
      wherePara = String.valueOf(wherePara) + " where po.domainId=" + domainId;
      select(request, wherePara);
      return actionMapping.findForward("select");
    } 
    if ("load".equals(action)) {
      GovTopicWordPO form = bd.load(request.getParameter("editId"));
      govTopicWordActionForm.setTopicWordName(form.getTopicWordName());
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
      Page page = new Page(" distinct po.id, po.topicWordName", 
          "com.js.doc.doc.po.GovTopicWordPO po ", 
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
  
  private void select(HttpServletRequest request, String wherePara) {
    int pageSize = 45;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    try {
      Page page = new Page(" distinct po.id, po.topicWordName", 
          "com.js.doc.doc.po.GovTopicWordPO po ", 
          wherePara);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("mylist", list);
      request.setAttribute("listSize", (list == null) ? "0" : (new StringBuilder(String.valueOf(list.size()))).toString());
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
