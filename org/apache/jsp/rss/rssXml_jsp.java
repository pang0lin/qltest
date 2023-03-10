/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:49:27 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.rss;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class rssXml_jsp extends org.apache.jasper.runtime.HttpJspBase
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

response.setContentType("text/xml;charset=utf-8");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
String basePath = request.getScheme()+"://"+request.getServerName()+(request.getServerPort()==80?"":":"+request.getServerPort())+request.getContextPath()+"/";
List<Map<String,String>> rssList = (List<Map<String,String>>)request.getAttribute("rssList");
Map<String, String> rssHeader = (Map<String, String>)request.getAttribute("rssHeader");

      out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n<rss version=\"2.0\">\r\n\t<channel>\r\n\t\t<title>");
      out.print(rssHeader.get("title")==null?"":rssHeader.get("title") );
      out.write("</title>\r\n    \t<description>");
      out.print(rssHeader.get("description")==null?"":rssHeader.get("description") );
      out.write("</description>\r\n    \t<link>");
      out.print(rssHeader.get("link")==null?"":rssHeader.get("link") );
      out.write("</link>\r\n    \t<copyright>");
      out.print(rssHeader.get("copyright")==null?"":rssHeader.get("copyright") );
      out.write("</copyright>\r\n    \t<language>zh-cn</language>\r\n    \t<generator>");
      out.print(basePath );
      out.write("</generator>\r\n    \r\n\t");
for(int i=0;i<rssList.size();i++){
		Map<String,String> rss = rssList.get(i);
      out.write("\r\n\t\t<item>\r\n\t\t\t<title>");
      out.print(rss.get("title")==null?"":rss.get("title") );
      out.write("</title>\r\n\t\t\t<link>");
      out.print(rss.get("link")==null?"":rss.get("link") );
      out.write("</link>\r\n\t\t\t<author>");
      out.print(rss.get("author")==null?"":rss.get("author") );
      out.write("</author>\r\n\t\t\t");
if(rss.get("category")==null){ 
      out.write("<category/>");
}else{ 
      out.write("<category>");
      out.print(rss.get("category") );
      out.write("</category>");
} 
      out.write("\r\n\t\t\t<pubDate>");
      out.print(rss.get("pubDate")==null?"":rss.get("pubDate") );
      out.write("</pubDate>\r\n\t\t\t");
if(rss.get("comments")==null){ 
      out.write("<comments/>");
}else{ 
      out.write("<comments>");
      out.print(rss.get("comments") );
      out.write("</comments>");
} 
      out.write("\r\n\t\t\t<description>");
      out.print(rss.get("description")==null?"":rss.get("description") );
      out.write("</description>\r\n\t    </item>\r\n\t");
}
      out.write("\r\n\t</channel>\r\n</rss>");
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
