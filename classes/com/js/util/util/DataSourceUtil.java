package com.js.util.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class DataSourceUtil {
  public List<String[]> getListQuery(String sql) {
    return getListQuery(sql, "null");
  }
  
  public List<String[]> getListQuery(String sql, String ifnullStr) {
    DataSourceBase base = new DataSourceBase();
    List<String[]> list = (List)new ArrayList<String>();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      ResultSetMetaData rsmd = rs.getMetaData();
      int columnCount = rsmd.getColumnCount();
      while (rs.next()) {
        String[] rsValue = new String[columnCount];
        for (int i = 0; i < columnCount; i++)
          rsValue[i] = (rs.getString(i + 1) == null) ? ifnullStr : rs.getString(i + 1); 
        list.add(rsValue);
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return list;
  }
  
  public List<String> getQuery(String sql, String ifnullStr) {
    DataSourceBase base = new DataSourceBase();
    List<String> list = new ArrayList<String>();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      while (rs.next())
        list.add((rs.getString(1) == null) ? ifnullStr : rs.getString(1)); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return list;
  }
  
  public void executeSql(String sql) {
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      base.executeSqlUpdate(sql);
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
  }
  
  public void executeSql(List<String> sqlList) {
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      for (int i = 0; i < sqlList.size(); i++)
        base.addBatch(sqlList.get(i)); 
      base.executeBatch();
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
  }
  
  public int getQueryCount(String sql) {
    DataSourceBase base = new DataSourceBase();
    int count = 0;
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      while (rs.next())
        count = rs.getInt(1); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return count;
  }
}
