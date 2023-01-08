/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:57:07 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.public_.edit._005fexample;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class clientapi_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n<HTML>\r\n<HEAD>\r\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=EmulateIE7\">\r\n<TITLE>eWebEditor ： 客户端API示例</TITLE>\r\n<META http-equiv=Content-Type content=\"text/html; charset=GBK\">\r\n<link rel='stylesheet' type='text/css' href='example.css'>\r\n</HEAD>\r\n<BODY>\r\n\r\n<p><b>导航 ： <a href=\"default.jsp\">示例首页</a> &gt; 客户端API示例</b></p>\r\n<p>您可以用eWebEditor提供的客户端API，对编辑器执行高级操作。更多API，请参见开发手册。</p>\r\n\r\n\r\n\r\n<FORM method=\"post\" name=\"myform\" action=\"retrieve.jsp\">\r\n<TABLE border=\"0\" cellpadding=\"2\" cellspacing=\"1\">\r\n<TR>\r\n\t<TD>编辑内容：</TD>\r\n\t<TD>\r\n\t\t<INPUT type=\"hidden\" name=\"content1\" value=\"&lt;p&gt;eWebEditor - 在线HTML编辑器，HTML在线编辑好帮手&lt;/p&gt;\">\r\n\t\t<IFRAME ID=\"eWebEditor1\" src=\"../ewebeditor.htm?id=content1&style=coolblue\" frameborder=\"0\" scrolling=\"no\" width=\"550\" height=\"350\"></IFRAME>\r\n\t</TD>\r\n</TR>\r\n<TR>\r\n\t<TD colspan=2 align=right>\r\n\t<INPUT type=submit value=\"提交\"> \r\n\t<INPUT type=reset value=\"重填\"> \r\n\t<INPUT type=button value=\"查看源文件\" onclick=\"location.replace('view-source:'+location)\"> \r\n\t</TD>\r\n</TR>\r\n<TR>\r\n\t<TD>HTML代码：</TD>\r\n");
      out.write("\t<TD><TEXTAREA cols=50 rows=5 id=myTextArea style=\"width:550px\">点击“取值”按钮，看一下效果！</TEXTAREA></TD>\r\n</TR>\r\n<TR>\r\n\t<TD colspan=2 align=right>\r\n\r\n<script type=\"text/javascript\">\r\nfunction DoAPI(s_Flag){\r\n\tvar o_Editor = document.getElementById(\"eWebEditor1\").contentWindow;\r\n\tvar o_Text = document.getElementById(\"myTextArea\");\r\n\tswitch(s_Flag){\r\n\tcase \"gethtml\":\r\n\t\to_Text.value = o_Editor.getHTML();\r\n\t\tbreak;\r\n\tcase \"gettext\":\r\n\t\to_Text.value = o_Editor.getText();\r\n\t\tbreak;\r\n\tcase \"sethtml\":\r\n\t\to_Editor.setHTML(\"<b>Hello My World!</b>\");\r\n\t\tbreak;\r\n\tcase \"inserthtml\":\r\n\t\to_Editor.insertHTML(\"This is insertHTML function!\");\r\n\t\tbreak;\r\n\tcase \"appendhtml\":\r\n\t\to_Editor.appendHTML(\"This is appendHTML function!\");\r\n\t\tbreak;\r\n\tcase \"code\":\r\n\t\to_Editor.setMode(\"CODE\");\r\n\t\tbreak;\r\n\tcase \"edit\":\r\n\t\to_Editor.setMode(\"EDIT\");\r\n\t\tbreak;\r\n\tcase \"text\":\r\n\t\to_Editor.setMode(\"TEXT\");\r\n\t\tbreak;\r\n\tcase \"view\":\r\n\t\to_Editor.setMode(\"VIEW\");\r\n\t\tbreak;\r\n\tcase \"getcount0\":\r\n\t\talert(o_Editor.getCount(0));\r\n\t\tbreak;\r\n\tcase \"getcount1\":\r\n\t\talert(o_Editor.getCount(1));\r\n");
      out.write("\t\tbreak;\r\n\tcase \"getcount2\":\r\n\t\talert(o_Editor.getCount(2));\r\n\t\tbreak;\r\n\tcase \"getcount3\":\r\n\t\talert(o_Editor.getCount(3));\r\n\t\tbreak;\r\n\tcase \"readonly1\":\r\n\t\to_Editor.setReadOnly(\"1\");\r\n\t\tbreak;\r\n\tcase \"readonly2\":\r\n\t\to_Editor.setReadOnly(\"2\");\r\n\t\tbreak;\r\n\tcase \"readonly0\":\r\n\t\to_Editor.setReadOnly(\"\");\r\n\t\tbreak;\r\n\r\n\t}\r\n\r\n}\r\n\r\n</script>\r\n\r\n\t\t<INPUT type=button value=\"取值(HTML)\" onclick=\"DoAPI('gethtml')\" class=btn> \r\n\t\t<INPUT type=button value=\"取值(纯文本)\" onclick=\"DoAPI('gettext')\" class=btn> \r\n\t\t<INPUT type=button value=\"赋值\" onclick=\"DoAPI('sethtml')\" class=btn>\r\n\t\t<INPUT type=button value=\"当前位置插入\" onclick=\"DoAPI('inserthtml')\" class=btn>\r\n\t\t<INPUT type=button value=\"尾部追加\" onclick=\"DoAPI('appendhtml')\" class=btn>\r\n\t\t<br>\r\n\t\t<INPUT type=button value=\"代码状态\" onclick=\"DoAPI('code')\" class=btn>\r\n\t\t<INPUT type=button value=\"设计状态\" onclick=\"DoAPI('edit')\" class=btn>\r\n\t\t<INPUT type=button value=\"文本状态\" onclick=\"DoAPI('text')\" class=btn>\r\n\t\t<INPUT type=button value=\"预览状态\" onclick=\"DoAPI('view')\" class=btn>\r\n\t\t<br>\r\n\t\t<INPUT type=button value=\"英文字数\" onclick=\"DoAPI('getcount0')\" class=btn>\r\n");
      out.write("\t\t<INPUT type=button value=\"中文字数\" onclick=\"DoAPI('getcount1')\" class=btn>\r\n\t\t<INPUT type=button value=\"中英文字数(中文加1)\" onclick=\"DoAPI('getcount2')\" class=btn>\r\n\t\t<INPUT type=button value=\"中英文字数(中文加2)\" onclick=\"DoAPI('getcount3')\" class=btn>\t\r\n\t\t<br>\r\n\t\t<INPUT type=button value=\"只读[模式1]\" onclick=\"DoAPI('readonly1')\" class=btn>\r\n\t\t<INPUT type=button value=\"只读[模式2]\" onclick=\"DoAPI('readonly2')\" class=btn>\r\n\t\t<INPUT type=button value=\"取消只读\" onclick=\"DoAPI('readonly0')\" class=btn>\r\n\r\n\t</TD>\r\n</TR>\r\n</TABLE>\r\n</FORM>\r\n\r\n\r\n</BODY>\r\n</HTML>");
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