/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:07:12 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class dataSynDefault_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<html>\r\n");
String name = request.getParameter("name");
String index = request.getParameter("index");
String value = request.getParameter("value"); 
      out.write("\r\n<head>\r\n<title>??????????????????</title>\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n<SCRIPT language=javascript src=\"/jsoa/js/openEndow.js\"></SCRIPT>\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject2.css\">\r\n\r\n</head>\r\n  \r\n<body leftmargin=\"10\" scroll=\"auto\" topmargin=\"0\" class=\"MainFrameBox Pupwin docBoxNoPanel\" onload=\"load();\" >\r\n<div style=\"padding:10px;\">\r\n\t<table width=\"98%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"docBoxNoPanel\">\r\n\t\t<tr><td width=\"100px\">????????????</td><td><input type=\"checkBox\" name=\"varchar\" id=\"varchar\" value=\"@varchar@\" ></td></tr>\r\n\t\t<tr><td width=\"100px\">?????????</td><td><input type=\"checkBox\" name=\"tranValue\" id=\"tranValue\" value=\"@tranValue@\" >(?????????????????????????????????????????????????????????)</td></tr>\r\n\t\t<tr style=\"display:none;\"><td>???????????????</td><td><select name=\"nowTime\" id=\"nowTime\" onchange=\"setSelect(this);\">\r\n\t\t\t<option value=\"\" selected>---?????????---</option>\r\n\t\t\t<option value=\"@sql-date@\">sqlServer</option>\r\n\t\t\t<option value=\"@mysql-date@\">Mysql</option>\r\n");
      out.write("\t\t\t<option value=\"@oracle-date@\">oracle</option>\r\n\t\t</select></td></tr>\r\n\t\t<tr><td width=\"100px\">????????????</td><td><input type=\"text\" style=\"width:500px;\" name=\"defValue\" id=\"defValue\" value=\"\" ></td></tr>\r\n\t\t<tr><td colspan=2><input type=\"button\" class=\"btnButton4font\" onclick=\"javascript:save();\" value=\"????????????\" />\r\n\t\t<input type=\"button\" class=\"btnButton4font\" onclick=\"javascript:window.close();\" value=\"??????\" /></td></tr>\r\n\t</table>\r\n</div>\r\n</body>\r\n<script type=\"text/javascript\">\r\nfunction save(){\r\n\tvar index = ");
      out.print(index );
      out.write(";\r\n\tvar defaultValue = opener.document.getElementsByName(\"");
      out.print(name );
      out.write("\");\r\n\tvar result = \"\";\r\n\tvar varchar = document.getElementById(\"varchar\");\r\n\tvar tranValue = document.getElementById(\"tranValue\");\r\n\tvar defValue = document.getElementById(\"defValue\");\r\n\tvar nowTime = document.getElementById(\"nowTime\");\r\n\tif(varchar.checked){\r\n\t\tresult += \"@varchar@\";\r\n\t}\r\n\tif(tranValue.checked){\r\n\t\tresult += \"@tranValue@\";\r\n\t}\r\n\tif(defValue.value!=\"\"){\r\n\t\tresult += defValue.value;\r\n\t}\r\n\tif(nowTime.value!=\"\"){\r\n\t\tresult = nowTime.value;\r\n\t}\r\n\tdefaultValue[index].value = result;\r\n\twindow.close();\r\n}\r\nfunction load(){\r\n\tvar index = ");
      out.print(index );
      out.write(";\r\n\tvar defaultValue = opener.document.getElementsByName(\"");
      out.print(name );
      out.write("\");\r\n\tvar varchar = document.getElementById(\"varchar\");\r\n\tvar tranValue = document.getElementById(\"tranValue\");\r\n\tvar defValue = document.getElementById(\"defValue\");\r\n\tvar nowTime = document.getElementById(\"nowTime\");\r\n\tvar result = defaultValue[index].value ;\r\n\t");
if("".equals(value)){ 
      out.write("\r\n\ttranValue.disabled = true;\r\n\t");
} 
      out.write("\r\n\tif(result.indexOf(\"@varchar@\")>=0){\r\n\t\tvarchar.checked=true;\r\n\t\tresult = result.replace(\"@varchar@\",\"\");\r\n\t}\r\n\tif(result.indexOf(\"@tranValue@\")>=0){\r\n\t\ttranValue.checked=true;\r\n\t\tresult = result.replace(\"@tranValue@\",\"\");\r\n\t}\r\n\tif(result.indexOf(\"-date@\")>0){\r\n\t\tnowTime.value=result;\r\n\t\treturn;\r\n\t}\r\n\tdefValue.value = result;\r\n}\r\nfunction setSelect(obj){\r\n\tvar varchar = document.getElementById(\"varchar\");\r\n\tvar tranValue = document.getElementById(\"tranValue\");\r\n\tvar defValue = document.getElementById(\"defValue\");\r\n\tif(obj.value==\"\"){\r\n\t\tvarchar.disabled = false;\r\n\t\ttranValue.disabled = false;\r\n\t\tdefValue.disabled = false;\r\n\t}else{\r\n\t\tvarchar.disabled = true;\r\n\t\ttranValue.disabled = true;\r\n\t\tdefValue.disabled = true;\r\n\t}\r\n}\r\n</script>\r\n</html>\r\n");
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
