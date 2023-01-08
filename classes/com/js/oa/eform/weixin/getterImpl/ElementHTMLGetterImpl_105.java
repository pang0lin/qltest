package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import com.js.oa.jsflow.util.InitWorkFlowData;
import com.js.oa.userdb.bean.BaseSetEJBBean;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ElementHTMLGetterImpl_105 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    DbOpt dbopt = new DbOpt();
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || noWriteField.indexOf(fieldTemp[0][5]) >= 0) {
      html = "<select style=width:" + (fieldTemp[0][3].equals("1") ? "92" : "100") + "%;font-size:1em; id=" + field + " name=" + field + 
        " disabled><option value=\"\">==请选择==</option>";
    } else {
      html = "<select style=width:" + (fieldTemp[0][3].equals("1") ? "92" : "100") + "%;font-size:1em; id=" + field + " name=" + field;
      if (Integer.parseInt(fieldTemp[0][10]) > 0)
        html = String.valueOf(html) + " onchange=\"" + field + "_onchange(this);\""; 
      if (fieldTemp[0][12] != null && "1".equals(fieldTemp[0][12]))
        html = String.valueOf(html) + " onchange=\"" + field + "_onchange(this);\""; 
      if (computeFieldStr.indexOf(field) >= 0)
        html = String.valueOf(html) + " onblur=checkSize(this); "; 
      html = String.valueOf(html) + "><option value=\"\">==请选择==</option>";
    } 
    String defaultValue = "";
    if (fieldTemp[0][4] != null && !"".equals(fieldTemp[0][4].toString())) {
      defaultValue = fieldTemp[0][4].toString();
      HttpSession hSession = request.getSession();
      String userId = hSession.getAttribute("userId").toString();
      String orgId = hSession.getAttribute("orgId").toString();
      String userAccount = hSession.getAttribute("userAccount").toString();
      String userName = hSession.getAttribute("userName").toString();
      if (defaultValue.equalsIgnoreCase("@$@CURRENTYEAR@$@")) {
        defaultValue = String.valueOf((new Date()).getYear() + 1900);
      } else if (defaultValue.equalsIgnoreCase("@$@CURRENTMONTH@$@")) {
        defaultValue = String.valueOf((new Date()).getMonth() + 1);
      } else if (defaultValue.equals("@$@userId@$@")) {
        defaultValue = userId;
      } else if (defaultValue.equals("@$@orgId@$@")) {
        defaultValue = orgId;
      } else if (defaultValue.equals("@$@userAccount@$@")) {
        defaultValue = userAccount;
      } else if (defaultValue.equals("@$@userName@$@")) {
        defaultValue = userName;
      } else {
        defaultValue = InitWorkFlowData.getValueFromRequest(request, defaultValue);
      } 
    } 
    String temp = fieldTemp[0][6];
    if (temp == null || temp.trim().length() < 1)
      return ""; 
    if (temp.startsWith("@")) {
      String table = temp.substring(temp.indexOf("][") + 2, temp.length() - 1);
      String[][] data = (String[][])null;
      try {
        String col = table.substring(table.indexOf(".") + 1, table.length());
        data = dbopt.executeQueryToStrArr2("select " + table.substring(0, table.indexOf(".")) + "_id," + col + " from " + table.substring(0, table.indexOf(".")) + " order by " + col);
      } catch (Exception exception) {}
      if (data != null)
        for (int i = 0; i < data.length; i++) {
          html = String.valueOf(html) + "<option value=\"" + data[i][0] + "\"";
          if (defaultValue.equals(data[i][0]))
            html = String.valueOf(html) + " selected "; 
          html = String.valueOf(html) + ">" + data[i][1] + "</option>";
        }  
    } else if (temp.startsWith("$")) {
      String dataSourceName = "system";
      if (temp.indexOf("].$[") > 0) {
        dataSourceName = temp.substring(2, temp.indexOf("].$["));
        sql = temp.substring(temp.indexOf("].$[") + 4, temp.length() - 1);
      } else {
        sql = temp.substring(2, temp.length() - 1);
      } 
      String sql = sql.replaceAll("@@submitPerson@@", request.getSession().getAttribute("userName").toString());
      sql = InitWorkFlowData.getValueFromRequest(request, sql);
      String[][] data = (String[][])null;
      DbOpt dbo = null;
      try {
        if (!"system".equals(dataSourceName)) {
          dbo = new DbOpt(dataSourceName);
          String dbType = SystemCommon.getUserDatabaseType(dataSourceName);
          if (dbType.indexOf("oracle") >= 0) {
            String lang = SystemCommon.getUserDatabaseLang(dataSourceName);
            if (!"".equals(lang))
              dbo.executeUpdate("ALTER SESSION SET NLS_LANGUAGE='" + lang + "'"); 
          } 
          data = dbo.executeQueryToStrArr2(sql);
          dbo.close();
        } else if (sql.length() > 0 && sql.indexOf("@$@selValue@$@") < 0) {
          data = dbopt.executeQueryToStrArr2(sql);
        } 
      } catch (Exception e3) {
        System.out.println("SQL语句错误！");
        if (dbo != null)
          try {
            dbo.close();
          } catch (SQLException e) {
            e.printStackTrace();
          }  
        e3.printStackTrace();
      } 
      if (data != null)
        for (int i = 0; i < data.length; i++) {
          html = String.valueOf(html) + "<option value=\"" + data[i][0] + "\"";
          if (defaultValue.equals(data[i][0]))
            html = String.valueOf(html) + " selected "; 
          html = String.valueOf(html) + ">" + data[i][1] + "</option>";
        }  
    } else if (temp.startsWith("*")) {
      String parentId = temp.substring(temp.indexOf(".*[") + 3, temp.length() - 1);
      String[] tempArr = (new BaseSetEJBBean()).getValue(parentId).split(";");
      for (int i = 0; i < tempArr.length; i++) {
        if (tempArr[i] != null && tempArr[i].trim().length() > 0 && tempArr[i].indexOf("/") >= 0 && tempArr[i].indexOf("/") < tempArr[i].length() - 1) {
          html = String.valueOf(html) + "<option value=\"" + tempArr[i].split("/")[0] + "\"";
          if (defaultValue.equals(tempArr[i].split("/")[0]))
            html = String.valueOf(html) + " selected "; 
          html = String.valueOf(html) + ">" + tempArr[i].split("/")[1] + "</option>";
        } 
      } 
    } else {
      if (temp.startsWith("#[interface:")) {
        HttpSession hSession = request.getSession();
        Map<String, String> map = new HashMap<String, String>();
        map.put("tableId", request.getParameter("tableId"));
        map.put("processId", request.getParameter("processId"));
        map.put("userId", hSession.getAttribute("userId").toString());
        map.put("userName", hSession.getAttribute("userName").toString());
        map.put("orgId", hSession.getAttribute("orgId").toString());
        map.put("orgName", hSession.getAttribute("orgName").toString());
        map.put("corpId", hSession.getAttribute("orgName").toString());
        map.put("userAccount", hSession.getAttribute("userAccount").toString());
        temp = InitWorkFlowData.getKeyValue(temp, map, request);
      } 
      String[] tempArr = temp.split(";");
      for (int i = 0; i < tempArr.length; i++) {
        if (tempArr[i] != null && tempArr[i].trim().length() > 0 && tempArr[i].indexOf("/") >= 0 && tempArr[i].indexOf("/") < tempArr[i].length() - 1) {
          html = String.valueOf(html) + "<option value=\"" + tempArr[i].split("/")[0] + "\"";
          if (defaultValue.equals(tempArr[i].split("/")[0]))
            html = String.valueOf(html) + " selected "; 
          html = String.valueOf(html) + ">" + tempArr[i].split("/")[1] + "</option>";
        } 
      } 
    } 
    html = String.valueOf(html) + "</select>";
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") < 0 && noWriteField.indexOf(fieldTemp[0][5]) < 0 && fieldTemp[0][3].equals("1"))
      html = String.valueOf(html) + "<input type=hidden name=mustWrite id=mustWrite value=" + field + "><label class=mustFillcolor>*</label>"; 
    html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + "'))\n{document.getElementById('" + 
      fieldTemp[0][0] + "-" + field + "').innerHTML='" + type + "';document.getElementById('" + fieldTemp[0][0] + "-" + field + "').innerHTML+='" + html + "';}";
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
      html = "<select style=width:" + (fieldTemp[0][3].equals("1") ? "92" : "100") + "% id=" + field + " name=" + field;
      if (Integer.parseInt(fieldTemp[0][10]) > 0)
        html = String.valueOf(html) + " onchange=\"" + field + "_onchange(this);\""; 
      if (computeFieldStr.indexOf(field) >= 0)
        html = String.valueOf(html) + " onblur=checkSize(this); "; 
      html = String.valueOf(html) + "><option value=\"\">==请选择==</option>";
      String[][] selectedValue = fieldIsFromOther(dbopt, fieldTemp[0][9], fieldId, fieldTemp[0][5], fieldValue, infoId, pageId, request);
      if (selectedValue != null) {
        if ((hideField != null && 
          hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0) || 
          hideField.indexOf(fieldTemp[0][5]) >= 0) {
          html = "";
          for (int i = 0; i < selectedValue.length; i++) {
            if (fieldValue.equals(selectedValue[i][0])) {
              html = "<input type=hidden name=" + field + " id=" + field + " value=\"" + 
                selectedValue[i][0] + "\">";
              html = String.valueOf(html) + selectedValue[i][1];
            } 
          } 
          html = "if(document.getElementById('" + 
            fieldTemp[0][0] + "-" + field + 
            "'))\n{document.getElementById('" + 
            fieldTemp[0][0] + "-" + field + 
            "').innerHTML='" + type + html + "';}";
        } else {
          for (int i = 0; i < selectedValue.length; i++)
            html = String.valueOf(html) + "<option value=\"" + selectedValue[i][0] + "\"" + (
              selectedValue[i][0].equals(fieldValue) ? 
              " selected " : "") + ">" + 
              selectedValue[i][1] + 
              "</option>"; 
          html = String.valueOf(html) + "</select>";
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
      } else {
        temp = fieldTemp[0][6];
        if (temp != null && temp.trim().length() >= 1) {
          if (fieldValue.contains("@#@")) {
            temp = String.valueOf(fieldValue) + "/" + fieldValue.split("@#@")[1] + ";";
          } else if (temp.startsWith("*")) {
            String parentId = temp.substring(temp.indexOf(".*[") + 3, temp.length() - 1);
            temp = (new BaseSetEJBBean()).getValue(parentId);
          } else if (temp.startsWith("#[interface:")) {
            if ("".equals(fieldValue)) {
              HttpSession hSession = request.getSession();
              Map<String, String> map = new HashMap<String, String>();
              map.put("tableId", request.getParameter("tableId"));
              map.put("processId", request.getParameter("processId"));
              map.put("userId", hSession.getAttribute("userId").toString());
              map.put("userName", hSession.getAttribute("userName").toString());
              map.put("orgId", hSession.getAttribute("orgId").toString());
              map.put("orgName", hSession.getAttribute("orgName").toString());
              map.put("corpId", hSession.getAttribute("orgName").toString());
              map.put("userAccount", hSession.getAttribute("userAccount").toString());
              String submitPerson = request.getParameter("submitPerson");
              if (submitPerson == null || "null".equals(submitPerson))
                submitPerson = hSession.getAttribute("userName").toString(); 
              map.put("submitPerson", submitPerson);
              temp = InitWorkFlowData.getKeyValue(temp, map, request);
            } else {
              temp = InitWorkFlowData.getShowContext(temp, fieldValue);
            } 
          } 
          tempArr = temp.split(";");
          if ((hideField != null && 
            hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0) || 
            hideField.indexOf(fieldTemp[0][5]) >= 0) {
            html = "";
            if (temp.startsWith("@")) {
              String table = temp.substring(temp.indexOf("][") + 
                  2, temp.length() - 1);
              String[][] data = (String[][])null;
              try {
                String col = table.substring(table.indexOf(".") + 1, table.length());
                data = dbopt.executeQueryToStrArr2(
                    "select " + 
                    table.substring(0, 
                      table.indexOf(".")) + "_id," + 
                    col + " from " + 
                    table.substring(0, 
                      table.indexOf(".")) + " order by " + col);
              } catch (Exception exception) {}
              if (data != null)
                for (int i = 0; i < data.length; i++) {
                  if (fieldValue.indexOf(data[i][0]) >= 0) {
                    html = "<input type=hidden name=" + field + " id=" + field + " value=\"" + 
                      data[i][0] + "\">";
                    html = String.valueOf(html) + data[i][1];
                  } 
                }  
            } else if (temp.startsWith("$")) {
              String dataSourceName = "system";
              if (temp.indexOf("].$[") > 0) {
                dataSourceName = temp.substring(2, temp.indexOf("].$["));
                sql = temp.substring(temp.indexOf("].$[") + 4, temp.length() - 1);
              } else {
                sql = temp.substring(2, temp.length() - 1);
              } 
              String submitPerson = request.getParameter("submitPerson");
              if (submitPerson == null || "null".equals(submitPerson))
                submitPerson = request.getSession().getAttribute("userName").toString(); 
              System.out.println("sql:" + sql);
              String sql = sql.replaceAll("@@submitPerson@@", submitPerson);
              sql = InitWorkFlowData.getValueFromRequest(request, sql);
              System.out.println("sql:" + sql);
              String[][] data = (String[][])null;
              DbOpt dbo = null;
              try {
                if (!"system".equals(dataSourceName)) {
                  dbo = new DbOpt(dataSourceName);
                  String dbType = SystemCommon.getUserDatabaseType(dataSourceName);
                  if (dbType.indexOf("oracle") >= 0) {
                    String lang = SystemCommon.getUserDatabaseLang(dataSourceName);
                    if (!"".equals(lang))
                      dbo.executeUpdate("ALTER SESSION SET NLS_LANGUAGE='" + lang + "'"); 
                  } 
                  data = dbo.executeQueryToStrArr2(sql);
                  dbo.close();
                } else if (sql.length() > 0 && sql.indexOf("@$@selValue@$@") < 0) {
                  data = dbopt.executeQueryToStrArr2(sql);
                } 
              } catch (Exception e3) {
                if (dbo != null)
                  try {
                    dbo.close();
                  } catch (SQLException e) {
                    e.printStackTrace();
                  }  
                e3.printStackTrace();
              } 
              if (data != null)
                for (int i = 0; i < data.length; i++) {
                  if (fieldValue.equals(data[i][0])) {
                    html = "<input type=hidden name=" + field + " id=" + field + " value=\"" + 
                      data[i][0] + "\">";
                    html = String.valueOf(html) + data[i][1];
                  } 
                }  
            } else {
              for (int i = 0; i < tempArr.length; i++) {
                if (tempArr[i] != null && 
                  tempArr[i].trim().length() > 0 && 
                  tempArr[i].indexOf("/") >= 0 && 
                  tempArr[i].indexOf("/") < 
                  tempArr[i].length() - 1 && 
                  tempArr[i].split("/")[0].equals(
                    fieldValue)) {
                  html = tempArr[i].split("/")[1];
                  html = String.valueOf(html) + "<input type=hidden name=" + field + " id=" + field + " value=\"" + 
                    tempArr[i].split("/")[0] + "\">";
                  break;
                } 
              } 
            } 
            html = "if(document.getElementById('" + 
              fieldTemp[0][0] + "-" + field + 
              "'))\n{document.getElementById('" + 
              fieldTemp[0][0] + "-" + field + 
              "').innerHTML='" + type + html + "';}";
          } else if (temp.startsWith("@")) {
            String table = temp.substring(temp.indexOf("][") + 
                2, temp.length() - 1);
            String[][] data = (String[][])null;
            try {
              String col = table.substring(table.indexOf(".") + 1, table.length());
              data = dbopt.executeQueryToStrArr2("select " + 
                  table.substring(0, table.indexOf(".")) + 
                  "_id," + col + " from " + 
                  table.substring(0, table.indexOf(".")) + " order by " + col);
            } catch (Exception exception) {}
            if (data != null)
              for (int i = 0; i < data.length; i++)
                html = String.valueOf(html) + "<option value=\"" + data[i][0] + "\"" + (
                  data[i][0].equals(fieldValue) ? 
                  " selected " : "") + ">" + 
                  data[i][1] + 
                  "</option>";  
            html = String.valueOf(html) + "</select>";
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
          } else if (temp.startsWith("$")) {
            String dataSourceName = "system";
            if (temp.indexOf("].$[") > 0) {
              dataSourceName = temp.substring(2, temp.indexOf("].$["));
              sql = temp.substring(temp.indexOf("].$[") + 4, temp.length() - 1);
            } else {
              sql = temp.substring(2, temp.length() - 1);
            } 
            String submitPerson = request.getParameter("submitPerson");
            if (submitPerson == null || "null".equals(submitPerson))
              submitPerson = request.getSession().getAttribute("userName").toString(); 
            String sql = sql.replaceAll("@@submitPerson@@", submitPerson);
            sql = InitWorkFlowData.getValueFromRequest(request, sql);
            String[][] data = (String[][])null;
            DbOpt dbo = null;
            try {
              if (!"system".equals(dataSourceName)) {
                dbo = new DbOpt(dataSourceName);
                String dbType = SystemCommon.getUserDatabaseType(dataSourceName);
                if (dbType.indexOf("oracle") >= 0) {
                  String lang = SystemCommon.getUserDatabaseLang(dataSourceName);
                  if (!"".equals(lang))
                    dbo.executeUpdate("ALTER SESSION SET NLS_LANGUAGE='" + lang + "'"); 
                } 
                data = dbo.executeQueryToStrArr2(sql);
                dbo.close();
              } else if (sql.length() > 0 && sql.indexOf("@$@selValue@$@") < 0) {
                data = dbopt.executeQueryToStrArr2(sql);
              } 
            } catch (Exception e3) {
              if (dbo != null)
                try {
                  dbo.close();
                } catch (SQLException e) {
                  e.printStackTrace();
                }  
              e3.printStackTrace();
            } 
            if (data != null)
              for (int i = 0; i < data.length; i++)
                html = String.valueOf(html) + "<option value=\"" + data[i][0] + "\"" + (
                  data[i][0].equals(fieldValue) ? 
                  " selected " : "") + ">" + 
                  data[i][1] + 
                  "</option>";  
            html = String.valueOf(html) + "</select>";
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
          } else {
            for (int i = 0; i < tempArr.length; i++) {
              if (tempArr[i] != null && 
                tempArr[i].trim().length() > 0 && 
                tempArr[i].indexOf("/") >= 0 && 
                tempArr[i].indexOf("/") < 
                tempArr[i].length() - 1)
                html = String.valueOf(html) + "<option value=\"" + 
                  tempArr[i].split("/")[0] + "\"" + (
                  
                  tempArr[i].split("/")[0].equals(
                    fieldValue) ? 
                  " selected " : "") + ">" + 
                  tempArr[i].split("/")[1] + 
                  "</option>"; 
            } 
            html = String.valueOf(html) + "</select>";
            html = "if(document.getElementById('" + 
              fieldTemp[0][0] + "-" + field + 
              "'))\n{document.getElementById('" + 
              fieldTemp[0][0] + "-" + field + 
              "').innerHTML='" + type + 
              "';document.getElementById('" + 
              fieldTemp[0][0] + "-" + field + 
              "').innerHTML+='" + html + ((
              fieldTemp[0][3].equals("1") && hideField.indexOf("," + fieldTemp[0][0] + ",") < 0 && hideField.indexOf(fieldTemp[0][5]) < 0) ? (
              "<input type=hidden name=mustWrite id=mustWrite value=" + 
              field + 
              "><label class=mustFillcolor>*</label>") : 
              "") + "';}";
          } 
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
  
  private String[][] fieldIsFromOther(DbOpt dbopt, String tableId, String fieldId, String fieldName, String fieldValue, String recordId, String pageId, HttpServletRequest request) {
    String[][] selectedValue = (String[][])null;
    try {
      String[][] fieldPara = dbopt.executeQueryToStrArr2("select field_name,field_fetchsql,field_toffield from tfield where field_table=" + tableId + " and field_toffield like '%" + fieldName + "=:=%'", 3);
      if (fieldPara != null && fieldPara.length > 0) {
        String mainFieldName = fieldPara[0][0];
        String mainFieldFetchSql = fieldPara[0][1];
        String mainToFField = fieldPara[0][2];
        String submitPerson = request.getParameter("submitPerson");
        if (submitPerson == null || "null".equals(submitPerson))
          submitPerson = request.getSession().getAttribute("userName").toString(); 
        if (mainToFField != null && !"".equals(mainToFField)) {
          String mainValue = getValue(mainFieldName, recordId, pageId, dbopt);
          String[] mainToFFieldArr = mainToFField.split(";;;;");
          for (int i = 0; i < mainToFFieldArr.length; i++) {
            if (mainToFFieldArr[i].indexOf(String.valueOf(fieldName) + "=:=") >= 0) {
              String fetchField = mainToFFieldArr[i].substring(mainToFFieldArr[i].indexOf("=:=") + 3);
              if (fetchField.startsWith("[")) {
                fetchField = fetchField.substring(1, fetchField.length() - 1);
                fetchField = fetchField.replaceAll("\\@\\$\\@selValue\\@\\$\\@", mainValue);
                fetchField = fetchField.replaceAll("\\@\\$\\@submitPerson\\@\\$\\@", submitPerson);
                selectedValue = dbopt.executeQueryToStrArr2(fetchField, 2);
              } 
              break;
            } 
          } 
        } 
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return selectedValue;
  }
  
  public static String getValue(String field, String infoId, String pageId, DbOpt dbopt) {
    String result = null;
    String type = "1000000";
    try {
      String table = dbopt.executeQueryToStr(
          "select AREA_TABLE from tarea where AREA_NAME='form1' and PAGE_ID=" + pageId);
      if (field != null && field.trim().length() >= 1 && table != null && 
        table.trim().length() >= 1 && infoId != null && 
        infoId.trim().length() >= 1) {
        result = dbopt.executeQueryToStr("select " + field + " from " + 
            table + " where " + table + "_ID=" + 
            infoId);
        type = dbopt.executeQueryToStr("select field_type from tfield,ttable where field_name='" + field + "' and field_table=table_id and table_name='" + table + "'");
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    try {
      if ("1000001".equals(type))
        result = String.valueOf(Double.parseDouble(result)); 
    } catch (Exception exception) {}
    if (result.toUpperCase().contains("<IMG"))
      result = result.replace("&quot;", ""); 
    return result;
  }
  
  public String getForeignEditHTMLForWeiXin(HttpServletRequest request, String field, String fieldValue, String[][] fieldTemp, String hideField, int index, String seq, String fieldId, String fromDraft, String foreignTableName, String parentRecordId, String curRecordId, String isTotalField, String computeFieldStr, boolean isHide, String type, boolean isNum, int maxlen, String temp, String[] tempArr) {
    String html = "";
    DbOpt dbopt = new DbOpt();
    try {
      html = "<select  style=width:" + (fieldTemp[index][3].equals("1") ? "92" : "100") + "%;font-size:1em; id=" + field + " name=" + field;
      if (!isHide)
        html = String.valueOf(html) + " onchange=\"" + field + "_onchange(this);\""; 
      html = String.valueOf(html) + "><option value=\"\">==请选择==</option>";
      String[][] selectedValue = fieldIsFromOtherForeign(dbopt, foreignTableName, fieldId, fieldTemp[index][5], fieldValue, parentRecordId, curRecordId);
      if (selectedValue != null) {
        if (isHide) {
          html = "";
          for (int i = 0; i < selectedValue.length; i++) {
            if (fieldValue.equals(selectedValue[i][0])) {
              html = "<input type=hidden name=" + field + " id=" + field + " value=\"" + 
                selectedValue[i][0] + "\">";
              html = String.valueOf(html) + selectedValue[i][1];
            } 
          } 
          html = "if(document.getElementsByName('" + 
            fieldTemp[index][0] + "-" + field + 
            "')[" + seq + "])\n{document.getElementsByName('" + 
            fieldTemp[index][0] + "-" + field + 
            "')[" + seq + "].innerHTML='" + type + html + "';}";
        } else {
          for (int i = 0; i < selectedValue.length; i++)
            html = String.valueOf(html) + "<option value=\"" + selectedValue[i][0] + "\"" + (
              selectedValue[i][0].equals(fieldValue) ? 
              " selected " : "") + ">" + 
              selectedValue[i][1] + 
              "</option>"; 
          html = String.valueOf(html) + "</select>";
          html = "if(document.getElementsByName('" + 
            fieldTemp[index][0] + "-" + 
            field + 
            "'))\n{document.getElementsByName('" + 
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
      } else {
        temp = fieldTemp[index][6];
        if (temp != null && temp.trim().length() >= 1) {
          if (temp.startsWith("*")) {
            String parentId = temp.substring(temp.indexOf(".*[") + 3, temp.length() - 1);
            temp = (new BaseSetEJBBean()).getValue(parentId);
          } 
          tempArr = temp.split(";");
          if (isHide) {
            type = String.valueOf(type) + "<input type=hidden name=" + field + " id=" + field + " value=" + fieldValue + ">";
            html = "";
            if (temp.startsWith("@")) {
              String table = temp.substring(temp.indexOf("][") + 2, temp.length() - 1);
              String[][] data = (String[][])null;
              try {
                data = dbopt.executeQueryToStrArr2("select " + table.substring(0, table.indexOf(".")) + 
                    "_id," + table.substring(table.indexOf(".") + 1, table.length()) + 
                    " from " + table.substring(0, table.indexOf(".")));
              } catch (Exception exception) {}
              if (data != null)
                for (int i = 0; i < data.length; i++) {
                  if (fieldValue.indexOf(data[i][0]) >= 0) {
                    html = String.valueOf(html) + data[i][1];
                    break;
                  } 
                }  
            } else if (temp.startsWith("$")) {
              String dataSourceName = "system";
              if (temp.indexOf("].$[") > 0) {
                dataSourceName = temp.substring(2, temp.indexOf("].$["));
                sql = temp.substring(temp.indexOf("].$[") + 4, temp.length() - 1);
              } else {
                sql = temp.substring(2, temp.length() - 1);
              } 
              if ("qingdaojinwang".equals(SystemCommon.getCustomerName()))
                sql = sql.split(" where ")[0]; 
              String sql = InitWorkFlowData.getValueFromRequest(request, sql);
              String[][] data = (String[][])null;
              DbOpt dbo = null;
              try {
                if (!"system".equals(dataSourceName)) {
                  dbo = new DbOpt(dataSourceName);
                  String dbType = SystemCommon.getUserDatabaseType(dataSourceName);
                  if (dbType.indexOf("oracle") >= 0) {
                    String lang = SystemCommon.getUserDatabaseLang(dataSourceName);
                    if (!"".equals(lang))
                      dbo.executeUpdate("ALTER SESSION SET NLS_LANGUAGE='" + lang + "'"); 
                  } 
                  data = dbo.executeQueryToStrArr2(sql);
                  dbo.close();
                } else {
                  data = dbopt.executeQueryToStrArr2(sql);
                } 
              } catch (Exception e3) {
                if (dbo != null)
                  try {
                    dbo.close();
                  } catch (SQLException e) {
                    e.printStackTrace();
                  }  
                e3.printStackTrace();
              } 
              if (data != null)
                for (int i = 0; i < data.length; i++) {
                  if (fieldValue.equals(data[i][0])) {
                    html = String.valueOf(html) + data[i][1];
                    break;
                  } 
                }  
            } else {
              for (int i = 0; i < tempArr.length; i++) {
                if (tempArr[i] != null && tempArr[i].trim().length() > 0 && tempArr[i].indexOf("/") >= 0 && 
                  tempArr[i].indexOf("/") < tempArr[i].length() - 1 && 
                  tempArr[i].split("/")[0].equals(fieldValue)) {
                  html = tempArr[i].split("/")[1];
                  break;
                } 
              } 
            } 
            html = "if(document.getElementsByName('" + fieldTemp[index][0] + "-" + field + "')[" + seq + "])\n{document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML='" + type + html + "';}";
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
            } catch (Exception e3) {
              e3.printStackTrace();
            } 
            if (data != null)
              for (int i = 0; i < data.length; i++)
                html = String.valueOf(html) + "<option value=\"" + data[i][0] + "\"" + (
                  data[i][0].equals(fieldValue) ? 
                  " selected " : "") + ">" + 
                  data[i][1] + 
                  "</option>";  
            html = String.valueOf(html) + "</select>";
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
          } else if (temp.startsWith("$")) {
            String dataSourceName = "system";
            if (temp.indexOf("].$[") > 0) {
              dataSourceName = temp.substring(2, temp.indexOf("].$["));
              sql = temp.substring(temp.indexOf("].$[") + 4, temp.length() - 1);
            } else {
              sql = temp.substring(2, temp.length() - 1);
            } 
            String sql = InitWorkFlowData.getValueFromRequest(request, sql);
            String[][] data = (String[][])null;
            DbOpt dbo = null;
            try {
              if (!"system".equals(dataSourceName)) {
                dbo = new DbOpt(dataSourceName);
                String dbType = SystemCommon.getUserDatabaseType(dataSourceName);
                if (dbType.indexOf("oracle") >= 0) {
                  String lang = SystemCommon.getUserDatabaseLang(dataSourceName);
                  if (!"".equals(lang))
                    dbo.executeUpdate("ALTER SESSION SET NLS_LANGUAGE='" + lang + "'"); 
                } 
                data = dbo.executeQueryToStrArr2(sql);
                dbo.close();
              } else {
                data = dbopt.executeQueryToStrArr2(sql);
              } 
            } catch (Exception e3) {
              if (dbo != null)
                try {
                  dbo.close();
                } catch (SQLException e) {
                  e.printStackTrace();
                }  
              e3.printStackTrace();
            } 
            if (data != null)
              for (int i = 0; i < data.length; i++)
                html = String.valueOf(html) + "<option value=\"" + data[i][0] + "\"" + (
                  data[i][0].equals(fieldValue) ? 
                  " selected " : "") + ">" + 
                  data[i][1] + 
                  "</option>";  
            html = String.valueOf(html) + "</select>";
            html = "if(document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "'))\n{document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "].innerHTML='" + type + 
              "';document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + "')[" + seq + "].innerHTML+='" + html + (
              fieldTemp[0][3].equals("1") ? (
              "<input type=hidden name=mustWrite id=mustWrite value=" + 
              field + 
              "><label class=mustFillcolor>*</label>") : 
              "") + "';}";
          } else {
            for (int i = 0; i < tempArr.length; i++) {
              if (tempArr[i] != null && 
                tempArr[i].trim().length() > 0 && 
                tempArr[i].indexOf("/") >= 0 && 
                tempArr[i].indexOf("/") < 
                tempArr[i].length() - 1)
                html = String.valueOf(html) + "<option value=\"" + 
                  tempArr[i].split("/")[0] + "\"" + (
                  
                  tempArr[i].split("/")[0].equals(
                    fieldValue) ? 
                  " selected " : "") + ">" + 
                  tempArr[i].split("/")[1] + 
                  "</option>"; 
            } 
            html = String.valueOf(html) + "</select>";
            html = "if(document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "])\n{document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "].innerHTML='" + type + 
              "';document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "].innerHTML+='" + html + (
              fieldTemp[index][3].equals("1") ? (
              "<input type=hidden name=mustWrite id=mustWrite value=" + 
              field + 
              "><label class=mustFillcolor>*</label>") : 
              "") + "';}";
          } 
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
  
  private String[][] fieldIsFromOtherForeign(DbOpt dbopt, String tableName, String fieldId, String fieldName, String fieldValue, String parentRecordId, String curRecordId) {
    String[][] selectedValue = (String[][])null;
    try {
      String tableId = dbopt.executeQueryToStr("select table_id from ttable where table_name='" + tableName + "'");
      String[][] fieldPara = dbopt.executeQueryToStrArr2("select field_name,field_fetchsql,field_toffield from tfield where field_table=" + tableId + " and field_toffield like '%" + fieldName + "=:=%'", 3);
      if (fieldPara != null && fieldPara.length > 0) {
        String mainFieldName = fieldPara[0][0];
        String mainFieldFetchSql = fieldPara[0][1];
        String mainToFField = fieldPara[0][2];
        if (mainToFField != null && !"".equals(mainToFField)) {
          String mainValue = dbopt.executeQueryToStr("select " + mainFieldName + " from " + tableName + " where " + tableName + "_id=" + curRecordId);
          String[] mainToFFieldArr = mainToFField.split(";;;;");
          for (int i = 0; i < mainToFFieldArr.length; i++) {
            if (mainToFFieldArr[i].indexOf(String.valueOf(fieldName) + "=:=") >= 0) {
              String fetchField = mainToFFieldArr[i].substring(mainToFFieldArr[i].indexOf("=:=") + 3);
              if (fetchField.startsWith("[")) {
                fetchField = fetchField.substring(1, fetchField.length() - 1);
                fetchField = fetchField.replaceAll("\\@\\$\\@selValue\\@\\$\\@", mainValue);
                selectedValue = dbopt.executeQueryToStrArr2(fetchField, 2);
              } 
              break;
            } 
          } 
        } 
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return selectedValue;
  }
}
