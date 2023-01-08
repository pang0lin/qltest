package com.js.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

public class HttpServletRequest implements HttpServletRequest {
  private Map<String, String> paraMap = new HashMap<String, String>();
  
  private Map<String, Object> attrMap = new HashMap<String, Object>();
  
  private HttpSession session = null;
  
  public void setParameter(String key, String value) {
    this.paraMap.put(key, value);
  }
  
  public String getParameter(String key) {
    return this.paraMap.get(key);
  }
  
  public void setAttribute(String key, Object obj) {
    this.attrMap.put(key, obj);
  }
  
  public Object getAttribute(String key) {
    return this.attrMap.get(key);
  }
  
  public void removeAttribute(String key) {
    this.attrMap.remove(key);
  }
  
  public AsyncContext getAsyncContext() {
    return null;
  }
  
  public Enumeration<String> getAttributeNames() {
    return null;
  }
  
  public String getCharacterEncoding() {
    return null;
  }
  
  public int getContentLength() {
    return 0;
  }
  
  public String getContentType() {
    return null;
  }
  
  public DispatcherType getDispatcherType() {
    return null;
  }
  
  public ServletInputStream getInputStream() throws IOException {
    return null;
  }
  
  public String getLocalAddr() {
    return null;
  }
  
  public String getLocalName() {
    return null;
  }
  
  public int getLocalPort() {
    return 0;
  }
  
  public Locale getLocale() {
    return null;
  }
  
  public Enumeration<Locale> getLocales() {
    return null;
  }
  
  public Map<String, String[]> getParameterMap() {
    return null;
  }
  
  public Enumeration<String> getParameterNames() {
    return null;
  }
  
  public String[] getParameterValues(String arg0) {
    return null;
  }
  
  public String getProtocol() {
    return null;
  }
  
  public BufferedReader getReader() throws IOException {
    return null;
  }
  
  public String getRealPath(String arg0) {
    return null;
  }
  
  public String getRemoteAddr() {
    return null;
  }
  
  public String getRemoteHost() {
    return null;
  }
  
  public int getRemotePort() {
    return 0;
  }
  
  public RequestDispatcher getRequestDispatcher(String arg0) {
    return null;
  }
  
  public String getScheme() {
    return null;
  }
  
  public String getServerName() {
    return null;
  }
  
  public int getServerPort() {
    return 0;
  }
  
  public ServletContext getServletContext() {
    return null;
  }
  
  public boolean isAsyncStarted() {
    return false;
  }
  
  public boolean isAsyncSupported() {
    return false;
  }
  
  public boolean isSecure() {
    return false;
  }
  
  public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {}
  
  public AsyncContext startAsync() throws IllegalStateException {
    return null;
  }
  
  public AsyncContext startAsync(ServletRequest arg0, ServletResponse arg1) throws IllegalStateException {
    return null;
  }
  
  public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
    return false;
  }
  
  public String getAuthType() {
    return null;
  }
  
  public String getContextPath() {
    return null;
  }
  
  public Cookie[] getCookies() {
    return null;
  }
  
  public long getDateHeader(String name) {
    return 0L;
  }
  
  public String getHeader(String name) {
    return null;
  }
  
  public Enumeration<String> getHeaderNames() {
    return null;
  }
  
  public Enumeration<String> getHeaders(String name) {
    return null;
  }
  
  public int getIntHeader(String name) {
    return 0;
  }
  
  public String getMethod() {
    return null;
  }
  
  public Part getPart(String name) throws IOException, ServletException {
    return null;
  }
  
  public Collection<Part> getParts() throws IOException, ServletException {
    return null;
  }
  
  public String getPathInfo() {
    return null;
  }
  
  public String getPathTranslated() {
    return null;
  }
  
  public String getQueryString() {
    return null;
  }
  
  public String getRemoteUser() {
    return null;
  }
  
  public String getRequestURI() {
    return null;
  }
  
  public StringBuffer getRequestURL() {
    return null;
  }
  
  public String getRequestedSessionId() {
    return null;
  }
  
  public String getServletPath() {
    return null;
  }
  
  public HttpSession getSession() {
    return this.session;
  }
  
  public HttpSession getSession(boolean create) {
    return this.session;
  }
  
  public void setSession(HttpSession session) {
    this.session = session;
  }
  
  public Principal getUserPrincipal() {
    return null;
  }
  
  public boolean isRequestedSessionIdFromCookie() {
    return false;
  }
  
  public boolean isRequestedSessionIdFromURL() {
    return false;
  }
  
  public boolean isRequestedSessionIdFromUrl() {
    return false;
  }
  
  public boolean isRequestedSessionIdValid() {
    return false;
  }
  
  public boolean isUserInRole(String role) {
    return false;
  }
  
  public void login(String username, String password) throws ServletException {}
  
  public void logout() throws ServletException {}
}
