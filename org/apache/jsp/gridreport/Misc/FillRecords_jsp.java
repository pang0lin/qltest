/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:41:15 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.gridreport.Misc;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.JspWriter;
import javax.servlet.http.HttpServletResponse;
import java.util.zip.DeflaterOutputStream;
import java.lang.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public final class FillRecords_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("java.sql");
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.lang");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("java.io");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("javax.servlet.jsp.JspWriter");
    _jspx_imports_classes.add("javax.servlet.http.HttpServletResponse");
    _jspx_imports_classes.add("java.util.zip.DeflaterOutputStream");
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
      response.setContentType("text/html; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n\t<head>\r\n\t\t<title>Web报表(B/S报表)演示　－ 服务端生成加载报表数据脚本代码</title>\r\n\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\r\n\t\t<script src=\"../CreateControl.js\" type=\"text/javascript\"></script>\r\n\t\t<script type=\"text/javascript\">\r\n// <!CDATA[\r\n\r\n//在网页初始加载时向报表提供数据\r\nfunction window_onload() {\r\n    ReportViewer.Stop();\r\n    \r\n    var Recordset = ReportViewer.Report.DetailGrid.Recordset;\r\n    var fldCustomerID = ReportViewer.Report.FieldByName(\"CustomerID\");\r\n    var fldCompanyName = ReportViewer.Report.FieldByDBName(\"CompanyName\");\r\n    var fldContactName = ReportViewer.Report.FieldByDBName(\"ContactName\");\r\n    var fldContactTitle = ReportViewer.Report.FieldByDBName(\"ContactTitle\");\r\n    var fldAddress = ReportViewer.Report.FieldByDBName(\"Address\");\r\n    var fldCity = ReportViewer.Report.FieldByDBName(\"City\");\r\n    var fldRegion = ReportViewer.Report.FieldByDBName(\"Region\");\r\n    var fldPostalCode = ReportViewer.Report.FieldByDBName(\"PostalCode\");\r\n    var fldCountry = ReportViewer.Report.FieldByDBName(\"Country\");\r\n");
      out.write("    var fldPhone = ReportViewer.Report.FieldByDBName(\"Phone\");\r\n    var fldFax = ReportViewer.Report.FieldByDBName(\"Fax\");\r\n\r\n    ReportViewer.Report.PrepareRecordset();\r\n\r\n    ");

    try
    {
        String url="jdbc:odbc:webreport";
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");                // Class.forName 装载驱动程序 
        Connection con=DriverManager.getConnection(url, "sa", "");    //用适当的驱动程序类与 DBMS 建立一个连接
        Statement stmt=con.createStatement();                         //用于发送简单的SQL语句
        ResultSet rs=stmt.executeQuery("select * from Customers order by Region,City");
        while( rs.next() ) 
	    {
	
      out.write("\r\n\t\t    Recordset.Append();\r\n\t\t    fldCustomerID.Value = \"");
      out.print(rs.getString(1));
      out.write("\";\r\n\t\t    fldCompanyName.AsString = \"");
      out.print(rs.getString(2));
      out.write("\";\r\n\t\t    fldContactName.AsString = \"");
      out.print(rs.getString(3));
      out.write("\";\r\n\t\t    fldContactTitle.AsString = \"");
      out.print(rs.getString(4));
      out.write("\";\r\n\t\t    fldAddress.AsString = \"");
      out.print(rs.getString(5));
      out.write("\";\r\n\t\t    fldCity.AsString = \"");
      out.print(rs.getString(6));
      out.write("\";\r\n\t\t    fldRegion.AsString = \"");
      out.print(rs.getString(7));
      out.write("\";\r\n\t\t    fldPostalCode.AsString = \"");
      out.print(rs.getString(8));
      out.write("\";\r\n\t\t    fldCountry.AsString = \"");
      out.print(rs.getString(9));
      out.write("\";\r\n\t\t    fldPhone.AsString = \"");
      out.print(rs.getString(10));
      out.write("\";\r\n\t\t    fldFax.AsString = \"");
      out.print(rs.getString(11));
      out.write("\";\r\n\t\t    Recordset.Post();\r\n    ");

        } 
        rs.close();
        stmt.close();
        con.close();
    }
    catch(Exception e)
    {
        //out.println(e.toString());
    }
    
      out.write("\r\n\t    \r\n    ReportViewer.Start();\r\n}\r\n// ]]>\r\n\t\t</script>\r\n\t\t\r\n\t</head>\r\n\t<body onload=\"window_onload()\">\r\n        这个例子演示在服务器端根据记录集产生填充报表数据集的脚本代码，从而将服务端的数据传递到报表中。\r\n\t    <script type=\"text/javascript\"> \r\n            CreatePrintViewerEx(\"100%\", \"90%\", \"../grf/1a.grf\", \"\", false, \"<param name=BorderStyle value=1>\");\r\n\t    </script>\r\n\t</body>\r\n</html> ");
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
