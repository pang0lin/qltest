/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:46:47 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.wap2.workitem;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.wap.util.WapUtil;

public final class new_005fcooperate_005f3g_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("com.js.wap.util.WapUtil");
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

      out.write("\r\n\r\n");

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

      out.write("\r\n<HTML xmlns=\"http://www.w3.org/1999/xhtml\">\r\n<HEAD>\r\n<TITLE>新建协同</TITLE>\r\n<META content=\"text/html; charset=UTF-8\" http-equiv=Content-Type>\r\n<META name=viewport content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\">\r\n<META name=apple-touch-fullscreen content=YES>\r\n<META name=apple-mobile-web-app-capable content=no>\r\n<LINK rel=apple-touch-icon href=\"");
      out.print(path);
      out.write("/wap2/images/iphone.jpg\">\r\n<LINK rel=stylesheet type=text/css href=\"");
      out.print(path);
      out.write("/wap2/css/main_3g.css\">\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path);
      out.write("/wap2/js/cookie.js\"></SCRIPT>\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path);
      out.write("/wap2/js/util.js\"></SCRIPT>\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path);
      out.write("/wap2/js/frost.js\"></SCRIPT>\r\n<META name=GENERATOR content=\"MSHTML 8.00.6001.19154\">\r\n</HEAD>\r\n\r\n");
 
request.setCharacterEncoding("UTF-8");
String name=(String)request.getAttribute("name");
String users="";
String[] users_array=request.getParameterValues("users");
if(users_array!=null&&!users_array.equals("")){
for(int i=0;i<users_array.length;i++){
	users+=users_array[i]+",";
}
}

String userIds_re=request.getParameter("userIds")!=null&&!request.getParameter("userIds").equals("")&&!request.getParameter("userIds").equals("null")?request.getParameter("userIds"):"";//从页面传回的id
String userNames_re=request.getParameter("userNames")!=null&&!request.getParameter("userNames").equals("")&&!request.getParameter("userNames").equals("null")?request.getParameter("userNames"):"";//从页面传回的name
String userIds=userIds_re;
String userNames=userNames_re;
if(users!=null&&!users.equals("")){
String[] users_=users.split(",");
for(int i=0;i<users_.length;i++){
   userIds=userIds+users_[i].substring(0,users_[i].indexOf("$"))+",";
   userNames=userNames+users_[i].substring(users_[i].indexOf("$")+1)+",";
}
userIds=WapUtil.delRepeatUserId(userIds)+",";
userNames=WapUtil.delRepeatUserId(userNames)+",";
}
String title=request.getParameter("title")!=null&&!request.getParameter("title").equals("null")?request.getParameter("title"):"";
String level=request.getParameter("level")!=null&&!request.getParameter("level").equals("null")?request.getParameter("level"):"";
String relproject=request.getParameter("relproject")!=null&&!request.getParameter("relproject").equals("null")?request.getParameter("relproject"):"";
String content=request.getParameter("content")!=null&&!request.getParameter("content").equals("null")?request.getParameter("content"):"";

com.js.oa.relproject.bean.RelProjectBean rpBean=new com.js.oa.relproject.bean.RelProjectBean();
java.util.List projectList=rpBean.getActiveProject(session.getAttribute(WapUtil.EMP_ID).toString(),session.getAttribute(WapUtil.EMP_ORG_ID).toString(),session.getAttribute(WapUtil.ORG_ID_STRING).toString());

      out.write("\r\n<body>\r\n\t<div class=\"main\">\r\n\t\t<div id=\"top\">\r\n\t\t\t<span id=\"lp2\"><div class=\"btn_3\"><a href=\"");
      out.print(path);
      out.write("/WapCoopAction.do?action=listseverals\">返回</a></div></span>\r\n\t\t\t<span id=\"title2\"></span>\r\n\t\t</div>\r\n\t\t\r\n\t\t\r\n<form name=\"formSubmit\" action=\"");
      out.print(path);
      out.write("/wap2/workitem/cooperate_add_3g.jsp\" method=\"post\">\r\n<input type=hidden name=\"userIds\" value=\"");
      out.print(userIds );
      out.write("\" />\r\n<input type=hidden name=\"userNames\" value=\"");
      out.print(userNames );
      out.write("\" />\r\n\t<div class=\"f_1\">新建协同</div>\r\n\t<div class=\"box_2\">\r\n\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">标 题：</div>\r\n\t\t\t\t<div class=\"t2\"><INPUT class=\"input\" id=\"titles\" maxlength=\"50\" value=\"");
      out.print(title );
      out.write("\" name=title style=\"width:100%\"></div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t</div>\r\n\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">重要程度：</div>\r\n\t\t\t\t<div class=\"t2\"><SELECT class=\"input\" id=\"level\" name=\"level\" style=\"width:100%\">\r\n\t\t            <option value=\"10\" ");
if(level.equals("10")){out.print("selected");} 
      out.write(">一般</option>\r\n\t\t            <option value=\"20\" ");
if(level.equals("20")){out.print("selected");} 
      out.write(">重要</option>\r\n\t\t            <option value=\"30\" ");
if(level.equals("30")){out.print("selected");} 
      out.write(">非常重要</option>\r\n\t\t\t\t</SELECT></div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t</div>\r\n\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">相关项目：</div>\r\n\t\t\t\t<div class=\"t2\"><SELECT class=\"input\" id=\"relproject\" name=\"relproject\" style=\"width:100%\">\r\n\t\t\t\t\t  <option value=\"-1\"></option>\r\n                     ");

			         if(projectList!=null && projectList.size()>0){
			        	 Object[] obj;
			        	 for(int i=0;i<projectList.size();i++){
			        		 obj=(Object[])projectList.get(i);
			         
      out.write("\r\n\t\t             <option value=\"");
      out.print(obj[0] );
      out.write('"');
      out.write(' ');
if(obj[0].toString().equals(relproject)){out.print("selected");} 
      out.write(' ');
      out.write('>');
      out.print(obj[1] );
      out.write("</option>\r\n\t\t\t         ");
}
			         } 
		   
      out.write("\r\n\t\t\t\t  </SELECT></div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t</div>\r\n\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">内 容：</div>\r\n\t\t\t\t<div class=\"t2\"><TEXTAREA class=\"input\" id=\"content\" name=\"content\" style=\"width:100%\" maxlength=\"1000\">");
      out.print(content );
      out.write("</TEXTAREA></div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t</div>\r\n\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">发送至：</div>\r\n\t\t\t\t<div class=\"t2\"><INPUT  class=\"input\" onclick=\"selectUsersubmit();\" value=\"");
      out.print(userNames );
      out.write("\" readonly=true style=\"width:100%\"  name=\"userNames\"><br/>\r\n\t\t\t\t<!-- <input type=\"button\" class=\"button2\" style=\"cursor:pointer\" value=\"选择\" onclick=\"javascript:selectUsersubmit();\"/>  --> </div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t</div>\r\n\t<div class=\"list_2\" align=\"center\">\r\n\t<input type=\"button\" class=\"button2\" value=\"发送\" onclick=\"javascript:document.formSubmit.submit();\" />\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t</div>\r\n\t</div>\r\n\t</form>\r\n</div>\r\n<form name=\"selectUserForm\" action=\"");
      out.print(path );
      out.write("/wap2/workitem/org_list_3g.jsp\" method=\"post\">\r\n<input name=\"userId\" value=\"");
      out.print(WapUtil.EMP_ID);
      out.write("\" type=\"hidden\"/>\r\n<input name=\"userIds\" value=\"");
      out.print(userIds );
      out.write("\" type=\"hidden\" />\r\n<input name=\"userNames\" value=\"");
      out.print(userNames );
      out.write("\" type=\"hidden\" />\r\n<input id=\"title_user\" name=\"title\" type=\"hidden\" value=\"\"/>\r\n<input id=\"level_user\" name=\"level\" type=\"hidden\" value=\"\"/>\r\n<input id=\"relproject_user\" name=\"relproject\" type=\"hidden\" value=\"\"/>\r\n<input id=\"content_user\" name=\"content\" type=\"hidden\" value=\"\"/>\r\n</form>\r\n<script>\r\nfunction selectUsersubmit(){\r\ndocument.getElementById(\"title_user\").value=document.getElementById(\"titles\").value;\r\ndocument.getElementById(\"level_user\").value=document.getElementById(\"level\").value;\r\ndocument.getElementById(\"relproject_user\").value=document.getElementById(\"relproject\").value;\r\ndocument.getElementById(\"content_user\").value=document.getElementById(\"content\").value;\r\ndocument.selectUserForm.submit();\r\n}\r\n</script>\r\n</body>\r\n</html>\r\n\r\n");
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
