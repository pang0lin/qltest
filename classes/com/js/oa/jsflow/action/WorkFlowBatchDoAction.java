package com.js.oa.jsflow.action;

import com.js.oa.eform.service.CustomFormBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.service.WorkFlowButtonBD;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.jsflow.util.FlowSendAndBack;
import com.js.oa.jsflow.util.ProcessStep;
import com.js.oa.jsflow.vo.ActivityVO;
import com.js.oa.message.service.MsManageBD;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.StringSplit;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WorkFlowBatchDoAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    String action = httpServletRequest.getParameter("action");
    String tag = "workflowBatchDo";
    if ("workflowBatchDo".equals(action)) {
      tag = "workflowBatchDo";
      String sql = null;
      sql = "SELECT aaa.wfWorkFlowProcessId,aaa.workFlowProcessName FROM com.js.oa.jsflow.po.WFWorkFlowProcessPO aaa";
      sql = String.valueOf(sql) + " order by aaa.wfWorkFlowProcessId ";
      MsManageBD msBD = new MsManageBD();
      List<Object[]> ttableList = null;
      try {
        ttableList = msBD.getListByYourSQL(sql);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      if (ttableList != null)
        for (int i = 0; i < ttableList.size(); i++) {
          Object[] obj = ttableList.get(i);
          obj[0] = obj[0] + "," + obj[0];
        }  
      httpServletRequest.setAttribute("ttableList", ttableList);
    } else if (!"".equals(action)) {
      if ("workflowlimitdo".equals(action)) {
        tag = "workflowBatchDo";
        String workprocess = httpServletRequest.getParameter("workprocess");
        String processId = workprocess.split(",")[0];
        String workemployeeid = httpServletRequest.getParameter("workemployeeid");
        String dotime = httpServletRequest.getParameter("dotime");
        String workactivityId = httpServletRequest.getParameter("workactivity");
        String doemployeeId = httpServletRequest.getParameter("doemployeeId");
        String doemployeeName = httpServletRequest.getParameter("doemployeeName");
        String[] selectuser = { doemployeeId, doemployeeName };
        String message = "";
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dotime = dateTimeFormat.format(new Date(dotime));
        String databaseType = SystemCommon.getDatabaseType();
        DataSourceBase base = new DataSourceBase();
        ResultSet rs = null;
        Map<String, String> onlineUser = null;
        onlineUser = new ConcurrentHashMap<String, String>();
        String onlineSql = "SELECT recordId,workId FROM jsf_onlineuser ";
        try {
          base.begin();
          rs = base.executeQuery(onlineSql);
          while (rs.next())
            onlineUser.put(rs.getString(1), rs.getString(2)); 
          rs.close();
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          base.end();
        } 
        List<String[]> workList = (List)new ArrayList<String>();
        String workSql = "SELECT wf_work_id,WF_CUREMPLOYEE_ID,workfileType,workTitle,WORKPROCESS_ID,WORKTABLE_ID,WORKRECORD_ID,workdeadlineDate,workDeadlinePressDate,WORKSTEPCOUNT,INITSTEPCOUNT,initactivity,INITACTIVITYNAME,WORKSUBMITTIME,WF_SUBMITEMPLOYEE_ID,standForUserName,WORKSUBMITPERSON,WORKMAINLINKFILE,isStandForWork,standForUserId,standForUserName,handleInfo FROM jsf_work WHERE workstatus=0 and (handleInfo=0 or handleInfo=1) AND worktable_id<>-1   and WF_CUREMPLOYEE_ID=" + 




          
          workemployeeid + " and WORKPROCESS_ID=" + processId + "  and WORKACTIVITY=" + workactivityId + " " + 
          " and WORKSUBMITTIME<='" + dotime + "'  " + 
          "ORDER BY WORKRECORD_ID desc,wf_work_id desc";
        if ("oracle".equals(databaseType))
          workSql = "SELECT wf_work_id,WF_CUREMPLOYEE_ID,workfileType,workTitle,WORKPROCESS_ID,WORKTABLE_ID,WORKRECORD_ID,workdeadlineDate,workDeadlinePressDate,WORKSTEPCOUNT,INITSTEPCOUNT,initactivity,INITACTIVITYNAME,WORKSUBMITTIME,WF_SUBMITEMPLOYEE_ID,standForUserName,WORKSUBMITPERSON,WORKMAINLINKFILE,isStandForWork,standForUserId,standForUserName,handleInfo FROM jsf_work WHERE workstatus=0 and (handleInfo=0 or handleInfo=1) AND worktable_id<>-1   and WF_CUREMPLOYEE_ID=" + 




            
            workemployeeid + " and WORKPROCESS_ID=" + processId + "  and WORKACTIVITY=" + workactivityId + " " + 
            " and WORKSUBMITTIME<=JSDB.FN_STRTODATE('" + dotime + "','') " + 
            "ORDER BY WORKRECORD_ID desc,wf_work_id desc"; 
        System.out.println("workSql:" + workSql);
        try {
          base.begin();
          rs = base.executeQuery(workSql);
          String flagStr = "";
          while (rs.next()) {
            String[] work = new String[19];
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
            workList.add(work);
          } 
          rs.close();
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          base.end();
        } 
        String[] handleInfo = (String[])null;
        for (int i = workList.size() - 1; i >= 0; i--) {
          String[] work = workList.get(i);
          if (onlineUser.get(work[1]) == null)
            sendFlow(work, handleInfo, selectuser); 
        } 
        if (workList.size() == 0) {
          message = "没有查询到要处理的数据！";
        } else {
          message = "true";
        } 
        httpServletRequest.setAttribute("flag", message);
      } 
    } 
    return actionMapping.findForward(tag);
  }
  
  public void sendFlow(String[] workInfo, String[] handleInfo, String[] selectuser) {
    WorkFlowBD workFlowBD = new WorkFlowBD();
    FlowSendAndBack flowSendAndBack = new FlowSendAndBack();
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
          } else {
            para.put("mainNextActivityId", (new StringBuilder(String.valueOf(vo.getId()))).toString());
            para.put("mainNextActivityName", (vo.getName() == null) ? "" : vo.getName());
            para.put("mainAllowStandFor", (new StringBuilder(String.valueOf(vo.getAllowStandFor()))).toString());
            para.put("mainAllowCancel", (new StringBuilder(String.valueOf(vo.getAllowcancel()))).toString());
            para.put("mainPressType", (new StringBuilder(String.valueOf(vo.getPressType()))).toString());
            para.put("mainDeadLineTime", (new StringBuilder(String.valueOf(vo.getDeadlineTime()))).toString());
            para.put("mainPressMotionTime", (new StringBuilder(String.valueOf(vo.getPressMotionTime()))).toString());
            para.put("operId", selectuser[0]);
            para.put("operName", selectuser[1]);
            if (selectuser[0].length() > 0)
              flagStr = "send"; 
          } 
        } else {
          String curTransactType = (new WorkFlowButtonBD()).getCurTransactTypeByWorkId(workInfo[4]);
          para.put("curTransactType", curTransactType);
          flagStr = "complete";
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
        para.put("include_comment", "系统自动处理");
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
        para.put("include_commField", "-1");
        para.put("userScope", "");
        para.put("scopeId", "");
        para.put("subProcType", "-1");
        para.put("mainLinkFile", workInfo[14]);
        para.put("approveMode", "1");
        para.put("dealTips", "");
        para.put("mainNeedPassRound", "1");
        para.put("passRoundName", "");
        if ("send".equals(flagStr)) {
          flowSendAndBack.flowSend(para);
        } else if ("end".equals(flagStr)) {
          flowSendAndBack.endFlow(para);
        } else if ("complete".equals(flagStr)) {
          flowSendAndBack.completeFlow(para);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
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
  
  public String[][] getEmpInfoByOrgId(String orgIds) {
    String[][] empInfo = (String[][])null;
    String sql = "SELECT e.emp_id,e.empName FROM org_employee e JOIN org_organization_user u ON e.emp_id=u.emp_id JOIN org_organization o ON u.org_id=o.org_id WHERE 1<>1  (whereInfo) ORDER BY o.orgIdString";
    String[] orgId = orgIds.split(",");
    String whereInfo = "";
    for (int i = 0; i < orgId.length; i++)
      whereInfo = String.valueOf(whereInfo) + " or o.orgIdString like '%" + orgId[i] + "%'"; 
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
}
