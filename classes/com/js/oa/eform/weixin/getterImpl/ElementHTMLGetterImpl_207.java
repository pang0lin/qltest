package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class ElementHTMLGetterImpl_207 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    Object object = request.getSession().getAttribute("userId");
    List<String[]> orgList = StaticParam.haveSidelineOrg((String)object);
    if (orgList == null) {
      if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
        noWriteField.indexOf(fieldTemp[0][5]) >= 0) {
        html = "<input type=hidden id=" + field + " name=" + field + 
          ">";
      } else {
        html = "<input type=hidden id=" + field + " name=" + field + 
          ">";
      } 
      String temp5 = "\n var " + field + 
        "_temp=document.createElement('SCRIPT');";
      temp5 = String.valueOf(temp5) + field + "_temp.text=\"document.all." + field + ".value=" + 
        "orgNameShow(document.all.org_Name.value," + 
        SystemCommon.getInnerShow() + ");\";";
      html = String.valueOf(temp5) + "if(document.getElementById('" + fieldTemp[0][0] + 
        "-" + field + "'))\n" + "{document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + "').innerHTML=" + 
        "orgNameShow(document.all.org_Name.value," + 
        SystemCommon.getInnerShow() + ")+'" + type + 
        "';document.getElementById('" + fieldTemp[0][0] + "-" + 
        field + "').innerHTML+=\"" + html + "\";" + 
        "document.getElementById('" + fieldTemp[0][0] + "-" + 
        field + "').appendChild(" + field + "_temp);}\n";
    } else {
      String select = "<select style=font-size:1em; id=" + field + 
        " name=" + field + " >";
      for (int s = 0; s < orgList.size(); s++) {
        String[] orgInfo = orgList.get(s);
        select = String.valueOf(select) + "<option value=" + StaticParam.getOrgByNum(orgInfo[1]) + (
          (s == 0) ? " selected" : "") + ">" + 
          StaticParam.getOrgByNum(orgInfo[1]) + "</option>";
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
      String abString = "   <input type=hidden id=" + field + " name=" + field + " value=" + fieldValue + ">";
      html = "if(document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "'))\n{document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + 
        "').innerHTML=" + (
        (fieldValue == null) ? "'&nbsp'" : (
        "orgNameShow('" + fieldValue.replaceAll("\n", "<br>")
        .replaceAll("\r", ""))) + "'," + SystemCommon.getInnerShow() + ")+'" + abString + "' ;}";
    } else {
      Object object = request.getSession().getAttribute("userId");
      List<String[]> orgList = StaticParam.haveSidelineOrg((String)object);
      if (orgList == null) {
        html = "<input type=hidden id=" + 
          field + " name=" + 
          field + " value='" + fieldValue + "'>";
        String temp3 = "var " + field + 
          "_temp=document.createElement('SCRIPT');";
        temp3 = String.valueOf(temp3) + field + "_temp.text=\"document.all." + field + 
          ".value=orgNameShow(document.all.org_Name.value," + SystemCommon.getInnerShow() + ");\";";
        html = String.valueOf(temp3) + "if(document.getElementById('" + 
          fieldTemp[0][0] + "-" + field + 
          "'))\n{document.getElementById('" + 
          fieldTemp[0][0] + "-" + field + 
          "').innerHTML=orgNameShow(document.all.org_Name.value," + SystemCommon.getInnerShow() + ")+'" + type + 
          "';document.getElementById('" + 
          fieldTemp[0][0] + "-" + field + 
          "').innerHTML+=\"" + html + "\";document.getElementById('" + 
          fieldTemp[0][0] + "-" + field + 
          "').appendChild(" + field + "_temp);}";
      } else {
        String select = "<input type=hidden id=" + field + "_type name=" + 
          field + "_type value=varchar><select style=font-size:1em; id=" + field + " name=" + field + " >";
        for (int s = 0; s < orgList.size(); s++) {
          String[] orgInfo = orgList.get(s);
          select = String.valueOf(select) + "<option value=" + orgInfo[1] + ((s == 0) ? " selected" : "") + ">" + StaticParam.getOrgByNum(orgInfo[1]) + "</option>";
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
      type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
      html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
        fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + ((fieldValue == null) ? "&nbsp" : 
        fieldValue.replaceAll("\n", "<br>").replaceAll("\r", "")) + type + "';}";
    } else {
      html = 
        "<input style=width:" + (fieldTemp[index][3].equals("1") ? "92" : "100") + "%;font-size:1em; type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
        field + " name=" + 
        field + " value='" + fieldValue + "'>" + (
        fieldTemp[index][3].equals("1") ? (
        "<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + 
        "><label class=mustFillcolor>*</label>") : 
        "");
      html = "if(document.getElementsByName('" + 
        fieldTemp[index][0] + "-" + field + 
        "')[" + seq + "])\n{document.getElementsByName('" + 
        fieldTemp[index][0] + "-" + field + 
        "')[" + seq + "].innerHTML='" + type + 
        "';document.getElementsByName('" + 
        fieldTemp[index][0] + "-" + field + 
        "')[" + seq + "].innerHTML+=\"" + html + "\";}";
    } 
    return html;
  }
}
