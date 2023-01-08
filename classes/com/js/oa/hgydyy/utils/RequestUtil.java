package com.js.oa.hgydyy.utils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
  private static HttpServletRequest request = null;
  
  public static void init(HttpServletRequest request) {
    RequestUtil.request = request;
  }
  
  public static String getStrValue(String name) {
    String nameTemp = request.getParameter(name);
    if (nameTemp == null)
      nameTemp = ""; 
    return nameTemp;
  }
}
