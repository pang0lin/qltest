/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:52:45 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.personnel_005fmanager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class EmpStatistics_jsp extends org.apache.jasper.runtime.HttpJspBase
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
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fhtml.release();
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

	Date now = new Date();
	int year = now.getYear() + 1900;
	int month = now.getMonth() + 1;

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
            out.write("\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/style/cssmain.css\">\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject1.css\">\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DateObject.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DatePicker.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/editlib.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/tree.js\"></SCRIPT>\r\n<title>统计报表</title>\r\n<!--  STYLE\tCHANGE START -->\r\n<link href=\"skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script\tsrc=\"js/js.js\" language=\"javascript\"></script>\r\n<!--  STYLE\tCHANGE START -->\r\n\r\n<script language=\"JavaScript\" type=\"text/JavaScript\">\r\n<!--\r\nfunction MM_openBrWindow(theURL,winName,features) { //v2.0\r\n  JSMainWinOpen(theURL,winName,features);\r\n}\r\ndocument.onkeydown=look;\r\nfunction look(){\r\n\tif(event.keyCode==13){\r\n\t\tsearch();\r\n\t}\r\n}\r\n//-->\r\n</script>\r\n<script language=\"JavaScript\" src=\"js/date0.js\"></script>\r\n</head>\r\n\r\n<body  class=\"MainFrameBox\">\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td>\r\n<!-- SEARCH\tPART -->\r\n<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"1\" cellspacing=\"1\" class=\"searchbar\">\r\n\t<form action=\"/jsoa/EmpStatisticsAction.do\" name=\"form1\" method=\"post\" target=\"_blank\">\r\n\t\t<tr>\r\n\t\t\t<td width=\"30%\" nowrap=\"nowrap\">\r\n\t\t\t\t&nbsp;&nbsp;&nbsp;&nbsp;统计年份：\r\n\t\t\t\t<select name=\"year\" id=\"year\" style=\"width:180px\">\r\n\t\t\t\t");

					for(int i=year-10; i<=year+10; i++){
				
            out.write("\r\n\t\t\t\t\t<option value=\"");
            out.print(i);
            out.write('"');
            out.write(' ');
            out.print(i==year?"selected":"");
            out.write('>');
            out.print(i);
            out.write("</option>\r\n\t\t\t\t");
}
            out.write("\r\n\t\t\t\t</select>\r\n\t\t\t</td>\r\n\t\t\t<td width=\"30%\" nowrap=\"nowrap\">\r\n\t\t\t\t统计月份：&nbsp;&nbsp;\r\n\t\t\t\t<select name=\"month\" id=\"month\" style=\"width:180px\">\r\n\t\t\t\t");

					for(int i=0; i<=12; i++){
					if(i==0){
				
            out.write("\r\n\t\t\t\t\t<option value=\"");
            out.print(i<10?"0"+i:""+i);
            out.write('"');
            out.write(' ');
            out.print(i==month?"selected":"");
            out.write(">--请选择--</option>\r\n\t\t\t\t");
		
					}else{
				
            out.write("\r\n\t\t\t\t\t<option value=\"");
            out.print(i<10?"0"+i:""+i);
            out.write('"');
            out.write(' ');
            out.print(i==month?"selected":"");
            out.write('>');
            out.print(i);
            out.write("月</option>\r\n\t\t\t\t");
}
				}
            out.write("\r\n\t\t\t\t</select>\r\n\t\t\t</td>\r\n\t\t\t<td nowrap=\"nowrap\">\r\n\t\t\t\t");
            out.write("\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\r\n\t\t<tr>\r\n\t\t\t<td nowrap=\"nowrap\">\r\n\t\t\t\t&nbsp;&nbsp;&nbsp;&nbsp;报表类型：\r\n\t\t\t\t<select name=\"statisticsType\" id=\"statisticsType\" style=\"width:180px\">\r\n\t \t\t\t\t<option value=\"listEmpChange\">人员异动表</option>\r\n\t\t\t\t\t<option value=\"listEmpStruct\">人员结构分析</option>\r\n\t\t\t\t\t<option value=\"listEmpCizhi\">离职名单报表</option>\r\n\t\t\t\t\t<option value=\"listEmpZhuanzheng\">员工转正报表</option>\r\n\t\t\t\t</select>\r\n\t\t\t</td>\r\n\t\t\t<td nowrap=\"nowrap\">\r\n\t\t\t</td>\r\n\t\t\t<td align=\"right\">\r\n\t\t\t<input type=\"button\" class=\"btnButton2Font\" onclick=\"search();\" style=\"cursor:pointer\" value=\"查询\" />\r\n\t\t\t<input type=\"button\" class=\"btnButton2Font\" onclick=\"clearr();\" style=\"cursor:pointer\" value=\"重置\" />\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\t</form>\r\n</table>\r\n<!-- SEARCH\tPART -->\r\n\r\n<!-- MIDDLE\tBUTTONS\t-->\r\n\r\n<!-- MIDDLE\tBUTTONS\t-->\r\n\r\n<!-- LIST TITLE PART -->\r\n\r\n<!-- LIST TITLE PART -->\r\n\r\n<!-- PAGER -->\r\n\r\n<!-- PAGER -->\r\n</td>\r\n</tr>\r\n</table>\r\n\r\n</body>\r\n");
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
      out.write("\r\n<script language=\"javascript\">\r\n<!--\r\n\r\nfunction search(){\r\n\tvar yearMonth = document.getElementById(\"year\").value + \"-\" + document.getElementById(\"month\").value;\r\n\tvar url = \"/jsoa/EmpStatisticsAction.do?action=\"+document.getElementById(\"statisticsType\").value+\"&yearMonth=\"+yearMonth;\r\n\tJSMainWinOpen(url,\"\",\"\");\r\n\t//document.form1.action=url;\r\n\t//document.form1.submit();\r\n}\r\n\r\nfunction clearr(){\r\n\tlocation.href=\"/jsoa/EmpStatisticsAction.do?action=statistics\";\r\n}\r\n\r\nfunction legalCharacters(o) {\r\n\t//参数'o'是页面上的一个对象，如'document.forms[0].code'\r\n\t//var cnst =\"!\\\"#$%&'()=`|~{+*}<>?_-^\\\\@[;:],./\";\r\n\t//var cnst =\"\\\\\\\"#$%&'()=`|~{+*}<>?_-^\\\\@[]\\.,;:!/\";\r\n\tvar cnst =\"\\\\\\\"'`<>^/\";\r\n\r\n    for (i=0;i<o.value.length;i++){\r\n       \tif (cnst.indexOf(o.value.charAt(i))>-1){\r\n\t\t\treturn true;\r\n        }\r\n    }\r\n    return false;\r\n}\r\n//-->\r\n</script>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
