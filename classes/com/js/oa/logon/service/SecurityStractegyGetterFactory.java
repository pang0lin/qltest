package com.js.oa.logon.service;

import com.js.oa.logon.service.impl.SecurityStrategyGetter_default;
import com.js.oa.logon.service.impl.SecurityStrategyGetter_rws;

public class SecurityStractegyGetterFactory {
  public static SecurityStrategyGetter getSecurityStrategyGetter(String customerName) {
    SecurityStrategyGetter getter = null;
    if ("rws".equals(customerName)) {
      getter = new SecurityStrategyGetter_rws();
    } else {
      getter = new SecurityStrategyGetter_default();
    } 
    return getter;
  }
}
