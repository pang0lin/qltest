package com.js.oa.messageWall.action;

import com.js.oa.messageWall.po.MessageWallGrantPO;
import com.js.oa.messageWall.service.MessageWallGrantService;
import com.js.util.util.StringSplit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MessageWallGrantAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    MessageWallGrantService service = new MessageWallGrantService();
    Long createdEmp = new Long(session.getAttribute("userId").toString());
    String method = request.getParameter("method");
    if ("setting".equals(method)) {
      MessageWallGrantPO mg = service.getMWGrant();
      request.setAttribute("mg", mg);
      return mapping.findForward("setting");
    } 
    if ("see".equals(method) || "update".equals(method)) {
      if ("update".equals(method))
        request.setAttribute("update", ""); 
      MessageWallGrantPO mg = service.getMWGrant();
      request.setAttribute("mg", mg);
      return mapping.findForward("update");
    } 
    if ("save".equals(method)) {
      String id = request.getParameter("id");
      String wallname = request.getParameter("wallname");
      String wallmaxnum = request.getParameter("wallmaxnum");
      String ids = request.getParameter("userRange");
      String wallreadname = request.getParameter("userRangeCh");
      String wallmanagerid = request.getParameter("wallmanagerid");
      String wallmanager = request.getParameter("wallmanager");
      String wallreader = "";
      String wallreadorg = "";
      String wallreadgroup = "";
      if (wallmaxnum == null || "".equals(wallmaxnum))
        wallmaxnum = "1"; 
      if (ids != null && !"".equals(ids)) {
        wallreader = StringSplit.splitWith(ids, "$", "*@");
        wallreadorg = StringSplit.splitWith(ids, "*", "@$");
        wallreadgroup = StringSplit.splitWith(ids, "@", "$*");
      } 
      MessageWallGrantPO mg = new MessageWallGrantPO((new Long(id)).longValue(), wallname, wallmanagerid, wallmanager, wallreader, wallreadorg, 
          wallreadgroup, wallreadname, (new Long(wallmaxnum)).longValue(), createdEmp.longValue());
      service.updateMWGrant(mg);
      request.setAttribute("updateGrant", "1");
      return mapping.findForward("update");
    } 
    return null;
  }
}
