package com.js.oa.routine.resource.action;

import com.js.oa.routine.resource.po.TypeDefinePO;
import com.js.oa.routine.resource.service.TypeDefineBD;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class TypeDefineAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    TypeDefineActionForm typeDefineActionForm = 
      (TypeDefineActionForm)actionForm;
    String domainId = "0";
    HttpSession httpSession = httpServletRequest.getSession(true);
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    TypeDefineBD bd = new TypeDefineBD();
    String action = httpServletRequest.getParameter("action");
    if (action.equals("add"))
      return actionMapping.findForward("add"); 
    if (action.equals("modify") || action.equals("view")) {
      Long id = new Long(httpServletRequest.getParameter("id"));
      TypeDefinePO po = bd.load(id);
      typeDefineActionForm.setId(po.getId());
      typeDefineActionForm.setTypeDefineMode(po.getTypeDefineMode());
      typeDefineActionForm.setTypeDefineName(po.getTypeDefineName());
      httpServletRequest.setAttribute("typeDefineMode", po.getTypeDefineMode());
      if (action.equals("view"))
        return actionMapping.findForward("view"); 
      return actionMapping.findForward("modify");
    } 
    if (action.equals("save")) {
      TypeDefinePO po = new TypeDefinePO();
      po.setTypeDefineMode(typeDefineActionForm.getTypeDefineMode());
      po.setTypeDefineName(typeDefineActionForm.getTypeDefineName());
      po.setDomainID(new Long(domainId));
      String result = bd.save(po);
      httpServletRequest.setAttribute("message", result);
      return actionMapping.findForward("add");
    } 
    if (action.equals("update")) {
      TypeDefinePO po = new TypeDefinePO();
      po.setTypeDefineMode(typeDefineActionForm.getTypeDefineMode());
      po.setTypeDefineName(typeDefineActionForm.getTypeDefineName());
      if (bd.update(po, typeDefineActionForm.getId()).booleanValue()) {
        httpServletRequest.setAttribute("message", "true");
      } else {
        httpServletRequest.setAttribute("message", "false");
      } 
      return actionMapping.findForward("modify");
    } 
    if (action.equals("delete")) {
      if (httpServletRequest.getParameter("id") != null && 
        !"".equals(httpServletRequest.getParameter("id"))) {
        Long id = new Long(httpServletRequest.getParameter("id"));
        bd.delete(id);
      } 
      action = "list";
    } 
    if (action.equals("list"))
      typeDefineList(httpServletRequest); 
    return actionMapping.findForward("list");
  }
  
  private void typeDefineList(HttpServletRequest httpServletRequest) {
    String domainId = "0";
    HttpSession httpSession = httpServletRequest.getSession(true);
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "po.id,po.typeDefineName,po.typeDefineMode", 
        "com.js.oa.routine.resource.po.TypeDefinePO po", 
        " where po.domainID=" + domainId + 
        " order by po.typeDefineMode ");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("typeDefineList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
}
