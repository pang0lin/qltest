/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:46:54 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.routine.resource_005fmanager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.Calendar;
import java.util.*;
import java.text.DateFormat;
import com.js.oa.routine.resource.po.CsDetailPO;
import com.js.util.util.BrowserJudge;

public final class stockCheckModify_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(3);
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-logic.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-html.tld", Long.valueOf(1499751390000L));
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
    _jspx_imports_classes.add("java.util.Calendar");
    _jspx_imports_classes.add("com.js.oa.routine.resource.po.CsDetailPO");
    _jspx_imports_classes.add("com.js.util.util.BrowserJudge");
    _jspx_imports_classes.add("java.text.DateFormat");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody;

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
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.release();
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.release();
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n<html>\r\n<head>\r\n<title>????????????</title>\r\n<link href=\"/jsoa/style/cssmain.css\" rel=\"stylesheet\" type=\"text/css\">\r\n<script language=\"javascript\" src=\"/jsoa/js/checkForm.js\"></script>\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject2.css\">\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DateObject.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DatePicker.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/editlib.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/tree.js\"></SCRIPT>\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script language=\"javascript\">\r\nfunction changePanle(flag){\r\n    for(i=0;i<2;i++){\r\n\t  eval(\"docinfo\"+i+\".style.display='none'\");\r\n\t  eval(\"Panle\"+i+\".className='btnBQ AlignLeft'\");\r\n\t}\r\n\teval(\"docinfo\"+flag+\".style.display=''\");\r\n\teval(\"Panle\"+flag+\".className='btnBQselected AlignLeft'\");\r\n}\r\n</script>\r\n<script language=\"JavaScript\">\r\n<!--\r\nfunction MM_openBrWindow(theURL,winName,features) { //v2.0\r\n  JSMainWinOpen(theURL,winName,features);\r\n}\r\n//-->\r\n</script>\r\n</head>\r\n");

String close = request.getAttribute("close") == null?"":request.getAttribute("close").toString();
DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.CHINESE);
Calendar now = Calendar.getInstance();

if(close.equals("1")){
      out.write("\r\n<script language=\"javascript\">\r\n<!--\r\nwindow.close();\r\ntry{\r\n    opener.location.href = \"/jsoa/StockCheckAction.do?flag=view&stockName=");
      out.print(request.getParameter("stockName"));
      out.write("&stockId=");
      out.print(request.getParameter("stock"));
      out.print(request.getParameter("pager.offset")==null?"":"&pager.offset="+request.getParameter("pager.offset"));
      out.write("\";\r\n}catch(e){}\r\n//-->\r\n</script>\r\n");

}else{
    if(close.equals("0")){
      out.write("\r\n    <script language=\"javascript\">\r\n    <!--\r\n    try{\r\n        opener.location.href = \"/jsoa/StockCheckAction.do?flag=view&stockName=");
      out.print(request.getParameter("stockName"));
      out.write("&stockId=");
      out.print(request.getParameter("stock"));
      out.print(request.getParameter("pager.offset")==null?"":"&pager.offset="+request.getParameter("pager.offset"));
      out.write("\";\r\n    }catch(e){}\r\n    //-->\r\n    </script>\r\n");

    }else if(close.equals("2")){
      out.write("\r\n    <script language=\"javascript\">\r\n    <!--\r\n    alert(\"???????????????\");\r\n    //-->\r\n    </script>\r\n    ");
}

      out.write("\r\n<!--intoStockModify.jsp-->\r\n\r\n<body leftmargin=\"0\" topmargin=\"0\"  class=\"MainFrameBox Pupwin\">\r\n<br />\r\n<table width=\"100%\" height=\"25\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"inlineBottomLine\">\r\n  <tr>\r\n  </tr>\r\n</table>\r\n\r\n\r\n<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"docBox\">\r\n  <tr>\r\n    <td height=\"312\" valign=\"top\">\r\n\t\t<div id=\"docinfo0\" style=\"display:;\"> \r\n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"1\">\r\n\t\t\t\t");
      //  html:form
      org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag) _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(org.apache.struts.taglib.html.FormTag.class);
      boolean _jspx_th_html_005fform_005f0_reused = false;
      try {
        _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
        _jspx_th_html_005fform_005f0.setParent(null);
        // /routine/resource_manager/stockCheckModify.jsp(87,4) name = action type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_html_005fform_005f0.setAction("/StockCheckAction");
        // /routine/resource_manager/stockCheckModify.jsp(87,4) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_html_005fform_005f0.setMethod("post");
        int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
        if (_jspx_eval_html_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n\t\t\t\t<tr>\r\n\t\t\t\t  <td height=\"25\">???????????????</td>\r\n\t\t\t\t  <td>");
            if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
              return;
            if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
              return;
            out.write("</td>\r\n\t\t\t\t  <td height=\"25\">???????????????</td>\r\n\t\t\t\t  <td>\r\n\t\t\t\t\t  ");
            out.print(request.getAttribute("stockName"));
            out.write("\r\n\t\t\t\t\t  ");
            if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
              return;
            out.write("<input type=\"hidden\" name=\"stockName\" value=\"");
            out.print(request.getAttribute("stockName"));
            out.write("\">\r\n\t\t\t\t  </td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n\t\t\t\t  <td height=\"25\">???????????????</td>\r\n\t\t\t\t  ");
if(request.getAttribute("csDate") != null) now.setTime((java.util.Date) request.getAttribute("csDate"));
            out.write("\r\n\t\t\t\t  <td><script language=\"javascript\">var dtpDate1 = createDatePicker(\"csDate\",\"");
            out.print(now.get(Calendar.YEAR));
            out.write('"');
            out.write(',');
            out.write('"');
            out.print(now.get(Calendar.MONTH) + 1);
            out.write('"');
            out.write(',');
            out.write('"');
            out.print(now.get(Calendar.DATE));
            out.write("\");</script></td>\r\n\t\t\t\t  <td height=\"25\">???&nbsp;???&nbsp;??????</td>\r\n\t\t\t\t  <td>\r\n\t\t\t\t\t  ");
            if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
              return;
            out.write("\r\n\t\t\t\t  </td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n\t\t\t\t  <td height=\"25\">???&nbsp;&nbsp;&nbsp;&nbsp;??????</td>\r\n\t\t\t\t  <td colspan=\"3\">\r\n\t\t\t\t\t  ");
            if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
              return;
            out.write("\r\n\t\t\t\t  </td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n\t\t\t\t  <td colspan=\"4\" valign=\"top\">&nbsp;&nbsp;<!--<a href=\"javascript:addGoods();\">??????????????????</a>--></td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n\t\t\t\t  <td colspan=\"4\" valign=\"top\" align=\"center\">\r\n\t\t\t\t\t  <table width=\"95%\" border=\"1\" cellpadding=\"1\" cellspacing=\"0\" bordercolor=\"000000\" id=\"detailTable\" bordercolordark=\"#E1E1E1\">\r\n\t\t\t\t\t\t  <tr>\r\n\t\t\t\t\t\t\t  <td align=\"center\" height=\"25\">????????????</td>\r\n\t\t\t\t\t\t\t  <td align=\"center\">????????????</td>\r\n\t\t\t\t\t\t\t  <td align=\"center\">??????</td>\r\n\t\t\t\t\t\t\t  <td align=\"center\">????????????</td>\r\n\t\t\t\t\t\t\t  <td align=\"center\">????????????</td>\r\n\t\t\t\t\t\t\t  <td align=\"center\">????????????</td>\r\n\t\t\t\t\t\t  </tr>\r\n\t\t\t\t\t\t  ");

						  Set csDetail = (Set) request.getAttribute("csDetail");
						  if(csDetail != null){
							  Iterator iter = csDetail.iterator();
							  CsDetailPO csDetailPO = null;
							  while(iter.hasNext()){
								  csDetailPO = (CsDetailPO) iter.next();
							  
            out.write("\r\n\t\t\t\t\t\t\t\t  <tr>\r\n\t\t\t\t\t\t\t\t\t  <td>");
            out.print(csDetailPO.getGoodsId());
            out.write("<input type=\"hidden\" name=\"goodsId\" value=\"");
            out.print(csDetailPO.getGoodsId());
            out.write("\"></td>\r\n\t\t\t\t\t\t\t\t\t  <td>");
            out.print(csDetailPO.getGoodsName());
            out.write("<input type=\"hidden\" name=\"goodsName\" value=\"");
            out.print(csDetailPO.getGoodsName());
            out.write("\"></td>\r\n\t\t\t\t\t\t\t\t\t  <td>");
            out.print(csDetailPO.getGoodsUnit());
            out.write("<input type=\"hidden\" name=\"goodsUnit\" value=\"");
            out.print(csDetailPO.getGoodsUnit());
            out.write("\"></td>\r\n\t\t\t\t\t\t\t\t\t  <td>");
            out.print(csDetailPO.getAccAmount());
            out.write("<input type=\"hidden\" name=\"accAmount\" value=\"");
            out.print(csDetailPO.getAccAmount());
            out.write("\"></td>\r\n\t\t\t\t\t\t\t\t\t  <td><input type=\"text\" class=\"css0\" value=\"");
            out.print(csDetailPO.getFactAmount());
            out.write("\" size=\"10\" name=\"factAmount\" onblur=\"javascript:CheckAndCount();\"></td>\r\n\t\t\t\t\t\t\t\t\t  <td><input type=\"text\" class=\"css0\" value=\"");
            out.print(csDetailPO.getPlAmount());
            out.write("\" size=\"10\" name=\"plAmount\" readonly></td>\r\n\t\t\t\t\t\t\t\t  </tr>\r\n\t\t\t\t\t\t\t  ");

							  }
						  }
						  
            out.write("\r\n\t\t\t\t\t  </table>\r\n\t\t\t\t  </td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n\t\t\t\t  <td height=\"25\">???&nbsp;???&nbsp;??????</td>\r\n\t\t\t\t  <td>");
            out.print(request.getAttribute("makeManName"));
            out.write("</td>\r\n\t\t\t\t  ");
String checkFlag = request.getAttribute("checkFlag")==null?"N":request.getAttribute("checkFlag").toString();
            out.write("\r\n\t\t\t\t  <td height=\"25\">???????????????</td>\r\n\t\t\t\t  <td>");
            out.print(checkFlag.equals("Y")?"??????":"?????????");
            out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n\t\t\t\t  <td height=\"25\">???????????????</td>\r\n\t\t\t\t  <td>");
            out.print(df.format((java.util.Date) request.getAttribute("makeDate")));
            out.write("</td>\r\n\t\t\t\t  <td height=\"25\" ");
            out.print(checkFlag.equals("Y")?"":"style='display:none'");
            out.write(">????????????</td>\r\n\t\t\t\t  <td ");
            out.print(checkFlag.equals("Y")?"":"style='display:none'");
            out.write('>');
            out.print(request.getAttribute("checkManName"));
            out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr ");
            out.print(checkFlag.equals("Y")?"":"style='display:none'");
            out.write(">\r\n\t\t\t\t  <td height=\"25\">&nbsp;</td>\r\n\t\t\t\t  <td>&nbsp;</td>\r\n\t\t\t\t  <td height=\"25\">???????????????</td>\r\n\t\t\t\t  <td>");
            out.print(request.getAttribute("checkDate")==null?"":df.format((java.util.Date) request.getAttribute("checkDate")));
            out.write("</td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<input type=\"hidden\" name=\"flag\">\r\n\t\t\t\t");
if(request.getParameter("pager.offset") != null){
            out.write("\r\n\t\t\t\t<input type=\"hidden\" name=\"pager.offset\" value=\"");
            out.print(request.getParameter("pager.offset"));
            out.write("\">\r\n\t\t\t\t");
}
            out.write("\r\n\t\t\t\t");
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
      out.write("\r\n\t\t\t</table>\r\n\t\t</div>\r\n\t</td>\r\n  </tr>\r\n</table>\r\n\r\n\r\n\r\n\r\n\r\n<table width=\"100%\" border=\"0\">\r\n<tr>\r\n  <td>\r\n\t<button class=\"btnButton4font\" style=\"cursor:pointer\" onclick=\"javascript:save('update');\">??????</button>\r\n\t<button class=\"btnButton4font\" style=\"cursor:pointer\" onclick=\"javascript:save('check');\">??????</button>\r\n\t<button class=\"btnButton4font\" style=\"cursor:pointer\" onclick=\"javascript:save('unCheck');\">????????????</button>\r\n\t<button class=\"btnButton2font\" style=\"cursor:pointer\" onclick=\"/jsoa/StockCheckAction.do?flag=modify&csMasterId=");
      out.print(request.getParameter("csMasterId"));
      out.write("&checkFlag=");
      out.print(checkFlag);
      out.write("\">??????</button>\r\n\t<button class=\"btnButton2font\" style=\"cursor:pointer\" onclick=\"javascript:window.close();\">??????</button>\r\n  </td>\r\n</tr>\r\n</table>\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n</body>\r\n");
}
      out.write("\r\n</html>\r\n<script language=\"javascript\">\r\n<!--\r\nfunction save(tag){\r\n    if(document.all.remark.value.length > 100){\r\n        alert(\"??????????????????100????????????\");\r\n        document.all.remark.focus();\r\n    }else{\r\n        document.all.flag.value = tag;\r\n        StockCheckActionForm.submit();\r\n    }\r\n}\r\n\r\nfunction addGoods(){\r\n    MM_openBrWindow('/jsoa/GoodsAction.do?addGoods=1','','TOP=0,LEFT=0,scrollbars=yes,resizable=yes,width=620,height=400');\r\n}\r\n\r\nfunction CheckAndCount(){\r\n    try{\r\n\t    var event = window.event || arguments.callee.caller.arguments[0];\r\n\t\tevent.srcElement = (typeof (event.srcElement) ? event.target : event.srcElement);\r\n\t}catch(e){}\r\n    ");
if (BrowserJudge.isMSIE11AndTrend(request) || BrowserJudge.isChrome(request) || 
	        		BrowserJudge.isFirefox(request) || BrowserJudge.isSafari(request) ){
      out.write("\r\n        var thisvalue =event.srcElement.value;\r\n    ");
}else{
      out.write("\r\n        var thisvalue=document.all[srcIndex].value;\r\n    ");
}
      out.write("\r\n    if (isNaN(thisvalue)){\r\n        onblurAlert(event, \"???????????????????????????\");\r\n    }else{\r\n        document.all[srcIndex+2].value=document.all[srcIndex].value-document.all[srcIndex-2].value;\r\n    }\r\n}\r\n\r\nfunction total_money(){\r\n    var goods_num=document.all.detailTable.rows.length - 1;\r\n    var total_money = 0;\r\n    if(goods_num == 0)\r\n\tdocument.all.ptMoney.value = 0;\r\n    else if(goods_num==1)\r\n\tdocument.all.ptMoney.value = document.all.money.value;\r\n    else{\r\n        for(i = 0;i < goods_num; i++){\r\n            total_money = total_money * 1 + (document.all.money[i].value) * 1;\r\n        }\r\n        document.all.ptMoney.value = total_money;\r\n    }\r\n}\r\nfunction onblurAlert(event, alertStr){\r\n    if(navigator.userAgent.indexOf(\"Firefox\") == -1 ){\r\n        alert(alertStr);\r\n        event.srcElement.focus();\r\n    }else{\r\n        window.setTimeout( function(){\r\n            alert(alertStr);\r\n            event.target.focus();\r\n        }, 0);\r\n    }\r\n}\r\n//-->\r\n</script>\r\n\r\n<script src=\"/jsoa/js/util.js\"></script>");
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

  private boolean _jspx_meth_bean_005fwrite_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f0 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    boolean _jspx_th_bean_005fwrite_005f0_reused = false;
    try {
      _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fwrite_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /routine/resource_manager/stockCheckModify.jsp(90,10) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f0.setName("StockCheckActionForm");
      // /routine/resource_manager/stockCheckModify.jsp(90,10) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f0.setProperty("csMasterId");
      int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
      if (_jspx_th_bean_005fwrite_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
      _jspx_th_bean_005fwrite_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fwrite_005f0, _jsp_getInstanceManager(), _jspx_th_bean_005fwrite_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005fhidden_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_005fhidden_005f0 = (org.apache.struts.taglib.html.HiddenTag) _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    boolean _jspx_th_html_005fhidden_005f0_reused = false;
    try {
      _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005fhidden_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /routine/resource_manager/stockCheckModify.jsp(90,74) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fhidden_005f0.setProperty("csMasterId");
      int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
      if (_jspx_th_html_005fhidden_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
      _jspx_th_html_005fhidden_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fhidden_005f0, _jsp_getInstanceManager(), _jspx_th_html_005fhidden_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005fhidden_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_005fhidden_005f1 = (org.apache.struts.taglib.html.HiddenTag) _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    boolean _jspx_th_html_005fhidden_005f1_reused = false;
    try {
      _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
      _jspx_th_html_005fhidden_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /routine/resource_manager/stockCheckModify.jsp(94,7) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fhidden_005f1.setProperty("stock");
      int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
      if (_jspx_th_html_005fhidden_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
      _jspx_th_html_005fhidden_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fhidden_005f1, _jsp_getInstanceManager(), _jspx_th_html_005fhidden_005f1_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005ftext_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f0 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
    boolean _jspx_th_html_005ftext_005f0_reused = false;
    try {
      _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005ftext_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /routine/resource_manager/stockCheckModify.jsp(103,7) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setProperty("csMan");
      // /routine/resource_manager/stockCheckModify.jsp(103,7) name = maxlength type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setMaxlength("10");
      // /routine/resource_manager/stockCheckModify.jsp(103,7) name = size type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setSize("25");
      // /routine/resource_manager/stockCheckModify.jsp(103,7) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setStyleClass("css0");
      int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
      if (_jspx_th_html_005ftext_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
      _jspx_th_html_005ftext_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005ftext_005f0, _jsp_getInstanceManager(), _jspx_th_html_005ftext_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005ftextarea_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:textarea
    org.apache.struts.taglib.html.TextareaTag _jspx_th_html_005ftextarea_005f0 = (org.apache.struts.taglib.html.TextareaTag) _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.get(org.apache.struts.taglib.html.TextareaTag.class);
    boolean _jspx_th_html_005ftextarea_005f0_reused = false;
    try {
      _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005ftextarea_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /routine/resource_manager/stockCheckModify.jsp(109,7) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setProperty("remark");
      // /routine/resource_manager/stockCheckModify.jsp(109,7) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setStyleClass("css0");
      // /routine/resource_manager/stockCheckModify.jsp(109,7) name = cols type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setCols("50");
      // /routine/resource_manager/stockCheckModify.jsp(109,7) name = rows type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setRows("3");
      int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
      if (_jspx_th_html_005ftextarea_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
      _jspx_th_html_005ftextarea_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005ftextarea_005f0, _jsp_getInstanceManager(), _jspx_th_html_005ftextarea_005f0_reused);
    }
    return false;
  }
}
