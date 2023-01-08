package com.js.oa.info.infomanager.action;

import com.js.lang.Resource;
import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.oa.info.infomanager.po.InformationPO;
import com.js.oa.info.infomanager.service.InformationBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.messages.RemindBD;
import com.js.system.service.messages.RemindUtil;
import com.js.system.vo.messages.Remind;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InfoViewIframeAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    InfoViewIframeActionForm infoViewIframeActionForm = (InfoViewIframeActionForm)actionForm;
    String action = request.getParameter("action");
    String informationId = request.getParameter("informationId");
    String informationType = request.getParameter("informationType");
    String channelType = request.getParameter("channelType");
    String userChannelName = request.getParameter("userChannelName");
    request.setAttribute("channelType", channelType);
    InformationBD informationBD = new InformationBD();
    String tag = "success";
    StringBuffer sb = new StringBuffer();
    Long curUserId = new Long(request.getSession(true).getAttribute("userId").toString());
    String local = request.getSession(true).getAttribute("org.apache.struts.action.LOCALE").toString();
    String isISO = (request.getParameter("isISO") == null) ? "0" : request.getParameter("isISO").toString();
    String managerMen = (request.getParameter("managerMen") == null) ? "0" : request.getParameter("managerMen").toString();
    if ("delHistory".equals(action)) {
      String historyId = request.getParameter("historyId");
      informationBD.deleteHistory(historyId, informationId);
    } 
    if (action.equals("browser")) {
      sb.append("<table width=\"80%\" border=1 cellpadding=1 cellspacing=0 bordercolor=#000000 bordercolordark=#FFFFFF>");
      List<Object[]> list = informationBD.getBrowser(informationId, "");
      int j = 0;
      Object[] obj = (Object[])null;
      for (int i = 0; i < list.size(); i++) {
        obj = list.get(i);
        if (j % 3 == 0)
          sb.append("<tr bgcolor=\"#FFFFFF\">"); 
        j++;
        sb.append("<td width=\"33%\" height=\"20\"><font color=\"#000000\">");
        sb.append(obj[1] + ".");
        sb.append(obj[0]);
        sb.append("</font></td>");
        if (j % 3 == 0)
          sb.append("</tr>"); 
      } 
      if (j % 3 != 0)
        for (int k = 0; k < 3 - j % 3; k++)
          sb.append("<td>&nbsp;</td>");  
      sb.append("<tr bgcolor=\"#FFFFFF\">");
      sb.append("<td height=\"20\" colspan=\"3\" align=\"center\">");
      sb.append(list.size());
      sb.append("（人次）</td>");
      sb.append("</tr>");
      sb.append("</table>");
      request.setAttribute("content", sb.toString());
    } else if (action.equals("history") || "delHistory".equals(action)) {
      boolean canVindicate = false;
      if ("delHistory".equals(action)) {
        canVindicate = true;
      } else {
        HttpSession httpSession = request.getSession(true);
        String userId = httpSession.getAttribute("userId").toString();
        String orgId = httpSession.getAttribute("orgId").toString();
        String orgIdString = httpSession.getAttribute("orgIdString")
          .toString();
        String channelId = request.getParameter("channelId");
        canVindicate = (new ChannelBD()).canVindicate(userId, orgId, 
            channelId);
        if (!canVindicate) {
          ManagerService mbd = new ManagerService();
          List rightList = mbd.getRightScope(userId, "01*03*03");
          String canModiIds = informationBD.getInformationModiIds(
              channelId, userId, orgId, orgIdString, 
              informationId, 
              rightList);
          if (canModiIds.indexOf("," + informationId + ",") >= 0)
            canVindicate = true; 
        } 
      } 
      if (request.getParameter("isISO") != null && request.getParameter("isISO").toString().equals("1"))
        canVindicate = false; 
      sb.append("<table width=\"98%\" border=0 cellspacing=0 cellpadding=0 class=\"InnerTable-border\">");
      sb.append("<tr>");
      sb.append("<td width=\"10%\" align=\"center\" height=\"28\">" + Resource.getValue(local, "information", "info.nono") + "</td>");
      sb.append("<td width=\"25%\" align=\"center\" height=\"28\">" + Resource.getValue(local, "information", "info.versionumber") + "</td>");
      sb.append("<td width=\"25%\" align=\"center\" height=\"28\">" + Resource.getValue(local, "information", "info.viewmodifier") + "</td>");
      sb.append("<td width=\"30%\" align=\"center\" height=\"28\">" + Resource.getValue(local, "information", "info.viewpubtime") + "</td>");
      if (canVindicate)
        sb.append("<td width=\"10%\" align=\"center\" height=\"28\" nowrap>" + Resource.getValue(local, "information", "info.columnoperation") + "</td>"); 
      List<Object[]> list = informationBD.getHistoryVersion(informationId);
      Object[] obj = (Object[])null;
      for (int i = 0; i < list.size(); i++) {
        obj = list.get(i);
        Object object1 = obj[0];
        Object object2 = obj[8];
        int b = Integer.parseInt(object1.substring(object1.indexOf(".") + 1, object1.length()));
        if (("0".equals(b) || !isISO.equals("1") || managerMen.equals("1")) && !object2.equals("2") && !object2.equals("3") && !object2.equals("4")) {
          String modifyMen = "&nbsp;";
          if (obj[1] != null && !obj[1].toString().equals("") && obj[2] != null && !obj[2].toString().equals(""))
            modifyMen = obj[1] + "." + obj[2]; 
          sb.append("<tr>");
          sb.append("<td align=\"center\" height=\"28\" class=\"InnerTable-border_top\">" + (i + 1) + "</td>");
          sb.append("<td align=\"center\" height=\"20\" class=\"InnerTable-border_top\">");
          sb.append("<a href=\"#\" onclick=javascript:JSMainWinOpenForOpener(\"InfoHistViewAction.do?historyId=");
          sb.append(obj[4]);
          sb.append("&redHead=");
          sb.append(obj[5]);
          sb.append("&informationId=" + request.getParameter("informationId"));
          sb.append("&informationType=" + informationType + "&channelId=" + obj[6]);
          if (request.getParameter("isISO") != null && 
            request.getParameter("isISO").toString()
            .equals("1"))
            sb.append("&isISO=1"); 
          if (request.getParameter("depart") != null && !"null".equals(request.getParameter("depart")))
            sb.append("&depart=1"); 
          sb.append("&channelType=" + obj[7]);
          sb.append("\",\"\",\"TOP=0,LEFT=0,scrollbars=yes,resizable=yes,width=800,height=600\");>");
          sb.append(obj[0] + "</a></td>");
          sb.append("<td height=\"20\" class=\"InnerTable-border_top\">" + modifyMen + "</td>");
          sb.append("<td align=\"center\" height=\"20\" class=\"InnerTable-border_top\">");
          String date = obj[3].toString();
          if (date.indexOf(".") > 0) {
            sb.append(date.substring(0, date.indexOf(".")));
          } else {
            sb.append(date);
          } 
          sb.append("</td>");
          if (canVindicate) {
            sb.append("<td class=\"InnerTable-border_top\"><img alt=\"" + Resource.getValue(local, "information", "info.alldelete") + "\" style=\"cursor:hand\" border=\"0\" src=\"images/del.gif\" onclick=javascript:delHistory('");
            sb.append(obj[4]);
            sb.append("');></td>");
          } 
          sb.append("</tr>");
        } 
      } 
      sb.append("</table>");
      request.setAttribute("content", sb.toString());
    } else if (action.equals("comment")) {
      sb.append("<form method=\"post\" action = \"\">");
      sb.append("<table width=\"80%\" border=1 cellpadding=1 cellspacing=0 bordercolor=#000000 bordercolordark=#FFFFFF>");
      List<Object[]> list = informationBD.getComment(informationId);
      Object[] obj = (Object[])null;
      for (int i = 0; i < list.size(); i++) {
        obj = list.get(i);
        sb.append("'+ \n'<tr><td height=\"20\" bgcolor=\"#CCCCCC\"><font color=\"#000000\"><strong>");
        sb.append(obj[0]);
        sb.append("." + obj[1]);
        sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
        if (obj[2].toString().indexOf(".") > 0) {
          sb.append(obj[2].toString().substring(0, obj[2].toString().indexOf(".")));
        } else {
          sb.append(obj[2]);
        } 
        sb.append("</strong></font></td></tr>");
        sb.append("<tr><td height=\"20\" bgcolor=\"#FFFFFF\"><p><font color=\"#000000\">");
        sb.append(obj[3]);
        sb.append("</font></p></td></tr>");
      } 
      sb.append("</table>");
      sb.append("</form>");
      request.setAttribute("content", sb.toString());
    } else if (action.equals("speak")) {
      HttpSession httpSession = request.getSession(true);
      String informationTitle = request.getParameter("informationTitle");
      String userId = httpSession.getAttribute("userId").toString();
      String userName = httpSession.getAttribute("userName").toString();
      String orgName = httpSession.getAttribute("orgName").toString();
      String commentParentId = request.getParameter("commentParentId");
      String content = infoViewIframeActionForm.getContent();
      String updateCommentId = (request.getParameter("updateCommentId") == null) ? "" : request.getParameter("updateCommentId");
      if (updateCommentId != null && !updateCommentId.equals("")) {
        informationBD.updateComment(content, updateCommentId);
      } else {
        informationBD.setComment(userId, userName, orgName, content, informationId, commentParentId);
      } 
      InformationPO po = informationBD.getInformationPoById(Long.valueOf(informationId));
      Long long_ = po.getInformationIssuerId();
      String url = "/jsoa/info/view_detail.jsp?informationId=" + informationId;
      String titleC = "您发布的" + informationTitle + "有新的回复";
      if (!"".equals(long_)) {
        Calendar tmp = Calendar.getInstance();
        tmp.set(2050, 12, 12);
        RemindUtil.sendMessageToUsers(titleC, url, (String)long_, "Info", new Date(), tmp.getTime());
      } 
      RemindBD remindBD = new RemindBD();
      try {
        List<Remind> list = remindBD.searchReminduser(Long.parseLong(informationId), "Info");
        String str = "";
        if (!list.isEmpty()) {
          for (int i = 0; i < list.size(); i++) {
            Remind forumRemind = list.get(i);
            if (!curUserId.toString().equals((new Long(forumRemind.getEmp_id())).toString()))
              str = String.valueOf(str) + forumRemind.getEmp_id() + ","; 
          } 
          str = String.valueOf(str) + po.getInformationIssuerId();
          String title = "您关注的" + informationTitle + "有新的回复";
          if (!"".equals(str)) {
            str = str.substring(0, str.length() - 1);
            Calendar tmp = Calendar.getInstance();
            tmp.set(2050, 12, 12);
            RemindUtil.sendMessageToUsers(title, url, str, "Info", new Date(), tmp.getTime());
          } 
        } 
      } catch (Exception exception) {}
      return actionMapping.findForward("addComment");
    } 
    return actionMapping.findForward(tag);
  }
}
