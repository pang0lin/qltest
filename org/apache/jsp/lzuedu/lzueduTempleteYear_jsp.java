/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:39:03 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.lzuedu;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class lzueduTempleteYear_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
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
      response.setContentType("text/html; charset=GBK");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\r');
      out.write('\n');

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

      out.write("\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<SCRIPT language=javascript src=\"/jsoa/js/openEndow.js\"></SCRIPT>\r\n<title>督办导入导出</title>\r\n<script type=\"text/javascript\">\r\nfunction downloadtemplate(a){\r\n  var downl=document.getElementById(\"downloadtemplate\");\r\n   downl.href=\"/jsoa/DownloadOrgTemplateAction.do?type=\"+a;\r\n   downl.click();\r\n }\r\n\r\nfunction uploadtemplate(a){\r\n   var wleft=window.screenLeft;\r\n   var wtop=window.screenTop;\r\n   window.open(\"/jsoa/lzuedu/uploadsupervisetemplate.jsp?type=\"+a,\"newpage\",'menubar=0,scrollbars=0,left='+wleft+',top='+wtop+',width=450,height=230,resizable=no');\r\n }\r\n function yearDelaySet(){\r\n\t var wleft=window.screenLeft;\r\n\t   var wtop=window.screenTop;\r\n\t   window.open(\"/jsoa/lzuedu/lzueduSuperviseDelaySet.jsp\",\"newpage\",'menubar=0,scrollbars=0,left='+wleft+',top='+wtop+',width=450,height=270,resizable=no');\r\n }\r\n</script>\r\n</head>\r\n<body>\r\n<br/>\r\n<div style=\"width: 80%;margin-bottom: -5px;\"><h3 align=\"center\">年度督办</h3></div>\r\n\r\n<table width=\"80%\"  class=\"listTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("\t<tr height=\"25\">\r\n\t\t<td class=\"listTableHead\">下载督办模板</td>\r\n\t\t<td class=\"listTableHead\">导入督办数据</td>\r\n\t\t<td class=\"listTableHead\">其他设置</td>\r\n\t</tr>\r\n\t<tr height=\"25\">\r\n\t\t<td class=\"listTableLine1\"><input class=\"btnButton2font\" onclick=\"javascript:downloadtemplate('lddbyear');\" type=\"button\" value=\"下载年度督办模版\"></td>\r\n\t\t<td class=\"listTableLine1\"><input class=\"btnButton2font\" onclick=\"javascript:uploadtemplate(2);\" type=\"button\" value=\"上传年度督办\"></td>\r\n\t\t<td class=\"listTableLine2\"><input class=\"btnButton2font\" onclick=\"javascript:yearDelaySet();\" type=\"button\" value=\"年度督办延迟设置\"></td>\r\n\t</tr>\r\n\t\r\n</table>\r\n<a id=\"downloadtemplate\" ></a>\r\n\r\n</body>\r\n</html>\r\n\r\n");
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