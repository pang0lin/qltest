/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:58:06 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.public_.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class pup_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html;charset=GBK");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n");

Map map = (Map) session.getAttribute("ftpMap");

String path = (String) request.getParameter("path")+"";
String fileNames = (String) request.getParameter("fileName") + "";
String saveNames = (String) request.getParameter("saveName") + "";

//是查看 还是管理  1是管理
String  canModify= request.getParameter("canModify")==null?"":request.getParameter("canModify").toString();
String  title="附件管理";
 if(saveNames.equals("accessorySaveName1")){
  title="正文管理";
 }

 if(saveNames.equals("contentAccSaveName")){
  title="正文管理";
 }

      out.write("\r\n\r\n\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\" />\r\n<title>");
      out.print(title);
      out.write("</title>\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script>\r\nfunction addFile(){\r\n  document.getElementById(\"uploadFrame\").style.display=\"inline\";\r\n}\r\n</script>\r\n</head>\r\n\r\n<body scroll=\"no\" onLoad=\"initFileManager();\">\r\n<table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" id=\"pup_main\">\r\n  <tr>\r\n    <td id=\"pup_top\" colspan=\"3\"><table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n      <tr>\r\n        <td><div id=\"pup_topright\">选中某个您要操作的文件，\r\n          然后选择操作按键来执行相应的操作。</div></td>\r\n        <td id=\"pup_topleft\">&nbsp;</td>\r\n      </tr>\r\n    </table></td>\r\n  </tr>\r\n  <tr>\r\n    <td valign=\"middle\" id=\"pup_toolbar\" colspan=\"3\">\r\n\t<a href=\"#\" onClick=\"saveClose();\"><img src=\"/jsoa/images/acc/accClose.gif\" border=\"0\" /></a>\r\n\t");
if(canModify.equals("1")){
      out.write("\r\n\t<a href=\"#\" onClick=\"editFile();\"><img src=\"/jsoa/images/acc/accModify.gif\" border=\"0\" /></a>\r\n\t");
}
      out.write("\r\n\t<a href=\"#\" onClick=\"seeFile();\"><img src=\"/jsoa/images/acc/accView.gif\" border=\"0\" /></a>\r\n     ");
if(canModify.equals("1")){
      out.write("\r\n\t<a href=\"#\" onClick=\"delFile();\"><img src=\"/jsoa/images/acc/accDelete.gif\" border=\"0\" /></a>\r\n\t<a href=\"#\" onClick=\"upload();\"><img src=\"/jsoa/images/acc/accAdd.gif\" border=\"0\" /></a>\t\r\n\t");
}
      out.write("\r\n\t\r\n\t</td>\r\n  </tr>\r\n  <tbody id=\"uploadFrame\" style=\"display:none;\">\r\n           \r\n            <tr>\r\n\t\t\t<td  nowrap width=\"92\">&nbsp;&nbsp;&nbsp;&nbsp;选择文件：</td>\r\n              <td height=\"22\" valign=\"bottom\" width=\"80%\">\r\n                <input type=\"file\" name=\"file\" class=\"inputText\" style=\"width:90%;\">\r\n              </td>\r\n              <td width=\"53\" valign=\"bottom\"><input name=\"Submit2\" type=\"button\" onClick=\"upload();\" class=\"btnButton2font\" value=\"上传\"></td>\r\n            </tr>\r\n\t\t\t</tbody>\r\n  <tr>\r\n    <td align=\"center\" valign=\"top\" colspan=\"3\">\r\n\t\t<div id=\"pup_content\">\r\n\t\t  <table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n            <tr>\r\n              <td colspan=\"2\" align=\"center\">\r\n\t\t\t  <OBJECT id=\"fileMana\"  CLASSID=\"CLSID:A51280DA-FEC3-489A-9A1D-2355E2A19FF6\"\r\n\t\t\t\tCODEBASE=\"FileManager.CAB#version=1,0,0,4\" width=\"520\" height=\"280\">\r\n\t\t\t\t</OBJECT>\r\n\t\t\t  </select>\r\n\t\t\t  </td>\r\n            </tr>\r\n\t\t\t\r\n          </table>\r\n\t\t</div>\r\n\t</td>\r\n  </tr>\r\n\r\n</table>\r\n</body>\r\n");
      out.write("</html>\r\n\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n<!--\r\n\r\nfunction initFileManager(){\r\n\ttry{\r\n        document.all.fileMana.fileType = \"TXT,DOC,EXCEL,PDF,GIF,BMP,JPG,PNG|*.txt;*.doc;*.xls;*.pdf;*.gif;*.bmp;*.jpg;*.png \";\r\n\t\tdocument.all.fileMana.fileMaxNum = \"20\";\r\n\t\tdocument.all.fileMana.hostIP = \"");
      out.print(map.get("server"));
      out.write("\";\r\n\t\tdocument.all.fileMana.user = \"");
      out.print(map.get("user"));
      out.write("\";\r\n\t\tdocument.all.fileMana.pwd = \"");
      out.print(map.get("password"));
      out.write("\";\r\n\t\tdocument.all.fileMana.hostPath = \"");
      out.print(path+"/");
      out.write("\";\r\n\t\tdocument.all.fileMana.dddd = \"");
      out.print(map.get("ddd"));
      out.write("\";\r\n\t\tdocument.all.fileMana.fileSaveName = opener.window.document.all.");
      out.print(saveNames);
      out.write(".value;\r\n\t\tdocument.all.fileMana.fileRealName = opener.window.document.all.");
      out.print(fileNames);
      out.write(".value;\r\n\t\tdocument.all.fileMana.initFile();\r\n\t}catch(ex){alert(\"控件安装失败！\");}\r\n}\r\n\r\nfunction upload(){\r\n\tdocument.all.fileMana.uploadFile();\r\n\topener.window.document.all.");
      out.print(saveNames);
      out.write(".value = document.all.fileMana.fileSaveName;\r\n\topener.window.document.all.");
      out.print(fileNames);
      out.write(".value = document.all.fileMana.fileRealName;\r\n}\r\n\r\nfunction downFile(){\r\n\tdocument.all.fileMana.downloadFile();\r\n}\r\n\r\nfunction editFile(){\r\n\tdocument.all.fileMana.editFile();\r\n}\r\n\r\nfunction delFile(){\r\n\tdocument.all.fileMana.deleteFile();\r\n\topener.window.document.all.");
      out.print(saveNames);
      out.write(".value = document.all.fileMana.fileSaveName;\r\n\topener.window.document.all.");
      out.print(fileNames);
      out.write(".value = document.all.fileMana.fileRealName;\r\n}\r\n\r\nfunction seeFile(){\r\n\tdocument.all.fileMana.openFile();\r\n}\r\nfunction saveClose(){\r\n\tif(document.all.fileMana.bDone){\r\n\r\n        var sName=document.all.fileMana.fileSaveName;\r\n\t\tvar rName=document.all.fileMana.fileRealName;\r\n \r\n\t\tif(sName!=\"\"){\r\n\t      sName=sName.replaceAll(\"\\\\|\\\\|\",\"\\|\");\r\n\t\t  rName=rName.replaceAll(\"\\\\|\\\\|\",\"\\|\");\r\n\t\t\r\n\t\t}\r\n       \r\n\t\topener.window.document.all.");
      out.print(saveNames);
      out.write(".value = sName;\r\n\t\topener.window.document.all.");
      out.print(fileNames);
      out.write(".value = rName;\r\n\t\tgetAccContent();\r\n\t\twindow.close();\r\n\t}else{\r\n\t\talert(\"文件正在上传，不能关闭窗口！\");\r\n\t}\r\n}\r\n\r\nwindow.onbeforeunload()\r\n{    \r\n\t//return false;\r\n\r\n\t    var sName=document.all.fileMana.fileSaveName;\r\n\t\tvar rName=document.all.fileMana.fileRealName;\r\n \r\n\t\tif(sName!=\"\"){\r\n\t      sName=sName.replaceAll(\"\\\\|\\\\|\",\"\\|\");\r\n\t\t  rName=rName.replaceAll(\"\\\\|\\\\|\",\"\\|\");\r\n\t\t\r\n\t\t}\r\n\topener.window.document.all.");
      out.print(saveNames);
      out.write(".value = sName;\r\n\topener.window.document.all.");
      out.print(fileNames);
      out.write(".value =rName;\r\n\tgetAccContent();\r\n}\r\n\r\n//显示附件说明，附件数量\r\nfunction  getAccContent(){\r\n\r\n   if(\"");
      out.print(saveNames);
      out.write("\"!=\"contentAccSaveName\"){\r\n   if(opener.window.document.all.sendFileAccessoryDesc){\r\n      \r\n\tif(document.all.fileMana.fileSaveName!=\"\"){\r\n\r\n\t\tvar rName=document.all.fileMana.fileRealName;\r\n \r\n\t\tif(rName!=\"\"){\r\n\t\t  rName=rName.replaceAll(\"\\\\|\\\\|\",\"\\|\");\t\t\r\n\t\t}\r\n\t var fileName=rName;\r\n     var  realFileArray = (fileName + \"\").split(\"\\|\");\r\n     var amountLength=realFileArray.length;\r\n\t var accessDesc=\"\";\r\n\t  for(var i=0;i<amountLength;i++){\r\n\t   accessDesc+=realFileArray[i]+\";\";\t   \r\n\t  }\r\n\t  if(accessDesc.length>1){\r\n\t   accessDesc=accessDesc.substring(0,accessDesc.length-1);\r\n\t  }\r\n    // opener.window.document.all.zjkyAmount.value=amountLength;\r\n\t opener.window.document.all.sendFileAccessoryDesc.value=accessDesc;\t\r\n\t \r\n\t }else{\r\n\t // opener.window.document.all.zjkyAmount.value=\"0\";\r\n\t opener.window.document.all.sendFileAccessoryDesc.value=\"\";\t\r\n\t \r\n\t } \r\n\t\r\n   }\r\n   }   \r\n\t\r\n}\r\n\r\nString.prototype.replaceAll  = function(s1,s2){    \r\nreturn this.replace(new RegExp(s1,\"gm\"),s2);    \r\n}   \r\n//-->\r\n</SCRIPT>\r\n");
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