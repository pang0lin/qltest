package com.js.oa.jsflow.action;

import com.js.oa.eform.service.CustomFormBD;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.util.ParseWorkFlowForm;
import com.js.oa.jsflow.util.ProcessSubmit;
import com.js.oa.jsflow.vo.WorkVO;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.util.ConversionString;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WorkFlowFormAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    WorkFlowFormActionForm workFlowFormActionForm = (WorkFlowFormActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    String myAction = httpServletRequest.getParameter("myAction");
    if (myAction == null)
      myAction = "newForm"; 
    String processId = httpServletRequest.getParameter("processId");
    String processName = httpServletRequest.getParameter("processName");
    String tableId = httpServletRequest.getParameter("tableId");
    String processType = httpServletRequest.getParameter("processType");
    String remindField = httpServletRequest.getParameter("remindField");
    String moduleId = httpServletRequest.getParameter("moduleId");
    httpServletRequest.setAttribute("moduleId", moduleId);
    String tag = "newForm";
    if (myAction.equals("newForm") || myAction.equals("draftForm")) {
      httpServletRequest.setAttribute("processId", processId);
      httpServletRequest.setAttribute("tableId", tableId);
      httpServletRequest.setAttribute("processType", processType);
      httpServletRequest.setAttribute("processName", processName);
      httpServletRequest.setAttribute("remindField", remindField);
      httpServletRequest.setAttribute("formType", httpServletRequest.getParameter("formType"));
      httpServletRequest.setAttribute("jspFile", httpServletRequest.getParameter("jspFile"));
      httpServletRequest.setAttribute("infoId", httpServletRequest.getParameter("record"));
      httpServletRequest.setAttribute("recordId", httpServletRequest.getParameter("record"));
      ProcessBD processBD = new ProcessBD();
      List noWriteField = processBD.getNoWriteField(processId);
      httpServletRequest.setAttribute("noWriteField", noWriteField);
      ParseWorkFlowForm parseWorkFlowForm = new ParseWorkFlowForm();
      String code = (new CustomFormBD()).getCode(tableId);
      if (code == null || code.toUpperCase().equals("NULL") || code.trim().length() < 1) {
        String[][] realTable = (new CustomFormBD()).getTableIDAndName(tableId);
        httpServletRequest.setAttribute("formContent", parseWorkFlowForm.parseForm((realTable == null || realTable.length < 1) ? "-1" : realTable[0][0], httpServletRequest, noWriteField));
      } else {
        httpServletRequest.setAttribute("formContent", "");
      } 
    } else if (myAction.equals("submitForm")) {
      tag = "close";
      WorkFlowBD workFlowBD = new WorkFlowBD();
      String tableName = workFlowBD.getTableName(tableId);
      String[] fieldName = httpServletRequest.getParameterValues("fieldName");
      StringBuffer fieldBuffer = new StringBuffer();
      StringBuffer valueBuffer = new StringBuffer();
      ArrayList<String> childTableList = new ArrayList();
      ArrayList<String> childFieldList = new ArrayList();
      ArrayList<ArrayList<String[]>> childFieldValueList = new ArrayList();
      StringBuffer remindFieldValue = new StringBuffer();
      String fieldNameStr = "";
      String tmpValue = "";
      for (int i = 0; i < fieldName.length; i++) {
        fieldBuffer.append(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + ",");
        if (fieldName[i].endsWith("_text")) {
          fieldNameStr = fieldName[i].substring(0, fieldName[i].indexOf("_"));
          tmpValue = httpServletRequest.getParameter(fieldNameStr).replace('"', '\'');
          tmpValue = tmpValue.replaceAll("'", "''");
          valueBuffer.append("'" + tmpValue + "',");
          if (remindField.indexOf("S" + fieldNameStr + "S") >= 0)
            if (httpServletRequest.getParameter("remindText_" + fieldNameStr) != null) {
              String remindStr = httpServletRequest.getParameter("remindText_" + fieldNameStr);
              if (remindStr.endsWith(";"))
                remindStr = remindStr.substring(0, remindStr.length() - 1); 
              String[] remindValue = remindStr.split(";");
              for (int k = 0; k < remindValue.length; k++) {
                if (remindValue[k].substring(0, remindValue[k].indexOf("/")).equals(httpServletRequest.getParameter(fieldNameStr)))
                  remindFieldValue.append(remindValue[k].substring(remindValue[k].indexOf("/") + 1)); 
              } 
            } else {
              remindFieldValue.append(httpServletRequest.getParameter(fieldNameStr));
            }  
        } else if (fieldName[i].endsWith("_checkbox")) {
          fieldNameStr = fieldName[i].substring(0, fieldName[i].indexOf("_"));
          String[] tmp = httpServletRequest.getParameterValues(fieldNameStr);
          if (tmp != null) {
            String tmp2 = "";
            for (int j = 0; j < tmp.length; j++)
              tmp2 = String.valueOf(tmp2) + tmp[j] + ","; 
            valueBuffer.append("'" + tmp2.substring(0, tmp2.length() - 1) + "',");
            if (remindField.indexOf("S" + fieldNameStr + "S") >= 0)
              if (httpServletRequest.getParameter("remindCheckBox_" + fieldNameStr) != null) {
                String remindStr = httpServletRequest.getParameter("remindCheckBox_" + fieldNameStr);
                if (remindStr.endsWith(";"))
                  remindStr = remindStr.substring(0, remindStr.length() - 1); 
                String[] remindValue = remindStr.split(";");
                for (int k = 0; k < remindValue.length; k++) {
                  for (int m = 0; m < tmp.length; m++) {
                    if (tmp[m].equals(remindValue[k].substring(0, remindValue[k].indexOf("/")))) {
                      remindFieldValue.append(remindValue[k].substring(remindValue[k].indexOf("/") + 1));
                      break;
                    } 
                  } 
                } 
              } else {
                remindFieldValue.append(tmp2.substring(0, tmp2.length() - 1));
              }  
          } else {
            valueBuffer.append("'',");
          } 
        } else if (fieldName[i].endsWith("_time")) {
          valueBuffer.append("'" + httpServletRequest.getParameter(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_hour"));
          valueBuffer.append(":" + httpServletRequest.getParameter(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_minute") + "',");
          if (remindField.indexOf("S" + fieldName[i].substring(0, fieldName[i].indexOf("_")) + "S") >= 0) {
            remindFieldValue.append(httpServletRequest.getParameter(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_hour"));
            remindFieldValue.append(":" + httpServletRequest.getParameter(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_minute"));
          } 
        } else if (fieldName[i].endsWith("_date")) {
          valueBuffer.append("'" + httpServletRequest.getParameter(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_day") + " ");
          valueBuffer.append(String.valueOf(httpServletRequest.getParameter(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_hour")) + ":");
          valueBuffer.append(String.valueOf(httpServletRequest.getParameter(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_minute")) + "',");
          if (remindField.indexOf("S" + fieldName[i].substring(0, fieldName[i].indexOf("_")) + "S") >= 0) {
            remindFieldValue.append(String.valueOf(httpServletRequest.getParameter(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_day")) + " ");
            remindFieldValue.append(String.valueOf(httpServletRequest.getParameter(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_hour")) + ":");
            remindFieldValue.append(httpServletRequest.getParameter(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_minute"));
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
      HttpSession httpSession = httpServletRequest.getSession(true);
      String userId = httpSession.getAttribute("userId").toString();
      String userName = httpSession.getAttribute("userName").toString();
      String orgName = httpSession.getAttribute("orgName").toString();
      int recordId = workFlowBD.insertFormRecord(tableName, 
          fieldBuffer.substring(0, fieldBuffer.length() - 1).toString(), 
          valueBuffer.substring(0, valueBuffer.length() - 1).toString(), 
          childTableList, 
          childFieldList, 
          childFieldValueList);
      WorkVO workVO = new WorkVO();
      workVO.setWorkType(Integer.parseInt(processType));
      workVO.setFileType(processName);
      workVO.setRemindValue(remindFieldValue.toString());
      workVO.setSelfMainLinkFile("/jsoa/WorkFlowProcAction.do?flowpara=1");
      workVO.setToMainLinkFile("/jsoa/WorkFlowProcAction.do?flowpara=1");
      workVO.setSubmitPerson(userName);
      workVO.setSubmitEmployeeId(new Long(userId));
      workVO.setSubmitOrg(orgName);
      workVO.setProcessId(new Long(processId));
      workVO.setTableId(new Long(tableId));
      workVO.setRecordId(new Long(recordId));
      workVO.setCreatorCancelLink("window.open('/jsoa/jsflow/workflow_cancelReason.jsp?workStatus=1&workId=workIdValue&tableId=" + tableId + "&processName=" + processName + "&recordId=" + recordId + "&processId=" + processId + "&remindValue=" + remindFieldValue.toString() + "','','TOP=0,LEFT=0,scrollbars=no,resizable=no,width=480,height=250')");
      workVO.setIsSubProcWork("0");
      workVO.setPareProcActiId("0");
      workVO.setPareStepCount("0");
      workVO.setPareTableId("0");
      workVO.setPareRecordId("0");
      workVO.setPareProcNextActiId("0");
      String relProjectId = httpServletRequest.getParameter("relproject");
      if (relProjectId != null && !"null".equals(relProjectId) && !"".equals(relProjectId)) {
        workVO.setRelProjectId(Long.valueOf(relProjectId));
      } else {
        workVO.setRelProjectId(Long.valueOf(-1L));
      } 
      String[] toUser = { "" };
      if (processType.equals("0")) {
        workVO.setDeadLine("-1");
        workVO.setPressTime("-1");
        workVO.setCurStep("");
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
          case 0:
            toUser = httpServletRequest.getParameterValues("candidateUser");
            break;
          case 1:
            operProcUser = httpServletRequest.getParameter("operProcUser");
            if (operProcUser != null && !operProcUser.equals("")) {
              ConversionString con = new ConversionString(operProcUser);
              String userIdStr = String.valueOf(con.getUserIdString()) + ",";
              userIdStr = String.valueOf(userIdStr) + getUserByGroup(con.getGroupIdString()) + ",";
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
                  toUser = dbopt.executeQueryToStrArr1("select EMP_ID from ORG_EMPLOYEE where USERISACTIVE=1 and USERISDELETED<>1 and EMP_ID in (" + userIdStr + ")");
                } else {
                  toUser = new String[0];
                } 
                dbopt.close();
              } catch (Exception e) {
                try {
                  dbopt.close();
                } catch (SQLException sQLException) {}
                toUser = new String[0];
              } 
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
          case 0:
            passUser = httpServletRequest.getParameterValues("passRoundCandUser");
            break;
          case 1:
            passRoundUser = httpServletRequest.getParameter("passRoundUser");
            if (passRoundUser != null && !passRoundUser.equals("")) {
              ConversionString con = new ConversionString(passRoundUser);
              String userIdStr = String.valueOf(con.getUserIdString()) + ",";
              userIdStr = String.valueOf(userIdStr) + getUserByGroup(con.getGroupIdString()) + ",";
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
                  passUser = dbopt.executeQueryToStrArr1("select EMP_ID from ORG_EMPLOYEE where USERISACTIVE=1 and USERISDELETED<>1 and EMP_ID in (" + userIdStr + ")");
                } else {
                  passUser = new String[0];
                } 
                dbopt.close();
              } catch (Exception e) {
                try {
                  dbopt.close();
                } catch (SQLException sQLException) {}
                passUser = new String[0];
              } 
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
      String resubmitWorkId = "";
      if (httpServletRequest.getParameter("resubmitWorkId") != null)
        resubmitWorkId = httpServletRequest.getParameter("resubmitWorkId"); 
      workFlowBD.insertCurFieldStr(processId, tableId, (new StringBuilder(String.valueOf(recordId))).toString(), httpServletRequest.getParameter("curFieldStr"));
      ProcessSubmit procSubmit = new ProcessSubmit();
      long submitSuccess = 0L;
      String[] para = new String[0];
      submitSuccess = procSubmit.newProcSubmit(workVO, toUser, moduleId, para);
      if (submitSuccess == -1000L)
        httpServletRequest.setAttribute("submitFailed", "1"); 
    } 
    return actionMapping.findForward(tag);
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
        String orgCode = dbopt.executeQueryToStr("select ORGIDSTRING from ORG_ORGANIZATION where ORG_ID=" + orgIdArr[i]);
        rs = dbopt.executeQuery("select EMP_ID from ORG_ORGANIZATION_USER where ORG_ID in (select ORG_ID from ORG_ORGANIZATION where ORGIDSTRING like '%" + orgCode + "%')");
        if (rs != null) {
          while (rs.next()) {
            Object empId = rs.getObject(1);
            if (empId != null && orgIds.indexOf(empId.toString()) < 0)
              orgIds = String.valueOf(orgIds) + empId.toString() + ","; 
          } 
          rs.close();
        } 
      } 
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } finally {}
    return orgIds;
  }
  
  public String getUserByGroup(String groupIdStr) {
    String userStr = "";
    if (groupIdStr == null || groupIdStr.length() < 1)
      return userStr; 
    String[] groupIdArr = groupIdStr.split(",");
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      for (int i = 0; i < groupIdArr.length; i++) {
        String empIdStr = dbopt.executeQueryToStr("select GROUPUSERSTRING from ORG_GROUP where GROUP_ID=" + groupIdArr[i]);
        if (empIdStr != null && empIdStr.length() > 1)
          userStr = String.valueOf(userStr) + empIdStr; 
      } 
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } finally {}
    if (userStr != null && userStr.length() > 1)
      userStr = userStr.substring(1, userStr.length() - 1).replaceAll("$$", ","); 
    return userStr;
  }
}
