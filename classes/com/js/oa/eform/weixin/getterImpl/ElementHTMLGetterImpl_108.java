package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import com.js.util.config.SystemCommon;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

public class ElementHTMLGetterImpl_108 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    Date now = new Date();
    type = "<input type=hidden name=" + field + "_type id=" + field + "_type value=jstime>";
    String timeStr = (new SimpleDateFormat("HH:mm:ss")).format(now);
    boolean showDefaultTime = false;
    if (fieldTemp[0][14] != null) {
      if (fieldTemp[0][14].toString().indexOf("#") > 0)
        if ("1".equals(fieldTemp[0][14].toString().split("#")[0])) {
          showDefaultTime = true;
          if ((fieldTemp[0][14].toString().split("#")).length > 1 && !"".equals(fieldTemp[0][14].toString().split("#")[1]))
            timeStr = fieldTemp[0][14].toString().split("#")[1]; 
        } else {
          timeStr = "";
        }  
    } else {
      timeStr = "";
    } 
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      noWriteField.indexOf(fieldTemp[0][5]) >= 0) {
      html = "<span>" + timeStr + "</span><input type=hidden id=" + field + " name=" + field + " value=\\\"" + timeStr + "\\\" />";
    } else if (SystemCommon.getSYWorkflowHR() == 1) {
      html = "<INPUT type=text readonly=readonly class=flowInput onmouseover=setStyle(this) readonly=true onmouseout=setStyle(this) id=" + 
        field + 
        " name=" + field + 
        " onclick='showDateTimeBar(this, \\\"h:n:s\\\", \\\"" + 
        String.valueOf(now.getMonth() + 1) + "/" + 
        String.valueOf(now.getDate() + 1) + "/" + 
        String.valueOf(now.getYear() + 1900) + " " + 
        String.valueOf(now.getHours()) + ":" + 
        String.valueOf(now.getMinutes()) + ":" + 
        String.valueOf(now.getSeconds()) + "\\\", " + 
        "test, \\\"hello world\\\")' name=" + 
        field + " id=" + 
        field + " value=\\\"00:00:00 \\\" style=\\\"background:url('/jsoa/eform/images/down_arrow.gif');font-size:1em;\\\">";
    } else {
      html = "<INPUT type=text readonly=readonly class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
        field + 
        " name=" + field + 
        " onclick='showDateTimeBar(this, \\\"h:n:s\\\", \\\"" + 
        String.valueOf(now.getMonth() + 1) + "/" + 
        String.valueOf(now.getDate() + 1) + "/" + 
        String.valueOf(now.getYear() + 1900) + " " + 
        String.valueOf(now.getHours()) + ":" + 
        String.valueOf(now.getMinutes()) + ":" + 
        String.valueOf(now.getSeconds()) + "\\\", " + 
        "test, \\\"hello world\\\")' name=" + 
        field + " id=" + 
        field + " value=\\\"" + 


        
        timeStr + "\\\" style=\\\"background:url('/jsoa/eform/images/down_arrow.gif');font-size:1em;\\\">";
    } 
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") < 0 && 
      noWriteField.indexOf(fieldTemp[0][5]) < 0 && 
      fieldTemp[0][3].equals("1"))
      html = String.valueOf(html) + "<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + 
        "><label class=mustFillcolor>*</label>"; 
    html = "if(document.getElementById('" + fieldTemp[0][0] + 
      "-" + field + "'))\n{document.getElementById('" + 
      fieldTemp[0][0] + "-" + field + 
      "').innerHTML='" + type + 
      "';document.getElementById('" + 
      fieldTemp[0][0] + "-" + field + 
      "').innerHTML+=\"" + html + "\";}";
    return html;
  }
  
  public String getEditHTML(String fieldValue, String hideField, String[][] fieldTemp, String fromDraft, String field, boolean isNum, int maxlen, String type, String computeFieldStr, HttpServletRequest request, String temp, String[] tempArr, String fieldId, String infoId, String pageId) {
    String html;
    Date now = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    String timeStr = sdf.format(now);
    type = "<input type=hidden name=" + field + "_type id=" + field + "_type value=jstime>";
    if (fieldValue == null || "".equals(fieldValue))
      fieldValue = ""; 
    if (hideField != null && (
      hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      hideField.indexOf(fieldTemp[0][5]) >= 0)) {
      html = "<span>" + fieldValue + "</span><input type=hidden id=" + field + " name=" + field + " value=\\\"" + fieldValue + "\\\" />";
      html = "if(document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "'))\n{document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "').innerHTML='" + type + html + "';}";
    } else {
      html = "<INPUT  type=text readonly=readonly class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
        field + " name=" + field + 
        " onclick='showDateTimeBar(this, \\\"h:n:s\\\", \\\"" + 
        String.valueOf(now.getMonth() + 1) + "/" + 
        String.valueOf(now.getDate() + 1) + "/" + 
        String.valueOf(now.getYear() + 1900) + " " + 
        fieldValue + "\\\", " + 
        "test, \\\"hello world\\\")' name=" + 
        field + " id=" + 
        field + " value=\\\"" + 
        fieldValue + "\\\" style=\\\"background:url('/jsoa/eform/images/down_arrow.gif');font-size:1em;\\\">" + ((
        fieldTemp[0][3].equals("1") && hideField.indexOf("," + fieldTemp[0][0] + ",") < 0 && hideField.indexOf(fieldTemp[0][5]) < 0) ? (
        "<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + 
        "><label class=mustFillcolor>*</label>") : 
        "");
      html = "if(document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "'))\n{document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "').innerHTML='" + type + 
        "';document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "').innerHTML+=\"" + html + "\";}";
    } 
    return html;
  }
  
  public String getForeignEditHTMLForWeiXin(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr, boolean isHide, String type, boolean isNum, int maxlen, String temp, String[] tempArr) {
    String html = "";
    Date now = new Date();
    if (isHide) {
      type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
      html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
        fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + fieldValue + type + "';}";
    } else {
      html = "<INPUT  type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
        field + " name=" + field + " onclick='showDateTimeBar(this, \\\"h:n:s\\\", \\\"" + 
        String.valueOf(now.getMonth() + 1) + "/" + String.valueOf(now.getDate() + 1) + "/" + 
        String.valueOf(now.getYear() + 1900) + " " + fieldValue + "\\\", " + 
        "test, \\\"hello world\\\")' name=" + field + " value=\\\"" + 
        fieldValue + "\\\" style=\\\"background:url('/jsoa/eform/images/down_arrow.gif');font-size:1em;\\\">" + (
        fieldTemp[index][3].equals("1") ? ("<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + "><label class=mustFillcolor>*</label>") : "");
      html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + 
        "')[" + seq + "])\n{document.getElementsByName('" + fieldTemp[index][0] + "-" + field + 
        "')[" + seq + "].innerHTML='" + type + "';document.getElementsByName('" + 
        fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML+=\"" + html + "\";}";
    } 
    return html;
  }
}
