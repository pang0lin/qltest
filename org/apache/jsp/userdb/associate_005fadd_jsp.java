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

public final class associate_005fadd_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
      //  html:html
      org.apache.struts.taglib.html.HtmlTag _jspx_th_html_005fhtml_005f0 = (org.apache.struts.taglib.html.HtmlTag) _005fjspx_005ftagPool_005fhtml_005fhtml.get(org.apache.struts.taglib.html.HtmlTag.class);
      boolean _jspx_th_html_005fhtml_005f0_reused = false;
      try {
        _jspx_th_html_005fhtml_005f0.setPageContext(_jspx_page_context);
        _jspx_th_html_005fhtml_005f0.setParent(null);
        int _jspx_eval_html_005fhtml_005f0 = _jspx_th_html_005fhtml_005f0.doStartTag();
        if (_jspx_eval_html_005fhtml_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n<!--\r\nopener.window.location.href=((window.location.href).substring(0,(window.location.href).indexOf(\"/jsoa\")))+\"/jsoa/AssociateAction.do?operate=list&tableid=");
            out.print(request.getParameter("tableid"));
            out.write("&tablename=");
            out.print(request.getParameter("tablename"));
            out.write("&shua=1\";\r\n//-->\r\n</SCRIPT>\r\n");

if(request.getParameter("close") != null){

            out.write("\r\n<script language=\"javascript\">\r\n\twindow.close();    \r\n\t//opener.window.location.reload();\r\n</script>\r\n");

}else{
            out.write("\r\n\r\n<head>\r\n\r\n<title>?????????</title>\r\n<link href=\"/jsoa/skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject2.css\">\r\n\r\n");

	String tid = request.getParameter("tableid");
	String tname = request.getParameter("tablename");

	Object obj = session.getAttribute("domainId");
	if(obj==null || obj.equals(""))
		obj="0";

	Object[][] mainFieldList = null;
	Object temp = request.getAttribute("mainFieldList");
	if(temp !=null)
		mainFieldList = (Object[][])temp;
	
	Object[][] pryTableList = null;
	temp = request.getAttribute("pryTableList");
	if(temp !=null)
		pryTableList = (Object[][])temp;
	
	Object[][] pryFieldList = null;
	temp = request.getAttribute("pryFieldList");
	if(temp !=null)
		pryFieldList = (Object[][])temp;
	

            out.write("\r\n</head>\r\n<body leftmargin=\"0\" topmargin=\"0\" class=\"MainFrameBox Pupwin\" onload=\"resizeWin(450,250);\"  >\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td>\r\n");

	if(request.getParameter("state") != null)
		out.print("<script>alert(\"?????????????????????\");</script>");

            out.write('\r');
            out.write('\n');
            //  html:form
            org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag) _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(org.apache.struts.taglib.html.FormTag.class);
            boolean _jspx_th_html_005fform_005f0_reused = false;
            try {
              _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
              _jspx_th_html_005fform_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
              // /userdb/associate_add.jsp(65,0) name = action type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setAction("/AssociateAddAction.do");
              // /userdb/associate_add.jsp(65,0) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setMethod("POST");
              int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
              if (_jspx_eval_html_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\r\n <input type=\"hidden\" name=\"user\" value=\"");
                  out.print(session.getAttribute("userName"));
                  out.write("\"/>\r\n <input type=\"hidden\" name=\"operate\">\r\n <input type=\"hidden\" name=\"tableid\" value=");
                  out.print(tid);
                  out.write(">\r\n <input type=\"hidden\" name=\"tablename\" value=");
                  out.print(tname);
                  out.write(">\r\n <input type=\"hidden\" name=\"limitpryfield\">\r\n <input type=\"hidden\" name=\"domainid\" value=\"");
                  out.print(session.getAttribute("domainId"));
                  out.write("\"/>\r\n\r\n\r\n<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"docBoxNoPanel\"> \r\n<tr>\r\n<td valign=\"top\">\r\n<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\">\r\n                          <tr style=\"display:none\">\r\n                            <td width=\"75\" nowrap=\"nowrap\">???????????????</td>\r\n                            <td>\r\n\t\t\t\t\t\t\t<select name=\"limitfield\" style=\"width:250\">\r\n\t\t\t\t\t\t\t");
if(mainFieldList!=null){
								for(int i=0;i<mainFieldList.length;i++){
									out.print("<option value=\""+mainFieldList[i][0]+"\">"+mainFieldList[i][1]+"</option>");
								}
							}								  
							
                  out.write("\r\n\t\t\t\t\t\t\t</select>\r\n                            </td>\r\n                          </tr>\r\n\t\t\t\t\t\t  <tr>\r\n                            <td>???????????????</td>\r\n                            <td>\r\n\t\t\t\t\t\t\t<input name=\"name\" type=\"text\" class=\"inputText\" style=\"width:90%\" onkeydown=\"javascript:if(event.keyCode==13) return false;\" value=\"");
                  out.print(request.getParameter("name")==null?"":request.getParameter("name"));
                  out.write("\"/> <label class=\"mustFillcolor\">*</label>\r\n\t\t\t\t\t\t\t</select>\r\n                            </td>\r\n                          </tr>\r\n\t\t\t\t\t\t  <tr>\r\n                            <td>??????????????????</td>\r\n                            <td>\r\n\t\t\t\t\t\t\t<select name=\"limitprytable\" type=\"text\" onchange=\"update()\" style=\"width:250\">\r\n\t\t\t\t\t\t\t");
if(pryTableList!=null){
								for(int i=0;i<pryTableList.length;i++){
									if(tid.equals(pryTableList[i][0])) continue;
									out.print("<option value=\""+pryTableList[i][0]+"\">"+pryTableList[i][1]+"</option>");
								}
							}								  
							
                  out.write("\r\n\t\t\t\t\t\t\t</select>\r\n                            </td>\r\n                          </tr>\r\n\t\t\t\t\t\t \r\n\t\t\t\t\t\t  <tr>\r\n                            <td>???????????????</td>\r\n                            <td>\r\n\t\t\t\t\t\t\t");

							if(pryTableList!=null){
								int index = 0;
								for(int i=0;i<pryTableList.length;i++){
									Object tbid=pryTableList[i][0];
									// ??????????????????????????????
									if(tid.equals(tbid.toString())) continue;
									
									if(index == 0){	// ???????????????????????????????????????
										out.print("<select name=\"fieldSelected\" onchange=\"setvalue(this)\" style=\"width:250\">");
									} else{
										out.print("<select name=\"fieldSelected\" style=\"display:none;\" onchange=\"setvalue(this)\" style=\"width:250\">");
									}

									// ??????????????????
									if(pryFieldList!=null){
										for(int j=0; j<pryFieldList.length; j++){
											if(pryFieldList[j][0]!=null && pryFieldList[j][0].equals(tbid)){
												out.print("<option value=\""+pryFieldList[j][1]+"\">"+pryFieldList[j][2]+"</option>");
											}
										}
									}
									out.print("</select>");
									index++;
								}
							}						
							
                  out.write("\r\n\t\t\t\t\t\t\t<script>\r\n\t\t\t\t\t\t\tdocument.all.limitpryfield.value=document.getElementsByName(\"fieldSelected\")[0].value;\r\n\t\t\t\t\t\t\t</script>\r\n\t\t\t\t\t\t\t\r\n                            </td>\r\n                          </tr>\r\n\t</table>\r\n\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n\t<tr>\r\n\t      <td colspan=2>\r\n\t\t    <input type=\"button\"  class=\"btnButton4font\" onclick=\"javascript:save('quit');\" value=\"????????????\"/>\r\n\t\t    <input type=\"button\"  class=\"btnButton4font\" onclick=\"javascript:save('continue');\" value=\"????????????\"/>\r\n\t\t    <button class=\"btnButton2font\" onclick=\"window.location.reload();\">??????</button>\r\n\t\t    <button class=\"btnButton2font\" onclick=\"javascript:window.close()\">??????</button>\r\n\t\t  </td>\r\n        </tr>                        \r\n</table>\r\n");
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
            out.write("\r\n</td>\r\n</tr>\r\n</table>\r\n</body>\r\n<script language=\"javascript\">\r\n\tfunction save(tag){\r\n\t\tAssociateAddActionForm.operate.value=tag;\r\n\t\tif(document.all.name.value==\"\"){\r\n\t\t\talert(\"???????????????????????????????????????????????????\");\r\n\t\t\tdocument.all.name.focus();\r\n\t\t\treturn;\r\n\t\t}\r\n\t\tif(document.all.limitfield.value==\"\"){\r\n\t\t\talert(\"?????????????????????????????????????????????\");\r\n\t\t\twindow.close();\r\n\t\t\treturn;\r\n\t\t}\r\n\t\tif(document.all.limitpryfield.value==\"\"){\r\n\t\t\talert(\"???????????????????????????????????????????????????\");\r\n\t\t\twindow.close();\r\n\t\t\treturn;\r\n\t\t}\r\n\t\telse{\r\n\t\t\tAssociateAddActionForm.action+=\"?assname=\"+document.all.name.value;\r\n\t\t\tAssociateAddActionForm.submit();\r\n\t\t}\r\n\t}\r\n\r\n\t\r\n\r\n\tfunction update(){\r\n\t\tvar idx = document.all.limitprytable.selectedIndex;\r\n\t\tvar sels = document.getElementsByName(\"fieldSelected\");\r\n\t\tfor(var i=0; i<sels.length; i++){\r\n\t\t\tif(i == idx){\r\n\t\t\t\tsels[i].style.display = \"\";\r\n\t\t\t\tdocument.getElementsByName(\"limitpryfield\")[0].value=sels[i].options[0].value;\r\n\t\t\t} else{\r\n\t\t\t\tsels[i].style.display = \"none\";\r\n\t\t\t}\r\n\t\t}\r\n\t\tdocument.getElementsByName(\"fieldSelected\")[idx].style.display = \"\";\r\n");
            out.write("\t}\r\n\r\n\t\tfunction setvalue(obj){\r\n\t\t\tdocument.all.limitpryfield.value=obj.value;\r\n\t\t}\t\r\n</script>\r\n\r\n");
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
