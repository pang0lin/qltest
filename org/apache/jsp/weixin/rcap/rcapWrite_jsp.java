/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:00:16 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.weixin.rcap;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import com.js.wap.util.WapUtil;
import java.util.Date;
import com.js.oa.relproject.bean.RelProjectBean;
import java.util.List;
import com.js.wap.bean.WapBean;
import com.js.wap.util.DateTools;

public final class rcapWrite_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("java.util.List");
    _jspx_imports_classes.add("com.js.wap.bean.WapBean");
    _jspx_imports_classes.add("java.util.Iterator");
    _jspx_imports_classes.add("java.util.Date");
    _jspx_imports_classes.add("java.util.Map");
    _jspx_imports_classes.add("com.js.wap.util.WapUtil");
    _jspx_imports_classes.add("java.util.Set");
    _jspx_imports_classes.add("com.js.wap.util.DateTools");
    _jspx_imports_classes.add("com.js.oa.relproject.bean.RelProjectBean");
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

String path = request.getContextPath();
String basePath = request.getContextPath();
request.setCharacterEncoding("utf-8");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

String users = "";
String[] users_array = request.getParameterValues("users");
if(users_array!=null && !users_array.equals("")){
	for(int i=0; i<users_array.length; i++){
		users += users_array[i] + ",";
	}
}
String toIDs = request.getParameter("toIDs")!=null&&!request.getParameter("toIDs").equals("")&&!request.getParameter("toIDs").equals("null")?request.getParameter("toIDs"):"";//从页面传回的id
String userNames = request.getParameter("userNames")!=null&&!request.getParameter("userNames").equals("")&&!request.getParameter("userNames").equals("null")?request.getParameter("userNames"):"";//从页面传回的name
String content = request.getParameter("contents")!=null&&!request.getParameter("contents").equals("")&&!request.getParameter("contents").equals("null")?request.getParameter("contents"):"";//从页面传回的content
String userAccounts = request.getParameter("userAccounts")!=null&&!request.getParameter("userAccounts").equals("")&&!request.getParameter("userAccounts").equals("null")?request.getParameter("userAccounts"):"";//从页面传回的name
String title = request.getParameter("title")!=null&&!request.getParameter("title").equals("")&&!request.getParameter("title").equals("null")?request.getParameter("title"):"";//title
String relProject=request.getParameter("relproject")!=null&&!request.getParameter("relproject").equals("null") ? request.getParameter("relproject") : "";
RelProjectBean rpBean =	new RelProjectBean();

List<Object[]> projectList = rpBean.getActiveProject(session.getAttribute(WapUtil.EMP_ID).toString(),
session.getAttribute(WapUtil.EMP_ORG_ID).toString(),
session.getAttribute(WapUtil.ORG_ID_STRING).toString());
if(users!=null && !"".equals(users)){
	String[] users_ = users.split(",");
	for(int i=0; i<users_.length; i++){
	   toIDs = toIDs + users_[i].substring(0,users_[i].indexOf("$")) + ",";
	   userNames = userNames+users_[i].substring(users_[i].indexOf("$") + 1) + ",";
	}
	toIDs = WapUtil.delRepeatUserId(toIDs) + ",";
	userNames = WapUtil.delRepeatUserId(userNames) + ",";
}

int bvalue = 18;
int evalue = 37;
	
String beginTime= request.getParameter("beginTime");
if(null!=beginTime&&!"".equals(beginTime) && !"null".equals(beginTime)){
	bvalue = Integer.valueOf(beginTime);
}

String endTime = request.getParameter("endTime");
if(null!=endTime && !"".equals(endTime) && !"null".equals(beginTime)){
	evalue = Integer.valueOf(endTime);
}

String beginDate = request.getParameter("beginDate");
String endDate = request.getParameter("endDate");


      out.write("\r\n<!DOCTYPE html>\r\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/wap2/commonImport.jsp", out, false);
      out.write("\r\n<HTML>\r\n\t<HEAD>\r\n\t\t<TITLE>新建日程</TITLE>\r\n\t\t<META content=\"text/html; charset=UTF-8\" http-equiv=Content-Type>\r\n\t\t<META name=viewport content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\">\r\n\t\t<META name=apple-touch-fullscreen content=YES>\r\n\t\t<META name=apple-mobile-web-app-capable content=no>\r\n\t\t<!-- 日期控件css -->\r\n\t\t<link href=\"/jsoa/js/weixin/mobiscroll/css/mobiscroll.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t\t<!--<link rel=\"stylesheet\" href=\"/jsoa/css/weixin/font-awesome.min.css\"> -->\r\n\t\t\r\n\t\t<!--比较日期大小-->\r\n\t\t<SCRIPT language=\"javascript\" src=\"");
      out.print(path );
      out.write("/wap2/js/dateCascade.js\"></SCRIPT>\r\n\t\t<!-- 日期控件用到的js -->\r\n\t\t<script type=\"text/javascript\" src=\"/jsoa/js/weixin/common.js\"></script>\r\n\t\t<script type=\"text/javascript\" src=\"/jsoa/js/weixin/mobiscroll/js/mobiscroll.js\"></script>\r\n\t\t<script type=\"text/javascript\">\r\n\t\t\r\n\t\tfunction a1() {\r\n\t\t    //opt1.dayText = 'day';\r\n\t\t    setCss(opt1);\r\n\t\t}\r\n\t\tfunction setCss(o) {\r\n\t\t    $('input:jqmData(role=\"datebox\")').mobiscroll(o);\r\n\t\t}\r\n\t\t$(function () {\r\n\t\t    opt1 = {\r\n\t\t        preset: 'date', //日期\r\n\t\t        theme: 'jqm', //皮肤样式\r\n\t\t        display: 'modal', //显示方式 \r\n\t\t        mode: 'mixed', //日期选择模式\r\n\t\t        dateFormat: 'yy-mm-dd', // 日期输出格式\r\n\t\t        dateOrder: 'yymmdd', //面板中日期排列格式\r\n\t\t        setText: '确定', //确认按钮名称\r\n\t\t        cancelText: '取消',//取消按钮名籍我\r\n\t\t        dayText: '日', //面板中日文字\r\n\t\t        monthText: '月', //面板中月文字\r\n\t\t        yearText: '年', //面板中年文字\r\n\t\t        endYear: 2050, //结束年份\r\n\t\t        hourText: '时',\r\n\t\t        minuteText: '分',\r\n\t\t        secText: '秒'\r\n\t\t    };\r\n\t\t    $('input:jqmData(role=\"datebox\")').mobiscroll(opt1);\r\n");
      out.write("\t\t    //-----------------------\r\n\t\t    var curr = new Date().getFullYear();\r\n\t\t    var opt = {};\r\n\t\t    opt.date = { preset: 'date' };\r\n\t\t    opt.datetime = { preset: 'datetime', minDate: new Date(2012, 3, 10, 9, 22), maxDate: new Date(2014, 7, 30, 15, 44), stepMinute: 5 };\r\n\t\t    opt.time = { preset: 'time' };\r\n\t\t    $('select.changes').bind('change', function () {\r\n\t\t        var demo = $('#demo').val();\r\n\t\t        $(\".demos\").hide();\r\n\t\t        if (!($(\"#demo_\" + demo).length))\r\n\t\t            demo = 'default';\r\n\t\t        $(\"#demo_\" + demo).show();\r\n\t\t        $('#test_' + demo).val('').scroller('destroy').scroller($.extend(opt[$('#demo').val()], { dateOrder: 'yymmdd', theme: $('#theme').val(), mode: $('#mode').val(), display: $('#display').val(), lang: $('#language').val() }));\r\n\t\t    });\r\n\t\t    $('#demo').trigger('change');\r\n\t\t});\r\n\t\t\t    \r\n\t\tfunction init(){\r\n\t\t\t//显示类型字典\r\n\t\t\t//showTypeDict();\r\n\t\t\tvar date2=new Date();\r\n\t\t\tdate2.setDate(date2.getDate());\r\n\t\t\t");

			if(userNames==null||"".equals(userNames)){
				
      out.write("\r\n\t\t\t\tdocument.getElementById(\"beginDate\").value=date2.Format(\"yyyy-MM-dd\");\r\n\t\t\t\tdocument.getElementById(\"endDate\").value=date2.Format(\"yyyy-MM-dd\");\r\n\t\t\t\t");

			}
			
      out.write("\r\n\t\t}\r\n\t\t\r\n\t\tfunction trim(str){//过滤空字符\r\n\t\t\treturn str.replace(/(^\\s*)|(\\s*$)/g, \"\");\r\n\t\t}\r\n\t\t</script>\r\n\t</head>\r\n\r\n\t<body onload=\"init();setHeader('javascript:closeWindow()', '写日程');\" class=\"wapcss\" style=\"overflow:hidden;\">\r\n\t\t<div class=\"form\">\r\n\t\t\t<form name=\"schForm\" id=\"schForm\" action=\"\" method=\"post\">\r\n\t\t\t  \t<input type=\"hidden\" id=\"userId\" name=\"userId\" value=\"");
      out.print(WapUtil.EMP_ID);
      out.write("\" />\r\n\t\t\t\t<input type=\"hidden\" id=\"toIDs\" name=\"toIDs\" value=\"");
      out.print(toIDs );
      out.write("\" />\r\n\t\t\t\t<input type=\"hidden\" id=\"userAccounts\" name=\"userAccounts\" value=\"");
      out.print(userAccounts);
      out.write("\" />\r\n\t\t\t\t<input type=\"hidden\" id=\"userNames\" name=\"userNames\" value=\"");
      out.print(userNames );
      out.write("\" />\r\n\t\t\t\t<input type=\"hidden\" name=\"saveTag\" value=\"YES\"/>\r\n\t\t\t\t<div class=\"item\">\r\n\t             \t<div class=\"content\" style=\"width:98%;\">\r\n\t             \t\t<div style=\"width: 96%; margin: 0 auto;\">\r\n\t               \t\t\t<textarea id=\"title\" style=\"width:100%;\"  name=\"title\" rows=\"3\" placeholder=\"请输入日程主题\">");
      out.print(title );
      out.write("</textarea>\r\n\t               \t\t</div>\r\n\t               \t</div>\r\n\t         \t</div>\r\n\t         \t\r\n               \t<div class=\"item\" >\r\n\t\t            <div class=\"content\"  style=\"width:98%;\">\r\n\t\t            \t<div style=\"width: 96%; margin: 0 auto;\">\r\n\t\t         \t\t\t<textarea id=\"content\" style=\"width:100%;\"  name=\"content\" rows=\"6\" placeholder=\"请输入日程备注\">");
      out.print(content );
      out.write("</textarea>\r\n\t\t            \t</div>\r\n\t\t            </div>\r\n              \t</div>\r\n              \t<div id=\"datePlugin\"></div> \r\n               \t<div class=\"item\">\r\n                \t<div class=\"title\">开始时间：</div>\r\n                  \t<div class=\"content\" >\r\n                  \t  <div class=\"info\">\r\n                    \t<input type=\"text\" data-role=\"datebox\" id=\"beginDate\" readonly=\"readonly\" name=\"beginDate\" style=\"width: 80px; height: 18px; float: left;\" />\r\n                      \t<select name=\"beginTime\" id=\"beginTime\" style=\"width: 60px; height: 18px;\">\r\n\t\t\t\t\t\t\t<option value=\"1\">0:00</option>\r\n\t\t\t\t\t\t\t");

							String timeStr = "";
							int hour = 0;
							int  minute = 0;
							for(int i=2;i<50;i++){
								if(minute == 0){
								minute = minute + 30; 
								timeStr = hour + ":" + minute;
								}else if(minute == 30){
								hour = hour + 1;
								minute = 0;
								timeStr = hour + ":" + minute + "0";
								}else{System.out.println("ERROR!");}
								if(i==bvalue){
									
      out.write("<option value=\"");
      out.print(i );
      out.write("\" selected=\"selected\">");
      out.print(timeStr );
      out.write("</option>");

								}else{
									
      out.write("<option value=\"");
      out.print(i );
      out.write('"');
      out.write('>');
      out.print(timeStr );
      out.write("</option>");

								}
							}
							
      out.write("\r\n\t\t\t\t\t\t</select>\r\n\t\t\t\t\t\t</div>\r\n                  \t</div>\r\n         \t\t</div>\r\n                    \r\n              \t<div class=\"item\">\r\n             \t\t<div class=\"title\">结束时间：</div>\r\n                    <div class=\"content\" >\r\n                      <div class=\"info\">\r\n                   \t    <input type=\"text\" data-role=\"datebox\" readonly=\"readonly\" id=\"endDate\" style=\"width: 80px; height: 18px;float: left;\" name=\"endDate\" />\r\n                   \t\t<select name=\"endTime\" id=\"endTime\" style=\"width: 60px; height: 18px;\">\r\n\t\t\t\t\t\t\t<option value=\"1\">0:00</option>\r\n\t\t\t\t\t\t\t");

							timeStr = "";
							hour = 0;
							minute = 0;
							for(int i=2;i<50;i++){
								if(minute == 0){
									minute = minute + 30; 
									timeStr = hour + ":" + minute;
								}else if(minute == 30){
									hour = hour + 1;
									minute = 0;
									timeStr = hour + ":" + minute + "0";
								}else{System.out.println("ERROR!");}
								
								if(i==evalue){
									
      out.write("<option value=\"");
      out.print(i );
      out.write("\" selected=\"selected\">");
      out.print(timeStr );
      out.write("</option>");

								}else{
									
      out.write("<option value=\"");
      out.print(i );
      out.write('"');
      out.write('>');
      out.print(timeStr );
      out.write("</option>");

								}
							}
							
      out.write("\r\n\t\t\t\t\t\t</select>\r\n\t\t\t\t\t\t</div>\r\n               \t\t</div> \r\n            \t</div>\r\n\t\t\t\t\t\r\n\t\t\t\t\t\r\n\t\t\t\t<div class=\"item\">\r\n\t\t\t     \t<div class=\"title\">相关项目：</div>\r\n\t\t\t\t   \t<div class=\"content\">\r\n\t\t\t\t   \t   <div class=\"info\">\r\n\t\t\t\t    \t<SELECT class=\"select\" id=\"relproject\" name=\"relproject\">\r\n\t\t\t\t\t\t\t<option value=\"-1\"></option>\r\n\t\t\t\t\t\t\t");

							if(projectList!=null && projectList.size()>0){
								Object[] obj;
								for(int i=0; i<projectList.size(); i++){
									obj = (Object[]) projectList.get(i); 
									if((obj[0].toString()).equals(relProject)){
										
      out.write("<option value=\"");
      out.print(obj[0] );
      out.write("\" selected=\"selected\">");
      out.print(obj[1] );
      out.write("</option>");

								   	}else{
								    	
      out.write("<option value=\"");
      out.print(obj[0] );
      out.write('"');
      out.write(' ');
      out.write('>');
      out.print(obj[1] );
      out.write("</option>");

								    }
								} 
							}
							
      out.write("\r\n\t\t\t\t\t\t</SELECT>\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t</div>\r\n\t\t\t   \t</div>\r\n\t\t\t\t\t\r\n\t\t\t  \t<div class=\"item\">\r\n\t\t\t     \t<div class=\"title\">发&nbsp;&nbsp;送&nbsp;&nbsp;至：</div>\r\n\t\t            <div class=\"userlist\">\r\n\t\t              \t<ul class=\"clearfix\" id=\"toPersonList\"></ul>\r\n\t\t          \t</div>\r\n\t\t\t    </div>\r\n\t\t\t</form>\r\n\t\t</div>\r\n\t\t\r\n\t\t<!-- 按钮 -->\r\n\t\t<div class=\"footer\">\r\n\t  \t\t<div class=\"buttons\">\r\n\t  \t\t\t<div class=\"button\" onclick=\"javascript:doSave(this)\">保存并提交</div>\r\n\t  \t\t\t<div id=\"closeWindow\" class=\"button gray\" onclick=\"javascript:closeWindow();\">关闭</div>\r\n\t   \t\t</div>\r\n\t   \t</div>\r\n\t   \t\r\n\t\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/weixin/common/weixin_messageReminders.jsp", out, false);
      out.write("\r\n\t\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/weixin/workflow/eform/selectUser.jsp", out, false);
      out.write("\r\n\t</body>\r\n\t<script type=\"text/javascript\">\r\n\tvar userContent = \"<li class=\\\"user-add\\\" onclick=\\\"showSelectUsers('toIDs', 'userNames', 'userAccounts', 1, 'user', 'orgPerson', '");
      out.print(session.getAttribute("browseRange"));
      out.write("', 'loadUser();', 'selList')\\\"></li><li class=\\\"user-remove\\\" onclick=\\\"removeUsersubmit()\\\"></li>\";\r\n\tfunction loadUser(){\r\n\t\tvar ids = document.getElementById(\"toIDs\").value;\r\n\t    var userName = document.getElementById(\"userNames\").value;\r\n\t    var userAccounts = document.getElementById(\"userAccounts\").value;\r\n\t\tvar completePeopleHtml= \"\";\r\n\t    \r\n\t    if(\"\"!=ids && \"\"!=userName && \"\"!=userAccounts){\r\n\t    \tids = ids.substring(1, ids.length - 1);\r\n\t    \tuserName = userName.substring(0, userName.length - 1);\r\n\t    \tuserAccounts = userAccounts.substring(0, userAccounts.length - 1);\r\n\t\t    var userIdArray = ids.split(\"$$\");\r\n\t\t    var nameArray = userName.split(\",\");\r\n\t\t    var userAccountsArray = userAccounts.split(\",\");\r\n\t    \t\r\n\t\t    //用户头像\r\n\t\t    for(var i=0; i<userIdArray.length; i++){\r\n\t\t       completePeopleHtml += \"<li id='\" + userIdArray[i] + \"_readSelect'><div>\";\r\n\t\t       completePeopleHtml += \"<div style='position: absolute; top: 0px; right: 0px;'>\";\r\n\t\t       completePeopleHtml += \"<img style='cursor:pointer;display:none' id='\" + userIdArray[i] + \"'  border='0' src='/jsoa/images/delete.gif' onclick=javascript:removeUserSelect('$\" + userIdArray[i] + \"$','\" + nameArray[i] + \"','\" + userAccountsArray[i] + \"'); /></div>\";\r\n");
      out.write("\t\t       completePeopleHtml += \"<img style='cursor:pointer;width:30px;height:30px' id='\" + userIdArray[i] + \"_p' border='0' src='/jsoa/weixin/common/getUserAvatar.jsp?userId=\" + userAccountsArray[i] + \"' onDblClick=javascript:removeUserSelect(',\" + userIdArray[i] + \",','\" + nameArray[i] + \"','\" + userAccountsArray[i] + \"'); />\";\r\n\t\t       completePeopleHtml += \"<p class='name'>\" + nameArray[i] + \"</p>\";\r\n\t\t       completePeopleHtml += \"</div></li>\";\r\n\t\t    }\r\n\t    }\r\n\t    \r\n\t\tcompletePeopleHtml += userContent;\r\n\t\tdocument.getElementById(\"toPersonList\").innerHTML = completePeopleHtml;\r\n\t}\r\n\tloadUser();\r\n\t\r\n\t//选择消息接收人\r\n\tfunction selectUsersubmit(){//选择部门\r\n\t    document.getElementById(\"beginDate\").value=document.getElementById(\"beginDateValue\").value\r\n        document.getElementById(\"endDate\").value=document.getElementById(\"endDateValue\").value \r\n\t\tdocument.schForm.action = \"");
      out.print(path);
      out.write("/weixin/rcap/weixin_org_list.jsp\";\r\n\t\tdocument.schForm.submit();\r\n       }\r\n\t\r\n\tfunction removeUsersubmit(){\r\n\t  var img = document.getElementsByTagName(\"img\");\r\n\t  for(var i = 0; i < img.length; i ++){\r\n\t\t  document.getElementById(img[i].id).style.display = '';          \r\n\t\t}\r\n\t}\r\n\t\r\n\tfunction removeUserSelect(empId,empName,userAccounts){\r\n\t\t\r\n\t\tvar parentDiv = document.getElementById(\"toPersonList\"); \r\n\t\tvar childDiv = document.getElementById(empId.substring(1, empId.length - 1) + \"_readSelect\");\r\n\t\tparentDiv.removeChild(childDiv);\r\n\t\t\r\n\t\tvar selectIds = document.getElementById(\"toIDs\").value;\r\n\t\tvar selectName = document.getElementById(\"userNames\").value;\r\n\t\tvar selectAccounts = document.getElementById(\"userAccounts\").value;\r\n\r\n\t\tselectIds = selectIds.replace(empId, \"\");\r\n\t\tselectName = selectName.replace(empName + \",\", \"\");\r\n\t\tif(\"\" != userAccounts){\r\n\t\t\tselectAccounts = selectAccounts.replace(userAccounts + \",\", \"\");\r\n\t\t}\r\n\t\t\r\n\t\tdocument.getElementById(\"toIDs\").value = selectIds;\r\n\t\tdocument.getElementById(\"userNames\").value = selectName;\r\n");
      out.write("\t\tdocument.getElementById(\"userAccounts\").value = selectAccounts;\r\n        // 将删除人员的勾选状态取消\r\n\t\tvar ins = $(\"input[name='users']\");\t// 获取所有人员选项\r\n\t\tvar id;\r\n\t\t// 遍历集合将ID为删除人员的复选框取消选择\r\n\t\tfor(var i=0; i<ins.length; i++){\r\n\t\t\tid = ins[i].id.split(\"_\")[1];\r\n\t\t\tid = c + id + c;\r\n\t\t\tif(id == empId){\t// ID与需要取消的人员ID相同\r\n\t\t\t\tins[i].checked = \"\";\r\n\t\t\t\tbreak;\r\n\t\t\t}\r\n\t\t}\t\t\r\n\t}\r\n\t\t\r\n\t\r\n\r\n   function compareDateStr(id1,id2){\r\n         var flag=0;\r\n\t\t var sDate = new Date(document.getElementById(id2).value.replace(/\\-/g, \"\\/\"));\r\n\t     var eDate = new Date(document.getElementById(id1).value.replace(/\\-/g, \"\\/\"));\r\n\t\t if(Date.parse(sDate) > Date.parse(eDate)){//正确情况\r\n\t\t\t flag=1;\r\n\t\t }else{\r\n\t\t\t flag=0;\r\n\t\t }\r\n\t\t var s_year=document.getElementById(id2).value.substring(0,4);\r\n\t     var e_year=document.getElementById(id1).value.substring(0,4);\r\n\t\t if(parseInt(s_year)>parseInt(e_year)){\r\n\t\t   flag=1;\r\n\t\t }\r\n\t\t return flag;\r\n\t}\r\n\r\n\tfunction doSave(obj){//保存\r\n\t\t\r\n\t\tvar title = document.getElementById(\"title\").value;\r\n\t\ttitle = trim(title);\r\n");
      out.write("\t\tif(title==null || title==\"\" || title.length>40){\r\n\t\t\tweixinMessageReminder(\"alert\", \"提示：\", \"日程主题不能为空且字符长度不能大于40！\");\r\n\t\t\treturn;\r\n\t\t} \r\n\t\tif('1'==compareDateStr(\"endDate\",\"beginDate\")){\r\n\t\t    weixinMessageReminder(\"alert\", \"提示：\", \"日程结束日期要大于开始日期！\");\r\n\t\t\treturn;\r\n\t\t}\r\n\t\tvar beginTime = document.getElementById(\"beginTime\");\t\t\r\n\t\tbeginTime = beginTime.value;\r\n\t\tbeginTime = parseInt(beginTime);\t\t\r\n\t\tvar endTime = document.getElementById(\"endTime\");\r\n\t\tendTime = endTime.value;\r\n\t\tendTime = parseInt(endTime);\r\n\t\tvar sDate = new Date(document.getElementById(\"endDate\").value.replace(/\\-/g, \"\\/\"));\r\n\t    var eDate = new Date(document.getElementById(\"beginDate\").value.replace(/\\-/g, \"\\/\"));\r\n\t\tif(Date.parse(sDate)==Date.parse(eDate)){\r\n\t\t\t if(endTime < beginTime){\r\n\t\t\t   weixinMessageReminder(\"alert\", \"提示：\", \"日程结束时间需大于开始时间!\");\r\n\t\t\t   return;\r\n\t\t    }  \r\n\t\t}\r\n\t\t\r\n\t\tvar content = document.getElementById(\"content\");\r\n\t\tcontent = content.value;\t\r\n\t\tif(content.length > 1000){\r\n\t\t\tvar contentMsg = document.getElementById(\"contentMsg\");\r\n");
      out.write("\t\t\tcontentMsg.innerHTML=\"日程内容字符长度不得大于1000!\";\r\n\t\t\treturn;\r\n\t\t}\r\n\t\t/* var userNames=document.getElementById(\"userNames\");\r\n\t\tif(null==userNames.value||\"\"==userNames.value){\r\n\t\t\tshowAlertWindow(\"请选择发送人！\");\r\n\t\t\treturn;\r\n\t\t} */\r\n\t\tdocument.schForm.action = \"");
      out.print(path);
      out.write("/weixin/rcap/rcapSave.jsp\";\r\n\t\tdocument.schForm.submit();\r\n\t\t$(obj).attr(\"onclick\", \"\");\r\n\t}\r\n\t</script>\r\n</html>");
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
