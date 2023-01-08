package com.js.oa.jsflow.action;

import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.util.ProcessSubmit;
import com.js.oa.jsflow.vo.WorkVO;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WorkFlowOperSubmitAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    String mainOperProcUser, mainUserField, str[];
    WorkFlowFormActionForm workFlowFormActionForm = (WorkFlowFormActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    String updateValue = httpServletRequest.getParameter("updateValue");
    String comment = httpServletRequest.getParameter("comment");
    String mainAllowStandFor = httpServletRequest.getParameter("mainAllowStandFor");
    String mainAllowCancel = httpServletRequest.getParameter("mainAllowCancel");
    String curActivityId = httpServletRequest.getParameter("curActivityId");
    String curActivityName = httpServletRequest.getParameter("curActivityName");
    String mainNextActivityId = httpServletRequest.getParameter("mainNextActivityId");
    String mainNextActivityName = httpServletRequest.getParameter("mainNextActivityName");
    String processName = httpServletRequest.getParameter("processName");
    String tableId = httpServletRequest.getParameter("tableId");
    String recordId = httpServletRequest.getParameter("recordId");
    String submitPersonId = httpServletRequest.getParameter("submitPersonId");
    String submitPerson = httpServletRequest.getParameter("submitPerson");
    String workId = httpServletRequest.getParameter("workId");
    String remindFieldValue = httpServletRequest.getParameter("remindFieldValue");
    HttpSession httpSession = httpServletRequest.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String stepCount = httpServletRequest.getParameter("stepCount");
    String curTransactType = httpServletRequest.getParameter("curTransactType");
    WorkFlowBD workFlowBD = new WorkFlowBD();
    String tableName = workFlowBD.getTableName(tableId);
    if (!updateValue.equals("")) {
      ArrayList<String> alist = new ArrayList();
      alist.add("update " + tableName + " set " + 
          updateValue.substring(0, updateValue.length() - 1) + 
          " where " + tableName + "_id = " + recordId);
      workFlowBD.updateTable(alist);
    } 
    String isStandForWork = httpServletRequest.getParameter("isStandForWork");
    String standForUserId = httpServletRequest.getParameter("standForUserId");
    String standForUserName = httpServletRequest.getParameter("standForUserName");
    String groupField = httpServletRequest.getParameter("groupField");
    if (groupField != null && !groupField.equals(""))
      workFlowBD.updateChildTable(tableId, recordId, groupField); 
    WorkFlowBD newWorkFlowBD = new WorkFlowBD();
    String[] mainTransactUser = { "" };
    int mainUserType = Integer.parseInt(httpServletRequest.getParameter("mainUserType"));
    switch (mainUserType) {
      case 0:
        mainTransactUser = httpServletRequest.getParameterValues("mainCandidateUser");
        break;
      case 1:
        mainOperProcUser = httpServletRequest.getParameter("mainOperProcUser");
        if (mainOperProcUser != null && !mainOperProcUser.equals("")) {
          mainOperProcUser = mainOperProcUser.substring(1, mainOperProcUser.length() - 1);
          if (mainOperProcUser.indexOf("$$") >= 0) {
            mainTransactUser = mainOperProcUser.split("\\$\\$");
            break;
          } 
          mainTransactUser[0] = mainOperProcUser;
        } 
        break;
      case 2:
        mainTransactUser = httpServletRequest.getParameterValues("mainCandidateUser");
        break;
      case 3:
        mainTransactUser = httpServletRequest.getParameterValues("mainAllUser");
        break;
      case 4:
        mainUserField = httpServletRequest.getParameter("mainUserField");
        mainUserField = workFlowBD.getAFieldValue(tableName, mainUserField, (new StringBuilder(String.valueOf(recordId))).toString());
        if (mainUserField != null && !mainUserField.equals("")) {
          mainUserField = mainUserField.substring(1, mainUserField.length() - 1);
          if (mainUserField.indexOf("$$") >= 0) {
            mainTransactUser = mainUserField.split("\\$\\$");
            break;
          } 
          mainTransactUser[0] = mainUserField;
        } 
        break;
      case 5:
        mainTransactUser[0] = submitPersonId;
        break;
      case 6:
        mainTransactUser = newWorkFlowBD.getRoleUser(httpServletRequest.getParameter("mainPartRole"), submitPersonId);
        break;
    } 
    if (mainTransactUser == null || mainTransactUser.length == 0 || mainTransactUser[0] == null || mainTransactUser[0].equals("")) {
      httpServletRequest.setAttribute("mainSubmitFailed", "1");
      return actionMapping.findForward("success");
    } 
    (new ProcessSubmit()).sendMsg(httpServletRequest, mainTransactUser, 0);
    String mainPressType = httpServletRequest.getParameter("mainPressType");
    String mainDeadLineTime = "-1";
    String mainPressMotionTime = "-1";
    switch (Integer.parseInt(mainPressType)) {
      case 0:
        mainDeadLineTime = "-1";
        mainPressMotionTime = "-1";
        break;
      case 1:
        mainDeadLineTime = httpServletRequest.getParameter("mainDeadLineTime");
        mainPressMotionTime = httpServletRequest.getParameter("mainPressMotionTime");
        break;
      case 2:
        str = workFlowBD.getPress(mainNextActivityId, tableName, (new StringBuilder(String.valueOf(recordId))).toString());
        mainDeadLineTime = str[0];
        mainPressMotionTime = str[1];
        break;
    } 
    String mainNeedPassRound = "";
    String[] mainPassRoundUser = { "" };
    if (httpServletRequest.getParameter("mainNeedPassRound") != null) {
      String mainPassRoundUserStr, mainPassRoundUserField;
      mainNeedPassRound = httpServletRequest.getParameter("mainNeedPassRound");
      switch (Integer.parseInt(httpServletRequest.getParameter("mainPassRoundUserType"))) {
        case 0:
          mainPassRoundUser = httpServletRequest.getParameterValues("mainPassRoundCandUser");
          break;
        case 1:
          mainPassRoundUserStr = httpServletRequest.getParameter("mainPassRoundUser");
          if (mainPassRoundUserStr != null && !mainPassRoundUserStr.equals("")) {
            mainPassRoundUserStr = mainPassRoundUserStr.substring(1, mainPassRoundUserStr.length() - 1);
            if (mainPassRoundUserStr.indexOf("$$") >= 0) {
              mainPassRoundUser = mainPassRoundUserStr.split("\\$\\$");
              break;
            } 
            mainPassRoundUser[0] = mainPassRoundUserStr;
          } 
          break;
        case 2:
          mainPassRoundUser = httpServletRequest.getParameterValues("mainPassRoundCandUser");
          break;
        case 3:
          mainPassRoundUser = httpServletRequest.getParameterValues("mainPassRoundAllUser");
          break;
        case 4:
          mainPassRoundUserField = httpServletRequest.getParameter("mainPassRoundUserField");
          mainPassRoundUserField = workFlowBD.getAFieldValue(tableName, mainPassRoundUserField, (new StringBuilder(String.valueOf(recordId))).toString());
          if (mainPassRoundUserField != null && !mainPassRoundUserField.equals("")) {
            mainPassRoundUserField = mainPassRoundUserField.substring(1, mainPassRoundUserField.length() - 1);
            if (mainPassRoundUserField.indexOf("$$") >= 0) {
              mainPassRoundUser = mainPassRoundUserField.split("\\$\\$");
              break;
            } 
            mainPassRoundUser[0] = mainPassRoundUserField;
          } 
          break;
        case 5:
          mainPassRoundUser[0] = submitPersonId;
          break;
        case 6:
          mainPassRoundUser = newWorkFlowBD.getRoleUser(httpServletRequest.getParameter("mainPassRole"), submitPersonId);
          break;
      } 
      (new ProcessSubmit()).sendMsg(httpServletRequest, mainPassRoundUser, 1);
    } 
    String activityClass = httpServletRequest.getParameter("activityClass");
    String subProcType = "", subProcWorkId = "0";
    if (activityClass.equals("0")) {
      String subProcTableId = httpServletRequest.getParameter("subProcTableId");
      String subProcTableName = workFlowBD.getTableName(subProcTableId);
      String subRemindField = httpServletRequest.getParameter("subRemindField");
      String[] fieldName = httpServletRequest.getParameterValues("fieldName");
      StringBuffer fieldBuffer = new StringBuffer();
      StringBuffer valueBuffer = new StringBuffer();
      StringBuffer subRemindValue = new StringBuffer();
      ArrayList<String> childTableList = new ArrayList();
      ArrayList<String> childFieldList = new ArrayList();
      ArrayList<ArrayList<String[]>> childFieldValueList = new ArrayList();
      String fieldNameStr = "";
      for (int i = 0; i < fieldName.length; i++) {
        fieldBuffer.append(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + ",");
        if (fieldName[i].endsWith("_text")) {
          fieldNameStr = fieldName[i].substring(0, fieldName[i].indexOf("_"));
          valueBuffer.append("'" + httpServletRequest.getParameter(fieldNameStr).replace('\'', '‘') + "',");
          if (subRemindField.indexOf("S" + fieldNameStr + "S") >= 0)
            if (httpServletRequest.getParameter("remindText_" + fieldNameStr) != null) {
              String remindStr = httpServletRequest.getParameter("remindText_" + fieldNameStr);
              if (remindStr.endsWith(";"))
                remindStr = remindStr.substring(0, remindStr.length() - 1); 
              String[] remindValue = remindStr.split(";");
              for (int k = 0; k < remindValue.length; k++) {
                if (remindValue[k].substring(0, remindValue[k].indexOf("/")).equals(httpServletRequest.getParameter(fieldNameStr)))
                  subRemindValue.append(remindValue[k].substring(remindValue[k].indexOf("/") + 1)); 
              } 
            } else {
              subRemindValue.append(httpServletRequest.getParameter(fieldNameStr));
            }  
        } else if (fieldName[i].endsWith("_checkbox")) {
          fieldNameStr = fieldName[i].substring(0, fieldName[i].indexOf("_"));
          String[] tmp = httpServletRequest.getParameterValues(fieldNameStr);
          if (tmp != null) {
            String tmp2 = "";
            for (int j = 0; j < tmp.length; j++)
              tmp2 = String.valueOf(tmp2) + tmp[j] + ","; 
            valueBuffer.append("'" + tmp2.substring(0, tmp2.length() - 1) + "',");
            if (subRemindField.indexOf("S" + fieldNameStr + "S") >= 0)
              if (httpServletRequest.getParameter("remindCheckBox_" + fieldNameStr) != null) {
                String remindStr = httpServletRequest.getParameter("remindCheckBox_" + fieldNameStr);
                if (remindStr.endsWith(";"))
                  remindStr = remindStr.substring(0, remindStr.length() - 1); 
                String[] remindValue = remindStr.split(";");
                for (int k = 0; k < remindValue.length; k++) {
                  for (int m = 0; m < tmp.length; m++) {
                    if (tmp[m].equals(remindValue[k].substring(0, remindValue[k].indexOf("/")))) {
                      subRemindValue.append(remindValue[k].substring(remindValue[k].indexOf("/") + 1));
                      break;
                    } 
                  } 
                } 
              } else {
                subRemindValue.append(tmp2.substring(0, tmp2.length() - 1));
              }  
          } else {
            valueBuffer.append("'',");
          } 
        } else if (fieldName[i].endsWith("_time")) {
          valueBuffer.append("'" + httpServletRequest.getParameter(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_hour"));
          valueBuffer.append(":" + httpServletRequest.getParameter(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_minute") + "',");
          if (subRemindField.indexOf("S" + fieldName[i].substring(0, fieldName[i].indexOf("_")) + "S") >= 0) {
            subRemindValue.append(httpServletRequest.getParameter(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_hour"));
            subRemindValue.append(":" + httpServletRequest.getParameter(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_minute"));
          } 
        } else if (fieldName[i].endsWith("_date")) {
          valueBuffer.append("'" + httpServletRequest.getParameter(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_day") + " ");
          valueBuffer.append(String.valueOf(httpServletRequest.getParameter(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_hour")) + ":");
          valueBuffer.append(String.valueOf(httpServletRequest.getParameter(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_minute")) + "',");
          if (subRemindField.indexOf("S" + fieldName[i].substring(0, fieldName[i].indexOf("_")) + "S") >= 0) {
            subRemindValue.append(String.valueOf(httpServletRequest.getParameter(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_day")) + " ");
            subRemindValue.append(String.valueOf(httpServletRequest.getParameter(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_hour")) + ":");
            subRemindValue.append(httpServletRequest.getParameter(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_minute"));
          } 
        } else if (fieldName[i].endsWith("_file")) {
          String[] fileName = httpServletRequest.getParameterValues(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_fileName");
          String[] saveName = httpServletRequest.getParameterValues(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_saveName");
          String tmp = "";
          if (fileName != null) {
            for (int j = 0; j < fileName.length; j++)
              tmp = String.valueOf(tmp) + fileName[j] + ":" + saveName[j] + "::"; 
            tmp = tmp.substring(0, tmp.length() - 2);
          } 
          valueBuffer.append("'" + tmp + "',");
        } else if (fieldName[i].endsWith("_group")) {
          valueBuffer.append("'0',");
          String name = fieldName[i].substring(0, fieldName[i].indexOf("_"));
          String childTableName = httpServletRequest.getParameter("childTable_" + name);
          childTableList.add(childTableName);
          String childFieldName = httpServletRequest.getParameter("childField_" + name);
          childFieldName = childFieldName.replace(';', ',');
          childFieldList.add(childFieldName);
          ArrayList<String[]> childFieldValue = new ArrayList();
          String[] childFieldNameArray = childFieldName.split(",");
          if (childFieldNameArray != null && childFieldNameArray.length > 0 && 
            httpServletRequest.getParameterValues(childFieldNameArray[0]) != null) {
            int size = (httpServletRequest.getParameterValues(childFieldNameArray[0])).length;
            int j;
            for (j = 0; j < size; j++)
              childFieldValue.add(new String[childFieldNameArray.length]); 
            for (j = 0; j < childFieldNameArray.length; j++) {
              String[] tmp = httpServletRequest.getParameterValues(childFieldNameArray[j]);
              for (int k = 0; k < tmp.length; k++) {
                String[] loadStr = childFieldValue.get(k);
                loadStr[j] = tmp[k];
                childFieldValue.set(k, loadStr);
              } 
            } 
            childFieldValueList.add(childFieldValue);
          } 
        } else {
          valueBuffer.append("'',");
        } 
      } 
      int subRecordId = workFlowBD.insertFormRecord(subProcTableName, 
          fieldBuffer.substring(0, fieldBuffer.length() - 1).toString(), 
          valueBuffer.substring(0, valueBuffer.length() - 1).toString(), 
          childTableList, 
          childFieldList, 
          childFieldValueList);
      WorkVO workVO = new WorkVO();
      String subProcessType = httpServletRequest.getParameter("subProcessType");
      workVO.setWorkType(Integer.parseInt(subProcessType));
      String subProcName = httpServletRequest.getParameter("subProcName");
      workVO.setFileType(subProcName);
      workVO.setRemindValue(subRemindValue.toString());
      workVO.setSelfMainLinkFile("/jsoa/WorkFlowProcAction.do?q=1");
      workVO.setToMainLinkFile("/jsoa/WorkFlowProcAction.do?q=1");
      workVO.setSubmitPerson(httpSession.getAttribute("userName").toString());
      workVO.setSubmitEmployeeId(new Long(httpSession.getAttribute("userId").toString()));
      String subProcId = httpServletRequest.getParameter("subProcId");
      workVO.setProcessId(new Long(subProcId));
      workVO.setTableId(new Long(subProcTableId));
      workVO.setRecordId(new Long(subRecordId));
      workVO.setCreatorCancelLink("window.open('/jsoa/jsflow/workflow_cancelReason.jsp?workStatus=1&workId=workIdValue&tableId=" + subProcTableId + "&processName=" + subProcName + "&recordId=" + subRecordId + "&processId=" + subProcId + "&remindValue=" + subRemindValue.toString() + "','','TOP=0,LEFT=0,scrollbars=no,resizable=no,width=480,height=250')");
      workVO.setIsSubProcWork("1");
      workVO.setPareProcActiId(curActivityId);
      workVO.setPareStepCount(stepCount);
      workVO.setPareTableId(tableId);
      workVO.setPareRecordId(recordId);
      workVO.setPareProcNextActiId(mainNextActivityId);
      workVO.setSubmitOrg(httpServletRequest.getSession(true).getAttribute("orgName").toString());
      String[] toUser = { "" };
      if (subProcessType.equals("0")) {
        toUser[0] = httpServletRequest.getParameter("randomProcUser");
      } else {
        String operProcUser;
        workVO.setActivity(new Long(httpServletRequest.getParameter("activityId")));
        workVO.setCurStep(httpServletRequest.getParameter("activityName"));
        workVO.setAllowStandFor(Integer.parseInt(httpServletRequest.getParameter("allowStandFor")));
        workVO.setPressType(Integer.parseInt(httpServletRequest.getParameter("pressType")));
        workVO.setDeadLine(httpServletRequest.getParameter("deadLineTime"));
        workVO.setPressTime(httpServletRequest.getParameter("pressTime"));
        workVO.setUserType(Integer.parseInt(httpServletRequest.getParameter("userType")));
        switch (Integer.parseInt(httpServletRequest.getParameter("userType"))) {
          case 1:
            operProcUser = httpServletRequest.getParameter("operProcUser");
            if (operProcUser != null && !operProcUser.equals("")) {
              operProcUser = operProcUser.substring(1, operProcUser.length() - 1);
              if (operProcUser.indexOf("$$") >= 0) {
                toUser = operProcUser.split("\\$\\$");
                break;
              } 
              toUser[0] = operProcUser;
            } 
            break;
          case 2:
            toUser = httpServletRequest.getParameterValues("candidateUser");
            break;
          case 3:
            toUser = httpServletRequest.getParameterValues("allUser");
            break;
          case 5:
            toUser[0] = userId;
            break;
          case 6:
            toUser = workFlowBD.getRoleUser(httpServletRequest.getParameter("partRole"), userId);
            break;
        } 
        (new ProcessSubmit()).sendMsg(httpServletRequest, toUser, 0);
        workVO.setPressType(Integer.parseInt(httpServletRequest.getParameter("pressType")));
        int pressType = Integer.parseInt(httpServletRequest.getParameter("pressType"));
        switch (pressType) {
          case 0:
            workVO.setDeadLine("-1");
            workVO.setPressTime("-1");
            break;
          case 1:
            workVO.setDeadLine(httpServletRequest.getParameter("deadLineTime"));
            workVO.setPressTime(httpServletRequest.getParameter("pressMotionTime"));
            break;
        } 
      } 
      if (httpServletRequest.getParameter("needPassRound") != null) {
        String passRoundUser;
        workVO.setNeedPassRound(true);
        int passRoundUserType = Integer.parseInt(httpServletRequest.getParameter("passRoundUserType"));
        workVO.setPassRoundUserType(passRoundUserType);
        String[] passUser = { "" };
        switch (passRoundUserType) {
          case 1:
            passRoundUser = httpServletRequest.getParameter("passRoundUser");
            if (passRoundUser != null && !passRoundUser.equals("")) {
              passRoundUser = passRoundUser.substring(1, passRoundUser.length() - 1);
              if (passRoundUser.indexOf("$$") >= 0) {
                passUser = passRoundUser.split("\\$\\$");
                break;
              } 
              passUser[0] = passRoundUser;
            } 
            break;
          case 2:
            passUser = httpServletRequest.getParameterValues("passRoundCandUser");
            break;
          case 3:
            passUser = httpServletRequest.getParameterValues("passRoundAllUser");
            break;
          case 5:
            passUser[0] = userId;
            break;
          case 6:
            passUser = workFlowBD.getRoleUser(httpServletRequest.getParameter("passRole"), userId);
            break;
        } 
        workVO.setPassRoundUser(passUser);
        (new ProcessSubmit()).sendMsg(httpServletRequest, passUser, 1);
      } else {
        workVO.setNeedPassRound(false);
      } 
      ProcessSubmit procSubmit = new ProcessSubmit();
      String[] arrayOfString1 = new String[0];
      subProcWorkId = (new StringBuilder(String.valueOf(procSubmit.newProcSubmit(workVO, toUser, "1", arrayOfString1)))).toString();
      workFlowBD.insertCurFieldStr(subProcId, subProcTableId, (new StringBuilder(String.valueOf(subRecordId))).toString(), httpServletRequest.getParameter("curFieldStr"));
      if (subProcWorkId.equals("-1000")) {
        httpServletRequest.setAttribute("subSubmitFailed", "1");
        return actionMapping.findForward("success");
      } 
      subProcType = httpServletRequest.getParameter("subProcType");
    } 
    String[] dealWithPara = { 
        tableId, recordId, curActivityName, curActivityId, userId, comment, 
        mainNextActivityName, mainNextActivityId, stepCount, isStandForWork, 
        standForUserId, activityClass, subProcWorkId };
    newWorkFlowBD.insertDealWith(dealWithPara);
    String toMainFile = "/jsoa/WorkFlowProcAction.do?pp=1";
    String[] para = { 
        mainNextActivityName, mainNextActivityId, workId, mainDeadLineTime, mainPressMotionTime, 
        curActivityId, mainAllowCancel, stepCount, remindFieldValue, curTransactType, 
        toMainFile, mainAllowStandFor, isStandForWork, userId, standForUserId, standForUserName, 
        activityClass, subProcType, "" };
    newWorkFlowBD.operateWork(para, mainTransactUser, mainNeedPassRound, mainPassRoundUser);
    httpServletRequest.setAttribute("search", httpServletRequest.getParameter("search"));
    httpServletRequest.setAttribute("workTitle", httpServletRequest.getParameter("workTitle"));
    return actionMapping.findForward("success");
  }
}
