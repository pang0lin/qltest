/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:05:12 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.doc.doc;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.lang.Resource;
import java.util.List;
import com.js.system.manager.service.ManagerService;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.service.WorkFlowBD;

public final class workflow_005flist_005fsendfile_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("com.js.lang.Resource");
    _jspx_imports_classes.add("java.util.List");
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

String localhi = session.getAttribute("org.apache.struts.action.LOCALE").toString();

String userId = session.getAttribute("userId").toString();
String orgIdString = session.getAttribute("orgIdString").toString();


      out.write("\r\n<html>\r\n<head>\r\n<link HREF=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" REL=\"stylesheet\" TYPE=\"text/css\" />\r\n<script SRC=\"/jsoa/js/js.js\" LANGUAGE=\"javascript\"></script>\r\n<title>???????????????</title>\r\n<STYLE>\r\nDIV.clsDivStyle {\r\n\tOVERFLOW: auto\r\n}\r\n</STYLE>\r\n\r\n ");
	
    List processList=null;


    //???????????????????????????
    WorkFlowBD wfbd = new WorkFlowBD();

    //???????????????????????????
    ProcessBD procbd = new ProcessBD();   

    Object tmp;   
    
    tmp = procbd.getUserProcess(userId, orgIdString, "2");
    if (null != tmp) {
      processList = (List) tmp;
    }

      out.write("\r\n</head>\r\n\r\n<body class=\"FlowBody\">\r\n\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td>\r\n<br/>\r\n<table WIDTH=\"100%\" BORDER=\"0\" CELLPADDING=\"0\" CELLSPACING=\"1\">\r\n  <tr>\r\n    <td VALIGN=\"top\" class=main>\r\n      <table WIDTH=\"99%\" BORDER=\"0\" ALIGN=\"center\" CELLPADDING=\"1\" CELLSPACING=\"0\">\r\n        <tr>\r\n          <td HEIGHT=\"350\" VALIGN=\"top\">\r\n\t\t\t  ");
              
              int k = 0;
              
              Object[] processObj = null;
             
              
      out.write("\r\n              <table WIDTH=\"95%\" BORDER=\"0\" ALIGN=\"center\" CELLPADDING=\"0\" CELLSPACING=\"1\">\r\n                 <tr>\r\n                    <td HEIGHT=\"24\" COLSPAN=\"2\" ><b>????????????</b></td>\r\n                 </tr>\r\n\t\t\t\t\t ");

						int kk = 0;
						String processId,processName,processType,tableId,remindField;
						for(int j = 0; j < processList.size(); j ++){
								processObj = (Object[]) processList.get(j);
								
							   processId = String.valueOf(processObj[2]);
							   processName = String.valueOf(processObj[3]);
							   processType = String.valueOf(processObj[5]);
							   tableId = String.valueOf(processObj[4]);
							   remindField = processObj[6]==null?"":String.valueOf(processObj[6]);
								
									if(kk%3==0){
										out.println("<tr>");
									}
					  
      out.write("\r\n                    <td VALIGN=\"top\" height=\"22\" width=\"33%\">\r\n\r\n\t\t\t\t\t\t\r\n                      <div STYLE=\"padding-left:15px;float:left;word-break:keep-all;position:static\">\r\n                                <!-- <font size=3>??</font> -->\r\n\t\t\t\t\t\t\t\t<img SRC=\"/jsoa/images/detail.gif\" ALT=\"????????????\" STYLE=\"cursor:'hand'\" onClick=\"MM_openBrWindow('/jsoa/jsflow/workflow_description.jsp?processId=");
      out.print(processObj[2]);
      out.write("','','TOP=0,LEFT=0,scrollbars=yes,resizable=yes,width=600,height=400')\">\r\n\t\t\t\t\t\t\t\t<a HREF=\"javascript:void(0);\" onclick=\"JSMainWinOpen('/jsoa/GovSendFileAction.do?action=see&processId=");
      out.print(processId);
      out.write("&processName=");
      out.print(processName);
      out.write("&processType=");
      out.print(processType);
      out.write("&tableId=");
      out.print(tableId);
      out.write("&remindField=");
      out.print(remindField);
      out.write("','','');\">");
      out.print(processObj[3]);
      out.write("</a>\r\n                      </div>\r\n\t\t\t\t\t\t\r\n                    </td> \r\n\t\t\t\t\t");

						
						if(kk%3==2){
							out.println("</tr>");
						}
						kk++;
							
						  }
					if((kk-1)%3<2){
						out.println("<td  width='33%'>&nbsp;</td></tr>");
				    }
					
      out.write("\r\n               </table>               \r\n\t\t\t \t                        \r\n                        \r\n \t\t  </td>\r\n        </tr>\r\n      </table>\r\n    </td>\r\n  </tr>\r\n</table>\r\n</td>\r\n</tr>\r\n</table>\r\n</body>\r\n</html>\r\n<script LANGUAGE=\"javascript\">\t\r\nfunction MM_openBrWindow(theURL,winName,features) { //v2.0\r\n  JSMainWinOpen(theURL,winName,features);\r\n}\r\n</script>\r\n\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
