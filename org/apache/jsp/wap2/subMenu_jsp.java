/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:45:57 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.wap2;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.qq.weixin.mp.config.pojo.Pic_SysphotoMenu;
import com.qq.weixin.mp.config.pojo.ClickMenu;
import com.js.oa.weixin.common.util.MobileUtils;
import com.js.oa.webservice.appservice.AuthService;
import java.sql.ResultSet;
import com.js.util.util.DataSourceBase;
import com.qq.weixin.mp.config.pojo.ComplexMenu;
import com.qq.weixin.mp.config.pojo.ViewMenu;
import com.qq.weixin.mp.config.pojo.Menu;
import java.util.List;
import com.qq.weixin.mp.pojo.AppRoom;
import com.qq.weixin.mp.config.pojo.App;

public final class subMenu_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {


private String getDaiBanNum(String userId){
	String sql = "SELECT COUNT(wf_work_id) FROM JSF_WORK WHERE WORKSTATUS=0 AND WF_CUREMPLOYEE_ID = "+userId
				+" AND WORKLISTCONTROL=1 AND WORKDELETE=0";
	//增加延迟待办逻辑
    String dataType = com.js.util.config.SystemCommon.getDatabaseType();
    if(dataType.indexOf("mysql") >= 0){
    	sql +=" and (activityDelaySend is null or activityDelaySend = '' or activityDelaySend<=date_format(now(),'%Y-%c-%d %H:%i:%s'))";
    }else{
    	sql+= " and (activityDelaySend is null or activityDelaySend = '' or TO_DATE(activityDelaySend,'yyyy-mm-dd hh24:mi:ss')<=SYSDATE)";    
    }
	DataSourceBase base = new DataSourceBase();
	int num = 0;
	try{
		base.begin();
		ResultSet rs = base.executeQuery(sql);
		if(rs.next()){
			num = rs.getInt(1);
		}
		rs.close();
	}catch(Exception e){
		e.printStackTrace();
	} finally {
		base.end();
	}
	return "(" + num + ")";
  	}
	private String getDocDaiBanNum(String userId,String type){
		String ty="";
		if("B101".equals(type)){
			ty="send";
		}else if("B102".equals(type)){
			ty="receive";
		}else{
			return "(0)";
		}
		com.js.wap.bean.WorkDealWithBean bean = new com.js.wap.bean.WorkDealWithBean();
		String workFlowIDs = bean.docFlowIdsByType(ty);
		String sql = "SELECT COUNT(wf_work_id) FROM JSF_WORK WHERE WORKSTATUS=0 AND WF_CUREMPLOYEE_ID = "+userId
					+" AND WORKLISTCONTROL=1 AND WORKDELETE=0 and WORKPROCESS_ID IN("+workFlowIDs+")";
		
		//增加延迟待办逻辑
	    String dataType = com.js.util.config.SystemCommon.getDatabaseType();
	    if(dataType.indexOf("mysql") >= 0){
	    	sql +=" and (activityDelaySend is null or activityDelaySend = '' or activityDelaySend<=date_format(now(),'%Y-%c-%d %H:%i:%s'))";
	    }else{
	    	sql+= " and (activityDelaySend is null or activityDelaySend = '' or TO_DATE(activityDelaySend,'yyyy-mm-dd hh24:mi:ss')<=SYSDATE)";    
	    }
		DataSourceBase base = new DataSourceBase();
		int num = 0;
		try{
			base.begin();
			ResultSet rs = base.executeQuery(sql);
			if(rs.next()){
				num = rs.getInt(1);
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			base.end();
		}
		return "(" + num + ")";
	  	}

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
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.qq.weixin.mp.config.pojo.ClickMenu");
    _jspx_imports_classes.add("java.util.List");
    _jspx_imports_classes.add("com.qq.weixin.mp.pojo.AppRoom");
    _jspx_imports_classes.add("com.qq.weixin.mp.config.pojo.ComplexMenu");
    _jspx_imports_classes.add("com.qq.weixin.mp.config.pojo.Menu");
    _jspx_imports_classes.add("java.sql.ResultSet");
    _jspx_imports_classes.add("com.js.util.util.DataSourceBase");
    _jspx_imports_classes.add("com.qq.weixin.mp.config.pojo.Pic_SysphotoMenu");
    _jspx_imports_classes.add("com.js.oa.webservice.appservice.AuthService");
    _jspx_imports_classes.add("com.js.oa.weixin.common.util.MobileUtils");
    _jspx_imports_classes.add("com.qq.weixin.mp.config.pojo.ViewMenu");
    _jspx_imports_classes.add("com.qq.weixin.mp.config.pojo.App");
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
      response.setContentType("text/html;charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
      out.write("\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);


String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String appBh = request.getParameter("appBh");
System.out.println("appBh:"+appBh);
App app = AppRoom.getAppByAppBh(appBh);
String title = "应用菜单";
if(null!=app && null!=app.getAppname() && !"".equals(app.getAppname())){
	title = app.getAppname();
}

String loginType = null==session.getAttribute("loginType") ? "" : session.getAttribute("loginType").toString();

String loadURL = AuthService.appServer + "/jsoa/wap2/index.jsp";
//String loginType = null!=session.getAttribute("loginType") ? session.getAttribute("loginType").toString() : "wap";

String userAgent = request.getHeader("USER-AGENT").toLowerCase();
if(null == userAgent){
	userAgent = "";
}

      out.write("\r\n\r\n<!DOCTYPE html>\r\n<html>\r\n\t<head>\r\n\t    <base href=\"");
      out.print(basePath);
      out.write("\">\r\n\t    \r\n\t    <title>");
      out.print(title );
      out.write("</title>\r\n\t    <meta name=viewport content=\"width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0\">\r\n\t\t<meta name=apple-touch-fullscreen content=YES>\r\n\t\t<meta name=apple-mobile-web-app-capable content=no>\r\n\t\t<meta http-equiv=\"pragma\" content=\"no-cache\">\r\n\t\t<meta http-equiv=\"cache-control\" content=\"no-cache\">\r\n\t\t<meta http-equiv=\"expires\" content=\"0\">\r\n\t\t<style type=\"text/css\">\r\n\t\t#mainDealwithDiv {\r\n\t\t\twidth: 100%;\r\n\t\t\theight: 100%;\r\n\t\t\tposition: fixed;\r\n\t\t\ttop: 0;\r\n\t\t\tleft: 0;\r\n\t\t\tdisplay: none;\r\n\t\t\tz-index: 99999;\r\n\t\t\toverflow: hidden;\r\n\t\t}\r\n\t\t</style>\r\n\t</head>\r\n\r\n\t<body>\r\n\t\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "commonImport.jsp", out, false);
      out.write("\r\n    \t");

    	if("wap".equals(loginType)){
    		
      out.write("\r\n    \t\t<div class=\"top\">\r\n\t\t    \t<a href=\"javascript:closeWindow();\"><img width=\"20\" height=\"15\" src=\"wap2/images/topfh.png\"></a>\r\n\t\t    \t<p>");
      out.print(title );
      out.write("</p>\r\n\t    \t</div>\r\n    \t\t");

    	}
    	
      out.write("\r\n\t    <div class=\"contenter\">\r\n\t    \t<div class=\"center\">\r\n\t\t    \t");

		    	if(null != app){
		    		List<Menu> menus = app.getMenu();
		    		if(null!=menus && menus.size()>0){
		    			ViewMenu vm = null;
		    			for(Menu menu : menus){
		    				
      out.write("\r\n\t\t    \t\t\t\t<div class=\"center-02\"></div>\r\n\t\t    \t\t\t\t");

		    				if(menu instanceof ViewMenu){
		    					vm = (ViewMenu) menu;
		    					String num = "";
		    					String txlType = "";
		    					if("dbsx".equals(app.getAppbh()) && "B1".equals(vm.getMenuId())) num = getDaiBanNum(session.getAttribute("userId").toString());
		    					if("dbsx".equals(app.getAppbh()) && ("B101".equals(vm.getMenuId())||"B102".equals(vm.getMenuId()))) num = getDocDaiBanNum(session.getAttribute("userId").toString(),vm.getMenuId());
		    					if("txl".equals(app.getAppbh())){
		    						if("K2".equals(vm.getMenuId())){
		    							txlType = "(公共)";
		    						} else if("K4".equals(vm.getMenuId())){
		    							txlType = "(个人)";
		    						}
		    					}
		    					
      out.write("\r\n\t\t    \t\t\t\t\t<div class=\"center-01\" onclick=\"showDealwith('");
      out.print(app.getAppbh() );
      out.write("', '");
      out.print(vm.getMenuId() );
      out.write("')\">\r\n\t\t    \t\t\t\t\t\t<img src=\"");
      out.print(vm.getImgURL() );
      out.write("\" align=\"absmiddle\">&nbsp;&nbsp;");
      out.print(vm.getName() + num + txlType );
      out.write("\r\n\t    \t\t\t\t\t\t</div>\r\n\t\t    \t\t\t\t\t");

		    				} else if(menu instanceof ComplexMenu){
		    					ComplexMenu cm = (ComplexMenu) menu;
		    					List<Menu> ms = cm.getSubMenu();
		    					
		    					if(null!=ms && ms.size()>0){
		    						for(Menu m : ms){
		    							if(m instanceof ViewMenu){
		    								vm = (ViewMenu) m;
		    								String txlType = "";
		    								String num = "";
					    					if("dbsx".equals(app.getAppbh()) && "B1".equals(vm.getMenuId())) num = getDaiBanNum(session.getAttribute("userId").toString());
					    					if("dbsx".equals(app.getAppbh()) && ("B101".equals(vm.getMenuId())||"B102".equals(vm.getMenuId()))) num = getDocDaiBanNum(session.getAttribute("userId").toString(),vm.getMenuId());
					    					
					    					if("txl".equals(app.getAppbh())){
					    						if("K2".equals(vm.getMenuId())){
					    							txlType = "(公共)";
					    						} else if("K4".equals(vm.getMenuId())){
					    							txlType = "(个人)";
					    						}
					    					}
					    					
      out.write("\r\n\t    \t\t\t\t\t\t\t\t\t<div class=\"center-01\" onclick=\"showDealwith('");
      out.print(app.getAppbh() );
      out.write("', '");
      out.print(vm.getMenuId() );
      out.write("')\">\r\n\t\t\t\t\t    \t\t\t\t\t\t<img src=\"");
      out.print(vm.getImgURL() );
      out.write("\" align=\"absmiddle\">&nbsp;&nbsp;");
      out.print(vm.getName() + num + txlType );
      out.write("\r\n\t\t\t\t    \t\t\t\t\t\t</div>\r\n\t\t    \t\t\t\t\t\t\t\t");

		    							}
		    						}
		    					}
		    				} else if(menu instanceof ClickMenu){	// 上下班打卡
								ClickMenu clickMenu = (ClickMenu) menu;
								
      out.write("\r\n\t\t\t\t\t\t\t\t<div class=\"center-01\" onclick=\"javascript:window.open('/jsoa/app/sxbdk.html');\">\r\n\t\t    \t\t\t\t\t\t<img src=\"");
      out.print(clickMenu.getImgURL() );
      out.write("\" align=\"absmiddle\">&nbsp;&nbsp;");
      out.print(clickMenu.getName() );
      out.write("\r\n\t    \t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t");

							} else if(menu instanceof Pic_SysphotoMenu){	// 外勤打卡
								Pic_SysphotoMenu picSysphotoMenu = (Pic_SysphotoMenu) menu;
								
      out.write("\r\n\t\t\t\t\t\t\t\t<div class=\"center-01\" onclick=\"javascript:window.open('/jsoa/app/wqdk.html');\">\r\n\t\t    \t\t\t\t\t\t<img src=\"");
      out.print(picSysphotoMenu.getImgURL() );
      out.write("\" align=\"absmiddle\">&nbsp;&nbsp;");
      out.print(picSysphotoMenu.getName() );
      out.write("\r\n\t    \t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t");

							}
		    			}
		    		}
		    	}
		    	
      out.write("\r\n\t    \t</div>\r\n\t    </div>\r\n\t    ");

	    if("wap".equals(loginType)){
	    	
      out.write("\r\n\t\t    ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "footer.jsp", out, false);
      out.write("\r\n\t    \t");

	    }
	    
      out.write("\r\n\t    <div id=\"mainDealwithDiv\">\r\n\t    \t<iframe id=\"mainDealwith\" style=\"width: 100%; height: 100%; overflow: hidden; border: 0;\"></iframe>\r\n\t    </div>\r\n\t      \r\n\t    <script type=\"text/javascript\">\r\n\t    function showDealwith(appBh, menuId){\r\n\t    \twindow.location.href = \"/jsoa/wap2/mainPage.jsp?appBh=\" + appBh + \"&menuId=\" + menuId;\r\n\t    \t//document.getElementById(\"mainDealwithDiv\").style.display = \"block\";\r\n\t    }\r\n\t // 关闭窗口\r\n\t    function closeWindow(){\r\n\t    \t");

	    	if("wap".equals(loginType) && null != session.getAttribute("lanxin")){
	    		
      out.write("\r\n\t    \t\twindow.close();\r\n\t    \t\t");

	    	} else if("wap".equals(loginType)){
	    		if(null != session.getAttribute("html5Page")){	// wap浏览器登录
	    			
      out.write("\r\n\t    \t\t\twindow.location.href = \"/jsoa/wap2/index.jsp\";\r\n\t    \t\t\t//window.history.back();\r\n\t    \t\t\t");

	    		} else{	// app登录
		       		if(MobileUtils.likeAndroid(userAgent)){	// 安卓
		       			
      out.write("\r\n\t\t        \t\t//window.js.back();\t\t     \r\n\t\t        \t\t//window.history.go(-1);\r\n\t\t        \t\twindow.location.href = \"/jsoa/wap2/index.jsp\";\r\n\t\t       \t\t\t");

		       		} else if(MobileUtils.likeIOS(userAgent)){	// IOS
		       			
      out.write("\r\n\t\t        \t\t//document.location = \"objc://IOSWindowBack:\";\r\n\t\t        \t\twindow.location.href = \"/jsoa/wap2/index.jsp\";\r\n\t\t       \t\t\t");

		       		} else{
		       			
      out.write("\r\n\t\t       \t\t\twindow.location.href = \"/jsoa/wap2/index.jsp\";\r\n\t\t       \t\t\t");

		       		}
	    		}
	        } else if("weixin".equals(loginType)){
	        	
      out.write("\r\n\t     \t\tvar readyFunc = function onBridgeReady() {\r\n\t\t\t\t\t// 关闭当前webview窗口 - closeWindow\r\n\t\t\t\t\tWeixinJSBridge.invoke('closeWindow', {}, function(res) {});\r\n\t\t\t\t}\r\n\t\t\t\tif(typeof WeixinJSBridge === \"undefined\"){\r\n\t\t\t\t\tif(document.addEventListener){\r\n\t\t\t\t\t\tdocument.addEventListener('WeixinJSBridgeReady', readyFunc, false);\r\n\t\t\t\t\t} else if(document.attachEvent){\r\n\t\t\t\t\t\tdocument.attachEvent('WeixinJSBridgeReady', readyFunc);\r\n\t\t\t\t\t\tdocument.attachEvent('onWeixinJSBridgeReady', readyFunc);\r\n\t\t\t\t\t}\r\n\t\t\t\t} else{\r\n\t\t\t\t\treadyFunc();\r\n\t\t\t\t}\r\n\t        \t");

	        } else if("lanxin".equals(loginType)){
	        	
      out.write("\r\n\t        \t");

	        } else if("dingding".equals(loginType)){
	        	
      out.write("\r\n\t        \tif(document.getElementById(\"mainDealwithDiv\")){\r\n\t        \t\tdocument.getElementById(\"mainDealwithDiv\").style.display='none';\r\n\t        \t} else if(window.parent.document.getElementById(\"mainDealwithDiv\")){\r\n\t        \t\twindow.parent.document.getElementById(\"mainDealwithDiv\").style.display='none';\r\n\t        \t} else if(window.parent.parent.document.getElementById(\"mainDealwithDiv\")){\r\n\t        \t\twindow.parent.parent.document.getElementById(\"mainDealwithDiv\").style.display='none';\r\n\t        \t} else if(window.parent.parent.parent.document.getElementById(\"mainDealwithDiv\")){\r\n\t        \t\twindow.parent.parent.parent.document.getElementById(\"mainDealwithDiv\").style.display='none';\r\n\t        \t}\r\n\t        \t");

	        }
	    	
      out.write("\r\n\t    }\r\n\t    </script>\r\n\t</body>\r\n</html>");
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
