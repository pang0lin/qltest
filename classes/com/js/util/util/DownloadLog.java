package com.js.util.util;

import com.js.oa.security.log.service.LogBD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

public class DownloadLog {
  public void getDownLoadLog(HttpServletRequest HSR, String nameShow, String moduleCode) {
    Date beginDate = new Date();
    LogBD logBD = new LogBD();
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String module_log = "";
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select module_log from sec_log_module where module_level=0 and module_serial='fujian_downlod'  ");
      while (rs.next())
        module_log = rs.getString("module_log"); 
      stmt.close();
      conn.close();
      rs.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }  
    } 
    String tableBegin = "下载文件名为 【";
    String tableEnd = "】的文件";
    String tableNameValue = String.valueOf(tableBegin) + nameShow + tableEnd;
    String tableName = tableNameValue;
    Date endDate = new Date();
    if ("1".equals(module_log))
      logBD.log(HSR.getSession(true).getAttribute("userId").toString(), HSR.getSession(true).getAttribute("userName").toString(), HSR.getSession(true).getAttribute("orgName").toString(), moduleCode, "", beginDate, endDate, "8", tableName, HSR.getRemoteAddr(), HSR.getSession(true).getAttribute("domainId").toString()); 
  }
}
