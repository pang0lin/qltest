package com.yunshipei.action;

import com.js.oa.logon.service.LogonBD;
import com.js.util.config.SystemCommon;
import com.js.util.config.UploadConfig;
import com.js.util.util.DataSourceBase;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class GMWAppLogonAction {
  private static Logger log = Logger.getLogger(GMWAppLogonAction.class);
  
  private static final String DOMAIN_ACCOUNT = "jiusi";
  
  private static final String CHECK_PASSWORD_TRUE = "1";
  
  private static final String CHECK_PASSWORD_FALSE = "0";
  
  public static boolean logon(HttpServletRequest request, HttpServletResponse response) {
    boolean isLogon = false;
    String userId = request.getParameter("identity");
    String[] accountAndPassword = getAccountAndPassword(userId);
    String userAccount = accountAndPassword[0];
    String userPassword = accountAndPassword[1];
    log.debug("userAccount = " + userAccount);
    log.debug("userPassword = " + userPassword);
    HttpSession session = getSessionWithBrowserType(request);
    String sessionId = session.getId();
    log.debug("sessionId = " + sessionId);
    String[] userIpAndServerIp = getUserIpAndServerIp(request);
    String userIP = userIpAndServerIp[0];
    String serverIP = userIpAndServerIp[1];
    session.setAttribute("userIP", userIP);
    HashMap<String, String> userInfo = (new LogonBD()).logon(userAccount, userPassword, userIP, serverIP, sessionId, "jiusi", "1");
    userInfo.put("userAccount", userAccount);
    isLogon = presetHttpSession(request, userInfo);
    if (isLogon) {
      Locale loc = new Locale("zh_cn");
      session.setAttribute("org.apache.struts.action.LOCALE", loc);
      session.setAttribute("domainId", "0");
    } else {
      session = null;
    } 
    log.debug("isLogon = " + isLogon);
    return isLogon;
  }
  
  private static String[] getUserIpAndServerIp(HttpServletRequest request) {
    String[] userIpAndServerIp = { "", "" };
    userIpAndServerIp[0] = request.getRemoteAddr();
    try {
      userIpAndServerIp[1] = InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } 
    log.debug("userIP = " + userIpAndServerIp[0]);
    log.debug("serverIP = " + userIpAndServerIp[1]);
    return userIpAndServerIp;
  }
  
  private static HttpSession getSessionWithBrowserType(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String browserVersion = request.getHeader("User-Agent");
    log.debug("browserVersion = " + browserVersion);
    browserTypeToSession(browserVersion, session);
    return session;
  }
  
  private static void browserTypeToSession(String browserVersion, HttpSession session) {
    if (browserVersion.indexOf("MSIE") >= 0) {
      session.setAttribute("browserVersion", "MSIEx");
      if (browserVersion.indexOf("MSIE 6.0") >= 0) {
        session.setAttribute("browserVersion", "MSIE6");
      } else if (browserVersion.indexOf("MSIE 10.0") >= 0) {
        session.setAttribute("browserVersion", "IE10");
      } 
    } else if (browserVersion.indexOf("Trident/7.0") >= 0) {
      session.setAttribute("browserVersion", "IE11");
    } else if (browserVersion.indexOf("Firefox") >= 0) {
      session.setAttribute("browserVersion", "Firefox");
    } else if (browserVersion.indexOf("Chrome") >= 0) {
      session.setAttribute("browserVersion", "Chrome");
    } else if (browserVersion.indexOf("Safari") >= 0) {
      session.setAttribute("browserVersion", "Safari");
    } else {
      session.setAttribute("browserVersion", "MSIEx");
    } 
    if (browserVersion.indexOf("iPad") >= 0) {
      session.setAttribute("OSType", "ipad");
    } else if (browserVersion.indexOf("Android") >= 0) {
      session.setAttribute("OSType", "Android");
    } else if (browserVersion.indexOf("iPhone") >= 0) {
      session.setAttribute("OSType", "iPhone");
    } else {
      session.setAttribute("OSType", "pc");
    } 
  }
  
  private static String[] getAccountAndPassword(String identity) {
    String[] accountAndPassword = { "", "" };
    String sql = "SELECT useraccounts, userpassword FROM org_employee WHERE useraccounts='" + identity + "' and userIsDeleted=0 and userIsActive=1 ";
    log.debug("sql = " + sql);
    Map<String, String> openIdMap = executeSelectSql(sql);
    int mapSize = openIdMap.size();
    log.debug("mapSize = " + mapSize);
    if (mapSize == 1) {
      for (Map.Entry<String, String> entry : openIdMap.entrySet()) {
        accountAndPassword[0] = entry.getKey();
        accountAndPassword[1] = entry.getValue();
      } 
    } else {
      log.debug("openId 重复或不存在, 所以不做处理！");
      for (Map.Entry<String, String> entry : openIdMap.entrySet()) {
        log.debug(entry.getKey());
        log.debug(entry.getValue());
      } 
    } 
    return accountAndPassword;
  }
  
  private static Map<String, String> executeSelectSql(String sql) {
    Map<String, String> map = new HashMap<String, String>();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        String userAccount = rs.getString("useraccounts");
        String userPassword = rs.getString("userpassword");
        map.put(userAccount, userPassword);
      } 
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (rs != null)
        try {
          rs.close();
        } catch (SQLException sQLException) {} 
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException sQLException) {} 
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException sQLException) {} 
    } 
    return map;
  }
  
  private static boolean presetHttpSession(HttpServletRequest request, HashMap userInfo) {
    boolean isSuccess = false;
    HttpSession session = request.getSession(false);
    if (userInfo != null && userInfo.get("userName") != null) {
      session.setAttribute("keySerial", null);
      Map uploadMap = UploadConfig.getInstance().getUploadMap();
      String lanIP = uploadMap.get("LanIP").toString();
      String userIP = (String)session.getAttribute("userIP");
      if (userIP.startsWith(lanIP)) {
        session.setAttribute("fileServer", uploadMap.get("FileInnerServer"));
        session.setAttribute("ftpMap", uploadMap.get("FtpInnerMap"));
      } else {
        session.setAttribute("fileServer", uploadMap.get("FileServer"));
        session.setAttribute("ftpMap", uploadMap.get("FtpMap"));
      } 
      session.setAttribute("domainId", userInfo.get("domainId"));
      if (userInfo.get("userAccount") != null && ("admin".equals(userInfo.get("userAccount")) || 
        "audit-admin".equals(userInfo.get("userAccount")))) {
        session.setAttribute("userName", "admin".equals(userInfo.get("userAccount")) ? "系统管理员" : "审计管理员");
        session.setAttribute("userId", "admin".equals(userInfo.get("userAccount")) ? "0" : "-99");
        session.setAttribute("userAccount", userInfo.get("userAccount").toString());
        session.setAttribute("orgName", "");
        session.setAttribute("orgId", "0");
        session.setAttribute("orgIdString", "");
        session.setAttribute("browseRange", "0");
        session.setAttribute("sysManager", "1");
        session.setAttribute("skin", "blue");
        session.setAttribute("rootCorpId", "0");
        session.setAttribute("corpId", "0");
        session.setAttribute("departId", "0");
      } else {
        session.setAttribute("userName", userInfo.get("userName"));
        session.setAttribute("userId", userInfo.get("userId"));
        session.setAttribute("userAccount", userInfo.get("userAccount"));
        session.setAttribute("orgName", userInfo.get("orgName"));
        session.setAttribute("orgId", userInfo.get("orgId"));
        session.setAttribute("orgIdString", userInfo.get("orgIdString"));
        session.setAttribute("skin", "blue");
        session.setAttribute("rootCorpId", userInfo.get("rootCorpId"));
        session.setAttribute("corpId", userInfo.get("corpId"));
        session.setAttribute("departId", userInfo.get("departId"));
        if (userInfo.get("sidelineDepartId") != null) {
          session.setAttribute("sidelineCorpId", userInfo.get("sidelineCorpId"));
          session.setAttribute("sidelineDepartId", userInfo.get("sidelineDepartId"));
        } else {
          session.setAttribute("sidelineCorpId", "0");
          session.setAttribute("sidelineDepartId", "0");
        } 
        if ("1".equals(SystemCommon.getUseBrowseRange())) {
          if (userInfo.get("browseRange") == null || "".equals(userInfo.get("browseRange").toString())) {
            String browseRangeType = SystemCommon.getDefaultBrowseRange();
            if ("1".equals(browseRangeType)) {
              session.setAttribute("browseRange", "*" + userInfo.get("corpId").toString() + "*");
            } else if ("0".equals(browseRangeType)) {
              session.setAttribute("browseRange", "*0*");
            } else if ("2".equals(browseRangeType)) {
              session.setAttribute("browseRange", "*" + userInfo.get("departId").toString() + "*");
            } 
          } else {
            String browseRangeType = userInfo.get("browseRange").toString();
            if ("1".equals(browseRangeType)) {
              session.setAttribute("browseRange", "*" + userInfo.get("corpId").toString() + "*");
            } else if ("0".equals(browseRangeType)) {
              session.setAttribute("browseRange", "*0*");
            } else if ("2".equals(browseRangeType)) {
              session.setAttribute("browseRange", "*" + userInfo.get("departId").toString() + "*");
            } else {
              session.setAttribute("browseRange", userInfo.get("browseRange"));
            } 
          } 
        } else {
          session.setAttribute("browseRange", "*0*");
        } 
        if ("1".equals(SystemCommon.getUseGrantRange())) {
          if (userInfo.get("grantRange") == null || "".equals(userInfo.get("grantRange").toString())) {
            String grantRangeType = SystemCommon.getDefaultGrantRange();
            if ("1".equals(grantRangeType)) {
              session.setAttribute("grantRange", "*" + userInfo.get("corpId").toString() + "*");
            } else if ("0".equals(grantRangeType)) {
              session.setAttribute("grantRange", "*0*");
            } else if ("2".equals(grantRangeType)) {
              session.setAttribute("grantRange", "*" + userInfo.get("departId").toString() + "*");
            } 
          } else {
            String grantRangeType = userInfo.get("grantRange").toString();
            if ("1".equals(grantRangeType)) {
              session.setAttribute("grantRange", "*" + userInfo.get("corpId").toString() + "*");
            } else if ("0".equals(grantRangeType)) {
              session.setAttribute("grantRange", "*0*");
            } else if ("2".equals(grantRangeType)) {
              session.setAttribute("grantRange", "*" + userInfo.get("departId").toString() + "*");
            } else {
              session.setAttribute("grantRange", userInfo.get("grantRange"));
            } 
          } 
        } else {
          session.setAttribute("grantRange", session.getAttribute("browseRange"));
        } 
        session.setAttribute("sysManager", userInfo.get("sysManager"));
        if (userInfo.get("userSimpleName") != null) {
          session.setAttribute("userSimpleName", userInfo.get("userSimpleName"));
        } else {
          session.setAttribute("userSimpleName", "");
        } 
        if (userInfo.get("orgSerial") != null) {
          session.setAttribute("orgSerial", userInfo.get("orgSerial"));
        } else {
          session.setAttribute("orgSerial", "");
        } 
        if (userInfo.get("orgSimpleName") != null) {
          session.setAttribute("orgSimpleName", userInfo.get("orgSimpleName"));
        } else {
          session.setAttribute("orgSimpleName", "");
        } 
        session.setAttribute("dutyName", (userInfo.get("dutyName") == null) ? "" : userInfo.get("dutyName"));
        session.setAttribute("dutyLevel", (userInfo.get("dutyLevel") == null) ? "0" : userInfo.get("dutyLevel"));
        session.setAttribute("imID", (userInfo.get("imID") == null) ? "0" : userInfo.get("imID").toString());
      } 
      session.setAttribute("hasLoged", null);
      session.setAttribute("empEnglishName", userInfo.get("empEnglishName"));
      String other = request.getParameter("other");
      String tan = request.getParameter("tan");
      String showTypeForVB = (request.getParameter("showTypeForVB") == null) ? "2" : request.getParameter("showTypeForVB");
      request.setAttribute("showTypeForVB", showTypeForVB);
      request.setAttribute("other", other);
      request.setAttribute("tan", tan);
      isSuccess = true;
    } else {
      request.setAttribute("errorType", "user");
      isSuccess = false;
    } 
    return isSuccess;
  }
}
