package com.js.oa.online.action;

import com.js.oa.online.bean.ChatEJBBean;
import com.js.oa.online.bean.OnlinerEJBBean;
import com.js.oa.online.service.ChatDB;
import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ChatAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String wherePara = "";
    String strname = request.getParameter("str");
    String action = request.getParameter("action");
    String username = request.getParameter("username");
    String receivename = request.getParameter("receivename");
    if (action.equals("search")) {
      OnlinerEJBBean onlinerEJBBean = new OnlinerEJBBean();
      String userIds = StaticParam.getEmpIdByName(username);
      String receiveIds = StaticParam.getEmpIdByName(receivename);
      if (strname.equals("all")) {
        wherePara = "where 1<>1 ";
        if (!"-1000".equals(receiveIds) && !"-1000".equals(userIds)) {
          wherePara = String.valueOf(wherePara) + "or (po.toUserId in (" + receiveIds + ") and po.sendUserId in(" + userIds + ") and po.msgSelfFlag='0' and po.sendUserId=" + userId + ") " + 
            "or ( po.toUserId in (" + receiveIds + ") and po.sendUserId in(" + userIds + ") and po.msgSelfFlag='1' and po.toUserId=" + userId + ") ";
        } else if (!"-1000".equals(receiveIds)) {
          wherePara = String.valueOf(wherePara) + "or (po.toUserId in (" + receiveIds + ") and po.msgSelfFlag='0' and po.sendUserId=" + userId + ") " + 
            "or ( po.toUserId in (" + receiveIds + ") and po.msgSelfFlag='1' and po.toUserId=" + userId + ") ";
        } else if (!"-1000".equals(userIds)) {
          wherePara = String.valueOf(wherePara) + "or ( po.sendUserId in (" + userIds + ") and po.msgSelfFlag='0' and po.sendUserId=" + userId + ") " + 
            "or ( po.sendUserId in (" + userIds + ") and po.msgSelfFlag='1' and po.toUserId=" + userId + ") ";
        } 
        wherePara = String.valueOf(wherePara) + " order by po.chatTime desc";
      } 
      request.setAttribute("strname", strname);
      getNum(request, userId);
      list(request, wherePara);
      return mapping.findForward("success");
    } 
    if (action.equals("getAll")) {
      String whereUserName = " (1=1";
      OnlinerEJBBean onlinerEJBBean = new OnlinerEJBBean();
      List<String> list = onlinerEJBBean.getID(username);
      if (list != null && list.size() > 0) {
        for (int i = 0; i < list.size(); i++) {
          if (i == 0) {
            whereUserName = String.valueOf(whereUserName) + " and ((po.sendUserId=" + list.get(i) + ") ";
          } else {
            whereUserName = String.valueOf(whereUserName) + " or (po.sendUserId=" + list.get(i) + ") ";
          } 
        } 
        whereUserName = String.valueOf(whereUserName) + ")) and ";
      } else {
        whereUserName = String.valueOf(whereUserName) + ") and ";
      } 
      if (strname == null)
        wherePara = "where " + whereUserName + "((po.toUserId=" + userId + " and po.msgSelfFlag='1') or (po.sendUserId=" + userId + " and po.msgSelfFlag='0')) " + "order by po.chatId asc"; 
      if (strname.equals("all"))
        wherePara = "where " + whereUserName + "((po.toUserId=" + userId + " and po.msgSelfFlag='1') or (po.sendUserId=" + userId + " and po.msgSelfFlag='0')) " + "order by po.chatId desc"; 
      if (strname.equals("isread"))
        wherePara = "where " + whereUserName + "((po.toUserId=" + userId + "  and po.msgSelfFlag='1') or (po.sendUserId=" + userId + " and po.msgSelfFlag='0')) " + "order by po.isRead asc"; 
      if (strname.equals("noread"))
        wherePara = "where " + whereUserName + "((po.toUserId=" + userId + "  and po.msgSelfFlag='1') or (po.sendUserId=" + userId + " and po.msgSelfFlag='0')) " + "order by po.isRead desc"; 
      if (strname.equals("timeup"))
        wherePara = "where " + whereUserName + "((po.toUserId=" + userId + "  and po.msgSelfFlag='1') or (po.sendUserId=" + userId + " and po.msgSelfFlag='0')) " + "order by po.chatTime asc"; 
      if (strname.equals("timedown"))
        wherePara = "where " + whereUserName + "((po.toUserId=" + userId + "  and po.msgSelfFlag='1') or (po.sendUserId=" + userId + " and po.msgSelfFlag='0')) " + "order by po.chatTime desc"; 
      if (strname.equals("small"))
        wherePara = "where " + whereUserName + "((po.toUserId=" + userId + "  and po.msgSelfFlag='1') or (po.sendUserId=" + userId + " and po.msgSelfFlag='0')) " + "order by po.accessorySize desc"; 
      if (strname.equals("big"))
        wherePara = "where " + whereUserName + "((po.toUserId=" + userId + "  and po.msgSelfFlag='1') or (po.sendUserId=" + userId + " and po.msgSelfFlag='0')) " + "order by po.accessorySize asc"; 
      getNum(request, userId);
      request.setAttribute("strname", strname);
      list(request, wherePara);
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
      Page page = new Page("po.chatId,po.sendUserId,po.content,po.chatHasaccessory,po.chatTime,po.isRead,po.accessorySize,po.toUserId,po.chatFlag,po.msgSelfFlag", 
          "com.js.oa.online.po.ChatPO po ", 
          String.valueOf(wherePara.substring(0, wherePara.indexOf("order by"))) + " group by po.chatFlag,po.sendUserId,po.msgSelfFlag,po.chatTime  " + wherePara.substring(wherePara.indexOf("order by"), wherePara.length()));
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String hql = "select po.chatFlag,po.sendUserId,po.msgSelfFlag,po.chatTime from com.js.oa.online.po.ChatPO po " + 
        wherePara.substring(0, wherePara.indexOf("order by")) + " group by po.chatFlag,po.sendUserId,po.msgSelfFlag,po.chatTime  " + wherePara.substring(wherePara.indexOf("order by"), wherePara.length());
      int countNum = (new ChatDB()).getCountChat(hql);
      String recordCount = (new StringBuilder(String.valueOf(countNum))).toString();
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      if (request.getParameter("toUserId") != null) {
        request.setAttribute("toUserId", request.getParameter("toUserId"));
        request.setAttribute("pageParameters", "action,str,username,receivename,toUserId");
      } else {
        request.setAttribute("pageParameters", "action,str,username,receivename");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void getNum(HttpServletRequest request, String userId) throws Exception {
    String sql = "select po.chatId from com.js.oa.online.po.ChatPO po where po.isRead=1 and ((po.toUserId=" + userId + " and po.msgSelfFlag='1') or (po.sendUserId=" + userId + " and po.msgSelfFlag='0')) ";
    ChatEJBBean bean = new ChatEJBBean();
    List tempList = bean.getListByYourSQL(sql);
    int listCount = 0;
    if (tempList != null && tempList.size() > 0)
      listCount = tempList.size(); 
    request.setAttribute("gerenxiaoxishu", Integer.valueOf(listCount));
    String type = request.getParameter("messageType");
    if (type == null || "null".equals(type))
      type = ""; 
    String xiTongwherePara = "where (po.message_toUserId=" + userId + ") and po.message_type like '%" + type + "%'  and po.message_type<>'Chat' ";
    String databaseType = SystemCommon.getDatabaseType();
    String audit = "";
    if (SystemCommon.getAudit() == 0)
      audit = " and message_type<>'audit' "; 
    if (databaseType.indexOf("mysql") >= 0) {
      xiTongwherePara = String.valueOf(xiTongwherePara) + " and po.message_status=1  and po.message_date_begin <= now() and po.message_date_end >= now() " + audit + " order by po.message_time desc";
    } else if (databaseType.indexOf("oracle") >= 0) {
      xiTongwherePara = String.valueOf(xiTongwherePara) + "  and po.message_status=1  and po.message_date_begin <= sysdate and po.message_date_end >= sysdate " + audit + " order by po.message_time desc";
    } 
    sql = "select po.message_id from com.js.system.vo.messages.MessagesVO po " + xiTongwherePara;
    tempList = bean.getListByYourSQL(sql);
    listCount = 0;
    if (tempList != null && tempList.size() > 0)
      listCount = tempList.size(); 
    request.setAttribute("xitongxiaoxishu", Integer.valueOf(listCount));
  }
}
