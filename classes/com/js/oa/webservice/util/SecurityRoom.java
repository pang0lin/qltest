package com.js.oa.webservice.util;

import java.util.ArrayList;
import java.util.List;

public class SecurityRoom {
  private static List<SecurityPojo> Security = new ArrayList<SecurityPojo>();
  
  public static void addAPP(SecurityPojo security) {
    Security.add(security);
  }
  
  public static List<SecurityPojo> getSecuritys() {
    return Security;
  }
}
