/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:51:01 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.setup;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.system.bean.usermanager.UserEJBBean;
import com.js.system.manager.service.ManagerService;
import java.util.*;

public final class systemmanager_005fuser_005fempturnoversyn_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("com.js.system.bean.usermanager.UserEJBBean");
    _jspx_imports_classes.add("com.js.system.manager.service.ManagerService");
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

      out.write("\r\n\r\n\r\n\r\n");
 


int j = 0;
int index=0;
String listClass="listTableLine1_xin";
Object[] listObject=null;
String turnOverEmpsName=(String)request.getParameter("turnOverEmpsName");
String personId=(String)request.getParameter("personId");

String turnOverEmpsNameOne=(String)request.getParameter("turnOverEmpsNameOne");
String personIdOne=(String)request.getParameter("personIdOne");

long empId=0;
if(personId!=null && !"".equals(personId))
{
	empId= Long.parseLong(personId);
}

UserEJBBean userEJBBean =new UserEJBBean();

List list=null;
List listCoordination=null;
try 
{
	listCoordination=userEJBBean.getUserCoordination(empId);
	list = userEJBBean.getUserWorkClass(empId);
	
	for(int z=0;z<listCoordination.size();z++)
	{
		list.add(listCoordination.get(z));
	}
	
	
} 
catch (Exception e) 
{
	e.printStackTrace();
}

String str="";
/*888888888888888888888888888888888888888888888888888*/

str+="<table width='90%' align='left' border='0' cellpadding='0' cellspacing='0'>";
str+="<tr>";
str+="<td>";
str+="<table  width='100%' border='0' id='personinner' cellpadding='0' cellspacing='0' class='listTable outTopline'>";
str+="<tr align='center'>";
str+="<td class='listTableHead'> 未处理事项类型 </td>";
str+="<td class='listTableHead'> 数量 </td>";
str+="<td width='180' nowrap class='listTableHead'>操作</td>";
str+="</tr>";
 if(list!=null ){
  for(int m=0;m<list.size();m++)
  {
	  listObject=(Object[])list.get(m);
	  
	  j++;
	  index ++;
	  if(index%2 != 0){
		listClass="listTableLine2_xin";
	  }else{
		listClass="listTableLine1_xin";
	  }
 
str+="<tr>";
str+="<td class="+listClass+">&nbsp;"+listObject[1]+"<input type='hidden' id='workIdCoordination'  name='workIdCoordination'  value="+listObject[0]+" /></td>";
str+="<td class="+listClass+">&nbsp;"+listObject[2]+"</td>";
str+="<td class="+listClass+">";
str+="<table width='100%' >";
str+="<tr>";
str+="<td valign='top'>";
str+="<input  class='inputTextarea' title='指定移交对象' value="+turnOverEmpsName+" cols='60' rows='1' Id='empsName"+j +"' name='empsName"+j+ "' readonly='true' style='width:90%;cursor:pointer' onclick=javascript:openEndow('emps"+j+"','empsName"+j +"',document.getElementById('emps"+j+"').value,document.getElementById('empsName"+j+"').value,'user','yes','usergroup','','*0*');></input>";
str+="<input type='hidden' id='emps"+j+"' name='emps"+j+"' value="+personIdOne+" />";
str+="</td>";
str+="<td style='color:blue;cursor:pointer' width='30%' valign='top'  >";
str+="<input type='hidden' id='workId"+j+"'  name='workId"+j+"'  value="+listObject[0]+" /><input type='button' class='btnButton2Font' value='移交' onclick=javascript:saveAndExitSingle('empsName"+j+"','emps"+j+"','workId"+j+"'); >";
str+="</td>";
str+="</tr>";
str+="</table>";
str+="</td>";
str+="</tr>";
}}
str+="</table>";
str+="</td>";
str+="</tr>";
str+="</table>";




/*8888888888888888888888888888888888888888888888888888888888*/




out.write(str);



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
