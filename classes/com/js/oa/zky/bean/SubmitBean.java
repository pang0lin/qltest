package com.js.oa.zky.bean;

import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceUtil;
import java.sql.ResultSet;
import java.util.List;

public class SubmitBean {
  public String getTableName(String pageId) {
    String sql = "select area_table from tarea where page_id=" + pageId + " and area_name='form1'";
    DataSourceBase base = new DataSourceBase();
    String tableName = "";
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      if (rs.next())
        tableName = rs.getString(1); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return tableName;
  }
  
  public void updateMqzt(String[] tableName, String mqzt, String nd) {
    updateMqzt(tableName, mqzt, nd, "");
  }
  
  public void updateMqzt(String[] tableName, String mqzt, String nd, String id) {
    String upMqzt = (new StringBuilder(String.valueOf(Integer.valueOf(mqzt).intValue() + 1))).toString();
    String sql = "update " + tableName[0] + "_" + tableName[1] + " set " + tableName[1] + "_mqzt='" + upMqzt + "'," + tableName[1] + "_jxzt='已提交' where " + 
      "  " + tableName[1] + "_mqzt='" + mqzt + "'";
    if (!id.equals(""))
      sql = String.valueOf(sql) + " and " + tableName[0] + "_" + tableName[1] + "_id in (" + id + ")"; 
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      base.executeSQL(sql);
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
  }
  
  public void backMqzt(String[] tableName, String mqzt, String nd) {
    backMqzt(tableName, mqzt, nd, "");
  }
  
  public void backMqzt(String[] tableName, String mqzt, String nd, String id) {
    String upMqzt = (new StringBuilder(String.valueOf(Integer.valueOf(mqzt).intValue() - 1))).toString();
    String sql = "update " + tableName[0] + "_" + tableName[1] + " set " + tableName[1] + "_mqzt='" + upMqzt + "'," + tableName[1] + "_jxzt='已退回' where " + 
      "  " + tableName[1] + "_mqzt='" + mqzt + "'";
    if (!id.equals("")) {
      if (id.endsWith(","))
        id = id.substring(0, id.length() - 1); 
      sql = String.valueOf(sql) + " and " + tableName[0] + "_" + tableName[1] + "_id in (" + id + ")";
    } 
    System.out.println("----" + sql);
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      base.executeSQL(sql);
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
  }
  
  public List<String> getTableNameList() {
    String sql = "select menu_listtblName from menu_ext where menu_iszky=1";
    return (new DataSourceUtil()).getQuery(sql, "");
  }
}
