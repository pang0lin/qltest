/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:50:59 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.setup;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.util.config.SystemCommon;
import com.js.util.util.BrowserJudge;

public final class systemmanager_005forganization_005ftree_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(8);
    _jspx_dependants.put("/setup/active.jsp", Long.valueOf(1499751456000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-nested.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-tiles.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-logic.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/SSHA.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-html.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-template.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-bean.tld", Long.valueOf(1499751390000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.util.config.SystemCommon");
    _jspx_imports_classes.add("com.js.util.util.BrowserJudge");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhtml;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fSSHA_005ftree_0026_005ftarget_005frange_005forgIdString_005fnobody;

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
    _005fjspx_005ftagPool_005fhtml_005fhtml = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fSSHA_005ftree_0026_005ftarget_005frange_005forgIdString_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fhtml.release();
    _005fjspx_005ftagPool_005fSSHA_005ftree_0026_005ftarget_005frange_005forgIdString_005fnobody.release();
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
    String webapp=request.getContextPath();
    String range=request.getParameter("range");
    //System.out.println("----range---->>>>>"+range);
    String orgIdString = "";
    if(SystemCommon.getChildUse()==1){
	    orgIdString = session.getAttribute("orgIdString").toString();
	    String[] orgIdStrs = orgIdString.split("\\$");
	    orgIdString = "";
	    for(int i=1;i<orgIdStrs.length&&i<(2*SystemCommon.getChildShow());i=i+2){
	    	orgIdString += "%"+orgIdStrs[i]+"%";
	    }
    }

      out.write("\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\"/>\r\n<title>无标题文档</title>\r\n<link rel=\"StyleSheet\" href=\"");
      out.print(webapp);
      out.write("/style/dtree.css\" type=\"text/css\" />\r\n<script type=\"text/javascript\" src=\"");
      out.print(webapp);
      out.write("/js/tree/wtree.js\"></script>\r\n");
      out.write("\r\n\r\n<STYLE>\r\n  body {\r\n  \tmargin: 0px\r\n  }\r\n\r\n  #Loading {\r\n\t    position: absolute;\r\n\t    text-align:left;\r\n\t    z-index: 10;\r\n\t    background-color: #FFFFFF;\r\n\t\tborder-top-width: thin;\r\n\t\tborder-right-width: thin;\r\n\t\tborder-bottom-width: thin;\r\n\t\tborder-left-width: thin;\r\n\t\tborder-top-style: outset;\r\n\t\tborder-right-style: outset;\r\n\t\tborder-bottom-style: outset;\r\n\t\tborder-left-style: outset;\r\n\t\tborder-top-color: #CCCCCC;\r\n\t\tborder-right-color: #CCCCCC;\r\n\t\tborder-bottom-color: #CCCCCC;\r\n\t\tborder-left-color: #CCCCCC;\r\n\t\tpadding:5px;\r\n\t\r\n  }\r\n  #Loading  {filter:progid:DXImageTransform.Microsoft.Shadow (Color=#333333,Direction=120,strength=5)} \r\n  \r\n  A.dian:link,A.dian:visited{\r\n\ttext-decoration:none;\r\n\tfloat:left;\r\n\twidth:98%;\r\n\tpadding:5px 10px;\r\n\tfont-size:13px;\r\n\tcolor:#000000;\r\n}\r\n\r\nA.dian:hover,A.dian:active{\r\n\tfloat:left;\r\n\tpadding:5px 10px;\r\n\twidth:100px;\r\n\ttext-decoration:none;\r\n\tbackground-color:#e2e2e2;\r\n\tfont-size:13px;\r\n\tcolor:#000000;\r\n}\r\n</STYLE>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n\r\nvar OverH,OverW,lefts,tops;\r\n");
      out.write("var mx,my;\r\nvar nameTemp,state,idTemp;\r\nvar clientH=window.screenTop*2;\r\n\r\n  \r\nfunction $(){return document.getElementById?document.getElementById(arguments[0]):eval(arguments[0]);}\r\n\r\nfunction getEvent()\r\n {\r\n         if(document.all)return window.event;        \r\n         func=getEvent.caller;            \r\n         while(func!=null){    \r\n             var arg0=func.arguments[0];\r\n              if(arg0){\r\n                  if((arg0.constructor==Event || arg0.constructor ==MouseEvent)\r\n                      || (typeof(arg0)==\"object\" && arg0.preventDefault && arg0.stopPropagation)){   \r\n                      arg0.x = arg0.pageX;\r\n                      arg0.y = arg0.pageY ;\r\n                      arg0.srcElement = arg0.target;\r\n                     return arg0;\r\n                  }\r\n              }\r\n              func=func.caller;\r\n          }\r\n          return null;\r\n\r\n } \r\n\r\nfunction OpenDiv(obj,id) {\r\n       $(\"Loading\").innerHTML=\"\";\r\n       nameTemp=obj.title;\r\n       state=obj.name;\r\n       idTemp=id;\r\n       OverW=125;\r\n");
      out.write("       OverH=100;\r\n       ");
if(BrowserJudge.likeFirefox(request)){
      out.write("\r\n       //if(navigator.userAgent.indexOf(\"Firefox\")>0){\r\n\t       var e = getEvent();\r\n\t       lefts = e.clientX;\r\n\t       tops = e.clientY;\r\n       //}else{");
}else{
      out.write("\r\n\t       lefts=window.event.clientX;\r\n\t       tops=window.event.clientY;\r\n       //}");
}
       if(BrowserJudge.isWebKit(request)){
      out.write("\r\n       //if(navigator.userAgent.indexOf(\"Safari\")>0){\r\n       \t   if(state=='0'){\r\n\t           if(clientH-tops<100){\r\n\t\t          tops=tops;   \r\n\t\t       }\r\n\t       }\r\n\t       if(state=='1'){\r\n\t           OverH=70;\r\n\t           if(clientH-tops<70){\r\n\t\t          tops=tops;   \r\n\t\t       }\r\n\t       }\r\n\t       if(state==''||state=='2'){\r\n\t          OverH=60;\r\n\t          if(clientH-tops<60){\r\n\t\t           tops=tops;     \r\n\t\t       }\r\n\t       }\r\n       //}else{\r\n       ");
}else{
      out.write("\r\n\t       if(state=='0'){\r\n\t           if(clientH-tops<100){\r\n\t\t          tops=tops-100;   \r\n\t\t       }\r\n\t       }\r\n\t       if(state=='1'){\r\n\t           OverH=70;\r\n\t           if(clientH-tops<70){\r\n\t\t          tops=tops-70;   \r\n\t\t       }\r\n\t       }\r\n\t       if(state==''||state=='2'){\r\n\t          OverH=60;\r\n\t          if(clientH-tops<60){\r\n\t\t           tops=tops-60;     \r\n\t\t       }\r\n\t       }\r\n       //}");
}
      out.write("\r\n\t   window.setTimeout(\"OpenNow()\",10);\r\n       window.setTimeout(\"closeNow()\",3000);\r\n}\r\n\r\nfunction OpenNow() {\r\n       $(\"Loading\").style.width=OverW+\"px\";\r\n       $(\"Loading\").style.left=lefts+\"px\";\r\n       $(\"Loading\").style.height=OverH+\"px\";\r\n       $(\"Loading\").style.top=tops+\"px\"\r\n       $(\"Loading\").style.display='';\r\n       $(\"Loading\").align=\"center\";\r\n       selectPOPMenu();\r\n}\r\n\r\nfunction selectPOPMenu(){\r\n   if(state=='1'){\r\n      reDept();\r\n   }if(state=='0'){\r\n      defalut();\r\n      Dept();\r\n   }if(state==''||state=='2'){\r\n      defalut();\r\n      rootDept();\r\n   }\r\n}\r\n\r\nfunction rootDept(){\r\n      var addCDept=document.createElement(\"a\");\r\n      addCDept.href=\"#\";\r\n      addCDept.className=\"dian\";\r\n      addCDept.onclick = function(){\r\n        addParent(idTemp,nameTemp,state);\r\n      }\r\n      var img_c=document.createElement(\"img\");\r\n      img_c.src=\"image/2.gif\";\r\n      img_c.border=\"0px\";\r\n      addCDept.appendChild(img_c);\r\n  $(\"Loading\").appendChild(addCDept);\r\n\r\n}\r\n\r\nfunction defalut(){\r\n");
      out.write("      var reDept=document.createElement(\"a\");\r\n      reDept.href=\"#\";\r\n      reDept.className=\"dian\";\r\n      reDept.onclick = function(){\r\n        detailDept(idTemp,nameTemp,state);\r\n      }\r\n      var img_re=document.createElement(\"img\");\r\n      img_re.src=\"image/6.gif\";\r\n      img_re.border=\"0px\";\r\n      reDept.appendChild(img_re);\r\n      $(\"Loading\").appendChild(reDept);\r\n      $(\"Loading\").appendChild(document.createElement(\"br\"));\r\n}\r\n\r\nfunction Dept(){\r\n      var addPDept=document.createElement(\"a\");\r\n      addPDept.href=\"#\";\r\n      addPDept.className=\"dian\";\r\n      addPDept.onclick = function(){\r\n        addChild(idTemp,nameTemp,state);\r\n      }\r\n      var img_p=document.createElement(\"img\");\r\n      img_p.src=\"image/1.gif\";\r\n      img_p.border=\"0px\";\r\n      addPDept.appendChild(img_p);\r\n      //*******************************\r\n      ");
if(range.equals("*0*")||range.equals("0")||range.equals("")){
      out.write("\r\n      var addCDept=document.createElement(\"a\");\r\n      addCDept.href=\"#\";\r\n      addCDept.className=\"dian\";\r\n      addCDept.onclick = function(){\r\n        addParent(idTemp,nameTemp,state);\r\n      }\r\n      var img_c=document.createElement(\"img\");\r\n      img_c.src=\"image/2.gif\";\r\n      img_c.border=\"0px\";\r\n      addCDept.appendChild(img_c);\r\n      ");
}
      out.write("\r\n      //*******************************\r\n      var stopDept=document.createElement(\"a\");\r\n      stopDept.href=\"#\";\r\n      stopDept.className=\"dian\";\r\n      stopDept.onclick = function(){\r\n        stopDep(idTemp,nameTemp,state);\r\n      }\r\n      var img_s=document.createElement(\"img\");\r\n      img_s.src=\"image/4.gif\";\r\n      img_s.border=\"0px\";\r\n      stopDept.appendChild(img_s);\r\n\r\n      $(\"Loading\").appendChild(addPDept);\r\n      $(\"Loading\").appendChild(document.createElement(\"br\"));\r\n      ");
if(range.equals("*0*")||range.equals("0")||range.equals("")){
      out.write("\r\n      $(\"Loading\").appendChild(addCDept);\r\n      $(\"Loading\").appendChild(document.createElement(\"br\"));\r\n      ");
}
      out.write("\r\n      $(\"Loading\").appendChild(stopDept);\r\n      $(\"Loading\").appendChild(document.createElement(\"br\"));\r\n      //$(\"Loading\").appendChild(editDept);\r\n}\r\n\r\nfunction reDept(){\r\n      var viewDept=document.createElement(\"a\");\r\n      viewDept.href=\"#\";\r\n      viewDept.className=\"dian\";\r\n      viewDept.onclick = function(){\r\n        reDep(idTemp,nameTemp,state);\r\n      }\r\n      var img_view=document.createElement(\"img\");\r\n      img_view.src=\"image/3.gif\";\r\n      img_view.border=\"0px\";\r\n      viewDept.appendChild(img_view);\r\n       //*******************************\r\n      var delDept=document.createElement(\"a\");\r\n      delDept.href=\"#\";\r\n      delDept.className=\"dian\";\r\n      delDept.onclick = function(){\r\n        delDep(idTemp,nameTemp,state);\r\n      }\r\n      var img_del=document.createElement(\"img\");\r\n      img_del.src=\"image/7.gif\";\r\n      img_del.border=\"0px\";\r\n      delDept.appendChild(img_del);\r\n      \r\n      $(\"Loading\").appendChild(viewDept);\r\n      $(\"Loading\").appendChild(document.createElement(\"br\"));\r\n");
      out.write("      $(\"Loading\").appendChild(delDept);\r\n}\r\n\r\nfunction yinc(){\r\n  document.getElementById(\"Loading\").style.display='none';\r\n}  \r\n  \r\n  \r\nfunction mouseMove(ev){ \r\n    ev= ev || window.event; \r\n    var mousePos = mouseCoords(ev); \r\n     mx = mousePos.x; \r\n     my = mousePos.y; \r\n} \r\n\r\nfunction mouseCoords(ev){ \r\n  if(ev.pageX || ev.pageY){ \r\n   return {x:ev.pageX, y:ev.pageY}; \r\n } \r\n  return { \r\n     x:ev.clientX + document.body.scrollLeft - document.body.clientLeft, \r\n     y:ev.clientY + document.body.scrollTop     - document.body.clientTop \r\n   }; \r\n} \r\n\r\nfunction closeNow(){\r\n  if(my>(tops+OverH)||mx>(lefts+OverW)||my<tops||mx<lefts){\r\n       $(\"Loading\").style.display='none';\r\n      } \r\n      window.setTimeout(\"closeNow()\",3000);\r\n}\r\ndocument.onmousemove = mouseMove; \r\n\r\n</SCRIPT>\r\n\r\n\r\n");
      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n<script>\r\n\r\nfunction detailDept(id,name,state){\r\n  //var orgId=id.substring(id.indexOf('(')+1,id.lastIndexOf(')'));\r\n  document.all.orgTreeBar.action='");
      out.print(webapp);
      out.write("'+\"/OrganizationAction.do?action=getOrgDetail&type=1&range=");
      out.print(range);
      out.write("&orgId=\"+id;\r\n  document.all.orgTreeBar.target=\"orgDetailFrame\";\r\n  document.all.orgTreeBar.submit();\r\n}\r\n\r\nfunction editDept(id,name,state){\r\n  //var orgId=id.substring(id.indexOf('(')+1,id.lastIndexOf(')'));\r\n  document.all.orgTreeBar.action='");
      out.print(webapp);
      out.write("'+\"/OrganizationAction.do?action=mod&range=");
      out.print(range);
      out.write("&orgId=\"+id;\r\n  document.all.orgTreeBar.target=\"orgDetailFrame\";\r\n  document.all.orgTreeBar.submit();\r\n}\r\n\r\n\r\nfunction addChild(id,name,state){\r\n  //var orgId=id.substring(id.indexOf('(')+1,id.lastIndexOf(')'));\r\n  document.all.orgTreeBar.action='");
      out.print(webapp);
      out.write("'+\"/OrganizationAction.do?action=proAdd&oldParnetName=\"+name+\"&type=1&range=");
      out.print(range);
      out.write("&orgId=\"+id;\r\n  document.all.orgTreeBar.target=\"orgDetailFrame\";\r\n  document.all.orgTreeBar.submit();\r\n}\r\n\r\nfunction addParent(id,name,state){\r\n  document.all.orgTreeBar.action='");
      out.print(webapp);
      out.write("'+\"/OrganizationAction.do?action=proAdd&oldParnetName=\"+name+\"&type=0&range=");
      out.print(range);
      out.write("&orgId=0\";\r\n  document.all.orgTreeBar.target=\"orgDetailFrame\";\r\n  document.all.orgTreeBar.submit();\r\n}\r\n\r\nfunction reDep(id,name,state){\r\n  hidenPOPMenu();\r\n  document.all.orgTreeBar.action='");
      out.print(webapp);
      out.write("'+\"/OrganizationAction.do?action=reDept&orgId=\"+id;\r\n  document.all.orgTreeBar.target=\"_parent\";\r\n  if(confirm(\"确认要恢复 \"+name.substring(0,name.length-3)+\" 吗？\")){\r\n\t  ");
if(com.js.util.config.SystemCommon.getAudit()!=0&&com.js.util.config.SystemCommon.getAutoAudit()!=1){ 
      out.write("\r\n\t        alert(\"数据已提交审计管理员审核！\");\r\n\t   ");
}
      out.write("\r\n     document.all.orgTreeBar.submit();\r\n  }\r\n}\r\n\r\nfunction stopDep(id,name,state){\r\n  hidenPOPMenu();\r\n  document.all.orgTreeBar.action='");
      out.print(webapp);
      out.write("'+\"/OrganizationAction.do?action=stopDep&orgId=\"+id;\r\n  document.all.orgTreeBar.target=\"_parent\";\r\n  if(confirm(\"确认要禁用 \"+name+\" 吗？\")){\r\n    if(confirm(\"被禁用组织下的人员同时被禁用,确认要禁用吗？\")){\r\n    ");
if(com.js.util.config.SystemCommon.getAudit()!=0&&com.js.util.config.SystemCommon.getAutoAudit()!=1){ 
      out.write("\r\n        alert(\"数据已提交审计管理员审核！\");\r\n    ");
}
      out.write("\r\n     document.all.orgTreeBar.submit();\r\n    } \r\n  }\r\n}\r\n\r\nfunction delDep(id,name,state){\r\n  hidenPOPMenu();\r\n  document.all.orgTreeBar.action='");
      out.print(webapp);
      out.write("'+\"/OrganizationAction.do?action=delete&orgId=\"+id;\r\n  document.all.orgTreeBar.target=\"_parent\";\r\n  if(confirm(\"确认要删除 \"+name.substring(0,name.length-3)+\" 吗？\")){\r\n    if(confirm(\"被删除组织下的子组织及人员都将删除,确认要删除吗？\")){\r\n    ");
if(com.js.util.config.SystemCommon.getAudit()!=0&&com.js.util.config.SystemCommon.getAutoAudit()!=1){ 
      out.write("\r\n\t    alert(\"数据已提交审计管理员审核！\");\r\n\t");
}
      out.write("\r\n     document.all.orgTreeBar.submit();\r\n    } \r\n  }\r\n}\r\n\r\n\r\nfunction hiddenOrShow(id,name,state,type){\r\n  if(state=='1'&&(type=='parnet'||type=='child'||type=='stop')){\r\n    alert(name+' 已被禁用！');\r\n    return false;\r\n  }else if(state=='1'&&type=='re'){\r\n     reDep(id,name,state);\r\n  }else{\r\n    if(type=='parnet'){\r\n      addParent(id,name,state)\r\n    }if(type=='child'){\r\n      addChild(id,name,state);\r\n    }if(type=='stop'){\r\n      stopDep(id,name,state);\r\n    }\r\n  }\r\n}\r\n\r\nfunction hidenPOPMenu(){\r\n document.getElementById(\"Loading\").style.display=\"none\";\r\n}\r\n\r\n</script>\r\n<style type=\"text/css\">\r\n<!--\r\nbody{\r\nscrollbar-base-color: #ffffff;\r\nscrollbar-darkshadow-color: #ffffff;\r\nscrollbar-highlight-color: #ffffff;\r\nbackground:url(背景图片地址);\r\noverflow:hidden;\r\n}\r\ndiv.main{\r\noverflow:scroll;\r\npadding:0px 0px 0px 15px;\r\nwidth:132px;\r\nheight:100%;\r\n\r\n}\r\n\r\n-->\r\n</style>\r\n</head>\r\n");
      //  html:html
      org.apache.struts.taglib.html.HtmlTag _jspx_th_html_005fhtml_005f0 = (org.apache.struts.taglib.html.HtmlTag) _005fjspx_005ftagPool_005fhtml_005fhtml.get(org.apache.struts.taglib.html.HtmlTag.class);
      boolean _jspx_th_html_005fhtml_005f0_reused = false;
      try {
        _jspx_th_html_005fhtml_005f0.setPageContext(_jspx_page_context);
        _jspx_th_html_005fhtml_005f0.setParent(null);
        int _jspx_eval_html_005fhtml_005f0 = _jspx_th_html_005fhtml_005f0.doStartTag();
        if (_jspx_eval_html_005fhtml_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n<body class=\"orgTreeFrame\" leftmargin=\"0\" topmargin=\"0\" onclick=\"hidenPOPMenu()\" oncontextmenu=\" return false\">\r\n<form name=\"orgTreeBar\" method=\"post\" style=\"height:100%\">\r\n<!-- <div style=\"width:250px; padding:20px 0px 6px 95px;\">");
            out.write("  \r\n  <a href=\"javascript:void(0);\" onClick=\"d.openAll()\"> <img src=\"../images/shouqi.gif\" border=\"0\"/></a>\r\n <a href=\"javascript:void(0);\" onClick=\"d.closeAll()\"><img src=\"../images/zhankai.gif\" border=\"0\"/></a> </div>-->\r\n  <div id=\"treearea\" class=\"main\" style=\"width:250; overflow-x:auto; overflow-y:auto; height:100%; scrollbar-face-color:#efebef;\">\r\n      ");
            //  SSHA:tree
            com.js.taglib.OrgTag _jspx_th_SSHA_005ftree_005f0 = (com.js.taglib.OrgTag) _005fjspx_005ftagPool_005fSSHA_005ftree_0026_005ftarget_005frange_005forgIdString_005fnobody.get(com.js.taglib.OrgTag.class);
            boolean _jspx_th_SSHA_005ftree_005f0_reused = false;
            try {
              _jspx_th_SSHA_005ftree_005f0.setPageContext(_jspx_page_context);
              _jspx_th_SSHA_005ftree_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
              // /setup/systemmanager_organization_tree.jsp(156,6) name = range type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_SSHA_005ftree_005f0.setRange(range);
              // /setup/systemmanager_organization_tree.jsp(156,6) name = orgIdString type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_SSHA_005ftree_005f0.setOrgIdString(orgIdString );
              // /setup/systemmanager_organization_tree.jsp(156,6) name = target type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_SSHA_005ftree_005f0.setTarget("orgDetailFrame");
              int _jspx_eval_SSHA_005ftree_005f0 = _jspx_th_SSHA_005ftree_005f0.doStartTag();
              if (_jspx_th_SSHA_005ftree_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                return;
              }
              _005fjspx_005ftagPool_005fSSHA_005ftree_0026_005ftarget_005frange_005forgIdString_005fnobody.reuse(_jspx_th_SSHA_005ftree_005f0);
              _jspx_th_SSHA_005ftree_005f0_reused = true;
            } finally {
              org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_SSHA_005ftree_005f0, _jsp_getInstanceManager(), _jspx_th_SSHA_005ftree_005f0_reused);
            }
            out.write("\r\n  </div> \r\n  <div id=\"Loading\" style=\"display:none;\" >f</div>\r\n</form>\r\n</body>\r\n");
            int evalDoAfterBody = _jspx_th_html_005fhtml_005f0.doAfterBody();
            if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
              break;
          } while (true);
        }
        if (_jspx_th_html_005fhtml_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return;
        }
        _005fjspx_005ftagPool_005fhtml_005fhtml.reuse(_jspx_th_html_005fhtml_005f0);
        _jspx_th_html_005fhtml_005f0_reused = true;
      } finally {
        org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fhtml_005f0, _jsp_getInstanceManager(), _jspx_th_html_005fhtml_005f0_reused);
      }
      out.write('\r');
      out.write('\n');
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
