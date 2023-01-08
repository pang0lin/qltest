package com.js.util.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BrowserJudge {
  public static boolean isMSIEx(HttpServletRequest request) {
    boolean flag = false;
    HttpSession session = request.getSession(true);
    if ("MSIEx".equals(session.getAttribute("browserVersion").toString()))
      flag = true; 
    return flag;
  }
  
  public static boolean isMSIE10(HttpServletRequest request) {
    boolean flag = false;
    HttpSession session = request.getSession(true);
    if ("IE10".equals(session.getAttribute("browserVersion").toString()))
      flag = true; 
    return flag;
  }
  
  public static boolean isMSIE11(HttpServletRequest request) {
    boolean flag = false;
    HttpSession session = request.getSession(true);
    if ("IE11".equals(session.getAttribute("browserVersion").toString()))
      flag = true; 
    return flag;
  }
  
  public static boolean isMSIE6(HttpServletRequest request) {
    boolean flag = false;
    HttpSession session = request.getSession(true);
    if ("MSIE6".equals(session.getAttribute("browserVersion").toString()))
      flag = true; 
    return flag;
  }
  
  public static boolean isMSIE(HttpServletRequest request) {
    boolean flag = false;
    HttpSession session = request.getSession(true);
    if (session.getAttribute("browserVersion").toString().startsWith("MSIE") || session.getAttribute("browserVersion").toString().startsWith("IE11360"))
      flag = true; 
    if (session.getAttribute("OSType").toString().startsWith("ipad"))
      flag = false; 
    if (session.getAttribute("OSType").toString().startsWith("iPhone"))
      flag = false; 
    return flag;
  }
  
  public static boolean isFirefox(HttpServletRequest request) {
    boolean flag = false;
    HttpSession session = request.getSession(true);
    if ("Firefox".equals(session.getAttribute("browserVersion").toString()))
      flag = true; 
    return flag;
  }
  
  public static boolean isChrome(HttpServletRequest request) {
    boolean flag = false;
    HttpSession session = request.getSession(true);
    if ("Chrome".equals(session.getAttribute("browserVersion").toString()))
      flag = true; 
    return flag;
  }
  
  public static boolean isSafari(HttpServletRequest request) {
    boolean flag = false;
    HttpSession session = request.getSession(true);
    if ("Safari".equals(session.getAttribute("browserVersion").toString()))
      flag = true; 
    return flag;
  }
  
  public static boolean isIpad(HttpServletRequest request) {
    boolean flag = false;
    HttpSession session = request.getSession(true);
    if ("ipad".equals((session.getAttribute("OSType") == null) ? "" : session.getAttribute("OSType").toString()))
      flag = true; 
    return flag;
  }
  
  public static boolean isIPhone(HttpServletRequest request) {
    boolean flag = false;
    HttpSession session = request.getSession(true);
    if ("iPhone".equals((session.getAttribute("OSType") == null) ? "" : session.getAttribute("OSType").toString()))
      flag = true; 
    return flag;
  }
  
  public static boolean isAndroid(HttpServletRequest request) {
    boolean flag = false;
    HttpSession session = request.getSession(true);
    if ("Android".equals((session.getAttribute("OSType") == null) ? "" : session.getAttribute("OSType").toString()))
      flag = true; 
    return flag;
  }
  
  public static boolean isPC(HttpServletRequest request) {
    boolean flag = false;
    HttpSession session = request.getSession(true);
    if ("pc".equals((session.getAttribute("OSType") == null) ? "" : session.getAttribute("OSType").toString()))
      flag = true; 
    return flag;
  }
  
  public static boolean isNotPc(HttpServletRequest request) {
    boolean flag = false;
    if (isAndroid(request) || isIpad(request) || isIPhone(request))
      flag = true; 
    return flag;
  }
  
  public static boolean isMobile(HttpServletRequest request) {
    boolean flag = false;
    if (isAndroid(request) || isIpad(request) || isIPhone(request))
      flag = true; 
    return flag;
  }
  
  public static boolean isOtherBrowser(HttpServletRequest request) {
    boolean flag = false;
    HttpSession session = request.getSession(true);
    if (!session.getAttribute("browserVersion").toString().startsWith("MSIE"))
      flag = true; 
    if (session.getAttribute("browserVersion").toString().startsWith("IE11360"))
      flag = false; 
    if (session.getAttribute("OSType").toString().startsWith("ipad"))
      flag = true; 
    if (session.getAttribute("OSType").toString().startsWith("iPhone"))
      flag = true; 
    return flag;
  }
  
  public static boolean isWebKit(HttpServletRequest request) {
    boolean flag = false;
    if (isChrome(request) || isSafari(request))
      flag = true; 
    return flag;
  }
  
  public static boolean likeFirefox(HttpServletRequest request) {
    boolean flag = false;
    if (isFirefox(request) || isMSIE10(request) || isMSIE11AndTrend(request))
      flag = true; 
    return flag;
  }
  
  public static boolean isMSIE11360(HttpServletRequest request) {
    boolean flag = false;
    HttpSession session = request.getSession(true);
    if ("IE11360".equals(session.getAttribute("browserVersion").toString()))
      flag = true; 
    return flag;
  }
  
  public static boolean isMSIE11AndTrend(HttpServletRequest request) {
    boolean flag = false;
    HttpSession session = request.getSession(true);
    if ("IE11".equals(session.getAttribute("browserVersion").toString()))
      flag = true; 
    return flag;
  }
  
  public static boolean isIE(HttpServletRequest request) {
    boolean flag = false;
    if (isMSIE(request) || isMSIE10(request) || isMSIE11(request))
      flag = true; 
    return flag;
  }
  
  public static String Browser(HttpSession session) {
    String browser = "IE浏览器";
    if ("Chrome".equals(session.getAttribute("browserVersion").toString()))
      browser = "Chrome浏览器"; 
    if ("Safari".equals(session.getAttribute("browserVersion").toString()))
      browser = "Safari浏览器"; 
    if ("Firefox".equals(session.getAttribute("browserVersion").toString()))
      browser = "Firefox浏览器"; 
    if ("IE10".equals(session.getAttribute("browserVersion").toString()))
      browser = "IE10浏览器"; 
    return browser;
  }
}
