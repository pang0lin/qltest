package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import com.js.system.util.StaticParam;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class ElementHTMLGetterImpl_213 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    Object object = request.getSession().getAttribute("userId");
    List<String[]> orgList2 = StaticParam.haveSidelineOrg((String)object);
    if (orgList2 == null) {
      html = "<input style=width:" + (
        fieldTemp[0][3].equals("1") ? "92" : "100") + 
        "%;font-size:1em; type=text id=" + field + " name=" + 
        field + " readonly style=border:0px solid white;>";
      String temp6 = "var " + field + 
        "_temp=document.createElement('SCRIPT');";
      temp6 = String.valueOf(temp6) + field + "_temp.text=\"document.all." + field + 
        ".value=document.all.org_ID.value;\";";
      if (noWriteField.indexOf("," + fieldTemp[0][0]) < 0 && 
        noWriteField.indexOf(fieldTemp[0][5]) < 0 && 
        fieldTemp[0][3].equals("1"))
        html = String.valueOf(html) + "<input type=hidden name=mustWrite id=mustWrite value=" + 
          field + "><label class=mustFillcolor>*</label>"; 
      html = String.valueOf(temp6) + "if(document.getElementById('" + fieldTemp[0][0] + 
        "-" + field + "'))\n{" + "document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + "').innerHTML='" + type + 
        "';document.getElementById('" + fieldTemp[0][0] + "-" + 
        field + "').innerHTML+=\"" + html + 
        "\";document.getElementById('" + fieldTemp[0][0] + "-" + 
        field + "').appendChild(" + field + "_temp);}";
    } else {
      String select = "<select id=" + field + " name=" + field + " >";
      for (int s = 0; s < orgList2.size(); s++) {
        String[] orgInfo = orgList2.get(s);
        select = String.valueOf(select) + "<option value=" + orgInfo[0] + (
          (s == 0) ? " selected" : "") + ">" + 
          StaticParam.getOrgByNum(orgInfo[1]) + "Id</option>";
      } 
      select = String.valueOf(select) + "</select>";
      html = "\nif(document.getElementById('" + fieldTemp[0][0] + "-" + 
        field + "')){\n" + "document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + "').innerHTML='" + select + 
        "';\n}\n";
    } 
    return html;
  }
  
  public String getEditHTML(String fieldValue, String hideField, String[][] fieldTemp, String fromDraft, String field, boolean isNum, int maxlen, String type, String computeFieldStr, HttpServletRequest request, String temp, String[] tempArr, String fieldId, String infoId, String pageId) {
    String html = "";
    if (hideField != null && (
      hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      hideField.indexOf(fieldTemp[0][5]) >= 0)) {
      html = fieldValue;
      type = "";
      html = "if(document.getElementById('" + fieldTemp[0][0] + 
        "-" + field + "'))\n{document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "').innerHTML='" + type + 
        "';document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "').innerHTML+='" + html + "';}";
    } else {
      Object object = request.getSession().getAttribute("userId");
      List<String[]> orgList2 = StaticParam.haveSidelineOrg((String)object);
      if (orgList2 == null) {
        html = "<input type=hidden id=" + 
          field + " name=" + 
          field + " value=\"" + fieldValue + "\" " + ">";
        String temp3 = "var " + field + 
          "_temp=document.createElement('SCRIPT');";
        temp3 = String.valueOf(temp3) + field + "_temp.text=\"document.all." + field + 
          ".value=document.all.org_ID.value;\";";
        html = String.valueOf(temp3) + "if(document.getElementById('" + fieldTemp[0][0] + 
          "-" + field + "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + field + 
          "').innerHTML=document.all.org_ID.value+'" + type + 
          "';document.getElementById('" + fieldTemp[0][0] + "-" + field + 
          "').innerHTML+='" + html + "';document.getElementById('" + 
          fieldTemp[0][0] + "-" + field + "').appendChild(" + field + "_temp);}";
      } else {
        String select = "<input type=hidden name=" + field + "_type id=" + field + "_type value=varchar><select id=" + field + " name=" + field + " >";
        for (int s = 0; s < orgList2.size(); s++) {
          String[] orgInfo = orgList2.get(s);
          select = String.valueOf(select) + "<option value=" + orgInfo[0] + ((s == 0) ? " selected" : "") + ">" + StaticParam.getOrgByNum(orgInfo[1]) + "Id</option>";
        } 
        select = String.valueOf(select) + "</select>";
        html = "\nif(document.getElementById('" + fieldTemp[0][0] + "-" + field + "')){\n" + 
          "document.getElementById('" + fieldTemp[0][0] + "-" + field + "').innerHTML='" + 
          select + "';\n}\n";
      } 
    } 
    return html;
  }
  
  public String getForeignEditHTMLForWeiXin(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr, boolean isHide, String type, boolean isNum, int maxlen, String temp, String[] tempArr) {
    String html = "";
    if (isHide) {
      html = fieldValue;
      type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
    } else {
      html = 
        "<input style=width:" + (fieldTemp[index][3].equals("1") ? "92" : "100") + "%;font-size:1em; type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
        field + " name=" + 
        field + " value=\"" + fieldValue + "\" " + (
        isNum ? "  onblur=checkNum(this);checkSize(this);" : " onblur=checkSize(this);") + 
        ">" + (
        fieldTemp[index][3].equals("1") ? (
        "<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + 
        "><label class=mustFillcolor>*</label>") : 
        "");
    } 
    html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
      fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + type + "';document.getElementsByName('" + 
      fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML+='" + html + "';}";
    return html;
  }
}
