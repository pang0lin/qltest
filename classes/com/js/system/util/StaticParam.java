package com.js.system.util;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceUtil;
import com.js.util.util.DateHelper;
import com.js.util.util.MD5;
import com.js.util.util.ParameterFilter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.sql.DataSource;

public class StaticParam {
  public static final String rootDeptId = "20080808518";
  
  public static String getRootDeptId() {
    String rootDeptId = "";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT ORG_ID FROM ORG_ORGANIZATION where ORGPARENTORGID=-1");
      if (rs.next())
        rootDeptId = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return rootDeptId;
  }
  
  public static String getDeptPic() {
    String pic = "";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT guid FROM ORG_ORGANIZATION where ORGPARENTORGID=-1");
      if (rs.next())
        pic = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return pic;
  }
  
  public static String getDeptName() {
    String name = "";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT orgname FROM ORG_ORGANIZATION where ORGPARENTORGID=-1");
      if (rs.next())
        name = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return name;
  }
  
  public static void delById(String id, String tabName, String coloum) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      stmt.executeUpdate(" delete from " + tabName + " where " + coloum + "=" + id);
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
  }
  
  public static String getNetDiskName(String id) {
    String name = "";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT file_name,file_savename FROM oa_netdisk_file where file_id=" + id);
      if (rs.next())
        name = String.valueOf(rs.getString(1)) + ";" + rs.getString(2); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return name;
  }
  
  public static String getDepOrgIdString(String id) {
    String name = "";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT ORGIDSTRING FROM org_organization where ORG_ID=" + id);
      if (rs.next())
        name = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return name;
  }
  
  public static String isEmpIsOrgLeader(String id) {
    String name = "0";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select count(*)  from org_organization o where  o.ORGMANAGEREMPID like '%$" + id + "$%'");
      if (rs.next() && 
        rs.getInt(1) != 0)
        name = "1"; 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return name;
  }
  
  public static String getEmpIdsByOrgId(String orgId) {
    DataSourceBase base = new DataSourceBase();
    String ids = "";
    try {
      base.begin();
      String sql = "SELECT emp_id FROM org_user WHERE orgIdString LIKE '%$" + orgId + "$%'";
      ResultSet rs = base.executeQuery(sql);
      while (rs.next())
        ids = String.valueOf(ids) + rs.getString(1) + ","; 
      rs.close();
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return ids;
  }
  
  public static String operLogonTempByEmpId(String id, String action, String other, String time) {
    String name = "";
    int num = 0;
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    Date nowDate = new Date();
    String now = DateHelper.date2String(nowDate, null);
    String databaseType = SystemCommon.getDatabaseType();
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      if (action.equals("select")) {
        rs = stmt.executeQuery("SELECT logon_date,logon_num FROM sys_logon_temp where logon_emp_id='" + id + "'");
        if (rs.next())
          name = String.valueOf(rs.getString(1)) + ";" + rs.getInt(2); 
        rs.close();
        if (other.equals("sau"))
          if ("".equals(name)) {
            String sql = " insert into sys_logon_temp set logon_date='" + now + "',logon_num=logon_num+1,logon_emp_id='" + id + "'";
            if (databaseType.indexOf("oracle") >= 0)
              sql = " insert into sys_logon_temp (logon_error_id,logon_date,logon_num,logon_emp_id) values (HIBERNATE_SEQUENCE.nextval,'" + now + "',1,'" + id + "')"; 
            stmt.executeUpdate(sql);
          } else {
            Date afterDate = DateHelper.string2Date(name.split(";")[0], null);
            long min = DateHelper.getDistanceMin(nowDate, afterDate);
            int sequence = Integer.parseInt(name.split(";")[1]) + 1;
            if (0L <= min && min < 1L) {
              String sql1 = " update sys_logon_temp set logon_date='" + now + "',logon_num=logon_num+1 where logon_emp_id='" + id + "'";
              if (databaseType.indexOf("oracle") >= 0)
                sql1 = " update sys_logon_temp set logon_date='" + now + "',logon_num=" + sequence + " where logon_emp_id='" + id + "'"; 
              stmt.executeUpdate(sql1);
            } else if (1L <= min && min < Long.parseLong(time.split(";")[2])) {
              String sql2 = " update sys_logon_temp set logon_date='" + now + "',logon_num=logon_num+1 where logon_emp_id='" + id + "'";
              if (databaseType.indexOf("oracle") >= 0)
                sql2 = " update sys_logon_temp set logon_date='" + now + "',logon_num=" + sequence + " where logon_emp_id='" + id + "'"; 
              stmt.executeUpdate(sql2);
              name = String.valueOf(now) + ";" + sequence;
            } else if (min >= Long.parseLong(time.split(";")[2])) {
              stmt.executeUpdate(" delete from sys_logon_temp where logon_emp_id='" + id + "'");
              String sql3 = " insert into sys_logon_temp set logon_date='" + now + "',logon_num=logon_num+1,logon_emp_id='" + id + "'";
              if (databaseType.indexOf("oracle") >= 0)
                sql3 = " insert into sys_logon_temp (logon_error_id,logon_date,logon_num,logon_emp_id) values (HIBERNATE_SEQUENCE.nextval,'" + now + "'," + sequence + ",'" + id + "')"; 
              stmt.executeUpdate(sql3);
              name = String.valueOf(now) + ";" + "1";
            } 
          }  
      } 
      if (action.equals("delete"))
        stmt.executeUpdate(" delete from sys_logon_temp where logon_emp_id='" + id + "'"); 
      if (action.equals("checkAgain")) {
        rs = stmt.executeQuery("SELECT logon_date,logon_num FROM sys_logon_temp where logon_emp_id='" + id + "'");
        if (rs.next()) {
          name = rs.getString(1);
          num = rs.getInt(2);
        } 
        rs.close();
        if (!name.equals("")) {
          long min = DateHelper.getDistanceMin(nowDate, DateHelper.string2Date(name, null));
          if (min > Long.parseLong(time.split(";")[2]) || num < Integer.parseInt(time.split(";")[0]))
            name = ""; 
        } 
      } 
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return name;
  }
  
  public static boolean isFirstTimeLoginValidate(String domainId) {
    boolean flag = true;
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select pwd_init from org_domain where domain_id='" + domainId + "'");
      if (rs.next()) {
        int count = rs.getInt("pwd_init");
        flag = (count == 1);
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return flag;
  }
  
  public static boolean isFirstTimeLogin(String userAccount) {
    boolean flag = true;
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select count(*) as flag from sys_logon_record where useraccount='" + userAccount + "'");
      if (rs.next()) {
        int count = rs.getInt("flag");
        flag = (count == 0);
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return flag;
  }
  
  public static boolean safetyVerification(String userAccount, String userPassword) {
    boolean flag = false;
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select userpassword as flag from org_employee where useraccounts='" + userAccount + "' and userisdeleted=0");
      if (rs.next())
        flag = (new MD5()).equals(userPassword, rs.getString(1)); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return flag;
  }
  
  public static boolean modifyPassword(String userAccount, String newPassword) {
    boolean flag = false;
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    try {
      String currentDateString = DateHelper.date2String(new Date(), "yyyy-MM-dd HH:mm");
      conn = ds.getConnection();
      stmt = conn.createStatement();
      String sqlString = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sqlString = "update org_employee set userpassword ='" + newPassword + "', last_modify_pwd_date='" + currentDateString + "' where useraccounts='" + userAccount + "' and userisdeleted=0 and userisactive=1";
      } else if (databaseType.indexOf("oracle") >= 0) {
        sqlString = "update org_employee set userpassword ='" + newPassword + "', last_modify_pwd_date=to_date('" + currentDateString + "', 'YYYY-MM-DD HH24:MI:SS') where useraccounts='" + userAccount + "' and userisdeleted=0 and userisactive=1";
      } else {
        sqlString = "update org_employee set userpassword ='" + newPassword + "', last_modify_pwd_date='" + currentDateString + "' where useraccounts='" + userAccount + "' and userisdeleted=0 and userisactive=1";
      } 
      int info = stmt.executeUpdate(sqlString);
      flag = (info == 1);
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return flag;
  }
  
  public static String getValidatePasswordStrong(String domainId) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String pwdStrong = "0";
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select pwd_strong from org_domain where domain_id='" + domainId + "'");
      if (rs.next())
        pwdStrong = rs.getString("pwd_strong"); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return pwdStrong;
  }
  
  public static boolean passwordModifyComplete(String userAccount) {
    boolean flag = false;
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      String sqlString = "";
      String databaseType = SystemCommon.getDatabaseType();
      rs = stmt.executeQuery("select count(*) as flag from sys_logon_record where useraccount='" + userAccount + "'");
      if (rs.next()) {
        int count = rs.getInt("flag");
        rs.close();
        if (count == 0) {
          if (databaseType.indexOf("oracle") >= 0) {
            sqlString = "insert into sys_logon_record (logon_record_id,useraccount) values(HIBERNATE_SEQUENCE.nextval,'" + userAccount + "')";
          } else {
            sqlString = "insert into sys_logon_record (useraccount) values('" + userAccount + "')";
          } 
          int info = stmt.executeUpdate(sqlString);
          flag = (info == 1);
        } 
      } else {
        rs.close();
      } 
      rs.close();
      String currentDateString = DateHelper.date2String(new Date(), "yyyy-MM-dd HH:mm");
      if (databaseType.indexOf("mysql") >= 0) {
        sqlString = "update org_employee set last_modify_pwd_date='" + currentDateString + "' where useraccounts='" + userAccount + "' and userisdeleted=0 and userisactive=1";
      } else if (databaseType.indexOf("oracle") >= 0) {
        sqlString = "update org_employee set last_modify_pwd_date=to_date('" + currentDateString + "', 'YYYY-MM-DD HH24:MI:SS') where useraccounts='" + userAccount + "' and userisdeleted=0 and userisactive=1";
      } else {
        sqlString = "update org_employee set last_modify_pwd_date='" + currentDateString + "' where useraccounts='" + userAccount + "' and userisdeleted=0 and userisactive=1";
      } 
      stmt.executeUpdate(sqlString);
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
      ex.printStackTrace();
    } 
    return flag;
  }
  
  public static String isValidatePasswordOutDate(String domainId) {
    String pwd_date = "0";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select pwd_date from org_domain where domain_id='" + domainId + "'");
      if (rs.next())
        pwd_date = rs.getString("pwd_date"); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return pwd_date;
  }
  
  public static String getLastModifyPwdDate(String userAccount) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String last_modify_pwd_date = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select last_modify_pwd_date from org_employee where useraccounts='" + userAccount + "' and userisdeleted=0");
      if (rs.next())
        last_modify_pwd_date = rs.getString("last_modify_pwd_date"); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return last_modify_pwd_date;
  }
  
  public static boolean updateLastModifyPwdDate(String userAccount) {
    boolean flag = false;
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    try {
      String currentDateString = DateHelper.date2String(new Date(), "yyyy-MM-dd HH:mm");
      conn = ds.getConnection();
      stmt = conn.createStatement();
      String sqlString = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sqlString = "update org_employee set last_modify_pwd_date='" + currentDateString + "' where useraccounts='" + userAccount + "' and userisdeleted=0";
      } else if (databaseType.indexOf("oracle") >= 0) {
        sqlString = "update org_employee set last_modify_pwd_date=to_date('" + currentDateString + "', 'YYYY-MM-DD HH24:MI:SS') where useraccounts='" + userAccount + "' and userisdeleted=0";
      } else {
        sqlString = "update org_employee set last_modify_pwd_date='" + currentDateString + "' where useraccounts='" + userAccount + "' and userisdeleted=0";
      } 
      int info = stmt.executeUpdate(sqlString);
      flag = (info == 1);
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
      ex.printStackTrace();
    } 
    return flag;
  }
  
  public static HashMap<String, String> getSysLogoConfig(String domainId) {
    HashMap<String, String> map = new HashMap<String, String>();
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select company_name,company_color,isdisplay_company_name,isdisplay_logo from org_domain where domain_id='" + domainId + "'");
      if (rs.next()) {
        map.put("company_name", rs.getString("company_name"));
        map.put("company_color", rs.getString("company_color"));
        map.put("isdisplay_company_name", rs.getString("isdisplay_company_name"));
        map.put("isdisplay_logo", rs.getString("isdisplay_logo"));
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return map;
  }
  
  public static boolean updateSysLogoConfig(String domainId, String companyName, String companyColor, String isDisplayCompanyName, String isDisplayLogo) {
    boolean flag = false;
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      String sql = "update org_domain set company_name=?,company_color=?,isdisplay_logo=?,isdisplay_company_name=? where domain_id=?";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, companyName);
      pstmt.setString(2, companyColor);
      pstmt.setString(3, isDisplayLogo);
      pstmt.setString(4, isDisplayCompanyName);
      pstmt.setString(5, domainId);
      int info = pstmt.executeUpdate();
      pstmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return flag;
  }
  
  public static HashMap<String, String> getVersionInfo() {
    HashMap<String, String> map = new HashMap<String, String>();
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select ptime,ver,memo from sys_patchinfo order by ptime desc");
      if (rs.next()) {
        String updateDate = rs.getString("ptime");
        if (updateDate != null && !"".equals(updateDate)) {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          updateDate = sdf.format(sdf.parse(updateDate));
        } 
        map.put("ptime", updateDate);
        map.put("ver", rs.getString("ver"));
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return map;
  }
  
  public static String getEmpIdByCondStr(String condStr, String type) {
    StringBuffer str = new StringBuffer();
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      if (condStr.indexOf("@") > 0 || type.equals("group")) {
        condStr = condStr.replaceAll("\\@\\@", ",");
        rs = stmt.executeQuery("select GROUPUSERSTRING from org_group where group_id in(" + condStr + ")");
        while (rs.next())
          str.append(rs.getString(1)); 
      } 
      if (condStr.indexOf("*") > 0 || type.equals("org")) {
        condStr = condStr.replaceAll("\\*\\*", ",");
        StringBuffer childSql = new StringBuffer(" select org_id from org_organization where ");
        if (!condStr.equals("")) {
          String[] child = condStr.split(",");
          for (int i = 0; i < child.length; i++)
            childSql.append(" ORGIDSTRING like '%$" + child[i] + "$%' or "); 
        } 
        StringBuffer sql = new StringBuffer();
        sql.append(" select o.emp_id from org_organization_user o,org_employee e ");
        sql.append(" where o.org_id in(" + childSql.toString().substring(0, childSql.toString().lastIndexOf("or")) + ") ");
        sql.append(" and e.EMP_ID=o.EMP_ID and e.USERISACTIVE=1 and e.USERISDELETED=0 and e.useraccounts is not null");
        rs = stmt.executeQuery(sql.toString());
        while (rs.next())
          str.append("$").append(rs.getInt(1)).append("$"); 
      } 
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return str.toString();
  }
  
  public static boolean isAllowGrade(String questionnaireId) {
    boolean flag = true;
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select grade from oa_questionnaire where questionnaireid='" + questionnaireId + "'");
      if (rs.next()) {
        int grade = rs.getInt("grade");
        flag = (grade == 1);
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return flag;
  }
  
  public static void updateInformationByChannelId(String channelId) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      stmt.executeUpdate("update oa_information set isallow='0' where isallow='1' and channel_id='" + channelId + "'");
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
  }
  
  public static String getorgNameBySerial(String serial) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String pwdStrong = "";
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select orgname from org_organization  where orgserial='" + serial + "'");
      if (rs.next())
        pwdStrong = rs.getString("orgname"); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return pwdStrong;
  }
  
  public static String getOrgIdStringByOrgId(String orgId) {
    String idString = "";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT orgidstring FROM ORG_ORGANIZATION where org_id=" + orgId);
      if (rs.next())
        idString = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return idString;
  }
  
  public static String getOrgNameByOrgId(String orgId) {
    String idString = "";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT orgname FROM ORG_ORGANIZATION where org_id=" + orgId);
      if (rs.next())
        idString = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return idString;
  }
  
  public static String getOrgIdByEmpId(String empId) {
    String idString = "";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      Long id = Long.valueOf(empId);
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT ORG_ID FROM ORG_ORGANIZATION_USER where emp_id=" + id.toString());
      while (rs.next())
        idString = String.valueOf(idString) + rs.getString(1) + ","; 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    if (idString.length() > 0)
      idString = idString.substring(0, idString.lastIndexOf(",")); 
    return idString;
  }
  
  public static String getProLeaderIdAndNameByProId(String proId) {
    String idString = "", nameString = "";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT ACTORID,ACTORNAME FROM pro_actor where ACTORROLE=20 and PROJECT_ID=" + proId);
      while (rs.next()) {
        idString = String.valueOf(idString) + "$" + rs.getInt(1) + "$";
        nameString = String.valueOf(nameString) + rs.getString(2) + ",";
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return String.valueOf(idString) + ";" + nameString;
  }
  
  public static String getOrgLeaderById(String orgId) {
    String idString = "", nameString = "";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT ORGMANAGEREMPID,ORGMANAGEREMPNAME FROM org_organization where org_id=" + orgId);
      if (rs.next()) {
        idString = rs.getString(1);
        nameString = rs.getString(2);
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return String.valueOf(idString) + ";" + nameString;
  }
  
  public static boolean isProjectExist(String channelId) {
    boolean flag = false;
    if (channelId.equals("100") || channelId.equals("101"))
      return flag; 
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT count(*) as totalNum FROM pro_body where ID=(SELECT relproject_id FROM oa_informationchannel where CHANNEL_ID=" + channelId + ")");
      if (rs.next() && 
        rs.getInt("totalNum") == 0)
        flag = true; 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return flag;
  }
  
  public void setComment(String dataType) {
    if (dataType.indexOf("oracle") >= 0) {
      Connection conn = null;
      Statement stmt = null;
      ResultSet rs = null;
      try {
        conn = (new DataSourceBase()).getDataSource().getConnection();
        stmt = conn.createStatement();
        List<String> tableList = new ArrayList<String>();
        rs = stmt.executeQuery("select table_name from user_tables");
        while (rs.next())
          tableList.add(rs.getString(1)); 
        rs.close();
        for (int i = 0; i < tableList.size(); i++) {
          String tableName = tableList.get(i);
          if (tableName.startsWith("OA_") || tableName.startsWith("JSF_") || tableName.startsWith("ORG_") || tableName.startsWith("SYS_")) {
            List<String> columnList = new ArrayList<String>();
            rs = stmt.executeQuery("select column_name from user_tab_columns where Table_Name='" + tableName + "'");
            while (rs.next())
              columnList.add(rs.getString(1)); 
            rs.close();
            for (int j = 0; j < columnList.size(); j++)
              stmt.executeUpdate("comment on column " + (String)tableList.get(i) + "." + (String)columnList.get(j) + " is ''"); 
          } 
        } 
        stmt.close();
        conn.close();
      } catch (Exception ex) {
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        } 
        ex.printStackTrace();
      } 
    } 
  }
  
  public static void deleteMessage(String type, String title, String url, String userId) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      int i = stmt.executeUpdate(" delete from sys_messages where message_title='" + title + "' and message_type='" + type + "' and message_url='" + url + "' and message_touserid=" + userId);
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }  
      ex.printStackTrace();
    } 
  }
  
  public static String getLogCheck(String logId) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    String check = "0";
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select isChecked from audit_log where log_id=" + logId);
      if (rs.next())
        check = rs.getString(1); 
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return check;
  }
  
  public static String getEmpIdByAccount(String account) {
    DataSourceBase base = new DataSourceBase();
    String empId = "";
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      String sql = "SELECT emp_id FROM org_employee WHERE useraccounts=? and userisdeleted=0";
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, account);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next())
        empId = rs.getString(1); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        conn.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return empId;
  }
  
  public static String getEmpNameByEmpId(String empId) {
    DataSourceBase base = new DataSourceBase();
    String empName = "";
    ResultSet rs = null;
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      String sql = "SELECT empName FROM org_employee WHERE emp_id=" + empId;
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, empId);
      rs = pstmt.executeQuery();
      if (rs.next())
        empName = rs.getString(1); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        conn.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return empName;
  }
  
  public static String getEmpIdByName(String name) {
    String empIds = "";
    if ("".equals(name)) {
      empIds = ",-1000";
    } else {
      DataSourceBase base = new DataSourceBase();
      ResultSet rs = null;
      Connection conn = null;
      PreparedStatement pstmt = null;
      try {
        String sql = "SELECT emp_id FROM org_employee WHERE empName like ? and USERISACTIVE=1 and USERISDELETED=0";
        conn = base.getDataSource().getConnection();
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "%" + name + "%");
        rs = pstmt.executeQuery();
        while (rs.next())
          empIds = "," + rs.getString(1); 
        rs.close();
        pstmt.close();
        conn.close();
      } catch (Exception e) {
        try {
          conn.close();
        } catch (Exception ex) {
          ex.printStackTrace();
        } 
        e.printStackTrace();
      } 
    } 
    return empIds.substring(1);
  }
  
  public static String getWorkFlowProject(String processId, String tableId, String recordId) {
    String projectId = "0";
    DataSourceBase base = new DataSourceBase();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      String sql = "SELECT relproject_id FROM jsf_work WHERE workprocess_id=? AND worktable_Id=? AND workrecord_Id=? ORDER BY wf_work_id";
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, processId);
      pstmt.setString(2, tableId);
      pstmt.setString(3, recordId);
      rs = pstmt.executeQuery();
      if (rs.next())
        projectId = rs.getString(1); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        conn.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return projectId;
  }
  
  public static String getWorkFlowProjectIdAndName(String processId, String tableId, String recordId) {
    String projectId = "0";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      String sql = "SELECT relproject_id FROM jsf_work WHERE workprocess_id=" + processId + " AND worktable_Id=" + tableId + 
        " AND workrecord_Id=" + recordId + " ORDER BY wf_work_id";
      rs = base.executeQuery(sql);
      if (rs.next())
        projectId = rs.getString(1); 
      rs.close();
      sql = "select title from pro_body where id=" + projectId;
      rs = base.executeQuery(sql);
      if (rs.next())
        projectId = String.valueOf(projectId) + ";" + rs.getString(1); 
      rs.close();
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return projectId;
  }
  
  public static String[] getPhoneByUserId(String userId) {
    String[] phones = new String[2];
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      String sql = "SELECT empMobilePhone,empBusinessPhone FROM org_employee WHERE emp_id=" + userId;
      rs = base.executeQuery(sql);
      if (rs.next()) {
        phones[0] = (rs.getString(1) == null) ? "" : rs.getString(1);
        phones[0] = (rs.getString(2) == null) ? "" : rs.getString(2);
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return phones;
  }
  
  public static String getOrgByNum(String orgName) {
    int showNum = SystemCommon.getInnerShow();
    return getOrgByNum(orgName, showNum);
  }
  
  public static String getOrgByNum(String orgName, int showNum) {
    if (orgName == null)
      orgName = ""; 
    String orgNameNew = "";
    String[] orgNames = orgName.split("\\.");
    if (showNum > 0 && orgNames.length > showNum) {
      for (int i = orgNames.length - showNum; i < orgNames.length; i++)
        orgNameNew = String.valueOf(orgNameNew) + orgNames[i] + "."; 
      if (orgNameNew.endsWith("."))
        orgNameNew = orgNameNew.substring(0, orgNameNew.length() - 1); 
    } else {
      orgNameNew = orgName;
    } 
    return orgNameNew;
  }
  
  public static String getOrgIdsByOrgId(String orgId) {
    String orgIds = "-99";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      String sql = "SELECT org_id FROM org_organization WHERE orgidString LIKE '%$" + orgId + "$%'";
      rs = base.executeQuery(sql);
      while (rs.next())
        orgIds = String.valueOf(orgIds) + "," + rs.getString(1); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return orgIds;
  }
  
  public static String getOrgTree(String orgId) {
    return getOrgTree(orgId, "");
  }
  
  public static String getOrgTree(String orgId, String curOrgId) {
    String benren = "";
    if (orgId.indexOf("-") > 0) {
      benren = orgId.split("-")[1];
      orgId = orgId.split("-")[0];
    } 
    String orgStr = "";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String orgIdString = "";
    String orgIdStr = "0";
    String sql = "";
    try {
      base.begin();
      if (!orgId.equals("0")) {
        for (; orgId.startsWith(","); orgId = orgId.substring(1));
        sql = "SELECT orgIdstring FROM org_organization WHERE org_Id in (" + orgId + ") and (orgstatus='0' or orgparentorgId=-1)";
        rs = base.executeQuery(sql);
        while (rs.next())
          orgIdString = String.valueOf(orgIdString) + rs.getString(1); 
        String[] orgIds = orgIdString.split("\\$");
        for (int i = 1; i < orgIds.length; i += 2) {
          if (orgIdStr.indexOf("," + orgIds[i] + ",") < 0)
            orgIdStr = String.valueOf(orgIdStr) + "," + orgIds[i]; 
        } 
        sql = "SELECT org_id,orgparentorgId,orgname FROM org_organization WHERE org_Id in (" + orgIdStr + ") and (orgstatus='0' or orgparentorgId=-1) ORDER BY orgIdstring,orgordercode";
      } else {
        sql = "SELECT org_id,orgparentorgId,orgname FROM org_organization where (orgstatus='0' or orgparentorgId=-1) ORDER BY orgIdstring,orgordercode";
      } 
      rs = base.executeQuery(sql);
      while (rs.next()) {
        if (curOrgId.contains("%" + rs.getString(1) + "%")) {
          orgStr = String.valueOf(orgStr) + "d.add(" + rs.getString(1) + "," + rs.getString(2) + ",'" + rs.getString(3) + "','#;','" + rs.getString(3) + "','','','',true);\n";
          continue;
        } 
        orgStr = String.valueOf(orgStr) + "d.add(" + rs.getString(1) + "," + rs.getString(2) + ",'" + rs.getString(3) + "','#;','" + rs.getString(3) + "','','','',false);\n";
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return orgStr;
  }
  
  public static String getPerforOrgTree(String orgId, String curOrgId) {
    return getPerforOrgTree(orgId, curOrgId, "");
  }
  
  public static String getPerforOrgTree(String orgId, String curOrgId, String empOrgId) {
    if (!"".equals(empOrgId))
      curOrgId = getOrgIdStringByOrgId(empOrgId).replace("$", "%"); 
    String orgStr = "";
    DataSourceBase base = new DataSourceBase();
    List<Object[]> list = new ArrayList();
    ResultSet rs = null;
    String orgIdString = "";
    String orgIdStr = "0";
    String sql = "";
    try {
      base.begin();
      if (!orgId.equals("0")) {
        for (; orgId.startsWith(","); orgId = orgId.substring(1));
        sql = "SELECT orgIdstring FROM org_organization WHERE org_Id in (" + orgId + ") and (orgstatus='0' or orgparentorgId=-1)";
        rs = base.executeQuery(sql);
        while (rs.next())
          orgIdString = String.valueOf(orgIdString) + rs.getString(1); 
        String[] orgIds = orgIdString.split("\\$");
        for (int i = 1; i < orgIds.length; i += 2) {
          if (orgIdStr.indexOf("," + orgIds[i] + ",") < 0)
            orgIdStr = String.valueOf(orgIdStr) + "," + orgIds[i]; 
        } 
        sql = "SELECT org_id,orgparentorgId,orgname FROM org_organization WHERE org_Id in (" + orgIdStr + ") and (orgstatus='0' or orgparentorgId=-1) ORDER BY orgIdstring,orgordercode";
      } else {
        sql = "SELECT org_id,orgparentorgId,orgname FROM org_organization where (orgstatus='0' or orgparentorgId=-1) ORDER BY orgIdstring,orgordercode";
      } 
      rs = base.executeQuery(sql);
      while (rs.next()) {
        if (curOrgId.contains("%" + rs.getString(1) + "%")) {
          orgStr = String.valueOf(orgStr) + "d.add(" + rs.getString(1) + "," + rs.getString(2) + ",'" + rs.getString(3) + "','#;','" + rs.getString(3) + "','','','',true);\n";
          list = getEmployeeList(rs.getString(1));
          for (int j = 0; j < list.size(); j++) {
            Object[] obj = list.get(j);
            String personName = obj[1] + "[P]";
            orgStr = String.valueOf(orgStr) + "d.add(" + obj[0] + "," + rs.getString(1) + ",'" + personName + "','#;','" + obj[1] + "','','','',true);\n";
          } 
          continue;
        } 
        orgStr = String.valueOf(orgStr) + "d.add(" + rs.getString(1) + "," + rs.getString(2) + ",'" + rs.getString(3) + "','#;','" + rs.getString(3) + "','','','',false);\n";
        list = getEmployeeList(rs.getString(1));
        if (list.size() > 0) {
          if (rs.getString(1).equals(empOrgId)) {
            for (int j = 0; j < list.size(); j++) {
              Object[] arrayOfObject = list.get(j);
              String str = arrayOfObject[1] + "[P]";
              orgStr = String.valueOf(orgStr) + "d.add(" + arrayOfObject[0] + "," + rs.getString(1) + ",'" + str + "','#;','" + arrayOfObject[1] + "','','','',false);\n";
            } 
            continue;
          } 
          Object[] obj = list.get(0);
          String personName = obj[1] + "[P]";
          orgStr = String.valueOf(orgStr) + "d.add(" + obj[0] + "," + rs.getString(1) + ",'" + personName + "','#;','" + obj[1] + "','','','',false);\n";
        } 
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return orgStr;
  }
  
  public static List getEmployeeList(String orgId) throws Exception {
    List<Object[]> list = new ArrayList();
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String imployeeSql = "select aa.emp_id,empName from org_employee aa,org_organization_user bb  where aa.emp_id=bb.emp_id and USERISACTIVE=1 and USERISDELETED=0 and aa.empnumber is not null  and org_id=" + orgId;
    try {
      base.begin();
      rs = base.executeQuery(imployeeSql);
      while (rs.next()) {
        Object[] obj = new Object[2];
        obj[0] = rs.getString(1);
        obj[1] = rs.getString(2);
        list.add(obj);
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return list;
  }
  
  public static String getOrgTree(String orgId, String curOrgId, String iscurrentOrg, String orgIdcurrent) {
    String benren = "";
    if (orgId.indexOf("-") > 0) {
      benren = orgId.split("-")[1];
      orgId = orgId.split("-")[0];
    } 
    String orgStr = "";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String orgIdString = "";
    String orgIdStr = "0";
    String sql = "";
    try {
      base.begin();
      if ("1".equals(iscurrentOrg))
        orgIdStr = String.valueOf(orgIdcurrent) + "," + orgIdStr; 
      sql = "SELECT org_id,orgparentorgId,orgname FROM org_organization WHERE org_Id in (" + orgIdStr + ") and (orgstatus='0' or orgparentorgId=-1) ORDER BY orgIdstring,orgordercode";
      String parStringId = "";
      rs = base.executeQuery(sql);
      while (rs.next()) {
        if (curOrgId.contains("%" + rs.getString(1) + "%")) {
          orgStr = String.valueOf(orgStr) + "d.add(" + rs.getString(1) + "," + rs.getString(2) + ",'" + rs.getString(3) + "','#;','" + rs.getString(3) + "','','','',true);\n";
          continue;
        } 
        if ("".equals(parStringId)) {
          parStringId = rs.getString(1);
          orgStr = String.valueOf(orgStr) + "d.add(" + rs.getString(1) + "," + rs.getString(2) + ",'" + rs.getString(3) + "','#;','" + rs.getString(3) + "','','','',false);\n";
          continue;
        } 
        orgStr = String.valueOf(orgStr) + "d.add(" + rs.getString(2) + "," + parStringId + ",'" + rs.getString(3) + "','#;','" + rs.getString(3) + "','','','',false);\n";
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return orgStr;
  }
  
  public static String getOrgTreeForOnline(String orgId, String curOrgId) {
    String benren = "";
    if (orgId.indexOf("-") > 0) {
      benren = orgId.split("-")[1];
      orgId = orgId.split("-")[0];
    } 
    String orgStr = "";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String orgIdString = "";
    String orgIdStr = "0";
    String sql = "";
    try {
      base.begin();
      if (!orgId.equals("0")) {
        for (; orgId.startsWith(","); orgId = orgId.substring(1));
        sql = "SELECT orgIdstring FROM org_organization WHERE org_Id in (" + orgId + ") and (orgstatus='0' or orgparentorgId=-1)";
        rs = base.executeQuery(sql);
        while (rs.next())
          orgIdString = String.valueOf(orgIdString) + rs.getString(1); 
        String[] orgIds = orgIdString.split("\\$");
        for (int i = 1; i < orgIds.length; i += 2)
          orgIdStr = String.valueOf(orgIdStr) + "," + orgIds[i]; 
        sql = "SELECT org_id,orgparentorgId,orgname FROM org_organization WHERE org_Id in (" + orgIdStr + ") and (orgstatus='0' or orgparentorgId=-1) ORDER BY orgIdstring,orgordercode";
      } else {
        sql = "SELECT org_id,orgparentorgId,orgname FROM org_organization where (orgstatus='0' or orgparentorgId=-1) ORDER BY orgIdstring,orgordercode";
      } 
      rs = base.executeQuery(sql);
      while (rs.next()) {
        if (curOrgId.contains("%" + rs.getString(1) + "%")) {
          orgStr = String.valueOf(orgStr) + "d.add(" + rs.getString(1) + "," + rs.getString(2) + ",'" + rs.getString(3) + "','#;','" + rs.getString(3) + "','','','',true);\n";
          continue;
        } 
        orgStr = String.valueOf(orgStr) + "d.add(" + rs.getString(1) + "," + rs.getString(2) + ",'" + rs.getString(3) + "','#;','" + rs.getString(3) + "','','','',false);\n";
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return orgStr;
  }
  
  public static String getDanganTree(String userId, String classId) {
    String orgStr = "";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String sql = "";
    String orgIdString = getOrgIdByEmpId(userId);
    String orgIdsString = getOrgIdStringByOrgId(orgIdString);
    String groupIdString = getGroupIdByEmpId(userId);
    String orgIdStr = "";
    String[] orgIds = orgIdsString.split("\\$");
    for (int i = 1; i < orgIds.length; i += 2)
      orgIdStr = String.valueOf(orgIdStr) + " or CLASSREADORG like '%*" + orgIds[i] + "*%' "; 
    String groupSpString = "";
    String[] groupIdStr = groupIdString.split(",");
    for (int j = 0; j < groupIdStr.length; j++)
      groupSpString = String.valueOf(groupSpString) + " or  CLASSREADGROUP like '%@" + groupIdStr[j] + "@%' "; 
    try {
      base.begin();
      if (!classId.equals("0")) {
        sql = "SELECT class_id,classparentId,classname FROM oa_archivesclass where classreader like '%$" + userId + "$%' " + orgIdStr + "  or classreadorg = '*-1*' " + groupSpString + " or classparentId=-1 ORDER BY classIdstring,classordercode";
      } else {
        sql = "SELECT class_id,classparentId,classname FROM oa_archivesclass where classparentId=-1 ORDER BY classIdstring,classordercode";
      } 
      rs = base.executeQuery(sql);
      while (rs.next())
        orgStr = String.valueOf(orgStr) + "d.add(" + rs.getString(1) + "," + rs.getString(2) + ",'" + rs.getString(3) + "','#;','" + rs.getString(3) + "','');\n"; 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return orgStr;
  }
  
  public static String getGroupIdByEmpId(String empId) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String returnValue = "";
    String sql = "SELECT group_id FROM org_user_group WHERE emp_id=" + empId;
    try {
      base.begin();
      rs = base.executeQuery(sql);
      while (rs.next())
        returnValue = String.valueOf(returnValue) + rs.getString(1) + ","; 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return returnValue;
  }
  
  public static String getParentOrgIdsByOrgId(String orgId) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String orgIds = "";
    String returnValue = "";
    try {
      base.begin();
      String sql = "SELECT orgidString FROM org_organization WHERE org_id=" + orgId;
      rs = base.executeQuery(sql);
      if (rs.next())
        orgIds = rs.getString(1); 
      base.end();
      String[] org_id = orgIds.split("\\$");
      for (int i = 1; i < org_id.length; i += 2)
        returnValue = String.valueOf(returnValue) + org_id[i] + ","; 
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return returnValue;
  }
  
  public static String getSidelineorg(String empId) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String orgIds = "";
    String returnValue = "";
    try {
      base.begin();
      String sql = "SELECT sidelineorg FROM org_employee WHERE emp_id=" + empId;
      rs = base.executeQuery(sql);
      if (rs.next())
        orgIds = (rs.getString(1) == null || "null".equals(rs.getString(1))) ? "" : rs.getString(1); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    if (orgIds.length() > 0)
      returnValue = orgIds.substring(1, orgIds.length() - 1).replace("**", ","); 
    return returnValue;
  }
  
  public static String orgIdsByOrgId(String orgId) {
    String[] orgIds = orgId.replace("**", ",").replace("*", "").split(",");
    String orgIdStr = "";
    String orgSql = "select org_Id from org_organization where 1<>1 ";
    for (int i = 0; i < orgIds.length; i++) {
      if (!"".equals(orgIds[i]))
        orgSql = String.valueOf(orgSql) + " or orgIdString like '%$" + orgIds[i] + "$%'"; 
    } 
    orgSql = String.valueOf(orgSql) + " order by orgIdString desc";
    DataSourceBase base = new DataSourceBase();
    ResultSet rSet = null;
    try {
      base.begin();
      rSet = base.executeQuery(orgSql);
      while (rSet.next())
        orgIdStr = String.valueOf(orgIdStr) + "*" + rSet.getString(1) + "*"; 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return orgIdStr;
  }
  
  public static List<String[]> haveSidelineOrg(String userId) {
    List<String[]> list = null;
    String sql = "SELECT e.sidelineOrg,o.org_id FROM org_employee e JOIN org_organization_user o ON e.emp_id=o.EMP_ID WHERE e.emp_id=" + 
      userId;
    DataSourceBase base = new DataSourceBase();
    ResultSet rSet = null;
    String flag = "";
    String mainOrgId = "";
    try {
      base.begin();
      rSet = base.executeQuery(sql);
      if (rSet.next()) {
        flag = (rSet.getString(1) == null) ? "" : rSet.getString(1).replace("**", ",").replace("*", "");
        mainOrgId = rSet.getString(2);
      } 
      rSet.close();
      if (flag != null && !flag.equalsIgnoreCase("null") && !flag.equals("")) {
        list = (List)new ArrayList<String>();
        sql = "SELECT org_id,orgNamestring FROM org_organization WHERE org_id=" + mainOrgId;
        rSet = base.executeQuery(sql);
        if (rSet.next()) {
          String[] orgInfo = { rSet.getString(1), rSet.getString(2) };
          list.add(orgInfo);
        } 
        rSet.close();
        sql = "SELECT org_id,orgNamestring FROM org_organization WHERE org_id IN (" + flag + ")";
        rSet = base.executeQuery(sql);
        while (rSet.next()) {
          String[] orgInfo = { rSet.getString(1), rSet.getString(2) };
          list.add(orgInfo);
        } 
        rSet.close();
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return list;
  }
  
  public static String getEmpNumberByEmpId(String empId) {
    String number = "";
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery("select empNumber from org_employee where emp_id=" + empId + " AND userIsActive=1 AND userIsDeleted=0");
      if (rs.next())
        number = (rs.getString(1) == null) ? "" : rs.getString(1); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return number;
  }
  
  public static String getZhiWuByEmpId(String empId) {
    String empduty = "";
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery("select empduty from org_employee where emp_id=" + empId + " AND userIsActive=1 AND userIsDeleted=0");
      if (rs.next())
        empduty = (rs.getString(1) == null) ? "" : rs.getString(1); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return empduty;
  }
  
  public static String getGangWeiByEmpId(String empId) {
    String empposition = "";
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery("select empposition from org_employee where emp_id=" + empId + " AND userIsActive=1 AND userIsDeleted=0");
      if (rs.next())
        empposition = (rs.getString(1) == null) ? "" : rs.getString(1); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return empposition;
  }
  
  public static List<String[]> getEmpInfoByEmpNumber(String empNumber) {
    empNumber = empNumber.replace(",", "','").replace("，", "','");
    String sql = "select emp_id,empName from org_employee where empNumber in ('" + empNumber + "') AND userIsActive=1 AND userIsDeleted=0";
    return (new DataSourceUtil()).getListQuery(sql, "");
  }
  
  public static String[] getEmpInfoByEmpId(String number) {
    String[] empInfo = new String[2];
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery("select emp_id,empName from org_employee where empNumber=" + number);
      if (rs.next()) {
        empInfo[0] = (rs.getString(1) == null) ? "" : rs.getString(1);
        empInfo[1] = (rs.getString(2) == null) ? "" : rs.getString(2);
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return empInfo;
  }
  
  public static String getUserAccountsByEmpId(String empId) {
    String number = "";
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery("select useraccounts from org_employee where emp_id=" + empId + " AND userIsActive=1 AND userIsDeleted=0");
      if (rs.next())
        number = (rs.getString(1) == null) ? "" : rs.getString(1); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return number;
  }
  
  public static String getUserAccountsByEmpIdStr(String empIds) throws SQLException {
    DataSourceBase base = new DataSourceBase();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rSet = null;
    String userAccounts = "";
    try {
      conn = base.getDataSource().getConnection();
      stmt = conn.createStatement();
      String sqlString = "select useraccounts,emp_id from org_employee where emp_id in (" + empIds + ") AND userIsActive=1 AND userIsDeleted=0 order by emp_id asc";
      rSet = stmt.executeQuery(sqlString);
      while (rSet.next())
        userAccounts = String.valueOf(userAccounts) + rSet.getString(1) + ","; 
      rSet.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      if (rSet != null)
        rSet.close(); 
      if (conn != null)
        conn.close(); 
    } 
    return userAccounts;
  }
  
  public static String getNamesByIds(String ids) {
    String names = "";
    ids = ids.replace("$$", ",").replace("$", "");
    if (!ids.equals("")) {
      String sql = "select distinct empName from org_employee where emp_id in (" + ids + ")";
      DataSourceBase base = new DataSourceBase();
      try {
        base.begin();
        ResultSet rs = base.executeQuery(sql);
        while (rs.next())
          names = String.valueOf(names) + ((rs.getString(1) == null) ? "" : (String.valueOf(rs.getString(1)) + ",")); 
        rs.close();
        base.end();
      } catch (Exception e) {
        base.end();
        e.printStackTrace();
      } 
    } 
    return names;
  }
  
  public static String getAccountByUserName(String userName) {
    if (userName != null && !userName.equals("")) {
      String useC = SystemCommon.getLoginField();
      if (!useC.equals("userAccounts") && !useC.equals("")) {
        String[] c = useC.split(",");
        String sql = "select userAccounts from org_employee where USERISACTIVE=1 and USERISDELETED=0 and userAccounts=? ";
        for (int i = 0; i < c.length; i++)
          sql = String.valueOf(sql) + "or " + c[i] + "=? "; 
        DataSourceBase base = new DataSourceBase();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
          conn = base.getDataSource().getConnection();
          pstmt = conn.prepareStatement(sql);
          pstmt.setString(1, userName);
          for (int j = 0; j < c.length; j++)
            pstmt.setString(j + 2, userName); 
          ResultSet rs = pstmt.executeQuery();
          if (rs.next())
            userName = (rs.getString(1) == null) ? userName : rs.getString(1); 
          rs.close();
          pstmt.close();
          conn.close();
        } catch (Exception e) {
          if (conn != null)
            try {
              conn.close();
            } catch (Exception err) {
              err.printStackTrace();
            }  
          e.printStackTrace();
        } 
      } 
    } 
    return userName;
  }
  
  public static String getOrgNameByEmpId(String empId) {
    String idString = "";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      String sql = "SELECT aa.ORG_ID,aa.ORGNAME,ee.empsex FROM ORG_ORGANIZATION_USER bb,ORG_ORGANIZATION aa,ORG_EMPLOYEE ee  where ee.emp_id=bb.emp_id and bb.org_id=aa.org_id  and ee.USERISACTIVE=1 and ee.USERISDELETED=0 and ee.emp_id=" + 
        
        empId;
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next())
        idString = String.valueOf(rs.getString(1)) + "," + rs.getString(2) + "," + rs.getString(3); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return idString;
  }
  
  public static String getOrgNameStringByEmpId(String empId) {
    String idString = "";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      String sql = "SELECT aa.ORGNAMESTRING FROM ORG_ORGANIZATION_USER bb,ORG_ORGANIZATION aa,ORG_EMPLOYEE ee  where ee.emp_id=bb.emp_id and bb.org_id=aa.org_id  and ee.USERISACTIVE=1 and ee.USERISDELETED=0 and ee.emp_id=" + 
        
        empId;
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next())
        idString = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return idString;
  }
  
  public static int getUserNum(String orgUserGroup) {
    int num = 0;
    String userId = orgUserGroup.contains("$") ? 
      orgUserGroup.substring(orgUserGroup.indexOf("$") + 1, orgUserGroup.lastIndexOf("$")).replace("$$", ",") : "";
    String orgId = orgUserGroup.contains("*") ? 
      orgUserGroup.substring(orgUserGroup.indexOf("*") + 1, orgUserGroup.lastIndexOf("*")).replace("**", ",") : "";
    String groupId = orgUserGroup.contains("@") ? 
      orgUserGroup.substring(orgUserGroup.indexOf("@") + 1, orgUserGroup.lastIndexOf("@")).replace("@@", ",") : "";
    DataSourceBase base = new DataSourceBase();
    userId = "".equals(userId) ? "," : ("," + userId + ",");
    try {
      base.begin();
      ResultSet rs = null;
      if (!"".equals(orgId)) {
        rs = base.executeQuery("SELECT emp_id FROM org_organization_user WHERE org_id in (" + orgId + ")");
        while (rs.next()) {
          if (!userId.contains("," + rs.getString(1) + ","))
            userId = String.valueOf(userId) + rs.getString(1) + ","; 
        } 
      } 
      if (!"".equals(groupId)) {
        rs = base.executeQuery("SELECT emp_id FROM org_user_group WHERE group_id in (" + orgId + ")");
        while (rs.next()) {
          if (!userId.contains("," + rs.getString(1) + ","))
            userId = String.valueOf(userId) + rs.getString(1) + ","; 
        } 
      } 
      num = (userId.substring(1, userId.length() - 1).split(",")).length;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return num;
  }
  
  public static int filterSQL(String sql, String para) {
    int sqlnum = 0;
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      String[] paraArr = para.split(",");
      for (int i = 0; i < paraArr.length; i++) {
        if (!ParameterFilter.checkParameter(paraArr[i]))
          return 0; 
      } 
      String sqltemp = sql.toLowerCase().trim();
      if (sqltemp.startsWith("select")) {
        DataSourceBase base = new DataSourceBase();
        conn = base.getDataSource().getConnection();
        sqltemp = sql.substring(0, sqltemp.indexOf(" from ") + 6);
        String sqlStr = "select count(page_id) from tpage where js_beforecommit like ? or js_onload like ?";
        pstmt = conn.prepareStatement(sqlStr);
        pstmt.setString(1, "%" + sqltemp + "%");
        pstmt.setString(2, "%" + sqltemp + "%");
        ResultSet rs = pstmt.executeQuery();
        if (rs.next())
          sqlnum = rs.getInt(1); 
        rs.close();
        if (sqlnum == 0) {
          sqlStr = "select count(wf_activity_id) from jsf_activity where exe_script like ? or beforesubmit like ?";
          pstmt = conn.prepareStatement(sqlStr);
          pstmt.setString(1, "%" + sqltemp + "%");
          pstmt.setString(2, "%" + sqltemp + "%");
          rs = pstmt.executeQuery();
          if (rs.next())
            sqlnum = rs.getInt(1); 
          rs.close();
        } 
        pstmt.close();
        conn.close();
      } 
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return sqlnum;
  }
}
