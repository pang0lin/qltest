/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:57:15 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.public_.edit8.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class menu_jsp extends org.apache.jasper.runtime.HttpJspBase
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
request.setCharacterEncoding("GBK");
      out.write('\r');
      out.write('\n');
      ewebeditor.admin.default_jsp eWebEditor = null;
      eWebEditor = (ewebeditor.admin.default_jsp) _jspx_page_context.getAttribute("eWebEditor", javax.servlet.jsp.PageContext.PAGE_SCOPE);
      if (eWebEditor == null){
        eWebEditor = new ewebeditor.admin.default_jsp();
        _jspx_page_context.setAttribute("eWebEditor", eWebEditor, javax.servlet.jsp.PageContext.PAGE_SCOPE);
      }
      out.write('\r');
      out.write('\n');

eWebEditor.Load(pageContext);

      out.write("\r\n\r\n<html>\r\n<head>\r\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=EmulateIE7\">\r\n<title>eWebEditor</title>\r\n<meta http-equiv=Content-Type content=\"text/html; charset=GBK\">\r\n<link type=text/css href='private.css' rel=stylesheet>\r\n<base target=main>\r\n</head>\r\n<script language=javascript>\r\n<!--\r\nfunction menu_tree(meval)\r\n{\r\n  var left_n=eval(meval);\r\n  if (left_n.style.display==\"none\")\r\n  { eval(meval+\".style.display='';\"); }\r\n  else\r\n  { eval(meval+\".style.display='none';\"); }\r\n}\r\n-->\r\n</script>\r\n<body>\r\n<center>\r\n\r\n  <table cellspacing=0  class=\"Menu\">\r\n  <tr><th align=center  onclick=\"javascript:menu_tree('left_1');\" >≡ 首选服务 ≡</th></tr>\r\n  <tr id='left_1'><td >\r\n    <table width='100%'>\r\n    <tr><td><img border=0 src='images/menu.gif' align=absmiddle>&nbsp;<a href='style.jsp'>样式管理</a></td></tr>\r\n    <tr><td><img border=0 src='images/menu.gif' align=absmiddle>&nbsp;<a href='upload.jsp'>上传管理</a></td></tr>\r\n    </table>\r\n  </td></tr>\r\n  </table>\r\n\r\n  <table width='90%' height=2><tr ><td></td></tr></table>\r\n  <table cellspacing=0  class=\"Menu\">\r\n");
      out.write("  <tr><th align=center  onclick=\"javascript:menu_tree('left_2');\" >≡ 辅助服务 ≡</th></tr>\r\n  <tr id='left_2'><td>\r\n    <table width='100%'>\r\n    <tr><td><img border=0 src='images/menu.gif' align=absmiddle>&nbsp;<a href='main.jsp'>后台首页</a></td></tr>\r\n\t<tr><td><img border=0 src='images/menu.gif' align=absmiddle>&nbsp;<a href='../_example/default.jsp' target='_blank'>示例首页</a></td></tr>\r\n    <tr><td><img border=0 src='images/menu.gif' align=absmiddle>&nbsp;<a href='modipwd.jsp'>修改密码</a></td></tr>\r\n\t\r\n    <tr><td><img border=0 src='images/menu.gif' align=absmiddle>&nbsp;<a onclick=\"return confirm('提示：您确定要退出系统吗？')\" href='login.jsp?action=out' target='_parent'>退出后台</a></td></tr>\r\n    </table>\r\n  </td></tr>\r\n  </table>\r\n  \r\n  <table width='90%' height=2><tr ><td></td></tr></table>\r\n  <table cellspacing=0  class=\"Menu\">\r\n  <tr><th align=center  >〓 版本信息 〓</th></tr>\r\n  <tr><td align=center>eWebEditor V9.5<br>(SP3)</td></tr>\r\n  <tr><td align=center><a href='http://www.ewebeditor.net' target=_blank><b>在线帮助</b></a></td></tr>\r\n  </table>\r\n");
      out.write("\r\n</center>\r\n</body>\r\n</html>");
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
