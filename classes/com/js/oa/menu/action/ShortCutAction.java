package com.js.oa.menu.action;

import com.js.oa.menu.service.ShortCutBD;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShortCutAction extends Action {
  private ShortCutBD bd = null;
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String action = request.getParameter("action");
    this.bd = new ShortCutBD();
    if ("load".equals(action)) {
      try {
        this.bd.init();
        loadMenu(request, userId);
        this.bd.close();
      } catch (Exception ex) {
        this.bd.close();
        ex.printStackTrace();
      } 
      return actionMapping.findForward("load");
    } 
    if ("update".equals(action)) {
      try {
        this.bd.init();
        String[] shortCutMenu = request.getParameterValues("shortcutTargetSelect");
        String[] toolsMenu = request.getParameterValues("toolsTargetSelect");
        String portalMenu = request.getParameter("portalTargetSelect");
        boolean res = this.bd.updateUserMenu(userId, shortCutMenu, toolsMenu, portalMenu);
        loadMenu(request, userId);
        this.bd.close();
      } catch (Exception ex) {
        this.bd.close();
        ex.printStackTrace();
      } 
      return actionMapping.findForward("load");
    } 
    return actionMapping.findForward("error");
  }
  
  private void loadMenu(HttpServletRequest request, String userId) {
    request.setAttribute("portalMenuUser", this.bd.getPortalMenuUser(userId));
    request.setAttribute("shortCutMenu", this.bd.getShortChutMenu(userId));
    request.setAttribute("ShortChutMenuChecked", this.bd.getShortChutMenuChecked(userId));
    request.setAttribute("shortCutMenuUser", this.bd.getShortCutMenuUser(userId));
    request.setAttribute("toolsMenu", this.bd.getToolsMenu(userId));
    request.setAttribute("toolsMenuUser", this.bd.getToolsMenuUser(userId));
  }
}
