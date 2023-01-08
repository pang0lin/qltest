package com.js.oa.form;

import cn.flyingsoft.oais.service.webservice.OACallArchiveServiceStub;
import com.js.oa.eform.bean.CustomFormEJBHome;
import com.js.oa.eform.service.CustomFormBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.service.WorkFlowButtonBD;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.jsflow.util.ProcessSubmit;
import com.js.oa.jsflow.vo.WorkLogVO;
import com.js.oa.jsflow.vo.WorkVO;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import com.js.util.util.ConversionString;
import com.js.util.util.DataSourceBase;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import com.js.util.util.StringSplit;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import sun.misc.BASE64Decoder;

public class WorkFlowArchives {
  private static String useArchives;
  
  public static boolean isUse() {
    if (useArchives == null) {
      String path = System.getProperty("user.dir");
      String filePath = String.valueOf(path) + "/jsconfig/sysconfig.xml";
      boolean useLDAP = false;
      SAXBuilder builder = new SAXBuilder();
      Document doc = null;
      try {
        doc = builder.build(filePath);
        Element WorkUse = doc.getRootElement().getChild("WorkflowData");
        if (WorkUse != null)
          useArchives = WorkUse.getAttribute("use").getValue(); 
      } catch (JDOMException e1) {
        e1.printStackTrace();
      } catch (IOException e1) {
        e1.printStackTrace();
      } 
      if (useArchives == null)
        useArchives = "0"; 
    } 
    if ("1".equals(useArchives))
      return true; 
    return false;
  }
  
  public boolean isLendProcess(String processId) {
    Connection conn = null;
    Statement stmt = null;
    try {
      String sql = "select id from jsf_archives  where workflow_id = " + Long.parseLong(processId) + " ";
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next())
        return true; 
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
    if (conn != null)
      try {
        conn.close();
      } catch (SQLException err) {
        err.printStackTrace();
      }  
    return false;
  }
  
  public static String getTableName() {
    String path = System.getProperty("user.dir");
    String filePath = String.valueOf(path) + "/jsconfig/sysconfig.xml";
    String tableName = "";
    SAXBuilder builder = new SAXBuilder();
    Document doc = null;
    try {
      doc = builder.build(filePath);
    } catch (JDOMException e1) {
      e1.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    } 
    Element WorkUse = doc.getRootElement().getChild("WorkflowData");
    if (WorkUse != null) {
      Element table = WorkUse.getChild("tableNameWork");
      tableName = table.getAttribute("name").getValue();
      if (tableName == null)
        tableName = ""; 
    } 
    return tableName;
  }
  
  public static String getArchivesTableName() {
    String path = System.getProperty("user.dir");
    String filePath = String.valueOf(path) + "/jsconfig/sysconfig.xml";
    String tableName = "";
    SAXBuilder builder = new SAXBuilder();
    Document doc = null;
    try {
      doc = builder.build(filePath);
    } catch (JDOMException e1) {
      e1.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    } 
    Element WorkUse = doc.getRootElement().getChild("WorkflowData");
    if (WorkUse != null) {
      Element table = WorkUse.getChild("tableNameArchives");
      tableName = table.getAttribute("name").getValue();
      if (tableName == null)
        tableName = ""; 
    } 
    return tableName;
  }
  
  public static String getArchivesPath() {
    String path = System.getProperty("user.dir");
    String filePath = String.valueOf(path) + "/jsconfig/sysconfig.xml";
    String archivesPath = "";
    SAXBuilder builder = new SAXBuilder();
    Document doc = null;
    try {
      doc = builder.build(filePath);
    } catch (JDOMException e1) {
      e1.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    } 
    Element WorkUse = doc.getRootElement().getChild("WorkflowData");
    if (WorkUse != null) {
      Element element = WorkUse.getChild("archivesPath");
      archivesPath = element.getAttribute("value").getValue();
      if (archivesPath == null)
        archivesPath = ""; 
    } 
    return archivesPath;
  }
  
  public static String getArchivesMethod() {
    String path = System.getProperty("user.dir");
    String filePath = String.valueOf(path) + "/jsconfig/sysconfig.xml";
    String archivesMethod = "";
    SAXBuilder builder = new SAXBuilder();
    Document doc = null;
    try {
      doc = builder.build(filePath);
    } catch (JDOMException e1) {
      e1.printStackTrace();
    } catch (IOException e1) {
      e1.printStackTrace();
    } 
    Element WorkUse = doc.getRootElement().getChild("WorkflowData");
    if (WorkUse != null) {
      Element element = WorkUse.getChild("archivesMethod");
      archivesMethod = element.getAttribute("value").getValue();
      if (archivesMethod == null)
        archivesMethod = ""; 
    } 
    return archivesMethod;
  }
  
  public List getTableColumn(String tableName) {
    List<String> list = new ArrayList();
    Connection conn = null;
    PreparedStatement pstmt = null;
    String databaseType = SystemCommon.getDatabaseType();
    String sql = "";
    if (databaseType.indexOf("mysql") >= 0) {
      sql = "show columns from " + tableName + " ";
    } else {
      sql = "select column_name from user_tab_columns  where table_name = ?";
    } 
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, tableName);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next())
        list.add(rs.getString(1)); 
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
    return list;
  }
  
  public Map getArchiveLinkMap(String tableName) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    Connection conn = null;
    PreparedStatement pstmt = null;
    String sql = "select oa_column_name,archives_column_name from jsf_archives_link where archives_table_name=?";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, tableName);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next())
        map.put(rs.getString(1), rs.getString(2)); 
      rs.close();
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
  
  public String saveAll(String userName, String archivesFlowId, String strs) {
    String retValue = "", temp = "";
    String[] rowData = strs.split("卐");
    for (int i = 0; i < rowData.length; i++) {
      try {
        temp = save(userName, archivesFlowId, rowData[i]);
      } catch (Exception ex) {
        temp = "-1";
      } 
      if (temp == null || "".equals(temp))
        temp = "-1"; 
      if (retValue.equals("")) {
        retValue = String.valueOf(retValue) + temp;
      } else {
        retValue = String.valueOf(retValue) + "," + temp;
      } 
    } 
    return retValue;
  }
  
  public String save(String userName, String archivesFlowId, String str) {
    String result = "-1";
    String path = "";
    String fileName = "";
    String fileNum = "";
    String url = "";
    String fileRead = "";
    String fileReadDay = "";
    String fileReadTime = "";
    String fileDownload = "";
    String fileDownloadDay = "";
    String fileDownloadTime = "";
    String fileLend = "";
    StringBuffer buffer = new StringBuffer(str);
    String[] rowData = buffer.toString().split("卐");
    String[] colData = (String[])null;
    byte b;
    int i;
    String[] arrayOfString1;
    for (i = (arrayOfString1 = rowData).length, b = 0; b < i; ) {
      String record = arrayOfString1[b];
      colData = record.split("@#");
      for (int j = 0; j < colData.length; j++) {
        if (j == 0)
          path = colData[j]; 
        if (j == 1)
          fileName = colData[j]; 
        if (j == 2)
          fileNum = colData[j]; 
        if (j == 3)
          url = colData[j]; 
        if (j == 4) {
          String[] fileInfo = colData[j].split(";");
          String[] fileReadStr = fileInfo[0].split("_");
          String[] fileDownStr = fileInfo[1].split("_");
          String[] fileLendStr = fileInfo[2].split("_");
          fileRead = fileReadStr[0].equals("FileRead") ? "1" : "0";
          fileReadDay = fileReadStr[1];
          fileReadTime = fileReadStr[2];
          fileDownload = fileDownStr[0].equals("FileDownLoad") ? "1" : "0";
          fileDownloadDay = fileDownStr[1];
          fileDownloadTime = fileDownStr[2];
          fileLend = fileLendStr[0].equals("FileLend") ? "1" : "0";
        } 
      } 
      b++;
    } 
    String tableName = getTableName();
    WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    Map map = getProcessIdByTableName(tableName);
    Map map_activity = getActivityId(map.get("processId").toString());
    Map map1 = save_process(map.get("processId").toString(), getTableIdByName(tableName), userName, path, fileName, fileNum, url, fileRead, fileReadDay, fileDownload, fileDownloadDay, fileLend, str, archivesFlowId, fileReadTime, fileDownloadTime, null);
    String recordId = (String)map1.get("id");
    String remindFieldValue = "";
    if ("0".equals(recordId))
      return "-1"; 
    String remindField = "";
    if (remindField.indexOf("S") < 0 || recordId == null || recordId.trim().length() < 1) {
      remindFieldValue = "";
    } else {
      remindFieldValue = getRemindValue(remindField, recordId, getTableIdByName(tableName));
    } 
    if (recordId != null && !"".equals(recordId) && Long.parseLong(recordId) > 0L) {
      String operId, operProcOrg, processType = "1";
      BASE64Decoder base64 = new BASE64Decoder();
      String processName = map.get("workflowProcessName").toString();
      String processId = map.get("processId").toString();
      String tableId = getTableIdByName(tableName);
      String moduleId = "1";
      moduleId = workFlowCommonBD.getModuleId(tableId);
      Map userMap = getUserInformation(userName);
      String userId = userMap.get("empId").toString();
      String orgName = userMap.get("orgName").toString();
      WorkVO workVO = new WorkVO();
      workVO.setWorkType(Integer.parseInt(processType));
      workVO.setFileType(processName);
      workVO.setRemindValue(remindFieldValue.toString());
      workVO.setSelfMainLinkFile("/jsoa/WorkFlowProcAction.do?flowpara=1");
      workVO.setToMainLinkFile("/jsoa/WorkFlowProcAction.do?flowpara=1");
      workVO.setSubmitPerson(userMap.get("empName").toString());
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
      String[] toUser = { "" };
      WorkFlowBD workFlowBD = new WorkFlowBD();
      workVO.setActivity(new Long(map_activity.get("id").toString()));
      workVO.setCurStep(map_activity.get("name").toString());
      workVO.setAllowStandFor(Integer.parseInt("0"));
      workVO.setPressType(Integer.parseInt("0"));
      workVO.setDeadLine("-1");
      workVO.setPressTime("");
      workVO.setUserType(Integer.parseInt("100"));
      int userType = Integer.parseInt("100");
      if (userType == 10)
        userType = 100; 
      switch (userType) {
        case 100:
          operId = "";
          operId = "$" + userMap.get("empId") + "$";
          if (operId != null && !operId.equals("")) {
            ConversionString con = new ConversionString(operId);
            String userIdStr = String.valueOf(con.getUserIdString()) + ",";
            if (con.getGroupIdString() != null && !"".equals(con.getGroupIdString()))
              userIdStr = String.valueOf(userIdStr) + getUserByGroup(con.getGroupIdString()) + ","; 
            if (con.getOrgIdString() != null && !"".equals(con.getOrgIdString()))
              userIdStr = String.valueOf(userIdStr) + getUserByOrg(con.getOrgIdString()); 
            DbOpt dbopt = null;
            try {
              dbopt = new DbOpt();
              userIdStr = userIdStr.replaceAll(",,", ",").replaceAll(",,", ",");
              if (userIdStr.startsWith(","))
                userIdStr = userIdStr.substring(1, userIdStr.length() - 1); 
              if (userIdStr.endsWith(","))
                userIdStr = userIdStr.substring(0, userIdStr.length() - 1); 
              if (userIdStr.length() > 0) {
                toUser = dbopt.executeQueryToStrArr1(
                    "select EMP_ID from ORG_EMPLOYEE where USERISACTIVE=1 and USERISDELETED<>1 and EMP_ID in (" + 
                    userIdStr + ")");
              } else {
                toUser = new String[0];
              } 
              dbopt.close();
            } catch (Exception e) {
              try {
                dbopt.close();
              } catch (SQLException ex1) {
                ex1.printStackTrace();
              } 
              toUser = new String[0];
              e.printStackTrace();
            } 
          } 
          break;
        case 10:
          operProcOrg = userMap.get("empId").toString();
          toUser = workFlowBD.getLeaderListByOrgId(operProcOrg);
          break;
      } 
      workVO.setPressType(Integer.parseInt("0"));
      int pressType = Integer.parseInt("0");
      switch (pressType) {
        case 0:
          workVO.setDeadLine("-1");
          workVO.setPressTime("-1");
          break;
      } 
      String nextTransactType = "1";
      workVO.setTransactType(nextTransactType);
      String insertResult = workFlowBD.insertCurFieldStr(Long.valueOf(processId).toString(), Long.valueOf(tableId).toString(), (new StringBuilder(String.valueOf(Long.valueOf(recordId).toString()))).toString(), "");
      if (!"0".equals(insertResult))
        return "-1"; 
      String dealTips = "";
      String processDeadlineDate = "0";
      String[] para = { "", dealTips, processDeadlineDate };
      ProcessSubmit procSubmit = new ProcessSubmit();
      long submitSuccess = 0L;
      submitSuccess = procSubmit.newProcSubmit(workVO, toUser, moduleId, para);
      if (submitSuccess == 0L)
        return "-1"; 
      if (submitSuccess != 0L) {
        WorkLogVO workLogVO = new WorkLogVO();
        workLogVO.setSendUserId(userId);
        workLogVO.setSendUserName(userMap.get("empName").toString());
        workLogVO.setSendAction("送" + map_activity.get("name").toString());
        if (!processType.equals("0"))
          workLogVO.setReceiveUserName(map_activity.get("name").toString()); 
        workLogVO.setProcessId(processId);
        workLogVO.setTableId(tableId);
        workLogVO.setRecordId(recordId);
        workLogVO.setDomainId("0");
        workFlowButtonBD.setDealWithLog(workLogVO);
      } 
      if ("1".equals("1")) {
        WorkFlowButtonBD bd = new WorkFlowButtonBD();
        String str1 = bd.getSaveFirstWorkUrl(processId, processName, tableId, recordId);
      } 
      result = "0";
    } 
    return result;
  }
  
  public boolean isZhiDing(String processId) {
    Connection conn = null;
    Statement stmt = null;
    boolean result = false;
    String sql = "select participanttype from jsf_activity where wf_activity_id=" + Long.parseLong(processId);
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next())
        if ("3".equals(rs.getString(1))) {
          result = true;
        } else {
          result = false;
        }  
      stmt.close();
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
    return result;
  }
  
  public String getBanLiRen(String processId) {
    Connection conn = null;
    Statement stmt = null;
    String userIds = "";
    try {
      String sql = "select participantuser from jsf_activity where wf_activity_id=" + Long.parseLong(processId);
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next())
        userIds = rs.getString(1); 
      stmt.close();
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
    return userIds;
  }
  
  public Map getProcessIdByTableName(String tableName) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    Connection conn = null;
    PreparedStatement pstmt = null;
    String sql = "select jsf.wf_workflowprocess_id,jsf.workflowprocessname from jsf_workflowprocess jsf,tarea t   where  t.area_table=? and jsf.accessdatabaseid=t.page_id";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, tableName);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        map.put("processId", rs.getString(1));
        map.put("workflowProcessName", rs.getString(2));
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
  
  public String getIdByTableName(String tableName) {
    String id = "";
    Connection conn = null;
    Statement stmt = null;
    String sql = "select t.page_id from tarea t  where  t.area_table = '" + tableName + "' ";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next())
        id = rs.getString(1); 
      stmt.close();
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
    return id;
  }
  
  public String getTableIdByName(String tableName) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    String sql = "select t.page_id from tarea t  where  t.area_table = ? ";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, tableName);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next())
        return rs.getString(1); 
      rs.close();
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
    if (conn != null)
      try {
        conn.close();
      } catch (SQLException err) {
        err.printStackTrace();
      }  
    return "none";
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
  
  public Map getUserInformation(String userName) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    Connection conn = null;
    PreparedStatement pstmt = null;
    String sql = "select oe.emp_id,oo.orgidstring,oe.empname,eu.org_id,oo.orgname from org_employee oe,org_organization_user eu,org_organization oo where useraccounts=? and oe.emp_id=eu.emp_id and oo.org_id=eu.org_id";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, userName);
      ResultSet rs = pstmt.executeQuery(sql);
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
  
  public String getUserByGroup(String groupIdStr) {
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
  
  public String getUserByOrg(String orgIdStr) {
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
    } finally {}
    return orgIds;
  }
  
  public String getColumnByColumn(String column) {
    Connection conn = null;
    Statement stmt = null;
    String sql = "select oa_column_name from jsf_archives_link  where  archives_column_name='" + column + "' ";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next())
        return rs.getString(1); 
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
    if (conn != null)
      try {
        conn.close();
      } catch (SQLException err) {
        err.printStackTrace();
      }  
    return "none";
  }
  
  public Map getActivityId(String processId) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    Connection conn = null;
    Statement stmt = null;
    String sql = "select wf_activity_id,activityname from jsf_activity where wf_activity_id=(select tr.transitionto from jsf_transition tr,(select wf_activity_id,activityname from jsf_activity  where  activitybeginend=1 and wf_workflowprocess_id=" + 
      
      processId + ") wf where tr.transitionfrom = wf.wf_activity_id)  ";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        map.put("id", rs.getString(1));
        map.put("name", rs.getString(2));
      } 
      stmt.close();
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
  
  public Map save_process(String processId, String tableId, String userName, String path, String fileName, String fileNum, String url, String fileRead, String fileReadDay, String fileDownload, String fileDownloadDay, String fileLend, String str, String archivesFlowId, String fileReadTime, String fileDownloadTime, String recordId) {
    String oldRecordId = recordId;
    String remindField = null;
    WorkFlowBD workFlowFormBD = new WorkFlowBD();
    String tableName = (new CustomFormBD()).getTable(tableId);
    StringBuffer fieldBuffer = new StringBuffer();
    StringBuffer valueBuffer = new StringBuffer();
    List fieldList = new ArrayList();
    List valueList = new ArrayList();
    ArrayList childTableList = new ArrayList();
    ArrayList childFieldList = new ArrayList();
    ArrayList childFieldValueList = new ArrayList();
    StringBuffer remindFieldValue = new StringBuffer();
    String[] remindFiledArr = (remindField == null) ? new String[0] : remindField.split("S");
    String fieldNameStr = "";
    String tmpValue = "";
    String code = (new CustomFormBD()).getCode(tableId);
    if (code != null && !code.toUpperCase().equals("NULL") && code.trim().length() > 1)
      recordId = save_page(processId, tableName, userName, path, fileName, fileNum, url, 
          fileRead, fileReadDay, fileDownload, fileDownloadDay, str, fileLend, archivesFlowId, fileReadTime, fileDownloadTime); 
    Map<Object, Object> result = new HashMap<Object, Object>();
    result.put("id", recordId);
    result.put("remindFieldValue", remindFieldValue.toString().replaceAll("null", ""));
    if (recordId != null)
      if (oldRecordId != null && !"null".equals(oldRecordId) && !"".equals(oldRecordId))
        (new WorkFlowCommonBD()).deleteOldRecord(tableId, tableName, oldRecordId);  
    return result;
  }
  
  public String save_page(String processId, String tableName, String userName, String path, String fileName, String fileNum, String url, String fileRead, String fileReadDay, String fileDownload, String fileDownloadDay, String str, String fileLend, String archivesFlowId, String fileReadTime, String fileDownloadTime) {
    DbOpt dbopt = null;
    String infoId = "";
    try {
      dbopt = new DbOpt();
      infoId = saveData(processId, tableName, dbopt, userName, path, fileName, fileNum, url, 
          fileRead, fileReadDay, fileDownload, fileDownloadDay, fileLend, str, archivesFlowId, fileReadTime, fileDownloadTime);
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
  
  public String saveData(String processId, String tableName, DbOpt dbopt, String userName, String path, String fileName, String fileNum, String url, String fileRead, String fileReadDay, String fileDownload, String fileDownloadDay, String fileLend, String str, String archivesFlowId, String fileReadTime, String fileDownloadTime) {
    Map userMap = getUserInformation(userName);
    String userId = userMap.get("empId").toString();
    String orgName = userMap.get("orgName").toString();
    String orgId = userMap.get("orgId").toString();
    String empName = userMap.get("empName").toString();
    Boolean success = Boolean.TRUE;
    String infoId = "";
    String pageId = getIdByTableName(tableName);
    if (pageId == null || pageId.length() < 1)
      return ""; 
    List<String> upList = new ArrayList();
    String[][] fieldStr = getFieldInfo(pageId);
    String[][] tableIdName = getTableIDAndName(pageId);
    String tableId = tableIdName[0][0];
    String table = tableIdName[0][1];
    try {
      if (fieldStr != null && fieldStr.length >= 1)
        if (table != null && table.trim().length() >= 1) {
          String sqlHead = "insert into " + table + "(";
          String sqlValue = " values(";
          String computeSqlHead = " update " + table + " set ";
          if (DbOpt.dbtype.indexOf("oracle") >= 0) {
            infoId = dbopt.executeQueryToStr("Select HIBERNATE_SEQUENCE.Nextval From dual");
          } else {
            dbopt.executeUpdate("update JSDB.OA_SEQ set SEQ_SEQ = SEQ_SEQ+1");
            infoId = dbopt.executeQueryToStr("select SEQ_SEQ from JSDB.OA_SEQ");
          } 
          Map<Object, Object> archivesMap = new HashMap<Object, Object>();
          archivesMap.put("userName", userName);
          archivesMap.put("processId", infoId);
          for (int i = 0; i < fieldStr.length; i++) {
            String fieldType = "varchar";
            if (DbOpt.dbtype.equals("oracle")) {
              if (i == 0) {
                sqlHead = String.valueOf(sqlHead) + table + "_id," + table + "_owner," + table + "_org," + table + "_group,";
                sqlValue = String.valueOf(sqlValue) + infoId + "," + userId + 
                  "," + orgId + "," + 
                  "''" + ",";
              } 
              if (fieldType != null && (fieldType.equals("varchar") || fieldType.equals("jsdate") || fieldType.equals("jstime"))) {
                String oa_column_name = getColumnByColumn(fieldStr[i][0].toUpperCase());
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                String val = "";
                if (oa_column_name.equals("FILE_NAME")) {
                  val = fileName.replaceAll("'", "\\\\''");
                  archivesMap.put("fileName", val);
                } 
                if (oa_column_name.equals("FILE_NUM")) {
                  val = fileNum.replaceAll("'", "\\\\''");
                  archivesMap.put("fileNum", val);
                } 
                if (oa_column_name.equals("URL")) {
                  val = url.replaceAll("'", "\\\\''");
                  String val2 = val;
                  val = ("<a href='" + val + "' target='_blank'>档案链接</a>").replaceAll("'", "\\\\''");
                  archivesMap.put("url", val2);
                } 
                if (oa_column_name.equals("FILE_READ")) {
                  val = fileRead.replaceAll("'", "\\\\''");
                  archivesMap.put("fileRead", val);
                } 
                if (oa_column_name.equals("FILE_READ_DAY")) {
                  val = fileReadDay.replaceAll("'", "\\\\''");
                  archivesMap.put("fileReadDay", val);
                } 
                if (oa_column_name.equals("FILE_DOWNLOAD")) {
                  val = fileDownload.replaceAll("'", "\\\\''");
                  archivesMap.put("fileDownload", val);
                } 
                if (oa_column_name.equals("FILE_DOWNLOAD_DAY")) {
                  val = fileDownloadDay.replaceAll("'", "\\\\''");
                  archivesMap.put("fileDownloadDay", val);
                } 
                if (oa_column_name.equals("FILE_LEND")) {
                  val = fileLend.replaceAll("'", "\\\\''");
                  archivesMap.put("fileLend", val);
                } 
                if (oa_column_name.equals("FILE_READ_TIME"))
                  val = fileReadTime.replaceAll("'", "\\\\''"); 
                if (oa_column_name.equals("FILE_DOWNLOAD_TIME"))
                  val = fileDownloadTime.replaceAll("'", "\\\\''"); 
                if (val == null || val.length() < 2000) {
                  sqlValue = String.valueOf(sqlValue) + "'" + val + "',";
                } else {
                  sqlValue = String.valueOf(sqlValue) + "'  '";
                  for (int m = 0; m * 2000 < val.length(); m++) {
                    int end = ((m + 1) * 2000 < val.length()) ? ((m + 1) * 2000) : val.length();
                    upList.add("update " + table + " set " + fieldStr[i][0] + "=" + fieldStr[i][0] + "||'" + val.substring(m * 2000, end).replaceAll("'", "\\\\''").replaceAll("&", "'||'&'||'") + "' where " + table + "_id=" + infoId);
                  } 
                } 
              } 
            } else if (DbOpt.dbtype.equals("mysql")) {
              if (i == 0) {
                sqlHead = String.valueOf(sqlHead) + table + "_id," + table + "_owner," + table + "_org," + table + "_group,";
                sqlValue = String.valueOf(sqlValue) + infoId + "," + userId + 
                  "," + orgId + "," + 
                  "''" + ",";
              } 
              if (fieldType != null && (fieldType.equals("varchar") || fieldType.equals("jsdate") || fieldType.equals("jstime"))) {
                String oa_column_name = getColumnByColumn(fieldStr[i][0].toUpperCase());
                sqlHead = String.valueOf(sqlHead) + fieldStr[i][0] + ",";
                String val = "";
                if (oa_column_name.equals("FILE_NAME")) {
                  val = fileName.replaceAll("'", "\\\\''");
                  archivesMap.put("fileName", val);
                } 
                if (oa_column_name.equals("FILE_NUM")) {
                  val = fileNum.replaceAll("'", "\\\\''");
                  archivesMap.put("fileNum", val);
                } 
                if (oa_column_name.equals("URL")) {
                  val = url.replaceAll("'", "\\\\''");
                  String val2 = val;
                  val = ("<a href='" + val + "' target='_blank'>档案链接</a>").replaceAll("'", "\\\\''");
                  archivesMap.put("url", val2);
                } 
                if (oa_column_name.equals("FILE_READ")) {
                  val = fileRead.replaceAll("'", "\\\\''");
                  archivesMap.put("fileRead", val);
                } 
                if (oa_column_name.equals("FILE_READ_DAY")) {
                  val = fileReadDay.replaceAll("'", "\\\\''");
                  archivesMap.put("fileReadDay", val);
                } 
                if (oa_column_name.equals("FILE_DOWNLOAD")) {
                  val = fileDownload.replaceAll("'", "\\\\''");
                  archivesMap.put("fileDownload", val);
                } 
                if (oa_column_name.equals("FILE_DOWNLOAD_DAY")) {
                  val = fileDownloadDay.replaceAll("'", "\\\\''");
                  archivesMap.put("fileDownloadDay", val);
                } 
                if (oa_column_name.equals("FILE_LEND")) {
                  val = fileLend.replaceAll("'", "\\\\''");
                  archivesMap.put("fileLend", val);
                } 
                if (oa_column_name.equals("FILE_READ_TIME"))
                  val = fileReadTime.replaceAll("'", "\\\\''"); 
                if (oa_column_name.equals("FILE_DOWNLOAD_TIME"))
                  val = fileDownloadTime.replaceAll("'", "\\\\''"); 
                if (val == null || val.length() < 2000) {
                  sqlValue = String.valueOf(sqlValue) + "'" + val + "',";
                } else {
                  sqlValue = String.valueOf(sqlValue) + "'  '";
                  for (int m = 0; m * 2000 < val.length(); m++) {
                    int end = ((m + 1) * 2000 < val.length()) ? ((m + 1) * 2000) : val.length();
                    upList.add("update " + table + " set " + fieldStr[i][0] + "=" + fieldStr[i][0] + "||'" + val.substring(m * 2000, end).replaceAll("'", "\\\\''").replaceAll("&", "'||'&'||'") + "' where " + table + "_id=" + infoId);
                  } 
                } 
              } 
            } 
          } 
          int result = 0;
          if (infoId != null) {
            result = dbopt.executeUpdate(String.valueOf(sqlHead.substring(0, sqlHead.length() - 1)) + ") " + sqlValue.substring(0, sqlValue.length() - 1) + ")");
            if (!computeSqlHead.trim().endsWith("set"))
              result = dbopt.executeUpdate(String.valueOf(computeSqlHead.substring(0, computeSqlHead.length() - 1)) + " where " + table + "_id=" + infoId); 
            String[][] updateArr = dbopt.executeQueryToStrArr2("select field_name,field_type from tfield a,ttable b where a.field_table=b.table_id and b.table_name='" + 
                table + "' and a.field_show=406");
            int j;
            for (j = 0; j < updateArr.length; j++) {
              String userAccount = userName;
              upList.add("update " + table + " set " + updateArr[j][0] + "='" + userAccount + "' where " + table + "_id=" + infoId);
            } 
            updateArr = dbopt.executeQueryToStrArr2("select field_name,field_type from tfield a,ttable b where a.field_table=b.table_id and b.table_name='" + 
                table + "' and a.field_show=201");
            for (j = 0; j < updateArr.length; j++) {
              String userAccount = userId;
              upList.add("update " + table + " set " + updateArr[j][0] + "='" + userAccount + "' where " + table + "_id=" + infoId);
            } 
            updateArr = dbopt.executeQueryToStrArr2("select field_name,field_type from tfield a,ttable b where a.field_table=b.table_id and b.table_name='" + 
                table + "' and a.field_show=202");
            for (j = 0; j < updateArr.length; j++) {
              String userAccount = empName;
              upList.add("update " + table + " set " + updateArr[j][0] + "='" + userAccount + "' where " + table + "_id=" + infoId);
            } 
            updateArr = dbopt.executeQueryToStrArr2("select field_name,field_type from tfield a,ttable b where a.field_table=b.table_id and b.table_name='" + 
                table + "' and a.field_show=207");
            for (j = 0; j < updateArr.length; j++) {
              String userAccount = orgName;
              upList.add("update " + table + " set " + updateArr[j][0] + "='" + userAccount + "' where " + table + "_id=" + infoId);
            } 
            updateArr = dbopt.executeQueryToStrArr2("select field_name,field_type from tfield a,ttable b where a.field_table=b.table_id and b.table_name='" + 
                table + "' and a.field_show=213");
            for (j = 0; j < updateArr.length; j++) {
              String userAccount = orgId;
              upList.add("update " + table + " set " + updateArr[j][0] + "='" + userAccount + "' where " + table + "_id=" + infoId);
            } 
            for (int n = 0; n < upList.size(); n++)
              dbopt.executeUpdate(upList.get(n).toString()); 
          } 
          if (result < 1) {
            success = Boolean.FALSE;
          } else {
            if (DbOpt.dbtype.indexOf("mysql") >= 0)
              dbopt.executeUpdate("update " + table + " set " + table + "_date=sysdate() where " + table + "_id=" + 
                  infoId); 
            insertArchives((String)archivesMap.get("userName"), (String)archivesMap.get("processId"), 
                path, (String)archivesMap.get("fileName"), (String)archivesMap.get("fileNum"), 
                (String)archivesMap.get("url"), (String)archivesMap.get("fileRead"), (String)archivesMap.get("fileReadDay"), 
                (String)archivesMap.get("fileDownload"), (String)archivesMap.get("fileDownloadDay"), (String)archivesMap.get("fileLend"), str, archivesFlowId, fileReadTime, fileDownloadTime);
          } 
        }  
    } catch (Exception e) {
      success = Boolean.FALSE;
      e.printStackTrace();
    } finally {}
    if (success.equals(Boolean.TRUE))
      return infoId; 
    return "";
  }
  
  public String[] getForeignTable(String pageId) {
    DbOpt dbopt = null;
    String[] result = (String[])null;
    try {
      dbopt = new DbOpt();
      result = dbopt.executeQueryToStrArr1("select distinct b.AREA_NAME from tarea b where b.area_name<>'form1' and b.PAGE_ID=" + 
          pageId);
      dbopt.close();
    } catch (Exception e) {
      e.printStackTrace();
      try {
        dbopt.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      } 
    } finally {}
    return result;
  }
  
  public String[][] getForeignFieldInfo(String pageId, String table) {
    DbOpt dbopt = null;
    String[][] result = (String[][])null;
    try {
      dbopt = new DbOpt();
      if (table != null) {
        result = dbopt.executeQueryToStrArr2("select distinct a.ELT_TABLE,c.field_only,c.field_show,b.area_name from TELT a ,tarea b,tfield c,ttable d where b.area_name=d.table_name and c.field_table=d.table_id and b.area_name='" + 
            table + "' and b.AREA_id=a.AREA_id and a.ELT_TABLE=c.field_name " + 
            " and a.PAGE_ID=" + pageId, 4);
      } else {
        result = dbopt.executeQueryToStrArr2("select distinct a.ELT_TABLE,c.field_only,c.field_show,b.area_name from TELT a ,tarea b,tfield c,ttable d where b.area_name=d.table_name and c.field_table=d.table_id and b.area_name<>'form1' and b.AREA_id=a.AREA_id and a.ELT_TABLE=c.field_name  and a.PAGE_ID=" + 
            
            pageId, 4);
      } 
      dbopt.close();
    } catch (Exception e) {
      e.printStackTrace();
      try {
        dbopt.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      } 
    } finally {}
    return result;
  }
  
  public String[][] getFieldInfo(String pageId) {
    String[][] result = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(pageId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      result = (String[][])ejbProxy.invoke("getFieldInfo", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  public String[][] getTableIDAndName(String pageId) {
    String[][] result = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(pageId, String.class);
      EJBProxy ejbProxy = new EJBProxy("CustomFormEJB", 
          "CustomFormEJBLocal", CustomFormEJBHome.class);
      result = (String[][])ejbProxy.invoke("getTableIDAndName", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return result;
  }
  
  private void insertArchives(String userName, String processId, String path, String fileName, String fileNum, String url, String fileRead, String fileReadDay, String fileDownload, String fileDownloadDay, String fileLend, String str, String archivesFlowId, String fileReadTime, String fileDownloadTime) {
    Connection conn = null;
    Statement stmt = null;
    String databaseType = SystemCommon.getDatabaseType();
    String sql = "";
    if (databaseType.indexOf("mysql") >= 0) {
      sql = "insert into jsf_archives(user_name,workflow_id,path,lendstr,file_name,file_num,url,file_read,file_read_day,file_download,file_download_day,file_lend,archives_flow_id,file_read_time,file_download_time) values('" + 
        
        userName + "'," + processId + ",'" + path + "','" + str + "','" + fileName + "','" + fileNum + "','" + url + "','" + fileRead + "'," + 
        "'" + fileReadDay + "','" + fileDownload + "','" + fileDownloadDay + "','" + fileLend + "','" + Long.parseLong(archivesFlowId) + "','" + fileReadTime + "','" + fileDownloadTime + "')";
    } else if (databaseType.indexOf("oracle") >= 0) {
      sql = "insert into jsf_archives(id,user_name,workflow_id,path,lendstr,file_name,file_num,url,file_read,file_read_day,file_download,file_download_day,file_lend,archives_flow_id,file_read_time,file_download_time) values(hibernate_sequence.nextval,'" + 
        
        userName + "'," + processId + ",'" + path + "','" + str + "','" + fileName + "','" + fileNum + "','" + url + "','" + fileRead + "'," + 
        "'" + fileReadDay + "','" + fileDownload + "','" + fileDownloadDay + "','" + fileLend + "','" + Long.parseLong(archivesFlowId) + "','" + fileReadTime + "','" + fileDownloadTime + "')";
    } 
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      boolean status = conn.getAutoCommit();
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
      conn.commit();
      conn.setAutoCommit(status);
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
  }
  
  public void updateArchives(String processId, String fileName, String fileNum, String fileRead, String fileReadDay, String fileDownload, String fileDownloadDay, String fileLend, String fileReadTime, String fileDownloadTime) {
    Connection conn = null;
    Statement stmt = null;
    String databaseType = SystemCommon.getDatabaseType();
    String sql = "update jsf_archives set file_name='" + fileName + "',file_num='" + fileNum + "',file_read='" + fileRead + "',file_read_day='" + fileReadDay + "'," + 
      "file_download='" + fileDownload + "',file_download_day='" + fileDownloadDay + "',file_lend='" + fileLend + "',file_read_time='" + fileReadTime + "',file_download_time='" + fileDownloadTime + "'";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      boolean status = conn.getAutoCommit();
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      stmt.close();
      conn.commit();
      conn.setAutoCommit(status);
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
  }
  
  public String getArchivesId(String recordId) {
    Connection conn = null;
    Statement stmt = null;
    String sql = "select archives_flow_id from jsf_archives where workflow_id=" + 
      recordId;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      boolean status = conn.getAutoCommit();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next())
        return rs.getString(1); 
      rs.close();
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
    if (conn != null)
      try {
        conn.close();
      } catch (SQLException err) {
        err.printStackTrace();
      }  
    return "";
  }
  
  public String getUserName(String recordId) {
    Connection conn = null;
    Statement stmt = null;
    String sql = "select user_name from jsf_archives where workflow_id=" + 
      recordId;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      boolean status = conn.getAutoCommit();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next())
        return rs.getString(1); 
      rs.close();
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
    if (conn != null)
      try {
        conn.close();
      } catch (SQLException err) {
        err.printStackTrace();
      }  
    return "";
  }
  
  public Map getColumn(String tableName) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    Connection conn = null;
    Statement stmt = null;
    String sql = "select oa_column_name,archives_column_name from jsf_archives_link where table_name='" + 
      tableName + "'";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      boolean status = conn.getAutoCommit();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next())
        map.put(rs.getString(1), rs.getString(2)); 
      rs.close();
      stmt.close();
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
  
  public String finishApply(HttpServletRequest request) {
    String retValue = "0";
    if (isUse() && isLendProcess(request.getParameter("recordId"))) {
      String id = getArchivesId(request.getParameter("recordId"));
      String userName = getUserName(request.getParameter("recordId"));
      Map map = getColumn(getTableName());
      String fileName = request.getParameter(map.get("FILE_NAME").toString().toLowerCase());
      String fileNum = request.getParameter(map.get("FILE_NUM").toString().toLowerCase());
      String fileRead = request.getParameter(map.get("FILE_READ").toString().toLowerCase());
      String fileRead1 = fileRead;
      StringBuffer buffer = new StringBuffer();
      String fileReadDay = request.getParameter(map.get("FILE_READ_DAY").toString().toLowerCase());
      String fileReadTime = request.getParameter(map.get("FILE_READ_TIME").toString().toLowerCase());
      String fileDownload = request.getParameter(map.get("FILE_DOWNLOAD").toString().toLowerCase());
      String fileDownload1 = fileDownload;
      String fileDownloadDay = request.getParameter(map.get("FILE_DOWNLOAD_DAY").toString().toLowerCase());
      String fileDownloadTime = request.getParameter(map.get("FILE_DOWNLOAD_TIME").toString().toLowerCase());
      String fileLend = request.getParameter(map.get("FILE_LEND").toString().toLowerCase());
      String fileLend1 = fileLend;
      if (fileDownload.equals("0")) {
        fileDownload = "noHave;";
        buffer.append(fileDownload);
      } else {
        fileDownload = "FileDownLoad";
        buffer.append(String.valueOf(fileDownload) + "_" + fileDownloadDay + "_" + fileDownloadTime + ";");
      } 
      if (fileRead.equals("0")) {
        fileRead = "noHave;";
        buffer.append(fileRead);
      } else {
        fileRead = "FileRead";
        buffer.append(String.valueOf(fileRead) + "_" + fileReadDay + "_" + fileReadTime + ";");
      } 
      if (fileLend.equals("0")) {
        fileLend = "noHave";
      } else {
        fileLend = "FileLend";
      } 
      buffer.append(fileLend);
      String dataHaveRightList = buffer.toString();
      try {
        OACallArchiveServiceStub stub = new OACallArchiveServiceStub();
        OACallArchiveServiceStub.FinishWorkFlow finish = new OACallArchiveServiceStub.FinishWorkFlow();
        finish.setUsername(userName);
        finish.setStatus("");
        finish.setWorkflowID(id);
        finish.setDataHavaRightlist(dataHaveRightList);
        stub.finishWorkFlow(finish);
        updateArchives(request.getParameter("recordId"), fileName, fileNum, fileRead1, fileReadDay, fileDownload1, fileDownloadDay, fileLend1, fileReadTime, fileDownloadTime);
        retValue = "1";
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return retValue;
  }
}
