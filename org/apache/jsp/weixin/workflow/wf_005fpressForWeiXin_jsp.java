/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:59:07 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.weixin.workflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.oa.message.action.ModelSendMsg;
import com.js.oa.jsflow.service.WorkFlowButtonBD;
import java.util.List;
import com.js.oa.jsflow.vo.WorkVO;
import com.js.oa.weixin.common.util.WorkflowForWeiXinUtil;
import com.js.lang.Resource;

public final class wf_005fpressForWeiXin_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("java.util.List");
    _jspx_imports_classes.add("com.js.oa.jsflow.vo.WorkVO");
    _jspx_imports_classes.add("com.js.oa.weixin.common.util.WorkflowForWeiXinUtil");
    _jspx_imports_classes.add("com.js.lang.Resource");
    _jspx_imports_classes.add("com.js.oa.message.action.ModelSendMsg");
    _jspx_imports_classes.add("com.js.oa.jsflow.service.WorkFlowButtonBD");
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

String local = session.getAttribute("org.apache.struts.action.LOCALE").toString();

//构造WorkVO
String processId = request.getParameter("processId");
String tableId = request.getParameter("tableId");
String recordId = request.getParameter("recordId");
String msgFrom = request.getParameter("msgFrom");
WorkVO workVO = new WorkVO();
workVO.setTableId(Long.valueOf(tableId));
workVO.setRecordId(Long.valueOf(recordId));
workVO.setProcessId(Long.valueOf(processId));
WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
List activityList = workFlowButtonBD.getAllDealwithUsersByStatus(workVO,"0");
String processName = WorkflowForWeiXinUtil.decordStr(request.getParameter("processName"));
java.text.SimpleDateFormat sf = new java.text.SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

//显示短信
boolean showSmsRemind = false;
if(new ModelSendMsg().judgePurviewMessage(msgFrom,session.getAttribute("domainId")==null?"0":session.getAttribute("domainId").toString())){
	if(new com.js.system.manager.service.ManagerService().hasRight(session.getAttribute("userId").toString(), "09*01*01")){
		showSmsRemind = true;
	}
}
String loginType = null!=session.getAttribute("loginType") ? session.getAttribute("loginType").toString() : "";

      out.write("\r\n<!DOCTYPE HTML>\r\n<html>\r\n\t<head>\r\n\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n\t\t<title>");
      if (_jspx_meth_bean_005fmessage_005f0(_jspx_page_context))
        return;
      out.write("</title>\r\n\t\t<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t\t<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n\t\t<SCRIPT language=\"javascript\" src=\"/jsoa/js/openEndow.js\"></SCRIPT>\r\n\t\t<style type=\"text/css\">\r\n\t\t* { margin: 0; padding: 0;}\r\n\t\tbody { background: #f5f5f5; padding-bottom: 80px;}\r\n\t\t.trItem { width: 96%; margin: 5px auto 5px; clear: both;}\r\n\t\t.childItem { padding: 5px 15px 5px 5px;}\r\n\t\t</style>\r\n\t</head>\r\n\r\n\t<body class=\"paddingTop\">\r\n\t\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/wap2/commonImport.jsp", out, false);
      out.write("\r\n\t\t");

		if("wap".equals(loginType)){
			
      out.write("\r\n\t\t\t<div id=\"mainpage_navdiv\" class=\"top\">\r\n\t\t    \t<a id=\"mainpage_backurl\" href=\"javascript:showFormBody();\">\r\n\t\t    \t\t<img width=\"20\" height=\"15\" src=\"wap2/images/topfh.png\">\r\n\t\t    \t</a>\r\n\t\t    \t<p id=\"mainpage_title\">催办</p>\r\n\t    \t</div>\r\n\t\t\t");

		}
		
      out.write("\r\n\t\r\n\t\t<div id=\"pup_top\" class=\"trItem\" style=\"display:none\">\r\n   \t\t\t<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n     \t\t\t<tr>\r\n       \t\t\t\t<td><div id=\"pup_topright\"></div></td>\r\n       \t\t\t\t<td id=\"pup_topleft\">&nbsp;</td>\r\n     \t\t\t</tr>\r\n   \t\t\t</table>\r\n\t    </div>\r\n\t    \r\n\t  \t<div class=\"trItem\">\r\n\t\t\t");
      if (_jspx_meth_bean_005fmessage_005f1(_jspx_page_context))
        return;
      out.write('：');
      out.print(session.getAttribute("userName")+"");
      out.write("\r\n\t\t</div>\r\n\r\n\t\t<div class=\"trItem\">\r\n\t\t\t催办时间：");
      out.print(sf.format(new java.util.Date()));
      out.write("\r\n\t\t</div>\r\n\r\n\t\t<!-- 选择被催办人 -->\r\n\t\t<div class=\"trItem\">\r\n\t\t\t<label class=\"mustFillcolor\">*</label>");
      if (_jspx_meth_bean_005fmessage_005f2(_jspx_page_context))
        return;
      out.write("：\r\n\t\t</div>\r\n\t\t<div class=\"trItem\" style=\"height:220px;-webkit-overflow-scrolling:touch;overflow:auto;background: #fff; border: 1px solid #dadada; border-radius: 5px; color: #b2b2b2;\">\r\n \t\t\t");
	
			for(int j = 0; j < activityList.size(); j ++){
				Object[] obj = (Object[]) activityList.get(j);
				if(!obj[0].toString().equals(session.getAttribute("userId")+"")){
					
      out.write("\r\n\t\t\t\t\t<div class=\"childItem\" style=\"clear: right;\">\r\n\t\t\t\t\t\t<INPUT TYPE=\"checkbox\" NAME=\"selPress\" checked value=\"");
      out.print(obj[0]+","+obj[1]);
      out.write('"');
      out.write('>');
      out.print(obj[1]+"["+obj[3]+"]");
      out.write("\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t");

				}
			}
			
      out.write("\r\n\t\t\t<div class=\"childItem\"><INPUT TYPE=\"checkbox\" NAME=\"selPress\" style=\"display:none\"></div>\r\n\t\t</div>\r\n\r\n\t\t<!-- 催办信息标题 -->\r\n\t\t<div class=\"trItem\">\r\n\t\t\t<label class=\"mustFillcolor\">*</label>");
      if (_jspx_meth_bean_005fmessage_005f3(_jspx_page_context))
        return;
      out.write("：\r\n\t\t</div>\r\n\t\t<div class=\"trItem\" style=\" border-bottom: 1px solid #dadada;\">\r\n\t\t\t<textarea rows=\"2\" name=\"pressTitle\" id=\"pressTitle\" class=\"inputTextarea\" style=\"color: #b2b2b2; width: 100%; border-radius: 5px; border: 0; padding: 3px;\">该流程已办理完毕！请查阅</textarea>\r\n\t\t</div>\r\n\t\t\r\n\t\t<!-- 催办信息内容 -->\r\n\t\t<div class=\"trItem\">\r\n\t\t\t<label class=\"mustFillcolor\">*</label>");
      if (_jspx_meth_bean_005fmessage_005f4(_jspx_page_context))
        return;
      out.write("：\r\n\t\t</div>\r\n\t\t<div class=\"trItem\" style=\"height: 30%; text-align: center; background: #fff;\">\r\n\t\t\t<textarea name=\"pressContent\" id=\"pressContent\" class=\"inputTextarea\" style=\"color: #b2b2b2; width:100%;height:100%; border: 1px solid #dadada; border-radius: 5px; padding: 3px;\">请抓紧时间办理！</textarea>\r\n\t\t</div>\r\n\t\t\r\n\t\t");
      out.write("\r\n\t\t\r\n\t\t<div class=\"trItem\" style=\"display:none\">\r\n\t\t\t");
      out.print(Resource.getValue(local,"filetransact","file.messageremind"));
      out.write("：\r\n\t        <input type=\"checkbox\" id=\"r1\" name=\"pressSMS\" value=\"1\" checked/>\r\n\t\t</div>\r\n\t\t\r\n\t\t<!-- 按钮 -->\r\n\t\t<div class=\"footer\" style=\"position: fixed;\">\r\n\t      \t<div class=\"buttons\">\r\n\t      \t\t<div class=\"button\" onclick=\"javascript:include_save();\">\r\n\t      \t\t\t");
      if (_jspx_meth_bean_005fmessage_005f5(_jspx_page_context))
        return;
      out.write("\r\n\t      \t\t</div>\r\n\t      \t\t<div class=\"button gray\" onclick=\"javascript:showFormBody();\">\r\n\t      \t\t\t");
      if (_jspx_meth_bean_005fmessage_005f6(_jspx_page_context))
        return;
      out.write("\r\n\t      \t\t</div>\r\n\t      \t</div>\r\n\t\t</div>\r\n\t</body>\r\n</html>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n\tparent.document.getElementById(\"formBody\").style.display = \"none\";\r\n\t//保存\r\n\tfunction include_save(){\r\n\t\tvar selPressBox = document.all.selPress;\r\n\t\tvar selPressUserId = '';\r\n\t\tvar selPressUserName = '';\r\n\t\tfor(var i=0;i<selPressBox.length;i++){\r\n\t\t\tif(selPressBox[i].checked){\r\n\t\t\t\tselPressUserId += \"$\"+selPressBox[i].value.split(\",\")[0] + \"$\";\r\n\t\t\t\tselPressUserName += selPressBox[i].value.split(\",\")[1] + \",\";\r\n\t\t\t}\r\n\t\t}\r\n\t\tif(selPressUserId==''){\r\n\t\t\tparent.weixinMessageReminder(\"alert\", \"提示：\", \"");
      out.print(Resource.getValue(local,"filetransact","file.selectremind"));
      out.write("\", \"\");\r\n\t\t\treturn;\r\n\t\t}\r\n\t\tif(document.all.pressTitle.value==''){\r\n\t\t\tparent.weixinMessageReminder(\"alert\", \"提示：\", \"");
      out.print(Resource.getValue(local,"filetransact","file.selectremindtitle"));
      out.write("\", \"\");\r\n\t\t\treturn;\r\n\t\t}\r\n\t\tif(document.all.pressContent.value==''){\r\n\t\t\tparent.weixinMessageReminder(\"alert\", \"提示：\", \"");
      out.print(Resource.getValue(local,"filetransact","file.selectremindcontent"));
      out.write("\", \"\");\r\n\t\t\treturn;\r\n\t\t}\r\n\t\t//$123$$123$\r\n\t\t//sam,tom,\r\n\t\twindow.parent.document.all.pressUserId.value=selPressUserId;\r\n\t\twindow.parent.document.all.pressUserName.value=selPressUserName;\r\n\t\twindow.parent.document.all.pressTitle.value=document.all.pressTitle.value;\r\n\t\twindow.parent.document.all.pressContent.value=document.all.pressContent.value;\r\n\t\tif(document.all.pressSMS.checked){\r\n\t\t\twindow.parent.document.all.pressSMS.value=\"true\";\r\n\t\t} else{\r\n\t\t\twindow.parent.document.all.pressSMS.value=\"false\";\r\n\t\t}\r\n\t\r\n\t\twindow.parent.include_waitSubmit();\r\n\t}\r\n\tfunction showFormBody(){\r\n\t\tparent.document.getElementById(\"formBody\").style.display = \"block\";\r\n\t\tparent.document.getElementById('commonPopDiv').style.display = 'none';\r\n\t}\r\n\t");

	String workTitle = request.getParameter("workTitle");
	if(!"".equals(workTitle)){
		
      out.write("\r\n\t\tvar submitPersonTime = window.parent.document.all.submitPersonTime.value.substring(2,16);\r\n\t\tdocument.all.pressTitle.value = \"");
      out.print(Resource.getValue(local,"filetransact","file.remind"));
      out.write("：\"+window.parent.document.all.submitPerson.value +submitPersonTime+\"");
      out.print(Resource.getValue(local,"filetransact","file.turnto"));
      out.write("\"+ window.parent.document.all.");
      out.print(workTitle);
      out.write(".value + \"");
      out.print(Resource.getValue(local,"filetransact","file.waityourdeal"));
      out.write("\";\r\n\t\t");

	} else{
		
      out.write("\r\n\t\tvar submitPersonTime = window.parent.document.all.submitPersonTime.value.substring(2,16);\r\n\t\tdocument.all.pressTitle.value = \"");
      out.print(Resource.getValue(local,"filetransact","file.remind"));
      out.write("：\"+window.parent.document.all.submitPerson.value +submitPersonTime+\"");
      out.print(Resource.getValue(local,"filetransact","file.turnto"));
      out.write("\";\r\n\t\tdocument.all.pressTitle.value += '");
      out.print(processName);
      out.write("';\r\n\t\tdocument.all.pressTitle.value += \"");
      out.print(Resource.getValue(local,"filetransact","file.waityourdeal"));
      out.write("\";\r\n\t\t");

	}
	
      out.write("\r\n</SCRIPT>");
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
      // /weixin/workflow/wf_pressForWeiXin.jsp(39,9) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f0.setBundle("filetransact");
      // /weixin/workflow/wf_pressForWeiXin.jsp(39,9) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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
      // /weixin/workflow/wf_pressForWeiXin.jsp(76,3) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f1.setBundle("filetransact");
      // /weixin/workflow/wf_pressForWeiXin.jsp(76,3) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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
      // /weixin/workflow/wf_pressForWeiXin.jsp(85,41) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f2.setBundle("filetransact");
      // /weixin/workflow/wf_pressForWeiXin.jsp(85,41) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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
      // /weixin/workflow/wf_pressForWeiXin.jsp(105,41) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f3.setBundle("filetransact");
      // /weixin/workflow/wf_pressForWeiXin.jsp(105,41) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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
      // /weixin/workflow/wf_pressForWeiXin.jsp(113,41) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f4.setBundle("filetransact");
      // /weixin/workflow/wf_pressForWeiXin.jsp(113,41) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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
      // /weixin/workflow/wf_pressForWeiXin.jsp(130,10) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f5.setBundle("filetransact");
      // /weixin/workflow/wf_pressForWeiXin.jsp(130,10) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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
      // /weixin/workflow/wf_pressForWeiXin.jsp(133,10) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f6.setBundle("common");
      // /weixin/workflow/wf_pressForWeiXin.jsp(133,10) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
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