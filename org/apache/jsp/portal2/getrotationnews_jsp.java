/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:40:54 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.portal2;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.oa.info.infomanager.service.*;
import com.js.oa.portal.service.*;
import java.util.*;
import com.js.oa.portal.po.*;

public final class getrotationnews_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("com.js.oa.portal.service");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("com.js.oa.portal.po");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_packages.add("com.js.oa.info.infomanager.service");
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n<HTML>\r\n<style type=\"text/css\">\r\n.container{\r\n\twidth:219px;\r\n\theight:127px;\r\n\tborder:1px solid #898989;\t\r\n\tposition:relative;\r\n}\r\n#idPicText{\r\n\tpadding-top:6px;\t\t\r\n\tline-height:18px;\r\n\ttext-align:center;\r\n\tfont-weight:bold;\r\n\twidth:219px;\r\n\twhite-space:nowrap;\t\r\n\tcolor:#FFFFFF;\r\n\toverflow:hidden;\r\n\tfont-size:13px;\r\n}\r\n#idPicText a{\r\n\ttext-decoration:none;\r\n\tcolor:#333;\r\n\tdisplay:block;\r\n}\r\n\r\n\r\n\r\n#idNum{ position:absolute; right:0px; bottom:0px;}\r\n#idNum li{\r\n\tfloat: left;\r\n\tlist-style:none;\r\n\tcolor: #fff;\r\n\ttext-align: center;\r\n\twidth: 14px;\r\n\theight: 14px;\r\n\tfont-family: Arial;\r\n\tfont-size: 11px;\r\n\tcursor: pointer;\r\n\tmargin: 1px;\r\n\tborder: 1px solid #707070;\r\n\tbackground-color: #696969;\r\n}\r\n  #idNum li.on{\r\n  \twidth: 15px;\r\n\theight: 15px;\r\n\tfont-size: 12px;\r\n\tborder: 0;\r\n\tbackground-color: #ce0609;\r\n\tfont-weight: bold;\r\n}\r\n\r\n</style>\r\n<HEAD>\r\n<TITLE> New Document </TITLE>\r\n<script type=\"text/javascript\">\r\nfunction loadF(){\r\n\tdocument.getElementById('idPicShow').style.width=document.body.clientWidth;\r\n");
      out.write("\tdocument.getElementById('idPicText').style.width=document.body.clientWidth;\r\n}\r\n</script>\r\n</HEAD>\r\n\r\n<BODY onload=\"loadF();\" onresize=\"location.reload();\">\r\n\r\n<script type=\"text/javascript\">\r\nvar brow = navigator.userAgent;\r\nvar isIE = (brow.indexOf(\"MSIE\")>0 && !brow.indexOf(\"MSIE 10\")>0) ? true : false;\r\n\r\nvar $ = function (id) {\r\n\treturn \"string\" == typeof id ? document.getElementById(id) : id;\r\n};\r\n\r\nvar Class = {\r\n\tcreate: function() {\r\n\t\treturn function() { this.initialize.apply(this, arguments); }\r\n\t}\r\n}\r\n\r\nvar Extend = function(destination, source) {\r\n\tfor (var property in source) {\r\n\t\tdestination[property] = source[property];\r\n\t}\r\n}\r\n\r\nvar Bind = function(object, fun) {\r\n\treturn function() {\r\n\t\treturn fun.apply(object, arguments);\r\n\t}\r\n}\r\n\r\nvar Each = function(list, fun){\r\n\tfor (var i = 0, len = list.length; i < len; i++) { fun(list[i], i); }\r\n};\r\n\r\n\r\n//ie only\r\nvar RevealTrans = Class.create();\r\nRevealTrans.prototype = {\r\n  initialize: function(container, options) {\r\n\tthis._img = document.createElement(\"img\");\r\n");
      out.write("\tthis._a = document.createElement(\"a\");\r\n\t\r\n\tthis._timer = null;//计时器\r\n\tthis.Index = 0;//显示索引\r\n\tthis._onIndex = -1;//当前索引\r\n\t\r\n\tthis.SetOptions(options);\r\n\t\r\n\tthis.Auto = !!this.options.Auto;\r\n\tthis.Pause = Math.abs(this.options.Pause);\r\n\tthis.Duration = Math.abs(this.options.Duration);\r\n\tthis.Transition = parseInt(this.options.Transition);\r\n\tthis.List = this.options.List;\r\n\tthis.onShow = this.options.onShow;\r\n\t\r\n\t//初始化显示区域\r\n\tthis._img.style.visibility = \"hidden\";//第一次变换时不显示红x图\r\n\tthis._img.style.width = document.body.clientWidth;\r\n\tthis._img.style.height = \"100%\"; \r\n\tthis._img.style.border = 0;\r\n\tthis._img.onmouseover = Bind(this, this.Stop);\r\n\tthis._img.onmouseout = Bind(this, this.Start);\r\n\tisIE && (this._img.style.filter = \"revealTrans()\");\r\n\t\r\n\tthis._a.target = \"_blank\";\r\n\t\r\n\t$(container).appendChild(this._a).appendChild(this._img);\r\n  },\r\n  //设置默认属性\r\n  SetOptions: function(options) {\r\n\tthis.options = {//默认值\r\n\t\tAuto:\t\ttrue,//是否自动切换\r\n\t\tPause:\t\t2000,//停顿时间(微妙)\r\n\t\tDuration:\t1,//变换持续时间(秒)\r\n\t\tTransition:\t23,//变换效果(23为随机)\r\n");
      out.write("\t\tList:\t\t[],//数据集合,如果这里不设置可以用Add方法添加\r\n\t\tonShow:\t\tfunction(){}//变换时执行\r\n\t};\r\n\tExtend(this.options, options || {});\r\n  },\r\n  Start: function() {\r\n\tclearTimeout(this._timer);\r\n\t//如果没有数据就返回\r\n\t\r\n\t//如果是weblogic应设置为如下两行,若不是应用第三行\r\n\tif(this.List==null) return;\r\n\tif(this.List.length==0) return;\t\r\n\t//if(!this.List.length) return;\r\n\t\r\n\t//修正Index\r\n\tif(this.Index < 0 || this.Index >= this.List.length){ this.Index = 0; }\r\n\t//如果当前索引不是显示索引就设置显示\r\n\tif(this._onIndex != this.Index){ this._onIndex = this.Index; this.Show(this.List[this.Index]); }\r\n\t//如果要自动切换\r\n\tif(this.Auto){\r\n\t\tthis._timer = setTimeout(Bind(this, function(){ this.Index++; this.Start(); }), this.Duration * 1000 + this.Pause);\r\n\t}\r\n  },\r\n  //显示\r\n  Show: function(list) {\r\n\tif(isIE){\r\n\t\t//设置变换参数\r\n\t\twith(this._img.filters.revealTrans){\r\n\t\t\tTransition = this.Transition; Duration = this.Duration; apply(); play();\r\n\t\t}\r\n\t}\r\n\tthis._img.style.visibility = \"\";\r\n\tthis._img.style.cursor=\"pointer\";\r\n\t//设置图片属性\r\n\tthis._img.src = list.img; this._img.alt = list.text;\r\n\t//设置链接\r\n\t//!!list[\"url\"] ? (this._a.href = list[\"url\"]) : this._a.removeAttribute(\"href\");\r\n");
      out.write("\t!!list[\"url\"] ? (this._a.onclick =function(){ textshow(list[\"url\"]); } ) : this._a.removeAttribute(\"href\");\r\n\t//附加函数\r\n\tthis.onShow();\r\n  },\r\n  //添加变换对象\r\n  Add: function(sIimg, sText, sUrl) {\r\n\tthis.List.push({ img: sIimg, text: sText, url: sUrl });\r\n  },\r\n  //停止\r\n  Stop: function() {\r\n\tclearTimeout(this._timer);\r\n  }\r\n};\r\n\r\n\r\n</script>\r\n<div id=\"idPicShow\" class=\"container\">\r\n  <ul id=\"idNum\">\r\n  </ul>\r\n</div>\r\n<div id=\"idPicText\"></div>\r\n\r\n<script>\r\n\r\nvar rvt = new RevealTrans(\"idPicShow\");\r\n\r\n");

//栏目ID
String channelId = request.getParameter("channelId");
//栏目名称
String userChannelName= request.getParameter("userChannelName");
//显示数目
int num = Integer.parseInt(request.getParameter("num"));


String userId = session.getAttribute("userId") == null ? "" : session.getAttribute("userId").toString();
String domainId = session.getAttribute("domainId") == null ? "0" : session.getAttribute("domainId").toString();
String orgId = session.getAttribute("orgId").toString();
String orgIdString = session.getAttribute("orgIdString").toString();
CustomDesktopBD customDesktopBD = new CustomDesktopBD();
List listInfo = customDesktopBD.listInformation(channelId, userId, orgId, orgIdString,domainId);
String imageName = "-1"; //图片新闻图片名称,初始值为-1
String imageNewsTitle = "-1"; //图片新闻标题,初始值为-1
String imageNewsTitleLink = "-1"; //图片新闻标题链接,初始值为-1
int c = 0;
for (int i = 0; i < listInfo.size(); i++) {
	//设置图片显示数目
	if(c>num-1){
		break;
	}

	CustomdesktopItemVO cd = new CustomdesktopItemVO();
	Object[] obj = (Object[]) listInfo.get(i);
	int channelType = ((Integer) obj[7]).intValue();

	//图片新闻
	String informationId = obj[1].toString();
	InformationAccessoryBD accBD = new InformationAccessoryBD();
	List listAcc = accBD.getAccessory(informationId);
	for (int k = 0; k < listAcc.size(); k++) {		
		Object[] objAcc = (Object[]) listAcc.get(k);
		if (((Integer) objAcc[4]).intValue() == 1) {
			//设置图片显示数目
			if(k>num-1){
				break;
			}
			c++;
			imageName = objAcc[2].toString(); //标记已找到图片,标记为附件名称
			//查找上传方式
			java.util.Map sysMap = com.js.system.util.SysSetupReader.getInstance().getSysSetupMap(domainId);
			int smartInUse = 0;
			if(sysMap != null && sysMap.get("附件上传") != null){
				smartInUse = Integer.parseInt(sysMap.get("附件上传").toString());
			}
			String fileServer="";
			if(com.js.util.config.SystemCommon.getUseClusterServer()==1){
				fileServer = com.js.util.config.SystemCommon.getClusterServerUrl();
			}else{
				fileServer = session.getAttribute("fileServer").toString();
			}
			  String src_myLove="0000";
			  if(imageName!=null && imageName.toString().length()>6 && imageName.toString().substring(4,5).equals("_")){
				  src_myLove=imageName.toString().substring(0,4);
			  }else{
				  src_myLove="0000";
			  }
			
			
			imageName=(smartInUse==1?"/jsoa":fileServer)+"/upload/"+src_myLove+"/information/"+imageName;
			//
			imageNewsTitle = obj[2].toString(); //标题
			imageNewsTitleLink =
					"/jsoa/InformationAction.do?" +
					"action=openInfo&informationId=" + obj[1] +
					"&redHead=" + obj[5] + "&informationType=" +
					obj[6] +
					"&channelType=" + channelType +
					"&userChannelName=" +
					userChannelName;
			
			if(imageNewsTitle.length()>15){
				imageNewsTitle=imageNewsTitle.substring(0,13)+"...";
			}

      out.write("\r\nrvt.Add('");
      out.print(imageName);
      out.write("', '");
      out.print(obj[2].toString());
      out.write("', '");
      out.print(imageNewsTitleLink);
      out.write("');\r\n");
			
		}
	}
}

      out.write("\r\n//添加变换对象\r\n//rvt.Add('http://images.cnblogs.com/cnblogs_com/cloudgamer/143727/r_rt_1.jpg', '图片变换效果', 'http://www.cnblogs.com/cloudgamer/archive/2008/05/23/1205642.html');\r\n//rvt.Add('http://images.cnblogs.com/cnblogs_com/cloudgamer/143727/r_rt_2.jpg', '图片滑动展示效果', 'http://www.cnblogs.com/cloudgamer/archive/2008/05/13/1194272.html');\r\n//rvt.Add('http://images.cnblogs.com/cnblogs_com/cloudgamer/143727/r_rt_3.jpg', '图片切换展示效果', 'http://www.cnblogs.com/cloudgamer/archive/2008/07/06/1236770.html');\r\n\r\n\r\nvar oText = $(\"idPicText\"), arrImg = [];\r\n\r\nvar oNum = $(\"idNum\"), arrNum = [];\r\n\r\n//设置图片列表\r\nEach(rvt.List, function(list, i){\r\n\t//图片式\r\n\tvar img = document.createElement(\"img\");\r\n\timg.src = list[\"img\"]; img.alt = list[\"text\"];\r\n\tarrImg[i] = img;\r\n//\toList.appendChild(img);\r\n\t//按钮式\r\n\tvar li = document.createElement(\"li\");\r\n\tli.innerHTML = i + 1;\r\n\tarrNum[i] = li;\r\n\toNum.appendChild(li);\r\n\t//事件设置\r\n\timg.onmouseover = li.onmouseover = function(){ rvt.Auto = false; rvt.Index = i; rvt.Start(); };\r\n\timg.onmouseout = li.onmouseout = function(){ rvt.Auto = true; rvt.Start(); };\r\n");
      out.write("});\r\n\r\n//设置图片列表样式 文本显示区域\r\nrvt.onShow = function(){\r\n\tvar i = this.Index, list = this.List[i];\r\n\t//图片式\r\n\tEach(arrImg, function(o){ o.className = \"\"; }); arrImg[i].className = \"on\";\r\n\t//按钮式\r\n\tEach(arrNum, function(o){ o.className = \"\"; }); arrNum[i].className = \"on\";\r\n\t//文本区域\r\n\t//oText.innerHTML = !!list.url ? \"<a href='\" + list.url + \"' target='_blank'>\" + list.text + \"</a>\" : list.text;\r\n\t\r\n\t oText.innerHTML = !!list.url ? \"<a href='#' onclick=textshow('\"+list.url+\"');  >\" + list.text + \"</a>\" : list.text;\r\n\r\n}\r\n\r\nfunction textshow(a)\r\n{\r\n        var element=parent.parent.document.all.content1;\r\n        var offsetWidth = element.offsetWidth; \r\n        var offsetHeight = element.offsetHeight; \r\n       var winTop=window.screenTop;\r\n       var winLeft=window.screenLeft; \r\n       var Top=winTop-40;\r\n       var Left=winLeft-17;\r\n      offsetHeight=offsetHeight-33;\r\n      offsetWidth=offsetWidth-10;\r\n        window.open(a,\"newpage\",'TOP='+Top+',LEFT='+Left+',scrollbars=yes,resizable=yes,width='+offsetWidth+',height='+offsetHeight);\r\n");
      out.write("}\r\n\r\n\r\n//文本显示区域\r\noText.onmouseover = function(){ rvt.Auto = false; rvt.Stop(); };\r\noText.onmouseout = function(){ rvt.Auto = true; rvt.Start(); };\r\n\r\nrvt.Start();\r\n\r\n</script>\r\n</BODY>\r\n</HTML>\r\n");
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