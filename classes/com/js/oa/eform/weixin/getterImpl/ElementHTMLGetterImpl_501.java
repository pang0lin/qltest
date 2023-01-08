package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import com.js.oa.userdb.util.DbOpt;
import javax.servlet.http.HttpServletRequest;

public class ElementHTMLGetterImpl_501 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    html = getRelationHTML(field, fieldTemp[0][0], fieldTemp[0][3], 
        noWriteField);
    return html;
  }
  
  private String getRelationHTML(String fieldName, String fieldId, String fieldNotNull, String noWriteField) {
    String type = "<input type=hidden name=relation_object id=relation_object value=" + 
      fieldName + " />";
    type = String.valueOf(type) + "<input type=hidden name=" + fieldName + "_relation_type id=" + 
      fieldName + "_relation_type value=jsflow >";
    String html = "<div style=width:100%; align=left id=relationObjectDIV-" + 
      fieldId + " name=relationObjectDIV-" + fieldId + " />";
    if (noWriteField.indexOf("," + fieldId + ",") < 0 && 
      noWriteField.indexOf(fieldName) < 0) {
      html = String.valueOf(html) + "<input type=button class=btnButton4font onclick=\\\"selectRelationObject('" + 
        fieldName + 
        "','" + 
        fieldId + 
        "','jsflow');\\\" value=\\\"相关流程\\\">";
      html = String.valueOf(html) + "<div id=relationObjectData-" + fieldId + 
        " name=relationObjectData-" + fieldId + " ></div>";
    } 
    if (noWriteField.indexOf("," + fieldId + ",") < 0 && 
      noWriteField.indexOf(fieldName) < 0 && 
      fieldNotNull.equals("1"))
      html = String.valueOf(html) + "<input type=hidden name=mustWrite id=mustWrite value=" + 
        fieldName + "><label class=mustFillcolor>*</label>"; 
    html = String.valueOf(html) + "<input type=hidden name=" + fieldName + " id=" + fieldName + 
      ">";
    html = String.valueOf(html) + "</div>";
    html = "if(document.getElementById('" + fieldId + "-" + fieldName + 
      "'))\n{document.getElementById('" + fieldId + "-" + fieldName + 
      "').innerHTML='" + type + "';document.getElementById('" + 
      fieldId + "-" + fieldName + "').innerHTML+=\"" + html + 
      "\";}";
    return html;
  }
  
  public String getEditHTML(String fieldValue, String hideField, String[][] fieldTemp, String fromDraft, String field, boolean isNum, int maxlen, String type, String computeFieldStr, HttpServletRequest request, String temp, String[] tempArr, String fieldId, String infoId, String pageId) {
    String html = "";
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      if (request != null && request.getParameter("table") != null && !"null".equals(request.getParameter("table")) && !"".equals(request.getParameter("table")))
        pageId = request.getParameter("table"); 
      html = getRelationEditHTML(pageId, infoId, field, fieldTemp[0][0], fieldTemp[0][3], hideField, dbopt);
      dbopt.close();
    } catch (Exception ex) {
      if (dbopt != null)
        try {
          dbopt.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return html;
  }
  
  private String getRelationEditHTML(String pageId, String infoId, String fieldName, String fieldId, String fieldNotNull, String noWriteField, DbOpt dbopt) throws Exception {
    boolean canEdit = false;
    String type = "<input type=hidden name=relation_object id=relation_object value=" + fieldName + " />";
    type = String.valueOf(type) + "<input type=hidden name=" + fieldName + "_relation_type id=" + fieldName + "_relation_type value=jsflow >";
    String html = "<div style=width:100%; align=left id=relationObjectDIV-" + fieldId + " name=relationObjectDIV-" + fieldId + " />";
    if (noWriteField.indexOf("," + fieldId + ",") < 0 && 
      noWriteField.indexOf(fieldName) < 0) {
      canEdit = true;
      html = String.valueOf(html) + "<input type=button class=btnButton4font onclick=\\\"selectRelationObject('" + fieldName + "','" + fieldId + "','jsflow');\\\" value=\\\"相关流程\\\">";
    } 
    String[][] relationData = (String[][])null;
    try {
      relationData = dbopt.executeQueryToStrArr2("select relationinfoname,relationsubid,relationinfoid from oa_relationdata where moduletype='jsflow' and modulesubid=" + 
          
          pageId + " and infoId=" + infoId + " and relationobjecttype='jsflow'" + 
          " and (fieldName is null or fieldName='' or fieldName='" + fieldName + "')", 3);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    html = String.valueOf(html) + "<div id=relationObjectData-" + fieldId + " name=relationObjectData-" + fieldId + " >";
    String hiddenText = "";
    if (relationData != null)
      for (int i = 0; i < relationData.length; i++) {
        String relationSubId = relationData[i][1];
        String relationInfoId = relationData[i][2];
        hiddenText = String.valueOf(hiddenText) + "flow-" + relationSubId + "-" + relationInfoId + ";";
        html = String.valueOf(html) + "<span id=flow-" + relationSubId + "-" + relationInfoId + " name=flow-" + relationSubId + "-" + relationInfoId + ">";
        html = String.valueOf(html) + "<a href=\\\"#\\\" onclick=\\\"JSMainWinOpen('/jsoa/jsflow/workflow_relationflow.jsp?tableId=" + relationSubId + "&record=" + relationInfoId + "&workStatus=100&curStatus=100&fromDossierData=y&notPC=yes','','')\\\">" + relationData[i][0] + "</a>";
        if (canEdit) {
          html = String.valueOf(html) + "&nbsp;<img src=\\\"/jsoa/images/del.gif\\\" onclick=\\\"deleteRelationObjMust('flow-" + relationSubId + "-" + relationInfoId + "','" + fieldName + "');\\\">";
          html = String.valueOf(html) + "<input type=\\\"hidden\\\" id=\\\"" + fieldName + "_relation_data\\\" name=\\\"" + fieldName + "_relation_data\\\" value=\\\"" + relationSubId + ";" + relationInfoId + "\\\">" + 
            "<input type=\\\"hidden\\\" id=\\\"" + fieldName + "_relation_title\\\" name=\\\"" + fieldName + "_relation_title\\\" value=\\\"" + relationData[i][0] + "\\\">";
        } 
        html = String.valueOf(html) + "&nbsp;&nbsp;</span>";
      }  
    html = String.valueOf(html) + "</div>";
    if (noWriteField.indexOf("," + fieldId + ",") < 0 && 
      noWriteField.indexOf(fieldName) < 0 && 
      fieldNotNull.equals("1"))
      html = String.valueOf(html) + "<input type=hidden name=mustWrite id=mustWrite value=" + 
        fieldName + "><label class=mustFillcolor>*</label>"; 
    html = String.valueOf(html) + "<input type=hidden name=" + fieldName + " id=" + fieldName + " value=" + hiddenText + " />";
    html = String.valueOf(html) + "</div>";
    if (canEdit) {
      html = "if(document.getElementById('" + fieldId + 
        "-" + fieldName + "'))\n{document.getElementById('" + 
        fieldId + "-" + fieldName + 
        "').innerHTML='" + type + 
        "';document.getElementById('" + 
        fieldId + "-" + fieldName + 
        "').innerHTML+=\"" + html + "\";}";
    } else {
      html = "if(document.getElementById('" + fieldId + 
        "-" + fieldName + "'))\n{document.getElementById('" + 
        fieldId + "-" + fieldName + 
        "').innerHTML=\"" + html + "\";}";
    } 
    return html;
  }
  
  public String getForeignEditHTMLForWeiXin(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr, boolean isHide, String type, boolean isNum, int maxlen, String temp, String[] tempArr) {
    return null;
  }
}
