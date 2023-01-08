package com.js.oa.jsflow.action;

import com.js.oa.eform.service.CustomFormBD;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.util.ParseFormWithValue;
import com.js.oa.jsflow.util.ParseWorkFlowForm;
import com.js.oa.jsflow.util.ProcessStep;
import com.js.oa.jsflow.vo.ActivityVO;
import com.js.oa.message.action.ModelSendMsg;
import com.js.util.config.SystemCommon;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WorkFlowSubmitAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = session.getAttribute("domainId").toString();
    httpServletRequest.setAttribute("search", httpServletRequest.getParameter("search"));
    httpServletRequest.setAttribute("workTitle", httpServletRequest.getParameter("workTitle"));
    String workId = httpServletRequest.getParameter("workId");
    String tableId = httpServletRequest.getParameter("tableId");
    String recordId = httpServletRequest.getParameter("recordId");
    String processName = httpServletRequest.getParameter("processName");
    String workType = httpServletRequest.getParameter("workType");
    String activityId = httpServletRequest.getParameter("curActivityId");
    String activityName = httpServletRequest.getParameter("curActivityName");
    String submitPerson = httpServletRequest.getParameter("submitPerson");
    String submitPersonId = httpServletRequest.getParameter("submitPersonId");
    String stepCount = httpServletRequest.getParameter("stepCount");
    String remindField = httpServletRequest.getParameter("remindField");
    HttpSession httpSession = httpServletRequest.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    Date now = new Date();
    WorkFlowBD workFlowBD = new WorkFlowBD();
    String transactType = workFlowBD.getTransactType(tableId, recordId, activityId);
    httpServletRequest.setAttribute("curTransactType", transactType);
    String tag = "success";
    if (workType.equals("0")) {
      String action = httpServletRequest.getParameter("myaction");
      String comment = httpServletRequest.getParameter("comment");
      ArrayList<String> alist = new ArrayList();
      StringBuffer remindFieldValue = new StringBuffer();
      if (remindField != null && !remindField.equals("")) {
        String[] remindFieldArray = ("S" + remindField + "S").split("SS");
        for (int j = 0; j < remindFieldArray.length; j++) {
          if (!remindFieldArray[j].equals(""))
            if (httpServletRequest.getParameter("remindText_" + remindFieldArray[j]) != null) {
              String remindStr = httpServletRequest.getParameter("remindText_" + remindFieldArray[j]);
              if (remindStr.endsWith(";"))
                remindStr = remindStr.substring(0, remindStr.length() - 1); 
              String[] remindValue = remindStr.split(";");
              for (int i = 0; i < remindValue.length; i++) {
                if (remindValue[i].substring(0, remindValue[i].indexOf("/")).equals(httpServletRequest.getParameter(remindFieldArray[j])))
                  remindFieldValue.append(remindValue[i].substring(remindValue[i].indexOf("/") + 1)); 
              } 
            } else if (httpServletRequest.getParameter("remindCheckBox_" + remindFieldArray[j]) != null) {
              String remindStr = httpServletRequest.getParameter("remindCheckBox_" + remindFieldArray[j]);
              if (remindStr.endsWith(";"))
                remindStr = remindStr.substring(0, remindStr.length() - 1); 
              String[] remindValue = remindStr.split(";");
              String tmpValue = httpServletRequest.getParameter(remindFieldArray[j]);
              if (tmpValue.endsWith(","))
                tmpValue = tmpValue.substring(0, tmpValue.length() - 1); 
              String[] tmp = { tmpValue };
              if (tmpValue.indexOf(",") >= 0)
                tmp = tmpValue.split(","); 
              for (int i = 0; i < remindValue.length; i++) {
                for (int m = 0; m < tmp.length; m++) {
                  if (tmp[m].equals(remindValue[i].substring(0, remindValue[i].indexOf("/")))) {
                    remindFieldValue.append(remindValue[i].substring(remindValue[i].indexOf("/") + 1));
                    break;
                  } 
                } 
              } 
            } else {
              remindFieldValue.append(httpServletRequest.getParameter(remindFieldArray[j]));
            }  
        } 
      } 
      if (remindFieldValue.toString().equals("null"))
        remindFieldValue = new StringBuffer(); 
      String fieldString = " wf_randflowcomment_id, randflowemployee_id,  randflowemployeecomment, randflowtime, databasetable_id,  databaserecord_id ";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        alist.add(String.valueOf(userId) + ",'" + comment + "','" + now.toLocaleString() + 
            "'," + tableId + "," + recordId);
      } else {
        alist.add(String.valueOf(userId) + ",'" + comment + "',JSDB.FN_STRTODATE('" + now.toLocaleString() + 
            "','L')," + tableId + "," + recordId);
      } 
      workFlowBD.insertTable("JSF_RandFlowComment", fieldString, alist, "JSF_RandFlowComment_seq");
      alist.clear();
      int k = (String.valueOf(submitPerson) + "的" + remindFieldValue.toString() + processName + "已办理完毕！").length() - 50;
      if (k > 0)
        remindFieldValue.substring(0, remindFieldValue.length() - k); 
      if (action.equals("end")) {
        if (databaseType.indexOf("mysql") >= 0) {
          alist.add("update JSF_WORK set WorkStatus = 100, worktitle = '" + submitPerson + 
              "的" + remindFieldValue.toString() + processName + "已办理完毕！', " + 
              " workAllowCancel = 0, workDoneWithDate = '" + 
              now.toLocaleString() + "' " + 
              " where " + " WorkTable_Id = " + tableId + " and WorkRecord_Id = " + 
              recordId + " and workstartflag = 1");
        } else {
          alist.add("update JSF_WORK set WorkStatus = 100, worktitle = '" + submitPerson + 
              "的" + remindFieldValue.toString() + processName + "已办理完毕！', " + 
              " workAllowCancel = 0, workDoneWithDate = JSDB.FN_STRTODATE('" + 
              now.toLocaleString() + "','L') " + 
              " where " + " WorkTable_Id = " + tableId + " and WorkRecord_Id = " + 
              recordId + " and workstartflag = 1");
        } 
        alist.add("update JSF_WORK set workstatus = 101,worktitle = '" + submitPerson + 
            "的" + remindFieldValue.toString() + processName + 
            "已办理完毕！', workAllowCancel = 0 where wf_work_id = " + workId);
        alist.add("update JSF_WORK set workTitle = '" + submitPerson + "的" + 
            remindFieldValue.toString() + processName + "已办理完毕！'" + 
            " where wf_work_id <> " + workId + " and workstartflag <> 1 and " + 
            " workTable_id = " + tableId + " and workRecord_id = " + recordId);
        workFlowBD.updateTable(alist);
      } else {
        String randUser = httpServletRequest.getParameter("randomProcUser");
        if (randUser.indexOf("$") >= 0)
          randUser = randUser.substring(1, randUser.length() - 1); 
        workFlowBD.newRandomWork(workId, randUser, remindFieldValue.toString());
        alist.add("update JSF_WORK set workstatus = 101, worktitle='" + submitPerson + 
            "的" + remindFieldValue.toString() + processName + "正在办理中！'" + 
            ", workAllowCancel = 0 where wf_work_id = " + workId);
        workFlowBD.updateTable(alist);
      } 
    } else {
      tag = "nextstep";
      StringBuffer updateValue = new StringBuffer();
      String[] fieldName = httpServletRequest.getParameterValues("fieldName");
      StringBuffer groupField = new StringBuffer();
      StringBuffer remindFieldValue = new StringBuffer();
      String[] remindFieldArray = ("S" + remindField + "S").split("SS");
      String remindStr = "";
      for (int i = 0; i < remindFieldArray.length; i++) {
        if (!remindFieldArray[i].equals(""))
          if (httpServletRequest.getParameter("remindText_" + remindFieldArray[i]) != null) {
            remindStr = httpServletRequest.getParameter("remindText_" + remindFieldArray[i]);
            if (remindStr.endsWith(";"))
              remindStr = remindStr.substring(0, remindStr.length() - 1); 
            String[] remindValue = remindStr.split(";");
            for (int k = 0; k < remindValue.length; k++) {
              if (remindValue[k].substring(0, remindValue[k].indexOf("/")).equals(httpServletRequest.getParameter(remindFieldArray[i])))
                remindFieldValue.append(remindValue[k].substring(remindValue[k].indexOf("/") + 1)); 
            } 
          } else if (httpServletRequest.getParameter("remindCheckBox_" + remindFieldArray[i]) != null) {
            remindStr = httpServletRequest.getParameter("remindCheckBox_" + remindFieldArray[i]);
            if (remindStr.endsWith(";"))
              remindStr = remindStr.substring(0, remindStr.length() - 1); 
            String[] remindValue = remindStr.split(";");
            String[] tmp = httpServletRequest.getParameterValues(remindFieldArray[i]);
            for (int k = 0; k < remindValue.length; k++) {
              for (int m = 0; m < tmp.length; m++) {
                if (tmp[m].equals(remindValue[k].substring(0, remindValue[k].indexOf("/")))) {
                  remindFieldValue.append(remindValue[k].substring(remindValue[k].indexOf("/") + 1));
                  break;
                } 
              } 
            } 
          } else {
            String str = "";
            if (fieldName != null)
              for (int j = 0; j < fieldName.length; j++) {
                if (fieldName[j].equals(String.valueOf(remindFieldArray[i]) + "_time") || 
                  fieldName[j].equals(String.valueOf(remindFieldArray[i]) + "_date")) {
                  str = fieldName[j];
                  break;
                } 
              }  
            if (str.equals("")) {
              remindFieldValue.append(httpServletRequest.getParameter(remindFieldArray[i]));
            } else if (str.endsWith("_time")) {
              remindFieldValue.append(String.valueOf(httpServletRequest.getParameter(String.valueOf(str.substring(0, str.indexOf("_"))) + "_hour")) + ":");
              remindFieldValue.append(httpServletRequest.getParameter(String.valueOf(str.substring(0, str.indexOf("_"))) + "_minute"));
            } else if (str.endsWith("_date")) {
              remindFieldValue.append(String.valueOf(httpServletRequest.getParameter(String.valueOf(str.substring(0, str.indexOf("_"))) + "_day")) + " ");
              remindFieldValue.append(String.valueOf(httpServletRequest.getParameter(String.valueOf(str.substring(0, str.indexOf("_"))) + "_hour")) + ":");
              remindFieldValue.append(httpServletRequest.getParameter(String.valueOf(str.substring(0, str.indexOf("_"))) + "_minute"));
            } 
          }  
      } 
      if (fieldName != null) {
        String fieldNameStr = "";
        String tmpValue = "";
        for (int j = 0; j < fieldName.length; j++) {
          fieldNameStr = fieldName[j].substring(0, fieldName[j].indexOf("_"));
          updateValue.append(String.valueOf(fieldNameStr) + " = ");
          if (fieldName[j].endsWith("_text")) {
            tmpValue = httpServletRequest.getParameter(fieldNameStr).replace('"', '\'');
            tmpValue = tmpValue.replaceAll("'", "''");
            updateValue.append("'" + tmpValue + "',");
          } else if (fieldName[j].endsWith("_checkbox")) {
            String[] tmp = httpServletRequest.getParameterValues(fieldNameStr);
            if (tmp != null) {
              String tmp2 = "";
              for (int k = 0; k < tmp.length; k++)
                tmp2 = String.valueOf(tmp2) + tmp[k] + ","; 
              updateValue.append("'" + tmp2.substring(0, tmp2.length() - 1) + "',");
            } else {
              updateValue.append("'',");
            } 
          } else if (fieldName[j].endsWith("_time")) {
            updateValue.append("'" + httpServletRequest.getParameter(String.valueOf(fieldName[j].substring(0, fieldName[j].indexOf("_"))) + "_hour"));
            updateValue.append(":" + httpServletRequest.getParameter(String.valueOf(fieldName[j].substring(0, fieldName[j].indexOf("_"))) + "_minute") + "',");
          } else if (fieldName[j].endsWith("_date")) {
            updateValue.append("'" + httpServletRequest.getParameter(String.valueOf(fieldName[j].substring(0, fieldName[j].indexOf("_"))) + "_day") + " ");
            updateValue.append(String.valueOf(httpServletRequest.getParameter(String.valueOf(fieldName[j].substring(0, fieldName[j].indexOf("_"))) + "_hour")) + ":");
            updateValue.append(String.valueOf(httpServletRequest.getParameter(String.valueOf(fieldName[j].substring(0, fieldName[j].indexOf("_"))) + "_minute")) + "',");
          } else if (fieldName[j].endsWith("_file")) {
            String[] fileName = httpServletRequest.getParameterValues(String.valueOf(fieldName[j].substring(0, fieldName[j].indexOf("_"))) + "_fileName");
            String[] saveName = httpServletRequest.getParameterValues(String.valueOf(fieldName[j].substring(0, fieldName[j].indexOf("_"))) + "_saveName");
            String tmp = "";
            if (fileName != null) {
              for (int k = 0; k < fileName.length; k++)
                tmp = String.valueOf(tmp) + fileName[k] + ":" + saveName[k] + "::"; 
              tmp = tmp.substring(0, tmp.length() - 2);
            } 
            updateValue.append("'" + tmp + "',");
          } else if (fieldName[j].endsWith("_group")) {
            updateValue.append("'0',");
            ArrayList<String> childTableList = new ArrayList();
            ArrayList<String> childFieldList = new ArrayList();
            ArrayList<ArrayList<String[]>> childFieldValueList = new ArrayList();
            String name = fieldName[j].substring(0, fieldName[j].indexOf("_"));
            String childTableName = httpServletRequest.getParameter("childTable_" + name);
            childTableList.add(childTableName);
            String childFieldName = httpServletRequest.getParameter("childField_" + name);
            childFieldName = childFieldName.replace(';', ',');
            childFieldList.add(childFieldName);
            ArrayList<String[]> childFieldValue = new ArrayList();
            String[] childFieldNameArray = childFieldName.split(",");
            if (childFieldNameArray != null && childFieldNameArray.length > 0) {
              if (httpServletRequest.getParameterValues(childFieldNameArray[0]) != null) {
                int size = (httpServletRequest.getParameterValues(childFieldNameArray[0])).length;
                int m;
                for (m = 0; m < size; m++)
                  childFieldValue.add(new String[childFieldNameArray.length]); 
                for (m = 0; m < childFieldNameArray.length; m++) {
                  String[] tmp = httpServletRequest.getParameterValues(childFieldNameArray[m]);
                  for (int n = 0; n < tmp.length; n++) {
                    String[] loadStr = childFieldValue.get(n);
                    loadStr[m] = tmp[n];
                    childFieldValue.set(n, loadStr);
                  } 
                } 
                childFieldValueList.add(childFieldValue);
              } 
              List<String[]> tmpList = null;
              for (int k = 0; k < childFieldValueList.size(); k++) {
                String childTable = childTableList.get(k);
                String childField = childFieldList.get(k);
                tmpList = childFieldValueList.get(k);
                for (int m = 0; m < tmpList.size(); m++) {
                  String[] tmp = tmpList.get(m);
                  StringBuffer sb = new StringBuffer();
                  for (int n = 0; n < tmp.length; n++)
                    sb.append("'" + tmp[n] + "', "); 
                  groupField.append(String.valueOf(childTable) + "::" + childField + "::" + sb.toString() + ";;");
                } 
              } 
            } 
          } else {
            updateValue.append("'',");
          } 
        } 
      } 
      httpServletRequest.setAttribute("groupField", groupField.toString());
      String comment = httpServletRequest.getParameter("comment");
      httpServletRequest.setAttribute("comment", comment);
      httpServletRequest.setAttribute("updateValue", updateValue.toString());
      httpServletRequest.setAttribute("processName", processName);
      httpServletRequest.setAttribute("tableId", tableId);
      httpServletRequest.setAttribute("recordId", recordId);
      httpServletRequest.setAttribute("workId", workId);
      httpServletRequest.setAttribute("submitPerson", submitPerson);
      httpServletRequest.setAttribute("submitPersonId", submitPersonId);
      httpServletRequest.setAttribute("curActivityId", activityId);
      httpServletRequest.setAttribute("curActivityName", activityName);
      httpServletRequest.setAttribute("stepCount", stepCount);
      httpServletRequest.setAttribute("remindFieldValue", remindFieldValue.toString());
      String stepType = workFlowBD.getActivityType(activityId, tableId, recordId);
      httpServletRequest.setAttribute("tableId", tableId);
      httpServletRequest.setAttribute("recordId", recordId);
      httpServletRequest.setAttribute("isStandForWork", httpServletRequest.getParameter("isStandForWork"));
      httpServletRequest.setAttribute("standForUserId", httpServletRequest.getParameter("standForUserId"));
      httpServletRequest.setAttribute("standForUserName", httpServletRequest.getParameter("standForUserName"));
      httpServletRequest.setAttribute("stepType", stepType);
      String[] actiClass = workFlowBD.getActivityClass(tableId, recordId, activityId);
      httpServletRequest.setAttribute("activityClass", actiClass[0]);
      if (stepType.equals("1")) {
        List<ActivityVO> activityList = workFlowBD.getAllNextActivity(tableId, recordId, activityId);
        StringBuffer nextActivity = new StringBuffer();
        boolean canFinish = false;
        boolean hasNextActivity = false;
        if (activityList != null && activityList.size() > 0) {
          ActivityVO activityVO = null;
          if (activityList.size() == 1) {
            activityVO = activityList.get(0);
            if (activityVO.getBeginEnd() == 2) {
              hasNextActivity = false;
            } else {
              hasNextActivity = true;
              nextActivity.append("<tr>");
              nextActivity.append("<td width=\"15%\" height=\"22\">选择下一节点：</td>");
              nextActivity.append("<td>");
            } 
          } else {
            hasNextActivity = true;
            nextActivity.append("<tr>");
            nextActivity.append("<td width=\"15%\" height=\"22\">选择下一节点：</td>");
            nextActivity.append("<td>");
          } 
          for (int j = 0; j < activityList.size(); j++) {
            activityVO = activityList.get(j);
            if (activityVO.getBeginEnd() == 2) {
              canFinish = true;
            } else {
              nextActivity.append("<input type=\"radio\" name=\"nextActivity\" value=\"" + activityVO.getId() + "\" onclick=\"nextUser(this)\">");
              nextActivity.append(String.valueOf(activityVO.getName()) + "&nbsp;&nbsp;");
            } 
          } 
          nextActivity.append("</td>");
          nextActivity.append("</tr>");
        } else {
          canFinish = true;
        } 
        httpServletRequest.setAttribute("nextActivity", nextActivity.toString());
        StringBuffer button = new StringBuffer();
        button.append("<tr id=\"nextTr\"><td colspan=\"2\" align=\"center\">");
        button.append("<table width=\"40%\" border=\"0\" align=\"center\" cellpadding=\"1\" cellspacing=\"1\">");
        button.append("<tr>");
        if (hasNextActivity) {
          button.append("<td align=\"center\">");
          button.append("<table width=\"90\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
          button.append("<tr><td width=\"10\"><img src=\"images/button_left.gif\" height=\"22\" width=\"10\"></td>");
          button.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
          button.append("<img src=\"images/button_dot.gif\" height=\"8\" width=\"4\" align=\"absmiddle\" hspace=\"3\">");
          if (actiClass[0].equals("1")) {
            button.append("<a href=\"javascript:sub();\"><font color=\"#000000\">提交</font></a>");
          } else {
            button.append("<a href=\"javascript:nextSub();\"><font color=\"#000000\">下一步骤</font></a>");
          } 
          button.append("<td width=\"10\"><img src=\"images/button_right.gif\" height=\"22\" width=\"10\"></td></tr>");
          button.append("</table>");
          button.append("</td>");
        } 
        if (canFinish) {
          button.append("<td align=\"center\">");
          button.append("<table width=\"80\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
          button.append("<tr><td width=\"10\"><img src=\"images/button_left.gif\" height=\"22\" width=\"10\"></td>");
          button.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
          button.append("<img src=\"images/button_dot.gif\" height=\"8\" width=\"4\" align=\"absmiddle\" hspace=\"3\">");
          button.append("<a href=\"javascript:comp();\"><font color=\"#000000\">结束流程</font></a>");
          button.append("<td width=\"10\"><img src=\"images/button_right.gif\" height=\"22\" width=\"10\"></td></tr>");
          button.append("</table>");
          button.append("</td>");
        } 
        button.append("<td align=\"center\">");
        button.append("<table width=\"70\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        button.append("<tr><td width=\"10\"><img src=\"images/button_left.gif\" height=\"22\" width=\"10\"></td>");
        button.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
        button.append("<img src=\"images/button_dot.gif\" height=\"8\" width=\"4\" align=\"absmiddle\" hspace=\"3\">");
        button.append("<a href=\"javascript:window.history.back();\"><font color=\"#000000\">取消</font></a>");
        button.append("<td width=\"10\"><img src=\"images/button_right.gif\" height=\"22\" width=\"10\"></td></tr>");
        button.append("</table>");
        button.append("</td>");
        button.append("</tr>");
        button.append("</table>");
        button.append("</td></tr>");
        button.append("<tr id=\"subTr\" style=\"display:none\"><td colspan=\"2\" align=\"center\">");
        button.append("<table width=\"50%\"><tr><td align=\"center\">");
        button.append("<table width=\"70\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        button.append("<tr>");
        button.append("<td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_left.gif\"></td>");
        button.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
        button.append("<img width=\"4\" height=\"8\" align=\"absmiddle\" hspace=\"3\" src=\"images/button_dot.gif\">");
        button.append("<a href=\"javascript:sub();\"><font color=\"#000000\">提交</font></a>");
        button.append("</td><td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_right.gif\"></td>");
        button.append("</tr>");
        button.append("</table>");
        button.append("</td><td>");
        button.append("<table width=\"70\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        button.append("<tr><td width=\"10\"><img src=\"images/button_left.gif\" height=\"22\" width=\"10\"></td>");
        button.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
        button.append("<img src=\"images/button_dot.gif\" height=\"8\" width=\"4\" align=\"absmiddle\" hspace=\"3\">");
        button.append("<a href=\"javascript:window.history.back();\"><font color=\"#000000\">取消</font></a>");
        button.append("<td width=\"10\"><img src=\"images/button_right.gif\" height=\"22\" width=\"10\"></td></tr>");
        button.append("</table>");
        button.append("</td></tr></table>");
        button.append("</td></tr>");
        httpServletRequest.setAttribute("button", button.toString());
      } else {
        ActivityVO activityVO = (new ProcessStep()).getProceedNextActi(activityId, tableId, 
            recordId, httpServletRequest);
        if (activityVO == null || activityVO.getActivityBeginEnd() == 2) {
          StringBuffer button = new StringBuffer();
          button.append("<tr><td colspan=\"2\" align=\"center\">");
          button.append("<table width=\"200\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
          button.append("<tr>");
          button.append("<td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_left.gif\"></td>");
          button.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
          button.append("<img width=\"4\" height=\"8\" align=\"absmiddle\" hspace=\"3\" src=\"images/button_dot.gif\">");
          button.append("<a href=\"javascript:comp();\"><font color=\"#000000\">结束流程</font></a></td>");
          button.append("<td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_right.gif\"></td>");
          button.append("<td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_left.gif\"></td>");
          button.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
          button.append("<img width=\"4\" height=\"8\" align=\"absmiddle\" hspace=\"3\" src=\"images/button_dot.gif\">");
          button.append("<a href=\"javascript:window.history.back();\"><font color=\"#000000\">取消</font></a></td>");
          button.append("<td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_right.gif\"></td>");
          button.append("</tr>");
          button.append("</table>");
          button.append("</td></tr>");
          httpServletRequest.setAttribute("nextActivity", "");
          httpServletRequest.setAttribute("button", button.toString());
        } else {
          int j;
          List<Object[]> list1, candidate;
          Object[] obj;
          int k;
          StringBuffer tmpBuffer = new StringBuffer();
          tmpBuffer.append("<tr>");
          tmpBuffer.append("<td>下一办理步骤：</td>");
          tmpBuffer.append("<td>");
          tmpBuffer.append("节点名称：<label class=mustFillcolor>" + activityVO.getName() + "</label>&nbsp;&nbsp;办理用户：");
          Object[] leaderObj = (Object[])null;
          List<Object[]> leaderList = null;
          switch (activityVO.getParticipantType()) {
            case 0:
              leaderList = (new WorkFlowBD()).getLeaderList(httpServletRequest.getParameter("submitPersonId"));
              tmpBuffer.append("<select name=\"mainCandidateUser\" size=\"4\" multiple=\"multiple\">");
              for (j = 0; j < leaderList.size(); j++) {
                leaderObj = leaderList.get(j);
                tmpBuffer.append("<option value=\"" + leaderObj[0] + "\"");
                if (j == 0)
                  tmpBuffer.append("selected"); 
                tmpBuffer.append(">" + leaderObj[1] + "</option>");
              } 
              tmpBuffer.append("</select>");
              tmpBuffer.append("<input type=\"hidden\" name=\"mainUserType\" value=\"0\">");
              break;
            case 1:
              tmpBuffer.append("<input type=\"text\" name=\"mainOperProcUserName\" readonly size=\"40\" class=\"css0\">");
              tmpBuffer.append("<input type=\"hidden\" name=\"mainOperProcUser\">&nbsp;");
              tmpBuffer.append("<img src=\"images/group.gif\">&nbsp;&nbsp;");
              tmpBuffer.append("<img width=\"15\" height=\"16\" style=\"cursor:'hand'\"src=\"images/select.gif\" onclick=\"openEndow('mainOperProcUser','mainOperProcUserName',mainOperProcUser.value,mainOperProcUserName.value,'user','no','user','*0*');\">");
              tmpBuffer.append("&nbsp;<label class=mustFillcolor>*</label>");
              tmpBuffer.append("<input type=\"hidden\" name=\"mainUserType\" value=\"1\">");
              break;
            case 2:
              tmpBuffer.append("<select name=\"mainCandidateUser\" size=\"4\" multiple=\"multiple\">");
              list1 = activityVO.getParticipantUser();
              obj = (Object[])null;
              for (k = 0; k < list1.size(); k++) {
                obj = list1.get(k);
                tmpBuffer.append("<option value=\"" + obj[0] + "\"");
                if (k == 0)
                  tmpBuffer.append("selected"); 
                tmpBuffer.append(">" + obj[1] + "</option>");
              } 
              tmpBuffer.append("</select>");
              tmpBuffer.append("<input type=\"hidden\" name=\"mainUserType\" value=\"2\">");
              break;
            case 3:
              candidate = activityVO.getParticipantUser();
              tmpBuffer.append("<label class=mustFillcolor>指定全部办理人</label>");
              for (k = 0; k < candidate.size(); k++) {
                obj = candidate.get(k);
                tmpBuffer.append("<input type=\"hidden\" name=\"mainAllUser\" value=\"" + obj[0] + "\">");
              } 
              tmpBuffer.append("<input type=\"hidden\" name=\"mainUserType\" value=\"3\">");
              break;
            case 4:
              tmpBuffer.append("<label class=mustFillcolor>由表单中的某个字段值决定</label>");
              tmpBuffer.append("<input type=\"hidden\" name=\"mainUserField\" value=\"" + activityVO.getParticipantUserField() + "\">");
              tmpBuffer.append("<input type=\"hidden\" name=\"mainUserType\" value=\"4\">");
              break;
            case 5:
              tmpBuffer.append("<label class=mustFillcolor>流程发起人</label>");
              tmpBuffer.append("<input type=\"hidden\" name=\"mainUserType\" value=\"5\">");
              break;
            case 6:
              tmpBuffer.append("<label class=mustFillcolor>" + activityVO.getPartRoleName() + "</label>");
              tmpBuffer.append("<input type=\"hidden\" name=\"mainUserType\" value=\"6\">");
              tmpBuffer.append("<input type=\"hidden\" name=\"mainPartRole\" value=\"" + activityVO.getPartRole() + "\">");
              break;
          } 
          if (activityVO.getNeedPassRound().booleanValue()) {
            int m;
            List<Object[]> passRoundCand;
            Object[] passRoundObj;
            int n;
            List<Object[]> passRoundAllUser;
            Object[] passRoundAllUserObj;
            int i1;
            tmpBuffer.append("&nbsp;&nbsp;阅件用户：<input type=\"hidden\" name=\"mainNeedPassRound\" value=\"1\">");
            switch (activityVO.getPassRoundUserType()) {
              case 0:
                tmpBuffer.append("<input type=\"hidden\" name=\"mainPassRoundUserType\" value=\"0\">");
                leaderList = (new WorkFlowBD()).getLeaderList(httpServletRequest.getParameter("submitPersonId"));
                for (m = 0; m < leaderList.size(); m++) {
                  leaderObj = leaderList.get(m);
                  tmpBuffer.append("<option value=\"" + leaderObj[0] + "\"");
                  if (m == 0)
                    tmpBuffer.append(" selected "); 
                  tmpBuffer.append(">" + leaderObj[1] + "</option>");
                } 
                tmpBuffer.append("</select>");
                break;
              case 1:
                tmpBuffer.append("<input type=\"text\" name=\"mainPassRoundUserName\" readonly size=\"30\" class=\"css0\"><input type=\"hidden\" name=\"mainPassRoundUser\">&nbsp;<img src=\"images/group.gif\">&nbsp;<img width=\"15\" height=\"16\" style=\"cursor:'hand'\"src=\"images/select.gif\" onclick=\"openEndow('mainPassRoundUser','mainPassRoundUserName',mainPassRoundUser.value,mainPassRoundUserName.value,'user','no','user','*0*');\"><input type=\"hidden\" name=\"mainPassRoundUserType\" value=\"1\">");
                break;
              case 2:
                tmpBuffer.append("<select name=\"mainPassRoundCandUser\" size=\"4\" multiple=\"multiple\">");
                passRoundCand = activityVO.getPassRoundUser();
                passRoundObj = (Object[])null;
                for (n = 0; n < passRoundCand.size(); n++) {
                  passRoundObj = passRoundCand.get(n);
                  tmpBuffer.append("<option value=\"" + passRoundObj[0] + "\"");
                  if (n == 0)
                    tmpBuffer.append(" selected "); 
                  tmpBuffer.append(">" + passRoundObj[1] + "</option>");
                } 
                tmpBuffer.append("</select>");
                tmpBuffer.append("<input type=\"hidden\" name=\"mainPassRoundUserType\" value=\"2\">");
                break;
              case 3:
                tmpBuffer.append("<label class=mustFillcolor>指定全部办理人</label>");
                passRoundAllUser = activityVO.getPassRoundUser();
                passRoundAllUserObj = (Object[])null;
                for (i1 = 0; i1 < passRoundAllUser.size(); i1++) {
                  passRoundAllUserObj = passRoundAllUser.get(i1);
                  tmpBuffer.append("<input type=\"hidden\" name=\"mainPassRoundAllUser\" value=\"" + passRoundAllUserObj[0] + "\">");
                } 
                tmpBuffer.append("<input type=\"hidden\" name=\"mainPassRoundUserType\" value=\"3\">");
                break;
              case 4:
                tmpBuffer.append("<label class=mustFillcolor>由表单中的某个字段值决定</label><input type=\"hidden\" name=\"mainPassRoundUserField\" value=\"" + 
                    activityVO.getPassRoundUserField() + "\">" + 
                    "<input type=\"hidden\" name=\"mainPassRoundUserType\" value=\"4\">");
                break;
              case 5:
                tmpBuffer.append("<label class=mustFillcolor>流程发起人</label>");
                tmpBuffer.append("<input type=\"hidden\" name=\"mainPassRoundUserType\" value=\"5\">");
                break;
              case 6:
                tmpBuffer.append("<label class=mustFillcolor>" + activityVO.getPassRoleName() + "</label>");
                tmpBuffer.append("<input type=\"hidden\" name=\"mainPassRoundUserType\" value=\"6\">");
                tmpBuffer.append("<input type=\"hidden\" name=\"mainPassRole\" value=\"" + activityVO.getPassRole() + "\">");
                break;
            } 
          } 
          if ((new ModelSendMsg()).judgePurviewMessage("工作流程", domainId))
            tmpBuffer.append("&nbsp;<input type=checkbox name=needSendMsg value=1>短信提醒"); 
          tmpBuffer.append("</td>");
          tmpBuffer.append("</tr>");
          tmpBuffer.append("<input type=\"hidden\" name=\"mainAllowStandFor\" value=\"" + activityVO.getAllowStandFor() + "\">");
          tmpBuffer.append("<input type=\"hidden\" name=\"mainAllowCancel\" value=\"" + activityVO.getAllowcancel() + "\">");
          tmpBuffer.append("<input type=\"hidden\" name=\"mainAllowTransition\" value=\"" + activityVO.getAllowTransition() + "\">");
          tmpBuffer.append("<input type=\"hidden\" name=\"mainPressType\" value=\"" + activityVO.getPressType() + "\">");
          tmpBuffer.append("<input type=\"hidden\" name=\"mainDeadLineTime\" value=\"" + activityVO.getDeadlineTime() + "\">");
          tmpBuffer.append("<input type=\"hidden\" name=\"mainPressMotionTime\" value=\"" + activityVO.getPressMotionTime() + "\">");
          tmpBuffer.append("<input type=\"hidden\" name=\"mainNextActivityId\" value=\"" + activityVO.getId() + "\">");
          tmpBuffer.append("<input type=\"hidden\" name=\"mainNextActivityName\" value=\"" + activityVO.getName() + "\">");
          httpServletRequest.setAttribute("nextActivity", tmpBuffer.toString());
          StringBuffer button = new StringBuffer();
          button.append("<tr id=\"nextTr\"><td colspan=\"2\" align=\"center\">");
          button.append("<table width=\"50%\"><tr><td align=\"center\">");
          button.append("<table width=\"80\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
          button.append("<tr>");
          button.append("<td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_left.gif\"></td>");
          button.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
          button.append("<img width=\"4\" height=\"8\" align=\"absmiddle\" hspace=\"3\" src=\"images/button_dot.gif\">");
          if (actiClass[0].equals("1")) {
            button.append("<a href=\"javascript:sub2();\"><font color=\"#000000\">提交</font></a>");
          } else {
            button.append("<a href=\"javascript:nextSub();\"><font color=\"#000000\">下一步骤</font></a>");
          } 
          button.append("</td><td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_right.gif\"></td>");
          button.append("</tr>");
          button.append("</table>");
          button.append("</td><td>");
          button.append("<table width=\"70\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
          button.append("<tr><td width=\"10\"><img src=\"images/button_left.gif\" height=\"22\" width=\"10\"></td>");
          button.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
          button.append("<img src=\"images/button_dot.gif\" height=\"8\" width=\"4\" align=\"absmiddle\" hspace=\"3\">");
          button.append("<a href=\"javascript:window.history.back();\"><font color=\"#000000\">取消</font></a>");
          button.append("<td width=\"10\"><img src=\"images/button_right.gif\" height=\"22\" width=\"10\"></td></tr>");
          button.append("</table>");
          button.append("</td></tr></table>");
          button.append("</td></tr>");
          button.append("<tr id=\"subTr\" style=\"display:none\"><td colspan=\"2\" align=\"center\">");
          button.append("<table width=\"50%\"><tr><td align=\"center\">");
          button.append("<table width=\"70\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
          button.append("<tr>");
          button.append("<td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_left.gif\"></td>");
          button.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
          button.append("<img width=\"4\" height=\"8\" align=\"absmiddle\" hspace=\"3\" src=\"images/button_dot.gif\">");
          button.append("<a href=\"javascript:sub2();\"><font color=\"#000000\">提交</font></a>");
          button.append("</td><td width=\"10\"><img width=\"10\" height=\"22\" src=\"images/button_right.gif\"></td>");
          button.append("</tr>");
          button.append("</table>");
          button.append("</td><td>");
          button.append("<table width=\"70\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
          button.append("<tr><td width=\"10\"><img src=\"images/button_left.gif\" height=\"22\" width=\"10\"></td>");
          button.append("<td align=\"center\" background=\"images/button_bg.jpg\">");
          button.append("<img src=\"images/button_dot.gif\" height=\"8\" width=\"4\" align=\"absmiddle\" hspace=\"3\">");
          button.append("<a href=\"javascript:window.history.back();\"><font color=\"#000000\">取消</font></a>");
          button.append("<td width=\"10\"><img src=\"images/button_right.gif\" height=\"22\" width=\"10\"></td></tr>");
          button.append("</table>");
          button.append("</td></tr></table>");
          button.append("</td></tr>");
          httpServletRequest.setAttribute("button", button.toString());
        } 
      } 
      if (actiClass[0].equals("0")) {
        ProcessBD processBD = new ProcessBD();
        List noWriteField = processBD.getNoWriteField(actiClass[1]);
        httpServletRequest.setAttribute("noWriteField", noWriteField);
        ParseWorkFlowForm parseWorkFlowForm = new ParseWorkFlowForm();
        String code = (new CustomFormBD()).getCode(tableId);
        if (code == null || code.toUpperCase().equals("NULL") || code.trim().length() < 1) {
          String[][] realTable = (new CustomFormBD()).getTableIDAndName(tableId);
          httpServletRequest.setAttribute("formContent", (new ParseFormWithValue()).parseForm((realTable == null || realTable.length < 1) ? "-1" : realTable[0][0], recordId, "", true, session.getAttribute("fileServer").toString(), httpServletRequest, tableId));
        } else {
          httpServletRequest.setAttribute("formContent", "");
        } 
        httpServletRequest.setAttribute("subProcId", actiClass[1]);
        httpServletRequest.setAttribute("subProcName", actiClass[4]);
        httpServletRequest.setAttribute("subProcTableId", actiClass[3]);
        if (actiClass[5].equals("0")) {
          httpServletRequest.setAttribute("nextStep", (new ProcessStep()).firstStep(actiClass[1], Integer.parseInt(actiClass[5])));
        } else {
          httpServletRequest.setAttribute("nextStep", "");
        } 
        httpServletRequest.setAttribute("subProcType", actiClass[2]);
        httpServletRequest.setAttribute("subProcessType", actiClass[5]);
        httpServletRequest.setAttribute("subRemindField", actiClass[6]);
      } 
    } 
    return actionMapping.findForward(tag);
  }
}
