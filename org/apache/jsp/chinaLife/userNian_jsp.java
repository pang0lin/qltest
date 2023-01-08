/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:42:28 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.chinaLife;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import com.js.system.manager.service.ManagerService;

public final class userNian_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("com.js.system.manager.service.ManagerService");
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

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
int yearNum = new Date().getYear()+1900;
List<String[]> nianData = (List<String[]>)request.getAttribute("dataList");
String year = request.getAttribute("year")+"";

      out.write("\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<html>\r\n\t<head>\r\n\t\t<title>员工年假情况统计表</title>\r\n\t\t<link href=\"/jsoa/skin/blue/style.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t\t<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject2.css\">\r\n\t\t<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DateObject.js\"></SCRIPT>\r\n\t\t<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DatePicker.js\"></SCRIPT>\r\n\t\t<SCRIPT language=javascript src=\"/jsoa/public/date_picker/editlib.js\"></SCRIPT>\r\n\t</head>\r\n\t  \r\n\t<body bgcolor=\"#ffffff\" class=\"docBoxNoPanel\">\r\n\t\t<div id=\"Layer1\" >\r\n\t\t\t<div height=\"30px;\" style=\"padding: 10px;\">\r\n\t\t\t<form action=\"/jsoa/kqShow.do\" method=\"get\" id=\"searchForm\" name=\"searchForm\">\r\n\t\t\t<input type=\"hidden\" value=\"kqNian\" name=\"action\" >\r\n\t\t\t<input type=\"hidden\" id=\"export\" name=\"export\">\r\n\t\t\t年度：<select id=\"year\" name=\"year\">\r\n\t\t\t");
for(int i=2014;i<=yearNum;i++){
      out.write("\r\n\t\t\t<option value=\"");
      out.print(i );
      out.write('"');
      out.write(' ');
      out.print(year.equals(i+"")?"selected":"" );
      out.write('>');
      out.print(i );
      out.write("年度</option>\r\n\t\t\t");
} 
      out.write("\r\n\t\t\t</select>\r\n            <input type=\"button\" class=\"btnButton2font\" value=\"查询\" onclick=\"exportExcel(0);\" />\r\n            <input type=\"button\" class=\"btnButton2font\" value=\"导出\" onclick=\"exportExcel(1);\" /></form></div>\r\n\t\t\t<div><table width=\"98%\" align=\"center\" border=\"1\" cellpadding=\"5\" cellspacing=\"0\" >\r\n\t\t\t\t<tr><td colspan=\"17\" align=\"center\" style=\"font-size: 20px;\"><b>员工年假情况统计表</b></td></tr>\r\n\t\t\t\t<tr height=\"20px\">\r\n\t\t\t\t\t<td align=\"center\"><b>部门</b></td>\r\n\t\t\t\t\t<td align=\"center\"><b>处室</b></td>\r\n\t\t\t\t\t<td align=\"center\"><b>姓名</b></td>\r\n\t\t\t\t\t<td align=\"center\"><b>应休年假天数</b></td>\r\n\t\t\t\t\t<td align=\"center\"><b>已休年假天数</b></td>\r\n\t\t\t\t\t<td align=\"center\"><b>剩余天数</b></td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t");
for(int i=0;i<nianData.size();i++){ 
					String[] nian = nianData.get(i); 
      out.write("\r\n\t\t\t\t<tr height=\"20px\">\r\n\t\t\t\t\t<td align=\"center\">");
      out.print(nian[0] );
      out.write("&nbsp;</td>\r\n\t\t\t\t\t<td align=\"center\">");
      out.print(nian[1] );
      out.write("&nbsp;</td>\r\n\t\t\t\t\t<td align=\"center\">");
      out.print(nian[2] );
      out.write("&nbsp;</td>\r\n\t\t\t\t\t<td align=\"center\">");
      out.print(nian[3] );
      out.write("&nbsp;</td>\r\n\t\t\t\t\t<td align=\"center\">");
      out.print(nian[4] );
      out.write("&nbsp;</td>\r\n\t\t\t\t\t<td align=\"center\">");
      out.print(nian[5] );
      out.write("&nbsp;</td>\r\n\t\t\t\t</tr>");
} 
      out.write("\r\n\t\t\t</table>\r\n\t\t\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/public/page/pageUtil.jsp", out, false);
      out.write("\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t</body>\r\n\t<script type=\"text/javascript\">\r\n\t\tfunction exportExcel(flag){\r\n\t\t\tdocument.getElementById(\"export\").value=flag;\r\n\t\t\tdocument.getElementById(\"searchForm\").submit();\r\n\t\t}\r\n\t</script>\r\n</html>\r\n<script src=\"/jsoa/js/openEndow.js\"></script>");
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