/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:03:51 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.oacollect;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.system.util.StaticParam;
import java.util.*;
import com.js.system.manager.service.ManagerService;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.oacollect.bean.OACollectCategoryEJBBean;

public final class oacollectfiletree_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("com.js.system.util.StaticParam");
    _jspx_imports_classes.add("com.js.oa.oacollect.bean.OACollectCategoryEJBBean");
    _jspx_imports_classes.add("com.js.oa.jsflow.service.ProcessBD");
    _jspx_imports_classes.add("com.js.system.manager.service.ManagerService");
    _jspx_imports_classes.add("com.js.oa.jsflow.service.WorkFlowBD");
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
  
	response.setHeader("Cache-Control","no-store");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader ("Expires", 0);
    String userId = session.getAttribute("userId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    OACollectCategoryEJBBean bean=new OACollectCategoryEJBBean();
    List<Object> categoryList=bean.searchAll(Long.valueOf(-1),userId,"1");
    ManagerService mbd = new ManagerService();
    String webapp=request.getContextPath();
    String[] treeShow = bean.treeShow(categoryList,userId);

      out.write("\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\"/>\r\n<title>无标题文档</title>\r\n<link rel=\"StyleSheet\" href=\"");
      out.print(webapp);
      out.write("/style/dtree.css\" type=\"text/css\" />\r\n<script language=\"JavaScript\" src=\"/jsoa/js/MzTreeView10.js\"></script>\r\n<STYLE>\r\n  body {\r\n  \tmargin: 0px\r\n  }\r\n\r\n  #Loading {\r\n\t    position: absolute;\r\n\t    text-align:left;\r\n\t    z-index: 10;\r\n\t    background-color: #FFFFFF;\r\n\t\tborder-top-width: thin;\r\n\t\tborder-right-width: thin;\r\n\t\tborder-bottom-width: thin;\r\n\t\tborder-left-width: thin;\r\n\t\tborder-top-style: outset;\r\n\t\tborder-right-style: outset;\r\n\t\tborder-bottom-style: outset;\r\n\t\tborder-left-style: outset;\r\n\t\tborder-top-color: #CCCCCC;\r\n\t\tborder-right-color: #CCCCCC;\r\n\t\tborder-bottom-color: #CCCCCC;\r\n\t\tborder-left-color: #CCCCCC;\r\n\t\tpadding:5px;\r\n\t\r\n  }\r\n  #Loading  {filter:progid:DXImageTransform.Microsoft.Shadow (Color=#333333,Direction=120,strength=5)} \r\n  \r\n  A.dian:link,A.dian:visited{\r\n\ttext-decoration:none;\r\n\tfloat:left;\r\n\twidth:98%;\r\n\tpadding:5px 10px;\r\n\tfont-size:13px;\r\n\tcolor:#000000;\r\n}\r\n\r\nA.dian:hover,A.dian:active{\r\n\tfloat:left;\r\n\tpadding:5px 10px;\r\n\twidth:100px;\r\n\ttext-decoration:none;\r\n\tbackground-color:#e2e2e2;\r\n");
      out.write("\tfont-size:13px;\r\n\tcolor:#000000;\r\n}\r\n</STYLE>\r\n<style type=\"text/css\">\r\n<!--\r\nbody{\r\nscrollbar-base-color: #ffffff;\r\nscrollbar-darkshadow-color: #ffffff;\r\nscrollbar-highlight-color: #ffffff;\r\nbackground:url(背景图片地址);\r\noverflow:hidden;\r\n}\r\ndiv.main{\r\noverflow:scroll;\r\npadding:0px 0px 0px 15px;\r\nwidth:132px;\r\nheight:100%;\r\n\r\n}\r\n\r\n-->\r\n</style>\r\n<script type=\"text/javascript\">\r\nfunction OpenDiv(url){\r\n\tvar personfile = parent.document.getElementById(\"personfile\");\r\n\tpersonfile.src =url;\r\n}\r\n</script>\r\n</head>\r\n<html>\r\n<body leftmargin=\"0\" topmargin=\"0\" style=\"background-color: #ffffff\" >\r\n<form name=\"orgTreeBar\" method=\"post\" style=\"height:100%\">\r\n    <div class=\"main\" style=\"width:100%;height:100%;overflow-x:auto;overflow-y:auto;font-size:12px; background-image:url(\\imges\\left-1.gif)\">   \r\n\t\t<script language=\"JavaScript\">\r\n\t\t\tvar tree = new MzTreeView(\"tree\");\r\n\t\t\ttree.setIconPath(\"\"); //可用相对路径\r\n\t\t\ttree.nodes[\"0_A\"] = \"text:文件采集;data:id=33;\";//不显示根节点\r\n\t\t\t");
if(treeShow[0].length()>0){
      out.write("\r\n\t\t\ttree.nodes[\"A_A-1\"] = \"icon:doc_public;text:单位文档;data:id=31;\";//不显示根节点\r\n\t\t\t");
      out.print(treeShow[0] );
      out.write("\r\n\t\t\t");
}
      out.write("\r\n\t\t\ttree.nodes[\"A_B-1\"] = \"icon:doc_personal;text:个人文档;data:id=33;method:OpenDiv('/jsoa/OACollectFileAction.do?action=fileList&displayFlag=suoluetu&fromFlag=gerenwendang')\";//显示根节点\r\n\t\t\t\r\n\t\t\t");
      out.print(treeShow[1] );
      out.write("\r\n\t\t\t\r\n\t\t\ttree.setTarget(\"MainDesktop\");\r\n\t\t\tdocument.write(tree.toString());\r\n\t\t</script>\r\n   \t</div>\r\n</form>\r\n</body>\r\n</html>\r\n");
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