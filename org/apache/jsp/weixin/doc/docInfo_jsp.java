/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:00:10 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.weixin.doc;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.system.vo.usermanager.EmployeeVO;
import java.util.List;
import java.util.Map;
import com.js.wap.util.WapUtil;
import com.js.util.util.HTMLEncoding;
import com.js.util.config.SystemCommon;

public final class docInfo_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("com.js.util.util.HTMLEncoding");
    _jspx_imports_classes.add("com.js.system.vo.usermanager.EmployeeVO");
    _jspx_imports_classes.add("java.util.Map");
    _jspx_imports_classes.add("com.js.wap.util.WapUtil");
    _jspx_imports_classes.add("com.js.util.config.SystemCommon");
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
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE html>\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n\r\n");

	String id=request.getAttribute("id").toString();
	request.setCharacterEncoding("UTF-8");
	String path = request.getContextPath();
	Map map = (Map)request.getAttribute("map");
	String from = (String)request.getAttribute("from");
	String loginType = null!=session.getAttribute("loginType") ? session.getAttribute("loginType").toString() : "";
	String backEvent = "javascript:loadURL();";

      out.write("\r\n<HTML xmlns=\"http://www.w3.org/1999/xhtml\">\r\n\t<HEAD>\r\n\t\t<TITLE>公文详细信息</TITLE>\r\n\t\t<META content=\"text/html; charset=UTF-8\" http-equiv=Content-Type>\r\n\t\t<META name=viewport content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\">\r\n\t\t<META name=apple-touch-fullscreen content=YES>\r\n\t\t<META name=apple-mobile-web-app-capable content=no>\r\n\t\t<link rel=\"stylesheet\" href=\"/jsoa/css/weixin/normalize.css\">\r\n\t\t<link rel=\"stylesheet\" href=\"/jsoa/css/weixin/font-awesome.min.css\">\r\n\t\t<link rel=\"stylesheet\" type=\"text/css\" href=\"/jsoa/eform/ext/resources/css/ext-all-jiusi.css\"/>\r\n\t\t<link rel=\"stylesheet\" href=\"/jsoa/css/weixin/search.css\">\r\n\t\t<script type=\"text/javascript\" src=\"/jsoa/js/weixin/zepto.min.js\"></script>\r\n\t\t<script type=\"text/javascript\" src=\"/jsoa/js/weixin/zepto.actual.min.js\"></script>\r\n\t\t<script type=\"text/javascript\" src=\"/jsoa/js/weixin/zepto.stack.js\"></script>\r\n\t\t<script type=\"text/javascript\" src=\"/jsoa/js/weixin/wechat.js\"></script>\r\n\t\t<script type=\"text/javascript\" src=\"/jsoa/js/weixin/ku.js\"></script>\r\n");
      out.write("\t\t<script type=\"text/javascript\" src=\"/jsoa/js/weixin/main.js\"></script>\r\n\t\t<META name=GENERATOR content=\"MSHTML 8.00.6001.19154\">\r\n\t</head>\r\n\t<style type=\"text/css\">\r\n\t.list_2 {\r\n\t\t/*height: 20px;*/\r\n\t\tborder-bottom: solid 1px;\r\n\t\tborder-bottom-color: #CCC;\r\n\t\tmargin-left: 10px;\r\n\t\tmargin-right: 10px;\r\n\t\tpadding-bottom: 10px;\r\n\t\tpadding-top: 10px;\r\n\t}\r\n\t\r\n\t.st1 {\r\n\t\tfloat: left;\r\n\t\tfont-weight: bold;\r\n\t\tpadding-left: 10px;\r\n\t\twidth: 85px;\r\n\t\ttext-align: right;\r\n\t}\r\n\t\r\n\t.t2 {\r\n\t\tmargin-left: 15px;\r\n\t\t/* float: left; */\r\n\t}\r\n\t/* .list_2 img {\r\n\t\tWIDTH: 50px; FLOAT: center;\r\n\t} */\r\n\t</style>\r\n\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/wap2/commonImport.jsp", out, false);
      out.write("\r\n\t<body class=\"wapcss\">\r\n\t\t");

		if(SystemCommon.getCustomerName().equals("zcl")){
			
      out.write("\r\n\t\t\t<div id=\"mainpage_navdiv\" class=\"top\" >\r\n\t\t    \t<a id=\"mainpage_backurl\" href=\"");
      out.print(backEvent );
      out.write("\">\r\n\t\t    \t\t<img width=\"20\" height=\"15\" src=\"/jsoa/wap2/images/topfh.png\" style=\"margin-top: 14px;\">\r\n\t\t    \t</a>\r\n\t\t    \t<p id=\"mainpage_title\">工作处理</p>\r\n\t    \t</div>\r\n\t\t\t");

		}
		
      out.write("\r\n\t\t<input type=\"hidden\" name=\"docId\" id=\"docId\" value=\"\" />\r\n\t\t\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"st1\">发文字号：</div>\r\n\t\t\t\t<div class=\"t2\" style=\"vertical-align:top\">");
      out.print(map.get("wh")==null || "".equals(map.get("wh"))?"&nbsp;":map.get("wh") );
      out.write("\r\n\t\t\t\t</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t\t\t</div>\r\n\r\n\t\t\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"st1\">标　　题：</div>\r\n\t\t\t\t<div class=\"t2\">");
      out.print(map.get("title") );
      out.write("</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t\t\t</div>\r\n\t\t\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"st1\">拟文单位：</div>\r\n\t\t\t\t<div class=\"t2\">");
      out.print(map.get("fwzz") );
      out.write("</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t\t\t</div>\r\n\t\t\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"st1\">发文时间：</div>\r\n\t\t\t\t<div class=\"t2\">");
      out.print(map.get("fwsj") );
      out.write("</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t\t\t</div>\r\n\t\t\t");
 if(map.get("zw") != null){ 
      out.write("\r\n\t\t\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"st1\">正　　文：</div>\r\n\t\t\t\t<div class=\"t2\"><A href=\"javascript:viewContent('");
      out.print(map.get("zw"));
      out.write("');\">[查看正文]</A></div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t\t\t</div>\r\n\t\t\t");
} //手机版添加公文附件查看功能
      out.write("\r\n\t\t\t");
 if(map.get("fj") != null){ 
			String strfj=map.get("fj").toString();
			String[] fj=strfj.split("\\|");
			String strfjlong=map.get("fjlong").toString();
			String[] fjlong=strfjlong.split("\\|");
			
      out.write("\r\n\t\t\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"st1\" style=\"float:left;\">附　　件：</div>\r\n\t\t\t\t<div class=\"t2\" style=\"float:left;\">");
for(int i=0;i<fj.length;i++){
      out.write("\r\n\t\t\t\t<A href=\"javascript:viewContent('");
      out.print(fjlong[i]);
      out.write("');\">");
      out.print(fj[i] );
      out.write("</A></br>\r\n\t\t\t\t");
}
      out.write("\r\n\t\t\t\t</div>\r\n\t\t\t\t<div  style=\"clear:both\"></div>\r\n\t\t\t</div>\r\n\t\t\t");
} 
      out.write("\r\n\t\t<div class=\"footer\">\r\n\t      \t<div class=\"buttons\">\r\n\t          \t<div id=\"closeWindow\" class=\"button gray\" ");
if(SystemCommon.getCustomerName().equals("zcl")){
      out.write("style=\"display:none;\"");
} 
      out.write(" onclick=\"closeWindow();\">关闭</div>\r\n\t      \t</div>\r\n\t    </div>\r\n\t    ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/weixin/common/weixin_messageReminders.jsp", out, false);
      out.write("\r\n\t    ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/weixin/workflow/daibanflow/weixinshowhtml.jsp", out, false);
      out.write("\r\n\t</body>\r\n<SCRIPT type=\"text/javascript\">\r\n");
if(SystemCommon.getCustomerName().equals("zcl")){
      out.write("\r\n\tfunction closeWindow(){\r\n\t\twindow.history.back();\r\n\t}\r\n\tfunction loadURL(){\r\n\t\tvar docId=document.getElementById(\"docId\").value;\r\n\t\tif(docId!=null && docId!=\"\"){\r\n\t\t   var url = \"/jsoa/weixinGovReceiveFileBoxAction.do?action=load&from=list&id=\" + docId;\r\n\t\t   window.location.href = url;\r\n\t\t}else{\r\n\t\t\twindow.history.back();\r\n\t\t}\r\n\t}\r\n");
}else{
      out.write("\r\n\tfunction closeWindow(){\r\n\t     if('");
      out.print(from);
      out.write("'=='message'){\r\n\t        closeWindow();\r\n\t     }else{\r\n\t        window.history.back();\r\n\t     }\r\n\t  }\r\n");
}
      out.write("\r\nvar readyFunc = function onBridgeReady() {\r\n\t\t\tWeixinJSBridge.call('hideOptionMenu');\r\n\t\t\tWeixinJSBridge.call('hideToolbar');\r\n\t\t}\r\n\t\r\n\t\tif (typeof WeixinJSBridge === \"undefined\") {\r\n\t\t\tif (document.addEventListener) {\r\n\t\t\t\tdocument.addEventListener('WeixinJSBridgeReady', readyFunc, false);\r\n\t\t\t} else if (document.attachEvent) {\r\n\t\t\t\tdocument.attachEvent('WeixinJSBridgeReady', readyFunc);\r\n\t\t\t\tdocument.attachEvent('onWeixinJSBridgeReady', readyFunc);\r\n\t\t\t}\r\n\t\t} else {\r\n\t\t\treadyFunc();\r\n\t\t}\r\n  function viewContent(fileName){\r\n\t  //此标识docId是中储粮用来页面跳转的\r\n\t  document.getElementById(\"docId\").value=\"");
      out.print(id);
      out.write("\";\r\n      var path=\"govdocumentmanager\";\r\n      if(fileName == \"\"){\r\n\t      weixinMessageReminder('alert', '提示：', '公文不存在或被删除');\r\n\t      return ;\r\n\t   }\r\n\t   showHtmlObject(fileName, \"0\", path);\r\n  }\r\n  </SCRIPT>\r\n</html>\r\n");
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
