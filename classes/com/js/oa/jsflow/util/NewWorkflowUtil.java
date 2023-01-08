package com.js.oa.jsflow.util;

import com.js.oa.jsflow.vo.ActivityVO;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class NewWorkflowUtil {
  public ActivityVO getPreActivity(String tableId, String recordId, String curActivityId, int stepCount) {
    ActivityVO activityVO = new ActivityVO();
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select wf_a.wf_activity_Id,wf_a.activityName,wf_a.presstype,wf_a.deadlinetime,wf_a.pressmotiontime,wf_a.allowstandfor,wf_a.allowcancel,wf_w.wf_curemployee_id from jsf_activity wf_a,jsf_work wf_w where wf_w.initactivity=wf_a.wf_activity_id and wf_w.worktable_id=" + tableId + " and wf_w.workrecord_id=" + recordId + " and workstepcount=" + (stepCount - 1));
      if (rs.next()) {
        activityVO.setId(rs.getLong(1));
        activityVO.setName(rs.getString(2));
        activityVO.setPressType(rs.getInt(3));
        activityVO.setDeadlineTime(rs.getInt(4));
        activityVO.setPressMotionTime(rs.getInt(5));
        activityVO.setAllowStandFor(rs.getInt(6));
        activityVO.setAllowcancel(rs.getInt(7));
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        if (stmt != null)
          stmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception ex) {
        System.out.println("getPreActivity close conn error:" + ex);
      } 
      e.printStackTrace();
    } 
    return activityVO;
  }
  
  public String[] getPreActivityUser(String tableId, String recordId, String curActivityId, int stepCount) {
    String userId = "0";
    Connection conn = null;
    Statement stmt = null;
    String[] result = (String[])null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      int rowNum = 0;
      ResultSet rs = stmt.executeQuery("select count(wf_w.wf_curemployee_id) from jsf_activity wf_a,jsf_work wf_w where wf_w.initactivity=wf_a.wf_activity_id and  wf_w.workstatus=101 and (wf_w.trantype is null or wf_w.trantype<>'1') and wf_w.worktable_id=" + tableId + " and wf_w.workrecord_id=" + recordId + " and workstepcount=" + (stepCount - 1));
      if (rs.next())
        rowNum = rs.getInt(1); 
      result = new String[rowNum];
      rs = stmt.executeQuery("select wf_w.wf_curemployee_id,wf_w.wf_work_id from jsf_activity wf_a,jsf_work wf_w where wf_w.initactivity=wf_a.wf_activity_id and  wf_w.workstatus=101 and (wf_w.trantype is null or wf_w.trantype<>'1') and wf_w.worktable_id=" + tableId + " and wf_w.workrecord_id=" + recordId + " and workstepcount=" + (stepCount - 1) + " order by wf_w.wf_work_id desc");
      int i = 0;
      while (rs.next()) {
        result[i] = rs.getString(1);
        i++;
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        if (stmt != null)
          stmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception ex) {
        System.out.println("getPreActivity close conn error:" + ex);
      } 
      e.printStackTrace();
    } 
    return result;
  }
  
  public String getNoDisplayField(String activityId, String work) {
    StringBuffer fieldStr = new StringBuffer(",");
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      String workStatus = "0", processId = "0";
      conn = (new DataSourceBase()).getDataSource().getConnection();
      if (work != null && !"null".equals(work) && !"".equals(work)) {
        String sql = "select workstatus,workprocess_Id from jsf_work where wf_work_id=?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, work);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
          workStatus = rs.getString(1);
          processId = rs.getString(2);
        } 
        rs.close();
      } 
      System.out.println("worksta:" + workStatus);
      System.out.println("processId:" + processId);
      if (workStatus.equals("1") || workStatus.equals("100")) {
        String sql = "select WRITECONTROLFIELD,field_name from jsf_workflowwritecontrol,tfield where WRITECONTROLFIELD=field_id and wf_workflowprocess_id=? and controltype=2";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, processId);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next())
          fieldStr.append(rs.getString(2)).append(","); 
        rs.close();
      } else {
        String sql = "SELECT controlfield,field_name FROM jsf_readwritecontrol,tfield WHERE controlfield=field_id AND controltype=2 AND wf_activity_id=?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, activityId);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next())
          fieldStr.append(rs.getString(2)).append(","); 
        rs.close();
      } 
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        if (conn != null)
          conn.close(); 
      } catch (Exception ex) {
        System.out.println("getPreActivity close conn error:" + ex);
      } 
      e.printStackTrace();
    } 
    return fieldStr.toString();
  }
  
  public String getNoDisplayField(String activityId, String work, String processId) {
    StringBuffer fieldStr = new StringBuffer("");
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      String moduleId = "1";
      String tableId = "";
      String workStatus = "0";
      String sql = "SELECT jsfp.wf_module_id,jsfw.accessdatabaseid FROM jsf_package jsfp ,jsf_workflowprocess jsfw WHERE jsfp.wf_package_id=jsfw.WF_PACKAGE_ID AND jsfw.wF_WORKFLOWPROCESS_ID=?";
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, processId);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        moduleId = rs.getString(1);
        tableId = rs.getString(2);
      } 
      rs.close();
      pstmt.close();
      if (work != null && !"null".equals(work) && !"".equals(work)) {
        sql = "select workstatus,workprocess_Id from jsf_work where wf_work_id=?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, work);
        rs = pstmt.executeQuery();
        if (rs.next())
          workStatus = rs.getString(1); 
        rs.close();
      } 
      if ("1".equals(moduleId)) {
        fieldStr.append(",");
        if (workStatus.equals("1") || workStatus.equals("100")) {
          sql = "select WRITECONTROLFIELD,field_name from jsf_workflowwritecontrol,tfield where WRITECONTROLFIELD=field_id and wf_workflowprocess_id=? and controltype=2";
          pstmt = conn.prepareStatement(sql);
          pstmt.setString(1, processId);
          rs = pstmt.executeQuery();
          while (rs.next())
            fieldStr.append(rs.getString(2)).append(","); 
          rs.close();
        } else {
          sql = "SELECT controlfield,field_name FROM jsf_readwritecontrol,tfield WHERE controlfield=field_id AND controltype=2 AND wf_activity_id=?";
          pstmt = conn.prepareStatement(sql);
          pstmt.setString(1, activityId);
          rs = pstmt.executeQuery();
          while (rs.next())
            fieldStr.append(rs.getString(2)).append(","); 
          rs.close();
        } 
      } else {
        fieldStr.append("$");
        if (workStatus.equals("1") || workStatus.equals("100")) {
          sql = "SELECT WRITECONTROLFIELD,immofield_pofield FROM jsf_workflowwritecontrol,jsf_immobilityfield WHERE WRITECONTROLFIELD=wf_immofield_id AND wf_workflowprocess_id=? AND controltype=2";
          pstmt = conn.prepareStatement(sql);
          pstmt.setString(1, processId);
          rs = pstmt.executeQuery();
          while (rs.next())
            fieldStr.append(rs.getString(2)).append("$"); 
          rs.close();
        } else {
          sql = "SELECT wf_immofield_id,immofield_pofield FROM jsf_immobilityfield,jsf_readwritecontrol WHERE controlfield=wf_immofield_id AND controltype=2 AND wf_activity_id=? AND wf_immoform_id=?";
          pstmt = conn.prepareStatement(sql);
          pstmt.setString(1, activityId);
          pstmt.setString(2, tableId);
          rs = pstmt.executeQuery();
          while (rs.next())
            fieldStr.append(rs.getString(2)).append("$"); 
          rs.close();
        } 
      } 
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        if (conn != null)
          conn.close(); 
      } catch (Exception ex) {
        System.out.println("getPreActivity close conn error:" + ex);
      } 
      e.printStackTrace();
    } 
    return fieldStr.toString();
  }
  
  public String getFirstActivityNoDisplayField(String processId) {
    StringBuffer fieldStr = new StringBuffer("");
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      String moduleId = "1";
      String tableId = "";
      String sql = "SELECT jsfp.wf_module_id,jsfw.accessdatabaseid FROM jsf_package jsfp ,jsf_workflowprocess jsfw WHERE jsfp.wf_package_id=jsfw.WF_PACKAGE_ID AND jsfw.wF_WORKFLOWPROCESS_ID=?";
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, processId);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        moduleId = rs.getString(1);
        tableId = rs.getString(2);
      } 
      rs.close();
      pstmt.close();
      if ("1".equals(moduleId)) {
        fieldStr.append(",");
        sql = "select WRITECONTROLFIELD,field_name from jsf_workflowwritecontrol,tfield where WRITECONTROLFIELD=field_id and controltype=2 AND wf_workflowprocess_id=?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, processId);
        rs = pstmt.executeQuery();
        while (rs.next())
          fieldStr.append(rs.getString(2)).append(","); 
        rs.close();
      } else {
        fieldStr.append("$");
        sql = "SELECT WRITECONTROLFIELD,immofield_pofield FROM jsf_workflowwritecontrol,jsf_immobilityfield WHERE WRITECONTROLFIELD=wf_immofield_id AND wf_workflowprocess_id=? AND controltype=2";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, processId);
        rs = pstmt.executeQuery();
        while (rs.next())
          fieldStr.append(rs.getString(2)).append("$"); 
        rs.close();
      } 
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        if (conn != null)
          conn.close(); 
      } catch (Exception ex) {
        System.out.println("getPreActivity close conn error:" + ex);
      } 
      e.printStackTrace();
    } 
    return fieldStr.toString();
  }
}
