/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:46:11 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.wap2.note;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.net.URLDecoder;

public final class newNotePaper_005f3g_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.net.URLDecoder");
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

      out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"DTD/xhtml1-strict.dtd\">\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n\r\n");
 
request.setCharacterEncoding("UTF-8");
String path = request.getContextPath();

      out.write("\r\n<HTML xmlns=\"http://www.w3.org/1999/xhtml\">\r\n<HEAD>\r\n<TITLE>新增工作便签</TITLE>\r\n<META content=\"text/html; charset=UTF-8\" http-equiv=Content-Type>\r\n<META name=viewport content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\">\r\n<META name=apple-touch-fullscreen content=YES>\r\n<META name=apple-mobile-web-app-capable content=no>\r\n<LINK rel=apple-touch-icon href=\"");
      out.print(path );
      out.write("/wap2/images/iphone.jpg\">\r\n<LINK rel=stylesheet type=text/css href=\"");
      out.print(path );
      out.write("/wap2/css/main_3g.css\">\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path );
      out.write("/wap2/js/cookie.js\"></SCRIPT>\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path );
      out.write("/wap2/js/util.js\"></SCRIPT>\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path );
      out.write("/wap2/js/frost.js\"></SCRIPT>\r\n<META name=GENERATOR content=\"MSHTML 8.00.6001.19154\">\r\n<script type=\"text/javascript\">\r\nfunction save(){\r\n\tvar con=document.getElementById(\"content\").value;\r\n\tif(con==\"\"){\r\n\talert(\"请填写便签内容\");\r\n\t}else{\r\n\t\tdocument.getElementById(\"contents\").value=encodeURI(con);\r\n\t\tdocument.getElementById(\"form1\").submit();\r\n\t}\r\n}\r\n</script>\r\n</head>\r\n<body>\r\n<form action=\"");
      out.print(path );
      out.write("/wap/action/WapNotePaperAction.do?action=add\" method=\"post\" id=\"form1\">\r\n<input type=\"hidden\" id=\"contents\" name=\"contents\"/>\r\n\t<div class=\"main\">\r\n\t\t<div id=\"top\">\r\n\t\t\t<span id=\"lp2\"><div class=\"btn_3\"><a href=\"");
      out.print(path );
      out.write("/wap/action/WapNotePaperAction.do?action=notePaperList\">返回</a></div></span>\r\n\t\t\t<span id=\"title2\"></span>\r\n\t\t</div>\r\n<div class=\"f_1\">新增工作便签</div>\r\n\t<div class=\"box_2\">\r\n\t<div class=\"list_2\">\r\n\t<div align=\"center\">\r\n\t\t\t\t<textarea rows=\"4\" style=\"width:250px;\" id=\"content\"></textarea>\r\n\t\t\t\t</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t</div>\r\n\t<div class=\"list_2\">\r\n\t\t<div align=\"center\">\r\n\t\t<input class=\"button2\" type=\"button\" value=\"保存\" onclick=\"javascript:save();\" />\r\n\t\t</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t</div>\r\n</form>\r\n</div>\r\n</div>\r\n</body>\r\n</html>\r\n");
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
