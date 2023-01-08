package com.js.oa.weixin.workflow;

import com.js.oa.archives.service.ArchivesBD;
import com.js.oa.form.zgrs.QjUtil;
import com.js.oa.jsflow.action.FileDealWithAction;
import com.js.oa.jsflow.action.FileDealWithActionForm;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.routine.boardroom.bean.BoardRoomEJBBean;
import com.js.oa.userdb.util.DbOpt;
import com.js.oa.weixin.common.util.WorkflowForWeiXinUtil;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FileDealWithForWeiXinAction extends Action {
  private static Logger log = Logger.getLogger(FileDealWithAction.class);
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    log.debug("HERE , FileDealWithAction...");
    FileDealWithActionForm fileDealWithActionForm = (FileDealWithActionForm)actionForm;
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
    if (httpServletRequest.getParameter("workSubmitPerson") != null && !httpServletRequest.getParameter("workSubmitPerson").equals("null")) {
      httpServletRequest.setAttribute("workSubmitPerson", httpServletRequest.getParameter("workSubmitPerson"));
    } else {
      httpServletRequest.setAttribute("workSubmitPerson", "");
    } 
    if (httpServletRequest.getParameter("submitOrg") != null && !httpServletRequest.getParameter("submitOrg").equals("null")) {
      httpServletRequest.setAttribute("submitOrg", httpServletRequest.getParameter("submitOrg"));
    } else {
      httpServletRequest.setAttribute("submitOrg", "");
    } 
    if (httpServletRequest.getParameter("workStepName") != null && !httpServletRequest.getParameter("workStepName").equals("null")) {
      httpServletRequest.setAttribute("workStepName", httpServletRequest.getParameter("workStepName"));
      fileDealWithActionForm.setWorkStepName(httpServletRequest.getParameter("workStepName"));
    } else {
      httpServletRequest.setAttribute("workStepName", "");
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
    log.debug("... ... if-else");
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
      String cancelReason = WorkflowForWeiXinUtil.decordStr(httpServletRequest.getParameter("cancelReason"));
      String tableId = httpServletRequest.getParameter("tableId");
      String recordId = httpServletRequest.getParameter("recordId");
      String processName = WorkflowForWeiXinUtil.decordStr(httpServletRequest.getParameter("processName"));
      String workId = httpServletRequest.getParameter("workId");
      String workflowType = httpServletRequest.getParameter("workflowType");
      String processId = httpServletRequest.getParameter("processId");
      String remindValue = "";
      if (httpServletRequest.getParameter("remindValue") != null)
        remindValue = WorkflowForWeiXinUtil.decordStr(httpServletRequest.getParameter("remindValue")); 
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
      if ("chinaLife".equals(SystemCommon.getCustomerName()))
        (new QjUtil()).isQj(tableId, recordId); 
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
      log.debug("--- --- 待批文件 --- ---");
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
    if (search == null || search.equals("null") || search.equals("")) {
      log.debug("Searh = NULL");
      view(workStatus, httpServletRequest);
    } else {
      String orderBy = httpServletRequest.getParameter("orderBy");
      String sortType = httpServletRequest.getParameter("sortType");
      String tempSort = "";
      if (orderBy != null && !orderBy.equals("null") && orderBy != "")
        tempSort = " aaa." + orderBy + " " + sortType + " , "; 
      String workTitle = httpServletRequest.getParameter("workTitle");
      String workSubmitPerson = httpServletRequest.getParameter("workSubmitPerson");
      String submitOrg = httpServletRequest.getParameter("submitOrg");
      String tempWh = "";
      if (workSubmitPerson != null && !"".equals(workSubmitPerson))
        tempWh = String.valueOf(tempWh) + " and aaa.workSubmitPerson like '%" + workSubmitPerson + "%' "; 
      if (submitOrg != null && !"".equals(submitOrg))
        tempWh = String.valueOf(tempWh) + " and aaa.submitOrg like '%" + submitOrg + "%' "; 
      String viewSql = "aaa.workFileType,aaa.workCurStep,aaa.workTitle,aaa.workDeadLine,aaa.workSubmitPerson,aaa.workSubmitTime,aaa.workType,aaa.workActivity,aaa.workTableId,aaa.workRecordId, aaa.wfWorkId,aaa.workSubmitPerson,aaa.wfSubmitEmployeeId,aaa.workAllowCancel,aaa.workProcessId,aaa.workStepCount,aaa.workMainLinkFile,aaa.workSubmitTime, aaa.workCurStep,aaa.creatorCancelLink,aaa.isStandForWork,aaa.standForUserId,aaa.standForUserName,aaa.workCreateDate,aaa.submitOrg,aaa.workDoneWithDate,aaa.emergence,aaa.initActivity,aaa.initActivityName,aaa.tranType, aaa.tranFromPersonId, aaa.processDeadlineDate ,aaa.wfCurEmployeeId,aaa.relProjectId,aaa.workHangup,aaa.workDeadlineDate,aaa.stickie ";
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
            dateWhere = String.valueOf(dateWhere) + " and (aaa.workDeadLine<>'-1' and JSDB.FN_STRTODATE('" + (new Date()).toLocaleString() + "','L') > aaa.workDeadlineDate) ";
          } 
        }  
      if (httpServletRequest.getParameter("workStepName") != null && !"".equals(httpServletRequest.getParameter("workStepName")))
        dateWhere = String.valueOf(dateWhere) + " and (aaa.workCurStep like '%" + httpServletRequest.getParameter("workStepName") + "%') "; 
      whereSql = " where aaa.workStatus = " + workStatus + " and aaa.wfCurEmployeeId = " + 
        wfCurEmployeeId + " and aaa.workTitle like '%" + workTitle + 
        "%' " + tempWh + " and aaa.workListControl = 1 and aaa.workDelete = 0 " + dateWhere + " order by " + tempSort + " aaa.wfWorkId desc";
      if ("9".equals(workStatus))
        whereSql = " where aaa.workStatus in (0,2,101,102 ) and aaa.wfCurEmployeeId = " + 
          wfCurEmployeeId + " and aaa.workTitle like '%" + workTitle + 
          "%' " + tempWh + " and aaa.workListControl = 1 and aaa.workDelete = 0 " + dateWhere + " order by " + tempSort + " aaa.workSubmitTime desc, aaa.wfWorkId desc"; 
      if (workStatus.equals("0")) {
        whereSql = " where aaa.workStatus = 0 and aaa.wfCurEmployeeId = " + 
          wfCurEmployeeId + " and aaa.workTitle like '%" + workTitle + 
          "%'  " + tempWh + " and aaa.workListControl = 1 and aaa.workDelete = 0 " + dateWhere + " order by " + tempSort + " aaa.emergence desc,aaa.wfWorkId desc";
        if ("4".equals(httpServletRequest.getParameter("emergence")))
          whereSql = 
            " where aaa.workStatus = 0 and aaa.emergence='4' and aaa.wfCurEmployeeId = " + 
            wfCurEmployeeId + " and aaa.workTitle like '%" + workTitle + 
            "%'  " + tempWh + " and aaa.workListControl = 1 and aaa.workDelete = 0 " + dateWhere + " order by " + tempSort + " aaa.emergence desc,aaa.wfWorkId desc"; 
        if ("3".equals(httpServletRequest.getParameter("emergence")))
          whereSql = 
            " where aaa.workStatus = 0 and aaa.emergence='3' and aaa.wfCurEmployeeId = " + 
            wfCurEmployeeId + " and aaa.workTitle like '%" + workTitle + 
            "%' " + tempWh + "  and aaa.workListControl = 1 and aaa.workDelete = 0 " + dateWhere + " order by " + tempSort + " aaa.emergence desc,aaa.wfWorkId desc"; 
        if ("2".equals(httpServletRequest.getParameter("emergence")))
          whereSql = 
            " where aaa.workStatus = 0 and aaa.emergence='2' and aaa.wfCurEmployeeId = " + 
            wfCurEmployeeId + " and aaa.workTitle like '%" + workTitle + 
            "%'  " + tempWh + " and aaa.workListControl = 1 and aaa.workDelete = 0 " + dateWhere + " order by " + tempSort + " aaa.emergence desc,aaa.wfWorkId desc"; 
        if ("1".equals(httpServletRequest.getParameter("emergence")))
          whereSql = 
            " where aaa.workStatus = 0 and aaa.emergence='1' and aaa.wfCurEmployeeId = " + 
            wfCurEmployeeId + " and aaa.workTitle like '%" + workTitle + 
            "%'  " + tempWh + " and aaa.workListControl = 1 and aaa.workDelete = 0 " + dateWhere + " order by " + tempSort + " aaa.emergence desc,aaa.wfWorkId desc"; 
        if ("0".equals(httpServletRequest.getParameter("emergence")))
          whereSql = 
            " where aaa.workStatus = 0 and aaa.emergence='0' and aaa.wfCurEmployeeId = " + 
            wfCurEmployeeId + " and aaa.workTitle like '%" + workTitle + 
            "%' " + tempWh + "  and aaa.workListControl = 1 and aaa.workDelete = 0 " + dateWhere + " order by " + tempSort + " aaa.emergence desc,aaa.wfWorkId desc"; 
      } 
      if (workStatus.equals("1011"))
        whereSql = " where aaa.workStatus = 101 and aaa.workDoneWithDate is null and aaa.wfCurEmployeeId = " + 
          wfCurEmployeeId + " and aaa.workTitle like '%" + workTitle + 
          "%' " + tempWh + "  and aaa.workListControl = 1 and aaa.workDelete = 0 " + dateWhere + " order by " + tempSort + " aaa.wfWorkId desc"; 
      if (workStatus.equals("1012"))
        whereSql = " where aaa.workStatus = 101 and aaa.workDoneWithDate is not null  and aaa.wfCurEmployeeId = " + 
          wfCurEmployeeId + " and aaa.workTitle like '%" + workTitle + 
          "%'  " + tempWh + " and aaa.workListControl = 1 and aaa.workDelete = 0 " + dateWhere + " order by " + tempSort + " aaa.wfWorkId desc"; 
      list(httpServletRequest, viewSql, fromSql, whereSql);
    } 
    httpServletRequest.setAttribute("status", workStatus);
    if ("1".equals(httpServletRequest.getParameter("transend")))
      tag = "transend"; 
    return actionMapping.findForward(tag);
  }
  
  public void view(String workStatus, HttpServletRequest httpServletRequest) {
    log.debug("VIEW workStatus");
    HttpSession httpSession = httpServletRequest.getSession(true);
    String orderBy = httpServletRequest.getParameter("orderBy");
    String sortType = httpServletRequest.getParameter("sortType");
    String tempSort = "";
    if (!"9".equals(workStatus))
      tempSort = " aaa.stickie desc,"; 
    if (orderBy != null && !orderBy.equals("null") && orderBy != "")
      tempSort = " aaa." + orderBy + " " + sortType + " , "; 
    String wfCurEmployeeId = (httpServletRequest.getParameter("employeeId") == null) ? httpSession.getAttribute("userId").toString() : httpServletRequest.getParameter("employeeId");
    String viewSql = "aaa.workFileType, aaa.workCurStep, aaa.workTitle, aaa.workDeadLine, aaa.workSubmitPerson, aaa.workSubmitTime, aaa.workType, aaa.workActivity, aaa.workTableId, aaa.workRecordId, aaa.wfWorkId, aaa.workSubmitPerson, aaa.wfSubmitEmployeeId, aaa.workAllowCancel, aaa.workProcessId, aaa.workStepCount,aaa.workMainLinkFile, aaa.workSubmitTime, aaa.workCurStep, aaa.creatorCancelLink, aaa.isStandForWork, aaa.standForUserId, aaa.standForUserName,aaa.workCreateDate,aaa.submitOrg,aaa.workDoneWithDate,aaa.emergence,aaa.initActivity,aaa.initActivityName,aaa.tranType, aaa.tranFromPersonId, aaa.processDeadlineDate,aaa.wfCurEmployeeId,aaa.relProjectId,aaa.workHangup  ,aaa.workDeadlineDate,aaa.stickie  ";
    String fromSql = " com.js.oa.jsflow.po.WFWorkPO aaa ";
    String whereSql = " where aaa.workStatus = " + workStatus + " and aaa.wfCurEmployeeId = " + wfCurEmployeeId + 
      " and aaa.workListControl = 1 and aaa.workDelete = 0 order by " + tempSort + " aaa.wfWorkId desc";
    if ("9".equals(workStatus))
      whereSql = " where aaa.workStatus in (0,2,101,102) and aaa.wfCurEmployeeId = " + wfCurEmployeeId + 
        " and aaa.workListControl = 1 and aaa.workDelete = 0 order by " + tempSort + " aaa.workSubmitTime desc,aaa.wfWorkId desc"; 
    if (workStatus.equals("0")) {
      whereSql = " where aaa.workStatus = 0 and aaa.wfCurEmployeeId = " + wfCurEmployeeId + 
        " and aaa.workListControl = 1 and aaa.workDelete = 0 order by " + tempSort + " aaa.emergence desc, aaa.wfWorkId desc";
      if ("4".equals(httpServletRequest.getParameter("emergence")))
        whereSql = " where aaa.workStatus = 0 and aaa.emergence='4' and aaa.wfCurEmployeeId = " + wfCurEmployeeId + 
          " and aaa.workListControl = 1 and aaa.workDelete = 0 order by " + tempSort + " aaa.emergence desc, aaa.wfWorkId desc"; 
      if ("3".equals(httpServletRequest.getParameter("emergence")))
        whereSql = " where aaa.workStatus = 0 and aaa.emergence='3' and aaa.wfCurEmployeeId = " + wfCurEmployeeId + 
          " and aaa.workListControl = 1 and aaa.workDelete = 0 order by " + tempSort + " aaa.emergence desc, aaa.wfWorkId desc"; 
      if ("2".equals(httpServletRequest.getParameter("emergence")))
        whereSql = " where aaa.workStatus = 0 and aaa.emergence='2' and aaa.wfCurEmployeeId = " + wfCurEmployeeId + 
          " and aaa.workListControl = 1 and aaa.workDelete = 0 order by " + tempSort + " aaa.emergence desc, aaa.wfWorkId desc"; 
      if ("1".equals(httpServletRequest.getParameter("emergence")))
        whereSql = " where aaa.workStatus = 0 and aaa.emergence='1' and aaa.wfCurEmployeeId = " + wfCurEmployeeId + 
          " and aaa.workListControl = 1 and aaa.workDelete = 0 order by " + tempSort + " aaa.emergence desc, aaa.wfWorkId desc"; 
      if ("0".equals(httpServletRequest.getParameter("emergence")))
        whereSql = " where aaa.workStatus = 0 and aaa.emergence='0' and aaa.wfCurEmployeeId = " + wfCurEmployeeId + 
          " and aaa.workListControl = 1 and aaa.workDelete = 0 order by " + tempSort + " aaa.emergence desc, aaa.wfWorkId desc"; 
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
    log.debug("即将进入是否只取列表的判断");
    String sortOrEmergence = httpServletRequest.getParameter("sortOrEmergence");
    log.debug("sortOrEmergence = " + httpServletRequest.getParameter("sortOrEmergence"));
    if (sortOrEmergence != null && sortOrEmergence.trim().length() != 0 && !sortOrEmergence.trim().equals("list")) {
      log.debug("取了分类与缓急");
      String sqlStr = "SELECT PACKAGENAME,aaa.WORKFILETYPE,count(WF_WORK_ID),aaa.WORKPROCESS_ID FROM JSDB.JSF_WORK aaa   left join JSDB.JSF_WORKFLOWPROCESS bbb on aaa.WORKPROCESS_ID = bbb.WF_WORKFLOWPROCESS_ID  left join JSDB.JSF_PACKAGE ccc  on bbb.WF_PACKAGE_ID=ccc.WF_PACKAGE_ID " + (

        
        "1".equals(httpServletRequest.getParameter("tran")) ? " ,JSDB.org_organization_user ddd " : "") + 
        whereSql.split(" order by")[0] + 
        " group by PACKAGENAME,aaa.WORKFILETYPE,aaa.WORKPROCESS_ID order by PACKAGENAME,aaa.WORKFILETYPE,aaa.WORKPROCESS_ID asc";
      DbOpt dbopt = null;
      try {
        dbopt = new DbOpt();
        sqlStr = sqlStr.replaceAll("ddd.empId", "ddd.emp_id").replaceAll("ddd.orgId", "ddd.org_id").replaceAll("aaa.workProcessId", "aaa.workProcess_Id");
        httpServletRequest.setAttribute("sortList", dbopt.executeQueryToStrArr2(sqlStr.replaceAll("wfCurEmployeeId", "wf_CurEmployee_Id"), 4));
        dbopt.close();
      } catch (Exception ex) {
        try {
          dbopt.close();
        } catch (SQLException sQLException) {}
        ex.printStackTrace();
        httpServletRequest.setAttribute("sortList", new String[0][3]);
      } 
      if (whereSql.indexOf("and aaa.emergence=") != -1)
        whereSql = String.valueOf(whereSql.substring(0, whereSql.indexOf("and aaa.emergence="))) + whereSql.substring(whereSql.indexOf("and aaa.emergence=") + 21, whereSql.length()); 
      String emer0 = "SELECT count(wf_work_id) from JSF_WORK aaa " + whereSql.split(" order by")[0] + " and emergence='0'";
      String emer1 = "SELECT count(wf_work_id) from JSF_WORK aaa " + whereSql.split(" order by")[0] + " and emergence='1'";
      String emer2 = "SELECT count(wf_work_id) from JSF_WORK aaa " + whereSql.split(" order by")[0] + " and emergence='2'";
      String emer3 = "SELECT count(wf_work_id) from JSF_WORK aaa " + whereSql.split(" order by")[0] + " and emergence='3'";
      String emer4 = "SELECT count(wf_work_id) from JSF_WORK aaa " + whereSql.split(" order by")[0] + " and emergence='4'";
      DbOpt dboptemer = null;
      if ("1".equals(httpServletRequest.getParameter("tran"))) {
        emer0 = "SELECT count(wf_work_id) from JSF_WORK aaa ,JSDB.org_organization_user ddd " + whereSql.split(" order by")[0] + " and emergence='0'";
        emer1 = "SELECT count(wf_work_id) from JSF_WORK aaa ,JSDB.org_organization_user ddd " + whereSql.split(" order by")[0] + " and emergence='1'";
        emer2 = "SELECT count(wf_work_id) from JSF_WORK aaa ,JSDB.org_organization_user ddd " + whereSql.split(" order by")[0] + " and emergence='2'";
        emer3 = "SELECT count(wf_work_id) from JSF_WORK aaa ,JSDB.org_organization_user ddd " + whereSql.split(" order by")[0] + " and emergence='3'";
        emer4 = "SELECT count(wf_work_id) from JSF_WORK aaa ,JSDB.org_organization_user ddd " + whereSql.split(" order by")[0] + " and emergence='4'";
        emer0 = emer0.replaceAll("ddd.empId", "ddd.emp_id").replaceAll("ddd.orgId", "ddd.org_id").replaceAll("aaa.workProcessId", "aaa.workProcess_Id");
        emer1 = emer1.replaceAll("ddd.empId", "ddd.emp_id").replaceAll("ddd.orgId", "ddd.org_id").replaceAll("aaa.workProcessId", "aaa.workProcess_Id");
        emer2 = emer2.replaceAll("ddd.empId", "ddd.emp_id").replaceAll("ddd.orgId", "ddd.org_id").replaceAll("aaa.workProcessId", "aaa.workProcess_Id");
        emer3 = emer3.replaceAll("ddd.empId", "ddd.emp_id").replaceAll("ddd.orgId", "ddd.org_id").replaceAll("aaa.workProcessId", "aaa.workProcess_Id");
        emer4 = emer4.replaceAll("ddd.empId", "ddd.emp_id").replaceAll("ddd.orgId", "ddd.org_id").replaceAll("aaa.workProcessId", "aaa.workProcess_Id");
      } 
      try {
        dboptemer = new DbOpt();
        httpServletRequest.setAttribute("emer0", dboptemer.executeQueryToStr(emer0.replaceAll("wfCurEmployeeId", "wf_CurEmployee_Id").replaceAll("aaa.workProcessId", "aaa.workProcess_Id")));
        httpServletRequest.setAttribute("emer1", dboptemer.executeQueryToStr(emer1.replaceAll("wfCurEmployeeId", "wf_CurEmployee_Id").replaceAll("aaa.workProcessId", "aaa.workProcess_Id")));
        httpServletRequest.setAttribute("emer2", dboptemer.executeQueryToStr(emer2.replaceAll("wfCurEmployeeId", "wf_CurEmployee_Id").replaceAll("aaa.workProcessId", "aaa.workProcess_Id")));
        httpServletRequest.setAttribute("emer3", dboptemer.executeQueryToStr(emer3.replaceAll("wfCurEmployeeId", "wf_CurEmployee_Id").replaceAll("aaa.workProcessId", "aaa.workProcess_Id")));
        httpServletRequest.setAttribute("emer4", dboptemer.executeQueryToStr(emer4.replaceAll("wfCurEmployeeId", "wf_CurEmployee_Id").replaceAll("aaa.workProcessId", "aaa.workProcess_Id")));
        dboptemer.close();
      } catch (Exception ex) {
        try {
          dboptemer.close();
        } catch (SQLException sQLException) {}
        ex.printStackTrace();
        httpServletRequest.setAttribute("emer0", "0");
        httpServletRequest.setAttribute("emer1", "0");
        httpServletRequest.setAttribute("emer2", "0");
        httpServletRequest.setAttribute("emer3", "0");
        httpServletRequest.setAttribute("emer4", "0");
      } 
    } else {
      if ("1".equals(httpServletRequest.getParameter("tran"))) {
        ManagerService mbd = new ManagerService();
        String curUserId = httpServletRequest.getSession(true).getAttribute("userId").toString();
        String curOrgId = httpServletRequest.getSession(true).getAttribute("orgId").toString();
        String tranWhereSql = " and aaa.workProcessId<>11 and aaa.wfCurEmployeeId=ddd.empId and ( " + mbd.getRightFinalWhere(curUserId, curOrgId, "02*03*02", "ddd.orgId", "aaa.wfCurEmployeeId") + " ) ";
        fromSql = " com.js.oa.jsflow.po.WFWorkPO aaa,  com.js.system.vo.usermanager.EmployeeOrgVO ddd";
        String[] tmp = whereSql.split("order by");
        whereSql = String.valueOf(tmp[0].replaceAll("and aaa.wfCurEmployeeId = " + httpSession.getAttribute("userId"), "")) + tranWhereSql + "order by" + tmp[1];
      } 
      log.debug("最后的分页。。。");
      String whereSQL = whereSql;
      String processId = httpServletRequest.getParameter("processID");
      if (processId != null && processId.trim().length() > 0 && !processId.toUpperCase().equals("NULL"))
        whereSQL = whereSql.replaceFirst("where", "where aaa.workProcessId=" + processId + " and "); 
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
      httpServletRequest.setAttribute("pageParameters", "workStatus,workTitle,search,submitOrg,processName,processID,searchDate,searchBeginDate,searchEndDate,tran,pressDeal,employeeId,workSubmitPerson,orderBy,sortType");
    } 
  }
}
