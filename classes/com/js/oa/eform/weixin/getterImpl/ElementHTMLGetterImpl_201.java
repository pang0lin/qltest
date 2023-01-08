package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import javax.servlet.http.HttpServletRequest;

public class ElementHTMLGetterImpl_201 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    html = "<input style=width:" + (
      fieldTemp[0][3].equals("1") ? "92" : "100") + 
      "%;font-size:1em; type=text id=" + field + " name=" + field + 
      " readonly style=border:0px solid white;>";
    String temp3 = "var " + field + 
      "_temp=document.createElement('SCRIPT');";
    temp3 = String.valueOf(temp3) + field + "_temp.text=\"document.all." + field + 
      ".value=document.all.user_Id.value;\";";
    html = String.valueOf(temp3) + "if(document.getElementById('" + fieldTemp[0][0] + "-" + 
      field + "'))\n{document.getElementById('" + fieldTemp[0][0] + 
      "-" + field + "').innerHTML='" + type + 
      "';document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "').innerHTML+=\"" + html + "\";document.getElementById('" + 
      fieldTemp[0][0] + "-" + field + "').appendChild(" + field + 
      "_temp);}";
    return html;
  }
  
  public String getEditHTML(String fieldValue, String hideField, String[][] fieldTemp, String fromDraft, String field, boolean isNum, int maxlen, String type, String computeFieldStr, HttpServletRequest request, String temp, String[] tempArr, String fieldId, String infoId, String pageId) {
    String html = "";
    if (hideField != null && (
      hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      hideField.indexOf(fieldTemp[0][5]) >= 0)) {
      html = "if(document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "'))\n{document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "').innerHTML='" + (
        (fieldValue == null) ? "&nbsp" : 
        fieldValue.replaceAll("\n", "<br>")
        .replaceAll("\r", "")) + "';}";
    } else {
      html = "<input style=width:" + (fieldTemp[0][3].equals("1") ? "92" : "100") + "%;font-size:1em; type=text id=" + 
        field + " name=" + 
        field + " value='" + fieldValue + "' " + " readonly style=border:0px solid white;>";
      String temp3 = "var " + field + 
        "_temp=document.createElement('SCRIPT');";
      temp3 = String.valueOf(temp3) + field + "_temp.text=\"document.all." + field + 
        ".value=document.all.user_Id.value;\";";
      html = String.valueOf(temp3) + "if(document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "'))\n{document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "').innerHTML='" + type + 
        "';document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "').innerHTML+=\"" + html + "\";document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "').appendChild(" + field + "_temp);}";
    } 
    return html;
  }
  
  public String getForeignEditHTMLForWeiXin(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr, boolean isHide, String type, boolean isNum, int maxlen, String temp, String[] tempArr) {
    String html = "";
    if (isHide) {
      type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
      html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
        fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + (
        (fieldValue == null) ? "&nbsp" : fieldValue.replaceAll("\n", "<br>").replaceAll("\r", "")) + type + "';}";
    } else {
      html = 
        "<input style=width:" + (fieldTemp[index][3].equals("1") ? "92" : "100") + "%;font-size:1em; type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
        field + " name=" + 
        field + " value='" + fieldValue + "' " + (
        isNum ? "  onblur=checkNum(this);checkSize(this);" : " onblur=checkSize(this);") + 
        ">" + (
        fieldTemp[index][3].equals("1") ? (
        "<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + 
        "><label class=mustFillcolor>*</label>") : 
        "");
      html = "if(document.getElementsByName('" + 
        fieldTemp[index][0] + "-" + field + 
        "')[" + seq + "])\n{document.getElementsByName('" + 
        fieldTemp[index][0] + "-" + field + 
        "')[" + seq + "].innerHTML='" + type + 
        "';document.getElementsByName('" + 
        fieldTemp[index][0] + "-" + field + 
        "')[" + seq + "].innerHTML+=\"" + html + "\";}";
    } 
    return html;
  }
}
