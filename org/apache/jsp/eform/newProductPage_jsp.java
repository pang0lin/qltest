/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:03:23 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.eform;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class newProductPage_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n<HTML>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gbk\">\r\n<title>新品基础数据全流程报表</title>\r\n");

	response.setHeader("Cache-Control","no-store");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader ("Expires", 0);

      out.write("\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<style type=\"text/css\">\r\n   \r\n</style>\r\n<script type=\"text/javascript\" src=\"/jsoa/js/jquery-1.4.2.min.js\"></script>\r\n<script type=\"text/javascript\">\r\n$(window).resize(function(){\r\n\t$(\"#container\").height($(document.body).height()-40);\r\n\t$(\"#container\").width($(document.body).width()-20);\r\n});\r\n\r\n$(document).ready(function(){\r\n\t$(\"#container\").height($(document.body).height()-40);\r\n\t$(\"#container\").width($(document.body).width()-20);\r\n\t $.ajax({\r\n\t\t\ttype : \"post\",\r\n\t\t\tasync: true,\r\n\t\t\turl : \"/jsoa/eform/newProductPageGetData.jsp\",\r\n\t\t\tdata : \"searchFlag=0\" ,\r\n\t\t\tsuccess : function(returnData){\r\n\t\t\t\t$(\"#databody\").html(returnData);\r\n\t\t\t},\r\n\t\t\terror: function(){\r\n\t\t\t\t$(\"#databody\").html(\"<tr> <td colspan=\\\"41\\\" class=\\\"listTableLine2\\\" align=\\\"center\\\">数据异常请联系管理员</td></tr>\");\r\n\t\t\t}\r\n\t\t\t\r\n\t\t});\r\n});\r\n \r\n function search(){\r\n\t var tuhao=$(\"#tuhao\").val();\r\n\t var lczt=$(\"#lczt\").val();\r\n\t var tbr=$(\"#tibaoren\").val();\r\n\t var jccjs=$(\"#jccjs\").val();\r\n\t var tbsjs=$(\"#tibaoStartDate\").val();\r\n");
      out.write("\t var tbsje=$(\"#tiaobaoEndDate\").val();\r\n\t var data=\"searchFlag=1\";\r\n\t if($.trim(tuhao)!=\"\"){\r\n\t\t data+=\"&tuhao=\"+encodeURIComponent(encodeURIComponent($.trim(tuhao)));\r\n\t }\r\n\t if($.trim(lczt)!=\"\"){\r\n\t\t data+=\"&lczt=\"+lczt;\r\n\t }\r\n\t if($.trim(jccjs)!=\"\"){\r\n\t\t data+=\"&jccjs=\"+jccjs;\r\n\t }\r\n\t if($.trim(tbr)!=\"\"){\r\n\t\t data+=\"&tbr=\"+encodeURIComponent(encodeURIComponent($.trim(tbr)));\r\n\t }\r\n\t if($.trim(tbsjs)!=\"\"){\r\n\t\t data+=\"&tbsjs=\"+tbsjs;\r\n\t }\r\n\t if($.trim(tbsje)!=\"\"){\r\n\t\t data+=\"&tbsje=\"+tbsje;\r\n\t }\r\n\t $.ajax({\r\n\t\t\ttype : \"post\",\r\n\t\t\tasync: true,\r\n\t\t\turl : \"/jsoa/eform/newProductPageGetData.jsp\",\r\n\t\t\tdata : data ,\r\n\t\t\tsuccess : function(returnData){\r\n\t\t\t\t$(\"#databody\").html(returnData);\r\n\t\t\t},\r\n\t\t\terror: function(){\r\n\t\t\t\t$(\"#databody\").html(\"<tr> <td colspan=\\\"41\\\" class=\\\"listTableLine2\\\" align=\\\"center\\\">数据异常请联系管理员</td></tr>\");\r\n\t\t\t}\r\n\t\t\t\r\n\t\t});\r\n }\r\n function exportT(){\r\n\t var tuhao=$(\"#tuhao\").val();\r\n\t var lczt=$(\"#lczt\").val();\r\n\t var tbr=$(\"#tibaoren\").val();\r\n\t var jccjs=$(\"#jccjs\").val();\r\n\t var tbsjs=$(\"#tibaoStartDate\").val();\r\n");
      out.write("\t var tbsje=$(\"#tiaobaoEndDate\").val();\r\n\t var data=\"\";\r\n\t if($.trim(tuhao)!=\"\"){\r\n\t\t data+=\"&tuhao=\"+encodeURIComponent(encodeURIComponent($.trim(tuhao)));\r\n\t }\r\n\t if($.trim(lczt)!=\"\"){\r\n\t\t data+=\"&lczt=\"+lczt;\r\n\t }\r\n\t if($.trim(jccjs)!=\"\"){\r\n\t\t data+=\"&jccjs=\"+jccjs;\r\n\t }\r\n\t if($.trim(tbr)!=\"\"){\r\n\t\t data+=\"&tbr=\"+encodeURIComponent(encodeURIComponent($.trim(tbr)));\r\n\t }\r\n\t if($.trim(tbsjs)!=\"\"){\r\n\t\t data+=\"&tbsjs=\"+tbsjs;\r\n\t }\r\n\t if($.trim(tbsje)!=\"\"){\r\n\t\t data+=\"&tbsje=\"+tbsje;\r\n\t }\r\n\tlocation.href=\"/jsoa/eform/newProductPageExport.jsp?\"+data;\r\n\t\t\t\r\n }\r\n function reset(){\r\n\t $(\"#tuhao\").val(\"\");\r\n\t $(\"#lczt\").val(\"\");\r\n\t $(\"#tibaoren\").val(\"\");\r\n\t $(\"#jccjs\").val(\"\");\r\n\t $(\"#tibaoStartDate\").val(\"\");\r\n\t $(\"#tiaobaoEndDate\").val(\"\");\r\n\t $.ajax({\r\n\t\t\ttype : \"post\",\r\n\t\t\tasync: true,\r\n\t\t\turl : \"/jsoa/eform/newProductPageGetData.jsp\",\r\n\t\t\tdata : \"searchFlag=0\" ,\r\n\t\t\tsuccess : function(returnData){\r\n\t\t\t\t$(\"#databody\").html(returnData);\r\n\t\t\t},\r\n\t\t\terror: function(){\r\n\t\t\t\t$(\"#databody\").html(\"<tr> <td colspan=\\\"41\\\" class=\\\"listTableLine2\\\" align=\\\"center\\\">数据异常请联系管理员</td></tr>\");\r\n");
      out.write("\t\t\t}\r\n\t\t\t\r\n\t\t});\r\n }\r\n\r\n</script>\r\n<script language=javascript src=\"/jsoa/eform/datetime/datetime_check.js\"></script>\r\n<script language=javascript src=\"/jsoa/eform/datetime/datetime_select.js\"></script>\r\n</head>\r\n<body>\r\n<table width=\"100%\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\" class=\"SearchBar\">\r\n<tr>\r\n\t<td width=\"50px\" align=\"right\">图号：</td>\r\n\t<td width=\"80px\"><input name=\"tuhao\" id=\"tuhao\" value=\"\" style=\"width: 99%;\"> </td>\r\n\t<td width=\"60px\" align=\"right\">流程状态：</td>\r\n\t<td width=\"100px\">\r\n\t   <select name=\"lczt\" id=\"lczt\" style=\"width: 100%;\">\r\n\t   <option value=\"\">==请选择==</option>\r\n\t   <option value=\"10\">基础数据办理中</option>\r\n\t   <option value=\"11\">基础数据办理完毕</option>\r\n\t   <option value=\"20\">依赖票办理中</option>\r\n\t   <option value=\"21\">依赖票办理完毕</option>\r\n\t   <option value=\"30\">新品办理中</option>\r\n\t   <option value=\"31\">新品办理完毕</option>\r\n\t   </select>\r\n\t </td>\r\n\t<td width=\"100px\" align=\"right\">检查成绩书号：</td>\r\n\t<td width=\"100px\"><input name=\"jccjs\" id=\"jccjs\" value=\"\" style=\"width: 99%;\"> </td>\r\n\t <td width=\"100px\" align=\"right\">基础数据提报人：</td>\r\n");
      out.write("\t<td width=\"70px\"><input name=\"tibaoren\" id=\"tibaoren\" value=\"\" style=\"width: 99%;\"> </td>\r\n\t<td width=\"120px\" align=\"right\">基础数据提报时间：</td>\r\n\t<td width=\"250px\">\r\n\t\t<div style=\"display: inline;\">\r\n\t\t\t<input type=\"text\" style=\"width:100px;\" id=\"tibaoStartDate\" name=\"tibaoStartDate\" class=\"inputText\" onclick=\"setDay(this)\" style=\"background:url('/jsoa/eform/images/down_arrow.gif')\">&nbsp;到&nbsp;\r\n\t\t\t<input type=\"text\" style=\"width:100px;\" id=\"tiaobaoEndDate\" name=\"tiaobaoEndDate\" onclick=\"setDay(this)\" class=\"inputText\" style=\"background:url('/jsoa/eform/images/down_arrow.gif')\">\r\n\t\t</div>\r\n\t</td>\r\n\t<td align=\"right\">\r\n\t\t<input type=\"button\" class=\"btnButton2font\" value=\"查 询\" onclick=\"search();\" />\r\n\t\t<input type=\"button\" class=\"btnButton2font\" value=\"重 置\" onclick=\"reset();\" />\r\n\t\t<input type=\"button\" class=\"btnButton2font\" value=\"导 出\" onclick=\"exportT();\" />\r\n\t</td>\r\n\t<td width=\"20px\">&nbsp;</td>\r\n</tr>\r\n</table>\r\n <div id=\"container\" Style=\"height: 500px;width:1200px; overflow:auto;\">\r\n\t <table width=\"3700px\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\" class=\"listTable\">\r\n");
      out.write("\t\t<tr>\r\n\t\t\t<td width=\"40px\" class=\"listTableHead\">序号</td>\r\n\t\t\t<td width=\"100px\" class=\"listTableHead\">图号</td>\r\n\t\t\t<td width=\"100px\" class=\"listTableHead\">货品中文名称</td>\r\n\t\t\t<td width=\"100px\" class=\"listTableHead\">类别</td>\r\n\t\t\t<td width=\"60px\" class=\"listTableHead\">主单位</td>\r\n\t\t\t<td width=\"60px\" class=\"listTableHead\">副单位</td>\r\n\t\t\t<td width=\"100px\" class=\"listTableHead\">换算关系</td>\r\n\t\t\t<td width=\"50px\" class=\"listTableHead\">托外否</td>\r\n\t\t\t<td width=\"80px\" class=\"listTableHead\">年使用量</td>\r\n\t\t\t<td width=\"50px\" class=\"listTableHead\">压缩机</td>\r\n\t\t\t<td width=\"80px\" class=\"listTableHead\">寄售标志</td>\r\n\t\t\t<td width=\"50px\" class=\"listTableHead\">资材员</td>\r\n\t\t\t<td width=\"80px\" class=\"listTableHead\">采购批量</td>\r\n\t\t\t<td width=\"80px\" class=\"listTableHead\">采购期</td>\r\n\t\t\t<td width=\"80px\" class=\"listTableHead\">HS编码</td>\r\n\t\t\t<td width=\"100px\" class=\"listTableHead\">预设仓库</td>\r\n\t\t\t<td width=\"50px\" class=\"listTableHead\">保管员</td>\r\n\t\t\t<td width=\"100px\" class=\"listTableHead\">安全库存</td>\r\n\t\t\t<td width=\"100px\" class=\"listTableHead\">最大库存</td>\r\n\t\t\t<td width=\"70px\" class=\"listTableHead\">发料类型</td>\r\n");
      out.write("\t\t\t<td width=\"100px\" class=\"listTableHead\">计划价格</td>\r\n\t\t\t<td width=\"100px\" class=\"listTableHead\">最小包装单位</td>\r\n\t\t\t<td width=\"100px\" class=\"listTableHead\">最小包装数量</td>\r\n\t\t\t<td width=\"160px\" class=\"listTableHead\">主供应商</td>\r\n\t\t\t<td width=\"100px\" class=\"listTableHead\">检查成绩书号</td>\r\n\t\t\t<td width=\"130px\" class=\"listTableHead\">样品评价报告编码</td>\r\n\t\t\t<td width=\"70px\" class=\"listTableHead\">是否加急</td>\r\n\t\t\t<td width=\"100px\" class=\"listTableHead\">希望完成时间</td>\r\n\t\t\t<td width=\"70px\" class=\"listTableHead\">技术担当</td>\r\n\t\t\t<td width=\"160px\" class=\"listTableHead\">厂家</td>\r\n\t\t\t<td width=\"80px\" class=\"listTableHead\">送样时间</td>\r\n\t\t\t<td width=\"100px\" class=\"listTableHead\">检验完成时间</td>\r\n\t\t\t<td width=\"80px\" class=\"listTableHead\">样品合格否</td>\r\n\t\t\t<td width=\"50px\" class=\"listTableHead\">检验员</td>\r\n\t\t\t<td width=\"80px\" class=\"listTableHead\">检验期</td>\r\n\t\t\t<td width=\"80px\" class=\"listTableHead\">成本办担当</td>\r\n\t\t\t<td width=\"70px\" class=\"listTableHead\">品证担当</td>\r\n\t\t\t<td width=\"50px\" class=\"listTableHead\">提报人</td>\r\n\t\t\t<td width=\"70px\" class=\"listTableHead\">提报时间</td>\r\n");
      out.write("\t\t\t<td width=\"240px\" class=\"listTableHead\">状态</td>\r\n\t\t\t<td class=\"listTableHead\">办理人</td>\r\n\t\t</tr>\r\n\t\t<tbody id=\"databody\">\r\n\t\t\r\n\t  </tbody>\r\n\t</table>\r\n\t</div>\r\n\t\r\n</body>\r\n</HTML>\r\n");
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