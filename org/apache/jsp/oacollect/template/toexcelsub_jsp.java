/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:03:58 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.oacollect.template;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.oa.oacollect.util.CollectUtil;
import java.util.*;

public final class toexcelsub_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.oa.oacollect.util.CollectUtil");
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

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
String collectId = request.getParameter("collectId");
String[] collectInfo = new CollectUtil().collectInfo(collectId);
String processId = collectInfo[1];
String tableId = collectInfo[0];
String[][] formTable = new com.js.oa.eform.service.CustomFormBD().getTableIDAndName(tableId);
com.js.oa.userdb.service.CustomDatabaseBD bd = new com.js.oa.userdb.service.CustomDatabaseBD();
String[][] listFields = bd.getAllField(formTable[0][0]);//??????????????????
String[][] fangan = bd.getFieldFang(formTable[0][0],session.getAttribute("userId").toString(),processId);
String[][] subTable = new com.js.oa.userdb.service.CustomDatabaseBD().getSubTableId(formTable[0][0]);
//String[][] subFangan = bd.getSubFieldFang(formTable[0][0],session.getAttribute("userId").toString(),processId);
String ids = "";
if(request.getAttribute("datas")!=null){
	ids = "-1";
	String[][] datas = (String[][])request.getAttribute("datas");
	for(int i=0;i<datas.length;i++){
		ids += ","+datas[i][0];
	}
}else{
	ids = request.getParameter("ids");
}

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
            out.write("\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\r\n<link href=\"/jsoa/skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script type=\"text/javascript\" src=\"/jsoa/oacollect/template/toexcel.js\"></script>\r\n<script type=\"text/javascript\" src=\"/jsoa/oacollect/template/excelsub.js\"></script>\r\n<title>??????????????????</title>\r\n</head>\r\n\r\n<body >\r\n<div id=\"Layer1\" style=\"position:absolute; width:; height:; z-index:1; left: ; top: ; filter:\">\r\n");
//if(subTable!=null&&subTable.length>0){ 
            out.write("\r\n<form id=\"frm\" name=\"frm\" method=\"post\" action=\"/jsoa/collectDealwithAction.do?action=subExport&tableId=");
            out.print(tableId );
            out.write("&processId=");
            out.print(processId );
            out.write("&collectId=");
            out.print(collectId );
            out.write("\"  >\r\n\t<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"docBoxNoPanel\">\r\n\t<input type=\"hidden\" name=\"fields\" id=\"fields\" value=\"\" />\r\n\t<input type=\"hidden\" name=\"subfields\" id=\"subfields\" value=\"\" />\r\n\t<input type=\"hidden\" name=\"subTable\" id=\"subTable\" value=\"\" />\r\n\t<input type=\"hidden\" name=\"fieldExcel\" id=\"fieldExcel\" value=\"\" />\r\n\t<input type=\"hidden\" name=\"ids\" id=\"ids\" value=\"");
            out.print(ids );
            out.write("\">\r\n\t\r\n\t\t<tr style=\"display:none;\"><td><div style=\"font-size:12px;\">???????????????\r\n\t\t<select id=\"thetitle\" name=\"thetitle\" onchange=\"sel(this,");
            out.print(formTable[0][0] );
            out.write(");\">\r\n\t\t<option value=\"\">--?????????--</option>\r\n\t\t");
if(fangan!=null){for(int i=0;i<fangan.length;i++){ 
            out.write("\r\n\t\t<option value=\"fangan_");
            out.print(fangan[i][0] );
            out.write('"');
            out.write('>');
            out.print(fangan[i][1] );
            out.write("</option>\r\n\t\t");
}} 
            out.write("\r\n\t\t<option value=\"-1\">--?????????--</option>\r\n\t\t</select></td></tr>\r\n\t\t<tr id=\"showTitle\" style=\"display:none;\"><td colspan=4><div style=\"font-size:12px;\">????????????<input type=\"text\" id=\"fangan\" name=\"fangan\" value=\"\" /></div>\r\n\t\t</td></tr>\r\n\t\t<tr>\r\n\t\t\t<td id=\"sourceTd\" width=\"15%\">\r\n\t\t\t<select size=\"10\" id=\"ceSourceSelect\" name=\"ceSourceSelect\" style=\"width:150px;\" multiple=\"multiple\" ondblclick=\"addThisItem('ce')\">\r\n\t\t\t");
for(int i=0;i<listFields.length;i++){ 
            out.write("\r\n\t\t\t<option value=\"");
            out.print(listFields[i][2] );
            out.write('"');
            out.write('>');
            out.print(listFields[i][1] );
            out.write("</option>\r\n\t\t\t");
} 
            out.write("\r\n\t\t\t</select>\r\n\t\t\t</td>\r\n\t\t\t<td width=\"5%\">\r\n\t\t\t<img src=\"/jsoa/images/arrow_right.gif\" class=\"cursor-hand\" onClick=\"addThisItem('ce')\"/><br /><br />\r\n\t\t\t<img src=\"/jsoa/images/arrow_left.gif\" class=\"cursor-hand\" onClick=\"removeThisItem('ce')\" />\r\n\t\t\t</td>\r\n\t\t\t<td id=\"targetTd\" width=\"15px\">\r\n\t\t\t<select size=\"10\" name=\"ceTargetSelect\" id=\"ceTargetSelect\" style=\"width:150px;\" multiple=\"multiple\" ondblclick=\"removeThisItem('ce')\">\r\n\t\t\t</select>\r\n\t\t\t</td>\r\n\t\t\t<td>&nbsp;</td>\r\n\t\t</tr>\r\n\t\t");
if(subTable!=null){ 
            out.write("<tr style=\"display:;\">");
}else{ 
            out.write("<tr style=\"display:none;\">");
} 
            out.write("\r\n\t\t<td colspan=\"4\" style=\"font-size:12px;\">?????????\r\n\t\t");
for(int i=0;subTable!=null&&i<subTable.length;i++){
            out.write("\r\n\t\t<input type=\"radio\" name=\"sub_table\" id=\"sub_table\" value=\"");
            out.print(subTable[i][2]+"-"+subTable[i][0] );
            out.write("\" onclick=\"getSubTable(this)\" />");
            out.print(subTable[i][1] );
            out.write("\r\n\t\t");
} 
            out.write("</td></tr>\r\n\t\t<tr id=\"subTr\" style=\"display:none;\">\r\n\t\t<td id=\"sourceTd1\" width=\"160px\">\r\n\t\t<select size=\"10\" id=\"subSourceSelect\" name=\"subSourceSelect\" style=\"width:150px;\" multiple=\"multiple\" ondblclick=\"addThisItem('sub')\">\r\n\t\t</select>\r\n\t\t</td>\r\n\t\t<td width=\"30px\">\r\n\t\t<img src=\"/jsoa/images/arrow_right.gif\" class=\"cursor-hand\" onClick=\"addThisItem('sub')\"/><br /><br />\r\n\t\t<img src=\"/jsoa/images/arrow_left.gif\" class=\"cursor-hand\" onClick=\"removeThisItem('sub')\" />\r\n\t\t</td>\r\n\t\t<td id=\"targetTd1\" width=\"160px\">\r\n\t\t<select size=\"10\" name=\"subTargetSelect\" id=\"subTargetSelect\" style=\"width:150px;\" multiple=\"multiple\" ondblclick=\"removeThisItem('sub')\">\r\n\t\t</select>\r\n\t\t</td>\r\n\t\t<td></td>\r\n\t\t</tr>\r\n\t\t\r\n\t\t<tr>\r\n\t\t<td>\r\n\t\t<!-- <input type=\"button\"???class=\"btnButton2font\" value=\"??????\" onclick=\"save(");
            out.print(formTable[0][0] );
            out.write(',');
            out.print("".equals(processId)?"''":processId );
            out.write(");\" /> -->\r\n\t\t<input type=\"button\"???class=\"btnButton2font\" value=\"??????\" onclick=\"tijiao();\" />\r\n\t\t</td>\r\n\t\t</tr>\r\n\t</table>\r\n</form></div>\r\n</body>\r\n\r\n");
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
      out.write("\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n<script src=\"/jsoa/js/shortcut.js\"></script>");
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
