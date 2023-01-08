package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import javax.servlet.http.HttpServletRequest;

public class ElementHTMLGetterImpl_200 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    html = "<input type=hidden name=" + field + "_type id=" + field + 
      "_type value=varchar>" + "<input type=hidden name=" + field + 
      "_size id=" + field + "_size value=255>";
    html = String.valueOf(html) + "<input type=text id=" + 
      field + 
      " name=" + 
      field + 
      " style=width:" + (
      fieldTemp[0][3].equals("1") ? "92" : "100") + 
      "%" + 
      " class=flowInput onblur=isPhone(this); maxlength=\"" + 
      maxlen + 
      "\"" + 
      " value=\"" + 
      fieldTemp[0][4] + 
      "\">" + ((
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
      String phoneNum = (fieldValue == null) ? "" : fieldValue;
      String telAndSms = "";
      if (phoneNum.startsWith("86")) {
        telAndSms = phoneNum.substring(2);
      } else if (phoneNum.startsWith("+86")) {
        telAndSms = phoneNum.substring(3);
      } else {
        telAndSms = phoneNum;
      } 
      telAndSms = telAndSms.replaceAll("-", "");
      String phone = "<a href=\"tel:" + telAndSms + "\"><img src=\"/jsoa/jsflow/images/dianhua.png\" " + 
        "style=\"cursor:pointer;width:20px;height:20px;border=0;\" title=\"拨打电话\" /></a>&nbsp;&nbsp;" + 
        "<a href=\"sms:" + telAndSms + "\"><img src=\"/jsoa/jsflow/images/duanxin.png\" " + 
        "style=\"cursor:pointer;width:20px;height:20px;border=0;\" title=\"发短信\" /></a>";
      String show = "if(navigator.userAgent.indexOf(\"Android\")>0||navigator.userAgent.indexOf(\"iPad\")>0||navigator.userAgent.indexOf(\"iPhone\")>0) ";
      html = "\n\nif(document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "'))\n{\ndocument.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "').innerHTML='" + phoneNum + "&nbsp;&nbsp;';\n" + show + 
        "{\ndocument.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "').innerHTML+='&nbsp;" + phone + 
        "';\n}\n}\n";
    } else {
      String phoneNum = (fieldValue == null) ? "" : fieldValue;
      html = "<input type=hidden name=" + field + "_type id=" + field + "_type value=varchar>" + 
        "<input type=hidden name=" + field + "_size id=" + field + "_size value=255>";
      html = String.valueOf(html) + "<input type=text id=" + field + " name=" + field + " style=width:" + (fieldTemp[0][3].equals("1") ? "92" : "100") + "%;font-size:1em;" + 
        " class=flowInput onblur=isPhone(this); maxlength=\"" + maxlen + "\"" + " value=\"" + phoneNum + "\">" + ((
        fieldTemp[0][3].equals("1") && hideField.indexOf("," + fieldTemp[0][0] + ",") < 0 && hideField.indexOf(fieldTemp[0][5]) < 0) ? (
        "<input type=hidden name=mustWrite id=mustWrite value=" + field + 
        "><label class=mustFillcolor>*</label>") : "");
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
