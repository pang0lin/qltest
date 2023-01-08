package com.js.oa.portal.action;

import com.js.oa.message.service.MsManageBD;
import com.js.oa.portal.po.CustomDesktopLayoutPO;
import com.js.oa.portal.service.CustomDesktopBD;
import com.js.oa.security.log.service.LogBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.ParameterFilter;
import com.js.util.util.StringSplit;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CustomDesktopLayoutAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
    CustomDesktopLayoutActionForm customDesktopLayoutActionForm = 
      (CustomDesktopLayoutActionForm)actionForm;
    CustomDesktopLayoutPO customDesktopLayoutPO = new CustomDesktopLayoutPO();
    CustomDesktopBD customDesktopBD = new CustomDesktopBD();
    LogBD logBD = new LogBD();
    Date endDate = new Date();
    Date startDate = new Date();
    HttpSession session = httpServletRequest.getSession(true);
    if (session == null || session.getAttribute("userName") == null)
      httpServletResponse.sendRedirect("/jsoa/login.jsp"); 
    String urlPath = "portal2/";
    ServletContext sct = session.getServletContext();
    String path = String.valueOf(sct.getRealPath("/")) + urlPath + "layout";
    if (SystemCommon.getUseClusterServer() == 1)
      path = String.valueOf(sct.getRealPath("/")) + "upload/layout"; 
    String action = httpServletRequest.getParameter("action");
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    String tag = "view";
    if (action.equals("add")) {
      tag = "add";
      customDesktopLayoutActionForm.setIsUrl("0");
    } else if (action.equals("saveandexit") || action.equals("saveandcontinue")) {
      tag = "add";
      String layoutName = customDesktopLayoutActionForm.getLayoutName();
      String userOrgGroup = customDesktopLayoutActionForm.getUserOrgGroup();
      String viewRangeName = customDesktopLayoutActionForm.getViewRangeName();
      customDesktopLayoutPO.setLayoutName(layoutName);
      customDesktopLayoutPO.setViewUser(StringSplit.splitWith(userOrgGroup, "$", "*@"));
      customDesktopLayoutPO.setViewOrg(StringSplit.splitWith(userOrgGroup, "*", "@$"));
      customDesktopLayoutPO.setViewGroup(StringSplit.splitWith(userOrgGroup, "@", "$*"));
      customDesktopLayoutPO.setCreatedEmp(Long.valueOf(Long.parseLong(session.getAttribute("userId").toString())));
      customDesktopLayoutPO.setCreatedOrg(Long.valueOf(Long.parseLong(session.getAttribute("orgId").toString())));
      customDesktopLayoutPO.setIspublic("1");
      customDesktopLayoutPO.setPersonEmpId(session.getAttribute("userId").toString());
      customDesktopLayoutPO.setViewRangeName(viewRangeName);
      customDesktopLayoutPO.setDomainId(domainId);
      customDesktopLayoutPO.setShowOrder(customDesktopLayoutActionForm.getShowOrder());
      customDesktopLayoutPO.setMenuName(customDesktopLayoutActionForm.getMenuName());
      customDesktopLayoutPO.setMenuType(customDesktopLayoutActionForm.getMenuType());
      customDesktopLayoutPO.setIsUrl(customDesktopLayoutActionForm.getIsUrl());
      customDesktopLayoutPO.setUrl(customDesktopLayoutActionForm.getUrl());
      if ("2".equals(customDesktopLayoutActionForm.getIsUrl()))
        customDesktopLayoutPO.setMenuId(httpServletRequest.getParameter("menuId")); 
      int opResult = 0;
      if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
        opResult = customDesktopBD.saveLayout(customDesktopLayoutPO, path, domainId).intValue();
        if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())
          customDesktopBD.autoAudit(customDesktopLayoutPO, "insert", new Long(0L), httpServletRequest); 
      } 
      if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0) {
        customDesktopBD.audit(customDesktopLayoutPO, "insert", new Long(0L), httpServletRequest);
        opResult = 0;
        httpServletRequest.setAttribute("flag", "foraudit");
      } 
      logBD.log(httpServletRequest.getSession(true).getAttribute("userId").toString(), 
          httpServletRequest.getSession(true).getAttribute("userName").toString(), 
          httpServletRequest.getSession(true).getAttribute("orgName").toString(), 
          "system_customdesktop", 
          "门户定义", 
          startDate, 
          endDate, 
          "1", 
          layoutName, 
          httpServletRequest.getRemoteAddr(), 
          httpServletRequest.getSession(true).getAttribute("domainId").toString());
      httpServletRequest.setAttribute("opResult", String.valueOf(opResult));
      if (action.equals("saveandexit")) {
        httpServletRequest.setAttribute("shouldClose", "1");
      } else if (action.equals("saveandcontinue")) {
        httpServletRequest.setAttribute("shouldClose", "0");
        customDesktopLayoutActionForm.setLayoutName("");
        customDesktopLayoutActionForm.setViewRangeName("");
        customDesktopLayoutActionForm.setUserOrgGroup("");
        customDesktopLayoutActionForm.setMenuName("");
        customDesktopLayoutActionForm.setShowOrder("");
        customDesktopLayoutActionForm.setMenuType(0);
        customDesktopLayoutActionForm.setIsUrl("0");
        customDesktopLayoutActionForm.setUrl("");
      } 
    } else if (action.equals("load")) {
      tag = "modi";
      String layoutId = httpServletRequest.getParameter("modifyId");
      customDesktopLayoutPO = customDesktopBD.getLayoutById(layoutId);
      customDesktopLayoutActionForm.setId(layoutId);
      customDesktopLayoutActionForm.setLayoutName(customDesktopLayoutPO
          .getLayoutName());
      String userOrgGroup = "";
      if (customDesktopLayoutPO.getViewUser() != null)
        userOrgGroup = String.valueOf(userOrgGroup) + customDesktopLayoutPO.getViewUser(); 
      if (customDesktopLayoutPO.getViewOrg() != null)
        userOrgGroup = String.valueOf(userOrgGroup) + customDesktopLayoutPO.getViewOrg(); 
      if (customDesktopLayoutPO.getViewGroup() != null)
        userOrgGroup = String.valueOf(userOrgGroup) + customDesktopLayoutPO.getViewGroup(); 
      customDesktopLayoutActionForm.setUserOrgGroup(userOrgGroup);
      customDesktopLayoutActionForm.setViewRangeName(
          customDesktopLayoutPO.getViewRangeName());
      customDesktopLayoutActionForm.setXmlFile(customDesktopLayoutPO.getXmlFile());
      customDesktopLayoutActionForm.setShowOrder(customDesktopLayoutPO.getShowOrder());
      customDesktopLayoutActionForm.setMenuName(customDesktopLayoutPO.getMenuName());
      customDesktopLayoutActionForm.setMenuType(customDesktopLayoutPO.getMenuType());
      customDesktopLayoutActionForm.setIsUrl(customDesktopLayoutPO.getIsUrl());
      customDesktopLayoutActionForm.setUrl(customDesktopLayoutPO.getUrl());
      httpServletRequest.setAttribute("empId", customDesktopBD.getLayoutEmp(layoutId));
      httpServletRequest.setAttribute("layYoutName", customDesktopLayoutPO.getLayoutName());
      httpServletRequest.setAttribute("menuId", (customDesktopLayoutPO.getMenuId() == null) ? "-1" : 
          customDesktopLayoutPO.getMenuId());
      httpServletRequest.setAttribute("checkFlag", customDesktopLayoutPO.getIsUrl());
    } else if (action.equals("update")) {
      tag = "modi";
      action = "view";
      String layoutId = customDesktopLayoutActionForm.getId();
      String layoutName = customDesktopLayoutActionForm.getLayoutName();
      String userOrgGroup = customDesktopLayoutActionForm.getUserOrgGroup();
      String viewRangeName = customDesktopLayoutActionForm
        .getViewRangeName();
      customDesktopLayoutPO = (new CustomDesktopBD()).getLayoutById(layoutId);
      customDesktopLayoutPO.setLayoutName(layoutName);
      customDesktopLayoutPO.setViewUser(StringSplit.splitWith(userOrgGroup, "$", "*@"));
      customDesktopLayoutPO.setViewOrg(StringSplit.splitWith(userOrgGroup, "*", "@$"));
      customDesktopLayoutPO.setViewGroup(StringSplit.splitWith(userOrgGroup, "@", "$*"));
      customDesktopLayoutPO.setViewRangeName(viewRangeName);
      customDesktopLayoutPO.setIspublic("1");
      customDesktopLayoutPO.setShowOrder(customDesktopLayoutActionForm.getShowOrder());
      customDesktopLayoutPO.setMenuName(customDesktopLayoutActionForm.getMenuName());
      customDesktopLayoutPO.setMenuType(customDesktopLayoutActionForm.getMenuType());
      customDesktopLayoutPO.setIsUrl(customDesktopLayoutActionForm.getIsUrl());
      customDesktopLayoutPO.setUrl(customDesktopLayoutActionForm.getUrl());
      String empId = httpServletRequest.getParameter("emp_Id");
      if ("2".equals(customDesktopLayoutActionForm.getIsUrl()))
        customDesktopLayoutPO.setMenuId(httpServletRequest.getParameter("menuId")); 
      int opResult = 0;
      if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
        opResult = customDesktopBD.updateLayout(customDesktopLayoutPO, layoutId, domainId).intValue();
        if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())
          customDesktopBD.autoAudit(customDesktopLayoutPO, "update", Long.valueOf(layoutId), httpServletRequest); 
      } 
      if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0) {
        customDesktopBD.audit(customDesktopLayoutPO, "update", Long.valueOf(layoutId), httpServletRequest);
        opResult = 0;
        httpServletRequest.setAttribute("flag", "foraudit");
      } 
      customDesktopBD.updateLayoutEmp(layoutId, empId);
      logBD.log(httpServletRequest.getSession(true).getAttribute("userId").toString(), 
          httpServletRequest.getSession(true).getAttribute("userName").toString(), 
          httpServletRequest.getSession(true).getAttribute("orgName").toString(), 
          "system_customdesktop", 
          "门户定义", 
          startDate, 
          endDate, 
          "2", 
          layoutName, 
          httpServletRequest.getRemoteAddr(), 
          httpServletRequest.getSession(true).getAttribute("domainId").toString());
      httpServletRequest.setAttribute("opResult", String.valueOf(opResult));
      httpServletRequest.setAttribute("shouldClose", "1");
    } else {
      if (action.equals("nameIsRepeat")) {
        String reStr = "0";
        PrintWriter out = null;
        try {
          httpServletResponse.setContentType("text/xml;charset=GBK");
          out = httpServletResponse.getWriter();
          String layoutName = httpServletRequest.getParameter("layoutName");
          if (!ParameterFilter.checkParameter(layoutName)) {
            reStr = "1";
          } else {
            Long createdOrg = Long.valueOf(Long.parseLong(session.getAttribute("corpId").toString()));
            String sqlAjax = "select po.id,po.layoutName from com.js.oa.portal.po.CustomDesktopLayoutPO po where po.domainId=" + domainId + 
              "and po.createdOrg=" + createdOrg + " and po.layoutName='" + layoutName + "'";
            MsManageBD msBD = new MsManageBD();
            List reList = msBD.getListByYourSQL(sqlAjax);
            if (reList != null)
              reStr = String.valueOf(reList.size()); 
          } 
        } catch (Exception e) {
          e.printStackTrace();
        } 
        out.print(reStr);
        out.close();
        return null;
      } 
      if (action.equals("del")) {
        String ids = httpServletRequest.getParameter("id");
        String[] idteam = ids.split(",");
        for (int i = 0; i < idteam.length; i++) {
          customDesktopLayoutPO = customDesktopBD.getLayoutById(idteam[i]);
          logBD.log(httpServletRequest.getSession(true).getAttribute("userId").toString(), 
              httpServletRequest.getSession(true).getAttribute("userName").toString(), 
              httpServletRequest.getSession(true).getAttribute("orgName").toString(), 
              "system_customdesktop", 
              "门户定义", 
              startDate, 
              endDate, 
              "3", 
              customDesktopLayoutPO.getLayoutName(), 
              httpServletRequest.getRemoteAddr(), 
              httpServletRequest.getSession(true).getAttribute("domainId").toString());
        } 
        if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
          if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())
            customDesktopBD.autoAudit(customDesktopLayoutPO, "delete", Long.valueOf(ids.substring(0, ids.length() - 1)), httpServletRequest); 
          customDesktopBD.delLayout(ids);
        } 
        if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0) {
          customDesktopBD.audit(customDesktopLayoutPO, "delete", Long.valueOf(ids.substring(0, ids.length() - 1)), httpServletRequest);
          httpServletRequest.setAttribute("flag", "foraudit");
        } 
        view(httpServletRequest, domainId);
      } else if (action.equals("view")) {
        view(httpServletRequest, domainId);
      } else if (action.equals("person")) {
        getPersonalDesktop(httpServletRequest, domainId);
      } else if (action.equals("tmpFrame")) {
        tag = "frame";
        String channelType = httpServletRequest.getParameter("channelType");
        String userChannelName = httpServletRequest.getParameter("userChannelName");
        httpServletRequest.setAttribute("userChannelName", userChannelName);
        httpServletRequest.setAttribute("channelType", channelType);
      } 
    } 
    return actionMapping.findForward(tag);
  }
  
  public void view(HttpServletRequest servletRequest, String domainId) {
    HttpSession session = servletRequest.getSession(true);
    String rightCode = "00*01*03";
    if ("1".equals(session.getAttribute("sysManager").toString()))
      rightCode = "00*01*01"; 
    ManagerService managerBD = new ManagerService();
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    String viewSql = "po.id,po.layoutName,po.ViewUser,po.viewRangeName,po.xmlFile,po.menuName,po.showOrder,po.menuType,po.ViewOrg,po.ViewGroup";
    String fromSql = 
      " com.js.oa.portal.po.CustomDesktopLayoutPO po ";
    String whereSql = "";
    whereSql = " where (" + managerBD.getRightWhere(userId, orgId, rightCode, "po.createdOrg", "po.createdEmp");
    whereSql = String.valueOf(whereSql) + ") and (po.ispublic=1 or po.ispublic is null or po.ispublic='') and  po.domainId=" + 
      session.getAttribute("domainId");
    whereSql = String.valueOf(whereSql) + " order by po.id desc";
    list(servletRequest, viewSql, fromSql, whereSql);
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
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  public void getPersonalDesktop(HttpServletRequest servletRequest, String domainId) {
    String viewSql = 
      "po.id,po.layoutName,po.ViewUser,po.viewRangeName,po.xmlFile,po.ViewOrg,po.ViewGroup";
    String fromSql = 
      "com.js.oa.portal.po.CustomDesktopLayoutPO po";
    String whereSql = " po.domainId=" + domainId + " order by po.id desc";
    list(servletRequest, viewSql, fromSql, whereSql);
  }
}
