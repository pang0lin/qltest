/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:38:50 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.util.config.UploadConfig;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import com.js.oa.dongcheng.db.DBUtil;
import java.sql.Connection;
import com.js.util.config.SystemCommon;
import com.js.oa.logon.service.LogonBD;
import com.bjca.sso.processor.*;
import com.bjca.sso.bean.*;
import java.util.*;
import java.net.*;
import com.bjca.security.*;

public final class dongchenglogin_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {


private String getUserAccountsByGUID(String guid){
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String result = "";
	if(null!=guid && !"".equals(guid)){
		try {
			conn = DBUtil.getConn();
			ps = conn.prepareStatement("select useraccounts from org_employee where guid=?");
			ps.setString(1, guid);
			rs = ps.executeQuery();
			if(rs.next()){
				result = rs.getString(1);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(rs, ps, conn);
		}
	}
	return result;
}

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("com.bjca.sso.processor");
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("java.net");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("com.bjca.sso.bean");
    _jspx_imports_packages.add("com.bjca.security");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.util.config.UploadConfig");
    _jspx_imports_classes.add("java.sql.Connection");
    _jspx_imports_classes.add("java.sql.ResultSet");
    _jspx_imports_classes.add("com.js.util.config.SystemCommon");
    _jspx_imports_classes.add("com.js.oa.logon.service.LogonBD");
    _jspx_imports_classes.add("com.js.oa.dongcheng.db.DBUtil");
    _jspx_imports_classes.add("java.sql.PreparedStatement");
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
      response.setContentType("text/html; charset=GB2312");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

	//服务器证书
	String BJCA_SERVER_CERT = request.getParameter("BJCA_SERVER_CERT");
	//票据
	String BJCA_TICKET = request.getParameter("BJCA_TICKET");
	//票据类型
	String BJCA_TICKET_TYPE = request.getParameter("BJCA_TICKET_TYPE");
	
	//System.out.println("BJCA_SERVER_CERT=====" + BJCA_SERVER_CERT);
	//System.out.println("BJCA_TICKET=====" + BJCA_TICKET);
	//System.out.println("BJCA_TICKET_TYPE=====" + BJCA_TICKET_TYPE);
	
	TicketManager ticketmag = new TicketManager();
	//验证签名和解密
	UserTicket userticket = ticketmag.getTicket(BJCA_TICKET, BJCA_TICKET_TYPE, BJCA_SERVER_CERT);
	//处理票据信息
	if(userticket != null){
		//取领导姓名
		String username = userticket.getUserName();
		//取领导id
		String userid = userticket.getUserUniqueID();
		//取委办局id
		String departid = userticket.getUserDepartCode();

		//System.out.println(userid);
		String userAccount = getUserAccountsByGUID(userid);
		if(null==userAccount || "".equals(userAccount)){
			request.setAttribute("errorType", "user");
			request.getRequestDispatcher("login.jsp").forward(request,response);	// 转向登录页面
		}
		
		String userIP = request.getRemoteAddr();
		String serverIP = InetAddress.getLocalHost().getHostAddress(); 
		String sessionId = request.getSession().getId();
		HashMap userInfo = new HashMap(10, 1);
		userInfo = new LogonBD().logon(userAccount, "", userIP, serverIP, sessionId, "jiusi", "0");

		//请委办局在此添加转向到子门户的链接地址。
		String ticketurl = "login.jsp";
		if(null == userInfo){
			request.setAttribute("errorType", "user");
		} else if(null != userInfo.get("error")){
			request.setAttribute("errorType", userInfo.get("error"));
		} else if(null != userInfo.get("userName")){
			// 登录动作
			String browserVersion = request.getHeader("User-Agent");
			if(browserVersion.indexOf("MSIE") >= 0){	// 判断是否是IE浏览器
				session.setAttribute("browserVersion", "MSIEx");
				if(browserVersion.indexOf("MSIE 6.0") >= 0){
					session.setAttribute("browserVersion", "MSIE6");
				} else if(browserVersion.indexOf("MSIE 10.0") >= 0){
					session.setAttribute("browserVersion", "IE10");
				}
			} else if(browserVersion.indexOf("Trident/7.0") >= 0){	// 判断是否是IE11
				session.setAttribute("browserVersion","IE11");
			} else if(browserVersion.indexOf("Firefox") >= 0){	// 判断是否是火狐浏览器
				session.setAttribute("browserVersion", "Firefox");
			} else if(browserVersion.indexOf("Chrome") >= 0){	// 谷歌浏览器
				session.setAttribute("browserVersion", "Chrome");
			} else if(browserVersion.indexOf("Safari") >= 0){	// 苹果浏览器
				session.setAttribute("browserVersion", "Safari");
			} else{
				session.setAttribute("browserVersion", "MSIEx");
			}
			if(browserVersion.indexOf("iPad") >= 0){	// 是否是iPad登录
				session.setAttribute("OSType","ipad");
			} else if(browserVersion.indexOf("Android") >= 0){	// 是否是Android移动设备登录
				session.setAttribute("OSType", "Android");
			} else if(browserVersion.indexOf("iPhone") >= 0){	// 是否是iPhone登录
				session.setAttribute("OSType", "iPhone");
			} else{
				session.setAttribute("OSType", "pc");
			}
			
			/*
			String IE11 = request.getParameter("checkBrow");
			if(null!=IE11 && !"".equals(IE11)){
				session.removeAttribute("browserVersion");
				session.setAttribute("browserVersion", IE11);
			}
			*/
			session.setAttribute("userName", userInfo.get("userName"));
			session.setAttribute("userId", userInfo.get("userId"));
			session.setAttribute("orgName", userInfo.get("orgName"));
			session.setAttribute("orgId", userInfo.get("orgId"));
			session.setAttribute("orgIdString", userInfo.get("orgIdString"));
			session.setAttribute("skin", "blue");	//userInfo.get("skin")==null?"green":userInfo.get("skin"));
			session.setAttribute("rootCorpId", userInfo.get("rootCorpId"));
			session.setAttribute("corpId", userInfo.get("corpId"));
			session.setAttribute("departId", userInfo.get("departId"));
			session.setAttribute("domainId", userInfo.get("domainId"));
			if(null != userInfo.get("sidelineDepartId")){
				session.setAttribute("sidelineCorpId", userInfo.get("sidelineCorpId"));
				session.setAttribute("sidelineDepartId", userInfo.get("sidelineDepartId"));
			} else{
				session.setAttribute("sidelineCorpId", "0");
				session.setAttribute("sidelineDepartId", "0");
			}
			
			// 将用户使用的ftp信息存到session中
			Map uploadMap = UploadConfig.getInstance().getUploadMap();
			String lanIP = uploadMap.get("LanIP").toString();

			if(userIP.startsWith(lanIP)){	// 用户使用内网地址
				session.setAttribute("fileServer", uploadMap.get("FileInnerServer"));
				session.setAttribute("ftpMap", uploadMap.get("FtpInnerMap"));
			} else{	// 用户使用外网地址
				session.setAttribute("fileServer", uploadMap.get("FileServer"));
				session.setAttribute("ftpMap", uploadMap.get("FtpMap"));
			}
			
			// 浏览范围
			if("1".equals(SystemCommon.getUseBrowseRange())){
				if(null==userInfo.get("browseRange") || "".equals(userInfo.get("browseRange").toString())){
					String browseRangeType = SystemCommon.getDefaultBrowseRange();
					if("1".equals(browseRangeType)){
						session.setAttribute("browseRange", "*" + userInfo.get("corpId").toString() + "*");
					} else if("0".equals(browseRangeType)){
						session.setAttribute("browseRange", "*0*");
					} else if("2".equals(browseRangeType)){
						session.setAttribute("browseRange", "*" + userInfo.get("departId").toString() + "*");
					}
				} else{
					String browseRangeType = userInfo.get("browseRange").toString();
					if("1".equals(browseRangeType)){
						session.setAttribute("browseRange", "*" + userInfo.get("corpId").toString() + "*");
					} else if("0".equals(browseRangeType)){
						session.setAttribute("browseRange", "*0*");
					} else if("2".equals(browseRangeType)){
						session.setAttribute("browseRange", "*" + userInfo.get("departId").toString() + "*");
					} else{
						session.setAttribute("browseRange",	userInfo.get("browseRange"));
					}
				}
			} else{
				session.setAttribute("browseRange", "*0*");
			}
			
			// 授权范围
			if("1".equals(SystemCommon.getUseGrantRange())){
				// 使用单独的授权范围
				if(null==userInfo.get("grantRange") || "".equals(userInfo.get("grantRange").toString())){
					String grantRangeType = SystemCommon.getDefaultGrantRange();
					if("1".equals(grantRangeType)){
						session.setAttribute("grantRange", "*" + userInfo.get("corpId").toString() + "*");
					} else if("0".equals(grantRangeType)){
						session.setAttribute("grantRange", "*0*");
					} else if("2".equals(grantRangeType)){
						session.setAttribute("grantRange", "*" + userInfo.get("departId").toString() + "*");
					}
				} else{
					String grantRangeType = userInfo.get("grantRange").toString();
					if("1".equals(grantRangeType)){
						session.setAttribute("grantRange", "*" + userInfo.get("corpId").toString() + "*");
					} else if("0".equals(grantRangeType)){
						session.setAttribute("grantRange", "*0*");
					} else if("2".equals(grantRangeType)){
						session.setAttribute("grantRange", "*" + userInfo.get("departId").toString() + "*");
					} else{
						session.setAttribute("grantRange",	userInfo.get("grantRange"));
					}
				} 
			} else{
				// 不使用单独的授权范围，与浏览范围一致
				session.setAttribute("grantRange", session.getAttribute("browseRange"));
			}
			
			session.setAttribute("userAccount", userAccount);
			session.setAttribute("sysManager", userInfo.get("sysManager"));
			if(null != userInfo.get("userSimpleName")) {
				session.setAttribute("userSimpleName", userInfo.get("userSimpleName"));
			} else{
				session.setAttribute("userSimpleName", "");
			}
			if(null != userInfo.get("orgSerial")){
				session.setAttribute("orgSerial", userInfo.get("orgSerial"));
			} else{
				session.setAttribute("orgSerial", "");
			}
			if(null != userInfo.get("orgSimpleName")){
				session.setAttribute("orgSimpleName", userInfo.get("orgSimpleName"));
			} else{
				session.setAttribute("orgSimpleName", "");
			}
			session.setAttribute("dutyName", null==userInfo.get("dutyName") ? "" : userInfo.get("dutyName"));
			session.setAttribute("dutyLevel", null==userInfo.get("dutyLevel") ? "0" : userInfo.get("dutyLevel"));
			session.setAttribute("imID", null==userInfo.get("imID") ? "0" : userInfo.get("imID").toString());
			session.setAttribute("hasLoged", null);
			session.setAttribute("serverIP", serverIP);
			session.setAttribute("userIP", userIP);
			session.setAttribute("empEnglishName", userInfo.get("empEnglishName"));
			
			/**取角色信息
			Hashtable roles = userticket.getUserRoles();
			String s_role = "";
			if(roles != null && roles.size() > 0) {
				int index = 1;
				Enumeration e = roles.keys();
				Enumeration e2 = roles.elements();
				for(;e.hasMoreElements();){
					String rolecode = (String)e.nextElement();
					String rolename = (String)e2.nextElement();
					if(rolename.indexOf("?") != -1) {
						rolename = new String(rolename.getBytes("GBK"),"ISO-8859-1");
					}
					if(index == 1){
						s_role = rolecode;
					}else{
						s_role = s_role + "," + rolecode;
					}
					index++;
				}
			}
			*/
			//角色、领导id、领导姓名、委办局id、功能URL写入SESSION中
			//session.setAttribute("roles",s_role);
			session.setAttribute("personID", userid);
			session.setAttribute("orgID", departid);
			
			// 用于邮件系统单点登录的参数
			session.setAttribute("BJCA_SERVER_CERT", BJCA_SERVER_CERT);
			session.setAttribute("BJCA_TICKET", BJCA_TICKET);
			session.setAttribute("BJCA_TICKET_TYPE", BJCA_TICKET_TYPE);
			ticketurl = "desktop.jsp";
		}
		
		response.sendRedirect(ticketurl);
		//request.getRequestDispatcher(ticketurl).forward(request,response);//转向业务系统
	} else{
		response.sendRedirect("dongchengErrors.jsp");//这里是临时的错误页面，可以修改错误页面
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
