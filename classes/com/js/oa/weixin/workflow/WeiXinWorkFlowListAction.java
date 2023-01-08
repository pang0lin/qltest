package com.js.oa.weixin.workflow;

import com.js.oa.jsflow.action.FlowListActionForm;
import com.js.oa.jsflow.bean.WFProcessEJBBean;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.jsflow.util.CreateFlowReportData;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.BASE64;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WeiXinWorkFlowListAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    FlowListActionForm myFlowActionForm = (FlowListActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String action = httpServletRequest.getParameter("action");
    String workflowContent = httpServletRequest.getParameter("workflowContent");
    if (action == null)
      action = "newFlow"; 
    String tag = "";
    String moduleId = httpServletRequest.getParameter("moduleId");
    httpServletRequest.setAttribute("moduleId", moduleId);
    httpServletRequest.setAttribute("appBh", "gzlc");
    if (action.equals("newFlow")) {
      ProcessBD processBD = new ProcessBD();
      List<Object[]> processList = processBD.getUserProcessList(userId, orgIdString, moduleId, workflowContent);
      ArrayList<Object[]> packageList = new ArrayList();
      if (processList != null) {
        String upPackageId = "";
        for (int i = 0; i < processList.size(); i++) {
          Object[] processObj = processList.get(i);
          if (!upPackageId.equals(processObj[0].toString())) {
            upPackageId = processObj[0].toString();
            Object[] packageObj = { processObj[0], processObj[1] };
            packageList.add(packageObj);
          } 
        } 
      } 
      httpServletRequest.setAttribute("packageList", packageList);
      httpServletRequest.setAttribute("processList", processList);
      tag = "newFlow";
    } else if ("delete".equals(action)) {
      String ids = httpServletRequest.getParameter("ids");
      WorkFlowCommonBD wfc = new WorkFlowCommonBD();
      if (ids != null)
        wfc.deleteDraftRecord(ids); 
      action = "draft";
    } else if ("oftenList".equals(action)) {
      ProcessBD processBD = new ProcessBD();
      if (httpServletRequest.getParameter("processId") != null) {
        String processId = httpServletRequest.getParameter("processId");
        String type = httpServletRequest.getParameter("type");
        String[] procIds = processId.split(",");
        for (int i = 0; i < procIds.length; i++)
          processBD.setProcessOnDeskTop(userId, procIds[i], type); 
      } 
      String whereSql = processBD.getProcWhereSql(userId, orgIdString, "new");
      String para = " bbb.wfPackageId, bbb.packageName, aaa.wfWorkFlowProcessId, aaa.workFlowProcessName, aaa.accessDatabaseId, aaa.processType,  aaa.remindField,aaa.formType,aaa.startJSP";
      String from = "com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa join aaa.wfPackage bbb";
      String where = "where (" + whereSql + ")  and bbb.moduleId = " + moduleId + " and aaa.isPublish=1 order by bbb.orderCode,bbb.wfPackageId desc,aaa.wfWorkFlowProcessId ";
      list(httpServletRequest, para, from, where);
      httpServletRequest.setAttribute("pageParameters", "action,moduleId");
      httpServletRequest.setAttribute("desktopID", processBD.getDeskTopFlowId(userId));
      tag = "oftenList";
    } 
    if ("draft".equals(action)) {
      list(httpServletRequest, 
          "po.id,po.processId,po.tableId,po.recordId,po.workTitle,po.saveTime,po.processName", 
          "com.js.oa.jsflow.po.JSFPDraftPO po", 
          "where po.userId=" + userId + " order by po.saveTime desc");
      httpServletRequest.setAttribute("pageParameters", "action");
      tag = "draftList";
    } 
    if ("reportPage".equals(action)) {
      Object object = httpServletRequest.getSession().getAttribute("domainId");
      ProcessBD procBD = new ProcessBD();
      List processList = procBD.getAllProcess((String)object);
      httpServletRequest.setAttribute("processList", processList);
      return actionMapping.findForward(action);
    } 
    if ("flowReport".equals(action)) {
      String beginDate = httpServletRequest.getParameter("beginDate").replace("/", "-");
      String endDate = httpServletRequest.getParameter("endDate").replace("/", "-");
      String flowId = (httpServletRequest.getParameter("flowId") == null) ? "" : httpServletRequest.getParameter("flowId");
      String orgIds = (httpServletRequest.getParameter("orgIds") == null) ? "" : httpServletRequest.getParameter("orgIds");
      String flowSubmitId = (httpServletRequest.getParameter("flowSubmitId") == null) ? "" : httpServletRequest.getParameter("flowSubmitId");
      String dealwithOrgIds = (httpServletRequest.getParameter("dealwithOrgIds") == null) ? "" : httpServletRequest.getParameter("dealwithOrgIds");
      String dealwithId = (httpServletRequest.getParameter("dealwithId") == null) ? "" : httpServletRequest.getParameter("dealwithId");
      String flowStatus = (httpServletRequest.getParameter("flowStatus") == null) ? "" : httpServletRequest.getParameter("flowStatus");
      String[] exportCond = { beginDate, endDate, flowId, orgIds, flowSubmitId, dealwithOrgIds, dealwithId, flowStatus };
      String filePath = httpServletRequest.getSession().getServletContext().getRealPath("/upload/" + ((new Date()).getYear() + 1900) + "/flowFile/");
      boolean flag = (new CreateFlowReportData()).flowReport(String.valueOf(filePath) + "/流程用时统计报表.xls", exportCond);
      if (flag) {
        String fileUrl = String.valueOf(filePath) + "\\流程用时统计报表.xls&流程用时统计报表.xls";
        httpServletRequest.setAttribute("queryString", BASE64.BASE64EncoderNoBR(fileUrl));
        return actionMapping.findForward(action);
      } 
      return actionMapping.findForward("exportClose");
    } 
    if (!"".equals(workflowContent) && workflowContent != null) {
      String databaseType = SystemCommon.getDatabaseType();
      WFProcessEJBBean bean = new WFProcessEJBBean();
      String whereSql = "";
      try {
        whereSql = bean.getProcWhereSql(userId, orgIdString, "new");
      } catch (Exception e) {
        e.printStackTrace();
      } 
      String para = " bbb.wfPackageId, bbb.packageName, aaa.wfWorkFlowProcessId,";
      para = String.valueOf(para) + " aaa.workFlowProcessName, aaa.accessDatabaseId, aaa.processType,";
      para = String.valueOf(para) + " aaa.remindField,aaa.formType,aaa.startJSP";
      String from = " from com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa join aaa.wfPackage bbb";
      String where = " where (" + whereSql + ") and bbb.moduleId = " + moduleId + " and aaa.isPublish=1";
      where = String.valueOf(where) + " and aaa.processStatus=1";
      where = String.valueOf(where) + " and (aaa.workFlowProcessName like '%" + workflowContent + "%' or aaa.processDescription like '%" + workflowContent + "%') ";
      if (databaseType.indexOf("mysql") >= 0) {
        where = String.valueOf(where) + " order by bbb.orderCode,bbb.wfPackageId desc,convert(aaa.workFlowProcessName using gbk ),aaa.wfWorkFlowProcessId ";
      } else {
        where = String.valueOf(where) + " order by bbb.orderCode,bbb.wfPackageId desc,aaa.workFlowProcessName,aaa.wfWorkFlowProcessId ";
      } 
      list(httpServletRequest, para, from, where);
      tag = "workflowContent";
    } 
    return actionMapping.findForward(tag);
  }
  
  public void list(HttpServletRequest request, String viewSql, String fromSql, String whereSql) {
    String status = request.getParameter("status");
    String keyword = (request.getParameter("keyword") != null) ? request.getParameter("keyword") : "";
    if (keyword != null && !"".equals(keyword))
      try {
        keyword = URLDecoder.decode(keyword, "utf-8");
        whereSql = whereSql.toString().replace("order by po.saveTime desc", " and po.workTitle like '%" + keyword + "%' order by po.saveTime desc");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }  
    int pageSize = SystemCommon.getPageNum();
    int offset = 0;
    if (request.getParameter("beginIndex") != null)
      offset = Integer.parseInt(request.getParameter("beginIndex")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSql, fromSql, whereSql);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    request.setAttribute("workList", list);
    request.setAttribute("RECORD_COUNT", recordCount);
    request.setAttribute("keyword", keyword);
    request.setAttribute("action", "list");
    request.setAttribute("status", status);
  }
}
