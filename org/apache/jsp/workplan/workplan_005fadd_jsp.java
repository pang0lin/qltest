/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:42:06 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.workplan;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.Calendar;
import java.util.*;
import com.js.util.util.DateHelper;

public final class workplan_005fadd_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("java.util.Calendar");
    _jspx_imports_classes.add("com.js.util.util.DateHelper");
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

int tempy,tempm,tempd;
String tempmstr,tempdstr;

//指定的日期与当天的偏移天数
int dateOffset=0;
String dateOffsetStr=request.getParameter("dateOffset");
if(dateOffsetStr!=null && !"null".equals(dateOffsetStr) && !"".equals(dateOffset)){
	dateOffset=Integer.parseInt(dateOffsetStr);
}

String leaderName=request.getAttribute("leaderName")==null?"":request.getAttribute("leaderName").toString();
String leaderId=request.getAttribute("leaderId")==null?"":request.getAttribute("leaderId").toString();

List leaderList=(List)request.getAttribute("leaderList");

Calendar calendar=Calendar.getInstance();

calendar.add(Calendar.DAY_OF_YEAR, dateOffset);

int currentYear=calendar.get(Calendar.YEAR);
int currentMonth=calendar.get(Calendar.MONTH);
int currentDay=calendar.get(Calendar.DAY_OF_MONTH);
int currentDayOfWeek=calendar.get(Calendar.DAY_OF_WEEK);

String noeStr=DateHelper.date2String(calendar.getTime());

String currentMonday="";
String currentSunday="";

//日期设置为一周的第一天(周一)，取得周一的日期
calendar.add(Calendar.DAY_OF_YEAR, (0-(currentDayOfWeek-2)));
currentMonday=DateHelper.date2String(calendar.getTime());
//取得当年中第几周，以周一为准
int currentWeekOfYear=calendar.get(Calendar.WEEK_OF_YEAR);

//日期设置为一周的第7天周日，取得周日的日期，和当前的年份(年份以周日为准)
calendar.add(Calendar.DAY_OF_YEAR, 6);
currentSunday=DateHelper.date2String(calendar.getTime());
currentYear=calendar.get(Calendar.YEAR);

String workYearMonth;
if(currentWeekOfYear<10){
	workYearMonth=String.valueOf(currentYear)+"0"+String.valueOf(currentWeekOfYear);
}else{
	workYearMonth=String.valueOf(currentYear)+String.valueOf(currentWeekOfYear);
}


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
            out.write("\r\n<head>\r\n<title>工作填报</title>\r\n<script language=\"javascript\" src=\"/jsoa/js/checkForm.js\"></script>\r\n<script src=\"js/js.js\" language=\"javascript\"></script>\r\n<SCRIPT language=\"javascript\" src=\"/jsoa/js/checkText.js\"></SCRIPT>\r\n<link href=\"/jsoa/skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\r\n<link href=\"/jsoa/style/cssmain.css\" rel=\"stylesheet\" type=\"text/css\">\r\n</head>\r\n\r\n<body leftmargin=\"0\" topmargin=\"0\" onload=\"initTable();\"  class=\"MainFrameBox Pupwin\">\r\n<form name=\"frm\" action=\"/jsoa/WorkplanAction.do?action=saveclose\"  method=\"post\">\r\n<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"1\" cellspacing=\"0\" class=\"docBoxNoPanel\">\r\n  <tr>\r\n    <td  valign=\"top\">\r\n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"1\">\t\t\t\t\r\n\t\t\t\t<input type=\"hidden\" name=\"dateOffset\" id=\"dateOffset\" value=\"");
            out.print(dateOffset);
            out.write("\"/>\r\n\t\t\t\t<input type=\"hidden\" name=\"currentYear\" id=\"currentYear\" value=\"");
            out.print(currentYear);
            out.write("\"/>\r\n\t\t\t\t<input type=\"hidden\" name=\"workYearMonth\" id=\"workYearMonth\" value=\"");
            out.print(workYearMonth);
            out.write("\"/>\r\n\t\t\t\t");
if(leaderList==null){ 
            out.write("\r\n\t\t\t\t<input type=\"hidden\" name=\"leaderId\" id=\"leaderId\" value=\"");
            out.print(leaderId );
            out.write("\" /> \r\n\t\t\t\t");
} 
            out.write("\r\n\t\t\t\t\t\t\t\t\r\n\t\t\t\t<tr>\r\n\t\t\t\t  <td width=\"8%\" nowrap>&nbsp;姓名：\r\n\t\t\t\t  ");

				  if(leaderList!=null){
					  
            out.write("\r\n\t\t\t\t\t  <select name=\"leaderId\">\t\t\t\t\t  \r\n\t\t\t\t\t  ");

					  Object[] obj;
					  for(int i=0;i<leaderList.size();i++){
						  obj=(Object[])leaderList.get(i);
						  
            out.write("\r\n\t\t\t\t\t\t  <option value=\"");
            out.print(obj[0] );
            out.write('"');
            out.write('>');
            out.print(obj[1] );
            out.write("</option>\r\n\t\t\t\t\t\t  ");

					  }
					  
            out.write("\r\n\t\t\t\t\t  </select>\r\n\t\t\t\t\t  ");

				  }else{
					  out.print(leaderName);
				  }
				  
            out.write("&nbsp;&nbsp;</td>\t\t\t\t \r\n\t\t\t\t  <td width=\"8%\" nowrap>填报期间:&nbsp;\r\n\t\t\t\t  <img src=\"/jsoa/skin/blue/images/prev.gif\" border=0 align=\"absmiddle\" style=\"cursor:hand;\" onclick=\"toWeek(0);\"/>\r\n\t\t\t\t  ");
            out.print(currentMonday );
            out.write('至');
            out.print(currentSunday );
            out.write("\r\n\t\t\t\t  <img src=\"/jsoa/skin/blue/images/next.gif\"  border=0 align=\"absmiddle\" style=\"cursor:hand;\" onclick=\"toWeek(1);\"/>\r\n\t\t\t\t  </td>\r\n\t\t\t\t  <td align=\"right\" nowrap>\r\n\t\t\t\t  \t<input type=\"button\" class=\"btnButton2Font\" value=\" 增 加 \" onClick=\"insertFieldRow()\" />\r\n\t\t\t\t  \t<input type=\"button\" class=\"btnButton2Font\" value=\" 删 除 \" onClick=\"deleteFieldRow()\" />&nbsp;\t\t\t\t  \r\n\t\t\t\t  </td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t\r\n\t\t\t\t<tr>\r\n\t\t\t\t    <td colspan=\"3\" width=\"98%\">   \r\n\t\t\t\t       <table id=\"workplanTable\" width=\"99%\" border=1> \r\n\t\t\t\t\t\t   <tr>\r\n\t\t\t\t\t\t      <td align=\"left\" style=\"width:180px;\" nowrap><input type=checkbox onclick=\"selectAll(this)\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日期</td>\r\n\t\t\t\t\t\t      <td align=\"center\" width=\"10%\" nowrap>类型</td>\r\n\t\t\t\t\t\t      <td align=\"center\" width=\"10%\" nowrap>状态</td>\r\n\t\t\t\t\t\t      <td align=\"center\" width=\"10%\" nowrap>地点</td>\r\n\t\t\t\t\t\t      <td align=\"center\"  nowrap>内容</td>\r\n\t\t\t\t\t\t      \r\n\t\t\t\t\t\t   </tr>   \r\n\t\t\t\t\t\t   \r\n\t\t\t\t\t\t    \r\n\t\t\t\t   \t\t</table>\r\n\t\t\t\t     </td>\r\n\t\t\t\t   </tr>\r\n\t\t\t\t\r\n\t\t\t\t\r\n\t\t\t\t\r\n");
            out.write("\t\t  </table>\r\n\t\t<br/>\r\n\t\t<table width=\"100%\" border=\"0\">\r\n\t\t\t<tr style=\"display:none\">\r\n\t\t   \t\t<td>\r\n\t\t   \t\t\t<select name=\"workTypeTemp\">\t\t   \t\t\t\t\r\n\t\t      \t\t\t<option value=\"上午\">上午</option>\r\n\t\t      \t\t\t<option value=\"下午\">下午</option>\r\n\t\t   \t\t\t</select>\r\n\t\t   \t\t\t<select name=\"workStatusTemp\">\r\n\t\t   \t\t\t\t");

				  		List list=(List)request.getAttribute("workStatusList");
				  		Object[] obj;
				  		if(list!=null && list.size()>0){
				  			for(int i=0;i<list.size();i++){
				  			obj=(Object[])list.get(i);
				  		
            out.write("\r\n\t\t\t\t  \t\t<option value=\"");
            out.print(obj[1]);
            out.write('"');
            out.write('>');
            out.print(obj[1]);
            out.write("</option>\r\n\t\t\t\t  \t\t");
}
				  		}
            out.write("\r\n\t\t   \t\t\t</select>\r\n\t\t   \t\t\t<select name=\"workYearTemp\">\r\n\t\t   \t\t\t\t");
for(int i=2014;i<2050;i++) {
            out.write("\r\n\t\t   \t\t\t\t<option value=\"");
            out.print(i );
            out.write('"');
            out.write('>');
            out.print(i );
            out.write("</option>\r\n\t\t   \t\t\t\t");
} 
            out.write("\r\n\t\t   \t\t\t</select>\r\n\t\t   \t\t\t<select name=\"workMonthTemp\">\r\n\t\t   \t\t\t\t");

		   				for(int i=1;i<13;i++) {		   					
		   					if(i<10){
		   						tempmstr="0"+i;
		   					}else{
		   						tempmstr=String.valueOf(i);
		   					}
		   				
            out.write("\r\n\t\t   \t\t\t\t<option value=\"");
            out.print(tempmstr );
            out.write('"');
            out.write('>');
            out.print(tempmstr );
            out.write("</option>\r\n\t\t   \t\t\t\t");
} 
            out.write("\r\n\t\t   \t\t\t</select>\r\n\t\t   \t\t\t<select name=\"workDateTemp\">\r\n\t\t   \t\t\t\t");

		   				for(int i=1;i<32;i++) {
		   					if(i<10){
		   						tempdstr="0"+i;
		   					}else{
		   						tempdstr=String.valueOf(i);
		   					}
		   				
            out.write("\r\n\t\t   \t\t\t\t<option value=\"");
            out.print(tempdstr );
            out.write('"');
            out.write('>');
            out.print(tempdstr );
            out.write("</option>\r\n\t\t   \t\t\t\t");
} 
            out.write("\r\n\t\t   \t\t\t</select>\r\n\t\t   \t\t</td>\r\n\t\t    </tr>\r\n\t\t\t<tr>\r\n\t\t\t  <td>\r\n\t\t\t    <input type=\"button\" class=\"btnButton4font\" style=\"cursor:pointer\" onClick=\"saveClose();\" value=\"保存退出\"/>\t\t  \t\t\t\r\n\t\t\t\t<input type=\"button\" class=\"btnButton2font\" style=\"cursor:pointer\" onClick=\"frm.reset();\" value=\"重置\"/>\r\n\t\t\t\t<input type=\"button\" class=\"btnButton2font\" style=\"cursor:pointer\" onClick=\"javascript:window.close();\" value=\"退出\"/>\r\n\t\t\t  </td>\r\n\t\t\t</tr>\r\n\t\t</table>\r\n\t</td>\r\n  </tr>\r\n</table>\r\n</form>\r\n</body>\r\n\r\n");
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
      out.write("\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n<script>\r\nvar currentWeek=new Array();\r\nfor(var i=0;i<7;i++){\r\n\tcurrentWeek[i]=new Array();\r\n}\r\n");

//组织一周内的日期数组
calendar.add(Calendar.DAY_OF_YEAR,1);
for(int i=0;i<7;i++){
	
	calendar.add(Calendar.DAY_OF_YEAR,-1);
	tempy=calendar.get(Calendar.YEAR);
	tempm=calendar.get(Calendar.MONTH)+1;
	tempd=calendar.get(Calendar.DAY_OF_MONTH);
	if(tempm<10){
		tempmstr="0"+tempm;
	}else{
		tempmstr=String.valueOf(tempm);
	}
	if(tempd<10){
		tempdstr="0"+tempd;
	}else{
		tempdstr=String.valueOf(tempd);
	}
	
      out.write("\r\n\tcurrentWeek[");
      out.print((6-i));
      out.write("][0]=\"");
      out.print(tempy);
      out.write("\"+\"-\"+\"");
      out.print(tempmstr);
      out.write("\"+\"-\"+\"");
      out.print(tempdstr);
      out.write("\";\r\n\tcurrentWeek[");
      out.print((6-i));
      out.write("][1]=\"");
      out.print(tempy);
      out.write("\";\r\n\tcurrentWeek[");
      out.print((6-i));
      out.write("][2]=\"");
      out.print(tempmstr);
      out.write("\";\r\n\tcurrentWeek[");
      out.print((6-i));
      out.write("][3]=\"");
      out.print(tempdstr);
      out.write("\";\r\n\t");

}


      out.write("\r\n\r\nfunction initTable(){\r\n\tfor(var i=0;i<7;i++){\r\n\t    insertFieldRow();\r\n\t    insertFieldRowp();\r\n\t}\r\n}\r\n//初始化上午行\r\nfunction insertFieldRow(){\r\n\tvar tableObj = findObj(\"workplanTable\",document);\r\n\t//新插入的行的行数\r\n\tvar currentRowNum=tableObj.rows.length;\r\n\tvar tableObj = tableObj.insertRow(currentRowNum);\t\r\n\t\r\n    var fieldCell1=tableObj.insertCell(0);\r\n\tvar fieldCell2=tableObj.insertCell(1);\r\n\tvar fieldCell3=tableObj.insertCell(2);\r\n\tvar fieldCell4=tableObj.insertCell(3);\t\r\n\tvar fieldCell5=tableObj.insertCell(4);\r\n\t\r\n\tvar str=\"\";\r\n\tvar currentWorkTime=getWorkTime(currentRowNum);\r\n\tstr=\"<input name=workNum id=workNum type=checkbox><input type=hidden name=workTime id=workTime value=\\\"\"+currentWorkTime+\"\\\"/>\"\r\n\t   +getCellWorkTimeSelect(\"workYear\",\"workYearTemp\",currentRowNum,currentWorkTime)+\"&nbsp;\"\r\n\t   +getCellWorkTimeSelect(\"workMonth\",\"workMonthTemp\",currentRowNum,currentWorkTime)+\"&nbsp;\"\r\n\t   +getCellWorkTimeSelect(\"workDate\",\"workDateTemp\",currentRowNum,currentWorkTime);\r\n\t\r\n\tfieldCell1.innerHTML=str;\t\t\r\n\t//fieldCell2.innerHTML=\"上午\";\r\n");
      out.write("\tfieldCell2.innerHTML=getCellWorkSelect(\"workType\",\"workTypeTemp\");\r\n\tfieldCell3.innerHTML=getCellWorkSelect(\"workStatus\",\"workStatusTemp\");\r\n\tfieldCell4.innerHTML=\"<input type=text style=\\\"width:100%\\\" name=workPlace id=workPlace value=\\\"\\\">\";\r\n\tfieldCell5.innerHTML=\"<input type=text style=\\\"width:100%\\\" name=description id=description value=\\\"\\\">\";\r\n\t\r\n\tfieldCell1.style.textAlign=\"left\";\r\n\tfieldCell2.style.textAlign=\"left\";\r\n\tfieldCell3.style.textAlign=\"left\";\r\n\tfieldCell4.style.textAlign=\"left\";\r\n\tfieldCell5.style.textAlign=\"left\";\r\n\t\t\r\n}\r\n//初始化下午的行\r\nfunction insertFieldRowp(){\r\n\tvar tableObj = findObj(\"workplanTable\",document);\r\n\t//新插入的行的行数\r\n\tvar currentRowNum=tableObj.rows.length;\r\n\tvar tableObj = tableObj.insertRow(currentRowNum);\t\r\n\t\r\n    var fieldCell1=tableObj.insertCell(0);\r\n\tvar fieldCell2=tableObj.insertCell(1);\r\n\tvar fieldCell3=tableObj.insertCell(2);\r\n\tvar fieldCell4=tableObj.insertCell(3);\t\r\n\tvar fieldCell5=tableObj.insertCell(4);\r\n\t\r\n\tvar str=\"\";\r\n\tvar currentWorkTime=getWorkTimep(currentRowNum);\r\n");
      out.write("\tstr=\"<input name=workNum id=workNum type=checkbox><input type=hidden name=workTime id=workTime value=\\\"\"+currentWorkTime+\"\\\"/>\"\r\n\t   +getCellWorkTimeSelectp(\"workYear\",\"workYearTemp\",currentRowNum,currentWorkTime)+\"&nbsp;\"\r\n\t   +getCellWorkTimeSelectp(\"workMonth\",\"workMonthTemp\",currentRowNum,currentWorkTime)+\"&nbsp;\"\r\n\t   +getCellWorkTimeSelectp(\"workDate\",\"workDateTemp\",currentRowNum,currentWorkTime);\r\n\t\r\n\tfieldCell1.innerHTML=str;\t\t\r\n\t//fieldCell2.innerHTML=\"下午\";\r\n\tfieldCell2.innerHTML=getCellWorkSelectp(\"workType\",\"workTypeTemp\");\r\n\tfieldCell3.innerHTML=getCellWorkSelect(\"workStatus\",\"workStatusTemp\");\r\n\tfieldCell4.innerHTML=\"<input type=text style=\\\"width:100%\\\" name=workPlace id=workPlace value=\\\"\\\">\";\r\n\tfieldCell5.innerHTML=\"<input type=text style=\\\"width:100%\\\" name=description id=description value=\\\"\\\">\";\r\n\t\r\n\tfieldCell1.style.textAlign=\"left\";\r\n\tfieldCell2.style.textAlign=\"left\";\r\n\tfieldCell3.style.textAlign=\"left\";\r\n\tfieldCell4.style.textAlign=\"left\";\r\n\tfieldCell5.style.textAlign=\"left\";\r\n\t\t\r\n}\r\n\r\n");
      out.write("function findObj(theObj, theDoc){  \r\n\tvar p, i, foundObj;\r\n    if(!theDoc) theDoc = document;  \r\n    if( (p = theObj.indexOf(\"?\")) > 0 && parent.frames.length)  { \r\n\t\ttheDoc = parent.frames[theObj.substring(p+1)].document;    \r\n\t\ttheObj = theObj.substring(0,p);  \r\n\t}  \r\n\tif(!(foundObj = theDoc[theObj]) && theDoc.all) \r\n\t\tfoundObj = theDoc.all[theObj]; \r\n\tfor (i=0; !foundObj && i < theDoc.forms.length; i++)     \r\n\t\tfoundObj = theDoc.forms[i][theObj];  \r\n\tfor(i=0; !foundObj && theDoc.layers && i < theDoc.layers.length; i++)     \r\n\t\tfoundObj = findObj(theObj,theDoc.layers[i].document);  \r\n\tif(!foundObj && document.getElementById)\r\n\t\tfoundObj = document.getElementById(theObj);\r\n    return foundObj;\r\n}\r\nfunction getCellWorkTimeSelect(newObjName,fromObjName,rowNum,currentWorkTime){\r\n\t//取得前面一行的年月日的值,在此基础上加一\r\n\tvar dateIndex=findWorkTimeIndex(currentWorkTime);\r\n\tvar currentVal;\r\n\tif(newObjName==\"workYear\"){\r\n\t\tcurrentVal=currentWeek[dateIndex][1];\r\n\t}else if(newObjName==\"workMonth\"){\r\n\t\tcurrentVal=currentWeek[dateIndex][2];\r\n");
      out.write("\t}else if(newObjName==\"workDate\"){\r\n\t\tcurrentVal=currentWeek[dateIndex][3];\r\n\t}\r\n\t\r\n\tif(Number(currentVal)<10){\r\n\t\tcurrentVal=\"0\"+Number(currentVal);\r\n\t}\r\n\t\r\n\tvar str=\"<select name=\"+newObjName+\">\";\r\n\tvar oSelectObject=eval(\"document.all.\"+fromObjName);\r\n\tfor(var i=0; i<oSelectObject.options.length; i++){\r\n\t\tstr+=\"<option value=\";\r\n\t\tstr+=oSelectObject.options[i].value;\r\n\t\tif(currentVal==oSelectObject.options[i].value){\r\n\t\t\tstr+=\" selected\";\r\n\t\t}\r\n\t\tstr+=\">\";\r\n\t\tstr+=oSelectObject.options[i].text;\r\n\t\tstr+=\"</option>\";\r\n\t}\r\n\tstr+=\"</select>\";\r\n\treturn str;\r\n}\r\n\r\nfunction getCellWorkTimeSelectp(newObjName,fromObjName,rowNum,currentWorkTime){\r\n\t//取得前面一行的年月日的值,在此基础上加一\r\n\tvar dateIndex=findWorkTimeIndex(currentWorkTime);\r\n\tvar currentVal;\r\n\tif(newObjName==\"workYear\"){\r\n\t\tcurrentVal=currentWeek[dateIndex][1];\r\n\t}else if(newObjName==\"workMonth\"){\r\n\t\tcurrentVal=currentWeek[dateIndex][2];\r\n\t}else if(newObjName==\"workDate\"){\r\n\t\tcurrentVal=currentWeek[dateIndex][3];\r\n\t}\r\n\t\r\n\tif(Number(currentVal)<10){\r\n\t\tcurrentVal=\"0\"+Number(currentVal);\r\n");
      out.write("\t}\r\n\t\r\n\tvar str=\"<select name=\"+newObjName+\">\";\r\n\tvar oSelectObject=eval(\"document.all.\"+fromObjName);\r\n\tfor(var i=0; i<oSelectObject.options.length; i++){\r\n\t\tstr+=\"<option value=\";\r\n\t\tstr+=oSelectObject.options[i].value;\r\n\t\tif(currentVal==oSelectObject.options[i].value){\r\n\t\t\tstr+=\" selected\";\r\n\t\t}\r\n\t\tstr+=\">\";\r\n\t\tstr+=oSelectObject.options[i].text;\r\n\t\tstr+=\"</option>\";\r\n\t}\r\n\tstr+=\"</select>\";\r\n\treturn str;\r\n}\r\nfunction getCellWorkSelect(newObjName,fromObjName){\r\n\tvar str=\"<select name=\"+newObjName+\">\";\r\n\tvar oSelectObject=eval(\"document.all.\"+fromObjName);\r\n\tfor(var i=0; i<oSelectObject.options.length; i++){\r\n\t\tstr+=\"<option value=\";\r\n\t\tstr+=oSelectObject.options[i].value;\r\n\t\tif(oSelectObject.options[i].selected){\r\n\t\t\tstr+=\" selected\";\r\n\t\t}\r\n\t\tstr+=\">\";\r\n\t\tstr+=oSelectObject.options[i].text;\r\n\t\tstr+=\"</option>\";\r\n\t}\r\n\tstr+=\"</select>\";\r\n\treturn str;\r\n}\r\n\r\nfunction getCellWorkSelectp(newObjName,fromObjName){\r\n\tvar str=\"<select name=\"+newObjName+\">\";\r\n\tvar oSelectObject=eval(\"document.all.\"+fromObjName);\r\n\tfor(var i=0; i<oSelectObject.options.length; i++){\r\n");
      out.write("\t\tstr+=\"<option value=\";\r\n\t\tstr+=oSelectObject.options[i].value;\r\n\t\tif(oSelectObject.options[1]){\r\n\t\t\tstr+=\" selected\";\r\n\t\t}\r\n\t\tstr+=\">\";\r\n\t\tstr+=oSelectObject.options[i].text;\r\n\t\tstr+=\"</option>\";\r\n\t}\r\n\tstr+=\"</select>\";\r\n\treturn str;\r\n}\r\nfunction getWorkTime(rowNum){\r\n\t//取得前面一行的年月日的值\r\n\tvar workTimeObj=document.getElementsByName(\"workTime\");\r\n\tvar workYearObj=document.getElementsByName(\"workYear\");\r\n\tvar workMonthObj=document.getElementsByName(\"workMonth\");\r\n\tvar workDateObj=document.getElementsByName(\"workDate\");\r\n\t\r\n\tif(workTimeObj.length==0){\r\n\t\treturn currentWeek[0][0];\r\n\t}else{\r\n\t\t//var lastValue=workTimeObj[Number(rowNum)-2].value;\r\n\t\tvar tempm;\r\n\t\tvar tempd;\r\n\t\tif(Number(workMonthObj[Number(rowNum)-2].value)<10){\r\n\t\t\ttempm=\"0\"+Number(workMonthObj[Number(rowNum)-2].value);\r\n\t\t}else{\r\n\t\t\ttempm=workMonthObj[Number(rowNum)-2].value;\r\n\t\t}\r\n\t\tif(Number(workDateObj[Number(rowNum)-2].value)<10){\r\n\t\t\ttempd=\"0\"+Number(workDateObj[Number(rowNum)-2].value);\r\n\t\t}else{\r\n\t\t\ttempd=workDateObj[Number(rowNum)-2].value;\r\n");
      out.write("\t\t}\r\n\t\tvar lastValue=workYearObj[Number(rowNum)-2].value+\"-\"+tempm+\"-\"+tempd;\r\n\t\t\t//alert(lastValue+\"..............\");\t\r\n\t\tvar lastTimeIndex=Number(findWorkTimeIndex(lastValue));\r\n\t\tif(lastTimeIndex<6){\r\n\t\t\tlastTimeIndex+=1;\r\n\t\t}\r\n\t\t\r\n\t\t//alert(currentWeek[lastTimeIndex][0]+\"............\");\r\n\t\treturn currentWeek[lastTimeIndex][0];\r\n\t}\r\n}\r\n\r\nfunction getWorkTimep(rowNum){\r\n\t//取得前面一行的年月日的值\r\n\tvar workTimeObj=document.getElementsByName(\"workTime\");\r\n\tvar workYearObj=document.getElementsByName(\"workYear\");\r\n\tvar workMonthObj=document.getElementsByName(\"workMonth\");\r\n\tvar workDateObj=document.getElementsByName(\"workDate\");\r\n\t\r\n\tif(workTimeObj.length==0){\r\n\t\treturn currentWeek[0][0];\r\n\t}else{\r\n\t\t//var lastValue=workTimeObj[Number(rowNum)-2].value;\r\n\t\tvar tempm;\r\n\t\tvar tempd;\r\n\t\tif(Number(workMonthObj[Number(rowNum)-2].value)<10){\r\n\t\t\ttempm=\"0\"+Number(workMonthObj[Number(rowNum)-2].value);\r\n\t\t}else{\r\n\t\t\ttempm=workMonthObj[Number(rowNum)-2].value;\r\n\t\t}\r\n\t\tif(Number(workDateObj[Number(rowNum)-2].value)<10){\r\n\t\t\ttempd=\"0\"+Number(workDateObj[Number(rowNum)-2].value);\r\n");
      out.write("\t\t}else{\r\n\t\t\ttempd=workDateObj[Number(rowNum)-2].value;\r\n\t\t}\r\n\t\tvar lastValue=workYearObj[Number(rowNum)-2].value+\"-\"+tempm+\"-\"+tempd;\r\n\t\t\t//alert(lastValue+\"..............\");\t\r\n\t\tvar lastTimeIndex=Number(findWorkTimeIndex(lastValue));\r\n\t\tif(lastTimeIndex<6){\r\n\t\t\tlastTimeIndex+=1;\r\n\t\t}\r\n\t\t\r\n\t\t//alert(currentWeek[lastTimeIndex][0]+\"............\");\r\n\t\treturn lastValue;\r\n\t}\r\n}\r\n\r\n\r\nfunction findWorkTimeIndex(val){\r\n\tfor(var i=0;i<7;i++){\r\n\t\tif(val==currentWeek[i][0]){\r\n\t\t\treturn i;\r\n\t\t}\r\n\t}\r\n\treturn 0;\r\n}\r\nfunction toWeek(flag){\r\n\tvar offset=document.getElementById(\"dateOffset\").value;\r\n\tif(flag==0){\r\n\t\toffset=Number(offset)-7;\r\n\t}else{\r\n\t\toffset=Number(offset)+7;\r\n\t}\r\n\tlocation.href=((window.location.href).substring(0,(window.location.href).indexOf(\"/jsoa\")))+\"/jsoa/WorkplanAction.do?action=addplan&dateOffset=\"+offset;\r\n}\r\nfunction saveClose(){\r\n\t//检查是否有重复的数据\r\n\tvar checkResult=checkData();\r\n\tif(\"\"!=checkResult){\r\n\t\tif(confirm(\"日期:\"+checkResult+\"的工作计划重复！\\n点击【确定】原来的计划将被覆盖！\\n点击【取消】重新检查后提交！\")){\r\n\t\t\tdocument.frm.submit();\r\n");
      out.write("\t\t}\r\n\t}else{\r\n\t\tdocument.frm.submit();\r\n\t}\r\n}\r\n//删除一行\r\nfunction deleteFieldRow(){\r\n\tvar tableObj=document.all.workplanTable;\r\n\tvar workNum=document.getElementsByName(\"workNum\");\r\n\tfor(var i=workNum.length-1;i>=0;i--){\r\n\t\tif(workNum[i].checked){\r\n\t\t\ttableObj.deleteRow(i+1);\r\n\t\t}\r\n\t}\r\n}\r\nfunction selectAll(obj){\r\n\tvar ckObj=document.getElementsByName(\"workNum\");\r\n\t\r\n\tfor(var i=0;i<ckObj.length;i++){\r\n\t\tckObj[i].checked=obj.checked;\r\n\t}\r\n}\r\n\r\n/****************************************************Ajax验证数据是否重复*************************************************/\r\n\r\n//采用ajax的方法传递参数并返回值给页面\r\nvar xmlHttpRequest;  \r\nfunction createXMLHttpRequest(){  \r\n\tif (window.XMLHttpRequest) {\r\n\t\txmlHttpRequest = new XMLHttpRequest();\r\n\t} else if (window.ActiveXObject) {\r\n\t\txmlHttpRequest = new ActiveXObject(\"Microsoft.XMLHTTP\");\r\n\t}   \r\n}\r\nvar Utils = new Object();\r\n\r\nUtils.getChildrenByTagName = function(node, tagName) {\t\r\n\tvar ln = node.childNodes.length;\r\n\tvar arr = [];\t\r\n\tfor (var z=0; z<ln; z++) {\r\n\t\tif (node.childNodes[z].nodeName==tagName) arr.push(node.childNodes[z]);\r\n");
      out.write("\t}\r\n\treturn arr;\r\n}\r\nfunction checkData(){\r\n\tvar ckDate=\"\";\r\n\tvar workYearObj=document.getElementsByName(\"workYear\");\r\n\tvar workMonthObj=document.getElementsByName(\"workMonth\");\r\n\tvar workDateObj=document.getElementsByName(\"workDate\");\r\n\tfor(var i=0;i<workYearObj.length;i++){\r\n\t\tckDate+=workYearObj[i].value+\"-\"+workMonthObj[i].value+\"-\"+workDateObj[i].value+\",\";\r\n\t}\r\n\tif(ckDate.length>1){\r\n\t\tckDate=ckDate.substring(0,ckDate.length-1);\r\n\t}\r\n\t\r\n\tvar para=\"action=checkdata&leaderId=\"+document.getElementById(\"leaderId\").value+\"&cdate=\"+ckDate+\"&ccdate=\"+new Date();\r\n\t\t\t\r\n\tcreateXMLHttpRequest();  \r\n\txmlHttpRequest.open(\"POST\",\"/jsoa/WorkplanAction.do\",false); //同步方式处理 \r\n\txmlHttpRequest.setRequestHeader(\"Content-Type\",\"application/x-www-form-urlencoded\");\r\n\t\r\n\txmlHttpRequest.send(para);  //参数\r\n\t\r\n\tvar result=\"\";\t\r\n\tif (xmlHttpRequest.status < 400) { //status==200\r\n\t\t//成功\t\t\r\n\t\tvar root=xmlHttpRequest.responseXML.documentElement;\r\n\t    var message=Utils.getChildrenByTagName(root,\"message\");\r\n\t    \r\n\t    if(message.length>0){\r\n");
      out.write("\t    \tif(message[0].firstChild){\r\n\t    \t\tresult=message[0].firstChild.nodeValue;\r\n\t    \t}\r\n\t    }\r\n\t    \r\n\t}\t\r\n\treturn result;\r\n}\r\n \r\n/**********************************************end*************************************/\r\n\r\n</script>");
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