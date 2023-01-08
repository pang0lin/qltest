package com.js.oa.webservice.langxin;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.IO2File;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class UserOperate {
  public String personAdd(String xml) {
    UserPO user = getPOFromXml(xml);
    return insertUser(user);
  }
  
  public String personEdit(String xml) {
    UserPO user = getPOFromXml(xml);
    return updateUser(user);
  }
  
  public String personDelete(String xml) {
    UserPO user = getPOFromXml(xml);
    return deleteUser(user);
  }
  
  public String personDeleteLogic(String xml) {
    UserPO user = getPOFromXml(xml);
    return deleteUserLogic(user);
  }
  
  public String insertUser(UserPO user) {
    String flag = "";
    String dataBaseType = SystemCommon.getDatabaseType();
    IO2File.printFile("新增用户：" + user.getUserName(), "朗新用户操作", 3);
    String empId = "0", orgId = "", orgMgrId = "", orgMgrName = "";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    int n = 0;
    try {
      if (user.getUserCardNO() != null && !"".equals(user.getUserCardNO()) && user.getUserAccount() != null && 
        !"".equals(user.getUserAccount())) {
        base.begin();
        String sql = "select emp_id from org_employee where (useraccounts='" + user.getUserAccount() + "' or EMPIDCARD='" + user.getUserCardNO() + "') and USERISDELETED=0";
        rs = base.executeQuery(sql);
        if (rs.next()) {
          flag = "0-证件号码或登录名重复。";
        } else {
          if (user.getUserOrgId() != null && !"".equals(user.getUserOrgId())) {
            sql = "SELECT org_id,orgmanagerempid,orgmanagerempname FROM org_organization where guid='" + user.getUserOrgId() + "'";
            rs = base.executeQuery(sql);
            if (rs.next()) {
              orgId = rs.getString(1);
              orgMgrId = rs.getString(2);
              orgMgrName = rs.getString(3);
            } else {
              System.out.println("部门信息不存在。");
            } 
          } 
          if ("".equals(flag)) {
            if (dataBaseType.indexOf("mysql") >= 0) {
              sql = "INSERT INTO org_employee (EMPNAME,EMPNUMBER,EMPBIRTH,EMPSEX,EMPISMARRIAGE,EMPMOBILEPHONE,EMPEMAIL,EMPENGLISHNAME,EMPHEIGHT,EMPWEIGHT,EMPIDCARD,EMPSTATUS,useraccounts,userpassword,USERISACTIVE,USERISDELETED,USERISFORMALUSER,USERISSUPER,USERSUPERBEGIN,USERSUPEREND,CREATEDORG,BROWSERANGE,BROWSERANGENAME,KEYVALIDATE,KEYSERIAL,MAILPOST,DOMAIN_ID,SKIN,MAILBOXSIZE,NETDISKSIZE,sidelineorg,sidelineorgname,EMPDUTYLEVEL,userOnline,empPositionId,empDuty,empleaderid,empleadername,wm_code,wm_userAccounts,wm_userPassword,wm_emp_id)VALUES('" + 



                
                user.getUserName() + "','" + user.getUserCode() + "',now()," + user.getUserSex() + "," + 
                "'0','" + user.getUserPhone() + "','" + user.getUserEmail() + "','',0," + 
                "0,'" + user.getUserCardNO() + "','0','" + user.getUserAccount() + "','5EB72F96E795C92A549DD5A330112621896O'," + 
                "'1','0','1','1','2013-04-12 00:00:00'," + 
                "'2050-01-01 00:00:00','0','0','','0'," + 
                "'','0','0','blue','1024'," + 
                "'1024','','','1000','0'," + (
                new DutyStation()).insertStation(user.getUserStation(), base) + ",'" + (new DutyStation()).insertDuty(user.getUserDuty(), base) + "','" + orgMgrId + "','" + 
                orgMgrName + "','" + user.getWmCode() + "','" + user.getWmUserAccounts() + "','" + 
                user.getWmUserPassword() + "','" + user.getWmEmpId() + "')";
              IO2File.printFile("新增用户：" + sql, "朗新用户操作");
              n = base.executeUpdate(sql);
              if (n > 0) {
                rs = base.executeQuery("select max(emp_id) from org_employee");
                if (rs.next())
                  empId = rs.getString(1); 
              } 
            } else {
              rs = base.executeQuery("select hibernate_sequence.nextval from dual");
              if (rs.next())
                empId = rs.getString(1); 
              sql = "INSERT INTO org_employee (emp_id,EMPNAME,EMPNUMBER,EMPBIRTH,EMPSEX,EMPISMARRIAGE,EMPMOBILEPHONE,EMPEMAIL,EMPENGLISHNAME,EMPHEIGHT,EMPWEIGHT,EMPIDCARD,EMPSTATUS,useraccounts,userpassword,USERISACTIVE,USERISDELETED,USERISFORMALUSER,USERISSUPER,USERSUPERBEGIN,USERSUPEREND,CREATEDORG,BROWSERANGE,BROWSERANGENAME,KEYVALIDATE,KEYSERIAL,MAILPOST,DOMAIN_ID,SKIN,MAILBOXSIZE,NETDISKSIZE,sidelineorg,sidelineorgname,EMPDUTYLEVEL,userOnline,empPositionId,empDuty,empleaderid,empleadername,wm_code,wm_userAccounts,wm_userPassword,wm_emp_id)VALUES(" + 



                
                empId + ",'" + user.getUserName() + "','" + user.getUserCode() + "',sysdate," + user.getUserSex() + "," + 
                "'0','" + user.getUserPhone() + "','" + user.getUserEmail() + "','',0," + 
                "0,'" + user.getUserCardNO() + "','0','" + user.getUserAccount() + "','5EB72F96E795C92A549DD5A330112621896O'," + 
                "'1','0','1','1',to_date('2013-04-12 00:00:00','yyyy-mm-dd hh24:mi:ss')," + 
                "to_date('2050-01-01 00:00:00','yyyy-mm-dd hh24:mi:ss'),'0','0','','0'," + 
                "'','0','0','blue','1024'," + 
                "'1024','','','1000','0'," + (
                new DutyStation()).insertStation(user.getUserStation(), base) + ",'" + (new DutyStation()).insertDuty(user.getUserDuty(), base) + "','" + orgMgrId + "','" + 
                orgMgrName + "','" + user.getWmCode() + "','" + user.getWmUserAccounts() + "','" + 
                user.getWmUserPassword() + "','" + user.getWmEmpId() + "')";
              IO2File.printFile("新增用户：" + sql, "朗新用户操作");
              n = base.executeUpdate(sql);
              System.out.println(sql);
            } 
            if (n > 0) {
              if (!"".equals(empId) && !"".equals(orgId)) {
                sql = "insert into org_organization_user(org_id,emp_id) values (" + orgId + "," + empId + ")";
                base.executeUpdate(sql);
              } 
              flag = "1-success";
            } else {
              flag = "0-error";
            } 
          } 
        } 
      } else {
        flag = "0-用户证件号、登录名不能为空。";
      } 
    } catch (Exception e) {
      e.printStackTrace();
      flag = "0-" + e.getMessage();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        base.end();
      } catch (SQLException e) {
        e.printStackTrace();
        flag = "0-" + e.getMessage();
      } 
    } 
    System.out.println("操作结果：" + flag);
    return flag.split("-")[0];
  }
  
  public String updateUser(UserPO user) {
    String flag = "";
    IO2File.printFile("修改用户：" + user.getUserName(), "朗新用户操作");
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String empId = "", orgId = "", orgMgrId = "", orgMgrName = "";
    try {
      if (user.getUserCardNO() != null && !"".equals(user.getUserCardNO()) && user.getUserAccount() != null && 
        !"".equals(user.getUserAccount())) {
        base.begin();
        String sql = "select emp_id from org_employee where EMPIDCARD='" + user.getUserCardNO() + "' and USERISDELETED=0";
        rs = base.executeQuery(sql);
        if (rs.next()) {
          empId = rs.getString(1);
          sql = "select emp_id from org_employee where emp_id<>" + empId + " and useraccounts='" + user.getUserAccount() + "' and USERISDELETED=0";
          rs = base.executeQuery(sql);
          if (rs.next()) {
            flag = "0-用户登录名称已存在。";
          } else {
            if (user.getUserOrgId() != null && !"".equals(user.getUserOrgId())) {
              sql = "SELECT org_id,orgmanagerempid,orgmanagerempname FROM org_organization where guid='" + user.getUserOrgId() + "'";
              rs = base.executeQuery(sql);
              if (rs.next()) {
                orgId = rs.getString(1);
                orgMgrId = rs.getString(2);
                orgMgrName = rs.getString(3);
                sql = "update org_organization_user set org_id=" + orgId + " where emp_id=" + empId;
                int n = base.executeUpdate(sql);
                if (n == 0) {
                  sql = "insert into org_organization_user(org_id,emp_id) values(" + orgId + "," + empId + ")";
                  base.executeUpdate(sql);
                } 
              } else {
                System.out.println("部门信息不存在。");
              } 
            } else {
              sql = "delete from org_organization_user where emp_id=" + empId;
              base.executeUpdate(sql);
            } 
            if ("".equals(flag)) {
              sql = "update org_employee set USERISACTIVE=1,USERISDELETED=0,EMPNAME='" + user.getUserName() + "',empNumber='" + user.getUserCode() + "'," + 
                "EMPSEX=" + user.getUserSex() + ",EMPMOBILEPHONE='" + user.getUserPhone() + "',EMPEMAIL='" + user.getUserEmail() + "'," + 
                "EMPIDCARD='" + user.getUserCardNO() + "',useraccounts='" + user.getUserAccount() + "'" + 
                ",empPositionId=" + (new DutyStation()).insertStation(user.getUserStation(), base) + 
                ",empDuty='" + (new DutyStation()).insertDuty(user.getUserDuty(), base) + "',userpassword='5EB72F96E795C92A549DD5A330112621896O'," + 
                "empleaderid='" + orgMgrId + "',empleadername='" + orgMgrName + "',wm_code='" + user.getWmCode() + "',wm_userAccounts='" + 
                user.getWmUserAccounts() + "',wm_userPassword='" + user.getWmUserPassword() + "',wm_emp_id='" + user.getWmEmpId() + "' where emp_id=" + empId;
              IO2File.printFile("修改用户：" + sql, "朗新用户操作");
              int n = base.executeUpdate(sql);
              if (n > 0) {
                flag = "1-success";
              } else {
                flag = "0-error";
              } 
            } 
          } 
        } else {
          flag = "0-用户信息不存在。";
        } 
      } else {
        flag = "0-用户证件号、登录名信息不能为空。";
      } 
    } catch (Exception e) {
      e.printStackTrace();
      flag = "0-" + e.getMessage();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        base.end();
      } catch (SQLException e) {
        e.printStackTrace();
        flag = "0-" + e.getMessage();
      } 
    } 
    System.out.println("操作结果：" + flag);
    return flag.split("-")[0];
  }
  
  public String deleteUser(UserPO user) {
    String flag = "0";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String empId = "";
    try {
      base.begin();
      String sql = "select emp_id from org_employee where EMPIDCARD='" + user.getUserCardNO() + "' and USERISDELETED=0";
      rs = base.executeQuery(sql);
      if (rs.next()) {
        empId = rs.getString(1);
        base.executeUpdate("delete from org_rightscope where emp_id=" + empId);
        base.executeUpdate("delete from org_user_role where emp_id=" + empId);
        base.executeUpdate("delete from org_user_group where emp_id=" + empId);
        base.executeUpdate("delete from org_organization_user where emp_id=" + empId);
        int n = base.executeUpdate("delete from org_employee where EMPIDCARD='" + user.getUserCardNO() + "'");
        if (n > 0) {
          flag = "1-success";
        } else {
          flag = "0-error";
        } 
      } else {
        flag = "0-用户信息不存在。";
      } 
    } catch (Exception e) {
      e.printStackTrace();
      flag = "0-" + e.getMessage();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        base.end();
      } catch (SQLException e) {
        e.printStackTrace();
        flag = "0-" + e.getMessage();
      } 
    } 
    System.out.println("操作结果：" + flag);
    return flag.split("-")[0];
  }
  
  public String deleteUserLogic(UserPO user) {
    String flag = "";
    String sql = "update org_employee set USERISACTIVE=0,USERISDELETED=1 where EMPIDCARD='" + user.getUserCardNO() + "' and USERISDELETED=0";
    DataSourceBase base = new DataSourceBase();
    try {
      if (user.getUserCardNO() != null && !"".equals(user.getUserCardNO())) {
        base.begin();
        int n = base.executeUpdate(sql);
        if (n > 0) {
          flag = "1-success";
        } else {
          flag = "0-用户不存在。";
        } 
      } else {
        flag = "0-人员标识字段不能为空。";
      } 
    } catch (Exception e) {
      e.printStackTrace();
      flag = "0-" + e.getMessage();
    } finally {
      base.end();
    } 
    System.out.println("操作结果：" + flag);
    return flag.split("-")[0];
  }
  
  public UserPO getPOFromXml(String xml) {
    System.out.println("人员信息xml：" + xml);
    UserPO user = new UserPO();
    SAXBuilder builder = new SAXBuilder();
    builder.setExpandEntities(false);
    Document doc = null;
    try {
      doc = builder.build(new InputSource(new StringReader(xml)));
    } catch (Exception e) {
      e.printStackTrace();
    } 
    Element person = doc.getRootElement();
    List<Element> fields = person.getChildren("Field");
    for (int i = 0; i < fields.size(); i++) {
      Element field = fields.get(i);
      String colName = field.getAttributeValue("ColName");
      if ("A0101".equalsIgnoreCase(colName)) {
        user.setUserName(field.getAttributeValue("Value"));
      } else if ("A0190".equalsIgnoreCase(colName)) {
        user.setUserCode(field.getAttributeValue("Value"));
      } else if ("LOGINOTHERNAME".equalsIgnoreCase(colName)) {
        user.setUserAccount(field.getAttributeValue("Value"));
      } else if ("A0177".equalsIgnoreCase(colName)) {
        user.setUserCardNO(field.getAttributeValue("Value"));
      } else if ("A0107".equalsIgnoreCase(colName)) {
        if ("1".equals(field.getAttributeValue("Value"))) {
          user.setUserSex("0");
        } else if ("2".equals(field.getAttributeValue("Value"))) {
          user.setUserSex("1");
        } 
      } else if ("A01274".equalsIgnoreCase(colName)) {
        user.setUserPhone(field.getAttributeValue("Value"));
      } else if ("Email".equalsIgnoreCase(colName)) {
        user.setUserEmail(field.getAttributeValue("Value"));
      } else if ("J01_dept_id".equalsIgnoreCase(colName)) {
        user.setUserOrgId(field.getAttributeValue("Value"));
      } else if ("SPJB".equalsIgnoreCase(colName)) {
        user.setUserDuty(field.getAttributeValue("Value"));
      } else if ("Password".equalsIgnoreCase(colName)) {
        user.setUserPassword(field.getAttributeValue("Value"));
      } else if ("A01122".equalsIgnoreCase(colName)) {
        user.setWmCode(field.getAttributeValue("Value"));
      } else if ("A01123".equalsIgnoreCase(colName)) {
        user.setWmUserAccounts(field.getAttributeValue("Value"));
      } else if ("A01127".equalsIgnoreCase(colName)) {
        user.setWmUserPassword(field.getAttributeValue("Value"));
      } else if ("A0188".equalsIgnoreCase(colName)) {
        user.setWmEmpId(field.getAttributeValue("Value"));
      } 
    } 
    return user;
  }
}
