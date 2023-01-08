package com.js.oa.messageWall.action;

import com.js.oa.messageWall.po.MessageWallPO;
import com.js.oa.messageWall.service.MessageWallService;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MessageWallAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String method = request.getParameter("method");
    MessageWallService service = new MessageWallService();
    Long messagewallEmpid = new Long(session.getAttribute("userId").toString());
    if ("list".equals(method)) {
      String databaseType = SystemCommon.getDatabaseType();
      String type = request.getParameter("type");
      boolean hasAddRight = service.getRightByUserId(messagewallEmpid.longValue());
      if (hasAddRight)
        request.setAttribute("hasAddRight", "yes"); 
      boolean isAdmin = service.isAdmin(messagewallEmpid.longValue());
      if (isAdmin)
        request.setAttribute("isAdmin", "yes"); 
      ManagerService managerBD = new ManagerService();
      HttpSession httpSession = request.getSession(true);
      StringBuffer whereSQL = new StringBuffer("(");
      whereSQL.append(managerBD.getRightFinalWhere(httpSession.getAttribute("userId").toString(), 
            httpSession.getAttribute("orgId").toString(), 
            "06*02*09", "m.messagewall_orgid", "m.messagewall_Empid"));
      whereSQL.append(" or (m.messagewall_empid=" + messagewallEmpid + ")) ");
      if (type != null && !"".equals(type)) {
        String name = request.getParameter("searchName");
        String content = request.getParameter("searchContent");
        String start = request.getParameter("searchStartDate");
        String end = request.getParameter("searchEndDate");
        String select = request.getParameter("selectTime");
        whereSQL.append(" and m.messagewall_empname like '%" + name + "%' ");
        whereSQL.append(" and m.messagewall_content like '%" + content + "%' ");
        if (select != null && "select".equals(select))
          if (databaseType.indexOf("mysql") >= 0) {
            whereSQL.append(" and m.messagewall_time between '").append(start)
              .append(" 00:00:00").append("' and '").append(end)
              .append(" 23:59:59'");
          } else {
            whereSQL.append(" and m.messagewall_time between to_date('").append(start).append(" 00:00:00")
              .append("','yyyy-mm-dd hh24:mi:ss')").append(" and ").append(" to_date('")
              .append(end).append(" 23:59:59").append("','yyyy-mm-dd hh24:mi:ss')");
          }  
        request.setAttribute("searchName", name);
        request.setAttribute("searchContent", content);
        request.setAttribute("sy", start.split("/")[0]);
        request.setAttribute("sm", start.split("/")[1]);
        request.setAttribute("sd", start.split("/")[2]);
        request.setAttribute("ey", end.split("/")[0]);
        request.setAttribute("em", end.split("/")[1]);
        request.setAttribute("ed", end.split("/")[2]);
        request.setAttribute("selectTime", select);
        request.setAttribute("type", type);
      } 
      List<MessageWallPO> mws = service.showAll(whereSQL.toString());
      request.setAttribute("list", mws);
      return mapping.findForward("list");
    } 
    String id = (request.getParameter("messagewallId") != null) ? request.getParameter("messagewallId").split("_")[1] : "";
    Long messagewallId = Long.valueOf("".equals(id) ? 0L : (new Long(id)).longValue());
    Long messagewallOrgid = new Long(session.getAttribute("orgId").toString());
    String messagewallName = session.getAttribute("userName").toString();
    String messagewallOrgname = session.getAttribute("orgName").toString();
    String messagewallContent = request.getParameter("messagewallContent");
    if (messagewallContent != null && !"".equals(messagewallContent))
      messagewallContent = URLDecoder.decode(messagewallContent, "utf-8"); 
    String messagewallClass = "";
    String messagewallAccounts = session.getAttribute("userAccount").toString();
    Date messagewallTime = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    int domain_id = 0;
    if ("add".equals(method)) {
      MessageWallPO mw = new MessageWallPO(messagewallEmpid.longValue(), messagewallName, messagewallOrgid.longValue(), messagewallOrgname, 
          messagewallContent, messagewallClass, messagewallAccounts, messagewallTime, domain_id);
      long newId = service.addMessage(mw);
      response.getWriter().print(String.valueOf(newId) + "*" + sdf.format(messagewallTime));
      return null;
    } 
    if ("update".equals(method)) {
      MessageWallPO mw = new MessageWallPO(messagewallId.longValue(), messagewallEmpid.longValue(), messagewallName, messagewallOrgid.longValue(), messagewallOrgname, 
          messagewallContent, messagewallClass, messagewallAccounts, messagewallTime, domain_id);
      int n = service.updateMessage(mw);
      response.getWriter().print(String.valueOf(n) + "*" + sdf.format(messagewallTime));
      return null;
    } 
    if ("delete".equals(method)) {
      int n = service.deleteMessage(messagewallId.longValue());
      response.getWriter().print(n);
      return null;
    } 
    if ("check".equals(method)) {
      int curNum = service.getCurNum(messagewallEmpid.longValue());
      int maxNum = service.getMaxNum();
      if (curNum < maxNum) {
        response.getWriter().print("ok");
      } else {
        response.getWriter().print("no");
      } 
      return null;
    } 
    return null;
  }
}
