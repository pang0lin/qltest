/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:09:13 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.cooperate;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class editRight_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\r');
      out.write('\n');

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

String seNodeId=request.getParameter("selNodeId");
String nodeRight=request.getParameter("nodeRight");

      out.write("\r\n\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\" />\r\n<title>节点权限</title>\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n</head>\r\n\r\n<body scroll=\"no\">\r\n<table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" id=\"pup_main\">\r\n \r\n  <tr>\r\n    <td align=\"center\" valign=\"top\">\r\n\t\t<div id=\"pup_content\">\r\n\t\t  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n\t\t    <tr>\r\n\t\t      <td width=\"20%\" nowrap valign=\"top\" style=\"padding-top:5px;\">\r\n\t\t      节点权限：\r\n\t\t      </td>\r\n\t\t      <td width=\"80%\" valign=\"top\">\r\n\t\t        <table width=\"100%\">\r\n\t\t          \r\n\t\t          <tr>\r\n\t\t            <td>修改正文</td>\r\n\t\t            <td><input type=\"radio\" name=\"modifyBody\" value=\"1\" ");
if(nodeRight.indexOf("modifyBody_")>=0){out.print("checked");}
      out.write(">允许&nbsp;&nbsp;<input type=\"radio\" name=\"modifyBody\" value=\"0\" ");
if(nodeRight.indexOf("modifyBody_")<0){out.print("checked");}
      out.write(">不允许</td>\r\n\t\t          </tr>\r\n\t\t          <tr valign=\"top\">\r\n\t\t            <td>补充正文</td>\r\n\t\t            <td><input type=\"radio\" name=\"appendBody\" value=\"1\" ");
if(nodeRight.indexOf("appendBody_")>=0){out.print("checked");}
      out.write(">允许&nbsp;&nbsp;<input type=\"radio\" name=\"appendBody\" value=\"0\" ");
if(nodeRight.indexOf("appendBody_")<0){out.print("checked");}
      out.write(">不允许</td>\r\n\t\t          </tr>\r\n\t\t          <tr valign=\"top\">\r\n\t\t            <td widht=\"30%\">增加办理人</td>\r\n\t\t            <td width=\"70%\"><input type=\"radio\" name=\"appendNode\" value=\"1\" ");
if(nodeRight.indexOf("appendNode_")>=0){out.print("checked");}
      out.write(">允许&nbsp;&nbsp;<input type=\"radio\" name=\"appendNode\" value=\"0\" ");
if(nodeRight.indexOf("appendNode_")<0){out.print("checked");}
      out.write(">不允许</td>\r\n\t\t          </tr>\r\n\t\t          <tr>\r\n\t\t            <td>打印</td>\r\n\t\t            <td><input type=\"radio\" name=\"printDoc\" value=\"1\" ");
if(nodeRight.indexOf("printDoc_")>=0){out.print("checked");}
      out.write(">允许&nbsp;&nbsp;<input type=\"radio\" name=\"printDoc\" value=\"0\" ");
if(nodeRight.indexOf("printDoc_")<0){out.print("checked");}
      out.write(">不允许</td>\r\n\t\t          </tr>\r\n\t\t          <!-- tr>\r\n\t\t            <td>转发</td>\r\n\t\t            <td><input type=\"radio\" name=\"trans\" value=\"1\" ");
if(nodeRight.indexOf("trans_")>=0){out.print("checked");}
      out.write(">允许&nbsp;&nbsp;<input type=\"radio\" name=\"trans\" value=\"0\" ");
if(nodeRight.indexOf("trans_")<0){out.print("checked");}
      out.write(">不允许</td>\r\n\t\t          </tr \r\n\t\t          <tr>\r\n\t\t            <td>退回</td>\r\n\t\t            <td><input type=\"radio\" name=\"back\" value=\"1\" ");
if(nodeRight.indexOf("back_")>=0){out.print("checked");}
      out.write(">允许&nbsp;&nbsp;<input type=\"radio\" name=\"back\" value=\"0\" ");
if(nodeRight.indexOf("back_")<0){out.print("checked");}
      out.write(">不允许</td>\r\n\t\t          </tr>\r\n\t\t          -->\r\n\t\t          <tr>\r\n\t\t            <td>终止</td>\r\n\t\t            <td><input type=\"radio\" name=\"overBody\" value=\"1\" ");
if(nodeRight.indexOf("overBody_")>=0){out.print("checked");}
      out.write(">允许&nbsp;&nbsp;<input type=\"radio\" name=\"overBody\" value=\"0\" ");
if(nodeRight.indexOf("overBody_")<0){out.print("checked");}
      out.write(">不允许</td>\r\n\t\t          </tr>\r\n\t\t        </table>\r\n\t\t      </td>\r\n\t\t    </tr>\r\n            \r\n            <tr>\r\n\t\t\t    <td colspan=\"2\" height=\"40\" valign=\"bottom\" align=\"left\"><label>\r\n\t\t\t\t\t<input type=\"button\" name=\"button\" value=\"确定\" class=\"btnButton2font\" onclick=\"saveRight();\"/>\r\n\t\t\t\t\t<input type=\"button\" name=\"Cancel\" value=\"取消\" class=\"btnButton2font\" onclick=\"window.close();\" />\r\n\t\t\t    </label>\r\n\t\t\t    </td>\r\n\t\t\t  </tr>\r\n          </table>\r\n\t\t</div>\t</td>\r\n  </tr>\r\n  \r\n</table>\r\n</body>\r\n</html>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n<!--\r\nfunction saveRight(){\r\n var rightSTR=\"\";\r\n //修改正文\r\n if(document.all.modifyBody[0].checked){\r\n \trightSTR+=\"modifyBody_\";\r\n }\r\n //补充正文\r\n if(document.all.appendBody[0].checked){\r\n \trightSTR+=\"appendBody_\";\r\n }\r\n //增加办理人\r\n if(document.all.appendNode[0].checked){\r\n \trightSTR+=\"appendNode_\";\r\n }\r\n //打印\r\n if(document.all.printDoc[0].checked){\r\n \trightSTR+=\"printDoc_\";\r\n }\r\n /*\r\n //转发\r\n if(document.all.trans[0].checked){\r\n \trightSTR+=\"trans_\";\r\n }\r\n //退回\r\n if(document.all.back[0].checked){\r\n");
      out.write(" \trightSTR+=\"back_\";\r\n }\r\n */\r\n //终止\r\n if(document.all.overBody[0].checked){\r\n \trightSTR+=\"overBody_\";\r\n }\r\n opener.setNodeProperty('");
      out.print(seNodeId );
      out.write("',rightSTR);\r\n window.close();\r\n}\r\n//-->\r\n</SCRIPT>");
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