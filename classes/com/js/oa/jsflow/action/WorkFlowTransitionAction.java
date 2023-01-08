package com.js.oa.jsflow.action;

import com.js.oa.jsflow.service.WorkFlowBD;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WorkFlowTransitionAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    String workId = httpServletRequest.getParameter("workId");
    String tableId = httpServletRequest.getParameter("tableId");
    String recordId = httpServletRequest.getParameter("recordId");
    String transitionUser = httpServletRequest.getParameter("transitionUser");
    String activityId = httpServletRequest.getParameter("curActivityId");
    String activityName = httpServletRequest.getParameter("curActivityName");
    String comment = httpServletRequest.getParameter("comment");
    String stepCount = httpServletRequest.getParameter("stepCount");
    String remindField = httpServletRequest.getParameter("remindField");
    StringBuffer remindFieldValue = new StringBuffer();
    StringBuffer updateValue = new StringBuffer();
    StringBuffer groupField = new StringBuffer();
    String[] fieldName = httpServletRequest.getParameterValues("fieldName");
    if (fieldName != null) {
      String fieldNameStr = "";
      for (int j = 0; j < fieldName.length; j++) {
        fieldNameStr = fieldName[j].substring(0, fieldName[j].indexOf("_"));
        updateValue.append(String.valueOf(fieldNameStr) + " = ");
        if (fieldName[j].endsWith("_text")) {
          updateValue.append("'" + httpServletRequest.getParameter(fieldNameStr) + "',");
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
                for (int i = 0; i < tmp.length; i++) {
                  String[] loadStr = childFieldValue.get(i);
                  loadStr[m] = tmp[i];
                  childFieldValue.set(i, loadStr);
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
    if (!remindField.equals("")) {
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
    } 
    WorkFlowBD workFlowBD = new WorkFlowBD();
    ArrayList<String> alist = new ArrayList();
    if (!updateValue.toString().equals("")) {
      String tmp = updateValue.substring(0, updateValue.length() - 1).toString();
      String tableName = workFlowBD.getTableName(tableId);
      alist.add(" update " + tableName + " set " + tmp + " where " + tableName + "_id = " + recordId);
      workFlowBD.updateTable(alist);
    } 
    transitionUser = transitionUser.replace('$', ',');
    String[] user = ("," + transitionUser + ",").split(",,");
    String[] para = { workId, stepCount, remindFieldValue.toString(), "/jsoa/WorkFlowProcAction.do?flowpara=1" };
    (new WorkFlowBD()).transitionWork(para, user);
    HttpSession httpSession = httpServletRequest.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String[] tmpPara = { 
        tableId, recordId, activityName, activityId, userId, comment, "", "0", stepCount, "0", 
        "0", "1", "0" };
    workFlowBD.insertDealWith(tmpPara);
    return actionMapping.findForward("success");
  }
}
