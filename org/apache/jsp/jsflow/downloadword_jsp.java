/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:08:03 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class downloadword_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html; charset=GBK");
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

	request.setCharacterEncoding("GBK");

      out.write("\r\n<html>\r\n\t<body>\r\n\t\t<div id=\"newForm\">\r\n\t</div>\r\n\t\t");
//=request.getParameter("content")
      out.write("\r\n\t</body>\r\n</html>\r\n<script language=\"javascript\">\r\n  document.all.newForm.innerHTML = opener.document.all.formTable.innerHTML;\r\n\r\n\r\n    for(var i = 0; i < document.getElementsByTagName(\"input\").length; i ++){\r\n        if(document.getElementsByTagName(\"input\")[i].type == \"hidden\"){\r\n\r\n            document.getElementsByTagName(\"input\")[i].removeNode(true);\r\n        }\r\n    }\r\n    if(document.all.editId){\r\n        document.all.editId.removeNode(true);\r\n    }\r\n    if(document.all.done){\r\n        document.all.done.removeNode(true);\r\n    }\r\n    if(document.all.noPostilDate){\r\n        document.all.noPostilDate.removeNode(true);\r\n    }\r\n    if(document.all.noPostilMin){\r\n        document.all.noPostilMin.removeNode(true);\r\n    }\r\n    if(document.all.createdDate1){\r\n        document.all.createdDate1.removeNode(true);\r\n    }\r\n    if(document.all.saveFileNameTemp_0_1){\r\n        if(document.all.saveFileNameTemp_0_1.length){\r\n            for(var j = 0 ; j < document.all.saveFileNameTemp_0_1.length; j ++){\r\n                document.all.saveFileNameTemp_0_1[j].removeNode(true);\r\n");
      out.write("            }\r\n        }else{\r\n            document.all.saveFileNameTemp_0_1.removeNode(true);\r\n        }\r\n    }\r\n    if(document.all.allAttachSize){\r\n        if(document.all.allAttachSize.length){\r\n            for(var j = 0 ; j < document.all.allAttachSize.length; j ++){\r\n                document.all.allAttachSize[j].removeNode(true);\r\n            }\r\n        }else{\r\n            document.all.allAttachSize.removeNode(true);\r\n        }\r\n    }\r\n    if(document.all.deletedFileNames){\r\n        if(document.all.deletedFileNames.length){\r\n            for(var j = 0 ; j < document.all.deletedFileNames.length; j ++){\r\n                document.all.deletedFileNames[j].removeNode(true);\r\n            }\r\n        }else{\r\n            document.all.deletedFileNames.removeNode(true);\r\n        }\r\n    }\r\n    if(document.all.createdDate){\r\n        document.all.createdDate.removeNode(true);\r\n    }\r\n    if(document.all.receiveFileFileNoCount){\r\n        document.all.receiveFileFileNoCount.removeNode(true);\r\n    }\r\n    if(document.all.field1){\r\n");
      out.write("        document.all.field1.removeNode(true);\r\n    }\r\n    if(document.all.field3){\r\n        document.all.field3.removeNode(true);\r\n    }\r\n    if(document.all.receiveFileFileNumber){\r\n        document.all.receiveFileFileNumber.removeNode(true);\r\n    }\r\n    if(document.all.receiveFileSafetyGrade){\r\n        document.all.receiveFileSafetyGrade.removeNode(true);\r\n    }\r\n    if(document.all.field4){\r\n        document.all.field4.removeNode(true);\r\n    }\r\n    if(document.all.receiveFileDoComment){\r\n        document.all.receiveFileDoComment.removeNode(true);\r\n    }\r\n    if(document.all.receiveFileSettleLeaderComment){\r\n        document.all.receiveFileSettleLeaderComment.removeNode(true);\r\n    }\r\n    if(document.all.field9){\r\n        document.all.field9.removeNode(true);\r\n    }\r\n    if(document.all.receiveFileMemo){\r\n        document.all.receiveFileMemo.removeNode(true);\r\n    }\r\n    if(document.all.field6){\r\n        document.all.field6.removeNode(true);\r\n    }\r\n    if(document.all.field8){\r\n        document.all.field8.removeNode(true);\r\n");
      out.write("    }\r\n    if(document.all.workId){\r\n        document.all.workId.removeNode(true);\r\n    }\r\n    if(document.all.recordId){\r\n        document.all.recordId.removeNode(true);\r\n    }\r\n    if(document.all.processName){\r\n        document.all.processName.removeNode(true);\r\n    }\r\n    if(document.all.curActivityName){\r\n        document.all.curActivityName.removeNode(true);\r\n    }\r\n    if(document.all.submitPersonId){\r\n        document.all.submitPersonId.removeNode(true);\r\n    }\r\n    if(document.all.remindField){\r\n        document.all.remindField.removeNode(true);\r\n    }\r\n    if(document.all.standForUserId){\r\n        document.all.standForUserId.removeNode(true);\r\n    }\r\n    if(document.all.processId){\r\n        document.all.processId.removeNode(true);\r\n    }\r\n    if(document.all.curTransactType){\r\n        document.all.curTransactType.removeNode(true);\r\n    }\r\n    if(document.all.mainLinkFile){\r\n        document.all.mainLinkFile.removeNode(true);\r\n    }\r\n    if(document.all.include_comment){\r\n        document.all.include_comment.removeNode(true);\r\n");
      out.write("    }\r\n    if(document.all.workStatus){\r\n        document.all.workStatus.removeNode(true);\r\n    }\r\n    /*\r\n    if(document.all.pager.offset){\r\n        document.all.pager.offset.removeNode(true);\r\n    }\r\n    */\r\n    if(document.getElementsByName(\"pager.offset\") && document.getElementsByName(\"pager.offset\").length &&document.getElementsByName(\"pager.offset\").length >0){\r\n        document.getElementsByName(\"pager.offset\")[0].removeNode(true);\r\n    }\r\n    if(document.all.search){\r\n        document.all.search.removeNode(true);\r\n    }\r\n    if(document.all.queryNumber){\r\n        document.all.queryNumber.removeNode(true);\r\n    }\r\n    if(document.all.queryEndDate){\r\n        document.all.queryEndDate.removeNode(true);\r\n    }\r\n    if(document.all.processType){\r\n        document.all.processType.removeNode(true);\r\n    }\r\n    if(document.all.sendFileCheckComeUnit){\r\n        document.all.sendFileCheckComeUnit.removeNode(true);\r\n    }\r\n    if(document.all.sendFileCheckFinishDate){\r\n        document.all.sendFileCheckFinishDate.removeNode(true);\r\n");
      out.write("    }\r\n    if(document.all.field5){\r\n        document.all.field5.removeNode(true);\r\n    }\r\n    if(document.all.field7){\r\n        document.all.field7.removeNode(true);\r\n    }\r\n    //alert(document.getElementsByTagName(\"input\").length);\r\n\r\n//    for(var i = 0; i < document.getElementsByTagName(\"input\").length; i ++){\r\n//        alert(document.getElementsByTagName(\"input\")[i].name + \"  \" + document.getElementsByTagName(\"input\")[i].type);\r\n//    }\r\n\r\n    /*\r\n    for(var i=0;i<document.getElementsByTagName(\"input\").length;){\r\n        //alert(document.getElementsByTagName(\"input\")[i].name);\r\n        if(document.getElementsByTagName(\"input\") && document.getElementsByTagName(\"input\")[i] && document.getElementsByTagName(\"input\")[i].type && document.getElementsByTagName(\"input\")[i].type==\"hidden\"){\r\n            document.getElementsByTagName(\"input\")[i].removeNode(true);\r\n        }\r\n        i++;\r\n    }*/\r\n\r\n    for(var i=0;i<document.getElementsByTagName(\"input\").length; i ++){\r\n        //alert(document.getElementsByTagName(\"input\")[i].name);\r\n");
      out.write("        document.getElementsByTagName(\"input\")[i].name;\r\n    }\r\n\r\n   //alert(document.all.saveFileNameTemp_0_1);\r\n\r\n\r\n    for(var i=0;i<document.getElementsByTagName(\"textarea\").length;){\r\n        document.getElementsByTagName(\"textarea\")[i].removeNode(true);\r\n        i++;\r\n    }\r\n    for(var i=0;i<document.getElementsByTagName(\"select\").length;){\r\n        document.getElementsByTagName(\"select\")[i].removeNode(true);\r\n        i++;\r\n    }\r\n\r\n  var oWD = new ActiveXObject(\"Word.Application\");\r\n  var oDC = oWD.Documents.Add(\"\",0,1);\r\n  var oRange =oDC.Range(0,1);\r\n  var sel = document.body.createTextRange();\r\n  sel.select();\r\n  sel.execCommand(\"Copy\");\r\n  oRange.Paste();\r\n  oWD.Application.Visible = true;\r\n  oDC.Save();\r\n  opener.window.location.reload();\r\n  window.close();\r\n</script>\r\n");
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
