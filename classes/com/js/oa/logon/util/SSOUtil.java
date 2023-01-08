package com.js.oa.logon.util;

import javax.servlet.http.HttpServletRequest;

public class SSOUtil {
  private static final String SSO_TYPE = "SSOType";
  
  public static void setSSOType(HttpServletRequest request) {
    request.getSession(true).setAttribute("SSOType", "1");
  }
  
  public static boolean isSSO(HttpServletRequest request) {
    boolean sso = false;
    if ("1".equals(request.getSession(true).getAttribute("SSOType")))
      sso = true; 
    return sso;
  }
}
