/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:42:22 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.chinaLife;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class userQingMore_jsp extends org.apache.jasper.runtime.HttpJspBase
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

List<String[]> kqQingData = (List<String[]>)request.getAttribute("dataList"); 
      out.write("\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<html>\r\n\t<head>\r\n\t\t<title>???????????????????????????</title>\r\n\t\t<link href=\"/jsoa/skin/blue/style.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t</head>\r\n\t  \r\n\t<body bgcolor=\"#ffffff\" class=\"docBoxNoPanel\">\r\n\t\t<div id=\"Layer1\" >\r\n\t\t\t<table width=\"90%\" align=\"center\" border=\"1\" cellpadding=\"5\" cellspacing=\"0\" >\r\n\t\t\t\t<tr><td colspan=\"15\" align=\"center\" style=\"font-size: 20px;\"><b>???????????????????????????</b></td></tr>\r\n\t\t\t\t<tr height=\"20px\"><td align=\"center\"><b>??????</td>\r\n\t\t\t\t<td align=\"center\"><b>??????</b></td>\r\n\t\t\t\t<td align=\"center\"><b>??????</b></td>\r\n\t\t\t\t<td align=\"center\"><b>??????</b></td>\r\n\t\t\t\t<td align=\"center\"><b>??????</b></td>\r\n\t\t\t\t<td align=\"center\"><b>??????</b></td>\r\n\t\t\t\t<td align=\"center\"><b>????????????</b></td>\r\n\t\t\t\t<td align=\"center\"><b>??????????????????</b></td>\r\n\t\t\t\t<td align=\"center\"><b>????????????</b></td>\r\n\t\t\t\t<td align=\"center\"><b>????????????</b></td>\r\n\t\t\t\t<td align=\"center\"><b>????????????</b></td>\r\n\t\t\t\t<td align=\"center\"><b>????????????</b></td>\r\n\t\t\t\t<td align=\"center\"><b>????????????</b></td>\r\n\t\t\t\t<td align=\"center\"><b>????????????</b></td>\r\n");
      out.write("\t\t\t\t<td align=\"center\"><b>????????????</b></td></tr>\r\n\t\t\t\t\r\n\t\t\t\t");
for(int i=0;i<kqQingData.size();i++){
					String[] kqQing = kqQingData.get(i); 
      out.write("\r\n\t\t\t\t<tr height=\"20px\"><td align=\"center\">");
      out.print(kqQing[0] );
      out.write("&nbsp;</td>\r\n\t\t\t\t<td align=\"center\">");
      out.print(kqQing[1] );
      out.write("&nbsp;</td>\r\n\t\t\t\t<td align=\"center\">");
      out.print(kqQing[2] );
      out.write("&nbsp;</td>\r\n\t\t\t\t<td align=\"center\">");
      out.print(kqQing[3] );
      out.write("&nbsp;</td>\r\n\t\t\t\t<td align=\"center\">");
      out.print(kqQing[4] );
      out.write("&nbsp;</td>\r\n\t\t\t\t<td align=\"center\">");
      out.print(kqQing[5] );
      out.write("&nbsp;</td>\r\n\t\t\t\t<td align=\"center\">");
      out.print(kqQing[6] );
      out.write("&nbsp;</td>\r\n\t\t\t\t<td align=\"center\">");
      out.print(kqQing[7] );
      out.write("&nbsp;</td>\r\n\t\t\t\t<td align=\"center\">");
      out.print(kqQing[8] );
      out.write("&nbsp;</td>\r\n\t\t\t\t<td align=\"center\">");
      out.print(kqQing[9] );
      out.write("&nbsp;</td>\r\n\t\t\t\t<td align=\"center\">");
      out.print(kqQing[10] );
      out.write("&nbsp;</td>\r\n\t\t\t\t<td align=\"center\">");
      out.print(kqQing[11] );
      out.write("&nbsp;</td>\r\n\t\t\t\t<td align=\"center\">");
      out.print(kqQing[12] );
      out.write("&nbsp;</td>\r\n\t\t\t\t<td align=\"center\">");
      out.print(kqQing[13] );
      out.write("&nbsp;</td>\r\n\t\t\t\t<td align=\"center\">");
      out.print(kqQing[14] );
      out.write("&nbsp;</td></tr>");
} 
      out.write("\r\n\t\t\t</table>\r\n\t\t</div>\r\n\t</body>\r\n</html>\r\n");
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
