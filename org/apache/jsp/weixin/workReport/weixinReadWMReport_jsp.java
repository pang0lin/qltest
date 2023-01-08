/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:00:23 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.weixin.workReport;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.util.CharacterTool;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import com.js.oa.scheme.workreport.po.WorkReportPO;
import com.js.wap.service.WapBD;
import com.js.wap.util.WapUtil;
import com.js.wap.util.DateTools;
import com.js.wap.util.WapStringTool;

public final class weixinReadWMReport_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {


private static String getBEStr(String str){
	if("".equals(str)){
		return "";
	}
	
	if(str.length() == 6){
		return str.substring(0, 4) + "年" +  str.substring(4) + "月份";
	} else if(8 == str.length()){
		return str.substring(0, 4) + "年" + str.substring(4, 6) + "月" + str.substring(6) + "日";
	}
		
	return str.substring(0, 4) + "-" +  str.substring(4,6) + "-" + str.substring(6,8) 
			+ "至" + str.substring(9,13) + "-" + str.substring(13,15) + "-" + str.substring(15, 17);
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
    _jspx_imports_classes.add("com.js.util.util.CharacterTool");
    _jspx_imports_classes.add("com.js.oa.scheme.workreport.po.WorkReportPO");
    _jspx_imports_classes.add("com.js.wap.util.WapStringTool");
    _jspx_imports_classes.add("java.sql.ResultSet");
    _jspx_imports_classes.add("com.js.wap.util.WapUtil");
    _jspx_imports_classes.add("com.js.util.util.DataSourceBase");
    _jspx_imports_classes.add("com.js.wap.util.DateTools");
    _jspx_imports_classes.add("com.js.wap.service.WapBD");
    _jspx_imports_classes.add("com.js.oa.userdb.util.DbOpt");
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

      out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
      out.write('\r');
      out.write('\n');
 
request.setCharacterEncoding("UTF-8");
String path = request.getContextPath();

String userId = (String)session.getAttribute(WapUtil.EMP_ID);
Long domainId = session.getAttribute("domainId")==null ? Long.valueOf("0") : Long.valueOf(session.getAttribute("domainId").toString());
String repId = request.getParameter("repId");
if(!"".equals(repId) && !"null".equals(repId)){	
String leaderId = request.getParameter("leaderId");
String repUser = null!=request.getParameter("repUser") ? request.getParameter("repUser") : "mine";
// 是否来自应用消息
String from = null!=request.getParameter("from") ? request.getParameter("from") : "";
WorkReportPO wrpo = new WapBD().getReportById(userId,domainId,repId);

String empName = "";
String empId = "";
String orgName = "";
String reportReader = "";
String reportReaderId = "";
String course = "";
String content = "";
String reportType = "";
boolean tag = false;
if(null != wrpo){
	empName = wrpo.getReportEmpName();
	empId = new Long(wrpo.getEmpId()).toString();
	orgName = wrpo.getReportDepart();
	reportReader = wrpo.getReportReader();
	reportReaderId = wrpo.getReportReaderId();
		
	course = wrpo.getReportCourse();
	content = wrpo.getPreviousReport(); 
	//content = WapStringTool.Html2Text(content);
				
	reportType = new Byte(wrpo.getReportType()).toString();
	
	if(null!=reportReaderId && !"".equals(reportReaderId) && reportReaderId.contains("$" + userId + "$") 
			&& !"mine".equals(repUser))
		tag = true;
	
	// 更新状态为已读
	DbOpt db = new DbOpt();
	db.executeUpdate("update rep_leader set hadread=1 where report_id=" + repId + " and emp_id=" + userId + " and domain_id=" + domainId);
	db.close();
}
String userIds="";
if(null!=reportReaderId && !"".equals(reportReaderId)){
  userIds=reportReaderId.substring(1, reportReaderId.length()-1).replace("$$", ",");
}

String beginIndex = null!=request.getParameter("beginIndex") ? request.getParameter("beginIndex") : "0";
String backto = null!=request.getParameter("backto") ? request.getParameter("backto") : "";
String worklogEmp = "mine".equals(repUser) ? "&nbsp;" : "汇报人：" + empName;
String repType = request.getParameter("repType").toString();
String url = path + "/wap/action/WapReportAction.do?method=getWapWMRepList&amp;beginIndex=" + beginIndex + "&amp;repUser=" + repUser + "&amp;repType=" + repType;

      out.write("\r\n<!DOCTYPE html>\r\n<html>\r\n\t<HEAD>\r\n\t\t<TITLE>汇报信息</TITLE>\r\n\t\t<META content=\"text/html; charset=UTF-8\" http-equiv=Content-Type>\r\n\t\t<META name=viewport content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\">\r\n\t\t<META name=apple-touch-fullscreen content=YES>\r\n\t\t<META name=apple-mobile-web-app-capable content=no>\r\n\t\t<META name=GENERATOR content=\"MSHTML 8.00.6001.19154\">\r\n\t\r\n\t\t<link rel=\"stylesheet\" href=\"/jsoa/css/weixin/weixin_main.css\">\r\n\t\t<link rel=\"stylesheet\" href=\"/jsoa/css/weixin/weixin_zsgl.css\">\r\n\t</head>\r\n\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/wap2/commonImport.jsp", out, false);
      out.write("\r\n\t<body onload=\"setHeader('javascript:closeWindow()', '汇报信息');\" class=\"wapcss\">\r\n\t\t<div class=\"form\">\r\n\t\t\t<div style=\"margin-top: 15px;\">\r\n\t\t\t\t<span style=\"float: left; margin-left: 5px;\">");
      out.print(worklogEmp );
      out.write("</span>\r\n\t\t\t\t<span style=\"float: right; margin-right: 5px;\">\r\n\t\t\t\t\t<a href=\"javascript:void(0);\" onclick=\"showInfo()\" style=\"text-decoration: none;\">详情</a>\r\n\t\t\t\t</span>\r\n\t\t\t</div>\r\n    \t\t<div class=\"zsgllineitem\"></div>\r\n    \t\t<div id=\"information\" style=\"display: none; background: #efefef;\"\">\r\n\t\t\t\t<div class=\"item\" style=\"padding: 0;\">\r\n\t\t\t\t\t<div class=\"title\">所属部门:</div>\r\n\t\t\t\t\t<div class=\"content\" align=\"left\">\r\n\t\t\t\t\t\t<div class=\"info\">");
      out.print(orgName);
      out.write("</div>\r\n\t\t\t\t\t</div>\r\n\t\t\t\t</div>\r\n\t\t\t\t<div class=\"zsgllineitem\"></div>\r\n\t\t\t\t<div class=\"item\" style=\"padding: 0;\">\t\r\n\t\t\t\t\t<div class=\"title\">汇报期间:</div>\r\n\t\t\t\t\t<div class=\"content\" align=\"left\">\r\n\t\t\t\t\t\t<div class=\"info\">");
      out.print(this.getBEStr(course) );
      out.write("</div>\r\n\t\t\t\t\t</div>\r\n\t\t\t\t</div>\r\n\t\t\t\t<div class=\"zsgllineitem\"></div>\r\n    \t\t</div>\r\n\t\t\t<div class=\"item\">\r\n\t\t\t\t<div class='title'>提&nbsp;&nbsp;交&nbsp;&nbsp;至:</div>\r\n\t\t \t\t<div class=\"userlist\">\r\n                 \t<ul class=\"clearfix\" id=\"toPersonList\">\r\n                  \t");

                  	if(reportReader!=null && !"".equals(reportReader)){
                     	String toName=reportReader;
                     	String[] toNameStr=null;
                     	String[] userIdstr=null; 
                        if(!"".equals(toName)){
                           	toNameStr=toName.split(",");
                           	userIdstr=userIds.split(",");
                        }
                     	if(toNameStr!=null){
                     		for(int i=0;i<toNameStr.length;i++){
                  				
      out.write("\r\n\t\t\t\t\t\t\t\t<li id=");
      out.print(userIdstr[i]);
      out.write("_readSelect>\r\n\t\t\t\t\t \t\t\t\t<img style='cursor:pointer;width:30px;height:30px' border='0' src='/jsoa/weixin/common/getUserAvatar.jsp?userId=");
      out.print(com.js.system.util.StaticParam.getUserAccountsByEmpId(userIdstr[i]));
      out.write("'/>\r\n\t\t\t\t\t \t\t\t\t<p class='name'>");
      out.print(toNameStr[i]);
      out.write("</p>\r\n\t\t\t\t\t\t\t\t</li>\r\n\t\t\t\t\t\t\t\t");

							}
						}
					}
     				
      out.write("\r\n               \t\t</ul>\r\n           \t\t</div>\r\n        \t</div>\r\n\t\t\t\t\r\n\t\t\t<div class=\"item\">\r\n        \t    <div class=\"title\">汇报内容:</div> \r\n\t\t\t\t<div class=\"content\" align=\"left\" id=\"content\" style=\"clear: left;\">");
      out.write("\r\n\t\t\t\t   ");
      out.print(content );
      out.write("\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t\t\t\t\r\n\t\t\t");

			StringBuffer html = new StringBuffer("");
			String grade = "B+";
			DataSourceBase dataSourceBase=new DataSourceBase();
			ResultSet rs=null;
			try{
				String sql = "select postilcontent,postilempname,grade from rep_postil where report_id="+repId;
				dataSourceBase.begin();
				rs = dataSourceBase.executeQuery(sql);
				while(rs.next()){
					grade = rs.getString(3);
					html.append(CharacterTool.escapeHTMLTags(rs.getString(1)));
					if(null!=grade && !"null".equals(grade) && !"".equals(grade)) html.append("<br/>评价等级：" + grade);
					html.append("<br/><div align='right'>" + rs.getString(2) + "</div><br/>");
				}
			} catch(Exception e){
				e.printStackTrace();
			} finally{
				try{
					rs.close();
					dataSourceBase.end();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
      out.write("\r\n\t\t\t<div class=\"item\">\r\n\t\t\t\t<div class=\"title\">评价留言:</div>\r\n\t\t\t\t<div class=\"content\" align=\"left\">\r\n\t\t\t\t    ");
if(html!=null && !"".equals(html+"")){
      out.write("\r\n\t\t\t\t\t <div class=\"info\">");
      out.print(html.toString() );
      out.write("</div>\r\n\t\t\t\t\t");
}else{ 
      out.write("\r\n\t\t\t\t\t <div class=\"info\">&nbsp;</div>\r\n\t\t\t\t\t");
} 
      out.write("\r\n\t\t\t\t</div> \r\n\t\t\t</div>\r\n\t\t\t\t\t\r\n\t\t\t");

			if(tag){
				if("3".equals(reportType)){
					
      out.write("\r\n\t\t\t\t\t<div class=\"item\">\r\n\t\t\t\t\t\t<div class=\"title\">考核等级:</div>\r\n\t\t\t\t\t\t<div class=\"content\" align=\"left\">\r\n\t\t\t\t\t\t\t<select class=\"select\" id=\"grades\">\r\n\t\t\t\t\t\t\t\t<option value=\"A+\" ");
if("A+".equals(grade)) out.print("selected='selected'"); 
      out.write(">A+</option>\r\n\t\t\t\t\t\t\t\t<option value=\"A\" ");
if("A".equals(grade)) out.print("selected='selected'"); 
      out.write(">A</option>\r\n\t\t\t\t\t\t\t\t<option value=\"A-\" ");
if("A-".equals(grade)) out.print("selected='selected'"); 
      out.write(">A-</option>\r\n\t\t\t\t\t\t\t\t<option value=\"B+\" ");
if("B+".equals(grade)) out.print("selected='selected'"); 
      out.write(">B+</option>\r\n\t\t\t\t\t\t\t\t<option value=\"B\" ");
if("B".equals(grade)) out.print("selected='selected'"); 
      out.write(">B</option>\r\n\t\t\t\t\t\t\t\t<option value=\"B-\" ");
if("B-".equals(grade)) out.print("selected='selected'"); 
      out.write(">B-</option>\r\n\t\t\t\t\t\t\t\t<option value=\"C+\" ");
if("C+".equals(grade)) out.print("selected='selected'"); 
      out.write(">C+</option>\r\n\t\t\t\t\t\t\t\t<option value=\"C\" ");
if("C".equals(grade)) out.print("selected='selected'"); 
      out.write(">C</option>\r\n\t\t\t\t\t\t\t\t<option value=\"C-\" ");
if("C-".equals(grade)) out.print("selected='selected'"); 
      out.write(">C-</option>\r\n\t\t\t\t\t\t\t</select>\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t");

				}
			} else{
				
      out.write("\r\n\t\t\t\t<div class='item'>\r\n\t\t\t\t\t<div class='content'><b>注意：无评价权限！</b></div>\r\n\t\t\t\t</div>\r\n\t\t\t\t");

			}
			
      out.write("\r\n\t\t</div>\r\n\t\t\t\r\n\t\t<div class=\"footer\">\r\n\t      \t<div class=\"buttons\">\r\n\t\t\t");

			if(tag && !"7".equals(reportType)){	// 其他类型工作汇报，暂不支持评价回复
				
      out.write("\r\n\t\t\t\t<!-- 按钮 -->\r\n\t\t      \t<form style=\"width: 100%;height: 100%;\" name=\"reportRepForm\" action=\"");
      out.print(path);
      out.write("/weixin/workReport/weixinReplyWMRep.jsp\" method=\"post\">\r\n\t\t\t\t\t<div class=\"input\">\r\n\t\t\t\t\t\t<input type=\"hidden\" name=\"repId\" value=\"");
      out.print(repId);
      out.write("\"/>\r\n\t\t\t\t  \t\t<input type=\"hidden\" name=\"leaderId\" value=\"");
      out.print(leaderId );
      out.write("\"/>\r\n\t\t\t\t  \t\t<input type=\"hidden\" name=\"usersId\" value=\"");
      out.print(reportReaderId.replace("$","_") );
      out.write("\"/>\r\n\t\t\t\t  \t\t<input type=\"hidden\" name=\"usersName\" value=\"");
      out.print(reportReader );
      out.write("\"/>\r\n\t\t\t\t  \t\t<input type=\"hidden\" name=\"reportType\" value=\"");
      out.print(reportType );
      out.write("\"/>\r\n\t\t\t\t  \t\t<input type=\"hidden\" name=\"grade\" id=\"grade\"/>\r\n\t\t\t\t  \t\t<input type=\"hidden\" name=\"repUser\" value=\"");
      out.print(request.getParameter("repUser") );
      out.write("\"/>\r\n\t\t\t\t  \t\t<input type=\"hidden\" name=\"repType\" value=\"");
      out.print(request.getParameter("repType") );
      out.write("\"/>\r\n\t\t\t\t  \t\t<input type=\"hidden\" name=\"backto\" value=\"");
      out.print(backto );
      out.write("\"/>\r\n\t\t\t\t  \t\t<input type=\"hidden\" name=\"from\" value=\"");
      out.print(from );
      out.write("\"/>\r\n\t\t\t\t\t\t<input type=\"text\" id=\"leaveword\" name=\"leaveword\"/>\r\n\t\t\t\t\t</div>\r\n\t\t\t\t   \t<div class=\"replyDiv\">\r\n\t \t\t\t\t\t<div class=\"reply\" onclick=\"javascript:reply();\">回复</div>\r\n\t \t\t      \t</div>\r\n\t\t\t\t</form>\t\r\n\t\t\t\t");

			}else{
				if(null!=backto && "message".equals(backto)){
					
      out.write("\r\n\t\t\t\t    <div id=\"closeWindow\" class=\"button gray\">返回</div>\r\n\t\t\t\t\t");

				} else{
					
      out.write("\r\n\t\t\t\t    <div class=\"button gray\" onclick=\"javascript:window.history.back();\">返回</div>\r\n\t\t\t\t\t");

				}
			} 
      out.write("\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/weixin/common/weixin_messageReminders.jsp", out, false);
      out.write("\r\n\t\t<script type=\"text/javascript\">\t\t\r\n\t\tfunction reply(){\r\n\t\t\t");

			if(tag && "3".equals(reportType)){
				
      out.write("\r\n\t\t\t\tdocument.getElementById(\"grade\").value = document.getElementById(\"grades\").value;\r\n\t\t\t\t");

			}
			
      out.write("\r\n\t\t\tvar leaveword = $.trim($(\"#leaveword\").val());\r\n\t\t\tif(leaveword==null || leaveword==\"\"){\r\n\t\t\t\tweixinMessageReminder(\"alert\", \"提示：\", \"评价留言不能为空！\", \"\");\r\n\t\t\t\treturn;\r\n\t\t\t}else if(leaveword.length>500){\r\n\t\t\t\tvar msg = document.getElementById(\"msg\");\r\n\t\t\t\tweixinMessageReminder(\"alert\", \"提示：\", \"评价留言长度不能大于500个字符！\", \"\");\r\n\t\t\t\treturn;\r\n\t\t\t}\r\n\t\t\tdocument.reportRepForm.submit();\t\r\n\t\t}\r\n\t\t\r\n\t\t\r\n\t\tfunction showInfo(obj){\r\n\t\t\tvar infoDiv = document.getElementById(\"information\");\r\n\t\t\tif(infoDiv.style.display == \"none\"){\r\n\t\t\t\tinfoDiv.style.display = \"block\";\r\n\t\t\t\tobj.innerHTML = \"隐藏\";\r\n\t\t\t} else{\r\n\t\t\t\tinfoDiv.style.display = \"none\";\r\n\t\t\t\tobj.innerHTML = \"详情\";\r\n\t\t\t}\r\n\t\t}\r\n\t\t</script>\r\n\t</body>\r\n</html>\r\n");
}else{
      out.write("\r\n\t<script type=\"text/javascript\">\r\n\talert(\"该文件已被删除或退回！\");\r\n\t</script>\r\n");
}
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
