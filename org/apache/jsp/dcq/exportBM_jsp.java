/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:44:34 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.dcq;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.text.SimpleDateFormat;
import java.net.URLEncoder;
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

public final class exportBM_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("jxl.write.Label");
    _jspx_imports_classes.add("jxl.write.WritableSheet");
    _jspx_imports_classes.add("java.text.SimpleDateFormat");
    _jspx_imports_classes.add("jxl.format.Alignment");
    _jspx_imports_classes.add("jxl.format.BorderLineStyle");
    _jspx_imports_classes.add("jxl.format.VerticalAlignment");
    _jspx_imports_classes.add("jxl.format.Border");
    _jspx_imports_classes.add("jxl.write.WritableFont");
    _jspx_imports_classes.add("java.net.URLEncoder");
    _jspx_imports_classes.add("jxl.Workbook");
    _jspx_imports_classes.add("jxl.write.WritableWorkbook");
    _jspx_imports_classes.add("jxl.format.UnderlineStyle");
    _jspx_imports_classes.add("jxl.write.WritableCellFormat");
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");

try{
	response.setCharacterEncoding("GBK");
	String title = "信息发布保密审查表";
    java.io.OutputStream os = response.getOutputStream();// 取得输出流   
    response.reset();// 清空输出流
    title = URLEncoder.encode(title, "UTF-8");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    response.setHeader("Content-disposition", "attachment; filename=" + title + "_" + sdf.format(new Date()) + ".xls");// 设定输出文件头   
    response.setContentType("application/msexcel");// 定义输出类型 
    
    //接收数据
    List list = (List) request.getAttribute("list");
        
    //建立excel文件
    WritableWorkbook wwb = Workbook.createWorkbook(os); 
    
    //创建一个工作表   ，一个excel文件中可以创建多个工作表
    WritableSheet ws = wwb.createSheet("保密审查信息", 10);
    ws.setRowView(0, 500);//设置行高度
    ws.setRowView(1, 400);

    //设置单元格的文字格式
    // 内容字体
    WritableFont wf = new WritableFont(WritableFont.TIMES,12,WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
    WritableCellFormat wcf = new WritableCellFormat(wf);
    wcf.setVerticalAlignment(VerticalAlignment.CENTRE);//垂直对齐
    wcf.setAlignment(Alignment.LEFT); //水平对齐
    wcf.setBorder(Border.ALL,BorderLineStyle.NONE);//边框
    wcf.setWrap(true);//是否换行
    // 表头
    WritableFont wf1 = new WritableFont(WritableFont.TIMES,18,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
    WritableCellFormat fwcf = new WritableCellFormat(wf1);
    fwcf.setVerticalAlignment(VerticalAlignment.CENTRE);//垂直对齐
    fwcf.setAlignment(Alignment.CENTRE); //水平对齐
    fwcf.setWrap(false);//是否换行
    // 标题
    WritableFont wf2 = new WritableFont(WritableFont.TIMES,12,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
    WritableCellFormat nwcf = new WritableCellFormat(wf2);
    nwcf.setVerticalAlignment(VerticalAlignment.CENTRE);//垂直对齐
    nwcf.setAlignment(Alignment.CENTRE); //水平对齐
    nwcf.setBorder(Border.ALL,BorderLineStyle.NONE);//边框
    nwcf.setWrap(false);//是否换行
    
    
    //插入单元格值（列，行，值，单元格样式）
    ws.addCell(new Label(0, 0, "信息发布保密审查表", fwcf));
    ws.addCell(new Label(0, 1, "单位：东城区行政服务中心", wcf));
    ws.addCell(new Label(0, 2, "序号", nwcf));
    ws.addCell(new Label(1, 2, "部门", nwcf));
    ws.addCell(new Label(2, 2, "信息名称", nwcf));
    ws.addCell(new Label(3, 2, "发布形式", nwcf));
    ws.addCell(new Label(4, 2, "承办人", nwcf));
    ws.addCell(new Label(5, 2, "工作机构审查意见", nwcf));
    ws.addCell(new Label(6, 2, "部门领导", nwcf));
    ws.addCell(new Label(7, 2, "主管领导意见", nwcf));
    ws.addCell(new Label(8, 2, "主管领导", nwcf));
    int i = 0;
    System.out.println(list.size());
    for(; i<list.size();){
    	Object[] obj = (Object[])list.get(i);
    	String orgLeaderName = "";
    	for(int y=0; y<9; y++){
    		if(0 == y){
    			ws.addCell(new Label(y, i+3, (i+1)+"", wcf));
    		} else if(1 == y){
	    		ws.addCell(new Label(y, i+3, obj[3].toString(), wcf));
    		} else if(2 == y){
	    		ws.addCell(new Label(y, i+3, obj[1].toString(), wcf));
    		} else if(3 == y){
	    		ws.addCell(new Label(y, i+3, "网站公开", wcf));
    		} else if(4 == y){
	    		ws.addCell(new Label(y, i+3, obj[2].toString(), wcf));
    		} else if(5 == y){
	    		ws.addCell(new Label(y, i+3, "同意", wcf));
    		} else if(6 == y){
    			orgLeaderName = null!=obj[4]&&!"null".equalsIgnoreCase(obj[4].toString()) ? obj[4].toString() : "";
    			if(orgLeaderName.endsWith(",")) orgLeaderName = orgLeaderName.substring(0, orgLeaderName.length()-1);
	    		ws.addCell(new Label(y, i+3, orgLeaderName, wcf));
    		} else if(7 == y){
	    		ws.addCell(new Label(y, i+3, "同意", wcf));
    		} else if(8 == y){
	    		ws.addCell(new Label(y, i+3, "董凌霄", wcf));
    		}
    	}
    	i++;
    }
    i += 3;
    ws.addCell(new Label(0, i, "注：\n1、本表有机关、单位信息发布主管部门负责组织填写和保管；\n2、机关、单位发布信息均应进行登记；\n3、请在对应的口打√；\n4、不明确事项应当由机关、单位保密工作机构组织保密审查。", wcf));
    ws.setRowView(i, 1800);//设置最后一行高度
    ws.mergeCells(0, 0, 8, 0);//合并单元格
    ws.mergeCells(0, 1, 8, 0);
    ws.mergeCells(0, i, 8, 0);
    
    
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