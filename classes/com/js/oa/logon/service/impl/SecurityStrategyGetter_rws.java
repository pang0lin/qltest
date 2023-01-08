package com.js.oa.logon.service.impl;

import com.js.oa.logon.service.SecurityStrategyGetter;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import small.danfer.sso.assertion.LocalPrincipal;
import small.danfer.sso.http.HttpSingleSignOnService;

public class SecurityStrategyGetter_rws implements SecurityStrategyGetter {
  public String getUserAccountFromRequest(HttpServletRequest request) {
    LocalPrincipal localPrincipal = null;
    HttpSingleSignOnService service = new HttpSingleSignOnService();
    Principal principal = null;
    try {
      localPrincipal = service.singleSignOn(request);
      System.out.println("++++++++" + localPrincipal);
    } catch (Exception exception) {}
    String userName = "";
    if (localPrincipal != null) {
      userName = localPrincipal.getName();
      System.out.println("++++++++" + userName);
    } 
    if (userName == null)
      userName = ""; 
    return userName;
  }
  
  public String getLogoutURL(HttpServletRequest request) {
    HttpSingleSignOnService service = new HttpSingleSignOnService();
    return service.getSingleSignOutURL();
  }
  
  public String getSingleSignOnURL(HttpServletRequest request) {
    HttpSingleSignOnService service = new HttpSingleSignOnService();
    return service.getSingleSignOnURL();
  }
}
