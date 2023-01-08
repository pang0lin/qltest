package com.js.oa.form;

import cn.flyingsoft.oais.service.webservice.OACallArchiveServiceStub;
import com.js.lang.NoOpEntityResolver;
import com.js.oa.eform.service.CustomFormBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.relproject.bean.RelProjectBean;
import com.js.util.util.BASE64;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class WorkflowToProjectItem {
  private static Map itemMap = null;
  
  private void initItemInfo() {
    if (itemMap == null) {
      itemMap = new HashMap<Object, Object>();
      try {
        String path = "";
        path = System.getProperty("user.dir");
        if (!"".equals(path)) {
          String configFile = String.valueOf(path) + "/jsconfig/workflowToProjectItem.xml";
          FileInputStream configFileInputStream = new FileInputStream(new File(configFile));
          SAXBuilder builder = new SAXBuilder();
          builder.setValidation(false);
          builder.setEntityResolver(new NoOpEntityResolver());
          Document doc = builder.build(configFileInputStream);
          Element node = doc.getRootElement();
          node = node.getChild("workflow");
          String tableName = node.getChild("table").getAttributeValue("tablename");
          itemMap.put("tableName", tableName);
          List<Element> nodeList = node.getChild("fields").getChildren("field");
          for (int i = 0; i < nodeList.size(); i++) {
            node = nodeList.get(i);
            itemMap.put(node.getAttributeValue("itemname"), node.getAttributeValue("fieldname"));
          } 
        } 
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
    } 
  }
  
  public Map save(HttpServletRequest httpServletRequest) {
    String tableId = httpServletRequest.getParameter("tableId");
    String oldRecordId = httpServletRequest.getParameter("recordId");
    String remindField = (httpServletRequest.getParameter("remindField") == null) ? "" : httpServletRequest.getParameter("remindField");
    WorkFlowBD workFlowFormBD = new WorkFlowBD();
    String tableName = (new CustomFormBD()).getTable(tableId);
    String[] fieldName = httpServletRequest.getParameterValues("fieldName");
    StringBuffer fieldBuffer = new StringBuffer();
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
    String code = (new CustomFormBD()).getCode(tableId);
    if (code != null && !code.toUpperCase().equals("NULL") && code.trim().length() > 1) {
      recordId = new Long((new CustomFormBD()).save(httpServletRequest));
      String projectId = httpServletRequest.getParameter("relproject");
      if (itemMap == null)
        initItemInfo(); 
      String itemTitle = httpServletRequest.getParameter(itemMap.get("itemTitle").toString());
      String startTime = httpServletRequest.getParameter(itemMap.get("startTime").toString());
      String startHour = httpServletRequest.getParameter(String.valueOf(itemMap.get("startTime").toString()) + "hours");
      String startMini = httpServletRequest.getParameter(String.valueOf(itemMap.get("startTime").toString()) + "minutes");
      String endTime = httpServletRequest.getParameter(itemMap.get("endTime").toString());
      String endHour = httpServletRequest.getParameter(String.valueOf(itemMap.get("endTime").toString()) + "hours");
      String endMini = httpServletRequest.getParameter(String.valueOf(itemMap.get("endTime").toString()) + "minutes");
      int remind = 0;
      if (httpServletRequest.getParameter(itemMap.get("remind").toString()) == null)
        Integer.parseInt(httpServletRequest.getParameter(itemMap.get("remind").toString())); 
      startTime = startTime.replaceAll("-", "/");
      endTime = endTime.replaceAll("-", "/");
      if (startHour != null && !"null".equals(startHour) && !"".equals(startHour))
        startTime = String.valueOf(startTime) + " " + startHour + ":" + startMini + ":00"; 
      if (endHour != null && !"null".equals(endHour) && !"".equals(endHour))
        endTime = String.valueOf(endTime) + " " + endHour + ":" + endMini + ":00"; 
      RelProjectBean bean = new RelProjectBean();
      bean.addItemFromWorkflow(projectId, itemTitle, startTime, endTime, remind);
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
              ":" + 
              httpServletRequest.getParameter(
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
              " " + 
              httpServletRequest.getParameter(
                String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_hour") + 
              ":" + 
              httpServletRequest.getParameter(
                String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_minute"));
          if (remindField.indexOf("S" + 
              fieldName[i].substring(0, 
                fieldName[i].indexOf("_")) + "S") >= 0) {
            remindFieldValue.append(String.valueOf(httpServletRequest.getParameter(
                    
                    String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_day")) + " ");
            remindFieldValue.append(String.valueOf(httpServletRequest.getParameter(
                    
                    String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_hour")) + ":");
            remindFieldValue.append(httpServletRequest.getParameter(
                  
                  String.valueOf(fieldName[i].substring(0, fieldName[i].indexOf("_"))) + "_minute"));
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
          ArrayList<String[]> childFieldValue = new ArrayList();
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
    Map<Object, Object> result = new HashMap<Object, Object>();
    result.put("id", recordId);
    result.put("remindFieldValue", remindFieldValue.toString().replaceAll("null", ""));
    if (recordId != null)
      if (oldRecordId != null && !"null".equals(oldRecordId) && !"".equals(oldRecordId))
        (new WorkFlowCommonBD()).deleteOldRecord(tableId, tableName, oldRecordId);  
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
              remindFieldValue.append(httpServletRequest.getParameter(String.valueOf(str.substring(0, str.indexOf("_"))) + "_minute"));
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
            valueList.add(String.valueOf(httpServletRequest.getParameter(String.valueOf(fieldName[j].substring(0, fieldName[j].indexOf("_"))) + "_day")) + " " + httpServletRequest.getParameter(String.valueOf(fieldName[j].substring(0, fieldName[j].indexOf("_"))) + "_hour") + ":" + httpServletRequest.getParameter(String.valueOf(fieldName[j].substring(0, fieldName[j].indexOf("_"))) + "_minute"));
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
    return remindFieldValue.toString();
  }
  
  public void complete(HttpServletRequest request) {
    WorkFlowArchives wfa = new WorkFlowArchives();
    if (WorkFlowArchives.isUse() && wfa.isLendProcess(request.getParameter("recordId"))) {
      String id = wfa.getArchivesId(request.getParameter("recordId"));
      String userName = wfa.getUserName(request.getParameter("recordId"));
      Map map = wfa.getColumn(WorkFlowArchives.getTableName());
      String fileName = request.getParameter(map.get("FILE_NAME").toString().toLowerCase());
      String fileNum = request.getParameter(map.get("FILE_NUM").toString().toLowerCase());
      String fileRead = request.getParameter(map.get("FILE_READ").toString().toLowerCase());
      String fileRead1 = fileRead;
      StringBuffer buffer = new StringBuffer();
      String fileReadDay = request.getParameter(map.get("FILE_READ_DAY").toString().toLowerCase());
      String fileReadTime = request.getParameter(map.get("FILE_READ_TIME").toString().toLowerCase());
      String fileDownload = request.getParameter(map.get("FILE_DOWNLOAD").toString().toLowerCase());
      String fileDownload1 = fileDownload;
      String fileDownloadDay = request.getParameter(map.get("FILE_DOWNLOAD_DAY").toString().toLowerCase());
      String fileDownloadTime = request.getParameter(map.get("FILE_DOWNLOAD_TIME").toString().toLowerCase());
      String fileLend = request.getParameter(map.get("FILE_LEND").toString().toLowerCase());
      String fileLend1 = fileLend;
      if (fileDownload.equals("0")) {
        fileDownload = "noHave";
        buffer.append(fileDownload);
      } else {
        fileDownload = "FileDownLoad";
        buffer.append(String.valueOf(fileDownload) + "_" + fileDownloadDay + "_" + fileDownloadTime + ";");
      } 
      if (fileRead.equals("0")) {
        fileRead = "noHave";
        buffer.append(fileRead);
      } else {
        fileRead = "FileRead";
        buffer.append(String.valueOf(fileRead) + "_" + fileReadDay + "_" + fileReadTime + ";");
      } 
      if (fileLend.equals("0")) {
        fileLend = "noHave";
      } else {
        fileLend = "FileLend";
      } 
      buffer.append(fileLend);
      String dataHaveRightList = buffer.toString();
      try {
        OACallArchiveServiceStub stub = new OACallArchiveServiceStub();
        OACallArchiveServiceStub.FinishWorkFlow finish = new OACallArchiveServiceStub.FinishWorkFlow();
        finish.setUsername(userName);
        finish.setStatus("");
        finish.setWorkflowID(id);
        finish.setDataHavaRightlist(dataHaveRightList);
        stub.finishWorkFlow(finish);
        wfa.updateArchives(request.getParameter("recordId"), fileName, fileNum, fileRead1, fileReadDay, fileDownload1, fileDownloadDay, fileLend1, fileReadTime, fileDownloadTime);
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
  
  public void delete(HttpServletRequest request) {}
  
  public void back(HttpServletRequest request) {}
}
