package com.js.oa.webservice.tjqz;

import com.js.util.util.DataSourceBase;
import com.js.util.util.IO2File;
import com.js.util.util.MD5;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
  
  private String insertUser(UserPO user) {
    if (user == null)
      return "0-请提交正确格式的参数"; 
    if (user.getGuid() == null || "".equals(user.getGuid()))
      return "0-用户唯一标识不能为空"; 
    if (user.getOrgId() == null || "".equals(user.getOrgId()))
      return "0-用户所属组织不能为空"; 
    if (user.getUserName() == null || "".equals(user.getUserName()))
      return "0-用户姓名不能为空"; 
    if (user.getUserAccount() == null || "".equals(user.getUserAccount()))
      return "0-用户登录名不能为空"; 
    if (user.getUserPassword() == null || "".equals(user.getUserPassword()))
      return "0-用户登录密码不能为空"; 
    if (!isValidDate(user.getUserBirth()))
      return "0-用户生日格式错误"; 
    if (user.getUserSex() == null || "".equals(user.getUserSex())) {
      user.setUserSex("0");
    } else if (!"0".equals(user.getUserSex()) && !"1".equals(user.getUserSex())) {
      return "0-用户性别设置错误";
    } 
    int orderCode = 1000;
    if (user.getUserOrderCode() != null && !"".equals(user.getUserOrderCode()))
      try {
        orderCode = Integer.parseInt(user.getUserOrderCode());
      } catch (Exception exception) {} 
    String flag = "";
    String empId = "0", orgId = "", orgMgrId = "", orgMgrName = "";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    int n = 0;
    try {
      base.begin();
      String sql = "select emp_id from org_employee where (useraccounts='" + user.getUserAccount() + "' or guid='" + 
        user.getGuid() + "') and userisactive=1 and USERISDELETED=0";
      rs = base.executeQuery(sql);
      if (rs.next()) {
        flag = "0-用户登录名或唯一标识已存在";
      } else {
        sql = "SELECT org_id,orgmanagerempid,orgmanagerempname FROM org_organization where guid='" + user.getOrgId() + "'";
        rs = base.executeQuery(sql);
        if (rs.next()) {
          orgId = rs.getString(1);
          orgMgrId = rs.getString(2);
          orgMgrName = rs.getString(3);
          if (orgMgrId == null || "".equals(orgMgrId) || "null".equalsIgnoreCase(orgMgrId)) {
            orgMgrId = "";
            orgMgrName = "";
          } 
          if (user.getUserNumber() != null && !"".equals(user.getUserNumber())) {
            sql = "select emp_id from org_employee where empnumber='" + user.getUserNumber() + "' and userisactive=1 and userisdeleted=0";
            rs = base.executeQuery(sql);
            if (rs.next())
              flag = "0-用户工号已存在"; 
          } 
          if ("".equals(flag)) {
            sql = "INSERT INTO org_employee (EMPNAME,EMPNUMBER,EMPBIRTH,EMPSEX,EMPNATION,EMPISMARRIAGE,EMPPHONE,EMPMOBILEPHONE,EMPBUSINESSPHONE,EMPBUSINESSFAX,EMPEMAIL,EMPWEBADDRESS,EMPENGLISHNAME,EMPHEIGHT,EMPWEIGHT,EMPADDRESS,EMPIDCARD,EMPSTATUS,EMPLEADERID,EMPLEADERNAME,useraccounts,userpassword,USERISACTIVE,USERISDELETED,USERISFORMALUSER,USERISSUPER,USERSUPERBEGIN,USERSUPEREND,USERORDERCODE,CREATEDORG,BROWSERANGE,BROWSERANGENAME,KEYVALIDATE,KEYSERIAL,MAILPOST,DOMAIN_ID,SKIN,MAILBOXSIZE,NETDISKSIZE,sidelineorg,sidelineorgname,EMPDUTYLEVEL,userOnline,deptleader,guid)VALUES('" + 







              
              user.getUserName() + "','" + user.getUserNumber() + "','" + user.getUserBirth() + "'," + user.getUserSex() + ",'" + user.getUserNation() + "'," + 
              "'0','" + user.getUserPhone() + "','" + user.getUserMobilePhone() + "','" + user.getUserBusinessPhone() + "','" + user.getUserBusinessFax() + "','" + 
              user.getUserEmail() + "','" + user.getUserWebAddress() + "','" + user.getUserEnglishName() + "',0,0,'" + 
              user.getUserAddress() + "','" + user.getUserCardNO() + "','0','" + orgMgrId + "','" + orgMgrName + "','" + 
              user.getUserAccount() + "','" + (new MD5()).getMD5Code(user.getUserPassword()) + "','1','0','1'," + 
              "'1','2015-01-01 00:00:00','2050-01-01 00:00:00','" + orderCode + "','0'," + 
              "'0','','0','','0'," + 
              "'0','blue','1024','1024',''," + 
              "'','1000','0','','" + user.getGuid() + "')";
            IO2File.printFile("新增用户：" + sql, "用户操作");
            n = base.executeUpdate(sql);
            if (n > 0) {
              rs = base.executeQuery("select max(emp_id) from org_employee");
              if (rs.next())
                empId = rs.getString(1); 
            } 
            if (n > 0) {
              if (!"".equals(empId) && !"".equals(orgId)) {
                sql = "insert into org_organization_user(org_id,emp_id) values (" + orgId + "," + empId + ")";
                IO2File.printFile("新增用户组织关系：" + sql, "用户操作");
                base.executeUpdate(sql);
              } 
              flag = "1-success";
            } else {
              flag = "0-error";
            } 
          } 
        } else {
          flag = "0-人员所属组织不存在";
        } 
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
    return flag;
  }
  
  private String updateUser(UserPO user) {
    if (user == null)
      return "0-请提交正确格式的参数"; 
    if (user.getGuid() == null || "".equals(user.getGuid()))
      return "0-用户唯一标识不能为空"; 
    if (user.getOrgId() == null || "".equals(user.getOrgId()))
      return "0-用户所属组织不能为空"; 
    if (user.getUserName() == null || "".equals(user.getUserName()))
      return "0-用户姓名不能为空"; 
    if (user.getUserAccount() == null || "".equals(user.getUserAccount()))
      return "0-用户登录名不能为空"; 
    if (!isValidDate(user.getUserBirth()))
      return "0-用户生日格式错误"; 
    if (user.getUserSex() == null || "".equals(user.getUserSex())) {
      user.setUserSex("0");
    } else if (!"0".equals(user.getUserSex()) && !"1".equals(user.getUserSex())) {
      return "0-用户性别设置错误";
    } 
    int orderCode = 1000;
    if (user.getUserOrderCode() != null && !"".equals(user.getUserOrderCode()))
      try {
        orderCode = Integer.parseInt(user.getUserOrderCode());
      } catch (Exception exception) {} 
    String flag = "";
    String empId = "0", orgId = "", orgMgrId = "", orgMgrName = "";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    int n = 0;
    try {
      base.begin();
      String sql = "select emp_id from org_employee where  guid='" + user.getGuid() + "' and USERISDELETED=0";
      rs = base.executeQuery(sql);
      if (rs.next()) {
        empId = rs.getString(1);
        sql = "SELECT org_id,orgmanagerempid,orgmanagerempname FROM org_organization where guid='" + user.getOrgId() + "' and orgstatus<>4";
        rs = base.executeQuery(sql);
        if (rs.next()) {
          orgId = rs.getString(1);
          orgMgrId = rs.getString(2);
          orgMgrName = rs.getString(3);
          sql = "select emp_id from org_employee where useraccounts='" + user.getUserAccount() + "' and userisdeleted=0 and emp_id<>" + empId;
          rs = base.executeQuery(sql);
          if (rs.next())
            flag = "0-用户登录名重复"; 
          if (user.getUserNumber() != null && !"".equals(user.getUserNumber())) {
            sql = "select emp_id from org_employee where empnumber='" + user.getUserNumber() + "' and userisdeleted=0 and emp_id<>" + empId;
            rs = base.executeQuery(sql);
            if (rs.next())
              flag = "0-用户工号已存在"; 
          } 
          if ("".equals(flag)) {
            sql = "update org_employee set EMPNAME='" + user.getUserName() + "',EMPNUMBER='" + user.getUserNumber() + "'," + 
              "EMPBIRTH='" + user.getUserBirth() + "',EMPSEX=" + user.getUserSex() + ",EMPNATION='" + user.getUserNation() + "'," + 
              "EMPPHONE='" + user.getUserPhone() + "',EMPMOBILEPHONE='" + user.getUserMobilePhone() + "'," + 
              "EMPBUSINESSPHONE='" + user.getUserBusinessPhone() + "',EMPBUSINESSFAX='" + user.getUserBusinessFax() + "'," + 
              "EMPEMAIL='" + user.getUserEmail() + "',EMPWEBADDRESS='" + user.getUserWebAddress() + "'," + 
              "EMPENGLISHNAME='" + user.getUserEnglishName() + "',EMPADDRESS='" + user.getUserAddress() + "'," + 
              "EMPIDCARD='" + user.getUserCardNO() + "'," + 
              "useraccounts='" + user.getUserAccount() + "'," + 
              "USERORDERCODE='" + orderCode + "' where emp_id=" + empId;
            IO2File.printFile("修改用户：" + sql, "用户操作");
            n = base.executeUpdate(sql);
            if (n > 0) {
              sql = "update org_organization_user set org_id=" + orgId + " where emp_id=" + empId;
              IO2File.printFile("修改用户组织关系：" + sql, "用户操作");
              base.executeUpdate(sql);
              flag = "1-success";
            } else {
              flag = "0-error";
            } 
          } 
        } else {
          flag = "0-人员所属组织不存在";
        } 
      } else {
        flag = "0-用户信息不存在";
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
    return flag;
  }
  
  private String deleteUser(UserPO user) {
    if (user == null)
      return "0-请提交正确格式的参数"; 
    String flag = "0";
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String empId = "";
    try {
      base.begin();
      String sql = "select emp_id from org_employee where guid='" + user.getGuid() + "'";
      rs = base.executeQuery(sql);
      if (rs.next()) {
        empId = rs.getString(1);
        base.executeUpdate("delete from org_rightscope where emp_id=" + empId);
        base.executeUpdate("delete from org_user_role where emp_id=" + empId);
        base.executeUpdate("delete from org_user_group where emp_id=" + empId);
        base.executeUpdate("delete from org_organization_user where emp_id=" + empId);
        int n = base.executeUpdate("delete from org_employee where emp_id=" + empId);
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
  
  private String deleteUserLogic(UserPO user) {
    if (user == null)
      return "0-请提交正确格式的参数"; 
    String flag = "";
    String sql = "update org_employee set USERISACTIVE=0,USERISDELETED=1 where guid='" + user.getGuid() + "'";
    DataSourceBase base = new DataSourceBase();
    try {
      if (user.getGuid() != null && !"".equals(user.getGuid())) {
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
    return flag;
  }
  
  private UserPO getPOFromXml(String xml) {
    System.out.println("人员信息xml：" + xml);
    UserPO user = new UserPO();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    user.setUserBirth(sdf.format(new Date()));
    SAXBuilder builder = new SAXBuilder();
    Document doc = null;
    try {
      doc = builder.build(new InputSource(new StringReader(xml)));
      Element person = doc.getRootElement();
      List<Element> fields = person.getChildren("Field");
      for (int i = 0; i < fields.size(); i++) {
        Element field = fields.get(i);
        String colName = field.getAttributeValue("ColName");
        if ("guid".equalsIgnoreCase(colName)) {
          user.setGuid(field.getAttributeValue("Value"));
        } else if ("orgId".equalsIgnoreCase(colName)) {
          user.setOrgId(field.getAttributeValue("Value"));
        } else if ("empName".equalsIgnoreCase(colName)) {
          user.setUserName(field.getAttributeValue("Value"));
        } else if ("empNumber".equalsIgnoreCase(colName)) {
          user.setUserNumber(field.getAttributeValue("Value"));
        } else if ("empAccounts".equalsIgnoreCase(colName)) {
          user.setUserAccount(field.getAttributeValue("Value"));
        } else if ("empPassWord".equalsIgnoreCase(colName)) {
          user.setUserPassword(field.getAttributeValue("Value"));
        } else if ("empNation".equalsIgnoreCase(colName)) {
          user.setUserNation(field.getAttributeValue("Value"));
        } else if ("empBirth".equalsIgnoreCase(colName)) {
          if (field.getAttributeValue("Value") != null && !"".equals(field.getAttributeValue("Value")))
            user.setUserBirth(field.getAttributeValue("Value")); 
        } else if ("empSex".equalsIgnoreCase(colName)) {
          user.setUserSex(field.getAttributeValue("Value"));
        } else if ("empPhone".equalsIgnoreCase(colName)) {
          user.setUserPhone(field.getAttributeValue("Value"));
        } else if ("empMobilePhone".equalsIgnoreCase(colName)) {
          user.setUserMobilePhone(field.getAttributeValue("Value"));
        } else if ("empBusinessPhone".equalsIgnoreCase(colName)) {
          user.setUserBusinessPhone(field.getAttributeValue("Value"));
        } else if ("empBusinessFax".equalsIgnoreCase(colName)) {
          user.setUserBusinessFax(field.getAttributeValue("Value"));
        } else if ("empEmail".equalsIgnoreCase(colName)) {
          user.setUserEmail(field.getAttributeValue("Value"));
        } else if ("empWebAddress".equalsIgnoreCase(colName)) {
          user.setUserWebAddress(field.getAttributeValue("Value"));
        } else if ("empEnglishName".equalsIgnoreCase(colName)) {
          user.setUserEnglishName(field.getAttributeValue("Value"));
        } else if ("empAddress".equalsIgnoreCase(colName)) {
          user.setUserAddress(field.getAttributeValue("Value"));
        } else if ("empIdCard".equalsIgnoreCase(colName)) {
          user.setUserCardNO(field.getAttributeValue("Value"));
        } else if ("empLeaderId".equalsIgnoreCase(colName)) {
          user.setUserLeaderId(field.getAttributeValue("Value"));
        } else if ("empOrderCode".equalsIgnoreCase(colName)) {
          user.setUserOrderCode(field.getAttributeValue("Value"));
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
    return user;
  }
  
  private boolean isValidDate(String str) {
    boolean convertSuccess = true;
    if (str == null || "".equals(str))
      return convertSuccess; 
    try {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      format.setLenient(false);
      format.parse(str);
    } catch (Exception e) {
      convertSuccess = false;
    } 
    return convertSuccess;
  }
}
