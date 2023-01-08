package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import com.js.oa.jsflow.util.FormReflection;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ElementHTMLGetterImpl_101 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String defaultValue = "";
    DbOpt dbopt = null;
    dbopt = new DbOpt();
    if (fieldTemp[0][4] != null)
      defaultValue = fieldTemp[0][4].toString(); 
    if (defaultValue.startsWith("$[$[") && defaultValue.endsWith("]]")) {
      String dataSourceName = defaultValue.substring(4, 
          defaultValue.indexOf("]$["));
      String sql = defaultValue.substring(
          defaultValue.indexOf("]$[") + 3, defaultValue.length() - 2);
      HttpSession hSession = request.getSession();
      String userId = hSession.getAttribute("userId").toString();
      String orgId = hSession.getAttribute("orgId").toString();
      String userAccount = hSession.getAttribute("userAccount")
        .toString();
      String userName = hSession.getAttribute("userName").toString();
      sql = sql.replaceAll("\\@\\$\\@userId\\@\\$\\@", userId);
      sql = sql.replaceAll("\\@\\$\\@orgId\\@\\$\\@", orgId);
      sql = sql.replaceAll("\\@\\$\\@userAccount\\@\\$\\@", userAccount);
      sql = sql.replaceAll("\\@\\$\\@userName\\@\\$\\@", userName);
      DbOpt dbo = null;
      try {
        if (!"system".equals(dataSourceName)) {
          dbo = new DbOpt(dataSourceName);
          String dbType = 
            SystemCommon.getUserDatabaseType(dataSourceName);
          if (dbType.indexOf("oracle") >= 0) {
            String lang = 
              SystemCommon.getUserDatabaseLang(dataSourceName);
            if (!"".equals(lang))
              dbo.executeUpdate("ALTER SESSION SET NLS_LANGUAGE='" + 
                  lang + "'"); 
          } 
          defaultValue = dbo.executeQueryToStr(sql);
          dbo.close();
        } else {
          defaultValue = dbopt.executeQueryToStr(sql);
        } 
      } catch (Exception e3) {
        defaultValue = "";
        if (dbo != null)
          try {
            dbo.close();
          } catch (SQLException e) {
            e.printStackTrace();
          }  
        e3.printStackTrace();
      } 
    } else if (defaultValue.startsWith("@[className") && 
      defaultValue.endsWith("]")) {
      String className = defaultValue.substring(12, 
          defaultValue.indexOf(";methodName:"));
      String classMethod = defaultValue.substring(
          defaultValue.indexOf(";methodName:") + 12, 
          defaultValue.length() - 1);
      if (!"".equals(className) && !"".equals(classMethod))
        try {
          FormReflection formReflection = new FormReflection();
          Object obj = formReflection.execute(className, classMethod, 
              request);
          if (obj != null)
            defaultValue = obj.toString(); 
        } catch (Exception e) {
          defaultValue = "";
          e.printStackTrace();
        }  
    } 
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      noWriteField.indexOf(fieldTemp[0][5]) >= 0) {
      html = "<input type=text name=" + field + " id=" + field + 
        " style=width:" + (
        fieldTemp[0][3].equals("1") ? "92" : "100") + 
        "%;font-size:1em; class=flowInput value=\"" + 
        defaultValue + "\" readonly>";
    } else {
      String onblur = "";
      if ("1".equals(fieldTemp[0][11]))
        onblur = "setSTotalValue(this);"; 
      if (computeFieldStr.indexOf(field) >= 0)
        onblur = String.valueOf(onblur) + "setComputeForeignFieldNew(this);"; 
      html = "<input type=text style=width:" + (
        fieldTemp[0][3].equals("1") ? "92" : "100") + 
        "%;font-size:1em;  name=" + 
        field + 
        " id=" + 
        field + 
        " value=\"" + 



        
        defaultValue + 
        "\" class=flowInput " + (
        isNum ? ("  onblur=checkNum(this);checkSize(this);" + 
        onblur) : ("maxlength=\"" + maxlen + "\"")) + ">";
      if (fieldTemp[0][3].equals("1") && 
        noWriteField.indexOf("," + fieldTemp[0][0] + ",") < 0 && noWriteField
        .indexOf(fieldTemp[0][5]) < 0)
        html = String.valueOf(html) + "<input type=hidden name=mustWrite id=mustWrite value=" + 
          field + "><label class=mustFillcolor>*</label>"; 
    } 
    String html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + 
      field + "').innerHTML='" + type + html + "';}";
    try {
      dbopt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    return html;
  }
  
  public String getEditHTML(String fieldValue, String hideField, String[][] fieldTemp, String fromDraft, String field, boolean isNum, int maxlen, String type, String computeFieldStr, HttpServletRequest request, String temp, String[] tempArr, String fieldId, String infoId, String pageId) {
    fieldValue = fieldValue.replaceAll("\\\\", "\\\\\\\\");
    if (hideField != null && (
      hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || hideField.indexOf("," + fieldTemp[0][5] + ",") >= 0)) {
      if ("1".equals(fromDraft)) {
        html = "<input style=width:" + (fieldTemp[0][3].equals("1") ? "92" : "100") + "% ;font-size:1em; type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
          field + " name=" + field + " value='" + fieldValue + "' " + (
          isNum ? "  onblur=checkNum(this);checkSize(this);" : ("maxlength='" + maxlen + "'")) + " readonly>";
      } else {
        html = (fieldValue == null) ? "&nbsp" : fieldValue.replaceAll("\n", "<br>").replaceAll("\r", "");
        type = "<input type=hidden id=" + field + "  name=" + field + " value=\"" + fieldValue + "\">";
      } 
    } else {
      String onblur = "";
      if ("1".equals(fieldTemp[0][11]))
        onblur = "setSTotalValue(this);"; 
      if (computeFieldStr.indexOf(field) >= 0)
        onblur = String.valueOf(onblur) + "setComputeForeignFieldNew(this);"; 
      html = "<input style=width:" + (fieldTemp[0][3].equals("1") ? "92" : "100") + "%;font-size:1em; type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
        field + " name=" + field + " value='" + fieldValue + "' " + (
        isNum ? ("  onblur=checkNum(this);checkSize(this);" + onblur) : ("maxlength='" + maxlen + "'")) + ">" + ((
        fieldTemp[0][3].equals("1") && hideField.indexOf("," + fieldTemp[0][0] + ",") < 0 && hideField.indexOf(fieldTemp[0][5]) < 0) ? (
        "<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + "><label class=mustFillcolor>*</label>") : "");
    } 
    String html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + field + "').innerHTML='" + type + 
      "';document.getElementById('" + fieldTemp[0][0] + "-" + field + "').innerHTML+=\"" + html + "\";}";
    return html;
  }
  
  public String getForeignEditHTMLForWeiXin(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr, boolean isHide, String type, boolean isNum, int maxlen, String temp, String[] tempArr) {
    String html = "";
    String onblur = "";
    if ("1".equals(isTotalField))
      onblur = "setSTotalValue(this);"; 
    if (computeFieldStr.indexOf(field) >= 0)
      onblur = String.valueOf(onblur) + "setComputeForeignFieldNew(this);"; 
    fieldValue = fieldValue.replaceAll("\\\\", "\\\\\\\\");
    if (isHide) {
      fieldValue.replaceAll("\n", "").replaceAll("\r", "");
      if ("1".equals(fromDraft)) {
        html = "<input style=width:" + (
          fieldTemp[index][3].equals("1") ? "92" : "100") + "%;font-size:1em; type=text class=flowInput onblur=" + onblur + " id=" + 
          field + " name=" + field + " value='" + 
          fieldValue + "' readonly> ";
      } else {
        html = (fieldValue == null) ? "&nbsp" : 
          fieldValue.replaceAll("\n", "<br>")
          .replaceAll("\r", "");
        type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=\"" + fieldValue + "\">";
      } 
    } else {
      html = "<input style=width:" + (
        fieldTemp[index][3].equals("1") ? "92" : "100") + "%;font-size:1em; type=text class=flowInput id=" + 
        field + " name=" + field + " value='" + 
        fieldValue + "' " + (
        isNum ? (" onblur=checkNum(this);checkSize(this);" + onblur) : (
        "maxlength='" + maxlen + "'")) + ">" + (
        fieldTemp[index][3].equals("1") ? (
        "<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + 
        "><label class=mustFillcolor>*</label>") : 
        "");
    } 
    html = "if(document.getElementsByName('" + 
      fieldTemp[index][0] + "-" + field + "')[" + seq + 
      "])\n{document.getElementsByName('" + 
      fieldTemp[index][0] + "-" + field + "')[" + seq + 
      "].innerHTML='" + type + 
      "';document.getElementsByName('" + 
      fieldTemp[index][0] + "-" + field + "')[" + 
      seq + "].innerHTML+=\"" + html + "\";}";
    return html;
  }
}
