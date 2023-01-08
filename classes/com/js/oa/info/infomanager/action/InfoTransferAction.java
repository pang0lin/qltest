package com.js.oa.info.infomanager.action;

import com.js.oa.info.infomanager.service.InformationBD;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InfoTransferAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    InfoTransferActionForm infoTransferActionForm = (InfoTransferActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    String idString = infoTransferActionForm.getIdString();
    String transferChannel = infoTransferActionForm.getTransferChannel();
    String orchannelId = httpServletRequest.getParameter("channelId");
    InformationBD informationBD = new InformationBD();
    informationBD.transfer(idString.split(","), transferChannel, orchannelId);
    httpServletRequest.setAttribute("channelId", httpServletRequest.getParameter("channelId"));
    httpServletRequest.setAttribute("userChannelName", httpServletRequest.getParameter("userChannelName"));
    httpServletRequest.setAttribute("channelName", httpServletRequest.getParameter("channelName"));
    httpServletRequest.setAttribute("channelType", httpServletRequest.getParameter("channelType"));
    httpServletRequest.setAttribute("channelShowType", httpServletRequest.getParameter("channelShowType"));
    httpServletRequest.setAttribute("depart", httpServletRequest.getParameter("depart"));
    httpServletRequest.setAttribute("styleId", httpServletRequest.getParameter("styleId"));
    String searchResult = httpServletRequest.getParameter("searchResult");
    String userDefine = httpServletRequest.getParameter("userDefine");
    httpServletRequest.setAttribute("userDefine", userDefine);
    if ("1".equals(searchResult))
      httpServletRequest.setAttribute("searchResult", searchResult); 
    return actionMapping.findForward("success");
  }
}
