/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:02:53 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.menu;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.oa.crm.util.JDBCManager;

public final class crm_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("com.js.oa.crm.util.JDBCManager");
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
int menuIndex=-1;
String userId=session.getAttribute("userId").toString();
String orgId=session.getAttribute("orgId").toString();
boolean kh=JDBCManager.hasUserRange(userId,orgId,"客户管理");
boolean tz=JDBCManager.hasUserRange(userId,orgId,"台帐管理");
boolean hk=JDBCManager.hasUserRange(userId,orgId,"回款管理");
boolean hf=JDBCManager.hasUserRange(userId,orgId,"回访管理");
boolean sz=JDBCManager.hasUserRange(userId,orgId,"设置管理");
boolean lw=JDBCManager.hasUserRange(userId,orgId,"例外管理");

      out.write("\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\" />\r\n<title>客戶管理</title>\r\n<link href=\"/jsoa/css/menustyle.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n</head> \r\n<script src=\"/jsoa/js/util.js\"></script>\r\n<script src=\"/jsoa/js/imenu.js\"></script>\r\n<style type=\"text/css\">\r\n<!--\r\nbody,table,div{font-size:12px; margin:0px; padding:0px;color:#003366;}\r\nbody{\r\nscrollbar-base-color: #ffffff;\r\nscrollbar-darkshadow-color: #ffffff;\r\nscrollbar-highlight-color: #ffffff;\r\nbackground:url(背景图片地址);\r\noverflow:hidden;\r\nmargin:0;\r\n}\r\ndiv.main{\r\noverflow:scroll;\r\nwidth:500px;\r\nheight:400px;\r\n/*filter: chroma(color=#ffffff);*/\r\n}\r\n*{margin:0;padding:0;}\r\n");
if(com.js.util.util.BrowserJudge.isMSIE(request)){ 
      out.write("\r\n.menuNormal{\r\n\theight:32px; \r\n\twidth:131px; \r\n\tpadding-left:17px; \r\n\tbackground-image:url(/jsoa/imges/left-1.gif); \t\r\n\tcursor:pointer;\r\n}\r\n\r\n.menuFocus{\r\n\theight:32px; \r\n\twidth:131px; \r\n\tpadding-left:17px; \r\n\tbackground-image:url(/jsoa/imges/left-jiaoh.gif);\r\n\tfont-weight:bold;\r\n\tcursor:pointer;\r\n}\r\n");
}
      out.write("\r\n.leftMenuTop{\r\n\tbackground-image:url(/jsoa/imges/left-1.gif);}\r\n\r\n/*.leftMenuTopDIV{\r\n\theight:22px; \r\n\tpadding-top:8px; \r\n\tfloat:left; \r\n\twidth:132px;\r\n\tmargin:0px;\r\n}*/\r\n\r\n.topMenuNormal{\r\n\twidth:60px;\r\n\tcursor:pointer;\r\n}\r\n\r\n.topMenuFocus{\r\n\tbackground-image:url(/jsoa/imges/zi.gif); \r\n\twidth:60px;\r\n\tcursor:pointer;\r\n}\r\n\r\n/*.menuTopTitle{\r\n\tfloat:left;\r\n\tpadding-top:2px;\r\n}\r\n\r\n.menuTopTitleLeft{\r\n\twidth:17px;\r\n\tfloat:left;\r\n\tpadding:0px 2px 0px 7px;\r\n}\r\n\r\n.menuTopTitleRight{\r\n\tpadding-left:2px;\r\n\twidth:17px;\r\n\tfloat:left;\r\n}\r\n*/\r\n.menuScrollDIV{\r\n\tfloat:left; \r\n\tpadding-bottom:2px;\r\n}\r\n\r\n.menuShowHide{\r\n\tbackground-image:url(/jsoa/imges/cent.gif); \r\n\twidth:8px; \r\n\tfloat:left; \r\n\theight:100%; \r\n\tpadding-top:270px;\r\n}\r\n\r\n.portalNormal{\r\n\tbackground-image:url(/jsoa/imges/03.gif);\r\n\tcolor:#000;\r\n\twidth:75px;\r\n\tcursor:pointer;\r\n}\r\n\r\n.portalFocus{\r\n\tbackground-image:url(/jsoa/imges/02.gif);\r\n\tcolor:#000;\r\n\twidth:75px;\r\n\tcursor:pointer;\r\n} \r\n-->\r\n</style>\r\n<body style=\"margin:0px; font-size:12px\" onload=\"initHeight();\" onresize=\"initHeight();\">\r\n");
      out.write("\t<div style=\"float:left; background-image:url(/jsoa/imges/left-cent.gif); height:500px; color:#003366;\">\r\n<table width=\"131\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n  <tr>\r\n    <td class=\"leftMenuTop\">\r\n\t    <div class=\"leftMenuTopDIV\"><div class=\"menuTopTitleLeft\"><img src=\"/jsoa/imges/9s.gif\"></img></div><div class=\"menuTopTitle\"><b>客戶管理</b></div><div class=\"menuTopTitleRight\"><img src=\"/jsoa/imges/9s.gif\"></img></div></div>\r\n\t    <div class=\"menuScrollDIV\">\r\n\t       \t<table height=\"32\"  width=\"20\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n\t\t\t  <tr>\r\n\t\t\t    <td align=\"center\" valign=\"center\"  style=\"padding-top:4px;\">\r\n\t\t\t       <img id=\"imgUP\" src=\"/jsoa/imges/kzmb.gif\" title=\"切换面板\" style=\"cursor:pointer\" onmouseover=\"shortFocus_(this);\" onmouseout=\"shortBlur_(this);\" onclick=\"changePanle(1);\"/>\r\n\t\t\t    </td>\r\n\t\t\t  </tr>\t\t\t\t\t\r\n\t\t\t</table>\r\n\t   </div>\r\n\t</td>\r\n  </tr>\r\n</table>\r\n\r\n<table  width=\"131\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n\r\n");
if(lw||kh){ 
      out.write("\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/cst.do?method=getCstList')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;客戶管理</div></td>\r\n  </tr>\r\n");
}if(lw||tz){
      out.write("\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/taiZha.do?method=getHuiQList')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;台帐管理</div></td>\r\n  </tr>\r\n");
}if(lw||hk){ 
      out.write("  \r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/moneyBack.do?method=getOwnList')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;回款管理</div></td>\r\n  </tr>\r\n");
}if(lw||hf){
      out.write("\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/cstVisit.do?method=getCstVisitList')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;回访管理</div></td>\r\n  </tr>\r\n");
}if(lw||sz){
      out.write("\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/cstType.do?method=getCstTypeList&flag=0')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;设置管理</div></td>\r\n  </tr>\r\n");
}
      out.write("    \r\n  \r\n</table>\r\n\r\n</div>\r\n</body>\r\n</html>\r\n<script src=\"/jsoa/js/imenu.js\"></script>");
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