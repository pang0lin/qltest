/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:48:21 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.routine.boardroom;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.oa.routine.boardroom.action.BoardRoomAction;
import com.js.util.config.SystemCommon;
import java.util.*;
import java.text.*;

public final class boardRoomViewUsers_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("java.text");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.util.config.SystemCommon");
    _jspx_imports_classes.add("com.js.oa.routine.boardroom.action.BoardRoomAction");
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");

String urlPath = request.getParameter("boardroomApplyId")==null?"":"&boardroomApplyId="+request.getParameter("boardroomApplyId");
urlPath += request.getParameter("boardroomName")==null?"":"&boardroomName="+request.getParameter("boardroomName");
urlPath += request.getParameter("type")==null?"":"&type="+request.getParameter("type");
urlPath += request.getParameter("isView")==null?"":"&isView="+request.getParameter("isView");
urlPath += request.getParameter("meetingId")==null?"":"&meetingId="+request.getParameter("meetingId");
urlPath += request.getParameter("executeStatus")==null?"":"&executeStatus="+request.getParameter("executeStatus");
urlPath += request.getParameter("statusa")==null?"":"&statusa="+request.getParameter("statusa");

      out.write("\r\n<html>\r\n<head>\r\n<title>??????????????????</title>\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n</head>\r\n<body class=\"MainFrameBox\" leftmargin=\"0\" topmargin=\"0\" rightmargin=\"0\" onload=\"window.focus();\" onKeyDown=\"if(event.keyCode==13){ searchSubmit();return false;}\" >\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td>\r\n<table width=\"100%\" height=\"25\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"inlineBottomLine\">\r\n  <tr>\r\n    <td>\r\n\t    <div id=\"PanleDIV0\" class=\"tabPanel\" onClick=\"changePanle('0');\">\r\n        <div class=\"tabPanelLeft\"></div>\r\n        <div class=\"tabPanelCenter\">??????????????????</div>\r\n        <div class=\"tabPanelRight\"></div>      \r\n      </div>\r\n\t  ");
if("true".equals(request.getParameter("executeStatus")+"")){ 
      out.write("\r\n      <div class=\"btnBQspace AlignLeft\"></div>    \r\n\t    <div id=\"PanleDIV3\" class=\"tabPanel\" onClick=\"changePanle('5');\">\r\n        <div class=\"tabPanelLeft\"></div>\r\n        <div class=\"tabPanelCenter\">????????????</div>\r\n        <div class=\"tabPanelRight\"></div>      \r\n      </div>\r\n      ");
if(SystemCommon.getMeetingTaskUse()==1){ 
      out.write("\r\n      <div class=\"btnBQspace AlignLeft\"></div>    \r\n\t    <div id=\"PanleDIV3\" class=\"tabPanel\" onClick=\"changePanle('6');\">\r\n        <div class=\"tabPanelLeft\"></div>\r\n        <div class=\"tabPanelCenter\">");
      out.print(SystemCommon.getMeetingTaskShowName() );
      out.write("</div>\r\n        <div class=\"tabPanelRight\"></div>      \r\n      </div>");
} 
      out.write("\r\n      <div class=\"btnBQspace AlignLeft\"></div>    \r\n\t    <div id=\"PanleDIV3\" class=\"tabPanel\" onClick=\"changePanle('4');\">\r\n        <div class=\"tabPanelLeft\"></div>\r\n        <div class=\"tabPanelCenter\">??????????????????</div>\r\n        <div class=\"tabPanelRight\"></div>      \r\n      </div>\r\n      ");
if(SystemCommon.getMeetingChatUse()==1){ 
      out.write("\r\n      <div class=\"btnBQspace AlignLeft\"></div>    \r\n\t    <div id=\"PanleDIV3\" class=\"tabPanel\" onClick=\"changePanle('7');\">\r\n        <div class=\"tabPanelLeft\"></div>\r\n        <div class=\"tabPanelCenter\">");
      out.print(SystemCommon.getMeetingChatShowName() );
      out.write("</div>\r\n        <div class=\"tabPanelRight\"></div>      \r\n      </div>");
} 
      out.write("\r\n\t  ");
} 
      out.write("\r\n\t  <div class=\"btnBQspace AlignLeft\"></div>\r\n\t    <div id=\"PanleDIV1\" class=\"tabPanel\" onClick=\"changePanle('1');\">\r\n        <div class=\"tabPanelLeft\"></div>\r\n        <div class=\"tabPanelCenter\">????????????</div>\r\n        <div class=\"tabPanelRight\"></div>      \r\n      </div>\r\n      \r\n      <div class=\"btnBQspace AlignLeft\"></div> \r\n\t    <div id=\"PanleDIV2\" class=\"tabPanelSelected\" onClick=\"changePanle('2');\">\r\n        <div class=\"tabPanelLeft\"></div>\r\n        <div class=\"tabPanelCenter\">???????????????</div>\r\n        <div class=\"tabPanelRight\"></div>      \r\n      </div>\r\n      \r\n      <div class=\"btnBQspace AlignLeft\"></div>    \r\n\t    <div id=\"PanleDIV3\" class=\"tabPanel\" onClick=\"changePanle('3');\">\r\n        <div class=\"tabPanelLeft\"></div>\r\n        <div class=\"tabPanelCenter\">???????????????</div>\r\n        <div class=\"tabPanelRight\"></div>      \r\n      </div>\r\n      \r\n     </td>\r\n  </tr>\r\n</table>\r\n\r\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" style=\"table-layout:fixed;\">\r\n\t<tr>\r\n        <td style=\"text-align:left;width:100%;\">\r\n");
      out.write("        \t<!-- ???????????? -->\r\n            <form name=\"form1\" method=\"POST\">\r\n\t            <span>??????:</span>\r\n\t            <input type=\"text\" name=\"empName\" class=\"inputText\" value=\"");
      out.print(request.getParameter("empName")==null?"":request.getParameter("empName"));
      out.write("\">\r\n\t\t\t\t<input type=\"button\" class=\"btnButton2font\" onclick=\"searchSubmit();\" style=\"margin-top:-5px\" value=\"??????\" />\r\n\t\t\t    <input type=\"button\" class=\"btnButton2font\" style=\"margin-top:-5px\" onclick=\"resetValue();\" value=\"??????\" />\r\n            </form>\r\n        </td>\r\n    </tr>\r\n    ");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	BoardRoomAction br=new BoardRoomAction();
    	Object obj = null;
        java.util.List list = (java.util.List) request.getAttribute("list2");
		String a = "";
		String b = "";
		
        for(int i = 0; list!=null&&i < list.size(); i++)
        {
        	obj = (Object) list.get(i);
			b = obj!=null?obj.toString():"";
			String orgName=br.getorgName(Integer.parseInt(b));
	
      out.write("\r\n\t<!-- ?????? -->\r\n\t<tr height=\"23\" bgcolor=\"#CCCCCC\">\r\n\t    <td style=\"text-align:left;\">\r\n\t    \t<b>");
      out.print(orgName);
      out.write("</b>\r\n\t    </td>\r\n    </tr>\r\n\t    ");

			Object[] obj2 = null;
			int k=0;
			java.util.List list2 = (java.util.List) request.getAttribute("list");
		
      out.write("\r\n\t\t\t<tr>\r\n\t\t\t\t<td class=\"listTableLine1\">\r\n\t\t");

			for(int j = 0; j < list2.size(); j++){
				obj2=(Object[])list2.get(j);
				if(obj2[2]!=null && obj2[2].toString().equals(b))
				{
      out.write("\r\n\t\t\t\t <span style=\"width:50px;\"><span style=\"color:green;\" title=\"");
      out.print(null!=obj2[4]?sdf.format((Date)obj2[4]):"");
      out.write('"');
      out.write('>');
      out.print(null!=obj2[1]?obj2[1].toString():"");
      out.write("</span></span>\r\n\t\t");

	   				k=k+1;
	   				if(k%10==0)
	   				{
      out.write("\r\n\t\t   \t\t\t\t</td>\r\n\t\t   \t\t\t\t</tr>\r\n\t\t   \t\t\t\t<tr>\r\n\t\t   \t\t\t\t<td class=\"listTableLine1\">\r\n\t   \t");

	   				}
	   			}
			}
      out.write("\r\n\t\t\t</td>\r\n\t\t\t</tr>\r\n\t");
}
      out.write("\r\n</table>\r\n</td>\r\n</tr></table>\r\n</body>\r\n</html>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n<!--\r\nfunction changePanle(id){\r\n\tif(id==\"0\"){\r\n\t\tlocation.href=\"/jsoa/BoardRoomAction.do?action=selectBoardroomApplyView");
      out.print(urlPath );
      out.write("\";\r\n\t}else if(id==\"1\"){\r\n\t\tlocation.href=\"/jsoa/BoardRoomAction.do?action=applyReportList&isView=");
      out.print(request.getParameter("isView"));
      out.print(urlPath );
      out.write("\";\r\n\t}else if(id==\"2\"){\r\n\t\tlocation.href=\"/jsoa/BoardRoomAction.do?action=viewUsers");
      out.print(urlPath );
      out.write("\";\r\n\t}else if(id==\"3\"){\r\n\t\tlocation.href=\"/jsoa/BoardRoomAction.do?action=unviewUsers");
      out.print(urlPath );
      out.write("\";\r\n\t}else if(id==\"4\"){\r\n\t\tlocation.href=\"/jsoa/BoardRoomAction.do?action=executeStatus&meetingId=");
      out.print(request.getParameter("meetingId"));
      out.print(urlPath );
      out.write("\";\r\n\t}else if(id==\"5\"){\r\n\t\tlocation.href=\"BoardRoomAction.do?action=openSummary&type=look");
      out.print(urlPath.replace("type=", "viewType=") );
      out.write("\";\r\n\t}else if(id==\"6\"){\r\n\t\tlocation.href=\"/jsoa/BoardRoomAction.do?action=task");
      out.print(urlPath );
      out.write("\";\r\n\t}else if(id==\"7\"){\r\n\t\tlocation.href=\"/jsoa/BoardRoomAction.do?action=discussion");
      out.print(urlPath );
      out.write("\";\r\n\t}\r\n}\r\n\r\nfunction searchSubmit(){\r\n\tvar val=document.all.empName.value;\r\n\tif(val.indexOf(\"'\")>=0){\r\n\t\talert(\"?????????????????? ' ???????????????\");\r\n\t\tdocument.all.empName.focus();\r\n\t}else{\r\n\t\tform1.action = \"/jsoa/BoardRoomAction.do?action=viewUsers");
      out.print(urlPath );
      out.write("\";\r\n\t\tform1.submit();\r\n\t}\r\n}\r\nfunction resetValue(){\r\n\twindow.location.href=\"/jsoa/BoardRoomAction.do?action=viewUsers");
      out.print(urlPath );
      out.write("\";\r\n}\r\n//-->\r\n</SCRIPT>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
