/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:47:39 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.routine.voiture_005fmanager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class voitureFeedBackAStat_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

      out.write("\r\n\r\n\r\n");

java.util.Date now= new java.util.Date();
java.util.Date tmpBeginDate=new java.util.Date();



java.util.Date tmpEndDate=new java.util.Date();

 if(request.getAttribute("beginDate")!=null&&!request.getAttribute("beginDate").toString().equals("")){
   tmpBeginDate=(java.util.Date) request.getAttribute("beginDate");
 }

 if(request.getAttribute("endDate")!=null&&!request.getAttribute("endDate").toString().equals("")){
  tmpEndDate=(java.util.Date) request.getAttribute("endDate");
 }


java.util.Map nmap=null;
   if(request.getAttribute("mapn")!=null){
  nmap = (java.util.Map) request.getAttribute("mapn");
   }
String  num1="0";
String  num1Per="0";

String  num2="0";
String  num2Per="0";

String  num3="0";
String  num3Per="0";

String  num4="0";
String  num4Per="0";
if(nmap!=null&&nmap.get("num1")!=null){
   num1=nmap.get("num1").toString();
  num1Per=nmap.get("numPer1").toString();

  num2=nmap.get("num2").toString();
  num2Per=nmap.get("numPer2").toString();

  num3=nmap.get("num3").toString();
  num3Per=nmap.get("numPer3").toString();

  num4=nmap.get("num4").toString();
  num4Per=nmap.get("numPer4").toString();

}



      out.write("\r\n\r\n\r\n");
      //  html:html
      org.apache.struts.taglib.html.HtmlTag _jspx_th_html_005fhtml_005f0 = (org.apache.struts.taglib.html.HtmlTag) _005fjspx_005ftagPool_005fhtml_005fhtml.get(org.apache.struts.taglib.html.HtmlTag.class);
      boolean _jspx_th_html_005fhtml_005f0_reused = false;
      try {
        _jspx_th_html_005fhtml_005f0.setPageContext(_jspx_page_context);
        _jspx_th_html_005fhtml_005f0.setParent(null);
        int _jspx_eval_html_005fhtml_005f0 = _jspx_th_html_005fhtml_005f0.doStartTag();
        if (_jspx_eval_html_005fhtml_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/style/cssmain.css\">\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject1.css\">\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DateObject.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DatePicker.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/editlib.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/tree.js\"></SCRIPT>\r\n<script  src=\"/jsoa/js/checkQuery.js\"  language=\"javascript\" ></script>\r\n<!--  STYLE\tCHANGE START -->\r\n<link href=\"skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script\tsrc=\"js/js.js\" language=\"javascript\"></script>\r\n</head>\r\n<body>\r\n\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td>\r\n\r\n<table width=\"100%\"\tborder=\"0\" align=\"center\" cellpadding=\"1\" cellspacing=\"1\" class=\"searchbar\">\r\n");
            //  html:form
            org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag) _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(org.apache.struts.taglib.html.FormTag.class);
            boolean _jspx_th_html_005fform_005f0_reused = false;
            try {
              _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
              _jspx_th_html_005fform_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
              // /routine/voiture_manager/voitureFeedBackAStat.jsp(87,0) name = action type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setAction("/VoitureSendAction?flag=reedBackAStat");
              // /routine/voiture_manager/voitureFeedBackAStat.jsp(87,0) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setMethod("post");
              int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
              if (_jspx_eval_html_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n<tr>\r\n\t  <td width=\"20\" rowspan=\"5\"></td>\r\n\t  <td width=\"30%\">\r\n\t\t  ???&nbsp;&nbsp;??????\r\n\t\t <input type=\"text\" name=\"motorMan\"  class=\"inputtext\" value=\"");
                  out.print(request.getParameter("motorMan")==null?"":request.getParameter("motorMan").toString());
                  out.write("\"/>\r\n\t  </td>\r\n\t  <td nowrap=\"nowrap\">\r\n\t\t   ???????????????\r\n\t\t");

				java.util.Date beginDate = new java.util.Date();
				if(tmpBeginDate!=null)
				beginDate=tmpBeginDate;
				
                  out.write("\r\n\t\t\t\t<script language=\"javascript\">\r\n\t\t\t\tvar dtpDate = createDatePicker(\"beginDate\",\"");
                  out.print(beginDate.getYear() + 1900);
                  out.write('"');
                  out.write(',');
                  out.write('"');
                  out.print(beginDate.getMonth() + 1);
                  out.write("\", \"");
                  out.print(beginDate.getDate());
                  out.write("\");\r\n\t\t\t\t</script>???\r\n\t\t\t\t");

				java.util.Date endDate = new java.util.Date();
				if(tmpEndDate!=null)
				endDate=tmpEndDate;
				
                  out.write("\r\n\t\t\t\t<script language=\"javascript\">\r\n\t\t\t\tvar dtpDate = createDatePicker(\"endDate\",\"");
                  out.print(endDate.getYear() + 1900);
                  out.write('"');
                  out.write(',');
                  out.write('"');
                  out.print(endDate.getMonth() + 1);
                  out.write("\", \"");
                  out.print(endDate.getDate());
                  out.write("\");\r\n\t\t\t\t</script>\r\n\t\t\t\t<input type=\"checkbox\" value=\"1\" name=\"dateCheck\" ");
if ("1".equals(request.getAttribute("dateCheck"))) {
                  out.write("checked");
}
                  out.write(" style=\"cursor:pointer\">\r\n\t  </td>\r\n\r\n\t</tr>\r\n\r\n\r\n<tr>\r\n\t  <td>\r\n\t\t ????????????\r\n\t\t  <input type=\"text\" name=\"empName\"  class=\"inputtext\" value=\"");
                  out.print(request.getParameter("empName")==null?"":request.getParameter("empName").toString());
                  out.write("\"/>\r\n\t  </td>\r\n\r\n\t  \t  <td align=\"right\"><input type=\"button\" class=\"btnButton2Font\" onClick=\"commit();\" value=\"??????\"/><button  class=\"btnButton2Font\" onclick=\"cleared();\" style=\"cursor:pointer\">??????</button></td>\r\n\t</tr>\r\n   ");
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
            out.write("\r\n</table>\r\n<table width=\"100%\"\theight=\"25\"\tborder=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n\t<tr>\r\n\t\t<td align=\"right\" valign=\"middle\">&nbsp;</td>\r\n\t</tr>\r\n</table>\r\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"listTable outTopline\">\r\n\r\n       <tr>\r\n\t   <td width=\"20%\" class=\"listTableHead\"> ?????????</td>\r\n\t   <td  class=\"listTableHead\" > ?????? </td>\r\n\t   <td width=\"30%\" class=\"listTableHeadLast\"> ????????? </td>\r\n\t   </tr>\r\n\r\n      <tr>\r\n\t\t  <td class=\"listTableLine1\">????????????</td>\r\n\t\t  <td class=\"listTableLine1\">");
            out.print(num1);
            out.write("</td>\r\n\t\t  <td class=\"listTableLine1\">");
            out.print(num1Per);
            out.write("</td>\r\n\t\t</tr>\r\n\r\n\r\n\t\t <tr>\r\n\t\t  <td class=\"listTableLine1\">?????????</td>\r\n\t\t  <td class=\"listTableLine1\">");
            out.print(num2);
            out.write("</td>\r\n\t\t  <td class=\"listTableLine1\">");
            out.print(num2Per);
            out.write("</td>\r\n\t\t</tr>\r\n\r\n\t\t <tr>\r\n\t\t  <td class=\"listTableLine1\">?????????</td>\r\n\t\t  <td class=\"listTableLine1\">");
            out.print(num3);
            out.write("</td>\r\n\t\t  <td class=\"listTableLine1\">");
            out.print(num3Per);
            out.write("</td>\r\n\t\t</tr>\r\n\t\t <tr>\r\n\t\t  <td class=\"listTableLine1\">????????????</td>\r\n\t\t  <td class=\"listTableLine1\">");
            out.print(num4);
            out.write("</td>\r\n\t\t  <td class=\"listTableLine1\">");
            out.print(num4Per);
            out.write("</td>\r\n\t\t</tr>\r\n</table>\r\n</td></tr></table>\r\n</body>\r\n");
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
      out.write("\r\n<script language=\"javascript\">\r\n<!--\r\n\r\nfunction cleared(){\r\n   window.location.href = \"/jsoa/VoitureSendAction.do?flag=reedBackAStat&feedlist=1\" ;\r\n}\r\n\r\nfunction commit(){\r\n\r\n VoitureSendActionForm.submit();\r\n}\r\n//-->\r\n</script>\r\n\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
