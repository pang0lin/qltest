package com.js.thread;

import com.js.oa.eform.service.CustomFormBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.service.WorkFlowButtonBD;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.jsflow.util.FlowSendAndBack;
import com.js.oa.jsflow.util.ProcessStep;
import com.js.oa.jsflow.vo.ActivityVO;
import com.js.system.service.messages.RemindUtil;
import com.js.system.service.usermanager.UserBD;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.StringSplit;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class FlowHandleMask extends TimerTask {
  SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  
  String databaseType = SystemCommon.getDatabaseType();
  
  FlowSendAndBack flowSendAndBack = new FlowSendAndBack();
  
  Map<String, String[]> activityMap = null;
  
  Map<String, String> onlineUser = null;
  
  public void run() {
    long nowLong = (new Date()).getTime();
    this.activityMap = (Map)new ConcurrentHashMap<String, String>();
    this.onlineUser = new ConcurrentHashMap<String, String>();
    String handleSql = "SELECT WF_ACTIVITY_ID,activityName,pressType,deadLineTime,handleType,handleView,handleClass,handleMethod,WF_WORKFLOWPROCESS_ID,handleParam,ACTICOMMFIELD,isbranch,isdivide,isgather FROM jsf_activity where handleType<>0 and pressType=1 order by WF_WORKFLOWPROCESS_ID,WF_ACTIVITY_ID";
    List<String[]> workList = (List)new ArrayList<String>();
    List<String[]> remindList = (List)new ArrayList<String>();
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      rs = base.executeQuery(handleSql);
      while (rs.next()) {
        String[] handleInfo = new String[14];
        handleInfo[0] = rs.getString(1);
        handleInfo[1] = rs.getString(2);
        handleInfo[2] = rs.getString(3);
        handleInfo[3] = rs.getString(4);
        handleInfo[4] = rs.getString(5);
        handleInfo[5] = (rs.getString(6) == null || "null".equals(rs.getString(6)) || "".equals(rs.getString(6))) ? 
          "系统自动处理" : rs.getString(6);
        handleInfo[6] = (rs.getString(7) == null || "null".equals(rs.getString(7)) || "".equals(rs.getString(7))) ? 
          "" : rs.getString(7);
        handleInfo[7] = (rs.getString(8) == null || "null".equals(rs.getString(8)) || "".equals(rs.getString(8))) ? 
          "" : rs.getString(8);
        handleInfo[8] = (rs.getString(9) == null || "null".equals(rs.getString(9)) || "".equals(rs.getString(9))) ? 
          "" : rs.getString(9);
        handleInfo[9] = (rs.getString(10) == null || "null".equals(rs.getString(10)) || "".equals(rs.getString(10))) ? 
          "" : rs.getString(10);
        handleInfo[10] = rs.getString(11);
        handleInfo[11] = rs.getString(12);
        handleInfo[12] = rs.getString(13);
        handleInfo[13] = rs.getString(14);
        this.activityMap.put(rs.getString(1), handleInfo);
      } 
      rs.close();
      String onlineSql = "SELECT recordId,workId FROM jsf_onlineuser";
      rs = base.executeQuery(onlineSql);
      while (rs.next())
        this.onlineUser.put(rs.getString(1), rs.getString(2)); 
      rs.close();
      String workSql = "SELECT wf_work_id,WF_CUREMPLOYEE_ID,workfileType,workTitle,WORKPROCESS_ID,WORKTABLE_ID,WORKRECORD_ID,workdeadlineDate,workDeadlinePressDate,WORKSTEPCOUNT,INITSTEPCOUNT,initactivity,INITACTIVITYNAME,WORKSUBMITTIME,WF_SUBMITEMPLOYEE_ID,standForUserName,WORKSUBMITPERSON,WORKMAINLINKFILE,isStandForWork,standForUserId,standForUserName,handleInfo,work_hangup FROM jsf_work t INNER JOIN (SELECT MAX(WF_WORK_ID) AS id FROM jsf_work WHERE workstatus=0 and (handleInfo=0 or handleInfo=1) AND worktable_id<>-1 AND (workdeadline<>-1 AND WORKPRESSTIME<>-1) GROUP BY WORKRECORD_ID, INITACTIVITY) t1 ON t.WF_WORK_ID=t1.id ORDER BY WORKRECORD_ID desc,wf_work_id desc";
      rs = base.executeQuery(workSql);
      String flagStr = "";
      while (rs.next()) {
        Timestamp workDeadlineDateObj = rs.getTimestamp("workdeadlineDate");
        long workDeadLine = 0L;
        if (workDeadlineDateObj != null) {
          workDeadLine = workDeadlineDateObj.getTime();
          long workDeadlinePress = rs.getTimestamp("workDeadlinePressDate").getTime();
          int handleInfo = rs.getInt("handleInfo");
          if (nowLong >= workDeadLine) {
            String[] work = new String[20];
            work[0] = (rs.getString("WORKTABLE_ID") == null) ? "" : rs.getString("WORKTABLE_ID");
            work[1] = (rs.getString("WORKRECORD_ID") == null) ? "" : rs.getString("WORKRECORD_ID");
            work[2] = (rs.getString("initactivity") == null) ? "" : rs.getString("initactivity");
            work[3] = (rs.getString("WORKPROCESS_ID") == null) ? "" : rs.getString("WORKPROCESS_ID");
            work[4] = (rs.getString("wf_work_id") == null) ? "" : rs.getString("wf_work_id");
            work[5] = (rs.getString("WF_CUREMPLOYEE_ID") == null) ? "" : rs.getString("WF_CUREMPLOYEE_ID");
            work[6] = (rs.getString("workTitle") == null) ? "" : rs.getString("workTitle");
            work[7] = (rs.getString("INITSTEPCOUNT") == null) ? "" : rs.getString("INITSTEPCOUNT");
            work[8] = (rs.getString("INITACTIVITYNAME") == null) ? "" : rs.getString("INITACTIVITYNAME");
            work[9] = (rs.getString("WORKSTEPCOUNT") == null) ? "" : rs.getString("WORKSTEPCOUNT");
            work[10] = (rs.getString("WF_SUBMITEMPLOYEE_ID") == null) ? "" : rs.getString("WF_SUBMITEMPLOYEE_ID");
            work[11] = (rs.getString("WORKSUBMITTIME") == null) ? "" : rs.getString("WORKSUBMITTIME");
            work[12] = (rs.getString("WORKSUBMITPERSON") == null) ? "" : rs.getString("WORKSUBMITPERSON");
            work[13] = (rs.getString("workfileType") == null) ? "" : rs.getString("workfileType");
            work[14] = (rs.getString("WORKMAINLINKFILE") == null) ? "" : rs.getString("WORKMAINLINKFILE");
            work[15] = (rs.getString("isStandForWork") == null) ? "" : rs.getString("isStandForWork");
            work[16] = (rs.getString("standForUserId") == null) ? "" : rs.getString("standForUserId");
            work[17] = (rs.getString("standForUserName") == null) ? "" : rs.getString("standForUserName");
            if (flagStr.contains("," + rs.getString("WORKRECORD_ID") + ",")) {
              work[18] = "0";
            } else {
              flagStr = String.valueOf(flagStr) + "," + rs.getString("WORKRECORD_ID") + ",";
              work[18] = "1";
            } 
            work[19] = (rs.getString("work_hangup") == null) ? "" : rs.getString("work_hangup");
            workList.add(work);
            continue;
          } 
          if (nowLong >= workDeadlinePress && nowLong < workDeadLine && handleInfo == 0) {
            String[] remind = new String[4];
            remind[0] = (new StringBuilder(String.valueOf(rs.getString("wf_work_id")))).toString();
            remind[1] = (new StringBuilder(String.valueOf(rs.getString("WF_CUREMPLOYEE_ID")))).toString();
            remind[2] = (new StringBuilder(String.valueOf(handleInfo))).toString();
            remind[3] = (new StringBuilder(String.valueOf(rs.getString("workTitle")))).toString();
            remindList.add(remind);
          } 
        } 
      } 
      rs.close();
      for (int j = 0; j < remindList.size(); j++) {
        String[] remind = remindList.get(j);
        base.addBatch(remindPerson(remind, remind[3], "0", remind[1], 0, remind[0]));
      } 
      base.executeBatch();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    List<String> updateRemind = new ArrayList<String>();
    int i;
    for (i = workList.size() - 1; i >= 0; i--) {
      String[] work = workList.get(i);
      if (this.activityMap.get(work[2]) != null) {
        String[] handleInfo = this.activityMap.get(work[2]);
        if ("1".equals(handleInfo[4])) {
          if (this.onlineUser.get(work[1]) == null)
            sendFlow(work, handleInfo); 
        } else if ("2".equals(handleInfo[4])) {
          if (this.onlineUser.get(work[1]) == null)
            backFlow(work, handleInfo); 
        } else if ("3".equals(handleInfo[4])) {
          if (this.onlineUser.get(work[1]) == null)
            updateRemind.add(remindPerson(work, handleInfo[5], "0", work[5], 1, work[4])); 
        } else if ("4".equals(handleInfo[4])) {
          if (this.onlineUser.get(work[1]) == null)
            customFlow(work, handleInfo); 
        } else if ("5".equals(handleInfo[4])) {
          if (this.onlineUser.get(work[1]) == null) {
            String[][] empList = getFrontActivityOper(work[0], work[1], work[3], Integer.valueOf(work[7]).intValue());
            for (int t = 0; t < empList.length; t++)
              updateRemind.add(remindPerson(work, handleInfo[5], "1", empList[t][0], 1, work[4])); 
          } 
        } else if ("6".equals(handleInfo[4]) && 
          this.onlineUser.get(work[1]) == null) {
          String[][] empList = getFrontActivityOper(work[0], work[1], work[3], 1);
          for (int t = 0; t < empList.length; t++)
            updateRemind.add(remindPerson(work, handleInfo[5], "1", empList[t][0], 1, work[4])); 
        } 
      } 
    } 
    try {
      base.begin();
      for (i = 0; i < updateRemind.size(); i++)
        base.addBatch(updateRemind.get(i)); 
      base.executeBatch();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
  }
  
  public void sendFlow(String[] workInfo, String[] handleInfo) {
    WorkFlowBD workFlowBD = new WorkFlowBD();
    WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
    try {
      Map<String, String> para = new ConcurrentHashMap<String, String>();
      String tableId = workInfo[0];
      String recordId = workInfo[1];
      String activityId = workInfo[2];
      String processId = workInfo[3];
      String[] empIdInfo = getEmpInfoByEmpId(workInfo[5]);
      String flagStr = "";
      if ("1".equals(workInfo[18])) {
        if ("1".equals(handleInfo[12])) {
          flagStr = "branch";
        } else {
          String stepType = workFlowBD.getActivityType(activityId, tableId, recordId);
          String activityClass = workFlowBD.getNextActivityClass(activityId, tableId, recordId, "");
          Map<String, String> requestMap = new ConcurrentHashMap<String, String>();
          requestMap.put("moduleId", workFlowCommonBD.getModuleId(tableId));
          requestMap.put("processId", processId);
          requestMap.put("tranFromPersonId", "-1");
          requestMap.put("standForUserId", "0");
          requestMap.put("curActivityId", activityId);
          requestMap.put("userId", workInfo[5]);
          requestMap.put("dutyLevel", empIdInfo[1]);
          ActivityVO vo = getNextActivityVO(stepType, activityClass, requestMap, tableId, recordId, activityId);
          if (vo.getBeginEnd() != 2) {
            String[] nextUser = workFlowBD.getProcActiUser(tableId, recordId, (new StringBuilder(String.valueOf(vo.getId()))).toString());
            int participantType = Integer.parseInt(nextUser[0]);
            if (",0,2,3,4,5,7,10,11,".contains("," + participantType + ",")) {
              para.put("mainNextActivityId", (new StringBuilder(String.valueOf(vo.getId()))).toString());
              para.put("mainNextActivityName", (vo.getName() == null) ? "" : vo.getName());
              para.put("mainAllowStandFor", (new StringBuilder(String.valueOf(vo.getAllowStandFor()))).toString());
              para.put("mainAllowCancel", (new StringBuilder(String.valueOf(vo.getAllowcancel()))).toString());
              para.put("mainPressType", (new StringBuilder(String.valueOf(vo.getPressType()))).toString());
              para.put("mainDeadLineTime", (new StringBuilder(String.valueOf(vo.getDeadlineTime()))).toString());
              para.put("mainPressMotionTime", (new StringBuilder(String.valueOf(vo.getPressMotionTime()))).toString());
              String[] selectUser = getSelectUser(workInfo, participantType, nextUser, empIdInfo);
              para.put("operId", selectUser[0]);
              para.put("operName", selectUser[1]);
              if (selectUser[0].length() > 0)
                flagStr = "send"; 
            } 
          } else {
            String curTransactType = (new WorkFlowButtonBD()).getCurTransactTypeByWorkId(workInfo[4]);
            para.put("curTransactType", curTransactType);
            flagStr = "complete";
          } 
        } 
      } else {
        para.put("mainNextActivityId", "0");
        para.put("mainNextActivityName", "");
        para.put("mainAllowStandFor", "1");
        para.put("mainAllowCancel", "0");
        para.put("mainPressType", "0");
        para.put("mainDeadLineTime", "0");
        para.put("mainPressMotionTime", "0");
        flagStr = "end";
      } 
      if (!"".equals(flagStr)) {
        para.put("userId", workInfo[5]);
        para.put("userName", empIdInfo[0]);
        para.put("orgId", empIdInfo[2]);
        para.put("orgIdString", empIdInfo[3]);
        para.put("isStandForWork", workInfo[15]);
        para.put("standForUserId", workInfo[16]);
        para.put("standForUserName", workInfo[17]);
        para.put("submitPerson", workInfo[12]);
        para.put("submitPersonId", workInfo[10]);
        para.put("include_comment", handleInfo[5]);
        para.put("curActivityId", activityId);
        para.put("curActivityName", workInfo[8]);
        para.put("processId", processId);
        para.put("processName", workInfo[13]);
        para.put("tableId", tableId);
        para.put("recordId", recordId);
        para.put("workId", workInfo[4]);
        para.put("stepCount", workInfo[9]);
        para.put("activityClass", "1");
        para.put("submitTime", workInfo[11]);
        para.put("mainUserType", "100");
        para.put("subProcWorkId", "");
        para.put("modiCommentId", "");
        para.put("include_commField", handleInfo[10]);
        para.put("userScope", "");
        para.put("scopeId", "");
        para.put("subProcType", "-1");
        para.put("mainLinkFile", workInfo[14]);
        para.put("approveMode", "1");
        para.put("dealTips", "");
        para.put("mainNeedPassRound", "1");
        para.put("passRoundName", "");
        if ("send".equals(flagStr)) {
          this.flowSendAndBack.flowSend(para);
        } else if ("end".equals(flagStr)) {
          this.flowSendAndBack.endFlow(para);
        } else if ("complete".equals(flagStr)) {
          this.flowSendAndBack.completeFlow(para);
        } 
      } 
      para.clear();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void backFlow(String[] workInfo, String[] handleInfo) {
    String[] empIdInfo = getEmpInfoByEmpId(workInfo[5]);
    try {
      String tableId = workInfo[0];
      String recordId = workInfo[1];
      String activityId = workInfo[2];
      String processId = workInfo[3];
      Map<String, String> para = new ConcurrentHashMap<String, String>();
      para.put("userId", workInfo[5]);
      para.put("userName", empIdInfo[0]);
      para.put("orgId", empIdInfo[2]);
      para.put("include_comment", handleInfo[5]);
      para.put("curActivityId", activityId);
      para.put("curActivityName", workInfo[8]);
      para.put("processId", processId);
      para.put("processName", workInfo[13]);
      para.put("tableId", tableId);
      para.put("recordId", recordId);
      para.put("workId", workInfo[4]);
      para.put("stepCount", workInfo[9]);
      para.put("submitPerson", workInfo[12]);
      para.put("submitTime", workInfo[11]);
      para.put("isStandForWork", workInfo[15]);
      para.put("standForUserId", workInfo[16]);
      para.put("backToActivityId", "");
      para.put("backToActivityName", "");
      para.put("backToUserId", "");
      para.put("backToUserName", "");
      para.put("mainLinkFile", workInfo[14]);
      para.put("backToStep", "");
      para.put("userScope", "");
      para.put("scopeId", "");
      para.put("title", workInfo[6].replace("等待您处理！", "").replace(String.valueOf(empIdInfo[0]) + "的", ""));
      String step = workInfo[9];
      String backToAllInfo = "0";
      if (!"1".equals(step) && !"0".equals(step)) {
        WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
        List<String[]> flowedActivityList = workFlowButtonBD.getFlowedActivity(tableId, recordId, step);
        String[] tmp = flowedActivityList.get(0);
        backToAllInfo = String.valueOf(tmp[0]) + "," + tmp[2] + "," + tmp[1] + "," + tmp[3] + "," + tmp[4];
      } 
      para.put("backToAllInfo", backToAllInfo);
      this.flowSendAndBack.flowBack(para);
      para.clear();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public String remindPerson(String[] workInfo, String title, String status, String userIds, int handleFlag, String workId) {
    String curEmpId = "";
    if (handleFlag == 0) {
      curEmpId = workInfo[1];
    } else {
      curEmpId = workInfo[5];
    } 
    if (title.indexOf("@$@当前办理人名称@$@") >= 0) {
      String[] empIdInfo = getEmpInfoByEmpId(curEmpId);
      title = title.replace("@$@当前办理人名称@$@", empIdInfo[0]);
    } 
    try {
      String url = "/jsoa/jsflow/item/jump_dealwith.jsp?status=" + status + "&workId=" + workId;
      if (!"0".equals(status)) {
        String processStatus = workInfo[19];
        url = "/jsoa/jsflow/workflow_listInfo.jsp?workStatus=" + status + "&&processStatus==" + processStatus + "&fromDossierData=y&curStatus=0&record=" + workInfo[1];
      } 
      RemindUtil.sendMessageToUsers2(title, url, userIds, "jsflow", new Date(), 
          this.dateTimeFormat.parse("2050-01-01 00:00:00"), "系统提醒", Long.valueOf(workId), 1);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return "update jsf_work set handleInfo=" + (handleFlag + 1) + " where wf_work_id=" + workId;
  }
  
  public void customFlow(String[] workInfo, String[] handleInfo) {
    if (handleInfo[9] == null || "".equals(handleInfo[9]) || "null".equals(handleInfo[9]))
      handleInfo[9] = "@$@workId@$@"; 
    String[] param = handleInfo[9].split(",");
    try {
      Class<?> cls = Class.forName("com.js.oa.form." + handleInfo[6]);
      Constructor<?> ct = cls.getConstructor(null);
      Object result = null;
      Class[] arg = new Class[param.length];
      Object[] arglist = new Object[param.length];
      for (int i = 0; i < param.length; i++) {
        arg[i] = String.class;
        if (param[i].equals("@$@tableId@$@")) {
          arglist[i] = workInfo[0];
        } else if (param[i].equals("@$@recordId@$@")) {
          arglist[i] = workInfo[1];
        } else if (param[i].equals("@$@activityId@$@")) {
          arglist[i] = workInfo[2];
        } else if (param[i].equals("@$@processId@$@")) {
          arglist[i] = workInfo[3];
        } else if (param[i].equals("@$@workId@$@")) {
          arglist[i] = workInfo[4];
        } 
      } 
      Method meth = cls.getMethod(handleInfo[7], arg);
      Object retobj = ct.newInstance(null);
      result = meth.invoke(retobj, arglist);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public String[] getFieldValue(String fieldName, String tableName, String recordId, String tableId) {
    String[] fieldValue = { "", "" };
    String sql = "select " + fieldName + " from " + tableName + " where " + tableName + "_id=" + recordId;
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      if (rs.next())
        fieldValue[0] = rs.getString(1); 
      rs.close();
      sql = "SELECT field_show FROM tfield WHERE field_table=" + tableId + " AND field_name='" + fieldName + "'";
      rs = base.executeQuery(sql);
      if (rs.next())
        fieldValue[1] = rs.getString(1); 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return fieldValue;
  }
  
  public ActivityVO getNextActivityVO(String stepType, String activityClass, Map<String, String> requestMap, String tableId, String recordId, String activityId) {
    ActivityVO activityVO = null;
    List<ActivityVO> activityList = new ArrayList<ActivityVO>();
    if (!activityClass.equals("0")) {
      activityVO = (new ProcessStep()).getProceedNextActi(activityClass, tableId, recordId, requestMap, null);
      if (activityVO != null) {
        activityVO.setBeginEnd(activityVO.getActivityBeginEnd());
        activityList.add(activityVO);
      } 
    } else if (stepType.equals("0")) {
      activityVO = (new ProcessStep()).getProceedNextActi(activityId, tableId, recordId, requestMap, null);
      if (activityVO != null) {
        activityVO.setBeginEnd(activityVO.getActivityBeginEnd());
        activityList.add(activityVO);
      } 
    } else {
      activityList = (new WorkFlowBD()).getAllNextActivity(tableId, recordId, activityId);
    } 
    ActivityVO vo = null;
    if (activityList.size() >= 0)
      if (activityList.size() == 1) {
        vo = activityList.get(0);
      } else {
        for (int i = 0; i < activityList.size(); i++) {
          ActivityVO avo = activityList.get(i);
          if (avo.getDefaultActivity().equals((new StringBuilder(String.valueOf(avo.getId()))).toString()))
            vo = avo; 
        } 
        if (vo == null)
          vo = activityList.get(0); 
      }  
    return vo;
  }
  
  public String[][] getEmpInfoByOrgId(String orgIds) {
    String[][] empInfo = (String[][])null;
    String sql = "SELECT e.emp_id,e.empName FROM org_employee e JOIN org_organization_user u ON e.emp_id=u.emp_id JOIN org_organization o ON u.org_id=o.org_id WHERE 1<>1  (whereInfo) ORDER BY o.orgIdString";
    String[] orgId = orgIds.split(",");
    String whereInfo = "";
    for (int i = 0; i < orgId.length; i++)
      whereInfo = String.valueOf(whereInfo) + " or o.orgIdString like '%$" + orgId[i] + "$%'"; 
    sql = sql.replace("(whereInfo)", whereInfo);
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      empInfo = base.getArrayQuery(sql);
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return empInfo;
  }
  
  public String[][] getOrgLeader(String empIds) {
    String[][] leader = (String[][])null;
    if (empIds.endsWith(","))
      empIds = empIds.substring(0, empIds.length() - 1); 
    String sql = "SELECT DISTINCT o.ORGMANAGEREMPID,o.ORGMANAGEREMPNAME FROM org_organization o JOIN org_organization_user u ON o.ORG_ID=u.ORG_ID WHERE u.EMP_ID IN (" + 
      empIds + ");";
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      leader = base.getArrayQuery(sql);
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return leader;
  }
  
  public String[] getEmpInfoByEmpId(String empId) {
    String[] empInfo = new String[4];
    String sql = "SELECT e.EMPNAME,e.EMPDUTYLEVEL,o.ORG_ID,o.ORGIDSTRING FROM org_employee e JOIN org_organization_user u ON e.EMP_ID=u.emp_id JOIN org_organization o ON u.ORG_ID=o.ORG_ID WHERE e.EMP_ID=" + 
      
      empId;
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      if (rs.next()) {
        empInfo[0] = (rs.getString(1) == null) ? "" : rs.getString(1);
        empInfo[1] = (rs.getString(2) == null) ? "" : rs.getString(2);
        empInfo[2] = (rs.getString(3) == null) ? "" : rs.getString(3);
        empInfo[3] = StringSplit.splitOrgIdString(rs.getString(4), "$", "_");
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return empInfo;
  }
  
  public String[][] getFrontActivityOper(String tableId, String recordId, String processId, int step) {
    String[][] empList = (String[][])null;
    String sql = "SELECT DISTINCT b.emp_Id,b.empName,a.wf_work_id FROM JSF_WORK a JOIN org_employee b ON a.wf_curemployee_id=b.emp_Id WHERE WORKPROCESS_ID=" + 
      processId + " AND a.worktable_id=" + tableId + " AND a.workrecord_id=" + recordId + 
      " AND workstepcount=" + (step - 1);
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      empList = base.getArrayQuery(sql);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return empList;
  }
  
  public String[] getSelectUser(String[] workInfo, int participantType, String[] nextUser, String[] empIdInfo) {
    int i;
    String participantUserField, tableName, fieldValue[], orgId, step, empInfo[][], userIds, leader[][];
    int j;
    String[][] empList;
    int k;
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    WorkFlowBD workFlowBD = new WorkFlowBD();
    List<Object[]> candidate = null;
    List<Object[]> leaderList = null;
    String selectUser = "";
    String selectUserName = "";
    switch (participantType) {
      case 0:
        leaderList = workFlowBD.getLeaderList(workInfo[10]);
        for (i = 0; i < leaderList.size(); i++) {
          Object[] tmp = leaderList.get(i);
          selectUser = String.valueOf(selectUser) + tmp[0] + ",";
          selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
        } 
        if (selectUser.indexOf(",") != -1) {
          selectUser = selectUser.substring(0, selectUser.length() - 1);
          selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
        } 
        break;
      case 2:
        candidate = workFlowBD.getCandidateSeq(nextUser[1], nextUser[3]);
        for (i = 0; i < candidate.size(); i++) {
          Object[] tmp = candidate.get(i);
          selectUser = String.valueOf(selectUser) + tmp[0] + ",";
          selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
        } 
        if (selectUser.indexOf(",") != -1) {
          selectUser = selectUser.substring(0, selectUser.length() - 1);
          selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
        } 
        if (selectUser.length() > 0 && selectUser.indexOf(",") > 0) {
          selectUser = selectUser.substring(0, selectUser.indexOf(","));
          selectUserName = selectUserName.substring(0, selectUserName.indexOf(","));
        } 
        break;
      case 3:
        candidate = workFlowBD.getCandidateSeq(nextUser[1], nextUser[3]);
        for (i = 0; i < candidate.size(); i++) {
          Object[] tmp = candidate.get(i);
          selectUser = String.valueOf(selectUser) + tmp[0] + ",";
          selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
        } 
        if (selectUser.indexOf(",") != -1) {
          selectUser = selectUser.substring(0, selectUser.length() - 1);
          selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
        } 
        break;
      case 4:
        participantUserField = workFlowButtonBD.getFieldInfoByFieldId(nextUser[4]);
        tableName = (new CustomFormBD()).getTable(workInfo[0]);
        fieldValue = getFieldValue(participantUserField, tableName, workInfo[1], workInfo[0]);
        if (fieldValue[0].indexOf(";") > 0) {
          String[] fieldInfo = fieldValue[0].split(";");
          if ("212".equals(fieldValue[1]) || "214".equals(fieldValue[1])) {
            String orgIds = fieldInfo[1].replaceAll("**", ",").replace("*", "");
            String[][] arrayOfString = getEmpInfoByOrgId(orgIds);
            selectUser = arrayOfString[0][0];
            selectUserName = arrayOfString[0][1];
            break;
          } 
          selectUser = fieldInfo[1].replaceAll("\\$\\$", ",").replace("\\$", "");
          selectUserName = fieldInfo[0];
          break;
        } 
        if ("201".equals(fieldValue[1])) {
          selectUser = fieldValue[0];
          selectUserName = fieldValue[0];
          break;
        } 
        orgId = fieldValue[0];
        empInfo = getEmpInfoByOrgId(orgId);
        selectUser = empInfo[0][0];
        selectUserName = empInfo[0][1];
        break;
      case 5:
        selectUser = workInfo[10];
        selectUserName = empIdInfo[0];
        break;
      case 7:
        step = workInfo[9];
        if ("1".equals(step)) {
          leaderList = workFlowBD.getLeaderList(workInfo[10]);
          for (int m = 0; m < leaderList.size(); m++) {
            Object[] tmp = leaderList.get(m);
            selectUser = String.valueOf(selectUser) + tmp[0] + ",";
            selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
          } 
        } else {
          String[][] arrayOfString = getFrontActivityOper(workInfo[0], workInfo[1], workInfo[3], Integer.valueOf(workInfo[7]).intValue());
          for (int m = 0; m < arrayOfString.length; m++) {
            leaderList = workFlowBD.getLeaderList((new StringBuilder(String.valueOf(arrayOfString[m][0]))).toString());
            for (int t = 0; t < leaderList.size(); t++) {
              Object[] tmp = leaderList.get(t);
              selectUser = String.valueOf(selectUser) + tmp[0] + ",";
              selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
            } 
          } 
        } 
        if (selectUser.indexOf(",") != -1) {
          selectUser = selectUser.substring(0, selectUser.length() - 1);
          selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
        } 
        break;
      case 10:
        step = workInfo[9];
        userIds = "";
        if ("1".equals(step)) {
          userIds = workInfo[10];
        } else {
          String[][] arrayOfString = getFrontActivityOper(workInfo[0], workInfo[1], workInfo[3], Integer.valueOf(workInfo[7]).intValue());
          for (int m = 0; m < arrayOfString.length; m++)
            userIds = String.valueOf(userIds) + arrayOfString[m][0] + ","; 
        } 
        leader = getOrgLeader(userIds);
        for (j = 0; j < leader.length; j++) {
          selectUser = String.valueOf(selectUser) + leader[j][0].replaceAll("\\$\\$", ",").replace("\\$", "") + ",";
          selectUserName = String.valueOf(selectUserName) + leader[j][1] + ",";
        } 
        if (selectUser.indexOf(",") != -1) {
          selectUser = selectUser.substring(0, selectUser.length() - 1);
          selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
        } 
        break;
      case 11:
        empList = getFrontActivityOper(workInfo[0], workInfo[1], workInfo[3], Integer.valueOf(workInfo[7]).intValue());
        for (k = 0; k < empList.length; k++) {
          selectUser = String.valueOf(selectUser) + empList[k][0] + ",";
          selectUserName = String.valueOf(selectUserName) + empList[k][1] + ",";
        } 
        if (selectUser.indexOf(",") != -1) {
          selectUser = selectUser.substring(0, selectUser.length() - 1);
          selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
        } 
        break;
    } 
    return new String[] { selectUser, selectUserName };
  }
  
  private List<String[]> getDivideActivitys(String curActivityId, String tableId, String recordId, String userId, String orgId, String orgIdString, String submitPersonId, String standForUserId, String tranFromPersonId) {
    List<String[]> actList = (List)new ArrayList<String>();
    WorkFlowBD workFlowBD = new WorkFlowBD();
    List<ActivityVO> activityList = workFlowBD.getAllNextActivity(tableId, recordId, curActivityId);
    if (activityList != null)
      for (int i = 0; i < activityList.size(); i++) {
        ActivityVO activityVO = activityList.get(i);
        long tmpActivityId = activityVO.getId();
        String[] arrayOfString = getActivityExecutor(
            tableId, recordId, String.valueOf(tmpActivityId), curActivityId, 
            userId, orgId, orgIdString, 
            submitPersonId, standForUserId, tranFromPersonId);
      }  
    return actList;
  }
  
  private String[] getActivityExecutor(String tableId, String recordId, String activityId, String curActivityId, String userId, String orgId, String orgIdString, String submitPersonId, String standForUserId, String tranFromPersonId) {
    String[] executor = { "", "", "", "", "" };
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    WorkFlowBD workFlowBD = new WorkFlowBD();
    String[] nextUser = workFlowBD.getProcActiUser(tableId, recordId, activityId);
    int participantType = -1;
    String participantUserField = "";
    String selectUser = "";
    String selectUserName = "";
    String range = "", show = "";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      if (nextUser[0] != null) {
        List<Object[]> list4, list3, list2, list1, candidate;
        int i;
        String sidelineOrg;
        int j;
        String currentUserId;
        int k;
        participantType = Integer.parseInt(nextUser[0]);
        List<Object[]> leaderList = null;
        switch (participantType) {
          case 3:
            list4 = workFlowBD.getCandidateSeq(nextUser[1], nextUser[3]);
            for (i = 0; i < list4.size(); i++) {
              Object[] tmp = list4.get(i);
              selectUser = String.valueOf(selectUser) + tmp[0] + ",";
              selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
              range = String.valueOf(range) + "$" + tmp[0] + "$";
            } 
            if (selectUser.indexOf(",") != -1) {
              selectUser = selectUser.substring(0, selectUser.length() - 1);
              selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
            } 
            break;
          case 2:
            list3 = workFlowBD.getCandidateSeq(nextUser[1], nextUser[3]);
            for (i = 0; i < list3.size(); i++) {
              Object[] tmp = list3.get(i);
              selectUser = String.valueOf(selectUser) + tmp[0] + ",";
              selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
              range = String.valueOf(range) + "$" + tmp[0] + "$";
            } 
            if (selectUser.indexOf(",") != -1) {
              selectUser = selectUser.substring(0, selectUser.length() - 1);
              selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
            } 
            break;
          case 4:
            participantUserField = nextUser[4];
            participantUserField = workFlowButtonBD.getFieldInfoByFieldId(participantUserField);
            break;
          case 8:
            range = nextUser[22].toString();
            show = "user";
            if (range != null && !range.equals("") && range.indexOf("@") >= 0)
              show = String.valueOf(show) + "group"; 
            if (range != null && !range.equals("") && range.indexOf("*") >= 0)
              show = String.valueOf(show) + "org"; 
            break;
          case 9:
            sidelineOrg = (workFlowButtonBD.getUserSideLineOrgId(userId) == null) ? "" : workFlowButtonBD.getUserSideLineOrgId(userId);
            range = "*" + orgId + "*" + sidelineOrg;
            show = String.valueOf(show) + "user";
            break;
          case 10:
            range = "*" + orgId + "*";
            show = String.valueOf(show) + "org";
            break;
          case 1:
            range = "*0*";
            show = "userorggroup";
            break;
          case 7:
            if (standForUserId == null || "null".equals(standForUserId) || "0".equals(standForUserId)) {
              if (tranFromPersonId != null && !"null".equals(tranFromPersonId) && !"".equals(tranFromPersonId) && !"-1".equals(tranFromPersonId)) {
                String tranFromPersonIdTemp = tranFromPersonId;
                while (!"".equals(tranFromPersonId)) {
                  rs = stmt.executeQuery("select tranfrompersonid from jsf_work where wf_curemployee_id=" + tranFromPersonId + " and initactivity=" + curActivityId + " and workrecord_id=" + recordId + " and worktable_id=" + tableId);
                  if (rs.next())
                    tranFromPersonId = rs.getString(1); 
                  rs.close();
                  if (tranFromPersonId == null || "null".equals(tranFromPersonId)) {
                    tranFromPersonId = "";
                    continue;
                  } 
                  tranFromPersonIdTemp = tranFromPersonId;
                } 
                tranFromPersonId = tranFromPersonIdTemp;
              } 
              if (!"".equals(tranFromPersonId)) {
                leaderList = workFlowBD.getLeaderList(tranFromPersonId);
              } else {
                leaderList = workFlowBD.getLeaderList(userId);
              } 
            } else {
              leaderList = workFlowBD.getLeaderList(standForUserId);
            } 
            for (j = 0; j < leaderList.size(); j++) {
              Object[] tmp = leaderList.get(j);
              selectUser = String.valueOf(selectUser) + tmp[0] + ",";
              selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
              range = String.valueOf(range) + "$" + tmp[0] + "$";
            } 
            if (selectUser.indexOf(",") != -1) {
              selectUser = selectUser.substring(0, selectUser.length() - 1);
              selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
            } 
            break;
          case 0:
            leaderList = workFlowBD.getLeaderList(submitPersonId);
            for (j = 0; j < leaderList.size(); j++) {
              Object[] tmp = leaderList.get(j);
              selectUser = String.valueOf(selectUser) + tmp[0] + ",";
              selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
              range = String.valueOf(range) + "$" + tmp[0] + "$";
            } 
            if (selectUser.indexOf(",") != -1) {
              selectUser = selectUser.substring(0, selectUser.length() - 1);
              selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
            } 
            break;
          case 5:
            selectUser = submitPersonId;
            selectUserName = (new UserBD()).getUserNameById(submitPersonId);
            break;
          case 6:
            currentUserId = userId;
            if (standForUserId == null || "null".equals(standForUserId) || "0".equals(standForUserId)) {
              if (tranFromPersonId != null && !"null".equals(tranFromPersonId) && !"".equals(tranFromPersonId) && !"-1".equals(tranFromPersonId)) {
                String tranFromPersonIdTemp = tranFromPersonId;
                while (!"".equals(tranFromPersonId)) {
                  rs = stmt.executeQuery("select tranfrompersonid from jsf_work where wf_curemployee_id=" + tranFromPersonId + " and initactivity=" + curActivityId + " and workrecord_id=" + recordId + " and worktable_id=" + tableId);
                  if (rs.next())
                    tranFromPersonId = rs.getString(1); 
                  rs.close();
                  if (tranFromPersonId == null || "null".equals(tranFromPersonId)) {
                    tranFromPersonId = "";
                    continue;
                  } 
                  tranFromPersonIdTemp = tranFromPersonId;
                } 
                tranFromPersonId = tranFromPersonIdTemp;
              } 
              if (!"".equals(tranFromPersonId))
                currentUserId = tranFromPersonId; 
            } else {
              currentUserId = standForUserId;
            } 
            list2 = workFlowBD.getRoleUserIDAndName(nextUser[18], String.valueOf(submitPersonId) + ";" + currentUserId);
            for (k = 0; k < list2.size(); k++) {
              Object[] tmp = list2.get(k);
              selectUser = String.valueOf(selectUser) + tmp[0] + ",";
              selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
              range = String.valueOf(range) + "$" + tmp[0] + "$";
            } 
            if (selectUser.indexOf(",") != -1) {
              selectUser = selectUser.substring(0, selectUser.length() - 1);
              selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
            } 
            break;
          case 15:
            currentUserId = userId;
            if (standForUserId == null || "null".equals(standForUserId) || "0".equals(standForUserId)) {
              if (tranFromPersonId != null && !"null".equals(tranFromPersonId) && !"".equals(tranFromPersonId) && !"-1".equals(tranFromPersonId)) {
                String tranFromPersonIdTemp = tranFromPersonId;
                while (!"".equals(tranFromPersonId)) {
                  rs = stmt.executeQuery("select tranfrompersonid from jsf_work where wf_curemployee_id=" + tranFromPersonId + " and initactivity=" + curActivityId + " and workrecord_id=" + recordId + " and worktable_id=" + tableId);
                  if (rs.next())
                    tranFromPersonId = rs.getString(1); 
                  rs.close();
                  if (tranFromPersonId == null || "null".equals(tranFromPersonId)) {
                    tranFromPersonId = "";
                    continue;
                  } 
                  tranFromPersonIdTemp = tranFromPersonId;
                } 
                tranFromPersonId = tranFromPersonIdTemp;
              } 
              if (!"".equals(tranFromPersonId))
                currentUserId = tranFromPersonId; 
            } else {
              currentUserId = standForUserId;
            } 
            list1 = workFlowBD.getPositionUserIDAndName(nextUser[18], String.valueOf(submitPersonId) + ";" + currentUserId);
            for (k = 0; k < list1.size(); k++) {
              Object[] tmp = list1.get(k);
              selectUser = String.valueOf(selectUser) + tmp[0] + ",";
              selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
              range = String.valueOf(range) + "$" + tmp[0] + "$";
            } 
            if (selectUser.indexOf(",") != -1) {
              selectUser = selectUser.substring(0, selectUser.length() - 1);
              selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
            } 
            break;
          case 14:
            candidate = workFlowButtonBD.getLeaderByDutyLevelAndOrg(submitPersonId, nextUser[18]);
            for (k = 0; k < candidate.size(); k++) {
              Object[] tmp = candidate.get(k);
              selectUser = String.valueOf(selectUser) + tmp[0] + ",";
              selectUserName = String.valueOf(selectUserName) + tmp[1] + ",";
              range = String.valueOf(range) + "$" + tmp[0] + "$";
            } 
            if (selectUser.indexOf(",") != -1) {
              selectUser = selectUser.substring(0, selectUser.length() - 1);
              selectUserName = selectUserName.substring(0, selectUserName.length() - 1);
            } 
            break;
        } 
      } 
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
    executor[0] = selectUser;
    executor[1] = selectUserName;
    executor[2] = String.valueOf(participantType);
    executor[3] = participantUserField;
    executor[4] = range;
    return executor;
  }
  
  private String[] getUserInfo(String userId) {
    String[] userInfo = { "", "" };
    String orgIdString = "";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      String sql = "SELECT o.org_id,orgIdString FROM org_organization o,org_organization_user ou WHERE o.org_id=ou.org_id AND ou.emp_id=" + userId;
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next()) {
        userInfo[0] = rs.getString(1);
        orgIdString = rs.getString(2);
        userInfo[1] = StringSplit.splitOrgIdString(orgIdString, "$", "_");
      } 
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
    return userInfo;
  }
}
