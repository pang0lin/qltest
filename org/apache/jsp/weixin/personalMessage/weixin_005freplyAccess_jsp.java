/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:58:34 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.weixin.personalMessage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.oa.weixin.common.service.WeiXinBD;
import com.js.oa.online.service.ChatDB;
import com.js.wap.util.WapUtil;
import com.js.util.util.DataSourceBase;

public final class weixin_005freplyAccess_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("com.js.oa.weixin.common.service.WeiXinBD");
    _jspx_imports_classes.add("com.js.oa.online.service.ChatDB");
    _jspx_imports_classes.add("com.js.wap.util.WapUtil");
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
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n");

request.setCharacterEncoding("UTF-8");
String path = request.getContextPath();
String beginIndex = request.getParameter("beginIndex")==null ? "0" : request.getParameter("beginIndex");
String loginType = null==session.getAttribute("loginType") ? "wap" : session.getAttribute("loginType").toString();
String messageFrom = null!=request.getParameter("from") ? request.getParameter("from") : "";
String title = "";
String message = "";
if(null == request.getParameter("action")){

	String userId = (String)session.getAttribute(WapUtil.EMP_ID);
	String toIDs = request.getParameter("toIDs");
	String content = request.getParameter("contents");
	String userNames = request.getParameter("userNames");
	String userName = "";
	if(toIDs.startsWith("$")) toIDs = toIDs.substring(1, toIDs.length());
	if(toIDs.endsWith("$")) toIDs = toIDs.substring(0, toIDs.length() - 1);
	toIDs = toIDs.replace("$$", ",");
	
	if(null==content || "".equals(content)){
	    message="???????????????????????????????????????";
		title="??????";
	} else if(null==toIDs || "".equals(toIDs)){
	    message="????????????????????????????????????";
		title="??????";
	} else{
		try {
			com.js.oa.weixin.common.service.WeiXinBD bd=new com.js.oa.weixin.common.service.WeiXinBD();
			ChatDB chatDB = new ChatDB();
			userName = chatDB.getName(userId);
			bd.replyMessage(userId, userName, toIDs, content, userNames);
			message="???????????????";
			title="??????";
		} catch (Exception e) {
		    e.printStackTrace();
	        message="???????????????????????????";
	        title="??????";	
		}
	}
	if(false && "??????".equals(title) && "1".equals(messageFrom)){	// ??????????????????????????????????????????????????????
		
      out.write("\r\n\t\t<script type=\"text/javascript\">\r\n\t\t   closeWindow();\r\n\t\t</script>\r\n\t\t");

	} else if("??????".equals(title)) {	// ????????????????????????????????????????????????????????????
		//response.sendRedirect(path+"/weiXinMessageAction.do?method=personalMessageList&from=" + messageFrom + "&beginIndex=" + beginIndex);
	}
} else{	// ??????
	String chatId = request.getParameter("chatId");
	String userId = (String)session.getAttribute(WapUtil.EMP_ID);
	DataSourceBase dataSourceBase = new DataSourceBase();
	try{
		dataSourceBase.begin();
		dataSourceBase.executeUpdate("delete from oa_chat_user where chat_status=0 and emp_id=" + userId + " and chat_id=" + chatId);
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		dataSourceBase.end();
	}
	title = "??????";
	//response.sendRedirect(path+"/weiXinMessageAction.do?method=personalMessageList&from=" + messageFrom + "&beginIndex=" + beginIndex);
}

      out.write("\r\n<!DOCTYPE HTML>\r\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/wap2/commonImport.jsp", out, false);
      out.write("\r\n<HTML>\r\n\t<HEAD>\r\n\t\t<title>????????????</title>\r\n\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\" />\r\n\t\t<link rel=\"stylesheet\" href=\"/jsoa/css/weixin/weixin_main.css\">\r\n\t</HEAD>\r\n\t\r\n\t<body onload=\"setHeader('javascript:closeWindow();','????????????');\">\r\n\t\t<div class=\"form\">\r\n\t\t\t<br>\r\n\t\t\t<div class='infoTitle'>");
      out.print(message);
      out.write("</div>\r\n\t\t</div>\r\n\r\n\t\t<!-- ?????? -->\r\n\t\t");

		if("??????".equals(title)){	
			
      out.write("\r\n\t\t\t<script type=\"text/javascript\">\r\n\t\t\t");

			if("weixin".equals(loginType)){
				
      out.write("closeWindow();");

			} else if("wap".equals(loginType)){
				if("0".equals(messageFrom)){
			
      out.write("\r\n\t\t\t\twindow.location.href=\"/jsoa/weiXinMessageAction.do?method=personalMessageList&from=0\";\r\n\t\t\t");

				}else if("1".equals(messageFrom)){
			
      out.write("\r\n\t\t\twindow.location.href=\"/jsoa/weiXinMessageAction.do?method=personalMessageList&from=1\";\r\n\t\t\t");

				}else{
			
      out.write("\r\n\t\t\t\t\tloadURL('grxx');\r\n\t\t\t");

				}
			}
			
      out.write("\r\n\t\t\t</script>\r\n\t\t\t");

		} else {	// ???????????????????????????????????????????????????????????????
			
      out.write("\r\n\t\t\t<div class=\"footer\">\r\n\t\t      \t<div class=\"buttons\">\r\n\t\t      \t\t\r\n\t\t      \t\t");

					if("??????".equals(title)){	// ?????????????????????
						
      out.write("\r\n\t\t\t\t\t\t<div class=\"button gray\" onclick=\"javascript:closeWindow();\">??????</div>\r\n\t\t\t\t\t\t");

					} else if(!"??????".equals(title)){	// ???????????????????????????????????????????????????????????????
						
      out.write("\r\n\t\t\t\t\t\t<div id=\"closeWindow\" class=\"button gray\" onclick=\"closeWindow();\">??????</div>\r\n\t\t\t\t\t\t");

					}
					
      out.write("\r\n\t\t \t\t</div>\r\n\t   \t\t</div>\r\n\t\t\t");

		}
		
      out.write("\r\n\t</body>\r\n\t");

	String loginType2017 = null==session.getAttribute("loginType") ? "" : session.getAttribute("loginType").toString();
	if(!"weixin".equals(loginType2017)){
	  out.print("<script type=\"text/javascript\">window.history.forward(1);</script>");
	}
	
      out.write("\r\n</html>");
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
