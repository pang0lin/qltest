/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:03:58 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.bjyh;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import com.js.oa.bjyh.bean.BjyhOpinionBean;

public final class bjyh_005fopinionSearchTemp_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-logic.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/public/jsp/online.jsp", Long.valueOf(1499751452000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.oa.bjyh.bean.BjyhOpinionBean");
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
      out.write("\r\n<script language=\"JScript.Encode\" src=\"/jsoa/js/browinfo.js\"></script>\r\n<script language=\"JScript.Encode\" src=\"/jsoa/js/rtxint.js\"></script>\r\n<script language=\"javascript\">\r\n\r\nfunction rtxonline(a){\r\n\t RAP(a);\r\n}\r\n\r\n</script>");
      out.write("\r\n\r\n\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
int index=0;
String userAccount = request.getAttribute("userAccount")==null?(null==request.getParameter("userAccount")?"":request.getParameter("userAccount")):request.getAttribute("userAccount").toString();

      out.write("\r\n<html>\r\n\t<head>\r\n\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n\t\t<title>????????????</title>\r\n\t\t<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t</head>\r\n\t<body class=\"MainFrameBox\">\r\n\t\t<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n\t\t\t<tr>\r\n\t\t\t\t<td>\r\n\t\t\t\t\t<form id=\"opinionList\" action=\"/jsoa/OpinionShow.do?action=opinionList\" method=\"post\">\r\n\t\t\t\t\t\t<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"1\" cellspacing=\"1\" class=\"searchbar\" id=\"searchbar\" style=\"display:none\">\r\n\t\t\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t\t  <td width=\"65\">???????????????</td>\r\n\t\t\t\t\t\t\t  <td width=\"188\">\t \r\n\t\t\t\t\t\t\t  \t<input type=\"text\" Class=\"inputtext\" id=\"userAccount\" name=\"userAccount\" value=\"");
      out.print(userAccount );
      out.write("\" size=\"20\"/>\t\t\r\n\t\t\t\t\t\t\t  </td>\r\n\t\t\t\t\t\t\t  <td align=\"right\">\r\n\t\t\t\t\t\t\t    <input type=\"button\" class=\"btnButton2Font\" onClick=\"searchSubmit();\" value=\"??????\" />\r\n\t\t\t\t\t\t\t\t<input type=\"button\" class=\"btnButton2Font\" onClick=\"searchReset();\" value=\"??????\" />\r\n\t\t\t\t\t\t\t  </td>\r\n\t\t\t\t\t\t\t  <td width=\"20\">&nbsp;</td>\r\n\t\t\t\t\t\t\t</tr>\r\n\t\t\t\t\t\t</table>\r\n\t\t\t\t\t </form> \r\n\t\t\t\t\t <table width=\"100%\" height=\"25\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"inlineBottomLine\">\r\n\t\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t  <td align=\"right\">\r\n\t\t\t\t\t\t\t<input type=\"button\" class=\"btnButton2Font\" id=\"btnButton2FontShow\" onClick=\"showSearch();\" value=\"??????\" />\r\n\t\t\t\t\t   \t\t<input type=\"button\" class=\"btnButton2Font\" id=\"btnButton2FontClose\" onClick=\"closeSearch();\" style=\"display:none\" value=\"????????????\" />\r\n\t\t\t\t\t\t  </td>\r\n\t\t\t\t\t\t</tr>\r\n\t\t\t\t\t</table> \r\n\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"listTable\" >\r\n\t\t\t\t\t  \t<tr align=\"center\">\r\n\t\t\t\t\t\t  <td class=\"listTableHead\"  width=\"10%\">????????????</td>\r\n\t\t\t\t\t\t  <td class=\"listTableHead\"  width=\"10%\">????????????</td>\r\n");
      out.write("\t\t\t\t\t\t  <td class=\"listTableHead\"  width=\"5%\">?????????</td>\r\n\t\t\t\t\t\t  <td class=\"listTableHead\"  width=\"5%\">?????????</td>\r\n\t\t\t\t\t\t  <td class=\"listTableHead\">????????????</td>\r\n\t\t\t\t\t\t</tr>\r\n\t\t\t\t\t</table>\r\n\t\t\t\t</td>\r\n\t\t\t</tr>\r\n\t\t</table>\r\n\t</body>\r\n</html>\r\n<script language=\"javascript\">\r\nfunction showSearch(){\r\n\tdocument.getElementById(\"searchbar\").style.display=\"\";\r\n\tdocument.getElementById(\"btnButton2FontShow\").style.display=\"none\";\r\n\tdocument.getElementById(\"btnButton2FontClose\").style.display=\"\";\r\n}\r\nfunction closeSearch(){\r\n\tdocument.getElementById(\"searchbar\").style.display=\"none\";\r\n\tdocument.getElementById(\"btnButton2FontShow\").style.display=\"\";\r\n\tdocument.getElementById(\"btnButton2FontClose\").style.display=\"none\";\r\n}\r\nfunction searchSubmit(){\r\n\tdocument.getElementById(\"opinionList\").submit();\r\n}\r\nfunction searchReset(){\r\n\tdocument.getElementById(\"userAccount\").value=\"\";\r\n\tcloseSearch();\r\n}\r\n//??????excel\r\n");
      out.write("\r\n</script>\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n<script src=\"/jsoa/js/imenu.js\"></script>");
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
