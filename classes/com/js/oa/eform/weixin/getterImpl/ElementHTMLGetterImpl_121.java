package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ElementHTMLGetterImpl_121 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    String mustWrite = "";
    HttpSession hSession = request.getSession();
    String userId = hSession.getAttribute("userId").toString();
    String[] qian = qianMingTu(userId);
    String shenPi = "'<input type=\"hidden\" name=\"" + field + "\" id=\"" + 
      field + "\" value=\"\" />';";
    if (noWriteField.indexOf("," + fieldId + ",") < 0 && 
      noWriteField.indexOf(field) < 0) {
      if (fieldTemp[0][3].equals("1") && 
        noWriteField.indexOf("," + fieldId + ",") < 0 && noWriteField
        .indexOf(field) < 0)
        mustWrite = String.valueOf(mustWrite) + "<input type=hidden name=mustWrite id=mustWrite value=" + 
          field + ">" + "<label class=mustFillcolor>*</label>"; 
      shenPi = "'<input type=hidden name=" + field + "_type id=" + field + 
        "_type value=varchar>" + "<input type=hidden name=" + 
        field + "_size id=" + field + "_size value=255>";
      String src = "0000";
      if (qian[1].length() > 6 && qian[1].substring(4, 5).equals("_")) {
        src = qian[1].substring(0, 4);
      } else {
        src = "0000";
      } 
      if ("".equals(qian[1])) {
        shenPi = String.valueOf(shenPi) + "<input type=\"text\" style=font-size:1em; name=\"" + 
          field + "\" id=\"" + field + "\" value=\"\" />" + 
          mustWrite + "';";
        shenPi = String.valueOf(shenPi) + "document.getElementById(\"" + field + 
          "\").value=document.all.user_Name.value;";
      } else {
        shenPi = String.valueOf(shenPi) + "<input type=\"hidden\" name=\"" + field + "\" id=\"" + 
          field + "\" value=\"*id*" + userId + "\" />" + 
          "<img src=\"/jsoa/upload/" + src + "/peopleinfo/" + 
          qian[1] + "\" style=\"width:80px;\" > " + mustWrite + 
          "';";
      } 
    } 
    html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + 
      field + "').innerHTML=" + shenPi + "}";
    return html;
  }
  
  private String[] qianMingTu(String empId) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String[] fileName = { "", "" };
    String sql = "select empName,signatureimgsavename from org_employee where emp_id=" + empId;
    try {
      base.begin();
      rs = base.executeQuery(sql);
      if (rs.next()) {
        fileName[0] = rs.getString(1);
        fileName[1] = rs.getString(2);
        if (fileName[1] == null || "null".equalsIgnoreCase(fileName[1]))
          fileName[1] = ""; 
      } else {
        fileName[1] = "*null*";
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return fileName;
  }
  
  public String getEditHTML(String fieldValue, String hideField, String[][] fieldTemp, String fromDraft, String field, boolean isNum, int maxlen, String type, String computeFieldStr, HttpServletRequest request, String temp, String[] tempArr, String fieldId, String infoId, String pageId) {
    String html = "";
    if (hideField != null && (
      hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      hideField.indexOf(fieldTemp[0][5]) >= 0)) {
      String[] qian = { "", "" };
      String show = fieldValue.replaceAll("\n", "<br>").replaceAll("\r", "");
      if (fieldValue.startsWith("*id*")) {
        qian = qianMingTu(fieldValue.substring(4));
        String srcOne = "0000";
        if (qian[1].length() > 6 && qian[1].substring(4, 5).equals("_")) {
          srcOne = qian[1].substring(0, 4);
        } else {
          srcOne = "0000";
        } 
        if ("".equals(qian[1])) {
          show = qian[0];
        } else {
          show = "<img src=\"/jsoa/upload/" + srcOne + "/peopleinfo/" + qian[1] + "\" style=\"width:80px;\">";
        } 
      } 
      html = "if(document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "'))\n{document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "').innerHTML='" + (
        (fieldValue == null) ? "&nbsp" : 
        show) + "';}";
    } else {
      HttpSession hSession = request.getSession();
      String userId = hSession.getAttribute("userId").toString();
      String[] qian = qianMingTu(userId);
      String srcTwo = "0000";
      if (qian[1].length() > 6 && qian[1].substring(4, 5).equals("_")) {
        srcTwo = qian[1].substring(0, 4);
      } else {
        srcTwo = "0000";
      } 
      String mustWrite = "";
      if (fieldTemp[0][3].equals("1") && hideField.indexOf("," + fieldTemp[0][0] + ",") < 0 && hideField.indexOf(fieldTemp[0][5]) < 0)
        mustWrite = String.valueOf(mustWrite) + "<input type=hidden name=mustWrite id=mustWrite value=" + field + ">" + 
          "<label class=mustFillcolor>*</label>"; 
      String shenPi = "'<input type=hidden style=\"width:80%\" name=" + field + "_type id=" + field + "_type value=varchar>" + 
        "<input type=hidden name=" + field + "_size id=" + field + "_size value=255>";
      if ("".equals(qian[1])) {
        shenPi = String.valueOf(shenPi) + "<input type=\"text\" style=font-size:1em; name=\"" + field + "\" id=\"" + field + "\" value=\"" + qian[0] + "\" />" + mustWrite + "';";
      } else {
        shenPi = String.valueOf(shenPi) + "<input type=\"hidden\" name=\"" + field + "\" id=\"" + field + "\" value=\"*id*" + userId + "\" />" + 
          "<img src=\"/jsoa/upload/" + srcTwo + "/peopleinfo/" + qian[1] + "\" style=\"width:80px;\" > " + mustWrite + "';";
      } 
      html = "if(document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "'))\n{document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "').innerHTML=" + shenPi + "}";
    } 
    return html;
  }
  
  public String getForeignEditHTMLForWeiXin(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr, boolean isHide, String type, boolean isNum, int maxlen, String temp, String[] tempArr) {
    return null;
  }
}
