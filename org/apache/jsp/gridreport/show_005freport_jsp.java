/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:41:09 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.gridreport;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.util.util.PicSingleton;
import java.util.*;
import java.text.*;
import com.js.oa.relproject.po.*;
import com.js.oa.relproject.bean.*;
import com.js.oa.info.channelmanager.service.ChannelBD;

public final class show_005freport_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(3);
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-logic.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-html.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-bean.tld", Long.valueOf(1499751390000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("java.text");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("com.js.oa.relproject.bean");
    _jspx_imports_packages.add("com.js.oa.relproject.po");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.oa.info.channelmanager.service.ChannelBD");
    _jspx_imports_classes.add("com.js.util.util.PicSingleton");
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

      out.write("\r\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n<title>报表在线显示</title>\r\n<script src=\"/jsoa/webmail/ajax_util.js\"></script>\r\n<link href=\"/jsoa/relproject/index.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<link href=\"skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<link rel=stylesheet type=\"text/css\" href=\"public/date_picker/DateObject1.css\" />\r\n<script language=\"javascript\" src=\"/jsoa/doc/form/ext/adapter/jquery/jquery.js\" type=\"text/javascript\"></script>\r\n<script language=\"javascript\" src=\"/jsoa/js/jquery.bt.min.js\" type=\"text/javascript\"></script>\r\n<script language=\"javascript\" src=\"/jsoa/js/jquery.bgiframe.min.js\" type=\"text/javascript\"></script>\r\n<script language=\"javascript\" src=\"/jsoa/js/jquery.hoverIntent.minified.js\" type=\"text/javascript\"></script>\r\n<script language=\"javascript\" src=\"/jsoa/js/excanvas.js\" type=\"text/javascript\"></script>\r\n<link href=\"skin/");
      out.print(session.getAttribute("skin"));
      out.write("/jquery.bt.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\r\n<style type=\"text/css\">\r\n\t#concat_book{width:500px;position:absolute;display:none;background-color:#ffffff;}\r\n\t#concat_book_top{height:10px;}\r\n\t#concat_book_body{border:1px solid #a9b1b5;clear:both;}\r\n\tbody,h1,h2,h3,h4,h5,h6,p,ol,ul,li,dl,dt,dd{padding:0;margin:0;}\r\n\t.person{background:url(/jsoa/images/concat_person.gif) no-repeat;padding-left:16px;}\r\n\t.tab{width:498px;overflow-y:auto;}\r\n\t.tab .header{ border-bottom:#ccc 1px solid;height:25px;background:#cccccc; margin-bottom:-1px;}\r\n\t.tab .header a{float:left;display:block;cursor:pointer;width:60px;height:25px;line-height:25px;text-align:center;background:#cccccc;color:#000;}\r\n\t.tab .header a.tabActive{background:#fff;color:#333;font-weight:bold;border-bottom:1px solid #fff;position:relative;border-right:1px solid #ccc;border-left:1px solid #ccc;}\r\n\t.tab .body{padding:10px;height:200px; clear:both;}\r\n\t.detial{background:url(/jsoa/images/superiors.gif) no-repeat;padding-left:16px;cursor:pointer;}\r\n\t.tableContent{width:100%;table-layout:fixed;}\r\n");
      out.write("\t.close {position:absolute;top:0;right:0;border:none;_margin-right:18px;margin-right:3px\\0;}\r\n\t.content{position:relative;}\r\n</style>\r\n<script type=\"text/javascript\">  \r\n//此处为string类添加三个成员\r\nString.prototype.Trim = function(){ return Trim(this);}\r\nString.prototype.LTrim = function(){return LTrim(this);}\r\nString.prototype.RTrim = function(){return RTrim(this);}\r\n\r\n//此处为独立函数\r\nfunction LTrim(str)\r\n{\r\n    var i;\r\n    for(i=0;i<str.length;i++)\r\n    {\r\n        if(str.charAt(i)!=\" \"&&str.charAt(i)!=\" \")break;\r\n    }\r\n    str=str.substring(i,str.length);\r\n    return str;\r\n}\r\nfunction RTrim(str)\r\n{\r\n    var i;\r\n    for(i=str.length-1;i>=0;i--)\r\n    {\r\n        if(str.charAt(i)!=\" \"&&str.charAt(i)!=\" \")break;\r\n    }\r\n    str=str.substring(0,i+1);\r\n    return str;\r\n}\r\nfunction Trim(str)\r\n{\r\n    return LTrim(RTrim(str));\r\n}\r\nvar popInfoPanel=new Array();\r\n$(document).ready(function(){\r\n\t$(\".tab .header>a:first\").addClass(\"tabActive\");//默认显示第一个\r\n\t$(\".tab .body ul\").not(\":first\").hide();\r\n\t$(\".tab .header>a\").unbind(\"click\").bind(\"click\", function(event){\r\n");
      out.write("\t\t$(this).siblings(\"a\").removeClass(\"tabActive\").end().addClass(\"tabActive\");\r\n\t\tvar index = $(\".tab .header>a\").index( $(this) );\r\n\t\t$(\".tab .body ul\").eq(index).siblings(\".tab .body ul\").hide().end().fadeIn(\"slow\");\r\n\t\tfor(var i=0;i<popInfoPanel.length;i++)\r\n\t\t{\r\n\t\t\t$(\"#\"+popInfoPanel[i]+\"\").btOff()//切换面板时关闭已打开的信息框\r\n\t\t}\r\n\t\treturn false;//取消冒泡事件\r\n   });\r\n});\r\n$(document).ready(function(){\r\n\t$(\"#concat_person\").click(function(event){//绑定事件处理\r\n\t\tvar offset = $(event.target).offset();\r\n\t\t$(\"#concat_book\").css({ top:offset.top + $(event.target).height() + \"px\", left:offset.left-500+\"px\"});//设置弹出层位置\r\n\t\t$(\"#concat_book\").show(200);//动画显示\t\r\n\t\treturn false;//取消冒泡事件\t\r\n\t});\r\n});\r\n$(document).click(function(){\r\n\t$(\"#concat_book\").hide(200);//动画隐藏\r\n})\r\nfunction closed()\r\n{\r\n\t$(\"#concat_book\").hide(200);//动画隐藏\r\n}\r\n//显示联系人信息\r\nfunction displayInfo(obj)\r\n{\r\n\tvar userId=$(obj).attr(\"id\");\r\n\tjQuery.bt.defaults.ajaxCache = true; \r\n\tpopInfoPanel.push(userId);\r\n\t$(obj).bt({\r\n\t\tajaxPath: \"/jsoa/ajaxGetData.jsp?method=getDetailInfoByUserId&parameters=curUserId@-@+userId, \r\n");
      out.write("\t\ttrigger: ['click','blur'],\r\n\t \tpositions: 'bottom', \r\n\t\twidth: 300, \r\n\t\tcenterPointX: .9, \r\n\t\tspikeLength: 20, \r\n\t\tspikeGirth: 40, \r\n\t\tpadding: 15, \r\n\t\tcornerRadius: 25, \r\n\t\tfill: '#FFF', \r\n\t\tstrokeStyle: '#ABABAB', \r\n\t\tstrokeWidth: 1,\r\n\t\tactiveClass: 'normal-active'\r\n\t}); \r\n}\r\nfunction closePoped(id)//切换选项卡关闭弹出框\r\n{\r\n\t$(\"#\"+id).btOff();\r\n\tvar index=-1;\r\n\tfor(var i=0;i<popInfoPanel.length;i++)\r\n\t\tif(popInfoPanel[i]==id)\r\n\t\t\tindex=i;\r\n\tif (index>=0)  \r\n   \t  \tpopInfoPanel.splice(index,1);//移除元素记录 \r\n}\r\n//页面加载完后初始化项目描述框\r\n$(document).ready(function(){\r\n\tif($('#proDescript').html().length>0)\r\n\t{\r\n\t\t$('#topbiaot').bt({ \r\n\t\t\ttrigger: ['hover',\"mouseover\"], \r\n\t\t\tcontentSelector: \"$('#proDescript')\",\r\n\t\t\tcenterPointX: .9,\r\n\t\t\tfill:'#FFF',\r\n\t\t\twidth: 200,\r\n\t\t\tpositions: ['bottom']\r\n\t\t});\t\r\n\t}\r\n})\r\nfunction input(obj)\r\n{\r\n\tobj.style.border=\"1px solid red\";\r\n\tobj.value=\"\";\r\n\tobj.style.color=\"#000000\";\r\n}\r\n//更新项目留言\r\nfunction update(obj)\r\n{\r\n\tobj.style.border=\"none\";\r\n\tif(obj.value.length>0)//如果留言不为空更新留言\r\n\t{\r\n\t\t$.ajax({\r\n\t   \t\t\ttype: \"GET\",\r\n");
      out.write("\t   \t\t\turl: \"/jsoa/ajaxGetData.jsp\",\r\n\t   \t\t\tdata: \"\",\r\n\t   \t\t\tasync: false,\r\n\t   \t\t\tsuccess: function(data)\r\n\t   \t\t\t{\r\n\t   \t\t\t\t$('#tableNoteList').html(data);\r\n\t\t\t\t}\r\n\t  \t   });\t\r\n\t}\r\n\tobj.style.color=\"#cccccc\"\r\n\tobj.value=\"请输入留言...\";\r\n}\r\n</script>\r\n</head>\r\n\r\n<body>\r\n<table width=\"100%\" border=\"0\" cellspacing=\"6\" cellpadding=\"0\">\r\n  <tr>\r\n    <td align=\"left\" valign=\"top\" width=\"50%\" ><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"boder_bottom\">\r\n      <tr>\r\n        <td class=\"table_left\">&nbsp;</td>\r\n        <td class=\"table_cenr\">协同工作</td>\r\n        <td class=\"table_right\">&nbsp;</td>\r\n      </tr>\r\n      <tr style=\"background:#FFFFFF;\">\r\n        <td class=\"boder_left\">&nbsp;</td>\r\n        <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"table-layout:fixed;\">\r\n          <tr ><td>\r\n          \t<iframe src=\"#\"\r\n          \tstyle=\"width: 100%;height: 100%;\" frameborder=\"no\" border=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" allowtransparency=\"yes\"></iframe>\r\n");
      out.write("          </td></tr>\r\n        </table></td>\r\n        <td class=\"boder_right\">&nbsp;</td>\r\n      </tr>\r\n    </table></td>\r\n    <td align=\"left\" valign=\"top\" width=\"50%\" ><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"boder_bottom\">\r\n      <tr>\r\n        <td class=\"table_left\">&nbsp;</td>\r\n        <td class=\"table_cenr\">项目文档</td>\r\n        <td class=\"table_right\">&nbsp;</td>\r\n      </tr>\r\n      <tr style=\"background:#FFFFFF;\">\r\n        <td class=\"boder_left\">&nbsp;</td>\r\n        <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"table-layout:fixed;\">\r\n             <tr><td><iframe src=\"#\"\r\n          \tstyle=\"width: 100%;height: 100%;\" frameborder=\"no\" border=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\"\r\n          \t allowtransparency=\"yes\"></iframe></td></tr>\r\n        </table></td>\r\n        <td class=\"boder_right\">&nbsp;</td>\r\n      </tr>\r\n    </table></td>\r\n  </tr>\r\n  <tr>\r\n    <td align=\"left\" valign=\"top\" width=\"50%\" ><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"boder_bottom\">\r\n");
      out.write("      <tr>\r\n        <td class=\"table_left\">&nbsp;</td>\r\n        <td class=\"table_cenr\">日程安排</td>\r\n        <td class=\"table_right\">&nbsp;</td>\r\n      </tr>\r\n      <tr style=\"background:#FFFFFF;\">\r\n        <td class=\"boder_left\">&nbsp;</td>\r\n        <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"table-layout:fixed;\">\r\n            <tr><td><iframe src=\"#\"\r\n          \tstyle=\"width: 100%;height: 100%;\" frameborder=\"no\" border=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\"\r\n          \t allowtransparency=\"yes\"></iframe></td></tr>\r\n        </table></td>\r\n        <td class=\"boder_right\">&nbsp;</td>\r\n      </tr>\r\n    </table></td>\r\n    <td align=\"left\" valign=\"top\" width=\"50%\" ><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"boder_bottom\">\r\n      <tr>\r\n        <td class=\"table_left\">&nbsp;</td>\r\n        <td class=\"table_cenr\">沟通交流</td>\r\n        <td class=\"table_right\">&nbsp;</td>\r\n      </tr>\r\n      <tr style=\"background:#FFFFFF;\">\r\n        <td class=\"boder_left\">&nbsp;</td>\r\n");
      out.write("        <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"table-layout:fixed;\">\r\n            <tr><td><iframe src=\"#\"\r\n          \tstyle=\"width: 100%;height: 100%;\" frameborder=\"no\" border=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\"\r\n          \t allowtransparency=\"yes\"></iframe></td></tr>\r\n        </table></td>\r\n        <td class=\"boder_right\">&nbsp;</td>\r\n      </tr>\r\n    </table></td>\r\n  </tr>\r\n  <tr>\r\n    <td align=\"left\" valign=\"top\" width=\"50%\" ><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"boder_bottom\">\r\n      <tr>\r\n        <td class=\"table_left\">&nbsp;</td>\r\n        <td class=\"table_cenr\">会议记录</td>\r\n        <td class=\"table_right\">&nbsp;</td>\r\n      </tr>\r\n      <tr>\r\n        <td class=\"boder_left\">&nbsp;</td>\r\n        <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"table-layout:fixed;\">\r\n          <tr><td><iframe src=\"#\"\r\n          \tstyle=\"width: 100%;height: 100%;\" frameborder=\"no\" border=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\"\r\n");
      out.write("          \t allowtransparency=\"yes\"></iframe></td></tr>\r\n        </table></td>\r\n        <td class=\"boder_right\">&nbsp;</td>\r\n      </tr>\r\n    </table></td>\r\n    <td align=\"left\" valign=\"top\" width=\"50%\" ><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"boder_bottom\">\r\n      <tr>\r\n        <td class=\"table_left\">&nbsp;</td>\r\n        <td class=\"table_cenr\">项目汇报</td>\r\n        <td class=\"table_right\">&nbsp;</td>\r\n      </tr>\r\n      <tr>\r\n        <td class=\"boder_left\">&nbsp;</td>\r\n        <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"table-layout:fixed;\">\r\n         ");
 for (int j=0; j<7; j++) { 
      out.write("\r\n            <tr>\r\n              <td height=22 align=\"left\" valign=\"middle\" class=\"left_xt_none\">&nbsp;</td>\r\n              <td align=\"left\" valign=\"bottom\" class=\"border_bm\">&nbsp;</td>\r\n              <td align=\"left\" valign=\"middle\" class=\"color_h\">&nbsp;</td>\r\n              <td align=\"left\" valign=\"middle\" class=\"color_right\">&nbsp;</td>\r\n              </tr>\r\n            ");
} 
      out.write("\r\n        </table></td>\r\n        <td class=\"boder_right\">&nbsp;</td>\r\n      </tr>\r\n      \r\n    </table></td> \r\n  </tr>\r\n  \r\n  <tr>\r\n    <td align=\"left\" valign=\"top\" width=\"50%\" ><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"boder_bottom\">\r\n      <tr>\r\n        <td class=\"table_left\">&nbsp;</td>\r\n        <td class=\"table_cenr\">项目任务</td>\r\n        <td class=\"table_right\">&nbsp;</td>\r\n      </tr>\r\n      <tr>\r\n        <td class=\"boder_left\">&nbsp;</td>\r\n        <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"table-layout:fixed;\">\r\n          ");
 for (int j=0; j<7; j++) { 
      out.write("\r\n            <tr>\r\n              <td height=22 align=\"left\" valign=\"middle\" class=\"left_xt_none\">&nbsp;</td>\r\n              <td align=\"left\" valign=\"bottom\" class=\"border_bm\">&nbsp;</td>\r\n              <td align=\"left\" valign=\"middle\" class=\"color_h\">&nbsp;</td>\r\n              <td align=\"left\" valign=\"middle\" class=\"color_right\">&nbsp;</td>\r\n            ");
} 
      out.write("\r\n        </table></td>\r\n        <td class=\"boder_right\">&nbsp;</td>\r\n      </tr>\r\n    </table></td>\r\n    <td align=\"left\" valign=\"top\" width=\"50%\" ><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"boder_bottom\">\r\n      <tr>\r\n        <td class=\"table_left\">&nbsp;</td>\r\n        <td class=\"table_cenr\">项目留言</td>\r\n        <td class=\"table_right\">&nbsp;</td>\r\n      </tr>\r\n      <tr >\r\n       \t<td class=\"boder_left\">&nbsp;</td>\r\n      \t<td style=\"heigth:22px;\">\r\n      \t\t<input id=\"txtNote\" type=\"text\" onfocus=\"input(this);\" onblur=\"update(this);\" style=\"line-height:15px;height:15px;font-size:12px;border:0px;width:100%;color:#cccccc\" value=\"请输入留言...\"></input>\r\n      \t</td>\r\n      \t<td class=\"boder_right\">&nbsp;</td>\r\n      </tr>\r\n      <tr>\r\n        <td class=\"boder_left\">&nbsp;</td>\r\n        <td><table id=\"tableNoteList\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"table-layout:fixed;\">\r\n         ");
 for (int j=0; j<0; j++) { 
      out.write("\r\n            <tr>\r\n              <td height=22 align=\"left\" valign=\"middle\" class=\"left_xt_none\">&nbsp;</td>\r\n              <td align=\"left\" valign=\"bottom\" class=\"border_bm\">&nbsp;</td>\r\n              <td align=\"left\" valign=\"middle\" class=\"color_h\">&nbsp;</td>\r\n              <td align=\"left\" valign=\"middle\" class=\"color_right\">&nbsp;</td>\r\n            ");
} 
      out.write("\r\n        </table>\r\n        </td>\r\n        <td class=\"boder_right\">&nbsp;</td>\r\n      </tr>\r\n    </table></td> \r\n  </tr>\r\n  <tr style=\"display:none;\">\r\n      <td align=\"left\" valign=\"top\" width=\"50%\" ><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"boder_bottom\">\r\n      <tr>\r\n        <td class=\"table_left\">&nbsp;</td>\r\n        <td class=\"table_cenr\">项目日报</td>\r\n        <td class=\"table_right\">&nbsp;</td>\r\n      </tr>\r\n      <tr>\r\n        <td class=\"boder_left\">&nbsp;</td>\r\n        <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"table-layout:fixed;\">\r\n          ");
 for (int j=0; j<7; j++) { 
      out.write("\r\n            <tr>\r\n              <td height=22 align=\"left\" valign=\"middle\" class=\"left_xt_none\">&nbsp;</td>\r\n              <td align=\"left\" valign=\"bottom\" class=\"border_bm\">&nbsp;</td>\r\n              <td align=\"left\" valign=\"middle\" class=\"color_h\">&nbsp;</td>\r\n              <td align=\"left\" valign=\"middle\" class=\"color_right\">&nbsp;</td>\r\n            ");
} 
      out.write("\r\n        </table></td>\r\n        <td class=\"boder_right\">&nbsp;</td>\r\n      </tr>\r\n    </table></td>\r\n    <td>&nbsp;</td>\r\n  \r\n  </tr>\r\n</table>\r\n</body>\r\n</html>\r\n\r\n<script language=\"JavaScript\">\r\n\r\n</script>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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