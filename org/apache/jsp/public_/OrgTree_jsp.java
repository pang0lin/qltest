/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:56:33 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.public_;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.util.util.BrowserJudge;
import com.js.util.config.SystemCommon;

public final class OrgTree_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/WEB-INF/tag-lib/SSHA.tld", Long.valueOf(1499751390000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.util.config.SystemCommon");
    _jspx_imports_classes.add("com.js.util.util.BrowserJudge");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fSSHA_005ftree_0026_005ftype_005ftarget_005frange_005forgInputType_005forgIdString_005finputType_005fcondition_005fallowId;

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
    _005fjspx_005ftagPool_005fSSHA_005ftree_0026_005ftype_005ftarget_005frange_005forgInputType_005forgIdString_005finputType_005fcondition_005fallowId = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fSSHA_005ftree_0026_005ftype_005ftarget_005frange_005forgInputType_005forgIdString_005finputType_005fcondition_005fallowId.release();
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
      response.setContentType("text/html;charset=gbk");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n");

String webapp=request.getContextPath();
String conditionType=(String)request.getParameter("condition");//empEmail,empMobilePhone
String inputType=(String)request.getParameter("single");//??????????????????
String range=(String)request.getParameter("range");
String type=(String)request.getParameter("type");
String orgInputType=request.getParameter("orgInputType");//??????????????????
String allowId=(String)request.getParameter("formAttr1");

String orgIdString="";
if(SystemCommon.getChildUse()==1){
	if("orgPerson".equals(type)){
		orgIdString = session.getAttribute("orgIdString").toString();
		String[] orgIdStrs = orgIdString.split("\\$");
		orgIdString = "";
		for(int i=1;i<orgIdStrs.length&&i<(2*SystemCommon.getChildShow());i=i+2){
			orgIdString += "%"+orgIdStrs[i]+"%";
		}
	}
}
//String func=(String)request.getParameter("func");//????????????????????????

String target="leftBottonFrame";
if(orgInputType.indexOf("only")>=0){
target="rightTopFrame";
}
String style="overflow:auto; background:#FFFFFF; border:1px solid #ADADAD;";
if(BrowserJudge.isOtherBrowser(request)){
	style="overflow:auto;";
}

      out.write("\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<html>\r\n<head>\r\n<link rel=\"StyleSheet\" href=\"");
      out.print(webapp);
      out.write("/style/dtree.css\" type=\"text/css\" />\r\n<script type=\"text/javascript\" src=\"");
      out.print(webapp);
      out.write("/js/tree/wtree.js\"></script>\r\n\r\n</head>\r\n<body  style=\"background:#e6eff8; margin:0px; padding:0px;font-size:12px;\" topmargin=\"0\" leftmargin=\"0\" rightmargin=\"0\" bottommargin=\"0\" onLoad=\"window.parent.rightFrame.init();\">\r\n<form name=\"orgTreeBar\" method=\"post\" style=\"margin-top:2px; padding-left:12px; width:233px; background:#e6eff8;\">\r\n\t <table align=\"left\" border=\"0\" width=\"243\" cellspacing=\"0\" cellpadding=\"0\" ");
      out.print(orgInputType.indexOf("only")>=0?"height:369":"height:172" );
      out.write(" bgcolor=\"#FFFFFF\">\r\n\t\t\t\t<tr>\r\n\t\t\t\t\t<td valign=\"top\">\r\n\t\t\t\t\t\t<div id=\"treearea\" style=\"");
      out.print(style );
      out.write(" width:243;");
      out.print(orgInputType.indexOf("only")>=0?"height:369":"height:172");
      out.write("\">\r\n\t                        ");
      //  SSHA:tree
      com.js.taglib.OrgTag _jspx_th_SSHA_005ftree_005f0 = (com.js.taglib.OrgTag) _005fjspx_005ftagPool_005fSSHA_005ftree_0026_005ftype_005ftarget_005frange_005forgInputType_005forgIdString_005finputType_005fcondition_005fallowId.get(com.js.taglib.OrgTag.class);
      boolean _jspx_th_SSHA_005ftree_005f0_reused = false;
      try {
        _jspx_th_SSHA_005ftree_005f0.setPageContext(_jspx_page_context);
        _jspx_th_SSHA_005ftree_005f0.setParent(null);
        // /public/OrgTree.jsp(49,25) name = condition type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_SSHA_005ftree_005f0.setCondition(conditionType);
        // /public/OrgTree.jsp(49,25) name = target type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_SSHA_005ftree_005f0.setTarget(target);
        // /public/OrgTree.jsp(49,25) name = allowId type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_SSHA_005ftree_005f0.setAllowId(allowId);
        // /public/OrgTree.jsp(49,25) name = inputType type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_SSHA_005ftree_005f0.setInputType(inputType);
        // /public/OrgTree.jsp(49,25) name = range type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_SSHA_005ftree_005f0.setRange(range);
        // /public/OrgTree.jsp(49,25) name = type type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_SSHA_005ftree_005f0.setType(type);
        // /public/OrgTree.jsp(49,25) name = orgInputType type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_SSHA_005ftree_005f0.setOrgInputType(orgInputType);
        // /public/OrgTree.jsp(49,25) name = orgIdString type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_SSHA_005ftree_005f0.setOrgIdString(orgIdString );
        int _jspx_eval_SSHA_005ftree_005f0 = _jspx_th_SSHA_005ftree_005f0.doStartTag();
        if (_jspx_eval_SSHA_005ftree_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n\t                        ");
            int evalDoAfterBody = _jspx_th_SSHA_005ftree_005f0.doAfterBody();
            if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
              break;
          } while (true);
        }
        if (_jspx_th_SSHA_005ftree_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return;
        }
        _005fjspx_005ftagPool_005fSSHA_005ftree_0026_005ftype_005ftarget_005frange_005forgInputType_005forgIdString_005finputType_005fcondition_005fallowId.reuse(_jspx_th_SSHA_005ftree_005f0);
        _jspx_th_SSHA_005ftree_005f0_reused = true;
      } finally {
        org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_SSHA_005ftree_005f0, _jsp_getInstanceManager(), _jspx_th_SSHA_005ftree_005f0_reused);
      }
      out.write("\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t</td>\r\n\t\t\t\t</tr>\r\n  </table>\r\n  <input type=\"hidden\" id=\"selectType\" value=\"");
      out.print(type);
      out.write("\"/>\r\n  <input type=\"hidden\" id=\"orgInputType\" value=\"");
      out.print(orgInputType);
      out.write("\"/>\r\n</form>\r\n</body>\r\n</html>");
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
