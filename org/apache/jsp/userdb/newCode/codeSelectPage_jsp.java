/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:43:20 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.userdb.newCode;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.oa.userdb.newCode.po.*;

public final class codeSelectPage_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("com.js.oa.userdb.newCode.po");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhtml;
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
    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fhtml.release();
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
String returnVal=(String)request.getAttribute("returnVal");

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
            out.write("\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<link href=\"skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<title>????????????</title>\r\n<SCRIPT language=javascript src=\"/jsoa/js/openEndow.js\"></SCRIPT>\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n");
if(request.getAttribute("reload")!=null){ 
String codeNumber=(String)request.getAttribute("codeNumber");
String codeId=(String)request.getAttribute("codeId");

            out.write("\r\n<script language=\"JavaScript\">\r\nvar returnVal='");
            out.print(returnVal);
            out.write("';\r\nif(returnVal!=\"\"&&returnVal!=\"null\"){\r\n\tvar returnVals=returnVal.split(\",,\");\r\n\topener.window.document.getElementsByName(returnVals[0])[0].value='");
            out.print(codeNumber);
            out.write("';\r\n\topener.window.document.getElementsByName(returnVals[1])[0].value='");
            out.print(codeId);
            out.write("';\r\n}\r\nwindow.close();\r\n</script>\r\n");
} else{
            out.write("\r\n</head>\r\n<body  style=\"padding-top: 0px;padding-bottom: 5px;margin-left: 5px;margin-right: 5px;\">\r\n<form name=\"frm\" action=\"/jsoa/NewCodeAction.do\" method=\"get\">\r\n\t\t<input type=\"hidden\" name=\"action\" value=\"getCodeNumber\">\r\n\t\t<input type=\"hidden\" name=\"returnVal\" value=\"");
            out.print(returnVal);
            out.write("\">\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n\r\n<tr>\r\n<td>\r\n\r\n<table width=\"100%\" height=\"25\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"inlineBottomLine\">\r\n  <tr>\r\n    <td>&nbsp;</td>\r\n    <td align=\"right\" nowrap>    \r\n   <input type=\"button\" class=\"btnButton4font\" onClick=\"ok();\" value='??????' />\r\n<input type=\"button\" class=\"btnButton4font\" onClick=\"window.close();\" value='??????' />\r\n    </td>\r\n  </tr>\r\n</table>\r\n<div style=\"height:305px; overflow:auto;\">\r\n\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"listTable\">\r\n\t\t<tr >\r\n\t\t    <td class=\"listTableHead\" style=\"width:25px;\">&nbsp;</td>\r\n\t\t\t<td class=\"listTableHead\" style=\"width:40%;\">????????????</td>\r\n\t\t\t<td class=\"listTableHeadLast\">????????????</td>\r\n\t\t</tr>\r\n\t\t\r\n\t\t\t");

				int index=0;
			
            out.write("\r\n\t\t\t");
            //  logic:iterate
            org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_005fiterate_005f0 = (org.apache.struts.taglib.logic.IterateTag) _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid.get(org.apache.struts.taglib.logic.IterateTag.class);
            boolean _jspx_th_logic_005fiterate_005f0_reused = false;
            try {
              _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
              _jspx_th_logic_005fiterate_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
              // /userdb/newCode/codeSelectPage.jsp(66,3) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_logic_005fiterate_005f0.setId("codeList");
              // /userdb/newCode/codeSelectPage.jsp(66,3) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_logic_005fiterate_005f0.setName("codeList");
              // /userdb/newCode/codeSelectPage.jsp(66,3) name = scope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_logic_005fiterate_005f0.setScope("request");
              int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
              if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                java.lang.Object codeList = null;
                if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = org.apache.jasper.runtime.JspRuntimeLibrary.startBufferedBody(_jspx_page_context, _jspx_th_logic_005fiterate_005f0);
                }
                codeList = (java.lang.Object) _jspx_page_context.findAttribute("codeList");
                do {
                  out.write("\r\n\t\t\t");
			
				index++;				
			NewCodePO obj = (NewCodePO) codeList;
				String listClass="listTableLine1";
				if(index%2 != 0){
					listClass="listTableLine2";
				}
			
                  out.write("\r\n\t\r\n\t\t<tr>\r\n\t\t\t<td class=\"");
                  out.print(listClass);
                  out.write("\"><input type=\"radio\" name=\"codeId\" value=\"");
                  out.print(obj.getCodeId());
                  out.write("\">&nbsp;</td>\r\n\t\t\t<td class=\"");
                  out.print(listClass);
                  out.write('"');
                  out.write('>');
                  out.print(obj.getCodeName() );
                  out.write("&nbsp;</td>\r\n\t\t\t<td class=\"");
                  out.print(listClass);
                  out.write(" listTableLineLastTD\">");
                  out.print(obj.getCodeContent()==null||"null".equals(obj.getCodeContent())?"":obj.getCodeContent() );
                  out.write("&nbsp;</td>\r\n\t\t\t\r\n\t\t</tr>\r\n\t\t\t\r\n\t\t");
                  int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
                  codeList = (java.lang.Object) _jspx_page_context.findAttribute("codeList");
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
            out.write("\r\n\t\t\r\n\t</table>\r\n\t</div>\r\n</td></tr>\r\n\r\n</table>\r\n</form>\r\n</body>\r\n");
} 
            out.write('\r');
            out.write('\n');
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
      out.write("\r\n\r\n<script language=\"javascript\">\r\nString.prototype.trim = function () {\r\n\treturn this.replace(/^\\s\\s*/,'').replace(/\\s\\s*$/,'');\r\n}\r\nfunction ok(){\r\n\tvar ras=document.getElementsByName(\"codeId\");\r\n\tvar val=\"\";\r\n\tfor(var i=0;i<ras.length;i++){\r\n\t\tif(ras[i].checked){\r\n\t\t\tval=ras[i].value;\r\n\t\t\tbreak;\r\n\t\t}\r\n\t}\r\n\tif(val==\"\"){\r\n\t\talert(\"?????????????????????\");\r\n\t\treturn;\r\n\t}\r\n\tdocument.all.frm.submit();\r\n}\r\n</script>\r\n");
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
