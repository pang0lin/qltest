/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:43:56 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.kq.weixinkq;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import java.util.*;
import java.text.*;
import com.js.oa.hr.kq.bean.KqCheckinInfoBean;
import com.js.oa.form.kq.PaiBanUtil;
import com.js.oa.form.kq.KqImportUtil;
import com.js.system.manager.service.ManagerService;
import com.js.util.util.BrowserJudge;

public final class weixin_005fkq_005fnq_005fshow_005fexport_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("java.text");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.oa.form.kq.KqImportUtil");
    _jspx_imports_classes.add("com.js.oa.hr.kq.bean.KqCheckinInfoBean");
    _jspx_imports_classes.add("com.js.util.util.BrowserJudge");
    _jspx_imports_classes.add("com.js.oa.form.kq.PaiBanUtil");
    _jspx_imports_classes.add("com.js.system.manager.service.ManagerService");
    _jspx_imports_classes.add("java.util.ArrayList");
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
      response.setContentType("application/vnd.ms-excel;charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n");

String year,month,day;
year=request.getParameter("Year")==null?(request.getAttribute("Year")==null?"":request.getAttribute("Year").toString()):request.getParameter("Year");
month=request.getParameter("Month")==null?(request.getAttribute("Month")==null?"":request.getAttribute("Month").toString()):request.getParameter("Month");
day=request.getParameter("Day")==null?(request.getAttribute("Day")==null?"":request.getAttribute("Day").toString()):request.getParameter("Day");

String kuanggongTitle = "???????????????";
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
Map map=new HashMap();
map=(Map)request.getAttribute("map");
String empOrgName = request.getAttribute("orgName")+"";
String empName = request.getParameter("empName");
String empId = request.getParameter("empId");
Map dutyShow = (Map)request.getAttribute("dutyShow");

List list=(List)request.getAttribute("list");


      out.write("\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"application/vnd.ms-excel; charset=utf-8\">\r\n");
response.setHeader("Content-disposition", "attachment;filename="+new String("?????????????????????????????????".getBytes("GBK"),"iso8859-1")+".xls");
      out.write("\r\n  <title>??????????????????????????????</title>\r\n</head>\r\n  <body>\r\n  <table border=\"1\">\r\n    <tr>\r\n        <td colspan=\"3\" height=\"30\" align=\"center\"><h2><b>?????????????????????????????????</b></h2></td>\r\n    </tr>\r\n    <tr>\r\n        <td colspan=\"3\" height=\"30\" align=\"left\">");
      out.print(request.getAttribute("orgName") );
      out.write('-');
      out.print(request.getAttribute("empName"));
      out.write("</td>\r\n    </tr>\r\n    <tr>\r\n        <td height=\"30\" align=\"center\" ><b>??????<b></td>\r\n        <td height=\"30\" align=\"center\"><b>??????<b></td>\r\n\t\t<td height=\"30\" align=\"center\"><b>??????<b></td>\t\r\n    </tr>\r\n     ");

      String userId=(String)request.getAttribute("userId");
	  for(int j=0;j<dateStr.length;j++){
    	  List listData=new KqCheckinInfoBean().getWeixinKqByDate(dateStr[j], userId);
    	
      out.write("\r\n       <tr>\r\n     \t<td align=\"left\" height=\"30\">");
      out.print(dateStr[j]);
      out.write("</td>\r\n\t\t");

		   String date="";
		   String weizhi="";
		   if(listData!=null&&listData.size()>0){
		      for(int i=0;i<listData.size();i++){
		        Object[] objss=(Object[])listData.get(i);
		        if(i==0){
		          date+="?????????"+objss[2].toString().substring(10, objss[2].toString().length()-2);
		          weizhi+="?????????"+objss[3]+"</br></br>";
		        }else if(i==listData.size()-1){
		          date+="</br></br>&nbsp;&nbsp;?????????"+objss[2].toString().substring(10, objss[2].toString().length()-2);
		          weizhi+="&nbsp;&nbsp;?????????"+objss[3];
		        }
		      }
		    }else{
		         DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");        
                 String bDate = dateStr[j].toString().substring(0,10); 
                 Date bdate = format1.parse(bDate); 
			     Calendar cal = Calendar.getInstance();
			     cal.setTime(bdate);
			     if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY)
				 {
				     //out.print("?????????");
				     date="?????????";
				     weizhi="";
				 }
				 if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
				 {
				      //out.print("?????????");
				       date="?????????";
				       weizhi="";
				 }
		    
		    }
		    
      out.write("\r\n\t\t    <td align=\"left\" height=\"30\">&nbsp;");
      out.print(date );
      out.write("</td>\r\n\t\t    <td align=\"left\" height=\"30\">&nbsp;");
      out.print(weizhi );
      out.write("</td>\r\n       </tr>\r\n    ");
} 
      out.write("\r\n    </table>\r\n  </body>\r\n</html>\r\n");
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
