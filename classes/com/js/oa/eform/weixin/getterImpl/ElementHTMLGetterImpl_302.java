package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import javax.servlet.http.HttpServletRequest;

public class ElementHTMLGetterImpl_302 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    html = "<label  class=xuhao>1</label>";
    String temp_302 = " <input type=hidden  name=" + field + " id=" + field + 
      " value=\"0\" > ";
    html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + 
      field + "').innerHTML='" + type + temp_302 + 
      "';document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "').innerHTML+='" + html + "';}";
    return html;
  }
  
  public String getEditHTML(String fieldValue, String hideField, String[][] fieldTemp, String fromDraft, String field, boolean isNum, int maxlen, String type, String computeFieldStr, HttpServletRequest request, String temp, String[] tempArr, String fieldId, String infoId, String pageId) {
    String html = "";
    html = "<label class=xuhao>1</label>";
    String temp_302 = " <input type=hidden style=width:100%  name=" + field + " id=" + field + " value=\"0\" > ";
    html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "').innerHTML='" + type + temp_302 + "';document.getElementById('" + fieldTemp[0][0] + "-" + field + "').innerHTML+='" + html + "';}";
    return html;
  }
  
  public String getForeignEditHTMLForWeiXin(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr, boolean isHide, String type, boolean isNum, int maxlen, String temp, String[] tempArr) {
    String html = "";
    html = "<label class=xuhao>" + (Integer.parseInt(seq) + 1) + "</label>";
    String temp_302 = " <input type=hidden style=width:100%  name=" + field + " id=" + field + " value=\"" + (Integer.parseInt(seq) + 1) + "\" > ";
    html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + fieldTemp[index][0] + "-" + field + 
      "')[" + seq + "].innerHTML='" + type + temp_302 + "';document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML+='" + html + "';}";
    return html;
  }
}
