package com.js.oa.hntdxy;

import com.js.util.util.DataSourceBase;
import com.js.util.util.StringSplit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonalInfo {
  public String getPersonalInfo(String useraccounts_new) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String useraccount = "";
    String sql = "select useraccount_old from userinfo_mapping where useraccount_new=?";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, useraccounts_new);
      rs = pstmt.executeQuery();
      if (rs.next())
        useraccount = rs.getString(1); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (rs != null)
          rs.close(); 
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return useraccount;
  }
  
  public List<String> getPersonalEmpId(String useraccounts) {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    List<String> list = new ArrayList<String>();
    String empid = "", orgid = "", orgidstring = "", domain_id = "";
    String sql = "select a.emp_id,c.org_id,c.orgidstring,a.domain_id from org_employee a left join ORG_ORGANIZATION_USER b on a.emp_id=b.emp_id left join org_organization c on b.org_id=c.org_id where a.useraccounts='" + 
      
      useraccounts + "'";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next()) {
        empid = rs.getString(1);
        orgid = rs.getString(2);
        orgidstring = rs.getString(3);
        orgidstring = StringSplit.splitOrgIdString(orgidstring, "$", "_");
        domain_id = rs.getString(4);
        list.add(empid);
        list.add(orgid);
        list.add(orgidstring);
        list.add(domain_id);
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (rs != null)
          rs.close(); 
        if (stmt != null)
          stmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return list;
  }
}
