package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import javax.servlet.http.HttpServletRequest;

public class ElementHTMLGetterImpl_212 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    type = "<input type=hidden name=" + field + "_type id=" + field + 
      "_type value=personorg>";
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      noWriteField.indexOf(fieldTemp[0][5]) >= 0) {
      html = "<input style=width:80%;font-size:1em; type=text class=flowInput id=" + 
        field + "_Name name=" + field + "_Name readonly>";
      html = String.valueOf(html) + "<input type=hidden id=" + field + "_Id name=" + field + 
        "_Id>";
    } else {
      html = "<input style=width:80%;font-size:1em; type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
        field + 
        "_Name name=" + 
        field + 
        "_Name title=\\\"请点击选择\\\" readonly onClick=\\\"showSelectUsers('" + 
        field + "_Id', '" + field + "_Name', '', 0, 'organization', '', '-100', 'formSingleOrgCallback', 'selList',this)\\\" readonly=readonly />";
      html = String.valueOf(html) + "<input type=hidden id=" + field + "_Id name=" + field + 
        "_Id>";
    } 
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
    type = "<input type=hidden name=" + field + 
      "_type id=" + field + 
      "_type value=personorg>";
    if (hideField != null && (
      hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      hideField.indexOf(fieldTemp[0][5]) >= 0)) {
      type = "";
      html = 
        "<input type=hidden id=" + 
        field + "_Name name=" + 
        field + "_Name value=\\\"" + ((
        fieldValue != null && (
        fieldValue.split(";")).length > 1) ? 
        fieldValue.split(";")[0] : "") + "\\\" >";
      html = String.valueOf(html) + "<input type=hidden id=" + field + 
        "_Id name=" + 
        field + "_Id value=\\\"" + ((
        fieldValue != null && (
        fieldValue.split(";")).length > 1) ? 
        fieldValue.split(";")[1] : "") + 
        "\\\">";
      html = String.valueOf(html) + (
        (fieldValue != null && (
        fieldValue.split(";")).length > 1) ? 
        fieldValue.split(";")[0] : "");
    } else {
      html = 
        "<input style=width:80%;font-size:1em; type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
        field + "_Name name=" + 
        field + "_Name readonly title\\\"请点击选择\\\" value=\\\"" + ((
        fieldValue != null && (
        fieldValue.split(";")).length > 1) ? 
        fieldValue.split(";")[0] : "") + "\\\" onClick=\\\"showSelectUsers('" + 
        field + "_Id', '" + field + "_Name', '', 0, 'organization', '', '-100', 'formSingleOrgCallback', 'selList',this)\\\" readonly=readonly />";
      html = String.valueOf(html) + "<input type=hidden id=" + field + 
        "_Id name=" + 
        field + "_Id value=\\\"" + ((
        fieldValue != null && (
        fieldValue.split(";")).length > 1) ? 
        fieldValue.split(";")[1] : "") + 
        "\\\">" + ((




        
        fieldTemp[0][3].equals("1") && hideField.indexOf("," + fieldTemp[0][0] + ",") < 0 && hideField.indexOf(fieldTemp[0][5]) < 0) ? (
        "<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + 
        "><label class=mustFillcolor>*</label>") : 
        "");
    } 
    html = "if(document.getElementById('" + fieldTemp[0][0] + 
      "-" + field + "'))\n{document.getElementById('" + 
      fieldTemp[0][0] + "-" + field + 
      "').innerHTML='" + type + 
      "';document.getElementById('" + 
      fieldTemp[0][0] + "-" + field + 
      "').innerHTML+=\"" + html + "\";}";
    return html;
  }
  
  public String getForeignEditHTMLForWeiXin(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr, boolean isHide, String type, boolean isNum, int maxlen, String temp, String[] tempArr) {
    String html = "";
    if (isHide) {
      type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
      html = String.valueOf(html) + ((fieldValue != null && (fieldValue.split(";")).length > 1) ? fieldValue.split(";")[0] : "");
    } else {
      type = "<input type=hidden name=" + field + "_type id=" + field + "_type value=personorg>";
      html = 
        "<input style=width:80%;font-size:1em; type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
        field + "_Name name=" + 
        field + "_Name readonly title=\\\"请点击选择\\\" value=\\\"" + ((
        fieldValue != null && (
        fieldValue.split(";")).length > 1) ? 
        fieldValue.split(";")[0] : "") + "\\\" onClick=\\\"showSelectUsers('" + 
        field + "_Id', '" + field + "_Name', '', 0, 'organization', '', '-100', 'formSingleOrgCallback', 'selList',this)\\\">";
      html = String.valueOf(html) + "<input type=hidden id=" + field + 
        "_Id name=" + 
        field + "_Id value=\\\"" + ((
        fieldValue != null && (
        fieldValue.split(";")).length > 1) ? 
        fieldValue.split(";")[0] : "") + 
        "\\\">" + (




        
        fieldTemp[index][3].equals("1") ? (
        "<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + 
        "><label class=mustFillcolor>*</label>") : 
        "");
    } 
    html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
      fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + type + "';document.getElementsByName('" + 
      fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML+=\"" + html + "\";}";
    return html;
  }
}
