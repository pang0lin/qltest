/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:58:33 UTC
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

public final class item_005fsubmit_005fendcurrentstep_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\r\n\r\n");

	request.setCharacterEncoding("UTF-8");

	String type = request.getParameter("type");
	String app = request.getContextPath();

	WorkFlowCommonBD workFlowCommonBD = new WorkFlowCommonBD();
	WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();

	String userId = session.getAttribute("userId").toString();//当前用户ID
	String userName = session.getAttribute("userName").toString();//当前用户ID
	String orgId = session.getAttribute("orgId").toString();//当前用户部门ID
	String orgIdString = session.getAttribute("orgIdString").toString();//当前用户部门ID

	String isStandForWork = request.getParameter("isStandForWork");//是否是代办的任务
	String standForUserId = request.getParameter("standForUserId");//代办用户ID
	String standForUserName = request.getParameter("standForUserName");//代办用户名称
	String submitPersonId = request.getParameter("submitPersonId");//发起人ID
	String mainPressType = request.getParameter("mainPressType");//催办类型
	String mainDeadLineTime = "-1";//办理期限
	String mainPressMotionTime = "-1";//催办时间
	String comment = request.getParameter("include_comment");//用户办理意见
	String mainAllowStandFor = request.getParameter("mainAllowStandFor");//是否允许代办
	String mainAllowCancel = request.getParameter("mainAllowCancel");//允许撤办
	String curActivityId = request.getParameter("curActivityId");//当前节点ID
	String curActivityName = request.getParameter("curActivityName");//当前节点名称
	String mainNextActivityId = "0";//下一节点ID
	String mainNextActivityName = "0";//下一节点名称
	String processName = request.getParameter("processName");//流程名称
	String tableId = request.getParameter("tableId");//数据表
	String recordId = request.getParameter("recordId");//数据记录ID
	String workId = request.getParameter("workId");//workId
	String processId = request.getParameter("processId");

	//--------- 取得表单对应的数据库表名称
	String tableName = new com.js.oa.eform.service.CustomFormBD()
			.getTable(tableId);

	String stepCount = request.getParameter("stepCount");//步骤数
	String activityClass = request.getParameter("activityClass");//有无子过程

	//mainDeadLineTime = request.getParameter("mainDeadLineTime"); //办理期限
	//mainPressMotionTime = request.getParameter("mainPressMotionTime");//催办时间

	WorkFlowBD workFlowBD = new WorkFlowBD();
	String[] mainTransactUser = { "" };//办理用户数组

	//如果是自动回复节点则办理人就是上一节点的办理人
	if ("3".equals(activityClass)) {
		//下一节点即为上一步节点，下一办理人为上一步办理人
		NewWorkflowUtil newUtil = new NewWorkflowUtil();
		com.js.oa.jsflow.vo.ActivityVO activityVO = newUtil.getPreActivity(tableId, recordId, curActivityId, Integer.parseInt(stepCount));
		mainNextActivityId = String.valueOf(activityVO.getId());
		mainNextActivityName = activityVO.getName();
		mainTransactUser = newUtil.getPreActivityUser(tableId, recordId, curActivityId, Integer.parseInt(stepCount));
		mainPressType = String.valueOf(activityVO.getPressType());
		mainDeadLineTime = String.valueOf(activityVO.getDeadlineTime());
		mainPressMotionTime = String.valueOf(activityVO.getPressMotionTime());
		mainAllowStandFor = String.valueOf(activityVO.getAllowStandFor());
		mainAllowCancel = String.valueOf(activityVO.getAllowcancel());
	} else {
		//节点参与人类型
		int mainUserType = Integer.parseInt(request.getParameter("mainUserType"));
		//部门领导按照普通用户处理(已经将部门领导取出)
		if (mainUserType == 10)
			mainUserType = 100;

		switch (mainUserType) {
		case 100:
			//新节点
			String operId = request.getParameter("operId");
			operId = operId.replaceAll(",", "\\$");
			if (operId != null && !operId.equals("")) {
				//新增选择用户组织和用户组
				ConversionString con = new ConversionString(operId);
				String userIdStr = con.getUserIdString() + ",";
				if (con.getGroupIdString() != null && !"".equals(con.getGroupIdString())) {
					userIdStr += this.getUserByGroup(con.getGroupIdString()) + ",";
				}
				if (con.getOrgIdString() != null && !"".equals(con.getOrgIdString())) {
					userIdStr += this.getUserByOrg(con.getOrgIdString());
				}

				//过滤组织或组中已经不活动的用户
				com.js.oa.userdb.util.DbOpt dbopt = null;
				try {
					dbopt = new com.js.oa.userdb.util.DbOpt();
					userIdStr = userIdStr.replaceAll(",,", ",").replaceAll(",,", ",");
					if (userIdStr.startsWith(",")) {
						userIdStr = userIdStr.substring(1, userIdStr.length() - 1);
					}
					if (userIdStr.endsWith(",")) {
						userIdStr = userIdStr.substring(0, userIdStr.length() - 1);
					}
					if (userIdStr.length() > 0) {
						String[] tmp = userIdStr.split(",");
						String sql = "select EMP_ID from ORG_EMPLOYEE where USERISACTIVE=1 and USERISDELETED<>1 and EMP_ID in ("
								+ userIdStr + ") order   by   case   emp_id ";
						for (int k = 0; k < tmp.length; k++) {
							sql += " when " + tmp[k] + " then " + (k + 1) + " ";
						}
						sql += " end ";
						mainTransactUser = dbopt.executeQueryToStrArr1(sql);
					} else
						mainTransactUser = new String[0];
					dbopt.close();
				} catch (Exception e) {
					try {
						dbopt.close();
					} catch (SQLException ex1) {
					}
					mainTransactUser = new String[0];
				} finally {

				}
			}
			break;

		case 10:
			//组织领导
			String operProcOrg = request.getParameter("operId");
			mainTransactUser = workFlowBD.getLeaderListByOrgId(operProcOrg);
			break;
		}// end of  switch (mainUserType)
	}

	//办理期限暂不处理
	//阅件用户 暂不处理
	String mainNeedPassRound = "";
	String[] mainPassRoundUser = { "" };

	//子过程的workID
	String subProcWorkId = request.getParameter("subProcWorkId") == null ? "0" : request.getParameter("subProcWorkId");
	//修改批示意见对应ID
	String modiCommentId = request.getParameter("modiCommentId");

	java.util.Map dealwithMap = new java.util.HashMap();
	dealwithMap.put("tableId", tableId);
	dealwithMap.put("recordId", recordId);
	dealwithMap.put("curActivityName", curActivityName);
	dealwithMap.put("curActivityId", curActivityId);
	dealwithMap.put("userId", userId);
	dealwithMap.put("orgIdString", orgIdString);
	dealwithMap.put("comment", comment);
	dealwithMap.put("nextActivityName", mainNextActivityName);
	dealwithMap.put("nextActivityId", mainNextActivityId);
	dealwithMap.put("stepCount", stepCount);
	dealwithMap.put("isStandForWork", isStandForWork);
	dealwithMap.put("standForUserId", standForUserId);
	dealwithMap.put("activityClass", activityClass);
	dealwithMap.put("subProcWorkId", subProcWorkId);
	if (request.getParameter("include_commField") != null) {
		dealwithMap.put("commentField", request.getParameter("include_commField"));
	}
	dealwithMap.put("userScope", request.getParameter("userScope"));
	dealwithMap.put("scopeId", request.getParameter("scopeId"));
	dealwithMap.put("modiCommentId", modiCommentId);

	com.js.oa.jsflow.service.WorkFlowCommonBD wfcBD = new com.js.oa.jsflow.service.WorkFlowCommonBD();

	String subProcType = request.getParameter("subProcType") == null ? "-1" : request.getParameter("subProcType");
	String toMainFile = request.getParameter("mainLinkFile");

	//当前环节办理方式
	String curTransactType = workFlowBD.getTransactType(tableId, recordId, curActivityId);

	//下一环节办理方式
	String nextTransactType = request.getParameter("approveMode");

	//提醒项设置 暂不处理

	//String docTitle = ""; //如果是公文审批，直接将公文标题设置为待办任务标题
	String docTitle = this.getWorkTitle(processId, recordId, tableId);
	/*if(!"".equals(request.getParameter("titleFieldName"))){
	 docTitle = request.getParameter(request.getParameter("titleFieldName"));
	 }*/
	//缓急程度
	String emergence = "0";//request.getParameter("emergence")==null?"0":request.getParameter("emergence");

	//办理提示
	String dealTips = "";//request.getParameter("dealTips");

	String remindFieldValue = "";

	String[] para = { mainNextActivityName, mainNextActivityId, workId, mainDeadLineTime, mainPressMotionTime, curActivityId,
			mainAllowCancel, stepCount, remindFieldValue, curTransactType, toMainFile, mainAllowStandFor,
			isStandForWork, userId, standForUserId, standForUserName, activityClass, subProcType, docTitle, emergence,
			nextTransactType };
	Integer result = wfcBD.endWorkflowButton(dealwithMap, para);

	if (result.intValue() < 0) {
		//流转提交失败；
		request.setAttribute("flowfaild", "1");
	} else {
		//流程提交成功，添加办理记录	
		WorkLogVO workLogVO = new WorkLogVO();
		workLogVO.setSendUserId(session.getAttribute("userId").toString());
		workLogVO.setSendUserName(session.getAttribute("userName").toString());
		workLogVO.setSendAction("办理完毕");
		//取待办人员
		com.js.oa.jsflow.vo.WorkVO workVO = new com.js.oa.jsflow.vo.WorkVO();
		workVO.setTableId(Long.valueOf(tableId));
		workVO.setRecordId(Long.valueOf(recordId));
		List tmpList = workFlowButtonBD.getAllDealwithUsersByStatus2(workVO, "0");
		String tmpUser = "";
		for (int i = 0; i < tmpList.size(); i++) {
			Object[] obj = (Object[]) tmpList.get(i);
			tmpUser += obj[1] + ",";
		}
		if (tmpUser.endsWith(",")) {
			tmpUser = tmpUser.substring(0, tmpUser.length() - 1);
		}
		workLogVO.setReceiveUserName(tmpUser);

		workLogVO.setProcessId(request.getParameter("processId"));
		workLogVO.setTableId(request.getParameter("tableId"));
		workLogVO.setRecordId(request.getParameter("recordId"));
		workLogVO.setDomainId(session.getAttribute("domainId").toString());
		workFlowButtonBD.setDealWithLog(workLogVO);
	}

	//发送短信、RTX消息提醒 暂不处理

	//保存子流程ID
	if (request.getParameter("subProcTempWorkId") != null && !"".equals(request.getParameter("subProcTempWorkId"))) {
		request.setAttribute("subProcWorkId", request.getParameter("subProcTempWorkId"));
	}
	String loginType = null==session.getAttribute("loginType") ? "wap" : session.getAttribute("loginType").toString();
	String wechatShowDoc2="";
	if(request.getParameter("wechatShowDoc")!=null){
		wechatShowDoc2 = request.getParameter("wechatShowDoc");
	}

      out.write("\r\n<!DOCTYPE HTML>\r\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/wap2/commonImport.jsp" + "?" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("wechatShowDoc", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(wechatShowDoc2 ), request.getCharacterEncoding()), out, false);
      out.write("\r\n<HTML>\r\n\t<HEAD>\r\n\t\t<TITLE>办理结束</TITLE>\r\n\t\t<META content=\"text/html; charset=UTF-8\" http-equiv=Content-Type>\r\n\t\t<META name=viewport content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\">\r\n\t\t<META name=apple-touch-fullscreen content=YES>\r\n\t\t<META name=apple-mobile-web-app-capable content=no>\r\n\t\t<META name=GENERATOR content=\"MSHTML 8.00.6001.19154\">\r\n\t\t");

		String loginType2017 = null==session.getAttribute("loginType") ? "" : session.getAttribute("loginType").toString();
		if(!"weixin".equals(loginType2017)){
		  out.print("<script type=\"text/javascript\">window.history.forward(1);</script>");
		}
		
      out.write("\r\n\t</head>\r\n\t\r\n\t<body>\r\n\t\t<div class=\"form\" style=\"padding-bottom: 60px;\">\r\n\t\t\t<div class=\"item\">\r\n\t\t\t\t<div class=\"content\">事项已经办理完毕!</div>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t\r\n\t\t<div class=\"footer\">\r\n\t  \t\t<div class=\"buttons\">\r\n\t  \t\t\t");

	  			if("wap".equals(loginType)){
	  				
      out.write("\r\n\t  \t\t\t\t<div id=\"closeWindow\" class=\"button gray\" onclick=\"javascript:loadURL('dbsx');\">关&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;闭</div>\r\n\t  \t\t\t\t");

	  			} else{
	  				
      out.write("\r\n\t  \t\t\t\t<div id=\"closeWindow\" class=\"button gray\" onclick=\"javascript:closeWindow('dbsx');\">关&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;闭</div>\r\n\t  \t\t\t\t");

	  			}
	  			
      out.write("\r\n\t    \t</div>\r\n\t    </div>\r\n\t</body>\r\n</html>");
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