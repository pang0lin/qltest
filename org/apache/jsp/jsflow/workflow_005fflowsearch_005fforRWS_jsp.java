/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:06:39 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class workflow_005fflowsearch_005fforRWS_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html; charset=GBK");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			"/public/jsp/error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<link rel=stylesheet type=\"text/css\" href=\"js/cssmain.css\">\r\n<title>无标题文档</title>\r\n<STYLE>\r\nDIV.clsDivStyle {\r\n\tOVERFLOW: auto\r\n}\r\n</STYLE>\r\n<link href=\"skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"js/js.js\" language=\"javascript\"></script>\r\n</head>\r\n\r\n<body  class=\"FlowBody\">\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td>\r\n<br/>\r\n<table WIDTH=\"100%\" BORDER=\"0\" CELLPADDING=\"0\" CELLSPACING=\"1\">\r\n\r\n  <tr>\r\n    <td VALIGN=\"top\" class=main>\r\n      <table WIDTH=\"99%\" BORDER=\"0\" ALIGN=\"center\" CELLPADDING=\"1\" CELLSPACING=\"0\">\r\n        <tr>\r\n          <td HEIGHT=\"350\" VALIGN=\"top\">\r\n\t\t\t  ");

				  java.util.List processList = (java.util.List) request.getAttribute("processList");
				  java.util.ArrayList packageList = (java.util.ArrayList) request.getAttribute("packageList");
				  Object[] packageObj = null;
				  Object[] processObj = null;
				  for(int i = 0; i < packageList.size(); i ++){
					  packageObj = (Object[]) packageList.get(i);
              
      out.write("\r\n              <table WIDTH=\"95%\" BORDER=\"0\" ALIGN=\"center\" CELLPADDING=\"0\" CELLSPACING=\"1\">\r\n                 <tr>\r\n                    <td HEIGHT=\"24\" COLSPAN=\"2\"><!--<strong><img SRC=\"images/tree_add.gif\" WIDTH=\"16\" HEIGHT=\"16\"></strong>--><b>");
      out.print(packageObj[1]);
      out.write("</b></td>\r\n                 </tr>\r\n                 <tr>\r\n\r\n\t\t\t\t\t\t");

						int kk = 0;	
						for(int j = 0; j < processList.size(); j ++){
                                    processObj = (Object[]) processList.get(j);
                                    if((packageObj[0].toString()).equals(processObj[0].toString())){
										if(kk%3==0){
											out.println("<tr>");
										}
                        
      out.write("\r\n\t\t\t\t\t<td VALIGN=\"top\" HEIGHT=\"22\" width=\"33%\">\r\n                      <div STYLE=\"padding-left:15px;float:left;word-break:keep-all;\">\r\n                                <div style=\"float:left;padding-right:5px;padding-top:1px;\"><img src=\"/jsoa/img/liuchen.gif\"></div><div style=\"float:left;\"><a href=\"javascript:openList('");
      out.print(processObj[2]);
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(processObj[4]);
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(processObj[3]);
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(processObj[5]);
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(processObj[6]);
      out.write("');\">");
      out.print(processObj[3]);
      out.write("</a></div>\r\n                      </div>\r\n\t\t\t\t\t  </td> \r\n\t\t\t\t\t\t");

							if(kk%3==2){
								out.println("</tr>");
							}
							kk++;
                            }

                          }
						  if((kk-1)%3<2){
								out.println("<td  width='33%'>&nbsp;</td></tr>");
						  }
						
      out.write("\r\n               </table>               \r\n               <div><hr style=\"border:1px solid #cccccc;height:1px;width:95%;\"></div>\r\n\t\t\t  ");

              }
              
      out.write("\t                        \r\n               <!-- 工作流程 -->   \r\n               <table WIDTH=\"95%\" BORDER=\"0\" ALIGN=\"center\" CELLPADDING=\"0\" CELLSPACING=\"1\">\r\n                 <tr>\r\n                    <td HEIGHT=\"24\" COLSPAN=\"2\"><!--<strong><img SRC=\"images/tree_add.gif\" WIDTH=\"16\" HEIGHT=\"16\"></strong>--><b>公文</b></td>\r\n                 </tr>\r\n                 <tr>\r\n                    <td VALIGN=\"top\" HEIGHT=\"22\" width=\"33%\">\r\n                      <div STYLE=\"padding-left:15px;float:left;word-break:keep-all;\">\r\n                                <div style=\"float:left;padding-right:5px;padding-top:1px;\"><img src=\"/jsoa/img/liuchen.gif\"></div><div style=\"float:left;\"><a href=\"javascript:location.href='/jsoa/GovSendFileForRWSAction.do?action=list&status=");
      out.print(request.getAttribute("status"));
      out.write("';\">发文</a></div>\r\n                      </div>\r\n\t\t\t\t\t</td> \r\n\t\t\t\t\t<td VALIGN=\"top\" HEIGHT=\"22\" width=\"33%\">\r\n                      <div STYLE=\"padding-left:15px;float:left;word-break:keep-all;\">\r\n                                <div style=\"float:left;padding-right:5px;padding-top:1px;\"><img src=\"/jsoa/img/liuchen.gif\"></div><div style=\"float:left;\"><a href=\"javascript:location.href='/jsoa/GovReceiveFileForRWSAction.do?action=list&status=");
      out.print(request.getAttribute("status"));
      out.write("';\">收文</a></div>\r\n                      </div>\r\n\t\t\t\t\t</td> \r\n\t\t\t\t\t<td>&nbsp;</td>\r\n                 </tr>\r\n               </table>  \r\n                <div><hr style=\"border:1px solid #cccccc;height:1px;width:95%;\"></div>    \r\n                <table WIDTH=\"95%\" BORDER=\"0\" ALIGN=\"center\" CELLPADDING=\"0\" CELLSPACING=\"1\">\r\n                 <tr>\r\n                    <td HEIGHT=\"24\" COLSPAN=\"2\"><b>知识管理</b></td>\r\n                 </tr>\r\n                 <tr>\r\n                    <td VALIGN=\"top\" HEIGHT=\"22\" width=\"100%\">\r\n                     ");
      out.write("\r\n                      <table WIDTH=\"95%\" BORDER=\"0\" ALIGN=\"center\" CELLPADDING=\"0\" CELLSPACING=\"1\">\r\n                   <tr>\r\n\r\n\t\t\t\t\t\t");

						String[][] infoChannelList = (String[][]) request.getAttribute("infoChannelList");
						int kk = 0;	
						for(int j = 0; j < infoChannelList.length; j ++){
                                    String[] infoChannel = infoChannelList[j];
										if(kk%3==0){
											out.println("<tr>");
										}
                        
      out.write("\r\n\t\t\t\t\t<td VALIGN=\"top\" HEIGHT=\"22\" width=\"33%\">\r\n                      <div STYLE=\"padding-left:15px;float:left;word-break:keep-all;\">\r\n                                <div style=\"float:left;padding-right:5px;padding-top:1px;\"><img src=\"/jsoa/img/liuchen.gif\"></div><div style=\"float:left;\"><a href=\"javascript:location.href='/jsoa/InfoListForRWSAction.do?type=all&channelType=0&userChannelName=知识管理&userDefine=0&gdzt=");
      out.print(request.getAttribute("status"));
      out.write("&channelId=");
      out.print(infoChannel[0]);
      out.write("';\">");
      out.print(infoChannel[1]);
      out.write("</a></div>\r\n                      </div>\r\n\t\t\t\t\t  </td> \r\n\t\t\t\t\t\t");

							if(kk%3==2){
								out.println("</tr>");
							}
							kk++;
                        }

						
      out.write("\r\n               </table>  \r\n\t\t\t\t\t</td> \r\n\t\t\t\t\t<td VALIGN=\"top\" HEIGHT=\"22\" width=\"33%\">\r\n                      &nbsp;\r\n\t\t\t\t\t</td> \r\n\t\t\t\t\t<td>&nbsp;</td>\r\n                 </tr>\r\n               </table>  \r\n                <div><hr style=\"border:1px solid #cccccc;height:1px;width:95%;\"></div> \r\n \t\t  </td>\r\n        </tr>\r\n      </table>\r\n    </td>\r\n  </tr>\r\n</table>\r\n</td>\r\n</tr>\r\n</table>\r\n</body>\r\n<script language=\"javascript\">\r\n<!--\r\nfunction openList(processId,tableId,processName,processType,isEdit){\r\n    window.location.href = \"/jsoa/WorkFlowDossierForRWSAction.do?type=queryData&processId=\"+processId+\"&tableId=\"+tableId+\"&processName=\"+processName+\"&processType=\"+processType+\"&queryType=");
      out.print(request.getParameter("queryType"));
      out.write("&isEdit=\"+isEdit+\"&status=");
      out.print(request.getAttribute("status"));
      out.write("\";\r\n\r\n}\r\n//-->\r\n</script>\r\n</html>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
