package com.js.oa.form.esesj;

import com.js.oa.esesj.bean.EsesjBean;
import com.js.oa.form.ClientInfoFromWeb;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;

public class CreateProcessForCooperation {
  public void createProcess(String id, String type, HttpServletRequest request) {
    ResourceBundle resource = ResourceBundle.getBundle("esesj");
    String cooProcessId = resource.getString("cooProcessId");
    String cooTableId = resource.getString("cooTableId");
    String curUserId = request.getSession(true).getAttribute("userId").toString();
    String curUserName = request.getSession(true).getAttribute("userName").toString();
    EsesjBean es = new EsesjBean();
    String ownerId = es.getInfoById(id, type)[1];
    StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
    xml.append("<WorkFlow>");
    xml.append("<Process processId=\"").append(cooProcessId).append("\"/>");
    xml.append("<UserName  submitName=\"").append(es.getEmployeeVOById(curUserId).getUserAccounts()).append("\"  receiveName=\"").append(es.getEmployeeVOById(ownerId).getUserAccounts()).append("\"/>");
    xml.append("<Data>");
    xml.append("<Table tableId=\"").append(cooTableId).append("\" tableName=\"Cooperation").append("\">");
    xml.append("<Column>");
    xml.append("<field name=\"RecordItem\" type=\"varchar\">").append(es.getInfoById(id, type)[3]).append("</field>");
    xml.append("<field name=\"Collaborator\" type=\"varchar\">").append(curUserName).append("</field>");
    xml.append("<field name=\"CooperationID\" type=\"varchar\">").append(String.valueOf(id) + "_" + type).append("</field>");
    xml.append("</Column>");
    xml.append("</Table>");
    xml.append("</Data>");
    xml.append("</WorkFlow>");
    ClientInfoFromWeb webservice = new ClientInfoFromWeb();
    webservice.createNewProcess(xml.toString());
  }
}
