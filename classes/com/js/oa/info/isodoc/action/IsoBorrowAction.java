package com.js.oa.info.isodoc.action;

import com.js.oa.info.isodoc.po.IsoBorrowUserPO;
import com.js.oa.info.isodoc.service.IsoDocBD;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.jsflow.util.WorkflowCommon;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class IsoBorrowAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    String action = httpServletRequest.getParameter("action");
    IsoInfoActionForm isoInfoActionForm = (IsoInfoActionForm)actionForm;
    if ("list".equals(action)) {
      view(httpServletRequest);
    } else {
      if ("add".equals(action))
        return actionMapping.findForward("add"); 
      if ("load".equals(action)) {
        load(httpServletRequest, actionForm);
        return actionMapping.findForward("modi");
      } 
      if ("del".equals(action)) {
        del(httpServletRequest);
        view(httpServletRequest);
      } else if ("listLoad".equals(action)) {
        ActionForward forward = new ActionForward();
        forward.setPath(listLoad(httpServletRequest, httpServletResponse));
        return forward;
      } 
    } 
    return actionMapping.findForward("view");
  }
  
  private void add(HttpServletRequest httpServletRequest, IsoInfoActionForm isoInfoActionForm) {}
  
  private void del(HttpServletRequest httpServletRequest) {
    IsoDocBD bd = new IsoDocBD();
    bd.deleteBorrow(httpServletRequest.getParameter("ids"));
  }
  
  private void load(HttpServletRequest httpServletRequest, ActionForm actionForm) {
    IsoDocBD bd = new IsoDocBD();
    IsoBorrowUserPO po = bd.loadBorrowUserPO(httpServletRequest.getParameter("record"));
    IsoInfoActionForm form = (IsoInfoActionForm)actionForm;
    httpServletRequest.setAttribute("isoBorrowUserPO", po);
    WorkflowCommon workflowCommon = new WorkflowCommon();
    String curModifyField = "";
    if (httpServletRequest.getParameter("resubmit") != null && 
      "1".equals(httpServletRequest.getParameter("resubmit"))) {
      curModifyField = "";
    } else {
      curModifyField = workflowCommon.getCurActivityWriteField(httpServletRequest);
    } 
    httpServletRequest.setAttribute("curModifyField", curModifyField);
    httpServletRequest.setAttribute("myform", form);
  }
  
  private String listLoad(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    WorkFlowCommonBD wfcBD = new WorkFlowCommonBD();
    Map workMap = wfcBD.getWorkInfo("36", httpServletRequest.getParameter("record"));
    String activityName = (workMap.get("activityName") == null) ? "" : URLEncoder.encode(workMap.get("activityName").toString());
    String submitPerson = (workMap.get("submitPerson") == null) ? "" : URLEncoder.encode(workMap.get("submitPerson").toString());
    String processName = (workMap.get("processName") == null) ? "" : URLEncoder.encode(workMap.get("processName").toString());
    String standForUserName = (workMap.get("standForUserName") == null) ? "" : URLEncoder.encode(workMap.get("standForUserName").toString());
    String sendFileLink = 
      "/IsoBorrowAction.do?action=load&activityName=" + 
      activityName + 
      "&submitPersonId=" + workMap.get("submitPersonId") + "&submitPerson=" + 
      submitPerson + 
      "&work=" + workMap.get("work") + "&workType=" + workMap.get("workType") + 
      "&activity=" + 
      workMap.get("activity") + "&table=" + workMap.get("tableId") + 
      "&record=" + 
      httpServletRequest.getParameter("record") + "&processName=" + 
      processName + 
      "&workStatus=1&submitTime=" + workMap.get("submitTime") + "&processId=" + 
      workMap.get("processId") + 
      "&stepCount=" + workMap.get("stepCount") + "&isStandForWork=" + 
      workMap.get("isStandForWork") + 
      "&standForUserId=" + workMap.get("standForUserId") + 
      "&standForUserName=" + standForUserName + "&submitPersonTime=" + workMap.get("submitTime");
    return sendFileLink;
  }
  
  private void view(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    String viewSql = "po.isoBorrowUserId,po.userId,po.userName,po.orgId,po.orgName,po.informationId,po.informationName,po.inforChannelName,po.inforChannelId,po.borrowBeginTime,po.borrowEndTime,po.borrowStatus,po.borrowReason,po.documentNO";
    String fromSql = "com.js.oa.info.isodoc.po.IsoBorrowUserPO po";
    ManagerService mbd = new ManagerService();
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId").toString();
    String curOrgId = httpServletRequest.getSession(true).getAttribute(
        "orgId").toString();
    String orgIdString = httpServletRequest.getSession(true).getAttribute(
        "orgIdString").toString();
    String whereSql = "where 1=1  ";
    if (httpServletRequest.getParameter("informationName") != null && 
      
      !httpServletRequest.getParameter("informationName").toString().equals("")) {
      whereSql = String.valueOf(whereSql) + " and  po.informationName like '%" + 
        httpServletRequest.getParameter("informationName") + 
        "%'";
      httpServletRequest.setAttribute("informationName", 
          httpServletRequest.getParameter(
            "informationName"));
    } 
    if (httpServletRequest.getParameter("documentNo") != null && 
      
      !httpServletRequest.getParameter("documentNo").toString().equals("")) {
      whereSql = String.valueOf(whereSql) + " and po.documentNO like '%" + 
        httpServletRequest.getParameter("documentNo") + 
        "%'";
      httpServletRequest.setAttribute("documentNo", 
          httpServletRequest.getParameter(
            "documentNo"));
    } 
    if (httpServletRequest.getParameter("borrowUserName") != null && 
      
      !httpServletRequest.getParameter("borrowUserName").toString().equals("")) {
      whereSql = String.valueOf(whereSql) + " and po.userName like '%" + 
        httpServletRequest.getParameter("borrowUserName") + 
        "%'";
      httpServletRequest.setAttribute("borrowUserName", 
          httpServletRequest.getParameter(
            "borrowUserName"));
    } 
    String searchBeginDate = "", searchEndDate = "";
    if (httpServletRequest.getParameter("searchBeginDate") != null && 
      !httpServletRequest.getParameter("searchBeginDate").toString().equals("")) {
      searchBeginDate = httpServletRequest.getParameter("searchBeginDate");
      httpServletRequest.setAttribute("searchBeginDate", 
          httpServletRequest.getParameter("searchBeginDate"));
      searchBeginDate = searchBeginDate.replaceAll("/", "-");
      searchBeginDate = String.valueOf(searchBeginDate) + " 00:00:00";
    } 
    if (httpServletRequest.getParameter("searchEndDate") != null && 
      !httpServletRequest.getParameter("searchEndDate").toString().equals("")) {
      searchEndDate = httpServletRequest.getParameter("searchEndDate");
      httpServletRequest.setAttribute("searchEndDate", 
          httpServletRequest.getParameter("searchEndDate"));
      searchEndDate = searchEndDate.replaceAll("/", "-");
      searchEndDate = String.valueOf(searchEndDate) + " 23:59:59";
    } 
    String databaseType = 
      SystemCommon.getDatabaseType();
    String searchDate = httpServletRequest.getParameter("searchDate");
    if (searchDate != null)
      if (databaseType.indexOf("mysql") >= 0) {
        whereSql = String.valueOf(whereSql) + " and '" + searchBeginDate + "' > =po.borrowBeginTime and '" + searchEndDate + 
          "' >=po.borrowEndTime";
      } else {
        whereSql = String.valueOf(whereSql) + " and  JSDB.FN_STRTODATE('" + searchBeginDate + 
          "','L') >=po.borrowBeginTime and JSDB.FN_STRTODATE('" + 
          searchEndDate + "','L') > =po.borrowEndTime";
      }  
    whereSql = String.valueOf(whereSql) + " and po.domainId=" + domainId + " and borrowStatus='1' ";
    whereSql = String.valueOf(whereSql) + " order by po.borrowBeginTime desc ";
    System.out.println("borrowList:" + whereSql);
    list(httpServletRequest, viewSql, fromSql, whereSql);
  }
  
  private void list(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    if (list.size() == 0 && offset >= 15) {
      offset -= 15;
      currentPage = offset / pageSize + 1;
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      httpServletRequest.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
      httpServletRequest.setAttribute("pager.realCurrent", (
          new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("mylist", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,applyUserName,status");
  }
  
  private void getConfigFile(HttpServletRequest httpServletRequest) {}
  
  private void setConfigFile(HttpServletRequest httpServletRequest) {}
}
