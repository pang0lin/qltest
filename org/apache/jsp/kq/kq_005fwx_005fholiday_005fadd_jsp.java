/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:43:28 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.kq;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class kq_005fwx_005fholiday_005fadd_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html; charset=GBK");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n<html>\r\n<head>\r\n<title>???????????????</title>\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"js/js.js\" language=\"javascript\"></script>\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject2.css\">\r\n<script language=\"javascript\" src=\"/jsoa/js/checkForm.js\"></script>\r\n<script language=\"javascript\" src=\"/jsoa/js/openEndow.js\"></script>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DateObject.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DatePicker.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/tree.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/editlib.js\"></SCRIPT>\r\n<script src=\"/jsoa/js/toolbar/standardAdd.js\" language=\"javascript\"></script>\r\n<script src=\"/jsoa/js/toolbar/boardroomAdd.js\" language=\"javascript\"></script>\r\n<script src=\"/jsoa/js/clsPullXMenu.js\" language=\"javascript\"></script>\r\n<script src=\"/jsoa/public/jsp/cmdbutton.jsp?button=Close,Send,Print\"></script>\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

      out.write("\r\n</head>\r\n<body class=\"MainFrameBox Pupwin\"  >\r\n<form action=\"/jsoa/WxKqAction.do?action=addHoliday\" id=\"form1\" name=\"form1\" method=\"post\" target=\"myIframe\" >\r\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"docBoxNoPanel\" \r\n\tstyle=\"padding-left:10px; padding-top:10px;\">\r\n<tr>\r\n<td width=\"80\" ><label class=\"mustFillcolor\" style=\"width:7px;\" >*</label>???????????????</td>\r\n<td colspan=\"5\"><input style=\"width:85%\" type=\"text\" name=\"holidayName\"  class=\"inputText\" id=\"holidayName\" /></td>\r\n</tr>\r\n<tr>\r\n<td height=\"32\" nowrap><label class=\"mustFillcolor\" style=\"width:7px;\" > *</label>???????????????</td>\r\n<td width=\"300px\"><script language=javascript>\r\nvar dtpDate = createDatePicker(\"start_date\");\r\n</script>&nbsp;&nbsp;<input type=\"hidden\" name=\"beginTime\" id=\"beginTime\" value=\"00:00\"> </td>\r\n\t<td width=\"80\" nowrap>???????????????</td>\r\n<td width=\"300px\"><script language=javascript>\t\t\t\t\t\t\t\t\t\r\nvar dtpDate = createDatePicker(\"end_date\");\t\t\t\t\t\t\t\t\t\r\n</script>&nbsp;&nbsp;<input type=\"hidden\" name=\"endTime\" id=\"endTime\" value=\"23:59\"> </td>\r\n");
      out.write("<td width=\"100px\"><label class=\"mustFillcolor\" style=\"width:7px;\" ></label>?????????</td>\r\n<td ><select name=\"type\"><option value='1'>??????</option>\r\n<option value='0' selected>??????</option></select></td>\r\n</tr>\r\n<tr>\r\n<td valign=\"top\"><label class=\"mustFillcolor\" style=\"width:7px;\" > </label>?????????</td>\r\n<td height=\"95\" colspan=\"5\" valign=\"middle\">\r\n<textarea name=\"memo\" rows=\"6\" style=\"width:85%\" class=\"inputTextarea\"></textarea></td>\r\n</tr>\r\n\r\n<tr>\r\n   <td colspan=\"6\">\r\n\t<input type=\"button\" class=\"btnButton4font\" style=\"cursor:pointer\" onclick=\"javascript:save();\" value=\"????????????\" />\r\n\t<input type=\"button\" class=\"btnButton2font\" style=\"cursor:pointer\" onclick=\"javascript:document.form1.reset();\" value=\"??????\" />\r\n\t<input type=\"button\" class=\"btnButton2font\" style=\"cursor:pointer\" onclick=\"javascript:window.close();\" value=\"??????\" />\r\n  </td>\r\n</tr>\r\n</table>\r\n</form>\r\n<iframe name=\"myIframe\" id=\"myIframe\" style=\"display:none;\"></iframe>\r\n</body>\r\n<script language=\"javascript\">\r\nfunction save(){\r\n\tif(document.getElementById(\"holidayName\").value==\"\"){\r\n");
      out.write("\t\talert(\"???????????????????????????\");\r\n\t\treturn;\r\n\t}\r\n\tif(document.getElementById(\"holidayName\").value.length>20){\r\n\t\talert(\"??????????????????10????????????\");\r\n\t\treturn;\r\n\t}\r\n\tvar btime=new Date(document.all.start_date.value+\" \"+document.all.beginTime.value);\r\n\tvar etime=new Date(document.all.end_date.value+\" \"+document.all.endTime.value);\r\n\tif(btime>=etime){\r\n\t\talert(\"???????????????????????????????????????\");\r\n\t\treturn;\r\n\t}\r\n\tdocument.form1.submit();\r\n}\r\nfunction onloadParentPage(){\r\n\twindow.opener.location.reload();\r\n\twindow.close();\r\n}\r\n</script>\r\n</html>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
