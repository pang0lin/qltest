package com.js.oa.info.channelmanager.action;

import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.oa.jsflow.util.ProcessStep;
import com.js.system.manager.service.ManagerService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ChannelISOMenuAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    ChannelActionForm channelActionForm = (ChannelActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String domainId = session.getAttribute("domainId").toString();
    HttpSession httpSession = httpServletRequest.getSession(true);
    Object object = httpSession.getAttribute("domainId");
    List<Object[]> list = (new ChannelBD()).getChannelMenu_ByType("", "", "", (String)object, "2");
    httpServletRequest.setAttribute("docClass", list);
    if (list != null && list.size() > 0) {
      Object[] obj = list.get(0);
      ChannelBD bd = new ChannelBD();
      String nextStep = "";
      ProcessStep processStep = new ProcessStep();
      Long processId = bd.getChannelProcessId((String)obj[0]);
      if (processId.longValue() != 0L) {
        nextStep = processStep.firstStep(processId.toString(), 1, 
            httpServletRequest);
        httpServletRequest.setAttribute("processId", processId);
      } 
    } 
    ChannelBD channelBD = new ChannelBD();
    ManagerService managerBD = new ManagerService();
    String where = managerBD.getScopeFinalWhere(userId, orgId, orgIdString, 
        "aaa.channelIssuer", 
        "aaa.channelIssuerOrg", 
        "aaa.channelIssuerGroup");
    List canIssueList = channelBD.getIsoCanIssue(where, domainId);
    if (canIssueList != null && canIssueList.size() > 0) {
      httpServletRequest.setAttribute("canIssue", "1");
    } else {
      httpServletRequest.setAttribute("canIssue", "0");
    } 
    return actionMapping.findForward("ISOMenu");
  }
  
  public List getIsoCanIssueChannel(String userId, String orgId, String orgIdString, String domainId) {
    ChannelBD channelBD = new ChannelBD();
    ManagerService managerBD = new ManagerService();
    String where = managerBD.getScopeFinalWhere(userId, orgId, orgIdString, 
        "aaa.channelIssuer", 
        "aaa.channelIssuerOrg", 
        "aaa.channelIssuerGroup");
    return channelBD.getIsoCanIssue(where, domainId);
  }
}
