/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:46:32 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.wap2.report;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import com.js.oa.scheme.worklog.po.WorkLogPO;
import com.js.oa.scheme.workreport.po.WorkReportPO;
import com.js.wap.util.DateTools;
import com.js.wap.util.WapStringTool;
import com.js.oa.scheme.workreport.po.WorkReportLeaderPO;

public final class wapReportIndex_005f3g_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("com.js.oa.scheme.workreport.po.WorkReportPO");
    _jspx_imports_classes.add("com.js.wap.util.WapStringTool");
    _jspx_imports_classes.add("com.js.oa.scheme.worklog.po.WorkLogPO");
    _jspx_imports_classes.add("com.js.wap.util.DateTools");
    _jspx_imports_classes.add("com.js.oa.scheme.workreport.po.WorkReportLeaderPO");
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

      out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"DTD/xhtml1-strict.dtd\">\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
 
request.setCharacterEncoding("UTF-8");
String path = request.getContextPath();
String app=request.getContextPath();
List<WorkLogPO> myReportlist=(List<WorkLogPO>)request.getAttribute("myReportList");
boolean hasUnderEmp = (Boolean)request.getAttribute("hasUnderEmp");

String dateStr = "";
String conHead = "";

      out.write("\r\n<HTML xmlns=\"http://www.w3.org/1999/xhtml\">\r\n<HEAD>\r\n<TITLE>????????????-??????????????????</TITLE>\r\n<META content=\"text/html; charset=UTF-8\" http-equiv=Content-Type>\r\n<META name=viewport content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\">\r\n<META name=apple-touch-fullscreen content=YES>\r\n<META name=apple-mobile-web-app-capable content=no>\r\n<LINK rel=apple-touch-icon href=\"");
      out.print(path);
      out.write("/wap2/images/iphone.jpg\">\r\n<LINK rel=stylesheet type=text/css href=\"");
      out.print(path);
      out.write("/wap2/css/main_3g.css\">\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path);
      out.write("/wap2/js/cookie.js\"></SCRIPT>\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path);
      out.write("/wap2/js/util.js\"></SCRIPT>\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path);
      out.write("/wap2/js/frost.js\"></SCRIPT>\r\n<META name=GENERATOR content=\"MSHTML 8.00.6001.19154\">\r\n</head>\r\n<BODY>\r\n   <DIV id=dd class=main>\r\n       <DIV id=top>\r\n           <SPAN id=lp><DIV class=btn_2><A href=\"");
      out.print(path);
      out.write("/wap2/index_3g.jsp\">??????</A></DIV></SPAN>\r\n           <SPAN id=title>????????????</SPAN>\r\n           <A class=btn_1 href=\"javascript:history.back();\">??????</A>\r\n       </DIV>\r\n       <DIV class=height2></DIV>    \r\n       <div class=\"lista1\">\r\n\t<div class=\"l3\"><a href=\"");
      out.print(app);
      out.write("/wap2/report/wapWriteReport_3g.jsp\">???????????????</a></div> <span></span></div>\r\n\t<div class=\"lista1\"><div class=\"l3\"><a href=\"");
      out.print(app);
      out.write("/wap/action/WapReportAction.do?method=getWapRepList&amp;repUser=mine\">\r\n \t??????????????????</a></span></div> <span></span></div>\r\n <!--???????????? -->\r\n ");
List<WorkReportPO> mineWeekReportList = (List<WorkReportPO>)request.getAttribute("mineWeekReportList");
	 String repCourse = "";
	if(mineWeekReportList != null && !mineWeekReportList.isEmpty()){
      out.write("\r\n\t \t<div class=\"lista1\"><div class=\"l3\"><a href=\"");
      out.print(app);
      out.write("/wap/action/WapReportAction.do?method=getWapWMRepList&amp;repUser=mine&amp;repType=1\">\r\n \t??????????????????</a></span></div> <span></span></div>\r\n <!-- ???????????? -->\r\n\t");
}List<WorkReportPO> mineMonthReportList = (List<WorkReportPO>)request.getAttribute("mineMonthReportList");
	if(mineMonthReportList != null && !mineMonthReportList.isEmpty()){
      out.write("\r\n\t \t<div class=\"lista1\"><div class=\"l3\"><a href=\"");
      out.print(app);
      out.write("/wap/action/WapReportAction.do?method=getWapWMRepList&amp;repUser=mine&amp;repType=3\">\r\n \t??????????????????</a></span></div> <span></span></div>\r\n\r\n <!-- ??????????????? -->\r\n ");
}//if(hasUnderEmp){
	List<WorkLogPO> unReportList = (List<WorkLogPO>)request.getAttribute("unReportList");
      out.write('\r');
      out.write('\n');
      out.write('	');
if(unReportList!=null && !unReportList.isEmpty()){
      out.write(" \r\n\t<!-- ???????????? -->\r\n\t<div class=\"lista1\"><div class=\"l3\"><a href=\"");
      out.print(app);
      out.write("/wap/action/WapReportAction.do?method=getWapRepList&amp;repUser=other\">\r\n \t??????????????????</a></span></div> <span></span></div>\r\n\t<!-- ???????????? -->\r\n\t");
}List<WorkReportLeaderPO> otherWeekReportList = (List<WorkReportLeaderPO>)request.getAttribute("otherWeekReportList");
	WorkReportPO wrpo = null;
	String leaderId = ""; 
	if(otherWeekReportList != null && !otherWeekReportList.isEmpty()){
      out.write("\r\n\t\t<div class=\"lista1\"><div class=\"l3\"><a href=\"");
      out.print(app);
      out.write("/wap/action/WapReportAction.do?method=getWapWMRepList&amp;repUser=other&amp;repType=1\">\r\n \t??????????????????</a></span></div> <span></span></div>\r\n\t<!-- ???????????? -->\r\n\t");
}List<WorkReportLeaderPO> otherMonthReportList = (List<WorkReportLeaderPO>)request.getAttribute("otherMonthReportList");
	if(otherMonthReportList != null && !otherMonthReportList.isEmpty()){
      out.write("\r\n\t\t<div class=\"lista1\"><div class=\"l3\"><a href=\"");
      out.print(app);
      out.write("/wap/action/WapReportAction.do?method=getWapWMRepList&amp;repUser=other&amp;repType=3\">\r\n \t??????????????????</a></span></div> <span></span></div>");
} 
      out.write("\r\n</div>\r\n</body>\r\n</html>\r\n\r\n\r\n");
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
