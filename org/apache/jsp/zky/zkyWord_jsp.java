/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:53:14 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.zky;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.oa.zky.util.word.ZkyWordEdit;
import com.js.util.config.SystemCommon;
import java.util.*;
import com.js.util.util.BASE64;
import com.js.util.page.util.PageUtil;
import java.util.*;

public final class zkyWord_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(7);
    _jspx_dependants.put("/public/page/pageUtil.jsp", Long.valueOf(1499751452000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-nested.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-logic.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-tiles.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-html.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-template.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-bean.tld", Long.valueOf(1499751390000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.oa.zky.util.word.ZkyWordEdit");
    _jspx_imports_classes.add("com.js.util.util.BASE64");
    _jspx_imports_classes.add("com.js.util.config.SystemCommon");
    _jspx_imports_classes.add("com.js.util.page.util.PageUtil");
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

List data = (List)request.getAttribute("data");
String fileName = "2013??????????????????????????????";
 
      out.write("\r\n<html>\r\n<head>\r\n\t<title>?????????word????????????</title>\r\n\t<link href=\"/jsoa/skin/blue/style.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<script language=javascript src=\"/jsoa/js/openEndow.js\"></script>\r\n\t<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n</head>\r\n<body bgcolor=\"#ffffff\" onload=\"\" class=\"docBoxNoPanel\">\r\n<div id=\"Layer1\" sytle=\"padding-top:20px;\">\r\n    <table width=\"60%\" border=\"1\" cellpadding=\"5\" cellspacing=\"0\" align=\"center\" >\r\n\t\t<tr>\r\n\t\t\t<td align=\"center\">??????</td><td align=\"center\">??????</td><td align=\"center\">??????</td>\r\n\t\t\t<td align=\"center\">??????</td><td align=\"center\">??????</td><td align=\"center\">??????word</td>\r\n\t\t</tr>\r\n\t\t");
for(int i=0;i<data.size();i++){
		String[] word = (String[])data.get(i); 
      out.write("\r\n\t\t<tr>\r\n\t\t\t<td>&nbsp;");
      out.print(word[0] );
      out.write("</td>\r\n\t\t\t<td>&nbsp;");
      out.print(word[1] );
      out.write("</td>\r\n\t\t\t<td>&nbsp;");
      out.print(word[2] );
      out.write("</td>\r\n\t\t\t<td>&nbsp;");
      out.print(word[3] );
      out.write("</td>\r\n\t\t\t<td>&nbsp;");
      out.print(word[4] );
      out.write("</td>\r\n\t\t\t<td>&nbsp;<a href=\"javascript:downloadWord('");
      out.print(word[0] );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(word[2] );
      out.write("')\">??????</a>\r\n\t\t\t");
if(request.getParameter("nd")!=null){ 
      out.write("<a href=\"javascript:createWord('");
      out.print(word[0] );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(word[5] );
      out.write("')\">??????</a>");
} 
      out.write("\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\t\t");
} 
      out.write("\r\n\t</table>\r\n\t<table width=\"60%\" ><tr><td align=\"center\">??????????????????????????????????????????????????????</td><td align=\"left\">");
      out.write('\r');
      out.write('\n');
 PageUtil pageUtil = (PageUtil)request.getAttribute("pageUtil");
if(pageUtil.getAllPageNum()>1){ 
      out.write("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr>\r\n<td><table width=\"100%\" height=\"22\" style=\"display:");
      out.print(pageUtil.getAllItem()==0?"none":"" );
      out.write(";\"><tr>\r\n\t<td height=\"22\" align=\"right\" width=\"100%\">???");
      out.print(pageUtil.getAllItem() );
      out.write("?????????&nbsp;???");
      out.print(pageUtil.getCurPageNum()+"/"+pageUtil.getAllPageNum() );
      out.write("???&nbsp;");
if(pageUtil.getCurPageNum()==1){ 
      out.write("\r\n\t<img src=\"/jsoa/images/p_first2.gif\" border=\"0\" >\r\n    <img src=\"/jsoa/images/p_pre2.gif\" border=\"0\" >");
}else{ 
      out.write("\r\n    <img src=\"/jsoa/images/p_first.gif\" style=\"cursor:pointer\" border=\"0\" title=\"??????\" onclick=\"goToPage(1)\">\r\n    <img src=\"/jsoa/images/p_pre.gif\" style=\"cursor:pointer\" border=\"0\" title=\"?????????\" onclick=\"goToPage(");
      out.print(pageUtil.getCurPageNum()-1 );
      out.write(')');
      out.write('"');
      out.write('>');
}Integer[] ints = pageUtil.getPageNum();
	for(int i=0;i<ints.length;i++){if(ints[i]==pageUtil.getCurPageNum()){
      out.write("\r\n\t<font color=\"red\"><u>");
      out.print(ints[i] );
      out.write("</u></font>");
}else{
      out.write("\r\n\t<a href=\"javascript:goToPage(");
      out.print(ints[i] );
      out.write(')');
      out.write('"');
      out.write('>');
      out.print(ints[i] );
      out.write("</a>");
}}if(pageUtil.getAllPageNum()==pageUtil.getCurPageNum()){ 
      out.write("\r\n\t<img src=\"/jsoa/images/p_next2.gif\" border=\"0\">\r\n    <img src=\"/jsoa/images/p_last2.gif\" border=\"0\">");
}else{ 
      out.write("\r\n    <img src=\"/jsoa/images/p_next.gif\" border=\"0\" style=\"cursor:pointer\" title=\"?????????\" onclick=\"goToPage(");
      out.print(pageUtil.getCurPageNum()+1 );
      out.write(")\"></a>\r\n    <img src=\"/jsoa/images/p_last.gif\" border=\"0\" style=\"cursor:pointer\" title=\"??????\" onclick=\"goToPage(");
      out.print(pageUtil.getAllPageNum() );
      out.write(')');
      out.write('"');
      out.write('>');
} 
      out.write("\r\n    &nbsp;???<input id=\"goPage\" type=\"text\" style=\"height:18px;padding-left:2px;padding-right:2px;\" name=\"goPage\" size=\"3\" onKeyUp=\"this.value=this.value.replace(/\\D/g,'')\" onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\">???\r\n    &nbsp;<input type=\"button\" class=\"btnButton4Font\" style=\"height:18px;margin-bottom:1px;\" value=\"Go\" onclick=\"goToPage(document.getElementById('goPage').value);\"></input>\r\n\t</td></tr></table>\r\n</td></tr>\r\n<script type=\"text/javascript\">\r\nfunction goToPage(pageNum){\r\n\tif(pageNum==\"\") alert(\"??????????????????????????????\");\r\n\telse{\r\n\t\tif(parseInt(pageNum)>");
      out.print(pageUtil.getAllPageNum() );
      out.write("){\r\n\t\t\talert(\"??????????????????????????????\");\r\n\t\t\tdocument.getElementById(\"goPage\").value=\"\";\r\n\t\t}else\r\n\t\t\tlocation.href=\"");
      out.print(pageUtil.getPageUrl() );
      out.write("&pager.offset=\"+pageNum;\r\n\t}\r\n}\r\n</script></table>");
} 
      out.write('\r');
      out.write('\n');
      out.write("</td></tr></table>\r\n</div>\r\n<iframe name=\"myIframe\" id=\"myIframe\" style=\"display:none;\"></iframe>\r\n</body>\r\n</html>\r\n\r\n<script type=\"text/javascript\">\r\nfunction downloadWord(nd,jobNum){\r\n\twindow.open(\"/jsoa/zky/zkyWordCreate.jsp?flag=download&nd=\"+nd+\"&jobNum=\"+jobNum+\"&fileName=");
      out.print(fileName );
      out.write("&r=\"+Math.random(),\"myIframe\");\r\n\t//document.getElementById(\"myIframe\").src=\"/jsoa/zky/zkyWordCreate.jsp?flag=download&nd=\"+nd+\"&jobNum=\"+jobNum+\"&fileName=");
      out.print(fileName );
      out.write("\";\r\n}\r\nfunction createWord(nd,userId){\r\n\tdocument.getElementById(\"myIframe\").src=\"/jsoa/zky/zkyWordCreate.jsp?flag=create&nd=\"+nd+\"&userId=\"+userId+\"&fileName=");
      out.print(fileName );
      out.write("&r=\"+Math.random();\r\n}\r\n</script>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
