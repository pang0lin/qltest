/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:02:39 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.menu;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.Date;
import com.js.system.manager.service.ManagerService;
import java.util.List;
import com.js.oa.routine.resource.service.*;
import com.js.system.util.*;
import com.js.util.config.SystemCommon;

public final class hr_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("com.js.system.util");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_packages.add("com.js.oa.routine.resource.service");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.util.List");
    _jspx_imports_classes.add("java.util.Date");
    _jspx_imports_classes.add("com.js.util.config.SystemCommon");
    _jspx_imports_classes.add("com.js.system.manager.service.ManagerService");
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

String userId = session.getAttribute("userId").toString();
String domainId = session.getAttribute("domainId").toString();

ManagerService managerBD = new ManagerService();

      out.write("\r\n<HTML>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\" />\r\n<title>应用设置</title>\r\n<link href=\"/jsoa/css/menustyle.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n<script src=\"/jsoa/js/imenu.js\"></script>\r\n<style type=\"text/css\">\r\n<!--\r\nbody,table,div{font-size:12px; margin:0px; padding:0px;color:#003366;}\r\nbody{\r\nscrollbar-base-color: #ffffff;\r\nscrollbar-darkshadow-color: #ffffff;\r\nscrollbar-highlight-color: #ffffff;\r\nbackground:url(背景图片地址);\r\noverflow:hidden;\r\nmargin:0;\r\n}\r\ndiv.main{\r\noverflow:scroll;\r\nwidth:500px;\r\nheight:400px;\r\n/*filter: chroma(color=#ffffff);*/\r\n}\r\n*{margin:0;padding:0;}\r\n");
if(com.js.util.util.BrowserJudge.isMSIE(request)){ 
      out.write("\r\n.menuNormal{\r\n\theight:32px; \r\n\twidth:131px; \r\n\tpadding-left:17px; \r\n\tbackground-image:url(/jsoa/imges/left-1.gif); \t\r\n\tcursor:pointer;\r\n}\r\n\r\n.menuFocus{\r\n\theight:32px; \r\n\twidth:131px; \r\n\tpadding-left:17px; \r\n\tbackground-image:url(/jsoa/imges/left-jiaoh.gif);\r\n\tfont-weight:bold;\r\n\tcursor:pointer;\r\n}\r\n");
}
      out.write("\r\n.leftMenuTop{\r\n\tbackground-image:url(/jsoa/imges/left-1.gif);}\r\n\r\n/*.leftMenuTopDIV{\r\n\theight:22px; \r\n\tpadding-top:8px; \r\n\tfloat:left; \r\n\twidth:132px;\r\n\tmargin:0px;\r\n}*/\r\n\r\n.topMenuNormal{\r\n\twidth:60px;\r\n\tcursor:pointer;\r\n}\r\n\r\n.topMenuFocus{\r\n\tbackground-image:url(/jsoa/imges/zi.gif); \r\n\twidth:60px;\r\n\tcursor:pointer;\r\n}\r\n\r\n/*.menuTopTitle{\r\n\tfloat:left;\r\n\tpadding-top:2px;\r\n}\r\n\r\n.menuTopTitleLeft{\r\n\twidth:17px;\r\n\tfloat:left;\r\n\tpadding:0px 2px 0px 7px;\r\n}\r\n\r\n.menuTopTitleRight{\r\n\tpadding-left:2px;\r\n\twidth:17px;\r\n\tfloat:left;\r\n}\r\n*/\r\n.menuScrollDIV{\r\n\tfloat:left; \r\n\tpadding-bottom:2px;\r\n}\r\n\r\n.menuShowHide{\r\n\tbackground-image:url(/jsoa/imges/cent.gif); \r\n\twidth:8px; \r\n\tfloat:left; \r\n\theight:100%; \r\n\tpadding-top:270px;\r\n}\r\n\r\n.portalNormal{\r\n\tbackground-image:url(/jsoa/imges/03.gif);\r\n\tcolor:#000;\r\n\twidth:75px;\r\n\tcursor:pointer;\r\n}\r\n\r\n.portalFocus{\r\n\tbackground-image:url(/jsoa/imges/02.gif);\r\n\tcolor:#000;\r\n\twidth:75px;\r\n\tcursor:pointer;\r\n} \r\n-->\r\n</style>\r\n<body style=\"margin:0px; padding:0px;\" onload=\"resetHeight();initHeight();\"   onresize=\"initHeight();\"> \r\n");
      out.write("<table height=\"100%\" width=\"132\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n  <tr>\r\n    <td height=\"32\">\r\n\t<!--//*头部*-->\r\n\t<table width=\"131\" height=\"32\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n \t\t <tr>\r\n    \t\t<td class=\"leftMenuTop\">\r\n\t\r\n<!--//*left*-->\r\n    <div class=\"leftMenuTopDIV\">\t\r\n\t<div class=\"menuTopTitleLeft\"><img src=\"/jsoa/imges/9s.gif\"></img></div>\r\n\t<div class=\"menuTopTitle\"><b>人事管理</b></div>\r\n\t<div class=\"menuTopTitleRight\"><img src=\"/jsoa/imges/9s.gif\"></img></div>\r\n\t</div>\r\n<!--//*right*-->\r\n\t\r\n    <div class=\"menuScrollDIV\">\r\n        \t\t<table height=\"32\"  width=\"20\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n\t\t\t\t\t  <tr>\r\n\t\t\t\t\t    <td align=\"center\" valign=\"center\"  style=\"padding-top:4px;\">\r\n\t\t\t\t\t       <img id=\"imgUP\" src=\"/jsoa/imges/kzmb.gif\" title=\"切换面板\" style=\"cursor:pointer\" onMouseOver=\"shortFocus_(this);\" onMouseOut=\"shortBlur_(this);\" onClick=\"changePanle(1);\"/>\r\n\t\t\t\t\t    </td>\r\n\t\t\t\t\t    </tr>\t\t\t\t\t\r\n\t\t\t\t\t</table>\r\n       </div>\r\n\t      <!--//*交换按扭*-->\r\n\t   \t\t</td>\r\n  \t\t</tr>\r\n");
      out.write("\t</table>\t \r\n<!--//*结束*-->\r\n\t\r\n\t</td>\r\n  </tr>\r\n  <tr>\r\n    <td>\r\n\t<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"100%\" width=\"132\" style=\"background-image:url(/jsoa/imges/left-cent.gif); background-attachment:fixed;padding:0px; margin:0px; scrollbar-base-color: #ffffff; scrollbar-darkshadow-color: #ffffff; scrollbar-highlight-color: #ffffff; overflow:hidden;\" onLoad=\"focusNode();\">\r\n       <tr id=\"submenuBox0\" valign=\"top\">\r\n        <td>\r\n           <div class=\"main\" id=\"meauDIV\" style=\"width:132px;height:99%;overflow-x:hidden;overflow-y:auto;font-size:12px; background-image:url(\\imges\\left-1.gif)\">   \r\n                <table  width=\"131\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n  ");

  int menuIndex=-1;
  int subMenuIndex=-1;
  if(SystemCommon.getHrUnderling()){
  
      out.write("\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">   \r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/EmployeeAction.do?action=myUnderling')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\"  src=\"/jsoa/imges/shanj.gif\" />&nbsp;我的下属</div></td>\r\n  </tr> \r\n  ");

  }
  if(SystemCommon.getHrOrganization()){
  
      out.write("  \r\n  \r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/office_manager/select_org.jsp')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\"  src=\"/jsoa/imges/shanj.gif\" />&nbsp;组织结构</div></td>\r\n  </tr> \r\n  ");

  }
  if(SystemCommon.getHRmanager()){
      Boolean isHasRyfxRight = managerBD.hasRight(userId, "07*05*01");
      Boolean isHasZpxxRight = managerBD.hasRight(userId, "07*06*01");
      Boolean isHasLdhthz = managerBD.hasRight(userId, "07*15*01");
  
      out.write("\r\n  \r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/EmployeeAction.do?action=view')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;人事信息</div></td>\r\n  </tr>\r\n  ");
 if(isHasRyfxRight && "chinaLife".equals(SystemCommon.getCustomerName())){ 
      out.write("\r\n   <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/LeaderAction.do?action=toLeaderQuery')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;人员分析</div></td>\r\n  </tr>\r\n  ");
}
    if(isHasLdhthz && "chinaLife".equals(SystemCommon.getCustomerName())){
  
      out.write("\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/LdhtAction.do?action=init')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;合同到期汇总</div></td>\r\n  </tr>\r\n   ");
}
   if(isHasZpxxRight && "chinaLife".equals(SystemCommon.getCustomerName())){
   
      out.write("\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa//ResumeAction.do?action=list')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;招聘信息</div></td>\r\n  </tr>\r\n  ");
}
  }
  
  if(SystemCommon.getSalaryQuery()){
      Boolean isHasXcdrRight = managerBD.hasRight(userId, "07*04*01");
      if(isHasXcdrRight && "chinaLife".equals(SystemCommon.getCustomerName())){
  
      out.write("\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n     <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/fSalaryRsNewAction.do?action=view')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;薪酬导入</div></td>\r\n  </tr>\r\n  ");
} 
      out.write("\r\n  \r\n  ");
 if("chinaLife".equals(SystemCommon.getCustomerName())) {
      out.write("\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/finance/user/checkforward.jsp?javaAction=fSalaryRsNewAction&action=showSalary')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;工资查询</div></td>\r\n  </tr>\r\n  \r\n  ");
}else{ 
      out.write("\r\n <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/finance/user/checkforward.jsp?javaAction=fSalaryAction&action=list')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;工资查询</div></td>\r\n  </tr>\r\n\t     <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" > \r\n\t         <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onClick=\"changeFont2('");
      out.print(menuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(subMenuIndex );
      out.write("');jumpMain('/jsoa/finance/user/checkforward.jsp?javaAction=fSalaryAction&action=list')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;工资查询</a></div></td>      \r\n\t    </tr>\r\n\t     <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n\t         <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onClick=\"changeFont2('");
      out.print(menuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(subMenuIndex );
      out.write("');jumpMain('/jsoa/finance/user/checkforward.jsp?javaAction=fExpenseAction&action=list')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;报销查询</a></div></td>      \r\n\t    </tr>\r\n\t     <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" > \r\n\t         <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onClick=\"changeFont2('");
      out.print(menuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(subMenuIndex );
      out.write("');jumpMain('/jsoa/finance/user/checkforward.jsp?javaAction=fTaxAction&action=list')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;计税查询</a></div></td>      \r\n\t    </tr>\r\n\t    <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n\t         <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onClick=\"changeFont2('");
      out.print(menuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(subMenuIndex );
      out.write("');jumpMain('/jsoa/finance/user/checkforward.jsp?javaAction=fPayableAction&action=list')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;其他应付</a></div></td>      \r\n\t    </tr>\r\n\t    \r\n  ");
}
  }
  subMenuIndex=-1;
  if(SystemCommon.getHRExam()){
  Boolean isHseeRight= managerBD.hasRight(userId,"07*40*01");
  Boolean isHasRight= managerBD.hasRight(userId,"07*40*02");
  
  if(isHseeRight||isHasRight){
  
      out.write("\t  \r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/ExaminationSelfTestAction.do?action=add')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;在线考试</div></td>\r\n  </tr>");
//以下只有培训管理-员工自测试 查看权限能看到
  if(isHseeRight){ 
      out.write("\r\n\t  <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n\t         <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onClick=\"changeFont2('");
      out.print(menuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(subMenuIndex );
      out.write("');jumpMain('/jsoa/ExaminationSelfTestAction.do?action=add')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;员工自测</a></div></td>      \r\n\t  </tr>\r\n\t  <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n\t         <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onClick=\"changeFont2('");
      out.print(menuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(subMenuIndex );
      out.write("');jumpMain('/jsoa/ExaminationSelfTestAction.do?action=list')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;自测记录</a></div></td>      \r\n\t  </tr>\r\n\t  <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n\t         <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onClick=\"changeFont2('");
      out.print(menuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(subMenuIndex );
      out.write("');jumpMain('/jsoa/ExaminationMyPaperAction.do?action=list')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;我的试卷</a></div></td>      \r\n\t  </tr> \r\n\t  ");
}//end 培训管理-员工自测试 查看权限
	  //以下是有培训管理-维护权限的用户才能显示
		  if(isHasRight)
		  {
	  
      out.write("\r\n\t\t  <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n\t\t         <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onClick=\"changeFont2('");
      out.print(menuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(subMenuIndex );
      out.write("');jumpMain('/jsoa/ExaminationManageAction.do?action=list')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;考卷管理</a></div></td>      \r\n\t\t  </tr>\r\n\t\t  <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n\t\t         <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onClick=\"changeFont2('");
      out.print(menuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(subMenuIndex );
      out.write("');jumpMain('/jsoa/ExaminationManageAction.do?action=manage')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;答卷管理</a></div></td>      \r\n\t\t  </tr>\r\n\t\t  <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n\t\t         <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onClick=\"changeFont2('");
      out.print(menuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(subMenuIndex );
      out.write("');jumpMain('/jsoa/ExaminationStockAction.do?action=list&sign=1')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;考试题库</a></div></td>      \r\n\t\t  </tr>\r\n\t\t  <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n\t\t         <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onClick=\"changeFont2('");
      out.print(menuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(subMenuIndex );
      out.write("');jumpMain('/jsoa/ExaminationStockAction.do?action=list&sign=0')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;自测题库</a></div></td>      \r\n\t\t  </tr>\r\n\t ");

	 	  }//end 培训管理-维护权限
	 }
  }
  if(SystemCommon.getHRmanager()){
	if(managerBD.hasRightType(userId, "人事管理-统计报表")){ 
      out.write("\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/EmpStatisticsAction.do?action=statistics')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;统计报表</div></td>\r\n  </tr>\r\n  ");
}
  }
  if(SystemCommon.getHrMyinfo()){
  
      out.write("\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/EmployeeAction.do?action=personCard&hasRight=true&empId=");
      out.print(userId);
      out.write("')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;我的信息</div></td>\r\n  </tr>\r\n  ");
 
  }
  if("shenzhougaotie".equals(SystemCommon.getCustomerName())) {
	  
      out.write("\t\r\n      <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n         <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/KqDataImportAcion.do?action=kqdatalist');\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;打卡信息</div></td>\r\n      </tr>\r\n\r\n   <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n         <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/SKqReportAction.do?action=list');\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;考勤汇总</div></td>\r\n      </tr>\r\n      <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n         <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/SKqHolidayStatAction.do?action=list');\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;请假查询</div></td>\r\n      </tr>\r\n\t  ");

  }else{
	  
  
  subMenuIndex=-1;
  boolean readAnonymous=managerBD.hasRight(session.getAttribute("userId").toString(),"07*55*09");//人事管理-考勤维护
  boolean importKq = managerBD.hasRight(session.getAttribute("userId").toString(),"07*55*11");//考勤导入
  //boolean kqView = managerBD.hasRight(session.getAttribute("userId").toString(),"07*55*06");//考勤记录
  if(SystemCommon.getCheckon()){
      out.write("\r\n   \r\n   ");
if("chinaLife".equals(SystemCommon.getCustomerName())){ 
      out.write("\r\n   <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;考勤情况</div></td>\r\n    <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n      <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onClick=\"jumpMain('/jsoa/kqShow.do?action=kqShow&userId=");
      out.print(session.getAttribute("userId") );
      out.write("')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;考勤统计</a></div>\r\n      </td></tr>\r\n    ");
boolean renshi=managerBD.hasRight(session.getAttribute("userId").toString(),"07*55*12");//人事管理-考勤维护 
    if(renshi){ 
      out.write("\r\n    <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n      <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onClick=\"jumpMain('/jsoa/kqShow.do?action=kqMeal&userId=");
      out.print(session.getAttribute("userId") );
      out.write("')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;餐补统计</a></div>\r\n      </td>      \r\n  </tr>\r\n  <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n      <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onClick=\"jumpMain('/jsoa/kqShow.do?action=buqian&userId=");
      out.print(session.getAttribute("userId") );
      out.write("')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;补签查询</a></div>\r\n      </td>  \r\n  </tr>");
}
  boolean ldright=managerBD.hasRight(session.getAttribute("userId").toString(),"07*55*16");//人事管理-部门考勤权限
  if(ldright){ 
      out.write("\r\n  <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n      <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onClick=\"jumpMain('/jsoa/kqShow.do?action=bumen&year=");
      out.print(new Date().getYear()+1900 );
      out.write("&month=");
      out.print((new Date().getMonth()+1) );
      out.write("')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;部门考勤</a></div>\r\n      </td>  \r\n  </tr>");
}
  if(false){ 
      out.write("\r\n  <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n      <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onClick=\"jumpMain('/jsoa/kqShow.do?action=lingdao&year=");
      out.print(new Date().getYear()+1900 );
      out.write("&month=");
      out.print((new Date().getMonth()+1) );
      out.write("')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;领导请假查询</a></div>\r\n      </td>  \r\n  </tr>\r\n  ");
}
  subMenuIndex=-1; 
      out.write("\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;请假情况</div></td>\r\n   </tr>\r\n   <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n      <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onClick=\"jumpMain('/jsoa/kqShow.do?action=kqQing&userId=");
      out.print(session.getAttribute("userId") );
      out.write("&year=");
      out.print(new Date().getYear()+1900 );
      out.write("&month=");
      out.print((new Date().getMonth()+1) );
      out.write("');\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;请假统计</a></div>\r\n      </td>      \r\n  </tr>\r\n  ");
if(renshi){ 
      out.write("\r\n  <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n      <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onClick=\"jumpMain('/jsoa/kqShow.do?action=kqNian&year=");
      out.print(new Date().getYear()+1900 );
      out.write("')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;年假统计</a></div>\r\n      </td>      \r\n  </tr>\r\n  <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n      <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onClick=\"jumpMain('/jsoa/kqShow.do?action=kouxin&userId=");
      out.print(session.getAttribute("userId") );
      out.write("&year=");
      out.print(new Date().getYear()+1900 );
      out.write("&month=");
      out.print((new Date().getMonth()+1) );
      out.write("')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;扣薪统计</a></div>\r\n      </td>      \r\n  </tr>\t\r\n    ");
}}else{ 
      out.write("\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/KqStatAction.do?action=list');\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;考勤统计</div>\r\n    </td>\r\n  </tr>\t\r\n    ");
}
    boolean lingdaoquanxian=managerBD.hasRight(session.getAttribute("userId").toString(),"07*55*15");//人事管理-领导考勤 维护
    if(lingdaoquanxian){ 
      out.write("\r\n  <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n      <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onClick=\"jumpMain('/jsoa/kqShow.do?action=ldqj&year=");
      out.print(new Date().getYear()+1900 );
      out.write("&month=");
      out.print((new Date().getMonth()+1) );
      out.write("')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;领导请假查询</a></div>\r\n      </td>  \r\n  </tr>\r\n  ");
}
if(readAnonymous) {
  if(importKq && !"chinaLife".equals(SystemCommon.getCustomerName())){ 
      out.write("\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');window.open('/jsoa/kq/kq_import.jsp');\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;考勤导入</div></td>\r\n  </tr>\t\r\n  ");
}}}
  if(SystemCommon.getWxkjKq()==1){
  subMenuIndex = -1; 
      out.write("\r\n<tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/WxKqAction.do?action=day');\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;玩蟹考勤</div></td>\r\n  </tr>\t\r\n  <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n      <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onClick=\"jumpMain('/jsoa/WxKqAction.do?action=day')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;考勤查看</a></div>\r\n      </td>      \r\n  </tr>\r\n  <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n      <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onClick=\"jumpMain('/jsoa/WxKqAction.do?action=day&leader=1')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;下属考勤</a></div>\r\n      </td>  \r\n  </tr>\r\n  ");
}
  //微信打卡查询
  boolean a = managerBD.hasRight(session.getAttribute("userId").toString(),"07*55*26");
  subMenuIndex = -1;
  if(a){
  	
      out.write("\r\n  \t<tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n\t    <td>\r\n\t    \t<div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/kqCheckinInfoAction.do?action=list');\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;微信查询</div>\r\n\t    </td>\r\n    </tr>\r\n    <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" > \r\n\t        <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onClick=\"changeFont2('");
      out.print(menuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(subMenuIndex );
      out.write("');jumpMain('/jsoa/kqCheckinInfoAction.do?action=list');\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;上下班查询</a></div></td>      \r\n\t</tr>\r\n     <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n         <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onClick=\"changeFont2('");
      out.print(menuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(subMenuIndex );
      out.write("');jumpMain('/jsoa/kqCheckinInfoAction.do?action=outsidelist');\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;外勤打卡查询</a></div></td>      \r\n    </tr>\r\n     <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n         <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onClick=\"changeFont2('");
      out.print(menuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(subMenuIndex );
      out.write("');jumpMain('/jsoa/kqCheckinInfoAction.do?action=kqquery');\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;考勤统计</a></div></td>      \r\n    </tr>\r\n  \t");

  }
   
      out.write("\r\n  \r\n   ");
if(managerBD.hasRight(userId, "07*55*55")){ 
      out.write("\r\n      <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n         <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/bhwKqAction.do?action=cqqk&isPersonal=0');parent.sAlert('考勤数据加载中。。。');\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;考勤查询</div></td>\r\n      </tr>\r\n   ");
} 
      out.write("\r\n  \r\n   ");
 if("bhw".equals(SystemCommon.getCustomerName())){ 
      out.write("\r\n      <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n         <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/bhwKqAction.do?action=cqqk&isPersonal=1');parent.sAlert('考勤数据加载中。。。');\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;我的考勤</div></td>\r\n      </tr>\r\n    ");
} 
      out.write("  \r\n   ");
}//end of fei zhenzhougaotei 
      out.write("\r\n</table>\r\n           </div>\r\n\t</td>\r\n  </tr>  \r\n</table></td>\r\n  </tr>\r\n</table>     \t  \r\n\r\n\r\n</BODY>\r\n</HTML>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\nfunction resetHeight(){\r\n \r\n\tif(navigator.userAgent.indexOf(\"Firefox\")!=-1)//火狐\r\n\t{\r\n\t\tdocument.getElementById(\"meauDIV\").style.height=\"279px\";\r\n\t}\r\n\tif(navigator.userAgent.indexOf(\"MSIE 10.0\")!=-1)//IE10\r\n\t{  \r\n\t\tdocument.getElementById(\"meauDIV\").style.height=\"278px\";\r\n\t}\r\n}\r\n</SCRIPT>\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n<script src=\"/jsoa/js/imenu.js\"></script>\r\n");
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
