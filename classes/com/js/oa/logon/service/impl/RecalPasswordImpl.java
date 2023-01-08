package com.js.oa.logon.service.impl;

import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RecalPasswordImpl {
  public Map<String, String> getUserInfo(String userName) {
    Map<String, String> resultMap = new HashMap<String, String>();
    DataSourceBase base = null;
    try {
      base = new DataSourceBase();
      base.begin();
      ResultSet rs = base.executeQuery("select empname,empemail,userisactive,useraccounts from org_employee WHERE useraccounts='" + userName + "'");
      if (rs.next()) {
        resultMap.put("name", rs.getString("empname"));
        resultMap.put("empemail", rs.getString("empemail"));
        resultMap.put("userisactive", rs.getString("userisactive"));
        resultMap.put("useraccounts", rs.getString("useraccounts"));
      } 
      rs.close();
      base.end();
    } catch (Exception ex) {
      if (base != null)
        base.end(); 
      ex.printStackTrace();
    } 
    return resultMap;
  }
  
  public String randomWord(int length) {
    if (length < 1)
      return ""; 
    String[] arr = { 
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", 
        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", 
        "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", 
        "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", 
        "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", 
        "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", 
        "Y", "Z" };
    StringBuffer bufString = new StringBuffer();
    for (int i = 0; i < length; i++)
      bufString.append(arr[(int)Math.round(Math.random() * (arr.length - 1))]); 
    return bufString.toString();
  }
  
  public String setRecallPwdInfo(Map<String, String> map) {
    String uid = UUID.randomUUID().toString().toUpperCase();
    StringBuffer sb = new StringBuffer("insert into org_employee_rePwd(repwd_id,repwd_userAccount,repwd_code,repwd_applyDate,repwd_applyIp,repwd_status) values('");
    sb.append(String.valueOf(uid) + "','");
    sb.append(String.valueOf(map.get("useraccounts")) + "','");
    sb.append(String.valueOf(map.get("code")) + "','");
    sb.append(String.valueOf((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date())) + "','");
    sb.append(String.valueOf(map.get("ip")) + "','");
    sb.append("1')");
    DataSourceBase base = null;
    try {
      base = new DataSourceBase();
      base.begin();
      base.executeSQL("update org_employee_rePwd set repwd_status='0' where repwd_userAccount='" + (String)map.get("useraccounts") + "' and repwd_status='1'");
      base.executeSQL(sb.toString());
      base.end();
    } catch (Exception ex) {
      if (base != null)
        base.end(); 
      uid = "";
      ex.printStackTrace();
    } 
    return uid;
  }
  
  public Map<String, String> getResetInfo(String userName, String code) {
    Map<String, String> resultMap = new HashMap<String, String>();
    DataSourceBase base = null;
    try {
      base = new DataSourceBase();
      base.begin();
      ResultSet rs = base.executeQuery("select repwd_id,repwd_userAccount,repwd_code,repwd_applyDate,repwd_status from org_employee_rePwd WHERE repwd_userAccount='" + userName + "' and repwd_code='" + code + "'");
      if (rs.next()) {
        resultMap.put("repwd_id", rs.getString("repwd_id"));
        resultMap.put("repwd_userAccount", rs.getString("repwd_userAccount"));
        resultMap.put("repwd_code", rs.getString("repwd_code"));
        resultMap.put("repwd_applyDate", rs.getString("repwd_applyDate"));
        resultMap.put("repwd_status", rs.getString("repwd_status"));
      } 
      rs.close();
      base.end();
    } catch (Exception ex) {
      if (base != null)
        base.end(); 
      ex.printStackTrace();
    } 
    return resultMap;
  }
  
  public Map<String, String> getResetInfoById(String sid) {
    Map<String, String> resultMap = new HashMap<String, String>();
    DataSourceBase base = null;
    try {
      base = new DataSourceBase();
      base.begin();
      ResultSet rs = base.executeQuery("select repwd_id,repwd_userAccount,repwd_code,repwd_applyDate,repwd_status from org_employee_rePwd WHERE repwd_id='" + sid + "'");
      if (rs.next()) {
        resultMap.put("repwd_id", rs.getString("repwd_id"));
        resultMap.put("repwd_userAccount", rs.getString("repwd_userAccount"));
        resultMap.put("repwd_code", rs.getString("repwd_code"));
        resultMap.put("repwd_applyDate", rs.getString("repwd_applyDate"));
        resultMap.put("repwd_status", rs.getString("repwd_status"));
      } 
      rs.close();
      base.end();
    } catch (Exception ex) {
      if (base != null)
        base.end(); 
      ex.printStackTrace();
    } 
    return resultMap;
  }
  
  public boolean resetPwd(String sid, String password, String ip) {
    Map<String, String> map = getResetInfoById(sid);
    String updatePwdSql = "update org_employee set userpassword ='" + password + "' WHERE useraccounts='" + (String)map.get("repwd_userAccount") + "'";
    String updateRecordSql = "update org_employee_rePwd set repwd_status='0',repwd_date='" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) + "',repwd_ip='" + ip + "' WHERE repwd_id='" + sid + "'";
    boolean res = true;
    DataSourceBase base = null;
    try {
      base = new DataSourceBase();
      base.begin();
      base.executeSQL(updatePwdSql);
      base.executeSQL(updateRecordSql);
      base.end();
    } catch (Exception ex) {
      if (base != null)
        base.end(); 
      ex.printStackTrace();
      res = false;
    } 
    return res;
  }
}
