package com.js.oa.jsflow.util;

import com.js.oa.eform.service.CustomFormBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.service.WorkFlowButtonBD;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.jsflow.vo.ActivityVO;
import com.js.oa.jsflow.vo.WorkLogVO;
import com.js.oa.jsflow.vo.WorkVO;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.service.messages.RemindUtil;
import com.js.system.service.usermanager.UserBD;
import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;
import com.js.util.util.CharacterTool;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

public class FlowSendAndBack {
  SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  
  public void flowBack(Map<String, String> backInfo) {
    String userId = backInfo.get("userId");
    String userName = backInfo.get("userName");
    String comment = CharacterTool.escapeHTMLTags2(backInfo.get("include_comment"));
    String curActivityId = backInfo.get("curActivityId");
    String curActivityName = backInfo.get("curActivityName");
    String processId = backInfo.get("processId");
    String tableId = backInfo.get("tableId");
    String recordId = backInfo.get("recordId");
    String workId = backInfo.get("workId");
    String stepCount = backInfo.get("stepCount");
    String submitPerson = backInfo.get("submitPerson");
    String submitTime = backInfo.get("submitTime");
    String isStandForWork = backInfo.get("isStandForWork");
    String standForUserId = backInfo.get("standForUserId");
    String backToActivityId = backInfo.get("backToActivityId");
    String backToActivityName = backInfo.get("backToActivityName");
    String backToUserId = backInfo.get("backToUserId");
    String backToUserName = backInfo.get("backToUserName");
    String mainLinkFile = backInfo.get("mainLinkFile");
    String backToStep = backInfo.get("backToStep");
    String include_commField = backInfo.get("include_commField");
    String userScope = backInfo.get("userScope");
    String scopeId = backInfo.get("scopeId");
    String title = backInfo.get("title");
    String backToAllInfo = backInfo.get("backToAllInfo");
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    String backType = "0";
    if ("0".equals(backToAllInfo)) {
      backType = "0";
      backToUserName = submitPerson;
      backToActivityName = "发起人";
    } else {
      backType = "1";
      String[] backToAllInfoArray = backToAllInfo.split(",");
      backToActivityId = backToAllInfoArray[0];
      backToStep = backToAllInfoArray[1];
      backToActivityName = backToAllInfoArray[2];
      backToUserId = backToAllInfoArray[3];
      backToUserName = backToAllInfoArray[4];
    } 
    WorkFlowCommonBD wfcBD = new WorkFlowCommonBD();
    WorkFlowButtonBD wfbBD = new WorkFlowButtonBD();
    Map<String, String> dealwithMap = new HashMap<String, String>();
    dealwithMap.put("tableId", tableId);
    dealwithMap.put("recordId", recordId);
    dealwithMap.put("curActivityName", curActivityName);
    dealwithMap.put("curActivityId", curActivityId);
    dealwithMap.put("userId", userId);
    dealwithMap.put("comment", comment);
    dealwithMap.put("nextActivityName", "退回");
    dealwithMap.put("nextActivityId", "-1");
    dealwithMap.put("stepCount", stepCount);
    dealwithMap.put("isStandForWork", isStandForWork);
    dealwithMap.put("standForUserId", standForUserId);
    if (include_commField != null)
      dealwithMap.put("commentField", include_commField); 
    dealwithMap.put("userScope", userScope);
    dealwithMap.put("scopeId", scopeId);
    wfcBD.insertDealWith(dealwithMap);
    String initStepCount = "0";
    String backContent = String.valueOf(submitPerson) + submitTime.substring(2, 16) + "发起的" + title + "被" + userName + "退回.<br />" + "退回原因：<br>" + comment;
    String exeUser = "";
    if ("0".equals(backType)) {
      Map<String, String> para = new HashMap<String, String>();
      para.put("workId", workId);
      para.put("tableId", tableId);
      para.put("recordId", recordId);
      para.put("workId", workId);
      para.put("stepCount", stepCount);
      para.put("userName", userName);
      para.put("isStandForWork", isStandForWork);
      para.put("standForUserId", standForUserId);
      para.put("userId", userId);
      para.put("comment", comment);
      para.put("backInfo", backContent);
      wfbBD.backToSubmitPerson(para);
    } else {
      Map<String, String> para = new HashMap<String, String>();
      para.put("workId", workId);
      para.put("recordId", recordId);
      para.put("tableId", tableId);
      para.put("workId", workId);
      para.put("stepCount", stepCount);
      para.put("userName", userName);
      para.put("userId", userId);
      para.put("backToActivityId", backToActivityId);
      initStepCount = backToStep;
      para.put("backToStep", initStepCount);
      para.put("backToActivityName", backToActivityName);
      para.put("workMainLinkFile", mainLinkFile);
      para.put("isStandForWork", isStandForWork);
      para.put("standForUserId", standForUserId);
      para.put("backToUserId", backToUserId);
      para.put("comment", comment);
      para.put("backInfo", backContent);
      exeUser = wfbBD.backToActivity(para);
    } 
    Map backMap = wfbBD.getBackToPerson(tableId, recordId, stepCount, initStepCount, workId);
    WorkLogVO workLogVO = new WorkLogVO();
    workLogVO.setSendUserId(userId);
    workLogVO.setSendUserName(userName);
    workLogVO.setSendAction("退回" + backToActivityName + "（系统自动处理）");
    workLogVO.setReceiveUserName((backToUserName == null) ? "" : backToUserName);
    workLogVO.setProcessId(processId);
    workLogVO.setTableId(tableId);
    workLogVO.setRecordId(recordId);
    workLogVO.setDomainId("0");
    workFlowButtonBD.setDealWithLog(workLogVO);
    if ("0".equals(backType)) {
      String to = backMap.get("empId").toString();
      String empWorkId = backMap.get("empWorkId").toString();
      if (!"".equals(to)) {
        to = userIdStringToArray(to);
        empWorkId = empWorkId.substring(0, empWorkId.length() - 1);
        title = "您发起的" + title + "被" + userName + "退回";
        String url = "/jsoa/jsflow/item/jump_dealwith.jsp?status=-1&workId=" + empWorkId;
        RemindUtil.sendMessageToUsers(title, url, to, "jsflow", new Date(), new Date("2050/1/1"));
      } 
    } else {
      String to = backMap.get("empId").toString();
      String empWorkId = backMap.get("empWorkId").toString();
      String url = "/jsoa/jsflow/item/jump_dealwith.jsp?status=-1&workId=";
      if (!"".equals(to)) {
        to = userIdStringToArray(to);
        empWorkId = empWorkId.substring(0, empWorkId.length() - 1);
        String[] toArr = to.split(",");
        String[] workIdArr = empWorkId.split(",");
        String titleTemp = String.valueOf(submitPerson) + "发起的" + title + "被" + userName + "退回到" + backToActivityName;
        for (int i = 0; i < toArr.length; i++) {
          if (exeUser.indexOf("," + toArr[i] + ",") < 0)
            RemindUtil.sendMessageToUsers(titleTemp, String.valueOf(url) + workIdArr[i], toArr[i], "jsflow", new Date(), new Date("2050/1/1")); 
        } 
      } 
      title = "您发起的" + title + "被" + userName + "退回到" + backToActivityName;
      to = backMap.get("submitEmpId").toString();
      RemindUtil.sendMessageToUsers(title, String.valueOf(url) + backMap.get("submitWorkId").toString(), to, "jsflow", new Date(), new Date("2050/1/1"));
    } 
  }
  
  private String userIdStringToArray(String userId) {
    userId = userId.substring(1, userId.length() - 1);
    userId = userId.replaceAll("\\$\\$", ",");
    return userId;
  }
  
  public void flowSend(Map<String, String> sendInfo) {
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    String userId = sendInfo.get("userId");
    String userName = sendInfo.get("userName");
    String orgId = sendInfo.get("orgId");
    String orgIdString = sendInfo.get("orgIdString");
    String isStandForWork = sendInfo.get("isStandForWork");
    String standForUserId = sendInfo.get("standForUserId");
    String standForUserName = sendInfo.get("standForUserName");
    String submitPersonId = sendInfo.get("submitPersonId");
    String mainPressType = sendInfo.get("mainPressType");
    String mainDeadLineTime = "-1";
    String mainPressMotionTime = "-1";
    String comment = CharacterTool.escapeHTMLTags2(sendInfo.get("include_comment"));
    String mainAllowStandFor = sendInfo.get("mainAllowStandFor");
    String mainAllowCancel = sendInfo.get("mainAllowCancel");
    String curActivityId = sendInfo.get("curActivityId");
    String curActivityName = sendInfo.get("curActivityName");
    String mainNextActivityId = sendInfo.get("mainNextActivityId");
    String mainNextActivityName = sendInfo.get("mainNextActivityName");
    String processName = sendInfo.get("processName");
    String tableId = sendInfo.get("tableId");
    String recordId = sendInfo.get("recordId");
    String workId = sendInfo.get("workId");
    String processId = sendInfo.get("processId");
    String stepCount = sendInfo.get("stepCount");
    String activityClass = sendInfo.get("activityClass");
    mainDeadLineTime = sendInfo.get("mainDeadLineTime");
    mainPressMotionTime = sendInfo.get("mainPressMotionTime");
    WorkFlowBD workFlowBD = new WorkFlowBD();
    String[] mainTransactUser = { "" };
    if ("3".equals(activityClass)) {
      NewWorkflowUtil newUtil = new NewWorkflowUtil();
      ActivityVO activityVO = newUtil.getPreActivity(tableId, recordId, curActivityId, Integer.parseInt(stepCount));
      mainNextActivityId = String.valueOf(activityVO.getId());
      mainNextActivityName = activityVO.getName();
      mainTransactUser = newUtil.getPreActivityUser(tableId, recordId, curActivityId, Integer.parseInt(stepCount));
      mainPressType = String.valueOf(activityVO.getPressType());
      mainDeadLineTime = String.valueOf(activityVO.getDeadlineTime());
      mainPressMotionTime = String.valueOf(activityVO.getPressMotionTime());
      mainAllowStandFor = String.valueOf(activityVO.getAllowStandFor());
      mainAllowCancel = String.valueOf(activityVO.getAllowcancel());
    } else {
      String operId, operProcOrg;
      int mainUserType = Integer.parseInt(sendInfo.get("mainUserType"));
      if (mainUserType == 10)
        mainUserType = 100; 
      switch (mainUserType) {
        case 100:
          operId = sendInfo.get("operId");
          if (operId != null && !operId.equals("")) {
            String userIdStr = operId;
            DbOpt dbopt = null;
            try {
              dbopt = new DbOpt();
              userIdStr = userIdStr.replaceAll(",,", ",").replaceAll("\\$\\$", ",").replaceAll("\\$", ",");
              while (userIdStr.startsWith(",") || userIdStr.endsWith(",")) {
                if (userIdStr.startsWith(",")) {
                  userIdStr = userIdStr.substring(1, userIdStr.length());
                  continue;
                } 
                userIdStr = userIdStr.substring(0, userIdStr.length() - 1);
              } 
              if (userIdStr.length() > 0) {
                String[] tmp = userIdStr.split(",");
                String sql = "select EMP_ID from ORG_EMPLOYEE where USERISACTIVE=1 and USERISDELETED<>1 and EMP_ID in (" + userIdStr + ") order   by   case   emp_id ";
                for (int k = 0; k < tmp.length; k++)
                  sql = String.valueOf(sql) + " when " + tmp[k] + " then " + (k + 1) + " "; 
                sql = String.valueOf(sql) + " end ";
                mainTransactUser = dbopt.executeQueryToStrArr1(sql);
              } else {
                mainTransactUser = new String[0];
              } 
              dbopt.close();
            } catch (Exception e) {
              try {
                dbopt.close();
              } catch (SQLException sQLException) {}
              mainTransactUser = new String[0];
            } 
          } 
          break;
        case 10:
          operProcOrg = sendInfo.get("operId");
          mainTransactUser = workFlowBD.getLeaderListByOrgId(operProcOrg);
          break;
      } 
    } 
    String mainNeedPassRound = "";
    String[] mainPassRoundUser = { "" };
    String subProcWorkId = (sendInfo.get("subProcWorkId") == null) ? "0" : sendInfo.get("subProcWorkId");
    String modiCommentId = sendInfo.get("modiCommentId");
    Map<String, String> dealwithMap = new HashMap<String, String>();
    dealwithMap.put("tableId", tableId);
    dealwithMap.put("recordId", recordId);
    dealwithMap.put("curActivityName", curActivityName);
    dealwithMap.put("curActivityId", curActivityId);
    dealwithMap.put("userId", userId);
    dealwithMap.put("orgIdString", orgIdString);
    dealwithMap.put("comment", comment);
    dealwithMap.put("nextActivityName", mainNextActivityName);
    dealwithMap.put("nextActivityId", mainNextActivityId);
    dealwithMap.put("stepCount", stepCount);
    dealwithMap.put("isStandForWork", isStandForWork);
    dealwithMap.put("standForUserId", standForUserId);
    dealwithMap.put("activityClass", activityClass);
    dealwithMap.put("subProcWorkId", subProcWorkId);
    if (sendInfo.get("include_commField") != null)
      dealwithMap.put("commentField", sendInfo.get("include_commField")); 
    dealwithMap.put("userScope", sendInfo.get("userScope"));
    dealwithMap.put("scopeId", sendInfo.get("scopeId"));
    dealwithMap.put("modiCommentId", modiCommentId);
    WorkFlowCommonBD wfcBD = new WorkFlowCommonBD();
    String subProcType = (sendInfo.get("subProcType") == null) ? "-1" : sendInfo.get("subProcType");
    String toMainFile = sendInfo.get("mainLinkFile");
    String curTransactType = workFlowBD.getTransactType(tableId, recordId, curActivityId);
    String nextTransactType = sendInfo.get("approveMode");
    String docTitle = getWorkTitle(processId, recordId, tableId);
    String emergence = "0";
    String dealTips = sendInfo.get("dealTips");
    if (dealTips != null && !dealTips.equals("")) {
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
      dealTips = "来自上一环节:" + mainNextActivityName + ";  办理人：" + userName + "; " + df.format(new Date()) + " " + dealTips;
    } 
    String remindFieldValue = "";
    String[] para = { 
        mainNextActivityName, mainNextActivityId, workId, mainDeadLineTime, mainPressMotionTime, 
        curActivityId, mainAllowCancel, stepCount, remindFieldValue, curTransactType, 
        toMainFile, mainAllowStandFor, isStandForWork, userId, standForUserId, standForUserName, 
        activityClass, subProcType, docTitle, emergence, 
        nextTransactType, dealTips };
    Integer result = wfcBD.transWorkflowButton(dealwithMap, para, mainTransactUser, mainNeedPassRound, mainPassRoundUser);
    if (result.intValue() < 0) {
      System.out.println("流程自动提交失败！");
    } else {
      WorkLogVO workLogVO = new WorkLogVO();
      if ("3".equals(activityClass)) {
        workLogVO.setSendUserId(userId);
        workLogVO.setSendUserName(userName);
        workLogVO.setSendAction("返回上一节点（系统自动处理）");
        String tmpUser = "0";
        for (int i = 0; i < mainTransactUser.length; i++)
          tmpUser = String.valueOf(tmpUser) + mainTransactUser[i] + ","; 
        if (tmpUser.endsWith(","))
          tmpUser = tmpUser.substring(0, tmpUser.length() - 1); 
        tmpUser = (new UserBD()).getUserNameById(tmpUser);
        if (tmpUser.endsWith(","))
          tmpUser = tmpUser.substring(0, tmpUser.length() - 1); 
        workLogVO.setReceiveUserName(tmpUser);
        workLogVO.setProcessId(processId);
        workLogVO.setTableId(tableId);
        workLogVO.setRecordId(recordId);
        workLogVO.setDomainId("0");
      } else {
        workLogVO.setSendUserId(userId);
        workLogVO.setSendUserName(userName);
        workLogVO.setSendAction("送" + mainNextActivityName + "（系统自动处理）");
        workLogVO.setReceiveUserName(sendInfo.get("operName"));
        workLogVO.setProcessId(processId);
        workLogVO.setTableId(tableId);
        workLogVO.setRecordId(recordId);
        workLogVO.setDomainId("0");
      } 
      workFlowButtonBD.setDealWithLog(workLogVO);
      if (sendInfo.get("mainNeedPassRound") != null && !"".equals(sendInfo.get("passRoundName"))) {
        workLogVO = new WorkLogVO();
        workLogVO.setSendUserId(userId);
        workLogVO.setSendUserName(userName);
        workLogVO.setSendAction("送" + mainNextActivityName + "阅件" + "（系统自动处理）");
        workLogVO.setReceiveUserName(sendInfo.get("passRoundName"));
        workLogVO.setProcessId(sendInfo.get("processId"));
        workLogVO.setTableId(sendInfo.get("tableId"));
        workLogVO.setRecordId(sendInfo.get("recordId"));
        workLogVO.setDomainId("0");
        workFlowButtonBD.setDealWithLog(workLogVO);
      } 
    } 
  }
  
  public void branchStart(Map<String, String> sendInfo) {
    WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    Map formClassNameMethod = workFlowCommonBD.getActivityClassMethod(sendInfo.get("curActivityId"));
    String remindFieldValue = "";
    if (!"error".equals(remindFieldValue)) {
      String remindField = sendInfo.get("remindField");
      if (remindField.startsWith("$") && remindField.endsWith("$")) {
        remindFieldValue = sendInfo.get("motif");
      } else if (remindField.indexOf("S") < 0) {
        remindFieldValue = "";
      } else {
        remindFieldValue = getRemindValue(remindField, sendInfo.get("recordId"), sendInfo.get("tableId"));
      } 
      remindFieldValue = CharacterTool.deleteEnter(remindFieldValue);
      String userId = sendInfo.get("userId");
      String userName = sendInfo.get("userName");
      String orgId = sendInfo.get("orgId");
      String orgIdString = sendInfo.get("orgIdString");
      String isStandForWork = sendInfo.get("isStandForWork");
      String standForUserId = sendInfo.get("standForUserId");
      String standForUserName = sendInfo.get("standForUserName");
      String submitPersonId = sendInfo.get("submitPersonId");
      String mainPressType = sendInfo.get("mainPressType");
      String mainDeadLineTime = "-1";
      String mainPressMotionTime = "-1";
      String comment = sendInfo.get("include_comment");
      comment = CharacterTool.escapeHTMLTags2(comment);
      String signComment = sendInfo.get("include_signcomment");
      String mainAllowStandFor = sendInfo.get("mainAllowStandFor");
      String mainAllowCancel = sendInfo.get("mainAllowCancel");
      String curActivityId = sendInfo.get("curActivityId");
      String curActivityName = sendInfo.get("curActivityName");
      String mainNextActivityId = sendInfo.get("mainNextActivityId");
      String mainNextActivityName = sendInfo.get("mainNextActivityName");
      String processName = sendInfo.get("processName");
      String tableId = sendInfo.get("tableId");
      String tableName = (new CustomFormBD()).getTable(tableId);
      String recordId = sendInfo.get("recordId");
      String workId = sendInfo.get("workId");
      String stepCount = sendInfo.get("stepCount");
      String activityClass = sendInfo.get("activityClass");
      mainDeadLineTime = sendInfo.get("mainDeadLineTime");
      mainPressMotionTime = sendInfo.get("mainPressMotionTime");
      String relprojectId = sendInfo.get("relproject");
      WorkFlowBD workFlowBD = new WorkFlowBD();
      String[] mainTransactUser = { "" };
      if ("3".equals(activityClass)) {
        NewWorkflowUtil newUtil = new NewWorkflowUtil();
        ActivityVO activityVO = newUtil.getPreActivity(tableId, recordId, curActivityId, Integer.parseInt(stepCount));
        mainNextActivityId = String.valueOf(activityVO.getId());
        mainNextActivityName = activityVO.getName();
        mainTransactUser = newUtil.getPreActivityUser(tableId, recordId, curActivityId, Integer.parseInt(stepCount));
        mainPressType = String.valueOf(activityVO.getPressType());
        mainDeadLineTime = String.valueOf(activityVO.getDeadlineTime());
        mainPressMotionTime = String.valueOf(activityVO.getPressMotionTime());
        mainAllowStandFor = String.valueOf(activityVO.getAllowStandFor());
        mainAllowCancel = String.valueOf(activityVO.getAllowcancel());
      } else {
        String operId = sendInfo.get("operId");
        if (operId == null || "null".equals(operId) || operId.length() < 2) {
          mainTransactUser = new String[0];
        } else {
          operId = operId.substring(1, operId.length() - 1);
          mainTransactUser = operId.split("\\$\\$");
        } 
      } 
      if (!"".equals(sendInfo.get("branchActInfo"))) {
        String changeDeadLineTime;
        switch (Integer.parseInt(mainPressType)) {
          case 0:
            mainDeadLineTime = "-1";
            mainPressMotionTime = "-1";
            break;
          case 1:
            changeDeadLineTime = sendInfo.get("changeDeadLineTime");
            if (changeDeadLineTime == null || "null".equals(changeDeadLineTime) || "-1".equals(changeDeadLineTime) || 
              "".equals(changeDeadLineTime)) {
              mainDeadLineTime = sendInfo.get("mainDeadLineTime");
            } else {
              mainDeadLineTime = changeDeadLineTime;
            } 
            mainPressMotionTime = sendInfo.get("mainPressMotionTime");
            break;
        } 
        String mainNeedPassRound = "";
        String[] mainPassRoundUser = { "" };
        String subProcWorkId = (sendInfo.get("subProcWorkId") == null) ? "0" : sendInfo.get("subProcWorkId");
        String modiCommentId = sendInfo.get("modiCommentId");
        Map<Object, Object> dealwithMap = new HashMap<Object, Object>();
        dealwithMap.put("tableId", tableId);
        dealwithMap.put("recordId", recordId);
        dealwithMap.put("curActivityName", curActivityName);
        dealwithMap.put("curActivityId", curActivityId);
        dealwithMap.put("userId", userId);
        dealwithMap.put("orgIdString", orgIdString);
        dealwithMap.put("comment", comment);
        dealwithMap.put("signcomment", signComment);
        dealwithMap.put("nextActivityName", mainNextActivityName);
        dealwithMap.put("nextActivityId", mainNextActivityId);
        dealwithMap.put("stepCount", stepCount);
        dealwithMap.put("isStandForWork", isStandForWork);
        dealwithMap.put("standForUserId", standForUserId);
        dealwithMap.put("activityClass", activityClass);
        dealwithMap.put("subProcWorkId", subProcWorkId);
        if (sendInfo.get("include_commField") != null)
          dealwithMap.put("commentField", sendInfo.get("include_commField")); 
        dealwithMap.put("userScope", sendInfo.get("userScope"));
        dealwithMap.put("scopeId", sendInfo.get("scopeId"));
        dealwithMap.put("modiCommentId", modiCommentId);
        dealwithMap.put("relproject", relprojectId);
        dealwithMap.put("branchActInfo", sendInfo.get("branchActInfo"));
        WorkFlowCommonBD wfcBD = new WorkFlowCommonBD();
        String subProcType = (sendInfo.get("subProcType") == null) ? "-1" : sendInfo.get("subProcType");
        String toMainFile = sendInfo.get("mainLinkFile");
        String curTransactType = workFlowBD.getTransactType(tableId, recordId, curActivityId);
        String nextTransactType = sendInfo.get("approveMode");
        if (remindFieldValue != null && remindFieldValue.toUpperCase().equals("NULL"))
          remindFieldValue = ""; 
        String docTitle = "";
        if (!"".equals(sendInfo.get("titleFieldName")))
          docTitle = sendInfo.get(sendInfo.get("titleFieldName")); 
        String emergence = (sendInfo.get("emergence") == null) ? "0" : sendInfo.get("emergence");
        String dealTips = "";
        String branchActInfo = dealwithMap.get("branchActInfo").toString();
        String branchUserId = "", branchActivityNames = "";
        String[] branchActArray = branchActInfo.split(";;;;");
        for (int i = 0; i < branchActArray.length; i++) {
          if (i == 0) {
            branchActivityNames = String.valueOf(branchActivityNames) + branchActArray[i].split(";;")[1];
            branchUserId = String.valueOf(branchUserId) + branchActArray[i].split(";;")[2];
          } else {
            if (i < 3) {
              branchActivityNames = String.valueOf(branchActivityNames) + "," + branchActArray[i].split(";;")[1];
            } else if (i == 5) {
              branchActivityNames = String.valueOf(branchActivityNames) + "等";
            } 
            branchUserId = String.valueOf(branchUserId) + "," + branchActArray[i].split(";;")[2];
          } 
        } 
        String branchUserName = StaticParam.getNamesByIds(branchUserId);
        dealwithMap.put("branchActivityNames", branchActivityNames);
        String sendSMS = (sendInfo.get("sendSMS") == null) ? "1" : sendInfo.get("sendSMS");
        String[] para = { 
            mainNextActivityName, mainNextActivityId, workId, mainDeadLineTime, mainPressMotionTime, 
            curActivityId, mainAllowCancel, stepCount, remindFieldValue, curTransactType, 
            toMainFile, mainAllowStandFor, isStandForWork, userId, standForUserId, standForUserName, 
            activityClass, subProcType, docTitle, emergence, 
            nextTransactType, dealTips, sendSMS };
        Integer result = wfcBD.transWorkflowBranch(dealwithMap, para, mainTransactUser, mainNeedPassRound, mainPassRoundUser);
        if (result.intValue() < 0) {
          System.out.println("分支开始节点自动提交失败");
        } else {
          WorkLogVO workLogVO = new WorkLogVO();
          workLogVO.setSendUserId(userId);
          workLogVO.setSendUserName(userName);
          workLogVO.setSendAction("送" + dealwithMap.get("branchActivityNames").toString());
          workLogVO.setReceiveUserName(branchUserName);
          workLogVO.setProcessId(sendInfo.get("processId"));
          workLogVO.setTableId(sendInfo.get("tableId"));
          workLogVO.setRecordId(sendInfo.get("recordId"));
          workLogVO.setDomainId("0");
          workFlowButtonBD.setDealWithLog(workLogVO);
          if (sendInfo.get("sendMsgToInitiator") != null && "1".equals(sendInfo.get("sendMsgToInitiator"))) {
            String sql = "SELECT DISTINCT b.emp_Id,b.empName,a.wf_work_id FROM JSF_WORK a JOIN org_employee b ON a.wf_curemployee_id=b.emp_Id WHERE WORKPROCESS_ID=" + 
              (String)sendInfo.get("processId") + " AND a.worktable_id=" + tableId + " AND a.workrecord_id=" + recordId + 
              " AND workstepcount=0";
            String[][] initWorkId = (new DataSourceBase()).queryArrayBySql(sql);
            try {
              String url = "/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + workId;
              RemindUtil.sendMessageToUsers2("您发起的" + (String)sendInfo.get("processName") + "流程已经被系统自动处理" + 
                  "(" + (String)sendInfo.get("curActivityName") + 
                  ")！", url, initWorkId[0][0], "jsflow", new Date(), (
                  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse("2050-01-01 00:00:00"), "系统提醒", Long.valueOf(initWorkId[0][2]), 1);
            } catch (Exception e) {
              e.printStackTrace();
            } 
          } 
        } 
      } else {
        System.out.println("分支开始节点自动提交失败");
      } 
    } 
  }
  
  public void endFlow(Map<String, String> endInfo) {
    String isStandForWork = endInfo.get("isStandForWork");
    String standForUserId = endInfo.get("standForUserId");
    String standForUserName = endInfo.get("standForUserName");
    String submitPersonId = endInfo.get("submitPersonId");
    String mainPressType = endInfo.get("mainPressType");
    String mainDeadLineTime = "-1";
    String mainPressMotionTime = "-1";
    String comment = endInfo.get("include_comment");
    String mainAllowStandFor = endInfo.get("mainAllowStandFor");
    String mainAllowCancel = endInfo.get("mainAllowCancel");
    String curActivityId = endInfo.get("curActivityId");
    String curActivityName = endInfo.get("curActivityName");
    String mainNextActivityId = "0";
    String mainNextActivityName = "0";
    String processName = endInfo.get("processName");
    String tableId = endInfo.get("tableId");
    String recordId = endInfo.get("recordId");
    String workId = endInfo.get("workId");
    String userId = ((String)endInfo.get("userId")).toString();
    String stepCount = endInfo.get("stepCount");
    String activityClass = endInfo.get("activityClass");
    mainDeadLineTime = endInfo.get("mainDeadLineTime");
    mainPressMotionTime = endInfo.get("mainPressMotionTime");
    WorkFlowBD workFlowBD = new WorkFlowBD();
    switch (Integer.parseInt(mainPressType)) {
      case 0:
        mainDeadLineTime = "-1";
        mainPressMotionTime = "-1";
        break;
    } 
    String subProcWorkId = (endInfo.get("subProcWorkId") == null) ? "0" : endInfo.get("subProcWorkId");
    String modiCommentId = endInfo.get("modiCommentId");
    Map<String, String> dealwithMap = new HashMap<String, String>();
    dealwithMap.put("tableId", tableId);
    dealwithMap.put("recordId", recordId);
    dealwithMap.put("curActivityName", curActivityName);
    dealwithMap.put("curActivityId", curActivityId);
    dealwithMap.put("userId", userId);
    dealwithMap.put("comment", comment);
    dealwithMap.put("nextActivityName", mainNextActivityName);
    dealwithMap.put("nextActivityId", mainNextActivityId);
    dealwithMap.put("stepCount", stepCount);
    dealwithMap.put("isStandForWork", isStandForWork);
    dealwithMap.put("standForUserId", standForUserId);
    dealwithMap.put("activityClass", activityClass);
    dealwithMap.put("subProcWorkId", subProcWorkId);
    dealwithMap.put("modiCommentId", modiCommentId);
    if (endInfo.get("include_commField") != null)
      dealwithMap.put("commentField", endInfo.get("include_commField")); 
    dealwithMap.put("userScope", endInfo.get("userScope"));
    dealwithMap.put("scopeId", endInfo.get("scopeId"));
    WorkFlowCommonBD wfcBD = new WorkFlowCommonBD();
    String subProcType = (endInfo.get("subProcType") == null) ? "-1" : endInfo.get("subProcType");
    String toMainFile = endInfo.get("mainLinkFile");
    String remindFieldValue = "";
    String curTransactType = workFlowBD.getTransactType(tableId, recordId, curActivityId);
    String nextTransactType = endInfo.get("approveMode");
    if (remindFieldValue != null && remindFieldValue.toUpperCase().equals("NULL"))
      remindFieldValue = ""; 
    String docTitle = getWorkTitle(endInfo.get("processId"), recordId, tableId);
    String emergence = (endInfo.get("emergence") == null) ? "0" : endInfo.get("emergence");
    String[] para = { 
        mainNextActivityName, mainNextActivityId, workId, mainDeadLineTime, mainPressMotionTime, 
        curActivityId, mainAllowCancel, stepCount, remindFieldValue, curTransactType, 
        toMainFile, mainAllowStandFor, isStandForWork, userId, standForUserId, standForUserName, 
        activityClass, subProcType, docTitle, emergence, 
        nextTransactType };
    Integer result = wfcBD.endWorkflowButton(dealwithMap, para);
    if (result.intValue() < 0) {
      System.out.println("流程提交失败！");
    } else {
      WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
      WorkLogVO workLogVO = new WorkLogVO();
      workLogVO.setSendUserId(((String)endInfo.get("userId")).toString());
      workLogVO.setSendUserName(((String)endInfo.get("userName")).toString());
      workLogVO.setSendAction("办理完毕(系统自动处理)");
      WorkVO workVO = new WorkVO();
      workVO.setTableId(Long.valueOf(tableId));
      workVO.setRecordId(Long.valueOf(recordId));
      List<Object[]> tmpList = workFlowButtonBD.getAllDealwithUsersByStatus2(workVO, "0");
      String tmpUser = "";
      for (int i = 0; i < tmpList.size(); i++) {
        Object[] obj = tmpList.get(i);
        tmpUser = String.valueOf(tmpUser) + obj[1] + ",";
      } 
      if (tmpUser.endsWith(","))
        tmpUser = tmpUser.substring(0, tmpUser.length() - 1); 
      workLogVO.setReceiveUserName(tmpUser);
      workLogVO.setProcessId(endInfo.get("processId"));
      workLogVO.setTableId(endInfo.get("tableId"));
      workLogVO.setRecordId(endInfo.get("recordId"));
      workLogVO.setDomainId("0");
      workFlowButtonBD.setDealWithLog(workLogVO);
    } 
  }
  
  public void completeFlow(Map<String, String> completeInfo) {
    WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    String remindFieldValue = "";
    String isStandForWork = completeInfo.get("isStandForWork");
    String standForUserId = completeInfo.get("standForUserId");
    String standForUserName = completeInfo.get("standForUserName");
    String comment = CharacterTool.escapeHTMLTags2(completeInfo.get("include_comment"));
    String signComment = completeInfo.get("include_signcomment");
    String curActivityId = completeInfo.get("curActivityId");
    String curActivityName = completeInfo.get("curActivityName");
    String processName = completeInfo.get("processName");
    String tableId = completeInfo.get("tableId");
    String recordId = completeInfo.get("recordId");
    String workId = completeInfo.get("workId");
    String userId = ((String)completeInfo.get("userId")).toString();
    String stepCount = completeInfo.get("stepCount");
    String activityClass = completeInfo.get("activityClass");
    String submitPerson = completeInfo.get("submitPerson");
    String curTransactType = completeInfo.get("curTransactType");
    String moduleId = workFlowCommonBD.getModuleId(tableId);
    Map<String, String> dealwithMap = new HashMap<String, String>();
    dealwithMap.put("tableId", tableId);
    dealwithMap.put("recordId", recordId);
    dealwithMap.put("curActivityName", curActivityName);
    dealwithMap.put("curActivityId", curActivityId);
    dealwithMap.put("userId", userId);
    dealwithMap.put("comment", comment);
    dealwithMap.put("signcomment", signComment);
    dealwithMap.put("nextActivityName", "");
    dealwithMap.put("nextActivityId", "0");
    dealwithMap.put("stepCount", stepCount);
    dealwithMap.put("isStandForWork", isStandForWork);
    dealwithMap.put("standForUserId", standForUserId);
    dealwithMap.put("activityClass", activityClass);
    dealwithMap.put("subProcWorkId", (completeInfo.get("subProcWorkId") == null) ? "0" : completeInfo.get("subProcWorkId"));
    if (completeInfo.get("include_commField") != null)
      dealwithMap.put("commentField", completeInfo.get("include_commField")); 
    dealwithMap.put("userScope", completeInfo.get("userScope"));
    dealwithMap.put("scopeId", completeInfo.get("scopeId"));
    WorkFlowCommonBD wfcBD = new WorkFlowCommonBD();
    wfcBD.insertDealWith(dealwithMap);
    String docTitle = "";
    String[] para = { 
        tableId, recordId, curActivityId, processName, submitPerson, remindFieldValue, curTransactType, stepCount, 
        isStandForWork, userId, 
        standForUserId, standForUserName, docTitle };
    Integer result = workFlowCommonBD.completeWork(para, workId);
    if (result.intValue() == 1) {
      WorkLogVO workLogVO = new WorkLogVO();
      workLogVO.setSendUserId(completeInfo.get("userId"));
      workLogVO.setSendUserName(completeInfo.get("userName"));
      workLogVO.setSendAction("流程办理完毕（系统自动处理）");
      workLogVO.setReceiveUserName(" ");
      workLogVO.setProcessId(completeInfo.get("processId"));
      workLogVO.setTableId(completeInfo.get("tableId"));
      workLogVO.setRecordId(completeInfo.get("recordId"));
      workLogVO.setDomainId(completeInfo.get("domainId"));
      workFlowButtonBD.setDealWithLog(workLogVO);
      if (!"1".equals(SystemCommon.getFlowCommentRange())) {
        if ("2".equals(moduleId) || "3".equals(moduleId)) {
          workFlowButtonBD.deleteFlowTempData(completeInfo.get("processId"), tableId, recordId, "1");
        } else {
          workFlowButtonBD.deleteFlowTempData(completeInfo.get("processId"), tableId, recordId, "2");
        } 
      } else {
        workFlowButtonBD.deleteFlowTempData(completeInfo.get("processId"), tableId, recordId, "1");
      } 
    } 
  }
  
  public String getWorkTitle(String processId, String recordId, String tableId) {
    int module = 1;
    String workTitle = "";
    DataSourceBase dsb = new DataSourceBase();
    DataSource ds = dsb.getDataSource();
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      String sql = "select wf_module_id from jsf_workflowprocess wp left join jsf_package pk on wp.wf_package_id=pk.wf_package_id where wp.wf_workflowprocess_id=" + processId;
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next())
        module = rs.getInt(1); 
      rs.close();
      if (module == 2) {
        sql = "select documentsendfile_title from doc_documentsendfile where documentsendfile_id=" + recordId;
        rs = stmt.executeQuery(sql);
        if (rs.next())
          workTitle = rs.getString(1); 
        rs.close();
      } else if (module == 3) {
        sql = "select receivefile_title from doc_receivefile where receivefile_id=" + recordId;
        rs = stmt.executeQuery(sql);
        if (rs.next())
          workTitle = rs.getString(1); 
        rs.close();
      } 
      stmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        conn.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      } 
    } 
    return workTitle;
  }
  
  private String getRemindValue(String remindField, String recordId, String formId) {
    String remindValue = "";
    if (remindField == null || remindField.length() < 1 || 
      remindField.toUpperCase().equals("null") || 
      recordId == null || recordId.length() < 1 || 
      recordId.toUpperCase().equals("null") || 
      formId == null || formId.length() < 1 || 
      formId.toUpperCase().equals("null"))
      return ""; 
    remindField = "S" + remindField + "S";
    String[] remindFieldArr = remindField.split("SS");
    CustomFormBD formBD = new CustomFormBD();
    for (int i = 0; i < remindFieldArr.length; i++) {
      String temp = formBD.getRemindValue(remindFieldArr[i], recordId, formId);
      if (temp == null || temp.length() < 1 || 
        temp.toUpperCase().equals("NULL")) {
        remindValue = (new StringBuilder(String.valueOf(remindValue))).toString();
      } else {
        if (temp.indexOf("`~`~`") >= 0)
          temp = temp.split("`~`~`")[0]; 
        remindValue = String.valueOf(remindValue) + temp;
      } 
    } 
    return remindValue;
  }
}
