/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:07:05 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.lang.Resource;
import javax.sql.*;
import java.sql.*;
import com.js.oa.jsflow.service.ActivityBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.vo.SimpleFieldVO;

public final class jsflow_005fmodifyfirstact_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-html.tld", Long.valueOf(1499751390000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("java.sql");
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.sql");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.oa.jsflow.vo.SimpleFieldVO");
    _jspx_imports_classes.add("com.js.lang.Resource");
    _jspx_imports_classes.add("com.js.oa.jsflow.service.ProcessBD");
    _jspx_imports_classes.add("com.js.oa.jsflow.service.ActivityBD");
    _jspx_imports_classes.add("com.js.oa.jsflow.service.WorkFlowBD");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhtml;

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
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fhtml.release();
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

request.setCharacterEncoding("GBK");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

String local = session.getAttribute("org.apache.struts.action.LOCALE").toString();

String processId=request.getParameter("processId");
String activityId=request.getParameter("activityId");
String tableId=request.getParameter("tableId");
String moduleId=request.getParameter("moduleId");
String nodeId=request.getParameter("nodeId");

java.util.List noWriteList = (java.util.List) request.getAttribute("noWriteFieldList");
java.util.List fieldList = (java.util.List) request.getAttribute("fieldList");
java.util.List noDisplayList = (java.util.List) request.getAttribute("noDisplayFieldList");
if(fieldList==null){
	fieldList = new java.util.ArrayList();
}
String activityName = "";
DataSource ds=new com.js.util.util.DataSourceBase().getDataSource();
Connection conn=null;
Statement stmt=null;
ResultSet rs=null;
conn=ds.getConnection();
stmt=conn.createStatement();
rs = stmt.executeQuery("select activityName from jsf_activity where wf_activity_id="+activityId);
if(rs.next()){
   activityName = rs.getString("activityName");
}
if(activityName == null || "null".equals(activityName)){
   activityName = "开始";
}
if(rs!= null){
  rs.close();
}
stmt.close();
conn.close();

SimpleFieldVO fieldVO = null;


      out.write("\r\n\r\n");
      //  html:html
      org.apache.struts.taglib.html.HtmlTag _jspx_th_html_005fhtml_005f0 = (org.apache.struts.taglib.html.HtmlTag) _005fjspx_005ftagPool_005fhtml_005fhtml.get(org.apache.struts.taglib.html.HtmlTag.class);
      boolean _jspx_th_html_005fhtml_005f0_reused = false;
      try {
        _jspx_th_html_005fhtml_005f0.setPageContext(_jspx_page_context);
        _jspx_th_html_005fhtml_005f0.setParent(null);
        int _jspx_eval_html_005fhtml_005f0 = _jspx_th_html_005fhtml_005f0.doStartTag();
        if (_jspx_eval_html_005fhtml_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\r\n<link href=\"/jsoa/skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<title>设置节点信息</title>\r\n\r\n<script language=\"JavaScript\" src=\"/jsoa/js/resource/");
            out.print(local);
            out.write("/CommonResource.js\" type=\"text/javascript\"></script>\r\n<script language=\"JavaScript\" src=\"js/date0.js\"></script>\r\n<script src=\"/jsoa/js/resource/");
            out.print(local);
            out.write("/PersonalworkResource.js\" language=\"javascript\"></script>\r\n<script src=\"js/Combox.js\"></script>\r\n</head>\r\n\r\n<body  class=\"MainFrameBox Pupwin\">\r\n<table height=\"100%\" width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td >\r\n\r\n<table height=\"100%\" width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"docBoxNoPanel\">\r\n <tr>\r\n    <td valign=\"top\" height=\"60%\">\r\n\t   <table  height=\"100%\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"5\">\r\n\t\t<form name=\"frm\" action=\"/jsoa/ActivityAction.do?action=updatefirstend\" method=\"post\">\r\n\t\t<input type=\"hidden\" name=\"processId\" value=\"");
            out.print(processId );
            out.write("\">\r\n\t\t<input type=\"hidden\" name=\"tableId\" value=\"");
            out.print(tableId );
            out.write("\">\r\n\t\t<input type=\"hidden\" name=\"moduleId\" value=\"");
            out.print(moduleId );
            out.write("\">\r\n\t\t<input type=\"hidden\" name=\"activityId\" value=\"");
            out.print(activityId );
            out.write("\">\r\n\t\t<input type=\"hidden\" name=\"nodeId\" value=\"");
            out.print(nodeId );
            out.write("\">\r\n\t\t<input type=\"hidden\" name=\"nowriteField\" value=\"\">\r\n\t\t<input type=\"hidden\" name=\"noDisplayField\" value=\"\">\r\n\t\t<tr>\r\n\t\t  <td width=\"12%\" nowrap>节点名称：</td>\r\n\t\t  <td colspan=2><input type=\"text\" name=\"activityName\" value=\"");
            out.print(activityName );
            out.write("\">         \r\n          </td>\r\n\t\t</tr>\r\n\t\t");

		int hei=fieldList.size()*26;
		if(hei>230){
			hei=230;
		}
		
            out.write("\r\n\t\t<tr>\r\n\t\t  <td valign=\"top\" style=\"padding-top:5px;\">字段权限：</td>\r\n\t\t  <td width=\"80%\" height=\"100%\">\r\n\t\t  <div style=\"width:90%;overflow:auto;padding:5px;border:1px;\">\r\n\t\t  \t\t<table height=\"100%\" border=0 width=\"100%\"  cellpadding=\"0\" cellspacing=\"0\">\r\n\t            \t<tr>\r\n\t\t            \t<td height=24 width=\"30%\" nowrap>&nbsp;</td>\r\n\t\t            \t<td width=\"15%\"><a href=\"javascript:checkAll(0);\">只读全选</a></td>\r\n\t\t            \t<td width=\"15%\"><a href=\"javascript:checkAll(1);\">可写全选</a></td>\r\n\t\t            \t<td width=\"15%\"><a href=\"javascript:checkAll(2);\">隐藏全选</a></td>\r\n\t            \t</tr>\r\n            \t</table>\r\n            </div>\r\n            <div style=\"height:");
            out.print(hei );
            out.write("px;width:90%;overflow:auto;padding:5px;border:1px;\">\r\n            \t<table height=\"100%\" border=0 width=\"100%\"  cellpadding=\"0\" cellspacing=\"0\">\r\n            \t");

            	String noWriteString=",";
            	String noDisplayString=",";
            	for(int i = 0; i < noWriteList.size(); i ++){
                    noWriteString += noWriteList.get(i) + ",";
                }
            	for(int i = 0; i < noDisplayList.size(); i ++){
            		noDisplayString += noDisplayList.get(i) + ",";
                }
            	String fieldNoWrite,fieldNoDisplay;
            	for(int i = 0; i < fieldList.size(); i ++){
                      fieldVO = (SimpleFieldVO) fieldList.get(i);
                      if(noWriteString.indexOf(","+fieldVO.getId()+",")>=0){
                    	  fieldNoWrite="checked";
                      }else{
                    	  fieldNoWrite="";
                      }
                      
                      if(noDisplayString.indexOf(","+fieldVO.getId()+",")>=0){
                    	  fieldNoDisplay="checked";
                      }else{
                    	  fieldNoDisplay="";
                      }
            		
            out.write("\r\n            \t\t<tr>\r\n\t            \t\t<td height=24 width=\"30%\" nowrap>");
            out.print(fieldVO.getDisplayName());
            out.write("</td>\r\n\t            \t\t<td width=\"15%\">&nbsp;<input type=\"radio\" name=\"rowRadio_");
            out.print(i);
            out.write("\" value=\"");
            out.print(fieldVO.getId());
            out.write('"');
            out.write(' ');
            out.print(fieldNoWrite);
            out.write(">只读</td>\r\n\t            \t\t<td width=\"15%\"><input type=\"radio\" name=\"rowRadio_");
            out.print(i);
            out.write("\" value=\"");
            out.print(fieldVO.getId());
            out.write('"');
            out.write(' ');
if("".equals(fieldNoWrite)){out.print("checked");} 
            out.write(">可写</td>\r\n\t            \t\t<td width=\"15%\"><input type=\"radio\" name=\"rowRadio_");
            out.print(i);
            out.write("\" value=\"");
            out.print(fieldVO.getId());
            out.write('"');
            out.write(' ');
if(!"".equals(fieldNoDisplay)){out.print("checked");} 
            out.write(">隐藏</td>\r\n            \t\t</tr>\r\n            \t");
}
            out.write("\r\n            \t</table>\r\n              </div>\r\n\t\t  </td>\r\n\t\t</tr>\t\r\n\t\t<tr><td><button class=\"btnButton2font\" onclick=\"submitForm();\">确定</button>\t</td></tr>\t\t\r\n\t\t</form>\r\n\t</table>\r\n\t</td>\r\n</tr>\r\n<tr><td></td></tr>\r\n</table>\r\n\r\n</td></tr>\r\n<tr><td>&nbsp;\t</td></tr>\r\n</table>\r\n</body>\r\n");
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
      out.write("\r\n<script>\r\nfunction submitForm(){   \r\n    if(document.all.activityName.value==''){\r\n       alert(\"请填写节点名称！\");\r\n       document.all.activityName.focus();\r\n       return false;\r\n    }\r\n      \r\n    //构造只读的字段信息\r\n\tvar i=0;\r\n\tvar rwObj=document.getElementsByName(\"rowRadio_\"+i);\r\n\tvar readFieldStr=\"\",noDisplayFieldStr=\"\";\r\n\twhile(rwObj.length>0){\r\n\t\tif(rwObj[0].checked){\r\n\t\t\t//读字段\r\n\t\t\tif(readFieldStr==\"\"){\r\n\t\t\t\treadFieldStr=rwObj[0].value;\r\n\t\t\t}else{\r\n\t\t\t\treadFieldStr+=\",\"+rwObj[0].value;\r\n\t\t\t}\r\n\t\t}else if(rwObj[2].checked){\r\n\t\t\tif(noDisplayFieldStr==\"\"){\r\n\t\t\t\tnoDisplayFieldStr=rwObj[2].value;\r\n\t\t\t}else{\r\n\t\t\t\tnoDisplayFieldStr+=\",\"+rwObj[2].value;\r\n\t\t\t}\r\n\t\t}\r\n\t\ti++;\r\n\t\trwObj=document.getElementsByName(\"rowRadio_\"+i);\r\n\t}\t\r\n\tdocument.all.nowriteField.value=readFieldStr;\r\n\tdocument.all.noDisplayField.value=noDisplayFieldStr;\r\n   \r\n    document.all.frm.submit();\r\n}\r\nfunction checkAll(flag){\r\n\tvar l = ");
      out.print(fieldList.size() );
      out.write(";\r\n\tfor(var i=0;i<l;i++){\r\n\t\tvar obj = document.getElementsByName(\"rowRadio_\"+i);\r\n\t\tobj[flag].checked=true;\r\n\t}\r\n}\r\n</script>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
