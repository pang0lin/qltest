/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:55:51 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.info;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import com.js.oa.online.service.OnlineDB;
import com.js.oa.online.po.SecurityOnlineuser;
import com.active.e_uc.user.service.TblUserBD;
import com.active.e_uc.user.po.TblUser;
import com.active.e_uc.user.service.TblUserStatusBD;
import com.js.util.util.BrowserJudge;
import com.js.util.util.ReadActiveXml;
import com.js.system.service.usermanager.UserBD;
import rtx.RTXSvrApi;
import com.js.oa.webmail.util.WebMailAccManager;

public final class browserPage_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(3);
    _jspx_dependants.put("/public/jsp/active.jsp", Long.valueOf(1499751452000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-logic.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/public/jsp/online.jsp", Long.valueOf(1499751452000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.active.e_uc.user.service.TblUserStatusBD");
    _jspx_imports_classes.add("com.js.util.util.ReadActiveXml");
    _jspx_imports_classes.add("com.active.e_uc.user.service.TblUserBD");
    _jspx_imports_classes.add("com.active.e_uc.user.po.TblUser");
    _jspx_imports_classes.add("com.js.oa.online.service.OnlineDB");
    _jspx_imports_classes.add("com.js.oa.webmail.util.WebMailAccManager");
    _jspx_imports_classes.add("com.js.util.util.BrowserJudge");
    _jspx_imports_classes.add("com.js.oa.online.po.SecurityOnlineuser");
    _jspx_imports_classes.add("com.js.system.service.usermanager.UserBD");
    _jspx_imports_classes.add("rtx.RTXSvrApi");
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
      out.write("\r\n\r\n\r\n\r\n");

	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

      out.write("\r\n<STYLE>\r\n  body {\r\n  \tmargin: 0px\r\n  }\r\n\r\n  #Loading {\r\n\t    position: absolute;\r\n\t    text-align:left;\r\n\t    z-index: 10;\r\n\t    background-color: #FFFFFF;\r\n\t\tborder-top-width: thin;\r\n\t\tborder-right-width: thin;\r\n\t\tborder-bottom-width: thin;\r\n\t\tborder-left-width: thin;\r\n\t\tborder-top-style: outset;\r\n\t\tborder-right-style: outset;\r\n\t\tborder-bottom-style: outset;\r\n\t\tborder-left-style: outset;\r\n\t\tborder-top-color: #CCCCCC;\r\n\t\tborder-right-color: #CCCCCC;\r\n\t\tborder-bottom-color: #CCCCCC;\r\n\t\tborder-left-color: #CCCCCC;\r\n\t\tpadding:5px;\r\n\t\r\n  }\r\n  #Loading  {filter:progid:DXImageTransform.Microsoft.Shadow \r\n(Color=#333333,Direction=120,strength=5)} \r\n  \r\n  \r\n  \r\n  \r\n  \r\n  \r\n  \r\n</STYLE>\r\n<!--<object classid=\"clsid:79B13EA1-83FB-49FD-A0D0-3F10C447DF75\"\r\n\tid=\"ACStart\" width=\"0\" height=\"0\"></object>\r\n-->\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n\r\nfunction IM_TextChat()\r\n\t{    \r\n\t var username=document.getElementById(\"name11\").value;\r\n\t var username1=document.getElementById(\"name22\").value;\r\n     ACStart.StartTextMsgSend(username,username1);\r\n");
      out.write("     document.getElementById(\"Loading\").style.display='none';\r\n\t}\r\n\r\n    function IM_AVChat()\r\n\t{\r\n\t\tvar username=document.getElementById(\"name11\").value;\r\n\t var username1=document.getElementById(\"name22\").value;\r\n\t\tACStart.StartAVTalk(username,username1);\r\n\t\tdocument.getElementById(\"Loading\").style.display='none';\r\n\t}\r\n    function IM_InviteJoinMeeting()\r\n\t{\r\n\t\tvar username=document.getElementById(\"name11\").value;\r\n\t    var username1=document.getElementById(\"name22\").value;\r\n\t\tACStart.InviteJoinCurMeeting(username, username1);\r\n\t\tdocument.getElementById(\"Loading\").style.display='none';\r\n\t}\r\n  function openEmalPage(){\r\n    \t    \r\n    var element=parent.document.all.content1;\r\n    var offsetTop = element.offsetTop; \r\n    var offsetLeft = element.offsetLeft; \r\n    var offsetWidth = element.offsetWidth; \r\n    var offsetHeight = element.offsetHeight; \r\n    \r\n    var userid=document.getElementById(\"name33\").value;\r\n\r\n    while( element = element.offsetParent ) \r\n   { \r\n        offsetTop += element.offsetTop; \r\n        offsetLeft += element.offsetLeft; \r\n");
      out.write("   }\r\n    var winTop=parent.window.screenTop;\r\n    var winLeft=parent.window.screenLeft; \r\n    \r\n    var Top=offsetTop+winTop;\r\n   var Left=offsetLeft+winLeft;\r\n   \r\n  \r\n    \r\n   offsetHeight=offsetHeight-33;\r\n   offsetWidth=offsetWidth-10;\r\n   \r\n    \r\n    window.open(\"/jsoa/innerMailAction.do?action=openAddMail&id1=\"+userid,'','TOP='+Top+',LEFT='+Left+',scrollbars=yes,resizable=yes,width='+offsetWidth+',height='+offsetHeight);\r\n    document.getElementById(\"Loading\").style.display='none';\r\n}\r\n\r\n function openEmalPage1(){\r\n    \r\n    var winTop=237;\r\n    var winLeft=145; \r\n    var offsetHeight=473;\r\n    var offsetWidth=872;\r\n     var userid=document.getElementById(\"name33\").value;\r\n    window.open(\"/jsoa/innerMailAction.do?action=openAddMail&id1=\"+userid,'','TOP='+winTop+',LEFT='+winLeft+',scrollbars=yes,resizable=yes,width='+offsetWidth+',height='+offsetHeight);\r\n    document.getElementById(\"Loading\").style.display='none';\r\n}\r\nfunction chat(){\t\t\r\n    \r\n\t\tvar wid=428;\r\n\t\tvar heigh=283;\r\n\t\tvar userid=document.getElementById(\"name33\").value;\t\r\n");
      out.write("\t\t\r\n\t\tvar popwin=JSMainWinOpen2('/jsoa/chat/chat.jsp?id='+userid+'','','',445,283)\r\n     document.getElementById(\"Loading\").style.display='none';\r\n\r\n        }\r\nvar OverH,OverW,ChangeDesc,cds,lefts,tops,userid,type,emailtype;\r\nfunction $(){\r\n     return document.getElementById?document.getElementById(arguments[0]):eval(arguments[0]);\r\n}\r\nfunction OpenDiv(_Dw,_Dh,_Desc,fd,_type,_emailtype,username) {\r\n \r\n       $(\"Loading\").innerHTML=\"\";\r\n       OverH=_Dh;OverW=_Dw;cds=fd;type=_type;emailtype=_emailtype;userid=_Desc;ChangeDesc=username;\r\n       lefts=window.event.clientX;\r\n       tops=window.event.clientY;\r\n       window.setTimeout(\"OpenNow()\",10)\r\n       window.setTimeout(\"closeNow()\",3000);\r\n      \r\n}\r\n    function OpenNow() {\r\n     \r\n       $(\"Loading\").style.width=OverW+\"px\";\r\n       $(\"Loading\").style.left=lefts+\"px\";\r\n       $(\"Loading\").style.height=OverH+\"px\";\r\n       $(\"Loading\").style.top=tops+\"px\"\r\n       $(\"Loading\").style.display='';\r\n       $(\"Loading\").align=\"center\";\r\n       if(\"iactive\"==type){\r\n");
      out.write("       //添加网动功能\r\n       //文本会议功能\r\n          var aTextChat = document.createElement(\"a\");\r\n           aTextChat.href=\"#\";\r\n            aTextChat.onclick = function(){\r\n\t\t\tIM_TextChat();}\r\n          aTextChat.innerHTML=\"发送文本消息\";\r\n           $(\"Loading\").appendChild(aTextChat);\r\n           $(\"Loading\").appendChild(document.createElement(\"br\"));\r\n       \r\n        //视频会议功能\r\n           var aAVChat = document.createElement(\"a\");\r\n           aAVChat.href=\"#\";\r\n            aAVChat.onclick = function(){\r\n\t\t\tjscript:IM_AVChat();}\r\n           aAVChat.innerHTML=\"发送视频聊天\";\r\n            $(\"Loading\").appendChild(aAVChat);\r\n           $(\"Loading\").appendChild(document.createElement(\"br\"));\r\n        //邀请会议功能\r\n           var aJoinMeeting = document.createElement(\"a\");\r\n           aJoinMeeting.href=\"#\";\r\n           aJoinMeeting.onclick = function(){\r\n\t\t\tjscript:IM_InviteJoinMeeting();}\r\n           aJoinMeeting.innerHTML=\"邀请参加会议\";\r\n            $(\"Loading\").appendChild(aJoinMeeting);\r\n            $(\"Loading\").appendChild(document.createElement(\"br\"));\r\n");
      out.write("        //内部邮件功能\r\n            var aemal = document.createElement(\"a\");\r\n             aemal.href=\"#\";\r\n             if(\"1\"==emailtype){\r\n             aemal.onclick = function(){jscript:openEmalPage();}\r\n\t           }else{\r\n            aemal.onclick = function(){jscript:openEmalPage1();}\r\n               }\t\t\t\r\n              aemal.innerHTML=\"发送邮件\";\r\n             $(\"Loading\").appendChild(aemal);\r\n              $(\"Loading\").appendChild(document.createElement(\"br\"));\r\n         //添加隐藏参数，以便方法调用\r\n         //当前登陆人帐号\r\n              var input1 = document.createElement(\"input\");\r\n              input1.type=\"hidden\";\r\n               input1.value=cds;\r\n               input1.id=\"name11\";\r\n                $(\"Loading\").appendChild(input1);\r\n         //通讯人帐号\r\n               var input2 = document.createElement(\"input\");\r\n                input2.type=\"hidden\";\r\n                 input2.value=ChangeDesc;\r\n                   input2.id=\"name22\";\r\n                   $(\"Loading\").appendChild(input2);\r\n         //通讯人ID\r\n               var input3 = document.createElement(\"input\");\r\n");
      out.write("               input3.type=\"hidden\";\r\n               input3.value=userid;\r\n                input3.id=\"name33\";\r\n                $(\"Loading\").appendChild(input3);\r\n      }else if(\"iactive1\"==type)\r\n          {\r\n          \r\n          //添加网动功能\r\n          //文本会议功能\r\n            var aTextChat = document.createElement(\"a\");\r\n             aTextChat.href=\"#\";\r\n             aTextChat.onclick = function(){\r\n\t\t  \t  IM_TextChat();}\r\n              aTextChat.innerHTML=\"发送文本消息\";\r\n               $(\"Loading\").appendChild(aTextChat);\r\n             $(\"Loading\").appendChild(document.createElement(\"br\"));\r\n       \r\n        //视频会议功能\r\n            var aAVChat = document.createElement(\"a\");\r\n           aAVChat.href=\"#\";\r\n             aAVChat.onclick = function(){\r\n\t\t\tjscript:IM_AVChat();}\r\n             aAVChat.innerHTML=\"发送视频聊天\";\r\n             $(\"Loading\").appendChild(aAVChat);\r\n            $(\"Loading\").appendChild(document.createElement(\"br\"));\r\n        //邀请会议功能\r\n             var aJoinMeeting = document.createElement(\"a\");\r\n             aJoinMeeting.href=\"#\";\r\n");
      out.write("             aJoinMeeting.onclick = function(){\r\n\t\t\tjscript:IM_InviteJoinMeeting();}\r\n            aJoinMeeting.innerHTML=\"邀请参加会议\";\r\n             $(\"Loading\").appendChild(aJoinMeeting);\r\n         //添加隐藏参数，以便方法调用\r\n         //当前登陆人帐号\r\n              var input1 = document.createElement(\"input\");\r\n              input1.type=\"hidden\";\r\n              input1.value=cds;\r\n              input1.id=\"name11\";\r\n              $(\"Loading\").appendChild(input1);\r\n         //通讯人帐号\r\n              var input2 = document.createElement(\"input\");\r\n              input2.type=\"hidden\";\r\n              input2.value=ChangeDesc;\r\n              input2.id=\"name22\";\r\n              $(\"Loading\").appendChild(input2);\r\n         //通讯人ID\r\n              var input3 = document.createElement(\"input\");\r\n              input3.type=\"hidden\";\r\n               input3.value=userid;\r\n               input3.id=\"name33\";\r\n              $(\"Loading\").appendChild(input3);\r\n          \r\n          \r\n          }else{\r\n           //九思网动功能\r\n           //短消息功能\r\n            \r\n             var achat = document.createElement(\"a\");\r\n");
      out.write("             achat.href=\"#\";\r\n             achat.onclick = function(){jscript:chat();}\r\n             achat.innerHTML=\"发送即时消息\";\r\n             $(\"Loading\").appendChild(achat);\r\n             $(\"Loading\").appendChild(document.createElement(\"br\"));\r\n           //内部邮件功能\r\n            var aemal = document.createElement(\"a\");\r\n             aemal.href=\"#\";\r\n            if(\"1\"==emailtype){\r\n            aemal.onclick = function(){jscript:openEmalPage();}\r\n\t          }\r\n            else{\r\n            aemal.onclick = function(){jscript:openEmalPage1();}\r\n              }\t\t\t\r\n            aemal.innerHTML=\"发送邮件\";\r\n            $(\"Loading\").appendChild(aemal);\r\n          //添加隐藏参数，以便方法调用\r\n           //通讯人ID\r\n           var input3 = document.createElement(\"input\");\r\n           input3.type=\"hidden\";\r\n           input3.value=userid;\r\n           input3.id=\"name33\";\r\n           $(\"Loading\").appendChild(input3);\r\n \r\n         }\r\n       \r\n      \r\n    }\r\n\r\n      \r\n\r\n\r\n\r\nvar xmlhttp;\r\n// function activegetUsername(a)\r\n// {\r\n   \r\n   // if (window.XMLHttpRequest) {\r\n");
      out.write("    //    xmlhttp = new XMLHttpRequest();\r\n    ///    if (xmlhttp.overrideMimeType) {\r\n        //    xmlhttp.overrideMimeType(\"text/xml\");\r\n      //  }\r\n   /// } else if (window.ActiveXObject) {\r\n   //     var activexName = [\"MSXML2.XMLHTTP\",\"Microsoft.XMLHTTP\"];\r\n    //    for (var i = 0; i < activexName.length; i++) {\r\n        //    try{\r\n       //         xmlhttp = new ActiveXObject(activexName[i]);\r\n      //          break;\r\n      //      } catch(e){\r\n      //      }\r\n     //   }\r\n  //  }\r\n   \r\n   // xmlhttp.onreadystatechange = activecallback;\r\n   // xmlhttp.open(\"GET\",\"");
      out.print(path);
      out.write("/public/jsp/activegetusername.jsp?userid=\"+a,true);\r\n    //xmlhttp.send(null);\r\n //}\r\n\r\n//function activecallback() {\r\n    \r\n   // if (xmlhttp.readyState == 4) {\r\n     //   if (xmlhttp.status == 200) {\r\n      //    var responseText = xmlhttp.responseText;\r\n       //    responseText = responseText.replace(/\\s+/, '');\r\n         //  responseText=responseText.substring(1,responseText.length-1);\r\n        //   ChangeDesc=responseText;\r\n        //   window.setTimeout(\"OpenNow()\",10)\r\n      //  } else {\r\n       //     alert(\"出错了\");\r\n           \r\n      //  }\r\n    //}\r\n//}\r\n var mx,my;\r\n  function yinc()\r\n  {\r\n  document.getElementById(\"Loading\").style.display='none';\r\n  }  \r\n  \r\n  \r\n  function mouseMove(ev) \r\n{ \r\nev= ev || window.event; \r\n  var mousePos = mouseCoords(ev); \r\n  //alert(ev.pageX); \r\n     mx = mousePos.x; \r\n     my = mousePos.y; \r\n} \r\n\r\n  function mouseCoords(ev) \r\n { \r\n   if(ev.pageX || ev.pageY){ \r\n   return {x:ev.pageX, y:ev.pageY}; \r\n  } \r\n  return { \r\n     x:ev.clientX + document.body.scrollLeft - document.body.clientLeft, \r\n");
      out.write("     y:ev.clientY + document.body.scrollTop     - document.body.clientTop \r\n}; \r\n} \r\n\r\n function closeNow()\r\n       {\r\n       \r\n       if(my>(tops+OverH)||mx>(lefts+OverW)||my<tops||mx<lefts)\r\n       {\r\n         $(\"Loading\").style.display='none';\r\n         \r\n       } \r\n        window.setTimeout(\"closeNow()\",3000);\r\n      \r\n       }\r\n\r\n\r\n document.onmousemove = mouseMove; \r\n ///////////////////////////////////////////////////////////////////////////////\r\n \r\n function jschat(a){\t  \r\n\t\tvar wid=428;\r\n\t\tvar heigh=283;\r\n\t\t//var userid=document.getElementById(\"name33\").value;\r\n\t\t");
if(BrowserJudge.isFirefox(request)){
      out.write("\t\r\n\t\t  var popwin=JSMainWinOpen2('/jsoa/chat/chat.jsp?id='+a,'','',445,320)\r\n\t\t");
}else{
      out.write("\r\n\t\t    var popwin=JSMainWinOpen2('/jsoa/chat/chat.jsp?id='+a,'','',445,283)\r\n\t\t");
}
      out.write("\r\n     // document.getElementById(\"Loading\").style.display='none';\r\n\r\n        }\r\n \r\n  function jsopenEmalPage(a){\r\n    \t    \r\n    var element=parent.document.all.content1;\r\n    if(element==null){\r\n    \telement=parent.parent.document.all.content1;\r\n    }\r\n    var offsetTop = element.offsetTop; \r\n    var offsetLeft = element.offsetLeft; \r\n    var offsetWidth = element.offsetWidth; \r\n    var offsetHeight = element.offsetHeight; \r\n    \r\n    \r\n\r\n    while( element = element.offsetParent ) \r\n   { \r\n        offsetTop += element.offsetTop; \r\n        offsetLeft += element.offsetLeft; \r\n   }\r\n    var winTop=parent.window.screenTop;\r\n    var winLeft=parent.window.screenLeft; \r\n    \r\n    var Top=offsetTop+winTop;\r\n   var Left=offsetLeft+winLeft;\r\n   \r\n  \r\n    \r\n   offsetHeight=offsetHeight-33;\r\n   offsetWidth=offsetWidth-10;\r\n   \r\n    \r\n    window.open(\"/jsoa/innerMailAction.do?action=openAddMail&id1=\"+a,'','TOP='+Top+',LEFT='+Left+',scrollbars=yes,resizable=yes,width='+offsetWidth+',height='+offsetHeight);\r\n}\r\n function jsopenEmalPage1(a){\r\n");
      out.write("    \r\n    var winTop=237;\r\n    var winLeft=145; \r\n    var offsetHeight=473;\r\n    var offsetWidth=872;\r\n    \r\n    window.open(\"/jsoa/innerMailAction.do?action=openAddMail&id1=\"+a,'','TOP='+winTop+',LEFT='+winLeft+',scrollbars=yes,resizable=yes,width='+offsetWidth+',height='+offsetHeight);\r\n}\r\n\r\nfunction jsopenURL(a){\r\n   \r\n    if(\"null\"==a||\"\"==a)\r\n    {\r\n    alert(\"该用户没有添写手机信息，不能发送手机短信\");\r\n    }else{\r\n    var element;\r\n    element=parent.document.all.content1;\r\n    if(element==null){\r\n    \telement=parent.parent.document.all.content1;\r\n    }\r\n    \r\n    var offsetTop = element.offsetTop; \r\n    var offsetLeft = element.offsetLeft; \r\n    var offsetWidth = element.offsetWidth; \r\n    var offsetHeight = element.offsetHeight; \r\n    while( element = element.offsetParent ) \r\n    { \r\n        offsetTop += element.offsetTop; \r\n        offsetLeft += element.offsetLeft; \r\n    }\r\n    var winTop=parent.window.screenTop;\r\n    var winLeft=parent.window.screenLeft; \r\n    \r\n    var Top=offsetTop+winTop;\r\n    var Left=offsetLeft+winLeft;\r\n");
      out.write("    \r\n    offsetHeight=offsetHeight-33;\r\n    offsetWidth=offsetWidth-10;\r\n    \r\n    window.open('/jsoa/MessageAction.do?action=openAddMsg&usermod='+a,'','TOP='+Top+',LEFT='+Left+',scrollbars=yes,resizable=yes,width='+offsetWidth+',height='+offsetHeight);\r\n    }\r\n}\r\n\r\n\r\nfunction jsopenURL1(a){\r\n    if(\"null\"==a||\"\"==a||null==a)\r\n    {\r\n    alert(\"该用户没有添写手机信息，不能发送手机短信\");\r\n    }else\r\n    {\r\n     \r\n   \r\n     var Top=237;\r\n    var Left=145; \r\n    var offsetHeight=473;\r\n    var offsetWidth=872;\r\n    \r\n    window.open('/jsoa/MessageAction.do?action=openAddMsg&usermod='+a,'','TOP='+Top+',LEFT='+Left+',scrollbars=yes,resizable=yes,width='+offsetWidth+',height='+offsetHeight);\r\n    }\r\n    \r\n    \r\n   \r\n\r\n}\r\n\r\n\r\nfunction sendWebEmail(isusd,sta){\r\n   if(\"0\"==isusd)\r\n   {\r\n   alert(\"你没有设置外部邮件，不能发送外部邮件！\");\r\n   }else{\r\n   \r\n   \r\n       if(\"\"==sta||null==sta||\"null\"==sta)\r\n       {\r\n        alert(\"该用户没有设置外部邮件，不能发送外部邮件！\");       \r\n       \r\n       }else{\r\n\t       var element;\r\n\t    element=parent.document.all.content1;\r\n\t    if(element==null){\r\n");
      out.write("\t    \telement=parent.parent.document.all.content1;\r\n\t    }\r\n\t    \r\n\t    var offsetTop = element.offsetTop; \r\n\t    var offsetLeft = element.offsetLeft; \r\n\t    var offsetWidth = element.offsetWidth; \r\n\t    var offsetHeight = element.offsetHeight; \r\n\t    while( element = element.offsetParent ) \r\n\t    { \r\n\t        offsetTop += element.offsetTop; \r\n\t        offsetLeft += element.offsetLeft; \r\n\t    }\r\n\t    var winTop=parent.window.screenTop;\r\n\t    var winLeft=parent.window.screenLeft; \r\n\t    \r\n\t    var Top=offsetTop+winTop;\r\n\t    var Left=offsetLeft+winLeft;\r\n\t    \r\n\t    offsetHeight=offsetHeight-33;\r\n\t    offsetWidth=offsetWidth-10;\r\n\t    \r\n\t    window.open('/jsoa/webMail.do?method=goWriteMail&mailUrlw='+sta,'','TOP='+Top+',LEFT='+Left+',scrollbars=yes,resizable=yes,width='+offsetWidth+',height='+offsetHeight);\r\n       \r\n             \r\n       \r\n       }\r\n\r\n   }\r\n\r\n\r\n\r\n}\r\n\r\n function sendWebEmail1(isusd,sta){\r\n     if(\"0\"==isusd)\r\n   {\r\n   alert(\"你没有设置外部邮件，不能发送外部邮件！\");\r\n   }else{\r\n   \r\n   \r\n       if(\"\"==sta||null==sta||\"null\"==sta)\r\n");
      out.write("       {\r\n        alert(\"该用户没有设置外部邮件，不能发送外部邮件！\");       \r\n       \r\n       }else{\r\n       \r\n       var winTop=237;\r\n       var winLeft=145; \r\n       var offsetHeight=473;\r\n       var offsetWidth=872;\r\n    window.open(\"/jsoa/webMail.do?method=goWriteMail&mailUrlw=\"+sta+\",\",'','TOP='+winTop+',LEFT='+winLeft+',scrollbars=yes,resizable=yes,width='+offsetWidth+',height='+offsetHeight);\r\n       \r\n       \r\n       }\r\n\r\n   }\r\n}\r\n\r\n\r\n</SCRIPT>\r\n\r\n\r\n");
      out.write("\r\n\r\n\r\n\r\n");
      out.write("\r\n<script language=\"JScript.Encode\" src=\"/jsoa/js/browinfo.js\"></script>\r\n<script language=\"JScript.Encode\" src=\"/jsoa/js/rtxint.js\"></script>\r\n<script language=\"javascript\">\r\n\r\nfunction rtxonline(a){\r\n\t RAP(a);\r\n}\r\n\r\n</script>");
      out.write("\r\n\r\n<html>\r\n\t<head>\r\n\t<style type=\"text/css\">\r\n\t body{ font-size:12px;}\r\n\t.border-top{border-top:1px solid #b1c8d7;}\r\n\t.td{background-image:url(/jsoa/images/left_cent.gif); color:#2c4a77; font-weight:bold;}\r\n\t.table{border:1px solid #b1c8d7;}\r\n\t.table2{border-left:1px solid #b1c8d7;border-right:1px solid #b1c8d7;border-bottom:1px solid #b1c8d7;}\r\n\t</style>\r\n");

//判断Email使用类型
com.js.system.util.SysSetupReader ssReader=com.js.system.util.SysSetupReader.getInstance();
String emailType=ssReader.emailType();
String isSetWebEmail="0";
  if("1".equals(emailType))
  {
    List list=new ArrayList();
    String userId=session.getAttribute("userId").toString();
    list=WebMailAccManager.getInstance().getMyAccList(Long.parseLong(userId));
    if(!list.isEmpty())
     {
     isSetWebEmail="1";
     }
}
         String us=(String) request.getSession().getAttribute("userAccount"); 
           List userOnlinList=new ArrayList();
		String usd=ReadActiveXml.getReadActive().getUse();
		String status="";
		String rtxstatusx="";
		if("rtx".equals(usd)){
		RTXSvrApi rtxApi=new RTXSvrApi();
		int rtxstatus=rtxApi.userIsOnline(us);
		if(rtxstatus==1||rtxstatus==2)
			{
			rtxstatusx="true";
			}
			}
			
		if("iactive".equals(usd)){
		TblUserBD tblUserBD = new TblUserBD();
		 TblUser tblUser = new TblUser();
		TblUserStatusBD tblUserStatusBD = new TblUserStatusBD();
		 tblUser = tblUserBD.findTblUser(us);
		 status= tblUserStatusBD.findstatus(tblUser.getId());
		 userOnlinList = tblUserStatusBD.findUserOnline();
		}else
		{
		status="false";
		}
		
		
		List browserlist=(List)request.getAttribute("browserlist");
		List nobrowserlist=(List)request.getAttribute("nobrowserlist");	
		int browsercount=browserlist.size();
		int nobrowsercount=nobrowserlist.size();
		
      out.write("\r\n  <link rel=stylesheet type=\"text/css\" href=\"/jsoa/style/cssmain.css\">    \r\n<title></title>\r\n</head>\r\n\t<body >\r\n\t<table width=\"100%\">\r\n\t<tr>\r\n\t\t<td colspan=\"3\">\r\n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"table\">\r\n\t\t\t\t<tr>\r\n\t\t\t\t\t<td width=\"19\"><img src=\"/jsoa/images/left_top.gif\"/></td>\r\n\t     \t\t\t<td class=\"td\">已查看用户(");
      out.print(browsercount);
      out.write("人)</td>\r\n\t\t\t\t</tr>\r\n\t\t\t</table>\r\n\t\t</td>\r\n   </tr>\r\n\t");

	for(int i=0;i<browserlist.size();i++)
	{
	 Object[] obj = (Object[])browserlist.get(i);
	  if(i%3 == 0)
     {
      out.write("\r\n      <tr>");
}
      out.write("\r\n\t <td  width=\"30%\">\r\n                   ");
if(0==(Integer)obj[4]) {
      out.write("\r\n                  <img src=\"/jsoa/images/la_ba_off.gif\" alt=\"发即时消息\" border=\"0\" style=\"cursor:pointer;\" onClick=\"javascript:jschat('");
      out.print(obj[0]);
      out.write("')\"/>\r\n                   ");
} else {
      out.write("\r\n                  <img src=\"/jsoa/images/la_ba.gif\" alt=\"发即时消息\" border=\"0\" style=\"cursor:pointer;\" onClick=\"javascript:jschat('");
      out.print(obj[0]);
      out.write("')\"/>\r\n                  ");
} if("1".equals(emailType)){
      out.write("\r\n                  <img src=\"/jsoa/images/mail.gif\" border=\"0\" alt=\"发外部邮件\" style=\"cursor:pointer;\" onClick=\"javascript:sendWebEmail1('");
      out.print(isSetWebEmail);
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(obj[5]);
      out.write("')\"/>\r\n                    ");
} 
      out.write("\r\n                  <img src=\"/jsoa/imges/duanxin.gif\" border=\"0\" alt=\"发手机短息\" style=\"cursor:pointer;\" onClick=\"javascript:jsopenURL1('");
      out.print(obj[2]);
      out.write("')\"/>\r\n\t         \r\n\t\t    ");

	         String ta="0";
		    if(userOnlinList.contains(obj[0])){
		     ta="1";
		    } 
		    if("true".equals(status))
              {
                
      out.write("    \r\n                 ");
if("0".equals(ta)) {
      out.write("\r\n                  <img src=\"/jsoa/images/man_off_16.bmp\" border=\"0\" style=\"cursor:pointer;\" onclick=\"javascript:OpenDiv(115,70,'");
      out.print(obj[0]);
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(us);
      out.write("','iactive1','1','");
      out.print(obj[3]);
      out.write("')\"  />\r\n                  ");
} else {
      out.write("\r\n                  <img src=\"/jsoa/images/man_on_16.bmp\" border=\"0\" style=\"cursor:pointer;\" onclick=\"javascript:OpenDiv(115,70,'");
      out.print(obj[0]);
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(us);
      out.write("','iactive1','1','");
      out.print(obj[3]);
      out.write("')\"  />\r\n                  ");
}} else if("rtx".equals(usd)&&"true".equals(rtxstatusx))
                  {
                  
      out.write("\r\n                  <img src=\"/jsoa/images/rtx/rtxon.GIF\" border=\"0\" style=\"cursor:pointer;\" onload=\"rtxonline('");
      out.print(obj[3]);
      out.write("');\"> \r\n                ");
}  
      out.write("\r\n\t \t\t\t  <span style=\"color:green;\">");
      out.print(obj[1]);
      out.write("</span></td>\r\n\t");

	  if(i%3 ==2)
     {
      out.write("\r\n      </tr>");
}
      out.write('\r');
      out.write('\n');
      out.write('	');
}
	
      out.write("\t\r\n\t<tr>\r\n\t\t<td colspan=\"3\">\r\n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"table\">\r\n\t\t\t\t<tr>\r\n\t\t\t\t\t<td width=\"19\"><img src=\"/jsoa/images/left_top.gif\"/></td>\r\n\t     \t\t\t<td class=\"td\">未查看用户(");
      out.print(nobrowsercount);
      out.write("人)：</td>\r\n\t\t\t\t</tr>\r\n\t\t\t</table>\r\n\t\t</td>\r\n\t</tr>\t\r\n\t\r\n\t");

	for(int i=0;i<nobrowserlist.size();i++)
	{
	 Object[] obj = (Object[])nobrowserlist.get(i);
	  if(i%3 == 0)
     {
      out.write("\r\n      <tr>");
}
      out.write("\r\n\t <td  width=\"30%\">\r\n\t \r\n                   ");
if(0==new Integer(obj[4].toString())) {
      out.write("\r\n                  <img src=\"/jsoa/images/la_ba_off.gif\" border=\"0\" style=\"cursor:pointer;\" onClick=\"javascript:jschat('");
      out.print(obj[0]);
      out.write("')\"/>\r\n                   ");
} else {
      out.write("\r\n                  <img src=\"/jsoa/images/la_ba.gif\" border=\"0\" style=\"cursor:pointer;\" onClick=\"javascript:jschat('");
      out.print(obj[0]);
      out.write("')\"/>\r\n                   ");
} if("1".equals(emailType)){ 
      out.write("\r\n                  <img src=\"/jsoa/images/mail.gif\" border=\"0\" style=\"cursor:pointer;\" onClick=\"javascript:sendWebEmail1('");
      out.print(isSetWebEmail);
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(obj[5]);
      out.write("')\"/>\r\n                   ");
} 
      out.write("\r\n                  <img src=\"/jsoa/imges/duanxin.gif\" border=\"0\" style=\"cursor:pointer;\" onClick=\"javascript:jsopenURL1('");
      out.print(obj[2]);
      out.write("')\"/>\r\n\t               ");

	         String ta="0";
		     for(int z=0;z<userOnlinList.size();z++)
		         {
		          
		           if(obj[0].equals((Long)userOnlinList.get(z)))
		             ta="1";
		             }
		    if("true".equals(status))
              {
                
      out.write("    \r\n                 ");
if("0".equals(ta)) {
      out.write("\r\n                  <img src=\"/jsoa/images/man_off_16.bmp\" border=\"0\" style=\"cursor:pointer;\" onclick=\"javascript:OpenDiv(115,70,'");
      out.print(obj[0]);
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(us);
      out.write("','iactive1','1','");
      out.print(obj[3]);
      out.write("')\"  />\r\n                  ");
} else {
      out.write("\r\n                  <img src=\"/jsoa/images/man_on_16.bmp\" border=\"0\" style=\"cursor:pointer;\" onclick=\"javascript:OpenDiv(115,70,'");
      out.print(obj[0]);
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(us);
      out.write("','iactive1','1','");
      out.print(obj[3]);
      out.write("')\"  />\r\n                  ");
}}else if("rtx".equals(usd)&&"true".equals(rtxstatusx))
                  {
                  
      out.write("\r\n                  <img src=\"/jsoa/images/rtx/rtxon.GIF\" border=\"0\" style=\"cursor:pointer;\" onload=\"rtxonline('");
      out.print(obj[3]);
      out.write("');\"> \r\n                ");
}  
      out.write("\r\n\t              \r\n\t              <span style=\"color:red;\">");
      out.print(obj[1]);
      out.write("</span>\r\n\t            </td>\r\n\t");
if(i%3 ==2)
     {
      out.write("</tr>");
}
      out.write('\r');
      out.write('\n');
      out.write('	');
}
      out.write("\t\r\n\t\t\r\n\r\n   </table>\r\n<div id=\"Loading\" style=\"display:none; position:absolute; left:354px; top:44px; width:85px; height:159px\" ondblclick=\"this.style.display='none'\">\r\nf</div>\r\n\t</body>\r\n</html>\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n");
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
