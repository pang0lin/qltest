/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:00:31 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.weixin.cooperate;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.wap.util.WapUtil;
import com.js.cooperate.po.NodeOpinionPO;
import com.js.cooperate.bean.CooperateBean;
import java.util.Date;

public final class weixin_005fdealwithAccessForMessage_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("java.util.Date");
    _jspx_imports_classes.add("com.js.wap.util.WapUtil");
    _jspx_imports_classes.add("com.js.cooperate.bean.CooperateBean");
    _jspx_imports_classes.add("com.js.cooperate.po.NodeOpinionPO");
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
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
String message = "";
String title = "";
String comeFrom = request.getParameter("comeFrom");

//提交处理意见及新增加的后续处理人（只增加直接后继）
   String userId = (String) session.getAttribute(WapUtil.EMP_ID);
String userName = (String) session.getAttribute(WapUtil.EMP_NAME);

String bodyId = request.getParameter("bodyId");
String memberId = request.getParameter("memberId");
String content = request.getParameter("reply_content");
String status = request.getParameter("status");

String type = request.getParameter("type");
if(content.equals("") || content==null){
    content = "已阅";
}
String selPersonIds = request.getParameter("selPersonIds");
String selPersonNames = request.getParameter("selPersonNames");
String[] attachName = request.getParameterValues("attachName");
String[] attachSaveName = request.getParameterValues("attachSaveName");
String tracker = request.getParameter("tracker")==null ? "0" : "1";

NodeOpinionPO nopo = new NodeOpinionPO();
nopo.setBodyId(Long.valueOf(bodyId));
nopo.setContent(content);
nopo.setEmpId(Long.valueOf(userId));
nopo.setEmpName(userName);
nopo.setSendTime(new Date());
nopo.setPreId(Long.valueOf(0));

if(request.getParameter("isHidden") == null){
	nopo.setIsHidden(Integer.valueOf(0));
}else{
	nopo.setIsHidden(Integer.valueOf(1));
}

CooperateBean cbean = new CooperateBean();

try {
	cbean.dealwith(nopo, memberId, tracker, selPersonIds, selPersonNames, attachName, attachSaveName, null);
	message = "办理成功，请关闭！";
	title = "成功";
} catch (Exception e) {
       message = "办理失败，请重试！";
       title = "失败";	
}

      out.write("\r\n<!DOCTYPE html>\r\n<HTML xmlns=\"http://www.w3.org/1999/xhtml\">\r\n\t<HEAD>\r\n\t\t<TITLE>");
      out.print(title );
      out.write("</TITLE>\r\n\r\n\t\t<link rel=\"stylesheet\" href=\"/jsoa/css/weixin/weixin_main.css\">\r\n\r\n\t\t<script type=\"text/javascript\" src=\"/jsoa/js/weixin/zepto.js\"></script>\r\n\t\t<script type=\"text/javascript\" src=\"/jsoa/js/weixin/wx.js\"></script>\r\n\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\" />\r\n\t</head>\r\n\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/wap2/commonImport.jsp", out, false);
      out.write("\r\n\t<body style=\"vertical-align: middle;\" onload=\"setHeader('javascript:closeWindow();','");
      out.print(title );
      out.write("');\">\r\n\t    <div class=\"form\">\r\n\t\t\t<div class=\"item\">\r\n\t\t\t\t<div class=\"content\">");
      out.print(message );
      out.write("</div>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t\r\n\t\t<div class=\"footer\">\r\n\t  \t\t<div class=\"buttons\">\r\n\t            <div id=\"closeWindow\" class=\"button gray\">关闭</div>\r\n\t        </div>\r\n\t    </div>\r\n\t</body>\r\n\t\r\n\t<script type=\"text/javascript\">\r\n\t\r\n\t</script>\r\n</html>");
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