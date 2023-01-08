package com.js.oa.logon.service;

import com.js.oa.logon.bean.LogonEJBBean;
import java.sql.SQLException;
import java.util.HashMap;

public class LogonBD {
  public HashMap logon(String userName, String userPassword, String userIP, String serverIP, String sessionId, String domainAccount, String flag) {
    HashMap userInfo = null;
    try {
      LogonEJBBean bean = new LogonEJBBean();
      userInfo = bean.logon(userName, userPassword, userIP, serverIP, sessionId, domainAccount, flag);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return userInfo;
  }
  
  public HashMap logonDealWith(String userName, String userPassword, String userIP, String serverIP, String sessionId, String domainAccount, String flag) {
    HashMap userInfo = null;
    try {
      LogonEJBBean bean = new LogonEJBBean();
      userInfo = bean.logon2(userName, userPassword, userIP, serverIP, sessionId, domainAccount, flag);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return userInfo;
  }
  
  public String validateIp(String userAccount, String userPassword, String userIP, String sessionId) throws Exception {
    String re = "Y";
    LogonEJBBean bean = new LogonEJBBean();
    re = bean.validateIp(userAccount, userPassword, userIP, sessionId);
    return re;
  }
  
  public void delForVb(String userId) throws SQLException {
    LogonEJBBean bean = new LogonEJBBean();
    bean.delForVb(userId);
  }
  
  public void updateUserTime(String userId, String sessionId) throws SQLException {
    LogonEJBBean bean = new LogonEJBBean();
    bean.updateUserTime(userId, sessionId);
  }
  
  public String getUserAccountByNumber(String userNumber) {
    LogonEJBBean bean = new LogonEJBBean();
    return bean.getUserAccountByNumber(userNumber);
  }
}
