package com.js.util.http;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

public class HttpSession implements HttpSession {
  private Map<String, Object> attrMap = new HashMap<String, Object>();
  
  public Object getAttribute(String name) {
    return this.attrMap.get(name);
  }
  
  public Enumeration<String> getAttributeNames() {
    return null;
  }
  
  public long getCreationTime() {
    return 0L;
  }
  
  public String getId() {
    return null;
  }
  
  public long getLastAccessedTime() {
    return 0L;
  }
  
  public int getMaxInactiveInterval() {
    return 0;
  }
  
  public ServletContext getServletContext() {
    return null;
  }
  
  public HttpSessionContext getSessionContext() {
    return null;
  }
  
  public Object getValue(String name) {
    return null;
  }
  
  public String[] getValueNames() {
    return null;
  }
  
  public void invalidate() {}
  
  public boolean isNew() {
    return false;
  }
  
  public void putValue(String name, Object value) {}
  
  public void removeAttribute(String name) {}
  
  public void removeValue(String name) {}
  
  public void setAttribute(String name, Object value) {
    this.attrMap.put(name, value);
  }
  
  public void setMaxInactiveInterval(int interval) {}
}
