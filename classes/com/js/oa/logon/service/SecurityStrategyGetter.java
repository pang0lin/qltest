package com.js.oa.logon.service;

import javax.servlet.http.HttpServletRequest;

public interface SecurityStrategyGetter {
  String getUserAccountFromRequest(HttpServletRequest paramHttpServletRequest);
  
  String getLogoutURL(HttpServletRequest paramHttpServletRequest);
  
  String getSingleSignOnURL(HttpServletRequest paramHttpServletRequest);
}
