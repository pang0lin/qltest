/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:40:43 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.portal2;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.system.manager.service.*;
import com.js.oa.personalwork.person.service.*;
import com.js.system.service.organizationmanager.OrganizationBD;
import java.util.*;

public final class calendar_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("com.js.system.manager.service");
    _jspx_imports_packages.add("com.js.oa.personalwork.person.service");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.system.service.organizationmanager.OrganizationBD");
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

      out.write("\r\n\r\n\r\n\r\n\r\n");

   int j;
   java.util.Date curDate=new java.util.Date();
   int intCurYear=curDate.getYear()+1900;
   int intCurMonth=curDate.getMonth()+1;
   int intCurDay=curDate.getDate();


      out.write("\r\n\r\n<HTML>\r\n<HEAD>\r\n\r\n<script language=\"JavaScript\" src=\"/jsoa/js/MzTreeView10.js\"></script>\r\n<SCRIPT language=javascript src=\"/jsoa/portal2/js/noteCalendar.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/select_user/tree.js\"></SCRIPT>\r\n\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<TITLE></TITLE>\r\n</HEAD>\r\n<body>\r\n  <table width=\"100%\" border=0 align=\"center\" cellpadding=0 cellspacing=0>\r\n\t\t<form name=\"frmCalendarSample\" method=\"post\" action=\"#\" >\r\n\t\t<input type=\"hidden\" name=\"calSelectedDate\" value=\"\">\r\n        <tr>\r\n          <td valign=\"top\" height=\"5\"></td>\r\n        </tr>\r\n        <tr>\r\n          <td valign=\"top\"  bgcolor=\"#d4e1f4\"> <select name=\"tbSelYear\" onChange='fUpdateCal(frmCalendarSample.tbSelYear.value, frmCalendarSample.tbSelMonth.value)'>\r\n              ");
for(j=intCurYear-2;j<intCurYear+10;j++) {
      out.write("\r\n              <option value=\"");
      out.print(j);
      out.write('"');
      out.write(' ');
if(j==intCurYear){out.print("selected");}
      out.write('>');
      out.print(j);
      out.write("</option>\r\n              ");
}
      out.write("\r\n            </select>年&nbsp;<select name=\"tbSelMonth\" onChange='fUpdateCal(frmCalendarSample.tbSelYear.value,frmCalendarSample.tbSelMonth.value)'>\r\n              ");
for(j=1;j<13;j++) {
      out.write("\r\n              <option value=\"");
      out.print(j);
      out.write('"');
      out.write(' ');
if(j==intCurMonth){out.print("selected");}
      out.write(">\r\n              ");
if(j<10){
      out.write('0');
}
      out.print(j);
      out.write("</option>");
}
      out.write("\r\n            </select>月</td>\r\n        </tr>\r\n        <tr>\r\n          <td valign=\"top\"  bgcolor=\"#d4e1f4\">\r\n\t\t  <script  language=\"JavaScript\">\r\n\t\tfDrawCal(");
      out.print(intCurYear);
      out.write(',');
      out.write(' ');
      out.print(intCurMonth);
      out.write(',');
      out.print(intCurDay);
      out.write(", 20, 20, \"10px\", \"bold\", 3);\r\n\t\t  </script>\r\n\t\t </td>\r\n        </tr>\r\n\t\t</form>\r\n      </table>\r\n</body>\r\n");
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
