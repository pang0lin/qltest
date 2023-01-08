package com.js.oa.info.channelmanager.action;

import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ChannelMenuAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    ChannelActionForm channelActionForm = (ChannelActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    String channelType = httpServletRequest.getParameter("channelType");
    String userChannelName = httpServletRequest.getParameter("userChannelName");
    HttpSession httpSession = httpServletRequest.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    String orgIdString = httpSession.getAttribute("orgIdString").toString();
    ChannelBD channelBD = new ChannelBD();
    ManagerService managerBD = new ManagerService();
    String rightWhere = "", scopeWhere = "";
    String userDefine = (httpServletRequest.getParameter("userDefine") == null) ? "0" : httpServletRequest.getParameter("userDefine");
    if (channelType.equals("0") || userDefine.equals("1")) {
      rightWhere = managerBD.getRightFinalWhere(userId, orgId, "01*02*01", "po2.createdOrg", "po2.createdEmp");
    } else {
      rightWhere = managerBD.getRightFinalWhere(userId, orgId, "01*01*02", "po2.createdOrg", "po2.createdEmp");
    } 
    scopeWhere = managerBD.getScopeFinalWhere(userId, orgId, orgIdString, "po2.channelReader", "po2.channelReaderOrg", "po2.channelReaderGroup");
    List<Object[]> list = channelBD.getChannelMenu(rightWhere, scopeWhere, channelType, domainId, (String)session.getAttribute("corpId"), (String)session.getAttribute("sidelineCorpId"));
    httpServletRequest.setAttribute("channelList", list);
    if (SystemCommon.getMultiDepart() == 1) {
      list = channelBD.getUserViewCh(userId, orgId, channelType, userDefine, domainId, (String)session.getAttribute("corpId"), (String)session.getAttribute("sidelineCorpId"));
    } else {
      list = channelBD.getUserViewCh(userId, orgId, channelType, userDefine, domainId, "", "");
    } 
    StringBuffer canReadChannel = new StringBuffer();
    Object[] obj = (Object[])null;
    if (list != null && list.size() > 0 && list.get(0) != null)
      for (int i = 0; i < list.size(); i++) {
        obj = list.get(i);
        canReadChannel.append("$").append(obj[0]).append("$");
      }  
    list = channelBD.getUserManageList(userId, orgId, orgIdString, channelType, userDefine, domainId);
    if (list != null && list.size() > 0)
      for (int i = 0; i < list.size(); i++)
        canReadChannel.append("$").append(list.get(i).toString()).append("$");  
    httpServletRequest.setAttribute("canReadChannel", canReadChannel.toString());
    httpServletRequest.setAttribute("channelType", channelType);
    httpServletRequest.setAttribute("userChannelName", userChannelName);
    if (httpServletRequest.getParameter("isAffiche") != null && httpServletRequest.getParameter("isAffiche").toString().equals("1")) {
      List channeellist = getCanIssueChannel(userId, orgId, orgIdString, domainId);
      httpServletRequest.setAttribute("canIssueChannel", channeellist);
      return actionMapping.findForward("isAffiche");
    } 
    String participationProId = channelBD.getParticipationProId(userId);
    httpServletRequest.setAttribute("participationProId", participationProId);
    list = channelBD.getChannelMenu(scopeWhere, "2", domainId);
    httpServletRequest.setAttribute("projectChannel", list);
    return actionMapping.findForward("success");
  }
  
  public List getCanIssueChannel(String userId, String orgId, String orgIdString, String domainId) {
    ChannelBD channelBD = new ChannelBD();
    ManagerService managerBD = new ManagerService();
    String where = managerBD.getScopeFinalWhere(userId, orgId, orgIdString, 
        "aaa.channelIssuer", 
        "aaa.channelIssuerOrg", 
        "aaa.channelIssuerGroup");
    return channelBD.getAfficheCanIssue(where, domainId);
  }
}
