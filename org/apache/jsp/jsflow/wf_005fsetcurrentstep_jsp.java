/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:06:55 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.util.util.BrowserJudge;
import com.js.lang.Resource;
import com.js.oa.jsflow.service.ActivityBD;
import java.util.*;

public final class wf_005fsetcurrentstep_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
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
    _jspx_imports_classes.add("com.js.lang.Resource");
    _jspx_imports_classes.add("com.js.util.util.BrowserJudge");
    _jspx_imports_classes.add("com.js.oa.jsflow.service.ActivityBD");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody;

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
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.release();
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");

String local = session.getAttribute("org.apache.struts.action.LOCALE").toString();

String processId=request.getParameter("processId");
String tableId=request.getParameter("tableId");
String recordId=request.getParameter("recordId");
ActivityBD actBD=new ActivityBD();

List list=actBD.getUserActivityList(processId,tableId,recordId);


      out.write("\r\n<head>\r\n\r\n<title>???????????????</title>\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n<SCRIPT language=\"javascript\" src=\"/jsoa/js/openEndow.js\"></SCRIPT>\r\n<META HTTP-EQUIV=\"pragma\" CONTENT=\"no-cache\">\r\n<META HTTP-EQUIV=\"Cache-Control\" CONTENT=\"no-cache\">\r\n<META HTTP-EQUIV=\"expires\" CONTENT=\"0\">\r\n</head>\r\n<base target=_self>\r\n<body leftmargin=\"0\" topmargin=\"0\">\r\n<form name=\"frm1\" action=\"/jsoa/WorkflowButtonAction.do\" style=\"margin:0px;\" method=\"post\">\r\n<input type=\"hidden\" name=\"processId\" id=\"processId\" value=\"");
      out.print(processId );
      out.write("\"/>\r\n<input type=\"hidden\" name=\"tableId\" id=\"tableId\" value=\"");
      out.print(tableId );
      out.write("\"/>\r\n<input type=\"hidden\" name=\"recordId\" id=\"recordId\" value=\"");
      out.print(recordId );
      out.write("\"/>\r\n<input type=\"hidden\" name=\"flag\" id=\"flag\" value=\"setCurrentStep\"/>\r\n\r\n<table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" id=\"pup_main\">\r\n<tr>\r\n\t<td align=\"center\" valign=\"top\">\r\n\t<div id=\"pup_content\">\r\n\t<table width=\"100%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" style=\"margin:0px;border:0px;\">\r\n\r\n\t  <tr id=\"selTextarea\">\r\n\t    <td colspan=\"3\">\r\n\t     <table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0px;border:0px;\">\r\n\t\t   <tr>\r\n\t\t\t<td height=\"40\" width=\"20%\" nowrap>\r\n\t\t\t\t???????????????\r\n\t\t\t</td>\r\n\t\t\t<td width=\"90%\">\r\n\t\t\t\t<select id=\"currentStep\" name=\"currentStep\">\r\n\t\t\t\t\t");

					String[] temp;
					for(int i=0;i<list.size();i++){
						temp=(String[])list.get(i);
						
      out.write("\r\n\t\t\t\t\t\t<option value=\"");
      out.print(temp[1] );
      out.write(',');
      out.print(temp[2] );
      out.write('"');
      out.write('>');
      out.print(temp[2] );
      out.write("</option>\r\n\t\t\t\t\t\t");

					}
					
      out.write("\r\n\t\t\t\t</select>\r\n\t\t\t</td>\r\n\t\t   </tr>\r\n\t\t   <tr>\r\n\t\t\t<td height=\"40\">???&nbsp;???&nbsp;??????\r\n\t\t\t</td>\r\n\t\t\t<td>\r\n\t\t\t\t<input type=\"text\" id=\"selUserName\" name=\"selUserName\" style=\"width:70%;\" class=\"inputText\" title=\"???????????????\" onclick=\"openSelUserWIN();\">\r\n\t\t\t\t<input type=\"hidden\" id=\"selUserId\" name=\"selUserId\" value=\"\">\r\n\t\t\t</td>\r\n\t\t   </tr>\r\n\t\t   <tr id=\"transTypeTD\" name=\"transTypeTD\">\r\n\t\t\t<td height=\"40\">???????????????\r\n\t\t\t</td>\r\n\t\t\t<td>\r\n\t\t\t\t<select name=\"transactType\" id=\"transactType\">\r\n\t\t\t\t\t<option value=\"1\">??????</option>\r\n\t\t\t\t\t<option value=\"3\">??????</option>\r\n\t\t\t\t\t<option value=\"0\">??????</option>\r\n\t\t\t\t</select>\r\n\t\t\t</td>\r\n\t\t   </tr>\r\n\t\t   \r\n\t\t </table>\r\n\t\t</td>\r\n\t  </tr>\t \r\n\t\r\n\t </table>\r\n\t</div>\r\n\t  <tr>\r\n\t\t<td valign=\"middle\" align=\"right\" valign=\"top\" height=\"100\">  \r\n\t\t\t<input type=\"button\" name=\"Submit\" value=\"");
      if (_jspx_meth_bean_005fmessage_005f0(_jspx_page_context))
        return;
      out.write("\" class=\"btnButton2font\" onclick=\"include_save();\"/>\r\n\t\t\t<input type=\"button\" name=\"Cancel\" value=\"");
      if (_jspx_meth_bean_005fmessage_005f1(_jspx_page_context))
        return;
      out.write("\" class=\"btnButton2font\" onclick=\"window.close();\" />\r\n\t\t\t&nbsp;&nbsp;\r\n\t\t</td>\r\n\t  </tr>\r\n  </table>\r\n</form>\r\n</body>\r\n</html>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n<!--\r\nfunction openSelUserWIN(){\r\n\tvar type = \"user\";\r\n\topenEndow('selUserId','selUserName',document.all.selUserId.value,document.all.selUserName.value,type,'no',type,'");
      out.print(session.getAttribute("browseRange"));
      out.write("')\r\n}\r\n\r\n\r\n//??????\r\nfunction include_save(){\r\n\tif(document.getElementById(\"selUserId\").value==\"\"){\r\n\t\talert(\"??????????????????!\");\r\n\t}else{\r\n\t\tdocument.all.frm1.submit();\r\n\t}\r\n\t\r\n}\r\n\r\n//-->\r\n</SCRIPT>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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

  private boolean _jspx_meth_bean_005fmessage_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f0 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    boolean _jspx_th_bean_005fmessage_005f0_reused = false;
    try {
      _jspx_th_bean_005fmessage_005f0.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fmessage_005f0.setParent(null);
      // /jsflow/wf_setcurrentstep.jsp(91,45) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f0.setBundle("common");
      // /jsflow/wf_setcurrentstep.jsp(91,45) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f0.setKey("comm.confirm");
      int _jspx_eval_bean_005fmessage_005f0 = _jspx_th_bean_005fmessage_005f0.doStartTag();
      if (_jspx_th_bean_005fmessage_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0);
      _jspx_th_bean_005fmessage_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fmessage_005f0, _jsp_getInstanceManager(), _jspx_th_bean_005fmessage_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f1(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f1 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    boolean _jspx_th_bean_005fmessage_005f1_reused = false;
    try {
      _jspx_th_bean_005fmessage_005f1.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fmessage_005f1.setParent(null);
      // /jsflow/wf_setcurrentstep.jsp(92,45) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f1.setBundle("common");
      // /jsflow/wf_setcurrentstep.jsp(92,45) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f1.setKey("comm.cancel");
      int _jspx_eval_bean_005fmessage_005f1 = _jspx_th_bean_005fmessage_005f1.doStartTag();
      if (_jspx_th_bean_005fmessage_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.reuse(_jspx_th_bean_005fmessage_005f1);
      _jspx_th_bean_005fmessage_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fmessage_005f1, _jsp_getInstanceManager(), _jspx_th_bean_005fmessage_005f1_reused);
    }
    return false;
  }
}
