/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:06:43 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.util.config.SystemCommon;
import com.js.oa.portal.service.CustomDesktopBD;
import java.util.List;

public final class showDealTips_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("java.util.List");
    _jspx_imports_classes.add("com.js.oa.portal.service.CustomDesktopBD");
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
      response.setContentType("text/html; charset=GBK");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\" />\r\n<title>办理提示</title>\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n</head>\r\n\r\n<body scroll=\"no\">\r\n<table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" id=\"pup_main\">\r\n  \r\n  <tr>\r\n    <td align=\"center\" valign=\"top\"><br>\r\n\t\t  <table width=\"100%\" border=\"0\" cellpadding=\"4\" cellspacing=\"4\">\r\n\t\t  ");
if(SystemCommon.getBackComment().equals("1") && request.getParameter("resubmit")!=null && "1".equals(request.getParameter("resubmit"))){
		  List<String[]> commentList = new com.js.oa.jsflow.service.WorkFlowButtonBD().getAllComment(request.getParameter("work")); 
      out.write("\r\n\t\t  <tr><td width=\"80\" valign=\"top\" align=\"right\">办理意见：</td>\r\n\t\t  <td><table width=\"100%\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\">\r\n\t\t  ");
for(int i=0;i<commentList.size()-1;i++){
		  	String[] obj = commentList.get(i);  
      out.write("\r\n\t\t    <tr><td width=\"80\" valign=\"top\" align=\"left\">");
      out.print(obj[0] );
      out.write("：</td><td>\r\n\t\t  \t<div style=\"width:100%;float:right\">");
      out.print(obj[1] );
      out.write("</div>\r\n\t\t  \t<div style=\"width:100%;float:left\">办理人：");
      out.print(obj[3] );
      out.write("&nbsp;&nbsp;时间：");
      out.print(obj[2] );
      out.write("</div></td></tr>\r\n\t\t  ");
} 
      out.write("</table>\r\n\t\t  </td>\r\n\t\t  </tr>\r\n\t\t  ");
} 
      out.write("\r\n            <tr>\r\n\t\t\t");

				//显示办理提示
				String dealTips;
				String title="办理提示";
				if(request.getParameter("logId")!=null){
					dealTips=new com.js.oa.jsflow.service.WorkFlowButtonBD().getDealTipsByLogId(request.getParameter("logId"));
					title="退回意见";
				}else{
			    	dealTips= new com.js.oa.jsflow.service.WorkFlowButtonBD().getDealTipsByWorkId(request.getParameter("work"));
				}
				dealTips = com.js.util.util.CharacterTool.escapeHTMLTagsSimple(dealTips==null?"取消流程已处理":dealTips);
				
				//如果办理提示是一串数字，则将办理提示置为空
				if(dealTips.length()>12){
					try{
						Long.parseLong(dealTips);
						dealTips="";
					}catch(Exception ex){
						
					}
				}
			
      out.write("\r\n\t\t\t  <td width=\"80\" valign=\"top\" align=\"right\">");
      out.print(title );
      out.write("：</td>\r\n              <td >");
      out.print(dealTips);
      out.write("</td>\r\n            </tr>\r\n            <tr>\r\n            ");
 
            
            //显示取消原因
            String backdetails;
           	String title2="取消原因";
           	CustomDesktopBD cdb=new CustomDesktopBD();
			List list=cdb.getSendFileUrlParameters(request.getParameter("work"));
			if(list!=null && list.size()>0){
           	Object[] obj=(Object[])list.get(0);
           if("-2".equals(obj[38].toString())){
           	backdetails=new com.js.oa.jsflow.service.WorkFlowBD().getCancelReason(request.getParameter("work"));
           
           	//如果取消原因是一串数字，则将办理提示置为空
				if(backdetails.length()>12){
					try{
						Long.parseLong(backdetails);
						backdetails="";
					}catch(Exception ex){
						
					}
				}
           
           
      out.write("\r\n           \t<td width=\"80\" valign=\"top\" align=\"right\">");
      out.print(title2 );
      out.write("：</td>\r\n              <td >");
      out.print(backdetails);
      out.write("</td>\r\n           \r\n           \r\n           ");
 }
			}
            
      out.write("\r\n            \r\n            </tr>\r\n      </table>\r\n    </td>\r\n  </tr>\r\n  <tr>\r\n    <td valign=\"top\" align=\"right\" height=\"70\">\r\n      <input name=\"button\" type=\"button\" class=\"btnButton2font\" value=\"确定\" onclick=\"window.close();\">\r\n      &nbsp;&nbsp;&nbsp;\r\n    </td>\r\n  </tr>\r\n</table>\r\n</body>\r\n</html>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
