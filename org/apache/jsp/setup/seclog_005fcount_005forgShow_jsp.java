/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:50:43 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.setup;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class seclog_005fcount_005forgShow_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html;charset=GBK");
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

      out.write("\r\n<html>\r\n<head>\r\n<title>部门登录人</title>\r\n<link rel=\"stylesheet\" type=\"text/css\" href=\"style/cssmain.css\">\r\n<style type=\"text/css\">\r\n<!--\r\n.border_bottom {border-bottom:1px solid #ccc; border-top:1px solid #FFF;  background:#e1ebf0;}\r\n#table{font-size:12px; font-family:\"宋体\"; margin-top:25px; margin-left:25px;}\r\n.border_right_left{border-right:solid 1px #c4c9cc; border-left:solid 1px #fdfeff;border-bottom:1px solid #e6e6e4; border-top:1px solid #e6e6e4; background:#6699cc;}\r\nbody,table{font-size:12px; font-family:\"宋体\";}\r\n#topbottom{margin:35px 0px;}\r\n#background_border{background:#6699cc; border-left:1px solid #6699cc; border-bottom:1px solid #e6e6e4; border-top:1px solid #e6e6e4;}\r\n.boder_5{border-right:1px solid #e6e6e4;  border-bottom:1px solid #ccc; border-top:1px solid #FFF;   background:#e1ebf0;}\r\n/**/\r\n\r\n\r\n\r\n-->\r\n</style>\r\n</head>\r\n<body bgcolor=\"8A9AA2\" leftmargin=\"0\" topmargin=\"0\" >\r\n<table width=\"96%\" height=\"96%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" id=\"table\">\r\n<tr><td align=\"center\" valign=\"top\" bgcolor=\"#FFFFFF\" id=\"td\">\r\n");
      out.write("<table width=\"90%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"55\" id=\"topbottom\" >\r\n  <tr>\r\n    <td height=\"25\" width=\"35\" id=\"background_border\">&nbsp;</td>\r\n    <td align=\"center\" class=\"border_right_left\" style=\"border-left:0px;\">登录人</span></td>\r\n    <td align=\"center\" class=\"border_right_left\">登录时间</span></td>\r\n    <td align=\"center\" class=\"border_right_left\">退出时间</td>\r\n  </tr>\r\n");
List dataList = (List)request.getAttribute("dataList");
for(int i=0;i<dataList.size();i++){
	Object[] obj = (Object[])dataList.get(i); 
      out.write("\r\n  <tr>\r\n    <td height=\"28\" align=\"center\" nowrap=\"nowrap\" class=\"border_bottom\">");
      out.print(i+1 );
      out.write("</td>\r\n    <td nowrap=\"nowrap\" align=\"left\" class=\"border_bottom\">");
      out.print(obj[0] );
      out.write("&nbsp;</td>\r\n    <td nowrap=\"nowrap\" align=\"center\" class=\"border_bottom\">");
      out.print(obj[1] );
      out.write("&nbsp;</td>\r\n    <td nowrap=\"nowrap\" align=\"center\" valign=\"middle\" class=\"boder_5\">");
      out.print(obj[2] );
      out.write("&nbsp;</td>\r\n  </tr>\r\n");
} 
      out.write("\r\n  <tr><td colspan=\"4\">");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/public/page/pageUtil.jsp", out, false);
      out.write("</td></tr>\r\n</table>\r\n</td></tr>\r\n\r\n</table>\r\n\r\n</body>\r\n\r\n</html>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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