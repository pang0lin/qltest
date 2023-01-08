package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

public class ElementHTMLGetterImpl_123 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    String datenew = "";
    if (noWriteField.indexOf("," + fieldId + ",") < 0 && 
      noWriteField.indexOf(field) < 0) {
      String temp = fieldTemp[0][6];
      if (temp.equals(""))
        temp = "yyyy-MM-dd HH:mm"; 
      SimpleDateFormat format = new SimpleDateFormat(temp);
      datenew = format.format(new Date());
    } 
    html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + 
      field + "').innerHTML='<input type=hidden name=" + field + 
      "_type id=" + field + "_type value=varchar>" + 
      "<input type=hidden name=" + field + "_size id=" + field + 
      "_size value=255>" + "<input type=\"hidden\" name=\"" + field + 
      "\" id=\"" + field + "\" value=\"" + datenew + "\" />" + 
      datenew + "';}";
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
      temp = fieldTemp[0][6];
      if (temp.equals(""))
        temp = "yyyy-MM-dd HH:mm"; 
      SimpleDateFormat format = new SimpleDateFormat(temp);
      String datenew = format.format(new Date());
      html = "if(document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "'))\n{document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "').innerHTML='<input type=hidden name=" + field + "_type id=" + field + "_type value=varchar>" + 
        "<input type=hidden name=" + field + "_size id=" + field + "_size value=255>" + 
        "<input type=\"hidden\" name=\"" + field + "\" id=\"" + 
        field + "\" value=\"" + datenew + "\" />" + datenew + "';}";
    } 
    return html;
  }
  
  public String getForeignEditHTMLForWeiXin(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr, boolean isHide, String type, boolean isNum, int maxlen, String temp, String[] tempArr) {
    return null;
  }
}
