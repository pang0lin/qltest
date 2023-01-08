package com.js.system.util;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperateUser {
  public void userOperate(List<Map<String, String>> list, Long curTime, String updateOrg, String notDel, String defaultOrg) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    StringBuffer buffer = new StringBuffer("");
    String sql = "";
    Map<String, String[]> orgMap = getOrgInfo();
    for (int i = 0; i < list.size(); i++) {
      Map<String, String> map = list.get(i);
      String account = (map.get("useraccounts") == null) ? "" : map.get("useraccounts");
      String empName = (map.get("empname") == null) ? "" : map.get("empname");
      String userNumber = (map.get("empnumber") == null) ? "" : map.get("empnumber");
      String empSex = (map.get("empsex") == null) ? "0" : ("男".equals(map.get("empsex")) ? "0" : "1");
      if ("".equals(account) || "null".equalsIgnoreCase(account))
        account = userNumber; 
      if (!"".equals(account) && !"null".equalsIgnoreCase(account)) {
        String userOrg = (map.get("orgnamestring") != null) ? map.get("orgnamestring") : (
          String.valueOf((new DataSourceBase()).queryStrBySql("SELECT orgnamestring FROM org_organization WHERE orgparentorgId=-1")) + ".其他部门");
        String orgId = "-1";
        String empStatus = "1";
        String empDuty = "";
        if (!"".equals(userOrg))
          if (orgMap.get(userOrg) == null) {
            if (!defaultOrg.equals("")) {
              if (userOrg.contains(".本科生.")) {
                userOrg = "兰州大学.本科生.其他部门";
              } else if (userOrg.contains(".研究生.")) {
                userOrg = "兰州大学.研究生.其他部门";
              } else {
                userOrg = defaultOrg;
              } 
            } else {
              System.out.println(String.valueOf(userOrg) + " 部门不存在！新增部门：" + userOrg);
              if (userOrg.replace("..", ".").endsWith("."))
                userOrg = String.valueOf(userOrg) + "其他部门"; 
            } 
            if (orgMap.get(userOrg) == null)
              orgMap = (new OperateOrg()).addOrgOrGetOrgId(userOrg, orgMap); 
            orgId = ((String[])orgMap.get(userOrg))[0];
            if ("1".equals(((String[])orgMap.get(userOrg))[4]))
              empStatus = "0"; 
          } else {
            orgId = ((String[])orgMap.get(userOrg))[0];
            if ("1".equals(((String[])orgMap.get(userOrg))[4]))
              empStatus = "0"; 
          }  
        System.out.println("部门：" + userOrg);
        if (userOrg.contains(".本科生.") || userOrg.contains("本科生.")) {
          empDuty = "本科生";
        } else if (userOrg.contains(".研究生.") || userOrg.contains("研究生.")) {
          empDuty = "研究生";
        } 
        System.out.println("获得职务：" + empDuty);
        if (map.get("userisactive") != null)
          empStatus = map.get("userisactive"); 
        String empId = "0";
        sql = "select emp_id from org_employee where userAccounts='" + account + "'" + (userNumber.equals("") ? "" : (" or empnumber='" + userNumber + "'"));
        String[][] empExist = base.queryArrayBySql(sql);
        try {
          base.begin();
          if (empExist.length == 0) {
            System.out.println(String.valueOf(empName) + " 用户不存在！新增用户：" + empName);
            String fieldSql = "";
            String valueSql = "";
            for (String key : map.keySet()) {
              if (",useraccounts,empname,empnumber,orgnamestring,empsex,userisactive,".indexOf("," + key.toLowerCase() + ",") < 0) {
                fieldSql = String.valueOf(fieldSql) + "," + key;
                valueSql = String.valueOf(valueSql) + "," + (String)map.get(key);
              } 
            } 
            buffer = new StringBuffer("");
            if (SystemCommon.getDatabaseType().indexOf("oracle") >= 0) {
              empId = base.getArrayQuery("select hibernate_sequence.nextval from dual", 1)[0][0];
              buffer.append("insert into org_employee (emp_id,empName,userAccounts,userSimpleName,empstatus,userPassword,empSex,empHeight,empIsMarriage,")
                .append("empState,empWeight,userIsActive,userIsDeleted,userIsFormalUser,createdOrg,domain_id,userordercode,keyvalidate")
                .append(",userIsSuper,usersuperBegin,usersuperEnd,lastUpdate,empnumber,empduty" + fieldSql + ") values(")
                .append(empId).append(",'").append(empName).append("','").append(account).append("','")
                .append(empName).append("','0','5EB72F96E795C92A549DD5A330112621896O'," + empSex + ",0,0,0,0," + empStatus + ",0,1,0,0,10000,0,")
                .append("1,to_date('2014-01-01 00:00:00','yyyy-mm-dd hh24:mi:ss'),to_date('2020-01-01 00:00:00','yyyy-mm-dd hh24:mi:ss')")
                .append("," + curTime + ",'" + userNumber + "','" + empDuty + "'" + valueSql + ")");
              base.executeUpdate(buffer.toString());
            } else {
              buffer.append("insert into org_employee (empName,userAccounts,userSimpleName,empstatus,userPassword,empSex,empHeight,empIsMarriage,")
                .append("empState,empWeight,userIsActive,userIsDeleted,userIsFormalUser,createdOrg,domain_id,userordercode,keyvalidate")
                .append(",userIsSuper,usersuperBegin,usersuperEnd,lastUpdate,empnumber,empduty" + fieldSql + ") values('")
                .append(empName).append("','").append(account).append("','")
                .append(empName).append("','0','5EB72F96E795C92A549DD5A330112621896O'," + empSex + ",0,0,0,0," + empStatus + ",0,1,0,0,10000,0,")
                .append("1,'2014-01-01 00:00:00','2020-01-01 00:00:00'")
                .append("," + curTime + ",'" + userNumber + "','" + empDuty + "'" + valueSql + ")");
              base.executeUpdate(buffer.toString());
              rs = base.executeQuery("select max(emp_id) from org_employee");
              if (rs.next())
                empId = rs.getString(1); 
              rs.close();
            } 
            base.executeUpdate("insert into org_organization_user(org_id,emp_id) values(" + orgId + "," + empId + ")");
          } else {
            System.out.println(String.valueOf(empName) + " 用户存在！ 更新用户:" + empName);
            String updateSql = "";
            for (String key : map.keySet()) {
              if (",useraccounts,empname,empnumber,orgnamestring,empsex,".indexOf("," + key.toLowerCase() + ",") < 0)
                updateSql = String.valueOf(updateSql) + "," + key + "=" + (String)map.get(key); 
            } 
            empId = empExist[0][0];
            sql = "update org_employee set userAccounts='" + account + "',empSex=" + empSex + ",empnumber='" + userNumber + "',empName='" + empName + "',lastUpdate=" + 
              curTime + ",userIsActive=" + empStatus + ",userIsDeleted=0" + updateSql + " where emp_id=" + empId;
            base.executeUpdate(sql);
            if (!"".equals(empDuty)) {
              System.out.println("更新【" + empName + "】职务：" + empDuty);
              base.executeUpdate("update org_employee set empduty='" + empDuty + "' where emp_id=" + empId);
            } 
            if ("1".equals(updateOrg) || "3".equals(updateOrg))
              base.executeUpdate("update org_organization_user set org_id=" + orgId + " where emp_id=" + empId); 
          } 
          base.end();
        } catch (Exception e) {
          base.end();
          e.printStackTrace();
        } 
      } 
    } 
    if ("2".equals(updateOrg) || "3".equals(updateOrg))
      try {
        base.begin();
        base.executeUpdate("update org_employee set userIsActive=0,userIsDeleted=0 where lastUpdate<" + curTime + " and useraccounts not like '%" + notDel + "%'");
        base.end();
      } catch (Exception e) {
        base.end();
        e.printStackTrace();
      }  
  }
  
  public static Map<String, String[]> getOrgInfo() {
    Map<String, String[]> map = (Map)new HashMap<String, String>();
    String orgSql = "SELECT org_id,orgName,orgIdString,orgNameString,orgstatus FROM org_organization WHERE domain_id=0 AND (orgstatus=0 or orgstatus=1)";
    String[][] orgs = (new DataSourceBase()).queryArrayBySql(orgSql);
    for (int i = 0; i < orgs.length; i++)
      map.put(orgs[i][3], orgs[i]); 
    return map;
  }
  
  public static Map<String, Map> getOrgSimpleInfo() {
    Map<String, Map> map = new HashMap<String, Map>();
    Map<String, String> twoLevelMap = new HashMap<String, String>();
    Map<String, String> twoLevelIdAndNameMap = new HashMap<String, String>();
    Map<String, String> thirdLevelMap = new HashMap<String, String>();
    Map<String, String> orgStatusMap = new HashMap<String, String>();
    String orgSql = "SELECT org_id,orgName,orgIdString,orgNameString,orgstatus,orgLevel FROM org_organization WHERE domain_id=0 AND (orgstatus=0 or orgstatus=1) order by orgnameString";
    String[][] orgs = (new DataSourceBase()).queryArrayBySql(orgSql);
    int orgLevel = 0;
    for (int i = 0; i < orgs.length; i++) {
      String orgName = orgs[i][3];
      String hrOrgName = orgName;
      orgLevel = Integer.parseInt(orgs[i][5]);
      orgStatusMap.put(orgs[i][0], orgs[i][4]);
      if (orgLevel == 1 && 
        orgName != null && (orgName.startsWith("研究生.") || orgName.startsWith("本科生.") || orgName.equals("兰州大学.其他部门"))) {
        twoLevelMap.put(orgName, orgs[i][0]);
        twoLevelIdAndNameMap.put(orgs[i][0], orgName);
      } 
      if (orgLevel == 2)
        if (orgName != null && orgName.startsWith("兰州大学.")) {
          String[] orgNameArr = orgName.split("\\.");
          if (orgNameArr.length > 2) {
            hrOrgName = "兰州大学." + orgNameArr[2];
            twoLevelMap.put(hrOrgName, orgs[i][0]);
            twoLevelIdAndNameMap.put(orgs[i][0], hrOrgName);
          } 
        }  
      if (orgLevel > 2)
        if (orgName != null && orgName.startsWith("兰州大学.")) {
          String[] orgNameArr = orgName.split("\\.");
          if (orgNameArr.length > 2) {
            hrOrgName = "兰州大学." + orgNameArr[2];
            thirdLevelMap.put(orgs[i][0], twoLevelMap.get(hrOrgName));
          } 
        }  
    } 
    map.put("twoLevelMap", twoLevelMap);
    map.put("twoLevelIdAndNameMap", twoLevelIdAndNameMap);
    map.put("thirdLevelMap", thirdLevelMap);
    map.put("orgStatusMap", orgStatusMap);
    return map;
  }
}
