package com.js.oa.jsflow.util;

import com.js.oa.userdb.bean.BaseSetEJBBean;
import com.js.util.util.DataSourceBase;
import com.js.util.util.IO2File;
import com.js.util.util.eval.NewEval;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataToOtherBase {
  protected DataSourceBase base = new DataSourceBase();
  
  protected ResultSet rs = null;
  
  protected Connection conn = null;
  
  protected Statement stat = null;
  
  protected String processId;
  
  protected String nodeId;
  
  protected String recordId;
  
  public void setProcessId(String processId) {
    this.processId = processId;
  }
  
  public void setNodeId(String nodeId) {
    this.nodeId = nodeId;
  }
  
  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }
  
  public DataToOtherBase(String processId, String nodeId, String recordId) {
    this.processId = processId;
    this.nodeId = nodeId;
    this.recordId = recordId;
  }
  
  public DataToOtherBase() {}
  
  public String dataSynchro() throws Exception {
    DataSynchro ds = new DataSynchro();
    Map<String, Object> infoMap = ds.getSynchroInfo(this.processId, this.nodeId);
    if (infoMap.get("executeInfo") != null) {
      String[] executeInfo = (String[])infoMap.get("executeInfo");
      if ("".equals(executeInfo[0]) || !isRun(executeInfo[6], executeInfo[1], this.recordId)) {
        IO2File.printFile("此节点条件不符合数据同步执行条件", "节点同步");
      } else {
        String tableIds = executeInfo[5];
        tableIds = tableIds.replaceAll(",,", ";").replaceAll(",", "");
        String[] tableId = tableIds.split(";");
        for (int i = 0; i < tableId.length; i++) {
          List<String> sql = new ArrayList<String>();
          String[] condInfo = (String[])infoMap.get("cond_" + tableId[i]);
          List<String[]> fieldSynchro = (List<String[]>)infoMap.get("synchro_" + tableId[i]);
          if ("0".equals(condInfo[3])) {
            sql = getSqlInsert(condInfo, fieldSynchro);
          } else {
            sql = getSqlUpdate(condInfo, fieldSynchro);
          } 
          try {
            if ("system".equals(condInfo[1])) {
              this.conn = this.base.getDataSource().getConnection();
            } else {
              this.conn = this.base.getDataSource(condInfo[1]).getConnection();
            } 
            this.stat = this.conn.createStatement();
            for (int t = 0; t < sql.size(); t++) {
              String sqlString = ((String)sql.get(t)).toString().replaceAll("&apos;", "'").replace("&acute", "'");
              IO2File.printFile("执行sql语句：" + sqlString, "节点同步");
              this.stat.executeUpdate(sqlString);
            } 
            this.stat.close();
            this.conn.close();
          } catch (SQLException e) {
            if (this.stat != null)
              this.stat.close(); 
            if (this.conn != null)
              this.conn.close(); 
            IO2File.printStackTrace(e, "节点同步");
          } 
        } 
      } 
    } 
    return "";
  }
  
  public List<String> getSqlInsert(String[] condInfo, List<String[]> fieldSynchro) {
    List<String> sqlList = new ArrayList<String>();
    String sql = "";
    if ("0".equals(condInfo[7])) {
      Map<String, String> valueMap = (Map)getValue(condInfo, fieldSynchro);
      sql = getInsertSql(valueMap, condInfo, fieldSynchro);
      sqlList.add(sql);
    } else {
      List<Map> valueList = (List)getValue(condInfo, fieldSynchro);
      for (int i = 0; i < valueList.size(); i++) {
        Map<String, String> valueMap = valueList.get(i);
        sql = getInsertSql(valueMap, condInfo, fieldSynchro);
        sqlList.add(sql);
      } 
    } 
    return sqlList;
  }
  
  public String getInsertSql(Map<String, String> valueMap, String[] condInfo, List<String[]> fieldSynchro) {
    String sql = "insert into " + condInfo[2] + " (";
    String values = ") values (";
    for (int j = 0; j < fieldSynchro.size(); j++) {
      String[] field = fieldSynchro.get(j);
      if (!"".equals(field[1])) {
        sql = String.valueOf(sql) + field[1] + ",";
        values = String.valueOf(values) + (String)valueMap.get(field[1]) + ",";
      } 
    } 
    return String.valueOf(sql.substring(0, sql.length() - 1)) + values.substring(0, values.length() - 1) + ")";
  }
  
  public List<String> getSqlUpdate(String[] condInfo, List<String[]> fieldSynchro) {
    List<String> sqlList = new ArrayList<String>();
    String sql = "";
    if ("0".equals(condInfo[7])) {
      Map<String, String> valueMap = (Map)getValue(condInfo, fieldSynchro);
      sql = getUpdateSql(valueMap, condInfo, fieldSynchro, this.recordId);
      sqlList.add(sql);
    } else {
      List<Map> valueList = (List)getValue(condInfo, fieldSynchro);
      for (int x = 0; x < valueList.size(); x++) {
        Map<String, String> valueMap = valueList.get(x);
        sql = getUpdateSql(valueMap, condInfo, fieldSynchro, valueMap.get(String.valueOf(condInfo[5]) + "_id"));
        sqlList.add(sql);
      } 
    } 
    return sqlList;
  }
  
  public String getUpdateSql(Map<String, String> valueMap, String[] condInfo, List<String[]> fieldSynchro, String rId) {
    String sql = "update " + condInfo[2] + " set ";
    for (int i = 0; i < fieldSynchro.size(); i++) {
      String[] field = fieldSynchro.get(i);
      if (!"".equals(field[1]) && valueMap.get(field[1]) != null)
        sql = String.valueOf(sql) + field[1] + "=" + (String)valueMap.get(field[1]) + ","; 
    } 
    String cond = condInfo[4];
    if (cond.indexOf("@$@") >= 0) {
      String[][] searchValue = getSearchValue(condInfo[5], cond, rId);
      for (int j = 0; j < searchValue.length; j++) {
        IO2File.printFile("@$@" + searchValue[j][0] + "@$@" + "替换为：" + searchValue[j][1], "节点同步");
        cond = cond.replace("@$@" + searchValue[j][0] + "@$@", searchValue[j][1]);
      } 
    } 
    if (cond.startsWith("where ") || cond.startsWith(" where ")) {
      sql = String.valueOf(sql.substring(0, sql.length() - 1)) + " " + cond;
    } else {
      sql = String.valueOf(sql.substring(0, sql.length() - 1)) + " where " + cond;
    } 
    return sql;
  }
  
  public Object getValue(String[] tableInfo, List<String[]> field) {
    DataSourceBase dsb = new DataSourceBase();
    ResultSet rset = null;
    Map<String, String> notField = new HashMap<String, String>();
    String sql = "select ";
    for (int i = 0; i < field.size(); i++) {
      String[] fieldInfo = field.get(i);
      if (!fieldInfo[2].equals("")) {
        sql = String.valueOf(sql) + fieldInfo[2] + ",";
      } else {
        String value = setValue(fieldInfo, fieldInfo[3], tableInfo, this.recordId);
        notField.put(fieldInfo[1], value);
      } 
    } 
    if ("0".equals(tableInfo[7])) {
      if (sql.endsWith(",")) {
        Map<String, String> valueMap = new HashMap<String, String>();
        if (notField.size() > 0)
          for (String key : notField.keySet())
            valueMap.put(key, notField.get(key));  
        sql = String.valueOf(sql.substring(0, sql.length() - 1)) + " from " + tableInfo[5] + " where " + tableInfo[5] + "_id=" + this.recordId;
        try {
          dsb.begin();
          rset = dsb.executeQuery(sql);
          if (rset.next())
            for (int j = 0; j < field.size(); j++) {
              String[] fieldInfo = field.get(j);
              String getValue = "";
              if (!"".equals(fieldInfo[2]))
                getValue = (rset.getString(fieldInfo[2]) == null) ? "" : rset.getString(fieldInfo[2]); 
              String value = setValue(fieldInfo, getValue, tableInfo, this.recordId);
              valueMap.put(fieldInfo[1], value);
            }  
          dsb.end();
        } catch (Exception e) {
          dsb.end();
          e.printStackTrace();
        } 
        return valueMap;
      } 
    } else {
      List<Map<String, String>> valueList = new ArrayList<Map<String, String>>();
      sql = sql.endsWith(",") ? sql : ("select " + tableInfo[5] + "_id,");
      if (sql.endsWith(",")) {
        sql = String.valueOf(sql.substring(0, sql.length() - 1)) + "," + tableInfo[5] + "_id from " + tableInfo[5] + " where " + tableInfo[5] + "_FOREIGNKEY=" + this.recordId;
        IO2File.printFile("子表取值sql:" + sql, "节点同步");
        try {
          dsb.begin();
          rset = dsb.executeQuery(sql);
          while (rset.next()) {
            Map<String, String> subMap = new HashMap<String, String>();
            if (notField.size() > 0)
              for (String key : notField.keySet())
                subMap.put(key, notField.get(key));  
            for (int j = 0; j < field.size(); j++) {
              String[] fieldInfo = field.get(j);
              String getValue = "";
              if (!"".equals(fieldInfo[2]))
                getValue = (rset.getString(fieldInfo[2]) == null) ? "" : rset.getString(fieldInfo[2]); 
              String value = setValue(fieldInfo, getValue, tableInfo, rset.getString(String.valueOf(tableInfo[5]) + "_id"));
              subMap.put(fieldInfo[1], value);
            } 
            subMap.put(String.valueOf(tableInfo[5]) + "_id", rset.getString(String.valueOf(tableInfo[5]) + "_id"));
            valueList.add(subMap);
          } 
          dsb.end();
        } catch (Exception e) {
          dsb.end();
          e.printStackTrace();
        } 
        return valueList;
      } 
      valueList.add(notField);
      return valueList;
    } 
    return notField;
  }
  
  public String setValue(String[] fieldInfo, String getValue, String[] tableInfo, String recordId) {
    String value = getValue;
    if ("".equals(fieldInfo[3])) {
      if (!fieldInfo[2].equals("")) {
        value = getValue;
        if (getValue.contains("@#@") || (getValue.contains("{@") && getValue.endsWith("}@")) || getValue.contains(";"))
          value = specialField(tableInfo[6], fieldInfo[2], getValue, false); 
      } else {
        value = "";
      } 
    } else {
      if (!fieldInfo[2].equals("")) {
        value = getOtherTableValue(fieldInfo, tableInfo, getValue, "1", recordId);
        if (getValue.contains("@#@") || (getValue.contains("{@") && getValue.endsWith("}@")) || getValue.contains(";"))
          value = specialField(tableInfo[6], fieldInfo[2], getValue, false); 
        if (fieldInfo[3].indexOf("@tranValue@") >= 0)
          if (getValue.contains("@#@")) {
            value = getValue.split("@#@")[1];
          } else {
            value = specialField(tableInfo[6], fieldInfo[2], getValue);
          }  
      } else {
        value = getOtherTableValue(fieldInfo, tableInfo, getValue, "", recordId);
        value = fieldInfo[3].replace("@varchar@", "").replace("@tranValue@", "");
      } 
      value = value.replaceAll("'", "&acute");
      if (fieldInfo[3].indexOf("@varchar@") >= 0 || fieldInfo[3].indexOf("@varchar2@") >= 0)
        value = "'" + value + "'"; 
      if (fieldInfo[3].indexOf("@processId@") >= 0)
        value = fieldInfo[3].replace("@processId@", this.processId); 
      if (fieldInfo[3].indexOf("@recordId@") >= 0)
        value = fieldInfo[3].replace("@recordId@", this.recordId); 
    } 
    if (value.indexOf("@$@") >= 0) {
      String[][] searchValue = getSearchValue(tableInfo[5], value, recordId);
      for (int i = 0; i < searchValue.length; i++)
        value = value.replace("@$@" + searchValue[i][0] + "@$@", searchValue[i][1]); 
    } 
    return value;
  }
  
  public String getOtherTableValue(String[] fieldInfo, String[] tableInfo, String getValue, String flag, String rId) {
    String value = getValue;
    if (fieldInfo[3].indexOf("@begin-@") >= 0 && fieldInfo[3].indexOf("@end-@") >= 0 && 
      fieldInfo[3].indexOf("@begin-@") < fieldInfo[3].indexOf("@end-@")) {
      float number = Float.valueOf(getNumber(fieldInfo[3], tableInfo[5], "@begin-@", "@end-@", rId)).floatValue();
      if ("".equals(flag)) {
        value = (new StringBuilder(String.valueOf(number))).toString();
      } else {
        float f = number - Float.valueOf(getValue).floatValue();
      } 
    } else if (fieldInfo[3].indexOf("@-begin@") >= 0 && fieldInfo[3].indexOf("@-end@") >= 0 && 
      fieldInfo[3].indexOf("@-begin@") < fieldInfo[3].indexOf("@-end@")) {
      float number = Float.valueOf(getNumber(fieldInfo[3], tableInfo[5], "@-begin@", "@-end@", rId)).floatValue();
      if ("".equals(flag)) {
        value = (new StringBuilder(String.valueOf(number))).toString();
      } else {
        float f = Float.valueOf(getValue).floatValue() - number;
      } 
    } else if (fieldInfo[3].indexOf("@begin+@") >= 0 && fieldInfo[3].indexOf("@end+@") >= 0 && 
      fieldInfo[3].indexOf("@begin+@") < fieldInfo[3].indexOf("@end+@")) {
      float number = Float.valueOf(getNumber(fieldInfo[3], tableInfo[5], "@begin+@", "@end+@", rId)).floatValue();
      if ("".equals(flag)) {
        value = (new StringBuilder(String.valueOf(number))).toString();
      } else {
        float f = Float.valueOf(getValue).floatValue() + number;
      } 
    } else if (fieldInfo[3].indexOf("@begin*@") >= 0 && fieldInfo[3].indexOf("@end*@") >= 0 && 
      fieldInfo[3].indexOf("@begin*@") < fieldInfo[3].indexOf("@end*@")) {
      float number = Float.valueOf(getNumber(fieldInfo[3], tableInfo[5], "@begin*@", "@end*@", rId)).floatValue();
      if ("".equals(flag)) {
        value = (new StringBuilder(String.valueOf(number))).toString();
      } else {
        float f = Float.valueOf(getValue).floatValue() * number;
      } 
    } else if (fieldInfo[3].indexOf("@begin/@") >= 0 && fieldInfo[3].indexOf("@end/@") >= 0 && 
      fieldInfo[3].indexOf("@begin/@") < fieldInfo[3].indexOf("@end/@")) {
      float number = Float.valueOf(getNumber(fieldInfo[3], tableInfo[5], "@begin/@", "@end/@", rId)).floatValue();
      if ("".equals(flag)) {
        value = (new StringBuilder(String.valueOf(number))).toString();
      } else if (number == 0.0F) {
        value = "0";
      } else {
        float f = Float.valueOf(getValue).floatValue() / number;
      } 
    } else if (fieldInfo[3].indexOf("@/begin@") >= 0 && fieldInfo[3].indexOf("@/end@") >= 0 && 
      fieldInfo[3].indexOf("@/begin@") < fieldInfo[3].indexOf("@/end@")) {
      float number = Float.valueOf(getNumber(fieldInfo[3], tableInfo[5], "@/begin@", "@/end@", rId)).floatValue();
      if ("".equals(flag)) {
        value = (new StringBuilder(String.valueOf(number))).toString();
      } else if (Float.valueOf(getValue).floatValue() == 0.0F) {
        value = "0";
      } else {
        float f = number / Float.valueOf(getValue).floatValue();
      } 
    } else if (fieldInfo[3].indexOf("@begin@") >= 0 && fieldInfo[3].indexOf("@end@") >= 0 && 
      fieldInfo[3].indexOf("@begin@") < fieldInfo[3].indexOf("@end@")) {
      value = getNumber(fieldInfo[3], tableInfo[5], "@begin@", "@end@", rId);
    } 
    return value;
  }
  
  public boolean isRun(String cond, String record) {
    String tableName = "";
    return isRun(tableName, cond, record);
  }
  
  public boolean isRun(String tableName, String cond, String record) {
    if ("".equals(cond) || "null".equals(cond))
      return true; 
    boolean flag = false;
    try {
      if (cond.indexOf("@$@") >= 0) {
        String[][] searchValue = getSearchValue(tableName, cond, record);
        for (int i = 0; i < searchValue.length; i++)
          cond = cond.replace("@$@" + searchValue[i][0] + "@$@", searchValue[i][1]); 
      } 
      IO2File.printFile("执行条件：" + cond, "节点同步");
      String rValue = NewEval.javaEval(cond);
      if ("true".equalsIgnoreCase(rValue) || "false".equalsIgnoreCase(rValue))
        flag = Boolean.valueOf(rValue).booleanValue(); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return flag;
  }
  
  public String[][] getSearchValue(String tableName, String cond, String record) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    if (cond.endsWith("@$@"))
      cond = String.valueOf(cond) + "jiusi"; 
    String[] teStrings = cond.split("\\@\\$\\@");
    String searchField = "";
    for (int i = 1; i < teStrings.length; i += 2)
      searchField = String.valueOf(searchField) + teStrings[i] + ","; 
    String[] search = searchField.split(",");
    String[][] searchValue = new String[search.length][2];
    try {
      base.begin();
      String sql = "select " + searchField.substring(0, searchField.length() - 1) + " from " + tableName + " where " + tableName + "_id=" + record;
      IO2File.printFile("数据库取值sql：" + sql, "节点同步");
      rs = base.executeQuery(sql);
      if (rs.next())
        for (int x = 0; x < search.length; x++) {
          searchValue[x][0] = search[x];
          searchValue[x][1] = rs.getString(x + 1);
        }  
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return searchValue;
  }
  
  public String getJianShu(String sql) {
    String i = "0";
    DataSourceBase bsd = new DataSourceBase();
    ResultSet rset = null;
    try {
      bsd.begin();
      rset = bsd.executeQuery(sql);
      if (rset.next())
        i = rset.getString(1); 
      bsd.end();
    } catch (Exception e) {
      bsd.end();
      e.printStackTrace();
    } 
    return i;
  }
  
  public String specialFieldByTableName(String tableName, String field, String value) {
    String result = value;
    String sql = "SELECT table_id FROM ttable WHERE table_name='" + tableName + "'";
    DataSourceBase bsd = new DataSourceBase();
    String tableId = "";
    try {
      bsd.begin();
      ResultSet rset = bsd.executeQuery(sql);
      if (rset.next())
        tableId = rset.getString(1); 
      rset.close();
      bsd.end();
      if (!"".equals(tableId))
        result = specialField(tableId, field, value); 
    } catch (Exception e) {
      bsd.end();
      e.printStackTrace();
    } 
    return result;
  }
  
  public String specialField(String tableId, String field, String value) {
    return specialField(tableId, field, value, true);
  }
  
  public String specialField(String tableId, String field, String value, boolean ifzhuanyi) {
    if (!ifzhuanyi) {
      if (value.contains("@#@"))
        return value.split("@#@")[0]; 
      if (value.contains("{@") && value.endsWith("@}"))
        return value.substring(0, value.indexOf("{@")); 
    } 
    String[] fieldValue = new String[3];
    DataSourceBase bsd = new DataSourceBase();
    Connection conn = null;
    Statement stam = null;
    ResultSet rset = null;
    try {
      bsd.begin();
      String sql = "SELECT field_name,field_show,field_value FROM tfield WHERE field_table=" + tableId + " AND field_name='" + field + "'";
      rset = bsd.executeQuery(sql);
      if (rset.next()) {
        if (!ifzhuanyi && ",210,211,212,214,".contains("," + rset.getString("field_show") + ","))
          return value.split(";")[1]; 
        fieldValue[0] = rset.getString("field_name");
        fieldValue[1] = rset.getString("field_show");
        fieldValue[2] = rset.getString("field_value");
      } else {
        return value;
      } 
      rset.close();
      bsd.end();
    } catch (Exception e) {
      bsd.end();
      e.printStackTrace();
      return value;
    } 
    if (ifzhuanyi) {
      if (",103,104,105,210,211,212,214,".indexOf("," + fieldValue[1] + ",") >= 0) {
        String returnValue = "";
        try {
          if (",103,104,105,".indexOf("," + fieldValue[1] + ",") >= 0) {
            if (value.contains("@#@")) {
              returnValue = value.split("@#@")[1];
            } else {
              String sql = "";
              if (fieldValue[2].startsWith("$") || fieldValue[2].startsWith("@")) {
                try {
                  if (fieldValue[2].indexOf("].$[") >= 0) {
                    String database = fieldValue[2].substring(fieldValue[2].indexOf("$[") + 
                        2, fieldValue[2].indexOf("].$["));
                    if ("system".equals(database)) {
                      conn = this.base.getDataSource().getConnection();
                    } else {
                      conn = this.base.getDataSource(database).getConnection();
                    } 
                    sql = fieldValue[2].substring(fieldValue[2].indexOf("].$[") + 4, fieldValue[2].length() - 1);
                  } else {
                    conn = this.base.getDataSource().getConnection();
                    if (fieldValue[2].startsWith("@")) {
                      sql = fieldValue[2].substring(fieldValue[2].indexOf("][") + 2, fieldValue[2].length() - 1);
                      String[] sqls = sql.split("\\.");
                      sql = "select " + sqls[0] + "_id," + sqls[1] + " from " + sqls[0];
                    } else {
                      sql = fieldValue[2].substring(fieldValue[2].indexOf("$[") + 2, fieldValue[2].length() - 1);
                    } 
                  } 
                  stam = conn.createStatement();
                  rset = stam.executeQuery(sql);
                  while (rset.next()) {
                    if ("104".equals(fieldValue[1])) {
                      while (value.startsWith(","))
                        value = value.substring(1); 
                      while (value.endsWith(","))
                        value = value.substring(0, value.length() - 1); 
                      value = ";" + value.replaceAll(",", ";,;") + ";";
                      if (value.indexOf(";" + rset.getString(1) + ";") >= 0)
                        returnValue = value.replaceAll(";" + rset.getString(1) + ";", rset.getString(2)); 
                      continue;
                    } 
                    if (value.equals(rset.getString(1))) {
                      returnValue = rset.getString(2);
                      break;
                    } 
                  } 
                  rset.close();
                  stam.close();
                  conn.close();
                } catch (Exception e) {
                  if (stam != null)
                    stam.close(); 
                  if (conn != null)
                    conn.close(); 
                  e.printStackTrace();
                } 
              } else {
                String[] xiala = (String[])null;
                if (fieldValue[2].startsWith("*")) {
                  String parentId = fieldValue[2].substring(fieldValue[2].indexOf(".*[") + 3, fieldValue[2].length() - 1);
                  xiala = (new BaseSetEJBBean()).getValue(parentId).split(";");
                } else if (fieldValue[2].startsWith("#")) {
                  xiala = InitWorkFlowData.getShowContext(fieldValue[2], value).split(";");
                } else {
                  xiala = fieldValue[2].split(";");
                } 
                for (int i = 0; i < xiala.length; i++) {
                  String[] item = xiala[i].split("/");
                  if ("104".equals(fieldValue[1])) {
                    while (value.startsWith(","))
                      value = value.substring(1); 
                    while (value.endsWith(","))
                      value = value.substring(0, value.length() - 1); 
                    value = ";" + value.replaceAll(",", ";,;") + ";";
                    if (value.indexOf(";" + item[0] + ";") >= 0)
                      returnValue = value.replaceAll(";" + item[0] + ";", item[1]); 
                  } else if (value.equals(item[0])) {
                    returnValue = item[1];
                    break;
                  } 
                } 
              } 
            } 
          } else if (",210,211,212,214,".indexOf("," + fieldValue[1] + ",") >= 0) {
            returnValue = value.split(";")[0];
          } 
          return returnValue;
        } catch (Exception e) {
          e.printStackTrace();
          return value;
        } 
      } 
      if ("199".equals(fieldValue[2]))
        return value.split("`~`~`")[0]; 
      return value;
    } 
    return value;
  }
  
  public String getNumber(String fieldInfo, String tableInfo, String begin, String end, String rId) {
    String xiangjian = fieldInfo.substring(fieldInfo.indexOf(begin) + begin.length(), fieldInfo.indexOf(end));
    String[][] values = getSearchValue(tableInfo, xiangjian, rId);
    for (int i = 0; i < values.length; i++)
      xiangjian = xiangjian.replace("@$@" + values[i][0] + "@$@", values[i][1]); 
    return getJianShu(xiangjian);
  }
}
