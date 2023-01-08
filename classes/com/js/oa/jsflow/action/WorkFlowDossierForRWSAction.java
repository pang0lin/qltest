package com.js.oa.jsflow.action;

import com.js.oa.eform.service.CustomFormBD;
import com.js.oa.jsflow.bean.WorkFlowEJBBeanForRWS;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.message.service.MsManageBD;
import com.js.oa.userdb.service.CustomDatabaseBD;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SysConfigReader;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.page.sql.Page;
import com.js.util.util.DataSourceUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WorkFlowDossierForRWSAction extends Action {
  private static String createFlowModiRight;
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    FlowListActionForm myFlowActionForm = (FlowListActionForm)actionForm;
    String tableId = httpServletRequest.getParameter("tableId");
    String processId = httpServletRequest.getParameter("processId");
    String type = httpServletRequest.getParameter("type");
    String status = httpServletRequest.getParameter("status");
    String tag = "";
    if (type.equals("endFlow")) {
      String recordId = httpServletRequest.getParameter("endRecordId");
      (new ProcessBD()).endFlowInstance(tableId, recordId);
      type = "queryData";
    } 
    if ("yuguidang".equals(type)) {
      String tableStr = httpServletRequest.getParameter("tableStr");
      String recordId = httpServletRequest.getParameter("recordId");
      status = "weiguidang";
      WorkFlowEJBBeanForRWS ejb = new WorkFlowEJBBeanForRWS();
      try {
        ejb.yuguidang(tableStr, recordId, httpServletRequest.getSession().getAttribute("userId").toString());
      } catch (Exception e) {
        e.printStackTrace();
      } 
      tag = "queryData";
      type = "queryData";
      httpServletRequest.setAttribute("optMsg", "预归档成功！");
    } 
    if ("chexiaoguidang".equals(type)) {
      String tableStr = httpServletRequest.getParameter("tableStr");
      String recordId = httpServletRequest.getParameter("recordId");
      status = "yuguidang";
      WorkFlowEJBBeanForRWS ejb = new WorkFlowEJBBeanForRWS();
      try {
        ejb.quxiaoguidang(tableStr, recordId, httpServletRequest.getSession().getAttribute("userId").toString());
      } catch (Exception e) {
        e.printStackTrace();
      } 
      tag = "queryData";
      type = "queryData";
      httpServletRequest.setAttribute("optMsg", "驳回归档成功！");
    } 
    if ("guidang".equals(type)) {
      String tableStr = httpServletRequest.getParameter("tableStr");
      String recordId = httpServletRequest.getParameter("recordId");
      status = "yuguidang";
      WorkFlowEJBBeanForRWS ejb = new WorkFlowEJBBeanForRWS();
      try {
        ejb.guidang(tableStr, recordId, httpServletRequest.getSession().getAttribute("userId").toString(), "");
      } catch (Exception e) {
        e.printStackTrace();
      } 
      tag = "queryData";
      type = "queryData";
      httpServletRequest.setAttribute("optMsg", "归档成功！");
    } 
    if ("tongyiguidang".equals(type)) {
      String tableStr = httpServletRequest.getParameter("tableStr");
      String recordId = httpServletRequest.getParameter("recordId");
      status = "yuguidang";
      WorkFlowEJBBeanForRWS ejb = new WorkFlowEJBBeanForRWS();
      try {
        ejb.tongyiguidang(tableStr, recordId, httpServletRequest.getSession().getAttribute("userId").toString());
      } catch (Exception e) {
        e.printStackTrace();
      } 
      tag = "queryData";
      type = "queryData";
    } 
    if ("guidangbukejian".equals(type)) {
      String tableStr = httpServletRequest.getParameter("tableStr");
      String recordId = httpServletRequest.getParameter("recordId");
      status = "yiguidang";
      WorkFlowEJBBeanForRWS ejb = new WorkFlowEJBBeanForRWS();
      try {
        ejb.guidangbukejian(tableStr, recordId, httpServletRequest.getSession().getAttribute("userId").toString());
      } catch (Exception e) {
        e.printStackTrace();
      } 
      tag = "queryData";
      type = "queryData";
    } 
    if (type.equals("changeHangup")) {
      String recordId = httpServletRequest.getParameter("endRecordId");
      String flag = httpServletRequest.getParameter("flag");
      (new ProcessBD()).hangupInstance(processId, flag, tableId, recordId);
      type = "queryData";
    } 
    if (type.equals("relation")) {
      String workStatus = httpServletRequest.getParameter("workStatus");
      String relationObjectType = httpServletRequest.getParameter("relationObjectType");
      String relationInfoId = httpServletRequest.getParameter("relationInfoId");
      String viewSql = "";
      String fromSql = "";
      String whereSql = "";
      viewSql = "work.wfWorkId,work.workProcessId,work.workFileType,work.workCurStep,work.workSubmitPerson,work.wfSubmitEmployeeId,work.workSubmitTime,work.workTableId,work.workRecordId,work.submitOrg";
      fromSql = "com.js.oa.relation.po.RelationDataPO rel,com.js.oa.jsflow.po.TTablePO t,com.js.oa.jsflow.po.WFWorkPO work,com.js.oa.eform.po.TAreaPO area join area.tpage page";
      String scopeWhere = getManagerScopeHQL(httpServletRequest);
      whereSql = " where rel.moduleSubId=t.tableId and t.tableName=area.areaTable and work.workTableId=page.id and work.workRecordId=rel.infoId and rel.moduleType='customdb' and work.workStatus=" + workStatus + " and rel.relationObjectType='" + relationObjectType + "' and rel.relationInfoId=" + relationInfoId;
      whereSql = String.valueOf(whereSql) + " and (" + scopeWhere + ") order by work.workSubmitTime desc";
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
      int currentPage = offset / pageSize + 1;
      Page page = new Page(viewSql, fromSql, whereSql);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      String pageCount = String.valueOf(page.getPageCount());
      httpServletRequest.setAttribute("dataList", list);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
      httpServletRequest.setAttribute("pageParameters", "type,workStatus,relationObjectType,relationInfoId");
      return actionMapping.findForward("relation");
    } 
    if (type.equals("catalog")) {
      HttpSession httpSession = httpServletRequest.getSession(true);
      ProcessBD procBD = new ProcessBD();
      httpServletRequest.setAttribute("queryType", httpServletRequest.getParameter("queryType"));
      httpServletRequest.setAttribute("status", status);
      List<Object[]> processList = null;
      processList = procBD.getUserDossViewOperAdminProc(httpSession.getAttribute("userId").toString(), httpSession.getAttribute("orgIdString").toString(), "1");
      List<Object[]> list = new ArrayList();
      String processIdList = SysConfigReader.readConfigValue("RwsGDWorkFlowIdList", "ids");
      String infoChannelIdList = SysConfigReader.readConfigValue("RwsGDInfChannelIdList", "ids");
      ArrayList<Object[]> packageList = new ArrayList();
      if (processList != null) {
        String upPackageId = "";
        for (int i = 0; i < processList.size(); i++) {
          Object[] processObj = processList.get(i);
          if (processIdList.indexOf("," + processObj[2] + ",") >= 0) {
            if (!upPackageId.equals(processObj[0].toString())) {
              upPackageId = processObj[0].toString();
              Object[] packageObj = { processObj[0], processObj[1] };
              packageList.add(packageObj);
            } 
            Object[] tmp = new Object[7];
            for (int k = 0; k < processObj.length; k++)
              tmp[k] = processObj[k]; 
            String flag = "0";
            tmp[6] = flag;
            list.add(tmp);
          } 
        } 
      } 
      httpServletRequest.setAttribute("packageList", packageList);
      httpServletRequest.setAttribute("processList", list);
      DbOpt db = new DbOpt();
      String[][] infoChannelList = (String[][])null;
      try {
        infoChannelList = db.executeQueryToStrArr2("select channel_id,channelName from oa_informationchannel where channel_id in (" + infoChannelIdList + ") order by channel_id");
        db.close();
      } catch (Exception e) {
        e.printStackTrace();
      } 
      httpServletRequest.setAttribute("infoChannelList", infoChannelList);
      tag = "catalog";
    } else if (type.equals("list")) {
      String processName = httpServletRequest.getParameter("processName");
      String processType = httpServletRequest.getParameter("processType");
      myFlowActionForm.setProcessName(processName);
      myFlowActionForm.setProcessId(processId);
      httpServletRequest.setAttribute("processId", processId);
      String viewSql = " aaa.workFileType, aaa.workCurStep, aaa.workTitle, aaa.workDeadLine, aaa.workSubmitPerson, aaa.workSubmitTime, aaa.workType, aaa.workActivity, aaa.workTableId, aaa.workRecordId, aaa.wfWorkId, aaa.workSubmitPerson,  aaa.wfSubmitEmployeeId, aaa.workDoneWithDate ";
      String fromSql = " com.js.oa.jsflow.po.WFWorkPO aaa ";
      String whereSql = " where aaa.workStartFlag = 1 and aaa.workProcessId = " + processId + 
        " and aaa.workDoneWithDate is not null order by aaa.wfWorkId desc";
      list(httpServletRequest, viewSql, fromSql, whereSql);
      httpServletRequest.setAttribute("cancelLink", "/jsoa/WorkFlowDossierAction.do?type=list&processId=" + processId + "&processName=" + processName);
      tag = "list";
    } else if (type.equals("search")) {
      String viewSql = " aaa.workFileType, aaa.workCurStep, aaa.workTitle, aaa.workDeadLine, aaa.workSubmitPerson, aaa.workSubmitTime, aaa.workType, aaa.workActivity, aaa.workTableId, aaa.workRecordId, aaa.wfWorkId, aaa.workSubmitPerson,  aaa.wfSubmitEmployeeId, aaa.workDoneWithDate ";
      String fromSql = " com.js.oa.jsflow.po.WFWorkPO aaa ";
      String tmpWhere = "";
      String hasTime = httpServletRequest.getParameter("hasTime");
      if (hasTime != null) {
        String[] startDate = httpServletRequest.getParameter("start_date").split("/");
        String[] endDate = httpServletRequest.getParameter("end_date").split("/");
        String tmpSql = "";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpWhere = "aaa.workDoneWithDate between '" + startDate[0] + "-" + 
            startDate[1] + "-" + startDate[2] + "' and '" + endDate[0] + "-" + endDate[1] + "-" + endDate[2] + " 23:59:59'";
        } else {
          tmpWhere = "aaa.workDoneWithDate between JSDB.FN_STRTODATE('" + startDate[0] + "-" + 
            startDate[1] + "-" + startDate[2] + "','L') and " + 
            " JSDB.FN_STRTODATE('" + endDate[0] + "-" + endDate[1] + "-" + endDate[2] + " 23:59:59','L')";
          tmpWhere = "aaa.workDoneWithDate between JSDB.FN_STRTODATE('" + startDate[0] + "-" + 
            startDate[1] + "-" + startDate[2] + "','L') and " + 
            " JSDB.FN_STRTODATE('" + endDate[0] + "-" + endDate[1] + "-" + endDate[2] + " 23:59:59','L')";
        } 
        httpServletRequest.setAttribute("start_date", startDate);
        httpServletRequest.setAttribute("end_date", endDate);
        httpServletRequest.setAttribute("hasTime", hasTime);
      } 
      if (tmpWhere.equals("")) {
        tmpWhere = " and aaa.workSubmitPerson like '%" + myFlowActionForm.getSubmitPerson() + "%'";
      } else {
        tmpWhere = " and " + tmpWhere + " and aaa.workSubmitPerson like '%" + myFlowActionForm.getSubmitPerson() + "%'";
      } 
      String whereSql = " where aaa.workStartFlag = 1 " + tmpWhere + " and aaa.workProcessId = " + 
        myFlowActionForm.getProcessId() + 
        " and aaa.workDoneWithDate is not null order by aaa.wfWorkId desc";
      list(httpServletRequest, viewSql, fromSql, whereSql);
      httpServletRequest.setAttribute("cancelLink", "/jsoa/WorkFlowDossierAction.do?type=list&processId=" + myFlowActionForm.getProcessId() + "&processName=" + myFlowActionForm.getProcessName());
      tag = "list";
    } else if (type.equals("queryData")) {
      String exportStr = (httpServletRequest.getParameter("export") == null) ? "" : httpServletRequest.getParameter("export");
      String idsString = "";
      String[][] formTable = (new CustomFormBD()).getTableIDAndName(tableId);
      httpServletRequest.setAttribute("processId", processId);
      httpServletRequest.setAttribute("tableId", tableId);
      httpServletRequest.setAttribute("tableName", formTable[0][1]);
      httpServletRequest.setAttribute("status", status);
      httpServletRequest.setAttribute("queryType", httpServletRequest.getParameter("queryType"));
      httpServletRequest.setAttribute("orderType", httpServletRequest.getParameter("orderType"));
      httpServletRequest.setAttribute("orderField", httpServletRequest.getParameter("orderField"));
      httpServletRequest.setAttribute("orderField", httpServletRequest.getParameter("orderField"));
      String search = httpServletRequest.getParameter("search");
      String queryType = httpServletRequest.getParameter("queryType");
      String flowName = (new CustomFormBD()).getflowName(processId);
      httpServletRequest.setAttribute("flowName", flowName);
      int pageSize = 100;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
      int currentPage = offset / pageSize + 1;
      if (formTable != null && formTable.length > 0) {
        String paraSQL, tableHead[][] = (new CustomDatabaseBD()).getListField(formTable[0][0]);
        if (httpServletRequest.getParameter("pager.offset") == null || httpServletRequest.getParameter("pager.offset").toUpperCase().equals("NULL")) {
          paraSQL = getQueryDataSQL(httpServletRequest, formTable[0][0], formTable[0][1], status);
        } else if (httpServletRequest.getSession(true).getAttribute("sql") == null || httpServletRequest.getSession(true).getAttribute("sql").toString().toUpperCase().equals("NULL") || httpServletRequest.getSession(true).getAttribute("sql").toString().trim().length() < 10) {
          paraSQL = getQueryDataSQL(httpServletRequest, formTable[0][0], formTable[0][1], status);
        } else {
          paraSQL = httpServletRequest.getSession(true).getAttribute("sql").toString();
        } 
        String idstr = formTable[0][0];
        List<String[]> idList = (new DataSourceUtil()).getListQuery("SELECT limit_prytable FROM tlimit WHERE limit_table=" + idstr, "-9");
        for (int i = 0; i < idList.size(); i++)
          idstr = String.valueOf(idstr) + "," + ((String[])idList.get(i))[0]; 
        StringBuffer sbutton = new StringBuffer("");
        MsManageBD msBD = new MsManageBD();
        String sql = "select po.statsTitle,po.statsId,po.isExtendUrl,po.extendUrl,po.statsIndexName,po.statsChart,po.statsTableId from com.js.oa.userdb.statistics.po.JsfStatisticsManage po where po.statsStatus=1 and po.statsTableId in (" + 
          
          idstr + ")";
        List<Object[]> msList = null;
        try {
          msList = msBD.getListByYourSQL(sql);
        } catch (Exception e) {
          e.printStackTrace();
        } 
        if (msList != null && msList.size() > 0)
          for (int k = 0; k < msList.size(); k++) {
            Object[] obj = msList.get(k);
            if ("1".equals(obj[2].toString())) {
              sbutton.append("<input type='button'  class=\"btnButton4Font\" onClick=\"MM_openBrWindow('" + 
                  obj[3] + 
                  "','','TOP=0,LEFT=0,scrollbars=yes,resizable=yes,width=800,height=600')\" " + 
                  " value='" + obj[0] + "' />");
            } else {
              String action = "";
              if (obj[5] != null && "5".equals(obj[5].toString())) {
                action = "statisticsOnlyData";
              } else {
                action = "statisticsDataChart";
              } 
              sbutton.append("<input type='button'  class=\"btnButton4Font\" onClick=\"doStatic('/jsoa/StatisticsAction.do?action=" + 
                  action + "&statsId=" + obj[1] + 
                  "&statsTableId=" + obj[6] + "&rightType=" + flowName + "&menuId=&processZaiBanMonitorProcessId=" + processId + 
                  "&processId=" + processId + "&processName=" + myFlowActionForm.getProcessName() + 
                  "&processType=" + httpServletRequest.getParameter("processType") + 
                  "&queryType=" + httpServletRequest.getParameter("queryType") + 
                  "&isEdit=" + httpServletRequest.getParameter("isEdit") + "&isSub=" + ((new StringBuilder(String.valueOf(formTable[0][0]))).toString().equals(obj[6]) ? 0 : 1) + 
                  "','" + obj[1] + "')\" " + 
                  " value='" + obj[0] + "' />");
            } 
          }  
        String createDate = httpServletRequest.getParameter("createDate");
        httpServletRequest.setAttribute("staticsButtonStr", sbutton);
        if (paraSQL != null) {
          httpServletRequest.setAttribute("searchTable", getSearchHTML(formTable[0][0]));
          httpServletRequest.setAttribute("tableHead", (tableHead == null) ? new String[0][0] : tableHead);
          String whereStr = "";
          if (paraSQL.indexOf(",JSF_WORK where ") > 0) {
            whereStr = ",JSF_WORK where ";
          } else if (paraSQL.indexOf("RECORD_ID where") > 0) {
            whereStr = "RECORD_ID where";
          } 
          String para = paraSQL.substring(paraSQL.indexOf("select") + 7, paraSQL.indexOf("st.guidang_time  from") + "st.guidang_time".length());
          String fromTable = paraSQL.substring(paraSQL.indexOf("tableStr,st.yuguidang_time,st.guidang_time  from") + "tableStr,st.yuguidang_time,st.guidang_time  from".length(), paraSQL.indexOf(whereStr) + 10);
          String where = paraSQL.substring(paraSQL.indexOf(whereStr) + 10);
          Page page = new Page(para, fromTable, where);
          if (!"".equals(exportStr)) {
            page.setPageSize(99999);
          } else {
            page.setPageSize(pageSize);
          } 
          page.setcurrentPage(currentPage);
          List<Object[]> list = page.getResultList();
          if (!"".equals(exportStr))
            for (int j = 0; j < list.size(); j++) {
              Object[] obj = list.get(j);
              idsString = String.valueOf(idsString) + obj[obj.length - 1] + ",";
            }  
          if (list == null) {
            httpServletRequest.setAttribute("tableData", new String[0][0]);
            httpServletRequest.setAttribute("recordCount", "0");
          } else {
            httpServletRequest.setAttribute("tableData", list);
            httpServletRequest.setAttribute("recordCount", String.valueOf(page.getRecordCount()));
          } 
          httpServletRequest.setAttribute("canModify", hasOperRights(httpServletRequest) ? "1" : "0");
        } else {
          String[][] tableData = new String[0][0];
          httpServletRequest.setAttribute("searchTable", "");
          httpServletRequest.setAttribute("tableHead", new String[0][0]);
          httpServletRequest.setAttribute("tableData", tableData);
          httpServletRequest.setAttribute("recordCount", "0");
          httpServletRequest.setAttribute("canModify", "0");
        } 
        if (!"".equals(exportStr)) {
          httpServletRequest.setAttribute("paraSQL", paraSQL.substring(paraSQL.indexOf(" from ")));
          httpServletRequest.setAttribute("ids", idsString);
          httpServletRequest.setAttribute("actinType", exportStr);
          if ("toexcel".equals(exportStr)) {
            ActionForward forward = new ActionForward("/jsflow/" + exportStr + ".jsp");
            forward.setPath("/jsflow/" + exportStr + ".jsp");
            return forward;
          } 
          if ("toexcelWithSub".equals(exportStr)) {
            ActionForward forward = new ActionForward("/jsflow/toexcelsub.jsp");
            forward.setPath("/jsflow/toexcelsub.jsp");
            return forward;
          } 
          tag = "queryData";
        } else {
          tag = "queryData";
        } 
      } else {
        String[][] tableData = new String[0][0];
        httpServletRequest.setAttribute("searchTable", "");
        httpServletRequest.setAttribute("tableHead", new String[0][0]);
        httpServletRequest.setAttribute("tableData", tableData);
        httpServletRequest.setAttribute("recordCount", "0");
      } 
      httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
      httpServletRequest.setAttribute("pageParameters", "type,processId,tableId,search,queryType,orderType,orderField,isEdit,searchStatus,exportStr");
    } else if (type.equals("toexcelWithSub")) {
      String[][] formTable = (new CustomFormBD()).getTableIDAndName(tableId);
      httpServletRequest.setAttribute("processId", processId);
      httpServletRequest.setAttribute("tableId", tableId);
      httpServletRequest.setAttribute("tableName", formTable[0][1]);
      httpServletRequest.setAttribute("queryType", httpServletRequest.getParameter("queryType"));
      httpServletRequest.setAttribute("orderType", httpServletRequest.getParameter("orderType"));
      httpServletRequest.setAttribute("orderField", httpServletRequest.getParameter("orderField"));
      String[][] tableData = (String[][])null;
      boolean flag = false;
      if (formTable != null && formTable.length > 0) {
        HttpSession session = httpServletRequest.getSession(true);
        String[][] tableHead1 = (String[][])null, tableHead2 = (String[][])null;
        if (httpServletRequest.getParameter("fields") != null)
          session.setAttribute("fields", httpServletRequest.getParameter("fields")); 
        if (httpServletRequest.getParameter("fields") == null && session.getAttribute("fields") == null) {
          tableHead1 = (new CustomDatabaseBD()).getListField(formTable[0][0]);
        } else {
          String fieldString = "";
          if (httpServletRequest.getParameter("fields") != null) {
            fieldString = httpServletRequest.getParameter("fields");
          } else {
            fieldString = session.getAttribute("fields").toString();
          } 
          tableHead1 = (new CustomDatabaseBD()).getListField(formTable[0][0], fieldString);
        } 
        if (httpServletRequest.getParameter("subfields") != null)
          session.setAttribute("subfields", httpServletRequest.getParameter("subfields")); 
        if (httpServletRequest.getParameter("subfields") == null && session.getAttribute("subfields") == null) {
          tableHead2 = (new CustomDatabaseBD()).getSubListField(formTable[0][0]);
        } else {
          String fieldString1 = "";
          if (httpServletRequest.getParameter("subfields") != null) {
            fieldString1 = httpServletRequest.getParameter("subfields");
          } else {
            fieldString1 = session.getAttribute("subfields").toString();
          } 
          tableHead2 = (new CustomDatabaseBD()).getSubListField(formTable[0][0], fieldString1);
        } 
        String[][] tableHead = (String[][])null;
        if (tableHead2 != null && tableHead2.length >= 0) {
          tableHead = unite(tableHead1, tableHead2);
          if (httpServletRequest.getParameter("pager.offset") == null || httpServletRequest.getParameter("pager.offset").toUpperCase().equals("NULL")) {
            tableData = getQueryDataAndSub(httpServletRequest, formTable[0][0], formTable[0][1]);
          } else {
            tableData = getQueryDataAndSub(httpServletRequest, formTable[0][0], formTable[0][1]);
            String sql = "";
            if (httpServletRequest.getSession(true).getAttribute("sqlex") == null) {
              sql = httpServletRequest.getSession(true).getAttribute("sql").toString();
            } else {
              sql = httpServletRequest.getSession(true).getAttribute("sqlex").toString();
            } 
            tableData = (new CustomFormBD()).loadDataBySQL(sql, String.valueOf(tableHead.length + 8));
          } 
          httpServletRequest.setAttribute("searchTable", getSearchHTML(formTable[0][0]));
          httpServletRequest.setAttribute("tableHead", (tableHead == null) ? new String[0][0] : tableHead);
          if (tableData == null)
            tableData = new String[0][0]; 
          httpServletRequest.setAttribute("tableData", tableData);
        } else {
          flag = true;
        } 
      } else {
        tableData = new String[0][0];
        httpServletRequest.setAttribute("searchTable", "");
        httpServletRequest.setAttribute("tableHead", new String[0][0]);
        httpServletRequest.setAttribute("tableData", tableData);
      } 
      if (!flag) {
        httpServletRequest.setAttribute("recordCount", String.valueOf((tableData == null) ? 0 : tableData.length));
        httpServletRequest.setAttribute("maxPageItems", "15");
        httpServletRequest.setAttribute("pageParameters", "type,processId,tableId,queryType,orderType,orderField,isEdit");
        ActionForward forward = new ActionForward("/jsflow/data2excelWithSub.jsp");
        forward.setPath("/jsflow/data2excelWithSub.jsp");
        return forward;
      } 
      type = "toexcel";
    } 
    if (type.equals("toexcel")) {
      String[][] tableData, formTable = (new CustomFormBD()).getTableIDAndName(tableId);
      httpServletRequest.setAttribute("processId", processId);
      httpServletRequest.setAttribute("tableId", tableId);
      httpServletRequest.setAttribute("tableName", formTable[0][1]);
      httpServletRequest.setAttribute("queryType", httpServletRequest.getParameter("queryType"));
      httpServletRequest.setAttribute("orderType", httpServletRequest.getParameter("orderType"));
      httpServletRequest.setAttribute("orderField", httpServletRequest.getParameter("orderField"));
      if (formTable != null && formTable.length > 0) {
        HttpSession session = httpServletRequest.getSession(true);
        session.removeAttribute("sql");
        String[][] tableHead = (String[][])null;
        if (httpServletRequest.getParameter("fields") != null)
          session.setAttribute("fields", httpServletRequest.getParameter("fields")); 
        if (httpServletRequest.getParameter("fields") == null && session.getAttribute("fields") == null) {
          tableHead = (new CustomDatabaseBD()).getListField(formTable[0][0]);
        } else {
          String fieldString = "";
          if (httpServletRequest.getParameter("fields") != null) {
            fieldString = httpServletRequest.getParameter("fields");
          } else {
            fieldString = session.getAttribute("fields").toString();
          } 
          tableHead = (new CustomDatabaseBD()).getListField(formTable[0][0], fieldString);
        } 
        if (httpServletRequest.getParameter("pager.offset") == null || httpServletRequest.getParameter("pager.offset").toUpperCase().equals("NULL")) {
          tableData = getQueryData(httpServletRequest, formTable[0][0], formTable[0][1]);
        } else if (httpServletRequest.getSession(true).getAttribute("sql") == null || httpServletRequest.getSession(true).getAttribute("sql").toString().toUpperCase().equals("NULL") || httpServletRequest.getSession(true).getAttribute("sql").toString().trim().length() < 10) {
          tableData = getQueryData(httpServletRequest, formTable[0][0], formTable[0][1]);
        } else {
          tableData = (new CustomFormBD()).loadDataBySQL(httpServletRequest.getSession(true).getAttribute("sql").toString(), String.valueOf(tableHead.length + 8));
        } 
        httpServletRequest.setAttribute("searchTable", getSearchHTML(formTable[0][0]));
        httpServletRequest.setAttribute("tableHead", (tableHead == null) ? new String[0][0] : tableHead);
        if (tableData == null)
          tableData = new String[0][0]; 
        httpServletRequest.setAttribute("tableData", tableData);
      } else {
        tableData = new String[0][0];
        httpServletRequest.setAttribute("searchTable", "");
        httpServletRequest.setAttribute("tableHead", new String[0][0]);
        httpServletRequest.setAttribute("tableData", tableData);
      } 
      httpServletRequest.setAttribute("recordCount", String.valueOf((tableData == null) ? 0 : tableData.length));
      httpServletRequest.setAttribute("maxPageItems", "15");
      httpServletRequest.setAttribute("pageParameters", "type,processId,tableId,queryType,orderType,orderField,isEdit");
      ActionForward forward = new ActionForward("/jsflow/data2excel.jsp");
      forward.setPath("/jsflow/data2excel.jsp");
      return forward;
    } 
    return actionMapping.findForward(tag);
  }
  
  public void list(HttpServletRequest httpServletRequest, String viewSql, String fromSql, String whereSql) {
    int pageSize = 100;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSql, fromSql, whereSql);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("workList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "type,processId,tableId,processName,processType,submitPerson,hasTime,start_date,end_date");
  }
  
  public String getSearchHTML(String tableId) {
    CustomDatabaseBD bd = new CustomDatabaseBD();
    String[][] queryFields = bd.getQueryField(tableId);
    HashMap<Object, Object> map = new HashMap<Object, Object>();
    if (queryFields != null && queryFields.length > 0)
      for (int i = 0; i < queryFields.length; i++)
        map.put(queryFields[i][0], bd.getQueryFieldHTML(queryFields[i][0]));  
    StringBuffer sbf = new StringBuffer("");
    if (queryFields != null && queryFields.length > 0) {
      int pt = 0;
      int colms = 2;
      HashMap<Object, Object> dateMap = new HashMap<Object, Object>();
      pt = 0;
      int rowIndex = 0;
      if (map != null && map.size() > 0) {
        while (pt < map.size()) {
          if (map.get(queryFields[pt][0]).toString().indexOf("showDateTimeBar") > 0 || map.get(queryFields[pt][0]).toString().indexOf("setDay") > 0) {
            dateMap.put(queryFields[pt][1], map.get(queryFields[pt][0]).toString());
            pt++;
            continue;
          } 
          if (rowIndex % 2 == 0)
            sbf.append("<tr><td width=20 nowrap/>"); 
          sbf.append("<td>");
          sbf.append(String.valueOf(queryFields[pt][1]) + "：");
          sbf.append("</td><td>" + map.get(queryFields[pt][0]).toString() + "</td>");
          if (rowIndex++ % 2 == 1)
            sbf.append("<td width=16 nowrap/></tr>"); 
          pt++;
        } 
        if (pt == map.size() - 1 && rowIndex-- % 2 == 0)
          sbf.append("<td cols=2></td><td width=16 nowrap/></tr>"); 
      } 
      if (dateMap != null && dateMap.size() > 0) {
        Iterator<String> it = dateMap.keySet().iterator();
        while (it.hasNext()) {
          String key = it.next();
          sbf.append("<tr><td width=20 nowrap/><td>");
          sbf.append(String.valueOf(key) + "：");
          sbf.append("</td><td colspan=\"3\">" + dateMap.get(key) + 
              "</td><td width=16 nowrap/></tr>");
        } 
      } 
    } 
    return sbf.toString();
  }
  
  public String[][] getQueryData(HttpServletRequest request, String tableId, String tableName) {
    CustomDatabaseBD bd = new CustomDatabaseBD();
    String[][] queryFields = (String[][])null;
    String[][] listFields = (String[][])null;
    int fieldNum = 0;
    HttpSession session = request.getSession(true);
    if ((request.getParameter("fields") != null && !"".equals(request.getParameter("fields"))) || (
      session.getAttribute("fields") != null && "".equals(session.getAttribute("fields").toString()))) {
      String fields = "";
      if (request.getParameter("fields") != null) {
        fields = request.getParameter("fields");
      } else {
        fields = session.getAttribute("fields").toString();
      } 
      queryFields = bd.getQueryField(tableId, fields);
      listFields = bd.getListField(tableId, fields);
      if (listFields == null)
        listFields = new String[0][0]; 
      fieldNum = listFields.length;
    } else {
      queryFields = bd.getQueryField(tableId);
      listFields = bd.getListField(tableId);
      fieldNum = listFields.length;
    } 
    if (queryFields == null)
      queryFields = new String[0][0]; 
    String[][] paras = new String[queryFields.length][3];
    String js = "";
    for (int i = 0; i < queryFields.length; i++) {
      paras[i][0] = queryFields[i][2];
      paras[i][1] = request.getParameter(String.valueOf(queryFields[i][2]) + "_type");
      if ("varchar".equals(paras[i][1]) && request.getParameter(queryFields[i][2]) != null && 
        request.getParameter(queryFields[i][2]).length() > 0) {
        paras[i][2] = String.valueOf(paras[i][0]) + " like '%" + request.getParameter(queryFields[i][2]).replaceAll("'", "''") + "%' ";
        js = String.valueOf(js) + "document.all." + queryFields[i][2] + ".value='" + request.getParameter(queryFields[i][2]).replaceAll("'", "\\\\'") + "';";
      } else if ("radiovarchar".equals(paras[i][1]) && request.getParameter(queryFields[i][2]) != null && request.getParameter(queryFields[i][2]).length() > 0) {
        paras[i][2] = String.valueOf(paras[i][0]) + " = " + request.getParameter(queryFields[i][2]);
        js = String.valueOf(js) + "if(document.all." + queryFields[i][2] + ".length){for(i=0;i<document.all." + queryFields[i][2] + ".length;i++){" + 
          "if(document.all." + queryFields[i][2] + "[i].value=='" + request.getParameter(queryFields[i][2]) + "')document.all." + queryFields[i][2] + "[i].checked=true;}}else{document.all." + queryFields[i][2] + ".checked=true;}";
      } else if ("radiovarchar".equals(paras[i][1]) && request.getParameter(queryFields[i][2]) != null && request.getParameter(queryFields[i][2]).length() > 0) {
        paras[i][2] = String.valueOf(paras[i][0]) + " = '" + request.getParameter(queryFields[i][2]) + "'";
        js = String.valueOf(js) + "if(document.all." + queryFields[i][2] + ".length){for(i=0;i<document.all." + queryFields[i][2] + ".length;i++){" + 
          "if(document.all." + queryFields[i][2] + "[i].value=='" + request.getParameter(queryFields[i][2]) + "')document.all." + queryFields[i][2] + "[i].checked=true;}}else{document.all." + queryFields[i][2] + ".checked=true;}";
      } else if ("selectnumber".equals(paras[i][1]) && request.getParameter(queryFields[i][2]) != null && 
        request.getParameter(queryFields[i][2]).length() > 0) {
        paras[i][2] = String.valueOf(paras[i][0]) + " = " + request.getParameter(queryFields[i][2]);
        js = String.valueOf(js) + "document.all." + queryFields[i][2] + ".value='" + request.getParameter(queryFields[i][2]) + "';";
        js = String.valueOf(js) + "try{document.all." + queryFields[i][2] + ".onchange();}catch(e){}";
      } else if ("selectvarchar".equals(paras[i][1]) && request.getParameter(queryFields[i][2]) != null && 
        request.getParameter(queryFields[i][2]).length() > 0) {
        paras[i][2] = String.valueOf(paras[i][0]) + " = '" + 
          request.getParameter(queryFields[i][2]) + "'";
        js = String.valueOf(js) + "document.all." + queryFields[i][2] + ".value='" + request.getParameter(queryFields[i][2]) + "';";
        js = String.valueOf(js) + "try{document.all." + queryFields[i][2] + ".onchange();}catch(e){}";
      } else if ("checkbox".equals(paras[i][1]) && request.getParameter(queryFields[i][2]) != null && 
        request.getParameter(queryFields[i][2]).length() > 0) {
        String[] values = request.getParameterValues(queryFields[i][2]);
        String tmpStr = "";
        for (int j = 0; j < values.length; j++)
          tmpStr = String.valueOf(tmpStr) + values[j] + ","; 
        paras[i][2] = String.valueOf(paras[i][0]) + " like '%" + tmpStr.substring(0, tmpStr.lastIndexOf(",")) + "%' ";
        js = String.valueOf(js) + "if(document.all." + queryFields[i][2] + ".length){for(i=0;i<document.all." + queryFields[i][2] + ".length;i++){" + 
          "if(\"" + tmpStr + "\".indexOf(document.all." + queryFields[i][2] + "[i].value)>=0)document.all." + queryFields[i][2] + "[i].checked=true;}}else{document.all." + queryFields[i][2] + ".checked=true;}";
      } else if ("date".equals(paras[i][1]) || "time".equals(paras[i][1]) || "datetime".equals(paras[i][1])) {
        if ("1".equals(request.getParameter(String.valueOf(queryFields[i][2]) + "date"))) {
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "date.checked=true;";
          String end = "";
          String start = request.getParameter(String.valueOf(queryFields[i][2]) + "_start");
          end = request.getParameter(String.valueOf(queryFields[i][2]) + "_end");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_start.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_start") + "';";
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_end.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_end") + "';";
          if ("date".equals(paras[i][1])) {
            if (DbOpt.dbtype.indexOf("oracle") >= 0) {
              paras[i][2] = "to_date(" + paras[i][0] + ",'YYYY-MM-DD') between to_date('" + start + "','YYYY-MM-DD') and to_date('" + end + "','YYYY-MM-DD') ";
            } else {
              paras[i][2] = String.valueOf(paras[i][0]) + " between '" + start + "' and '" + end + "' ";
            } 
          } else if ("time".equals(paras[i][1])) {
            if (DbOpt.dbtype.indexOf("oracle") >= 0) {
              paras[i][2] = "to_date(" + paras[i][0] + ",'MM:MI:SS')  between to_date('" + start + "','HH24:MI:SS') and to_date('" + end + "','HH24:MI:SS') ";
            } else {
              paras[i][2] = String.valueOf(paras[i][0]) + " between '" + start + "' and '" + end + "' ";
            } 
          } else if ("datetime".equals(paras[i][1])) {
            if (DbOpt.dbtype.indexOf("oracle") >= 0) {
              paras[i][2] = "to_date(" + paras[i][0] + ",'YYYY-MM-DD HH24:MI:SS') between to_date('" + start + "','YYYY-MM-DD HH24:MI:SS') and to_date('" + end + "','YYYY-MM-DD HH24:MI:SS') ";
            } else {
              paras[i][2] = String.valueOf(paras[i][0]) + " between '" + start + "' and '" + end + "' ";
            } 
          } 
        } 
      } else if ("number".equals(paras[i][1]) && request.getParameter(queryFields[i][2]) != null && 
        request.getParameter(queryFields[i][2]).length() > 0) {
        paras[i][2] = String.valueOf(paras[i][0]) + " = " + request.getParameter(queryFields[i][2]);
        js = String.valueOf(js) + "document.all." + queryFields[i][2] + ".value='" + request.getParameter(queryFields[i][2]).replaceAll("'", "\\\\'") + "';";
      } else if ("float".equals(paras[i][1])) {
        if (request.getParameter(String.valueOf(queryFields[i][2]) + "_begin") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").trim().length() > 0 && request.getParameter(String.valueOf(queryFields[i][2]) + "_end") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_end").trim().length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " >= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_begin.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").replaceAll("'", "\\\\'") + "';";
          paras[i][2] = String.valueOf(paras[i][2]) + " and " + paras[i][0] + " <= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_end");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_end.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_end").replaceAll("'", "\\\\'") + "';";
        } else if (request.getParameter(String.valueOf(queryFields[i][2]) + "_begin") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").trim().length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " >= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_begin.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").replaceAll("'", "\\\\'") + "';";
        } else if (request.getParameter(String.valueOf(queryFields[i][2]) + "_end") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_end").trim().length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " <= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_end");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_end.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_end").replaceAll("'", "\\\\'") + "';";
        } 
      } else if ("integer".equals(paras[i][1])) {
        if (request.getParameter(String.valueOf(queryFields[i][2]) + "_begin") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").trim().length() > 0 && request.getParameter(String.valueOf(queryFields[i][2]) + "_end") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_end").trim().length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " >= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_begin.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").replaceAll("'", "\\\\'") + "';";
          paras[i][2] = String.valueOf(paras[i][2]) + " and " + paras[i][0] + " <= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_end");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_end.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_end").replaceAll("'", "\\\\'") + "';";
        } else if (request.getParameter(String.valueOf(queryFields[i][2]) + "_begin") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").trim().length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " >= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_begin.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").replaceAll("'", "\\\\'") + "';";
        } else if (request.getParameter(String.valueOf(queryFields[i][2]) + "_end") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_end").trim().length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " <= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_end");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_end.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_end").replaceAll("'", "\\\\'") + "';";
        } 
      } 
    } 
    String parameters = "select distinct ";
    if (listFields != null && listFields.length > 0) {
      for (int j = 0; j < listFields.length; j++) {
        if (SystemCommon.getDatabaseType().equals("oracle") && "1000003".equals(listFields[j][6])) {
          parameters = String.valueOf(parameters) + "to_char(" + listFields[j][2] + "),";
        } else {
          parameters = String.valueOf(parameters) + listFields[j][2] + ",";
        } 
      } 
    } else {
      return null;
    } 
    String rightWhere = getManagerScope(request);
    String[] subTable = (new CustomFormBD()).getForeignTable(request.getParameter("tableId"));
    int index = 0;
    String tableStr = tableName;
    while (subTable != null && subTable.length > 0 && index < subTable.length) {
      if (tableName.indexOf(subTable[index]) > 0) {
        index++;
        continue;
      } 
      tableStr = String.valueOf(tableStr) + " left join " + subTable[index] + " on " + tableName + "_ID=" + subTable[index] + "_FOREIGNKEY";
      index++;
    } 
    String para = "";
    parameters = String.valueOf(parameters) + "worksubmittime,workstatus,workprocess_id,WORKCURSTEP,WF_CUREMPLOYEE_ID,WORKRECORD_ID,WORKFILETYPE,WF_WORK_ID from " + tableStr;
    String joinType = SystemCommon.getFlowDossierJoinType();
    if ("".equals(joinType)) {
      parameters = String.valueOf(parameters) + ",JSF_WORK where " + tableName + "_ID=WORKRECORD_ID and WORKPROCESS_ID=";
    } else if ("right".equals(joinType)) {
      parameters = String.valueOf(parameters) + " right join JSF_WORK on " + tableName + "_ID=WORKRECORD_ID where WORKPROCESS_ID=";
    } else {
      parameters = String.valueOf(parameters) + " left join JSF_WORK on " + tableName + "_ID=WORKRECORD_ID where WORKPROCESS_ID=";
    } 
    parameters = String.valueOf(parameters) + request.getParameter("processId") + " and (" + rightWhere + ") and ";
    if (paras != null && paras.length > 0)
      for (int j = 0; j < paras.length; j++) {
        if (paras[j][2] != null && paras[j][2].length() > 0) {
          parameters = String.valueOf(parameters) + paras[j][2] + " and ";
          para = String.valueOf(para) + paras[j][2] + " and ";
        } 
      }  
    request.getSession(true).setAttribute("para", para);
    String searchStatus = request.getParameter("searchStatus");
    if (searchStatus == null || "null".equals(searchStatus))
      searchStatus = ""; 
    if ("0".equals(request.getParameter("queryType"))) {
      parameters = String.valueOf(parameters) + " WORKSTATUS=1";
      if (!"".equals(searchStatus))
        parameters = String.valueOf(parameters) + " and workcurstep like '%" + searchStatus + "%'"; 
    } else if ("100".equals(request.getParameter("queryType"))) {
      if ("".equals(searchStatus)) {
        parameters = String.valueOf(parameters) + " (WORKSTATUS=100 or WORKSTATUS=-1 or WORKSTATUS=-2)";
      } else {
        parameters = String.valueOf(parameters) + " workstatus=" + searchStatus;
      } 
    } 
    if ("1".equals(request.getParameter("createDate"))) {
      js = String.valueOf(js) + "document.all.createDate.checked=true;";
      js = String.valueOf(js) + "document.all.createStartDate.value='" + request.getParameter("createStartDate") + "';";
      js = String.valueOf(js) + "document.all.createEndDate.value='" + request.getParameter("createEndDate") + "';";
      if (DbOpt.dbtype.indexOf("oracle") >= 0) {
        parameters = String.valueOf(parameters) + " and to_date(" + tableName + "_date,'YYYY-MM-DD') between to_date('" + request.getParameter("createStartDate") + "','YYYY-MM-DD') and to_date('" + request.getParameter("createEndDate") + "','YYYY-MM-DD') ";
      } else if (DbOpt.dbtype.indexOf("sqlserver") >= 0) {
        parameters = String.valueOf(parameters) + " and " + tableName + "_date>= convert(datetime,'" + request.getParameter("createStartDate") + "') and " + tableName + "_date<=convert(datetime,'" + request.getParameter("createEndDate") + " 23:59:59') ";
      } else if (DbOpt.dbtype.indexOf("mysql") >= 0) {
        parameters = String.valueOf(parameters) + " and " + tableName + "_date>= date_format('" + request.getParameter("createStartDate") + "','%Y-%m-%d') and " + tableName + "_date<=date_format('" + request.getParameter("createEndDate") + " 23:59:59','%Y-%m-%d %H:%i:%s') ";
      } 
    } 
    if (request.getParameter("ids") != null && !"".equals(request.getParameter("ids")) && !"null".equalsIgnoreCase(request.getParameter("ids"))) {
      String[] ids = request.getParameter("ids").split(",");
      parameters = String.valueOf(parameters) + " and (1<>1 ";
      for (int j = 0; j < ids.length; j++)
        parameters = String.valueOf(parameters) + " or WF_WORK_ID=" + ids[j] + " "; 
      parameters = String.valueOf(parameters) + ")";
    } 
    String order = (request.getParameter("orderField") == null || request.getParameter("orderField").trim().equalsIgnoreCase("null")) ? "WF_WORK_ID " : request.getParameter("orderField");
    order = String.valueOf(order) + " " + ((request.getParameter("orderType") == null || request.getParameter("orderType").trim().equalsIgnoreCase("null")) ? " desc  " : request.getParameter("orderType"));
    parameters = String.valueOf(parameters) + " order by " + order;
    setSumFieldValue(request, parameters.substring(parameters.indexOf(" from")));
    request.getSession(true).setAttribute("sql", parameters);
    request.getSession(true).setAttribute("js", js);
    String[][] result = (new CustomFormBD()).loadDataBySQL(parameters, String.valueOf(fieldNum + 8));
    if (result == null || result.length < 1)
      result = new String[0][0]; 
    return result;
  }
  
  private boolean hasAdminRights(HttpServletRequest request) {
    boolean hasRight = false;
    DbOpt dbopt = null;
    String curOrgIdString = getCurOrgIdStr(request);
    String curGroupId = (request.getSession(true).getAttribute("orgIdString") == null) ? "  " : request.getSession(true).getAttribute("orgIdString").toString();
    String curUserId = request.getSession(true).getAttribute("userId").toString();
    try {
      dbopt = new DbOpt();
      Map map = dbopt.executeQueryToMap("select PROCESSADMINORG,PROCESSADMINGROUP,PROCESSADMINPERSON from JSF_WORKFLOWPROCESS where WF_WORKFLOWPROCESS_ID=" + request.getParameter("processId"));
      String adminUser = (map.get("processadminperson") == null) ? null : map.get("processadminperson").toString();
      String adminGroup = (map.get("processadmingroup") == null) ? null : map.get("processadmingroup").toString();
      String adminOrg = (map.get("processadminorg") == null) ? null : map.get("processadminorg").toString();
      if (!hasRight && adminUser != null && adminUser.indexOf("$") >= 0 && 
        adminUser.indexOf("$" + curUserId + "$") >= 0)
        hasRight = true; 
      if (!hasRight && adminOrg != null && adminOrg.indexOf("*") >= 0) {
        adminOrg = ("0*" + adminOrg + "*0").replaceAll("\\*\\*", ",");
        String[][] orgArr = dbopt.executeQueryToStrArr2("select ORGIDSTRING from ORG_ORGANIZATION where ORG_ID in(" + adminOrg + ")", 1);
        int i = 0;
        while (!hasRight && orgArr != null && orgArr.length > 0 && i < orgArr.length) {
          if (curOrgIdString.startsWith(orgArr[i][0]))
            hasRight = true; 
          i++;
        } 
      } 
      if (!hasRight && adminGroup != null && adminGroup.indexOf("@") >= 0 && 
        adminGroup.indexOf("@" + curGroupId + "@") >= 0)
        hasRight = true; 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return hasRight;
  }
  
  private boolean hasFlowModiRight(String userId) {
    boolean hasModiRight = false;
    if (createFlowModiRight == null) {
      DbOpt dbopt = null;
      try {
        dbopt = new DbOpt();
        ResultSet rs = dbopt.executeQuery("select count(*) from org_right where rightcode='02*03*03'");
        int num = 0;
        if (rs.next())
          num = rs.getInt(1); 
        rs.close();
        if (num > 0) {
          createFlowModiRight = "1";
        } else {
          createFlowModiRight = "0";
        } 
        dbopt.close();
      } catch (Exception ex) {
        ex.printStackTrace();
        if (dbopt != null)
          try {
            dbopt.close();
          } catch (SQLException err) {
            err.printStackTrace();
          }  
      } 
    } 
    if ("1".equals(createFlowModiRight)) {
      ManagerService managerBD = new ManagerService();
      hasModiRight = managerBD.hasRight(userId, "02*03*03");
    } else {
      hasModiRight = true;
    } 
    return hasModiRight;
  }
  
  private boolean hasOperRights(HttpServletRequest request) {
    boolean hasRight = false;
    DbOpt dbopt = null;
    String curOrgIdString = getCurOrgIdStr(request);
    String curGroupId = (request.getSession(true).getAttribute("orgIdString") == null) ? "  " : request.getSession(true).getAttribute("orgIdString").toString();
    String curUserId = request.getSession(true).getAttribute("userId").toString();
    boolean hasModiRight = hasFlowModiRight(curUserId);
    try {
      if (hasModiRight) {
        dbopt = new DbOpt();
        Map map = dbopt.executeQueryToMap("select DOSSIERFILEOPERORG,DOSSIERFILEOPERGROUP,DOSSIERFILEOPERPERSON from JSF_WORKFLOWPROCESS where WF_WORKFLOWPROCESS_ID=" + request.getParameter("processId"));
        String adminUser = (map.get("dossierfileoperperson") == null) ? null : map.get("dossierfileoperperson").toString();
        String adminGroup = (map.get("dossierfileopergroup") == null) ? null : map.get("dossierfileopergroup").toString();
        String adminOrg = (map.get("dossierfileoperorg") == null) ? null : map.get("dossierfileoperorg").toString();
        if (!hasRight && adminUser != null && adminUser.indexOf("$") >= 0 && 
          adminUser.indexOf("$" + curUserId + "$") >= 0)
          hasRight = true; 
        if (!hasRight && adminOrg != null && adminOrg.indexOf("*") >= 0) {
          adminOrg = ("0*" + adminOrg + "*0").replaceAll("\\*\\*", ",");
          String[][] orgArr = dbopt.executeQueryToStrArr2("select ORGIDSTRING from ORG_ORGANIZATION where ORG_ID in(" + adminOrg + ")", 1);
          int i = 0;
          while (!hasRight && orgArr != null && orgArr.length > 0 && i < orgArr.length) {
            if (curOrgIdString.startsWith(orgArr[i][0]))
              hasRight = true; 
            i++;
          } 
        } 
        if (!hasRight && adminGroup != null && adminGroup.indexOf("@") >= 0 && 
          adminGroup.indexOf("@" + curGroupId + "@") >= 0)
          hasRight = true; 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (dbopt != null)
        try {
          dbopt.close();
        } catch (SQLException ex) {
          ex.printStackTrace();
        }  
    } 
    return (hasRight && hasModiRight);
  }
  
  private String getCurOrgIdStr(HttpServletRequest request) {
    String idStr = "";
    DbOpt dbopt = null;
    String orgId = request.getSession(true).getAttribute("orgId").toString();
    try {
      dbopt = new DbOpt();
      idStr = dbopt.executeQueryToStr("select ORGIDSTRING from ORG_ORGANIZATION where ORG_ID=" + orgId);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return idStr;
  }
  
  public void setSumFieldValue(HttpServletRequest request, String sql) {
    CustomDatabaseBD bd = new CustomDatabaseBD();
    List<Object[]> list = bd.getTotFieldInfo(request.getParameter("tableId"));
    String[][] sumFieldValue = (String[][])null;
    String res = "";
    int i = 0, fieldNum = -1;
    String subFieldSql = sql;
    if (list != null && list.size() > 0) {
      sumFieldValue = new String[list.size()][3];
      String sumSql = "";
      for (i = 0; i < list.size(); i++) {
        Object[] obj = list.get(i);
        if ("form1".equals(obj[2].toString())) {
          fieldNum++;
          sumFieldValue[fieldNum][0] = obj[0].toString();
          sumFieldValue[fieldNum][1] = obj[1].toString();
          if (fieldNum == 0) {
            sumSql = "sum(" + sumFieldValue[fieldNum][1] + ")";
          } else {
            sumSql = String.valueOf(sumSql) + ",sum(" + sumFieldValue[fieldNum][1] + ")";
          } 
        } 
      } 
      if (sumSql.startsWith("sum")) {
        if (sql.indexOf("_FOREIGNKEY") > 0) {
          String sqlTemp = sql.substring(0, sql.lastIndexOf("_FOREIGNKEY"));
          sql = sql.substring(sql.lastIndexOf("_FOREIGNKEY") + 11);
          sql = String.valueOf(sqlTemp.substring(0, sqlTemp.indexOf("left join"))) + sql;
        } 
        sql = String.valueOf(sumSql) + sql;
        DbOpt dbopt = new DbOpt();
        try {
          String[][] sum = dbopt.executeQueryToStrArr2("select  " + sql, fieldNum + 1);
          if (sum != null && sum.length == 1)
            for (i = 0; i < fieldNum + 1; i++)
              res = String.valueOf(res) + "&nbsp;&nbsp;" + sumFieldValue[i][0] + ":" + sum[0][i];  
          dbopt.close();
        } catch (Exception ex) {
          try {
            dbopt.close();
          } catch (Exception err) {
            err.printStackTrace();
          } 
          ex.printStackTrace();
        } 
      } 
    } 
    if (list != null && list.size() > 0) {
      DbOpt dbopt = null;
      try {
        dbopt = new DbOpt();
        fieldNum = -1;
        sumFieldValue = new String[list.size()][3];
        if (subFieldSql != null && subFieldSql.lastIndexOf("_FOREIGNKEY") > 0) {
          String sumSql = "";
          String sqlTemp = subFieldSql.substring(0, subFieldSql.lastIndexOf("_FOREIGNKEY"));
          subFieldSql = subFieldSql.substring(subFieldSql.lastIndexOf("_FOREIGNKEY") + 11);
          sqlTemp = sqlTemp.substring(0, sqlTemp.indexOf("left join"));
          String mainTableName = sqlTemp.substring(sqlTemp.indexOf("from ") + 5).trim();
          for (i = 0; i < list.size(); i++) {
            Object[] obj = list.get(i);
            if (!"form1".equals(obj[2].toString())) {
              fieldNum++;
              sumFieldValue[fieldNum][0] = obj[0].toString();
              sumFieldValue[fieldNum][1] = obj[1].toString();
              String subTableName = obj[2].toString();
              sumSql = "select sum(" + sumFieldValue[fieldNum][1] + ")";
              sql = String.valueOf(sumSql) + sqlTemp + "left join " + subTableName + " on " + mainTableName + "_ID=" + subTableName + "_FOREIGNKEY " + subFieldSql;
              String sum = dbopt.executeQueryToStr(sql);
              if (sum != null)
                res = String.valueOf(res) + "&nbsp;&nbsp;" + sumFieldValue[fieldNum][0] + ":" + sum; 
            } 
          } 
        } 
        dbopt.close();
      } catch (Exception ex) {
        try {
          if (dbopt != null)
            dbopt.close(); 
        } catch (Exception err) {
          err.printStackTrace();
        } 
        ex.printStackTrace();
      } 
    } 
    request.setAttribute("sumFieldValue", res);
  }
  
  public String getManagerScope(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    ManagerService managerBD = new ManagerService();
    List<Object[]> rightList = managerBD.getRightScope(userId, "02*03*01");
    String rightWhere = "1<>1";
    if (rightList != null && rightList.size() > 0 && !hasAdminRights(request)) {
      String rangeArray[], tmpW;
      int j;
      Object[] objRight = rightList.get(0);
      int scopeType = Integer.parseInt(objRight[0].toString());
      switch (scopeType) {
        case 0:
          rightWhere = "1=1";
          break;
        case 1:
          rightWhere = "wf_submitemployee_id=" + userId;
          break;
        case 3:
          rightWhere = "wf_submitemployee_id in (select emp_id from org_organization_user where org_id=" + orgId + ")";
          break;
        case 2:
          rightWhere = "wf_submitemployee_id in (select b.emp_id from org_organization a,org_organization_user b where a.org_id = b.org_id and a.orgidstring like '%$" + orgId + "$%')";
          break;
        case 4:
          rangeArray = objRight[1].toString().split("\\*");
          tmpW = "";
          for (j = 0; j < rangeArray.length; j++) {
            if (!"".equals(rangeArray[j]))
              tmpW = String.valueOf(tmpW) + " a.orgidstring like '%$" + rangeArray[j] + "$%' or"; 
          } 
          if (tmpW.endsWith("or"))
            tmpW = tmpW.substring(0, tmpW.length() - 2); 
          rightWhere = "wf_submitemployee_id in (select b.emp_id from org_organization a,org_organization_user b where a.org_id = b.org_id and (" + tmpW + "))";
          break;
      } 
    } else if (hasAdminRights(request)) {
      rightWhere = "1=1";
    } 
    return rightWhere;
  }
  
  public String getManagerScopeHQL(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    ManagerService managerBD = new ManagerService();
    List<Object[]> rightList = managerBD.getRightScope(userId, "02*03*01");
    String rightWhere = "1<>1";
    if (rightList != null && rightList.size() > 0 && !hasAdminRights(request)) {
      String rangeArray[], tmpW;
      int j;
      Object[] objRight = rightList.get(0);
      int scopeType = Integer.parseInt(objRight[0].toString());
      switch (scopeType) {
        case 0:
          rightWhere = "1=1";
          break;
        case 1:
          rightWhere = "work.wfSubmitEmployeeId=" + userId;
          break;
        case 3:
          rightWhere = "work.wfSubmitEmployeeId in (select po.empId from com.js.system.vo.usermanager.EmployeeOrgVO po where po.orgId=" + orgId + ")";
          break;
        case 2:
          rightWhere = "work.wfSubmitEmployeeId in (select po.empId from com.js.system.vo.usermanager.EmployeeVO po join po.organizations org  where org.orgIdString like  JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', " + orgId + "),'$%'))";
          break;
        case 4:
          rangeArray = objRight[1].toString().split("\\*");
          tmpW = "";
          j = 0;
          for (; j < rangeArray.length; j++) {
            if (!"".equals(rangeArray[j]))
              tmpW = String.valueOf(tmpW) + " org.orgIdString like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', " + rangeArray[j] + "),'$%') or"; 
          } 
          if (tmpW.endsWith("or"))
            tmpW = tmpW.substring(0, tmpW.length() - 2); 
          if (!"".equals(tmpW))
            rightWhere = "work.wfSubmitEmployeeId in (select po.empId from com.js.system.vo.usermanager.EmployeeVO po join po.organizations org where  (" + 
              tmpW + "))"; 
          break;
      } 
    } else if (hasAdminRights(request)) {
      rightWhere = "1=1";
    } 
    return rightWhere;
  }
  
  public String[][] getQueryDataAndSub(HttpServletRequest request, String tableId, String tableName) {
    CustomDatabaseBD bd = new CustomDatabaseBD();
    String[][] queryFields = (String[][])null;
    String[][] listFields = (String[][])null;
    int fieldNum = 0;
    HttpSession session = request.getSession(true);
    if ((request.getParameter("fields") != null && !"".equals(request.getParameter("fields"))) || (
      session.getAttribute("fields") != null && "".equals(session.getAttribute("fields").toString()))) {
      String fields = "";
      if (request.getParameter("fields") != null) {
        fields = request.getParameter("fields");
      } else {
        fields = session.getAttribute("fields").toString();
      } 
      queryFields = bd.getQueryField(tableId, fields);
      listFields = bd.getListField(tableId, fields);
      if (listFields == null)
        listFields = new String[0][0]; 
      fieldNum = listFields.length;
    } else {
      queryFields = bd.getQueryField(tableId);
      listFields = bd.getListField(tableId);
      fieldNum = listFields.length;
    } 
    if (queryFields == null)
      queryFields = new String[0][0]; 
    String[][] paras = new String[queryFields.length][3];
    String js = "";
    for (int i = 0; i < queryFields.length; i++) {
      paras[i][0] = queryFields[i][2];
      paras[i][1] = request.getParameter(String.valueOf(queryFields[i][2]) + "_type");
      if ("varchar".equals(paras[i][1]) && request.getParameter(queryFields[i][2]) != null && 
        request.getParameter(queryFields[i][2]).length() > 0) {
        paras[i][2] = String.valueOf(paras[i][0]) + " like '%" + request.getParameter(queryFields[i][2]).replaceAll("'", "''") + "%' ";
        js = String.valueOf(js) + "document.all." + queryFields[i][2] + ".value='" + request.getParameter(queryFields[i][2]).replaceAll("'", "\\\\'") + "';";
      } else if ("radiovarchar".equals(paras[i][1]) && request.getParameter(queryFields[i][2]) != null && request.getParameter(queryFields[i][2]).length() > 0) {
        paras[i][2] = String.valueOf(paras[i][0]) + " = " + request.getParameter(queryFields[i][2]);
        js = String.valueOf(js) + "if(document.all." + queryFields[i][2] + ".length){for(i=0;i<document.all." + queryFields[i][2] + ".length;i++){" + 
          "if(document.all." + queryFields[i][2] + "[i].value=='" + request.getParameter(queryFields[i][2]) + "')document.all." + queryFields[i][2] + "[i].checked=true;}}else{document.all." + queryFields[i][2] + ".checked=true;}";
      } else if ("radiovarchar".equals(paras[i][1]) && request.getParameter(queryFields[i][2]) != null && request.getParameter(queryFields[i][2]).length() > 0) {
        paras[i][2] = String.valueOf(paras[i][0]) + " = '" + request.getParameter(queryFields[i][2]) + "'";
        js = String.valueOf(js) + "if(document.all." + queryFields[i][2] + ".length){for(i=0;i<document.all." + queryFields[i][2] + ".length;i++){" + 
          "if(document.all." + queryFields[i][2] + "[i].value=='" + request.getParameter(queryFields[i][2]) + "')document.all." + queryFields[i][2] + "[i].checked=true;}}else{document.all." + queryFields[i][2] + ".checked=true;}";
      } else if ("selectnumber".equals(paras[i][1]) && request.getParameter(queryFields[i][2]) != null && 
        request.getParameter(queryFields[i][2]).length() > 0) {
        paras[i][2] = String.valueOf(paras[i][0]) + " = " + request.getParameter(queryFields[i][2]);
        js = String.valueOf(js) + "document.all." + queryFields[i][2] + ".value='" + request.getParameter(queryFields[i][2]) + "';";
        js = String.valueOf(js) + "try{document.all." + queryFields[i][2] + ".onchange();}catch(e){}";
      } else if ("selectvarchar".equals(paras[i][1]) && request.getParameter(queryFields[i][2]) != null && 
        request.getParameter(queryFields[i][2]).length() > 0) {
        paras[i][2] = String.valueOf(paras[i][0]) + " = '" + 
          request.getParameter(queryFields[i][2]) + "'";
        js = String.valueOf(js) + "document.all." + queryFields[i][2] + ".value='" + request.getParameter(queryFields[i][2]) + "';";
        js = String.valueOf(js) + "try{document.all." + queryFields[i][2] + ".onchange();}catch(e){}";
      } else if ("checkbox".equals(paras[i][1]) && request.getParameter(queryFields[i][2]) != null && 
        request.getParameter(queryFields[i][2]).length() > 0) {
        String[] values = request.getParameterValues(queryFields[i][2]);
        String tmpStr = "";
        for (int j = 0; j < values.length; j++)
          tmpStr = String.valueOf(tmpStr) + values[j] + ","; 
        paras[i][2] = String.valueOf(paras[i][0]) + " like '%" + tmpStr.substring(0, tmpStr.lastIndexOf(",")) + "%' ";
        js = String.valueOf(js) + "if(document.all." + queryFields[i][2] + ".length){for(i=0;i<document.all." + queryFields[i][2] + ".length;i++){" + 
          "if(\"" + tmpStr + "\".indexOf(document.all." + queryFields[i][2] + "[i].value)>=0)document.all." + queryFields[i][2] + "[i].checked=true;}}else{document.all." + queryFields[i][2] + ".checked=true;}";
      } else if ("date".equals(paras[i][1]) || "time".equals(paras[i][1]) || "datetime".equals(paras[i][1])) {
        if ("1".equals(request.getParameter(String.valueOf(queryFields[i][2]) + "date"))) {
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "date.checked=true;";
          String end = "";
          String start = request.getParameter(String.valueOf(queryFields[i][2]) + "_start");
          end = request.getParameter(String.valueOf(queryFields[i][2]) + "_end");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_start.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_start") + "';";
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_end.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_end") + "';";
          paras[i][2] = String.valueOf(paras[i][0]) + " between '" + start + "' and '" + end + "' ";
        } 
      } else if ("number".equals(paras[i][1]) && request.getParameter(queryFields[i][2]) != null && 
        request.getParameter(queryFields[i][2]).length() > 0) {
        paras[i][2] = String.valueOf(paras[i][0]) + " = " + request.getParameter(queryFields[i][2]);
        js = String.valueOf(js) + "document.all." + queryFields[i][2] + ".value='" + request.getParameter(queryFields[i][2]).replaceAll("'", "\\\\'") + "';";
      } else if ("float".equals(paras[i][1])) {
        if (request.getParameter(String.valueOf(queryFields[i][2]) + "_begin") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").trim().length() > 0 && request.getParameter(String.valueOf(queryFields[i][2]) + "_end") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_end").trim().length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " >= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_begin.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").replaceAll("'", "\\\\'") + "';";
          paras[i][2] = String.valueOf(paras[i][2]) + " and " + paras[i][0] + " <= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_end");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_end.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_end").replaceAll("'", "\\\\'") + "';";
        } else if (request.getParameter(String.valueOf(queryFields[i][2]) + "_begin") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").trim().length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " >= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_begin.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").replaceAll("'", "\\\\'") + "';";
        } else if (request.getParameter(String.valueOf(queryFields[i][2]) + "_end") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_end").trim().length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " <= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_end");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_end.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_end").replaceAll("'", "\\\\'") + "';";
        } 
      } else if ("integer".equals(paras[i][1])) {
        if (request.getParameter(String.valueOf(queryFields[i][2]) + "_begin") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").trim().length() > 0 && request.getParameter(String.valueOf(queryFields[i][2]) + "_end") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_end").trim().length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " >= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_begin.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").replaceAll("'", "\\\\'") + "';";
          paras[i][2] = String.valueOf(paras[i][2]) + " and " + paras[i][0] + " <= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_end");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_end.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_end").replaceAll("'", "\\\\'") + "';";
        } else if (request.getParameter(String.valueOf(queryFields[i][2]) + "_begin") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").trim().length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " >= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_begin.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").replaceAll("'", "\\\\'") + "';";
        } else if (request.getParameter(String.valueOf(queryFields[i][2]) + "_end") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_end").trim().length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " <= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_end");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_end.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_end").replaceAll("'", "\\\\'") + "';";
        } 
      } 
    } 
    String parameters = "select ";
    if (listFields != null && listFields.length > 0) {
      for (int j = 0; j < listFields.length; j++)
        parameters = String.valueOf(parameters) + listFields[j][2] + ","; 
    } else {
      return null;
    } 
    String rightWhere = getManagerScope(request);
    String[] subTableInfo = request.getParameter("subTable").split("-");
    String[][] subListFields = (String[][])null;
    if (request.getParameter("subfields") != null) {
      subListFields = bd.getSubListField(tableId, request.getParameter("subfields"));
    } else {
      subListFields = bd.getSubListField(tableId);
    } 
    int index = 0;
    String tableStr = tableName;
    String subName = "";
    tableStr = String.valueOf(tableStr) + " left join " + subTableInfo[0] + " on " + tableName + "_ID=" + subTableInfo[0] + "_FOREIGNKEY";
    subName = String.valueOf(subName) + "," + subTableInfo[0] + "_id";
    if (subListFields != null && subListFields.length > 0) {
      for (int j = 0; j < subListFields.length; j++)
        parameters = String.valueOf(parameters) + subListFields[j][2] + ","; 
    } else {
      return null;
    } 
    parameters = String.valueOf(parameters) + "worksubmittime,workstatus,workprocess_id,WORKCURSTEP,WF_CUREMPLOYEE_ID,WORKRECORD_ID,WORKFILETYPE,WF_WORK_ID from " + tableStr;
    String joinType = SystemCommon.getFlowDossierJoinType();
    if ("".equals(joinType)) {
      parameters = String.valueOf(parameters) + ",JSF_WORK where " + tableName + "_ID=WORKRECORD_ID and WORKPROCESS_ID=";
    } else if ("right".equals(joinType)) {
      parameters = String.valueOf(parameters) + " right join JSF_WORK on " + tableName + "_ID=WORKRECORD_ID where WORKPROCESS_ID=";
    } else {
      parameters = String.valueOf(parameters) + " left join JSF_WORK on " + tableName + "_ID=WORKRECORD_ID where WORKPROCESS_ID=";
    } 
    parameters = String.valueOf(parameters) + request.getParameter("processId") + " and (" + rightWhere + ") and ";
    if (paras != null && paras.length > 0)
      for (int j = 0; j < paras.length; j++) {
        if (paras[j][2] != null && paras[j][2].length() > 0)
          parameters = String.valueOf(parameters) + paras[j][2] + " and "; 
      }  
    parameters = String.valueOf(parameters) + ((request.getSession(true).getAttribute("para") == null) ? "" : request.getSession(true).getAttribute("para").toString());
    String searchStatus = request.getParameter("searchStatus");
    if (searchStatus == null || "null".equals(searchStatus))
      searchStatus = ""; 
    if ("0".equals(request.getParameter("queryType"))) {
      parameters = String.valueOf(parameters) + " WORKSTATUS=1";
      if (!"".equals(searchStatus))
        parameters = String.valueOf(parameters) + " and workcurstep like '%" + searchStatus + "%'"; 
    } else if ("100".equals(request.getParameter("queryType"))) {
      if ("".equals(searchStatus)) {
        parameters = String.valueOf(parameters) + " (WORKSTATUS=100 or WORKSTATUS=-1 or WORKSTATUS=-2)";
      } else {
        parameters = String.valueOf(parameters) + " workstatus=" + searchStatus;
      } 
    } 
    if ("1".equals(request.getParameter("createDate")))
      if (DbOpt.dbtype.indexOf("oracle") < 0)
        if (DbOpt.dbtype.indexOf("sqlserver") >= 0) {
          parameters = String.valueOf(parameters) + " and " + tableName + "_date>= convert(datetime,'" + request.getParameter("createStartDate") + "') and " + tableName + "_date<=convert(datetime,'" + request.getParameter("createEndDate") + " 23:59:59') ";
        } else if (DbOpt.dbtype.indexOf("mysql") >= 0) {
          parameters = String.valueOf(parameters) + " and " + tableName + "_date>= date_format('" + request.getParameter("createStartDate") + "','%Y-%m-%d') and " + tableName + "_date<=date_format('" + request.getParameter("createEndDate") + " 23:59:59','%Y-%m-%d %H:%i:%s') ";
        }   
    if (request.getParameter("ids") != null && !"".equals(request.getParameter("ids")) && !"null".equalsIgnoreCase(request.getParameter("ids"))) {
      String[] ids = request.getParameter("ids").split(",");
      parameters = String.valueOf(parameters) + " and (1<>1 ";
      for (int j = 0; j < ids.length; j++)
        parameters = String.valueOf(parameters) + " or WF_WORK_ID=" + ids[j] + " "; 
      parameters = String.valueOf(parameters) + ")";
    } 
    String order = (request.getParameter("orderField") == null || request.getParameter("orderField").trim().equalsIgnoreCase("null")) ? "WF_WORK_ID " : request.getParameter("orderField");
    order = String.valueOf(order) + " " + ((request.getParameter("orderType") == null || request.getParameter("orderType").trim().equalsIgnoreCase("null")) ? " desc  " : request.getParameter("orderType"));
    parameters = String.valueOf(parameters) + " order by " + order + subName;
    request.getSession(true).setAttribute("sqlex", parameters);
    request.getSession(true).setAttribute("js", js);
    String[][] result = (new CustomFormBD()).loadDataBySQL(parameters, String.valueOf(listFields.length + subListFields.length + 8));
    if (result == null || result.length < 1)
      result = new String[0][0]; 
    return result;
  }
  
  public static String[][] unite(String[][] os1, String[][] os2) {
    if (os1 == null)
      os1 = new String[0][0]; 
    if (os2 == null)
      os2 = new String[0][0]; 
    List<String[]> list = (List)new ArrayList<String>(Arrays.asList((String[])os1));
    list.addAll(Arrays.asList((String[])os2));
    return list.<String[]>toArray(os1);
  }
  
  public String getQueryDataSQL(HttpServletRequest request, String tableId, String tableName, String status) {
    CustomDatabaseBD bd = new CustomDatabaseBD();
    String[][] queryFields = bd.getQueryField(tableId);
    String[][] listFields = bd.getListField(tableId);
    if (queryFields == null)
      queryFields = new String[0][0]; 
    String[][] paras = new String[queryFields.length][3];
    String js = "";
    for (int i = 0; i < queryFields.length; i++) {
      paras[i][0] = queryFields[i][2];
      paras[i][1] = request.getParameter(String.valueOf(queryFields[i][2]) + "_type");
      if ("varchar".equals(paras[i][1]) && request.getParameter(queryFields[i][2]) != null && 
        request.getParameter(queryFields[i][2]).length() > 0) {
        paras[i][2] = String.valueOf(paras[i][0]) + " like '%" + request.getParameter(queryFields[i][2]).replaceAll("'", "''") + "%' ";
        js = String.valueOf(js) + "document.all." + queryFields[i][2] + ".value='" + request.getParameter(queryFields[i][2]).replaceAll("'", "\\\\'") + "';";
      } else if ("radiovarchar".equals(paras[i][1]) && request.getParameter(queryFields[i][2]) != null && request.getParameter(queryFields[i][2]).length() > 0) {
        paras[i][2] = String.valueOf(paras[i][0]) + " = '" + request.getParameter(queryFields[i][2]) + "'";
        js = String.valueOf(js) + "if(document.all." + queryFields[i][2] + ".length){for(i=0;i<document.all." + queryFields[i][2] + ".length;i++){" + 
          "if(document.all." + queryFields[i][2] + "[i].value=='" + request.getParameter(queryFields[i][2]) + "')document.all." + queryFields[i][2] + "[i].checked=true;}}else{document.all." + queryFields[i][2] + ".checked=true;}";
      } else if ("radiovarchar".equals(paras[i][1]) && request.getParameter(queryFields[i][2]) != null && request.getParameter(queryFields[i][2]).length() > 0) {
        paras[i][2] = String.valueOf(paras[i][0]) + " = '" + request.getParameter(queryFields[i][2]) + "'";
        js = String.valueOf(js) + "if(document.all." + queryFields[i][2] + ".length){for(i=0;i<document.all." + queryFields[i][2] + ".length;i++){" + 
          "if(document.all." + queryFields[i][2] + "[i].value=='" + request.getParameter(queryFields[i][2]) + "')document.all." + queryFields[i][2] + "[i].checked=true;}}else{document.all." + queryFields[i][2] + ".checked=true;}";
      } else if ("selectnumber".equals(paras[i][1]) && request.getParameter(queryFields[i][2]) != null && 
        request.getParameter(queryFields[i][2]).length() > 0) {
        paras[i][2] = String.valueOf(paras[i][0]) + " = " + request.getParameter(queryFields[i][2]);
        js = String.valueOf(js) + "document.all." + queryFields[i][2] + ".value='" + request.getParameter(queryFields[i][2]) + "';";
        js = String.valueOf(js) + "try{document.all." + queryFields[i][2] + ".onchange();}catch(e){}";
      } else if ("selectvarchar".equals(paras[i][1]) && request.getParameter(queryFields[i][2]) != null && 
        request.getParameter(queryFields[i][2]).length() > 0) {
        paras[i][2] = String.valueOf(paras[i][0]) + " = '" + 
          request.getParameter(queryFields[i][2]) + "'";
        js = String.valueOf(js) + "document.all." + queryFields[i][2] + ".value='" + request.getParameter(queryFields[i][2]) + "';";
        js = String.valueOf(js) + "try{document.all." + queryFields[i][2] + ".onchange();}catch(e){}";
      } else if ("checkbox".equals(paras[i][1]) && request.getParameter(queryFields[i][2]) != null && 
        request.getParameter(queryFields[i][2]).length() > 0) {
        String[] values = request.getParameterValues(queryFields[i][2]);
        String tmpStr = "";
        for (int j = 0; j < values.length; j++)
          tmpStr = String.valueOf(tmpStr) + values[j] + ","; 
        paras[i][2] = String.valueOf(paras[i][0]) + " like '%" + tmpStr.substring(0, tmpStr.lastIndexOf(",")) + "%' ";
        js = String.valueOf(js) + "if(document.all." + queryFields[i][2] + ".length){for(i=0;i<document.all." + queryFields[i][2] + ".length;i++){" + 
          "if(\"" + tmpStr + "\".indexOf(document.all." + queryFields[i][2] + "[i].value)>=0)document.all." + queryFields[i][2] + "[i].checked=true;}}else{document.all." + queryFields[i][2] + ".checked=true;}";
      } else if ("date".equals(paras[i][1]) || "time".equals(paras[i][1]) || "datetime".equals(paras[i][1])) {
        if ("1".equals(request.getParameter(String.valueOf(queryFields[i][2]) + "date"))) {
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "date.checked=true;";
          String end = "";
          String start = request.getParameter(String.valueOf(queryFields[i][2]) + "_start");
          end = request.getParameter(String.valueOf(queryFields[i][2]) + "_end");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_start.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_start") + "';";
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_end.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_end") + "';";
          if ("date".equals(paras[i][1])) {
            if (DbOpt.dbtype.indexOf("oracle") >= 0) {
              paras[i][2] = "to_date(" + paras[i][0] + ",'YYYY-MM-DD') between to_date('" + start + "','YYYY-MM-DD') and to_date('" + end + "','YYYY-MM-DD') ";
            } else {
              paras[i][2] = String.valueOf(paras[i][0]) + " between '" + start + "' and '" + end + "' ";
            } 
          } else if ("time".equals(paras[i][1])) {
            if (DbOpt.dbtype.indexOf("oracle") >= 0) {
              paras[i][2] = "to_date(" + paras[i][0] + ",'MM:MI:SS')  between to_date('" + start + "','HH24:MI:SS') and to_date('" + end + "','HH24:MI:SS') ";
            } else {
              paras[i][2] = String.valueOf(paras[i][0]) + " between '" + start + "' and '" + end + "' ";
            } 
          } else if ("datetime".equals(paras[i][1])) {
            if (DbOpt.dbtype.indexOf("oracle") >= 0) {
              paras[i][2] = "to_date(" + paras[i][0] + ",'YYYY-MM-DD HH24:MI:SS') between to_date('" + start + "','YYYY-MM-DD HH24:MI:SS') and to_date('" + end + "','YYYY-MM-DD HH24:MI:SS') ";
            } else {
              paras[i][2] = String.valueOf(paras[i][0]) + " between '" + start + "' and '" + end + "' ";
            } 
          } 
        } 
      } else if ("number".equals(paras[i][1]) && request.getParameter(queryFields[i][2]) != null && 
        request.getParameter(queryFields[i][2]).length() > 0) {
        paras[i][2] = String.valueOf(paras[i][0]) + " = " + request.getParameter(queryFields[i][2]);
        js = String.valueOf(js) + "document.all." + queryFields[i][2] + ".value='" + request.getParameter(queryFields[i][2]).replaceAll("'", "\\\\'") + "';";
      } else if ("float".equals(paras[i][1])) {
        if (request.getParameter(String.valueOf(queryFields[i][2]) + "_begin") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").trim().length() > 0 && request.getParameter(String.valueOf(queryFields[i][2]) + "_end") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_end").trim().length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " >= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_begin.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").replaceAll("'", "\\\\'") + "';";
          paras[i][2] = String.valueOf(paras[i][2]) + " and " + paras[i][0] + " <= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_end");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_end.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_end").replaceAll("'", "\\\\'") + "';";
        } else if (request.getParameter(String.valueOf(queryFields[i][2]) + "_begin") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").trim().length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " >= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_begin.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").replaceAll("'", "\\\\'") + "';";
        } else if (request.getParameter(String.valueOf(queryFields[i][2]) + "_end") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_end").trim().length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " <= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_end");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_end.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_end").replaceAll("'", "\\\\'") + "';";
        } 
      } else if ("integer".equals(paras[i][1])) {
        if (request.getParameter(String.valueOf(queryFields[i][2]) + "_begin") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").trim().length() > 0 && request.getParameter(String.valueOf(queryFields[i][2]) + "_end") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_end").trim().length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " >= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_begin.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").replaceAll("'", "\\\\'") + "';";
          paras[i][2] = String.valueOf(paras[i][2]) + " and " + paras[i][0] + " <= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_end");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_end.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_end").replaceAll("'", "\\\\'") + "';";
        } else if (request.getParameter(String.valueOf(queryFields[i][2]) + "_begin") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").trim().length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " >= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_begin.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_begin").replaceAll("'", "\\\\'") + "';";
        } else if (request.getParameter(String.valueOf(queryFields[i][2]) + "_end") != null && request.getParameter(String.valueOf(queryFields[i][2]) + "_end").trim().length() > 0) {
          paras[i][2] = String.valueOf(paras[i][0]) + " <= " + request.getParameter(String.valueOf(queryFields[i][2]) + "_end");
          js = String.valueOf(js) + "document.all." + queryFields[i][2] + "_end.value='" + request.getParameter(String.valueOf(queryFields[i][2]) + "_end").replaceAll("'", "\\\\'") + "';";
        } 
      } 
    } 
    String parameters = "select distinct ";
    if (listFields != null && listFields.length > 0) {
      for (int j = 0; j < listFields.length; j++) {
        if (SystemCommon.getDatabaseType().equals("oracle") && "1000003".equals(listFields[j][6])) {
          parameters = String.valueOf(parameters) + "to_char(" + listFields[j][2] + "),";
        } else {
          parameters = String.valueOf(parameters) + listFields[j][2] + ",";
        } 
      } 
    } else {
      return null;
    } 
    String rightWhere = getManagerScope(request);
    String[] subTable = (new CustomFormBD()).getForeignTable(request.getParameter("tableId"));
    int index = 0;
    String tableStr = tableName;
    while (subTable != null && subTable.length > 0 && index < subTable.length) {
      if (tableName.indexOf(subTable[index]) > 0) {
        index++;
        continue;
      } 
      tableStr = String.valueOf(tableStr) + " left join " + subTable[index] + " on " + tableName + "_ID=" + subTable[index] + "_FOREIGNKEY";
      index++;
    } 
    String para = "";
    parameters = String.valueOf(parameters) + "WORK_HANGUP,worksubmittime,workstatus,workprocess_id,WORKCURSTEP,WF_CUREMPLOYEE_ID,WORKRECORD_ID,WORKFILETYPE,WF_WORK_ID,(select max(status) from RWS_WORKFLOWSTATUS where tablename='" + tableName + "' and recordid=" + tableName + "_id) guidangstatus,'" + tableName + "' tableStr,st.yuguidang_time,st.guidang_time  from rws_workflowstatus st," + tableStr + " ";
    String joinType = SystemCommon.getFlowDossierJoinType();
    if ("".equals(joinType)) {
      parameters = String.valueOf(parameters) + ",JSF_WORK where " + tableName + "_ID=WORKRECORD_ID and WORKPROCESS_ID=";
    } else if ("right".equals(joinType)) {
      parameters = String.valueOf(parameters) + " right join JSF_WORK on " + tableName + "_ID=WORKRECORD_ID where WORKPROCESS_ID=";
    } else {
      parameters = String.valueOf(parameters) + " left join JSF_WORK on " + tableName + "_ID=WORKRECORD_ID where WORKPROCESS_ID=";
    } 
    parameters = String.valueOf(parameters) + request.getParameter("processId") + " and (" + rightWhere + ") and ";
    parameters = String.valueOf(parameters) + " st.tablename='" + tableName + "' and recordid=WORKRECORD_ID and ";
    if (paras != null && paras.length > 0)
      for (int j = 0; j < paras.length; j++) {
        if (paras[j][2] != null && paras[j][2].length() > 0) {
          parameters = String.valueOf(parameters) + paras[j][2] + " and ";
          para = String.valueOf(para) + paras[j][2] + " and ";
        } 
      }  
    request.getSession(true).setAttribute("para", para);
    String searchStatus = request.getParameter("searchStatus");
    if (searchStatus == null || "null".equals(searchStatus))
      searchStatus = ""; 
    if ("0".equals(request.getParameter("queryType"))) {
      parameters = String.valueOf(parameters) + " WORKSTATUS=1";
      if (!"".equals(searchStatus))
        parameters = String.valueOf(parameters) + " and workcurstep like '%" + searchStatus + "%'"; 
    } else if ("100".equals(request.getParameter("queryType"))) {
      if ("".equals(searchStatus)) {
        parameters = String.valueOf(parameters) + " (WORKSTATUS=100)";
      } else {
        parameters = String.valueOf(parameters) + " workstatus=" + searchStatus;
      } 
    } 
    String createDate = request.getParameter("createDate");
    if ("1".equals(request.getParameter("createDate"))) {
      js = String.valueOf(js) + "document.all.createDate.checked=true;";
      js = String.valueOf(js) + "document.all.createStartDate.value='" + request.getParameter("createStartDate") + "';";
      js = String.valueOf(js) + "document.all.createEndDate.value='" + request.getParameter("createEndDate") + "';";
      if (DbOpt.dbtype.indexOf("oracle") >= 0) {
        parameters = String.valueOf(parameters) + " and to_date(" + tableName + "_date,'YYYY-MM-DD') between to_date('" + request.getParameter("createStartDate") + "','YYYY-MM-DD') and to_date('" + request.getParameter("createEndDate") + "','YYYY-MM-DD') ";
      } else if (DbOpt.dbtype.indexOf("sqlserver") >= 0) {
        parameters = String.valueOf(parameters) + " and " + tableName + "_date>= convert(datetime,'" + request.getParameter("createStartDate") + "') and " + tableName + "_date<=convert(datetime,'" + request.getParameter("createEndDate") + " 23:59:59') ";
      } else if (DbOpt.dbtype.indexOf("mysql") >= 0) {
        parameters = String.valueOf(parameters) + " and " + tableName + "_date>= date_format('" + request.getParameter("createStartDate") + "','%Y-%m-%d') and " + tableName + "_date<=date_format('" + request.getParameter("createEndDate") + " 23:59:59','%Y-%m-%d %H:%i:%s') ";
      } 
    } 
    if ("weiguidang".equals(status)) {
      parameters = String.valueOf(parameters) + " and (not exists(select 1 from RWS_WORKFLOWSTATUS where tablename='" + tableStr + "' and recordid=" + tableStr + "_id) or " + 
        " exists(select 1 from RWS_WORKFLOWSTATUS where tablename='" + tableStr + "' and recordid=" + tableStr + "_id and status=2)) ";
    } else if ("yuguidang".equals(status)) {
      parameters = String.valueOf(parameters) + " and  st.status = 0 ";
    } else if ("yiguidang".equals(status)) {
      parameters = String.valueOf(parameters) + " and  st.status = 1";
    } 
    String order = (request.getParameter("orderField") == null || request.getParameter("orderField").trim().equalsIgnoreCase("null")) ? "WF_WORK_ID " : request.getParameter("orderField");
    order = String.valueOf(order) + " " + ((request.getParameter("orderType") == null || request.getParameter("orderType").trim().equalsIgnoreCase("null")) ? " desc  " : request.getParameter("orderType"));
    parameters = String.valueOf(parameters) + " order by " + order;
    setSumFieldValue(request, parameters.substring(parameters.indexOf(" from")));
    request.getSession(true).setAttribute("sql", parameters);
    request.getSession(true).setAttribute("js", js);
    return parameters;
  }
}
