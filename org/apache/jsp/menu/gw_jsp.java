/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:02:55 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.menu;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.util.config.SystemCommon;
import com.js.doc.doc.service.SenddocumentBD;
import com.js.doc.doc.service.ReceivedocumentBD;
import java.util.*;
import com.js.system.manager.service.ManagerService;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.service.WorkFlowBD;

public final class gw_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("com.js.doc.doc.service.ReceivedocumentBD");
    _jspx_imports_classes.add("com.js.doc.doc.service.SenddocumentBD");
    _jspx_imports_classes.add("com.js.util.config.SystemCommon");
    _jspx_imports_classes.add("com.js.oa.jsflow.service.ProcessBD");
    _jspx_imports_classes.add("com.js.system.manager.service.ManagerService");
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

String userId = session.getAttribute("userId").toString();
String orgIdString = session.getAttribute("orgIdString").toString();


//得到收文处理的链接
//String fwlink = request.getAttribute("fwlink")+"";

//得到发文部分的列表 也是为了得到对应的链接
List receivefilelist=null;
List sendfilelist=null;
//List sendfilechecklist = (List)request.getAttribute("sendfilechecklist") ;
//List redHeadList = (List)request.getAttribute("redHeadList") ;


    //得到对应的收文链接
    WorkFlowBD wfbd = new WorkFlowBD();

    //列出所有的流程发文
    ProcessBD procbd = new ProcessBD();   

    Object tmp;
    tmp = procbd.getUserProcess(userId, orgIdString, "3");
    if (null != tmp) {
      receivefilelist = (List) tmp;
    }
    
    tmp = procbd.getUserProcess(userId, orgIdString, "2");
    if (null != tmp) {
      sendfilelist = (List) tmp;
    }

Object [] baseObj=(Object[]) new SenddocumentBD().getSenddocumentBaseInfo();
String [] numObj=null;
if(baseObj!=null&&baseObj[6]!=null&&!baseObj[6].toString().equals("")){
    numObj=baseObj[6].toString().split(";");
}


Object[] reseObj=(Object[]) new ReceivedocumentBD().getReceivedocumentBaseInfo();

String [] seqObj=null;
if(reseObj!=null&&reseObj[7]!=null&&!reseObj[7].toString().equals("")){
    seqObj=reseObj[7].toString().split(";");
}

int openMenuID=0;

ManagerService mbd = new ManagerService();
boolean  signatureRight=false;//印章 维护

if(mbd.hasRight(session.getAttribute("userId").toString(),"03*15*86")){
signatureRight=true;
}

      out.write("\r\n\r\n\r\n<HTML>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\" />\r\n<title>公文管理</title>\r\n<link href=\"/jsoa/css/menustyle.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n<script src=\"/jsoa/js/imenu.js\"></script>\r\n<style type=\"text/css\">\r\n<!--\r\nbody,table,div{font-size:12px; margin:0px; padding:0px;color:#003366;}\r\nbody{\r\nscrollbar-base-color: #ffffff;\r\nscrollbar-darkshadow-color: #ffffff;\r\nscrollbar-highlight-color: #ffffff;\r\nbackground:url(背景图片地址);\r\noverflow:hidden;\r\nmargin:0;\r\n}\r\ndiv.main{\r\noverflow:scroll;\r\nwidth:500px;\r\nheight:400px;\r\n/*filter: chroma(color=#ffffff);*/\r\n}\r\n*{margin:0;padding:0;}\r\n");
if(com.js.util.util.BrowserJudge.isMSIE(request)){ 
      out.write("\r\n.menuNormal{\r\n\theight:32px; \r\n\twidth:131px; \r\n\tpadding-left:17px; \r\n\tbackground-image:url(/jsoa/imges/left-1.gif); \t\r\n\tcursor:pointer;\r\n}\r\n\r\n.menuFocus{\r\n\theight:32px; \r\n\twidth:131px; \r\n\tpadding-left:17px; \r\n\tbackground-image:url(/jsoa/imges/left-jiaoh.gif);\r\n\tfont-weight:bold;\r\n\tcursor:pointer;\r\n}\r\n");
}
      out.write("\r\n.leftMenuTop{\r\n\tbackground-image:url(/jsoa/imges/left-1.gif);}\r\n\r\n/*.leftMenuTopDIV{\r\n\theight:22px; \r\n\tpadding-top:8px; \r\n\tfloat:left; \r\n\twidth:132px;\r\n\tmargin:0px;\r\n}*/\r\n\r\n.topMenuNormal{\r\n\twidth:60px;\r\n\tcursor:pointer;\r\n}\r\n\r\n.topMenuFocus{\r\n\tbackground-image:url(/jsoa/imges/zi.gif); \r\n\twidth:60px;\r\n\tcursor:pointer;\r\n}\r\n\r\n/*.menuTopTitle{\r\n\tfloat:left;\r\n\tpadding-top:2px;\r\n}\r\n\r\n.menuTopTitleLeft{\r\n\twidth:17px;\r\n\tfloat:left;\r\n\tpadding:0px 2px 0px 7px;\r\n}\r\n\r\n.menuTopTitleRight{\r\n\tpadding-left:2px;\r\n\twidth:17px;\r\n\tfloat:left;\r\n}\r\n*/\r\n.menuScrollDIV{\r\n\tfloat:left; \r\n\tpadding-bottom:2px;\r\n}\r\n\r\n.menuShowHide{\r\n\tbackground-image:url(/jsoa/imges/cent.gif); \r\n\twidth:8px; \r\n\tfloat:left; \r\n\theight:100%; \r\n\tpadding-top:270px;\r\n}\r\n\r\n.portalNormal{\r\n\tbackground-image:url(/jsoa/imges/03.gif);\r\n\tcolor:#000;\r\n\twidth:75px;\r\n\tcursor:pointer;\r\n}\r\n\r\n.portalFocus{\r\n\tbackground-image:url(/jsoa/imges/02.gif);\r\n\tcolor:#000;\r\n\twidth:75px;\r\n\tcursor:pointer;\r\n} \r\n-->\r\n</style>\r\n<body style=\"margin:0px; padding:0px;\" onload=\"initHeight();\" onresize=\"initHeight();\"> \r\n");
      out.write("<table height=\"100%\" width=\"132\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n  <tr>\r\n    <td height=\"32\">\r\n\t<!--//*头部*-->\r\n\t<table width=\"131\" height=\"32\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n \t\t <tr>\r\n    \t\t<td class=\"leftMenuTop\">\r\n\t\r\n<!--//*left*-->\r\n    <div class=\"leftMenuTopDIV\">\t\r\n\t<div class=\"menuTopTitleLeft\"><img src=\"/jsoa/imges/9s.gif\"></img></div>\r\n\t<div class=\"menuTopTitle\"><b>公文管理</b></div>\r\n\t<div class=\"menuTopTitleRight\"><img src=\"/jsoa/imges/9s.gif\"></img></div>\r\n\t</div>\r\n<!--//*right*-->\r\n\t\r\n    <div class=\"menuScrollDIV\">\r\n        \t\t<table height=\"32\"  width=\"20\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n\t\t\t\t\t  <tr>\r\n\t\t\t\t\t    <td align=\"center\" valign=\"center\"  style=\"padding-top:4px;\">\r\n\t\t\t\t\t       <img id=\"imgUP\" src=\"/jsoa/imges/kzmb.gif\" title=\"切换面板\" style=\"cursor:pointer\" onMouseOver=\"shortFocus_(this);\" onMouseOut=\"shortBlur_(this);\" onClick=\"changePanle(1);\"/>\r\n\t\t\t\t\t    </td>\r\n\t\t\t\t\t    </tr>\t\t\t\t\t\r\n\t\t\t\t\t</table>\r\n       </div>\r\n\t      <!--//*交换按扭*-->\r\n\t   \t\t</td>\r\n  \t\t</tr>\r\n");
      out.write("\t</table>\t \r\n<!--//*结束*-->\r\n\t\r\n\t</td>\r\n  </tr>\r\n  <tr>\r\n    <td>\r\n\t\t<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"100%\" width=\"132\" style=\"background-image:url(/jsoa/imges/left-cent.gif); background-attachment:fixed;padding:0px; margin:0px; scrollbar-base-color: #ffffff; scrollbar-darkshadow-color: #ffffff; scrollbar-highlight-color: #ffffff; overflow:hidden;\" onLoad=\"focusNode();\">\r\n       \t\t<tr id=\"submenuBox0\" valign=\"top\">\r\n\t\t        <td>\r\n\t\t           <div class=\"main\" id=\"meauDIV\" style=\"width:132px;height:260px;overflow-x:hidden;overflow-y:auto;font-size:12px; background-image:url(\\imges\\left-1.gif)\">   \r\n\t\t                <table  width=\"131\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n\t  \t\t\t\t\t  ");

						  int menuIndex=-1;
						  int subMenuIndex=-1;
						  if(!"1".equals(SystemCommon.getLZGOVSplit())){
						  
      out.write("\r\n\t\t\t\t\t\t  <!--山东国投隐藏我的收文  -->\r\n\t\t\t\t\t\t  ");
if(!"shandongguotou".equals(com.js.util.config.SystemCommon.getCustomerName())){ 
      out.write("\r\n\t\t\t\t\t\t   <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">   \r\n\t\t\t\t\t\t    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/GovReceiveFileBoxAction.do?action=list')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\"  src=\"/jsoa/imges/shanj.gif\" />&nbsp;我的收文</div></td>\r\n\t\t\t\t\t\t  </tr>  \r\n\t\t\t\t\t\t  ");
} 
      out.write(" \r\n\t\t\t\t\t\t  \r\n\t\t\t\t\t\t  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n\t\t\t\t\t\t    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/doc/doc/workflow_list_sendfile.jsp')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\"  src=\"/jsoa/imges/shanj.gif\" />&nbsp;发文拟稿</div></td>\r\n\t\t\t\t\t\t  </tr> \r\n\t\t\t\t\t\t  \r\n\t\t\t\t\t\t  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n\t\t\t\t\t\t    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/GovSendFileDraftBoxAction.do?action=list')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;发文草稿</div></td>\r\n\t\t\t\t\t\t  </tr> \r\n\t\t\t\t\t\t  ");
    
						   }  
						      if(mbd.hasRight(userId, "03*01")){
						        if(!"1".equals(SystemCommon.getLZGOVSplit())){
						      
      out.write("\r\n\t\t\t\t\t\t   <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n\t\t\t\t\t\t    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/GovSendFileAction.do?action=list')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;发文查阅</div></td>\r\n\t\t\t\t\t\t  </tr>\r\n\t\t\t\t\t\t  ");

						     }
						   }
						  
      out.write("\r\n\t\t\t\t\t\t  <!--山东国投添加我经手的发文  -->\r\n\t\t\t\t\t\t  ");
if("shandongguotou".equals(com.js.util.config.SystemCommon.getCustomerName())){ 
      out.write("\r\n\t\t\t\t\t\t   <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">   \r\n\t\t\t\t\t\t    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/GovSendFileAction.do?action=mylist')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\"  src=\"/jsoa/imges/shanj.gif\" />&nbsp;我的发文</div></td>\r\n\t\t\t\t\t\t  </tr>  \r\n\t\t\t\t\t\t  ");
} 
      out.write("\r\n\t\t\t\t\t\t  \r\n\t\t\t\t\t\t  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n\t\t\t\t\t\t    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/doc/doc/workflow_list_receivefile.jsp')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;签收公文</div></td>\r\n\t\t\t\t\t\t  </tr>\r\n\t\t\t\t\t\t  \t\t\t\t\t\t  \r\n\t\t\t\t\t\t  ");

						  //2016-09-08 去掉国资委标识 合并到标准产品
							//String projectType = this.getServletContext().getInitParameter("projectType");
							//if(true){//是天津国资委项目 增加 待发收文
							
						  
      out.write("\t\r\n\t\t\t\t\t\t\t  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n\t\t\t\t\t\t\t    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/TjgzwAction.do?type=list_gzw_receivfile_draf')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;收文草稿</div></td>\r\n\t\t\t\t\t\t\t  </tr>\r\n\t\t\t\t\t\t  ");
//}
							if(mbd.hasRight(userId, "03*05")) {
						  
      out.write("\r\n\t\t\t\t\t\t  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n\t\t\t\t\t\t    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/GovReceiveFileAction.do?action=list')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;收文查阅</div></td>\r\n\t\t\t\t\t\t  </tr>\r\n\t\t\t\t\t\t  ");
}
						  //山东国投添加我经手的收文  -->
						  if("shandongguotou".equals(com.js.util.config.SystemCommon.getCustomerName())){ 
      out.write("\r\n\t\t\t\t\t\t   <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">   \r\n\t\t\t\t\t\t    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/GovReceiveFileAction.do?action=mylist')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\"  src=\"/jsoa/imges/shanj.gif\" />&nbsp;我的收文</div></td>\r\n\t\t\t\t\t\t  </tr>  \r\n\t\t\t\t\t\t  ");
} 
						  if("chongqingshangtou".equals(SystemCommon.getCustomerName())){
						   
      out.write("\r\n\t\t\t\t\t\t  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n\t\t\t\t\t\t    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/ChartReceivefileAction.do?action=list')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;收文统计</div></td>\r\n\t\t\t\t\t\t  </tr>\r\n\t\t\t\t\t\t  ");
}
							if(mbd.hasRight(userId, "03*01*03")) {
						  
      out.write("\r\n\t\t\t\t\t\t  ");
if("1".equals(SystemCommon.getLZGOVSplit())){ 
      out.write("\r\n\t\t\t\t\t\t  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n\t\t\t\t\t\t    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/GovFileTransAction.do?workStatus=0&tran=1&type=1')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;督办转办</div></td>\r\n\t\t\t\t\t\t  </tr>\r\n\t\t\t\t\t\t  ");
}else{ 
      out.write("\r\n\t\t\t\t\t\t  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n\t\t\t\t\t\t    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/GovFileTransAction.do?workStatus=0&tran=1')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;督办转办</div></td>\r\n\t\t\t\t\t\t  </tr>\r\n\t\t\t\t\t\t  \r\n\t\t\t\t\t\t  ");
} 
      out.write("\r\n\t\t\t\t\t\t  \r\n\t\t\t\t\t\t  ");
} 
						  if(mbd.hasRight(userId, "03*07*01")){
      out.write("\r\n\t\t\t\t\t\t   <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n\t\t\t\t\t\t    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onclick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/businessShowAction.do?action=documentShow')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\" src=\"/jsoa/imges/shanj.gif\" />&nbsp;收文同步</div></td>\r\n\t\t\t\t\t\t  </tr>\r\n\t\t\t\t\t\t  ");
 subMenuIndex=-1; 
      out.write("  \r\n\t\t\t\t\t\t  <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n\t\t\t\t\t\t         <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onclick=\"changeFont2('");
      out.print(menuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(subMenuIndex );
      out.write("');dcqTongBu('dcq','dcq')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;信息获取</a></div></td>      \r\n\t\t\t\t\t\t  </tr>\r\n\t\t\t\t\t\t  <tr id=\"subTR_");
      out.print(menuIndex );
      out.write('_');
      out.print(++subMenuIndex );
      out.write("\" style=\"display:none;\" bgcolor=\"#EBEBEB\" >\r\n\t\t\t\t\t\t         <td height=\"22\"><div style=\"padding-left:25px;\"><a style=\"cursor:pointer\" onclick=\"changeFont2('");
      out.print(menuIndex );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(subMenuIndex );
      out.write("');jumpMain('/jsoa/businessShowAction.do?action=documentShow')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;收文列表</a></div></td>      \r\n\t\t\t\t\t\t  </tr>\r\n\t\t\t\t\t\t  ");
}
      out.write("\t\t\r\n\t\t\t\t\t\t</table>\r\n\t\t\t\t   </div>\r\n\t\t\t\t</td>\r\n  \t\t\t</tr>\r\n\t\t</table>\r\n</BODY>\r\n<script type=\"text/javascript\">\r\n\r\nfunction dcqTongBu(type,name){\r\n    var str=\"数据获取中，请稍后！\";\r\n    sAlert(str);\r\n    if(window.XMLHttpRequest){\r\n        xmlhttp = new XMLHttpRequest();\r\n        if(xmlhttp.overrideMimeType){\r\n            xmlhttp.overrideMimeType(\"text/xml\");\r\n        }\r\n    }else if(window.ActiveXObject){\r\n    \tvar activexName = [\"MSXML2.XMLHTTP\",\"Microsoft.XMLHTTP\"];\r\n        for(var i = 0; i < activexName.length; i++) {\r\n            try{\r\n                xmlhttp = new ActiveXObject(activexName[i]);\r\n                break;\r\n            }catch(e){\r\n            }\r\n        }\r\n    }\r\n    xmlhttp.onreadystatechange = callback;\r\n    try{\r\n        xmlhttp.open(\"GET\",\"/jsoa/businessShowAction.do?action=getMessage&date=\"+new Date(),true);\r\n        xmlhttp.send(null);\r\n    }catch(e){\r\n        alert(e);\r\n    }\r\n}\r\n\r\nfunction callback() {\r\n    \r\n    if (xmlhttp.readyState == 4) {\r\n        if (xmlhttp.status == 200) {\r\n          var responseText = xmlhttp.responseText;\r\n");
      out.write("           responseText = responseText.replace(/\\s+/, '');\r\n           removeObj();\r\n           if(responseText==\"success\"){\r\n              alert(\"数据获取完成！\");\r\n              window.parent.MainDesktop.location.reload();\r\n           }else {\r\n        \t  alert(\"数据获取失败,请检查对应的WEBSERVICE是否正确！\");\r\n           }\r\n        } else {\r\n            removeObj()\r\n            alert(\"数据获取失败!\");\r\n        }\r\n    }\r\n}\r\n//////////////////////////////////////////////////////////////////\r\n function   sAlert(str){ \r\n   var   msgw,msgh,bordercolor; \r\n    msgw=400;//提示窗口的宽度 \r\n    msgh=100;//提示窗口的高度 \r\n    titleheight=25   //提示窗口标题高度 \r\n    bordercolor=\"#336699\";//提示窗口的边框颜色 \r\n    titlecolor=\"#99CCFF\";//提示窗口的标题颜色 \r\n    var   sWidth,sHeight; \r\n    sWidth=parent.document.body.offsetWidth;//浏览器工作区域内页面宽度 \r\n    sHeight=screen.height;//屏幕高度（垂直分辨率） \r\n    //背景层（大小与窗口有效区域相同，即当弹出对话框时，背景显示为放射状透明灰色） \r\n    var   bgObj=parent.document.createElement(\"div\");//创建一个div对象（背景层） \r\n    //定义div属性，即相当于 \r\n    bgObj.setAttribute(\"id\",\"bgDiv\");\r\n    bgObj.style.position=\"absolute\"; \r\n");
      out.write("    bgObj.style.top=\"0\"; \r\n    bgObj.style.background=\"#777\"; \r\n    bgObj.style.filter=\"progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75\"; \r\n    bgObj.style.opacity=\"0.6\"; \r\n    bgObj.style.left=\"0\"; \r\n    bgObj.style.width=sWidth   +  \"px\"; \r\n    bgObj.style.height=sHeight   +  \"px\"; \r\n    bgObj.style.zIndex   =  \"10000\"; \r\n    parent.document.body.appendChild(bgObj);//在body内添加该div对象 \r\n    var   msgObj=parent.document.createElement(\"div\")//创建一个div对象（提示框层） \r\n    //定义div属性，即相当于 \r\n    msgObj.setAttribute(\"id\",\"msgDiv\"); \r\n    msgObj.setAttribute(\"align\",\"center\"); \r\n    msgObj.style.background=\"white\"; \r\n    msgObj.style.border=\"1px   solid  \"   +   bordercolor; \r\n    msgObj.style.position   =  \"absolute\"; \r\n    msgObj.style.left   =  \"50%\"; \r\n    msgObj.style.top   =  \"50%\"; \r\n    msgObj.style.font=\"12px/1.6em   Verdana,   Geneva,   Arial,   Helvetica,   sans-serif\"; \r\n    msgObj.style.marginLeft   =  \"-225px\"   ; \r\n    msgObj.style.marginTop   =   -75+parent.document.documentElement.scrollTop+\"px\"; \r\n");
      out.write("    msgObj.style.width   =   msgw   +  \"px\"; \r\n    msgObj.style.height   =msgh   +  \"px\"; \r\n    msgObj.style.textAlign   =  \"center\"; \r\n    msgObj.style.lineHeight   =\"25px\"; \r\n    msgObj.style.zIndex   =  \"10001\"; \r\n    parent.document.body.appendChild(msgObj);\r\n     \r\n     \r\n    var   txt=parent.document.createElement(\"p\");//创建一个p对象（提示框提示信息） \r\n    //定义p的属性，即相当于 \r\n    txt.style.margin=\"1em   0\" \r\n    txt.setAttribute(\"id\",\"msgTxt\"); \r\n    txt.innerHTML=str;//来源于函数调用时的参数值  \r\n     msgObj.appendChild(txt);\r\n    var msgIMG=parent.document.createElement(\"IMG\") \r\n     msgIMG.src=\"/jsoa/iSignatureHTML.jsp/load.gif\";\r\n     msgIMG.setAttribute(\"id\",\"msgIMG\"); \r\n     msgObj.appendChild(msgIMG);\r\n    \r\n \r\n} \r\nfunction removeObj(){ //点击标题栏触发的事件 \r\n   parent.document.body.removeChild(parent.document.getElementById(\"bgDiv\"));//删除背景层Div \r\n   parent.document.body.removeChild(parent.document.getElementById(\"msgDiv\"));//删除提示框层 \r\n } \r\n</script>\r\n\r\n</HTML>\r\n<script src=\"/jsoa/js/imenu.js\"></script>");
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