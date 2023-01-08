package com.js.oa.dongcheng.dao;

import com.js.oa.dongcheng.db.DBUtil;
import com.js.oa.dongcheng.pojo.EmployeePojo;
import com.js.util.config.SystemCommon;
import com.js.util.util.MD5;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeDAO {
  private Connection conn = null;
  
  private PreparedStatement ps = null;
  
  private ResultSet rs = null;
  
  public String insertUser(EmployeePojo emp) {
    if (emp == null)
      return "{result:'用户信息不能为空。'}"; 
    if (emp.getUserGUID() == null || "".equals(emp.getUserGUID()))
      return "{result:'用户唯一标识不能为空。'}"; 
    if (emp.getUserAccounts() == null || "".equals(emp.getUserAccounts()))
      return "{result:'用户登录名称不能为空。'}"; 
    if (emp.getUserPassword() == null || "".equals(emp.getUserPassword()))
      return "{result:'用户登录密码不能为空。'}"; 
    if (emp.getEmpName() == null || "".equals(emp.getEmpName()))
      return "{result:'用户姓名不能为空。'}"; 
    if (emp.getEmpSex() == null || "".equals(emp.getEmpSex()))
      return "{result:'用户性别不能为空。'}"; 
    if (!"0".equals(emp.getEmpSex()) && !"1".equals(emp.getEmpSex()))
      return "{result:'用户行吧超出允许范围。'}"; 
    if (emp.getOrgGUID() == null || "".equals(emp.getOrgGUID()))
      return "{result:'用户所属组织不能为空。'}"; 
    String databaseType = SystemCommon.getDatabaseType();
    String ipContrl = (emp.getIpContrl() == null) ? "1" : emp.getIpContrl();
    String ipContrlBeginTime = (emp.getIpContrlBeginTime() == null) ? "" : emp.getIpContrlBeginTime();
    String ipContrlEndTime = (emp.getIpContrlEndTime() == null) ? "" : emp.getIpContrlEndTime();
    if ("0".equals(ipContrl)) {
      if (databaseType.indexOf("mysql") >= 0) {
        ipContrlBeginTime = "now()";
        ipContrlEndTime = "now()";
      } else if (databaseType.indexOf("oracle") >= 0) {
        ipContrlBeginTime = "sysdate";
        ipContrlEndTime = "sysdate";
      } 
    } else {
      ipContrl = "1";
      if (!checkDate(ipContrlBeginTime) || !checkDate(ipContrlEndTime))
        return "{result:'时间格式不正确。'}"; 
      if (databaseType.indexOf("oracle") >= 0) {
        ipContrlBeginTime = "to_date(" + ipContrlBeginTime + ",'yyyy-mm-dd hh24:mi:ss')";
        ipContrlEndTime = "to_date(" + ipContrlEndTime + ",'yyyy-mm-dd hh24:mi:ss')";
      } 
      if (databaseType.indexOf("mysql") >= 0) {
        ipContrlBeginTime = "'" + ipContrlBeginTime + "'";
        ipContrlEndTime = "'" + ipContrlEndTime + "'";
      } 
    } 
    String result = "";
    try {
      String sql = null;
      this.conn = DBUtil.getConn();
      this.ps = this.conn.prepareStatement("select * from org_employee where userisdeleted=0 and useraccounts=? or guid=?");
      this.ps.setString(1, emp.getUserAccounts());
      this.ps.setString(2, emp.getUserGUID());
      this.rs = this.ps.executeQuery();
      if (this.rs.next())
        return "{result:'用户登录名称或唯一标识已存在。'}"; 
      String empLeaderIds = "";
      String empLeaderNames = "";
      if (emp.getEmpLeader() != null && !"".equals(emp.getEmpLeader())) {
        String[] leaders = emp.getEmpLeader().split(",");
        sql = "select emp_id,empname from org_employee where userisdeleted=0 and guid=?";
        for (int i = 0; i < leaders.length; i++) {
          this.ps = this.conn.prepareStatement(sql);
          this.ps.setString(1, leaders[i]);
          this.rs = this.ps.executeQuery();
          if (this.rs.next()) {
            empLeaderIds = String.valueOf(empLeaderIds) + "$" + this.rs.getString(1) + "$";
            empLeaderNames = String.valueOf(empLeaderNames) + this.rs.getString(2) + ",";
          } else {
            result = "{result:'用户领导【" + leaders[i] + "】不存在。'}";
            break;
          } 
        } 
        if (!"".equals(result))
          return result; 
      } 
      long orgId = 0L;
      sql = "select org_id from org_organization where guid=?";
      this.ps = this.conn.prepareStatement(sql);
      this.ps.setString(1, emp.getOrgGUID());
      this.rs = this.ps.executeQuery();
      if (this.rs.next())
        orgId = this.rs.getLong(1); 
      if (0L == orgId)
        return "{result:'用户所属组织不存在。'}"; 
      String empNumber = (emp.getEmpnumber() == null) ? "" : emp.getEmpnumber();
      String simpleName = (emp.getEmpSimpleName() == null) ? "" : emp.getEmpSimpleName();
      if (databaseType.indexOf("mysql") >= 0) {
        sql = "insert into org_employee(EMPNAME,EMPSEX,USERACCOUNTS,USERPASSWORD,USERSIMPLENAME,USERSUPERBEGIN,USERSUPEREND,EMPISMARRIAGE,EMPHEIGHT,EMPWEIGHT,EMPSTATUS,USERISACTIVE,USERISDELETED,USERISFORMALUSER,USERISSUPER,USERORDERCODE,CREATEDORG,BROWSERANGE,BROWSERANGENAME,KEYVALIDATE,DOMAIN_ID,SKIN,MAILBOXSIZE,SIDELINEORG,SIDELINEORGNAME,EMPDUTYLEVEL,SIGNATUREIMGNAME,SIGNATUREIMGSAVENAME,USERONLINE,DEPTLEADER,EMPLEADERNAME,EMPLEADERID,empNumber,guid) values('" + 


          
          emp.getEmpName() + "'," + emp.getEmpSex() + 
          ",'" + emp.getUserAccounts() + "','" + (new MD5()).getMD5Code(emp.getUserPassword()) + "','" + 
          simpleName + "'," + ipContrlBeginTime + "," + ipContrlEndTime + ",0,0,0,0,1,0,1," + ipContrl + 
          ",10000,0,'','',0,0,'blue',100,'','',-1,'','',0,0,'" + empLeaderNames + "','" + empLeaderIds + "','" + 
          empNumber + "', '" + emp.getUserGUID() + "')";
      } else if (databaseType.indexOf("oracle") >= 0) {
        sql = "insert into org_employee(EMP_ID,EMPNAME,EMPSEX,USERACCOUNTS,USERPASSWORD,USERSIMPLENAME,USERSUPERBEGIN,USERSUPEREND,EMPISMARRIAGE,EMPHEIGHT,EMPWEIGHT,EMPSTATUS,USERISACTIVE,USERISDELETED,USERISFORMALUSER,USERISSUPER,USERORDERCODE,CREATEDORG,BROWSERANGE,BROWSERANGENAME,KEYVALIDATE,DOMAIN_ID,SKIN,MAILBOXSIZE,SIDELINEORG,SIDELINEORGNAME,EMPDUTYLEVEL,SIGNATUREIMGNAME,SIGNATUREIMGSAVENAME,USERONLINE,DEPTLEADER,EMPLEADERNAME,EMPLEADERID,empNumber,guid) values(HIBERNATE_SEQUENCE.nextval,'" + 


          
          emp.getEmpName() + "'," + 
          emp.getEmpSex() + ",'" + emp.getUserAccounts() + "','" + (new MD5()).getMD5Code(emp.getUserPassword()) + 
          "','" + simpleName + "'," + ipContrlBeginTime + "," + ipContrlEndTime + ",0,0,0,0,1,0,1," + 
          ipContrl + ",10000,0,'','',0,0,'blue',100,'','',-1,'','',0,0,'" + empLeaderNames + "','" + empLeaderIds + 
          "','" + empNumber + "', '" + emp.getUserGUID() + "')";
      } 
      System.out.println(sql);
      this.ps = this.conn.prepareStatement(sql);
      int n = this.ps.executeUpdate();
      if (n == 0)
        return "{return:'用户信息添加失败。'}"; 
      if (databaseType.indexOf("mysql") >= 0) {
        sql = "insert into org_organization_user values(" + orgId + "," + 
          "(select emp_id from org_employee where userisdeleted=0 and guid='" + emp.getUserGUID() + "' limit 1))";
      } else if (databaseType.indexOf("oracle") >= 0) {
        sql = "insert into org_organization_user values(" + orgId + "," + 
          "(select emp_id from org_employee where userisdeleted=0 and guid='" + emp.getUserGUID() + "' and rownum<=1))";
      } 
      this.ps = this.conn.prepareStatement(sql);
      n = this.ps.executeUpdate();
      if (n == 0)
        return "{result:'用户组织关系添加失败。'}"; 
      result = "{result:'1'}";
    } catch (Exception e) {
      e.printStackTrace();
      result = "{result:" + e.getMessage() + "}";
    } finally {
      DBUtil.closeAll(this.rs, this.ps, this.conn);
    } 
    return result;
  }
  
  public String updateUser(EmployeePojo emp) {
    if (emp == null)
      return "{result:'用户信息不能为空。'}"; 
    if (emp.getUserGUID() == null || "".equals(emp.getUserGUID()))
      return "{result:'用户唯一标识不能为空。'}"; 
    String databaseType = SystemCommon.getDatabaseType();
    String ipContrl = emp.getIpContrl();
    String ipContrlBeginTime = (emp.getIpContrlBeginTime() == null) ? "" : emp.getIpContrlBeginTime();
    String ipContrlEndTime = (emp.getIpContrlEndTime() == null) ? "" : emp.getIpContrlEndTime();
    if (ipContrl != null)
      if ("0".equals(ipContrl)) {
        if (databaseType.indexOf("mysql") >= 0) {
          ipContrlBeginTime = "now()";
          ipContrlEndTime = "now()";
        } else if (databaseType.indexOf("oracle") >= 0) {
          ipContrlBeginTime = "sysdate";
          ipContrlEndTime = "sysdate";
        } 
      } else {
        ipContrl = "1";
        if (!checkDate(ipContrlBeginTime) || !checkDate(ipContrlEndTime))
          return "{result:'时间格式不正确。'}"; 
        if (databaseType.indexOf("oracle") >= 0) {
          ipContrlBeginTime = "to_date(" + ipContrlBeginTime + ",'yyyy-mm-dd hh24:mi:ss')";
          ipContrlEndTime = "to_date(" + ipContrlEndTime + ",'yyyy-mm-dd hh24:mi:ss')";
        } 
        if (databaseType.indexOf("mysql") >= 0) {
          ipContrlBeginTime = "'" + ipContrlBeginTime + "'";
          ipContrlEndTime = "'" + ipContrlEndTime + "'";
        } 
      }  
    String result = "";
    try {
      long empId = 0L;
      this.conn = DBUtil.getConn();
      this.ps = this.conn.prepareStatement("select emp_id from org_employee where userisdeleted=0 and guid=?");
      this.ps.setString(1, emp.getUserGUID());
      this.rs = this.ps.executeQuery();
      if (this.rs.next()) {
        empId = this.rs.getLong(1);
        this.ps = this.conn.prepareStatement("select * from org_employee where userisdeleted=0 and guid<>? and useraccounts=?");
        this.ps.setString(1, emp.getUserGUID());
        this.ps.setString(2, emp.getUserAccounts());
        this.rs = this.ps.executeQuery();
        if (this.rs.next())
          return "{result:'用户登录名称已存在。'}"; 
        String empLeaderIds = "";
        String empLeaderNames = "";
        if (emp.getEmpLeader() != null && !"".equals(emp.getEmpLeader())) {
          String[] leaders = emp.getEmpLeader().split(",");
          for (int i = 0; i < leaders.length; i++) {
            this.ps = this.conn.prepareStatement("select emp_id,empname from org_employee where userisdeleted=0 and guid=?");
            this.ps.setString(1, leaders[i]);
            this.rs = this.ps.executeQuery();
            if (this.rs.next()) {
              empLeaderIds = String.valueOf(empLeaderIds) + "$" + this.rs.getString(1) + "$";
              empLeaderNames = String.valueOf(empLeaderNames) + this.rs.getString(2) + ",";
            } else {
              result = "{result:'用户领导【" + leaders[i] + "】不存在。'}";
              break;
            } 
          } 
          if (!"".equals(result))
            return result; 
        } 
        long orgId = 0L;
        if (emp.getOrgGUID() != null && !"".equals(emp.getOrgGUID())) {
          this.ps = this.conn.prepareStatement("select org_id from org_organization where guid=?");
          this.ps.setString(1, emp.getOrgGUID());
          this.rs = this.ps.executeQuery();
          if (this.rs.next())
            orgId = this.rs.getLong(1); 
          if (0L == orgId)
            return "{result:'用户所属组织不存在。'}"; 
        } 
        List<String> para = new ArrayList<String>();
        StringBuffer sql = new StringBuffer("update org_employee set guid=?");
        para.add(emp.getUserGUID());
        if (emp.getUserAccounts() != null && !"".equals(emp.getUserAccounts())) {
          sql.append(",useraccounts=?");
          para.add(emp.getUserAccounts());
        } 
        if (emp.getEmpName() != null && !"".equals(emp.getEmpName())) {
          sql.append(",empname=?");
          para.add(emp.getEmpName());
        } 
        if (emp.getEmpSex() != null && !"".equals(emp.getEmpSex()) && (
          "0".equals(emp.getEmpSex()) || "1".equals(emp.getEmpSex()))) {
          sql.append(",empsex=?");
          para.add(emp.getEmpSex());
        } 
        if (emp.getUserPassword() != null && !"".equals(emp.getUserPassword())) {
          sql.append(",userpassword=?");
          para.add((new MD5()).getMD5Code(emp.getUserPassword()));
        } 
        if (ipContrl != null) {
          sql.append(",userissuper=?,usersuperbegin=?,usersuperend=?");
          para.add(ipContrl);
          para.add(ipContrlBeginTime);
          para.add(ipContrlEndTime);
        } 
        if (emp.getEmpnumber() != null) {
          sql.append(",empNumber=?");
          para.add(emp.getEmpnumber());
        } 
        if (!"".equals(empLeaderIds)) {
          sql.append(",empleaderid=?,empleadername=?");
          para.add(empLeaderIds);
          para.add(empLeaderNames);
        } 
        sql.append(" where emp_id=?");
        para.add(String.valueOf(empId));
        this.ps = this.conn.prepareStatement(sql.toString());
        if (para.size() > 0)
          for (int i = 0; i < para.size(); i++)
            this.ps.setString(i + 1, para.get(i));  
        int n = this.ps.executeUpdate();
        if (n == 0)
          return "{return:'用户信息更新失败。'}"; 
        if (0L != orgId) {
          this.ps = this.conn.prepareStatement("update org_organization_user set org_id=" + orgId + " where emp_id=" + empId);
          n = this.ps.executeUpdate();
          if (n == 0)
            return "{result:'用户组织关系更新失败。'}"; 
        } 
        result = "{result:'1'}";
      } else {
        return "{result:'用户信息不存在。'}";
      } 
    } catch (Exception e) {
      e.printStackTrace();
      result = "{result:" + e.getMessage() + "}";
    } finally {
      DBUtil.closeAll(this.rs, this.ps, this.conn);
    } 
    return result;
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
}
