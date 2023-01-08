package com.js.oa.info.infomanager.action;

import com.js.oa.info.channelmanager.po.InformationChannelPO;
import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.oa.jsflow.util.ProcessStep;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ChangeChannelIframeAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    InfoViewIframeActionForm infoViewIframeActionForm = 
      (InfoViewIframeActionForm)actionForm;
    String channelType = httpServletRequest.getParameter("channelType");
    httpServletRequest.setAttribute("channelType", channelType);
    String userDefine = httpServletRequest.getParameter("userDefine");
    httpServletRequest.setAttribute("userDefine", userDefine);
    String newaddress = httpServletRequest.getParameter("newaddress");
    httpServletRequest.setAttribute("newaddress", newaddress);
    String channelId = httpServletRequest.getParameter("channelId");
    httpServletRequest.setAttribute("channelId", channelId);
    String nextStep = "";
    String tagls = "b";
    ProcessStep processStep = new ProcessStep();
    if (!channelId.equals("")) {
      ChannelBD bd = new ChannelBD();
      Long processId = bd.getChannelProcessId(channelId);
      if (processId.longValue() != 0L) {
        nextStep = processStep.firstStep(processId.toString(), 1, 
            httpServletRequest);
        tagls = "a";
        httpServletRequest.setAttribute("tagls", tagls);
        httpServletRequest.setAttribute("processId", processId);
      } 
      httpServletRequest.setAttribute("tagls", tagls);
      InformationChannelPO po = bd.loadChannel(channelId);
      String reader = "";
      if (po.getChannelReader() != null)
        reader = String.valueOf(reader) + po.getChannelReader(); 
      if (po.getChannelReaderGroup() != null)
        reader = String.valueOf(reader) + po.getChannelReaderGroup(); 
      if (po.getChannelReaderOrg() != null)
        reader = String.valueOf(reader) + po.getChannelReaderOrg(); 
      httpServletRequest.setAttribute("canReader", reader);
      httpServletRequest.setAttribute("canReaderName", (po.getChannelReaderName() == null) ? "" : po.getChannelReaderName());
      String afficheReader = "";
      String afficheReaderOrg = "";
      String afficheReaderGroup = "";
      String afficheReaderId = "";
      if (po.getChannelIssuer() != null) {
        afficheReader = String.valueOf(afficheReader) + po.getChannelIssuer();
        afficheReaderId = String.valueOf(afficheReaderId) + po.getChannelIssuer();
      } 
      if (po.getChannelIssuerOrg() != null) {
        afficheReaderOrg = String.valueOf(afficheReaderOrg) + po.getChannelIssuerOrg();
        afficheReaderId = String.valueOf(afficheReaderId) + po.getChannelIssuerOrg();
      } 
      if (po.getChannelIssuerGroup() != null) {
        afficheReaderGroup = String.valueOf(afficheReaderGroup) + po.getChannelIssuerGroup();
        afficheReaderId = String.valueOf(afficheReaderId) + po.getChannelIssuerGroup();
      } 
      httpServletRequest.setAttribute("afficheReaderId", afficheReaderId);
      httpServletRequest.setAttribute("afficheReader", afficheReader);
      httpServletRequest.setAttribute("afficheReaderOrg", afficheReaderOrg);
      httpServletRequest.setAttribute("afficheReaderGroup", afficheReaderGroup);
      httpServletRequest.setAttribute("afficheReaderName", (po.getChannelIssuerName() == null) ? "" : po.getChannelIssuerName());
    } 
    httpServletRequest.setAttribute("nextStep", nextStep);
    if (httpServletRequest.getParameter("isAffiche") != null && httpServletRequest.getParameter("isAffiche").equals("1"))
      return actionMapping.findForward("afficheProcess"); 
    return actionMapping.findForward("success");
  }
}
