/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:50:51 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.setup;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.regex.Pattern;
import com.js.oa.relproject.bean.*;
import com.js.lang.Resource;
import java.util.*;
import java.text.*;
import com.js.util.util.BrowserJudge;

public final class systemmanager_005fuser_005fadjustorg_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("java.text");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("com.js.oa.relproject.bean");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.lang.Resource");
    _jspx_imports_classes.add("java.util.regex.Pattern");
    _jspx_imports_classes.add("com.js.util.util.BrowserJudge");
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
String url="/UserAction.do?action=adjustorg";

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
            out.write("\r\n<head>\r\n<title>??????????????????</title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<link href=\"/jsoa/skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script language=\"javascript\" src=\"js/checkForm.js\"></script>\r\n<SCRIPT language=\"javascript\" src=\"/jsoa/js/openEndow.js\"></SCRIPT>\r\n<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n</head>\r\n<body  class=\"MainFrameBox Pupwin\" onLoad=\"resizeWin(650,320);\">\r\n");
            //  html:form
            org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag) _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(org.apache.struts.taglib.html.FormTag.class);
            boolean _jspx_th_html_005fform_005f0_reused = false;
            try {
              _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
              _jspx_th_html_005fform_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
              // /setup/systemmanager_user_adjustorg.jsp(31,0) name = action type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setAction(url);
              // /setup/systemmanager_user_adjustorg.jsp(31,0) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setMethod("post");
              int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
              if (_jspx_eval_html_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n \r\n<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"docBoxNoPanel\">\r\n<tr>\r\n<td style=\"width:10%\">???????????????</td>\r\n<td   colspan=\"3\"  >\r\n<textarea  class=\"inputTextarea\" title=\"???????????????\" cols=\"65\" rows=\"2\" Id=\"adjustEmpsName\" name=\"adjustEmpsName\" readonly=\"true\" style=\"width:90%;cursor:pointer\" \r\nonclick=\"javascript:openLimitedEndow('adjustEmps','adjustEmpsName',document.getElementById('adjustEmps').value,document.getElementById('adjustEmpsName').value,'user','no','usergroup','");
                  out.print(request.getAttribute("managerScope"));
                  out.write("','yes');\"></textarea>\r\n<input type=\"hidden\" id=\"adjustEmps\" name=\"adjustEmps\" value=\"\" />\r\n</td>\r\n</tr>\r\n<tr>\r\n<td style=\"width:10%\">??????????????????</td>\r\n<td   colspan=\"3\">\r\n<textarea class=\"inputtext\" maxlength=\"3000\"  id=\"adjustOrgName\" name=\"adjustOrgName\" style=\"width:90%\" title=\"???????????????\" readonly=\"true\" \r\nonclick=\"javascript:openLimitedEndow('adjustOrg','adjustOrgName',document.getElementById('adjustOrg').value,document.getElementById('adjustOrgName').value,'org','yes','org','");
                  out.print(request.getAttribute("managerScope"));
                  out.write("','yes');\">\r\n</textarea>\r\n<input type=\"hidden\" id=\"adjustOrg\" name=\"adjustOrg\" value=\"\"/>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td style=\"width:10%\">???????????????</td>\r\n<td colspan=\"3\">\r\n\t<select style=\"width:150px;\" name=\"stationId\" id=\"stationId\">\r\n\t<option value=\"\">--???????????????--</option>\r\n\t");
List listStation = (List)request.getAttribute("listStation");
	for(int i=0;i<listStation.size();i++){
	Object[] obj = (Object[])listStation.get(i); 
                  out.write("\r\n\t<option value=\"");
                  out.print(obj[0].toString() );
                  out.write('"');
                  out.write('>');
                  out.print(obj[1].toString() );
                  out.write("</option>\r\n\t");
} 
                  out.write("\r\n\t</select>\r\n</td>\r\n</tr>\r\n<tr>\r\n<td style=\"width:10%\">???????????????</td>\r\n<td colspan=\"3\">\r\n\t<select style=\"width:150px;\" name=\"dutyId\" id=\"dutyId\">\r\n\t<option value=\"\">--???????????????--</option>\r\n\t");
List listDuty = (List)request.getAttribute("listDuty");
	for(int i=0;i<listDuty.size();i++){
	Object[] obj = (Object[])listDuty.get(i); 
                  out.write("\r\n\t<option value=\"");
                  out.print(obj[1].toString() );
                  out.write('"');
                  out.write('>');
                  out.print(obj[1].toString() );
                  out.write("</option>\r\n\t");
} 
                  out.write("\r\n\t</select>\r\n</td>\r\n</tr>\r\n   </table>\r\n   <table>\r\n   <tr>\r\n    <td >\r\n  \t <input type=\"button\" class=\"btnButton4font\" onClick=\"javascript:saveAndExit();\" value=\"????????????\" />\r\n\t</td>\r\n\t</tr>\r\n   </table>\r\n");
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
            out.write("\r\n</body>\r\n<script language=\"JavaScript\">\r\nvar adjustFlag=\"");
            out.print(request.getAttribute("adjustFlag"));
            out.write("\";\r\nif(\"null\"==adjustFlag){\r\n}else if(\"\"==adjustFlag){\r\n\talert(\"???????????????\");\r\n}else{\r\n\tvar alertContext = adjustFlag.replace(\"1\",\"??????\").replace(\"2\",\"??????\").replace(\"3\",\"??????\");\r\n\talert(alertContext+\"???????????????\");\r\n\twindow.opener.location.reload();\r\n\twindow.close();\r\n}\r\n\r\nfunction checkValue(){\r\n\t var adjustEmps=document.getElementById(\"adjustEmps\");\r\n\t if(null==adjustEmps.value||\"\"==adjustEmps.value){\r\n\t \talert(\"????????????????????????\");\r\n\t \tdocument.getElementById(\"adjustEmpsName\").focus();\r\n\t \treturn false;\r\n\t }\r\n\t var adjustOrg=document.getElementById(\"adjustOrg\");\r\n\t var stationId = document.getElementById(\"stationId\");\r\n\t var dutyId = document.getElementById(\"dutyId\");\r\n\t if(\"\"==adjustOrg.value&&\"\"==stationId.value&&\"\"==dutyId.value){\r\n\t\t alert(\"??????????????????????????????\");\r\n\t\t document.getElementById(\"adjustOrgName\").focus();\r\n\t\t return false;\r\n\t }\r\n\t return true;\r\n}\r\n//??????\r\nfunction saveAndExit(){\r\n\tif(checkValue()){\r\n\t\tvar adjustEmps=document.getElementById(\"adjustEmps\").value;\r\n\t\tvar adjustOrg=document.getElementById(\"adjustOrg\").value;\r\n\t\tUserForm.action+=\"&adjustEmps=\"+adjustEmps+\"&adjustOrg=\"+adjustOrg;\r\n");
            out.write("\t\tUserForm.submit();\r\n\t//window.close();\r\n\t}\r\n\t\r\n}\r\n</script>\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n");
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
