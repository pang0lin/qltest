package com.js.oa.jsflow.action;

import com.js.oa.archives.service.ArchivesBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.routine.boardroom.bean.BoardRoomEJBBean;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.DataSourceBase;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class FileTransAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    FileDealWithActionForm fileDealWithActionForm = (FileDealWithActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    String workStatus = httpServletRequest.getParameter("workStatus");
    fileDealWithActionForm.setWorkStatus(workStatus);
    String tag = "tag1";
    HttpSession httpSession = httpServletRequest.getSession(true);
    if (httpServletRequest.getParameter("search") != null && !httpServletRequest.getParameter("search").equals("null")) {
      httpServletRequest.setAttribute("search", httpServletRequest.getParameter("search"));
    } else {
      httpServletRequest.setAttribute("search", "");
    } 
    if (httpServletRequest.getParameter("workTitle") != null && !httpServletRequest.getParameter("workTitle").equals("null")) {
      httpServletRequest.setAttribute("workTitle", httpServletRequest.getParameter("workTitle"));
    } else {
      httpServletRequest.setAttribute("workTitle", "");
    } 
    if (httpServletRequest.getParameter("workStepName") != null && !httpServletRequest.getParameter("workStepName").equals("null")) {
      httpServletRequest.setAttribute("workStepName", httpServletRequest.getParameter("workStepName"));
      fileDealWithActionForm.setWorkStepName(httpServletRequest.getParameter("workStepName"));
    } else {
      httpServletRequest.setAttribute("workStepName", "");
    } 
    if (httpServletRequest.getParameter("overdue") != null && !httpServletRequest.getParameter("overdue").equals("null")) {
      httpServletRequest.setAttribute("overdue", httpServletRequest.getParameter("overdue"));
    } else {
      httpServletRequest.setAttribute("overdue", "0");
    } 
    if (httpServletRequest.getParameter("orderBy") != null && !httpServletRequest.getParameter("orderBy").equals("null")) {
      httpServletRequest.setAttribute("orderBy", httpServletRequest.getParameter("orderBy"));
    } else {
      httpServletRequest.setAttribute("orderBy", "");
    } 
    if (httpServletRequest.getParameter("sortType") != null && !httpServletRequest.getParameter("sortType").equals("null")) {
      httpServletRequest.setAttribute("sortType", httpServletRequest.getParameter("sortType"));
    } else {
      httpServletRequest.setAttribute("sortType", "");
    } 
    String type = httpServletRequest.getParameter("type");
    if (type != null && type.equals("cancel")) {
      String workId = httpServletRequest.getParameter("workId");
      String tableId = httpServletRequest.getParameter("tableId");
      String recordId = httpServletRequest.getParameter("recordId");
      String stepCount = httpServletRequest.getParameter("stepCount");
      String[] para = { workId, tableId, recordId, stepCount, httpSession.getAttribute("userId").toString() };
      WorkFlowBD workFlowBD = new WorkFlowBD();
      workFlowBD.workCancel(para);
    } 
    if (type != null && type.equals("creatorCancel")) {
      String cancelReason = httpServletRequest.getParameter("cancelReason");
      String tableId = httpServletRequest.getParameter("tableId");
      String recordId = httpServletRequest.getParameter("recordId");
      String processName = httpServletRequest.getParameter("processName");
      String workId = httpServletRequest.getParameter("workId");
      String workflowType = httpServletRequest.getParameter("workflowType");
      String processId = httpServletRequest.getParameter("processId");
      String remindValue = "";
      if (httpServletRequest.getParameter("remindValue") != null)
        remindValue = httpServletRequest.getParameter("remindValue"); 
      WorkFlowBD workFlowBD = new WorkFlowBD();
      ArrayList<String> sqlList = new ArrayList();
      sqlList.add("delete from JSDB.JSF_WORK where worktable_id = " + tableId + " and workrecord_id = " + 
          recordId + " and workProcess_id = " + processId + " and wf_work_id <> " + workId);
      if (httpServletRequest.getParameter("channelType") != null) {
        sqlList.add("update JSDB.JSF_WORK set workTitle = '您已取消了您的" + remindValue + processName + 
            "', workStatus = -2, workCancelReason = '" + cancelReason + 
            "', workMainLinkFile = '/jsoa/InfoProcAction.do?channelType=" + httpServletRequest.getParameter("channelType") + "&informationType=" + httpServletRequest.getParameter("informationType") + "&redhead=" + httpServletRequest.getParameter("redhead") + "' where wf_work_id = " + workId);
      } else {
        sqlList.add("update JSDB.JSF_WORK set workTitle = '您已取消了您的" + remindValue + processName + 
            "', workStatus = -2, workCancelReason = '" + cancelReason + 
            "', workMainLinkFile = '/jsoa/WorkFlowReSubmitAction.do?pp=1&moduleId=" + httpServletRequest.getParameter("moduleId") + "&remindField=" + httpServletRequest.getParameter("remindField") + "' where wf_work_id = " + workId);
      } 
      workFlowBD.updateTable(sqlList);
      if (workflowType.equals("0")) {
        BoardRoomEJBBean bean = new BoardRoomEJBBean();
        bean.updateBoardroomCancel(workId);
      } 
      try {
        if ("12".equals(httpServletRequest.getParameter("moduleId")))
          (new ArchivesBD()).restoreBorrowCountAfterCancel(recordId); 
      } catch (IllegalAccessException illegalAccessException) {
      
      } catch (InvocationTargetException invocationTargetException) {}
      tag = "close";
    } 
    if (type != null && type.equals("singleDel")) {
      String workId = httpServletRequest.getParameter("workId");
      ArrayList<String> sqlList = new ArrayList();
      sqlList.add("update JSDB.JSF_WORK set workDelete = 1 where wf_work_id = " + workId);
      (new WorkFlowBD()).updateTable(sqlList);
    } 
    if (type != null && type.equals("batchDel")) {
      String[] workId = httpServletRequest.getParameterValues("batchId");
      ArrayList<String> sqlList = new ArrayList();
      for (int i = 0; i < workId.length; i++)
        sqlList.add("update JSDB.JSF_WORK set workDelete = 1 where wf_work_id = " + workId[i]); 
      (new WorkFlowBD()).updateTable(sqlList);
    } 
    if (type != null && type.equals("forceCancel")) {
      String workId = httpServletRequest.getParameter("workId");
      String tableId = httpServletRequest.getParameter("tableId");
      String recordId = httpServletRequest.getParameter("recordId");
      String stepCount = httpServletRequest.getParameter("stepCount");
      String[] para = { workId, tableId, recordId, stepCount, httpSession.getAttribute("userId").toString() };
      WorkFlowBD workFlowBD = new WorkFlowBD();
      if (!httpServletRequest.getParameter("fileType").equals("信息发布"))
        workFlowBD.forceCancel(para); 
    } 
    if (type != null && type.equals("forceDel")) {
      String tableId = httpServletRequest.getParameter("tableId");
      String recordId = httpServletRequest.getParameter("recordId");
      String[] para = { tableId, recordId };
      WorkFlowBD workFlowBD = new WorkFlowBD();
      workFlowBD.forceDel(para);
    } 
    String search = httpServletRequest.getParameter("search");
    if (workStatus.equals("0")) {
      fileDealWithActionForm.setFileType("待批文件");
    } else if (workStatus.equals("1")) {
      fileDealWithActionForm.setFileType("文件监控");
    } else if (workStatus.equals("101")) {
      fileDealWithActionForm.setFileType("已批文件");
    } else if (workStatus.equals("102")) {
      fileDealWithActionForm.setFileType("已阅文件");
    } else if (workStatus.equals("2")) {
      fileDealWithActionForm.setFileType("待阅文件");
    } 
    String wfCurEmployeeId = (httpServletRequest.getParameter("employeeId") == null) ? httpSession.getAttribute("userId").toString() : httpServletRequest.getParameter("employeeId");
    String orderBy = httpServletRequest.getParameter("orderBy");
    String sortType = httpServletRequest.getParameter("sortType");
    String tempSort = "";
    if (orderBy != null && !orderBy.equals("null") && orderBy != "")
      tempSort = " aaa." + orderBy + " " + sortType + " , "; 
    if (search == null || search.equals("null") || search.equals("")) {
      view(workStatus, httpServletRequest);
    } else {
      String workTitle = httpServletRequest.getParameter("workTitle");
      String viewSql = "aaa.workFileType,aaa.workCurStep,aaa.workTitle,aaa.workDeadLine,aaa.workSubmitPerson,aaa.workSubmitTime,aaa.workType,aaa.workActivity,aaa.workTableId,aaa.workRecordId, aaa.wfWorkId,aaa.workSubmitPerson,aaa.wfSubmitEmployeeId,aaa.workAllowCancel,aaa.workProcessId,aaa.workStepCount,aaa.workMainLinkFile,aaa.workSubmitTime, aaa.workCurStep,aaa.creatorCancelLink,aaa.isStandForWork,aaa.standForUserId,aaa.standForUserName,aaa.workCreateDate,aaa.submitOrg,aaa.workDoneWithDate,aaa.emergence,aaa.initActivity,aaa.initActivityName,aaa.tranType, aaa.tranFromPersonId,  aaa.processDeadlineDate ,aaa.wfCurEmployeeId ,aaa.workDeadlineDate";
      String fromSql = " com.js.oa.jsflow.po.WFWorkPO aaa ";
      String whereSql = "";
      String dateWhere = "";
      if ("1".equals(httpServletRequest.getParameter("searchDate"))) {
        String dataType = SystemCommon.getDatabaseType();
        if (dataType.indexOf("mysql") >= 0) {
          dateWhere = " and aaa.workSubmitTime between '" + httpServletRequest.getParameter("searchBeginDate") + " 00:00:00' and '" + 
            httpServletRequest.getParameter("searchEndDate") + " 23:59:59' ";
        } else {
          dateWhere = " and aaa.workSubmitTime between JSDB.FN_STRTODATE('" + httpServletRequest.getParameter("searchBeginDate") + " 00:00:00','L') and JSDB.FN_STRTODATE('" + 
            httpServletRequest.getParameter("searchEndDate") + " 23:59:59','L') ";
        } 
      } 
      String pressDeal = httpServletRequest.getParameter("pressDeal");
      if (httpServletRequest.getParameter("pressDeal") != null && !"".equals(httpServletRequest.getParameter("pressDeal")))
        if ("0".equals(httpServletRequest.getParameter("pressDeal"))) {
          String dataType = SystemCommon.getDatabaseType();
          if (dataType.indexOf("mysql") >= 0) {
            dateWhere = String.valueOf(dateWhere) + " and (aaa.workDeadLine='-1' or (aaa.workDeadLine<>'-1' and '" + (new Date()).toLocaleString() + "' <= aaa.workDeadlineDate)) ";
          } else {
            dateWhere = String.valueOf(dateWhere) + " and (aaa.workDeadLine='-1' or (aaa.workDeadLine<>'-1' and JSDB.FN_STRTODATE('" + (new Date()).toLocaleString() + "','L') <= aaa.workDeadlineDate)) ";
          } 
        } else if ("1".equals(httpServletRequest.getParameter("pressDeal"))) {
          String dataType = SystemCommon.getDatabaseType();
          if (dataType.indexOf("mysql") >= 0) {
            dateWhere = String.valueOf(dateWhere) + " and (aaa.workDeadLine<>'-1' and '" + (new Date()).toLocaleString() + "' > aaa.workDeadlineDate) ";
          } else {
            dateWhere = String.valueOf(dateWhere) + "and JSDB.FN_STRTODATE('" + (new Date()).toLocaleString() + "','L') >(CASE WHEN (aaa.workDeadLine <> '-1' AND aaa.workSubmitTime <>aaa.workDeadlineDate and not ((aaa.workDeadlineDate is null ))) THEN aaa.workDeadlineDate else aaa.processDeadlineDate END )";
          } 
        }  
      if (httpServletRequest.getParameter("workStepName") != null && !"".equals(httpServletRequest.getParameter("workStepName")))
        dateWhere = String.valueOf(dateWhere) + " and (aaa.workCurStep like '%" + httpServletRequest.getParameter("workStepName") + "%') "; 
      String perSearch = " and 1=1 ";
      if (httpServletRequest.getParameter("dealPerson") != null && !"".equals(httpServletRequest.getParameter("dealPerson"))) {
        String dealPerson = httpServletRequest.getParameter("dealPerson").toString();
        httpServletRequest.setAttribute("dealPerson", dealPerson);
        String ids = getEmpId(dealPerson);
        perSearch = String.valueOf(perSearch) + " and aaa.wfCurEmployeeId in(" + ids + ") ";
      } 
      whereSql = " where aaa.workStatus = " + workStatus + " and aaa.wfCurEmployeeId = " + 
        wfCurEmployeeId + " and aaa.workTitle like '%" + workTitle + 
        "%' " + perSearch + "and aaa.workListControl = 1 and aaa.workDelete = 0 " + dateWhere + " order by " + tempSort + " aaa.wfWorkId desc";
      if (workStatus.equals("0")) {
        whereSql = " where aaa.workStatus = 0 and aaa.wfCurEmployeeId = " + 
          wfCurEmployeeId + " and aaa.workTitle like '%" + workTitle + 
          "%' " + perSearch + "and aaa.workListControl = 1 and aaa.workDelete = 0 " + dateWhere + " order by " + tempSort + " aaa.emergence desc,aaa.wfWorkId desc";
        if ("4".equals(httpServletRequest.getParameter("emergence")))
          whereSql = 
            " where aaa.workStatus = 0 and aaa.emergence='4' and aaa.wfCurEmployeeId = " + 
            wfCurEmployeeId + " and aaa.workTitle like '%" + workTitle + 
            "%' " + perSearch + "and aaa.workListControl = 1 and aaa.workDelete = 0 " + dateWhere + " order by " + tempSort + " aaa.emergence desc,aaa.wfWorkId desc"; 
        if ("3".equals(httpServletRequest.getParameter("emergence")))
          whereSql = 
            " where aaa.workStatus = 0 and aaa.emergence='3' and aaa.wfCurEmployeeId = " + 
            wfCurEmployeeId + " and aaa.workTitle like '%" + workTitle + 
            "%' " + perSearch + "and aaa.workListControl = 1 and aaa.workDelete = 0 " + dateWhere + " order by " + tempSort + " aaa.emergence desc,aaa.wfWorkId desc"; 
        if ("2".equals(httpServletRequest.getParameter("emergence")))
          whereSql = 
            " where aaa.workStatus = 0 and aaa.emergence='2' and aaa.wfCurEmployeeId = " + 
            wfCurEmployeeId + " and aaa.workTitle like '%" + workTitle + 
            "%' " + perSearch + "and aaa.workListControl = 1 and aaa.workDelete = 0 " + dateWhere + " order by " + tempSort + " aaa.emergence desc,aaa.wfWorkId desc"; 
        if ("1".equals(httpServletRequest.getParameter("emergence")))
          whereSql = 
            " where aaa.workStatus = 0 and aaa.emergence='1' and aaa.wfCurEmployeeId = " + 
            wfCurEmployeeId + " and aaa.workTitle like '%" + workTitle + 
            "%' " + perSearch + "and aaa.workListControl = 1 and aaa.workDelete = 0 " + dateWhere + " order by " + tempSort + " aaa.emergence desc,aaa.wfWorkId desc"; 
        if ("0".equals(httpServletRequest.getParameter("emergence")))
          whereSql = 
            " where aaa.workStatus = 0 and aaa.emergence='0' and aaa.wfCurEmployeeId = " + 
            wfCurEmployeeId + " and aaa.workTitle like '%" + workTitle + 
            "%' " + perSearch + "and aaa.workListControl = 1 and aaa.workDelete = 0 " + dateWhere + " order by " + tempSort + " aaa.emergence desc,aaa.wfWorkId desc"; 
      } 
      if (workStatus.equals("1011"))
        whereSql = " where aaa.workStatus = 101 and aaa.workDoneWithDate is null and aaa.wfCurEmployeeId = " + 
          wfCurEmployeeId + " and aaa.workTitle like '%" + workTitle + 
          "%' " + perSearch + "and aaa.workListControl = 1 and aaa.workDelete = 0 " + dateWhere + " order by " + tempSort + " aaa.wfWorkId desc"; 
      if (workStatus.equals("1012"))
        whereSql = " where aaa.workStatus = 101 and aaa.workDoneWithDate is not null  and aaa.wfCurEmployeeId = " + 
          wfCurEmployeeId + " and aaa.workTitle like '%" + workTitle + 
          "%' " + perSearch + "and aaa.workListControl = 1 and aaa.workDelete = 0 " + dateWhere + " order by " + tempSort + " aaa.wfWorkId desc"; 
      list(httpServletRequest, viewSql, fromSql, whereSql);
    } 
    httpServletRequest.setAttribute("status", workStatus);
    return actionMapping.findForward("transend");
  }
  
  public void view(String workStatus, HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String orderBy = httpServletRequest.getParameter("orderBy");
    String sortType = httpServletRequest.getParameter("sortType");
    String tempSort = "";
    if (orderBy != null && !orderBy.equals("null") && orderBy != "")
      tempSort = " aaa." + orderBy + " " + sortType + " , "; 
    String wfCurEmployeeId = (httpServletRequest.getParameter("employeeId") == null) ? httpSession.getAttribute("userId").toString() : httpServletRequest.getParameter("employeeId");
    String viewSql = "aaa.workFileType, aaa.workCurStep, aaa.workTitle, aaa.workDeadLine, aaa.workSubmitPerson, aaa.workSubmitTime, aaa.workType, aaa.workActivity, aaa.workTableId, aaa.workRecordId, aaa.wfWorkId, aaa.workSubmitPerson, aaa.wfSubmitEmployeeId, aaa.workAllowCancel, aaa.workProcessId, aaa.workStepCount,aaa.workMainLinkFile, aaa.workSubmitTime, aaa.workCurStep, aaa.creatorCancelLink, aaa.isStandForWork, aaa.standForUserId, aaa.standForUserName,aaa.workCreateDate,aaa.submitOrg,aaa.workDoneWithDate,aaa.emergence,aaa.initActivity,aaa.initActivityName,aaa.tranType, aaa.tranFromPersonId,  aaa.processDeadlineDate,aaa.wfCurEmployeeId  ,aaa.workDeadlineDate";
    String fromSql = " com.js.oa.jsflow.po.WFWorkPO aaa ";
    String whereSql = " where aaa.workStatus = " + workStatus + " and aaa.wfCurEmployeeId = " + wfCurEmployeeId + 
      " and aaa.workListControl = 1 and aaa.workDelete = 0 ";
    whereSql = String.valueOf(whereSql) + " order by " + tempSort + " aaa.wfWorkId desc ";
    if (workStatus.equals("0")) {
      String workFileType = "";
      if (httpServletRequest.getParameter("tran") != null && "1".equals(httpServletRequest.getParameter("tran")))
        workFileType = " and workFileType<>'数据采集' "; 
      whereSql = " where aaa.workStatus = 0 and aaa.wfCurEmployeeId = " + wfCurEmployeeId + workFileType + 
        " and aaa.workListControl = 1 and aaa.workDelete = 0  ";
      if ("4".equals(httpServletRequest.getParameter("emergence")))
        whereSql = " where aaa.workStatus = 0 and aaa.emergence='4' and aaa.wfCurEmployeeId = " + wfCurEmployeeId + workFileType + 
          " and aaa.workListControl = 1 and aaa.workDelete = 0  "; 
      if ("3".equals(httpServletRequest.getParameter("emergence")))
        whereSql = " where aaa.workStatus = 0 and aaa.emergence='3' and aaa.wfCurEmployeeId = " + wfCurEmployeeId + workFileType + 
          " and aaa.workListControl = 1 and aaa.workDelete = 0  "; 
      if ("2".equals(httpServletRequest.getParameter("emergence")))
        whereSql = " where aaa.workStatus = 0 and aaa.emergence='2' and aaa.wfCurEmployeeId = " + wfCurEmployeeId + workFileType + 
          " and aaa.workListControl = 1 and aaa.workDelete = 0  "; 
      if ("1".equals(httpServletRequest.getParameter("emergence")))
        whereSql = " where aaa.workStatus = 0 and aaa.emergence='1' and aaa.wfCurEmployeeId = " + wfCurEmployeeId + workFileType + 
          " and aaa.workListControl = 1 and aaa.workDelete = 0  "; 
      if ("0".equals(httpServletRequest.getParameter("emergence")))
        whereSql = " where aaa.workStatus = 0 and aaa.emergence='0' and aaa.wfCurEmployeeId = " + wfCurEmployeeId + workFileType + 
          " and aaa.workListControl = 1 and aaa.workDelete = 0  "; 
      if ("1".equals(httpServletRequest.getParameter("overdue"))) {
        String dataType = SystemCommon.getDatabaseType();
        if (dataType.indexOf("mysql") >= 0) {
          whereSql = String.valueOf(whereSql) + " and '" + (new Date()).toLocaleString() + "' > (CASE WHEN (aaa.workDeadLine <> -1 AND aaa.workSubmitTime <>aaa.workDeadlineDate and !((aaa.workDeadlineDate is null )or(aaa.workDeadlineDate='' ))) THEN aaa.workDeadlineDate else aaa.processDeadlineDate END ) ";
        } else {
          whereSql = String.valueOf(whereSql) + " and JSDB.FN_STRTODATE('" + (new Date()).toLocaleString() + "','L') > (CASE WHEN (aaa.workDeadLine <> -1 AND aaa.workSubmitTime <>aaa.workDeadlineDate and not ((aaa.workDeadlineDate is null ))) THEN aaa.workDeadlineDate else aaa.processDeadlineDate END ) ";
        } 
      } 
      whereSql = String.valueOf(whereSql) + " order by " + tempSort + " aaa.emergence desc, aaa.wfWorkId desc ";
    } 
    if (workStatus.equals("1011"))
      whereSql = " where aaa.workStatus = 101 and aaa.workDoneWithDate is null and aaa.wfCurEmployeeId = " + wfCurEmployeeId + 
        " and aaa.workListControl = 1 and aaa.workDelete = 0 order by " + tempSort + " aaa.wfWorkId desc"; 
    if (workStatus.equals("1012"))
      whereSql = " where aaa.workStatus = 101 and aaa.workDoneWithDate is not null and aaa.wfCurEmployeeId = " + wfCurEmployeeId + 
        " and aaa.workListControl = 1 and aaa.workDelete = 0 order by " + tempSort + " aaa.wfWorkId desc"; 
    list(httpServletRequest, viewSql, fromSql, whereSql);
  }
  
  public void list(HttpServletRequest httpServletRequest, String viewSql, String fromSql, String whereSql) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String orderBy = httpServletRequest.getParameter("orderBy");
    String sortType = httpServletRequest.getParameter("sortType");
    String tempSort = "";
    if (orderBy != null && !orderBy.equals("null") && orderBy != "")
      tempSort = " aaa." + orderBy + " " + sortType + " , "; 
    if ("1".equals(httpServletRequest.getParameter("tran"))) {
      ManagerService mbd = new ManagerService();
      String curUserId = httpServletRequest.getSession(true).getAttribute("userId").toString();
      String curOrgId = httpServletRequest.getSession(true).getAttribute("orgId").toString();
      String tranWhereSql = " and aaa.workProcessId<>11 and aaa.wfCurEmployeeId=ddd.empId and ( " + mbd.getRightFinalWhere(curUserId, curOrgId, "02*03*02", "ddd.orgId", "aaa.wfCurEmployeeId") + " ) ";
      fromSql = " com.js.oa.jsflow.po.WFWorkPO aaa,  com.js.system.vo.usermanager.EmployeeOrgVO ddd";
      String[] tmp = whereSql.split("order by");
      whereSql = String.valueOf(tmp[0].replaceAll("and aaa.wfCurEmployeeId = " + httpSession.getAttribute("userId"), "")) + tranWhereSql + "order by" + tmp[1];
    } 
    String sqlStr = "SELECT PACKAGENAME,aaa.WORKFILETYPE,count(WF_WORK_ID),aaa.WORKPROCESS_ID FROM JSDB.JSF_WORK aaa   left join JSDB.JSF_WORKFLOWPROCESS bbb on aaa.WORKPROCESS_ID = bbb.WF_WORKFLOWPROCESS_ID  left join JSDB.JSF_PACKAGE ccc  on bbb.WF_PACKAGE_ID=ccc.WF_PACKAGE_ID " + (

      
      "1".equals(httpServletRequest.getParameter("tran")) ? " ,JSDB.org_organization_user ddd " : "") + 
      whereSql.split(" order by")[0] + 
      " group by PACKAGENAME,aaa.WORKFILETYPE,aaa.WORKPROCESS_ID order by " + tempSort + " PACKAGENAME,aaa.WORKFILETYPE,aaa.WORKPROCESS_ID asc";
    String whereSQL = whereSql;
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSql, fromSql, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("workList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "workStatus,workTitle,search,processName,processID,searchDate,searchBeginDate,searchEndDate,tran,pressDeal,employeeId,orderBy,sortType,overdue,dealPerson,submitOrg");
  }
  
  private String getEmpId(String name) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String ids = "";
    String sql = "select emp_id from org_employee where empname like '%" + name + "%'";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next())
        ids = String.valueOf(ids) + rs.getString(1) + ","; 
      ids = String.valueOf(ids) + "-1";
      rs.close();
      pstmt.close();
      conn.close();
    } catch (SQLException e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return ids;
  }
}
