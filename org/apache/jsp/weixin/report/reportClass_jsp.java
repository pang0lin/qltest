/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:00:11 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.weixin.report;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import com.js.wap.util.WapUtil;

public final class reportClass_jsp extends org.apache.jasper.runtime.HttpJspBase
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
			List list = (List) request.getAttribute("list");
			List classList = (List) request.getAttribute("classList");
			int size = Integer.valueOf(request.getAttribute("size").toString());
			int beginIndex = Integer.parseInt(request
					.getParameter("beginIndex") == null ? "0" : request
					.getParameter("beginIndex"));
			int currentPage = Integer.valueOf(request.getAttribute(
					"currentPage").toString());
			String keyword = null != request.getAttribute("keyword") ? request
					.getAttribute("keyword").toString() : "";

      out.write("\r\n<HTML xmlns=\"http://www.w3.org/1999/xhtml\">\r\n<HEAD>\r\n<TITLE>报表分类</TITLE>\r\n<META content=\"text/html; charset=UTF-8\" http-equiv=\"Content-Type\">\r\n<META name=\"viewport\"\r\n    content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\">\r\n<META name=\"apple-touch-fullscreen\" content=\"YES\">\r\n<META name=\"apple-mobile-web-app-capable\" content=\"no\">\r\n<META name=\"GENERATOR\" content=\"MSHTML 8.00.6001.19154\">\r\n<link rel=\"stylesheet\" href=\"/jsoa/css/weixin/weixin_main.css\">\r\n<style type=\"text/css\">\r\n.nextStep {\r\n\tdisplay: none;\r\n\twidth: 100%;\r\n\theight: 100%;\r\n\tposition: fixed;\r\n\ttop: 0;\r\n\tleft: 0;\r\n\tz-index: 100;\r\n\tmargin: 0;\r\n\tpadding: 0;\r\n\toverflow: auto;\r\n}\r\n\r\n.bottomDiv {\r\n\twidth: 99.5%;\r\n\theight: 50px;\r\n\ttext-align: center;\r\n\tmargin: 3px auto 0;\r\n\tpadding: 0px;\r\n\tbackground: white;\r\n\tborder: 1px solid #999;\r\n\tposition: relative;\r\n}\r\n\r\n/* 上一页 */\r\n.up {\r\n\twidth: 33%;\r\n\theight: 45px;\r\n\tline-height: 45px;\r\n\ttext-align: center;\r\n\tfloat: left;\r\n\tfont-weight: 500;\r\n}\r\n\r\n/* 显示页数 */\r\n.page {\r\n\theight: 45px;\r\n");
      out.write("\tline-height: 45px;\r\n\ttext-align: center;\r\n\tfloat: left;\r\n\twidth: 33%;\r\n\tborder-left: 1px solid #dfdfdd;\r\n\tborder-right: 1px solid #dfdfdd;\r\n\tfont-weight: 500;\r\n}\r\n\r\n/* 下一页 */\r\n.down {\r\n\twidth: 33%;\r\n\theight: 45px;\r\n\tline-height: 45px;\r\n\ttext-align: center;\r\n\tfloat: left;\r\n\tfont-weight: 500;\r\n}\r\n</style>\r\n<script type=\"text/javascript\"\r\n    src=\"/jsoa/js/weixin/mobiscroll/js/jquery-1.9.1.min.js\"></script>\r\n<SCRIPT language=\"JavaScript\">\r\n    function gotoPage(url, flag) {\r\n        if (flag) {\r\n            location.href = encodeURI(url);\r\n        }\r\n    }\r\n\r\n    /*  function submitForm() {\r\n         var k = document.getElementById(\"keys\").value;\r\n         if (\"\" != k) {\r\n             k = encodeURI(k);\r\n         }\r\n         document.getElementById(\"keyword\").value = k;\r\n         document.getElementById(\"searchForm\").submit();\r\n     } */\r\n</SCRIPT>\r\n</HEAD>\r\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/wap2/commonImport.jsp", out, false);
      out.write("\r\n<BODY onload=\"setHeader('javascript:closeWindow()','报表分类');\">\r\n\r\n    <!-- 信息列表 -->\r\n    <div class=\"list\" style=\"padding-top: 5px\">\r\n        ");

            if (null != classList && classList.size() > 0 && 1 == currentPage) {
                SimpleDateFormat format = new SimpleDateFormat("MM-dd");
                String typeName = "";
                String typeId = "";
                String parentId = "";
                String url = "";
                for (int i = 0; i < classList.size(); i++) {
                    Object[] obj = (Object[]) classList.get(i);
                    typeName = obj[1].toString();
                    typeId = obj[0].toString();
                    parentId = obj[6].toString();
                    url = "/jsoa/weixinReportFormAction.do?action=class&parentId=" + typeId;
                    String content = (String) obj[1];
                    if (content.length() > 14) {
                        content = content.substring(0, 14) + "...";
                    }
        
      out.write("\r\n        <a style=\"text-decoration: none;\" href=\" ");
      out.print(url);
      out.write(" \">\r\n            <div class=\"item\">\r\n                <div class=\"picture\">\r\n                    <img alt=\"报表分类\" src=\"wap2/images/xxfl.png\">\r\n                </div>\r\n                <div class=\"content\">\r\n                    <div class=\"first\">\r\n                        <div class=\"title\">");
      out.print(typeName);
      out.write("</div>\r\n                    </div>\r\n                    <div class=\"second\">\r\n                        描述：");
      out.print(obj[2]);
      out.write("</div>\r\n                </div>\r\n            </div> </a>\r\n        ");

            }
            } else {
        
      out.write("\r\n        ");

            }
        
      out.write("\r\n        ");

            if (null != list && list.size() > 0) {
                SimpleDateFormat format = new SimpleDateFormat("MM-dd");
                String reportClass = "";
                for (int i = 0; i < list.size(); i++) {
                    Object[] obj = (Object[]) list.get(i);
                    String reportUrl = "";
                    reportClass = obj[6].toString();
                    String id = obj[0] + "";
                    if ("2".equals(reportClass)) {
                        reportUrl = obj[7].toString();
                    } else if ("0".equals(reportClass)) {
                        //TODO
                    } else if ("1".equals(reportClass)) {
                        //TODO
                    }
                    String content = (String) obj[1];
                    if (content.length() > 14)
                        content = content.substring(0, 14) + "...";
        
      out.write("\r\n        <!-- \"\r\n            href=\"javascript:showContent('");
      out.print(id);
      out.write("'); -->\r\n        <a style=\"text-decoration: none;\" href=\"");
      out.print(reportUrl);
      out.write("\">\r\n            <div class=\"item\">\r\n                <div class=\"picture\">\r\n                    <img alt=\"报表分类\" src=\"wap2/images/dtbb.png\">\r\n                </div>\r\n                <div class=\"content\">\r\n                    <div class=\"first\">\r\n                        <div class=\"title\">");
      out.print(content);
      out.write("</div>\r\n                    </div>\r\n                    <div class=\"second\">\r\n                        描述：");
      out.print(obj[2]);
      out.write("</div>\r\n                </div>\r\n            </div> </a>\r\n        ");

            }
            } else {
                if (null == classList || !(classList.size() > 0)) {
        
      out.write("\r\n\r\n        <div class=\"item\">\r\n            <div class=\"nodata\">本分类下没有报表数据！</div>\r\n        </div>\r\n        ");

            }
            }
        
      out.write("\r\n    </div>\r\n    <!-- 分页 -->\r\n    ");

        int curNum = beginIndex / WapUtil.LIMITED + 1; // 当前页数
        int totalNum = size % WapUtil.LIMITED > 0 ? size / WapUtil.LIMITED + 1 : size / WapUtil.LIMITED; // 总页数
        int upNum = (beginIndex / WapUtil.LIMITED) - 1;
        int upIndex = upNum * WapUtil.LIMITED;
        int nextNum = (beginIndex / WapUtil.LIMITED) + 1;
        int nextIndex = nextNum * WapUtil.LIMITED;

        String upUrl = "#", nextUrl = "#", upUrlFont = "", nextUrlFont = "";
        String parentId = (String) request.getAttribute("parentId");
        if (size > WapUtil.LIMITED) {
            if (upIndex >= 0) {
                upUrl = path + "/weixinReportFormAction.do?action=class&amp;beginIndex=" + upIndex + "&parentId="
                        + parentId;
                ;
                if (null != keyword && !"".equals(keyword))
                    upUrl += "&keyword=" + URLEncoder.encode(URLEncoder.encode(keyword, "utf-8"), "utf-8");
            } else {
                upUrl = "javascript:void(0);";
                upUrlFont = "grayFont";
            }
            if (nextIndex < size) {

                nextUrl = path + "/weixinReportFormAction.do?action=class&amp;beginIndex=" + nextIndex
                        + "&parentId=" + parentId;
                ;
                if (null != keyword && !"".equals(keyword))
                    nextUrl += "&keyword=" + URLEncoder.encode(URLEncoder.encode(keyword, "utf-8"), "utf-8");
            } else {
                nextUrl = "javascript:void(0);";
                nextUrlFont = "grayFont";
            }
    
      out.write("\r\n    <div class=\"bottomDiv\">\r\n        <a href=\"");
      out.print(upUrl);
      out.write("\">\r\n            <div class=\"up ");
      out.print(upUrlFont);
      out.write("\">上一页</div> </a>\r\n        <div class=\"page grayFont\">");
      out.print(curNum);
      out.write('/');
      out.print(totalNum);
      out.write("</div>\r\n        <a href=\"");
      out.print(nextUrl);
      out.write("\">\r\n            <div class=\"down ");
      out.print(nextUrlFont);
      out.write("\">下一页</div> </a>\r\n    </div>\r\n    ");

        }
    
      out.write("\r\n    ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/weixin/common/weixin_messageReminders.jsp", out, false);
      out.write("\r\n    <div id=\"commonPopDiv\" class=\"nextStep\"\r\n        style=\"text-align: center;vertical-align: middle;\">\r\n        <iframe id=\"commonPopIframe\"\r\n            style=\"width: 100%;height: 100%;border: none;\"></iframe>\r\n    </div>\r\n</body>\r\n</html>");
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