package com.buguniao;

import com.js.util.util.DataSourceBase;
import com.js.util.util.IO2File;
import com.js.util.util.MD5;
import com.js.util.util.ReadActiveXml;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.UUID;
import javax.sql.DataSource;

public class TransBuguniao {
  public void Sync() {
    System.out.println("**************Dept sync begin*****************");
    Connection conn = null;
    Connection conn2 = null;
    Statement stmt = null;
    Statement stmt2 = null;
    ResultSet allrs = null;
    DataSource ds = (new DataSourceBase()).getDataSource();
    try {
      conn = ds.getConnection();
      conn2 = (new ServerConn()).getConnection();
      stmt = conn.createStatement();
      stmt2 = conn2.createStatement();
      String WorkGroupId = "";
      String PWorkGroupId = "";
      String WorkGroupName = "";
      String WorkGroupRemark = "";
      int SortNum = -1;
      int WorkGroupIsShow = 1;
      allrs = stmt.executeQuery("SELECT ORG_ID,ORGPARENTORGID,ORGNAME,ORGLEVEL FROM org_organization WHERE ORGPARENTORGID<>-1 and orgstatus=0 ORDER BY ORGLEVEL,ORGIDSTRING");
      int i = 0;
      while (allrs.next()) {
        WorkGroupId = allrs.getString(1);
        PWorkGroupId = allrs.getString(2);
        PWorkGroupId = "0".equals(PWorkGroupId) ? "1" : PWorkGroupId;
        WorkGroupName = allrs.getString(3);
        SortNum = allrs.getInt(4);
        if (!searchID(WorkGroupId, "workgroup", "WorkGroupId")) {
          stmt2.execute("insert into workgroup (WorkGroupId,PWorkGroupId,WorkGroupName,WorkGroupRemark,SortNum,WorkGroupIsShow) value('" + 
              WorkGroupId + "','" + PWorkGroupId + "','" + WorkGroupName + "','" + WorkGroupRemark + "','" + i + "','" + WorkGroupIsShow + "')");
        } else {
          stmt2.executeUpdate("update  workgroup set PWorkGroupId='" + PWorkGroupId + "',WorkGroupName='" + WorkGroupName + "',WorkGroupRemark='" + WorkGroupRemark + "',SortNum='" + i + "',WorkGroupIsShow=" + WorkGroupIsShow + 
              " where  WorkGroupId='" + WorkGroupId + "'");
        } 
        SortNum = -1;
        WorkGroupIsShow = 1;
        i++;
      } 
      String sql = "select * from workgroup";
      ResultSet allrs1 = stmt2.executeQuery(sql);
      Statement stmt3 = conn2.createStatement();
      while (allrs1.next()) {
        String orgId = allrs1.getString("workgroupid");
        sql = "select 1 FROM org_organization WHERE ORGPARENTORGID<>-1 and orgstatus<>4 and org_id='" + orgId + "'";
        if (!stmt.executeQuery(sql).next())
          stmt3.executeUpdate("delete from workgroup where workgroupid = '" + orgId + "'"); 
      } 
      allrs1.close();
      allrs.close();
      stmt.close();
      stmt2.close();
      stmt3.close();
      conn.close();
      conn2.close();
    } catch (SQLException e) {
      try {
        if (conn2 != null)
          conn2.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException ex) {
        ex.printStackTrace();
      } 
      e.printStackTrace();
    } 
    System.out.println("**************Dept syn end*****************");
  }
  
  public void transUser() {
    System.out.println("**************User syn begin*****************");
    Connection conn = null;
    Connection conn2 = null;
    Statement stmt = null;
    Statement stmt2 = null;
    ResultSet rs = null;
    String UserId = "";
    String UserNo = "";
    String UserPass = "";
    String UserTName = "";
    String UserDetail = "";
    String userTel = "";
    String UserMobile = "";
    String UserEmail = "";
    String UserIcon = "";
    int UserSex = 0;
    String UserZW = "";
    int UserAge = 0;
    String UserRemark = "";
    String UserAddress = "";
    String UserWebSite = "";
    String UserRole = "users";
    int UserLevel = 5;
    int UserState = 1;
    String workgroupid = "";
    String sql = null;
    try {
      long wId = (new Date()).getTime();
      sql = "SELECT useraccounts , useraccounts username ,userpassword , empname ,empmobilephone ,empemail,empdescribe,empsex,empduty,empaddress,org_id,org.emp_id,empbusinessphone FROM org_employee org,org_organization_user oo WHERE org.emp_id=oo.emp_id and oo.emp_id>0 AND userisactive=1 and userisdeleted=0 AND (useraccounts <> '' or useraccounts is not null)";
      conn = (new DataSourceBase()).getDataSource().getConnection();
      conn2 = (new ServerConn()).getConnection();
      stmt = conn.createStatement();
      stmt2 = conn2.createStatement();
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        wId++;
        UserId = rs.getString(1);
        if (UserId == null || "".equals(UserId))
          continue; 
        UserNo = rs.getString(2);
        UserPass = rs.getString(3);
        UserPass = (new MD5()).getOldMD5Code(UserPass).toLowerCase();
        UserTName = rs.getString(4);
        UserMobile = rs.getString(5);
        UserEmail = rs.getString(6);
        UserRemark = rs.getString(7);
        UserSex = rs.getInt(8) + 1;
        UserZW = rs.getString(9);
        UserAddress = rs.getString(10);
        workgroupid = rs.getString(11);
        userTel = rs.getString(13);
        if (UserMobile == null || "null".equals(UserMobile))
          UserMobile = ""; 
        if (UserEmail == null || "null".equals(UserEmail))
          UserEmail = ""; 
        if (UserRemark == null || "null".equals(UserRemark))
          UserRemark = ""; 
        if (UserZW == null || "null".equals(UserZW))
          UserZW = ""; 
        if (UserAddress == null || "null".equals(UserAddress))
          UserAddress = ""; 
        if (userTel == null || "null".equals(userTel))
          userTel = ""; 
        if (!searchID(UserId, "userlist", "UserId")) {
          stmt2.execute("insert into  userlist (UserId,UserNo,UserPass,UserTName,UserDetail,userTel,UserMobile,UserEmail,UserSex,UserZW,UserAge,UserRemark,UserAddress,UserWebSite,UserRole,UserLevel,UserState) value('" + 
              
              UserNo + "','" + UserNo + "','" + UserPass + "','" + UserTName + "','" + UserDetail + "','" + userTel + "','" + UserMobile + "','" + UserEmail + "','" + UserSex + "','" + UserZW + "','" + UserAge + "','" + UserRemark + "','" + UserAddress + "','" + UserWebSite + "','" + UserRole + "','" + UserLevel + "','" + UserState + "')");
          stmt2.execute("insert into workgroupuserlist (wid,workgroupid,userno) value('" + UUID.randomUUID() + "','" + workgroupid + "','" + UserNo + "')");
          continue;
        } 
        stmt2.executeUpdate("update userlist set UserNo='" + 
            UserNo + "',UserPass='" + UserPass + "',UserTName='" + UserTName + "',UserDetail='" + UserDetail + "',userTel='" + userTel + "',UserMobile='" + UserMobile + "',UserEmail='" + UserEmail + "',UserIcon='" + UserIcon + "',UserSex='" + UserSex + "',UserZW='" + UserZW + "',UserAge='" + UserAge + "',UserRemark='" + UserRemark + "',UserAddress='" + UserAddress + "',UserWebSite='" + UserWebSite + "',UserRole='" + UserRole + "',UserLevel='" + UserLevel + "',UserState='" + UserState + 
            "' where UserId='" + UserId + "'");
        stmt2.executeUpdate("update workgroupuserlist set workgroupid='" + workgroupid + "',userno='" + UserId + "' where userno='" + UserId + "'");
      } 
      sql = "select * from userlist";
      ResultSet rs1 = stmt2.executeQuery(sql);
      Statement stmt3 = conn2.createStatement();
      while (rs1.next()) {
        String userId = rs1.getString("UserId");
        sql = "select 1 from org_employee where useraccounts='" + userId + "' AND userisactive=1 and userisdeleted=0 AND (useraccounts <>'' OR useraccounts IS not NULL) and useraccounts <> 'audit-admin'";
        if (!stmt.executeQuery(sql).next())
          stmt3.executeUpdate("delete from userlist where UserId='" + userId + "'"); 
      } 
      rs1.close();
      rs.close();
      stmt.close();
      stmt2.close();
      stmt3.close();
      conn.close();
      conn2.close();
    } catch (SQLException e) {
      try {
        if (conn2 != null)
          conn2.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException ex) {
        ex.printStackTrace();
      } 
      e.printStackTrace();
    } 
    System.out.println("**************User syn End*****************");
  }
  
  public void updateUserPass(String password, String useraccounts, String oldAccounts) {
    String usd = ReadActiveXml.getReadActive().getUse();
    if ("jsim".equals(usd) && password != null && !password.equals("")) {
      password = (new MD5()).getOldMD5Code(password).toLowerCase();
      String updateSql = "update userlist set UserPass='" + password + "',UserId='" + useraccounts + "' where UserId='" + oldAccounts + "'";
      IO2File.printFile("同步布谷鸟密码：" + updateSql);
      Connection conn = null;
      Statement stmt = null;
      try {
        conn = (new ServerConn()).getConnection();
        stmt = conn.createStatement();
        stmt.executeUpdate(updateSql);
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        try {
          if (conn != null)
            conn.close(); 
          if (stmt != null)
            stmt.close(); 
        } catch (SQLException e) {
          e.printStackTrace();
        } 
      } 
    } 
  }
  
  public static boolean searchID(String id, String tbname, String sid) {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String sql = null;
    boolean re = false;
    sql = "select count(*) from " + tbname + " where " + sid + "='" + id + "'";
    try {
      conn = (new ServerConn()).getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next() && 
        rs.getInt(1) > 0)
        re = true; 
      rs.close();
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return re;
  }
}
