/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:59:06 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.weixin.workflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class failure_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html;charset=utf-8");
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

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String failure=request.getAttribute("failure")==null?"error":request.getAttribute("failure").toString();

      out.write("\r\n\r\n<!DOCTYPE html>\r\n<html>\r\n  <head>\r\n\t<TITLE>提示信息</TITLE>\r\n\t<META content=\"text/html; charset=UTF-8\" http-equiv=Content-Type>\r\n\t<META name=viewport content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\">\r\n\t<META name=apple-touch-fullscreen content=YES>\r\n\t<META name=apple-mobile-web-app-capable content=no>\r\n    <script type=\"text/javascript\" src=\"/jsoa/js/weixin/mobiscroll/js/jquery-1.9.1.min.js\"></script>\r\n    ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/weixin/common/weixin_messageReminders.jsp", out, false);
      out.write("\r\n  </head>\r\n  <body>\r\n   <div id=\"closeWindow\" style=\"display:none;\">关闭</div>\r\n  <script type=\"text/javascript\">\r\n  \tvar readyFunc = function onBridgeReady() {\r\n\t\t//隐藏顶部右侧的分享按钮\r\n\t\tWeixinJSBridge.call('hideOptionMenu');\r\n\t\t//隐藏底部的工具栏\r\n\t\tWeixinJSBridge.call('hideToolbar');\r\n\t}\r\n\t\r\n\tif (typeof WeixinJSBridge === \"undefined\") {\r\n\t\tif (document.addEventListener) {\r\n\t\t\tdocument.addEventListener('WeixinJSBridgeReady', readyFunc, false);\r\n\t\t} else if (document.attachEvent) {\r\n\t\t\tdocument.attachEvent('WeixinJSBridgeReady', readyFunc);\r\n\t\t\tdocument.attachEvent('onWeixinJSBridgeReady', readyFunc);\r\n\t\t}\r\n\t} else {\r\n\t\treadyFunc();\r\n\t}\r\n\tfunction colseWindow(){\r\n\t     WeixinJSBridge.invoke('closeWindow', {}, function(res) {\r\n      \t\tif(\"system:access_denied\" == res.err_msg){\r\n      \t\t\t// 微信6.0.2以下版本不能直接关闭页面，可以跳转到待办事项列表页面\r\n      \t\t\t//location.href = \"/jsoa/weiXinBacklogAction.do?action=list&status=101&from=mine\";\r\n\t\t\t\tweixinMessageReminder(\"alert\", \"提示：\", \"您的微信版本暂不支持直接关闭页面，请升级到微信6.0.2及以上版本！\", \"\");\r\n\t\t\t}\r\n       \t});\r\n\t\r\n\t}  \r\n");
      out.write("   ");
if("workflow".equals(failure)){ 
      out.write("\r\n       weixinMessageReminder(\"alert\", \"提示：\", \"相关流转数据已经被删除，或者被转交代理！\",\"colseWindow();\");\r\n   ");
}else if("cooprate".equals(failure)){
      out.write("\r\n      weixinMessageReminder(\"alert\", \"提示：\", \"数据已被终止或删除！\",\"colseWindow();\");\r\n   ");
}else{
      out.write("\r\n       weixinMessageReminder(\"alert\", \"提示：\", \"数据已被终止或删除！\",\"colseWindow();\");\r\n   ");
}
      out.write("\r\n    </script>\r\n  </body>\r\n</html>\r\n");
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
