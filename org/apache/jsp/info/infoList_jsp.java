/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:55:15 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.info;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class infoList_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes = null;
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
      response.setContentType("text/html;charset=GBK");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<html>\r\n<head><script language=\"JavaScript\" src=\"/jsoa/js/resource/zh_cn/CommonResource.js\" type=\"text/javascript\"></script>\r\n<link href=\"/jsoa/skin/blue/style-MSIEx.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<title>新闻公告</title>\r\n\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject1.css\">\r\n<script src=\"/jsoa/js/imenu.js\"></script>\r\n</head>\r\n<body  class=\"MainFrameBox\">\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td>\r\n<table width=\"100%\" border=\"0\" cellpadding=\"1\" cellspacing=\"0\" class=\"SearchBar\" id=\"searchTable\" style=\"display:none\">\r\n\t<tr>\r\n\t\t<td width=\"50px\">标题：</td>\r\n\t\t<td><input type=\"text\" name=\"title\" id=\"title\" class=\"inputText\" value=\"\" style=\"width:300px;\"></td>\r\n\t\t<td width=\"150px\"><input type=\"button\" value=\"查询\" class=\"btnButton4font\" onclick=\"searchGo()\" />\r\n\t\t<input type=\"button\" value=\"重置\" class=\"btnButton4font\" onclick=\"document.getElementById('title').value='';\" /></td>\r\n\t</tr>\r\n");
      out.write("</table>\r\n\r\n<table width=\"100%\" height=\"25\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"inlineBottomLine\">\r\n  <tr>\r\n    <td style=\"display:none\">&nbsp;</td>\r\n\t<td align=\"right\" nowrap><input type=\"button\" class=\"btnButton4font\" onClick=\"chSearch(this)\" value=\"查询\" /></td>\r\n  </tr>\r\n</table>\r\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"listTable\">\r\n\t<tr>\r\n\t\t<td  class=\"listTableHead\" id=\"tdWidth\">标题</td>\r\n        <td width=\"8%\" class=\"listTableHead\">发布人</td>\r\n        <td width=\"12%\" class=\"listTableHead\">发布日期</td>\r\n    </tr>\r\n");
Map<String,Object> infoMap = (Map<String,Object>)request.getAttribute("infoMap");
List<String[]> infoList = (List<String[]>)infoMap.get("infoList");
for(int i=0;i<infoList.size();i++){
	String[] info = infoList.get(i);
	String tdClass = "listTableLine1";
	if(i%2==0) tdClass = "listTableLine2";  
      out.write("         \r\n\t<tr>\r\n\t\t<td class=\"");
      out.print(tdClass );
      out.write("\" >\r\n\t\t\t<a title=\"");
      out.print(info[5]==null||"".equals(info[5])||"null".equalsIgnoreCase(info[5])?info[2]:info[5] );
      out.write("\" \r\n\t\t\thref=\"javascript:void(0);\" onclick=\"window.open('/jsoa/rss/");
      out.print(info[0] );
      out.write(".html');\">\r\n\t\t\t");
      out.print(info[2] );
      out.write("</a>\r\n\t\t</td>\r\n\t\t<td class=\"");
      out.print(tdClass );
      out.write('"');
      out.write('>');
      out.print(info[8] );
      out.write("</td>\r\n\t\t<td class=\"");
      out.print(tdClass );
      out.write('"');
      out.write('>');
      out.print(info[7].substring(0,19) );
      out.write("</td>\r\n\t</tr>\r\n");
} 
      out.write("\r\n</table>\r\n");
int curPage = Integer.valueOf(infoMap.get("curPage")+"");
int allPage = Integer.valueOf(infoMap.get("allPageNum")+"");
Integer[] ints = (Integer[])request.getAttribute("showNum"); 
      out.write("\r\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"pagebar\">\r\n<tr>\r\n\t<td>\r\n\t<div style=\"display:");
      out.print(Integer.valueOf(infoMap.get("allRow")+"")==0?"none":"" );
      out.write(";\"><table width=\"100%\" height=\"22\">\r\n\t<tr>\r\n\t\t<td height=\"22\" align=\"right\" width=\"100%\">\r\n\t\t共");
      out.print(infoMap.get("allRow") );
      out.write("条记录&nbsp;第");
      out.print(curPage+"/"+allPage );
      out.write("页&nbsp;\r\n\t\t");
if(curPage==1){ 
      out.write("\r\n\t\t<img src=\"/jsoa/images/p_first2.gif\" border=0>\r\n        <img src=\"/jsoa/images/p_pre2.gif\" border=0 >\r\n        ");
}else{ 
      out.write("\r\n        <a href=\"javascript:pageGo(1)\"><img src=\"/jsoa/images/p_first.gif\" border=0 title=\"首页\"></a>\r\n        <a href=\"javascript:pageGo(");
      out.print(curPage-1 );
      out.write(")\"><img src=\"/jsoa/images/p_pre.gif\" border=0 title=\"上一页\"></a>\r\n\t\t");
}
		for(int i=0;i<ints.length;i++){
			if(ints[i]==curPage){
      out.write("\r\n\t\t<font color=\"red\"><u>");
      out.print(ints[i] );
      out.write("</u></font>\r\n\t\t");
}else{
      out.write("\r\n\t\t<a href=\"javascript:pageGo(");
      out.print(ints[i] );
      out.write(')');
      out.write('"');
      out.write('>');
      out.print(ints[i] );
      out.write("</a>\r\n\t\t");
}
		}
		if(allPage==curPage){ 
      out.write("\r\n\t\t<img src=\"/jsoa/images/p_next2.gif\" border=0>\r\n        <img src=\"/jsoa/images/p_last2.gif\" border=0>\r\n        ");
}else{ 
      out.write("\r\n        <a href=\"javascript:pageGo(");
      out.print(curPage+1 );
      out.write(")\"><img src=\"/jsoa/images/p_next.gif\" border=0 title=\"下一页\"></a>\r\n        <a href=\"javascript:pageGo(");
      out.print(allPage );
      out.write(")\"><img src=\"/jsoa/images/p_last.gif\" border=0 title=\"尾页\"></a>\r\n        ");
} 
      out.write("\r\n        &nbsp;第<input id=\"goPage\" type=\"text\" style=\"height:18px;padding-left:2px;padding-right:2px;\" name=\"goPage\" size=3 onKeyUp=\"this.value=this.value.replace(/\\D/g,'')\" onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\">页\r\n        &nbsp;<input type=\"button\" class=\"btnButton4Font\" style=\"height:18px;margin-bottom:7px;\" value=\"Go\" onclick=\"pageGo(document.getElementById('goPage').value);\"></input>\r\n\t\t</td></tr></table>\r\n\t</div>\r\n\t</td>\r\n</tr>\r\n</table>\r\n</td>\r\n</tr>\r\n</table>\r\n</body>\r\n<script language =\"javascript\">\r\nfunction pageGo(pageNum){\r\n\tif(parseInt(pageNum)>");
      out.print(allPage );
      out.write("){\r\n\t\talert(\"跳转页码超过总页数！\");\r\n\t\tdocument.getElementById(\"goPage\").value=\"\";\r\n\t}else{\r\n\t\tlocation.href=\"/jsoa/rss/");
      out.print((request.getAttribute("queryStr")+"").replace("_"+curPage,"") );
      out.write("_\"+pageNum+\".html\";\r\n\t}\r\n}\r\nfunction searchGo(){\r\n\tif(document.getElementById(\"title\").value==\"\"){\r\n\t\tlocation.href=\"/jsoa/rss/list.html\";\r\n\t}else{\r\n\t\tlocation.href=\"/jsoa/rss/list_\"+encodeURI(document.getElementById(\"title\").value)+\"_1.html\";\r\n\t}\r\n}\r\nfunction chSearch(obj){\r\n\tif(obj.value == \"查询\"){\r\n\t\tobj.value = \"关闭查询\";\r\n\t\tdocument.getElementById(\"searchTable\").style.display=\"\";\r\n\t}else{\r\n\t\tobj.value = \"查询\";\r\n\t\tdocument.getElementById(\"searchTable\").style.display=\"none\";\r\n\t}\r\n}\r\n</script>\r\n</html>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
