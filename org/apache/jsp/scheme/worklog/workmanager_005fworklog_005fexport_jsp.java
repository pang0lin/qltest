/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:39:48 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.scheme.worklog;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.Locale;
import com.js.oa.scheme.worklog.vo.WorkLogVO;
import com.js.lang.Resource;

public final class workmanager_005fworklog_005fexport_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(6);
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-nested.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-logic.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-tiles.tld", Long.valueOf(1499751390000L));
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
    _jspx_imports_classes.add("java.util.Locale");
    _jspx_imports_classes.add("com.js.oa.scheme.worklog.vo.WorkLogVO");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody;

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
    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid.release();
    _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.release();
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.release();
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

String local = session.getAttribute("org.apache.struts.action.LOCALE").toString();

      out.write("\r\n<html>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<head>\r\n    <title>");
      out.print(Resource.getValue(local, "personalwork", "innercontact.export"));
      out.write("</title>\r\n</head>\r\n");

int index = 0 ;
int countRecordCount = 0  ;
if(request.getParameter("pager.offset")!=null)
        index=Integer.parseInt(request.getParameter("pager.offset"));
int offset1 = index ;

      out.write("\r\n<body>\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td>\r\n<table border=\"1\">\r\n    <tr>\r\n        <td align=\"center\"><b>项目阶段</b></td>\r\n        <td align=\"center\"><b>填写人</b></td>\r\n        <td align=\"center\"><b>工时</b></td>\r\n        <td align=\"center\"><b>填写日期</b></td>\r\n        <td align=\"center\"><b>任务代号</b></td>\r\n        <td align=\"center\"><b>任务名称</b></td>\r\n        <td align=\"center\"><b>工作量</b></td>\r\n        <td align=\"center\"><b>内容</b></td>\r\n    </tr>\r\n\r\n\t");

	  String listClass="listTableLine1";
	  
      out.write("\r\n    <!--logic:iterate id=\"mylist\" name=\"mylist\" scope=\"request\"-->\r\n\t ");

//		Object[] obj = (Object[])mylist;
//		index ++;
//		listClass = index%2!=0?"listTableLine2":"listTableLine1";
//		countRecordCount ++;
//	  
      out.write("\r\n       \t");
      //  logic:iterate
      org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_005fiterate_005f0 = (org.apache.struts.taglib.logic.IterateTag) _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid.get(org.apache.struts.taglib.logic.IterateTag.class);
      boolean _jspx_th_logic_005fiterate_005f0_reused = false;
      try {
        _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
        _jspx_th_logic_005fiterate_005f0.setParent(null);
        // /scheme/worklog/workmanager_worklog_export.jsp(52,8) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_logic_005fiterate_005f0.setId("List");
        // /scheme/worklog/workmanager_worklog_export.jsp(52,8) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_logic_005fiterate_005f0.setName("projectDetailInformationList");
        // /scheme/worklog/workmanager_worklog_export.jsp(52,8) name = scope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_logic_005fiterate_005f0.setScope("request");
        int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
        if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          java.lang.Object List = null;
          if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
            out = org.apache.jasper.runtime.JspRuntimeLibrary.startBufferedBody(_jspx_page_context, _jspx_th_logic_005fiterate_005f0);
          }
          List = (java.lang.Object) _jspx_page_context.findAttribute("List");
          do {
            out.write("\r\n\t\t\t");

                        WorkLogVO workLogVO=(WorkLogVO)List;
	index ++;
	listClass = index%2!=0?"listTableLine2":"listTableLine1";
		String logContent = "";//((com.js.oa.scheme.worklog.po.WorkLogPO)List).getLogContent();
				if(logContent != null && logContent.length()>50){
                 logContent = logContent.substring(0,50)+"...";
				}
					
            out.write("\r\n\t  <tr>\r\n\t\t<td class=\"");
            out.print(listClass);
            out.write('"');
            out.write('>');
            if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
              return;
            out.write('(');
            if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
              return;
            if (_jspx_meth_bean_005fmessage_005f0(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
              return;
            out.write(")</td>\r\n\t\t<td class=\"");
            out.print(listClass);
            out.write('"');
            out.write('>');
            if (_jspx_meth_bean_005fwrite_005f2(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
              return;
            out.write("</td>\r\n\t\t<td class=\"");
            out.print(listClass);
            out.write('"');
            out.write('>');
            if (_jspx_meth_bean_005fwrite_005f3(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
              return;
            out.write("</td>\r\n\t\t<td class=\"");
            out.print(listClass);
            out.write('"');
            out.write('>');
            if (_jspx_meth_bean_005fwrite_005f4(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
              return;
            out.write("</td>\r\n                <td class=\"");
            out.print(listClass);
            out.write('"');
            out.write('>');
            if (_jspx_meth_bean_005fwrite_005f5(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
              return;
            out.write("</td>\r\n                <td class=\"");
            out.print(listClass);
            out.write('"');
            out.write('>');
            if (_jspx_meth_bean_005fwrite_005f6(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
              return;
            out.write("</td>\r\n                <td class=\"");
            out.print(listClass);
            out.write('"');
            out.write('>');
            if (_jspx_meth_bean_005fwrite_005f7(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
              return;
            out.write("(h)</td>\r\n\t\t<td class=\"");
            out.print(listClass);
            out.write(" listTableLineLastTD\" style=\"word-break:break-all;\">");
            if (_jspx_meth_bean_005fwrite_005f8(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
              return;
            out.write("</td>\r\n\t  </tr>\r\n\t  ");
            int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
            List = (java.lang.Object) _jspx_page_context.findAttribute("List");
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
      out.write("\r\n    <!--/logic:iterate-->\r\n</table>\r\n\r\n</td></tr></table>\r\n</body>\r\n</html>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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

  private boolean _jspx_meth_bean_005fwrite_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fiterate_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f0 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    boolean _jspx_th_bean_005fwrite_005f0_reused = false;
    try {
      _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fwrite_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fiterate_005f0);
      // /scheme/worklog/workmanager_worklog_export.jsp(63,29) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f0.setName("List");
      // /scheme/worklog/workmanager_worklog_export.jsp(63,29) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f0.setProperty("projectStep");
      // /scheme/worklog/workmanager_worklog_export.jsp(63,29) name = format type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f0.setFormat("");
      int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
      if (_jspx_th_bean_005fwrite_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
      _jspx_th_bean_005fwrite_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fwrite_005f0, _jsp_getInstanceManager(), _jspx_th_bean_005fwrite_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_bean_005fwrite_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fiterate_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f1 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    boolean _jspx_th_bean_005fwrite_005f1_reused = false;
    try {
      _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fwrite_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fiterate_005f0);
      // /scheme/worklog/workmanager_worklog_export.jsp(63,88) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f1.setName("List");
      // /scheme/worklog/workmanager_worklog_export.jsp(63,88) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f1.setProperty("projectStepTotalTime");
      // /scheme/worklog/workmanager_worklog_export.jsp(63,88) name = format type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f1.setFormat("0");
      int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
      if (_jspx_th_bean_005fwrite_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
      _jspx_th_bean_005fwrite_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fwrite_005f1, _jsp_getInstanceManager(), _jspx_th_bean_005fwrite_005f1_reused);
    }
    return false;
  }

  private boolean _jspx_meth_bean_005fmessage_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fiterate_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f0 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    boolean _jspx_th_bean_005fmessage_005f0_reused = false;
    try {
      _jspx_th_bean_005fmessage_005f0.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fmessage_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fiterate_005f0);
      // /scheme/worklog/workmanager_worklog_export.jsp(63,156) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f0.setBundle("personalwork");
      // /scheme/worklog/workmanager_worklog_export.jsp(63,156) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f0.setKey("diary.hour");
      int _jspx_eval_bean_005fmessage_005f0 = _jspx_th_bean_005fmessage_005f0.doStartTag();
      if (_jspx_th_bean_005fmessage_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0);
      _jspx_th_bean_005fmessage_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fmessage_005f0, _jsp_getInstanceManager(), _jspx_th_bean_005fmessage_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_bean_005fwrite_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fiterate_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f2 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    boolean _jspx_th_bean_005fwrite_005f2_reused = false;
    try {
      _jspx_th_bean_005fwrite_005f2.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fwrite_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fiterate_005f0);
      // /scheme/worklog/workmanager_worklog_export.jsp(64,29) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f2.setName("List");
      // /scheme/worklog/workmanager_worklog_export.jsp(64,29) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f2.setProperty("empName");
      // /scheme/worklog/workmanager_worklog_export.jsp(64,29) name = format type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f2.setFormat("");
      int _jspx_eval_bean_005fwrite_005f2 = _jspx_th_bean_005fwrite_005f2.doStartTag();
      if (_jspx_th_bean_005fwrite_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
      _jspx_th_bean_005fwrite_005f2_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fwrite_005f2, _jsp_getInstanceManager(), _jspx_th_bean_005fwrite_005f2_reused);
    }
    return false;
  }

  private boolean _jspx_meth_bean_005fwrite_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fiterate_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f3 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    boolean _jspx_th_bean_005fwrite_005f3_reused = false;
    try {
      _jspx_th_bean_005fwrite_005f3.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fwrite_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fiterate_005f0);
      // /scheme/worklog/workmanager_worklog_export.jsp(65,29) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f3.setName("List");
      // /scheme/worklog/workmanager_worklog_export.jsp(65,29) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f3.setProperty("manHour");
      // /scheme/worklog/workmanager_worklog_export.jsp(65,29) name = format type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f3.setFormat("");
      int _jspx_eval_bean_005fwrite_005f3 = _jspx_th_bean_005fwrite_005f3.doStartTag();
      if (_jspx_th_bean_005fwrite_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.reuse(_jspx_th_bean_005fwrite_005f3);
      _jspx_th_bean_005fwrite_005f3_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fwrite_005f3, _jsp_getInstanceManager(), _jspx_th_bean_005fwrite_005f3_reused);
    }
    return false;
  }

  private boolean _jspx_meth_bean_005fwrite_005f4(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fiterate_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f4 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    boolean _jspx_th_bean_005fwrite_005f4_reused = false;
    try {
      _jspx_th_bean_005fwrite_005f4.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fwrite_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fiterate_005f0);
      // /scheme/worklog/workmanager_worklog_export.jsp(66,29) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f4.setName("List");
      // /scheme/worklog/workmanager_worklog_export.jsp(66,29) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f4.setProperty("logDateFormat");
      // /scheme/worklog/workmanager_worklog_export.jsp(66,29) name = format type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f4.setFormat("");
      int _jspx_eval_bean_005fwrite_005f4 = _jspx_th_bean_005fwrite_005f4.doStartTag();
      if (_jspx_th_bean_005fwrite_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.reuse(_jspx_th_bean_005fwrite_005f4);
      _jspx_th_bean_005fwrite_005f4_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fwrite_005f4, _jsp_getInstanceManager(), _jspx_th_bean_005fwrite_005f4_reused);
    }
    return false;
  }

  private boolean _jspx_meth_bean_005fwrite_005f5(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fiterate_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f5 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    boolean _jspx_th_bean_005fwrite_005f5_reused = false;
    try {
      _jspx_th_bean_005fwrite_005f5.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fwrite_005f5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fiterate_005f0);
      // /scheme/worklog/workmanager_worklog_export.jsp(67,43) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f5.setName("List");
      // /scheme/worklog/workmanager_worklog_export.jsp(67,43) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f5.setProperty("projectTaskCode");
      // /scheme/worklog/workmanager_worklog_export.jsp(67,43) name = format type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f5.setFormat("");
      int _jspx_eval_bean_005fwrite_005f5 = _jspx_th_bean_005fwrite_005f5.doStartTag();
      if (_jspx_th_bean_005fwrite_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.reuse(_jspx_th_bean_005fwrite_005f5);
      _jspx_th_bean_005fwrite_005f5_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fwrite_005f5, _jsp_getInstanceManager(), _jspx_th_bean_005fwrite_005f5_reused);
    }
    return false;
  }

  private boolean _jspx_meth_bean_005fwrite_005f6(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fiterate_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f6 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    boolean _jspx_th_bean_005fwrite_005f6_reused = false;
    try {
      _jspx_th_bean_005fwrite_005f6.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fwrite_005f6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fiterate_005f0);
      // /scheme/worklog/workmanager_worklog_export.jsp(68,43) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f6.setName("List");
      // /scheme/worklog/workmanager_worklog_export.jsp(68,43) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f6.setProperty("projectTaskName");
      // /scheme/worklog/workmanager_worklog_export.jsp(68,43) name = format type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f6.setFormat("");
      int _jspx_eval_bean_005fwrite_005f6 = _jspx_th_bean_005fwrite_005f6.doStartTag();
      if (_jspx_th_bean_005fwrite_005f6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.reuse(_jspx_th_bean_005fwrite_005f6);
      _jspx_th_bean_005fwrite_005f6_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fwrite_005f6, _jsp_getInstanceManager(), _jspx_th_bean_005fwrite_005f6_reused);
    }
    return false;
  }

  private boolean _jspx_meth_bean_005fwrite_005f7(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fiterate_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f7 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    boolean _jspx_th_bean_005fwrite_005f7_reused = false;
    try {
      _jspx_th_bean_005fwrite_005f7.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fwrite_005f7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fiterate_005f0);
      // /scheme/worklog/workmanager_worklog_export.jsp(69,43) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f7.setName("List");
      // /scheme/worklog/workmanager_worklog_export.jsp(69,43) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f7.setProperty("taskLoad");
      // /scheme/worklog/workmanager_worklog_export.jsp(69,43) name = format type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f7.setFormat("");
      int _jspx_eval_bean_005fwrite_005f7 = _jspx_th_bean_005fwrite_005f7.doStartTag();
      if (_jspx_th_bean_005fwrite_005f7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.reuse(_jspx_th_bean_005fwrite_005f7);
      _jspx_th_bean_005fwrite_005f7_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fwrite_005f7, _jsp_getInstanceManager(), _jspx_th_bean_005fwrite_005f7_reused);
    }
    return false;
  }

  private boolean _jspx_meth_bean_005fwrite_005f8(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fiterate_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f8 = (org.apache.struts.taglib.bean.WriteTag) _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    boolean _jspx_th_bean_005fwrite_005f8_reused = false;
    try {
      _jspx_th_bean_005fwrite_005f8.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fwrite_005f8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fiterate_005f0);
      // /scheme/worklog/workmanager_worklog_export.jsp(70,79) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f8.setName("List");
      // /scheme/worklog/workmanager_worklog_export.jsp(70,79) name = property type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f8.setProperty("logContent");
      // /scheme/worklog/workmanager_worklog_export.jsp(70,79) name = format type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fwrite_005f8.setFormat("");
      int _jspx_eval_bean_005fwrite_005f8 = _jspx_th_bean_005fwrite_005f8.doStartTag();
      if (_jspx_th_bean_005fwrite_005f8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fformat_005fnobody.reuse(_jspx_th_bean_005fwrite_005f8);
      _jspx_th_bean_005fwrite_005f8_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fwrite_005f8, _jsp_getInstanceManager(), _jspx_th_bean_005fwrite_005f8_reused);
    }
    return false;
  }
}
