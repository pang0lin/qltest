/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:04:33 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.doc.form;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import com.js.doc.doc.po.GovCustomFieldPO;
import com.js.doc.doc.po.GovCustomCheckedFieldPO;

public final class form_005fedt_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(6);
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-nested.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-tiles.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-logic.tld", Long.valueOf(1499751390000L));
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
    _jspx_imports_classes.add("com.js.doc.doc.po.GovCustomCheckedFieldPO");
    _jspx_imports_classes.add("com.js.doc.doc.po.GovCustomFieldPO");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhtml;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction;

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
    _005fjspx_005ftagPool_005fhtml_005fhtml = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fhtml.release();
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.release();
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n<script language=javascript src=\"/jsoa/doc/form/datetime/datetime_check.js\"></script>\r\n<script language=javascript src=\"/jsoa/doc/form/datetime/datetime_select.js\"></script>\r\n<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n<script type='text/javascript' src='/jsoa/doc/form/js/common.js'></script>\r\n<script type='text/javascript' src='/jsoa/doc/form/js/custFormAnsc.js'></script>\r\n\r\n<link rel=\"stylesheet\" type=\"text/css\" href=\"/jsoa/doc/form/ext/resources/css/ext-all.css\"/>\r\n<script type=\"text/javascript\" src=\"/jsoa/doc/form/ext/adapter/ext/ext-base.js\"></script>\r\n<script type=\"text/javascript\" src=\"/jsoa/doc/form/ext/ext-all.js\"></script>\r\n\r\n");
      //  html:html
      org.apache.struts.taglib.html.HtmlTag _jspx_th_html_005fhtml_005f0 = (org.apache.struts.taglib.html.HtmlTag) _005fjspx_005ftagPool_005fhtml_005fhtml.get(org.apache.struts.taglib.html.HtmlTag.class);
      boolean _jspx_th_html_005fhtml_005f0_reused = false;
      try {
        _jspx_th_html_005fhtml_005f0.setPageContext(_jspx_page_context);
        _jspx_th_html_005fhtml_005f0.setParent(null);
        int _jspx_eval_html_005fhtml_005f0 = _jspx_th_html_005fhtml_005f0.doStartTag();
        if (_jspx_eval_html_005fhtml_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write('\r');
            out.write('\n');

 String mustfield=request.getAttribute("mustfield")==null?"":request.getAttribute("mustfield")+"";
 String mustword=request.getAttribute("mustword")==null?"":request.getAttribute("mustword")+"";

 int nowYear = (new Date().getYear()) + 1900;
 
 // 发文还是收文  默认 发文：0   收文：1  文件送：2
  String  govFormType="0";
  if(request.getParameter("govFormType")!=null&&!request.getParameter("govFormType").toString().equals("")){
   govFormType=request.getParameter("govFormType");
  }
  String govFormName="ff";
    if(request.getAttribute("govFormName")!=null&&!request.getAttribute("govFormName").toString().equals("")){
   govFormName=request.getAttribute("govFormName").toString();
  }



  String id=request.getParameter("id").toString();

   // 是显示表单 还是 打印表单    0:显示表单  1：打印表单
  String  gffType="0";
  if(request.getParameter("gffType")!=null&&!request.getParameter("gffType").toString().equals("")){
   gffType=request.getParameter("gffType"); 
  }

  String formId=request.getParameter("formId").toString();

  String customContent="";
  if(request.getAttribute("customContent")!=null){
    customContent=request.getAttribute("customContent").toString();
  }


  //
  String fieldelt="";

  com.js.doc.doc.service.CovCustomBD  cbd=new com.js.doc.doc.service.CovCustomBD();
  List fieldList=new ArrayList();

  if(request.getAttribute("fieldList")!=null){
	  fieldList=(List)request.getAttribute("fieldList");
  }



  List  checkFieldList=new ArrayList();
  if(request.getAttribute("checkFieldList")!=null){
      checkFieldList=(List)request.getAttribute("checkFieldList");
  }
            out.write("\r\n\r\n  <script language=\"javascript\">\r\n\tvar govFieldArr = new Array(");
            out.print(fieldList.size());
            out.write(");\r\n\t");

	for(int fi=0;fi<fieldList.size();fi++){
	GovCustomFieldPO fpo=(GovCustomFieldPO)fieldList.get(fi);
	String fieldName=fpo.getGffName();
	String displayName=fpo.getGffDisplayName();
	String  displayType=""+fpo.getGffFieldType();
	String  isChecked=" ";
	int   gffLength=fpo.getGffLength();
	int   gffDisplayType=fpo.getGffDisplayType();
	int   gft=fpo.getGffFieldType();

	String  tips=" ";
	 switch (gffDisplayType) {
		   case 0:{//普通输入框
			   if(gft==0){
				   tips="字符型("+gffLength+")";				   
			   }else if(gft==1){
				   tips="数值型("+gffLength+")";				   
			   }else if(gft==3){
					 tips="字符型("+gffLength+")";			   
			   }
			   break;
		   }
		   case 1:{// 时间
			   tips="时间";
			   break;
		   }
		   case 2:{//选择框
			   tips="下拉选择";
			   break;
		   }
		   case 4: {//选组织并可修改
			   tips="选组织并可修改";
			   break;
		   }
		   case 5: {//附件
			   tips="附件";
			   break;
		   }

		   case 6: {//机关代字
				tips="机关代字";
			   break;
		   }

		   case 7: {// 文号
			   tips="文号";
			   break;
		   }

		   case 8: {//流水号
			   tips="流水号";
			   break;
		   }
		   case 9: {//流水号
			  tips="主题词";
			  break;
		 }
		 case 10: { //流水号
			  tips="批示意见";
			 break;
		 }
		 case 12: { //流水号
			  tips="多行文本";
			 break;
		 }
		 case 13:{
        	 tips = "多选人";
        	 break;
         }
	   }
 
	
	  if(checkFieldList!=null){
            for(int jj=0;jj<checkFieldList.size();jj++){
              GovCustomCheckedFieldPO  po=(GovCustomCheckedFieldPO)checkFieldList.get(jj);
              if(fieldName.equals(po.getGffName())){
                 displayName=po.getGffDisplayName();   
				 isChecked="checked=\\\"checked\\\"";
				 fieldelt+=fieldName+";";
              }          
            }
	  }
	 
	
            out.write("\r\n\r\n\tgovFieldArr[");
            out.print(fi);
            out.write("] = new Array('");
            out.print(fieldName);
            out.write('\'');
            out.write(',');
            out.write('\'');
            out.print(displayName);
            out.write('\'');
            out.write(',');
            out.write('\'');
            out.print(displayType);
            out.write('\'');
            out.write(',');
            out.write('\'');
            out.print(isChecked);
            out.write('\'');
            out.write(',');
            out.write('\'');
            out.print(tips);
            out.write("');\r\n\r\n\t");
}
            out.write("\r\n </script>\r\n\r\n ");
 

 int index=0;
  Object[] obj = null;
 if("1".equals(request.getAttribute("close"))){
  
            out.write("\r\n   <script language=\"javascript\">\r\n\t  alert(\"保存成功！\");\r\n\t window.close();\r\n     opener.location.href=\"/jsoa/GovCustomAction.do?action=list&govFormType=");
            out.print(govFormType);
            out.write("&pager.offset=");
            out.print(request.getParameter("pager.offset"));
            out.write("&govFormName=");
            out.print(request.getParameter("govFormName_old"));
            out.write("\";\r\n    //opener.parent.topFrame.window.location.reload();\r\n   </script>\r\n");

}else{
            out.write('\r');
            out.write('\n');
if("1".equals(request.getAttribute("stat"))){
            out.write("\r\n<script language=\"javascript\">\r\n\talert(\"表单名重复！请更换名称！\");\r\n</script>\r\n");
} if("0".equals(request.getAttribute("stat"))){
            out.write("\r\n\t<script language=\"javascript\">\r\n\t\talert(\"新增表单失败，请重试或与管理员联系！\");\r\n\t</script>\r\n");
}
            out.write("\r\n\r\n<head>\r\n<title>修改表单信息</title>\r\n<link href=\"skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject2.css\">\r\n\r\n\r\n</head>\r\n<body  onload=\"resizeWin(1024,768);initEditor();\" class=\"MainFrameBox Pupwin\">\r\n");
            //  html:form
            org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag) _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(org.apache.struts.taglib.html.FormTag.class);
            boolean _jspx_th_html_005fform_005f0_reused = false;
            try {
              _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
              _jspx_th_html_005fform_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
              // /doc/form/form_edt.jsp(200,0) name = action type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setAction("/GovCustomAction.do");
              // /doc/form/form_edt.jsp(200,0) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setMethod("POST");
              int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
              if (_jspx_eval_html_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n<table width=\"100%\"  height=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"docBoxNoPanel\">\r\n\r\n\t<input type=\"hidden\" name=\"operate\" />\r\n\t<input type=\"hidden\" name=\"fieldelt\"  value=\"");
                  out.print(fieldelt);
                  out.write("\"/>\r\n\t<input type=\"hidden\" name=\"pageid\" value=\"");
                  out.print(request.getParameter("pageid"));
                  out.write("\"/>\r\n\t<input type=\"hidden\" name=\"pager.offset\" value=\"");
                  out.print(request.getParameter("pager.offset"));
                  out.write("\">\r\n\t<input type=\"hidden\" name=\"govFormName_old\" value=\"");
                  out.print(request.getParameter("govFormName_old"));
                  out.write("\">\r\n\t<input type=\"hidden\" name=\"govFormType\" value=\"");
                  out.print(govFormType);
                  out.write("\">\r\n\t<input type=\"hidden\" name=\"gffType\"  value=\"");
                  out.print(gffType);
                  out.write("\">\r\n\t<input type=\"hidden\" name=\"displayValues\" value=\"\">\r\n\t<input type=\"hidden\" name=\"id\" value=\"");
                  out.print(id);
                  out.write("\">\r\n\t<input type=\"hidden\" name=\"formId\" value=\"");
                  out.print(formId);
                  out.write("\">\r\n\t<input type=\"hidden\" name=\"action\" value=\"modify\">\r\n\t<input type=\"hidden\" id=\"mustfield\" name=\"mustfield\" value=\"");
                  out.print(mustfield);
                  out.write("\"/>\r\n <tr>\r\n <td valign=\"top\"  height=\"100%\">\r\n\t<table width=\"100%\" border=\"0\">\r\n\t\t<tr>\r\n\t\t\t<td> ");
                  out.print("1".equals(request.getParameter("isPrint"))?"打印模版":"显示模版");
                  out.write("</td>\r\n\t\t</tr>\r\n\t\t<tr>\r\n\t\t\t<td width=\"120\" nowrap=\"nowrap\">表单名称<label class=\"mustFillcolor\">*</label>：</td>\r\n\t\t\t<td>\r\n\t\t\t\t<input name=\"govFormName\" ");
if(gffType.equals("1")){ out.print("readonly");}
                  out.write(" type=\"text\" value=\"");
                  out.print(govFormName);
                  out.write("\" class=\"inputText\" style=\"width:100%\" onkeydown=\"javascript:if(event.keyCode==13) return false;\"/>\r\n\t\t\t</td>\r\n\t\t\t<td></td>\r\n\t\t\t<td width=\"20%\">&nbsp;</td>\r\n       \t</tr>\r\n        <!--为发文的表单中添加 选择是否同步模块  -->\r\n       ");
String display = "";
       if("1".equals(request.getParameter("isPrint"))){}else{ 
                  out.write("\r\n        <tr id=\"fckTitle\" style=\"display:");
                  out.print(display );
                  out.write(";\">\r\n       \t\t<td>是否同步打印模板：</td>\r\n       \t\t<td>\r\n       \t\t\t<input type=\"radio\" name=\"isUpdatePrint\"  id=\"isUpdatePrint\" value=\"1\" />是   \r\n          \t\t<input type=\"radio\" name=\"isUpdatePrint\"  id=\"isUpdatePrint\" value=\"0\" checked/>否\r\n    \t \t</td>\r\n\t \t  </tr>\r\n       ");
}
                  out.write("\r\n   \t</table>\r\n\t<table width=\"100%\" border=\"0\"  height=\"100%\">\r\n\t\t<tr>\r\n\t       <td>\r\n\t       <div>\r\n\t       \t\t<div style=\"float:left;width:120px;margin-top:15px;\">必填字段：</div>\r\n\t       \t\t<textarea readonly=\"true\" title=\"点击请选择\" id=\"mustword\" name=\"mustword\" rows=\"3\" class=\"inputTextarea\" style=\"width:70%;\" onclick=\"choose()\">");
                  out.print(mustword );
                  out.write("</textarea>\r\n\t       </div>\r\n\t       </td>\r\n       </tr>\r\n\t\t<tr id=\"fckLine\" style=\"display:\">\r\n            <td id=\"fckCon\" style=\"width:100%\">\r\n\t\t\t    <table width=\"100%\" border=\"0\">\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t   <td style=\"width:80%\" ><input type=\"hidden\" name=\"code\"/><div id=\"codeDIV\" style=\"display:none\">");
                  out.print(customContent);
                  out.write("</div>\r\n\t\t\t\t\t\t");
 if(com.js.util.util.BrowserJudge.isMSIE(request)) { 
                  out.write("\r\n\t\t\t\t\t\t<IFRAME id=\"pinEditID1\" src=\"/jsoa/public/edit/ewebeditor.htm?id=code&style=coolblue&lang=");
                  out.print(session.getAttribute("org.apache.struts.action.LOCALE"));
                  out.write("\" frameborder=\"0\" scrolling=\"no\" width=\"100%\" height=\"520\"></IFRAME>\r\n\t\t\t\t\t\t");
 }else{
                  out.write("\r\n\t\t\t\t\t\t<IFRAME id=\"pinEditID1\" src=\"/jsoa/public/edit8/ewebeditor.htm?id=code&style=coolblue&lang=");
                  out.print(session.getAttribute("org.apache.struts.action.LOCALE"));
                  out.write("\" frameborder=\"0\" scrolling=\"no\" width=\"100%\" height=\"520\"></IFRAME>\r\n\t\t\t\t\t\t");
}
                  out.write("\t\r\n\t\t\t\t\t   </td>\r\n\t\t\t\t\t   <td style=\"width:20%\" id=\"fieldDIV\" valign=\"top\">\r\n\t\t\t\t\t   </td>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t</table>\r\n            </td>\r\n        </tr>\r\n\r\n\t\t<tr  height=\"25%\" valign=\"top\">\r\n          <td>\r\n         \t<input type=\"hidden\" class=\"btnButton4font\" id=\"saveAndContinue\"onclick=\"javascript:save('modify');\" value=\"保存继续1\" />\r\n\t\t    <input type=\"button\" class=\"btnButton4font\" id=\"saveAndExit\"onclick=\"javascript:save('modify');\" value=\"保存退出\" />\r\n\t\t    <input type=\"button\" class=\"btnButton2font\" onclick=\"location.reload();\" value=\"重置\" />\r\n\t\t    <input type=\"button\" class=\"btnButton2font\" onclick=\"javascript:window.close()\" value=\"退出\" />\r\n\t\t  </td>\r\n        </tr>\r\n\t\t</table>\r\n\t</td></tr>\r\n\t</table>\r\n\t<DIV id=adddelrow_div\r\nstyle=\"BORDER-RIGHT: #0a246a 1px solid; BORDER-TOP: #0a246a 1px solid; VISIBILITY: hidden; BORDER-LEFT: #0a246a 1px solid; WIDTH: 50px; BORDER-BOTTOM: #0a246a 1px solid; POSITION: absolute\">\r\n<TABLE height=\"100%\" cellSpacing=0 cellPadding=0 width=\"100%\" border=0>\r\n  <TBODY>\r\n  <TR>\r\n    <TD onmouseover=\"this.style.borderRiht='1px #0A246A solid'\"\r\n");
                  out.write("    onmouseout=\"this.style.borderRiht=''\" align=middle><SPAN id=delrow_div\r\n      title=点击删除重复项 style=\"CURSOR: hand\"><IMG height=15\r\n      src=\"/jsoa/custom_form/images/delarrow.gif\" width=16 align=absMiddle></SPAN> </TD>\r\n    <TD onmouseover=\"this.style.borderLeft='1px #0A246A solid'\"\r\n    onmouseout=\"this.style.borderLeft=''\" align=middle><SPAN id=addrow_div\r\n      title=点击添加重复项 style=\"CURSOR: hand\"><IMG height=15\r\n      src=\"/jsoa/custom_form/images/addarrow.gif\" width=16 align=absMiddle></SPAN>\r\n  </TD></TR></TBODY></TABLE></DIV>\r\n");
                  int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
              }
              if (_jspx_th_html_005fform_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                return;
              }
              _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
              _jspx_th_html_005fform_005f0_reused = true;
            } finally {
              org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fform_005f0, _jsp_getInstanceManager(), _jspx_th_html_005fform_005f0_reused);
            }
            out.write("\r\n</body>\r\n<script language=\"javascript\">\r\n//显示主表字段-加入国投项目判断\r\nfunction  gov_showFile(govFieldArr){\r\n\tvar bitian=\"@receiveFileSendFileUnit@receiveFileTitle@accessoryName@sendFileDepartWord@documentSendFileByteNumber@documentSendFileTitle@\"\r\n\t");
if("shandongguotou".equals(com.js.util.config.SystemCommon.getCustomerName())){ 
            out.write("\r\n\t\tbitian += \"zjkyType@accessoryName1@\";\r\n\t");
}
            out.write("\r\n\t\r\n\tvar color=\"\";\r\n\tvar html = \"\";\r\n\tvar htmlArr = new Array();\r\n    if (govFieldArr != null && govFieldArr.length > 0) {\r\n\t\t\r\n\t\t if (govFieldArr != null && govFieldArr.length > 0) {\r\n\t    \tfor(var i=0;i<govFieldArr.length;i++){\r\n\t    \t\tif(bitian.indexOf(\"@\"+govFieldArr[i][0]+\"@\")>=0){\r\n\t    \t\t\tcolor=\"style='background:#E1FAFD;'\";\r\n\t    \t\t}\r\n\t\t\t\thtml += \"<input type=checkbox id=\"+govFieldArr[i][0]+\" value=\"+govFieldArr[i][0]+\"-\"+govFieldArr[i][1]+\"-\"+govFieldArr[i][2]+\" onclick=gov_setHTML2Editor(this,'\"+govFieldArr[i][1]+\"') \"+govFieldArr[i][3]+\"><input type='text' size='10' id=\"+govFieldArr[i][0]+\"_label \"+color+\" value=\"+govFieldArr[i][1]+\">\"+govFieldArr[i][4]+\"<br>\";\r\n\t\t\t\tcolor = \"\";\r\n\t\t\t}\r\n\t\t//\thtml += \"<input type=checkbox id=ffff value=ffff-ddddffff-00000 onclick=gov_setHTML2Editor(this,'ddddffff')><input type='text'  id=ffff_label value=ddddffff> <br>\";\r\n\r\n\t\t\thtmlArr[0] = {title: '主表字段','html': html};\r\n\t\t }\r\n       //去掉了关联子表\r\n\t\t\r\n    }\r\n\t//alert(1);\r\n\tif(htmlArr.length<1)htmlArr[0] = {title: '主表字段',html: ''};\r\n");
            out.write("\tinitTab(htmlArr);\r\n}\r\n\r\n\r\n\r\nfunction initEditor(){\r\n\t\r\n\t gov_showFile(govFieldArr);\r\n\t if(document.all){\r\n\t  \tpinEditID1.insertHTML(document.all.codeDIV.innerHTML);\r\n\t }else{\r\n\t\tdocument.getElementById(\"pinEditID1\").contentWindow.insertHTML(document.all.codeDIV.innerHTML);\r\n\t }\r\n\t\r\n\t document.all.codeDIV.innerHTML = \"\";\r\n}\r\n\r\n//保存自定义表单-加入国投项目判断\r\nfunction save(tag){\r\n\r\n    if(checkmsg()){\r\n    \t\tif(navigator.userAgent.indexOf(\"MSIE\")>0){\r\n\t\t\t\tGovCustomActionForm.code.value=pinEditID1.getHTML();\r\n\t\t\t}else{\r\n\t\t\t\tGovCustomActionForm.code.value=document.getElementById(\"pinEditID1\").contentWindow.getHTML()\r\n\t\t\t}  \r\n\t\t\t//GovCustomActionForm.code.value=pinEditID1.getHTML();\r\n\t\t\tGovCustomActionForm.operate.value=tag;\r\n\r\n\t\t\t/*var dd=document.all.sendFileAccessoryDesc_label.value;\r\n\t\t\talert(dd);\r\n\t\t\treturn ;*/\r\n            document.all.action.value=tag;\r\n\r\n\t\t\tvar fieldeltvalue=document.all.fieldelt.value;\r\n\t\t\t\tif(document.all.govFormType.value==\"0\"){\r\n\t\t\t\t\t");
if(!"ynnt".equals(com.js.util.config.SystemCommon.getCustomerName())){
            out.write("\r\n\t\t\t\t\t\tif(fieldeltvalue.indexOf(\"accessoryName\")<0){\r\n\t\t\t\t\t\t\talert(\"附件是必选项，请您选中!\");\r\n\t\t\t\t\t\t\treturn false;\r\n\t\r\n\t\t\t\t\t\t}\r\n\t\t\t\t\t\t\r\n\t\t\t\t\t\tif(fieldeltvalue.indexOf(\"sendFileDepartWord\")<0){\r\n\t\t\t\t\t\t\t\talert(\"机关代字是必选项，请您选中!\");\r\n\t\t\t\t\t\t\t\treturn false;\r\n\t\r\n\t\t\t\t\t\t}\r\n\t\t\t\t\t\tif(fieldeltvalue.indexOf(\"documentSendFileByteNumber\")<0){\r\n\t\t\t\t\t\t\t\talert(\"文号是必选项，请您选中!\");\r\n\t\t\t\t\t\t\t\treturn false;\r\n\t\t\t\t\t\t}\r\n\t\t\t\t\t");
}
            out.write("\r\n\t\t\t\t\r\n\t\t\t\tif(fieldeltvalue.indexOf(\"documentSendFileTitle\")<0){\r\n\t\t\t\t\t\talert(\"发文标题是必选项，请您选中!\");\r\n\t\t\t\t\t\treturn false;\r\n\t\t\t\t}\r\n\t\t\t\t\r\n\t\t\t}\r\n\r\n             if(document.all.govFormType.value==\"1\"){\r\n            \t ");
if(!"ynnt".equals(com.js.util.config.SystemCommon.getCustomerName())){
            out.write("\r\n\t            \t if(fieldeltvalue.indexOf(\"receiveFileSendFileUnit\")<0){\r\n\t \t\t\t\t\talert(\"来文单位是必选项，请您选中!\");\r\n\t \t\t\t\t\treturn false;\r\n\t\r\n\t \t\t\t\t\t}\r\n            \t ");
}
            out.write("\r\n\t\t\t\tif(fieldeltvalue.indexOf(\"receiveFileTitle\")<0){\r\n\t\t\t\t\t\talert(\"标题是必选项，请您选中!\");\r\n\t\t\t\t\t\treturn false;\r\n\t\t\t\t}\r\n\t\t\t\t");
if("shandongguotou".equals(com.js.util.config.SystemCommon.getCustomerName())){
            out.write("\r\n\t\t\t\tif(fieldeltvalue.indexOf(\"zjkyType\")<0){\r\n\t\t\t\t\t\talert(\"文种是必选项，请您选中！\");\r\n\t\t\t\t\t\treturn false;\r\n\t\t\t\t}\r\n\t\t\t\tif(fieldeltvalue.indexOf(\"accessoryName1\")<0){\r\n\t\t\t\t\t\talert(\"正文是必选项，请您选中！\")\r\n\t\t\t\t\t\treturn false;\r\n\t\t\t\t}\r\n\t\t\t  ");
}
            out.write("\r\n\t\t\t \r\n\t\t\t }\r\n\t\t\t if(document.all.govFormType.value==\"2\"){\r\n\t\t\t \r\n\t\t\t\tif(fieldeltvalue.indexOf(\"sendFileCheckComeUnit\")<0){\r\n\t\t\t\t  alert(\"来文单位是必选项，请您选中!\");\r\n\t\t\t\t  return false;\r\n\r\n\t\t\t\t}\r\n\t\t\t\tif(fieldeltvalue.indexOf(\"sendFileCheckTitle\")<0){\r\n\t\t\t\t  alert(\"标题是必选项，请您选中!\");\r\n\t\t\t\t   return false;\r\n\r\n\t\t\t\t}\r\n\t\t\t \r\n\t\t\t }\r\n\r\n             var fieldeltArr=fieldeltvalue.split(\";\");\r\n             var displayValues=\"\";\r\n\r\n\t\t\t for(var i=0;i<fieldeltArr.length-1;i++){\r\n\t\t\t\t  var fieldName=fieldeltArr[i];\r\n\t\t\t\t  var fieldNameValue=eval(\"document.all.\"+fieldName+\"_label.value\");\r\n\t\t\t\t  if(fieldNameValue.indexOf(\"\\\"\") >= 0){\r\n\t\t\t\t    alert(\"请检查，字段名称中不能包含\\\"\");\r\n\t\t\t\t\teval(\"document.all.\"+fieldName+\"_label.focus()\");\r\n\t\t\t\t\treturn ;\r\n\t\t\t\t  }\r\n\t\t\t\t   if(fieldNameValue.indexOf(\"'\") >= 0){\r\n\t\t\t\t\t  alert(\"请检查，字段名称中不能包含'\");\r\n\t\t\t\t\t  \teval(\"document.all.\"+fieldName+\"_label.focus()\");\r\n\t\t\t\t\t  return ;\r\n\t\t\t\t   \r\n\t\t\t\t  }\r\n\t\t\t\t  if(fieldNameValue.length>20){\r\n\t\t\t\t    alert(\"请检查，字段名称长度不能超过20'\");\r\n\t\t\t\t\t\teval(\"document.all.\"+fieldName+\"_label.focus()\");\r\n");
            out.write("\t\t\t\t\treturn ;\r\n\t\t\t\t  }\r\n                  displayValues+=fieldName+\"_\"+fieldNameValue+\";\";                  \r\n\t\t\t\t}\r\n\r\n\t\t\tdocument.all.displayValues.value=displayValues;\r\n\t\t\tsetButtonDisabled(\"saveAndExit\",true);\r\n\t\t\tsetButtonDisabled(\"saveAndContinue\",true);\r\n\t\t\tsetTimeout(\"fobbidenBtn()\",8000);\r\n            GovCustomActionForm.submit();\r\n\t\t\r\n    }\r\n}\r\nfunction choose(){\r\n\tvar mustfield=document.getElementById(\"mustfield\").value;\r\n\tvar url=\"/jsoa/doc/form/chooseMustWord.jsp?dovtype=");
            out.print(govFormType);
            out.write("&mustfield=\"+mustfield;\r\n\t//JSMainWinOpen4(url,'选择必填字段','menubar=0,scrollbars=0,locations=0,width=274,height=230,resizable=yes');\r\n\twindow.open(url,\"\",'menubar=0,scrollbars=1,locations=0,width=474,height=430,resizable=yes');\r\n}\r\n</script>\r\n\r\n");
}
            out.write('\r');
            out.write('\n');
            int evalDoAfterBody = _jspx_th_html_005fhtml_005f0.doAfterBody();
            if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
              break;
          } while (true);
        }
        if (_jspx_th_html_005fhtml_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return;
        }
        _005fjspx_005ftagPool_005fhtml_005fhtml.reuse(_jspx_th_html_005fhtml_005f0);
        _jspx_th_html_005fhtml_005f0_reused = true;
      } finally {
        org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fhtml_005f0, _jsp_getInstanceManager(), _jspx_th_html_005fhtml_005f0_reused);
      }
      out.write("\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
