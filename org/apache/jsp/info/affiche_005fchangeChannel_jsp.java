/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:55:31 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.info;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.List;
import java.lang.Long;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.vo.*;
import com.js.system.manager.service.*;

public final class affiche_005fchangeChannel_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("com.js.oa.jsflow.vo");
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("com.js.system.manager.service");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.util.List");
    _jspx_imports_classes.add("java.lang.Long");
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
String nextStep = request.getAttribute("nextStep").toString();

      out.write('\r');
      out.write('\n');

  WorkFlowBD wfBD = new WorkFlowBD();
  ModuleVO moduleVO = new ModuleVO();
  moduleVO.setId(51);
  moduleVO.setFormType(0);
  AccessTableVO tableVO = (AccessTableVO) wfBD.getAccessTable(moduleVO).get(0);
 // String[] tmp = (String[]) wfBD.getModuleProc("4").get(0);
 String afficheReaderId="";
 String afficheReaderName="";
  
 if(request.getAttribute("afficheReaderId")!=null){
  afficheReaderId=request.getAttribute("afficheReaderId").toString();
 }

 if(request.getAttribute("afficheReaderName")!=null){
  afficheReaderName=request.getAttribute("afficheReaderName").toString();
 }

      out.write("\r\n\r\n\r\n<html>\r\n<script language=\"javascript\">\r\n\r\n");
if("noActivity".equals(nextStep)){
      out.write("\r\nalert(\"<bean:message bundle=\"information\" key=\"info.noprocess\" />\");\r\nwindow.close();\r\n\r\n");
}else{
	 String  tag=request.getAttribute("tagls").toString();
	 if(tag!=null&&tag.equals("a")){
	
      out.write("\r\n//parent.document.all.nextStepContent.innerHTML = \"");
      out.print(request.getAttribute("nextStep"));
      out.write("\"\r\n\r\nwindow.location.href=\"/jsoa/InformationAction.do?action=adds&channelType=");
      out.print(request.getAttribute("channelType").toString());
      out.write("&userDefine=");
      out.print(request.getAttribute("userDefine").toString());
      out.write("&newaddress=");
      out.print(request.getAttribute("newaddress").toString());
      out.write("&channelId=");
      out.print(request.getAttribute("channelId").toString());
      out.write("&processId=");
      out.print(request.getAttribute("processId").toString());
      out.write("&moduleId=\"+51+\"&remindField=&tableId=");
      out.print(tableVO.getId());
      out.write("&processName=??????????????????&processType=1&isAffiche=1&afficheReaderId=");
      out.print(afficheReaderId);
      out.write("&afficheReaderName=");
      out.print(afficheReaderName);
      out.write("\";\r\n\t\r\n");
}else{
      out.write("\r\n\r\n alert(\"?????????\");\r\n\r\n");
}}
      out.write("\r\n</script>\r\n</html>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
