/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:07:47 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.util.config.SystemCommon;
import com.js.lang.Resource;

public final class wf_005fback_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.lang.Resource");
    _jspx_imports_classes.add("com.js.util.config.SystemCommon");
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

      out.write("\r\n\r\n\r\n\r\n");

String local = session.getAttribute("org.apache.struts.action.LOCALE").toString();

      out.write('\r');
      out.write('\n');

    com.js.oa.jsflow.service.WorkFlowButtonBD workFlowButtonBD = new com.js.oa.jsflow.service.WorkFlowButtonBD();
    java.util.List flowedActivityList = workFlowButtonBD.getFlowedActivity(request.getParameter("tableId"), request.getParameter("recordId"), request.getParameter("stepCount"));

      out.write("\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\" />\r\n<title>");
      if (_jspx_meth_bean_005fmessage_005f0(_jspx_page_context))
        return;
      out.write("</title>\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n</head>\r\n\r\n<body scroll=\"no\" onload=\"resizeWin(500,400);");
if("shandongguotou".equals(com.js.util.config.SystemCommon.getCustomerName())){
      out.write("guotouInit()");
}
      out.write("\">\r\n<table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" id=\"pup_main\">\r\n \r\n  <tr>\r\n    <td align=\"center\" valign=\"top\">\r\n\t\t<div id=\"pup_content\">\r\n\t\t  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n\t\t    \r\n\t\t    ");

		    if("1".equals(com.js.util.config.SystemCommon.getFlowBackToSubmitPersonOnly())){
		    
      out.write("\r\n\t\t    <tr>\r\n\t\t      <td colspan=\"2\" height=\"40\">\r\n\t\t      退回至发起人<input type=\"hidden\"  name=\"include_backActivity\" id=\"include_backActivity\" value=\"0\" />\r\n\t\t      </td>\r\n\t\t    </tr>\r\n\t\t    ");

		    }else{
		    
      out.write("\r\n\t\t    <tr>\r\n\t\t      <td colspan=\"2\" height=\"40\">要退回的环节和人员：</td>\r\n\t\t    </tr>\r\n            <tr>\r\n              <td colspan=\"2\" height=\"120\">\r\n\t\t\t  <select size=\"2\" name=\"include_backActivity\" id=\"include_backActivity\" style=\"width:100%;height:100%;\">\r\n\t\t\t\t");

				String[] tmp = null;
				//2016-07-28国投项目只显示一个退回信息
				if("shandongguotou".equals(com.js.util.config.SystemCommon.getCustomerName())){
					if(flowedActivityList.size()>0){
						tmp = (String[]) flowedActivityList.get(0);
						
      out.write("\r\n\t\t\t\t\t\t<option value=\"");
      out.print(tmp[0] + "," + tmp[2] + "," + tmp[1] + "," + tmp[3]+ "," + tmp[4]);
      out.write("\" selected=\"selected\">");
      out.print(tmp[1]);
      out.write(':');
      out.print(tmp[4]);
      out.write('[');
      out.write('第');
      out.print(tmp[2]);
      out.write("步]</option>\r\n\t\t\t\t\t\t");

					}else{
						out.print("<option value=0 selected='selected'>发起人</option>");
					}
				}else{
					for(int i = 0; i < flowedActivityList.size(); i ++){
						tmp = (String[]) flowedActivityList.get(i);//tmp[0] 节点ID,tmp[1] 节点名称,tmp[2] 节点步骤数
						
      out.write("\r\n\t\t\t\t\t\t<option value=\"");
      out.print(tmp[0] + "," + tmp[2] + "," + tmp[1] + "," + tmp[3]+ "," + tmp[4]);
      out.write('"');
      out.write('>');
      out.print(tmp[1]);
      out.write(':');
      out.print(tmp[4]);
      out.write('[');
      out.write('第');
      out.print(tmp[2]);
      out.write("步]</option>\r\n\t\t\t\t\t\t");
}
      out.write("\r\n\t\t\t\t\t\t<option value=0>发起人</option>\r\n\t\t\t\t");
 }
      out.write("\r\n\t\t\t\t\r\n\t\t\t\t\r\n\t\t\t  </select>\t\t\t  \r\n\t\t\t  </td>\r\n            </tr>\r\n            ");
} 
      out.write("\r\n            <tr style=\"display:\">\r\n              <td height=\"40\" colspan=\"2\" valign=\"middle\">办理提示：</td>\r\n            </tr>\r\n            <tr style=\"display:\">\r\n              <td height=\"110\" colspan=\"2\"><textarea name=\"commentTxt\" id=\"commentTxt\" rows=\"1\" class=\"inputTextarea\" style=\"width:100%;height:99%;\"></textarea></td>\r\n            </tr>\r\n            <tr>\r\n\t\t\t    <td height=\"40\" valign=\"middle\" align=\"right\">\r\n\t\t\t    ");

			  //2016-07-29国投项目显示短信提醒且选中
				if("shandongguotou".equals(com.js.util.config.SystemCommon.getCustomerName())){
				 
      out.write("\r\n\t\t\t         短信提醒：<input type=\"checkbox\" name=\"sendSMS\" id=\"sendSMS\" value=\"1\" onclick=\"changeSendSMS(this);\"/>&nbsp;&nbsp;&nbsp;\r\n\t\t\t         ");
} 
      out.write("\r\n\t\t\t\t    <label>\r\n\t\t\t\t\t\t<input type=\"button\" name=\"button\" value=\"");
      if (_jspx_meth_bean_005fmessage_005f1(_jspx_page_context))
        return;
      out.write("\" class=\"btnButton2font\" onclick=\"include_save();\"/>\r\n\t\t\t\t\t\t<input type=\"button\" name=\"Cancel\" value=\"");
      if (_jspx_meth_bean_005fmessage_005f2(_jspx_page_context))
        return;
      out.write("\" class=\"btnButton2font\" onclick=\"window.close();\" />\r\n\t\t\t\t    </label>\r\n\t\t\t    </td>\r\n\t\t\t  </tr>\r\n          </table>\r\n\t\t</div>\t</td>\r\n  </tr>\r\n  \r\n</table>\r\n</body>\r\n</html>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n<!--\r\n//保存\r\n//$:userId\r\n//*:orgId\r\n//@:groupId\r\nfunction include_save(){\r\n\tif(document.all.include_backActivity.value==''){\r\n\t\talert(\"");
      out.print(Resource.getValue(local,"filetransact","file.selreturnstep"));
      out.write("\");\r\n\t\treturn;\r\n\t}\r\n\t");
if("1".equals(com.js.util.config.SystemCommon.getBackViewMust())){ 
      out.write("\r\n\tif(document.all.commentTxt.value==''){\r\n\t\talert(\"请填写退回意见！\");\r\n\t\tdocument.all.commentTxt.focus();\r\n\t\treturn;\r\n\t}");
} 
      out.write("\r\n\tif(document.all.commentTxt.value.length>400){\r\n\t\talert(\"办理提示内容过长，请限制在400字以内！\");\r\n\t\tdocument.all.commentTxt.focus();\r\n\t\treturn false;\r\n\t}\r\n\t\r\n \r\n    //退回意见\r\n\t//window.opener.document.all.include_comment.value = document.all.commentTxt.value;\r\n\twindow.opener.document.all.dealTips.value = document.all.commentTxt.value;\r\n\t\r\n\tvar backActivityName=document.all.include_backActivity.value.split(\",\")[2]==null?\"发起人\":document.all.include_backActivity.value.split(\",\")[2];\r\n\tif(confirm(\"");
      out.print(Resource.getValue(local,"filetransact","file.confirmreturn"));
      out.write("\"+backActivityName+\"？\")){\r\n\t    var i_backActivity=document.all.include_backActivity.value;\t\r\n\t\twindow.close();\t\t\t\r\n\t\twindow.opener.include_backSubmit(i_backActivity);\r\n\t}\r\n}\r\n");

if("shandongguotou".equals(com.js.util.config.SystemCommon.getCustomerName())){

      out.write("\r\nfunction changeSendSMS(obj){\r\n\tif(window.opener.document.getElementById(\"sendSMS\")!=null){\r\n\t\tif(obj.checked){\r\n\t\t\twindow.opener.document.getElementById(\"sendSMS\").value=\"1\";\r\n\t\t}else{\r\n\t\t\twindow.opener.document.getElementById(\"sendSMS\").value=\"0\";\r\n\t\t}\r\n\t}\r\n\t//alert(window.opener.document.getElementById(\"sendSMS\").value);\r\n}\r\n//国投项目增加\r\nfunction guotouInit(){\r\n\tif(window.opener.document.getElementById(\"sendSMS\")!=null && document.getElementById(\"sendSMS\")!=null){\r\n\t\tdocument.getElementById(\"sendSMS\").checked=true;\r\n\t\twindow.opener.document.getElementById(\"sendSMS\").value=\"1\";\r\n\t}\r\n}\r\n");
}
      out.write("\r\n//-->\r\n</SCRIPT>");
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
      // /jsflow/wf_back.jsp(14,7) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f0.setBundle("filetransact");
      // /jsflow/wf_back.jsp(14,7) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f0.setKey("file.return");
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
      // /jsflow/wf_back.jsp(85,48) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f1.setBundle("common");
      // /jsflow/wf_back.jsp(85,48) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f1.setKey("comm.confirm");
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

  private boolean _jspx_meth_bean_005fmessage_005f2(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f2 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    boolean _jspx_th_bean_005fmessage_005f2_reused = false;
    try {
      _jspx_th_bean_005fmessage_005f2.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fmessage_005f2.setParent(null);
      // /jsflow/wf_back.jsp(86,48) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f2.setBundle("common");
      // /jsflow/wf_back.jsp(86,48) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f2.setKey("comm.cancel");
      int _jspx_eval_bean_005fmessage_005f2 = _jspx_th_bean_005fmessage_005f2.doStartTag();
      if (_jspx_th_bean_005fmessage_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.reuse(_jspx_th_bean_005fmessage_005f2);
      _jspx_th_bean_005fmessage_005f2_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fmessage_005f2, _jsp_getInstanceManager(), _jspx_th_bean_005fmessage_005f2_reused);
    }
    return false;
  }
}