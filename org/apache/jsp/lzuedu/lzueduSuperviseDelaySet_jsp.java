/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:39:04 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.lzuedu;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class lzueduSuperviseDelaySet_jsp extends org.apache.jasper.runtime.HttpJspBase
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

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
java.util.List<String[]> list=com.js.ldap.supervise.CreateProcessForSupervise.getYearDelaySendList();

      out.write("\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<SCRIPT language=javascript src=\"/jsoa/js/openEndow.js\"></SCRIPT>\r\n<script type=\"text/javascript\" src=\"/jsoa/js/jquery-1.4.2.min.js\"></script>\r\n<title>设置年度督办延迟时间</title>\r\n\r\n\r\n<script type=\"text/javascript\">\r\nfunction dayControl(oper){\r\n\tvar id=oper.id;\r\n\tvar nid=id.substr(id.indexOf(\"_\"));\r\n\tif(oper.value==\"02\"){\r\n\t\t$(\"#op30\"+nid).attr(\"disabled\",\"disabled\");\r\n\t\t$(\"#op31\"+nid).attr(\"disabled\",\"disabled\");\r\n\t\tif($(\"#day\"+nid).val()==\"30\"||$(\"#day\"+nid).val()==\"31\"){\r\n\t\t\t$(\"#day\"+nid).val(\"01\");\r\n\t\t}\r\n\t}else if(oper.value==\"04\"||oper.value==\"06\"||oper.value==\"09\"||oper.value==\"11\"){\r\n\t\t$(\"#op30\"+nid).removeAttr(\"disabled\");\r\n\t\t$(\"#op31\"+nid).attr(\"disabled\",\"disabled\");\r\n\t\tif($(\"#day\"+nid).val()==\"31\"){\r\n\t\t\t$(\"#day\"+nid).val(\"01\");\r\n\t\t}\r\n\t}else{\r\n\t\t$(\"#op30\"+nid).removeAttr(\"disabled\");\r\n\t\t$(\"#op31\"+nid).removeAttr(\"disabled\");\r\n\t}\r\n}\r\nfunction initTime(id,t){\r\n\tif(t!=\"\"){\r\n\t\tvar arr=t.split(\"-\");\r\n\t\t$(\"#month_\"+id).val(arr[0]);\r\n\t\t$(\"#day_\"+id).val(arr[1]);\r\n\t}else{\r\n\t\t$(\"#month_\"+id).val(\"01\");\r\n");
      out.write("\t}\r\n    dayControl($(\"#month_\"+id)[0]);\r\n}\r\nfunction closePage(){\r\n\twindow.close();\r\n}\r\nfunction saveDelay(){\r\n\tvar parms=\"\";\r\n\t$(\"select[id^='month_']\").each(function(){\r\n\t\tvar vid=this.id;\r\n\t\tvar pid=vid.substr(vid.indexOf(\"_\")+1);\r\n\t\tvar gs=$(this).val()+\"-\"+$(\"#day_\"+pid).val();\r\n\t\tparms+=pid+\"#\"+gs+\"$$\";\r\n\t});\r\n\tif(parms.length>2){\r\n\t\tparms=parms.substring(0,parms.length-2);\r\n\t\t$.ajax({\r\n\t\t\ttype : \"post\",\r\n\t\t\tasync: true,\r\n\t\t\turl : \"/jsoa/lzuedu/lzueduSuperviseDelaySetData.jsp\",\r\n\t\t\tdata : \"parms=\"+parms ,\r\n\t\t\tsuccess : function(returnData){\r\n\t\t\t\tif($.trim(returnData)==\"t\"){\r\n\t\t\t\t\talert(\"保存成功！\");\r\n\t\t\t\t\tclosePage();\r\n\t\t\t\t}else{\r\n\t\t\t\t\talert(\"保存失败！\");\r\n\t\t\t\t}\r\n\t\t\t},\r\n\t\t\terror: function(){\r\n\t\t\t\talert(\"提交失败请联系管理员！\");\r\n\t\t\t}\r\n\t\t\t\r\n\t\t});\r\n\t}else{\r\n\t\talert(\"未获取到节点数据\");\r\n\t}\r\n\t\r\n}\r\n</script>\r\n</head>\r\n<body>\r\n\r\n\r\n<table width=\"100%\"  class=\"listTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n \t<tr height=\"25\">\r\n\t\t<td class=\"listTableHead\" width=\"30%\">节点名称</td>\r\n\t\t<td class=\"listTableHead\">延迟时间</td>\r\n\t</tr>\r\n    ");

     if(list!=null&&list.size()>0){
    	 for(int i=0;i<list.size();i++){
    		 String cls=i%2==0?"listTableLine2":"listTableLine1"; 
    		 String[] tmp=list.get(i);
    
      out.write("\r\n    <tr>\r\n    \t<td class=\"");
      out.print(cls);
      out.write('"');
      out.write('>');
      out.print(tmp[1] );
      out.write("</td>\r\n    \t<td class=\"");
      out.print(cls);
      out.write("\">\r\n\t    \t<select id=\"month_");
      out.print(tmp[0]);
      out.write("\" onchange=\"dayControl(this)\">\r\n\t\t\t\t<option value=\"01\">1</option>\r\n\t\t\t\t<option value=\"02\">2</option>\r\n\t\t\t\t<option value=\"03\">3</option>\r\n\t\t\t\t<option value=\"04\">4</option>\r\n\t\t\t\t<option value=\"05\">5</option>\r\n\t\t\t\t<option value=\"06\">6</option>\r\n\t\t\t\t<option value=\"07\">7</option>\r\n\t\t\t\t<option value=\"08\">8</option>\r\n\t\t\t\t<option value=\"09\">9</option>\r\n\t\t\t\t<option value=\"10\">10</option>\r\n\t\t\t\t<option value=\"11\">11</option>\r\n\t\t\t\t<option value=\"12\">12</option>\r\n\t\t\t</select>\r\n    \t月\r\n    \t<select id=\"day_");
      out.print(tmp[0]);
      out.write("\">\r\n\t\t\t\t<option value=\"01\">1</option>\r\n\t\t\t\t<option value=\"02\">2</option>\r\n\t\t\t\t<option value=\"03\">3</option>\r\n\t\t\t\t<option value=\"04\">4</option>\r\n\t\t\t\t<option value=\"05\">5</option>\r\n\t\t\t\t<option value=\"06\">6</option>\r\n\t\t\t\t<option value=\"07\">7</option>\r\n\t\t\t\t<option value=\"08\">8</option>\r\n\t\t\t\t<option value=\"09\">9</option>\r\n\t\t\t\t<option value=\"10\">10</option>\r\n\t\t\t\t<option value=\"11\">11</option>\r\n\t\t\t\t<option value=\"12\">12</option>\r\n\t\t\t\t<option value=\"13\">13</option>\r\n\t\t\t\t<option value=\"14\">14</option>\r\n\t\t\t\t<option value=\"15\">15</option>\r\n\t\t\t\t<option value=\"16\">16</option>\r\n\t\t\t\t<option value=\"17\">17</option>\r\n\t\t\t\t<option value=\"18\">18</option>\r\n\t\t\t\t<option value=\"19\">19</option>\r\n\t\t\t\t<option value=\"20\">20</option>\r\n\t\t\t\t<option value=\"21\">21</option>\r\n\t\t\t\t<option value=\"22\">22</option>\r\n\t\t\t\t<option value=\"23\">23</option>\r\n\t\t\t\t<option value=\"24\">24</option>\r\n\t\t\t\t<option value=\"25\">25</option>\r\n\t\t\t\t<option value=\"26\">26</option>\r\n\t\t\t\t<option value=\"27\">27</option>\r\n\t\t\t\t<option value=\"28\">28</option>\r\n\t\t\t\t<option value=\"29\">29</option>\r\n");
      out.write("\t\t\t\t<option value=\"30\" id=\"op30_");
      out.print(tmp[0]);
      out.write("\" disabled=\"disabled\">30</option>\r\n\t\t\t\t<option value=\"31\" id=\"op31_");
      out.print(tmp[0]);
      out.write("\" disabled=\"disabled\">31</option>\r\n\t\t\t</select>\r\n    \t日\r\n    \t<script type=\"text/javascript\">\r\n    \tinitTime('");
      out.print(tmp[0]);
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(tmp[2]);
      out.write("');\r\n    \t</script>\r\n    \t</td>\r\n    </tr>\r\n    ");
 
    	 }
     }

    
      out.write("\r\n    <tr height=\"25\">\r\n\t\t<td colspan=\"2\">\r\n\t\t  &nbsp;&nbsp;<input class=\"btnButton2font\" onclick=\"javascript:saveDelay();\" type=\"button\" value=\"确 定\">\r\n\t\t  &nbsp;&nbsp;<input class=\"btnButton2font\" onclick=\"javascript:closePage();\" type=\"button\" value=\"关 闭\">\r\n\t\t</td>\r\n\t</tr>\r\n</table>\r\n<div style=\"width: 440px;padding-left: 5px;\">\r\n<br/>\r\n注：\r\n<br/>\r\n 1、请指定每个季度的延迟时间，系统会自动在指定待办时间的当天8点发送待办（如未指定或流程发送时已超时则自动发送不会延时）。<br/>\r\n 2、修改本页面延迟时间仅对新建年度督办流程有效，已发流程无影响。<br/>\r\n 3、系统自动根据流程发起或导入所在年份指定对应节点延迟时间，故流程发起或导入请在年初进行。\r\n</div>\r\n</body>\r\n</html>\r\n\r\n");
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