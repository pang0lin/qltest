/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:00:10 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.weixin.doc;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import com.js.wap.util.WapUtil;

public final class docList_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.net.URLEncoder");
    _jspx_imports_classes.add("com.js.wap.util.WapUtil");
    _jspx_imports_classes.add("java.text.SimpleDateFormat");
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

      out.write("\r\n\r\n\r\n\r\n\r\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE html>\r\n");

	request.setCharacterEncoding("UTF-8");
	String path = request.getContextPath();
	List list = (List) request.getAttribute("myList");
	int size = Integer.valueOf(request.getAttribute("size").toString());
	int beginIndex = Integer.parseInt(request.getParameter("beginIndex")==null ? "0" : request.getParameter("beginIndex"));
	String keyword = null!=request.getAttribute("keyword") ? request.getAttribute("keyword").toString() : "";

      out.write("\r\n<HTML xmlns=\"http://www.w3.org/1999/xhtml\">\r\n\t<HEAD>\r\n\t\t<TITLE>我的收文</TITLE>\r\n\t\t<META content=\"text/html; charset=UTF-8\" http-equiv=\"Content-Type\">\r\n\t\t<META name=\"viewport\" content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\">\r\n\t\t<META name=\"apple-touch-fullscreen\" content=\"YES\">\r\n\t\t<META name=\"apple-mobile-web-app-capable\" content=\"no\">\r\n\t\t<META name=\"GENERATOR\" content=\"MSHTML 8.00.6001.19154\">\r\n        \r\n\t\t<link rel=\"stylesheet\" href=\"/jsoa/css/weixin/weixin_main.css\">\r\n\t\t<style type=\"text/css\">\r\n\t \t\t\t.nextStep{\r\n\t       \t \t\tdisplay: none;\r\n\t        \t\twidth: 100%;\r\n\t\t\t\t\theight: 100%;\r\n\t\t\t\t\tposition: fixed;\r\n\t\t\t\t\ttop: 0;\r\n\t\t\t\t\tleft: 0;\r\n\t\t\t\t\tz-index: 100;\r\n\t\t\t\t\tmargin: 0;\r\n\t\t\t\t\tpadding: 0;\r\n\t\t\t\t\toverflow: auto;\r\n\t\t\t\t}\r\n\t\t\t\t</style>\r\n\r\n\t\t<link rel=\"stylesheet\" href=\"/jsoa/css/weixin/search.css\">\r\n\t\t<script type=\"text/javascript\" src=\"/jsoa/js/weixin/mobiscroll/js/jquery-1.9.1.min.js\" ></script>\r\n\t\t<SCRIPT language=\"JavaScript\">\r\n\t\tfunction gotoPage(url, flag) {\r\n\t\t\tif (flag) {\r\n");
      out.write("\t\t\t\tlocation.href = encodeURI(url);\r\n\t\t\t}\r\n\t\t}\r\n\t\t\r\n\r\n\t\tfunction submitForm() {\r\n\t\t\tvar k = document.getElementById(\"keys\").value;\r\n\t\t\tif (\"\" != k) {\r\n\t\t\t\tk = encodeURI(k);\r\n\t\t\t}\r\n\t\t\tdocument.getElementById(\"keyword\").value = k;\r\n\t\t\tdocument.getElementById(\"searchForm\").submit();\r\n\t\t}\r\n\t\t</SCRIPT>\r\n\t</HEAD>\r\n\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/wap2/commonImport.jsp", out, false);
      out.write("\r\n\t<BODY onload=\"setHeader('javascript:closeWindow()','我的收文');\">\r\n\t\t<!-- 搜索 -->\r\n\t\t<FORM id=\"searchForm\" action=\"/jsoa/weixinGovReceiveFileBoxAction.do?action=list\" method=\"post\">\r\n\t\t\t<div class=\"sousuo\">\r\n\t\t\t\t<div class=\"sousuo-01\">\r\n\t\t\t\t\t<INPUT type=\"hidden\" id=\"keyword\" name=\"keyword\" />\r\n\t\t\t\t\t<INPUT type=\"text\" id=\"keys\" name=\"keys\" value=\"");
      out.print(keyword);
      out.write("\" placeholder=\"请输入标题\" />\r\n\t\t\t\t\t<div class=\"sousuo-11\" onclick=\"javascript:submitForm();\"><img src=\"images/weixin/ss.png\"></div>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t</FORM>\r\n\r\n\t\t<!-- 信息列表 -->\r\n\t\t<div class=\"list\">\r\n\t\t\t");

			if (null != list && list.size() > 0) {
				SimpleDateFormat format = new SimpleDateFormat("MM-dd");
				for (int i = 0; i < list.size(); i++) {
				    Object[] obj = (Object[])list.get(i); 
				    String id = obj[0]+"";
				
					String content = (String)obj[2];
					if (content.length() > 14)
						content = content.substring(0, 14) + "...";
					
      out.write("\r\n\t\t\t\t\t<a style=\"text-decoration: none;\"\r\n\t\t\t\t\t\thref=\"javascript:showContent('");
      out.print(id);
      out.write("');\">\r\n\t\t\t\t\t\t<div class=\"item\">\r\n\t\t\t\t\t\t\t<div class=\"content\">\r\n\t\t\t\t\t\t\t\t<div class=\"first\">\r\n\t\t\t\t\t\t\t\t\t<div class=\"title\">");
      out.print(content);
      out.write("</div>\r\n\t\t\t\t\t\t\t\t\t<div class=\"time\">");
      out.print(format.format(obj[4]));
      out.write("</div>\r\n\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t<div class=\"second\"></div>\r\n\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t</a>\r\n\t\t\t\t\t");

				}
			} else {
				
      out.write("\r\n\t\t\t\t<div class=\"item\">\r\n            \t\t<div class=\"nodata\">没有查询到数据！</div>\r\n         \t\t</div>\r\n\t\t\t\t");

			}
			
      out.write("\r\n\t\t</div>\r\n\r\n\t\t<!-- 分页 -->\r\n\t\t");

		int curNum = beginIndex/WapUtil.LIMITED + 1;	// 当前页数
		int totalNum = size%WapUtil.LIMITED > 0 ? size/WapUtil.LIMITED+1 : size/WapUtil.LIMITED;	// 总页数
		int upNum = (beginIndex/WapUtil.LIMITED) - 1;
		int upIndex = upNum * WapUtil.LIMITED;
		int nextNum = (beginIndex/WapUtil.LIMITED) + 1;
		int nextIndex = nextNum * WapUtil.LIMITED;

		String upUrl = "#", nextUrl = "#", upUrlFont = "", nextUrlFont = "";
		if (size > WapUtil.LIMITED) {
			if (upIndex >= 0){
				upUrl = path + "/weixinGovReceiveFileBoxAction.do?action=list&amp;beginIndex=" + upIndex;
				if (null != keyword && !"".equals(keyword))
					upUrl += "&keyword=" + URLEncoder.encode(URLEncoder.encode(keyword, "utf-8"), "utf-8");
			} else{
				upUrl = "javascript:void(0);";
				upUrlFont = "grayFont";
			}
			if (nextIndex < size){
				nextUrl = path + "/weixinGovReceiveFileBoxAction.do?action=list&amp;beginIndex=" + nextIndex;
				if (null != keyword && !"".equals(keyword))
					nextUrl += "&keyword=" + URLEncoder.encode(URLEncoder.encode(keyword, "utf-8"), "utf-8");
			} else{
				nextUrl = "javascript:void(0);";
				nextUrlFont = "grayFont";
			}
			
      out.write("\r\n\t\t\t<div class=\"bottomDiv\">\r\n\t\t\t\t<a href=\"");
      out.print(upUrl );
      out.write("\">\r\n\t\t\t\t\t<div class=\"up ");
      out.print(upUrlFont );
      out.write("\">上一页</div>\r\n\t\t\t\t</a>\r\n\t\t\t\t<div class=\"page grayFont\">");
      out.print(curNum );
      out.write('/');
      out.print(totalNum );
      out.write("</div>\r\n\t\t\t\t<a href=\"");
      out.print(nextUrl );
      out.write("\">\r\n\t\t\t\t\t<div class=\"down ");
      out.print(nextUrlFont );
      out.write("\">下一页</div>\r\n\t\t\t\t</a>\r\n\t\t\t</div>\r\n\t\t\t");

			}
		
      out.write("\r\n\t\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/weixin/common/weixin_messageReminders.jsp", out, false);
      out.write("\r\n\t\t<div id=\"commonPopDiv\" class=\"nextStep\" style=\"text-align: center;vertical-align: middle;\">\r\n\t    \t\t<iframe id=\"commonPopIframe\" style=\"width: 100%;height: 100%;border: none;\"></iframe>\r\n\t\t</div>\r\n\t</body>\r\n\t<SCRIPT type=\"text/javascript\">\r\n\tfunction showContent(id){\r\n\t   var url = \"/jsoa/weixinGovReceiveFileBoxAction.do?action=load&from=list&id=\" + id;\r\n\t   window.location.href = url;\r\n\t}\r\n\t</SCRIPT>\r\n</html>");
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
