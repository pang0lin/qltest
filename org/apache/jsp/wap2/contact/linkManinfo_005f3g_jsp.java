/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:46:18 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.wap2.contact;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.oa.personalwork.person.po.PersonPO;

public final class linkManinfo_005f3g_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

public String getCont(Object o){
	String cont="";
	if(o!=null&&!"".equals(o.toString())&&!"null".equals(o.toString())){
	cont=o.toString();
	}
	return cont;
} 
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
    _jspx_imports_classes.add("com.js.oa.personalwork.person.po.PersonPO");
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

      out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"DTD/xhtml1-strict.dtd\">\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n\r\n");
 
request.setCharacterEncoding("UTF-8");
String path = request.getContextPath();
String modity=request.getAttribute("modity").toString();
String type=request.getAttribute("type").toString();
PersonPO personPO = (PersonPO)request.getAttribute("person");
int beginIndex=Integer.parseInt(request.getAttribute("beginIndex")==null?"0":request.getAttribute("beginIndex").toString());
String title="个人联系人";
if("1".equals(type)){
	title="公共联系人";
}

      out.write("\r\n<HTML xmlns=\"http://www.w3.org/1999/xhtml\">\r\n<HEAD>\r\n<TITLE>");
      out.print(title );
      out.write("详细信息</TITLE>\r\n<META content=\"text/html; charset=UTF-8\" http-equiv=Content-Type>\r\n<META name=viewport content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\">\r\n<META name=apple-touch-fullscreen content=YES>\r\n<META name=apple-mobile-web-app-capable content=no>\r\n<LINK rel=apple-touch-icon href=\"");
      out.print(path );
      out.write("/wap2/images/iphone.jpg\">\r\n<LINK rel=stylesheet type=text/css href=\"");
      out.print(path );
      out.write("/wap2/css/main_3g.css\">\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path );
      out.write("/wap2/js/cookie.js\"></SCRIPT>\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path );
      out.write("/wap2/js/util.js\"></SCRIPT>\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path );
      out.write("/wap2/js/frost.js\"></SCRIPT>\r\n<META name=GENERATOR content=\"MSHTML 8.00.6001.19154\">\r\n</head>\r\n<body>\r\n\t<div class=\"main\">\r\n\t");
if("yes".equals(modity)){ 
      out.write("\r\n\t\t<div id=\"top\">\r\n\t\t\t<SPAN id=lp><DIV class=btn_2><A href=\"");
      out.print(path);
      out.write("/wap/action/WapContactAction.do?action=commonLinkMan&beginIndex=");
      out.print(beginIndex );
      out.write("&type=");
      out.print(type );
      out.write("\">返回</A></DIV></SPAN>\r\n           <A class=btn_1 href=\"");
      out.print(path);
      out.write("/wap/action/WapContactAction.do?action=addLinkMan&modity=y&beginIndex=");
      out.print(beginIndex );
      out.write("&linkManId=");
      out.print(personPO.getId() );
      out.write("&type=");
      out.print(type );
      out.write("\">修改</A>\r\n\t\t</div>\r\n\t\t");
}else{ 
      out.write("\r\n\t\t<div id=\"top\">\r\n\t\t\t<span id=\"lp2\"><div class=\"btn_3\"><a href=\"");
      out.print(path);
      out.write("/wap/action/WapContactAction.do?action=commonLinkMan&beginIndex=");
      out.print(beginIndex );
      out.write("&type=");
      out.print(type );
      out.write("\">返回</a></div></span>\r\n\t\t\t<span id=\"title2\"></span>\r\n\t\t</div>\r\n\t\t");
 }
      out.write("\r\n<div class=\"f_1\">");
      out.print(title );
      out.write("详细信息</div>\r\n\t<div class=\"box_2\">\r\n\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">姓名：</div>\r\n\t\t\t\t<div class=\"t2\">");
      out.print(personPO.getLinkManName()+
				"&nbsp;&nbsp;&nbsp;("+(personPO.getLinkManSex().equals("0")?"男":"女")+")" );
      out.write("</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t</div>\r\n\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">分类：</div>\r\n\t\t\t\t<div class=\"t2\">");
      out.print(personPO.getLinkManClass().getClassName() );
      out.write("</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t</div>\r\n\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">手机号码：</div>");
if(personPO.getMobilePhone()==null||"".equals(personPO.getMobilePhone())){ 
      out.write("\r\n\t\t\t\t<div class=\"t2\">&nbsp;&nbsp;&nbsp;</div>");
}else{ 
      out.write("\r\n\t\t\t\t<div class=\"t2\">");
      out.print(personPO.getMobilePhone() );
      out.write("&nbsp;&nbsp;&nbsp;\r\n\t\t\t\t<a href=\"tel:");
      out.print(personPO.getMobilePhone() );
      out.write("\"><img src=\"");
      out.print(path);
      out.write("/wap2/images/dianhua.png\" style=\"cursor:pointer;width:21px;height:21px;\" title=\"拨打电话\" /></a>&nbsp;\r\n\t\t\t\t<a href=\"SMS:");
      out.print(personPO.getMobilePhone() );
      out.write("\"><img src=\"");
      out.print(path);
      out.write("/wap2/images/duanxin.png\" style=\"cursor:pointer;width:21px;height:21px;\" title=\"发短信\" /></a></span></div>\r\n\t\t\t\t");
} 
      out.write("<div class=\"clear\"></div>\r\n\t</div>\r\n\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">办公电话：</div>\r\n\t\t\t\t<div class=\"t2\">");
      out.print(getCont(personPO.getBussinessPhone()) );
      out.write("</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t</div>\r\n\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">传真：</div>\r\n\t\t\t\t<div class=\"t2\">");
      out.print(getCont(personPO.getBussinessFax()) );
      out.write("</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t</div>\r\n\r\n\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">电子邮箱：</div>\r\n\t\t\t\t<div class=\"t2\">");
      out.print(getCont(personPO.getLinkManEmail()) );
      out.write("</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t</div>\r\n\r\n\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">所在公司：</div>\r\n\t\t\t\t<div class=\"t2\">");
      out.print(getCont(personPO.getLinkManUnit()) );
      out.write("</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t</div>\r\n\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">部门：</div>\r\n\t\t\t\t<div class=\"t2\">");
      out.print(getCont(personPO.getLinkManDepart()) );
      out.write("</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t</div>\r\n\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">职务：</div>\r\n\t\t\t\t<div class=\"t2\">");
      out.print(getCont(personPO.getLinkManDuty()) );
      out.write("</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t</div>\r\n\t\t\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">QQ：</div>\r\n\t\t\t\t<div class=\"t2\">");
      out.print(getCont(personPO.getLinkManEmail2()) );
      out.write("</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t</div>\r\n\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">MSN：</div>\r\n\t\t\t\t<div class=\"t2\">");
      out.print(getCont(personPO.getLinkManEmail3()) );
      out.write("</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t</div>\r\n\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">住宅电话：</div>\r\n\t\t\t\t<div class=\"t2\">");
      out.print(getCont(personPO.getFixedPhone()) );
      out.write("</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t</div>\r\n\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">地址：</div>\r\n\t\t\t\t<div class=\"t2\">");
      out.print(getCont(personPO.getLinkManAddress()) );
      out.write("</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t</div>\r\n\t\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">邮编：</div>\r\n\t\t\t\t<div class=\"t2\">");
      out.print(getCont(personPO.getLinkManPostZip()) );
      out.write("</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t</div>\r\n\t</div>\r\n</div>\r\n</body>\r\n</html>\r\n");
      out.write('\r');
      out.write('\n');
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
