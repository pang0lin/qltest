/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:01:30 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.personal_005fwork.setup.tjgzw;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.apache.commons.lang.StringUtils;
import com.js.oa.personalwork.setup.po.MyInfoPO;
import com.js.oa.tjgzw.bean.TjgzwBean;
import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;

public final class personalwork_005femail_005flink_005ftjgzw_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(6);
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-nested.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-tiles.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-logic.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-html.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-template.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-bean.tld", Long.valueOf(1499751390000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("org.apache.commons.lang.StringUtils");
    _jspx_imports_classes.add("com.js.system.util.StaticParam");
    _jspx_imports_classes.add("com.js.oa.personalwork.setup.po.MyInfoPO");
    _jspx_imports_classes.add("com.js.util.config.SystemCommon");
    _jspx_imports_classes.add("com.js.oa.tjgzw.bean.TjgzwBean");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody;

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
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.release();
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
      			"../../../public/jsp/error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

String local = session.getAttribute("org.apache.struts.action.LOCALE").toString();
String userAccount = session.getAttribute("userAccount").toString().toLowerCase();
String domainId=(String)session.getAttribute("domainId");
//判断密码强度开关是否已开
String pwdStrong=StaticParam.getValidatePasswordStrong(domainId);
String minLength="";
String maxLength="";
String isMustCotainNumAndLetter="0";
if(!pwdStrong.equals("0"))//开启
{
  request.setAttribute("isDisplay","inline");
  //读取pwd_strong设置的字段
  pwdStrong=pwdStrong.substring(1,pwdStrong.length()-1).replaceAll("\\$\\$", ";");
  String[] parameters=pwdStrong.split(";");
  minLength=parameters[0];
  maxLength=parameters[1];
  isMustCotainNumAndLetter=parameters[2];
 }
else//未开启
{
  request.setAttribute("isDisplay","none");
}
	Long curUserId = new Long(request.getSession(true).getAttribute("userId").toString());
	TjgzwBean  tjgzwBean = new TjgzwBean();
	MyInfoPO poc = tjgzwBean.getUserInfo(curUserId);

      out.write("\r\n<!--\r\n *  注释:个人办公：个人设置 最后修改\r\n * <p>Description:密码修改页面</p>\r\n * <p>Copyright: Copyright (c) 2008</p>\r\n * <p>Company: Beijing JS  C0. Ltd.</p>\r\n * \r\n * @version jsoa 1.0\r\n */\r\n 主要参数：\r\n\r\n-->\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<title>");
      if (_jspx_meth_bean_005fmessage_005f0(_jspx_page_context))
        return;
      out.write("</title>\r\n<script language=\"JavaScript\" src=\"/jsoa/js/resource/");
      out.print(local);
      out.write("/CommonResource.js\" type=\"text/javascript\"></script>\r\n<script language=\"JavaScript\" src=\"js/date0.js\"></script>\r\n<script src=\"/jsoa/js/resource/");
      out.print(local);
      out.write("/PersonalworkResource.js\" language=\"javascript\"></script>\r\n<style type=\"text/css\">\r\n\t.color{color:#FF0000;}\r\n\t.wrong_info{vertical-align:middle;}\r\n\t.right_info{vertical-align:middle;}\r\n\t.pass_top{width: 48px; background-color:gray; height: 8px; overflow: hidden; border:1px solid #d0d0d0;}\r\n\t.pass_top_show{width: 48px; background-color:#0074DB; height: 8px; overflow: hidden; border:1px solid #d0d0d0;}\r\n\t.pass_bottom{font-family: \"宋体\"; color: #666; padding-left:17px;border:0px;}\r\n\t.pass_bottom_show{font-family: \"宋体\"; color: #666; font-weight:bold; padding-left:17px;border:0px;}\r\n\t.block{margin:10px 0px;text-align:left;padding-top:10px;}\r\n\t#contentDiv{position:relative;}\r\n\t.span_middle{padding-bottom:10px;}\r\n\t.btn{width:50px;height:20px;cursor:pointer;}\r\n\t.input{height:20px;}\r\n</style>\r\n\t\r\n</head>\r\n\r\n<body  onload=\"init()\" class=\"MainFrameBox\">\r\n\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\" class=\"docBox\">\r\n<tr>\r\n<td>\r\n\r\n<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"doc\">\r\n");
      out.write("<tr>\r\n<td >\r\n <fieldset>\r\n    <legend>邮箱账户关联</legend>\r\n\r\n<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" >\r\n <tr>\r\n    <td valign=\"top\">\r\n\t<div id=\"contentDiv\">\r\n\t<form action=\"/jsoa/TjgzwAction.do?type=saveOrUpdateLinkEmail\" method=\"post\" id=\"form1\">\r\n\t<!-- 邮箱账户管理 -->\r\n\t<div id=\"pwd_new\" class=\"block\">\r\n\t\t<span class=\"span_middle\"><font color=\"red\" >&nbsp;*</font>邮箱地址</span>\r\n\t\t");

		String empEmail2 = "";
		if(StringUtils.isNotEmpty(poc.getEmpEmail2())){
			empEmail2 = poc.getEmpEmail2();
		}
		 
		
      out.write("\r\n\t\t<span><input id=\"email\" name=\"email\"  value=\"");
      out.print(empEmail2);
      out.write("\"></input></span>\r\n\t\t<span style=\"display:");
      out.print(request.getAttribute("isDisplay"));
      out.write(";width:150px;\"></span>\r\n\t</div>\r\n\t\t  \t\t\r\n\t<!-- 新密码 -->\r\n\t<div id=\"pwd_new\" class=\"block\">\r\n\t\t<span class=\"span_middle\"><font color=\"red\" >&nbsp;*</font>密&nbsp;&nbsp;码&nbsp;&nbsp;</span>\r\n\t\t<span><input id=\"pwd\" name=\"pwd\" onblur=\"checkNewPassword(this.value);\"  value=\"\"></input></span>\r\n\t\t<span style=\"display:");
      out.print(request.getAttribute("isDisplay"));
      out.write(";width:150px;\"></span>\r\n\t\t<span id=\"newPasswordMsg\"></span>\r\n\t</div>\r\n\t<!-- 密码确认 -->\r\n\t<div id=\"pwd_confirm\" class=\"block\">\r\n\t\t<span class=\"span_middle\"><font color=\"red\" >&nbsp;*</font>密码确认</span>\r\n\t\t<span><input id=\"pwdcon\" name=\"pwdcon\" onblur=\"checkConfirmPassword(this.value)\"></input></span>\r\n\t\t<span id=\"confirmPasswordMsg\"></span>\r\n\t</div>\r\n\t\r\n\t<br/>\r\n\t\r\n\t<table>\r\n\t\t<tr>\r\n\t\t\t<td colspan=\"2\">\r\n\t\t\t\t<input type=\"button\" class=\"btnButton2font\" onclick=\"commit();\" value=\"保存\"></input>\r\n\t\t\t\t<input type=\"button\" class=\"btnButton2font\" onclick=\"re_set();\" value=\"重置\"></input>\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\t</table>\r\n\t\r\n\t<div style=\"display:inline;position:absolute;top:52;left:215;\">\r\n\t\t<table style=\"display:");
      out.print(request.getAttribute("isDisplay"));
      out.write("\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n\t\t\t<tr>\r\n\t\t\t\t<td id=\"strength_L\" class=\"pass_top\"></td>\r\n\t\t\t\t<td id=\"strength_M\" class=\"pass_top\"></td>\r\n\t\t\t\t<td id=\"strength_H\" class=\"pass_top\"></td>\r\n\t\t\t</tr>\r\n\t\t\t<tr>\r\n\t\t\t\t<td id=\"strength_L_Font\" class=\"pass_bottom\">弱</td>\r\n\t\t\t\t<td id=\"strength_M_Font\" class=\"pass_bottom\">中</td>\r\n\t\t\t\t<td id=\"strength_H_Font\" class=\"pass_bottom\">强</td>\r\n\t\t\t</tr>\r\n\t\t</table>\r\n\t</div>\r\n\t</div>\r\n</tr>\r\n</table>\r\n\r\n</td>\r\n</tr>\r\n</trable>\r\n</fieldset>\r\n</td>\r\n</tr>\r\n</table>\r\n\r\n</td>\r\n</tr>\r\n</table>\r\n</body>\r\n</form>\r\n<script language=\"javascript\">\r\nfunction commit(){\r\n\tvar email = document.getElementById(\"email\").value;\r\n\tvar pwd = document.getElementById(\"pwd\").value;\r\n\tvar pwdcon = document.getElementById(\"pwdcon\").value;\r\n\tif(email==null||email==''){\r\n\t\talert(\"请输入邮箱地址！\");\r\n\t\treturn false;\r\n\t}\r\n\tif(pwd==null||pwd==''){\r\n\t\talert(\"请输入邮箱密码！\");\r\n\t\treturn false;\r\n\r\n\t}\r\n\tif(pwdcon==null||pwdcon==''){\r\n\t\talert(\"请再次输入邮箱密码！\");\r\n\t\treturn false;\r\n\r\n\t}\r\n\tif(pwd!=pwdcon){\r\n\t\talert(\"两次输入的密码不一致！\");\r\n");
      out.write("\t\treturn false;\r\n\r\n\t}\r\n\t document.getElementById(\"form1\").submit();\r\n\r\n}\r\n</script>\r\n\r\n<script src=\"/jsoa/js/util.js\"></script>");
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

  private boolean _jspx_meth_bean_005fmessage_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f0 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    boolean _jspx_th_bean_005fmessage_005f0_reused = false;
    try {
      _jspx_th_bean_005fmessage_005f0.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fmessage_005f0.setParent(null);
      // /personal_work/setup/tjgzw/personalwork_email_link_tjgzw.jsp(60,7) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f0.setBundle("personalwork");
      // /personal_work/setup/tjgzw/personalwork_email_link_tjgzw.jsp(60,7) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f0.setKey("personalset.passwordmodi");
      int _jspx_eval_bean_005fmessage_005f0 = _jspx_th_bean_005fmessage_005f0.doStartTag();
      if (_jspx_th_bean_005fmessage_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0);
      _jspx_th_bean_005fmessage_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fmessage_005f0, _jsp_getInstanceManager(), _jspx_th_bean_005fmessage_005f0_reused);
    }
    return false;
  }
}