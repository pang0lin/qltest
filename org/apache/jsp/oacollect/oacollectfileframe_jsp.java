/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:03:45 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.oacollect;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class oacollectfileframe_jsp extends org.apache.jasper.runtime.HttpJspBase
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
            out.write("\r\n<head>\r\n<META http-equiv=\"Content-Type\" content=\"text/html; charset=gbk\">\r\n<meta http-equiv=\"pragma\" content=\"no-cache\">\r\n<meta http-equiv=\"cache-control\" content=\"no-cache\">\r\n<meta http-equiv=\"expires\" content=\"0\">   \r\n<link href=\"/jsoa/skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject1.css\">\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DateObject.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DatePicker.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/editlib.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/tree.js\"></SCRIPT>\r\n<script language=\"javascript\" src=\"/jsoa/js/checkForm.js\"></script>\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n<script language=\"javascript\" src=\"/jsoa/js/ajax.js\"></script>\r\n<title>????????????</title>\r\n</head>\r\n");

List myList=(List)request.getAttribute("myList");
int index=0;
if(request.getAttribute("pager.offset")!=null){
    index=Integer.parseInt(request.getAttribute("pager.offset").toString());
}else if(request.getParameter("pager.offset")!=null){
    index=Integer.parseInt(request.getParameter("pager.offset"));
}
int offset1=index;
String collectEmpName=null==request.getParameter("collectEmpName")?"":request.getParameter("collectEmpName");
String collectOrgName=null==request.getParameter("collectOrgName")?"":request.getParameter("collectOrgName");
String displayFlag=null==request.getParameter("displayFlag")?"":request.getParameter("displayFlag");
String searchTime=request.getParameter("searchTime");
String oprStartTime=null;
String oprEndTime=null;
if("1".equals(searchTime)){
	oprStartTime=request.getParameter("oprStartTime");
	oprEndTime=request.getParameter("oprEndTime");
}
if(request.getAttribute("pager.offset")!=null){
    index=Integer.parseInt(request.getAttribute("pager.offset").toString());
}else if(request.getParameter("pager.offset")!=null){
    index=Integer.parseInt(request.getParameter("pager.offset"));
}
String fromFlag=request.getParameter("fromFlag");

            out.write("\r\n<body class=\"MainFrameBox\">\r\n<input type=\"hidden\" name=\"multiSelectItem\" id=\"multiSelectItem\" value=\"\" />\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td>\r\n<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"1\" cellspacing=\"1\" class=\"searchbar\" id=\"searchbar\" style=\"display:none\">\r\n   <form name=\"frm\" action=\"/jsoa/OACollectFileAction.do?action=fileList&collectId=");
            out.print(request.getAttribute("collectId"));
            out.write("\" method=\"post\">\r\n\t  <tr>\r\n\t\t  <td align=\"center\">????????????</td>\r\n\t\t  <td>\r\n\t\t\t<input type=\"text\" class=\"inputtext\" name=\"collectEmpName\" value=\"");
            out.print(collectEmpName);
            out.write("\"/>\r\n\t\t\t<input type=\"hidden\" name=\"displayFlag\" id=\"displayFlag\" value=\"");
            out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${param.displayFlag}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
            out.write("\">\r\n\t\t\t</td>\r\n\t\t  <td align=\"left\">???????????????<input type=\"text\" class=\"inputtext\" name=\"collectOrgName\" value=\"");
            out.print(collectOrgName);
            out.write("\"/></td>\r\n\t\t  <td>\r\n\t\t\t&nbsp;\r\n\t\t  </td>\r\n\t\t  </tr>\r\n\t\t  <tr>\r\n\t\t  <td align=\"center\">?????????????????????</td>\r\n\t\t  <td ><script language=javascript>\r\n\t\t\t\t\tvar dtpDate = createDatePickerByDateStr(\"oprStartTime\",\"");
            out.print(oprStartTime);
            out.write("\");\r\n\t\t\t\t  </script>\r\n\t\t  </td>\r\n\t\t  <td>\r\n\t\t  \t?????????????????????\r\n\t\t\t<script language=javascript>\r\n\t\t\t\t\tvar dtpDate = createDatePickerByDateStr(\"oprEndTime\",\"");
            out.print(oprEndTime);
            out.write("\");\r\n\t\t\t</script>\r\n\t\t\t<input type=\"checkbox\" id=\"searchTime\" name=\"searchTime\" value=\"1\" ");
if("1".equals(searchTime)){out.print("checked");}
            out.write(">\r\n\t\t  </td>\r\n\t\t  <td  align=\"right\">\r\n\t\t  <input type=\"button\" class=\"btnButton2Font\" onClick=\"searchSubmit();\" value=\"??????\"/>\r\n\t\t  <input type=\"button\" class=\"btnButton2Font\" onClick=\"searchReset();\" value=\"??????\"/>\r\n\t\t  </td>\r\n\t </tr>\r\n   </form>\r\n</table>\r\n<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"listTable\" style=\"BORDER-RIGHT:none;BORDER-Left:none;BORDER-BOTTOM:none;\">\r\n<tr><td width=\"20%\"><iframe src=\"/jsoa/oacollect/oacollectfiletree.jsp\" id=\"fileTreeFrame\" name=\"fileTreeFrame\" \r\n frameborder=no  border=0  marginwidth=0  marginheight=0 scrolling=\"no\" width=\"95%\" height=\"500px\" ></iframe></td>\r\n<td width=\"80%\"><iframe src=\"/jsoa/OACollectFileAction.do?action=fileList&displayFlag=suoluetu&fromFlag=gerenwendang\" id=\"personfile\" name=\"personfile\" \r\n frameborder=no  border=0  marginwidth=0  marginheight=0  id=\"personinner\" scrolling=\"no\" width=\"99%\" height=\"500px\" ></iframe></td></tr>\r\n</table>\r\n</td>\r\n</tr>\r\n</table>\r\n</body>\r\n<script language=\"javascript\">\r\nfunction MM_openBrWindow(theURL,winName,features) { //v2.0\r\n");
            out.write("  JSMainWinOpen(theURL,winName,features);\r\n}\r\nfunction showSearch(){\r\n  document.getElementById(\"searchbar\").style.display=\"\";\r\n  document.getElementById(\"btnButton2FontShow\").style.display=\"none\";\r\n  document.getElementById(\"btnButton2FontClose\").style.display=\"\";\r\n}\r\nfunction closeSearch(){\r\n  document.getElementById(\"searchbar\").style.display=\"none\";\r\n  document.getElementById(\"btnButton2FontShow\").style.display=\"\";\r\n  document.getElementById(\"btnButton2FontClose\").style.display=\"none\";\r\n}\r\nfunction setEnter(){\r\n if(document.getElementById(\"searchbar\").style.display==\"\"){\r\n  searchSubmit();\r\n }\r\n}\r\nfunction searchSubmit(){\r\n\tdocument.frm.submit();\r\n}\r\n</script>\r\n");
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
      out.write("\r\n\r\n\r\n");
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
