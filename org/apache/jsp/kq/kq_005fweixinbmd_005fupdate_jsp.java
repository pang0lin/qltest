/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:43:21 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.kq;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import com.js.oa.hr.kq.po.*;

public final class kq_005fweixinbmd_005fupdate_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("com.js.oa.hr.kq.po");
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

      out.write("\r\n\r\n\r\n\r\n<html>\r\n<head>\r\n<title>修改微信白名单</title>\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"js/js.js\" language=\"javascript\"></script>\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject2.css\">\r\n<script language=\"javascript\" src=\"/jsoa/js/checkForm.js\"></script>\r\n<script language=\"javascript\" src=\"/jsoa/js/openEndow.js\"></script>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DateObject.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DatePicker.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/tree.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/editlib.js\"></SCRIPT>\r\n<script src=\"/jsoa/js/toolbar/standardAdd.js\" language=\"javascript\"></script>\r\n<script src=\"/jsoa/js/toolbar/boardroomAdd.js\" language=\"javascript\"></script>\r\n<script src=\"/jsoa/js/clsPullXMenu.js\" language=\"javascript\"></script>\r\n<script src=\"/jsoa/public/jsp/cmdbutton.jsp?button=Close,Send,Print\"></script>\r\n");

	response.setHeader("Cache-Control","no-store");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader ("Expires", 0);
    KqWeiXinBMDPO kqWeiXinBMDPO=(KqWeiXinBMDPO)request.getAttribute("kqWeiXinBMDPO");
    Calendar calendar=Calendar.getInstance();
    String empName = request.getAttribute("empName")+"";
    

      out.write("\r\n\r\n</head>\r\n<body class=\"MainFrameBox Pupwin\"  >\r\n<form action=\"/jsoa/KqWeiXinBMDAction.do?action=save\"  name=\"form1\" method=\"post\" >\r\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"docBoxNoPanel\" style=\"padding-left:10px; padding-top:10px;\">\r\n     <tr>\r\n\t   <td width=\"80\" ><label class=\"mustFillcolor\" style=\"width:7px;\" > *</label>姓名：</td>\r\n\t   <td colspan=\"3\">\r\n\t       <input type=\"hidden\" name=\"bmdid\" value=\"");
      out.print(kqWeiXinBMDPO.getId());
      out.write("\"/>\r\n\t       <input type=\"text\" name=\"userName\" id=\"userName\" value=\"");
      out.print(empName );
      out.write("\"  title=\"请点击选择\" onclick=\"selectEmp();\" readonly=\"readonly\" class=\"inputText\">\r\n\t\t   <input type=\"hidden\" value=\"");
      out.print(kqWeiXinBMDPO.getEmp_id() );
      out.write("\" name=\"userId\" id=\"userId\" >\r\n\t   </td>\r\n    </tr>\r\n                              <tr>\r\n                              <td height=\"32\" nowrap><label class=\"mustFillcolor\" style=\"width:7px;\" > *</label>开始时间：</td>\r\n                              <td >\r\n                              ");
 
                              calendar.setTime(kqWeiXinBMDPO.getBeginTime());
                              
      out.write("\r\n                              <script language=javascript>\r\n\t\t\t\t\t\t\t\t\tvar dtpDate = createDatePicker(\"beginTime\",\"");
      out.print(calendar.get(Calendar.YEAR));
      out.write('"');
      out.write(',');
      out.write('"');
      out.print(calendar.get(Calendar.MONTH)+1);
      out.write('"');
      out.write(',');
      out.write('"');
      out.print(calendar.get(Calendar.DAY_OF_MONTH));
      out.write("\" );\r\n\t\t\t\t\t\t\t\t</script></td>\r\n                              <td width=\"80\" nowrap>结束时间：</td>\r\n                              ");
  calendar.setTime(kqWeiXinBMDPO.getEndTime());
                              
      out.write("\r\n\t\t\t\t\t\t\t  <td><script language=javascript>\t\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\tvar dtpDate = createDatePicker(\"endTime\",\"");
      out.print(calendar.get(Calendar.YEAR));
      out.write('"');
      out.write(',');
      out.write('"');
      out.print(calendar.get(Calendar.MONTH)+1);
      out.write('"');
      out.write(',');
      out.write('"');
      out.print(calendar.get(Calendar.DAY_OF_MONTH));
      out.write("\" );\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t</script></td>\r\n                            </tr>\r\n  \r\n</table>\r\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n<tr><td>&nbsp;</td></tr>\r\n<tr>\r\n   <td colspan=\"2\">\r\n\t<button class=\"btnButton4font\" style=\"cursor:pointer\" onclick=\"javascript:save();\">保存</button>\r\n\t<button class=\"btnButton2font\" style=\"cursor:pointer\" onclick=\"javascript:window.close();\">退出</button>\r\n  </td>\r\n</tr>\r\n</table>\r\n</form>\r\n</body>\r\n\r\n\r\n<script language=\"javascript\">\r\n\r\nfunction save(){\r\n  if(document.getElementById(\"userId\").value==\"\"){\r\n              alert(\"人员不能为空！\");\r\n               return false;\r\n    }\r\n   var btime=new Date(document.all.beginTime.value);\r\n  var etime=new Date(document.all.endTime.value);\r\n  if(btime>=etime)\r\n  {\r\n  alert(\"结束时间必须大于开始时间！\");\r\n   return false;\r\n  }\r\n  \r\n  \r\n form1.action=\"KqWeiXinBMDAction.do?action=updatesave\";\r\n    document.form1.submit();\r\n}\r\nfunction selectEmp(){\r\n\topenEndow('userId','userName',document.getElementById(\"userId\").value,document.getElementById(\"userName\").value,'user','yes','user','*0*');\r\n");
      out.write("}\r\n</script>\r\n</html>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
