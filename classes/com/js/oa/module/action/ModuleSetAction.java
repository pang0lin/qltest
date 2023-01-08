package com.js.oa.module.action;

import com.js.oa.info.channelmanager.po.UserChannelPO;
import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.oa.module.po.ModuleMenuPO;
import com.js.oa.module.po.SystemMenuPO;
import com.js.oa.module.service.ModuleMenuAllService;
import com.js.oa.module.service.ModuleMenuService;
import com.js.oa.module.vo.ListItem;
import com.js.oa.security.log.service.LogBD;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.DataSourceBase;
import com.js.util.util.FillBean;
import com.js.util.util.StringSplit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ModuleSetAction extends Action {
  private static String GO = "toAdd";
  
  private static String ADD = "add";
  
  private static String CUST_MENU_LIST = "custMenuList";
  
  private static String CUST_MENU_LOAD = "load";
  
  private static String DEL_BATCH = "delBatch";
  
  private static String DEL_ALL = "delAll";
  
  private static String UPDATE = "update";
  
  private static String MODI_CHANNEL = "modiChannel";
  
  private static String DEL_CHANNEL = "delChannel";
  
  private static String LOAD_CHANNEL = "loadChannel";
  
  private static String HIDE_MENU = "hideMenu";
  
  private static String SHOW_MENU = "showMenu";
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
    HttpSession session = request.getSession(true);
    String curUserId = session.getAttribute("userId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    Long domainId = (session.getAttribute("domainId") != null) ? Long.valueOf(session.getAttribute("domainId").toString()) : 
      Long.valueOf("0");
    ModuleMenuActionForm form = (ModuleMenuActionForm)actionForm;
    ModuleMenuService bd = new ModuleMenuService();
    String action = request.getParameter("action");
    String mode = request.getParameter("mode");
    if (action.equals(GO)) {
      form.setOperationType("0");
      form.setZorder("1");
      form.setZorderLocation("1");
      form.setMenuActionParams1("");
      form.setMenuIsValide(1);
      form.setMenuZkyType("0");
      form.setMenuIsZky("0");
      String flag = request.getParameter("flag");
      request.setAttribute("flag", flag);
      return actionMapping.findForward("go");
    } 
    if (action.equals(ADD)) {
      if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
        ModuleMenuPO pPo = null;
        if (form.getChannel() != null && form.getChannel().length() > 0) {
          ChannelBD channelBD = new ChannelBD();
          channelBD.addUserChannel(form.getChannel(), 
              form.getChannelOrder(), 
              domainId.toString());
        } else if (-1L == form.getMenuBlone().longValue() || 0L == form.getMenuBlone().longValue()) {
          try {
            ModuleMenuAllService mms = new ModuleMenuAllService();
            SystemMenuPO oMenu = setMenuSetPo(form, domainId.toString(), request);
            Long retNo = mms.saveTopMenu(oMenu);
            if (retNo.longValue() > 0L) {
              String menuViewId;
              ModuleMenuPO po = new ModuleMenuPO();
              po.setMenuName(form.getMenuName());
              po.setMenuLocation(retNo);
              po.setMenuAction(form.getMenuAction());
              po.setMenuActionParams1(form.getMenuActionParams1());
              po.setMenuActionParams2(form.getMenuActionParams2());
              po.setMenuActionParams3(form.getMenuActionParams3());
              po.setMenuActionParams4(form.getMenuActionParams4());
              po.setMenuActionParams4Value(form.getMenuActionParams4Value());
              po.setMenuBlone(retNo);
              po.setMenuLevel("_" + retNo.toString());
              po.setDomainId(domainId);
              po.setMenuCount(8);
              po.setMenuOpenStyle(form.getMenuOpenStyle());
              po.setMenuListTableMap(new Long(0L));
              po.setMenuRefFlow("|");
              po.setMenuIsZky((request.getParameter("menuIsZky") == null) ? "0" : request.getParameter("menuIsZky"));
              String menuZkyDoSelect = "0";
              String zkyAdd = request.getParameter("zkyAdd");
              String zkyExport = request.getParameter("zkyExport");
              String zkyDelete = request.getParameter("zkyDelete");
              String zkyImport = request.getParameter("zkyImport");
              String menuZkyType = (request.getParameter("menuZkyType") == null) ? "0" : request.getParameter("menuZkyType");
              po.setMenuZkyType(menuZkyType);
              if (zkyImport != null && !"".equals(zkyImport))
                menuZkyDoSelect = String.valueOf(menuZkyDoSelect) + zkyImport + ","; 
              if (zkyAdd != null && !"".equals(zkyAdd))
                menuZkyDoSelect = String.valueOf(menuZkyDoSelect) + zkyAdd + ","; 
              if (zkyExport != null && !"".equals(zkyExport))
                menuZkyDoSelect = String.valueOf(menuZkyDoSelect) + zkyExport + ","; 
              if (zkyDelete != null && !"".equals(zkyDelete))
                menuZkyDoSelect = String.valueOf(menuZkyDoSelect) + zkyDelete + ","; 
              if (menuZkyDoSelect != null && !"".equals(menuZkyDoSelect) && menuZkyDoSelect.endsWith(","))
                menuZkyDoSelect = menuZkyDoSelect.substring(0, menuZkyDoSelect.length() - 1); 
              po.setMenuZkyDoSelect(menuZkyDoSelect);
              String menuScopeTemp = form.getMenuScope();
              if (menuScopeTemp.lastIndexOf("-*") > 0) {
                po.setMenuScope(form.getMenuScope().substring(0, form.getMenuScope().lastIndexOf("-*")));
                menuViewId = form.getMenuScope().substring(form.getMenuScope().lastIndexOf("-*") + 1, form.getMenuScope().length());
              } else if (menuScopeTemp.lastIndexOf("-$") > 0) {
                po.setMenuScope(form.getMenuScope().substring(0, form.getMenuScope().lastIndexOf("-$")));
                menuViewId = form.getMenuScope().substring(form.getMenuScope().lastIndexOf("-$") + 1, form.getMenuScope().length());
              } else {
                po.setMenuScope(form.getMenuScope().substring(0, form.getMenuScope().lastIndexOf("-@")));
                menuViewId = form.getMenuScope().substring(form.getMenuScope().lastIndexOf("-@") + 1, form.getMenuScope().length());
              } 
              po.setMenuViewUser(StringSplit.splitWith(menuViewId, "$", "*@"));
              po.setMenuViewGroup(StringSplit.splitWith(menuViewId, "@", "$*"));
              po.setMenuViewOrg(StringSplit.splitWith(menuViewId, "*", "@$"));
              po.setMenuListQueryConditionElements(form.getMenuListQueryConditionElements());
              po.setMenuListDisplayElements(form.getMenuListDisplayElements());
              po.setMenuIsValide(form.getMenuIsValide());
              po.setMenuMaintenanceSubTableMap(po.getMenuBlone());
              po.setParentOrder(form.getParentOrder());
              bd.saveMenuConfig(po);
            } 
          } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
          } 
        } else {
          pPo = bd.loadParentMenuConfiger(domainId.toString(), 
              form.getMenuBlone().toString());
          ModuleMenuPO po = new ModuleMenuPO();
          injectPo(po, domainId, form, curUserId, mode);
          if (pPo.getMenuMaintenanceSubTableMap() != null && 
            pPo.getMenuMaintenanceSubTableMap().longValue() > 0L)
            po.setMenuMaintenanceSubTableMap(pPo.getMenuMaintenanceSubTableMap()); 
          po.setMenuIsZky((request.getParameter("menuIsZky") == null) ? "0" : request.getParameter("menuIsZky"));
          String menuZkyDoSelect = "0";
          String zkyAdd = request.getParameter("zkyAdd");
          String zkyExport = request.getParameter("zkyExport");
          String zkyDelete = request.getParameter("zkyDelete");
          String zkyImport = request.getParameter("zkyImport");
          String menuZkyType = (request.getParameter("menuZkyType") == null) ? "0" : request.getParameter("menuZkyType");
          po.setMenuZkyType(menuZkyType);
          if (zkyImport != null && !"".equals(zkyImport))
            menuZkyDoSelect = String.valueOf(menuZkyDoSelect) + zkyImport + ","; 
          if (zkyAdd != null && !"".equals(zkyAdd))
            menuZkyDoSelect = String.valueOf(menuZkyDoSelect) + zkyAdd + ","; 
          if (zkyExport != null && !"".equals(zkyExport))
            menuZkyDoSelect = String.valueOf(menuZkyDoSelect) + zkyExport + ","; 
          if (zkyDelete != null && !"".equals(zkyDelete))
            menuZkyDoSelect = String.valueOf(menuZkyDoSelect) + zkyDelete + ","; 
          if (menuZkyDoSelect != null && !"".equals(menuZkyDoSelect) && menuZkyDoSelect.endsWith(","))
            menuZkyDoSelect = menuZkyDoSelect.substring(0, menuZkyDoSelect.length() - 1); 
          po.setMenuZkyDoSelect(menuZkyDoSelect);
          String menuRefFlowStatue1 = request.getParameter("menuRefFlowStatue1");
          menuRefFlowStatue1 = (menuRefFlowStatue1 == null) ? "0" : menuRefFlowStatue1;
          String menuRefFlowStatue2 = request.getParameter("menuRefFlowStatue2");
          menuRefFlowStatue2 = (menuRefFlowStatue2 == null) ? "0" : menuRefFlowStatue2;
          String menuRefFlowStatue3 = request.getParameter("menuRefFlowStatue3");
          menuRefFlowStatue3 = (menuRefFlowStatue3 == null) ? "0" : menuRefFlowStatue3;
          String menuRefFlowStatue4 = request.getParameter("menuRefFlowStatue4");
          menuRefFlowStatue4 = (menuRefFlowStatue4 == null) ? "0" : menuRefFlowStatue4;
          String menuRefFlowStatue = String.valueOf(menuRefFlowStatue1) + "," + menuRefFlowStatue2 + "," + menuRefFlowStatue3 + "," + menuRefFlowStatue4;
          po.setMenuRefFlowStatue(menuRefFlowStatue);
          setMenuOrderRule(pPo, po, domainId.toString(), bd, form);
          po.setParentOrder(pPo.getParentOrder());
          if (form.getZorder() != null && form.getMenuBlone() != null && 
            form.getMenuBlone().longValue() > 0L && 
            form.getMenuLocation() != null && 
            form.getMenuLocation().intValue() > 0)
            menuZorder(po, request, form, domainId.toString(), "0"); 
          String defaultRoleCBView = request.getParameter("defaultRoleCBView");
          String defaultRoleCBUpdate = request.getParameter("defaultRoleCBUpdate");
          String defaultRoleCB = "";
          if (defaultRoleCBView != null && !"".equals(defaultRoleCBView))
            defaultRoleCB = String.valueOf(defaultRoleCB) + defaultRoleCBView + ","; 
          if (defaultRoleCBUpdate != null && !"".equals(defaultRoleCBUpdate))
            defaultRoleCB = String.valueOf(defaultRoleCB) + defaultRoleCBUpdate + ","; 
          if (!"".equals(defaultRoleCB)) {
            defaultRoleCB = defaultRoleCB.substring(0, defaultRoleCB.length() - 1);
            po.setDefaultRoleCb(defaultRoleCB);
            String defaultRoleRangeType = request.getParameter("defaultRoleRangeType");
            po.setDefaultRoleRangeType(defaultRoleRangeType);
            if ("4".equals(defaultRoleRangeType)) {
              String defaultRoleRange = request.getParameter("defaultRoleRange");
              String defaultRoleRangeName = request.getParameter("defaultRoleRangeName");
              po.setDefaultRoleRange(defaultRoleRange);
              po.setDefaultRoleRangeName(defaultRoleRangeName);
            } else {
              po.setDefaultRoleRange("");
              po.setDefaultRoleRangeName("");
            } 
          } else {
            po.setDefaultRoleCb("");
            po.setDefaultRoleRangeType("");
            po.setDefaultRoleRange("");
            po.setDefaultRoleRangeName("");
          } 
          Long menuId = bd.saveMenuConfig(po);
          if (menuId != null && menuId.longValue() > 0L)
            if (po.getMenuListTableMap() != null && 
              po.getMenuListTableMap().longValue() > 0L)
              addMenuListRight(menuId, po.getMenuName(), domainId, bd);  
        } 
        request.setAttribute("validate", request.getParameter("validate"));
        LogBD logBD = new LogBD();
        Date startDate = new Date();
        String moduleCode = "system_custommenu";
        String userId = session.getAttribute("userId").toString();
        String userName = session.getAttribute("userName").toString();
        String orgName = session.getAttribute("orgName").toString();
        logBD.log(userId, userName, orgName, moduleCode, "", startDate, startDate, "1", form.getMenuName(), request.getRemoteAddr(), session.getAttribute("domainId").toString());
        if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit()) {
          String menuViewId;
          ModuleMenuPO po = (ModuleMenuPO)FillBean.transformOTO(form, ModuleMenuPO.class);
          String menuScopeTemp = form.getMenuScope();
          if (menuScopeTemp.lastIndexOf("-*") > 0) {
            po.setMenuScope(form.getMenuScope().substring(0, form.getMenuScope().lastIndexOf("-*")));
            menuViewId = form.getMenuScope().substring(form.getMenuScope().lastIndexOf("-*") + 1, form.getMenuScope().length());
          } else if (menuScopeTemp.lastIndexOf("-$") > 0) {
            po.setMenuScope(form.getMenuScope().substring(0, form.getMenuScope().lastIndexOf("-$")));
            menuViewId = form.getMenuScope().substring(form.getMenuScope().lastIndexOf("-$") + 1, form.getMenuScope().length());
          } else {
            po.setMenuScope(form.getMenuScope().substring(0, form.getMenuScope().lastIndexOf("-@")));
            menuViewId = form.getMenuScope().substring(form.getMenuScope().lastIndexOf("-@") + 1, form.getMenuScope().length());
          } 
          po.setMenuLocationSelValue((form.getMenuLocation() == null) ? "" : form.getMenuLocation().toString());
          po.setMenuViewUser(StringSplit.splitWith(menuViewId, "$", "*@"));
          po.setMenuViewGroup(StringSplit.splitWith(menuViewId, "@", "$*"));
          po.setMenuViewOrg(StringSplit.splitWith(menuViewId, "*", "@$"));
          bd.autoAudit(po, "insert", new Long(0L), request);
        } 
      } 
      if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0) {
        String menuViewId;
        ModuleMenuPO po = (ModuleMenuPO)FillBean.transformOTO(form, ModuleMenuPO.class);
        String menuScopeTemp = form.getMenuScope();
        if (menuScopeTemp.lastIndexOf("-*") > 0) {
          po.setMenuScope(form.getMenuScope().substring(0, form.getMenuScope().lastIndexOf("-*")));
          menuViewId = form.getMenuScope().substring(form.getMenuScope().lastIndexOf("-*") + 1, form.getMenuScope().length());
        } else if (menuScopeTemp.lastIndexOf("-$") > 0) {
          po.setMenuScope(form.getMenuScope().substring(0, form.getMenuScope().lastIndexOf("-$")));
          menuViewId = form.getMenuScope().substring(form.getMenuScope().lastIndexOf("-$") + 1, form.getMenuScope().length());
        } else {
          po.setMenuScope(form.getMenuScope().substring(0, form.getMenuScope().lastIndexOf("-@")));
          menuViewId = form.getMenuScope().substring(form.getMenuScope().lastIndexOf("-@") + 1, form.getMenuScope().length());
        } 
        po.setMenuLocationSelValue((form.getMenuLocation() == null) ? "" : form.getMenuLocation().toString());
        po.setMenuViewUser(StringSplit.splitWith(menuViewId, "$", "*@"));
        po.setMenuViewGroup(StringSplit.splitWith(menuViewId, "@", "$*"));
        po.setMenuViewOrg(StringSplit.splitWith(menuViewId, "*", "@$"));
        bd.audit(po, "insert", new Long(0L), request);
        request.setAttribute("flag", "foraudit");
      } 
      return actionMapping.findForward("add");
    } 
    if (SHOW_MENU.equals(action) || HIDE_MENU.equals(action)) {
      String menuId = request.getParameter("menuId");
      String menuLevel = request.getParameter("menuLevel");
      String menuLocation = request.getParameter("menuLocation");
      String menuCount = request.getParameter("menuCount");
      if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
        if (SHOW_MENU.equals(action))
          bd.setMenuDisplay(menuId, menuLevel, menuLocation, menuCount, 
              domainId.toString(), 1); 
        if (HIDE_MENU.equals(action))
          bd.setMenuDisplay(menuId, menuLevel, menuLocation, menuCount, 
              domainId.toString(), 0); 
        if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit()) {
          ModuleMenuPO po = new ModuleMenuPO();
          String able = "";
          if (SHOW_MENU.equals(action))
            able = "showMenu"; 
          if (HIDE_MENU.equals(action))
            able = "hideMenu"; 
          bd.autoAudit(po, able, Long.valueOf(menuId), request);
        } 
      } 
      if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0) {
        ModuleMenuPO po = new ModuleMenuPO();
        String able = "";
        if (SHOW_MENU.equals(action))
          able = "showMenu"; 
        if (HIDE_MENU.equals(action))
          able = "hideMenu"; 
        bd.audit(po, able, Long.valueOf(menuId), request);
        request.setAttribute("flag", "foraudit");
      } 
      action = CUST_MENU_LIST;
    } 
    if (CUST_MENU_LIST.equals(action))
      try {
        int pageSize = 15;
        int offset = 0;
        if (request.getParameter("pager.offset") != null)
          offset = Integer.parseInt(request.getParameter(
                "pager.offset")); 
        int currentPage = offset / pageSize + 1;
        String where = " where po.domainId = " + domainId + " order by po.parentOrder, po.menuLevel ";
        Page page = new Page("po.id, po.menuName, po.menuLevel, po.menuIsValide, po.menuLocation, po.menuBlone, po.menuCount,po.menuScope ", 
            " com.js.oa.module.po.ModuleMenuPO po ", 
            where);
        page.setPageSize(pageSize);
        page.setcurrentPage(currentPage);
        List list = page.getResultList();
        getOrderAndReader(list);
        String recordCount = String.valueOf(page.getRecordCount());
        request.setAttribute("mylist", list);
        request.setAttribute("recordCount", recordCount);
        request.setAttribute("maxPageItems", String.valueOf(pageSize));
        request.setAttribute("pageParameters", "action,validate");
        String flag = request.getParameter("flag");
        if (flag == null)
          flag = (request.getAttribute("flag") == null) ? "" : request.getAttribute("flag").toString(); 
        request.setAttribute("flag", flag);
        return actionMapping.findForward("success");
      } catch (Exception e) {
        e.printStackTrace();
      }  
    if (CUST_MENU_LOAD.equals(action)) {
      String level = request.getParameter("level");
      String zorderLocation = "";
      ModuleMenuAllService oBd = new ModuleMenuAllService();
      if ("0".equals(level)) {
        try {
          SystemMenuPO po = oBd.loadMenuSet(request.getParameter("menuId"));
          if (po != null) {
            zorderLocation = po.getMenuId().toString();
            form.setMenuName(po.getMenuName());
            form.setId(po.getMenuId());
            form.setMenuLocation(po.getMenuId());
            form.setMenuScope(po.getMenuView());
            form.setMenuCount(8);
            form.setParentOrder((po.getMenuOrder() == null || "".equals(po.getMenuOrder())) ? Long.valueOf("0") : Long.valueOf(po.getMenuOrder()));
          } 
        } catch (Exception ex) {
          ex.printStackTrace();
        } 
      } else {
        ModuleMenuPO po = 
          bd.loadMenuConfig(domainId
            .toString(), 
            request.getParameter("menuId"))
          .get(0);
        String menuViewUser = (po.getMenuViewUser() == null || po.getMenuViewUser().equals("null")) ? "" : po.getMenuViewUser();
        String menuViewGroup = (po.getMenuViewGroup() == null || po.getMenuViewGroup().equals("null")) ? "" : po.getMenuViewGroup();
        String menuViewOrg = (po.getMenuViewOrg() == null || po.getMenuViewOrg().equals("null")) ? "" : po.getMenuViewOrg();
        request.setAttribute("menuView", po.getMenuScope());
        request.setAttribute("menuViewId", String.valueOf(menuViewUser) + menuViewOrg + menuViewGroup);
        request.setAttribute("menuZkyDoSelect", po.getMenuZkyDoSelect());
        request.setAttribute("menuZkyType", po.getMenuZkyType());
        request.setAttribute("menuRefFlowStatue", po.getMenuRefFlowStatue());
        form.setMenuZkyType(po.getMenuZkyType());
        form.setMenuIsZky(po.getMenuIsZky());
        request.setAttribute("tmpDefaultRoleCb", po.getDefaultRoleCb());
        request.setAttribute("tmpDefaultRoleRangeType", po.getDefaultRoleRangeType());
        form.setDefaultRoleRangeType(po.getDefaultRoleRangeType());
        if ("4".equals(po.getDefaultRoleRangeType())) {
          request.setAttribute("tmpDefaultRoleRangeName", po.getDefaultRoleRangeName());
          request.setAttribute("tmpDefaultRoleRange", po.getDefaultRoleRange());
        } 
        SystemMenuPO ppo = oBd.loadMenuSet(po.getMenuBlone().toString());
        if (ppo != null && ppo.getMenuURL() != null)
          if (ppo.getMenuURL().indexOf("?") > 0) {
            form.setDefHref(ppo.getMenuURL().substring(ppo
                  .getMenuURL()
                  .lastIndexOf("&") + 3, ppo.getMenuURL().length()));
            form.setDefHrefOld(form.getDefHref());
          } else {
            form.setDefHref(ppo.getMenuURL().substring(ppo
                  .getMenuURL()
                  .lastIndexOf("?") + 3, ppo.getMenuURL().length()));
            form.setDefHrefOld(form.getDefHref());
          }  
        zorderLocation = po.getMenuBlone().toString();
        injectForm(po, form);
        String[][] filed = getQueryFiled(po.getMenuListTableName());
        String filtstr = (po.getMenuDefQueryCondition() == null) ? "" : po.getMenuDefQueryCondition();
        String orderstr = (po.getMenuDefQueryOrder() == null) ? "" : po.getMenuDefQueryOrder();
        String mainTableNameTemp = "";
        if (filed != null && filed.length > 0) {
          for (int i = 0; i < filed.length; i++) {
            filtstr = replaceAll(filtstr, filed[i][2], 
                String.valueOf(filed[i][1]) + "." + 
                filed[i][3]);
            orderstr = replaceAll(orderstr, filed[i][2], filed[i][3]);
            mainTableNameTemp = filed[i][1];
          } 
          orderstr = replaceAll(orderstr, po.getMenuListTableName(), mainTableNameTemp);
        } 
        request.setAttribute("orderstr", orderstr);
        request.setAttribute("filtstr", filtstr);
        po.getMenuCount();
        if (po.getMenuAction() != null && 
          po.getMenuAction().length() > 0) {
          request.setAttribute("span", "1");
        } else if (po.getMenuListTableMap() != null && 
          po.getMenuListTableMap().longValue() > 0L) {
          request.setAttribute("span", "2");
        } else if (po.getMenuStartFlow() != null && 
          po.getMenuStartFlow().length() > 0 && 
          !"-1".equals(po.getMenuStartFlow())) {
          request.setAttribute("span", "4");
        } else if (po.getMenuFileLink() != null && 
          po.getMenuFileLink().length() > 0 && 
          !"null".equals(po.getMenuFileLink())) {
          request.setAttribute("span", "5");
        } 
        if (po.getMenuListQueryConditionElements() != null && 
          po.getMenuListQueryConditionElements().length() > 0) {
          List queryCaseList = bd.getAllQueryCaseByTblId(po
              .getMenuListTableMap().toString(), domainId.toString(), 
              "0");
          request.setAttribute("queryCaseList", queryCaseList);
          List queryFieldList = bd.getQueryShowFieldsByCase(po
              .getMenuListQueryConditionElements(), 
              domainId.toString(), "0");
          request.setAttribute("queryFieldList", queryFieldList);
        } 
        if (po.getMenuListDisplayElements() != null && 
          po.getMenuListDisplayElements().length() > 0) {
          List listCaseList = bd.getAllQueryCaseByTblId(po
              .getMenuListTableMap().toString(), domainId.toString(), 
              "1");
          request.setAttribute("listCaseList", listCaseList);
          List listFieldList = bd.getQueryShowFieldsByCase(po
              .getMenuListDisplayElements(), domainId.toString(), 
              "1");
          request.setAttribute("listFieldList", listFieldList);
        } 
        request.setAttribute("real", (
            po.getMenuFileLink() != null && 
            po.getMenuFileLink().length() > 0) ? 
            po.getMenuFileLink() : null);
        request.setAttribute("save", (
            po.getMenuFileLink() != null && 
            po.getMenuFileLink().length() > 0) ? 
            po.getMenuFileLink() : null);
        request.setAttribute("allcusttables", 
            bd.getAllCustTables(domainId.toString()));
        request.setAttribute("alllistfields", 
            bd.getTableFieldsById(domainId.toString(), 
              po.getMenuListTableMap().toString()));
        request.setAttribute("alllistorderfields", 
            bd.getTableFieldsById(domainId.toString(), 
              po.getMenuListTableMap().toString()));
        request.setAttribute("searchbound", 
            bd.getQueryFormByTblId(domainId.toString(), 
              po.getMenuListTableMap().toString()));
        request.setAttribute("workflows", 
            bd.getAllWFProcesses("", "", 
              domainId.toString(), 
              ""));
        request.setAttribute("defListFields", 
            bd.getDefListFields(po.getMenuListTableMap()
              .toString()));
        if (po.getMenuCount() > 7)
          request.setAttribute("subFlag", "1"); 
      } 
      request.setAttribute("allmenus", 
          bd.getAllCustomMenu(domainId.toString(), null, true, true, request.getParameter("validate")));
      request.setAttribute("zorderlist", 
          bd.getAllSubMenusForOrder(domainId
            .toString(), zorderLocation));
      return actionMapping.findForward("load");
    } 
    if (UPDATE.equals(action)) {
      if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
        ModuleMenuPO po = new ModuleMenuPO();
        ModuleMenuPO pPo = null;
        if (form.getMenuLevel().indexOf("-") <= 0) {
          String menuViewId;
          ModuleMenuAllService oBd = new ModuleMenuAllService();
          SystemMenuPO oMenu = oBd.loadMenuSet(
              (form.getMenuLevel().indexOf("-") <= 0) ? 
              form.getMenuLevel().substring(1, 
                form.getMenuLevel().length()) : form.getMenuLevel());
          oMenu.setIsSystemInit(Integer.valueOf("0"));
          String menuScopeTemp = form.getMenuScope();
          if (menuScopeTemp.lastIndexOf("-*") > 0) {
            oMenu.setMenuView(form.getMenuScope().substring(0, form.getMenuScope().lastIndexOf("-*")));
            menuViewId = form.getMenuScope().substring(form.getMenuScope().lastIndexOf("-*") + 1, form.getMenuScope().length());
          } else if (menuScopeTemp.lastIndexOf("-$") > 0) {
            oMenu.setMenuView(form.getMenuScope().substring(0, form.getMenuScope().lastIndexOf("-$")));
            menuViewId = form.getMenuScope().substring(form.getMenuScope().lastIndexOf("-$") + 1, form.getMenuScope().length());
          } else {
            oMenu.setMenuView(form.getMenuScope().substring(0, form.getMenuScope().lastIndexOf("-@")));
            menuViewId = form.getMenuScope().substring(form.getMenuScope().lastIndexOf("-@") + 1, form.getMenuScope().length());
          } 
          oMenu.setMenuViewUser(StringSplit.splitWith(menuViewId, "$", "*@"));
          oMenu.setMenuViewGroup(StringSplit.splitWith(menuViewId, "@", "$*"));
          oMenu.setMenuViewOrg(StringSplit.splitWith(menuViewId, "*", "@$"));
          oMenu.setMenuName(form.getMenuName());
          oMenu.setMenuCode(form.getMenuName());
          oMenu.setInUse(new Integer(form.getMenuIsValide()));
          setDefMenuRightUrl(form, domainId.toString(), oMenu, request);
          oMenu.setMenuOrder(String.valueOf(form.getParentOrder()));
          oBd.updateMenuSet(oMenu);
          ModuleMenuPO ppo = bd
            .loadMenuConfig(domainId.toString(), 
              form.getId().toString()).get(0);
          ppo.setMenuIsZky((request.getParameter("menuIsZky") == null) ? "0" : request.getParameter("menuIsZky"));
          String menuZkyDoSelect = "0";
          String zkyAdd = request.getParameter("zkyAdd");
          String zkyExport = request.getParameter("zkyExport");
          String zkyDelete = request.getParameter("zkyDelete");
          String zkyImport = request.getParameter("zkyImport");
          String menuZkyType = (request.getParameter("menuZkyType") == null) ? "0" : request.getParameter("menuZkyType");
          po.setMenuZkyType(menuZkyType);
          if (zkyImport != null && !"".equals(zkyImport))
            menuZkyDoSelect = String.valueOf(menuZkyDoSelect) + zkyImport + ","; 
          if (zkyAdd != null && !"".equals(zkyAdd))
            menuZkyDoSelect = String.valueOf(menuZkyDoSelect) + zkyAdd + ","; 
          if (zkyExport != null && !"".equals(zkyExport))
            menuZkyDoSelect = String.valueOf(menuZkyDoSelect) + zkyExport + ","; 
          if (zkyDelete != null && !"".equals(zkyDelete))
            menuZkyDoSelect = String.valueOf(menuZkyDoSelect) + zkyDelete + ","; 
          if (menuZkyDoSelect != null && !"".equals(menuZkyDoSelect) && menuZkyDoSelect.endsWith(","))
            menuZkyDoSelect = menuZkyDoSelect.substring(0, menuZkyDoSelect.length() - 1); 
          ppo.setMenuZkyDoSelect(menuZkyDoSelect);
          injectPo(ppo, domainId, form, curUserId, mode);
          ppo.setParentOrder(form.getParentOrder());
          bd.updateMenuConfig(ppo);
        } else {
          pPo = bd.loadParentMenuConfiger(domainId.toString(), 
              form.getMenuBlone().toString());
          po.setId(form.getId());
          List<ModuleMenuPO> changeMenuSublist = null;
          boolean changeSub = false;
          if (!form.getMenuBlone().equals(form.getMenuBloneHId())) {
            changeMenuSublist = bd.getAllSubMenusByLevel(form
                .getMenuLevel().toString());
            if (changeMenuSublist != null && 
              changeMenuSublist.size() > 0)
              changeSub = true; 
          } 
          if (!form.getMenuBlone().equals(form.getMenuBloneHId())) {
            po.setMenuBlone(form.getMenuBlone());
            po.setMenuLocation(form.getMenuBlone());
            if (pPo.getMenuLevel().indexOf("_") >= 0 || 
              pPo.getMenuLevel().indexOf("-") <= 0) {
              po.setMenuLevel(String.valueOf(pPo.getMenuLevel()) + "-5000000");
            } else if (pPo.getMenuLevel().length() == 
              form.getMenuLevel().length()) {
              po.setMenuLevel(String.valueOf(pPo.getMenuLevel()) + 
                  form
                  .getMenuLevel().substring(form
                    .getMenuLevel()
                    .indexOf("-"), 
                    form.getMenuLevel().length()));
            } else {
              po.setMenuLevel(String.valueOf(pPo.getMenuLevel()) + 
                  form
                  .getMenuLevel().substring(form
                    .getMenuLevel().lastIndexOf("-"), 
                    form.getMenuLevel().length()));
            } 
          } else {
            po.setMenuBlone(form.getMenuBlone());
            po.setMenuLocation(form.getMenuBlone());
            po.setMenuLevel(po.getMenuLevel());
          } 
          injectPo(po, domainId, form, curUserId, mode);
          if (pPo.getMenuMaintenanceSubTableMap() != null && 
            pPo.getMenuMaintenanceSubTableMap().longValue() > 0L)
            po.setMenuMaintenanceSubTableMap(pPo
                .getMenuMaintenanceSubTableMap()); 
          if (form.getZorder() != null && form.getZorderLocation() != null && 
            form.getZorderLocation().length() > 1 && 
            !form.getZorderLocation().equals(form.getZorderLacationOld()) && 
            !"-1".equals(form.getZorderLocation())) {
            menuZorder(po, request, form, domainId.toString(), "1");
            changeMenuSublist = bd.getAllSubMenusByLevel(form
                .getMenuLevel().toString());
            if (changeMenuSublist != null && 
              changeMenuSublist.size() > 0)
              changeSub = true; 
          } else {
            form.getMenuBlone().equals(form.getMenuBloneHId());
          } 
          if (po.getMenuListTableMap() != null && 
            po.getMenuListTableMap().longValue() > 0L) {
            addMenuListRight(po.getId(), po.getMenuName(), domainId, bd);
          } else {
            delMenuListRight(po.getId().toString(), domainId.toString(), 
                bd);
          } 
          po.setMenuIsZky((request.getParameter("menuIsZky") == null) ? "0" : request.getParameter("menuIsZky"));
          String menuZkyDoSelect = "0";
          String zkyAdd = request.getParameter("zkyAdd");
          String zkyExport = request.getParameter("zkyExport");
          String zkyDelete = request.getParameter("zkyDelete");
          String zkyImport = request.getParameter("zkyImport");
          String menuRefFlowStatue1 = request.getParameter("menuRefFlowStatue1");
          menuRefFlowStatue1 = (menuRefFlowStatue1 == null) ? "0" : menuRefFlowStatue1;
          String menuRefFlowStatue2 = request.getParameter("menuRefFlowStatue2");
          menuRefFlowStatue2 = (menuRefFlowStatue2 == null) ? "0" : menuRefFlowStatue2;
          String menuRefFlowStatue3 = request.getParameter("menuRefFlowStatue3");
          menuRefFlowStatue3 = (menuRefFlowStatue3 == null) ? "0" : menuRefFlowStatue3;
          String menuRefFlowStatue4 = request.getParameter("menuRefFlowStatue4");
          menuRefFlowStatue4 = (menuRefFlowStatue4 == null) ? "0" : menuRefFlowStatue4;
          String menuRefFlowStatue = String.valueOf(menuRefFlowStatue1) + "," + menuRefFlowStatue2 + "," + menuRefFlowStatue3 + "," + menuRefFlowStatue4;
          String menuZkyType = (request.getParameter("menuZkyType") == null) ? "0" : request.getParameter("menuZkyType");
          po.setMenuZkyType(menuZkyType);
          if (zkyImport != null && !"".equals(zkyImport))
            menuZkyDoSelect = String.valueOf(menuZkyDoSelect) + zkyImport + ","; 
          if (zkyAdd != null && !"".equals(zkyAdd))
            menuZkyDoSelect = String.valueOf(menuZkyDoSelect) + zkyAdd + ","; 
          if (zkyExport != null && !"".equals(zkyExport))
            menuZkyDoSelect = String.valueOf(menuZkyDoSelect) + zkyExport + ","; 
          if (zkyDelete != null && !"".equals(zkyDelete))
            menuZkyDoSelect = String.valueOf(menuZkyDoSelect) + zkyDelete + ","; 
          if (menuZkyDoSelect != null && !"".equals(menuZkyDoSelect) && menuZkyDoSelect.endsWith(","))
            menuZkyDoSelect = menuZkyDoSelect.substring(0, menuZkyDoSelect.length() - 1); 
          po.setMenuZkyDoSelect(menuZkyDoSelect);
          po.setParentOrder(pPo.getParentOrder());
          po.setMenuRefFlowStatue(menuRefFlowStatue);
          String defaultRoleCBView = request.getParameter("defaultRoleCBView");
          String defaultRoleCBUpdate = request.getParameter("defaultRoleCBUpdate");
          String defaultRoleCB = "";
          if (defaultRoleCBView != null && !"".equals(defaultRoleCBView))
            defaultRoleCB = String.valueOf(defaultRoleCB) + defaultRoleCBView + ","; 
          if (defaultRoleCBUpdate != null && !"".equals(defaultRoleCBUpdate))
            defaultRoleCB = String.valueOf(defaultRoleCB) + defaultRoleCBUpdate + ","; 
          if (!"".equals(defaultRoleCB)) {
            defaultRoleCB = defaultRoleCB.substring(0, defaultRoleCB.length() - 1);
            po.setDefaultRoleCb(defaultRoleCB);
            String defaultRoleRangeType = request.getParameter("defaultRoleRangeType");
            po.setDefaultRoleRangeType(defaultRoleRangeType);
            if ("4".equals(defaultRoleRangeType)) {
              String defaultRoleRange = request.getParameter("defaultRoleRange");
              po.setDefaultRoleRange(defaultRoleRange);
              String defaultRoleRangeName = request.getParameter("defaultRoleRangeName");
              po.setDefaultRoleRangeName(defaultRoleRangeName);
            } else {
              po.setDefaultRoleRange("");
              po.setDefaultRoleRangeName("");
            } 
          } else {
            po.setDefaultRoleCb("");
            po.setDefaultRoleRangeType("");
            po.setDefaultRoleRange("");
            po.setDefaultRoleRangeName("");
          } 
          if (!bd.updateMenuConfig(po))
            throw new Exception("自定义菜单更新失败！"); 
          if (changeSub && changeMenuSublist.size() > 0) {
            int stepDif = 0;
            String oriLevel = "";
            for (int i = 0; i < changeMenuSublist.size(); i++) {
              ModuleMenuPO subPo = 
                changeMenuSublist.get(i);
              oriLevel = po.getMenuLevel();
              subPo.setMenuLevel(String.valueOf(oriLevel) + 
                  subPo.getMenuLevel()
                  .substring(form.getMenuLevel().length(), 
                    subPo.getMenuLevel().length()));
              if (pPo.getMenuMaintenanceSubTableMap() != null && 
                pPo.getMenuMaintenanceSubTableMap().longValue() > 0L)
                subPo.setMenuMaintenanceSubTableMap(pPo
                    .getMenuMaintenanceSubTableMap()); 
              subPo.setParentOrder(pPo.getParentOrder());
              bd.updateMenuConfig(subPo);
            } 
          } 
        } 
        request.setAttribute("validate", request.getParameter("validate"));
        if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit()) {
          String menuViewId;
          ModuleMenuPO po2 = (ModuleMenuPO)FillBean.transformOTO(form, ModuleMenuPO.class);
          String menuScopeTemp = form.getMenuScope();
          if (menuScopeTemp.lastIndexOf("-*") > 0) {
            po2.setMenuScope(form.getMenuScope().substring(0, form.getMenuScope().lastIndexOf("-*")));
            menuViewId = form.getMenuScope().substring(form.getMenuScope().lastIndexOf("-*") + 1, form.getMenuScope().length());
          } else if (menuScopeTemp.lastIndexOf("-$") > 0) {
            po2.setMenuScope(form.getMenuScope().substring(0, form.getMenuScope().lastIndexOf("-$")));
            menuViewId = form.getMenuScope().substring(form.getMenuScope().lastIndexOf("-$") + 1, form.getMenuScope().length());
          } else {
            po2.setMenuScope(form.getMenuScope().substring(0, form.getMenuScope().lastIndexOf("-@")));
            menuViewId = form.getMenuScope().substring(form.getMenuScope().lastIndexOf("-@") + 1, form.getMenuScope().length());
          } 
          po2.setMenuViewUser(StringSplit.splitWith(menuViewId, "$", "*@"));
          po2.setMenuViewGroup(StringSplit.splitWith(menuViewId, "@", "$*"));
          po2.setMenuViewOrg(StringSplit.splitWith(menuViewId, "*", "@$"));
          bd.autoAudit(po2, "update", form.getId(), request);
        } 
      } 
      if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0) {
        String menuViewId;
        ModuleMenuPO po = (ModuleMenuPO)FillBean.transformOTO(form, ModuleMenuPO.class);
        String menuScopeTemp = form.getMenuScope();
        if (menuScopeTemp.lastIndexOf("-*") > 0) {
          po.setMenuScope(form.getMenuScope().substring(0, form.getMenuScope().lastIndexOf("-*")));
          menuViewId = form.getMenuScope().substring(form.getMenuScope().lastIndexOf("-*") + 1, form.getMenuScope().length());
        } else if (menuScopeTemp.lastIndexOf("-$") > 0) {
          po.setMenuScope(form.getMenuScope().substring(0, form.getMenuScope().lastIndexOf("-$")));
          menuViewId = form.getMenuScope().substring(form.getMenuScope().lastIndexOf("-$") + 1, form.getMenuScope().length());
        } else {
          po.setMenuScope(form.getMenuScope().substring(0, form.getMenuScope().lastIndexOf("-@")));
          menuViewId = form.getMenuScope().substring(form.getMenuScope().lastIndexOf("-@") + 1, form.getMenuScope().length());
        } 
        po.setMenuViewUser(StringSplit.splitWith(menuViewId, "$", "*@"));
        po.setMenuViewGroup(StringSplit.splitWith(menuViewId, "@", "$*"));
        po.setMenuViewOrg(StringSplit.splitWith(menuViewId, "*", "@$"));
        bd.audit(po, "update", form.getId(), request);
        request.setAttribute("flag", "foraudit");
      } 
      LogBD logBD = new LogBD();
      Date startDate = new Date();
      String moduleCode = "system_custommenu";
      String userId = session.getAttribute("userId").toString();
      String userName = session.getAttribute("userName").toString();
      String orgName = session.getAttribute("orgName").toString();
      logBD.log(userId, userName, orgName, moduleCode, "", startDate, startDate, "2", form.getMenuName(), request.getRemoteAddr(), session.getAttribute("domainId").toString());
      return actionMapping.findForward("modi");
    } 
    if (DEL_ALL.equals(action)) {
      deleteMenuRight("", bd);
      bd.delAllCustmizeMenus(domainId.toString());
      (new ModuleMenuAllService()).delAllCustomizeMenuSet(domainId
          .toString());
      return actionMapping.findForward("delover");
    } 
    if (DEL_BATCH.equals(action)) {
      try {
        String ids = request.getParameter("ids");
        String menuName = bd.getMenuNameByIds(ids);
        if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
          if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit()) {
            String[] idArr = ids.split(",");
            for (int i = 0; i < idArr.length; i++) {
              ModuleMenuPO po = new ModuleMenuPO();
              bd.autoAudit(po, "delete", Long.valueOf(idArr[i]), request);
            } 
          } 
          deleteMenuRight(ids, bd);
          bd.delBatchCustmizeMenus(domainId.toString(), ids);
          if (!(new ModuleMenuAllService()).delBatchMenuSet(domainId.toString(), ids))
            request.setAttribute("errs", "当前菜单包含子菜单！"); 
        } 
        if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0) {
          String[] idArr = ids.split(",");
          for (int i = 0; i < idArr.length; i++) {
            ModuleMenuPO po = new ModuleMenuPO();
            bd.audit(po, "delete", Long.valueOf(idArr[i]), request);
            request.setAttribute("flag", "foraudit");
          } 
        } 
        LogBD logBD = new LogBD();
        Date startDate = new Date();
        String moduleCode = "system_custommenu";
        String userId = session.getAttribute("userId").toString();
        String userName = session.getAttribute("userName").toString();
        String orgName = session.getAttribute("orgName").toString();
        logBD.log(userId, userName, orgName, moduleCode, "", startDate, startDate, "3", menuName, request.getRemoteAddr(), session.getAttribute("domainId").toString());
      } catch (Exception ex) {
        request.setAttribute("errs", "当前菜单包含子菜单！");
      } 
      return actionMapping.findForward("delover");
    } 
    if (LOAD_CHANNEL.equals(action)) {
      String userChannelId = request.getParameter("userChannelId");
      form.setChannelId(Long.valueOf(userChannelId));
      ChannelBD channelBD = new ChannelBD();
      UserChannelPO userChannelPO = channelBD.getUserChannel(
          userChannelId);
      if (userChannelPO != null) {
        form.setChannel(userChannelPO.getUserChannelName());
        form.setChannelOrder(userChannelPO.getUserChannelOrder());
      } else {
        form.setChannel("");
        form.setChannelOrder("");
      } 
      return actionMapping.findForward("channelLoad");
    } 
    if (MODI_CHANNEL.equals(action)) {
      ChannelBD channelBD = new ChannelBD();
      channelBD.updateUserChannel(form.getChannelId().toString(), 
          form.getChannel(), 
          form.getChannelOrder());
      return actionMapping.findForward("channelModi");
    } 
    if (DEL_CHANNEL.equals(action)) {
      String userChannelId = request.getParameter("userChannelId");
      ChannelBD channelBD = new ChannelBD();
      channelBD.deleteUserChannel(userChannelId);
      return actionMapping.findForward("channelDel");
    } 
    return null;
  }
  
  private String confirmNewLevel(String level, String oriLevel, String menuBlone, int stepDif) {
    if (level != null && level.length() > 0) {
      int step = 0;
      int pStep = 0;
      int i;
      for (i = 0; i < level.length(); i++) {
        if (level.charAt(i) == '-')
          step++; 
      } 
      for (i = 0; i < menuBlone.length(); i++) {
        if (menuBlone.charAt(i) == '-')
          pStep++; 
      } 
      if (stepDif == 0) {
        stepDif = step - pStep;
        if (step <= pStep);
      } 
      return "";
    } 
    return "";
  }
  
  private String plusSurfNum(String level) {
    if (level != null && level.length() > 0) {
      StringTokenizer st = new StringTokenizer(level, "-");
      int scoll = 0;
      boolean isCustRoot = false;
      String conStr = "";
      while (st.hasMoreTokens()) {
        String tok = st.nextToken();
        if (scoll == 0 || "_".equals(tok)) {
          conStr = String.valueOf(conStr) + tok;
        } else {
          conStr = String.valueOf(conStr) + "-" + (Long.valueOf(tok).longValue() + 1L);
        } 
        scoll++;
      } 
      return conStr;
    } 
    return "";
  }
  
  private boolean saveSearchSegment(String searchSegment, HttpServletRequest request) {
    return false;
  }
  
  private boolean saveListSegment(String listSegment) {
    return false;
  }
  
  private void injectForm(ModuleMenuPO po, ModuleMenuActionForm form) {
    form.setId(po.getId());
    form.setMenuName(po.getMenuName());
    form.setMenuBlone(po.getMenuBlone());
    form.setMenuBloneHId(po.getMenuBlone());
    form.setMenuLocation(po.getMenuLocation());
    form.setMenuCount(po.getMenuCount());
    form.setMenuScope(po.getMenuScope());
    form.setMenuAction(po.getMenuAction());
    form.setMenuActionParams1(po.getMenuActionParams1());
    form.setMenuActionParams2(po.getMenuActionParams2());
    form.setMenuActionParams3(po.getMenuActionParams3());
    form.setMenuActionParams4(po.getMenuActionParams4());
    form.setMenuActionParams4Value(po.getMenuActionParams4Value());
    form.setMenuListTableMap(po.getMenuListTableMap());
    form.setMenuSubTableMap(po.getMenuSubTableMap());
    form.setMenuMaintenanceTableMap(po.getMenuMaintenanceTableMap());
    form.setMenuMaintenanceSubTableMap(po.getMenuMaintenanceSubTableMap());
    form.setMenuMaintenanceSubTableName(po.getMenuMaintenanceSubTableName());
    form.setMenuListDisplayElements(po.getMenuListDisplayElements());
    form.setMenuListQueryConditionElements(po
        .getMenuListQueryConditionElements());
    form.setMenuDefQueryCondition(po.getMenuDefQueryCondition());
    form.setMenuDefQueryOrder(po.getMenuDefQueryOrder());
    form.setMenuSearchBound(po.getMenuSearchBound());
    form.setMenuAccesss(po.getMenuAccess());
    form.setMenuMessageSend(po.getMenuMessageSend());
    form.setMenuIsValide(po.getMenuIsValide());
    form.setMenuOpenStyle(po.getMenuOpenStyle());
    form.setMenuFileLink(po.getMenuFileLink());
    form.setMenuHtmlLink(po.getMenuHtmlLink());
    form.setMenuRefFlow(po.getMenuRefFlow());
    form.setMenuStartFlow(po.getMenuStartFlow());
    if (po.getMenuRelationRefFlow() != null && 
      po.getMenuRelationRefFlow().length() > 0) {
      form.setMenuRelationRefFlow(po.getMenuRelationRefFlow().substring(0, 
            po.getMenuRelationRefFlow().lastIndexOf("|")));
    } else {
      po.setMenuRelationRefFlow("");
    } 
    form.setMenuRelationRefFlow2(po.getMenuRelationRefFlow().substring(po
          .getMenuRelationRefFlow().lastIndexOf("|") + 1, 
          po.getMenuRelationRefFlow().length()));
    form.setMenuLevel(po.getMenuLevel());
    form.setMenuBlone(po.getMenuBlone());
    form.setRecordOperattion(po.getRecordOperattion());
    form.setOperationType(po.getOperationType());
    form.setOperationImg(po.getOperationImg());
    form.setOperationComponert(po.getOperationComponert());
    form.setZorderLocation(po.getMenuBlone().toString());
    form.setZorderLacationOld(po.getMenuBlone().toString());
    form.setParentOrder(po.getParentOrder());
    form.setMenuAdd(po.getMenuAdd());
    form.setMenuDel(po.getMenuDel());
    form.setMenuImp(po.getMenuImp());
    form.setMenuExp(po.getMenuExp());
    form.setMenuQuery(po.getMenuQuery());
  }
  
  private void setMenuOrderRule(ModuleMenuPO pPo, ModuleMenuPO po, String domainId, ModuleMenuService bd, ModuleMenuActionForm form) throws Exception {
    String maxLevel = bd.getMenuMaxCount(domainId, 
        form.getMenuBlone().toString());
    long level = -1L;
    if (maxLevel != null && maxLevel.length() > 0 && 
      maxLevel.indexOf("-") > 0)
      level = 
        Long.valueOf(maxLevel
          .substring(maxLevel.lastIndexOf("-") + 1, 
            maxLevel.length())).longValue(); 
    if (pPo != null) {
      po.setMenuBlone(form.getMenuBlone());
      if (pPo.getId().toString().equals(form.getMenuBlone().toString()) && !pPo.getId().equals(pPo.getMenuBlone()) && pPo.getMenuCount() != 9) {
        if (level < 0L) {
          po.setMenuLevel(String.valueOf(pPo.getMenuLevel()) + "-5000000");
        } else if (pPo.getMenuLevel().indexOf("-") < 0) {
          po.setMenuLevel(String.valueOf(pPo.getMenuLevel()) + "-" + 
              String.valueOf((level + 5000000L) / 2L));
        } else {
          po.setMenuLevel(String.valueOf(maxLevel.substring(0, 
                  maxLevel.lastIndexOf("-") + 1)) + 
              String.valueOf((level + 5000000L) / 2L));
        } 
      } else {
        po.setMenuLevel(String.valueOf(pPo.getMenuLevel()) + "-5000000");
      } 
    } else {
      po.setMenuBlone(form.getMenuBlone());
      po.setMenuLevel((level < 0L) ? (form.getMenuBlone() + "-5000000") : (
          String.valueOf(maxLevel.substring(0, maxLevel.lastIndexOf("-") + 1)) + ((
          level + 5000000L) / 2L)));
    } 
    if (pPo.getMenuCount() == 8 || pPo.getMenuCount() == 9) {
      po.setMenuLocation(pPo.getId());
    } else {
      po.setMenuLocation(form.getMenuLocation());
    } 
  }
  
  private void injectPo(ModuleMenuPO po, Long domainId, ModuleMenuActionForm form, String curUserId, String mode) {
    if (po.getMenuBlone() == null)
      po.setMenuBlone(form.getMenuBlone()); 
    if (po.getMenuLevel() == null)
      po.setMenuLevel(form.getMenuLevel()); 
    po.setMenuName(form.getMenuName());
    String menuViewId = "";
    String menuScopeTemp = form.getMenuScope();
    if (menuScopeTemp.lastIndexOf("-*") > 0) {
      po.setMenuScope(form.getMenuScope().substring(0, form.getMenuScope().lastIndexOf("-*")));
      menuViewId = form.getMenuScope().substring(form.getMenuScope().lastIndexOf("-*") + 1, form.getMenuScope().length());
    } else if (menuScopeTemp.lastIndexOf("-$") > 0) {
      po.setMenuScope(form.getMenuScope().substring(0, form.getMenuScope().lastIndexOf("-$")));
      menuViewId = form.getMenuScope().substring(form.getMenuScope().lastIndexOf("-$") + 1, form.getMenuScope().length());
    } else if (menuScopeTemp.lastIndexOf("-@") > 0) {
      po.setMenuScope(form.getMenuScope().substring(0, form.getMenuScope().lastIndexOf("-@")));
      menuViewId = form.getMenuScope().substring(form.getMenuScope().lastIndexOf("-@") + 1, form.getMenuScope().length());
    } else {
      menuViewId = form.getMenuScope();
    } 
    po.setMenuViewUser(StringSplit.splitWith(menuViewId, "$", "*@"));
    po.setMenuViewGroup(StringSplit.splitWith(menuViewId, "@", "$*"));
    po.setMenuViewOrg(StringSplit.splitWith(menuViewId, "*", "@$"));
    po.setMenuAction(form.getMenuAction());
    po.setMenuActionParams1(form.getMenuActionParams1());
    po.setMenuActionParams2(form.getMenuActionParams2());
    po.setMenuActionParams3(form.getMenuActionParams3());
    po.setMenuActionParams4(form.getMenuActionParams4());
    po.setMenuActionParams4Value(form.getMenuActionParams4Value());
    po.setMenuListTableMap(form.getMenuListTableMap());
    po.setMenuListTableName(form.getMenuListTableName());
    po.setMenuMaintenanceTableMap(form.getMenuMaintenanceTableMap());
    po.setMenuMaintenanceSubTableName(form.getMenuMaintenanceSubTableName());
    po.setMenuListDisplayElements(form.getMenuListDisplayElements());
    po.setMenuListQueryConditionElements(form.getMenuListQueryConditionElements());
    po.setMenuDefQueryCondition(form.getMenuDefQueryCondition());
    po.setMenuDefQueryOrder(form.getMenuDefQueryOrder());
    po.setMenuSearchBound(form.getMenuSearchBound());
    po.setMenuAccess(form.getMenuAccesss());
    po.setMenuOpenStyle(form.getMenuOpenStyle());
    po.setMenuIsValide(form.getMenuIsValide());
    po.setMenuMessageSend(form.getMenuMessageSend());
    po.setMenuFileLink(form.getMenuFileLink());
    po.setMenuHtmlLink(form.getMenuHtmlLink());
    po.setMenuStartFlow(form.getMenuStartFlow());
    po.setMenuRefFlow(form.getMenuRefFlow());
    po.setMenuRelationRefFlow(String.valueOf(form.getMenuRelationRefFlow()) + "|" + form.getMenuRelationRefFlow2());
    po.setRecordOperattion(form.getRecordOperattion());
    po.setOperationType(form.getOperationType());
    po.setOperationImg(form.getOperationImg());
    po.setMenuCount(form.getMenuCount());
    po.setOperationComponert(form.getOperationComponert());
    po.setDomainId(domainId);
    po.setMenuAdd(form.getMenuAdd());
    po.setMenuDel(form.getMenuDel());
    po.setMenuImp(form.getMenuImp());
    po.setMenuExp(form.getMenuExp());
    po.setMenuQuery(form.getMenuQuery());
  }
  
  private List appendSpace(List<Object[]> list) {
    String space = "";
    List<Object[]> retList = new ArrayList();
    int level = 0;
    if (list != null && list.size() > 0)
      for (int i = 0; i < list.size(); i++) {
        Object[] obj = list.get(i);
        space = obj[2].toString();
        level = Integer.parseInt(space.substring(space.indexOf("-") + 1, 
              space.length()));
        space = "";
        for (int j = 1; j < level; j++)
          space = String.valueOf(space) + "    "; 
        obj[0] = obj[0];
        obj[1] = String.valueOf(space) + obj[1].toString();
        retList.add(obj);
      }  
    return retList;
  }
  
  private SystemMenuPO setMenuSetPo(ModuleMenuActionForm form, String domainId, HttpServletRequest request) {
    if (form != null) {
      String menuViewId;
      SystemMenuPO po = new SystemMenuPO();
      if (form.getMenuLocation() != null && 
        form.getMenuLocation().longValue() > 0L) {
        po.setMenuId(form.getMenuLocation());
        po.setLeftURL(
            "/jsoa/ModuleMenuAction.do?menuid=" + 
            form.getMenuLocation());
        po.setMenuIdString(form.getMenuLocation().toString());
      } 
      setDefMenuRightUrl(form, domainId, po, request);
      po.setIsSystemInit(Integer.valueOf("0"));
      po.setInUse(new Integer(form.getMenuIsValide()));
      String menuScopeTemp = form.getMenuScope();
      if (menuScopeTemp.lastIndexOf("-*") > 0) {
        po.setMenuView(form.getMenuScope().substring(0, form.getMenuScope().lastIndexOf("-*")));
        menuViewId = form.getMenuScope().substring(form.getMenuScope().lastIndexOf("-*") + 1, form.getMenuScope().length());
      } else if (menuScopeTemp.lastIndexOf("-$") > 0) {
        po.setMenuView(form.getMenuScope().substring(0, form.getMenuScope().lastIndexOf("-$")));
        menuViewId = form.getMenuScope().substring(form.getMenuScope().lastIndexOf("-$") + 1, form.getMenuScope().length());
      } else {
        po.setMenuView(form.getMenuScope().substring(0, form.getMenuScope().lastIndexOf("-@")));
        menuViewId = form.getMenuScope().substring(form.getMenuScope().lastIndexOf("-@") + 1, form.getMenuScope().length());
      } 
      po.setMenuViewUser(StringSplit.splitWith(menuViewId, "$", "*@"));
      po.setMenuViewGroup(StringSplit.splitWith(menuViewId, "@", "$*"));
      po.setMenuViewOrg(StringSplit.splitWith(menuViewId, "*", "@$"));
      po.setMenuName(form.getMenuName());
      po.setMenuLevel("0");
      po.setMenuParent("0");
      po.setDeskTop1("0");
      po.setDeskTop2("0");
      po.setDomainId(domainId);
      po.setMenuOrder(String.valueOf(form.getParentOrder()));
      return po;
    } 
    return null;
  }
  
  private void setDefMenuRightUrl(ModuleMenuActionForm form, String domainId, SystemMenuPO po, HttpServletRequest request) {
    if (form.getDefHref() != null && form.getDefHref().length() > 0)
      if ("-1".equals(form.getDefHref())) {
        po.setMenuURL((form.getMenuAction().indexOf("&") > 0 || 
            !"-1".equals(form.getDefHref())) ? (
            String.valueOf(form.getMenuAction()) + "&d=" + form.getDefHref()) : (
            String.valueOf(form.getMenuAction()) + "?d=" + form.getDefHref()));
        po.setRightURL((form.getMenuAction().indexOf("&") > 0 || 
            !"-1".equals(form.getDefHref())) ? (
            String.valueOf(form.getMenuAction()) + "&d=" + form.getDefHref()) : (
            String.valueOf(form.getMenuAction()) + "?d=" + form.getDefHref()));
      } else if (form.getDefHref().indexOf("-") <= 0) {
        ModuleMenuAllService setBd = new ModuleMenuAllService();
        SystemMenuPO setPo = setBd.loadMenuSet(form.getDefHref());
        if (setPo != null) {
          po.setRightURL(String.valueOf(setPo.getRightURL()) + ((
              form.getMenuAction().indexOf("&") > 0 || 
              !"-1".equals(form.getDefHref())) ? (
              String.valueOf(form.getMenuAction()) + "&d=" + 
              form.getDefHref()) : (
              String.valueOf(form.getMenuAction()) + "?d=" + 
              form.getDefHref())));
          po.setMenuURL(String.valueOf(setPo.getRightURL()) + (
              (form.getMenuAction().indexOf("&") > 0 || 
              !"-1".equals(form.getDefHref())) ? (
              String.valueOf(form.getMenuAction()) + "&d=" + 
              form.getDefHref()) : (
              String.valueOf(form.getMenuAction()) + "?d=" + 
              form.getDefHref())));
        } 
      } else {
        ModuleMenuService bd = new ModuleMenuService();
        ModuleMenuPO ppo = 
          bd.loadMenuConfig(domainId, 
            form.getDefHref()).get(
            0);
        if (ppo != null) {
          po.setMenuURL(String.valueOf(bd.confirmAction(ppo, request)) + ((
              form.getMenuAction().indexOf("&") > 0 || 
              !"-1".equals(form.getDefHref())) ? (
              String.valueOf(form.getMenuAction()) + "&d=" + 
              form.getDefHref()) : (
              String.valueOf(form.getMenuAction()) + "?d=" + 
              form.getDefHref())));
          po.setRightURL(String.valueOf(bd.confirmAction(ppo, request)) + (
              (form.getMenuAction().indexOf("&") > 0 || 
              !"-1".equals(form.getDefHref())) ? (
              String.valueOf(form.getMenuAction()) + "&d=" + 
              form.getDefHref()) : (
              String.valueOf(form.getMenuAction()) + "?d=" + 
              form.getDefHref())));
        } 
      }  
  }
  
  private void menuZorder(ModuleMenuPO po, HttpServletRequest request, ModuleMenuActionForm form, String domainId, String flag) {
    String zorder = form.getZorder();
    String locMenuId = "";
    if ("0".equals(flag)) {
      locMenuId = form.getMenuLocation().toString();
    } else {
      locMenuId = form.getZorderLocation().toString();
    } 
    ModuleMenuService bd = new ModuleMenuService();
    String preMenuLevel = "";
    String surMenuLevel = "";
    String tmpLevel = "";
    ListItem preItem = null;
    ListItem choItem = null;
    ListItem surItem = null;
    String retNum = "";
    Long preNum = new Long(0L);
    Long surNum = new Long(0L);
    List<ListItem> list = 
      bd.getAllSubMenusForOrder(domainId, po.getMenuBlone().toString());
    if (list != null && list.size() > 0) {
      for (int i = 0; i < list.size(); i++) {
        ListItem item = list.get(i);
        if (item.getField2().indexOf("-") > 0)
          if (item.getId().equals(locMenuId)) {
            choItem = item;
            if ("1".equals(zorder) && i + 1 < list.size()) {
              surItem = list.get(i + 1);
              break;
            } 
            if (i == 0) {
              preItem = null;
            } else {
              preItem = list.get(i - 1);
            } 
          }  
      } 
      if ("0".equals(zorder) && preItem != null) {
        preNum = Long.valueOf(preItem.getField2()
            .substring(preItem.getField2()
              .lastIndexOf(
                "-"), preItem.getField2().length()));
        surNum = Long.valueOf(choItem.getField2()
            .substring(choItem.getField2()
              .lastIndexOf(
                "-"), choItem.getField2().length()));
        retNum = (new StringBuilder(String.valueOf((surNum.intValue() + preNum.intValue()) / 2 + 1))).toString();
      } else if ("0".equals(zorder) && preItem == null) {
        surNum = Long.valueOf(choItem.getField2()
            .substring(choItem.getField2()
              .lastIndexOf(
                "-"), choItem.getField2().length()));
        retNum = (new StringBuilder(String.valueOf(surNum.longValue() + 90000L + 1L))).toString();
      } else if ("1".equals(zorder) && surItem != null) {
        preNum = Long.valueOf(choItem.getField2()
            .substring(choItem.getField2()
              .lastIndexOf(
                "-"), choItem.getField2().length()));
        surNum = Long.valueOf(surItem.getField2()
            .substring(surItem.getField2()
              .lastIndexOf(
                "-"), surItem.getField2().length()));
        retNum = (new StringBuilder(String.valueOf((surNum.longValue() + preNum.longValue()) / 2L + 1L))).toString();
      } else if ("1".equals(zorder) && surItem == null) {
        surNum = Long.valueOf(choItem.getField2()
            .substring(choItem.getField2()
              .lastIndexOf(
                "-"), choItem.getField2().length()));
        retNum = (new StringBuilder(String.valueOf(surNum.intValue() - 90000 + 1))).toString();
      } 
      po.setMenuLevel(String.valueOf(po.getMenuLevel().substring(0, 
              po.getMenuLevel().lastIndexOf("-"))) + String.valueOf(retNum));
    } 
  }
  
  private String doubleFormat(double d) {
    DecimalFormat df = new DecimalFormat("0.####");
    return df.format(d);
  }
  
  private void delMenuListRight(String menuId, String domainId, ModuleMenuService bd) {
    bd.delMenuRights(menuId);
  }
  
  private void addMenuListRight(Long menuId, String menuName, Long domainId, ModuleMenuService bd) {
    if (menuId != null && menuId.longValue() > 0L) {
      ArrayList<HashMap<Object, Object>> list = new ArrayList();
      HashMap<Object, Object> subMap1 = new HashMap<Object, Object>();
      subMap1.put("rightName", "查看");
      subMap1.put("rightType", menuName);
      subMap1.put("rightClass", menuName);
      subMap1.put("rightCode", "99-" + menuId + "-01");
      subMap1.put("domainId", domainId);
      list.add(subMap1);
      HashMap<Object, Object> subMap2 = new HashMap<Object, Object>();
      subMap2.put("rightName", "新增");
      subMap2.put("rightType", menuName);
      subMap2.put("rightClass", menuName);
      subMap2.put("rightCode", "99-" + menuId + "-02");
      subMap2.put("domainId", domainId);
      list.add(subMap2);
      HashMap<Object, Object> subMap3 = new HashMap<Object, Object>();
      subMap3.put("rightName", "维护");
      subMap3.put("rightType", menuName);
      subMap3.put("rightClass", menuName);
      subMap3.put("rightCode", "99-" + menuId + "-03");
      subMap3.put("domainId", domainId);
      list.add(subMap3);
      HashMap<Object, Object> subMap4 = new HashMap<Object, Object>();
      subMap4.put("rightName", "导出");
      subMap4.put("rightType", menuName);
      subMap4.put("rightClass", menuName);
      subMap4.put("rightCode", "99-" + menuId + "-04");
      subMap4.put("domainId", domainId);
      list.add(subMap4);
      HashMap<Object, Object> subMap5 = new HashMap<Object, Object>();
      subMap5.put("rightName", "删除");
      subMap5.put("rightType", menuName);
      subMap5.put("rightClass", menuName);
      subMap5.put("rightCode", "99-" + menuId + "-05");
      subMap5.put("domainId", domainId);
      list.add(subMap5);
      bd.addMenuRights(list);
    } 
  }
  
  private void deleteMenuRight(String ids, ModuleMenuService bd) {
    if (ids != null)
      if (ids.length() > 0) {
        StringTokenizer st = new StringTokenizer(ids, ",");
        String id = " ";
        while (st.hasMoreTokens()) {
          id = st.nextToken();
          if (id != null && id.length() > 0)
            bd.deleteMenuRights(id); 
        } 
      } else {
        bd.deleteMenuRights("");
      }  
  }
  
  public void list(HttpServletRequest httpServletRequest, String viewSql, String fromSql, String whereSql) {
    int pageSize = 150;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSql, fromSql, whereSql);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "action,userChannelName");
    httpServletRequest.setAttribute("userChannel", list);
  }
  
  private String[][] getQueryFiled(String tablename) throws SQLException, Exception {
    DbOpt dbopt = new DbOpt();
    String sql = "select t.table_id,t.table_desname,v.field_name,v.field_desname from ttable t,tfield v  where v.field_table=t.table_id and t.table_name='" + 
      tablename + "'";
    String[][] result = dbopt.executeQueryToStrArr2(sql, 4);
    dbopt.close();
    return result;
  }
  
  public static String replaceAll(String str, String befor, String after) {
    StringBuffer buffer = new StringBuffer();
    int i = 0, preIndex = 0, lastIndex = 0;
    int strLen = str.length();
    int litLen = befor.length();
    if (str.endsWith(befor))
      str = String.valueOf(str.substring(0, strLen - litLen)) + after; 
    if (str.startsWith(befor))
      str = String.valueOf(after) + str.substring(litLen); 
    preIndex = str.indexOf(befor);
    String tmp = "";
    while (preIndex >= 0) {
      if (preIndex == 0) {
        if (str.length() > 1) {
          str = str.substring(befor.length(), str.length());
          preIndex = str.indexOf(befor);
        } else {
          preIndex = -1;
        } 
        buffer.append(after);
      } else {
        if (i == 0) {
          buffer.append(str.substring(0, preIndex));
        } else {
          buffer.append(after).append(str.substring(0, preIndex));
        } 
        str = str.substring(preIndex + befor.length());
        preIndex = str.indexOf(befor);
      } 
      i++;
    } 
    if (!"".equals(str)) {
      if (i > 0)
        buffer.append(after); 
      buffer.append(str);
    } 
    return buffer.toString();
  }
  
  public List getOrderAndReader(List<Object[]> list) {
    if (list != null) {
      List arry = new ArrayList();
      int i = 0, j = 0;
      Connection conn = null;
      Statement stmt = null;
      try {
        conn = (new DataSourceBase()).getDataSource().getConnection();
        stmt = conn.createStatement();
        for (i = 0; i < list.size(); i++) {
          Object[] objTmp = list.get(i);
          Object[] obj = new Object[9];
          for (j = 0; j < 8; j++)
            obj[j] = objTmp[j]; 
          if ("9".equals(obj[6].toString()) || 
            "8".equals(obj[6].toString())) {
            ResultSet rs = stmt.executeQuery("select menuOrder,menuview from menu_sys where menu_id=" + obj[5]);
            if (rs.next()) {
              obj[8] = rs.getString(1);
              obj[7] = rs.getString(2);
            } 
            rs.close();
          } else {
            obj[8] = "";
          } 
          list.set(i, obj);
        } 
        stmt.close();
        conn.close();
      } catch (Exception ex) {
        if (conn != null)
          try {
            conn.close();
          } catch (Exception er) {
            er.printStackTrace();
          }  
        ex.printStackTrace();
      } 
    } 
    return list;
  }
}
