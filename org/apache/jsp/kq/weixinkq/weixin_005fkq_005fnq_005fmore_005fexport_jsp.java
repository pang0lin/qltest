/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:43:56 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.kq.weixinkq;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import java.util.*;
import java.text.*;

public final class weixin_005fkq_005fnq_005fmore_005fexport_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("java.text");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.util.ArrayList");
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
      response.setContentType("application/vnd.ms-excel;charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n");

request.setCharacterEncoding("utf-8");
String year,month,day;
year=request.getParameter("Year")==null?(request.getAttribute("Year")==null?"":request.getAttribute("Year").toString()):request.getParameter("Year");
month=request.getParameter("Month")==null?(request.getAttribute("Month")==null?"":request.getAttribute("Month").toString()):request.getParameter("Month");
day=request.getParameter("Day")==null?(request.getAttribute("Day")==null?"":request.getAttribute("Day").toString()):request.getParameter("Day");

String kuanggongTitle = "无考勤数据";
Date d = new Date();
if(year==null || "".equals(year)){
	year = String.valueOf(d.getYear()+1900);
}
if(month==null || "".equals(month)){
	month = String.valueOf(d.getMonth()+1);
}
if(day == null || "".equals(day)){
	day = String.valueOf(d.getDate());
}


int index = 0;
String range = "*0*";
if(session.getAttribute("rang")!=null && !"".equals(session.getAttribute("rang"))){
	range = (String)session.getAttribute("rang");
}
String dateString = request.getAttribute("dateString").toString();
String[] dateStr = dateString.split(",");
Map map=new HashMap();
map=(Map)request.getAttribute("map");
String empOrgName = request.getAttribute("orgName")+"";
String empName = request.getParameter("empName");
String empId = request.getParameter("empId");
Map dutyShow = (Map)request.getAttribute("dutyShow");

List list=(List)request.getAttribute("list");


      out.write("\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"application/vnd.ms-excel; charset=utf-8\">\r\n");
response.setHeader("Content-disposition", "attachment;filename="+new String("考勤统计详细信息表".getBytes("GBK"),"iso8859-1")+".xls");
      out.write("\r\n  <title>导出考勤统计信息</title>\r\n</head>\r\n  <body>\r\n  <table border=\"1\">\r\n    <tr>\r\n        <td colspan=\"");
      out.print(dateStr.length+2);
      out.write("\" height=\"30\" align=\"center\"><h2><b>考勤统计详细信息表</b></h2></td>\r\n    </tr>\r\n    <tr>\r\n        <td height=\"30\" align=\"center\" ><b>部门<b></td>\r\n        <td height=\"30\" align=\"center\"><b>姓名<b></td>\r\n        ");
for(int i=0;i<dateStr.length;i++){
      out.write("\r\n\t\t     <td height=\"30\" align=\"center\"><b>");
      out.print(dateStr[i] );
      out.write("<b></td>\t\r\n\t\t ");
}
      out.write("\r\n    </tr>\r\n     ");

	  for(int i=0;i<list.size();i++){ 
    	Object[] obj = (Object[])list.get(i);
    	 Map useridmap=(Map)map.get(obj[2]+"");
    	
      out.write("\r\n       <tr>\r\n     \t<td align=\"left\" height=\"30\">");
      out.print(obj[5] );
      out.write("</td>\r\n\t\t<td align=\"left\" height=\"30\">");
      out.print(obj[1] );
      out.write("</td>\r\n\t\t");

		    for(int j=0;j<dateStr.length;j++){
		       String dateMap=useridmap.get(dateStr[j]+"")==null?"<font color='red'>上班：</br></br>&nbsp;下班：</font>":useridmap.get(dateStr[j]+"")+"";
		       String[] datasplit=dateMap.toString().split(";");
	       
      out.write("\r\n     \t   <td align=\"left\" height=\"30\"  >");
      out.print(dateMap==null?"无数据":dateMap );
      out.write("</td>\r\n     \t");
}
      out.write("\r\n       </tr>\r\n    ");
} 
      out.write("\r\n    </table>\r\n  </body>\r\n</html>\r\n");
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