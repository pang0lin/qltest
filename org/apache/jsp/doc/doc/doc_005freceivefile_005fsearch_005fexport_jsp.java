/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:05:04 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.doc.doc;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import java.util.*;

public final class doc_005freceivefile_005fsearch_005fexport_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("jxl.format.BorderLineStyle");
    _jspx_imports_classes.add("jxl.format.Border");
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

      out.write("\r\n\r\n\r\n\r\n");


try{ 	   
	response.setCharacterEncoding("GBK");
	
    List dataList = (List)request.getAttribute("mylist");
    
    java.io.OutputStream os = response.getOutputStream();// ???????????????   
    
    response.reset();// ???????????????
    response.setHeader("Content-disposition", "attachment; filename=receivefile.xls");// ?????????????????????   
    response.setContentType("application/msexcel");// ?????????????????? 
        
    //??????excel??????
    jxl.write.WritableWorkbook wwb = jxl.Workbook.createWorkbook(os); 
    
    //?????????????????????    
    jxl.write.WritableSheet ws = wwb.createSheet("??????", 10);
    ws.setRowView(0, 500);
    ws.setColumnView(0, 15);//?????????????????????
    ws.setColumnView(1, 20);
    ws.setColumnView(2, 20);
    ws.setColumnView(3, 30);
    ws.setColumnView(4, 45);
	ws.setColumnView(5, 15);
    ws.setColumnView(6, 20);

    //??????????????????????????????
    jxl.write.WritableFont wf = new jxl.write.WritableFont(
  		  jxl.write.WritableFont.TIMES,
  		  12,
  		  jxl.write.WritableFont.BOLD,
  		  false,
  		  jxl.format.UnderlineStyle.NO_UNDERLINE,
  		  jxl.format.Colour.BLACK);
    
    jxl.write.WritableFont nwf = new jxl.write.WritableFont(
  		  jxl.write.WritableFont.TIMES,
  		  12,
  		  jxl.write.WritableFont.NO_BOLD,
  		  false,
  		  jxl.format.UnderlineStyle.NO_UNDERLINE,
  		  jxl.format.Colour.BLACK);
    
    //???????????????
    jxl.write.WritableCellFormat wcf = new jxl.write.WritableCellFormat(wf);
    wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
    wcf.setAlignment(jxl.format.Alignment.CENTRE); 
    wcf.setBorder(Border.ALL,BorderLineStyle.THIN);
    wcf.setWrap(false);    
    
    //???????????????
    jxl.write.WritableCellFormat nwcf = new jxl.write.WritableCellFormat(nwf);
    nwcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
    nwcf.setAlignment(jxl.format.Alignment.LEFT); 
    nwcf.setBorder(Border.ALL,BorderLineStyle.THIN);
    nwcf.setWrap(false); 
	
    ws.addCell(new jxl.write.Label(0, 0, "?????????", wcf));
	ws.addCell(new jxl.write.Label(1, 0, "????????????", wcf));
	ws.addCell(new jxl.write.Label(2, 0, "????????????", wcf));
	ws.addCell(new jxl.write.Label(3, 0, "??????", wcf));
	ws.addCell(new jxl.write.Label(4, 0, "??????", wcf));
	ws.addCell(new jxl.write.Label(5, 0, "????????????", wcf));
	ws.addCell(new jxl.write.Label(6, 0, "????????????", wcf));
    
    //????????????
    //wcf = new jxl.write.WritableCellFormat();
	Object[] procObj = null;
	com.js.oa.jsflow.util.NewProc newProc = new com.js.oa.jsflow.util.NewProc();
    for(int j=0;j<dataList.size();j++){
    	Object[] obj = (Object[])dataList.get(j);
    	int rowNum = j+1;
		String status = "";
		if(obj[11] == null){
			status = "";
		}else if("0".equals(obj[11]+"")){
			procObj = (Object[]) newProc.getDocInfo("3", obj[0].toString());
			status = procObj[0]+"";
			//out.print("?????????");
		}else if("1".equals(obj[11]+"")){
			status = "????????????";
		}else if("2".equals(obj[11]+"")){
			status = "??????";
		}
   		ws.addCell(new jxl.write.Label(0, rowNum, obj[14]==null?"":obj[14].toString(), nwcf));
   		ws.addCell(new jxl.write.Label(1, rowNum, obj[10]==null?"":obj[10].toString(), nwcf));
   		ws.addCell(new jxl.write.Label(2, rowNum, obj[3]==null?"":com.js.doc.util.TimeTool.convertObjectToYearMonthDate(obj[12]).toString(), nwcf));
   		ws.addCell(new jxl.write.Label(3, rowNum, obj[1]==null?"":obj[1].toString(), nwcf));
		ws.addCell(new jxl.write.Label(4, rowNum, obj[2]==null?"":obj[2].toString(), nwcf));
		ws.addCell(new jxl.write.Label(5, rowNum, status, nwcf));
		ws.addCell(new jxl.write.Label(6, rowNum, obj[4]==null?"":com.js.doc.util.TimeTool.convertObjectToYearMonthDate(obj[4]).toString(), nwcf));
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
