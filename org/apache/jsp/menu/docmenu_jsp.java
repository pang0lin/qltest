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
import java.util.*;
import com.js.system.manager.service.ManagerService;
import com.js.oa.info.channelmanager.service.ChannelBD;
import com.js.oa.jsflow.util.ProcessStep;
import com.js.lang.Resource;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.vo.*;

public final class docmenu_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-bean.tld", Long.valueOf(1499751390000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("com.js.oa.jsflow.vo");
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.oa.jsflow.util.ProcessStep");
    _jspx_imports_classes.add("com.js.lang.Resource");
    _jspx_imports_classes.add("com.js.oa.info.channelmanager.service.ChannelBD");
    _jspx_imports_classes.add("com.js.system.manager.service.ManagerService");
    _jspx_imports_classes.add("com.js.oa.jsflow.service.WorkFlowBD");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody;

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
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.release();
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

String module=request.getParameter("module");
String index=request.getParameter("index");  

String channelType = "0";
String userChannelName = "知识管理";
String userDefine ="0";

String userId = session.getAttribute("userId").toString();
String orgId = session.getAttribute("orgId").toString();
String orgIdString = session.getAttribute("orgIdString").toString();
String domainId=session.getAttribute("domainId").toString();

String processId="0";
List list=new ChannelBD().getChannelMenu_ByType("","","",domainId,"2");
if(list!=null&&list.size()>0){
    Object obj[] = (Object[]) list.get(0);
    ChannelBD bd = new ChannelBD();
    String nextStep = "";
    ProcessStep processStep = new ProcessStep();
    Long _processId = (Long) bd.getChannelProcessId("" + obj[0]);
    if (_processId.longValue() != 0) {
        processId=String.valueOf(_processId);
    }
}
        
 WorkFlowBD wfBD = new WorkFlowBD();
 ModuleVO moduleVO = new ModuleVO();
 moduleVO.setId(50);
 moduleVO.setFormType(0);
 AccessTableVO tableVO = (AccessTableVO) wfBD.getAccessTable(moduleVO).get(0);        
        
boolean managerRight=false;
boolean itMRight=false;
ManagerService mbd = new ManagerService();
managerRight= mbd.hasRight(userId,"26*08*26");
itMRight= mbd.hasRight(userId,"26*08*27");

boolean canIssue=false;
ChannelBD channelBD = new ChannelBD();
ManagerService managerBD = new ManagerService();
String where = managerBD.getScopeFinalWhere(userId, orgId, orgIdString,
        "aaa.channelIssuer",
        "aaa.channelIssuerOrg",
        "aaa.channelIssuerGroup");
 List canIssueList=channelBD.getIsoCanIssue(where, domainId);
 if(canIssueList!=null&&canIssueList.size()>0){
    canIssue=true;
 }


      out.write("\r\n\r\n\r\n<HTML>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\" />\r\n<title>档案管理</title>\r\n<link href=\"/jsoa/css/menustyle.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n<script src=\"/jsoa/js/imenu.js\"></script>\r\n<style type=\"text/css\">\r\n<!--\r\nbody,table,div{font-size:12px; margin:0px; padding:0px;color:#003366;}\r\nbody{\r\nscrollbar-base-color: #ffffff;\r\nscrollbar-darkshadow-color: #ffffff;\r\nscrollbar-highlight-color: #ffffff;\r\nbackground:url(背景图片地址);\r\noverflow:hidden;\r\nmargin:0;\r\n}\r\ndiv.main{\r\noverflow:scroll;\r\nwidth:500px;\r\nheight:400px;\r\n/*filter: chroma(color=#ffffff);*/\r\n}\r\n*{margin:0;padding:0;}\r\n.menuNormal{\r\n\theight:32px; \r\n\twidth:131px; \r\n\tpadding-left:17px; \r\n\tbackground-image:url(/jsoa/imges/left-1.gif); \t\r\n\tcursor:pointer;\r\n}\r\n\r\n.menuFocus{\r\n\theight:32px; \r\n\twidth:131px; \r\n\tpadding-left:17px; \r\n\tbackground-image:url(/jsoa/imges/left-jiaoh.gif);\r\n\tfont-weight:bold;\r\n\tcursor:pointer;\r\n}\r\n\r\n.leftMenuTop{\r\n\tbackground-image:url(/jsoa/imges/left-1.gif);}\r\n");
      out.write("\r\n/*.leftMenuTopDIV{\r\n\theight:22px; \r\n\tpadding-top:8px; \r\n\tfloat:left; \r\n\twidth:132px;\r\n\tmargin:0px;\r\n}*/\r\n\r\n.topMenuNormal{\r\n\twidth:60px;\r\n\tcursor:pointer;\r\n}\r\n\r\n.topMenuFocus{\r\n\tbackground-image:url(/jsoa/imges/zi.gif); \r\n\twidth:60px;\r\n\tcursor:pointer;\r\n}\r\n\r\n/*.menuTopTitle{\r\n\tfloat:left;\r\n\tpadding-top:2px;\r\n}\r\n\r\n.menuTopTitleLeft{\r\n\twidth:17px;\r\n\tfloat:left;\r\n\tpadding:0px 2px 0px 7px;\r\n}\r\n\r\n.menuTopTitleRight{\r\n\tpadding-left:2px;\r\n\twidth:17px;\r\n\tfloat:left;\r\n}\r\n*/\r\n.menuScrollDIV{\r\n\tfloat:left; \r\n\tpadding-bottom:2px;\r\n}\r\n\r\n.menuShowHide{\r\n\tbackground-image:url(/jsoa/imges/cent.gif); \r\n\twidth:8px; \r\n\tfloat:left; \r\n\theight:100%; \r\n\tpadding-top:270px;\r\n}\r\n\r\n.portalNormal{\r\n\tbackground-image:url(/jsoa/imges/03.gif);\r\n\tcolor:#000;\r\n\twidth:75px;\r\n\tcursor:pointer;\r\n}\r\n\r\n.portalFocus{\r\n\tbackground-image:url(/jsoa/imges/02.gif);\r\n\tcolor:#000;\r\n\twidth:75px;\r\n\tcursor:pointer;\r\n} \r\n-->\r\n</style>\r\n<body style=\"margin:0px; padding:0px;\" onload=\"initHeight();\"> \r\n<table height=\"100%\" width=\"132\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("  <tr>\r\n    <td height=\"32\">\r\n\t<!--//*头部*-->\r\n\t<table width=\"131\" height=\"32\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n \t\t <tr>\r\n    \t\t<td class=\"leftMenuTop\">\r\n\t\r\n<!--//*left*-->\r\n    <div class=\"leftMenuTopDIV\">\t\r\n\t<div class=\"menuTopTitleLeft\"><img src=\"/jsoa/imges/9s.gif\" title=\"切换面板\"></img></div>\r\n\t<div class=\"menuTopTitle\"><b>文档管理</b></div>\r\n\t<div class=\"menuTopTitleRight\"><img src=\"/jsoa/imges/9s.gif\" title=\"切换面板\"></img></div>\r\n\t</div>\r\n<!--//*right*-->\r\n\t\r\n    <div class=\"menuScrollDIV\">\r\n        \t\t<table height=\"32\"  width=\"20\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n\t\t\t\t\t  <tr>\r\n\t\t\t\t\t    <td align=\"center\" valign=\"center\"  style=\"padding-top:4px;\">\r\n\t\t\t\t\t       <img id=\"imgUP\" src=\"/jsoa/imges/kzmb.gif\" title=\"切换面板\" style=\"cursor:pointer\" onMouseOver=\"shortFocus_(this);\" onMouseOut=\"shortBlur_(this);\" onClick=\"changePanle(1);\"/>\r\n\t\t\t\t\t    </td>\r\n\t\t\t\t\t    </tr>\t\t\t\t\t\r\n\t\t\t\t\t</table>\r\n       </div>\r\n\t      <!--//*交换按扭*-->\r\n\t   \t\t</td>\r\n  \t\t</tr>\r\n\t</table>\t \r\n<!--//*结束*-->\r\n\t\r\n\t</td>\r\n  </tr>\r\n  <tr>\r\n");
      out.write("    <td>\r\n\t<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"100%\" width=\"132\" style=\"background-image:url(/jsoa/imges/left-cent.gif); background-attachment:fixed;padding:0px; margin:0px; scrollbar-base-color: #ffffff; scrollbar-darkshadow-color: #ffffff; scrollbar-highlight-color: #ffffff; overflow:hidden;\" onLoad=\"focusNode();\">\r\n       <tr id=\"submenuBox0\" valign=\"top\">\r\n        <td>\r\n           <div class=\"main\" style=\"width:132px;height:99%;overflow-x:hidden;overflow-y:auto;font-size:12px; background-image:url(\\imges\\left-1.gif)\">   \r\n                \r\n\t  <table  width=\"131\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n  ");

  int menuIndex=-1;
  
      out.write("\r\n  ");
if(managerRight){
      out.write("\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">   \r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/IsoInfoListAction.do?type=all&channelType=");
      out.print(channelType);
      out.write("&userChannelName=");
      out.print(userChannelName);
      out.write("&userDefine=");
      out.print(userDefine);
      out.write("&isISO=1&isoViewType=1')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\"  src=\"/jsoa/imges/shanj.gif\" />&nbsp;");
      if (_jspx_meth_bean_005fmessage_005f0(_jspx_page_context))
        return;
      out.write("</div></td>\r\n  </tr> \r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">   \r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/IsoInfoListAction.do?type=all&channelType=");
      out.print(channelType);
      out.write("&userChannelName=");
      out.print(userChannelName);
      out.write("&userDefine=");
      out.print(userDefine);
      out.write("&isISO=1&isoViewType=2')\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\"  src=\"/jsoa/imges/shanj.gif\" />&nbsp;");
      if (_jspx_meth_bean_005fmessage_005f1(_jspx_page_context))
        return;
      out.write("</div></td>\r\n  </tr>  \r\n  ");
} 
      out.write("\r\n  \r\n  ");
if(itMRight){
      out.write("\r\n    \r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">   \r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/IsoInfoListAction.do?type=all&channelType=");
      out.print(channelType);
      out.write("&userChannelName=");
      out.print(userChannelName);
      out.write("&userDefine=");
      out.print(userDefine);
      out.write("&isISO=1&isoViewType=3');\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\"  src=\"/jsoa/imges/shanj.gif\" />&nbsp;历史文档</div></td>\r\n  </tr>  \r\n  ");
} 
      out.write("\r\n  ");
if(canIssue){
      out.write("\r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">   \r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');openURL('/jsoa/InformationAction.do?action=add&channelType=");
      out.print(channelType);
      out.write("&userDefine=");
      out.print(userDefine);
      out.write("&processId=");
      out.print(processId);
      out.write("&moduleId=4&remindField=&tableId=");
      out.print(tableVO.getId());
      out.write("&processName=信息发布流程&processType=1&isISO=1');\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\"  src=\"/jsoa/imges/shanj.gif\" />&nbsp;");
      if (_jspx_meth_bean_005fmessage_005f2(_jspx_page_context))
        return;
      out.write("</div></td>\r\n  </tr> \r\n  ");
} 
      out.write("\r\n  \r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">   \r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/IsoInfoAction.do?action=paperlist&mypaper=1');\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\"  src=\"/jsoa/imges/shanj.gif\" />&nbsp;");
      if (_jspx_meth_bean_005fmessage_005f3(_jspx_page_context))
        return;
      out.write("</div></td>\r\n  </tr> \r\n  \r\n  ");
if(managerRight){
      out.write("\r\n  \r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">   \r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/IsoInfoAction.do');\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\"  src=\"/jsoa/imges/shanj.gif\" />&nbsp;");
      if (_jspx_meth_bean_005fmessage_005f4(_jspx_page_context))
        return;
      out.write("</div></td>\r\n  </tr> \r\n  \r\n <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">   \r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/WFProcessAction.do?moduleId=36');\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\"  src=\"/jsoa/imges/shanj.gif\" />&nbsp;");
      if (_jspx_meth_bean_005fmessage_005f5(_jspx_page_context))
        return;
      out.write("</div></td>\r\n  </tr> \r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">   \r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/IsoBorrowAction.do?action=list');\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\"  src=\"/jsoa/imges/shanj.gif\" />&nbsp;");
      if (_jspx_meth_bean_005fmessage_005f6(_jspx_page_context))
        return;
      out.write("</div></td>\r\n  </tr> \r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">   \r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/IsoInfoListAction.do?action=staUpdate&channelType=");
      out.print(channelType);
      out.write("&userChannelName=");
      out.print(userChannelName);
      out.write("&userDefine=");
      out.print(userDefine);
      out.write("');\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\"  src=\"/jsoa/imges/shanj.gif\" />&nbsp;");
      if (_jspx_meth_bean_005fmessage_005f7(_jspx_page_context))
        return;
      out.write("</div></td>\r\n  </tr> \r\n  <tr id=\"mainTR_");
      out.print(++menuIndex);
      out.write("\">   \r\n    <td><div id=\"topDIV");
      out.print(menuIndex );
      out.write("\" class=\"menuNormal\" onClick=\"clickMenu('");
      out.print(menuIndex );
      out.write("');jumpMain('/jsoa/ISOChannelAction.do?action=isoDocView&channelType=");
      out.print(channelType);
      out.write("&userChannelName=");
      out.print(userChannelName);
      out.write("&userDefine=");
      out.print(userDefine);
      out.write("&isIsoDoc=1');\"><img id=\"menuImg_");
      out.print(menuIndex );
      out.write("\"  src=\"/jsoa/imges/shanj.gif\" />&nbsp;");
      if (_jspx_meth_bean_005fmessage_005f8(_jspx_page_context))
        return;
      out.write("</div></td>\r\n  </tr> \r\n      \r\n  ");
} 
      out.write("\r\n  \r\n</table>\r\n\r\n  \r\n           </div>\r\n\t</td>\r\n  </tr>  \r\n</table></td>\r\n  </tr>\r\n</table>     \t  \r\n\r\n\r\n</BODY>\r\n</HTML>\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n<script src=\"/jsoa/js/imenu.js\"></script>\r\n");
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

  private boolean _jspx_meth_bean_005fmessage_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f0 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    boolean _jspx_th_bean_005fmessage_005f0_reused = false;
    try {
      _jspx_th_bean_005fmessage_005f0.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fmessage_005f0.setParent(null);
      // /menu/docmenu.jsp(224,332) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f0.setBundle("iso");
      // /menu/docmenu.jsp(224,332) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f0.setKey("ISO.expired");
      int _jspx_eval_bean_005fmessage_005f0 = _jspx_th_bean_005fmessage_005f0.doStartTag();
      if (_jspx_th_bean_005fmessage_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0);
      _jspx_th_bean_005fmessage_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fmessage_005f0, _jsp_getInstanceManager(), _jspx_th_bean_005fmessage_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f1(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f1 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    boolean _jspx_th_bean_005fmessage_005f1_reused = false;
    try {
      _jspx_th_bean_005fmessage_005f1.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fmessage_005f1.setParent(null);
      // /menu/docmenu.jsp(227,332) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f1.setBundle("iso");
      // /menu/docmenu.jsp(227,332) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f1.setKey("ISO.destroyed");
      int _jspx_eval_bean_005fmessage_005f1 = _jspx_th_bean_005fmessage_005f1.doStartTag();
      if (_jspx_th_bean_005fmessage_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.reuse(_jspx_th_bean_005fmessage_005f1);
      _jspx_th_bean_005fmessage_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fmessage_005f1, _jsp_getInstanceManager(), _jspx_th_bean_005fmessage_005f1_reused);
    }
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f2(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f2 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    boolean _jspx_th_bean_005fmessage_005f2_reused = false;
    try {
      _jspx_th_bean_005fmessage_005f2.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fmessage_005f2.setParent(null);
      // /menu/docmenu.jsp(239,394) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f2.setBundle("iso");
      // /menu/docmenu.jsp(239,394) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f2.setKey("ISO.newdocument");
      int _jspx_eval_bean_005fmessage_005f2 = _jspx_th_bean_005fmessage_005f2.doStartTag();
      if (_jspx_th_bean_005fmessage_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.reuse(_jspx_th_bean_005fmessage_005f2);
      _jspx_th_bean_005fmessage_005f2_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fmessage_005f2, _jsp_getInstanceManager(), _jspx_th_bean_005fmessage_005f2_reused);
    }
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f3(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f3 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    boolean _jspx_th_bean_005fmessage_005f3_reused = false;
    try {
      _jspx_th_bean_005fmessage_005f3.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fmessage_005f3.setParent(null);
      // /menu/docmenu.jsp(244,232) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f3.setBundle("iso");
      // /menu/docmenu.jsp(244,232) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f3.setKey("ISO.receive");
      int _jspx_eval_bean_005fmessage_005f3 = _jspx_th_bean_005fmessage_005f3.doStartTag();
      if (_jspx_th_bean_005fmessage_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.reuse(_jspx_th_bean_005fmessage_005f3);
      _jspx_th_bean_005fmessage_005f3_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fmessage_005f3, _jsp_getInstanceManager(), _jspx_th_bean_005fmessage_005f3_reused);
    }
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f4(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f4 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    boolean _jspx_th_bean_005fmessage_005f4_reused = false;
    try {
      _jspx_th_bean_005fmessage_005f4.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fmessage_005f4.setParent(null);
      // /menu/docmenu.jsp(250,205) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f4.setBundle("iso");
      // /menu/docmenu.jsp(250,205) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f4.setKey("ISO.issue");
      int _jspx_eval_bean_005fmessage_005f4 = _jspx_th_bean_005fmessage_005f4.doStartTag();
      if (_jspx_th_bean_005fmessage_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.reuse(_jspx_th_bean_005fmessage_005f4);
      _jspx_th_bean_005fmessage_005f4_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fmessage_005f4, _jsp_getInstanceManager(), _jspx_th_bean_005fmessage_005f4_reused);
    }
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f5(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f5 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    boolean _jspx_th_bean_005fmessage_005f5_reused = false;
    try {
      _jspx_th_bean_005fmessage_005f5.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fmessage_005f5.setParent(null);
      // /menu/docmenu.jsp(254,219) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f5.setBundle("iso");
      // /menu/docmenu.jsp(254,219) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f5.setKey("ISO.workflowdefine");
      int _jspx_eval_bean_005fmessage_005f5 = _jspx_th_bean_005fmessage_005f5.doStartTag();
      if (_jspx_th_bean_005fmessage_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.reuse(_jspx_th_bean_005fmessage_005f5);
      _jspx_th_bean_005fmessage_005f5_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fmessage_005f5, _jsp_getInstanceManager(), _jspx_th_bean_005fmessage_005f5_reused);
    }
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f6(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f6 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    boolean _jspx_th_bean_005fmessage_005f6_reused = false;
    try {
      _jspx_th_bean_005fmessage_005f6.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fmessage_005f6.setParent(null);
      // /menu/docmenu.jsp(257,219) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f6.setBundle("iso");
      // /menu/docmenu.jsp(257,219) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f6.setKey("ISO.borrowrecord");
      int _jspx_eval_bean_005fmessage_005f6 = _jspx_th_bean_005fmessage_005f6.doStartTag();
      if (_jspx_th_bean_005fmessage_005f6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.reuse(_jspx_th_bean_005fmessage_005f6);
      _jspx_th_bean_005fmessage_005f6_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fmessage_005f6, _jsp_getInstanceManager(), _jspx_th_bean_005fmessage_005f6_reused);
    }
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f7(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f7 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    boolean _jspx_th_bean_005fmessage_005f7_reused = false;
    try {
      _jspx_th_bean_005fmessage_005f7.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fmessage_005f7.setParent(null);
      // /menu/docmenu.jsp(260,319) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f7.setBundle("iso");
      // /menu/docmenu.jsp(260,319) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f7.setKey("ISO.stat");
      int _jspx_eval_bean_005fmessage_005f7 = _jspx_th_bean_005fmessage_005f7.doStartTag();
      if (_jspx_th_bean_005fmessage_005f7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.reuse(_jspx_th_bean_005fmessage_005f7);
      _jspx_th_bean_005fmessage_005f7_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fmessage_005f7, _jsp_getInstanceManager(), _jspx_th_bean_005fmessage_005f7_reused);
    }
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f8(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f8 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    boolean _jspx_th_bean_005fmessage_005f8_reused = false;
    try {
      _jspx_th_bean_005fmessage_005f8.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fmessage_005f8.setParent(null);
      // /menu/docmenu.jsp(263,330) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f8.setBundle("iso");
      // /menu/docmenu.jsp(263,330) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f8.setKey("ISO.catalogue");
      int _jspx_eval_bean_005fmessage_005f8 = _jspx_th_bean_005fmessage_005f8.doStartTag();
      if (_jspx_th_bean_005fmessage_005f8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.reuse(_jspx_th_bean_005fmessage_005f8);
      _jspx_th_bean_005fmessage_005f8_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fmessage_005f8, _jsp_getInstanceManager(), _jspx_th_bean_005fmessage_005f8_reused);
    }
    return false;
  }
}
