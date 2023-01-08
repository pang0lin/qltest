package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import javax.servlet.http.HttpServletRequest;

public class ElementHTMLGetterImpl_203 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String jisuanSet = "";
    if (fieldTemp[0][6].length() > 0) {
      String[] setStr = fieldTemp[0][6].substring(0, fieldTemp[0][6].length() - 1).split("\\]\\.\\[");
      if (setStr.length > 2)
        jisuanSet = "<input type=\\'hidden\\' value=\\'" + setStr[2] + ":" + setStr[3] + "\\' name=\\'" + field + "_js\\' id=\\'" + field + "_js\\' />"; 
    } 
    String html = "";
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      noWriteField.indexOf(fieldTemp[0][5]) >= 0) {
      html = "<input style=width:" + (
        fieldTemp[0][3].equals("1") ? "92" : "100") + 
        "%;font-size:1em; type=text name=" + field + "_cmp id=" + 
        field + "_cmp class=flowInputRed readonly>";
    } else {
      html = "<input style=width:" + (
        fieldTemp[0][3].equals("1") ? "92" : "100") + 
        "%;font-size:1em; type=text name=" + field + "_cmp id=" + 
        field + "_cmp class=flowInputRed readonly>";
    } 
    html = String.valueOf(html) + "<input type=hidden name=" + field + " id=" + field + " >";
    if (fieldTemp[0][2].equals("1000000") || 
      fieldTemp[0][2].equals("1000001")) {
      type = "<input type=hidden name=" + field + "_type id=" + field + 
        "_type value=numbercompute>";
      isNum = true;
    } else {
      type = "<input type=hidden name=" + field + "_type id=" + field + 
        "_type value=varcharcompute>";
    } 
    html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + 
      field + "').innerHTML='" + html + type + jisuanSet + "';}";
    return html;
  }
  
  public String getEditHTML(String fieldValue, String hideField, String[][] fieldTemp, String fromDraft, String field, boolean isNum, int maxlen, String type, String computeFieldStr, HttpServletRequest request, String temp, String[] tempArr, String fieldId, String infoId, String pageId) {
    String html = "";
    String jisuanSet = "";
    if (fieldTemp[0][6].length() > 0) {
      String[] setStr = fieldTemp[0][6].substring(0, fieldTemp[0][6].length() - 1).split("\\]\\.\\[");
      if (setStr.length > 2) {
        if (setStr[3].equals("1") && !"".equals(setStr[2]) && !fieldValue.equals(""))
          if (fieldValue.indexOf(".") >= 0) {
            String endStr = fieldValue.substring(fieldValue.indexOf(".") + 1);
            for (int i = 0; endStr.length() < Integer.valueOf(setStr[2]).intValue() && i < Integer.valueOf(setStr[2]).intValue() - endStr.length(); i++)
              fieldValue = String.valueOf(fieldValue) + "0"; 
          } else {
            fieldValue = String.valueOf(fieldValue) + ".";
            for (int i = 0; i < Integer.valueOf(setStr[2]).intValue(); i++)
              fieldValue = String.valueOf(fieldValue) + "0"; 
          }  
        if (fieldValue.endsWith("."))
          fieldValue = fieldValue.substring(0, fieldValue.length() - 1); 
        jisuanSet = "<input type=\\'hidden\\' value=\\'" + setStr[2] + ":" + setStr[3] + "\\' name=\\'" + field + "_js\\' id=\\'" + field + "_js\\' />";
      } 
    } 
    if ((hideField != null && 
      hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0) || 
      hideField.indexOf(fieldTemp[0][5]) >= 0) {
      html = "<input style=width:" + (fieldTemp[0][3].equals("1") ? "92" : "100") + "%;font-size:1em; type=text name=" + 
        field + "_cmp id=" + 
        field + "_cmp class=flowInputRed value='" + fieldValue + "' readonly>";
      type = "";
    } else {
      html = "<input style=width:" + (fieldTemp[0][3].equals("1") ? "92" : "100") + "%;font-size:1em; type=text name=" + 
        field + "_cmp id=" + 
        field + "_cmp class=flowInputRed onmouseover=setStyle(this) onmouseout=setStyle(this) value='" + 
        fieldValue + 
        "' readonly>";
    } 
    html = String.valueOf(html) + "<input type=hidden name=" + field + " id=" + field + " value='" + fieldValue + "'>";
    if (fieldTemp[0][2].equals("1000000") || fieldTemp[0][2].equals("1000001")) {
      type = "<input type=hidden name=" + field + "_type id=" + field + "_type value=numbercompute>";
      isNum = true;
    } else {
      type = "<input type=hidden name=" + field + "_type id=" + field + "_type value=varcharcompute>";
    } 
    html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + field + "').innerHTML=\"" + html + type + jisuanSet + "\";}";
    return html;
  }
  
  public String getForeignEditHTMLForWeiXin(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr, boolean isHide, String type, boolean isNum, int maxlen, String temp, String[] tempArr) {
    String html = "";
    String styleValue = "";
    if (fieldValue == null || "".equals(fieldValue))
      fieldValue = "0"; 
    String jisuanSet = "";
    if (fieldTemp[index][6].length() > 0) {
      String[] setStr = fieldTemp[index][6].substring(0, fieldTemp[index][6].length() - 1).split("\\]\\.\\[");
      if (setStr.length > 2) {
        if (setStr[3].equals("1") && !"".equals(setStr[2]) && !fieldValue.equals(""))
          if (fieldValue.indexOf(".") >= 0) {
            String endStr = fieldValue.substring(fieldValue.indexOf(".") + 1);
            for (int i = 0; endStr.length() < Integer.valueOf(setStr[2]).intValue() && i < Integer.valueOf(setStr[2]).intValue() - endStr.length(); i++)
              fieldValue = String.valueOf(fieldValue) + "0"; 
          } else {
            fieldValue = String.valueOf(fieldValue) + ".";
            for (int i = 0; i < Integer.valueOf(setStr[2]).intValue(); i++)
              fieldValue = String.valueOf(fieldValue) + "0"; 
          }  
        if (fieldValue.endsWith("."))
          fieldValue = fieldValue.substring(0, fieldValue.length() - 1); 
        jisuanSet = "<input type=\\'hidden\\' value=\\'" + setStr[2] + ":" + setStr[3] + "\\' name=\\'" + field + "_js\\' id=\\'" + field + "_js\\' />";
      } 
    } 
    if (isHide) {
      html = fieldValue;
      html = String.valueOf(html) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
      html = String.valueOf(html) + "<input type=hidden name=" + field + "_cmp id=" + field + "_cmp value=" + fieldValue + ">";
      try {
        if (Double.valueOf(fieldValue).doubleValue() < 0.0D)
          styleValue = "document.getElementsByName('" + 
            fieldTemp[index][0] + "-" + field + "')[" + seq + "].style.color='red';"; 
      } catch (Exception err) {
        err.printStackTrace();
      } 
    } else {
      html = "<input style=width:" + (fieldTemp[index][3].equals("1") ? "92" : "100") + "%;font-size:1em; type=text name=" + 
        field + "_cmp id=" + 
        field + "_cmp class=flowInputRed onmouseover=setStyle(this) onmouseout=setStyle(this) value='" + 
        fieldValue + 
        "' readonly>";
      html = String.valueOf(html) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
    } 
    if (fieldTemp[index][2].equals("1000000") || fieldTemp[index][2].equals("1000001")) {
      type = "<input type=hidden name=" + field + "_type id=" + field + "_type value=numbercompute>";
      isNum = true;
    } else {
      type = "<input type=hidden name=" + field + "_type id=" + field + "_type value=varcharcompute>";
    } 
    html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{" + (
      "".equals(styleValue) ? "" : styleValue) + 
      "document.getElementsByName('" + 
      fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML=\"" + html + type + jisuanSet + "\";}";
    return html;
  }
}
