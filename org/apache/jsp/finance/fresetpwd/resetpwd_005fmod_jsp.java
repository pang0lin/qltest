/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:06:18 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.finance.fresetpwd;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.system.util.StaticParam;

public final class resetpwd_005fmod_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("com.js.system.util.StaticParam");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhtml;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleId_005fstyleClass_005fproperty_005fonkeyup_005fonblur_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleId_005fstyleClass_005fproperty_005fonblur_005fnobody;

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
    _005fjspx_005ftagPool_005fhtml_005fhtml = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleId_005fstyleClass_005fproperty_005fonkeyup_005fonblur_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleId_005fstyleClass_005fproperty_005fonblur_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fhtml.release();
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.release();
    _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleId_005fstyleClass_005fproperty_005fonkeyup_005fonblur_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleId_005fstyleClass_005fproperty_005fonblur_005fnobody.release();
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
String local = session.getAttribute("org.apache.struts.action.LOCALE").toString();
String userAccount = session.getAttribute("userAccount").toString().toLowerCase();
String domainId=(String)session.getAttribute("domainId");
String id=(String)request.getParameter("id");

      out.write("\r\n<!--\r\n *  ??????:??????????????????????????? ????????????\r\n * <p>Description:??????????????????</p>\r\n * <p>Copyright: Copyright (c) 2008</p>\r\n * <p>Company: Beijing JS  C0. Ltd.</p>\r\n * \r\n * @version jsoa 1.0\r\n */\r\n ???????????????\r\n\r\n-->\r\n");
      //  html:html
      org.apache.struts.taglib.html.HtmlTag _jspx_th_html_005fhtml_005f0 = (org.apache.struts.taglib.html.HtmlTag) _005fjspx_005ftagPool_005fhtml_005fhtml.get(org.apache.struts.taglib.html.HtmlTag.class);
      boolean _jspx_th_html_005fhtml_005f0_reused = false;
      try {
        _jspx_th_html_005fhtml_005f0.setPageContext(_jspx_page_context);
        _jspx_th_html_005fhtml_005f0.setParent(null);
        int _jspx_eval_html_005fhtml_005f0 = _jspx_th_html_005fhtml_005f0.doStartTag();
        if (_jspx_eval_html_005fhtml_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\r\n<link href=\"/jsoa/skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<title>");
            if (_jspx_meth_bean_005fmessage_005f0(_jspx_th_html_005fhtml_005f0, _jspx_page_context))
              return;
            out.write("</title>\r\n<script language=\"JavaScript\" src=\"/jsoa/js/resource/");
            out.print(local);
            out.write("/CommonResource.js\" type=\"text/javascript\"></script>\r\n<script language=\"JavaScript\" src=\"js/date0.js\"></script>\r\n<script src=\"/jsoa/js/resource/");
            out.print(local);
            out.write("/PersonalworkResource.js\" language=\"javascript\"></script>\r\n<style type=\"text/css\">\r\n\t.color{color:#FF0000;}\r\n\t.wrong_info{vertical-align:middle;}\r\n\t.right_info{vertical-align:middle;}\r\n\t.pass_top{width: 48px; background-color:gray; height: 8px; overflow: hidden; border:1px solid #d0d0d0;}\r\n\t.pass_top_show{width: 48px; background-color:#0074DB; height: 8px; overflow: hidden; border:1px solid #d0d0d0;}\r\n\t.pass_bottom{font-family: \"??????\"; color: #666; padding-left:17px;border:0px;}\r\n\t.pass_bottom_show{font-family: \"??????\"; color: #666; font-weight:bold; padding-left:17px;border:0px;}\r\n\t.block{margin:10px 0px;text-align:left;padding-top:10px;}\r\n\t#contentDiv{position:relative;}\r\n\t.span_middle{padding-bottom:10px;}\r\n\t.btn{width:50px;height:20px;cursor:pointer;}\r\n\t.input{height:20px;}\r\n</style>\r\n<script type=\"text/javascript\">\r\n\r\n\tvar flag=location.href.indexOf(\"personalwork_passwordsetup\")\r\n\tvar imgPathRight;\r\n\tvar imgPathWrong;\r\n\t\r\n\t\r\n\timgPathRight=\"images/button_ok.gif\";\r\n\timgPathWrong=\"images/button_cancel.png\";\t\r\n\t\r\n");
            out.write("\t//?????????string?????????????????????\r\n\tString.prototype.Trim = function(){ return Trim(this);}\r\n\tString.prototype.LTrim = function(){return LTrim(this);}\r\n\tString.prototype.RTrim = function(){return RTrim(this);}\r\n//?????????????????????\r\n\tfunction LTrim(str)\r\n\t{\r\n\t    var i;\r\n\t    for(i=0;i<str.length;i++)\r\n\t    {\r\n\t        if(str.charAt(i)!=\" \"&&str.charAt(i)!=\" \")break;\r\n\t    }\r\n\t    str=str.substring(i,str.length);\r\n\t    return str;\r\n\t}\r\n\tfunction RTrim(str)\r\n\t{\r\n\t    var i;\r\n\t    for(i=str.length-1;i>=0;i--)\r\n\t    {\r\n\t        if(str.charAt(i)!=\" \"&&str.charAt(i)!=\" \")break;\r\n\t    }\r\n\t    str=str.substring(0,i+1);\r\n\t    return str;\r\n\t}\r\n\tfunction Trim(str)\r\n\t{\r\n\t    return LTrim(RTrim(str));\r\n\t}\r\n</script>\r\n\r\n</head>\r\n\r\n<body onload=\"init();\"  class=\"MainFrameBox\">\r\n\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\" class=\"docBox\">\r\n<tr>\r\n<td>\r\n <fieldset>\r\n    <legend>????????????????????????</legend>\r\n\r\n<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" >\r\n <tr>\r\n    <td valign=\"top\">\r\n\t<div id=\"contentDiv\">\r\n\t");
            //  html:form
            org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag) _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(org.apache.struts.taglib.html.FormTag.class);
            boolean _jspx_th_html_005fform_005f0_reused = false;
            try {
              _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
              _jspx_th_html_005fform_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
              // /finance/fresetpwd/resetpwd_mod.jsp(107,1) name = action type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setAction("/fUserAction.do?action=modPwd");
              // /finance/fresetpwd/resetpwd_mod.jsp(107,1) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setMethod("post");
              int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
              if (_jspx_eval_html_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t<!-- ????????? -->\r\n\t<div id=\"f_pwd_new\" class=\"block\">\r\n\t\t<span class=\"span_middle\"><font color=\"red\" >&nbsp;*</font>?????????&nbsp;&nbsp;</span>\r\n\t\t<span>");
                  if (_jspx_meth_html_005fpassword_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
                    return;
                  out.write("</span>\r\n\t\t<span id=\"fNewPasswordMsg\"></span>\r\n\t</div>\r\n\t<!-- ???????????? -->\r\n\t<div id=\"f_pwd_confirm\" class=\"block\">\r\n\t\t<span class=\"span_middle\"><font color=\"red\" >&nbsp;*</font>????????????</span>\r\n\t\t<span>");
                  if (_jspx_meth_html_005fpassword_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
                    return;
                  out.write("</span>\r\n\t\t<span id=\"fConfirmPasswordMsg\"></span>\r\n\t</div>\r\n\t<input type=\"hidden\" id=\"id\" name=\"id\" value=\"");
                  out.print(id);
                  out.write("\"/>\r\n\t");
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
            out.write("\r\n\t<br/>\r\n\t<table>\r\n\t\t<tr>\r\n\t\t\t<td colspan=\"2\">\r\n\t\t\t\t<input type=\"button\" class=\"btnButton2font\" onclick=\"fCommit();\" value=\"??????\"/>\r\n\t\t\t\t<button class=\"btnButton2font\" onclick=\"window.close();\">??????</button>\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\t</table>\r\n\t\r\n\t</div>\r\n\t</td>\r\n</tr>\r\n</table>\r\n</fieldset>\r\n</td>\r\n</tr>\r\n</table>\r\n\r\n</td>\r\n</tr>\r\n</table>\r\n</body>\r\n");
            int evalDoAfterBody = _jspx_th_html_005fhtml_005f0.doAfterBody();
            if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
              break;
          } while (true);
        }
        if (_jspx_th_html_005fhtml_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return;
        }
        _005fjspx_005ftagPool_005fhtml_005fhtml.reuse(_jspx_th_html_005fhtml_005f0);
        _jspx_th_html_005fhtml_005f0_reused = true;
      } finally {
        org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fhtml_005f0, _jsp_getInstanceManager(), _jspx_th_html_005fhtml_005f0_reused);
      }
      out.write("\r\n<script language=\"javascript\">\r\n//????????????\r\nfunction init() {\r\n\r\n    //????????????????????????\r\n    ");

    String message =(null==request.getAttribute("message")?"":request.getAttribute("message").toString());
    if(message!=null&&!"".equals(message)){
    
      out.write("\r\n         alert(\"");
      out.print(message);
      out.write("\") ;\r\n          window.close();\r\n    ");

    }
      out.write("\r\n    fUserActionForm.fNewPass.value = \"\" ;\r\n    fUserActionForm.fNewPassCon.value= \"\" ;\r\n   \r\n}\r\n\r\n//??????--??????\r\nfunction fCommit() {\r\n    fValidateAll();\r\n}\r\nfunction fValidateAll()\r\n{\r\n\tvar newPassword=document.getElementsByName(\"fNewPass\")[0].value;\r\n\tvar confirmPassword=document.getElementsByName(\"fNewPassCon\")[0].value;\r\n\t\r\n\tif(fCheckNewPassword(newPassword)&&fCheckConfirmPassword(confirmPassword))\r\n\t{\r\n\t\tdocument.forms[0].submit();\r\n\t}\t\t\t\r\n}\r\nfunction fCheckConfirmPassword(value)//??????????????????--??????\r\n\t{\r\n\t\tvar display=document.getElementById(\"fConfirmPasswordMsg\");\r\n\t\tvar imgright=document.createElement(\"img\");\r\n\t\timgright.setAttribute(\"src\",imgPathRight);\r\n\t\timgright.setAttribute(\"alt\",\"right\");\r\n\t\timgright.setAttribute(\"height\",16);\r\n\t\timgright.setAttribute(\"width\",16);\r\n\t\toldValue=document.getElementsByName(\"fNewPass\")[0].value;\r\n\t\tvar displayControl=document.getElementById(\"fConfirmPasswordMsg\");\r\n\t\tdisplayControl.innerHTML=\"\";\r\n\t\tif(value.Trim()==oldValue.Trim())\r\n\t\t{\r\n\t\t\tdisplayControl.appendChild(imgright);\r\n");
      out.write("\t\t\treturn true;\r\n\t\t}\r\n\t\telse\r\n\t\t{\r\n\t\t\tdisplayControl.innerHTML=\"????????????????????????!\";\r\n\t\t\tdisplayControl.style.color=\"red\";\r\n\t\t\treturn false;\r\n\t\t}\r\n\t}\r\n\r\n//??????????????????????????????????????????????????????,?????????????????????????????????????????? \r\n\tfunction pwStrength(pwd)\r\n\t{ \r\n\t\tvar low=document.getElementById(\"strength_L\");\r\n\t\tvar middle=document.getElementById(\"strength_M\");\r\n\t\tvar high=document.getElementById(\"strength_H\");\r\n\t\tvar low_font=document.getElementById(\"strength_L_Font\");\r\n\t\tvar middle_font=document.getElementById(\"strength_M_Font\");\r\n\t\tvar high_font=document.getElementById(\"strength_H_Font\");\r\n\t\tif(pwd.length==0)\r\n\t\t{\r\n\t\t\tlow.className=middle.className=high.className=\"pass_top\";\r\n\t\t\tlow_font.className=middle_font.className=high_font.className=\"pass_bottom\";\r\n\t\t\t\r\n\t\t}\r\n\t\tif (pwd==null||pwd=='')\r\n\t\t{ \r\n\t\t\tlow.className=low.className=low.className=\"pass_top\";\r\n\t\t\tlow_font.className=middle_font.className=high_font.className=\"pass_bottom\";\r\n\t\t} \r\n\t\telse\r\n\t\t{ \r\n\t\t\tS_level=checkStrong(pwd); \r\n\t\t\tswitch(S_level)\r\n\t\t \t{ \r\n\t\t\t\tcase 1:\r\n\t\t\t\t\tlow.className=\"pass_top_show\";\r\n");
      out.write("\t\t\t\t\tmiddle.className=high.className=\"pass_top\";\r\n\t\t\t\t\tlow_font.className=\"pass_bottom_show\";\r\n\t\t\t\t\tmiddle_font.className=high_font.className=\"pass_bottom\";\r\n\t\t\t\t\tbreak; \r\n\t\t\t\tcase 2:\r\n\t\t\t\t\tlow.className=middle.className=\"pass_top_show\";\r\n\t\t\t\t\thigh.className=\"pass_top\";\r\n\t\t\t\t\tmiddle_font.className=\"pass_bottom_show\";\r\n\t\t\t\t\tlow_font.className=high_font.className=\"pass_bottom\";\r\n\t\t\t\t\tbreak; \r\n\t\t\t\tcase 3:\r\n\t\t\t\t\tlow.className=middle.className=high.className=\"pass_top_show\";\r\n\t\t\t\t\tlow_font.className=middle_font.className=\"pass_bottom\";\r\n\t\t\t\t\thigh_font.className=\"pass_bottom_show\";\r\n\t\t\t\t\tbreak;\t\r\n\t\t\t\tdefault:\r\n\t\t\t\t\tbreak;\t\t\t\t\r\n\t\t\t} \r\n\t\t} \r\n\t\treturn; \r\n\t} \r\n\r\nfunction fCheckNewPassword(value)//???????????????--??????\r\n{\r\n\t\tvar display=document.getElementById(\"fNewPasswordMsg\");\r\n\t\tvalue=value.Trim();\r\n\t\r\n\t\tvar regExp=/^[a-zA-Z0-9\\`\\.\\~\\!\\@#$\\%\\^\\&\\_\\-\\\\\\/\\:\\*\\?\\<\\>\\|\\(\\)\\+\\[\\]\\}\\{\\,]{1,32}$/;\r\n\t\t\r\n\t\treturn executeValidate(value,regExp,\"?????????????????????????????????,???????????????1-32???,???????????? \\'???\\\" ?????????!\",display);\r\n\t\t\r\n}\t\r\n/*\r\n\t\t??????:\r\n\t\tvalidateText:?????????????????????\r\n\t\tregExp:???????????????\r\n");
      out.write("\t\trightMsg:?????????????????????\r\n\t\terrorMsg:?????????????????????\r\n\t\tdisplayControl:??????????????????\r\n\t*/\r\n\tfunction executeValidate(validateText,regExp,errorMsg,displayControl)\r\n\t{\r\n\t\r\n\t\tvar imgright=document.createElement(\"img\");\r\n\t\timgright.setAttribute(\"src\",imgPathRight);\r\n\t\timgright.setAttribute(\"alt\",\"right\");\r\n\t\timgright.setAttribute(\"height\",16);\r\n\t\timgright.setAttribute(\"width\",16);\r\n\t\r\n\t\tvar msg=validate(validateText,regExp,errorMsg);\r\n\t\tdisplayControl.innerHTML=\"\";\r\n\t\t//??????????????????????????????????????????????????????\r\n\t\tif(msg.length>0)//????????????\r\n\t\t{\r\n\t\t\tdisplayControl.innerHTML=errorMsg;\r\n\t\t\tdisplayControl.style.color=\"red\";\r\n\t\t\treturn false;\r\n\t\t}\r\n\t\telse//????????????\r\n\t\t{\r\n\t\t\tdisplayControl.appendChild(imgright);\r\n\t\t\treturn true;\r\n\t\t}\r\n\t}\r\n\t\t/*\r\n\t\t??????:\r\n\t\tvalidateText:?????????????????????\r\n\t\tregExp:???????????????\r\n\t\terrorMsg:?????????????????????\r\n\t*/\r\n\tfunction validate(validateText,regExp,errorMsg)\r\n\t{\r\n\t\r\n\t\tvar msg=errorMsg;\r\n\t\tvar temp =regExp.exec(validateText);\r\n\t\tif(temp!=null)\r\n\t\t{\r\n\t\t\tif(temp[0]==validateText)msg=\"\";\r\n\t\t\treturn msg;\r\n\t\t}\r\n\t\telse return msg;\r\n\t}\r\n\t\t//checkStrong?????? \r\n\t//??????????????????????????? \r\n\tfunction checkStrong(pwd)\r\n");
      out.write("\t{ \r\n\t\tif (pwd.length<=6) \r\n\t\treturn 1; //???????????? \r\n\t\tModes=0; \r\n\t\tfor (i=0;i<pwd.length;i++)\r\n\t\t{ \r\n\t\t\t//???????????????????????????????????????????????????????????????. \r\n\t\t\tModes|=CharMode(pwd.charCodeAt(i)); \r\n\t\t} \r\n\t\treturn bitTotal(Modes); \r\n\t} \r\n\t//CharMode?????? \r\n\t//????????????????????????????????????. \r\n\tfunction CharMode(input)\r\n\t{ \r\n\t\tif (input>=48 && input <=57) //?????? \r\n\t\treturn 1; \r\n\t\tif (input>=65 && input <=90) //???????????? \r\n\t\treturn 2; \r\n\t\tif (input>=97 && input <=122) //?????? \r\n\t\treturn 4; \r\n\t\telse \r\n\t\treturn 8; //???????????? \r\n\t} \r\n\t//bitTotal?????? \r\n\t//??????????????????????????????????????????????????? \r\n\tfunction bitTotal(num)\r\n\t{ \r\n\t\tmodes=0; \r\n\t\tfor (i=0;i<4;i++)\r\n\t\t{ \r\n\t\t\tif (num & 1) modes++; \r\n\t\t\tnum>>>=1; \r\n\t\t} \r\n\t\treturn modes; \r\n\t} \r\n</script>\r\n\r\n<script src=\"/jsoa/js/util.js\"></script>");
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

  private boolean _jspx_meth_bean_005fmessage_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fhtml_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f0 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    boolean _jspx_th_bean_005fmessage_005f0_reused = false;
    try {
      _jspx_th_bean_005fmessage_005f0.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fmessage_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
      // /finance/fresetpwd/resetpwd_mod.jsp(34,7) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f0.setBundle("personalwork");
      // /finance/fresetpwd/resetpwd_mod.jsp(34,7) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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

  private boolean _jspx_meth_html_005fpassword_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:password
    org.apache.struts.taglib.html.PasswordTag _jspx_th_html_005fpassword_005f0 = (org.apache.struts.taglib.html.PasswordTag) _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleId_005fstyleClass_005fproperty_005fonkeyup_005fonblur_005fnobody.get(org.apache.struts.taglib.html.PasswordTag.class);
    boolean _jspx_th_html_005fpassword_005f0_reused = false;
    try {
      _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005fpassword_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /finance/fresetpwd/resetpwd_mod.jsp(111,8) name = styleId type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fpassword_005f0.setStyleId("fNewPass");
      // /finance/fresetpwd/resetpwd_mod.jsp(111,8) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fpassword_005f0.setProperty("fNewPass");
      // /finance/fresetpwd/resetpwd_mod.jsp(111,8) name = onkeyup type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fpassword_005f0.setOnkeyup("pwStrength(this.value)");
      // /finance/fresetpwd/resetpwd_mod.jsp(111,8) name = onblur type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fpassword_005f0.setOnblur("fCheckNewPassword(this.value);");
      // /finance/fresetpwd/resetpwd_mod.jsp(111,8) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fpassword_005f0.setStyleClass("input");
      int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
      if (_jspx_th_html_005fpassword_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleId_005fstyleClass_005fproperty_005fonkeyup_005fonblur_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
      _jspx_th_html_005fpassword_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fpassword_005f0, _jsp_getInstanceManager(), _jspx_th_html_005fpassword_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005fpassword_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:password
    org.apache.struts.taglib.html.PasswordTag _jspx_th_html_005fpassword_005f1 = (org.apache.struts.taglib.html.PasswordTag) _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleId_005fstyleClass_005fproperty_005fonblur_005fnobody.get(org.apache.struts.taglib.html.PasswordTag.class);
    boolean _jspx_th_html_005fpassword_005f1_reused = false;
    try {
      _jspx_th_html_005fpassword_005f1.setPageContext(_jspx_page_context);
      _jspx_th_html_005fpassword_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /finance/fresetpwd/resetpwd_mod.jsp(117,8) name = styleId type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fpassword_005f1.setStyleId("fNewPassCon");
      // /finance/fresetpwd/resetpwd_mod.jsp(117,8) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fpassword_005f1.setProperty("fNewPassCon");
      // /finance/fresetpwd/resetpwd_mod.jsp(117,8) name = onblur type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fpassword_005f1.setOnblur("fCheckConfirmPassword(this.value)");
      // /finance/fresetpwd/resetpwd_mod.jsp(117,8) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fpassword_005f1.setStyleClass("input");
      int _jspx_eval_html_005fpassword_005f1 = _jspx_th_html_005fpassword_005f1.doStartTag();
      if (_jspx_th_html_005fpassword_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleId_005fstyleClass_005fproperty_005fonblur_005fnobody.reuse(_jspx_th_html_005fpassword_005f1);
      _jspx_th_html_005fpassword_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fpassword_005f1, _jsp_getInstanceManager(), _jspx_th_html_005fpassword_005f1_reused);
    }
    return false;
  }
}
