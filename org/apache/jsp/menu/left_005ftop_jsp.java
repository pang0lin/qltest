/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:02:54 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.menu;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.util.config.SystemCommon;
import com.js.system.manager.service.ManagerService;
import java.util.*;
import com.js.oa.routine.resource.service.*;
import com.js.oa.webmail.util.WebMailAccManager;
import com.js.util.util.BrowserJudge;

public final class left_005ftop_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("com.js.oa.routine.resource.service");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.util.config.SystemCommon");
    _jspx_imports_classes.add("com.js.oa.webmail.util.WebMailAccManager");
    _jspx_imports_classes.add("com.js.util.util.BrowserJudge");
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
String empid=session.getAttribute("userId").toString();
String tempEmpid="1974";//鹏驰董事长的id,作为办理中心菜单筛选使用
String year,month,day;
year=request.getParameter("Year")==null?(request.getAttribute("Year")==null?"":request.getAttribute("Year").toString()):request.getParameter("Year");
month=request.getParameter("Month")==null?(request.getAttribute("Month")==null?"":request.getAttribute("Month").toString()):request.getParameter("Month");
day=request.getParameter("Day")==null?(request.getAttribute("Day")==null?"":request.getAttribute("Day").toString()):request.getParameter("Day");
if(year == null || "".equals(year)){
	Date d = new Date();
	year = String.valueOf(d.getYear()+1900);
	month = String.valueOf(d.getMonth() + 1);
	day = String.valueOf(d.getDate());
}

      out.write("\r\n<HTML>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\" />\r\n<title>办理中心</title>\r\n<link href=\"/jsoa/css/menustyle.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n<script src=\"/jsoa/js/imenu.js\"></script>\r\n<style type=\"text/css\">\r\n<!--\r\nbody,table,div{font-size:12px; margin:0px; padding:0px;color:#003366;}\r\nbody{\r\nscrollbar-base-color: #ffffff;\r\nscrollbar-darkshadow-color: #ffffff;\r\nscrollbar-highlight-color: #ffffff;\r\nbackground:url(背景图片地址);\r\noverflow:hidden;\r\nmargin:0;\r\n}\r\ndiv.main{\r\noverflow:scroll;\r\nwidth:500px;\r\nheight:400px;\r\n/*filter: chroma(color=#ffffff);*/\r\n}\r\n*{margin:0;padding:0;}\r\n");
if(com.js.util.util.BrowserJudge.isMSIE(request)){ 
      out.write("\r\n.menuNormal{\r\n\theight:32px; \r\n\twidth:131px; \r\n\tpadding-left:17px; \r\n\tbackground-image:url(/jsoa/imges/left-1.gif); \t\r\n\tcursor:pointer;\r\n}\r\n\r\n.menuFocus{\r\n\theight:32px; \r\n\twidth:131px; \r\n\tpadding-left:17px; \r\n\tbackground-image:url(/jsoa/imges/left-jiaoh.gif);\r\n\tfont-weight:bold;\r\n\tcursor:pointer;\r\n}\r\n");
} 
      out.write("\r\n.leftMenuTop{\r\n\tbackground-image:url(/jsoa/imges/left-1.gif);}\r\n\r\n/*.leftMenuTopDIV{\r\n\theight:22px; \r\n\tpadding-top:8px; \r\n\tfloat:left; \r\n\twidth:132px;\r\n\tmargin:0px;\r\n}*/\r\n\r\n.topMenuNormal{\r\n\twidth:60px;\r\n\tcursor:pointer;\r\n}\r\n\r\n.topMenuFocus{\r\n\tbackground-image:url(/jsoa/imges/zi.gif); \r\n\twidth:60px;\r\n\tcursor:pointer;\r\n}\r\n\r\n/*.menuTopTitle{\r\n\tfloat:left;\r\n\tpadding-top:2px;\r\n}\r\n\r\n.menuTopTitleLeft{\r\n\twidth:17px;\r\n\tfloat:left;\r\n\tpadding:0px 2px 0px 7px;\r\n}\r\n\r\n.menuTopTitleRight{\r\n\tpadding-left:2px;\r\n\twidth:17px;\r\n\tfloat:left;\r\n}\r\n*/\r\n.menuScrollDIV{\r\n\tfloat:left; \r\n\tpadding-bottom:2px;\r\n}\r\n\r\n.menuShowHide{\r\n\tbackground-image:url(/jsoa/imges/cent.gif); \r\n\twidth:8px; \r\n\tfloat:left; \r\n\theight:100%; \r\n\tpadding-top:270px;\r\n}\r\n\r\n.portalNormal{\r\n\tbackground-image:url(/jsoa/imges/03.gif);\r\n\tcolor:#000;\r\n\twidth:75px;\r\n\tcursor:pointer;\r\n}\r\n\r\n.portalFocus{\r\n\tbackground-image:url(/jsoa/imges/02.gif);\r\n\tcolor:#000;\r\n\twidth:75px;\r\n\tcursor:pointer;\r\n} \r\n-->\r\n</style>\r\n<body style=\"margin:0px; padding:0px;\" onload=\"resetHeight();initHeight();\" onresize=\"initHeight();\"> \r\n");
      out.write("<table height=\"100%\" width=\"132\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n  <tr>\r\n    <td height=\"32\">\r\n\t<!--//*头部*-->\r\n\t<table width=\"131\" height=\"32\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n \t\t <tr>\r\n    \t\t<td class=\"leftMenuTop\">\r\n\t\r\n<!--//*left*-->\r\n    <div class=\"leftMenuTopDIV\">\t\r\n\t<div class=\"menuTopTitleLeft\"><img src=\"/jsoa/imges/9s.gif\" title=\"切换面板\"></img></div>\r\n\t<div class=\"menuTopTitle\"><b>办理中心</b></div>\r\n\t<div class=\"menuTopTitleRight\"><img src=\"/jsoa/imges/9s.gif\" title=\"切换面板\"></img></div>\r\n\t</div>\r\n<!--//*right*-->\r\n\t\r\n    <div class=\"menuScrollDIV\">\r\n        \t\t<table height=\"32\"  width=\"20\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n\t\t\t\t\t  <tr>\r\n\t\t\t\t\t    <td align=\"center\" valign=\"center\"  style=\"padding-top:4px;\">\r\n\t\t\t\t\t       <img id=\"imgUP\" src=\"/jsoa/imges/kzmb.gif\" title=\"切换面板\" style=\"cursor:pointer\" onMouseOver=\"shortFocus_(this);\" onMouseOut=\"shortBlur_(this);\" onClick=\"changePanle(0);\"/>\r\n\t\t\t\t\t    </td>\r\n\t\t\t\t\t    </tr>\t\t\t\t\t\r\n\t\t\t\t\t</table>\r\n       </div>\r\n\t      <!--//*交换按扭*-->\r\n");
      out.write("\t   \t\t</td>\r\n  \t\t</tr>\r\n\t</table>\t \r\n<!--//*结束*-->\r\n\t\r\n\t</td>\r\n  </tr>\r\n  <tr>\r\n    <td>\r\n\t<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"100%\" width=\"132\" style=\"background-image:url(/jsoa/imges/left-cent.gif); background-attachment:fixed;padding:0px; margin:0px; scrollbar-base-color: #ffffff; scrollbar-darkshadow-color: #ffffff; scrollbar-highlight-color: #ffffff; overflow:hidden;\" onLoad=\"focusNode();\">\r\n       <tr id=\"submenuBox0\" valign=\"top\">\r\n        <td>\r\n           <div class=\"main\"  id=\"meauDIV\" style=\"width:132px;height:99%;overflow-x:hidden;overflow-y:auto;font-size:12px; background-image:url(\\imges\\left-1.gif)\">   \r\n                <table  width=\"131\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n  ");

  int menuIndex=-1;
  //鹏驰客户董事长，不显示办理中心栏目菜单，1974是其ID
  if(SystemCommon.getCustomerName().equals("pengchi")){
	  if(!empid.equals(tempEmpid)){
  
      out.write("\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">   \r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/FileDealWithAction.do?workStatus=0')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\"  src=\"/jsoa/imges/shanj.gif\" />&nbsp;接收办理</div></td>\r\n  </tr>\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">   \r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/FileDealWithAction.do?workStatus=1')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\"  src=\"/jsoa/imges/shanj.gif\" />&nbsp;已发事项</div></td>\r\n  </tr> \r\n \t ");
}
  }else{
  
      out.write("\r\n   <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/FileDealWithAction.do?workStatus=0')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\"  src=\"/jsoa/imges/shanj.gif\" />&nbsp;接收办理</div></td>\r\n  </tr>\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\"> \r\n  \t<td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/FileDealWithAction.do?workStatus=1')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\"  src=\"/jsoa/imges/shanj.gif\" />&nbsp;已发事项</div></td>\r\n  </tr> \r\n  ");
} 
      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n  ");

  
  com.js.oa.menu.service.ShortCutBD bd=new com.js.oa.menu.service.ShortCutBD();
  bd.init();
  java.util.List menuList=bd.getLeftMenu(session.getAttribute("userId").toString(),0);
  String[] obj;
  String name,url,urlType,emailremind="1",menuid;
  boolean mailMenu=false;

  for(int i=0;i<menuList.size();i++){
    obj=(String[])menuList.get(i);
  	  menuid=obj[0];
      name=obj[1];
      urlType=obj[2];
      url=obj[3];
      mailMenu=false;
      //鹏驰董事长不再显示办理中心栏目下其他菜单
      if(SystemCommon.getCustomerName().equals("pengchi")){
    	  if(empid.equals(tempEmpid)){
    		 break; 
    	  }
      }
    if("新建邮件".equals(name))
    {
    	mailMenu=true;
         com.js.system.util.SysSetupReader ssReader=com.js.system.util.SysSetupReader.getInstance();
         String emailType=ssReader.emailType();
         if("1".equals(emailType))
       {
          List list=new ArrayList();
          String userId=session.getAttribute("userId").toString();
         list=WebMailAccManager.getInstance().getMyAccList(Long.parseLong(userId));
       if(!list.isEmpty())
        {
        url="/jsoa/webMail.do?method=goWriteMail";
         }else{
         emailremind="0";
          url="/jsoa/webMailAcc.do?method=goCreateMailAcc";
          urlType="0";
         }
      }
    } 
      out.write("\r\n     <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">   \r\n ");
//3代表新建日程，日程里添加类型：定期日程、不定期日程
 if("3".equals(menuid)){
      out.write("\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"");
if(mailMenu && "0".equals(emailremind)){
      out.write("alert('请先设置相关的邮箱信息！');");
} 
      out.write("clickMenu('");
      out.print(menuIndex );
      out.write('\'');
      out.write(')');
      out.write(';');
if("0".equals(urlType)){ 
      out.write("jumpMain");
}else{ 
      out.write("openURL");
} 
      out.write('(');
      out.write('\'');
      out.print(url );
      out.write("&start=8:30&end=17:30&ymd=");
      out.print(year);
      out.write('-');
      out.print(month);
      out.write('-');
      out.print(day);
      out.write("')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;");
      out.print(name );
      out.write("</div></td>\r\n  </tr>\r\n  ");

  }else{
  
      out.write("\r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"");
if(mailMenu && "0".equals(emailremind)){
      out.write("alert('请先设置相关的邮箱信息！');");
} 
      out.write("clickMenu('");
      out.print(menuIndex );
      out.write('\'');
      out.write(')');
      out.write(';');
if("0".equals(urlType)){ 
      out.write("jumpMain");
}else{ 
      out.write("openURL");
} 
      out.write('(');
      out.write('\'');
      out.print(url );
      out.write("')\"><img src=\"/jsoa/imges/shanj.gif\" />&nbsp;");
      out.print(name );
      out.write("</div></td>\r\n  </tr>\r\n  \r\n  ");
}
  } 
  bd.close();
  
      out.write('\r');
      out.write('\n');
      out.write('	');

	// 东城区内部邮件系统单点登录，oa到邮件系统的单点登录目前不用，从CA门户统一登录
	if(false && null!=SystemCommon.getCustomerName() && "dcq".equalsIgnoreCase(SystemCommon.getCustomerName())){
		
		//服务器证书
		String personID = null==session.getAttribute("personID") ? "" : session.getAttribute("personID").toString();
		if(null!=personID && !"".equals(personID)){
			String emailURL = "http://172.25.0.5/SSOSecService?LinkType=online&LinkID=qzfnbyjxt&sID=" + personID;
			
      out.write("\r\n\t\t\t<tr>\r\n\t\t\t\t<td>\r\n\t\t\t\t\t<div class=\"menuNormal\" accept-charset=\"GBK\" onClick=\"javascript:window.open('");
      out.print(emailURL );
      out.write("');\">\r\n\t\t\t\t\t\t<img src=\"/jsoa/imges/shanj.gif\" />&nbsp;邮件系统\r\n\t\t\t\t\t</div>\r\n\t\t\t\t</td>\r\n\t\t\t</tr>\r\n\t\t\t");

		}
	}
	
      out.write("\r\n</table>\r\n  \r\n           </div>\r\n\t</td>\r\n  </tr>  \r\n</table></td>\r\n  </tr>\r\n</table>     \t  \r\n\r\n\r\n</BODY>\r\n</HTML>\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n<script src=\"/jsoa/js/imenu.js\"></script>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n\r\nfunction resetHeight(){\r\n \r\n    \r\n\tif(navigator.userAgent.indexOf(\"Firefox\")>=0)//火狐\r\n\t{\r\n\t\tdocument.getElementById(\"meauDIV\").style.height=document.body.clientHeight-33;\r\n\t}\r\n\tif(navigator.userAgent.indexOf(\"MSIE\")>=0)//IE10\r\n\t{  \r\n\t\tdocument.getElementById(\"meauDIV\").style.height=\"99%\";\r\n\t}\r\n\tif(navigator.userAgent.indexOf(\"MSIE 10.0\")>=0)//IE10\r\n\t{  \r\n\t\tdocument.getElementById(\"meauDIV\").style.height=\"99%\";\r\n\t}\r\n\t\r\n    if(navigator.userAgent.indexOf(\"Trident/7.0\")>=0 ){\r\n\t  document.getElementById(\"meauDIV\").style.height=\"75%\";\r\n    } \r\n    \r\n    if(navigator.userAgent.indexOf(\"Trident/7.0\")>=0 && navigator.userAgent.indexOf(\"MSIE 7.0\")>=0){\r\n        document.getElementById(\"meauDIV\").style.height=\"90%\";\r\n       changeClassName();\r\n    }\r\n}\r\n\r\n</SCRIPT>");
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