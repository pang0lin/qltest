/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:49:20 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.chart.workflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class flowStatusChart_jsp extends org.apache.jasper.runtime.HttpJspBase
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

String filenamebing= (String)request.getAttribute("filenamebing");
String filenamehengzhu_flowStatusOrgTop10_daiban= (String)request.getAttribute("filenamehengzhu_flowStatusOrgTop10_daiban");
String filenamehengzhu_flowStatusOrgTop10_banjie= (String)request.getAttribute("filenamehengzhu_flowStatusOrgTop10_banjie");
String filenamehengzhu_flowStatusOrgTop10_zaiban= (String)request.getAttribute("filenamehengzhu_flowStatusOrgTop10_zaiban");

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
            out.write("\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/style/cssmain.css\">\r\n<link href=\"/jsoa/relproject/index.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject1.css\">\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DateObject.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DatePicker.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/editlib.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/tree.js\"></SCRIPT>\r\n<title>数据采集-事项状态</title>\r\n<!--  STYLE\tCHANGE START -->\r\n<link href=\"skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script\tsrc=\"js/js.js\" language=\"javascript\"></script>\r\n<!--  STYLE\tCHANGE START -->\r\n<style><!--\r\n.head\r\n\t{font-size:20.0pt;\r\n\tfont-weight:700;\r\n\ttext-align:center;}\r\n-->\r\n</style>\r\n<script language=\"JavaScript\" type=\"text/JavaScript\">\r\n<!--\r\nfunction MM_openBrWindow(theURL,winName,features) { //v2.0\r\n  JSMainWinOpen(theURL,winName,features);\r\n}\r\ndocument.onkeydown=look;\r\nfunction look(){\r\n\tif(event.keyCode==13){\r\n\t\tsearch();\r\n\t}\r\n}\r\nfunction back(){\r\n  window.location.href=((window.location.href).substring(0,(window.location.href).indexOf('/jsoa')))\r\n                   +'/jsoa/chartWorkFlow.do?action=flowStatusDataList&flowStatusType=");
            out.print(request.getAttribute("flowStatusType"));
            out.write("&pager.offset=");
            out.print(request.getAttribute("offset"));
            out.write("';\r\n}\r\n//-->\r\n</script>\r\n<script language=\"JavaScript\" src=\"js/date0.js\"></script>\r\n</head>\r\n\r\n<body  class=\"MainFrameBox\">\r\n\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td>\r\n<table width=\"100%\"\theight=\"25\"\tborder=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\r\n\t<tr height=\"22\">\r\n        <td align=\"center\" class=\"head\" colspan=\"2\">\r\n\t\t\t事项状态分析-图示\r\n\t\t</td>\r\n\t</tr>\r\n\t<tr>\r\n\t\t<td align=\"right\">\r\n\t\t<button  class=\"btnButton2Font\" onclick=\"back();\">返回</button>\r\n\t\t</td>\r\n\t</tr>\r\n</table>\r\n</td>\r\n<!-- MIDDLE\tBUTTONS\t-->\r\n<br />\r\n</tr>\r\n<tr>\r\n<td>\r\n<!-- SEARCH\tPART -->\r\n\r\n<!-- SEARCH\tPART -->\r\n\r\n<!-- MIDDLE\tBUTTONS\t-->\r\n<!-- MIDDLE\tBUTTONS\t-->\r\n<br />\r\n<!-- LIST TITLE PART -->\r\n<!--table width=\"100%\"  border=\"1\" align=\"center\" cellpadding=\"1\" cellspacing=\"0\" bordercolorlight=\"#000000\" bordercolordark=\"#FFFFFF\"-->\r\n<table width=\"100%\"  border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n\t<tr align=\"center\">\r\n\t\t<td>\r\n\t\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"boder_bottom\">\r\n");
            out.write("\t\t\t      <tr>\r\n\t\t\t        <td class=\"table_left\" >&nbsp;</td>\r\n\t\t\t        <td class=\"table_cenr\" >事项状态</td>\r\n\t\t\t        <td class=\"table_right\" >&nbsp;</td>\r\n\t\t\t      </tr>\r\n\t\t\t      <tr>\r\n\t\t\t        <td class=\"boder_left\">&nbsp;</td>\r\n\t\t\t        <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n\t\t\t            <tr >\r\n\t\t\t              <td  align=\"center\" valign=\"middle\">\r\n\t\t\t              <img src=\"");
            out.print( request.getContextPath() + "/DisplayChart?filename=" +filenamebing);
            out.write("\" width=400 height=240 border=0 usemap=\"#");
            out.print(filenamebing);
            out.write("\">\r\n\t\t\t              </td>\r\n\t\t\t            </tr>\r\n\t\t\t        </table></td>\r\n\t\t\t        <td class=\"boder_right\">&nbsp;</td>\r\n\t\t\t      </tr>\r\n\t   \t\t </table>\r\n\t\t</td>\r\n\t\t<td width=\"1%\" >\r\n\t\t</td>\r\n\t\t<td  width=\"49.5%\" >\r\n\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"boder_bottom\">\r\n\t\t\t      <tr>\r\n\t\t\t        <td class=\"table_left\" >&nbsp;</td>\r\n\t\t\t        <td class=\"table_cenr\" >待办前五</td>\r\n\t\t\t        <td class=\"table_right\" >&nbsp;</td>\r\n\t\t\t      </tr>\r\n\t\t\t      <tr>\r\n\t\t\t        <td class=\"boder_left\">&nbsp;</td>\r\n\t\t\t        <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n\t\t\t            <tr >\r\n\t\t\t              <td  align=\"center\" valign=\"middle\">\r\n\t\t\t              <img src=\"");
            out.print( request.getContextPath() + "/DisplayChart?filename=" +filenamehengzhu_flowStatusOrgTop10_daiban);
            out.write("\" width=400 height=240 border=0 usemap=\"#");
            out.print(filenamehengzhu_flowStatusOrgTop10_daiban);
            out.write("\">\r\n\t\t\t              </td>\r\n\t\t\t            </tr>\r\n\t\t\t        </table></td>\r\n\t\t\t        <td class=\"boder_right\">&nbsp;</td>\r\n\t\t\t      </tr>\r\n\t   \t\t </table>\r\n\t\t</td>\r\n\t</tr>\r\n\t\t<tr align=\"center\">\r\n\t\t<td height=\"5\"></td>\r\n\t\t<td></td>\r\n\t</tr>\r\n\t<tr align=\"center\">\r\n\t\t<td  >\r\n\t\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"boder_bottom\">\r\n\t\t\t      <tr>\r\n\t\t\t        <td class=\"table_left\" >&nbsp;</td>\r\n\t\t\t        <td class=\"table_cenr\">办结前五</td>\r\n\t\t\t        <td class=\"table_right\" >&nbsp;</td>\r\n\t\t\t      </tr>\r\n\t\t\t      <tr>\r\n\t\t\t        <td class=\"boder_left\">&nbsp;</td>\r\n\t\t\t        <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n\t\t\t            <tr >\r\n\t\t\t              <td  align=\"center\" valign=\"middle\">\r\n\t\t\t              <img src=\"");
            out.print( request.getContextPath() + "/DisplayChart?filename=" +filenamehengzhu_flowStatusOrgTop10_banjie);
            out.write("\" width=400 height=240 border=0 usemap=\"#");
            out.print(filenamehengzhu_flowStatusOrgTop10_banjie);
            out.write("\">\r\n\t\t\t              </td>\r\n\t\t\t            </tr>\r\n\t\t\t        </table></td>\r\n\t\t\t        <td class=\"boder_right\">&nbsp;</td>\r\n\t\t\t      </tr>\r\n\t   \t\t </table>\r\n\t\t</td>\r\n\t\t<td>\r\n\t\t</td>\r\n\t\t<td >\r\n\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"boder_bottom\">\r\n\t\t\t      <tr>\r\n\t\t\t        <td class=\"table_left\" >&nbsp;</td>\r\n\t\t\t        <td class=\"table_cenr\"  >在办前五</td>\r\n\t\t\t        <td class=\"table_right\" >&nbsp;</td>\r\n\t\t\t      </tr>\r\n\t\t\t      <tr>\r\n\t\t\t        <td class=\"boder_left\">&nbsp;</td>\r\n\t\t\t        <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n\t\t\t            <tr >\r\n\t\t\t              <td  align=\"center\" valign=\"middle\">\r\n\t\t\t              <img src=\"");
            out.print( request.getContextPath() + "/DisplayChart?filename=" +filenamehengzhu_flowStatusOrgTop10_zaiban);
            out.write("\" width=400 height=240 border=0 usemap=\"#");
            out.print(filenamehengzhu_flowStatusOrgTop10_zaiban);
            out.write("\">\r\n\t\t\t              </td>\r\n\t\t\t            </tr>\r\n\t\t\t        </table></td>\r\n\t\t\t        <td class=\"boder_right\">&nbsp;</td>\r\n\t\t\t      </tr>\r\n\t   \t\t </table>\r\n\t\t</td>\r\n\t</tr>\r\n</table>\r\n<!-- LIST TITLE PART -->\r\n<br>\r\n<div align=\"center\">注：图示中显示为当前用户所有的“数据权限”所看到的内容。</div>\r\n</td>\r\n</tr>\r\n</table>\r\n</body>\r\n");
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
      out.write("\r\n<script language=\"javascript\">\r\n</script>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
