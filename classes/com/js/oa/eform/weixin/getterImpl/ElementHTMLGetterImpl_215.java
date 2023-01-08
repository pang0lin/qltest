package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import com.js.system.util.StaticParam;
import javax.servlet.http.HttpServletRequest;

public class ElementHTMLGetterImpl_215 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    String curEmpNumber = StaticParam.getEmpNumberByEmpId(request
        .getSession().getAttribute("userId").toString());
    html = "<input type=text name=" + field + " id=" + field + 
      " style=width:" + (
      fieldTemp[0][3].equals("1") ? "92" : "100") + 
      "%;font-size:1em; " + 
      "class=flowInput title=多个工号之间用逗号隔开 value=\\\"" + curEmpNumber + 
      "\\\" >";
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") < 0 && 
      noWriteField.indexOf(fieldTemp[0][5]) < 0 && 
      fieldTemp[0][3].equals("1"))
      html = String.valueOf(html) + "<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + "><label class=mustFillcolor>*</label>"; 
    html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + 
      field + "').innerHTML='" + type + 
      "';document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "').innerHTML+=\"" + html + "\";}";
    return html;
  }
  
  public String getEditHTML(String fieldValue, String hideField, String[][] fieldTemp, String fromDraft, String field, boolean isNum, int maxlen, String type, String computeFieldStr, HttpServletRequest request, String temp, String[] tempArr, String fieldId, String infoId, String pageId) {
    String html = "";
    fieldValue = fieldValue.replaceAll("'", "\\'").replaceAll("\"", "\\\\\"");
    if ("".equals(fieldValue) || "null".equalsIgnoreCase(fieldValue))
      fieldValue = StaticParam.getEmpNumberByEmpId(request.getSession().getAttribute("userId").toString()); 
    if (hideField != null && (
      hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || hideField.indexOf(fieldTemp[0][5]) >= 0)) {
      if ("1".equals(fromDraft)) {
        html = "<input style=width:" + (fieldTemp[0][3].equals("1") ? "92" : "100") + "% ;font-size:1em; type=text class=flowInput id=" + 
          field + " name=" + field + " value='" + fieldValue + "' readonly>";
      } else {
        html = (fieldValue == null) ? "&nbsp" : fieldValue.replaceAll("\n", "<br>").replaceAll("\r", "");
        type = "<input type=hidden id=" + field + "  name=" + field + " value=\"" + fieldValue + "\">";
      } 
    } else {
      html = "<input style=width:" + (fieldTemp[0][3].equals("1") ? "92" : "100") + "%;font-size:1em; type=text title=多个工号之间用逗号隔开 class=flowInput id=" + 
        field + " name=" + field + " value='" + fieldValue + "'>" + ((
        fieldTemp[0][3].equals("1") && hideField.indexOf("," + fieldTemp[0][0] + ",") < 0 && hideField.indexOf(fieldTemp[0][5]) < 0) ? (
        "<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + "><label class=mustFillcolor>*</label>") : "");
    } 
    html = "\nif(document.getElementById('" + fieldTemp[0][0] + "-" + field + "'))\n" + 
      "{document.getElementById('" + fieldTemp[0][0] + "-" + field + "').innerHTML='" + type + "';" + 
      "document.getElementById('" + fieldTemp[0][0] + "-" + field + "').innerHTML+=\"" + html + "\";}";
    return html;
  }
  
  public String getForeignEditHTMLForWeiXin(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr, boolean isHide, String type, boolean isNum, int maxlen, String temp, String[] tempArr) {
    String html = "";
    fieldValue = fieldValue.replaceAll("'", "\\'").replaceAll("\"", "\\\\\"");
    if ("".equals(fieldValue) || "null".equalsIgnoreCase(fieldValue))
      fieldValue = StaticParam.getEmpNumberByEmpId(request.getSession().getAttribute("userId").toString()); 
    if (hideField != null && (
      hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || hideField.indexOf(fieldTemp[0][5]) >= 0)) {
      if ("1".equals(fromDraft)) {
        html = "<input style=width:" + (fieldTemp[0][3].equals("1") ? "92" : "100") + "% ;font-size:1em; type=text class=flowInput id=" + 
          field + " name=" + field + " value='" + fieldValue + "' readonly>";
      } else {
        html = (fieldValue == null) ? "&nbsp" : fieldValue.replaceAll("\n", "<br>").replaceAll("\r", "");
        type = "<input type=hidden id=" + field + "  name=" + field + " value=\"" + fieldValue + "\">";
      } 
    } else {
      html = "<input style=width:" + (fieldTemp[0][3].equals("1") ? "92" : "100") + "%;font-size:1em; type=text class=flowInput id=" + 
        field + " name=" + field + " value='" + fieldValue + "'>" + ((
        fieldTemp[0][3].equals("1") && hideField.indexOf("," + fieldTemp[0][0] + ",") < 0 && hideField.indexOf(fieldTemp[0][5]) < 0) ? (
        "<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + "><label class=mustFillcolor>*</label>") : "");
    } 
    html = "\nif(document.getElementById('" + fieldTemp[0][0] + "-" + field + "'))\n" + 
      "{document.getElementById('" + fieldTemp[0][0] + "-" + field + "').innerHTML='" + type + "';" + 
      "document.getElementById('" + fieldTemp[0][0] + "-" + field + "').innerHTML+=\"" + html + "\";}";
    return html;
  }
}
