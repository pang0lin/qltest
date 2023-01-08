/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:45:49 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.module.template;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import com.js.oa.eform.service.CustomFormBD;

public final class templete_005fexport_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.oa.eform.service.CustomFormBD");
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
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n");

try{ 	   
	response.setCharacterEncoding("GBK");
	
	String[][] listFields = (String[][])request.getAttribute("listFields");
    String[][] datas = (String[][])request.getAttribute("datas");
    String tableName=request.getAttribute("mainTableName").toString();
    
    java.io.OutputStream os = response.getOutputStream();// È¡µÃÊä³öÁ÷   
    
    response.reset();// Çå¿ÕÊä³öÁ÷

    response.setHeader("Content-disposition", "attachment; filename="+"Êý¾Ý±í-"+tableName+".xls");// Éè¶¨Êä³öÎÄ¼þÍ·   
    response.setContentType("application/msexcel");// ¶¨ÒåÊä³öÀàÐÍ 
        
    //½¨Á¢excelÎÄ¼þ
    jxl.write.WritableWorkbook wwb = jxl.Workbook.createWorkbook(os); 
    
    //´´½¨Ò»¸ö¹¤×÷±í    
    jxl.write.WritableSheet ws = wwb.createSheet(tableName, 10);
    ws.setRowView(0, 500);

    //ÉèÖÃµ¥Ôª¸ñµÄÎÄ×Ö¸ñÊ½
    jxl.write.WritableFont wf = new jxl.write.WritableFont(
  		  jxl.write.WritableFont.TIMES,
  		  12,
  		  jxl.write.WritableFont.NO_BOLD,
  		  false,
  		  jxl.format.UnderlineStyle.NO_UNDERLINE,
  		  jxl.format.Colour.BLACK);
    
    //µ¥Ôª¸ñÑùÊ½
    jxl.write.WritableCellFormat wcf = new jxl.write.WritableCellFormat(wf);
    wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
    wcf.setAlignment(jxl.format.Alignment.CENTRE); 
    wcf.setBorder(Border.ALL,BorderLineStyle.THIN);
    wcf.setWrap(false);    
    

    //Ìî³äÊý¾ÝµÄÄÚÈÝÓÐ¶àÉÙÐÐ
    int len=2;//((String[])hmOut.get(excelKeyArray[0])).length;
    
    //ÉèÖÃÁÐÍ·Ãû
    if (listFields != null && listFields.length > 0) {
    	for (int i = 0; i < listFields.length; i++) {
	  	  ws.addCell(new jxl.write.Label(i, 0, listFields[i][1], wcf));
	    }    	
    }
    
    //ÉèÖÃÄÚÈÝ
    //wcf = new jxl.write.WritableCellFormat();
   
    if(datas!=null){
    	CustomFormBD formBd = new CustomFormBD();
    	String temp;
    
	    for (int i = 0; i < datas.length; i++) {
	    	//datas  µÚÒ»ÁÐÎªID£¬´íÒ»Î»¶ÁÈ¡
	      	for (int j=1;j<=listFields.length;j++){
	      		if ("103,104,105".indexOf(listFields[j-1][4].toString()) >=0 
	                    && datas[i][j+2] != null
	                    && datas[i][j+2].length() > 0){  
	      			temp=formBd.getFieldShowValue(listFields[j-1][2], listFields[j-1][4],datas[i][j+2],listFields[j-1][0],request);
	      		}else if("210,211,212,214".indexOf(listFields[j-1][4].toString()) >=0 
	                    && datas[i][j+2] != null
	                    && datas[i][j+2].length() > 0){
	      			temp=datas[i][j+2];
	      			if(temp!=null && temp.indexOf(";")>0){
                 	   temp=temp.substring(0,temp.indexOf(";"));
                 	}
	      		}else if("115".indexOf(listFields[j-1][4].toString()) >=0 
	                    && datas[i][j+2] != null
	                    && datas[i][j+2].length() > 0){
	      			temp=datas[i][j+2];
	      			if(temp!=null && temp.indexOf(";")>0){
	                 	   temp=temp.substring(temp.indexOf(";")+1);
	                }
	      		}else{
	      			temp=datas[i][j+2];
	      		} 
	      			      		
	         	//ws.addCell(new jxl.write.Label(j, i+1, ((String[])hmOut.get(excelKeyArray[j]))[i], wcf));
	   	   		ws.addCell(new jxl.write.Label(j-1, i+1, temp, wcf));
	      	}
	    }
    }
    
    

    wwb.write();
    wwb.close(); 	  

}catch(Exception ex){
	ex.printStackTrace();
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