/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:02:32 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.webmail;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class active_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n<STYLE>\r\n  body {\r\n  \tmargin: 0px\r\n  }\r\n\r\n  #Loading {\r\n\t    position: absolute;\r\n\t    text-align:left;\r\n\t    z-index: 10;\r\n\t    background-color: #FFFFFF;\r\n\t\tborder-top-width: thin;\r\n\t\tborder-right-width: thin;\r\n\t\tborder-bottom-width: thin;\r\n\t\tborder-left-width: thin;\r\n\t\tborder-top-style: outset;\r\n\t\tborder-right-style: outset;\r\n\t\tborder-bottom-style: outset;\r\n\t\tborder-left-style: outset;\r\n\t\tborder-top-color: #CCCCCC;\r\n\t\tborder-right-color: #CCCCCC;\r\n\t\tborder-bottom-color: #CCCCCC;\r\n\t\tborder-left-color: #CCCCCC;\r\n\t\tpadding:5px;\r\n\t\r\n  }\r\n  #Loading  {filter:progid:DXImageTransform.Microsoft.Shadow (Color=#333333,Direction=120,strength=5)} \r\n  \r\n  A.dian:link,A.dian:visited{\r\n\ttext-decoration:none;\r\n\tfloat:left;\r\n\twidth:98%;\r\n\tpadding:5px 10px;\r\n\tfont-size:13px;\r\n\tcolor:#000000;\r\n}\r\n\r\nA.dian:hover,A.dian:active{\r\n\tfloat:left;\r\n\tpadding:5px 10px;\r\n\twidth:100px;\r\n\ttext-decoration:none;\r\n\tbackground-color:#e2e2e2;\r\n\tfont-size:13px;\r\n\tcolor:#000000;\r\n}\r\n</STYLE>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n\r\nvar OverH,OverW,lefts,tops;\r\n");
      out.write("var mx,my;\r\nvar nameTemp,state,idTemp;\r\n  \r\nfunction $(){return document.getElementById?document.getElementById(arguments[0]):eval(arguments[0]);}\r\n\r\nfunction OpenDiv() {\r\n       $(\"Loading\").innerHTML=\"\";\r\n       OverH=40;\r\n\t   OverW=150;\r\n       lefts=document.getElementById(\"button1\").offsetLeft+6;\r\n       tops=document.getElementById(\"button1\").offsetTop+document.getElementById(\"button1\").offsetHeight+10;\r\n       window.setTimeout(\"OpenNow()\",10);\r\n}\r\n\r\nfunction OpenNow() {\r\n   ");
if(folderList!=null&&folderList.size()>0){
      out.write("\r\n       $(\"Loading\").style.width=OverW+\"px\";\r\n       $(\"Loading\").style.left=lefts+\"px\";\r\n       $(\"Loading\").style.height=OverH+\"px\";\r\n       $(\"Loading\").style.top=tops+\"px\"\r\n       $(\"Loading\").style.display='';\r\n       $(\"Loading\").align=\"center\";\r\n       selectPOPMenu();\r\n   ");
}else{
      out.write("    \r\n       alert('请设置web邮件账户信息！');\r\n       jumpMain('/jsoa/webMailAcc.do?method=goCreateMailAcc');\r\n   ");
}
      out.write("\r\n}\r\n\r\nfunction selectPOPMenu(){\r\n      defalut();\r\n  \r\n}\r\n\r\nfunction defalut(){\r\n      ");

         if(folderList!=null&&folderList.size()>0){
        	 
      out.write("\r\n        \t$(\"Loading\").style.height=");
      out.print(folderList.size());
      out.write("*30+\"px\";\r\n        \t");

           for(int i=0;i<folderList.size();i++){
           WebMailAcc temp = (WebMailAcc) folderList.get(i);
       
      out.write("\r\n      var mail");
      out.print(i);
      out.write("=document.createElement(\"a\");\r\n      mail");
      out.print(i);
      out.write(".href=\"#\";\r\n      mail");
      out.print(i);
      out.write(".title=\"收取<");
      out.print(temp.getMailAccUser());
      out.write(">账户邮件\";\r\n      mail");
      out.print(i);
      out.write(".className=\"dian\";\r\n      mail");
      out.print(i);
      out.write(".onclick = function(){\r\n          crawlEmail('");
      out.print(temp.getMailAccId());
      out.write("');\r\n      }\r\n      var span_mail");
      out.print(i);
      out.write("=document.createElement(\"div\");\r\n      span_mail");
      out.print(i);
      out.write(".innerHTML=\"");
      out.print(temp.getMailAccUser());
      out.write("\";\r\n      span_mail");
      out.print(i);
      out.write(".style.cursor=\"pointer\";\r\n      span_mail");
      out.print(i);
      out.write(".style.styleFloat=\"left\";\r\n      mail");
      out.print(i);
      out.write(".appendChild(span_mail");
      out.print(i);
      out.write(");\r\n      $(\"Loading\").appendChild(mail");
      out.print(i);
      out.write(");\r\n      $(\"Loading\").appendChild(document.createElement(\"br\"));\r\n      \r\n      ");
}}else{
      out.write("\r\n      ");
}
      out.write("\r\n     \r\n}\r\n\r\nfunction yinc(){\r\n  document.getElementById(\"Loading\").style.display='none';\r\n}  \r\n  \r\nfunction mouseMove(ev){ \r\n    ev= ev || window.event; \r\n    var mousePos = mouseCoords(ev); \r\n     mx = mousePos.x; \r\n     my = mousePos.y; \r\n} \r\n\r\nfunction mouseCoords(ev){ \r\n  if(ev.pageX || ev.pageY){ \r\n   return {x:ev.pageX, y:ev.pageY}; \r\n } \r\n  return { \r\n     x:ev.clientX + document.body.scrollLeft - document.body.clientLeft, \r\n     y:ev.clientY + document.body.scrollTop     - document.body.clientTop \r\n   }; \r\n} \r\n\r\nfunction closeNow(){\r\n  if(my>(tops+OverH)||mx>(lefts+OverW)||my<tops||mx<lefts){\r\n       $(\"Loading\").style.display='none';\r\n      } \r\n      window.setTimeout(\"closeNow()\",6000);\r\n}\r\ndocument.onmousemove = mouseMove; \r\n\r\n</SCRIPT>\r\n\r\n\r\n");
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
