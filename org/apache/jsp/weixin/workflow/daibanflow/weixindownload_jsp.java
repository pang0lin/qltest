/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:59:39 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.weixin.workflow.daibanflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class weixindownload_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes = null;
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
      response.setContentType("text/html; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\r');
      out.write('\n');


try{ 
	String _queryString=request.getQueryString();
	String queryString="&"+com.js.util.util.BASE64.BASE64DecoderNoBR(_queryString);
	String temp;
	java.util.Calendar cal = java.util.Calendar.getInstance();
	String year = String.valueOf(cal.get(java.util.Calendar.YEAR));//年
	int index=0;
	String informationId="",path="",FileName="",name="",moduleCode="";
	
	//查找informationId
	index=queryString.indexOf("&informationId");
	if(index>=0){
		temp=queryString.substring(index+15);
		if(temp.indexOf("&")>=0){
			informationId=temp.substring(0,temp.indexOf("&"));
		}else{
			informationId=temp;
		}
		
	}
	//查找path
	index=queryString.indexOf("&path");
	if(index>=0){
		temp=queryString.substring(index+6);
		if(temp.indexOf("&")>=0){
			path=temp.substring(0,temp.indexOf("&"));
		}else{
			path=temp;
		}
	}
	//查找FileName
	index=queryString.indexOf("&FileName");
	if(index>=0){
		temp=queryString.substring(index+10);
		if(temp.indexOf("&")>=0){
			FileName=temp.substring(0,temp.indexOf("&"));
		}else{
			FileName=temp;
		}
		if(!FileName.substring(4,5).equals("_"))
		{
		   if(path.indexOf("govdocumentmanager")>=0 || path.indexOf("information")>=0){
		   
		   }else{
		      path="0000/"+path;
		   }
			
		}else
		{
			path=FileName.substring(0,4)+"/"+path;
		}
		
	}
	//查找name
	index=queryString.indexOf("&name");
	if(index>=0){
		temp=queryString.substring(index+6);
		if(temp.indexOf("&")>=0){
			name=temp.substring(0,temp.indexOf("&"));
		}else{
			name=temp;
		}
		//System.out.println("name="+name);
	}
	//查找moduleCode
			index=queryString.indexOf("&moduleCode");
			if(index>=0){
				temp=queryString.substring(index+12);
				if(temp.indexOf("&")>=0){
					moduleCode=temp.substring(0,temp.indexOf("&"));
				}else{
					moduleCode=temp;
				}
			}
	//informationId,path,FileName,name;
	
  // 得到文件名字和路径
  //String informationId=request.getParameter("informationId");
  String filepath="";
  HttpServletRequest HSR=(HttpServletRequest)pageContext.getRequest();
  HttpSession session1=HSR.getSession(false);

  if(informationId!=null && !"null".equals(informationId) && !"".equals(informationId)){
	  try{
		  //记录知识管理文档阅读次数并记录查看人
		  com.js.oa.info.infomanager.service.InformationBD info=new com.js.oa.info.infomanager.service.InformationBD();
		  
		  String userId=session.getAttribute("userId").toString();
		  String userName=session.getAttribute("userName").toString();
		  String orgId=session.getAttribute("orgId").toString();
		  String orgName=session.getAttribute("orgName").toString();
		  String orgIdString=session.getAttribute("orgIdString").toString();
		  
		  info.recordReader(userId,userName,orgId,orgName,orgIdString,informationId);
	  }catch(Exception ex){
		  ex.printStackTrace();
	  }
  }
  
  //判断是否使用了文件服务器 2014-09-14 注释 ding
  /*if(com.js.util.config.SystemCommon.getUseClusterServer()==1){
	  response.sendRedirect(com.js.util.config.SystemCommon.getClusterServerUrl()+
			  "/download_f.jsp?"+_queryString);
  }else{*/
	  
	  //直接下载
	  //String path=request.getParameter("path");
	  while(path.indexOf("../")>=0){
		  path = path.replace("../","");
	  }
	  
	  //判断是否使用了文件服务器 直接从文件服务器读取文件 2014-09-14 注释 ding
	  if(com.js.util.config.SystemCommon.getUseClusterServer()==1){
		  filepath=com.js.util.config.SystemCommon.getClusterServerPath()+"/upload/"+path+"/";
	  }else{	  
	  	  filepath=HSR.getRealPath("/upload/")+"/"+path+"/";
	  }
	  
	  System.out.println("path:"+filepath);
	  
	  filepath = filepath.replaceAll("\\\\", "/");
	  
	  if("".equals(moduleCode) || moduleCode ==null)
	  {
		  if(filepath.contains("cooperate"))
		  {
			  moduleCode="co_attach_waitsend";
		  }
		  if(filepath.contains("workflow"))
		  {
			  moduleCode="oa_workflow_waitsend";
		  }
		  if(filepath.contains("customform"))
		  {
			  moduleCode="oa_workflow_complete";
		  }
		  if(filepath.contains("archives"))
		  {
			  moduleCode="oa_archives_fujian";
		  }
	  }
	  
		if(filepath.indexOf("webmail") != -1){
			String pathTemp = filepath.substring(filepath.indexOf("webmail"));
			String currentYear=String.valueOf(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR));
			filepath = application.getRealPath("/upload/" + currentYear + "/" + pathTemp) + "/";
			// System.out.println("filepath = " + filepath);
		}
	  
	  // String Filename = request.getParameter("FileName");
	  // String name = request.getParameter("name");
	  String nameShow=name;
	  name=new String(name.getBytes("GBK"),"iso-8859-1");  
	  System.out.println("filepath = " + filepath);
	  java.io.File file = new java.io.File(filepath + FileName);
	  if(file.exists()){
			  com.js.util.util.DownloadLog downloadLog =new com.js.util.util.DownloadLog();
			  downloadLog.getDownLoadLog(HSR, nameShow, moduleCode);
			  String prefix=FileName.substring(FileName.lastIndexOf(".")).toLowerCase();
			  if(com.js.util.config.SystemCommon.getFileShowUse()==0
					  ||(com.js.util.util.BrowserJudge.isFirefox(request)&&",.doc,.docx,.ppt,.pptx,.xls,.xlsx,".indexOf(","+prefix+",")>=0)
					  ||(com.js.util.util.BrowserJudge.isWebKit(request)&&",.doc,.docx,.ppt,.pptx,.xls,.xlsx,".indexOf(","+prefix+",")>=0) 
					  ||(com.js.util.util.BrowserJudge.isIE(request)&&",.ppt,.pptx,".indexOf(","+prefix+",")>=0)
					  ||(com.js.util.config.SystemCommon.getDefaultType().indexOf(","+prefix+",")<0
					  &&com.js.util.config.SystemCommon.getExtendType().indexOf(","+prefix+",")<0)){
				  //开关，判断word,excel,pdf是否在线打开
			  
				  // 设置响应头和下载保存的文件名
				  //response.setContentType("application/msword");
				  response.setContentType("csv");
			
				  response.setHeader("Content-Disposition","attachment; filename=\"" + name + "\"");
			
				  // 打开指定文件的流信息
				  /*java.io.FileInputStream fileInputStream = new java.io.FileInputStream(filepath + FileName);
			
				  // 写出流信息
				  int i;
				  while ((i=fileInputStream.read()) != -1) {
				   out.write(i);
				  }
				  fileInputStream.close();
				  out = pageContext.pushBody();
				  out.clear();
				  out.close();*/
				  
				  java.io.FileInputStream fis=new java.io.FileInputStream(file);
				  java.io.BufferedInputStream buff=new java.io.BufferedInputStream(fis);
				  
				  byte [] b=new byte[1024];//相当于我们的缓存
				  long k=0;//该值用于计算当前实际下载了多少字节
				  
				  //从response对象中得到输出流,准备下载
				  java.io.OutputStream myout=response.getOutputStream();
				  //开始循环下载
				  while(k<file.length()){
					  int j=buff.read(b,0,1024);
					  k+=j;
					  //将b中的数据写到客户端的内存
					  myout.write(b,0,j);
				  }
				  //将写入到客户端的内存的数据,刷新到磁盘
				  myout.flush();
				  buff.close();
				  fis.close();			  
				  myout.close();
				  out.clear();  
				  out = pageContext.pushBody();  
			  }else{
				  String filePath = filepath + FileName;
				  response.setContentType("text/html; charset=GBK");
				  
      out.write("\r\n\t\t\t\t  <html>\r\n\t\t\t      <head>\r\n\t\t\t      <title></title>\r\n\t\t\t      </head>\r\n\t\t\t      <body>\r\n\t\t\t\t  <script type=\"text/javascript\">\r\n\t\t\t\t  \t");
 String fileUrl = filePath.substring(filePath.indexOf("/jsoa/upload/"));
				  	if(",.doc,.docx,.ppt,.pptx,.xls,.xlsx,".indexOf(","+prefix+",")>=0){
				  		
      out.write("\r\n\t\t\t\t  \t\t\r\n\t\t\t\t  \t\t");
 if(com.js.util.util.BrowserJudge.isIE(request)){
				  			//System.out.println("---"+queryString);
				  			//System.out.println(fileUrl.substring(13)+"&"+nameShow);
				  			nameShow += "&"+informationId;
				  			if(queryString.contains("edit=1")){
				  				nameShow += "&edit=1";
				  			}
				  				//&&",.doc,.docx,.xls,.xlsx,pp".indexOf(","+prefix+",")>=0){
      out.write("\r\n\t\t\t\t  \t\twindow.open(\"");
      out.print("/jsoa/iWebOfficeSign/showView.jsp?"+com.js.util.util.BASE64.BASE64EncoderNoBR(fileUrl.substring(13)+"&"+nameShow) );
      out.write("\",\"newPage\",\"location=0\");\r\n\t\t\t\t  \t\t//window.open(\"");
      out.print("/jsoa/soaShow.jsp?"+com.js.util.util.BASE64.BASE64EncoderNoBR(fileUrl.substring(13)+"&"+nameShow) );
      out.write("\",\"newPage\",\"location=0\");\r\n\t\t\t\t  \t\t");
}else{
      out.write("\r\n\t\t\t\t  \t\twindow.open(\"");
      out.print("/jsoa/fileShow.jsp?"+com.js.util.util.BASE64.BASE64EncoderNoBR(fileUrl.substring(13)+"&"+nameShow) );
      out.write("\",\"newPage\",\"location=0\");\r\n\t\t\t\t  \t\t");
}
				  	}else{
				  		
      out.write("\r\n\t\t\t\t  \t\r\n\t\t\t\t  \t\twindow.open(\"");
      out.print("/jsoa/fileShow.jsp?"+com.js.util.util.BASE64.BASE64EncoderNoBR(fileUrl.substring(13)+"&"+nameShow) );
      out.write("\",\"newPage\",\"location=0\");\r\n\t\t\t\t  \t");
}
      out.write("\r\n\t\t\t\t  \twindow.close();\r\n\t\t\t\t  </script>\r\n\t\t\t\t  </body>\r\n\t\t\t\t  </html>\r\n\t\t\t\t  ");

			  }
	  }else{
		  response.setContentType("text/html; charset=GBK");
		  
      out.write("\r\n\t\t  <html>\r\n\t      <head>\r\n\t      <title></title>\r\n\t      <meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n\t\t  <SCRIPT LANGUAGE=\"JavaScript\">\r\n\t\t\talert(\"File Not Found!\");\r\n\t\t    history.back();\r\n\t\t  </SCRIPT>\r\n\t\t  </head>\r\n\t\t  <body>\r\n\t\t  </body>\r\n\t\t  </html>\r\n\t\t  ");

	  }
  //}//end of 直接下载  2014-09-14 注释 ding
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
