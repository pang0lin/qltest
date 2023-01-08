package com.js.oa.info.infomanager.action;

import com.js.oa.archives.service.ArchivesBD;
import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.oa.info.infomanager.service.InformationAccessoryBD;
import com.js.oa.info.infomanager.service.InformationBD;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InfoHistViewAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    InformationActionForm informationActionForm = (InformationActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    HttpSession httpSession = httpServletRequest.getSession(true);
    String redHead = httpServletRequest.getParameter("redHead");
    String historyId = httpServletRequest.getParameter("historyId");
    String informationId = httpServletRequest.getParameter("informationId");
    httpServletRequest.setAttribute("informationId", informationId);
    httpServletRequest.setAttribute("redHead", redHead);
    httpServletRequest.setAttribute("informationType", httpServletRequest.getParameter("informationType"));
    String userId = httpSession.getAttribute("userId").toString();
    String userName = httpSession.getAttribute("userName").toString();
    String orgName = httpSession.getAttribute("orgName").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    String domainId = httpSession.getAttribute("domainId").toString();
    InformationBD informationBD = new InformationBD();
    List<Object[]> infoList = informationBD.getSingleInfo(httpServletRequest.getParameter("informationId"), null);
    if (infoList != null && infoList.size() > 0) {
      Object[] infoObj = infoList.get(0);
      httpServletRequest.setAttribute("informationIssuer", infoObj[3].toString());
      httpServletRequest.setAttribute("informationIssueOrg", infoObj[4].toString());
      if (infoObj[37] == null) {
        httpServletRequest.setAttribute("dossierStatus", "0");
      } else {
        httpServletRequest.setAttribute("dossierStatus", infoObj[37].toString());
      } 
    } 
    String returnValue = "";
    try {
      returnValue = (new ArchivesBD()).archivesPigeonholeSet("ZSGL", session.getAttribute("domainId").toString());
    } catch (Exception exception) {}
    if (returnValue.equals(""))
      returnValue = "1,1,0"; 
    Boolean dossier = Boolean.FALSE;
    String isSendMessage = "0";
    if (!returnValue.equals("-1")) {
      String[] arrayOfString = returnValue.split(",");
      if (arrayOfString[0].equals("1"))
        dossier = Boolean.TRUE; 
      isSendMessage = arrayOfString[2];
    } 
    httpServletRequest.setAttribute("dossier", dossier);
    httpServletRequest.setAttribute("isSendMessage", isSendMessage);
    httpServletRequest.setAttribute("canVindicate", 
        informationBD.vindicateInfo(userId, orgId, httpServletRequest.getParameter("informationId")));
    String tag = "";
    if (redHead.equals("0")) {
      tag = "noRedHead";
      if (httpServletRequest.getParameter("isISO") != null && httpServletRequest.getParameter("isISO").toString().equals("1"))
        tag = "isoNoRedHead"; 
    } else {
      tag = "hasRedHead";
    } 
    List commentList = informationBD.getComment(httpServletRequest.getParameter("informationId"));
    httpServletRequest.setAttribute("commentList", commentList);
    httpServletRequest.setAttribute("channelType", httpServletRequest.getParameter("channelType"));
    ChannelBD channelBD = new ChannelBD();
    String userDefine = informationBD.getInfoUserdefine(httpServletRequest.getParameter("informationId"));
    httpServletRequest.setAttribute("assoicateInfo", (new InformationBD()).getAssociateInfo(orgId, httpServletRequest.getParameter("informationId"), userId, httpSession.getAttribute("orgIdString").toString(), httpServletRequest.getParameter("channelType"), userDefine));
    List<Object[]> list = informationBD.getSingleHistInfo(historyId);
    Object[] obj = list.get(0);
    httpServletRequest.setAttribute("informationTitle", obj[0]);
    if (obj[1] == null) {
      httpServletRequest.setAttribute("informationSubTitle", "");
    } else {
      httpServletRequest.setAttribute("informationSubTitle", "-- " + obj[1].toString());
    } 
    String content = "";
    if (list.get(1) == null) {
      httpServletRequest.setAttribute("content", "");
    } else {
      content = list.get(1).toString();
      httpServletRequest.setAttribute("content", content);
    } 
    if (obj[3] != null && !obj[3].toString().equals("")) {
      httpServletRequest.setAttribute("modifyEmp", obj[4] + "." + obj[3]);
    } else {
      httpServletRequest.setAttribute("modifyEmp", "");
    } 
    String tmp = obj[5].toString();
    if (tmp.indexOf(".") > 0) {
      httpServletRequest.setAttribute("informationIssueTime", tmp.substring(0, tmp.indexOf(".")));
    } else {
      httpServletRequest.setAttribute("informationIssueTime", tmp);
    } 
    tmp = obj[6].toString();
    if (tmp.indexOf(".") > 0) {
      httpServletRequest.setAttribute("informationModifyTime", tmp.substring(0, tmp.indexOf(".")));
    } else {
      httpServletRequest.setAttribute("informationModifyTime", tmp);
    } 
    if (obj[7] == null) {
      httpServletRequest.setAttribute("informationVersion", "");
    } else {
      httpServletRequest.setAttribute("informationVersion", obj[7].toString());
    } 
    if (obj[8] == null) {
      httpServletRequest.setAttribute("informationAuthor", "");
    } else {
      httpServletRequest.setAttribute("informationAuthor", obj[8].toString());
    } 
    if (tag.equals("hasRedHead")) {
      if (obj[9] == null) {
        httpServletRequest.setAttribute("informationMark", "");
      } else {
        httpServletRequest.setAttribute("informationMark", obj[9].toString());
      } 
      if (obj[10] == null) {
        httpServletRequest.setAttribute("informationHeadFile", "");
      } else {
        httpServletRequest.setAttribute("informationHeadFile", obj[10].toString());
      } 
      if (obj[11] == null) {
        httpServletRequest.setAttribute("informationSeal", "");
      } else {
        httpServletRequest.setAttribute("informationSeal", obj[11].toString());
      } 
      if (obj[12] == null) {
        httpServletRequest.setAttribute("infoRedIssueOrg", "");
      } else {
        httpServletRequest.setAttribute("infoRedIssueOrg", obj[12].toString());
      } 
      httpServletRequest.setAttribute("infoRedIssueTime", obj[13]);
    } 
    InformationAccessoryBD informationAccessoryBD = new InformationAccessoryBD();
    list = informationAccessoryBD.getHistAccessory(historyId);
    httpServletRequest.setAttribute("accessoryList", list);
    httpServletRequest.setAttribute("forbidCopy", obj[16]);
    httpServletRequest.setAttribute("displayTitle", obj[19]);
    httpServletRequest.setAttribute("displayImage", obj[20]);
    if (obj[15] == null) {
      httpServletRequest.setAttribute("informationKey", "");
    } else {
      httpServletRequest.setAttribute("informationKey", 
          obj[15]);
    } 
    if (obj[14] == null) {
      httpServletRequest.setAttribute("informationSummary", 
          "");
    } else {
      httpServletRequest.setAttribute("informationSummary", 
          obj[14]);
    } 
    String depart = httpServletRequest.getParameter("depart");
    if (depart != null) {
      httpServletRequest.setAttribute("depart", depart);
      httpServletRequest.setAttribute("canVindicate", new Boolean((new ChannelBD()).canVindicate(userId, orgId, httpServletRequest.getParameter("channelId"))));
      httpServletRequest.setAttribute("infoDepaFlag", obj[17]);
      httpServletRequest.setAttribute("infoDepaFlag2", obj[18]);
    } 
    if ("3".equals(httpServletRequest.getParameter("informationType"))) {
      tag = "file";
      if (content != null && !"".equals(content)) {
        String[] file = content.split(":");
        httpServletRequest.setAttribute("fileName", file[0]);
        httpServletRequest.setAttribute("saveName", file[1]);
      } 
    } 
    String channelIds = "";
    String channelNames = "";
    List<Object[]> channelstringids = informationBD.getchannleinfo(informationId);
    if (channelstringids != null && channelstringids.size() > 0) {
      Object[] objid = (Object[])null;
      objid = channelstringids.get(0);
      channelIds = objid[0].toString();
      channelNames = objid[1].toString();
    } 
    String channelId = channelIds;
    String channelName = channelNames;
    String userChannelName = "知识管理";
    httpServletRequest.setAttribute("channelName", channelName);
    httpServletRequest.setAttribute("userChannelName", userChannelName);
    String channelidstring = "";
    List<Object[]> channelstringid = channelBD.getSingleChannel(channelId);
    if (channelstringid != null && channelstringid.size() > 0) {
      Object[] objid = (Object[])null;
      objid = channelstringid.get(0);
      channelidstring = objid[13].toString();
    } 
    if (!"".equals(channelidstring)) {
      String address = "";
      String[] temp_channelidstring = channelidstring.split("_");
      for (int i = 0; i < temp_channelidstring.length; i++) {
        String channelidstring_new = "";
        channelidstring_new = temp_channelidstring[i];
        String[] temp_channelidstring_new = channelidstring_new.split("\\$");
        String channelidstring_new_now = "";
        channelidstring_new_now = temp_channelidstring_new[1];
        channelId = channelidstring_new_now;
        List<Object[]> channelname = channelBD.getSingleChannel(channelId);
        if (channelname != null && channelname.size() > 0) {
          Object[] objid = (Object[])null;
          objid = channelname.get(0);
          channelidstring = objid[0].toString();
        } 
        address = String.valueOf(address) + "." + channelidstring;
      } 
      httpServletRequest.setAttribute("channelId", channelId);
      httpServletRequest.setAttribute("channelName", channelidstring);
      httpServletRequest.setAttribute("address", address);
    } 
    httpServletRequest.setAttribute("informationId", informationId);
    String informationId_same = "";
    List information_sameKeylist = new ArrayList();
    List<Object[]> information_samelist = informationBD.getinformation(informationId);
    if (information_samelist != null && information_samelist.size() > 0) {
      Object[] objlist = (Object[])null;
      objlist = information_samelist.get(0);
      if (objlist[1] != null && !objlist[1].toString().equals("")) {
        informationId_same = objlist[1].toString();
        information_sameKeylist = informationBD.getinformationID(informationId_same, informationId, domainId);
        httpServletRequest.setAttribute("information_sameKeylist", information_sameKeylist);
      } else {
        httpServletRequest.setAttribute("information_sameKeylist", information_sameKeylist);
      } 
    } 
    return actionMapping.findForward(tag);
  }
}
