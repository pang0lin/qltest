/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:56:31 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.public_;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class topType_005f_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html;charset=gbk");
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

String webapp=request.getContextPath(); //
String empEmail=request.getParameter("condition");
String inputType=request.getParameter("inputType");
String formTemp=request.getParameter("formAttr");
String allowId=request.getParameter("formAttr1");
String range=request.getParameter("range");
String show=request.getParameter("show");

String display=request.getParameter("display");
String type=request.getParameter("type");//????????????????????????
String orgInput=request.getParameter("orgInputType").toString();
if("yes".equals(inputType)){
  inputType="yes";
}else{
  inputType="no";
}

      out.write("\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<html>\r\n<head>\r\n<style type=\"text/css\">\r\n  body {font-size:12px;}\r\n  .inputCss {height:18px;}\r\n  .inputText{\r\n  behavior:url(/jsoa/js/validate.htc);\r\n  height:19px;\r\n  border:1px solid #829FBB;\r\n  background-color:#FFF;\r\n  margin-top:3px;\r\n}\r\n</style>\r\n</head>\r\n<script type=\"text/javascript\">\r\n var queryType='");
      out.print(type);
      out.write("';\r\n var search='");
      out.print(type);
      out.write("';\r\n \r\n function init(){\r\n    window.parent.rightFrame.init();\r\n }\r\n \r\n function doQuery(){\r\n \t var text=orgTreeBar.queryText.value;\r\n  \t orgTreeBar.action=\"/jsoa/selectObj.do?method=doQueryUser&radioType=''&range=");
      out.print(range);
      out.write("&type=\"+search+\"&allowId=");
      out.print(allowId);
      out.write("&inputType=");
      out.print(inputType);
      out.write("&condition=");
      out.print(empEmail);
      out.write("&queryType=\"+queryType+\"&text=\"+text;\r\n     orgTreeBar.target=\"leftBottonFrame\";\r\n     orgTreeBar.submit();\r\n  }\r\n \r\n function entySubmit(){\r\n  if(event.keyCode==13){\r\n    var text=orgTreeBar.queryText.value;\r\n    if(text==null||text==''||text=='????????????'){\r\n      alert('????????????????????????');\r\n      return false;\r\n    }else{\r\n    \tdoQuery();\r\n    }\r\n  }\r\n }\r\n \r\n</script>\r\n\r\n<body  bgcolor=\"#e6eff8\" topmargin=\"0\" leftmargin=\"0\" rightmargin=\"0\" bottommargin=\"0\" onkeydown=\"entySubmit()\">\r\n<form name=\"orgTreeBar\" method=\"post\">\r\n   \t<table width=\"100%\" height=\"50\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" id=\"table5\">\r\n          <tr>\r\n\t\t\t  <td id=\"department\" style=\"font-size:12px;\">\r\n\t\t\t  \t");
if(type.equals("customer")){
      out.write("\r\n\t\t\t  \t &nbsp;&nbsp;????????????\r\n\t\t\t  \t");
}else{
      out.write("\r\n\t\t\t  \t&nbsp;&nbsp;????????????\r\n\t\t\t  \t");
} 
      out.write("\r\n\t\t\t  \t???<input type=\"text\" name=\"queryText\" class=\"inputText\" size=\"8\" style=\"height:20px;\"><img style=\"cursor:pointer;\" src='/jsoa/images/toolbar/viewtext.gif' align='absmiddle' onClick=\"doQuery()\">\r\n\t\t\t   </td>\r\n\t\t\t  \r\n\t\t\t</tr>\r\n </table>\r\n</form>\r\n</body>\r\n</html>");
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
