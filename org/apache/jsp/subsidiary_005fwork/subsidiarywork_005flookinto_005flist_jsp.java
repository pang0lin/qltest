/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:50:16 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.subsidiary_005fwork;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.List;

public final class subsidiarywork_005flookinto_005flist_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.util.List");
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

      out.write("\r\n\r\n\r\n<html>\r\n");

List list=(List)request.getAttribute("voteList");
String surveyId = request.getParameter("surveyId");
String[][] item = (String[][])request.getAttribute("item");

      out.write("\r\n<head>\r\n<STYLE type=text/css>TD {\r\n\tFONT-FAMILY: 宋体; FONT-SIZE: 9pt\r\n}\r\nBODY {\r\n\tFONT-FAMILY: 宋体; FONT-SIZE: 9pt\r\n}\r\nSELECT {\r\n\tFONT-FAMILY: 宋体; FONT-SIZE: 9pt\r\n}\r\nA {\r\n\tCOLOR: #990000; FONT-FAMILY: 宋体; FONT-SIZE: 9pt; TEXT-DECORATION: none\r\n}\r\nA:hover {\r\n\tCOLOR: #ffcc99; FONT-FAMILY: 宋体; FONT-SIZE: 9pt; TEXT-DECORATION: underline\r\n}\r\n</STYLE>\r\n<title>投票查看</title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\r\n<link href=\"skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"js/js.js\" language=\"javascript\"></script>\r\n</head>\r\n<script language=\"JavaScript\">\r\n<!--\r\nfunction MM_openBrWindow(theURL,winName,features) { //v2.0\r\n  JSMainWinOpen(theURL,winName,features);\r\n}\r\n//-->\r\n</script>\r\n<body   class=\"MainFrameBox\" onLoad=\"resizeWin(800,600);\">\r\n\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td>\r\n\r\n<table width=\"95%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"td\">\r\n  <tr>\r\n    <td  height=\"31\">\r\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n        <tr>\r\n          <td align=\"right\">&nbsp;</td>\r\n        </tr>\r\n      </table>\r\n    </td>\r\n  </tr>\r\n  <tr>\r\n    <td height=\"45\">\r\n\r\n\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n\t\t<tr>\r\n          <td valign=\"top\" align=\"center\">\r\n            <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\">\r\n              <tr>\r\n                <td>\r\n                  <div align=\"center\" style=\"font-size:18px\"><b>");
      out.print(list.get(0));
      out.write("</b></div>\r\n                </td>\r\n              </tr>\r\n\t\t\t  <tr>\r\n\t\t\t  <td>&nbsp;</td>\r\n\t\t\t  </tr>\r\n              <tr>\r\n                <td> <a href=\"javascript:viewBrowser(1);\">已投票人员</a> <a href=\"javascript:viewBrowser(0);\">未投票人员</a> 目前共有 <font color=\"#FF0000\">");
      out.print(list.get(2));
      out.write("  张选票 &nbsp;&nbsp;<font color=\"#FF0000\">");
      out.print(list.get(list.size()-1));
      out.write(" 人参加投票</td>\r\n              </tr>\r\n            </table>\r\n            <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"listTable outTopline\">\r\n              <tr>\r\n                <td class=\"listTableHead\" width=\"30%\">\r\n\t\t\t\t选项\r\n                </td>\r\n                <td width=\"60%\" class=\"listTableHead\">\r\n                  比例（%）\r\n                </td>\r\n                <td class=\"listTableHeadLast\" width=\"10%\">\r\n                  票数（人）\r\n                </td>\r\n              </tr>\r\n\t\t\t\t  ");

				  for(int i=1;i<list.size()/3;i++){
					 String listClass="listTableLine1";
						if(i%2 != 0){listClass="listTableLine2";}
				  
      out.write("\r\n\t\t\t\t\t<TR>\r\n\t\t\t\t\t\t<TD class=\"");
      out.print(listClass);
      out.write("\"><a href=\"javascript:viewItemBrowser(");
      out.print(item[i-1][1]);
      out.write(')');
      out.write('"');
      out.write('>');
      out.print(list.get(i*3).toString());
      out.write("</a></TD>\r\n\t\t\t\t\t\t<TD class=\"");
      out.print(listClass);
      out.write("\"><img src=\"/jsoa/images/surveybar.gif\" height=10 width=");
      out.print(3*(Float.parseFloat(list.get(i*3+1).toString())));
      out.write('>');
      out.write(' ');
      out.print(list.get(3*i+1));
      out.write("%</TD>\r\n\t\t\t\t\t\t<TD class=\"");
      out.print(listClass);
      out.write(" listTableLineLastTD\">");
      out.print(Integer.parseInt(list.get(i*3+2).toString()));
      out.write("</TD>\r\n\t\t\t\t\t</TR>\r\n\t\t\t\t  ");
}
      out.write("\r\n            </table>\r\n            <br>\r\n            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n              <tr>\r\n                <td>\r\n                  <div align=\"center\"><button class=\"btnButton4font\" onclick=\"javascript:window.close()\">关闭窗口</button></div>\r\n                </td>\r\n              </tr>\r\n            </table>\r\n          </td>\r\n        </tr>\r\n      </table>\r\n\r\n    </td>\r\n  </tr>\r\n</table>\r\n<br>\r\n</td></tr></table>\r\n</body>\r\n<script language=\"javascript\">\r\nfunction viewBrowser(read) {\r\n    MM_openBrWindow(\"/jsoa/LookIntoAction.do?action=viewbrowserUser&surveyId=");
      out.print(surveyId);
      out.write("&read=\"+read,'','width=660,height=480,toolbar=no,location=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,left=0,top=0') ;\r\n}\r\n\r\nfunction viewItemBrowser(items) {\r\n    MM_openBrWindow(\"/jsoa/LookIntoAction.do?action=viewItemBrowserUser&items=\"+items,'','width=660,height=480,toolbar=no,location=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,left=0,top=0') ;\r\n}\r\n\r\n</script>\r\n</html>\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n");
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
