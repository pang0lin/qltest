package com.js.oa.hr.kq.action;

import com.js.oa.hr.kq.po.KqOvertimePO;
import com.js.oa.hr.kq.service.KqOvertimeBD;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.groupmanager.GroupBD;
import com.js.system.service.usermanager.UserBD;
import com.js.util.page.Page;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class KqOvertimeAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws NumberFormatException, Exception {
    KqOvertimeActionForm form = (KqOvertimeActionForm)actionForm;
    String tag = "list";
    String action = (httpServletRequest.getParameter("action") == null) ? 
      "list" : httpServletRequest.getParameter("action");
    String orgId = httpServletRequest.getSession(true).getAttribute("orgId")
      .toString();
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId").toString();
    String userName = httpServletRequest.getSession(true).getAttribute("userName").toString();
    KqOvertimeBD kqOvertimeBD = new KqOvertimeBD();
    if (action.equals("list")) {
      list(httpServletRequest);
    } else {
      if (action.equals("add")) {
        tag = "add";
        if (httpServletRequest.getParameter("processId") == null) {
          ProcessBD procbd = new ProcessBD();
          List<Object[]> flowList = new ArrayList();
          Object tmpl = procbd.getUserProcess(httpServletRequest
              .getSession()
              .getAttribute("userId").toString(), 
              httpServletRequest.getSession()
              .getAttribute("orgIdString")
              .toString(), "7");
          if (tmpl != null)
            flowList = (List)tmpl; 
          if (flowList != null && flowList.size() == 1) {
            Object[] flowObj = flowList.get(0);
            String processId = String.valueOf(flowObj[2]);
            String processName = String.valueOf(flowObj[3]);
            String processType = String.valueOf(flowObj[5]);
            String tableId = String.valueOf(flowObj[4]);
            String remindField = (flowObj[6] == null) ? "" : String.valueOf(flowObj[6]);
            StringBuffer forwardURL = new StringBuffer("/KqOvertimeAction.do?action=add&moduleId=7&");
            forwardURL.append("processType=").append(processType)
              .append("&remindField=").append(remindField)
              .append("&processId=").append(processId)
              .append("&tableId=").append(tableId)
              .append("&processName=").append(processName);
            return new ActionForward(forwardURL.toString());
          } 
          if (flowList != null && flowList.size() > 1) {
            httpServletRequest.setAttribute("flowList", flowList);
            return actionMapping.findForward("selectFlow");
          } 
          return actionMapping.findForward("noFlow");
        } 
        return actionMapping.findForward("add");
      } 
      if (action.equals("update")) {
        String overtimeId = httpServletRequest.getParameter("overtimeid");
        if (overtimeId == null)
          overtimeId = httpServletRequest.getParameter("record"); 
        KqOvertimePO kqOvertimePO = new KqOvertimePO();
        kqOvertimePO = kqOvertimeBD.searchById((new Long(overtimeId)).longValue());
        httpServletRequest.setAttribute("kqOvertimePO", kqOvertimePO);
        tag = "update";
      } else if (action.equals("detail")) {
        String overtimeId = httpServletRequest.getParameter("overtimeid");
        KqOvertimePO kqOvertimePO = new KqOvertimePO();
        kqOvertimePO = kqOvertimeBD.searchById((new Long(overtimeId)).longValue());
        httpServletRequest.setAttribute("kqOvertimePO", kqOvertimePO);
        tag = "detail";
      } 
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest httpServletRequest) throws NumberFormatException, Exception {
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId").toString();
    Long domainId = (httpServletRequest.getSession(true).getAttribute(
        "domainId") == null) ? 
      Long.valueOf("0") : 
      Long.valueOf(httpServletRequest.getSession(true)
        .getAttribute("domainId").toString());
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    ManagerService mbd = new ManagerService();
    String whereSQL = mbd.getRightFinalWhere(curUserId, httpServletRequest.getSession(true).getAttribute("orgId")
        .toString(), "07*55*09", 
        "po.orgId", 
        "po.userId");
    String orgId = (httpServletRequest.getParameter("orgId") == null) ? "" : httpServletRequest.getParameter("orgId").toString();
    String orgName = (httpServletRequest.getParameter("orgName") == null) ? "" : httpServletRequest.getParameter("orgName").toString();
    String userId = (httpServletRequest.getParameter("userId") == null) ? "" : httpServletRequest.getParameter("userId").toString();
    String userName = (httpServletRequest.getParameter("userName") == null) ? "" : httpServletRequest.getParameter("userName").toString();
    String status = (httpServletRequest.getParameter("status") == null) ? "none" : httpServletRequest.getParameter("status").toString();
    String searchDate = (httpServletRequest.getParameter("searchDate") == null) ? "" : httpServletRequest.getParameter("searchDate").toString();
    String start_date = "";
    String end_date = "";
    String searchSQL = "";
    if (!userId.equals("")) {
      userId = userId.substring(1, userId.length() - 1);
      String searchUser = " and (";
      String[] userIds = userId.split("\\$\\$");
      if (userIds.length > 0) {
        for (int i = 0; i < userIds.length; i++)
          searchUser = String.valueOf(searchUser) + "po.attendUserId like '%$" + userIds[i] + "$%' or "; 
        UserBD userBD = new UserBD();
        List<E> list1 = new ArrayList();
        list1 = userBD.searchOrgIdByUserId(userId);
        for (int j = 0; j < list1.size(); j++)
          searchUser = String.valueOf(searchUser) + "po.attendOrgId like '%*" + list1.get(j).toString() + "*%' or "; 
      } 
      searchUser = searchUser.substring(0, searchUser.lastIndexOf("or"));
      searchUser = String.valueOf(searchUser) + " )";
      searchSQL = String.valueOf(searchSQL) + searchUser;
    } 
    if (!orgId.equals("")) {
      orgId = orgId.substring(1, orgId.length() - 1);
      String searchorg = " and (";
      String[] orgIds = orgId.split("\\*\\*");
      if (orgIds.length > 0) {
        for (int i = 0; i < orgIds.length; i++)
          searchorg = String.valueOf(searchorg) + "po.attendOrgId like '%*" + orgIds[i] + "*%' or "; 
        searchorg = searchorg.substring(0, searchorg.lastIndexOf("or"));
      } 
      searchorg = String.valueOf(searchorg) + " )";
      searchSQL = String.valueOf(searchSQL) + searchorg;
    } 
    if (!"none".equals(status))
      searchSQL = String.valueOf(searchSQL) + " and po.status =" + status; 
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    if ("1".equals(searchDate)) {
      start_date = (httpServletRequest.getParameter("start_date") == null) ? "" : httpServletRequest.getParameter("start_date");
      end_date = (httpServletRequest.getParameter("end_date") == null) ? "" : httpServletRequest.getParameter("end_date");
      Date bdate = new Date(String.valueOf(start_date) + " 0:00");
      searchSQL = String.valueOf(searchSQL) + " and po.startTime >='" + dateFormat.format(bdate) + "'";
      Date edate = new Date(String.valueOf(end_date) + " 23:30");
      searchSQL = String.valueOf(searchSQL) + " and po.endTime <= '" + dateFormat.format(edate) + "'";
    } 
    String orgIdString = httpServletRequest.getSession().getAttribute("orgIdString").toString();
    StringBuffer buffer = new StringBuffer();
    orgIdString = buffer.append("$").append(orgIdString).append("$").toString();
    String[] orgIdArray = orgIdString.split("\\$\\$");
    GroupBD groupBD = new GroupBD();
    List groupList = null;
    groupList = groupBD.searchByUserid((new Long(curUserId)).longValue());
    buffer = new StringBuffer("((");
    if (orgIdArray.length > 0)
      for (int i = 0; i < orgIdArray.length; i++) {
        if (!"".equals(orgIdArray[i]))
          buffer.append(" po.attendOrgId like '%").append(orgIdArray[i]).append("%' or "); 
      }  
    buffer.append(" po.attendUserId like '%").append(curUserId).append("%'");
    buffer.append(") and po.status=100 ) ");
    String whereSql = "where  ( po.userId =" + curUserId + " or " + buffer.toString() + " or " + whereSQL + " )" + searchSQL;
    whereSql = String.valueOf(whereSql) + " order by po.id desc";
    Page page = new Page(
        "distinct po.id,po.userName,po.cause,po.projectId,po.startTime,po.endTime,po.attendUserId,po.attendUserName,po.attendOrgId,po.attendGroupId,po.status,po.userId", 
        "com.js.oa.hr.kq.po.KqOvertimePO po ", 
        whereSql);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("list", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,status,userId,orgId,userName,orgName,searchDate,start_date,end_date");
  }
}
