/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:06:06 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.doc.trans;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.util.util.DataSourceBase;

public final class Govjump_005fdealwith_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

String status=request.getParameter("status");
String workId=request.getParameter("workId");

java.sql.Connection conn=null;
try{
	conn=new DataSourceBase().getDataSource().getConnection();
	java.sql.Statement stmt=conn.createStatement();
	java.sql.ResultSet rs;
	
	StringBuffer sqlBuffer=new StringBuffer("select ");		
	sqlBuffer.append("work.workstatus,work.WORKFILETYPE, work.WORKCURSTEP , work.WORKTITLE ,")
	         .append("work.WORKDEADLINE, work.WORKSUBMITPERSON, work.WORKSUBMITTIME ,")
	         .append("work.WORKTYPE, work.WORKACTIVITY, work.WORKTABLE_ID,")
	         .append("work.WORKRECORD_ID , work.WF_WORK_ID , work.WORKSUBMITPERSON,")
	         .append("work.WF_SUBMITEMPLOYEE_ID, work.WORKALLOWCANCEL, work.WORKPROCESS_ID ,")
	         .append("work.WORKSTEPCOUNT, work.WORKMAINLINKFILE , work.WORKSUBMITTIME,")
	        
	         .append("work.WORKCURSTEP, work.CREATORCANCELLINK , work.ISSTANDFORWORK,")
	         .append("work.STANDFORUSERID, work.STANDFORUSERNAME , work.WORKCREATEDATE ,")
	         .append("work.SUBMITORG , work.WORKDONEWITHDATE, work.emergence ,")
	         .append("work.INITACTIVITY, work.initActivityName, work.tranType,")
	         .append("work.tranFromPersonId , work.processDeadlineDate , work.WF_CUREMPLOYEE_ID ")
	         .append(" from JSF_WORK work ")
	         .append("where work.WF_WORK_ID=")
	         .append(workId);
	
			
	rs=stmt.executeQuery(sqlBuffer.toString());
	String workStatus,title,processName,curStep,deadline,submitPerson,submitTime,
	       workType,activity,table,record,work,
	       submitPersonId,allowCancel,processId,stepCount,mainlink,
	       activityName,createOrCancelLink,isStandForWork,standForUserId,standForUserName,createDate,
	       submitOrg,doneWithDate,emergence,initActivity,initActivityName,tranType,
	       tranFromPersonId,processDeadlineDate,curEmpId;
	if(rs.next()){
		workStatus=rs.getString("workstatus");
		if(workStatus.equals(status)){
			//工作项还没有处理
		}else{
			//工作项已经处理了
		}
		processName=rs.getString("WORKFILETYPE");
		curStep=rs.getString("WORKCURSTEP");
		title=rs.getString("WORKTITLE");
		deadline=rs.getString("WORKDEADLINE");
		submitPerson=rs.getString("WORKSUBMITPERSON");			
		submitTime=rs.getString("WORKSUBMITTIME");
		
		workType=rs.getString("WORKTYPE");
		activity=rs.getString("WORKACTIVITY");
		table=rs.getString("WORKTABLE_ID");
		record=rs.getString("WORKRECORD_ID");			
		work=rs.getString("WF_WORK_ID");
		submitPerson=rs.getString("WORKSUBMITPERSON");
		
		submitPersonId=rs.getString("WF_SUBMITEMPLOYEE_ID");
		allowCancel=rs.getString("WORKALLOWCANCEL");
		processId=rs.getString("WORKPROCESS_ID");			
		stepCount=rs.getString("WORKSTEPCOUNT");
		mainlink=rs.getString("WORKMAINLINKFILE");
		submitTime=rs.getString("WORKSUBMITTIME");
		
		activityName=rs.getString("WORKCURSTEP");
		createOrCancelLink=rs.getString("CREATORCANCELLINK");			
		isStandForWork=rs.getString("ISSTANDFORWORK");
		standForUserId=rs.getString("STANDFORUSERID");
		standForUserName=rs.getString("STANDFORUSERNAME");
		createDate=rs.getString("WORKCREATEDATE");
		
		submitOrg=rs.getString("SUBMITORG");			
		doneWithDate=rs.getString("WORKDONEWITHDATE");
		emergence=rs.getString("emergence");
		initActivity=rs.getString("INITACTIVITY");
		initActivityName=rs.getString("initActivityName");
		tranType=rs.getString("tranType");
		
		tranFromPersonId=rs.getString("tranFromPersonId");			
		processDeadlineDate=rs.getString("processDeadlineDate");
		curEmpId=rs.getString("WF_CUREMPLOYEE_ID");
		
		sqlBuffer=new StringBuffer(mainlink);
		sqlBuffer.append("&search=&from=dealwith&workTitle=")
		         .append("&activityName=").append(activityName)
		         .append("&submitPersonId=").append(submitPersonId)
		         .append("&submitPerson=").append(submitPerson)
		         .append("&work=").append(work)
		         .append("&workType=").append(workType)
		         .append("&activity=").append(activity)
		         .append("&table=").append(table)
		         .append("&record=").append(record)
		         .append("&processName=").append(processName)
		         .append("&workStatus=").append(workStatus)
		         .append("&submitTime=").append(submitTime)
		         .append("&processId=").append(processId)
		         .append("&stepCount=").append(stepCount)
		         .append("&isStandForWork=").append(isStandForWork)
		         .append("&standForUserId=").append(standForUserId)
		         .append("&standForUserName=").append(standForUserName)
		         .append("&initActivity=").append(initActivity)
		         .append("&initActivityName=").append(initActivityName)
		         .append("&submitPersonTime=").append(submitTime)
		         .append("&tranType=").append(tranType)
		         .append("&tranFromPersonId=").append(tranFromPersonId)
		         .append("&processDeadlineDate=").append(processDeadlineDate)
		         .append("&fromdesktop=1");	
		
      out.write("\r\n\t\t<script type=\"text/javascript\">\r\n\t\t<!--\r\n\t\tlocation.href=\"");
      out.print(sqlBuffer.toString());
      out.write("\";\r\n\t\t//-->\r\n\t\t</script>\r\n\t\t");

		
	}else{
		//记录不存在了
		
      out.write("\r\n\t\t<script type=\"text/javascript\">\r\n\t\t<!--\r\n\t\talert(\"相关流转数据已经被删除，或者被转交代理！\");\r\n\t\twindow.close();\t\t\r\n\t\t//-->\r\n\t\t</script>\r\n\t\t");

	}
	rs.close();
	stmt.close();
	conn.close();

}catch(Exception ex){
	if(conn!=null){
		conn.close();
	}
}


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
