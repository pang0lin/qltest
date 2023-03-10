/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:08:02 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class deleteMeetWorkLog_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes = null;
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
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;


	String recordId = request.getParameter("recordId");

    response.setContentType("text/xml; charset=UTF-8");
	response.setHeader("Cache-Control","no-cache");

	com.js.oa.userdb.util.DbOpt dbopt = null;

	try{
		dbopt = new com.js.oa.userdb.util.DbOpt();
		String sql;
		//????????????????????????ID??????????????????ID
		if(recordId.indexOf(",")>0 && recordId.trim().endsWith(",")){
		  sql="select distinct worktable_id,workrecord_id from JSF_WORK where wf_work_id in ("+recordId+"0)";
		}else{
			sql="select distinct worktable_id,workrecord_id from JSF_WORK where wf_work_id="+recordId;
		}
		
		String[][] data=dbopt.executeQueryToStrArr2(sql,2);
		String delRecordId="-1";
		//????????????????????????????????????????????????????????????????????????????????????????????????
		for(int i=0;i<data.length;i++){
			delRecordId+=","+data[i][1];
			//????????????
           dbopt.executeUpdate("delete from jsf_dealwithcomment where wf_dealwith_id in (select wf_dealwith_id from jsf_dealwith where databasetable_id="+data[i][0]+" and databaserecord_id="+data[i][1]+")");
           dbopt.executeUpdate("delete from jsf_dealwith where databasetable_id="+data[i][0]+" and databaserecord_id="+data[i][1]);
           
           //????????????????????????
           dbopt.executeUpdate("delete from jsf_p_tr where wf_proceedtransition_id in (select wf_proceedtransition_id from jsf_p_transition where wf_proceedactivity_id in(select wf_proceedactivity_id from jsf_p_activity where ttable_id="+data[i][0]+" and trecord_id="+data[i][1]+" ))");
           dbopt.executeUpdate("delete from jsf_p_transition where wf_proceedactivity_id in(select wf_proceedactivity_id from jsf_p_activity where ttable_id="+data[i][0]+" and trecord_id="+data[i][1]+" )");
           
           //????????????????????????
           String sqlCtrol="select wf_proceedactivity_id from jsf_p_activity where ttable_id="+data[i][0]+" and trecord_id="+data[i][1]+" ";
           String[][] dataCtrol=dbopt.executeQueryToStrArr2(sql,1);
           String wf_proceedactivity_id="";
           for(int s=0;s<dataCtrol.length;s++){
               wf_proceedactivity_id+=dataCtrol[s][0]+",";
           }
           if(wf_proceedactivity_id!=null && !"".equals(wf_proceedactivity_id)){
              if(wf_proceedactivity_id.endsWith(",")){
                wf_proceedactivity_id=wf_proceedactivity_id.substring(0, wf_proceedactivity_id.length()-1);
              }
           }
           dbopt.executeUpdate("delete from jsf_p_readwritecontrol where wf_proceedactivity_id in("+wf_proceedactivity_id+")");
           
           //??????????????????????????????
           dbopt.executeUpdate("delete from jsf_p_activity where ttable_id="+data[i][0]+" and trecord_id="+data[i][1]);
           
		   dbopt.executeUpdate("delete from JSF_WORK where worktable_id="+data[i][0]+" and workrecord_id="+data[i][1]);	
		}		
		
		String isearchSwitch=com.js.oa.search.client.SearchService.getInstance().getiSearchSwitch();
		if("1".equals(isearchSwitch)){
			//??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????--??????????????????xinqingsong20131123??????
			String ifActiveUpdateDelete=com.js.oa.search.client.SearchService.getInstance().getIfActiveUpdateDelete();
			if("1".equals(isearchSwitch)&&null!=recordId&&null!=ifActiveUpdateDelete&&!"".equals(recordId)&&!"".equals(ifActiveUpdateDelete)&&!"no".equals(ifActiveUpdateDelete)){//????????????????????????????????????
				if(recordId.contains(",")){
					String id[] =recordId.split(",");
					for(int z=0;z<id.length;z++){
						com.js.oa.search.client.SearchService.getInstance().deleteIndex(id[z], "jsf_workflow");
					}
				}else{
					com.js.oa.search.client.SearchService.getInstance().deleteIndex(recordId, "jsf_workflow");
				}
				
			
			}
		}
		
		
		
		out.print("OK");
		//dbopt.close();
	}catch(Exception ee){
		ee.printStackTrace();
	}finally{
		dbopt.close();
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
