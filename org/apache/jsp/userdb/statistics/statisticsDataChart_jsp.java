/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:43:12 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.userdb.statistics;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class statisticsDataChart_jsp extends org.apache.jasper.runtime.HttpJspBase
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

String fileName= (String)request.getAttribute("fileName");
String[][] queryFields = (String[][])request.getAttribute("queryFields");

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
            out.write("\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/style/cssmain.css\">\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject1.css\">\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DateObject.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DatePicker.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/editlib.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/tree.js\"></SCRIPT>\r\n<title>时间分布</title>\r\n<!--  STYLE\tCHANGE START -->\r\n<link href=\"skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script\tsrc=\"js/js.js\" language=\"javascript\"></script>\r\n<!--  STYLE\tCHANGE START -->\r\n<style><!--\r\n.head\r\n\t{font-size:20.0pt;\r\n\tfont-weight:700;\r\n\ttext-align:center;}\r\n-->\r\n</style>\r\n<script language=\"JavaScript\" type=\"text/JavaScript\">\r\n<!--\r\nfunction MM_openBrWindow(theURL,winName,features) { //v2.0\r\n  JSMainWinOpen(theURL,winName,features);\r\n}\r\ndocument.onkeydown=look;\r\nfunction look(){\r\n\tif(event.keyCode==13){\r\n\t\tsearch();\r\n\t}\r\n}\r\nfunction showData(){\r\n\tJsfStatisticsManageForm.action=\"/jsoa/StatisticsAction.do?action=statisticsData&statsId=");
            out.print(request.getAttribute("statsId"));
            out.write("\"\r\n\t\t\t+\"&statsTableId=");
            out.print(request.getAttribute("statsTableId"));
            out.write("&rightType=");
            out.print(request.getAttribute("rightType"));
            out.write("&menuId=");
            out.print(request.getAttribute("menuId"));
            out.write("\"\r\n\t\t\t+\"&moduleProcessId=");
            out.print(request.getAttribute("moduleProcessId"));
            out.write("&processId=");
            out.print(request.getAttribute("processId"));
            out.write("\"\r\n\t\t\t+\"&processName=");
            out.print(request.getAttribute("processName"));
            out.write("\"+\"&processType=");
            out.print(request.getAttribute("processType"));
            out.write("\"\r\n\t\t\t+\"&isEdit=");
            out.print(request.getAttribute("isEdit"));
            out.write("\"+\"&queryType=");
            out.print(request.getAttribute("queryType"));
            out.write("\"\r\n\t\t\t+\"&processZaiBanMonitorProcessId=");
            out.print(request.getAttribute("processZaiBanMonitorProcessId"));
            out.write("&isCollect=");
            out.print(request.getAttribute("param.isCollect"));
            out.write("\"\r\n\t\t\t+\"&isSub=");
            out.print(request.getParameter("isSub")==null?"0":request.getParameter("isSub") );
            out.write("\";\r\n\tJsfStatisticsManageForm.submit();\r\n}\r\n\r\n//-->\r\n</script>\r\n<script language=\"JavaScript\" src=\"js/date0.js\"></script>\r\n</head>\r\n\r\n<body  class=\"MainFrameBox\"  onload=\"syncValue();\">\r\n");
            //  html:form
            org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag) _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(org.apache.struts.taglib.html.FormTag.class);
            boolean _jspx_th_html_005fform_005f0_reused = false;
            try {
              _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
              _jspx_th_html_005fform_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
              // /userdb/statistics/statisticsDataChart.jsp(62,0) name = action type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setAction("/StatisticsAction.do?action=statisticsData");
              // /userdb/statistics/statisticsDataChart.jsp(62,0) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setMethod("post");
              int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
              if (_jspx_eval_html_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td>\r\n<div style=\"display:none\"><table>");
                  out.print(request.getAttribute("searchPart"));
                  out.write("</table></div>\r\n<table width=\"100%\"\theight=\"25\"\tborder=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\r\n\t<tr>\r\n\t\t<td align=\"right\">\r\n\t\t<button  class=\"btnButton2Font\" onclick=\"showData();\">列表显示</button>\r\n\t\t</td>\r\n\t</tr>\r\n</table>\r\n</td>\r\n<!-- MIDDLE\tBUTTONS\t-->\r\n<br />\r\n</tr>\r\n<tr>\r\n<td>\r\n<!-- SEARCH\tPART -->\r\n\r\n<!-- SEARCH\tPART -->\r\n\r\n<!-- MIDDLE\tBUTTONS\t-->\r\n<!-- MIDDLE\tBUTTONS\t-->\r\n<br />\r\n<!-- LIST TITLE PART -->\r\n<!--table width=\"100%\"  border=\"1\" align=\"center\" cellpadding=\"1\" cellspacing=\"0\" bordercolorlight=\"#000000\" bordercolordark=\"#FFFFFF\"-->\r\n<table width=\"100%\"  border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n\t<tr align=\"center\">\r\n\t\t<td width=\"100%\"><img src=\"");
                  out.print( request.getContextPath() + "/DisplayChart?filename=" +fileName);
                  out.write("\" width=\"");
                  out.print(request.getAttribute("w"));
                  out.write("\" height=\"");
                  out.print(request.getAttribute("h"));
                  out.write("\" border=0 usemap=\"#");
                  out.print(fileName);
                  out.write("\"></td>\r\n\t</tr>\r\n</table>\r\n<!-- LIST TITLE PART -->\r\n<br>\r\n<div align=\"center\">注：图示中显示为当前用户所有的“数据权限”所看到的内容。</div>\r\n</td>\r\n</tr>\r\n</table>\r\n");
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
            out.write("\r\n</body>\r\n");
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
      out.write("\r\n<script language=\"javascript\">\r\nfunction syncValue() {\r\n\t");

	    	if (queryFields != null && queryFields.length > 0) {
	        	for (int i = 0; i < queryFields.length; i++) {
	
      out.write("\r\n\t                            if (document.getElementById('");
      out.print(queryFields[i][2]+"_type");
      out.write("').value == \"date\" ||\r\n\t                                document.getElementById('");
      out.print(queryFields[i][2]+"_type");
      out.write("').value == \"time\" ||\r\n\t                                document.getElementById('");
      out.print(queryFields[i][2]+"_type");
      out.write("').value == \"datetime\") {\r\n\t                                    if (document.getElementById('");
      out.print(queryFields[i][2]+"_start_hid");
      out.write("') &&\r\n\t                                        document.getElementById('");
      out.print(queryFields[i][2]+"_start_hid");
      out.write("').value != '' &&\r\n\t                                        document.getElementById('");
      out.print(queryFields[i][2]+"_start_hid");
      out.write("').value != 'null' &&\r\n\t                                        document.getElementById('");
      out.print(queryFields[i][2]+"_end_hid");
      out.write("') &&\r\n\t                                        document.getElementById('");
      out.print(queryFields[i][2]+"_end_hid");
      out.write("').value != '' &&\r\n\t                                        document.getElementById('");
      out.print(queryFields[i][2]+"_end_hid");
      out.write("').value != 'null') {\r\n\t                                    \t\tdocument.getElementById('");
      out.print(queryFields[i][2]+"_start");
      out.write("').value =\r\n\t                                    \t\tdocument.getElementById('");
      out.print(queryFields[i][2]+"_start_hid");
      out.write("').value;\r\n\t                                    \t\tdocument.getElementById('");
      out.print(queryFields[i][2]+"_end");
      out.write("').value =\r\n\t                                    \t\tdocument.getElementById('");
      out.print(queryFields[i][2]+"_end_hid");
      out.write("').value;\r\n\t                                               \r\n\t                                                if (document.getElementById('");
      out.print(queryFields[i][2]+"dateHid");
      out.write("').value == '1') {                                                   \r\n\t                                                    document.getElementById('");
      out.print(queryFields[i][2]+"date");
      out.write("').checked = true;\r\n\t                                                }\r\n\t                                    }\r\n\t                            } else {\r\n\t                                if (\"checkbox\" == document.getElementById('");
      out.print(queryFields[i][2]);
      out.write("_type').value) {\r\n\t                                        var raHIdVal = document.getElementById('");
      out.print(queryFields[i][2]);
      out.write("_hid').value;\r\n\t                                    \r\n\t                                        var radios = document.getElementById('");
      out.print(queryFields[i][2]);
      out.write("');\r\n\t                                        if (raHIdVal == '0') {\r\n\t\t\t\t\t\t    \t\t\t\t\tradios.checked = true;\r\n\t                                        }\r\n\r\n\t                                } else if (\"radiovarchar\" == document.getElementById('");
      out.print(queryFields[i][2]);
      out.write("_type').value ||\r\n\t                                           \"radionumber\" == document.getElementById('");
      out.print(queryFields[i][2]);
      out.write("_type').value) {\r\n\t                                        \r\n\t                                        var raHIdVal = document.getElementById('");
      out.print(queryFields[i][2]);
      out.write("_hid').value;\r\n\t                                        \r\n\t                                        var radios = document.getElementsByName('");
      out.print(queryFields[i][2]);
      out.write("');\r\n\t                                        if (radios) {\r\n\t                                            \r\n\t                                            for (var i = 0; i < radios.length; i++) {                                               \r\n\t                                                if(radios[i].value == raHIdVal) {\r\n\t                                                \tradios[i].checked = true;\r\n\t                                                        break;\r\n\t                                                }\r\n\t                                            }\r\n\t                                        }\r\n\t                                } else if (\"selectnumber\" == document.getElementById('");
      out.print(queryFields[i][2]);
      out.write("_type').value ||\r\n\t                                           \"selectvarchar\" == document.getElementById('");
      out.print(queryFields[i][2]);
      out.write("_type').value) {\r\n\t                                   \r\n\t                                    var raHIdVal = document.getElementById('");
      out.print(queryFields[i][2]);
      out.write("_hid').value;\r\n\t                                    \r\n\t                                    var sel = document.getElementById('");
      out.print(queryFields[i][2]);
      out.write("');                                   \r\n\t                                    if (sel.options.length) {\r\n\t                                        for (var i = 0; i < sel.options.length; i++) {                                            \r\n\t                                            if (sel.options[i].value == raHIdVal) {\r\n\t                                                sel.options[i].selected = true;\r\n\t                                                break;\r\n\t                                            }\r\n\t                                        }\r\n\t                                    }\r\n\t                                } else {\r\n\t                                    if (document.getElementById('");
      out.print(queryFields[i][2]+"_hid");
      out.write("').value != 'null'){\r\n\t                   \t\t\t\t\tdocument.getElementById('");
      out.print(queryFields[i][2]);
      out.write("').value =\r\n\t                         \t\t\tdocument.getElementById('");
      out.print(queryFields[i][2]+"_hid");
      out.write("').value;\r\n\t                                    }\r\n\t                                }\r\n\t                            \r\n\t\t\t\t}\r\n\t");

	    		}
	        }
	
      out.write("\r\n\t}\r\n</script>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
