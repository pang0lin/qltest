package com.js.oa.weixin.workflow;

import com.js.oa.userdb.util.DbOpt;
import com.js.util.page.Page;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WeiXinFileDoneWithAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    String action = httpServletRequest.getParameter("action");
    if ("weixinselectRelationObject".equals(action)) {
      if (!"1".equals(httpServletRequest.getParameter("fileType"))) {
        viewMyRelation(httpServletRequest);
      } else {
        viewDealWithRelation(httpServletRequest);
      } 
      return actionMapping.findForward("selectRelationweixin");
    } 
    return actionMapping.findForward("success");
  }
  
  public void view(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String wfCurEmployeeId = (httpServletRequest.getParameter("employeeId") == null) ? httpSession.getAttribute("userId").toString() : httpServletRequest.getParameter("employeeId");
    String viewSql = " aaa.workFileType, aaa.workCurStep, aaa.workTitle, aaa.workDeadLine, aaa.workSubmitPerson, aaa.workSubmitTime, aaa.workType, aaa.workActivity, aaa.workTableId, aaa.workRecordId, aaa.wfWorkId, aaa.workSubmitPerson,  aaa.wfSubmitEmployeeId, aaa.workDoneWithDate,aaa.workMainLinkFile, aaa.workSubmitTime, aaa.workCurStep, aaa.submitOrg, aaa.workProcessId";
    String fromSql = " com.js.oa.jsflow.po.WFWorkPO aaa ";
    String whereSql = " where aaa.workStartFlag = 1 and aaa.wfCurEmployeeId = " + 
      wfCurEmployeeId + " and " + 
      " aaa.workDoneWithDate is not null and workDelete = 0 order by  aaa.wfWorkId desc";
    list(httpServletRequest, viewSql, fromSql, whereSql);
  }
  
  private void viewMyRelation(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String orderBy = httpServletRequest.getParameter("orderBy");
    String sortType = httpServletRequest.getParameter("sortType");
    String tempSort = "";
    if (orderBy != null && !orderBy.equals("null") && orderBy != "")
      tempSort = " aaa." + orderBy + " " + sortType + " , "; 
    String wfCurEmployeeId = (httpServletRequest.getParameter("employeeId") == null) ? httpSession.getAttribute("userId").toString() : httpServletRequest.getParameter("employeeId");
    String viewSql = " aaa.workFileType, aaa.workCurStep, aaa.workTitle, aaa.workDeadLine, aaa.workSubmitPerson, aaa.workSubmitTime, aaa.workType, aaa.workActivity, aaa.workTableId, aaa.workRecordId, aaa.wfWorkId, aaa.workSubmitPerson,  aaa.wfSubmitEmployeeId, aaa.workDoneWithDate,aaa.workMainLinkFile, aaa.workSubmitTime, aaa.workCurStep, aaa.submitOrg, aaa.workProcessId,aaa.workStatus,aaa.initActivity ";
    String fromSql = " com.js.oa.jsflow.po.WFWorkPO aaa,com.js.oa.jsflow.po.WFWorkFlowProcessPO proc join proc.wfPackage wfpackage ";
    String whereSql = " where aaa.workProcessId=proc.wfWorkFlowProcessId and wfpackage.moduleId=1 and aaa.workStartFlag = 1 and aaa.wfCurEmployeeId = " + 
      wfCurEmployeeId + " and " + 
      "(aaa.workStatus =1 or aaa.workStatus=100) and " + 
      
      "  workDelete = 0";
    if (httpServletRequest.getParameter("flag") != null) {
      if ("1".equals(httpServletRequest.getParameter("searchStatus"))) {
        whereSql = String.valueOf(whereSql) + " and  aaa.workDoneWithDate is null ";
      } else if ("100".equals(httpServletRequest.getParameter("searchStatus"))) {
        whereSql = String.valueOf(whereSql) + " and  aaa.workDoneWithDate is not null ";
      } 
      if (httpServletRequest.getParameter("workTitle") != null)
        whereSql = String.valueOf(whereSql) + " and (aaa.workTitle like '%" + httpServletRequest.getParameter("workTitle") + "%' )"; 
    } 
    whereSql = String.valueOf(whereSql) + " order by " + tempSort + " aaa.wfWorkId desc";
    list(httpServletRequest, viewSql, fromSql, whereSql);
  }
  
  private void viewDealWithRelation(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String wfCurEmployeeId = (httpServletRequest.getParameter("employeeId") == null) ? httpSession.getAttribute("userId").toString() : httpServletRequest.getParameter("employeeId");
    String viewSql = " aaa.workFileType, aaa.workCurStep, aaa.workTitle, aaa.workDeadLine, aaa.workSubmitPerson, aaa.workSubmitTime, aaa.workType, aaa.workActivity, aaa.workTableId, aaa.workRecordId, aaa.wfWorkId, aaa.workSubmitPerson,  aaa.wfSubmitEmployeeId, aaa.workDoneWithDate,aaa.workMainLinkFile, aaa.workSubmitTime, aaa.workCurStep, aaa.submitOrg, aaa.workProcessId,aaa.workStatus,aaa.initActivity";
    String fromSql = " com.js.oa.jsflow.po.WFWorkPO aaa,com.js.oa.jsflow.po.WFWorkFlowProcessPO proc join proc.wfPackage wfpackage ";
    String whereSql = " where aaa.workProcessId=proc.wfWorkFlowProcessId and wfpackage.moduleId=1 and aaa.wfCurEmployeeId = " + 
      wfCurEmployeeId + " and aaa.workListControl = 1 and aaa.workDelete = 0 and " + 
      "aaa.workStatus =101 and workDelete = 0 ";
    if (httpServletRequest.getParameter("flag") != null) {
      if ("1".equals(httpServletRequest.getParameter("searchStatus"))) {
        whereSql = String.valueOf(whereSql) + " and  aaa.workDoneWithDate is null ";
      } else if ("100".equals(httpServletRequest.getParameter("searchStatus"))) {
        whereSql = String.valueOf(whereSql) + " and  aaa.workDoneWithDate is not null ";
      } 
      whereSql = String.valueOf(whereSql) + " and (aaa.workTitle like '%" + httpServletRequest.getParameter("workTitle") + "%'   )";
    } 
    whereSql = String.valueOf(whereSql) + " order by  aaa.wfWorkId desc";
    list(httpServletRequest, viewSql, fromSql, whereSql);
  }
  
  public void list(HttpServletRequest httpServletRequest, String viewSql, String fromSql, String whereSql) {
    String sqlStr = "SELECT PACKAGENAME,WORKFILETYPE,count(WORKFILETYPE),aaa.WORKPROCESS_ID FROM JSDB.JSF_WORK aaa   left join JSDB.JSF_WORKFLOWPROCESS bbb on aaa.WORKPROCESS_ID = bbb.WF_WORKFLOWPROCESS_ID  left join JSDB.JSF_PACKAGE ccc  on bbb.WF_PACKAGE_ID=ccc.WF_PACKAGE_ID " + 

      
      whereSql.split(" order by")[0] + 
      " group by PACKAGENAME,WORKFILETYPE,aaa.WORKPROCESS_ID order by  PACKAGENAME,WORKFILETYPE,aaa.WORKPROCESS_ID asc";
    DbOpt dbopt = null;
    if (!"selectRelationObject".equals(httpServletRequest.getParameter("action")))
      try {
        dbopt = new DbOpt();
        httpServletRequest.setAttribute("sortList", dbopt.executeQueryToStrArr2(sqlStr.replaceAll("wfCurEmployeeId", "wf_CurEmployee_Id"), 4));
        dbopt.close();
      } catch (Exception ex) {
        try {
          dbopt.close();
        } catch (SQLException sQLException) {}
        httpServletRequest.setAttribute("sortList", new String[0][3]);
      }  
    String whereSQL = whereSql;
    String processId = httpServletRequest.getParameter("processID");
    if (processId != null && processId.trim().length() > 0 && !processId.toUpperCase().equals("NULL"))
      whereSQL = whereSql.replaceFirst("where", "where aaa.workProcessId=" + processId + " and "); 
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null && !"null".equals(httpServletRequest.getParameter("pager.offset")))
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSql, fromSql, whereSQL);
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
      httpServletRequest.setAttribute("pager.realCurrent", (new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("workList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,fieldName,fieldId,flag,fileType,searchStatus,workTitle,processName,processID,searchDate,searchBeginDate,searchEndDate,employeeId,orderBy,sortType");
  }
}
