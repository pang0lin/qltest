package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import com.js.util.config.SystemCommon;
import javax.servlet.http.HttpServletRequest;

public class ElementHTMLGetterImpl_117 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    String getDocModel = "";
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      noWriteField.indexOf(fieldTemp[0][5]) >= 0) {
      html = "<input type=text class=flowInput style=font-size:1em; id=" + 
        field + " name=" + field + " readonly>";
    } else {
      html = "<input type=hidden id=" + field + " name=" + field + ">";
      html = String.valueOf(html) + "<input type=\\\"button\\\" id=_weboffice_view name=_weboffice_view class=btnButton4font onclick=\\\"selectFile('', '" + 
        field + "', '" + field + "_fileList', '1', '.xls,');\\\" value=\\\"上传正文\\\" />";
    } 
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") < 0 && 
      noWriteField.indexOf(fieldTemp[0][5]) < 0 && 
      fieldTemp[0][3].equals("1")) {
      html = String.valueOf(html) + "<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + "><label class=mustFillcolor>*</label>";
      getDocModel = "getDocModel('" + field + "', '.xls');";
    } 
    html = String.valueOf(html) + "<div id=\\\"" + field + "_fileList\\\" class=\\\"fileList\\\"></div>";
    html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + 
      field + "').innerHTML='" + type + 
      "';document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "').innerHTML+=\"" + html + "\";" + getDocModel + "}";
    return html;
  }
  
  public String getEditHTML(String fieldValue, String hideField, String[][] fieldTemp, String fromDraft, String field, boolean isNum, int maxlen, String type, String computeFieldStr, HttpServletRequest request, String temp, String[] tempArr, String fieldId, String infoId, String pageId) {
    String html = "";
    if (hideField != null && (
      hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      hideField.indexOf(fieldTemp[0][5]) >= 0)) {
      html = "<input type=hidden id=" + field + " name=" + 
        field + " value=\\\"" + fieldValue + "\\\">";
      type = "";
    } else {
      html = "<input type=hidden id=" + field + " name=" + 
        field + " value=\\\"" + fieldValue + "\\\">";
      html = String.valueOf(html) + "<input type=\\\"button\\\" id=_weboffice_view name=_weboffice_view class=btnButton4font onclick=\\\"selectFile('', '" + 
        field + "', '" + field + "_fileList', '1', '.xls,');\\\" value=\\\"上传正文\\\" />";
    } 
    if (fieldValue != null && !"".equals(fieldValue)) {
      html = String.valueOf(html) + "<div id=\\\"" + field + "_fileList\\\" class=\\\"fileList\\\">";
      html = String.valueOf(html) + "<a href=\\\"javascript:showHtmlObject('" + fieldValue + ".xls','0','information')\\\">查看正文</a>";
      html = String.valueOf(html) + "</div>";
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
      html = "<input type=hidden id=" + field + " name=" + field + " value=\\\"" + fieldValue + "\\\">";
      html = String.valueOf(html) + "<input type='button' class=btnButton4font onclick=\\\"window.open('/jsoa/iWebOfficeSign/DocumentEdit.jsp?RecordID='+document.all." + 
        field + ".value+'&EditType=1&UserName='+document.all.user_Name.value+'&ShowSign=0&CanSave=0&moduleType=information&saveHtmlImage=0&saveDocFile=0&field=" + 
        field + "&FileType=.xls', '', 'status=no,menubar=no,scrollbars=yes,resizable=yes,width=500,Height=400,left=0,top=0')\\\" value='查看正文' />";
      type = "";
    } else {
      html = "<input type=hidden id=" + field + " name=" + 
        field + " value=\\\"" + fieldValue + "\\\">";
      String bjzw2017 = "编辑正文";
      if ("shandongguotou".equals(SystemCommon.getCustomerName()))
        bjzw2017 = "打开正文"; 
      html = String.valueOf(html) + "<input type='button' class=btnButton4font onclick=\\\"window.open('/jsoa/iWebOfficeSign/DocumentEdit.jsp?RecordID='+document.all." + 
        field + ".value+'&EditType=1&UserName='+document.all.user_Name.value+'&ShowSign=0&CanSave=1&moduleType=information&saveHtmlImage=0&saveDocFile=0&field=" + 
        field + "&FileType=.xls&showEditButton=1&showSignButton=1', '', 'status=no,menubar=no,scrollbars=yes,resizable=yes,width=500,Height=400,left=0,top=0')\\\" value='" + bjzw2017 + "' />" + (
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
