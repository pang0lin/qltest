/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:05:19 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.doc.doc;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class sendocument_005fbottom_005ftosendfilecheck_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

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
    _jspx_imports_classes = null;
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
      response.setContentType("text/html; charset=GBK");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\r');
      out.write('\n');


  request.setCharacterEncoding("GBK");


  String fileTitle =request.getParameter("filetitle")==null?"":request.getParameter("fileTitle").toString();
  String accessory1=request.getParameter("accessory1")==null?"":request.getParameter("accessory1").toString();
  String accessorySaveName1=request.getParameter("accessorySaveName1")==null?"":request.getParameter("accessorySaveName1").toString();




  String accessoryName2=request.getParameter("accessoryName2")==null?"":request.getParameter("accessoryName2").toString();
  String accessorySaveName2=request.getParameter("accessorySaveName2")==null?"":request.getParameter("accessorySaveName2").toString();



	String accessoryName="";
	String  accessorySaveName="";

	accessoryName=accessory1;
	accessorySaveName=accessorySaveName1;

	if(!accessoryName.equals("")){
	accessoryName+="|"+accessoryName2;
	accessorySaveName+="|"+accessorySaveName2;
	}else{
	  
    if(!accessorySaveName2.equals("")){  
	accessoryName+=accessoryName2;
	accessorySaveName+=accessorySaveName2;
	}

	
	}

	String id=request.getParameter("id")==null?"":request.getParameter("id").toString();
	String sendType=request.getParameter("sendType")==null?"":request.getParameter("sendType").toString();


    String  slink="/jsoa/GovReceiveFileAction.do?action=listLoad&editId="+id+"&viewOnly=1";//收文时 的链接

    if(sendType.equals("sendFile")){// 发文时的连接
    slink="/jsoa/GovSendFileAction.do?action=listLoad&editId="+id+"&viewOnly=1&editType=0&canEdit=0";//收文时 的链接
	}

      out.write("\r\n\r\n<head>\r\n<title>转发文</title>\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n<SCRIPT language=\"javascript\" src=\"/jsoa/js/openEndow.js\"></SCRIPT>\r\n<script>\r\nfunction addFile(){\r\n  document.getElementById(\"uploadFrame\").style.display=\"inline\";\r\n}\r\n</script>\r\n</head>\r\n\r\n<body scroll=\"no\">\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td>\r\n<form name=\"webform\" method=\"post\" action=\"/jsoa/GovSendFileCheckWithWorkFlowAction.do?flag=add&fromReceiveFile=1\" >\r\n<input type=\"hidden\" name=\"receiveFileTitle\" value=\"");
      out.print(fileTitle);
      out.write("\">\r\n<input type=\"hidden\" name=\"processId\">\r\n<input type=\"hidden\" name=\"processName\">\r\n<input type=\"hidden\" name=\"processType\">\r\n<input type=\"hidden\" name=\"tableId\">\r\n<input type=\"hidden\" name=\"remindField\">\r\n<input type=\"hidden\" name=\"editId\" value=\"");
      out.print(id);
      out.write("\">\r\n<input type=\"hidden\" name=\"accessory1\"  value=\"");
      out.print(accessory1);
      out.write("\">\r\n<input type=\"hidden\" name=\"accessorySaveName1\"  value=\"");
      out.print(accessorySaveName1);
      out.write("\">\r\n<input type=\"hidden\" name=\"accessoryName2\"  value=\"");
      out.print(accessoryName2);
      out.write("\">\r\n<input type=\"hidden\" name=\"accessorySaveName2\"  value=\"");
      out.print(accessorySaveName2);
      out.write("\">\r\n<input type=\"hidden\" name=\"accessoryName\" value=\"");
      out.print(accessoryName);
      out.write("\">\r\n<input type=\"hidden\" name=\"accessorySaveName\"  value=\"");
      out.print(accessorySaveName);
      out.write("\"> \r\n<input type=\"hidden\" name=\"receiveFileId\" value=\"");
      out.print(id);
      out.write("\">\r\n<input type=\"hidden\" name=\"fromReceiveFileId\" value=\"");
      out.print(id);
      out.write("\">\r\n<input type=\"hidden\" name=\"recordId\" value=\"");
      out.print(id);
      out.write("\">\r\n<input type=\"hidden\" name=\"fromReceiveFileLink\" value=\"");
      out.print(slink);
      out.write("\">\r\n\r\n\r\n");
//=request.getAttribute("javax.servlet.forward.query_string")
      out.write('\r');
      out.write('\n');
java.util.List receivefilelist = new com.js.oa.jsflow.service.ProcessBD().getUserProcess(session.getAttribute("userId").toString(), session.getAttribute("orgIdString").toString(), "34");
      out.write("\r\n <script language=\"javascript\">\r\n var receiveFileProcArr = new Array(");
      out.print(receivefilelist.size());
      out.write(");\r\n </script>\r\n\r\n<table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" id=\"pup_main\">\r\n\r\n  <tr>\r\n    <td id=\"pup_top\"><table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n      <tr>\r\n        <td><div id=\"pup_topright\"></div></td>\r\n        <td id=\"pup_topleft\">&nbsp;</td>\r\n      </tr>\r\n    </table></td>\r\n  </tr>  \r\n  <tr>\r\n    <td align=\"center\" valign=\"top\">\r\n\t\t<div id=\"pup_content\">\r\n\t\t  <table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n\t\t    <tr>            \r\n              <td valign=\"top\" align=\"left\" height=\"25\"><label class=\"mustFillColor\">&nbsp;&nbsp;请选择文件送审签流程：</label></td>\r\n            </tr>\r\n            \r\n            <tr>\r\n              <td valign=\"top\" align=\"center\" height=\"25\">\r\n\t\t\t  <select name=\"docReceiveFileProc\" onchange=\"chReceiveFileProc(this);\" >\r\n<option value=\"0\">---------请选择------</option>\r\n");

Object[] rfObj = null;
String receiveFileProcessId, receiveFileProcessName, receiveFileProcessType, receiveFileTableId, receiveFileRemindField;
for (int i = 0 ; i < receivefilelist.size() ; i++) {
 rfObj = (Object[])receivefilelist.get(i) ;
 receiveFileProcessId = rfObj[2] + "" ;
receiveFileProcessName = rfObj[3] + "";
receiveFileProcessType = rfObj[5] + "";
receiveFileTableId = rfObj[4] + "";
receiveFileRemindField = rfObj[6]==null?"":rfObj[6] + "";

      out.write("\r\n<script language=\"javascript\">\r\nreceiveFileProcArr[");
      out.print(i);
      out.write("] = new Array(\"");
      out.print(receiveFileProcessId);
      out.write("\", \"");
      out.print(receiveFileProcessName);
      out.write("\", \"");
      out.print(receiveFileProcessType);
      out.write("\", \"");
      out.print(receiveFileTableId);
      out.write("\", \"");
      out.print(receiveFileRemindField);
      out.write("\");\r\n</script>\r\n<option value=\"");
      out.print(receiveFileProcessId);
      out.write('"');
      out.write('>');
      out.print(receiveFileProcessName);
      out.write("</option>\r\n ");
}
      out.write("\r\n</select> \r\n\r\n</td>\r\n            </tr>\r\n\t\t\t<tr><td>&nbsp;</td></tr>\r\n         \r\n          </table>\r\n\t\t</div>\r\n\t</td>\r\n  </tr>\r\n  <tr>\r\n    <td valign=\"middle\" id=\"pup_bottom\"><label>\r\n     <input type=\"button\" name=\"Cancel\" value=\"新建\" class=\"btnButton2font\" onclick=\"newDocReceiveFile();\" />  <input type=\"button\" name=\"Cancel\" value=\"退出\" class=\"btnButton2font\" onclick=\"window.close();\" />\r\n    </label></td>\r\n  </tr>\r\n</table>\r\n</td>\r\n</tr>\r\n</table>\r\n</body>\r\n</html>\r\n\r\n<script>\r\n\r\nfunction newDocReceiveFile(){\r\n\r\nif(document.all.docReceiveFileProc.value == 0){\r\nalert(\"请选文件送审签流程！\");\r\n}else{\r\n\r\n\r\ndocument.webform.submit();\r\nwindow.opener.close();\r\n}\r\n}\r\n\r\nfunction chReceiveFileProc(obj){\r\nfor(var i = 0; i < receiveFileProcArr.length; i ++){\r\nif(receiveFileProcArr[i][0] == obj.value){\r\ndocument.webform.processId.value = receiveFileProcArr[i][0];\r\ndocument.webform.processName.value = receiveFileProcArr[i][1];\r\ndocument.webform.processType.value = receiveFileProcArr[i][2];\r\ndocument.webform.tableId.value = receiveFileProcArr[i][3];\r\n");
      out.write("document.webform.remindField.value = receiveFileProcArr[i][4];\r\nbreak;\r\n}\r\n}\r\n}\r\n\r\n</script>\r\n\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n\r\n\r\n\r\n\r\n\r\n//保存\r\n//$:userId\r\n//*:orgId\r\n//@:groupId\r\nfunction include_save(){\r\nwindow.close();\r\n}\r\n\r\n//-->\r\n</SCRIPT>\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n");
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
