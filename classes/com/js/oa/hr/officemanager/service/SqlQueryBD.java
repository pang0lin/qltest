package com.js.oa.hr.officemanager.service;

import com.js.oa.hr.officemanager.bean.SqlQueryEJBBean;
import org.apache.log4j.Logger;

public class SqlQueryBD {
  private static Logger logger = Logger.getLogger(SqlQueryBD.class.getName());
  
  public String[][] query(String sql) throws Exception {
    String[][] list = (String[][])null;
    try {
      list = (new SqlQueryEJBBean()).query(sql);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return list;
  }
}
