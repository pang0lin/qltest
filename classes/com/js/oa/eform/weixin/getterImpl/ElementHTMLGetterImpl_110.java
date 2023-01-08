package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import com.js.util.config.SystemCommon;
import javax.servlet.http.HttpServletRequest;

public class ElementHTMLGetterImpl_110 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      noWriteField.indexOf(fieldTemp[0][5]) >= 0) {
      html = "<textarea class=flowInput id=" + field + " name=" + field + 
        " style=\"width:" + (
        fieldTemp[0][3].equals("1") ? "92" : "100") + 
        "%;font-size:1em;\" rows=\"6\" readonly=\"readonly\">" + 
        fieldTemp[0][4] + "</textarea>";
    } else {
      html = "<textarea class=flowInput  onblur=checkSize(this); onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
        field + 
        " name=" + 
        field + 
        " style=\"width:" + (
        fieldTemp[0][3].equals("1") ? "92" : "100") + 
        "%;font-size:1em;\" rows=\"6\">" + 
        fieldTemp[0][4] + 
        "</textarea>";
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
      "').innerHTML+='" + html + "';}";
    return html;
  }
  
  public String getEditHTML(String fieldValue, String hideField, String[][] fieldTemp, String fromDraft, String field, boolean isNum, int maxlen, String type, String computeFieldStr, HttpServletRequest request, String temp, String[] tempArr, String fieldId, String infoId, String pageId) {
    String html;
    fieldValue = fieldValue.replaceAll("\\\\", "\\\\\\\\");
    if (hideField != null && (
      hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      hideField.indexOf(fieldTemp[0][5]) >= 0)) {
      if (SystemCommon.getSYWorkflowHR() == 1) {
        String hiddenHtml = "    <input type=hidden name=" + field + " id=" + field + " value='" + ((fieldValue == null) ? "&nbsp" : 
          fieldValue.replaceAll("\n", "<br>")
          .replaceAll("\r", "")) + "'/>";
        String hiddenvalue = (fieldValue == null) ? "&nbsp" : 
          fieldValue.replaceAll("\n", "<br>")
          .replaceAll("\r", "");
        html = "if(document.getElementById('" + 
          fieldTemp[0][0] + "-" + field + 
          "'))\n{document.getElementById('" + 
          fieldTemp[0][0] + "-" + field + 
          "').innerHTML=\"" + hiddenHtml + "\";}";
        html = String.valueOf(html) + "if(document.getElementById('" + 
          fieldTemp[0][0] + "-" + field + 
          "'))\n{document.getElementById('" + 
          fieldTemp[0][0] + "-" + field + 
          "').innerHTML+='" + (
          (fieldValue == null) ? "&nbsp" : 
          fieldValue.replaceAll("\n", "<br>")
          .replaceAll("\r", "")) + "';}";
      } else {
        html = "if(document.getElementById('" + 
          fieldTemp[0][0] + "-" + field + 
          "'))\n{document.getElementById('" + 
          fieldTemp[0][0] + "-" + field + 
          "').innerHTML='" + (
          (fieldValue == null) ? "&nbsp" : 
          fieldValue.replaceAll("\n", "<br>")
          .replaceAll("\r", "")) + "';}";
      } 
    } else {
      html = "<textarea class=flowInput  onblur=checkSize(this) onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + field + 
        " name=" + field + 
        " style=\"width:" + (fieldTemp[0][3].equals("1") ? "92" : "100") + "%;font-size:1em;\" rows=\"6\">" + (
        (fieldValue == null) ? "&nbsp" : 
        fieldValue.replaceAll("\n", "\\\\n'+'")
        .replaceAll("\r", "\\\\r'+'")) + 
        "</textarea>" + ((
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
        "').innerHTML+='" + html + "';}";
    } 
    return html;
  }
  
  public String getForeignEditHTMLForWeiXin(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr, boolean isHide, String type, boolean isNum, int maxlen, String temp, String[] tempArr) {
    String html = "";
    fieldValue = fieldValue.replaceAll("\\\\", "\\\\\\\\");
    if (isHide) {
      type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=\\'" + fieldValue.replaceAll("\n", "").replaceAll("\r", "") + "\\' >";
      html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
        fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + ((fieldValue == null) ? "&nbsp" : 
        fieldValue.replaceAll("\n", "<br>").replaceAll("\r", "")) + type + "';}";
    } else {
      html = "<textarea class=flowInput onmouseover=setStyle(this) onblur=checkSize(this) onmouseout=setStyle(this) id=" + field + 
        " name=" + field + " style=\"width:" + (fieldTemp[index][3].equals("1") ? "92" : "100") + "%;font-size:1em;\" rows=\"6\">" + (
        (fieldValue == null) ? "&nbsp" : fieldValue.replaceAll("\n", "\\\\n'+'")
        .replaceAll("\r", "\\\\r'+'")) + "</textarea>" + (
        fieldTemp[index][3].equals("1") ? ("<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + "><label class=mustFillcolor>*</label>") : "");
      html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + 
        "')[" + seq + "])\n{document.getElementsByName('" + fieldTemp[index][0] + "-" + field + 
        "')[" + seq + "].innerHTML='" + type + "';document.getElementsByName('" + 
        fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML+='" + html + "';}";
    } 
    return html;
  }
}
