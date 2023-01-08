/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:45:48 UTC
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
import com.js.oa.eform.bean.CustomFormEJBBean;

public final class data2excelWithSub_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("com.js.oa.eform.bean.CustomFormEJBBean");
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

      out.write("\r\n\r\n\r\n\r\n");

try{       
    response.setCharacterEncoding("GBK");
    
    String[][] listFields = (String[][])request.getAttribute("tableHead");
    String[][] datas = (String[][])request.getAttribute("tableData");
    String tableName=request.getAttribute("tableName").toString();
    
    java.io.OutputStream os = response.getOutputStream();// È¡µÃÊä³öÁ÷   
    
    response.reset();// Çå¿ÕÊä³öÁ÷

    response.setHeader("Content-disposition", "attachment; filename=À©Õ¹²Ëµ¥"+tableName+".xls");// Éè¶¨Êä³öÎÄ¼þÍ·   
    response.setContentType("application/msexcel");// ¶¨ÒåÊä³öÀàÐÍ 
    
    //½¨Á¢excelÎÄ¼þ
    jxl.write.WritableWorkbook wwb = jxl.Workbook.createWorkbook(os); 
    
    //´´½¨Ò»¸ö¹¤×÷±í    
    jxl.write.WritableSheet ws = wwb.createSheet(tableName, 10);
    ws.setRowView(0, 500);

    //ÉèÖÃµ¥Ôª¸ñµÄÎÄ×Ö¸ñÊ½
    jxl.write.WritableFont wf = new jxl.write.WritableFont(jxl.write.WritableFont.TIMES,12,jxl.write.WritableFont.NO_BOLD,
    		false,jxl.format.UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK);
    
    //µ¥Ôª¸ñÑùÊ½
    jxl.write.WritableCellFormat wcf = new jxl.write.WritableCellFormat(wf);
    wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
    wcf.setAlignment(jxl.format.Alignment.CENTRE); 
    wcf.setBorder(Border.ALL,BorderLineStyle.THIN);
    wcf.setWrap(false);    
    //Ìî³äÊý¾ÝµÄÄÚÈÝÓÐ¶àÉÙÐÐ
    
    //ÉèÖÃÁÐÍ·Ãû
    int lie = 0,lie2 = 0;
    if (listFields != null && listFields.length > 0) {
        for (int i = 0; i < listFields.length; i++) {
          ws.addCell(new jxl.write.Label(i+lie, 0, listFields[i][1], wcf));
        }       
    }
    
    //ÉèÖÃÄÚÈÝ
    //wcf = new jxl.write.WritableCellFormat();
   
    if(datas!=null){
        CustomFormBD formBd = new CustomFormBD();
        String temp = "";
        for (int i = 0; i < datas.length; i++) {
            //datas  µÚÒ»ÁÐÎªID£¬´íÒ»Î»¶ÁÈ¡
            String[] obj = datas[i];
            for (int k=0;k<listFields.length;k++){
                //System.out.println("-=-=-="+listFields.length+"-=-=("+i+")--"+datas.length+"--("+k+")+====("+listFields[k][4]+")");
                if(obj[k]!=null && "210,211,212,214,".indexOf(listFields[k][4])>=0){
                    temp = obj[k].toString().split(";").length>0?obj[k].toString().split(";")[0]:"";
                }else if(obj[k]!=null && "450,".indexOf(listFields[k][4])>=0){
                    if(obj[k]!=null && obj[k].toString().indexOf("@@$@@")>=0){
                        temp = obj[k].toString().substring(obj[k].toString().indexOf("@@$@@")+5);
                    }else{
                        temp = obj[k].toString();
                    }           
                }else if(obj[k]==null || obj[k].toString().length()<1 || "115,".indexOf(listFields[k][4])>=0){
                    /*´¦ÀíÔ­Ê¼¹¤×÷Á÷ÖÐ¸½¼þÉÏ´«ÀàÐÍ */
                    String fieldValue = obj[k].toString();
                    if(fieldValue.indexOf(":")>0){
                        String[] tempFile = null;
                        if(fieldValue.indexOf("::")>0){
                            tempFile = fieldValue.split("::");
                        }else{
                            tempFile = new String[1];
                            tempFile[0] = fieldValue ;
                        }
                        String fileName = "";
                        if(tempFile!=null){
                            for(int j=0;j<tempFile.length;j++){
                                fileName += tempFile[j].split(":")[0]+",";
                            }
                        }
                        temp = fileName;
                    }else{
                        temp = obj[k].toString().split(";").length>1?obj[k].toString().split(";")[1].replaceAll(",,",""):"";
                    }
                /*´¦ÀíÔ­Ê¼¹¤×÷Á÷ÖÐ¸½¼þÉÏ´«ÀàÐÍ */
                }else if(listFields[k][4].equals("121")){
    				if(obj[k]!=null&&obj[k].toString().startsWith("*id*")){
    					String[] quan = new CustomFormEJBBean().qianMingTu(obj[k].toString().substring(4));
    					temp=quan[0];
    				}else if(obj[k]!=null){
    					temp=obj[k].toString();
    				}else{
    					temp="";
    				}
    			}else if(obj[k]==null || obj[k].toString().length()<1 || "103,104,105".indexOf(listFields[k][4])<0){
                    temp = (obj[k]==null || obj[k].toString().length()<1)?"":obj[k].toString();
                }else{
                    temp = formBd.getFieldShowValue(listFields[k][2],listFields[k][4],obj[k].toString(),listFields[k][0],request);
                }
                ws.addCell(new jxl.write.Label(k+lie, i+1, temp, wcf));
            }
        }
    }
    wwb.write();
    wwb.close();
}catch(Exception ex){
	ex.printStackTrace();
    response.setContentType("text/html; charset=GBK");

      out.write("\r\n<html>\r\n<head>\r\n<title></title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n    alert(\"ÎÄ¼þÎ´ÕÒµ½£¡\");\r\n    history.back();\r\n</SCRIPT>\r\n</head>\r\n<body>\r\n</body>\r\n</html>\r\n");
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
