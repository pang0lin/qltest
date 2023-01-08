package com.js.system.util;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperateOrg {
  private String dataBaseType = SystemCommon.getDatabaseType();
  
  public Map<String, String[]> addOrgOrGetOrgId(String orgNameString, Map<String, String[]> map) {
    if (!orgNameString.equals("")) {
      String[] orgInfoAll = getOrgId(orgNameString);
      if ("".equals(orgInfoAll[0])) {
        if (orgNameString.contains(".")) {
          String[] orgNames = orgNameString.split("\\.");
          String[] orgInfoOld = { "0", "", "_500000$-1$", "", "0" };
          for (int i = 0; i < orgNames.length; i++) {
            String[] orgInfo = (String[])null;
            if ("".equals(orgInfoOld[1])) {
              orgInfoOld[3] = orgNames[i];
            } else {
              orgInfoOld[3] = String.valueOf(orgInfoOld[3]) + "." + orgNames[i];
            } 
            if (orgNameString.equals(orgInfoOld[3])) {
              orgInfo = orgInfoAll;
            } else {
              orgInfo = getOrgId(orgInfoOld[3]);
            } 
            String[] addOrg = (String[])null;
            if ("".equals(orgInfo[0])) {
              addOrg = insertOrg(orgNames[i], orgInfoOld[3], orgInfoOld[2], orgInfoOld[0], orgInfoOld[4]);
              orgInfoOld = new String[] { addOrg[0], orgNames[i], addOrg[2], orgInfoOld[3], addOrg[4] };
            } else {
              orgInfoOld = orgInfo;
              addOrg = orgInfoOld;
            } 
            if (map.get(orgInfoOld[3]) == null)
              map.put(orgInfoOld[3], addOrg); 
          } 
        } else {
          map.put(orgNameString, insertOrg(orgNameString, orgNameString, "_500000$-1$", "0", "0"));
        } 
      } else {
        map.put(orgNameString, orgInfoAll);
      } 
    } 
    return map;
  }
  
  private String[] insertOrg(String orgName, String orgNameString, String orgIdString, String parentOrg, String status) {
    System.out.println("新增部门：" + orgNameString);
    int level = (orgNameString.split("\\.")).length - 1;
    DataSourceBase base = new DataSourceBase();
    String sql = "";
    String[] orgInfo = (String[])null;
    String orgId = "";
    int orderNum = getOrderNum();
    try {
      base.begin();
      if (this.dataBaseType.indexOf("mysql") >= 0) {
        sql = "INSERT INTO org_organization (ORGPARENTORGID,ORGMANAGEREMPID,ORGMANAGEREMPNAME,ORGNAME,ORGSIMPLENAME,ORGCODE,ORGORDERCODE,ORGFOUNDDATE,ORGDESCRIPTE,ORGLEVEL,ORGHASJUNIOR,ORGSTATUS,ORGIDSTRING,ORGNAMESTRING,orghaschannel,RTXDEPTID,RTXDEPTPID,ORGBANNER,ORGSERIAL,USERORDERCODE,DOMAIN_ID,guid,lastupdate,orgtype,orgChannelType,orgChannelUrl) VALUES(" + 


          
          parentOrg + ",'','','" + orgName + "'," + 
          "'',NULL,'" + orderNum + "',NULL,'','" + level + "','0','" + status + "'," + 
          "'_500000$-1$_500000$123$','" + orgNameString + "','0',NULL,NULL,NULL,'" + orgName + "',NULL," + 
          "'0',NULL,NULL,'1','0',NULL)";
        base.executeUpdate(sql);
        orgId = base.getArrayQuery("select max(ORG_ID) from org_organization", 1)[0][0];
        orgIdString = String.valueOf(orgIdString) + "_" + orderNum + "$" + orgId + "$";
        base.executeUpdate("update org_organization set ORGIDSTRING ='" + orgIdString + "' where org_id=" + orgId);
        orgInfo = new String[] { orgId, orgName, orgIdString, orgNameString, status };
      } else {
        orgId = base.getArrayQuery("select hibernate_sequence.nextval from dual", 1)[0][0];
        orgIdString = String.valueOf(orgIdString) + "_" + orderNum + "$" + orgId + "$";
        sql = "INSERT INTO org_organization (org_id,ORGPARENTORGID,ORGMANAGEREMPID,ORGMANAGEREMPNAME,ORGNAME,ORGSIMPLENAME,ORGCODE,ORGORDERCODE,ORGFOUNDDATE,ORGDESCRIPTE,ORGLEVEL,ORGHASJUNIOR,ORGSTATUS,ORGIDSTRING,ORGNAMESTRING,orghaschannel,RTXDEPTID,RTXDEPTPID,ORGBANNER,ORGSERIAL,USERORDERCODE,DOMAIN_ID,guid,lastupdate,orgtype,orgChannelType,orgChannelUrl) VALUES(" + 


          
          orgId + "," + parentOrg + ",'','','" + orgName + "'," + 
          "'',NULL,'" + orderNum + "',NULL,'','" + level + "','0','" + status + "'," + 
          "'" + orgIdString + "','" + orgNameString + "','0',NULL,NULL,NULL,'" + orgName + "',NULL," + 
          "'0',NULL,NULL,'1','0',NULL)";
        base.executeUpdate(sql);
        orgInfo = new String[] { orgId, orgName, orgIdString, orgNameString, status };
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return orgInfo;
  }
  
  public String[] getOrgId(String orgNameString) {
    String[] orgInfo = { "", "", "", "", "0" };
    String sql = "SELECT org_id,orgName,orgIdString,orgNameString,orgstatus FROM org_organization WHERE domain_id=0 AND (orgstatus=0 or orgstatus=1) and orgNameString='" + 
      orgNameString + "'";
    String[][] orgs = (new DataSourceBase()).queryArrayBySql(sql);
    if (orgs.length > 0)
      orgInfo = orgs[0]; 
    return orgInfo;
  }
  
  public void updateOrg(List<Map<String, String>> orgList) {
    DataSourceBase base = new DataSourceBase();
    Map<String, String[]> allOrgMap = getOrgInfo();
    try {
      base.begin();
      for (int i = 0; i < orgList.size(); i++) {
        Map<String, String> orgMap = orgList.get(i);
        String[] orgInfo = (String[])null;
        if (allOrgMap.get(orgMap.get("orgnamestring")) == null)
          allOrgMap = addOrgOrGetOrgId(orgMap.get("orgnamestring"), allOrgMap); 
        if (allOrgMap.get(orgMap.get("orgnamestring")) != null) {
          orgInfo = allOrgMap.get(orgMap.get("orgnamestring"));
          String updateSql = "";
          for (String key : orgMap.keySet()) {
            if (!",orgnamestring,".contains("," + key + ","))
              updateSql = String.valueOf(updateSql) + "," + key + "=" + (String)orgMap.get(key); 
          } 
          updateSql = "update org_organization set org_id=" + orgInfo[0] + updateSql + " where org_id=" + orgInfo[0];
          System.out.println("更新部门：" + (String)orgMap.get("orgnamestring"));
          base.addBatch(updateSql);
        } 
      } 
      base.executeBatch();
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
  }
  
  public Map<String, String[]> getOrgInfo() {
    Map<String, String[]> map = (Map)new HashMap<String, String>();
    String orgSql = "SELECT org_id,orgName,orgIdString,orgNameString,orgstatus FROM org_organization WHERE domain_id=0 AND (orgstatus=0 or orgstatus=1)";
    String[][] orgs = (new DataSourceBase()).queryArrayBySql(orgSql);
    for (int i = 0; i < orgs.length; i++)
      map.put(orgs[i][3], orgs[i]); 
    return map;
  }
  
  public int getOrderNum() {
    int num = (int)(Math.random() * 40.0D);
    while (num == 0)
      num = (int)(Math.random() * 40.0D); 
    if (num % 2 == 0)
      num *= -1; 
    return 500000 + num * 10000;
  }
}
