package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import java.sql.SQLException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

public class ElementHTMLGetterImpl_111 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    if (fieldTemp[0][6] != null && fieldTemp[0][6].startsWith("@") && 
      !fieldTemp[0][6].startsWith("@$@")) {
      if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
        noWriteField.indexOf(fieldTemp[0][5]) >= 0) {
        html = "<input type=text  class=flowInput id=" + field + 
          " name=" + field + 
          " readonly style=width:95%;font-size:1em; " + 
          "onblur=bianhaoBlur(this," + fieldTemp[0][0] + ",\"" + 
          fieldTemp[0][6] + "\"); >";
      } else {
        html = "<select style=font-size:1em; onchange=getAutoCode(this,\\\"" + 
          field + "\\\")><option value=0>==请选择==</option>";
        String tableTemp = fieldTemp[0][6].split("=")[0];
        String table = tableTemp.substring(tableTemp.indexOf("][") + 2, 
            tableTemp.length() - 1);
        String[][] data = (String[][])null;
        DbOpt dbopt = null;
        try {
          dbopt = new DbOpt();
          data = dbopt.executeQueryToStrArr2("select " + 
              table.substring(0, table.indexOf(".")) + 
              "_id," + 
              table.substring(table.indexOf(".") + 1, 
                table.length()) + " from " + 
              table.substring(0, table.indexOf(".")));
          dbopt.close();
        } catch (Exception e3) {
          if (dbopt != null)
            try {
              dbopt.close();
            } catch (SQLException e) {
              e.printStackTrace();
            }  
          e3.printStackTrace();
        } 
        if (data != null)
          for (int i = 0; i < data.length; i++)
            html = String.valueOf(html) + "<option value=\"" + data[i][1] + "\">" + 
              data[i][1] + "</option>";  
        html = String.valueOf(html) + "</select><input type=text style=font-size:1em; class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
          field + " name=" + field + " readonly";
        if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") < 0 && 
          noWriteField.indexOf(fieldTemp[0][5]) < 0 && 
          fieldTemp[0][3].equals("1")) {
          html = String.valueOf(html) + " style=width:80%; ><input type=hidden name=mustWrite id=mustWrite value=" + 
            
            field + "><label class=mustFillcolor>*</label>";
        } else {
          html = String.valueOf(html) + " style=width:95%; >";
        } 
      } 
      html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + 
        field + "'))\n{document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + "').innerHTML='" + type + 
        "';document.getElementById('" + fieldTemp[0][0] + "-" + 
        field + "').innerHTML+='" + html + "';}";
    } else {
      if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
        noWriteField.indexOf(fieldTemp[0][5]) >= 0) {
        html = "<input type=text  class=flowInput id=" + field + 
          " name=" + field + " value=\"" + 
          getAutoCode(field, fieldId) + 
          "\" style=width:95%;font-size:1em; readonly>";
      } else {
        html = "<input type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
          field + 
          " name=" + 
          field + 
          " value=\"" + 
          getAutoCode(field, fieldId) + "\" ";
        if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") < 0 && 
          noWriteField.indexOf(fieldTemp[0][5]) < 0 && 
          fieldTemp[0][3].equals("1")) {
          html = String.valueOf(html) + " style=width:80%;font-size:1em; onblur=bianhaoBlur(this," + 
            fieldTemp[0][0] + 
            ",\"" + 
            fieldTemp[0][6] + 
            "\"); ><input type=hidden name=mustWrite id=mustWrite value=" + 
            field + "><label class=mustFillcolor>*</label>";
        } else {
          html = String.valueOf(html) + " style=width:95%; onblur=bianhaoBlur(this," + 
            fieldTemp[0][0] + ",\"" + fieldTemp[0][6] + 
            "\"); >";
        } 
      } 
      html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + 
        field + "'))\n{document.getElementById('" + 
        fieldTemp[0][0] + "-" + field + "').innerHTML='" + type + 
        "';document.getElementById('" + fieldTemp[0][0] + "-" + 
        field + "').innerHTML+='" + html + "';}";
    } 
    return html;
  }
  
  private String getAutoCode(String fieldName, String fieldId) {
    String newCode = "";
    DbOpt dbopt = null;
    String codeHead = "", codeStart = "", codeInsert = "", insertLen = "0", codeAdd = "", currentValue = "";
    try {
      dbopt = new DbOpt();
      String[][] codeValue = dbopt
        .executeQueryToStrArr2("select field_value,field_codevalue from tfield where field_id=" + 
          fieldId);
      if (codeValue != null && codeValue.length > 0) {
        String[] temp = (codeValue[0][0] == null) ? null : 
          codeValue[0][0].split("=");
        if (temp != null && temp.length > 0) {
          codeHead = temp[0];
          codeStart = temp[1];
          codeInsert = temp[2];
          insertLen = temp[3];
          codeAdd = temp[4];
        } 
        int nowYear = (new Date()).getYear() + 1900;
        int nowMonth = (new Date()).getMonth() + 1;
        int nowDay = (new Date()).getDate();
        String fieldValue = String.valueOf(codeHead) + "=" + codeStart + "=" + 
          codeInsert + "=" + insertLen + "=" + codeAdd + 
          "=@$@flag@$@=" + nowYear + "=" + nowMonth + "=" + 
          codeStart;
        boolean flag = false;
        if (temp.length < 7) {
          fieldValue = fieldValue.replace("@$@flag@$@", "a");
          dbopt.executeUpdate("update tfield set field_value='" + 
              fieldValue + "' where field_id=" + fieldId);
        } else {
          if (temp[5].equals("b") && !temp[6].equals((new StringBuilder(String.valueOf(nowYear))).toString())) {
            fieldValue = fieldValue.replace("@$@flag@$@", "b");
            flag = true;
          } 
          if (temp[5].equals("c") && !temp[7].equals((new StringBuilder(String.valueOf(nowMonth))).toString())) {
            fieldValue = fieldValue.replace("@$@flag@$@", "c");
            flag = true;
          } 
          if (temp[5].equals("d") && (
            !temp[6].equals((new StringBuilder(String.valueOf(nowYear))).toString()) || 
            !temp[7].equals((new StringBuilder(String.valueOf(nowMonth))).toString()))) {
            fieldValue = fieldValue.replace("@$@flag@$@", "d");
            flag = true;
          } 
          if (flag) {
            dbopt.executeUpdate("update tfield set field_value='" + 
                fieldValue + "' where field_id=" + fieldId);
            dbopt.executeUpdate("update tfield set field_codevalue=" + 
                codeStart + " where field_id=" + fieldId);
          } 
        } 
        currentValue = dbopt
          .executeQueryToStr("select field_codevalue from tfield where field_id=" + 
            fieldId);
        int len = (currentValue == null) ? 0 : currentValue.length();
        for (int j = 0; j < Integer.parseInt(insertLen) - len; j++)
          currentValue = String.valueOf(codeInsert) + currentValue; 
        if (codeHead.indexOf("@$@year@$@") >= 0)
          codeHead = codeHead.replace("@$@year@$@", nowYear); 
        if (codeHead.indexOf("@$@month@$@") >= 0)
          codeHead = codeHead.replace("@$@month@$@", 
              (nowMonth < 10) ? ("0" + nowMonth) : (new StringBuilder(String.valueOf(nowMonth))).toString()); 
        if (codeHead.indexOf("@$@day@$@") >= 0)
          codeHead = codeHead.replace("@$@day@$@", (nowDay < 10) ? ("0" + 
              nowDay) : (new StringBuilder(String.valueOf(nowDay))).toString()); 
        newCode = String.valueOf(codeHead) + currentValue;
      } 
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return newCode;
  }
  
  public String getEditHTML(String fieldValue, String hideField, String[][] fieldTemp, String fromDraft, String field, boolean isNum, int maxlen, String type, String computeFieldStr, HttpServletRequest request, String temp, String[] tempArr, String fieldId, String infoId, String pageId) {
    if (hideField != null && (
      hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      hideField.indexOf(fieldTemp[0][5]) >= 0)) {
      html = (fieldValue == null) ? "&nbsp" : 
        fieldValue.replaceAll("\n", "<br>")
        .replaceAll("\r", "");
      if ("1".equals(fromDraft) || SystemCommon.getSYWorkflowHR() == 1) {
        html = String.valueOf(html) + "<input type=hidden  id=" + 
          field + " name=" + 
          field + " value=\"" + (
          (fieldValue == null) ? "&nbsp" : fieldValue.replaceAll("\n", "").replaceAll("\r", "")) + 
          "\" " + 
          " readonly>";
      } else {
        type = "";
      } 
    } else {
      if (fieldValue == null || "null".equals(fieldValue) || "".equals(fieldValue))
        fieldValue = getAutoCode(field, fieldId); 
      html = 
        "<input type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
        field + " name=" + 
        field + " value=\"" + (
        (fieldValue == null) ? "&nbsp" : fieldValue.replaceAll("\n", "").replaceAll("\r", "")) + 
        "\" " + (
        isNum ? ("  onblur=checkNum(this);checkSize(this);bianhaoBlur(this," + fieldTemp[0][0] + ",\"" + fieldTemp[0][6] + "\");") : (
        " onblur=checkSize(this);bianhaoBlur(this," + fieldTemp[0][0] + ",\"" + fieldTemp[0][6] + "\");")) + ((
        
        fieldTemp[0][3].equals("1") && hideField.indexOf("," + fieldTemp[0][0] + ",") < 0 && hideField.indexOf(fieldTemp[0][5]) < 0) ? (
        " sytle=width:80%;font-size:1em; ><input type=hidden name=mustWrite id=mustWrite value=" + field + 
        "><label class=mustFillcolor>*</label>") : 
        " sytle=width:95%;font-size:1em; >");
    } 
    String html = "if(document.getElementById('" + fieldTemp[0][0] + 
      "-" + field + "'))\n{document.getElementById('" + 
      fieldTemp[0][0] + "-" + field + 
      "').innerHTML='" + type + 
      "';document.getElementById('" + 
      fieldTemp[0][0] + "-" + field + 
      "').innerHTML+='" + html + "';}";
    return html;
  }
  
  public String getForeignEditHTMLForWeiXin(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr, boolean isHide, String type, boolean isNum, int maxlen, String temp, String[] tempArr) {
    String html = "";
    if (isHide) {
      html = (fieldValue == null) ? "&nbsp" : fieldValue.replaceAll("\n", "<br>").replaceAll("\r", "");
      type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
    } else {
      html = 
        "<input type=text class=flowInput onmouseover=setStyle(this) onmouseout=setStyle(this) id=" + 
        field + " name=" + field + " value=\"" + ((fieldValue == null) ? "&nbsp" : 
        fieldValue.replaceAll("\n", "").replaceAll("\r", "")) + "\" " + (
        isNum ? "  onblur=checkNum(this);checkSize(this);" : " onblur=checkSize(this);") + (fieldTemp[index][3].equals("1") ? (
        " style=width:80%;font-size:1em; ><input type=hidden name=mustWrite id=mustWrite value=" + field + "><label class=mustFillcolor>*</label>") : " sytle=width:95%; >");
    } 
    html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
      fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + type + "';document.getElementsByName('" + 
      fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML+='" + html + "';}";
    return html;
  }
}
