/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:53:39 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.message.select_005fuser;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class select_005faffirm_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html;charset=GBK");
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

  String allowId=request.getParameter("allowId");
  String mobilePhone=request.getParameter("mobilephone");
  String allowName=request.getParameter("allowName");
  String selectedName = request.getParameter("selectedName");
  String selectedPhone=request.getParameter("selectedPhone");
  //selectedName = new String(selectedName.getBytes("iso-8859-1"));
   String email=request.getParameter("email");

      out.write("\r\n<html>\r\n<head>\r\n<title>Untitled Document</title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\"/>\r\n</head>\r\n\r\n<body  class=\"MainFrameBox\">\r\n<br>\r\n<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"1\" cellspacing=\"1\" class=\"\">\r\n <tr>\r\n     <td>\r\n\t     <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"1\">\r\n\t\t   <form name=\"frm\">\r\n\t\t      <tr>\r\n                                    <td align=\"center\"><br>\r\n                                    <textarea id=\"allowName\" name=\"allowName\" cols=\"50\" rows=\"5\"\r\n                                        class=\"inputTextarea\" readonly=\"readonly\">");
if(selectedName==null ||
                                        selectedName.equals("null")){
                                        }else{
                                            out.print(selectedName);
                                        }
      out.write("</textarea>\r\n                                        <input type=\"hidden\" id=\"selectedPhone\" name=\"selectedPhone\"\r\n                                         value=\"");
if(request.getParameter("selectedPhone")==null ||
                                        request.getParameter("selectedPhone").equals("null")){
                                        }else{
                                            out.print(request.getParameter("selectedPhone"));
                                        }
      out.write("\">\r\n\t\t\t\t         <input type=\"hidden\" id=\"allowId\" name=\"allowId\"\r\n                                         value=\"");
if(request.getParameter("selectedId")==null ||
                                        request.getParameter("selectedId").equals("null")){
                                        }else{
                                            out.print(request.getParameter("selectedId"));
                                        }
      out.write("\">\r\n\t\t\t\t\t<input type=\"hidden\" id=\"mobilePhone\" name=\"mobilePhone\"\r\n                                         value=\"");
if(request.getParameter("mobilePhone")==null ||
                                        request.getParameter("mobilePhone").equals("null")){
                                        }else{
                                            out.print(request.getParameter("mobilePhone"));
                                        }
      out.write("\">\r\n\r\n\t\t\t\t  </td>\r\n               </tr>\r\n\t\t\t   <tr>\r\n\t\t\t\t  <td align=\"left\">\r\n\t\t\t\t\t  <button class=\"btnButton2font\" onClick=\"javascript:turnBack();\">??????</button>\r\n\t\t\t\t\t  <button class=\"btnButton2font\" onClick=\"javascript:frmReset();\">??????</button>\r\n\t\t\t\t\t  <button class=\"btnButton2font\" onClick=\"javascript:frmClear();\">??????</button>\r\n\t\t\t\t  </td>\r\n\t\t\t  </tr>\r\n\t\t\t</form>\r\n\t     </table>\r\n\t </td>\r\n  </tr>\r\n</table>\r\n\r\n</body>\r\n</html>\r\n<script langauage=\"javascript\">\r\nfunction turnBack(){\r\n    _id=document.frm.allowId.value;\r\n    _name=document.frm.allowName.value;\r\n    _phone=document.frm.selectedPhone.value;\r\n\r\n\r\n    mobilePhone=document.frm.mobilePhone.value;\r\n\r\n    parent.opener.document.all.");
      out.print(allowId);
      out.write(".value = _id;\r\n    parent.opener.document.all.");
      out.print(allowName);
      out.write(".value=_name;\r\n    //parent.opener.document.all.");
//=mobilePhone
      out.write(".value=mobilePhone;\r\n    ");
if(email==null||email.equals("null")){
      out.write("\r\n      parent.opener.document.all.");
      out.print(mobilePhone);
      out.write(".value=_phone;\r\n    ");
}else{
      out.write("\r\n      parent.opener.document.all.");
      out.print(mobilePhone);
      out.write(".value=_name;\r\n   ");
}
      out.write("\r\n    parent.window.close();\r\n}\r\n\r\nfunction frmClear(){\r\n//    document.frm.allowId.value=\"\";\r\n//    document.frm.allowName.value=\"\";\r\n\tvar parentHref = parent.location.href;\r\n\r\n\tvar firstPos = parentHref.indexOf(\"&selectedId=\") + 12;\r\n\tvar secondPos = parentHref.indexOf(\"&\", firstPos);\r\n//\talert(firstPos);\r\n//\talert(secondPos);\r\n\tparentHref = parentHref.substring(0, firstPos) + parentHref.substring(secondPos);\r\n//\talert(parentHref);\r\n\tfirstPos = parentHref.indexOf(\"&selectedName=\") + 14;\r\n\tsecondPos = parentHref.indexOf(\"&\", firstPos);\r\n\tparentHref = parentHref.substring(0, firstPos) + parentHref.substring(secondPos);\r\n//\talert(parentHref);\r\n\tparent.location.href = parentHref;\r\n\r\n}\r\n\r\nfunction frmReset(){\r\n\tparent.location.reload();\r\n}\r\n</script>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
