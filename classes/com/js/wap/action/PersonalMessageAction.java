package com.js.wap.action;

import com.js.oa.chat.po.ChatPO;
import com.js.oa.chat.service.ChatService;
import com.js.wap.service.PersonalMessageBD;
import com.js.wap.util.WapUtil;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class PersonalMessageAction extends DispatchAction {
  public ActionForward personalMessageList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = (String)session.getAttribute("userId");
    String orgId = String.valueOf(session.getAttribute("orgId"));
    String orgIdString = (String)session.getAttribute("orgIdString");
    String version = (String)session.getAttribute("wapVersion");
    PersonalMessageBD bd = new PersonalMessageBD();
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    Map map = null;
    try {
      map = bd.getMessageList(userId, orgId, orgIdString, beginIndex, WapUtil.LIMITED);
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
    String name = "";
    PersonalMessageBD bd = new PersonalMessageBD();
    ChatPO chatPO = null;
    try {
      bd.setRead(chatId, userId);
      ChatService chatDB = new ChatService();
      chatPO = chatDB.getByChatID(chatId);
      name = chatDB.getName(chatPO.getSenderId());
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
    if ("3G".equals(version))
      return mapping.findForward("showMessage_3g"); 
    if ("COLOR".equals(version))
      return mapping.findForward("showMessage"); 
    return mapping.findForward("showMessage_3g");
  }
}
