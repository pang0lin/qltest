/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:45:33 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.ssdz;

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

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
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

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction;

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
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.release();
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
	 response.sendRedirect("/jsoa/login.jsp?error="+sd);	
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

      out.write("\r\n\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n<!-- saved from url=(0051)https://office.inspur.com/eportal/ui?pageId=1793540 -->\r\n<html>\r\n<head>\r\n    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\" />\r\n    <link href=\"/jsoa/ssdz/res/default.css\" rel=\"stylesheet\" id=\"lhgdialoglink\" />\r\n    <title>用户登录</title>\r\n    <meta name=\"页面生成时间\" content=\"2016-02-29 13:45:19\" />\r\n    <meta name=\"缓存清理时间\" content=\"2015-10-20 01:55:07\" />\r\n    <meta name=\"easysite版本\" content=\"7.8.9\" />\r\n    <meta name=\"keywords\" content=\"  \" />\r\n    <meta name=\"description\" content=\" 登录页面  \" />\r\n    <link rel=\"stylesheet\" type=\"text/css\" media=\"screen\" href=\"/jsoa/ssdz/res/huilan-jquery-ui.css\" />\r\n    <script type=\"text/javascript\" src=\"/jsoa/ssdz/res/huilan-jquery-ui.js\"></script>\r\n    <link rel=\"stylesheet\" type=\"text/css\" media=\"screen\" href=\"/jsoa/ssdz/res/langchao.css\" />\r\n    <script type=\"text/javascript\" src=\"/jsoa/ssdz/res/langchao.js\"></script>\r\n");
      out.write("    <SCRIPT LANGUAGE=\"JavaScript\" src=\"/jsoa/js/cookie.js\"></SCRIPT>\r\n<script LANGUAGE=\"JavaScript\" src=\"/jsoa/js/log.js\"></script>\r\n<script src=\"");
      out.print(webapp);
      out.write("/webmail/ajax_util.js\"></script>\r\n</head>\r\n<body onUnload=\"javascript:unLoad();\" onLoad=\"javascript:load();checkIE11();jumpErr();msg();\">\r\n");
      //  html:form
      org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag) _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(org.apache.struts.taglib.html.FormTag.class);
      boolean _jspx_th_html_005fform_005f0_reused = false;
      try {
        _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
        _jspx_th_html_005fform_005f0.setParent(null);
        // /ssdz/login.jsp(81,0) name = action type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_html_005fform_005f0.setAction("/CheckUser");
        // /ssdz/login.jsp(81,0) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_html_005fform_005f0.setMethod("post");
        int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
        if (_jspx_eval_html_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n<input type=\"hidden\" name=\"checkBrow\" id=\"checkBrow\" value=\"\"/>\r\n<input type=\"hidden\" name=\"pwdError\" value=\"");
            out.print(markError);
            out.write("\"/>\r\n    <div class=\"\" style=\"left: 0px; top: 0px; visibility: hidden; position: absolute;\"><table class=\"ui_border\"><tbody><tr><td class=\"ui_lt\"></td><td class=\"ui_t\"></td><td class=\"ui_rt\"></td></tr><tr><td class=\"ui_l\"></td><td class=\"ui_c\"><div class=\"ui_inner\"><table class=\"ui_dialog\"><tbody><tr><td colspan=\"2\"><div class=\"ui_title_bar\"><div class=\"ui_title\" unselectable=\"on\" style=\"cursor: move;\"></div><div class=\"ui_title_buttons\"><a class=\"ui_min\" href=\"javascript:void(0);\" title=\"最小化\" style=\"display: inline-block;\"><b class=\"ui_min_b\"></b></a><a class=\"ui_max\" href=\"javascript:void(0);\" title=\"最大化\" style=\"display: inline-block;\"><b class=\"ui_max_b\"></b></a><a class=\"ui_res\" href=\"javascript:void(0);\" title=\"还原\"><b class=\"ui_res_b\"></b><b class=\"ui_res_t\"></b></a><a class=\"ui_close\" href=\"javascript:void(0);\" title=\"关闭(esc键)\" style=\"display: inline-block;\">×</a></div></div></td></tr><tr><td class=\"ui_icon\" style=\"display: none;\"></td><td class=\"ui_main\" style=\"width: auto; height: auto;\"><div class=\"ui_content\" style=\"padding: 10px;\"></div></td></tr><tr><td colspan=\"2\"><div class=\"ui_buttons\" style=\"display: none;\"></div></td></tr></tbody></table></div></td><td class=\"ui_r\"></td></tr><tr><td class=\"ui_lb\"></td><td class=\"ui_b\"></td><td class=\"ui_rb\" style=\"cursor: se-resize;\"></td></tr></tbody></table></div>\r\n");
            out.write("    <link rel=\"stylesheet\" type=\"text/css\" media=\"screen\" href=\"/jsoa/ssdz/res/skin.css\" />\r\n\r\n        <input id=\"SSOUserName\" type=\"hidden\" name=\"j_username\" value=\"$currentLoginUser.userMap.loginName\" />\r\n        <input id=\"SSOPwd\" type=\"hidden\" name=\"j_password\" value=\"$currentLoginUser.userMap.password\" />\r\n     <div style=\" height:90px; background-color:#fff;\">\r\n        <div style=\"height:30px;\"></div>\r\n        <table style=\"float:left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"437\">\r\n            <tbody>\r\n                <tr>\r\n                    <td width=\"100\"></td>\r\n                    <td width=\"173\"><img src=\"/jsoa/ssdz/res/2014091819272321269.png\" /></td>\r\n                    <td width=\"164\"></td>\r\n                </tr>\r\n            </tbody>\r\n        </table>\r\n    </div>\r\n    <div style=\" height:396px; background:url(/jsoa/ssdz/res/2014091819272129996.jpg) no-repeat center;\">\r\n        <div style=\"width:1000px; margin: 0 auto;\">\r\n            <div style=\"float:left; width:500px;\" runat=\"server\" id=\"tub\" class=\" column\">\r\n");
            out.write("                <div class=\"portlet\" id=\"7cfeeffa92094245abd020265566421a\" pagemoduleid=\"d88d757572164332955be964f4ebd08d\">\r\n                    <div align=\"left\" class=\"portlet-header\" style=\"display: none;\">\r\n                        <span id=\"menu\">\r\n                        </span>\r\n                        <div id=\"submenu7cfeeffa92094245abd020265566421a\" class=\"shadow dn\">\r\n                            <ul class=\"float_list_ul\"></ul>\r\n                        </div>\r\n                    </div>\r\n                    <div>\r\n                        <div style=\"padding-top: 45px; margin-left: -40px;\"><img src=\"/jsoa/ssdz/res/2015101215535368721.png\" /></div>\r\n\r\n                    </div>\r\n                </div>\r\n            </div>\r\n            <div style=\"float:right; width:500px; height:310px; padding-top:10px; margin-top:50px; background:url(/jsoa/ssdz/res/2015092310111886787.png) no-repeat;\" runat=\"server\" id=\"loginpane\" class=\" column\">\r\n                <div class=\"portlet\" id=\"0e660d9d61e14f31aab9aae2116ca68a\" pagemoduleid=\"96f2556a9d4e4963abd50d96be6d6128\">\r\n");
            out.write("                    <div align=\"left\" class=\"portlet-header\" style=\"display: none;\">\r\n                        <span id=\"menu\">\r\n                        </span>\r\n                        <div id=\"submenu0e660d9d61e14f31aab9aae2116ca68a\" class=\"shadow dn\">\r\n                            <ul class=\"float_list_ul\"></ul>\r\n                        </div>\r\n                    </div>\r\n                    <div>\r\n\r\n\r\n                        <script type=\"text/javascript\" src=\"/jsoa/ssdz/res/artDialog.min.js\"></script>\r\n                        <link media=\"all\" type=\"text/css\" href=\"/jsoa/ssdz/res/huilanFormStyle.css\" rel=\"stylesheet\" />\r\n                        <script type=\"text/javascript\" src=\"/jsoa/ssdz/res/Validform_v5.3.js\"></script>\r\n                        <script type=\"text/javascript\" src=\"/jsoa/ssdz/res/passwordStrength-min.js\"></script>\r\n                        <script type=\"text/javascript\" src=\"/jsoa/ssdz/res/adminLogin.js\"></script>\r\n                        <input type=\"hidden\" id=\"portalVerificateCodeUrl\" value=\"/eportal/verificateCode\" />\r\n");
            out.write("                        <style type=\"text/css\">\r\n                            .login_nyj {\r\n                                margin: 10px 0;\r\n                                padding: 0;\r\n                            }\r\n\r\n                            .login_nyjth {\r\n                                width: 85px;\r\n                                height: 32px;\r\n                                line-height: 16px;\r\n                                font-size: 14px;\r\n                            }\r\n\r\n                            .login_nyjtd {\r\n                                width: 210px;\r\n                                height: 24px;\r\n                                overflow: hidden;\r\n                            }\r\n\r\n                            .login_nyj tr td input {\r\n                                vertical-align: middle;\r\n                            }\r\n\r\n                            .bj01 input {\r\n                                no-repeat center right;\r\n                                width: 166px;\r\n                                height: 20px;\r\n");
            out.write("                                line-height: 20px;\r\n                                vertical-align: middle;\r\n                                padding: 0;\r\n                                margin: 0;\r\n                                border: 1px solid #B0B0B0;\r\n                                font-size: 14px;\r\n                                font-family: Arial;\r\n                            }\r\n\r\n                            .bj02 input {\r\n                                no-repeat center right;\r\n                                width: 166px;\r\n                                height: 20px;\r\n                                line-height: 20px;\r\n                                padding: 0;\r\n                                vertical-align: middle;\r\n                                margin: 0;\r\n                                border: 1px solid #B0B0B0;\r\n                                font-size: 14px;\r\n                            }\r\n\r\n                            .Validform_right {\r\n                                font-size: 0px;\r\n");
            out.write("                                background: none;\r\n                                color: #ffffff;\r\n                            }\r\n\r\n                            .yzm_pic img {\r\n                                width: 90px;\r\n                                height: 30px;\r\n                                border0: 1px solid #B0B0B0;\r\n                            }\r\n\r\n                            .yzm input {\r\n                                width: 100px;\r\n                                height: 26px;\r\n                            }\r\n\r\n                            .Validform_checktip {\r\n                                margin-left: -8px !important;\r\n                            }\r\n                        </style>\r\n                        <div style=\"overflow:hidden;margin-left: auto;margin-right: auto; padding-left:20px;height:300px;\">\r\n                            \r\n                                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n                                    <tbody>  \r\n                                        <tr>\r\n");
            out.write("\r\n                                            <td class=\"bj01 login_nyjtd\" style=\"font-size:18px;font-weight:800;padding-left:60px;\"></td>\r\n\r\n                                        </tr> \r\n                                        <tr>\r\n\r\n                                            <td class=\"bj01 login_nyjtd\" style=\"font-size:18px;font-weight:800;padding-left:60px;\">欢迎登录神思内部网站</td>\r\n\r\n                                        </tr>                                    \r\n                                        <tr>\r\n                                            <td>\r\n                                                <table width=\"380\" align=\"left\" style=\"margin-left:8px;table-layout:fixed;margin-top:36px;\" class=\"memberLoginTable login_nyj\">\r\n                                                    <tbody>\r\n                                                       \r\n                                                        <tr>\r\n                                                            <td width=\"60\" class=\"login_nyjth\" align=\"center\">\r\n");
            out.write("                                                                账号\r\n                                                            </td>\r\n                                                            <td colspan=\"1\" class=\"bj01 login_nyjtd\"><input type=\"text\" 　value=\"\" name=\"userName\" datatype=\"*\" nullmsg=\" \" style=\"height:24px;width:200px\" /></td>\r\n                                                            <td><div class=\"Validform_checktip Validform_right\"></div></td>\r\n                                                        </tr>\r\n                                                        <tr>\r\n                                                            <td style=\"height:5px;\" colspan=\"3\"></td>\r\n                                                        </tr>\r\n                                                        <tr>\r\n                                                            <td class=\"login_nyjth\" align=\"center\">\r\n                                                                密码\r\n                                                            </td>\r\n");
            out.write("                                                            <td class=\"bj02 login_nyjtd\" colspan=\"1\"><input type=\"password\" 　value=\"\" name=\"userPassword\" class=\"\" datatype=\"*\" nullmsg=\" \" style=\"height:24px;width:200px\" /></td>\r\n                                                            <td><div class=\"Validform_checktip Validform_right\"></div></td>\r\n                                                        </tr>\r\n                                                        <tr>\r\n                                                            <td style=\"height:5px;\" colspan=\"3\"></td>\r\n                                                        </tr>\r\n                                                       \r\n                                                        <tr>\r\n                                                            <td style=\"height:5px;\" colspan=\"3\"></td>\r\n                                                        </tr>\r\n                                                        <tr>\r\n                                                            <td style=\"height:15px;padding-left:15px;\" colspan=\"3\">\r\n");
            out.write("                                                                <span style=\"color:#999999; \">\r\n                                                                    <input type=\"checkbox\"  name=\"checkbox\" id=\"checkbox\" value=\"1\" />\r\n                                                                    <label for=\"saveLoginInfo\" class=\"login_nyjth\">记住密码</label>\r\n                                                                </span>\r\n                                                            </td>\r\n                                                        </tr>\r\n                                                    </tbody>\r\n                                                </table>\r\n                                            </td>\r\n                                        </tr>\r\n                                        <tr>\r\n                                            <td style=\"height:20px;\">\r\n                                                \r\n                                            </td>\r\n                                        </tr>\r\n");
            out.write("                                        <tr>\r\n                                            <td>\r\n                                                <table class=\"dlzc\" cellspacing=\"0\" align=\"left\" border=\"0\" cellpadding=\"0\" style=\"margin-left:15px;\" width=\"300\">\r\n                                                    <tbody>\r\n                                                        <tr>\r\n                                                            <td align=\"center\" value=\"\" onclick=\"javascript:submitForm();\" style=\"padding-left:28px;\"><input type=\"button\" style=\" width:115px; height:40px;line-height:28px; background:url(/jsoa/ssdz/res/2015092817131610086.jpg) no-repeat;padding:0; vertical-align:middle; margin:0; border:1px solid #B0B0B0; border:0; cursor:pointer; \" /></td>\r\n                                                            <td><!-- <button id=\"registBt\" style=\" width:110px; height:40px;line-height:28px;background:url(/jsoa/ssdz/res/2015092817131697755.jpg) no-repeat;padding:0; vertical-align:middle; margin:0; border:1px solid #B0B0B0; border:0; cursor:pointer;\" type=\"reset\" onclick=\"resetLoginForm()\"> </button> --></td>\r\n");
            out.write("                                                        </tr>\r\n                                                    </tbody>\r\n                                                </table>\r\n                                            </td>\r\n                                        </tr>\r\n                                    </tbody>\r\n                                </table>                           \r\n                        </div>\r\n                      \r\n                    </div>\r\n                </div>\r\n            </div>\r\n            <div style=\" clear:both\"></div>\r\n        </div>\r\n    </div>\r\n    <div style=\"padding-bottom: 20px;\" id=\"foot\" runat=\"server\" name=\"底部文字\" class=\" column\">\r\n        <div class=\"portlet\" id=\"aeb151025eda420eaaa562ffb216a29b\" pagemoduleid=\"67c2c86e9a85482785b365720cac5d2d\">\r\n            <div align=\"left\" class=\"portlet-header\" style=\"display: none;\">\r\n                <span id=\"menu\">\r\n                </span>\r\n                <div id=\"submenuaeb151025eda420eaaa562ffb216a29b\" class=\"shadow dn\">\r\n");
            out.write("                    <ul class=\"float_list_ul\"></ul>\r\n                </div>\r\n            </div>\r\n            <div>\r\n                <div style=\"text-align: center; line-height: 30px; color: #666666; font-size: 14px; padding-top: 10px\">\r\n                    联系我们：itc@shensi.com 热线电话：0531-888888&nbsp; 内部信息注意保密不得随意传播 版权所有?神思电子&nbsp;\r\n                   \r\n                </div>\r\n\r\n            </div>\r\n        </div>\r\n    </div>\r\n    <input type=\"hidden\" name=\"portalLogon\" value=\"1\"/>\r\n");
            int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
            if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
              break;
          } while (true);
        }
        if (_jspx_th_html_005fform_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return;
        }
        _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
        _jspx_th_html_005fform_005f0_reused = true;
      } finally {
        org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fform_005f0, _jsp_getInstanceManager(), _jspx_th_html_005fform_005f0_reused);
      }
      out.write("    \r\n</body>\r\n</html>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\nfunction initHeight(){\r\n\t if(navigator.userAgent.indexOf(\"MSIE\")!=-1) {\r\n     \tif(navigator.userAgent.indexOf(\"MSIE 10.0\")==-1){\r\n     \t\tdocument.getElementById(\"tdHeight\").style.height=\"80\";\r\n     \t}else{\r\n     \t\tdocument.getElementById(\"tdHeight\").style.height=\"40\";\r\n     \t}\r\n     }else if(navigator.userAgent.indexOf(\"Trident/7.0\")!=-1){//ie 11\r\n\t\t document.getElementById(\"tdHeight\").style.height=\"40\";\r\n\t }\r\n\t if(navigator.userAgent.indexOf(\"Firefox\")!=-1){//火狐\r\n\t\t document.getElementById(\"tdHeight\").style.height=\"40\";\r\n\t }\r\n\t if(navigator.userAgent.indexOf(\"Safari\")!=-1){//苹果\r\n\t\t document.getElementById(\"tdHeight\").style.height=\"40\";\r\n\t }\t\r\n\t \r\n}\r\nvar error=document.CheckUserForm.pwdError.value;\r\n<!--\r\n\r\nfunction msg(){\r\n");

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
      out.write("\r\n}\r\n\r\nfunction submit(){\r\n\t/*if(navigator.userAgent.indexOf(\"iPad\")>0){\r\n\t unLoad();\r\n\t}*/\r\n\t");
if(BrowserJudge.isIpad(request)){
      out.write("\r\n\tunLoad();\r\n\t");
} 
      out.write("\r\n    document.CheckUserForm.submit();\r\n}\r\n\r\nfunction checkForm(){\r\n     if(document.CheckUserForm.userName.value==\"\"){\r\n        alert(\"");
      out.print(Resource.getValue(localeCode,"common","comm.loginremind4"));
      out.write("\");\r\n        document.CheckUserForm.userName.focus();\r\n        return false;\r\n    }else if( document.CheckUserForm.userPassword.value==\"\"){\r\n        alert(\"");
      out.print(Resource.getValue(localeCode,"common","comm.loginremind5"));
      out.write("\");\r\n        document.CheckUserForm.userPassword.focus();\r\n        return false;\r\n    }\r\n    return true;\r\n}\r\nfunction load(){\r\n\t//判断是否在框架内或子页面打开了登陆页\r\n\t/*if(opener){\r\n\t\topener.location.href=\"/jsoa/login.jsp\";\r\n\t\twindow.close();\r\n\t}\r\n\tif(self.frameElement && self.frameElement.tagName==\"IFRAME\"){\r\n\t\twindow.parent.location.href=\"/jsoa/login.jsp\";\r\n\t}*/\t\r\n    \r\n    //deleteCookie(\"jsoaUSERNAME\");\r\n    if(Cookie(\"jsoaUserName\") != null || Cookie(\"jsoaDomainAccount\") != null){\t\t\r\n        if(Cookie(\"jsoaUserName\") != null){\r\n            document.CheckUserForm.userName.value = Cookie(\"jsoaUserName\");\r\n        }\r\n                \r\n    }\r\n    if(document.getElementById('checkbox')){\r\n      if(Cookie(\"MarkPwd\") != null || Cookie(\"jsoaDomainAccount\") != null){\t\t\r\n        if(Cookie(\"MarkPwd\") != null){\r\n            if(Cookie(\"MarkPwd\")=='1'){\r\n                document.CheckUserForm.checkbox.checked=true;\r\n                  if(Cookie(\"jsoaUserPwd\") != null || Cookie(\"jsoaDomainAccount\") != null){\r\n\t\t\t\t        if(Cookie(\"jsoaUserPwd\") != null){\r\n");
      out.write("\t\t\t\t            document.CheckUserForm.userPassword.value = Cookie(\"jsoaUserPwd\");\r\n\t\t\t\t        }        \r\n\t\t\t\t    }\r\n            }else{\r\n               document.CheckUserForm.checkbox.checked=false;\r\n            }\r\n        }        \r\n      }\r\n    }\r\n\t");
if("user".equals((String)request.getAttribute("errorType"))){
      out.write("\r\n\t\tdocument.CheckUserForm.userName.select();   \r\n\t");
}else{
      out.write("\r\n\t\tif(document.CheckUserForm.userName.value==\"\"){\r\n\t\t\tdocument.CheckUserForm.userName.focus();\r\n\t\t}else{\r\n\t\t\tdocument.CheckUserForm.userPassword.focus();\r\n\t\t}\r\n\t");
}
      out.write("\r\n}\r\n\r\nfunction addFavorite(){\r\n  window.external.addFavorite(window.location.href, document.title);\r\n  return false;\r\n}\r\nfunction setHomePage(){\r\n  document.body.style.behavior='url(#default#homepage)';\r\n  document.body.setHomePage('");
      out.print(request.getRequestURL());
      out.write("');\r\n  return false;\r\n}\r\nfunction jumpSite(){\r\n  location.href=\"http://www.jiusi.net\";\r\n}\r\nfunction jumpErr(){\r\n\t");
if(!"".equals(flagStr)&&null!=flagStr&& "false".equals(flagStr) ){
      out.write("\r\n\t  location.href=\"/jsoa/jumperrorMsg.jsp?\";\r\n\t");
}
      out.write("\r\n}\r\n\r\nfunction checkIE11(){\r\n   if(navigator.userAgent.indexOf(\"Trident/7.0\")>=0 && navigator.userAgent.indexOf(\"MSIE\")>=0){\r\n       document.getElementById(\"checkBrow\").value=\"IE11And360\";\r\n   }else if(navigator.userAgent.indexOf(\"Trident/7.0\")>=0 && navigator.userAgent.indexOf(\"MSIE\")<0){\r\n       document.getElementById(\"checkBrow\").value=\"IE11\"; \r\n   }\r\n}\r\n//-->\r\n\r\n</script>\r\n");
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
