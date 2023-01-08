package com.js.oa.webservice.szgt;

import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.IO2File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserOperate {
  private String databaseType = SystemCommon.getDatabaseType();
  
  public String saveUser(List<UserPO> users, long timeStamp) {
    String flag = "success";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    int n = 0;
    try {
      base.begin();
      for (UserPO user : users) {
        String empId = "0", orgId = "";
        if (user == null) {
          IO2File.printFile("错误信息：请提交正确格式的参数", "用户信息操作");
          continue;
        } 
        if (user.getUserId() == null || "".equals(user.getUserId())) {
          IO2File.printFile("错误信息：用户主键不能为空", "用户信息操作");
          continue;
        } 
        if (!"del".equals(user.getOperateType())) {
          if (user.getOrgId() == null || "".equals(user.getOrgId())) {
            IO2File.printFile("错误信息：用户所属组织不能为空", "用户信息操作");
            continue;
          } 
          if (user.getUserName() == null || "".equals(user.getUserName())) {
            IO2File.printFile("错误信息：用户姓名不能为空", "用户信息操作");
            continue;
          } 
          if (user.getUserAccount() == null || "".equals(user.getUserAccount())) {
            IO2File.printFile("错误信息：用户登录名不能为空", "用户信息操作");
            continue;
          } 
          if (user.getUserSex() == null || "".equals(user.getUserSex())) {
            user.setUserSex("0");
          } else if (!"0".equals(user.getUserSex()) && !"1".equals(user.getUserSex())) {
            IO2File.printFile("错误信息：用户性别设置错误", "用户信息操作");
            continue;
          } 
        } 
        if (!"success".equals(flag)) {
          IO2File.printFile("错误信息：" + flag, "用户信息操作");
          break;
        } 
        if ("5".equals(user.getTrnsevent()))
          IO2File.printFile("错误信息：离职后变动：" + user.getUserName(), "用户信息操作"); 
        String sql = "";
        if ("del".equals(user.getOperateType()) || "4".equals(user.getTrnsevent()) || "5".equals(user.getTrnsevent())) {
          sql = "update org_employee set userisdeleted=1,userisactive=0 where guid='" + user.getUserId() + "'";
          IO2File.printFile("删除人员信息" + user.getUserId(), "用户信息操作");
          base.executeUpdate(sql);
          continue;
        } 
        sql = "SELECT org_id FROM org_organization where orgStatus<>4 and orgStatus<>1 and guid='" + user.getOrgId() + "'";
        IO2File.printFile("查询用户组织：" + sql, "用户信息操作");
        rs = base.executeQuery(sql);
        if (rs.next()) {
          orgId = rs.getString(1);
          sql = "select emp_id from org_employee where guid='" + user.getUserId() + "' and USERISDELETED=0";
          IO2File.printFile("查询用户信息：" + sql, "用户信息操作");
          rs = base.executeQuery(sql);
          if (rs.next())
            empId = rs.getString(1); 
          sql = "select 1 from org_employee where userisdeleted=0 and (empnumber='" + user.getUserNumber() + "' or useraccounts='" + user.getUserAccount() + "') and emp_id<>" + empId;
          IO2File.printFile("查询工号、帐号信息：" + sql, "用户信息操作");
          rs = base.executeQuery(sql);
          if (rs.next()) {
            IO2File.printFile("错误信息：用户工号或帐号已存在" + user.getUserNumber(), "用户信息操作");
            continue;
          } 
          String empPositionId = "-1";
          if (!"".equals(user.getUserStation())) {
            if ("oracle".equals(this.databaseType)) {
              sql = "INSERT INTO st_station SELECT hibernate_sequence.nextval,'" + 
                user.getUserStation() + "',0,'',0,seq_gw.Nextval FROM DUAL " + 
                "WHERE NOT EXISTS (SELECT 1 FROM st_station WHERE station_name='" + user.getUserStation() + "')";
            } else {
              sql = "INSERT INTO st_station(station_name,domain_id,description,corpid,NO) SELECT '" + 
                user.getUserStation() + "',0,'',0,seq_gw.Nextval FROM DUAL " + 
                "WHERE NOT EXISTS (SELECT 1 FROM st_station WHERE station_name='" + user.getUserStation() + "')";
            } 
            IO2File.printFile("岗位信息：" + sql, "用户信息操作");
            base.executeUpdate(sql);
            rs = base.executeQuery("SELECT id FROM st_station WHERE station_name='" + user.getUserStation() + "'");
            if (rs.next())
              empPositionId = rs.getString(1); 
          } 
          if (!"".equals(user.getUserDuty())) {
            if ("oracle".equals(this.databaseType)) {
              sql = "INSERT INTO oa_duty SELECT hibernate_sequence.nextval,'" + 
                user.getUserDuty() + "',0,100,'',0,seq_zw.Nextval FROM DUAL " + 
                "WHERE NOT EXISTS(SELECT 1 FROM oa_duty WHERE dutyname='" + user.getUserDuty() + "')";
            } else {
              sql = "INSERT INTO oa_duty(dutyname,domain_id,dutylevel,duty_describe,corpid,dutyno) SELECT '" + 
                user.getUserDuty() + "',0,100,'',0,seq_zw.Nextval FROM DUAL " + 
                "WHERE NOT EXISTS(SELECT 1 FROM oa_duty WHERE dutyname='" + user.getUserDuty() + "')";
            } 
            IO2File.printFile("职务信息：" + sql, "用户信息操作");
            base.executeUpdate(sql);
          } 
          if ("0".equals(empId)) {
            if ("oracle".equals(this.databaseType)) {
              sql = "insert into org_employee(emp_id,empname,empnumber,empsex,empemail,EMPHEIGHT,EMPWEIGHT,useraccounts,userpassword,USERISACTIVE,USERISDELETED,emppositionid,empposition,USERISFORMALUSER,USERISSUPER,USERSUPERBEGIN,USERSUPEREND,USERORDERCODE,CREATEDORG,BROWSERANGE,BROWSERANGENAME,KEYVALIDATE,KEYSERIAL,MAILPOST,DOMAIN_ID,SKIN,MAILBOXSIZE,NETDISKSIZE,sidelineorg,sidelineorgname,EMPDUTYLEVEL,userOnline,deptleader,empnativeplace,EmpStatus,personalKind,intoCompanyDate,zhuanzhengDate,jobStatus,empFireDate,empduty,lastupdate,guid,EMPISMARRIAGE,pk_org,pk_hrorg,glbdef15) values(hibernate_sequence.nextval,'" + 






                
                user.getUserName() + "','" + user.getUserNumber() + "','" + user.getUserSex() + "','" + user.getUserEmail() + "',0," + 
                "0,'" + user.getUserAccount() + "','5EB72296E792C92A549DD5A330112Y218965',1,0,'" + 
                empPositionId + "','" + user.getUserStation() + "',1,1,to_date('2015-01-01 00:00:00','yyyy-MM-dd HH24:mi:ss'),to_date('2050-01-01 00:00:00','yyyy-MM-dd HH24:mi:ss'),'1000'," + 
                "0,0,'','0',''," + 
                "'0','0','blue','1024','1024'," + 
                "'','','1000','0',''," + 
                "'',0,'" + user.getPersonalKind() + "',to_date('" + user.getIntoCompanyDate() + "','yyyy-MM-dd HH24:mi:ss'),to_date('" + user.getRxzsj() + "','yyyy-MM-dd HH24:mi:ss')," + 
                "'" + user.getJobStatus() + "',to_date('" + user.getEmpFireDate() + "','yyyy-MM-dd HH24:mi:ss'),'" + user.getUserDuty() + 
                "'," + timeStamp + ",'" + user.getUserId() + "',0,'" + user.getPkOrg() + "','" + user.getPkHrorg() + "','" + user.getGlbdef15() + "')";
            } else {
              sql = "insert into org_employee(emp_id,empname,empnumber,empsex,empemail,EMPHEIGHT,EMPWEIGHT,useraccounts,userpassword,USERISACTIVE,USERISDELETED,emppositionid,empposition,USERISFORMALUSER,USERISSUPER,USERSUPERBEGIN,USERSUPEREND,USERORDERCODE,CREATEDORG,BROWSERANGE,BROWSERANGENAME,KEYVALIDATE,KEYSERIAL,MAILPOST,DOMAIN_ID,SKIN,MAILBOXSIZE,NETDISKSIZE,sidelineorg,sidelineorgname,EMPDUTYLEVEL,userOnline,deptleader,empnativeplace,EmpStatus,personalKind,intoCompanyDate,zhuanzhengDate,jobStatus,empFireDate,empduty,lastupdate,guid,EMPISMARRIAGE,pk_org,pk_hrorg,glbdef15) values(hibernate_sequence.nextval,'" + 






                
                user.getUserName() + "','" + user.getUserNumber() + "','" + user.getUserSex() + "','" + user.getUserEmail() + "',0," + 
                "0,'" + user.getUserAccount() + "','" + user.getUserPassword() + "',1,0,'" + 
                empPositionId + "','" + user.getUserStation() + "',1,1,'2015-01-01 00:00:00','2050-01-01 00:00:00','1000'," + 
                "0,0,'','0',''," + 
                "'0','0','blue','1024','1024'," + 
                "'','','1000','0',''," + 
                "'',0,'" + user.getPersonalKind() + "','" + user.getIntoCompanyDate() + "','" + user.getRxzsj() + "'," + 
                "'" + user.getJobStatus() + "','" + user.getEmpFireDate() + "','" + user.getUserDuty() + 
                "'," + timeStamp + ",'" + user.getUserId() + "',0,'" + user.getPkOrg() + "','" + user.getPkHrorg() + "','" + user.getGlbdef15() + "')";
            } 
            IO2File.printFile("新增用户信息：" + sql, "用户信息操作");
            n = base.executeUpdate(sql);
            if (n > 0) {
              sql = "select emp_id from org_employee where useraccounts='" + user.getUserAccount() + "'";
              IO2File.printFile("查询新增用户ID：" + sql, "用户信息操作");
              rs = base.executeQuery(sql);
              if (rs.next()) {
                empId = rs.getString(1);
                sql = "insert into org_organization_user(org_id,emp_id) values (" + orgId + "," + empId + ")";
                IO2File.printFile("新增用户组织关系：" + sql, "用户信息操作");
                base.executeUpdate(sql);
              } 
            } 
          } else {
            if ("oracle".equals(this.databaseType)) {
              sql = "update org_employee set userisactive=1,empname='" + user.getUserName() + "',empnumber='" + user.getUserNumber() + 
                "',empposition='" + user.getUserStation() + "',empduty='" + user.getUserDuty() + "',empdutylevel='100'," + 
                "empsex='" + user.getUserSex() + "',empemail='" + user.getUserEmail() + 
                "',intoCompanyDate=to_date('" + user.getIntoCompanyDate() + "','yyyy-MM-dd HH24:mi:ss')," + 
                "zhuanzhengDate=to_date('" + user.getRxzsj() + "','yyyy-MM-dd HH24:mi:ss')," + 
                "personalKind='" + user.getPersonalKind() + "',jobStatus='" + user.getJobStatus() + 
                "',empFireDate=to_date('" + user.getEmpFireDate() + "','yyyy-MM-dd HH24:mi:ss'),lastupdate=" + 
                timeStamp + ",emppositionid='" + empPositionId + "',pk_org='" + user.getPkOrg() + "',pk_hrorg='" + 
                user.getPkHrorg() + "',glbdef15='" + user.getGlbdef15() + "' where emp_id=" + empId;
            } else {
              sql = "update org_employee set userisactive=1,empname='" + user.getUserName() + "',empnumber='" + user.getUserNumber() + 
                "',empposition='" + user.getUserStation() + "',empduty='" + user.getUserDuty() + "',empdutylevel='100'," + 
                "empsex='" + user.getUserSex() + "',empemail='" + user.getUserEmail() + 
                "',intoCompanyDate='" + user.getIntoCompanyDate() + "'," + 
                "zhuanzhengDate='" + user.getRxzsj() + "'," + 
                "personalKind='" + user.getPersonalKind() + "',jobStatus='" + user.getJobStatus() + 
                "',empFireDate='" + user.getEmpFireDate() + "',lastupdate=" + timeStamp + 
                ",emppositionid='" + empPositionId + "',pk_org='" + user.getPkOrg() + "',pk_hrorg='" + user.getPkHrorg() + 
                "',glbdef15='" + user.getGlbdef15() + "' where emp_id=" + empId;
            } 
            IO2File.printFile("更新用户基本信息：" + sql, "用户信息操作");
            base.executeUpdate(sql);
            sql = "update org_organization_user set org_id=" + orgId + " where emp_id=" + empId;
            IO2File.printFile("更新用户组织关系：" + sql, "用户信息操作");
            base.executeUpdate(sql);
          } 
          if ("3".equals(user.getEnablestate())) {
            sql = "update org_employee set userisactive=0 where emp_id=" + empId;
            IO2File.printFile("更新用户帐号状态：" + sql, "用户信息操作");
            base.executeUpdate(sql);
          } 
          continue;
        } 
        IO2File.printFile("错误信息：用户组织不存在" + user.getOrgId(), "用户信息操作");
      } 
    } catch (Exception e) {
      e.printStackTrace();
      IO2File.printFile("错误信息：" + e.getMessage(), "用户信息操作");
      flag = e.getMessage();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        base.end();
      } catch (SQLException e) {
        e.printStackTrace();
        IO2File.printFile("错误信息：" + e.getMessage(), "用户信息操作");
        flag = e.getMessage();
      } 
    } 
    return flag;
  }
  
  public String deleteUser(UserPO user) {
    String flag = "success";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String empId = "";
    try {
      base.begin();
      String sql = "select emp_id from org_employee where useraccounts='" + user.getUserAccount() + "'";
      rs = base.executeQuery(sql);
      if (rs.next()) {
        empId = rs.getString(1);
        base.executeUpdate("delete from org_rightscope where emp_id=" + empId);
        base.executeUpdate("delete from org_user_role where emp_id=" + empId);
        base.executeUpdate("delete from org_user_group where emp_id=" + empId);
        base.executeUpdate("delete from org_organization_user where emp_id=" + empId);
        base.executeUpdate("delete from org_employee where emp_id=" + empId);
      } else {
        flag = "用户信息不存在。";
      } 
    } catch (Exception e) {
      e.printStackTrace();
      flag = e.getMessage();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        base.end();
      } catch (SQLException e) {
        e.printStackTrace();
        flag = e.getMessage();
      } 
    } 
    System.out.println("操作结果：" + flag);
    return flag;
  }
  
  public String getMinLastUpdate() {
    DbOpt db = new DbOpt();
    String t = "";
    try {
      String sql = "SELECT IFNULL(MIN(lastupdate),0) FROM org_employee WHERE emp_id>0";
      if ("oracle".equals(this.databaseType))
        sql = "SELECT NVL(MIN(lastupdate),0) FROM org_employee WHERE emp_id>0"; 
      t = db.executeQueryToStr(sql);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        db.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return t;
  }
}
