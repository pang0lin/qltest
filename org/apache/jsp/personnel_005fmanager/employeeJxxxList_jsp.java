/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:51:53 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.personnel_005fmanager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.Calendar;
import java.util.Locale;
import java.text.DateFormat;
import com.js.system.manager.service.ManagerService;

public final class employeeJxxxList_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.util.Calendar");
    _jspx_imports_classes.add("java.util.Locale");
    _jspx_imports_classes.add("java.text.DateFormat");
    _jspx_imports_classes.add("com.js.system.manager.service.ManagerService");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid;

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
    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid.release();
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n<html>\r\n<head>\r\n<META HTTP-EQUIV=\"pragma\" CONTENT=\"no-cache\"> \r\n<META HTTP-EQUIV=\"Cache-Control\" CONTENT=\"no-cache, must-revalidate\"> \r\n<META HTTP-EQUIV=\"expires\" CONTENT=\"Wed, 26 Feb 1997 08:21:57 GMT\">\r\n<link rel=stylesheet type=\"text/css\" href=\"style/cssmain.css\">\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject1.css\">\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DateObject.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DatePicker.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/editlib.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/tree.js\"></SCRIPT>\r\n<title>绩效考核信息：</title>\r\n");

String empID = request.getAttribute("empID").toString();
String open = request.getAttribute("open").toString();
//String vindicate = request.getAttribute("vindicate")==null?"":request.getAttribute("vindicate").toString();
Boolean deleteSuccess = request.getAttribute("deleteSuccess")==null?new Boolean(true):(Boolean)request.getAttribute("deleteSuccess");
//Boolean addRight = request.getAttribute("addRight")==null?new Boolean(false):(Boolean)request.getAttribute("addRight");
String canAdd = "";
Long userId = new Long(session.getAttribute("userId").toString()); //取当前用户的ID
ManagerService mbd = new ManagerService();
boolean scopeSQL = mbd.hasRight(userId.toString(), "07*55*08");
if(scopeSQL&&!"2".equals(open))
{
canAdd="1";
  }else
{  
canAdd="0";
}

      out.write("\r\n<!--  STYLE\tCHANGE START -->\r\n<link href=\"skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script\tsrc=\"js/js.js\" language=\"javascript\"></script>\r\n<!--  STYLE\tCHANGE START -->\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\nfunction load(){\r\n//parent.document.all(\"contract\").height=document.body.scrollHeight; \r\n\r\n}\r\nfunction parentIframeHeight() {\r\n\tif(top.location != self.location) {\r\n\t\tvar a = window.parent.document.getElementsByTagName('iframe');\r\n\t\tfor (var i=0; i<a.length; i++) {\t \r\n\t\t\tif (a[i].name == self.name) {\r\n\t\t\t\tif(window.parent.document.getElementById(\"jxxx\")){\r\n\t\t\t\t\ta[i].height = document.body.scrollHeight;\r\n\t\t\t\t}\r\n\t\t\t}\r\n\t\t}\r\n\t}\r\n}\r\n\r\n</SCRIPT>　\r\n<!--stationList.jsp-->\r\n\r\n<script language=\"JavaScript\">\r\n<!--\r\nfunction MM_openBrWindow(theURL,winName,features) { //v2.0\r\n \r\n      var open=\"");
      out.print(open);
      out.write("\";\r\n       if(\"0\"==open)\r\n        {\r\n\t    var wleft=parent.window.screenLeft;\r\n    \tvar wtop=parent.window.screenTop;\r\n\t    var element=parent.parent.document.all.content1;\r\n\t    \r\n\t    offsetWidth = element.offsetWidth-33;\r\n\t    offsetHeight = element.offsetHeight-10;\r\n\t    \r\n\t    }else if(\"1\"==open)\r\n\t    {\r\n\t    \r\n\t      var wleft=parent.window.screenLeft;\r\n    \tvar wtop=parent.window.screenTop;\r\n\t    var element=parent.opener.parent.document.all.content1;\r\n\t    \r\n\t    offsetWidth = element.offsetWidth-33;\r\n\t    offsetHeight = element.offsetHeight-50;\r\n\t    \r\n\t    } \r\n\t \r\n     window.open(theURL,winName,'TOP='+wtop+',LEFT='+wleft+',scrollbars=yes,status=no,resizable=yes,width='+offsetWidth+',height='+offsetHeight);\r\n}\r\n//-->\r\n</script>\r\n<script language=\"javascript\" src=\"/jsoa/js/checkForm.js\"></script>\r\n</head>\r\n\r\n<body   class=\"MainFrameBox\" onLoad=\"parentIframeHeight();\" scrolling=\"no\" style=\"BACKGROUND-COLOR:transparent\">\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n\r\n<tr>\r\n<td height=\"22\" align=\"left\">\r\n");
      out.write("<strong>绩效考核信息：</strong></td>\r\n</tr>\r\n\r\n\r\n<!-- MIDDLE\tBUTTONS\t-->\r\n");
 if(canAdd.equals("1")){
      out.write("\r\n\r\n<tr>\r\n<td align=\"center\" >\r\n\r\n</td>\r\n</tr>\r\n");
}
      out.write("\r\n\r\n\r\n<tr>\r\n<td align=\"center\">\r\n<!-- MIDDLE\tBUTTONS\t-->\r\n<!-- LIST TITLE PART -->\r\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"docBoxNoPanel\" style=\"border:1px solid #6ea2da;\">\r\n\t<form name=\"EmployeeJxxxActionForm\" method=\"post\" action=\"/jsoa/EmployeeJxxxAction.do\">\r\n\t\t<tr align=\"center\" bgcolor=\"#cfe6ff\">\r\n\t\t\t<td class=\"outRightLine1\" width=\"50%\"> 考核年度</td>\r\n\t\t\t<td class=\"outRightLine1\" width=\"50%\"> 考核结果</td>\r\n\t\t\t\r\n\t\t</tr>\r\n\t\t");

			int index = 0;
			Object[] jxxxObj = null;
		
      out.write("\r\n\t\t");
      //  logic:iterate
      org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_005fiterate_005f0 = (org.apache.struts.taglib.logic.IterateTag) _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid.get(org.apache.struts.taglib.logic.IterateTag.class);
      boolean _jspx_th_logic_005fiterate_005f0_reused = false;
      try {
        _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
        _jspx_th_logic_005fiterate_005f0.setParent(null);
        // /personnel_manager/employeeJxxxList.jsp(130,2) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_logic_005fiterate_005f0.setId("jxxxList");
        // /personnel_manager/employeeJxxxList.jsp(130,2) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_logic_005fiterate_005f0.setName("jxxxList");
        int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
        if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          java.lang.Object jxxxList = null;
          if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
            out = org.apache.jasper.runtime.JspRuntimeLibrary.startBufferedBody(_jspx_page_context, _jspx_th_logic_005fiterate_005f0);
          }
          jxxxList = (java.lang.Object) _jspx_page_context.findAttribute("jxxxList");
          do {
            out.write("\r\n\t\t");

		  jxxxObj = (Object[]) jxxxList;
		  index ++;
		

String listClass="listTableLine1";
if(index%2 != 0){listClass="listTableLine2";}

            out.write("\r\n\t\t<tr>\r\n\t\t  <td class=\"listTableLine3\">");
            out.print((""+jxxxObj[0]));
            out.write("</td>\r\n\t\t  <td class=\"listTableLine3\">");
            out.print((""+jxxxObj[1]));
            out.write("</td>\r\n\t\t  </tr>\r\n\t");
            int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
            jxxxList = (java.lang.Object) _jspx_page_context.findAttribute("jxxxList");
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
        _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
        _jspx_th_logic_005fiterate_005f0_reused = true;
      } finally {
        org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_logic_005fiterate_005f0, _jsp_getInstanceManager(), _jspx_th_logic_005fiterate_005f0_reused);
      }
      out.write("\r\n\t</form>\r\n</table>\r\n<!-- LIST TITLE PART -->\r\n\r\n</td>\r\n</tr>\r\n</table>\r\n</body>\r\n");
if(!deleteSuccess.booleanValue()){
      out.write("\r\n<script language=\"javascript\">\r\nalert(\"删除失败！您选择的奖惩信息正在使用\");\r\n</script>\r\n");
}
      out.write("\r\n</html>\r\n<script language=\"javascript\">\r\n\r\n//-->\r\n</script>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
