package com.js.oa.message.action;

import com.js.oa.message.service.MessageBD;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MsManageInfoAction extends Action {
  private int pageSize = 15;
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    String action = httpServletRequest.getParameter("action");
    MessageForm messageForm = (MessageForm)actionForm;
    MessageBD messageBD = new MessageBD();
    String saveType = httpServletRequest.getParameter("saveType");
    HttpSession session = httpServletRequest.getSession(true);
    Long userID = new Long(session.getAttribute("userId").toString());
    String userName = session.getAttribute("userName").toString();
    Long orgId = new Long(session.getAttribute("orgId").toString());
    if ("countBook".equals(action)) {
      httpServletRequest.setAttribute("countResult", (new MessageBD()).getMsAccountInfo((session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString()));
      return actionMapping.findForward("countBook");
    } 
    return actionMapping.findForward("list");
  }
}
