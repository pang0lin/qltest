/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:03:02 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.eform;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.system.util.StaticParam;
import java.net.URLEncoder;
import com.js.util.util.DataSourceBase;

public final class form_005fsetfieldvalue_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("java.net.URLEncoder");
    _jspx_imports_classes.add("com.js.system.util.StaticParam");
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
      response.setContentType("text/xml; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
StringBuffer buffer=new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?><root>");

String sql=request.getParameter("sql");
String para=request.getParameter("para");

if(StaticParam.filterSQL(sql, para)>0){
	String fields=request.getParameter("fields");
	String dataSourceName=request.getParameter("datasource");
	if(dataSourceName==null || "null".equals(dataSourceName) || "".equals(dataSourceName)){
		dataSourceName="system";
	}
	
	String userId=session.getAttribute("userId").toString();
	String orgId=session.getAttribute("orgId").toString();
	String userAccount=session.getAttribute("userAccount").toString();
	String userName=session.getAttribute("userName").toString();
	
	String submitPersonId=request.getParameter("submitPersonId")==null?"":request.getParameter("submitPersonId");
	String submitPersonOrgId="0";
	
	if("".equals(submitPersonId)||"null".equalsIgnoreCase(submitPersonId)){
		submitPersonId=userId;
		submitPersonOrgId=orgId;
	}else{
		submitPersonOrgId=(new com.js.system.service.usermanager.UserBD().getUserOrgId(Long.valueOf(submitPersonId))).toString();
	}
	
	sql=sql.replaceAll("\\@\\$\\@userId\\@\\$\\@",userId);
	sql=sql.replaceAll("\\@\\$\\@orgId\\@\\$\\@",orgId);
	sql=sql.replaceAll("\\@\\$\\@userAccount\\@\\$\\@",userAccount);
	sql=sql.replaceAll("\\@\\$\\@userName\\@\\$\\@",userName);
	
	sql=sql.replaceAll("\\@\\$\\@applyId\\@\\$\\@",submitPersonId);
	sql=sql.replaceAll("\\@\\$\\@applyOrgId\\@\\$\\@",submitPersonOrgId);	
	
	/*
	<root>
	   <data>      
	      <formfield>jst_1230_f3340</formfield>
	      <fetchdata>??????</fetchdata>  0:?????????????????????  1:????????????????????? id???text      
	   </data>   
	</root>
	*/
	
	DataSourceBase base=new DataSourceBase();
	
	java.sql.Connection conn=null;
	java.sql.PreparedStatement pstmt=null;
	
	try{	
		java.sql.ResultSet rs;
		if("system".equals(dataSourceName)){
			conn=base.getDataSource().getConnection();
		}else{
			conn=base.getDataSource(dataSourceName).getConnection();
		}
		
		pstmt=conn.prepareStatement(sql);
		if(null!=para && !"".equals(para)){
			String[] paraArr=para.split(",");
			for(int i=0;i<paraArr.length;i++){
				pstmt.setString((i+1),paraArr[i]);
			}
		}
		
		String[] fieldArr=fields.split(",");
		rs=pstmt.executeQuery();
		if(rs.next()){
			for(int i=0;i<fieldArr.length;i++){
				buffer.append("<data>");
				buffer.append("<formfield>").append(fieldArr[i]).append("</formfield>");
				buffer.append("<fielddata>").append(rs.getString((i+1))==null?"*!KongValue!*":URLEncoder.encode(rs.getString((i+1)), "utf-8")).append("</fielddata>");
				buffer.append("</data>");
			}		
		}
		rs.close();
		pstmt.close();
		conn.close();	
		
	}catch(Exception ex){
		if(conn!=null){
			conn.close();
		}	
		ex.printStackTrace();
	}
}
buffer.append("</root>");
//System.out.println("-------"+buffer.toString());
out.clear();
out.print(buffer.toString());

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
