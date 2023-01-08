package com.js.oa.eform.weixin.getterImpl;

import com.js.oa.eform.weixin.ElementHTMLGetter;
import com.js.oa.jsflow.util.InitWorkFlowData;
import com.js.oa.userdb.bean.BaseSetEJBBean;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ElementHTMLGetterImpl_103 implements ElementHTMLGetter {
  public String getHTML(String field, String noWriteField, String domainId, String fieldId, HttpServletRequest request, String computeFieldStr, String[][] fieldTemp, int maxlen, String type, boolean isNum) {
    String html = "";
    DbOpt dbopt = null;
    dbopt = new DbOpt();
    String mustField = "";
    if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") < 0 && noWriteField.indexOf(fieldTemp[0][5]) < 0 && fieldTemp[0][3].equals("1")) {
      mustField = String.valueOf(mustField) + "&nbsp;<input type=hidden name=mustWrite id=mustWrite value=" + field + "><label class=mustFillcolor>*</label>";
    } else {
      fieldTemp[0][3].equals("1");
    } 
    String temp = fieldTemp[0][6];
    if (temp == null || temp.trim().length() < 1)
      return ""; 
    if (temp.startsWith("@")) {
      String table = temp.substring(temp.indexOf("][") + 2, temp.length() - 1);
      String[][] data = (String[][])null;
      try {
        data = dbopt.executeQueryToStrArr2("select " + table.substring(0, table.indexOf(".")) + 
            "_id," + table.substring(table.indexOf(".") + 1, table.length()) + " from " + table.substring(0, table.indexOf(".")));
      } catch (Exception exception) {}
      if (data != null)
        for (int i = 0; i < data.length; i++) {
          if (noWriteField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || noWriteField.indexOf(fieldTemp[0][5]) >= 0) {
            html = String.valueOf(html) + "var " + field + "_temp='<input type=radio style=font-size:1em; id=" + 
              field + " name=" + field + " value=" + data[i][0] + " disabled>';\n";
          } else {
            html = String.valueOf(html) + "var " + field + "_temp='<input type=radio style=font-size:1em; id=" + 
              field + " name=" + field + " value=" + data[i][0] + ((i == 0) ? " checked " : "") + ">';\n";
          } 
          html = String.valueOf(html) + "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + 
            "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + field + 
            "').innerHTML+=" + field + "_temp;\ndocument.getElementById('" + fieldTemp[0][0] + "-" + field + 
            "').innerHTML+='" + data[i][1] + ((i == data.length - 1) ? mustField : "") + "';}";
        }  
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
          if (noWriteField.indexOf(fieldTemp[0][0]) >= 0 || noWriteField.indexOf(fieldTemp[0][5]) >= 0) {
            html = String.valueOf(html) + "var " + field + "_temp='<input type=radio style=font-size:1em; id=" + 
              field + " name=" + field + " value=" + data[i][0] + " disabled>';\n";
          } else {
            html = String.valueOf(html) + "var " + field + "_temp='<input type=radio style=font-size:1em; id=" + 
              field + " name=" + field + " value=" + data[i][0] + ((i == 0) ? " checked " : "") + ">';\n";
          } 
          html = String.valueOf(html) + "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + 
            "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + field + 
            "').innerHTML+=" + field + "_temp;\ndocument.getElementById('" + fieldTemp[0][0] + "-" + field + 
            "').innerHTML+='" + data[i][1] + ((i == data.length - 1) ? mustField : "") + "';}";
        }  
    } else if (temp.startsWith("*")) {
      String parentId = temp.substring(temp.indexOf(".*[") + 3, temp.length() - 1);
      String[] tempArr = (new BaseSetEJBBean()).getValue(parentId).split(";");
      for (int i = 0; i < tempArr.length; i++) {
        if (tempArr[i] != null && tempArr[i].trim().length() > 0 && 
          tempArr[i].indexOf("/") > 0 && tempArr[i].indexOf("/") < tempArr[i].length() - 1) {
          if (noWriteField.indexOf(fieldTemp[0][0]) >= 0 || noWriteField.indexOf(fieldTemp[0][5]) >= 0) {
            html = String.valueOf(html) + "var " + field + "_temp='<input type=radio style=font-size:1em; id=" + 
              field + " name=" + field + " value=" + tempArr[i].split("/")[0] + " disabled>';\n";
          } else {
            html = String.valueOf(html) + "var " + field + "_temp='<input type=radio style=font-size:1em; id=" + 
              field + " name=" + field + " value=" + tempArr[i].split("/")[0] + ((i == 0) ? " checked " : "") + ">';\n";
          } 
          html = String.valueOf(html) + "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + 
            "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + field + "').innerHTML+=" + field + "_temp;\ndocument.getElementById('" + 
            fieldTemp[0][0] + "-" + field + "').innerHTML+='" + tempArr[i].split("/")[1] + ((i == tempArr.length - 1) ? mustField : "") + "';}";
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
        if (tempArr[i] != null && tempArr[i].trim().length() > 0 && 
          tempArr[i].indexOf("/") > 0 && tempArr[i].indexOf("/") < tempArr[i].length() - 1) {
          if (noWriteField.indexOf(fieldTemp[0][0]) >= 0 || noWriteField.indexOf(fieldTemp[0][5]) >= 0) {
            html = String.valueOf(html) + "var " + field + "_temp='<input type=radio style=font-size:1em; id=" + 
              field + " name=" + field + " value=" + tempArr[i].split("/")[0] + " disabled>';\n";
          } else {
            html = String.valueOf(html) + "var " + field + "_temp='<input type=radio style=font-size:1em; id=" + 
              field + " name=" + field + " value=" + tempArr[i].split("/")[0] + ((i == 0) ? " checked " : "") + ">';\n";
          } 
          html = String.valueOf(html) + "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + 
            "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + field + "').innerHTML+=" + field + "_temp;\ndocument.getElementById('" + 
            fieldTemp[0][0] + "-" + field + "').innerHTML+='" + tempArr[i].split("/")[1] + ((i == tempArr.length - 1) ? mustField : "") + "';}";
        } 
      } 
    } 
    html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + field + "').innerHTML='" + type + "';}" + html;
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
    html = "";
    try {
      temp = fieldTemp[0][6];
      if (temp != null && temp.trim().length() >= 1) {
        if (temp.startsWith("*")) {
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
            temp = InitWorkFlowData.getKeyValue(temp, map, request);
          } else {
            temp = InitWorkFlowData.getShowContext(temp, fieldValue);
          } 
        } 
        tempArr = temp.split(";");
        if (hideField != null && (
          hideField.indexOf("," + fieldTemp[0][0] + ",") >= 0 || 
          hideField.indexOf(fieldTemp[0][5]) >= 0)) {
          type = "";
          if (temp.startsWith("@")) {
            String table = temp.substring(temp.indexOf("][") + 2, temp.length() - 1);
            String[][] data = (String[][])null;
            try {
              data = dbopt.executeQueryToStrArr2("select " + table.substring(0, table.indexOf(".")) + "_id," + 
                  table.substring(table.indexOf(".") + 1, table.length()) + " from " + table.substring(0, table.indexOf(".")));
            } catch (Exception exception) {}
            if (data != null) {
              html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + "'))\n{\ndocument.getElementById('" + 
                fieldTemp[0][0] + "-" + field + "').innerHTML='" + type + "';}";
              for (int i = 0; i < data.length; i++) {
                html = String.valueOf(html) + "var " + field + String.valueOf(i) + "_temp='<input type=radio style=font-size:1em; disabled id=" + 
                  field + " name=" + field + " value=" + data[i][0] + (data[i][0].equals(fieldValue) ? " checked " : "") + ">';\n";
                html = String.valueOf(html) + "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + field + 
                  "').innerHTML+=" + field + String.valueOf(i) + "_temp;\ndocument.getElementById('" + 
                  fieldTemp[0][0] + "-" + field + "').innerHTML+='" + data[i][1] + "';}";
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
            if (data != null) {
              html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + "'))\n{\ndocument.getElementById('" + 
                fieldTemp[0][0] + "-" + field + "').innerHTML='" + type + "';}";
              for (int i = 0; i < data.length; i++) {
                html = String.valueOf(html) + "var " + field + String.valueOf(i) + "_temp='<input type=radio style=font-size:1em; disabled id=" + 
                  field + " name=" + field + " value=" + data[i][0] + (data[i][0].equals(fieldValue) ? " checked " : "") + ">';\n";
                html = String.valueOf(html) + "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + field + 
                  "').innerHTML+=" + field + String.valueOf(i) + "_temp;\ndocument.getElementById('" + 
                  fieldTemp[0][0] + "-" + field + "').innerHTML+='" + data[i][1] + "';}";
              } 
            } 
          } else {
            for (int i = 0; i < tempArr.length; i++) {
              if (tempArr[i] != null && 
                tempArr[i].trim().length() > 0 && 
                tempArr[i].indexOf("/") > 0 && 
                tempArr[i].indexOf("/") < 
                tempArr[i].length() - 1) {
                html = String.valueOf(html) + "var " + field + String.valueOf(i) + "_temp='<input type=radio  style=font-size:1em; disabled id=" + 
                  field + " name=" + field + " value=" + tempArr[i].split("/")[0] + (tempArr[i].split("/")[0].equals(fieldValue) ? " checked " : "") + ">';\n";
                html = String.valueOf(html) + "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + field + "').innerHTML+=" + field + String.valueOf(i) + 
                  "_temp;\ndocument.getElementById('" + fieldTemp[0][0] + "-" + field + "').innerHTML+='" + tempArr[i].split("/")[1] + 
                  "';}";
              } 
            } 
            html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + "'))\n{document.getElementById('" + fieldTemp[0][0] + "-" + field + "').innerHTML='" + type + "';}" + html;
          } 
        } else if (temp.startsWith("@")) {
          String table = temp.substring(temp.indexOf("][") + 2, temp.length() - 1);
          String[][] data = (String[][])null;
          try {
            data = dbopt.executeQueryToStrArr2("select " + table.substring(0, table.indexOf(".")) + "_id," + table.substring(table.indexOf(".") + 1, 
                  table.length()) + " from " + table.substring(0, table.indexOf(".")));
          } catch (Exception e3) {
            e3.printStackTrace();
          } 
          if (data != null) {
            html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + "'))\n{\ndocument.getElementById('" + 
              fieldTemp[0][0] + "-" + field + "').innerHTML='" + type + "';}";
            for (int i = 0; i < data.length; i++) {
              html = String.valueOf(html) + "var " + field + String.valueOf(i) + "_temp='<input type=radio style=font-size:1em;  id=" + field + " name=" + field + (data[i][0].equals(fieldValue) ? " checked " : "") + " value=" + data[i][0] + ">';\n";
              html = String.valueOf(html) + "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + "'))\n{document.getElementById('" + 
                fieldTemp[0][0] + "-" + field + "').innerHTML+=" + field + String.valueOf(i) + "_temp;\ndocument.getElementById('" + 
                fieldTemp[0][0] + "-" + field + "').innerHTML+='" + data[i][1] + ((fieldTemp[0][3].equals("1") && i == data.length - 1 && 
                hideField.indexOf("," + fieldTemp[0][0] + ",") < 0 && hideField.indexOf(fieldTemp[0][5]) < 0) ? (
                "<input type=hidden name=mustWrite id=mustWrite value=" + field + "><label class=mustFillcolor>*</label>") : "") + "';}";
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
          if (data != null) {
            html = "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + "'))\n{\ndocument.getElementById('" + 
              fieldTemp[0][0] + "-" + field + "').innerHTML='" + type + "';}";
            for (int i = 0; i < data.length; i++) {
              html = String.valueOf(html) + "var " + field + String.valueOf(i) + "_temp='<input type=radio style=font-size:1em; id=" + field + " name=" + field + (data[i][0].equals(fieldValue) ? " checked " : "") + " value=" + data[i][0] + ">';\n";
              html = String.valueOf(html) + "if(document.getElementById('" + fieldTemp[0][0] + "-" + field + "'))\n{document.getElementById('" + 
                fieldTemp[0][0] + "-" + field + "').innerHTML+=" + field + String.valueOf(i) + "_temp;\ndocument.getElementById('" + 
                fieldTemp[0][0] + "-" + field + "').innerHTML+='" + data[i][1] + ((fieldTemp[0][3].equals("1") && i == data.length - 1 && 
                hideField.indexOf("," + fieldTemp[0][0] + ",") < 0 && hideField.indexOf(fieldTemp[0][5]) < 0) ? (
                "<input type=hidden name=mustWrite id=mustWrite value=" + field + "><label class=mustFillcolor>*</label>") : "") + "';}";
            } 
          } 
        } else {
          for (int i = 0; i < tempArr.length; i++) {
            if (tempArr[i] != null && 
              tempArr[i].trim().length() > 0 && 
              tempArr[i].indexOf("/") > 0 && 
              tempArr[i].indexOf("/") < 
              tempArr[i].length() - 1) {
              html = String.valueOf(html) + "var " + field + String.valueOf(i) + "_temp='<input style=font-size:1em; type=radio id=" + field + " name=" + field + " value=" + 
                tempArr[i].split("/")[0] + (tempArr[i].split("/")[0].equals(fieldValue) ? " checked " : "") + ">';\n";
              html = String.valueOf(html) + "if(document.getElementById('" + 
                fieldTemp[0][0] + 
                "-" + field + 
                "'))\n{document.getElementById('" + 
                fieldTemp[0][0] + "-" + field + 
                "').innerHTML+=" + 
                field + String.valueOf(i) + 
                "_temp;\ndocument.getElementById('" + 
                fieldTemp[0][0] + "-" + field + 
                "').innerHTML+='" + 
                tempArr[i].split("/")[1] + ((
                fieldTemp[0][3].equals("1") && 
                i == tempArr.length - 1 && hideField.indexOf("," + fieldTemp[0][0] + ",") < 0 && hideField.indexOf(fieldTemp[0][5]) < 0) ? (
                "<input type=hidden name=mustWrite id=mustWrite value=" + 
                field + 
                "><label class=mustFillcolor>*</label>") : 
                "") + "';}";
            } 
          } 
          html = "if(document.getElementById('" + 
            fieldTemp[0][0] + "-" + field + 
            "'))\n{document.getElementById('" + 
            fieldTemp[0][0] + "-" + field + 
            "').innerHTML='" + type + "';}" + html;
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
      temp = fieldTemp[index][6];
      if (temp != null && temp.trim().length() >= 1) {
        tempArr = temp.split(";");
        if (isHide) {
          type = String.valueOf(type) + "<input type=hidden name=" + field + "id=" + field + 
            " value=" + fieldValue + ">";
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
              for (int i = 0; i < data.length; i++) {
                html = String.valueOf(html) + "var " + field + 
                  String.valueOf(i) + 
                  "_temp='<input type=radio style=font-size:1em; disabled id=" + 
                  field + " name=" + field + 
                  " id=" + field + 
                  " value=" + data[i][0] + 
                  ">';\n";
                html = String.valueOf(html) + 
                  "if(document.getElementsByName('" + 
                  fieldTemp[index][0] + "-" + 
                  field + "')[" + seq + 
                  "])\n{document.getElementsByName('" + 
                  fieldTemp[index][0] + "-" + 
                  field + "')[" + seq + 
                  "].innerHTML+=" + field + 
                  String.valueOf(i) + 
                  "_temp;\ndocument.getElementsByName('" + 
                  fieldTemp[index][0] + "-" + 
                  field + "')[" + seq + 
                  "].innerHTML+='" + data[i][1] + "';}";
              }  
          } else {
            for (int i = 0; i < tempArr.length; i++) {
              if (tempArr[i] != null && 
                tempArr[i].trim().length() > 0 && 
                tempArr[i].indexOf("/") > 0 && 
                tempArr[i].indexOf("/") < 
                tempArr[i].length() - 1) {
                html = String.valueOf(html) + "var " + field + 
                  String.valueOf(i) + 
                  "_temp='<input type=radio style=font-size:1em; disabled id=" + 
                  field + " name=" + field + 
                  " value=" + 
                  tempArr[i].split("/")[0] + (
                  
                  tempArr[i].split("/")[0].equals(fieldValue) ? 
                  " checked " : "") + ">';\n";
                html = String.valueOf(html) + 
                  "if(document.getElementsByName('" + 
                  fieldTemp[index][0] + "-" + 
                  field + 
                  "')[" + seq + 
                  "])\n{document.getElementsByName('" + 
                  fieldTemp[index][0] + "-" + 
                  field + 
                  "')[" + seq + "].innerHTML+=" + 
                  field + String.valueOf(i) + 
                  "_temp;\ndocument.getElementsByName('" + 
                  fieldTemp[index][0] + "-" + 
                  field + "')[" + seq + 
                  "].innerHTML+='" + 
                  tempArr[i].split("/")[1] + "';}";
              } 
            } 
            html = "if(document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + 
              "])\n{document.getElementsByName('" + 
              fieldTemp[index][0] + "-" + field + 
              "')[" + seq + "].innerHTML='" + type + 
              "';}" + html;
          } 
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
          if (data != null)
            for (int i = 0; i < data.length; i++) {
              html = String.valueOf(html) + "var " + field + String.valueOf(i) + 
                "_temp='<input type=radio style=font-size:1em; id=" + 
                field + String.valueOf((Integer.parseInt(seq) > 0) ? String.valueOf(Integer.parseInt(seq) + 1) : "") + " name=" + field + 
                String.valueOf((Integer.parseInt(seq) > 0) ? String.valueOf(Integer.parseInt(seq) + 1) : "") + 
                " value=" + data[i][0] + ">';\n";
              html = String.valueOf(html) + "if(document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + field + 
                "')[" + seq + 
                "])\n{document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + field + 
                "')[" + seq + "].innerHTML+=" + 
                field + String.valueOf(i) + 
                "_temp;\ndocument.getElementsByName('" + 
                fieldTemp[index][0] + "-" + field + 
                "')[" + seq + "].innerHTML+='" + 
                data[i][1] + ((
                fieldTemp[index][3].equals("1") && 
                i == data.length - 1) ? (
                "<input type=hidden name=mustWrite id=mustWrite value=" + 
                field + String.valueOf((Integer.parseInt(seq) > 0) ? String.valueOf(Integer.parseInt(seq) + 1) : "") + 
                "><label class=mustFillcolor>*</label>") : 
                "") + "';}";
            }  
        } else {
          for (int i = 0; i < tempArr.length; i++) {
            if (tempArr[i] != null && 
              tempArr[i].trim().length() > 0 && 
              tempArr[i].indexOf("/") > 0 && 
              tempArr[i].indexOf("/") < 
              tempArr[i].length() - 1) {
              html = String.valueOf(html) + "var " + field + String.valueOf(i) + 
                "_temp='<input type=radio style=font-size:1em; id=" + 
                field + String.valueOf((Integer.parseInt(seq) > 0) ? String.valueOf(Integer.parseInt(seq) + 1) : "") + " name=" + 
                field + String.valueOf((Integer.parseInt(seq) > 0) ? String.valueOf(Integer.parseInt(seq) + 1) : "") + " value=" + 
                tempArr[i].split("/")[0] + (
                tempArr[i].split("/")[0].equals(
                  fieldValue) ? " checked " : "") + 
                ">';\n";
              html = String.valueOf(html) + "if(document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + field + 
                "')[" + seq + 
                "])\n{document.getElementsByName('" + 
                fieldTemp[index][0] + "-" + field + 
                "')[" + seq + "].innerHTML+=" + 
                field + String.valueOf(i) + 
                "_temp;\ndocument.getElementsByName('" + 
                fieldTemp[index][0] + "-" + field + 
                "')[" + seq + "].innerHTML+='" + 
                tempArr[i].split("/")[1] + ((
                fieldTemp[index][3].equals("1") && 
                i == tempArr.length - 1) ? (
                "<input type=hidden name=mustWrite id=mustWrite value=" + 
                field + String.valueOf((Integer.parseInt(seq) > 0) ? String.valueOf(Integer.parseInt(seq) + 1) : "") + 
                "><label class=mustFillcolor>*</label>") : 
                "") + "';}";
            } 
          } 
          html = "if(document.getElementsByName('" + 
            fieldTemp[index][0] + "-" + field + 
            "')[" + seq + 
            "])\n{document.getElementsByName('" + 
            fieldTemp[index][0] + "-" + field + 
            "')[" + seq + "].innerHTML='" + type + "';}" + 
            html;
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
