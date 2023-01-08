/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:46:10 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.wap2.scheme;

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

public final class writeSchPage_005f3g_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"DTD/xhtml1-strict.dtd\">\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
 
	request.setCharacterEncoding("UTF-8");
	String path = request.getContextPath();
	String selectEmps = "";
	String[] emps = request.getParameterValues("selectEmps");
	
	if(null!=emps && emps.length>0){
		for(String str:emps){
			selectEmps = selectEmps + "," + str;
		}
		selectEmps = selectEmps.substring(1);
	}
	
	String allSelEmps = request.getParameter("allSelEmps");
	String userId = (String)session.getAttribute(WapUtil.EMP_ID);
	allSelEmps = WapUtil.delRepeatUserId(userId + "," + allSelEmps + "," + selectEmps);
	
	String title = request.getParameter("title");
	if(null == title || "null".equals(title))
		title = "";

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
		
	String relProject = request.getParameter("relProject");
	String content = request.getParameter("content");
	if(null == content || "null".equals(content)) content = "";
				
	String empWhere = " and EMP_ID in(" + allSelEmps + ")";
	
	Map<String, String> empMap = WapBean.getWapList("EMP_ID,EMPNAME", "org_employee", empWhere);
	Set<String> empKeySet = empMap.keySet();
	
	RelProjectBean rpBean =	new RelProjectBean();
	List<Object[]> projectList = rpBean.getActiveProject(session.getAttribute(WapUtil.EMP_ID).toString(),
	session.getAttribute(WapUtil.EMP_ORG_ID).toString(),
	session.getAttribute(WapUtil.ORG_ID_STRING).toString());
		
	String attendName = "";
	String msg = "";

      out.write("\r\n<HTML xmlns=\"http://www.w3.org/1999/xhtml\">\r\n<HEAD>\r\n<TITLE>新建日程</TITLE>\r\n<META content=\"text/html; charset=UTF-8\" http-equiv=Content-Type>\r\n<META name=viewport content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\">\r\n<META name=apple-touch-fullscreen content=YES>\r\n<META name=apple-mobile-web-app-capable content=no>\r\n<LINK rel=apple-touch-icon href=\"");
      out.print(path );
      out.write("/wap2/images/iphone.jpg\">\r\n<LINK rel=stylesheet type=text/css href=\"");
      out.print(path );
      out.write("/wap2/css/main_3g.css\">\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path );
      out.write("/wap2/js/cookie.js\"></SCRIPT>\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path );
      out.write("/wap2/js/util.js\"></SCRIPT>\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path );
      out.write("/wap2/js/frost.js\"></SCRIPT>\r\n<SCRIPT language=\"javascript\" src=\"");
      out.print(path );
      out.write("/wap2/js/dateCascade.js\"></SCRIPT>\r\n<META name=GENERATOR content=\"MSHTML 8.00.6001.19154\">\r\n<script type=\"text/javascript\">\r\n\t\r\n\tfunction seleDept(){//选择部门\r\n\t\t\r\n\t\tdocument.schForm.action = \"");
      out.print(path);
      out.write("/wap2/scheme/selectDept_3g.jsp\";\r\n\t\tdocument.schForm.submit();\r\n\t}\r\n\t\r\n\tfunction save(){//保存\r\n\t\t\r\n\t\tvar title = document.getElementById(\"titles\");\r\n\t\ttitle = title.value;\r\n\t\ttitle = trim(title);\r\n\t\tif(title==null || title==\"\" || title.length>40){\r\n\t\t\t\r\n\t\t\tvar titleMsg = document.getElementById(\"titleMsg\");\r\n\t\t\ttitleMsg.innerHTML=\"日程主题不能为空且字符长度不能大于40!\";\r\n\t\t\treturn;\r\n\t\t}\r\n\t\tif(\"beginDate\"==compareDate(\"endDate\",\"beginDate\")){\r\n\t\t\talert(\"日程结束日期要大于开始日期\");\r\n\t\t\treturn;\r\n\t\t}\r\n\t\t\r\n\t\tvar beginTime = document.getElementById(\"beginTime\");\t\t\r\n\t\tbeginTime = beginTime.value;\r\n\t\tbeginTime = parseInt(beginTime);\t\t\r\n\t\tvar endTime = document.getElementById(\"endTime\");\r\n\t\tendTime = endTime.value;\r\n\t\tendTime = parseInt(endTime);\r\n\t\tif(!(endTime > beginTime)){\r\n\t\t\tvar timeMsg = document.getElementById(\"timeMsg\");\r\n\t\t\ttimeMsg.innerHTML=\"日程结束时间需大于开始时间!\";\r\n\t\t\treturn;\r\n\t\t}\r\n\t\t\t\t\r\n\t\tvar content = document.getElementById(\"content\");\r\n\t\tcontent = content.value;\t\r\n\t\tif(content.length > 1000){\r\n\t\t\tvar contentMsg = document.getElementById(\"contentMsg\");\r\n");
      out.write("\t\t\tcontentMsg.innerHTML=\"日程内容字符长度不得大于1000!\";\r\n\t\t\treturn;\r\n\t\t}\r\n\t\t\r\n\t\tdocument.schForm.action = \"");
      out.print(path);
      out.write("/wap2/scheme/saveWapSch_3g.jsp\";\r\n\t\tdocument.schForm.submit();\r\n\t}\r\n\t\r\n\tfunction trim(str){//过滤空字符\r\n\t\treturn str.replace(/(^\\s*)|(\\s*$)/g, \"\");\r\n\t}\r\n\r\n</script>\r\n</head>\r\n\r\n<body>\r\n\t<div class=\"main\">\r\n\t\t<div id=\"top\">\r\n           <SPAN id=lp><DIV class=btn_2><A href=\"");
      out.print(path);
      out.write("/wap2/index_3g.jsp\">桌面</A></DIV></SPAN>\r\n           <A class=btn_1 href=\"");
      out.print(path);
      out.write("/wap/action/WapSchemeAction.do?method=wapSchList\">返回</A>\r\n\t\t\t<span id=\"title2\"></span>\r\n\t\t</div>\r\n\t\t<div class=\"f_1\">新建日程</div>\t\t\r\n\t\t<form name=\"schForm\" id=\"schForm\" action=\"");
      out.print(path);
      out.write("/wap2/scheme/saveWapSch_3g.jsp\" method=\"post\"\">\r\n\t\t<div class=\"box_2\">\r\n\t\t<div class=\"list_2\">\r\n\t\t\t\t\t<div class=\"t1\">日程主题:</div>\r\n\t\t\t\t\t<div class=\"t2\"><input id=\"titles\" type=\"text\" name=\"title\" maxlength=\"40\" value=\"");
      out.print(title );
      out.write("\"/><br/>\r\n\t\t\t\t\t<div id=\"titleMsg\" style=\"color:red;\"></div>\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t<div class=\"clear\"></div>\r\n\t\t</div>\r\n\t\t\t");
Iterator<String> empIt =  empKeySet.iterator();
				String key = "";
				while(empIt.hasNext()){
					key = empIt.next();
					if("".equals(attendName))
						attendName = empMap.get(key);
					else
						attendName = attendName + "," + empMap.get(key);
				}
      out.write("\r\n\t\t<div class=\"list_2\">\r\n\r\n\t\t\t\t\t<div class=\"t1\">发送至:</div>\r\n\t\t\t\t\t<div class=\"t2\">\r\n\t\t\t\t\t<textarea rows=\"2\" name=\"attendName\" onclick=\"javascript:seleDept();\" readonly=\"readonly\">");
      out.print(attendName );
      out.write("</textarea>\r\n\t\t\t\t\t\t<input type=\"hidden\" name=\"orgId\" value=\"$(dept)\"/>\r\n\t\t\t\t\t\t<input type=\"hidden\" name=\"allSelEmps\" value=\"");
      out.print(allSelEmps);
      out.write("\"/>\r\n\t\t\t\t\t\t<input type=\"hidden\" name=\"attendName\" value=\"");
      out.print(attendName );
      out.write("\"/>\r\n\t\t\t\t\t\t<input type=\"hidden\" name=\"attendEmp\" value=\"");
      out.print(allSelEmps );
      out.write("\"/>\r\n\t\t\t\t\t\t<input type=\"hidden\" name=\"saveTag\" value=\"YES\"/>\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t<div class=\"clear\"></div>\r\n\t\t</div>\r\n\t\t<div class=\"list_2\">\r\n\t\t\t\t\t<div class=\"t1\">开始时间:</div>\r\n\t\t\t\t\t<div class=\"t2\">");
String dateStr = DateTools.getMD(new Date()); 
      out.write("\r\n\t\t\t\t\t<script type=\"text/javascript\">\r\n\t\t\t\t\t\tdateCascade(\"beginDate\",\"\",\"\",\"\",\"y\");\r\n\t\t\t\t\t</script>\r\n\t\t\t<select name=\"beginTime\" id=\"beginTime\">\r\n\t\t\t<option value=\"1\">0:00</option>\r\n\t\t\t");
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
      out.write("\r\n\t\t\t\t\t\t<option value=\"");
      out.print(i );
      out.write("\" selected=\"selected\">");
      out.print(timeStr );
      out.write("</option>\r\n\t\t\t\t\t");
}else{
      out.write("\r\n\t\t\t\t\t\t<option value=\"");
      out.print(i );
      out.write('"');
      out.write('>');
      out.print(timeStr );
      out.write("</option>\r\n\t\t\t\t\t");
}
				}
      out.write("\r\n\t\t\t</select></div>\r\n\t\t\t\t\t<div class=\"clear\"></div>\r\n\t\t</div>\r\n\t\t<div class=\"list_2\">\r\n\t\t\t\t\t<div class=\"t1\">结束时间:</div>\r\n\t\t\t\t\t<div class=\"t2\"><script type=\"text/javascript\">\r\n\t\t\t\t\t\tdateCascade(\"endDate\",\"\",\"\",\"\",\"y\");\r\n\t\t\t\t\t</script>\r\n\t\t\t<select name=\"endTime\" id=\"endTime\">\r\n\t\t\t\t<option value=\"1\">0:00</option>\r\n\t\t\t");
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
      out.write("\r\n\t\t\t\t\t\t<option value=\"");
      out.print(i );
      out.write("\" selected=\"selected\">");
      out.print(timeStr );
      out.write("</option>\r\n\t\t\t\t\t");
}else{
      out.write("\r\n\t\t\t\t\t\t<option value=\"");
      out.print(i );
      out.write('"');
      out.write('>');
      out.print(timeStr );
      out.write("</option>\r\n\t\t\t\t\t");
}
				}
      out.write("\r\n\t\t\t</select><br />\r\n\t\t\t<div id=\"timeMsg\" style=\"color:red;\"></div></div>\r\n\t\t\t\t\t<div class=\"clear\"></div>\r\n\t\t</div>\r\n\t\t<div class=\"list_2\">\r\n\t\t\t\t\t<div class=\"t1\">相关项目:</div>\r\n\t\t\t\t\t<div class=\"t2\"><select name=\"relProject\">\r\n\t\t\t<option value=\"-1\"></option>\r\n\t\t\t");
if(projectList!=null && projectList.size()>0){
			     Object[] obj;
			     for(int i=0;i<projectList.size();i++){
			     	obj=(Object[])projectList.get(i); 
			     				     	
			     	if((obj[0].toString()).equals(relProject)){
      out.write("\r\n\t\t\t       \t <option value=\"");
      out.print(obj[0] );
      out.write("\" selected=\"selected\">");
      out.print(obj[1] );
      out.write("</option>\r\n\t\t\t    \t ");
}else{
      out.write("\r\n\t\t\t     \t   <option value=\"");
      out.print(obj[0] );
      out.write('"');
      out.write(' ');
      out.write('>');
      out.print(obj[1] );
      out.write("</option>\r\n\t\t\t     \t");
}
			     } 
			 }
      out.write("\r\n\t\t</select></div>\r\n\t\t\t\t\t<div class=\"clear\"></div>\r\n\t\t</div>\r\n\t\t<div class=\"list_2\">\r\n\t\t\t\t\t<div class=\"t1\">日程备注:</div>\r\n\t\t\t\t\t<div class=\"t2\"><textarea rows=\"3\" id=\"content\" name=\"content\">");
      out.print(content );
      out.write("</textarea><br />\r\n\t\t<div id=\"contentMsg\" style=\"color:red;\"></div></div>\r\n\t\t<br/>\r\n\t\t<div align=\"center\">\r\n\t\t<input type=\"button\"  class=\"button4\" onclick=\"javascript:save();\" value=\"提交\"/>\r\n\t\t</div>\r\n\t\t\t\t\t<div class=\"clear\"></div>\r\n\t\t</div>\r\n\t\t</div></form></div>\r\n</body>\r\n</html>\r\n\r\n\t");
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
