/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:46:11 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.wap2.note;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.oa.personalwork.paper.po.NotePaperPO;
import java.text.SimpleDateFormat;
import java.util.*;
import com.js.wap.util.WapUtil;

public final class wapNoteList_005f3g_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("com.js.oa.personalwork.paper.po.NotePaperPO");
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

      out.write("\r\n\r\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"DTD/xhtml1-strict.dtd\">\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n\r\n\r\n\r\n");
 
	request.setCharacterEncoding("UTF-8");
	String path = request.getContextPath();
	List<NotePaperPO> list = (List<NotePaperPO>)request.getAttribute("noteList");
	int size=Integer.valueOf(request.getAttribute("size").toString());
	int beginIndex=Integer.parseInt(request.getParameter("beginIndex")==null?"0":request.getParameter("beginIndex"));

      out.write("\r\n<HTML xmlns=\"http://www.w3.org/1999/xhtml\">\r\n<HEAD>\r\n<TITLE>工作便签-移动办公系统</TITLE>\r\n<META content=\"text/html; charset=UTF-8\" http-equiv=Content-Type>\r\n<META name=viewport content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\">\r\n<META name=apple-touch-fullscreen content=YES>\r\n<META name=apple-mobile-web-app-capable content=no>\r\n<LINK rel=apple-touch-icon href=\"");
      out.print(path);
      out.write("/wap2/images/iphone.jpg\">\r\n<LINK rel=stylesheet type=text/css href=\"");
      out.print(path);
      out.write("/wap2/css/main_3g.css\">\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path);
      out.write("/wap2/js/cookie.js\"></SCRIPT>\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path);
      out.write("/wap2/js/util.js\"></SCRIPT>\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path);
      out.write("/wap2/js/frost.js\"></SCRIPT>\r\n<META name=GENERATOR content=\"MSHTML 8.00.6001.19154\">\r\n\r\n<SCRIPT language=JavaScript>\r\n<!--\r\nfunction gotoPage(url, flag){\r\n    if(flag){\r\n        location.href=encodeURI(url);\r\n    }\r\n}\r\n//-->\r\n</SCRIPT>\r\n</HEAD>\r\n <BODY>\r\n   <DIV id=dd class=main>\r\n       <DIV id=top>\r\n           <SPAN id=lp><DIV class=btn_2><A href=\"");
      out.print(path);
      out.write("/wap2/index_3g.jsp\">桌面</A></DIV></SPAN>\r\n           <SPAN id=title>工作便签</SPAN>\r\n           <A class=btn_1 href=\"");
      out.print(path);
      out.write("/wap2/note/newNotePaper_3g.jsp\">新增</A>\r\n       </DIV>\r\n       <DIV class=height2></DIV>\r\n\t");
SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
	for(int i=0;i<list.size();i++){
	NotePaperPO notePaperPO = list.get(i);
	String content = notePaperPO.getNotePaperContent();
	if(content.length()>20)
		content=content.substring(0,20)+"……";
	
      out.write("\r\n\t\t\t<DIV class=lista1>\r\n         <DIV class=l1>\r\n            <A class=listing href=\"");
      out.print(path );
      out.write("/wap/action/WapNotePaperAction.do?action=notePaper&noteId=");
      out.print(notePaperPO.getId() );
      out.write("&beginIndex=");
      out.print(beginIndex );
      out.write('"');
      out.write('>');
      out.print(content );
      out.write("</A>\r\n         </DIV>\r\n         <SPAN>");
      out.print("创建时间:"+format.format(notePaperPO.getCreatedTime()) );
      out.write("</SPAN>\r\n       </DIV>\r\n");
}

	int nextNum=(beginIndex/WapUtil.LIMITED)+1;
	int nextIndex=nextNum*WapUtil.LIMITED;
	int upNum=(beginIndex/WapUtil.LIMITED)-1;
	int upIndex=upNum*WapUtil.LIMITED;

	String upUrl="#",nextUrl="#";
	if(size>WapUtil.LIMITED){
		if(upIndex>=0)upUrl=path+"/wap/action/WapNotePaperAction.do?action=notePaperList&amp;beginIndex=" + upIndex ;
		if(nextIndex<size)nextUrl=path+"/wap/action/WapNotePaperAction.do?action=notePaperList&amp;beginIndex=" + nextIndex;
      out.write("\r\n\t\t <DIV class=page>\r\n       ");
if(upUrl.indexOf("#")<0){
      out.write("\r\n       <INPUT class=btn_4 onclick=\"gotoPage('");
      out.print(upUrl );
      out.write("',true);\" value=上一页 type=submit name=prev>\r\n       ");
}if(nextUrl.indexOf("#")<0){ 
      out.write(" \r\n       <INPUT class=btn_4 onclick=\"gotoPage('");
      out.print(nextUrl );
      out.write("',true);\" value=下一页 type=submit name=next>\r\n       ");
}
      out.write("\r\n       </DIV>\r\n\t");
}
      out.write("\r\n\t\r\n</div>\r\n</body>\r\n</html>\r\n\r\n\r\n");
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
