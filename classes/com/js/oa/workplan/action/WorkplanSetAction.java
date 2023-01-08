package com.js.oa.workplan.action;

import com.js.oa.workplan.bean.WorkplanSetBean;
import com.js.oa.workplan.po.WorkplanGroupPO;
import com.js.oa.workplan.po.WorkplanProxyPO;
import com.js.oa.workplan.po.WorkplanStatusPO;
import com.js.oa.workplan.service.WorkplanSetService;
import com.js.util.util.DateHelper;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WorkplanSetAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    String action = request.getParameter("action");
    if ("addgroup".equals(action))
      return actionMapping.findForward("addgroup"); 
    if ("viewgroup".equals(action)) {
      WorkplanSetBean ws = new WorkplanSetBean();
      try {
        List list = ws.searchGroupList();
        request.setAttribute("groupList", list);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("viewgroup");
    } 
    if ("savegroup".equals(action)) {
      String groupName = request.getParameter("groupName");
      String groupLeaderId = request.getParameter("groupLeaderId");
      String groupLeaderName = request.getParameter("groupLeaderName");
      WorkplanGroupPO po = new WorkplanGroupPO();
      po.setGroupName(groupName);
      po.setGroupLeaderId(groupLeaderId);
      po.setGroupLeaderName(groupLeaderName);
      WorkplanSetService service = new WorkplanSetService();
      service.addGroup(po);
      return actionMapping.findForward("saveclose");
    } 
    if ("modify".equals(action)) {
      String groupId = request.getParameter("groupId");
      WorkplanSetService service = new WorkplanSetService();
      request.setAttribute("groupInfo", service.loadGroup(groupId));
      return actionMapping.findForward("modify");
    } 
    if ("modifysave".equals(action)) {
      WorkplanGroupPO po = new WorkplanGroupPO();
      po.setId(Long.valueOf(request.getParameter("groupId")).longValue());
      po.setGroupName(request.getParameter("groupName"));
      po.setGroupLeaderId(request.getParameter("groupLeaderId"));
      po.setGroupLeaderName(request.getParameter("groupLeaderName"));
      (new WorkplanSetService()).modifyGroup(po);
      return actionMapping.findForward("saveclose");
    } 
    if ("deletegroup".equals(action)) {
      String groupId = request.getParameter("groupId");
      (new WorkplanSetService()).delGroup(groupId);
      WorkplanSetBean ws = new WorkplanSetBean();
      try {
        List list = ws.searchGroupList();
        request.setAttribute("groupList", list);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("del");
    } 
    if ("viewstatus".equals(action)) {
      WorkplanSetBean ws = new WorkplanSetBean();
      try {
        List list = ws.searchStatusList();
        request.setAttribute("statusList", list);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("viewstatus");
    } 
    if ("addstatus".equals(action))
      return actionMapping.findForward("addstatus"); 
    if ("savestatus".equals(action)) {
      String statusName = request.getParameter("statusName");
      WorkplanStatusPO po = new WorkplanStatusPO();
      po.setStatusName(statusName);
      WorkplanSetService service = new WorkplanSetService();
      service.addStatus(po);
      return actionMapping.findForward("saveclose");
    } 
    if ("statusmodify".equals(action)) {
      String statusId = request.getParameter("statusId");
      WorkplanSetService service = new WorkplanSetService();
      request.setAttribute("statusInfo", service.loadStatus(statusId));
      return actionMapping.findForward("statusmodify");
    } 
    if ("statusmodifysave".equals(action)) {
      WorkplanStatusPO po = new WorkplanStatusPO();
      po.setId(Long.valueOf(request.getParameter("statusId")).longValue());
      po.setStatusName(request.getParameter("statusName"));
      (new WorkplanSetService()).modifyStatus(po);
      return actionMapping.findForward("saveclose");
    } 
    if ("deletestatus".equals(action)) {
      String statusId = request.getParameter("statusId");
      (new WorkplanSetService()).delStatus(statusId);
      WorkplanSetBean ws = new WorkplanSetBean();
      try {
        List list = ws.searchStatusList();
        request.setAttribute("statusList", list);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("viewstatus");
    } 
    if ("proxy".equals(action)) {
      WorkplanSetBean ws = new WorkplanSetBean();
      String leaderId = (String)session.getAttribute("userId");
      try {
        List list = ws.searchProxyList(leaderId);
        request.setAttribute("proxyList", list);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("proxy");
    } 
    if ("addsetproxy".equals(action))
      return actionMapping.findForward("addsetproxy"); 
    if ("saveproxy".equals(action)) {
      String leaderId = (String)session.getAttribute("userId");
      String leaderName = (String)session.getAttribute("userName");
      String proxyId = request.getParameter("proxyId");
      String proxyName = request.getParameter("proxyName");
      String proxyStatus = request.getParameter("proxyStatus");
      String proxybegin = request.getParameter("beginTime");
      String proxyend = request.getParameter("endTime");
      WorkplanProxyPO po = new WorkplanProxyPO();
      DateHelper sdf = new DateHelper();
      po.setLeaderId(Long.parseLong(leaderId));
      po.setLeaderName(leaderName);
      po.setProxyId(Long.parseLong(proxyId));
      po.setProxyName(proxyName);
      WorkplanSetService service = new WorkplanSetService();
      service.addProxy(po);
      return actionMapping.findForward("saveclose");
    } 
    if ("proxysetmodify".equals(action)) {
      String proxysetId = request.getParameter("proxysetId");
      WorkplanSetService service = new WorkplanSetService();
      request.setAttribute("proxyInfo", service.loadproxy(proxysetId));
      return actionMapping.findForward("proxysetmodify");
    } 
    if ("proxymodifysave".equals(action)) {
      DateHelper sdf = new DateHelper();
      WorkplanProxyPO po = new WorkplanProxyPO();
      po.setId(Long.valueOf(request.getParameter("proxyId")).longValue());
      po.setProxyName(request.getParameter("proxyName"));
      (new WorkplanSetService()).modifyProxy(po);
      return actionMapping.findForward("saveclose");
    } 
    if ("deleteproxy".equals(action)) {
      String proxysetId = request.getParameter("proxysetId");
      (new WorkplanSetService()).delProxy(proxysetId);
      WorkplanSetBean ws = new WorkplanSetBean();
      String leaderId = (String)session.getAttribute("userId");
      try {
        List list = ws.searchProxyList(leaderId);
        request.setAttribute("proxyList", list);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("proxy");
    } 
    return actionMapping.findForward("error");
  }
}
