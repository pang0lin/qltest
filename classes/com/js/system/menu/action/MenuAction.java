package com.js.system.menu.action;

import com.js.oa.module.service.ModuleMenuService;
import com.js.oa.security.log.service.LogBD;
import com.js.system.menu.po.MenuSetPO;
import com.js.system.menu.service.MenuBD;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.FillBean;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.hibernate.HibernateException;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MenuAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws HibernateException, SQLException {
    HttpSession session = request.getSession(true);
    String action = request.getParameter("action");
    String domainId = session.getAttribute("domainId").toString();
    MenuActionForm form = (MenuActionForm)actionForm;
    MenuBD bd = new MenuBD();
    if ("add".equals(action)) {
      List list = bd.getTopMenu(session.getAttribute("domainId").toString());
      request.setAttribute("parentMenu", list);
      return actionMapping.findForward("add");
    } 
    if ("save".equals(action)) {
      MenuSetPO po = (MenuSetPO)FillBean.transformOneToOne(form, MenuSetPO.class);
      if ("1".equals(form.getDeskTop1())) {
        po.setDeskTop1("1");
      } else {
        po.setDeskTop1("0");
      } 
      if ("1".equals(form.getDeskTop2())) {
        po.setDeskTop2("1");
      } else {
        po.setDeskTop2("0");
      } 
      po.setIsSystemInit(Integer.valueOf("0"));
      if (request.getParameter("inUse") != null) {
        po.setInUse(Integer.valueOf(request.getParameter("inUse")));
      } else {
        po.setInUse(Integer.valueOf("0"));
      } 
      String menuView = form.getMenuView();
      String menuViewId = form.getMenuViewId();
      bd.add(po, menuViewId, menuView, session.getAttribute("domainId").toString());
      request.setAttribute("topReload", "reload");
    } else {
      if ("load".equals(action)) {
        Long menuId = Long.valueOf(request.getParameter("modifyId"));
        Map result = bd.loadMenu(menuId);
        MenuSetPO po = (MenuSetPO)result.get("menuSetPO");
        form.setMenuId(menuId);
        form.setMenuURL(po.getMenuURL());
        form.setMenuView(po.getMenuView());
        form.setMenuName(po.getMenuName());
        form.setMenuParent(po.getMenuParent());
        form.setDeskTop1(po.getDeskTop1());
        form.setDeskTop2(po.getDeskTop2());
        String menuViewId = "";
        request.setAttribute("isSystemInit", po.getIsSystemInit());
        request.setAttribute("inUse", po.getInUse());
        menuViewId = String.valueOf(menuViewId) + ((po.getMenuViewUser() != null) ? po.getMenuViewUser() : "");
        menuViewId = String.valueOf(menuViewId) + ((po.getMenuViewOrg() != null) ? po.getMenuViewOrg() : "");
        menuViewId = String.valueOf(menuViewId) + ((po.getMenuViewGroup() != null) ? po.getMenuViewGroup() : "");
        form.setMenuViewId(menuViewId);
        request.setAttribute("menuName", po.getMenuName());
        List list = bd.getTopMenu(session.getAttribute("domainId").toString());
        request.setAttribute("parentMenu", list);
        request.setAttribute("menuOrder", po.getMenuOrder());
        return actionMapping.findForward("load");
      } 
      if ("update".equals(action)) {
        MenuSetPO modiPO = (MenuSetPO)FillBean.transformOneToOne(form, MenuSetPO.class);
        if ("1".equals(form.getDeskTop1())) {
          modiPO.setDeskTop1("1");
        } else {
          modiPO.setDeskTop1("0");
        } 
        if ("1".equals(form.getDeskTop2())) {
          modiPO.setDeskTop2("1");
        } else {
          modiPO.setDeskTop2("0");
        } 
        Long menuId = form.getMenuId();
        String menuView = form.getMenuView();
        String menuViewId = form.getMenuViewId();
        if (request.getParameter("inUse") != null) {
          modiPO.setInUse(Integer.valueOf(request.getParameter("inUse")));
        } else {
          modiPO.setInUse(Integer.valueOf("0"));
        } 
        modiPO.setMenuOrder(request.getParameter("menuOrder"));
        bd.update(modiPO, menuId, menuViewId, menuView, session.getAttribute("domainId").toString());
        ModuleMenuService cbd = new ModuleMenuService();
        String custMenuId = request.getParameter("custMenuId");
        cbd.setMenuDisplay(custMenuId, "", menuId.toString(), "9", domainId, modiPO.getInUse().intValue());
        LogBD logBD = new LogBD();
        Date startDate = new Date();
        String moduleCode = "system_custommenu";
        String userId = session.getAttribute("userId").toString();
        String userName = session.getAttribute("userName").toString();
        String orgName = session.getAttribute("orgName").toString();
        logBD.log(userId, userName, orgName, moduleCode, "", startDate, startDate, "2", form.getMenuName(), request.getRemoteAddr(), session.getAttribute("domainId").toString());
        request.setAttribute("topReload", "reload");
      } else if ("del".equals(action)) {
        if (request.getParameter("id") != null) {
          Long delId = new Long(request.getParameter("id"));
          bd.delete(delId);
        } 
        action = "list";
      } else {
        if ("new_setrole_update".equals(action)) {
          String numeIds = request.getParameter("ids");
          String inuse = request.getParameter("inuse");
          if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
            bd.setTotMenu1(numeIds, inuse);
            if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())
              try {
                String[] idsArr = numeIds.split(",");
                for (int i = 0; i < idsArr.length; i++) {
                  MenuSetPO po = new MenuSetPO();
                  po.setMenuId(Long.valueOf(idsArr[i]));
                  po.setInUse(Integer.valueOf(Integer.parseInt(inuse)));
                  bd.autoAudit(po, inuse.equals("1") ? "enable" : "disable", Long.valueOf(idsArr[i]), request);
                } 
              } catch (NumberFormatException e) {
                e.printStackTrace();
              } catch (Exception e) {
                e.printStackTrace();
              }  
          } 
          if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0)
            try {
              String[] idsArr = numeIds.split(",");
              for (int i = 0; i < idsArr.length; i++) {
                MenuSetPO po = new MenuSetPO();
                po.setMenuId(Long.valueOf(idsArr[i]));
                po.setInUse(Integer.valueOf(Integer.parseInt(inuse)));
                bd.audit(po, inuse.equals("1") ? "enable" : "disable", Long.valueOf(idsArr[i]), request);
              } 
              request.setAttribute("flag", "foraudit");
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }  
          List liseTopMenu = bd.getAllTopMenu();
          request.setAttribute("liseTopMenu", liseTopMenu);
          return actionMapping.findForward("new_setrole");
        } 
        if ("new_setrole".equals(action)) {
          List liseTopMenu = bd.getAllTopMenu();
          request.setAttribute("liseTopMenu", liseTopMenu);
          return actionMapping.findForward("new_setrole");
        } 
        if ("new_load".equals(action)) {
          Long menuId = Long.valueOf(request.getParameter("modifyId"));
          Map result = bd.loadMenu(menuId);
          MenuSetPO po = (MenuSetPO)result.get("menuSetPO");
          request.setAttribute("menuSetPO", po);
          return actionMapping.findForward("load");
        } 
        if ("new_update".equals(action)) {
          String id = request.getParameter("menuId");
          String inuse = request.getParameter("inuse");
          String userOrgGroup = request.getParameter("userOrgGroup");
          String classUserName = request.getParameter("classUserName");
          String MenuName = request.getParameter("menu_name");
          String menuOrder = request.getParameter("menu_order");
          if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
            bd.setTotMenu(id, inuse, userOrgGroup, classUserName, MenuName, menuOrder);
            if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())
              try {
                MenuSetPO menuSetPO = new MenuSetPO();
                menuSetPO.setMenuId(Long.valueOf(id));
                menuSetPO.setInUse(Integer.valueOf(Integer.parseInt(inuse)));
                menuSetPO.setUserOrgGroup(userOrgGroup);
                menuSetPO.setMenuView(classUserName);
                menuSetPO.setMenuName(MenuName);
                menuSetPO.setMenuOrder(menuOrder);
                bd.autoAudit(menuSetPO, "update", Long.valueOf(id), request);
              } catch (NumberFormatException e) {
                e.printStackTrace();
              } catch (Exception e) {
                e.printStackTrace();
              }  
          } 
          if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0)
            try {
              MenuSetPO menuSetPO = new MenuSetPO();
              menuSetPO.setMenuId(Long.valueOf(id));
              menuSetPO.setInUse(Integer.valueOf(Integer.parseInt(inuse)));
              menuSetPO.setUserOrgGroup(userOrgGroup);
              menuSetPO.setMenuView(classUserName);
              menuSetPO.setMenuName(MenuName);
              menuSetPO.setMenuOrder(menuOrder);
              bd.audit(menuSetPO, "update", Long.valueOf(id), request);
              request.setAttribute("flag", "foraudit");
            } catch (NumberFormatException e) {
              e.printStackTrace();
            } catch (Exception e) {
              e.printStackTrace();
            }  
          Map result = bd.loadMenu(Long.valueOf(id));
          MenuSetPO po = (MenuSetPO)result.get("menuSetPO");
          request.setAttribute("menuSetPO", po);
          return actionMapping.findForward("close");
        } 
      } 
    } 
    if ("list".equals(action)) {
      int pageSize = 15;
      int offset = 0;
      if (request.getParameter("pager.offset") != null)
        offset = Integer.parseInt(request.getParameter("pager.offset")); 
      int currentPage = offset / pageSize + 1;
      Page page = new Page(
          "po.menuId,po.menuName,po.menuView,po.menuLevel,po.isSystemInit, po.inUse", 
          "com.js.system.menu.po.MenuSetPO po", 
          "where po.domainId=" + session.getAttribute("domainId") + " order by po.menuIdString, po.menuId desc");
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      int recordCount = page.getRecordCount();
      if (offset >= recordCount) {
        offset = (recordCount - pageSize) / pageSize;
        currentPage = offset + 1;
        offset *= pageSize;
        page.setcurrentPage(currentPage);
        list = page.getResultList();
        recordCount = page.getRecordCount();
        request.setAttribute("pager.offset", String.valueOf(offset));
        request.setAttribute("pager.realCurrent", 
            String.valueOf(currentPage));
      } 
      request.setAttribute("menuList", list);
      request.setAttribute("recordCount", String.valueOf(recordCount));
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action");
      return actionMapping.findForward("list");
    } 
    return actionMapping.findForward("success");
  }
}
