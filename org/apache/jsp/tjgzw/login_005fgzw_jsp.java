/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:54:25 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.tjgzw;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.lang.Resource;
import java.util.*;
import java.lang.*;
import com.js.system.util.SysSetupReader;
import com.js.system.service.logomanager.LogoManager;
import com.js.util.config.SystemCommon;
import com.js.util.util.BrowserJudge;

public final class login_005fgzw_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-html.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-bean.tld", Long.valueOf(1499751390000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("java.lang");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.lang.Resource");
    _jspx_imports_classes.add("com.js.system.service.logomanager.LogoManager");
    _jspx_imports_classes.add("com.js.util.config.SystemCommon");
    _jspx_imports_classes.add("com.js.system.util.SysSetupReader");
    _jspx_imports_classes.add("com.js.util.util.BrowserJudge");
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");


String path = request.getContextPath();
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);


String webapp=request.getContextPath(); 
LogoManager.initManager();
String logoPath="images/index.gif";
String logoFile=LogoManager.getLogo("1");
if(logoFile.indexOf("_")<0){
logoFile=logoFile.replace("upload/logo","upload/0000/logo");
}
if(logoFile!=null&&!logoFile.equals("")){

if(com.js.util.config.SystemCommon.getUseClusterServer()==1){
	 logoPath=com.js.util.config.SystemCommon.getClusterServerUrl()+logoFile;
}else{
	logoPath=webapp+logoFile;
}

}

String outyes=request.getParameter("outyes")==null?"":request.getParameter("outyes");
String localeCode="zh_cn";
String validate=request.getParameter("validate");
if("no".equals(validate)){
 session.invalidate();	 
 String sd="";
 if("1".equals(outyes)){
 sd="您的帐号已经从其它地点登陆，您被迫下线！";
 sd=new String(sd.getBytes("GBK"),"iso8859-1"); 
 }
 response.sendRedirect("/jsoa/tjgzw/login_gzw.jsp?error="+sd);	
}else{
Locale loc=new Locale("zh_cn");
session.setAttribute("org.apache.struts.action.LOCALE",loc);

String error=request.getParameter("error")==null?"":request.getParameter("error");
com.js.system.util.SysSetupReader ssReader=com.js.system.util.SysSetupReader.getInstance();
String flag=ssReader.getMarkPWD("0");
String markPwd=flag.split(";")[0];
String markImage=flag.split(";")[1];
//0跳过
String markError=flag.split(";")[2];
String flagStr=(String)request.getAttribute("flagStr");
String userAccountName=(String)request.getAttribute("userAccountName");

	

      out.write("\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\" />\r\n<script src=\"");
      out.print(path);
      out.write("/tjgzw/javascript/jquery-1.9.1.min.js\" type=\"text/javascript\"></script>\r\n<script src=\"");
      out.print(path);
      out.write("/tjgzw/javascript/md5.js\" type=\"text/javascript\"></script>\r\n<title>欢迎光临天津市国资系统信息互通平台</title>\r\n<style type=\"text/css\">\r\n<!--\r\nbody {\r\n\tbackground-image: url(");
      out.print(path);
      out.write("/tjgzw/images/index_bg.jpg);\r\n\tbackground-repeat: no-repeat;\r\n\tmargin-left: 0px;\r\n\tmargin-top: 0px;\r\n\tmargin-right: 0px;\r\n\tmargin-bottom: 0px;\r\n}\r\n.font14 {\r\n\tfont-size: 14px;\r\n\tfont-family: \"微软雅黑\";\r\n}\r\n.fontinput{\r\n\t font-size:14px; \r\n\t font-family:微软雅黑;\r\n\t width:250px; \r\n\t height:37px; \r\n\t border:1px solid #999999; \r\n\t line-height:37px; \r\n\t text-align:left; \r\n\t vertical-align:middle; \r\n\t text-indent:5px;\r\n\t}\r\n\t\r\n.pass-button {\r\n\tdisplay: block;\r\n\theight: 40px;\r\n\twidth:200px;\r\n\tfont-size: 16px;\r\n\tfont-weight: bold;\r\n\tcursor: pointer;\r\n\tcolor: #fff;\r\n\tbackground: #3f89ec;\r\n\tborder-radius: 3px;\r\n\tborder: none;\r\n\t}\r\n.pass-button-submit {\r\n\tdisplay: block;\r\n\theight: 40px;\r\n\tfont-size: 16px;\r\n\tfont-weight: bold;\r\n\tcursor: pointer;\r\n\tcolor: #fff;\r\n\tbackground: #4490f7;\r\n\tborder-radius: 3px;\r\n\tborder: none;\r\n\t\r\n\t}\r\n-->\r\n</style>\r\n<SCRIPT LANGUAGE=\"JavaScript\" src=\"js/cookie.js\"></SCRIPT>\r\n<script LANGUAGE=\"JavaScript\" src=\"js/log.js\"></script>\r\n<script src=\"");
      out.print(webapp);
      out.write("/webmail/ajax_util.js\"></script>\r\n<script>\r\n/*\r\n function OnClientSubmit() {\r\n           // SetPwdAndChk();\r\n            //var txtpws = document.getElementById(\"userPassword\");\r\n//            if (document.getElementById(\"hidUseSecrityPassword\").value == \"1\") {\r\n//                txtpws.value = hex_md5(hex_md5(txtpws.value))\r\n//            }\r\n         //   login(); \r\n\t\t\t//收文登录\r\n\t\t\t//window.location.href = \"tjgzw/index.html\";\r\n\t\t\tvar form = document.getElementById(\"CheckUserForm\");\r\n\t\t\tform.submit();\r\n\t\t\t\r\n        }\r\n \t\t//记住密码\r\n        window.onload = function onLoginLoaded() {\r\n            if (isPostBack == \"False\") {\r\n                GetLastUser();\r\n            }\r\n        }\r\n\r\n        function GetLastUser() {\r\n            var id = \"49BAC005-7D5B-4231-8CEA-16939BEACD67\"; //GUID标识符\r\n            var usr = GetCookie(id);\r\n            if (usr != null) {\r\n                document.getElementById('userName').value = usr;\r\n            } else {\r\n                document.getElementById('userName').value = \"\"; //默认登录名\r\n\t\t\t\tdocument.getElementById(\"userName\").focus();\r\n");
      out.write("            }\r\n            GetPwdAndChk();\r\n        }\r\n        //点击登录时触发客户端事件\r\n\r\n        function SetPwdAndChk() {\r\n            //取用户名\r\n            var usr = document.getElementById('userName').value;\r\n            //alert(usr);\r\n            //将最后一个用户信息写入到Cookie\r\n            SetLastUser(usr);\r\n            //如果记住密码选项被选中\r\n            if (document.getElementById('Login1_RememberMe').checked == true) {\r\n                //取密码值\r\n                var pwd = document.getElementById('userPassword').value;\r\n                //alert(pwd);\r\n                var expdate = new Date();\r\n                expdate.setTime(expdate.getTime() + 14 * (24 * 60 * 60 * 1000));\r\n                //将用户名和密码写入到Cookie\r\n                SetCookie(usr, pwd, expdate);\r\n            } else {\r\n                //如果没有选中记住密码,则立即过期\r\n                ResetCookie();\r\n            }\r\n        }\r\n\r\n        function SetLastUser(usr) {\r\n            var id = \"49BAC005-7D5B-4231-8CEA-16939BEACD67\";\r\n            var expdate = new Date();\r\n            //当前时间加上两周的时间\r\n            expdate.setTime(expdate.getTime() + 14 * (24 * 60 * 60 * 1000));\r\n");
      out.write("            SetCookie(id, usr, expdate);\r\n        }\r\n        //用户名失去焦点时调用该方法\r\n\r\n        function GetPwdAndChk() {\r\n            var usr = document.getElementById('userName').value;\r\n            var pwd = GetCookie(usr);\r\n            if (pwd != null) {\r\n                document.getElementById('Login1_RememberMe').checked = true;\r\n                document.getElementById('userPassword').value = pwd;\r\n            } else {\r\n                document.getElementById('Login1_RememberMe').checked = false;\r\n                document.getElementById('userPassword').value = \"\";\r\n            }\r\n        }\r\n        //取Cookie的值\r\n\r\n        function GetCookie(name) {\r\n            var arg = name + \"=\";\r\n            var alen = arg.length;\r\n            var clen = document.cookie.length;\r\n            var i = 0;\r\n            while (i < clen) {\r\n                var j = i + alen;\r\n                //alert(j);\r\n                if (document.cookie.substring(i, j) == arg) return getCookieVal(j);\r\n                i = document.cookie.indexOf(\" \", i) + 1;\r\n");
      out.write("                if (i == 0) break;\r\n            }\r\n            return null;\r\n        }\r\n        var isPostBack = \"False\";\r\n\r\n        function getCookieVal(offset) {\r\n            var endstr = document.cookie.indexOf(\";\", offset);\r\n            if (endstr == -1) endstr = document.cookie.length;\r\n            return unescape(document.cookie.substring(offset, endstr));\r\n        }\r\n        //写入到Cookie\r\n\r\n        function SetCookie(name, value, expires) {\r\n            var argv = SetCookie.arguments;\r\n            //本例中length = 3\r\n            var argc = SetCookie.arguments.length;\r\n            var expires = (argc > 2) ? argv[2] : null;\r\n            var path = (argc > 3) ? argv[3] : null;\r\n            var domain = (argc > 4) ? argv[4] : null;\r\n            var secure = (argc > 5) ? argv[5] : false;\r\n            document.cookie = name + \"=\" + escape(value) + ((expires == null) ? \"\" : (\"; expires=\" + expires.toGMTString())) + ((path == null) ? \"\" : (\"; path=\" + path)) + ((domain == null) ? \"\" : (\"; domain=\" + domain)) + ((secure == true) ? \"; secure\" : \"\");\r\n");
      out.write("        }\r\n\r\n        function ResetCookie() {\r\n            var usr = document.getElementById('userName').value;\r\n            var expdate = new Date();\r\n            SetCookie(usr, null, expdate);\r\n        }\r\n        var oaServerAddress = \"http://\" + window.location.host;\r\n\r\n        //登录\r\n        function login() {\r\n\r\n            var loginurl = oaServerAddress + \"/Services/MobileService.asmx/ValidateUser?jsoncallback=?\";\r\n            var alias = $(\"#userName\").val();\r\n            var pws = $(\"#userPassword\").val();\r\n            $.ajax({\r\n                url: loginurl,\r\n                data: { \"alias\": alias, \"pws\": pws },\r\n                dataType: 'jsonp',\r\n                success: function(v) {\r\n                    if (v.ID != \"0\") {\r\n                        window.location.href = \"index.html\";\r\n\r\n                    }\r\n                    else {\r\n\t\t\t\t\talert(\"请检查用户名和密码是否正确或者该用户是否启用！\");\r\n\r\n                    }\r\n                }\r\n                ,\r\n                error: function(result, status) { //如果没有上面的捕获出错会执行这里的回调函数\r\n");
      out.write("\r\n                     //alert(result.responseText);\r\n\r\n                },\r\n                complete: function(XMLHttpRequest, textStatus) {\r\n\r\n                     var a = textStatus;\r\n                }\r\n            });\r\n\r\n\r\n        }*/\r\n    <!--\r\n        \r\n        var screenwidth;//分辨率宽度\r\n        var screenheight;//分辨率高度\r\n        screenwidth = screen.width;\r\n        screenheight = screen.height;\r\n    //-->\r\n\t\t\r\n</script>\r\n</head>\r\n\r\n<body onUnload=\"javascript:unLoad();\" onLoad=\"javascript:msg();\">\r\n<form  name=\"CheckUserForm\" id=\"CheckUserForm\" action=\"/jsoa/GzwCheckUser.do\" method=\"post\">\r\n  ");

    String uKeyType=SystemCommon.getUKey();
    String uKeyLogType=SystemCommon.getLogType();
    
      out.write("\r\n    <input type=\"hidden\" name=\"checkBrow\" id=\"checkBrow\" value=\"\"/>\r\n\t<input type=\"hidden\" name=\"pwdError\" value=\"");
      out.print(markError);
      out.write("\"/>\r\n<table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"0\">\r\n  <tr>\r\n    <th width=\"20%\" height=\"39\" scope=\"col\">&nbsp;</th>\r\n    <th width=\"22%\" scope=\"col\">&nbsp;</th>\r\n    <th width=\"14%\" scope=\"col\">&nbsp;</th>\r\n    <th width=\"31%\" scope=\"col\">&nbsp;</th>\r\n    <th width=\"13%\" scope=\"col\">&nbsp;</th>\r\n  </tr>\r\n  <tr>\r\n    <td height=\"147\">&nbsp;</td>\r\n    <td colspan=\"3\" align=\"center\"><img src=\"");
      out.print(path);
      out.write("/tjgzw/images/logo.png\" alt=\"logo\" width=\"605\" height=\"86\" /></td>\r\n    <td>&nbsp;</td>\r\n  </tr>\r\n  <tr>\r\n    <td height=\"280\">&nbsp;</td>\r\n    <td>&nbsp;</td>\r\n    <td>&nbsp;</td>\r\n    <td><table width=\"385\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n      <tr>\r\n        <th width=\"385\" height=\"434\" valign=\"top\" background=\"");
      out.print(path);
      out.write("/tjgzw/images/login_bg1.png\" scope=\"col\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n          <tr>\r\n            <th width=\"15%\" height=\"45\" scope=\"col\">&nbsp;</th>\r\n            <th width=\"72%\" scope=\"col\">&nbsp;</th>\r\n            <th width=\"13%\" scope=\"col\">&nbsp;</th>\r\n          </tr>\r\n          <tr>\r\n            <td height=\"24\">&nbsp;</td>\r\n            <td><img src=\"");
      out.print(path);
      out.write("/tjgzw/images/logo_01.jpg\" width=\"36\" height=\"40\" align=\"absmiddle\" /><font style=\"font-size:16px; font-family:微软雅黑; font-weight:bold; margin-top:5px;\"> 系统登录</font></td>\r\n            <td>&nbsp;</td>\r\n          </tr>\r\n          <tr>\r\n            <td height=\"10\">&nbsp;</td>\r\n            <td>&nbsp;</td>\r\n            <td>&nbsp;</td>\r\n          </tr>\r\n          <tr>\r\n            <td>&nbsp;</td>\r\n            <td align=\"left\"><input  id=\"userName\" name=\"userName\" type=\"text\" class=\"fontinput\" value=\"请输入用户名\" onfocus=\"if(this.value=='请输入用户名') {this.value=''}\" onblur=\"if\r\n\r\n(this.value=='') {this.value='请输入用户名'}\" /></td>\r\n            <td>&nbsp;</td>\r\n          </tr>\r\n          <tr>\r\n            <td>&nbsp;</td>\r\n            <td>&nbsp;</td>\r\n            <td>&nbsp;</td>\r\n          </tr>\r\n          <tr>\r\n            <td>&nbsp;</td>\r\n            <td align=\"left\"><input id=\"userPassword\" type=\"password\" class=\"fontinput\" value=\"\"  name=\"userPassword\" /></td>\r\n            <td>&nbsp;</td>\r\n          </tr>\r\n          <tr>\r\n            <td>&nbsp;</td>\r\n");
      out.write("            <td>&nbsp;</td>\r\n            <td>&nbsp;</td>\r\n          </tr>\r\n          <tr>\r\n            <td>&nbsp;</td><!--\r\n            <td align=\"left\"><input type=\"checkbox\" name=\"Login1_RememberMe\" id=\"Login1_RememberMe\" value=\"checkbox\" />\r\n\t\t\t      <span class=\"font14\">记住用户名</span></td>\r\n            --><td>&nbsp;</td>\r\n          </tr>\r\n          <tr>\r\n            <td>&nbsp;</td>\r\n            <td>&nbsp;</td>\r\n            <td>&nbsp;</td>\r\n          </tr>\r\n          <tr>\r\n            <td>&nbsp;</td>\r\n            <td align=\"center\"><input  type=\"submit\" value=\"登录\" class=\"pass-button pass-button-submit\"></td>\r\n            <td>&nbsp;</td>\r\n          </tr>\r\n          <tr>\r\n            <td>&nbsp;</td>\r\n            <td>&nbsp;</td>\r\n            <td>&nbsp;</td>\r\n          </tr>\r\n        </table></th>\r\n      </tr>\r\n    </table></td>\r\n    <td>&nbsp;</td>\r\n  </tr>\r\n  <tr>\r\n    <td>&nbsp;</td>\r\n    <td>&nbsp;</td>\r\n    <td>&nbsp;</td>\r\n    <td>&nbsp;</td>\r\n    <td>&nbsp;</td>\r\n  </tr>\r\n  <tr>\r\n    <td>&nbsp;</td>\r\n    <td>&nbsp;</td>\r\n");
      out.write("    <td>&nbsp;</td>\r\n    <td>&nbsp;</td>\r\n    <td>&nbsp;</td>\r\n  </tr>\r\n</table>\r\n\r\n</form>\r\n</body>\r\n</html>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\nfunction initHeight(){\r\n\t if(navigator.userAgent.indexOf(\"MSIE\")!=-1) {\r\n     \tif(navigator.userAgent.indexOf(\"MSIE 10.0\")==-1){\r\n     \t\tdocument.getElementById(\"tdHeight\").style.height=\"80\";\r\n     \t}else{\r\n     \t\tdocument.getElementById(\"tdHeight\").style.height=\"40\";\r\n     \t}\r\n     }else if(navigator.userAgent.indexOf(\"Trident/7.0\")!=-1){//ie 11\r\n\t\t document.getElementById(\"tdHeight\").style.height=\"40\";\r\n\t }\r\n\t if(navigator.userAgent.indexOf(\"Firefox\")!=-1){//火狐\r\n\t\t document.getElementById(\"tdHeight\").style.height=\"40\";\r\n\t }\r\n\t if(navigator.userAgent.indexOf(\"Safari\")!=-1){//苹果\r\n\t\t document.getElementById(\"tdHeight\").style.height=\"40\";\r\n\t }\t\r\n\t \r\n}\r\nvar error=document.all.pwdError.value;\r\n<!--\r\n\r\nfunction msg(){\r\n");

if(request.getParameter("noDog") != null){

      out.write("\r\nalert(\"");
      out.print(Resource.getValue(localeCode,"common","comm.noDog"));
      out.write("\");\r\n");

}
String errorType=(String)request.getAttribute("errorType");
if("active".equals(errorType)){
      out.write("\r\nalert(\"");
      out.print(Resource.getValue(localeCode,"common","comm.active"));
      out.write("\");\r\n");
}else if("ip".equals(errorType)){
    String addr=request.getRemoteAddr();
    addr=addr==null?"":"("+addr+")";
    
      out.write("\r\nalert(\"");
      out.print(Resource.getValue(localeCode,"common","comm.loginremind1"));
      out.write("\");\r\n");
}else if("password".equals(errorType)){
String pwdError=(String)request.getAttribute("PWDError");
int errorNum=0;
if(pwdError!=null&&!pwdError.equals("")){
   errorNum=Integer.parseInt(pwdError.split(";")[1]);
}

      out.write("\r\nif(error!='0'){\r\nvar errorNum=error.substring(1,error.length-1).split('$$')[0];\r\nvar errorTime=error.substring(1,error.length-1).split('$$')[1];\r\n   if(Number('");
      out.print(errorNum);
      out.write("')>=Number(errorNum)){\r\n      alert('对不起!您的密码已多次输入错误,请'+errorTime+'分钟后重试!');\r\n      return;\r\n   }\r\n}\r\nalert(\"");
      out.print(Resource.getValue(localeCode,"common","comm.loginremind3"));
      out.write("\");\r\n//密码错误\r\ndocument.CheckUserForm.userPassword.select();\r\n");
}else if("user".equals(errorType)||"ifCaps".equals(errorType)){
      out.write("\r\nalert(\"");
      out.print(Resource.getValue(localeCode,"common","comm.loginremind2"));
      out.write("\");\r\ndocument.CheckUserForm.userName.select();\r\n");
}else if("online".equals(errorType)){
      out.write("\r\nalert(\"");
      out.print(Resource.getValue(localeCode,"common","comm.online"));
      out.write("\");\r\n");
}else if("userNumError".equals(errorType)){
      out.write("\r\nalert(\"");
      out.print(Resource.getValue(localeCode,"common","comm.usernumerror"));
      out.write("\");\r\n");
}else if("domainOverDate".equals(errorType)){
      out.write("\r\nalert(\"");
      out.print(Resource.getValue(localeCode,"common","comm.noDog"));
      out.write("\");\r\n");
}else if("noaudit".equals(errorType)){
      out.write("\r\nalert(\"系统未开启权限审计功能！\");\r\n");
}
errorType=request.getParameter("errorType");
if("overtime".equals(errorType)){
      out.write("\r\nalert(\"");
      out.print(Resource.getValue(localeCode,"common","comm.overtime"));
      out.write("\");\r\n");
}else if("nokey".equals(errorType)){
      out.write("\r\nalert(\"");
      out.print(Resource.getValue(localeCode,"common","comm.nokey"));
      out.write("\");\r\n");
}else if("keyErr".equals(errorType)){
      out.write("\r\nalert(\"");
      out.print(Resource.getValue(localeCode,"common","comm.keyerror"));
      out.write("\");\r\n");
}else if("domainError".equals(errorType)){
      out.write("\r\nalert(\"");
      out.print(Resource.getValue(localeCode,"common","comm.domainiderror"));
      out.write("\");\r\n");
}else if("userNumError".equals(errorType)){
      out.write("\r\nalert(\"");
      out.print(Resource.getValue(localeCode,"common","comm.domainerror"));
      out.write("\");\r\n");
}else if("serverdown".equals(errorType)){
      out.write("\r\nalert(\"您与服务器的连接已断开，请重新登录！\");\r\n");
}else if("imageCode".equals(errorType)){
      out.write("\r\nalert(\"您输入的验证码不正确!\");\r\n");
}
      out.write("\r\n}\r\n//-->\r\n\r\n</script>\r\n");
} 
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
