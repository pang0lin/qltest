/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:44:55 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.chat;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.util.util.BrowserJudge;
import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;

public final class show_005forg_005ftree_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(7);
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-nested.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-tiles.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-logic.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/SSHA.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-html.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-template.tld", Long.valueOf(1499751390000L));
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
    _jspx_imports_classes.add("com.js.system.util.StaticParam");
    _jspx_imports_classes.add("com.js.util.config.SystemCommon");
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
  
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
String webapp=request.getContextPath();
String range=request.getParameter("range");
String userAcc = session.getAttribute("userAccount").toString();
String orgIdString = "";
if(SystemCommon.getChildUse()==1){
	orgIdString = session.getAttribute("orgIdString").toString();
	String[] orgIdStrs = orgIdString.split("\\$");
	orgIdString = "";
	//String[] orgIds = new String[(orgIdStrs.length/2)];
	for(int i=1;i<orgIdStrs.length&&i<(2*SystemCommon.getChildShow());i=i+2){
		//orgIds[((i-1)/2)] = orgIdStrs[i];
		orgIdString += "%"+orgIdStrs[i]+"%";
	}
}
String width = "94%;";
if(BrowserJudge.isMSIE(request)){
	width="99%;";
}
String benren = request.getParameter("benren")==null?"":request.getParameter("benren");
if(!"".equals(benren)){
	range = range+"-"+benren;
}

      out.write("\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\"/>\r\n<title>无标题文档</title>\r\n<link rel=\"StyleSheet\" href=\"");
      out.print(webapp);
      out.write("/style/dtree.css\" type=\"text/css\" />\r\n<script type=\"text/javascript\" src=\"");
      out.print(webapp);
      out.write("/js/tree/wtree.js\"></script>\r\n<STYLE>\r\n  body {\r\n  \tmargin: 0px\r\n  }\r\n\r\n  #Loading {\r\n\t    position: absolute;\r\n\t    text-align:left;\r\n\t    z-index: 10;\r\n\t    background-color: #FFFFFF;\r\n\t\tborder-top-width: thin;\r\n\t\tborder-right-width: thin;\r\n\t\tborder-bottom-width: thin;\r\n\t\tborder-left-width: thin;\r\n\t\tborder-top-style: outset;\r\n\t\tborder-right-style: outset;\r\n\t\tborder-bottom-style: outset;\r\n\t\tborder-left-style: outset;\r\n\t\tborder-top-color: #CCCCCC;\r\n\t\tborder-right-color: #CCCCCC;\r\n\t\tborder-bottom-color: #CCCCCC;\r\n\t\tborder-left-color: #CCCCCC;\r\n\t\tpadding:5px;\r\n\t\r\n  }\r\n  #Loading  {filter:progid:DXImageTransform.Microsoft.Shadow (Color=#333333,Direction=120,strength=5)} \r\n  \r\n  A.dian:link,A.dian:visited{\r\n\ttext-decoration:none;\r\n\tfloat:left;\r\n\twidth:98%;\r\n\tpadding:5px 10px;\r\n\tfont-size:13px;\r\n\tcolor:#000000;\r\n}\r\n\r\nA.dian:hover,A.dian:active{\r\n\tfloat:left;\r\n\tpadding:5px 10px;\r\n\twidth:100px;\r\n\ttext-decoration:none;\r\n\tbackground-color:#e2e2e2;\r\n\tfont-size:13px;\r\n\tcolor:#000000;\r\n}\r\n</STYLE>\r\n<style type=\"text/css\">\r\n");
      out.write("<!--\r\nbody{\r\nscrollbar-base-color: #ffffff;\r\nscrollbar-darkshadow-color: #ffffff;\r\nscrollbar-highlight-color: #ffffff;\r\nbackground:url(背景图片地址);\r\noverflow:hidden;\r\n}\r\ndiv.main{\r\noverflow:scroll;\r\npadding:0px 0px 0px 15px;\r\nwidth:132px;\r\nheight:100%;\r\n\r\n}\r\n\r\n-->\r\n</style>\r\n<script type=\"text/javascript\">\r\nfunction OpenDiv(obj,id){\r\n\tvar iframeInner = parent.document.getElementById(\"personinner\");\r\n\tvar showOnline = window.parent.frames[\"personinner\"].document.getElementById(\"onLine\").checked;\r\n\tvar username = window.parent.frames[\"personinner\"].document.getElementsByName(\"username\")[0].value;;\r\n\tiframeInner.src = ((window.location.href).substring(0,(window.location.href).indexOf(\"/jsoa\")))+\"/jsoa/OnlinerAction.do?method=showOnliner&action=showOnline&order=5&username=\"+username+\"&orgShow=\"+id+\"&onLine=\"+showOnline;\r\n}\r\n</script>\r\n</head>\r\n<html>\r\n<body leftmargin=\"0\" topmargin=\"0\" onclick=\"hidenPOPMenu()\" style=\"background-color: #EEF3FA\" oncontextmenu=\" return true\">\r\n<form name=\"orgTreeBar\" method=\"post\" style=\"height:100%\">\r\n");
      out.write("  <div id=\"treearea\" class=\"main\" style=\"width:");
      out.print(width );
      out.write(" overflow-x:auto; overflow-y:auto; height:100%; scrollbar-face-color:#efebef;\">\r\n      <script language=\"javascript\" type=\"text/javascript\">\r\n\t\t\tvar d=new dTree('d','/jsoa/js/tree/images/menubar_4/','org_tree')\r\n\t\t\td.config.folderLinks=true;\r\n\t\t\t");
      out.print(StaticParam.getOrgTreeForOnline(range,orgIdString) );
      out.write("\r\n\t\t\tdocument.getElementById('treearea').innerHTML = d;\r\n\t  </script>\r\n  </div> \r\n  <div id=\"Loading\" style=\"display:none;\" >f</div>\r\n</form>\r\n</body>\r\n</html>\r\n");
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
