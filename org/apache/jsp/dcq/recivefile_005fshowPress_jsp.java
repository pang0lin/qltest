/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:44:33 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.dcq;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.oa.dcq.bean.BusinessShowBean;
import java.util.*;

public final class recivefile_005fshowPress_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.oa.dcq.bean.BusinessShowBean");
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

      out.write("\r\n\r\n");

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String fileId = request.getParameter("fileId");
String fileType = request.getParameter("fileType");
if(null==fileId || "".equals(fileId)){
	
      out.write("\r\n\t<script type=\"text/javascript\">\r\n\talert(\"参数信息错误。\");\r\n\twindow.close();\r\n\t</script>\r\n\t");

}

List<Object[]> list = new BusinessShowBean().findPressByFileId(fileId, fileType);

      out.write("\r\n<!-- 收文催办信息 -->\r\n<!DOCTYPE HTML>\r\n<html>\r\n\t<head>\r\n\t    <base href=\"");
      out.print(basePath);
      out.write("\">\r\n\t    <title>催办信息</title>\r\n\t\t<meta http-equiv=\"pragma\" content=\"no-cache\">\r\n\t\t<meta http-equiv=\"cache-control\" content=\"no-cache\">\r\n\t\t<meta http-equiv=\"expires\" content=\"0\">    \r\n\t\t<link href=\"skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t</head>\r\n  \r\n\t<body>\r\n    \t");

    	if(null!=list && list.size()>0){
    		Object[] obj = null;
    		
      out.write("\r\n    \t\t<div style=\"width: 90%; margin: 10px auto;\">\r\n    \t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"listTable outTopline\">\r\n    \t\t\t\t<tr>\r\n    \t\t\t\t\t<td class=\"listTableHead\">序号</td>\r\n    \t\t\t\t\t<td class=\"listTableHead\">标题</td>\r\n    \t\t\t\t\t<td class=\"listTableHead\">时间</td>\r\n    \t\t\t\t\t<td class=\"listTableHead\">内容</td>\r\n    \t\t\t\t</tr>\r\n    \t\t\t\t");

    				String listClass = "listTableLine2";
    				for(int i=0; i<list.size(); i++){
		    			obj = list.get(i);
		    			if(i%2 == 0){
							listClass = "listTableLine1";
						} else{
							listClass = "listTableLine2";
						}
		    			
      out.write("\r\n\t\t    \t\t\t<tr>\r\n\t\t    \t\t\t\t<td class=\"");
      out.print(listClass );
      out.write('"');
      out.write('>');
      out.print((i+1) );
      out.write("</td>\r\n\t\t    \t\t\t\t<td class=\"");
      out.print(listClass );
      out.write('"');
      out.write('>');
      out.print(obj[0] );
      out.write("</td>\r\n\t\t    \t\t\t\t<td class=\"");
      out.print(listClass );
      out.write('"');
      out.write('>');
      out.print(obj[1] );
      out.write("</td>\r\n\t\t    \t\t\t\t<td class=\"");
      out.print(listClass );
      out.write('"');
      out.write('>');
      out.print(obj[2] );
      out.write("</td>\r\n\t\t    \t\t\t</tr>\r\n\t\t    \t\t\t");

		    		}
    				 
      out.write("\r\n    \t\t\t\t<tr>\r\n\t\t\t\t\t\t<td colspan=\"4\" align=\"right\">\r\n\t\t\t\t\t\t\t<input type=\"button\" class=\"btnButton2Font\" value=\"关闭\" onclick=\"javascript:window.close();\">\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t</tr>\r\n    \t\t\t</table>\r\n    \t\t</div>\r\n    \t\t");

    	} else{
    		
      out.write("\r\n    \t\t<script type=\"text/javascript\">\r\n    \t\talert(\"无催办消息。\");\r\n    \t\twindow.close();\r\n    \t\t</script>\r\n    \t\t");

    	}
    	 
      out.write("\r\n\t</body>\r\n</html>");
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
