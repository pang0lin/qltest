/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:02:39 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.menu;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.system.manager.service.ManagerService;

public final class itemcenter_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("com.js.system.manager.service.ManagerService");
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

      out.write("\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

      out.write("\r\n\r\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\" />\r\n<title>????????????</title>\r\n<link href=\"/jsoa/css/menustyle.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n</head>\r\n\r\n<body style=\"margin:0px; font-size:12px\" onload=\"initHeight();\">\r\n\t<div style=\"float:left; background-image:url(/jsoa/imges/left-cent.gif); height:500px; color:#003366;\">\r\n<table width=\"131\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n  <tr>\r\n    <td class=\"leftMenuTop\">\r\n    <div class=\"leftMenuTopDIV\"><div class=\"menuTopTitleLeft\"><img src=\"/jsoa/imges/9s.gif\" title=\"????????????\" style=\"cursor:pointer\" onmouseover=\"shortFocus(this);\" onmouseout=\"shortBlur(this);\" onclick=\"changePanle(1);\"></img></div><div class=\"menuTopTitle\"><b>????????????</b></div><div class=\"menuTopTitleRight\"><img src=\"/jsoa/imges/9s.gif\" title=\"????????????\" style=\"cursor:pointer\" onmouseover=\"shortFocus(this);\" onmouseout=\"shortBlur(this);\" onclick=\"changePanle(1);\"></img></div></div>\r\n");
      out.write("    <div class=\"menuScrollDIV\">\r\n        \t<table width=\"20\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n\t\t\t\t\t  <tr>\r\n\t\t\t\t\t    <td height=\"17\" align=\"center\" valign=\"bottom\" ><img id=\"imgUP\" src=\"/jsoa/imges/up1.gif\" onmouseover=\"upFocus(this);\" onmouseout=\"upBlur(this);stopUp();\" onmousedown=\"setUp();\" onmouseup=\"stopUp();\" onclick=\"scrollUp();\"/></td>\r\n\t\t\t\t\t    </tr>\r\n\t\t\t\t\t  <tr>\r\n\t\t\t\t\t    <td height=\"14\" align=\"center\" valign=\"middle\"><img id=\"imgDown\" src=\"/jsoa/imges/down1.gif\" onmouseover=\"downFocus(this);\" onmouseout=\"downBlur(this);stopDown();\" onmousedown=\"setDown();\" onmouseup=\"stopDown();\" onclick=\"scrollDown();\"/></td>\r\n\t\t\t\t\t    </tr>\r\n\t\t\t\t\t</table>\r\n       </div></td>\r\n  </tr>\r\n</table>\r\n\r\n<table  width=\"131\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n  ");

  int menuIndex=-1;
  
      out.write("\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n   \r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/FileDealWithAction.do?workStatus=0')\">&nbsp;&nbsp;&nbsp;<img src=\"/jsoa/imges/shanj.gif\" />&nbsp;????????????</div></td>\r\n  </tr>\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/FileDealWithAction.do?workStatus=2')\">&nbsp;&nbsp;&nbsp;<img src=\"/jsoa/imges/shanj.gif\" />&nbsp;????????????</div></td>\r\n  </tr>\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/FileDealWithAction.do?workStatus=1011')\">&nbsp;&nbsp;&nbsp;<img src=\"/jsoa/imges/shanj.gif\" />&nbsp;????????????</div></td>\r\n  </tr>\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/FileDealWithAction.do?workStatus=1012')\">&nbsp;&nbsp;&nbsp;<img src=\"/jsoa/imges/shanj.gif\" />&nbsp;????????????</div></td>\r\n  </tr>\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/FileDealWithAction.do?workStatus=102')\">&nbsp;&nbsp;&nbsp;<img src=\"/jsoa/imges/shanj.gif\" />&nbsp;????????????</div></td>\r\n  </tr>\r\n  \r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/FileDealWithAction.do?workStatus=1')\">&nbsp;&nbsp;&nbsp;<img src=\"/jsoa/imges/shanj.gif\" id=\"menuImg_");
      out.print(menuIndex );
      out.write("\"/>&nbsp;????????????</div></td>\r\n  </tr>\r\n  ");
int subMenuIndex=-1; 
      out.write("\r\n      <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n         <td height=\"22\"><a style=\"cursor:pointer\" onclick=\"jumpMain('/jsoa/FileDealWithAction.do?workStatus=1')\">&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/jsoa/imges/shanj.gif\" />&nbsp;????????????</a></td>      \r\n      </tr> \r\n      <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n         <td height=\"22\"><a style=\"cursor:pointer\" onclick=\"jumpMain('/jsoa/FileDoneWithAction.do')\">&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/jsoa/imges/shanj.gif\" />&nbsp;????????????</a></td>      \r\n      </tr> \r\n      <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n         <td height=\"22\"><a style=\"cursor:pointer\" onclick=\"jumpMain('/jsoa/FileUntreadAction.do?workStatus=-1')\">&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/jsoa/imges/shanj.gif\" />&nbsp;????????????</a></td>      \r\n      </tr> \r\n      <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n         <td height=\"22\"><a style=\"cursor:pointer\" onclick=\"jumpMain('/jsoa/FileUntreadAction.do?workStatus=-2')\">&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/jsoa/imges/shanj.gif\" />&nbsp;????????????</a></td>      \r\n      </tr> \r\n   ");

    ManagerService mservice=new ManagerService();
	if(mservice.hasRight(session.getAttribute("userId").toString(), "02*03*02")){
	
      out.write("   \r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/FileDealWithAction.do?workStatus=0&tran=1')\">&nbsp;&nbsp;&nbsp;<img src=\"/jsoa/imges/shanj.gif\" />&nbsp;????????????</div></td>\r\n  </tr> \r\n  ");
} 
      out.write("\r\n  \r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/pressManageAction.do?action=receive_list')\">&nbsp;&nbsp;&nbsp;<img src=\"/jsoa/imges/shanj.gif\" />&nbsp;????????????</div></td>\r\n  </tr>   \r\n  \r\n</table>\r\n\r\n</div>\r\n</body>\r\n</html>\r\n<script src=\"/jsoa/js/imenu.js\"></script>");
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
