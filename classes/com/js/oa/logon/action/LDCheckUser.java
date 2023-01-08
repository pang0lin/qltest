package com.js.oa.logon.action;

import com.js.ldap.OpenLDAP;
import com.js.oa.logon.service.LogonBD;
import com.js.util.config.SystemCommon;
import com.js.util.config.UploadConfig;
import com.js.util.util.OnlineUserMap;
import com.wiscom.is.IdentityFactory;
import com.wiscom.is.IdentityManager;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LDCheckUser extends Action {
  String userAccount = "";
  
  String userPassword = "";
  
  String userIP = "";
  
  String sessionId = "";
  
  String serverIP = "";
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    HttpSession session = httpServletRequest.getSession(true);
    this.sessionId = session.getId();
    this.userIP = httpServletRequest.getRemoteAddr();
    LogonBD logonBD = new LogonBD();
    String is_config = httpServletRequest.getRealPath("/client.properties");
    Cookie[] all_cookies = httpServletRequest.getCookies();
    String decodedCookieValue = null;
    if (all_cookies != null)
      for (int i = 0; i < all_cookies.length; i++) {
        Cookie myCookie = all_cookies[i];
        if (myCookie.getName().equals("iPlanetDirectoryPro"))
          decodedCookieValue = URLDecoder.decode(myCookie.getValue(), "GB2312"); 
      }  
    IdentityFactory factory = IdentityFactory.createFactory(is_config);
    IdentityManager im = factory.getIdentityManager();
    String curUser = "";
    if (decodedCookieValue != null)
      curUser = im.getCurrentUser(decodedCookieValue); 
    if (curUser != null) {
      this.userAccount = logonBD.getUserAccountByNumber(curUser);
      if ("".equals(this.userAccount)) {
        Map<String, String> map = (new OpenLDAP()).getLdapInfo();
        String userSql = ((String)map.get("oneUseSql")).replaceAll("@value@", curUser);
        this.userAccount = logonBD.getUserAccountByNumber(curUser);
      } 
    } 
    String browserVersion = httpServletRequest.getHeader("User-Agent");
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
    try {
      this.serverIP = InetAddress.getLocalHost().getHostAddress();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    HashMap<Object, Object> userInfo = new HashMap<Object, Object>(10, 1.0F);
    userInfo = logonBD.logon(this.userAccount, this.userPassword, this.userIP, this.serverIP, this.sessionId, "jiusi", "0");
    this.userAccount.trim().toLowerCase().equals("admin");
    if (userInfo == null) {
      httpServletRequest.setAttribute("errorType", "user");
    } else if (userInfo.get("error") != null) {
      httpServletRequest.setAttribute("errorType", userInfo.get("error"));
    } else {
      if (userInfo.get("userName") != null) {
        if (!"2".equals(SystemCommon.getUKey()) && userInfo.get("keySerial") != null) {
          session.setAttribute("keySerial", userInfo.get("keySerial"));
        } else {
          session.setAttribute("keySerial", null);
        } 
        Map uploadMap = UploadConfig.getInstance().getUploadMap();
        String lanIP = uploadMap.get("LanIP").toString();
        if (this.userIP.startsWith(lanIP)) {
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
          session.setAttribute("userAccount", this.userAccount);
          session.setAttribute("sysManager", userInfo.get("sysManager"));
          if (userInfo.get("userSimpleName") != null) {
            session.setAttribute("userSimpleName", 
                userInfo.get("userSimpleName"));
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
        session.setAttribute("serverIP", this.serverIP);
        session.setAttribute("userIP", this.userIP);
        session.setAttribute("empEnglishName", userInfo.get("empEnglishName"));
        String other = httpServletRequest.getParameter("other");
        String tan = httpServletRequest.getParameter("tan");
        String showTypeForVB = (httpServletRequest.getParameter("showTypeForVB") == null) ? "2" : httpServletRequest.getParameter("showTypeForVB");
        httpServletRequest.setAttribute("showTypeForVB", showTypeForVB);
        httpServletRequest.setAttribute("other", other);
        httpServletRequest.setAttribute("tan", tan);
        String domainId = (String)session.getAttribute("domainId");
        if (OnlineUserMap.getInstance().get(this.userAccount) != null)
          OnlineUserMap.getInstance().remove(this.userAccount); 
        OnlineUserMap.getInstance().put(this.userAccount, this.sessionId);
        return actionMapping.findForward("success");
      } 
      httpServletRequest.setAttribute("errorType", "user");
    } 
    return actionMapping.findForward("error");
  }
}
