package com.js.wap.util;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.StringSplit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;

public class WapUtil {
  public static final String EMP_ID = "userId";
  
  public static final String EMP_PWD = "userPassword";
  
  public static final String EMP_NAME = "userName";
  
  public static final String USER_ACCOUNT = "userAccount";
  
  public static final String ORG_NAME = "orgName";
  
  public static final String EMP_ORG_ID = "orgId";
  
  public static final String ORG_ID_STRING = "orgIdString";
  
  public static final String DUTY_NAME = "dutyName";
  
  public static final String DUTY_LEVEL = "dutyLevel";
  
  public static final String SHOW_LIST = "SHOW_LIST";
  
  public static final int LIMITED = SystemCommon.getPageNum();
  
  public static final int LIMIT = SystemCommon.getForumNum();
  
  public static final String RECORD_COUNT = "RECORD_COUNT";
  
  public static final String QUERY_LIST = "QUERY_LIST";
  
  public static final String WAP_VERSION = "wapVersion";
  
  public static final String WAP_VER_3G = "3G";
  
  public static final String WAP_VER_COLOR = "COLOR";
  
  public static Map getUserInfoByLogonName(String name) {
    Map<Object, Object> infoMap = new HashMap<Object, Object>();
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      PreparedStatement pstmt = null;
      conn = ds.getConnection();
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        pstmt = conn.prepareStatement("select emp_id,userpassword,empname,userissuper,usersuperbegin,usersuperend,domain_id,browseRange,initPassword from org_employee where userisactive=1 and userisdeleted=0 and binary useraccounts=?");
      } else {
        pstmt = conn.prepareStatement("select emp_id,userpassword,empname,userissuper,usersuperbegin,usersuperend,domain_id,browseRange,initPassword from org_employee where userisactive=1 and userisdeleted=0 and useraccounts=?");
      } 
      pstmt.setString(1, name.trim());
      rs = pstmt.executeQuery();
      String emp_id = null;
      String org_id = null;
      String orgname = null;
      if (rs.next()) {
        emp_id = String.valueOf(rs.getLong("emp_id"));
        infoMap.put("userId", emp_id);
        infoMap.put("userPassword", rs.getString("userpassword"));
        infoMap.put("userName", rs.getString("empname"));
        infoMap.put("userissuper", rs.getString("userissuper"));
        infoMap.put("usersuperbegin", rs.getDate("usersuperbegin"));
        infoMap.put("usersuperend", rs.getDate("usersuperend"));
        infoMap.put("userAccount", name.trim());
        infoMap.put("domainId", rs.getString("domain_id"));
        infoMap.put("browseRange", rs.getString("browseRange"));
        infoMap.put("initpassword", rs.getString("initPassword"));
      } 
      pstmt.close();
      stmt = conn.createStatement();
      if (emp_id != null && !emp_id.equals("null") && !emp_id.equals("")) {
        rs = stmt.executeQuery(" select u.org_id,o.orgname from org_organization_user u,org_organization o where u.emp_id=" + emp_id + " and u.org_id=o.org_id");
        if (rs.next()) {
          org_id = String.valueOf(rs.getLong("org_id"));
          orgname = String.valueOf(rs.getString("orgname"));
          infoMap.put("orgId", org_id);
          infoMap.put("orgName", orgname);
        } 
      } 
      if (org_id != null && !org_id.equals("null") && !org_id.equals("")) {
        rs = stmt.executeQuery(" select orgidstring,orgnamestring,orgparentorgid from org_organization where org_id=" + org_id);
        if (rs.next()) {
          String orgIdString = rs.getString("orgidstring");
          infoMap.put("orgIdString", StringSplit.splitOrgIdString(orgIdString, "$", "_"));
          infoMap.put("orgNameString", rs.getString(2));
          String[] corpS = orgIdString.split("\\$");
          infoMap.put("corpId", corpS[3]);
        } 
      } 
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
      ex.printStackTrace();
    } 
    return infoMap;
  }
  
  public boolean ifIP(String userIP) {
    Boolean flag = Boolean.valueOf(false);
    Calendar date = 
      Calendar.getInstance();
    String today = String.valueOf(date.get(1)) + "/" + (
      date.get(2) + 1) + "/" + 
      date.get(5);
    String[] ipAddr = userIP.split("\\.");
    StringBuffer ip = new StringBuffer(16);
    for (int i = 0; i < 4; i++) {
      int len = 3 - ipAddr[i].length();
      while (len > 0) {
        ip.append("0");
        len--;
      } 
      ip.append(ipAddr[i]).append(".");
    } 
    userIP = ip.toString().substring(0, 15);
    String queryString = "";
    String databaseType = 
      SystemCommon.getDatabaseType();
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      if (databaseType.indexOf("mysql") >= 0) {
        queryString = "SELECT ipid FROM SEC_IP WHERE domain_id=0 and ipisopen=1 AND ('" + 
          
          today + 
          "'>=ipopenbegintime AND '" + 
          today + 
          "'<=ipOpenEndTime AND (ipAddressBegin='" + 
          userIP + "' OR '" + userIP + 
          "' BETWEEN ipAddressBegin AND ipAddressEnd))";
      } else {
        queryString = "SELECT IPID FROM SEC_IP WHERE domain_id=0 and ipIsOpen=1 AND to_date('" + 
          today + "','yyyy-mm-dd')" + 
          ">=ipOpenBeginTime AND to_date('" + today + "','yyyy-mm-dd') " + 
          "<=ipOpenEndTime AND (ipAddressBegin='" + 
          userIP + "' OR ( '" + userIP + 
          "' BETWEEN ipAddressBegin AND ipAddressEnd))";
      } 
      rs = stmt.executeQuery(queryString);
      if (rs.next()) {
        flag = Boolean.valueOf(true);
      } else {
        flag = Boolean.valueOf(false);
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return flag.booleanValue();
  }
  
  public static Map getInfoById(String id) {
    String con = "";
    String sendUser = "";
    String sendTime = "";
    String title = "";
    List<Map<Object, Object>> filelist = new ArrayList();
    String accname = "";
    String accsave = "";
    String orgName = "";
    Map<Object, Object> map = new HashMap<Object, Object>();
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    ResultSet frs = null;
    try {
      Long infoId = Long.valueOf(id);
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(" select info.informationcontent,info.informationissuer,info.informationissuetime,info.informationtitle,info.INFORMATIONISSUEORG from oa_information info where info.information_id=" + 
          infoId.toString());
      if (rs.next()) {
        con = rs.getString(1);
        sendUser = rs.getString(2);
        sendTime = rs.getString(3);
        title = rs.getString(4);
        orgName = rs.getString(5);
      } 
      frs = stmt.executeQuery("select acc.accessoryname,acc.accessorysavename from oa_informationaccessory acc where acc.information_id=" + infoId.toString());
      while (frs.next()) {
        Map<Object, Object> filemap = new HashMap<Object, Object>();
        accname = frs.getString(1);
        accsave = frs.getString(2);
        filemap.put("accname", frs.getString(1));
        filemap.put("accsave", frs.getString(2));
        filelist.add(filemap);
      } 
      map.put("content", con);
      map.put("sendUser", sendUser);
      map.put("sendTime", sendTime);
      map.put("title", title);
      map.put("accname", accname);
      map.put("accsave", accsave);
      map.put("filelist", filelist);
      map.put("orgName", orgName);
      rs.close();
      frs.close();
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
    return map;
  }
  
  public static String getRoomInfoById(String id) {
    String con = "";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(" select name,location from oa_boardroom where BOARDROOMID=" + id);
      if (rs.next())
        con = String.valueOf(rs.getString(2)) + "-" + rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return con;
  }
  
  public String userCanLogMobile(String userAccount) {
    String can = "-1";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      PreparedStatement pstmt = conn.prepareStatement("select mailpost from org_employee where useraccounts=? and userisactive=1 and userisdeleted=0");
      pstmt.setString(1, userAccount);
      rs = pstmt.executeQuery();
      if (rs.next())
        can = rs.getString(1); 
      rs.close();
      pstmt.close();
      stmt = conn.createStatement();
      if ("1".equals(can)) {
        rs = stmt.executeQuery("select count(emp_id) from org_employee where mailpost='1' and userisdeleted=0");
        if (rs.next()) {
          int num = rs.getInt(1);
          if (num > Integer.parseInt(SystemCommon.getMobileLogonNum()))
            can = "9"; 
        } 
        rs.close();
      } 
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return can;
  }
  
  public static String delRepeatUserId(String str) {
    Set<String> set = new HashSet<String>();
    String[] strs = str.split(",");
    StringBuffer retStr = new StringBuffer();
    byte b;
    int i;
    String[] arrayOfString1;
    for (i = (arrayOfString1 = strs).length, b = 0; b < i; ) {
      String s = arrayOfString1[b];
      if (s != null && !"".equals(s) && !"null".equals(s))
        set.add(s); 
      b++;
    } 
    for (String rs : set)
      retStr.append("," + rs); 
    return retStr.substring(1);
  }
}
