/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:41:23 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.gridreport.General;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.util.util.DataSourceBase;

public final class CopyDisplayReport_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("com.js.util.util.DataSourceBase");
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
      response.setContentType("text/html; charset=GBK");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires", 0);

      out.write('\r');
      out.write('\n');
String ShowToolbar = "";
if(request.getParameter("ShowToolbar")!=null){
	ShowToolbar = "<param name='ShowToolbar' value='false'>";
}
System.out.println("session :"+session.getId());
String queryString=request.getQueryString();
String dataString = queryString.substring(queryString.indexOf(".grf")+4);
//创建查询条件
String html = "";
String reportSql = "";
String js = "";
String grfName = request.getParameter("report");
String reportId = "0";
DataSourceBase base = new DataSourceBase();
try{
	base.begin();
	String sql = "SELECT reportId,reportSql FROM oa_report WHERE grfName='"+grfName+"'";
	java.sql.ResultSet rs = base.executeQuery(sql);
	if(rs.next()){
		reportSql = rs.getString(2);
		reportId = rs.getString(1);
	}
	rs.close();
	if(!"0".equals(reportId)){
		sql = "SELECT beReplaceName,beReplaceString FROM oa_reportreplace WHERE reportId="+reportId;
		rs = base.executeQuery(sql);
		while(rs.next()){
			html += "&nbsp;"+rs.getString(1)+"：<input type=\"input\" name=\""+rs.getString(2)+"\" id=\""+rs.getString(2)+"\" value=\"\""+
					" style=\"width:100px;\"/>&nbsp;";
			js += "if(document.getElementById('"+rs.getString(2)+"').value!='')"+
					"{url+='&"+rs.getString(2)+"='+encodeURI(encodeURI(document.getElementById('"+rs.getString(2)+"').value));}\n";
		}
	}
}catch(Exception e){
	e.printStackTrace();
}finally{
	base.end();
}

      out.write("\r\n<head>\r\n\t<title>报表展示</title>\r\n\t<meta http-equiv=\"content-type\" content=\"text/html; charset=GBK\">\r\n\t<script src=\"../CreateControl.js\" type=\"text/javascript\"></script>\r\n    <style type=\"text/css\">\r\n        html,body {\r\n            margin:0;\r\n            height:100%;\r\n            width:100%;\r\n        }\r\n    </style>\r\n    ");
if(!"".equals(js)){ 
      out.write("\r\n    <script type=\"text/javascript\">\r\n    \tfunction searchUrl(){\r\n    \t\turl = \"/jsoa/gridreport/General/DisplayReport.jsp?data=data/xmlData.jsp&report=");
      out.print(grfName );
      out.write("\";\r\n    \t\t");
      out.print(js );
      out.write("\r\n    \t\tlocation.href = url;\r\n    \t}\r\n    </script>");
} 
      out.write("\r\n</head>\r\n<body style=\"margin:0;bgcolor:#FFFFFF;\">\r\n<table height=\"100%\" width=\"100%\">\r\n");
if(!"".equals(html)&&"".equals(ShowToolbar)){ 
      out.write("<tr><td>\r\n<input type=\"hidden\" name=\"data\" value=\"data/xmlData.jsp\">\r\n<input type=\"hidden\" name=\"report\" value=\"");
      out.print(grfName );
      out.write("\">\r\n<span style=\"font-size:12px;\">");
      out.print(html );
      out.write("<input type=\"button\" onclick=\"searchUrl();\" value=\"查询\" /></span>\r\n</td></tr>\r\n");
} 
      out.write("\r\n<tr height=\"100%\"><td>\r\n<script type=\"text/javascript\">\r\n    var Report = \"");
      out.print(grfName );
      out.write("\";\r\n    if (Report == \"null\")\r\n\t\tReport = \"\";\r\n    else if (Report != \"\")\r\n        Report = \"/jsoa/gridreport/grf/\" + Report;\r\n        \r\n    var Data = \"");
      out.print(request.getParameter("data") );
      out.write("\";\r\n    if (Data == \"null\")\r\n\t\tData = \"\";\r\n    else if (Data != \"\")\r\n    \t");
if(!"".equals(reportSql)){
      out.write("Data = \"/jsoa/gridreport/\" + Data+\"?grfName=");
      out.print(grfName+dataString );
      out.write("\";\r\n    \t");
}else{
      out.write("Data = \"\";");
}
      out.write("\r\n    CreateDisplayViewerEx(\"100%\", \"100%\", Report, Data, true, \"");
      out.print(ShowToolbar );
      out.write("\");\r\n    ");
if("".equals(reportSql)){
      out.write("\r\n    //从报表模板中获取到查询SQL，并作为DataURL(xmlSQLParam)的参数\r\n    var QuerySQL = ReportViewer.Report.DetailGrid.Recordset.QuerySQL;\r\n    ReportViewer.DataURL = encodeURI(\"POST::/jsoa/gridreport/data/xmlSQLParam.jsp?sessionId=");
      out.print(session.getId() );
      out.write("&QuerySQL=\" + QuerySQL);");
}
      out.write("\r\n</script></td></tr>\r\n</table>\r\n</body>\r\n</html>\r\n ");
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