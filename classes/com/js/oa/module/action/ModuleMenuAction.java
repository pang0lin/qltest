package com.js.oa.module.action;

import com.js.oa.module.service.ModuleMenuService;
import com.js.system.manager.service.ManagerService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ModuleMenuAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    Long domainId = (session.getAttribute("domainId") != null) ? Long.valueOf(session.getAttribute("domainId").toString()) : Long.valueOf("0");
    ModuleMenuService service = new ModuleMenuService();
    String menuId = request.getParameter("menuid");
    ManagerService managerBD = new ManagerService();
    String where = managerBD.getScopeFinalWhere(userId, orgId, orgIdString, 
        "po.menuViewUser", "po.menuViewOrg", "po.menuViewGroup");
    List mList = service.getMenuListByTopID(menuId, where);
    request.setAttribute("menuList", service.generateMenuItem(mList));
    return actionMapping.findForward("success");
  }
}
