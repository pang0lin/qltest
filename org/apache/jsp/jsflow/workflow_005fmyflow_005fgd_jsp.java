/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:06:58 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.oa.jsflow.util.FieldValue;
import java.util.*;
import java.sql.ResultSet;
import com.js.util.util.DataSourceBase;

public final class workflow_005fmyflow_005fgd_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.sql.ResultSet");
    _jspx_imports_classes.add("com.js.oa.jsflow.util.FieldValue");
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
      response.setContentType("text/html;charset=GBK");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n");

response.setCharacterEncoding("GBK");
String workId = "1853097";
String recordId = "1852961";
String submitPersonId = "72";
String tableId = "270";
String[][] formTable = new com.js.oa.eform.service.CustomFormBD().getTableIDAndName(tableId);
String[][] tableHead = new com.js.oa.userdb.service.CustomDatabaseBD().getAllField(formTable[0][0]);

DataSourceBase base = new DataSourceBase();
String sql = "SELECT page_content FROM tpage WHERE page_id="+tableId;
String dataSql = "select ";
for(int i=0;i<tableHead.length;i++){
	dataSql += tableHead[i][2]+",";
}
dataSql = dataSql.substring(0,dataSql.length()-1)+" from "+formTable[0][1]+" where "+formTable[0][1]+"_id="+recordId;
String content = "";
try{
	base.begin();
	ResultSet rs = base.executeQuery(sql);
	if(rs.next()){
		content = rs.getString(1);
	}
	rs.close();
	//System.out.println(dataSql);
	rs = base.executeQuery(dataSql);
	if(rs.next()){
		for(int i=0;i<tableHead.length;i++){
			String value = rs.getString(tableHead[i][2])==null?"":rs.getString(tableHead[i][2]);
			if("401".equals(tableHead[i][4])){
				value = recordId;
			}
			String fieldValue = new FieldValue().specialField(new String[]{tableHead[i][2],tableHead[i][4],tableHead[i][5]},value);
			content = content.replace("<STRONG>["+tableHead[i][1]+"]</STRONG>","<span style=\"font-size:12px;\">"+fieldValue+"</span>");
		}
	}
	rs.close();
}catch(Exception e){
	e.printStackTrace();
}finally{
	base.end();
}

      out.write("\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<html>\r\n  <head>\r\n    <title>??????????????????</title>\r\n</style>\r\n  </head>\r\n  <body onload=\"guidang();\">\r\n  \t<div>\r\n\t    <div>");
      out.print(content );
      out.write("</div>\r\n\t    <br />\r\n\t\t");
      out.print(new FieldValue().getOpinion(recordId) );
      out.write("\r\n\t</div>\r\n\t<!-- start -->\r\n    <form  name=\"form4\" action=\"/jsoa/jsflow/workflow_myflow_add_gd.jsp?gd=1&workId=");
      out.print(workId );
      out.write("&submitPersonId=");
      out.print(submitPersonId );
      out.write("&recordId=");
      out.print(recordId );
      out.write("\" method=\"POST\">\r\n    <input type=\"hidden\" name=\"pageContent\"></form>\r\n    <script language=\"javascript\">\r\n\t\tfunction guidang(){\r\n\t\t    form4.pageContent.value = document.body.innerHTML;\r\n\t\t    form4.submit();\r\n\t\t}\r\n\t</script><!-- end -->\r\n  </body>\r\n</html>\r\n");
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
