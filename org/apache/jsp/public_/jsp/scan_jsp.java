/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:57:50 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.public_.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.jsupload.upload.*;
import com.js.util.config.SysConfig;
import java.util.Map;

public final class scan_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("com.jsupload.upload");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.util.config.SysConfig");
    _jspx_imports_classes.add("java.util.Map");
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
      response.setContentType("text/html;charset=GBK");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\r\n<meta name=\"GENERATOR\" content=\"Microsoft FrontPage 4.0\">\r\n<meta name=\"ProgId\" content=\"FrontPage.Editor.Document\">\r\n<title>New Page 1</title>\r\n</head>\r\n<body>\r\n<p>\r\n<object classid=\"clsid:BC47B00B-BBA8-41ED-86F5-8674F18DF53D\" id=\"scanpic1\" width=\"322\" height=\"72\" codebase=\"http://");
      out.print(request.getServerName());
      out.write(':');
      out.print(request.getServerPort());
      out.write("/jsoa/public/jsp/PSCAN.ocx\">\r\n  <param name=\"Visible\" value=\"0\">\r\n  <param name=\"AutoScroll\" value=\"0\">\r\n  <param name=\"AutoSize\" value=\"0\">\r\n  <param name=\"AxBorderStyle\" value=\"1\">\r\n  <param name=\"Caption\" value=\"scanpic\">\r\n  <param name=\"Color\" value=\"2147483663\">\r\n  <param name=\"Font\" value=\"??????\">\r\n  <param name=\"KeyPreview\" value=\"0\">\r\n  <param name=\"PixelsPerInch\" value=\"96\">\r\n  <param name=\"PrintScale\" value=\"1\">\r\n  <param name=\"Scaled\" value=\"0\">\r\n  <param name=\"DropTarget\" value=\"0\">\r\n  <param name=\"HelpFile\" value>\r\n  <param name=\"DoubleBuffered\" value=\"0\">\r\n  <param name=\"Enabled\" value=\"-1\">\r\n  <param name=\"Cursor\" value=\"0\">\r\n  <param name=\"HelpType\" value=\"1\">\r\n  <param name=\"HelpKeyword\" value>\r\n  <param name=\"fileUrl\" value=\"http://");
      out.print(request.getServerName());
      out.write(':');
      out.print(request.getServerPort());
      out.write("/jsoa/public/jsp\">\r\n</object>\r\n&nbsp;<input name=\"ddd\" type=\"button\" value=\"??????\" onclick=\"getFilename();\">\r\n");

Map map = SysConfig.getInstance().getFtpServer();
String mode = (String) request.getParameter("mode");
String path = (String) request.getParameter("path");
String tableName = (String) request.getParameter("tableName");
String fileNames = (String) request.getParameter("fileName");
String saveNames = (String) request.getParameter("saveName");
String fileType = (String) request.getParameter("fileType");

String fileCountLimit="0";
String fileSizeLimit="0";
String fileSizeLimitAll="110240000";

int fileNum;
int fileMaxSize;
if(request.getParameter("fileMaxNum")!=null && !"null".equals(request.getParameter("fileMaxNum"))){
	fileNum=Integer.parseInt(request.getParameter("fileMaxNum"));
}else{
	fileNum=0;
}

if(request.getParameter("fileMaxSize")!=null && !"null".equals(request.getParameter("fileMaxSize"))){
	fileMaxSize=Integer.parseInt(request.getParameter("fileMaxSize"));
}else{
	fileMaxSize=0;
}

if(fileNum>0){
	fileCountLimit="1"+fileNum;
}
if(fileMaxSize>0){
	fileSizeLimitAll="1"+fileMaxSize;
}

String saveName = "";
String fileName = "";
String fileSuffix = "";

if(fileType != null && !fileType.equals("")){
	if(fileType.indexOf(",") >= 0){
		fileType = fileType.replace(',', '|');
	}
	fileType = "1" + fileType;
}else{
	fileType = "2exe|jsp|asp|cmd|ocx|com|dll";
}

      out.write("\r\n<TABLE width=\"80%\" align=\"center\" border=\"0\" style=\"display:none\">\r\n\t<CENTER>\r\n\t<TR>\r\n\t\t<TD align=\"center\"><object\r\n\t\t\tclassid=\"clsid:89AF7F33-300E-4AFB-8DEA-D375FCE398B4\"\r\n\t\t\tid=\"ActiveFormX1\" width=\"407\" height=\"52\" codebase=\"P.cab#version=1,0,30,0\">\r\n\t\t\t<param name=\"filecountlimit\" value=\"");
      out.print(fileCountLimit);
      out.write("\">\r\n\t\t\t<param name=\"filesizelimit\" value=\"");
      out.print(fileSizeLimit);
      out.write("\">\r\n\t\t\t<param name=\"filesizelimitall\" value=\"");
      out.print(fileSizeLimitAll);
      out.write("\">\r\n\t\t\t<param name=\"filterext\" value=\"");
      out.print(fileType);
      out.write("\">\r\n\t\t\t<param name=\"Visible\" value=\"0\">\r\n\t\t\t<param name=\"AutoScroll\" value=\"0\">\r\n\t\t\t<param name=\"AutoSize\" value=\"0\">\r\n\t\t\t<param name=\"AxBorderStyle\" value=\"0\">\r\n\t\t\t<param name=\"Caption\" value=\"ActiveFormX\">\r\n\t\t\t<param name=\"Color\" value=\"15592680\">\r\n\t\t\t<param name=\"Font\" value=\"??????\">\r\n\t\t\t<param name=\"KeyPreview\" value=\"0\">\r\n\t\t\t<param name=\"PixelsPerInch\" value=\"96\">\r\n\t\t\t<param name=\"PrintScale\" value=\"1\">\r\n\t\t\t<param name=\"Scaled\" value=\"0\">\r\n\t\t\t<param name=\"DropTarget\" value=\"0\">\r\n\t\t\t<param name=\"HelpFile\" value>\r\n\t\t\t<param name=\"DoubleBuffered\" value=\"0\">\r\n\t\t\t<param name=\"Enabled\" value=\"-1\">\r\n\t\t\t<param name=\"Cursor\" value=\"0\">\r\n\t\t\t<param name=\"ftphost\" value=\"");
      out.print(map.get("server"));
      out.write("\">\r\n\t\t\t<param name=\"ftpport\" value=\"");
      out.print(map.get("port"));
      out.write("\">\r\n\t\t\t<param name=\"ftpuser\" value=\"");
      out.print(map.get("user"));
      out.write("\">\r\n\t\t\t<param name=\"ftppwd\" value=\"");
      out.print(map.get("password"));
      out.write("\">\r\n\t\t\t<param name=\"ftpfile\" value=\"\">\r\n\t\t\t<param name=\"ftpupdfile\" value=\"\">\r\n\t\t\t<param name=\"dddd\" value=\"");
      out.print(map.get("ddd"));
      out.write("\">\r\n\t\t\t<param name=\"curpath\" value=\"");
      out.print(path);
      out.write("\">\r\n\t\t</object></TD>\r\n\t</TR>\r\n\t<tr>\r\n\t\t<td><input type=\"hidden\" name=\"allFileLimitedSize\" value=\"0\">\r\n\t\t</td>\r\n\t</tr>\r\n\t<TR>\r\n\t\t<TD align=\"center\"><INPUT NAME=\"button\" VALUE=\"??????\"\r\n\t\t\tonclick=\"javascript:upload()\" TYPE=\"button\"> &nbsp;&nbsp;<INPUT\r\n\t\t\tNAME=\"reset\" VALUE=\"??????\" TYPE=\"reset\"></TD>\r\n\t</TR>\r\n\t<tr>\r\n\t\t<td>&nbsp;</td>\r\n\t</tr>\r\n\t<tr>\r\n\t\t<td align=\"center\"><input type=\"hidden\" name=\"fileRealName\" value=\"\">\r\n\t\t<input type=\"hidden\" name=\"fileSaveName\" value=\"\"> <!--<img src=\"/jsoa/images/boder_left.gif\" hspace=\"5\" vspace=\"5\" id=\"preview\" >-->\r\n\t\t</td>\r\n\t</tr>\r\n\t</CENTER>\r\n</TABLE>\r\n</BODY>\r\n\r\n</body>\r\n</html>\r\n<script>\r\nfunction getFilename(){\r\n\tActiveFormX1.setlocalfile(scanpic1.getFile());\r\n\tupload();\r\n}\r\n</script>\r\n\r\n<script language=\"javascript\">\r\nfunction upload(){\r\n\tvar fileSizeLimitAll=\"");
      out.print(fileSizeLimitAll);
      out.write("\";\r\n\tfileSizeLimitAll=fileSizeLimitAll.substring(1);\r\n\tvar oriSize=opener.window.document.all.allAttachSize.value;\r\n\t//if(\"0\"==oriSize){\r\n\t//\tdocument.all.allFileLimitedSize.value=fileSizeLimitAll;\r\n\t//}else{\r\n\t//\tdocument.all.allFileLimitedSize.value=(fileSizeLimitAll-oriSize);\r\n\t//}\r\n\r\n\r\n\t//???????????????????????????????????????????????????????????????\r\n\tvar remainSpace=document.all.allFileLimitedSize.value;\r\n\r\n\t//???????????????????????????\r\n\tvar currentSize=0;\r\n\tvar thisAttachSizeTmp=document.ActiveFormX1.getfilesize();\r\n\tvar thisAttachArr;\r\n\tif(thisAttachSizeTmp.indexOf(\"|\")>=0){\r\n\t\tthisAttachArr=thisAttachSizeTmp.split(\"|\");\r\n\t\tfor(var i=0;i<thisAttachArr.length;i++){\r\n\t\t\tcurrentSize=(thisAttachArr[i]*1)+currentSize;\r\n\t\t}\r\n\t}else{\r\n\t\tcurrentSize=thisAttachSizeTmp;\r\n\t}\r\n\r\n\tif((remainSpace-currentSize)<0){\r\n\t\talert(\"??????????????????????????????,???????????????????????????!\");\r\n\t\twindow.close();\r\n\t\treturn;\r\n\t}\r\n    var tmpReturn = document.ActiveFormX1.startupload();\r\n\t//alert(tmpReturn);\r\n\r\n\t/*\r\n\t    tmpReturn???????????????\r\n\t        LINKFAIL    ???????????????\r\n\t\t\tNOFILE      :?????????????????????\r\n\t\t\tOK          :????????????\r\n\t*/\r\n\tif(tmpReturn==\"OK\"){\r\n");
      out.write("\t\twriteParentPage();\r\n\t\tgetReset();\r\n\t\twindow.close();\r\n\t}else{\r\n\t\talert(\"????????????\");\r\n\t}\r\n}\r\n//??????\r\nfunction getReset(){\r\n    document.ActiveFormX1.setreset();\r\n}\r\n\r\n//??????????????????\r\nfunction getOldfile(){\r\n    document.all.fileRealName.value=document.ActiveFormX1.getoldfile();\r\n}\r\n\r\n//?????????????????????\r\nfunction getNewfile(){\r\n\tdocument.all.fileSaveName.value=document.ActiveFormX1.getnewfile();\r\n}\r\n\r\n//??????????????????\r\nfunction getFileSize(){\r\n    alert(document.ActiveFormX1.getfilesize());\r\n}\r\n\r\n//????????????\r\nfunction delFile(){\r\n\r\n\tvar tmpReturn=document.ActiveFormX1.delfile(document.all.delfileinput.value);\r\n    alert(tmpReturn);\r\n    /*\r\n\t    tmpReturn???????????????\r\n\t        LINKFAIL    ???????????????\r\n\t\t\tNOFILE      :?????????????????????\r\n\t\t\tFAIL:424234234.gif|4565657.jpg     :??????424234234.gif,4565657.jpg????????????????????????\r\n\t\t\tOK          :????????????\r\n\t*/\r\n}\r\n\r\nfunction writeParentPage(){\r\n\tvar saveNameTmp=document.ActiveFormX1.getnewfile();\r\n\tvar fileNameTmp=document.ActiveFormX1.getoldfile();\r\n    //??????????????????\r\n\tvar allFileMaxSize=document.all.allFileLimitedSize.value;\r\n\r\n\t//??????????????????????????????\r\n");
      out.write("\tvar oriAttachSize=opener.window.document.all.allAttachSize.value;\r\n\r\n\t//??????????????????????????????\r\n\tvar thisAttachSize=0;\r\n\tvar thisAttachSizeTmp=document.ActiveFormX1.getfilesize();\r\n\tvar thisAttachArr;\r\n\tif(thisAttachSizeTmp.indexOf(\"|\")>=0){\r\n\t\tthisAttachArr=thisAttachSizeTmp.split(\"|\");\r\n\t\tfor(var i=0;i<thisAttachArr.length;i++){\r\n\t\t\tthisAttachSize=(thisAttachArr[i]*1)+thisAttachSize;\r\n\t\t}\r\n\t}else{\r\n\t\tthisAttachSize=thisAttachSizeTmp;\r\n\t}\r\n\r\n\toriAttachSize=(oriAttachSize*1)+(thisAttachSize*1);\r\n\topener.window.document.all.allAttachSize.value=oriAttachSize;\r\n\r\n\t//????????????????????????????????????\r\n\tallFileMaxSize=allFileMaxSize-oriAttachSize;\r\n\tdocument.all.allFileLimitedSize.value=allFileMaxSize;\r\n\r\n\r\n\r\n\tif(saveNameTmp.indexOf(\"|\")>=0){\r\n\t\tvar saveNameArr=saveNameTmp.split(\"|\");\r\n\t\tvar fileNameArr=fileNameTmp.split(\"|\");\r\n\t\tfor(var i=0;i<saveNameArr.length;i++){\r\n\r\n\t\t\tinsertTable(saveNameArr[i],fileNameArr[i],thisAttachArr[i]);\r\n\t\t}\r\n\t}else{\r\n\t\tinsertTable(saveNameTmp,fileNameTmp,thisAttachSize);\r\n\t}\r\n\r\n\r\n\talert(\"??????????????????\");\r\n}\r\n\r\nfunction insertTable(saveName,fileName,fileSize){\r\n");
      out.write("\tvar fileSuffix;\r\n\tvar fileNameTemp;\r\n\tvar tooLong=false;\r\n\tfileName=fileName.substring(fileName.lastIndexOf(\"\\\\\")+1);\r\n\tif(fileName.length>50){\r\n\t\tfileName=fileName.substring(0,50);\r\n\t\ttooLong=true;\r\n\t}\r\n\tif(fileName!=null) fileSuffix=fileName.substring(fileName.lastIndexOf(\".\")+1);\r\n\r\n\t//??????????????????table???????????????\r\n\tvar path=\"");
      out.print(path);
      out.write("\";\r\n\tvar parentTable=\"");
      out.print(tableName);
      out.write("\";\r\n\tvar fileNames=\"");
      out.print(fileNames);
      out.write("\";\r\n\tvar saveNames=\"");
      out.print(saveNames);
      out.write("\";\r\n\tvar fileNum=\"");
      out.print(fileNum);
      out.write("\";\r\n\tvar fileNameTemp=fileName;\r\n\r\n\tvar obj=eval(\"opener.window.document.all.\"+parentTable);\r\n\r\n\tobj.insertRow();\r\n\tvar rowNum=obj.rows.length-1;\r\n\tvar newNode=obj.rows(rowNum);\r\n\tnewNode.bgColor=\"#FFFFFF\";\r\n\tnewNode.id=\"newInsertedTrid\";\r\n\tfor(var i=0;i<2;i++){\r\n\t\tnewNode.insertCell();\r\n\t}\r\n\r\n\tif(tooLong==true){\r\n\t\tfileNameTemp+=\"~\"+rowNum+\".\"+fileSuffix;\r\n\t}\r\n\tvar innerTextTmp=\"<a href='/jsoa/download.jsp?FileName=\"+saveName+\"&name=\"+fileName+\"&path=\"+path+\"' title=\\\"??????\\\">\"+fileNameTemp+\"</a>\";\r\n\tobj.rows(rowNum).cells(0).innerHTML=innerTextTmp;\r\n\tobj.rows(rowNum).cells(0).id=\"A\"+rowNum;\r\n\tvar innerValue=\"&nbsp;<a href='/jsoa/download.jsp?FileName=\"+saveName+\"&name=\"+fileName+\"&path=\"+path+\"' title=\\\"??????\\\">??????</a>\";\r\n\r\n\tinnerValue=innerValue+\"&nbsp;&nbsp;&nbsp;<a href='javascript:delaccessory(\\\"\"+rowNum+\"\\\",\\\"\"+parentTable+\"\\\",\\\"\"+path+\"\\\",\\\"\"+fileNames+\"\\\",\\\"\"+saveNames+\"\\\");'>\"\r\n\t+\"??????</a>\"\r\n\t+\"<input type=\\\"hidden\\\" id=\"+saveNames+\" name=\"+saveNames+\" value='\"+saveName+\"'><input type=\\\"hidden\\\" id=\\\"\"+fileNames+\"\\\" name=\\\"\"+fileNames+\"\\\" value='\"+fileNameTemp+\"'><input type=\\\"hidden\\\" id=\\\"\"+saveNames+\"size\\\" name=\\\"\"+saveNames+\"size\\\" value='\"+fileSize+\"'>\"\r\n");
      out.write("\t+\"<input type=\\\"hidden\\\" id=saveTempNames_0 name=saveTempNames_0 vale='\"+saveName+\"'>\";\r\n\r\n\tif(fileSuffix==\"jpg\" || fileSuffix==\"jpeg\" || fileSuffix==\"gif\" || fileSuffix==\"bmp\"){\r\n\t\tinnerValue+=\"\";\r\n\t}\r\n\tobj.rows(rowNum).cells(1).innerHTML=innerValue;\r\n\tobj.rows(rowNum).cells(1).height=\"20\";\r\n\r\n\t//?????????????????????\r\n\tvar objTemp=opener.window.document.all.saveFileNameTemp_0_1;\r\n\tobjTemp.value=objTemp.value+\",\"+saveName;\r\n\r\n\tif(fileNum!=\"0\"){\r\n\t\tvar len=0;\r\n\t\tobj=eval(\"opener.window.document.all.\"+saveNames);\r\n\t\tif(obj.length){\r\n\t\t\tlen=obj.length;\r\n\t\t}else{\r\n\t\t\tlen=1;\r\n\t\t}\r\n\t\tif(len==fileNum){\r\n\t\t\twindow.close();\r\n\t\t}\r\n\t}\r\n}\r\n\r\n    //-->\r\n</script>");
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
