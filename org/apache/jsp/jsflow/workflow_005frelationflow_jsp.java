/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:07:47 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.net.URLEncoder;

public final class workflow_005frelationflow_jsp extends org.apache.jasper.runtime.HttpJspBase
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

	String fromDossierData=request.getParameter("fromDossierData").toString();
	String record = request.getParameter("record");
	String tableId=request.getParameter("tableId");
	String notPC = null==request.getParameter("notPC") ? "" : request.getParameter("notPC");
	if(record==null || record.toUpperCase().equals("NULL") || record.trim().length()<1){
            	record = "0";
    }
	if(tableId==null || tableId.toUpperCase().equals("NULL") || tableId.trim().length()<1){
		tableId = "0";
	}
  	String workId = request.getParameter("workId");
	if(workId==null || workId.toUpperCase().equals("NULL") || workId.trim().length()<1){
        workId = "0";
    }

    String workStatus = request.getParameter("workStatus");
	if(workStatus==null || workStatus.toUpperCase().equals("NULL") || workStatus.trim().length()<1){
        workStatus = "0";
    }

    String href = "";

    String sql = "select aaa.WORKFILETYPE, aaa.WORKCURSTEP, aaa.WORKTITLE, aaa.WORKSUBMITPERSON,aaa.WORKSUBMITTIME,"
    	+" aaa.WORKTYPE, aaa.WORKACTIVITY, aaa.WORKTABLE_ID, aaa.WORKRECORD_ID,aaa.WF_WORK_ID,"
            +" aaa.WORKSUBMITPERSON, aaa.WF_SUBMITEMPLOYEE_ID,aaa.WORKPROCESS_ID, aaa.WORKSTEPCOUNT, "
            +" aaa.ISSTANDFORWORK, aaa.STANDFORUSERID, aaa.STANDFORUSERNAME,aaa.WORKSTATUS from JSF_WORK aaa ";
   
    String where=""; 
    String curStatus=request.getParameter("curStatus");
    if(!("-1".equals(curStatus) && "100".equals(workStatus))){
    	where=" where (aaa.WORKSTATUS = 100 or aaa.WORKSTATUS =1 or aaa.WORKSTATUS=-1)";
    }
    
        
    if(!"0".equals(record)){
    	if(!where.equals("")){
    		where+=" and aaa.WORKRECORD_ID = " +record;
    	}else{
            where= " where aaa.WORKRECORD_ID = " +record;        		
    	}
    }
    if(!"0".equals(tableId)){
    	if(!where.equals("")){
    		where+=" and aaa.WORKTABLE_ID = " +tableId;
    	}else{
            where= " where aaa.WORKTABLE_ID = " +tableId;        		
    	}
    }
    if(!"0".equals(workId)){           
        if(!where.equals("")){
            where= " and aaa.wf_work_id="+workId;
      	}else{
      		where= " where aaa.wf_work_id="+workId;
      	}
    }
    com.js.oa.userdb.util.DbOpt dbopt = null;
    
    sql=sql+where;        

    try{
        dbopt = new com.js.oa.userdb.util.DbOpt();
        String[][] workData = dbopt.executeQueryToStrArr2(sql,18);
        if(workData!=null && workData.length>0){
        	//System.out.println(workData[0][2].replaceAll("\n","").replaceAll("\r",""));
        	if("yes".equals(notPC)){
        		href = "/jsoa/WorkFlowProcForWeiXinAction.do?flowpara=1&processId="+workData[0][12]+"&work="+workData[0][9]+"&search=&workTitle="+URLEncoder.encode(workData[0][2].replaceAll("\n","").replaceAll("\r",""),"GBK")
    	            	+"&activityName="+workData[0][1]+"&submitPersonId="+workData[0][11]+"&submitPerson="+workData[0][3]
    	                    +"&workType="+workData[0][5]+"&activity="+workData[0][6]+"&table="+workData[0][7]
    	                    +"&record="+workData[0][8]+"&processName="+URLEncoder.encode(workData[0][0],"GBK")+"&workStatus="+workData[0][17]+"&submitTime="+workData[0][4]
    	                    +"&stepCount="+workData[0][13]+"&isStandForWork="+workData[0][14]
    	                    +"&standForUserId="+workData[0][15]+"&standForUserName="+workData[0][16]+"&fromDossierData="+fromDossierData;
        	} else{
	            href = "/jsoa/WorkFlowProcAction.do?flowpara=1&processId="+workData[0][12]+"&work="+workData[0][9]+"&search=&workTitle="+URLEncoder.encode(workData[0][2].replaceAll("\n","").replaceAll("\r",""),"GBK")
	            	+"&activityName="+workData[0][1]+"&submitPersonId="+workData[0][11]+"&submitPerson="+workData[0][3]
	                    +"&workType="+workData[0][5]+"&activity="+workData[0][6]+"&table="+workData[0][7]
	                    +"&record="+workData[0][8]+"&processName="+URLEncoder.encode(workData[0][0],"GBK")+"&workStatus="+workData[0][17]+"&submitTime="+workData[0][4]
	                    +"&stepCount="+workData[0][13]+"&isStandForWork="+workData[0][14]
	                    +"&standForUserId="+workData[0][15]+"&standForUserName="+workData[0][16]+"&fromDossierData="+fromDossierData;
        	}
        }
        dbopt.close();
    }catch(Exception e){
    	dbopt.close();
    	/*System.out.print("\n------------\n"+sql+"\n------------\n");*/
    	e.printStackTrace();
    }finally{
    	out.print("<script language=\"javascript\">window.location.href=\""+href+"\";</script>");        
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
