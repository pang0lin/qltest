package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import com.js.util.config.SystemCommon;
import com.js.util.util.BASE64;
import javax.servlet.http.HttpServletRequest;

public class ElementHTMLGetterImpl_118 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      noWriteField.indexOf(fieldTemp[0][5]) >= 0) {
      html = "<input type=text style=font-size:1em; class=flowInput id=" + 
        field + " name=" + field + " readonly>";
    } else {
      html = "<input type=hidden id=" + field + " name=" + field + ">";
      String bjzw2017 = "编辑正文";
      if ("shandongguotou".equals(SystemCommon.getCustomerName()))
        bjzw2017 = "打开正文"; 
      html = String.valueOf(html) + "<input type=\\\"button\\\" style=font-size:1em; id=_weboffice_view name=_weboffice_view class=btnButton4font onclick=\\\"editOffices('" + 
        
        field + 
        "','wps','" + 
        fieldTemp[0][6] + "','0');\\\" value=\"" + bjzw2017 + "\" />";
    } 
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") < 0 && 
      noWriteField.indexOf(fieldTemp[0][5]) < 0 && 
      fieldTemp[0][3].equals("1"))
      html = String.valueOf(html) + "<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + "><label class=mustFillcolor>*</label>"; 
    String html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + 
      field + "').innerHTML='" + type + 
      "';document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "').innerHTML+=\"" + html + "\";}";
    return html;
  }
  
  public String getEditHTML(String fieldValue, String hideField, String[][] fieldTemp, String fromDraft, String field, boolean isNum, int maxlen, String type, String computeFieldStr, HttpServletRequest request, String temp, String[] tempArr, String fieldId, String infoId, String pageId) {
    String html = "";
    if (hideField != null && (
      hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      hideField.indexOf(fieldTemp[0][5]) >= 0)) {
      html = "<input type=hidden id=" + field + " name=" + 
        field + " value=\\\"" + fieldValue + "\\\">";
      fieldValue = String.valueOf(fieldValue) + ".doc";
      String url = "/jsoa/weixin/workflow/daibanflow/weixindownload.jsp?" + BASE64.BASE64EncoderNoBR("FileName=" + fieldValue + "&name=" + fieldValue + "&path=information");
      html = "<div align=\\\"left\\\" title=\\\"点击下载\\\">&nbsp;<input type=\\\"button\\\" onclick=\\\"window.open('" + url + "','')\\\" class=btnButton4font value=\\\"查看正文\\\" /></div>";
      type = "";
    } else {
      html = "<input type=hidden id=" + field + " name=" + 
        field + " value=\\\"" + fieldValue + "\\\">";
      fieldValue = String.valueOf(fieldValue) + ".doc";
      String url = "/jsoa/weixin/workflow/daibanflow/weixindownload.jsp?" + BASE64.BASE64EncoderNoBR("FileName=" + fieldValue + "&name=" + fieldValue + "&path=information");
      html = "<div align=\\\"left\\\" title=\\\"点击下载\\\">&nbsp;<input type=\\\"button\\\" onclick=\\\"window.open('" + url + "','')\\\" class=btnButton4font value=\\\"查看正文\\\" /></div>";
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
    return html;
  }
}
