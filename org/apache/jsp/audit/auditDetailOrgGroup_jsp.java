/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:00:45 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.audit;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class auditDetailOrgGroup_jsp extends org.apache.jasper.runtime.HttpJspBase
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
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fstyle_005fsize_005freadonly_005fproperty_005fmaxlength_005fdisabled_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fstyleId_005fproperty_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fdisabled_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid;

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
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fstyle_005fsize_005freadonly_005fproperty_005fmaxlength_005fdisabled_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fstyleId_005fproperty_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fdisabled_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fhtml.release();
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.release();
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fstyle_005fsize_005freadonly_005fproperty_005fmaxlength_005fdisabled_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fstyleId_005fproperty_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fdisabled_005fnobody.release();
    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid.release();
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

 response.setHeader("Cache-Control","no-store");
 response.setHeader("Pragma","no-cache");
 response.setDateHeader ("Expires", 0);
 String log_id = request.getParameter("id")==null?"0":request.getParameter("id");
 String done=(String)request.getAttribute("done");
String opResult=request.getAttribute("opResult")==null?"":request.getAttribute("opResult").toString();
String groupType=request.getParameter("groupType");
String groupRange=(String)request.getAttribute("groupRange");
//String groupRange=session.getAttribute("grantRange").toString();
String operationType=(String)request.getAttribute("operationType");
if("1".equals(groupType)){
	groupRange=session.getAttribute("browseRange").toString();
}
if("0".equals(groupRange)){
	groupRange="*0*";
}

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
            out.write("\r\n\r\n<head>\r\n<title>修改组</title>\r\n<link href=\"skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n<SCRIPT language=\"javascript\" src=\"/jsoa/js/openEndow.js\"></SCRIPT>\r\n<script language=\"javascript\" src=\"/jsoa/js/checkForm.js\"></script>\r\n</head>\r\n\r\n<body  onload=\"resizeWin(600,500)\" class=\"MainFrameBox Pupwin\">\r\n<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"docBoxNoPanel\">\r\n <tr>\r\n    <td valign=\"top\">\r\n\t<div id=\"docinfo0\" style=\"display:;\"> \r\n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"1\">\r\n\t\t  ");
            //  html:form
            org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag) _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(org.apache.struts.taglib.html.FormTag.class);
            boolean _jspx_th_html_005fform_005f0_reused = false;
            try {
              _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
              _jspx_th_html_005fform_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
              // /audit/auditDetailOrgGroup.jsp(42,4) name = action type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setAction("/AuditOrgGroupAction.do?action=shenji");
              // /audit/auditDetailOrgGroup.jsp(42,4) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setMethod("post");
              int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
              if (_jspx_eval_html_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t  ");
                  //  html:hidden
                  org.apache.struts.taglib.html.HiddenTag _jspx_th_html_005fhidden_005f0 = (org.apache.struts.taglib.html.HiddenTag) _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(org.apache.struts.taglib.html.HiddenTag.class);
                  boolean _jspx_th_html_005fhidden_005f0_reused = false;
                  try {
                    _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
                    _jspx_th_html_005fhidden_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
                    // /audit/auditDetailOrgGroup.jsp(43,4) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                    _jspx_th_html_005fhidden_005f0.setProperty("groupType");
                    // /audit/auditDetailOrgGroup.jsp(43,4) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                    _jspx_th_html_005fhidden_005f0.setValue(groupType);
                    int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
                    if (_jspx_th_html_005fhidden_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                      return;
                    }
                    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
                    _jspx_th_html_005fhidden_005f0_reused = true;
                  } finally {
                    org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fhidden_005f0, _jsp_getInstanceManager(), _jspx_th_html_005fhidden_005f0_reused);
                  }
                  out.write("\r\n\t\t  <tr>\r\n\t\t\t<td width=\"67\" nowrap=\"nowrap\">组名称&nbsp;<label class=\"mustFillcolor\">*</label>：</td>\r\n\t\t\t<td >\r\n\t\t\t");

			  String auditOrgGroupId = (String) request.getAttribute("auditOrgGroupId");
			  String groupName = (String) request.getAttribute("groupName");
			  String groupUserString = (String) request.getAttribute("groupUserString");
			  String groupUserName = (String) request.getAttribute("groupUserName");
			  String groupOrder=(String)request.getAttribute("groupOrder");
			  
                  out.write("\r\n\t\t\t  ");
                  if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
                    return;
                  out.write("\r\n\t\t\t</td>\r\n\t\t\t<td width=\"20\">&nbsp;</td>\r\n\t\t  </tr>\r\n\t\t   <tr>\r\n\t\t\t<td width=\"67\" nowrap=\"nowrap\">组用户：</td>\r\n\t\t\t<td><textarea class=\"inputTextarea\" cols=\"65\"  rows=\"8\" name=\"groupUserNames\" id=\"groupUserNames\" readonly=\"readonly\"  disabled=\"disabled\"  style=\"width:100%;cursor:pointer\" onclick='javascript:selectUser(\"groupUserString\", \"groupUserNames\", \"user\");'>");
                  out.print(groupUserName==null?"":groupUserName);
                  out.write("</textarea>\r\n                              \r\n                              ");
                  //  html:hidden
                  org.apache.struts.taglib.html.HiddenTag _jspx_th_html_005fhidden_005f1 = (org.apache.struts.taglib.html.HiddenTag) _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(org.apache.struts.taglib.html.HiddenTag.class);
                  boolean _jspx_th_html_005fhidden_005f1_reused = false;
                  try {
                    _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
                    _jspx_th_html_005fhidden_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
                    // /audit/auditDetailOrgGroup.jsp(62,30) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                    _jspx_th_html_005fhidden_005f1.setProperty("groupUserString");
                    // /audit/auditDetailOrgGroup.jsp(62,30) name = styleId type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                    _jspx_th_html_005fhidden_005f1.setStyleId("groupUserString");
                    // /audit/auditDetailOrgGroup.jsp(62,30) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                    _jspx_th_html_005fhidden_005f1.setValue(groupUserString);
                    int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
                    if (_jspx_th_html_005fhidden_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                      return;
                    }
                    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
                    _jspx_th_html_005fhidden_005f1_reused = true;
                  } finally {
                    org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fhidden_005f1, _jsp_getInstanceManager(), _jspx_th_html_005fhidden_005f1_reused);
                  }
                  out.write("\r\n                              ");
                  //  html:hidden
                  org.apache.struts.taglib.html.HiddenTag _jspx_th_html_005fhidden_005f2 = (org.apache.struts.taglib.html.HiddenTag) _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(org.apache.struts.taglib.html.HiddenTag.class);
                  boolean _jspx_th_html_005fhidden_005f2_reused = false;
                  try {
                    _jspx_th_html_005fhidden_005f2.setPageContext(_jspx_page_context);
                    _jspx_th_html_005fhidden_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
                    // /audit/auditDetailOrgGroup.jsp(63,30) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                    _jspx_th_html_005fhidden_005f2.setProperty("auditOrgGroupId");
                    // /audit/auditDetailOrgGroup.jsp(63,30) name = styleId type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                    _jspx_th_html_005fhidden_005f2.setStyleId("auditOrgGroupId");
                    // /audit/auditDetailOrgGroup.jsp(63,30) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                    _jspx_th_html_005fhidden_005f2.setValue(auditOrgGroupId);
                    int _jspx_eval_html_005fhidden_005f2 = _jspx_th_html_005fhidden_005f2.doStartTag();
                    if (_jspx_th_html_005fhidden_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                      return;
                    }
                    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
                    _jspx_th_html_005fhidden_005f2_reused = true;
                  } finally {
                    org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fhidden_005f2, _jsp_getInstanceManager(), _jspx_th_html_005fhidden_005f2_reused);
                  }
                  out.write(" \r\n\t\t    </td>\r\n\t\t\t<td valign=\"bottom\">\r\n");
                  out.write('\r');
                  out.write('\n');
                  out.write("\r\n                               </td>\r\n\t\t  </tr>\r\n\t\t  <tr  ");
if("1".equals(groupType)){
                  out.write("style=\"display:none\"");
}
                  out.write(">\r\n\t\t\t<td width=\"67\" nowrap=\"nowrap\">使用范围：</td>\r\n\t\t\t<td><textarea class=\"inputTextarea\" cols=\"65\"  rows=\"8\" name=\"rangeName\" id=\"rangeName\"   disabled=\"true\"  style=\"width:100%;cursor:pointer\" onclick='javascript:selectUser(\"rangeId\",\"rangeName\", \"userorggroup\");'>");
                  out.print(request.getAttribute("rangeName"));
                  out.write("</textarea>\r\n                              \r\n                              <input type=\"hidden\" name=\"rangeId\" id=\"rangeId\" value=\"");
                  out.print((request.getAttribute("rangeEmp")==null?"":request.getAttribute("rangeEmp").toString()) + (request.getAttribute("rangeOrg")==null?"":request.getAttribute("rangeOrg").toString()) + (request.getAttribute("rangeGroup")==null?"":request.getAttribute("rangeGroup").toString()));
                  out.write("\">\r\n                              <input type=\"hidden\" name=\"rangeEmp\" id=\"rangeEmp\">\r\n                              <input type=\"hidden\" name=\"rangeOrg\" id=\"rangeOrg\">\r\n                              <input type=\"hidden\" name=\"rangeGroup\" id=\"rangeGroup\">\r\n\t\t    </td>\r\n\t\t\t<td valign=\"bottom\">\r\n");
                  out.write('\r');
                  out.write('\n');
                  out.write("\r\n                              </td>\r\n\t\t  </tr>\r\n\t\t  <tr ");
if("1".equals(groupType)){
                  out.write("style=\"display:none\"");
}
                  out.write(">\r\n\t\t\t<td></td>\r\n\t\t\t<td><span class=\"TextDescriptionColor\">注：“使用范围”为空时默认为所有用户！</span></td>\r\n\t\t  </tr>\r\n\t\t  <tr>\r\n\t\t\t<td width=\"67\" nowrap=\"nowrap\">排序码：</td>\r\n\t\t\t<td>\r\n\t\t\t\t");
                  //  html:text
                  org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f1 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fdisabled_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
                  boolean _jspx_th_html_005ftext_005f1_reused = false;
                  try {
                    _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
                    _jspx_th_html_005ftext_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
                    // /audit/auditDetailOrgGroup.jsp(91,4) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                    _jspx_th_html_005ftext_005f1.setStyleClass("inputText");
                    // /audit/auditDetailOrgGroup.jsp(91,4) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                    _jspx_th_html_005ftext_005f1.setProperty("groupOrder");
                    // /audit/auditDetailOrgGroup.jsp(91,4) name = disabled type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                    _jspx_th_html_005ftext_005f1.setDisabled(true);
                    // /audit/auditDetailOrgGroup.jsp(91,4) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                    _jspx_th_html_005ftext_005f1.setValue(groupOrder);
                    int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
                    if (_jspx_th_html_005ftext_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                      return;
                    }
                    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fdisabled_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
                    _jspx_th_html_005ftext_005f1_reused = true;
                  } finally {
                    org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005ftext_005f1, _jsp_getInstanceManager(), _jspx_th_html_005ftext_005f1_reused);
                  }
                  out.write("\r\n\t\t\t\t</td>\r\n\t\t  </tr>\r\n\t\t  \r\n\t\t ");

                            //如果管理员的可管理组织多于一个在需要指定一个组织
                            if("1".equals(request.getAttribute("multiRange"))){
                                Object[] obj=null;
                                String createdOrg=request.getAttribute("createdOrg").toString();
                                String tmp;
                            
                  out.write("\r\n\t\t  <tr>\r\n\t\t\t<td width=\"67\" nowrap=\"nowrap\">所属组织：</td>\r\n\t\t\t<td>\r\n\t\t\t<select name=\"createdOrg\">\r\n\t\t\t   ");
                  //  logic:iterate
                  org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_005fiterate_005f0 = (org.apache.struts.taglib.logic.IterateTag) _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid.get(org.apache.struts.taglib.logic.IterateTag.class);
                  boolean _jspx_th_logic_005fiterate_005f0_reused = false;
                  try {
                    _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
                    _jspx_th_logic_005fiterate_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
                    // /audit/auditDetailOrgGroup.jsp(106,6) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                    _jspx_th_logic_005fiterate_005f0.setId("orgList");
                    // /audit/auditDetailOrgGroup.jsp(106,6) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                    _jspx_th_logic_005fiterate_005f0.setName("managerRange");
                    // /audit/auditDetailOrgGroup.jsp(106,6) name = scope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                    _jspx_th_logic_005fiterate_005f0.setScope("request");
                    int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
                    if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                      java.lang.Object orgList = null;
                      if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                        out = org.apache.jasper.runtime.JspRuntimeLibrary.startBufferedBody(_jspx_page_context, _jspx_th_logic_005fiterate_005f0);
                      }
                      orgList = (java.lang.Object) _jspx_page_context.findAttribute("orgList");
                      do {
                        out.write("\r\n\t\t\t\t ");
 obj=(Object[])orgList;
					tmp=createdOrg.equals(obj[0].toString())?"selected":"";
				  
                        out.write("\r\n\t\t\t\t  <option value=\"");
                        out.print(obj[0]);
                        out.write('"');
                        out.write(' ');
                        out.print(tmp);
                        out.write('>');
                        out.print(obj[1]);
                        out.write("</option>\r\n\t\t\t  ");
                        int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
                        orgList = (java.lang.Object) _jspx_page_context.findAttribute("orgList");
                        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                          break;
                      } while (true);
                      if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                        out = _jspx_page_context.popBody();
                      }
                    }
                    if (_jspx_th_logic_005fiterate_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                      return;
                    }
                    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
                    _jspx_th_logic_005fiterate_005f0_reused = true;
                  } finally {
                    org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_logic_005fiterate_005f0, _jsp_getInstanceManager(), _jspx_th_logic_005fiterate_005f0_reused);
                  }
                  out.write("\r\n\t\t  </select>\r\n\t\t\t</td>\r\n\t\t  </tr>\r\n\t\t ");
}else{
                  out.write("\r\n\t\t<input type=\"hidden\" name=\"createdOrg\" value=\"");
                  out.print(request.getAttribute("managerRange"));
                  out.write("\"/>\r\n\t\t");
}
                  out.write("\t\t \r\n\t \t  <tr>\r\n\t\t\t<td width=\"67\" nowrap=\"nowrap\"><br/><font color=\"red\">操作类型：</font></td>\r\n\t\t\t<td>\r\n\t\t\t\t<font color=\"red\"><br/>");
                  out.print(request.getAttribute("operationTypeCh"));
                  out.write("</font>\r\n\t\t\t\t</td>\r\n\t\t  </tr>\r\n\t    ");
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
            out.write("\r\n\t\t\t</table>\r\n\t\t\t\r\n\t</div>\r\n\t<br/>\r\n\t<table>\r\n\t\t\t   <tr>\r\n\t\t     <td colspan=\"4\">\r\n\t\t     <button class=\"btnButton2font\" onClick=\"javascript:window.close();\">退出</button>\r\n\t\t\t </td>\r\n\t\t  </tr>\r\n\t</table>\r\n\t</td>\r\n  </tr>\r\n</table>\r\n\r\n</body>\r\n");
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
      out.write('\r');
      out.write('\n');

if("1".equals(opResult)){
      out.write("\r\n    <script>\r\n    alert(\"组名称重复!\");\r\n    document.all.groupName.select();\r\n    </script>\r\n");
}else if("2".equals(opResult)){
      out.write("\r\n    <script>alert(\"更新操作不成功!\");</script>\r\n");
}
      out.write("\r\n<script language=\"javascript\">\r\n</script>\r\n\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
    org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f0 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fstyle_005fsize_005freadonly_005fproperty_005fmaxlength_005fdisabled_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
    boolean _jspx_th_html_005ftext_005f0_reused = false;
    try {
      _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005ftext_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /audit/auditDetailOrgGroup.jsp(54,5) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setStyleClass("inputText");
      // /audit/auditDetailOrgGroup.jsp(54,5) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setProperty("groupName");
      // /audit/auditDetailOrgGroup.jsp(54,5) name = size type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setSize("50");
      // /audit/auditDetailOrgGroup.jsp(54,5) name = styleId type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setStyleId("groupName");
      // /audit/auditDetailOrgGroup.jsp(54,5) name = disabled type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setDisabled(true);
      // /audit/auditDetailOrgGroup.jsp(54,5) name = readonly type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setReadonly(true);
      // /audit/auditDetailOrgGroup.jsp(54,5) name = maxlength type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setMaxlength("15");
      // /audit/auditDetailOrgGroup.jsp(54,5) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setStyle("width:100%");
      int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
      if (_jspx_th_html_005ftext_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fstyle_005fsize_005freadonly_005fproperty_005fmaxlength_005fdisabled_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
      _jspx_th_html_005ftext_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005ftext_005f0, _jsp_getInstanceManager(), _jspx_th_html_005ftext_005f0_reused);
    }
    return false;
  }
}
