package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

public class ElementHTMLGetterImpl_107 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    Date now = new Date();
    String temp = fieldTemp[0][6];
    type = "<input type=hidden name=" + field + "_type id=" + field + "_type value=jsdate>";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String valueTemp = "";
    valueTemp = sdf.format(new Date());
    boolean showDefaultTime = false;
    if (fieldTemp[0][14] == null) {
      valueTemp = "";
    } else if ("".equals(fieldTemp[0][14])) {
      showDefaultTime = true;
      SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
      valueTemp = ymd.format(now);
    } else if (fieldTemp[0][14].toString().indexOf("#") > 0) {
      if ("1".equals(fieldTemp[0][14].toString().split("#")[0])) {
        showDefaultTime = true;
        if ((fieldTemp[0][14].toString().split("#")).length > 1 && !"".equals(fieldTemp[0][14].toString().split("#")[1]))
          valueTemp = fieldTemp[0][14].toString().split("#")[1]; 
      } else {
        valueTemp = "";
      } 
    } 
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || noWriteField.indexOf(fieldTemp[0][5]) >= 0) {
      type = String.valueOf(type) + "<span>" + valueTemp + "</span>";
      type = String.valueOf(type) + "<input type=\\\"hidden\\\" name=\\\"" + field + "\\\" id=\\\"" + field + "\\\" value=\\\"" + valueTemp + "\\\" />";
    } else {
      type = String.valueOf(type) + "<input type=\\\"text\\\" data-role=\\\"datebox\\\" readonly=\\\"readonly\\\" style=\\\"width:75px;font-size:1em;background:url('/jsoa/eform/images/down_arrow.gif');\\\" onmouseover=\\\"setStyle(this)\\\" onmouseout=\\\"setStyle(this)\\\" name=\\\"" + 
        
        field + "\\\" " + 
        "id=\\\"" + field + "\\\" " + "value=\\\"" + valueTemp + "\\\">";
    } 
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") < 0 && 
      noWriteField.indexOf(fieldTemp[0][5]) < 0 && fieldTemp[0][3].equals("1"))
      type = String.valueOf(type) + "<input type=\\\"hidden\\\" name=\\\"mustWrite\\\" id=\\\"mustWrite\\\" value=\\\"" + field + "\\\">" + 
        "<label class=\\\"mustFillcolor\\\">*</label>"; 
    html = String.valueOf(html) + "if(document.getElementById(\"" + fieldTemp[0][0] + "-" + field + "\")){\n" + 
      "document.getElementById(\"" + fieldTemp[0][0] + "-" + field + "\").innerHTML=\"" + type + "\";\n}";
    if (!"".equals(fieldTemp[0][4]) && !"null".equalsIgnoreCase((new StringBuilder(String.valueOf(fieldTemp[0][4]))).toString()))
      html = String.valueOf(html) + "\ndocument.getElementById(\"dateCompare\").value=document.getElementById(\"dateCompare\").value+\"{" + fieldTemp[0][4] + "}\";\n"; 
    return html;
  }
  
  public String getEditHTML(String fieldValue, String hideField, String[][] fieldTemp, String fromDraft, String field, boolean isNum, int maxlen, String type, String computeFieldStr, HttpServletRequest request, String temp, String[] tempArr, String fieldId, String infoId, String pageId) {
    String html = "";
    type = "<input type=hidden name=" + field + "_type id=" + field + "_type value=jsdate>";
    if (hideField != null && (
      hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      hideField.indexOf(fieldTemp[0][5]) >= 0)) {
      type = String.valueOf(type) + "<span>" + fieldValue + "</span>";
      type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value='" + fieldValue + "' />";
      html = "if(document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "'))\n{document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "').innerHTML=\"" + type + html + "\";}";
    } else {
      type = String.valueOf(type) + 
        "<input  type=text readonly=readonly  data-role=datebox  style=\\\"width:75px;font-size:1em;\\\"   onmouseover=setStyle(this) onmouseout=setStyle(this) name=" + 
        field + " id=" + field + 
        "  value='" + 
        fieldValue + "' >" + ((
        fieldTemp[0][3].equals("1") && hideField.indexOf("," + fieldTemp[0][0] + ",") < 0 && hideField.indexOf(fieldTemp[0][5]) < 0) ? (
        "<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + 
        "><label class=mustFillcolor>*</label>") : 
        "");
      html = String.valueOf(html) + "if(document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "'))\n{document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "').innerHTML=\"" + type + "\";}";
    } 
    return html;
  }
  
  public String getForeignEditHTMLForWeiXin(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr, boolean isHide, String type, boolean isNum, int maxlen, String temp, String[] tempArr) {
    String html = "";
    if (isHide) {
      type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
      html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
        fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + fieldValue + type + "';}";
    } else {
      type = String.valueOf(type) + "<input   type=text readonly=readonly  data-role=datebox onmouseover=setStyle(this) onmouseout=setStyle(this) name=" + 
        field + " id=" + field + "  value='" + 
        fieldValue + "' style=\\\"background:url('/jsoa/eform/images/down_arrow.gif');font-size:1em;\\\">" + (
        fieldTemp[index][3].equals("1") ? ("<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + "><label class=mustFillcolor>*</label>") : "");
      html = String.valueOf(html) + "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + 
        "')[" + seq + "])\n{document.getElementsByName('" + fieldTemp[index][0] + "-" + field + 
        "')[" + seq + "].innerHTML=\"" + type + "\";}";
    } 
    return html;
  }
}
