package com.js.oa.form;

import com.js.oa.eform.bean.CustomFormEJBBean;
import com.js.oa.eform.service.CustomFormBD;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.service.WorkFlowButtonBD;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.jsflow.util.ProcessSubmit;
import com.js.oa.jsflow.util.WorkflowCommon;
import com.js.oa.jsflow.vo.ActivityVO;
import com.js.oa.jsflow.vo.WorkLogVO;
import com.js.oa.jsflow.vo.WorkVO;
import com.js.oa.rws.oaprocessservice.ProcessService;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;
import com.js.util.http.HttpServletRequest;
import com.js.util.http.HttpSession;
import com.js.util.util.CharacterTool;
import com.js.util.util.DataSourceBase;
import com.js.util.util.StringSplit;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class ClientInfoFromWeb {
  public String createNewProcess(String xml) {
    return getClientInfo(xml);
  }
  
  public String toNextActivity(String xml) {
    Map map = readXML(xml);
    String processId = map.get("processId").toString();
    String tableId = map.get("tableId").toString();
    String recordId = map.get("recordId").toString();
    String nextActivityId = map.get("nextActivityId").toString();
    String currentStep = String.valueOf(nextActivityId) + "," + getActivityName(nextActivityId);
    String receiveName = (map.get("receiveName") == null) ? "" : map.get("receiveName").toString();
    String selUserId = "";
    if (!"".equals(receiveName)) {
      if (receiveName.indexOf(",") > 0) {
        String[] receiveNames = receiveName.split(",");
        for (int i = 0; i < receiveNames.length; i++) {
          if (!"".equals(receiveNames[i]))
            selUserId = String.valueOf(selUserId) + "$" + StaticParam.getEmpIdByAccount(receiveNames[i]) + "$"; 
        } 
      } else {
        selUserId = "$" + StaticParam.getEmpIdByAccount(receiveName) + "$";
      } 
    } else {
      HttpServletRequest request = new HttpServletRequest();
      request.setAttribute("processId", processId);
      request.setAttribute("tableId", tableId);
      request.setAttribute("recordId", recordId);
      request.setAttribute("nextActivityId", nextActivityId);
      setRequestParameter(request, map);
      Map activityMap = getNextActivityInfo(request);
      selUserId = (activityMap.get("user") == null) ? "" : activityMap.get("user").toString();
    } 
    String transactType = getActivityTransactType(nextActivityId);
    updateFormData(map);
    WorkFlowButtonBD bd = new WorkFlowButtonBD();
    String result = bd.setCurrentStep(processId, tableId, recordId, currentStep, selUserId, transactType);
    (new FlowWebservice()).executeWebservice(map, "node");
    return result;
  }
  
  public String completeProcess(String xml) {
    Map map = readXML(xml);
    String tableId = map.get("tableId").toString();
    String recordId = map.get("recordId").toString();
    updateFormData(map);
    (new FlowWebservice()).executeWebservice(map, "complete");
    return (new ProcessBD()).endFlowInstance(tableId, recordId);
  }
  
  public String getClientInfo(String info) {
    if (info.startsWith("<?xml version=\"1.0\" encoding=")) {
      System.out.println("webservice发起流程，参数信息：" + info);
      Map<String, String> map = readXML(info);
      String submitName = (map.get("submitName") == null) ? "" : map.get("submitName").toString();
      String receiveName = (map.get("receiveName") == null) ? "" : map.get("receiveName").toString();
      String processId = map.get("processId").toString();
      String pageId = map.get("tableId").toString();
      String tableName = map.get("tableName").toString();
      if ("rws".equals(SystemCommon.getCustomerName())) {
        ProcessService ps = new ProcessService();
        String empid = ps.getEmpId(submitName);
        map.put("userId", empid);
      } 
      String result = save(submitName, receiveName, pageId, processId, map);
      if (Long.valueOf(result).longValue() > 0L) {
        if ("hzzf".equals(SystemCommon.getCustomerName()))
          saveRelationObject(map, result); 
        (new FlowWebservice()).executeWebservice(map, "save");
      } 
      return result;
    } 
    return "传入的值不是xml格式！传入xml必须以<?xml version=\"1.0\" encoding=…… 开头。\n传入的值为：" + info;
  }
  
  public String saveData(String submitName, String receiveName, String tableId, String flowId, Map map) {
    return save(submitName, receiveName, tableId, flowId, map);
  }
  
  private String save(String submitName, String receiveName, String tableId, String flowId, Map map) {
    return save(submitName, receiveName, tableId, flowId, map, "");
  }
  
  public String save(String submitName, String receiveName, String tableId, String flowId, String recordId) {
    return save(submitName, receiveName, tableId, flowId, null, recordId);
  }
  
  private String save(String submitName, String receiveName, String tableId, String flowId, Map map, String recordId) {
    String result = "";
    WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    String flowName = getProcessIdByTableName(flowId);
    HttpServletRequest request = new HttpServletRequest();
    request.setAttribute("processId", flowId);
    setRequestParameter(request, map);
    Map map_activity = getFirstActivityInfo(request);
    String[] userNames = (String[])null;
    String userIds = receiveName;
    if (receiveName == null || "".equals(receiveName)) {
      userIds = map_activity.get("user").toString();
      userIds = userIds.replace("$$", ",");
      userIds = userIds.replace("$", "");
      userNames = getUserAccs(userIds);
    } else {
      userNames = new String[] { receiveName };
      userIds = "";
      if (receiveName.indexOf(",") > 0) {
        String[] receiveNames = receiveName.split(",");
        for (int i = 0; i < receiveNames.length; i++) {
          if (!"".equals(receiveNames[i]))
            userIds = String.valueOf(userIds) + StaticParam.getEmpIdByAccount(receiveNames[i]) + ","; 
        } 
        if (userIds.endsWith(","))
          userIds = userIds.substring(0, userIds.length() - 1); 
      } else {
        userIds = StaticParam.getEmpIdByAccount(receiveName);
      } 
    } 
    if ("".equals(submitName) && !"".equals(receiveName)) {
      submitName = receiveName;
    } else if ("".equals(submitName) && "".equals(receiveName)) {
      submitName = userNames[0];
    } 
    if (recordId.equals("")) {
      Map map1 = save_process(flowId, tableId, submitName, map, null);
      recordId = (String)map1.get("id");
    } 
    String remindFieldValue = "";
    if ("0".equals(recordId))
      return "-1"; 
    String remindField = getRemindField(flowId);
    if (remindField.indexOf("S") < 0 || recordId == null || recordId.trim().length() < 1) {
      remindFieldValue = "";
    } else {
      remindFieldValue = getRemindValue(remindField, recordId, tableId);
      if (remindFieldValue != null)
        remindFieldValue = CharacterTool.deleteEnter(remindFieldValue); 
    } 
    if (recordId != null && !"".equals(recordId) && Long.parseLong(recordId) > 0L) {
      String processType = "1";
      String processName = flowName;
      String processId = flowId;
      String moduleId = "1";
      moduleId = workFlowCommonBD.getModuleId(tableId);
      Map submitMap = getUserInformation(submitName);
      String userId = submitMap.get("empId").toString();
      String orgName = submitMap.get("orgName").toString();
      WorkVO workVO = new WorkVO();
      workVO.setWorkType(Integer.parseInt(processType));
      workVO.setFileType(processName);
      workVO.setRemindValue(remindFieldValue.toString());
      workVO.setSelfMainLinkFile("/jsoa/WorkFlowProcAction.do?qq=1");
      workVO.setToMainLinkFile("/jsoa/WorkFlowProcAction.do?qq=1");
      workVO.setSubmitEmployeeId(new Long(userId));
      workVO.setSubmitOrg(orgName);
      workVO.setProcessId(new Long(processId));
      workVO.setTableId(new Long(tableId));
      workVO.setRecordId(new Long(recordId));
      workVO.setCreatorCancelLink("");
      String isSubProcWork = "0";
      workVO.setIsSubProcWork(isSubProcWork);
      String pareProcActiId = "0";
      workVO.setPareProcActiId(pareProcActiId);
      String pareStepCount = "0";
      workVO.setPareStepCount(pareStepCount);
      String pareTableId = "0";
      workVO.setPareTableId(pareTableId);
      String pareRecordId = "0";
      workVO.setPareRecordId(pareRecordId);
      workVO.setPareProcNextActiId("0");
      workVO.setEmergence("0");
      String cancelHref = "JSMainWinOpen('/jsoa/jsflow/workflow_cancelReason.jsp?workStatus=1&workId=workIdValue&tableId=tableIdValue&processName=processNameValue&recordId=recordIdValue&processId=processIdValue&remindValue=remindValueValue&moduleId=1&remindField=null','','TOP=0,LEFT=0,scrollbars=no,resizable=no,width=480,height=250')";
      cancelHref = cancelHref.replaceAll("tableIdValue", tableId);
      cancelHref = cancelHref.replaceAll("processNameValue", processName);
      cancelHref = cancelHref.replaceAll("recordIdValue", recordId);
      cancelHref = cancelHref.replaceAll("processIdValue", processId);
      cancelHref = cancelHref.replaceAll("remindValueValue", remindFieldValue.toString());
      cancelHref = cancelHref.replaceAll("fileTitleValue", "");
      workVO.setCreatorCancelLink(cancelHref);
      String relProjectId = "";
      if (relProjectId != null && !"null".equals(relProjectId) && !"".equals(relProjectId)) {
        workVO.setRelProjectId(Long.valueOf(relProjectId));
      } else {
        workVO.setRelProjectId(Long.valueOf(-1L));
      } 
      String[] toUser = userIds.split(",");
      WorkFlowBD workFlowBD = new WorkFlowBD();
      workVO.setActivity(new Long(map_activity.get("id").toString()));
      workVO.setCurStep(map_activity.get("name").toString());
      workVO.setAllowStandFor(Integer.parseInt("0"));
      workVO.setPressType(Integer.parseInt("0"));
      workVO.setDeadLine("-1");
      workVO.setPressTime("");
      workVO.setUserType(Integer.parseInt("100"));
      workVO.setPressType(Integer.parseInt("0"));
      int pressType = Integer.parseInt("0");
      switch (pressType) {
        case 0:
          workVO.setDeadLine("-1");
          workVO.setPressTime("-1");
          break;
      } 
      workVO.setTransactType(map_activity.get("transactType").toString());
      String insertResult = workFlowBD.insertCurFieldStr(processId, tableId, (new StringBuilder(String.valueOf(recordId))).toString(), "");
      if (!"0".equals(insertResult))
        return "-1"; 
      String dealTips = "";
      String processDeadlineDate = "0";
      String[] para = { "", dealTips, processDeadlineDate };
      ProcessSubmit procSubmit = new ProcessSubmit();
      long submitSuccess = 0L;
      workVO.setSubmitPerson(submitMap.get("empName").toString());
      workVO.setDomainId("0");
      submitSuccess = procSubmit.newProcSubmit(workVO, toUser, moduleId, para);
      if (submitSuccess == 0L)
        return "-1"; 
      if (submitSuccess != 0L) {
        WorkLogVO workLogVO = new WorkLogVO();
        workLogVO.setSendUserId(userId);
        Map userMap = getUserInformation(submitName);
        workLogVO.setSendUserName(userMap.get("empName").toString());
        workLogVO.setSendAction("送" + map_activity.get("name").toString());
        if (!processType.equals("0"))
          workLogVO.setReceiveUserName(getSendToUserName(toUser)); 
        workLogVO.setProcessId(processId);
        workLogVO.setTableId(tableId);
        workLogVO.setRecordId(recordId);
        workLogVO.setDomainId("0");
        workFlowButtonBD.setDealWithLog(workLogVO);
      } 
    } else {
      recordId = "-1";
    } 
    return recordId;
  }
  
  private Map save_process(String processId, String tableId, String userName, Map map, String recordId) {
    String oldRecordId = recordId;
    String tableName = (new CustomFormBD()).getTable(tableId);
    StringBuffer remindFieldValue = new StringBuffer();
    String code = (new CustomFormBD()).getCode(tableId);
    if (code != null && !code.toUpperCase().equals("NULL") && code.trim().length() > 1)
      recordId = save_page(processId, tableName, userName, map); 
    Map<Object, Object> result = new HashMap<Object, Object>();
    result.put("id", recordId);
    result.put("remindFieldValue", remindFieldValue.toString().replaceAll("null", ""));
    if (recordId != null)
      if (oldRecordId != null && !"null".equals(oldRecordId) && !"".equals(oldRecordId))
        (new WorkFlowCommonBD()).deleteOldRecord(tableId, tableName, oldRecordId);  
    return result;
  }
  
  private String save_page(String processId, String tableName, String userName, Map map) {
    DbOpt dbopt = null;
    String infoId = "";
    try {
      dbopt = new DbOpt();
      infoId = saveData(processId, tableName, dbopt, userName, map);
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return infoId;
  }
  
  private String saveData(String processId, String tableName, DbOpt dbopt, String userName, Map map) {
    List<String[]> info = (List<String[]>)map.get("list");
    Map userMap = getUserInformation(userName);
    String userId = userMap.get("empId").toString();
    String orgName = userMap.get("orgName").toString();
    String orgId = userMap.get("orgId").toString();
    String empName = userMap.get("empName").toString();
    Boolean success = Boolean.TRUE;
    String infoId = "", infoIds = "";
    try {
      String sqlHead = "insert into " + tableName + " (";
      String sqlValue = " values(";
      if (DbOpt.dbtype.indexOf("oracle") >= 0) {
        infoId = dbopt.executeQueryToStr("Select HIBERNATE_SEQUENCE.Nextval From dual");
      } else {
        dbopt.executeUpdate("update JSDB.OA_SEQ set SEQ_SEQ = SEQ_SEQ+1");
        infoId = dbopt.executeQueryToStr("select SEQ_SEQ from JSDB.OA_SEQ");
      } 
      sqlHead = String.valueOf(sqlHead) + tableName + "_id," + tableName + "_owner," + tableName + "_org," + tableName + "_group,";
      sqlValue = String.valueOf(sqlValue) + infoId + "," + userId + "," + orgId + "," + "''" + ",";
      CustomFormEJBBean cBean = new CustomFormEJBBean();
      CustomFormBD cBD = new CustomFormBD();
      for (int i = 0; i < info.size(); i++) {
        String[] fieldInfo = new String[3];
        fieldInfo = info.get(i);
        if ("varchar2".equalsIgnoreCase(fieldInfo[1]) || "text".equalsIgnoreCase(fieldInfo[1]) || "varchar".equalsIgnoreCase(fieldInfo[1]) || 
          "clob".equalsIgnoreCase(fieldInfo[1])) {
          sqlHead = String.valueOf(sqlHead) + fieldInfo[0] + ",";
          sqlValue = String.valueOf(sqlValue) + "'" + fieldInfo[2] + "',";
        } else if ("number".equalsIgnoreCase(fieldInfo[1]) || "float".equalsIgnoreCase(fieldInfo[1]) || "int".equalsIgnoreCase(fieldInfo[1])) {
          sqlHead = String.valueOf(sqlHead) + fieldInfo[0] + ",";
          sqlValue = String.valueOf(sqlValue) + fieldInfo[2] + ",";
        } else if ("bianhao".equalsIgnoreCase(fieldInfo[1])) {
          sqlHead = String.valueOf(sqlHead) + fieldInfo[0] + ",";
          String[] fieldInfos = getFieldInfo(fieldInfo[0], tableName);
          String value = cBean.getAutoCode(fieldInfo[0], fieldInfos[0]);
          cBD.updateAutoCode(fieldInfo[0], value, fieldInfos[0]);
          sqlValue = String.valueOf(sqlValue) + "'" + value + "',";
        } else if ("empId".equalsIgnoreCase(fieldInfo[1])) {
          sqlHead = String.valueOf(sqlHead) + fieldInfo[0] + ",";
          sqlValue = String.valueOf(sqlValue) + "'" + userId + "',";
        } else if ("empName".equalsIgnoreCase(fieldInfo[1])) {
          sqlHead = String.valueOf(sqlHead) + fieldInfo[0] + ",";
          sqlValue = String.valueOf(sqlValue) + "'" + empName + "',";
        } else if ("orgId".equalsIgnoreCase(fieldInfo[1])) {
          sqlHead = String.valueOf(sqlHead) + fieldInfo[0] + ",";
          sqlValue = String.valueOf(sqlValue) + "'" + orgId + "',";
        } else if ("orgName".equalsIgnoreCase(fieldInfo[1])) {
          sqlHead = String.valueOf(sqlHead) + fieldInfo[0] + ",";
          sqlValue = String.valueOf(sqlValue) + "'" + orgName + "',";
        } else if ("shenPiUserName".equalsIgnoreCase(fieldInfo[1])) {
          sqlHead = String.valueOf(sqlHead) + fieldInfo[0] + ",";
          sqlValue = String.valueOf(sqlValue) + "'*id*" + userId + "',";
        } else if ("shenPiDate".equalsIgnoreCase(fieldInfo[1])) {
          SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String dateStr = format.format(new Date());
          sqlHead = String.valueOf(sqlHead) + fieldInfo[0] + ",";
          sqlValue = String.valueOf(sqlValue) + "'" + dateStr + "',";
        } else if ("shenPiOrgName".equalsIgnoreCase(fieldInfo[1])) {
          sqlHead = String.valueOf(sqlHead) + fieldInfo[0] + ",";
          sqlValue = String.valueOf(sqlValue) + "'" + orgName + "',";
        } else {
          sqlHead = String.valueOf(sqlHead) + fieldInfo[0] + ",";
          sqlValue = String.valueOf(sqlValue) + fieldInfo[1] + ",";
        } 
      } 
      sqlHead = sqlHead.substring(0, sqlHead.length() - 1);
      sqlValue = sqlValue.substring(0, sqlValue.length() - 1);
      sqlHead = String.valueOf(sqlHead) + ") ";
      sqlValue = String.valueOf(sqlValue) + ")";
      dbopt.executeUpdate(String.valueOf(sqlHead) + sqlValue);
      if (map.get("subTableNameList") != null) {
        List<String> subTableNameList = (List<String>)map.get("subTableNameList");
        for (int j = 0; j < subTableNameList.size(); j++) {
          String subTableName = ((String)subTableNameList.get(j)).substring(0, ((String)subTableNameList.get(j)).indexOf("*"));
          String sqlHeads = "insert into " + subTableName + " (";
          String sqlValues = " values(";
          if (DbOpt.dbtype.indexOf("oracle") >= 0) {
            infoIds = dbopt.executeQueryToStr("Select HIBERNATE_SEQUENCE.Nextval From dual");
          } else {
            dbopt.executeUpdate("update JSDB.OA_SEQ set SEQ_SEQ = SEQ_SEQ+1");
            infoIds = dbopt.executeQueryToStr("select SEQ_SEQ from JSDB.OA_SEQ");
          } 
          sqlHeads = String.valueOf(sqlHeads) + subTableName + "_id," + subTableName + "_owner," + 
            subTableName + "_org," + subTableName + "_group," + 
            subTableName + "_foreignkey,";
          sqlValues = String.valueOf(sqlValues) + infoIds + "," + userId + "," + orgId + "," + "''" + "," + infoId + ",";
          List<String[]> valueInfo = (List<String[]>)map.get(subTableNameList.get(j));
          for (int k = 0; k < valueInfo.size(); k++) {
            String[] fieldInfo = new String[3];
            fieldInfo = valueInfo.get(k);
            if ("varchar2".equalsIgnoreCase(fieldInfo[1]) || "text".equalsIgnoreCase(fieldInfo[1]) || "varchar".equalsIgnoreCase(fieldInfo[1]) || 
              "clob".equalsIgnoreCase(fieldInfo[1])) {
              sqlHeads = String.valueOf(sqlHeads) + fieldInfo[0] + ",";
              sqlValues = String.valueOf(sqlValues) + "'" + fieldInfo[2] + "',";
            } else if ("number".equalsIgnoreCase(fieldInfo[1]) || "float".equalsIgnoreCase(fieldInfo[1]) || "int".equalsIgnoreCase(fieldInfo[1])) {
              sqlHeads = String.valueOf(sqlHeads) + fieldInfo[0] + ",";
              sqlValues = String.valueOf(sqlValues) + fieldInfo[2] + ",";
            } else if ("bianhao".equalsIgnoreCase(fieldInfo[1])) {
              sqlHeads = String.valueOf(sqlHeads) + fieldInfo[0] + ",";
              String[] fieldInfos = getFieldInfo(fieldInfo[0], subTableName);
              String value = cBean.getAutoCode(fieldInfo[0], fieldInfos[0]);
              cBD.updateAutoCode(fieldInfo[0], value, fieldInfos[0]);
              sqlValues = String.valueOf(sqlValues) + "'" + value + "',";
            } else if ("empId".equalsIgnoreCase(fieldInfo[1])) {
              sqlHead = String.valueOf(sqlHead) + fieldInfo[0] + ",";
              sqlValue = String.valueOf(sqlValue) + "'" + userId + "',";
            } else if ("empName".equalsIgnoreCase(fieldInfo[1])) {
              sqlHead = String.valueOf(sqlHead) + fieldInfo[0] + ",";
              sqlValue = String.valueOf(sqlValue) + "'" + empName + "',";
            } else if ("orgId".equalsIgnoreCase(fieldInfo[1])) {
              sqlHead = String.valueOf(sqlHead) + fieldInfo[0] + ",";
              sqlValue = String.valueOf(sqlValue) + "'" + orgId + "',";
            } else if ("orgName".equalsIgnoreCase(fieldInfo[1])) {
              sqlHead = String.valueOf(sqlHead) + fieldInfo[0] + ",";
              sqlValue = String.valueOf(sqlValue) + "'" + orgName + "',";
            } else if ("shenPiUserName".equalsIgnoreCase(fieldInfo[1])) {
              sqlHead = String.valueOf(sqlHead) + fieldInfo[0] + ",";
              sqlValue = String.valueOf(sqlValue) + "'*id*" + userId + "',";
            } else if ("shenPiDate".equalsIgnoreCase(fieldInfo[1])) {
              SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
              String dateStr = format.format(new Date());
              sqlHead = String.valueOf(sqlHead) + fieldInfo[0] + ",";
              sqlValue = String.valueOf(sqlValue) + "'" + dateStr + "',";
            } else if ("shenPiOrgName".equalsIgnoreCase(fieldInfo[1])) {
              sqlHead = String.valueOf(sqlHead) + fieldInfo[0] + ",";
              sqlValue = String.valueOf(sqlValue) + "'" + orgName + "',";
            } else {
              sqlHead = String.valueOf(sqlHead) + fieldInfo[0] + ",";
              sqlValue = String.valueOf(sqlValue) + fieldInfo[1] + ",";
            } 
          } 
          sqlHeads = String.valueOf(sqlHeads.substring(0, sqlHeads.length() - 1)) + ")";
          sqlValues = String.valueOf(sqlValues.substring(0, sqlValues.length() - 1)) + ")";
          dbopt.executeUpdate(String.valueOf(sqlHeads) + sqlValues);
        } 
      } 
      dbopt.close();
    } catch (Exception e) {
      dbopt.close();
      success = Boolean.FALSE;
      e.printStackTrace();
    } finally {}
    if (success.equals(Boolean.TRUE))
      return infoId; 
    return "";
  }
  
  private String updateFormData(Map map) {
    List<String[]> info = (List<String[]>)map.get("list");
    String tableName = map.get("tableName").toString();
    String recordId = map.get("recordId").toString();
    Connection conn = null;
    try {
      String sqlHead = "update " + tableName + " set ";
      CustomFormEJBBean cBean = new CustomFormEJBBean();
      CustomFormBD cBD = new CustomFormBD();
      for (int i = 0; i < info.size(); i++) {
        String[] fieldInfo = new String[3];
        fieldInfo = info.get(i);
        if ("varchar2".equalsIgnoreCase(fieldInfo[1]) || "text".equalsIgnoreCase(fieldInfo[1]) || "varchar".equalsIgnoreCase(fieldInfo[1]) || 
          "clob".equalsIgnoreCase(fieldInfo[1])) {
          sqlHead = String.valueOf(sqlHead) + fieldInfo[0] + "='" + fieldInfo[2] + "',";
        } else if ("number".equalsIgnoreCase(fieldInfo[1]) || "float".equalsIgnoreCase(fieldInfo[1]) || "int".equalsIgnoreCase(fieldInfo[1])) {
          sqlHead = String.valueOf(sqlHead) + fieldInfo[0] + "='" + fieldInfo[2] + "',";
        } else if ("bianhao".equalsIgnoreCase(fieldInfo[1])) {
          String[] fieldInfos = getFieldInfo(fieldInfo[0], tableName);
          String value = cBean.getAutoCode(fieldInfo[0], fieldInfos[0]);
          cBD.updateAutoCode(fieldInfo[0], value, fieldInfos[0]);
          sqlHead = String.valueOf(sqlHead) + fieldInfo[0] + "='" + value + "',";
        } 
      } 
      sqlHead = sqlHead.substring(0, sqlHead.length() - 1);
      sqlHead = String.valueOf(sqlHead) + " where " + tableName + "_id=" + recordId;
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.executeUpdate(sqlHead);
      stmt.close();
      conn.close();
      String infoIds = "0";
      String databaseType = SystemCommon.getDatabaseType();
      if (map.get("subTableNameList") != null) {
        DbOpt dbopt = null;
        try {
          dbopt = new DbOpt();
          List<String> subTableNameList = (List<String>)map.get("subTableNameList");
          List<String> subTableList = new ArrayList<String>();
          for (int j = 0; j < subTableNameList.size(); j++) {
            String subTableName = ((String)subTableNameList.get(j)).substring(0, ((String)subTableNameList.get(j)).indexOf("*"));
            if (!subTableList.contains(subTableName)) {
              subTableList.add(subTableName);
              dbopt.executeUpdate("delete from " + subTableName + " where " + subTableName + "_foreignkey=" + recordId);
            } 
            String sqlHeads = "insert into " + subTableName + " (";
            String sqlValues = " values(";
            if (DbOpt.dbtype.indexOf("oracle") >= 0) {
              infoIds = dbopt.executeQueryToStr("Select HIBERNATE_SEQUENCE.Nextval From dual");
            } else {
              dbopt.executeUpdate("update JSDB.OA_SEQ set SEQ_SEQ = SEQ_SEQ+1");
              infoIds = dbopt.executeQueryToStr("select SEQ_SEQ from JSDB.OA_SEQ");
            } 
            sqlHeads = String.valueOf(sqlHeads) + subTableName + "_id," + subTableName + "_group," + 
              subTableName + "_foreignkey,";
            sqlValues = String.valueOf(sqlValues) + infoIds + ",''" + "," + recordId + ",";
            List<String[]> valueInfo = (List<String[]>)map.get(subTableNameList.get(j));
            for (int k = 0; k < valueInfo.size(); k++) {
              String[] fieldInfo = new String[3];
              fieldInfo = valueInfo.get(k);
              if ("varchar2".equalsIgnoreCase(fieldInfo[1]) || "text".equalsIgnoreCase(fieldInfo[1]) || "varchar".equalsIgnoreCase(fieldInfo[1]) || 
                "clob".equalsIgnoreCase(fieldInfo[1])) {
                sqlHeads = String.valueOf(sqlHeads) + fieldInfo[0] + ",";
                sqlValues = String.valueOf(sqlValues) + "'" + fieldInfo[2] + "',";
              } else if ("number".equalsIgnoreCase(fieldInfo[1]) || "float".equalsIgnoreCase(fieldInfo[1]) || "int".equalsIgnoreCase(fieldInfo[1])) {
                sqlHeads = String.valueOf(sqlHeads) + fieldInfo[0] + ",";
                sqlValues = String.valueOf(sqlValues) + fieldInfo[2] + ",";
              } else if ("bianhao".equalsIgnoreCase(fieldInfo[1])) {
                sqlHeads = String.valueOf(sqlHeads) + fieldInfo[0] + ",";
                String[] fieldInfos = getFieldInfo(fieldInfo[0], subTableName);
                String value = cBean.getAutoCode(fieldInfo[0], fieldInfos[0]);
                cBD.updateAutoCode(fieldInfo[0], value, fieldInfos[0]);
                sqlValues = String.valueOf(sqlValues) + "'" + value + "',";
              } 
            } 
            sqlHeads = String.valueOf(sqlHeads.substring(0, sqlHeads.length() - 1)) + ")";
            sqlValues = String.valueOf(sqlValues.substring(0, sqlValues.length() - 1)) + ")";
            System.out.println("============" + sqlHeads + sqlValues);
            dbopt.executeUpdate(String.valueOf(sqlHeads) + sqlValues);
            dbopt.close();
          } 
        } catch (Exception err) {
          if (dbopt != null)
            dbopt.close(); 
          err.printStackTrace();
        } 
      } 
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return null;
  }
  
  private Map getUserInformation(String userName) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    Connection conn = null;
    PreparedStatement pstmt = null;
    String sql = "select oe.emp_id,oo.orgidstring,oe.empname,eu.org_id,oo.orgname from org_employee oe,org_organization_user eu,org_organization oo where useraccounts=? and oe.emp_id=eu.emp_id and oo.org_id=eu.org_id";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, userName);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        map.put("empId", rs.getString(1));
        map.put("empOrgIdString", StringSplit.splitOrgIdString(rs.getString(2), "$", "_"));
        map.put("empName", rs.getString(3));
        map.put("orgId", rs.getString(4));
        map.put("orgName", rs.getString(5));
      } 
      pstmt.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
    } 
    return map;
  }
  
  private String getProcessIdByTableName(String flowId) {
    String flowName = "";
    Connection conn = null;
    PreparedStatement pstmt = null;
    String sql = "SELECT jsf.wf_workflowprocess_id,jsf.workflowprocessname FROM jsf_workflowprocess jsf WHERE jsf.wf_workflowprocess_id=?";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, flowId);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next())
        flowName = rs.getString(2); 
      pstmt.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
    } 
    return flowName;
  }
  
  private Map getActivityId(String processId) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    Connection conn = null;
    PreparedStatement pstmt = null;
    String sql = "select wf_activity_id,activityname,participanttype,participantuser,participantusername from jsf_activity where wf_activity_id=(select tr.transitionto from jsf_transition tr,(select wf_activity_id,activityname from jsf_activity  where  activitybeginend=1 and wf_workflowprocess_id=?) wf where tr.transitionfrom = wf.wf_activity_id)  ";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, processId);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        map.put("id", rs.getString(1));
        map.put("name", rs.getString(2));
        map.put("type", rs.getString(3));
        map.put("user", rs.getString(4));
        map.put("userName", rs.getString(5));
      } 
      pstmt.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException err) {
          err.printStackTrace();
        }  
    } 
    return map;
  }
  
  private Map getFirstActivityInfo(HttpServletRequest request) {
    String participantUserField, fieldShow;
    List<Object[]> list4, list3, list2, list1, leaderList;
    int i;
    Map<Object, Object> map = new HashMap<Object, Object>();
    ActivityVO activityVO = (new WorkflowCommon()).getFirstActivity(request);
    map.put("id", Long.valueOf(activityVO.getId()));
    map.put("name", activityVO.getName());
    map.put("type", String.valueOf(activityVO.getParticipantType()));
    map.put("transactType", activityVO.getTransactType());
    Map submitUserMap = getUserInformation(request.getParameter("submitName"));
    String submitUserId = submitUserMap.get("empId").toString();
    String submitOrgId = submitUserMap.get("orgId").toString();
    String activityUser = "";
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    int participantType = activityVO.getParticipantType();
    switch (participantType) {
      case 3:
        activityUser = getAcitivityUser(activityVO.getParticipantUser());
        break;
      case 2:
        activityUser = getAcitivityUser(activityVO.getParticipantUser());
        break;
      case 4:
        participantUserField = activityVO.getParticipantUserField();
        fieldShow = workFlowButtonBD.getFieldShowByFieldId(participantUserField);
        participantUserField = workFlowButtonBD.getFieldInfoByFieldId(participantUserField);
        break;
      case 7:
        list4 = (new WorkFlowBD()).getLeaderList(submitUserId);
        for (i = 0; i < list4.size(); i++) {
          Object[] tmp = list4.get(i);
          activityUser = String.valueOf(activityUser) + "$" + tmp[0] + "$";
        } 
        break;
      case 0:
        list3 = (new WorkFlowBD()).getLeaderList(submitUserId);
        for (i = 0; i < list3.size(); i++) {
          Object[] tmp = list3.get(i);
          activityUser = String.valueOf(activityUser) + "$" + tmp[0] + "$";
        } 
        break;
      case 5:
        activityUser = "$" + submitUserId + "$";
        break;
      case 6:
        list2 = (new WorkFlowBD()).getRoleUserIDAndName(activityVO.getPartRole(), submitUserId);
        for (i = 0; i < list2.size(); i++) {
          Object[] tmp = list2.get(i);
          activityUser = String.valueOf(activityUser) + "$" + tmp[0] + "$";
        } 
        break;
      case 14:
        list1 = workFlowButtonBD.getLeaderByDutyLevelAndOrg(submitUserId, activityVO.getPartRole());
        for (i = 0; i < list1.size(); i++) {
          Object[] tmp = list1.get(i);
          activityUser = String.valueOf(activityUser) + "$" + tmp[0] + "$";
        } 
        break;
      case 15:
        leaderList = (new WorkFlowBD()).getPositionUserIDAndName(activityVO.getPartRole(), submitUserId);
        for (i = 0; i < leaderList.size(); i++) {
          Object[] tmp = leaderList.get(i);
          activityUser = String.valueOf(activityUser) + "$" + tmp[0] + "$";
        } 
        break;
      case 8:
        activityUser = getUserIdsByRange(activityVO.getParticipantGivenOrg());
        break;
      case 9:
        activityUser = getUserIdsByCurrentOrg(submitOrgId);
        break;
      case 10:
        activityUser = getDepartLeader(submitUserId);
        break;
      case 11:
        activityUser = "$" + submitUserId + "$";
        break;
    } 
    map.put("user", activityUser);
    return map;
  }
  
  private Map getNextActivityInfo(HttpServletRequest request) {
    String participantUserField, fieldShow;
    List<Object[]> list4, list3, list2, list1, leaderList;
    int i;
    Map<Object, Object> map = new HashMap<Object, Object>();
    ActivityVO activityVO = (new WorkFlowBD()).getFirstProcActiVO(request.getAttribute("nextActivityId").toString()), activityVO = activityVO;
    map.put("id", Long.valueOf(activityVO.getId()));
    map.put("name", activityVO.getName());
    map.put("type", String.valueOf(activityVO.getParticipantType()));
    map.put("transactType", activityVO.getTransactType());
    String[] currentStep = getLastStepUser(request.getAttribute("processId").toString(), request.getAttribute("tableId").toString(), request.getAttribute("recordId").toString());
    String currentStepUser = currentStep[0];
    String lastCurStepUser = currentStep[1];
    String currentStepOrg = currentStep[2];
    String submitUserId = currentStep[3];
    String activityUser = "";
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    int participantType = activityVO.getParticipantType();
    switch (participantType) {
      case 3:
        activityUser = getAcitivityUser(activityVO.getParticipantUser());
        break;
      case 2:
        activityUser = getAcitivityUser(activityVO.getParticipantUser());
        break;
      case 4:
        participantUserField = activityVO.getParticipantUserField();
        fieldShow = workFlowButtonBD.getFieldShowByFieldId(participantUserField);
        participantUserField = workFlowButtonBD.getFieldInfoByFieldId(participantUserField);
        break;
      case 7:
        list4 = (new WorkFlowBD()).getLeaderList(lastCurStepUser);
        for (i = 0; i < list4.size(); i++) {
          Object[] tmp = list4.get(i);
          activityUser = String.valueOf(activityUser) + "$" + tmp[0] + "$";
        } 
        break;
      case 0:
        list3 = (new WorkFlowBD()).getLeaderList(submitUserId);
        for (i = 0; i < list3.size(); i++) {
          Object[] tmp = list3.get(i);
          activityUser = String.valueOf(activityUser) + "$" + tmp[0] + "$";
        } 
        break;
      case 5:
        activityUser = "$" + submitUserId + "$";
        break;
      case 6:
        list2 = (new WorkFlowBD()).getRoleUserIDAndName(activityVO.getPartRole(), lastCurStepUser);
        for (i = 0; i < list2.size(); i++) {
          Object[] tmp = list2.get(i);
          activityUser = String.valueOf(activityUser) + "$" + tmp[0] + "$";
        } 
        break;
      case 14:
        list1 = workFlowButtonBD.getLeaderByDutyLevelAndOrg(submitUserId, activityVO.getPartRole());
        for (i = 0; i < list1.size(); i++) {
          Object[] tmp = list1.get(i);
          activityUser = String.valueOf(activityUser) + "$" + tmp[0] + "$";
        } 
        break;
      case 15:
        leaderList = (new WorkFlowBD()).getPositionUserIDAndName(activityVO.getPartRole(), lastCurStepUser);
        for (i = 0; i < leaderList.size(); i++) {
          Object[] tmp = leaderList.get(i);
          activityUser = String.valueOf(activityUser) + "$" + tmp[0] + "$";
        } 
        break;
      case 8:
        activityUser = getUserIdsByRange(activityVO.getParticipantGivenOrg());
        break;
      case 9:
        activityUser = getUserIdsByCurrentOrg(currentStepOrg);
        break;
      case 10:
        activityUser = getDepartLeader(lastCurStepUser);
        break;
      case 11:
        activityUser = currentStepUser;
        break;
    } 
    map.put("user", activityUser);
    return map;
  }
  
  private String getAcitivityUser(List<String[]> userList) {
    StringBuffer user = new StringBuffer();
    for (int i = 0; i < userList.size(); i++) {
      String[] temp = userList.get(i);
      user.append("$").append(temp[0]).append("$");
    } 
    return user.toString();
  }
  
  private String getUserIdsByRange(String range) {
    StringBuffer user = new StringBuffer();
    Connection conn = null;
    try {
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("oracle") >= 0) {
        sql = "select org_id from org_organization where ? like '%*'||org_id||'*%'";
      } else {
        sql = "select org_id from org_organization where ? like concat('%*',org_id,'*%')";
      } 
      conn = (new DataSourceBase()).getDataSource().getConnection();
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, range);
      List<String> orgList = new ArrayList<String>();
      ResultSet rs = pstmt.executeQuery();
      while (rs.next())
        orgList.add(rs.getString(1)); 
      rs.close();
      String sql = "select oe.emp_id from org_employee oe left join org_organization_user oou on oe.emp_id=oou.emp_id left join org_organization oo on oou.org_id=oo.org_id where oe.userisactive=1 and oe.userisdeleted=0 ";
      if (orgList.size() > 0) {
        sql = String.valueOf(sql) + " and(1>2";
        for (String orgId : orgList)
          sql = String.valueOf(sql) + " or oo.orgidstring like '%$" + orgId + "$%'"; 
        sql = String.valueOf(sql) + ")";
      } 
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next())
        user.append("$").append(rs.getString(1)).append("$"); 
      rs.close();
      if (databaseType.indexOf("oracle") >= 0) {
        sql = "select oug.emp_id from org_group og left join org_user_group oug on og.group_id=oug.group_id left join org_employee oe on oug.emp_id=oe.emp_id where ? like '%@'||group_id||'@%' and oe.userisactive=1 and oe.userisdeleted=0";
      } else {
        sql = "select oug.emp_id from org_group og left join org_user_group oug on og.group_id=oug.group_id left join org_employee oe on oug.emp_id=oe.emp_id where ? like concat('%@',group_id,'@%') and oe.userisactive=1 and oe.userisdeleted=0";
      } 
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, range);
      List<String> groupList = new ArrayList<String>();
      rs = pstmt.executeQuery();
      while (rs.next())
        user.append("$").append(rs.getString(1)).append("$"); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return user.toString();
  }
  
  private String getUserIdsByCurrentOrg(String orgId) {
    StringBuffer user = new StringBuffer();
    Connection conn = null;
    try {
      String sql = "select oe.emp_id from org_employee oe left join org_organization_user oou on oe.emp_id=oou.emp_id left join org_organization oo on oou.org_id=oo.org_id where oe.userisactive=1 and oe.userisdeleted=0 and oo.orgidstring like ?";
      conn = (new DataSourceBase()).getDataSource().getConnection();
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, "%$" + orgId + "$%");
      ResultSet rs = pstmt.executeQuery();
      while (rs.next())
        user.append("$").append(rs.getString(1)).append("$"); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return user.toString();
  }
  
  private String getDepartLeader(String userId) {
    String leader = "";
    Connection conn = null;
    try {
      String sql = "select oo.orgmanagerempid, oe.empleaderid from org_employee oe left join org_organization_user oou on oe.emp_id=oou.emp_id left join org_organization oo on oou.org_id=oo.org_id where oe.emp_id=?";
      conn = (new DataSourceBase()).getDataSource().getConnection();
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, userId);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        leader = rs.getString(1);
        if (leader == null || "".equals(leader))
          leader = rs.getString(2); 
        if (leader == null || "".equals(leader))
          leader = ""; 
      } 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return leader;
  }
  
  private String[] getLastStepUser(String processId, String tableId, String recordId) {
    String[] user = { "", "", "", "" };
    Connection conn = null;
    try {
      String sql = "select workstepcount,wf_submitemployee_id,wf_work_id,wf_curemployee_id from jsf_work where workprocess_id=? and worktable_id=? and workrecord_id=? and worklistcontrol=1  order by workstepcount desc, wf_work_id desc";
      conn = (new DataSourceBase()).getDataSource().getConnection();
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, processId);
      pstmt.setString(2, tableId);
      pstmt.setString(3, recordId);
      ResultSet rs = pstmt.executeQuery();
      int count = -1;
      String userTemp = "";
      while (rs.next()) {
        int stepCount = rs.getInt(1);
        if (count == -1) {
          user[3] = rs.getString(2);
          user[1] = rs.getString(4);
        } 
        if (stepCount >= count) {
          count = stepCount;
          userTemp = String.valueOf(userTemp) + "$" + rs.getString(4) + "$";
          continue;
        } 
        break;
      } 
      rs.close();
      user[0] = userTemp;
      sql = "select org_id from org_organization_user where emp_id=?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, user[1]);
      rs = pstmt.executeQuery();
      if (rs.next())
        user[2] = rs.getString(1); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return user;
  }
  
  private String getRemindValue(String remindField, String recordId, String formId) {
    String remindValue = "";
    if (remindField == null || remindField.length() < 1 || 
      remindField.toUpperCase().equals("null") || recordId == null || 
      recordId.length() < 1 || 
      recordId.toUpperCase().equals("null") || formId == null || 
      formId.length() < 1 || formId.toUpperCase().equals("null"))
      return ""; 
    remindField = "S" + remindField + "S";
    String[] remindFieldArr = remindField.split("SS");
    CustomFormBD formBD = new CustomFormBD();
    for (int i = 0; i < remindFieldArr.length; i++) {
      String temp = formBD.getRemindValue(remindFieldArr[i], 
          recordId, formId);
      remindValue = String.valueOf(remindValue) + ((temp == null || temp.length() < 1 || temp
        .toUpperCase().equals("NULL")) ? "" : temp);
    } 
    return remindValue;
  }
  
  private String getUserByGroup(String groupIdStr) {
    String userStr = "";
    DataSourceBase dsb = new DataSourceBase();
    DataSource ds = dsb.getDataSource();
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(
          "SELECT DISTINCT EMP_ID FROM ORG_USER_GROUP WHERE GROUP_ID IN (" + 
          groupIdStr + ")");
      while (rs.next())
        userStr = String.valueOf(userStr) + rs.getString(1) + ","; 
      if (userStr.endsWith(","))
        userStr = userStr.substring(0, userStr.length() - 1); 
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        conn.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      } 
    } 
    return userStr;
  }
  
  private String getUserByOrg(String orgIdStr) {
    String orgIds = "";
    if (orgIdStr == null || orgIdStr.length() < 1)
      return orgIds; 
    String[] orgIdArr = orgIdStr.split(",");
    DbOpt dbopt = null;
    ResultSet rs = null;
    try {
      dbopt = new DbOpt();
      for (int i = 0; i < orgIdArr.length; i++) {
        String orgCode = dbopt.executeQueryToStr(
            "select ORGIDSTRING from ORG_ORGANIZATION where ORG_ID=" + 
            orgIdArr[i]);
        rs = dbopt.executeQuery("select EMP_ID from ORG_ORGANIZATION_USER where ORG_ID in (select ORG_ID from ORG_ORGANIZATION where ORGIDSTRING like '%" + 
            orgCode + "%')");
        if (rs != null) {
          while (rs.next()) {
            Object empId = rs.getObject(1);
            if (empId != null && 
              orgIds.indexOf(empId.toString()) < 0)
              orgIds = String.valueOf(orgIds) + empId.toString() + ","; 
          } 
          rs.close();
        } 
      } 
      dbopt.close();
    } catch (Exception e) {
      e.printStackTrace();
      try {
        dbopt.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      } 
    } 
    return orgIds;
  }
  
  private Map readXML(String info) {
    List<String[]> fieldList = (List)new ArrayList<String>();
    Map<Object, Object> map = new HashMap<Object, Object>();
    StringReader read = new StringReader(info);
    InputSource source = new InputSource(read);
    SAXBuilder sb = new SAXBuilder();
    sb.setExpandEntities(false);
    try {
      Document doc = sb.build(source);
      Element root = doc.getRootElement();
      Element node = root.getChild("Process");
      map.put("processId", Long.valueOf(node.getAttributeValue("processId")).toString());
      if (node.getAttributeValue("recordId") != null)
        map.put("recordId", Long.valueOf(node.getAttributeValue("recordId")).toString()); 
      node = root.getChild("Activity");
      if (node != null)
        map.put("nextActivityId", Long.valueOf(node.getAttributeValue("nextActivityId")).toString()); 
      node = root.getChild("UserName");
      if (node != null) {
        if (node.getAttributeValue("submitName") != null)
          map.put("submitName", node.getAttributeValue("submitName")); 
        if (node.getAttributeValue("receiveName") != null)
          map.put("receiveName", node.getAttributeValue("receiveName")); 
      } 
      node = root.getChild("Data").getChild("Table");
      map.put("tableId", Long.valueOf(node.getAttributeValue("tableId")).toString());
      map.put("tableName", node.getAttributeValue("tableName"));
      Element column = node.getChild("Column");
      List<Element> list = column.getChildren();
      String fieldName = "", fieldType = "", fieldValue = "";
      for (int i = 0; i < list.size(); i++) {
        String[] fieldInfo = new String[3];
        Element field = list.get(i);
        fieldInfo[0] = field.getAttributeValue("name");
        fieldInfo[1] = field.getAttributeValue("type");
        fieldInfo[2] = field.getText();
        System.out.println("----------name:" + fieldInfo[0] + "type:" + fieldInfo[1] + "Text:" + fieldInfo[2]);
        fieldList.add(fieldInfo);
      } 
      map.put("list", fieldList);
      List<Element> subNode = root.getChild("Data").getChildren("SubTable");
      List<String> subTableNameList = new ArrayList<String>();
      if (subNode != null) {
        for (int j = 0; j < subNode.size(); j++) {
          List<String[]> subFieldInfoList = (List)new ArrayList<String>();
          Element subTable = subNode.get(j);
          String subTableName = "";
          subTableName = subTable.getAttributeValue("tableName");
          String subTableType = subTable.getAttributeValue("type");
          subTableNameList.add(String.valueOf(subTableName) + "*" + subTableType);
          Element subColumn = subTable.getChild("Column");
          List<Element> subList = subColumn.getChildren();
          for (int k = 0; k < subList.size(); k++) {
            Element field = subList.get(k);
            String[] subFieldInfo = new String[3];
            subFieldInfo[0] = field.getAttributeValue("name");
            subFieldInfo[1] = field.getAttributeValue("type");
            subFieldInfo[2] = field.getText();
            subFieldInfoList.add(subFieldInfo);
          } 
          map.put(String.valueOf(subTableName) + "*" + subTableType, subFieldInfoList);
        } 
        map.put("subTableNameList", subTableNameList);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return map;
  }
  
  private String[] getFieldInfo(String fieldName, String tableName) {
    String sql = "SELECT f.field_id,f.field_desname,f.field_name,f.field_table,f.field_value FROM tfield f,ttable t WHERE f.field_table=t.table_id AND t.table_name=? AND f.field_name=?";
    String[] field = new String[5];
    DataSourceBase base = new DataSourceBase();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, tableName);
      pstmt.setString(2, fieldName);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        field[0] = (rs.getString(1) == null) ? "" : rs.getString(1);
        field[1] = (rs.getString(2) == null) ? "" : rs.getString(2);
        field[2] = (rs.getString(3) == null) ? "" : rs.getString(3);
        field[3] = (rs.getString(4) == null) ? "" : rs.getString(4);
        field[4] = (rs.getString(5) == null) ? "" : rs.getString(5);
      } 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return field;
  }
  
  private String[] getUserAccs(String ids) {
    List<String> accs = new ArrayList();
    String[] accsStrings = (String[])null;
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String sql = "SELECT useraccounts FROM org_employee WHERE emp_id IN (" + ids + ")";
    try {
      base.begin();
      rs = base.executeQuery(sql);
      while (rs.next())
        accs.add(rs.getString(1)); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    accsStrings = new String[accs.size()];
    for (int i = 0; i < accs.size(); i++)
      accsStrings[i] = accs.get(i).toString(); 
    return accsStrings;
  }
  
  private String getSendToUserName(String[] users) {
    String name = "";
    String accs = "-212";
    for (int i = 0; i < users.length; i++)
      accs = String.valueOf(accs) + "," + users[i]; 
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String sql = "SELECT empName FROM org_employee WHERE emp_id IN (" + accs + ")";
    try {
      base.begin();
      rs = base.executeQuery(sql);
      while (rs.next())
        name = String.valueOf(name) + "," + rs.getString(1); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return name.substring(1);
  }
  
  private String getActivityName(String activityId) {
    String name = null;
    Connection conn = null;
    PreparedStatement pstmt = null;
    String sql = "select activityname from jsf_activity where wf_activity_id=?";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, activityId);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next())
        name = rs.getString(1); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return name.substring(1);
  }
  
  private String getActivityTransactType(String activityId) {
    String result = null;
    Connection conn = null;
    PreparedStatement pstmt = null;
    String sql = "SELECT transacttype FROM jsf_activity WHERE wf_activity_id=?";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, activityId);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next())
        result = rs.getString(1); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return result;
  }
  
  private void setRequestParameter(HttpServletRequest request, Map map) {
    HttpSession jssession = new HttpSession();
    List<String[]> fieldList = (List)map.get("list");
    for (int i = 0; i < fieldList.size(); i++) {
      String[] fieldInfo = fieldList.get(i);
      request.setParameter(fieldInfo[0], fieldInfo[2]);
    } 
    if (map.get("submitName") != null)
      request.setParameter("submitName", map.get("submitName").toString()); 
    if (map.get("userId") != null)
      jssession.setAttribute("userId", map.get("userId")); 
    request.setSession(jssession);
  }
  
  private void saveRelationObject(Map map, String infoId) {
    DbOpt dbopt = null;
    List<String[]> fieldList = (List)new ArrayList<String>();
    fieldList = (List<String[]>)map.get("list");
    String[] fieldInfo = new String[3];
    String textValue = "";
    for (int i = 0; i < fieldList.size(); i++) {
      fieldInfo = fieldList.get(i);
      if ("jst_3171_f4969".equals(fieldInfo[0]))
        textValue = fieldInfo[2]; 
    } 
    String tableId = map.get("tableId").toString();
    String[] relationObjects = new String[1];
    relationObjects[0] = "jst_3171_f4969";
    String[] relationValues = new String[1], relationTitles = new String[1];
    String relationSubId = "0";
    String databaseType = DbOpt.dbtype;
    try {
      dbopt = new DbOpt();
      for (int j = 0; j < relationObjects.length; j++) {
        String fieldName = relationObjects[j];
        String relationType = "jsflow";
        relationValues[0] = "1518656;2819635";
        relationTitles[0] = textValue;
        for (int k = 0; relationValues != null && k < relationValues.length; k++) {
          String sql, temp = relationValues[k];
          relationSubId = temp.substring(0, temp.indexOf(";"));
          String relationInfoId = temp.substring(temp.indexOf(";") + 1);
          if (databaseType.indexOf("oracle") >= 0) {
            sql = "insert into oa_relationdata(dataid,moduletype,modulesubid,infoid,relationobjecttype,relationinfoname,relationsubid,relationinfoid,relationinfohref,domain_id,fieldName) values(hibernate_sequence.nextval,'jsflow'," + 
              tableId + "," + infoId + ",'" + relationType + "','" + relationTitles[k] + "'," + relationSubId + "," + relationInfoId + ",'',0,'" + fieldName + "')";
          } else {
            sql = "insert into oa_relationdata(moduletype,modulesubid,infoid,relationobjecttype,relationinfoname,relationsubid,relationinfoid,relationinfohref,domain_id,fieldName) values('jsflow'," + 
              tableId + "," + infoId + ",'" + relationType + "','" + relationTitles[k] + "'," + relationSubId + "," + relationInfoId + ",'',0,'" + fieldName + "')";
          } 
          dbopt.executeUpdate(sql);
        } 
      } 
    } catch (Exception err) {
      err.printStackTrace();
    } 
  }
  
  private String getRemindField(String processId) {
    String remindField = "";
    Connection conn = null;
    PreparedStatement pstmt = null;
    String sql = "select remindfield from jsf_workflowprocess where wf_workflowprocess_id=?";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, processId);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next())
        remindField = rs.getString(1); 
      if (remindField == null)
        remindField = ""; 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return remindField;
  }
}
