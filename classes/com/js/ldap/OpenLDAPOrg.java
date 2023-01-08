package com.js.ldap;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.util.Map;

public class OpenLDAPOrg {
  private String dataBaseType = SystemCommon.getDatabaseType();
  
  public Map<String, String[]> addOrgOrGetOrgId(String orgNameString, Map<String, String[]> map) {
    String[] orgInfoAll = getOrgId(orgNameString);
    if ("".equals(orgInfoAll[0])) {
      if (orgNameString.contains(".")) {
        String[] orgNames = orgNameString.split("\\.");
        String[] orgInfoOld = { "0", "", "_500000$-1$", "" };
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
            addOrg = insertOrg(orgNames[i], orgInfoOld[3], orgInfoOld[2], orgInfoOld[0]);
            orgInfoOld = new String[] { addOrg[0], orgNames[i], String.valueOf(orgInfoOld[2]) + "_500000$" + addOrg[0] + "$", orgInfoOld[3] };
          } else {
            orgInfoOld = orgInfo;
            addOrg = orgInfoOld;
          } 
          if (map.get(orgInfoOld[3]) == null)
            map.put(orgInfoOld[3], addOrg); 
        } 
      } else {
        map.put(orgNameString, insertOrg(orgNameString, orgNameString, "_500000$-1$", "0"));
      } 
    } else {
      map.put(orgNameString, orgInfoAll);
    } 
    return map;
  }
  
  public String[] insertOrg(String orgName, String orgNameString, String orgIdString, String parentOrg) {
    DataSourceBase base = new DataSourceBase();
    String sql = "";
    String[] orgInfo = (String[])null;
    String orgId = "";
    try {
      base.begin();
      if (this.dataBaseType.indexOf("mysql") >= 0) {
        sql = "INSERT INTO org_organization (ORGPARENTORGID,ORGMANAGEREMPID,ORGMANAGEREMPNAME,ORGNAME,ORGSIMPLENAME,ORGCODE,ORGORDERCODE,ORGFOUNDDATE,ORGDESCRIPTE,ORGLEVEL,ORGHASJUNIOR,ORGSTATUS,ORGIDSTRING,ORGNAMESTRING,orghaschannel,RTXDEPTID,RTXDEPTPID,ORGBANNER,ORGSERIAL,USERORDERCODE,DOMAIN_ID,guid,lastupdate,orgtype,orgChannelType,orgChannelUrl) VALUES(" + 


          
          parentOrg + ",'','','" + orgName + "'," + 
          "'',NULL,'500000',NULL,'','0','0'," + 
          "'0','_500000$-1$_500000$123$','" + orgNameString + "','0',NULL,NULL,NULL,'" + orgName + "',NULL," + 
          "'0',NULL,NULL,'1','0',NULL)";
        base.executeUpdate(sql);
        orgId = base.getArrayQuery("select max(ORG_ID) from org_organization", 1)[0][0];
        orgIdString = String.valueOf(orgIdString) + "_500000$" + orgId + "$";
        base.executeUpdate("update org_organization set ORGIDSTRING ='" + orgIdString + "' where org_id=" + orgId);
        orgInfo = new String[] { orgId, orgName, orgIdString, orgNameString };
      } else {
        orgId = base.getArrayQuery("select hibernate_sequence.nextval from dual", 1)[0][0];
        orgIdString = String.valueOf(orgIdString) + "_500000$" + orgId + "$";
        sql = "INSERT INTO org_organization (org_id,ORGPARENTORGID,ORGMANAGEREMPID,ORGMANAGEREMPNAME,ORGNAME,ORGSIMPLENAME,ORGCODE,ORGORDERCODE,ORGFOUNDDATE,ORGDESCRIPTE,ORGLEVEL,ORGHASJUNIOR,ORGSTATUS,ORGIDSTRING,ORGNAMESTRING,orghaschannel,RTXDEPTID,RTXDEPTPID,ORGBANNER,ORGSERIAL,USERORDERCODE,DOMAIN_ID,guid,lastupdate,orgtype,orgChannelType,orgChannelUrl) VALUES(" + 


          
          orgId + "," + parentOrg + ",'','','" + orgName + "'," + 
          "'',NULL,'500000',NULL,'','0','0'," + 
          "'0','" + orgIdString + "','" + orgNameString + "','0',NULL,NULL,NULL,'" + orgName + "',NULL," + 
          "'0',NULL,NULL,'1','0',NULL)";
        base.executeUpdate(sql);
        orgInfo = new String[] { orgId, orgName, orgIdString, orgNameString };
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return orgInfo;
  }
  
  public String[] getOrgId(String orgNameString) {
    String[] orgInfo = { "", "", "", "" };
    String sql = "SELECT org_id,orgName,orgIdString,orgNameString FROM org_organization WHERE domain_id=0 AND (orgstatus=0 or orgstatus=1) and orgNameString='" + 
      orgNameString + "'";
    String[][] orgs = (new DataSourceBase()).queryArrayBySql(sql);
    if (orgs.length > 0)
      orgInfo = orgs[0]; 
    return orgInfo;
  }
}
