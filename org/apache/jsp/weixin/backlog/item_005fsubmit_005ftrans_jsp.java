/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:58:26 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.weixin.backlog;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import java.text.SimpleDateFormat;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.util.*;
import com.js.util.util.ConversionString;
import java.sql.SQLException;
import com.js.oa.jsflow.vo.WorkLogVO;
import com.js.system.service.usermanager.UserBD;
import com.js.oa.jsflow.service.*;
import com.js.util.config.SystemCommon;

public final class item_005fsubmit_005ftrans_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

//根据组织取所有用户
	public String getUserByOrg(String orgIdStr) {
		String orgIds = "";
		if (orgIdStr == null || orgIdStr.length() < 1) {
			return orgIds;
		}
		String[] orgIdArr = orgIdStr.split(",");
		com.js.oa.userdb.util.DbOpt dbopt = null;
		java.sql.ResultSet rs = null;
		try {
			dbopt = new com.js.oa.userdb.util.DbOpt();

			for (int i = 0; i < orgIdArr.length; i++) {
				String orgCode = dbopt.executeQueryToStr("select ORGIDSTRING from ORG_ORGANIZATION where ORG_ID=" + orgIdArr[i]);
				rs = dbopt.executeQuery("select EMP_ID from ORG_ORGANIZATION_USER where ORG_ID in (select ORG_ID from ORG_ORGANIZATION where ORGIDSTRING like '%"
								+ orgCode + "%')");
				if (rs != null) {
					while (rs.next()) {
						Object empId = rs.getObject(1);
						if (empId != null && orgIds.indexOf(empId.toString()) < 0) {
							orgIds += empId.toString() + ",";
						}
					}
					rs.close();
				}
			}
			dbopt.close();
		} catch (Exception e) {
			try {
				dbopt.close();
			} catch (SQLException ex) {
			}
		} finally {
			return orgIds;
		}
	}

	/**
	 * 根据组ID取所有用户
	 * @param groupIdStr String
	 * @return String
	 */
	public String getUserByGroup(String groupIdStr) {
		String userStr = "";
		com.js.util.util.DataSourceBase dsb = new com.js.util.util.DataSourceBase();
		javax.sql.DataSource ds = dsb.getDataSource();
		java.sql.Connection conn = null;
		java.sql.Statement stmt = null;
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			java.sql.ResultSet rs = stmt.executeQuery("SELECT DISTINCT EMP_ID FROM ORG_USER_GROUP WHERE GROUP_ID IN (" + groupIdStr + ")");
			while (rs.next()) {
				userStr += rs.getString(1) + ",";
			}
			if (userStr.endsWith(",")) {
				userStr = userStr.substring(0, userStr.length() - 1);
			}
			rs.close();
			stmt.close();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (java.sql.SQLException ex) {
				ex.printStackTrace();
			}
		}
		return userStr;
	}

	/**
	 * 取办理节点
	 * @param groupIdStr String
	 * @return String
	 */
	public String getWorkTitle(String processId, String recordId, String tableId) {
		int module = 1;
		String workTitle = "";
		com.js.util.util.DataSourceBase dsb = new com.js.util.util.DataSourceBase();
		javax.sql.DataSource ds = dsb.getDataSource();
		java.sql.Connection conn = null;
		java.sql.Statement stmt = null;
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			String sql = "select wf_module_id from jsf_workflowprocess wp left join jsf_package pk on wp.wf_package_id=pk.wf_package_id where wp.wf_workflowprocess_id="
					+ processId;
			java.sql.ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				module = rs.getInt(1);
			}
			rs.close();

			if (module == 2) {
				//发文
				sql = "select documentsendfile_title from doc_documentsendfile where documentsendfile_id=" + recordId;
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					workTitle = rs.getString(1);
				}
				rs.close();

			} else if (module == 3) {
				//收文
				sql = "select receivefile_title from doc_receivefile where receivefile_id=" + recordId;
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					workTitle = rs.getString(1);
				}
				rs.close();
			}
			stmt.close();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (java.sql.SQLException ex) {
				ex.printStackTrace();
			}
		}
		return workTitle;
	}
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
    _jspx_imports_packages.add("com.js.oa.jsflow.util");
    _jspx_imports_packages.add("com.js.oa.jsflow.service");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.sql.SQLException");
    _jspx_imports_classes.add("com.js.util.config.SystemCommon");
    _jspx_imports_classes.add("java.text.SimpleDateFormat");
    _jspx_imports_classes.add("com.js.oa.jsflow.vo.WorkLogVO");
    _jspx_imports_classes.add("com.js.util.util.ConversionString");
    _jspx_imports_classes.add("com.js.oa.jsflow.service.WorkFlowBD");
    _jspx_imports_classes.add("com.js.system.service.usermanager.UserBD");
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
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
      out.write('\r');
      out.write('\n');

	request.setCharacterEncoding("UTF-8");

	String type = request.getParameter("type");
	String app = request.getContextPath();

	String userId = session.getAttribute("userId") + "";

	String workId = request.getParameter("workId");//work_id
	String stepCount = request.getParameter("stepCount");//步骤数
	String comment = request.getParameter("include_comment");//批示意见
	String recordId = request.getParameter("recordId");//数据记录ID
	String tableId = request.getParameter("tableId");//数据表ID
	String activityName = request.getParameter("curActivityName");//当前节点名称
	String activityId = request.getParameter("curActivityId");//当前节点ID
	String processId = request.getParameter("processId");

	//String transitionUser = request.getParameter("transitionUser");//转办用户
	//transitionUser = transitionUser.replace('$', ',');
	//String[] user = ("," + transitionUser + ",").split(",,");

	String transitionUser = request.getParameter("newSelectedOper");
	String[] user = transitionUser.split(",");

	//String docTitle = ""; //如果是公文审批，直接将公文标题设置为待办任务标题
	String docTitle = this.getWorkTitle(processId, recordId, tableId);
	/*if(!"".equals(request.getParameter("titleFieldName"))){
	 docTitle = request.getParameter(request.getParameter("titleFieldName"));
	 }*/
	//转交处理标题
	if ("transend".equals(request.getParameter("titleFieldName"))) {
		docTitle = "transend";
	}

	//转办还是增加批阅人 addperson:增加批阅人; 空：转办；tranByReturn：转办需要自动返回；tranAutoReturn：转办后自动返回
	String actionType = request.getParameter("actiontype") == null ? "" : request.getParameter("actiontype");

	//转办类型
	String tranType = request.getParameter("tranType");
	String tranFromPersonId = request.getParameter("tranFromPersonId");

	//提醒项 暂不处理
	String remindFieldValue = "";

	String[] para = { workId, stepCount, remindFieldValue.toString(), request.getParameter("mainLinkFile"), docTitle, 
					actionType, userId, tranType, tranFromPersonId };

	if ("addperson".equals(actionType)) {
		//增加批阅人
		new WorkFlowButtonBD().addPersonWork(para, user);

		//流程提交成功，添加办理记录
		WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
		WorkLogVO workLogVO = new WorkLogVO();
		workLogVO.setSendUserId(session.getAttribute("userId").toString());
		workLogVO.setSendUserName(session.getAttribute("userName").toString());
		workLogVO.setSendAction("增加批阅人");
		//workLogVO.setReceiveUserId(transitionUser+"");
		String tmpUser = request.getParameter("transitionUserName");
		if (tmpUser.endsWith(",")) {
			tmpUser = tmpUser.substring(0, tmpUser.length() - 1);
		}
		workLogVO.setReceiveUserName(tmpUser);
		workLogVO.setProcessId(request.getParameter("processId"));
		workLogVO.setTableId(request.getParameter("tableId"));
		workLogVO.setRecordId(request.getParameter("recordId"));
		workLogVO.setDomainId(session.getAttribute("domainId").toString());

		workFlowButtonBD.setDealWithLog(workLogVO);

	} else {

		if ("tranAutoReturn".equals(actionType)) {
			//转办后自动返回
			new WorkFlowButtonBD().tranAutoReturn(para);
		} else {
			//转办操作
			new WorkFlowBD().transitionWork(para, user);
		}

		//sendMsg(user, request);

		//HttpSession session = request.getSession(true);
		//String userId = session.getAttribute("userId").toString();
		com.js.oa.jsflow.service.WorkFlowCommonBD wfcBD = new com.js.oa.jsflow.service.WorkFlowCommonBD();
		//修改批示意见对应ID
		String modiCommentId = request.getParameter("modiCommentId");
		java.util.Map dealwithMap = new java.util.HashMap();
		dealwithMap.put("tableId", tableId);
		dealwithMap.put("recordId", recordId);
		dealwithMap.put("curActivityName", activityName);
		dealwithMap.put("curActivityId", activityId);
		dealwithMap.put("userId", userId);
		dealwithMap.put("comment", comment);
		dealwithMap.put("nextActivityName", "");
		dealwithMap.put("nextActivityId", "0");
		dealwithMap.put("stepCount", stepCount);
		dealwithMap.put("isStandForWork", request.getParameter("isStandForWork"));
		dealwithMap.put("standForUserId", request.getParameter("standForUserId"));
		if (request.getParameter("include_commField") != null) {
			dealwithMap.put("commentField", request.getParameter("include_commField"));
		}
		dealwithMap.put("commType", request.getParameter("commType"));

		dealwithMap.put("userScope", request.getParameter("userScope"));
		dealwithMap.put("scopeId", request.getParameter("scopeId"));

		dealwithMap.put("modiCommentId", modiCommentId);
		if (!"1".equals(request.getParameter("transend"))) {
			//转交不保存意见
			wfcBD.insertTransDealWith(dealwithMap);
		}

		//流程提交成功，添加办理记录
		WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
		WorkLogVO workLogVO = new WorkLogVO();
		workLogVO.setSendUserId(session.getAttribute("userId").toString());
		workLogVO.setSendUserName(session.getAttribute("userName").toString());
		workLogVO.setSendAction("转办");
		//workLogVO.setReceiveUserId(transitionUser+"");

		/*
		String tmpUser = request.getParameter("transitionUserName");
		if (tmpUser.endsWith(",")) {
		    tmpUser = tmpUser.substring(0, tmpUser.length() - 1);
		}
		 */

		workLogVO.setReceiveUserName(transitionUser);
		workLogVO.setProcessId(request.getParameter("processId"));
		workLogVO.setTableId(request.getParameter("tableId"));
		workLogVO.setRecordId(request.getParameter("recordId"));
		workLogVO.setDomainId(session.getAttribute("domainId").toString());

		//转办返回
		if ("tranAutoReturn".equals(actionType)) {
			workLogVO.setSendAction("转办自动返回");
			workLogVO.setReceiveUserName(" ");
		}
		workFlowButtonBD.setDealWithLog(workLogVO);
	}
	String loginType = null==session.getAttribute("loginType") ? "wap" : session.getAttribute("loginType").toString();
	String wechatShowDoc="";
	String wechatShowDoc1="";
	String wechatShowDoc2="";
	if(request.getParameter("wechatShowDoc")!=null){
		wechatShowDoc = "&wechatShowDoc="+request.getParameter("wechatShowDoc");
		wechatShowDoc1 = "?wechatShowDoc="+request.getParameter("wechatShowDoc");
		wechatShowDoc2 = request.getParameter("wechatShowDoc");
	}

      out.write("\r\n<!DOCTYPE HTML>\r\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/wap2/commonImport.jsp" + "?" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("wechatShowDoc", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(wechatShowDoc2 ), request.getCharacterEncoding()), out, false);
      out.write("\r\n<HTML>\r\n\t<HEAD>\r\n\t\t<TITLE>办理结束</TITLE>\r\n\t\t<META content=\"text/html; charset=UTF-8\" http-equiv=Content-Type>\r\n\t\t<META name=viewport content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\">\r\n\t\t<META name=apple-touch-fullscreen content=YES>\r\n\t\t<META name=apple-mobile-web-app-capable content=no>\r\n\t\t<META name=GENERATOR content=\"MSHTML 8.00.6001.19154\">\r\n\t\t");

		String loginType2017 = null==session.getAttribute("loginType") ? "" : session.getAttribute("loginType").toString();
		if(!"weixin".equals(loginType2017)){
		  out.print("<script type=\"text/javascript\">window.history.forward(1);</script>");
		}
		
      out.write("\r\n\t</head>\r\n\r\n\t<body onload=\"setHeader('javascript:loadURL();','办理完成');\" class=\"paddingTop\">\r\n\t\t<div class=\"form\">\r\n\t\t\t<div class=\"item\">\r\n\t\t\t\t<div class=\"content\">事项已经办理完毕!</div>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t\r\n\t\t<div class=\"footer\">\r\n\t  \t\t<div class=\"buttons\">\r\n\t  \t\t\t");

	  			if("wap".equals(loginType)){
	  				
      out.write("\r\n\t  \t\t\t\t<div id=\"closeWindow\" class=\"button gray\" onclick=\"loadURL();\">关&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;闭</div>\r\n\t  \t\t\t\t");

	  			} else{
	  				
      out.write("\r\n\t  \t\t\t\t<div id=\"closeWindow\" class=\"button gray\" onclick=\"javascript:closeWindow();\">关&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;闭</div>\r\n\t  \t\t\t\t");

	  			}
	  			
      out.write("\r\n\t    \t</div>\r\n\t    </div>\r\n\t</body>\r\n</html>\r\n<script type=\"text/javascript\">\r\n\t\tfunction loadURL(){\r\n\t\t\t");
if(SystemCommon.getCustomerName().equals("zcl")){
      out.write("\r\n\t\t\t\twindow.close();\r\n\t\t\t");
}else{
      out.write("\r\n\t\t\t\twindow.location.href=\"/jsoa/weiXinBacklogAction.do?action=list&status=0");
      out.print(wechatShowDoc);
      out.write("\";\r\n\t\t\t");
}
      out.write("\r\n\t\t}\r\n\t\t");
if(SystemCommon.getCustomerName().equals("zcl")){
      out.write("\r\n\t\t\tfunction closeWindow(){\r\n\t\t\t\twindow.close();\r\n\t\t\t}\r\n\t\t");
}
      out.write("\r\n</script>");
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