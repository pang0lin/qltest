package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import com.js.oa.userdb.util.DbOpt;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;

public class ElementHTMLGetterImpl_402 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    DbOpt dbopt = new DbOpt();
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
      noWriteField.indexOf(fieldTemp[0][5]) >= 0) {
      html = "<select style=width:" + (
        fieldTemp[0][3].equals("1") ? "92" : "100") + 
        "%;font-size:1em; id=" + field + " name=" + field + 
        " disabled><option value=\"\">==请选择==</option>";
    } else {
      html = "<select id=" + field + " name=" + field + 
        "><option value=\"\">==请选择==</option>";
    } 
    String temp = fieldTemp[0][6];
    if (temp == null || temp.trim().length() < 1) {
      try {
        dbopt.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
      return "";
    } 
    if (temp.startsWith("@")) {
      String table = temp.substring(temp.indexOf("][") + 2, 
          temp.length() - 1);
      String[][] data = (String[][])null;
      try {
        data = dbopt.executeQueryToStrArr2("select " + 
            table.substring(0, table.indexOf(".")) + 
            "_id," + 
            table.substring(table.indexOf(".") + 1, 
              table.length()) + " from " + 
            table.substring(0, table.indexOf(".")));
      } catch (Exception exception) {}
      if (data != null)
        for (int i = 0; i < data.length; i++)
          html = String.valueOf(html) + "<option value=\"" + data[i][1] + "\">" + 
            data[i][1] + "</option>";  
    } 
    html = String.valueOf(html) + "</select>";
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") < 0 && 
      noWriteField.indexOf(fieldTemp[0][5]) < 0 && 
      fieldTemp[0][3].equals("1"))
      html = String.valueOf(html) + "<input type=hidden name=mustWrite id=mustWrite value=" + 
        field + "><label class=mustFillcolor>*</label>"; 
    html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + 
      field + "').innerHTML='" + type + 
      "';document.getElementById('" + fieldTemp[0][0] + "-" + field + 
      "').innerHTML+='" + html + "';}";
    try {
      dbopt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    return html;
  }
  
  public String getEditHTML(String fieldValue, String hideField, String[][] fieldTemp, String fromDraft, String field, boolean isNum, int maxlen, String type, String computeFieldStr, HttpServletRequest request, String temp, String[] tempArr, String fieldId, String infoId, String pageId) {
    String html = "";
    DbOpt dbopt = new DbOpt();
    try {
      html = "<select id=" + field + " name=" + field + 
        "><option value=\"\">==请选择==</option>";
      try {
        temp = dbopt.executeQueryToStr(
            "select field_value from tfield where field_id=" + fieldId);
      } catch (SQLException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      } 
      if (temp != null && temp.trim().length() >= 1)
        if (hideField != null && (
          hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
          hideField.indexOf(fieldTemp[0][5]) >= 0)) {
          html = "";
          if (temp.startsWith("@")) {
            String table = temp.substring(temp.indexOf("][") + 
                2, temp.length() - 1);
            String[][] data = (String[][])null;
            try {
              data = dbopt.executeQueryToStrArr2(
                  "select " + 
                  table.substring(0, 
                    table.indexOf(".")) + "_id," + 
                  table.substring(table.indexOf(".") + 
                    1, table.length()) + " from " + 
                  table.substring(0, 
                    table.indexOf(".")));
            } catch (Exception exception) {}
            if (data != null)
              for (int i = 0; i < data.length; i++)
                html = String.valueOf(html) + (
                  (fieldValue.indexOf(data[i][1]) >= 0) ? 
                  
                  data[i][1] : "");  
          } 
          html = "if(document.getElementById('" + 
            fieldTemp[0][0] + "-" + field + 
            "'))\n{document.getElementById('" + 
            fieldTemp[0][0] + "-" + field + 
            "').innerHTML='" + html + "';}";
        } else if (temp.startsWith("@")) {
          String table = temp.substring(temp.indexOf("][") + 
              2, temp.length() - 1);
          String[][] data = (String[][])null;
          try {
            data = dbopt.executeQueryToStrArr2("select " + 
                table.substring(0, table.indexOf(".")) + 
                "_id," + 
                table.substring(table.indexOf(".") + 1, 
                  table.length()) + " from " + 
                table.substring(0, table.indexOf(".")));
          } catch (Exception exception) {}
          if (data != null) {
            for (int i = 0; i < data.length; i++)
              html = String.valueOf(html) + "<option value=" + data[i][1] + (
                data[i][1].equals(fieldValue) ? 
                " selected " : "") + ">" + 
                data[i][1] + 
                "</option>"; 
            html = "if(document.getElementById('" + 
              fieldTemp[0][0] + "-" + 
              field + 
              "'))\n{document.getElementById('" + 
              fieldTemp[0][0] + "-" + field + 
              "').innerHTML='" + type + 
              "';document.getElementById('" + 
              fieldTemp[0][0] + "-" + 
              field + "').innerHTML+='" + html + ((
              fieldTemp[0][3].equals("1") && hideField.indexOf("," + fieldTemp[0][0] + ",") < 0 && hideField.indexOf(fieldTemp[0][5]) < 0) ? (
              "<input type=hidden name=mustWrite id=mustWrite value=" + 
              field + 
              "><label class=mustFillcolor>*</label>") : 
              "") + "';}";
          } 
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (dbopt != null)
        try {
          dbopt.close();
        } catch (SQLException ex) {
          ex.printStackTrace();
        }  
    } 
    return html;
  }
  
  public String getForeignEditHTMLForWeiXin(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr, boolean isHide, String type, boolean isNum, int maxlen, String temp, String[] tempArr) {
    String html = "";
    DbOpt dbopt = new DbOpt();
    try {
      html = "<select style=font-size:1em; id=" + field + " name=" + field + "><option value=\"\">==请选择==</option>";
      try {
        temp = dbopt.executeQueryToStr("select field_value from tfield where field_id=" + fieldId);
      } catch (SQLException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      } 
      if (temp != null && temp.trim().length() >= 1)
        if (isHide) {
          html = "";
          if (temp.startsWith("@")) {
            String table = temp.substring(temp.indexOf("][") + 2, temp.length() - 1);
            String[][] data = (String[][])null;
            try {
              data = dbopt.executeQueryToStrArr2("select " + table.substring(0, table.indexOf(".")) + "_id," + 
                  table.substring(table.indexOf(".") + 1, table.length()) + " from " + table.substring(0, table.indexOf(".")));
            } catch (Exception exception) {}
            if (data != null)
              for (int i = 0; i < data.length; i++)
                html = String.valueOf(html) + ((fieldValue.indexOf(data[i][1]) >= 0) ? data[i][1] : "");  
          } 
          type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
          html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
            fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + html + type + "';}";
        } else if (temp.startsWith("@")) {
          String table = temp.substring(temp.indexOf("][") + 
              2, temp.length() - 1);
          String[][] data = (String[][])null;
          try {
            data = dbopt.executeQueryToStrArr2("select " + 
                table.substring(0, table.indexOf(".")) + 
                "_id," + 
                table.substring(table.indexOf(".") + 1, 
                  table.length()) + " from " + 
                table.substring(0, table.indexOf(".")));
          } catch (Exception exception) {}
          if (data != null) {
            for (int i = 0; i < data.length; i++)
              html = String.valueOf(html) + "<option value=" + data[i][1] + (
                data[i][1].equals(fieldValue) ? 
                " selected " : "") + ">" + 
                data[i][1] + 
                "</option>"; 
            html = "if(document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + 
              field + 
              "')[" + seq + "])\n{document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "].innerHTML='" + type + 
              "';document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + 
              field + "')[" + seq + "].innerHTML+='" + html + (
              fieldTemp[index][3].equals("1") ? (
              "<input type=hidden name=mustWrite id=mustWrite value=" + 
              field + 
              "><label class=mustFillcolor>*</label>") : 
              "") + "';}";
          } 
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (dbopt != null)
        try {
          dbopt.close();
        } catch (SQLException ex) {
          ex.printStackTrace();
        }  
    } 
    return html;
  }
}
