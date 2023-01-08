package net.jiusi.jsoa.service.dao;

import com.js.util.config.SystemCommon;
import com.js.util.util.MD5;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.jiusi.jsoa.service.IDBConnection;
import net.jiusi.jsoa.service.impl.DBConnectionImpl;
import net.jiusi.jsoa.service.pojo.EmployeePojo;

public class EmployeeDao {
  private IDBConnection dbc = new DBConnectionImpl();
  
  public int modifyEmployeeStatus(String account, String active, String delete) {
    int reValue = 0;
    Connection conn = this.dbc.getConnection();
    PreparedStatement pstmt = null;
    String sql = "update org_employee set USERISACTIVE=?,USERISDELETEd=? where USERISDELETEd=0 and USERACCOUNTS=?";
    try {
      conn.setAutoCommit(false);
      if ("1".equals(delete)) {
        String databaseType = SystemCommon.getDatabaseType();
        String sql1 = "";
        if (databaseType.indexOf("mysql") >= 0) {
          System.out.println("deletesql:delete oou from org_organization_user oou where oou.EMP_ID=(select emp.EMP_ID from org_employee emp where emp.USERISDELETEd=0 and emp.USERACCOUNTS='" + 
              account + "' limit 1)");
          sql1 = "delete oou from org_organization_user oou where oou.EMP_ID=(select emp.EMP_ID from org_employee emp where emp.USERISDELETEd=0 and emp.USERACCOUNTS=? limit 1)";
        } else if (databaseType.indexOf("oracle") >= 0) {
          sql1 = "delete org_organization_user oou where oou.EMP_ID=(select emp.EMP_ID from org_employee emp where emp.USERISDELETEd=0 and emp.USERACCOUNTS=? and rownum<=1)";
        } 
        pstmt = conn.prepareStatement(sql1);
        pstmt.setString(1, account);
        pstmt.executeUpdate();
      } 
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, active);
      pstmt.setString(2, delete);
      pstmt.setString(3, account);
      reValue = pstmt.executeUpdate();
      conn.commit();
    } catch (SQLException e) {
      e.printStackTrace();
      try {
        conn.rollback();
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
    } finally {
      this.dbc.close(null, pstmt, null, conn);
    } 
    return reValue;
  }
  
  public String addEmployee(EmployeePojo employee) {
    String reValue = "-1";
    Connection conn = this.dbc.getConnection();
    Statement stmt = null;
    ResultSet rs = null;
    String databaseType = SystemCommon.getDatabaseType();
    String ipContrl = employee.getIpContrl();
    String ipContrlBeginTime = employee.getIpContrlBeginTime();
    String ipContrlEndTime = employee.getIpContrlEndTime();
    String empLeader = employee.getEmpLeader();
    String empLeaderName = null;
    if ("0".equals(ipContrl)) {
      if (databaseType.indexOf("mysql") >= 0) {
        ipContrlBeginTime = "now()";
        ipContrlEndTime = "now()";
      } else if (databaseType.indexOf("oracle") >= 0) {
        ipContrlBeginTime = "sysdate";
        ipContrlEndTime = "sysdate";
      } 
    } else {
      if (!checkDate(ipContrlBeginTime) || !checkDate(ipContrlEndTime)) {
        ipContrl = "0";
        return "4";
      } 
      ipContrl = "1";
      if (databaseType.indexOf("oracle") >= 0) {
        ipContrlBeginTime = "to_date(" + ipContrlBeginTime + ",'yyyy-mm-dd hh24:mi:ss')";
        ipContrlEndTime = "to_date(" + ipContrlEndTime + ",'yyyy-mm-dd hh24:mi:ss')";
      } 
      if (databaseType.indexOf("mysql") >= 0) {
        ipContrlBeginTime = "'" + ipContrlBeginTime + "'";
        ipContrlEndTime = "'" + ipContrlEndTime + "'";
      } 
    } 
    try {
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      String account = employee.getUserAccounts();
      if (account == null || "".equals(account))
        return "1"; 
      System.out.println("******************************");
      System.out.println("sql:select EMP_ID from org_employee where USERISDELETED=0 and USERACCOUNTS='" + account + "'");
      rs = stmt.executeQuery("select EMP_ID from org_employee where USERISDELETED=0 and USERACCOUNTS='" + account + "'");
      if (rs.next())
        return "2"; 
      if (empLeader != null && !"".equals(empLeader)) {
        rs = stmt.executeQuery("select EMP_ID,EMPNAME from org_employee where USERISDELETED=0 and USERACCOUNTS='" + empLeader + "'");
        if (rs.next()) {
          empLeaderName = rs.getString("EMPNAME");
          empLeader = "$" + rs.getString("EMP_ID") + "$";
        } else {
          return "5";
        } 
      } 
      rs.close();
      rs = null;
      long org_id = 0L;
      rs = stmt.executeQuery("select ORG_ID from org_organization where ORGSTATUS in(0,1) and ORGSERIAL='" + employee.getOrgSerial() + "'");
      if (rs.next())
        org_id = rs.getLong(1); 
      if (org_id == 0L)
        return "3"; 
      String sql = null;
      if (databaseType.indexOf("mysql") >= 0) {
        sql = "insert into org_employee(EMPNAME,EMPSEX,USERACCOUNTS,USERPASSWORD,USERSIMPLENAME,USERSUPERBEGIN,USERSUPEREND,EMPISMARRIAGE,EMPHEIGHT,EMPWEIGHT,EMPSTATUS,USERISACTIVE,USERISDELETED,USERISFORMALUSER,USERISSUPER,USERORDERCODE,CREATEDORG,BROWSERANGE,BROWSERANGENAME,KEYVALIDATE,DOMAIN_ID,SKIN,MAILBOXSIZE,SIDELINEORG,SIDELINEORGNAME,EMPDUTYLEVEL,SIGNATUREIMGNAME,SIGNATUREIMGSAVENAME,USERONLINE,DEPTLEADER,EMPLEADERNAME,EMPLEADERID,empNumber) values('" + 

          
          employee.getEmpName() + "'," + employee.getEmpSex() + ",'" + employee.getUserAccounts() + 
          "','" + (new MD5()).getMD5Code(employee.getUserPassword()) + "','" + employee.getEmpSimpleName() + "'," + ipContrlBeginTime + "," + ipContrlEndTime + 
          ",0,0,0,0,1,0,1," + ipContrl + ",1000,0,'','',0,0,'blue',100,'','',-1,'','',0,0,'" + empLeaderName + "','" + empLeader + "','" + employee.getEmpnumber() + "')";
      } else if (databaseType.indexOf("oracle") >= 0) {
        sql = "insert into org_employee(EMP_ID,EMPNAME,EMPSEX,USERACCOUNTS,USERPASSWORD,USERSIMPLENAME,USERSUPERBEGIN,USERSUPEREND,EMPISMARRIAGE,EMPHEIGHT,EMPWEIGHT,EMPSTATUS,USERISACTIVE,USERISDELETED,USERISFORMALUSER,USERISSUPER,USERORDERCODE,CREATEDORG,BROWSERANGE,BROWSERANGENAME,KEYVALIDATE,DOMAIN_ID,SKIN,MAILBOXSIZE,SIDELINEORG,SIDELINEORGNAME,EMPDUTYLEVEL,SIGNATUREIMGNAME,SIGNATUREIMGSAVENAME,USERONLINE,DEPTLEADER,EMPLEADERNAME,EMPLEADERID,empNumber) values(HIBERNATE_SEQUENCE.nextval,'" + 

          
          employee.getEmpName() + "'," + employee.getEmpSex() + ",'" + employee.getUserAccounts() + 
          "','" + (new MD5()).getMD5Code(employee.getUserPassword()) + "','" + employee.getEmpSimpleName() + "'," + ipContrlBeginTime + "," + ipContrlEndTime + 
          ",0,0,0,0,1,0,1," + ipContrl + ",1000,0,'','',0,0,'blue',100,'','',-1,'','',0,0,'" + empLeaderName + "','" + empLeader + "','" + employee.getEmpnumber() + "')";
      } 
      String sql1 = null;
      if (databaseType.indexOf("oracle") >= 0) {
        sql1 = "insert into org_organization_user(ORG_ID,EMP_ID) values(" + org_id + "," + 
          "(select emp.EMP_ID from org_employee emp where emp.USERISDELETED=0 and emp.USERACCOUNTS='" + employee.getUserAccounts() + "' and rownum<=1))";
      } else if (databaseType.indexOf("mysql") >= 0) {
        sql1 = "insert into org_organization_user(ORG_ID,EMP_ID) values(" + org_id + "," + 
          "(select emp.EMP_ID from org_employee emp where emp.USERISDELETED=0 and emp.USERACCOUNTS='" + employee.getUserAccounts() + "' limit 1))";
      } 
      stmt.execute(sql);
      stmt.execute(sql1);
      conn.commit();
      reValue = "0";
    } catch (SQLException e) {
      e.printStackTrace();
      try {
        conn.rollback();
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
    } finally {
      this.dbc.close(rs, null, stmt, conn);
    } 
    return reValue;
  }
  
  public String modifyEmployeeInfo(EmployeePojo employee) {
    String reValue = "-1";
    if (employee == null)
      return "1"; 
    String account = employee.getUserAccounts();
    if (account == null || "".equals(account))
      return "1"; 
    Connection conn = this.dbc.getConnection();
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn.setAutoCommit(false);
      stmt = conn.createStatement();
      String databaseType = SystemCommon.getDatabaseType();
      String ipContrl = employee.getIpContrl();
      String ipContrlBeginTime = employee.getIpContrlBeginTime();
      String ipContrlEndTime = employee.getIpContrlEndTime();
      String empLeader = employee.getEmpLeader();
      String empLeaderName = null;
      if (ipContrl != null && !"".equals(ipContrl))
        if ("0".equals(ipContrl)) {
          if (databaseType.indexOf("mysql") >= 0) {
            ipContrlBeginTime = "now()";
            ipContrlEndTime = "now()";
          } else if (databaseType.indexOf("oracle") >= 0) {
            ipContrlBeginTime = "sysdate";
            ipContrlEndTime = "sysdate";
          } 
        } else {
          if (!checkDate(ipContrlBeginTime) || !checkDate(ipContrlEndTime)) {
            ipContrl = "0";
            return "4";
          } 
          ipContrl = "1";
          if (databaseType.indexOf("oracle") >= 0) {
            ipContrlBeginTime = "to_date(" + ipContrlBeginTime + ",'yyyy-mm-dd hh24:mi:ss')";
            ipContrlEndTime = "to_date(" + ipContrlEndTime + ",'yyyy-mm-dd hh24:mi:ss')";
          } 
          if (databaseType.indexOf("mysql") >= 0) {
            ipContrlBeginTime = "'" + ipContrlBeginTime + "'";
            ipContrlEndTime = "'" + ipContrlEndTime + "'";
          } 
        }  
      long org_id = 0L;
      System.out.println("orgSerial:" + employee.getOrgSerial());
      System.out.println("sql:select ORG_ID from org_organization where ORGSTATUS in(0,1) and ORGSERIAL='" + employee.getOrgSerial() + "'");
      String orgSerial = employee.getOrgSerial();
      if (orgSerial != null && !"".equals(orgSerial)) {
        rs = stmt.executeQuery("select ORG_ID from org_organization where ORGSTATUS in(0,1) and ORGSERIAL='" + employee.getOrgSerial() + "'");
        if (rs.next())
          org_id = rs.getLong(1); 
        if (org_id == 0L)
          return "2"; 
      } 
      if (empLeader != null && !"".equals(empLeader)) {
        rs = stmt.executeQuery("select EMP_ID,EMPNAME from org_employee where USERISDELETED=0 and USERACCOUNTS='" + empLeader + "'");
        if (rs.next()) {
          empLeaderName = rs.getString("EMPNAME");
        } else {
          return "4";
        } 
      } 
      StringBuffer sqlsb = new StringBuffer("update org_employee set USERACCOUNTS='" + account + "'");
      String empName = employee.getEmpName();
      if (empName != null && !"".equals(empName))
        sqlsb.append(", EMPNAME='" + empName + "'"); 
      String empSex = employee.getEmpSex();
      if (empSex != null && !"".equals(empSex))
        sqlsb.append(", EMPSEX=" + empSex); 
      String empEngName = employee.getEmpSimpleName();
      if (empEngName != null && !"".equals(empEngName))
        sqlsb.append(", EMPENGLISHNAME='" + empEngName + "'"); 
      String password = employee.getUserPassword();
      if (password != null && !"".equals(password)) {
        password = (new MD5()).getMD5Code(password);
        sqlsb.append(", USERPASSWORD='" + password + "'");
      } 
      if (ipContrl != null && !"".equals(ipContrl))
        sqlsb.append(", USERISSUPER='" + ipContrl + "'"); 
      if (ipContrlBeginTime != null && !"".equals(ipContrlBeginTime))
        sqlsb.append(", USERSUPERBEGIN=" + ipContrlBeginTime); 
      if (ipContrlEndTime != null && !"".equals(ipContrlEndTime))
        sqlsb.append(", USERSUPEREND=" + ipContrlEndTime); 
      if (empLeaderName != null && !"".equals(empLeaderName))
        sqlsb.append(", EMPLEADERNAME='" + empLeaderName + "'"); 
      if (empLeader != null && !"".equals(empLeader))
        sqlsb.append(", EMPLEADERID='" + empLeader + "'"); 
      String empNumber = employee.getEmpnumber();
      if (empNumber != null && !"".equals(empNumber))
        sqlsb.append(", empNumber='" + empNumber + "'"); 
      sqlsb.append(" where USERISDELETED=0 and USERACCOUNTS='" + account + "'");
      if (orgSerial != null && !"".equals(orgSerial))
        if (databaseType.indexOf("mysql") >= 0) {
          System.out.println("sql:update org_organization_user oou set oou.ORG_ID=" + org_id + 
              " where oou.EMP_ID=(select emp.EMP_ID from org_employee emp where emp.USERISDELETED=0 and emp.USERACCOUNTS='" + account + "' limit 1)");
          stmt.execute("update org_organization_user oou set oou.ORG_ID=" + org_id + 
              " where oou.EMP_ID=(select emp.EMP_ID from org_employee emp where emp.USERISDELETED=0 and emp.USERACCOUNTS='" + account + "' limit 1)");
        } else if (databaseType.indexOf("oracle") >= 0) {
          stmt.execute("update org_organization_user oou set oou.ORG_ID=" + org_id + 
              " where oou.EMP_ID=(select emp.EMP_ID from org_employee emp where emp.USERISDELETED=0 and emp.USERACCOUNTS='" + account + "' and rownum<=1)");
        }  
      stmt.execute(sqlsb.toString());
      conn.commit();
      reValue = "0";
    } catch (SQLException e) {
      e.printStackTrace();
      try {
        conn.rollback();
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
    } catch (Exception e1) {
      e1.printStackTrace();
    } finally {
      this.dbc.close(rs, null, stmt, conn);
    } 
    return reValue;
  }
  
  public boolean checkDate(String str) {
    try {
      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date date = formatter.parse(str);
      return str.equals(formatter.format(date));
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } 
  }
  
  public String queryUserInfo(String userAccount) {
    StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs2 = null;
    ResultSet rs = null;
    ResultSet rs1 = null;
    try {
      conn = this.dbc.getConnection();
      String sql = "select OE.EMP_ID,oe.empname,oe.empnumber,oe.empbirth,oe.empsex,oe.empnation,oe.empismarriage,oe.empphone,oe.empmobilephone,oe.empbusinessphone,oe.empemail,oe.empposition,oe.empduty,oe.empdutylevel,oro.ORGNAME,oro.ORG_ID,oro.ORGSERIAL from org_employee oe,ORG_ORGANIZATION oro,ORG_ORGANIZATION_USER oru where oe.emp_id = oru.EMP_ID and oro.org_id = oru.org_id and useraccounts=?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, userAccount);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        xml.append("<employee>");
        if (rs.getString("emp_id") == null) {
          xml.append("<empId></empId>");
        } else {
          xml.append("<empId>" + rs.getString("emp_id") + "</empId>");
        } 
        if (rs.getString("empname") == null) {
          xml.append("<empName></empName>");
        } else {
          xml.append("<empName>" + rs.getString("empname") + "</empName>");
        } 
        if (rs.getString("empnumber") == null) {
          xml.append("<empNumber></empNumber>");
        } else {
          xml.append("<empNumber>" + rs.getString("empnumber") + "</empNumber>");
        } 
        SimpleDateFormat empbirth = new SimpleDateFormat("yyyy-MM-dd");
        if (rs.getString("empbirth") == null) {
          xml.append("<empBirth></empBirth>");
        } else {
          empbirth.format((new SimpleDateFormat("yyyy-MM-dd")).parse(rs.getString("empbirth")));
          xml.append("<empBirth>empbirth</empBirth>");
        } 
        if (rs.getString("empsex").equals("0")) {
          xml.append("<empGander>男</empGander>");
        } else {
          xml.append("<empGander>女</empGander>");
        } 
        if (rs.getString("empnation") == null) {
          xml.append("<empNation></empNation>");
        } else {
          xml.append("<empNation>" + rs.getString("empnation") + "</empNation>");
        } 
        if (rs.getString("empismarriage") == null) {
          xml.append("<empIsmarriage></empIsmarriage>");
        } else {
          xml.append("<empIsmarriage>" + rs.getString("empismarriage") + "</empIsmarriage>");
        } 
        if (rs.getString("empphone") == null) {
          xml.append("<empPhone></empPhone>");
        } else {
          xml.append("<empPhone>" + rs.getString("empphone") + "</empPhone>");
        } 
        if (rs.getString("empmobilephone") == null) {
          xml.append("<empMobilephone></empMobilephone>");
        } else {
          xml.append("<empMobilephone>" + rs.getString("empmobilephone") + "</empMobilephone>");
        } 
        if (rs.getString("empbusinessphone") == null) {
          xml.append("<empBusinessphone></empBusinessphone>");
        } else {
          xml.append("<empBusinessphone>" + rs.getString("empbusinessphone") + "</empBusinessphone>");
        } 
        if (rs.getString("empemail") == null) {
          xml.append("<empEmail></empEmail>");
        } else {
          xml.append("<empEmail>" + rs.getString("empemail") + "</empEmail>");
        } 
        if (rs.getString("empposition") == null) {
          xml.append("<empPosition></empPosition>");
        } else {
          String empposition = rs.getString("empposition").toString();
          xml.append("<empPosition>" + empposition + "</empPosition>");
          sql = "select no from  st_station where station_name=' " + empposition + "'";
          pstmt = conn.prepareStatement(sql);
          rs2 = pstmt.executeQuery();
          if (rs2.next())
            xml.append("<empNo>" + rs.getString("no") + "</empNo>"); 
        } 
        if (rs.getString("empduty") == null) {
          xml.append("<empDuty></empDuty>");
        } else {
          String empduty = rs.getString("empduty").toString();
          xml.append("<empDuty>" + empduty + "</empDuty>");
          sql = "select dutyno from oa_duty where dutyname = '" + empduty + "'";
          pstmt = conn.prepareStatement(sql);
          rs1 = pstmt.executeQuery();
          if (rs1.next())
            xml.append("<dutyNo>" + rs1.getString("dutyno") + "</dutyNo>"); 
        } 
        if (rs.getString("empdutylevel") == null) {
          xml.append("<empdutylevel></empdutylevel>");
        } else {
          xml.append("<empdutylevel>" + rs.getString("empdutylevel") + "</empdutylevel>");
        } 
        xml.append("<organization>");
        if (rs.getString("orgname") == null) {
          xml.append("<orgName></orgName>");
        } else {
          xml.append("<orgName>" + rs.getString("orgname") + "</orgName>");
        } 
        if (rs.getString("org_id") == null) {
          xml.append("<orgId></orgId>");
        } else {
          xml.append("<orgId>" + rs.getString("org_id") + "</orgId>");
        } 
        if (rs.getString("orgserial") == null) {
          xml.append("<orgSerial></orgSerial>");
        } else {
          xml.append("<orgSerial>" + rs.getString("orgserial") + "</orgSerial>");
        } 
        xml.append("</organization>");
        xml.append("</employee>");
      } 
      this.dbc.close(rs, pstmt, null, conn);
    } catch (Exception ex) {
      this.dbc.close(rs, pstmt, null, conn);
      ex.printStackTrace();
    } 
    return xml.toString();
  }
}
