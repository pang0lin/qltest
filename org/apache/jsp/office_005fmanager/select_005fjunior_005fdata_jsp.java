/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:02:05 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.office_005fmanager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;
import java.util.regex.*;
import com.js.oa.hr.officemanager.service.EmployeeBD;

public final class select_005fjunior_005fdata_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("java.sql");
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("java.util.regex");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.sql");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.oa.hr.officemanager.service.EmployeeBD");
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");

DataSource ds = new com.js.util.util.DataSourceBase().getDataSource();
Connection conn=null;
Statement stmt=null;
ResultSet rs=null;
conn=ds.getConnection();
stmt=conn.createStatement();

StringBuffer buffer=new StringBuffer("<root>");
String type=request.getParameter("type");
String range=request.getParameter("range");
String fromParent=request.getParameter("fromParent");
EmployeeBD empBD=new EmployeeBD();
String domainId = session.getAttribute("domainId")==null?"0":session.getAttribute("domainId").toString();
if("group".equals(type)){
	String userId=session.getAttribute("userId").toString();
	String orgIdString=session.getAttribute("orgIdString").toString();
	String where="";

	//取群组
	//取得用户所在的部门上级部门，判断此部门是否在该组的组织使用范围内
	//取得用户所在的组，判断用户是否在该组的组使用范围内
	if("".equals(orgIdString)){
		//组织Id为0则可以查看所以组
		where=" or 1=1";
	}else{
		orgIdString=orgIdString.substring(1,orgIdString.length()-1);
		String[] orgArr=orgIdString.split("\\$\\$");
		for(int i=0;i<orgArr.length;i++){
			where+=" or rangeorg like '%*"+orgArr[i]+"*%' ";
		}
		rs=stmt.executeQuery("select group_id from org_group where groupuserstring like '%$"+userId+"$%'");
		while(rs.next()){
			where+=" or rangegroup like '%@"+rs.getString(1)+"@%' ";
		}
		rs.close();
	}
	String sql = "";


	String databaseType=com.js.util.config.SystemCommon.getDatabaseType();
	if(databaseType.indexOf("mysql")>=0){
		if("1".equals(fromParent)){
			sql="select group_id,group_name from org_group where '"+range+"' like concat('%@',group_id,'@%') and domain_id="+domainId;
		}else{
		    sql="select group_id,group_name from org_group where ((rangeemp is null and rangeorg is null and rangegroup is null) or (rangeemp='' and rangeorg='' and rangegroup='') or  rangeemp like '%$"+userId+"$%' "+where+" or '"+range+"' like concat('%@',group_id,'@%')) and domain_id="+domainId;
		}

	}else{
		if("1".equals(fromParent)){
			sql="select group_id,group_name from org_group where '"+range+"' like jsdb.FN_LINKCHAR(jsdb.FN_LINKCHAR('%@',group_id),'@%') and domain_id="+domainId;
		}else{
			sql="select group_id,group_name from org_group where ((rangeemp is null and rangeorg is null and rangegroup is null) or (rangeemp='' and rangeorg='' and rangegroup='') or  rangeemp like '%$"+userId+"$%' "+where+" or '"+range+"' like jsdb.FN_LINKCHAR(jsdb.FN_LINKCHAR('%@',group_id),'@%')) and domain_id="+domainId;
		}

	}

	rs=stmt.executeQuery(sql);
	String groupId="";
	while(rs.next()){
		groupId=rs.getString(1);
		buffer.append("<node>")
			.append("<id>").append(groupId).append("</id>")
			.append("<name>").append(rs.getString(2)+"("+empBD.containUsersCount(groupId)+"人").append("</name>")
			.append("<parentId>").append("group").append("</parentId>")
			.append("<level>").append(1).append("</level>")
			.append("<childCount>").append(0).append("</childCount>")
			.append("<type>group</type>")
			.append("<hasHref>1</hasHref>");
		if(range.indexOf("@")>=0){
			if(range.indexOf("@"+groupId+"@")>=0){
				buffer.append("<show>1</show>");
			}else{
				buffer.append("<show>0</show>");
			}
		}else{
			buffer.append("<show>1</show>");
		}

		buffer.append("</node>");
	}
	rs.close();
}else{
	//取组织
	if("".equals(range) || range.indexOf("*")>=0){
		String parentOrgId=request.getParameter("parentOrgId");
		StringBuffer rangeIdStringBuffer=new StringBuffer();
		
		String tmpSql = "";
		String databaseType=com.js.util.config.SystemCommon.getDatabaseType();
		if(databaseType.indexOf("mysql")>=0){
			tmpSql = "select orgidstring from org_organization where '"+range+"' like concat('%*',org_id,'*%') and domain_id=" + domainId;
		
		}else{	
			tmpSql = "select orgidstring from org_organization where '"+range+"' like jsdb.FN_LINKCHAR(jsdb.FN_LINKCHAR('%*',org_id),'*%') and domain_id=" + domainId;
		}
		rs=stmt.executeQuery(tmpSql);

		while(rs.next()){
			rangeIdStringBuffer.append(rs.getString(1));
		}
		rs.close();
		String rangeIdString=rangeIdStringBuffer.toString();

		Pattern p=null; //正则表达式
		Matcher m=null; //操作的字符串
		
		p = Pattern.compile("\\$[0-9]*\\$");
		m = p.matcher(range);
		range = m.replaceAll("");


		p = Pattern.compile("@[0-9]*@");
		m = p.matcher(range);
		range = m.replaceAll("");
		

		range=range.replaceAll("\\*\\*","\\$,\\$");
		range=range.replaceAll("\\*","\\$");
		String[] rangeArr=range.split(",");

		String orgId,orgName,orgHasChild,orgIdString;
		int orgLevel=0;
		int hasHref=0;
		int show=0;
		int i=0;
		rs=stmt.executeQuery("select org_id,orgName,orgHasJunior,orgLevel,orgIdString from org_organization where orgParentOrgId="+parentOrgId+" and orgstatus=0 and domain_id=" + domainId + " order by orgidstring");
		while(rs.next()){
			orgId=rs.getString(1);
			orgName=rs.getString(2).trim();
			orgHasChild=rs.getString(3);
			orgLevel=rs.getInt(4);
			orgIdString=rs.getString(5);

			buffer.append("<node>")
				.append("<id>").append(orgId).append("</id>")
				.append("<name>").append(orgName+"<font color=\"red\">("+empBD.containUsersCount(orgId)+"人)</font>").append("</name>")
				.append("<parentId>").append(parentOrgId).append("</parentId>")
				.append("<level>").append(orgLevel).append("</level>")
				.append("<childCount>").append(orgHasChild).append("</childCount>")
				.append("<type>org</type>");
			hasHref=0;
			show=0;
			if("".equals(range) || "$0$".equals(range)){
				hasHref=1;
				show=1;
			}else{
				for(i=0;i<rangeArr.length;i++){
					if(orgIdString.indexOf(rangeArr[i])>0){
						hasHref=1;
						show=1;
						break;
					}
				}
				if(rangeIdString.indexOf("$"+orgId+"$")>=0){
					show=1;
				}
			}


			buffer.append("<hasHref>").append(hasHref).append("</hasHref>")
				.append("<show>").append(show).append("</show>")
				.append("</node>");

		}
		rs.close();
	}
}
stmt.close();
conn.close();
buffer.append("</root>");
out.println(buffer.toString());

      out.write('\r');
      out.write('\n');
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
