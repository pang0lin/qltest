package com.js.util.util;

import java.util.StringTokenizer;

public class ConversionUserSimpleName {
  private String userSimpleString;
  
  public ConversionUserSimpleName(String userSimple) {
    this.userSimpleString = "";
    splitByUserSimple(userSimple);
  }
  
  public String getUserSimpleString() {
    if (this.userSimpleString.equals(""))
      return ""; 
    return this.userSimpleString.substring(0, this.userSimpleString.length() - 1);
  }
  
  private void splitByUserSimple(String userSimple) {
    StringTokenizer stk = new StringTokenizer(userSimple, ",");
    while (stk.hasMoreTokens()) {
      StringTokenizer stk2 = new StringTokenizer(stk.nextToken(), "<");
      this.userSimpleString = String.valueOf(this.userSimpleString) + "|" + stk2.nextElement() + "|" + ",";
    } 
  }
}
