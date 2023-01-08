package com.js.oa.jsflow.util;

import bsh.Interpreter;
import com.js.oa.eform.service.CustomFormBD;
import com.js.oa.form.pengchi.GetPersoninfo;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.service.WorkFlowButtonBD;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.jsflow.vo.ActivityVO;
import com.js.util.config.SystemCommon;
import com.js.util.util.CharacterTool;
import com.js.util.util.DataSourceBase;
import com.js.util.util.IO2File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class WorkflowCommon {
  public ActivityVO getFirstActivity(HttpServletRequest httpServletRequest) {
    ProcessBD newProcessBD = new ProcessBD();
    ActivityVO activityVO = newProcessBD.getFirstActivity((httpServletRequest.getAttribute("processId") == null || httpServletRequest.getAttribute("processId").toString().toUpperCase().equals("NULL")) ? httpServletRequest.getParameter("processId") : httpServletRequest.getAttribute("processId").toString());
    if (activityVO != null && activityVO.getId() != 0L && 
      activityVO.getActivityClass() == 2)
      activityVO = getNextActivity((new StringBuilder(String.valueOf(activityVO.getId()))).toString(), httpServletRequest); 
    return activityVO;
  }
  
  public ActivityVO getFirstActivityInfo(HttpServletRequest httpServletRequest) {
    ProcessBD newProcessBD = new ProcessBD();
    ActivityVO activityVO = newProcessBD.getFirstActivity((httpServletRequest.getAttribute("processId") == null || httpServletRequest.getAttribute("processId").toString().toUpperCase().equals("NULL")) ? httpServletRequest.getParameter("processId") : httpServletRequest.getAttribute("processId").toString());
    return activityVO;
  }
  
  public ActivityVO getNextActivity(String fatherActivityId, HttpServletRequest request) {
    WorkFlowBD workFlowBD = new WorkFlowBD();
    String nextActivityId = getNextActivityIdWithExp(workFlowBD.getFirstNextActi(fatherActivityId), request, "", "");
    if ("0".equals(nextActivityId))
      nextActivityId = workFlowBD.getNextDefaultActivityId(fatherActivityId); 
    ActivityVO activityVO = null;
    if (nextActivityId != null && !nextActivityId.equals("0") && !nextActivityId.equals("")) {
      activityVO = workFlowBD.getFirstProcActiVO(nextActivityId);
      if (activityVO.getActivityClass() == 2)
        activityVO = getNextActivity((new StringBuilder(String.valueOf(activityVO.getId()))).toString(), request); 
    } 
    return activityVO;
  }
  
  public String getNextActivityIdWithExp(List<String[]> nextActivityList, HttpServletRequest request, String tableId, String recordId) {
    if (tableId.equals(""))
      tableId = (request.getParameter("tableId") == null) ? "" : request.getParameter("tableId"); 
    if (recordId.equals(""))
      recordId = (request.getParameter("recordId") == null) ? "" : request.getParameter("recordId"); 
    String nextActivityId = "0";
    String submitPersonId = "";
    String curEmployeeId = (request.getSession(true).getAttribute("userId") == null) ? "" : request.getSession(true).getAttribute("userId").toString();
    if (!tableId.equals("") && !recordId.equals("")) {
      Connection conn = null;
      Statement stmt = null;
      try {
        conn = (new DataSourceBase()).getDataSource().getConnection();
        stmt = conn.createStatement();
      } catch (Exception exception) {}
      try {
        ResultSet rs = stmt.executeQuery("Select wf_submitemployee_id From JSF_WORK where worktable_id=" + tableId + " and workrecord_id=" + recordId);
        if (rs.next())
          submitPersonId = rs.getString("wf_submitemployee_id"); 
        rs.close();
      } catch (Exception exception) {}
      if (conn != null && stmt != null)
        try {
          stmt.close();
          conn.close();
        } catch (Exception exception) {} 
    } 
    if (nextActivityList != null && nextActivityList.size() > 0) {
      String[] tmp = (String[])null;
      ProcessStep proStep = new ProcessStep();
      for (int j = 0; j < nextActivityList.size(); j++) {
        tmp = nextActivityList.get(j);
        if (tmp[4] != null && !"".equals(tmp[4]) && !"null".equals(tmp[4])) {
          StringBuffer expression = new StringBuffer(256);
          String[] exps = tmp[4].split("\\@\\$\\@");
          for (int i = 0; i < exps.length; i++) {
            if (!"".equals(exps[i])) {
              String expTemp = exps[i];
              String leftTag = expTemp.substring(0, expTemp.indexOf("$:$"));
              expTemp = expTemp.substring(expTemp.indexOf("$:$") + 3);
              String fieldName = expTemp.substring(0, expTemp.indexOf("$:$"));
              expTemp = expTemp.substring(expTemp.indexOf("$:$") + 3);
              String oprate = expTemp.substring(0, expTemp.indexOf("$:$"));
              expTemp = expTemp.substring(expTemp.indexOf("$:$") + 3);
              String oprateValue = expTemp.substring(0, expTemp.indexOf("$:$"));
              expTemp = expTemp.substring(expTemp.indexOf("$:$") + 3);
              String rightTag = expTemp.substring(0, expTemp.indexOf("$:$"));
              String nextTag = expTemp.substring(expTemp.indexOf("$:$") + 3);
              expression.append(leftTag);
              if ("-1".equals(fieldName)) {
                if (submitPersonId.equals("")) {
                  HttpSession session = request.getSession(true);
                  submitPersonId = session.getAttribute("userId").toString();
                } 
                if (oprate.equals("<>")) {
                  if (!getEmpDuty(submitPersonId).equals(oprateValue)) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("like")) {
                  if (getEmpDuty(submitPersonId).indexOf(oprateValue) >= 0) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("not like")) {
                  if (getEmpDuty(submitPersonId).indexOf(oprateValue) >= 0) {
                    expression.append("false");
                  } else {
                    expression.append("true");
                  } 
                } else if (getEmpDuty(submitPersonId).equals(oprateValue)) {
                  expression.append("true");
                } else {
                  expression.append("false");
                } 
              } else if ("-2".equals(fieldName)) {
                if (submitPersonId.equals(""))
                  if (SystemCommon.getCustomerName().equals("pengchi")) {
                    if (request.getParameter("submitName") != null && !request.getParameter("submitName").equals("null")) {
                      submitPersonId = GetPersoninfo.getOaUserId(request.getParameter("submitName").toString());
                    } else {
                      HttpSession session = request.getSession(true);
                      submitPersonId = session.getAttribute("userId").toString();
                    } 
                  } else {
                    HttpSession session = request.getSession(true);
                    submitPersonId = session.getAttribute("userId").toString();
                  }  
                String dutyLevel = getEmpDutyLevel(submitPersonId);
                if (oprate.equals("<>")) {
                  if (!dutyLevel.equals(oprateValue)) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("=")) {
                  if (dutyLevel.equals(oprateValue)) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("<")) {
                  try {
                    if (Float.parseFloat(dutyLevel) < 
                      Float.parseFloat(oprateValue)) {
                      expression.append("true");
                    } else {
                      expression.append("false");
                    } 
                  } catch (Exception e) {
                    expression.append("false");
                  } 
                } else if (oprate.equals("<=")) {
                  try {
                    if (Float.parseFloat(dutyLevel) <= 
                      Float.parseFloat(oprateValue)) {
                      expression.append("true");
                    } else {
                      expression.append("false");
                    } 
                  } catch (Exception e) {
                    expression.append("false");
                  } 
                } else if (oprate.equals(">")) {
                  try {
                    if (Float.parseFloat(dutyLevel) > 
                      Float.parseFloat(oprateValue)) {
                      expression.append("true");
                    } else {
                      expression.append("false");
                    } 
                  } catch (Exception e) {
                    expression.append("false");
                  } 
                } else if (oprate.equals(">=")) {
                  try {
                    if (Float.parseFloat(dutyLevel) >= 
                      Float.parseFloat(oprateValue)) {
                      expression.append("true");
                    } else {
                      expression.append("false");
                    } 
                  } catch (Exception e) {
                    expression.append("false");
                  } 
                } else if (oprate.equals("like")) {
                  if (dutyLevel.indexOf(oprateValue) >= 0) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("not like")) {
                  if (dutyLevel.indexOf(oprateValue) >= 0) {
                    expression.append("false");
                  } else {
                    expression.append("true");
                  } 
                } 
              } else if ("-3".equals(fieldName)) {
                Object object = request.getSession().getAttribute("dutyLevel");
                if (oprate.equals("<>")) {
                  if (!object.equals(oprateValue)) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("=")) {
                  if (object.equals(oprateValue)) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("<")) {
                  try {
                    if (Float.parseFloat((String)object) < 
                      Float.parseFloat(oprateValue)) {
                      expression.append("true");
                    } else {
                      expression.append("false");
                    } 
                  } catch (Exception e) {
                    expression.append("false");
                  } 
                } else if (oprate.equals("<=")) {
                  try {
                    if (Float.parseFloat((String)object) <= 
                      Float.parseFloat(oprateValue)) {
                      expression.append("true");
                    } else {
                      expression.append("false");
                    } 
                  } catch (Exception e) {
                    expression.append("false");
                  } 
                } else if (oprate.equals(">")) {
                  try {
                    if (Float.parseFloat((String)object) > 
                      Float.parseFloat(oprateValue)) {
                      expression.append("true");
                    } else {
                      expression.append("false");
                    } 
                  } catch (Exception e) {
                    expression.append("false");
                  } 
                } else if (oprate.equals(">=")) {
                  try {
                    if (Float.parseFloat((String)object) >= 
                      Float.parseFloat(oprateValue)) {
                      expression.append("true");
                    } else {
                      expression.append("false");
                    } 
                  } catch (Exception e) {
                    expression.append("false");
                  } 
                } else if (oprate.equals("like")) {
                  if (object.indexOf(oprateValue) >= 0) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("not like")) {
                  if (object.indexOf(oprateValue) >= 0) {
                    expression.append("false");
                  } else {
                    expression.append("true");
                  } 
                } 
              } else if ("-4".equals(fieldName)) {
                if (submitPersonId.equals("")) {
                  HttpSession session = request.getSession(true);
                  submitPersonId = session.getAttribute("userId").toString();
                } 
                String tmpRole = getEmpRoleString(submitPersonId);
                if (oprate.equals("<>")) {
                  if (tmpRole.indexOf(",," + oprateValue + ",,") < 0) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("like")) {
                  if (tmpRole.indexOf(oprateValue) >= 0) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("not like")) {
                  if (tmpRole.indexOf(oprateValue) >= 0) {
                    expression.append("false");
                  } else {
                    expression.append("true");
                  } 
                } else if (tmpRole.indexOf(",," + oprateValue + ",,") >= 0) {
                  expression.append("true");
                } else {
                  expression.append("false");
                } 
              } else if ("-5".equals(fieldName)) {
                String tmpPosition = getEmpPosition(curEmployeeId);
                if (oprate.equals("<>")) {
                  if (tmpPosition.indexOf(",," + oprateValue + ",,") < 0) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("like")) {
                  if (tmpPosition.indexOf(oprateValue) >= 0) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("not like")) {
                  if (tmpPosition.indexOf(oprateValue) >= 0) {
                    expression.append("false");
                  } else {
                    expression.append("true");
                  } 
                } else if (tmpPosition.indexOf(",," + oprateValue + ",,") >= 0) {
                  expression.append("true");
                } else {
                  expression.append("false");
                } 
              } else if ("-6".equals(fieldName)) {
                String tmpRole = "";
                tmpRole = getEmpRoleString(curEmployeeId);
                if (oprate.equals("<>")) {
                  if (tmpRole.indexOf(",," + oprateValue + ",,") < 0) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("like")) {
                  if (tmpRole.indexOf(oprateValue) >= 0) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("not like")) {
                  if (tmpRole.indexOf(oprateValue) >= 0) {
                    expression.append("false");
                  } else {
                    expression.append("true");
                  } 
                } else if (tmpRole.indexOf(",," + oprateValue + ",,") >= 0) {
                  expression.append("true");
                } else {
                  expression.append("false");
                } 
              } else if ("-7".equals(fieldName)) {
                String tmpPosition = "";
                tmpPosition = getEmpPosition(curEmployeeId);
                if (oprate.equals("<>")) {
                  if (tmpPosition.indexOf(",," + oprateValue + ",,") < 0) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("like")) {
                  if (tmpPosition.indexOf(oprateValue) >= 0) {
                    expression.append("true");
                  } else {
                    expression.append("false");
                  } 
                } else if (oprate.equals("not like")) {
                  if (tmpPosition.indexOf(oprateValue) >= 0) {
                    expression.append("false");
                  } else {
                    expression.append("true");
                  } 
                } else if (tmpPosition.indexOf(",," + oprateValue + ",,") >= 0) {
                  expression.append("true");
                } else {
                  expression.append("false");
                } 
              } else {
                String userValue = null;
                if (request != null)
                  userValue = (request.getParameter(fieldName) == null) ? request.getParameter(String.valueOf(fieldName) + "_Name") : request.getParameter(fieldName); 
                if (userValue == null || userValue.toUpperCase().equals("NULL"))
                  userValue = (new CustomFormBD()).getValue(fieldName, recordId, tableId); 
                IO2File.printFile("字段值判断：" + userValue + " " + oprate + " " + oprateValue, "流程节点条件判断");
                if (userValue != null)
                  if (oprate.equals("<")) {
                    try {
                      if (Float.parseFloat(userValue) < 
                        Float.parseFloat(oprateValue)) {
                        expression.append("true");
                      } else {
                        expression.append("false");
                      } 
                    } catch (Exception e) {
                      expression.append("false");
                    } 
                  } else if (oprate.equals("<=")) {
                    try {
                      if (Float.parseFloat(userValue) <= 
                        Float.parseFloat(oprateValue)) {
                        expression.append("true");
                      } else {
                        expression.append("false");
                      } 
                    } catch (Exception e) {
                      expression.append("false");
                    } 
                  } else if (oprate.equals("<>")) {
                    if (!userValue.equals(oprateValue)) {
                      expression.append("true");
                    } else {
                      expression.append("false");
                    } 
                  } else if (oprate.equals("=")) {
                    if (userValue.equals(oprateValue)) {
                      expression.append("true");
                    } else {
                      expression.append("false");
                    } 
                  } else if (oprate.equals(">")) {
                    try {
                      if (Float.parseFloat(userValue) > 
                        Float.parseFloat(oprateValue)) {
                        expression.append("true");
                      } else {
                        expression.append("false");
                      } 
                    } catch (Exception e) {
                      expression.append("false");
                    } 
                  } else if (oprate.equals(">=")) {
                    try {
                      if (Float.parseFloat(userValue) >= 
                        Float.parseFloat(oprateValue)) {
                        expression.append("true");
                      } else {
                        expression.append("false");
                      } 
                    } catch (Exception e) {
                      expression.append("false");
                    } 
                  } else if (oprate.equals("like")) {
                    if (userValue.indexOf(oprateValue) >= 0) {
                      expression.append("true");
                    } else {
                      expression.append("false");
                    } 
                  } else if (oprate.equals("not like")) {
                    if (userValue.indexOf(oprateValue) >= 0) {
                      expression.append("false");
                    } else {
                      expression.append("true");
                    } 
                  }  
              } 
              expression.append(rightTag);
              if ("0".equals(nextTag))
                break; 
              if ("1".equals(nextTag)) {
                expression.append(" && ");
              } else {
                expression.append(" || ");
              } 
            } 
          } 
          try {
            Interpreter inter = new Interpreter();
            Object obj = inter.eval(expression.toString());
            if (obj != null && 
              "TRUE".equals(obj.toString().trim().toUpperCase())) {
              nextActivityId = tmp[0];
              break;
            } 
          } catch (Exception e) {
            System.out.println("解析后的表达式语法错误！");
          } 
        } else {
          if (tmp[1].equals("0")) {
            nextActivityId = tmp[0];
            break;
          } 
          if (tmp[1].equals("-1")) {
            if (submitPersonId.equals("")) {
              HttpSession session = request.getSession(true);
              submitPersonId = session.getAttribute("userId").toString();
            } 
            if (tmp[3].equals("<>")) {
              if (!getEmpDuty(submitPersonId).equals(tmp[2])) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("like")) {
              if (getEmpDuty(submitPersonId).indexOf(tmp[2]) >= 0) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("not like")) {
              if (getEmpDuty(submitPersonId).indexOf(tmp[2]) < 0) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (getEmpDuty(submitPersonId).equals(tmp[2])) {
              nextActivityId = tmp[0];
              break;
            } 
          } else if (tmp[1].equals("-2")) {
            if (submitPersonId.equals("")) {
              HttpSession session = request.getSession(true);
              submitPersonId = session.getAttribute("userId").toString();
            } 
            String dutyLevel = getEmpDutyLevel(submitPersonId);
            if (tmp[3].equals("<>")) {
              if (!dutyLevel.equals(tmp[2])) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("=")) {
              if (dutyLevel.equals(tmp[2])) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("<")) {
              try {
                if (Float.parseFloat(dutyLevel) < 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals("<=")) {
              try {
                if (Float.parseFloat(dutyLevel) <= 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals(">")) {
              try {
                if (Float.parseFloat(dutyLevel) > 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals(">=")) {
              try {
                if (Float.parseFloat(dutyLevel) >= 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } 
          } else if (tmp[1].equals("-3")) {
            if (submitPersonId.equals("")) {
              HttpSession session = request.getSession(true);
              submitPersonId = session.getAttribute("userId").toString();
            } 
            Object object = request.getSession().getAttribute("dutyLevel");
            if (tmp[3].equals("<>")) {
              if (!object.equals(tmp[2])) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("=")) {
              if (object.equals(tmp[2])) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("<")) {
              try {
                if (Float.parseFloat((String)object) < 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals("<=")) {
              try {
                if (Float.parseFloat((String)object) <= 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals(">")) {
              try {
                if (Float.parseFloat((String)object) > 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals(">=")) {
              try {
                if (Float.parseFloat((String)object) >= 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } 
          } else {
            String myFieldName = (new WorkFlowCommonBD()).getFieldName(tmp[1], request.getParameter("moduleId"));
            String userValue = (request.getParameter(myFieldName) == null) ? request.getParameter(String.valueOf(myFieldName) + "_Name") : request.getParameter(myFieldName);
            if (userValue != null)
              if (tmp[3].equals("<")) {
                try {
                  if (Float.parseFloat(userValue) < 
                    Float.parseFloat(tmp[2])) {
                    nextActivityId = tmp[0];
                    break;
                  } 
                } catch (Exception exception) {}
              } else if (tmp[3].equals("<=")) {
                try {
                  if (Float.parseFloat(userValue) <= 
                    Float.parseFloat(tmp[2])) {
                    nextActivityId = tmp[0];
                    break;
                  } 
                } catch (Exception exception) {}
              } else if (tmp[3].equals("<>")) {
                if (!userValue.equals(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } else if (tmp[3].equals("=")) {
                if (userValue.equals(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } else if (tmp[3].equals(">")) {
                try {
                  if (Float.parseFloat(userValue) > 
                    Float.parseFloat(tmp[2])) {
                    nextActivityId = tmp[0];
                    break;
                  } 
                } catch (Exception exception) {}
              } else if (tmp[3].equals(">=")) {
                try {
                  if (Float.parseFloat(userValue) >= 
                    Float.parseFloat(tmp[2])) {
                    nextActivityId = tmp[0];
                    break;
                  } 
                } catch (Exception exception) {}
              } else if (tmp[3].equals("like")) {
                if (userValue.indexOf(tmp[2]) >= 0) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } else if (tmp[3].equals("not like") && 
                userValue.indexOf(tmp[2]) < 0) {
                nextActivityId = tmp[0];
                break;
              }  
          } 
        } 
      } 
    } 
    return nextActivityId;
  }
  
  public String getNextActivityId(List<String[]> nextActivityList, HttpServletRequest request, String tableId, String recordId) {
    String nextActivityId = "0", submitPersonId = "";
    if (!tableId.equals("") && !recordId.equals("")) {
      Connection conn = null;
      Statement stmt = null;
      try {
        conn = (new DataSourceBase()).getDataSource().getConnection();
        stmt = conn.createStatement();
      } catch (Exception exception) {}
      try {
        ResultSet rs = stmt.executeQuery("Select wf_submitemployee_id From JSF_WORK where worktable_id=" + tableId + " and workrecord_id=" + recordId);
        if (rs.next())
          submitPersonId = rs.getString("wf_submitemployee_id"); 
        rs.close();
      } catch (Exception exception) {}
      if (conn != null && stmt != null)
        try {
          stmt.close();
          conn.close();
        } catch (Exception exception) {} 
    } 
    if (nextActivityList != null && nextActivityList.size() > 0) {
      String[] tmp = (String[])null;
      ProcessStep proStep = new ProcessStep();
      for (int j = 0; j < nextActivityList.size(); j++) {
        tmp = nextActivityList.get(j);
        if (tmp.length == 6 && "1".equals(tmp[5])) {
          if (proStep.checkExpression((tmp[4] == null) ? "" : tmp[4].toString(), request))
            nextActivityId = tmp[0]; 
        } else {
          if (tmp[1].equals("0")) {
            nextActivityId = tmp[0];
            break;
          } 
          if (tmp[1].equals("-1")) {
            if (submitPersonId.equals("")) {
              HttpSession session = request.getSession(true);
              submitPersonId = session.getAttribute("userId")
                .toString();
            } 
            if (tmp[3].equals("<>")) {
              if (!getEmpDuty(submitPersonId).equals(tmp[2])) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("like")) {
              if (getEmpDuty(submitPersonId).indexOf(tmp[2]) >= 0) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("not like")) {
              if (getEmpDuty(submitPersonId).indexOf(tmp[2]) < 0) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (getEmpDuty(submitPersonId).equals(tmp[2])) {
              nextActivityId = tmp[0];
              break;
            } 
          } else if (tmp[1].equals("-2")) {
            if (submitPersonId.equals("")) {
              HttpSession session = request.getSession(true);
              submitPersonId = session.getAttribute("userId").toString();
            } 
            String dutyLevel = getEmpDutyLevel(submitPersonId);
            if (tmp[3].equals("<>")) {
              if (!dutyLevel.equals(tmp[2])) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("=")) {
              if (dutyLevel.equals(tmp[2])) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("<")) {
              try {
                if (Float.parseFloat(dutyLevel) < 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals("<=")) {
              try {
                if (Float.parseFloat(dutyLevel) <= 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals(">")) {
              try {
                if (Float.parseFloat(dutyLevel) > 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals(">=")) {
              try {
                if (Float.parseFloat(dutyLevel) >= 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } 
          } else if (tmp[1].equals("-3")) {
            if (submitPersonId.equals("")) {
              HttpSession session = request.getSession(true);
              submitPersonId = session.getAttribute("userId").toString();
            } 
            Object object = request.getSession().getAttribute("dutyLevel");
            if (tmp[3].equals("<>")) {
              if (!object.equals(tmp[2])) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("=")) {
              if (object.equals(tmp[2])) {
                nextActivityId = tmp[0];
                break;
              } 
            } else if (tmp[3].equals("<")) {
              try {
                if (Float.parseFloat((String)object) < 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals("<=")) {
              try {
                if (Float.parseFloat((String)object) <= 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals(">")) {
              try {
                if (Float.parseFloat((String)object) > 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } else if (tmp[3].equals(">=")) {
              try {
                if (Float.parseFloat((String)object) >= 
                  Float.parseFloat(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } catch (Exception exception) {}
            } 
          } else {
            String myFieldName = (new WorkFlowCommonBD()).getFieldName(tmp[1], request.getParameter("moduleId"));
            String userValue = (request.getParameter(myFieldName) == null) ? request.getParameter(String.valueOf(myFieldName) + "_Name") : request.getParameter(myFieldName);
            userValue = CharacterTool.getFormValue(userValue);
            if (userValue != null)
              if (tmp[3].equals("<")) {
                try {
                  if (Float.parseFloat(userValue) < 
                    Float.parseFloat(tmp[2])) {
                    nextActivityId = tmp[0];
                    break;
                  } 
                } catch (Exception exception) {}
              } else if (tmp[3].equals("<=")) {
                try {
                  if (Float.parseFloat(userValue) <= 
                    Float.parseFloat(tmp[2])) {
                    nextActivityId = tmp[0];
                    break;
                  } 
                } catch (Exception exception) {}
              } else if (tmp[3].equals("<>")) {
                if (!userValue.equals(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } else if (tmp[3].equals("=")) {
                if (userValue.equals(tmp[2])) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } else if (tmp[3].equals(">")) {
                try {
                  if (Float.parseFloat(userValue) > 
                    Float.parseFloat(tmp[2])) {
                    nextActivityId = tmp[0];
                    break;
                  } 
                } catch (Exception exception) {}
              } else if (tmp[3].equals(">=")) {
                try {
                  if (Float.parseFloat(userValue) >= 
                    Float.parseFloat(tmp[2])) {
                    nextActivityId = tmp[0];
                    break;
                  } 
                } catch (Exception exception) {}
              } else if (tmp[3].equals("like")) {
                if (userValue.indexOf(tmp[2]) >= 0) {
                  nextActivityId = tmp[0];
                  break;
                } 
              } else if (tmp[3].equals("not like") && 
                userValue.indexOf(tmp[2]) < 0) {
                nextActivityId = tmp[0];
                break;
              }  
          } 
        } 
      } 
    } 
    return nextActivityId;
  }
  
  public String getEmpDuty(String empId) {
    String empDuty = "";
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select empduty from org_employee where emp_id=" + empId);
      if (rs.next())
        empDuty = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return (empDuty == null) ? "" : empDuty;
  }
  
  public String getEmpDutyLevel(String empId) {
    String empDuty = "";
    String empDutyLevel = "0";
    empDuty = getEmpDuty(empId);
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select d.dutyLevel from oa_duty d where d.dutyName='" + empDuty + "'");
      if (rs.next())
        empDutyLevel = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return empDutyLevel;
  }
  
  public String getCurActivityWriteField(String activityId, String tableId, String recordId) {
    String result = (new WorkFlowCommonBD()).getCurActivityWriteField(activityId, tableId, recordId);
    return result;
  }
  
  public String getCurActivityWriteField(HttpServletRequest httpServletRequest) {
    String tableId = httpServletRequest.getParameter("table");
    String activityId = httpServletRequest.getParameter("activity");
    String recordId = httpServletRequest.getParameter("record");
    return getCurActivityWriteField(activityId, tableId, recordId);
  }
  
  public String getFirstActivityWriteField(String processId, String moduleId) {
    return (new ProcessBD()).getFirstActivityWriteField(processId, moduleId);
  }
  
  public Map getCurActivityCommField(String activityId, String tableId, String recordId) {
    Map result = (new WorkFlowCommonBD()).getCurActivityCommField(activityId, tableId, recordId);
    return result;
  }
  
  public Map getCurActivityCommField(String activityId, String tableId, String recordId, String workId) {
    Map result = (new WorkFlowCommonBD()).getCurActivityCommField(activityId, tableId, recordId, workId);
    return result;
  }
  
  public Map getCurActivityCommField(HttpServletRequest httpServletRequest) {
    String tableId = httpServletRequest.getParameter("table");
    String activityId = httpServletRequest.getParameter("activity");
    String recordId = httpServletRequest.getParameter("record");
    String workId = httpServletRequest.getParameter("work");
    return getCurActivityCommField(activityId, tableId, recordId, workId);
  }
  
  public String getCurProcCommField(String processId, String tableId, String recordId) {
    return (new WorkFlowBD()).getAllCommField(processId, tableId, recordId);
  }
  
  public String getCurProcCommField(HttpServletRequest httpServletRequest) {
    return getCurProcCommField(httpServletRequest.getParameter("processId"), 
        httpServletRequest.getParameter("table"), 
        httpServletRequest.getParameter("record"));
  }
  
  public String getCommentByCommField(String processId, String tableId, String recordId, String commField) {
    return (new WorkFlowCommonBD()).getCommentByCommField(processId, tableId, recordId, commField);
  }
  
  public String getCommentByCommField(String processId, String tableId, String recordId, String commField, String userId, String orgId, String isEdit) {
    return (new WorkFlowCommonBD()).getCommentByCommField(processId, tableId, recordId, commField, userId, orgId, isEdit);
  }
  
  public String getCommentByCommField(String processId, String tableId, String recordId, String commField, String userId, String orgId, String isEdit, String hideModiComment) {
    return (new WorkFlowButtonBD()).getCommentByCommField(processId, tableId, recordId, commField, userId, orgId, isEdit, hideModiComment);
  }
  
  public String getCommentByCommField(HttpServletRequest httpServletRequest, String commField) {
    HttpSession session = httpServletRequest.getSession(true);
    String tmp = getCommentByCommField(httpServletRequest.getParameter("processId"), 
        httpServletRequest.getParameter("table"), 
        httpServletRequest.getParameter("record"), 
        commField, 
        (String)session.getAttribute("userId"), 
        (String)session.getAttribute("orgId"), 
        httpServletRequest.getParameter("isEdit"));
    return tmp;
  }
  
  public String getCommentByCommField(HttpServletRequest httpServletRequest, String commField, String hideModiComment) {
    HttpSession session = httpServletRequest.getSession(true);
    String tmp = getCommentByCommField(httpServletRequest.getParameter("processId"), 
        httpServletRequest.getParameter("table"), 
        httpServletRequest.getParameter("record"), 
        commField, 
        (String)session.getAttribute("userId"), 
        (String)session.getAttribute("orgId"), 
        httpServletRequest.getParameter("isEdit"), hideModiComment);
    return tmp;
  }
  
  private String getEmpRoleString(String empId) {
    StringBuffer roles = new StringBuffer(",,");
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("SELECT DISTINCT rolename FROM org_user_role our,org_role o WHERE our.role_id=o.role_id AND emp_id=?");
      pstmt.setString(1, empId);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next())
        roles.append(rs.getString(1)).append(",,"); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exx) {
          exx.printStackTrace();
        }  
    } 
    return roles.toString();
  }
  
  private String getEmpPosition(String empId) {
    String position = ",,";
    Connection conn = null;
    try {
      String positionId = "", positionOtherId = "";
      conn = (new DataSourceBase()).getDataSource().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("SELECT emppositionid,emppositionotherid FROM org_employee WHERE emp_id=?");
      pstmt.setString(1, empId);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        positionId = rs.getString(1);
        positionOtherId = rs.getString(2);
      } 
      rs.close();
      pstmt.close();
      if (positionId != null && !"".equals(positionId)) {
        pstmt = conn.prepareStatement("SELECT station_name FROM st_station WHERE id=?");
        pstmt.setString(1, positionId);
        rs = pstmt.executeQuery();
        if (rs.next())
          position = String.valueOf(position) + rs.getString(1) + ",,"; 
        rs.close();
        pstmt.close();
      } 
      if (positionOtherId != null && !"".equals(positionOtherId)) {
        pstmt = conn.prepareStatement("SELECT station_name FROM st_station WHERE id=?");
        pstmt.setString(1, positionOtherId);
        rs = pstmt.executeQuery();
        if (rs.next())
          position = String.valueOf(position) + rs.getString(1) + ",,"; 
        rs.close();
        pstmt.close();
      } 
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exx) {
          exx.printStackTrace();
        }  
    } 
    return position;
  }
}
