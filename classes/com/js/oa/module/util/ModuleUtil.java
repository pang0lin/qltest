package com.js.oa.module.util;

import com.js.oa.userdb.util.DbOpt;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ModuleUtil {
  public String[] moduleInfo(String menuId) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String[] result = new String[3];
    try {
      base.begin();
      String sql = "SELECT MENU_LISTTBLMAPPING,MENU_LISTTBLNAME,MENU_REFFLOW,menu_searchbound FROM MENU_EXT WHERE id=" + menuId;
      rs = base.executeQuery(sql);
      if (rs.next()) {
        result[0] = rs.getString(4);
        if (rs.getString(3).indexOf("\\$") >= 0)
          result[1] = rs.getString(3).split("\\$")[0]; 
        result[2] = rs.getString(1);
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return result;
  }
  
  public String[][] moduleData(String sql, int num) {
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
