package com.js.oa.userdb.statistics.service;

import com.js.oa.jsflow.action.WorkFlowDossierAction;
import com.js.oa.message.service.MsManageBD;
import com.js.oa.module.service.ModuleMenuService;
import com.js.oa.userdb.statistics.bean.JsfStatisticsEJBBean;
import com.js.oa.userdb.statistics.po.JsfStatisticsManage;
import com.js.oa.userdb.statistics.util.JsfStatisticsChartUtil;
import com.js.oa.userdb.statistics.util.JsfStatisticsCommonUtil;
import com.js.util.config.SystemCommon;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class JsfStatisticsService {
  public JsfStatisticsManage loadStatisticsManage(Long id) {
    JsfStatisticsManage po = null;
    JsfStatisticsEJBBean db = new JsfStatisticsEJBBean();
    try {
      po = db.loadStatisticsManage(id);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return po;
  }
  
  public Map queryStatisticsData(HttpServletRequest request, int page_size) throws Exception {
    JsfStatisticsEJBBean db = new JsfStatisticsEJBBean();
    Map reMap = null;
    try {
      String processZaiBanMonitorProcessId = request.getParameter("processZaiBanMonitorProcessId");
      if (processZaiBanMonitorProcessId == null || "".equals(processZaiBanMonitorProcessId) || 
        "null".equals(processZaiBanMonitorProcessId)) {
        reMap = queryStatisticsDataWithModule(request, page_size);
      } else {
        reMap = queryStatisticsDataWithProcessZaiBanMonitor(request, page_size);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return reMap;
  }
  
  public Map queryStatisticsDataWithModule(HttpServletRequest request, int page_size) throws Exception {
    JsfStatisticsEJBBean db = new JsfStatisticsEJBBean();
    HttpSession session = request.getSession(true);
    Long domainId = (session.getAttribute("domainId") != null) ? 
      Long.valueOf(session.getAttribute("domainId").toString()) : 
      Long.valueOf("0");
    MsManageBD msBD = new MsManageBD();
    List<Object[]> list = null;
    Map<Object, Object> reMap = new HashMap<Object, Object>();
    try {
      String databaseType = SystemCommon.getDatabaseType();
      int pageSize = page_size;
      int offset = 0;
      if (request.getParameter("pager.offset") != null)
        offset = Integer.parseInt(request.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      int recordCount = 0;
      String statsId = request.getParameter("statsId");
      String rightType = request.getParameter("rightType");
      String moduleProcessId = request.getParameter("moduleProcessId");
      String processZaiBanMonitorWhere = request.getParameter("processZaiBanMonitorWhere");
      String processZaiBanMonitorProcessId = request.getParameter("processZaiBanMonitorProcessId");
      String menuId = request.getParameter("menuId");
      String sortType = request.getParameter("sortType");
      request.setAttribute("sortType", sortType);
      if (sortType == null || "".equals(sortType) || "null".equals(sortType))
        sortType = "desc"; 
      request.setAttribute("statsId", statsId);
      request.setAttribute("rightType", rightType);
      request.setAttribute("moduleProcessId", moduleProcessId);
      request.setAttribute("processZaiBanMonitorWhere", processZaiBanMonitorWhere);
      request.setAttribute("processZaiBanMonitorProcessId", processZaiBanMonitorProcessId);
      JsfStatisticsManage po = null;
      po = db.loadStatisticsManage(Long.valueOf(statsId));
      String statsIndex = po.getStatsIndex();
      String statsType = po.getStatsType();
      String statsGroupIndex = po.getStatsGroupIndex();
      String statsOrder = po.getStatsOrder();
      String statsOrderNum = po.getStatsOrderNum();
      SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
      String statsBeginTime = format2.format(po.getStatsBeginTime());
      Calendar c = Calendar.getInstance();
      c.setTime(po.getStatsEndTime());
      c.add(5, 1);
      Date newDate = c.getTime();
      String statsEndTime = format2.format(newDate);
      String statsChart = po.getStatsChart();
      String statsTableId = po.getStatsTableId().toString();
      String statsIndexName = po.getStatsIndexName();
      list = msBD.getListByYourSQL("select t.tableId,t.tableName from com.js.oa.jsflow.po.TTablePO t where t.tableId=" + 
          statsTableId);
      String tableName = "";
      if (list != null && list.size() > 0) {
        Object[] obj = list.get(0);
        tableName = obj[1].toString();
      } 
      reMap.put("tableName", tableName);
      list = msBD.getListByYourSQL("select t.fieldId,t.fieldName,t.fieldDesName from com.js.oa.jsflow.po.TFieldPO t where t.fieldId=" + 
          statsIndex);
      String indexName = "";
      String fieldDesName = "";
      if (list != null && list.size() > 0) {
        Object[] obj = list.get(0);
        indexName = obj[1].toString();
        fieldDesName = obj[2].toString();
      } 
      reMap.put("fieldName", indexName);
      String viewScopeWhere = "";
      ModuleMenuService bd = new ModuleMenuService();
      viewScopeWhere = bd.getViewScope(tableName, 
          request.getSession(true).getAttribute("orgIdString").toString(), 
          request.getSession(true).getAttribute("userId").toString(), 
          request.getSession(true).getAttribute("orgId").toString(), 
          rightType);
      viewScopeWhere = viewScopeWhere.trim();
      if (viewScopeWhere.equals("1=1")) {
        viewScopeWhere = " ( 1=1 ";
      } else {
        viewScopeWhere = viewScopeWhere.substring(0, 
            viewScopeWhere.length() - 1);
      } 
      viewScopeWhere = String.valueOf(viewScopeWhere) + " or " + tableName + 
        "_ORG is null or " + tableName + 
        "_org='' )";
      String isCollect = request.getParameter("isCollect");
      if (isCollect == null || "".equals(isCollect) || "null".equals(isCollect)) {
        JsfStatisticsCommonUtil util = new JsfStatisticsCommonUtil();
        String searchParam = util.getModuleSearchParam(domainId.toString(), menuId, request);
        if (searchParam != null && !"".equals(searchParam))
          viewScopeWhere = String.valueOf(viewScopeWhere) + searchParam; 
      } else if ("1".equals(isCollect)) {
        viewScopeWhere = String.valueOf(viewScopeWhere) + " and " + tableName + "_relaByInde=" + menuId;
      } else {
        viewScopeWhere = " " + tableName + "_relaByInde=" + menuId;
      } 
      String sql = "";
      if (moduleProcessId != null && !"".equals(moduleProcessId) && !"-1".equals(moduleProcessId)) {
        String wh = " AND WORKPROCESS_ID=" + moduleProcessId + " AND  WORKSTATUS=1";
        if ("emp".equals(statsGroupIndex)) {
          sql = " select max(t0.empname)," + statsType + "(t0.i) r" + " from ( " + 
            "select e.empname,CASE WHEN " + indexName + " IS NULL THEN 0 ELSE " + indexName + " END AS i," + 
            tableName + "_date," + tableName + "_OWNER," + tableName + "_ORG from " + tableName + 
            "  INNER JOIN JSF_WORK ON " + tableName + "_ID=WORKRECORD_ID" + 
            " LEFT JOIN org_employee e ON " + tableName + "_OWNER=e.emp_id " + 
            " where " + tableName + "_date >='" + statsBeginTime + 
            "' and " + tableName + "_date <'" + statsEndTime + "' and " + viewScopeWhere + 
            wh + 
            ") t0   " + 
            "group by t0.empname order by r " + statsOrder;
        } else if ("org".equals(statsGroupIndex)) {
          sql = " select max(t0.orgname)," + statsType + "(t0.i) r" + " from ( " + 
            "select o.orgname,CASE WHEN " + indexName + " IS NULL THEN 0 ELSE " + indexName + " END AS i," + 
            tableName + "_date," + tableName + "_OWNER," + tableName + "_ORG from " + tableName + 
            "  INNER JOIN JSF_WORK ON " + tableName + "_ID=WORKRECORD_ID" + 
            " LEFT JOIN org_organization_user ou ON " + tableName + "_OWNER=ou.emp_id " + 
            " LEFT JOIN  org_organization o ON ou.org_id=o.org_id " + 
            " where " + tableName + "_date >='" + statsBeginTime + 
            "' and " + tableName + "_date <'" + statsEndTime + "' and " + viewScopeWhere + 
            wh + 
            ") t0   " + 
            "group by t0.orgname order by r " + statsOrder;
        } else {
          list = msBD.getListByYourSQL("select t.fieldId,t.fieldName from com.js.oa.jsflow.po.TFieldPO t where t.fieldId=" + 
              statsGroupIndex);
          String groupIndexName = "";
          if (list != null && list.size() > 0) {
            Object[] obj = list.get(0);
            groupIndexName = obj[1].toString();
          } 
          sql = " select max(t0." + groupIndexName + ")," + statsType + "(t0.i) r" + " from ( " + 
            "select " + groupIndexName + ",CASE WHEN " + indexName + " IS NULL THEN 0 ELSE " + indexName + " END AS i," + 
            tableName + "_date," + tableName + "_OWNER," + tableName + "_ORG from " + tableName + 
            "  INNER JOIN JSF_WORK ON " + tableName + "_ID=WORKRECORD_ID" + 
            " where " + tableName + "_date >='" + statsBeginTime + 
            "' and " + tableName + "_date <='" + statsEndTime + "' and " + viewScopeWhere + 
            wh + 
            ") t0   group by t0." + groupIndexName + " order by r " + statsOrder;
        } 
      } else if ("emp".equals(statsGroupIndex)) {
        sql = " select max(t0.empname)," + statsType + "(t0.i) r" + " from ( " + 
          "select e.empname,CASE WHEN " + indexName + " IS NULL THEN 0 ELSE " + indexName + " END AS i," + 
          tableName + "_date," + tableName + "_OWNER," + tableName + "_ORG from " + tableName + 
          " LEFT JOIN org_employee e ON " + tableName + "_OWNER=e.emp_id " + 
          " where " + tableName + "_date >='" + statsBeginTime + 
          "' and " + tableName + "_date <'" + statsEndTime + "' and " + viewScopeWhere + 
          ") t0   " + 
          "group by t0.empname order by r " + statsOrder;
      } else if ("org".equals(statsGroupIndex)) {
        sql = " select max(t0.orgname)," + statsType + "(t0.i) r" + " from ( " + 
          "select o.orgname,CASE WHEN " + indexName + " IS NULL THEN 0 ELSE " + indexName + " END AS i," + 
          tableName + "_date," + tableName + "_OWNER," + tableName + "_ORG from " + tableName + 
          " LEFT JOIN org_organization_user ou ON " + tableName + "_OWNER=ou.emp_id " + 
          " LEFT JOIN  org_organization o ON ou.org_id=o.org_id " + 
          " where " + tableName + "_date >='" + statsBeginTime + 
          "' and " + tableName + "_date <'" + statsEndTime + "' and " + viewScopeWhere + 
          ") t0   " + 
          "group by t0.orgname order by r " + statsOrder;
      } else {
        list = msBD.getListByYourSQL("select t.fieldId,t.fieldName from com.js.oa.jsflow.po.TFieldPO t where t.fieldId=" + 
            statsGroupIndex);
        String groupIndexName = "";
        if (list != null && list.size() > 0) {
          Object[] obj = list.get(0);
          groupIndexName = obj[1].toString();
        } 
        sql = " select max(t0." + groupIndexName + ")," + statsType + "(t0.i) r" + " from ( select " + groupIndexName + ",CASE WHEN " + indexName + " IS NULL THEN 0 ELSE " + indexName + " END AS i," + 
          tableName + "_date," + tableName + "_OWNER," + tableName + "_ORG from " + tableName + " where " + tableName + "_date >='" + statsBeginTime + 
          "' and " + tableName + "_date <='" + statsEndTime + "' and " + viewScopeWhere + 
          ") t0   group by t0." + groupIndexName + " order by r " + statsOrder;
      } 
      String qSql = "";
      if ("oracle".equals(databaseType)) {
        if (statsOrderNum == null || "".equals(statsOrderNum)) {
          qSql = "select j.*,rownum rn  from (select t.* from (" + sql + ")t ) j ORDER BY j.r " + sortType;
        } else {
          qSql = "select j.*,rownum rn  from (select t.* from (" + sql + ")t ) j ORDER BY j.r " + sortType;
        } 
      } else if (statsOrderNum == null || "".equals(statsOrderNum)) {
        qSql = "select * from (select t.*,'rn' from (" + sql + ")t ) j ORDER BY j.r " + sortType;
      } else {
        qSql = "select * from (select t.*,'rn' from (" + sql + ")t  limit " + statsOrderNum + ") j ORDER BY j.r " + sortType;
      } 
      recordCount = db.queryStatisticsDataCount("select count(*) from (" + qSql + ") w");
      String reSql = "select * from (" + qSql + ") w";
      if ("oracle".equals(databaseType)) {
        reSql = String.valueOf(reSql) + " where w.rn >=" + ((currentPage - 1) * pageSize + 1) + 
          "and w.rn <=" + (currentPage * pageSize);
      } else {
        reSql = String.valueOf(reSql) + " limit " + ((currentPage - 1) * pageSize) + "," + pageSize;
      } 
      list = db.queryStatisticsData(reSql);
      reMap.put("recordCount", Integer.valueOf(recordCount));
      reMap.put("reList", list);
      request.setAttribute("valueType", String.valueOf(statsType) + "(" + fieldDesName + ")");
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action,statsId,rightType,moduleProcessId,processZaiBanMonitorWhere,processZaiBanMonitorProcessId,menuId,sortType");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return reMap;
  }
  
  public Map queryStatisticsDataWithProcessZaiBanMonitor(HttpServletRequest request, int page_size) throws Exception {
    JsfStatisticsEJBBean db = new JsfStatisticsEJBBean();
    MsManageBD msBD = new MsManageBD();
    List<Object[]> list = null;
    Map<Object, Object> reMap = new HashMap<Object, Object>();
    try {
      String databaseType = SystemCommon.getDatabaseType();
      int pageSize = page_size;
      int offset = 0;
      if (request.getParameter("pager.offset") != null)
        offset = Integer.parseInt(request.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      int recordCount = 0;
      String statsId = request.getParameter("statsId");
      String rightType = request.getParameter("rightType");
      String moduleProcessId = request.getParameter("moduleProcessId");
      String processZaiBanMonitorProcessId = request.getParameter("processZaiBanMonitorProcessId");
      String processId = request.getParameter("processId");
      String processName = request.getParameter("processName");
      String processType = request.getParameter("processType");
      String queryType = request.getParameter("queryType");
      String isEdit = request.getParameter("isEdit");
      String isSub = (request.getParameter("isSub") == null) ? "" : request.getParameter("isSub");
      String sortType = request.getParameter("sortType");
      request.setAttribute("sortType", sortType);
      request.setAttribute("queryType", queryType);
      request.setAttribute("processId", processId);
      request.setAttribute("processName", processName);
      request.setAttribute("processType", processType);
      request.setAttribute("isEdit", isEdit);
      request.setAttribute("statsId", statsId);
      request.setAttribute("rightType", rightType);
      request.setAttribute("moduleProcessId", moduleProcessId);
      request.setAttribute("processZaiBanMonitorProcessId", processZaiBanMonitorProcessId);
      JsfStatisticsManage po = null;
      po = db.loadStatisticsManage(Long.valueOf(statsId));
      String statsIndex = po.getStatsIndex();
      String statsType = po.getStatsType();
      String statsGroupIndex = po.getStatsGroupIndex();
      String statsOrder = po.getStatsOrder();
      if (sortType == null || "".equals(sortType) || "null".equals(sortType))
        sortType = statsOrder; 
      String statsOrderNum = po.getStatsOrderNum();
      SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
      String statsBeginTime = format2.format(po.getStatsBeginTime());
      Calendar c = Calendar.getInstance();
      c.setTime(po.getStatsEndTime());
      c.add(5, 1);
      Date newDate = c.getTime();
      String statsEndTime = format2.format(newDate);
      String statsChart = po.getStatsChart();
      String statsTableId = po.getStatsTableId().toString();
      String statsIndexName = po.getStatsIndexName();
      list = msBD.getListByYourSQL("select t.tableId,t.tableName from com.js.oa.jsflow.po.TTablePO t where t.tableId=" + 
          statsTableId);
      String tableName = "";
      if (list != null && list.size() > 0) {
        Object[] obj = list.get(0);
        tableName = obj[1].toString();
      } 
      reMap.put("tableName", tableName);
      list = msBD.getListByYourSQL("select t.fieldId,t.fieldName,t.fieldDesName from com.js.oa.jsflow.po.TFieldPO t where t.fieldId=" + 
          statsIndex);
      String indexName = "";
      String fieldDesName = "";
      if (list != null && list.size() > 0) {
        Object[] obj = list.get(0);
        indexName = obj[1].toString();
        fieldDesName = obj[2].toString();
      } 
      reMap.put("fieldName", indexName);
      String viewScopeWhere = "";
      JsfStatisticsCommonUtil util = new JsfStatisticsCommonUtil();
      util.getZaiBanMonitorSearchParam(statsTableId, request);
      String paraSQL = "";
      WorkFlowDossierAction workFlowDossierAction = new WorkFlowDossierAction();
      if (request.getParameter("pager.offset") == null || request.getParameter("pager.offset").toUpperCase().equals("NULL")) {
        paraSQL = workFlowDossierAction.getQueryDataSQL(request, statsTableId, tableName);
      } else if (request.getSession(true).getAttribute("sql") == null || request.getSession(true).getAttribute("sql").toString().toUpperCase().equals("NULL") || request.getSession(true).getAttribute("sql").toString().trim().length() < 10) {
        paraSQL = workFlowDossierAction.getQueryDataSQL(request, statsTableId, tableName);
      } else {
        paraSQL = request.getSession(true).getAttribute("sql").toString();
      } 
      if (paraSQL != null && !"".equals(paraSQL))
        viewScopeWhere = paraSQL.substring(paraSQL.indexOf("where") + 6); 
      if (viewScopeWhere == null || "".equals(viewScopeWhere))
        viewScopeWhere = "1=1"; 
      String sql = "";
      if ("emp".equals(statsGroupIndex)) {
        sql = " select max(t0.empname)," + statsType + "(t0.i) r" + " from ( " + 
          "select DISTINCT e.empname,CASE WHEN " + indexName + " IS NULL THEN 0 ELSE " + indexName + " END AS i," + 
          tableName + "_date," + tableName + "_OWNER," + tableName + "_ORG from " + tableName;
        if (isSub.equals("1")) {
          sql = String.valueOf(sql) + " INNER JOIN JSF_WORK ON " + tableName + "_foreignkey=WORKRECORD_ID";
        } else {
          sql = String.valueOf(sql) + " INNER JOIN JSF_WORK ON " + tableName + "_ID=WORKRECORD_ID";
        } 
        sql = String.valueOf(sql) + " LEFT JOIN org_employee e ON " + tableName + "_OWNER=e.emp_id ";
        sql = String.valueOf(sql) + " where " + tableName + "_date >='" + statsBeginTime + 
          "' and " + tableName + "_date <'" + statsEndTime + "' and " + viewScopeWhere + 
          ") t0 group by t0.empname order by r " + statsOrder;
      } else if ("org".equals(statsGroupIndex)) {
        sql = " select max(t0.orgname)," + statsType + "(t0.i) r" + " from ( " + 
          "select DISTINCT o.orgname,CASE WHEN " + indexName + " IS NULL THEN 0 ELSE " + indexName + " END AS i," + 
          tableName + "_date," + tableName + "_OWNER," + tableName + "_ORG from " + tableName;
        if (isSub.equals("1")) {
          sql = String.valueOf(sql) + " INNER JOIN JSF_WORK ON " + tableName + "_foreignkey=WORKRECORD_ID";
        } else {
          sql = String.valueOf(sql) + " INNER JOIN JSF_WORK ON " + tableName + "_ID=WORKRECORD_ID";
        } 
        sql = String.valueOf(sql) + " LEFT JOIN org_organization_user ou ON " + tableName + "_OWNER=ou.emp_id " + 
          " LEFT JOIN  org_organization o ON ou.org_id=o.org_id " + 
          " where " + tableName + "_date >='" + statsBeginTime + 
          "' and " + tableName + "_date <'" + statsEndTime + "' and " + viewScopeWhere + ") t0   " + 
          "group by t0.orgname order by r " + statsOrder;
      } else {
        list = msBD.getListByYourSQL("select t.fieldId,t.fieldName from com.js.oa.jsflow.po.TFieldPO t where t.fieldId=" + 
            statsGroupIndex);
        String groupIndexName = "";
        if (list != null && list.size() > 0) {
          Object[] obj = list.get(0);
          groupIndexName = obj[1].toString();
        } 
        sql = " select max(t0." + groupIndexName + ")," + statsType + "(t0.i) r" + " from (" + 
          " select DISTINCT " + groupIndexName + ",CASE WHEN " + indexName + " IS NULL THEN 0 ELSE " + indexName + " END AS i," + 
          tableName + "_date," + tableName + "_OWNER," + tableName + "_ORG from " + tableName;
        if (isSub.equals("1")) {
          sql = String.valueOf(sql) + " INNER JOIN JSF_WORK ON " + tableName + "_foreignkey=WORKRECORD_ID";
        } else {
          sql = String.valueOf(sql) + " INNER JOIN JSF_WORK ON " + tableName + "_ID=WORKRECORD_ID";
        } 
        sql = String.valueOf(sql) + " where " + tableName + "_date >='" + statsBeginTime + 
          "' and " + tableName + "_date <='" + statsEndTime + "' and " + viewScopeWhere + 
          ") t0   group by t0." + groupIndexName + " order by r " + statsOrder;
        request.setAttribute("groupIndexName", groupIndexName);
        request.setAttribute("t_tableName", tableName);
      } 
      String qSql = "";
      if ("oracle".equals(databaseType)) {
        if (statsOrderNum == null || "".equals(statsOrderNum)) {
          qSql = "select j.*,rownum rn  from (select t.* from (" + sql + ")t ) j ORDER BY j.r " + sortType;
        } else {
          qSql = "select j.*,rownum rn  from (select t.* from (" + sql + ")t where  rownum<=" + statsOrderNum + ") j ORDER BY j.r " + sortType;
        } 
      } else if (statsOrderNum == null || "".equals(statsOrderNum)) {
        qSql = "select * from (select t.*,'rn' from (" + sql + ")t ) j ORDER BY j.r " + sortType;
      } else {
        qSql = "select * from (select t.*,'rn' from (" + sql + ")t  limit " + statsOrderNum + ") j ORDER BY j.r " + sortType;
      } 
      recordCount = db.queryStatisticsDataCount("select count(*) from (" + qSql + ") w");
      String reSql = "select * from (" + qSql + ") w";
      if ("oracle".equals(databaseType)) {
        reSql = String.valueOf(reSql) + " where w.rn >=" + ((currentPage - 1) * pageSize + 1) + 
          "and w.rn <=" + (currentPage * pageSize);
      } else {
        reSql = String.valueOf(reSql) + " limit " + ((currentPage - 1) * pageSize) + "," + pageSize;
      } 
      list = db.queryStatisticsData(reSql);
      reMap.put("recordCount", Integer.valueOf(recordCount));
      reMap.put("reList", list);
      request.setAttribute("valueType", String.valueOf(statsType) + "(" + fieldDesName + ")");
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action,statsId,rightType,moduleProcessId,processId,processZaiBanMonitorProcessId,processName,processType,isEdit,queryType,sortType");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return reMap;
  }
  
  public String queryStatisticsDataChart(HttpServletRequest request, int page_size, int w, int h) throws Exception {
    JsfStatisticsEJBBean db = new JsfStatisticsEJBBean();
    HttpSession session = request.getSession(true);
    MsManageBD msBD = new MsManageBD();
    String fileName = null;
    String statsId = request.getParameter("statsId");
    String rightType = request.getParameter("rightType");
    JsfStatisticsManage po = null;
    po = db.loadStatisticsManage(Long.valueOf(statsId));
    String statsChart = po.getStatsChart();
    int statsOrderNum = Integer.valueOf(po.getStatsOrderNum()).intValue();
    Map reMap = queryStatisticsData(request, page_size);
    List<Object[]> dataList = (List)reMap.get("reList");
    int recordCount = ((Integer)reMap.get("recordCount")).intValue();
    if (recordCount < statsOrderNum)
      statsOrderNum = recordCount; 
    w = statsOrderNum * w;
    h = statsOrderNum * h;
    if (w < 600)
      w = 600; 
    if (h < 450)
      h = 450; 
    if (w > 900)
      w = 900; 
    if (h > 600)
      h = 600; 
    request.setAttribute("w", Integer.valueOf(w));
    request.setAttribute("h", Integer.valueOf(h));
    JsfStatisticsChartUtil chartUtil = new JsfStatisticsChartUtil();
    if (dataList != null && dataList.size() > 0) {
      if ("1".equals(statsChart)) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (int i = 0; i < dataList.size(); i++) {
          Object[] obj = dataList.get(i);
          dataset.setValue((obj[0] == null) ? "" : obj[0].toString(), Double.parseDouble(String.valueOf(obj[1])));
        } 
        fileName = chartUtil.getChartBing(rightType, dataset, session, w, h);
      } 
      if ("2".equals(statsChart)) {
        double[][] data = new double[dataList.size()][1];
        String[] columnKeys = new String[dataList.size()];
        String valueAxisLabel = "个数（次）";
        for (int i = 0; i < dataList.size(); i++) {
          Object[] obj = dataList.get(i);
          data[i][0] = Double.valueOf((obj[1] != null) ? obj[1].toString() : "0").doubleValue();
          String tempName = (obj[0] != null) ? obj[0].toString() : "";
          for (int j = 0; j < columnKeys.length; j++) {
            if (tempName.equals(columnKeys[j])) {
              tempName = String.valueOf(tempName) + "(" + i + ")";
              break;
            } 
          } 
          columnKeys[i] = tempName;
        } 
        fileName = chartUtil.getChartZhu("", rightType, valueAxisLabel, data, new String[] { "" }, columnKeys, session, "heng", w, h);
      } 
      if ("3".equals(statsChart)) {
        double[][] data = new double[dataList.size()][1];
        String[] columnKeys = new String[dataList.size()];
        String valueAxisLabel = "个数（次）";
        for (int i = 0; i < dataList.size(); i++) {
          Object[] obj = dataList.get(i);
          data[i][0] = Double.valueOf((obj[1] != null) ? obj[1].toString() : "0").doubleValue();
          String tempName = (obj[0] != null) ? obj[0].toString() : "";
          for (int j = 0; j < columnKeys.length; j++) {
            if (tempName.equals(columnKeys[j])) {
              tempName = String.valueOf(tempName) + "(" + i + ")";
              break;
            } 
          } 
          columnKeys[i] = tempName;
        } 
        fileName = chartUtil.getChartZhu("", rightType, valueAxisLabel, data, new String[] { "" }, columnKeys, session, "shu", w, h);
      } 
      if ("4".equals(statsChart)) {
        DefaultCategoryDataset linedata = new DefaultCategoryDataset();
        for (int i = 0; i < dataList.size(); i++) {
          Object[] obj = dataList.get(i);
          linedata.addValue(Double.valueOf((obj[1] != null) ? obj[1].toString() : "0"), "个数（次）", (obj[0] != null) ? obj[0].toString() : "");
        } 
        fileName = chartUtil.getChartZhe(rightType, "流程状态", "", "", linedata, session, w, h);
      } 
    } 
    return fileName;
  }
}
