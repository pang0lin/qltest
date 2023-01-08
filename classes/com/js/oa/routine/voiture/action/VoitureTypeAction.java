package com.js.oa.routine.voiture.action;

import com.js.oa.routine.voiture.po.VoitureTypePO;
import com.js.oa.routine.voiture.service.VoitureManagerService;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.Page;
import com.js.util.util.FillBean;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class VoitureTypeAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    String tag = "view";
    String flag = (httpServletRequest.getParameter("flag") == null) ? "view" : 
      httpServletRequest.getParameter("flag");
    if (flag.equals("view")) {
      tag = "view";
      view(httpServletRequest);
    } else if (flag.equals("add")) {
      tag = "add";
    } else if (flag.equals("modify")) {
      load(httpServletRequest, actionForm);
      tag = "modi";
    } else if (flag.equals("update")) {
      updateType(httpServletRequest, actionForm);
      httpServletRequest.setAttribute("shouldClose", "1");
      tag = "modi";
    } else if (flag.equals("close") || flag.equals("continue")) {
      tag = "save";
      saveType(httpServletRequest, actionForm);
      view(httpServletRequest);
      if (flag.equals("close"))
        httpServletRequest.setAttribute("shouldClose", "1"); 
    } else if (flag.equals("del")) {
      tag = "view";
      delType(httpServletRequest, actionForm);
    } 
    return actionMapping.findForward(tag);
  }
  
  public void saveType(HttpServletRequest httpServletRequest, ActionForm actionForm) {
    VoitureManagerService vmbd = new VoitureManagerService();
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    String orgString = httpSession.getAttribute("orgIdString").toString();
    VoitureTypeActionForm voitureTypeActionForm = 
      (VoitureTypeActionForm)actionForm;
    voitureTypeActionForm.setCreatedEmp(Long.valueOf(userId));
    voitureTypeActionForm.setCreatedOrg(Long.valueOf(orgId));
    VoitureTypePO vtpo = (VoitureTypePO)FillBean.transformOneToOne(
        voitureTypeActionForm, VoitureTypePO.class);
    vtpo.setDomainId(Long.valueOf(domainId));
    int result = vmbd.saveVoitureType(vtpo).intValue();
    if (result > 0) {
      httpServletRequest.setAttribute("opResult", String.valueOf(result));
      voitureTypeActionForm.setName("");
    } else {
      voitureTypeActionForm.setName("");
    } 
  }
  
  public void listType(HttpServletRequest httpServletRequest, ActionForm actionForm) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    VoitureManagerService vmbd = new VoitureManagerService();
    List list = vmbd.listVoitureType(domainId);
    httpServletRequest.setAttribute("list", list);
  }
  
  public void delType(HttpServletRequest httpServletRequest, ActionForm actionForm) {
    VoitureManagerService vmbd = new VoitureManagerService();
    String delId = (httpServletRequest.getParameter("delId") == null) ? "" : 
      httpServletRequest.getParameter("delId").toString();
    boolean bl = vmbd.delVoitureType(delId);
    System.out.println("action:" + bl);
    System.out.println("action2:" + String.valueOf(bl));
    httpServletRequest.setAttribute("bl", String.valueOf(bl));
    view(httpServletRequest);
  }
  
  public void load(HttpServletRequest httpServletRequest, ActionForm actionForm) {
    VoitureManagerService vmbd = new VoitureManagerService();
    String typeId = httpServletRequest.getParameter("typeId");
    VoitureTypeActionForm voitureTypeActionForm = 
      (VoitureTypeActionForm)actionForm;
    VoitureTypePO vtpo = vmbd.loadVoitureType(typeId);
    voitureTypeActionForm.setName(vtpo.getName());
    voitureTypeActionForm.setId(vtpo.getId());
    voitureTypeActionForm.setCreatedEmp(vtpo.getCreatedEmp());
    voitureTypeActionForm.setCreatedOrg(vtpo.getCreatedOrg());
  }
  
  public void updateType(HttpServletRequest httpServletRequest, ActionForm actionForm) {
    VoitureManagerService vmbd = new VoitureManagerService();
    VoitureTypeActionForm voitureTypeActionForm = 
      (VoitureTypeActionForm)actionForm;
    String typeId = voitureTypeActionForm.getId().toString();
    VoitureTypePO vtpo = new VoitureTypePO();
    vtpo.setId(voitureTypeActionForm.getId());
    vtpo.setCreatedEmp(voitureTypeActionForm.getCreatedEmp());
    vtpo.setCreatedOrg(voitureTypeActionForm.getCreatedOrg());
    vtpo.setName(voitureTypeActionForm.getName());
    int result = vmbd.updateVoitureType(vtpo, typeId).intValue();
    if (result > 0) {
      httpServletRequest.setAttribute("opResult", String.valueOf(result));
      voitureTypeActionForm.setName("");
    } else {
      voitureTypeActionForm.setName("");
    } 
  }
  
  private void view(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    String viewSql = "po.id,po.name";
    String fromSql = "com.js.oa.routine.voiture.po.VoitureTypePO po";
    ManagerService mbd = new ManagerService();
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId").toString();
    String curOrgId = httpServletRequest.getSession(true).getAttribute(
        "orgId").toString();
    String orgIdString = httpServletRequest.getSession(true).getAttribute(
        "orgIdString").toString();
    String whereSql = "where " + 
      mbd.getRightFinalWhere(curUserId, curOrgId, orgIdString, 
        "车辆管理", "维护", "po.createdOrg", 
        "po.createdEmp");
    whereSql = String.valueOf(whereSql) + " and po.domainId=" + domainId + " order by po.id desc";
    boolean voitureMtRight = mbd.hasRightTypeName(curUserId, "车辆管理", "维护");
    httpServletRequest.setAttribute("voitureMtRight", 
        String.valueOf(voitureMtRight));
    boolean voitureAddRight = mbd.hasRightTypeName(curUserId, "车辆管理", "维护");
    httpServletRequest.setAttribute("voitureAddRight", 
        String.valueOf(voitureAddRight));
    list(httpServletRequest, viewSql, fromSql, whereSql);
  }
  
  private void list(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
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
    httpServletRequest.setAttribute("list", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "");
  }
}
