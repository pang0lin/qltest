/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:46:11 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.wap2.scheme;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class selectDept_005f3g_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"DTD/xhtml1-strict.dtd\">\r\n\r\n");
 
	request.setCharacterEncoding("UTF-8");
	String path = request.getContextPath();
	String allSelEmps = request.getParameter("allSelEmps");
	String title = request.getParameter("title");
	String beginTime= request.getParameter("beginTime");
	String endTime = request.getParameter("endTime");
	String relProject = request.getParameter("relProject");
	String content = request.getParameter("content");
	String attentName = request.getParameter("attendName");

      out.write("\r\n<HTML xmlns=\"http://www.w3.org/1999/xhtml\">\r\n\t<HEAD>\r\n\t\t<TITLE>选择部门</TITLE>\r\n\t\t<META content=\"text/html; charset=UTF-8\" http-equiv=Content-Type>\r\n\t\t<META name=viewport content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\">\r\n\t\t<META name=apple-touch-fullscreen content=YES>\r\n\t\t<META name=apple-mobile-web-app-capable content=no>\r\n\t\t<LINK rel=apple-touch-icon href=\"");
      out.print(path);
      out.write("/wap2/images/iphone.jpg\">\r\n\t\t<LINK rel=stylesheet type=text/css href=\"");
      out.print(path);
      out.write("/wap2/css/main_3g.css\">\r\n\t\t<SCRIPT type=application/x-javascript src=\"");
      out.print(path);
      out.write("/wap2/js/cookie.js\"></SCRIPT>\r\n\t\t<SCRIPT type=application/x-javascript src=\"");
      out.print(path);
      out.write("/wap2/js/util.js\"></SCRIPT>\r\n\t\t<SCRIPT type=application/x-javascript src=\"");
      out.print(path);
      out.write("/wap2/js/frost.js\"></SCRIPT>\r\n\t\t<META name=GENERATOR content=\"MSHTML 8.00.6001.19154\">\r\n\t\t<SCRIPT type=application/x-javascript src=\"");
      out.print(path);
      out.write("/js/jquery-1.4.2.min.js\"></SCRIPT>\r\n\t</head>\r\n\r\n\t<body>\r\n\t\t<form name=\"deptForm\" action=\"");
      out.print(path);
      out.write("/wap2/scheme/writeSchPage_3g.jsp\" method=\"post\">\r\n  \t\t\t<input type=\"hidden\" id=\"allSelEmps\" name=\"allSelEmps\" value=\"");
      out.print(allSelEmps);
      out.write("\"/>\r\n  \t\t\t<input type=\"hidden\" name=\"title\" value=\"");
      out.print(title);
      out.write("\"/>\r\n  \t\t\t<input type=\"hidden\" name=\"beginTime\" value=\"");
      out.print(beginTime );
      out.write("\"/>\r\n  \t\t\t<input type=\"hidden\" name=\"endTime\" value=\"");
      out.print(endTime );
      out.write("\"/>\r\n  \t\t\t<input type=\"hidden\" id=\"attendEmp\" name=\"attendEmp\" value=\"");
      out.print(attentName );
      out.write("\"/>\r\n  \t\t\t<input type=\"hidden\" name=\"relProject\" value=\"");
      out.print(relProject );
      out.write("\"/>\r\n  \t\t\t<input type=\"hidden\" name=\"content\" value=\"");
      out.print(content );
      out.write("\"/>\r\n  \t\t\t<input type=\"hidden\" name=\"orgId\"/>\r\n  \t\t</form>\r\n\t\t<DIV id=top>\r\n           <SPAN id=lp><DIV class=btn_2><A href=\"");
      out.print(path);
      out.write("/wap2/index_3g.jsp\">桌面</A></DIV></SPAN>\r\n           <A class=btn_1 href=\"JavaScript:history.go(-1);\">返回</A>\r\n       \t</DIV>\r\n       \t<br/>\r\n       \t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/wap2/wapSelectUsers.jsp", out, false);
      out.write("\r\n\t\t<div class=\"list_2\" style=\"text-align: center; background: none;\">\r\n\t\t\t<input type=\"button\" class=\"button2\" value=\"清空\" onclick=\"clearSel('allSelEmps', 'attendEmp')\" />\r\n\t\t\t<input type=\"button\" class=\"button2\" value=\"确定\" onclick=\"getSelectUsers('allSelEmps', 'attendEmp');submitForm()\" />\r\n\t\t</div>\r\n\t\t<script type=\"text/javascript\">\r\n\t\t$(function(){\r\n\t\t\tloadItems(\"orgUserList\", \"allSelEmps\");\r\n\t\t});\r\n\t\tfunction submitForm(){\r\n\t\t\t// 提交表单\r\n\t\t\tdocument.deptForm.submit();\r\n\t\t}\r\n\t\t</script>\r\n\t</body>\r\n</html>");
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
