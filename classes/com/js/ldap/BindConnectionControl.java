package com.js.ldap;

import javax.naming.ldap.Control;

public class BindConnectionControl implements Control {
  public byte[] getEncodedValue() {
    return null;
  }
  
  public String getID() {
    return "1.2.840.113556.1.4.1781";
  }
  
  public boolean isCritical() {
    return true;
  }
}
