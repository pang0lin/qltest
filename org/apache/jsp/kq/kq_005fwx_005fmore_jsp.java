/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:43:35 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.kq;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class kq_005fwx_005fmore_jsp extends org.apache.jasper.runtime.HttpJspBase
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

String empName = request.getAttribute("empName")+"";
String empNumber = request.getAttribute("empNumber")+"";
String empOrg = request.getAttribute("empOrg")+"";
//????????????????????????001[????????????]???003[????????????]???010[????????????]????????????002[????????????]???
//?????????????????????005[???????????????]???008[???????????????]?????????006[???????????????]???007[???????????????]???
Map<String,String> map = (Map<String,String>)request.getAttribute("map");
String[] q001 = (map.get("001")==null?"":map.get("001")).split(",");
String[] q002 = (map.get("002")==null?"":map.get("002")).split(",");
String[] q003 = (map.get("003")==null?"":map.get("003")).split(",");
String[] q010 = (map.get("010")==null?"":map.get("010")).split(",");
String[] q005 = (map.get("005")==null?"":map.get("005")).split(",");
String[] q006 = (map.get("006")==null?"":map.get("006")).split(",");
String[] q007 = (map.get("007")==null?"":map.get("007")).split(",");
String[] q008 = (map.get("008")==null?"":map.get("008")).split(",");

int[] num={q001.length,q002.length,q003.length,q010.length,q005.length,q006.length,q007.length,q008.length};
int max=0;
for(int i=0;i<num.length;i++){
	if(num[i]>max){
		max=num[i];
	}
}
String[] date = (request.getParameter("date")==null?"":request.getParameter("date")).split("-");
String showDate = date[0]+"???"+date[1]+"???"+date[2]+"???";
 
      out.write("\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<html>\r\n  <head>\r\n    <title>");
      out.print(empName+showDate+"????????????" );
      out.write("</title>\r\n    <link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n  </head>\r\n  \r\n  <body class=\"MainFrameBox Pupwin\" onLoad=\"load();resizeWin(800,650);\">\r\n  <table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"docBoxNoPanel\">\r\n  <tr height=\"50px\"><td valign=\"bottom\" >\r\n  <div sytle=\"width:90%;padding-left:200px\">&nbsp;&nbsp;&nbsp;\r\n  ?????????<b>");
      out.print(empName );
      out.write("</b>&nbsp;&nbsp;&nbsp;&nbsp;\r\n  ?????????<b>");
      out.print(empNumber );
      out.write("</b>&nbsp;&nbsp;&nbsp;&nbsp;\r\n  ?????????<b>");
      out.print(empOrg );
      out.write("</b>\r\n  \r\n  </div></td></tr>\r\n  <tr><td valign=\"top\">\r\n    <table width=\"90%\" id=\"detailTable\" borderColor=\"#000000\" borderColorDark=\"#e1e1e1\" \r\n\t    \t\t\tborder=\"1\" cellSpacing=\"0\" cellPadding=\"1\" align=\"center\" sytle=\"margin-top:50px;\">\r\n    \t<tr height=\"40px\">\r\n    \t\t<td colspan=\"9\" align=\"left\">&nbsp;");
      out.print(empName+showDate+"????????????" );
      out.write("</td>\r\n    \t</tr>\r\n    \t<tr height=\"30px\">\r\n    \t\t<td colspan=\"4\" align=\"center\"><b>?????????</b></td>\r\n    \t\t<td rowspan=\"");
      out.print(max+2 );
      out.write("\">&nbsp;</td>\r\n    \t\t<td colspan=\"4\" align=\"center\"><b>?????????</b></td>\r\n    \t</tr>\r\n    \t<tr>\r\n    \t\t<td align=\"center\"><font color=\"green\">????????????</font></td>\r\n    \t\t<td align=\"center\"><font color=\"green\">????????????</font></td>\r\n    \t\t<td align=\"center\"><font color=\"green\">????????????</font></td>\r\n    \t\t<td align=\"center\">????????????</td>\r\n    \t\t<td align=\"center\"><font color=\"green\">???????????????</font></td>\r\n    \t\t<td align=\"center\">???????????????</td>\r\n    \t\t<td align=\"center\"><font color=\"green\">???????????????</font></td>\r\n    \t\t<td align=\"center\">???????????????</td>\r\n    \t</tr>\r\n    \t");
for(int i=0;i<max;i++){ 
      out.write("\r\n\t    \t<tr>\r\n\t    \t\t<td align=\"center\">");
      out.print((q001.length>i?q001[i]:"") );
      out.write("&nbsp;</td>\r\n\t    \t\t<td align=\"center\">");
      out.print((q003.length>i?q003[i]:"") );
      out.write("&nbsp;</td>\r\n\t    \t\t<td align=\"center\">");
      out.print((q010.length>i?q010[i]:"") );
      out.write("&nbsp;</td>\r\n\t    \t\t<td align=\"center\">");
      out.print((q002.length>i?q002[i]:"") );
      out.write("&nbsp;</td>\r\n\t    \t\t<td align=\"center\">");
      out.print((q005.length>i?q005[i]:"") );
      out.write("&nbsp;</td>\r\n\t    \t\t<td align=\"center\">");
      out.print((q008.length>i?q008[i]:"") );
      out.write("&nbsp;</td>\r\n\t    \t\t<td align=\"center\">");
      out.print((q006.length>i?q006[i]:"") );
      out.write("&nbsp;</td>\r\n\t    \t\t<td align=\"center\">");
      out.print((q007.length>i?q007[i]:"") );
      out.write("&nbsp;</td>\r\n\t    \t</tr>\r\n    \t");
} 
      out.write("\r\n    </table></td></tr></table>\r\n  </body>\r\n</html>\r\n");
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
