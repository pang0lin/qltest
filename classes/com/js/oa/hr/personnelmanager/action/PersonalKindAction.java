package com.js.oa.hr.personnelmanager.action;

import com.js.oa.hr.personnelmanager.po.PersonalKindPO;
import com.js.oa.hr.personnelmanager.service.PersonalKindBD;
import com.js.util.page.simple.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PersonalKindAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    String action = httpServletRequest.getParameter("action");
    PersonalKindActionForm personalKindActionForm = 
      (PersonalKindActionForm)actionForm;
    if ("add".equals(action)) {
      PersonalKindBD bd = new PersonalKindBD();
      if (bd.checkExistKind(null, personalKindActionForm.getKindName().trim())) {
        httpServletRequest.setAttribute("isExist", "true");
        return actionMapping.findForward(action);
      } 
      save(personalKindActionForm, httpServletRequest);
      httpServletRequest.setAttribute("saveType", httpServletRequest.getParameter("saveType"));
    } 
    if ("modify".equals(action)) {
      PersonalKindBD bd = new PersonalKindBD();
      if (bd.checkExistKind(personalKindActionForm.getKindId(), personalKindActionForm.getKindName().trim())) {
        httpServletRequest.setAttribute("isExist", "true");
        return actionMapping.findForward(action);
      } 
      update(personalKindActionForm, httpServletRequest);
      httpServletRequest.setAttribute("saveType", httpServletRequest.getParameter("saveType"));
    } 
    if ("load".equals(action)) {
      load(personalKindActionForm, httpServletRequest);
      httpServletRequest.setAttribute("action", action);
      action = "modify";
    } 
    if ("delete".equals(action)) {
      delete(httpServletRequest);
      action = "list";
    } 
    if ("view".equals(action)) {
      load(personalKindActionForm, httpServletRequest);
      httpServletRequest.setAttribute("action", action);
      action = "modify";
    } 
    if ("list".equals(action))
      list(httpServletRequest); 
    return actionMapping.findForward(action);
  }
  
  private void list(HttpServletRequest httpServletRequest) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page("po.kindId, po.kindName, po.kindDescription, po.kindSort", "com.js.oa.hr.personnelmanager.po.PersonalKindPO po", "order by po.kindSort");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    if (list.size() == 0 && offset >= 15) {
      offset -= 15;
      currentPage = offset / pageSize + 1;
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      httpServletRequest.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
      httpServletRequest.setAttribute("pager.realCurrent", (
          new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("personalKindList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  private boolean save(PersonalKindActionForm actionForm, HttpServletRequest httpServletRequest) {
    boolean ret = false;
    PersonalKindPO po = new PersonalKindPO();
    po.setKindName(actionForm.getKindName().trim());
    po.setKindDescription(actionForm.getKindDescription());
    po.setKindSort(actionForm.getKindSort());
    ret = (new PersonalKindBD()).save(po);
    return ret;
  }
  
  private boolean update(PersonalKindActionForm actionForm, HttpServletRequest httpServletRequest) {
    boolean ret = false;
    PersonalKindPO po = new PersonalKindPO();
    po.setKindName(actionForm.getKindName().trim());
    po.setKindDescription(actionForm.getKindDescription());
    po.setKindSort(actionForm.getKindSort());
    ret = (new PersonalKindBD()).update(po, actionForm.getKindId());
    return ret;
  }
  
  private void load(PersonalKindActionForm actionForm, HttpServletRequest httpServletRequest) {
    Long id = new Long(httpServletRequest.getParameter("kindId"));
    PersonalKindPO po = (new PersonalKindBD()).load(id);
    actionForm.setKindId(po.getKindId());
    actionForm.setKindName(po.getKindName());
    actionForm.setKindDescription(po.getKindDescription());
    actionForm.setKindSort(po.getKindSort());
  }
  
  private void delete(HttpServletRequest httpServletRequest) {
    httpServletRequest.setAttribute("deleteSuccess", 
        new Boolean((new PersonalKindBD()).delete(
            httpServletRequest.getParameter("kindId"))));
  }
}
