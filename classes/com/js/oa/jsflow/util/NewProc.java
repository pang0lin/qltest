package com.js.oa.jsflow.util;

import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.jsflow.vo.ActivityVO;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NewProc {
  public Object[] getDocInfo(String moduleId, String tableId, String editId) {
    WorkFlowCommonBD wfcBD = new WorkFlowCommonBD();
    Map workMap = wfcBD.getWorkInfoByTableID(moduleId, editId);
    Object[] result = { "", "" };
    if (workMap.get("processId") != null && !"null".equalsIgnoreCase(workMap.get("processId").toString()) && 
      !"".equals(workMap.get("tableId").toString()))
      result = getInfo(workMap.get("tableId").toString(), editId, 
          workMap.get("processId").toString(), workMap.get("submitPerson").toString()); 
    return result;
  }
  
  public String getDocCurstep(String tableId, String editId) {
    String res = "";
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select workcurstep from jsf_work where worktable_id=" + tableId + " and workrecord_id=" + editId);
      if (rs.next())
        res = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
    } 
    return res;
  }
  
  public String getProcessId(String tableId, String editId) {
    String res = "";
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select workprocess_id from jsf_work where worktable_id=" + tableId + " and workrecord_id=" + editId);
      if (rs.next())
        res = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
    } 
    return res;
  }
  
  public String getProcessIdGzw(String tableId, String editId) {
    String res = "";
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select WF_WORKFLOWPROCESS_ID from JSF_WORKFLOWPROCESS where ACCESSDATABASEID=(select tableid from doc_receivefile where  receivefile_id=" + editId + ")");
      if (rs.next())
        res = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
    } 
    return res;
  }
  
  public Object[] getDocInfo(String moduleId, String editId) {
    WorkFlowCommonBD wfcBD = new WorkFlowCommonBD();
    Map workMap = wfcBD.getWorkInfo(moduleId, editId);
    Object[] result = { "", "" };
    if (workMap.get("processId") != null && !"null".equalsIgnoreCase(workMap.get("processId").toString()) && 
      !"".equals(workMap.get("tableId").toString()))
      result = getInfo(workMap.get("tableId").toString(), editId, 
          workMap.get("processId").toString(), workMap.get("submitPerson").toString()); 
    return result;
  }
  
  public Object[] getInfo(String tableId, String recordId, String processId, String submitPerson) {
    Object[] result = new Object[2];
    WorkFlowBD workFlowBD = new WorkFlowBD();
    List<String[]> dealProc = workFlowBD.getDealProc(tableId, recordId, "1");
    if (dealProc == null || dealProc.size() == 0) {
      ProcessBD newProcessBD = new ProcessBD();
      ActivityVO activityVO = newProcessBD.getFirstActivity(processId);
      if (activityVO != null && activityVO.getActivityClass() == 2)
        activityVO = (new ProcessStep()).getProceedNextActi((new StringBuilder(String.valueOf(activityVO.getId()))).toString(), tableId, recordId, submitPerson); 
      if (activityVO != null) {
        result[0] = activityVO.getName();
        result[1] = workFlowBD.getNextStepUserName(tableId, 
            recordId, (new StringBuilder(String.valueOf(activityVO.getId()))).toString(), "1");
      } else {
        result[0] = "";
        result[1] = "";
      } 
    } else {
      String[] dealProcCont = (String[])null;
      if (dealProc.size() == 1) {
        dealProcCont = dealProc.get(0);
        result[0] = dealProcCont[1];
        if (dealProcCont[6].equals("0")) {
          result[1] = dealProcCont[8].replaceAll(dealProcCont[3], "");
        } else if (dealProcCont[7] != null && !dealProcCont[7].toString().equals("")) {
          result[0] = dealProcCont[7];
          result[1] = dealProcCont[8];
        } 
      } else {
        String dealWithId = "";
        List<String[]> dealWith = new ArrayList();
        for (int j = 0; j < dealProc.size(); j++) {
          String[] tmp2 = dealProc.get(j);
          if (!dealWithId.equals(tmp2[0])) {
            dealWithId = tmp2[0];
            String[] tmp3 = { 
                tmp2[0], tmp2[1], tmp2[4], tmp2[5], tmp2[6], tmp2[7], 
                tmp2[8], tmp2[9], tmp2[10], tmp2[11], 
                tmp2[12], tmp2[13], tmp2[14] };
            dealWith.add(tmp3);
          } 
        } 
        String[] dealWithCont = dealWith.get(dealWith.size() - 1);
        if ("1".equals((new StringBuilder(String.valueOf(dealWithCont[3]))).toString())) {
          result[0] = "";
          result[1] = "";
        } else {
          result[0] = dealWithCont[1];
          String spareUser = dealWithCont[6];
          for (int i = 0; i < dealProc.size(); i++) {
            dealProcCont = dealProc.get(i);
            if (dealProcCont[0].equals(dealWithCont[0]))
              spareUser = spareUser.replaceAll(dealProcCont[3], ""); 
          } 
          result[1] = spareUser;
          if (dealWithCont[4].equals("1") && dealWithCont[5] != null && !dealWithCont[5].toString().equals("")) {
            result[0] = dealWithCont[5];
            result[1] = dealWithCont[6];
          } 
        } 
      } 
    } 
    return result;
  }
  
  public Object[] getDocDealwithInfo(String moduleId, String tableId, String editId) {
    Object[] result = { "", "" };
    Connection conn = null;
    Statement stmt = null;
    try {
      String empIds = "-1";
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select wf_curemployee_id,workcurstep from jsf_work where workstatus=0 and worktable_id=" + tableId + " and workrecord_id=" + editId);
      while (rs.next()) {
        empIds = String.valueOf(empIds) + "," + rs.getString(1);
        result[0] = rs.getString(2);
      } 
      rs.close();
      if (empIds.length() > 2) {
        rs = stmt.executeQuery("select empname from org_employee where emp_id in(" + empIds + ")");
        empIds = "";
        while (rs.next())
          empIds = String.valueOf(empIds) + rs.getString(1) + ","; 
        rs.close();
        if (empIds.endsWith(","))
          empIds = empIds.substring(0, empIds.length() - 1); 
      } else {
        empIds = "";
      } 
      result[1] = empIds;
      stmt.close();
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
    return result;
  }
}
