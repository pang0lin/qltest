/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:02:11 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.office_005fmanager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class officemanager_005ffunction_005fadd_jsp extends org.apache.jasper.runtime.HttpJspBase
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
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fonsubmit_005fmethod_005faction;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleId_005fstyleClass_005fstyle_005frows_005fproperty_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody;

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
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fonsubmit_005fmethod_005faction = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleId_005fstyleClass_005fstyle_005frows_005fproperty_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fhtml.release();
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fonsubmit_005fmethod_005faction.release();
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleId_005fstyleClass_005fstyle_005frows_005fproperty_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.release();
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

String fromSystem=request.getParameter("fromSystem");
if(request.getAttribute("fromSystem")!=null && !"".equals(request.getAttribute("fromSystem").toString()) && !"null".equals(request.getAttribute("fromSystem").toString())){
	fromSystem=request.getAttribute("fromSystem").toString();
}

String offset=request.getParameter("pager.offset")==null?"0":request.getParameter("pager.offset");
String searchDutyName = request.getParameter("searchDutyName")==null?"":request.getParameter("searchDutyName");
 String searchDutyLevel = request.getParameter("searchDutyLevel")==null?"":request.getParameter("searchDutyLevel");
String url="/DutyAction.do?pager.offset="+offset;

      out.write("\r\n\r\n");
      //  html:html
      org.apache.struts.taglib.html.HtmlTag _jspx_th_html_005fhtml_005f0 = (org.apache.struts.taglib.html.HtmlTag) _005fjspx_005ftagPool_005fhtml_005fhtml.get(org.apache.struts.taglib.html.HtmlTag.class);
      boolean _jspx_th_html_005fhtml_005f0_reused = false;
      try {
        _jspx_th_html_005fhtml_005f0.setPageContext(_jspx_page_context);
        _jspx_th_html_005fhtml_005f0.setParent(null);
        int _jspx_eval_html_005fhtml_005f0 = _jspx_th_html_005fhtml_005f0.doStartTag();
        if (_jspx_eval_html_005fhtml_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n<head>\r\n<title>");
if("modify".equals(request.getParameter("action"))||"update".equals(request.getParameter("action"))){
            out.write("修改职务");
}else{
            out.write('新');
            out.write('职');
            out.write('务');
}
            out.write("</title>\r\n<script language=\"javascript\" src=\"/jsoa/js/checkForm.js\"></script>\r\n<script src=\"js/js.js\" language=\"javascript\"></script>\r\n<SCRIPT language=\"javascript\" src=\"/jsoa/js/checkText.js\"></SCRIPT>\r\n<link href=\"/jsoa/skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script language=\"javascript\">\r\nfunction changePanle(flag){\r\n    for(i=0;i<2;i++){\r\n\t  eval(\"docinfo\"+i+\".style.display='none'\");\r\n\t  eval(\"Panle\"+i+\".className='btnBQ AlignLeft'\");\r\n\t}\r\n\teval(\"docinfo\"+flag+\".style.display=''\");\r\n\teval(\"Panle\"+flag+\".className='btnBQselected AlignLeft'\");\r\n}\r\n</script>\r\n<link href=\"/jsoa/style/cssmain.css\" rel=\"stylesheet\" type=\"text/css\">\r\n</head>\r\n\r\n<body leftmargin=\"0\" topmargin=\"0\" onLoad=\"init();resizeWin(480,300);\"  class=\"MainFrameBox Pupwin\">\r\n\r\n<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"1\" cellspacing=\"0\" class=\"docBoxNoPanel\">\r\n  <tr>\r\n    <td  valign=\"top\">\r\n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"1\">\r\n\t\t\t\t");
            //  html:form
            org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag) _005fjspx_005ftagPool_005fhtml_005fform_0026_005fonsubmit_005fmethod_005faction.get(org.apache.struts.taglib.html.FormTag.class);
            boolean _jspx_th_html_005fform_005f0_reused = false;
            try {
              _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
              _jspx_th_html_005fform_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
              // /office_manager/officemanager_function_add.jsp(50,4) name = action type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setAction(url);
              // /office_manager/officemanager_function_add.jsp(50,4) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setMethod("post");
              // /office_manager/officemanager_function_add.jsp(50,4) name = onsubmit type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setOnsubmit("commit();");
              int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
              if (_jspx_eval_html_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t\t");
                  if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
                    return;
                  out.write("\r\n\t\t\t\t<input type=\"hidden\" name=\"fromSystem\" id=\"fromSystem\" value=\"");
                  out.print(request.getParameter("fromSystem") );
                  out.write("\" />\r\n\t\t\t\t<input type=\"hidden\" name=\"pager.offset\" value=\"");
                  out.print(offset);
                  out.write("\">\r\n\t\t\t\t<input type=\"hidden\" name=\"searchDutyName\" value=\"");
                  out.print(searchDutyName);
                  out.write("\">\r\n\t\t\t\t<input type=\"hidden\" name=\"searchDutyLevel\" value=\"");
                  out.print(searchDutyLevel);
                  out.write("\">\r\n\t\t\t\t<input type=\"hidden\" name=\"fromSystem\" value=\"");
                  out.print(fromSystem);
                  out.write("\">\r\n\t\t\t\t\r\n\t\t\t\t<tr>\r\n\t\t\t\t  <td width=\"80\">&nbsp;<label class=\"mustFillcolor\">*</label>职务编码：</td>\r\n\t\t\t\t  <td  valign=\"middle\">");
                  if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
                    return;
                  out.write("\r\n\t\t\t\t\t</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n\t\t\t\t  <td width=\"80\">&nbsp;<label class=\"mustFillcolor\">*</label>职务名称：</td>\r\n\t\t\t\t  <td  valign=\"middle\">");
                  if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
                    return;
                  out.write("\r\n\t\t\t\t\t</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n\t\t\t\t  <td width=\"80\">&nbsp;<label class=\"mustFillcolor\">*</label>职务级别：</td>\r\n\t\t\t\t  <td  valign=\"middle\">");
                  if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
                    return;
                  out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n\t\t\t\t  <td width=\"80\" valign=\"top\">&nbsp;&nbsp;&nbsp;职务描述：</td>\r\n\t\t\t\t  <td valign=\"middle\">\r\n\t\t\t\t  ");
                  if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
                    return;
                  out.write("\r\n\t\t\t\t </td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t");
if(request.getParameter("dutyId") != null){
                  out.write("\r\n\t\t\t\t<input type=\"hidden\" name=\"dutyId\" value=\"");
                  out.print(request.getParameter("dutyId"));
                  out.write("\">\r\n\t\t\t\t");
}
                  out.write("\r\n\t\t\t\t");
                  if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
                    return;
                  out.write("\r\n\t\t\t\t");
                  if (_jspx_meth_html_005fhidden_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
                    return;
                  out.write("\r\n\t\t\t\t");
                  int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
              }
              if (_jspx_th_html_005fform_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                return;
              }
              _005fjspx_005ftagPool_005fhtml_005fform_0026_005fonsubmit_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
              _jspx_th_html_005fform_005f0_reused = true;
            } finally {
              org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fform_005f0, _jsp_getInstanceManager(), _jspx_th_html_005fform_005f0_reused);
            }
            out.write("\r\n\t\t  </table>\r\n\t\t<br/>\r\n\t\t<table width=\"100%\" border=\"0\">\r\n\t\t\t<tr>\r\n\t\t\t  <td>\r\n\t\t\t     <input type=\"button\" id=\"saveAndExit\" class=\"btnButton4font\" style=\"cursor:pointer\" onClick=\"document.all.action.value='");
            out.print("modify".equals(request.getParameter("action"))||"update".equals(request.getParameter("action"))?"update":"saveclose");
            out.write("';saveClose();\" value=\"保存退出\">\r\n\t\t\t  ");
if("add".equals(request.getParameter("action")) || "savecontinue".equals(request.getParameter("action"))){
            out.write("\r\n\t\t\t\t<input type=\"button\" id=\"saveAndContinue\" class=\"btnButton4font\" style=\"cursor:pointer\" onClick=\"document.all.action.value='savecontinue';saveContinue();\" value=\"保存继续\"/>\r\n\t\t\t  ");
}
            out.write("\t\t\t\t\r\n\t\t\t\t<input type=\"button\" class=\"btnButton2font\" style=\"cursor:pointer\" onClick=\"DutyActionForm.reset();\" value=\"重置\">\r\n\t\t\t\t<input type=\"button\" class=\"btnButton2font\" style=\"cursor:pointer\" onClick=\"javascript:window.close();\" value=\"退出\">\r\n\t\t\t  </td>\r\n\t\t\t</tr>\r\n\t\t</table>\r\n\t</td>\r\n  </tr>\r\n</table>\r\n\r\n</body>\r\n\r\n");

String opResult="";
if(request.getAttribute("opResult")!=null){
	opResult=request.getAttribute("opResult").toString();
}
if("1".equals(opResult)){
            out.write("\r\n\t<script>\r\n        alert(\"职务名称重复,请重新填写!\");\r\n        document.all.dutyName.select();\r\n        </script>\r\n");
}else if("3".equals(opResult)){
	
            out.write("\r\n    <script>alert(\"职务编码重复,请重新填写!\");</script>\r\n");

}else if("2".equals(opResult)){

            out.write("\r\n       <script>alert(\"操作不成功!\");</script>\r\n");

}else{
opResult=request.getAttribute("shouldClose")==null?"":request.getAttribute("shouldClose").toString();
if("1".equals(opResult)){

            out.write("\r\n<script>\r\nopener.window.location.href=((window.location.href).substring(0,(window.location.href).indexOf(\"/jsoa\")))+\"/jsoa/DutyAction.do?action=view&pager.offset=");
            out.print(offset);
            out.write("&fromSystem=");
            out.print(fromSystem);
            out.write("&searchDutyName=");
            out.print(searchDutyName);
            out.write("&searchDutyLevel=");
            out.print(searchDutyLevel);
            out.write("\";\r\nwindow.close();\r\n</script>\r\n");

} }

            out.write("\r\n<script language=\"javascript\">\r\n<!--\r\nfunction init(){\r\n    //DutyActionForm.dutyName.focus();\r\n    var done = \"");
            out.print(request.getParameter("done"));
            out.write("\" ;\r\n    var con = \"");
            out.print(request.getParameter("con"));
            out.write("\" ;\r\n    var hhref = ((window.location.href).substring(0,(window.location.href).indexOf(\"/jsoa\")))+\"/jsoa/DutyAction.do?action=view&pager.offset=");
            out.print(offset);
            out.write("&fromSystem=");
            out.print(fromSystem);
            out.write("&searchDutyName=");
            out.print(searchDutyName);
            out.write("&searchDutyLevel=");
            out.print(searchDutyLevel);
            out.write("\" ;\r\n     if(done==\"done\") {\r\n         opener.location.href= hhref ;\r\n         //window.close();\r\n     }else if(con==\"continue\"){\r\n         opener.location.href= hhref ;\r\n     }\r\n}\r\n\r\nfunction commit(){\r\n    var issubmit = true;\r\n    if(textIsEmpty(document.all.dutyNO)){\r\n        alert(\"职务编码不能为空！\");\r\n        issubmit = false;\r\n        return false;\r\n    }\r\n    if(textIsEmpty(document.all.dutyName)){\r\n        alert(\"职务名称不能为空！\");\r\n        issubmit = false;\r\n        return false;\r\n    }\r\n\tif(legalCharacters(document.all.dutyName)){\r\n     alert(\"职务名称不能包含以下字符： \\\\\\\"#$%&'()=`|~{+*}<>?^\\\\@[]\\.,;:!/\");\r\n\t document.all.dutyName.focus();\r\n\t return false;\r\n\t}\r\n\tif(textIsEmpty(document.all.dutyLevel)){\r\n\t\talert(\"职务级别不能为空!\");\r\n\t\treturn false;\r\n\t}else{\r\n\t\tif(isNaN(document.all.dutyLevel.value)){\r\n\t\t\talert(\"职务级别必须为数字!\");\r\n\t\t\treturn false;\r\n\t\t}\r\n\t}\r\n\tif(document.all.dutyDescribe.value.length>250){\r\n      alert(\"职务描述不能超过250个字符！\");\r\n\t  return false;\r\n\t}\r\n\treturn true;\r\n}\r\n//保存继续\r\nfunction saveContinue() {\r\n        if(commit()==false) return ;\r\n");
            out.write("\tDutyActionForm.con.value = \"continue\";\r\n\tDutyActionForm.done.value = \"\";\r\n\tsetButtonDisabled('saveAndExit',true);\r\n    setButtonDisabled('saveAndContinue',true);\r\n    setTimeout(\"fob()\",8000);\r\n\tDutyActionForm.submit();\r\n}\r\n\r\n\r\n//保存退出\r\nfunction saveClose() {\r\n       if(commit()==false) return ;\r\n       DutyActionForm.done.value = \"done\";\r\n       setButtonDisabled('saveAndExit',true);\r\n    setButtonDisabled('saveAndContinue',true);\r\n    setTimeout(\"fob()\",8000);\r\n    \tDutyActionForm.submit();\r\n}\r\nfunction fob(){\r\n    setButtonDisabled('saveAndExit',false);\r\n    setButtonDisabled('saveAndContinue',false);\r\n}\r\n\r\nfunction legalCharacters(o) {\r\n\t//参数'o'是页面上的一个对象，如'document.forms[0].code'\r\n\t//var cnst =\"!\\\"#$%&'()=`|~{+*}<>?_-^\\\\@[;:],./\";\r\n\tvar cnst =\"\\\\\\\"#$%&'()=`|~{+*}<>?^\\\\@[]\\.,;:!/\";\r\n\r\n    for (i=0;i<o.value.length;i++){\r\n       \tif (cnst.indexOf(o.value.charAt(i))>-1){\r\n\t\t\treturn true;\r\n        }\r\n    }\r\n    return false;\r\n}\r\n\r\n//-->\r\n</script>\r\n");
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
      out.write("\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_005fhidden_005f0 = (org.apache.struts.taglib.html.HiddenTag) _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    boolean _jspx_th_html_005fhidden_005f0_reused = false;
    try {
      _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005fhidden_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /office_manager/officemanager_function_add.jsp(51,4) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fhidden_005f0.setProperty("action");
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
      // /office_manager/officemanager_function_add.jsp(60,27) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setStyleClass("inputtext");
      // /office_manager/officemanager_function_add.jsp(60,27) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setProperty("dutyNO");
      // /office_manager/officemanager_function_add.jsp(60,27) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setStyle("width:95%");
      // /office_manager/officemanager_function_add.jsp(60,27) name = maxlength type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setMaxlength("20");
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
      // /office_manager/officemanager_function_add.jsp(65,27) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f1.setStyleClass("inputtext");
      // /office_manager/officemanager_function_add.jsp(65,27) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f1.setProperty("dutyName");
      // /office_manager/officemanager_function_add.jsp(65,27) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f1.setStyle("width:95%");
      // /office_manager/officemanager_function_add.jsp(65,27) name = maxlength type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f1.setMaxlength("30");
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
      // /office_manager/officemanager_function_add.jsp(70,27) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f2.setStyleClass("inputtext");
      // /office_manager/officemanager_function_add.jsp(70,27) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f2.setProperty("dutyLevel");
      // /office_manager/officemanager_function_add.jsp(70,27) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f2.setStyle("width:95%");
      // /office_manager/officemanager_function_add.jsp(70,27) name = maxlength type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f2.setMaxlength("3");
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
    org.apache.struts.taglib.html.TextareaTag _jspx_th_html_005ftextarea_005f0 = (org.apache.struts.taglib.html.TextareaTag) _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleId_005fstyleClass_005fstyle_005frows_005fproperty_005fnobody.get(org.apache.struts.taglib.html.TextareaTag.class);
    boolean _jspx_th_html_005ftextarea_005f0_reused = false;
    try {
      _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005ftextarea_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /office_manager/officemanager_function_add.jsp(75,6) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setStyleClass("inputTextarea");
      // /office_manager/officemanager_function_add.jsp(75,6) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setProperty("dutyDescribe");
      // /office_manager/officemanager_function_add.jsp(75,6) name = styleId type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setStyleId("dutyDescribe");
      // /office_manager/officemanager_function_add.jsp(75,6) name = rows type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setRows("3");
      // /office_manager/officemanager_function_add.jsp(75,6) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setStyle("width:95%");
      int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
      if (_jspx_th_html_005ftextarea_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleId_005fstyleClass_005fstyle_005frows_005fproperty_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
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
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_005fhidden_005f1 = (org.apache.struts.taglib.html.HiddenTag) _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    boolean _jspx_th_html_005fhidden_005f1_reused = false;
    try {
      _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
      _jspx_th_html_005fhidden_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /office_manager/officemanager_function_add.jsp(81,4) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fhidden_005f1.setProperty("con");
      // /office_manager/officemanager_function_add.jsp(81,4) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fhidden_005f1.setValue(" ");
      int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
      if (_jspx_th_html_005fhidden_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
      _jspx_th_html_005fhidden_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fhidden_005f1, _jsp_getInstanceManager(), _jspx_th_html_005fhidden_005f1_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005fhidden_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_005fhidden_005f2 = (org.apache.struts.taglib.html.HiddenTag) _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    boolean _jspx_th_html_005fhidden_005f2_reused = false;
    try {
      _jspx_th_html_005fhidden_005f2.setPageContext(_jspx_page_context);
      _jspx_th_html_005fhidden_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /office_manager/officemanager_function_add.jsp(82,4) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fhidden_005f2.setProperty("done");
      // /office_manager/officemanager_function_add.jsp(82,4) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fhidden_005f2.setValue(" ");
      int _jspx_eval_html_005fhidden_005f2 = _jspx_th_html_005fhidden_005f2.doStartTag();
      if (_jspx_th_html_005fhidden_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
      _jspx_th_html_005fhidden_005f2_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fhidden_005f2, _jsp_getInstanceManager(), _jspx_th_html_005fhidden_005f2_reused);
    }
    return false;
  }
}
