package com.js.util.http;

import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class HttpServletRequestWrapper extends HttpServletRequestWrapper {
  private Map params = null;
  
  public HttpServletRequestWrapper(HttpServletRequest request, Map newParams) {
    super(request);
    this.params = newParams;
    renewParameterMap(request);
  }
  
  public Map getParameterMap() {
    return this.params;
  }
  
  public Enumeration getParameterNames() {
    Vector l = new Vector(this.params.keySet());
    return l.elements();
  }
  
  public String[] getParameterValues(String name) {
    Object v = this.params.get(name);
    if (v == null)
      return null; 
    if (v instanceof String[])
      return (String[])v; 
    if (v instanceof String)
      return new String[] { (String)v }; 
    return new String[] { v.toString() };
  }
  
  public String getParameter(String name) {
    Object v = this.params.get(name);
    if (v == null)
      return null; 
    if (v instanceof String[]) {
      String[] strArr = (String[])v;
      if (strArr.length > 0)
        return strArr[0]; 
      return null;
    } 
    if (v instanceof String)
      return (String)v; 
    return v.toString();
  }
  
  private void renewParameterMap(HttpServletRequest req) {
    String queryString = req.getQueryString();
    if (queryString != null && queryString.trim().length() > 0) {
      String[] params = queryString.split("&");
      for (int i = 0; i < params.length; i++) {
        int splitIndex = params[i].indexOf("=");
        if (splitIndex != -1) {
          String key = params[i].substring(0, splitIndex);
          if (!this.params.containsKey(key) && 
            splitIndex < params[i].length()) {
            String value = params[i].substring(splitIndex + 1);
            this.params.put(key, new String[] { value });
          } 
        } 
      } 
    } 
  }
}
