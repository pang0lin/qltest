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

public final class test2_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html;charset=gb2312");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n<HTML>\r\n<HEAD>\r\n<TITLE>eWebEditor在线编辑器 - 使用例子</TITLE>\r\n<META http-equiv=Content-Type content=\"text/html; charset=gb2312\">\r\n<style>\r\nbody,td,input,textarea {font-size:9pt}\r\n</style>\r\n</HEAD>\r\n<BODY>\r\n\r\n\r\n\r\n<script language=JavaScript>\r\n\r\n// 表单提交检测\r\nfunction doCheck(){\r\n\r\n\t// 检测表单的有效性\r\n\t// 如：标题不能为空，内容不能为空，等等....\r\n\tif (eWebEditor1.getHTML()==\"\") {\r\n\t\talert(\"内容不能为空！\");\r\n\t\treturn false;\r\n\t}\r\n\r\n\t// 表单有效性检测完后，自动上传远程文件\r\n\t// 函数：remoteUpload(strEventUploadAfter)\r\n\t// 参数：strEventUploadAfter ; 上传完后，触发的函数名，如果上传完后不需动作可不填参数\r\n\teWebEditor1.remoteUpload(\"doSubmit()\");\r\n\treturn false;\r\n\r\n}\r\n\r\n// 表单提交（当远程上传完成后，触发此函数）\r\nfunction doSubmit(){\r\n\tdocument.myform1.submit();\r\n}\r\n\r\n</script>\r\n\r\n\r\n\r\n<p><b>eWebEditor 远程文件上传示例：</b></p>\r\n\r\n<FORM method=\"POST\" name=\"myform1\" action=\"submit.jsp\" onsubmit=\"return doCheck();\">\r\n<TABLE border=\"0\" cellpadding=\"2\" cellspacing=\"1\">\r\n<TR>\r\n\t<TD>编辑内容：</TD>\r\n\t<TD>\r\n\t\t<INPUT type=\"hidden\" name=\"content1\" value=\"&lt;P&gt;&lt;IMG src=&quot;http://ewebeditor.webasp.net/images/ewebeditor.gif&quot; border=0&gt;&lt;/P&gt;&lt;P&gt;以上图片地址为：&lt;A href=&quot;http://ewebeditor.webasp.net/images/ewebeditor.gif&quot;&gt;http://ewebeditor.webasp.net/images/ewebeditor.gif&lt;/A&gt;&lt;/P&gt;&lt;P&gt;1。点击&lt;IMG src=&quot;http://ewebeditor.webasp.net/ewebeditor/buttonimage/standard/remoteupload.gif&quot; border=0&gt;按钮，然后转到“代码”状态看一下，以上图片的地址已经到本地服务器了；&lt;/P&gt;&lt;P&gt;2。或点此表单的“提交”，提交后查看源文件看一下，图片的地址也到本地服务器了；&lt;/P&gt;&lt;P&gt;要看其使用说明，请见压缩包中，_example/test2.jsp 中的注释。&lt;/P&gt;\">\r\n");
      out.write("\t\t<IFRAME ID=\"eWebEditor1\" src=\"../ewebeditor.htm?id=content1&style=full\" frameborder=\"0\" scrolling=\"no\" width=\"550\" height=\"400\"></IFRAME>\r\n\t</TD>\r\n</TR>\r\n<TR>\r\n\t<TD colspan=2 align=right>\r\n\t<INPUT type=submit name=b1 value=\"提交\"> \r\n\t<INPUT type=reset name=b2 value=\"重填\"> \r\n\t<INPUT type=button name=b3 value=\"查看源文件\" onclick=\"location.replace('view-source:'+location)\"> \r\n\t</TD>\r\n</TR>\r\n</TABLE>\r\n</FORM>\r\n\r\n\r\n\r\n\r\n\r\n\r\n</BODY>\r\n</HTML>\r\n");
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
