/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:39:55 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.scheme.worklog;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.oa.scheme.worklog.service.*;
import java.util.*;

public final class workmanager_005fworklog_005fprogrammer_005ftask_005fadd_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("com.js.oa.scheme.worklog.service");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhtml;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fnobody;

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
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fhtml.release();
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.release();
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fnobody.release();
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

	response.setHeader("Cache-Control","no-store");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader ("Expires", 0);
    String project_id=request.getAttribute("project_id")==null?"":request.getAttribute("project_id").toString();
    String close=request.getAttribute("close")==null?"":request.getAttribute("close").toString();

      out.write("\r\n\r\n");
      //  html:html
      org.apache.struts.taglib.html.HtmlTag _jspx_th_html_005fhtml_005f0 = (org.apache.struts.taglib.html.HtmlTag) _005fjspx_005ftagPool_005fhtml_005fhtml.get(org.apache.struts.taglib.html.HtmlTag.class);
      boolean _jspx_th_html_005fhtml_005f0_reused = false;
      try {
        _jspx_th_html_005fhtml_005f0.setPageContext(_jspx_page_context);
        _jspx_th_html_005fhtml_005f0.setParent(null);
        int _jspx_eval_html_005fhtml_005f0 = _jspx_th_html_005fhtml_005f0.doStartTag();
        if (_jspx_eval_html_005fhtml_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<link href=\"skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<title>??????????????????</title>\r\n\r\n<SCRIPT language=javascript src=\"/jsoa/js/openEndow.js\"></SCRIPT>\r\n</head>\r\n<body  class=\"MainFrameBox\" >\r\n<table width=\"100%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\" class=\"searchbar\">\r\n\t\t\t\t    ");
            //  html:form
            org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag) _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(org.apache.struts.taglib.html.FormTag.class);
            boolean _jspx_th_html_005fform_005f0_reused = false;
            try {
              _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
              _jspx_th_html_005fform_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
              // /scheme/worklog/workmanager_worklog_programmer_task_add.jsp(28,8) name = action type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setAction("/workLogAction?action=addTask");
              // /scheme/worklog/workmanager_worklog_programmer_task_add.jsp(28,8) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setMethod("post");
              int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
              if (_jspx_eval_html_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n                                        <input type=\"hidden\" name=\"hasChanged\" value=\"1\">\r\n                                        <input type=\"hidden\" name=\"project_id\" value=\"");
                  out.print(project_id);
                  out.write("\" />\r\n                                        <input type=\"hidden\" name=\"close\" value\"1\"/>\r\n\t\t\t\t\t  <tr>\r\n\t\t\t\t\t\t<td nowrap><b><span id=\"actionName\">??????????????????</span></td>\r\n                                                    <td>\r\n                                                    </td>\r\n\t\t\t\t\t  </tr>\r\n                                          <tr>\r\n                                              <td nowrap>????????????&nbsp;<label class=\"mustFillcolor\">*</label>???</td>\r\n                                              <td nowrap>\r\n                                                  ");
                  if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
                    return;
                  out.write("\r\n                                              </td>\r\n\t\t\t\t\t  </tr>\r\n\r\n\t\t\t\t\t  <tr>\r\n                                              <td nowrap>????????????&nbsp;<label class=\"mustFillcolor\">*</label>???</td>\r\n                                              <td nowrap>");
                  if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
                    return;
                  out.write("</td>\r\n                                          </tr>\r\n\t\t\t\t\t  <tr name=\"positionId\" id=\"positionId\" style=\"display:''\">\r\n\t\t\t\t\t\t  <td nowrap>???????????????</td>\r\n\t\t\t\t\t\t  <td nowrap> <select name=\"task_fathercode\" onchange=\"changeProject(this);\">\r\n                                                      <option value=\"0:-1:0\">?????????&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>\r\n                                                      ");

                                                      		List list1=new ArrayList();
        							List list=request.getAttribute("workTaskList")==null?list1:(List)request.getAttribute("workTaskList");
                                                                for(int i=0;i<list.size();i++){
                                                                    Object[] obj=(Object[])list.get(i);
                                                                    
                  out.write("\r\n                                                       <option value=\"");
                  out.print(obj[0]);
                  out.write(':');
                  out.print(obj[5]);
                  out.write(':');
                  out.print(obj[6]);
                  out.write("\">\r\n                                                           ");

                                                           int x=Integer.parseInt(obj[5].toString());
                							for(int n=0;n<x;n++){
                         					 	 
                  out.write("\r\n                \t\t\t\t\t\t\t&nbsp;\r\n                                                                        ");
}
                  out.write("\r\n                                                           ");
                  out.print(obj[3]);
                  out.write("\r\n\r\n                                                           </option>\r\n\r\n                                                ");
}
                  out.write("\r\n                                      \t</select>\r\n\r\n\t\t\t\t\t  </tr>\r\n\r\n\t\t\t\t\t  <tr>\r\n                                              <td nowrap>??????&nbsp;</td>\r\n                                              <td nowrap>   <span id=\"showContent\" >\r\n                                              \t\t<select name=\"task_sortcode\">\r\n                                                                <option value=\"-1\">?????????</option>\r\n                                                            </select>\r\n                                                        </span>\r\n                                       \t <input type=\"radio\" value=\"up\" name=\"orgSort\" checked=\"checked\" onclick=\"javascript:changeOrder();\"/>\r\n                                     \t ???\r\n                                     \t <input type=\"radio\" value=\"down\" name=\"orgSort\" onclick=\"javascript:changeOrder1();\"/>\r\n                                     \t ???\r\n                                         </td>\r\n\t\t\t\t\t\t  <td nowrap>&nbsp;</td></td>\r\n                                          </tr>\r\n");
                  out.write("\t\t\t\t\t  <tr>\r\n\t\t\t\t\t\t  <td nowrap>?????????</td>\r\n\t\t\t\t\t\t  <td nowrap>");
                  if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
                    return;
                  out.write("</td>\r\n\t\t\t\t\t  </tr>\r\n\r\n\r\n\t\t\t\t    ");
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
            out.write("\r\n\r\n  \t\t\t\t<tr>\r\n\t\t\t\t\t\t<td colspan=\"2\">\r\n\r\n\t\t\t\t\t\t\t  <input type=\"button\" class=\"btnButton2font\" onClick=\"javascript:formSubmit('1');\" value=\"????????????\" />\r\n\t\t\t\t\t\t\t  <input type=\"button\" class=\"btnButton2font\" onClick=\"javascript:formSubmit('3');\" value=\"????????????\" />\r\n\r\n\t\t\t\t\t\t  <input type=\"button\" class=\"btnButton2font\" onClick=\"javascript:formReset();\" value=\"??????\" />\r\n\t\t\t\t\t\t\t <input type=\"button\" class=\"btnButton2font\" onClick=\"window.close();\" value=\"??????\" />\r\n                                                  </td>\r\n\t\t\t\t</tr>\r\n</table>\r\n</body>\r\n");
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
      out.write('\r');
      out.write('\n');
if("1".equals(close)){
      out.write("\r\n       <script>\r\n       \t\t//alert(1);\r\n                opener.searcher();\r\n\t\t//opener.window.location.href=\"/jsoa/workLogAction.do?action=selectWorkTaskById&project_id=\"+document.all.project_id.value;\r\n\t\t window.close();\r\n      </script>\r\n");
}
      out.write('\r');
      out.write('\n');
if("2".equals(close)){
      out.write("\r\n       <script>\r\n       \t\talert(\"???????????????????????????????????????\");\r\n      </script>\r\n");
}
      out.write('\r');
      out.write('\n');
if("3".equals(close)){
      out.write("\r\n       <script>\r\n       \t\t opener.searcher();\r\n                document.all.task_fathercode.value=\"0:-1:0\";\r\n    document.all.task_code.value=\"\";\r\n     document.all.task_name.value=\"\";\r\n      document.all.task_description.value=\"\";\r\n      document.all.task_sortcode.value=\"-1\";\r\n      </script>\r\n");
}
      out.write("\r\n<script language=\"javascript\">\r\n\r\nfunction orgTrim(str){\r\n    while(str.charAt(0)==\" \"){\r\n        str=str.substring(1,str.length);\r\n    }\r\n    while(str.charAt(str.length-1)==\" \"){\r\n        str=str.substring(0,str.length-1);\r\n    }\r\n    return str;\r\n}\r\n\r\nfunction formSubmit(close){\r\n\r\n    var tmp=document.all.task_code.value;\r\n    var tmpSerial=document.all.task_name.value;\r\n    var tmpDescription=document.all.task_description.value;\r\n    tmp=orgTrim(tmp);\r\n    tmpSerial=orgTrim(tmpSerial);\r\n    if(tmp==\"\"){\r\n        document.all.task_code.value=\"\";\r\n        alert(\"???????????????????????????\");\r\n        document.all.task_code.focus();\r\n        return;\r\n    }if(tmpSerial==\"\"){\r\n        document.all.task_name.value=\"\";\r\n        alert(\"???????????????????????????\");\r\n        document.all.task_name.focus();\r\n        return;\r\n    }if(tmp.length >100){\r\n        alert(\"????????????????????????100?????????\");\r\n       \tdocument.all.task_code.focus();\r\n        return ;\r\n    }if(tmpSerial.length >100){\r\n        alert(\"????????????????????????100?????????\");\r\n       \tdocument.all.task_name.focus();\r\n");
      out.write("        return ;\r\n    }\r\n    if(tmpDescription.length >100){\r\n        alert(\"????????????????????????100?????????\");\r\n       \tdocument.all.task_description.focus();\r\n        return ;\r\n    }\r\n    document.all.close.value=close;\r\n    //alert(document.all.close.value);\r\n    document.workLogActionForm.submit();\r\n}\r\n\r\nfunction formReset(){\r\n    document.all.task_fathercode.value=\"0:-1:0\";\r\n    document.all.task_code.value=\"\";\r\n     document.all.task_name.value=\"\";\r\n      document.all.task_description.value=\"\";\r\n      document.all.task_sortcode.value=\"-1\";\r\n\r\n\r\n}\r\n\r\n\r\nfunction changeOrder(){\r\n\t//alert(document.all.hasChanged.value);\r\n\r\n    document.all.hasChanged.value=\"1\";\r\n}\r\nfunction changeOrder1(){\r\n    \t//alert(document.all.hasChanged.value);\r\n    document.all.hasChanged.value=\"2\";\r\n    //alert(document.all.hasChanged.value);\r\n}\r\n\r\nfunction setChannelStyle(){\r\n    if(document.all.orgHasChannel.value==1){\r\n        if(document.all.orgChannelStyle.value==\"0\"){\r\n            document.all.orgChannelStyle.value=\"1\";\r\n        }\r\n    }\r\n}\r\n\r\n");
      out.write("function show(){\r\n\r\n\r\n        document.all.obj.style.display=\"\";\r\n\r\n}\r\n//4???3???1??????\r\nfunction changeProject(classId){\r\n    var selectedValue = classId.options[classId.selectedIndex].value;\r\n\r\n    if(selectedValue!='0:-1:0'){//???????????????\r\n        var task_id = selectedValue.split(\":\")[0];\r\n        getHttprequestData(task_id);\r\n    } else{\r\n        getHttprequestData(\"-1\");\r\n    }\r\n}\r\n\r\nvar xmlHttp = false;\r\ntry {\r\n\t xmlHttp = new ActiveXObject(\"Msxml2.XMLHTTP\");\r\n} catch (e) {\r\n\ttry {\r\n\t\txmlHttp = new ActiveXObject(\"Microsoft.XMLHTTP\");\r\n\t} catch (e2) {\r\n\t\txmlHttp = false;\r\n\t}\r\n}\r\nif (!xmlHttp && typeof XMLHttpRequest != 'undefined') {\r\n\txmlHttp = new XMLHttpRequest();\r\n}\r\nfunction getHttprequestData(task_id){\r\n\txmlHttp.open(\"GET\", \"/jsoa/scheme/worklog/workmanager_httprequest.jsp?task_id=\"+task_id, true);\r\n\txmlHttp.onreadystatechange = getData;\r\n\txmlHttp.send(null);\r\n}\r\n\r\nfunction getData(){\r\n\tif (xmlHttp.readyState == 4) {\r\n\t\tif(xmlHttp.status == 200) {\r\n\t\t\tvar response = xmlHttp.responseText;\r\n\t\t\tdocument.getElementById(\"showContent\").innerHTML=response;\r\n");
      out.write("\t\t}\r\n\t}\r\n}\r\n</script>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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

  private boolean _jspx_meth_html_005ftext_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f0 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
    boolean _jspx_th_html_005ftext_005f0_reused = false;
    try {
      _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005ftext_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /scheme/worklog/workmanager_worklog_programmer_task_add.jsp(40,50) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setStyleClass("inputText");
      // /scheme/worklog/workmanager_worklog_programmer_task_add.jsp(40,50) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setProperty("task_code");
      // /scheme/worklog/workmanager_worklog_programmer_task_add.jsp(40,50) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setStyle("width:350px");
      int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
      if (_jspx_th_html_005ftext_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
      _jspx_th_html_005ftext_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005ftext_005f0, _jsp_getInstanceManager(), _jspx_th_html_005ftext_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005ftext_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f1 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
    boolean _jspx_th_html_005ftext_005f1_reused = false;
    try {
      _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
      _jspx_th_html_005ftext_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /scheme/worklog/workmanager_worklog_programmer_task_add.jsp(46,57) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f1.setStyleClass("inputText");
      // /scheme/worklog/workmanager_worklog_programmer_task_add.jsp(46,57) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f1.setProperty("task_name");
      // /scheme/worklog/workmanager_worklog_programmer_task_add.jsp(46,57) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f1.setStyle("width:350px");
      int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
      if (_jspx_th_html_005ftext_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
      _jspx_th_html_005ftext_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005ftext_005f1, _jsp_getInstanceManager(), _jspx_th_html_005ftext_005f1_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005ftextarea_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:textarea
    org.apache.struts.taglib.html.TextareaTag _jspx_th_html_005ftextarea_005f0 = (org.apache.struts.taglib.html.TextareaTag) _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fnobody.get(org.apache.struts.taglib.html.TextareaTag.class);
    boolean _jspx_th_html_005ftextarea_005f0_reused = false;
    try {
      _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005ftextarea_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /scheme/worklog/workmanager_worklog_programmer_task_add.jsp(90,19) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setStyle("width:350px");
      // /scheme/worklog/workmanager_worklog_programmer_task_add.jsp(90,19) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setStyleClass("inputTextarea");
      // /scheme/worklog/workmanager_worklog_programmer_task_add.jsp(90,19) name = rows type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setRows("8");
      // /scheme/worklog/workmanager_worklog_programmer_task_add.jsp(90,19) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftextarea_005f0.setProperty("task_description");
      int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
      if (_jspx_th_html_005ftextarea_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005frows_005fproperty_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
      _jspx_th_html_005ftextarea_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005ftextarea_005f0, _jsp_getInstanceManager(), _jspx_th_html_005ftextarea_005f0_reused);
    }
    return false;
  }
}
