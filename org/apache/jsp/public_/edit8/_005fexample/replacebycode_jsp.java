/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:57:23 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.public_.edit8._005fexample;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class replacebycode_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n<html>\r\n<head>\r\n<title>eWebEditor ??? ???Javascript???????????????DIV??????????????????</title>\r\n<meta http-equiv=Content-Type content=\"text/html; charset=GBK\">\r\n<script type=\"text/javascript\" src=\"../ewebeditor.js\"></script>\r\n<link rel='stylesheet' type='text/css' href='example.css'>\r\n\r\n<script type=\"text/javascript\">\r\n// ?????????EWEBEDITOR.Replace(s_Id, o_Config)\r\n// ?????????s_Id     : ????????????????????????Textarea???id???name, Div???id\r\n//       o_Config : ??????????????????????????????????????????????????????????????????????????? style=coolblue,width=100%,height=350\r\n\r\n\r\n//???????????????\r\nfunction RemoveEditor(){\r\n\tif (!EWEBEDITOR.Instances[\"content1\"]){return;}\r\n\r\n\tEWEBEDITOR.Instances[\"content1\"].Remove();\r\n\tEWEBEDITOR.Instances[\"div1\"].Remove();\r\n}\r\n\r\n//???????????????\r\nfunction ReplaceEditor(){\r\n\tif (EWEBEDITOR.Instances[\"content1\"]){return;}\r\n\r\n\tEWEBEDITOR.Replace(\"content1\", {style:\"coolblue\",width:\"550\",height:\"300\"} );\r\n\tEWEBEDITOR.Replace(\"div1\");\r\n}\r\n</script>\r\n\r\n</head>\r\n<body>\r\n\r\n<p><b>?????? ??? <a href=\"default.jsp\">????????????</a> &gt; ???Javascript???????????????DIV??????????????????</b></p>\r\n<p>????????????????????????Javascript????????? &lt;TEXTAREA&gt; ??? &lt;INPUT&gt; ??? &lt;DIV&gt; ????????? eWebEditor ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????</p>\r\n");
      out.write("<div class=code>EWEBEDITOR.Replace(&quot;TextareaOrInputOrDiv_id&quot;);</div>\r\n<p>?????????????????????????????????????????????????????????????????????????????????????????????????????????&ltTEXTAREA&gt;????????????&lt;DIV&gt;??????????????????Javascript???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????</p>\r\n\r\n<p>\r\n\t<input type=\"button\" value=\"???????????????\" onclick=\"RemoveEditor()\">\r\n\t<input type=\"button\" value=\"???????????????\" onclick=\"ReplaceEditor()\">\r\n</p>\r\n\r\n<p><b>??????1???</b>??????&lt;TEXTAREA&gt;????????????????????????(style=coolblue,width=550,height=300)</p>\r\n<textarea cols=\"80\" name=\"content1\" rows=\"10\">&lt;p&gt;??????&lt;strong&gt;??????TEXTAREA&lt;/strong&gt;??? ??????????????? &lt;a href=&quot;http://www.ewebeditor.net/&quot;&gt;eWebEditor&lt;/a&gt;.&lt;/p&gt;</textarea>\r\n<script type=\"text/javascript\">\r\n\t// ??????????????? window.onload ???????????????\r\n\t// ?????? <textarea id=\"content1\"> ??? <textarea name=\"content1\"> ??????????????????\r\n\tEWEBEDITOR.Replace(\"content1\", {style:\"coolblue\",width:\"550\",height:\"300\"} );\r\n</script>\r\n\r\n<p><b>??????2???</b>??????&lt;DIV&gt;?????????????????????(style=coolblue,width=100%,height=350)</p>\r\n<div id=\"div1\" rows=\"10\"><p>??????<strong>??????DIV</strong>??? ??????????????? <a href=\"http://www.ewebeditor.net/\">eWebEditor</a>.</p></div>\r\n");
      out.write("<script type=\"text/javascript\">\r\n\t// ??????????????? window.onload ???????????????\r\n\t// ?????? <div id=\"div1\"> ??????????????????\r\n\tEWEBEDITOR.Replace(\"div1\");\r\n</script>\r\n\r\n</body>\r\n</html>");
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
