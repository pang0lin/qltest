/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:08:03 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.oa.hr.kq.bry.util.BryUtil;
import com.js.util.config.SystemCommon;

public final class deleteWorkLog_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("com.js.oa.hr.kq.bry.util.BryUtil");
    _jspx_imports_classes.add("com.js.util.config.SystemCommon");
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
      response.setContentType("text/html;charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n");

	String recordId = request.getParameter("recordId");

    response.setContentType("text/xml; charset=UTF-8");
	response.setHeader("Cache-Control","no-cache");

	com.js.oa.userdb.util.DbOpt dbopt = null;

	try{
		dbopt = new com.js.oa.userdb.util.DbOpt();
		String sql;
		//取得表单ID，记录ID
		if(recordId.indexOf(",")>0 && recordId.trim().endsWith(",")){
		  sql="select distinct worktable_id,workrecord_id from JSF_WORK where wf_work_id in ("+recordId+"0)";
		}else{
			sql="select distinct worktable_id,workrecord_id from JSF_WORK where wf_work_id="+recordId;
		}
		
		String[][] data=dbopt.executeQueryToStrArr2(sql,2);
		String delRecordId="-1";
		//删除与此记录相关的所有的办理记录
		for(int i=0;i<data.length;i++){
			delRecordId+=","+data[i][1];
			//意见
           dbopt.executeUpdate("delete from jsf_dealwithcomment where wf_dealwith_id in (select wf_dealwith_id from jsf_dealwith where databasetable_id="+data[i][0]+" and databaserecord_id="+data[i][1]+")");
           dbopt.executeUpdate("delete from jsf_dealwith where databasetable_id="+data[i][0]+" and databaserecord_id="+data[i][1]);
           
           //转移条件
           dbopt.executeUpdate("delete from jsf_p_tr where wf_proceedtransition_id in (select wf_proceedtransition_id from jsf_p_transition where wf_proceedactivity_id in(select wf_proceedactivity_id from jsf_p_activity where ttable_id="+data[i][0]+" and trecord_id="+data[i][1]+" ))");
           dbopt.executeUpdate("delete from jsf_p_transition where wf_proceedactivity_id in(select wf_proceedactivity_id from jsf_p_activity where ttable_id="+data[i][0]+" and trecord_id="+data[i][1]+" )");
           
           //读写字段
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
           
           //流转中节点
           dbopt.executeUpdate("delete from jsf_p_activity where ttable_id="+data[i][0]+" and trecord_id="+data[i][1]);
           
		   dbopt.executeUpdate("delete from JSF_WORK where worktable_id="+data[i][0]+" and workrecord_id="+data[i][1]);	
		}		
		//删除实际数据表中的记录
		if(data.length>0){
			//java.sql.ResultSet rs=dbopt.executeQuery("select area_name,area_table from t_area where page_id="+data[0][0]);
			data=dbopt.executeQueryToStrArr2("select area_name,area_table from tarea where page_id="+data[0][0],2);
			for(int i=0;i<data.length;i++){
				if("form1".equals(data[i][0])){
					// 宝日医办结查阅中删除流程时，从考勤数据中删除相应时间
					if("bry".equals(SystemCommon.getCustomerName())){
						// 外出jst_3015、出差jst_chuchai、加班jst_3017、请假jst_3016
						if("jst_3016".equals(data[i][1]) || "jst_3017".equals(data[i][1])){
							BryUtil util = new BryUtil();
							String[] ids = delRecordId.split(",");
							for(int j=1; j<ids.length; j++){
								String date = "";
								String[][] tempData = null;
								String selSQL = "";
								// 查询流程表单信息的加班开始、结束时间
								if("jst_3017".equals(data[i][1])){	// 加班
									selSQL = "SELECT jst_3017_f3183,jst_3017_f3184,jst_3017_f3185,jst_3017_owner FROM jst_3017 WHERE jst_3017_id=" + ids[j];
									tempData = dbopt.executeQueryToStrArr2(selSQL, 4);
									// 查询加班统计时间
									selSQL = "select jq_id,jq_userId,jq_hourLong,jq_oType,jq_type from bry_jq where jq_startDate='" + tempData[0][0] + " " + tempData[0][1] + 
										"' and jq_endDate='" + tempData[0][0] + " " + tempData[0][2] + "' and jq_type=2 and jq_userId=" + tempData[0][3];
								} else if("jst_3016".equals(data[i][1])){	// 休假
									selSQL = "SELECT jst_3016_f3169,jst_3016_f3170,jst_3016_owner FROM jst_3016 WHERE jst_3016_id=" + ids[j];
									tempData = dbopt.executeQueryToStrArr2(selSQL, 3);
									// 查询加班统计时间
									selSQL = "select jq_id,jq_userId,jq_hourLong,jq_oType,jq_type from bry_jq where jq_startDate='" + 
										util.dateFormat(tempData[0][0]) + "' and jq_endDate='" + util.dateFormat(tempData[0][1]) + 
										"' and jq_type=5 and jq_userId=" + tempData[0][2];
								}
								
								date = tempData[0][0].replace("-", "");
								date = date.substring(0, 6);
								
								tempData = dbopt.executeQueryToStrArr2(selSQL, 5);
								float hourLong = Float.parseFloat(tempData[0][2]);
								// 删除加班信息
								dbopt.executeUpdate("delete from bry_jq where jq_id=" + tempData[0][0]);
								// 更新考勤统计信息
								String field = "";
								if("2".equals(tempData[0][4])){
									if("普通加班".equals(tempData[0][3])){
										field = "kqtj_jbpt";
									} else if("周末加班".equals(tempData[0][3])){
										field = "kqtj_jbsx";
									} else if("节假日加班".equals(tempData[0][3])){
										field = "kqtj_jbfdjr";
									} else{
										continue;
									}
								} else if("5".equals(tempData[0][4])){	// 休假
									if("事假".equals(tempData[0][3])){
										field = "kqtj_sj";
									} else if("病假".equals(tempData[0][3])){
										field = "kqtj_bj";
									} else if("年假".equals(tempData[0][3])){
										field = "kqtj_nj";
									} else if("调休".equals(tempData[0][3])){
										field = "kqtj_cctx";
									} else if("产假".equals(tempData[0][3])){
										field = "kqtj_chj";
									} else if("婚假".equals(tempData[0][3])){
										field = "kqtj_hj";
									} else if("哺乳假".equals(tempData[0][3])){
										field = "kqtj_brj";
									} else if("其他".equals(tempData[0][3]) || "丧假".equals(tempData[0][3])){
										field = "kqtj_qt";
									} else{
										continue;
									}
									hourLong /= 8;
								} else{
									continue;
								}
								hourLong = Float.valueOf(String.format("%.2f", hourLong));
								selSQL = "select kqtj_id," + field + ",kqtj_userId from bry_kqtj where kqtj_num=" + date + " and kqtj_userId=" + tempData[0][1];
								tempData = dbopt.executeQueryToStrArr2(selSQL, 3);
								float oldHourLong = Float.parseFloat(tempData[0][1]);
								//if(oldHourLong >= hourLong){
									oldHourLong -= hourLong;
								//}
								dbopt.executeUpdate("update bry_kqtj set " + field + "=" + oldHourLong + " where kqtj_id=" + tempData[0][0]);
								
								// 事假、哺乳假、产假、婚假、病假、其他（丧假），更新实际出勤天数和实际交通费
								if("kqtj_sj".equals(field) || "kqtj_brj".equals(field) || "kqtj_chj".equals(field) || "kqtj_hj".equals(field) 
										|| "kqtj_bj".equals(field) || "kqtj_qt".equals(field)){
									selSQL = "select kqtj_id,kqtj_sjcqts,kqtj_rjtf,kqtj_sjjtf from bry_kqtj where kqtj_num=" + date + " and kqtj_userId=" + tempData[0][2];
									tempData = dbopt.executeQueryToStrArr2(selSQL, 4);
									float sjcqts = Float.parseFloat(tempData[0][1]) + hourLong;
									float sjjtf = Float.parseFloat(tempData[0][2]) * sjcqts;
									dbopt.executeUpdate("update bry_kqtj set kqtj_sjcqts=" + sjcqts + ",kqtj_sjjtf=" + sjjtf + " where kqtj_id=" + tempData[0][0]);
								} else if("kqtj_nj".equals(field)){	// 年假，更新现剩余年假、合计剩余休假
									selSQL = "select kqtj_id,kqtj_xsynj,kqtj_hjsxxj from bry_kqtj where kqtj_num=" + date + " and kqtj_userId=" + tempData[0][2];
									tempData = dbopt.executeQueryToStrArr2(selSQL, 3);
									float xsynj = Float.parseFloat(tempData[0][1]) + hourLong;
									float hjsyxj = Float.parseFloat(tempData[0][2]) + hourLong;
									dbopt.executeUpdate("update bry_kqtj set kqtj_xsynj=" + xsynj + ",kqtj_hjsxxj=" + hjsyxj + " where kqtj_id=" + tempData[0][0]);
								} else if("kqtj_cctx".equals(field)){	// 调休，更新现剩余存休、合计剩余修改
									selSQL = "select kqtj_id,kqtj_xsycx,kqtj_hjsxxj from bry_kqtj where kqtj_num=" + date + " and kqtj_userId=" + tempData[0][2];
									tempData = dbopt.executeQueryToStrArr2(selSQL, 3);
									float xsycx = Float.parseFloat(tempData[0][1]) + hourLong;
									float hjsyxj = Float.parseFloat(tempData[0][2]) + hourLong;
									dbopt.executeUpdate("update bry_kqtj set kqtj_xsycx=" + xsycx + ",kqtj_hjsxxj=" + hjsyxj + " where kqtj_id=" + tempData[0][0]);
								}
							}
						}
					}
					
					//主表
					dbopt.executeUpdate("delete from "+data[i][1]+" where "+data[i][1]+"_id in ("+delRecordId+")");
				}else{
					//子表
					dbopt.executeUpdate("delete from "+data[i][1]+" where "+data[i][1]+"_foreignkey in ("+delRecordId+")");
				}
			}			
		}
		String isearchSwitch=com.js.oa.search.client.SearchService.getInstance().getiSearchSwitch();
		if("1".equals(isearchSwitch)){
			//调用接口动态对流程数据已建立的索引进行删除--开始（xinqingsong20131123）
			String ifActiveUpdateDelete=com.js.oa.search.client.SearchService.getInstance().getIfActiveUpdateDelete();
			if("1".equals(isearchSwitch)&&null!=recordId&&null!=ifActiveUpdateDelete&&!"".equals(recordId)&&!"".equals(ifActiveUpdateDelete)&&!"no".equals(ifActiveUpdateDelete)){//调用远程接口
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
