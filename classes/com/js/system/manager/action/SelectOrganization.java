package com.js.system.manager.action;

import com.js.system.manager.service.ManagerService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SelectOrganization extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
    HttpSession session = request.getSession(true);
    String range = request.getParameter("range");
    String single = request.getParameter("single");
    String select = request.getParameter("select");
    String show = request.getParameter("show");
    String email = request.getParameter("email");
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    String currentOrg = request.getParameter("currentOrg");
    ManagerService managerBD = new ManagerService();
    Map map = managerBD.getOrgAndGroupByRange(range, show, session.getAttribute("orgIdString").toString(), session.getAttribute("userId").toString(), currentOrg, domainId);
    request.setAttribute("organizations", map.get("org"));
    if (show.indexOf("group") > 0)
      request.setAttribute("groups", map.get("group")); 
    request.setAttribute("single", single);
    request.setAttribute("select", select);
    request.setAttribute("show", show);
    request.setAttribute("email", email);
    if ("publicPerson".equals(show))
      request.setAttribute("publicPerson", map.get("publicPerson")); 
    if ("privatePerson".equals(show))
      request.setAttribute("privatePerson", map.get("privatePerson")); 
    if ("client".equals(show))
      return actionMapping.findForward("client"); 
    if ("publicPerson".equals(show) || "privatePerson".equals(show))
      return actionMapping.findForward("success2"); 
    return actionMapping.findForward("success");
  }
}
