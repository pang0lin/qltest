package com.js.util.page.util;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.IO2File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

public class PageSqlUtil extends PageUtil {
  public PageSqlUtil() {
    setEveryNum(15);
  }
  
  public PageSqlUtil(int everyPageNum) {
    setEveryNum(everyPageNum);
  }
  
  protected void getData(String select, String from, String where, String orderBy, int curPage) {
    this.curPageNum = (curPage <= 0) ? 1 : curPage;
    DataSourceBase base = new DataSourceBase();
    String baseType = SystemCommon.getDatabaseType();
    this.list = new ArrayList();
    String sql = "select " + select + " from " + from + " where " + (where.equals("") ? "1=1" : where);
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      while (rs.next())
        this.allItem++; 
      rs.close();
      sql = "select " + select + " from " + from + " where " + (where.equals("") ? "1=1" : where) + ("".equals(orderBy) ? "" : (" order by " + orderBy));
      if ("mysql".equals(baseType)) {
        sql = String.valueOf(sql) + " limit " + ((curPage - 1) * this.everyNum) + "," + this.everyNum;
      } else {
        sql = "SELECT * FROM ( SELECT A.*, ROWNUM RN FROM (" + sql + ") A ) WHERE RN BETWEEN " + ((
          this.curPageNum - 1) * this.everyNum + 1) + 
          
          " AND " + (this.curPageNum * this.everyNum);
      } 
      IO2File.printFile("分页sql:" + sql);
      rs = base.executeQuery(sql);
      ResultSetMetaData rsmd = rs.getMetaData();
      int columnCount = rsmd.getColumnCount();
      while (rs.next()) {
        String[] arrayOfString = new String[columnCount];
        for (int i = 0; i < columnCount; i++)
          arrayOfString[i] = (rs.getString(i + 1) == null) ? "" : rs.getString(i + 1); 
        this.list.add(arrayOfString);
      } 
      rs.close();
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    getPageDate();
  }
}
