/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:53:32 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.message;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class AddLimitJsp_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes = null;
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhtml;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005freadonly_005fproperty_005fcols_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;

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
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005freadonly_005fproperty_005fcols_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fhtml.release();
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.release();
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005freadonly_005fproperty_005fcols_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
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
      			"/jsoa/public/jsp/error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n<SCRIPT language=\"javascript\" src=\"/jsoa/js/openEndow.js\"></SCRIPT>\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

session.setAttribute("addNotCanDel","addNotCanDel");
String saveType = (String)session.getAttribute("saveType");//发送状态
session.setAttribute("saveType",null);
String sampleName = (String)session.getAttribute("sampleName");//发送状态
session.setAttribute("sampleName",null);
String sampleCount = (String)session.getAttribute("sampleCount");//发送状态
session.setAttribute("sampleCount",null);
String pageURL = request.getParameter("pageURL");
String pageTitle = "";
if(request.getParameter("pageTitle") !=null){
	pageTitle = request.getParameter("pageTitle");
}

String canChooseOrg = "cannot";
if(request.getAttribute("canChooseOrg") !=null){
	canChooseOrg = request.getAttribute("canChooseOrg").toString();
}
String range = session.getAttribute("browseRange")+"";

      out.write('\r');
      out.write('\n');
      //  html:html
      org.apache.struts.taglib.html.HtmlTag _jspx_th_html_005fhtml_005f0 = (org.apache.struts.taglib.html.HtmlTag) _005fjspx_005ftagPool_005fhtml_005fhtml.get(org.apache.struts.taglib.html.HtmlTag.class);
      boolean _jspx_th_html_005fhtml_005f0_reused = false;
      try {
        _jspx_th_html_005fhtml_005f0.setPageContext(_jspx_page_context);
        _jspx_th_html_005fhtml_005f0.setParent(null);
        int _jspx_eval_html_005fhtml_005f0 = _jspx_th_html_005fhtml_005f0.doStartTag();
        if (_jspx_eval_html_005fhtml_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n<head>\r\n<title>添加限制发送短信的条数</title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<link href=\"/jsoa/skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n</head>\r\n<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n<body  class=\"MainFrameBox Pupwin\" onload=\"load();resizeWin(700,350);\">\r\n<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"docBoxNoPanel\">\r\n  <tr>\r\n     <td>\r\n        ");
            //  html:form
            org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag) _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(org.apache.struts.taglib.html.FormTag.class);
            boolean _jspx_th_html_005fform_005f0_reused = false;
            try {
              _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
              _jspx_th_html_005fform_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
              // /message/AddLimitJsp.jsp(45,8) name = action type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setAction("/MessageSettingAction.do?action=AddLimit");
              // /message/AddLimitJsp.jsp(45,8) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setMethod("post");
              int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
              if (_jspx_eval_html_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t<input type=\"hidden\" name=\"saveType\" value=\"");
                  out.print(saveType);
                  out.write("\"/>\r\n\t\t<input type=\"hidden\" name=\"sampleName\" value=\"");
                  out.print(sampleName);
                  out.write("\"/>\r\n\t\t<input type=\"hidden\" name=\"sampleCount\" value=\"");
                  out.print(sampleCount);
                  out.write("\"/>\r\n\t\t<input type=\"hidden\" name=\"successContinue\" value=\"\"/>\r\n\t\t<input type=\"hidden\" name=\"pageURL\" value=\"");
                  out.print(pageURL);
                  out.write("\"/>\r\n\t\t<input type=\"hidden\" name=\"pageTitle\" value=\"");
                  out.print(pageTitle);
                  out.write("\"/>\r\n          <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"1\">\r\n\t\t     <tr>\r\n\t\t\t    <td width=\"20%\" nowrap>允许发送总条数&nbsp;<label class=\"mustFillcolor\">*</label>：</td>\r\n\t\t\t\t<td width=\"80%\">\r\n\t\t\t\t");
                  if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
                    return;
                  out.write("\r\n\t\t\t\t</td>\r\n\t\t\t </tr>\r\n\t\t\t <tr>\r\n\t\t\t    <td>每月允许发送条数&nbsp;<label class=\"mustFillcolor\">*</label>：</td>\r\n\t\t\t\t<td>\r\n\t\t\t\t");
                  if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
                    return;
                  out.write("\r\n\t\t\t\t</td>\r\n\t\t\t </tr>\r\n\t\t\t <tr>\r\n\t\t\t    <td>每天允许发送条数&nbsp;<label class=\"mustFillcolor\">*</label>：</td>\r\n\t\t\t\t<td>\r\n\t\t\t\t");
                  if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
                    return;
                  out.write("\r\n\t\t\t\t</td>\r\n\t\t\t </tr>\r\n\t\t\t <tr>\r\n\t\t\t    <td height=\"25\">使用范围&nbsp;<label class=\"mustFillcolor\">*</label>：</td>\r\n\t\t\t\t<td colspan=\"2\">\r\n\t\t\t\t\t   <span id=\"tospan\" style=\"display=''\">");
                  if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
                    return;
                  if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
                    return;

								if("can".equals(canChooseOrg))
								{
                  out.write("\r\n\t\t\t\t\t\t\t\t <img width=\"16\" height=\"16\" border=\"0\" src=\"/jsoa/images/select.gif\" style=\"cursor:pointer\" onClick=\"openEndow('sendLimitId','sendLimitMan',MessageSettingActionForm.sendLimitId.value,MessageSettingActionForm.sendLimitMan.value,'user','no','user','*0*')\">\r\n\t\t\t\t\t\t\t\t ");
}
                  out.write("\r\n\t\t\t\t\t\t\t\t ");

								 if("cannot".equals(canChooseOrg))
								 {
                  out.write("\r\n\t\t\t\t\t\t\t\t <img width=\"16\" height=\"16\" border=\"0\" src=\"/jsoa/images/select.gif\" style=\"cursor:pointer\" onClick=\"openEndow('sendLimitId','sendLimitMan',MessageSettingActionForm.sendLimitId.value,MessageSettingActionForm.sendLimitMan.value,'user','no','usergroup','*0*')\">\r\n\t\t\t\t\t\t\t\t ");
}
                  out.write("\r\n\t\t\t\t\t\t </span>\r\n\t\t\t\t\t  </td>\r\n\t\t\t\t </tr>\r\n\t\t\t\t <tr>\r\n\t\t\t\t\t<td colspan=\"2\">\r\n\t\t\t\t\t <button class=\"btnButton4font\" onClick=\"sendAndExit();\">保存退出</button>\r\n\t\t\t\t\t <button class=\"btnButton4font\" onClick=\"sendAndContinue();\">保存继续</button>\r\n\t\t\t\t\t <button class=\"btnButton2font\" onClick=\"resetMe();\">重置</button>\r\n\t\t\t\t\t <button class=\"btnButton2font\" onClick=\"window.close();\">退出</button>\r\n\t\t\t\t\t</td>\r\n\t\t\t\t </tr>\r\n\t\t\t</table>\r\n\t\t");
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
            out.write("\r\n\t</td>\r\n   </tr>\r\n</table>\r\n\r\n</body>\r\n");
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
      out.write("\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n<!--\r\n  function load()\r\n  {\r\n      var reload = document.all.MessageSettingActionForm.saveType.value;\r\n      if(reload == \"LimitSucceed\")\r\n      {\r\n          alert(\"添加成功！\");\r\n          window.opener.location=((window.location.href).substring(0,(window.location.href).indexOf(\"/jsoa\")))+\"/jsoa/MessageSettingAction.do?action=settingMessage\";\r\n          window.close();\r\n      }\r\n      if(reload == \"successContinue\")\r\n      {\r\n          document.all.MessageSettingActionForm.limitCount.value = \"\";\r\n          document.all.MessageSettingActionForm.sendLimitId.value = \"\";\r\n          document.all.MessageSettingActionForm.sendLimitMan.value = \"\";\r\n\t\t  document.all.MessageSettingActionForm.monthCount.value = \"\";\r\n\t\t  document.all.MessageSettingActionForm.dayCount.value = \"\";\r\n          alert(\"添加成功，请继续添加！\");\r\n          window.opener.location=((window.location.href).substring(0,(window.location.href).indexOf(\"/jsoa\")))+\"/jsoa/MessageSettingAction.do?action=settingMessage\";\r\n      }\r\n      if(reload == \"LimitFailure\")\r\n");
      out.write("      {\r\n          alert(\"抱歉，保存失败，请从新保存！\");\r\n      }\r\n      if(reload == \"existCount\")\r\n      {\r\n          alert(\"您好，您刚设置的\" + document.all.MessageSettingActionForm.sampleCount.value + \"条 限制发送条数已经存在了\");\r\n      }\r\n\r\n      if(reload == \"existName\")\r\n      {\r\n          alert(\"抱歉，\" + document.all.MessageSettingActionForm.sampleName.value + \"已经被限制了发送短信的条数，如果想在此增加请先删除其已经存在的记录\");\r\n      }\r\n\r\n  }\r\n\r\n   function sendAndExit(){\r\n\tvar sendLimitIdLength = document.getElementById(\"sendLimitId\").value.length;\r\n\tvar sendLimitManNameLength = document.getElementById(\"sendLimitMan\").value.length;\r\n\tif(sendLimitIdLength>4000 || sendLimitManNameLength>2000) {\r\n\t\talert(\"您选择的用户太多，请少选一些！\");\r\n\t\tdocument.getElementById(\"sendLimitMan\").focus;\r\n\t\treturn false;\r\n\t}\r\n       if(isNum() == 1&&isNum1() == 1&&isNum2() == 1 && checkCount() && checkCount2() && checkSendLimitMan())\r\n          document.MessageSettingActionForm.submit()\r\n   }\r\n\r\n\r\n   function resetMe(){\r\n       \r\n       document.getElementById(\"limitCount\").value=\"\";\r\n       document.getElementById(\"monthCount\").value=\"\";\r\n");
      out.write("       document.getElementById(\"dayCount\").value=\"\";\r\n       document.getElementById(\"sendLimitId\").value=\"\";\r\n       document.getElementById(\"sendLimitMan\").value=\"\";\r\n       \r\n   }\r\n\r\n\r\n function sendAndContinue()\r\n {\r\n\tvar sendLimitIdLength = document.getElementById(\"sendLimitId\").value.length;\r\n\tvar sendLimitManNameLength = document.getElementById(\"sendLimitMan\").value.length;\r\n\tif(sendLimitIdLength>4000 || sendLimitManNameLength>2000) {\r\n\t\talert(\"您选择的用户太多，请少选一些！\");\r\n\t\tdocument.getElementById(\"sendLimitMan\").focus;\r\n\t\treturn false;\r\n\t}\r\n\tif(isNum() == 1&&isNum1() == 1&&isNum2() == 1 && checkCount() && checkCount2() && checkSendLimitMan())\r\n\t{\r\n\t   document.all.MessageSettingActionForm.successContinue.value = \"successContinue\";\r\n\t   document.MessageSettingActionForm.submit();\r\n\t}\r\n\r\n }\r\n\r\n function checkCount(){\r\n\t var count= document.getElementById(\"limitCount\").value;\r\n\t var mcount= document.getElementById(\"monthCount\").value;\r\n\t if(eval(mcount)>eval(count)){\r\n\t\t alert(\"每月允许发送条数不能大于允许发送总条数\");\r\n\t\t return false;\r\n");
      out.write("\t }\r\n\r\n\t return true;\r\n }\r\nfunction checkCount2(){\r\n\t var mcount= document.getElementById(\"monthCount\").value;\r\n\t var dcount= document.getElementById(\"dayCount\").value;\r\n\r\n\t if(eval(dcount)>eval(mcount)){\r\n\t\t alert(\"每天允许发送条数不能大于每月允许发送条数\");\r\n\t\t return false;\r\n\t }\r\n\r\n\t return true;\r\n }\r\n\r\n function checkSendLimitMan(){\r\n\tif(document.MessageSettingActionForm.sendLimitMan.value==''){\r\n\t\talert(\"适用用户不能为空！\");\r\n\t\treturn false;\r\n\t}\r\n\treturn true;\r\n}\r\n\r\n function isNum()\r\n {\r\n     var tage = 0;\r\n     var count= document.MessageSettingActionForm.limitCount.value;\r\n     if(count == \"\")\r\n     {\r\n         alert(\"限制条数不能为空，请添加！\");\r\n         return tage;\r\n     }\r\n     var test = isNaN(count);\r\n     if(!test){\r\n        if(chksafe(count) == 0)\r\n           alert(\"数字中不可以含有 +  -  . 空格 等字符\")\r\n        else\r\n           {\r\n               tage = 1;\r\n           }\r\n     }\r\n\r\n     else\r\n        alert(\"含有非数字项\")\r\n\r\n     return tage;\r\n }\r\n\r\n function isNum1()\r\n {\r\n     var tage = 0;\r\n     var count= document.MessageSettingActionForm.monthCount.value;\r\n");
      out.write("     if(count == \"\")\r\n     {\r\n         alert(\"每月允许发送条数不能为空，请添加！\");\r\n         return tage;\r\n     }\r\n     var test = isNaN(count);\r\n     if(!test){\r\n        if(chksafe(count) == 0)\r\n           alert(\"数字中不可以含有 +  -  . 空格 等字符\")\r\n        else\r\n           {\r\n               tage = 1;\r\n           }\r\n     }\r\n\r\n     else\r\n        alert(\"含有非数字项\")\r\n\r\n     return tage;\r\n }\r\n function isNum2()\r\n {\r\n     var tage = 0;\r\n     var count= document.MessageSettingActionForm.dayCount.value;\r\n     if(count == \"\")\r\n     {\r\n         alert(\"每天允许发送条数不能为空，请添加！\");\r\n         return tage;\r\n     }\r\n     var test = isNaN(count);\r\n     if(!test){\r\n        if(chksafe(count) == 0)\r\n           alert(\"数字中不可以含有 +  -  . 空格 等字符\")\r\n        else\r\n           {\r\n               tage = 1;\r\n           }\r\n     }\r\n\r\n     else\r\n        alert(\"含有非数字项\")\r\n\r\n     return tage;\r\n }\r\n\r\n function chksafe(a)\r\n{\r\n   var fibdn = new Array (\".\" ,\"-\",\"+\",\" \");\r\n   var i=fibdn.length;\r\n   var j=a.length;\r\n   var temp1;var temp2;\r\n   for (var ii=0;ii<i;ii++)\r\n   {\r\n       for (var jj=0;jj<j;jj++)\r\n");
      out.write("       {\r\n           temp1=a.charAt(jj);\r\n           temp2=fibdn[ii];\r\n           if (temp1==temp2)\r\n           {\r\n               return 0;\r\n           }\r\n       }\r\n   }\r\n   return 1;\r\n}\r\n\r\n\r\n\r\n\r\n\r\n//>-->\r\n</SCRIPT>\r\n\r\n<script src=\"/jsoa/js/util.js\"></script>");
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

  private boolean _jspx_meth_html_005ftext_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f0 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
    boolean _jspx_th_html_005ftext_005f0_reused = false;
    try {
      _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005ftext_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /message/AddLimitJsp.jsp(56,4) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setStyleClass("inputText");
      // /message/AddLimitJsp.jsp(56,4) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setProperty("limitCount");
      // /message/AddLimitJsp.jsp(56,4) name = maxlength type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setMaxlength("18");
      // /message/AddLimitJsp.jsp(56,4) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setStyle("width:100%");
      int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
      if (_jspx_th_html_005ftext_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
      _jspx_th_html_005ftext_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005ftext_005f0, _jsp_getInstanceManager(), _jspx_th_html_005ftext_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005ftext_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f1 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
    boolean _jspx_th_html_005ftext_005f1_reused = false;
    try {
      _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
      _jspx_th_html_005ftext_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /message/AddLimitJsp.jsp(62,4) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f1.setStyleClass("inputText");
      // /message/AddLimitJsp.jsp(62,4) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f1.setProperty("monthCount");
      // /message/AddLimitJsp.jsp(62,4) name = maxlength type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f1.setMaxlength("18");
      // /message/AddLimitJsp.jsp(62,4) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f1.setStyle("width:100%");
      int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
      if (_jspx_th_html_005ftext_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
      _jspx_th_html_005ftext_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005ftext_005f1, _jsp_getInstanceManager(), _jspx_th_html_005ftext_005f1_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005ftext_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f2 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
    boolean _jspx_th_html_005ftext_005f2_reused = false;
    try {
      _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
      _jspx_th_html_005ftext_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /message/AddLimitJsp.jsp(68,4) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f2.setStyleClass("inputText");
      // /message/AddLimitJsp.jsp(68,4) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f2.setProperty("dayCount");
      // /message/AddLimitJsp.jsp(68,4) name = maxlength type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f2.setMaxlength("18");
      // /message/AddLimitJsp.jsp(68,4) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f2.setStyle("width:100%");
      int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
      if (_jspx_th_html_005ftext_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
      _jspx_th_html_005ftext_005f2_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005ftext_005f2, _jsp_getInstanceManager(), _jspx_th_html_005ftext_005f2_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005ftextarea_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:textarea
    org.apache.struts.taglib.html.TextareaTag _jspx_th_html_005ftextarea_005f0 = (org.apache.struts.taglib.html.TextareaTag) _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005freadonly_005fproperty_005fcols_005fnobody.get(org.apache.struts.taglib.html.TextareaTag.class);
    boolean _jspx_th_html_005ftextarea_005f0_reused = false;
    try {
      _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005ftextarea_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /message/AddLimitJsp.jsp(74,45) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setStyleClass("inputTextarea");
      // /message/AddLimitJsp.jsp(74,45) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setProperty("sendLimitMan");
      // /message/AddLimitJsp.jsp(74,45) name = cols type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setCols("50");
      // /message/AddLimitJsp.jsp(74,45) name = rows type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setRows("8");
      // /message/AddLimitJsp.jsp(74,45) name = readonly type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setReadonly(true);
      // /message/AddLimitJsp.jsp(74,45) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setStyle("width:80%");
      int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
      if (_jspx_th_html_005ftextarea_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005freadonly_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
      _jspx_th_html_005ftextarea_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005ftextarea_005f0, _jsp_getInstanceManager(), _jspx_th_html_005ftextarea_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005fhidden_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_005fhidden_005f0 = (org.apache.struts.taglib.html.HiddenTag) _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    boolean _jspx_th_html_005fhidden_005f0_reused = false;
    try {
      _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005fhidden_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /message/AddLimitJsp.jsp(74,168) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fhidden_005f0.setProperty("sendLimitId");
      int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
      if (_jspx_th_html_005fhidden_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
      _jspx_th_html_005fhidden_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fhidden_005f0, _jsp_getInstanceManager(), _jspx_th_html_005fhidden_005f0_reused);
    }
    return false;
  }
}
