package com.js.oa.lcpsoa.client;

import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class LcpsoaClient extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public boolean updateIndexOrg(Object[] obj) {
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
    } catch (SQLException e1) {
      e1.printStackTrace();
      System.out.println("修改部门基本信息时数据库没有获取到Connection！");
    } 
    try {
      stmt = conn.createStatement();
    } catch (SQLException e1) {
      e1.printStackTrace();
      System.out.println("修改部门基本信息时没有获取到Statement！");
    } 
    boolean flag = false;
    try {
      if ("名称".equals(obj[4].toString())) {
        String sql = "update org_organization set orgName='" + obj[6].toString() + "' where org_Id= " + obj[2].toString();
        stmt.executeUpdate(sql);
        flag = true;
      } 
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      flag = false;
      e.printStackTrace();
      System.out.println("修改部门基本信息时出现错误！");
    } 
    return flag;
  }
  
  public boolean deleteIndexOrg(String orgId) {
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
    } catch (SQLException e1) {
      e1.printStackTrace();
      System.out.println("删除部门信息时未能获取到connection！");
    } 
    try {
      stmt = conn.createStatement();
    } catch (SQLException e1) {
      e1.printStackTrace();
      System.out.println("删除部门信息时未能获取相应的Statement");
    } 
    boolean flag = false;
    try {
      String sql = "update org_organization set orgstatus=1   where org_id=" + orgId + " or orgparentorgid=" + orgId + "  ";
      stmt.executeUpdate(sql);
      flag = true;
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      flag = false;
      e.printStackTrace();
      System.out.println("删除部门基本信息时出现错误！");
    } 
    return flag;
  }
  
  public boolean updateIndexUser(Object[] obj) {
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
    } catch (SQLException e1) {
      e1.printStackTrace();
      System.out.println("修改用户基本信息时未能获取相应的connection！");
    } 
    try {
      stmt = conn.createStatement();
    } catch (SQLException e1) {
      e1.printStackTrace();
      System.out.println("修改用户基本信息时未能获取相应的Statement！");
    } 
    boolean flag = false;
    if ("所属部门".equals(obj[4].toString().trim())) {
      String hqlOrg = "select ORG_ID from ORG_ORGANIZATION  where ORGNAME='" + obj[6].toString() + "' ";
      ResultSet rs = null;
      try {
        rs = stmt.executeQuery(hqlOrg.toString());
      } catch (SQLException e) {
        e.printStackTrace();
      } 
      String orgIdString = "";
      try {
        while (rs.next()) {
          try {
            orgIdString = rs.getString(1);
          } catch (SQLException e) {
            e.printStackTrace();
          } 
        } 
      } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("修改用户所属部门时出现异常！");
      } 
      String sql = "update ORG_ORGANIZATION_USER set ORG_ID=" + orgIdString + "  where EMP_ID= " + obj[2].toString();
      if (!"".equals(orgIdString) && orgIdString != null)
        try {
          stmt.executeUpdate(sql);
        } catch (SQLException e) {
          e.printStackTrace();
        }  
    } 
    try {
      StringBuffer sb = new StringBuffer();
      sb.append(" update ORG_EMPLOYEE");
      if ("姓名".equals(obj[4].toString().trim()))
        sb.append(" set EMPNAME='" + obj[6].toString() + "' "); 
      if ("编号".equals(obj[4].toString().trim()))
        sb.append(" set useraccounts='" + obj[6].toString() + "' "); 
      if ("出生日期".equals(obj[4].toString().trim()))
        sb.append(" set empBirth='" + obj[6].toString() + "' "); 
      sb.append("where EMP_ID= " + obj[2].toString());
      if (sb.toString().contains("set"))
        stmt.executeUpdate(sb.toString()); 
      flag = true;
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      flag = false;
      e.printStackTrace();
      System.out.println("修改用户基本信息时出现异常！");
    } 
    return flag;
  }
  
  public boolean deleteIndexUser(String userId) {
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
    } catch (SQLException e1) {
      e1.printStackTrace();
      System.out.println("删除用户基本信息获取相应的connection时出现异常！");
    } 
    try {
      stmt = conn.createStatement();
    } catch (SQLException e1) {
      System.out.println("删除用户基本信息获取相应的statement时出现异常！");
      e1.printStackTrace();
    } 
    boolean flag = false;
    try {
      String sql = "update org_employee set USERISDELETED=0 where emp_id= " + userId;
      stmt.executeUpdate(sql);
      flag = true;
      stmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      flag = false;
      e.printStackTrace();
      System.out.println("进行删除用户基本信息业务操作时出现异常！");
    } 
    return flag;
  }
}
