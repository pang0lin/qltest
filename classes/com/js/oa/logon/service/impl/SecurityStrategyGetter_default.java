package com.js.oa.logon.service.impl;

import com.js.oa.logon.service.SecurityStrategyGetter;
import javax.servlet.http.HttpServletRequest;

public class SecurityStrategyGetter_default implements SecurityStrategyGetter {
  public String getUserAccountFromRequest(HttpServletRequest request) {
    return "";
  }
  
  public String getLogoutURL(HttpServletRequest request) {
    return "";
  }
  
  public String getSingleSignOnURL(HttpServletRequest request) {
    return "";
  }
}
