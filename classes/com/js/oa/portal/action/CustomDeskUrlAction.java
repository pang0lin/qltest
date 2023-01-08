package com.js.oa.portal.action;

import com.js.oa.portal.po.CustomDefaultPO;
import com.js.oa.portal.po.CustomurlPO;
import com.js.oa.portal.service.CustomDesktopBD;
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

public class CustomDeskUrlAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
    CustomDeskUrlActionForm customDeskUrlActionForm = (CustomDeskUrlActionForm)actionForm;
    String action = request.getParameter("action");
    HttpSession httpSession = request.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String userName = httpSession.getAttribute("userName").toString();
    CustomDesktopBD customDesktopBD = new CustomDesktopBD();
    if ("list".equals(action)) {
      customDeskUrlList(request);
    } else {
      if ("save".equals(action)) {
        CustomurlPO po = new CustomurlPO();
        po.setUrlname(customDeskUrlActionForm.getUrlname());
        po.setUrlapp(customDeskUrlActionForm.getUrlapp());
        customDesktopBD.saveCustomDeskUrl(po);
        return actionMapping.findForward("add");
      } 
      if (action.equals("modify") || action.equals("view")) {
        Long id = new Long(request.getParameter("id"));
        CustomurlPO po = customDesktopBD.loadCustomDeskUrl(id);
        customDeskUrlActionForm.setUrlapp(po.getUrlapp());
        customDeskUrlActionForm.setUrlname(po.getUrlname());
        if (action.equals("view"))
          return actionMapping.findForward("view"); 
        return actionMapping.findForward("modify");
      } 
      if ("update".equals(action)) {
        CustomurlPO po = new CustomurlPO();
        po.setUrlapp(customDeskUrlActionForm.getUrlapp());
        po.setUrlname(customDeskUrlActionForm.getUrlname());
        customDesktopBD.updateCustomDeskUrl(po, customDeskUrlActionForm.getId());
        return actionMapping.findForward("modify");
      } 
      if ("delete".equals(action)) {
        if (request.getParameter("id") != null && !"".equals(request.getParameter("id"))) {
          Long id = new Long(request.getParameter("id"));
          customDesktopBD.deleteCustomDeskUrl(id);
        } 
        customDeskUrlList(request);
        action = "list";
      } else if ("search".equals(action)) {
        customDeskUrlList(request);
      } else if ("deleteDefault".equals(action)) {
        if (request.getParameter("id") != null && !"".equals(request.getParameter("id"))) {
          Long id = new Long(request.getParameter("id"));
          customDesktopBD.deleteCustomDefault(id);
        } 
        action = "listdefault";
        listdefault(request);
      } else if ("listdefault".equals(action)) {
        action = "listdefault";
        listdefault(request);
      } else if ("defaultportaladd".equals(action)) {
        String rightCode = "00*01*03";
        if ("1".equals(httpSession.getAttribute("sysManager").toString()))
          rightCode = "00*01*01"; 
        ManagerService managerBD = new ManagerService();
        String orgId = httpSession.getAttribute("orgId").toString();
        String viewSql = "po.id,po.layoutName";
        String fromSql = 
          " com.js.oa.portal.po.CustomDesktopLayoutPO po ";
        String whereSql = "";
        whereSql = " where (" + managerBD.getRightWhere(userId, orgId, rightCode, "po.createdOrg", "po.createdEmp");
        whereSql = String.valueOf(whereSql) + ") and (po.ispublic=1 or po.ispublic is null or po.ispublic='') and  po.domainId=" + 
          httpSession.getAttribute("domainId");
        whereSql = String.valueOf(whereSql) + " order by po.id desc";
        int pageSize = 15;
        int offset = 0;
        if (request.getParameter("pager.offset") != null)
          offset = Integer.parseInt(request.getParameter(
                "pager.offset")); 
        int currentPage = offset / pageSize + 1;
        Page page = new Page(viewSql, fromSql, whereSql);
        page.setPageSize(65536);
        page.setcurrentPage(currentPage);
        List list = page.getResultList();
        request.setAttribute("list", list);
      } else if ("defaultportalsave".equals(action)) {
        CustomDefaultPO po = new CustomDefaultPO();
        String[] portal = request.getParameter("portalId").split("&&");
        String userOrgGroup = request.getParameter("userOrgGroup");
        String viewRangeName = request.getParameter("viewRangeName");
        po.setPortal_id(Long.valueOf(Long.parseLong(portal[0])));
        po.setPortalname(portal[1]);
        po.setViewrangename(viewRangeName);
        po.setViewuser(StringSplit.splitWith(userOrgGroup, "$", "*@"));
        po.setVieworg(StringSplit.splitWith(userOrgGroup, "*", "@$"));
        po.setViewgroup(StringSplit.splitWith(userOrgGroup, "@", "$*"));
        po.setCreatedemp(Long.valueOf(Long.parseLong(httpSession.getAttribute("userId").toString())));
        po.setCreatedorg(Long.valueOf(Long.parseLong(httpSession.getAttribute("orgId").toString())));
        customDesktopBD.saveCustomDefaultPortal(po);
        request.setAttribute("flag", "1");
        action = "defaultportaladd";
      } else {
        if ("defaultportalupdate".equals(action)) {
          CustomDefaultPO po = new CustomDefaultPO();
          String[] portal = request.getParameter("portalId").split("&&");
          String userOrgGroup = request.getParameter("userOrgGroup");
          String viewRangeName = request.getParameter("viewRangeName");
          po.setPortal_id(Long.valueOf(Long.parseLong(portal[0])));
          po.setPortalname(portal[1]);
          po.setViewrangename(viewRangeName);
          po.setViewuser(StringSplit.splitWith(userOrgGroup, "$", "*@"));
          po.setVieworg(StringSplit.splitWith(userOrgGroup, "*", "@$"));
          po.setViewgroup(StringSplit.splitWith(userOrgGroup, "@", "$*"));
          Long id = new Long(request.getParameter("id"));
          customDesktopBD.updateCustomDefaultPortal(po, id);
          request.setAttribute("flag", "1");
          return actionMapping.findForward("defaultportalmodify");
        } 
        if (action.equals("defaultportalmodify") || action.equals("defaultportalview")) {
          String rightCode = "00*01*03";
          if ("1".equals(httpSession.getAttribute("sysManager").toString()))
            rightCode = "00*01*01"; 
          ManagerService managerBD = new ManagerService();
          String orgId = httpSession.getAttribute("orgId").toString();
          String viewSql = "po.id,po.layoutName";
          String fromSql = 
            " com.js.oa.portal.po.CustomDesktopLayoutPO po ";
          String whereSql = "";
          whereSql = " where (" + managerBD.getRightWhere(userId, orgId, rightCode, "po.createdOrg", "po.createdEmp");
          whereSql = String.valueOf(whereSql) + ") and (po.ispublic=1 or po.ispublic is null or po.ispublic='') and  po.domainId=" + 
            httpSession.getAttribute("domainId");
          whereSql = String.valueOf(whereSql) + " order by po.id desc";
          int pageSize = 15;
          int offset = 0;
          if (request.getParameter("pager.offset") != null)
            offset = Integer.parseInt(request.getParameter(
                  "pager.offset")); 
          int currentPage = offset / pageSize + 1;
          Page page = new Page(viewSql, fromSql, whereSql);
          page.setPageSize(65536);
          page.setcurrentPage(currentPage);
          List list = page.getResultList();
          request.setAttribute("list", list);
          Long id = new Long(request.getParameter("id"));
          CustomDefaultPO po = customDesktopBD.loadCustomDefault(id);
          request.setAttribute("id", request.getParameter("id"));
          request.setAttribute("portal", po.getPortal_id() + "&&" + po.getPortalname());
          request.setAttribute("viewrangename", po.getViewrangename());
          String userOrgGroup = "";
          if (po.getViewuser() != null)
            userOrgGroup = String.valueOf(userOrgGroup) + po.getViewuser(); 
          if (po.getVieworg() != null)
            userOrgGroup = String.valueOf(userOrgGroup) + po.getVieworg(); 
          if (po.getViewgroup() != null)
            userOrgGroup = String.valueOf(userOrgGroup) + po.getViewgroup(); 
          request.setAttribute("userOrgGroup", userOrgGroup);
          if (action.equals("defaultportalview"))
            return actionMapping.findForward("defaultportalview"); 
          return actionMapping.findForward("defaultportalmodify");
        } 
      } 
    } 
    return actionMapping.findForward(action);
  }
  
  private void customDeskUrlList(HttpServletRequest httpServletRequest) {
    String domainId = "0";
    HttpSession httpSession = httpServletRequest.getSession(true);
    if (httpSession.getAttribute("domainId") != null && 
      !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    String urlname = httpServletRequest.getParameter("urlname");
    String whereString = " ";
    if (!"".equals(urlname) && urlname != null) {
      whereString = String.valueOf(whereString) + "where 1=1  and po.urlname like '%" + urlname + "%' ";
    } else {
      whereString = String.valueOf(whereString) + " where 1=1 ";
    } 
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "po.id,po.urlname,po.urlapp ", 
        "com.js.oa.portal.po.CustomurlPO po", 
        String.valueOf(whereString) + "  order by po.id ");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("customDeskUrlList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  private void listdefault(HttpServletRequest httpServletRequest) {
    String urlname = httpServletRequest.getParameter("buildName");
    String whereString = " ";
    if (!"".equals(urlname) && urlname != null) {
      whereString = String.valueOf(whereString) + "where 1=1  and po.portalname like '%" + urlname + "%' ";
    } else {
      whereString = String.valueOf(whereString) + " where 1=1 ";
    } 
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "po.id,po.portalname,po.viewrangename ", 
        "com.js.oa.portal.po.CustomDefaultPO po", 
        String.valueOf(whereString) + "  order by po.id ");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("customDeskUrlList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
}
