package com.js.oa.online.bean;

import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;

public class OnlinerEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public List getAllOnliner() throws Exception {
    begin();
    String hql = "select distinct onlineUser.userId ,onlineUser.userName,onlineUser.orgName,onlineUser.userId,onlineUser.dutyName  from SecurityOnlineuser onlineUser";
    List onlinerList = null;
    try {
      onlinerList = this.session.createQuery(hql).list();
    } catch (HibernateException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
      this.session.close();
    } 
    if (onlinerList == null)
      onlinerList = new ArrayList(); 
    this.session.close();
    return onlinerList;
  }
  
  public String getAllOnlinerRang(String orgId, String curOrgId) throws Exception {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String orgIdString = "";
    String orgIdStr = "0";
    String sql = "";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      if (!orgId.equals("0")) {
        if (orgId.startsWith(","))
          orgId = orgId.substring(1); 
        String[] orgIdsTmp = orgId.split(",");
        if (orgIdsTmp.length > 999) {
          int arrCnt = (orgIdsTmp.length % 990 == 0) ? (orgIdsTmp.length / 990) : (orgIdsTmp.length / 990 + 1);
          String[] subArr = new String[arrCnt];
          String tempString = "";
          int i;
          for (i = 0; i < orgIdsTmp.length; i++) {
            if (i % 990 == 0) {
              tempString = String.valueOf(orgIdsTmp[i]) + ",";
            } else if ((i + 1) % 990 == 0) {
              tempString = String.valueOf(tempString) + orgIdsTmp[i];
              subArr[i / 990] = tempString;
            } else {
              tempString = String.valueOf(tempString) + orgIdsTmp[i] + ",";
            } 
          } 
          if (!"".equals(tempString)) {
            if (tempString.endsWith(","))
              tempString = tempString.substring(0, tempString.length() - 1); 
            subArr[arrCnt - 1] = tempString;
          } 
          sql = "SELECT orgIdstring FROM org_organization WHERE ";
          for (i = 0; i < subArr.length; i++) {
            if (i > 0)
              sql = String.valueOf(sql) + " or "; 
            sql = String.valueOf(sql) + " org_Id in (" + subArr[i] + ") ";
          } 
        } else {
          sql = "SELECT orgIdstring FROM org_organization WHERE org_Id in (" + orgId + ") ";
        } 
        rs = stmt.executeQuery(sql);
        if (rs != null) {
          while (rs.next())
            orgIdString = String.valueOf(orgIdString) + rs.getString(1); 
          String[] orgIds = orgIdString.split("\\$");
          for (int i = 1; i < orgIds.length; i += 2)
            orgIdStr = String.valueOf(orgIdStr) + "," + orgIds[i]; 
        } 
      } 
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return orgIdStr;
  }
  
  public List getByName(String name) throws Exception {
    List listName = null;
    begin();
    String hql = "select distinct onlineUser.userId ,onlineUser.userName,onlineUser.orgName,onlineUser.userId,onlineUser.dutyName from SecurityOnlineuser as onlineUser where onlineUser.userName like '%" + name + "%'";
    try {
      listName = this.session.createQuery(hql).list();
    } catch (HibernateException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
      this.session.close();
    } 
    if (listName == null)
      listName = new ArrayList(); 
    this.session.close();
    return listName;
  }
  
  public List getByOrg(String orgName) throws Exception {
    List listOrg = null;
    try {
      begin();
      String hql = "select distinct onlineUser.userId ,onlineUser.userName,onlineUser.orgName,onlineUser.userId,onlineUser.dutyName from SecurityOnlineuser as onlineUser where onlineUser.orgName like '%" + orgName + "%'";
      listOrg = this.session.createQuery(hql).list();
    } catch (HibernateException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
      this.session.close();
    } 
    if (listOrg == null)
      listOrg = new ArrayList(); 
    this.session.close();
    return listOrg;
  }
  
  public String getName(String userID) throws Exception {
    String username = "";
    try {
      begin();
      List list = this.session.createQuery("select user.empName from com.js.system.vo.usermanager.EmployeeVO user where user.empId='" + userID + "'").list();
      if (list.size() > 0)
        username = String.valueOf(list.get(0)); 
      this.session.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      this.session.close();
    } 
    return username;
  }
  
  public String getNames(String userIDs) throws Exception {
    String username = "";
    try {
      begin();
      List<Object> list = this.session.createQuery("select user.empName from com.js.system.vo.usermanager.EmployeeVO user where user.empId in ( " + userIDs + ")").list();
      if (list.size() > 0)
        for (Object obj : list)
          username = String.valueOf(username) + String.valueOf(obj) + ",";  
      if (!"".equals(username))
        username = username.substring(0, username.length() - 1); 
      this.session.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      this.session.close();
    } 
    return username;
  }
  
  public List getID(String username) throws Exception {
    List list = null;
    try {
      begin();
      String hql = "select user.empId from com.js.system.vo.usermanager.EmployeeVO user where user.empName like '%" + username + "%' ";
      list = this.session.createQuery(hql).list();
      if (list.size() <= 0)
        list = new ArrayList(); 
      this.session.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      this.session.close();
    } 
    return list;
  }
  
  public List getAllOnlinerId() throws Exception {
    begin();
    String hql = "select distinct onlineuser.userId from SecurityOnlineuser onlineuser";
    List onlinerList = null;
    try {
      onlinerList = this.session.createQuery(hql).list();
    } catch (HibernateException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
      this.session.close();
    } 
    if (onlinerList == null)
      onlinerList = new ArrayList(); 
    this.session.close();
    return onlinerList;
  }
  
  public String getOnlinerById(long userid) throws Exception {
    String online = "N";
    begin();
    String hql = "from SecurityOnlineuser onlineuser where onlineuser.userId = " + userid;
    List onlinerList = null;
    try {
      onlinerList = this.session.createQuery(hql).list();
      if (!onlinerList.isEmpty())
        online = "Y"; 
      this.session.close();
    } catch (HibernateException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
      this.session.close();
    } 
    return online;
  }
}
