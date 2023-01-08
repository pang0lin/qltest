package com.js.oa.message.action;

import com.js.oa.message.service.MessageBD;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SelectSubOrgAndUserMsg extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    String select = httpServletRequest.getParameter("select");
    String show = httpServletRequest.getParameter("show");
    String single = httpServletRequest.getParameter("single");
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    httpServletRequest.setAttribute("select", select);
    String email = httpServletRequest.getParameter("email");
    httpServletRequest.setAttribute("single", single);
    httpServletRequest.setAttribute("show", show);
    String action = httpServletRequest.getParameter("action");
    if ("group".equals(action)) {
      String groupId = httpServletRequest.getParameter("groupid");
      String groupName = httpServletRequest.getParameter("groupname");
      List userList = null;
      MessageBD groupBD = new MessageBD();
      if (email == null || email.equals("null")) {
        userList = groupBD.selectGroupUser(groupId);
      } else {
        userList = groupBD.selectGroupUserEmail(groupId);
      } 
      httpServletRequest.setAttribute("userList", userList);
      httpServletRequest.setAttribute("groupid", groupId);
      httpServletRequest.setAttribute("groupname", groupName);
      return actionMapping.findForward("groupUser");
    } 
    if ("privatePerson".equals(action)) {
      System.out.println("-----22222222222----->>>>");
      String personId = httpServletRequest.getParameter("personId");
      String personName = httpServletRequest.getParameter("personName");
      MessageBD groupBD = new MessageBD();
      List userList = null;
      if (email == null || email.equals("null")) {
        userList = groupBD.selectPersonUser(personId, "", "");
      } else {
        userList = groupBD.selectPersonUserEmail(personId, "", "");
      } 
      httpServletRequest.setAttribute("email", email);
      httpServletRequest.setAttribute("userList", userList);
      httpServletRequest.setAttribute("personId", personId);
      httpServletRequest.setAttribute("personName", personName);
      return actionMapping.findForward("personUser");
    } 
    if ("publicPerson".equals(action)) {
      System.out.println("-----33333333----->>>>");
      String personId = httpServletRequest.getParameter("personId");
      String personName = httpServletRequest.getParameter("personName");
      MessageBD groupBD = new MessageBD();
      List userList = null;
      if (email == null || email.equals("null")) {
        userList = groupBD.selectPersonUser(personId, userId, orgId);
      } else {
        userList = groupBD.selectPersonUserEmail(personId, userId, orgId);
      } 
      httpServletRequest.setAttribute("email", email);
      httpServletRequest.setAttribute("userList", userList);
      httpServletRequest.setAttribute("personId", personId);
      httpServletRequest.setAttribute("personName", personName);
      return actionMapping.findForward("personUser");
    } 
    orgId = httpServletRequest.getParameter("orgid");
    String currentOrg = httpServletRequest.getParameter("currentOrg");
    MessageBD managerBD = new MessageBD();
    if ("updateOrg".equals(action)) {
      httpServletRequest.setAttribute("orgList", managerBD.getSubOrgs(orgId));
      return actionMapping.findForward("updateOrg");
    } 
    httpServletRequest.setAttribute("parentId", orgId);
    httpServletRequest.setAttribute("parentName", httpServletRequest.getParameter("orgname"));
    Map map = null;
    if (email == null || email.equals("null")) {
      map = managerBD.getSubOrgAndUsers(orgId, currentOrg);
    } else {
      map = managerBD.getSubOrgAndUsersEmail(orgId, currentOrg);
    } 
    if ("org".equals(select)) {
      httpServletRequest.setAttribute("orgList", map.get("org"));
      return actionMapping.findForward("org");
    } 
    httpServletRequest.setAttribute("userList", map.get("user"));
    if (show.indexOf("org") < 0 || "yes".equals(single))
      return actionMapping.findForward("userOnly"); 
    httpServletRequest.setAttribute("orgList", map.get("org"));
    return actionMapping.findForward("user");
  }
}
