/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:59:24 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.weixin.workflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import com.js.util.util.Word2PDF;
import com.js.util.util.Html2Word;
import java.util.*;
import com.js.oa.eform.service.CustomFormBD;

public final class workflow_005fmyflow_005fadd_005fgd_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {


private String getRemindValue(String remindField, String recordId,
        String formId) {
	String remindValue = "";
	if (remindField == null || remindField.length() < 1 ||
		remindField.toUpperCase().equals("null") ||
		recordId == null || recordId.length() < 1 ||
		recordId.toUpperCase().equals("null") ||
		formId == null || formId.length() < 1 ||
		formId.toUpperCase().equals("null")) {
		return "";
	} else {
		remindField = "S" + remindField + "S";
		String[] remindFieldArr = remindField.split("SS");
		CustomFormBD formBD = new CustomFormBD();
		for (int i = 0; i < remindFieldArr.length; i++) {
			String temp = formBD.getRemindValue(remindFieldArr[i], recordId, formId);
			if(temp == null || temp.length() < 1 ||
				temp.toUpperCase().equals("NULL")){
				remindValue+="";
			}else{
				if(temp.indexOf("`~`~`")>=0){
					temp = temp.split("`~`~`")[0];
				}
				remindValue+=temp;
			}
			/*                remindValue +=
			(temp == null || temp.length() < 1 ||
			temp.toUpperCase().equals("NULL")) ? "" : temp;*/
		}
		return remindValue;
	}
}

 
public String getHtml(String content){
	//将归档方法去掉
	content = content.replace("guidang();", "");
	//自定义删除不需要归档的部分
	while(content.indexOf("<!-- start -->")>=0 && content.indexOf("<!-- end -->")>=0){
		content = content.substring(0,content.indexOf("<!-- start -->"))+content.substring(content.indexOf("<!-- end -->")+12);
	}
	return content;
}

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
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.oa.eform.service.CustomFormBD");
    _jspx_imports_classes.add("com.js.util.util.Html2Word");
    _jspx_imports_classes.add("com.js.util.util.Word2PDF");
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
      response.setContentType("text/html; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");
      out.write("\r\n<html>\r\n<head>\r\n\t<title>流程归档</title>\r\n</head>\r\n<body bgcolor=\"#ffffff\">\r\n");

request.setCharacterEncoding("utf-8");

if(request.getParameter("gd") != null) {
    String pageContent = request.getParameter("pageContent");
	if (pageContent != null && pageContent.length() > 0) {
		pageContent = pageContent.replace("id=popToolbar", "id=popToolbar style='display:none'").replace("id=panleAll", "id=panleAll style='display:none'");
		
		/* 2013-3-29 dingfy 
		if(pageContent.indexOf("gd();") >= 0){
			int tmp = pageContent.indexOf("gd();");
			pageContent = pageContent.substring(0, tmp) + pageContent.substring(tmp + 5);
		}
		pageContent = pageContent.replaceAll("gd();", "");
		*/
		
		//去除归档按钮
		int tmp=pageContent.indexOf("cmdbutton.jsp?button=");
		
		if(tmp>0){
			pageContent=pageContent.replace(",Print,FlowToArchives", "");
		}
	}
    String workId = request.getParameter("workId");
	if (workId == null || workId.length() <= 0){
		workId = request.getAttribute("workId") + "";
	}

	java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMdd");
    String tmp = "LC_" + workId + "_" + format.format(new java.util.Date()) + ".htm";
	String path = getServletConfig().getServletContext().getRealPath("/")+ "archivesfile\\";
    String fileName = path + tmp;
	String returnValue = new com.js.oa.archives.service.ArchivesBD().archivesPigeonholeSet("GZLC",(session.getAttribute("domainId")==null?"-1":session.getAttribute("domainId").toString()));

	String createEmp = request.getParameter("submitPersonId")==null?"-1":request.getParameter("submitPersonId");

	String createOrg = "-1";

	/*String title = request.getParameter("title");
	if (title == null || title.length() <= 0)
		title = request.getAttribute("title") + "";
	*/
	
	String title = "";

	com.js.oa.userdb.util.DbOpt dbopt = null;

	try{
		//取得创建组织ID
		dbopt = new com.js.oa.userdb.util.DbOpt();
		createOrg = dbopt.executeQueryToStr("select ORG_ID from org_organization_user where EMP_ID="+createEmp);
		
		//取得归档标题
		String processId="0";
		java.sql.ResultSet rs=dbopt.executeQuery("select empname from org_employee where emp_id="+createEmp);
		if(rs.next()){
			title+=rs.getString(1);
		}
		rs.close();
		
		rs=dbopt.executeQuery("select workprocess_id from jsf_work where wf_work_id="+workId);
		if(rs.next()){
			processId=rs.getString(1);
		}
		rs.close();
		
		String processName="",remindField="";
		rs=dbopt.executeQuery("select workflowprocessname,remindfield from jsf_workflowprocess  where wf_workflowprocess_id="+processId);
		if(rs.next()){
			processName=rs.getString(1);
			remindField=rs.getString(2);
		}
		rs.close();
		
		String talbeId=request.getParameter("tableId");
		String recordId=request.getParameter("recordId");
		title += "的"+ this.getRemindValue(remindField,recordId,recordId)+processName;
		dbopt.close();
	}catch(Exception e){
		try{
			dbopt.close();
		}catch(Exception ee){
		}
	}
	
    new com.js.oa.archives.action.ArchivesAction().addArchivesWaitPigeonhole(title,tmp,Long.valueOf(request.getParameter("recordId")),"GZLC",
    		session.getAttribute("userName").toString(), new java.util.Date(), returnValue, 
    		request,createEmp,createOrg, "");
	java.io.File file = new java.io.File(fileName);
	if(!file.exists()){
		file.createNewFile();
	}
    java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.FileOutputStream(file));
    StringBuffer content = new StringBuffer("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
    content.append("<html><head><meta charset=\"GBK\"/><title>流程归档</title>")
    		//.append("<link href=\"/jsoa/skin/"+session.getAttribute("skin")+"/style-"+session.getAttribute("browserVersion")+".css\" rel=\"stylesheet\" type=\"text/css\" />")
    		.append("</head>")
    		.append("<body leftmargin=0 topmargin=0 onload=\"loadToolbar('commandBar');init();\">")
    		.append(getHtml(pageContent))
    		.append("</body></html>");
    pw.println(content.toString());
    pw.close();
    /*//将html代码转化成word
    Html2Word h2w = new Html2Word();
    h2w.html2Word(path, tmp.replace(".htm" , ".doc"), content.toString());
    //将转化的word转成pdf
    Word2PDF w2p = new Word2PDF();
    w2p.word2Pdf(path+tmp.replace(".htm" , ".doc"));*/

      out.write("\r\n<script language=\"javascript\"> \r\n\talert(\"归档成功！\");\r\n\tparent.window.close();\r\n    //parent.opener.location.reload();\r\n</script>\r\n");

}else{
      out.write("\r\n<iframe id=\"gd\" src=\"");
      out.print(request.getAttribute("myhref") + "&gd=1");
      out.write("\"></iframe>\r\n");
}
      out.write("\r\n</body>\r\n</html>\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n");
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