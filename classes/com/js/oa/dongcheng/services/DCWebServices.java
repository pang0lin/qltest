package com.js.oa.dongcheng.services;

import net.sf.json.JSONObject;

public class DCWebServices {
  private DongChengService dcs = new DongChengService();
  
  public String insertOrganization(String jsonStr) {
    String result = "";
    if (jsonStr == null || "".equals(jsonStr)) {
      result = "{result:'璇疯緭鍏ュ弬鏁般�'}";
    } else {
      try {
        JSONObject json = JSONObject.fromObject(jsonStr);
        String orgGUID = json.getString("orgGUID");
        String orgName = json.getString("orgName");
        String orgDescripte = json.getString("orgDescripte");
        String parentOrgGUID = json.getString("parentOrgGUID");
        String orderOrgGUID = json.getString("orderOrgGUID");
        String tag = json.getString("tag");
        result = this.dcs.insertOrganization(orgGUID, orgName, orgDescripte, parentOrgGUID, orderOrgGUID, tag);
      } catch (Exception e) {
        e.printStackTrace();
        result = "{result:'" + e.getMessage() + "'}";
      } 
    } 
    return result;
  }
  
  public String updateOrganization(String jsonStr) {
    String result = "";
    if (jsonStr == null || "".equals(jsonStr)) {
      result = "{result:'璇疯緭鍏ュ弬鏁般�'}";
    } else {
      try {
        JSONObject json = JSONObject.fromObject(jsonStr);
        String orgGUID = json.getString("orgGUID");
        String orgName = json.getString("orgName");
        String orgDescripte = json.getString("orgDescripte");
        String parentOrgGUID = json.getString("parentOrgGUID");
        String orderOrgGUID = json.getString("orderOrgGUID");
        String tag = json.getString("tag");
        result = this.dcs.updateOrganization(orgGUID, orgName, orgDescripte, parentOrgGUID, orderOrgGUID, tag);
      } catch (Exception e) {
        e.printStackTrace();
        result = "{result:'" + e.getMessage() + "'}";
      } 
    } 
    return result;
  }
  
  public String insertUser(String jsonStr) {
    String result = "";
    if (jsonStr == null || "".equals(jsonStr)) {
      result = "{result:'璇疯緭鍏ュ弬鏁般�'}";
    } else {
      try {
        JSONObject json = JSONObject.fromObject(jsonStr);
        String userGUID = json.getString("userGUID");
        String userAccounts = json.getString("userAccounts");
        String userPassword = json.getString("userPassword");
        String empName = json.getString("empName");
        String empSex = json.getString("empSex");
        String orgGUID = json.getString("orgGUID");
        String iPContrl = json.getString("iPContrl");
        String ipContrlBeginTime = json.getString("ipContrlBeginTime");
        String ipContrlEndTime = json.getString("ipContrlEndTime");
        String empLeader = json.getString("empLeader");
        String empNumber = json.getString("empNumber");
        result = this.dcs.insertUser(userGUID, userAccounts, userPassword, empName, empSex, orgGUID, iPContrl, 
            ipContrlBeginTime, ipContrlEndTime, empLeader, empNumber);
      } catch (Exception e) {
        e.printStackTrace();
        result = "{result:'" + e.getMessage() + "'}";
      } 
    } 
    return result;
  }
  
  public String updateUser(String jsonStr) {
    String result = "";
    if (jsonStr == null || "".equals(jsonStr)) {
      result = "{result:'璇疯緭鍏ュ弬鏁般�'}";
    } else {
      try {
        JSONObject json = JSONObject.fromObject(jsonStr);
        String userGUID = json.getString("userGUID");
        String userAccounts = json.getString("userAccounts");
        String userPassword = json.getString("userPassword");
        String empName = json.getString("empName");
        String empSex = json.getString("empSex");
        String orgGUID = json.getString("orgGUID");
        String iPContrl = json.getString("iPContrl");
        String ipContrlBeginTime = json.getString("ipContrlBeginTime");
        String ipContrlEndTime = json.getString("ipContrlEndTime");
        String empLeader = json.getString("empLeader");
        String empNumber = json.getString("empNumber");
        result = this.dcs.updateUser(userGUID, userAccounts, userPassword, empName, empSex, orgGUID, iPContrl, 
            ipContrlBeginTime, ipContrlEndTime, empLeader, empNumber);
      } catch (Exception e) {
        e.printStackTrace();
        result = "{result:'" + e.getMessage() + "'}";
      } 
    } 
    return result;
  }
}
