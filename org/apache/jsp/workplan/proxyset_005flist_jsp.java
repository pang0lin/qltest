/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:42:13 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.workplan;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.ibm.icu.text.SimpleDateFormat;
import com.js.lang.Resource;
import com.js.system.manager.service.ManagerService;

public final class proxyset_005flist_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.lang.Resource");
    _jspx_imports_classes.add("com.ibm.icu.text.SimpleDateFormat");
    _jspx_imports_classes.add("com.js.system.manager.service.ManagerService");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhtml;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid;

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
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fhtml.release();
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid.release();
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

int index=0;

      out.write('\r');
      out.write('\n');
      //  html:html
      org.apache.struts.taglib.html.HtmlTag _jspx_th_html_005fhtml_005f0 = (org.apache.struts.taglib.html.HtmlTag) _005fjspx_005ftagPool_005fhtml_005fhtml.get(org.apache.struts.taglib.html.HtmlTag.class);
      boolean _jspx_th_html_005fhtml_005f0_reused = false;
      try {
        _jspx_th_html_005fhtml_005f0.setPageContext(_jspx_page_context);
        _jspx_th_html_005fhtml_005f0.setParent(null);
        int _jspx_eval_html_005fhtml_005f0 = _jspx_th_html_005fhtml_005f0.doStartTag();
        if (_jspx_eval_html_005fhtml_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n<head>\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/style/cssmain.css\">\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject1.css\">\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DateObject.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DatePicker.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/editlib.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/tree.js\"></SCRIPT>\r\n<script language=\"javascript\" src=\"/jsoa/js/checkForm.js\"></script>\r\n<script  src=\"/jsoa/js/checkQuery.js\"  language=\"javascript\" ></script>\r\n<title>????????????</title>\r\n<link href=\"skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/style/cssmain.css\"/>\r\n<script src=\"js/js.js\" language=\"javascript\"></script>\r\n</head>\r\n<body   class=\"MainFrameBox\" onKeyDown=\"if(event.keyCode==13) setEnter();\">\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<!-- MIDDLE\tBUTTONS\t-->\r\n<tr>\r\n<td>\r\n<table width=\"100%\" height=\"25\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"inlineBottomLine\">\r\n  <tr>\r\n    \r\n\r\n\r\n     \r\n\t  <td align=\"right\">\r\n\t  <input type=\"button\" class=\"btnButton2Font\" onclick=\"MM_openBrWindow('/jsoa/WorkplanSetAction.do?action=addsetproxy','','TOP=0,LEFT=0,scrollbars=yes,resizable=yes,width=600,height=300')\" value=\"??????\"/>\r\n\t  <input type=\"button\" class=\"btnButton2Font\" onclick=\"javascript:del();\" value=\"??????\"/>\r\n\t  \r\n\t  </td> \r\n    </tr>\r\n</table>\r\n</td>\r\n</tr>\r\n\r\n\r\n<!-- LIST TITLE PART -->\r\n\r\n\r\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"listTable\">\r\n    ");
            //  html:form
            org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag) _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(org.apache.struts.taglib.html.FormTag.class);
            boolean _jspx_th_html_005fform_005f0_reused = false;
            try {
              _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
              _jspx_th_html_005fform_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
              // /workplan/proxyset_list.jsp(59,4) name = action type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_html_005fform_005f0.setAction("DutyAction.do?action=del");
              int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
              if (_jspx_eval_html_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t<tr>\r\n\t\t\t<td class=\"listTableHead\" width=\"40\" nowrap>\r\n\t\t\t <input type=\"checkbox\"  name=\"Del\"  onclick=\"javascript:selectall(this);\"></td>\r\n\t\t\t<td class=\"listTableHead\" width=\"40%\" >??????</td>\r\n\t\t\t<td class=\"listTableHead\" width=\"40%\" >?????????</td>\r\n\t\t\t\t\r\n\t\t\t<td class=\"listTableHeadLast\" width=\"20%\" nowrap> ?????? </td>\r\n\t\t</tr>\r\n\t\t");

		Object[] obj;
		
                  out.write("\r\n\r\n\t\t");
                  //  logic:iterate
                  org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_005fiterate_005f0 = (org.apache.struts.taglib.logic.IterateTag) _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid.get(org.apache.struts.taglib.logic.IterateTag.class);
                  boolean _jspx_th_logic_005fiterate_005f0_reused = false;
                  try {
                    _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
                    _jspx_th_logic_005fiterate_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
                    // /workplan/proxyset_list.jsp(72,2) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                    _jspx_th_logic_005fiterate_005f0.setId("proxyList");
                    // /workplan/proxyset_list.jsp(72,2) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                    _jspx_th_logic_005fiterate_005f0.setName("proxyList");
                    // /workplan/proxyset_list.jsp(72,2) name = scope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
                    _jspx_th_logic_005fiterate_005f0.setScope("request");
                    int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
                    if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                      java.lang.Object proxyList = null;
                      if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                        out = org.apache.jasper.runtime.JspRuntimeLibrary.startBufferedBody(_jspx_page_context, _jspx_th_logic_005fiterate_005f0);
                      }
                      proxyList = (java.lang.Object) _jspx_page_context.findAttribute("proxyList");
                      do {
                        out.write("\r\n\t\t\r\n\t\t\t");

			obj = (Object[]) proxyList;
			index ++;
			
                        out.write("\r\n\t\t\t");

			String listClass="listTableLine1";
			if(index%2 != 0){
				listClass="listTableLine2";
			}
			
                        out.write("\r\n\t\t\t<tr ");
if(index%2 == 0){
                        out.write("bgcolor=\"#FFFFFF\"");
}else{
                        out.write("bgcolor=\"#E8ECED\"");
}
                        out.write(">\r\n\t\t\t\t<td class=\"");
                        out.print(listClass);
                        out.write("\" align=\"left\"><input type=\"checkbox\" id=\"proxysetId\" name=\"proxysetId\" value=\"");
                        out.print(obj[0]);
                        out.write("\"></td>\r\n\t\t\t\t<td class=\"");
                        out.print(listClass);
                        out.write("\" align=\"left\">");
                        out.print(obj[1]);
                        out.write("</td>\r\n\t\t\t\t<td class=\"");
                        out.print(listClass);
                        out.write("\" align=\"left\">");
                        out.print(obj[2]);
                        out.write("</td>\t\t\t  \r\n\t\t\t\t\r\n\t\t\t\t<td class=\"");
                        out.print(listClass);
                        out.write(" listTableLineLastTD\"  nowrap align=\"center\">\t\r\n\t\t\t\t\t\t\t\t\r\n\t\t\t\t<img src=\"images/modi.gif\" title=\"??????\" style=\"cursor:pointer\" onclick=\"modify('");
                        out.print(obj[0]);
                        out.write("')\">&nbsp;\r\n\t\t\t\t<img src=\"/jsoa/images/del.gif\" style=\"cursor:pointer\" title=\"??????\" onclick=\"deleteItem('");
                        out.print(obj[0]);
                        out.write("')\" >&nbsp;\r\n\t\t\t\t\r\n\t\t\t\t</td>\r\n\t\t\t</tr>\r\n\t\t");
                        int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
                        proxyList = (java.lang.Object) _jspx_page_context.findAttribute("proxyList");
                        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                          break;
                      } while (true);
                      if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                        out = _jspx_page_context.popBody();
                      }
                    }
                    if (_jspx_th_logic_005fiterate_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                      return;
                    }
                    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
                    _jspx_th_logic_005fiterate_005f0_reused = true;
                  } finally {
                    org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_logic_005fiterate_005f0, _jsp_getInstanceManager(), _jspx_th_logic_005fiterate_005f0_reused);
                  }
                  out.write("\r\n                 ");
                  int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
              }
              if (_jspx_th_html_005fform_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                return;
              }
              _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
              _jspx_th_html_005fform_005f0_reused = true;
            } finally {
              org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fform_005f0, _jsp_getInstanceManager(), _jspx_th_html_005fform_005f0_reused);
            }
            out.write("\r\n</table>\r\n\r\n</table>\r\n\r\n</body>\r\n");
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
      out.write("\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n<script language=\"JavaScript\" type=\"text/JavaScript\">\r\n<!--\r\nfunction MM_openBrWindow(theURL,winName,features) { //v2.0\r\n  JSMainWinOpenNew(theURL,winName,features);\r\n}\r\nfunction modify(id){\r\n\tJSMainWinOpenNew(\"/jsoa/WorkplanSetAction.do?action=proxysetmodify&proxysetId=\"+id,'','TOP=0,LEFT=0,scrollbars=yes,resizable=yes,width=600,height=300');\r\n}\r\nfunction del(){\r\n\tvar ids=getBatchIDs();\r\n\tif(ids!=\"\"){\r\n\t\tdeleteItem(ids)\r\n\t}else{\r\n\t\talert(\"???????????????????????????!\");\r\n\t}\r\n}\r\nfunction deleteItem(ids){\r\n\tif(confirm(\"????????????????????????????????????!\")){\r\n\t\tlocation.href=((window.location.href).substring(0,(window.location.href).indexOf(\"/jsoa\")))+\"/jsoa/WorkplanSetAction.do?action=deleteproxy&proxysetId=\"+ids;\r\n\t}\r\n}\r\nfunction getBatchIDs(){\r\n\tvar ckObj=document.getElementsByName(\"proxysetId\");\r\n\tvar ids=\"\";\r\n\tfor(var i=0;i<ckObj.length;i++){\r\n\t\tif(ckObj[i].checked){\r\n\t\t\tids+=ckObj[i].value+\",\";\r\n\t\t}\r\n\t}\r\n\tif(ids.length>1){\r\n\t\tids=ids.substring(0,(ids.length-1));\r\n\t}\r\n\treturn ids;\r\n\t\r\n}\r\n\r\n//-->\r\n</script>");
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
