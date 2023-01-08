package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import javax.servlet.http.HttpServletRequest;

public class ElementHTMLGetterImpl_450 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      noWriteField.indexOf("," + fieldTemp[0][5] + ",") >= 0) {
      html = String.valueOf(html) + "<div style=\\\"float:left;width:60%;font-size:1em;\\\"><input type=hidden id=" + 
        field + 
        " name=" + 
        field + 
        "><input type=text style=font-size:1em; id=" + 
        field + 
        "_temp name=" + field + "_temp readonly></div>";
    } else {
      html = String.valueOf(html) + "<div style=\\\"float:left;width:60%;font-size:1em;\\\"><input type=hidden id=" + 
        field + 
        " name=" + 
        field + 
        "><input type=text id=" + 
        field + 
        "_temp name=" + 
        field + 
        "_temp style=\\\"width:100%;font-size:1em;\\\"></div>";
      html = String.valueOf(html) + "<div style=\\\"float:left;font-size:1em;\\\"><input  type=button id=" + 
        field + 
        "_btn name=" + 
        field + 
        "_btn onclick=\"" + 
        field + 
        "_enter(this);\" value=检索></div>";
    } 
    html = (new StringBuilder(String.valueOf(html))).toString();
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") < 0 && 
      noWriteField.indexOf("," + fieldTemp[0][5] + ",") < 0 && 
      fieldTemp[0][3].equals("1"))
      html = String.valueOf(html) + "<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + "><label class=mustFillcolor>*</label>"; 
    html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + 
      field + "').innerHTML='" + type + 
      "';document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "').innerHTML+='" + html + "';}";
    return html;
  }
  
  public String getEditHTML(String fieldValue, String hideField, String[][] fieldTemp, String fromDraft, String field, boolean isNum, int maxlen, String type, String computeFieldStr, HttpServletRequest request, String temp, String[] tempArr, String fieldId, String infoId, String pageId) {
    String html = "";
    fieldValue = fieldValue.replaceAll("'", "\\'").replaceAll("\"", "\\\\\"");
    String fieldValueTemp = "";
    if (fieldValue.length() > 0)
      if (fieldValue.indexOf("@@$@@") >= 0) {
        fieldValueTemp = fieldValue.substring(fieldValue.indexOf("@@$@@") + 5);
      } else {
        fieldValueTemp = fieldValue;
      }  
    if (hideField != null && (
      hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || hideField.indexOf("," + fieldTemp[0][5] + ",") >= 0)) {
      html = "<input type=hidden id=" + field + " name=" + field + " value=\"" + fieldValue + "\"><div style=\\\"float:left;width:60%;font-size:1em;\\\"><input type=hidden id=" + field + "_temp name=" + field + "_temp value=\"" + fieldValueTemp + "\">" + fieldValueTemp + "</div>";
    } else {
      html = "<input type=hidden id=" + field + " name=" + field + " value=\"" + fieldValue + "\"><div style=\\\"float:left;width:60%;font-size:1em;\\\"><input type=text id=" + field + "_temp name=" + field + "_temp value=\"" + fieldValueTemp + "\" style=\\\"width:100%;font-size:1em;\\\"></div>";
      html = String.valueOf(html) + "<div style=\\\"float:left;font-size:1em;\\\"><input  type=button id=" + field + "_btn name=" + field + "_btn onclick=\"" + field + "_enter(this);\" value=检索></div>";
    } 
    html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + field + "').innerHTML='" + type + "';document.getElementById('" + fieldTemp[0][0] + "-" + field + "').innerHTML+='" + html + "';}";
    return html;
  }
  
  public String getForeignEditHTMLForWeiXin(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr, boolean isHide, String type, boolean isNum, int maxlen, String temp, String[] tempArr) {
    String html = "";
    fieldValue = fieldValue.replaceAll("'", "\\'").replaceAll("\"", "\\\\\"");
    String fieldValueTemp = "";
    if (fieldValue.length() > 0)
      if (fieldValue.indexOf("@@$@@") >= 0) {
        fieldValueTemp = fieldValue.substring(fieldValue.indexOf("@@$@@") + 5);
      } else {
        fieldValueTemp = fieldValue;
      }  
    if (isHide) {
      html = "<input type=hidden id=" + field + " name=" + field + " value=\"" + fieldValue + "\"><div style=\\\"float:left;width:60%;\\\"><input type=hidden id=" + field + "_temp name=" + field + "_temp value=\"" + fieldValueTemp + "\">" + fieldValueTemp + "</div>";
    } else {
      html = "<input type=hidden id=" + field + " name=" + field + " value=\"" + fieldValue + "\"><div style=\\\"float:left;width:60%;font-size:1em;\\\"><input type=text id=" + field + "_temp name=" + field + "_temp value=\"" + fieldValueTemp + "\" style=\\\"width:100%;font-size:1em;\\\"></div>";
      html = String.valueOf(html) + "<div style=\\\"float:left;font-size:1em;\\\"><input style=font-size:1em; type=button id=" + field + "_btn name=" + field + "_btn onclick=\"" + field + "_enter(this);\" value=检索></div>";
    } 
    html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + 
      "'))\n{document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + type + "';document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML+='" + html + "';}";
    return html;
  }
}
