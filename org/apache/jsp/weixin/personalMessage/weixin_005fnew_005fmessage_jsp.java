/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:58:40 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.weixin.personalMessage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.wap.util.WapUtil;

public final class weixin_005fnew_005fmessage_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n<!DOCTYPE html>\r\n");

String path = request.getContextPath();
String basePath = request.getContextPath();
request.setCharacterEncoding("UTF-8");

      out.write("\r\n<HTML>\r\n\t<HEAD>\r\n\t\t<TITLE>????????????</TITLE>\r\n\t\t<META content=\"text/html; charset=UTF-8\" http-equiv=Content-Type>\r\n\t\t<META name=viewport content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\">\r\n\t\t<META name=apple-touch-fullscreen content=YES>\r\n\t\t<META name=apple-mobile-web-app-capable content=no>\r\n\r\n\t\t");

		String loginType2017 = null==session.getAttribute("loginType") ? "" : session.getAttribute("loginType").toString();
		if(!"weixin".equals(loginType2017)){
		  out.print("<script type=\"text/javascript\">window.history.forward(1);</script>");
		}
		
      out.write("\r\n\t</HEAD>\r\n\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/wap2/commonImport.jsp", out, false);
      out.write("\r\n\t<body onload=\"setHeader('javascript:closeWindow();','????????????');\" class=\"wapcss\">\r\n\t\t<div class=\"form\">\r\n\t\t\t<form name=\"formSubmit\" action=\"\" method=\"post\">\r\n\t\t\t\t<input type=\"hidden\" id=\"userId\" name=\"userId\" value=\"");
      out.print(WapUtil.EMP_ID);
      out.write("\" />\r\n\t\t\t\t<input type=\"hidden\" id=\"toIDs\" name=\"toIDs\" />\r\n\t\t\t\t<input type=\"hidden\" id=\"userAccounts\" name=\"userAccounts\" />\r\n\t\t\t\t<input type=\"hidden\" id=\"userNames\" name=\"userNames\" />\r\n\t\t\t\t\t\r\n\t\t\t\t<div class=\"item\">\r\n           \t\t\t<div class=\"content\">\r\n\t\t                <textarea id=\"contents\" name=\"contents\" rows=\"6\" placeholder=\"?????????????????????\"></textarea>\r\n\t              \t</div>\r\n\t\t\t\t</div>\r\n\t\t\t\t\r\n\t\t\t\t<div class=\"item\">\r\n\t    \t\t\t<div class=\"title\">????????????</div>\r\n\t\t            <div class=\"userlist\">\r\n\t\t            \t<ul class=\"clearfix\" id=\"toPersonList\"></ul>\r\n\t\t\t        </div>\r\n\t\t\t\t</div>\r\n\t\t\t\t\r\n\t\t\t</form>\r\n\t\t</div>\r\n\t\t\r\n\t\t<!-- ?????? -->\r\n \t\t<div class=\"footer\">\r\n\t  \t\t<div class=\"buttons\">\r\n\t  \t\t\t<div class=\"button\" onclick=\"javascript:doSend(this);\">??????</div> \r\n\t  \t\t\t<div id=\"closeWindow\" class=\"button gray\" onclick=\"closeWindow();\">??????</div>\r\n\t \t\t</div>\r\n   \t\t</div>\r\n   \t\t\r\n\t\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/weixin/common/weixin_messageReminders.jsp", out, false);
      out.write("\r\n\t\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/weixin/workflow/eform/selectUser.jsp", out, false);
      out.write("\r\n\t\t<script type=\"text/javascript\">\r\n\t\t\r\n\t\tvar userContent = \"<li class=\\\"user-add\\\" onclick=\\\"showSelectUsers('toIDs', 'userNames', 'userAccounts', 1, 'user', 'orgPerson', '");
      out.print(session.getAttribute("browseRange"));
      out.write("', 'loadUser();', 'selList')\\\"></li><li class='user-remove' onclick='removeUsersubmit()'></li>\";\r\n\t\tfunction loadUser(){\r\n\t\t\tvar completePeopleHtml= \"\";\r\n\t\t\tvar ids = document.getElementById(\"toIDs\").value;\r\n\t\t    var userName = document.getElementById(\"userNames\").value;\r\n\t\t    var userAccounts = document.getElementById(\"userAccounts\").value;\r\n\r\n\t\t    if(\"\" != ids){\r\n\t\t    \tids = ids.substring(1, ids.length - 1);\r\n\t\t    \tuserName = userName.substring(0, userName.length - 1);\r\n\t\t    \tuserAccounts = userAccounts.substring(0, userAccounts.length - 1);\r\n\t\t\t    var userIdArray = ids.split(\"$$\");\r\n\t\t\t    var nameArray = userName.split(\",\");\r\n\t\t\t    var userAccountsArray = userAccounts.split(\",\");\r\n\t\t\t    //????????????\r\n\t\t\t    for(var i=0; i<userIdArray.length; i++){\r\n\t\t\t       completePeopleHtml += \"<li id='\" + userIdArray[i] + \"_readSelect'><div>\";\r\n\t\t\t       completePeopleHtml += \"<div style='position: absolute; top: 0px; right: 0px;'>\";\r\n\t\t\t       completePeopleHtml += \"<img style='cursor:pointer;display:none' id='\" + userIdArray[i] + \"'  border='0' src='/jsoa/images/delete.gif' onclick=javascript:removeUserSelect('$\" + userIdArray[i] + \"$','\" + nameArray[i] + \"','\" + userAccountsArray[i] + \"'); /></div>\";\r\n");
      out.write("\t\t\t       completePeopleHtml += \"<img style='cursor:pointer;width:30px;height:30px' id='\" + userIdArray[i] + \"_p' border='0' src='/jsoa/weixin/common/getUserAvatar.jsp?userId=\" + userAccountsArray[i] + \"' onDblClick=javascript:removeUserSelect(',\" + userIdArray[i] + \",','\" + nameArray[i] + \"','\" + userAccountsArray[i] + \"'); />\";\r\n\t\t\t       completePeopleHtml += \"<p class='name'>\" + nameArray[i] + \"</p>\";\r\n\t\t\t       completePeopleHtml += \"</div></li>\";\r\n\t\t\t    }\r\n\t\t    }\r\n\t\t    \r\n\t\t\tcompletePeopleHtml += userContent;\r\n\t\t\tdocument.getElementById(\"toPersonList\").innerHTML = completePeopleHtml;\r\n\t\t}\r\n\t\tloadUser();\r\n\t\t\r\n\t\t//?????????????????????\r\n\t\tfunction selectUsersubmit(){\r\n\t\t    document.formSubmit.action = \"");
      out.print(path );
      out.write("/weixin/personalMessage/weixin_org_list.jsp\";\r\n\t\t\tdocument.formSubmit.submit();\r\n\t\t}\r\n\t\t\r\n\t\tfunction doSend(obj){\r\n\t\t\tvar content = $.trim($(\"#contents\").val());\r\n\t\t\tif(null==content||\"\"==content){\r\n\t\t\t\tweixinMessageReminder(\"alert\", \"?????????\", \"???????????????????????????\")\r\n\t\t\t\treturn;\r\n\t\t\t}\r\n\t\t\tvar userNames=document.getElementById(\"userNames\");\r\n\t\t\tif(null==userNames.value||\"\"==userNames.value){\r\n\t\t\t\tweixinMessageReminder(\"alert\", \"?????????\", \"?????????????????????\")\r\n\t\t\t\treturn;\r\n\t\t\t}\r\n\t\t\tdocument.formSubmit.action = \"");
      out.print(path);
      out.write("/weixin/personalMessage/weixin_replyAccess.jsp\";\r\n\t\t\tdocument.formSubmit.submit();\r\n\t\t\t$(obj).attr(\"onclick\", \"\");\r\n\t\t}\r\n\t\t\r\n\t\tfunction removeUsersubmit(){\r\n\t\t  var img = document.getElementsByTagName(\"img\");\r\n\t\t  for(var i = 0; i < img.length; i ++){\r\n\t\t\t  document.getElementById(img[i].id).style.display = '';          \r\n\t\t\t}\r\n\t\t}\r\n\t\t\r\n\t\tfunction removeUserSelect(empId,empName,userAccounts){\r\n\t\t\r\n\t\t\tvar parentDiv = document.getElementById(\"toPersonList\"); \r\n\t\t\tvar childDiv = document.getElementById(empId.substring(1, empId.length - 1) + \"_readSelect\");\r\n\t\t\tparentDiv.removeChild(childDiv);\r\n\t\t\t\r\n\t\t\tvar selectIds = document.getElementById(\"toIDs\").value;\r\n\t\t\tvar selectName = document.getElementById(\"userNames\").value;\r\n\t\t\tvar selectAccounts = document.getElementById(\"userAccounts\").value;\r\n\r\n\t\t\tselectIds = selectIds.replace(empId, \"\");\r\n\t\t\tselectName = selectName.replace(empName + \",\", \"\");\r\n\t\t\tif(\"\" != userAccounts){\r\n\t\t\t\tselectAccounts = selectAccounts.replace(userAccounts + \",\", \"\");\r\n\t\t\t}\r\n\t\t\t\r\n\t\t\tdocument.getElementById(\"toIDs\").value = selectIds;\r\n");
      out.write("\t\t\tdocument.getElementById(\"userNames\").value = selectName;\r\n\t\t\tdocument.getElementById(\"userAccounts\").value = selectAccounts;\r\n\t\t\t// ????????????????????????????????????\r\n\t\t\tvar ins = $(\"input[name='users']\");\t// ????????????????????????\r\n\t\t\tvar id;\r\n\t\t\t// ???????????????ID???????????????????????????????????????\r\n\t\t\tfor(var i=0; i<ins.length; i++){\r\n\t\t\t\tid = ins[i].id.split(\"_\")[1];\r\n\t\t\t\tid = c + id + c;\r\n\t\t\t\tif(id == empId){\t// ID????????????????????????ID??????\r\n\t\t\t\t\tins[i].checked = \"\";\r\n\t\t\t\t\tbreak;\r\n\t\t\t\t}\r\n\t\t\t}\r\n\t\t}\r\n\t\t\r\n\t\t</script>\r\n\t</body>\r\n</html>");
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
