/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:45:24 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.netdisk;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.lang.Resource;

public final class netdisk_005frename_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("com.js.lang.Resource");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhtml;
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
    _005fjspx_005ftagPool_005fhtml_005fhtml = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fhtml.release();
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
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

request.setCharacterEncoding("GBK"); 
String webapp=request.getContextPath();
  String flag="";
  if(request.getAttribute("flag")!=null){
    flag=request.getAttribute("flag").toString();
  }
  String local = session.getAttribute("org.apache.struts.action.LOCALE").toString();
  
String idString=request.getParameter("idString");

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
            out.write("\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<title>");
            if (_jspx_meth_bean_005fmessage_005f0(_jspx_th_html_005fhtml_005f0, _jspx_page_context))
              return;
            out.write("</title>\r\n<link href=\"/jsoa/skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<!--<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>-->\r\n<!--<script language=\"javascript\" src=\"/jsoa/js/checkForm.js\"></script>-->\r\n<script language=\"JavaScript\" src=\"/jsoa/js/resource/");
            out.print(local);
            out.write("/CommonResource.js\" type=\"text/javascript\"></script>\r\n<script language=\"JavaScript\" src=\"/jsoa/js/resource/");
            out.print(local);
            out.write("/PersonalworkResource.js\" type=\"text/javascript\"></script>\r\n<SCRIPT language=javascript src=\"/jsoa/webmail/ajax_util.js\"></SCRIPT>\r\n</head>\r\n<body leftmargin=\"0\" topmargin=\"0\"  class=\"MainFrameBox Pupwin\" onLoad=\"init();resizeWin(450,200);\">\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td>\r\n<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"docBoxNoPanel\">\r\n<tr>\r\n <td>\r\n  <table height=\"230\">\r\n    <form name=\"form1\" action=\"/jsoa/NetdiskAction.do?action=rename\" method=\"post\" onkeypress= \"if(event.keyCode   ==   13)event.returnValue   =   false; \"> \r\n\t\t\t<tr><td>");
if("dir".equals(request.getParameter("type"))){
            if (_jspx_meth_bean_005fmessage_005f1(_jspx_th_html_005fhtml_005f0, _jspx_page_context))
              return;
            out.write('：');
}else{
            if (_jspx_meth_bean_005fmessage_005f2(_jspx_th_html_005fhtml_005f0, _jspx_page_context))
              return;
            out.write('：');
}
            out.write("</td>\r\n\t\t\t<input type=\"hidden\" name=\"netdiskid\" value=\"");
            out.print(request.getParameter("netdiskid"));
            out.write("\">\r\n\t\t\t <input type=\"hidden\" name=\"fileidstring\" value=\"");
            out.print(idString);
            out.write("\">\r\n\t\t\t <input type=\"hidden\" name=\"oldname\" value=\"");

				  if(request.getParameter("oldname") !=null && !"".equals(request.getParameter("oldname"))){
				     out.print(request.getParameter("oldname"));
				  }else{
				    out.print("");
				  }
				  
            out.write("\">\r\n\t\t\t  <td height=\"40\" valign=\"middle\" colspan=\"2\">\r\n\t\t\t\t  <input type=\"text\" name=\"rename\" class=\"inputtext\" maxLength=\"45\" size=\"45\"  value=\"");

				  if(request.getParameter("oldname") !=null && !"".equals(request.getParameter("oldname"))){
				     out.print(request.getParameter("oldname"));
				  }else{
				    out.print("");
				  }
				  
            out.write("\"><label class=\"mustFillcolor\">*</label>\r\n\t\t\t  </td>\r\n\t\t\t</tr>\r\n\t\t\t<tr>\r\n\t\t\t  <td colspan=\"2\" align=\"center\" height=\"40\" valign=\"bottom\">\r\n\t\t\t   <table width=\"50%\" border=\"0\" cellspacing=\"1\" cellpadding=\"1\">\r\n\t\t\t\t  <tr >\r\n\t\t\t\t\t<td>\r\n\t\t\t\t\t  <input type=\"button\" class=\"btnButton4font\" style=\"cursor:pointer\" onClick=\"save();\" value=\"");
            if (_jspx_meth_bean_005fmessage_005f3(_jspx_th_html_005fhtml_005f0, _jspx_page_context))
              return;
            out.write("\" /></td>\r\n");
            out.write("\r\n\t\t\t\t\t  <td><input type=\"button\" class=\"btnButton2font\" style=\"cursor:pointer\" onClick=\"window.close();\" value=\"");
            if (_jspx_meth_bean_005fmessage_005f4(_jspx_th_html_005fhtml_005f0, _jspx_page_context))
              return;
            out.write("\" />\r\n\t\t\t\t\t</td>\r\n\t\t\t</tr>\r\n\t\t  </form>\r\n  </table>\r\n  </td>\r\n</tr>\r\n</table>\r\n</td>\r\n</tr>\r\n</table>\r\n</body>\r\n");
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
      out.write("\r\n<script language=\"javascript\">\r\nfunction checkName(name){\r\n\tif(name.indexOf(\"%\")>=0){\r\n\t\treturn true;\r\n\t}else if(name.indexOf(\"\\\"\")>=0){\r\n\t\treturn true;\r\n\t}else if(name.indexOf(\"'\")>=0){\r\n\t\treturn true;\r\n\t}\r\n\r\n}\r\nfunction save(){\r\n  var name=document.all.rename.value;\r\n  var oldname=document.all.oldname.value;\r\n  var id=document.all.netdiskid.value;\r\n  var idstring=document.all.fileidstring.value;\r\n  if(name==\"\"||name.replace(/(^\\s*)|(\\s*$)/g,\"\")=='' || checkName(name)){\r\n    alert(Personalwork.webdisk_newnameremind);\r\n\treturn false;\r\n  }else if(name==oldname){\r\n     window.close();\r\n  }else{\r\n\t  var url='");
      out.print(webapp);
      out.write("'+\"/NetdiskAction.do?action=checkFolderName&type=rename&idString=\"+idstring+\"&currenid=\"+id+\"&foldername=\"+name;\r\n\t  sendG(url,getResult,null);\r\n  }\r\n}\r\n\r\nfunction getResult(cartXML){\r\n      var cart = cartXML.getElementsByTagName(\"result\")[0];\r\n\t  var name = cart.getElementsByTagName(\"name\")[0].firstChild.nodeValue;\r\n      if(name=='true'){\r\n        alert('同级目录下文件夹名称不能重复！');\r\n        return false;\r\n      }else{\r\n        form1.submit();\r\n      }\r\n }\r\n\r\n\r\n\r\nfunction init(){\r\n  var flag=\"");
      out.print(flag);
      out.write("\";\r\n  if(flag==\"reload\"){\r\n\twindow.opener.location.reload();\r\n\twindow.close();\r\n  }\r\n}\r\nfunction res(){\r\n  //document.all.rename.value=\"\";\r\n  document.form1.reset();\r\n}\r\n</script>\r\n<!--<script src=\"/jsoa/js/util.js\"></script>-->");
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
      // /netdisk/netdisk_rename.jsp(24,7) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f0.setBundle("personalwork");
      // /netdisk/netdisk_rename.jsp(24,7) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f0.setKey("webdisk.modifyname");
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

  private boolean _jspx_meth_bean_005fmessage_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fhtml_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f1 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    boolean _jspx_th_bean_005fmessage_005f1_reused = false;
    try {
      _jspx_th_bean_005fmessage_005f1.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fmessage_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
      // /netdisk/netdisk_rename.jsp(41,62) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f1.setBundle("personalwork");
      // /netdisk/netdisk_rename.jsp(41,62) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f1.setKey("webdisk.foldername");
      int _jspx_eval_bean_005fmessage_005f1 = _jspx_th_bean_005fmessage_005f1.doStartTag();
      if (_jspx_th_bean_005fmessage_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.reuse(_jspx_th_bean_005fmessage_005f1);
      _jspx_th_bean_005fmessage_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fmessage_005f1, _jsp_getInstanceManager(), _jspx_th_bean_005fmessage_005f1_reused);
    }
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fhtml_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f2 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    boolean _jspx_th_bean_005fmessage_005f2_reused = false;
    try {
      _jspx_th_bean_005fmessage_005f2.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fmessage_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
      // /netdisk/netdisk_rename.jsp(41,136) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f2.setBundle("personalwork");
      // /netdisk/netdisk_rename.jsp(41,136) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f2.setKey("webdisk.filename");
      int _jspx_eval_bean_005fmessage_005f2 = _jspx_th_bean_005fmessage_005f2.doStartTag();
      if (_jspx_th_bean_005fmessage_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.reuse(_jspx_th_bean_005fmessage_005f2);
      _jspx_th_bean_005fmessage_005f2_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fmessage_005f2, _jsp_getInstanceManager(), _jspx_th_bean_005fmessage_005f2_reused);
    }
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fhtml_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f3 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    boolean _jspx_th_bean_005fmessage_005f3_reused = false;
    try {
      _jspx_th_bean_005fmessage_005f3.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fmessage_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
      // /netdisk/netdisk_rename.jsp(66,99) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f3.setBundle("common");
      // /netdisk/netdisk_rename.jsp(66,99) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f3.setKey("comm.saveclose");
      int _jspx_eval_bean_005fmessage_005f3 = _jspx_th_bean_005fmessage_005f3.doStartTag();
      if (_jspx_th_bean_005fmessage_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.reuse(_jspx_th_bean_005fmessage_005f3);
      _jspx_th_bean_005fmessage_005f3_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fmessage_005f3, _jsp_getInstanceManager(), _jspx_th_bean_005fmessage_005f3_reused);
    }
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f4(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fhtml_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f4 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    boolean _jspx_th_bean_005fmessage_005f4_reused = false;
    try {
      _jspx_th_bean_005fmessage_005f4.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fmessage_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
      // /netdisk/netdisk_rename.jsp(68,111) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f4.setBundle("common");
      // /netdisk/netdisk_rename.jsp(68,111) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f4.setKey("comm.exit");
      int _jspx_eval_bean_005fmessage_005f4 = _jspx_th_bean_005fmessage_005f4.doStartTag();
      if (_jspx_th_bean_005fmessage_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.reuse(_jspx_th_bean_005fmessage_005f4);
      _jspx_th_bean_005fmessage_005f4_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fmessage_005f4, _jsp_getInstanceManager(), _jspx_th_bean_005fmessage_005f4_reused);
    }
    return false;
  }
}
