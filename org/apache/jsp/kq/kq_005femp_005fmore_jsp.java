/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:43:27 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.kq;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.oa.form.kq.PaiBanUtil;
import com.js.oa.form.kq.KqImportUtil;
import java.util.*;
import java.text.*;
import com.js.system.manager.service.ManagerService;
import com.js.util.util.BrowserJudge;

public final class kq_005femp_005fmore_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("java.text");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.oa.form.kq.KqImportUtil");
    _jspx_imports_classes.add("com.js.util.util.BrowserJudge");
    _jspx_imports_classes.add("com.js.oa.form.kq.PaiBanUtil");
    _jspx_imports_classes.add("com.js.system.manager.service.ManagerService");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody;

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
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.release();
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

String year,month,day;
year=request.getParameter("Year")==null?(request.getAttribute("Year")==null?"":request.getAttribute("Year").toString()):request.getParameter("Year");
month=request.getParameter("Month")==null?(request.getAttribute("Month")==null?"":request.getAttribute("Month").toString()):request.getParameter("Month");
day=request.getParameter("Day")==null?(request.getAttribute("Day")==null?"":request.getAttribute("Day").toString()):request.getParameter("Day");

String kuanggongTitle = "无考勤数据";
Date d = new Date();
if(year==null || "".equals(year)){
	year = String.valueOf(d.getYear()+1900);
}
if(month==null || "".equals(month)){
	month = String.valueOf(d.getMonth()+1);
}
if(day == null || "".equals(day)){
	day = String.valueOf(d.getDate());
}

boolean bflag=false;if(BrowserJudge.isMSIE(request))bflag=true;

int index = 0;
String range = "*0*";
if(session.getAttribute("rang")!=null && !"".equals(session.getAttribute("rang"))){
	range = (String)session.getAttribute("rang");
}
String dateString = request.getAttribute("dateString").toString();
String[] dateStr = dateString.split(",");
Map map = (Map)request.getAttribute("dateMap");
Map dutyMap = (Map)request.getAttribute("dutyMap");
String[] dutyType = {"正常班","<font color='red'>外出</font>","<font color='red'>加班</font>","<font color='red'>请假</font>",
				"<font color='red'>出差</font>","<font color='green'>调班</font>","<font color='green'>放假</font>"};
String[] dutyTypeTitle = {"正常班","外出","加班","请假","出差","调班","放假"};
String empOrgName = request.getAttribute("orgName").toString();
String empName = request.getParameter("empName");
String empId = request.getParameter("empId");
Map dutyShow = (Map)request.getAttribute("dutyShow");

Map<String,String> kqDuty = (Map<String,String>)request.getAttribute("kqDuty");

      out.write("\r\n<head>\r\n<META HTTP-EQUIV=\"pragma\" CONTENT=\"no-cache\"> \r\n<META HTTP-EQUIV=\"Cache-Control\" CONTENT=\"no-cache, must-revalidate\"> \r\n<META HTTP-EQUIV=\"expires\" CONTENT=\"Wed, 26 Feb 1997 08:21:57 GMT\">\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<title>考勤统计</title>\r\n<link href=\"skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject2.css\">\r\n<SCRIPT language=javascript src=\"/jsoa/js/openEndow.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DateObject.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DatePicker.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/editlib.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/tree.js\"></SCRIPT>\r\n<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n<script src=\"/jsoa/kq/showDuty.js\"></script>\r\n</head>\r\n\r\n<body  class=\"MainFrameBox\" >\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td>\r\n");

String local = session.getAttribute("org.apache.struts.action.LOCALE").toString();
SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
SimpleDateFormat dateTimeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String searchDate=request.getAttribute("searchDate")==null?"":request.getAttribute("searchDate").toString();
	
String byear,bmonth,bday,eyear,emonth,eday;
String start_date=request.getAttribute("start_date")==null?"":request.getAttribute("start_date").toString();
String end_date=request.getAttribute("end_date")==null?"":request.getAttribute("end_date").toString();
//System.out.println("-------"+start_date+"   "+end_date);
Date b=null; 
Date e=null; 
if("".equals(start_date)){
	b = new Date();
}else{
	b= format.parse(start_date);
}
byear = String.valueOf(b.getYear()+1900);
bmonth = String.valueOf(b.getMonth()+1);
bday = String.valueOf(b.getDate());
if("".equals(end_date)){
	e = new Date();
}else{
	e= format.parse(end_date);
}
eyear = String.valueOf(e.getYear()+1900);
emonth = String.valueOf(e.getMonth()+1);
eday = String.valueOf(e.getDate());

      out.write('\r');
      out.write('\n');
      //  html:form
      org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag) _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(org.apache.struts.taglib.html.FormTag.class);
      boolean _jspx_th_html_005fform_005f0_reused = false;
      try {
        _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
        _jspx_th_html_005fform_005f0.setParent(null);
        // /kq/kq_emp_more.jsp(104,0) name = action type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_html_005fform_005f0.setAction("KqStatAction.do?action=emp");
        int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
        if (_jspx_eval_html_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write('\r');
            out.write('\n');
            if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
              return;
            out.write('\r');
            out.write('\n');
            if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
              return;
            out.write("\r\n<table width=\"100%\" border=\"0\" id=\"searchtable\"  style=\"display:none;\"  align=\"center\" cellpadding=\"1\" cellspacing=\"1\" class=\"searchbar\">\r\n\t<tr>\r\n\t  <td width=\"50px\">\r\n\t\t&nbsp; 时间：\r\n\t  </td>\r\n\t  <td colspan=\"3\">\r\n\t\t<script language=javascript>\r\n\t\t\tvar dtpDate = createDatePicker(\"start_date\",\"");
            out.print(byear);
            out.write('"');
            out.write(',');
            out.write('"');
            out.print(bmonth);
            out.write('"');
            out.write(',');
            out.write('"');
            out.print(bday);
            out.write("\" );\r\n\t\t</script> 至：\r\n\t\t<script language=javascript>\r\n\t\t\tvar dtpDate = createDatePicker(\"end_date\",\"");
            out.print(eyear);
            out.write('"');
            out.write(',');
            out.write('"');
            out.print(emonth);
            out.write('"');
            out.write(',');
            out.write('"');
            out.print(eday);
            out.write("\" );\r\n\t\t</script>\r\n\t\t<input type=\"checkbox\" ");
            out.print(searchDate.equals("1")?"checked":"");
            out.write(" name=\"searchDate\" value=\"1\" style=\"cursor:pointer\">\r\n\t  </td>\r\n\t  <td align=\"right\" colspan=2>\r\n\t      <input type=\"button\" class=\"btnButton2Font\" onClick=\"searcher();\" value=\"查询\" />\r\n\t\t  <input type=\"button\" class=\"btnButton2Font\" onClick=\"unsearcher();\" value=\"重置\" />\r\n\t  </td>\r\n\t  <td width=\"16\" rowspan=\"2\"></td>\r\n\t</tr>\r\n</table>\r\n");
            int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
            if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
              break;
          } while (true);
        }
        if (_jspx_th_html_005fform_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return;
        }
        _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
        _jspx_th_html_005fform_005f0_reused = true;
      } finally {
        org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fform_005f0, _jsp_getInstanceManager(), _jspx_th_html_005fform_005f0_reused);
      }
      out.write("\r\n<table width=\"100%\" height=\"25\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"inlineBottomLine\">\r\n  <tr>\r\n  <td align=\"left\">\r\n       <div valign=\"bottom\" >&nbsp;&nbsp;&nbsp;\r\n       <span style=\"font-size:15px;\"><b>");
      out.print(empOrgName );
      out.write("</b></span>&nbsp;&nbsp;&nbsp;\r\n       <span style=\"font-size:15px;\"><b>");
      out.print(empName );
      out.write("</b></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\r\n     \t<a href=\"javascript:void(0);getMonth(-1,");
      out.print(Integer.parseInt(month)==1?(Integer.parseInt(year)-1):Integer.parseInt(year));
      out.write(',');
      out.print(Integer.parseInt(month)==1?12:(Integer.parseInt(month)-1));
      out.write(");\" title=\"上月\"><img src=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/images/prev.gif\" border=0 align=\"absmiddle\"/></a>&nbsp;\r\n\t\t  ");
if(local.indexOf("en")!=-1){
		  	out.print(new java.text.SimpleDateFormat("MMMM yyyy",java.util.Locale.US).format((new java.util.Date(Integer.parseInt(year)-1900, Integer.parseInt(month)-1, Integer.parseInt(day)))));
		  }else{
		  	out.print(new java.text.SimpleDateFormat("yyyy年M月",java.util.Locale.CHINA).format((new java.util.Date(Integer.parseInt(year)-1900, Integer.parseInt(month)-1, Integer.parseInt(day)))));
		  }
		  Date thisDate = new Date();
		  int nian = thisDate.getYear()+1900;
		  int yue = thisDate.getMonth()+1;
		  int nextNian = Integer.parseInt(month)==12?(Integer.parseInt(year)+1):Integer.parseInt(year);
		  int nextYue = Integer.parseInt(month)==12?1:(Integer.parseInt(month)+1);
		  //System.out.println("-------"+(nextNian*12+nextYue)+"   "+(nian*12+yue));
		  if((nextNian*12+nextYue)<=(nian*12+yue)){
      out.write("\r\n\t   <a href=\"javascript:void(0);getMonth(1,");
      out.print(nextNian );
      out.write(',');
      out.print(nextYue );
      out.write(");\" title=\"下月\"><img src=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/images/next.gif\"  border=0 align=\"absmiddle\"/></a>&nbsp;\r\n\t  ");
} 
      out.write("\r\n     </div>\r\n  </td>\r\n    <td align=\"right\"><input type=\"button\" class=\"btnButton2Font\" onClick=\"hidserch()\" value=\"查询\" /> \r\n    <input type=\"button\" class=\"btnButton2Font\" onClick=\"fanhui()\" value=\"返回列表\" />  \r\n\t</td>\r\n </tr>\r\n</table>\r\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"listTable\">\r\n     <tr align=\"center\">\r\n\t\t");
for(int i=0;i<dateStr.length;i++){
      out.write("\r\n\t\t<td class=\"listTableHead\" width=\"88px\">");
      out.print(dateStr[i] );
      out.write("</td>\t\r\n\t\t");
} 
      out.write("\r\n\t </tr>\r\n\t");
String listClass="listTableLine1";
      out.write("\r\n\t<tr >\r\n\t");

	KqImportUtil util = new KqImportUtil();
	for(int i=0;i<dateStr.length;i++){
		if(!format.format(new Date()).equals(dateStr[i])){
		String font = "";
		String title = "";
		//请假，外出，出差提示
		String dutyStr = map.get(dateStr[i])==null?"0":map.get(dateStr[i]).toString();
		String[] dutys = dutyStr.split(",");
		for(int t=0;t<dutys.length;t++){
			if(Integer.valueOf(dutys[t])!=0){
				font += dutyType[Integer.valueOf(dutys[t])]+"/";
				title +=dutyTypeTitle[Integer.valueOf(dutys[t])]+"/";
			}else{
				String[][] dutySet = util.getDutySet(empId);
				Calendar dateC = Calendar.getInstance();
				dateC.setTime(format.parse(dateStr[i]));
				boolean flag = true;
				String[] paiBan = new PaiBanUtil().getPaiBan(dutySet, dateStr[i]);
				char[] weekDay = paiBan[6].toCharArray();
				if(weekDay[dateC.get(Calendar.DAY_OF_WEEK)-1]=='1'){
					flag = false;
				}
				if(flag){
					font += "<font color='blue'>休班</font>/";
					title += "休班 ";
				}
			}
		}
		//打卡旷工，早退等提示
		String punchFont = "";
		String punchTitle = "";
		if(dutyMap.get(dateStr[i])==null){
			punchFont = kuanggongTitle;
		}else{
			if(!"正常打卡".equals(dutyMap.get(dateStr[i]).toString())){
				punchFont += "<font color='red'>"+dutyMap.get(dateStr[i]).toString()+"</font>";
			}else{
				punchFont += "<font color='#00aa55'>"+dutyMap.get(dateStr[i]).toString()+"</font>";
			}
			punchTitle += dutyMap.get(dateStr[i]).toString()+"/";
		}
		
		Date kqdutyBegin = null;
		Date kqdutyEnd = null;
		Date[] punchTime = null;
		if(kqDuty.get(dateStr[i])!=null&&dutyShow.get(dateStr[i])!=null){
			String kqDutyStr = kqDuty.get(dateStr[i]).split("</font><br/><div>")[1];
			String[] kqDutyDay = kqDutyStr.substring(0,kqDutyStr.indexOf("</div></div>")).split("&nbsp;到&nbsp;");
			kqdutyBegin = dateTimeformat.parse(kqDutyDay[0]+":00");
			kqdutyEnd = dateTimeformat.parse(kqDutyDay[1]+":00");
			
			String[] dutyShows = (dutyShow.get(dateStr[i])+"").split("打卡时间：");
			punchTime = new Date[dutyShows.length-1];
			for(int j=1;j<dutyShows.length;j++){
				String dateStrP = dutyShows[j].substring(22,dutyShows[j].indexOf("</td></tr>"));
				if(dateStrP.split(":").length<3){
					dateStrP += ":00";
				}
				punchTime[j-1] = dateTimeformat.parse(dateStr[i]+" "+dateStrP);
			}
			if(punchTime[punchTime.length-1].getTime()<kqdutyBegin.getTime()){//请假在打卡之后
				font = punchFont+"/"+font;
				title = punchTitle+title;
			}else{
				font = font+punchFont;
				title = title+punchTitle;
			}
			//System.out.println("--------"+dutyShows[0]+"--"+dutyShows[1]+"--"+dutyShows[2]);
		}else{
			if(!kuanggongTitle.equals(punchFont)){
				font = font+punchFont;
				title = title+punchTitle;
			}else{
				if(font.equals("")){
					font = punchFont;
					title = punchTitle;
				}
			}
		}
		if(font.endsWith("/")){
			font = font.substring(0,font.length()-1);
		}
		if(title.length()>0){
			title = title.substring(0,title.length()-1);
		}else{
			title = dutyMap.get(dateStr[i])==null?kuanggongTitle:dutyMap.get(dateStr[i]).toString();
		}
		int tdWidth = 88;
		if(font.length()>6) tdWidth = font.length()*13;
		
      out.write("\r\n\t\t<td class=\"");
      out.print(listClass);
      out.write("\" nowrap=\"nowrap\" title=\"");
      out.print(title );
      out.write("\" style=\"cursor:pointer;\" align=\"center\"\r\n\t\tonMouseOver=\"javascript:show(this,'");
      out.print(dateStr[i] );
      out.write("',event);\" onMouseOut=\"hide(this,'");
      out.print(dateStr[i] );
      out.write("');\">");
      out.print(font );
      out.write("</td>\r\n\t");
}else{
      out.write("\r\n\t\t<td class=\"");
      out.print(listClass);
      out.write("\" nowrap=\"nowrap\" title=\"暂无同步考勤数据\" style=\"cursor:pointer;\" align=\"center\"\r\n\t\tonMouseOver=\"javascript:show(this,'");
      out.print(dateStr[i] );
      out.write("',event);\" onMouseOut=\"hide(this,'");
      out.print(dateStr[i] );
      out.write("');\">暂无同步数据</td>\r\n\t");
}} 
      out.write("\r\n\t</tr>\r\n</table>\r\n");
for(int i=0;i<dateStr.length;i++){
	if(dutyShow.get(dateStr[i])!=null||kqDuty.get(dateStr[i])!=null){
      out.write("\r\n\t<div id=\"");
      out.print(dateStr[i] );
      out.write("\" style=\"position:absolute;display:none;\">\r\n\t");
String tableWidth = "125";
	if(kqDuty.get(dateStr[i])!=null) tableWidth = "225"; 
      out.write("\r\n\t<input type=\"hidden\" id=\"");
      out.print(dateStr[i] );
      out.write("_width\" value=\"");
      out.print(tableWidth );
      out.write("\"  >\r\n\t\t<table width='");
      out.print(tableWidth );
      out.write("px' border='0' cellpadding='0' cellspacing='1' bgcolor='#C1F0FF'>\r\n\t\t");
if(kqDuty.get(dateStr[i])!=null){
      out.write("\r\n\t\t<tr><td>");
      out.print(kqDuty.get(dateStr[i]).toString() );
      out.write("</td></tr>\r\n\t\t");
}if(dutyShow.get(dateStr[i])!=null){
		//System.out.println("--------"+dutyShow.get(dateStr[i]));
      out.write("\r\n\t\t<tr><td><div><font color=\"green\">打卡：</font></div>");
      out.print(dutyShow.get(dateStr[i]).toString() );
      out.write("</td></tr>\r\n\t\t");
} 
      out.write("\r\n\t\t</table>\r\n\t</div>\r\n\t");
}
}
      out.write("\r\n\t<div id=\"show\" style=\"position:absolute;display:none;\">\r\n\t\t<table width='120' border='0' cellpadding='0' cellspacing='1' bgcolor='#C1F0FF'><tr>\r\n\t\t<td>");
      out.print(kuanggongTitle );
      out.write("</td>\r\n\t\t</tr></table>\r\n\t</div>\r\n\t<div id=\"");
      out.print(format.format(new Date()) );
      out.write("\" style=\"position:absolute;display:none;\">\r\n\t\t<table width='120' border='0' cellpadding='0' cellspacing='1' bgcolor='#C1F0FF'><tr>\r\n\t\t<td>暂无同步考勤数据</td>\r\n\t\t</tr></table>\r\n\t</div>\r\n\r\n</body>\r\n<script language=\"javascript\">\r\nfunction show(obj,flag,event){\r\n\tvar event = event || window.event;\r\n\tvar x = event.clientX+document.body.scrollLeft||event.pageX;\r\n\tvar y = event.clientY+document.body.scrollTop||event.pageY;\r\n\tvar theDiv = document.getElementById(flag);\r\n\tif(!theDiv){\r\n\t\ttheDiv = document.getElementById(\"show\");\r\n\t}\r\n\tvar txt = \"\";\r\n\ttheDiv.style.display=\"\";   \r\n\tobj.style.background=\"#a3c3e6\";\r\n\tvar tableWidth = 120;\r\n\tif(document.getElementById(flag+\"_width\")){\r\n\t\ttableWidth =parseInt(document.getElementById(flag+\"_width\").value);\r\n\t}\t\r\n\tif(x+tableWidth+20>document.body.clientWidth){\r\n\t\ttheDiv.style.left=x-(tableWidth-20);   \r\n\t\ttheDiv.style.top=y+5;\r\n\t}else{\r\n\t\ttheDiv.style.left=x;   \r\n\t\ttheDiv.style.top=y+5;\r\n\t}\r\n}\r\nfunction hide(obj,flag){\r\n\tvar theDiv = document.getElementById(flag);\r\n\tif(!theDiv){\r\n\t\ttheDiv = document.getElementById(\"show\");\r\n");
      out.write("\t}\r\n\ttheDiv.style.display=\"none\";\r\n\tobj.style.background=\"#f6f9fc\"; \r\n}\r\nfunction searcher(){\r\n\tKqLeaveActionForm.submit();\r\n}\r\nfunction hidserch(){\r\n     var stable=document.getElementById(\"searchtable\");\r\n     var sbutton=document.getElementById(\"hiddenSearch\");\r\n     if(stable.style.display==\"none\"){\r\n\t     stable.style.display=\"\";\r\n\t     hiddenSearch.innerHTML=\"关闭查询\";\r\n     }else{\r\n\t     stable.style.display=\"none\";\r\n\t     hiddenSearch.innerHTML=\"查询\";\r\n     }\r\n}\r\nfunction changePanle(cURL){\r\n\tlocation.href=cURL;\r\n}\r\nfunction getMonth(type, year, month){\r\n\tif(type=='0'){\r\n\t\tvar d=new Date();\r\n\t\t");
if(bflag){ 
      out.write("year=d.getYear();");
}else{
      out.write("year=d.getYear()+1900;");
}
      out.write("\r\n\t\tmonth=d.getMonth()+1;\r\n\t}\r\n\tif(month<10){\r\n\t\tmonth=\"0\"+month;\r\n\t}\r\n\t//alert(\"1111\");\r\n\tlocation.href=((window.location.href).substring(0,(window.location.href).indexOf(\"/jsoa\")))+'/jsoa/KqStatAction.do?action=emp&empId=");
      out.print(request.getParameter("empId") );
      out.write("&empName=");
      out.print(request.getParameter("empName") );
      out.write("&Year='+year+'&Month='+month+'&Day=");
      out.print(day);
      out.write("';\r\n}\r\nfunction fanhui(){\r\n\tvar url = \"/jsoa/KqStatAction.do?action=list\";\r\n\t");
if(request.getParameter("pager.offset")!=null){ 
      out.write("url += \"&pager.offset=");
      out.print(request.getParameter("pager.offset") );
      out.write('"');
      out.write(';');
} 
      out.write("\r\n\tlocation.href=((window.location.href).substring(0,(window.location.href).indexOf(\"/jsoa\")))+url;\r\n}\r\n</script>\r\n");
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
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_005fhidden_005f0 = (org.apache.struts.taglib.html.HiddenTag) _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    boolean _jspx_th_html_005fhidden_005f0_reused = false;
    try {
      _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005fhidden_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /kq/kq_emp_more.jsp(105,0) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fhidden_005f0.setProperty("empId");
      // /kq/kq_emp_more.jsp(105,0) name = styleId type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fhidden_005f0.setStyleId("empId");
      int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
      if (_jspx_th_html_005fhidden_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
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
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_005fhidden_005f1 = (org.apache.struts.taglib.html.HiddenTag) _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    boolean _jspx_th_html_005fhidden_005f1_reused = false;
    try {
      _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
      _jspx_th_html_005fhidden_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /kq/kq_emp_more.jsp(106,0) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fhidden_005f1.setProperty("empName");
      // /kq/kq_emp_more.jsp(106,0) name = styleId type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fhidden_005f1.setStyleId("empName");
      int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
      if (_jspx_th_html_005fhidden_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
      _jspx_th_html_005fhidden_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fhidden_005f1, _jsp_getInstanceManager(), _jspx_th_html_005fhidden_005f1_reused);
    }
    return false;
  }
}