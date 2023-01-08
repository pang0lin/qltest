package com.js.oa.online;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

public class OnlineUser {
  private static Map onlineUserNum = null;
  
  private static OnlineUser onlineUser;
  
  public static synchronized OnlineUser getInstance() {
    if (onlineUser == null)
      onlineUser = new OnlineUser(); 
    return onlineUser;
  }
  
  private OnlineUser() {
    onlineUserNum = new HashMap<Object, Object>();
    DataSource ds = null;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String serverIP = "";
    try {
      serverIP = InetAddress.getLocalHost().getHostAddress();
    } catch (Exception exception) {}
    try {
      ds = (new DataSourceBase()).getDataSource();
      conn = ds.getConnection();
      stmt = conn.createStatement();
      if ("".equals(serverIP)) {
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("oracle") < 0)
          databaseType.indexOf("sqlserver"); 
      } 
      rs = stmt.executeQuery("select domain_id,count(*) from sec_onlineuser group by domain_id");
      while (rs.next())
        onlineUserNum.put(rs.getString(1), rs.getString(2)); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }  
      System.out.println("初始化在线用户数:" + ex);
    } 
  }
  
  public int getOnlineUserNum(String domainId) {
    if (onlineUserNum.get(domainId) != null)
      return Integer.parseInt(onlineUserNum.get(domainId).toString()); 
    onlineUserNum.put(domainId, "1");
    return 1;
  }
  
  public void setOnlineUserNum(int onlineUserNum, String domainId) {
    OnlineUser.onlineUserNum.put(domainId, String.valueOf(onlineUserNum));
  }
  
  public boolean userCanLog(String userId, String userIP) {
    boolean result = false;
    DataSourceBase dsb = new DataSourceBase();
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = dsb.getDataSource().getConnection();
      stmt = conn.createStatement();
      String sql = "select user_id,user_ip from sec_onlineuser where user_id=" + userId;
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next()) {
        String dataIP = rs.getString(2);
        if (userIP != null) {
          if (userIP.equals(rs.getString(2))) {
            result = true;
          } else {
            result = false;
          } 
        } else {
          result = false;
        } 
      } else {
        result = true;
      } 
      rs.close();
      stmt.close();
    } catch (Exception e) {
      result = false;
      e.printStackTrace();
    } finally {
      try {
        conn.close();
      } catch (Exception exception) {}
    } 
    return result;
  }
}
