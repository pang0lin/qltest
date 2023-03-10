/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:08:27 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.iWebOfficeSign;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import java.text.*;
import java.util.*;
import java.sql.ResultSet;
import com.js.util.util.DataSourceBase;

public final class infoAjax_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

public List<String[]> historyList(String infoId){
	DataSourceBase base = new DataSourceBase();
	String sql = "SELECT history_id,historyTitle,historycontent,historyissuername,historyTime,historyversion "
			+"FROM oa_informationhistory WHERE information_id="+infoId+" ORDER BY historyTime DESC";
	//System.out.println(sql);
	ResultSet rs = null;
	List<String[]> list = new ArrayList<String[]>();
	try{
		base.begin();
		rs = base.executeQuery(sql);
		while(rs.next()){
			String[] listStrs = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
					new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(rs.getTimestamp(5).getTime()))
					,rs.getString(6)};
			list.add(listStrs);
		}
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		base.end();
	}
	return list;
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
    _jspx_imports_packages.add("java.text");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.sql.ResultSet");
    _jspx_imports_classes.add("com.js.util.util.DataSourceBase");
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
      response.setContentType("text/html;charset=GBK");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

if(request.getParameter("historyId")!=null){
	DataSourceBase base = new DataSourceBase();
	String sql = "delete from oa_informationhistory where history_id="+request.getParameter("historyId");
	System.out.println(sql);
	try{
		base.begin();
		base.executeUpdate(sql);
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		base.end();
	}
}
      out.write("\r\n<table width=\"100%\" border=1  cellspacing='0' cellpadding='0'>\r\n<tr><td align=\"center\">????????????</td><td align=\"center\">?????????</td><td align=\"center\">?????????</td><td align=\"center\">??????</td></tr>\r\n");

String infoId = request.getParameter("infoId")+"";
String queryString=request.getQueryString();
String url = java.net.URLDecoder.decode(queryString.substring(queryString.indexOf("url=")+4),"utf-8");
String mFileType = request.getParameter("mFileType")+"";
List<String[]> list = historyList(infoId);
boolean canDel = false;
if(url.indexOf("mEditType=1")>=0){
	canDel = true;
}
for(int i=0;i<list.size();i++){
	String[] listStrs = list.get(i);
	String versionUrl = url;
	String fileNameVesion = listStrs[2].substring(0,listStrs[2].lastIndexOf("_"))+mFileType;
	versionUrl = url.replace(fileNameVesion, listStrs[2]).replace("mEditType=1", "mEditType=0");
	
      out.write("\r\n<tr height=\"25px\"><td>&nbsp;<a href=\"javascript:iframeSrc('");
      out.print(versionUrl );
      out.write("');\">");
      out.print(listStrs[4] );
      out.write("</a></td>\r\n<td>&nbsp;");
      out.print(listStrs[3] );
      out.write("</td><td>&nbsp;");
      out.print(listStrs[5] );
      out.write("</td>\r\n<td>&nbsp;\r\n");
if(canDel){ 
      out.write("\r\n<img alt=\"??????\" style=\"cursor:hand\" border=\"0\" src=\"/jsoa/images/del.gif\" onclick=\"delHistory(");
      out.print(listStrs[0] );
      out.write(");\" />");
} 
      out.write("\r\n</td></tr>\r\n");
} 
      out.write("\r\n</table>\r\n");
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
