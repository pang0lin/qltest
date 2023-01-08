/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:46:37 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.wap2.report;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.Date;
import com.js.wap.util.DateTools;
import com.js.oa.scheme.worklog.service.WorkLogBD;
import com.js.oa.scheme.worklog.po.WorkLogPO;

public final class wapModifyReport_005f3g_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"DTD/xhtml1-strict.dtd\">\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n\r\n\r\n\r\n\r\n\r\n");
 
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

      out.write("\r\n<HTML xmlns=\"http://www.w3.org/1999/xhtml\">\r\n<HEAD>\r\n<TITLE>修改汇报</TITLE>\r\n<META content=\"text/html; charset=UTF-8\" http-equiv=Content-Type>\r\n<META name=viewport content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\">\r\n<META name=apple-touch-fullscreen content=YES>\r\n<META name=apple-mobile-web-app-capable content=no>\r\n<LINK rel=apple-touch-icon href=\"");
      out.print(path );
      out.write("/wap2/images/iphone.jpg\">\r\n<LINK rel=stylesheet type=text/css href=\"");
      out.print(path );
      out.write("/wap2/css/main_3g.css\">\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path );
      out.write("/wap2/js/cookie.js\"></SCRIPT>\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path );
      out.write("/wap2/js/util.js\"></SCRIPT>\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path );
      out.write("/wap2/js/frost.js\"></SCRIPT>\r\n<META name=GENERATOR content=\"MSHTML 8.00.6001.19154\">\r\n\r\n<script type=\"text/javascript\">\r\n\r\n\tfunction changeType(type){\r\n\t\tvar t = type.value;\r\n\t\t\r\n\t\tif(t==\"0\"){\r\n\t\t\tvar hort = document.getElementById(\"hort\");\r\n\t\t\thort.innerHTML=\"工时:\";\r\n\t\t\tvar htcon = document.getElementById(\"htcon\");\r\n\t\t\thtcon.innerHTML = createHourSel();\r\n\t\t}else if(t==\"1\"){\r\n\t\t\tvar hort = document.getElementById(\"hort\");\r\n\t\t\thort.innerHTML=\"时间段:\";\r\n\t\t\tvar htcon = document.getElementById(\"htcon\");\r\n\t\t\thtcon.innerHTML = createTimeSel();\r\n\t\t}else{\r\n\t\t\talert(\"error:\" + t);\r\n\t\t}\r\n   \r\n\t}\r\n\r\n\tfunction createHourSel(){\r\n\t\tvar retStr = \"<select name='manhour'>\";\r\n\t\tvar hour = 0;\r\n\t\tvar tag = \"\";\t\t\r\n\t\tfor(var i=0;i<48;i++){\r\n\t\t\thour = hour + 0.5;\r\n\t\t\ttag = hour + \"\";\r\n\t\t\tif(tag.length<3){\r\n\t\t\t\ttag = tag + \".0\";\r\n\t\t\t}\r\n\t\t\tif(i==15){\r\n\t\t\t\tretStr = retStr + \"<option value='\" + hour + \"' selected='selected'>\" + tag + \"小时</option>\";\r\n\t\t\t}else{\r\n\t\t\t\tretStr = retStr + \"<option value='\" + hour + \"'>\" + tag + \"小时</option>\";\r\n\t\t\t}\t\r\n");
      out.write("\t\t}\r\n\t\tretStr = retStr + \"</select>\";\r\n\t\treturn retStr;\r\n\t}\r\n\r\n\tfunction createTimeSel(){\r\n\t\tvar retStr = \"<select name='startTime' id='startTime'>\";\r\n\t\tvar hour = 0;\r\n\t\tvar minute = 0;\r\n\t\tvar timeStr = \"\";\r\n\t\tfor(var i=0;i<48;i++){\r\n\t\t\tif(minute == 0){\r\n\t\t\t\tminute = minute + 30; \r\n\t\t\t\ttimeStr = hour + \":\" + minute;\r\n\t\t\t}else if(minute == 30){\r\n\t\t\t\thour = hour + 1;\r\n\t\t\t\tminute = 0;\r\n\t\t\t\ttimeStr = hour + \":\" + minute + \"0\";\r\n\t\t\t}else{alert(\"ERROR!\");}\r\n\t\t\tif(i==16){\r\n\t\t\t\tretStr = retStr + \"<option value='\"+timeStr +\"' selected='selected'>\"+timeStr+\"</option>\";\r\n\t\t\t}else{\r\n\t\t\t\tretStr = retStr + \"<option value='\"+timeStr +\"'>\"+timeStr+\"</option>\";\r\n\t\t\t}\r\n\t\t\t//retStr = retStr + \"<option value='\"+timeStr +\"'>\"+timeStr+\"</option>\";\r\n\t\t}\r\n\t\tretStr = retStr + \"</select>\";\r\n\t\tretStr = retStr + \"到<select name='endTime' id='endTime'>\";\r\n\t\t\t\t\t\r\n\t\ttimeStr = \"\";\r\n\t\thour = 0;\r\n\t\tminute = 0;\r\n\t\tfor(var i=0;i<48;i++){\r\n\t\t\tif(minute == 0){\r\n\t\t\t\tminute = minute + 30; \r\n\t\t\t\ttimeStr = hour + \":\" + minute;\r\n\t\t\t}else if(minute == 30){\r\n");
      out.write("\t\t\t\thour = hour + 1;\r\n\t\t\t\tminute = 0;\r\n\t\t\t\ttimeStr = hour + \":\" + minute + \"0\";\r\n\t\t\t}else{\r\n\t\t\t\talert(\"ERROR!\");\r\n\t\t\t}\r\n\t\t\tif(i==35){\r\n\t\t\t\tretStr = retStr + \"<option value='\" + timeStr + \"' selected='selected'>\" + timeStr + \"</option>\";\r\n\t\t\t}else{\r\n\t\t\t\tretStr = retStr + \"<option value='\" + timeStr + \"'>\" + timeStr + \"</option>\";\r\n\t\t\t}\r\n\t\t}\r\n\t\tretStr = retStr + \"</select>\";\t\r\n\t\treturn retStr;\r\n\t}\r\n\t\r\n\tfunction compare(str1,str2){\r\n\t\tvar time1 = str1.split(\":\");\r\n\t\tvar time2 = str2.split(\":\");\r\n\t\tvar t1 = Number(time1[0]) + Number(time1[1])/60;\r\n\t\tvar t2 = Number(time2[0]) + Number(time2[1])/60;\r\n\t\tif(t1>t2){\r\n\t\t\treturn true;\r\n\t\t}else{\r\n\t\t\treturn false;\r\n\t\t}\r\n\t}\r\n\t\r\n\tfunction save(){\r\n\t\tvar logType = document.getElementById(\"logType\").value;\r\n\t\tif(logType == \"1\"){\r\n\t\t\tvar startTime = document.getElementById(\"startTime\").value;\r\n\t\t\tvar endTime = document.getElementById(\"endTime\").value;\r\n\t\t\tvar tag = compare(endTime,startTime);\r\n\t\t\tif(tag==false){\r\n\t\t\t\tvar tmsg = document.getElementById(\"tmsg\");\r\n\t\t\t\ttmsg.innerHTML = \"日志内容结束时间需大于开始时间!\";\r\n");
      out.write("\t\t\t\treturn;\r\n\t\t\t}\r\n\t\t}\t\r\n\t\tvar logContent = document.getElementById(\"logContent\").value;\t\r\n\t\tif(logContent==null || logContent==\"\" || logContent.lengt>1000){\r\n\t\t\tvar msg = document.getElementById(\"msg\");\r\n\t\t\tmsg.innerHTML = \"日志内容不能为空且不得大于1000个字符！\";\r\n\t\t\treturn;\r\n\t\t}\r\n\t\t\r\n\t\tdocument.submitForm.submit();\r\n\t}\r\n\r\n</script>\r\n<SCRIPT language=\"javascript\" src=\"");
      out.print(path );
      out.write("/wap2/js/dateCascade.js\"></SCRIPT>\r\n</head>\r\n\r\n<body>\r\n<form name=\"submitForm\" action=\"");
      out.print(path);
      out.write("/wap2/report/wapSaveReport_3g.jsp\" method=\"post\">\r\n<input type=\"hidden\" name=\"action\" value=\"modify\" />\r\n<input type=\"hidden\" name=\"logId\" value=\"");
      out.print(logId );
      out.write("\" />\r\n<input type=\"hidden\" name=\"repUser\" value=\"");
      out.print(repUser );
      out.write("\" />\r\n<input type=\"hidden\" name=\"beginIndex\" value=\"");
      out.print(beginIndex );
      out.write("\" />\r\n<div class=\"main\">\r\n\t\t<div id=\"top\">\r\n\t\t\t<span id=\"lp2\"><div class=\"btn_3\"><a href=\"");
      out.print(path);
      out.write("/wap2/report/wapReadReport_3g.jsp?logId=");
      out.print(logId );
      out.write("&repUser=");
      out.print(repUser );
      out.write("&beginIndex=");
      out.print(beginIndex );
      out.write("\">返回</a></div></span>\r\n\t\t\t<span id=\"title2\"></span>\r\n\t\t</div>\r\n<div class=\"f_1\">修改汇报</div>\r\n<div class=\"box_2\">\r\n\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">日志日期:</div>\r\n\t\t\t\t<div class=\"t2\"><!-- <select><option>");
      out.print(DateTools.getMD(logDate));
      out.write("</option></select> -->\r\n\t\t\t\t<script type=\"text/javascript\">\r\n\t\t\t\t\tdateCascade(\"logDate\",");
      out.print((logDate.getYear()+1900)+"" );
      out.write(',');
      out.print((logDate.getMonth()+1)+"" );
      out.write(',');
      out.print(logDate.getDate()+"" );
      out.write(",\"y\");\r\n\t\t\t\t</script>\r\n\t\t\t\t</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t</div>\r\n\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">日志类型:</div>\r\n\t\t\t\t<div class=\"t2\"><select id=\"logType\" name=\"logType\" onchange=\"changeType(this);\">\r\n\t\t\t\t<option value=\"0\" ");
      out.print("0".equals(logType)?"selected":"" );
      out.write(">全&nbsp;&nbsp;天</option>\r\n\t\t\t\t<option value=\"1\" ");
      out.print("1".equals(logType)?"selected":"" );
      out.write(">时间段</option>\r\n\t\t\t\t</select></div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t</div>\r\n\t");
if("0".equals(logType)){ 
      out.write("\r\n\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\" id=\"hort\">工时:</div>\r\n\t\t\t\t<div class=\"t2\" id=\"htcon\">\r\n\t\t\t\t<select name=\"manhour\">\r\n\t\t\t\t");
float hour = 0;
				for(int i=0;i<48;i++){
					hour = hour + 0.5f;
					if(hour == time){
      out.write("\r\n\t\t\t\t\t\t<option value=\"");
      out.print(hour);
      out.write("\" selected=\"selected\">");
      out.print(hour);
      out.write("小时</option>\r\n\t\t\t\t\t");
}else{
      out.write("\r\n\t\t\t\t\t\t<option value=\"");
      out.print(hour);
      out.write('"');
      out.write('>');
      out.print(hour);
      out.write("小时</option>\r\n\t\t\t\t\t");
}
      out.write("\r\n\t\t\t\t\t\r\n\t\t\t\t");
} 
      out.write("\r\n\t\t\t\t</select></div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t</div>");
}else if("1".equals(logType)){ 
      out.write("\r\n\t\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\" id=\"hort\">时间段:</div>\r\n\t\t\t\t<div class=\"t2\" id=\"htcon\">\r\n\t\t\t\t<select name='startTime' id='startTime'>\r\n\t\t\t\t");
String selected="";
				int hour=0;
				int minute=0;
				String timeStr=""; 
				for(int i=0;i<48;i++){
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
					
      out.write("\r\n\t\t\t\t\t<option value=\"");
      out.print(timeStr );
      out.write('"');
      out.write(' ');
      out.print(selected );
      out.write('>');
      out.print(timeStr );
      out.write("</option>\r\n\t\t\t\t");
selected="";
				} 
      out.write("</select>到<select name='endTime' id='endTime'>\r\n\t\t\t\t\t");
hour=0;
					minute=0;
					selected="";
					timeStr="";
					for(int i=0;i<48;i++){
						if(minute == 0){
							minute = minute + 30; 
							timeStr = hour + ":" + minute;
						}else if(minute == 30){
							hour = hour + 1;
							minute = 0;
							timeStr = hour + ":" + minute + "0";
						}
						if(endTime.equals(timeStr)){
							selected="selected=\"selected\"";
						}
					
      out.write("\r\n\t\t\t\t\t<option value=\"");
      out.print(timeStr );
      out.write('"');
      out.write(' ');
      out.print(selected );
      out.write('>');
      out.print(timeStr );
      out.write("</option>\r\n\t\t\t\t");
selected="";
				} 
      out.write("\r\n\t\t\t\t</select></div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t</div>");
} 
      out.write("\r\n\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">日志内容:</div>\r\n\t\t\t\t<div class=\"t2\">\r\n\t\t\t\t<textarea rows=\"2\" name=\"logContent\" id=\"logContent\">");
      out.print(logContent);
      out.write("</textarea><br />\r\n\t\t\t\t<div id=\"msg\" style=\"color:red\"></div><br />\r\n\t\t\t\t<input class=\"button2\" type=\"button\" value=\"提交\" onclick=\"javascript:save();\" /> </a></div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t</div>\r\n\t</div>\r\n</div>\r\n</form>\r\n</body>\r\n</html>");
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
