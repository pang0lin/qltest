/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:38:14 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import com.js.system.util.StaticParam;

public final class version_005finfo_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.system.util.StaticParam");
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=GBK");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n");

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

HashMap<String,String> map=StaticParam.getVersionInfo();

      out.write("\r\n\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<html>\r\n  <head>\r\n    <base href=\"");
      out.print(basePath);
      out.write("\">\r\n    \r\n    <title>版本信息</title>\r\n    \r\n\t<meta http-equiv=\"pragma\" content=\"no-cache\">\r\n\t<meta http-equiv=\"cache-control\" content=\"no-cache\">\r\n\t<meta http-equiv=\"expires\" content=\"0\">    \r\n\t<meta http-equiv=\"keywords\" content=\"版本\">\r\n\t<meta http-equiv=\"description\" content=\"版本信息\">\r\n\t<!--\r\n\t<link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\">\r\n\t-->\r\n  </head>\r\n  \r\n  <body style=\"font-size:12px;background:url(images/version_bg.gif) no-repeat;\">\r\n    \t<div id=\"content\" style=\"width:350px;height:270px;overflow:hidden;\">\r\n    \t\t<div id=\"divLogo\" style=\"margin-left:50px;height:40px;\"></div>\r\n    \t\t<div id=\"divVersionInfo\" style=\"padding:20px;\">\r\n\t    \t\t<div style=\"float:left;width:150px;padding-left:30px;\">\r\n\t    \t\t\t版本号:\r\n\t    \t\t</div>\r\n\t    \t\t<div style=\"float:right;width:180px\">\r\n\t    \t\t\t");

	    				if(null!=map.get("ver"))
	    				{
	    			
      out.write("\t\r\n\t    \t\t\t\t");
      out.print(map.get("ver"));
      out.write("\r\n\t    \t\t\t");

	    				} 
	    			
      out.write("\r\n\t    \t\t</div>\r\n\t    \t\t<div style=\"clear:both;float:left;width:150px;padding-left:30px;\">\r\n\t    \t\t\t更新时间:\r\n\t    \t\t</div>\r\n\t    \t\t<div style=\"float:right;width:180px\">\r\n\t    \t\t\t");

	    				if(null!=map.get("ptime"))
	    				{
	    			
      out.write("\t\r\n\t    \t\t\t\t");
      out.print(map.get("ptime") );
      out.write("\r\n\t    \t\t\t");

	    				} 
	    			
      out.write("\r\n\t\t\t\t\t\r\n\t    \t\t</div>\r\n\t    \t\t\r\n\t    \t\t<div style=\"clear:both;width:400px;padding-left:30px;\">\r\n\t\t\t\t\t<textarea name=\"textArea\"  rows=\"5\" cols=\"35\">");
      out.print(map.get("memo").trim() );
      out.write("</textarea>\r\n\t    \t\t</div>\r\n    \t\t</div>\r\n    \t</div>\r\n    \t<div style=\"align:center;\">\r\n\t    \t版权所有(C)&nbsp;Copyright 2006-2009 Jiusi Software\r\n\t    </div>\r\n  </body>\r\n</html>\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}