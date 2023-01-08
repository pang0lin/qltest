/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:59:23 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.weixin.workflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.oa.jsflow.vo.ActivityVO;

public final class wf_005fgetnextactivityForWeiXin_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("com.js.oa.jsflow.vo.ActivityVO");
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
      response.setContentType("text/html; charset=utf-8");
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

String directSend = request.getAttribute("directSend")==null ? "0" : request.getAttribute("directSend").toString();
if("0".equals(directSend)){
	// 流程类型(业务流程1，随机流程0)
	String processType = request.getParameter("processType");
	String type = request.getParameter("type");//是否开始节点
	String tableId = request.getParameter("tableId");
	String recordId = request.getParameter("recordId");
	String curActivityId = request.getParameter("activityId");//当前节点
	com.js.oa.jsflow.vo.ActivityVO activityVO = null;
	com.js.oa.jsflow.service.WorkFlowBD workFlowBD = new com.js.oa.jsflow.service.WorkFlowBD();
	String activityIds = "";
	String transactType = null!=request.getAttribute("transactType") ? request.getAttribute("transactType").toString() : "";
	if(!"0".equals(processType)){
		if("getFirstActivity".equals(type)){
			if(request.getAttribute("activityIds")!=null && !"".equals(request.getAttribute("activityIds").toString())){
				activityIds=request.getAttribute("activityIds").toString();
			} else{
				activityVO = new com.js.oa.jsflow.util.WorkflowCommon().getFirstActivity(request);
				activityIds = activityIds+","+activityVO.getId();
			}
		} else{
			/*中间节点*/
			if(request.getAttribute("activityIds")!=null && !"".equals(request.getAttribute("activityIds").toString())){
				activityIds=request.getAttribute("activityIds").toString();
			}else{
			    // 下一节点的选择类型（用户决定还是流程决定）
		        String stepType = workFlowBD.getActivityType(curActivityId, tableId, recordId);
			    // 2010-05-13 取得下一个节点的类型，判断是否虚拟节点
			    String activityClass = workFlowBD.getNextActivityClass(curActivityId, tableId, recordId, "");
			    if(!activityClass.equals("0")){
			    	activityVO = new com.js.oa.jsflow.util.ProcessStep().getProceedNextActi(activityClass, tableId, recordId, request);
			    	activityIds = activityIds + "," + activityVO.getId();
			    }else if(stepType.equals("0")){	
			    	// 流程决定走向	    	
					activityVO = new com.js.oa.jsflow.util.ProcessStep().getProceedNextActi(curActivityId, tableId, recordId, request);
					activityIds = activityIds+","+activityVO.getId();
			    }
			}
		}
	}
	if("end".equals(transactType)){	// 后续节点没有办理人时，结束流程
		
      out.write("\r\n\t\t<script type=\"text/javascript\">\r\n\t\tparent.weixinMessageReminder(\"alert\", \"提示：\", \"下一节点没有办理人！\", \"window.parent.cmdEndOnlyComp();\");\r\n\t\t</script>\r\n\t\t");

	} else{
		
      out.write("\r\n\t\t<script type=\"text/javascript\">\r\n\t\twindow.parent.cmdSend2('");
      out.print(activityIds );
      out.write("');\t\r\n\t\t</script>\r\n\t\t");

	}
}else{
	//下一步骤办理人
	String selUser = request.getAttribute("selectUser")==null ? "" : request.getAttribute("selectUser").toString();
	
	if(selUser.indexOf(",") > 0){
		selUser = selUser.replaceAll(",","\\$\\$");
	}
	if(!selUser.startsWith("$")){
		selUser = "$" + selUser + "$";	
	}
	
	if("0".equals(request.getParameter("sendType"))){
		//开始节点
		ActivityVO nextActivityVO = (ActivityVO) request.getAttribute("nextActivityVO");
		
      out.write("\r\n\t\t<script type=\"text/javascript\">\r\n\t\t//开始节点\r\n\t\tparent.document.all.activityId.value = \"");
      out.print(nextActivityVO.getId());
      out.write("\";\r\n\t\tparent.document.all.activityName.value = \"");
      out.print(nextActivityVO.getName());
      out.write("\";\r\n\t\t\r\n\t\t//节点其他属性\r\n\t\tparent.document.all.allowStandFor.value = \"");
      out.print(nextActivityVO.getAllowStandFor());
      out.write("\";\r\n\t\tparent.document.all.allowCancel.value = \"");
      out.print(nextActivityVO.getAllowcancel());
      out.write("\";\r\n\t\tparent.document.all.pressType.value = \"");
      out.print(nextActivityVO.getPressType());
      out.write("\";\r\n\t\tparent.document.all.deadLineTime.value = \"");
      out.print(nextActivityVO.getDeadlineTime());
      out.write("\";\r\n\t\tparent.document.all.pressMotionTime.value = \"");
      out.print(nextActivityVO.getPressMotionTime());
      out.write("\";\r\n\t\t\r\n\t\t//发送页面获取属性\r\n\t\tvar approveMode = \"");
      out.print(request.getAttribute("transactType"));
      out.write("\"; //处理方式 并行\r\n\t\tparent.document.all.approveMode.value = approveMode;\r\n\t\tparent.document.all.emergence.value = \"0\" ;//缓急 默认一般\r\n\t\t\r\n\t\t//短信\r\n\t\t/*if(document.getElementById(\"smsDiv1\").style.display==\"\"){\r\n\t\t\r\n\t\t\tif(document.frm1.needSendMsg && document.frm1.needSendMsg.checked){\r\n\t\t\t\tparent.document.all.needSendMsg.value=document.frm1.needSendMsg.checked;//短信\r\n\t\t\t\tparent.document.all.smsContent.value=document.frm1.smsContent.value;//短信\r\n\t\t\t}\r\n\t\t}*/\r\n\t\t\r\n\t\t//tips\r\n\t\tparent.document.all.dealTips.value = \"\";//tips内容\r\n\t\t\r\n\t\t//流程办理期限\r\n\t\t/*if(frm1.needProcessDeadlineDate.checked){\r\n\t\t\tparent.document.all.processDeadlineDate.value=frm1.processDeadlineDate.value;\r\n\t\t} else{\r\n\t\t\tparent.document.all.processDeadlineDate.value=\"0\";\r\n\t\t}*/\r\n\t\t\r\n\t\tparent.document.all.processDeadlineDate.value = \"0\";\t\r\n\t\t\r\n\t\t//办理人ID、姓名\r\n\t\tvar selUser = \"");
      out.print(selUser);
      out.write("\";\r\n\t\t\r\n\t\t\r\n\t\tparent.document.all.operId.value = selUser;\r\n\t\tparent.document.all.operName.value = \"");
      out.print(request.getAttribute("selectUserName"));
      out.write("\";\r\n\t\t\r\n\t\t\t\r\n\t\tif(parent.document.all.activityId.value == '2'){\r\n\t\t\t//结束流程\r\n\t\t\twindow.close();\r\n\t\t\tparent.include_comp();\r\n\t\t} else{\r\n\t\t\tif(parent.document.all.operId.value == ''){\r\n\t\t\t\tparent.weixinMessageReminder(\"alert\", \"提示：\", \"请选择办理人！\", \"\");\r\n\t\t\t} else{\r\n\t\t\t\tif(parent.document.all.operName.value.indexOf(\",\", parent.document.all.operName.value.length-1) != -1){\r\n\t\t\t\t\tparent.document.all.operName.value = parent.document.all.operName.value.substring(0, parent.document.all.operName.value.length - 1);\r\n\t\t\t\t}\r\n\t\t\t\tif(approveMode == '2' && parent.document.all.operName.value.indexOf(\",\")!=-1){\r\n\t\t\t\t\tparent.weixinMessageReminder(\"alert\", \"提示：\", \"只能选择单人！\", \"\");\r\n\t\t\t\t}\r\n\t\t\t\tif(selUser.length>2){\r\n\t\t\t\t\tparent.include_save();\r\n\t\t\t\t}else{\r\n\t\t\t\t\tparent.weixinMessageReminder(\"alert\", \"提示：\", \"未找到合适的后续节点办理人，请选择办理人或与管理员联系！\", \"\");\r\n\t\t\t\t}\r\n\t\t\t}\r\n\t\t}\r\n\t\t</script>\r\n\t\t");

	}else{
		//中间节点
		ActivityVO nextActivityVO = (ActivityVO) request.getAttribute("nextActivityVO");
		
      out.write("\r\n\t\t<script type=\"text/javascript\">\r\n\t\t//中间节点\r\n\t\tparent.document.all.mainNextActivityId.value = \"");
      out.print(nextActivityVO.getId());
      out.write("\";\r\n\t\tparent.document.all.mainNextActivityName.value = \"");
      out.print(nextActivityVO.getName());
      out.write("\";\r\n\t\r\n\t\t//节点其他属性\r\n\t\tparent.document.all.mainAllowStandFor.value = \"");
      out.print(nextActivityVO.getAllowStandFor());
      out.write("\";\r\n\t\tparent.document.all.mainAllowCancel.value = \"");
      out.print(nextActivityVO.getAllowcancel());
      out.write("\";\r\n\t\tparent.document.all.mainPressType.value = \"");
      out.print(nextActivityVO.getPressType());
      out.write("\";\r\n\t\tparent.document.all.mainDeadLineTime.value = \"");
      out.print(nextActivityVO.getDeadlineTime());
      out.write("\";\r\n\t\tparent.document.all.mainPressMotionTime.value = \"");
      out.print(nextActivityVO.getPressMotionTime());
      out.write("\";\r\n\t\tparent.document.all.mainAllowTransition.value = \"");
      out.print(nextActivityVO.getAllowTransition());
      out.write("\";\r\n\t\t\t\t\r\n\t\t\r\n\t\t//发送页面获取属性\r\n\t\tvar approveMode = \"");
      out.print(request.getAttribute("transactType"));
      out.write("\"; //处理方式 并行\r\n\t\t\r\n\t\tparent.document.all.approveMode.value = approveMode;\r\n\t\tparent.document.all.emergence.value = \"0\";//缓急\r\n\t\t\t\t\t\r\n\t\t//短信\r\n\t\t/*if(document.getElementById(\"smsDiv1\").style.display==\"\"){\r\n\t\t\tif(document.frm1.needSendMsg && document.frm1.needSendMsg.checked){\r\n\t\t\t\tparent.document.all.needSendMsg.value=document.frm1.needSendMsg.checked;//短信\r\n\t\t\t\tparent.document.all.smsContent.value=document.frm1.smsContent.value;//短信\r\n\t\t\t}\r\n\t\t}*/\r\n\t\r\n\t\t//tips\r\n\t\tparent.document.all.dealTips.value = \"\";//tips内容\r\n\t\r\n\t\t//办理人ID、姓名\r\n\t\tvar selUser = \"");
      out.print(selUser);
      out.write("\";\r\n\t\t\t\r\n\t\tparent.document.all.operId.value = selUser;\r\n\t\tparent.document.all.operName.value = \"");
      out.print(request.getAttribute("selectUserName"));
      out.write("\";\r\n\t\t\r\n\t\tif(parent.document.all.mainNextActivityId.value == '2'){\r\n\t\t\t//结束流程\t\t\r\n\t\t\tparent.include_comp();\r\n\t\t} else{\r\n\t\t\tif(parent.document.all.operId.value == ''){\r\n\t\t\t\tparent.weixinMessageReminder(\"alert\", \"提示：\", \"请选择办理人！\", \"\");\r\n\t\t\t} else{\r\n\t\t\t\tif(parent.document.all.operName.value.indexOf(\",\",parent.document.all.operName.value.length-1)!=-1){\r\n\t\t\t\t\tparent.document.all.operName.value = parent.document.all.operName.value.substring(0,parent.document.all.operName.value.length-1);\r\n\t\t\t\t}\r\n\t\t\t\tif(approveMode=='2' && parent.document.all.operName.value.indexOf(\",\")!=-1){\r\n\t\t\t\t\tparent.weixinMessageReminder(\"alert\", \"提示：\", \"只能选择单人！\", \"\");\r\n\t\t\t\t}\r\n\t\t\t\tif(selUser.length > 2){\r\n\t\t\t\t\tparent.include_sub();\r\n\t\t\t\t}else{\r\n\t\t\t\t\tparent.weixinMessageReminder(\"alert\", \"提示：\", \"未找到合适的后续节点办理人，请选择办理人或与管理员联系！\", \"\");\r\n\t\t\t\t}\r\n\t\t\t}\r\n\t\t}\r\n\t\t</script>\r\n\t\t");

	}
}

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
