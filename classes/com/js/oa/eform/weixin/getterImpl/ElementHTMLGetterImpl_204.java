package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

public class ElementHTMLGetterImpl_204 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    Date now = new Date();
    type = "<input type=hidden name=" + field + "_type id=" + field + 
      "_type value=varchar>";
    String dateT = String.valueOf(String.valueOf(now.getYear() + 1900)) + 
      "-" + (
      (now.getMonth() >= 0) ? String.valueOf(now.getMonth() + 1) : (
      "0" + String.valueOf(now.getMonth() + 1))) + 
      "-" + (
      (now.getDate() > 9) ? String.valueOf(now.getDate()) : ("0" + 
      String.valueOf(now.getDate())));
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      noWriteField.indexOf(fieldTemp[0][5]) >= 0) {
      type = String.valueOf(type) + dateT + "<input type=hidden name=" + field + " id=" + field + 
        " value=" + dateT + ">";
    } else {
      type = String.valueOf(type) + dateT + "<input type=hidden name=" + field + " id=" + field + 
        " value=" + dateT + ">";
    } 
    html = String.valueOf(html) + "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + 
      field + "').innerHTML=\"" + type + "\";}";
    return html;
  }
  
  public String getEditHTML(String fieldValue, String hideField, String[][] fieldTemp, String fromDraft, String field, boolean isNum, int maxlen, String type, String computeFieldStr, HttpServletRequest request, String temp, String[] tempArr, String fieldId, String infoId, String pageId) {
    String html = "";
    Date now = new Date();
    if ((hideField != null && 
      hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0) || 
      hideField.indexOf(fieldTemp[0][5]) >= 0) {
      if (fieldValue == null || fieldValue.length() < 8)
        fieldValue = String.valueOf(String.valueOf(now.getYear() + 1900)) + 
          "-" + ((now.getMonth() >= 0) ? String.valueOf(now.getMonth() + 1) : ("0" + String.valueOf(now.getMonth() + 1))) + 
          "-" + ((now.getDate() > 9) ? String.valueOf(now.getDate()) : ("0" + String.valueOf(now.getDate()))); 
      html = (fieldValue == null) ? "&nbsp" : fieldValue.replaceAll("\n", "<br>").replaceAll("\r", "");
      html = String.valueOf(html) + "<input type=hidden id=" + field + " name=" + field + " value='" + fieldValue + "'>";
    } else {
      if (fieldValue == null || fieldValue.length() < 8)
        fieldValue = String.valueOf(String.valueOf(now.getYear() + 1900)) + 
          "-" + ((now.getMonth() >= 0) ? String.valueOf(now.getMonth() + 1) : ("0" + String.valueOf(now.getMonth() + 1))) + 
          "-" + ((now.getDate() > 9) ? String.valueOf(now.getDate()) : ("0" + String.valueOf(now.getDate()))); 
      html = String.valueOf(fieldValue) + "<input type=hidden id=" + field + " name=" + field + " value='" + fieldValue + "'>";
    } 
    html = "if(document.getElementById('" + 
      fieldTemp[0][0] + "-" + field + 
      "'))\n{document.getElementById('" + 
      fieldTemp[0][0] + "-" + field + 
      "').innerHTML=\"" + html + type + "\";}";
    return html;
  }
  
  public String getForeignEditHTMLForWeiXin(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr, boolean isHide, String type, boolean isNum, int maxlen, String temp, String[] tempArr) {
    String html = "";
    if (isHide) {
      html = (fieldValue == null) ? "&nbsp" : fieldValue.replaceAll("\n", "<br>").replaceAll("\r", "");
      type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
    } else {
      html = 
        "<input style=width:" + (fieldTemp[index][3].equals("1") ? "92" : "100") + "%;font-size:1em; type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) value='" + 
        fieldValue + 
        "'>";
    } 
    html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
      fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML=\"" + html + type + "\";}";
    return html;
  }
}
