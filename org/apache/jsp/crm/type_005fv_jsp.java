/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:49:29 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.crm;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.oa.crm.po.VisitType;
import com.js.oa.crm.util.JDBCManager;

public final class type_005fv_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.oa.crm.po.VisitType");
    _jspx_imports_classes.add("com.js.oa.crm.util.JDBCManager");
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

      out.write("\r\n\r\n\r\n");

String webapp=request.getContextPath();
VisitType visitType=(VisitType)request.getAttribute("visitType");
String temp=request.getParameter("temp");
String parentRange="";
if(temp!=null&&temp.equals("true")){
	parentRange=JDBCManager.getUserRange(null,"客户管理");
}

      out.write("\r\n<HTML>\r\n<HEAD><TITLE>");
      out.print(visitType==null?"修改权限设置":"修改权限设置");
      out.write("</TITLE></HEAD>\r\n<style type=\"text/css\">\r\n</style>\r\n<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<SCRIPT language=javascript src=\"/jsoa/webmail/ajax_util.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/js/openEndow.js\"></SCRIPT>\r\n<BODY  class=\"MainFrameBox Pupwin\">\r\n<FORM  NAME =\"frm\" METHOD=\"POST\">\r\n<input type=\"hidden\" name=\"id\" value=\"");
      out.print(visitType==null?"":visitType.getId());
      out.write("\">\r\n<input type=\"hidden\" name=\"rangeId\" value=\"");
      out.print(visitType==null?"":visitType.getUserRange());
      out.write("\">\r\n<input type=\"hidden\" name=\"rangeIds\" value=\"");
      out.print(visitType==null?"":visitType.getOrgRange());
      out.write("\">\r\n<TABLE width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  style=\"padding-left:10px;\" class=\"docBoxNoPanel\">\r\n  <tr>\r\n    <td valign=\"top\">\r\n    <table width=\"90%\">\r\n  <TR valign=\"top\" style=\"padding-top:12px;\">\r\n    <TD width=\"13%\" align=\"left\" id=\"3230\">名&nbsp;称<span style=\"color: red\">*</span>：</TD>\r\n     <TD align=\"left\" id=\"3230\"><input style=\"width:70%;\" type=\"text\" class=\"inputText\" maxLength=\"50\" name=\"typeName\" readonly=\"true\" value=\"");
      out.print(visitType==null?"":visitType.getName());
      out.write("\"></TD>\r\n  </TR>\r\n  <TR valign=\"top\" style=\"padding-top:12px;\">\r\n    <td nowrap>使用范围：</td>\r\n     <td><textarea rows=\"3\" onClick=\"selectPeople('rangeId','range',1)\" name=\"range\" class=\"inputTextarea\" title=\"请点击选择\" style=\"width:70%;\" readonly=\"true\">");
      out.print(visitType==null?"":visitType.getUserRangeName());
      out.write("</textarea></td>\r\n  </TR>\r\n  <TR valign=\"top\" ");
if(temp.equals("true")){
      out.write("style=\"padding-top:12px;display:none;\"");
}else{
      out.write("style=\"padding-top:12px;\" ");
}
      out.write(">\r\n    <td nowrap>数据查看范围：</td>\r\n     <td><textarea rows=\"3\" ");
if(visitType!=null&&(!visitType.getName().equals("例外管理")&&!visitType.getName().equals("回访管理"))){
      out.write(" onClick=\"selectPeople('rangeIds','ranges',0)\" title=\"请点击选择\"");
}
      out.write(" name=\"ranges\" class=\"inputTextarea\" style=\"width:70%;\" readonly=\"true\">");
      out.print(visitType==null?"":visitType.getOrgRangeName());
      out.write("</textarea></td>\r\n  </TR>\r\n  <TR valign=\"top\" style=\"display:none;padding-top:12px;\">\r\n    <td>描述：</td>\r\n     <td><textarea rows=\"3\" name=\"desc\" class=\"inputTextarea\" style=\"width:70%;\" maxSize=\"120\"></textarea></td>\r\n  </TR>   \r\n  <TR>\r\n    <TD colspan=\"2\" align=\"left\" style=\"padding-bottom:20px;\">&nbsp;");
if(visitType==null){ 
      out.write("   \r\n\t  <INPUT VALUE=\"保存继续\" TYPE=\"button\" onClick=\"checkForm('1')\" class=\"btnButton4Font\">\r\n\t  ");
} 
      out.write("\r\n\t  <INPUT VALUE=\"保存退出\" TYPE=\"button\" onClick=\"checkForm('0')\" class=\"btnButton4Font\">\r\n\t  <INPUT VALUE=\"取消\" TYPE=\"button\" onClick=\"javascript:window.close();\"  class=\"btnButton4Font\">\r\n\t  <br>     </TD>\r\n     </TR>\r\n  </table>\r\n  </td>\r\n  </tr>\r\n  \r\n  <tr>\r\n  <td></td>\r\n  </tr>\r\n</TABLE>\r\n</FORM>\r\n</BODY>\r\n</HTML>\r\n<script language=\"javascript\">\r\nvar type;\r\nfunction checkForm(flag){\r\ntype=flag;\r\nvar name=frm.typeName.value;\r\nvar id=frm.id.value;\r\nvar range=frm.range.value;\r\nvar rangeId=frm.rangeId.value;\r\nvar ranges=frm.ranges.value;\r\nvar rangeIds=frm.rangeIds.value;\r\nif(range=='请点击选择')range=\"\";\r\nif(ranges=='请点击选择')ranges=\"\";\r\nif(name==''||name==null||name.replace(/(^\\s*)|(\\s*$)/g,\"\")==''){\r\n    alert('请输入类别名称！');\r\n    return false;\r\n }else{\r\n    var url='");
      out.print(webapp);
      out.write("'+\"/visitType.do?method=checkName&ranges=\"+ranges+\"&rangeIds=\"+rangeIds+\"&range=\"+range+\"&rangeId=\"+rangeId+\"&id=\"+id+\"&name=\"+name;\r\n    sendG(url,getResult,null);\r\n }\r\n}\r\n\r\nfunction getResult(cartXML){\r\n  var cart = cartXML.getElementsByTagName(\"result\")[0];\r\n  var status = cart.getElementsByTagName(\"status\")[0].firstChild.nodeValue;\r\n  var info = cart.getElementsByTagName(\"info\")[0].firstChild.nodeValue;\r\n  if(status!='0'){\r\n  \talert(info);\r\n  \tdocument.all.typeName.select();\r\n  }else{\r\n  \tif(type=='1'){\r\n  \t\tdocument.all.typeName.value=\"\";\r\n  \t\topener.location.href=\"/jsoa/visitType.do?method=getVisitTypeList\";\r\n  \t}else{\r\n  \t\twindow.close();\r\n  \t\topener.location.href=\"/jsoa/visitType.do?method=getVisitTypeList\";\r\n  \t}\r\n  }\r\n}\r\n\r\nfunction selectPeople(a,b,c){\r\n var selectedId=eval('frm.'+a+'.value');\r\n var selectedName=eval('frm.'+b+'.value');\r\n if(c==\"1\"){\r\n \t//if('");
//=temp
      out.write("'=='false'){\r\n \t\tselectPersionFromOrg('',a,b,selectedId,selectedName,'orgPerson','no','_js_','*0*','yes','org');\r\n \t//}else{\r\n \t//\tselect(a,b,selectedId,selectedName);\r\n \t//}\r\n }if(c==\"0\"){\r\n  \tselectEndowSC(a,b,'org','no','org','*0*','yes');\r\n }\r\n}\r\n\r\nfunction selectEndowSC(contrId,contrName,type,single,show,range,limit){\r\n    var selectedId=eval(\"document.all.\"+contrId+\".value\");\r\n    var selectedName=eval(\"document.all.\"+contrName+\".value\");\r\n    if(selectedId==null || selectedId==\"null\" || selectedId==\"\"){\r\n        selectedId=\"\";\r\n        selectedName=\"\";\r\n    }\r\n    openLimitedEndow(contrId,contrName,selectedId,selectedName,type,single,show,range,limit);\r\n}\r\n\r\nfunction select(a,b,c,d){\r\n openFromParentEndow(a,b,c,d,'userorggroup','no','userorggroup','");
      out.print(parentRange);
      out.write("','1','yes');\r\n}\r\n</script>\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
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