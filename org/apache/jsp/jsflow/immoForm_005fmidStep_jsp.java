/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:07:42 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class immoForm_005fmidStep_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

if(request.getParameter("noProc") == null){
      out.write("\r\n<input type=\"hidden\" name=\"tableId\" value=\"");
      out.print(request.getParameter("table"));
      out.write("\">\r\n<input type=\"hidden\" name=\"recordId\" value=\"");
      out.print(request.getParameter("record"));
      out.write("\">\r\n");
if(!"-1".equals(request.getParameter("workStatus"))){
      out.write("\r\n<input type=\"hidden\" name=\"activityId\" value=\"");
      out.print(request.getParameter("activity"));
      out.write("\">\r\n");
}
      out.write("\r\n<input type=\"hidden\" name=\"curActivityId\" value=\"");
      out.print(request.getParameter("activity"));
      out.write("\">\r\n<input type=\"hidden\" name=\"curActivityName\" value=\"");
      out.print(request.getParameter("activityName"));
      out.write("\">\r\n<input type=\"hidden\" name=\"stepCount\" value=\"");
      out.print(request.getParameter("stepCount"));
      out.write("\">\r\n<input type=\"hidden\" name=\"isStandForWork\" value=\"");
      out.print(request.getParameter("isStandForWork"));
      out.write("\">\r\n<input type=\"hidden\" name=\"standForUserId\" value=\"");
      out.print(request.getParameter("standForUserId"));
      out.write("\">\r\n<input type=\"hidden\" name=\"workId\" value=\"");
      out.print(request.getParameter("work"));
      out.write("\">\r\n<input type=\"hidden\" name=\"standForUserName\" value=\"");
      out.print(request.getParameter("standForUserName"));
      out.write("\">\r\n<input type=\"hidden\" name=\"submitPersonId\" value=\"");
      out.print(request.getParameter("submitPersonId"));
      out.write("\">\r\n<input type=\"hidden\" name=\"processName\" value=\"");
      out.print(request.getParameter("processName"));
      out.write("\">\r\n<input type=\"hidden\" name=\"submitPerson\" value=\"");
      out.print(request.getParameter("submitPerson"));
      out.write("\">\r\n<input type=\"hidden\" name=\"processId\" value=\"");
      out.print(request.getParameter("processId"));
      out.write("\">\r\n<input type=\"hidden\" name=\"fileType\" value=\"");
      out.print(request.getParameter("workFileType"));
      out.write("\">\r\n");

com.js.oa.jsflow.util.ProcessSubmit processSubmit = new com.js.oa.jsflow.util.ProcessSubmit();
String button = processSubmit.getButton(request);
String comment = processSubmit.getComment(request);

      out.write("\r\n<table align=\"center\" width=\"100%\">\r\n    <tr><td>&nbsp;</td></tr>\r\n    <tr valign=\"top\">\r\n    <td colspan=\"10\" align=\"center\">\r\n\t<table width=\"100%\" border=\"0\">");
      out.print(comment);
      out.write("</table>\r\n    </td>\r\n    </tr>\r\n    ");
if("-1".equals(request.getParameter("workStatus")) && !"Éè±¸ÉêÇë".equals(request.getParameter("workFileType")) && !"µµ°¸½èÔÄ".equals(request.getParameter("workFileType"))){
	String processId = request.getParameter("processId");
	String nextStep = new com.js.oa.jsflow.util.ProcessStep().firstStep(processId, 1, request);
        
      out.write("\r\n        <tr>");
      out.print(nextStep);
      out.write("</tr>\r\n        ");

    }
    
      out.write("\r\n    <tr valign=\"top\" align=\"center\">\r\n        <td colspan=\"4\">\r\n            ");
if("-1".equals(request.getParameter("workStatus")) && ("Éè±¸ÉêÇë".equals(request.getParameter("workFileType")) || "µµ°¸½èÔÄ".equals(request.getParameter("workFileType")))){

            }else{
                out.println(button);
            }
      out.write("\r\n        </td>\r\n    </tr>\r\n    <tr valign=\"top\" align=\"center\">\r\n    \t<td height=\"40\"></td>\r\n    </tr>\r\n    ");
if(!"-1".equals(request.getParameter("workStatus"))){
        /*
        */
        
      out.write("\r\n    <tr>\r\n        <td  align=\"center\" colspan=\"6\"  valign=\"middle\">\r\n            <table>\r\n                <tr>\r\n                    <td>\r\n                        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n                        \r\n                        <tr>\r\n                            <td colspan=\"2\" align=\"center\">\r\n                                <iframe width=\"80%\" src=\"/jsoa/jsflow/jsflow_view.jsp?processId=");
      out.print(request.getParameter("processId"));
      out.write("&table=");
      out.print(request.getParameter("table"));
      out.write("&record=");
      out.print(request.getParameter("record"));
      out.write("&CurActivityID=");
      out.print(request.getParameter("activity"));
      out.write("&ActivityIDStr=");
      out.print(request.getAttribute("ActivityIDStr"));
      out.write("\"></iframe>\r\n                            </td>\r\n                        </tr>\r\n                        \r\n                        </table>\r\n                    </td>\r\n                </tr>\r\n            </table>\r\n        </td>\r\n    </tr>\r\n    ");
}
      out.write("\r\n\r\n</table>\r\n<script language=\"javascript\">\r\n<!--\r\nfunction beforeProcSubmit(){\r\n    var canSubmit = false;\r\n    if(document.all.randomProcUserName){\r\n        if(textIsEmpty(document.all.randomProcUserName)){\r\n            alert(\"ÇëÑ¡ÔñËæ»úÁ÷³Ì°ìÀíÈË£¡\");\r\n            canSubmit = false;\r\n        }else{\r\n            canSubmit = true;\r\n        }\r\n    }else{\r\n        canSubmit = true;\r\n    }\r\n    if(document.all.userType){\r\n    \tif(document.all.userType.value == 1){\r\n              if(document.all.operProcUserName.value.length == 0){\r\n                  alert(\"ÇëÑ¡Ôñ°ìÀíÈË£¡\");\r\n                  canSubmit = false;\r\n              }else{\r\n                  canSubmit = true;\r\n              }\r\n         }\r\n    }\r\n    //±íÊ¾ÓÐ´ÓºòÑ¡ÈËÔ±ÖÐÖ¸¶¨°ìÀíÈË\r\n    if(document.all.candidateUser){\r\n        var j = 0;\r\n        var len = document.all.candidateUser.options.length;\r\n        for(var i = 0; i < len; i ++){\r\n            if(document.all.candidateUser.options[i].selected == true){\r\n                j ++;\r\n                canSubmit = true;\r\n");
      out.write("                break;\r\n            }\r\n        }\r\n        if(j == 0){\r\n            alert(\"ÇëÑ¡Ôñ°ìÀíÈË£¡\");\r\n            canSubmit = false;\r\n        }\r\n    }\r\n}\r\n\r\nfunction offiDict(userId, textAreaName){\r\n    JSMainWinOpen('jsflow/workflow_offiDict.jsp?userId=' + userId + '&textAreaName=' + textAreaName,'','menubar=0,scrollbars=yes,locations=0,width=400,height=200,resizable=yes');\r\n}\r\n//-->\r\n</script>\r\n");
}
      out.write('\r');
      out.write('\n');
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
