package com.js.oa.jsflow.bean;

import com.js.oa.jsflow.vo.WorkLogVO;
import com.js.oa.jsflow.vo.WorkVO;
import com.js.system.service.messages.MessagesBD;
import com.js.system.vo.messages.MessagesVO;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceUtil;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
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
import org.apache.commons.lang.StringUtils;

public class WorkFlowButtonEJBBean extends DataSourceBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public String undoWork(WorkVO workVO) throws Exception {
    begin();
    ResultSet rs = null;
    String sql = "";
    String activityName = "";
    String activityId = "";
    String stepCount = "";
    String maxStep = "";
    try {
      sql = "SELECT ACTIVITY_ID,activityName,curstepcount FROM JSDB.JSF_DEALWITH WHERE DATABASETABLE_ID=" + 
        workVO.getTableId() + 
        " AND DATABASERECORD_ID=" + workVO.getRecordId() + 
        " AND CURSTEPCOUNT=" + workVO.getPareStepCount();
      rs = this.stmt.executeQuery(sql);
      if (rs.next()) {
        activityId = rs.getString("ACTIVITY_ID");
        activityName = rs.getString("activityName");
        stepCount = rs.getString("curstepcount");
      } 
      rs.close();
      boolean sameStep = false;
      sql = "SELECT max(workstepcount) FROM JSDB.JSF_WORK WHERE workTable_Id=" + 
        workVO.getTableId() + " and workRecord_id=" + 
        workVO.getRecordId() + " and worklistcontrol=1";
      rs = this.stmt.executeQuery(sql);
      if (rs.next()) {
        maxStep = rs.getString(1);
        if (maxStep.equals(workVO.getPareStepCount()))
          sameStep = true; 
      } 
      rs.close();
      String transactType = workVO.getTransactType();
      if (sameStep) {
        if ("3".equals(transactType))
          this.stmt.execute(
              "update JSDB.JSF_WORK set WORKDELETE=1 WHERE worktable_id=" + 
              workVO.getTableId() + 
              " AND workrecord_id=" + workVO.getRecordId() + 
              " and workStatus = 0 AND workstepcount=" + 
              workVO.getPareStepCount()); 
        this.stmt.execute(
            "update JSDB.JSF_WORK set workStatus = 0,DEALWITHTIME = null,WORKDELETE=0, workTitle = replace(workTitle,'您已办理完毕','等待您处理') where workStatus='101'  and WORKSTEPCOUNT=" + 

            
            workVO.getPareStepCount() + " and wf_curEmployee_id='" + 
            workVO.getCurEmployeeId() + "' and workprocess_id='" + 
            workVO.getProcessId() + "' and worktable_id='" + 
            workVO.getTableId() + "' and workrecord_id='" + 
            workVO.getRecordId() + "' and wf_work_id='" + workVO.getId() + "'");
        int curStepWorkedCount = 1;
        rs = this.stmt.executeQuery("select count(wf_work_id) from JSDB.JSF_WORK WHERE worktable_id=" + 
            workVO.getTableId() + 
            " AND workrecord_id=" + workVO.getRecordId() + 
            " and workStatus = 101 AND workstepcount=" + workVO.getPareStepCount());
        if (rs.next())
          curStepWorkedCount = rs.getInt(1); 
        rs.close();
        this.stmt.addBatch("delete from jsf_dealwithcomment where WF_DEALWITH_ID in (SELECT WF_DEALWITH_ID FROM JSDB.JSF_DEALWITH WHERE DATABASETABLE_ID=" + 
            workVO.getTableId() + 
            " AND DATABASERECORD_ID=" + workVO.getRecordId() + 
            " AND CURSTEPCOUNT=" + workVO.getPareStepCount() + 
            ") and dealwithemployee_id=" + workVO.getCurEmployeeId());
        if (curStepWorkedCount == 0)
          this.stmt.addBatch(
              "delete from JSF_DEALWITH where DATABASETABLE_ID=" + 
              workVO.getTableId() + 
              " AND DATABASERECORD_ID=" + workVO.getRecordId() + 
              " AND CURSTEPCOUNT=" + workVO.getPareStepCount()); 
        this.stmt.executeBatch();
        this.stmt.clearBatch();
      } else {
        this.stmt.addBatch("delete from jsf_dealwithcomment where WF_DEALWITH_ID in (SELECT WF_DEALWITH_ID FROM JSDB.JSF_DEALWITH WHERE DATABASETABLE_ID=" + 
            workVO.getTableId() + 
            " AND DATABASERECORD_ID=" + workVO.getRecordId() + 
            " AND CURSTEPCOUNT=" + maxStep + ")");
        this.stmt.addBatch("delete from JSF_DEALWITH where DATABASETABLE_ID=" + 
            workVO.getTableId() + 
            " AND DATABASERECORD_ID=" + workVO.getRecordId() + 
            " AND CURSTEPCOUNT=" + maxStep);
        this.stmt.addBatch("delete from jsf_dealwithcomment where WF_DEALWITH_ID in (SELECT WF_DEALWITH_ID FROM JSDB.JSF_DEALWITH WHERE DATABASETABLE_ID=" + 
            workVO.getTableId() + 
            " AND DATABASERECORD_ID=" + workVO.getRecordId() + 
            " AND CURSTEPCOUNT=" + workVO.getPareStepCount() + 
            ") and dealwithemployee_id=" + workVO.getCurEmployeeId());
        this.stmt.addBatch("UPDATE JSDB.JSF_DEALWITH SET CURACTIVITYSTATUS=0 where DATABASETABLE_ID=" + 
            workVO.getTableId() + 
            " AND DATABASERECORD_ID=" + workVO.getRecordId() + 
            " AND CURSTEPCOUNT=" + workVO.getPareStepCount());
        this.stmt.addBatch(
            "delete from jsf_work where workStatus='0' and workprocess_id='" + 
            workVO.getProcessId() + "' and worktable_id='" + 
            workVO.getTableId() + "' and workrecord_id='" + 
            workVO.getRecordId() + "' AND workstepcount=" + maxStep);
        this.stmt.addBatch(
            "update JSDB.jsf_work set workStatus = 0,DEALWITHTIME = null,WORKDELETE=0, workCurStep = '" + 
            activityName + "', " + 
            " workActivity = " + activityId + 
            ", workTitle = replace(workTitle,'您已办理完毕','等待您处理')" + 
            " where workStatus='101'  and WORKSTEPCOUNT=" + 
            stepCount + " and wf_curEmployee_id='" + 
            workVO.getCurEmployeeId() + "' and workprocess_id='" + 
            workVO.getProcessId() + "' and worktable_id='" + 
            workVO.getTableId() + "' and workrecord_id='" + 
            workVO.getRecordId() + "' and wf_work_id='" + workVO.getId() + "'");
        this.stmt.addBatch("delete from JSF_WORK where (workStatus<>'1' and workStatus<>'100') and workstepcount > '" + stepCount + "' and workprocess_id='" + workVO.getProcessId() + "' and worktable_id='" + workVO.getTableId() + "' and workrecord_id='" + workVO.getRecordId() + "'");
        this.stmt.addBatch(
            "UPDATE JSDB.JSF_WORK SET WORKALLOWCANCEL=0 WHERE WORKTABLE_ID=" + 
            workVO.getTableId() + " AND WORKRECORD_ID=" + 
            workVO.getRecordId() + " AND WORKSTEPCOUNT=" + (
            Integer.parseInt(stepCount) - 1));
        this.stmt.addBatch("update JSDB.JSF_WORK set workCurStep = '" + 
            activityName + "', " + 
            " workActivity = " + activityId + 
            " where workprocess_id='" + 
            workVO.getProcessId() + "' and worktable_id='" + 
            workVO.getTableId() + "' and workrecord_id='" + 
            workVO.getRecordId() + "'");
        this.stmt.addBatch(
            "UPDATE JSDB.JSF_WORK SET WORKSTATUS=1,workTitle = replace(workTitle,'已办理完毕','正在办理中'),WORKALLOWCANCEL=0 WHERE workTable_Id=" + 
            workVO.getTableId() + " and workRecord_id=" + 
            workVO.getRecordId() + " and WORKSTATUS=100");
        this.stmt.addBatch(
            "UPDATE JSDB.JSF_WORK SET workDoneWithDate=null WHERE WORKRECORD_ID=" + 
            workVO.getRecordId() + " AND WORKTABLE_ID=" + 
            workVO.getTableId());
        this.stmt.executeBatch();
        this.stmt.clearBatch();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return 
      String.valueOf(activityId) + "," + activityName;
  }
  
  public Boolean receiveWork(WorkVO workVO) throws Exception {
    Boolean bl = new Boolean(false);
    begin();
    ResultSet rs = null;
    String sql = "";
    String activityName = "";
    String activityId = "";
    String stepCount = "";
    stepCount = workVO.getCurStep();
    try {
      sql = "SELECT ACTIVITY_ID,activityName,curstepcount FROM JSDB.JSF_DEALWITH WHERE DATABASETABLE_ID=" + 
        workVO.getTableId() + 
        " AND DATABASERECORD_ID=" + workVO.getRecordId() + 
        " AND ACTIVITY_ID=" + workVO.getActivity();
      rs = this.stmt.executeQuery(sql);
      if (rs.next()) {
        activityId = rs.getString("ACTIVITY_ID");
        activityName = rs.getString("activityName");
      } 
      rs.close();
      this.stmt.addBatch(
          "delete from JSF_WORK where workStatus=0 and workprocess_id=" + 
          workVO.getProcessId() + " and worktable_id=" + 
          workVO.getTableId() + " and workrecord_id=" + 
          workVO.getRecordId());
      this.stmt.addBatch(
          "update JSDB.JSF_WORK set workStatus = 0, WORKDELETE=0, workCurStep = '" + activityName + "', " + 
          " workActivity = " + activityId + 
          ", workTitle = replace(workTitle,'您已办理完毕','等待您处理')" + 
          " where workStatus=101 and WORKSTEPCOUNT=" + 
          stepCount + " and wf_curEmployee_id=" + 
          workVO.getCurEmployeeId() + " and workprocess_id=" + 
          workVO.getProcessId() + " and worktable_id=" + 
          workVO.getTableId() + " and workrecord_id=" + 
          workVO.getRecordId());
      this.stmt.addBatch("delete from JSF_WORK where (workStatus<>1 and workStatus<>100) and workstepcount > " + stepCount + " and workprocess_id=" + workVO.getProcessId() + " and worktable_id=" + workVO.getTableId() + " and workrecord_id=" + workVO.getRecordId());
      this.stmt.addBatch(
          "UPDATE JSDB.JSF_WORK SET WORKALLOWCANCEL=0 WHERE WORKTABLE_ID=" + 
          workVO.getTableId() + " AND WORKRECORD_ID=" + 
          workVO.getRecordId() + " AND WORKSTEPCOUNT=" + (
          Integer.parseInt(stepCount) - 1));
      this.stmt.addBatch("update JSDB.JSF_WORK set workCurStep = '" + 
          activityName + "', " + 
          " workActivity = " + activityId + 
          " where workprocess_id=" + 
          workVO.getProcessId() + " and worktable_id=" + 
          workVO.getTableId() + " and workrecord_id=" + 
          workVO.getRecordId());
      this.stmt.addBatch(
          "UPDATE JSDB.JSF_WORK SET WORKSTATUS=1,workTitle = replace(workTitle,'已办理完毕','正在办理中'),WORKALLOWCANCEL=0 WHERE workTable_Id=" + 
          workVO.getTableId() + " and workRecord_id=" + 
          workVO.getRecordId() + " and WORKSTATUS=100");
      this.stmt.addBatch(
          "UPDATE JSDB.JSF_WORK SET workDoneWithDate=null WHERE WORKRECORD_ID=" + 
          workVO.getRecordId() + " AND WORKTABLE_ID=" + 
          workVO.getTableId());
      this.stmt.executeBatch();
      this.stmt.clearBatch();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return bl;
  }
  
  public Boolean deleteWork(WorkVO workVO) throws Exception {
    Boolean bl = new Boolean(false);
    begin();
    try {
      this.stmt.addBatch("delete from JSF_DEALWITH where DATABASETABLE_ID=" + 
          workVO.getTableId() + 
          " AND DATABASERECORD_ID=" + workVO.getRecordId());
      this.stmt.addBatch("delete from jsf_dealwithcomment where WF_DEALWITH_ID in (SELECT WF_DEALWITH_ID FROM JSDB.JSF_DEALWITH WHERE DATABASETABLE_ID=" + 
          workVO.getTableId() + 
          " AND DATABASERECORD_ID=" + workVO.getRecordId() + ")");
      this.stmt.addBatch("delete from jsf_work where workprocess_id='" + 
          workVO.getProcessId() + "' and worktable_id='" + 
          workVO.getTableId() + "' and workrecord_id='" + 
          workVO.getRecordId() + "'");
      this.stmt.addBatch("delete from jsf_p_transition where wf_proceedactivity_id in(select wf_proceedactivity_id from jsf_p_activity where wf_workflowprocess_id='" + 
          workVO.getProcessId() + "' and ttable_id='" + 
          workVO.getTableId() + "' and trecord_id='" + 
          workVO.getRecordId() + "')");
      this.stmt.addBatch(
          "delete from jsf_p_activity where wf_workflowprocess_id='" + 
          workVO.getProcessId() + "' and ttable_id='" + 
          workVO.getTableId() + "' and trecord_id='" + 
          workVO.getRecordId() + "'");
      this.stmt.executeBatch();
      this.stmt.clearBatch();
      String delDataWhenDelFLow = SystemCommon.getDelDataWhenDelFLow();
      if (StringUtils.isNotEmpty(delDataWhenDelFLow) && "1".equals(delDataWhenDelFLow)) {
        ResultSet rs = this.stmt.executeQuery("select c.area_table from tarea c where c.page_id=" + workVO.getTableId());
        String tableName = "";
        while (rs.next())
          tableName = rs.getString(1); 
        if (!"".equals(tableName))
          this.stmt.execute("delete from  " + tableName + " where " + tableName + "_ID=" + workVO.getRecordId()); 
        rs.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return bl;
  }
  
  public List getReceiveActivityList(String tableId, String recordId, String userId) throws Exception {
    List<String[]> aList = new ArrayList();
    SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 hh时mm分ss秒");
    begin();
    try {
      String sql = "select distinct a.activity_id,a.activityname,emp.emp_id,emp.empName,a.CURSTEPCOUNT from JSF_DEALWITH a ,jsf_dealwithcomment b, org_employee emp where a.wf_dealwith_id=b.wf_dealwith_id and b.dealwithemployee_id = emp.emp_id AND CURSTEPCOUNT<>0 and (b.commtype is null or (b.commtype<>1 and b.commtype<>2)) and a.databasetable_id=" + 
        tableId + " and a.databaserecord_id=" + recordId + " and emp.emp_id = " + userId + 
        " order by a.curstepcount desc";
      ResultSet rs = this.stmt.executeQuery(sql);
      while (rs.next()) {
        String[] tmp = { "", "", "", "", "", "" };
        tmp[0] = rs.getString(1);
        tmp[1] = rs.getString(2);
        tmp[2] = rs.getString(3);
        tmp[3] = rs.getString(4);
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
  
  public List getFrontActivityOper(WorkVO workVO) throws Exception {
    begin();
    ArrayList<Object[]> alist = new ArrayList();
    ResultSet rs = null;
    String sql = "";
    try {
      int maxStep = 0;
      sql = "select max(workstepcount) from jsf_work a, org_employee b  where a.wf_curemployee_id=b.emp_Id and a.worktable_id='" + 
        
        workVO.getTableId() + 
        "' and a.workrecord_id='" + workVO.getRecordId() + 
        "' and a.workactivity='" + workVO.getActivity() + 
        "' and (a.workstatus<>1)";
      rs = this.stmt.executeQuery(sql);
      if (rs.next())
        maxStep = rs.getInt(1); 
      rs.close();
      sql = "select distinct b.emp_Id,b.empName from JSF_WORK a, org_employee b  where a.wf_curemployee_id=b.emp_Id and a.worktable_id='" + 
        
        workVO.getTableId() + 
        "' and a.workrecord_id='" + workVO.getRecordId() + 
        "' and a.workactivity='" + workVO.getActivity() + 
        "' and (a.workstatus<>1) and workstepcount=" + maxStep;
      rs = this.stmt.executeQuery(sql);
      while (rs.next()) {
        Object[] obj = new Object[2];
        obj[0] = String.valueOf(rs.getInt(1));
        obj[1] = rs.getString(2);
        alist.add(obj);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.stmt.close();
      this.conn.close();
    } 
    return alist;
  }
  
  public Boolean endWork(WorkVO workVO) throws Exception {
    Boolean bl = new Boolean(false);
    return bl;
  }
  
  public Boolean getOnlineUserByUserAccounts(String userAccounts) throws Exception {
    Boolean bl = new Boolean(false);
    begin();
    ResultSet rs = null;
    try {
      rs = this.stmt.executeQuery(
          "select * from sec_onlineuser where user_account='" + userAccounts + "'");
      if (rs.next())
        bl = new Boolean(true); 
      rs.close();
      end();
    } catch (Exception e) {
      end();
      e.printStackTrace();
      throw e;
    } 
    return bl;
  }
  
  public Boolean hasMoreDealFile(WorkVO workVO) throws Exception {
    Boolean bl = new Boolean(false);
    begin();
    ResultSet rs = null;
    String sql = "";
    String transactType = "", curStandForUserId = "0", isStandForWork = "0";
    int isParallel = 0, parallelStep = 0;
    String parallelId = "", parallelCurActId = "";
    String curEmployeeId = "0";
    try {
      sql = 
        " select transactType,isstandforwork,standforuserid,wf_curemployee_id,is_parallel,parallel_id,parallel_step,parallel_curactid from JSDB.JSF_WORK where wf_work_id = " + 
        workVO.getId();
      rs = this.stmt.executeQuery(sql);
      if (rs.next()) {
        transactType = rs.getString("transactType");
        isStandForWork = rs.getString("isstandforwork");
        curStandForUserId = rs.getString("standforuserid");
        curEmployeeId = rs.getString("wf_curemployee_id");
        isParallel = rs.getInt("is_parallel");
        parallelId = rs.getString("parallel_id");
        parallelStep = rs.getInt("parallel_step");
        parallelCurActId = rs.getString("parallel_curactid");
        if (transactType == null)
          transactType = ""; 
      } 
      rs.close();
      if (isParallel == 1) {
        bl = new Boolean(false);
      } else if (transactType.equals("2") || transactType.equals("0")) {
        bl = new Boolean(false);
      } else if (transactType.equals("1") || transactType.equals("3")) {
        sql = 
          " select wf_work_id,wf_curemployee_id,isstandforwork,standforuserid from JSDB.JSF_WORK where worktable_id = " + 
          workVO.getTableId() + 
          " and workRecord_id = " + workVO.getRecordId() + 
          " and workStepCount = " + workVO.getStepCount() + 
          " and workstatus = 0 and wf_work_id<>" + 
          workVO.getId();
        rs = this.stmt.executeQuery(sql);
        while (rs.next()) {
          String curEmployeeIdTemp = rs.getString(2);
          String isStandTemp = rs.getString(3);
          String standForUserIdTemp = rs.getString(4);
          if ("1".equals(isStandForWork)) {
            if (!"0".equals(isStandTemp) || !curStandForUserId.equals(curEmployeeIdTemp)) {
              bl = new Boolean(true);
              break;
            } 
            continue;
          } 
          if (!"1".equals(isStandTemp) || !curEmployeeId.equals(standForUserIdTemp)) {
            bl = new Boolean(true);
            break;
          } 
        } 
        rs.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return bl;
  }
  
  public String getCurTransactTypeByWorkId(String workId) throws Exception {
    begin();
    ResultSet rs = null;
    String curTransactType = "";
    try {
      rs = this.stmt.executeQuery(" select transactType from JSDB.JSF_WORK where wf_work_id = " + workId);
      if (rs.next())
        curTransactType = rs.getString("transactType"); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return curTransactType;
  }
  
  public List getAllPassRoundUsers(WorkVO workVO) throws Exception {
    begin();
    ArrayList<Object[]> alist = new ArrayList();
    ResultSet rs = null;
    String sql = "";
    try {
      sql = 
        "select distinct b.emp_Id,b.empName from JSF_WORK a, org_employee b  where a.wf_curemployee_id=b.emp_Id and a.worktable_id='" + 
        
        workVO.getTableId() + 
        "' and a.workrecord_id='" + workVO.getRecordId() + 
        "' and (a.workstatus=2 or a.workstatus=102) and a.initactivity='" + workVO.getActivity() + "'";
      rs = this.stmt.executeQuery(sql);
      while (rs.next()) {
        Object[] obj = new Object[2];
        obj[0] = (new StringBuilder(String.valueOf(rs.getInt(1)))).toString();
        obj[1] = rs.getString(2);
        alist.add(obj);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {}
    this.stmt.close();
    this.conn.close();
    return alist;
  }
  
  public String backToActivity(Map parameter) throws Exception {
    String sendSMS = (parameter.get("sendSMS") == null) ? "1" : parameter.get("sendSMS").toString();
    String exeUser = "";
    String databaseType = SystemCommon.getDatabaseType();
    String backInfo = parameter.get("backInfo").toString();
    if ("".equals(backInfo))
      backInfo = (parameter.get("comment") == null) ? "" : parameter.get("comment").toString(); 
    begin();
    try {
      String dealwithTime, workTableId = parameter.get("tableId").toString();
      String workRecordId = parameter.get("recordId").toString();
      StringBuffer commentIdBuffer = new StringBuffer("-1");
      ResultSet rs = this.stmt.executeQuery("SELECT wf_dealwithcomment_id FROM jsf_dealwithcomment dwc WHERE EXISTS (SELECT dw.wf_dealwith_id FROM jsf_dealwith dw WHERE dw.wf_dealwith_id=dwc.wf_dealwith_id and databasetable_id=" + workTableId + " AND databaserecord_id=" + workRecordId + " AND curstepcount>" + parameter.get("backToStep").toString() + ")");
      while (rs.next())
        commentIdBuffer.append(",").append(rs.getString(1)); 
      rs.close();
      rs = this.stmt.executeQuery("SELECT wf_dealwithcomment_id FROM jsf_dealwithcomment dwc WHERE EXISTS (SELECT dw.wf_dealwith_id FROM jsf_dealwith dw WHERE dw.wf_dealwith_id=dwc.wf_dealwith_id and databasetable_id=" + workTableId + " AND databaserecord_id=" + workRecordId + " AND curstepcount=" + parameter.get("backToStep").toString() + ") AND dealwithemployee_id= " + parameter.get("backToUserId"));
      if (rs.next())
        commentIdBuffer.append(",").append(rs.getString(1)); 
      rs.close();
      this.stmt.executeUpdate("update jsf_dealwithcomment set commentisback=1 where wf_dealwithcomment_id in(" + commentIdBuffer.toString() + ")");
      String workTitle = "", workTitle2 = "";
      rs = this.stmt.executeQuery(
          "SELECT WORKTITLE FROM JSDB.JSF_WORK WHERE workTable_Id=" + 
          workTableId + " and workRecord_id=" + 
          workRecordId + 
          " and (WORKSTATUS=1 or WORKSTATUS=101)");
      if (rs.next())
        workTitle = rs.getString(1); 
      rs.close();
      workTitle2 = workTitle;
      if (workTitle.endsWith("等待您的办理！")) {
        workTitle = workTitle.substring(0, 
            workTitle.length() - 
            "等待您处理！".length());
        workTitle = String.valueOf(workTitle) + "已被您退回！";
      } 
      if (workTitle.endsWith("您已办理完毕！")) {
        workTitle = workTitle.substring(0, 
            workTitle.length() - 
            "您已办理完毕！".length());
        workTitle = String.valueOf(workTitle) + "已被您退回！";
      } 
      if (workTitle2.endsWith("您已办理完毕！")) {
        workTitle2 = workTitle2.substring(0, 
            workTitle2.length() - 
            "您已办理完毕！".length());
        String workTitle3 = String.valueOf(workTitle2) + "正在办理中！";
        workTitle2 = String.valueOf(workTitle2) + "等待您处理！";
        this.stmt.addBatch(
            "UPDATE JSDB.JSF_WORK SET WORKSTATUS=1,WORKTITLE='" + 
            workTitle3 + "',WORKALLOWCANCEL=0 WHERE workTable_Id=" + 
            workTableId + " and workRecord_id=" + 
            workRecordId + " and WORKSTATUS=100");
        this.stmt.addBatch(
            "UPDATE JSDB.JSF_WORK SET workDoneWithDate=null WHERE WORKRECORD_ID=" + 
            workRecordId + " AND WORKTABLE_ID=" + 
            workTableId);
      } 
      this.stmt.addBatch("update JSF_WORK set workstatus=-101,workstepcount=-99,initstepcount=-99 where (workStatus<>1 and workStatus<>100) and worktable_id=" + 
          workTableId + " and workrecord_id=" + 
          workRecordId + " and WORKSTEPCOUNT>" + 
          parameter.get("backToStep"));
      this.stmt.addBatch(
          "UPDATE JSDB.JSF_WORK SET WORKALLOWCANCEL=0 WHERE WORKTABLE_ID=" + 
          workTableId + " AND WORKRECORD_ID=" + 
          workRecordId + " AND WORKSTEPCOUNT=" + (
          Integer.parseInt(parameter.get("backToStep").toString()) - 
          1));
      this.stmt.executeBatch();
      this.stmt.clearBatch();
      rs = this.stmt.executeQuery("SELECT a.initactivity,b.activityName FROM JSDB.jsf_WORK a,JSDB.jsf_activity b WHERE a.initactivity=b.wf_activity_id and a.workTable_Id=" + 
          workTableId + 
          " and a.workRecord_id=" + 
          workRecordId + 
          " AND a.WORKSTEPCOUNT=" + 
          parameter.get("backToStep"));
      String workCurStep = "";
      String workActivity = "";
      if (rs.next()) {
        workCurStep = rs.getString("activityName");
        workActivity = rs.getString("initactivity");
      } 
      rs.close();
      Date now = new Date();
      String backToUserId = parameter.get("backToUserId").toString();
      String sql = "select WORKFILETYPE,WORKTITLE,WORKMAINLINKFILE,WORKSUBMITPERSON,WORKSUBMITTIME,WF_SUBMITEMPLOYEE_ID,WORKPROCESS_ID,";
      sql = String.valueOf(sql) + "WORKDEADLINE,WORKPRESSTIME,wf_curemployee_id,";
      sql = String.valueOf(sql) + "WORKISTRAN,WORKUSER,CREATORCANCELLINK,ISSTANDFORWORK,STANDFORUSERID,STANDFORUSERNAME,INITACTIVITY,INITSTEPCOUNT,";
      sql = String.valueOf(sql) + "ISSUBPROCWORK,PAREPROCACTIID,PARESTEPCOUNT,PARETABLEID,PARERECORDID,PAREPROCNEXTACTIID,SUBMITORG,emergence,";
      sql = String.valueOf(sql) + "transactType,INITACTIVITYNAME,workDeadlineDate,workDeadlinePressDate,workTask,tranType,tranFromPersonId,processDeadlineDate,";
      sql = String.valueOf(sql) + "relproject_id,WFCUREMPLOYEEORGID,WORK_HANGUP,handleInfo,stickie  from jsf_work where  WORKTABLE_ID=" + workTableId;
      sql = String.valueOf(sql) + " AND WORKRECORD_ID=" + workRecordId + " AND WORKSTEPCOUNT=" + parameter.get("backToStep") + 
        " AND wf_curemployee_id=" + parameter.get("backToUserId");
      List<MessagesVO> msgList = new ArrayList();
      rs = this.stmt.executeQuery(sql);
      if (rs.next()) {
        String processDeadlineDateStr, workFileType = rs.getString("WORKFILETYPE");
        workTitle = rs.getString("WORKTITLE");
        workTitle = workTitle.replaceAll("您已办理完毕", "被" + parameter.get("userName") + "退回");
        if ("shandongguotou".equals(SystemCommon.getCustomerName()))
          workTitle = "【退回】" + workTitle; 
        String workMainLinkFile = rs.getString("WORKMAINLINKFILE");
        String workSubmitPerson = rs.getString("WORKSUBMITPERSON");
        String workSubmitTime = rs.getString("WORKSUBMITTIME");
        if (workSubmitTime.indexOf(".") > 0)
          workSubmitTime = workSubmitTime.substring(0, workSubmitTime.indexOf(".")); 
        String submitEmployeeId = rs.getString("WF_SUBMITEMPLOYEE_ID");
        String workProcessId = rs.getString("WORKPROCESS_ID");
        String workDeadLine = rs.getString("WORKDEADLINE");
        String workPressTime = rs.getString("WORKPRESSTIME");
        String wf_curemployee_id = rs.getString("wf_curemployee_id");
        String workIsTran = rs.getString("WORKISTRAN");
        String workUser = rs.getString("WORKUSER");
        String createOrCancelLink = rs.getString("CREATORCANCELLINK");
        String isStandForWork = rs.getString("ISSTANDFORWORK");
        String standForUserId = rs.getString("STANDFORUSERID");
        String standForUserName = rs.getString("STANDFORUSERNAME");
        String initActivity = rs.getString("INITACTIVITY");
        String initStepCount = rs.getString("INITSTEPCOUNT");
        String isSubProcWork = rs.getString("ISSUBPROCWORK");
        String pareProcActiId = rs.getString("PAREPROCACTIID");
        String pareStepCount = rs.getString("PARESTEPCOUNT");
        String pareTableId = rs.getString("PARETABLEID");
        String pareRecordId = rs.getString("PARERECORDID");
        String pareProcNextActiId = rs.getString("PAREPROCNEXTACTIID");
        String submitOrg = rs.getString("SUBMITORG");
        String emergence = rs.getString("emergence");
        String transactType = rs.getString("transactType");
        String initActivityName = rs.getString("INITACTIVITYNAME");
        String workDeadlineDateStr = rs.getString("workDeadlineDate");
        String workDeadlinePressDateStr = rs.getString("workDeadlinePressDate");
        String workTask = rs.getString("workTask");
        String tranType = rs.getString("tranType");
        if (tranType == null || "null".equals(tranType))
          tranType = "0"; 
        String tranFromPersonId = rs.getString("tranFromPersonId");
        Timestamp processDeadlineDate = rs.getTimestamp("processDeadlineDate");
        String relprojectId = rs.getString("relproject_id");
        String curemployeeOrgId = rs.getString("WFCUREMPLOYEEORGID");
        String workHangup = rs.getString("WORK_HANGUP");
        String handInfo = rs.getString("handleInfo");
        String stickie = rs.getString("stickie");
        rs.close();
        Date workDeadlineDate = now;
        Date workDeadlinePressDate = now;
        if (!"-1".equals(workDeadLine)) {
          workDeadlineDate = new Date(now.getTime() + Long.parseLong(workDeadLine) * 1000L);
          workDeadlinePressDate = new Date(now.getTime() + (Long.parseLong(workDeadLine) - Long.parseLong(workPressTime)) * 1000L);
        } 
        if (databaseType.indexOf("mysql") >= 0) {
          processDeadlineDateStr = (processDeadlineDate == null) ? "null" : ("'" + processDeadlineDate.toLocaleString() + "'");
        } else {
          processDeadlineDateStr = (processDeadlineDate == null) ? "null" : ("JSDB.FN_STRTODATE('" + processDeadlineDate.toLocaleString() + "','L')");
        } 
        List<String[]> toUserList = getStandForUserByProcessAndOrgWithConn(new String[] { backToUserId }, workProcessId, submitEmployeeId, this.conn, this.stmt);
        String newWorkId = "";
        String[] standForUser = (String[])null;
        for (int i = toUserList.size() - 1; i >= 0; i--) {
          standForUser = toUserList.get(i);
          if (standForUser[0] != null && !standForUser[0].equals("")) {
            exeUser = String.valueOf(exeUser) + "," + standForUser[0] + ",";
            newWorkId = getTableId();
            if (databaseType.indexOf("mysql") >= 0) {
              sql = "insert into JSDB.JSF_WORK ( wf_work_id,wf_curEmployee_id,workStatus,workFileType,workCurStep,workTitle,workLeftLinkFile,workMainLinkFile,workListControl,workActivity,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workReadMarker,workType,workProcess_id,worktable_id,workrecord_id,workDeadLine,workPressTime,workCreateDate,workStepCount,isStandForWork,standForUserId,standForUserName,initActivity,initStepCount,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,domain_id,emergence,transactType,INITACTIVITYNAME,dealTips,workDeadlineDate,workDeadlinePressDate,processDeadlineDate,relproject_id) values ( " + 





                
                newWorkId + "," + standForUser[0] + ",0,'" + workFileType + "','" + workCurStep + 
                "','" + workTitle + "'," + "'','" + workMainLinkFile + "', 1," + workActivity + ",'" + workSubmitPerson + "','" + 
                workSubmitTime + "'," + submitEmployeeId + ",0,1," + workProcessId + "," + workTableId + "," + 
                workRecordId + "," + workDeadLine + "," + workPressTime + ",'" + now.toLocaleString() + "'," + parameter.get("backToStep") + "," + isStandForWork + 
                "," + standForUserId + ",'" + standForUserName + "'," + initActivity + "," + initStepCount + "," + isSubProcWork + "," + pareProcActiId + "," + 
                pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "',0," + emergence + "," + 
                transactType + ",'" + initActivityName + "','" + backInfo + "','" + workDeadlineDate.toLocaleString() + "','" + workDeadlinePressDate.toLocaleString() + "'," + processDeadlineDateStr + "," + relprojectId + ")";
            } else {
              sql = "insert into JSDB.JSF_WORK ( wf_work_id,wf_curEmployee_id,workStatus,workFileType,workCurStep,workTitle,workLeftLinkFile,workMainLinkFile,workListControl,workActivity,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workReadMarker,workType,workProcess_id,worktable_id,workrecord_id,workDeadLine,workPressTime,workCreateDate,workStepCount,isStandForWork,standForUserId,standForUserName,initActivity,initStepCount,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,domain_id,emergence,transactType,INITACTIVITYNAME,dealTips,workDeadlineDate,workDeadlinePressDate,processDeadlineDate,relproject_id) values ( " + 





                
                newWorkId + "," + standForUser[0] + ",0,'" + workFileType + "','" + workCurStep + 
                "','" + workTitle + "'," + "'','" + workMainLinkFile + "',1," + workActivity + ",'" + workSubmitPerson + "'," + 
                "JSDB.FN_STRTODATE('" + workSubmitTime + "','')," + submitEmployeeId + ",0,1," + workProcessId + "," + workTableId + "," + 
                workRecordId + "," + workDeadLine + "," + workPressTime + ",JSDB.FN_STRTODATE('" + now.toLocaleString() + "','')," + parameter.get("backToStep") + "," + isStandForWork + 
                "," + standForUserId + ",'" + standForUserName + "'," + initActivity + "," + initStepCount + "," + isSubProcWork + "," + pareProcActiId + "," + 
                pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + ",'" + submitOrg + "',0," + emergence + "," + 
                transactType + ",'" + initActivityName + "','" + backInfo + "',JSDB.FN_STRTODATE('" + workDeadlineDate.toLocaleString() + "','L'),JSDB.FN_STRTODATE('" + workDeadlinePressDate.toLocaleString() + "','L')," + processDeadlineDateStr + "," + relprojectId + ")";
            } 
            this.stmt.executeUpdate(sql);
            MessagesVO msgVO = new MessagesVO();
            msgVO.setMessage_date_begin(now);
            msgVO.setMessage_date_end(new Date("2050/1/1"));
            msgVO.setMessage_send_UserId(Long.parseLong(submitEmployeeId));
            msgVO.setMessage_send_UserName(workSubmitPerson);
            msgVO.setMessage_show(1);
            msgVO.setMessage_status(1);
            msgVO.setMessage_time(now);
            msgVO.setMessage_title(workTitle);
            msgVO.setMessage_toUserId(Long.parseLong(standForUser[0]));
            msgVO.setMessage_type("jsflow");
            msgVO.setData_id(Long.parseLong(newWorkId));
            msgVO.setMessage_url("/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + newWorkId);
            msgVO.setSendSMS(Integer.valueOf(sendSMS).intValue());
            msgList.add(msgVO);
            if (!standForUser[2].equals("")) {
              exeUser = String.valueOf(exeUser) + "," + standForUser[2] + ",";
              newWorkId = getTableId();
              if (databaseType.indexOf("mysql") >= 0) {
                sql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType, workCurStep,  workTitle, workLeftLinkFile, workMainLinkFile, workListControl, workActivity, workSubmitPerson, workSubmitTime, wf_submitEmployee_id, workReadMarker, workType, workProcess_id, workTable_id, workRecord_id, workDeadLine, workPressTime, workCreateDate, workstartflag, workIsTran, workStepCount,initActivity, initStepCount,submitOrg,isSubProcWork,isStandForWork,standForUserId, standForUserName,pareProcActiId,pareStepCount,pareTableId,pareRecordId, pareProcNextActiId,domain_id,emergence,transactType,INITACTIVITYNAME, dealTips,workDeadlineDate,workDeadlinePressDate,tranType, tranFromPersonId, processDeadlineDate,relproject_id) values (" + 









                  
                  newWorkId + "," + standForUser[2] + ",0,'" + workFileType + "','" + workCurStep + "','" + 
                  workTitle + "','','" + workMainLinkFile + "',1," + workActivity + ",'" + 
                  workSubmitPerson + "','" + workSubmitTime + "'," + submitEmployeeId + ",0,1," + 
                  workProcessId + "," + workTableId + "," + workRecordId + "," + workDeadLine + "," + workPressTime + ",'" + 
                  now.toLocaleString() + "',0,1," + parameter.get("backToStep") + "," + initActivity + "," + 
                  initStepCount + ", '" + submitOrg + "'," + isSubProcWork + ",1,'" + 
                  standForUser[0] + "','" + standForUser[1] + "','" + pareProcActiId + "','" + 
                  pareStepCount + "','" + pareTableId + "','" + pareRecordId + "','" + pareProcNextActiId + "','0','" + emergence + "','" + 
                  transactType + "','" + initActivityName + "','" + backInfo + "','" + workDeadlineDate.toLocaleString() + "','" + 
                  workDeadlinePressDate.toLocaleString() + "','" + tranType + "','" + wf_curemployee_id + "'," + processDeadlineDateStr + "," + relprojectId + ")";
              } else {
                sql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType, workCurStep,  workTitle, workLeftLinkFile, workMainLinkFile, workListControl,  workActivity, workSubmitPerson, workSubmitTime, wf_submitEmployee_id,  workReadMarker, workType, workProcess_id, workTable_id, workRecord_id,  workDeadLine, workPressTime, workCreateDate, workstartflag, workIsTran,  workStepCount,initActivity,initStepCount,submitOrg,isSubProcWork,isStandForWork, standForUserId,standForUserName,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,domain_id,emergence,transactType,INITACTIVITYNAME,dealTips,workDeadlineDate,workDeadlinePressDate,tranType, tranFromPersonId,processDeadlineDate,relproject_id) values (" + 








                  
                  newWorkId + "," + standForUser[2] + ",0,'" + workFileType + "','" + workCurStep + 
                  "','" + workTitle + "','','" + workMainLinkFile + "',1," + workActivity + ",'" + workSubmitPerson + 
                  "',JSDB.FN_STRTODATE('" + workSubmitTime + "','')," + 
                  submitEmployeeId + ",0,1," + workProcessId + "," + 
                  workTableId + "," + workRecordId + "," + workDeadLine + "," + workPressTime + 
                  ",JSDB.FN_STRTODATE('" + now.toLocaleString() + "',''),0,1," + 
                  parameter.get("backToStep") + "," + initActivity + "," + initStepCount + ", '" + submitOrg + "'," + isSubProcWork + ",1,'" + 
                  standForUser[0] + "','" + standForUser[1] + "','" + pareProcActiId + "','" + 
                  pareStepCount + "','" + pareTableId + "','" + pareRecordId + "','" + pareProcNextActiId + "','0','" + emergence + "','" + 
                  transactType + "','" + initActivityName + "','" + backInfo + "',JSDB.FN_STRTODATE('" + workDeadlineDate.toLocaleString() + "','L'),JSDB.FN_STRTODATE('" + workDeadlinePressDate.toLocaleString() + "','L'),'" + 
                  tranType + "','" + wf_curemployee_id + "'," + processDeadlineDateStr + "," + relprojectId + ")";
              } 
              this.stmt.executeUpdate(sql);
              msgVO = new MessagesVO();
              msgVO.setMessage_date_begin(now);
              msgVO.setMessage_date_end(new Date("2050/1/1"));
              msgVO.setMessage_send_UserId(Long.parseLong(submitEmployeeId));
              msgVO.setMessage_send_UserName(workSubmitPerson);
              msgVO.setMessage_show(1);
              msgVO.setMessage_status(1);
              msgVO.setMessage_time(now);
              msgVO.setMessage_title(workTitle);
              msgVO.setMessage_toUserId(Long.parseLong(standForUser[2]));
              msgVO.setMessage_type("jsflow");
              msgVO.setData_id(Long.parseLong(newWorkId));
              msgVO.setMessage_url("/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + newWorkId);
              msgVO.setSendSMS(Integer.valueOf(sendSMS).intValue());
              msgList.add(msgVO);
            } 
          } 
        } 
      } 
      if (databaseType.indexOf("mysql") >= 0) {
        dealwithTime = "'" + now.toLocaleString() + "'";
      } else {
        dealwithTime = "JSDB.FN_STRTODATE('" + now.toLocaleString() + "','')";
      } 
      this.stmt.addBatch("UPDATE JSDB.JSF_WORK SET DEALWITHTIME=" + dealwithTime + " where wf_work_id=" + parameter.get("workId"));
      this.stmt.addBatch("UPDATE JSDB.JSF_WORK SET workStatus=-101,workstepcount=-99,initstepcount=-99,WORKDELETE=0 WHERE workStatus=101 and WORKTABLE_ID=" + 
          workTableId + " AND WORKRECORD_ID=" + 
          workRecordId + " AND WORKSTEPCOUNT=" + 
          parameter.get("backToStep") + " AND wf_curemployee_id=" + parameter.get("backToUserId"));
      this.stmt.addBatch("UPDATE JSDB.JSF_WORK SET workCurStep='" + 
          workCurStep + "',workActivity=" + workActivity + 
          " WHERE WORKTABLE_ID=" + workTableId + 
          " AND WORKRECORD_ID=" + workRecordId);
      this.stmt.executeBatch();
      this.stmt.clearBatch();
      if (msgList.size() > 0)
        (new MessagesBD()).messageArrayAdd(msgList); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return exeUser;
  }
  
  public List getFlowedActivity(String tableId, String recordId, String stepCount) throws Exception {
    List<String[]> aList = new ArrayList();
    SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 hh时mm分ss秒");
    begin();
    String sql = "";
    try {
      if (stepCount != null && !stepCount.equals("null") && !stepCount.equals("0")) {
        sql = "select distinct ACTIVITY_ID, ACTIVITYNAME, CURSTEPCOUNT ,dealwithemployee_id,empName from JSF_DEALWITH a left join jsf_dealwithcomment b on a.wf_dealwith_id=b.wf_dealwith_id left join org_employee c on b.dealwithemployee_id=c.emp_id where  (b.commtype is null or (b.commtype<>1  and b.commtype<>2)) and DATABASETABLE_ID=" + tableId + " AND DATABASERECORD_ID=" + recordId + " AND CURSTEPCOUNT<>0 AND CURSTEPCOUNT<" + stepCount + " and dealwithemployee_id in(select wf_curemployee_id from jsf_work where worktable_id=" + tableId + " and workrecord_id=" + recordId + ") order by a.CURSTEPCOUNT desc";
      } else {
        sql = "select distinct ACTIVITY_ID, ACTIVITYNAME, CURSTEPCOUNT ,dealwithemployee_id,empName from JSF_DEALWITH a left join jsf_dealwithcomment b on a.wf_dealwith_id=b.wf_dealwith_id left join org_employee c on b.dealwithemployee_id=c.emp_id where (b.commtype is null or (b.commtype<>1 and b.commtype<>2))  and  DATABASETABLE_ID=" + tableId + " AND DATABASERECORD_ID=" + recordId + " AND CURSTEPCOUNT<=(select max(workstepcount) from jsf_work where workdelete<>1 and worktable_id='" + tableId + "' and workrecord_id='" + recordId + "') and dealwithemployee_id in(select wf_curemployee_id from jsf_work where worktable_id=" + tableId + " and workrecord_id=" + recordId + ") order by a.CURSTEPCOUNT desc";
      } 
      ResultSet rs = this.stmt.executeQuery(sql);
      while (rs.next()) {
        String[] tmp = { "", "", "", "", "", "" };
        tmp[0] = rs.getString(1);
        tmp[1] = rs.getString(2);
        tmp[2] = rs.getString(3);
        tmp[3] = rs.getString(4);
        tmp[4] = rs.getString(5);
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
  
  public Integer backToSubmitPerson(Map parameter) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      ResultSet rs = this.stmt.executeQuery(
          "SELECT WORKTITLE FROM JSDB.JSF_WORK WHERE workTable_Id=" + 
          parameter.get("tableId") + " and workRecord_id=" + 
          parameter.get("recordId") + 
          " and (WORKSTATUS=1 or WORKSTATUS=101)");
      String workTitle = "";
      if (rs.next())
        workTitle = rs.getString(1); 
      rs.close();
      if (workTitle.endsWith("正在办理中！")) {
        workTitle = workTitle.substring(0, 
            workTitle.length() - 
            "正在办理中！".length());
        if ("shandongguotou".equals(
            SystemCommon.getCustomerName())) {
          workTitle = "【退回】" + workTitle + "已被" + parameter.get("userName") + 
            "退回！";
        } else {
          workTitle = String.valueOf(workTitle) + "已被" + parameter.get("userName") + 
            "退回！";
        } 
      } else if (workTitle.endsWith("您已办理完毕！")) {
        workTitle = workTitle.substring(0, 
            workTitle.length() - 
            "您已办理完毕！".length());
        if ("shandongguotou".equals(
            SystemCommon.getCustomerName())) {
          workTitle = "【退回】" + workTitle + "已被" + parameter.get("userName") + 
            "退回！";
        } else {
          workTitle = String.valueOf(workTitle) + "已被" + parameter.get("userName") + 
            "退回！";
        } 
        this.stmt.execute(
            "UPDATE JSDB.JSF_WORK SET workDoneWithDate=null WHERE WORKRECORD_ID=" + 
            parameter.get("recordId") + " AND WORKTABLE_ID=" + 
            parameter.get("tableId"));
      } else if ("shandongguotou".equals(
          SystemCommon.getCustomerName())) {
        workTitle = "【退回】" + workTitle + "被" + 
          parameter.get("userName") + "退回";
      } else {
        workTitle = String.valueOf(workTitle) + "（被" + parameter.get("userName") + 
          "退回）";
      } 
      this.stmt.addBatch(
          "UPDATE JSDB.JSF_WORK SET WORKSTATUS=-1,WORKTITLE='" + 
          workTitle + "' WHERE WORKSTARTFLAG=1 AND WORKTABLE_ID=" + 
          parameter.get("tableId") + " AND WORKRECORD_ID=" + 
          parameter.get("recordId"));
      String databaseType = SystemCommon.getDatabaseType();
      Date now = new Date();
      if (databaseType.indexOf("mysql") >= 0) {
        this.stmt.addBatch("UPDATE JSDB.JSF_WORK SET DEALWITHTIME='" + 
            now.toLocaleString() + "' where wf_work_id=" + parameter.get("workId"));
      } else {
        this.stmt.addBatch("UPDATE JSDB.JSF_WORK SET DEALWITHTIME=JSDB.FN_STRTODATE('" + 
            now.toLocaleString() + "','') where wf_work_id=" + parameter.get("workId"));
      } 
      this.stmt.addBatch(
          "UPDATE JSDB.JSF_WORK SET WORKSTATUS=-101,WORKTITLE='" + 
          workTitle + "' WHERE WORKSTATUS<>-1 AND WORKTABLE_ID=" + 
          parameter.get("tableId") + " AND WORKRECORD_ID=" + 
          parameter.get("recordId"));
      String backInfo = parameter.get("backInfo").toString();
      if ("".equals(backInfo))
        backInfo = (parameter.get("comment") == null) ? "" : parameter.get("comment").toString(); 
      this.stmt.addBatch("update JSDB.JSF_WORK set DEALTIPS = '" + backInfo + "' WHERE WORKSTARTFLAG=1 AND WORKTABLE_ID=" + 
          parameter.get("tableId") + " AND WORKRECORD_ID=" + 
          parameter.get("recordId"));
      this.stmt.executeBatch();
      this.stmt.clearBatch();
    } catch (Exception e) {
      e.printStackTrace();
      result = Integer.valueOf("-1");
      throw e;
    } finally {
      end();
    } 
    return result;
  }
  
  public Boolean showUndoButton(WorkVO workVO) throws Exception {
    Boolean bl = new Boolean(false);
    begin();
    ResultSet rs = null;
    ResultSet rss = null;
    String sql = "";
    int maxStep = 0;
    try {
      sql = 
        "SELECT max(workstepcount) FROM JSDB.JSF_WORK WHERE workTable_Id=" + 
        workVO.getTableId() + " and workRecord_id=" + 
        workVO.getRecordId() + " and worklistcontrol=1";
      rs = this.stmt.executeQuery(sql);
      if (rs.next())
        maxStep = rs.getInt(1); 
      rs.close();
      if (maxStep - Integer.parseInt(workVO.getStepCount()) == 1) {
        sql = "SELECT * FROM JSDB.JSF_WORK WHERE workTable_Id=" + 
          workVO.getTableId() + " and workRecord_id=" + 
          workVO.getRecordId() + " and worklistcontrol=1 and WORKSTATUS=101 and workstepcount=" + workVO.getStepCount() + 
          " and wf_work_id=" + workVO.getId() + " and DEALWITHTIME=(SELECT max(DEALWITHTIME) FROM JSDB.JSF_WORK WHERE workTable_Id=" + 
          workVO.getTableId() + " and workRecord_id=" + 
          workVO.getRecordId() + " and worklistcontrol=1 and WORKSTATUS=101 and workstepcount=" + workVO.getStepCount() + ")";
        rss = this.stmt.executeQuery(sql);
        if (maxStep - Integer.parseInt(workVO.getStepCount()) == 1 && rss.next())
          bl = new Boolean(true); 
        rss.close();
      } else if (maxStep == Integer.parseInt(workVO.getStepCount())) {
        sql = "SELECT transactType FROM JSDB.JSF_WORK WHERE wf_work_id=" + workVO.getId();
        rs = this.stmt.executeQuery(sql);
        if (rs.next()) {
          String transactType = rs.getString(1);
          if ("1".equals(transactType) || "3".equals(transactType)) {
            sql = 
              "SELECT * FROM JSDB.JSF_WORK WHERE workTable_Id=" + 
              workVO.getTableId() + " and workRecord_id=" + 
              workVO.getRecordId() + 
              " and worklistcontrol=1 and WORKSTATUS=101 and workstepcount=" + 
              workVO.getStepCount() + 
              " and wf_work_id=" + workVO.getId() + " and DEALWITHTIME=(SELECT max(DEALWITHTIME) FROM JSDB.JSF_WORK WHERE workTable_Id=" + 
              workVO.getTableId() + " and workRecord_id=" + 
              workVO.getRecordId() + 
              " and worklistcontrol=1 and WORKSTATUS=101 and workstepcount=" + 
              workVO.getStepCount() + ")";
            rss = this.stmt.executeQuery(sql);
            if (rss.next())
              bl = new Boolean(true); 
            rss.close();
          } 
        } 
        rs.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (rss != null)
        rss.close(); 
      if (rs != null)
        rs.close(); 
      end();
    } 
    return bl;
  }
  
  public String getSaveFirstWorkUrl(String processId, String processName, String tableId, String recordId) throws Exception {
    begin();
    ResultSet rs = null;
    String sql = "";
    String workUrl = "";
    try {
      sql = "select * from JSF_WORK where workprocess_id='" + processId + 
        "' and worktable_id='" + tableId + "' and workrecord_id='" + 
        recordId + "' and workStatus=0";
      rs = this.stmt.executeQuery(sql);
      if (rs.next())
        workUrl = String.valueOf(rs.getString("WORKMAINLINKFILE")) + 
          "&activityName=" + URLEncoder.encode((rs.getString("WORKCURSTEP") == null) ? "" : rs.getString("WORKCURSTEP")) + 
          "&submitPersonId=" + rs.getString("WF_SUBMITEMPLOYEE_ID") + 
          "&submitPerson=" + 
          URLEncoder.encode((rs.getString("WORKSUBMITPERSON") == null) ? "" : rs.getString("WORKSUBMITPERSON")) + 
          "&work=" + rs.getString("WF_WORK_ID") + "&workType=" + 
          rs.getString("WORKTYPE") + 
          "&activity=" + 
          rs.getString("WORKACTIVITY") + "&table=" + 
          rs.getString("worktable_id") + 
          "&record=" + 
          rs.getString("workrecord_id") + 
          "&processName=" + URLEncoder.encode((processName == null) ? "" : processName) + 
          "&workStatus=0&submitTime=" + 
          rs.getString("WORKSUBMITTIME") + "&processId=" + 
          rs.getString("WORKPROCESS_ID") + 
          "&stepCount=" + rs.getString("WORKSTEPCOUNT") + 
          "&isStandForWork=" + 
          rs.getString("ISSTANDFORWORK") + 
          "&standForUserId=" + rs.getString("STANDFORUSERID") + 
          "&standForUserName=" + URLEncoder.encode((rs.getString("STANDFORUSERNAME") == null) ? "" : rs.getString("STANDFORUSERNAME")); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return workUrl;
  }
  
  public List getAllDealwithUsersByStatus(WorkVO workVO, String workStatus) throws Exception {
    begin();
    ArrayList<Object[]> alist = new ArrayList();
    ResultSet rs = null;
    String sql = "";
    try {
      if (workStatus.equals("101")) {
        sql = 
          "select distinct b.emp_Id,b.empName,b.USERSIMPLENAME,c.orgname,c.orgnamestring from JSF_WORK a, org_employee b,org_organization c,org_organization_user d  where a.wf_submitemployee_id=b.emp_Id and b.emp_id=d.emp_id and d.org_id=c.org_id and a.worktable_id=" + 
          
          workVO.getTableId() + 
          " and a.workrecord_id=" + workVO.getRecordId() + 
          " and a.workstatus=0";
        rs = this.stmt.executeQuery(sql);
        if (rs.next()) {
          Object[] obj = new Object[5];
          obj[0] = (new StringBuilder(String.valueOf(rs.getInt(1)))).toString();
          obj[1] = rs.getString(2);
          obj[2] = rs.getString(3);
          obj[3] = rs.getString(4);
          obj[4] = rs.getString(5);
          alist.add(obj);
        } 
        rs.close();
      } 
      sql = 
        "select distinct b.emp_Id,b.empName,b.USERSIMPLENAME,c.orgname,c.orgnamestring,a.wf_submitemployee_id,a.worksubmitperson from JSF_WORK a, org_employee b,org_organization c,org_organization_user d  where a.wf_curemployee_id=b.emp_Id and b.emp_id=d.emp_id and d.org_id=c.org_id and a.worktable_id=" + 
        
        workVO.getTableId() + 
        " and a.workrecord_id=" + workVO.getRecordId() + 
        " and a.workstatus=" + workStatus;
      rs = this.stmt.executeQuery(sql);
      while (rs.next()) {
        if (!workStatus.equals("101") || (workStatus.equals("101") && !(new StringBuilder(String.valueOf(rs.getInt(1)))).toString().equals(rs.getString(6)))) {
          Object[] obj = new Object[5];
          obj[0] = (new StringBuilder(String.valueOf(rs.getInt(1)))).toString();
          obj[1] = rs.getString(2);
          obj[2] = rs.getString(3);
          obj[3] = rs.getString(4);
          obj[4] = rs.getString(5);
          alist.add(obj);
        } 
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {}
    end();
    return alist;
  }
  
  public List getAllDealwithUsersByStatus2(WorkVO workVO, String workStatus) throws Exception {
    begin();
    ArrayList<Object[]> alist = new ArrayList();
    ResultSet rs = null;
    String sql = "";
    try {
      sql = 
        "select distinct b.emp_Id,b.empName,b.USERSIMPLENAME,c.orgname,c.orgnamestring,a.wf_submitemployee_id,a.worksubmitperson,a.wf_work_id from JSF_WORK a, org_employee b,org_organization c,org_organization_user d  where a.wf_curemployee_id=b.emp_Id and b.emp_id=d.emp_id and d.org_id=c.org_id and a.worktable_id=" + 
        
        workVO.getTableId() + 
        " and a.workrecord_id=" + workVO.getRecordId() + 
        " and a.workstatus=" + workStatus + " order by a.wf_work_id desc";
      rs = this.stmt.executeQuery(sql);
      while (rs.next()) {
        if (!workStatus.equals("101") || (workStatus.equals("101") && !(new StringBuilder(String.valueOf(rs.getInt(1)))).toString().equals(rs.getString(6)))) {
          Object[] obj = new Object[5];
          obj[0] = (new StringBuilder(String.valueOf(rs.getInt(1)))).toString();
          obj[1] = rs.getString(2);
          obj[2] = rs.getString(3);
          obj[3] = rs.getString(4);
          obj[4] = rs.getString(5);
          alist.add(obj);
        } 
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {}
    end();
    return alist;
  }
  
  public List getAllDealWithLog(String processId, String tableId, String recordId) throws Exception {
    begin();
    List<String[]> list = new ArrayList();
    SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
    try {
      String sql = "select SENDUSERNAME,SENDTIME,SENDACTION,RECEIVEUSERNAME,RECEIVEUSERID,LOGID from JSF_DEALWITHLOG  where PROCESSID= " + 
        processId + " and TABLEID=" + tableId + " and RECORDID=" + recordId + 
        " order by LOGID asc";
      ResultSet rs2 = this.stmt.executeQuery(sql);
      while (rs2.next()) {
        String[] tmp = { "", "", "", "", "", "" };
        tmp[0] = rs2.getString(1);
        tmp[1] = sf.format(rs2.getTimestamp(2));
        tmp[2] = rs2.getString(3);
        tmp[3] = rs2.getString(4);
        tmp[4] = rs2.getString(5);
        tmp[5] = rs2.getString(6);
        list.add(tmp);
      } 
      rs2.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return list;
  }
  
  public void setDealWithLog(WorkLogVO workLogVO) throws Exception {
    begin();
    List list = new ArrayList();
    try {
      String databaseType = SystemCommon.getDatabaseType();
      String LOGID = getTableId();
      StringBuffer sql = new StringBuffer("INSERT INTO JSDB.JSF_DEALWITHLOG VALUES (");
      Date now = new Date();
      if (databaseType.indexOf("mysql") >= 0) {
        sql.append(LOGID).append(",").append(workLogVO.getSendUserId()).append(",'").append(workLogVO.getSendUserName()).append("','").append(now.toLocaleString()).append("','").append(workLogVO.getSendAction())
          .append("','").append(workLogVO.getReceiveUserId()).append("','").append(workLogVO.getReceiveUserName()).append("',").append(workLogVO.getProcessId()).append(",").append(workLogVO.getTableId()).append(",")
          .append(workLogVO.getRecordId()).append(",").append(workLogVO.getDomainId()).append(")");
      } else {
        sql.append(LOGID).append(",").append(workLogVO.getSendUserId()).append(",'").append(workLogVO.getSendUserName()).append("',JSDB.FN_STRTODATE('").append(now.toLocaleString()).append("',''),'").append(workLogVO.getSendAction())
          .append("','").append(workLogVO.getReceiveUserId()).append("','").append(workLogVO.getReceiveUserName()).append("',").append(workLogVO.getProcessId()).append(",").append(workLogVO.getTableId()).append(",")
          .append(workLogVO.getRecordId()).append(",").append(workLogVO.getDomainId()).append(")");
      } 
      this.stmt.execute(sql.toString());
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
  }
  
  public void setDealWithLogNoConn(WorkLogVO workLogVO) throws Exception {
    List list = new ArrayList();
    try {
      String databaseType = SystemCommon.getDatabaseType();
      String LOGID = getTableId();
      StringBuffer sql = new StringBuffer("INSERT INTO JSDB.JSF_DEALWITHLOG VALUES (");
      Date now = new Date();
      if (databaseType.indexOf("mysql") >= 0) {
        sql.append(LOGID).append(",").append(workLogVO.getSendUserId()).append(",'").append(workLogVO.getSendUserName()).append("','").append(now.toLocaleString()).append("','").append(workLogVO.getSendAction())
          .append("','").append(workLogVO.getReceiveUserId()).append("','").append(workLogVO.getReceiveUserName()).append("',").append(workLogVO.getProcessId()).append(",").append(workLogVO.getTableId()).append(",")
          .append(workLogVO.getRecordId()).append(",").append(workLogVO.getDomainId()).append(")");
      } else {
        sql.append(LOGID).append(",").append(workLogVO.getSendUserId()).append(",'").append(workLogVO.getSendUserName()).append("',JSDB.FN_STRTODATE('").append(now.toLocaleString()).append("',''),'").append(workLogVO.getSendAction())
          .append("','").append(workLogVO.getReceiveUserId()).append("','").append(workLogVO.getReceiveUserName()).append("',").append(workLogVO.getProcessId()).append(",").append(workLogVO.getTableId()).append(",")
          .append(workLogVO.getRecordId()).append(",").append(workLogVO.getDomainId()).append(")");
      } 
      this.stmt.execute(sql.toString());
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } 
  }
  
  public String getBackComment(String processId, String tableId, String recordId) throws Exception {
    StringBuffer result = new StringBuffer();
    begin();
    try {
      ResultSet rs = this.stmt.executeQuery("SELECT B.DEALWITHEMPLOYEECOMMENT,C.EMPNAME,B.DEALWITHTIME,B.ISSTANDFORCOMM,B.STANDFORUSERNAME FROM JSF_DEALWITH A,JSF_DEALWITHCOMMENT B,ORG_EMPLOYEE C WHERE A.WF_DEALWITH_ID=B.WF_DEALWITH_ID AND B.DEALWITHEMPLOYEE_ID=C.EMP_ID AND  B.commtype=1 and A.DATABASETABLE_ID=" + 
          
          tableId + " AND A.DATABASERECORD_ID=" + recordId + 
          " AND A.ACTIVITY_ID IN (SELECT WF_ACTIVITY_ID FROM JSF_P_ACTIVITY WHERE WF_WORKFLOWPROCESS_ID=" + processId + ")");
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
  
  public String getFieldInfoByFieldId(String fieldId) throws Exception {
    String ret = "";
    begin();
    try {
      String sql = "select a.field_name from tfield a where a.field_id=" + fieldId;
      ResultSet rs = this.stmt.executeQuery(sql);
      if (rs.next())
        ret = rs.getString(1); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return ret;
  }
  
  public String getFieldShowByFieldId(String fieldId) {
    String ret = "";
    try {
      begin();
      String sql = "select a.field_show from tfield a where a.field_id=" + fieldId;
      ResultSet rs = this.stmt.executeQuery(sql);
      if (rs.next())
        ret = rs.getString(1); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      end();
    } 
    return ret;
  }
  
  public String[] getModiCommentByCommField(String tableId, String recordId, String commField, String userId, String stepCount) throws Exception {
    String[] result = { "", "" };
    begin();
    try {
      ResultSet rs = null;
      String sql = "select b.dealwithemployeecomment,b.wf_dealwithcomment_id from JSF_DEALWITH a, jsf_dealwithcomment b where a.wf_dealwith_id=b.wf_dealwith_id  and  (B.commtype is null or B.commtype<>1) and a.databasetable_id=" + 
        tableId + " and a.databaserecord_id=" + recordId + " and b.dealwithemployee_id=" + userId + 
        
        " and commentfield='" + commField + "'";
      rs = this.stmt.executeQuery(sql);
      if (rs.next()) {
        result[0] = rs.getString(1);
        result[1] = rs.getString(2);
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
  
  public String[] getCommentUserAndDateByCommField(String tableId, String recordId, String commField) throws Exception {
    String[] result = { "", "", "", "" };
    begin();
    SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
    try {
      ResultSet rs = null;
      String sql = "select b.dealwithemployee_id, c.empname, b.dealwithtime,b.DEALWITHEMPLOYEECOMMENT from JSF_DEALWITH a, jsf_dealwithcomment b,org_employee c where a.wf_dealwith_id=b.wf_dealwith_id and b.dealwithemployee_id=c.emp_id  and a.databasetable_id='" + 
        tableId + "' and a.databaserecord_id='" + recordId + "' and commentfield='" + commField + "' order by b.wf_dealwithcomment_id desc";
      rs = this.stmt.executeQuery(sql);
      if (rs.next()) {
        result[0] = rs.getString(1);
        result[1] = rs.getString(2);
        result[2] = sf.format(rs.getTimestamp(3));
        result[3] = rs.getString(4);
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
  
  public String getCommentByCommField(String processId, String tableId, String recordId, String commField, String userId, String orgId, String isEdit, String hideModiComment) throws Exception {
    StringBuffer result = new StringBuffer("<table width=100%>");
    begin();
    try {
      ResultSet rs = null;
      StringBuffer sql = new StringBuffer("SELECT B.DEALWITHEMPLOYEECOMMENT,C.EMPNAME,B.DEALWITHTIME,B.ISSTANDFORCOMM,B.STANDFORUSERNAME,B.WF_DEALWITHCOMMENT_ID,B.RANGENAME,B.RANGEIDSTR,C.SIGNATUREIMGSAVENAME,B.commtype FROM JSF_DEALWITH A,JSF_DEALWITHCOMMENT B,ORG_EMPLOYEE C WHERE A.WF_DEALWITH_ID=B.WF_DEALWITH_ID AND B.DEALWITHEMPLOYEE_ID=C.EMP_ID AND   (B.commtype is null or B.commtype<>1) and A.DATABASETABLE_ID=" + 
          
          tableId + " AND A.DATABASERECORD_ID=" + recordId + " AND B.COMMENTFIELD='" + commField + "' " + 
          "AND A.ACTIVITY_ID IN (SELECT WF_ACTIVITY_ID FROM JSF_P_ACTIVITY WHERE WF_WORKFLOWPROCESS_ID=" + processId + ") ");
      if (!"".equals(hideModiComment))
        sql = new StringBuffer("SELECT B.DEALWITHEMPLOYEECOMMENT,C.EMPNAME,B.DEALWITHTIME,B.ISSTANDFORCOMM,B.STANDFORUSERNAME,B.WF_DEALWITHCOMMENT_ID,B.RANGENAME,B.RANGEIDSTR,C.SIGNATUREIMGSAVENAME,B.commtype FROM JSF_DEALWITH A,JSF_DEALWITHCOMMENT B,ORG_EMPLOYEE C WHERE A.WF_DEALWITH_ID=B.WF_DEALWITH_ID AND B.DEALWITHEMPLOYEE_ID=C.EMP_ID AND   (B.commtype is null or B.commtype<>1) and B.wf_dealwithcomment_id<>'" + 
            
            hideModiComment + "' and  (B.commtype is null or B.commtype<>1) and A.DATABASETABLE_ID=" + tableId + " AND A.DATABASERECORD_ID=" + recordId + " AND B.COMMENTFIELD='" + commField + "' " + 
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
      String comment = "", empName = "", dealwithDate = "", isStandForComm = "", standForUserName = "", SIGNATUREIMGSAVENAME = "", commtype = "";
      while (rs.next()) {
        if (rs.getString(1) == null || "".equals(rs.getString(1)) || rs.getString(1).indexOf("无批示意见") != -1)
          continue; 
        comment = rs.getString(1);
        empName = rs.getString(2);
        dealwithDate = rs.getString(3);
        isStandForComm = rs.getString(4);
        standForUserName = rs.getString(5);
        SIGNATUREIMGSAVENAME = (rs.getString(9) == null) ? "" : rs.getString(9);
        commtype = (rs.getString(10) == null) ? "" : rs.getString(10);
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
        result.append("&nbsp;&nbsp;</td>");
        result.append("<input type=hidden name=wf_dealwithcomment_id value=" + rs.getString(6) + ">");
        result.append("</tr>");
        if (isEdit != null && isEdit.equals("1")) {
          String rangeName = rs.getString(7);
          String rangeIdStr = rs.getString(8);
          result.append("<tr><td colspan=3>查看范围:<input type=text class=css0 size=30 readonly=true name=rangeName value=" + (
              (rangeName == null) ? "" : rangeName) + 
              "><input type=hidden name=rangeId value=" + (
              (rangeIdStr == null) ? "" : rangeIdStr) + ">&nbsp;<img src=/jsoa/images/group.gif>&nbsp;<img id=myImg src=/jsoa/images/select.gif style=cursor:hand onclick=\"include_myOpen(this, 'myImg', 'rangeName', 'rangeId')\"></td></tr>");
        } 
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
  
  public void addPersonWork(String[] para, String[] user) throws Exception {
    begin();
    try {
      String workFileType = "", workCurStep = "", workActivity = "", workSubmitPerson = "";
      String workSubmitTime = "", wf_submitEmployee_id = "", workType = "", workProcess_id = "";
      String workTable_id = "", workRecord_id = "", workDeadLine = "", workPressTime = "";
      String initActivity = "", initStepCount = "", workallowcancel = "", submitOrg = "", isSubProcWork = "0";
      int workStepCount = 0;
      String pareProcActiId = "", pareStepCount = "", pareTableId = "", pareRecordId = "", pareProcNextActiId = "", curTransactType = "";
      String domain_id = "", emergence = "", INITACTIVITYNAME = "", standForUserId = "", standForUserName = "";
      String wf_curemployee_id = "";
      String sql = "select workFileType,workCurStep,workActivity,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workType,workProcess_id,workTable_id,workRecord_id,workDeadLine,workPressTime,initActivity,initStepCount,workallowcancel,submitOrg,isSubProcWork,wf_curemployee_id,standForUserId,standForUserName,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,domain_id,emergence,transactType,INITACTIVITYNAME,workStepCount from JSDB.jsf_work where wf_work_id=" + 





        
        para[0];
      ResultSet rs = this.stmt.executeQuery(sql);
      if (rs.next()) {
        workFileType = rs.getString("workFileType");
        workCurStep = rs.getString("workCurStep");
        workActivity = rs.getString("workActivity");
        workSubmitPerson = rs.getString("workSubmitPerson");
        workSubmitTime = rs.getString("workSubmitTime");
        if (workSubmitTime.indexOf(".") > 0)
          workSubmitTime = workSubmitTime.substring(0, workSubmitTime.indexOf(".")); 
        wf_submitEmployee_id = rs.getString("wf_submitEmployee_id");
        workType = rs.getString("workType");
        workProcess_id = rs.getString("workProcess_id");
        workTable_id = rs.getString("workTable_id");
        workRecord_id = rs.getString("workRecord_id");
        workDeadLine = rs.getString("workDeadLine");
        workPressTime = rs.getString("workPressTime");
        initActivity = rs.getString("initActivity");
        initStepCount = rs.getString("initStepCount");
        workallowcancel = rs.getString("workallowcancel");
        submitOrg = rs.getString("submitOrg");
        isSubProcWork = rs.getString("isSubProcWork");
        wf_curemployee_id = rs.getString("wf_curemployee_id");
        standForUserId = (rs.getString("standForUserId") == null) ? "0" : rs.getString("standForUserId");
        standForUserName = rs.getString("standForUserName");
        pareProcActiId = rs.getString("pareProcActiId");
        pareStepCount = rs.getString("pareStepCount");
        pareTableId = rs.getString("pareTableId");
        pareRecordId = rs.getString("pareRecordId");
        pareProcNextActiId = rs.getString("pareProcNextActiId");
        domain_id = rs.getString("domain_id");
        emergence = rs.getString("emergence");
        curTransactType = rs.getString("transactType");
        INITACTIVITYNAME = rs.getString("INITACTIVITYNAME");
        workStepCount = rs.getInt("workStepCount");
      } 
      rs.close();
      Date now = new Date();
      String remindFieldValue = para[2];
      int k = (String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "等待您处理！").length() - 50;
      if (k > 0)
        remindFieldValue = remindFieldValue.substring(0, remindFieldValue.length() - k); 
      String tmpTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "等待您处理！";
      if (!para[4].equals(""))
        tmpTitle = para[4]; 
      String curWorkUser = "";
      sql = "select wf_curemployee_id from JSDB.JSF_WORK where workProcess_id=" + workProcess_id + 
        " and workTable_id=" + workTable_id + " and workRecord_id=" + workRecord_id + " and workStepCount=" + (workStepCount + 1) + " and WORKDELETE <>1 and workstatus = 0";
      rs = this.stmt.executeQuery(sql);
      while (rs.next())
        curWorkUser = String.valueOf(curWorkUser) + "$" + rs.getString("wf_curemployee_id") + "$"; 
      rs.close();
      int newWorkId = 0;
      StringBuffer workUser = new StringBuffer();
      List<MessagesVO> msgList = new ArrayList();
      MessagesVO msgVO = new MessagesVO();
      for (int i = 0; i < user.length; i++) {
        if (!user[i].equals(""))
          if (curWorkUser.indexOf("$" + user[i] + "$") == -1) {
            workUser.append("$" + user[i] + "$");
            newWorkId = Integer.parseInt(getTableId());
            String databaseType = SystemCommon.getDatabaseType();
            if (databaseType.indexOf("mysql") >= 0) {
              sql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType, workCurStep,  workTitle, workLeftLinkFile, workMainLinkFile, workListControl,  workActivity, workSubmitPerson, workSubmitTime, wf_submitEmployee_id,  workReadMarker, workType, workProcess_id, workTable_id, workRecord_id,  workDeadLine, workPressTime, workCreateDate, workstartflag, workIsTran,  workStepCount,initActivity,initStepCount,workallowcancel,submitOrg,isSubProcWork,standForUserId,standForUserName,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,domain_id,emergence,transactType,INITACTIVITYNAME ) values (" + 







                
                newWorkId + "," + user[i] + ",0,'" + workFileType + "','" + workCurStep + 
                "','" + tmpTitle + "','','" + para[3] + "',1," + workActivity + ",'" + workSubmitPerson + 
                "','" + workSubmitTime + "'," + 
                wf_submitEmployee_id + ",0," + workType + "," + workProcess_id + "," + 
                workTable_id + "," + workRecord_id + "," + workDeadLine + "," + workPressTime + 
                ",'" + now.toLocaleString() + "',0,0," + 
                para[1] + "," + initActivity + "," + initStepCount + ", " + workallowcancel + ",'" + submitOrg + "'," + isSubProcWork + ",'" + 
                standForUserId + "','" + standForUserName + "','" + pareProcActiId + "','" + 
                pareStepCount + "','" + pareTableId + "','" + pareRecordId + "','" + pareProcNextActiId + "','" + domain_id + "','" + emergence + "','" + 
                curTransactType + "','" + INITACTIVITYNAME + "')";
            } else {
              sql = " insert into JSDB.JSF_WORK (  wf_work_id, wf_curEmployee_id, workStatus, workFileType, workCurStep,  workTitle, workLeftLinkFile, workMainLinkFile, workListControl,  workActivity, workSubmitPerson, workSubmitTime, wf_submitEmployee_id,  workReadMarker, workType, workProcess_id, workTable_id, workRecord_id,  workDeadLine, workPressTime, workCreateDate, workstartflag, workIsTran,  workStepCount,initActivity,initStepCount,workallowcancel,submitOrg,isSubProcWork,standForUserId,standForUserName,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,domain_id,emergence,transactType,INITACTIVITYNAME ) values (" + 







                
                newWorkId + "," + user[i] + ",0,'" + workFileType + "','" + workCurStep + 
                "','" + tmpTitle + "','','" + para[3] + "',1," + workActivity + ",'" + workSubmitPerson + 
                "',JSDB.FN_STRTODATE('" + workSubmitTime + "','')," + 
                wf_submitEmployee_id + ",0," + workType + "," + workProcess_id + "," + 
                workTable_id + "," + workRecord_id + "," + workDeadLine + "," + workPressTime + 
                ",JSDB.FN_STRTODATE('" + now.toLocaleString() + "',''),0,0," + (
                Integer.parseInt(para[1]) + 1) + "," + initActivity + "," + initStepCount + ", " + workallowcancel + ",'" + submitOrg + "'," + isSubProcWork + ",'" + 
                standForUserId + "','" + standForUserName + "'," + pareProcActiId + "," + 
                pareStepCount + "," + pareTableId + "," + pareRecordId + "," + pareProcNextActiId + "," + domain_id + "," + emergence + "," + 
                curTransactType + ",'" + INITACTIVITYNAME + "')";
            } 
            msgVO = new MessagesVO();
            msgVO.setMessage_date_begin(now);
            msgVO.setMessage_date_end(new Date("2050/1/1"));
            msgVO.setMessage_send_UserId(Long.parseLong(wf_submitEmployee_id));
            msgVO.setMessage_send_UserName(workSubmitPerson);
            msgVO.setMessage_show(1);
            msgVO.setMessage_status(1);
            msgVO.setMessage_time(now);
            msgVO.setMessage_title(tmpTitle);
            msgVO.setMessage_toUserId(Long.parseLong(user[i]));
            msgVO.setMessage_type("jsflow");
            msgVO.setData_id(newWorkId);
            msgVO.setMessage_url("/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + newWorkId);
            msgList.add(msgVO);
            this.stmt.addBatch(sql);
          }  
      } 
      if (msgList.size() > 0) {
        this.stmt.executeBatch();
        this.stmt.clearBatch();
        MessagesBD messagesBD = new MessagesBD();
        messagesBD.messageArrayAdd(msgList);
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
  }
  
  public Integer insertPassroundDealWith(Map para) throws Exception {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      String activityId = "0", stepCount = "0", tableId = "0", recordId = "0", title = "";
      ResultSet rs = this.stmt.executeQuery("select initactivity, initstepcount,worktable_id,workrecord_id,worktitle,initactivityname from JSDB.JSF_WORK where wf_work_id=" + para.get("workId"));
      if (rs.next()) {
        activityId = rs.getString(1);
        stepCount = rs.getString(2);
        tableId = rs.getString(3);
        recordId = rs.getString(4);
        title = rs.getString(5);
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
            wf_dealwith_id + "," + tableId + "," + recordId + ",'" + para.get("activityName") + "'," + activityId + ",0,'-100',-100," + stepCount + ",1,0)");
      } 
      Date now = new Date();
      String commentField = "";
      if (para.get("commentField") != null)
        commentField = para.get("commentField").toString(); 
      String wf_dealwithcomment_id = getTableId();
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,COMMENTFIELD,COMMTYPE,domain_id,signcomment) VALUES (" + 
          wf_dealwithcomment_id + "," + para.get("userId") + ",'" + para.get("comment") + "','" + now.toLocaleString() + "'," + wf_dealwith_id + ",0,'','" + commentField + "',2,0,'" + para.get("signcomment") + "')";
      } else {
        tmpSql = "INSERT INTO JSDB.JSF_DEALWITHCOMMENT (WF_DEALWITHCOMMENT_ID,DEALWITHEMPLOYEE_ID,DEALWITHEMPLOYEECOMMENT,DEALWITHTIME,WF_DEALWITH_ID,STANDFORUSERID,STANDFORUSERNAME,COMMENTFIELD,COMMTYPE,domain_id,signcomment) VALUES (" + 
          wf_dealwithcomment_id + "," + para.get("userId") + ",'" + para.get("comment") + "',JSDB.FN_STRTODATE('" + now.toLocaleString() + "','')," + wf_dealwith_id + ",0,'','" + commentField + "',2,0,'" + para.get("signcomment") + "')";
      } 
      this.stmt.addBatch(tmpSql);
      if (databaseType.indexOf("mysql") >= 0) {
        this.stmt.addBatch("update JSDB.JSF_WORK set workstatus=102, worktitle='" + title.replaceAll("等待您的审阅", "审阅完毕") + "',WORKDONEWITHDATE='" + now.toLocaleString() + "' where wf_work_id=" + para.get("workId"));
      } else {
        this.stmt.addBatch("update JSDB.JSF_WORK set workstatus=102, worktitle='" + title.replaceAll("等待您的审阅", "审阅完毕") + "',WORKDONEWITHDATE=JSDB.FN_STRTODATE('" + now.toLocaleString() + "','') where wf_work_id=" + para.get("workId"));
      } 
      this.stmt.executeBatch();
      this.stmt.clearBatch();
    } catch (Exception e) {
      result = Integer.valueOf("-1");
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return result;
  }
  
  public void setWFOnlineUser(Map para) throws Exception {
    begin();
    try {
      StringBuffer sql = new StringBuffer("insert into jsf_onlineuser values (");
      sql.append(getTableId()).append(",").append(para.get("recordId")).append(",")
        .append(para.get("tableId")).append(",").append(para.get("processId")).append(",'")
        .append(para.get("userAccount")).append("','").append(para.get("userName")).append("','")
        .append(para.get("workId")).append("','").append(para.get("pageKey"));
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sql.append("','").append(para.get("onlinebegintime")).append("')");
      } else {
        sql.append("',to_date('").append(para.get("onlinebegintime")).append("','yyyy-mm-dd hh24:mi:ss'))");
      } 
      this.stmt.execute(sql.toString());
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.stmt.close();
      this.conn.close();
    } 
  }
  
  public String[] getWFOnlineUser(Map para) throws Exception {
    String[] ret = { "", "", "", "" };
    begin();
    ResultSet rs = null;
    try {
      String sql = "select userAccount,userName,workId,pageKey from jsf_onlineuser where recordId=" + para.get("recordId") + " and tableId=" + para.get("tableId") + " and processId=" + para.get("processId");
      rs = this.stmt.executeQuery(sql);
      if (rs.next()) {
        ret[0] = rs.getString("userAccount");
        ret[1] = rs.getString("userName");
        ret[2] = rs.getString("workId");
        ret[3] = rs.getString("pageKey");
      } 
      rs.close();
      if (!"".equals(ret[0])) {
        rs = this.stmt.executeQuery("select * from sec_onlineuser where user_account='" + ret[0] + "'");
        if (rs.next()) {
          rs.close();
        } else {
          rs.close();
          this.stmt.executeUpdate("delete from jsf_onlineuser where userAccount='" + ret[0] + "' and workId=" + ret[2]);
          ret[0] = "";
          ret[1] = "";
          ret[2] = "";
          ret[3] = "";
        } 
      } 
      end();
    } catch (Exception e) {
      end();
      e.printStackTrace();
      throw e;
    } 
    return ret;
  }
  
  public void delWFOnlineUser(Map para) throws Exception {
    begin();
    try {
      String sql = "delete from jsf_onlineuser where recordId=" + para.get("recordId") + " and tableId=" + para.get("tableId") + " and processId=" + para.get("processId") + " and workId=" + para.get("workId");
      this.stmt.execute(sql);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
  }
  
  public Map getBackToPerson(String tableId, String recordId, String stepCount, String initStepCount, String workId) throws Exception {
    Map<Object, Object> result = new HashMap<Object, Object>();
    begin();
    try {
      StringBuffer empId = new StringBuffer();
      StringBuffer empName = new StringBuffer();
      StringBuffer empWorkId = new StringBuffer();
      String sql = "";
      if (initStepCount.equals("0")) {
        sql = "SELECT DISTINCT WF_CUREMPLOYEE_ID,worksubmitperson,wf_work_id FROM JSDB.JSF_WORK WHERE WORKTABLE_ID=" + tableId + " AND WORKRECORD_ID=" + recordId + " AND workstartflag='1'";
      } else {
        sql = "SELECT DISTINCT WF_CUREMPLOYEE_ID,worksubmitperson,wf_work_id FROM JSDB.JSF_WORK WHERE WORKTABLE_ID=" + tableId + " AND WORKRECORD_ID=" + recordId + " AND INITSTEPCOUNT>=" + initStepCount + " and (workStatus<>2 and workStatus<>102 and workStatus<>-101) AND WORKSTEPCOUNT<=" + stepCount + " AND WF_WORK_ID<>" + workId;
      } 
      ResultSet rs = this.stmt.executeQuery(sql);
      while (rs.next()) {
        empId.append("$").append(rs.getString(1)).append("$");
        empName.append(rs.getString(2)).append(",");
        empWorkId.append(rs.getString(3)).append(",");
      } 
      rs.close();
      result.put("empId", empId.toString());
      result.put("empName", empName.toString());
      result.put("empWorkId", empWorkId);
      if (!initStepCount.equals("0")) {
        sql = "SELECT DISTINCT WF_CUREMPLOYEE_ID,worksubmitperson,wf_work_id FROM JSDB.JSF_WORK WHERE WORKTABLE_ID=" + tableId + " AND WORKRECORD_ID=" + recordId + " AND workstartflag='1'";
        rs = this.stmt.executeQuery(sql);
        while (rs.next()) {
          result.put("submitEmpId", rs.getString(1));
          result.put("submitEmpName", rs.getString(2));
          result.put("submitWorkId", rs.getString(3));
        } 
        rs.close();
      } 
      String title = "";
      result.put("title", title);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return result;
  }
  
  public List getLeaderByDutyLevelAndOrg(String userId, String dutyOper) throws Exception {
    begin();
    ArrayList<String[]> alist = new ArrayList();
    try {
      String sql;
      ResultSet rs = null;
      String orgIdString = "";
      rs = this.stmt.executeQuery("select a.orgidstring from JSDB.org_organization a,org_organization_user b where a.org_id=b.org_id and b.emp_id = " + userId);
      if (rs.next())
        orgIdString = rs.getString(1); 
      rs.close();
      String sidelineOrgSql = "";
      String[] tmp = orgIdString.split("\\$");
      orgIdString = "";
      for (int i = 0; i < tmp.length; i++) {
        if (!tmp[i].startsWith("_")) {
          orgIdString = String.valueOf(orgIdString) + tmp[i] + ",";
          if ("".equals(sidelineOrgSql)) {
            sidelineOrgSql = " emp.sidelineorg like '%*" + tmp[i] + "*%'";
          } else {
            sidelineOrgSql = String.valueOf(sidelineOrgSql) + " or emp.sidelineorg like '%*" + tmp[i] + "*%'";
          } 
        } 
      } 
      if (orgIdString.endsWith(","))
        orgIdString = orgIdString.substring(0, orgIdString.length() - 1); 
      if (!"".equals(sidelineOrgSql)) {
        sidelineOrgSql = "(c.org_id in(" + orgIdString + ") or a.emp_id in(select emp.emp_id from org_employee emp where " + sidelineOrgSql + "))";
      } else {
        sidelineOrgSql = "c.org_id in(" + orgIdString + ")";
      } 
      String dutyLevelOperate = "";
      String dutyLevel = "";
      if (dutyOper.indexOf("|") > 0) {
        dutyLevelOperate = dutyOper.split("\\|")[0];
        dutyLevel = dutyOper.split("\\|")[1];
      } 
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sql = "select a.emp_id,a.empname from org_employee a, oa_duty b,org_organization_user c where a.emp_id=c.emp_id and a.empduty=b.dutyName and a.userIsDeleted=0 and a.USERISACTIVE=1 and (a.userAccounts is not null and a.userAccounts <> '') and b.dutyLevel" + dutyLevelOperate + dutyLevel + " and " + sidelineOrgSql;
      } else if (databaseType.indexOf("oracle") >= 0) {
        sql = "select a.emp_id,a.empname from org_employee a, oa_duty b,org_organization_user c where a.emp_id=c.emp_id and a.empduty=b.dutyName and a.userIsDeleted=0 and a.USERISACTIVE=1 and (a.userAccounts is not null) and b.dutyLevel" + dutyLevelOperate + dutyLevel + " and " + sidelineOrgSql;
      } else {
        sql = "select a.emp_id,a.empname from org_employee a, oa_duty b,org_organization_user c where a.emp_id=c.emp_id and a.empduty=b.dutyName and a.userIsDeleted=0 and a.USERISACTIVE=1 and (a.userAccounts is not null and a.userAccounts <> '') and b.dutyLevel" + dutyLevelOperate + dutyLevel + " and " + sidelineOrgSql;
      } 
      rs = this.stmt.executeQuery(sql);
      while (rs.next()) {
        String[] emp = { "", "" };
        emp[0] = rs.getString(1);
        emp[1] = rs.getString(2);
        alist.add(emp);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public String getDealTipsByWorkId(String workId) throws Exception {
    String ret = "";
    begin();
    try {
      String sql = "select a.dealtips from JSF_WORK a where a.wf_work_id=" + workId;
      ResultSet rs = this.stmt.executeQuery(sql);
      if (rs.next())
        ret = rs.getString(1); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return ret;
  }
  
  public String getDealTipsByLogId(String logId) throws Exception {
    String ret = "";
    begin();
    try {
      String sql = "select a.receiveUserId from JSF_dealwithlog a where a.logid=" + logId;
      ResultSet rs = this.stmt.executeQuery(sql);
      if (rs.next())
        ret = rs.getString(1); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return ret;
  }
  
  public List getPressDealList() throws Exception {
    begin();
    ArrayList<String[]> alist = new ArrayList();
    try {
      ResultSet rs = null;
      Calendar now1 = Calendar.getInstance();
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        rs = this.stmt.executeQuery("select aaa.wf_curemployee_id,b.empName, aaa.workTitle,aaa.worksubmitPerson,aaa.worksubmittime,aaa.workFileType, aaa.domain_id,aaa.wf_work_id from JSF_WORK aaa,org_employee b where aaa.wf_curemployee_id=b.emp_Id and aaa.workstatus=0 and aaa.workDeadLine is not null and aaa.workDeadLine<>-1 and ('" + 
            
            now1.get(1) + "/" + (now1.get(2) + 1) + "/" + now1.get(5) + "' between aaa.workDeadlinePressDate and aaa.workDeadlineDate) and (worktask is null or aaa.workTask<>1)");
      } else {
        rs = this.stmt.executeQuery("select aaa.wf_curemployee_id,b.empName, aaa.workTitle,aaa.worksubmitPerson,aaa.worksubmittime,aaa.workFileType, aaa.domain_id,aaa.wf_work_id from JSDB.JSF_WORK aaa,JSDB.org_employee b where aaa.wf_curemployee_id=b.emp_Id and aaa.workstatus=0 and aaa.workDeadLine is not null and aaa.workDeadLine<>-1 and (JSDB.FN_STRTODATE('" + (

            
            new Date()).toLocaleString() + "','L') between aaa.workDeadlinePressDate and aaa.workDeadlineDate) and (worktask is null or aaa.workTask<>1)");
      } 
      while (rs.next()) {
        String[] tmp = { "", "", "", "", "", "", "", "" };
        tmp[0] = rs.getString(1);
        tmp[1] = rs.getString(2);
        tmp[2] = rs.getString(3);
        tmp[3] = rs.getString(4);
        tmp[4] = rs.getString(5);
        tmp[5] = rs.getString(6);
        tmp[6] = rs.getString(7);
        tmp[7] = (rs.getString(8) == null) ? "0" : rs.getString(8);
        alist.add(tmp);
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return alist;
  }
  
  public void setWorkTask(String workIds, String taskStatus) throws Exception {
    begin();
    try {
      String tmpSql = "update JSF_WORK set workTask=" + taskStatus + " where wf_work_id in(" + workIds + ")";
      this.stmt.execute(tmpSql);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
  }
  
  public void tranAutoReturn(String[] para) throws Exception {
    begin();
    Date now = new Date();
    try {
      String workFileType = "", workCurStep = "", workActivity = "", workSubmitPerson = "";
      String workSubmitTime = "", wf_submitEmployee_id = "", workType = "", workProcess_id = "";
      String workTable_id = "", workRecord_id = "", workDeadLine = "", workPressTime = "";
      String initActivity = "", initStepCount = "", workallowcancel = "", submitOrg = "", isSubProcWork = "", workStepCount = "";
      String pareProcActiId = "", pareStepCount = "", pareTableId = "", pareRecordId = "", pareProcNextActiId = "", curTransactType = "";
      String domain_id = "", emergence = "", INITACTIVITYNAME = "", standForUserId = "", standForUserName = "", isStandForWork = "", dealTips = "";
      Date workDeadlineDate = now, workDeadlinePressDate = now;
      String wf_curemployee_id = "";
      String sql = "select workFileType,workCurStep,workActivity,workSubmitPerson,workSubmitTime,wf_submitEmployee_id,workType,workProcess_id,workTable_id,workRecord_id,workDeadLine,workPressTime,initActivity,initStepCount,workallowcancel,submitOrg,isSubProcWork,wf_curemployee_id,standForUserId,standForUserName,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,domain_id,emergence,transactType,INITACTIVITYNAME,isStandForWork,dealTips,workDeadlineDate,workDeadlinePressDate,workStepCount from JSDB.JSF_WORK where wf_work_id=" + 






        
        para[0];
      ResultSet rs = this.stmt.executeQuery(sql);
      if (rs.next()) {
        workFileType = rs.getString("workFileType");
        workCurStep = rs.getString("workCurStep");
        workActivity = rs.getString("workActivity");
        workSubmitPerson = rs.getString("workSubmitPerson");
        workSubmitTime = rs.getString("workSubmitTime");
        if (workSubmitTime.indexOf(".") > 0)
          workSubmitTime = workSubmitTime.substring(0, workSubmitTime.indexOf(".")); 
        wf_submitEmployee_id = rs.getString("wf_submitEmployee_id");
        workType = rs.getString("workType");
        workProcess_id = rs.getString("workProcess_id");
        workTable_id = rs.getString("workTable_id");
        workRecord_id = rs.getString("workRecord_id");
        workDeadLine = rs.getString("workDeadLine");
        workPressTime = rs.getString("workPressTime");
        initActivity = rs.getString("initActivity");
        initStepCount = rs.getString("initStepCount");
        workallowcancel = rs.getString("workallowcancel");
        submitOrg = rs.getString("submitOrg");
        isSubProcWork = rs.getString("isSubProcWork");
        wf_curemployee_id = rs.getString("wf_curemployee_id");
        workStepCount = rs.getString("workStepCount");
        standForUserId = (rs.getString("standForUserId") == null) ? "0" : rs.getString("standForUserId");
        standForUserName = rs.getString("standForUserName");
        pareProcActiId = rs.getString("pareProcActiId");
        pareStepCount = rs.getString("pareStepCount");
        pareTableId = rs.getString("pareTableId");
        pareRecordId = rs.getString("pareRecordId");
        pareProcNextActiId = rs.getString("pareProcNextActiId");
        domain_id = rs.getString("domain_id");
        emergence = rs.getString("emergence");
        curTransactType = rs.getString("transactType");
        INITACTIVITYNAME = rs.getString("INITACTIVITYNAME");
        isStandForWork = rs.getString("isStandForWork");
        dealTips = (rs.getString("dealTips") == null) ? "" : rs.getString("dealTips");
        workDeadlineDate = rs.getTimestamp("workDeadlineDate");
        workDeadlinePressDate = rs.getTimestamp("workDeadlinePressDate");
      } 
      rs.close();
      String remindFieldValue = para[2];
      int k = (String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "等待您处理！").length() - 50;
      if (k > 0)
        remindFieldValue = remindFieldValue.substring(0, remindFieldValue.length() - k); 
      String tmpTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "等待您处理！";
      if (!para[4].equals(""))
        tmpTitle = para[4]; 
      String tranFromPersonId = para[8];
      int countTranByUser = 0;
      sql = "select count(wf_work_id) from JSDB.JSF_WORK where workProcess_id=" + workProcess_id + 
        " and workTable_id=" + workTable_id + " and workRecord_id=" + workRecord_id + " and workStepCount=" + workStepCount + 
        " and WORKDELETE <>1 and workstatus = 0 and tranType=1 and tranFromPersonId=" + tranFromPersonId;
      rs = this.stmt.executeQuery(sql);
      if (rs.next())
        countTranByUser = rs.getInt(1); 
      rs.close();
      if (countTranByUser == 1) {
        sql = "update JSDB.JSF_WORK set workstatus = 0, workTitle = '" + tmpTitle + "',WORKDELETE =0 where workProcess_id=" + workProcess_id + 
          " and workTable_id=" + workTable_id + " and workRecord_id=" + workRecord_id + " and workStepCount=" + workStepCount + 
          " and workstatus = 101 and wf_curemployee_id=" + tranFromPersonId;
        this.stmt.addBatch(sql);
      } 
      tmpTitle = String.valueOf(workSubmitPerson) + "的" + remindFieldValue + workFileType + "您已办理完毕！";
      if (!para[4].equals(""))
        tmpTitle = para[4]; 
      this.stmt.addBatch("update JSDB.JSF_WORK set WORKDELETE =1 where workstatus = 101 and worktable_id = " + workTable_id + 
          " and workrecord_id = " + workRecord_id + " and wf_curemployee_id = " + 
          wf_curemployee_id + " and workprocess_id=" + workProcess_id);
      sql = "update JSDB.JSF_WORK set workstatus = 101, workTitle = '" + tmpTitle + "', workallowcancel = " + workallowcancel;
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sql = String.valueOf(sql) + ",dealwithtime='" + now.toLocaleString() + "'";
      } else {
        sql = String.valueOf(sql) + ",dealwithtime=JSDB.FN_STRTODATE('" + now.toLocaleString() + "','') ";
      } 
      sql = String.valueOf(sql) + " where wf_work_id = " + para[0];
      this.stmt.addBatch(sql);
      this.stmt.executeBatch();
      this.stmt.clearBatch();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
  }
  
  public void setWorkViewedDate(String workId, WorkLogVO workLogVO) throws Exception {
    begin();
    String databaseType = SystemCommon.getDatabaseType();
    boolean hasViewed = true;
    try {
      StringBuffer sql = new StringBuffer("select WF_WORK_ID from JSF_WORK where wf_work_id =");
      sql.append(workId).append(" and WORKSTATUS=0 and workViewedDate is null");
      ResultSet rs = this.stmt.executeQuery(sql.toString());
      if (rs.next())
        hasViewed = false; 
      rs.close();
      if (!hasViewed) {
        sql = new StringBuffer("update JSF_WORK set workViewedDate=");
        if (databaseType.indexOf("mysql") >= 0) {
          sql.append("'").append((new Date()).toLocaleString())
            .append("' where wf_work_id =").append(workId)
            .append(" and WORKSTATUS=0 and workViewedDate is null");
        } else {
          sql.append("JSDB.FN_STRTODATE('").append((new Date()).toLocaleString())
            .append("','L') where wf_work_id =").append(workId)
            .append(" and WORKSTATUS=0 and workViewedDate is null");
        } 
        this.stmt.addBatch(sql.toString());
        this.stmt.addBatch("update sys_messages set message_status =0 where data_id =" + workId + " and message_toUserId=" + workLogVO.getSendUserId() + " and message_type ='jsflow'");
        this.stmt.executeBatch();
        this.stmt.clearBatch();
        setDealWithLogNoConn(workLogVO);
      } 
      end();
    } catch (Exception e) {
      end();
      e.printStackTrace();
      throw e;
    } 
  }
  
  public void setWorkViewedDate(String workId, WorkLogVO workLogVO, String lastStepCount) throws Exception {
    begin();
    String databaseType = SystemCommon.getDatabaseType();
    boolean hasViewed = true;
    try {
      StringBuffer sql = new StringBuffer("select WF_WORK_ID from JSF_WORK where wf_work_id =");
      sql.append(workId).append(" and WORKSTATUS=0 and workViewedDate is null");
      ResultSet rs = this.stmt.executeQuery(sql.toString());
      if (rs.next())
        hasViewed = false; 
      rs.close();
      if (!hasViewed) {
        sql = new StringBuffer("update JSF_WORK set  workReadMarker = 1,workViewedDate=");
        if (databaseType.indexOf("mysql") >= 0) {
          sql.append("'").append((new Date()).toLocaleString())
            .append("' where wf_work_id =").append(workId);
        } else {
          sql.append("JSDB.FN_STRTODATE('").append((new Date()).toLocaleString())
            .append("','L') where wf_work_id =").append(workId);
        } 
        this.stmt.addBatch(sql.toString());
        this.stmt.addBatch("update sys_messages set message_status =0 where data_id =" + workId + " and message_toUserId=" + workLogVO.getSendUserId() + " and message_type ='jsflow'");
        this.stmt.executeBatch();
        this.stmt.clearBatch();
        setDealWithLogNoConn(workLogVO);
      } 
      end();
    } catch (Exception e) {
      end();
      e.printStackTrace();
      throw e;
    } 
  }
  
  public String getWorkViewedDate(String processId, String tableId, String recordId, String empId, String stepcount) throws Exception {
    String ret = "";
    begin();
    SimpleDateFormat sf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    try {
      String sql = "select a.workViewedDate from jsf_work a where a.WORKPROCESS_ID=" + processId + " and a.WORKTABLE_ID=" + tableId + 
        " and a.WORKRECORD_ID=" + recordId + " and a.WF_CUREMPLOYEE_ID=" + empId + " and WORKSTEPCOUNT=" + stepcount;
      ResultSet rs = this.stmt.executeQuery(sql);
      if (rs.next() && rs.getString(1) != null)
        ret = sf.format(rs.getTimestamp(1)); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return ret;
  }
  
  public String getUserOrgId(String empId) throws Exception {
    String ret = "";
    begin();
    try {
      String sql = "select b.ORGIDSTRING from org_organization_user a,ORG_ORGANIZATION b where a.org_id=b.org_id and a.emp_id=" + empId;
      ResultSet rs = this.stmt.executeQuery(sql);
      if (rs.next())
        ret = rs.getString(1); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return ret;
  }
  
  public String getSubmitPerson(String workId, String tableId, String recordId) throws Exception {
    String ret = "";
    begin();
    try {
      String sql = "select distinct wf_submitemployee_id from jsf_work where wf_work_id=" + workId;
      ResultSet rs = this.stmt.executeQuery(sql);
      if (rs.next())
        ret = rs.getString(1); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return ret;
  }
  
  public List getAllRelationWork(String processId, String tableId, String recordId, String workId) throws Exception {
    begin();
    List<String[]> list = new ArrayList();
    SimpleDateFormat sf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    try {
      String PAREPROCACTIID = "", PARESTEPCOUNT = "", PARETABLEID = "", PARERECORDID = "", PAREPROCNEXTACTIID = "";
      String sql = "select PAREPROCACTIID,PARESTEPCOUNT,PARETABLEID,PARERECORDID,PAREPROCNEXTACTIID from JSF_WORK where WF_WORK_ID=" + workId + " and ISSUBPROCWORK=1";
      ResultSet rs = this.stmt.executeQuery(sql);
      if (rs.next()) {
        PAREPROCACTIID = rs.getString("PAREPROCACTIID");
        PARESTEPCOUNT = rs.getString("PARESTEPCOUNT");
        PARETABLEID = rs.getString("PARETABLEID");
        PARERECORDID = rs.getString("PARERECORDID");
        PAREPROCNEXTACTIID = rs.getString("PAREPROCNEXTACTIID");
      } 
      rs.close();
      if (PAREPROCACTIID != null && !"".equals(PAREPROCACTIID) && !"0".equals(PAREPROCACTIID)) {
        sql = "select workFileType,workMainLinkFile,workSubmitPerson,workCurStep,wf_submitEmployee_id,workType,workActivity,workTable_id,workRecord_id,workStatus,workSubmitTime,workProcess_id,workStepCount,WF_WORK_ID,SUBMITORG,WORKTITLE,WORKDONEWITHDATE from JSF_WORK  where WORKTABLE_ID=" + 

          
          PARETABLEID + " and WORKRECORD_ID=" + PARERECORDID + 
          " and (WORKSTATUS=1 or WORKSTATUS=100)";
        rs = this.stmt.executeQuery(sql);
        if (rs.next()) {
          String workFileType = "", workMainLinkFile = "", workSubmitPerson = "", workCurStep = "";
          String wf_submitEmployee_id = "", workType = "", workActivity = "", workTable_id = "";
          String workRecord_id = "", workStatus = "", workSubmitTime = "", workSubmitTimeStr = "";
          String workProcess_id = "", workStepCount = "", WF_WORK_ID = "", SUBMITORG = "";
          String WORKTITLE = "", url = "";
          workFileType = rs.getString("workFileType");
          workMainLinkFile = rs.getString("workMainLinkFile");
          workSubmitPerson = rs.getString("workSubmitPerson");
          workCurStep = rs.getString("workCurStep");
          wf_submitEmployee_id = rs.getString("wf_submitEmployee_id");
          workType = rs.getString("workType");
          workActivity = rs.getString("workActivity");
          workTable_id = rs.getString("workTable_id");
          workRecord_id = rs.getString("workRecord_id");
          workStatus = rs.getString("workStatus");
          workSubmitTime = rs.getString("workSubmitTime");
          workSubmitTimeStr = sf.format(rs.getTimestamp("workSubmitTime"));
          workProcess_id = rs.getString("workProcess_id");
          workStepCount = rs.getString("workStepCount");
          WF_WORK_ID = rs.getString("WF_WORK_ID");
          SUBMITORG = rs.getString("SUBMITORG");
          WORKTITLE = rs.getString("WORKTITLE");
          url = String.valueOf(workMainLinkFile) + 
            "&search=&workTitle=&activityName=" + workCurStep + 
            "&submitPersonId=" + wf_submitEmployee_id + "&submitPerson=" + workSubmitPerson + "&work=" + WF_WORK_ID + 
            "&workType=" + workType + "&activity=" + workActivity + "&table=" + workTable_id + 
            "&record=" + workRecord_id + "&processName=" + workFileType + "&workStatus=" + workStatus + 
            "&submitTime=" + workSubmitTime + "&processId=" + workProcess_id + "&stepCount=" + workStepCount;
          String[] tmp = new String[7];
          tmp[0] = SUBMITORG;
          tmp[1] = workSubmitTimeStr;
          tmp[2] = WORKTITLE.replaceAll("您的", "").replaceAll("正在办理中！", "").replaceAll("已办理完毕！", "");
          tmp[3] = (rs.getString("WORKDONEWITHDATE") != null) ? "办理完毕" : workCurStep;
          tmp[4] = workSubmitPerson;
          tmp[5] = url;
          tmp[6] = "0";
          list.add(tmp);
        } 
        rs.close();
      } 
      if (processId != null && !"".equals(processId)) {
        sql = "select workFileType,workMainLinkFile,workSubmitPerson,workCurStep,wf_submitEmployee_id,workType,workActivity,workTable_id,workRecord_id,workStatus,workSubmitTime,workProcess_id,workStepCount,WF_WORK_ID,SUBMITORG,WORKTITLE,WORKDONEWITHDATE from JSF_WORK  where ISSUBPROCWORK=1 and PARETABLEID=" + 

          
          tableId + " and PARERECORDID=" + recordId + 
          " and (WORKSTATUS=1 or WORKSTATUS=100)";
        rs = this.stmt.executeQuery(sql);
        while (rs.next()) {
          String workFileType = "", workMainLinkFile = "", workSubmitPerson = "", workCurStep = "";
          String wf_submitEmployee_id = "", workType = "", workActivity = "", workTable_id = "";
          String workRecord_id = "", workStatus = "", workSubmitTime = "", workSubmitTimeStr = "";
          String workProcess_id = "", workStepCount = "", WF_WORK_ID = "", SUBMITORG = "";
          String WORKTITLE = "", url = "";
          workFileType = rs.getString("workFileType");
          workMainLinkFile = rs.getString("workMainLinkFile");
          workSubmitPerson = rs.getString("workSubmitPerson");
          workCurStep = rs.getString("workCurStep");
          wf_submitEmployee_id = rs.getString("wf_submitEmployee_id");
          workType = rs.getString("workType");
          workActivity = rs.getString("workActivity");
          workTable_id = rs.getString("workTable_id");
          workRecord_id = rs.getString("workRecord_id");
          workStatus = rs.getString("workStatus");
          workSubmitTime = rs.getString("workSubmitTime");
          workSubmitTimeStr = sf.format(rs.getTimestamp("workSubmitTime"));
          workProcess_id = rs.getString("workProcess_id");
          workStepCount = rs.getString("workStepCount");
          WF_WORK_ID = rs.getString("WF_WORK_ID");
          SUBMITORG = rs.getString("SUBMITORG");
          WORKTITLE = rs.getString("WORKTITLE");
          url = String.valueOf(workMainLinkFile) + 
            "&search=&workTitle=&activityName=" + workCurStep + 
            "&submitPersonId=" + wf_submitEmployee_id + "&submitPerson=" + workSubmitPerson + "&work=" + WF_WORK_ID + 
            "&workType=" + workType + "&activity=" + workActivity + "&table=" + workTable_id + 
            "&record=" + workRecord_id + "&processName=" + workFileType + "&workStatus=" + workStatus + 
            "&submitTime=" + workSubmitTime + "&processId=" + workProcess_id + "&stepCount=" + workStepCount;
          String[] tmp = new String[7];
          tmp[0] = SUBMITORG;
          tmp[1] = workSubmitTimeStr;
          tmp[2] = WORKTITLE.replaceAll("您的", "").replaceAll("正在办理中！", "").replaceAll("已办理完毕！", "");
          tmp[3] = (rs.getString("WORKDONEWITHDATE") != null) ? "办理完毕" : workCurStep;
          tmp[4] = workSubmitPerson;
          tmp[5] = url;
          tmp[6] = "1";
          list.add(tmp);
        } 
        rs.close();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return list;
  }
  
  public String saveToDraft(String processId, String tableId, String recordId, String userId, String workTitle, String processName, String relProjectId) throws Exception {
    try {
      begin();
      StringBuffer sql = new StringBuffer("insert into jsf_p_draft (id,work_title,user_id,process_id,table_id,record_id,process_name,savetime,relproject_id) values (");
      sql.append(getTableId()).append(",'")
        .append(workTitle).append("',")
        .append(userId).append(",")
        .append(processId).append(",")
        .append(tableId).append(",")
        .append(recordId).append(",'")
        .append(processName).append("',");
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("oracle") >= 0) {
        sql.append("sysdate");
      } else if (databaseType.indexOf("mysql") >= 0) {
        sql.append("now()");
      } else {
        sql.append("now()");
      } 
      sql.append(",").append(relProjectId);
      sql.append(")");
      this.stmt.executeUpdate(sql.toString());
      end();
    } catch (Exception e) {
      end();
      e.printStackTrace();
      throw e;
    } 
    return null;
  }
  
  public String getUserSideLineOrgId(String empId) throws Exception {
    String ret = "";
    begin();
    try {
      String sql = "select sidelineorg from org_employee where emp_id=" + empId;
      ResultSet rs = this.stmt.executeQuery(sql);
      if (rs.next())
        ret = rs.getString(1); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
    return ret;
  }
  
  public void cleanWFOnlineUserInvalidate() throws Exception {
    begin();
    try {
      this.stmt.execute("delete from jsf_onlineuser where useraccount not in(select user_account from sec_onlineuser)");
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      end();
    } 
  }
  
  public String getSubProcWorkId(String processId, String tableId, String recordId, String activityId) throws Exception {
    String ret = "";
    try {
      begin();
      ResultSet rs = this.stmt.executeQuery("select wf_work_id from jsf_work where workstartflag=1 and pareprocactiid in (" + activityId + ") and paretableId=" + tableId + " and parerecordId=" + recordId);
      if (rs.next())
        ret = rs.getString(1); 
      rs.close();
      end();
    } catch (Exception e) {
      end();
      e.printStackTrace();
      throw e;
    } 
    return ret;
  }
  
  public String getSubProcWorkId(String processId, String tableId, String recordId) throws Exception {
    String ret = "";
    try {
      begin();
      ResultSet rs = this.stmt.executeQuery("select initactivity from jsf_work where workprocess_id=" + processId + " and worktable_id=" + tableId + " and workrecord_id=" + recordId);
      StringBuffer sb = new StringBuffer("0");
      while (rs.next()) {
        String temp = rs.getString(1);
        if (temp != null && !"".equals(temp))
          sb.append(",").append(temp); 
      } 
      rs.close();
      rs = this.stmt.executeQuery("select wf_work_id from jsf_work where workstartflag=1 and pareprocactiid in (" + sb.toString() + ") and paretableId=" + tableId + " and parerecordId=" + recordId);
      if (rs.next())
        ret = rs.getString(1); 
      rs.close();
      end();
    } catch (Exception e) {
      end();
      e.printStackTrace();
      throw e;
    } finally {}
    return ret;
  }
  
  public String deleteFlowTempData(String processId, String tableId, String recordId, String flag) {
    try {
      begin();
      StringBuffer strIndex = new StringBuffer("0");
      ResultSet rs = this.stmt.executeQuery("select wf_proceedactivity_id from jsf_p_activity where ttable_id=" + tableId + " and trecord_id=" + recordId);
      while (rs.next())
        strIndex.append(",").append(rs.getString(1)); 
      rs.close();
      this.stmt.executeUpdate("delete from jsf_p_readwritecontrol where wf_proceedactivity_id in (" + strIndex.toString() + ")");
      if ("2".equals(flag)) {
        StringBuffer transId = new StringBuffer("0");
        rs = this.stmt.executeQuery("select wf_proceedtransition_id from jsf_p_transition where wf_proceedactivity_id in(" + strIndex.toString() + ")");
        while (rs.next())
          transId.append(",").append(rs.getString(1)); 
        rs.close();
        this.stmt.executeUpdate("delete from jsf_p_tr where wf_proceedtransition_id in(" + transId.toString() + ")");
        this.stmt.executeUpdate("delete from jsf_p_transition where wf_proceedtransition_id in(" + transId.toString() + ")");
        this.stmt.executeUpdate("delete from jsf_p_activity where ttable_id=" + tableId + " and trecord_id=" + recordId);
      } 
      end();
    } catch (Exception ex) {
      end();
      ex.printStackTrace();
    } 
    return null;
  }
  
  public String saveFirstComp(WorkVO workVO) {
    String sql = "";
    String workId = "0";
    try {
      workId = getTableId();
      String creatorCancelLink = workVO.getCreatorCancelLink();
      creatorCancelLink = creatorCancelLink.replaceAll("workIdValue", workId);
      if (creatorCancelLink.indexOf("'") >= 0)
        creatorCancelLink = creatorCancelLink.replace('\'', '"'); 
      String selfTitle = "您的" + workVO.getRemindValue() + workVO.getFileType() + "正在办理中！";
      if (workVO.getDocTitle() != null && !workVO.getDocTitle().equals(""))
        selfTitle = workVO.getDocTitle(); 
      if (selfTitle.length() > 100)
        selfTitle = selfTitle.substring(0, 99); 
      Date now = new Date();
      String isSubProcWork = "0", pareProcActiId = "0", pareStepCount = "0";
      String pareTableId = "0", pareRecordId = "0", pareProcNextActiId = "0";
      if (workVO.getIsSubProcWork() != null)
        isSubProcWork = workVO.getIsSubProcWork(); 
      if (workVO.getPareProcActiId() != null)
        pareProcActiId = workVO.getPareProcActiId(); 
      if (workVO.getPareStepCount() != null)
        pareStepCount = workVO.getPareStepCount(); 
      if (workVO.getPareTableId() != null)
        pareTableId = workVO.getPareTableId(); 
      if (workVO.getPareRecordId() != null)
        pareRecordId = workVO.getPareRecordId(); 
      if (workVO.getPareProcNextActiId() != null)
        pareProcNextActiId = workVO.getPareProcNextActiId(); 
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sql = "insert into JSDB.JSF_WORK (wf_work_id, wf_curemployee_id, workstatus, workfiletype, workcurstep, worktitle, workleftlinkfile, workmainlinkfile, worklistcontrol, workactivity, worksubmitperson, worksubmittime, wf_submitemployee_id, workreadmarker, workprocess_id, worktable_id, workrecord_id, workdeadline, workpresstime, workcreatedate, workType, workstartflag, workAllowCancel, workStepCount, creatorCancelLink,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,DOMAIN_ID,relproject_id) values (" + 





          
          workId + "," + workVO.getSubmitEmployeeId() + ",1,'" + workVO.getFileType() + "','" + 
          workVO.getCurStep() + "','" + selfTitle + "','','" + workVO.getSelfMainLinkFile() + 
          "',1," + workVO.getActivity() + ",'" + workVO.getSubmitPerson() + "','" + 
          now.toLocaleString() + "'," + workVO.getSubmitEmployeeId() + 
          ",0," + workVO.getProcessId() + "," + workVO.getTableId() + "," + workVO.getRecordId() + 
          "," + workVO.getDeadLine() + "," + workVO.getPressTime() + ",'" + 
          now.toLocaleString() + "'," + workVO.getWorkType() + ",1," + 
          workVO.getAllowCancel() + ",0,'" + creatorCancelLink + "'," + isSubProcWork + "," + 
          pareProcActiId + "," + pareStepCount + "," + pareTableId + "," + pareRecordId + "," + 
          pareProcNextActiId + ",'" + workVO.getSubmitOrg() + "', " + workVO.getDomainId() + "," + workVO.getRelProjectId() + ")";
      } else {
        sql = "insert into JSDB.JSF_WORK (wf_work_id, wf_curemployee_id, workstatus, workfiletype, workcurstep, worktitle, workleftlinkfile, workmainlinkfile, worklistcontrol, workactivity, worksubmitperson, worksubmittime, wf_submitemployee_id, workreadmarker, workprocess_id, worktable_id, workrecord_id, workdeadline, workpresstime, workcreatedate, workType, workstartflag, workAllowCancel, workStepCount, creatorCancelLink,isSubProcWork,pareProcActiId,pareStepCount,pareTableId,pareRecordId,pareProcNextActiId,submitOrg,DOMAIN_ID,relproject_id) values (" + 





          
          workId + "," + workVO.getSubmitEmployeeId() + ",1,'" + workVO.getFileType() + "','" + 
          workVO.getCurStep() + "','" + selfTitle + "','','" + workVO.getSelfMainLinkFile() + 
          "',1," + workVO.getActivity() + ",'" + workVO.getSubmitPerson() + "',JSDB.FN_STRTODATE('" + 
          now.toLocaleString() + "','')," + workVO.getSubmitEmployeeId() + 
          ",0," + workVO.getProcessId() + "," + workVO.getTableId() + "," + workVO.getRecordId() + 
          "," + workVO.getDeadLine() + "," + workVO.getPressTime() + ",JSDB.FN_STRTODATE('" + 
          now.toLocaleString() + "','')," + workVO.getWorkType() + ",1," + 
          workVO.getAllowCancel() + ",0,'" + creatorCancelLink + "'," + isSubProcWork + "," + 
          pareProcActiId + "," + pareStepCount + "," + pareTableId + "," + pareRecordId + "," + 
          pareProcNextActiId + ",'" + workVO.getSubmitOrg() + "', " + workVO.getDomainId() + "," + workVO.getRelProjectId() + ")";
      } 
      this.stmt.executeUpdate(sql);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      end();
    } 
    return null;
  }
  
  public String setCurrentStep(String processId, String tableId, String recordId, String currentStep, String selUserId, String transactType) {
    try {
      int stepCount = 1;
      Date now = new Date();
      int index = currentStep.indexOf(",");
      String currentActivityId = currentStep.substring(0, index);
      currentStep = currentStep.substring(index + 1);
      String workFileType = "";
      String workTitle = "";
      String workListControl = "1";
      String workDeadLine = "-1";
      String workPressTime = "-1";
      String workMainLinkFile = "";
      String workActivityId = "";
      String workSubmitPerson = "";
      String workSubmitTime = "";
      String workSubmitEmployeeId = "";
      String submitOrg = "";
      String domainId = "0";
      String emergence = "0";
      String initActivityName = "";
      String relProjectId = "-1";
      Date workDeadlineDate = now, workDeadlinePressDate = now, processDeadlineDate = now;
      String databaseType = SystemCommon.getDatabaseType();
      begin();
      ResultSet rs = this.stmt.executeQuery("select max(workstepcount) from jsf_work where worktable_id=" + tableId + " and workrecord_id=" + recordId);
      if (rs.next())
        stepCount = rs.getInt(1) + 1; 
      rs.close();
      rs = this.stmt.executeQuery("select workfiletype,worktitle,workmainlinkfile,worksubmitperson,worksubmittime,wf_submitemployee_id,submitorg,domain_id,relproject_id,workDeadlinePressDate,processdeadlinedate from jsf_work where workstatus=1 and worktable_id=" + tableId + " and workrecord_id=" + recordId);
      if (rs.next()) {
        workFileType = rs.getString("workfiletype");
        workTitle = rs.getString("worktitle");
        workMainLinkFile = rs.getString("workmainlinkfile");
        workSubmitPerson = rs.getString("worksubmitperson");
        workSubmitTime = rs.getString("worksubmittime");
        workSubmitEmployeeId = rs.getString("wf_submitemployee_id");
        submitOrg = rs.getString("submitorg");
        domainId = rs.getString("domain_id");
        relProjectId = rs.getString("relproject_id");
        workDeadlinePressDate = (rs.getTimestamp("workDeadlinePressDate") == null) ? now : rs.getTimestamp("workDeadlinePressDate");
        processDeadlineDate = (rs.getTimestamp("processdeadlinedate") == null) ? null : rs.getTimestamp("processdeadlinedate");
      } 
      rs.close();
      String processDeadlineDateStr = "null";
      if (processDeadlineDate != null)
        if (databaseType.indexOf("oracle") >= 0) {
          processDeadlineDateStr = "JSDB.FN_STRTODATE('" + processDeadlineDate.toLocaleString() + "','L')";
        } else {
          processDeadlineDateStr = "'" + processDeadlineDate.toLocaleString() + "'";
        }  
      workTitle = workTitle.replaceAll("您的", String.valueOf(workSubmitPerson) + "的").replaceAll("正在办理中", "等待您处理");
      if (workSubmitTime != null && workSubmitTime.indexOf(".") > 0)
        workSubmitTime = workSubmitTime.substring(0, workSubmitTime.indexOf(".")); 
      this.stmt.executeUpdate("delete from jsf_work where workstatus=0 and worktable_id=" + tableId + " and workrecord_id=" + recordId);
      selUserId = selUserId.substring(1, selUserId.length() - 1);
      String[] currentUser = selUserId.split("\\$\\$");
      List<MessagesVO> msgList = new ArrayList();
      MessagesVO msgVO = new MessagesVO();
      for (int i = currentUser.length - 1; i >= 0; i--) {
        if ("3".equals(transactType) && i > 0) {
          workListControl = "0";
        } else {
          workListControl = "1";
        } 
        String curEmployeeId = currentUser[i];
        String workId = getTableId();
        StringBuffer sql = new StringBuffer(1024);
        sql.append("insert into jsf_work (");
        sql.append("wf_work_id,wf_curemployee_id,workstatus,workfiletype,workcurstep,")
          .append("worktitle,workleftlinkfile,workmainlinkfile,worklistcontrol,workactivity,")
          .append("worksubmitperson,worksubmittime,wf_submitemployee_id,workreadmarker,worktype,")
          .append("workprocess_id,worktable_id,workrecord_id,workdeadline,workpresstime,")
          .append("workcreatedate,workstepcount,")
          .append("isstandforwork,standforuserid,standforusername,initactivity,initstepcount,")
          .append("issubprocwork,pareprocactiid,parestepcount,paretableid,parerecordid,")
          .append("pareprocnextactiid,submitorg,domain_id,emergence,transacttype,")
          .append("initactivityname,dealtips,workdeadlinedate,workdeadlinepressdate,processdeadlinedate");
        sql.append(") values(");
        if (databaseType.indexOf("mysql") >= 0) {
          sql.append(String.valueOf(workId) + "," + curEmployeeId + ",0,'" + workFileType + "','" + currentStep + "','")
            .append(String.valueOf(workTitle) + "','','" + workMainLinkFile + "'," + workListControl + "," + currentActivityId + ",'")
            .append(String.valueOf(workSubmitPerson) + "','" + workSubmitTime + "'," + workSubmitEmployeeId + ",0,1,")
            .append(String.valueOf(processId) + "," + tableId + "," + recordId + "," + workDeadLine + "," + workPressTime + ",'")
            .append(String.valueOf(now.toLocaleString()) + "'," + stepCount + ",")
            .append("0,0,''," + currentActivityId + "," + stepCount + ",")
            .append("0,0,0,0,0,")
            .append("0,'" + submitOrg + "'," + domainId + ",'" + emergence + "','" + transactType + "','")
            .append(String.valueOf(initActivityName) + "','','" + now.toLocaleString() + "','" + now.toLocaleString() + "','" + now.toLocaleString() + "'");
          sql.append(")");
        } else {
          sql.append(String.valueOf(workId) + "," + curEmployeeId + ",0,'" + workFileType + "','" + currentStep + "','")
            .append(String.valueOf(workTitle) + "','','" + workMainLinkFile + "'," + workListControl + "," + currentActivityId + ",'")
            .append(String.valueOf(workSubmitPerson) + "',JSDB.FN_STRTODATE('" + workSubmitTime + "','')," + workSubmitEmployeeId + ",0,1,")
            .append(String.valueOf(processId) + "," + tableId + "," + recordId + "," + workDeadLine + "," + workPressTime + ",")
            .append("sysdate," + stepCount + ",")
            .append("0,0,''," + currentActivityId + "," + stepCount + ",")
            .append("0,0,0,0,0,")
            .append("0,'" + submitOrg + "'," + domainId + ",'" + emergence + "','" + transactType + "','")
            .append(String.valueOf(initActivityName) + "','',sysdate,sysdate,sysdate");
          sql.append(")");
        } 
        msgVO = new MessagesVO();
        msgVO.setMessage_date_begin(now);
        msgVO.setMessage_date_end(new Date("2050/1/1"));
        msgVO.setMessage_send_UserId(Long.parseLong(workSubmitEmployeeId));
        msgVO.setMessage_send_UserName(workSubmitPerson);
        msgVO.setMessage_show(1);
        msgVO.setMessage_status(1);
        msgVO.setMessage_time(now);
        msgVO.setMessage_title(workTitle);
        msgVO.setMessage_toUserId(Long.parseLong(curEmployeeId));
        msgVO.setMessage_type("jsflow");
        msgVO.setData_id(Integer.parseInt(workId));
        msgVO.setMessage_url("/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + Integer.parseInt(workId));
        msgList.add(msgVO);
        if (!"-1".equals(workPressTime)) {
          msgVO = new MessagesVO();
          msgVO.setMessage_date_begin(workDeadlinePressDate);
          msgVO.setMessage_date_end(new Date("2050/1/1"));
          msgVO.setMessage_send_UserId(Long.parseLong(workSubmitEmployeeId));
          msgVO.setMessage_send_UserName(workSubmitPerson);
          msgVO.setMessage_show(1);
          msgVO.setMessage_status(1);
          msgVO.setMessage_time(now);
          msgVO.setMessage_title(workTitle);
          msgVO.setMessage_toUserId(Long.parseLong(curEmployeeId));
          msgVO.setMessage_type("jsflow");
          msgVO.setData_id(Integer.parseInt(workId));
          msgVO.setMessage_url("/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + Integer.parseInt(workId));
          msgList.add(msgVO);
        } 
        this.stmt.executeUpdate(sql.toString());
      } 
      if (msgList.size() > 0) {
        MessagesBD messagesBD = new MessagesBD();
        messagesBD.messageArrayAdd(msgList);
      } 
      this.stmt.executeUpdate("update jsf_work set workcurstep='" + currentStep + "',workactivity=" + currentActivityId + " where worktable_id=" + tableId + " and workrecord_id=" + recordId);
      end();
    } catch (Exception ex) {
      end();
      ex.printStackTrace();
    } 
    return null;
  }
  
  private List getStandForUserByProcessAndOrgWithConn(String[] userId, String processId, String submitPersonId, Connection conn, Statement stmt1) throws Exception {
    ArrayList<String[]> alist = new ArrayList();
    try {
      String databaseType = SystemCommon.getDatabaseType();
      ResultSet rs = null;
      String orgIdString = "";
      rs = this.stmt.executeQuery("select b.ORGIDSTRING from org_organization_user a,ORG_ORGANIZATION b where a.org_id=b.org_id and a.emp_id=" + submitPersonId);
      if (rs.next())
        orgIdString = rs.getString(1); 
      for (int i = 0; i < userId.length; i++) {
        if (userId[i] != null && !"".equals(userId[i]) && !"null".equals(userId[i])) {
          String sql, tmp[] = { "", "", "", "" };
          tmp[0] = userId[i];
          rs = stmt1.executeQuery("select empName from JSDB.org_employee where emp_id = " + userId[i]);
          if (rs.next())
            tmp[1] = rs.getString(1); 
          rs.close();
          Calendar now = Calendar.getInstance();
          if (databaseType.indexOf("mysql") >= 0) {
            sql = "select proxyEmpId, proxyEmpName, proxyAllProcess, proxyProcess, proxyOrgId  from JSDB.oa_workproxy where emp_id = " + userId[i] + " and " + 
              "proxyState = 1 and '" + now.get(1) + "/" + (now.get(2) + 1) + "/" + now.get(5) + " " + now.get(10) + ":" + now.get(12) + "' between beginTime and endTime";
          } else {
            sql = "select proxyEmpId, proxyEmpName, proxyAllProcess, proxyProcess, proxyOrgId  from JSDB.oa_workproxy where emp_id = " + userId[i] + " and " + 
              "proxyState = 1 and JSDB.FN_STRTODATE('" + now.get(1) + "/" + (now.get(2) + 1) + "/" + now.get(5) + " " + now.get(10) + ":" + now.get(12) + "', 'L') between beginTime and endTime";
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
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } 
    return alist;
  }
  
  public List<String[]> getAllComment(String sql) {
    return (new DataSourceUtil()).getListQuery(sql, "");
  }
}
