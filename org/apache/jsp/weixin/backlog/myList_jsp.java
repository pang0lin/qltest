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
import com.js.oa.weixin.common.util.WorkflowForWeiXinUtil;
import java.net.URLEncoder;
import com.js.wap.util.WapUtil;
import java.text.SimpleDateFormat;
import java.util.*;

public final class myList_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("com.js.oa.weixin.common.util.WorkflowForWeiXinUtil");
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
      response.setContentType("text/html;charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n<!DOCTYPE html>\r\n");

response.setHeader("Cache-Control", "no-store");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

request.setCharacterEncoding("UTF-8");
List list = (List) request.getAttribute("itemList");

String action = null!=request.getParameter("action") ? request.getParameter("action") : "list";

int beginIndex = Integer.parseInt(request.getParameter("beginIndex")==null ? "0" : request.getParameter("beginIndex"));
String showDoc = request.getParameter("showDoc")!=null && "doc".equals(request.getParameter("showDoc")) ? "&amp;showDoc=doc" : "";
String keyword = null!=request.getAttribute("keyword") ? request.getAttribute("keyword").toString() : "";
String workStatus = null!=request.getAttribute("workStatus") ? request.getAttribute("workStatus").toString() : "";
String pageTitle = "已发事项";
String backto = "";
if("1".equals(workStatus)){
	pageTitle = "已发在办";
	backto = "yfzb";
} else if("100".equals(workStatus)){
	pageTitle = "已发办结";
	backto = "yfbj";
} else if("-1".equals(workStatus)){
	pageTitle = "已发退回";
	backto = "yfth";
} else if("-2".equals(workStatus)){
	pageTitle = "已发取消";
	backto = "yfqx";
}

      out.write('\r');
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/wap2/commonImportOther.jsp", out, false);
      out.write("\r\n<html>\r\n\t<head>\r\n\t    <base href=\"");
      out.print(basePath);
      out.write("\">\r\n\t    \r\n\t    <title>");
      out.print(pageTitle );
      out.write("</title>\r\n\t    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\" />\r\n\t\t<meta http-equiv=\"pragma\" content=\"no-cache\">\r\n\t\t<meta http-equiv=\"cache-control\" content=\"no-cache\">\r\n\t\t<meta http-equiv=\"expires\" content=\"0\">\r\n\t\t<link rel=\"stylesheet\" href=\"/jsoa/css/weixin/search.css\">\r\n\t</head>\r\n\t<SCRIPT language=\"JavaScript\">\r\n\tfunction gotoPage(url, flag){\r\n\t    if(flag){\r\n\t        location.href = encodeURI(url);\r\n\t    }\r\n\t}\r\n\tfunction showInfo(url){\r\n\t\tlocation.href = url;\r\n\t}\r\n\t\r\n\tfunction submitForm(){\r\n\t\tvar k = document.getElementById(\"keys\").value;\r\n\t\tif(\"\" != k){\r\n\t\t\tk = encodeURI(k);\r\n\t\t}\r\n\t\tdocument.getElementById(\"keyword\").value = k;\r\n\t\tdocument.getElementById(\"searchForm\").submit();\r\n\t}\r\n\t</SCRIPT>\r\n\t\r\n\t<body onload=\"setHeader('javascript:closeWindow();', '");
      out.print(pageTitle );
      out.write("');\">\r\n\t\t<!-- 搜索 -->\r\n\t\t<FORM id=\"searchForm\" action=\"/jsoa/weiXinBacklogAction.do?action=mine&workStatus=");
      out.print(workStatus );
      out.write("\" method=\"post\">\r\n\t\t\t<div class=\"sousuo\">\r\n\t\t\t\t<div class=\"sousuo-01\">\r\n\t\t\t\t\t<INPUT type=\"hidden\" id=\"keyword\" name=\"keyword\" />\r\n\t\t\t\t\t<INPUT type=\"text\" id=\"keys\" name=\"keys\" value=\"");
      out.print(keyword);
      out.write("\" placeholder=\"请输入标题\" />\r\n\t\t\t\t\t<div class=\"sousuo-11\" onclick=\"submitForm()\"><img src=\"images/weixin/ss.png\"></div>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t</FORM>\r\n\t\t\r\n\t\t<!-- 信息列表 -->\r\n\t\t<div class=\"list\">\r\n\t\t\t");

			if (list != null && list.size() > 0) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
				Object[] obj;
				String mainURL = "";
				String title = "";
				String time = "";
				String url = "";
				for (int i = 0; i < list.size(); i++) {
					obj = (Object[]) list.get(i);
					title = obj[2].toString();
					mainURL = obj[16].toString();
					time = obj[5].toString();
					url = "weixin/common/getUserAvatar.jsp?userId=" + obj[37] + "&time="+System.currentTimeMillis();
					if (time.length() > 10)
						time = time.substring(5, 10);
					if (title.length() > 12) {
						title = title.substring(0, 12) + "...";
					}
					if (mainURL.indexOf("BodyAction.do") > -1) {
						//协同工作
						mainURL = mainURL.substring(mainURL.indexOf("bodyId="));
						mainURL = mainURL.replaceAll("\\&", "\\&amp;");
						mainURL = path + "/weiXinCoopAction.do?action=toDealwith&amp;" + mainURL + "&amp;from=" + action
								+ "&amp;status=" + workStatus + "&amp;comeFrom=1&amp;type=workdealwith&amp;beginIndex=" + beginIndex;
					} else if(mainURL.indexOf("WorkFlowProcAction.do")>-1 || mainURL.indexOf("WorkFlowReSubmitAction.do")>-1){
					    mainURL = "javascript:parentOpen('" + path + WorkflowForWeiXinUtil.getUrlByWorkId(obj[10].toString())+"&backto=" + backto + "')";
					} else {
						mainURL = "javascript:parentOpen('" +path + "/weixin/backlog/item_info.jsp?workId=" + obj[10] + "&amp;from=" + action + "&amp;beginIndex=" + beginIndex
								+ "&amp;workStatus=" + workStatus + "&amp;" + showDoc+ "')";
					}
					
      out.write("\r\n\t\t\t\t\t<a style=\"text-decoration: none;\" href=\"");
      out.print(mainURL);
      out.write("\">\r\n\t\t\t\t\t\t<div class=\"item\">\r\n\t\t\t\t\t\t\t<div class=\"picture\">\r\n\t\t\t\t\t\t\t\t<img alt=\"头像\" src=\"");
      out.print(url );
      out.write("\">\r\n\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t<div class=\"content\">\r\n\t\t\t\t\t\t\t\t<div class=\"first\">\r\n\t\t\t\t\t\t\t\t\t<div class=\"title\">");
      out.print(title );
      out.write("</div>\r\n\t\t\t\t\t\t\t\t\t<div class=\"time\">");
      out.print(time );
      out.write("</div>\r\n\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t<div class=\"second\">当前状态：");
      out.print(obj[1] );
      out.write("</div>\r\n\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t</a>\r\n\t\t\t\t\t");

				}
			} else {
				
      out.write("\r\n\t\t\t\t<div class=\"item\">\r\n\t\t\t\t\t<div class=\"nodata\">\r\n\t\t\t\t\t\t没有查询到数据！</p>\r\n\t\t\t\t\t</div>\r\n\t\t\t\t</div>\r\n\t\t\t\t");

			}
			
      out.write("\r\n\t\t</div>\r\n\t\t\r\n\t\t<!-- 分页 -->\r\n\t\t");

		int recordCount = Integer.parseInt(request.getAttribute(WapUtil.RECORD_COUNT) == null ? "0" : request.getAttribute(WapUtil.RECORD_COUNT).toString());
		int curNum = beginIndex/WapUtil.LIMITED + 1;	// 当前页数
		int totalNum = recordCount%WapUtil.LIMITED > 0 ? recordCount/WapUtil.LIMITED+1 : recordCount/WapUtil.LIMITED;	// 总页数
		int nextNum = (beginIndex / WapUtil.LIMITED) + 1;
		int nextIndex = nextNum * WapUtil.LIMITED;
		int upNum = (beginIndex / WapUtil.LIMITED) - 1;
		int upIndex = upNum * WapUtil.LIMITED;
		String upUrl = "#", nextUrl = "#", upUrlFont = "", nextUrlFont = "";
		
		if (recordCount > WapUtil.LIMITED) {
			if (upIndex >= 0){
				upUrl = path + "/weiXinBacklogAction.do?action=" + action + "&amp;workStatus=" + workStatus + "&amp;beginIndex=" + upIndex + showDoc;
				if (null != keyword && !"".equals(keyword))
					upUrl += "&keyword=" + URLEncoder.encode(URLEncoder.encode(keyword, "utf-8"), "utf-8");
			} else{
				upUrl = "javascript:void(0);";
				upUrlFont = "grayFont";
			}
			if (nextIndex < recordCount){
				nextUrl = path + "/weiXinBacklogAction.do?action=" + action + "&amp;workStatus=" + workStatus + "&amp;beginIndex=" + nextIndex + showDoc;
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
		
      out.write("\r\n\t\t<script type=\"text/javascript\">\r\n\t\tfunction parentOpen(url){\r\n\t\t\twindow.parent.parent.location.href = url;\r\n\t\t}\r\n\t\t</script>\r\n\t</body>\r\n</html>");
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