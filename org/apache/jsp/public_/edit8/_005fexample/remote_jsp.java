/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:57:29 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.public_.edit8._005fexample;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class remote_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n<HTML>\r\n<HEAD>\r\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=EmulateIE7\">\r\n<TITLE>eWebEditor ： 远程文件自动上传示例</TITLE>\r\n<META http-equiv=Content-Type content=\"text/html; charset=GBK\">\r\n<link rel='stylesheet' type='text/css' href='example.css'>\r\n</HEAD>\r\n<BODY>\r\n\r\n<p><b>导航 ： <a href=\"default.jsp\">示例首页</a> &gt; 远程文件自动上传示例</b></p>\r\n<p>演示操作说明：</p>\r\n<ul>\r\n<li>编辑区中的图片地址为：http://www.ewebeditor.net/images/ewebeditor.gif\r\n<li>点击按钮<img src=\"images/remoteupload.gif\">，然后转到“代码”模式看一下，编辑区的图片的地址已经到本地服务器了。\r\n<li>或点此表单的“提交”，提交后，用IE的“查看源文件”看一下，图片的地址也到本地服务器了。\r\n<li>到eWebEditor所有的目录下的uploadfile目录中，查看一下，是不是多了一个图片文件，这个文件就是远程自动获取的。\r\n</ul>\r\n\r\n\r\n<script language=javascript>\r\n// 表单提交检测\r\nfunction doCheck(){\r\n\t// 取编辑器对象，然后可以调用对象接口\r\n\tvar editor1 = document.getElementById(\"eWebEditor1\").contentWindow;\r\n\r\n\r\n\t// 检测表单的有效性\r\n\t// 如：标题不能为空，内容不能为空，等等...\r\n\tif (editor1.getHTML()==\"\") {\r\n\t\talert(\"内容不能为空！\");\r\n\t\treturn false;\r\n\t}\r\n\r\n\t// 表单有效性检测完后，自动上传远程文件\r\n\t// 函数： remoteUpload(strEventUploadAfter)\r\n\t// 参数：strEventUploadAfter ; 上传完后，触发的函数名，如果上传完后不需动作可不填参数\r\n");
      out.write("\teditor1.remoteUpload(\"doSubmit()\");\r\n\treturn false;\r\n\r\n}\r\n\r\n// 表单提交（当远程上传完成后，触发此函数）\r\nfunction doSubmit(){\r\n\tdocument.myform.submit();\r\n}\r\n</script>\r\n\r\n\r\n<FORM method=\"post\" name=\"myform\" action=\"retrieve.jsp\" onsubmit=\"return doCheck();\">\r\n<TABLE border=\"0\" cellpadding=\"2\" cellspacing=\"1\">\r\n<TR>\r\n\t<TD>编辑内容：</TD>\r\n\t<TD>\r\n\t\t<INPUT type=\"hidden\" name=\"content1\" value=\"&lt;IMG src=&quot;http://www.ewebeditor.net/images/ewebeditor.gif&quot;&gt;\">\r\n\t\t<IFRAME ID=\"eWebEditor1\" src=\"../ewebeditor.htm?id=content1&style=coolblue\" frameborder=\"0\" scrolling=\"no\" width=\"550\" height=\"350\"></IFRAME>\r\n\t</TD>\r\n</TR>\r\n<TR>\r\n\t<TD colspan=2 align=right>\r\n\t<INPUT type=submit value=\"提交\"> \r\n\t<INPUT type=reset value=\"重填\"> \r\n\t<INPUT type=button value=\"查看源文件\" onclick=\"location.replace('view-source:'+location)\"> \r\n\t</TD>\r\n</TR>\r\n</TABLE>\r\n</FORM>\r\n\r\n\r\n\r\n</BODY>\r\n</HTML>\r\n");
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