/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:59:16 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.weixin.workflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.oa.weixin.common.util.WorkflowForWeiXinUtil;

public final class workflow_005fcommonForWeiXin_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("com.js.oa.weixin.common.util.WorkflowForWeiXinUtil");
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

try{
	java.util.Map para = new java.util.HashMap();
	para.put("recordId",request.getParameter("recordId"));
	para.put("tableId",request.getParameter("tableId"));
	para.put("processId",request.getParameter("processId"));
	para.put("workId",request.getParameter("workId"));
	com.js.oa.jsflow.service.WorkFlowButtonBD wfbd = new com.js.oa.jsflow.service.WorkFlowButtonBD();
	wfbd.delWFOnlineUser(para);
}catch(Exception ex){
}
String backto = (String)request.getAttribute("backto");
boolean parentPageFlush = true;
if(request.getAttribute("subProcWorkId") != null){
    parentPageFlush = false;
    //子流程提交以后
    
      out.write("\r\n    <script language=\"javascript\">\r\n    if(parent.parent){\r\n        if(parent.parent.document.all.subProcWorkId){\r\n            parent.parent.document.all.subProcWorkId.value = \"");
      out.print(request.getAttribute("subProcWorkId"));
      out.write("\";\r\n        }\r\n    }\r\n    </script>\r\n    ");

}

if(request.getAttribute("passround") != null){
    if(request.getAttribute("passround").toString().equals("-1")){
    
      out.write("\r\n    <script language=\"javascript\">\r\n    alert(\"由于节点尚未有人办理，您的审阅意见未能保存！\");\r\n    </script>\r\n    ");

    }
}

if(request.getAttribute("repeat") != null){
	
      out.write("\r\n   \t<script language=\"javascript\">\r\n   \talert(\"文号重复！\");\r\n   \t</script>\r\n\t");

}

      out.write("\r\n<script language=\"javascript\">\r\n\t");

	if(parentPageFlush && !"1".equals(request.getParameter("fromdesktop"))){
		
      out.write("\r\n\t\ttry{   \r\n\t    \tbackto(\"");
      out.print(backto);
      out.write("\");\r\n\t\t}catch(e){}\r\n\t\t");

	}
	if("back".equals(request.getParameter("flag"))){
		//退回
		
      out.write("\r\n\t\talert(\"您已成功退回至");
      out.print(WorkflowForWeiXinUtil.decordStr(request.getParameter("backToActivityName")) );
      out.write("！\");\r\n\t\tbackto(\"");
      out.print(backto);
      out.write("\");\r\n\t\t");

	}
	if("1".equals(request.getParameter("fromdesktop"))){
		
      out.write("\r\n\t\ttry{\r\n\t\t    parent.refreshModFileDealWith();\r\n\t\t}catch(e){ }\r\n\t\ttry{\r\n\t\t\tparent.MainDesktop.refreshModFileDealWith();\r\n\t\t}catch(e){ }\r\n\t\t");

	}

	if("feedback".equals(request.getParameter("flag")) || "press".equals(request.getParameter("flag"))){
		//如果是催办按钮则不关闭按钮
	}else{
		if(request.getAttribute("flowfaild") != null){
			if("2".equals(request.getAttribute("flowfaild").toString())){
				//归档失败
				
      out.write("\r\n\t\t\t\talert(\"归档失败，请检查表单数据或与管理员联系！\");\r\n\t\t\t\ttry{\r\n\t\t    \t\tbackto(\"");
      out.print(backto);
      out.write("\");\r\n\t\t    \t}catch(e){ }\r\n\t\t\t\t");

			}else{
				String errorMessage = "流程提交失败，请检查表单数据重新提交或与管理员联系！";
				if(request.getAttribute("ErrorMessage")!=null && !"".equals(request.getAttribute("ErrorMessage").toString()) && !"null".equals(request.getAttribute("ErrorMessage").toString())){
					errorMessage = request.getAttribute("ErrorMessage").toString();				
				}
		    	
      out.write("\r\n\t\t    \talert(\"");
      out.print(errorMessage);
      out.write("\");\r\n\t\t    \ttry{\r\n\t\t        \tbackto(\"");
      out.print(backto);
      out.write("\");\r\n\t\t    \t}catch(e){ }\r\n\t\t    \t");

			}
		} else if("1".equals(request.getAttribute("savefirst")+"")){
	  		//保存第一步
			response.sendRedirect(request.getAttribute("sendUrl")+"&subProcWorkId="+request.getAttribute("subProcWorkId"));
		} else if("1".equals(request.getParameter("sendContinue"))){
	  		//发送继续
		 	response.sendRedirect(request.getParameter("sendContinueUrl"));
		} else{
	    	
      out.write("\r\n\t    \ttry{\r\n\t    \t\tbackto(\"");
      out.print(backto);
      out.write("\");\r\n\t    \t}catch(e){ }\r\n\t    \t");

		}
	}
	
      out.write("\r\n\t\r\n\tfunction backto(backto){\r\n     \tif(\"dbsx\" == backto){\t// 待办事项\r\n\t    \tlocation.href = \"/jsoa/weiXinBacklogAction.do?action=list&status=0\";\r\n\t    } else if(\"dysx\" == backto){\t// 待阅事项\r\n\t    \tlocation.href = \"/jsoa/weiXinBacklogAction.do?action=list&status=2\";\r\n\t    } else if(\"ybsx\" == backto){\t// 已办事项\r\n\t    \tlocation.href = \"/jsoa/weiXinBacklogAction.do?action=list&status=101&from=mine\";\r\n\t    } else if(\"yysx\" == backto){\t// 已阅事项\r\n\t    \tlocation.href = \"/jsoa/weiXinBacklogAction.do?action=list&status=102&from=mine\";\r\n\t    } else if(\"yfzb\" == backto){\t// 已发在办\r\n\t    \tlocation.href = \"/jsoa/weiXinBacklogAction.do?action=mine&workStatus=1\";\r\n\t    } else if(\"yfbj\" == backto){\t// 已发办结\r\n\t    \tlocation.href = \"/jsoa/weiXinBacklogAction.do?action=mine&workStatus=100\";\r\n\t    } else if(\"yfth\" == backto){\t// 已发退回\r\n\t    \tlocation.href = \"/jsoa/weiXinBacklogAction.do?action=mine&workStatus=-1\";\r\n\t    } else if(\"yfqx\" == backto){\t// 已发取消\r\n\t    \tlocation.href = \"/jsoa/weiXinBacklogAction.do?action=mine&workStatus=-2\";\r\n\t    } else{\t// 否则认为是消息提醒过来的，关闭窗口\r\n");
      out.write("\t       parent.include_close();\r\n\t\t}\r\n\t}\r\n</script>");
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