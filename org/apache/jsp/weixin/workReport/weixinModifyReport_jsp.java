/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:00:24 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.weixin.workReport;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.Date;
import com.js.wap.util.DateTools;
import com.js.oa.scheme.worklog.service.WorkLogBD;
import com.js.oa.scheme.worklog.po.WorkLogPO;

public final class weixinModifyReport_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.util.Date");
    _jspx_imports_classes.add("com.js.oa.scheme.worklog.po.WorkLogPO");
    _jspx_imports_classes.add("com.js.wap.util.DateTools");
    _jspx_imports_classes.add("com.js.oa.scheme.worklog.service.WorkLogBD");
  }

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
  }

  public void _jspDestroy() {
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
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE html>\r\n\r\n\r\n\r\n\r\n\r\n");
 
request.setCharacterEncoding("UTF-8");
String path = request.getContextPath();
WorkLogBD worklogBD = new WorkLogBD();
Long logId = Long.valueOf(request.getParameter("logId"));

WorkLogPO worklogPO = worklogBD.selectSingleWorkLogPO(logId);

String logType = worklogPO.getLogType();

String logContent = worklogPO.getLogContent();
if(null == logContent)logContent = "";

Date logDate = worklogPO.getLogDate();
Date logWriteDate = worklogPO.getLogWriteDate();
String empName = worklogPO.getEmpName();
String beginIndex=request.getParameter("beginIndex").toString();
String repUser=request.getParameter("repUser").toString();

String beginTime="",endTime="";
float time=0f;
if("0".equals(logType)){
	time=Float.valueOf(worklogPO.getManHour());
}else{
	beginTime=worklogPO.getStartPeriod();
	endTime=worklogPO.getEndPeriod();
}

      out.write("\r\n<HTML>\r\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/wap2/commonImport.jsp", out, false);
      out.write("\r\n\t<HEAD>\r\n\t\t<TITLE>????????????</TITLE>\r\n\t\t<META content=\"text/html; charset=UTF-8\" http-equiv=Content-Type>\r\n\t\t<META name=viewport content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\">\r\n\t\t<META name=apple-touch-fullscreen content=YES>\r\n\t\t<META name=apple-mobile-web-app-capable content=no>\r\n\t\t<META name=GENERATOR content=\"MSHTML 8.00.6001.19154\">\r\n\t\t\r\n\t\t<link rel=\"stylesheet\" href=\"/jsoa/css/weixin/weixin_main.css\">\r\n\t<!-- ????????????css -->\r\n\t\t<link href=\"/jsoa/js/weixin/mobiscroll/css/mobiscroll.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t\t\r\n\t\t<!-- jquery???????????????????????????1.9.1 -->\r\n\t\t<script type=\"text/javascript\" src=\"/jsoa/js/weixin/mobiscroll/js/jquery-1.9.1.min.js\"></script>\r\n\t\t<!-- ?????????????????????js -->\r\n\t\t<script type=\"text/javascript\" src=\"/jsoa/js/weixin/common.js\"></script>\r\n\t\t<script type=\"text/javascript\" src=\"/jsoa/js/weixin/mobiscroll/js/jquery.mobile-1.3.0.min.js\"></script>\r\n\t\t<script type=\"text/javascript\" src=\"/jsoa/js/weixin/mobiscroll/js/mobiscroll.js\"></script>\r\n\t\t\r\n\t\t<script type=\"text/javascript\">\r\n");
      out.write("\t\t\r\n\t\tfunction a1() {\r\n\t\t    //opt1.dayText = 'day';\r\n\t\t    setCss(opt1);\r\n\t\t}\r\n\t\tfunction setCss(o) {\r\n\t\t    $('input:jqmData(role=\"datebox\")').mobiscroll(o);\r\n\t\t}\r\n\t\t$(function () {\r\n\t\t    opt1 = {\r\n\t\t        preset: 'date', //??????\r\n\t\t        theme: 'jqm', //????????????\r\n\t\t        display: 'modal', //???????????? \r\n\t\t        mode: 'mixed', //??????????????????\r\n\t\t        dateFormat: 'yy-mm-dd', // ??????????????????\r\n\t\t        dateOrder: 'yymmdd', //???????????????????????????\r\n\t\t        setText: '??????', //??????????????????\r\n\t\t        cancelText: '??????',//?????????????????????\r\n\t\t        dayText: '???', //??????????????????\r\n\t\t        monthText: '???', //??????????????????\r\n\t\t        yearText: '???', //??????????????????\r\n\t\t        endYear: 2050, //????????????\r\n\t\t        hourText: '???',\r\n\t\t        minuteText: '???',\r\n\t\t        secText: '???'\r\n\t\t    };\r\n\t\t    $('input:jqmData(role=\"datebox\")').mobiscroll(opt1);\r\n\t\t    //-----------------------\r\n\t\t    var curr = new Date().getFullYear();\r\n\t\t    var opt = {};\r\n\t\t    opt.date = { preset: 'date' };\r\n\t\t    opt.datetime = { preset: 'datetime', minDate: new Date(2012, 3, 10, 9, 22), maxDate: new Date(2014, 7, 30, 15, 44), stepMinute: 5 };\r\n");
      out.write("\t\t    opt.time = { preset: 'time' };\r\n\t\t    $('select.changes').bind('change', function () {\r\n\t\t        var demo = $('#demo').val();\r\n\t\t        $(\".demos\").hide();\r\n\t\t        if (!($(\"#demo_\" + demo).length))\r\n\t\t            demo = 'default';\r\n\t\t        $(\"#demo_\" + demo).show();\r\n\t\t        $('#test_' + demo).val('').scroller('destroy').scroller($.extend(opt[$('#demo').val()], { dateOrder: 'yymmdd', theme: $('#theme').val(), mode: $('#mode').val(), display: $('#display').val(), lang: $('#language').val() }));\r\n\t\t    });\r\n\t\t    $('#demo').trigger('change');\r\n\t\t});\r\n\t\t\t    \r\n\t\tfunction init(){\r\n\t\t\t//??????????????????\r\n\t\t\t//showTypeDict();\r\n\t\t\tvar date2=new Date();\r\n\t\t\tdate2.setDate(date2.getDate());\r\n\t\t\tdocument.getElementById(\"logDate\").value=date2.Format(\"yyyy-MM-dd\");\r\n\t\t\t\r\n\t\t}\r\n\t\t\r\n\t\tfunction trim(str){//???????????????\r\n\t\t\treturn str.replace(/(^\\s*)|(\\s*$)/g, \"\");\r\n\t\t}\r\n\t\t</script>\r\n\t\t");

		String loginType2017 = null==session.getAttribute("loginType") ? "" : session.getAttribute("loginType").toString();
		if(!"weixin".equals(loginType2017)){
		  out.print("<script type=\"text/javascript\">window.history.forward(1);</script>");
		}

		
      out.write("\r\n\t</head>\r\n\t<body onload=\"setHeader('javascript:closeWindow()', '????????????');\">\r\n\t\t<div class=\"form\">\r\n\t\t\t<form name=\"submitForm\" action=\"");
      out.print(path);
      out.write("/weixin/workReport/weixinSaveReport.jsp\" method=\"post\">\r\n\t\t\t\t<input type=\"hidden\" name=\"action\" value=\"modify\" />\r\n\t\t\t\t<input type=\"hidden\" name=\"logId\" value=\"");
      out.print(logId );
      out.write("\" />\r\n\t\t\t\t<input type=\"hidden\" name=\"repUser\" value=\"");
      out.print(repUser );
      out.write("\" />\r\n\t\t\t\t<input type=\"hidden\" name=\"beginIndex\" value=\"");
      out.print(beginIndex );
      out.write("\" />\r\n\t\t\t\t<div class=\"item\">\r\n            \t\t<div class=\"content\">\r\n                \t\t<textarea id=\"logContent\" name=\"logContent\" rows=\"6\" placeholder=\"?????????????????????\">");
      out.print(logContent);
      out.write("</textarea>\r\n           \t\t\t</div>\r\n        \t\t</div>\r\n        \t\t\r\n\t\t\t\t<div class=\"item\">\r\n\t\t\t\t\t<div class=\"title\">????????????:</div>\r\n\t\t\t\t\t<div class=\"content\" align=\"left\">\r\n\t\t\t\t\t\t<input type=\"text\" value=\"");
      out.print(logDate.toString().substring(0, 10) );
      out.write("\" data-role=\"datebox\" id=\"logDate\" readonly=\"readonly\" name=\"logDate\" style=\"width: 85px; height: 18px;\" />\r\n\t\t\t\t\t</div>\r\n\t\t\t\t</div>\r\n\r\n\t\t\t\t<div class=\"item\">\r\n\t\t\t\t\t<div class=\"title\">????????????:</div>\r\n\t\t\t\t\t<div class=\"content\" align=\"left\">\r\n\t\t\t\t\t\t<select class=\"select\" id=\"logType\" name=\"logType\" onchange=\"changeType(this);\">\r\n\t\t\t\t\t\t\t<option value=\"0\" ");
      out.print("0".equals(logType)?"selected":"" );
      out.write(">???&nbsp;&nbsp;???</option>\r\n\t\t\t\t\t\t\t<option value=\"1\" ");
      out.print("1".equals(logType)?"selected":"" );
      out.write(">?????????</option>\r\n\t\t\t\t\t\t</select>\r\n\t\t\t\t\t</div>\r\n\t\t\t\t</div>\r\n\r\n\t\t\t\t<div class=\"item\">\r\n\t\t\t\t\t");

					if("0".equals(logType)){
						
      out.write("\r\n\t\t\t\t\t\t<div class=\"title\" id=\"hort\">???&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;???:</div>\r\n\t\t\t\t\t\t<div class=\"content\" id=\"htcon\" align=\"left\" >\r\n\t\t\t\t\t\t\t<select class=\"select\" name=\"manhour\">\r\n\t\t\t\t\t\t\t\t");

								float hour = 0;
								for(int i=0; i<48; i++){
									hour = hour + 0.5f;
									if(hour == time){
										
      out.write("<option value=\"");
      out.print(hour);
      out.write("\" selected=\"selected\">");
      out.print(hour);
      out.write("??????</option>");

									}else{
										
      out.write("<option value=\"");
      out.print(hour);
      out.write('"');
      out.write('>');
      out.print(hour);
      out.write("??????</option>");

									}
								}
								
      out.write("\r\n\t\t\t\t\t\t\t</select>\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t");

					}else if("1".equals(logType)){
						
      out.write("\r\n\t\t\t\t\t\t<div class=\"title\" id=\"hort\">???&nbsp;&nbsp;???&nbsp;&nbsp;???:</div>\r\n\t\t\t\t\t\t<div class=\"content\" id=\"htcon\" align=\"left\">\r\n\t\t\t\t\t\t\t<select   name='startTime' id='startTime'>\r\n\t\t\t\t\t\t\t\t");

								String selected = "";
								int hour = 0;
								int minute = 0;
								String timeStr = ""; 
								for(int i=0; i<48; i++){
									if(minute == 0){
										minute = minute + 30; 
										timeStr = hour + ":" + minute;
									}else if(minute == 30){
										hour = hour + 1;
										minute = 0;
										timeStr = hour + ":" + minute + "0";
									}
									if(beginTime.endsWith(timeStr)){
										selected="selected=\"selected\"";
									}
									
      out.write("<option value=\"");
      out.print(timeStr );
      out.write('"');
      out.write(' ');
      out.print(selected );
      out.write('>');
      out.print(timeStr );
      out.write("</option>");

									selected = "";
								}
								
      out.write("\r\n\t\t\t\t\t\t\t</select>\r\n\t\t\t\t\t\t\t<div style=\"float:left;\">???</div>\r\n\t\t\t\t\t\t\t<select  name='endTime' id='endTime'>\r\n\t\t\t\t\t\t\t\t");

								hour = 0;
								minute = 0;
								selected = "";
								timeStr = "";
								for(int i=0; i<48; i++){
									if(minute == 0){
										minute = minute + 30; 
										timeStr = hour + ":" + minute;
									}else if(minute == 30){
										hour = hour + 1;
										minute = 0;
										timeStr = hour + ":" + minute + "0";
									}
									if(endTime.endsWith(timeStr)){
										selected="selected=\"selected\"";
									}
									
      out.write("\r\n\t\t\t\t\t\t\t\t\t<option value=\"");
      out.print(timeStr );
      out.write('"');
      out.write(' ');
      out.print(selected );
      out.write('>');
      out.print(timeStr );
      out.write("</option>\r\n\t\t\t\t\t\t\t\t\t");

									selected = "";
								}
								
      out.write("\r\n\t\t\t\t\t\t\t</select>\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t");

					}
					
      out.write("\r\n\t\t\t\t</div>\r\n\t       \t</form>\r\n\t  \t</div>\r\n\t  \r\n\t\t<!-- ?????? -->\r\n\t\t<div class=\"footer\">\r\n\t      \t<div class=\"buttons\">\r\n\t      \t\t<div class=\"button\" onclick=\"javascript:save();\">??????</div>\r\n\t      \t\t<div class=\"button gray\" onclick=\"javascript:window.history.back();\">??????</div>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t\r\n\t\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/weixin/common/weixin_messageReminders.jsp", out, false);
      out.write("\r\n\t\t<SCRIPT language=\"javascript\" src=\"");
      out.print(path );
      out.write("/wap2/js/dateCascade.js\"></SCRIPT>\r\n\t\t<script type=\"text/javascript\">\r\n\t\tfunction changeType(type){\r\n\t\t\tvar t = type.value;\r\n\t\t\tif(t==\"0\"){\r\n\t\t\t\tvar hort = document.getElementById(\"hort\");\r\n\t\t\t\thort.innerHTML=\"???&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;???:\";\r\n\t\t\t\tvar htcon = document.getElementById(\"htcon\");\r\n\t\t\t\thtcon.innerHTML = createHourSel();\r\n\t\t\t}else if(t==\"1\"){\r\n\t\t\t\tvar hort = document.getElementById(\"hort\");\r\n\t\t\t\thort.innerHTML=\"???&nbsp;&nbsp;???&nbsp;&nbsp;???:\";\r\n\t\t\t\tvar htcon = document.getElementById(\"htcon\");\r\n\t\t\t\thtcon.innerHTML = createTimeSel();\r\n\t\t\t}else{\r\n\t\t\t\tweixinMessageReminder(\"alert\", \"?????????\", \"error:\" + t, \"\");\r\n\t\t\t}\r\n\t\t}\r\n\r\n\t\tfunction createHourSel(){\r\n\t\t\tvar retStr = \"<select name='manhour'>\";\r\n\t\t\tvar hour = 0;\r\n\t\t\tvar tag = \"\";\t\t\r\n\t\t\tfor(var i=0;i<48;i++){\r\n\t\t\t\thour = hour + 0.5;\r\n\t\t\t\ttag = hour + \"\";\r\n\t\t\t\tif(tag.length<3){\r\n\t\t\t\t\ttag = tag + \".0\";\r\n\t\t\t\t}\r\n\t\t\t\tif(i==15){\r\n\t\t\t\t\tretStr = retStr + \"<option value='\" + hour + \"' selected='selected'>\" + tag + \"??????</option>\";\r\n\t\t\t\t}else{\r\n\t\t\t\t\tretStr = retStr + \"<option value='\" + hour + \"'>\" + tag + \"??????</option>\";\r\n");
      out.write("\t\t\t\t}\t\r\n\t\t\t}\r\n\t\t\tretStr = retStr + \"</select>\";\r\n\t\t\treturn retStr;\r\n\t\t}\r\n\r\n\t\tfunction createTimeSel(){\r\n\t\t\tvar retStr = \"<select name='startTime' id='startTime'>\";\r\n\t\t\tvar hour = 0;\r\n\t\t\tvar minute = 0;\r\n\t\t\tvar timeStr = \"\";\r\n\t\t\tfor(var i=0;i<48;i++){\r\n\t\t\t\tif(minute == 0){\r\n\t\t\t\t\tminute = minute + 30; \r\n\t\t\t\t\ttimeStr = hour + \":\" + minute;\r\n\t\t\t\t}else if(minute == 30){\r\n\t\t\t\t\thour = hour + 1;\r\n\t\t\t\t\tminute = 0;\r\n\t\t\t\t\ttimeStr = hour + \":\" + minute + \"0\";\r\n\t\t\t\t}else{\r\n\t\t\t\t\tweixinMessageReminder(\"alert\", \"?????????\", \"ERROR!\", \"\");\r\n\t\t\t\t}\r\n\t\t\t\tif(i==16){\r\n\t\t\t\t\tretStr = retStr + \"<option value='\"+timeStr +\"' selected='selected'>\"+timeStr+\"</option>\";\r\n\t\t\t\t}else{\r\n\t\t\t\t\tretStr = retStr + \"<option value='\"+timeStr +\"'>\"+timeStr+\"</option>\";\r\n\t\t\t\t}\r\n\t\t\t}\r\n\t\t\tretStr = retStr + \"</select>\";\r\n\t\t\tretStr = retStr + \"???<select name='endTime' id='endTime'>\";\r\n\t\t\t\t\t\t\r\n\t\t\ttimeStr = \"\";\r\n\t\t\thour = 0;\r\n\t\t\tminute = 0;\r\n\t\t\tfor(var i=0;i<48;i++){\r\n\t\t\t\tif(minute == 0){\r\n\t\t\t\t\tminute = minute + 30; \r\n\t\t\t\t\ttimeStr = hour + \":\" + minute;\r\n\t\t\t\t}else if(minute == 30){\r\n");
      out.write("\t\t\t\t\thour = hour + 1;\r\n\t\t\t\t\tminute = 0;\r\n\t\t\t\t\ttimeStr = hour + \":\" + minute + \"0\";\r\n\t\t\t\t}else{\r\n\t\t\t\t\tweixinMessageReminder(\"alert\", \"?????????\", \"ERROR!\", \"\");\r\n\t\t\t\t}\r\n\t\t\t\tif(i==35){\r\n\t\t\t\t\tretStr = retStr + \"<option value='\" + timeStr + \"' selected='selected'>\" + timeStr + \"</option>\";\r\n\t\t\t\t}else{\r\n\t\t\t\t\tretStr = retStr + \"<option value='\" + timeStr + \"'>\" + timeStr + \"</option>\";\r\n\t\t\t\t}\r\n\t\t\t}\r\n\t\t\tretStr = retStr + \"</select>\";\t\r\n\t\t\treturn retStr;\r\n\t\t}\r\n\t\t\r\n\t\tfunction compare(str1,str2){\r\n\t\t\tvar time1 = str1.split(\":\");\r\n\t\t\tvar time2 = str2.split(\":\");\r\n\t\t\tvar t1 = Number(time1[0]) + Number(time1[1])/60;\r\n\t\t\tvar t2 = Number(time2[0]) + Number(time2[1])/60;\r\n\t\t\tif(t1>t2){\r\n\t\t\t\treturn true;\r\n\t\t\t}else{\r\n\t\t\t\treturn false;\r\n\t\t\t}\r\n\t\t}\r\n\t\t\r\n\t\tfunction save(){\r\n\t\t\tvar logType = document.getElementById(\"logType\").value;\r\n\t\t\tif(logType == \"1\"){\r\n\t\t\t\tvar startTime = document.getElementById(\"startTime\").value;\r\n\t\t\t\tvar endTime = document.getElementById(\"endTime\").value;\r\n\t\t\t\tvar tag = compare(endTime,startTime);\r\n\t\t\t\tif(tag==false){\r\n");
      out.write("\t\t\t\t\tweixinMessageReminder(\"alert\", \"?????????\", \"?????????????????????????????????????????????!\", \"\");\r\n\t\t\t\t\treturn;\r\n\t\t\t\t}\r\n\t\t\t}\t\r\n\t\t\tvar logContent = document.getElementById(\"logContent\").value;\t\r\n\t\t\tif(logContent==null || logContent==\"\" || logContent.lengt>1000){\r\n\t\t\t\tweixinMessageReminder(\"alert\", \"?????????\", \"???????????????????????????????????????1000????????????\", \"\");\r\n\t\t\t\treturn;\r\n\t\t\t}\r\n\t\t\tdocument.submitForm.submit();\r\n\t\t}\r\n\t\t</script>\r\n\t</body>\r\n</html>");
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
