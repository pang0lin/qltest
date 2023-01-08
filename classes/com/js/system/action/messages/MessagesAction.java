package com.js.system.action.messages;

import com.js.oa.chat.service.ChatService;
import com.js.oa.pressdeal.action.PressManageAction;
import com.js.system.service.messages.MessagesBD;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.text.SimpleDateFormat;
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

public class MessagesAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String wherePara = "";
    String strname = request.getParameter("str");
    String action = request.getParameter("action");
    String timetype = request.getParameter("timeScan");
    if (timetype == null || "null".equalsIgnoreCase(timetype) || " ".equals(timetype))
      timetype = "all"; 
    MessagesBD messagesBD = new MessagesBD();
    String databaseType = SystemCommon.getDatabaseType();
    String messageType = request.getParameter("messageType");
    if (action.equals("delBatch")) {
      if (request.getParameter("ids") != null)
        messagesBD.delBatch(request.getParameter("ids"), request.getParameter("type"), request.getParameter("timeScan"), userId); 
      action = "getAll";
      strname = "all";
    } 
    action.equals("search");
    if (action.equals("getAll")) {
      String type = request.getParameter("messageType");
      if (type == null || "null".equals(type))
        type = ""; 
      if (strname.equals("all"))
        wherePara = "where (po.message_toUserId=" + userId + ") and po.message_type like '%" + type + "%'  and po.message_type<>'Chat' "; 
      if (strname.equals("timeup"))
        wherePara = "where (po.message_toUserId=" + userId + ") and po.message_type like '%" + type + "%' and po.message_type<>'Chat' "; 
      if (timetype != null && !"null".equalsIgnoreCase(timetype) && !"".equals(timetype)) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(6, -7);
        if ("new".equals(timetype)) {
          if ("mysql".equals(databaseType)) {
            wherePara = String.valueOf(wherePara) + " and po.message_time>='" + sdf.format(calendar.getTime()) + "' ";
            if (type != null && !"null".equalsIgnoreCase(type) && !"".equals(type))
              wherePara = String.valueOf(wherePara) + " and po.message_type like '%" + type + "%'"; 
          } else {
            wherePara = String.valueOf(wherePara) + " and po.message_time>=to_date('" + sdf.format(calendar.getTime()) + "','YYYY-MM-DD HH24:MI:SS') ";
            if (type != null && !"null".equalsIgnoreCase(type) && !"".equals(type))
              wherePara = String.valueOf(wherePara) + " and po.message_type like '%" + type + "%'"; 
          } 
        } else if ("week".equals(timetype)) {
          if ("mysql".equals(databaseType)) {
            wherePara = String.valueOf(wherePara) + " and po.message_time<'" + sdf.format(calendar.getTime()) + "' ";
            if (type != null && !"null".equalsIgnoreCase(type) && !"".equals(type))
              wherePara = String.valueOf(wherePara) + " and po.message_type like '%" + type + "%'"; 
          } else {
            wherePara = String.valueOf(wherePara) + " and po.message_time<(to_date('" + sdf.format(calendar.getTime()) + "','YYYY-MM-DD HH24:MI:SS')) ";
            if (type != null && !"null".equalsIgnoreCase(type) && !"".equals(type))
              wherePara = String.valueOf(wherePara) + " and po.message_type like '%" + type + "%'"; 
          } 
        } 
      } 
      request.setAttribute("timeScan", timetype);
      String countSql = wherePara;
      String audit = "";
      if (SystemCommon.getAudit() == 0)
        audit = " and message_type<>'audit' "; 
      if (databaseType.indexOf("mysql") >= 0) {
        wherePara = String.valueOf(wherePara) + "  and po.message_date_begin <= now() and po.message_date_end >= now() " + audit + " order by po.message_time desc";
        countSql = String.valueOf(countSql) + " and po.message_status=1  and po.message_date_begin <= now() and po.message_date_end >= now() " + audit + " order by po.message_time desc";
      } else if (databaseType.indexOf("oracle") >= 0) {
        wherePara = String.valueOf(wherePara) + "  and po.message_date_begin <= sysdate and po.message_date_end >= sysdate " + audit + " order by po.message_time desc";
        countSql = String.valueOf(countSql) + "  and po.message_status=1  and po.message_date_begin <= sysdate and po.message_date_end >= sysdate " + audit + " order by po.message_time desc";
      } 
      request.setAttribute("strname", strname);
      request.setAttribute("type", type);
      list(request, wherePara);
      ChatService chatService = new ChatService();
      String sql = "select count(*) from com.js.system.vo.messages.MessagesVO po " + countSql;
      String pSum = chatService.getCountByHQL(sql);
      request.setAttribute("xitongxiaoxishu", pSum);
      sql = sql.replace("and po.message_status=1", "");
      pSum = chatService.getCountByHQL(sql);
      request.setAttribute("xitongxiaoxishutotal", pSum);
      sql = "select count(cu.id) from com.js.oa.chat.po.ChatUserPO cu where cu.isRead=0 and cu.chatStatus=1 and cu.empId=" + userId;
      pSum = chatService.getCountByHQL(sql);
      request.setAttribute("gerenxiaoxishu", pSum);
      sql = "select count(cu.id) from com.js.oa.chat.po.ChatUserPO cu where cu.empId=" + userId;
      pSum = chatService.getCountByHQL(sql);
      request.setAttribute("gerenxiaoxishutotal", pSum);
      PressManageAction PressManageAction = new PressManageAction();
      String receivecount = PressManageAction.getNum(userId);
      request.setAttribute("receivecount", receivecount);
      receivecount = PressManageAction.getTotalNum(userId);
      request.setAttribute("receivecounttotal", receivecount);
      return mapping.findForward("success");
    } 
    return mapping.findForward("success");
  }
  
  private void list(HttpServletRequest request, String wherePara) {
    Object object = (request.getSession(true).getAttribute("domainId") == null) ? "0" : request.getSession(true).getAttribute("domainId");
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    try {
      Page page = new Page("po.message_id,po.message_type,po.message_status,po.message_time,po.message_url,po.message_title", 
          "com.js.system.vo.messages.MessagesVO po ", 
          wherePara);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action,str,messageType,timeScan");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
