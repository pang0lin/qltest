package com.js.oa.hr.kq.action;

import com.js.oa.hr.kq.po.KqEvectionPO;
import com.js.oa.hr.kq.service.KqEvectionBD;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.Page;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class KqEvectionAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws NumberFormatException, Exception {
    KqEvectionActionForm form = (KqEvectionActionForm)actionForm;
    String tag = "list";
    String action = (httpServletRequest.getParameter("action") == null) ? 
      "list" : httpServletRequest.getParameter("action");
    String orgId = httpServletRequest.getSession(true).getAttribute("orgId")
      .toString();
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId").toString();
    String userName = httpServletRequest.getSession(true).getAttribute("userName").toString();
    KqEvectionBD kqEvectionBD = new KqEvectionBD();
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
              .toString(), "6");
          if (tmpl != null)
            flowList = (List)tmpl; 
          if (flowList != null && flowList.size() == 1) {
            Object[] flowObj = flowList.get(0);
            String processId = String.valueOf(flowObj[2]);
            String processName = String.valueOf(flowObj[3]);
            String processType = String.valueOf(flowObj[5]);
            String tableId = String.valueOf(flowObj[4]);
            String remindField = (flowObj[6] == null) ? "" : String.valueOf(flowObj[6]);
            StringBuffer forwardURL = new StringBuffer("/KqEvectionAction.do?action=add&moduleId=6&");
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
        String evectionId = httpServletRequest.getParameter("evectionid");
        if (evectionId == null)
          evectionId = httpServletRequest.getParameter("record"); 
        KqEvectionPO kqEvectionPO = new KqEvectionPO();
        kqEvectionPO = kqEvectionBD.searchById((new Long(evectionId)).longValue());
        httpServletRequest.setAttribute("kqEvectionPO", kqEvectionPO);
        tag = "update";
      } else if (action.equals("isreturn")) {
        String evectionid = httpServletRequest.getParameter("evectionid");
        kqEvectionBD.isreturn((new Long(evectionid)).longValue());
        list(httpServletRequest);
        tag = "list";
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
    if (!orgId.equals("")) {
      StringTokenizer st = new StringTokenizer(orgId, 
          "*");
      searchSQL = String.valueOf(searchSQL) + " and ( po.orgId in (";
      while (st.hasMoreTokens())
        searchSQL = String.valueOf(searchSQL) + 
          st.nextToken() + ","; 
      searchSQL = String.valueOf(searchSQL.substring(0, searchSQL.length() - 1)) + 
        ")) ";
    } 
    if (!userId.equals("")) {
      StringTokenizer st = new StringTokenizer(
          userId, "$");
      searchSQL = String.valueOf(searchSQL) + " and ( po.userId in (";
      while (st.hasMoreTokens())
        searchSQL = String.valueOf(searchSQL) + st.nextToken() + ","; 
      searchSQL = String.valueOf(searchSQL.substring(0, searchSQL.length() - 1)) + 
        ")) ";
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
    String whereSql = "where (po.userId =" + curUserId + " or " + whereSQL + " )" + searchSQL;
    whereSql = String.valueOf(whereSql) + " order by po.id desc";
    Page page = new Page(
        "distinct po.id,po.userId,po.userName,po.orgId,po.orgName,po.place,po.startTime,po.endTime,po.cause,po.projectId,po.status,po.isreturn", 
        "com.js.oa.hr.kq.po.KqEvectionPO po ", 
        whereSql);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("list", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "action,status,userId,orgId,userName,orgName,searchDate,start_date,end_date");
  }
}
