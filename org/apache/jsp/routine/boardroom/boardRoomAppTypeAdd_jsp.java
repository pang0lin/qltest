/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:48:13 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.routine.boardroom;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.Calendar;
import com.js.util.config.SystemCommon;

public final class boardRoomAppTypeAdd_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-logic.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-html.tld", Long.valueOf(1499751390000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.util.Calendar");
    _jspx_imports_classes.add("com.js.util.config.SystemCommon");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005ftitle_005fstyleId_005fstyleClass_005fstyle_005frows_005freadonly_005fproperty_005fonclick_005fnobody;

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
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005ftitle_005fstyleId_005fstyleClass_005fstyle_005frows_005freadonly_005fproperty_005fonclick_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.release();
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005ftitle_005fstyleId_005fstyleClass_005fstyle_005frows_005freadonly_005fproperty_005fonclick_005fnobody.release();
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

      out.write("\r\n\r\n\r\n\r\n\r\n<html>\r\n<head>\r\n<title>新会议类型</title>\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script language=\"javascript\" src=\"/jsoa/js/checkForm.js\"></script>\r\n<script language=\"javascript\" src=\"/jsoa/js/openEndow.js\"></script>\r\n<script src=\"js/js.js\" language=\"javascript\"></script>\r\n");

	response.setHeader("Cache-Control","no-store");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader ("Expires", 0);

      out.write("\r\n</head>\r\n\r\n\r\n<body  class=\"MainFrameBox Pupwin\" onload=\"load(); resizeWin(400,170);\" onKeyDown=\"if(event.keyCode==13) return false;\" >\r\n<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"docBoxNoPanel\">\r\n    ");
      //  html:form
      org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag) _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(org.apache.struts.taglib.html.FormTag.class);
      boolean _jspx_th_html_005fform_005f0_reused = false;
      try {
        _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
        _jspx_th_html_005fform_005f0.setParent(null);
        // /routine/boardroom/boardRoomAppTypeAdd.jsp(23,4) name = action type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_html_005fform_005f0.setAction("/BoardRoomAction.do?action=addBoardroomAppType");
        // /routine/boardroom/boardRoomAppTypeAdd.jsp(23,4) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_html_005fform_005f0.setMethod("post");
        int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
        if (_jspx_eval_html_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n\t    ");
            if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
              return;
            out.write("\r\n\t\t<tr valign=\"top\">\r\n\t   <td>\r\n\t      <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"1\">\r\n\t\t<tr>\r\n\t\t  <td width=\"12%\" nowrap=\"nowrap\"><label class=\"MustFillColor\">*</label>会议类型：</td>\r\n\t\t  <td height=\"40\" valign=\"middle\">");
            if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
              return;
            out.write("</td>\r\n\t\t</tr>\r\n\t\t<tr>\r\n\t\t  <td width=\"12%\" nowrap=\"nowrap\"><label class=\"MustFillColor\">*</label>会议形式：</td>\r\n\t\t  <td height=\"40\" valign=\"middle\">\r\n\t\t  \t<input type=\"radio\" name=\"applyType\" value=\"0\" checked onclick=\"hideUseRange(0)\"/>即时会议&nbsp;\r\n\t\t\t<input type=\"radio\" name=\"applyType\" value=\"1\" onclick=\"hideUseRange(1)\"/>定期会议&nbsp;\r\n\t\t  </td>\r\n\t\t</tr>\r\n\t\t<tr>\r\n\t\t  <td width=\"12%\" nowrap=\"nowrap\"><label class=\"MustFillColor\">*</label>申请优先：</td>\r\n\t\t  <td height=\"40\" valign=\"middle\">\r\n\t\t  \t<input type=\"radio\" name=\"applyFirst\" value=\"0\" checked />否&nbsp;\r\n\t\t\t<input type=\"radio\" name=\"applyFirst\" value=\"1\" />是&nbsp;\r\n\t\t  </td>\r\n\t\t</tr>\r\n\t\t");

		  if ("haier".equals(SystemCommon.getCustomerName())) {
			  
            out.write("\r\n\t\t\t  <tr>\r\n\t\t  <td nowrap=\"nowrap\" valign=\"top\"></label>使用范围：</td>\r\n\t\t  <td >\r\n\t\t\t  ");
            if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
              return;
            out.write("\r\n\t\t\t  ");
            if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
              return;
            out.write("\r\n\t\t  </td>\r\n\t\t  <tr>\r\n\t\t    <td></td>\r\n\t\t  \t<td><span class=\"TextDescriptionColor\">注：“使用范围”为空时默认为所有用户！</span></td>\r\n\t\t  </tr>\r\n\t\t</tr>\r\n\t\t\t  ");

		  }
		
            out.write("\r\n\t\t\r\n\t\t<input type=\"hidden\" name=\"_range\" value=\"");
            out.print(session.getAttribute("grantRange"));
            out.write("\">\r\n\t\t<tr>\r\n\t\t  <td colspan=\"2\" height=\"30\" valign=\"bottom\">\r\n\t\t     <input type=\"button\" class=\"btnButton4font\" style=\"cursor:pointer\" id=\"saveAndExit1\"  onclick=\"saveAndExit();\" value=\"保存退出\"/>\r\n\t\t     <input type=\"button\" class=\"btnButton4font\" style=\"cursor:pointer\" id=\"saveAndContinue1\"   onclick=\"saveAndContinue();\" value=\"保存继续\"/>\r\n\t\t     <input type=\"button\" class=\"btnButton2font\" style=\"cursor:pointer\"  onclick=\"resetMe();\" value=\"重置\" />\r\n\t\t     <input type=\"button\" class=\"btnButton2font\" style=\"cursor:pointer\"  onClick=\"window.close();\" value=\"退出\"/>\r\n\t\t</td>\r\n\t</tr>\r\n\t\t</table>\r\n\t\t</td>\r\n\t</tr>\r\n ");
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
      out.write("\r\n</table>\r\n</body>\r\n\r\n</html>\r\n<script language=\"javascript\">\r\n<!--\r\nfunction load(){\r\n    var reload = document.getElementById(\"saveType\").value;\r\n    if (reload == \"saveAndContinue\"){\r\n        //window.opener.location.reload();\r\n        window.opener.location.href = \"/jsoa/BoardRoomAction.do?action=boardroomAppTypeView\";\r\n    }\r\n    if (reload == \"saveAndExit\"){\r\n    \t//window.opener.location.reload();\r\n          window.opener.location.href = \"/jsoa/BoardRoomAction.do?action=boardroomAppTypeView\";\r\n        window.close();\r\n    }\r\n    if (reload == \"isRepeatName\"){\r\n    \talert(\"您填写的会议类型已存在，请重新填写。\");\r\n\t\tdocument.getElementById(\"type\").focus();\r\n    }\r\n    if (reload == \"null\"){\r\n    \treturn ;\r\n    }\r\n\r\n}\r\n\r\nfunction saveAndExit(){\r\n    //document.BoardRoomActionForm.saveType.value = \"saveAndExit\";\r\n    var type = document.getElementById(\"type\").value;\r\n    if (type !=\"\"){\r\n            if(type.substring(0,1) ==\" \"){\r\n                alert(\"会议类型不得为空格开头，请去空格。\");\r\n                document.getElementById(\"type\").focus();\r\n");
      out.write("                return false;\r\n                }\r\n        document.getElementById(\"saveType\").value = \"saveAndExit\";\r\n        setButtonDisabled(\"saveAndExit1\",true);\r\n\t\tsetButtonDisabled(\"saveAndContinue1\",true);\r\n\t\tsetTimeout(\"fobbidenBtn()\",8000);\r\n         document.BoardRoomActionForm.submit();\r\n    } else {\r\n        alert(\"会议类型不得为空，必须填写。\");\r\n        return false;\r\n    }\r\n}\r\n\r\nfunction saveAndContinue(){\r\n    var type = document.getElementById(\"type\").value;    \r\n    if (type ==\"\"){\r\n        alert(\"类型名称不得为空，必须填写。\");\r\n        document.getElementById(\"type\").focus();\r\n        document.getElementById(\"saveType\").value = \"null\";\r\n        return false;\r\n        }\r\n    if (type !=\"\"){\r\n            if(type.substring(0,1) ==\" \"){\r\n                alert(\"类型名称不得为空格开头，请去空格。\");\r\n                document.getElementById(\"type\").focus();\r\n                return false;\r\n                }\r\n        }\r\n        document.getElementById(\"saveType\").value = \"saveAndContinue\";\r\n        setButtonDisabled(\"saveAndExit1\",true);\r\nsetButtonDisabled(\"saveAndContinue1\",true);\r\n");
      out.write("setTimeout(\"fobbidenBtn()\",8000);\r\n   document.BoardRoomActionForm.submit();\r\n    }\r\n\r\nfunction fobbidenBtn(){\r\n   \tsetButtonDisabled('saveAndExit1',false);\r\n  \tsetButtonDisabled('saveAndContinue1',false);\r\n}\r\nfunction resetMe(){\r\n    document.BoardRoomActionForm.reset();\r\n}\r\n\r\nfunction hideUseRange(type){\r\n\tif(1==type){\r\n\t  document.getElementById(\"useRange\").style.display=\"none\";\r\n\t}else{\r\n\t  document.getElementById(\"useRange\").style.display=\"\";\r\n\t}\r\n}\r\n//-->\r\n</script>\r\n\r\n<script src=\"/jsoa/js/util.js\"></script>");
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

  private boolean _jspx_meth_html_005fhidden_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_005fhidden_005f0 = (org.apache.struts.taglib.html.HiddenTag) _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    boolean _jspx_th_html_005fhidden_005f0_reused = false;
    try {
      _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005fhidden_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /routine/boardroom/boardRoomAppTypeAdd.jsp(24,5) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fhidden_005f0.setProperty("saveType");
      // /routine/boardroom/boardRoomAppTypeAdd.jsp(24,5) name = styleId type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fhidden_005f0.setStyleId("saveType");
      int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
      if (_jspx_th_html_005fhidden_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
      _jspx_th_html_005fhidden_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fhidden_005f0, _jsp_getInstanceManager(), _jspx_th_html_005fhidden_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005ftext_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f0 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
    boolean _jspx_th_html_005ftext_005f0_reused = false;
    try {
      _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005ftext_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /routine/boardroom/boardRoomAppTypeAdd.jsp(30,36) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setStyleClass("inputText");
      // /routine/boardroom/boardRoomAppTypeAdd.jsp(30,36) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setProperty("type");
      // /routine/boardroom/boardRoomAppTypeAdd.jsp(30,36) name = styleId type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setStyleId("type");
      // /routine/boardroom/boardRoomAppTypeAdd.jsp(30,36) name = maxlength type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setMaxlength("30");
      // /routine/boardroom/boardRoomAppTypeAdd.jsp(30,36) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setStyle("width:95%");
      // /routine/boardroom/boardRoomAppTypeAdd.jsp(30,36) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setValue("");
      int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
      if (_jspx_th_html_005ftext_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
      _jspx_th_html_005ftext_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005ftext_005f0, _jsp_getInstanceManager(), _jspx_th_html_005ftext_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005ftextarea_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:textarea
    org.apache.struts.taglib.html.TextareaTag _jspx_th_html_005ftextarea_005f0 = (org.apache.struts.taglib.html.TextareaTag) _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005ftitle_005fstyleId_005fstyleClass_005fstyle_005frows_005freadonly_005fproperty_005fonclick_005fnobody.get(org.apache.struts.taglib.html.TextareaTag.class);
    boolean _jspx_th_html_005ftextarea_005f0_reused = false;
    try {
      _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005ftextarea_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /routine/boardroom/boardRoomAppTypeAdd.jsp(52,5) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setProperty("useScope");
      // /routine/boardroom/boardRoomAppTypeAdd.jsp(52,5) name = styleId type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setStyleId("useScope");
      // /routine/boardroom/boardRoomAppTypeAdd.jsp(52,5) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setStyleClass("inputtextarea");
      // /routine/boardroom/boardRoomAppTypeAdd.jsp(52,5) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setStyle("width:95%");
      // /routine/boardroom/boardRoomAppTypeAdd.jsp(52,5) name = rows type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setRows("4");
      // /routine/boardroom/boardRoomAppTypeAdd.jsp(52,5) name = readonly type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setReadonly(true);
      // /routine/boardroom/boardRoomAppTypeAdd.jsp(52,5) name = title type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setTitle("请点击选择");
      // /routine/boardroom/boardRoomAppTypeAdd.jsp(52,5) name = onclick type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setOnclick("openEndow('useScopeId','useScope',document.all.useScopeId.value,document.all.useScope.value,'userorg','no','userorg',BoardRoomActionForm._range.value)");
      int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
      if (_jspx_th_html_005ftextarea_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005ftitle_005fstyleId_005fstyleClass_005fstyle_005frows_005freadonly_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
      _jspx_th_html_005ftextarea_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005ftextarea_005f0, _jsp_getInstanceManager(), _jspx_th_html_005ftextarea_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005fhidden_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_005fhidden_005f1 = (org.apache.struts.taglib.html.HiddenTag) _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    boolean _jspx_th_html_005fhidden_005f1_reused = false;
    try {
      _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
      _jspx_th_html_005fhidden_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /routine/boardroom/boardRoomAppTypeAdd.jsp(53,5) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fhidden_005f1.setProperty("useScopeId");
      // /routine/boardroom/boardRoomAppTypeAdd.jsp(53,5) name = styleId type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fhidden_005f1.setStyleId("useScopeId");
      int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
      if (_jspx_th_html_005fhidden_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
      _jspx_th_html_005fhidden_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fhidden_005f1, _jsp_getInstanceManager(), _jspx_th_html_005fhidden_005f1_reused);
    }
    return false;
  }
}
