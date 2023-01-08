package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import javax.servlet.http.HttpServletRequest;

public class ElementHTMLGetterImpl_209 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      noWriteField.indexOf(fieldTemp[0][5]) >= 0) {
      html = "<input style=width:" + (
        fieldTemp[0][3].equals("1") ? "92" : "100") + 
        "%;font-size:1em; type=text class=flowInput id=" + field + 
        " name=" + field + " disabled>";
    } else {
      html = "<input style=width:" + (
        fieldTemp[0][3].equals("1") ? "92" : "100") + 
        "%;font-size:1em; type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
        field + 
        " name=" + 
        field + 
        " " + (
        isNum ? "  onblur=checkNum(this);checkSize(this);" : 
        " onblur=checkSize(this);") + ">";
    } 
    String temp7 = "var " + field + 
      "_temp=document.createElement('SCRIPT');";
    temp7 = String.valueOf(temp7) + field + "_temp.text=\"document.all." + field + 
      ".value=document.all.group_ID.value;\";";
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") < 0 && 
      noWriteField.indexOf(fieldTemp[0][5]) < 0 && 
      fieldTemp[0][3].equals("1"))
      html = String.valueOf(html) + "<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + "><label class=mustFillcolor>*</label>"; 
    html = String.valueOf(temp7) + "if(document.getElementById('" + fieldTemp[0][0] + "-" + 
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
    return html;
  }
  
  public String getForeignEditHTMLForWeiXin(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr, boolean isHide, String type, boolean isNum, int maxlen, String temp, String[] tempArr) {
    return null;
  }
}
