/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:51:59 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.personnel_005fmanager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.Calendar;

public final class employeeWorkModify_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(4);
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-logic.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/lib/jstl-1.2.jar", Long.valueOf(1499751388000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-html.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("jar:file:/Users/pang0lin/Downloads/ithink/webapps/jsoa/WEB-INF/lib/jstl-1.2.jar!/META-INF/c.tld", Long.valueOf(1153356282000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.util.Calendar");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;

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
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.release();
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
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

      out.write("\r\n\r\n\r\n\r\n    \r\n\r\n<html>\r\n<head>\r\n<title>修改工作经历</title>\r\n<link href=\"/jsoa/style/cssmain.css\" rel=\"stylesheet\" type=\"text/css\">\r\n<script language=\"javascript\" src=\"/jsoa/js/checkForm.js\"></script>\r\n<script language=\"javascript\" src=\"/jsoa/js/openEndow.js\"></script>\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject2.css\">\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DateObject.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DatePicker.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/editlib.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/tree.js\"></SCRIPT>\r\n<link href=\"skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"js/js.js\" language=\"javascript\"></script>\r\n\r\n<script language=\"javascript\">\r\nfunction changePanle(flag){\r\n    for(i=0;i<2;i++){\r\n\t  eval(\"docinfo\"+i+\".style.display='none'\");\r\n\t  eval(\"Panle\"+i+\".className='btnBQ AlignLeft'\");\r\n\t}\r\n\teval(\"docinfo\"+flag+\".style.display=''\");\r\n\teval(\"Panle\"+flag+\".className='btnBQselected AlignLeft'\");\r\n}\r\n</script>\r\n</head>\r\n");

String empID = request.getParameter("empID");
String close = request.getAttribute("close") == null?"":request.getAttribute("close").toString();
String canAdd = request.getParameter("canAdd")==null?"0":request.getParameter("canAdd");
if(close.equals("1")){
      out.write("\r\n<script language=\"javascript\">\r\n\r\ntry{\r\n opener.location.reload();\r\n\t//opener.location.href = \"/jsoa/EmployeeWorkAction.do?action=list&empID=");
      out.print(empID);
      out.write("&canAdd=");
      out.print(canAdd);
      out.write("\";\r\n}catch(e){}\r\nwindow.close();\r\n</script>\r\n");

}else{
    if(close.equals("0")){
      out.write("\r\n    <script language=\"javascript\">\r\n    <!--\r\n    try{\r\n     opener.location.reload();\r\n //opener.location.href = \"/jsoa/EmployeeWorkAction.do?action=list&empID=");
      out.print(empID);
      out.write("&canAdd=");
      out.print(canAdd);
      out.write("\";\r\n    }catch(e){}\r\n    //-->\r\n    </script>\r\n");

    }else if(close.equals("2")){
      out.write("\r\n    <script language=\"javascript\">\r\n    <!--\r\n    alert(\"更新失败！\");\r\n    //-->\r\n    </script>\r\n    ");
}
        java.util.Date newDate = new java.util.Date();
        String n_year = Integer.toString(newDate.getYear()+1900);
        String n_month = Integer.toString(newDate.getMonth()+1);
        String n_day = Integer.toString(newDate.getDate());
		  if (request.getAttribute("beginDate")!= null){
            String beginDate = request.getAttribute("beginDate").toString();
            n_year= beginDate.substring(0,4);
            n_month = beginDate.substring(5,7);
            n_day = beginDate.substring(8,10);
            }

	 java.util.Date eDate = new java.util.Date();
        String e_year = Integer.toString(eDate.getYear()+1900);
        String e_month = Integer.toString(eDate.getMonth()+1);
        String e_day = Integer.toString(eDate.getDate());
		  if (request.getAttribute("endDate")!= null){
            String endDate = request.getAttribute("endDate").toString();
           e_year= endDate.substring(0,4);
            e_month = endDate.substring(5,7);
           e_day = endDate.substring(8,10);
            }
	String edustyId = request.getParameter("workId");

      out.write("\r\n\r\n<!--stationAdd.jsp-->\r\n<body leftmargin=\"0\" topmargin=\"0\"  class=\"MainFrameBox Pupwin\"\r\n      onload=\"resizeWin(450,300);setIfEndNow();\" >\r\n<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"docBoxNoPanel\">\r\n  <tr>\r\n    <td valign=\"top\">\r\n\t\t<div id=\"docinfo0\" style=\"display:;\">\r\n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"1\">\r\n\t\t\t\t");
      //  html:form
      org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag) _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(org.apache.struts.taglib.html.FormTag.class);
      boolean _jspx_th_html_005fform_005f0_reused = false;
      try {
        _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
        _jspx_th_html_005fform_005f0.setParent(null);
        // /personnel_manager/employeeWorkModify.jsp(96,4) name = action type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_html_005fform_005f0.setAction("/EmployeeWorkAction.do");
        // /personnel_manager/employeeWorkModify.jsp(96,4) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_html_005fform_005f0.setMethod("post");
        int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
        if (_jspx_eval_html_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n\t\t\t\t<tr>\r\n\t\t\t\t  <td width=\"100\"  nowrap>开始工作时间：</td>\r\n\t\t\t\t  <td width=\"80%\" nowrap>\r\n\t\t\t\t     <script language=javascript>\r\n\t\t\t\t\t  var dtpDate = createDatePicker(\"beginDate\",");
            out.print(n_year);
            out.write(',');
            out.print(n_month);
            out.write(',');
            out.print(n_day);
            out.write(");\r\n\t\t\t\t\t  </script></td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t <tr>\r\n\t\t\t\t  <td width=\"100\" nowrap>结束工作时间：</td>\r\n\t\t\t\t  <td width=\"80%\" nowrap>\r\n\t\t\t\t  \t  <input type=\"radio\" name=\"ifEndNow\" value=\"1\" onclick=\"changeEndDate(1)\"  ");
if("1".equals(request.getAttribute("ifEndNow").toString())) {
            out.write(" checked ");
}
            out.write(" 至今\r\n\t\t\t\t\t  <input type=\"radio\" name=\"ifEndNow\" value=\"0\" onclick=\"changeEndDate(0)\"  ");
if("0".equals(request.getAttribute("ifEndNow").toString())) {
            out.write(" checked ");
}
            out.write(" 具体时间\r\n\t\t\t\t\t  <div style=\"display:none\"  id=\"endDateDiv\">\r\n\t\t\t\t\t   <script language=javascript>\r\n\t\t\t\t\t   var dtpDate = createDatePicker(\"endDate\",");
            out.print(e_year);
            out.write(',');
            out.print(e_month);
            out.write(',');
            out.print(e_day);
            out.write(");\r\n\t\t\t\t\t   </script>\r\n\t\t\t\t\t  </div>\r\n\t\t\t\t  \t  </td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n\t\t\t\t  <td height=\"25\" nowrap>工作单位&nbsp;<label class=\"mustFillcolor\">*</label>：</td>\r\n\t\t\t\t  <td  width=\"80%\" nowrap>");
            if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
              return;
            out.write("\r\n\t\t\t\t\t  </td>\r\n\t\t\t\t</tr>\r\n\t\t\t\t<tr>\r\n\t\t\t\t  <td height=\"25\" nowrap>担任职务：</td>\r\n\t\t\t\t  <td  width=\"80%\" nowrap>");
            if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
              return;
            out.write("\r\n\t\t\t\t\t  </td>\r\n\t\t\t\t</tr>\r\n\r\n\t\t\t\t<tr>\r\n\t\t\t\t  <td height=\"25\" nowrap>证明人：</td>\r\n\t\t\t\t  <td  width=\"80%\" nowrap>");
            if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
              return;
            out.write("\r\n\t\t\t\t\t  </td>\r\n\t\t\t\t</tr>\r\n\r\n\t\t\t\t<tr>\r\n\t\t\t\t  <td height=\"25\" nowrap>电话：</td>\r\n\t\t\t\t  <td  width=\"80%\" nowrap>");
            if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
              return;
            out.write("\r\n\t\t\t\t\t  </td>\r\n\t\t\t\t</tr>\r\n\r\n\t\t\t\t<tr>\r\n\t\t\t\t  <td height=\"25\" nowrap>备注：</td>\r\n\t\t\t\t  <td  width=\"80%\" nowrap>");
            if (_jspx_meth_html_005ftext_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
              return;
            out.write("\r\n\t\t\t\t\t  </td>\r\n\t\t\t\t</tr>\r\n\r\n\t\t\t\t<input type=\"hidden\" name=\"action\">\r\n\t\t\t\t");
            if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
              return;
            out.write("\r\n\t\t\t\t");
            if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
              return;
            out.write("\r\n\t\t\t\t<input type=\"hidden\" name=\"canAdd\" value=\"");
            out.print(canAdd);
            out.write("\" >\r\n\t\t\t\t");
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
      out.write("\r\n\t\t  </table>\r\n\t\t</div>\r\n</br>\r\n\r\n<table width=\"100%\" border=\"0\">\r\n<tr>\r\n  <td>\r\n    <input type=\"button\"class=\"btnButton4font\" style=\"cursor:pointer\" onClick=\"javascript:save('update');\" value=\"保存退出\">\r\n\t<input type=\"button\"class=\"btnButton2font\" style=\"cursor:pointer\" onClick=\"javascript:document.EmployeeWorkActionForm.reset();\" value=\"重置\">\r\n\t<input type=\"button\"class=\"btnButton2font\" style=\"cursor:pointer\" onClick=\"javascript:window.close();\" value=\"退出\">\r\n  </td>\r\n</tr>\r\n</table>\r\n\t</td>\r\n  </tr>\r\n</table>\r\n\r\n\r\n\r\n\r\n</body>\r\n");
}
      out.write("\r\n</html>\r\n<script language=\"javascript\">\r\n<!--\r\nString.prototype.trim = function() {\r\n\treturn this.replace(/(^ +| +$)/, \"\");\r\n}\r\nfunction save(tag){\r\n\r\n\t\tvar ifEndNowArr=document.getElementsByName(\"ifEndNow\");\r\n\t   \tvar ifEndNow;\r\n\t   \t for(var i = 0;i<ifEndNowArr.length;i++){\r\n\t\t\t  if(ifEndNowArr[i].checked){\r\n\t\t\t\t  ifEndNow=ifEndNowArr[i].value;\r\n\t\t\t  }\r\n\t\t  }\r\n\t\tvar beginDate=document.all.beginDate.value;\r\n        var endDate=document.all.endDate.value;\r\n        var bi1=new Date(beginDate);\r\n\t    var bi2=new Date(endDate);\r\n        if(bi1>=bi2&&1!=ifEndNow){\r\n         alert(\"结束工作时间必须大于开始工作时间\");\r\n         return false;\r\n\t\t}\r\n\t    if(document.all.workunit.value.trim() == \"\"){\r\n\t\t alert(\"工作单位不能为空！\");\r\n\t\t document.all.workunit.value = \"\";\r\n\t\t document.all.workunit.focus();\r\n\t\t return false;\r\n\t\t}else if(isPhone(document.all.telephone)){\r\n        document.all.action.value = tag;\r\n        EmployeeWorkActionForm.submit();\r\n        }\r\n}\r\n\r\n//验证电话号码有效性\r\nfunction isPhone(obj) {\r\n    var str = \"0123456789- ()\" ;\r\n    var val = obj.value ;\r\n");
      out.write("    for(var i = 0  ; i < val.length ; i++ ) {\r\n        if(str.indexOf(val.substring(i,i+1))==-1) {\r\n            alert(\"号码允许字符：0123456789-( )\") ;\r\n            obj.focus() ;\r\n            obj.select();\r\n            return false ;\r\n        }\r\n    }\r\n    return true ;\r\n}\r\nfunction changeEndDate(v){\r\n\tif(1==v){\r\n\t\tdocument.getElementById(\"endDateDiv\").style.display=\"none\";\r\n\t}\r\n\tif(0==v){\r\n\t\tdocument.getElementById(\"endDateDiv\").style.display=\"block\";\r\n\t}\r\n}\r\n//加载时设置setIfEndNow的值\r\nfunction setIfEndNow(){\r\n\tvar ifEndNowArr=document.getElementsByName(\"ifEndNow\");\r\n   \tvar ifEndNow;\r\n   \t for(var i = 0;i<ifEndNowArr.length;i++){\r\n\t\t  if(ifEndNowArr[i].checked){\r\n\t\t\t  ifEndNow=ifEndNowArr[i].value;\r\n\t\t  }\r\n\t  }\r\n   \tchangeEndDate(ifEndNow);\r\n}\r\n//-->\r\n</script>\r\n\r\n<script src=\"/jsoa/js/util.js\"></script>");
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

  private boolean _jspx_meth_html_005ftext_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f0 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
    boolean _jspx_th_html_005ftext_005f0_reused = false;
    try {
      _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005ftext_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /personnel_manager/employeeWorkModify.jsp(118,30) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setProperty("workunit");
      // /personnel_manager/employeeWorkModify.jsp(118,30) name = maxlength type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setMaxlength("50");
      // /personnel_manager/employeeWorkModify.jsp(118,30) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setStyleClass("inputtext");
      // /personnel_manager/employeeWorkModify.jsp(118,30) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setStyle("width:280px");
      int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
      if (_jspx_th_html_005ftext_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
      _jspx_th_html_005ftext_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005ftext_005f0, _jsp_getInstanceManager(), _jspx_th_html_005ftext_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005ftext_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f1 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
    boolean _jspx_th_html_005ftext_005f1_reused = false;
    try {
      _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
      _jspx_th_html_005ftext_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /personnel_manager/employeeWorkModify.jsp(123,30) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f1.setProperty("workduty");
      // /personnel_manager/employeeWorkModify.jsp(123,30) name = maxlength type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f1.setMaxlength("50");
      // /personnel_manager/employeeWorkModify.jsp(123,30) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f1.setStyleClass("inputtext");
      // /personnel_manager/employeeWorkModify.jsp(123,30) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f1.setStyle("width:280px");
      int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
      if (_jspx_th_html_005ftext_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
      _jspx_th_html_005ftext_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005ftext_005f1, _jsp_getInstanceManager(), _jspx_th_html_005ftext_005f1_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005ftext_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f2 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
    boolean _jspx_th_html_005ftext_005f2_reused = false;
    try {
      _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
      _jspx_th_html_005ftext_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /personnel_manager/employeeWorkModify.jsp(129,30) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f2.setProperty("proveperson");
      // /personnel_manager/employeeWorkModify.jsp(129,30) name = maxlength type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f2.setMaxlength("50");
      // /personnel_manager/employeeWorkModify.jsp(129,30) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f2.setStyleClass("inputtext");
      // /personnel_manager/employeeWorkModify.jsp(129,30) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f2.setStyle("width:280px");
      int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
      if (_jspx_th_html_005ftext_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
      _jspx_th_html_005ftext_005f2_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005ftext_005f2, _jsp_getInstanceManager(), _jspx_th_html_005ftext_005f2_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005ftext_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f3 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
    boolean _jspx_th_html_005ftext_005f3_reused = false;
    try {
      _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
      _jspx_th_html_005ftext_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /personnel_manager/employeeWorkModify.jsp(135,30) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f3.setProperty("telephone");
      // /personnel_manager/employeeWorkModify.jsp(135,30) name = maxlength type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f3.setMaxlength("50");
      // /personnel_manager/employeeWorkModify.jsp(135,30) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f3.setStyleClass("inputtext");
      // /personnel_manager/employeeWorkModify.jsp(135,30) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f3.setStyle("width:280px");
      int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
      if (_jspx_th_html_005ftext_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
      _jspx_th_html_005ftext_005f3_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005ftext_005f3, _jsp_getInstanceManager(), _jspx_th_html_005ftext_005f3_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005ftext_005f4(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f4 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
    boolean _jspx_th_html_005ftext_005f4_reused = false;
    try {
      _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
      _jspx_th_html_005ftext_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /personnel_manager/employeeWorkModify.jsp(141,30) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f4.setProperty("workMemo");
      // /personnel_manager/employeeWorkModify.jsp(141,30) name = maxlength type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f4.setMaxlength("50");
      // /personnel_manager/employeeWorkModify.jsp(141,30) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f4.setStyleClass("inputtext");
      // /personnel_manager/employeeWorkModify.jsp(141,30) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f4.setStyle("width:280px");
      int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
      if (_jspx_th_html_005ftext_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
      _jspx_th_html_005ftext_005f4_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005ftext_005f4, _jsp_getInstanceManager(), _jspx_th_html_005ftext_005f4_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005fhidden_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_005fhidden_005f0 = (org.apache.struts.taglib.html.HiddenTag) _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    boolean _jspx_th_html_005fhidden_005f0_reused = false;
    try {
      _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005fhidden_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /personnel_manager/employeeWorkModify.jsp(146,4) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fhidden_005f0.setProperty("empID");
      int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
      if (_jspx_th_html_005fhidden_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
      _jspx_th_html_005fhidden_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fhidden_005f0, _jsp_getInstanceManager(), _jspx_th_html_005fhidden_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005fhidden_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_005fhidden_005f1 = (org.apache.struts.taglib.html.HiddenTag) _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    boolean _jspx_th_html_005fhidden_005f1_reused = false;
    try {
      _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
      _jspx_th_html_005fhidden_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /personnel_manager/employeeWorkModify.jsp(147,4) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fhidden_005f1.setProperty("id");
      int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
      if (_jspx_th_html_005fhidden_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
      _jspx_th_html_005fhidden_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fhidden_005f1, _jsp_getInstanceManager(), _jspx_th_html_005fhidden_005f1_reused);
    }
    return false;
  }
}
