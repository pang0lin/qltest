package com.js.oa.oacollect.util;

import com.js.oa.userdb.util.DbOpt;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CollectUtil {
  public String[] collectInfo(String collectId) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String[] result = new String[2];
    try {
      base.begin();
      String sql = "SELECT collect_tpage,collect_tProcess FROM oa_collect WHERE collect_id=" + collectId;
      rs = base.executeQuery(sql);
      if (rs.next()) {
        result[0] = rs.getString(1);
        result[1] = rs.getString(2);
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return result;
  }
  
  public String[][] collectData(String sql, int num) {
    String[][] list = (String[][])null;
    DbOpt dbopt = null;
    List tlist = null;
    try {
      dbopt = new DbOpt();
      list = dbopt.executeQueryToStrArr2(sql, num);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return list;
  }
}
