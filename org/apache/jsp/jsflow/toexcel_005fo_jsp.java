/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:07:52 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.HashMap;
import java.util.Map;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public final class toexcel_005fo_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("java.sql.ResultSet");
    _jspx_imports_classes.add("java.util.Map");
    _jspx_imports_classes.add("com.js.util.util.DataSourceBase");
    _jspx_imports_classes.add("java.util.HashMap");
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
      response.setContentType("text/html;charset=GBK");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

if(request.getParameter("field")==null){
    String tableId = request.getParameter("tableid");
	String processId = request.getParameter("processId");
	String title = request.getParameter("title");
	String row = request.getParameter("row");
	String empId = session.getAttribute("userId").toString(); 
	int i = 0;
	DataSourceBase base = new DataSourceBase();
	try{
	    base.begin();
	    if(request.getParameter("id")==null){
		    String check = "select id from jsf_exportexcel where protitle='"+title+"' and createEmp='"+empId+"' and processId='"+processId+"'";
		    ResultSet rs = base.executeQuery(check);
		    if(rs.next()){
		        i=1000;
		    }else{
		        String databaseType=com.js.util.config.SystemCommon.getDatabaseType();
			    String sql = "";
			    if(databaseType.indexOf("mysql")>=0){ 
			        sql = "insert into jsf_exportexcel (protitle,fromTable,getRow,createEmp,processId,subTableId,subTableName,subRow) values ('"+title+"','"+tableId+"','"+row+"','"+empId+"','"+processId+"','','','')";
			    }if(databaseType.indexOf("oracle") >= 0){
			        sql = "insert into jsf_exportexcel values (hibernate_sequence.nextval,'"+title+"','"+tableId+"','"+row+"','"+empId+"','"+processId+"','','','')";
			    }
		        i = base.executeUpdate(sql);
		    }
	    }else{
	       String sql = "update jsf_exportexcel set getRow='"+row+"' where id="+request.getParameter("id");
	       i=base.executeUpdate(sql);
	    }
	    base.end();
	}catch(Exception e){
	    base.end();
	    e.printStackTrace();
	}
	if(i>0){
	    if(i==1000){
	        out.print("2");
	    }else{
	        out.print("1");
	    }
	}else{
	    out.print("0");
	}
}else{
	String field = request.getParameter("field");
	String tableId = request.getParameter("tableId");
	com.js.oa.userdb.service.CustomDatabaseBD bd = new com.js.oa.userdb.service.CustomDatabaseBD();
	String[][] listFields = bd.getAllField(tableId);//取得列表字段
	Map map = new HashMap();
	List showField = new ArrayList();
	List hideField = new ArrayList();
	for(int i=0;i<listFields.length;i++){
	    map.put(listFields[i][2],listFields[i][1]);
	    if(field.indexOf(listFields[i][2]+",")>=0){
	       showField.add(listFields[i][2]);
	    }else{
	       hideField.add(listFields[i][2]);
	    }
	}
	String html = "";
	for(int i=0;i<showField.size();i++){
	   html+="<option value='"+showField.get(i)+"'>"+map.get(showField.get(i))+"</option>";
	}
	html+="[-fenkai-]";
	for(int i=0;i<hideField.size();i++){
       html+="<option value='"+hideField.get(i)+"'>"+map.get(hideField.get(i))+"</option>";
    }
	out.print(html);
}

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