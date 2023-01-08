package com.js.oa.zky.bean;

import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceUtil;
import java.sql.ResultSet;
import java.util.List;

public class ZkyRankBean {
  public List<String[]> getScoreList(String sql) {
    return (new DataSourceUtil()).getListQuery(sql, "");
  }
  
  public int getScoreNum(String sql) {
    DataSourceBase base = new DataSourceBase();
    int num = 0;
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      if (rs.next())
        num = rs.getInt(1); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return num;
  }
  
  public void executeUpdate(String sql) {
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      base.executeUpdate(sql);
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
  }
}
