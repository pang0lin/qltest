/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:42:50 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.userdb;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.oa.userdb.service.CustomDatabaseBD;

public final class associate_005ftable_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("com.js.oa.userdb.service.CustomDatabaseBD");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction;
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
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.release();
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

      out.write("\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<title>无标题文档</title>\r\n</head>\r\n");

	if(request.getAttribute("success")!=null && request.getAttribute("success").equals("1")){

      out.write("\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n<!--\r\n\talert(\"数据库关联保存成功！\");\r\n//-->\r\n</SCRIPT>\r\n");

	}else if(request.getAttribute("success")!=null && request.getAttribute("success").equals("0")){

      out.write("\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n<!--\r\n\talert(\"数据库关联保存失败！\");\r\n//-->\r\n</SCRIPT>\r\n");
}
      out.write("\r\n<link href=\"skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\r\n<body  class=\"MainFrameBox\">\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td>\r\n\t");
      //  html:form
      org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag) _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(org.apache.struts.taglib.html.FormTag.class);
      boolean _jspx_th_html_005fform_005f0_reused = false;
      try {
        _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
        _jspx_th_html_005fform_005f0.setParent(null);
        // /userdb/associate_table.jsp(42,1) name = action type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_html_005fform_005f0.setAction("/ShowAction.do");
        // /userdb/associate_table.jsp(42,1) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_html_005fform_005f0.setMethod("POST");
        int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
        if (_jspx_eval_html_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n\t<input type=\"hidden\" name=\"operate\">\r\n\t<input type=\"hidden\" name=\"fieldId\">\r\n\t<input type=\"hidden\" name=\"tableid\" value=\"");
            out.print(request.getParameter("tableid"));
            out.write("\">\r\n\t<input type=\"hidden\" name=\"tablename\" value=\"");
            out.print(request.getParameter("tablename"));
            out.write("\">\r\n<table width=\"100%\" height=\"25\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n  <tr>\r\n    <td>\r\n\t  <div id=\"Panle1\" class=\"btnBQ AlignLeft\" onclick=\"javascript:window.location.href='/jsoa/FieldAction.do?operate=list&tableid=");
            out.print(request.getParameter("tableid"));
            out.write("&tablename=");
            out.print(request.getParameter("tablename"));
            out.write("'\">字段</div>\r\n\t  <div class=\"btnBQspace AlignLeft\"></div>\r\n\t  <div id=\"Panle2\" class=\"btnBQ AlignLeft\" onclick=\"javascript:window.location.href='/jsoa/AssociateAction.do?operate=list&tableid=");
            out.print(request.getParameter("tableid"));
            out.write("&tablename=");
            out.print(request.getParameter("tablename"));
            out.write("'\">关联</div>\r\n\t  <div class=\"btnBQspace AlignLeft\"></div>\r\n\t  <div id=\"Panle3\" class=\"btnBQ AlignLeft\" onclick=\"javascript:window.location.href='/jsoa/ShowAction.do?operate=list&tableid=");
            out.print(request.getParameter("tableid"));
            out.write("&tablename=");
            out.print(request.getParameter("tablename"));
            out.write("'\">显示</div>\r\n\t   <div class=\"btnBQspace AlignLeft\"></div>\r\n\t  <div id=\"Panle4\" class=\"btnBQ AlignLeft\" onclick=\"javascript:window.location.href='/jsoa/ShowAction.do?operate=queryField&tableid=");
            out.print(request.getParameter("tableid"));
            out.write("&tablename=");
            out.print(request.getParameter("tablename"));
            out.write("'\">查询项</div>\r\n\t  <div class=\"btnBQspace AlignLeft\"></div>\r\n\t   <div id=\"Panle5\" class=\"btnBQselected AlignLeft\" onclick=\"javascript:window.location.href='/jsoa/ShowAction.do?operate=associatetable&tableid=");
            out.print(request.getParameter("tableid"));
            out.write("&tablename=");
            out.print(request.getParameter("tablename"));
            out.write("'\">关系表</div>\r\n           <div class=\"btnBQspace AlignLeft\"></div>\r\n        <div id=\"Panle6\" class=\"btnBQ AlignLeft\" onclick=\"javascript:window.location.href='/jsoa/ShowAction.do?operate=totField&tableid=");
            out.print(request.getParameter("tableid"));
            out.write("&tablename=");
            out.print(request.getParameter("tablename"));
            out.write("'\">合计项</div>\r\n        </td>\r\n    <td width=\"140\" align=\"right\" nowrap>&nbsp;</td>\r\n  </tr>\r\n</table>\r\n\r\n\r\n<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"listTable\">\r\n\t<tr align=\"center\">\r\n\t       <td width=\" 10%\" class=\"listTableHead\">关系类型</td>\r\n\t\t\t<td width=\"40%\" class=\"listTableHead\">数据表</td>\r\n\t\t\t<td width=\"10%\" class=\"listTableHead\">&nbsp;</td>\r\n\t\t\t<td nowrap class=\"listTableHeadLast\">相关数据表</td>\r\n\t</tr>\r\n\t<tr>\r\n\t    <td align=\"center\" valign=\"center\">\r\n\t      <select name=\"associtype\">\r\n\r\n\t\t   <option value=\"0\" >一对多</option>\r\n\t\t   <option value=\"1\" >多对多</option>\r\n\r\n\t\t   </select>\r\n\t\t</td>\r\n\t\t<td align=\"center\" style=\"padding-top:15px;\">\r\n\t\t  <select size=12 name=\"field\" multiple=\"true\"  style=\"width:200px;\">\r\n\t\t\t");
            //  logic:iterate
            org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_005fiterate_005f0 = (org.apache.struts.taglib.logic.IterateTag) _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid.get(org.apache.struts.taglib.logic.IterateTag.class);
            boolean _jspx_th_logic_005fiterate_005f0_reused = false;
            try {
              _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
              _jspx_th_logic_005fiterate_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
              // /userdb/associate_table.jsp(85,3) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_logic_005fiterate_005f0.setId("fieldlist");
              // /userdb/associate_table.jsp(85,3) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_logic_005fiterate_005f0.setName("fieldlist");
              // /userdb/associate_table.jsp(85,3) name = scope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_logic_005fiterate_005f0.setScope("request");
              int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
              if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                java.lang.Object fieldlist = null;
                if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = org.apache.jasper.runtime.JspRuntimeLibrary.startBufferedBody(_jspx_page_context, _jspx_th_logic_005fiterate_005f0);
                }
                fieldlist = (java.lang.Object) _jspx_page_context.findAttribute("fieldlist");
                do {
                  out.write("\r\n\t\t\t");

				Object[] obj = (Object[]) fieldlist;

			
                  out.write("\r\n\t\t\t\t<option value=\"");
                  out.print(obj[0]);
                  out.write('"');
                  out.write('>');
                  out.print(obj[1]);
                  out.write("</option>\r\n\r\n\t\t\t");
                  int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
                  fieldlist = (java.lang.Object) _jspx_page_context.findAttribute("fieldlist");
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
            out.write("\r\n\t\t  </select>\r\n\t\t</td>\r\n\t\t<td height=\"100%\">\r\n\t\t  <div style=\"height:100%;padding-top:20px;\" align=\"center\" valign=\"center\">\r\n\t\t    <div style=\"float:center;height:25%;\">\r\n\t\t    <img src=\"/jsoa/images/arrow_right1.gif\" onclick=\"transferOptions1()\">\r\n\t\t    </div>\r\n\t\t\t<div style=\"float:top;height:25%;\">\r\n\t\t\t<img src=\"/jsoa/images/arrow_right2.gif\" onclick=\"transferOptionsAll1()\">\r\n\t\t\t</div>\r\n\t\t\t<div style=\"float:top;height:25%;\">\r\n\t\t\t<img src=\"/jsoa/images/arrow_left1.gif\" onclick=\"transferOptions2()\">\r\n\t\t\t</div>\r\n\t\t\t<div style=\"float:top;height:25%;\">\r\n\t\t\t<img src=\"/jsoa/images/arrow_left2.gif\" onclick=\"transferOptionsAll2()\">\r\n\t\t\t</div>\r\n\t\t  </div>\t\t\t\r\n\t\t</td>\r\n\t\t<td align=\"center\"  style=\"padding-top:15px;\">\r\n\t\t  <select size=12 name=\"queryField\" multiple=\"true\"  style=\"width:200px;\">\r\n\t\t\t");
            //  logic:iterate
            org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_005fiterate_005f1 = (org.apache.struts.taglib.logic.IterateTag) _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid.get(org.apache.struts.taglib.logic.IterateTag.class);
            boolean _jspx_th_logic_005fiterate_005f1_reused = false;
            try {
              _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
              _jspx_th_logic_005fiterate_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
              // /userdb/associate_table.jsp(113,3) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_logic_005fiterate_005f1.setId("querylist");
              // /userdb/associate_table.jsp(113,3) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_logic_005fiterate_005f1.setName("querylist");
              // /userdb/associate_table.jsp(113,3) name = scope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_logic_005fiterate_005f1.setScope("request");
              int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
              if (_jspx_eval_logic_005fiterate_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                java.lang.Object querylist = null;
                if (_jspx_eval_logic_005fiterate_005f1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = org.apache.jasper.runtime.JspRuntimeLibrary.startBufferedBody(_jspx_page_context, _jspx_th_logic_005fiterate_005f1);
                }
                querylist = (java.lang.Object) _jspx_page_context.findAttribute("querylist");
                do {
                  out.write("\r\n\t\t\t");

				Object[] obj = (Object[]) querylist;
			
                  out.write("\r\n\t\t\t<option value=\"");
                  out.print(obj[0]);
                  out.write('"');
                  out.write('>');
                  out.print(obj[1]);
                  out.write("</option>\r\n\t\t\t");
                  int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
                  querylist = (java.lang.Object) _jspx_page_context.findAttribute("querylist");
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
                if (_jspx_eval_logic_005fiterate_005f1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.popBody();
                }
              }
              if (_jspx_th_logic_005fiterate_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                return;
              }
              _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
              _jspx_th_logic_005fiterate_005f1_reused = true;
            } finally {
              org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_logic_005fiterate_005f1, _jsp_getInstanceManager(), _jspx_th_logic_005fiterate_005f1_reused);
            }
            out.write("\r\n\t\t  </select>\r\n\t\t</td>\r\n\t</tr>\r\n\r\n\t <tr>\r\n\t\t<td style=\"padding-bottom:20px;\">\r\n\t\t  <input type=\"button\"  class=\"btnButton2Font\" onClick=\"javascript:save();\" value=\"保存\"/>\r\n\t\t\t<button class=\"btnButton2Font\" onClick=\"javascript:window.location.href='/jsoa/ShowAction.do?operate=associatetable&tableid=");
            out.print(request.getParameter("tableid"));
            out.write("&tablename=");
            out.print(request.getParameter("tablename"));
            out.write("';\">重置</button></td>\r\n     </tr>\r\n\r\n</table>\r\n\r\n");
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
      out.write("\r\n</td></tr></table>\r\n</body>\r\n</html>\r\n\r\n<script language=\"javascript\">\r\n\r\nfunction transferOptions1(){\r\n\t//var srcSel =eval(\"document.all.\"+srcObj);\r\n\r\n    var type=document.all.associtype.value;\r\n\r\n\tvar srcValue = new Array();\r\n\tvar srcText  = new Array();\r\n\tvar desValue = new Array();\r\n\tvar desText  = new Array();\r\n\tnSrc = 0;\r\n\tnDes = 0;\r\n\tnLen =\tdocument.all.field.length;\r\n\tfor(i=0;i<nLen;i++){\r\n\t\t//var optObj = document.all.field.options(i);\r\n\r\n\t\tvar strValue =document.all.field.options(i).value;\r\n\t\tvar strText = document.all.field.options(i).text;\r\n\r\n\r\n\t\tif(document.all.field.options(i).selected){\r\n\t\t\tdocument.all.field.options(i).selected =false;\r\n\r\n\t\t\tif(type==\"1\")\r\n          strText=strText+\"*\"\r\n\r\n\t\t\tdesValue[nDes] = strValue;\r\n\t\t\tdesText[nDes]=strText;\r\n\t\t\tnDes ++;\r\n\t\t}\r\n\t\telse{\r\n\t\t\tsrcValue[nSrc] = strValue;\r\n\t\t\tsrcText[nSrc]=strText;\r\n\t\t\tnSrc ++;\r\n\t\t}\r\n\t}\r\n\r\n\r\n\twhile(document.all.field.length>0)\r\n\t\tdocument.all.field.remove(0);\r\n\r\n\r\n\tfor(i=0;i<nSrc;i++){\r\n\t\tvar newOpt =document.createElement(\"OPTION\");\r\n");
      out.write("\t\tnewOpt.value=srcValue[i];\r\n\t\tnewOpt.text =srcText[i];\r\n\t\tdocument.all.field.add(newOpt);\r\n\t}\r\n\r\n\tfor(i=0;i<nDes;i++){\r\n\t\tvar newOpt =document.createElement(\"OPTION\");\r\n\t\tnewOpt.value=desValue[i];\r\n\t\tnewOpt.text =desText[i];\r\n\t\tdocument.all.queryField.add(newOpt);\r\n\t}\r\n\r\n\tdocument.all.queryField.selectedIndex =-1;\r\n}\r\n\r\nfunction transferOptionsAll1(){\r\n\tvar opts =document.all.field.options;\r\n\r\n\tfor(j=0,i=0;i<opts.length;i++){\r\n\t\topts(i).selected=true;\r\n\t}\r\n\ttransferOptions1();\r\n}\r\n\r\n\r\n\r\n\r\nfunction transferOptionsAll2(){\r\n\tvar opts =document.all.queryField.options;\r\n\r\n\tfor(j=0,i=0;i<opts.length;i++){\r\n\t\topts(i).selected=true;\r\n\t}\r\n\ttransferOptions2();\r\n}\r\n\r\n\r\n\r\nfunction transferOptions2(){\r\n\t//var srcSel =eval(\"document.all.\"+srcObj);\r\n\r\n\r\n\tvar srcValue = new Array();\r\n\tvar srcText  = new Array();\r\n\tvar desValue = new Array();\r\n\tvar desText  = new Array();\r\n\tnSrc = 0;\r\n\tnDes = 0;\r\n\tnLen =\tdocument.all.queryField.length;\r\n\tfor(i=0;i<nLen;i++){\r\n\t\t//var optObj = document.all.field.options(i);\r\n\r\n\t\tvar strValue =document.all.queryField.options(i).value;\r\n");
      out.write("\t\tvar strText = document.all.queryField.options(i).text;\r\n\r\n\t\tif(document.all.queryField.options(i).selected){\r\n\t\t\tdocument.all.queryField.options(i).selected =false;\r\n\r\n\t\t\tdesValue[nDes] = strValue;\r\n\t\t\t if(strText.indexOf(\"*\")!=-1){\r\n              strText=strText.substring(0,strText.length-1);\r\n\t\t\t }\r\n\t\t\tdesText[nDes]=strText;\r\n\t\t\tnDes ++;\r\n\t\t}\r\n\t\telse{\r\n\t\t\tsrcValue[nSrc] = strValue;\r\n\t\t\tsrcText[nSrc]=strText;\r\n\t\t\tnSrc ++;\r\n\t\t}\r\n\t}\r\n\r\n\r\n\twhile(document.all.queryField.length>0)\r\n\t\tdocument.all.queryField.remove(0);\r\n\r\n\r\n\tfor(i=0;i<nSrc;i++){\r\n\t\tvar newOpt =document.createElement(\"OPTION\");\r\n\t\tnewOpt.value=srcValue[i];\r\n\t\tnewOpt.text =srcText[i];\r\n\t\tdocument.all.queryField.add(newOpt);\r\n\t}\r\n\r\n\tfor(i=0;i<nDes;i++){\r\n\t\tvar newOpt =document.createElement(\"OPTION\");\r\n\t\tnewOpt.value=desValue[i];\r\n\t\tnewOpt.text =desText[i];\r\n\t\tdocument.all.field.add(newOpt);\r\n\t}\r\n\r\n\tdocument.all.field.selectedIndex =-1;\r\n}\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\nfunction transferOptions(srcObj,desObj){\r\n\tvar srcSel =eval(\"document.all.\"+srcObj);\r\n\tvar srcValue = new Array();\r\n");
      out.write("\tvar srcText  = new Array();\r\n\tvar desValue = new Array();\r\n\tvar desText  = new Array();\r\n\tnSrc = 0;\r\n\tnDes = 0;\r\n\tnLen =srcSel.length;\r\n\tfor(i=0;i<nLen;i++){\r\n\t\tvar optObj = srcSel.options(i);\r\n\t\tvar strValue = optObj.value;\r\n\t\tvar strText = optObj.text;\r\n\r\n\t\tif(optObj.selected){\r\n\t\t\toptObj.selected =false;\r\n\t\t\tdesValue[nDes] = strValue;\r\n\t\t\tdesText[nDes]=strText;\r\n\t\t\tnDes ++;\r\n\t\t}\r\n\t\telse{\r\n\t\t\tsrcValue[nSrc] = strValue;\r\n\t\t\tsrcText[nSrc]=strText;\r\n\t\t\tnSrc ++;\r\n\t\t}\r\n\t}\r\n\twhile(srcSel.length>0)\r\n\t\teval(\"document.all.\"+srcObj).remove(0);\r\n\r\n\tfor(i=0;i<nSrc;i++){\r\n\t\tvar newOpt =document.createElement(\"OPTION\");\r\n\t\tnewOpt.value=srcValue[i];\r\n\t\tnewOpt.text =srcText[i];\r\n\t\teval(\"document.all.\"+srcObj).add(newOpt);\r\n\t}\r\n\r\n\tfor(i=0;i<nDes;i++){\r\n\t\tvar newOpt =document.createElement(\"OPTION\");\r\n\t\tnewOpt.value=desValue[i];\r\n\t\tnewOpt.text =desText[i];\r\n\t\teval(\"document.all.\"+desObj).add(newOpt);\r\n\t}\r\n\r\n\teval(\"document.all.\"+desObj).selectedIndex =-1;\r\n}\r\n\r\nfunction transferOptionsAll(srcObj,desObj){\r\n\tvar opts =eval(\"document.all.\"+srcObj).options;\r\n");
      out.write("\tfor(j=0,i=0;i<opts.length;i++){\r\n\t\topts(i).selected=true;\r\n\t}\r\n\ttransferOptions(srcObj,desObj);\r\n}\r\n\r\n\r\n\r\n\r\nfunction save(){\r\n\tdocument.all.operate.value=\"addAssociateTableContinue\";\r\n\tvar opts =eval(\"document.all.queryField\").options;\r\n\tfor(j=0,i=opts.length-1;i>-1;i--){\r\n\t\t  var textvalue=opts(i).text;\r\n\t\t  if(textvalue.indexOf(\"*\")!=-1)\r\n\t\tdocument.all.fieldId.value +=opts(i).value+\"*,\";\r\n\t\t  else\r\n        document.all.fieldId.value +=opts(i).value+\",\";\r\n\r\n\t}\r\n\tShowActionForm.submit();\r\n}\r\nfunction openNew(url){\r\n\tJSMainWinOpen(url,\"\",\"TOP=0,LEFT=0,scrollbars=yes,resizable=yes,width=600,height=300\") ;\r\n}\r\n</script>\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n");
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
