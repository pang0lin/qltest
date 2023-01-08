package com.js.system.action.organizationmanager;

import com.js.system.service.organizationmanager.OrganizationBD;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GenerateOrgTreeAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    OrganizationBD organizationBD = new OrganizationBD();
    String range = httpServletRequest.getParameter("range");
    httpServletRequest.setAttribute("validOrgs", organizationBD.getValidOrgsByRange(range, httpServletRequest.getSession(true).getAttribute("domainId").toString()));
    return actionMapping.findForward("success");
  }
}
