/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:59:10 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.weixin.workflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.lang.Resource;

public final class wf_005fpress_005fnewForWeiXin_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n");

String local = session.getAttribute("org.apache.struts.action.LOCALE").toString();

      out.write('\r');
      out.write('\n');

//构造WorkVO
String processId = request.getParameter("processId");
String tableId = request.getParameter("tableId");
String recordId = request.getParameter("recordId");
String msgFrom = request.getParameter("msgFrom");


com.js.oa.jsflow.vo.WorkVO workVO = new com.js.oa.jsflow.vo.WorkVO();
	workVO.setTableId(Long.valueOf(tableId));
	workVO.setRecordId(Long.valueOf(recordId));
	workVO.setProcessId(Long.valueOf(processId));

com.js.oa.jsflow.service.WorkFlowButtonBD workFlowButtonBD = new com.js.oa.jsflow.service.WorkFlowButtonBD();
java.util.List activityList = workFlowButtonBD.getAllDealwithUsersByStatus(workVO,"0");

java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");


//显示短信
boolean showSmsRemind = false;
if(new com.js.oa.message.action.ModelSendMsg().judgePurviewMessage(msgFrom,session.getAttribute("domainId")==null?"0":session.getAttribute("domainId").toString())){
	if(new com.js.system.manager.service.ManagerService().hasRight(session.getAttribute("userId").toString(), "09*01*01")){
		showSmsRemind = true;
	}
}

      out.write("\r\n\r\n\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n<title>");
      if (_jspx_meth_bean_005fmessage_005f0(_jspx_page_context))
        return;
      out.write("</title>\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n<SCRIPT language=\"javascript\" src=\"/jsoa/js/openEndow.js\"></SCRIPT>\r\n</head>\r\n\r\n<body scroll=\"no\" onload=\"resizeWin(500,450);\"  >\r\n<form name=\"frm\" id=\"frm\" method=\"post\">\r\n<input type=\"hidden\" name=\"pressUserId\" id=\"pressUserId\" value=\"\">\r\n<input type=\"hidden\" name=\"pressSMS\" id=\"pressSMS\" value=\"\">\r\n<input type=\"hidden\" name=\"pressUserName\" id=\"pressUserName\" value=\"\">\r\n<table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" id=\"pup_main\">\r\n  <tr style=\"display:none\">\r\n    <td id=\"pup_top\"><table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n      <tr>\r\n        <td><div id=\"pup_topright\"></div></td>\r\n        <td id=\"pup_topleft\">&nbsp;</td>\r\n      </tr>\r\n    </table></td>\r\n  </tr>  \r\n  <tr>\r\n    <td align=\"center\" valign=\"top\">\r\n\t\t<div id=\"pup_content\">\r\n\t\t  <table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n\t\t  <tr>\r\n              <td width=\"70\" height=\"25\" valign=\"middle\" nowrap>");
      if (_jspx_meth_bean_005fmessage_005f1(_jspx_page_context))
        return;
      out.write("：</td>\r\n              <td valign=\"middle\">");
      out.print(session.getAttribute("userName")+"");
      out.write("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\r\n                催办时间：");
      out.print(sf.format(new java.util.Date()));
      out.write("\r\n              </td>\r\n            </tr>\r\n            <tr>\r\n              <td width=\"70\" height=\"70\" valign=\"top\" nowrap>");
      if (_jspx_meth_bean_005fmessage_005f2(_jspx_page_context))
        return;
      out.write("：</td>\r\n              <td>\r\n\t\t\t  <div class=\"pup_scrollbox\" style=\"width:97%;\">\r\n\t\t\t    ");
	
					for(int j = 0; j < activityList.size(); j ++){
						Object[] obj = (Object[]) activityList.get(j);
						if(!obj[0].toString().equals(session.getAttribute("userId")+"")){
				
      out.write("\r\n\t\t\t\t\t\t<div><INPUT TYPE=\"checkbox\" NAME=\"selPress\" checked value=\"");
      out.print(obj[0]+","+obj[1]);
      out.write('"');
      out.write('>');
      out.print(obj[1]+"["+obj[3]+"]");
      out.write("</div>\r\n\t\t\t\t");

					}}
				
      out.write("\r\n\t\t\t\t\t\t<div><INPUT TYPE=\"checkbox\" NAME=\"selPress\" style=\"display:none\"></div>\r\n\t\t\t\t</div></td>\r\n            </tr>\r\n            <tr>\r\n              <td width=\"70\" height=\"30\" valign=\"middle\" nowrap>");
      if (_jspx_meth_bean_005fmessage_005f3(_jspx_page_context))
        return;
      out.write("：</td>\r\n              <td valign=\"top\">\r\n\t\t\t  <textarea name=\"pressTitle\" id=\"pressTitle\" class=\"inputTextarea\" style=\"width:97%;height:99%;\">该流程已办理完毕！请查阅</textarea>&nbsp;<label class=\"mustFillcolor\">*</label></td>\r\n            </tr>\r\n            <tr>\r\n              <td width=\"70\" height=\"100\" valign=\"top\" nowrap>");
      if (_jspx_meth_bean_005fmessage_005f4(_jspx_page_context))
        return;
      out.write("：</td>\r\n              <td valign=\"top\">\r\n                <textarea name=\"pressContent\" id=\"pressContent\" class=\"inputTextarea\" style=\"width:97%;height:99%;\">请抓紧时间办理！</textarea>&nbsp;<label class=\"mustFillcolor\">*</label>\r\n              </td>\r\n            </tr>\r\n\t\t\t<tr ");
      out.print(showSmsRemind?"":"style='display:none'");
      out.write(">\r\n\t\t\t<tr style=\"display:none\">\r\n              <td width=\"70\" height=\"25\" valign=\"middle\" nowrap>");
      out.print(Resource.getValue(local,"filetransact","file.messageremind"));
      out.write("：</td>\r\n              <td valign=\"middle\"><input type=\"checkbox\" id=\"r1\" name=\"pressSMSs\" value=\"true\" checked/>\r\n              </td>\r\n            </tr>\r\n            \r\n          </table>\r\n\t\t</div>\r\n\t</td>\r\n  </tr>\r\n  <tr>\r\n    <td valign=\"middle\" height=\"60\" valign=\"top\" align=\"right\">\t\r\n    <input type=\"button\" name=\"Submit\" value=\"");
      if (_jspx_meth_bean_005fmessage_005f5(_jspx_page_context))
        return;
      out.write("\" class=\"btnButton2font\" onclick=\"include_save();\"/>\r\n    <input type=\"button\" name=\"Cancel\" value=\"");
      if (_jspx_meth_bean_005fmessage_005f6(_jspx_page_context))
        return;
      out.write("\" class=\"btnButton2font\" onclick=\"window.close();\" />\r\n    &nbsp;&nbsp;\r\n    </td>\r\n  </tr>\r\n</table>\r\n</form>\r\n<iframe name=\"include_iframe\" style=\"display:none;\"></iframe>\r\n</body>\r\n</html>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n//保存\r\nfunction include_save(){\r\n\tvar selPressBox = document.all.selPress;\r\n\tvar selPressUserId = '';\r\n\tvar selPressUserName = '';\r\n\tfor(var i=0;i<selPressBox.length;i++){\r\n\t\tif(selPressBox[i].checked){\r\n\t\t\tselPressUserId += \"$\"+selPressBox[i].value.split(\",\")[0] + \"$\";\r\n\t\t\tselPressUserName += selPressBox[i].value.split(\",\")[1] + \",\";\r\n\t\t}\r\n\t}\r\n\tif(selPressUserId==''){\r\n\t\talert(\"请选择催办人！\");\r\n\t\treturn;\r\n\t}\r\n\tif(document.all.pressTitle.value==''){\r\n\t\talert(\"请填写催办标题！\");\r\n\t\treturn;\r\n\t}\r\n\tif(document.all.pressContent.value==''){\r\n\t\talert(\"请填写催办内容！\");\r\n\t\treturn;\r\n\t}\r\n\t//$123$$123$\r\n\t//sam,tom,\r\n\tdocument.getElementById(\"pressUserId\").value=selPressUserId;\r\n\tdocument.getElementById(\"pressUserName\").value=selPressUserName;\r\n\t//window.opener.document.all.pressTitle.value=document.all.pressTitle.value;\r\n");
      out.write("\t//window.opener.document.all.pressContent.value=document.all.pressContent.value;\r\n\tif(document.getElementById(\"r1\").checked){\r\n\t\tdocument.getElementById(\"pressSMS\").value=\"true\";\r\n\t} else{\r\n\t\tdocument.getElementById(\"pressSMS\").value=\"false\";\r\n\t}\r\n\tinclude_waitSubmit();\r\n\twindow.close();\r\n}\r\n//按钮催办\r\nfunction include_waitSubmit(){\r\n    //发邮件\r\n\tfrm.target = \"include_iframe\";\r\n\tfrm.action = \"/jsoa/WorkflowButtonAction.do?flag=press\";\r\n\tfrm.submit();\r\n\talert(\"已成功发送催办！\");\r\n}\r\n</SCRIPT>\r\n");

String workTitle = request.getParameter("workTitle");
if(!"".equals(workTitle)){

      out.write("\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\nvar submitPersonTime = \"");
      out.print(request.getParameter("submitPersonTime").substring(0,16) );
      out.write("\";\r\ndocument.all.pressTitle.value = \"催办：");
      out.print(request.getParameter("submitPerson") );
      out.write("\"+submitPersonTime+\r\n\t\t\"提交的");
      out.print(request.getParameter("workTitles") );
      out.write(" 等待您的办理！\";\r\n</SCRIPT>\r\n");
} else{
      out.write("\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\nvar submitPersonTime = \"");
      out.print(request.getParameter("submitPersonTime").substring(0,16) );
      out.write("\";\r\ndocument.all.pressTitle.value = \"催办：");
      out.print(request.getParameter("submitPerson") );
      out.write("\"+submitPersonTime+\"提交的\";\r\ndocument.all.pressTitle.value += '");
      out.print(request.getParameter("processName"));
      out.write("';\r\ndocument.all.pressTitle.value += \"等待您的办理！\";\r\n</SCRIPT>\r\n");
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
      // /weixin/workflow/wf_press_newForWeiXin.jsp(38,7) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f0.setBundle("filetransact");
      // /weixin/workflow/wf_press_newForWeiXin.jsp(38,7) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f0.setKey("file.remind");
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
      // /weixin/workflow/wf_press_newForWeiXin.jsp(63,64) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f1.setBundle("filetransact");
      // /weixin/workflow/wf_press_newForWeiXin.jsp(63,64) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f1.setKey("file.remindperson");
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
      // /weixin/workflow/wf_press_newForWeiXin.jsp(69,61) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f2.setBundle("filetransact");
      // /weixin/workflow/wf_press_newForWeiXin.jsp(69,61) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f2.setKey("file.remindto");
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

  private boolean _jspx_meth_bean_005fmessage_005f3(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f3 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    boolean _jspx_th_bean_005fmessage_005f3_reused = false;
    try {
      _jspx_th_bean_005fmessage_005f3.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fmessage_005f3.setParent(null);
      // /weixin/workflow/wf_press_newForWeiXin.jsp(85,64) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f3.setBundle("filetransact");
      // /weixin/workflow/wf_press_newForWeiXin.jsp(85,64) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f3.setKey("file.remindtitle");
      int _jspx_eval_bean_005fmessage_005f3 = _jspx_th_bean_005fmessage_005f3.doStartTag();
      if (_jspx_th_bean_005fmessage_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.reuse(_jspx_th_bean_005fmessage_005f3);
      _jspx_th_bean_005fmessage_005f3_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fmessage_005f3, _jsp_getInstanceManager(), _jspx_th_bean_005fmessage_005f3_reused);
    }
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f4(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f4 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    boolean _jspx_th_bean_005fmessage_005f4_reused = false;
    try {
      _jspx_th_bean_005fmessage_005f4.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fmessage_005f4.setParent(null);
      // /weixin/workflow/wf_press_newForWeiXin.jsp(90,62) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f4.setBundle("filetransact");
      // /weixin/workflow/wf_press_newForWeiXin.jsp(90,62) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f4.setKey("file.remindcontent");
      int _jspx_eval_bean_005fmessage_005f4 = _jspx_th_bean_005fmessage_005f4.doStartTag();
      if (_jspx_th_bean_005fmessage_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.reuse(_jspx_th_bean_005fmessage_005f4);
      _jspx_th_bean_005fmessage_005f4_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fmessage_005f4, _jsp_getInstanceManager(), _jspx_th_bean_005fmessage_005f4_reused);
    }
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f5(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f5 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    boolean _jspx_th_bean_005fmessage_005f5_reused = false;
    try {
      _jspx_th_bean_005fmessage_005f5.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fmessage_005f5.setParent(null);
      // /weixin/workflow/wf_press_newForWeiXin.jsp(108,46) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f5.setBundle("filetransact");
      // /weixin/workflow/wf_press_newForWeiXin.jsp(108,46) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f5.setKey("file.send");
      int _jspx_eval_bean_005fmessage_005f5 = _jspx_th_bean_005fmessage_005f5.doStartTag();
      if (_jspx_th_bean_005fmessage_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.reuse(_jspx_th_bean_005fmessage_005f5);
      _jspx_th_bean_005fmessage_005f5_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fmessage_005f5, _jsp_getInstanceManager(), _jspx_th_bean_005fmessage_005f5_reused);
    }
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f6(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f6 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    boolean _jspx_th_bean_005fmessage_005f6_reused = false;
    try {
      _jspx_th_bean_005fmessage_005f6.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fmessage_005f6.setParent(null);
      // /weixin/workflow/wf_press_newForWeiXin.jsp(109,46) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f6.setBundle("common");
      // /weixin/workflow/wf_press_newForWeiXin.jsp(109,46) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f6.setKey("comm.cancel");
      int _jspx_eval_bean_005fmessage_005f6 = _jspx_th_bean_005fmessage_005f6.doStartTag();
      if (_jspx_th_bean_005fmessage_005f6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.reuse(_jspx_th_bean_005fmessage_005f6);
      _jspx_th_bean_005fmessage_005f6_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fmessage_005f6, _jsp_getInstanceManager(), _jspx_th_bean_005fmessage_005f6_reused);
    }
    return false;
  }
}
