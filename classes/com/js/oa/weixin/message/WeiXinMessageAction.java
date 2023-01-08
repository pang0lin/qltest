package com.js.oa.weixin.message;

import com.js.oa.chat.po.ChatAttachPO;
import com.js.oa.chat.po.ChatPO;
import com.js.oa.chat.service.ChatService;
import com.js.oa.weixin.common.service.WeiXinBD;
import com.js.wap.service.PersonalMessageBD;
import com.js.wap.util.WapUtil;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class WeiXinMessageAction extends DispatchAction {
  public ActionForward personalMessageList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = (String)session.getAttribute("userId");
    String orgId = String.valueOf(session.getAttribute("orgId"));
    String orgIdString = (String)session.getAttribute("orgIdString");
    String version = (String)session.getAttribute("wapVersion");
    String keyword = (request.getParameter("keyword") != null) ? request.getParameter("keyword") : "";
    String from = request.getParameter("from");
    if (keyword != null && !"".equals(keyword))
      keyword = URLDecoder.decode(keyword, "utf-8"); 
    WeiXinBD bd = new WeiXinBD();
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    Map map = null;
    try {
      map = bd.getMessageList(userId, orgId, orgIdString, beginIndex, WapUtil.LIMITED, keyword, from);
    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("ERROR", "错误：" + e);
      if ("3G".equals(version))
        return mapping.findForward("error_3g"); 
      if ("COLOR".equals(version))
        return mapping.findForward("error"); 
      return mapping.findForward("error_3g");
    } 
    int recordCount = ((Integer)map.get("RECORD_COUNT")).intValue();
    request.setAttribute("RECORD_COUNT", Integer.valueOf(recordCount));
    request.setAttribute("personalMessageMap", map);
    request.setAttribute("keyword", keyword);
    request.setAttribute("from", from);
    if ("3G".equals(version))
      return mapping.findForward("personalMessageList_3g"); 
    if ("COLOR".equals(version))
      return mapping.findForward("personalMessageList"); 
    return mapping.findForward("personalMessageList_3g");
  }
  
  public ActionForward showMessage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = (String)session.getAttribute("userId");
    String version = (String)session.getAttribute("wapVersion");
    String chatId = request.getParameter("chatId");
    String userAccounts = request.getParameter("userAccounts");
    String name = "";
    PersonalMessageBD bd = new PersonalMessageBD();
    ChatPO chatPO = null;
    List<ChatAttachPO> chatAttachList = null;
    try {
      bd.setRead(chatId, userId);
      ChatService chatDB = new ChatService();
      chatPO = chatDB.getByChatID(chatId);
      name = chatDB.getName(chatPO.getSenderId());
      chatAttachList = chatDB.getChatAttach(chatId);
    } catch (Exception e) {
      request.setAttribute("ERROR", "错误：" + e);
      e.printStackTrace();
      if ("3G".equals(version))
        return mapping.findForward("error_3g"); 
      if ("COLOR".equals(version))
        return mapping.findForward("error"); 
      return mapping.findForward("error_3g");
    } 
    request.setAttribute("chatPO", chatPO);
    request.setAttribute("name", name);
    request.setAttribute("userAccounts", userAccounts);
    request.setAttribute("chatAttachList", chatAttachList);
    if (request.getParameter("from") != null)
      request.setAttribute("from", request.getParameter("from")); 
    if ("3G".equals(version))
      return mapping.findForward("showMessage_3g"); 
    if ("COLOR".equals(version))
      return mapping.findForward("showMessage"); 
    return mapping.findForward("showMessage_3g");
  }
  
  public ActionForward personalCuibanList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = (String)session.getAttribute("userId");
    String pressStatus = "1";
    String keyword = (request.getParameter("keyword") != null) ? request.getParameter("keyword") : "";
    String from = request.getParameter("from");
    if (keyword != null && !"".equals(keyword))
      keyword = URLDecoder.decode(keyword, "utf-8"); 
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    WeiXinBD bd = new WeiXinBD();
    Map map = null;
    try {
      map = bd.personalCuibanList(userId, beginIndex, WapUtil.LIMITED, keyword, pressStatus);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    int recordCount = ((Integer)map.get("RECORD_COUNT")).intValue();
    request.setAttribute("RECORD_COUNT", Integer.valueOf(recordCount));
    request.setAttribute("personalMessageMap", map);
    request.setAttribute("keyword", keyword);
    return mapping.findForward("showCuibanMessage");
  }
}
