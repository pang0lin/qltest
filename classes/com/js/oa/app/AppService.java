package com.js.oa.app;

import com.js.oa.logon.service.LogonBD;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import com.js.util.config.UploadConfig;
import com.js.util.util.AES;
import com.js.util.util.MD5;
import com.qq.weixin.mp.service.WeixinMessageUrlGetter;
import com.qq.weixin.mp.service.WeixinMessageUrlGetterFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AppService {
  private static final String DOMAIN_ACCOUNT = "jiusi";
  
  private static final String CHECK_PASSWORD_TRUE = "1";
  
  private static final String CHECK_PASSWORD_FALSE = "0";
  
  public static void processMessageLink(HttpServletRequest request, HttpServletResponse response) {
    String messageId = request.getParameter("messageURL");
    String messageUrl = "wap.jsp";
    String userAccounts = request.getParameter("userAccounts");
    String userPassword = request.getParameter("userPassword");
    String errorType = "";
    DbOpt db = new DbOpt();
    try {
      if (messageId != null && !"".equals(messageId))
        db.executePSUpdate("update sys_messages set message_status=0 where message_id=?", new String[] { messageId }); 
      String password = db.executeQueryToStrPS("select userpassword from org_employee where userisactive=1 and userisdeleted=0 and useraccounts=?", new String[] { userAccounts });
      if (password == null || "".equals(password))
        errorType = "0"; 
      if (userAccounts == null || "".equals(userAccounts) || userPassword == null || "".equals(userAccounts) || messageId == null || "".equals(messageId))
        errorType = "-1"; 
      if ("".equals(errorType)) {
        userPassword = AES.decrypt2Str(userPassword, "jiusi.net0123abc");
        MD5 md5 = new MD5();
        userPassword = md5.getMD5Code(userPassword);
        if (userPassword != null && md5.equals(userPassword, password)) {
          HttpSession session = request.getSession(true);
          String browserVersion = request.getHeader("User-Agent");
          browserTypeToSession(browserVersion, session);
          String sessionId = session.getId();
          String[] userIpAndServerIp = getUserIpAndServerIp(request);
          String userIP = userIpAndServerIp[0];
          String serverIP = userIpAndServerIp[1];
          session.setAttribute("userIP", userIP);
          HashMap<String, String> userInfo = (new LogonBD()).logon(userAccounts, userPassword, userIP, serverIP, sessionId, "jiusi", "1");
          userInfo.put("userAccount", userAccounts);
          boolean isLogon = presetHttpSession(request, userInfo);
          if (isLogon) {
            Locale loc = new Locale("zh_cn");
            session.setAttribute("org.apache.struts.action.LOCALE", loc);
            session.setAttribute("wapVersion", "3G");
            session.setAttribute("domainId", "0");
            session.setAttribute("loginType", "wap");
            session.setAttribute("logDevice", "WebApp");
          } else {
            errorType = "2";
          } 
        } else {
          errorType = "1";
        } 
      } 
      if ("".equals(errorType)) {
        String sql = "select message_id, message_type, message_title, message_send_username, (select max(useraccounts) from org_employee where emp_id= message.message_toUserId) userid, message_toUserId,message_Url,data_Id from sys_messages message where message_id=?";
        String[][] result = db.executeQueryToStrArr2PS(sql, 8, new String[] { messageId });
        if (result != null && result.length > 0) {
          String messageType = result[0][1];
          String toEmpId = result[0][5];
          messageUrl = result[0][6];
          WeixinMessageUrlGetter getter = WeixinMessageUrlGetterFactory.getWeixinMessageUrlGetter(messageType);
          messageUrl = getter.getWeiXinUrl(messageUrl, result[0][7], toEmpId);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        db.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    try {
      if ("wap.jsp".equals(messageUrl)) {
        response.sendRedirect(messageUrl);
      } else {
        request.getRequestDispatcher(String.valueOf(messageUrl) + "&fromAppMessage=1").forward((ServletRequest)request, (ServletResponse)response);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
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
  
  private static String[] getUserIpAndServerIp(HttpServletRequest request) {
    String[] userIpAndServerIp = { "", "" };
    userIpAndServerIp[0] = request.getRemoteAddr();
    try {
      userIpAndServerIp[1] = InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } 
    return userIpAndServerIp;
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
