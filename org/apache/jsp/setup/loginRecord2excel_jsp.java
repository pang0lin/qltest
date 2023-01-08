/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:51:23 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.setup;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import com.js.oa.eform.service.CustomFormBD;
import com.js.oa.eform.bean.CustomFormEJBBean;
import java.sql.PreparedStatement;
import java.util.*;

public final class loginRecord2excel_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.oa.eform.service.CustomFormBD");
    _jspx_imports_classes.add("jxl.format.BorderLineStyle");
    _jspx_imports_classes.add("com.js.oa.eform.bean.CustomFormEJBBean");
    _jspx_imports_classes.add("jxl.format.Border");
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
      response.setContentType("application/vnd.ms-excel;charset=GBK");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n");


try{ 	   
	response.setCharacterEncoding("GBK");
	
    int num=Integer.valueOf(request.getAttribute("num").toString());
    String headTitle = request.getAttribute("headTitle")+"";
    String[] head = headTitle.split(",");
    String beginDate = request.getAttribute("begin")==null?"":request.getAttribute("begin")+"";
    String endDate = request.getAttribute("end")==null?"":request.getAttribute("end")+"";
    List dataList = (List)request.getAttribute("dataList");
    
    java.io.OutputStream os = response.getOutputStream();// 取得输出流   
    
    response.reset();// 清空输出流
	String title = java.net.URLEncoder.encode(beginDate+"至"+endDate+"上线统计","UTF-8");
    response.setHeader("Content-disposition", "attachment; filename="+title+".xls");// 设定输出文件头   
    response.setContentType("application/msexcel");// 定义输出类型 
        
    //建立excel文件
    jxl.write.WritableWorkbook wwb = jxl.Workbook.createWorkbook(os); 
    
    //创建一个工作表    
    jxl.write.WritableSheet ws = wwb.createSheet("上线统计", 10);
    ws.setRowView(0, 500);
    ws.setColumnView(0, 30);//设置第一列宽度
    ws.setColumnView(num+1, 15);//设置倒数第二列宽度

    //设置单元格的文字格式
    jxl.write.WritableFont wf = new jxl.write.WritableFont(
  		  jxl.write.WritableFont.TIMES,
  		  12,
  		  jxl.write.WritableFont.NO_BOLD,
  		  false,
  		  jxl.format.UnderlineStyle.NO_UNDERLINE,
  		  jxl.format.Colour.BLACK);
    
    //单元格样式
    jxl.write.WritableCellFormat wcf = new jxl.write.WritableCellFormat(wf);
    wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
    wcf.setAlignment(jxl.format.Alignment.CENTRE); 
    wcf.setBorder(Border.ALL,BorderLineStyle.THIN);
    wcf.setWrap(false);    
    
    //单元格样式
    jxl.write.WritableCellFormat nwcf = new jxl.write.WritableCellFormat(wf);
    nwcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
    nwcf.setAlignment(jxl.format.Alignment.LEFT); 
    nwcf.setBorder(Border.ALL,BorderLineStyle.THIN);
    nwcf.setWrap(false); 
	
	//填充数据有多少列
	int columns = num+3;
	ws.addCell(new jxl.write.Label(0, 0, head[0], nwcf));
	for (int i = 1; i < columns; i++) {
		ws.addCell(new jxl.write.Label(i, 0, head[i], wcf));
    }
    
    //设置内容
    //wcf = new jxl.write.WritableCellFormat();
    for(int j=0;j<dataList.size();j++){
    	Object[] obj = (Object[])dataList.get(j);
    	int rowNum = j+1;
    	int level = Integer.valueOf(obj[columns+1]+"");
    	String beforeOrgName = new String();
    	if(level != 0){
	    	for(int l=0;l<level;l++){
	    		beforeOrgName = "        "+beforeOrgName;
	    	}
	    	beforeOrgName += "|-";
	    }
    	ws.addCell(new jxl.write.Label(0, rowNum, beforeOrgName+obj[0], nwcf));
    	for(int k=1;k<columns;k++){
    		ws.addCell(new jxl.write.Label(k, rowNum, obj[k].toString(), wcf));
    	}
    }
    
    wwb.write();
    wwb.close(); 	  
}catch(Exception ex){
	response.setContentType("text/html; charset=GBK");
	  
      out.write("\r\n\t  <html>\r\n    <head>\r\n    <title></title>\r\n    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n\t  <SCRIPT LANGUAGE=\"JavaScript\">\r\n\t\talert(\"File Not Found!\");\r\n\t    history.back();\r\n\t  </SCRIPT>\r\n\t  </head>\r\n\t  <body>\r\n\t  </body>\r\n\t  </html>\r\n\t  ");

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
