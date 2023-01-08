package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import javax.servlet.http.HttpServletRequest;

public class ElementHTMLGetterImpl_102 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      noWriteField.indexOf(fieldTemp[0][5]) >= 0) {
      html = "<input type=password style=width:" + (
        fieldTemp[0][3].equals("1") ? "92" : "100") + 
        "%;font-size:1em; name=" + field + " id=" + field + 
        " class=flowInput readonly>";
    } else {
      html = "<input type=password style=width:" + (
        fieldTemp[0][3].equals("1") ? "92" : "100") + 
        "%;font-size:1em; name=" + 
        field + 
        " id=" + 
        field + 
        " class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this)  " + (
        isNum ? "  onblur=checkNum(this);checkSize(this);" : (
        "maxlength=\"" + maxlen + "\"")) + ">";
      if (fieldTemp[0][3].equals("1") && 
        noWriteField.indexOf("," + fieldTemp[0][0] + ",") < 0 && noWriteField
        .indexOf(fieldTemp[0][5]) < 0)
        html = String.valueOf(html) + "<input type=hidden name=mustWrite id=mustWrite value=" + 
          field + "><label class=mustFillcolor>*</label>"; 
    } 
    html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + 
      field + "').innerHTML='" + type + html + "';}";
    return html;
  }
  
  public String getEditHTML(String fieldValue, String hideField, String[][] fieldTemp, String fromDraft, String field, boolean isNum, int maxlen, String type, String computeFieldStr, HttpServletRequest request, String temp, String[] tempArr, String fieldId, String infoId, String pageId) {
    if (hideField != null && (hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || hideField.indexOf(fieldTemp[0][5]) >= 0)) {
      html = (fieldValue == null) ? "&nbsp" : fieldValue.replaceAll("\n", "<br>").replaceAll("\r", "");
      html = "<input type=password style=width:" + (fieldTemp[0][3].equals("1") ? "92" : "100") + "%;font-size:1em;   id=" + field + 
        " name=" + field + " value='" + fieldValue + "' readOnly> ";
      type = "";
    } else {
      html = "<input type=password style=width:" + (fieldTemp[0][3].equals("1") ? "92" : "100") + "%;font-size:1em; id=" + field + 
        " name=" + field + " value='" + fieldValue + "' " + (
        isNum ? "  onblur=checkNum(this);checkSize(this);" : ("maxlength='" + maxlen + "'")) + ">" + ((
        fieldTemp[0][3].equals("1") && hideField.indexOf("," + fieldTemp[0][0] + ",") < 0 && hideField.indexOf(fieldTemp[0][5]) < 0) ? (
        "<input type=hidden name=mustWrite id=mustWrite value=" + field + 
        "><label class=mustFillcolor>*</label>") : "");
    } 
    String html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + "'))\n{document.getElementById('" + 
      fieldTemp[0][0] + "-" + field + "').innerHTML='" + type + "';document.getElementById('" + 
      fieldTemp[0][0] + "-" + field + "').innerHTML+=\"" + html + "\";}";
    return html;
  }
  
  public String getForeignEditHTMLForWeiXin(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr, boolean isHide, String type, boolean isNum, int maxlen, String temp, String[] tempArr) {
    String html = "";
    if (isHide) {
      html = "<input type=password style=width:" + (fieldTemp[index][3].equals("1") ? "92" : "100") + "%;font-size:1em; id=" + field + 
        " name=" + field + " value='" + fieldValue + "' readOnly> ";
      type = (new StringBuilder(String.valueOf(type))).toString();
    } else {
      html = "<input type=password style=width:" + (
        fieldTemp[index][3].equals("1") ? "92" : "100") + 
        "%;font-size:1em; id=" + field + 
        " name=" + field + " id=" + field + " value='" + fieldValue + 
        "' " + (
        isNum ? " onblur=checkNum(this);checkSize(this);" : (
        "maxlength='" + maxlen + "'")) + ">" + (
        fieldTemp[index][3].equals("1") ? (
        "<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + 
        "><label class=mustFillcolor>*</label>") : 
        "");
    } 
    html = "if(document.getElementsByName('" + 
      fieldTemp[index][0] + "-" + field + "')[" + seq + 
      "])\n{document.getElementsByName('" + 
      fieldTemp[index][0] + "-" + field + "')[" + seq + 
      "].innerHTML='" + type + 
      "';document.getElementsByName('" + 
      fieldTemp[index][0] + "-" + field + "')[" + 
      seq + "].innerHTML+=\"" + html + "\";}";
    return html;
  }
}
