/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:45:39 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.module;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.system.util.StaticParam;
import java.util.Date;
import com.js.util.config.SystemCommon;
import com.js.oa.zky.service.ZkyMenuBD;
import com.js.system.manager.service.*;
import com.js.oa.scheme.worklog.service.WorkLogBD;
import com.js.oa.scheme.taskcenter.service.TaskBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import java.util.List;
import com.js.oa.jsflow.vo.*;
import com.js.util.util.BrowserJudge;
import com.js.util.config.SystemCommon;

public final class module_005fmenu_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("com.js.oa.jsflow.vo");
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("com.js.system.manager.service");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.util.List");
    _jspx_imports_classes.add("com.js.oa.scheme.taskcenter.service.TaskBD");
    _jspx_imports_classes.add("com.js.system.util.StaticParam");
    _jspx_imports_classes.add("java.util.Date");
    _jspx_imports_classes.add("com.js.util.config.SystemCommon");
    _jspx_imports_classes.add("com.js.oa.zky.service.ZkyMenuBD");
    _jspx_imports_classes.add("com.js.oa.scheme.worklog.service.WorkLogBD");
    _jspx_imports_classes.add("com.js.util.util.BrowserJudge");
    _jspx_imports_classes.add("com.js.oa.jsflow.service.WorkFlowBD");
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

String module=request.getParameter("module");
String index=request.getParameter("index");  

String userId = session.getAttribute("userId").toString();
String domainId = session.getAttribute("domainId").toString();
java.util.List menuList=(java.util.List)request.getAttribute("menuList");
String moduleName="";

if(menuList!=null){
	moduleName=((Object[])menuList.get(0))[1].toString();
}
boolean bflag=false;if(BrowserJudge.isMSIE(request))bflag=true;

boolean isJx = false;

      out.write("\r\n\r\n\r\n<HTML>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\" />\r\n<title>扩展菜单</title>\r\n<link href=\"/jsoa/css/menustyle.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n<script src=\"/jsoa/js/imenu.js\"></script>\r\n<style type=\"text/css\">\r\n<!--\r\nbody,table,div{font-size:12px; margin:0px; padding:0px;color:#003366;}\r\nbody{\r\nscrollbar-base-color: #ffffff;\r\nscrollbar-darkshadow-color: #ffffff;\r\nscrollbar-highlight-color: #ffffff;\r\nbackground:url(背景图片地址);\r\noverflow:hidden;\r\nmargin:0;\r\n}\r\ndiv.main{\r\noverflow:scroll;\r\nwidth:500px;\r\nheight:400px;\r\n/*filter: chroma(color=#ffffff);*/\r\n}\r\n*{margin:0;padding:0;}\r\n");
if(com.js.util.util.BrowserJudge.isMSIE(request)){ 
      out.write("\r\n.menuNormal{\r\n\theight:32px; \r\n\twidth:131px; \r\n\tpadding-left:17px; \r\n\tbackground-image:url(/jsoa/imges/left-1.gif); \t\r\n\tcursor:pointer;\r\n}\r\n\r\n.menuFocus{\r\n\theight:32px; \r\n\twidth:131px; \r\n\tpadding-left:17px; \r\n\tbackground-image:url(/jsoa/imges/left-jiaoh.gif);\r\n\tfont-weight:bold;\r\n\tcursor:pointer;\r\n}\r\n");
}
      out.write("\r\n.leftMenuTop{\r\n\tbackground-image:url(/jsoa/imges/left-1.gif);}\r\n\r\n/*.leftMenuTopDIV{\r\n\theight:22px; \r\n\tpadding-top:8px; \r\n\tfloat:left; \r\n\twidth:132px;\r\n\tmargin:0px;\r\n}*/\r\n\r\n.topMenuNormal{\r\n\twidth:60px;\r\n\tcursor:pointer;\r\n}\r\n\r\n.topMenuFocus{\r\n\tbackground-image:url(/jsoa/imges/zi.gif); \r\n\twidth:60px;\r\n\tcursor:pointer;\r\n}\r\n\r\n/*.menuTopTitle{\r\n\tfloat:left;f\r\n\tpadding-top:2px;\r\n}\r\n\r\n.menuTopTitleLeft{\r\n\twidth:17px;\r\n\tfloat:left;\r\n\tpadding:0px 2px 0px 7px;\r\n}\r\n\r\n.menuTopTitleRight{\r\n\tpadding-left:2px;\r\n\twidth:17px;\r\n\tfloat:left;\r\n}\r\n*/\r\n.menuScrollDIV{\r\n\tfloat:left; \r\n\tpadding-bottom:2px;\r\n}\r\n\r\n.menuShowHide{\r\n\tbackground-image:url(/jsoa/imges/cent.gif); \r\n\twidth:8px; \r\n\tfloat:left; \r\n\theight:100%; \r\n\tpadding-top:270px;\r\n}\r\n\r\n.portalNormal{\r\n\tbackground-image:url(/jsoa/imges/03.gif);\r\n\tcolor:#000;\r\n\twidth:75px;\r\n\tcursor:pointer;\r\n}\r\n\r\n.portalFocus{\r\n\tbackground-image:url(/jsoa/imges/02.gif);\r\n\tcolor:#000;\r\n\twidth:75px;\r\n\tcursor:pointer;\r\n} \r\n-->\r\n</style>\r\n<body style=\"margin:0px; padding:0px;\" onload=\"initHeight();\" onresize=\"initHeight();\"> \r\n");
      out.write("<table height=\"100%\" width=\"132\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n  <tr>\r\n    <td height=\"32\">\r\n\t<!--//*头部*-->\r\n\t<table width=\"131\" height=\"32\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n \t\t <tr>\r\n    \t\t<td class=\"leftMenuTop\">\r\n\t\r\n<!--//*left*-->\r\n    <div class=\"leftMenuTopDIV\">\t\r\n\t<div class=\"menuTopTitleLeft\"><img src=\"/jsoa/imges/9s.gif\"></img></div>\r\n\t<div class=\"menuTopTitle\"><b>");
      out.print(moduleName );
      out.write("</b></div>\r\n\t<div class=\"menuTopTitleRight\"><img src=\"/jsoa/imges/9s.gif\"></img></div>\r\n\t</div>\r\n<!--//*right*-->\r\n\t\r\n    <div class=\"menuScrollDIV\">\r\n        \t\t<table height=\"32\"  width=\"20\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n\t\t\t\t\t  <tr>\r\n\t\t\t\t\t    <td align=\"center\" valign=\"center\"  style=\"padding-top:4px;\">\r\n\t\t\t\t\t       <img id=\"imgUP\" src=\"/jsoa/imges/kzmb.gif\" title=\"切换面板\" style=\"cursor:pointer\" onMouseOver=\"shortFocus_(this);\" onMouseOut=\"shortBlur_(this);\" onClick=\"changePanle(1);\"/>\r\n\t\t\t\t\t    </td>\r\n\t\t\t\t\t    </tr>\t\t\t\t\t\r\n\t\t\t\t\t</table>\r\n       </div>\r\n\t      <!--//*交换按扭*-->\r\n\t   \t\t</td>\r\n  \t\t</tr>\r\n\t</table>\t \r\n<!--//*结束*-->\r\n\t\r\n\t</td>\r\n  </tr>\r\n  <tr>\r\n    <td>\r\n\t\t<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"100%\" width=\"132\" style=\"background-image:url(/jsoa/imges/left-cent.gif); background-attachment:fixed;padding:0px; margin:0px; scrollbar-base-color: #ffffff; scrollbar-darkshadow-color: #ffffff; scrollbar-highlight-color: #ffffff; overflow:hidden;\" onLoad=\"focusNode();\">\r\n       <tr id=\"submenuBox0\" valign=\"top\">\r\n");
      out.write("        <td>\r\n           <div class=\"main\" id=\"meauDIV\" style=\"width:132px;height:260px;overflow-x:hidden;overflow-y:auto;font-size:12px; background-image:url(\\imges\\left-1.gif)\">   \r\n                <table  width=\"131\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n                \r\n  ");

  //循环显示菜单
  int menuIndex=-1;
  int subMenuIndex=-1;
  int thirdMenuIndex=-1;
  String menuName,menuURL;
  int menuLevel;
  Object[] obj;
  boolean zkyJx = false;
  //menuList 第一个为主菜单 循环从1开始
  for(int i=1;i<menuList.size();i++){
	  obj=(Object[])menuList.get(i);
	  menuName=obj[1].toString();
	  
	  if("中科院".equals(SystemCommon.getCustomerName()) && 
	  		(obj[24]!=null && !"0".equals(obj[24]+"") && !"".equals(obj[24]+"") && !"null".equalsIgnoreCase(obj[24]+""))){
	  		isJx = true;
	  		menuURL=obj[4].toString();
	  		if(("1".equals(obj[25]+"")||"2".equals(obj[25]+""))){
				String menuId=obj[4].toString().substring(obj[4].toString().indexOf("menuId=")+7, obj[4].toString().length());
				menuURL="/jsoa/ModuleDealwithAction.do?action=getTreeMain&menuId="+menuId;
			}
	  }else{
			menuURL=obj[4].toString();
	  } 
	  //menuURL=obj[4].toString();
	  
	  menuLevel=obj[3].toString().split("-").length-1;
	  if(menuLevel==1){
		  //一级菜单
		  subMenuIndex=-1;
		  
      out.write("\r\n\t\t   <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">   \r\n\t\t       <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('");
      out.print(menuURL );
      out.write("')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\"  src=\"/jsoa/imges/shanj.gif\" />&nbsp;");
      out.print(menuName);
      out.write("</div></td>\r\n\t\t   </tr> \r\n\t\t  ");

	  }else if(menuLevel==2){
		  //二级菜单
		  thirdMenuIndex=-1;
		  if(obj[24]==null || "0".equals(obj[24]+"") || "".equals(obj[24]+"") || "null".equalsIgnoreCase(obj[24]+"")){
		  
      out.write("\r\n\t\t  <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n\t\t       <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onclick=\"clickSubMenu('");
      out.print(menuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(subMenuIndex );
      out.write("');jumpMain('");
      out.print(menuURL );
      out.write("')\"><img id=\"subMenuImg_");
      out.print(menuIndex );
      out.write('_');
      out.print(subMenuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;");
      out.print(menuName);
      out.write("</a></div></td>\r\n\t\t  </tr>\r\n\t\t  ");
}else{
		  ZkyMenuBD zbd = new ZkyMenuBD();
		  boolean isMoKuai = zbd.isMoKuaiManger(session.getAttribute("userId")+"", obj[0]+"");
		  boolean isJiXiao = zbd.isJiXiaoManger(session.getAttribute("userId")+"", obj[0]+"");
		  zkyJx = isJiXiao;
		  if("1".equals(obj[25]+"") && isMoKuai){
      out.write("\r\n\t\t  <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n\t\t       <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onclick=\"clickSubMenu('");
      out.print(menuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(subMenuIndex );
      out.write("');jumpMain('");
      out.print(menuURL+"&mqzt="+obj[25] );
      out.write("')\"><img id=\"subMenuImg_");
      out.print(menuIndex );
      out.write('_');
      out.print(subMenuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;");
      out.print(menuName);
      out.write("</a></div></td>\r\n\t\t  </tr>\r\n\t\t  ");
}else if("2".equals(obj[25]+"") && isJiXiao){
      out.write("\r\n\t\t  <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n\t\t       <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onclick=\"clickSubMenu('");
      out.print(menuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(subMenuIndex );
      out.write("');jumpMain('");
      out.print(menuURL+"&mqzt="+obj[25] );
      out.write("')\"><img id=\"subMenuImg_");
      out.print(menuIndex );
      out.write('_');
      out.print(subMenuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;");
      out.print(menuName);
      out.write("</a></div></td>\r\n\t\t  </tr>\r\n\t\t  ");
}else if("0".equals(obj[25]+"")){
      out.write("\r\n\t\t  <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n\t\t       <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onclick=\"clickSubMenu('");
      out.print(menuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(subMenuIndex );
      out.write("');jumpMain('");
      out.print(menuURL );
      out.write("')\"><img id=\"subMenuImg_");
      out.print(menuIndex );
      out.write('_');
      out.print(subMenuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;");
      out.print(menuName);
      out.write("</a></div></td>\r\n\t\t  </tr>\r\n\t\t  ");
}}
	  }else if(menuLevel==3){
		  //三级菜单
		  if(obj[24]==null || "0".equals(obj[24]+"") || "".equals(obj[24]+"") || "null".equalsIgnoreCase(obj[24]+"")){
		  
      out.write("\r\n\t\t  <tr id=\"thirdTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(subMenuIndex );
      out.write('_');
      out.print(++thirdMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n\t         <td height=\"22\"><div style=\"padding-left:33px;\"><a style=\"cursor:pointer\" onclick=\"changeFont3('");
      out.print(menuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(subMenuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(thirdMenuIndex);
      out.write("');jumpMain('");
      out.print(menuURL );
      out.write("')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;");
      out.print(menuName);
      out.write("</a></div></td>      \r\n\t      </tr>");
}else{
		  ZkyMenuBD zbd = new ZkyMenuBD();
		  boolean isMoKuai = zbd.isMoKuaiManger(session.getAttribute("userId")+"", obj[0]+"");
		  boolean isJiXiao = zbd.isJiXiaoManger(session.getAttribute("userId")+"", obj[0]+"");
		  zkyJx = isJiXiao;
		  if("1".equals(obj[25]+"") && isMoKuai){
      out.write("\r\n\t\t <tr id=\"thirdTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(subMenuIndex );
      out.write('_');
      out.print(++thirdMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n\t\t       <td height=\"22\"><div style=\"padding-left:33px;\"><a style=\"cursor:pointer\" onclick=\"changeFont3('");
      out.print(menuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(subMenuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(thirdMenuIndex);
      out.write("');jumpMain('");
      out.print(menuURL+"&mqzt="+obj[25] );
      out.write("')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;");
      out.print(menuName);
      out.write("</a></div></td>\r\n\t\t  </tr>\r\n\t\t  ");
}else if("2".equals(obj[25]+"") && isJiXiao){
      out.write("\r\n\t\t  <tr id=\"thirdTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(subMenuIndex );
      out.write('_');
      out.print(++thirdMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n\t\t       <td height=\"22\"><div style=\"padding-left:33px;\"><a style=\"cursor:pointer\" onclick=\"changeFont3('");
      out.print(menuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(subMenuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(thirdMenuIndex);
      out.write("');jumpMain('");
      out.print(menuURL+"&mqzt="+obj[25] );
      out.write("')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;");
      out.print(menuName);
      out.write("</a></div></td>\r\n\t\t  </tr>\r\n\t\t  ");
}else if("0".equals(obj[25]+"")){
      out.write("\r\n\t\t  <tr id=\"thirdTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(subMenuIndex );
      out.write('_');
      out.print(++thirdMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n\t\t       <td height=\"22\"><div style=\"padding-left:33px;\"><a style=\"cursor:pointer\" onclick=\"changeFont3('");
      out.print(menuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(subMenuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(thirdMenuIndex);
      out.write("');jumpMain('");
      out.print(menuURL );
      out.write("')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;");
      out.print(menuName);
      out.write("</a></div></td>\r\n\t\t  </tr>\r\n\t\t  ");
}}
	  }
  }
  if("中科院".equals(SystemCommon.getCustomerName())&& isJx){
  if(!zkyJx){ 
      out.write("\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">   \r\n      <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/zkyRankAction.do?action=word&jobNum=");
      out.print(StaticParam.getEmpNumberByEmpId(session.getAttribute("userId")+"") );
      out.write("')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\"  src=\"/jsoa/imges/shanj.gif\" />&nbsp;查看绩效</div></td>\r\n  </tr> ");
}else{ 
      out.write("\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">   \r\n      <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/zkyRankAction.do?action=word&nd=");
      out.print(SystemCommon.getZkyNd() );
      out.write("')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\"  src=\"/jsoa/imges/shanj.gif\" />&nbsp;生成绩效</div></td>\r\n  </tr>\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">   \r\n      <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/zkyRankAction.do?nd=");
      out.print(SystemCommon.getZkyNd() );
      out.write("&orgId=150376')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\"  src=\"/jsoa/imges/shanj.gif\" />&nbsp;绩效排名</div></td>\r\n  </tr> ");
}} 
      out.write("\r\n</table>\r\n</td>\r\n  </tr>\r\n</table>     \t  \r\n\r\n\r\n</BODY>\r\n</HTML>\r\n<script src=\"/jsoa/js/imenu.js\"></script>");
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
