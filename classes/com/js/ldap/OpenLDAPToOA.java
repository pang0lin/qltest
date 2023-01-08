package com.js.ldap;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenLDAPToOA {
  public void orgOperate(List<Map<String, String>> list, Map<String, String> ldapInfo, Long curTime) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    String sql = "";
    for (int i = 0; i < list.size(); i++) {
      Map<String, String> map = list.get(i);
      String orgName = map.get("ou");
      String orgNameString = orgName;
      try {
        sql = "SELECT org_id FROM org_organization WHERE  ";
        String[][] orgExist = base.queryArrayBySql(sql);
        if (orgExist.length > 0) {
          base.begin();
          String orgId = orgExist[0][0];
          sql = "update org_organization set orgName='' ,orgNameString='',lastUpdate=" + curTime + " where org_id=" + orgId;
          base.executeUpdate(sql);
        } else {
          System.out.println(String.valueOf(orgName) + " 部门不存在！");
          System.out.println("新增部门：" + orgName);
          base.begin();
          String orgId = "";
          StringBuffer buffer = new StringBuffer("");
          if (SystemCommon.getDatabaseType().indexOf("oracle") >= 0) {
            rs = base.executeQuery("select hibernate_sequence.nextval from dual");
            if (rs.next())
              orgId = (new StringBuilder(String.valueOf(rs.getLong(1)))).toString(); 
            rs.close();
            buffer.append("insert into org_organization(org_id,orgName,orgSimpleName,orgSerial,orgLevel,orgHasChannel) values(")
              .append(orgId).append(",'").append(orgName).append("','").append(orgName).append("','")
              .append(orgName).append("',").append("0").append(",0)");
            base.executeUpdate(buffer.toString());
          } else {
            buffer.append("insert into org_organization(orgName,orgSimpleName,orgSerial,orgLevel,orgHasChannel) values('")
              .append(orgName).append("','").append(orgName).append("','").append(orgName).append("',")
              .append("0").append(",0)");
            base.executeUpdate(buffer.toString());
            rs = base.executeQuery("select max(org_id) from org_organization");
            if (rs.next())
              orgId = (new StringBuilder(String.valueOf(rs.getLong(1)))).toString(); 
            rs.close();
          } 
          sql = "update org_organization set orgparentorgId=0,orgmanagerempId='',orgmanagerempName='',orgordercode=500000,orgdescripte='',orghasjunior=0,orgstatus=0,orgIdString='_500000$-1$_500000$" + 
            orgId + "$',orgnamestring='" + orgNameString + "'" + 
            ",domain_id=0,orgtype=1 where org_id=" + orgId;
          base.executeUpdate(sql);
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        base.end();
      } 
    } 
  }
  
  public void userOperate(List<Map<String, String>> list, Map<String, String> ldapInfo, Long curTime) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    StringBuffer buffer = new StringBuffer("");
    String sql = "";
    Map<String, String[]> orgMap = getOrgInfo();
    for (int i = 0; i < list.size(); i++) {
      Map<String, String> map = list.get(i);
      String account = (map.get("uid") == null) ? "" : map.get("uid");
      String empName = (map.get("cn") == null) ? "" : map.get("cn");
      String email = (map.get("mail") == null) ? "" : map.get("mail");
      String userOrg = getOrgNameString(ldapInfo.get("LdapBase"), map.get("nameStr"));
      String orgId = "-1";
      try {
        if (orgMap.get(userOrg) == null) {
          System.out.println(String.valueOf(userOrg) + " 部门不存在！");
          System.out.println("新增部门：" + userOrg);
          orgMap = (new OpenLDAPOrg()).addOrgOrGetOrgId(userOrg, orgMap);
          orgId = ((String[])orgMap.get(userOrg))[0];
        } else {
          orgId = ((String[])orgMap.get(userOrg))[0];
        } 
        String empId = "0";
        sql = "select emp_id from org_employee where userAccounts='" + account + "'";
        String[][] empExist = base.queryArrayBySql(sql);
        try {
          base.begin();
          if (empExist.length == 0) {
            System.out.println(String.valueOf(empName) + " 用户不存在！");
            System.out.println("新增用户：" + empName);
            buffer = new StringBuffer("");
            if (SystemCommon.getDatabaseType().indexOf("oracle") >= 0) {
              empId = base.queryArrayBySql("select hibernate_sequence.nextval from dual")[0][0];
              buffer.append("insert into org_employee (emp_id,empName,userAccounts,userSimpleName,empstatus,userPassword,empSex,empHeight,empIsMarriage,empState,empWeight,userIsActive,userIsDeleted,userIsFormalUser,userIsSuper,createdOrg,domain_id,userordercode,keyvalidate,empEmail,lastUpdate) values(")

                
                .append(empId).append(",'").append(empName).append("','").append(account).append("','")
                .append(empName).append("','0','5EB72F96E795C92A549DD5A330112621896O',0,0,0,0,0,1,0,1,0,0,0,10000,0")
                .append(",'" + email + "'," + curTime + ")");
              base.executeUpdate(buffer.toString());
              base.executeUpdate("insert into org_organization_user(org_id,emp_id) values(" + orgId + "," + empId + ")");
            } else {
              buffer.append("insert into org_employee (empName,userAccounts,userSimpleName,empstatus,userPassword,empSex,empHeight,empIsMarriage,empState,empWeight,userIsActive,userIsDeleted,userIsFormalUser,userIsSuper,createdOrg,domain_id,userordercode,keyvalidate,empEmail,lastUpdate) values('")

                
                .append(empName).append("','").append(account).append("','")
                .append(empName).append("','0','5EB72F96E795C92A549DD5A330112621896O',0,0,0,0,0,1,0,1,0,0,0,10000,0")
                .append(",'" + email + "'," + curTime + ")");
              base.executeUpdate(buffer.toString());
              rs = base.executeQuery("select max(emp_id) from org_employee");
              if (rs.next())
                empId = rs.getString(1); 
              rs.close();
              base.executeUpdate("insert into org_organization_user(org_id,emp_id) values(" + orgId + "," + empId + ")");
            } 
          } else {
            System.out.println("更新用户:" + empName);
            empId = empExist[0][0];
            sql = "update org_employee set empName='" + empName + "',empEmail='" + email + "',lastUpdate=" + curTime + ",userIsActive=1,userIsDeleted=0 " + 
              "where emp_id=" + empId + " and userAccounts='" + account + "'";
            base.executeUpdate(sql);
          } 
          base.end();
        } catch (Exception e) {
          base.end();
          e.printStackTrace();
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
  
  public Map<String, String[]> getOrgInfo() {
    Map<String, String[]> map = (Map)new HashMap<String, String>();
    String orgSql = "SELECT org_id,orgName,orgIdString,orgNameString FROM org_organization WHERE domain_id=0 AND (orgstatus=0 or orgstatus=1)";
    String[][] orgs = (new DataSourceBase()).queryArrayBySql(orgSql);
    for (int i = 0; i < orgs.length; i++)
      map.put(orgs[i][3], orgs[i]); 
    return map;
  }
  
  public String getOrgNameString(String dn, String nameStr) {
    String orgNameString = "";
    if (nameStr.toLowerCase().contains("ou=")) {
      String[] org = nameStr.toLowerCase().substring(nameStr.indexOf("ou=") + 3).replaceAll(" ", "").split(",ou=");
      for (int i = org.length - 1; i >= 0; i--)
        orgNameString = String.valueOf(orgNameString) + "." + org[i]; 
    } 
    if (dn.toLowerCase().contains("ou=")) {
      String[] org = dn.toLowerCase().substring(dn.indexOf("ou=") + 3, dn.indexOf(",dc=")).replaceAll(" ", "").split(",ou=");
      for (int i = 0; i < org.length; i++)
        orgNameString = "." + org[i] + orgNameString; 
    } 
    if (!"".equals(orgNameString))
      return orgNameString.substring(1); 
    return (new DataSourceBase()).queryStrBySql("SELECT orgnamestring FROM org_organization WHERE orgparentorgId=-1");
  }
}
