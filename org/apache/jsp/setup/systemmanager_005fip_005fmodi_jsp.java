/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:50:32 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.setup;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class systemmanager_005fip_005fmodi_jsp extends org.apache.jasper.runtime.HttpJspBase
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
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleClass_005fproperty_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody;

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
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleClass_005fproperty_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fhtml.release();
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.release();
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleClass_005fproperty_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.release();
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

	response.setHeader("Cache-Control","no-store");
    	response.setHeader("Pragma","no-cache");
    	response.setDateHeader ("Expires", 0);

        String strBenginAddress = request.getParameter("begineAddress");

        String strEndAddress = request.getParameter("endAddress");
        String strBenginTime = request.getParameter("beginTime");
        String strEndTime = request.getParameter("endTime");
        String action = request.getParameter("action");
        String saveType =request.getParameter("saveType");
        String modify_id = request.getParameter("id");
        java.util.Date ipOpenBeginTime=new java.util.Date();
        java.util.Date ipOpenEndTime=new java.util.Date();
        String ipB1 = "0";
        String ipB2 = "0";
        String ipB3 = "0";
        String ipB4 = "0";

        String ipE1 = "0";
        String ipE2 = "0";
        String ipE3 = "0";
        String ipE4 = "0";
        int beginYear = 0;
        int beginMonth = 0;
        int beginDate = 0;
        int endYear = 0;
        int endMonth = 0;
        int endDate = 0;
        if("update".equals(action)){
            	//IP开始地址
            	java.util.regex.Pattern p1 = java.util.regex.Pattern.compile("(\\d+).(\\d+).(\\d+).(\\d+)");
		java.util.regex.Matcher m1 = p1.matcher(strBenginAddress);
                m1.find();
		ipB1 = m1.group(1);
            	ipB2 = m1.group(2);
            	ipB3 = m1.group(3);
            	ipB4 = m1.group(4);
                if("".equals(ipB1)){
                    ipB1="0";
                    ipB2="0";
                    ipB3="0";
                    ipB4="0";
                }

                //开始时间
            	 beginYear = Integer.parseInt(strBenginTime.substring(0,4));
            	 beginMonth = Integer.parseInt(strBenginTime.substring(5,7));
            	 beginDate = Integer.parseInt(strBenginTime.substring(8,10));
            }else{
            	 beginYear=ipOpenBeginTime.getYear()+1900;
                 beginMonth=ipOpenBeginTime.getMonth()+1;
                 beginDate=ipOpenBeginTime.getDate();
             }
        if("update".equals(action)){
            	//IP结束地址
                if(!"0.0.0.0".equals(strEndAddress)){
                java.util.regex.Pattern p2 = java.util.regex.Pattern.compile("(\\d+).(\\d+).(\\d+).(\\d+)");
		java.util.regex.Matcher m2 = p2.matcher(strEndAddress);
                m2.find();
		ipE1 = m2.group(1);
                ipE2 = m2.group(2);
            	ipE3 = m2.group(3);
            	ipE4 = m2.group(4);
                }else{
                    ipE1 = "0";
                    ipE2 = "0";
                    ipE3 = "0";
                    ipE4 = "0";
                    }

            	//结束时间
            	 endYear = Integer.parseInt(strEndTime.substring(0,4));
            	 endMonth = Integer.parseInt(strEndTime.substring(5,7));
            	 endDate = Integer.parseInt(strEndTime.substring(8,10));

            }else{
            	endYear=ipOpenEndTime.getYear()+1900;
    		endMonth=ipOpenEndTime.getMonth()+1;
    		endDate=ipOpenEndTime.getDate();
                }
int pageOffset=0;
if(request.getParameter("pager.offset")!=null) pageOffset=Integer.parseInt(request.getParameter("pager.offset"));
if(pageOffset<0) pageOffset=0;
String url="/ipAction.do?action=modify&pager.offset="+pageOffset;
String flag= request.getAttribute("flag")==null?"":request.getAttribute("flag").toString();

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
            out.write("\r\n<head>\r\n<title>修改IP</title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<link href=\"/jsoa/skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject2.css\">\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DateObject.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DatePicker.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/editlib.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/tree.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/js/public.js\"></SCRIPT>\r\n<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n<style>\r\n.a3{width:28;border:0;text-align:center}\r\n</style>\r\n</head>\r\n\r\n<body  class=\"MainFrameBox Pupwin\" onLoad=\"load();resizeWin(650,320);\">\r\n\r\n<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"docBoxNoPanel\">\r\n<tr>\r\n    <td valign=\"top\">\r\n\t<div id=\"docinfo0\" style=\"display:;\"> \r\n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"1\">\r\n   ");
            //  html:form
            org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag) _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(org.apache.struts.taglib.html.FormTag.class);
            boolean _jspx_th_html_005fform_005f0_reused = false;
            try {
              _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
              _jspx_th_html_005fform_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
              // /setup/systemmanager_ip_modi.jsp(122,3) name = action type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setAction(url);
              // /setup/systemmanager_ip_modi.jsp(122,3) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setMethod("post");
              int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
              if (_jspx_eval_html_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n    <tr>\r\n\t    <td width=\"110\" nowrap>IP起始地址&nbsp;<label class=\"mustFillcolor\">*</label>：</td>\r\n\t\t<td>\r\n\t\t   ");
                  if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
                    return;
                  out.write("\r\n                                <table>\r\n                                    <tr>\r\n                                        <td>\r\n                                <div id=\"ipBeginAddress_div\" style=\"border-width:1;border-color:balck;border-style:solid;width:150;font-size:9pt\" class=\"inputText\">\r\n\t\t\t\t<input  type=\"text\"  name=\"ipB1\"  value=\"");
                  out.print(Integer.parseInt(ipB1));
                  out.write("\" maxlength=\"3\"  class=\"a3\" onKeyUp=\"mask(this)\"  onbeforepaste=\"mask_c()\" />.\r\n\t\t\t\t<input  type=\"text\"  name=\"ipB2\"  value=\"");
                  out.print(Integer.parseInt(ipB2));
                  out.write("\" maxlength=\"3\"  class=\"a3\" onKeyUp=\"mask(this)\"  onbeforepaste=\"mask_c()\" />.\r\n\t\t\t\t<input  type=\"text\"  name=\"ipB3\"  value=\"");
                  out.print(Integer.parseInt(ipB3));
                  out.write("\" maxlength=\"3\"  class=\"a3\" onKeyUp=\"mask(this)\"  onbeforepaste=\"mask_c()\" />.\r\n\t\t\t\t<input  type=\"text\"  name=\"ipB4\"  value=\"");
                  out.print(Integer.parseInt(ipB4));
                  out.write("\" maxlength=\"3\"  class=\"a3\" onKeyUp=\"mask(this)\"  onbeforepaste=\"mask_c()\" />\r\n                            </div>\r\n                                      </td>\r\n                                      <td></td>\r\n                                    </tr>\r\n                                </table>\r\n\t\t</td>\r\n\t\t<td width=\"110\" nowrap>IP结束地址：</td>\r\n\t\t<td>\r\n\t\t   ");
                  if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
                    return;
                  out.write("\r\n                            <div id=\"ipEndAddress_div\" style=\"border-width:1;border-color:balck;border-style:solid;width:150;font-size:9pt\" class=\"inputText\">\r\n\t\t\t\t<input  type=\"text\" name=\"ipE1\"  value=\"");
                  out.print(Integer.parseInt(ipE1));
                  out.write("\" maxlength=\"3\"  class=\"a3\"  onkeyup=\"mask2(this)\"  onbeforepaste=\"mask_c()\">.\r\n\t\t\t\t<input  type=\"text\"  name=\"ipE2\" value=\"");
                  out.print(Integer.parseInt(ipE2));
                  out.write("\"  maxlength=\"3\"  class=\"a3\"  onkeyup=\"mask2(this)\"  onbeforepaste=\"mask_c()\">.\r\n\t\t\t\t<input  type=\"text\"  name=\"ipE3\" value=\"");
                  out.print(Integer.parseInt(ipE3));
                  out.write("\"  maxlength=\"3\"  class=\"a3\"  onkeyup=\"mask2(this)\"  onbeforepaste=\"mask_c()\">.\r\n\t\t\t\t<input  type=\"text\"  name=\"ipE4\" value=\"");
                  out.print(Integer.parseInt(ipE4));
                  out.write("\" maxlength=\"3\"  class=\"a3\"  onkeyup=\"mask2(this)\"  onbeforepaste=\"mask_c()\">\r\n                            </div>\t\r\n\t\t</td>\r\n\t</tr>\r\n\t<tr>\r\n\t    <td>申请开通日期：</td>\r\n\t\t<td>\r\n\t\t<script language=javascript>\r\n\t\t\tvar dtpDate = createDatePicker(\"ipOpenBeginTime\",");
                  out.print(beginYear);
                  out.write(',');
                  out.print(beginMonth);
                  out.write(',');
                  out.print(beginDate);
                  out.write(");\r\n\t\t</script>\r\n\t\t</td>\r\n\t\t<td>申请结束日期：</td>\r\n\t\t<td>\r\n\t\t<script language=javascript>\r\n\t\t      var dtpDate = createDatePicker(\"ipOpenEndTime\",");
                  out.print(endYear);
                  out.write(',');
                  out.print(endMonth);
                  out.write(',');
                  out.print(endDate);
                  out.write(");\r\n\t\t</script>\r\n\t\t</td>\r\n\t</tr>\r\n\t<tr>\r\n\t    <td>申请人姓名&nbsp;<label class=\"mustFillcolor\">*</label>：</td>\r\n\t\t<td>");
                  if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
                    return;
                  out.write("</td>\r\n\t\t<td>是否开通：</td>\r\n\t\t<td>");
                  if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
                    return;
                  out.write("&nbsp;注:打勾即为开通.</td>\r\n\t</tr>\r\n\t\r\n\t<input type=\"hidden\" name=\"modify_id\" value=\"");
                  out.print(modify_id);
                  out.write("\"/>\r\n\t<input type=\"hidden\" name=\"saveType\" value=\"");
                  out.print(saveType);
                  out.write("\"/>\r\n   ");
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
            out.write("\r\n   </table>\r\n   </div>\r\n   <br/>\r\n   <table>\r\n<tr>\r\n\t    <td colspan=\"4\">\r\n\t\t    <input type=\"button\"  class=\"btnButton4font\" onclick=\"javascript:updateAndExit();\" value=\"保存退出\"/>\r\n\t\t    <button class=\"btnButton2font\" onClick=\"javascript:resetMe();\">重置</button>\r\n\t\t    <button class=\"btnButton2font\" onClick=\"javascript:window.close();\">退出</button>\r\n\t\t</td>\r\n\t</tr>\r\n   </table>\r\n   </td>\r\n   </tr>\r\n</table>\r\n</body>\r\n");
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
      out.write("\r\n\r\n<script language=\"JavaScript\">\r\n<!--\r\nfunction load(){\r\n    var reload = document.all.ipActionForm.saveType.value;\r\n\r\n    if (reload == \"updateAndExit\"){\r\n\t    \tvar flag=\"");
      out.print(flag);
      out.write("\";\r\n\t    \tif(\"foraudit\"==flag){\r\n\t    \t\talert(\"数据已提交审核管理员审核！\");\r\n\t    \t}\r\n            window.opener.location.href=((window.location.href).substring(0,(window.location.href).indexOf(\"/jsoa\")))+\"/jsoa/ipAction.do?action=view&pager.offset=");
      out.print(pageOffset);
      out.write("\";\r\n            //alert(\"数据修改成功\");\r\n            window.close();\r\n            }\r\n\r\n    if (reload == \"null\"){\r\n            return ;\r\n    }\r\n\r\n}\r\n\r\n\r\nfunction updateAndExit(){\r\n    document.ipActionForm.saveType.value = \"updateAndExit\";\r\n    \tvar ipBegin1 =  document.all.ipB1.value;\r\n        var ipBegin2 =\tdocument.all.ipB2.value;\r\n        var ipBegin3 =  document.all.ipB3.value;\r\n        var ipBegin4 =  document.all.ipB4.value;\r\n        if(ipBegin1 == \"\"){\r\n            \talert(\"开始地址不能为空！\")\r\n                    document.all.ipB1.focus();\r\n                    return false;\r\n            }else if(ipBegin2 == \"\"){\r\n                alert(\"开始地址不能为空！\")\r\n                    document.all.ipB2.focus();\r\n                    return false;\r\n                }else if(ipBegin3 == \"\"){\r\n            \talert(\"开始地址不能为空！\")\r\n                    document.all.ipB3.focus();\r\n                    return false;\r\n                    }else if(ipBegin4 == \"\"){\r\n            \talert(\"开始地址不能为空！\")\r\n                    document.all.ipB4.focus();\r\n                    return false;\r\n");
      out.write("        \t}\r\n        var ipBegin  =  ipBegin1+\".\"+ipBegin2+\".\"+ipBegin3+\".\"+ipBegin4;\r\n        if (ipBegin ==\"0.0.0.0\"){\r\n            alert(\"0不是一个有效项目。请指定一个介于1和223之间的数值！\")\r\n            document.all.ipB1.focus();\r\n            return false;\r\n            }\r\n        document.all.ipActionForm.ipAddressBegin.value = ipBegin;\r\n        var ipEnd1  =  document.all.ipE1.value;\r\n        \tif (ipEnd1 == \"0\"){\r\n                    alert(\"0不是一个有效项目。请指定一个介于1和223之间的数值！\")\r\n                    document.all.ipE1.focus();\r\n                    return false;\r\n                    }\r\n     \t\tif (ipEnd1 == \"\"){\r\n        \t\tipEnd1 = 0;\r\n            \t}\r\n            var ipEnd2 = document.all.ipE2.value;\r\n        \tif (ipEnd2 == \"\"){\r\n                \tipEnd2 = 0;\r\n            \t}\r\n            var ipEnd3 = document.all.ipE3.value;\r\n        \tif (ipEnd3 == \"\"){\r\n                \tipEnd3 = 0;\r\n            \t}\r\n            var ipEnd4 = document.all.ipE4.value;\r\n       \t\tif (ipEnd4 == \"\"){\r\n                \tipEnd4 = 0;\r\n            \t}\r\n        var ipEnd  =  ipEnd1+\".\"+ipEnd2+\".\"+ipEnd3+\".\"+ipEnd4;\r\n");
      out.write("        document.all.ipActionForm.ipAddressEnd.value = ipEnd;\r\n\r\n    var beginDate=new Date(document.all.ipOpenBeginTime.value);\r\n    var endDate=new Date(document.all.ipOpenEndTime.value);\r\n    if(beginDate>endDate){\r\n        alert(\"申请开始日期不能在结束日期之后!\");\r\n        return false;\r\n    }\r\n        var ipPro = document.all.ipActionForm.ipProposer.value;\r\n        if (ipPro ==\"\"){\r\n            alert(\"申请人不能为空！\")\r\n            document.all.ipActionForm.ipProposer.focus();\r\n                    return false;\r\n            }\r\n        if(!checkIPC()){\r\n            alert(\"ip起始地址不能大于结束地址!\");\r\n            document.all.ipB1.focus();\r\n            return false;\r\n        }\r\n    document.ipActionForm.submit();\r\n    //alert(\"数据修改成功\");\r\n    //window.close();\r\n    //window.opener.location.reload();\r\n}\r\nfunction checkIPC(){\r\n    if(parseInt(document.all.ipB1.value)>parseInt(document.all.ipE1.value)){\r\n        return false;\r\n    }else if(parseInt(document.all.ipB1.value)<parseInt(document.all.ipE1.value)){\r\n        return true;\r\n    }else{\r\n");
      out.write("        if(parseInt(document.all.ipB2.value)>parseInt(document.all.ipE2.value)){\r\n            return false;\r\n        }else if(parseInt(document.all.ipB2.value)<parseInt(document.all.ipE2.value)){\r\n            return true;\r\n        }else{\r\n            if(parseInt(document.all.ipB3.value)>parseInt(document.all.ipE3.value)){\r\n                return false;\r\n            }else if(parseInt(document.all.ipB3.value)<parseInt(document.all.ipE3.value)){\r\n                return true;\r\n            }else{\r\n                if(parseInt(document.all.ipB4.value)>parseInt(document.all.ipE4.value)){\r\n                    return false;\r\n                }else{\r\n                    return true;\r\n                }\r\n            }\r\n        }\r\n    }\r\n}\r\n\r\nfunction resetMe(){\r\n     document.all.ipB1.value=\"\";\r\n     document.all.ipB2.value=\"\";\r\n     document.all.ipB3.value=\"\";\r\n     document.all.ipB4.value=\"\";\r\n     document.all.ipE1.value=\"\";\r\n     document.all.ipE2.value=\"\";\r\n     document.all.ipE3.value=\"\";\r\n     document.all.ipE4.value=\"\";\r\n");
      out.write("     document.all.ipProposer.value=\"\";\r\n     document.all.ipIsOpen.checked=false;\r\n}\r\n\r\nfunction  mask(obj){\r\nobj.value=obj.value.replace(/[^\\d]/g,'')\r\nkey1=event.keyCode\r\nif  (key1==37  ||  key1==39)\r\n{  obj.blur();\r\nnextip=parseInt(obj.name.substring(3,4));\r\n//alert(nextip);\r\nnextip=key1==37?nextip-1:nextip+1;\r\nnextip=nextip>=5?1:nextip\r\nnextip=nextip<=0?4:nextip\r\neval(\"document.all.ipB\"+nextip+\".focus()\");\r\n}\r\nif(obj.value.length>=3)\r\nif(parseInt(obj.value)>=256  ||  parseInt(obj.value)<=0)\r\n{\r\nalert(parseInt(obj.value)+\" 不是一个有效项目。请指定一个介于0和255之间的数值！\")\r\n\r\nobj.value=\"\"\r\nobj.focus()\r\nreturn  false;\r\n}\r\nelse\r\n{  obj.blur();\r\nnextip=parseInt(obj.name.substr(3,4))+1\r\n//alert(nextip);\r\nnextip=nextip>=5?1:nextip\r\nnextip=nextip<=0?4:nextip\r\neval(\"document.all.ipB\"+nextip+\".focus()\")\r\n\r\n}\r\n}\r\nfunction  mask2(obj){\r\nobj.value=obj.value.replace(/[^\\d]/g,'')\r\nkey1=event.keyCode\r\nif  (key1==37  ||  key1==39)\r\n{  obj.blur();\r\nnextip=parseInt(obj.name.substring(3,4));\r\n//alert(nextip);\r\nnextip=key1==37?nextip-1:nextip+1;\r\n");
      out.write("nextip=nextip>=5?1:nextip\r\nnextip=nextip<=0?4:nextip\r\neval(\"document.all.ipE\"+nextip+\".focus()\");\r\n}\r\nif(obj.value.length>=3)\r\nif(parseInt(obj.value)>=256  ||  parseInt(obj.value)<=0)\r\n{\r\nalert(parseInt(obj.value)+\" 不是一个有效项目。请指定一个介于0和255之间的数值！\")\r\nobj.value=\"\"\r\nobj.focus()\r\nreturn  false;\r\n}\r\nelse\r\n{  obj.blur();\r\nnextip=parseInt(obj.name.substr(3,4))+1\r\n//alert(nextip);\r\nnextip=nextip>=5?1:nextip\r\nnextip=nextip<=0?4:nextip\r\neval(\"document.all.ipE\"+nextip+\".focus()\")\r\n}\r\n}\r\nfunction  mask_c(obj)\r\n{\r\nclipboardData.setData('text',clipboardData.getData('text').replace(/[^\\d]/g,''))\r\n}\r\n\r\n//-->\r\n</script>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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

  private boolean _jspx_meth_html_005fhidden_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_005fhidden_005f0 = (org.apache.struts.taglib.html.HiddenTag) _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleClass_005fproperty_005fnobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    boolean _jspx_th_html_005fhidden_005f0_reused = false;
    try {
      _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005fhidden_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /setup/systemmanager_ip_modi.jsp(126,5) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fhidden_005f0.setStyleClass("inputText");
      // /setup/systemmanager_ip_modi.jsp(126,5) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fhidden_005f0.setProperty("ipAddressBegin");
      int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
      if (_jspx_th_html_005fhidden_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
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
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_005fhidden_005f1 = (org.apache.struts.taglib.html.HiddenTag) _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleClass_005fproperty_005fnobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    boolean _jspx_th_html_005fhidden_005f1_reused = false;
    try {
      _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
      _jspx_th_html_005fhidden_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /setup/systemmanager_ip_modi.jsp(143,5) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fhidden_005f1.setStyleClass("inputText");
      // /setup/systemmanager_ip_modi.jsp(143,5) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fhidden_005f1.setProperty("ipAddressEnd");
      int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
      if (_jspx_th_html_005fhidden_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
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
    org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f0 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
    boolean _jspx_th_html_005ftext_005f0_reused = false;
    try {
      _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005ftext_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /setup/systemmanager_ip_modi.jsp(168,6) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setStyleClass("inputText");
      // /setup/systemmanager_ip_modi.jsp(168,6) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setProperty("ipProposer");
      // /setup/systemmanager_ip_modi.jsp(168,6) name = size type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setSize("20");
      int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
      if (_jspx_th_html_005ftext_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
      _jspx_th_html_005ftext_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005ftext_005f0, _jsp_getInstanceManager(), _jspx_th_html_005ftext_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005fcheckbox_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:checkbox
    org.apache.struts.taglib.html.CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (org.apache.struts.taglib.html.CheckboxTag) _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.get(org.apache.struts.taglib.html.CheckboxTag.class);
    boolean _jspx_th_html_005fcheckbox_005f0_reused = false;
    try {
      _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005fcheckbox_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /setup/systemmanager_ip_modi.jsp(170,6) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fcheckbox_005f0.setProperty("ipIsOpen");
      // /setup/systemmanager_ip_modi.jsp(170,6) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fcheckbox_005f0.setValue("1");
      int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
      if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
      _jspx_th_html_005fcheckbox_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fcheckbox_005f0, _jsp_getInstanceManager(), _jspx_th_html_005fcheckbox_005f0_reused);
    }
    return false;
  }
}
