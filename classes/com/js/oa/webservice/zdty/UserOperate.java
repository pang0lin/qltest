package com.js.oa.webservice.zdty;

import com.js.util.util.DataSourceBase;
import com.js.util.util.IO2File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserOperate {
  public boolean saveUsers() {
    boolean result = true;
    List<UserPO> users = queryUsers();
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    int n = 0;
    try {
      IO2File.printFile("获取用户信息数量：" + users.size(), "用户同步");
      if (users.size() > 0) {
        base.begin();
        for (UserPO user : users) {
          IO2File.printFile("\n\r*****************************************\n\r", "用户同步");
          if (user.getUserName() == null || "".equals(user.getUserName())) {
            IO2File.printFile("用户名为空。", "用户同步");
            result = false;
            break;
          } 
          if (user.getOrgId() == null || "".equals(user.getOrgId())) {
            IO2File.printFile("所属组织为空。", "用户同步");
            result = false;
            break;
          } 
          if (user.getUserAccount() == null || "".equals(user.getUserAccount())) {
            IO2File.printFile("用户帐号为空。", "用户同步");
            result = false;
            break;
          } 
          String sql = "select org_id from org_organization where orgserial='" + user.getOrgId() + "' and orgstatus<>4";
          IO2File.printFile("查询用户部门：" + sql, "用户同步");
          rs = base.executeQuery(sql);
          if (rs.next()) {
            user.setOrgId(rs.getString("org_id"));
            sql = "select emp_id from org_employee where (useraccounts='" + user.getUserAccount() + "' or empnumber='" + 
              user.getUserNumber() + "') and userisactive=1 and USERISDELETED=0";
            IO2File.printFile("判断用户是否存在：" + sql, "用户同步");
            rs = base.executeQuery(sql);
            if (rs.next()) {
              user.setEmpId(rs.getString("emp_id"));
              n = updateUser(user, base);
            } else {
              n = insertUser(user, base);
            } 
            if (n == 0) {
              IO2File.printFile("数据保存失败。", "用户同步");
              result = false;
              break;
            } 
            continue;
          } 
          IO2File.printFile("用户所属组织不存在。", "用户同步");
          result = false;
          break;
        } 
      } 
    } catch (Exception e) {
      e.getStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        base.end();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
      IO2File.printFile("同步用户完成：" + result, "用户同步");
    } 
    return result;
  }
  
  private int updateUser(UserPO user, DataSourceBase base) {
    int n = 0;
    String sql = "update org_employee set EMPNAME='" + user.getUserName() + "'," + 
      "EMPBIRTH=to_date('" + user.getUserBirth() + "','yyyy-MM-dd HH24:mi:ss'),EMPSEX=" + user.getUserSex() + ",EMPNATION='" + user.getUserNation() + "'," + 
      "EMPPHONE='" + user.getUserPhone() + "',EMPMOBILEPHONE='" + user.getUserMobilePhone() + "',EmpStatus=0," + 
      "EMPBUSINESSPHONE='" + user.getUserBusinessPhone() + "',EMPEMAIL='" + user.getUserEmail() + "'," + 
      "EMPIDCARD='" + user.getUserCardNO() + "',empposition='" + user.getUserPosition() + "',empnativeplace='" + user.getUserNativePlace() + "' " + 
      " where emp_id=" + user.getEmpId();
    n = base.executeUpdate(sql);
    if (n > 0)
      base.executeUpdate("update org_organization_user set org_id=" + user.getOrgId() + " where emp_id=" + user.getEmpId()); 
    IO2File.printFile("更新用户信息：：" + n + ":" + sql, "用户同步");
    return n;
  }
  
  private int insertUser(UserPO user, DataSourceBase base) {
    String empId = "0";
    int n = 0;
    try {
      ResultSet rs = base.executeQuery("select hibernate_sequence.nextval from dual");
      if (rs.next())
        empId = rs.getString(1); 
      if (rs != null)
        rs.close(); 
      String sql = "INSERT INTO org_employee (emp_id,EMPNAME,EMPNUMBER,EMPBIRTH,EMPSEX,EMPNATION,EMPISMARRIAGE,EMPPHONE,EMPMOBILEPHONE,EMPBUSINESSPHONE,EMPEMAIL,EMPHEIGHT,EMPWEIGHT,EMPIDCARD,empposition,useraccounts,userpassword,USERISACTIVE,USERISDELETED,USERISFORMALUSER,USERISSUPER,USERSUPERBEGIN,USERSUPEREND,USERORDERCODE,CREATEDORG,BROWSERANGE,BROWSERANGENAME,KEYVALIDATE,KEYSERIAL,MAILPOST,DOMAIN_ID,SKIN,MAILBOXSIZE,NETDISKSIZE,sidelineorg,sidelineorgname,EMPDUTYLEVEL,userOnline,deptleader,empnativeplace,EmpStatus)VALUES(" + 






        
        empId + ",'" + user.getUserName() + "','" + user.getUserNumber() + "',to_date('" + user.getUserBirth() + "','yyyy-MM-dd HH24:mi:ss')," + user.getUserSex() + ",'" + user.getUserNation() + "'," + 
        "'0','" + user.getUserPhone() + "','" + user.getUserMobilePhone() + "','" + user.getUserBusinessPhone() + "','" + user.getUserEmail() + "'," + 
        "0,0,'" + user.getUserCardNO() + "','" + user.getUserPosition() + "','" + user.getUserAccount() + "'," + 
        "'5EB72296E792C92A549DD5A330112Y218965','1','0','1','1'," + 
        "to_date('2015-01-01 00:00:00','yyyy-MM-dd HH24:mi:ss'),to_date('2050-01-01 00:00:00','yyyy-MM-dd HH24:mi:ss'),'1000','0','0'," + 
        "'','0','','0','0'," + 
        "'blue','1024','1024','',''," + 
        "'1000','0','','" + user.getUserNativePlace() + "',0)";
      n = base.executeUpdate(sql);
      if (n > 0)
        base.executeUpdate("insert into org_organization_user values (" + user.getOrgId() + "," + empId + ")"); 
      IO2File.printFile("新增用户信息：：" + n + ":" + sql, "用户同步");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return n;
  }
  
  private List<UserPO> queryUsers() {
    String databaseName = "zdty";
    String getOrganizationSQL = "select * from V_BRW_Teacher where isnull(sfwp,'1')<>'0'";
    String now = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<UserPO> users = new ArrayList<UserPO>();
    UserPO user = null;
    int count = 0;
    try {
      DataSourceBase base = new DataSourceBase();
      conn = base.getDataSource(databaseName).getConnection();
      IO2File.printFile("查询老师数量：" + getOrganizationSQL + "\n", "用户同步");
      ps = conn.prepareStatement(getOrganizationSQL);
      rs = ps.executeQuery();
      while (rs.next()) {
        try {
          if (rs.getString("SSBM_USERDM") == null) {
            IO2File.printFile("所属部门为空--" + rs.getString("gh") + "--" + rs.getString("xm") + "\n", "用户同步");
            continue;
          } 
          user = new UserPO();
          IO2File.printFile(String.valueOf(count) + "--" + rs.getString("gh") + "--" + rs.getString("xm") + "\n", "用户同步");
          user.setUserAccount(rs.getString("gh"));
          user.setUserNumber(rs.getString("gh"));
          user.setUserName(rs.getString("xm"));
          user.setUserSex("1".equals(rs.getString("xb")) ? "0" : "1");
          user.setUserBirth(rs.getString("csrq"));
          if (user.getUserBirth() == null || "".equals(user.getUserBirth()) || "null".equalsIgnoreCase(user.getUserBirth())) {
            user.setUserBirth(now);
          } else if (user.getUserBirth().endsWith(".0")) {
            user.setUserBirth(user.getUserBirth().substring(0, user.getUserBirth().length() - 2));
          } 
          user.setUserNation(checkString(rs.getString("mz")));
          user.setUserNativePlace(checkString(rs.getString("jg")));
          user.setUserCardNO(checkString(rs.getString("sfzh")));
          user.setUserPosition(checkString(rs.getString("szgw")));
          user.setUserPhone(checkString(rs.getString("lxdh")));
          user.setUserEmail(checkString(rs.getString("email")));
          user.setUserMobilePhone(checkString(rs.getString("mobile")));
          user.setOrgId(rs.getString("SSBM_USERDM"));
          user.setOrgId(user.getOrgId().trim());
          users.add(user);
          count++;
        } catch (Exception e) {
          e.getStackTrace();
          IO2File.printFile("出现异常：" + e.getStackTrace(), "用户同步");
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        IO2File.printFile("查询：" + count + ", 封装：" + users.size(), "用户同步");
        if (rs != null)
          rs.close(); 
        if (ps != null)
          ps.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e1) {
        e1.getStackTrace();
      } 
    } 
    return users;
  }
  
  private String checkString(String s) {
    if (s == null || "".equals(s) || "null".equalsIgnoreCase(s))
      return ""; 
    return s;
  }
}
