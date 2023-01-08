package com.js.util.util;

import com.js.system.util.SysSetupReader;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckDowloadFilter implements Filter {
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest)request;
    HttpServletResponse res = (HttpServletResponse)response;
    HttpSession session = req.getSession();
    String URLRoad = String.valueOf(req.getContextPath()) + req.getServletPath();
    if (session.getAttribute("domainId") != null) {
      String domainId = session.getAttribute("domainId").toString();
      SysSetupReader sysRed = SysSetupReader.getInstance();
      String filterLimitSize = sysRed.getFilterLimit(domainId);
      if (filterLimitSize != null && !"".equals(filterLimitSize))
        if (filterLimitSize.startsWith("1")) {
          String[] limitValue = filterLimitSize.substring(1).split(";");
          for (int i = 0; i < limitValue.length; i++) {
            if (URLRoad.indexOf("/upload/") >= 0 && URLRoad.substring(URLRoad.length() - 3, URLRoad.length()).contains(limitValue[i].toString().toLowerCase())) {
              System.out.println("登陆【拦截器1】:" + URLRoad);
              res.sendRedirect(req.getContextPath());
            } 
          } 
        }  
    } else if (URLRoad.indexOf("/upload/") >= 0) {
      System.out.println("登陆【拦截器2】:" + URLRoad);
      res.sendRedirect(req.getContextPath());
    } 
    chain.doFilter(request, response);
  }
  
  public void init(FilterConfig filterConfig) throws ServletException {}
  
  public void destroy() {}
}
