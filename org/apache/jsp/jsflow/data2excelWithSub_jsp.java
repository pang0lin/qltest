/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:06:34 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import com.js.oa.eform.service.CustomFormBD;
import com.js.oa.eform.bean.CustomFormEJBBean;
import java.sql.PreparedStatement;

public final class data2excelWithSub_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {


public String getRelationFlow(String pageId,String infoId,String fieldName){
	java.sql.Connection conn=null;
	String retStr="";
	String sql="select relationinfoname,relationsubid,relationinfoid from oa_relationdata " +
	 		"where moduletype='jsflow' and modulesubid=? and infoId=? and relationobjecttype='jsflow'" +
	 		" and (fieldName is null or fieldName='' or fieldName=?)";
	try{
		conn=new com.js.util.util.DataSourceBase().getDataSource().getConnection();
		PreparedStatement pstmt=conn.prepareStatement(sql);
		pstmt.setString(1,pageId);
		pstmt.setString(2,infoId);
		pstmt.setString(3,fieldName);
		java.sql.ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			retStr+=rs.getString(1)+" ";
		}
		rs.close();
		pstmt.close();
		conn.close();
		
	}catch(Exception ex){
		if(conn!=null){
			try{
				conn.close();
				
			}catch(Exception err){
				err.printStackTrace();
			}
		}
		ex.printStackTrace();
	}
	return retStr;
	
}

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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");
      out.write('\r');
      out.write('\n');

try{       
    response.setCharacterEncoding("GBK");
    
    String[][] listFields = (String[][])request.getAttribute("tableHead");
    String[][] datas = (String[][])request.getAttribute("tableData");
    String tableName=request.getAttribute("tableName").toString();
    String tableId=request.getAttribute("tableId").toString();
    String fieldExcel=request.getParameter("fieldExcel");
    
    java.io.OutputStream os = response.getOutputStream();// 取得输出流   
    
    response.reset();// 清空输出流

    response.setHeader("Content-disposition", "attachment; filename="+"Excel-Sub-"+tableName+".xls");// 设定输出文件头   
    response.setContentType("application/msexcel");// 定义输出类型 
    
    //建立excel文件
    jxl.write.WritableWorkbook wwb = jxl.Workbook.createWorkbook(os); 
    
    //创建一个工作表    
    jxl.write.WritableSheet ws = wwb.createSheet(tableName, 10);
    ws.setRowView(0, 500);

    //设置单元格的文字格式
    jxl.write.WritableFont wf = new jxl.write.WritableFont(jxl.write.WritableFont.TIMES,12,jxl.write.WritableFont.NO_BOLD,
    		false,jxl.format.UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK);
    
    //单元格样式
    jxl.write.WritableCellFormat wcf = new jxl.write.WritableCellFormat(wf);
    wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); 
    wcf.setAlignment(jxl.format.Alignment.CENTRE); 
    wcf.setBorder(Border.ALL,BorderLineStyle.THIN);
    wcf.setWrap(false);    
    //填充数据的内容有多少行
    
    //设置列头名
    int lie = 0,lie2 = 0,lie3=0;
    if(fieldExcel.indexOf("1,")>=0){
        lie = 1;
        ws.addCell(new jxl.write.Label(0, 0, "标题", wcf));
    }
    if (listFields != null && listFields.length > 0) {
        for (int i = 0; i < listFields.length; i++) {
          ws.addCell(new jxl.write.Label(i+lie, 0, listFields[i][1], wcf));
        }       
    }
    if(fieldExcel.indexOf("2,")>=0){
        lie2 = 1;
        ws.addCell(new jxl.write.Label(listFields.length-1+lie+lie2, 0, "提交时间", wcf));
    }
    if(fieldExcel.indexOf("4,")>=0){
        lie3 = 1;
        ws.addCell(new jxl.write.Label(listFields.length-1+lie+lie2+lie3, 0, "办结时间", wcf));
    }
    if(fieldExcel.indexOf("3,")>=0){
        ws.addCell(new jxl.write.Label(listFields.length-1+lie+lie2+lie3+1, 0, "办理状态", wcf));
    }
    
    //设置内容
    //wcf = new jxl.write.WritableCellFormat();
   
    if(datas!=null){
        CustomFormBD formBd = new CustomFormBD();
        String temp = "";
        for (int i = 0; i < datas.length; i++) {
            //datas  第一列为ID，错一位读取
            String[] obj = datas[i];
            if(fieldExcel.indexOf("1,")>=0){
                ws.addCell(new jxl.write.Label(0, i+1, obj[obj.length-2], wcf));
            }
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
                }else if(listFields[k][4].equals("501")){
					temp=getRelationFlow(tableId,obj[obj.length-3].toString(),listFields[k][2]);
				}else if(listFields[k][4].equals("401")){
					temp = formBd.getComment(listFields[k][2],obj[obj.length-3]);
				}else if(obj[k]==null || obj[k].toString().length()<1 || "115,".indexOf(listFields[k][4])>=0){
                    /*处理原始工作流中附件上传类型 */
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
                /*处理原始工作流中附件上传类型 */
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
                    temp = formBd.getFieldShowValue(listFields[k][2],listFields[k][4],obj[k].toString(),listFields[k][0]);
                }
                ws.addCell(new jxl.write.Label(k+lie, i+1, temp, wcf));
            }
            if(fieldExcel.indexOf("2,")>=0){
                String submitTime=obj[obj.length-8].toString();
                if(submitTime.indexOf(":")>0){
                    submitTime=submitTime.substring(0,submitTime.lastIndexOf(":"));
                }
                ws.addCell(new jxl.write.Label(listFields.length-1+lie+lie2,i+1, submitTime, wcf));
            }
            if(fieldExcel.indexOf("4,")>=0){
		        String submitTime=obj[obj.length-10].toString();
                if(submitTime.indexOf(":")>0){
                    submitTime=submitTime.substring(0,submitTime.lastIndexOf(":"));
                }
		        ws.addCell(new jxl.write.Label(listFields.length-1+lie+lie2+lie3,i+1, submitTime, wcf));
		    }
            if(fieldExcel.indexOf("3,")>=0){
                String zhuangtai = "";
                if("0".equals(request.getAttribute("queryType"))){
                    zhuangtai = formBd.getCurStep(obj[obj.length-1].toString());
                    zhuangtai = zhuangtai.replace("<label style=color:red>", "").replace("</label>","");
                }else{
                    if("-1".equals(obj[obj.length-7].toString())){
                        zhuangtai = "退回";
                    }else if("-2".equals(obj[obj.length-7].toString())){
                        zhuangtai = "取消";
                    }else{
                        zhuangtai = "办理完毕";
                    }
                }
                ws.addCell(new jxl.write.Label(listFields.length-1+lie+lie2+lie3+1,i+1, zhuangtai, wcf));
            }
        }
    }
    wwb.write();
    wwb.close();
}catch(Exception ex){
	ex.printStackTrace();
    response.setContentType("text/html; charset=GBK");

      out.write("\r\n<html>\r\n<head>\r\n<title></title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n    alert(\"File Not Found!\");\r\n    history.back();\r\n</SCRIPT>\r\n</head>\r\n<body>\r\n</body>\r\n</html>\r\n");
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