package com.js.util.util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class SetCharacterEncodingFilter implements Filter {
  protected String encoding = null;
  
  protected FilterConfig filterConfig = null;
  
  protected boolean ignore = true;
  
  public void destroy() {
    this.encoding = null;
    this.filterConfig = null;
  }
  
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest)request;
    String servletPath = req.getServletPath();
    if (servletPath.toLowerCase().indexOf("weixin") > 0) {
      request.setCharacterEncoding("utf-8");
    } else if (this.ignore || request.getCharacterEncoding() == null) {
      String encoding = selectEncoding(request);
      if (encoding != null)
        request.setCharacterEncoding(encoding); 
    } 
    if (req.getHeader("USER-AGENT").toLowerCase().indexOf("firefox") > 0 && req.getMethod().equalsIgnoreCase("get") && req.getQueryString() != null && !req.getQueryString().equals("null")) {
      CharacterEncodingRequest requestWrapper = new CharacterEncodingRequest(req);
      chain.doFilter((ServletRequest)requestWrapper, response);
    } else {
      chain.doFilter(request, response);
    } 
  }
  
  public void init(FilterConfig filterConfig) throws ServletException {
    this.filterConfig = filterConfig;
    this.encoding = filterConfig.getInitParameter("encoding");
    String value = filterConfig.getInitParameter("ignore");
    if (value == null) {
      this.ignore = true;
    } else if (value.equalsIgnoreCase("true")) {
      this.ignore = true;
    } else if (value.equalsIgnoreCase("yes")) {
      this.ignore = true;
    } else {
      this.ignore = false;
    } 
  }
  
  protected String selectEncoding(ServletRequest request) {
    return this.encoding;
  }
}
