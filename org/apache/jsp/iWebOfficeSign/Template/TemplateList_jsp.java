/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:08:50 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.iWebOfficeSign.Template;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import java.sql.*;
import DBstep.iDBManager2000.*;
import com.js.system.manager.service.ManagerService;
import com.js.util.util.BrowserJudge;

public final class TemplateList_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("java.sql");
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("DBstep.iDBManager2000");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.util.util.BrowserJudge");
    _jspx_imports_classes.add("com.js.system.manager.service.ManagerService");
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
      response.setContentType("text/html; charset=gb2312");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n");
 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String templateAction;
templateAction="/jsoa/iWebOfficeSign/Template/TemplateEdit.jsp";

      out.write("\r\n<html>\r\n<head>\r\n<title>模板管理</title>\r\n<link href=\"");
      out.print(request.getContextPath());
      out.write("/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\"/>\r\n<script language=\"javascript\">\r\nfunction ConfirmDel(FileUrl){\r\n\tif (confirm('是否确定删除该模板！')){\r\n\t\tlocation.href=FileUrl;\r\n\t}\r\n}\r\n");

if(BrowserJudge.isOtherBrowser(request)){
	String url=request.getRequestURI()+"?"+request.getQueryString(); 
	session.setAttribute("mobanURL",url);
}
String haveRight=request.getParameter("haveRight")+"";
String flag=request.getParameter("flag");
if(flag==null){
	flag="1";
}
String userId = session.getAttribute("userId").toString();
ManagerService managerBD = new ManagerService();

      out.write("\r\nfunction init(){\r\n");
if("yes".equals(haveRight)){
	if("false".equals(request.getParameter("mResult"))){

      out.write("\r\n\t\talert(\"保存失败，数据库中已存在相同的模板\");\r\n");
}}else{
      out.write("\r\n\twindow.opener='xxx';\r\n\twindow.close();\r\n");
}
      out.write("\r\n}\r\nfunction changePanle(obj,cURL){\r\n\tlocation.href=((window.location.href).substring(0,(window.location.href).indexOf(\"/jsoa\")))+cURL;\r\n}\r\n</script>\r\n</head>\r\n<body class=\"MainFrameBox\" onload=\"\">\r\n<table width=\"100%\" height=\"25\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"inlineBottomLine\">\r\n  <tr>\r\n    <td>\r\n    ");

    String name="发文模板";
    if(flag==null||"".equals(flag)){ 
        name="模板";
    
      out.write("\r\n\t  <div id=\"PanleDIV\"  class=\"tabPanel\" onClick=\"changePanle(this,'/jsoa/SenddocumentBaseAction.do?action=senddocumentnumList');\">\r\n        <div class=\"tabPanelLeft\"></div>\r\n        <div class=\"tabPanelCenter\">文号</div>\r\n        <div class=\"tabPanelRight\"></div>      \r\n      </div>\r\n      <div class=\"btnBQspace AlignLeft\"></div>\r\n      <div id=\"PanleDIV\" class=\"tabPanel\" onClick=\"changePanle(this,'/jsoa/SenddocumentBaseAction.do?action=senddocumentseqList');\">\r\n        <div class=\"tabPanelLeft\"></div>\r\n        <div class=\"tabPanelCenter\">流水号</div>\r\n        <div class=\"tabPanelRight\"></div>      \r\n      </div>\r\n      <div class=\"btnBQspace AlignLeft\"></div>\r\n\t    <div id=\"PanleDIV\" class=\"tabPanel\" onClick=\"changePanle(this,'/jsoa/WFProcessAction.do?moduleId=2');\">\r\n        <div class=\"tabPanelLeft\"></div>\r\n        <div class=\"tabPanelCenter\">流程设置</div>\r\n        <div class=\"tabPanelRight\"></div>      \r\n      </div>\r\n      <div class=\"btnBQspace AlignLeft\"></div>\r\n\t    <div id=\"PanleDIV\" class=\"tabPanel\" onClick=\"changePanle(this,'/jsoa/SenddocumentBaseAction.do?action=wordlist');\">\r\n");
      out.write("        <div class=\"tabPanelLeft\"></div>\r\n        <div class=\"tabPanelCenter\">机关代字</div>\r\n        <div class=\"tabPanelRight\"></div>      \r\n      </div>\r\n      <div class=\"btnBQspace AlignLeft\"></div>\r\n\t    <div id=\"PanleDIV\" class=\"tabPanel\" onClick=\"changePanle(this,'/jsoa/SenddocumentBaseAction.do?action=zjkytopical');\">\r\n        <div class=\"tabPanelLeft\"></div>\r\n        <div class=\"tabPanelCenter\">主题词</div>\r\n        <div class=\"tabPanelRight\"></div>      \r\n      </div>\r\n      <div class=\"btnBQspace AlignLeft\"></div>\r\n\t    <div id=\"PanleDIV\" class=\"tabPanel\" onClick=\"changePanle(this,'/jsoa/SenddocumentBaseAction.do?action=unitlist');\">\r\n        <div class=\"tabPanelLeft\"></div>\r\n        <div class=\"tabPanelCenter\">单位</div>\r\n        <div class=\"tabPanelRight\"></div>      \r\n      </div>\r\n       <div class=\"btnBQspace AlignLeft\"></div>\r\n\t    <div id=\"PanleDIV\" class=\"tabPanel\" onClick=\"changePanle(this,'/jsoa/SenddocumentBaseAction.do?action=listBaseinfo');\">\r\n        <div class=\"tabPanelLeft\"></div>\r\n        <div class=\"tabPanelCenter\">参数</div>\r\n");
      out.write("        <div class=\"tabPanelRight\"></div>      \r\n      </div>\r\n       <div class=\"btnBQspace AlignLeft\"></div>\r\n\t    <div id=\"PanleDIV\" class=\"tabPanel\" onClick=\"changePanle(this,'/jsoa/iWebOfficeSign/BookMark/BookMarkList.jsp?haveRight=yes');\">\r\n        <div class=\"tabPanelLeft\"></div>\r\n        <div class=\"tabPanelCenter\">标签</div>\r\n        <div class=\"tabPanelRight\"></div>      \r\n      </div>\r\n      ");
 }else{ if(flag!=null&&("1".equals(flag)||("2".equals(flag)))){
      out.write("\r\n           <div id=\"PanleDIV\" class=\"tabPanel\" onClick=\"changePanle(this,'/jsoa/TemplateAction.do?action=list&module=2');\">\r\n\t        <div class=\"tabPanelLeft\"></div>\r\n\t        <div class=\"tabPanelCenter\">协同模板</div>\r\n\t        <div class=\"tabPanelRight\"></div>      \r\n\t      </div>\r\n      \r\n\t     ");
if(managerBD.hasRight(userId, "04*01*02")){
      out.write(" \r\n\t      <div class=\"btnBQspace AlignLeft\"></div> \r\n\t\t  <div id=\"PanleDIV\" class=\"tabPanel\" onClick=\"changePanle(this,'/jsoa/WorkReportTemplateAction.do?action=list');\">\r\n\t        <div class=\"tabPanelLeft\"></div>\r\n\t        <div class=\"tabPanelCenter\">工作汇报</div>\r\n\t        <div class=\"tabPanelRight\"></div>  \r\n\t      </div>\r\n\t    ");
} 
      out.write("\r\n\t    ");
if(managerBD.hasRight(userId, "01*02")){ 
      out.write("\r\n\t      <div class=\"btnBQspace AlignLeft\"></div>\r\n\t\t    <div id=\"PanleDIV\" class=\"tabPanel\" onClick=\"changePanle(this,'/jsoa/TemplateAction.do?action=list&module=1');\">\r\n\t        <div class=\"tabPanelLeft\"></div>\r\n\t        <div class=\"tabPanelCenter\">知识模板</div>\r\n\t        <div class=\"tabPanelRight\"></div>      \r\n\t      </div>\r\n\t     ");
}
	     if("1".equals(flag)){
      out.write(" \r\n\t      <div class=\"btnBQspace AlignLeft\"></div>\r\n\t\t    <div id=\"PanleDIV\" class=\"tabPanelSelected\">\r\n\t        <div class=\"tabPanelLeft\"></div>\r\n\t        <div class=\"tabPanelCenter\">");
      out.print(name);
      out.write("</div>\r\n\t        <div class=\"tabPanelRight\"></div>      \r\n\t      </div>\r\n\t      ");
if(managerBD.hasRight(userId, "02*02*02")){ 
      out.write("\r\n\t      <div class=\"btnBQspace AlignLeft\"></div>\r\n\t\t    <div id=\"PanleDIV\" class=\"tabPanel\" onClick=\"changePanle(this,'/jsoa/iWebOfficeSign/Template/TemplateList.jsp?haveRight=yes&flag=2');\">\r\n\t        <div class=\"tabPanelLeft\"></div>\r\n\t        <div class=\"tabPanelCenter\">流程模板</div>\r\n\t        <div class=\"tabPanelRight\"></div>      \r\n\t      </div>\r\n\t     ");
}
	     }else{
	     if(managerBD.hasRight(userId, "03*03*01")){
      out.write("\r\n\t     <div class=\"btnBQspace AlignLeft\"></div>\r\n\t\t    <div id=\"PanleDIV\" class=\"tabPanel\" onClick=\"changePanle(this,'/jsoa/iWebOfficeSign/Template/TemplateList.jsp?haveRight=yes&flag=1')\";>\r\n\t        <div class=\"tabPanelLeft\"></div>\r\n\t        <div class=\"tabPanelCenter\">");
      out.print(name);
      out.write("</div>\r\n\t        <div class=\"tabPanelRight\"></div>      \r\n\t      </div>\r\n\t      ");
}
      out.write("\r\n\t      <div class=\"btnBQspace AlignLeft\"></div>\r\n\t\t    <div id=\"PanleDIV\" class=\"tabPanelSelected\">\r\n\t        <div class=\"tabPanelLeft\"></div>\r\n\t        <div class=\"tabPanelCenter\">流程模板</div>\r\n\t        <div class=\"tabPanelRight\"></div>      \r\n\t      </div>\r\n\t      \r\n      ");
}}}if(flag==null||"".equals(flag)){  
      out.write("\r\n      ");
if(managerBD.hasRight(session.getAttribute("userId").toString(),"03*15*86")){ 
      out.write("\r\n      \r\n       <div class=\"btnBQspace AlignLeft\"></div>\r\n\t    <div id=\"PanleDIV\" class=\"tabPanel\" onClick=\"changePanle(this,'/jsoa/iWebOfficeSign/Signature/SignatureList.jsp?haveRight=yes');\">\r\n        <div class=\"tabPanelLeft\"></div>\r\n        <div class=\"tabPanelCenter\">印章</div>\r\n        <div class=\"tabPanelRight\"></div>      \r\n      </div>    \r\n      ");
} }
      out.write("\r\n\t</td>\r\n\t  <td colspan=4 align=\"right\">\r\n\t\t  <input style=\"width:85px;\" class=\"btnButton4font\" type=button name=\"AddDocTemplate\" value=\"新建Word模板\"  onclick=\"javascript:location.href='");
      out.print(templateAction);
      out.write("?FileType=.doc&flag=");
      out.print(flag );
      out.write("';\">\r\n\t\t  <input style=\"width:85px\" class=\"btnButton4font\" type=button name=\"AddXslTemplate\" value=\"新建Excel模板\" onclick=\"javascript:location.href='");
      out.print(templateAction);
      out.write("?FileType=.xls&flag=");
      out.print(flag );
      out.write("';\">\r\n\t\t  <!-- <input type=button name=\"Return\" value=\"返 回\"  onclick=\"javascript:location.href='../DocumentList.jsp';\"> -->\r\n\t\t</td>\r\n  </tr>\r\n</table>\r\n\r\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"listTable\">  \r\n\t<tr>\r\n\t\t<td class=\"listTableHead\" height=\"26\">编号</td>\r\n\t\t<td class=\"listTableHead\">模板名称</td>\r\n\t\t<td class=\"listTableHead\">模板类型</td>\r\n\t\t<td class=\"listTableHead\">模板说明</td>\r\n\t\t<td class=\"listTableHeadLast\">处理</td>\r\n\t</tr>\r\n");

  
  String domainId = session.getAttribute("domainId")==null?"0":session.getAttribute("domainId").toString();
  DBstep.iDBManager2000 DbaObj=new DBstep.iDBManager2000();
  
  if (DbaObj.OpenConnection())
  {
    try
    {
      ResultSet result=DbaObj.ExecuteQuery("Select RecordID,FileName,FileType,Descript From Template_File where flag="+flag+" and DOMAIN_ID=" + domainId + " order by TemplateID desc") ;
      while ( result.next() )
      {
        String mRecordID=result.getString("RecordID");
        String mFileName=result.getString("FileName");
        String mFileType=result.getString("FileType");
        String mDescript=result.getString("Descript");

      out.write("\r\n\t<tr align=\"center\">\r\n\t\t<td class=\"listTableLine2\">");
      out.print(mRecordID);
      out.write("&nbsp;</td>\r\n\t\t<td class=\"listTableLine2\">");
      out.print(mFileName);
      out.write("&nbsp;</td>\r\n\t\t<td class=\"listTableLine2\">");
      out.print(mFileType);
      out.write("&nbsp;</td>\r\n\t\t<td class=\"listTableLine2\">");
      out.print(mDescript);
      out.write("&nbsp;</td>\r\n\t\t<td class=\"listTableLine2\" width=148 nowrap>\r\n\t\t\t<input type=button onclick=\"javascript:location.href='");
      out.print(templateAction);
      out.write("?RecordID=");
      out.print(mRecordID);
      out.write("&FileType=");
      out.print(mFileType);
      out.write("&flag=");
      out.print(flag );
      out.write("';\" name=\"Edit\" value=\" 修 改 \">\r\n\t\t\t<input type=button onclick=\"javascript:ConfirmDel('TemplateDel.jsp?RecordID=");
      out.print(mRecordID);
      out.write("&flag=");
      out.print(flag );
      out.write("');\" name=\"Del\" value=\" 删 除 \">\r\n\t\t</td>\r\n\t</tr>\r\n");

      }
      result.close() ;
    }
    catch(Exception e)
    {
      System.out.println(e.toString());
    }
    DbaObj.CloseConnection() ;
  }
  else
  {
    out.println("OpenDatabase Error") ;
  }

      out.write("\r\n</table>\r\n</body>\r\n</html>\r\n");
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