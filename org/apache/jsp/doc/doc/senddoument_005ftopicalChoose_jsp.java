/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:04:56 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.doc.doc;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class senddoument_005ftopicalChoose_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(6);
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-nested.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-tiles.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-logic.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-html.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-template.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-bean.tld", Long.valueOf(1499751390000L));
  }

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

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhtml;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fnobody;

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
    _005fjspx_005ftagPool_005fhtml_005fhtml = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fhtml.release();
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.release();
    _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fnobody.release();
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

int index = 0  ;
int countRecordCount = 0  ;
if(request.getParameter("pager.offset")!=null)
    index=Integer.parseInt(request.getParameter("pager.offset"));
int offset1 = index ;


      out.write("\r\n\r\n");


Object[]  areaTypeObj=null;
Object[]  sortTopicalObj =null;

	Map mapinfo = (Map)request.getAttribute("mapinfo");
	
	 if(mapinfo.get("areaType")!=null)
	 areaTypeObj = (Object[])mapinfo.get("areaType");//????????????
       if(mapinfo.get("sortTopical")!=null)
	 sortTopicalObj = (Object[])mapinfo.get("sortTopical");//????????????
	
    java.util.List list=new java.util.ArrayList();

	if(request.getAttribute("mylist")!=null){
	list=(List)request.getAttribute("mylist");
	}


      out.write("\r\n\r\n\r\n\r\n");
      //  html:html
      org.apache.struts.taglib.html.HtmlTag _jspx_th_html_005fhtml_005f0 = (org.apache.struts.taglib.html.HtmlTag) _005fjspx_005ftagPool_005fhtml_005fhtml.get(org.apache.struts.taglib.html.HtmlTag.class);
      boolean _jspx_th_html_005fhtml_005f0_reused = false;
      try {
        _jspx_th_html_005fhtml_005f0.setPageContext(_jspx_page_context);
        _jspx_th_html_005fhtml_005f0.setParent(null);
        int _jspx_eval_html_005fhtml_005f0 = _jspx_th_html_005fhtml_005f0.doStartTag();
        if (_jspx_eval_html_005fhtml_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\r\n<link rel=stylesheet type=\"text/css\" href=\"public/date_picker/DateObject1.css\">\r\n<link href=\"/jsoa/skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<SCRIPT language=\"javascript\" src=\"public/date_picker/DateObject.js\"></SCRIPT>\r\n<SCRIPT language=\"javascript\" src=\"public/date_picker/DatePicker.js\"></SCRIPT>\r\n<SCRIPT language=\"javascript\" src=\"public/date_picker/editlib.js\"></SCRIPT>\r\n<SCRIPT language=\"javascript\" src=\"public/date_picker/tree.js\"></SCRIPT>\r\n<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n<script language=\"javascript\" src=\"js/openEndow.js\"></script>\r\n<title>???????????????</title>\r\n<script language=\"JavaScript\" src=\"js/date0.js\"></script>\r\n</head>\r\n<body  onload=\"resizeWin(700,620);\">\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td>\r\n\r\n<table width=\"98%\"\tborder=\"0\" height=\"60\" align=\"center\" cellpadding=\"1\" cellspacing=\"1\" class=\"searchbar\" style=\"display:none\">\r\n");
            //  html:form
            org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag) _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(org.apache.struts.taglib.html.FormTag.class);
            boolean _jspx_th_html_005fform_005f0_reused = false;
            try {
              _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
              _jspx_th_html_005fform_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
              // /doc/doc/senddoument_topicalChoose.jsp(64,0) name = action type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setAction("/SenddocumentBaseAction.do?");
              // /doc/doc/senddoument_topicalChoose.jsp(64,0) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setMethod("post");
              int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
              if (_jspx_eval_html_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n  <input type=\"hidden\"  name=\"action\">\r\n  <input type=\"hidden\" name=\"queryType\">\r\n  \r\n</table>\r\n\r\n<table width=\"98%\"\theight=\"10\"\tborder=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n\r\n</table>\r\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"listTable outTopline\" align=\"center\">\r\n\r\n\r\n\r\n<tr align=\"center\">\r\n<td width=\"35%\" class=\"listTableHead\">????????????</td>\r\n<td width=\"20%\" class=\"listTableHead\">?????????</td>\r\n<td  class=\"listTableHead\">??????</td>\r\n<td  class=\"listTableHeadLast\" width=\"10%\">??????</td>\r\n</tr>\r\n\r\n\r\n<tr>\r\n<td>\r\n\t<select name=\"areaType\" id=\"areaType\" style=\"width:90%\" onchange=\"linkOption();\">\r\n\t<option value=\"\">?????????</option>\t\r\n\t");

	if(areaTypeObj!=null&&areaTypeObj.length>0){
	for(int i=0;i<areaTypeObj.length;i++){

	String areaName=areaTypeObj[i].toString();
	String [] areaArr=areaName.split("\\|");
	String areas1=""+areaArr[0];
	String areas2=""+areaArr[1];
	
                  out.write("\t\t\t\t \r\n\t<option value=\"");
                  out.print(areaName);
                  out.write('"');
                  out.write('>');
                  out.print(areas1);
                  out.write("</option>\t\r\n\t");
}}
                  out.write("\r\n\t</select>\r\n</td>\r\n\r\n\r\n<td>\r\n\t<select name=\"sortTopical\" id=\"sortTopical\" style=\"width:90%\" onchange=\"linkOptionLast();\">\r\n\t <option value=\"\">?????????</option>\r\n\t</select>\r\n</td>\r\n\r\n\r\n<td>\r\n<select name=\"lastTopical\" id=\"lastTopical\" style=\"width:220;\" class=\"inputText\">\r\n<option value=\"\">?????????</option>\r\n</select>        \r\n</td>\r\n\r\n\r\n<td>\r\n <input type=\"button\"  class=\"btnButton2Font\"  style=\"cursor:pointer\"  onClick=\"choose();\" value=\"??????\" />\r\n</td>\r\n</tr>\r\n\r\n\r\n</table>\r\n\r\n\r\n<table   width=\"98%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  align=\"center\"> \r\n<tr><td  align=\"left\" > ?????????????????????</td><td>&nbsp;</td><td>&nbsp;</td></tr>\r\n \r\n <tr><td  colspan=\"3\" width=\"100%\" align=\"center\">");
                  if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
                    return;
                  out.write("</td></tr> \r\n <tr align=\"left\"><td><input type=\"button\" class=\"btnButton4Font\"  style=\"cursor:pointer\"  onClick=\"save()\" value=\"????????????\" /><input type=\"button\"  class=\"btnButton2Font\"  style=\"cursor:pointer\"  onClick=\"reset()\" value=\"??????\"/><input type=\"button\"  class=\"btnButton2Font\"  style=\"cursor:pointer\"  onClick=\"goout();\" value=\"??????\"/></td>\r\n</tr>\r\n</table>\r\n\r\n<!-- PAGER -->\r\n\r\n ");
                  int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
              }
              if (_jspx_th_html_005fform_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                return;
              }
              _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
              _jspx_th_html_005fform_005f0_reused = true;
            } finally {
              org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fform_005f0, _jsp_getInstanceManager(), _jspx_th_html_005fform_005f0_reused);
            }
            out.write("\r\n\r\n</td>\r\n</tr>\r\n</table>\r\n</body>\r\n");
            int evalDoAfterBody = _jspx_th_html_005fhtml_005f0.doAfterBody();
            if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
              break;
          } while (true);
        }
        if (_jspx_th_html_005fhtml_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return;
        }
        _005fjspx_005ftagPool_005fhtml_005fhtml.reuse(_jspx_th_html_005fhtml_005f0);
        _jspx_th_html_005fhtml_005f0_reused = true;
      } finally {
        org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fhtml_005f0, _jsp_getInstanceManager(), _jspx_th_html_005fhtml_005f0_reused);
      }
      out.write("\r\n<script language=\"javascript\">\r\n<!--\r\n\r\nfunction commit(){\r\n\r\nSenddocumentBaseActionForm.action.value=\"chooseSendTopical\";\r\nSenddocumentBaseActionForm.queryType.value=\"0\";\r\nSenddocumentBaseActionForm.submit() ;\r\n}\r\n\r\n//???????????????\r\nfunction choose(){\r\nif(document.all.areaType.value==\"\"){\r\n alert(\"???????????????????????????\");\r\n  return false;\r\n}\r\nif(document.all.sortTopical.value==\"\"){\r\n alert(\"????????????????????????\");\r\n return false;\r\n}\r\n\r\nvar firtArr=document.all.areaType.value.split(\"\\|\");\r\nvar secordArr=document.all.sortTopical.value.split(\"\\|\");\r\n\r\n\r\nvar allStr=firtArr[0]+\" \"+secordArr[0];\r\nvar nowValue=document.all.attributeTopical.value;\r\nvar tValue=document.all.lastTopical.value;\r\n\r\nif(nowValue==\"\"){\r\nnowValue=allStr+\" \"+tValue;\r\n document.all.attributeTopical.value=nowValue+\" \";\r\n return ;\r\n}\r\n\r\n//???????????? ??????????????? ???????????? ????????????!\r\nvar resultY=nowValue.indexOf(allStr);\r\nif(resultY==-1){\r\n nowValue=nowValue+\" \"+allStr+\" \"+tValue+\" \";\r\n}else{ \r\n nowValue=nowValue.substring(0,resultY+allStr.length)+\" \"+tValue+\" \"+nowValue.substring(resultY+allStr.length+1);\r\n");
      out.write("}\r\n\r\n\r\n document.all.attributeTopical.value=nowValue;\r\n\r\n}\r\n\r\n//\r\nfunction reset(){\r\n\r\n document.all.attributeTopical.vlaue=\"\";\r\n}\r\n\r\n//?????????????????????\r\nfunction save(){\r\n opener.document.all.documentSendFileTopicWord.value =opener.document.all.documentSendFileTopicWord.value +document.all.attributeTopical.value;\r\n window.close(); \r\n}\r\n\r\n\r\n//\r\nfunction goout(){\r\nwindow.close();\r\n\r\n}\r\n\r\n\r\n\r\n//???????????? ??????\r\nfunction  linkOption(){\r\n    \r\n//???????????? ?????? ???\r\nwhile(document.all.lastTopical.length>0)\r\n\tdocument.all.lastTopical.remove(0);\r\n\r\n/*\r\n\tvar newOpt =document.createElement(\"OPTION\");\r\n\tnewOpt.value=\"\";\r\n\tnewOpt.text =\"?????????\";\r\n\tdocument.all.lastTopical.add(newOpt);\r\n*/\r\n\r\n//?????? ?????? ?????????\r\nwhile(document.all.sortTopical.length>0)\r\n\tdocument.all.sortTopical.remove(0);\r\n\r\n/*\r\n\tvar newOpt2 =document.createElement(\"OPTION\");\r\n\tnewOpt2.value=\"\";\r\n\tnewOpt2.text =\"?????????\";\r\n\tdocument.all.sortTopical.add(newOpt2);\r\n\r\n*/\r\n\r\n\tif(document.all.areaType.value!=\"\"){\r\n\t  var avalue=document.all.areaType.value;\r\n      var aArr=avalue.split(\"|\");\r\n\t  var a1=aArr[0];\r\n");
      out.write("\t  var a2=aArr[1];\r\n\r\n\r\n\r\n\r\n     ");

		 if(sortTopicalObj!=null&&sortTopicalObj.length>0){
	 for(int i=0;i<sortTopicalObj.length;i++){ 
		 String sortName=sortTopicalObj[i].toString();
		 String [] sArr=sortName.split("\\|");
		 String s1=""+sArr[0];
		 String s2=""+sArr[1];
		 s2=s2.substring(0,s2.length()-1);
		 
		 
      out.write("\t\r\n         if(a2==\"");
      out.print(s2);
      out.write("\"){\r\n\r\n\t   var newOpt =document.createElement(\"OPTION\");\r\n\t\tnewOpt.value=\"");
      out.print(sortName);
      out.write("\";\r\n\t\tnewOpt.text =\"");
      out.print(s1);
      out.write("\";\r\n\t\tdocument.all.sortTopical.add(newOpt);\r\n\t\t \r\n\t\t }\r\n\t");
}}
      out.write("\r\n\r\n\t   \r\n\t\r\n\t}\r\n\r\n\r\nlinkOptionLast();\r\n\r\n\r\n}\r\n\r\n\r\n//?????? ???????????? ???\r\nfunction linkOptionLast(){\r\n\r\n\r\n    \r\n//???????????? ?????? ???\r\nwhile(document.all.lastTopical.length>0)\r\ndocument.all.lastTopical.remove(0);\r\n\r\n/*\r\nvar newOpt =document.createElement(\"OPTION\");\r\nnewOpt.value=\"\";\r\nnewOpt.text =\"?????????\";\r\ndocument.all.lastTopical.add(newOpt);\r\n*/\r\n var realParaStr=\"\";//???????????? ???????????? ??? ?????? ??? ??? ???\r\n\r\n\tif(document.all.sortTopical.value!=\"\"){\r\n\t  var avalue2=document.all.sortTopical.value;\r\n      var aArr2=avalue2.split(\"|\");\r\n\t  var a12=aArr2[0];\r\n\t  var a22=aArr2[1];\r\n\r\n\t  var avalue1=document.all.areaType.value;\r\n      var aArr1=avalue1.split(\"|\");\r\n\t  var a11=aArr1[0];\r\n\t  var a21=aArr1[1];\r\n\r\n     realParaStr=a11+\" \"+a12;//???????????? ???????????? ??? ?????? ??? ??? ???\r\n\r\n\r\n\t  ");

	  if(list!=null&&list.size()>0){
	   for(int i=0;i<list.size();i++){
	   Object obj[]=(Object[])list.get(i);

		String parentStr="";
		String [] leiArr2=new String []{"",""};
		String [] leiArr3=new String []{"",""};

	  if(obj[2]!=null&&obj[2].toString().length()>0){
	    leiArr2=obj[2].toString().split("\\|");
  
	  }
	  if(obj[3]!=null&&obj[3].toString().length()>0){
	    leiArr3=obj[3].toString().split("\\|"); 
	  }
	   
		String content=""+obj[4];
		String [] contentObj=null;
		if(content!=null&&content.length()>0){
		contentObj=content.split(" ");
		}

       parentStr=leiArr2[0]+" "+leiArr3[0];// ????????? ?????????  ???????????????  ???????????? ??? ?????? ??? ??? ???
	   
	   
      out.write("\r\n\r\n         if(realParaStr==\"");
      out.print(parentStr);
      out.write("\"){ //?????????????????? ????????? ?????? ????????????\r\n\t\t \r\n\t\t\t\t   ");
if(contentObj!=null&&contentObj.length>0){
					for(int ii=0;ii<contentObj.length;ii++){
						if(contentObj[ii]!=null&&!contentObj[ii].trim().equals("")){
      out.write("\t\r\n\t\t\t\t\t\t\tvar newOpt3 =document.createElement(\"OPTION\");\r\n\t\t\t\t\t\t\tnewOpt3.value=\"");
      out.print(contentObj[ii]);
      out.write("\";\r\n\t\t\t\t\t\t\tnewOpt3.text =\"");
      out.print(contentObj[ii]);
      out.write("\";\r\n\t\t\t\t\t\t\tdocument.all.lastTopical.add(newOpt3);\r\n\r\n\t\t\t\t\t\t");
}			 
					}		
					}
      out.write("\r\n\t\t \t \r\n\t\t }\r\n     \r\n\t   \r\n   ");
} } 
      out.write("\r\n\r\n   \r\n\t\r\n\t}\r\n\r\n\r\n\r\n\r\n}\r\n//-->\r\n</script>\r\n\r\n<script src=\"/jsoa/js/util.js\"></script>");
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

  private boolean _jspx_meth_html_005ftextarea_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:textarea
    org.apache.struts.taglib.html.TextareaTag _jspx_th_html_005ftextarea_005f0 = (org.apache.struts.taglib.html.TextareaTag) _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fnobody.get(org.apache.struts.taglib.html.TextareaTag.class);
    boolean _jspx_th_html_005ftextarea_005f0_reused = false;
    try {
      _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005ftextarea_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /doc/doc/senddoument_topicalChoose.jsp(130,50) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setStyleClass("inputTextarea");
      // /doc/doc/senddoument_topicalChoose.jsp(130,50) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setProperty("attributeTopical");
      // /doc/doc/senddoument_topicalChoose.jsp(130,50) name = styleId type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setStyleId("attributeTopical");
      // /doc/doc/senddoument_topicalChoose.jsp(130,50) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setStyle("width:100%;height=60");
      int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
      if (_jspx_th_html_005ftextarea_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleId_005fstyleClass_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
      _jspx_th_html_005ftextarea_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005ftextarea_005f0, _jsp_getInstanceManager(), _jspx_th_html_005ftextarea_005f0_reused);
    }
    return false;
  }
}
