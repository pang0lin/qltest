/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:59:32 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.weixin.workflow.pressfeedback;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import java.text.*;
import com.js.oa.pressdeal.po.OaPersonoaPressPO;
import com.js.lang.Resource;
import com.js.system.service.messages.MessagesBD;

public final class press_005fdisplayForWeiXin_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {


   private String myTrim(String str){
     String rslt =  (null==str||"".equals(str.trim())||"null".equals(str.trim()))?"":str.trim();
     return rslt;
   }

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("java.text");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.lang.Resource");
    _jspx_imports_classes.add("com.js.oa.pressdeal.po.OaPersonoaPressPO");
    _jspx_imports_classes.add("com.js.system.service.messages.MessagesBD");
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
      response.setContentType("text/html; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			"../../../public/jsp/error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n<!DOCTYPE html>\r\n");


String path = request.getContextPath();
String basePath = request.getContextPath();
request.setCharacterEncoding("UTF-8");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
String local = session.getAttribute("org.apache.struts.action.LOCALE").toString();

      out.write("\r\n<html>\r\n<head>\r\n<title>\r\n????????????\r\n</title>\r\n<META content=\"text/html; charset=UTF-8\" http-equiv=Content-Type>\r\n\t<META name=viewport content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\">\r\n\t<META name=apple-touch-fullscreen content=YES>\r\n\t<META name=apple-mobile-web-app-capable content=no>\r\n\r\n\t<link rel=\"stylesheet\" href=\"/jsoa/css/weixin/weixin_main.css\">\r\n    <script language=\"javascript\" src=\"/jsoa/js/openEndow.js\"></script>\r\n</head>\r\n");
      out.write('\r');
      out.write('\n');

  OaPersonoaPressPO press = (OaPersonoaPressPO)request.getAttribute("press");
  String time_str ="";
  time_str = String.valueOf(press.getDispatchTime());
  time_str = time_str.substring(0,time_str.length()-2);
  String receivePress = String.valueOf(request.getAttribute("receivePress"));
  receivePress = (null==receivePress||"".equals(receivePress.trim())||"null".equals(receivePress.trim()))?"":receivePress.trim();
  String Offset = String.valueOf(request.getAttribute("pager.offset")); Offset = myTrim(Offset);
  String title = String.valueOf(request.getAttribute("title")); title = myTrim(title);
  String category = String.valueOf(request.getAttribute("category")); category = myTrim(category);
  String subcategory = String.valueOf(request.getAttribute("subcategory")); subcategory = myTrim(subcategory);
  String receiver_name = String.valueOf(request.getAttribute("receiver_name")); receiver_name = myTrim(receiver_name);
  String date_start = String.valueOf(request.getAttribute("date_start")); date_start = myTrim(date_start);
  String date_end = String.valueOf(request.getAttribute("date_end")); date_end = myTrim(date_end);
  String check_date = String.valueOf(request.getAttribute("check_date")); check_date = myTrim(check_date);
  String remind =request.getAttribute("remind")==null?"":request.getAttribute("remind").toString();
  String workflow =request.getAttribute("workflow")==null?"":request.getAttribute("workflow").toString();
  String[] workflowurl=null;
  String table="";
  String record="";
  String processId="";
  String urlStr="";
  String userId=session.getAttribute("userId").toString();
  if(null!=press.getWorkflowurl() && !"".equals(press.getWorkflowurl()+"")){
    workflowurl=press.getWorkflowurl().toString().split("&&");
    table=workflowurl[0]+"";
    record=workflowurl[1]+"";
    processId=workflowurl[2]+"";
    urlStr=new com.js.oa.pressdeal.bean.PressDealDoEJBBean().loadWfwordataUrl(table, record, processId, "0",userId,workflowurl,"weixin");
    urlStr=urlStr.replace("WorkFlowProcAction", "WorkFlowProcForWeiXinAction");
  }
  String loginType = null!=session.getAttribute("loginType") ? session.getAttribute("loginType").toString() : "";
/////////??????????????????
MessagesBD messagesBD=new MessagesBD();
  messagesBD.changeMessageStatus(press.getPressId().toString(),session.getAttribute("userId").toString(),"Press","b");

      out.write('\r');
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/wap2/commonImport.jsp", out, false);
      out.write("\r\n<body>\r\n<div class=\"form\">\r\n     <div class=\"item\">\r\n\t\t<div class=\"title\">???&nbsp;&nbsp;???&nbsp;&nbsp;???:</div>\r\n\t\t<div class=\"content\">\r\n\t\t\t<div class=\"info\">");
      out.print(press.getSendUserDep()+"."+press.getSendUsername());
      out.write("</div>\r\n\t\t</div>    \r\n     </div>\r\n     <div class=\"item\">\r\n\t\t<div class=\"title\"> ???????????????</div>\r\n\t\t<div class=\"content\">\r\n\t\t\t<div class=\"info\">");
      out.print(time_str);
      out.write("</div>\r\n\t\t</div>    \r\n     </div>\r\n     <div class=\"item\">\r\n\t\t<div class=\"title\">???&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;??????</div>\r\n\t\t<div class=\"content\">\r\n\t\t\t<div class=\"info\">");
      out.print(press.getTitle());
      out.write("</div>\r\n\t\t</div>    \r\n     </div>\r\n     <div class=\"item\">\r\n\t\t<div class=\"title\">???&nbsp;&nbsp;???&nbsp;&nbsp;??????</div>\r\n\t\t<div class=\"content\">\r\n\t\t\t<div class=\"info\">");
      out.print(press.getReceiveUsernameStr());
      out.write("</div>\r\n\t\t</div>    \r\n     </div>\r\n     <div class=\"item\">\r\n\t\t<div class=\"title\"> ???????????????</div>\r\n\t\t<div class=\"content\">\r\n\t\t\t<div class=\"info\">");
      out.print(press.getContent());
      out.write("</div>\r\n\t\t</div>    \r\n     </div>\r\n</div>\r\n<!-- ?????? -->\r\n    <div class=\"footer\">\r\n \t\t<div class=\"buttons\">\r\n \t\t\t");
if(urlStr!=null&&"1".equals(workflow) && !"".equals(urlStr) 
 			     && urlStr.indexOf("WorkFlowProcForWeiXinAction")>=0){
 				urlStr=urlStr+"&backto=cbtx";
 			
      out.write("\r\n \t\t\t<div  class=\"button\" onclick=\"javascript:doWorkflow('");
      out.print(urlStr);
      out.write("');\">??????</div>\r\n \t\t\t");
}
      out.write("\r\n \t\t\t<div id=\"closeWindow\" class=\"button gray\" onclick=\"closeWindow();\">??????</div>\r\n \t\t</div>\r\n \t</div>\r\n</body>\r\n</html>\r\n\r\n<script language=\"javascript\" type=\"text/javascript\">\r\n ");
if(!("wap".equals(loginType)||"weixin".equals(loginType))){
      out.write("\r\n    window.history.forward(1);\r\n  ");
}
      out.write("\r\n  \r\nfunction doWorkflow(url){\r\n    ");
if("wap".equals(loginType)){
      out.write("\r\n     window.parent.parent.location.href = url;\r\n    ");
}else{
      out.write("\r\n       location.replace(url);\r\n    ");
}
      out.write("\r\n   \r\n}\r\n</script>");
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
