/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:59:47 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.weixin.workflow.eform;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.util.util.DataSourceBase;

public final class fetchdata_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/xml; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

String curIndex=request.getParameter("curIndex");
String fieldId=request.getParameter("fieldId");
String selValue=request.getParameter("selValue");
String startUserId=request.getParameter("startUserId");
String startUserOrgId=request.getParameter("startOrgId");
String startUserAccount=request.getParameter("startUserAccount");

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

if(selValue.indexOf("@@$@@")>=0){
	selValue=selValue.substring(0,selValue.indexOf("@@$@@"));
}

int i;
StringBuffer buffer=new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?><root>");
buffer.append("<curIndex>").append(curIndex).append("</curIndex>");
/*
<root>
   <data>      
      <formfield></formfield>
      <fetchdata>0</fetchdata>  0:直接给字段赋值  1:下拉框类型需要 id与text
      <content></content>
   </data>
   <data>      
      <formfield></formfield>
      <fetchdata>1</fetchdata>  0:直接给字段赋值  1:下拉框等类型需要两个字段(id与text)
      <content>
         <id></id>
         <text></text>
      </content>
   </data>
   <data>      
   <formfield></formfield>
   <fetchdata>2</fetchdata>   0:直接给字段赋值  1:下拉框等类型需要两个字段(id与text) 2:需要多个值 一般为执行sql提取数据
   <content>
   	  <subdata>
   	    <id></id>
        <text></text>
   	  </subdata>      
   </content>
</data>
</root>
*/

DataSourceBase base=new DataSourceBase();

java.sql.Connection conn=null;
java.sql.Connection conn2=null;
java.sql.Statement stmt2=null;

try{
	java.sql.Statement stmt;
	java.sql.ResultSet rs;
	conn=base.getDataSource().getConnection();
	
	
	String fetchSql="",toFormField="",fieldValue="";
	String[][] fetchSqlPara;
	
	java.sql.PreparedStatement pstmt=conn.prepareStatement("select field_fetchsql,field_toffield,field_value from tfield where field_id=?");
	pstmt.setString(1, fieldId);
	
	rs=pstmt.executeQuery();
	if(rs.next()){
		fetchSql=rs.getString(1);
		toFormField=rs.getString(2);
		fieldValue=rs.getString(3);
		if(fieldValue.indexOf("].$[")>0){
			fieldValue=fieldValue.substring(2,fieldValue.indexOf("].$["));
		}else{
			fieldValue="";
		}
		//System.out.println("fieldValue:"+fieldValue);
	}
	rs.close();
	pstmt.close();
	
	stmt=conn.createStatement();
	
		
	if(toFormField!=null && toFormField.length()>0){
		if(!"".equals(fieldValue) && !"system".equals(fieldValue)){
			conn2=new com.js.util.util.DataSourceBase().getDataSource(fieldValue).getConnection();
			stmt2=conn2.createStatement();
			String dbType=com.js.util.config.SystemCommon.getUserDatabaseType(fieldValue);
			if(dbType.indexOf("oracle")>=0){
				String lang=com.js.util.config.SystemCommon.getUserDatabaseLang(fieldValue);
				if(!"".equals(lang)){
					stmt2.executeUpdate("ALTER SESSION SET NLS_LANGUAGE='"+lang+"'");
				}
            }			
		}
		//有设置取数据的字段
		String temp;
		String[] formFieldArr=toFormField.split(";;;");
		fetchSqlPara=new String[formFieldArr.length][4];
		String formField,sqlField;
		for(i=0;i<formFieldArr.length;i++){
			formField=formFieldArr[i].substring(0,formFieldArr[i].indexOf("=:=")).trim();
			sqlField=formFieldArr[i].substring(formFieldArr[i].indexOf("=:=")+3).trim();
			fetchSqlPara[i][0]=formField;
			if(sqlField.startsWith("[")){
				fetchSqlPara[i][1]="2";
			}else if(sqlField.indexOf(",")>0){
				fetchSqlPara[i][1]="1";
			}else{
				fetchSqlPara[i][1]="0";
			}
			if(sqlField.startsWith("[")){
				sqlField=sqlField.substring(1,sqlField.length()-1);
			}
			fetchSqlPara[i][2]=sqlField;
			fetchSqlPara[i][3]="";
			//System.out.println("sqlField:"+sqlField);
		}	
		//System.out.println("fetchSql:"+fetchSql);
		if(fetchSql!=null && fetchSql.toLowerCase().indexOf("select ")>=0 && fetchSql.toLowerCase().indexOf(" from ")>0){
			//sql语句正确
			fetchSql=fetchSql.replaceAll("\\@\\$\\@selValue\\@\\$\\@",selValue);
			fetchSql=fetchSql.replaceAll("\\@\\$\\@userId\\@\\$\\@",userId);
			fetchSql=fetchSql.replaceAll("\\@\\$\\@orgId\\@\\$\\@",orgId);
			fetchSql=fetchSql.replaceAll("\\@\\$\\@userAccount\\@\\$\\@",userAccount);
			fetchSql=fetchSql.replaceAll("\\@\\$\\@userName\\@\\$\\@",userName);
			
			fetchSql=fetchSql.replaceAll("\\@\\$\\@applyId\\@\\$\\@",submitPersonId);
			fetchSql=fetchSql.replaceAll("\\@\\$\\@applyOrgId\\@\\$\\@",submitPersonOrgId);
			
			if("".equals(fieldValue) || "system".equals(fieldValue)){
				rs=stmt.executeQuery(fetchSql);
			}else{
				rs=stmt2.executeQuery(fetchSql);
			}
			if(rs.next()){
				for(i=0;i<fetchSqlPara.length;i++){
					if("0".equals(fetchSqlPara[i][1])){
						fetchSqlPara[i][3]=rs.getString(fetchSqlPara[i][2]);
					}else if("1".equals(fetchSqlPara[i][1])){
						String[] manyField=fetchSqlPara[i][2].split(",");
						fetchSqlPara[i][3]=rs.getString(manyField[0])+"=:="+rs.getString(manyField[1]);
					}		
					//System.out.println("fetchSqlPara[i][3]:"+fetchSqlPara[i][3]);
				}
			}
			rs.close();
			
		}else{
			//sql语句不正确
		}	
		
		//循环所有字段构造返回xml文件
		for(i=0;i<fetchSqlPara.length;i++){
			buffer.append("<data><formfield>").append(fetchSqlPara[i][0]).append("</formfield>");
			buffer.append("<fetchdata>").append(fetchSqlPara[i][1]).append("</fetchdata>");
			buffer.append("<content>");
			//System.out.println(fetchSqlPara[i][1]+"=====fetchSqlPara========"+fetchSqlPara.length);
			if(fetchSqlPara[i][1].equals("0")){
				buffer.append(com.js.util.util.CharacterTool.replaceXMLTags((fetchSqlPara[i][3]==null || "null".equals(fetchSqlPara[i][3]))?"":fetchSqlPara[i][3]));
			}else if(fetchSqlPara[i][1].equals("1")){
				
			}else{
				//sql 取数据
				
				fetchSql=fetchSqlPara[i][2];
				fetchSql=fetchSql.replaceAll("\\@\\$\\@selValue\\@\\$\\@",selValue);
				fetchSql=fetchSql.replaceAll("\\@\\$\\@userId\\@\\$\\@",userId);
				fetchSql=fetchSql.replaceAll("\\@\\$\\@orgId\\@\\$\\@",orgId);
				fetchSql=fetchSql.replaceAll("\\@\\$\\@userAccount\\@\\$\\@",userAccount);
				fetchSql=fetchSql.replaceAll("\\@\\$\\@userName\\@\\$\\@",userName);
				
				fetchSql=fetchSql.replaceAll("\\@\\$\\@applyId\\@\\$\\@",submitPersonId);
				fetchSql=fetchSql.replaceAll("\\@\\$\\@applyOrgId\\@\\$\\@",submitPersonOrgId);
				if("".equals(fieldValue) || "system".equals(fieldValue)){
					rs=stmt.executeQuery(fetchSql);
				}else{
					rs=stmt2.executeQuery(fetchSql);
				}
				//System.out.println("fetchSql:"+fetchSql);
				while(rs.next()){
					buffer.append("<subdata>");
					temp=rs.getString(1);
					if(temp==null || "null".equals(temp)){
						temp="";
					}
					temp=com.js.util.util.CharacterTool.replaceXMLTags(temp);
					buffer.append("<datavalue>").append(temp).append("</datavalue>");
					temp=rs.getString(2);
					if(temp==null || "null".equals(temp)){
						temp="";
					}
					temp=com.js.util.util.CharacterTool.replaceXMLTags(temp);
					buffer.append("<datatext>").append(temp).append("</datatext>");
					buffer.append("</subdata>");
					
				}
				rs.close();
				
				
			}
			buffer.append("</content>");
			buffer.append("</data>");
		}
	}
	
	stmt.close();
	conn.close();
	
	if(stmt2!=null){
		stmt2.close();
		conn2.close();
	}
}catch(Exception ex){
	if(conn!=null){
		conn.close();
	}
	if(conn2!=null){
		conn2.close();
	}
	ex.printStackTrace();
}

buffer.append("</root>");

out.clear();
//System.out.println(buffer.toString());
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