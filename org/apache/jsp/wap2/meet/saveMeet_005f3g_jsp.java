/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:46:24 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.wap2.meet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.oa.routine.boardroom.po.BoardRoomApplyPO;
import com.js.oa.routine.boardroom.po.BoardroomPersons;
import java.util.Date;
import com.js.oa.routine.boardroom.service.BoardRoomBD;
import com.js.wap.util.WapUtil;

public final class saveMeet_005f3g_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.util.Date");
    _jspx_imports_classes.add("com.js.oa.routine.boardroom.service.BoardRoomBD");
    _jspx_imports_classes.add("com.js.wap.util.WapUtil");
    _jspx_imports_classes.add("com.js.oa.routine.boardroom.po.BoardroomPersons");
    _jspx_imports_classes.add("com.js.oa.routine.boardroom.po.BoardRoomApplyPO");
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
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"DTD/xhtml1-strict.dtd\">\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

	request.setCharacterEncoding("UTF-8");
	String path = request.getContextPath();
	String id = request.getParameter("boardroomApplyId");
	String beginIndex=request.getParameter("beginIndex").toString();
	String msg = "";
	String content = request.getParameter("content");
	Long boardroomApplyId = null;
	boolean tag = false;
	
	if(null != id && !"".equals(id) && !"null".equals(id)){
		tag = true;
		boardroomApplyId = Long.valueOf(id);
	}

	if (tag && null != content && !"".equals(content) && !"null".equals(content) && content.length() <= 100) {
		String status = request.getParameter("status");
		Long userID = new Long((String) session.getAttribute(WapUtil.EMP_ID)); //??????????????????ID
		String userName = (String) session.getAttribute(WapUtil.EMP_NAME); //????????????????????????
		Long orgId = new Long((String) session.getAttribute(WapUtil.EMP_ORG_ID)); //??????????????????orgID

		String orgName = request.getParameter("orgName");
		BoardRoomBD boardRoomBD = new BoardRoomBD();
		BoardRoomApplyPO brap = new BoardRoomApplyPO();
		brap.setBoardroomApplyId(boardroomApplyId);
		BoardroomPersons bp = new BoardroomPersons();
		bp.setApply(brap);
		bp.setEmpId(userID);
		bp.setEmpName(userName);
		bp.setOrgId(orgId);
		bp.setOrgName(orgName);
		bp.setStatus(status);
		bp.setContent(content);
		bp.setReplyDate(new Date());
		if(request.getParameter("id")==null){
			boardRoomBD.addBoardroomPersons(bp);
		}else{
			bp.setId(Long.valueOf(request.getParameter("id").toString()));
			boardRoomBD.modityBoardroomPersons(bp);
		}
		response.sendRedirect(path + "/wap/action/WapMeetingAction.do?method=getMeetList&beginIndex="+beginIndex);
	}else{
		msg = "????????????????????????????????????????????????????????????200?????????";
	}
		

      out.write("\r\n<HTML xmlns=\"http://www.w3.org/1999/xhtml\">\r\n<HEAD>\r\n<TITLE>????????????</TITLE>\r\n<META content=\"text/html; charset=UTF-8\" http-equiv=Content-Type>\r\n<META name=viewport content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\">\r\n<META name=apple-touch-fullscreen content=YES>\r\n<META name=apple-mobile-web-app-capable content=no>\r\n<LINK rel=apple-touch-icon href=\"");
      out.print(path);
      out.write("/wap2/images/iphone.jpg\">\r\n<LINK rel=stylesheet type=text/css href=\"");
      out.print(path);
      out.write("/wap2/css/main_3g.css\">\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path);
      out.write("/wap2/js/cookie.js\"></SCRIPT>\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path);
      out.write("/wap2/js/util.js\"></SCRIPT>\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path);
      out.write("/wap2/js/frost.js\"></SCRIPT>\r\n<META name=GENERATOR content=\"MSHTML 8.00.6001.19154\">\r\n</HEAD>\r\n\r\n\r\n\t<body>\r\n\r\n\t<div class=\"wrap\" id=\"top\">\r\n\t<div id=\"top\">\r\n\t\t\t<span id=\"lp2\"><div class=\"btn_3\"><a href=\"");
      out.print(path);
      out.write("/wap/action/WapMeetingAction.do?method=getMeetList&beginIndex=");
      out.print(beginIndex );
      out.write("\">??????</a></div></span>\r\n\t\t\t<span id=\"title2\"></span>\r\n\t\t</div>\r\n  \t\t<h1>??????????????????</h1>\r\n  \t \t<form action=\"\" method=\"post\" id=\"repMeetForm\">\r\n\t\t\t<div>\r\n\t\t\t\t<div style=\"color:red;\">");
      out.print(msg );
      out.write("</div>\r\n\t\t\t\t  \t\t\t\r\n\t\t\t\t<a href=\"");
      out.print(path);
      out.write("/wap2/meet/readMeetInfo_3g.jsp?boardroomApplyId=");
      out.print(boardroomApplyId);
      out.write("\">\r\n\t\t\t\t<img src=\"");
      out.print(path);
      out.write("/wap2/image/continue.gif\"/></a>\r\n\t\t\t\t<a href=\"");
      out.print(path);
      out.write("/wap/action/WapMeetingAction.do?method=getMeetList\">\r\n\t\t\t\t<img src=\"");
      out.print(path);
      out.write("/wap2/image/meeting.gif\"/></a>\r\n\t\t\t\t\r\n\t\t\t</div>\r\n\t\t</form>\r\n\t   \r\n  \r\n  </div>\r\n\r\n\t</body>\r\n</html>\r\n");
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
