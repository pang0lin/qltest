package com.js.oa.form;

import com.js.oa.datasync.service.DataSynchronizeService;
import com.js.oa.eform.service.CustomFormBD;
import com.js.oa.info.util.InfoArchives;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.jsflow.util.DataToOtherBase;
import com.js.oa.jsflow.util.WorkflowSYHRData;
import com.js.oa.rws.service.TongBuService;
import com.js.oa.search.client.SearchService;
import com.js.util.config.SysConfigReader;
import com.js.util.config.SystemCommon;
import com.js.util.util.BASE64;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class Workflow {
  public Map save(HttpServletRequest httpServletRequest) {
    String tableId = httpServletRequest.getParameter("tableId");
    String oldRecordId = httpServletRequest.getParameter("recordId");
    String remindField = (httpServletRequest.getParameter("remindField") == null) ? "" : httpServletRequest.getParameter("remindField");
    String tableName = (new CustomFormBD()).getTable(tableId);
    String[] fieldName = httpServletRequest.getParameterValues("fieldName");
    StringBuffer valueBuffer = new StringBuffer();
    List<String> fieldList = new ArrayList();
    List<String> valueList = new ArrayList();
    ArrayList<String> childTableList = new ArrayList();
    ArrayList<String> childFieldList = new ArrayList();
    ArrayList<ArrayList<String[]>> childFieldValueList = new ArrayList();
    StringBuffer remindFieldValue = new StringBuffer();
    String[] remindFiledArr = (remindField == null) ? new String[0] : remindField.split("S");
    for (int k = 0; k < remindFiledArr.length; k++) {
      if (remindFiledArr[k] != null && remindFiledArr[k].trim().length() >= 1)
        remindFieldValue.append(httpServletRequest.getParameter(remindFiledArr[k])); 
    } 
    String fieldNameStr = "";
    String tmpValue = "";
    Long recordId = null;
    String recordIdStr = null;
    String code = (new CustomFormBD()).getCode(tableId);
    if (code != null && !code.toUpperCase().equals("NULL") && code.trim().length() > 1) {
      String addFlag = httpServletRequest.getParameter("addFlag");
      if (addFlag != null && "batchAdd".equals(addFlag)) {
        recordIdStr = (new CustomFormBD()).save(httpServletRequest);
      } else {
        recordId = new Long((new CustomFormBD()).save(httpServletRequest));
      } 
    } else {
      for (int i = 0; i < fieldName.length; i++) {
        fieldList.add(fieldName[i].substring(0, fieldName[i].indexOf("_")));
        if (fieldName[i].endsWith("_text")) {
          fieldNameStr = fieldName[i].substring(0, fieldName[i].indexOf("_"));
          tmpValue = httpServletRequest.getParameter(fieldNameStr).replace('"', '\'');
          tmpValue = tmpValue.replaceAll("'", "''");
          valueList.add(tmpValue);
          if (remindField.indexOf("S" + fieldNameStr + "S") >= 0)
            if (httpServletRequest.getParameter("remindText_" + 
                fieldNameStr) != null) {
              String remindStr = httpServletRequest.getParameter(
                  "remindText_" + fieldNameStr);
              if (remindStr.endsWith(";"))
                remindStr = remindStr.substring(0, remindStr.length() - 1); 
              String[] remindValue = remindStr.split(";");
              for (int j = 0; j < remindValue.length; j++) {
                if (remindValue[j].substring(0, 
                    remindValue[j].indexOf("/")).equals(
                    httpServletRequest.getParameter(
                      fieldNameStr)))
                  remindFieldValue.append(remindValue[j]
                      .substring(remindValue[j].indexOf(
                          "/") + 1)); 
              } 
            } else {
              remindFieldValue.append(httpServletRequest
                  .getParameter(fieldNameStr));
            }  
        } else if (fieldName[i].endsWith("_html")) {
          fieldNameStr = fieldName[i].substring(0, 
              fieldName[i].indexOf("_"));
          BASE64 base64 = new BASE64();
          valueList.add(base64.BASE64Encoder(
                String.valueOf(httpServletRequest.getParameter(fieldNameStr)) + "_@ISBASE64CODE@"));
        } else if (fieldName[i].endsWith("_checkbox")) {
          fieldNameStr = fieldName[i].substring(0, 
              fieldName[i].indexOf("_"));
          String[] tmp = httpServletRequest.getParameterValues(
              fieldNameStr);
          if (tmp != null) {
            String tmp2 = "";
            for (int j = 0; j < tmp.length; j++)
              tmp2 = String.valueOf(tmp2) + tmp[j] + ","; 
            valueList.add(tmp2.substring(0, tmp2.length() - 1));
            if (remindField.indexOf("S" + fieldNameStr + "S") >= 0)
              if (httpServletRequest.getParameter(
                  "remindCheckBox_" + fieldNameStr) != null) {
                String remindStr = httpServletRequest
                  .getParameter("remindCheckBox_" + 
                    fieldNameStr);
                if (remindStr.endsWith(";"))
                  remindStr = 
                    remindStr.substring(0, 
                      remindStr.length() - 1); 
                String[] remindValue = remindStr.split(";");
                for (int m = 0; m < remindValue.length; m++) {
                  for (int n = 0; n < tmp.length; n++) {
                    if (tmp[n].equals(remindValue[m]
                        .substring(0, 
                          remindValue[m].indexOf("/")))) {
                      remindFieldValue.append(remindValue[
                            m].substring(remindValue[m]
                            .indexOf("/") + 1));
                      break;
                    } 
                  } 
                } 
              } else {
                remindFieldValue.append(tmp2.substring(0, 
                      tmp2.length() - 1));
              }  
          } else {
            valueBuffer.append("'',");
          } 
        } else if (fieldName[i].endsWith("_time")) {
          valueList.add(String.valueOf(httpServletRequest.getParameter(
                  String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_hour")) + 
              ":" + httpServletRequest.getParameter(
                String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_minute"));
          if (remindField.indexOf("S" + 
              fieldName[i].substring(0, 
                fieldName[i].indexOf("_")) + "S") >= 0) {
            remindFieldValue.append(httpServletRequest.getParameter(
                  
                  String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_hour"));
            remindFieldValue.append(":" + 
                httpServletRequest
                .getParameter(
                  String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + 
                  "_minute"));
          } 
        } else if (fieldName[i].endsWith("_date")) {
          valueList.add(String.valueOf(httpServletRequest.getParameter(
                  String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_day")) + 
              " " + httpServletRequest.getParameter(
                String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_hour") + 
              ":" + httpServletRequest.getParameter(
                String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_minute") + 
              ":" + httpServletRequest.getParameter(
                String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_second"));
          if (remindField.indexOf("S" + 
              fieldName[i].substring(0, 
                fieldName[i].indexOf("_")) + "S") >= 0) {
            remindFieldValue.append(String.valueOf(httpServletRequest.getParameter(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_day")) + " ");
            remindFieldValue.append(String.valueOf(httpServletRequest.getParameter(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_hour")) + ":");
            remindFieldValue.append(String.valueOf(httpServletRequest.getParameter(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_minute")) + ":");
            remindFieldValue.append(httpServletRequest.getParameter(String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_second"));
          } 
        } else if (fieldName[i].endsWith("_file")) {
          String[] fileName = httpServletRequest.getParameterValues(
              String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + 
              "_fileName");
          String[] saveName = httpServletRequest.getParameterValues(
              String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + 
              "_saveName");
          String tmp = "";
          if (fileName != null) {
            for (int j = 0; j < fileName.length; j++)
              tmp = String.valueOf(tmp) + fileName[j] + ":" + saveName[j] + "::"; 
            tmp = tmp.substring(0, tmp.length() - 2);
          } 
          valueList.add(tmp);
        } else if (fieldName[i].endsWith("_group")) {
          valueList.add("0");
          String name = fieldName[i].substring(0, 
              fieldName[i].indexOf("_"));
          String childTableName = httpServletRequest.getParameter(
              "childTable_" + name);
          childTableList.add(childTableName);
          String childFieldName = httpServletRequest.getParameter(
              "childField_" + name);
          childFieldName = childFieldName.replace(';', ',');
          childFieldList.add(childFieldName);
          ArrayList<String[]> childFieldValue = 
            new ArrayList();
          String[] childFieldNameArray = childFieldName.split(",");
          if (childFieldNameArray != null && 
            childFieldNameArray.length > 0 && 
            httpServletRequest.getParameterValues(
              childFieldNameArray[0]) != null) {
            int size = (httpServletRequest.getParameterValues(
                childFieldNameArray[0])).length;
            int j;
            for (j = 0; j < size; j++)
              childFieldValue.add(new String[
                    childFieldNameArray.length]); 
            for (j = 0; j < childFieldNameArray.length; j++) {
              String[] tmp = httpServletRequest
                .getParameterValues(
                  childFieldNameArray[j]);
              for (int m = 0; m < tmp.length; m++) {
                String[] loadStr = 
                  childFieldValue.get(m);
                loadStr[j] = tmp[m];
                childFieldValue.set(m, loadStr);
              } 
            } 
            childFieldValueList.add(childFieldValue);
          } 
        } else {
          valueBuffer.append("'',");
        } 
      } 
      String[] fieldType = httpServletRequest.getParameterValues("fieldType");
      recordId = (new WorkFlowCommonBD()).insertFormRecord(tableName, 
          fieldList, 
          valueList, 
          childTableList, 
          childFieldList, 
          childFieldValueList, 
          fieldType);
    } 
    if (recordId != null)
      if (oldRecordId != null && !"null".equals(oldRecordId) && !"".equals(oldRecordId))
        (new WorkFlowCommonBD()).deleteOldRecord(tableId, tableName, oldRecordId);  
    String preparetype = (httpServletRequest.getAttribute("preparetype") == null) ? "" : httpServletRequest.getAttribute("preparetype").toString();
    if ("saveToDraft".equals(preparetype) && !"null".equals(oldRecordId) && !"".equals(oldRecordId)) {
      CustomFormBD cbd = new CustomFormBD();
      String[] tables = cbd.getForeignTable(httpServletRequest.getParameter("Page_Id"));
      (new WorkFlowCommonBD()).updateFormRecord(recordId, tableName, Long.valueOf(Long.parseLong(oldRecordId)), tables, tableId);
      recordId = Long.valueOf(Long.parseLong(oldRecordId));
    } 
    Map<Object, Object> result = new HashMap<Object, Object>();
    result.put("id", recordId);
    result.put("idStr", recordIdStr);
    result.put("remindFieldValue", remindFieldValue.toString().replaceAll("null", ""));
    (new FlowWebservice()).executeWebservice(httpServletRequest, "save");
    return result;
  }
  
  public String update(HttpServletRequest httpServletRequest) {
    String recordId = httpServletRequest.getParameter("recordId");
    String tableId = httpServletRequest.getParameter("tableId");
    WorkFlowBD workFlowBD = new WorkFlowBD();
    String tableName = (new CustomFormBD()).getTable(tableId);
    StringBuffer remindFieldValue = new StringBuffer();
    String code = (new CustomFormBD()).getCode(tableId);
    if (code != null && !code.toUpperCase().equals("NULL") && code.trim().length() > 1) {
      (new CustomFormBD()).update(httpServletRequest);
    } else {
      StringBuffer updateValue = new StringBuffer();
      String[] fieldName = httpServletRequest.getParameterValues("fieldName");
      StringBuffer groupField = new StringBuffer();
      String remindField = httpServletRequest.getParameter("remindField");
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
              remindFieldValue.append(String.valueOf(httpServletRequest.getParameter(String.valueOf(str.substring(0, str.indexOf("_"))) + "_minute")) + ":");
              remindFieldValue.append(httpServletRequest.getParameter(String.valueOf(str.substring(0, str.indexOf("_"))) + "_second"));
            } 
          }  
      } 
      List<String> fieldList = new ArrayList();
      List<String> valueList = new ArrayList();
      if (fieldName != null) {
        String fieldNameStr = "";
        String tmpValue = "";
        for (int j = 0; j < fieldName.length; j++) {
          fieldNameStr = fieldName[j].substring(0, fieldName[j].indexOf("_"));
          fieldList.add(fieldNameStr);
          if (fieldName[j].endsWith("_text")) {
            valueList.add(httpServletRequest.getParameter(fieldNameStr));
          } else if (fieldName[j].endsWith("_html")) {
            BASE64 base64 = new BASE64();
            valueList.add(base64.BASE64Encoder(String.valueOf(httpServletRequest.getParameter(fieldNameStr)) + "_@ISBASE64CODE@"));
          } else if (fieldName[j].endsWith("_checkbox")) {
            String[] tmp = httpServletRequest.getParameterValues(fieldNameStr);
            if (tmp != null) {
              String tmp2 = "";
              for (int k = 0; k < tmp.length; k++)
                tmp2 = String.valueOf(tmp2) + tmp[k] + ","; 
              valueList.add(tmp2.substring(0, tmp2.length() - 1));
            } else {
              valueList.add("");
            } 
          } else if (fieldName[j].endsWith("_time")) {
            valueList.add(String.valueOf(httpServletRequest.getParameter(String.valueOf(fieldName[j].substring(0, fieldName[j].indexOf("_"))) + "_hour")) + ":" + httpServletRequest.getParameter(String.valueOf(fieldName[j].substring(0, fieldName[j].indexOf("_"))) + "_minute"));
          } else if (fieldName[j].endsWith("_date")) {
            valueList.add(
                String.valueOf(httpServletRequest.getParameter(String.valueOf(fieldName[j].substring(0, fieldName[j].indexOf("_"))) + "_day")) + " " + 
                httpServletRequest.getParameter(String.valueOf(fieldName[j].substring(0, fieldName[j].indexOf("_"))) + "_hour") + ":" + 
                httpServletRequest.getParameter(String.valueOf(fieldName[j].substring(0, fieldName[j].indexOf("_"))) + "_minute") + ":" + 
                httpServletRequest.getParameter(String.valueOf(fieldName[j].substring(0, fieldName[j].indexOf("_"))) + "_second"));
          } else if (fieldName[j].endsWith("_file")) {
            String[] fileName = httpServletRequest.getParameterValues(String.valueOf(fieldName[j].substring(0, fieldName[j].indexOf("_"))) + "_fileName");
            String[] saveName = httpServletRequest.getParameterValues(String.valueOf(fieldName[j].substring(0, fieldName[j].indexOf("_"))) + "_saveName");
            String tmp = "";
            if (fileName != null) {
              for (int k = 0; k < fileName.length; k++)
                tmp = String.valueOf(tmp) + fileName[k] + ":" + saveName[k] + "::"; 
              tmp = tmp.substring(0, tmp.length() - 2);
            } 
            valueList.add(tmp);
          } else if (fieldName[j].endsWith("_group")) {
            valueList.add("0");
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
            valueList.add("");
          } 
        } 
      } 
      if (fieldList.size() > 0)
        (new WorkFlowCommonBD()).updateFormRecord(tableName, fieldList, valueList, recordId); 
      if (groupField != null && !groupField.equals(""))
        workFlowBD.updateChildTable(tableId, recordId, groupField.toString()); 
    } 
    if (SystemCommon.getFormProgram() == 1)
      try {
        String processeId = httpServletRequest.getParameter("processId");
        String recorded = httpServletRequest.getParameter("recordId");
        String activityId = httpServletRequest.getParameter("curActivityId");
        DataToOtherBase dob = new DataToOtherBase(processeId, activityId, recorded);
        dob.dataSynchro();
        DataSynchronizeService service = new DataSynchronizeService(processeId, activityId, recordId);
        service.synchronizeData(httpServletRequest);
      } catch (Exception e) {
        e.printStackTrace();
      }  
    (new FlowWebservice()).executeWebservice(httpServletRequest, "node");
    return remindFieldValue.toString();
  }
  
  public String complete(HttpServletRequest request) {
    if (SystemCommon.getFormProgram() == 1)
      try {
        String processeId = request.getParameter("processId");
        String recordId = request.getParameter("recordId");
        String work = request.getParameter("work");
        DataToOtherBase dob = new DataToOtherBase(processeId, "-1", recordId);
        dob.dataSynchro();
        DataSynchronizeService service = new DataSynchronizeService(processeId, "-1", recordId);
        service.synchronizeData(request);
        SearchService.getInstance();
        String ifActiveUpdateDelete = SearchService.getIfActiveUpdateDelete();
        SearchService.getInstance();
        String isearchSwitch = SearchService.getiSearchSwitch();
        if ("1".equals(isearchSwitch) && recordId != null && ifActiveUpdateDelete != null && !"".equals(recordId) && !"".equals(ifActiveUpdateDelete) && !"no".equals(ifActiveUpdateDelete)) {
          SearchService.getInstance();
          SearchService.updateIndex(work, "jsf_workflow");
        } 
      } catch (Exception e) {
        e.printStackTrace();
      }  
    if (SystemCommon.getArchiveToInfo() == 1) {
      String recordId = request.getParameter("recordId");
      String tableId = request.getParameter("tableId");
      String processeId = request.getParameter("processId");
      String tableName = (new CustomFormBD()).getTable(tableId);
      DataSourceBase base = new DataSourceBase();
      ResultSet rs = null;
      List<String> list = new ArrayList<String>();
      boolean flag = false;
      try {
        base.begin();
        String sql = "SELECT infoChannelId,WORKFLOWPROCESSNAME FROM jsf_workflowprocess WHERE WF_WORKFLOWPROCESS_ID=" + processeId;
        rs = base.executeQuery(sql);
        if (rs.next()) {
          list.add((new StringBuilder(String.valueOf(rs.getString(2)))).toString());
          list.add(rs.getString(1));
          if (rs.getString(1) != null && !"".equals(rs.getString(1)) && !"null".equalsIgnoreCase(rs.getString(1)))
            flag = true; 
        } 
        rs.close();
        if (flag) {
          sql = "SELECT emp.empname,emp.emp_id,o.org_id,o.orgName,o.orgIdString FROM " + tableName + " j JOIN org_employee emp ON j." + tableName + 
            "_owner=emp.emp_id JOIN org_organization_user e ON emp.emp_id=e.EMP_ID JOIN org_organization o ON e.ORG_ID=o.org_id " + 
            "WHERE j." + tableName + "_id=" + recordId;
          rs = base.executeQuery(sql);
          if (rs.next()) {
            list.add(rs.getString(1));
            list.add(rs.getString(2));
            list.add(rs.getString(3));
            list.add(rs.getString(4));
            list.add(rs.getString(5));
          } 
          rs.close();
          sql = "SELECT wf_work_id,worktitle FROM jsf_work WHERE WORKPROCESS_ID=" + processeId + " AND WORKTABLE_ID=" + tableId + " AND " + 
            "WORKRECORD_ID=" + recordId + " and workstatus=100 ORDER BY wf_work_id";
          rs = base.executeQuery(sql);
          if (rs.next()) {
            list.add(rs.getString(1));
            String title = rs.getString(2);
            title = title.substring(2, title.length() - 6);
            list.set(0, title);
          } 
          rs.close();
          (new InfoArchives()).saveInfo(list, ((String)list.get(3)).toString(), "GZLC");
        } 
        base.end();
      } catch (Exception e) {
        base.end();
        e.printStackTrace();
      } 
    } 
    if (SystemCommon.getSYWorkflowHR() == 1)
      try {
        (new WorkflowSYHRData()).insertSYHRData(request);
      } catch (SQLException e) {
        e.printStackTrace();
      }  
    (new FlowWebservice()).executeWebservice(request, "complete");
    if ("rws".equalsIgnoreCase(SystemCommon.getCustomerName())) {
      String processeId = request.getParameter("processId");
      String processIdList = SysConfigReader.readConfigValue("RwsGDWorkFlowIdList", "ids");
      if (processIdList != null && processIdList.indexOf("," + processeId + ",") >= 0) {
        String recordId = request.getParameter("recordId");
        String tableId = request.getParameter("tableId");
        TongBuService.yuguidang(tableId, recordId, request.getSession(true).getAttribute("userId").toString());
      } 
    } 
    return "success";
  }
  
  public void delete(HttpServletRequest request) {}
  
  public void back(HttpServletRequest request) {}
}
