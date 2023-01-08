package com.js.system.manager.action;

import com.js.system.manager.service.ManagerService;
import com.js.system.service.groupmanager.GroupBD;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SelectSubOrgAndUser extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    String select = httpServletRequest.getParameter("select");
    String show = httpServletRequest.getParameter("show");
    String single = httpServletRequest.getParameter("single");
    httpServletRequest.setAttribute("select", select);
    httpServletRequest.setAttribute("single", single);
    httpServletRequest.setAttribute("show", show);
    String action = httpServletRequest.getParameter("action");
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    String rootCorpId = session.getAttribute("rootCorpId").toString();
    String departId = session.getAttribute("departId").toString();
    String corpId = session.getAttribute("corpId").toString();
    String otherDepart = (httpServletRequest.getParameter("otherDepart") == null) ? "" : httpServletRequest.getParameter("otherDepart");
    if ("group".equals(action)) {
      String groupId = httpServletRequest.getParameter("groupid");
      String groupName = httpServletRequest.getParameter("groupname");
      String str1 = httpServletRequest.getParameter("email");
      System.out.println("------groupId----->>>>" + groupId);
      System.out.println("------email----->>>>" + str1);
      GroupBD groupBD = new GroupBD();
      List userList = null;
      if (str1 == null || str1.equals("null")) {
        userList = groupBD.selectGroupUser(groupId);
      } else {
        userList = groupBD.selectGroupUserEmail(groupId);
        httpServletRequest.setAttribute("email", str1);
      } 
      httpServletRequest.setAttribute("userList", userList);
      httpServletRequest.setAttribute("groupid", groupId);
      httpServletRequest.setAttribute("groupname", groupName);
      return actionMapping.findForward("groupUser");
    } 
    if ("privatePerson".equals(action)) {
      System.out.println("-----1111111111111111----->>>>");
      String personId = httpServletRequest.getParameter("personId");
      String personName = httpServletRequest.getParameter("personName");
      GroupBD groupBD = new GroupBD();
      List userList = groupBD.selectPersonUser(personId);
      httpServletRequest.setAttribute("userList", userList);
      httpServletRequest.setAttribute("personId", personId);
      httpServletRequest.setAttribute("personName", personName);
      return actionMapping.findForward("personUser");
    } 
    if ("publicPerson".equals(action)) {
      System.out.println("-----2222222222222----->>>>");
      String personId = httpServletRequest.getParameter("personId");
      String personName = httpServletRequest.getParameter("personName");
      GroupBD groupBD = new GroupBD();
      List userList = groupBD.selectPersonUser(personId);
      httpServletRequest.setAttribute("userList", userList);
      httpServletRequest.setAttribute("personId", personId);
      httpServletRequest.setAttribute("personName", personName);
      return actionMapping.findForward("personUser");
    } 
    String email = httpServletRequest.getParameter("email");
    String orgId = httpServletRequest.getParameter("orgid");
    String currentOrg = httpServletRequest.getParameter("currentOrg");
    ManagerService managerBD = new ManagerService();
    System.out.println("-----email1111----->>>>" + email);
    if ("updateOrg".equals(action)) {
      System.out.println("-----333333333333----->>>>");
      httpServletRequest.setAttribute("orgList", managerBD.getSubOrgs(orgId, domainId));
      return actionMapping.findForward("updateOrg");
    } 
    httpServletRequest.setAttribute("parentId", orgId);
    httpServletRequest.setAttribute("parentName", httpServletRequest.getParameter("orgname"));
    Map map = null;
    if (email == null || email.equals("null")) {
      map = managerBD.getSubOrgAndUsers(orgId, currentOrg, domainId, rootCorpId, corpId, departId, otherDepart);
    } else {
      map = managerBD.getSubOrgAndUsersEmail(orgId, currentOrg, domainId, rootCorpId, corpId, departId, otherDepart);
    } 
    if ("org".equals(select)) {
      System.out.println("-----44444444444----->>>>");
      httpServletRequest.setAttribute("orgList", map.get("org"));
      return actionMapping.findForward("org");
    } 
    System.out.println("-----55555----->>>>");
    httpServletRequest.setAttribute("userList", map.get("user"));
    if (show.indexOf("org") < 0 || "yes".equals(single)) {
      System.out.println("-----66666----->>>>");
      httpServletRequest.setAttribute("email", email);
      return actionMapping.findForward("userOnly");
    } 
    System.out.println("-----777777----->>>>");
    httpServletRequest.setAttribute("orgList", map.get("org"));
    return actionMapping.findForward("user");
  }
}
