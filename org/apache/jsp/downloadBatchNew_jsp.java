/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:38:36 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.jsupload.upload.SmartUpload;
import com.js.util.util.DESFileUtil;

public final class downloadBatchNew_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("com.jsupload.upload.SmartUpload");
    _jspx_imports_classes.add("com.js.util.util.DESFileUtil");
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
      response.setContentType("text/html; charset=GBK");
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
	String _queryString=session.getAttribute("urlData")==null?"":session.getAttribute("urlData").toString();
	if(_queryString==null || "".equals(_queryString) || "null".equals(_queryString)){
		_queryString=request.getParameter("downloadFiles");
	}
	session.removeAttribute("urlData");
		
	String[] downloadFiles = _queryString.split(",");
	String[] downloadFileName = new String[downloadFiles.length];
	String[] downloadName = new String[downloadFiles.length];
	String Filepath= "";
	for(int k=0;k<downloadFiles.length;k++){
		String decodeStr = "&"+com.js.util.util.BASE64.BASE64DecoderNoBR(downloadFiles[k]);
		System.out.println(decodeStr);
		int numNo = 0;
		String tem;
		String tempFileName = "",tempName="";
		numNo=decodeStr.indexOf("&FileName");
		if(numNo>=0){
			tem=decodeStr.substring(numNo+10);
			if(tem.indexOf("&")>=0){
				tempFileName=tem.substring(0,tem.indexOf("&"));
			}else{
				tempFileName=tem;
			}
		}
		downloadFileName[k] = tempFileName;
		
		numNo=decodeStr.indexOf("&name");
		if(numNo>=0){
			tem=decodeStr.substring(numNo+6);
			if(tem.indexOf("&")>=0){
				tempName=tem.substring(0,tem.indexOf("&"));
			}else{
				tempName=tem;
			}
		}
		
		downloadName[k] = tempName;
		
		numNo=decodeStr.indexOf("&path");
		if(numNo>=0){
			tem=decodeStr.substring(numNo+6);
			if(tem.indexOf("&")>=0){
				Filepath=tem.substring(0,tem.indexOf("&"));
			}else{
				Filepath=tem;
			}
		}
	}
	
	String queryString=	"&FileName="+java.util.Arrays.toString(downloadFileName)+"&name="+java.util.Arrays.toString(downloadName)+"&path="+Filepath;
	String temp;
	int index=0;
	String informationId="",path="",FileName="",name="",moduleCode="",FileNametemp="";
	String[] fileNames=new String[10],names=new String[10],paths=new String[10];
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
		if(FileName.substring(0,1).equals("[")&&FileName.substring(FileName.length()-1).equals("]")){
			FileName=FileName.substring(1,FileName.length()-1);
		}
		fileNames=FileName.split(",");
		if(fileNames.length>0){
			paths=new String[fileNames.length];
			for(int s=0;s<fileNames.length;s++){
				fileNames[s]=fileNames[s].trim();
				if(fileNames[s].substring(4,5).equals("_")){
					paths[s]=fileNames[s].substring(0,4)+"/"+path;
				}else{
					if(path.indexOf("govdocumentmanager")>=0){
						paths[s]=path;
					}else{
						paths[s]="0000/"+path;
					}
						
				}
			}
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
		if(name.substring(0,1).equals("[")&&name.substring(name.length()-1).equals("]")){
			name=name.substring(1,name.length()-1);
		}
		names=name.split(",");
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
  String filepath="";
  HttpServletRequest HSR=(HttpServletRequest)pageContext.getRequest();
  
  if(path.indexOf("./")>=0 || path.indexOf(".\\")>=0 || FileName.indexOf("./")>=0 || FileName.indexOf(".\\")>=0){
		System.out.println("不允许下載upload以外的目录文件！");
		throw new Exception("不允许下載upload以外的目录文件！");
  }
  
  
  //判断是否使用了文件服务器 直接从文件服务器读取文件 2014-09-14 注释 ding
  if(com.js.util.config.SystemCommon.getUseClusterServer()==1){
	  filepath=com.js.util.config.SystemCommon.getClusterServerPath()+"/upload/";
  }else{	  
  	  filepath=HSR.getRealPath("/upload/")+"/";
  }
  	  
  filepath = filepath.replaceAll("\\\\", "/");
  
  if("".equals(moduleCode) || moduleCode ==null){
	  if(filepath.contains("cooperate")){
		  moduleCode="co_attach_waitsend";
	  }
	  if(filepath.contains("workflow")){
		  moduleCode="oa_workflow_waitsend";
	  }
	  if(filepath.contains("customform")){
		  moduleCode="oa_workflow_complete";
	  }
	  if(filepath.contains("archives")){
		  moduleCode="oa_archives_fujian";
	  }
  }
  
  
  String zipName = com.js.util.util.BatchDownloadTool.zipFile(paths,fileNames,filepath,names);
  
  String nameShow=names[0].substring(0,names[0].lastIndexOf("."));
  if(nameShow.indexOf(".")>-1){
	  nameShow=nameShow.substring(nameShow.indexOf(".")+1);
  }
  nameShow=nameShow+"等"+names.length+"个文件.zip";
  name=new String(nameShow.getBytes("GBK"),"iso-8859-1");  
  java.io.File file = new java.io.File(filepath +paths[0]+java.io.File.separator+zipName);
  if(file.exists()){
	  com.js.util.util.DownloadLog downloadLog =new com.js.util.util.DownloadLog();
	  downloadLog.getDownLoadLog(HSR, nameShow, moduleCode);
	 
	
	  response.setHeader("Content-Disposition","attachment; filename=\"" + name + "\"");

	  // 打开指定文件的流信息
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
	  file.delete();
	}else{
  response.setContentType("text/html; charset=GBK");
	  
      out.write("\r\n\t  <html>\r\n      <head>\r\n      <title></title>\r\n      <meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n\t  <SCRIPT LANGUAGE=\"JavaScript\">\r\n\t\talert(\"文件已丢失!\");\r\n\t\twindow.close();\r\n\t    //history.back();\r\n\t  </SCRIPT>\r\n\t  </head>\r\n\t  <body>\r\n\t  </body>\r\n\t  </html>\r\n\t  ");

  }
  //}//end of 直接下载  2014-09-14 注释 ding
}catch(Exception ex){

ex.printStackTrace();
	response.setContentType("text/html; charset=GBK");
	  
      out.write("\r\n\t  <html>\r\n    <head>\r\n    <title></title>\r\n    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n\t  <SCRIPT LANGUAGE=\"JavaScript\">\r\n\t\talert(\"文件已丢失!\");\r\n\t\twindow.close();\r\n\t    //history.back();\r\n\t  </SCRIPT>\r\n\t  </head>\r\n\t  <body>\r\n\t  </body>\r\n\t  </html>\r\n\t  ");

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