/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:41:02 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.haier;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class rwsDataSend_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-logic.tld", Long.valueOf(1499751390000L));
  }

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

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid;

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
    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid.release();
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

      out.write("\r\n\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
int index=0;
String success=request.getAttribute("success")==null?"":request.getAttribute("success").toString();
if("-1".equals(success)){

      out.write("\r\n\t<script language=\"JavaScript\">\r\n\talert(\"推送失败！\");\r\n\tvar url=\"/jsoa/DataPushAcion.do?action=list\";\r\n\twindow.location.href=url;\r\n\t</script>\r\n");
}else{
      out.write("\r\n<html>\r\n\t<head>\r\n\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n\t\t<title>数据推送</title>\r\n\t\t<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t</head>\r\n\t<body class=\"MainFrameBox\">\r\n\t\t<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n\t\t\t<tr>\r\n\t\t\t\t<td>\r\n\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"listTable\" >\r\n\t\t\t\t\t  \t<tr align=\"center\">\r\n\t\t\t\t\t\t  <td class=\"listTableHead\"  width=\"15%\">流程名称</td>\r\n\t\t\t\t\t\t  <td class=\"listTableHead\"  width=\"20%\">接口地址</td>\r\n\t\t\t\t\t\t  <td class=\"listTableHead\" >参数</td>\r\n\t\t\t\t\t\t  <td class=\"listTableHead\" width=\"13%\">生成 时间</td>\r\n\t\t\t\t\t\t  <td class=\"listTableHead\"  width=\"8%\">处理</td>\r\n\t\t\t\t\t\t</tr>\r\n\t\t\t\t\t\t");
Object[] obj; 
      out.write("\r\n\t\t\t\t\t\t");
      //  logic:iterate
      org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_005fiterate_005f0 = (org.apache.struts.taglib.logic.IterateTag) _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid.get(org.apache.struts.taglib.logic.IterateTag.class);
      boolean _jspx_th_logic_005fiterate_005f0_reused = false;
      try {
        _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
        _jspx_th_logic_005fiterate_005f0.setParent(null);
        // /haier/rwsDataSend.jsp(37,6) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_logic_005fiterate_005f0.setId("list");
        // /haier/rwsDataSend.jsp(37,6) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_logic_005fiterate_005f0.setName("list");
        // /haier/rwsDataSend.jsp(37,6) name = scope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_logic_005fiterate_005f0.setScope("request");
        int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
        if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          java.lang.Object list = null;
          if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
            out = org.apache.jasper.runtime.JspRuntimeLibrary.startBufferedBody(_jspx_page_context, _jspx_th_logic_005fiterate_005f0);
          }
          list = (java.lang.Object) _jspx_page_context.findAttribute("list");
          do {
            out.write("\r\n\t\t\t\t\t\t");

						 	obj=(Object[])list;
							index ++;
							String listClass="listTableLine1";
							if(index%2 != 0){
								listClass="listTableLine2";
							}
						
            out.write("\r\n\t\t\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t\t\t<td class=\"");
            out.print(listClass);
            out.write("\">&nbsp;");
            out.print(obj[4]);
            out.write("</td>\r\n\t\t\t\t\t\t\t\t<td class=\"");
            out.print(listClass);
            out.write("\">&nbsp;");
            out.print(obj[1]);
            out.write("</td>\r\n\t\t\t\t\t\t\t\t<td class=\"");
            out.print(listClass);
            out.write("\" title=\"");
            out.print(obj[2]);
            out.write("\">&nbsp;");
            out.print(obj[2]);
            out.write("</td>\r\n\t\t\t\t\t\t\t\t<td class=\"");
            out.print(listClass);
            out.write("\">&nbsp;");
            out.print(obj[3]);
            out.write("</td>\r\n\t\t\t\t\t\t\t\t<td class=\"");
            out.print(listClass);
            out.write("\"><img title=\"查看\" style=\"cursor: pointer;\" src=\"/jsoa/images/konwledge_view.gif\" onclick=\"view('");
            out.print(obj[0]);
            out.write("')\">&nbsp;&nbsp;<img title=\"发送\" style=\"cursor: pointer;\" src=\"/jsoa/images/arrow_right.gif\" onclick=\"pushData('");
            out.print(obj[0]);
            out.write("');\"></td>\r\n\t\t\t\t\t\t\t</tr>\r\n\t\t\t\t\t\t");
            int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
            list = (java.lang.Object) _jspx_page_context.findAttribute("list");
            if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
              break;
          } while (true);
          if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
            out = _jspx_page_context.popBody();
          }
        }
        if (_jspx_th_logic_005fiterate_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return;
        }
        _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
        _jspx_th_logic_005fiterate_005f0_reused = true;
      } finally {
        org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_logic_005fiterate_005f0, _jsp_getInstanceManager(), _jspx_th_logic_005fiterate_005f0_reused);
      }
      out.write(" \r\n\t\t\t\t\t</table>\r\n\t\t\t\t</td>\r\n\t\t\t</tr>\r\n\t\t</table>\r\n\t</body>\r\n</html>\r\n<script language=\"javascript\">\r\nfunction pushData(id){\r\n\tvar url=\"/jsoa/DataPushAcion.do?action=send&id=\"+id;\r\n\twindow.location.href=url;\r\n}\r\nfunction view(id){\r\n\tvar url=\"/jsoa/DataPushAcion.do?action=view&id=\"+id;\r\n\tJSMainWinOpen2(url,'','',800,400);\r\n\t//window.location.href=url;\r\n}\r\n</script>\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n<script src=\"/jsoa/js/imenu.js\"></script>\r\n");
}
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