package com.js.system.action.organizationmanager;

import com.js.system.service.groupmanager.GroupBD;
import com.js.system.service.organizationmanager.OrganizationBD;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ValidOrgTreeAction extends Action {
  private static Logger logger = Logger.getLogger("日常操作");
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    String action = httpServletRequest.getParameter("action");
    String single = httpServletRequest.getParameter("single");
    String group = httpServletRequest.getParameter("group");
    OrganizationBD organizationBD = new OrganizationBD();
    List validOrgs = organizationBD.getValidOrgs();
    httpServletRequest.setAttribute("validOrgs", validOrgs);
    List groupList = null;
    if ("yes".equals(group)) {
      GroupBD groupBD = new GroupBD();
      groupList = groupBD.select();
      httpServletRequest.setAttribute("groupList", groupList);
      httpServletRequest.setAttribute("group", group);
    } 
    if ("search".equals(httpServletRequest.getParameter("flag"))) {
      httpServletRequest.setAttribute("single", single);
      if ("org".equals(action))
        return actionMapping.findForward("org"); 
      return actionMapping.findForward("search");
    } 
    httpServletRequest.setAttribute("action", action);
    httpServletRequest.setAttribute("single", single);
    return actionMapping.findForward("success");
  }
}
