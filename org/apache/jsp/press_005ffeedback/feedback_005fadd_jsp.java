/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:54:43 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.press_005ffeedback;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.lang.Resource;

public final class feedback_005fadd_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.lang.Resource");
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
      			"../../../public/jsp/error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
String local = session.getAttribute("org.apache.struts.action.LOCALE").toString();

      out.write("\r\n\r\n<html>\r\n<head>\r\n<title>\r\n");
      out.print(Resource.getValue(local,"personalwork","pressmanage.newfeedback"));
      out.write("\r\n</title>\r\n</head>\r\n\r\n\r\n<!--link href=\"/jsoa/style/cssmain.css\" rel=\"stylesheet\" type=\"text/css\">\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject2.css\"-->\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<link href=\"/jsoa/style/cssmain.css\" rel=\"stylesheet\" type=\"text/css\">\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject2.css\">\r\n<script language=\"JavaScript\" src=\"/jsoa/js/resource/");
      out.print(local);
      out.write("/CommonResource.js\" type=\"text/javascript\"></script>\r\n<script language=\"JavaScript\" src=\"/jsoa/js/resource/");
      out.print(local);
      out.write("/PersonalworkResource.js\" type=\"text/javascript\"></script>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DateObject.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DatePicker.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/editlib.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/tree.js\"></SCRIPT>\r\n<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n<script  src=\"/jsoa/js/checkText.js\"  language=\"javascript\" ></script>\r\n<script src=\"/jsoa/js/Combox.js\"></script>\r\n\r\n");

  String pressId = (String)request.getParameter("pressId");

  String jspType = (String) request.getParameter("jspType");
  if(null==jspType) jspType="";

      out.write("\r\n\r\n<body class=\"searchbar\" onload=\"resizeWin(700,360);load();\">\r\n\r\n<form action=\"\" name=\"frm\" method=\"POST\">\r\n<table width=\"100%\" height=\"86\" border=\"0\">\r\n  <tr>\r\n    <td width=\"10%\" scope=\"col\" align=\"center\">");
      out.print(Resource.getValue(local,"personalwork","pressmanage.feedback"));
      out.write("<font color=\"red\">*</font>???<input  type=\"hidden\" name=\"pressId\" value=\"");
      out.print(pressId);
      out.write("\"/></td>\r\n    <td scope=\"col\" align=\"left\"><textarea rows=\"15\" cols=\"80\" name=\"content\" class=\"inputTextarea\"></textarea></td>\r\n  </tr>\r\n  <tr>\r\n  \t<td colspan=\"2\">\r\n       <input type=\"button\" value=\"");
      out.print(Resource.getValue(local,"personalwork","pressmanage.sendto"));
      out.write("\" onclick=\"return addFeedback_do();\" class=\"btnButton2font\"/><input type=\"submit\" value=\"");
      out.print(Resource.getValue(local,"common","comm.reset"));
      out.write("\" class=\"btnButton2font\"/><input type=\"button\" value=\"");
      out.print(Resource.getValue(local,"common","comm.exit"));
      out.write("\" onclick=\"javascript:window.close();\" class=\"btnButton2font\"/>\r\n       </td>\r\n  </tr>\r\n</table>\r\n</form>\r\n</body>\r\n</html>\r\n\r\n<script language=\"javascript\">\r\nvar  TypeTmp=\"");
      out.print(jspType.trim());
      out.write("\"; //????????????????????????\r\n//alert(TypeTmp);\r\n function  load(){\r\n    if (TypeTmp == \"exit\"){\r\n        try{\r\n        //window.opener.location.reload();\r\n        window.close();\r\n        }catch(e){\r\n            }\r\n      //window.close();\r\n    }\r\n    document.frm.content.focus();\r\n }\r\n\r\nfunction  myReset(){\r\n  document.frm.content.value=\"\";\r\n  window.close();\r\n}\r\n\r\nfunction close_window(){\r\n window.opener = null;\r\n window.close();\r\n}\r\n\r\nfunction addFeedback_do(){\r\n  if(trim(document.frm.content.value).length<1){\r\n    alert(Personalwork.pressmanage_contentvoid);\r\n    document.frm.content.focus();\r\n    return false;\r\n  }\r\n  if(trim(document.frm.content.value).length>500){\r\n    alert(Personalwork.pressmanage_contentoversize);\r\n    document.frm.content.focus();\r\n    return false;\r\n  }\r\n  //alert('??????????????????');\r\n  document.frm.action = \"/jsoa/pressManageAction.do?action=add_feedback\";\r\n  document.frm.submit();\r\n}\r\n\r\n\r\n\r\nfunction trim(tt){\r\n  return tt.replace(/(^\\s*)|(\\s*$)/g, \"\");\r\n}\r\n</script>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
