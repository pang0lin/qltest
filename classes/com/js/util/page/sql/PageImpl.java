package com.js.util.page.sql;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PageImpl {
  private int recordCount = 0;
  
  public List getResultList(String para, String PO, String where, int pageSize, int currentPage) {
    return getResultList(para, PO, where, pageSize, currentPage, "");
  }
  
  public List getResultList(String para, String PO, String where, int pageSize, int currentPage, String dataSourceName) {
    int paraCount = (para.split(",")).length;
    List<String[]> list = new ArrayList();
    if (currentPage <= 0)
      currentPage = 1; 
    int beginRecord = pageSize * (currentPage - 1);
    StringBuffer queryBuffer = new StringBuffer(768);
    StringBuffer queryCount = new StringBuffer(768);
    queryBuffer.append("SELECT ");
    queryBuffer.append(para);
    queryBuffer.append(" FROM ");
    queryBuffer.append(PO);
    queryBuffer.append(" ");
    queryBuffer.append(where);
    Connection conn = null;
    try {
      String lang = "";
      String dbType = SystemCommon.getDatabaseType();
      if ("".equals(dataSourceName)) {
        conn = (new DataSourceBase()).getDataSource().getConnection();
      } else {
        conn = (new DataSourceBase()).getDataSource(dataSourceName).getConnection();
        dbType = SystemCommon.getUserDatabaseType(dataSourceName);
        lang = SystemCommon.getUserDatabaseLang(dataSourceName);
      } 
      Statement stmt = conn.createStatement();
      if (!"".equals(lang) && dbType.indexOf("oracle") >= 0)
        stmt.executeUpdate("ALTER SESSION SET NLS_LANGUAGE='" + lang + "'"); 
      if (dbType.indexOf("oracle") >= 0) {
        String temp = queryBuffer.toString();
        queryCount.append("select count(*) from (").append(temp).append(")");
        queryBuffer = new StringBuffer("select * from (");
        queryBuffer.append("select row_.*,rownum rownum1 from (").append(temp);
        queryBuffer.append(") row_ where rownum<=").append(beginRecord + pageSize).append(")");
        queryBuffer.append(" where rownum1>").append(beginRecord);
      } else if (dbType.indexOf("mysql") >= 0) {
        String temp = queryBuffer.toString();
        queryCount.append("select count(*) from (").append(temp).append(") _TempTable");
        queryBuffer.append(" limit ").append(beginRecord).append(",").append(pageSize);
      } else if (dbType.indexOf("sqlserver") >= 0) {
        String temp = queryBuffer.toString();
        String countTemp = temp;
        int index = temp.toLowerCase().indexOf("order by");
        if (index > 0)
          countTemp = temp.substring(0, index); 
        queryCount.append("select count(*) from (").append(countTemp).append(") _TempTable");
        queryBuffer = new StringBuffer("select top " + (beginRecord + pageSize) + " a.* from (");
        temp = "select top " + (beginRecord + pageSize) + temp.substring(7);
        queryBuffer.append(temp).append(") a");
      } 
      ResultSet rs = stmt.executeQuery(queryCount.toString());
      if (rs.next())
        this.recordCount = rs.getInt(1); 
      rs.close();
      setRecordCount(this.recordCount);
      rs = stmt.executeQuery(queryBuffer.toString());
      if (dbType.indexOf("sqlserver") >= 0)
        for (int j = 0; j < beginRecord; j++)
          rs.next();  
      int i = 0;
      while (rs.next()) {
        String[] obj = new String[paraCount];
        for (i = 0; i < obj.length; i++) {
          String temp = rs.getString(i + 1);
          if (temp == null)
            temp = ""; 
          obj[i] = temp;
        } 
        list.add(obj);
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      list = null;
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return list;
  }
  
  public Iterator getResultPO(String para, String PO, String where, int pageSize, int currentPage) {
    return null;
  }
  
  public int getRecordCount() {
    return this.recordCount;
  }
  
  public void setRecordCount(int recordCount) {
    this.recordCount = recordCount;
  }
}
