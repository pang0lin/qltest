/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:44:05 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.kq.szgt;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import java.util.*;

public final class kqExportExcel_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("jxl.format.Colour");
    _jspx_imports_classes.add("jxl.Workbook");
    _jspx_imports_classes.add("jxl.write.Label");
    _jspx_imports_classes.add("jxl.write.WritableSheet");
    _jspx_imports_classes.add("jxl.write.WritableWorkbook");
    _jspx_imports_classes.add("jxl.format.UnderlineStyle");
    _jspx_imports_classes.add("jxl.write.WritableCellFormat");
    _jspx_imports_classes.add("jxl.format.Alignment");
    _jspx_imports_classes.add("jxl.format.BorderLineStyle");
    _jspx_imports_classes.add("jxl.format.VerticalAlignment");
    _jspx_imports_classes.add("jxl.format.Border");
    _jspx_imports_classes.add("jxl.write.WritableFont");
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
	String title = request.getAttribute("title")==null?"":request.getAttribute("title").toString();
	response.setCharacterEncoding("GBK");
    java.io.OutputStream os = response.getOutputStream();// 取得输出流   
    response.reset();// 清空输出流
    title = java.net.URLEncoder.encode(title+"考勤统计","UTF-8");
    response.setHeader("Content-disposition", "attachment; filename="+title+".xls");// 设定输出文件头   
    response.setContentType("application/msexcel");// 定义输出类型 
    
    //接收数据
    List list = (List)request.getAttribute("list");
        
    //建立excel文件
    WritableWorkbook wwb = Workbook.createWorkbook(os); 
    
    //创建一个工作表   ，一个excel文件中可以创建多个工作表
    WritableSheet ws = wwb.createSheet("打卡统计", 10);
    ws.setRowView(0, 500);//设置第一行高度
    ws.setColumnView(0, 30);//设置第一列宽度

    //设置单元格的文字格式
    WritableFont wf = new WritableFont(WritableFont.TIMES,12,WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
    
    //单元格样式
    WritableCellFormat wcf = new WritableCellFormat(wf);
    wcf.setVerticalAlignment(VerticalAlignment.CENTRE);//垂直对齐
    wcf.setAlignment(Alignment.LEFT); //水平对齐
    wcf.setBorder(Border.ALL,BorderLineStyle.NONE);//边框
    wcf.setWrap(true);//是否换行
    WritableCellFormat fwcf = new WritableCellFormat(wf);
    fwcf.setVerticalAlignment(VerticalAlignment.CENTRE);//垂直对齐
    fwcf.setAlignment(Alignment.CENTRE); //水平对齐
    fwcf.setWrap(true);//是否换行
    
    WritableFont nwf = new WritableFont(WritableFont.TIMES,12,WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.RED);
    WritableCellFormat nwcf = new WritableCellFormat(nwf);
    nwcf.setVerticalAlignment(VerticalAlignment.CENTRE);//垂直对齐
    nwcf.setAlignment(Alignment.CENTRE); //水平对齐
    nwcf.setBorder(Border.ALL,BorderLineStyle.NONE);//边框
    nwcf.setWrap(true);//是否换行
    
    
    //插入单元格值（列，行，值，单元格样式）
    ws.addCell(new Label(0, 0, "考勤统计表（考勤数据仅供参考）", fwcf));
    ws.addCell(new Label(0, 1, "部门", fwcf));
    ws.addCell(new Label(1, 1, "姓名", fwcf));
    ws.addCell(new Label(2, 1, "应出勤天数（天）", fwcf));
    ws.addCell(new Label(3, 1, "实际出勤天数（天）", fwcf));
    ws.addCell(new Label(4, 1, "出差（天）", fwcf));
    ws.addCell(new Label(5, 1, "请假（天）", fwcf));
    ws.addCell(new Label(6, 1, "外出（天）", fwcf));
    ws.addCell(new Label(7, 1, "加班（小时）", fwcf));
    ws.addCell(new Label(8, 1, "迟到（次）", fwcf));
    ws.addCell(new Label(9, 1, "早退（次）", fwcf));
    ws.addCell(new Label(10, 1, "下班未打卡（次）", fwcf));
    ws.addCell(new Label(11, 1, "上班未打卡（次）", fwcf));
    ws.addCell(new Label(12, 1, "旷工（次）", fwcf));
    for(int i=0;i<list.size();i++){
    	Object[] obj = (Object[])list.get(i);
    	for(int y=0;y<=12;y++){
    		if(y==0){
    			ws.addCell(new Label(y,i+2,obj[3].toString(),wcf));
    		}else if(y==1){
    			ws.addCell(new Label(y,i+2,obj[1].toString(),wcf));
    		}else{
    			String show = obj[y+2].toString();
    			if("0.0".equals(show)){
    				show = "0";	
    			}
    			if(!show.equals("0")){
    				ws.addCell(new Label(y,i+2,show,nwcf));
    			}else{
    				ws.addCell(new Label(y,i+2,show,fwcf));
    			}
    		}
    	}
    }
    ws.mergeCells(0, 0, 12, 0);//合并单元格
    wwb.write();
    wwb.close();	  
}catch(Exception ex){
	ex.printStackTrace();
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
