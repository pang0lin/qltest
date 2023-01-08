/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:41:36 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.gridreport.Advance.ReportInDB;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.lang.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public final class ReportSaveToDB_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write('\r');
      out.write('\n');

    //因不熟悉java，暂时未能成功将二进制数据写入到数据库
    
    int DataLen = request.getContentLength();
    
//    //打开文件准备读取数据
//    String FileName = request.getRealPath("grf") + "\\1a.grf";
//    FileInputStream fis = new FileInputStream(FileName);
//    byte[] DataBuf = new byte[1024*50];
//    //读文件数据，并写入响应流中
//    int ReadBytes = fis.read(DataBuf);
//    DataLen = 100; //DataBuf.length();
    
    if (DataLen > 0)
    {
        //如下转换之后，中文参数才不会乱码
        String ReportName = new String(request.getParameter("Report").getBytes("ISO-8859-1"));  
        
        //读出客户端发送的数据
        byte[] DataBuf = new byte[DataLen];   
        ServletInputStream sif = request.getInputStream();
        sif.read(DataBuf);
        
        //try
        //{
            //String url= "jdbc:mysql://localhost/gridreport?user=root&password=";
            //Class.forName("com.mysql.jdbc.Driver");         // Class.forName 装载驱动程序 
            //Connection con=DriverManager.getConnection(url); //用适当的驱动程序类与 DBMS 建立一个连接
            String url="jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=gridreport";
            Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");// Class.forName 装载驱动程序 
            Connection con=DriverManager.getConnection(url, "sa", "");    //用适当的驱动程序类与 DBMS 建立一个连接
            
            Statement stmt = con.createStatement();            //用于发送简单的SQL语句
            String QuerySQL = "Select ReportData from Report where ReportName='" + ReportName + "'";
            ResultSet rs = stmt.executeQuery(QuerySQL);
            boolean Existed = rs.next();
            rs.close();
            stmt.close();

            Blob blob = con.createBlob(); //new Blob(con);
            //对CLOB对象赋值
            blob.setBytes(0,DataBuf);

            //报表模板数据写入表中
            String strSQL;
            if (Existed)
                strSQL = "UPDATE Report SET ReportData=@ReportData where ReportName=?";
            else
                strSQL = "INSERT INTO Report(ReportName,ReportData) VALUES(?, ?)";
                    
            PreparedStatement pstmt = con.prepareStatement(strSQL);
            int Index = 1;
            if ( !Existed )
            {
                pstmt.setString(1, ReportName);
                ++Index;
            }
            pstmt.setBlob(Index, blob);
            pstmt.executeUpdate();                    
            pstmt.close();
                    
            con.close();
//        }
//        catch(Exception e)
//        {
//            out.println(e.toString());
//        }
    }
            //out.println("finished");

      out.write(' ');
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
