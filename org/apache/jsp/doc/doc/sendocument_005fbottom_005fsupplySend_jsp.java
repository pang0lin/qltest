/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:05:26 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.doc.doc;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class sendocument_005fbottom_005fsupplySend_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html; charset=GBK");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n<head>\r\n<title>直接发送</title>\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n<SCRIPT language=\"javascript\" src=\"/jsoa/js/openEndow.js\"></SCRIPT>\r\n</head>\r\n<base target=_self>\r\n<body leftmargin=\"0\" topmargin=\"0\" onload=\"resizeWin(500,400);\"  class=\"MainFrameBox\">\r\n\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td>\r\n\r\n<form name=\"frm1\">\r\n<!--table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"docBoxNoPanel\" style=\"margin:0px;\"-->\r\n<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"docBoxNoPanel\" style=\"margin:0px;border:0px;\">\r\n\r\n                <tr >\r\n                              <td height=\"22\" colspan=\"6\" nowrap>\r\n\t\t\t\t<div align=center>发送到：<input type=hidden name=sendToId2>\r\n\t\t\t\t<textarea cols=40 rows=5 class=css0 readonly=true name=sendToName2></textarea>&nbsp;&nbsp;&nbsp;&nbsp;<img src=/jsoa/images/group.gif>&nbsp;<img src=/jsoa/images/select.gif style=cursor:pointer onclick=\"openEndow('sendToId2','sendToName2',document.all.sendToId2.value,document.all.sendToName2.value,'user','no','userorggroup','");
      out.print(session.getAttribute("browseRange"));
      out.write("');\"></div>\r\n\t\t\t\t ");
if(new com.js.oa.message.action.ModelSendMsg().judgePurviewMessage("发文管理",session.getAttribute("domainId")==null?"0":session.getAttribute("domainId").toString())){
      out.write("\r\n                              <input type=\"checkbox\" name=\"sendFileNeedSendMsg2\" value=\"1\">短信提醒\r\n                              ");
}
      out.write("\r\n                              </td>\r\n                          </tr>\r\n \r\n     <tr>\r\n\r\n    <td align=\"right\">\r\n    <input type=\"button\" name=\"Submit\" value=\"直接发送\" class=\"btnButton4font\" onclick=\"include_save();\"/>\r\n    <input type=\"button\" name=\"Cancel\" value=\"取消\" class=\"btnButton2font\" onclick=\"window.close();\" />\r\n\t</td>\r\n  </tr>\r\n</table>\r\n</form>\r\n</td>\r\n</tr>\r\n</table>\r\n</body>\r\n</html>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n\r\n//保存\r\n//$:userId\r\n//*:orgId\r\n//@:groupId\r\nfunction include_save(){\r\n\r\n     if(document.frm1.sendToId2.value==\"\"){\r\n\t alert(\"请选择要发送的人！\");\r\n\t }else{\r\n\t var oo = new Object();\r\n     oo.sendToId2=document.frm1.sendToId2.value;\r\n\t oo.sendToName2=document.frm1.sendToName2.value;\r\n\t if(document.frm1.sendFileNeedSendMsg2){\r\n\t oo.sendFileNeedSendMsg2=document.frm1.sendFileNeedSendMsg2.value;\r\n\t }else{\r\n\t oo.sendFileNeedSendMsg2=\"\";\r\n\t }\r\n\t\twindow.returnValue =oo;\r\n\t\twindow.opener=null;\r\n\t\twindow.close();\r\n\t\t}\r\n\t\r\n}\r\n\r\n//-->\r\n</SCRIPT>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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