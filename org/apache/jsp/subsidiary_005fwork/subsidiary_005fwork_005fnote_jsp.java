/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:50:04 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.subsidiary_005fwork;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.util.util.BrowserJudge;

public final class subsidiary_005fwork_005fnote_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("/public/jsp/online.jsp", Long.valueOf(1499751452000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-bean.tld", Long.valueOf(1499751390000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.util.util.BrowserJudge");
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

      out.write("\r\n\r\n\r\n");
      out.write("\r\n<script language=\"JScript.Encode\" src=\"/jsoa/js/browinfo.js\"></script>\r\n<script language=\"JScript.Encode\" src=\"/jsoa/js/rtxint.js\"></script>\r\n<script language=\"javascript\">\r\n\r\nfunction rtxonline(a){\r\n\t RAP(a);\r\n}\r\n\r\n</script>");
      out.write('\r');
      out.write('\n');

boolean bflag=false;
if(BrowserJudge.isMSIE(request)){
bflag=true;
}
 
      out.write("\r\n<html>\r\n<style type=\"text/css\">\r\n.Hidden{display:none;}\r\n.Show{display:;}\r\n body{ font-size:12px;}\r\n.border-top{border-top:1px solid #b1c8d7;}\r\n.td{background-image:url(/jsoa/images/left_cent.gif); color:#2c4a77; font-weight:bold;}\r\n.table{border:1px solid #b1c8d7;width:100%;}\r\n.table2{border-left:1px solid #b1c8d7;border-right:1px solid #b1c8d7;border-bottom:1px solid #b1c8d7;}\r\n</style>\r\n\t<head lang=\"javascrpt\">\r\n\t<link rel=stylesheet type=\"text/css\" href=\"/jsoa/style/cssmain.css\"> \r\n\t");
if(bflag){ 
      out.write("<SCRIPT language=javascript src=\"/jsoa/subsidiary_work/subsidiary_work_tree.js\"></SCRIPT>");
}else{ 
      out.write("\r\n<SCRIPT language=javascript src=\"/jsoa/subsidiary_work/subsidiary_work_tree_o.js\"></SCRIPT>");
} 
      out.write('\r');
      out.write('\n');

String questionnaireId=(String)request.getAttribute("questionnaireId");
String select=request.getParameter("select");
String single=request.getParameter("single");
String show=request.getParameter("show");
String currentOrg=request.getParameter("currentOrg");
String fromParent=request.getParameter("fromParent");
String countPerson = request.getParameter("countPerson");

 
      out.write("\t\t<title>问卷答题人员统计</title>\r\n</head>\r\n<body  style=\"padding:10px;\">\r\n    <table height=\"100%\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n\t<tr height=\"100%\">\r\n\t\t<td width=\"300\" height=\"100%\">\r\n\t\t\t<table height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"table\">\r\n\t\t\t  <tr height=\"19\">\r\n\t\t\t    <td width=\"19\"><img src=\"/jsoa/images/left_top.gif\"/></td>\r\n\t\t\t    <td class=\"td\">参与问卷答题统计</td>\r\n\t\t\t  </tr>\r\n\t\t\t  <tr>\r\n\t\t\t    <td class=\"border-top\">&nbsp;</td>\r\n\t\t\t    <td class=\"border-top\">\r\n\t\t    \t <div id=\"org_list\" style=\"float:left;height:100%\" >\r\n\t\t\t\t   <SCRIPT LANGUAGE=\"JavaScript\">\r\n\t\t\t\t    loadXML('");
      out.print(select);
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(single);
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(show);
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(currentOrg);
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(session.getAttribute("browseRange"));
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(fromParent);
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(countPerson);
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(questionnaireId);
      out.write("');\r\n\t\t\t\t   </SCRIPT>\r\n\t\t\t\t  </div>\r\n\t\t\t\t</td>\r\n\t\t\t  </tr>\r\n\t\t\t</table>\r\n\t\t</td>\r\n\t\t<td width=\"5px\">&nbsp;</td>\r\n\t\t<td height=\"100%\">\r\n\t\t\t<table height=\"100%\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"table\" >\r\n\t\t\t\t<tr>\r\n\t\t\t\t\t<td>\r\n\t\t\t\t\t  <div id=\"waitBar\" style=\"width:100%;height:100%;display:none;\">\r\n\t\t\t\t\t  \t<img src=\"/jsoa/images/wait.gif\"/>正在加载数据...\r\n\t\t\t\t\t  </div>\r\n\t\t\t\t\t  <div id=\"user\" style=\"width:100%;height:100%\" >\r\n\t\t\t\t      <iframe  id=\"browserPage\" name=\"browserPage\" frameborder=\"0\" border=\"0\" width=\"100%\" height=\"100%\" scrolling=\"yes\"></iframe>\r\n\t\t\t\t      </div>\r\n\t\t\t      \t</td>\r\n\t\t\t    </tr>\r\n\t\t\t</table>\r\n\t\t</td>\r\n\t</tr>\r\n</table>\t  \r\n</body>\r\n<script >\r\nfunction finduser(a,b)\r\n {\r\n    window.open(\"/jsoa/QuestionnaireAction.do?action=browser&questionnaireId=\"+a+\"&orgId=\"+b,\"browserPage\",\"scrollbars=yes,resizable=yes\");\r\n }\r\n</script>\r\n</html>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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