/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:42:20 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.workplan;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class proxyset_005fadd_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n<html>\r\n<head>\r\n<title>新建代理</title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gbk\">\r\n<link href=\"/jsoa/style/cssmain.css\" rel=\"stylesheet\" type=\"text/css\">\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject2.css\">\r\n<script language=\"JavaScript\" src=\"/jsoa/js/resource/zh_cn/CommonResource.js\" type=\"text/javascript\"></script>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DateObject.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DatePicker.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/editlib.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/tree.js\"></SCRIPT>\r\n<link href=\"skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n<script src=\"/jsoa/js/resource/zh_cn/PersonalworkResource.js\" language=\"javascript\"></script>\r\n\r\n</head>\r\n<body onload=\"init();resizeWin(600,350)\"  class=\"MainFrameBox Pupwin\">\r\n\r\n<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"docBoxNoPanel\">\r\n<tr>\r\n    <td valign=\"top\">\r\n\t<div id=\"docinfo0\" style=\"display:;\">\r\n\t   <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"1\">\r\n<form name=\"WorkProxyActionForm\"  action=\"/jsoa/WorkplanSetAction.do?action=saveproxy\" method=\"post\">\r\n\r\n\t\t\t\t  <input type=\"hidden\" name=\"con\" value=\" \">\r\n\t\t\t\t  <input type=\"hidden\" name=\"done\" value=\" \">\r\n\t\t\t\t  <td width=\"80\" nowrap>授权至&nbsp;<label class=\"MustFillColor\">*</label>：</td>\r\n\t\t\t\t  <td>\r\n\t\t\t\t\t  <input type=\"hidden\" name=\"proxyId\" value=\"\">\r\n\t\t\t\t\t  <input type=\"text\"  title=\"请点击选择\"style=\"cursor:pointer\"class=\"inputText\" name=\"proxyName\"  readonly=\"readonly\"  onClick=\"openEndow('proxyId','proxyName',WorkProxyActionForm.proxyId.value,WorkProxyActionForm.proxyName.value,'user','yes','user','*0*')\"/>\r\n");
      out.write("\t\t\t\t  </td>\r\n\t\t\t\t</tr>\r\n\r\n\t\t\t<tr>\r\n\t\t\t  <td height=\"30\" valign=\"bottom\">\r\n\t\t\t    <input type=\"button\" class=\"btnButton4font\" style=\"cursor:pointer\" onclick=\"saveClose();\" value=\"保存退出\"/>\t\r\n\t\t\t\t<button class=\"btnButton2font\" style=\"cursor:pointer\" onClick=\"javascript:window.close();\">退出</button>\r\n\t\t\t  </td>\r\n\t\t\t</tr>\r\n\t\t\t</table>\r\n\t\t\t\t</td>\r\n\t\t\t  </tr>\r\n\t\t\t</table>\r\n\r\n\t\t</td>\r\n\t\t</tr>\r\n</table>\r\n\r\n</body>\r\n</html>\r\n<script language=\"javascript\">\r\n//检查日期\r\nfunction checkDate() {\r\n\tvar beginDate=new Date(WorkProxyActionForm.beginTime.value);\r\n    var endDate=new Date(WorkProxyActionForm.endTime.value);\r\n    if(beginDate>endDate){\r\n        alert(Personalwork.agent_datecheck);\r\n        return false;\r\n    }\r\n    return true ;\r\n}\r\n\r\n\r\n//保存退出\r\nfunction saveClose(){\r\n\tdocument.WorkProxyActionForm.submit();\r\n}\r\n\r\n\r\nfunction checkSelect(){\r\n  if(document.all.proxyOrgId.value==''){\r\n      document.all.proxyOrgName.value='';\r\n  }if(document.all.empId.value==''){\r\n      document.all.empName.value=''; \r\n  }if(document.all.proxyEmpId.value==''){\r\n");
      out.write("      document.all.proxyEmpName.value='';\r\n  }\r\n}\r\n\r\nfunction checkStartTimeAndEndTime(){\r\n\tvar bH=document.all.beginHour.value;\r\n\tvar bM=document.all.beginMinutes.value;\r\n\tvar eH=document.all.endHour.value;\r\n\tvar eM=document.all.endMinutes.value;\r\n\r\n\tvar beginDate=WorkProxyActionForm.beginTime.value;\r\n    var endDate=WorkProxyActionForm.endTime.value;\r\n    if(beginDate==endDate){\r\n\t\tif(eH<bH){\r\n\t\treturn false;\r\n\t\t}\r\n\t\tif(eH==bH&&eM<bM){\r\n\t\t\treturn false;\r\n\t\t}\r\n    }\r\n\treturn true;\r\n}\r\n\r\n\r\nfunction showProcess(type){\r\n\tif(type=='1'){\r\n\t\tresizeWin(600,320);\r\n\t\tdocument.all.processTR.style.display=\"none\";\r\n\t} else if(type=='0'){\r\n\t\tresizeWin(screen.width, screen.height);\r\n\t\tdocument.all.processTR.style.display=\"\";\r\n\t}\r\n}\r\n\r\nfunction selPackage(packageId,obj){\r\n\tvar processCheck = eval(\"document.all.package\"+packageId);\r\n\tif(obj.checked) {\r\n\t\tif(processCheck.length){\r\n\t\t\tfor(var i = 0 ; i < processCheck.length ; i++ ){\r\n\t\t\t\tprocessCheck[i].checked = true ;\r\n\t\t\t}\r\n\t\t} else if(processCheck){\r\n\t\t\t\tprocessCheck.checked = true ;\r\n");
      out.write("\t\t}\r\n\t} else{\r\n\t\tif(processCheck.length){\r\n\t\t\tfor(var i = 0 ; i < processCheck.length ; i++ ){\r\n\t\t\t\tprocessCheck[i].checked = false ;\r\n\t\t\t}\r\n\t\t} else if(processCheck){\r\n\t\t\t\tprocessCheck.checked = false ;\r\n\t\t}\r\n\t}\r\n}\r\n\r\nfunction getCheckBoxID() {\r\n    var retString=\"\" ;\r\n    for(var i = 0 ; i < WorkProxyActionForm.length ; i++ ) {\r\n        var obj = WorkProxyActionForm[i] ;\r\n         if(obj.type == \"checkbox\" && obj.name != \"upPackage\"){\r\n\t\t\tif(obj.checked) {\r\n\t\t\t\tretString += \"$\"+obj.value+\"$\"  ;\r\n\t\t\t\t//retString += \",\" ;\r\n\t\t\t}\r\n         }\r\n    }\r\n    return retString ;\r\n}\r\n\r\nfunction resetForm(){\r\n\tWorkProxyActionForm.reset();\r\n\tshowProcess('1');\r\n}\r\n</script>\r\n<script  src=\"/jsoa/js/checkText.js\"  language=\"javascript\" ></script>\r\n<SCRIPT language=\"javascript\" src=\"/jsoa/js/openEndow.js\"></SCRIPT>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
