package com.js.oa.online.service;

import com.js.oa.online.bean.OnlinerEJBBean;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class OnlineDB extends HibernateBase {
  OnlinerEJBBean onlinerEJBBean = new OnlinerEJBBean();
  
  public List getAllOnliner() throws Exception {
    List list = this.onlinerEJBBean.getAllOnliner();
    return list;
  }
  
  public List getByName(String name) throws Exception {
    return this.onlinerEJBBean.getByName(name);
  }
  
  public List getByOrg(String orgName) throws Exception {
    return this.onlinerEJBBean.getByOrg(orgName);
  }
  
  public String getName(String userID) throws Exception {
    return this.onlinerEJBBean.getName(userID);
  }
  
  public String getNames(String userIDs) throws Exception {
    return this.onlinerEJBBean.getNames(userIDs);
  }
  
  public List getAllOnlinerId() throws Exception {
    List list = this.onlinerEJBBean.getAllOnlinerId();
    return list;
  }
  
  public String getOnlinerById(long userid) throws Exception {
    String online = "N";
    online = this.onlinerEJBBean.getOnlinerById(userid);
    return online;
  }
  
  public int getOnlineUserNum(String domainId, String sessionId) {
    int userNum = 0;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      stmt = conn.createStatement();
      int temp = -1;
      rs = stmt.executeQuery("select count(*) from sec_onlineuser where session_id='" + sessionId + "' and ishelper=0");
      if (rs.next())
        temp = rs.getInt(1); 
      rs.close();
      if (temp == 0) {
        userNum = -2;
      } else {
        rs = stmt.executeQuery("select  count(distinct onlineuser.USER_ID) from sec_onlineuser onlineuser where domain_id=" + domainId);
        if (rs.next())
          userNum = rs.getInt(1); 
        rs.close();
      } 
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
      e.printStackTrace();
    } 
    return userNum;
  }
}
