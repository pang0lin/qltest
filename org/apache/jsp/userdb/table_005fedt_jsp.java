/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:42:59 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.userdb;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.oa.userdb.service.CustomDatabaseBD;

public final class table_005fedt_jsp extends org.apache.jasper.runtime.HttpJspBase
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

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhtml;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction;

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
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fhtml.release();
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.release();
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
      //  html:html
      org.apache.struts.taglib.html.HtmlTag _jspx_th_html_005fhtml_005f0 = (org.apache.struts.taglib.html.HtmlTag) _005fjspx_005ftagPool_005fhtml_005fhtml.get(org.apache.struts.taglib.html.HtmlTag.class);
      boolean _jspx_th_html_005fhtml_005f0_reused = false;
      try {
        _jspx_th_html_005fhtml_005f0.setPageContext(_jspx_page_context);
        _jspx_th_html_005fhtml_005f0.setParent(null);
        int _jspx_eval_html_005fhtml_005f0 = _jspx_th_html_005fhtml_005f0.doStartTag();
        if (_jspx_eval_html_005fhtml_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write('\r');
            out.write('\n');

if(request.getParameter("close") != null){

            out.write("\r\n<script language=\"javascript\">\r\n\talert(\"???????????????\");\r\n\twindow.close();\r\n\topener.window.location.href=((window.location.href).substring(0,(window.location.href).indexOf(\"/jsoa\")))+\"/jsoa/TableAction.do?pager.offset=");
            out.print(request.getParameter("pager.offset"));
            out.write("\";\r\n</script>\r\n");

}else{
            out.write("\r\n\r\n");
if("1".equals(request.getParameter("stat"))){
            out.write("\r\n<script language=\"javascript\">\r\n    alert(1);\r\n\talert(\"???????????????????????????????????????\");\r\n</script>\r\n");
} if("0".equals(request.getParameter("stat"))){
            out.write("\r\n\t<script language=\"javascript\">\r\n\t\talert(\"?????????????????????????????????????????????????????????\");\r\n\t</script>\r\n");
}
	String code = "";
	String desname = "";
	String fullName="";
	String name = "";
	String tmodel = "";
	String sysmark= "";


	CustomDatabaseBD bd = new CustomDatabaseBD();

	Object obj = session.getAttribute("domainId");
	if(obj==null || obj.equals(""))
		obj="";
	String[][] model = bd.getModelIDName(obj.toString());

	String[][] tableInfo = bd.getSingleTable(request.getParameter("tableid"));
	if(tableInfo!=null && tableInfo.length>0){
		//String sysmark= bd.getSystemMark();
		code = tableInfo[0][1];
		desname = tableInfo[0][2];
		fullName = tableInfo[0][3];
		tmodel = tableInfo[0][4];
		if(fullName.indexOf("$")!=-1){
			sysmark=fullName.substring(0,fullName.indexOf("$"));
			name=fullName.substring(fullName.indexOf("$")+1);
		}else{
			if(fullName.indexOf("_")!=-1){
				sysmark=fullName.substring(0,fullName.indexOf("_"));
				name=fullName.substring(fullName.indexOf("_")+1);
			}
		}
	}else{
            out.write("\r\n\t\t<SCRIPT LANGUAGE=\"JavaScript\">\r\n\t\t<!--\r\n\t\talert(\"??????????????????\");\r\n\t\twindow.close();\r\n\t\t//-->\r\n\t\t</SCRIPT>\r\n\t");
}
            out.write("\r\n\r\n<head>\r\n<title>??????????????????????????????</title>\r\n<link href=\"/jsoa/skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject2.css\">\r\n<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n</head>\r\n<body  class=\"MainFrameBox Pupwin\" onload=\"resizeWin(650,300);\">\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td>\r\n\r\n<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"docBoxNoPanel\">\r\n");
            //  html:form
            org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag) _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(org.apache.struts.taglib.html.FormTag.class);
            boolean _jspx_th_html_005fform_005f0_reused = false;
            try {
              _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
              _jspx_th_html_005fform_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
              // /userdb/table_edt.jsp(84,0) name = action type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setAction("/TableEdtAction.do");
              // /userdb/table_edt.jsp(84,0) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setMethod("POST");
              int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
              if (_jspx_eval_html_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n<input type=hidden name=pager.offset value=\"");
                  out.print(request.getParameter("pager.offset"));
                  out.write("\">\r\n<input type=hidden name=\"fullName\" value=\"");
                  out.print(fullName);
                  out.write("\">\r\n<input type=\"hidden\" name=\"tableid\" value=\"");
                  out.print(request.getParameter("tableid") );
                  out.write("\">\r\n<tr><td>\r\n\t<table width=\"100%\">\r\n\t<tr style=\"display:none\">\r\n\t\t<td width=\"80\" nowrap>??????<label class=\"mustFillcolor\">*</label>???</td>\r\n\t\t<td width=\"60%\">\r\n\t\t\t<input name=\"tablecode\" type=\"text\" class=\"inputText\" style=\"width:100%;\" value=\"");
                  out.print(code);
                  out.write("\"/></td>\r\n\t\t<td ></td>\r\n\t</tr>\r\n\r\n\t<tr>\r\n\t\t<td width=\"100\" nowrap>???&nbsp;???&nbsp;??????</td>\r\n\t\t<td>\r\n\t\t\t\t\t\t\t<input name=\"tabledesname\" type=\"text\" class=\"inputText\" style=\"width:90%;\" value=\"");
                  out.print(desname);
                  out.write("\"/><label class=\"mustFillcolor\" style=\"padding-left:2px;\">*</label></td>\r\n\t\t<td></td>\r\n\t</tr>\r\n\t<tr>\r\n\t\t<td>???????????????</td>\r\n\t\t<td><input name=\"tablename2\" type=\"hidden\" class=\"inputText\" style=\"width:20%;\" value=\"");
                  out.print(sysmark);
                  out.write("\" readonly/><input name=\"tablename\" type=\"text\" class=\"inputText\" style=\"width:90%;\" value=\"");
                  out.print(name);
                  out.write("\" readonly/><label class=\"mustFillcolor\" style=\"padding-left:2px;\">*</label></td>\r\n\t\t<td>&nbsp;</td>\r\n\t</tr>\r\n\t<tr style=\"display:none\">\r\n\t\t<td>???????????????</td>\r\n\t\t<td>\r\n\t\t\t\t\t\t\t<select name=\"tablemodel\" style=\"width:90%;\">\r\n\t\t\t\t\t\t\t\t");

									if(model!=null && model.length>0){
										for(int i=0;i<model.length;i++){
											out.print("<option value="+model[i][0]);
											if(model[i][0]!=null && model[i][0].equals(tmodel))
												out.print(" selected");
											out.print(">"+model[i][1]+"</option>");
										}
									}
								
                  out.write("\r\n\t\t\t\t\t\t\t</select></td>\r\n\t\t<td >&nbsp;</td>\r\n\t</tr>\r\n\t</table>\r\n\t\t\t\t\t\t  <input name=\"tablefilepath\" type=\"hidden\" value=\" \"/>\r\n\t<TABLE height=\"15\" border=0>\r\n\t<TR>\r\n\t\t<TD></TD>\r\n\t</TR>\r\n\t</TABLE>\r\n\t<table>\r\n\t\t<tr>\r\n\t      <td colspan=3>\r\n\t\t    <input type=\"button\"  class=\"btnButton4font\" onclick=\"javascript:save();\" value=\"????????????\"/><button class=\"btnButton2font\" onclick=\"TableEdtActionForm.reset();\">??????</button><button class=\"btnButton2font\" onclick=\"javascript:window.close()\">??????</button>\r\n\t\t  </td>\r\n        </tr>\r\n\t</table>\r\n\t</td></tr>\r\n");
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
            out.write("\r\n</table>\r\n</td></tr></table>\r\n</body>\r\n<script language=\"javascript\">\r\n\tfunction save(){\r\n\t\tif(checkmsg()){\r\n\t\t\tTableEdtActionForm.submit();\r\n\t\t}\r\n\t}\r\n\r\n\tfunction checkmsg(){\r\n\t  if (TableEdtActionForm.tablecode.value == \"\"){\r\n\t\talert(\"??????????????????????????????????????????\");\r\n\t\tTableEdtActionForm.tablecode.focus();\r\n\t\treturn (false);\r\n\t  }\r\n          if (TableEdtActionForm.tablecode.value.replace(/[^\\x00-\\xff]/g,\"**\").length>10){\r\n\t\talert(\"?????????????????????????????????????????????5?????????10??????????????????\");\r\n\t\tTableEdtActionForm.tablecode.focus();\r\n\t\treturn (false);\r\n\t  }\r\n\t  if (TableEdtActionForm.tabledesname.value == \"\"){\r\n\t\talert(\"?????????????????????????????????????????????\");\r\n\t\tTableEdtActionForm.tabledesname.focus();\r\n\t\treturn (false);\r\n\t  }\r\n          if (TableEdtActionForm.tabledesname.value.replace(/[^\\x00-\\xff]/g,\"**\").length>20){\r\n\t\talert(\"?????????????????????????????????????????????10?????????20??????????????????\");\r\n\t\tTableEdtActionForm.tabledesname.focus();\r\n\t\treturn (false);\r\n\t  }\r\n\t  \r\n\t  if (TableEdtActionForm.tablefilepath.value == \"\"){\r\n\t\talert(\"?????????????????? ?????????????????????????????????\");\r\n\t\tTableEdtActionForm.tablefilepath.focus();\r\n\t\treturn (false);\r\n");
            out.write("\t  }\r\n\t  return true;\r\n\t}\r\n</script>\r\n\r\n");
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
}
