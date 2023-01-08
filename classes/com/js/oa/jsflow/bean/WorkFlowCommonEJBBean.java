package com.js.oa.jsflow.bean;

import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.util.FlowDeadline;
import com.js.system.service.messages.MessagesBD;
import com.js.system.util.StaticParam;
import com.js.system.vo.messages.MessagesVO;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.IO2File;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.sql.DataSource;
import oracle.sql.CLOB;

public class WorkFlowCommonEJBBean extends DataSourceBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List getFlowedActivity(String tableId, String recordId, String stepCount) throws Exception {
    List<String[]> aList = new ArrayList();
    begin();
    try {
      ResultSet rs = this.stmt.executeQuery("SELECT ACTIVITY_ID, ACTIVITYNAME, CURSTEPCOUNT FROM JSDB.JSF_DEALWITH WHERE DATABASETABLE_ID=" + tableId + " AND DATABASERECORD_ID=" + recordId + " AND CURSTEPCOUNT<" + stepCount);
      while (rs.next()) {
        String[] tmp = { "", "", "" };
        tmp[0] = rs.getString(1);
        tmp[1] = rs.getString(2);
        tmp[2] = rs.getString(3);
        aList.add(tmp);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return aList;
  }
  
  public Map getCommField(String tableId, String recordId) throws Exception {
    Map<Object, Object> commFieldMap = new HashMap<Object, Object>();
    begin();
    try {
      ResultSet rs = this.stmt.executeQuery("SELECT ACTICOMMFIELD, PASSROUNDCOMMFIELD FROM JSF_P_ACTIVITY WHERE TTABLE_ID=" + tableId + " AND TRECORD_ID=" + recordId);
      String commField = null, passroundCommField = null;
      if (rs.next()) {
        commField = rs.getString(1);
        passroundCommField = rs.getString(2);
      } 
      rs.close();
      if (commField != null && !"".equals(commField) && !"null".equals(commField) && !"NULL".equals(commField))
        commFieldMap.put("commField", commField); 
      if (passroundCommField != null && !"".equals(passroundCommField) && !"null".equals(passroundCommField) && !"NULL".equals(passroundCommField))
        commFieldMap.put("passroundCommField", passroundCommField); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return commFieldMap;
  }
  
  public Integer backToSubmitPerson(Map parameter) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      ResultSet rs = this.stmt.executeQuery("SELECT WORKTITLE FROM JSDB.JSF_WORK WHERE WORKSTARTFLAG=1 AND WORKTABLE_ID=" + parameter.get("tableId") + " AND WORKRECORD_ID=" + parameter.get("recordId"));
      String workTitle = "";
      if (rs.next())
        workTitle = rs.getString(1); 
      rs.close();
      if (workTitle.endsWith("正在办理中！")) {
        workTitle = workTitle.substring(0, workTitle.length() - "正在办理中！".length());
        workTitle = String.valueOf(workTitle) + "已被" + parameter.get("userName") + "退回！";
      } else if (workTitle.endsWith("已办理完毕！")) {
        workTitle = workTitle.substring(0, workTitle.length() - "已办理完毕！".length());
        workTitle = String.valueOf(workTitle) + "已被" + parameter.get("userName") + "退回！";
        this.stmt.execute("UPDATE JSDB.JSF_WORK SET workDoneWithDate=null WHERE WORKRECORD_ID=" + parameter.get("recordId") + " AND WORKTABLE_ID=" + parameter.get("tableId"));
      } else {
        workTitle = String.valueOf(workTitle) + "（被" + parameter.get("userName") + "退回）";
      } 
      this.stmt.execute("UPDATE JSDB.JSF_WORK SET WORKSTATUS=-1,WORKTITLE='" + workTitle + "' WHERE WORKSTARTFLAG=1 AND WORKTABLE_ID=" + parameter.get("tableId") + " AND WORKRECORD_ID=" + parameter.get("recordId"));
      rs = this.stmt.executeQuery("SELECT WORKTITLE FROM JSDB.JSF_WORK WHERE WF_WORK_ID=" + parameter.get("workId"));
      if (rs.next())
        workTitle = rs.getString(1); 
      rs.close();
      if (workTitle.endsWith("等待您处理！")) {
        workTitle = workTitle.substring(0, workTitle.length() - "等待您处理！".length());
        workTitle = String.valueOf(workTitle) + "已被您退回！";
      } 
      this.stmt.execute("UPDATE JSDB.JSF_WORK SET WORKSTATUS=101,WORKTITLE='" + workTitle + "', WORKALLOWCANCEL=0 WHERE WF_WORK_ID=" + parameter.get("workId"));
      if (parameter.get("isStandForWork") != null && "1".equals(parameter.get("isStandForWork").toString().trim())) {
        this.stmt.execute("UPDATE JSDB.JSF_WORK SET WORKSTATUS=101,WORKTITLE='" + workTitle + "',WORKALLOWCANCEL=0 WHERE workTable_Id=" + parameter.get("tableId") + " and workRecord_id=" + 
            parameter.get("recordId") + " and workStepCount=" + Integer.parseInt(parameter.get("stepCount").toString()) + " and wf_curemployee_id=" + 
            parameter.get("standForUserId"));
      } else {
        this.stmt.execute("UPDATE JSDB.JSF_WORK SET WORKSTATUS=101,WORKTITLE='" + workTitle + "',WORKALLOWCANCEL=0 WHERE workTable_Id=" + parameter.get("tableId") + " and workRecord_id=" + 
            parameter.get("recordId") + " and workStepCount=" + Integer.parseInt(parameter.get("stepCount").toString()) + " and standForUserId=" + 
            parameter.get("userId") + " and ISSTANDFORWORK=1");
      } 
      this.stmt.execute("DELETE from JSDB.JSF_WORK WHERE WORKTABLE_ID=" + parameter.get("tableId") + " AND WORKRECORD_ID=" + parameter.get("recordId") + " AND WF_WORK_ID<>" + parameter.get("workId") + " AND (WORKSTATUS=0 OR WORKSTATUS=2)");
      this.stmt.execute("UPDATE JSDB.JSF_WORK SET WORKALLOWCANCEL=0 WHERE WORKTABLE_ID=" + parameter.get("tableId") + " AND WORKRECORD_ID=" + parameter.get("recordId") + " AND WORKSTEPCOUNT=" + (Integer.parseInt(parameter.get("stepCount").toString()) - 1));
      rs = this.stmt.executeQuery("SELECT PARETABLEID,PARERECORDID,PARESTEPCOUNT FROM JSDB.JSF_WORK WHERE WF_WORK_ID=" + parameter.get("workId"));
      if (rs != null && rs.next()) {
        String parentTableId = rs.getString(1);
        String parentRecordId = rs.getString(2);
        String parentStepCount = rs.getString(3);
        if (parentStepCount == null || 
          parentStepCount.trim().length() < 1 || 
          parentStepCount.trim().equals("0"))
          parentStepCount = "0"; 
        rs.close();
        if (parentTableId != null && parentTableId.trim().length() >= 1 && 
          !parentTableId.trim().equals("0") && 
          parentRecordId != null && 
          parentRecordId.trim().length() >= 1 && 
          !parentRecordId.trim().equals("0")) {
          rs = this.stmt.executeQuery(
              "SELECT WORKTITLE FROM JSDB.JSF_WORK WHERE WORKSTARTFLAG=1 AND WORKTABLE_ID=" + 
              parentTableId + " AND WORKRECORD_ID=" + 
              parentRecordId);
          workTitle = "";
          if (rs.next())
            workTitle = rs.getString(1); 
          rs.close();
          if (workTitle.endsWith("正在办理中！")) {
            workTitle = workTitle.substring(0, 
                workTitle.length() - "正在办理中！".length());
            if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
              workTitle = "【退回】" + workTitle + "已被" + parameter.get("userName") + 
                "退回！";
            } else {
              workTitle = String.valueOf(workTitle) + "已被" + parameter.get("userName") + 
                "退回！";
            } 
          } else if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
            workTitle = "【退回】" + workTitle + "（被" + parameter.get("userName") + 
              "退回）";
          } else {
            workTitle = String.valueOf(workTitle) + "（被" + parameter.get("userName") + 
              "退回）";
          } 
          this.stmt.execute(
              "UPDATE JSDB.JSF_WORK SET WORKSTATUS=-1,WORKTITLE='" + 
              workTitle + 
              "' WHERE WORKSTARTFLAG=1 AND WORKTABLE_ID=" + 
              parentTableId + " AND WORKRECORD_ID=" + 
              parentRecordId);
          rs = this.stmt.executeQuery("SELECT WORKTITLE FROM JSDB.JSF_WORK WHERE (WORKSTATUS=0 or WORKSTATUS=2) and WORKTABLE_ID=" + 
              parentTableId + 
              " AND WORKRECORD_ID=" + 
              parentRecordId);
          if (rs.next())
            workTitle = rs.getString(1); 
          rs.close();
          if (workTitle.endsWith("等待您处理！") || 
            workTitle.endsWith("等待您审阅！")) {
            workTitle = workTitle.substring(0, 
                workTitle.length() - "等待您处理！".length());
            workTitle = String.valueOf(workTitle) + "已被" + parameter.get("userName") + 
              "退回！";
          } else {
            workTitle = String.valueOf(workTitle) + "（被" + parameter.get("userName") + 
              "退回）";
          } 
          this.stmt.execute(
              "UPDATE JSDB.JSF_WORK SET WORKSTATUS=101,WORKTITLE='" + 
              workTitle + 
              "', WORKALLOWCANCEL=0 WHERE WORKSTATUS=0 and WORKTABLE_ID=" + 
              parentTableId + " AND WORKRECORD_ID=" + 
              parentRecordId);
          this.stmt.execute(
              "UPDATE JSDB.JSF_WORK SET WORKSTATUS=102,WORKTITLE='" + 
              workTitle + 
              "', WORKALLOWCANCEL=0 WHERE WORKSTATUS=2 and WORKTABLE_ID=" + 
              parentTableId + " AND WORKRECORD_ID=" + 
              parentRecordId);
          this.stmt.execute(
              "UPDATE JSDB.JSF_WORK SET WORKALLOWCANCEL=0 WHERE WORKTABLE_ID=" + 
              parentTableId + " AND WORKRECORD_ID=" + 
              parentRecordId + " AND WORKSTEPCOUNT=" + (
              Integer.parseInt(parentStepCount) - 1));
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      result = Integer.valueOf("-1");
      throw e;
    } finally {
      end();
    } 
    return result;
  }
  
  public Integer insertDealWith(Map para) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      String domainId = "0";
      ResultSet rs = this.stmt.executeQuery("SELECT DISTINCT DOMAIN_ID FROM JSF_P_ACTIVITY WHERE WF_ACTIVITY_ID=" + para.get("curActivityId"));
      if (rs.next())
        domainId = rs.getString(1); 
      rs.close();
      rs = this.stmt.executeQuery("SELECT WF_DEALWITH_ID FROM JSDB.JSF_DEALWITH WHERE DATABASETABLE_ID=" + para.get("tableId") + " AND DATABASERECORD_ID=" + para.get("recordId") + " AND ACTIVITY_ID=" + para.get("curActivityId") + " AND CURSTEPCOUNT=" + para.get("stepCount"));
      String wf_dealwith_id = "";
      if (rs.next())
        wf_dealwith_id = rs.getString(1); 
      rs.close();
      if (wf_dealwith_id.equals("")) {
        wf_dealwith_id = getTableId();
        int curActivityStatus = 0;
        if (para.get("nextActivityName") != null && para.get("nextActivityName").toString().equals("-1"))
          curActivityStatus = 1; 
        String subProcWorkId = "0", activityClass = "1";
        if (para.get("activityClass") != null)
          activityClass = para.get("activityClass").toString(); 
        if (para.get("activityClass") != null && para.get("subProcWorkId") != null) {
          subProcWorkId = para.get("subProcWorkId").toString();
          if (para.get("activityClass").toString().equals("0") && para.get("subProcWorkId").toString().equals("0")) {
            rs = this.stmt.executeQuery("SELECT SUBPROCWORKID FROM JSDB.JSF_DEALWITH WHERE DATABASETABLE_ID=" + para.get("tableId") + " AND DATABASERECORD_ID=" + para.get("recordId"));
            if (rs.next())
              subProcWorkId = rs.getString("subProcWorkId"); 
            rs.close();
          } 
          if ("".equals(subProcWorkId))
            subProcWorkId = "0"; 
        } 
        this.stmt.execute("INSERT INTO JSDB.JSF_DEALWITH (WF_DEALWITH_ID,DATABASETABLE_ID,DATABASERECORD_ID,ACTIVITYNAME,ACTIVITY_ID,NEXTACTIVITYNAME,NEXTACTIVITYID,CURACTIVITYSTATUS,CURSTEPCOUNT,ACTIVITYCLASS,SUBPROCWORKID,DOMAIN_ID) VALUES (" + 
            wf_dealwith_id + "," + para.get("tableId") + "," + para.get("recordId") + ",'" + para.get("curActivityName") + "'," + para.get("curActivityId") + ",'" + para.get("nextActivityName") + "'," + para.get("nextActivityId") + "," + curActivityStatus + "," + para.get("stepCount") + "," + activityClass + "," + subProcWorkId + "," + domainId + ")");
      } else if (!para.get("curActivityId").toString().equals("0")) {
        this.stmt.execute("UPDATE JSDB.JSF_DEALWITH SET NEXTACTIVITYID=" + para.get("nextActivityId") + ",NEXTACTIVITYNAME='" + para.get("nextActivityName") + "' " + 
            "WHERE WF_DEALWITH_ID=" + wf_dealwith_id);
      } 
      Date now = new Date();
      String commentField = "";
      if (para.get("commentField") != null)
        commentField = para.get("commentField").toString(); 
      String rangeName = (para.get("userScope") == null) ? "" : para.get("userScope").toString();
      String rangeIdStr = (para.get("scopeId") == null) ? "" : para.get("scopeId").toString();
      String comment = (String)para.get("comment");
      String commentOrg = (String)para.get("commentOrg");
      String commtype = "0";
      if (para.get("nextActivityId").toString().equals("-1"))
        commtype = "1"; 
      String databaseType = SystemCommon.getDatabaseType();
      if (!para.get("isStandForWork").toString().equals("1")) {
        String wf_dealwithcomment_id = getTableId();
        String tmpSql = "";
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,COMMENTFIELD,DOMAIN_ID,RANGENAME,RANGEIDSTR,commtype,signcomment,dealwithOrg) VALUES (" + 
            wf_dealwithcomment_id + "," + para.get("userId") + ",'" + comment + "','" + now.toLocaleString() + "'," + wf_dealwith_id + ",0,'','" + commentField + "'," + domainId + ",'" + rangeName + "','" + rangeIdStr + "','" + commtype + "','" + para.get("signcomment") + "','" + commentOrg + "')";
        } else {
          tmpSql = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,COMMENTFIELD,DOMAIN_ID,RANGENAME,RANGEIDSTR,commtype,signcomment,dealwithOrg) VALUES (" + 
            wf_dealwithcomment_id + "," + para.get("userId") + ",'" + comment + "',JSDB.FN_STRTODATE('" + now.toLocaleString() + "','')," + wf_dealwith_id + ",0,'','" + commentField + "'," + domainId + ",'" + rangeName + "','" + rangeIdStr + "'," + commtype + ",'" + para.get("signcomment") + "','" + commentOrg + "')";
        } 
        this.stmt.execute(tmpSql);
      } else {
        String wf_dealwithcomment_id = getTableId();
        String empName = "";
        rs = this.stmt.executeQuery("SELECT EMPNAME FROM JSDB.ORG_EMPLOYEE WHERE EMP_ID=" + para.get("userId"));
        if (rs.next())
          empName = rs.getString(1); 
        rs.close();
        String tmpSql = "";
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,ISSTANDFORCOMM,COMMENTFIELD,DOMAIN_ID,RANGENAME,RANGEIDSTR,commtype,signcomment,dealwithOrg) VALUES (" + 
            wf_dealwithcomment_id + "," + para.get("standForUserId") + ",'" + comment + "','" + now.toLocaleString() + "'," + wf_dealwith_id + "," + para.get("userId") + ",'" + empName + "',1,'" + commentField + "'," + domainId + ",'" + rangeName + "','" + rangeIdStr + "','" + commtype + "','" + para.get("signcomment") + "','" + para.get("commentOrg") + "')";
        } else {
          tmpSql = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,ISSTANDFORCOMM,COMMENTFIELD,DOMAIN_ID,RANGENAME,RANGEIDSTR,commtype,signcomment,dealwithOrg) VALUES (" + 
            wf_dealwithcomment_id + "," + para.get("standForUserId") + ",'" + comment + "',JSDB.FN_STRTODATE('" + now.toLocaleString() + "','')," + wf_dealwith_id + "," + para.get("userId") + ",'" + empName + "',1,'" + commentField + "'," + domainId + ",'" + rangeName + "','" + rangeIdStr + "','" + commtype + "','" + para.get("signcomment") + "','" + para.get("commentOrg") + "')";
        } 
        this.stmt.execute(tmpSql);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      result = Integer.valueOf("-1");
      throw e;
    } finally {
      end();
    } 
    return result;
  }
  
  public Integer insertTransDealWith(Map para) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    String databaseType = SystemCommon.getDatabaseType();
    try {
      String domainId = "0";
      ResultSet rs = this.stmt.executeQuery("SELECT DISTINCT DOMAIN_ID FROM JSF_P_ACTIVITY WHERE WF_ACTIVITY_ID=" + para.get("curActivityId"));
      if (rs.next())
        domainId = rs.getString(1); 
      rs.close();
      rs = this.stmt.executeQuery("SELECT WF_DEALWITH_ID FROM JSDB.JSF_DEALWITH WHERE DATABASETABLE_ID=" + para.get("tableId") + " AND DATABASERECORD_ID=" + para.get("recordId") + " AND ACTIVITY_ID=" + para.get("curActivityId") + " AND CURSTEPCOUNT=" + para.get("stepCount"));
      String wf_dealwith_id = "";
      if (rs.next())
        wf_dealwith_id = rs.getString(1); 
      rs.close();
      if (wf_dealwith_id.equals("")) {
        wf_dealwith_id = getTableId();
        int curActivityStatus = 0;
        if (para.get("nextActivityName") != null && para.get("nextActivityName").toString().equals("-1"))
          curActivityStatus = 1; 
        String subProcWorkId = "0", activityClass = "1";
        if (para.get("activityClass") != null)
          activityClass = para.get("activityClass").toString(); 
        if (para.get("activityClass") != null && para.get("subProcWorkId") != null) {
          subProcWorkId = para.get("subProcWorkId").toString();
          if (para.get("activityClass").toString().equals("0") && para.get("subProcWorkId").toString().equals("0")) {
            rs = this.stmt.executeQuery("SELECT SUBPROCWORKID FROM JSDB.JSF_DEALWITH WHERE DATABASETABLE_ID=" + para.get("tableId") + " AND DATABASERECORD_ID=" + para.get("recordId"));
            if (rs.next())
              subProcWorkId = rs.getString("subProcWorkId"); 
            rs.close();
          } 
        } 
        this.stmt.execute("INSERT INTO JSDB.JSF_DEALWITH (WF_DEALWITH_ID,DATABASETABLE_ID,DATABASERECORD_ID,ACTIVITYNAME,ACTIVITY_ID,NEXTACTIVITYNAME,NEXTACTIVITYID,CURACTIVITYSTATUS,CURSTEPCOUNT,ACTIVITYCLASS,SUBPROCWORKID,DOMAIN_ID) VALUES (" + 
            wf_dealwith_id + "," + para.get("tableId") + "," + para.get("recordId") + ",'" + para.get("curActivityName") + "'," + para.get("curActivityId") + ",'" + para.get("nextActivityName") + "'," + para.get("nextActivityId") + "," + curActivityStatus + "," + para.get("stepCount") + "," + activityClass + "," + subProcWorkId + "," + domainId + ")");
      } else if (!para.get("curActivityId").toString().equals("0")) {
        this.stmt.execute("UPDATE JSDB.JSF_DEALWITH SET NEXTACTIVITYID=" + para.get("nextActivityId") + ",NEXTACTIVITYNAME='" + para.get("nextActivityName") + "' " + 
            "WHERE WF_DEALWITH_ID=" + wf_dealwith_id);
      } 
      Date now = new Date();
      String commentField = "";
      if (para.get("commentField") != null)
        commentField = para.get("commentField").toString(); 
      String rangeName = (para.get("userScope") == null) ? "" : para.get("userScope").toString();
      String rangeIdStr = (para.get("scopeId") == null) ? "" : para.get("scopeId").toString();
      String modiCommentId = (para.get("modiCommentId") == null) ? "" : para.get("modiCommentId").toString();
      if ("".equals(modiCommentId) || para.get("commentField") == null || "-1".equals(para.get("commentField")) || "".equals(para.get("commentField"))) {
        if (!para.get("isStandForWork").toString().equals("1")) {
          String wf_dealwithcomment_id = getTableId2();
          String tmpSql = "";
          if (databaseType.indexOf("mysql") >= 0) {
            tmpSql = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,COMMENTFIELD,DOMAIN_ID,RANGENAME,RANGEIDSTR,signcomment) VALUES (" + 
              wf_dealwithcomment_id + "," + 
              para.get("userId") + ",'" + 
              para.get("comment") + "','" + 
              now.toLocaleString() + "'," + wf_dealwith_id + 
              ",0,'','" + commentField + "'," + domainId + 
              ",'" + rangeName + "','" + rangeIdStr + "','" + para.get("signcomment") + "')";
          } else {
            tmpSql = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,COMMENTFIELD,DOMAIN_ID,RANGENAME,RANGEIDSTR,signcomment) VALUES (" + 
              wf_dealwithcomment_id + "," + 
              para.get("userId") + ",'" + 
              para.get("comment") + 
              "',JSDB.FN_STRTODATE('" + 
              now.toLocaleString() + "','')," + 
              wf_dealwith_id + ",0,'','" + commentField + 
              "'," + domainId + ",'" + rangeName + "','" + 
              rangeIdStr + "','" + para.get("signcomment") + "')";
          } 
          this.stmt.addBatch(tmpSql);
          this.stmt.addBatch("update JSDB.JSF_DEALWITHCOMMENT set commtype=3 where WF_DEALWITHCOMMENT_ID=" + wf_dealwithcomment_id);
          this.stmt.executeBatch();
          this.stmt.clearBatch();
        } else {
          String wf_dealwithcomment_id = getTableId2();
          String empName = "";
          rs = this.stmt.executeQuery(
              "SELECT EMPNAME FROM JSDB.ORG_EMPLOYEE WHERE EMP_ID=" + 
              para.get("userId"));
          if (rs.next())
            empName = rs.getString(1); 
          rs.close();
          String tmpSql = "";
          if (databaseType.indexOf("mysql") >= 0) {
            tmpSql = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,ISSTANDFORCOMM,COMMENTFIELD,DOMAIN_ID,RANGENAME,RANGEIDSTR,signcomment) VALUES (" + 
              wf_dealwithcomment_id + "," + 
              para.get("standForUserId") + ",'" + 
              para.get("comment") + "','" + 
              now.toLocaleString() + "'," + wf_dealwith_id + 
              "," + para.get("userId") + ",'" + 
              empName + "',1,'" + commentField + "'," + 
              domainId + ",'" + rangeName + "','" + 
              rangeIdStr + "','" + para.get("signcomment") + "')";
          } else {
            tmpSql = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,ISSTANDFORCOMM,COMMENTFIELD,DOMAIN_ID,RANGENAME,RANGEIDSTR,signcomment) VALUES (" + 
              wf_dealwithcomment_id + "," + 
              para.get("standForUserId") + ",'" + 
              para.get("comment") + 
              "',JSDB.FN_STRTODATE('" + 
              now.toLocaleString() + "','')," + 
              wf_dealwith_id + "," + 
              para.get("userId") + ",'" + empName + 
              "',1,'" + commentField + "'," + domainId + 
              ",'" + rangeName + "','" + rangeIdStr + "','" + para.get("signcomment") + "')";
          } 
          this.stmt.addBatch(tmpSql);
          this.stmt.addBatch("update JSDB.JSF_DEALWITHCOMMENT set commtype=3 where WF_DEALWITHCOMMENT_ID=" + wf_dealwithcomment_id);
          this.stmt.executeBatch();
          this.stmt.clearBatch();
        } 
      } else if (!para.get("isStandForWork").toString().equals("1")) {
        String tmpSql = "";
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "update JSDB.JSF_DEALWITHCOMMENT set DEALWITHEMPLOYEE_ID='" + para.get("userId") + "'," + 
            "DEALWITHEMPLOYEECOMMENT='" + para.get("comment") + "',DEALWITHTIME='" + now.toLocaleString() + "',WF_DEALWITH_ID='" + wf_dealwith_id + "'," + 
            "STANDFORUSERID=0,STANDFORUSERNAME='',COMMENTFIELD='" + commentField + "',DOMAIN_ID='" + domainId + "',RANGENAME='" + rangeName + "',RANGEIDSTR='" + rangeIdStr + "',signcomment='" + para.get("signcomment") + "' where WF_DEALWITHCOMMENT_ID=" + modiCommentId;
        } else {
          tmpSql = "update JSDB.JSF_DEALWITHCOMMENT set DEALWITHEMPLOYEE_ID='" + para.get("userId") + "'," + 
            "DEALWITHEMPLOYEECOMMENT='" + para.get("comment") + "',DEALWITHTIME=JSDB.FN_STRTODATE('" + 
            now.toLocaleString() + "',''),WF_DEALWITH_ID='" + wf_dealwith_id + "'," + 
            "STANDFORUSERID=0,STANDFORUSERNAME='',COMMENTFIELD='" + commentField + "',DOMAIN_ID='" + domainId + "',RANGENAME='" + rangeName + "',RANGEIDSTR='" + rangeIdStr + "',signcomment='" + para.get("signcomment") + "' where WF_DEALWITHCOMMENT_ID=" + modiCommentId;
        } 
        this.stmt.execute(tmpSql);
      } else {
        String empName = "";
        rs = this.stmt.executeQuery(
            "SELECT EMPNAME FROM JSDB.ORG_EMPLOYEE WHERE EMP_ID=" + 
            para.get("userId"));
        if (rs.next())
          empName = rs.getString(1); 
        rs.close();
        String tmpSql = "";
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "update JSDB.JSF_DEALWITHCOMMENT set DEALWITHEMPLOYEE_ID='" + para.get("standForUserId") + "'," + 
            "DEALWITHEMPLOYEECOMMENT='" + para.get("comment") + "',DEALWITHTIME='" + now.toLocaleString() + "',WF_DEALWITH_ID='" + wf_dealwith_id + "'," + 
            "STANDFORUSERID='" + para.get("userId") + "',STANDFORUSERNAME='" + empName + "',ISSTANDFORCOMM='1' ,COMMENTFIELD='" + commentField + "'," + 
            "DOMAIN_ID='" + domainId + "',RANGENAME='" + rangeName + "',RANGEIDSTR='" + rangeIdStr + "',signcomment='" + para.get("signcomment") + "' where WF_DEALWITHCOMMENT_ID=" + modiCommentId;
        } else {
          tmpSql = "update JSDB.JSF_DEALWITHCOMMENT set DEALWITHEMPLOYEE_ID='" + para.get("standForUserId") + "'," + 
            "DEALWITHEMPLOYEECOMMENT='" + para.get("comment") + "',DEALWITHTIME=JSDB.FN_STRTODATE('" + 
            now.toLocaleString() + "',''),WF_DEALWITH_ID='" + wf_dealwith_id + "'," + 
            "STANDFORUSERID='" + para.get("userId") + "',STANDFORUSERNAME='" + empName + "',ISSTANDFORCOMM='1' ,COMMENTFIELD='" + commentField + "',DOMAIN_ID='" + domainId + "',RANGENAME='" + rangeName + "',RANGEIDSTR='" + rangeIdStr + "',signcomment='" + para.get("signcomment") + "' where WF_DEALWITHCOMMENT_ID=" + modiCommentId;
        } 
        this.stmt.execute(tmpSql);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      result = Integer.valueOf("-1");
      throw e;
    } finally {
      end();
    } 
    return result;
  }
  
  public Integer backToActivity(Map parameter) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      String workTitle = "", workTitle2 = "";
      ResultSet rs = this.stmt.executeQuery("SELECT WORKTITLE FROM JSDB.JSF_WORK WHERE WF_WORK_ID=" + parameter.get("workId"));
      if (rs.next())
        workTitle = rs.getString(1); 
      rs.close();
      workTitle2 = workTitle;
      if (workTitle.endsWith("等待您处理！")) {
        workTitle = workTitle.substring(0, workTitle.length() - "等待您处理！".length());
        workTitle = String.valueOf(workTitle) + "已被您退回！";
      } 
      if (workTitle.endsWith("您已办理完毕！")) {
        workTitle = workTitle.substring(0, workTitle.length() - "您已办理完毕！".length());
        workTitle = String.valueOf(workTitle) + "已被您退回！";
      } 
      if (workTitle2.endsWith("您已办理完毕！")) {
        workTitle2 = workTitle2.substring(0, workTitle2.length() - "您已办理完毕！".length());
        String workTitle3 = String.valueOf(workTitle2) + "正在处理中！";
        workTitle2 = String.valueOf(workTitle2) + "等待您处理！";
        this.stmt.execute("UPDATE JSDB.JSF_WORK SET WORKSTATUS=1,WORKTITLE='" + workTitle3 + "',WORKALLOWCANCEL=0 WHERE workTable_Id=" + parameter.get("tableId") + " and workRecord_id=" + 
            parameter.get("recordId") + " and WORKSTATUS=100");
        this.stmt.execute("UPDATE JSDB.JSF_WORK SET workDoneWithDate=null WHERE WORKRECORD_ID=" + parameter.get("recordId") + " AND WORKTABLE_ID=" + parameter.get("tableId"));
      } 
      this.stmt.execute("UPDATE JSDB.JSF_WORK SET WORKSTATUS=101,WORKTITLE='" + workTitle + "',WORKALLOWCANCEL=0 WHERE WF_WORK_ID=" + parameter.get("workId"));
      if ("1".equals(parameter.get("isStandForWork"))) {
        this.stmt.execute("UPDATE JSDB.JSF_WORK SET WORKSTATUS=101,WORKTITLE='" + workTitle + "',WORKALLOWCANCEL=0 WHERE workTable_Id=" + parameter.get("tableId") + " and workRecord_id=" + 
            parameter.get("recordId") + " and workStepCount=" + Integer.parseInt(parameter.get("stepCount").toString()) + " and wf_curemployee_id=" + 
            parameter.get("standForUserId"));
      } else {
        this.stmt.execute("UPDATE JSDB.JSF_WORK SET WORKSTATUS=101,WORKTITLE='" + workTitle + "',WORKALLOWCANCEL=0 WHERE workTable_Id=" + parameter.get("tableId") + " and workRecord_id=" + 
            parameter.get("recordId") + " and workStepCount=" + Integer.parseInt(parameter.get("stepCount").toString()) + " and standForUserId=" + 
            parameter.get("userId") + " and isstandForWork=1");
      } 
      this.stmt.execute("DELETE from JSDB.JSF_WORK WHERE WORKTABLE_ID=" + parameter.get("tableId") + " AND WORKRECORD_ID=" + parameter.get("recordId") + " AND WF_WORK_ID<>" + parameter.get("workId") + " AND ( WORKSTARTFLAG IS NULL OR WORKSTARTFLAG=0)");
      this.stmt.execute("UPDATE JSDB.JSF_WORK SET WORKALLOWCANCEL=0 WHERE WORKTABLE_ID=" + parameter.get("tableId") + " AND WORKRECORD_ID=" + parameter.get("recordId") + " AND WORKSTEPCOUNT=" + (Integer.parseInt(parameter.get("stepCount").toString()) - 1));
      String sql = "select workFileType,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workProcess_id,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,emergence,transacttype from JSDB.JSF_WORK where wf_work_id=" + 

        
        parameter.get("workId");
      Date now = new Date();
      rs = this.stmt.executeQuery(sql);
      String workFileType = "";
      String workSubmitPerson = "";
      String workSubmitTime = "";
      String wf_submitEmployee_id = "";
      String workProcess_id = "";
      String isSubProcWork = "", pareProcActiId = "", pareStepCount = "";
      String pareTableId = "", pareRecordId = "", pareProcNextActiId = "", submitOrg = "";
      String emergence = "", transacttype = "";
      if (rs.next()) {
        workFileType = rs.getString("workFileType");
        workSubmitPerson = rs.getString("workSubmitPerson");
        workSubmitTime = rs.getString("workSubmitTime");
        if (workSubmitTime.indexOf(".") > 0)
          workSubmitTime = workSubmitTime.substring(0, workSubmitTime.indexOf(".")); 
        wf_submitEmployee_id = rs.getString("wf_submitEmployee_id");
        workProcess_id = rs.getString("workProcess_id");
        isSubProcWork = rs.getString("isSubProcWork");
        pareProcActiId = rs.getString("pareProcActiId");
        pareStepCount = rs.getString("pareStepCount");
        pareTableId = rs.getString("pareTableId");
        pareRecordId = rs.getString("pareRecordId");
        pareProcNextActiId = rs.getString("pareProcNextActiId");
        submitOrg = rs.getString("submitOrg");
        emergence = rs.getString("emergence");
        transacttype = rs.getString("transacttype");
      } 
      rs.close();
      rs = this.stmt.executeQuery("SELECT DEALWITHEMPLOYEE_ID FROM JSDB.JSF_DEALWITHCOMMENT A,JSDB.JSF_DEALWITH B WHERE A.WF_DEALWITH_ID=B.WF_DEALWITH_ID AND B.DATABASETABLE_ID=" + parameter.get("tableId") + " AND B.DATABASERECORD_ID=" + parameter.get("recordId") + " AND B.ACTIVITY_ID=" + parameter.get("backToActivityId") + " AND B.CURSTEPCOUNT=" + parameter.get("backToStep"));
      String tmp = "";
      while (rs.next()) {
        tmp = rs.getString(1);
        String tmpSql = "";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "INSERT INTO JSDB.JSF_WORK(WF_WORK_ID,WF_CUREMPLOYEE_ID,WORKSTATUS,WORKFILETYPE,WORKCURSTEP,WORKTITLE,WORKLEFTLINKFILE,WORKMAINLINKFILE,WORKLISTCONTROL,WORKACTIVITY,WORKSUBMITPERSON,WORKSUBMITTIME,WF_SUBMITEMPLOYEE_ID,WORKREADMARKER,WORKTYPE,WORKPROCESS_ID,WORKTABLE_ID,WORKRECORD_ID,WORKDEADLINE,WORKPRESSTIME,WORKCREATEDATE,WORKSTEPCOUNT,ISSTANDFORWORK,STANDFORUSERID,STANDFORUSERNAME,INITACTIVITY,INITSTEPCOUNT,ISSUBPROCWORK,PAREPROCACTIID,PARESTEPCOUNT,PARETABLEID,PARERECORDID,PAREPROCNEXTACTIID,SUBMITORG,emergence,transacttype) VALUES (" + 


            
            getTableId() + "," + tmp + ",0," + 
            "'" + workFileType + "','" + parameter.get("backToActivityName") + "','" + workTitle2 + "',''," + 
            "'" + parameter.get("workMainLinkFile") + "',1," + parameter.get("backToActivityId") + ",'" + workSubmitPerson + "'," + 
            "'" + workSubmitTime + "'," + wf_submitEmployee_id + ",0,1," + workProcess_id + "," + 
            parameter.get("tableId") + "," + parameter.get("recordId") + ",-1,-1," + 
            "'" + now.toLocaleString() + "'," + (Integer.parseInt(parameter.get("stepCount").toString()) + 1) + "," + 
            "0,0,''," + parameter.get("backToActivityId") + "," + (Integer.parseInt(parameter.get("stepCount").toString()) + 1) + "," + 
            isSubProcWork + "," + pareProcActiId + "," + pareStepCount + "," + pareTableId + "," + pareRecordId + "," + 
            pareProcNextActiId + ",'" + submitOrg + "','" + emergence + "','" + transacttype + "')";
        } else {
          tmpSql = "INSERT INTO JSDB.JSF_WORK(WF_WORK_ID,WF_CUREMPLOYEE_ID,WORKSTATUS,WORKFILETYPE,WORKCURSTEP,WORKTITLE,WORKLEFTLINKFILE,WORKMAINLINKFILE,WORKLISTCONTROL,WORKACTIVITY,WORKSUBMITPERSON,WORKSUBMITTIME,WF_SUBMITEMPLOYEE_ID,WORKREADMARKER,WORKTYPE,WORKPROCESS_ID,WORKTABLE_ID,WORKRECORD_ID,WORKDEADLINE,WORKPRESSTIME,WORKCREATEDATE,WORKSTEPCOUNT,ISSTANDFORWORK,STANDFORUSERID,STANDFORUSERNAME,INITACTIVITY,INITSTEPCOUNT,ISSUBPROCWORK,PAREPROCACTIID,PARESTEPCOUNT,PARETABLEID,PARERECORDID,PAREPROCNEXTACTIID,SUBMITORG,emergence,transacttype) VALUES (" + 


            
            getTableId() + "," + tmp + ",0," + 
            "'" + workFileType + "','" + parameter.get("backToActivityName") + "','" + workTitle2 + "',''," + 
            "'" + parameter.get("workMainLinkFile") + "',1," + parameter.get("backToActivityId") + ",'" + workSubmitPerson + "'," + 
            "JSDB.FN_STRTODATE('" + workSubmitTime + "','')," + wf_submitEmployee_id + ",0,1," + workProcess_id + "," + 
            parameter.get("tableId") + "," + parameter.get("recordId") + ",-1,-1," + 
            "JSDB.FN_STRTODATE('" + now.toLocaleString() + "','')," + (Integer.parseInt(parameter.get("stepCount").toString()) + 1) + "," + 
            "0,0,''," + parameter.get("backToActivityId") + "," + (Integer.parseInt(parameter.get("stepCount").toString()) + 1) + "," + 
            isSubProcWork + "," + pareProcActiId + "," + pareStepCount + "," + pareTableId + "," + pareRecordId + "," + 
            pareProcNextActiId + ",'" + submitOrg + "','" + emergence + "','" + transacttype + "')";
        } 
        this.stmt.execute(tmpSql);
        ResultSet rs1 = this.stmt.executeQuery("select ALLOWSTANDFOR from JSF_ACTIVITY where ALLOWSTANDFOR=1 and WF_ACTIVITY_ID=" + parameter.get("backToActivityId"));
        if (rs1.next()) {
          ResultSet rs2 = null;
          String[] standInfo = { "", "", "", "" };
          standInfo[0] = tmp;
          rs2 = this.stmt.executeQuery("select empName from JSDB.org_employee where emp_id = " + tmp);
          if (rs2.next())
            standInfo[1] = rs2.getString(1); 
          Calendar now1 = Calendar.getInstance();
          if (databaseType.indexOf("mysql") >= 0) {
            tmpSql = "select proxyEmpId, proxyEmpName from JSDB.oa_workproxy where emp_id = " + tmp + " and " + 
              "proxyState = 1 and '" + now1.get(1) + "/" + (now1.get(2) + 1) + "/" + now1.get(5) + "' between beginTime and endTime";
          } else {
            tmpSql = "select proxyEmpId, proxyEmpName from JSDB.oa_workproxy where emp_id = " + tmp + " and " + 
              "proxyState = 1 and JSDB.FN_STRTODATE('" + now1.get(1) + "/" + (now1.get(2) + 1) + "/" + now1.get(5) + "', 'L') between beginTime and endTime";
          } 
          rs2 = this.stmt.executeQuery(tmpSql);
          if (rs2.next()) {
            standInfo[2] = rs2.getString(1);
            standInfo[3] = rs2.getString(2);
          } else {
            standInfo = (String[])null;
          } 
          rs2.close();
          String[] allowStandForUser = (String[])null;
          if (standInfo != null && 
            !standInfo[2].equals("")) {
            if (databaseType.indexOf("mysql") >= 0) {
              tmpSql = "INSERT INTO JSDB.JSF_WORK(WF_WORK_ID,WF_CUREMPLOYEE_ID,WORKSTATUS,WORKFILETYPE,WORKCURSTEP,WORKTITLE,WORKLEFTLINKFILE,WORKMAINLINKFILE,WORKLISTCONTROL,WORKACTIVITY,WORKSUBMITPERSON,WORKSUBMITTIME,WF_SUBMITEMPLOYEE_ID,WORKREADMARKER,WORKTYPE,WORKPROCESS_ID,WORKTABLE_ID,WORKRECORD_ID,WORKDEADLINE,WORKPRESSTIME,WORKCREATEDATE,WORKSTEPCOUNT,ISSTANDFORWORK,STANDFORUSERID,STANDFORUSERNAME,INITACTIVITY,INITSTEPCOUNT,ISSUBPROCWORK,PAREPROCACTIID,PARESTEPCOUNT,PARETABLEID,PARERECORDID,PAREPROCNEXTACTIID,SUBMITORG,emergence,transacttype) VALUES (" + 



                
                getTableId() + "," + standInfo[2] + ",0," + "'" + workFileType + "','" + 
                parameter.get("backToActivityName") + "','" + workTitle2 + "（代" + standInfo[1] + "办理）',''," + 
                "'" + parameter.get("workMainLinkFile") + "',1," + parameter.get("backToActivityId") + 
                ",'" + workSubmitPerson + "'," + "'" + workSubmitTime + "'," + 
                wf_submitEmployee_id + ",0,1," + workProcess_id + "," + parameter.get("tableId") + "," + 
                parameter.get("recordId") + ",-1,-1," + "'" + 
                now.toLocaleString() + "'," + (Integer.parseInt(parameter.get("stepCount").toString()) + 1) + "," + 
                "1," + standInfo[0] + ",'" + standInfo[1] + "'," + 
                parameter.get("backToActivityId") + "," + (Integer.parseInt(parameter.get("stepCount").toString()) + 1) + "," + 
                isSubProcWork + "," + pareProcActiId + "," + pareStepCount + "," + pareTableId + "," + pareRecordId + "," + 
                pareProcNextActiId + ",'" + submitOrg + "','" + emergence + "','" + transacttype + "')";
            } else {
              tmpSql = "INSERT INTO JSDB.JSF_WORK(WF_WORK_ID,WF_CUREMPLOYEE_ID,WORKSTATUS,WORKFILETYPE,WORKCURSTEP,WORKTITLE,WORKLEFTLINKFILE,WORKMAINLINKFILE,WORKLISTCONTROL,WORKACTIVITY,WORKSUBMITPERSON,WORKSUBMITTIME,WF_SUBMITEMPLOYEE_ID,WORKREADMARKER,WORKTYPE,WORKPROCESS_ID,WORKTABLE_ID,WORKRECORD_ID,WORKDEADLINE,WORKPRESSTIME,WORKCREATEDATE,WORKSTEPCOUNT,ISSTANDFORWORK,STANDFORUSERID,STANDFORUSERNAME,INITACTIVITY,INITSTEPCOUNT,ISSUBPROCWORK,PAREPROCACTIID,PARESTEPCOUNT,PARETABLEID,PARERECORDID,PAREPROCNEXTACTIID,SUBMITORG,emergence,transacttype) VALUES (" + 



                
                getTableId() + "," + standInfo[2] + ",0," + "'" + workFileType + "','" + 
                parameter.get("backToActivityName") + "','" + workTitle2 + "（代" + standInfo[1] + "办理）',''," + 
                "'" + parameter.get("workMainLinkFile") + "',1," + parameter.get("backToActivityId") + 
                ",'" + workSubmitPerson + "'," + "JSDB.FN_STRTODATE('" + workSubmitTime + "','')," + 
                wf_submitEmployee_id + ",0,1," + workProcess_id + "," + parameter.get("tableId") + "," + 
                parameter.get("recordId") + ",-1,-1," + "JSDB.FN_STRTODATE('" + 
                now.toLocaleString() + "','')," + (Integer.parseInt(parameter.get("stepCount").toString()) + 1) + "," + 
                "1," + standInfo[0] + ",'" + standInfo[1] + "'," + 
                parameter.get("backToActivityId") + "," + (Integer.parseInt(parameter.get("stepCount").toString()) + 1) + "," + 
                isSubProcWork + "," + pareProcActiId + "," + pareStepCount + "," + pareTableId + "," + pareRecordId + "," + 
                pareProcNextActiId + ",'" + submitOrg + "','" + emergence + "','" + transacttype + "')";
            } 
            this.stmt.execute(tmpSql);
          } 
        } 
        rs1.close();
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      result = Integer.valueOf("-1");
      throw e;
    } finally {
      end();
    } 
    return result;
  }
  
  public String getFieldName(String fieldId, String moduleId) throws Exception {
    String fieldName = "";
    begin();
    try {
      ResultSet rs = this.stmt.executeQuery("SELECT MODULE_FORMTYPE FROM JSDB.JSF_NEEDFLOWMODULE WHERE WF_MODULE_ID=" + moduleId);
      String formType = "";
      if (rs.next())
        formType = rs.getString(1); 
      rs.close();
      if (formType.equals("1")) {
        rs = this.stmt.executeQuery("SELECT FIELD_NAME FROM TFIELD WHERE FIELD_ID=" + fieldId);
        if (rs.next())
          fieldName = rs.getString(1); 
        rs.close();
      } else {
        rs = this.stmt.executeQuery("SELECT IMMOFIELD_POFIELD FROM JSF_IMMOBILITYFIELD WHERE WF_IMMOFIELD_ID=" + fieldId);
        if (rs.next())
          fieldName = rs.getString(1); 
        rs.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return fieldName;
  }
  
  public Map getProcessClassMethod(String processId) throws Exception {
    Map<Object, Object> result = new HashMap<Object, Object>();
    begin();
    try {
      String formClassName = null;
      String formClassMethod = null;
      String formClassCompMethod = null;
      ResultSet rs = this.stmt.executeQuery("SELECT FORMCLASSNAME,FORMCLASSMETHOD,FORMCLASSCOMPMETHOD FROM JSDB.JSF_WORKFLOWPROCESS WHERE WF_WORKFLOWPROCESS_ID=" + processId);
      if (rs.next()) {
        formClassName = rs.getString(1);
        formClassMethod = rs.getString(2);
        formClassCompMethod = rs.getString(3);
      } 
      rs.close();
      if (formClassName == null || "".equals(formClassName) || formClassMethod == null || "".equals(formClassMethod)) {
        rs = this.stmt.executeQuery("SELECT A.FORMCLASSNAME,A.NEWFORMMETHOD FROM JSDB.JSF_NEEDFLOWMODULE A WHERE A.WF_MODULE_ID=(SELECT B.WF_MODULE_ID FROM JSDB.JSF_PACKAGE B,JSDB.JSF_WORKFLOWPROCESS C WHERE B.WF_PACKAGE_ID=C.WF_PACKAGE_ID AND C.WF_WORKFLOWPROCESS_ID=" + processId + ")");
        if (rs.next()) {
          formClassName = rs.getString(1);
          formClassMethod = rs.getString(2);
          formClassCompMethod = "";
        } 
        rs.close();
      } 
      result.put("formClassName", formClassName);
      result.put("formClassMethod", formClassMethod);
      result.put("formClassCompMethod", formClassCompMethod);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return result;
  }
  
  public Map getActivityClassMethod(String activityId) throws Exception {
    Map<Object, Object> result = new HashMap<Object, Object>();
    begin();
    try {
      String formClassName = null;
      String formClassMethod = null;
      String untreadMethod = null;
      ResultSet rs = this.stmt.executeQuery("SELECT A.FORMCLASSNAME, B.FORMCLASSMETHOD, B.UNTREADMETHOD FROM JSDB.JSF_WORKFLOWPROCESS A,JSDB.JSF_ACTIVITY B WHERE A.WF_WORKFLOWPROCESS_ID=B.WF_WORKFLOWPROCESS_ID AND B.WF_ACTIVITY_ID=" + activityId);
      if (rs.next()) {
        formClassName = rs.getString(1);
        formClassMethod = rs.getString(2);
        untreadMethod = rs.getString(3);
      } 
      rs.close();
      if (formClassName == null || "".equals(formClassName)) {
        rs = this.stmt.executeQuery("SELECT A.FORMCLASSNAME,A.ACTIVITYFORMMETHOD FROM JSDB.JSF_NEEDFLOWMODULE A WHERE A.WF_MODULE_ID=(SELECT B.WF_MODULE_ID FROM JSDB.JSF_PACKAGE B,JSDB.JSF_WORKFLOWPROCESS C,JSDB.JSF_ACTIVITY D WHERE B.WF_PACKAGE_ID=C.WF_PACKAGE_ID AND C.WF_WORKFLOWPROCESS_ID=D.WF_WORKFLOWPROCESS_ID AND D.WF_ACTIVITY_ID=" + activityId + ")");
        if (rs.next()) {
          formClassName = rs.getString(1);
          formClassMethod = rs.getString(2);
        } 
      } 
      result.put("formClassName", formClassName);
      result.put("formClassMethod", formClassMethod);
      result.put("untreadMethod", untreadMethod);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return result;
  }
  
  public String getNoWriteField(String processId) throws Exception {
    begin();
    String result = "";
    try {
      ResultSet rs = this.stmt.executeQuery("SELECT A.MODULE_FORMTYPE FROM JSDB.JSF_NEEDFLOWMODULE A,JSDB.JSF_PACKAGE B,JSDB.JSF_WORKFLOWPROCESS C WHERE C.WF_PACKAGE_ID=B.WF_PACKAGE_ID AND B.WF_MODULE_ID=A.WF_MODULE_ID AND C.WF_WORKFLOWPROCESS_ID=" + processId);
      int formType = 0;
      if (rs.next())
        formType = rs.getInt(1); 
      rs.close();
      StringBuffer sb = new StringBuffer();
      if (formType == 0) {
        rs = this.stmt.executeQuery("SELECT A.IMMOFIELD_POFIELD FROM JSDB.JSF_IMMOBILITYFIELD A WHERE A.WF_IMMOFIELD_ID IN (SELECT B.WRITECONTROLFIELD FROM JSDB.JSF_WORKFLOWWRITECONTROL B WHERE B.WF_WORKFLOWPROCESS_ID=" + processId + ")");
        while (rs.next())
          sb.append("$").append(rs.getString(1)).append("$"); 
        rs.close();
        result = sb.toString();
      } else {
        rs = this.stmt.executeQuery("SELECT FILED_NAME FROM JSDB.TFIELD WHERE FIELD_ID IN (SELECT B.WRITECONTROLFIELD FROM JSDB.JSF_WORKFLOWWRITECONTROL B WHERE B.WF_WORKFLOWPROCESS_ID=" + processId + ")");
        while (rs.next())
          sb.append("$").append(rs.getString(1)).append("$"); 
        rs.close();
        result = sb.toString();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return result;
  }
  
  public Map getModuleDefaultMethod(String moduleId) throws Exception {
    Map<Object, Object> result = new HashMap<Object, Object>();
    begin();
    try {
      ResultSet rs = this.stmt.executeQuery("SELECT FORMCLASSNAME,NEWFORMMETHOD,ACTIVITYFORMMETHOD FROME JSDB.JSF_NEEDFLOWMODULE WHERE WF_MODULE_ID=" + moduleId);
      if (rs.next()) {
        result.put("formClassName", rs.getString(1));
        result.put("newFormMethod", rs.getString(2));
        result.put("activityFormMethod", rs.getString(3));
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return result;
  }
  
  public String getCurActivityWriteField(String activityId, String tableId, String recordId) throws Exception {
    StringBuffer result = new StringBuffer();
    begin();
    try {
      int formType = 1;
      ResultSet rs = this.stmt.executeQuery("SELECT A.MODULE_FORMTYPE FROM JSDB.JSF_NEEDFLOWMODULE A WHERE A.WF_MODULE_ID=(SELECT B.WF_MODULE_ID FROM JSDB.JSF_PACKAGE B,JSDB.JSF_WORKFLOWPROCESS C,JSDB.JSF_ACTIVITY D WHERE B.WF_PACKAGE_ID=C.WF_PACKAGE_ID AND C.WF_WORKFLOWPROCESS_ID=D.WF_WORKFLOWPROCESS_ID AND D.WF_ACTIVITY_ID=" + activityId + ")");
      if (rs.next())
        formType = rs.getInt(1); 
      rs.close();
      if (formType == 1) {
        rs = this.stmt.executeQuery("SELECT FIELD_NAME FROM JSDB.JSF_P_READWRITECONTROL A,JSDB.TFIELD B,JSDB.JSF_P_ACTIVITY C WHERE C.WF_PROCEEDACTIVITY_ID=A.WF_PROCEEDACTIVITY_ID AND A.CONTROLFIELD=B.FIELD_ID AND C.WF_ACTIVITY_ID=" + 
            
            activityId + " AND C.TTABLE_ID= " + tableId + " AND C.TRECORD_ID= " + recordId + " AND A.CONTROLTYPE=1");
      } else {
        rs = this.stmt.executeQuery("SELECT IMMOFIELD_POFIELD FROM JSDB.JSF_P_READWRITECONTROL A,JSDB.JSF_IMMOBILITYFIELD B,JSDB.JSF_P_ACTIVITY C WHERE C.WF_PROCEEDACTIVITY_ID=A.WF_PROCEEDACTIVITY_ID AND A.CONTROLFIELD=B.WF_IMMOFIELD_ID AND C.WF_ACTIVITY_ID=" + 
            
            activityId + " AND C.TTABLE_ID=" + tableId + " AND C.TRECORD_ID=" + recordId + " AND A.CONTROLTYPE=1");
      } 
      while (rs.next())
        result.append("$" + rs.getString(1) + "$"); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return result.toString();
  }
  
  public Map getCurActivityCommField(String activityId, String tableId, String recordId) throws Exception {
    Map<Object, Object> result = new HashMap<Object, Object>();
    begin();
    try {
      ResultSet rs = this.stmt.executeQuery("SELECT ACTICOMMFIELD,PASSROUNDCOMMFIELD FROM JSDB.JSF_P_ACTIVITY WHERE WF_ACTIVITY_ID=" + activityId + " AND TTABLE_ID=" + tableId + " AND TRECORD_ID=" + recordId);
      if (rs.next()) {
        result.put("actiCommField", rs.getString(1));
        result.put("passRoundCommField", rs.getString(2));
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return result;
  }
  
  public String getCommentByCommField(String processId, String tableId, String recordId, String commField) throws Exception {
    StringBuffer result = new StringBuffer();
    begin();
    try {
      ResultSet rs = this.stmt.executeQuery("SELECT B.DEALWITHEMPLOYEECOMMENT,C.EMPNAME,B.DEALWITHTIME,B.ISSTANDFORCOMM,B.STANDFORUSERNAME FROM JSF_DEALWITH A,JSF_DEALWITHCOMMENT B,ORG_EMPLOYEE C WHERE A.WF_DEALWITH_ID=B.WF_DEALWITH_ID AND B.DEALWITHEMPLOYEE_ID=C.EMP_ID AND A.DATABASETABLE_ID=" + 
          
          tableId + " AND A.DATABASERECORD_ID=" + recordId + " AND B.COMMENTFIELD='" + commField + "' " + 
          "AND A.ACTIVITY_ID IN (SELECT WF_ACTIVITY_ID FROM JSF_P_ACTIVITY WHERE WF_WORKFLOWPROCESS_ID=" + processId + ")");
      String comment = "", empName = "", dealwithDate = "", isStandForComm = "", standForUserName = "";
      while (rs.next()) {
        comment = rs.getString(1);
        empName = rs.getString(2);
        dealwithDate = rs.getString(3);
        isStandForComm = rs.getString(4);
        standForUserName = rs.getString(5);
        result.append("&nbsp;&nbsp;&nbsp;&nbsp;");
        if (comment != null && !comment.equals("") && !comment.toUpperCase().equals("NULL"))
          result.append(String.valueOf(comment) + "&nbsp;&nbsp;&nbsp;&nbsp;"); 
        if (dealwithDate.endsWith(".0"))
          dealwithDate = dealwithDate.substring(0, dealwithDate.indexOf(".0")); 
        result.append(String.valueOf(empName) + "&nbsp;&nbsp;" + dealwithDate);
        if (isStandForComm != null && isStandForComm.equals("1"))
          result.append("（" + standForUserName + "代）"); 
        result.append("<br>");
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return result.toString();
  }
  
  public Map getWorkInfo(String moduleId, String recordId) throws Exception {
    Map<Object, Object> result = new HashMap<Object, Object>();
    begin();
    try {
      StringBuffer tableId = new StringBuffer("-100");
      ResultSet rs = this.stmt.executeQuery("SELECT WF_IMMOFORM_ID FROM JSDB.JSF_IMMOBILITYFORM WHERE WF_MODULE_ID=" + moduleId);
      while (rs.next())
        tableId.append(",").append(rs.getString(1)); 
      rs.close();
      String formIdStr = tableId.toString();
      rs = this.stmt.executeQuery("SELECT WORKCURSTEP,WF_SUBMITEMPLOYEE_ID,WORKSUBMITPERSON,WF_WORK_ID,WORKTYPE,WORKACTIVITY,WORKFILETYPE,WORKSUBMITTIME,WORKPROCESS_ID,WORKSTEPCOUNT,ISSTANDFORWORK,STANDFORUSERID,STANDFORUSERNAME FROM JSDB.JSF_WORK WHERE WORKTABLE_ID in(" + formIdStr + ") AND WORKRECORD_ID=" + recordId + " AND (WORKSTATUS=1 OR WORKSTATUS=100 OR WORKSTATUS=-1)");
      if (rs.next()) {
        result.put("activityName", rs.getString(1));
        result.put("submitPersonId", rs.getString(2));
        result.put("submitPerson", rs.getString(3));
        result.put("work", rs.getString(4));
        result.put("workType", rs.getString(5));
        result.put("activity", rs.getString(6));
        result.put("processName", rs.getString(7));
        result.put("submitTime", rs.getString(8));
        result.put("processId", rs.getString(9));
        result.put("stepCount", rs.getString(10));
        result.put("isStandForWork", rs.getString(11));
        result.put("standForUserId", rs.getString(12));
        result.put("standForUserName", rs.getString(13));
      } 
      result.put("tableId", tableId);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return result;
  }
  
  public Map getWorkInfo(String moduleId, String recordId, String formName) throws Exception {
    Map<Object, Object> result = new HashMap<Object, Object>();
    begin();
    try {
      ResultSet rs = this.stmt.executeQuery("SELECT WF_IMMOFORM_ID FROM JSDB.JSF_IMMOBILITYFORM WHERE WF_MODULE_ID=" + moduleId + " and IMMOFORM_DISPLAYNAME='" + formName + "'");
      String tableId = "0";
      if (rs.next())
        tableId = rs.getString(1); 
      rs.close();
      rs = this.stmt.executeQuery("SELECT WORKCURSTEP,WF_SUBMITEMPLOYEE_ID,WORKSUBMITPERSON,WF_WORK_ID,WORKTYPE,WORKACTIVITY,WORKFILETYPE,WORKSUBMITTIME,WORKPROCESS_ID,WORKSTEPCOUNT,ISSTANDFORWORK,STANDFORUSERID,STANDFORUSERNAME FROM JSDB.JSF_WORK WHERE WORKTABLE_ID=" + tableId + " AND WORKRECORD_ID=" + recordId + " AND (WORKSTATUS=1 OR WORKSTATUS=100 OR WORKSTATUS=-1)");
      if (rs.next()) {
        result.put("activityName", rs.getString(1));
        result.put("submitPersonId", rs.getString(2));
        result.put("submitPerson", rs.getString(3));
        result.put("work", rs.getString(4));
        result.put("workType", rs.getString(5));
        result.put("activity", rs.getString(6));
        result.put("processName", rs.getString(7));
        result.put("submitTime", rs.getString(8));
        result.put("processId", rs.getString(9));
        result.put("stepCount", rs.getString(10));
        result.put("isStandForWork", rs.getString(11));
        result.put("standForUserId", rs.getString(12));
        result.put("standForUserName", rs.getString(13));
      } 
      result.put("tableId", tableId);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return result;
  }
  
  public Integer completeWork(String[] para, String workId) throws Exception {
    begin();
    Date now = new Date();
    Integer result = Integer.valueOf("0");
    String databaseType = SystemCommon.getDatabaseType();
    try {
      ResultSet rs = this.stmt.executeQuery("select wf_curemployee_id,workRecord_id,workTable_id,workProcess_id,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId from JSDB.JSF_WORK where wf_work_id=" + 
          workId);
      String workRecord_id = "", workTable_id = "", wf_curemployee_id = "", workProcess_id = "";
      String isSubProcWork = "", pareProcActiId = "", pareStepCount = "", pareTableId = "";
      String pareRecordId = "", pareProcNextActiId = "";
      if (rs.next()) {
        wf_curemployee_id = rs.getString("wf_curemployee_id");
        workRecord_id = rs.getString("workRecord_id");
        workTable_id = rs.getString("workTable_id");
        workProcess_id = rs.getString("workProcess_id");
        isSubProcWork = rs.getString("isSubProcWork");
        pareProcActiId = rs.getString("pareProcActiId");
        pareStepCount = rs.getString("pareStepCount");
        pareTableId = rs.getString("pareTableId");
        pareRecordId = rs.getString("pareRecordId");
        pareProcNextActiId = rs.getString("pareProcNextActiId");
      } 
      rs.close();
      String standTitle = "";
      String workTitle = "";
      if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
        workTitle = String.valueOf(para[5]) + para[3] + "您已办理完毕！";
      } else {
        workTitle = String.valueOf(para[4]) + "的" + para[5] + para[3] + "您已办理完毕！";
      } 
      if (!para[12].equals(""))
        workTitle = para[12]; 
      if (workTitle.endsWith("等待您处理!"))
        workTitle = workTitle.replaceAll("等待您处理!", "您已办理完毕！"); 
      this.stmt.execute("update JSDB.JSF_WORK set WORKDELETE =1 where workstatus = 101 and worktable_id = " + workTable_id + 
          " and workrecord_id = " + workRecord_id + " and wf_curemployee_id = " + 
          wf_curemployee_id + " and workprocess_id=" + workProcess_id);
      this.stmt.execute("delete from JSDB.JSF_WORK where workTable_Id=" + workTable_id + " and workRecord_id=" + 
          workRecord_id + " and workStepCount=" + para[7] + " and standForUserId=" + 
          para[9] + " and workprocess_id = " + workProcess_id);
      if (para[8].equals("1")) {
        rs = this.stmt.executeQuery("SELECT EMPNAME FROM JSDB.ORG_EMPLOYEE WHERE EMP_ID=" + para[9]);
        String myTmp = "";
        if (rs.next())
          myTmp = rs.getString(1); 
        rs.close();
        String tmp = String.valueOf(workTitle) + "（" + myTmp + "代办）";
        if (tmp.length() > 100)
          tmp = tmp.substring(0, 99); 
        this.stmt.execute("UPDATE JSDB.JSF_WORK SET WORKSTATUS=101,WORKTITLE='" + tmp + "',WORKALLOWCANCEL=0 where workTable_Id=" + workTable_id + " and workRecord_id=" + 
            workRecord_id + " and workStepCount=" + para[7] + " and wf_curemployee_id=" + para[10]);
        standTitle = "（代" + para[11] + "办理）";
      } 
      workTitle = String.valueOf(workTitle) + standTitle;
      if (workTitle.length() > 100)
        workTitle = workTitle.substring(0, 99); 
      String deakWithTime = "";
      if ("oracle".equals(databaseType)) {
        deakWithTime = ",dealwithtime=to_date('" + now.toLocaleString() + "','yyyy-MM-dd HH24:mi:ss')";
      } else {
        deakWithTime = ",dealwithtime='" + now.toLocaleString() + "'";
      } 
      String sql = "update JSDB.JSF_WORK set workStatus=101, workTitle='" + workTitle + "'," + 
        "workAllowCancel=0 " + deakWithTime + " where wf_work_id=" + workId;
      this.stmt.execute(sql);
      String activityClass = "1";
      sql = "SELECT activityclass FROM jsf_activity WHERE wf_activity_id=" + para[2];
      rs = this.stmt.executeQuery(sql);
      if (rs.next()) {
        activityClass = rs.getString(1);
        if ("0".equals(activityClass))
          para[6] = "1"; 
      } 
      rs.close();
      int mycount = 0;
      if (para[6].equals("1")) {
        sql = "select count(wf_work_id) from JSDB.JSF_WORK where worktable_id = " + para[0] + 
          " and workrecord_id = " + para[1] + " and workactivity = " + para[2] + 
          " and workstatus = 0";
        rs = this.stmt.executeQuery(sql);
        if (rs.next())
          mycount = rs.getInt(1); 
        rs.close();
      } 
      if (mycount == 0) {
        rs = this.stmt.executeQuery("select userAccounts from JSDB.org_employee,JSDB.JSF_WORK where emp_id=wf_submitemployee_id and worktable_id=" + 
            para[0] + " and workrecord_id=" + para[1] + " and workstartflag=1");
        rs.next();
        rs.close();
        workTitle = "您的" + para[5] + para[3] + "已办理完毕！";
        if (!para[12].equals(""))
          workTitle = para[12]; 
        if (workTitle.endsWith("等待您处理!"))
          workTitle = workTitle.replaceAll("等待您处理!", "已办理完毕！"); 
        if (workTitle.length() > 100)
          workTitle = workTitle.substring(0, 99); 
        if (databaseType.indexOf("mysql") >= 0) {
          sql = "update JSDB.JSF_WORK set workstatus=100,workTitle='" + workTitle + "',workDoneWithDate='" + 
            now.toLocaleString() + "' where worktable_id=" + para[0] + " and " + 
            "workrecord_id=" + para[1] + " and workstartflag=1";
        } else {
          sql = "update JSDB.JSF_WORK set workstatus=100,workTitle='" + workTitle + "',workDoneWithDate=JSDB.FN_STRTODATE('" + 
            now.toLocaleString() + "','') where worktable_id=" + para[0] + " and " + 
            "workrecord_id=" + para[1] + " and workstartflag=1";
        } 
        this.stmt.execute(sql);
      } 
      if (para[6].equals("0")) {
        if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
          workTitle = String.valueOf(para[5]) + para[3] + "已办理完毕！";
        } else {
          workTitle = String.valueOf(para[4]) + "的" + para[5] + para[3] + "已办理完毕！";
        } 
        if (!para[12].equals(""))
          workTitle = para[12]; 
        if (workTitle.endsWith("等待您处理!"))
          workTitle = workTitle.replaceAll("等待您处理!", "已办理完毕！"); 
        if (workTitle.length() > 100)
          workTitle = workTitle.substring(0, 99); 
        this.stmt.execute("update JSDB.JSF_WORK set workStatus = 101, workTitle = '" + workTitle + "',workAllowCancel = 0 " + 
            "where (workStatus = 0 or workStatus = 2) and workTable_id = " + para[0] + 
            " and workRecord_id = " + para[1] + " and workStepCount = " + 
            para[7] + " and wf_work_id <> " + workId);
      } 
      if (mycount == 0 || para[6].equals("0")) {
        result = Integer.valueOf("1");
        if (para.length > 13 && 
          para[13].equals("information"))
          this.stmt.execute("update JSDB.oa_information set informationStatus = 0 where information_id=" + para[1]); 
        this.stmt.execute("update JSDB.JSF_DEALWITH set curActivityStatus=1 where databasetable_id=" + para[0] + " and " + 
            "databaserecord_id=" + para[1] + " and activity_id=" + para[2]);
        if (isSubProcWork.equals("1")) {
          int subProcType = 0;
          rs = this.stmt.executeQuery("select SUBPROCTYPE from jsf_activity where WF_ACTIVITY_ID=" + pareProcActiId);
          if (rs.next())
            subProcType = rs.getInt(1); 
          if (subProcType == 1) {
            String minWorkId = "0";
            String itemTitle = "", toUserId = "", wf_submitEmployee_id = "", workSubmitPerson = "";
            rs = this.stmt.executeQuery("select max(wf_work_id) from JSDB.JSF_WORK where workTable_id = " + 
                pareTableId + " and workRecord_Id = " + pareRecordId + " and workStatus = 0 and workStepCount=" + (Integer.parseInt(pareStepCount) + 1));
            if (rs.next())
              minWorkId = rs.getString(1); 
            rs.close();
            rs = this.stmt.executeQuery("select worktitle,wf_curemployee_id,wf_submitemployee_id,worksubmitperson from JSDB.jsf_work where wf_work_id=" + minWorkId);
            if (rs.next()) {
              itemTitle = rs.getString(1);
              toUserId = rs.getString(2);
              wf_submitEmployee_id = rs.getString(3);
              workSubmitPerson = rs.getString(4);
              rs.close();
              this.stmt.execute(" update JSDB.JSF_WORK set workListControl = 1 where wf_work_id = " + minWorkId);
              MessagesVO msgVO = new MessagesVO();
              msgVO.setMessage_date_begin(now);
              msgVO.setMessage_date_end(new Date("2050/1/1"));
              msgVO.setMessage_send_UserId(Long.parseLong(wf_submitEmployee_id));
              msgVO.setMessage_send_UserName(workSubmitPerson);
              msgVO.setMessage_show(1);
              msgVO.setMessage_status(1);
              msgVO.setMessage_time(now);
              msgVO.setMessage_title(itemTitle);
              msgVO.setMessage_toUserId(Long.parseLong(toUserId));
              msgVO.setMessage_type("jsflow");
              msgVO.setData_id(Long.parseLong(minWorkId));
              msgVO.setMessage_url("/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + minWorkId);
              (new MessagesBD()).messageAdd(msgVO);
            } else {
              rs.close();
            } 
            this.stmt.execute("update JSDB.JSF_DEALWITH set curActivityStatus=1 where databasetable_id=" + pareTableId + 
                " and databaserecord_id=" + pareRecordId + " and activity_id=" + pareProcActiId + " and " + 
                "curStepCount=" + pareStepCount);
          } 
        } 
      } 
      if (mycount == 0) {
        if (databaseType.indexOf("mysql") >= 0) {
          this.stmt.execute("UPDATE JSDB.JSF_WORK SET WORKACTIVITY=0, workDoneWithDate='" + now.toLocaleString() + "' WHERE WORKRECORD_ID=" + workRecord_id + " AND WORKTABLE_ID=" + workTable_id);
        } else {
          this.stmt.execute("UPDATE JSDB.JSF_WORK SET WORKACTIVITY=0, workDoneWithDate=JSDB.FN_STRTODATE('" + 
              now.toLocaleString() + "','') WHERE WORKRECORD_ID=" + workRecord_id + " AND WORKTABLE_ID=" + workTable_id);
        } 
        rs = this.stmt.executeQuery("select wf_work_id,worktitle,wf_curemployee_id from JSDB.jsf_work where workstatus=100 and WORKRECORD_ID=" + workRecord_id + " AND WORKTABLE_ID=" + workTable_id);
        if (rs.next()) {
          workId = rs.getString(1);
          workTitle = rs.getString(2);
          String toUserId = rs.getString(3);
          MessagesVO msgVO = new MessagesVO();
          msgVO.setMessage_date_begin(now);
          msgVO.setMessage_date_end(new Date("2050/1/1"));
          msgVO.setMessage_send_UserId(0L);
          msgVO.setMessage_send_UserName("系统提醒");
          msgVO.setMessage_show(1);
          msgVO.setMessage_status(1);
          msgVO.setMessage_time(now);
          msgVO.setMessage_title(workTitle);
          msgVO.setMessage_toUserId(Long.parseLong(toUserId));
          msgVO.setMessage_type("jsflow");
          msgVO.setData_id(Long.parseLong(workId));
          msgVO.setMessage_url("/jsoa/jsflow/item/jump_dealwith.jsp?status=100&workId=" + workId);
          (new MessagesBD()).messageAdd(msgVO);
        } 
        rs.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return result;
  }
  
  public Long insertFormRecord(String tableName, List field, List<E> fieldValue, List<E> childTableList, List<E> childFieldList, List<ArrayList> childFieldValueList) throws Exception {
    begin();
    Long recordId = null;
    try {
      recordId = Long.valueOf(getTableId());
      StringBuffer tmpField = new StringBuffer();
      for (int i = 0; i < field.size(); i++)
        tmpField.append((new StringBuilder()).append(field.get(i)).append(",").toString()); 
      StringBuffer tmpValue = new StringBuffer();
      for (int j = 0; j < fieldValue.size(); j++)
        tmpValue.append("?,"); 
      String sql = "INSERT INTO JSDB." + tableName + " (" + tmpField + tableName + "_ID) " + 
        "VALUES (" + tmpValue + "?)";
      PreparedStatement pStmt = this.conn.prepareStatement(sql);
      for (int k = 0; k < fieldValue.size(); k++)
        pStmt.setString(k + 1, fieldValue.get(k).toString()); 
      pStmt.setLong(fieldValue.size() + 1, recordId.longValue());
      pStmt.executeUpdate();
      ResultSet rs = null;
      if (childTableList.size() > 0) {
        String childTableName = "", childFieldName = "";
        ArrayList<String[]> tmpChildFieldValueList = new ArrayList();
        String assoField = "";
        for (int m = 0; m < childTableList.size(); m++) {
          childTableName = childTableList.get(m).toString();
          childFieldName = childFieldList.get(m).toString();
          rs = this.stmt.executeQuery("SELECT FIELD_NAME FROM JSDB.TFIELD,JSDB.TLIMIT,TTABLE A, JSDB.TTABLE B WHERE FIELD_ID=LIMIT_FIELD AND LIMIT_TABLE=A.TABLE_ID AND LIMIT_PRYTABLE=B.TABLE_ID AND A.TABLE_NAME='" + 

              
              childTableName + 
              "' AND B.TABLE_NAME='" + tableName + "'");
          if (rs.next())
            assoField = rs.getString(1); 
          rs.close();
          tmpChildFieldValueList = childFieldValueList.get(m);
          for (int n = 0; n < tmpChildFieldValueList.size(); n++) {
            String[] tmp = tmpChildFieldValueList.get(n);
            StringBuffer sb = new StringBuffer();
            for (int i1 = 0; i1 < tmp.length; i1++)
              sb.append("?,"); 
            int childRecordId = 0;
            this.stmt.executeUpdate("UPDATE JSDB.TSEQ SET IUSER=IUSER+1");
            rs = this.stmt.executeQuery("SELECT IUSER FROM JSDB.TSEQ");
            if (rs.next())
              childRecordId = rs.getInt(1); 
            rs.close();
            sql = "INSERT INTO JSDB." + childTableName + "(" + childFieldName + "," + childTableName + "_ID," + assoField + ") VALUES (" + sb.toString() + "?,?)";
            pStmt = this.conn.prepareStatement(sql);
            for (int i2 = 0; i2 < tmp.length; i2++)
              pStmt.setString(i2 + 1, tmp[i2]); 
            pStmt.setString(tmp.length + 1, (new StringBuilder(String.valueOf(childRecordId))).toString());
            pStmt.setString(tmp.length + 2, (String)recordId);
            pStmt.executeUpdate();
          } 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return recordId;
  }
  
  public String getModuleId(String immoFormId) throws Exception {
    String moduleId = "1";
    begin();
    try {
      ResultSet rs = this.stmt.executeQuery("SELECT WF_MODULE_ID FROM JSDB.JSF_IMMOBILITYFORM WHERE WF_IMMOFORM_ID=" + immoFormId);
      if (rs.next())
        moduleId = rs.getString(1); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return moduleId;
  }
  
  public Integer randomProcess(Map randomProcMap) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      String type = (String)randomProcMap.get("type");
      String comment = (String)randomProcMap.get("comment");
      String userId = (String)randomProcMap.get("userId");
      String tableId = (String)randomProcMap.get("tableId");
      String recordId = (String)randomProcMap.get("recordId");
      String submitPerson = (String)randomProcMap.get("submitPerson");
      String remindFieldValue = (String)randomProcMap.get("remindFieldValue");
      String processName = (String)randomProcMap.get("processName");
      String processId = (String)randomProcMap.get("processId");
      String workId = (String)randomProcMap.get("workId");
      String randUser = (String)randomProcMap.get("randUser");
      String randomProcUser = (String)randomProcMap.get("randomProcUser");
      String WORKMAINLINKFILE = (String)randomProcMap.get("mainLinkFile");
      String emergence = (String)randomProcMap.get("emergence");
      int stepCount = Integer.parseInt(randomProcMap.get("stepCount").toString());
      String now = (new Date()).toLocaleString();
      if (remindFieldValue.equals("null"))
        remindFieldValue = ""; 
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "INSERT INTO JSF_RANDFLOWCOMMENT (WF_RANDFLOWCOMMENT_ID,RANDFLOWEMPLOYEE_ID,RANDFLOWEMPLOYEECOMMENT,RANDFLOWTIME,DATABASETABLE_ID,DATABASERECORD_ID) VALUES (" + getTableId() + "," + userId + ",'" + comment + "','" + now + "'," + tableId + "," + recordId + ")";
      } else {
        tmpSql = "INSERT INTO JSF_RANDFLOWCOMMENT (WF_RANDFLOWCOMMENT_ID,RANDFLOWEMPLOYEE_ID,RANDFLOWEMPLOYEECOMMENT,RANDFLOWTIME,DATABASETABLE_ID,DATABASERECORD_ID) VALUES (" + getTableId() + "," + userId + ",'" + comment + "',JSDB.FN_STRTODATE('" + now + "','L')," + tableId + "," + recordId + ")";
      } 
      this.stmt.execute(tmpSql);
      this.stmt.execute("update JSDB.JSF_WORK set WORKDELETE =1 where workstatus = 101 and worktable_id = " + tableId + 
          " and workrecord_id = " + recordId + " and wf_curemployee_id = " + 
          userId + " and workprocess_id=" + processId);
      if (type.equals("end")) {
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "UPDATE JSDB.JSF_WORK SET WORKSTATUS=100,WORKTITLE='您的" + remindFieldValue + processName + "已办理完毕！',WORKALLOWCANCEL=0,WORKDONEWITHDATE='" + now + "' WHERE WORKTABLE_ID=" + tableId + " AND WORKRECORD_ID=" + recordId + " AND WORKSTARTFLAG=1";
        } else {
          tmpSql = "UPDATE JSDB.JSF_WORK SET WORKSTATUS=100,WORKTITLE='您的" + remindFieldValue + processName + "已办理完毕！',WORKALLOWCANCEL=0,WORKDONEWITHDATE=JSDB.FN_STRTODATE('" + now + "','L') WHERE WORKTABLE_ID=" + tableId + " AND WORKRECORD_ID=" + recordId + " AND WORKSTARTFLAG=1";
        } 
        this.stmt.execute(tmpSql);
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "UPDATE JSDB.JSF_WORK SET WORKDONEWITHDATE='" + now + "' WHERE WORKTABLE_ID=" + tableId + " AND WORKRECORD_ID=" + recordId;
        } else {
          tmpSql = "UPDATE JSDB.JSF_WORK SET WORKDONEWITHDATE=JSDB.FN_STRTODATE('" + now + "','L') WHERE WORKTABLE_ID=" + tableId + " AND WORKRECORD_ID=" + recordId;
        } 
        this.stmt.execute(tmpSql);
        if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
          this.stmt.execute("UPDATE JSDB.JSF_WORK SET WORKSTATUS=101,WORKTITLE='" + remindFieldValue + processName + "您已办理完毕！',WORKALLOWCANCEL=0 WHERE WF_WORK_ID=" + workId);
          this.stmt.execute("UPDATE JSDB.JSF_WORK SET WORKTITLE='" + remindFieldValue + processName + "已办理完毕！' WHERE WF_WORK_ID<>" + workId + " AND WORKSTARTFLAG<> 1 AND  WORKTABLE_ID=" + tableId + " AND WORKRECORD_ID=" + recordId);
        } else {
          this.stmt.execute("UPDATE JSDB.JSF_WORK SET WORKSTATUS=101,WORKTITLE='" + submitPerson + "的" + remindFieldValue + processName + "您已办理完毕！',WORKALLOWCANCEL=0 WHERE WF_WORK_ID=" + workId);
          this.stmt.execute("UPDATE JSDB.JSF_WORK SET WORKTITLE='" + submitPerson + "的" + remindFieldValue + processName + "已办理完毕！' WHERE WF_WORK_ID<>" + workId + " AND WORKSTARTFLAG<> 1 AND  WORKTABLE_ID=" + tableId + " AND WORKRECORD_ID=" + recordId);
        } 
      } else {
        if (randUser.indexOf("$") >= 0)
          randUser = randUser.substring(1, randUser.length() - 1); 
        if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
          this.stmt.execute("UPDATE JSF_WORK SET WORKSTATUS=101,WORKTITLE='" + remindFieldValue.toString() + processName + "您已办理完毕！'" + ",WORKALLOWCANCEL=0 WHERE WF_WORK_ID=" + workId);
        } else {
          this.stmt.execute("UPDATE JSF_WORK SET WORKSTATUS=101,WORKTITLE='" + submitPerson + "的" + remindFieldValue.toString() + processName + "您已办理完毕！'" + ",WORKALLOWCANCEL=0 WHERE WF_WORK_ID=" + workId);
        } 
        ResultSet rs = this.stmt.executeQuery("SELECT WORKFILETYPE,WORKSUBMITPERSON,WORKSUBMITTIME,WF_SUBMITEMPLOYEE_ID,WORKPROCESS_ID,WORKTABLE_ID,WORKRECORD_ID,SUBMITORG FROM JSDB.JSF_WORK WHERE WF_WORK_ID=" + 
            
            workId);
        String workFileType = "";
        String workSubmitPerson = "";
        String workSubmitTime = "";
        String wf_submitemployee_id = "";
        String workprocess_id = "";
        String worktable_id = "";
        String workrecord_id = "";
        String submitOrg = "";
        if (rs.next()) {
          workFileType = rs.getString(1);
          workSubmitPerson = rs.getString(2);
          workSubmitTime = rs.getString(3);
          if (workSubmitTime.indexOf(".") >= 0)
            workSubmitTime = workSubmitTime.substring(0, workSubmitTime.indexOf(".")); 
          wf_submitemployee_id = rs.getString(4);
          workprocess_id = rs.getString(5);
          worktable_id = rs.getString(6);
          workrecord_id = rs.getString(7);
          submitOrg = rs.getString(8);
        } 
        rs.close();
        String newWorkId = getTableId();
        String workTitle = "";
        if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
          workTitle = String.valueOf(remindFieldValue) + workFileType;
        } else {
          workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "等待您处理！";
        } 
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "INSERT INTO JSDB.JSF_WORK( WF_WORK_ID,WF_CUREMPLOYEE_ID,WORKSTATUS,WORKFILETYPE,WORKCURSTEP,WORKTITLE,WORKLEFTLINKFILE,WORKMAINLINKFILE,WORKLISTCONTROL,WORKACTIVITY,WORKSUBMITPERSON,WORKSUBMITTIME,WF_SUBMITEMPLOYEE_ID,WORKREADMARKER,WORKTYPE,WORKPROCESS_ID,WORKTABLE_ID,WORKRECORD_ID,WORKDEADLINE,WORKPRESSTIME,WORKCREATEDATE,WORKSTARTFLAG,SUBMITORG,WORKSTEPCOUNT,emergence ) values (" + 





            
            newWorkId + "," + randomProcUser + ", 0, '" + workFileType + "', '', '" + 
            workTitle + "', '','" + WORKMAINLINKFILE + "',1,-100,'" + 
            workSubmitPerson + "','" + workSubmitTime + "'," + 
            wf_submitemployee_id + ",0,0," + workprocess_id + "," + worktable_id + "," + workrecord_id + 
            ",-1,-1,'" + now + "',0 " + 
            ",'" + submitOrg + "'," + String.valueOf(stepCount + 1) + ",'" + emergence + "')";
        } else {
          tmpSql = "INSERT INTO JSDB.JSF_WORK( WF_WORK_ID,WF_CUREMPLOYEE_ID,WORKSTATUS,WORKFILETYPE,WORKCURSTEP,WORKTITLE,WORKLEFTLINKFILE,WORKMAINLINKFILE,WORKLISTCONTROL,WORKACTIVITY,WORKSUBMITPERSON,WORKSUBMITTIME,WF_SUBMITEMPLOYEE_ID,WORKREADMARKER,WORKTYPE,WORKPROCESS_ID,WORKTABLE_ID,WORKRECORD_ID,WORKDEADLINE,WORKPRESSTIME,WORKCREATEDATE,WORKSTARTFLAG,SUBMITORG,WORKSTEPCOUNT,emergence ) values (" + 





            
            newWorkId + "," + randomProcUser + ", 0, '" + workFileType + "', '', '" + 
            workTitle + "', '','" + WORKMAINLINKFILE + "',1,-100,'" + 
            workSubmitPerson + "',JSDB.FN_STRTODATE('" + workSubmitTime + "','')," + 
            wf_submitemployee_id + ",0,0," + workprocess_id + "," + worktable_id + "," + workrecord_id + 
            ",-1,-1,JSDB.FN_STRTODATE('" + now + "',''),0 " + 
            ",'" + submitOrg + "'," + String.valueOf(stepCount + 1) + ",'" + emergence + "')";
        } 
        this.stmt.execute(tmpSql);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return result;
  }
  
  public Long insertFormRecord(String tableName, List<String> field, List<E> fieldValue, List<E> childTableList, List<E> childFieldList, List<ArrayList> childFieldValueList, String[] fieldType) throws Exception {
    begin();
    Long recordId = null;
    try {
      recordId = Long.valueOf(getTableId());
      StringBuffer tmpField = new StringBuffer();
      StringBuffer clobTypeNo = new StringBuffer();
      for (int i = 0; i < field.size(); i++) {
        tmpField.append((new StringBuilder()).append(field.get(i)).append(",").toString());
        if (fieldType[i].equals("CLOB"))
          clobTypeNo.append(String.valueOf(i) + ","); 
      } 
      StringBuffer tmpValue = new StringBuffer();
      for (int j = 0; j < fieldValue.size(); j++)
        tmpValue.append("?,"); 
      String sql = "INSERT INTO JSDB." + tableName + " (" + tmpField + tableName + "_ID) " + 
        "VALUES (" + tmpValue + "?)";
      PreparedStatement pStmt = this.conn.prepareStatement(sql);
      for (int k = 0; k < fieldValue.size(); k++) {
        if (fieldType[k].equals("CLOB")) {
          pStmt.setClob(k + 1, (Clob)CLOB.empty_lob());
        } else {
          pStmt.setString(k + 1, ((fieldType[k].equalsIgnoreCase("numeric") || fieldType[k].equalsIgnoreCase("int")) && fieldValue.get(k).toString().equals("")) ? "0" : fieldValue.get(k).toString());
        } 
      } 
      pStmt.setLong(fieldValue.size() + 1, recordId.longValue());
      pStmt.executeUpdate();
      if (clobTypeNo.length() > 0) {
        String tmp = clobTypeNo.toString();
        if (tmp.endsWith(","))
          tmp = tmp.substring(0, tmp.length() - 1); 
        String[] tmpArray = { tmp };
        if (tmp.indexOf(",") > 0)
          tmpArray = tmp.split(","); 
        ResultSet resultSet = null;
        for (int m = 0; m < tmpArray.length; m++) {
          resultSet = this.stmt.executeQuery("SELECT " + field.get(Integer.parseInt(tmpArray[m])) + " FROM " + tableName + " WHERE " + tableName + "_ID=" + recordId);
          resultSet.next();
          resultSet.close();
        } 
      } 
      ResultSet rs = null;
      if (childTableList.size() > 0) {
        String childTableName = "", childFieldName = "";
        ArrayList<String[]> tmpChildFieldValueList = new ArrayList();
        String assoField = "";
        for (int m = 0; m < childTableList.size(); m++) {
          childTableName = childTableList.get(m).toString();
          childFieldName = childFieldList.get(m).toString();
          rs = this.stmt.executeQuery("SELECT FIELD_NAME FROM JSDB.TFIELD,JSDB.TLIMIT,TTABLE A, JSDB.TTABLE B WHERE FIELD_ID=LIMIT_FIELD AND LIMIT_TABLE=A.TABLE_ID AND LIMIT_PRYTABLE=B.TABLE_ID AND A.TABLE_NAME='" + 

              
              childTableName + 
              "' AND B.TABLE_NAME='" + tableName + "'");
          if (rs.next())
            assoField = rs.getString(1); 
          rs.close();
          tmpChildFieldValueList = childFieldValueList.get(m);
          for (int n = 0; n < tmpChildFieldValueList.size(); n++) {
            String[] tmp = tmpChildFieldValueList.get(n);
            StringBuffer sb = new StringBuffer();
            for (int i1 = 0; i1 < tmp.length; i1++)
              sb.append("?,"); 
            int childRecordId = 0;
            this.stmt.executeUpdate("UPDATE JSDB.TSEQ SET IUSER=IUSER+1");
            rs = this.stmt.executeQuery("SELECT IUSER FROM JSDB.TSEQ");
            if (rs.next())
              childRecordId = rs.getInt(1); 
            rs.close();
            sql = "INSERT INTO JSDB." + childTableName + "(" + childFieldName + "," + childTableName + "_ID," + assoField + ") VALUES (" + sb.toString() + "?,?)";
            pStmt = this.conn.prepareStatement(sql);
            for (int i2 = 0; i2 < tmp.length; i2++)
              pStmt.setString(i2 + 1, tmp[i2]); 
            pStmt.setString(tmp.length + 1, (new StringBuilder(String.valueOf(childRecordId))).toString());
            pStmt.setString(tmp.length + 2, (String)recordId);
            pStmt.executeUpdate();
          } 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return recordId;
  }
  
  public void updateFormRecord(Long recordIdNew, String tableName, Long oldRecordId, String[] tables, String tableid) throws Exception {
    begin();
    Long recordId = null;
    String sql1 = null;
    String sql2 = null;
    String sql3 = null;
    ResultSet rs = null;
    try {
      sql1 = "update " + tableName + " set " + tableName + "_id=" + oldRecordId + " where " + tableName + "_id=" + recordIdNew;
      int n = this.stmt.executeUpdate(sql1);
      if (n > 0)
        if (tables != null && tables.length > 0) {
          for (int j = 0; j < tables.length; j++) {
            sql2 = "select " + tables[j] + "_id from " + tables[j] + " where " + tables[j] + "_FOREIGNKEY=" + recordIdNew;
            rs = this.stmt.executeQuery(sql2);
            while (rs.next()) {
              sql3 = "update " + tables[j] + " set " + tables[j] + "_FOREIGNKEY=" + oldRecordId + " where " + tables[j] + "_id=" + rs.getString(1);
              this.stmt.addBatch(sql3);
            } 
            rs.close();
          } 
          this.stmt.executeBatch();
        }  
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
  }
  
  public Long updateFormRecord(String tableName, List<String> field, List<E> fieldValue, String recordId) throws Exception {
    begin();
    try {
      ResultSet rs = null;
      String typeName = "";
      PreparedStatement pstmt = null;
      for (int i = 0; i < field.size(); i++) {
        rs = this.stmt.executeQuery("SELECT TYPE_NAME FROM JSDB.TFIELD,JSDB.TTYPE WHERE FIELD_TYPE=TYPE_ID AND FIELD_NAME='" + field.get(i) + "'");
        if (rs.next())
          typeName = rs.getString(1); 
        rs.close();
        if (typeName.toUpperCase().equals("CLOB")) {
          rs = this.stmt.executeQuery("SELECT " + field.get(i) + " FROM JSDB." + tableName + " WHERE " + tableName + "_ID=" + recordId);
          rs.next();
        } else {
          pstmt = this.conn.prepareStatement("UPDATE JSDB." + tableName + " SET " + field.get(i) + "=? WHERE " + tableName + "_ID=" + recordId);
          pstmt.setString(1, fieldValue.get(i).toString());
          pstmt.executeUpdate();
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return null;
  }
  
  public Integer insertPassroundDealWith(Map para) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      String activityId = "0", stepCount = "0", tableId = "0", recordId = "0", title = "", initactivityname = "";
      ResultSet rs = this.stmt.executeQuery("select initactivity, initstepcount,worktable_id,workrecord_id,worktitle,initactivityname from JSDB.JSF_WORK where wf_work_id=" + para.get("workId"));
      if (rs.next()) {
        activityId = rs.getString(1);
        stepCount = rs.getString(2);
        tableId = rs.getString(3);
        recordId = rs.getString(4);
        title = rs.getString(5);
        initactivityname = rs.getString(6);
      } 
      rs.close();
      rs = this.stmt.executeQuery("SELECT WF_DEALWITH_ID FROM JSDB.JSF_DEALWITH WHERE DATABASETABLE_ID=" + tableId + " AND DATABASERECORD_ID=" + recordId + " AND ACTIVITY_ID=" + activityId + " AND CURSTEPCOUNT=" + stepCount);
      String wf_dealwith_id = "";
      if (rs.next())
        wf_dealwith_id = rs.getString(1); 
      rs.close();
      if (wf_dealwith_id.equals("")) {
        wf_dealwith_id = getTableId();
        this.stmt.execute("INSERT INTO JSDB.JSF_DEALWITH (WF_DEALWITH_ID,DATABASETABLE_ID,DATABASERECORD_ID,ACTIVITYNAME,ACTIVITY_ID,CURACTIVITYSTATUS,NEXTACTIVITYNAME,NEXTACTIVITYID,CURSTEPCOUNT,ACTIVITYCLASS,SUBPROCWORKID) VALUES (" + 
            wf_dealwith_id + "," + tableId + "," + recordId + ",'" + initactivityname + "'," + activityId + ",0,'-100',-100," + stepCount + ",1,0)");
      } 
      Date now = new Date();
      String commentField = "";
      if (para.get("commentField") != null)
        commentField = para.get("commentField").toString(); 
      String wf_dealwithcomment_id = getTableId();
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,COMMENTFIELD,signcomment) VALUES (" + 
          wf_dealwithcomment_id + "," + para.get("userId") + ",'" + para.get("comment") + "','" + now.toLocaleString() + "'," + wf_dealwith_id + ",0,'','" + commentField + "','" + para.get("signcomment") + "')";
      } else {
        tmpSql = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,COMMENTFIELD,signcomment) VALUES (" + 
          wf_dealwithcomment_id + "," + para.get("userId") + ",'" + para.get("comment") + "',JSDB.FN_STRTODATE('" + now.toLocaleString() + "','')," + wf_dealwith_id + ",0,'','" + commentField + "','" + para.get("signcomment") + "')";
      } 
      this.stmt.execute(tmpSql);
      if (databaseType.indexOf("mysql") >= 0) {
        this.stmt.execute("update JSDB.JSF_WORK set workstatus=102, worktitle='" + title.replaceAll("等待您的审阅", "审阅完毕") + "',WORKDONEWITHDATE='" + now.toLocaleString() + "' where wf_work_id=" + para.get("workId"));
      } else {
        this.stmt.execute("update JSDB.JSF_WORK set workstatus=102, worktitle='" + title.replaceAll("等待您的审阅", "审阅完毕") + "',WORKDONEWITHDATE=JSDB.FN_STRTODATE('" + now.toLocaleString() + "','') where wf_work_id=" + para.get("workId"));
      } 
    } catch (Exception e) {
      result = Integer.valueOf("-1");
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return result;
  }
  
  public Map getCurActivityCommField(String activityId, String tableId, String recordId, String workId) throws Exception {
    Map<Object, Object> result = new HashMap<Object, Object>();
    begin();
    try {
      ResultSet rs = this.stmt.executeQuery("SELECT ACTICOMMFIELD FROM JSDB.JSF_P_ACTIVITY WHERE WF_ACTIVITY_ID=" + activityId + " AND TTABLE_ID=" + tableId + " AND TRECORD_ID=" + recordId);
      if (rs.next())
        result.put("actiCommField", rs.getString(1)); 
      rs.close();
      rs = this.stmt.executeQuery("SELECT INITACTIVITY FROM JSDB.JSF_WORK WHERE WF_WORK_ID=" + workId);
      String initActivity = "0";
      if (rs.next())
        initActivity = rs.getString(1); 
      rs.close();
      rs = this.stmt.executeQuery("SELECT PASSROUNDCOMMFIELD FROM JSDB.JSF_P_ACTIVITY WHERE WF_ACTIVITY_ID=" + initActivity + " AND TTABLE_ID=" + tableId + " AND TRECORD_ID=" + recordId);
      if (rs.next())
        result.put("passRoundCommField", rs.getString(1)); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return result;
  }
  
  public Map getBackToPerson(String tableId, String recordId, String stepCount, String initStepCount, String workId) throws Exception {
    Map<Object, Object> result = new HashMap<Object, Object>();
    begin();
    try {
      StringBuffer empId = new StringBuffer();
      StringBuffer empName = new StringBuffer();
      String sql = "";
      if (initStepCount.equals("0")) {
        sql = "SELECT EMP_ID,EMPNAME FROM ORG_EMPLOYEE WHERE EMP_ID IN (SELECT DISTINCT WF_CUREMPLOYEE_ID FROM JSDB.JSF_WORK WHERE WORKTABLE_ID=" + tableId + " AND WORKRECORD_ID=" + recordId + " AND WORKSTEPCOUNT<=" + stepCount + " and (workStatus<>2 and workStatus<>102) AND WF_WORK_ID<>" + workId + ")";
      } else {
        sql = "SELECT EMP_ID,EMPNAME FROM ORG_EMPLOYEE WHERE EMP_ID IN (SELECT DISTINCT WF_CUREMPLOYEE_ID FROM JSDB.JSF_WORK WHERE WORKTABLE_ID=" + tableId + " AND WORKRECORD_ID=" + recordId + " AND INITSTEPCOUNT>=" + initStepCount + " and (workStatus<>2 and workStatus<>102) AND WORKSTEPCOUNT<=" + stepCount + " AND WF_WORK_ID<>" + workId + ")";
      } 
      ResultSet rs = this.stmt.executeQuery(sql);
      while (rs.next()) {
        empId.append("$" + rs.getString(1) + "$");
        empName.append(String.valueOf(rs.getString(2)) + ",");
      } 
      rs.close();
      result.put("empId", empId.toString());
      result.put("empName", empName.toString());
      String title = "";
      rs = this.stmt.executeQuery("SELECT WORKTITLE FROM JSDB.JSF_WORK WHERE WF_WORK_ID=" + workId);
      if (rs.next())
        title = rs.getString(1); 
      rs.close();
      title = title.replaceAll("已被您退回！", "");
      result.put("title", title);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return result;
  }
  
  public String getCommentByCommField(String processId, String tableId, String recordId, String commField, String userId, String orgId, String isEdit) throws Exception {
    StringBuffer result = new StringBuffer("<table width=100% border=0>");
    begin();
    try {
      ResultSet rs = null;
      StringBuffer sql = new StringBuffer("SELECT B.DEALWITHEMPLOYEECOMMENT,C.EMPNAME,B.DEALWITHTIME,B.ISSTANDFORCOMM,B.STANDFORUSERNAME,B.WF_DEALWITHCOMMENT_ID,B.RANGENAME,B.RANGEIDSTR,C.SIGNATUREIMGSAVENAME,B.commtype,B.signcomment,B.DEALWITHEMPLOYEE_ID FROM JSF_DEALWITH A,");
      sql.append("JSF_DEALWITHCOMMENT B,ORG_EMPLOYEE C WHERE A.WF_DEALWITH_ID=B.WF_DEALWITH_ID AND B.DEALWITHEMPLOYEE_ID=C.EMP_ID AND ");
      if (SystemCommon.getShowBackComment() == 1) {
        sql.append(" A.DATABASETABLE_ID=");
      } else {
        sql.append(" (B.commtype is null or B.commtype<>1) and A.DATABASETABLE_ID=");
      } 
      sql.append(String.valueOf(tableId) + " AND A.DATABASERECORD_ID=" + recordId + " AND B.COMMENTFIELD='" + commField + "' ");
      if (isEdit == null || isEdit.equals("0")) {
        sql.append("AND (B.RANGEIDSTR IS NULL OR B.RANGEIDSTR='' ");
        sql.append("OR B.RANGEIDSTR LIKE '%$" + userId + "$%' ");
        rs = this.stmt.executeQuery("SELECT GROUP_ID FROM ORG_USER_GROUP WHERE EMP_ID=" + userId);
        while (rs.next())
          sql.append("OR B.RANGEIDSTR LIKE '%@" + rs.getString(1) + "@%' "); 
        rs.close();
        String tmpSql = "";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "SELECT A.ORG_ID FROM ORG_ORGANIZATION A,ORG_ORGANIZATION B WHERE B.ORGIDSTRING LIKE concat('%$', A.ORG_ID,'$%') AND B.ORG_ID=" + orgId;
        } else if (databaseType.indexOf("db2") >= 0) {
          tmpSql = "SELECT A.ORG_ID FROM ORG_ORGANIZATION A,ORG_ORGANIZATION B WHERE B.ORGIDSTRING locate JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', A.ORG_ID),'$')>0 AND B.ORG_ID=" + orgId;
        } else {
          tmpSql = "SELECT A.ORG_ID FROM ORG_ORGANIZATION A,ORG_ORGANIZATION B WHERE B.ORGIDSTRING LIKE JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', A.ORG_ID),'$%') AND B.ORG_ID=" + orgId;
        } 
        rs = this.stmt.executeQuery(tmpSql);
        while (rs.next())
          sql.append("OR B.RANGEIDSTR LIKE '%*" + rs.getString(1) + "*%' "); 
        rs.close();
        sql.append(")");
      } 
      sql.append(" ORDER BY B.WF_DEALWITHCOMMENT_ID");
      rs = this.stmt.executeQuery(sql.toString());
      String comment = "", empName = "", dealwithDate = "", isStandForComm = "", standForUserName = "", SIGNATUREIMGSAVENAME = "", commtype = "";
      while (rs.next()) {
        if (rs.getString(1) == null || "".equals(rs.getString(1)) || rs.getString(1).indexOf("无批示意见") != -1)
          continue; 
        comment = rs.getString(1);
        if ("1".equals(SystemCommon.getIsshowDealwithOrgName())) {
          String level = SystemCommon.getIsshowOrgNameLevel();
          int showlevel = 0;
          if ("".equals(level)) {
            showlevel = 0;
          } else {
            showlevel = Integer.parseInt(level);
          } 
          empName = String.valueOf(StaticParam.getOrgByNum(StaticParam.getOrgNameStringByEmpId(rs.getString(12)), showlevel)) + " " + rs.getString(2);
        } else {
          empName = rs.getString(2);
        } 
        dealwithDate = rs.getString(3);
        isStandForComm = rs.getString(4);
        standForUserName = rs.getString(5);
        SIGNATUREIMGSAVENAME = (rs.getString(9) == null) ? "" : rs.getString(9);
        commtype = (rs.getString(10) == null) ? "" : rs.getString(10);
        String signcomment = rs.getString(11);
        if (signcomment != null && signcomment.length() > 4) {
          result.append("<tr>");
          result.append("<td height=1 nowrap valign=bottom>");
          result.append("<div id=\"signPosi_").append(signcomment).append("\" style=\"position:absolute;width:100%;height:100%;\"></div>");
          result.append("</td>");
          result.append("</tr>");
        } 
        result.append("<tr>");
        result.append("<td>&nbsp;&nbsp;");
        if (comment != null && !comment.equals("") && 
          !comment.toUpperCase().equals("NULL"))
          result.append(comment); 
        result.append("</td>");
        result.append("</tr>");
        result.append("<tr>");
        result.append("<td nowrap valign=bottom align=right>");
        if (commtype.equals("3"))
          result.append("转办人："); 
        if (SIGNATUREIMGSAVENAME.equals("")) {
          result.append((new StringBuilder(String.valueOf(empName))).toString());
        } else {
          String srcTr = "0000";
          if (SIGNATUREIMGSAVENAME != null && SIGNATUREIMGSAVENAME.length() > 6 && SIGNATUREIMGSAVENAME.substring(4, 5).equals("_")) {
            srcTr = SIGNATUREIMGSAVENAME.substring(0, 4);
          } else {
            srcTr = "0000";
          } 
          result.append("<IMG SRC='/jsoa/upload/" + srcTr + "/peopleinfo/" + SIGNATUREIMGSAVENAME + "'>");
        } 
        if (dealwithDate.endsWith(".0"))
          dealwithDate = dealwithDate.substring(0, 
              dealwithDate.indexOf(".0")); 
        result.append("&nbsp;&nbsp;" + dealwithDate);
        if (isStandForComm != null && isStandForComm.equals("1"))
          result.append("（" + standForUserName + "代）"); 
        result.append("&nbsp;&nbsp;");
        result.append("<input type=hidden name=wf_dealwithcomment_id value=" + rs.getString(6) + ">");
        result.append("</td></tr>");
        result.append("<tr><td height=10></td></tr>");
      } 
      rs.close();
      result.append("</table>");
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return result.toString();
  }
  
  public List getCommentListByCommField(String processId, String tableId, String recordId, String commField, String userId, String orgId, String isEdit) throws Exception {
    List<String[]> result = new ArrayList();
    begin();
    try {
      ResultSet rs = null;
      StringBuffer sql = new StringBuffer("SELECT B.DEAadministratorLWITHEMPLOYEECOMMENT,C.EMPNAME,B.DEALWITHTIME,B.ISSTANDFORCOMM,B.STANDFORUSERNAME,B.WF_DEALWITHCOMMENT_ID,B.RANGENAME,B.RANGEIDSTR,C.SIGNATUREIMGSAVENAME FROM JSF_DEALWITH A,JSF_DEALWITHCOMMENT B,ORG_EMPLOYEE C WHERE A.WF_DEALWITH_ID=B.WF_DEALWITH_ID AND B.DEALWITHEMPLOYEE_ID=C.EMP_ID AND   (B.commtype is null or B.commtype<>1) and A.DATABASETABLE_ID=" + 
          
          tableId + " AND A.DATABASERECORD_ID=" + recordId + " AND B.COMMENTFIELD='" + commField + "' " + 
          "AND A.ACTIVITY_ID IN (SELECT WF_ACTIVITY_ID FROM JSF_P_ACTIVITY WHERE WF_WORKFLOWPROCESS_ID=" + processId + ") ");
      if (isEdit == null || isEdit.equals("0")) {
        sql.append("AND (B.RANGEIDSTR IS NULL OR B.RANGEIDSTR='' ");
        sql.append("OR B.RANGEIDSTR LIKE '%$" + userId + "$%' ");
        rs = this.stmt.executeQuery("SELECT GROUP_ID FROM ORG_USER_GROUP WHERE EMP_ID=" + userId);
        while (rs.next())
          sql.append("OR B.RANGEIDSTR LIKE '%@" + rs.getString(1) + "@%' "); 
        rs.close();
        String tmpSql = "";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "SELECT A.ORG_ID FROM ORG_ORGANIZATION A,ORG_ORGANIZATION B WHERE B.ORGIDSTRING LIKE concat('%$', A.ORG_ID,'$%') AND B.ORG_ID=" + orgId;
        } else if (databaseType.indexOf("db2") >= 0) {
          tmpSql = "SELECT A.ORG_ID FROM ORG_ORGANIZATION A,ORG_ORGANIZATION B WHERE B.ORGIDSTRING locate JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', A.ORG_ID),'$')>0 AND B.ORG_ID=" + orgId;
        } else {
          tmpSql = "SELECT A.ORG_ID FROM ORG_ORGANIZATION A,ORG_ORGANIZATION B WHERE B.ORGIDSTRING LIKE JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', A.ORG_ID),'$%') AND B.ORG_ID=" + orgId;
        } 
        rs = this.stmt.executeQuery(tmpSql);
        while (rs.next())
          sql.append("OR B.RANGEIDSTR LIKE '%*" + rs.getString(1) + "*%' "); 
        rs.close();
        sql.append(")");
      } 
      sql.append(" ORDER BY B.WF_DEALWITHCOMMENT_ID");
      rs = this.stmt.executeQuery(sql.toString());
      String comment = "", empName = "", dealwithDate = "", isStandForComm = "", standForUserName = "", SIGNATUREIMGSAVENAME = "";
      while (rs.next()) {
        String[] tmp = new String[3];
        if (rs.getString(1).indexOf("无批示意见") != -1)
          continue; 
        comment = rs.getString(1);
        empName = rs.getString(2);
        dealwithDate = rs.getString(3);
        isStandForComm = rs.getString(4);
        standForUserName = rs.getString(5);
        SIGNATUREIMGSAVENAME = (rs.getString(9) == null) ? "" : rs.getString(9);
        if (comment != null && !comment.equals("") && 
          !comment.toUpperCase().equals("NULL"))
          tmp[0] = comment; 
        tmp[1] = (new StringBuilder(String.valueOf(empName))).toString();
        if (dealwithDate.endsWith(".0"))
          dealwithDate = dealwithDate.substring(0, 
              dealwithDate.indexOf(".0")); 
        tmp[2] = dealwithDate;
        result.add(tmp);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return result;
  }
  
  public Integer updateCommentRange(String[] commentId, String[] rangeName, String[] rangeId) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      if (commentId != null)
        for (int i = 0; i < commentId.length; i++)
          this.stmt.execute("UPDATE JSDB.JSF_DEALWITHCOMMENT SET RANGENAME='" + rangeName[i] + "',RANGEIDSTR='" + rangeId[i] + "' WHERE WF_DEALWITHCOMMENT_ID=" + commentId[i]);  
    } catch (Exception e) {
      result = Integer.valueOf("-1");
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return result;
  }
  
  public Boolean inCommentRange(String userId, String orgIdString, String commentRange) throws Exception {
    Boolean result = Boolean.FALSE;
    begin();
    try {
      if (commentRange != null && !"".equals(commentRange) && !"null".equalsIgnoreCase(commentRange)) {
        if (commentRange.indexOf("$" + userId + "$") >= 0) {
          result = Boolean.TRUE;
        } else {
          ResultSet rs = this.stmt.executeQuery("SELECT GROUP_ID FROM ORG_USER_GROUP WHERE EMP_ID=" + userId);
          while (rs.next()) {
            if (commentRange.indexOf("@" + rs.getString(1) + "@") >= 0) {
              result = Boolean.TRUE;
              break;
            } 
          } 
          rs.close();
          if (!result.booleanValue()) {
            String tmpSql = "";
            String databaseType = SystemCommon.getDatabaseType();
            if (databaseType.indexOf("mysql") >= 0) {
              tmpSql = "SELECT ORG_ID FROM ORG_ORGANIZATION WHERE '" + orgIdString + "' LIKE concat('%$', ORG_ID,'$%')";
            } else if (databaseType.indexOf("db2") >= 0) {
              tmpSql = "SELECT ORG_ID FROM ORG_ORGANIZATION WHERE '" + orgIdString + "' locate JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', ORG_ID),'$')>0";
            } else {
              tmpSql = "SELECT ORG_ID FROM ORG_ORGANIZATION WHERE '" + orgIdString + "' LIKE JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', ORG_ID),'$%')";
            } 
            rs = this.stmt.executeQuery(tmpSql);
            while (rs.next()) {
              if (commentRange.indexOf("*" + rs.getString(1) + "*") >= 0) {
                result = Boolean.TRUE;
                break;
              } 
            } 
            rs.close();
          } 
        } 
      } else {
        result = Boolean.TRUE;
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return result;
  }
  
  public List getRelatedProc(String workId) throws Exception {
    List<String[]> result = new ArrayList();
    begin();
    try {
      ResultSet rs = this.stmt.executeQuery("SELECT ISSUBPROCWORK, PARESTEPCOUNT, PARETABLEID, PARERECORDID FROM JSF_WORK WHERE WF_WORK_ID=" + workId);
      String issubprocwork = "", parestepcount = "", paretableid = "", parerecordid = "";
      if (rs.next()) {
        issubprocwork = rs.getString(1);
        parestepcount = rs.getString(2);
        paretableid = rs.getString(3);
        parerecordid = rs.getString(4);
      } 
      rs.close();
      if (issubprocwork != null && "1".equals(issubprocwork)) {
        List<String[]> tmpList = new ArrayList();
        rs = this.stmt.executeQuery("SELECT DISTINCT WORKTABLE_ID, WORKRECORD_ID FROM JSF_WORK WHERE ISSUBPROCWORK=1 AND PARESTEPCOUNT<" + parestepcount + " AND PARETABLEID=" + paretableid + " AND PARERECORDID=" + parerecordid);
        while (rs.next()) {
          String[] obj = { "", "" };
          obj[0] = rs.getString(1);
          obj[1] = rs.getString(2);
          tmpList.add(obj);
        } 
        rs.close();
        Object[] tmpObj = (Object[])null;
        for (int i = 0; i < tmpList.size(); i++) {
          tmpObj = (Object[])tmpList.get(i);
          rs = this.stmt.executeQuery("SELECT WORKFILETYPE, WORKMAINLINKFILE, WORKCURSTEP, WF_SUBMITEMPLOYEE_ID, WORKSUBMITPERSON, WF_WORK_ID, WORKTYPE, WORKACTIVITY, WORKTABLE_ID, WORKRECORD_ID, WORKSUBMITTIME, WORKPROCESS_ID, WORKSTEPCOUNT, ISSTANDFORWORK, STANDFORUSERID, STANDFORUSERNAME, WORKSTATUS FROM JSF_WORK WHERE (WORKSTATUS=1 OR WORKSTATUS=100) AND WORKTABLE_ID=" + tmpObj[0] + " AND WORKRECORD_ID=" + tmpObj[1] + " ORDER BY WORKSTEPCOUNT");
          String workFileType = "";
          if (rs.next()) {
            String[] str = { "", "" };
            workFileType = rs.getString("WORKFILETYPE");
            str[0] = workFileType;
            str[1] = String.valueOf(rs.getString("WORKMAINLINKFILE")) + "&search=&workTitle=&activityName=" + rs.getString("WORKCURSTEP") + "&submitPersonId=" + rs.getString("WF_SUBMITEMPLOYEE_ID") + "&submitPerson=" + rs.getString("WORKSUBMITPERSON") + "&work=" + rs.getString("WF_WORK_ID") + "&workType=" + rs.getString("WORKTYPE") + "&activity=" + rs.getString("WORKACTIVITY") + "&table=" + rs.getString("WORKTABLE_ID") + "&record=" + rs.getString("WORKRECORD_ID") + "&processName=" + workFileType + "&submitTime=" + rs.getString("WORKSUBMITTIME") + "&processId=" + rs.getString("WORKPROCESS_ID") + "&stepCount=" + rs.getString("WORKSTEPCOUNT") + "&isStandForWork=" + rs.getString("ISSTANDFORWORK") + "&standForUserId=" + rs.getString("STANDFORUSERID") + "&standForUserName=" + rs.getString("STANDFORUSERNAME") + "&workStatus=" + rs.getString("WORKSTATUS");
            result.add(str);
          } 
          rs.close();
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return result;
  }
  
  public Integer transWorkflow(Map dealwithPara, String[] para, String[] transactUser, String needPassRound, String[] passRoundUser) throws Exception {
    Integer result = Integer.valueOf("0");
    DataSourceBase dsb = new DataSourceBase();
    DataSource ds = dsb.getDataSource();
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      String domainId = "0";
      ResultSet rs = stmt.executeQuery("SELECT DISTINCT DOMAIN_ID FROM JSF_P_ACTIVITY WHERE WF_ACTIVITY_ID=" + dealwithPara.get("curActivityId"));
      if (rs.next())
        domainId = rs.getString(1); 
      rs.close();
      rs = stmt.executeQuery("SELECT WF_DEALWITH_ID FROM JSDB.JSF_DEALWITH WHERE DATABASETABLE_ID=" + dealwithPara.get("tableId") + " AND DATABASERECORD_ID=" + dealwithPara.get("recordId") + " AND ACTIVITY_ID=" + dealwithPara.get("curActivityId") + " AND CURSTEPCOUNT=" + dealwithPara.get("stepCount"));
      String wf_dealwith_id = "";
      if (rs.next())
        wf_dealwith_id = rs.getString(1); 
      rs.close();
      if (wf_dealwith_id.equals("")) {
        wf_dealwith_id = getTableId2();
        int curActivityStatus = 0;
        if (dealwithPara.get("nextActivityName") != null && dealwithPara.get("nextActivityName").toString().equals("-1"))
          curActivityStatus = 1; 
        String subProcWorkId = "0", str1 = "1";
        if (dealwithPara.get("activityClass") != null)
          str1 = dealwithPara.get("activityClass").toString(); 
        if (dealwithPara.get("activityClass") != null && dealwithPara.get("subProcWorkId") != null && !"".equals(dealwithPara.get("subProcWorkId"))) {
          subProcWorkId = dealwithPara.get("subProcWorkId").toString();
          if (dealwithPara.get("activityClass").toString().equals("0") && dealwithPara.get("subProcWorkId").toString().equals("0")) {
            rs = stmt.executeQuery("SELECT SUBPROCWORKID FROM JSDB.JSF_DEALWITH WHERE DATABASETABLE_ID=" + dealwithPara.get("tableId") + " AND DATABASERECORD_ID=" + dealwithPara.get("recordId"));
            if (rs.next())
              subProcWorkId = rs.getString("subProcWorkId"); 
            rs.close();
          } 
        } 
        stmt.execute("INSERT INTO JSDB.JSF_DEALWITH (WF_DEALWITH_ID,DATABASETABLE_ID,DATABASERECORD_ID,ACTIVITYNAME,ACTIVITY_ID,NEXTACTIVITYNAME,NEXTACTIVITYID,CURACTIVITYSTATUS,CURSTEPCOUNT,ACTIVITYCLASS,SUBPROCWORKID,DOMAIN_ID) VALUES (" + 
            wf_dealwith_id + "," + dealwithPara.get("tableId") + "," + dealwithPara.get("recordId") + ",'" + dealwithPara.get("curActivityName") + "'," + dealwithPara.get("curActivityId") + ",'" + dealwithPara.get("nextActivityName") + "'," + dealwithPara.get("nextActivityId") + "," + curActivityStatus + "," + dealwithPara.get("stepCount") + "," + str1 + "," + subProcWorkId + "," + domainId + ")");
      } else if (!dealwithPara.get("nextActivityId").toString().equals("-1") && 
        !dealwithPara.get("curActivityId").toString().equals("0")) {
        stmt.execute("UPDATE JSDB.JSF_DEALWITH SET NEXTACTIVITYID=" + dealwithPara.get("nextActivityId") + ",NEXTACTIVITYNAME='" + dealwithPara.get("nextActivityName") + "' " + 
            "WHERE WF_DEALWITH_ID=" + wf_dealwith_id);
      } 
      Date now = new Date();
      String commentField = "";
      if (dealwithPara.get("commentField") != null)
        commentField = dealwithPara.get("commentField").toString(); 
      String rangeName = (dealwithPara.get("userScope") == null) ? "" : dealwithPara.get("userScope").toString();
      String rangeIdStr = (dealwithPara.get("scopeId") == null) ? "" : dealwithPara.get("scopeId").toString();
      if (!dealwithPara.get("isStandForWork").toString().equals("1")) {
        String wf_dealwithcomment_id = getTableId2();
        String str1 = "";
        String str2 = SystemCommon.getDatabaseType();
        if (str2.indexOf("mysql") >= 0) {
          str1 = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,COMMENTFIELD,DOMAIN_ID,RANGENAME,RANGEIDSTR,signcomment) VALUES (" + 
            wf_dealwithcomment_id + "," + dealwithPara.get("userId") + ",'" + dealwithPara.get("comment") + "','" + now.toLocaleString() + "'," + wf_dealwith_id + ",0,'','" + commentField + "'," + domainId + ",'" + rangeName + "','" + rangeIdStr + "','" + dealwithPara.get("signcomment") + "')";
        } else {
          str1 = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,COMMENTFIELD,DOMAIN_ID,RANGENAME,RANGEIDSTR,signcomment) VALUES (" + 
            wf_dealwithcomment_id + "," + dealwithPara.get("userId") + ",'" + dealwithPara.get("comment") + "',JSDB.FN_STRTODATE('" + now.toLocaleString() + "','')," + wf_dealwith_id + ",0,'','" + commentField + "'," + domainId + ",'" + rangeName + "','" + rangeIdStr + "','" + dealwithPara.get("signcomment") + "')";
        } 
        stmt.execute(str1);
      } else {
        String wf_dealwithcomment_id = getTableId2();
        String empName = "";
        rs = stmt.executeQuery("SELECT EMPNAME FROM JSDB.ORG_EMPLOYEE WHERE EMP_ID=" + dealwithPara.get("userId"));
        if (rs.next())
          empName = rs.getString(1); 
        String str1 = "";
        String str2 = SystemCommon.getDatabaseType();
        if (str2.indexOf("mysql") >= 0) {
          str1 = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,ISSTANDFORCOMM,COMMENTFIELD,DOMAIN_ID,RANGENAME,RANGEIDSTR,signcomment) VALUES (" + 
            wf_dealwithcomment_id + "," + dealwithPara.get("standForUserId") + ",'" + dealwithPara.get("comment") + "','" + now.toLocaleString() + "'," + wf_dealwith_id + "," + dealwithPara.get("userId") + ",'" + empName + "',1,'" + commentField + "'," + domainId + ",'" + rangeName + "','" + rangeIdStr + "','" + dealwithPara.get("signcomment") + "')";
        } else {
          str1 = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,ISSTANDFORCOMM,COMMENTFIELD,DOMAIN_ID,RANGENAME,RANGEIDSTR,signcomment) VALUES (" + 
            wf_dealwithcomment_id + "," + dealwithPara.get("standForUserId") + ",'" + dealwithPara.get("comment") + "',JSDB.FN_STRTODATE('" + now.toLocaleString() + "','')," + wf_dealwith_id + "," + dealwithPara.get("userId") + ",'" + empName + "',1,'" + commentField + "'," + domainId + ",'" + rangeName + "','" + rangeIdStr + "','" + dealwithPara.get("signcomment") + "')";
        } 
        stmt.execute(str1);
      } 
      List<String[]> toUserList = null;
      if (para[11].equals("1"))
        toUserList = getStandForUser(transactUser); 
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      String nextActivityName = para[0];
      String nextActivityId = para[1];
      String curWorkId = para[2];
      String deadLineTime = para[3];
      String pressTime = para[4];
      String activityClass = para[16];
      String subProcType = para[17];
      rs = stmt.executeQuery("SELECT DISTINCT DOMAIN_ID FROM JSF_P_ACTIVITY WHERE WF_ACTIVITY_ID=" + nextActivityId);
      if (rs.next())
        domainId = rs.getString(1); 
      rs.close();
      String sql = "select workFileType,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workProcess_id,workTable_id,workRecord_id,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg from JSDB.JSF_WORK where wf_work_id=" + 

        
        curWorkId;
      now = new Date();
      rs = stmt.executeQuery(sql);
      String workFileType = "";
      String workSubmitPerson = "";
      String workSubmitTime = "";
      String wf_submitEmployee_id = "";
      String workProcess_id = "";
      String workTable_id = "";
      String workRecord_id = "";
      String isSubProcWork = "", pareProcActiId = "", pareStepCount = "";
      String pareTableId = "", pareRecordId = "", pareProcNextActiId = "", submitOrg = "";
      if (rs.next()) {
        workFileType = rs.getString("workFileType");
        workSubmitPerson = rs.getString("workSubmitPerson");
        workSubmitTime = rs.getString("workSubmitTime");
        if (workSubmitTime.indexOf(".") > 0)
          workSubmitTime = workSubmitTime.substring(0, workSubmitTime.indexOf(".")); 
        wf_submitEmployee_id = rs.getString("wf_submitEmployee_id");
        workProcess_id = rs.getString("workProcess_id");
        workTable_id = rs.getString("workTable_id");
        workRecord_id = rs.getString("workRecord_id");
        isSubProcWork = rs.getString("isSubProcWork");
        pareProcActiId = rs.getString("pareProcActiId");
        pareStepCount = rs.getString("pareStepCount");
        pareTableId = rs.getString("pareTableId");
        pareRecordId = rs.getString("pareRecordId");
        pareProcNextActiId = rs.getString("pareProcNextActiId");
        submitOrg = rs.getString("submitOrg");
      } 
      rs.close();
      StringBuffer workUser = new StringBuffer();
      for (int i = 0; i < transactUser.length; i++)
        workUser.append("$" + transactUser[i] + "$"); 
      stmt.execute("delete from JSDB.JSF_WORK where workstatus = 101 and worktable_id = " + workTable_id + 
          " and workrecord_id = " + workRecord_id + " and wf_curemployee_id = " + 
          para[13] + " and workprocess_id=" + workProcess_id);
      stmt.execute("delete from JSDB.JSF_WORK where workTable_Id=" + workTable_id + " and workRecord_id=" + 
          workRecord_id + " and workStepCount=" + para[7] + " and standForUserId=" + 
          para[13]);
      String remindFieldValue = para[8];
      int k = 0;
      if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
        k = (String.valueOf(remindFieldValue) + workFileType + "您已办理完毕！").length() - 100;
      } else {
        k = (String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "您已办理完毕！").length() - 100;
      } 
      if (k > 0)
        remindFieldValue.substring(0, remindFieldValue.length() - k); 
      String standTitle = "";
      String workTitle = "";
      if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
        workTitle = String.valueOf(remindFieldValue) + workFileType + "您已办理完毕！";
      } else {
        workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "您已办理完毕！";
      } 
      if (!para[18].equals(""))
        workTitle = para[18]; 
      if (para[12].equals("1")) {
        rs = stmt.executeQuery("SELECT EMPNAME FROM JSDB.ORG_EMPLOYEE WHERE EMP_ID=" + para[13]);
        String myTmp = "";
        if (rs.next())
          myTmp = rs.getString(1); 
        rs.close();
        myTmp = String.valueOf(workTitle) + "（" + myTmp + "代办）";
        if (myTmp.length() > 100)
          myTmp = myTmp.substring(0, 99); 
        stmt.execute("UPDATE JSDB.JSF_WORK SET WORKSTATUS=101,WORKTITLE='" + myTmp + "',WORKALLOWCANCEL=" + para[6] + ",WORKUSER='" + workUser.toString() + "' where workTable_Id=" + workTable_id + " and workRecord_id=" + 
            workRecord_id + " and workStepCount=" + para[7] + " and wf_curemployee_id=" + 
            para[14]);
        standTitle = "（代" + para[15] + "办理）";
      } 
      workTitle = String.valueOf(workTitle) + standTitle;
      if (workTitle.length() > 100)
        workTitle = workTitle.substring(0, 99); 
      stmt.execute("update JSDB.JSF_WORK set workstatus = 101, workTitle = '" + workTitle + "', " + 
          "workAllowCancel=" + para[6] + ", workUser = '" + workUser.toString() + "' " + 
          "where wf_work_id = " + curWorkId);
      int mycount = 0;
      if (para[9].equals("1")) {
        sql = " select count(wf_work_id) from JSDB.JSF_WORK where worktable_id = " + workTable_id + 
          " and workRecord_id = " + workRecord_id + " and workStepCount = " + para[7] + 
          " and workstatus = 0";
        rs = stmt.executeQuery(sql);
        if (rs.next())
          mycount = rs.getInt(1); 
        rs.close();
      } 
      int workListControl = 0;
      if (mycount == 0)
        if (activityClass.equals("1") || (activityClass.equals("0") && subProcType.equals("0")) || activityClass.equals("3")) {
          stmt.execute("update JSDB.JSF_WORK set workListControl = 1 where worktable_id = " + workTable_id + 
              " and workrecord_id = " + workRecord_id + " and workActivity = " + nextActivityId);
          stmt.execute("update JSDB.JSF_WORK set workCurStep='" + nextActivityName + "' where worktable_id=" + workTable_id + 
              " and workrecord_id = " + workRecord_id);
          workListControl = 1;
          stmt.execute("update JSDB.JSF_DEALWITH set curActivityStatus = 1 where databasetable_id = " + 
              workTable_id + " and databaserecord_id = " + workRecord_id + " and curStepCount = " + 
              para[7]);
          if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
            workTitle = String.valueOf(remindFieldValue) + workFileType + "已办理完毕！";
          } else {
            workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "已办理完毕！";
          } 
          if (!para[18].equals(""))
            workTitle = para[18]; 
          if (workTitle.length() > 100)
            workTitle = workTitle.substring(0, 99); 
        }  
      if (para[11].equals("1")) {
        String[] standForUser = (String[])null;
        for (int j = 0; j < toUserList.size(); j++) {
          standForUser = toUserList.get(j);
          rs = stmt.executeQuery("select count(wf_work_id) from JSDB.JSF_WORK where wf_curemployee_id=" + standForUser[0] + " and worktable_id=" + 
              workTable_id + " and workrecord_id=" + workRecord_id + " and workreadmarker=0 and workActivity=" + 
              nextActivityId + " and workStepCount = " + (Integer.parseInt(para[7]) + 1));
          rs.next();
          int sameWorkCount = rs.getInt(1);
          rs.close();
          if (sameWorkCount == 0) {
            if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
              workTitle = String.valueOf(remindFieldValue) + workFileType;
            } else {
              workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "等待您处理！";
            } 
            if (!para[18].equals(""))
              workTitle = para[18]; 
            if (workTitle.length() > 100)
              workTitle = workTitle.substring(0, 99); 
            String workId = getTableId2();
            if (databaseType.indexOf("mysql") >= 0) {
              tmpSql = "insert into JSDB.JSF_WORK ( wf_work_id,wf_curEmployee_id,workStatus,workFileType,workCurStep,workTitle,workLeftLinkFile,workMainLinkFile,workListControl,workActivity,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workReadMarker,workType,workProcess_id,worktable_id,workrecord_id,workDeadLine,workPressTime,workCreateDate,workStepCount,isStandForWork,standForUserId,standForUserName,initActivity,initStepCount,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,domain_id,emergence) values ( " + 




                
                workId + "," + standForUser[0] + ",0,'" + workFileType + "','" + nextActivityName + 
                "','" + workTitle + "'," + "'','" + para[10] + "'," + workListControl + "," + nextActivityId + 
                ",'" + workSubmitPerson + "','" + workSubmitTime + "'," + 
                wf_submitEmployee_id + ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                "," + deadLineTime + "," + pressTime + ",'" + now.toLocaleString() + 
                "'," + (Integer.parseInt(para[7]) + 1) + ",0,0,''," + nextActivityId + 
                "," + (Integer.parseInt(para[7]) + 1) + "," + isSubProcWork + "," + pareProcActiId + "," + 
                pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + 
                submitOrg + "'," + domainId + "," + para[19] + ")";
            } else {
              tmpSql = "insert into JSDB.JSF_WORK ( wf_work_id,wf_curEmployee_id,workStatus,workFileType,workCurStep,workTitle,workLeftLinkFile,workMainLinkFile,workListControl,workActivity,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workReadMarker,workType,workProcess_id,worktable_id,workrecord_id,workDeadLine,workPressTime,workCreateDate,workStepCount,isStandForWork,standForUserId,standForUserName,initActivity,initStepCount,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,domain_id,emergence) values ( " + 




                
                workId + "," + standForUser[0] + ",0,'" + workFileType + "','" + nextActivityName + 
                "','" + workTitle + "'," + "'','" + para[10] + "'," + workListControl + "," + nextActivityId + 
                ",'" + workSubmitPerson + "',JSDB.FN_STRTODATE('" + workSubmitTime + "','')," + 
                wf_submitEmployee_id + ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                "," + deadLineTime + "," + pressTime + ",JSDB.FN_STRTODATE('" + now.toLocaleString() + 
                "','')," + (Integer.parseInt(para[7]) + 1) + ",0,0,''," + nextActivityId + 
                "," + (Integer.parseInt(para[7]) + 1) + "," + isSubProcWork + "," + pareProcActiId + "," + 
                pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + 
                submitOrg + "'," + domainId + "," + para[19] + ")";
            } 
            stmt.execute(tmpSql);
          } 
          if (!standForUser[2].equals("")) {
            rs = stmt.executeQuery("select count(wf_work_id) from JSDB.JSF_WORK where wf_curemployee_id=" + standForUser[2] + " and worktable_id=" + 
                workTable_id + " and workrecord_id=" + workRecord_id + " and workreadmarker=0 and workActivity=" + 
                nextActivityId + " and workStepCount = " + (Integer.parseInt(para[7]) + 1) + " and standForUserId=" + 
                standForUser[0]);
            rs.next();
            sameWorkCount = rs.getInt(1);
            rs.close();
            if (sameWorkCount == 0) {
              String workId = getTableId2();
              if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
                workTitle = String.valueOf(remindFieldValue) + workFileType + "（代" + standForUser[1] + "办理）";
              } else {
                workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "等待您处理！" + "（代" + standForUser[1] + "办理）";
              } 
              if (!para[18].equals(""))
                workTitle = para[18]; 
              if (workTitle.length() > 100)
                workTitle = workTitle.substring(0, 99); 
              if (databaseType.indexOf("mysql") >= 0) {
                tmpSql = "insert into JSDB.JSF_WORK ( wf_work_id,wf_curEmployee_id,workStatus,workFileType,workCurStep,workTitle,workLeftLinkFile,workMainLinkFile,workListControl,workActivity,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workReadMarker,workType,workProcess_id,worktable_id,workrecord_id,workDeadLine,workPressTime,workCreateDate,workStepCount,isStandForWork,standForUserId,standForUserName,initActivity,initStepCount,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,domain_id,emergence) values ( " + 




                  
                  workId + "," + standForUser[2] + ",0,'" + workFileType + "','" + nextActivityName + "','" + workTitle + "'," + 
                  "'','" + para[10] + "'," + workListControl + "," + nextActivityId + ",'" + workSubmitPerson + 
                  "','" + workSubmitTime + "'," + wf_submitEmployee_id + ",0,1," + 
                  workProcess_id + "," + workTable_id + "," + workRecord_id + "," + deadLineTime + "," + pressTime + 
                  ",'" + now.toLocaleString() + "'," + (
                  Integer.parseInt(para[7]) + 1) + ",1," + standForUser[0] + ",'" + standForUser[1] + "'," + 
                  nextActivityId + "," + (Integer.parseInt(para[7]) + 1) + "," + isSubProcWork + "," + pareProcActiId + "," + 
                  pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + domainId + "," + para[19] + ")";
              } else {
                tmpSql = "insert into JSDB.JSF_WORK ( wf_work_id,wf_curEmployee_id,workStatus,workFileType,workCurStep,workTitle,workLeftLinkFile,workMainLinkFile,workListControl,workActivity,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workReadMarker,workType,workProcess_id,worktable_id,workrecord_id,workDeadLine,workPressTime,workCreateDate,workStepCount,isStandForWork,standForUserId,standForUserName,initActivity,initStepCount,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,domain_id,emergence) values ( " + 




                  
                  workId + "," + standForUser[2] + ",0,'" + workFileType + "','" + nextActivityName + "','" + workTitle + "'," + 
                  "'','" + para[10] + "'," + workListControl + "," + nextActivityId + ",'" + workSubmitPerson + 
                  "',JSDB.FN_STRTODATE('" + workSubmitTime + "','')," + wf_submitEmployee_id + ",0,1," + 
                  workProcess_id + "," + workTable_id + "," + workRecord_id + "," + deadLineTime + "," + pressTime + 
                  ",JSDB.FN_STRTODATE('" + now.toLocaleString() + "','')," + (
                  Integer.parseInt(para[7]) + 1) + ",1," + standForUser[0] + ",'" + standForUser[1] + "'," + 
                  nextActivityId + "," + (Integer.parseInt(para[7]) + 1) + "," + isSubProcWork + "," + pareProcActiId + "," + 
                  pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + domainId + "," + para[19] + ")";
              } 
              stmt.execute(tmpSql);
            } 
          } 
        } 
      } else {
        String tmpUser = "";
        String insertUser = "";
        for (int j = 0; j < transactUser.length; j++) {
          tmpUser = transactUser[j];
          if (insertUser.indexOf(String.valueOf(tmpUser) + ",") < 0) {
            insertUser = String.valueOf(insertUser) + tmpUser + ",";
            if (tmpUser.startsWith("$"))
              tmpUser = tmpUser.substring(1); 
            if (tmpUser.endsWith("$"))
              tmpUser = tmpUser.substring(0, tmpUser.length() - 1); 
            rs = stmt.executeQuery("select count(wf_work_id) from JSDB.JSF_WORK where wf_curemployee_id = " + 
                tmpUser + " and worktable_id = " + workTable_id + 
                " and workrecord_id = " + workRecord_id + 
                " and workreadmarker = 0 and workActivity = " + nextActivityId + 
                " and workStepCount = " + (Integer.parseInt(para[7]) + 1));
            rs.next();
            int sameWorkCount = rs.getInt(1);
            rs.close();
            if (sameWorkCount == 0) {
              String workId = getTableId2();
              if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
                workTitle = String.valueOf(remindFieldValue) + workFileType;
              } else {
                workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "等待您处理！";
              } 
              if (!para[18].equals(""))
                workTitle = para[18]; 
              if (databaseType.indexOf("mysql") >= 0) {
                tmpSql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType,  workCurStep, workTitle, workLeftLinkFile, workMainLinkFile,  workListControl, workActivity, workSubmitPerson, workSubmitTime,  wf_submitEmployee_id, workReadMarker, workType, workProcess_id,  worktable_id, workrecord_id, workDeadLine, workPressTime,  workCreateDate, workStepCount, initActivity, initStepCount ,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,domain_id,emergence,isStandForWork ) values ( " + 







                  
                  workId + ", " + tmpUser + ",0,'" + workFileType + "','" + 
                  nextActivityName + "','" + workTitle + "','','" + para[10] + "'," + workListControl + "," + 
                  nextActivityId + ",'" + workSubmitPerson + "','" + 
                  workSubmitTime + "'," + wf_submitEmployee_id + 
                  ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                  "," + deadLineTime + "," + pressTime + ", '" + 
                  now.toLocaleString() + "', " + (
                  Integer.parseInt(para[7]) + 1) + "," + nextActivityId + "," + (Integer.parseInt(para[7]) + 1) + "," + isSubProcWork + "," + pareProcActiId + "," + 
                  pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + domainId + "," + para[19] + ",0)";
              } else {
                tmpSql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType,  workCurStep, workTitle, workLeftLinkFile, workMainLinkFile,  workListControl, workActivity, workSubmitPerson, workSubmitTime,  wf_submitEmployee_id, workReadMarker, workType, workProcess_id,  worktable_id, workrecord_id, workDeadLine, workPressTime,  workCreateDate, workStepCount, initActivity, initStepCount ,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,domain_id,emergence,isStandForWork ) values ( " + 







                  
                  workId + ", " + tmpUser + ",0,'" + workFileType + "','" + 
                  nextActivityName + "','" + workTitle + "','','" + para[10] + "'," + workListControl + "," + 
                  nextActivityId + ",'" + workSubmitPerson + "',JSDB.FN_STRTODATE('" + 
                  workSubmitTime + "','')," + wf_submitEmployee_id + 
                  ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                  "," + deadLineTime + "," + pressTime + ", JSDB.FN_STRTODATE('" + 
                  now.toLocaleString() + "',''), " + (
                  Integer.parseInt(para[7]) + 1) + "," + nextActivityId + "," + (Integer.parseInt(para[7]) + 1) + "," + isSubProcWork + "," + pareProcActiId + "," + 
                  pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + domainId + "," + para[19] + ",0)";
              } 
              stmt.execute(tmpSql);
            } 
          } 
        } 
      } 
      if (!needPassRound.equals("") && passRoundUser != null) {
        boolean flag = true;
        String tmpUser = "";
        String insertUser = "";
        for (int m = 0; m < passRoundUser.length; m++) {
          if (insertUser.indexOf(String.valueOf(passRoundUser[m]) + ",") < 0) {
            insertUser = String.valueOf(insertUser) + passRoundUser[m] + ",";
            tmpUser = passRoundUser[m];
            if (!tmpUser.equals("")) {
              if (tmpUser.startsWith("$"))
                tmpUser = tmpUser.substring(1); 
              if (tmpUser.endsWith("$"))
                tmpUser = tmpUser.substring(0, tmpUser.length() - 1); 
              for (int j = 0; j < transactUser.length && 
                !tmpUser.equals(transactUser[j]); j++);
              if (flag) {
                String workId = getTableId2();
                if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
                  workTitle = String.valueOf(remindFieldValue) + workFileType + "等待您的审阅！";
                } else {
                  workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "等待您的审阅！";
                } 
                if (!para[18].equals(""))
                  workTitle = para[18]; 
                if (databaseType.indexOf("mysql") >= 0) {
                  tmpSql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType,  workCurStep, workTitle, workLeftLinkFile, workMainLinkFile,  workListControl, workActivity, workSubmitPerson, workSubmitTime,  wf_submitEmployee_id, workReadMarker, workType, workProcess_id,  worktable_id, workrecord_id, workDeadLine, workPressTime,  workCreateDate, workStepCount, initActivity, initStepCount, isSubProcWork, pareProcActiId,  pareStepCount, pareTableId, pareRecordId, pareProcNextActiId, submitOrg, domain_id ,emergence,isStandForWork ) values ( " + 







                    
                    workId + ", " + tmpUser + ",2,'" + workFileType + "','" + 
                    nextActivityName + "','" + workTitle + "','','" + para[10] + "'," + workListControl + "," + 
                    nextActivityId + ",'" + workSubmitPerson + "','" + 
                    workSubmitTime + "'," + wf_submitEmployee_id + 
                    ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                    "," + deadLineTime + "," + pressTime + ", '" + 
                    now.toLocaleString() + "', " + (
                    Integer.parseInt(para[7]) + 1) + "," + nextActivityId + ", " + (Integer.parseInt(para[7]) + 1) + "," + isSubProcWork + "," + pareProcActiId + "," + 
                    pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + domainId + "," + para[19] + ",0)";
                } else {
                  tmpSql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType,  workCurStep, workTitle, workLeftLinkFile, workMainLinkFile,  workListControl, workActivity, workSubmitPerson, workSubmitTime,  wf_submitEmployee_id, workReadMarker, workType, workProcess_id,  worktable_id, workrecord_id, workDeadLine, workPressTime,  workCreateDate, workStepCount, initActivity, initStepCount, isSubProcWork, pareProcActiId,  pareStepCount, pareTableId, pareRecordId, pareProcNextActiId, submitOrg, domain_id,emergence ,isStandForWork ) values ( " + 







                    
                    workId + ", " + tmpUser + ",2,'" + workFileType + "','" + 
                    nextActivityName + "','" + workTitle + "','','" + para[10] + "'," + workListControl + "," + 
                    nextActivityId + ",'" + workSubmitPerson + "',JSDB.FN_STRTODATE('" + 
                    workSubmitTime + "','')," + wf_submitEmployee_id + 
                    ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                    "," + deadLineTime + "," + pressTime + ", JSDB.FN_STRTODATE('" + 
                    now.toLocaleString() + "','yyyy-mm-dd hh24:mi:ss'), " + (
                    Integer.parseInt(para[7]) + 1) + "," + nextActivityId + ", " + (Integer.parseInt(para[7]) + 1) + "," + isSubProcWork + "," + pareProcActiId + "," + 
                    pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + domainId + "," + para[19] + ",0)";
                } 
                stmt.execute(tmpSql);
              } 
            } 
          } 
        } 
      } 
      if (mycount == 0)
        stmt.execute(" update JSDB.JSF_WORK set workcurstep = '" + nextActivityName + "', workactivity = " + 
            nextActivityId + " where worktable_id = " + workTable_id + " and " + 
            " workrecord_id = " + workRecord_id); 
      if (mycount == 0 && activityClass.equals("0") && subProcType.equals("0"))
        stmt.execute(" update JSDB.JSF_WORK set workListControl = 1 where workTable_id = " + 
            workTable_id + " and workRecord_Id = " + workRecord_id + " and workStepCount = " + (
            Integer.parseInt(para[7]) + 1)); 
      if (para[9].equals("0") || (activityClass.equals("0") && subProcType.equals("0"))) {
        if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
          workTitle = String.valueOf(remindFieldValue) + workFileType + "已办理完毕！";
        } else {
          workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "已办理完毕！";
        } 
        if (!para[18].equals(""))
          workTitle = para[18]; 
        stmt.execute("update JSDB.JSF_WORK set workStatus = 101, workTitle = '" + workTitle + "',workAllowCancel = 0 where workStatus = 0 and workTable_id = " + 
            workTable_id + " and workRecord_id = " + workRecord_id + 
            " and workStepCount = " + para[7] + " and wf_work_id <> " + curWorkId);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      result = Integer.valueOf("-1");
      throw e;
    } finally {
      try {
        stmt.close();
        conn.close();
      } catch (SQLException sQLException) {}
    } 
    return result;
  }
  
  public Integer transWorkflowButton(Map dealwithPara, String[] para, String[] transactUser, String needPassRound, String[] passRoundUser) throws Exception {
    Integer result = Integer.valueOf("0");
    List<MessagesVO> msgList = new ArrayList();
    MessagesVO msgVO = new MessagesVO();
    DataSourceBase dsb = new DataSourceBase();
    DataSource ds = dsb.getDataSource();
    Connection conn = ds.getConnection();
    Statement stmt = null;
    boolean isCommit = conn.getAutoCommit();
    conn.setAutoCommit(false);
    try {
      String commentCurActivity, commentCurActivityName;
      stmt = conn.createStatement();
      String databaseType = SystemCommon.getDatabaseType();
      String tmpSql = "";
      Date now = new Date();
      String relproject = (String)dealwithPara.get("relproject");
      String dealwithUserId = dealwithPara.get("userId").toString();
      String nextActivityName = para[0];
      String nextActivityId = para[1];
      String curWorkId = para[2];
      String deadLineTime = para[3];
      String pressTime = para[4];
      Date workDeadlineDate = now;
      Date workDeadlinePressDate = now;
      if (!"-1".equals(deadLineTime)) {
        workDeadlineDate = FlowDeadline.getDeadline(now, Long.parseLong(deadLineTime));
        workDeadlinePressDate = new Date(workDeadlineDate.getTime() - Long.parseLong(pressTime) * 1000L);
      } 
      String activityClass = para[16];
      String subProcType = para[17];
      String workFileType = "";
      String workSubmitPerson = "";
      String workSubmitTime = "";
      String wf_submitEmployee_id = "";
      String workProcess_id = "";
      String workTable_id = "";
      String workRecord_id = "";
      String isSubProcWork = "", pareProcActiId = "", pareStepCount = "";
      String pareTableId = "", pareRecordId = "", pareProcNextActiId = "", submitOrg = "";
      String processDeadlineDate = "null";
      String curTransactType = "1";
      String nextTransactType = para[20];
      String dealTips = para[21];
      int mainWorkStepCount = Integer.parseInt(para[7]) + 1;
      String mainWorkCurActivity = "0", mainWorkCurActivityName = "";
      int workIsParallel = 0;
      String workParallelId = "0";
      int workParallelStep = 0;
      String workParallelCurActId = "0";
      String workParallelFromWork = "0";
      int newWorkIsParallel = 0;
      String newWorkParallelId = "0";
      int newWorkParallelStep = 0;
      String newWorkParallelCurActId = "0";
      String newWorkParallelFromWork = "0";
      int nextActivityIsDivide = 0;
      int nextActivityIsGather = 0;
      boolean nextActivityIsFirstDivide = false;
      boolean nextActivityIsGanther = false;
      boolean nextActivityIsMiddleDivide = false;
      boolean curActivityIsDivide = false;
      int currentWorkStatus = 0;
      String sql = "select workstatus,workFileType,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workProcess_id,workTable_id,workRecord_id,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,transactType,processDeadlineDate,is_parallel,parallel_id,parallel_step,workcurstep,workactivity,parallel_curactid,parallel_fromwork from JSDB.JSF_WORK where wf_work_id=" + 


        
        curWorkId;
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next()) {
        currentWorkStatus = rs.getInt(1);
        if (currentWorkStatus != 0) {
          result = Integer.valueOf("1");
          rs.close();
          stmt.close();
          conn.setAutoCommit(isCommit);
          conn.close();
          return result;
        } 
        workFileType = rs.getString("workFileType");
        workSubmitPerson = rs.getString("workSubmitPerson");
        workSubmitTime = rs.getString("workSubmitTime");
        if (workSubmitTime.indexOf(".") > 0)
          workSubmitTime = workSubmitTime.substring(0, workSubmitTime.indexOf(".")); 
        wf_submitEmployee_id = rs.getString("wf_submitEmployee_id");
        workProcess_id = rs.getString("workProcess_id");
        workTable_id = rs.getString("workTable_id");
        workRecord_id = rs.getString("workRecord_id");
        isSubProcWork = rs.getString("isSubProcWork");
        pareProcActiId = rs.getString("pareProcActiId");
        pareStepCount = rs.getString("pareStepCount");
        pareTableId = rs.getString("pareTableId");
        pareRecordId = rs.getString("pareRecordId");
        pareProcNextActiId = rs.getString("pareProcNextActiId");
        submitOrg = rs.getString("submitOrg");
        curTransactType = (rs.getString("transactType") == null || "".equals(rs.getString("transactType"))) ? "1" : rs.getString("transactType");
        if (databaseType.indexOf("mysql") >= 0) {
          processDeadlineDate = (rs.getDate("processDeadlineDate") == null) ? "null" : ("'" + rs.getTimestamp("processDeadlineDate").toLocaleString() + "'");
        } else {
          processDeadlineDate = (rs.getDate("processDeadlineDate") == null) ? "null" : ("JSDB.FN_STRTODATE('" + rs.getTimestamp("processDeadlineDate").toLocaleString() + "','L')");
        } 
        workIsParallel = rs.getInt("is_parallel");
        workParallelId = rs.getString("parallel_id");
        workParallelStep = rs.getInt("parallel_step");
        mainWorkCurActivityName = rs.getString("workcurstep");
        mainWorkCurActivity = rs.getString("workactivity");
        workParallelCurActId = rs.getString("parallel_curactid");
        workParallelFromWork = rs.getString("parallel_fromwork");
        newWorkParallelFromWork = workParallelFromWork;
      } 
      rs.close();
      sql = "select isdivide,isgather from jsf_activity where wf_activity_id=" + nextActivityId;
      rs = stmt.executeQuery(sql);
      if (rs.next()) {
        nextActivityIsDivide = rs.getInt(1);
        nextActivityIsGather = rs.getInt(2);
      } 
      if (transactUser.length == 1 && workIsParallel == 0)
        nextActivityIsDivide = 0; 
      if (workIsParallel == 0 && nextActivityIsDivide == 1) {
        nextActivityIsFirstDivide = true;
        newWorkParallelFromWork = curWorkId;
      } 
      if (workIsParallel == 1) {
        curActivityIsDivide = true;
        if (nextActivityIsGather == 1) {
          nextActivityIsGanther = true;
          newWorkParallelFromWork = "0";
        } else {
          nextActivityIsMiddleDivide = true;
        } 
      } 
      String submitEmployeeOrgId = getUserOrgId(wf_submitEmployee_id);
      String submitPersonOrgIdString = submitEmployeeOrgId;
      List<E> toUserList = null;
      if (para[11].equals("1"))
        toUserList = getStandForUserByProcessAndOrgWithConn(transactUser, workProcess_id, submitPersonOrgIdString, conn, stmt); 
      StringBuffer workUser = new StringBuffer();
      for (int i = 0; i < transactUser.length; i++)
        workUser.append("$" + transactUser[i] + "$"); 
      if (para[11].equals("1")) {
        if (toUserList == null || toUserList.size() < 1 || "".equals(toUserList.get(0).toString())) {
          conn.commit();
          conn.setAutoCommit(isCommit);
          stmt.close();
          conn.close();
          return Integer.valueOf(-1);
        } 
      } else if (transactUser.length < 1) {
        conn.commit();
        conn.setAutoCommit(isCommit);
        stmt.close();
        conn.close();
        return Integer.valueOf(-1);
      } 
      String domainId = "0";
      if (curActivityIsDivide) {
        commentCurActivity = workParallelCurActId;
        rs = stmt.executeQuery("select activityname from jsf_activity where wf_activity_id=" + commentCurActivity);
        rs.next();
        commentCurActivityName = rs.getString(1);
        rs.close();
      } else {
        commentCurActivity = dealwithPara.get("curActivityId").toString();
        commentCurActivityName = dealwithPara.get("curActivityName").toString();
      } 
      rs = stmt.executeQuery("SELECT WF_DEALWITH_ID FROM JSDB.JSF_DEALWITH WHERE DATABASETABLE_ID=" + dealwithPara.get("tableId") + " AND DATABASERECORD_ID=" + dealwithPara.get("recordId") + " AND ACTIVITY_ID=" + commentCurActivity + " AND CURSTEPCOUNT=" + dealwithPara.get("stepCount"));
      String wf_dealwith_id = "";
      if (rs.next())
        wf_dealwith_id = rs.getString(1); 
      rs.close();
      if (wf_dealwith_id.equals("")) {
        wf_dealwith_id = getTableId2();
        int curActivityStatus = 0;
        if (dealwithPara.get("nextActivityName") != null && dealwithPara.get("nextActivityName").toString().equals("-1"))
          curActivityStatus = 1; 
        String subProcWorkId = "0", activityClassTemp = "1";
        if (dealwithPara.get("activityClass") != null)
          activityClassTemp = dealwithPara.get("activityClass").toString(); 
        if (dealwithPara.get("activityClass") != null && dealwithPara.get("subProcWorkId") != null && !"".equals(dealwithPara.get("subProcWorkId"))) {
          subProcWorkId = dealwithPara.get("subProcWorkId").toString();
          if (dealwithPara.get("activityClass").toString().equals("0") && dealwithPara.get("subProcWorkId").toString().equals("0")) {
            rs = stmt.executeQuery("SELECT SUBPROCWORKID FROM JSDB.JSF_DEALWITH WHERE DATABASETABLE_ID=" + dealwithPara.get("tableId") + " AND DATABASERECORD_ID=" + dealwithPara.get("recordId"));
            if (rs.next())
              subProcWorkId = rs.getString("subProcWorkId"); 
            rs.close();
          } 
        } 
        stmt.execute("INSERT INTO JSDB.JSF_DEALWITH (WF_DEALWITH_ID,DATABASETABLE_ID,DATABASERECORD_ID,ACTIVITYNAME,ACTIVITY_ID,NEXTACTIVITYNAME,NEXTACTIVITYID,CURACTIVITYSTATUS,CURSTEPCOUNT,ACTIVITYCLASS,SUBPROCWORKID,DOMAIN_ID,parallel_id,parallel_step) VALUES (" + 
            wf_dealwith_id + "," + dealwithPara.get("tableId") + "," + dealwithPara.get("recordId") + ",'" + commentCurActivityName + "'," + commentCurActivity + ",'" + dealwithPara.get("nextActivityName") + "'," + dealwithPara.get("nextActivityId") + "," + curActivityStatus + "," + dealwithPara.get("stepCount") + "," + activityClassTemp + "," + subProcWorkId + "," + domainId + "," + workParallelId + "," + workParallelStep + ")");
      } else if (!dealwithPara.get("nextActivityId").toString().equals("-1") && 
        !dealwithPara.get("curActivityId").toString().equals("0")) {
        stmt.execute("UPDATE JSDB.JSF_DEALWITH SET NEXTACTIVITYID=" + dealwithPara.get("nextActivityId") + ",NEXTACTIVITYNAME='" + dealwithPara.get("nextActivityName") + "' " + 
            "WHERE WF_DEALWITH_ID=" + wf_dealwith_id);
      } 
      String commentField = "";
      if (dealwithPara.get("commentField") != null)
        commentField = dealwithPara.get("commentField").toString(); 
      String rangeName = (dealwithPara.get("userScope") == null) ? "" : dealwithPara.get("userScope").toString();
      String rangeIdStr = (dealwithPara.get("scopeId") == null) ? "" : dealwithPara.get("scopeId").toString();
      String modiCommentId = (dealwithPara.get("modiCommentId") == null) ? "" : dealwithPara.get("modiCommentId").toString();
      String orgIdString = (dealwithPara.get("orgIdString") == null) ? "" : dealwithPara.get("orgIdString").toString();
      if ("".equals(modiCommentId) || dealwithPara.get("commentField") == null || "-1".equals(dealwithPara.get("commentField")) || "".equals(dealwithPara.get("commentField"))) {
        if (!dealwithPara.get("isStandForWork").toString().equals("1")) {
          String wf_dealwithcomment_id = getTableId2();
          if (databaseType.indexOf("mysql") >= 0) {
            tmpSql = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,COMMENTFIELD,DOMAIN_ID,RANGENAME,RANGEIDSTR,signcomment,parallel_id,parallel_step,DEALWITHORG) VALUES (" + 
              wf_dealwithcomment_id + "," + 
              dealwithPara.get("userId") + ",'" + 
              dealwithPara.get("comment") + "','" + 
              now.toLocaleString() + "'," + wf_dealwith_id + 
              ",0,'','" + commentField + "'," + domainId + 
              ",'" + rangeName + "','" + rangeIdStr + "','" + dealwithPara.get("signcomment") + "'," + workParallelId + "," + workParallelStep + ",'" + dealwithPara.get("commentOrg") + "')";
          } else {
            tmpSql = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,COMMENTFIELD,DOMAIN_ID,RANGENAME,RANGEIDSTR,signcomment,parallel_id,parallel_step,DEALWITHORG) VALUES (" + 
              wf_dealwithcomment_id + "," + 
              dealwithPara.get("userId") + ",'" + 
              dealwithPara.get("comment") + 
              "',JSDB.FN_STRTODATE('" + 
              now.toLocaleString() + "','')," + 
              wf_dealwith_id + ",0,'','" + commentField + 
              "'," + domainId + ",'" + rangeName + "','" + 
              rangeIdStr + "','" + dealwithPara.get("signcomment") + "'," + workParallelId + "," + workParallelStep + ",'" + dealwithPara.get("commentOrg") + "')";
          } 
          stmt.execute(tmpSql);
        } else {
          String wf_dealwithcomment_id = getTableId2();
          String empName = "";
          rs = stmt.executeQuery(
              "SELECT EMPNAME FROM JSDB.ORG_EMPLOYEE WHERE EMP_ID=" + 
              dealwithPara.get("userId"));
          if (rs.next())
            empName = rs.getString(1); 
          if (databaseType.indexOf("mysql") >= 0) {
            tmpSql = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,ISSTANDFORCOMM,COMMENTFIELD,DOMAIN_ID,RANGENAME,RANGEIDSTR,signcomment,parallel_id,parallel_step,DEALWITHORG) VALUES (" + 
              wf_dealwithcomment_id + "," + 
              dealwithPara.get("standForUserId") + ",'" + 
              dealwithPara.get("comment") + "','" + 
              now.toLocaleString() + "'," + wf_dealwith_id + 
              "," + dealwithPara.get("userId") + ",'" + 
              empName + "',1,'" + commentField + "'," + 
              domainId + ",'" + rangeName + "','" + 
              rangeIdStr + "','" + dealwithPara.get("signcomment") + "'," + workParallelId + "," + workParallelStep + ",'" + dealwithPara.get("commentOrg") + "')";
          } else {
            tmpSql = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,ISSTANDFORCOMM,COMMENTFIELD,DOMAIN_ID,RANGENAME,RANGEIDSTR,signcomment,parallel_id,parallel_step,DEALWITHORG) VALUES (" + 
              wf_dealwithcomment_id + "," + 
              dealwithPara.get("standForUserId") + ",'" + 
              dealwithPara.get("comment") + 
              "',JSDB.FN_STRTODATE('" + 
              now.toLocaleString() + "','')," + 
              wf_dealwith_id + "," + 
              dealwithPara.get("userId") + ",'" + empName + 
              "',1,'" + commentField + "'," + domainId + 
              ",'" + rangeName + "','" + rangeIdStr + "','" + dealwithPara.get("signcomment") + "'," + workParallelId + "," + workParallelStep + ",'" + dealwithPara.get("commentOrg") + "')";
          } 
          stmt.execute(tmpSql);
        } 
      } else if (!dealwithPara.get("isStandForWork").toString().equals("1")) {
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "update JSDB.JSF_DEALWITHCOMMENT set DEALWITHEMPLOYEE_ID='" + dealwithPara.get("userId") + "'," + 
            "DEALWITHEMPLOYEECOMMENT='" + dealwithPara.get("comment") + "',DEALWITHTIME='" + now.toLocaleString() + "',WF_DEALWITH_ID='" + wf_dealwith_id + "'," + 
            "STANDFORUSERID=0,STANDFORUSERNAME='',COMMENTFIELD='" + commentField + "',DOMAIN_ID='" + domainId + "',RANGENAME='" + rangeName + "',RANGEIDSTR='" + rangeIdStr + "',signcomment='" + dealwithPara.get("signcomment") + "',DEALWITHORG='" + dealwithPara.get("commentOrg") + "' where WF_DEALWITHCOMMENT_ID=" + modiCommentId;
        } else {
          tmpSql = "update JSDB.JSF_DEALWITHCOMMENT set DEALWITHEMPLOYEE_ID='" + dealwithPara.get("userId") + "'," + 
            "DEALWITHEMPLOYEECOMMENT='" + dealwithPara.get("comment") + "',DEALWITHTIME=JSDB.FN_STRTODATE('" + 
            now.toLocaleString() + "',''),WF_DEALWITH_ID='" + wf_dealwith_id + "'," + 
            "STANDFORUSERID=0,STANDFORUSERNAME='',COMMENTFIELD='" + commentField + "',DOMAIN_ID='" + domainId + "',RANGENAME='" + rangeName + "',RANGEIDSTR='" + rangeIdStr + "',signcomment='" + dealwithPara.get("signcomment") + "',DEALWITHORG='" + dealwithPara.get("commentOrg") + "' where WF_DEALWITHCOMMENT_ID=" + modiCommentId;
        } 
        stmt.execute(tmpSql);
      } else {
        String empName = "";
        rs = stmt.executeQuery(
            "SELECT EMPNAME FROM JSDB.ORG_EMPLOYEE WHERE EMP_ID=" + dealwithPara.get("userId"));
        if (rs.next())
          empName = rs.getString(1); 
        rs.close();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "update JSDB.JSF_DEALWITHCOMMENT set DEALWITHEMPLOYEE_ID='" + dealwithPara.get("standForUserId") + "'," + 
            "DEALWITHEMPLOYEECOMMENT='" + dealwithPara.get("comment") + "',DEALWITHTIME='" + now.toLocaleString() + "',WF_DEALWITH_ID='" + wf_dealwith_id + "'," + 
            "STANDFORUSERID='" + dealwithPara.get("userId") + "',STANDFORUSERNAME='" + empName + "',ISSTANDFORCOMM='1' ,COMMENTFIELD='" + commentField + "'," + 
            "DOMAIN_ID='" + domainId + "',RANGENAME='" + rangeName + "',RANGEIDSTR='" + rangeIdStr + "',signcomment='" + dealwithPara.get("signcomment") + "',DEALWITHORG='" + dealwithPara.get("commentOrg") + "' where WF_DEALWITHCOMMENT_ID=" + modiCommentId;
        } else {
          tmpSql = "update JSDB.JSF_DEALWITHCOMMENT set DEALWITHEMPLOYEE_ID='" + dealwithPara.get("standForUserId") + "'," + 
            "DEALWITHEMPLOYEECOMMENT='" + dealwithPara.get("comment") + "',DEALWITHTIME=JSDB.FN_STRTODATE('" + 
            now.toLocaleString() + "',''),WF_DEALWITH_ID='" + wf_dealwith_id + "'," + 
            "STANDFORUSERID='" + dealwithPara.get("userId") + "',STANDFORUSERNAME='" + empName + "',ISSTANDFORCOMM='1' ,COMMENTFIELD='" + commentField + "',DOMAIN_ID='" + domainId + "',RANGENAME='" + rangeName + "',RANGEIDSTR='" + rangeIdStr + "',signcomment='" + dealwithPara.get("signcomment") + "',DEALWITHORG='" + dealwithPara.get("commentOrg") + "' where WF_DEALWITHCOMMENT_ID=" + modiCommentId;
        } 
        stmt.execute(tmpSql);
      } 
      int hasDoneFile = 0;
      rs = stmt.executeQuery("select count(wf_work_id) from jsf_work where workstatus = 101 and worktable_id = " + workTable_id + 
          " and workrecord_id = " + workRecord_id + " and wf_curemployee_id = " + 
          para[13] + " and workprocess_id=" + workProcess_id);
      if (rs.next())
        hasDoneFile = rs.getInt(1); 
      rs.close();
      if ("last".equals(SystemCommon.getRepeatFileDealwith()) && hasDoneFile > 0)
        stmt.execute("update JSDB.JSF_WORK set WORKDELETE =1 where workstatus = 101 and worktable_id = " + workTable_id + 
            " and workrecord_id = " + workRecord_id + " and wf_curemployee_id = " + 
            para[13] + " and workprocess_id=" + workProcess_id); 
      stmt.execute("delete from JSDB.jsf_work where workTable_Id=" + workTable_id + " and workRecord_id=" + 
          workRecord_id + " and workStepCount=" + para[7] + " and standForUserId=" + 
          para[13]);
      String remindFieldValue = para[8];
      int k = 0;
      if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
        k = (String.valueOf(remindFieldValue) + workFileType + "您已办理完毕！").length() - 100;
      } else {
        k = (String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "您已办理完毕！").length() - 100;
      } 
      if (k > 0)
        remindFieldValue.substring(0, remindFieldValue.length() - k); 
      String standTitle = "";
      String workTitle = "";
      if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
        workTitle = String.valueOf(remindFieldValue) + workFileType + "您已办理完毕！";
      } else {
        workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "您已办理完毕！";
      } 
      if (!para[18].equals(""))
        workTitle = para[18]; 
      if (para[12].equals("1")) {
        rs = stmt.executeQuery("SELECT EMPNAME FROM JSDB.ORG_EMPLOYEE WHERE EMP_ID=" + para[13]);
        String myTmp = "";
        if (rs.next())
          myTmp = rs.getString(1); 
        rs.close();
        myTmp = String.valueOf(workTitle) + "（" + myTmp + "代办）";
        if (myTmp.length() > 100)
          myTmp = myTmp.substring(0, 99); 
        if (databaseType.indexOf("mysql") >= 0) {
          stmt.execute("UPDATE JSDB.JSF_WORK SET WORKSTATUS=101,DEALWITHTIME='" + 
              now.toLocaleString() + "',WORKTITLE='" + 
              myTmp + "',WORKALLOWCANCEL=" + para[6] + 
              ",WORKUSER='" + workUser.toString() + 
              "' where workTable_Id=" + workTable_id + 
              " and workRecord_id=" + 
              workRecord_id + " and workStepCount=" + para[7] + 
              " and wf_curemployee_id=" + 
              para[14]);
        } else {
          stmt.execute("UPDATE JSDB.JSF_WORK SET WORKSTATUS=101,DEALWITHTIME=JSDB.FN_STRTODATE('" + 
              now.toLocaleString() + "',''),WORKTITLE='" + 
              myTmp + "',WORKALLOWCANCEL=" + para[6] + 
              ",WORKUSER='" + workUser.toString() + 
              "' where workTable_Id=" + workTable_id + 
              " and workRecord_id=" + 
              workRecord_id + " and workStepCount=" + para[7] + 
              " and wf_curemployee_id=" + 
              para[14]);
        } 
        standTitle = "（代" + para[15] + "办理）";
      } 
      workTitle = String.valueOf(workTitle) + standTitle;
      if (workTitle.length() > 100)
        workTitle = workTitle.substring(0, 99); 
      sql = "update JSDB.JSF_WORK set workstatus = 101,DEALWITHTIME=";
      if (databaseType.indexOf("mysql") >= 0) {
        sql = String.valueOf(sql) + "'" + now.toLocaleString() + "', workTitle = '" + workTitle + "', ";
      } else {
        sql = String.valueOf(sql) + "JSDB.FN_STRTODATE('" + now.toLocaleString() + "',''), workTitle = '" + workTitle + "', ";
      } 
      if ("first".equals(SystemCommon.getRepeatFileDealwith()) && hasDoneFile > 0)
        sql = String.valueOf(sql) + "WORKDELETE =1,"; 
      sql = String.valueOf(sql) + "workAllowCancel=" + para[6] + ", workUser = '" + workUser.toString() + "' " + 
        "where wf_work_id = " + curWorkId;
      stmt.executeUpdate(sql);
      int mycount = 0;
      int workListControl = 0;
      if (curTransactType.equals("1") || curTransactType.equals("3")) {
        sql = " select count(wf_work_id) from JSDB.JSF_WORK where worktable_id = " + workTable_id + 
          " and workRecord_id = " + workRecord_id + " and workStepCount = " + para[7] + 
          " and workstatus = 0";
        if (curActivityIsDivide)
          if (nextActivityIsGanther) {
            sql = String.valueOf(sql) + " and is_parallel=1";
          } else {
            sql = String.valueOf(sql) + " and parallel_id=" + workParallelId + " and parallel_step=" + workParallelStep;
          }  
        rs = stmt.executeQuery(sql);
        if (rs.next())
          mycount = rs.getInt(1); 
        rs.close();
      } else if (curActivityIsDivide && nextActivityIsGanther) {
        sql = " select count(wf_work_id) from JSDB.JSF_WORK where worktable_id = " + workTable_id + 
          " and workRecord_id = " + workRecord_id + " and workStepCount = " + para[7] + 
          " and workstatus = 0 and parallel_curactid<>" + workParallelCurActId + " and parallel_fromwork=" + workParallelFromWork;
        rs = stmt.executeQuery(sql);
        if (rs.next())
          mycount = rs.getInt(1); 
        rs.close();
      } 
      if (mycount == 0) {
        if (nextActivityIsGanther && "2".equals(nextTransactType))
          stmt.execute("delete from jsf_work where workListControl=0 and workTable_id = " + 
              workTable_id + " and workRecord_Id = " + workRecord_id + " and workActivity = " + nextActivityId); 
        if (activityClass.equals("1") || (activityClass.equals("0") && (subProcType.equals("0") || subProcType.equals("1"))) || activityClass.equals("3")) {
          sql = "update JSDB.JSF_WORK set workListControl = 1 where worktable_id = " + workTable_id + 
            " and workrecord_id = " + workRecord_id + " and workActivity = " + nextActivityId;
          stmt.execute(sql);
          stmt.execute("update JSDB.JSF_WORK set workCurStep='" + nextActivityName + "' where worktable_id=" + workTable_id + 
              " and workrecord_id = " + workRecord_id);
          workListControl = 1;
          stmt.execute("update JSDB.JSF_DEALWITH set curActivityStatus = 1 where databasetable_id = " + 
              workTable_id + " and databaserecord_id = " + workRecord_id + " and curStepCount = " + 
              para[7]);
          workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "已办理完毕！";
          if (!para[18].equals(""))
            workTitle = para[18]; 
          if (workTitle.length() > 100)
            workTitle = workTitle.substring(0, 99); 
        } 
      } 
      String delayTime = (String)dealwithPara.get("delayTime");
      if ("null".equalsIgnoreCase(delayTime))
        delayTime = ""; 
      if (!"".equals(delayTime)) {
        SimpleDateFormat timeFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
          Date d = timeFormater.parse(delayTime);
          if (d.getTime() >= now.getTime())
            now = d; 
        } catch (ParseException e) {
          System.out.println("格式化延迟时间出错，使用当前时间");
        } 
      } 
      if (para[11].equals("1")) {
        String[] standForUser = (String[])null;
        for (int m = toUserList.size() - 1; m >= 0; m--) {
          standForUser = (String[])toUserList.get(m);
          if (curActivityIsDivide && !nextActivityIsGanther) {
            sql = "select count(wf_work_id) from JSDB.JSF_WORK where wf_curemployee_id=" + standForUser[0] + " and worktable_id=" + 
              workTable_id + " and workrecord_id=" + workRecord_id + " and workreadmarker=0 and workActivity=" + 
              nextActivityId + " and workStepCount = " + mainWorkStepCount + " and parallel_id=" + workParallelId + " and parallel_step=" + (workParallelStep + 1);
          } else {
            sql = "select count(wf_work_id) from JSDB.JSF_WORK where wf_curemployee_id=" + standForUser[0] + " and worktable_id=" + 
              workTable_id + " and workrecord_id=" + workRecord_id + " and workreadmarker=0 and workActivity=" + 
              nextActivityId + " and workStepCount = " + (Integer.parseInt(para[7]) + 1);
          } 
          rs = stmt.executeQuery(sql);
          rs.next();
          int sameWorkCount = rs.getInt(1);
          rs.close();
          if (sameWorkCount == 0) {
            if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
              workTitle = String.valueOf(remindFieldValue) + workFileType + "等待您处理！";
            } else {
              workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "等待您处理！";
            } 
            if (!para[18].equals("")) {
              workTitle = para[18];
              if ("shandongguotou".equals(SystemCommon.getCustomerName()))
                workTitle = String.valueOf(workTitle) + workFileType + "等待您处理！"; 
            } 
            if (workTitle.length() > 100)
              workTitle = workTitle.substring(0, 99); 
            String workId = getTableId2();
            if (nextActivityIsFirstDivide) {
              newWorkIsParallel = 1;
              newWorkParallelId = getTableId2();
              newWorkParallelStep = 1;
              newWorkParallelCurActId = nextActivityId;
            } else if (nextActivityIsMiddleDivide) {
              newWorkIsParallel = 1;
              newWorkParallelId = workParallelId;
              newWorkParallelStep = workParallelStep + 1;
              newWorkParallelCurActId = nextActivityId;
              mainWorkStepCount = Integer.parseInt(para[7]);
            } else {
              newWorkIsParallel = 0;
              newWorkParallelId = "0";
              newWorkParallelStep = 0;
              newWorkParallelCurActId = "0";
            } 
            if (databaseType.indexOf("mysql") >= 0) {
              tmpSql = "insert into JSDB.JSF_WORK ( wf_work_id,wf_curEmployee_id,workStatus,workFileType,workCurStep,workTitle,workLeftLinkFile,workMainLinkFile,workListControl,workActivity,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workReadMarker,workType,workProcess_id,worktable_id,workrecord_id,workDeadLine,workPressTime,workCreateDate,workStepCount,isStandForWork,standForUserId,standForUserName,initActivity,initStepCount,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,domain_id,emergence,transactType,INITACTIVITYNAME,dealTips,workDeadlineDate,workDeadlinePressDate,processDeadlineDate,relproject_id,is_parallel,parallel_id,parallel_step,parallel_curactid,parallel_fromwork,activityDelaySend) values ( " + 







                
                workId + "," + standForUser[0] + ",0,'" + workFileType + "','" + nextActivityName + 
                "','" + workTitle + "'," + "'','" + para[10] + "'," + workListControl + "," + nextActivityId + 
                ",'" + workSubmitPerson + "','" + workSubmitTime + "'," + 
                wf_submitEmployee_id + ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                "," + deadLineTime + "," + pressTime + ",'" + now.toLocaleString() + 
                "'," + mainWorkStepCount + ",0,0,''," + nextActivityId + 
                "," + mainWorkStepCount + "," + isSubProcWork + "," + pareProcActiId + "," + 
                pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + 
                submitOrg + "'," + domainId + "," + para[19] + "," + para[20] + ",'" + para[0] + "','" + dealTips + "','" + 
                workDeadlineDate.toLocaleString() + "','" + workDeadlinePressDate.toLocaleString() + "'," + processDeadlineDate + "," + relproject + "," + 
                newWorkIsParallel + "," + newWorkParallelId + "," + newWorkParallelStep + "," + newWorkParallelCurActId + "," + newWorkParallelFromWork + ",'" + delayTime + "'" + 
                ")";
            } else {
              tmpSql = "insert into JSDB.JSF_WORK ( wf_work_id,wf_curEmployee_id,workStatus,workFileType,workCurStep,workTitle,workLeftLinkFile,workMainLinkFile,workListControl,workActivity,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workReadMarker,workType,workProcess_id,worktable_id,workrecord_id,workDeadLine,workPressTime,workCreateDate,workStepCount,isStandForWork,standForUserId,standForUserName,initActivity,initStepCount,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,domain_id,emergence,transactType,INITACTIVITYNAME,dealTips,workDeadlineDate,workDeadlinePressDate,processDeadlineDate,relproject_id,is_parallel,parallel_id,parallel_step,parallel_curactid,parallel_fromwork,activityDelaySend) values ( " + 







                
                workId + "," + standForUser[0] + ",0,'" + workFileType + "','" + nextActivityName + 
                "','" + workTitle + "'," + "'','" + para[10] + "'," + workListControl + "," + nextActivityId + 
                ",'" + workSubmitPerson + "',JSDB.FN_STRTODATE('" + workSubmitTime + "','')," + 
                wf_submitEmployee_id + ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                "," + deadLineTime + "," + pressTime + ",JSDB.FN_STRTODATE('" + now.toLocaleString() + 
                "','')," + mainWorkStepCount + ",0,0,''," + nextActivityId + 
                "," + mainWorkStepCount + "," + isSubProcWork + "," + pareProcActiId + "," + 
                pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + 
                submitOrg + "'," + domainId + "," + para[19] + "," + para[20] + ",'" + para[0] + "','" + dealTips + 
                "',JSDB.FN_STRTODATE('" + workDeadlineDate.toLocaleString() + "','L'),JSDB.FN_STRTODATE('" + workDeadlinePressDate.toLocaleString() + "','L')," + processDeadlineDate + "," + relproject + "," + 
                newWorkIsParallel + "," + newWorkParallelId + "," + newWorkParallelStep + "," + newWorkParallelCurActId + "," + newWorkParallelFromWork + ",'" + delayTime + "'" + 
                ")";
            } 
            stmt.execute(tmpSql);
            if ("1".equals(Integer.valueOf(workListControl))) {
              msgVO = new MessagesVO();
              msgVO.setMessage_date_begin(now);
              msgVO.setMessage_date_end(new Date("2050/1/1"));
              msgVO.setMessage_send_UserId(Long.parseLong(wf_submitEmployee_id));
              msgVO.setMessage_send_UserName(workSubmitPerson);
              msgVO.setMessage_show(1);
              msgVO.setMessage_status(1);
              msgVO.setMessage_time(now);
              msgVO.setMessage_title(workTitle);
              msgVO.setMessage_toUserId(Long.parseLong(standForUser[0]));
              msgVO.setMessage_type("jsflow");
              msgVO.setData_id(Long.parseLong(workId));
              msgVO.setSendSMS((para.length > 22) ? Integer.valueOf(para[22]).intValue() : 1);
              msgVO.setMessage_url("/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + workId);
              msgList.add(msgVO);
            } 
          } 
          if (!standForUser[2].equals("")) {
            if (curActivityIsDivide && !nextActivityIsGanther) {
              sql = "select count(wf_work_id) from JSDB.JSF_WORK where wf_curemployee_id=" + standForUser[2] + " and worktable_id=" + 
                workTable_id + " and workrecord_id=" + workRecord_id + " and workreadmarker=0 and workActivity=" + 
                nextActivityId + " and workStepCount = " + mainWorkStepCount + " and standForUserId=" + 
                standForUser[0] + " and parallel_id=" + workParallelId + " and parallel_step=" + (workParallelStep + 1);
            } else {
              sql = "select count(wf_work_id) from JSDB.JSF_WORK where wf_curemployee_id=" + standForUser[2] + " and worktable_id=" + 
                workTable_id + " and workrecord_id=" + workRecord_id + " and workreadmarker=0 and workActivity=" + 
                nextActivityId + " and workStepCount = " + mainWorkStepCount + " and standForUserId=" + 
                standForUser[0];
            } 
            rs = stmt.executeQuery(sql);
            rs.next();
            sameWorkCount = rs.getInt(1);
            rs.close();
            if (sameWorkCount == 0) {
              String workId = getTableId2();
              if (nextActivityIsFirstDivide) {
                newWorkIsParallel = 1;
                newWorkParallelId = getTableId2();
                newWorkParallelStep = 1;
                newWorkParallelCurActId = nextActivityId;
              } else if (nextActivityIsMiddleDivide) {
                newWorkIsParallel = 1;
                newWorkParallelId = workParallelId;
                newWorkParallelStep = workParallelStep + 1;
                newWorkParallelCurActId = nextActivityId;
                mainWorkStepCount = Integer.parseInt(para[7]);
              } else {
                newWorkIsParallel = 0;
                newWorkParallelId = "0";
                newWorkParallelStep = 0;
                newWorkParallelCurActId = "0";
              } 
              if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
                workTitle = String.valueOf(remindFieldValue) + workFileType + "等待您处理！" + "（代" + standForUser[1] + "办理）";
              } else {
                workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "等待您处理！" + "（代" + standForUser[1] + "办理）";
              } 
              if (!para[18].equals("")) {
                workTitle = para[18];
                if ("shandongguotou".equals(SystemCommon.getCustomerName()))
                  workTitle = String.valueOf(workTitle) + workFileType + "等待您处理！"; 
              } 
              if (workTitle.length() > 100)
                workTitle = workTitle.substring(0, 99); 
              if (databaseType.indexOf("mysql") >= 0) {
                tmpSql = "insert into JSDB.JSF_WORK ( wf_work_id,wf_curEmployee_id,workStatus,workFileType,workCurStep,workTitle,workLeftLinkFile,workMainLinkFile,workListControl,workActivity,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workReadMarker,workType,workProcess_id,worktable_id,workrecord_id,workDeadLine,workPressTime,workCreateDate,workStepCount,isStandForWork,standForUserId,standForUserName,initActivity,initStepCount,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,domain_id,emergence,transactType,INITACTIVITYNAME,dealTips,workDeadlineDate,workDeadlinePressDate,processDeadlineDate,relproject_id,is_parallel,parallel_id,parallel_step,parallel_curactid,parallel_fromwork,activityDelaySend) values ( " + 







                  
                  workId + "," + standForUser[2] + ",0,'" + workFileType + "','" + nextActivityName + "','" + workTitle + "'," + 
                  "'','" + para[10] + "'," + workListControl + "," + nextActivityId + ",'" + workSubmitPerson + 
                  "','" + workSubmitTime + "'," + wf_submitEmployee_id + ",0,1," + 
                  workProcess_id + "," + workTable_id + "," + workRecord_id + "," + deadLineTime + "," + pressTime + 
                  ",'" + now.toLocaleString() + "'," + 
                  mainWorkStepCount + ",1," + standForUser[0] + ",'" + standForUser[1] + "'," + 
                  nextActivityId + "," + mainWorkStepCount + "," + isSubProcWork + "," + pareProcActiId + "," + 
                  pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + 
                  domainId + "," + para[19] + "," + para[20] + ",'" + para[0] + "','" + dealTips + "','" + workDeadlineDate.toLocaleString() + 
                  "','" + workDeadlinePressDate.toLocaleString() + "'," + processDeadlineDate + "," + relproject + "," + 
                  newWorkIsParallel + "," + newWorkParallelId + "," + newWorkParallelStep + "," + newWorkParallelCurActId + "," + newWorkParallelFromWork + ",'" + delayTime + "'" + 
                  ")";
              } else {
                tmpSql = "insert into JSDB.JSF_WORK ( wf_work_id,wf_curEmployee_id,workStatus,workFileType,workCurStep,workTitle,workLeftLinkFile,workMainLinkFile,workListControl,workActivity,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workReadMarker,workType,workProcess_id,worktable_id,workrecord_id,workDeadLine,workPressTime,workCreateDate,workStepCount,isStandForWork,standForUserId,standForUserName,initActivity,initStepCount,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,domain_id,emergence,transactType,INITACTIVITYNAME,dealTips,workDeadlineDate,workDeadlinePressDate,processDeadlineDate,relproject_id,is_parallel,parallel_id,parallel_step,parallel_curactid,parallel_fromwork,activityDelaySend) values ( " + 







                  
                  workId + "," + standForUser[2] + ",0,'" + workFileType + "','" + nextActivityName + "','" + workTitle + "'," + 
                  "'','" + para[10] + "'," + workListControl + "," + nextActivityId + ",'" + workSubmitPerson + 
                  "',JSDB.FN_STRTODATE('" + workSubmitTime + "','')," + wf_submitEmployee_id + ",0,1," + 
                  workProcess_id + "," + workTable_id + "," + workRecord_id + "," + deadLineTime + "," + pressTime + 
                  ",JSDB.FN_STRTODATE('" + now.toLocaleString() + "','')," + 
                  mainWorkStepCount + ",1," + standForUser[0] + ",'" + standForUser[1] + "'," + 
                  nextActivityId + "," + mainWorkStepCount + "," + isSubProcWork + "," + pareProcActiId + "," + 
                  pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + 
                  domainId + "," + para[19] + "," + para[20] + ",'" + para[0] + "','" + dealTips + 
                  "',JSDB.FN_STRTODATE('" + workDeadlineDate.toLocaleString() + "','L'),JSDB.FN_STRTODATE('" + workDeadlinePressDate.toLocaleString() + "','L')," + processDeadlineDate + "," + relproject + "," + 
                  newWorkIsParallel + "," + newWorkParallelId + "," + newWorkParallelStep + "," + newWorkParallelCurActId + "," + newWorkParallelFromWork + ",'" + delayTime + "'" + 
                  ")";
              } 
              stmt.execute(tmpSql);
              if ("1".equals(Integer.valueOf(workListControl))) {
                msgVO = new MessagesVO();
                msgVO.setMessage_date_begin(now);
                msgVO.setMessage_date_end(new Date("2050/1/1"));
                msgVO.setMessage_send_UserId(Long.parseLong(wf_submitEmployee_id));
                msgVO.setMessage_send_UserName(workSubmitPerson);
                msgVO.setMessage_show(1);
                msgVO.setMessage_status(1);
                msgVO.setMessage_time(now);
                msgVO.setMessage_title(workTitle);
                msgVO.setMessage_toUserId(Long.parseLong(standForUser[2]));
                msgVO.setMessage_type("jsflow");
                msgVO.setData_id(Long.parseLong(workId));
                msgVO.setSendSMS((para.length > 22) ? Integer.valueOf(para[22]).intValue() : 1);
                msgVO.setMessage_url("/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + workId);
                msgList.add(msgVO);
              } 
            } 
          } 
        } 
      } else {
        String tmpUser = "";
        String insertUser = "";
        for (int m = transactUser.length - 1; m >= 0; m--) {
          tmpUser = transactUser[m];
          if (insertUser.indexOf(String.valueOf(tmpUser) + ",") < 0) {
            insertUser = String.valueOf(insertUser) + tmpUser + ",";
            if (tmpUser.startsWith("$"))
              tmpUser = tmpUser.substring(1); 
            if (tmpUser.endsWith("$"))
              tmpUser = tmpUser.substring(0, tmpUser.length() - 1); 
            if (curActivityIsDivide && !nextActivityIsGanther) {
              sql = "select count(wf_work_id) from JSDB.JSF_WORK where wf_curemployee_id = " + 
                tmpUser + " and worktable_id = " + workTable_id + 
                " and workrecord_id = " + workRecord_id + 
                " and workreadmarker = 0 and workActivity = " + nextActivityId + 
                " and workStepCount = " + mainWorkStepCount + " and parallel_id=" + workParallelId + " and parallel_step=" + (workParallelStep + 1);
            } else {
              sql = "select count(wf_work_id) from JSDB.JSF_WORK where wf_curemployee_id = " + 
                tmpUser + " and worktable_id = " + workTable_id + 
                " and workrecord_id = " + workRecord_id + 
                " and workreadmarker = 0 and workActivity = " + nextActivityId + 
                " and workStepCount = " + mainWorkStepCount;
            } 
            rs = stmt.executeQuery(sql);
            rs.next();
            int sameWorkCount = rs.getInt(1);
            rs.close();
            if (sameWorkCount == 0) {
              String workId = getTableId2();
              if (nextActivityIsFirstDivide) {
                newWorkIsParallel = 1;
                newWorkParallelId = getTableId2();
                newWorkParallelStep = 1;
                newWorkParallelCurActId = nextActivityId;
              } else if (nextActivityIsMiddleDivide) {
                newWorkIsParallel = 1;
                newWorkParallelId = workParallelId;
                newWorkParallelStep = workParallelStep + 1;
                newWorkParallelCurActId = nextActivityId;
                mainWorkStepCount = Integer.parseInt(para[7]);
              } else {
                newWorkIsParallel = 0;
                newWorkParallelId = "0";
                newWorkParallelStep = 0;
                newWorkParallelCurActId = "0";
              } 
              if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
                workTitle = String.valueOf(remindFieldValue) + workFileType + "等待您处理！";
              } else {
                workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "等待您处理！";
              } 
              if (!para[18].equals("")) {
                workTitle = para[18];
                if ("shandongguotou".equals(SystemCommon.getCustomerName()))
                  workTitle = String.valueOf(workTitle) + workFileType + "等待您处理！"; 
              } 
              if (databaseType.indexOf("mysql") >= 0) {
                tmpSql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType,  workCurStep, workTitle, workLeftLinkFile, workMainLinkFile,  workListControl, workActivity, workSubmitPerson, workSubmitTime,  wf_submitEmployee_id, workReadMarker, workType, workProcess_id,  worktable_id, workrecord_id, workDeadLine, workPressTime,  workCreateDate, workStepCount, initActivity, initStepCount ,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,domain_id,emergence,transactType,INITACTIVITYNAME,isStandForWork,dealTips,workDeadlineDate,workDeadlinePressDate,processDeadlineDate,relproject_id,is_parallel,parallel_id,parallel_step,parallel_curactid,parallel_fromwork,activityDelaySend) values ( " + 









                  
                  workId + ", " + tmpUser + ",0,'" + workFileType + "','" + 
                  nextActivityName + "','" + workTitle + "','','" + para[10] + "'," + workListControl + "," + 
                  nextActivityId + ",'" + workSubmitPerson + "','" + 
                  workSubmitTime + "'," + wf_submitEmployee_id + 
                  ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                  "," + deadLineTime + "," + pressTime + ", '" + 
                  now.toLocaleString() + "', " + 
                  mainWorkStepCount + "," + nextActivityId + "," + mainWorkStepCount + "," + 
                  isSubProcWork + "," + pareProcActiId + "," + pareStepCount + "," + pareTableId + "," + pareRecordId + "," + 
                  pareProcNextActiId + ",'" + submitOrg + "'," + domainId + ",'" + para[19] + "','" + para[20] + "','" + para[0] + "',0,'" + dealTips + "','" + workDeadlineDate.toLocaleString() + "','" + workDeadlinePressDate.toLocaleString() + "'," + processDeadlineDate + "," + relproject + "," + 
                  newWorkIsParallel + "," + newWorkParallelId + "," + newWorkParallelStep + "," + newWorkParallelCurActId + "," + newWorkParallelFromWork + ",'" + delayTime + "'" + 
                  ")";
              } else {
                tmpSql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType,  workCurStep, workTitle, workLeftLinkFile, workMainLinkFile,  workListControl, workActivity, workSubmitPerson, workSubmitTime,  wf_submitEmployee_id, workReadMarker, workType, workProcess_id,  worktable_id, workrecord_id, workDeadLine, workPressTime,  workCreateDate, workStepCount, initActivity, initStepCount ,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,domain_id,emergence,transactType,INITACTIVITYNAME,isStandForWork,dealTips,workDeadlineDate,workDeadlinePressDate,processDeadlineDate,relproject_id,is_parallel,parallel_id,parallel_step,parallel_curactid,parallel_fromwork,activityDelaySend) values ( " + 









                  
                  workId + ", " + tmpUser + ",0,'" + workFileType + "','" + 
                  nextActivityName + "','" + workTitle + "','','" + para[10] + "'," + workListControl + "," + 
                  nextActivityId + ",'" + workSubmitPerson + "',JSDB.FN_STRTODATE('" + 
                  workSubmitTime + "','')," + wf_submitEmployee_id + 
                  ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                  "," + deadLineTime + "," + pressTime + ", JSDB.FN_STRTODATE('" + 
                  now.toLocaleString() + "',''), " + 
                  mainWorkStepCount + "," + nextActivityId + "," + mainWorkStepCount + "," + 
                  isSubProcWork + "," + pareProcActiId + "," + pareStepCount + "," + pareTableId + "," + pareRecordId + "," + 
                  pareProcNextActiId + ",'" + submitOrg + "'," + domainId + ",'" + para[19] + "','" + para[20] + "','" + para[0] + "',0,'" + dealTips + "',JSDB.FN_STRTODATE('" + workDeadlineDate.toLocaleString() + "','L'),JSDB.FN_STRTODATE('" + workDeadlinePressDate.toLocaleString() + "','L')," + processDeadlineDate + "," + relproject + "," + 
                  newWorkIsParallel + "," + newWorkParallelId + "," + newWorkParallelStep + "," + newWorkParallelCurActId + "," + newWorkParallelFromWork + ",'" + delayTime + "'" + 
                  ")";
              } 
              stmt.execute(tmpSql);
              if ("1".equals(Integer.valueOf(workListControl))) {
                msgVO = new MessagesVO();
                msgVO.setMessage_date_begin(now);
                msgVO.setMessage_date_end(new Date("2050/1/1"));
                msgVO.setMessage_send_UserId(Long.parseLong(wf_submitEmployee_id));
                msgVO.setMessage_send_UserName(workSubmitPerson);
                msgVO.setMessage_show(1);
                msgVO.setMessage_status(1);
                msgVO.setMessage_time(now);
                msgVO.setMessage_title(workTitle);
                msgVO.setMessage_toUserId(Long.parseLong(tmpUser));
                msgVO.setMessage_type("jsflow");
                msgVO.setData_id(Long.parseLong(workId));
                msgVO.setSendSMS((para.length > 22) ? Integer.valueOf(para[22]).intValue() : 1);
                msgVO.setMessage_url("/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + workId);
                msgList.add(msgVO);
              } 
            } 
          } 
        } 
      } 
      if (!needPassRound.equals("") && passRoundUser != null) {
        boolean flag = true;
        String tmpUser = "";
        String insertUser = "";
        for (int m = 0; m < passRoundUser.length; m++) {
          if (insertUser.indexOf(String.valueOf(passRoundUser[m]) + ",") < 0) {
            insertUser = String.valueOf(insertUser) + passRoundUser[m] + ",";
            tmpUser = passRoundUser[m];
            if (!tmpUser.equals("")) {
              if (tmpUser.startsWith("$"))
                tmpUser = tmpUser.substring(1); 
              if (tmpUser.endsWith("$"))
                tmpUser = tmpUser.substring(0, tmpUser.length() - 1); 
              for (int n = 0; n < transactUser.length && 
                !tmpUser.equals(transactUser[n]); n++);
              if (flag) {
                String workId = getTableId2();
                if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
                  workTitle = String.valueOf(remindFieldValue) + workFileType + "等待您的审阅！";
                } else {
                  workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "等待您的审阅！";
                } 
                if (!para[18].equals("")) {
                  workTitle = para[18];
                  if ("shandongguotou".equals(SystemCommon.getCustomerName()))
                    workTitle = String.valueOf(workTitle) + workFileType + "等待您的审阅！"; 
                } 
                if (databaseType.indexOf("mysql") >= 0) {
                  tmpSql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType,  workCurStep, workTitle, workLeftLinkFile, workMainLinkFile,  workListControl, workActivity, workSubmitPerson, workSubmitTime,  wf_submitEmployee_id, workReadMarker, workType, workProcess_id,  worktable_id, workrecord_id, workDeadLine, workPressTime,  workCreateDate, workStepCount, initActivity, initStepCount, isSubProcWork, pareProcActiId,  pareStepCount, pareTableId, pareRecordId, pareProcNextActiId, submitOrg, domain_id ,emergence,INITACTIVITYNAME,isStandForWork,activityDelaySend ) values ( " + 







                    
                    workId + ", " + tmpUser + ",2,'" + workFileType + "','" + 
                    nextActivityName + "','" + workTitle + "','','" + para[10] + "'," + workListControl + "," + 
                    nextActivityId + ",'" + workSubmitPerson + "','" + 
                    workSubmitTime + "'," + wf_submitEmployee_id + 
                    ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                    "," + deadLineTime + "," + pressTime + ", '" + 
                    now.toLocaleString() + "', " + 
                    mainWorkStepCount + "," + nextActivityId + ", " + mainWorkStepCount + "," + isSubProcWork + "," + pareProcActiId + "," + 
                    pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + domainId + "," + para[19] + ",'" + para[0] + "',0,'" + delayTime + "')";
                } else {
                  tmpSql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType,  workCurStep, workTitle, workLeftLinkFile, workMainLinkFile,  workListControl, workActivity, workSubmitPerson, workSubmitTime,  wf_submitEmployee_id, workReadMarker, workType, workProcess_id,  worktable_id, workrecord_id, workDeadLine, workPressTime,  workCreateDate, workStepCount, initActivity, initStepCount, isSubProcWork, pareProcActiId,  pareStepCount, pareTableId, pareRecordId, pareProcNextActiId, submitOrg, domain_id,emergence,INITACTIVITYNAME ,isStandForWork,activityDelaySend ) values ( " + 







                    
                    workId + ", " + tmpUser + ",2,'" + workFileType + "','" + 
                    nextActivityName + "','" + workTitle + "','','" + para[10] + "'," + workListControl + "," + 
                    nextActivityId + ",'" + workSubmitPerson + "',JSDB.FN_STRTODATE('" + 
                    workSubmitTime + "','')," + wf_submitEmployee_id + 
                    ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                    "," + deadLineTime + "," + pressTime + ", JSDB.FN_STRTODATE('" + 
                    now.toLocaleString() + "','yyyy-mm-dd hh24:mi:ss'), " + 
                    mainWorkStepCount + "," + nextActivityId + ", " + mainWorkStepCount + "," + isSubProcWork + "," + pareProcActiId + "," + 
                    pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + domainId + "," + para[19] + ",'" + para[20] + "',0,'" + delayTime + "')";
                } 
                stmt.execute(tmpSql);
                msgVO = new MessagesVO();
                msgVO.setMessage_date_begin(now);
                msgVO.setMessage_date_end(new Date("2050/1/1"));
                msgVO.setMessage_send_UserId(Long.parseLong(wf_submitEmployee_id));
                msgVO.setMessage_send_UserName(workSubmitPerson);
                msgVO.setMessage_show(1);
                msgVO.setMessage_status(1);
                msgVO.setMessage_time(now);
                msgVO.setMessage_title(workTitle);
                msgVO.setMessage_toUserId(Long.parseLong(tmpUser));
                msgVO.setMessage_type("jsflow");
                msgVO.setData_id(Long.parseLong(workId));
                msgVO.setSendSMS((para.length > 22) ? Integer.valueOf(para[22]).intValue() : 1);
                msgVO.setMessage_url("/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + workId);
                msgList.add(msgVO);
              } 
            } 
          } 
        } 
      } 
      if (mycount == 0)
        if (curActivityIsDivide && !nextActivityIsGanther) {
          stmt.execute(" update JSDB.JSF_WORK set workcurstep = '" + mainWorkCurActivityName + "', workactivity = " + 
              mainWorkCurActivity + " where worktable_id = " + workTable_id + " and " + 
              " workrecord_id = " + workRecord_id + " and workStepCount <=" + Integer.parseInt(para[7]));
        } else {
          stmt.execute(" update JSDB.JSF_WORK set workcurstep = '" + nextActivityName + "', workactivity = " + 
              nextActivityId + " where worktable_id = " + workTable_id + " and " + 
              " workrecord_id = " + workRecord_id);
        }  
      if (mycount == 0 && activityClass.equals("0") && subProcType.equals("0"))
        if (curActivityIsDivide && !nextActivityIsGanther) {
          stmt.execute(" update JSDB.JSF_WORK set workListControl = 1 where workTable_id = " + 
              workTable_id + " and workRecord_Id = " + workRecord_id + " and parallel_id = " + 
              workParallelId + " and parallel_step=" + (workParallelStep + 1));
        } else {
          stmt.execute(" update JSDB.JSF_WORK set workListControl = 1 where workTable_id = " + 
              workTable_id + " and workRecord_Id = " + workRecord_id + " and workStepCount = " + (
              Integer.parseInt(para[7]) + 1));
        }  
      MessagesBD messagesBD = new MessagesBD();
      if (nextTransactType.equals("3")) {
        if (curActivityIsDivide && !nextActivityIsGanther) {
          stmt.execute(" update JSDB.JSF_WORK set workListControl = 0 where workTable_id = " + 
              workTable_id + " and workRecord_Id = " + workRecord_id + " and workStatus = 0  and parallel_id = " + 
              workParallelId + " and parallel_step=" + (workParallelStep + 1));
        } else {
          stmt.execute(" update JSDB.JSF_WORK set workListControl = 0 where workTable_id = " + 
              workTable_id + " and workRecord_Id = " + workRecord_id + " and workStatus = 0 and workStepCount = " + (
              Integer.parseInt(para[7]) + 1));
        } 
        String minWorkId = "0";
        String itemTitle = "", toUserId = "";
        if (curActivityIsDivide && !nextActivityIsGanther) {
          sql = "select max(wf_work_id) from JSDB.JSF_WORK where workTable_id = " + 
            workTable_id + " and workRecord_Id = " + workRecord_id + " and workStatus = 0 and parallel_id = " + 
            workParallelId + " and parallel_step=" + (workParallelStep + 1);
        } else {
          sql = "select max(wf_work_id) from JSDB.JSF_WORK where workTable_id = " + 
            workTable_id + " and workRecord_Id = " + workRecord_id + " and workStatus = 0 and workStepCount = " + (
            Integer.parseInt(para[7]) + 1);
        } 
        rs = stmt.executeQuery(sql);
        if (rs.next())
          minWorkId = rs.getString(1); 
        rs.close();
        rs = stmt.executeQuery("select worktitle,wf_curemployee_id from JSDB.jsf_work where wf_work_id=" + minWorkId);
        if (rs.next()) {
          itemTitle = rs.getString(1);
          toUserId = rs.getString(2);
        } 
        rs.close();
        stmt.execute(" update JSDB.JSF_WORK set workListControl = 1 where wf_work_id = " + minWorkId);
        msgVO = new MessagesVO();
        msgVO.setMessage_date_begin(now);
        msgVO.setMessage_date_end(new Date("2050/1/1"));
        msgVO.setMessage_send_UserId(Long.parseLong(wf_submitEmployee_id));
        msgVO.setMessage_send_UserName(workSubmitPerson);
        msgVO.setMessage_show(1);
        msgVO.setMessage_status(1);
        msgVO.setMessage_time(now);
        msgVO.setMessage_title(itemTitle);
        msgVO.setMessage_toUserId(Long.parseLong(toUserId));
        msgVO.setMessage_type("jsflow");
        msgVO.setData_id(Long.parseLong(minWorkId));
        msgVO.setSendSMS((para.length > 22) ? Integer.valueOf(para[22]).intValue() : 1);
        msgVO.setMessage_url("/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + minWorkId);
        messagesBD.messageAdd(msgVO);
      } else if (!"1".equals(subProcType)) {
        if (msgList.size() > 0) {
          messagesBD.messageArrayAdd(msgList);
        } else {
          String itemTitle = "", toUserId = "";
          if (curActivityIsDivide && !nextActivityIsGanther) {
            sql = "select worktitle,wf_curemployee_id,wf_work_id from JSDB.JSF_WORK where workTable_id = " + 
              workTable_id + " and workRecord_Id = " + workRecord_id + " and workStatus = 0 and worklistcontrol=1 and parallel_id = " + 
              workParallelId + " and parallel_step=" + (workParallelStep + 1);
          } else {
            sql = "select worktitle,wf_curemployee_id,wf_work_id from JSDB.JSF_WORK where workTable_id = " + 
              workTable_id + " and workRecord_Id = " + workRecord_id + " and workStatus = 0 and worklistcontrol=1 and workStepCount = " + (
              Integer.parseInt(para[7]) + 1);
          } 
          rs = stmt.executeQuery(sql);
          while (rs.next()) {
            itemTitle = rs.getString(1);
            toUserId = rs.getString(2);
            String tmpWorkId = rs.getString(3);
            msgVO = new MessagesVO();
            msgVO.setMessage_date_begin(now);
            msgVO.setMessage_date_end(new Date("2050/1/1"));
            msgVO.setMessage_send_UserId(Long.parseLong(wf_submitEmployee_id));
            msgVO.setMessage_send_UserName(workSubmitPerson);
            msgVO.setMessage_show(1);
            msgVO.setMessage_status(1);
            msgVO.setMessage_time(now);
            msgVO.setMessage_title(itemTitle);
            msgVO.setMessage_toUserId(Long.parseLong(toUserId));
            msgVO.setMessage_type("jsflow");
            msgVO.setData_id(Long.parseLong(tmpWorkId));
            msgVO.setSendSMS((para.length > 22) ? Integer.valueOf(para[22]).intValue() : 1);
            msgVO.setMessage_url("/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + tmpWorkId);
            messagesBD.messageAdd(msgVO);
          } 
          rs.close();
        } 
      } 
      List<String[]> messageInfo = (List)new ArrayList<String>();
      messageInfo.add(new String[] { dealwithUserId.toString(), curWorkId });
      if (curTransactType.equals("2") || curTransactType.equals("0") || (activityClass.equals("0") && subProcType.equals("0"))) {
        if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
          workTitle = String.valueOf(remindFieldValue) + workFileType + "已办理完毕！";
        } else {
          workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "已办理完毕！";
        } 
        if (!para[18].equals("")) {
          workTitle = para[18];
          if ("shandongguotou".equals(SystemCommon.getCustomerName()))
            workTitle = String.valueOf(workTitle) + workFileType + "已办理完毕！"; 
        } 
        if (curActivityIsDivide) {
          sql = "select wf_curemployee_id,wf_work_id from jsf_work  where workStatus = 0 and workTable_id = " + 
            workTable_id + " and workRecord_id = " + workRecord_id + " and parallel_id = " + 
            workParallelId + " and parallel_step=" + workParallelStep + " and wf_work_id <> " + curWorkId;
        } else {
          sql = "select wf_curemployee_id,wf_work_id from jsf_work  where workStatus = 0 and workTable_id = " + 
            workTable_id + " and workRecord_id = " + workRecord_id + 
            " and workStepCount = " + para[7] + " and wf_work_id <> " + curWorkId;
        } 
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
          String[] temp = new String[2];
          temp[0] = rs.getString(1);
          temp[1] = rs.getString(2);
          messageInfo.add(temp);
        } 
        rs.close();
        String dealwithTimeStr = "";
        if (databaseType.indexOf("mysql") >= 0) {
          dealwithTimeStr = "DEALWITHTIME='" + now.toLocaleString() + "'";
        } else {
          dealwithTimeStr = "DEALWITHTIME=JSDB.FN_STRTODATE('" + now.toLocaleString() + "','')";
        } 
        if (curActivityIsDivide) {
          stmt.execute("update JSDB.JSF_work set workStatus = 101," + dealwithTimeStr + " , workTitle = '" + workTitle + "',workAllowCancel = 0 where workStatus = 0 and workTable_id = " + 
              workTable_id + " and workRecord_id = " + workRecord_id + " and parallel_curactid = " + 
              workParallelCurActId + " and parallel_step=" + workParallelStep + " and wf_work_id <> " + curWorkId);
        } else {
          stmt.execute("update JSDB.JSF_work set workStatus = 101," + dealwithTimeStr + ", workTitle = '" + workTitle + "',workAllowCancel = 0 where workStatus = 0 and workTable_id = " + 
              workTable_id + " and workRecord_id = " + workRecord_id + 
              " and workStepCount = " + para[7] + " and wf_work_id <> " + curWorkId);
        } 
      } 
      conn.commit();
      conn.setAutoCommit(isCommit);
      stmt.close();
      conn.close();
      for (int j = 0; j < messageInfo.size(); j++) {
        String[] temp = messageInfo.get(j);
        messagesBD.readMessage(temp[1], temp[0], "jsflow");
      } 
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.rollback();
          conn.close();
        } catch (SQLException se) {
          se.printStackTrace();
        }  
      e.printStackTrace();
      result = Integer.valueOf("-1");
      throw e;
    } 
    return result;
  }
  
  public Integer selfSendWorkflowButton(String[] para, String[] passRoundUser) throws Exception {
    Integer result = Integer.valueOf("0");
    DataSourceBase dsb = new DataSourceBase();
    DataSource ds = dsb.getDataSource();
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      String nextActivityName = para[0];
      String nextActivityId = para[1];
      String curWorkId = para[2];
      String deadLineTime = para[3];
      String pressTime = para[4];
      String workTitle = "";
      String remindFieldValue = para[8];
      String domainId = "0";
      String initActivity = "0";
      ResultSet rs = stmt.executeQuery("SELECT DISTINCT DOMAIN_ID FROM JSF_P_ACTIVITY WHERE WF_ACTIVITY_ID=" + nextActivityId);
      if (rs.next())
        domainId = rs.getString(1); 
      rs.close();
      String sql = "select workFileType,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workProcess_id,workTable_id,workRecord_id,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,transactType,initActivity from JSDB.JSF_work where wf_work_id=" + 

        
        curWorkId;
      Date now = new Date();
      rs = stmt.executeQuery(sql);
      String workFileType = "";
      String workSubmitPerson = "";
      String workSubmitTime = "";
      String wf_submitEmployee_id = "";
      String workProcess_id = "";
      String workTable_id = "";
      String workRecord_id = "";
      String isSubProcWork = "", pareProcActiId = "", pareStepCount = "";
      String pareTableId = "", pareRecordId = "", pareProcNextActiId = "", submitOrg = "";
      if (rs.next()) {
        workFileType = rs.getString("workFileType");
        workSubmitPerson = rs.getString("workSubmitPerson");
        workSubmitTime = rs.getString("workSubmitTime");
        if (workSubmitTime.indexOf(".") > 0)
          workSubmitTime = workSubmitTime.substring(0, workSubmitTime.indexOf(".")); 
        wf_submitEmployee_id = rs.getString("wf_submitEmployee_id");
        workProcess_id = rs.getString("workProcess_id");
        workTable_id = rs.getString("workTable_id");
        workRecord_id = rs.getString("workRecord_id");
        isSubProcWork = rs.getString("isSubProcWork");
        pareProcActiId = rs.getString("pareProcActiId");
        pareStepCount = rs.getString("pareStepCount");
        pareTableId = rs.getString("pareTableId");
        pareRecordId = rs.getString("pareRecordId");
        pareProcNextActiId = rs.getString("pareProcNextActiId");
        submitOrg = rs.getString("submitOrg");
        initActivity = rs.getString("initActivity");
      } 
      rs.close();
      String sendType = para[21];
      if ("reSend".equals(sendType))
        stmt.execute(" delete from JSDB.JSF_WORK where workTable_id = " + 
            workTable_id + " and workRecord_Id = " + workRecord_id + " and (workstatus=2 or workstatus=102) and initActivity='" + initActivity + "'"); 
      String tmpUser = "";
      String insertUser = "";
      List<MessagesVO> msgList = new ArrayList();
      MessagesVO msgVO = new MessagesVO();
      for (int m = 0; m < passRoundUser.length; m++) {
        if (insertUser.indexOf(String.valueOf(passRoundUser[m]) + ",") < 0) {
          insertUser = String.valueOf(insertUser) + passRoundUser[m] + ",";
          tmpUser = passRoundUser[m];
          if (!tmpUser.equals("")) {
            if (tmpUser.startsWith("$"))
              tmpUser = tmpUser.substring(1); 
            if (tmpUser.endsWith("$"))
              tmpUser = tmpUser.substring(0, tmpUser.length() - 1); 
            String tmp = "select * from JSDB.JSF_WORK where workTable_id = " + 
              workTable_id + " and workRecord_Id = " + workRecord_id + " and (workstatus=2 or workstatus=102) and  wf_curEmployee_id=" + tmpUser;
            rs = stmt.executeQuery(tmp);
            if (rs.next()) {
              rs.close();
              stmt.executeUpdate("update jsf_work set workstatus=102,worklistcontrol=0 where workTable_id = " + 
                  workTable_id + " and workRecord_Id = " + workRecord_id + " and (workstatus=2 or workstatus=102) and  wf_curEmployee_id=" + tmpUser);
            } 
            String workId = getTableId2();
            if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
              workTitle = String.valueOf(remindFieldValue) + workFileType + "等待您的审阅！";
            } else {
              workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "等待您的审阅！";
            } 
            if (para[18] != null && !para[18].equals(""))
              workTitle = para[18]; 
            if (databaseType.indexOf("mysql") >= 0) {
              tmpSql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType,  workCurStep, workTitle, workLeftLinkFile, workMainLinkFile,  workListControl, workActivity, workSubmitPerson, workSubmitTime,  wf_submitEmployee_id, workReadMarker, workType, workProcess_id,  worktable_id, workrecord_id, workDeadLine, workPressTime,  workCreateDate, workStepCount, initActivity, initStepCount, isSubProcWork, pareProcActiId,  pareStepCount, pareTableId, pareRecordId, pareProcNextActiId, submitOrg, domain_id ,emergence,isStandForWork, initActivityname) values ( " + 







                
                workId + ", " + tmpUser + ",2,'" + workFileType + "','" + 
                nextActivityName + "','" + workTitle + "','','" + para[10] + "',1," + 
                nextActivityId + ",'" + workSubmitPerson + "','" + 
                workSubmitTime + "'," + wf_submitEmployee_id + 
                ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                "," + deadLineTime + "," + pressTime + ", '" + 
                now.toLocaleString() + "', " + 
                Integer.parseInt(para[7]) + "," + nextActivityId + ", " + Integer.parseInt(para[7]) + "," + isSubProcWork + "," + pareProcActiId + "," + 
                pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + domainId + "," + para[19] + ",0,'" + para[22] + "')";
            } else {
              tmpSql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType,  workCurStep, workTitle, workLeftLinkFile, workMainLinkFile,  workListControl, workActivity, workSubmitPerson, workSubmitTime,  wf_submitEmployee_id, workReadMarker, workType, workProcess_id,  worktable_id, workrecord_id, workDeadLine, workPressTime,  workCreateDate, workStepCount, initActivity, initStepCount, isSubProcWork, pareProcActiId,  pareStepCount, pareTableId, pareRecordId, pareProcNextActiId, submitOrg, domain_id,emergence ,isStandForWork, initActivityname) values ( " + 







                
                workId + ", " + tmpUser + ",2,'" + workFileType + "','" + 
                nextActivityName + "','" + workTitle + "','','" + para[10] + "',1," + 
                nextActivityId + ",'" + workSubmitPerson + "',JSDB.FN_STRTODATE('" + 
                workSubmitTime + "','')," + wf_submitEmployee_id + 
                ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                "," + deadLineTime + "," + pressTime + ", JSDB.FN_STRTODATE('" + 
                now.toLocaleString() + "','yyyy-mm-dd hh24:mi:ss'), " + 
                Integer.parseInt(para[7]) + "," + nextActivityId + ", " + Integer.parseInt(para[7]) + "," + isSubProcWork + "," + pareProcActiId + "," + 
                pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "'," + domainId + "," + para[19] + ",0,'" + para[22] + "')";
            } 
            stmt.execute(tmpSql);
            msgVO = new MessagesVO();
            msgVO.setMessage_date_begin(now);
            msgVO.setMessage_date_end(new Date("2050/1/1"));
            msgVO.setMessage_send_UserId(Long.parseLong(wf_submitEmployee_id));
            msgVO.setMessage_send_UserName(workSubmitPerson);
            msgVO.setMessage_show(1);
            msgVO.setMessage_status(1);
            msgVO.setMessage_time(now);
            msgVO.setMessage_title(workTitle);
            msgVO.setMessage_toUserId(Long.parseLong(tmpUser));
            msgVO.setMessage_type("jsflow");
            msgVO.setData_id(Long.parseLong(workId));
            msgVO.setMessage_url("/jsoa/jsflow/item/jump_dealwith.jsp?status=2&workId=" + workId);
            msgList.add(msgVO);
          } 
        } 
      } 
      if (msgList.size() > 0) {
        MessagesBD messagesBD = new MessagesBD();
        messagesBD.messageArrayAdd(msgList);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      result = Integer.valueOf("-1");
      throw e;
    } finally {
      try {
        stmt.close();
        conn.close();
      } catch (SQLException se) {
        se.printStackTrace();
      } 
    } 
    return result;
  }
  
  public Integer endWorkflowButton(Map dealwithPara, String[] para) throws Exception {
    Integer result = Integer.valueOf("0");
    DataSourceBase dsb = new DataSourceBase();
    DataSource ds = dsb.getDataSource();
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      String domainId = "0";
      ResultSet rs = stmt.executeQuery("SELECT WF_DEALWITH_ID FROM JSDB.JSF_DEALWITH WHERE DATABASETABLE_ID=" + dealwithPara.get("tableId") + " AND DATABASERECORD_ID=" + dealwithPara.get("recordId") + " AND ACTIVITY_ID=" + dealwithPara.get("curActivityId") + " AND CURSTEPCOUNT=" + dealwithPara.get("stepCount"));
      String wf_dealwith_id = "";
      if (rs.next())
        wf_dealwith_id = rs.getString(1); 
      rs.close();
      if (wf_dealwith_id.equals("")) {
        wf_dealwith_id = getTableId2();
        int curActivityStatus = 0;
        if (dealwithPara.get("nextActivityName") != null && dealwithPara.get("nextActivityName").toString().equals("-1"))
          curActivityStatus = 1; 
        String subProcWorkId = "0", str1 = "1";
        if (dealwithPara.get("activityClass") != null)
          str1 = dealwithPara.get("activityClass").toString(); 
        if (dealwithPara.get("activityClass") != null && dealwithPara.get("subProcWorkId") != null && !"".equals(dealwithPara.get("subProcWorkId"))) {
          subProcWorkId = dealwithPara.get("subProcWorkId").toString();
          if (dealwithPara.get("activityClass").toString().equals("0") && dealwithPara.get("subProcWorkId").toString().equals("0")) {
            rs = stmt.executeQuery("SELECT SUBPROCWORKID FROM JSDB.JSF_DEALWITH WHERE DATABASETABLE_ID=" + dealwithPara.get("tableId") + " AND DATABASERECORD_ID=" + dealwithPara.get("recordId"));
            if (rs.next())
              subProcWorkId = rs.getString("subProcWorkId"); 
            rs.close();
          } 
        } 
        stmt.execute("INSERT INTO JSDB.JSF_DEALWITH (WF_DEALWITH_ID,DATABASETABLE_ID,DATABASERECORD_ID,ACTIVITYNAME,ACTIVITY_ID,NEXTACTIVITYNAME,NEXTACTIVITYID,CURACTIVITYSTATUS,CURSTEPCOUNT,ACTIVITYCLASS,SUBPROCWORKID,DOMAIN_ID) VALUES (" + 
            wf_dealwith_id + "," + dealwithPara.get("tableId") + "," + dealwithPara.get("recordId") + ",'" + dealwithPara.get("curActivityName") + "'," + dealwithPara.get("curActivityId") + ",'" + dealwithPara.get("nextActivityName") + "'," + dealwithPara.get("nextActivityId") + "," + curActivityStatus + "," + dealwithPara.get("stepCount") + "," + str1 + "," + subProcWorkId + "," + domainId + ")");
      } else if (!dealwithPara.get("nextActivityId").toString().equals("-1") && 
        !dealwithPara.get("curActivityId").toString().equals("0")) {
        stmt.execute("UPDATE JSDB.JSF_DEALWITH SET NEXTACTIVITYID=" + dealwithPara.get("nextActivityId") + ",NEXTACTIVITYNAME='" + dealwithPara.get("nextActivityName") + "' " + 
            "WHERE WF_DEALWITH_ID=" + wf_dealwith_id);
      } 
      Date now = new Date();
      String commentField = "";
      if (dealwithPara.get("commentField") != null)
        commentField = dealwithPara.get("commentField").toString(); 
      String rangeName = (dealwithPara.get("userScope") == null) ? "" : dealwithPara.get("userScope").toString();
      String rangeIdStr = (dealwithPara.get("scopeId") == null) ? "" : dealwithPara.get("scopeId").toString();
      String modiCommentId = (dealwithPara.get("modiCommentId") == null) ? "" : dealwithPara.get("modiCommentId").toString();
      if ("".equals(modiCommentId) || dealwithPara.get("commentField") == null || "-1".equals(dealwithPara.get("commentField")) || "".equals(dealwithPara.get("commentField"))) {
        if (!dealwithPara.get("isStandForWork").toString().equals("1")) {
          String wf_dealwithcomment_id = getTableId2();
          String tmpSql = "";
          String str1 = 
            SystemCommon.getDatabaseType();
          if (str1.indexOf("mysql") >= 0) {
            tmpSql = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,COMMENTFIELD,DOMAIN_ID,RANGENAME,RANGEIDSTR,signcomment) VALUES (" + 
              wf_dealwithcomment_id + "," + 
              dealwithPara.get("userId") + ",'" + 
              dealwithPara.get("comment") + "','" + 
              now.toLocaleString() + "'," + wf_dealwith_id + 
              ",0,'','" + commentField + "'," + domainId + 
              ",'" + rangeName + "','" + rangeIdStr + "','" + dealwithPara.get("signcomment") + "')";
          } else {
            tmpSql = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,COMMENTFIELD,DOMAIN_ID,RANGENAME,RANGEIDSTR,signcomment) VALUES (" + 
              wf_dealwithcomment_id + "," + 
              dealwithPara.get("userId") + ",'" + 
              dealwithPara.get("comment") + 
              "',JSDB.FN_STRTODATE('" + 
              now.toLocaleString() + "','')," + 
              wf_dealwith_id + ",0,'','" + commentField + 
              "'," + domainId + ",'" + rangeName + "','" + 
              rangeIdStr + "','" + dealwithPara.get("signcomment") + "')";
          } 
          stmt.execute(tmpSql);
        } else {
          String wf_dealwithcomment_id = getTableId2();
          String empName = "";
          rs = stmt.executeQuery(
              "SELECT EMPNAME FROM JSDB.ORG_EMPLOYEE WHERE EMP_ID=" + 
              dealwithPara.get("userId"));
          if (rs.next())
            empName = rs.getString(1); 
          String tmpSql = "";
          String str1 = 
            SystemCommon.getDatabaseType();
          if (str1.indexOf("mysql") >= 0) {
            tmpSql = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,ISSTANDFORCOMM,COMMENTFIELD,DOMAIN_ID,RANGENAME,RANGEIDSTR,signcomment) VALUES (" + 
              wf_dealwithcomment_id + "," + 
              dealwithPara.get("standForUserId") + ",'" + 
              dealwithPara.get("comment") + "','" + 
              now.toLocaleString() + "'," + wf_dealwith_id + 
              "," + dealwithPara.get("userId") + ",'" + 
              empName + "',1,'" + commentField + "'," + 
              domainId + ",'" + rangeName + "','" + 
              rangeIdStr + "','" + dealwithPara.get("signcomment") + "')";
          } else {
            tmpSql = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,ISSTANDFORCOMM,COMMENTFIELD,DOMAIN_ID,RANGENAME,RANGEIDSTR,signcomment) VALUES (" + 
              wf_dealwithcomment_id + "," + 
              dealwithPara.get("standForUserId") + ",'" + 
              dealwithPara.get("comment") + 
              "',JSDB.FN_STRTODATE('" + 
              now.toLocaleString() + "','')," + 
              wf_dealwith_id + "," + 
              dealwithPara.get("userId") + ",'" + empName + 
              "',1,'" + commentField + "'," + domainId + 
              ",'" + rangeName + "','" + rangeIdStr + "','" + dealwithPara.get("signcomment") + "')";
          } 
          stmt.execute(tmpSql);
        } 
      } else if (!dealwithPara.get("isStandForWork").toString().equals("1")) {
        String tmpSql = "";
        String str1 = 
          SystemCommon.getDatabaseType();
        if (str1.indexOf("mysql") >= 0) {
          tmpSql = "update JSDB.JSF_DEALWITHCOMMENT set DEALWITHEMPLOYEE_ID='" + dealwithPara.get("userId") + "'," + 
            "DEALWITHEMPLOYEECOMMENT='" + dealwithPara.get("comment") + "',DEALWITHTIME='" + now.toLocaleString() + "',WF_DEALWITH_ID='" + wf_dealwith_id + "'," + 
            "STANDFORUSERID=0,STANDFORUSERNAME='',COMMENTFIELD='" + commentField + "',DOMAIN_ID='" + domainId + "',RANGENAME='" + rangeName + "',RANGEIDSTR='" + rangeIdStr + "',signcomment='" + dealwithPara.get("signcomment") + "' where WF_DEALWITHCOMMENT_ID=" + modiCommentId;
        } else {
          tmpSql = "update JSDB.JSF_DEALWITHCOMMENT set DEALWITHEMPLOYEE_ID='" + dealwithPara.get("userId") + "'," + 
            "DEALWITHEMPLOYEECOMMENT='" + dealwithPara.get("comment") + "',DEALWITHTIME=JSDB.FN_STRTODATE('" + 
            now.toLocaleString() + "',''),WF_DEALWITH_ID='" + wf_dealwith_id + "'," + 
            "STANDFORUSERID=0,STANDFORUSERNAME='',COMMENTFIELD='" + commentField + "',DOMAIN_ID='" + domainId + "',RANGENAME='" + rangeName + "',RANGEIDSTR='" + rangeIdStr + "',signcomment='" + dealwithPara.get("signcomment") + "' where WF_DEALWITHCOMMENT_ID=" + modiCommentId;
        } 
        stmt.execute(tmpSql);
      } else {
        String empName = "";
        rs = stmt.executeQuery(
            "SELECT EMPNAME FROM JSDB.ORG_EMPLOYEE WHERE EMP_ID=" + 
            dealwithPara.get("userId"));
        if (rs.next())
          empName = rs.getString(1); 
        String tmpSql = "";
        String str1 = 
          SystemCommon.getDatabaseType();
        if (str1.indexOf("mysql") >= 0) {
          tmpSql = "update JSDB.JSF_DEALWITHCOMMENT set DEALWITHEMPLOYEE_ID='" + dealwithPara.get("standForUserId") + "'," + 
            "DEALWITHEMPLOYEECOMMENT='" + dealwithPara.get("comment") + "',DEALWITHTIME='" + now.toLocaleString() + "',WF_DEALWITH_ID='" + wf_dealwith_id + "'," + 
            "STANDFORUSERID='" + dealwithPara.get("userId") + "',STANDFORUSERNAME='" + empName + "',ISSTANDFORCOMM='1' ,COMMENTFIELD='" + commentField + "'," + 
            "DOMAIN_ID='" + domainId + "',RANGENAME='" + rangeName + "',RANGEIDSTR='" + rangeIdStr + "',signcomment='" + dealwithPara.get("signcomment") + "' where WF_DEALWITHCOMMENT_ID=" + modiCommentId;
        } else {
          tmpSql = "update JSDB.JSF_DEALWITHCOMMENT set DEALWITHEMPLOYEE_ID='" + dealwithPara.get("standForUserId") + "'," + 
            "DEALWITHEMPLOYEECOMMENT='" + dealwithPara.get("comment") + "',DEALWITHTIME=JSDB.FN_STRTODATE('" + 
            now.toLocaleString() + "',''),WF_DEALWITH_ID='" + wf_dealwith_id + "'," + 
            "STANDFORUSERID='" + dealwithPara.get("userId") + "',STANDFORUSERNAME='" + empName + "',ISSTANDFORCOMM='1' ,COMMENTFIELD='" + commentField + "',DOMAIN_ID='" + domainId + "',RANGENAME='" + rangeName + "',RANGEIDSTR='" + rangeIdStr + "',signcomment='" + dealwithPara.get("signcomment") + "' where WF_DEALWITHCOMMENT_ID=" + modiCommentId;
        } 
        stmt.execute(tmpSql);
      } 
      String databaseType = SystemCommon.getDatabaseType();
      String nextActivityName = para[0];
      String nextActivityId = para[1];
      String curWorkId = para[2];
      String deadLineTime = para[3];
      String pressTime = para[4];
      String activityClass = para[16];
      String subProcType = para[17];
      String sql = "select workFileType,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workProcess_id,workTable_id,workRecord_id,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,transactType from JSDB.jsf_work where wf_work_id=" + 

        
        curWorkId;
      now = new Date();
      String workFileType = "";
      String workSubmitPerson = "";
      String workSubmitTime = "";
      String wf_submitEmployee_id = "";
      String workProcess_id = "";
      String workTable_id = "";
      String workRecord_id = "";
      String isSubProcWork = "", pareProcActiId = "", pareStepCount = "";
      String pareTableId = "", pareRecordId = "", pareProcNextActiId = "", submitOrg = "";
      String curTransactType = "1";
      rs = stmt.executeQuery(sql);
      if (rs.next()) {
        workFileType = rs.getString("workFileType");
        workSubmitPerson = rs.getString("workSubmitPerson");
        workSubmitTime = rs.getString("workSubmitTime");
        if (workSubmitTime.indexOf(".") > 0)
          workSubmitTime = workSubmitTime.substring(0, workSubmitTime.indexOf(".")); 
        wf_submitEmployee_id = rs.getString("wf_submitEmployee_id");
        workProcess_id = rs.getString("workProcess_id");
        workTable_id = rs.getString("workTable_id");
        workRecord_id = rs.getString("workRecord_id");
        isSubProcWork = rs.getString("isSubProcWork");
        pareProcActiId = rs.getString("pareProcActiId");
        pareStepCount = rs.getString("pareStepCount");
        pareTableId = rs.getString("pareTableId");
        pareRecordId = rs.getString("pareRecordId");
        pareProcNextActiId = rs.getString("pareProcNextActiId");
        submitOrg = rs.getString("submitOrg");
        curTransactType = rs.getString("transactType");
      } 
      rs.close();
      int hasDoneFile = 0;
      rs = stmt.executeQuery("select count(wf_work_id) from jsf_work where workstatus = 101 and worktable_id = " + workTable_id + 
          " and workrecord_id = " + workRecord_id + " and wf_curemployee_id = " + 
          para[13] + " and workprocess_id=" + workProcess_id);
      if (rs.next())
        hasDoneFile = rs.getInt(1); 
      rs.close();
      if ("last".equals(SystemCommon.getRepeatFileDealwith()) && hasDoneFile > 0)
        stmt.execute("update JSDB.JSF_WORK set WORKDELETE =1 where workstatus = 101 and worktable_id = " + workTable_id + 
            " and workrecord_id = " + workRecord_id + " and wf_curemployee_id = " + 
            para[13] + " and workprocess_id=" + workProcess_id); 
      stmt.execute("delete from JSDB.JSF_WORK where workTable_Id=" + workTable_id + " and workRecord_id=" + 
          workRecord_id + " and workStepCount=" + para[7] + " and standForUserId=" + 
          para[13]);
      String remindFieldValue = para[8];
      int k = 0;
      if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
        k = (String.valueOf(remindFieldValue) + workFileType + "您已办理完毕！").length() - 100;
      } else {
        k = (String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "您已办理完毕！").length() - 100;
      } 
      if (k > 0)
        remindFieldValue.substring(0, remindFieldValue.length() - k); 
      String standTitle = "";
      String workTitle = "";
      if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
        workTitle = String.valueOf(remindFieldValue) + workFileType + "您已办理完毕！";
      } else {
        workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "您已办理完毕！";
      } 
      if (!para[18].equals(""))
        workTitle = para[18]; 
      if (para[12].equals("1")) {
        rs = stmt.executeQuery("SELECT EMPNAME FROM JSDB.ORG_EMPLOYEE WHERE EMP_ID=" + para[13]);
        String myTmp = "";
        if (rs.next())
          myTmp = rs.getString(1); 
        rs.close();
        myTmp = String.valueOf(workTitle) + "（" + myTmp + "代办）";
        if (myTmp.length() > 100)
          myTmp = myTmp.substring(0, 99); 
        if (databaseType.indexOf("mysql") >= 0) {
          stmt.execute("UPDATE JSDB.JSF_WORK SET WORKSTATUS=101,DEALWITHTIME='" + 
              now.toLocaleString() + "',WORKTITLE='" + myTmp + "',WORKALLOWCANCEL=" + para[6] + " where workTable_Id=" + workTable_id + " and workRecord_id=" + 
              workRecord_id + " and workStepCount=" + para[7] + " and wf_curemployee_id=" + 
              para[14]);
        } else {
          stmt.execute("UPDATE JSDB.JSF_WORK SET WORKSTATUS=101,DEALWITHTIME=JSDB.FN_STRTODATE('" + 
              now.toLocaleString() + "',''),WORKTITLE='" + myTmp + "',WORKALLOWCANCEL=" + para[6] + " where workTable_Id=" + workTable_id + " and workRecord_id=" + 
              workRecord_id + " and workStepCount=" + para[7] + " and wf_curemployee_id=" + 
              para[14]);
        } 
        standTitle = "（代" + para[15] + "办理）";
      } 
      workTitle = String.valueOf(workTitle) + standTitle;
      if (workTitle.length() > 100)
        workTitle = workTitle.substring(0, 99); 
      sql = "update JSDB.JSF_WORK set workstatus = 101,DEALWITHTIME=";
      if (databaseType.indexOf("mysql") >= 0) {
        sql = String.valueOf(sql) + "'" + now.toLocaleString() + "', workTitle = '" + workTitle + "', ";
      } else {
        sql = String.valueOf(sql) + "JSDB.FN_STRTODATE('" + now.toLocaleString() + "',''), workTitle = '" + workTitle + "', ";
      } 
      if ("first".equals(SystemCommon.getRepeatFileDealwith()) && hasDoneFile > 0)
        sql = String.valueOf(sql) + "WORKDELETE =1,"; 
      sql = String.valueOf(sql) + "workAllowCancel=" + para[6] + " where wf_work_id = " + curWorkId;
      stmt.executeUpdate(sql);
      String temp = "";
      String toUserId = "";
      rs = stmt.executeQuery("select max(wf_work_id) from JSDB.jsf_work where workTable_id = " + 
          workTable_id + " and workRecord_Id = " + workRecord_id + " and workstatus = 0 ");
      if (rs.next())
        temp = rs.getString(1); 
      rs.close();
      String workCreateDate = "", workdDeadlineDate = "", workDeadLinePressDate = "";
      rs = stmt.executeQuery("select wf_curemployee_id,worktitle,workcreatedate,workdeadlinedate,workdeadlinepressdate from JSDB.jsf_work where wf_work_id=" + temp);
      if (rs.next()) {
        toUserId = rs.getString(1);
        workTitle = rs.getString(2);
        workCreateDate = rs.getString(3);
        workdDeadlineDate = rs.getString(4);
        workDeadLinePressDate = rs.getString(5);
        if (workdDeadlineDate == null)
          workdDeadlineDate = ""; 
        if (workDeadLinePressDate == null)
          workDeadLinePressDate = ""; 
        if (workCreateDate.indexOf(".") > 0)
          workCreateDate = workCreateDate.substring(0, workCreateDate.indexOf(".")); 
        if (workdDeadlineDate.indexOf(".") > 0)
          workdDeadlineDate = workdDeadlineDate.substring(0, workdDeadlineDate.indexOf(".")); 
        if (workDeadLinePressDate.indexOf(".") > 0)
          workDeadLinePressDate = workDeadLinePressDate.substring(0, workDeadLinePressDate.indexOf(".")); 
      } 
      rs.close();
      stmt.execute(" update JSDB.JSF_WORK set workListControl = 1 where wf_work_id = " + temp);
      if ("3".equals(curTransactType)) {
        MessagesVO msgVO = new MessagesVO();
        msgVO.setMessage_date_begin(now);
        msgVO.setMessage_date_end(new Date("2050/1/1"));
        msgVO.setMessage_send_UserId(0L);
        msgVO.setMessage_send_UserName("系统提醒");
        msgVO.setMessage_show(1);
        msgVO.setMessage_status(1);
        msgVO.setMessage_time(now);
        msgVO.setMessage_title(workTitle);
        msgVO.setMessage_toUserId(Long.parseLong(toUserId));
        msgVO.setMessage_type("jsflow");
        msgVO.setData_id(Long.parseLong(temp));
        msgVO.setMessage_url("/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + temp);
        (new MessagesBD()).messageAdd(msgVO);
        if (!workCreateDate.equals(workdDeadlineDate) && !"".equals(workdDeadlineDate) && !"null".equals(workdDeadlineDate)) {
          workDeadLinePressDate = workDeadLinePressDate.replaceAll("-", "\\/");
          Date pressTimeTemp = new Date(workDeadLinePressDate);
          msgVO = new MessagesVO();
          msgVO.setMessage_date_begin(pressTimeTemp);
          msgVO.setMessage_date_end(new Date("2050/1/1"));
          msgVO.setMessage_send_UserId(0L);
          msgVO.setMessage_send_UserName("系统提醒");
          msgVO.setMessage_show(1);
          msgVO.setMessage_status(1);
          msgVO.setMessage_time(now);
          msgVO.setMessage_title(workTitle);
          msgVO.setMessage_toUserId(Long.parseLong(toUserId));
          msgVO.setMessage_type("jsflow");
          msgVO.setData_id(Long.parseLong(temp));
          msgVO.setMessage_url("/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + temp);
          (new MessagesBD()).messageAdd(msgVO);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      result = Integer.valueOf("-1");
      throw e;
    } finally {
      try {
        stmt.close();
        conn.close();
      } catch (SQLException sQLException) {}
    } 
    return result;
  }
  
  public String getTableId2() throws Exception {
    String seq = "0";
    DataSourceBase dsb = new DataSourceBase();
    DataSource ds = dsb.getDataSource();
    Connection conn = null;
    Statement stmt = null;
    try {
      ResultSet rs = null;
      conn = ds.getConnection();
      stmt = conn.createStatement();
      if (SystemCommon.getDatabaseType().indexOf("oracle") >= 0) {
        rs = stmt.executeQuery("Select HIBERNATE_SEQUENCE.Nextval From dual");
        if (rs.next())
          seq = rs.getString(1); 
      } else {
        rs = stmt.executeQuery("select seq_seq from JSDB.oa_seq where id=1");
        if (rs.next()) {
          seq = (new StringBuilder(String.valueOf(rs.getLong(1) + 1L))).toString();
          stmt.execute("update JSDB.oa_seq set seq_seq=seq_seq+1");
        } else {
          seq = "1000";
          stmt.execute("insert into JSDB.oa_seq (id, seq_seq) values (1, 1000)");
        } 
      } 
      rs.close();
      stmt.close();
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
    return seq;
  }
  
  public List getStandForUser(String[] userId) throws Exception {
    begin();
    ArrayList<String[]> alist = new ArrayList();
    try {
      String databaseType = SystemCommon.getDatabaseType();
      ResultSet rs = null;
      for (int i = 0; i < userId.length; i++) {
        String sql, tmp[] = { "", "", "", "" };
        tmp[0] = userId[i];
        rs = this.stmt.executeQuery("select empName from JSDB.org_employee where emp_id = " + userId[i]);
        if (rs.next())
          tmp[1] = rs.getString(1); 
        rs.close();
        String nowTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
        if (databaseType.indexOf("mysql") >= 0) {
          sql = "select proxyEmpId, proxyEmpName from JSDB.oa_workproxy where emp_id = " + userId[i] + " and " + 
            "proxyState = 1 and '" + nowTime + "' between beginTime and endTime";
        } else {
          sql = "select proxyEmpId, proxyEmpName from JSDB.oa_workproxy where emp_id = " + userId[i] + " and " + 
            "proxyState = 1 and JSDB.FN_STRTODATE('" + nowTime + "', 'L') between beginTime and endTime";
        } 
        rs = this.stmt.executeQuery(sql);
        if (rs.next()) {
          tmp[2] = rs.getString(1);
          tmp[3] = rs.getString(2);
        } 
        rs.close();
        alist.add(tmp);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public List getStandForUserByProcessAndOrg(String[] userId, String processId, String orgIdString) throws Exception {
    begin();
    ArrayList<String[]> alist = new ArrayList();
    try {
      String databaseType = SystemCommon.getDatabaseType();
      ResultSet rs = null;
      for (int i = 0; i < userId.length; i++) {
        String sql, tmp[] = { "", "", "", "" };
        tmp[0] = userId[i];
        rs = this.stmt.executeQuery("select empName from JSDB.org_employee where emp_id = " + userId[i]);
        if (rs.next())
          tmp[1] = rs.getString(1); 
        rs.close();
        String nowTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
        if (databaseType.indexOf("mysql") >= 0) {
          sql = "select proxyEmpId, proxyEmpName, proxyAllProcess, proxyProcess, proxyOrgId  from JSDB.oa_workproxy where emp_id = " + userId[i] + " and " + 
            "proxyState = 1 AND (proxyAllProcess=1 OR (proxyAllProcess=0 AND proxyProcess IS NOT NULL)) " + 
            "and '" + nowTime + "' between beginTime and endTime";
        } else {
          sql = "select proxyEmpId, proxyEmpName, proxyAllProcess, proxyProcess, proxyOrgId  from JSDB.oa_workproxy where emp_id = " + userId[i] + " and " + 
            "proxyState = 1 AND (proxyAllProcess=1 OR (proxyAllProcess=0 AND proxyProcess IS NOT NULL)) " + 
            "and JSDB.FN_STRTODATE('" + nowTime + "', 'L') between beginTime and endTime";
        } 
        IO2File.printFile("获得代理sql语句：" + sql);
        rs = this.stmt.executeQuery(sql);
        while (rs.next()) {
          String proxyAllProcess = rs.getString(3);
          String proxyProcess = (rs.getString(4) == null) ? "" : rs.getString(4);
          String proxyOrgId = rs.getString(5);
          if ("1".equals(proxyAllProcess) || ("0".equals(proxyAllProcess) && proxyProcess.indexOf("$" + processId + "$") != -1)) {
            if (proxyOrgId != null && !"".equals(proxyOrgId) && !"null".equals(proxyOrgId.toLowerCase())) {
              boolean orgOK = false;
              if (proxyOrgId.startsWith("*")) {
                String[] proxyOrgArr = ("*" + proxyOrgId + "*").split("\\*\\*");
                for (int j = 0; j < proxyOrgArr.length; j++) {
                  if (!"".equals(proxyOrgArr[j]) && 
                    orgIdString.indexOf("$" + proxyOrgArr[j] + "$") != -1) {
                    orgOK = true;
                    break;
                  } 
                } 
              } else if (orgIdString.indexOf("$" + proxyOrgId + "$") != -1) {
                orgOK = true;
              } 
              if (orgOK) {
                tmp[2] = rs.getString(1);
                tmp[3] = rs.getString(2);
              } 
              continue;
            } 
            tmp[2] = rs.getString(1);
            tmp[3] = rs.getString(2);
          } 
        } 
        rs.close();
        alist.add(tmp);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  private List getStandForUserByProcessAndOrgWithConn(String[] userId, String processId, String orgIdString, Connection conn, Statement stmt1) throws Exception {
    ArrayList<String[]> alist = new ArrayList();
    try {
      String databaseType = SystemCommon.getDatabaseType();
      ResultSet rs = null;
      for (int i = 0; i < userId.length; i++) {
        String sql, tmp[] = { "", "", "", "" };
        tmp[0] = userId[i];
        rs = stmt1.executeQuery("select empName from JSDB.org_employee where emp_id = " + userId[i]);
        if (rs.next())
          tmp[1] = rs.getString(1); 
        rs.close();
        Calendar now = Calendar.getInstance();
        String nowTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
        if (databaseType.indexOf("mysql") >= 0) {
          sql = "select proxyEmpId, proxyEmpName, proxyAllProcess, proxyProcess, proxyOrgId  from JSDB.oa_workproxy where emp_id = " + userId[i] + " and " + 
            "proxyState = 1 and '" + nowTime + "' between beginTime and endTime";
        } else {
          sql = "select proxyEmpId, proxyEmpName, proxyAllProcess, proxyProcess, proxyOrgId  from JSDB.oa_workproxy where emp_id = " + userId[i] + " and " + 
            "proxyState = 1 and JSDB.FN_STRTODATE('" + nowTime + "', 'L') between beginTime and endTime";
        } 
        rs = stmt1.executeQuery(sql);
        while (rs.next()) {
          String proxyAllProcess = rs.getString(3);
          String proxyProcess = (rs.getString(4) == null) ? "" : rs.getString(4);
          String proxyOrgId = rs.getString(5);
          if (proxyProcess != null && ("1".equals(proxyAllProcess) || ("0".equals(proxyAllProcess) && proxyProcess.indexOf("$" + processId + "$") != -1))) {
            if (proxyOrgId != null && !"".equals(proxyOrgId) && !"null".equals(proxyOrgId.toLowerCase())) {
              boolean orgOK = false;
              if (proxyOrgId.startsWith("*")) {
                String[] proxyOrgArr = ("*" + proxyOrgId + "*").split("\\*\\*");
                for (int j = 0; j < proxyOrgArr.length; j++) {
                  if (!"".equals(proxyOrgArr[j]) && 
                    orgIdString.indexOf("$" + proxyOrgArr[j] + "$") != -1) {
                    orgOK = true;
                    break;
                  } 
                } 
              } else if (orgIdString.indexOf("$" + proxyOrgId + "$") != -1) {
                orgOK = true;
              } 
              if (orgOK) {
                tmp[2] = rs.getString(1);
                tmp[3] = rs.getString(2);
              } 
              continue;
            } 
            tmp[2] = rs.getString(1);
            tmp[3] = rs.getString(2);
          } 
        } 
        rs.close();
        alist.add(tmp);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } 
    return alist;
  }
  
  private String getUserOrgId(String empId) throws Exception {
    String ret = "";
    begin();
    try {
      String sql = "select b.ORGIDSTRING from org_organization_user a,ORG_ORGANIZATION b where a.org_id=b.org_id and a.emp_id=" + empId;
      ResultSet rs = this.stmt.executeQuery(sql);
      if (rs.next())
        ret = rs.getString(1); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return ret;
  }
  
  public Map getWorkInfoByTableID(String recordId, String tableId) throws Exception {
    Map<Object, Object> result = new HashMap<Object, Object>();
    try {
      begin();
      ResultSet rs = this.stmt.executeQuery("SELECT WORKCURSTEP,WF_SUBMITEMPLOYEE_ID,WORKSUBMITPERSON,WF_WORK_ID,WORKTYPE,WORKACTIVITY,WORKFILETYPE,WORKSUBMITTIME,WORKPROCESS_ID,WORKSTEPCOUNT,ISSTANDFORWORK,STANDFORUSERID,STANDFORUSERNAME FROM JSDB.JSF_WORK WHERE WORKTABLE_ID=" + tableId + " AND WORKRECORD_ID=" + recordId + " AND (WORKSTATUS=1 OR WORKSTATUS=100 OR WORKSTATUS=-1 OR WORKSTATUS=-2)");
      if (rs.next()) {
        result.put("activityName", rs.getString(1));
        result.put("submitPersonId", rs.getString(2));
        result.put("submitPerson", rs.getString(3));
        result.put("work", rs.getString(4));
        result.put("workType", rs.getString(5));
        result.put("activity", rs.getString(6));
        result.put("processName", rs.getString(7));
        result.put("submitTime", rs.getString(8));
        result.put("processId", rs.getString(9));
        result.put("stepCount", rs.getString(10));
        result.put("isStandForWork", rs.getString(11));
        result.put("standForUserId", rs.getString(12));
        result.put("standForUserName", rs.getString(13));
      } 
      result.put("tableId", tableId);
      end();
    } catch (Exception e) {
      end();
      e.printStackTrace();
      throw e;
    } 
    return result;
  }
  
  public void deleteOldRecord(String tableId, String tableName, String recordId) {
    try {
      begin();
      String delSql = "delete from " + tableName + " where " + tableName + "_id=" + recordId;
      if ("1".equals(SystemCommon.getReSubmitDel()))
        delSql = String.valueOf(delSql) + " AND NOT EXISTS (SELECT 1 FROM jsf_work WHERE WORKRECORD_ID=" + recordId + ")"; 
      int n = this.stmt.executeUpdate(delSql);
      if (n > 0) {
        this.stmt.executeUpdate("delete from jsf_p_draft where table_id=" + tableId + " and record_id=" + recordId);
        List<String> list = new ArrayList();
        ResultSet rs = this.stmt.executeQuery("select area_table from tarea where area_name<>'form1' and page_id=" + tableId);
        while (rs.next())
          list.add(rs.getString(1)); 
        rs.close();
        for (int i = 0; i < list.size(); i++) {
          String subName = list.get(i).toString();
          this.stmt.executeUpdate("delete from " + subName + " where " + subName + "_foreignkey=" + recordId);
        } 
      } 
      end();
    } catch (Exception e) {
      end();
      e.printStackTrace();
    } 
  }
  
  public void deleteDraftRecord(String draftIds) {
    String[] idArr = draftIds.split(",");
    if (idArr != null) {
      String tableName = "", tableId = "", recordId = "";
      try {
        begin();
        for (int i = 0; i < idArr.length; i++) {
          ResultSet rs = this.stmt.executeQuery("select table_id,record_id from jsf_p_draft where id=" + idArr[i]);
          if (rs.next()) {
            tableId = rs.getString(1);
            recordId = rs.getString(2);
          } 
          rs.close();
          String mainTableName = "";
          List<String> subTable = new ArrayList();
          rs = this.stmt.executeQuery("select area_name,area_table from tarea where page_id=" + tableId);
          while (rs.next()) {
            if ("form1".equals(rs.getString(1))) {
              mainTableName = rs.getString(2);
              continue;
            } 
            subTable.add(rs.getString(2));
          } 
          rs.close();
          this.stmt.executeUpdate("delete from " + mainTableName + " where " + mainTableName + "_id=" + recordId);
          if (subTable.size() > 0)
            for (int m = 0; m < subTable.size(); m++) {
              mainTableName = subTable.get(m).toString();
              this.stmt.executeUpdate("delete from " + mainTableName + " where " + mainTableName + "_foreignkey=" + recordId);
            }  
          this.stmt.executeUpdate("delete from jsf_p_draft where table_id=" + tableId + " and record_id=" + recordId);
        } 
        end();
      } catch (Exception e) {
        end();
        e.printStackTrace();
      } 
    } 
  }
  
  public String[] getWorkParallelInfo(String workId) {
    String[] workInfo = { "0", "0", "0", "0", "0" };
    try {
      begin();
      String initActivity = "0";
      ResultSet rs = this.stmt.executeQuery("select is_parallel,parallel_id,parallel_step,parallel_curactid,initActivity from jsf_work where wf_work_id=" + workId);
      if (rs.next()) {
        workInfo[0] = rs.getString(1);
        workInfo[1] = rs.getString(2);
        workInfo[2] = rs.getString(3);
        workInfo[3] = rs.getString(4);
        initActivity = rs.getString(5);
      } 
      rs.close();
      if (initActivity != null && !"".equals(initActivity)) {
        rs = this.stmt.executeQuery("select isbranch from jsf_activity where wf_activity_id=" + initActivity);
        if (rs.next())
          workInfo[4] = rs.getString(1); 
        rs.close();
      } 
      end();
    } catch (Exception ex) {
      end();
      ex.printStackTrace();
    } 
    return workInfo;
  }
  
  public Integer transWorkflowBranch(Map dealwithPara, String[] para, String[] transactUser, String needPassRound, String[] passRoundUser) throws Exception {
    Integer result = Integer.valueOf("0");
    List<MessagesVO> msgList = new ArrayList();
    MessagesVO msgVO = new MessagesVO();
    DataSourceBase dsb = new DataSourceBase();
    DataSource ds = dsb.getDataSource();
    Connection conn = ds.getConnection();
    Statement stmt = null;
    boolean isCommit = conn.getAutoCommit();
    conn.setAutoCommit(false);
    try {
      String commentCurActivity, commentCurActivityName;
      stmt = conn.createStatement();
      String databaseType = SystemCommon.getDatabaseType();
      String tmpSql = "";
      String branchActInfo = dealwithPara.get("branchActInfo").toString();
      String[] branchActArray = branchActInfo.split(";;;;");
      Date now = new Date();
      String relproject = (String)dealwithPara.get("relproject");
      String dealwithUserId = dealwithPara.get("userId").toString();
      String nextActivityName = para[0];
      String nextActivityId = para[1];
      String curWorkId = para[2];
      String deadLineTime = para[3];
      String pressTime = para[4];
      nextActivityName = branchActArray[0].split(";;")[1];
      nextActivityId = branchActArray[0].split(";;")[0];
      Date workDeadlineDate = now;
      Date workDeadlinePressDate = now;
      String activityClass = para[16];
      String subProcType = para[17];
      now = new Date();
      String workFileType = "";
      String workSubmitPerson = "";
      String workSubmitTime = "";
      String wf_submitEmployee_id = "";
      String workProcess_id = "";
      String workTable_id = "";
      String workRecord_id = "";
      String isSubProcWork = "", pareProcActiId = "", pareStepCount = "";
      String pareTableId = "", pareRecordId = "", pareProcNextActiId = "", submitOrg = "";
      String processDeadlineDate = "null";
      String curTransactType = "1";
      String nextTransactType = para[20];
      String dealTips = para[21];
      int mainWorkStepCount = Integer.parseInt(para[7]) + 1;
      String mainWorkCurActivity = "0", mainWorkCurActivityName = "";
      int workIsParallel = 0;
      String workParallelId = "0";
      int workParallelStep = 0;
      String workParallelCurActId = "0";
      int newWorkIsParallel = 0;
      String newWorkParallelId = "0";
      int newWorkParallelStep = 0;
      String newWorkParallelCurActId = "0";
      String newWorkParallelFromWork = "0";
      int nextActivityIsDivide = 1;
      int nextActivityIsGather = 0;
      boolean nextActivityIsFirstDivide = false;
      boolean nextActivityIsGanther = false;
      boolean nextActivityIsMiddleDivide = false;
      boolean curActivityIsDivide = false;
      String sql = "select workFileType,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workProcess_id,workTable_id,workRecord_id,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,transactType,processDeadlineDate,is_parallel,parallel_id,parallel_step,workcurstep,workactivity,parallel_curactid,parallel_fromwork from JSDB.JSF_WORK where wf_work_id=" + 


        
        curWorkId;
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next()) {
        workFileType = rs.getString("workFileType");
        workSubmitPerson = rs.getString("workSubmitPerson");
        workSubmitTime = rs.getString("workSubmitTime");
        if (workSubmitTime.indexOf(".") > 0)
          workSubmitTime = workSubmitTime.substring(0, workSubmitTime.indexOf(".")); 
        wf_submitEmployee_id = rs.getString("wf_submitEmployee_id");
        workProcess_id = rs.getString("workProcess_id");
        workTable_id = rs.getString("workTable_id");
        workRecord_id = rs.getString("workRecord_id");
        isSubProcWork = rs.getString("isSubProcWork");
        pareProcActiId = rs.getString("pareProcActiId");
        pareStepCount = rs.getString("pareStepCount");
        pareTableId = rs.getString("pareTableId");
        pareRecordId = rs.getString("pareRecordId");
        pareProcNextActiId = rs.getString("pareProcNextActiId");
        submitOrg = rs.getString("submitOrg");
        curTransactType = (rs.getString("transactType") == null || "".equals(rs.getString("transactType"))) ? "1" : rs.getString("transactType");
        if (databaseType.indexOf("mysql") >= 0) {
          processDeadlineDate = (rs.getDate("processDeadlineDate") == null) ? "null" : ("'" + rs.getTimestamp("processDeadlineDate").toLocaleString() + "'");
        } else {
          processDeadlineDate = (rs.getDate("processDeadlineDate") == null) ? "null" : ("JSDB.FN_STRTODATE('" + rs.getTimestamp("processDeadlineDate").toLocaleString() + "','L')");
        } 
        workIsParallel = rs.getInt("is_parallel");
        workParallelId = rs.getString("parallel_id");
        workParallelStep = rs.getInt("parallel_step");
        mainWorkCurActivityName = rs.getString("workcurstep");
        mainWorkCurActivity = rs.getString("workactivity");
        workParallelCurActId = rs.getString("parallel_curactid");
        newWorkParallelFromWork = rs.getString("parallel_fromwork");
      } 
      rs.close();
      nextActivityIsFirstDivide = true;
      newWorkParallelFromWork = curWorkId;
      String submitEmployeeOrgId = getUserOrgId(wf_submitEmployee_id);
      String submitPersonOrgIdString = submitEmployeeOrgId;
      List toUserList = null;
      StringBuffer workUser = new StringBuffer();
      String domainId = "0";
      if (curActivityIsDivide) {
        commentCurActivity = workParallelCurActId;
        rs = stmt.executeQuery("select activityname from jsf_activity where wf_activity_id=" + commentCurActivity);
        rs.next();
        commentCurActivityName = rs.getString(1);
        rs.close();
      } else {
        commentCurActivity = dealwithPara.get("curActivityId").toString();
        commentCurActivityName = dealwithPara.get("curActivityName").toString();
      } 
      rs = stmt.executeQuery("SELECT WF_DEALWITH_ID FROM JSDB.JSF_DEALWITH WHERE DATABASETABLE_ID=" + dealwithPara.get("tableId") + " AND DATABASERECORD_ID=" + dealwithPara.get("recordId") + " AND ACTIVITY_ID=" + commentCurActivity + " AND CURSTEPCOUNT=" + dealwithPara.get("stepCount"));
      String wf_dealwith_id = "";
      if (rs.next())
        wf_dealwith_id = rs.getString(1); 
      rs.close();
      if (wf_dealwith_id.equals("")) {
        wf_dealwith_id = getTableId2();
        int curActivityStatus = 0;
        if (nextActivityName != null && nextActivityName.equals("-1"))
          curActivityStatus = 1; 
        String subProcWorkId = "0", activityClassTemp = "1";
        if (dealwithPara.get("activityClass") != null)
          activityClassTemp = dealwithPara.get("activityClass").toString(); 
        if (dealwithPara.get("activityClass") != null && dealwithPara.get("subProcWorkId") != null && !"".equals(dealwithPara.get("subProcWorkId"))) {
          subProcWorkId = dealwithPara.get("subProcWorkId").toString();
          if (dealwithPara.get("activityClass").toString().equals("0") && dealwithPara.get("subProcWorkId").toString().equals("0")) {
            rs = stmt.executeQuery("SELECT SUBPROCWORKID FROM JSDB.JSF_DEALWITH WHERE DATABASETABLE_ID=" + dealwithPara.get("tableId") + " AND DATABASERECORD_ID=" + dealwithPara.get("recordId"));
            if (rs.next())
              subProcWorkId = rs.getString("subProcWorkId"); 
            rs.close();
          } 
        } 
        stmt.execute("INSERT INTO JSDB.JSF_DEALWITH (WF_DEALWITH_ID,DATABASETABLE_ID,DATABASERECORD_ID,ACTIVITYNAME,ACTIVITY_ID,NEXTACTIVITYNAME,NEXTACTIVITYID,CURACTIVITYSTATUS,CURSTEPCOUNT,ACTIVITYCLASS,SUBPROCWORKID,DOMAIN_ID,parallel_id,parallel_step) VALUES (" + 
            wf_dealwith_id + "," + dealwithPara.get("tableId") + "," + dealwithPara.get("recordId") + ",'" + commentCurActivityName + "'," + commentCurActivity + ",'" + nextActivityName + "'," + nextActivityId + "," + curActivityStatus + "," + dealwithPara.get("stepCount") + "," + activityClassTemp + "," + subProcWorkId + "," + domainId + "," + workParallelId + "," + workParallelStep + ")");
      } else if (!dealwithPara.get("nextActivityId").toString().equals("-1") && 
        !dealwithPara.get("curActivityId").toString().equals("0")) {
        stmt.execute("UPDATE JSDB.JSF_DEALWITH SET NEXTACTIVITYID=" + nextActivityId + ",NEXTACTIVITYNAME='" + nextActivityName + "' " + 
            "WHERE WF_DEALWITH_ID=" + wf_dealwith_id);
      } 
      String commentField = "";
      if (dealwithPara.get("commentField") != null)
        commentField = dealwithPara.get("commentField").toString(); 
      String rangeName = (dealwithPara.get("userScope") == null) ? "" : dealwithPara.get("userScope").toString();
      String rangeIdStr = (dealwithPara.get("scopeId") == null) ? "" : dealwithPara.get("scopeId").toString();
      String modiCommentId = (dealwithPara.get("modiCommentId") == null) ? "" : dealwithPara.get("modiCommentId").toString();
      String orgIdString = (dealwithPara.get("orgIdString") == null) ? "" : dealwithPara.get("orgIdString").toString();
      if ("".equals(modiCommentId) || dealwithPara.get("commentField") == null || "-1".equals(dealwithPara.get("commentField")) || "".equals(dealwithPara.get("commentField"))) {
        if (!dealwithPara.get("isStandForWork").toString().equals("1")) {
          String wf_dealwithcomment_id = getTableId2();
          if (databaseType.indexOf("mysql") >= 0) {
            tmpSql = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,COMMENTFIELD,DOMAIN_ID,RANGENAME,RANGEIDSTR,signcomment,parallel_id,parallel_step,DEALWITHORG) VALUES (" + 
              wf_dealwithcomment_id + "," + 
              dealwithPara.get("userId") + ",'" + 
              dealwithPara.get("comment") + "','" + 
              now.toLocaleString() + "'," + wf_dealwith_id + 
              ",0,'','" + commentField + "'," + domainId + 
              ",'" + rangeName + "','" + rangeIdStr + "','" + dealwithPara.get("signcomment") + "'," + workParallelId + "," + workParallelStep + ",'" + dealwithPara.get("commentOrg") + "')";
          } else {
            tmpSql = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,COMMENTFIELD,DOMAIN_ID,RANGENAME,RANGEIDSTR,signcomment,parallel_id,parallel_step,DEALWITHORG) VALUES (" + 
              wf_dealwithcomment_id + "," + 
              dealwithPara.get("userId") + ",'" + 
              dealwithPara.get("comment") + 
              "',JSDB.FN_STRTODATE('" + 
              now.toLocaleString() + "','')," + 
              wf_dealwith_id + ",0,'','" + commentField + 
              "'," + domainId + ",'" + rangeName + "','" + 
              rangeIdStr + "','" + dealwithPara.get("signcomment") + "'," + workParallelId + "," + workParallelStep + ",'" + dealwithPara.get("commentOrg") + "')";
          } 
          stmt.execute(tmpSql);
        } else {
          String wf_dealwithcomment_id = getTableId2();
          String empName = "";
          rs = stmt.executeQuery(
              "SELECT EMPNAME FROM JSDB.ORG_EMPLOYEE WHERE EMP_ID=" + 
              dealwithPara.get("userId"));
          if (rs.next())
            empName = rs.getString(1); 
          if (databaseType.indexOf("mysql") >= 0) {
            tmpSql = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,ISSTANDFORCOMM,COMMENTFIELD,DOMAIN_ID,RANGENAME,RANGEIDSTR,signcomment,parallel_id,parallel_step,DEALWITHORG) VALUES (" + 
              wf_dealwithcomment_id + "," + 
              dealwithPara.get("standForUserId") + ",'" + 
              dealwithPara.get("comment") + "','" + 
              now.toLocaleString() + "'," + wf_dealwith_id + 
              "," + dealwithPara.get("userId") + ",'" + 
              empName + "',1,'" + commentField + "'," + 
              domainId + ",'" + rangeName + "','" + 
              rangeIdStr + "','" + dealwithPara.get("signcomment") + "'," + workParallelId + "," + workParallelStep + ",'" + dealwithPara.get("commentOrg") + "')";
          } else {
            tmpSql = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,ISSTANDFORCOMM,COMMENTFIELD,DOMAIN_ID,RANGENAME,RANGEIDSTR,signcomment,parallel_id,parallel_step,DEALWITHORG) VALUES (" + 
              wf_dealwithcomment_id + "," + 
              dealwithPara.get("standForUserId") + ",'" + 
              dealwithPara.get("comment") + 
              "',JSDB.FN_STRTODATE('" + 
              now.toLocaleString() + "','')," + 
              wf_dealwith_id + "," + 
              dealwithPara.get("userId") + ",'" + empName + 
              "',1,'" + commentField + "'," + domainId + 
              ",'" + rangeName + "','" + rangeIdStr + "','" + dealwithPara.get("signcomment") + "'," + workParallelId + "," + workParallelStep + ",'" + dealwithPara.get("commentOrg") + "')";
          } 
          stmt.execute(tmpSql);
        } 
      } else if (!dealwithPara.get("isStandForWork").toString().equals("1")) {
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "update JSDB.JSF_DEALWITHCOMMENT set DEALWITHEMPLOYEE_ID='" + dealwithPara.get("userId") + "'," + 
            "DEALWITHEMPLOYEECOMMENT='" + dealwithPara.get("comment") + "',DEALWITHTIME='" + now.toLocaleString() + "',WF_DEALWITH_ID='" + wf_dealwith_id + "'," + 
            "STANDFORUSERID=0,STANDFORUSERNAME='',COMMENTFIELD='" + commentField + "',DOMAIN_ID='" + domainId + "',RANGENAME='" + rangeName + "',RANGEIDSTR='" + rangeIdStr + "',signcomment='" + dealwithPara.get("signcomment") + "',DEALWITHORG='" + dealwithPara.get("commentOrg") + "' where WF_DEALWITHCOMMENT_ID=" + modiCommentId;
        } else {
          tmpSql = "update JSDB.JSF_DEALWITHCOMMENT set DEALWITHEMPLOYEE_ID='" + dealwithPara.get("userId") + "'," + 
            "DEALWITHEMPLOYEECOMMENT='" + dealwithPara.get("comment") + "',DEALWITHTIME=JSDB.FN_STRTODATE('" + 
            now.toLocaleString() + "',''),WF_DEALWITH_ID='" + wf_dealwith_id + "'," + 
            "STANDFORUSERID=0,STANDFORUSERNAME='',COMMENTFIELD='" + commentField + "',DOMAIN_ID='" + domainId + "',RANGENAME='" + rangeName + "',RANGEIDSTR='" + rangeIdStr + "',signcomment='" + dealwithPara.get("signcomment") + "',DEALWITHORG='" + dealwithPara.get("commentOrg") + "' where WF_DEALWITHCOMMENT_ID=" + modiCommentId;
        } 
        stmt.execute(tmpSql);
      } else {
        String empName = "";
        rs = stmt.executeQuery(
            "SELECT EMPNAME FROM JSDB.ORG_EMPLOYEE WHERE EMP_ID=" + dealwithPara.get("userId"));
        if (rs.next())
          empName = rs.getString(1); 
        rs.close();
        if (databaseType.indexOf("mysql") >= 0) {
          tmpSql = "update JSDB.JSF_DEALWITHCOMMENT set DEALWITHEMPLOYEE_ID='" + dealwithPara.get("standForUserId") + "'," + 
            "DEALWITHEMPLOYEECOMMENT='" + dealwithPara.get("comment") + "',DEALWITHTIME='" + now.toLocaleString() + "',WF_DEALWITH_ID='" + wf_dealwith_id + "'," + 
            "STANDFORUSERID='" + dealwithPara.get("userId") + "',STANDFORUSERNAME='" + empName + "',ISSTANDFORCOMM='1' ,COMMENTFIELD='" + commentField + "'," + 
            "DOMAIN_ID='" + domainId + "',RANGENAME='" + rangeName + "',RANGEIDSTR='" + rangeIdStr + "',signcomment='" + dealwithPara.get("signcomment") + "',DEALWITHORG='" + dealwithPara.get("commentOrg") + "' where WF_DEALWITHCOMMENT_ID=" + modiCommentId;
        } else {
          tmpSql = "update JSDB.JSF_DEALWITHCOMMENT set DEALWITHEMPLOYEE_ID='" + dealwithPara.get("standForUserId") + "'," + 
            "DEALWITHEMPLOYEECOMMENT='" + dealwithPara.get("comment") + "',DEALWITHTIME=JSDB.FN_STRTODATE('" + 
            now.toLocaleString() + "',''),WF_DEALWITH_ID='" + wf_dealwith_id + "'," + 
            "STANDFORUSERID='" + dealwithPara.get("userId") + "',STANDFORUSERNAME='" + empName + "',ISSTANDFORCOMM='1' ,COMMENTFIELD='" + commentField + "',DOMAIN_ID='" + domainId + "',RANGENAME='" + rangeName + "',RANGEIDSTR='" + rangeIdStr + "',signcomment='" + dealwithPara.get("signcomment") + "',DEALWITHORG='" + dealwithPara.get("commentOrg") + "' where WF_DEALWITHCOMMENT_ID=" + modiCommentId;
        } 
        stmt.execute(tmpSql);
      } 
      int hasDoneFile = 0;
      rs = stmt.executeQuery("select count(wf_work_id) from jsf_work where workstatus = 101 and worktable_id = " + workTable_id + 
          " and workrecord_id = " + workRecord_id + " and wf_curemployee_id = " + 
          para[13] + " and workprocess_id=" + workProcess_id);
      if (rs.next())
        hasDoneFile = rs.getInt(1); 
      rs.close();
      if ("last".equals(SystemCommon.getRepeatFileDealwith()) && hasDoneFile > 0)
        stmt.execute("update JSDB.JSF_WORK set WORKDELETE =1 where workstatus = 101 and worktable_id = " + workTable_id + 
            " and workrecord_id = " + workRecord_id + " and wf_curemployee_id = " + 
            para[13] + " and workprocess_id=" + workProcess_id); 
      stmt.execute("delete from JSDB.jsf_work where workTable_Id=" + workTable_id + " and workRecord_id=" + 
          workRecord_id + " and workStepCount=" + para[7] + " and standForUserId=" + 
          para[13]);
      String remindFieldValue = para[8];
      int k = 0;
      if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
        k = (String.valueOf(remindFieldValue) + workFileType + "您已办理完毕！").length() - 100;
      } else {
        k = (String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "您已办理完毕！").length() - 100;
      } 
      if (k > 0)
        remindFieldValue.substring(0, remindFieldValue.length() - k); 
      String standTitle = "";
      String workTitle = "";
      if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
        workTitle = String.valueOf(remindFieldValue) + workFileType + "您已办理完毕！";
      } else {
        workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "您已办理完毕！";
      } 
      if (!para[18].equals(""))
        workTitle = para[18]; 
      if (para[12].equals("1")) {
        rs = stmt.executeQuery("SELECT EMPNAME FROM JSDB.ORG_EMPLOYEE WHERE EMP_ID=" + para[13]);
        String myTmp = "";
        if (rs.next())
          myTmp = rs.getString(1); 
        rs.close();
        myTmp = String.valueOf(workTitle) + "（" + myTmp + "代办）";
        if (myTmp.length() > 100)
          myTmp = myTmp.substring(0, 99); 
        if (databaseType.indexOf("mysql") >= 0) {
          stmt.execute("UPDATE JSDB.JSF_WORK SET WORKSTATUS=101,DEALWITHTIME='" + 
              now.toLocaleString() + "',WORKTITLE='" + 
              myTmp + "',WORKALLOWCANCEL=" + para[6] + 
              ",WORKUSER='" + workUser.toString() + 
              "' where workTable_Id=" + workTable_id + 
              " and workRecord_id=" + 
              workRecord_id + " and workStepCount=" + para[7] + 
              " and wf_curemployee_id=" + 
              para[14]);
        } else {
          stmt.execute("UPDATE JSDB.JSF_WORK SET WORKSTATUS=101,DEALWITHTIME=JSDB.FN_STRTODATE('" + 
              now.toLocaleString() + "',''),WORKTITLE='" + 
              myTmp + "',WORKALLOWCANCEL=" + para[6] + 
              ",WORKUSER='" + workUser.toString() + 
              "' where workTable_Id=" + workTable_id + 
              " and workRecord_id=" + 
              workRecord_id + " and workStepCount=" + para[7] + 
              " and wf_curemployee_id=" + 
              para[14]);
        } 
        standTitle = "（代" + para[15] + "办理）";
      } 
      workTitle = String.valueOf(workTitle) + standTitle;
      if (workTitle.length() > 100)
        workTitle = workTitle.substring(0, 99); 
      sql = "update JSDB.JSF_WORK set workstatus = 101,DEALWITHTIME=";
      if (databaseType.indexOf("mysql") >= 0) {
        sql = String.valueOf(sql) + "'" + now.toLocaleString() + "', workTitle = '" + workTitle + "', ";
      } else {
        sql = String.valueOf(sql) + "JSDB.FN_STRTODATE('" + now.toLocaleString() + "',''), workTitle = '" + workTitle + "', ";
      } 
      if ("first".equals(SystemCommon.getRepeatFileDealwith()) && hasDoneFile > 0)
        sql = String.valueOf(sql) + "WORKDELETE =1,"; 
      sql = String.valueOf(sql) + "workAllowCancel=" + para[6] + ", workUser = '" + workUser.toString() + "' " + 
        "where wf_work_id = " + curWorkId;
      stmt.executeUpdate(sql);
      int mycount = 0;
      int workListControl = 0;
      if (curTransactType.equals("1") || curTransactType.equals("3")) {
        sql = " select count(wf_work_id) from JSDB.JSF_WORK where worktable_id = " + workTable_id + 
          " and workRecord_id = " + workRecord_id + " and workStepCount = " + para[7] + 
          " and workstatus = 0";
        if (curActivityIsDivide)
          if (nextActivityIsGanther) {
            sql = String.valueOf(sql) + " and is_parallel=1";
          } else {
            sql = String.valueOf(sql) + " and parallel_id=" + workParallelId + " and parallel_step=" + workParallelStep;
          }  
        rs = stmt.executeQuery(sql);
        if (rs.next())
          mycount = rs.getInt(1); 
        rs.close();
      } 
      if (mycount == 0)
        if (activityClass.equals("1") || (activityClass.equals("0") && (subProcType.equals("0") || subProcType.equals("1"))) || activityClass.equals("3")) {
          stmt.execute("update JSDB.JSF_WORK set workListControl = 1 where worktable_id = " + workTable_id + 
              " and workrecord_id = " + workRecord_id + " and workActivity = " + nextActivityId);
          stmt.execute("update JSDB.JSF_WORK set workCurStep='" + nextActivityName + "' where worktable_id=" + workTable_id + 
              " and workrecord_id = " + workRecord_id);
          workListControl = 1;
          stmt.execute("update JSDB.JSF_DEALWITH set curActivityStatus = 1 where databasetable_id = " + 
              workTable_id + " and databaserecord_id = " + workRecord_id + " and curStepCount = " + 
              para[7]);
          if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
            workTitle = String.valueOf(remindFieldValue) + workFileType + "已办理完毕！";
          } else {
            workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "已办理完毕！";
          } 
          if (!para[18].equals(""))
            workTitle = para[18]; 
          if (workTitle.length() > 100)
            workTitle = workTitle.substring(0, 99); 
        }  
      String branchActivityNames = dealwithPara.get("branchActivityNames").toString();
      for (int actInd = 0; actInd < branchActArray.length; actInd++) {
        String branchActId = branchActArray[actInd].split(";;")[0];
        String branchActName = branchActArray[actInd].split(";;")[1];
        String branchActUser = branchActArray[actInd].split(";;")[2];
        String branchTransActType = branchActArray[actInd].split(";;")[3];
        transactUser = branchActUser.split(",");
        workDeadlineDate = (Date)now.clone();
        workDeadlinePressDate = (Date)now.clone();
        sql = "SELECT presstype,deadlinetime,pressmotiontime FROM jsf_activity WHERE WF_ACTIVITY_ID=" + branchActId;
        rs = stmt.executeQuery(sql);
        if (rs.next()) {
          int pressType = rs.getInt(1);
          if (pressType == 1) {
            deadLineTime = rs.getString(2);
            pressTime = rs.getString(3);
            workDeadlineDate = FlowDeadline.getDeadline(now, Long.parseLong(deadLineTime));
            workDeadlinePressDate = new Date(workDeadlineDate.getTime() - Long.parseLong(pressTime) * 1000L);
          } 
        } 
        rs.close();
        String tmpUser = "";
        String insertUser = "";
        for (int j = transactUser.length - 1; j >= 0; j--) {
          tmpUser = transactUser[j];
          if (insertUser.indexOf(String.valueOf(tmpUser) + ",") < 0) {
            insertUser = String.valueOf(insertUser) + tmpUser + ",";
            if (tmpUser.startsWith("$"))
              tmpUser = tmpUser.substring(1); 
            if (tmpUser.endsWith("$"))
              tmpUser = tmpUser.substring(0, tmpUser.length() - 1); 
            if (curActivityIsDivide && !nextActivityIsGanther) {
              sql = "select count(wf_work_id) from JSDB.JSF_WORK where wf_curemployee_id = " + 
                tmpUser + " and worktable_id = " + workTable_id + 
                " and workrecord_id = " + workRecord_id + 
                " and workreadmarker = 0 and workActivity = " + nextActivityId + 
                " and workStepCount = " + mainWorkStepCount + " and parallel_id=" + workParallelId + " and parallel_step=" + (workParallelStep + 1);
            } else if (nextActivityIsFirstDivide) {
              sql = "select count(wf_work_id) from JSDB.JSF_WORK where wf_curemployee_id = " + 
                tmpUser + " and worktable_id = " + workTable_id + 
                " and workrecord_id = " + workRecord_id + 
                " and workreadmarker = 0 and workActivity = " + nextActivityId + 
                " and workStepCount = " + mainWorkStepCount + " and parallel_id=" + workParallelId + " and parallel_step=" + (workParallelStep + 1);
            } else {
              sql = "select count(wf_work_id) from JSDB.JSF_WORK where wf_curemployee_id = " + 
                tmpUser + " and worktable_id = " + workTable_id + 
                " and workrecord_id = " + workRecord_id + 
                " and workreadmarker = 0 and workActivity = " + nextActivityId + 
                " and workStepCount = " + mainWorkStepCount;
            } 
            rs = stmt.executeQuery(sql);
            rs.next();
            int sameWorkCount = rs.getInt(1);
            rs.close();
            if (sameWorkCount == 0) {
              String workId = getTableId2();
              nextActivityId = branchActId;
              nextActivityName = branchActName;
              newWorkIsParallel = 1;
              newWorkParallelId = getTableId2();
              newWorkParallelStep = 1;
              newWorkParallelCurActId = nextActivityId;
              if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
                workTitle = String.valueOf(remindFieldValue) + workFileType;
              } else {
                workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "等待您处理！";
              } 
              if (!para[18].equals(""))
                workTitle = para[18]; 
              if (databaseType.indexOf("mysql") >= 0) {
                tmpSql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType,  workCurStep, workTitle, workLeftLinkFile, workMainLinkFile,  workListControl, workActivity, workSubmitPerson, workSubmitTime,  wf_submitEmployee_id, workReadMarker, workType, workProcess_id,  worktable_id, workrecord_id, workDeadLine, workPressTime,  workCreateDate, workStepCount, initActivity, initStepCount ,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,domain_id,emergence,transactType,INITACTIVITYNAME,isStandForWork,dealTips,workDeadlineDate,workDeadlinePressDate,processDeadlineDate,relproject_id,is_parallel,parallel_id,parallel_step,parallel_curactid,parallel_fromwork) values ( " + 









                  
                  workId + ", " + tmpUser + ",0,'" + workFileType + "','" + 
                  branchActivityNames + "','" + workTitle + "','','" + para[10] + "'," + workListControl + "," + 
                  nextActivityId + ",'" + workSubmitPerson + "','" + 
                  workSubmitTime + "'," + wf_submitEmployee_id + 
                  ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                  "," + deadLineTime + "," + pressTime + ", '" + 
                  now.toLocaleString() + "', " + 
                  mainWorkStepCount + "," + nextActivityId + "," + mainWorkStepCount + "," + 
                  isSubProcWork + "," + pareProcActiId + "," + pareStepCount + "," + pareTableId + "," + pareRecordId + "," + 
                  pareProcNextActiId + ",'" + submitOrg + "'," + domainId + ",'" + para[19] + "','" + branchTransActType + "','" + nextActivityName + "',0,'" + dealTips + "','" + workDeadlineDate.toLocaleString() + "','" + workDeadlinePressDate.toLocaleString() + "'," + processDeadlineDate + "," + relproject + "," + 
                  newWorkIsParallel + "," + newWorkParallelId + "," + newWorkParallelStep + "," + newWorkParallelCurActId + "," + newWorkParallelFromWork + 
                  ")";
              } else {
                tmpSql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType,  workCurStep, workTitle, workLeftLinkFile, workMainLinkFile,  workListControl, workActivity, workSubmitPerson, workSubmitTime,  wf_submitEmployee_id, workReadMarker, workType, workProcess_id,  worktable_id, workrecord_id, workDeadLine, workPressTime,  workCreateDate, workStepCount, initActivity, initStepCount ,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,domain_id,emergence,transactType,INITACTIVITYNAME,isStandForWork,dealTips,workDeadlineDate,workDeadlinePressDate,processDeadlineDate,relproject_id,is_parallel,parallel_id,parallel_step,parallel_curactid,parallel_fromwork) values ( " + 









                  
                  workId + ", " + tmpUser + ",0,'" + workFileType + "','" + 
                  branchActivityNames + "','" + workTitle + "','','" + para[10] + "'," + workListControl + "," + 
                  nextActivityId + ",'" + workSubmitPerson + "',JSDB.FN_STRTODATE('" + 
                  workSubmitTime + "','')," + wf_submitEmployee_id + 
                  ",0,1," + workProcess_id + "," + workTable_id + "," + workRecord_id + 
                  "," + deadLineTime + "," + pressTime + ", JSDB.FN_STRTODATE('" + 
                  now.toLocaleString() + "',''), " + 
                  mainWorkStepCount + "," + nextActivityId + "," + mainWorkStepCount + "," + 
                  isSubProcWork + "," + pareProcActiId + "," + pareStepCount + "," + pareTableId + "," + pareRecordId + "," + 
                  pareProcNextActiId + ",'" + submitOrg + "'," + domainId + ",'" + para[19] + "','" + branchTransActType + "','" + nextActivityName + "',0,'" + dealTips + "',JSDB.FN_STRTODATE('" + workDeadlineDate.toLocaleString() + "','L'),JSDB.FN_STRTODATE('" + workDeadlinePressDate.toLocaleString() + "','L')," + processDeadlineDate + "," + relproject + "," + 
                  newWorkIsParallel + "," + newWorkParallelId + "," + newWorkParallelStep + "," + newWorkParallelCurActId + "," + newWorkParallelFromWork + 
                  ")";
              } 
              stmt.execute(tmpSql);
              msgVO = new MessagesVO();
              msgVO.setMessage_date_begin(now);
              msgVO.setMessage_date_end(new Date("2050/1/1"));
              msgVO.setMessage_send_UserId(Long.parseLong(wf_submitEmployee_id));
              msgVO.setMessage_send_UserName(workSubmitPerson);
              msgVO.setMessage_show(1);
              msgVO.setMessage_status(1);
              msgVO.setMessage_time(now);
              msgVO.setMessage_title(workTitle);
              msgVO.setMessage_toUserId(Long.parseLong(tmpUser));
              msgVO.setMessage_type("jsflow");
              msgVO.setData_id(Long.parseLong(workId));
              msgVO.setSendSMS((para.length > 22) ? Integer.valueOf(para[22]).intValue() : 1);
              msgVO.setMessage_url("/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + workId);
              msgList.add(msgVO);
            } 
          } 
        } 
      } 
      if (mycount == 0)
        if (curActivityIsDivide && !nextActivityIsGanther) {
          stmt.execute(" update JSDB.JSF_WORK set workcurstep = '" + mainWorkCurActivityName + "', workactivity = " + 
              mainWorkCurActivity + " where worktable_id = " + workTable_id + " and " + 
              " workrecord_id = " + workRecord_id + " and workStepCount <=" + Integer.parseInt(para[7]));
        } else {
          stmt.execute(" update JSDB.JSF_WORK set workcurstep = '" + branchActivityNames + "', workactivity = " + 
              nextActivityId + " where worktable_id = " + workTable_id + " and " + 
              " workrecord_id = " + workRecord_id);
        }  
      if (mycount == 0 && activityClass.equals("0") && subProcType.equals("0"))
        if (curActivityIsDivide && !nextActivityIsGanther) {
          stmt.execute(" update JSDB.JSF_WORK set workListControl = 1 where workTable_id = " + 
              workTable_id + " and workRecord_Id = " + workRecord_id + " and parallel_id = " + 
              workParallelId + " and parallel_step=" + (workParallelStep + 1));
        } else {
          stmt.execute(" update JSDB.JSF_WORK set workListControl = 1 where workTable_id = " + 
              workTable_id + " and workRecord_Id = " + workRecord_id + " and workStepCount = " + (
              Integer.parseInt(para[7]) + 1));
        }  
      MessagesBD messagesBD = new MessagesBD();
      if (nextTransactType.equals("3")) {
        if (curActivityIsDivide && !nextActivityIsGanther) {
          stmt.execute(" update JSDB.JSF_WORK set workListControl = 0 where workTable_id = " + 
              workTable_id + " and workRecord_Id = " + workRecord_id + " and workStatus = 0  and parallel_id = " + 
              workParallelId + " and parallel_step=" + (workParallelStep + 1));
        } else {
          stmt.execute(" update JSDB.JSF_WORK set workListControl = 0 where workTable_id = " + 
              workTable_id + " and workRecord_Id = " + workRecord_id + " and workStatus = 0 and workStepCount = " + (
              Integer.parseInt(para[7]) + 1));
        } 
        String minWorkId = "0";
        String itemTitle = "", toUserId = "";
        if (curActivityIsDivide && !nextActivityIsGanther) {
          sql = "select max(wf_work_id) from JSDB.JSF_WORK where workTable_id = " + 
            workTable_id + " and workRecord_Id = " + workRecord_id + " and workStatus = 0 and parallel_id = " + 
            workParallelId + " and parallel_step=" + (workParallelStep + 1);
        } else {
          sql = "select max(wf_work_id) from JSDB.JSF_WORK where workTable_id = " + 
            workTable_id + " and workRecord_Id = " + workRecord_id + " and workStatus = 0 and workStepCount = " + (
            Integer.parseInt(para[7]) + 1);
        } 
        rs = stmt.executeQuery(sql);
        if (rs.next())
          minWorkId = rs.getString(1); 
        rs.close();
        rs = stmt.executeQuery("select worktitle,wf_curemployee_id from JSDB.jsf_work where wf_work_id=" + minWorkId);
        if (rs.next()) {
          itemTitle = rs.getString(1);
          toUserId = rs.getString(2);
        } 
        rs.close();
        stmt.execute(" update JSDB.JSF_WORK set workListControl = 1 where wf_work_id = " + minWorkId);
        msgVO = new MessagesVO();
        msgVO.setMessage_date_begin(now);
        msgVO.setMessage_date_end(new Date("2050/1/1"));
        msgVO.setMessage_send_UserId(Long.parseLong(wf_submitEmployee_id));
        msgVO.setMessage_send_UserName(workSubmitPerson);
        msgVO.setMessage_show(1);
        msgVO.setMessage_status(1);
        msgVO.setMessage_time(now);
        msgVO.setMessage_title(itemTitle);
        msgVO.setMessage_toUserId(Long.parseLong(toUserId));
        msgVO.setMessage_type("jsflow");
        msgVO.setData_id(Long.parseLong(minWorkId));
        msgVO.setSendSMS((para.length > 22) ? Integer.valueOf(para[22]).intValue() : 1);
        msgVO.setMessage_url("/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + minWorkId);
        messagesBD.messageAdd(msgVO);
      } else if (!"1".equals(subProcType)) {
        messagesBD.messageArrayAdd(msgList);
      } 
      List<String[]> messageInfo = (List)new ArrayList<String>();
      messageInfo.add(new String[] { dealwithUserId.toString(), curWorkId });
      if (curTransactType.equals("0") || (activityClass.equals("0") && subProcType.equals("0"))) {
        if ("shandongguotou".equals(SystemCommon.getCustomerName())) {
          workTitle = String.valueOf(remindFieldValue) + workFileType + "已办理完毕！";
        } else {
          workTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "已办理完毕！";
        } 
        if (!para[18].equals(""))
          workTitle = para[18]; 
        if (curActivityIsDivide) {
          sql = "select wf_curemployee_id,wf_work_id from jsf_work  where workStatus = 0 and workTable_id = " + 
            workTable_id + " and workRecord_id = " + workRecord_id + " and parallel_id = " + 
            workParallelId + " and parallel_step=" + workParallelStep + " and wf_work_id <> " + curWorkId;
        } else {
          sql = "select wf_curemployee_id,wf_work_id from jsf_work  where workStatus = 0 and workTable_id = " + 
            workTable_id + " and workRecord_id = " + workRecord_id + 
            " and workStepCount = " + para[7] + " and wf_work_id <> " + curWorkId;
        } 
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
          String[] temp = new String[2];
          temp[0] = rs.getString(1);
          temp[1] = rs.getString(2);
          messageInfo.add(temp);
        } 
        rs.close();
        if (curActivityIsDivide) {
          if (databaseType.indexOf("mysql") >= 0) {
            stmt.execute("update JSDB.JSF_work set workStatus = 101, DEALWITHTIME='" + 
                now.toLocaleString() + "', workTitle = '" + workTitle + "',workAllowCancel = 0 where workStatus = 0 and workTable_id = " + 
                workTable_id + " and workRecord_id = " + workRecord_id + " and parallel_id = " + 
                workParallelId + " and parallel_step=" + workParallelStep + " and wf_work_id <> " + curWorkId);
          } else {
            stmt.execute("update JSDB.JSF_work set workStatus = 101, DEALWITHTIME=JSDB.FN_STRTODATE('" + 
                now.toLocaleString() + "',''), workTitle = '" + workTitle + "',workAllowCancel = 0 where workStatus = 0 and workTable_id = " + 
                workTable_id + " and workRecord_id = " + workRecord_id + " and parallel_id = " + 
                workParallelId + " and parallel_step=" + workParallelStep + " and wf_work_id <> " + curWorkId);
          } 
        } else if (databaseType.indexOf("mysql") >= 0) {
          stmt.execute("update JSDB.JSF_work set workStatus = 101, DEALWITHTIME='" + 
              now.toLocaleString() + "', workTitle = '" + workTitle + "',workAllowCancel = 0 where workStatus = 0 and workTable_id = " + 
              workTable_id + " and workRecord_id = " + workRecord_id + 
              " and workStepCount = " + para[7] + " and wf_work_id <> " + curWorkId);
        } else {
          stmt.execute("update JSDB.JSF_work set workStatus = 101, DEALWITHTIME=JSDB.FN_STRTODATE('" + 
              now.toLocaleString() + "',''), workTitle = '" + workTitle + "',workAllowCancel = 0 where workStatus = 0 and workTable_id = " + 
              workTable_id + " and workRecord_id = " + workRecord_id + 
              " and workStepCount = " + para[7] + " and wf_work_id <> " + curWorkId);
        } 
      } 
      conn.commit();
      conn.setAutoCommit(isCommit);
      stmt.close();
      conn.close();
      for (int i = 0; i < messageInfo.size(); i++) {
        String[] temp = messageInfo.get(i);
        messagesBD.readMessage(temp[1], temp[0], "jsflow");
      } 
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.rollback();
          conn.close();
        } catch (SQLException se) {
          se.printStackTrace();
        }  
      e.printStackTrace();
      result = Integer.valueOf("-1");
      throw e;
    } 
    return result;
  }
  
  public Integer updateToDraftButton(Map dealwithPara) throws Exception {
    String workId = (String)dealwithPara.get("workId");
    String recordId = (String)dealwithPara.get("recordId");
    String tableId = (String)dealwithPara.get("tableId");
    List<String[]> draftCommentList = (new WorkFlowBD()).getDraftComment(workId, recordId, tableId);
    Integer count = Integer.valueOf(0);
    if (draftCommentList.size() > 0) {
      String[] draftCommen = draftCommentList.get(0);
      String id = draftCommen[0];
      draftCommenUpdate(dealwithPara, id);
    } else {
      draftCommenInsert(dealwithPara);
    } 
    return count;
  }
  
  private Integer draftCommenInsert(Map dealwithPara) throws Exception {
    Integer count = Integer.valueOf(0);
    String sql = "insert into jsf_commentdraft(id,workid,recordid,tableid,curactivityid,empid,commenfiled,draftcomment,signComment,drafttime) values(?,?,?,?,?,?,?,?,?,?)";
    Connection conn = null;
    PreparedStatement pstmt = null;
    DataSourceBase base = new DataSourceBase();
    Date now = new Date();
    DateFormat dtmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      String Id = getTableId2();
      pstmt.setString(1, Id);
      pstmt.setString(2, (String)dealwithPara.get("workId"));
      pstmt.setString(3, (String)dealwithPara.get("recordId"));
      pstmt.setString(4, (String)dealwithPara.get("tableId"));
      pstmt.setString(5, (String)dealwithPara.get("curActivityId"));
      pstmt.setString(6, (String)dealwithPara.get("userId"));
      pstmt.setString(7, (String)dealwithPara.get("commentField"));
      pstmt.setString(8, (String)dealwithPara.get("comment"));
      pstmt.setString(9, (String)dealwithPara.get("signcomment"));
      pstmt.setString(10, dtmt.format(now));
      count = Integer.valueOf(pstmt.executeUpdate());
      System.out.println("count:" + count);
      pstmt.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return count;
  }
  
  private Integer draftCommenUpdate(Map dealwithPara, String id) {
    Integer count = Integer.valueOf(0);
    String comment = (String)dealwithPara.get("comment");
    String signcomment = (String)dealwithPara.get("signcomment");
    String sql = "update jsf_commentdraft set draftcomment=?,signComment=?,drafttime=? where id=?";
    Date now = new Date();
    DateFormat dtmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Connection conn = null;
    PreparedStatement pstmt = null;
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, comment);
      pstmt.setString(2, signcomment);
      pstmt.setString(3, dtmt.format(now));
      pstmt.setString(4, id);
      count = Integer.valueOf(pstmt.executeUpdate());
      pstmt.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return count;
  }
}
