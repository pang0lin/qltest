/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:44:11 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.kq.bhw;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.oa.hr.kq.pojo.BhwKqItem;
import java.util.List;
import com.js.system.vo.usermanager.EmployeeVO;
import java.text.DateFormat;
import java.util.Locale;

public final class exportExcel_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-logic.tld", Long.valueOf(1499751390000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.util.List");
    _jspx_imports_classes.add("com.js.system.vo.usermanager.EmployeeVO");
    _jspx_imports_classes.add("com.js.oa.hr.kq.pojo.BhwKqItem");
    _jspx_imports_classes.add("java.util.Locale");
    _jspx_imports_classes.add("java.text.DateFormat");
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
      response.setContentType("application/vnd.ms-excel;charset=GBK");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n<html>\r\n<meta http-equiv=\"Content-Type\" content=\"application/vnd.ms-excel; charset=GBK\">\r\n");

request.setCharacterEncoding("GBK");

String export = request.getAttribute("export").toString();
String[] header = (String[]) request.getAttribute("header");
List infoList = (List) request.getAttribute("infoList");

String headerName = "", colspan = "1", fileName = "";
if("qd".equals(export)){
	headerName = "姓名\\出勤时间";
	fileName = "签到统计";
	colspan = "2";
} else if("cq".equals(export)){
	headerName = "姓名\\日期";
	fileName = "出勤统计";
} else if("cqhz".equals(export)){
	headerName = "姓名\\类别（默认：天）";
	fileName = "出勤汇总";
}
response.setHeader("Content-disposition","attachment;filename=infoList.xls");

      out.write("\r\n<head>\r\n    <title>导出</title>\r\n</head>\r\n<body>\r\n\t<table border=\"1\">\r\n\t    <tr>\r\n\t        <th rowspan=\"");
      out.print(colspan );
      out.write("\">部门</th>\r\n\t        <th rowspan=\"");
      out.print(colspan );
      out.write("\">工号</th>\r\n\t        <th rowspan=\"");
      out.print(colspan );
      out.write("\">姓名</th>\r\n\t        ");

	        for(int i=0; i<header.length; i++){
	        	
      out.write("\r\n\t        \t<th colspan=\"");
      out.print(colspan );
      out.write('"');
      out.write('>');
      out.print(header[i] );
      out.write("</th>\r\n\t        \t");

	        }
	        
      out.write("\r\n\t    </tr>\r\n\t    ");

		if("qd".equals(export)){
			
      out.write("\r\n\t\t\t<tr>\r\n\t\t\t\t");

				for(int i=0; i<header.length; i++){
					
      out.write("\r\n\t\t\t\t\t<th>上午</th>\r\n\t\t\t\t\t<th>下午</th>\r\n\t\t\t\t\t");

				}
				
      out.write("\r\n\t\t\t</tr>\r\n\t\t\t");

		}
		if(null!=infoList && infoList.size()>0){
			String[] details = null;
			BhwKqItem item = null;
			for(int j=0; j<infoList.size(); j++){
				item = (BhwKqItem) infoList.get(j);
				details = item.getCqqk();
				
      out.write("\r\n\t\t\t\t<tr>\r\n\t\t\t\t\t<td>");
      out.print(item.getDeptName() );
      out.write("</td>\r\n\t\t\t\t\t<td>");
      out.print(item.getEmpNumber() );
      out.write("</td>\r\n\t\t\t\t\t<td>");
      out.print(item.getName() );
      out.write("</td>\r\n\t\t\t\t\t");

					for(int i=0; i<details.length; i++){
						
      out.write("\r\n\t\t\t\t\t\t<td>");
      out.print(details[i] );
      out.write("</td>\r\n\t\t\t\t\t\t");

					}
					
      out.write("\r\n\t\t\t\t</tr>\r\n\t\t\t\t");

			}
		}
		
      out.write("\r\n\t</table>\r\n</body>\r\n</html>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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