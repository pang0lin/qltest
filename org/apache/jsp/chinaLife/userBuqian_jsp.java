/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:42:22 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.chinaLife;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.text.SimpleDateFormat;
import java.util.*;

public final class userBuqian_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.text.SimpleDateFormat");
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
      response.setContentType("text/html;charset=GBK");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

List<String[]> buqianData = (List<String[]>)request.getAttribute("dataList");
String orgId = request.getAttribute("orgId")==null?"":request.getAttribute("orgId")+"";
String orgName = request.getAttribute("orgName")==null?"":request.getAttribute("orgName")+"";
SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
String startDate = request.getAttribute("startDate")==null||"".equals(request.getAttribute("startDate")+"")?ymd.format(new Date()):request.getAttribute("startDate")+"";
String endDate = request.getAttribute("endDate")==null||"".equals(request.getAttribute("endDate")+"")?ymd.format(new Date()):request.getAttribute("endDate")+"";

      out.write("\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<html>\r\n\t<head>\r\n\t\t<title>员工补签统计</title>\r\n\t\t<link href=\"/jsoa/skin/blue/style.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t\t<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject2.css\">\r\n\t\t<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DateObject.js\"></SCRIPT>\r\n\t\t<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DatePicker.js\"></SCRIPT>\r\n\t\t<SCRIPT language=javascript src=\"/jsoa/public/date_picker/editlib.js\"></SCRIPT>\r\n\t</head>\r\n\t  \r\n\t<body bgcolor=\"#ffffff\" class=\"docBoxNoPanel\">\r\n\t\t<div id=\"Layer1\" >\r\n\t\t\t<div height=\"30px;\" style=\"padding: 10px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\r\n\t\t\t<input type=\"button\" class=\"btnButton4font\" value=\"公司员工考勤补签单情况\" title=\"查看所有员工提交补单信息\" onclick=\"goToMore();\" />\r\n\t\t\t<input type=\"button\" class=\"btnButton2font\" value=\"考勤补签单审批\" title=\"补签流程当前处理人为当前登录人\" onclick=\"goToShenPi();\" />\r\n\t\t\t<input type=\"button\" class=\"btnButton2font\" value=\"个人界面\" onclick=\"selfPage();\" />\r\n\t\t\t</div>\r\n\t\t\t<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\r\n");
      out.write("\t\t\t查询部门：<input type=\"text\" name=\"orgName\" value=\"");
      out.print(orgName );
      out.write("\" onclick=\"selectOrg();\" readonly=\"readonly\" \r\n\t\t\t\t\tstyle=\"width:200px\" class=\"inputText\" id=\"orgName\" title=\"请点击选择\">\r\n            <input type=\"hidden\" name=\"orgId\" value=\"");
      out.print(orgId );
      out.write("\" id=\"orgId\">\r\n            查询日期：\r\n            <script language=\"javascript\">createDatePicker(\"startDate\",");
      out.print(startDate.replace("-", ",") );
      out.write(");</script>&nbsp;至&nbsp;\r\n            <script language=\"javascript\">createDatePicker(\"endDate\",");
      out.print(endDate.replace("-", ",") );
      out.write(");</script>\r\n            <input type=\"checkbox\" name=\"dateSearch\" id=\"dateSearch\" />\r\n            <input type=\"button\" class=\"btnButton2font\" value=\"查询\" onclick=\"exportExcel(0);\" /></div>\r\n\t\t\t<div><table width=\"90%\" align=\"center\" border=\"1\" cellpadding=\"5\" cellspacing=\"0\" >\r\n\t\t\t\t<tr height=\"20px\">\r\n\t\t\t\t\t<td width=\"25%\" align=\"center\"><b>部门</b></td>\r\n\t\t\t\t\t<td width=\"15%\" align=\"center\"><b>补签单号</b></td>\r\n\t\t\t\t\t<td align=\"center\" width=\"15%\"><b>补签申请人</b></td>\r\n\t\t\t\t\t<td align=\"center\"><b>补登人</b></td>\r\n\t\t\t\t\t<td align=\"center\"><b>职位</b></td>\r\n\t\t\t\t\t<td align=\"center\"><b>提交日期</b></td>\r\n\t\t\t\t\t<td align=\"center\"><b>补签状态</b></td></tr>\r\n\t\t\t\t");
for(int i=0;i<buqianData.size();i++){
					String[] buqian = buqianData.get(i); 
      out.write("\r\n\t\t\t\t<tr>\r\n\t\t\t\t\t<td align=\"center\">");
      out.print(buqian[0].substring(buqian[0].indexOf(".")+1) );
      out.write("&nbsp;</td>\r\n\t\t\t\t\t<td align=\"center\">");
      out.print(buqian[1] );
      out.write("&nbsp;</td>\r\n\t\t\t\t\t<td align=\"center\">");
      out.print(buqian[3] );
      out.write("&nbsp;</td>\r\n\t\t\t\t\t<td align=\"center\">");
      out.print(buqian[8].equals(buqian[9])?"无":buqian[10] );
      out.write("&nbsp;</td>\t\r\n\t\t\t\t\t<td align=\"center\">");
      out.print(buqian[4] );
      out.write("&nbsp;</td>\r\n\t\t\t\t\t<td align=\"center\">");
      out.print(buqian[11] );
      out.write("&nbsp;</td>\r\n\t\t\t\t\t<td align=\"center\"><a title=\"点击查看详情\" href=\"javaScript:visitFlow('");
      out.print(buqian[5] );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(buqian[6] );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(buqian[7] );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(buqian[8] );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(buqian[8].equals(buqian[9])?"无":buqian[10] );
      out.write("');\">\r\n\t\t\t\t\t");
      out.print((buqian[5].equals("1")||("0".equals(buqian[5])))?"审批中":("100".equals(buqian[5])||(!buqian[8].equals(buqian[9]))?"已审批":"未提交") );
      out.write("</a>&nbsp;</td>\t\t\r\n\t\t\t\t</tr>");
} 
      out.write("\r\n\t\t\t</table>\r\n\t\t\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/public/page/pageUtil.jsp", out, false);
      out.write("\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t<script type=\"text/javascript\">\r\n\t\tfunction visitFlow(status,workId,recordId,draftId,empName){\r\n\t\t\tif(empName!=\"无\"){\r\n\t\t\t\twindow.open(\"/jsoa/EFormPageAction.do?action=update&flag=1&formId=113004&recordId=\"+recordId+\"&menuId=226746&pager.offset=0\");\r\n\t\t\t}else{\r\n\t\t\t\tif((status==\"\"||workId==\"\")&&draftId!=\"\"){\r\n\t\t\t\t\twindow.open(\"/jsoa/JsFlowAddAction.do?action=draftForm&wfWorkId=\"+draftId+\"&processId=126586&tableId=112959&record=\"+recordId+\"&processName=考勤补签单&processType=1&remindField=null&moduleId=1&formType=0&jspFile=null&workStatus=-2\");\r\n\t\t\t\t}else if(status!=\"\"&&workId!=\"\"){\r\n\t\t\t\t\twindow.open(\"/jsoa/jsflow/item/jump_dealwith.jsp?status=\"+status+\"&workId=\"+workId);\r\n\t\t\t\t}else{\r\n\t\t\t\t\talert(\"扩展接口未关联流程新增数据！\");\r\n\t\t\t\t}\r\n\t\t\t}\t\t\t\r\n\t\t}\r\n\t\tfunction goToMore(){\r\n\t\t\tlocation.href=\"/jsoa/kqShow.do?action=buqian&type=1\";\r\n\t\t}\r\n\t\tfunction goToShenPi(){\r\n\t\t\tlocation.href=\"/jsoa/kqShow.do?action=buqian&type=2&userId=");
      out.print(session.getAttribute("userId") );
      out.write("\";\r\n\t\t}\r\n\t\tfunction selfPage(){\r\n\t\t\tlocation.href=\"/jsoa/kqShow.do?action=buqian&userId=");
      out.print(session.getAttribute("userId") );
      out.write("\";\r\n\t\t}\r\n\t\tfunction selectOrg(){\r\n\t\t\topenEndow('orgId','orgName',document.getElementById(\"orgId\").value,document.getElementById(\"orgName\").value,'org','no','org','*0*');\r\n\t\t}\r\n\t\tfunction exportExcel(flag){\r\n\t\t\tvar orgId = document.getElementById(\"orgId\").value;\r\n\t\t\tvar orgName = document.getElementById(\"orgName\").value;\r\n\t\t\tvar startDate = \"\";\r\n\t\t\tvar endDate = \"\";\r\n\t\t\tif(document.getElementById(\"dateSearch\").checked){\r\n\t\t\t\tstartDate = document.getElementById(\"startDate\").value;\r\n\t\t\t\tendDate = document.getElementById(\"endDate\").value;\r\n\t\t\t}\r\n\t\t\t\r\n\t\t\tlocation.href=\"/jsoa/kqShow.do?action=buqian&orgId=\"+orgId+\"&orgName=\"+orgName+\"&startDate=\"+startDate+\"&endDate=\"+endDate\r\n\t\t\t\t\t+\"");
      out.print(request.getParameter("type")==null?"&type=":"&type="+request.getParameter("type") );
      out.write("\";\r\n\t\t}\r\n\t\t</script>\r\n\t</body>\r\n</html>\r\n<script src=\"/jsoa/js/openEndow.js\"></script>");
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
