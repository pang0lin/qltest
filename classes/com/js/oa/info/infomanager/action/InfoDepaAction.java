package com.js.oa.info.infomanager.action;

import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.system.manager.service.ManagerService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InfoDepaAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    InfoDepaActionForm infoDepaActionForm = (InfoDepaActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    httpServletRequest.setAttribute("orgId", httpServletRequest.getParameter("orgId"));
    httpServletRequest.setAttribute("orgName", httpServletRequest.getParameter("orgName"));
    String tag = "success";
    String action = httpServletRequest.getParameter("action");
    httpServletRequest.setAttribute("styleId", httpServletRequest.getParameter("styleId"));
    if (action != null && action.equals("leftFrame")) {
      HttpSession httpSession = httpServletRequest.getSession(true);
      String userId = httpSession.getAttribute("userId").toString();
      String orgId = httpSession.getAttribute("orgId").toString();
      ChannelBD channelBD = new ChannelBD();
      List<Object[]> list = new ArrayList();
      httpServletRequest.setAttribute("channelList", list);
      list = channelBD.getUserViewCh(userId, orgId, httpServletRequest.getParameter("orgId"));
      String canReadChannel = "";
      if (list != null && list.size() > 0 && list.get(0) != null) {
        Object[] obj = (Object[])null;
        for (int i = 0; i < list.size(); i++) {
          obj = list.get(i);
          canReadChannel = String.valueOf(canReadChannel) + "$" + obj[0] + "$";
        } 
      } 
      httpServletRequest.setAttribute("canReadChannel", canReadChannel);
      ManagerService managerBD = new ManagerService();
      if (channelBD.departPageRight(userId, orgId, httpSession.getAttribute("orgIdString").toString(), httpServletRequest.getParameter("orgId"))) {
        httpServletRequest.setAttribute("canMana", "1");
      } else {
        httpServletRequest.setAttribute("canMana", "0");
      } 
      boolean canIssue = channelBD.canIssue(userId, orgId, httpServletRequest.getParameter("orgId"));
      if (canIssue) {
        httpServletRequest.setAttribute("canIssue", "1");
      } else {
        httpServletRequest.setAttribute("canIssue", "0");
      } 
      tag = "leftFrame";
      String addTemplate = "0";
      if (managerBD.hasRightTypeName(userId, "模板维护", "维护"))
        addTemplate = "1"; 
      httpServletRequest.setAttribute("addTemplate", addTemplate);
    } 
    return actionMapping.findForward(tag);
  }
}
