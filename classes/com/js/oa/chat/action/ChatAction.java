package com.js.oa.chat.action;

import com.js.oa.chat.bean.ChatBean;
import com.js.oa.chat.po.ChatPO;
import com.js.oa.chat.service.ChatService;
import com.js.oa.pressdeal.action.PressManageAction;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
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

public class ChatAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, Exception {
    HttpSession session = request.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    String userId = session.getAttribute("userId").toString();
    String userName = session.getAttribute("userName").toString();
    ChatService chatService = new ChatService();
    String action = request.getParameter("action");
    String flag = (request.getParameter("flag") == null) ? "" : request.getParameter("flag").toString();
    if (!"loadChat".equals(action))
      if (!"send".equals(action))
        if ("sendChat".equals(action)) {
          String chatToId = request.getParameter("chatToId");
          ChatPO chatPO = new ChatPO();
          chatPO.setChatContent(request.getParameter("chatContent"));
          chatPO.setChatTime(new Date());
          chatPO.setSenderId(userId);
          chatPO.setSenderName(userName);
          chatPO.setChatTo(request.getParameter("chatTo"));
          if (request.getParameter("accessorySize") != null && !"".equals(request.getParameter("accessorySize"))) {
            chatPO.setChatAttachsize(request.getParameter("accessorySize"));
          } else {
            chatPO.setChatAttachsize("0");
          } 
          String[] fileName = request.getParameterValues("chatFileName");
          String[] saveName = request.getParameterValues("chatSaveName");
          chatService.sendChat(chatPO, fileName, saveName, chatToId);
          return actionMapping.findForward("sendClose");
        }   
    if ("delete".equals(flag)) {
      String deltype = (request.getParameter("deltype") == null) ? "" : request.getParameter("deltype");
      String chatUserIds = request.getParameter("chatId");
      chatService.deleteChat(chatUserIds, userId, deltype);
      list(request);
    } else if ("markAsRead".equals(flag)) {
      String markype = (request.getParameter("markype") == null) ? "" : request.getParameter("markype");
      String chatUserIds = request.getParameter("chatId");
      chatService.markAsRead(chatUserIds, userId, markype);
      list(request);
    } else {
      list(request);
    } 
    return actionMapping.findForward("list");
  }
  
  private void list(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    String userId = session.getAttribute("userId").toString();
    String userName = session.getAttribute("userName").toString();
    String timeScan = request.getParameter("timeScan");
    getNum(request, userId);
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String viewSql = "cu.id,cu.chatStatus,cu.isRead,chat.senderId,chat.senderName,chat.chatTo,chat.chatContent,chat.chatTime,chat.chatHasattach,chat.chatAttachsize,chat.chatId";
    String fromSql = "com.js.oa.chat.po.ChatUserPO cu left join cu.chat chat ";
    String whereSql = " where cu.empId=" + userId;
    String senderName = request.getParameter("senderName");
    String toName = request.getParameter("toName");
    String sortFlag = (request.getParameter("sortFlag") == null) ? "all" : request.getParameter("sortFlag");
    String databaseType = SystemCommon.getDatabaseType();
    ChatBean chatBean = new ChatBean();
    if (!"all".equals(timeScan)) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      if ("new".equals(timeScan)) {
        whereSql = String.valueOf(whereSql) + " and chat.chatTime < " + chatBean.getDateString(databaseType, calendar);
        calendar.add(6, -7);
        whereSql = String.valueOf(whereSql) + " and chat.chatTime > " + chatBean.getDateString(databaseType, calendar);
      } else if ("week".equals(timeScan)) {
        calendar.add(6, -7);
        whereSql = String.valueOf(whereSql) + " and chat.chatTime < " + chatBean.getDateString(databaseType, calendar);
      } else if ("month".equals(timeScan)) {
        calendar.add(2, -1);
        whereSql = String.valueOf(whereSql) + " and chat.chatTime < " + chatBean.getDateString(databaseType, calendar);
      } else if ("tmonth".equals(timeScan)) {
        calendar.add(2, -3);
        whereSql = String.valueOf(whereSql) + " and chat.chatTime < " + chatBean.getDateString(databaseType, calendar);
      } else if ("hyear".equals(timeScan)) {
        calendar.add(2, -6);
        whereSql = String.valueOf(whereSql) + " and chat.chatTime < " + chatBean.getDateString(databaseType, calendar);
      } else if ("year".equals(timeScan)) {
        calendar.add(2, -12);
        whereSql = String.valueOf(whereSql) + " and chat.chatTime < " + chatBean.getDateString(databaseType, calendar);
      } 
    } 
    if (senderName != null && !"null".equals(senderName) && !"".equals(senderName))
      whereSql = String.valueOf(whereSql) + " and chat.senderName like '%" + senderName + "%' "; 
    if (toName != null && !"null".equals(toName) && !"".equals(toName))
      whereSql = String.valueOf(whereSql) + " and chat.chatTo like '%" + toName + "%' "; 
    if (sortFlag.equals("isread")) {
      whereSql = String.valueOf(whereSql) + " order by cu.isRead asc";
    } else if (sortFlag.equals("noread")) {
      whereSql = String.valueOf(whereSql) + " order by cu.isRead desc";
    } else if (sortFlag.equals("timeup")) {
      whereSql = String.valueOf(whereSql) + " order by chat.chatTime asc";
    } else if (sortFlag.equals("timedown")) {
      whereSql = String.valueOf(whereSql) + " order by chat.chatTime desc";
    } else if (sortFlag.equals("small")) {
      whereSql = String.valueOf(whereSql) + " order by chat.chatAttachsize asc";
    } else if (sortFlag.equals("big")) {
      whereSql = String.valueOf(whereSql) + " order by chat.chatAttachsize desc";
    } else {
      whereSql = String.valueOf(whereSql) + " order by chat.chatTime desc";
    } 
    try {
      Page page = new Page(viewSql, fromSql, whereSql);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("mylist", list);
      request.setAttribute("timeScan", timeScan);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      if (request.getParameter("toUserId") != null) {
        request.setAttribute("toUserId", request.getParameter("toUserId"));
        request.setAttribute("pageParameters", "action,sortFlag,senderName,toName,timeScan");
      } else {
        request.setAttribute("pageParameters", "action,sortFlag,senderName,toName,timeScan");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void getNum(HttpServletRequest request, String userId) {
    ChatService chatService = new ChatService();
    String timeScan = request.getParameter("timeScan");
    String senderName = request.getParameter("senderName");
    String toName = request.getParameter("toName");
    String sql = "select count(cu.id) from com.js.oa.chat.po.ChatUserPO cu left join cu.chat cp";
    sql = String.valueOf(sql) + " where cu.isRead=0 and cu.chatStatus=1 and cu.empId=" + userId;
    if (senderName != null && !"".equals(senderName) && !"null".equals(senderName))
      sql = String.valueOf(sql) + " and cp.senderName like '%" + senderName + "%'"; 
    if (toName != null && !"".equals(toName) && !"null".equals(toName))
      sql = String.valueOf(sql) + " and cp.chatTo like '%" + toName + "%'"; 
    String databaseType = SystemCommon.getDatabaseType();
    ChatBean chatBean = new ChatBean();
    if (!"all".equals(timeScan)) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      if ("new".equals(timeScan)) {
        sql = String.valueOf(sql) + " and cp.chatTime < " + chatBean.getDateString(databaseType, calendar);
        calendar.add(6, -7);
        sql = String.valueOf(sql) + " and cp.chatTime > " + chatBean.getDateString(databaseType, calendar);
      } else if ("week".equals(timeScan)) {
        calendar.add(6, -7);
        sql = String.valueOf(sql) + " and cp.chatTime < " + chatBean.getDateString(databaseType, calendar);
      } else if ("month".equals(timeScan)) {
        calendar.add(2, -1);
        sql = String.valueOf(sql) + " and cp.chatTime < " + chatBean.getDateString(databaseType, calendar);
      } else if ("tmonth".equals(timeScan)) {
        calendar.add(2, -3);
        sql = String.valueOf(sql) + " and cp.chatTime < " + chatBean.getDateString(databaseType, calendar);
      } else if ("hyear".equals(timeScan)) {
        calendar.add(2, -6);
        sql = String.valueOf(sql) + " and cp.chatTime < " + chatBean.getDateString(databaseType, calendar);
      } else if ("year".equals(timeScan)) {
        calendar.add(2, -12);
        sql = String.valueOf(sql) + " and cp.chatTime < " + chatBean.getDateString(databaseType, calendar);
      } 
    } 
    String pSum = chatService.getCountByHQL(sql);
    request.setAttribute("gerenxiaoxishu", pSum);
    PressManageAction PressManageAction = new PressManageAction();
    String receivecount = PressManageAction.getNum(userId);
    request.setAttribute("receivecount", receivecount);
    String type = request.getParameter("messageType");
    if (type == null || "null".equals(type))
      type = ""; 
    String xiTongwherePara = "where (po.message_toUserId=" + userId + ") and po.message_type like '%" + type + "%'  and po.message_type<>'Chat' ";
    String audit = "";
    if (SystemCommon.getAudit() == 0)
      audit = " and message_type<>'audit' "; 
    if (databaseType.indexOf("mysql") >= 0) {
      xiTongwherePara = String.valueOf(xiTongwherePara) + " and po.message_status=1  and po.message_date_begin <= now() and po.message_date_end >= now() " + audit + " order by po.message_time desc";
    } else if (databaseType.indexOf("oracle") >= 0) {
      xiTongwherePara = String.valueOf(xiTongwherePara) + "  and po.message_status=1  and po.message_date_begin <= sysdate and po.message_date_end >= sysdate " + audit + " order by po.message_time desc";
    } 
    sql = "select count(po.message_id) from com.js.system.vo.messages.MessagesVO po " + xiTongwherePara;
    pSum = chatService.getCountByHQL(sql);
    request.setAttribute("xitongxiaoxishu", pSum);
  }
}
