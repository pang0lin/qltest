/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:53:35 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.message.select_005fuser;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class select_005forganduser_jsp extends org.apache.jasper.runtime.HttpJspBase
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


  String mobilephone=request.getParameter("mobilephone");

  String selectedId=request.getParameter("selectedId");
  String selectedName=request.getParameter("selectedName").toString();
  String allowId=request.getParameter("allowId");
  String allowName=request.getParameter("allowName");
  String selectedPhone=request.getParameter("selectedPhone");
  //selectedName = new String(selectedName.getBytes("iso-8859-1"));
  String email=request.getParameter("email");
  String select=request.getParameter("select"); //选择的类型 用户还是组织
  String single=request.getParameter("single"); //单选还是多选
  String range=request.getParameter("range");   //选择的范围
  if(request.getParameter("limited")==null){
      range="*0*";
  }
  String show=request.getParameter("show");   //要显示的选项 组织,用户,组中的任意组合
  String frameSrc;
  String currentOrg="";
  if("org".equals(select)){//user
      frameSrc="../select_user/select_org.jsp";
      currentOrg=request.getParameter("currentOrg")==null?"":request.getParameter("currentOrg");
  }else{
      if(show.indexOf("org")<0 || "yes".equals(single)){
          frameSrc="../select_user/select_useronly.jsp";
      }else{
		  //out.println("====================111111111==================");
          frameSrc="../select_user/select_user.jsp";
      }
  }

  String tmphref = "select_org.jsp";
  if("privatePerson".equals(show) || "publicPerson".equals(show)){
	tmphref = ((window.location.href).substring(0,(window.location.href).indexOf("/jsoa")))+"/jsoa/SelectOrganizationMsg.do";
  }

      out.write("\r\n\r\n<html>\r\n<head>\r\n<title>选择对象</title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n</head>\r\n\r\n<frameset rows=\"56,*\" cols=\"*\" frameborder=\"NO\" border=\"0\" framespacing=\"0\">\r\n  <frame src=\"search_user.jsp?select=");
      out.print(select);
      out.write("&single=");
      out.print(single);
      out.write("&show=");
      out.print(show);
      out.write("\" name=\"topFrame\" id=\"topFrame\" scrolling=\"NO\" noresize>\r\n  <frameset cols=\"180,*\" border=0 frameSpacing=0 rows=* frameBorder=no>\r\n    <frame src=\"");
      out.print(tmphref);
      out.write("?frame=");
      out.print(frameSrc);
      out.write("&select=");
      out.print(select);
      out.write("&single=");
      out.print(single);
      out.write("&show=");
      out.print(show);
      out.write("&range=");
      out.print(range);
      out.write("&currentOrg=");
      out.print(currentOrg);
      out.write("&email=");
      out.print(email);
      out.write("\" name=\"leftFrame\" frameBorder=no scrolling=\"auto\" noResize>\r\n\t<frameset rows=\"270,*\" cols=\"*\" frameborder=\"NO\" border=\"0\" framespacing=\"0\">\r\n\t    <frame src=\"");
      out.print(frameSrc);
      out.write("?select=");
      out.print(select);
      out.write("&single=");
      out.print(single);
      out.write("&show=");
      out.print(show);
      out.write("\" name=\"mainFrame\" frameBorder=no scrolling=\"auto\" >\r\n\t\t<frame src=\"select_affirm.jsp?selectedId=");
      out.print(selectedId);
      out.write("&selectedName=");
      out.print(selectedName);
      out.write("&selectedPhone=");
      out.print(selectedPhone);
      out.write("&allowId=");
      out.print(allowId);
      out.write("&allowName=");
      out.print(allowName);
      out.write("&mobilephone=");
      out.print(mobilephone);
      out.write("&email=");
      out.print(email);
      out.write("\" name=\"bottomFrame\" frameBorder=no scrolling=\"auto\">\r\n\t</frameset>\r\n  </frameset>\r\n</frameset>\r\n<noframes><body>\r\n\r\n</body></noframes>\r\n</html>\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n");
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
