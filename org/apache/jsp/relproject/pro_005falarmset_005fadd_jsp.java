/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:45:03 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.relproject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import com.js.util.util.BrowserJudge;

public final class pro_005falarmset_005fadd_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(3);
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-logic.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-html.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-bean.tld", Long.valueOf(1499751390000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.util.util.BrowserJudge");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fstyle_005fsize_005fproperty_005fmaxlength_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fstyle_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody;

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
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fstyle_005fsize_005fproperty_005fmaxlength_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fstyle_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.release();
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fstyle_005fsize_005fproperty_005fmaxlength_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fstyle_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.release();
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

      out.write("\r\n<html>\r\n<head>\r\n<META HTTP-EQUIV=\"pragma\" CONTENT=\"no-cache\"> \r\n<META HTTP-EQUIV=\"Cache-Control\" CONTENT=\"no-cache, must-revalidate\"> \r\n<META HTTP-EQUIV=\"expires\" CONTENT=\"Wed, 26 Feb 1997 08:21:57 GMT\">\r\n<title>新增项目提醒设置</title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n\r\n<link href=\"skin/blue/style.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"/jsoa/js/imenu.js\"></script>\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n<link href=\"/jsoa/public/color/color.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script language=javascript src=\"/jsoa/public/color/color.js\"></script>\r\n</head>\r\n\r\n<body leftmargin=\"0\" topmargin=\"0\"  class=\"MainFrameBox Pupwin\">\r\n");
      //  html:form
      org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag) _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(org.apache.struts.taglib.html.FormTag.class);
      boolean _jspx_th_html_005fform_005f0_reused = false;
      try {
        _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
        _jspx_th_html_005fform_005f0.setParent(null);
        // /relproject/pro_alarmset_add.jsp(28,0) name = action type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_html_005fform_005f0.setAction("/proAlarmSetAction.do?action=alarmSetAdd");
        // /relproject/pro_alarmset_add.jsp(28,0) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_html_005fform_005f0.setMethod("post");
        int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
        if (_jspx_eval_html_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"docBoxNoPanel\" style=\"border:0px #000000 solid;\">\r\n <tr>\r\n    <td valign=\"top\">\r\n\t   <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"1\">\r\n           <tr>\r\n            <td width=\"7%\">提醒名称&nbsp;：</td>\r\n            <td align=\"left\">\r\n\t\t\t");
            if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
              return;
            out.write("<label class=\"MustFillColor\">*</label>\r\n\t\t\t</td>\r\n           <tr>\r\n           <tr>\r\n            <td >提醒天数&nbsp;：</td>\r\n            <td align=\"left\">\r\n\t\t\t");
            if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
              return;
            out.write("<label class=\"MustFillColor\">*</label>\r\n\t\t\t</td>\r\n           <tr>\r\n           <tr>\r\n            <td >提醒颜色&nbsp;：</td>\r\n            <td align=\"left\">\r\n            <div style=\"padding:10px 0px;\">\r\n            \t");
if(BrowserJudge.isMSIE(request)){ 
            out.write("\r\n            \t");
            if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
              return;
            out.write("\r\n       \t    \t");
}else{ 
            out.write("\r\n       \t    \t");
            if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
              return;
            out.write("\r\n       \t    \t");
} 
            out.write(" \r\n       \t    \t<div id=\"colorDisplay\" style=\"vertical-align:middle;display:inline;line-height:15px;height:15px;width:15px;cursor:pointer;\">&nbsp;&nbsp;&nbsp;</div>\r\n\t\t\t</div>\r\n\t\t\t</td>\r\n           <tr>\r\n            <tr>\r\n            <td >提醒状态&nbsp;：</td>\r\n            <td align=\"left\">\r\n\t\t\t");
            if (_jspx_meth_html_005fradio_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
              return;
            out.write("有效\r\n\t\t\t");
            if (_jspx_meth_html_005fradio_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
              return;
            out.write("无效\r\n\t\t\t</td>\r\n           <tr>\r\n\r\n\t  </table>\r\n\t  <table>\r\n\t\t<tr>\r\n\t\t  <td height=\"60\">\r\n\t\t     <input type=\"button\" class=\"btnButton4font\" style=\"cursor:pointer\" onClick=\"saveAndExit()\" value=\"确定\"/>\r\n\t\t     <input type=\"button\" class=\"btnButton4font\" style=\"cursor:pointer\" onClick=\"javascript:resetMe()\" value=\"重置\"/>\r\n\t\t     <input type=\"button\" class=\"btnButton4font\" style=\"cursor:pointer\" onClick=\"javascript:window.close();\" value=\"退出\"/>\r\n  \r\n\t\t  </td>\r\n\t    </tr>\r\n\t  </table>\r\n\t  \r\n\t</td>\r\n  </tr>\r\n</table>\r\n");
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
      out.write("\r\n</body>\r\n\r\n<script language=\"JavaScript\">\r\n<!--\r\n//保存\r\nfunction saveAndExit(){\r\n  if(checkForm()){\r\n     document.proAlarmSetActionForm.submit();\r\n  }\r\n}\r\nfunction resetMe(){\r\n   document.proAlarmSetActionForm.reset();\r\n}\r\nfunction checkForm(){\r\n   var alarmName=document.getElementById(\"alarmName\");\r\n   if(null==alarmName.value||\"\"==alarmName.value){\r\n\t   alert(\"请输入提醒名称！\");\r\n\t   alarmName.focus();\r\n\t   return false;\r\n   }\r\n   var alarmDays=document.getElementById(\"alarmDays\");\r\n   if(null==alarmDays.value||\"\"==alarmDays.value){\r\n\t   alert(\"请输入提醒天数！\");\r\n\t   alarmDays.focus();\r\n\t   return false;\r\n   }\r\n   var t=/^[0-9]*[1-9][0-9]*$/ ; \r\n\tif(!t.test(alarmDays.value)){\r\n\t\talert(\"提醒天数请输入正整数！\");\r\n\t\talarmDays.focus();\r\n\t\treturn false;\r\n\t}\r\n   var alarmColor=document.getElementById(\"alarmColor\");\r\n   if(null==alarmColor.value||\"\"==alarmColor.value){\r\n\t   alert(\"请输入提醒颜色！\");\r\n\t   alarmColor.focus();\r\n\t   return false;\r\n   }\r\n   return true;\r\n}\r\nvar ocolorPopup = window.createPopup(); \r\nvar ecolorPopup=null; \r\n//-->\r\n</script>\r\n");
      out.write("</html>\r\n<script language=javascript src=\"/jsoa/public/color/colorIE.js\"></script>");
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
    org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f0 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fstyle_005fsize_005fproperty_005fmaxlength_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
    boolean _jspx_th_html_005ftext_005f0_reused = false;
    try {
      _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005ftext_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /relproject/pro_alarmset_add.jsp(36,3) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setStyleClass("inputtext");
      // /relproject/pro_alarmset_add.jsp(36,3) name = maxlength type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setMaxlength("20");
      // /relproject/pro_alarmset_add.jsp(36,3) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setProperty("alarmName");
      // /relproject/pro_alarmset_add.jsp(36,3) name = styleId type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setStyleId("alarmName");
      // /relproject/pro_alarmset_add.jsp(36,3) name = size type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setSize("30");
      // /relproject/pro_alarmset_add.jsp(36,3) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setStyle("width:30%");
      int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
      if (_jspx_th_html_005ftext_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fstyle_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
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
    org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f1 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fstyle_005fsize_005fproperty_005fmaxlength_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
    boolean _jspx_th_html_005ftext_005f1_reused = false;
    try {
      _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
      _jspx_th_html_005ftext_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /relproject/pro_alarmset_add.jsp(42,3) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f1.setStyleClass("inputtext");
      // /relproject/pro_alarmset_add.jsp(42,3) name = maxlength type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f1.setMaxlength("10");
      // /relproject/pro_alarmset_add.jsp(42,3) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f1.setProperty("alarmDays");
      // /relproject/pro_alarmset_add.jsp(42,3) name = styleId type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f1.setStyleId("alarmDays");
      // /relproject/pro_alarmset_add.jsp(42,3) name = size type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f1.setSize("30");
      // /relproject/pro_alarmset_add.jsp(42,3) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f1.setStyle("width:30%");
      int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
      if (_jspx_th_html_005ftext_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fstyle_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
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
    org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f2 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fstyle_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
    boolean _jspx_th_html_005ftext_005f2_reused = false;
    try {
      _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
      _jspx_th_html_005ftext_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /relproject/pro_alarmset_add.jsp(50,13) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f2.setStyleClass("inputtext");
      // /relproject/pro_alarmset_add.jsp(50,13) name = maxlength type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f2.setMaxlength("20");
      // /relproject/pro_alarmset_add.jsp(50,13) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f2.setProperty("alarmColor");
      // /relproject/pro_alarmset_add.jsp(50,13) name = onfocus type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f2.setOnfocus("colordiaPro('colorDisplay')");
      // /relproject/pro_alarmset_add.jsp(50,13) name = styleId type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f2.setStyleId("alarmColor");
      // /relproject/pro_alarmset_add.jsp(50,13) name = size type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f2.setSize("30");
      // /relproject/pro_alarmset_add.jsp(50,13) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f2.setStyle("width:30%");
      int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
      if (_jspx_th_html_005ftext_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fstyle_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
      _jspx_th_html_005ftext_005f2_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005ftext_005f2, _jsp_getInstanceManager(), _jspx_th_html_005ftext_005f2_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005ftext_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f3 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fstyle_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
    boolean _jspx_th_html_005ftext_005f3_reused = false;
    try {
      _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
      _jspx_th_html_005ftext_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /relproject/pro_alarmset_add.jsp(52,13) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f3.setStyleClass("inputtext");
      // /relproject/pro_alarmset_add.jsp(52,13) name = maxlength type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f3.setMaxlength("20");
      // /relproject/pro_alarmset_add.jsp(52,13) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f3.setProperty("alarmColor");
      // /relproject/pro_alarmset_add.jsp(52,13) name = onfocus type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f3.setOnfocus("colorSelect('alarmColor','colorDisplay',event,'2')");
      // /relproject/pro_alarmset_add.jsp(52,13) name = styleId type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f3.setStyleId("alarmColor");
      // /relproject/pro_alarmset_add.jsp(52,13) name = size type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f3.setSize("30");
      // /relproject/pro_alarmset_add.jsp(52,13) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f3.setStyle("width:30%");
      int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
      if (_jspx_th_html_005ftext_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fstyle_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
      _jspx_th_html_005ftext_005f3_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005ftext_005f3, _jsp_getInstanceManager(), _jspx_th_html_005ftext_005f3_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005fradio_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:radio
    org.apache.struts.taglib.html.RadioTag _jspx_th_html_005fradio_005f0 = (org.apache.struts.taglib.html.RadioTag) _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(org.apache.struts.taglib.html.RadioTag.class);
    boolean _jspx_th_html_005fradio_005f0_reused = false;
    try {
      _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005fradio_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /relproject/pro_alarmset_add.jsp(61,3) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fradio_005f0.setProperty("alarmEnable");
      // /relproject/pro_alarmset_add.jsp(61,3) name = value type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fradio_005f0.setValue("1");
      int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
      if (_jspx_th_html_005fradio_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
      _jspx_th_html_005fradio_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fradio_005f0, _jsp_getInstanceManager(), _jspx_th_html_005fradio_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005fradio_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:radio
    org.apache.struts.taglib.html.RadioTag _jspx_th_html_005fradio_005f1 = (org.apache.struts.taglib.html.RadioTag) _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(org.apache.struts.taglib.html.RadioTag.class);
    boolean _jspx_th_html_005fradio_005f1_reused = false;
    try {
      _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
      _jspx_th_html_005fradio_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /relproject/pro_alarmset_add.jsp(62,3) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fradio_005f1.setProperty("alarmEnable");
      // /relproject/pro_alarmset_add.jsp(62,3) name = value type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fradio_005f1.setValue("0");
      int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
      if (_jspx_th_html_005fradio_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
      _jspx_th_html_005fradio_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fradio_005f1, _jsp_getInstanceManager(), _jspx_th_html_005fradio_005f1_reused);
    }
    return false;
  }
}
