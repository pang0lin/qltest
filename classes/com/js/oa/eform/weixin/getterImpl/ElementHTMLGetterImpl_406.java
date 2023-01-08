package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import javax.servlet.http.HttpServletRequest;

public class ElementHTMLGetterImpl_406 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    html = "<input type=text name=" + 
      field + 
      " id=" + 
      field + 
      " value=\"" + 
      fieldTemp[0][4] + 
      "\" " + 
      "readonly style=\"width:95%;border:0px solid white;font-size:1em;\">";
    html = String.valueOf(html) + "<input type=hidden name=wf_ua id=wf_ua value=" + field + ">";
    html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + 
      field + "').innerHTML='" + type + html + "';}";
    return html;
  }
  
  public String getEditHTML(String fieldValue, String hideField, String[][] fieldTemp, String fromDraft, String field, boolean isNum, int maxlen, String type, String computeFieldStr, HttpServletRequest request, String temp, String[] tempArr, String fieldId, String infoId, String pageId) {
    String html = "";
    if (hideField != null && (hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || hideField.indexOf(fieldTemp[0][5]) >= 0)) {
      html = (fieldValue == null) ? "&nbsp" : fieldValue.replaceAll("\n", "<br>").replaceAll("\r", "");
      type = "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
      html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + field + 
        "').innerHTML='" + type + "';document.getElementById('" + fieldTemp[0][0] + "-" + field + "').innerHTML+=\"" + html + "\";}";
    } else {
      html = "<input type=hidden id=" + field + " name=" + field + " value='" + fieldValue + "' " + ">";
      String temp3 = "var " + field + "_temp=document.createElement('SCRIPT');";
      temp3 = String.valueOf(temp3) + field + "_temp.text=\"document.all." + field + ".value=document.all.user_Account.value;\";";
      html = String.valueOf(temp3) + "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + 
        "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + field + 
        "').innerHTML=document.all.user_Account.value+'" + type + "';document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + "').innerHTML+=\"" + html + "\";document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + "').appendChild(" + field + "_temp);}";
    } 
    return html;
  }
  
  public String getForeignEditHTMLForWeiXin(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr, boolean isHide, String type, boolean isNum, int maxlen, String temp, String[] tempArr) {
    String html = "";
    return html;
  }
}
