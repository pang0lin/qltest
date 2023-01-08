package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import javax.servlet.http.HttpServletRequest;

public class ElementHTMLGetterImpl_199 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    html = "<input type=hidden name=" + field + "_type id=" + field + 
      "_type value=varchar>" + "<input type=hidden name=" + field + 
      "_size id=" + field + "_size value=255>";
    html = String.valueOf(html) + "<input type=hidden id=" + 
      field + 
      " name=" + 
      field + 
      " value=\"\" />" + 
      "<div class=ui-body-c>标题：</div><input type=text id=" + 
      field + 
      "_title name=" + 
      field + 
      "_title value=\"\" onblur=comURL(\"" + 
      field + 
      "\"); style=\"width:20%;font-size:1em;\" />" + 
      "<div class=ui-body-c>&nbsp;链接：</div><input type=text id=" + 
      field + 
      "_url name=" + 
      field + 
      "_url  value=\"" + 
      fieldTemp[0][4] + 
      "\" onblur=comURL(\"" + 
      field + 
      "\"); " + 
      "style=font-size:1em;width:" + (
      fieldTemp[0][3].equals("1") ? "20" : "30") + 
      "%" + 
      " class=flowInput  maxlength=\"" + 
      maxlen + 
      "\" >" + ((
      fieldTemp[0][3].equals("1") && 
      noWriteField.indexOf("," + fieldId + ",") < 0 && noWriteField
      .indexOf(field) < 0) ? ("<input type=hidden name=mustWrite id=mustWrite value=" + 
      field + "><label class=mustFillcolor>*</label>") : 
      "");
    html = "\n\nif(document.getElementById('" + fieldTemp[0][0] + "-" + 
      field + "'))\n{\ndocument.getElementById('" + fieldTemp[0][0] + 
      "-" + field + "').innerHTML='" + html + "';\n}\n\n";
    return html;
  }
  
  public String getEditHTML(String fieldValue, String hideField, String[][] fieldTemp, String fromDraft, String field, boolean isNum, int maxlen, String type, String computeFieldStr, HttpServletRequest request, String temp, String[] tempArr, String fieldId, String infoId, String pageId) {
    String html = "";
    if (hideField != null && (
      hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      hideField.indexOf(fieldTemp[0][5]) >= 0)) {
      String value = (fieldValue == null) ? "" : fieldValue;
      String title = "";
      String URL = "#";
      if (!"".equals(value) && 
        value.indexOf("`~`~`") >= 0) {
        String[] strings = value.split("`~`~`");
        title = strings[0];
        URL = strings[1];
      } 
      URL = "<a href=\"" + URL + "\" style=\"cursor:pointer;\" >" + title + "</a>";
      html = "\n\nif(document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "'))\n{\ndocument.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "').innerHTML='" + URL + "';\n}\n";
    } else {
      String value = (fieldValue == null) ? "" : fieldValue;
      String title = "";
      String URL = "";
      if (!"".equals(value)) {
        if (value.indexOf("`~`~`") >= 0) {
          String[] strings = value.split("`~`~`");
          title = strings[0];
          URL = strings[1];
        } 
        if ("#".equals(URL))
          URL = ""; 
      } 
      html = "<input type=hidden name=" + field + "_type id=" + field + "_type value=varchar>" + 
        "<input type=hidden name=" + field + "_size id=" + field + "_size value=255>";
      html = String.valueOf(html) + "<input type=hidden id=" + field + " name=" + field + " value=\"" + value + "\" />" + 
        "<div class=ui-body-c>标题：</div><input type=text id=" + field + "_title name=" + field + "_title value=\"" + title + "\" onblur=comURL(\"" + field + "\"); style=\"width:20%;font-size:1em;\" />" + 
        "<div class=ui-body-c>&nbsp;链接：</div><input type=text id=" + field + "_url name=" + field + "_url value=\"" + URL + "\" onblur=comURL(\"" + field + "\"); " + 
        "style=width:" + (fieldTemp[0][3].equals("1") ? "20" : "30") + "%;font-size:1em;" + 
        " class=flowInput  maxlength=\"" + maxlen + "\" >" + (fieldTemp[0][3].equals("1") ? (
        "<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + 
        "><label class=mustFillcolor>*</label>") : 
        "");
      html = "\n\nif(document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "'))\n{\ndocument.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "').innerHTML='" + html + "';\n}\n\n";
    } 
    return html;
  }
  
  public String getForeignEditHTMLForWeiXin(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr, boolean isHide, String type, boolean isNum, int maxlen, String temp, String[] tempArr) {
    return null;
  }
}
