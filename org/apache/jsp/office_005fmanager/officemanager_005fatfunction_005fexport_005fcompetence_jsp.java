/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:01:43 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.office_005fmanager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.system.vo.usermanager.CompetenceVO;
import java.text.DateFormat;
import java.util.Locale;

public final class officemanager_005fatfunction_005fexport_005fcompetence_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-logic.tld", Long.valueOf(1499751390000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.util.Locale");
    _jspx_imports_classes.add("com.js.system.vo.usermanager.CompetenceVO");
    _jspx_imports_classes.add("java.text.DateFormat");
  }

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
    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
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
      response.setContentType("application/vnd.ms-excel;charset=GBK");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n");

response.setHeader("Content-disposition", "attachment;filename="+new String("资格证书".getBytes("GBK"),"iso8859-1")+".xls");

      out.write("\r\n<html>\r\n<head>\r\n    <title>导出</title>\r\n</head>\r\n<body>\r\n<table border=\"1\">\r\n    <tr>\r\n        <td align=\"center\"><b>姓名</b></td>\r\n        <td align=\"center\"><b>组织</b></td>\r\n        <td align=\"center\"><b>证书名称</b></td>\r\n        <td align=\"center\"><b>开始日期</b></td>\r\n        <td align=\"center\"><b>结束日期</b></td>\r\n        <td align=\"center\"><b>颁发单位</b></td>\r\n    </tr>\r\n    ");
CompetenceVO empVO = null;
    Object[] empObj = null;
	String empName = "";
    String orgName = "";
    DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.CHINESE);
      out.write("\r\n    ");
      //  logic:iterate
      org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_005fiterate_005f0 = (org.apache.struts.taglib.logic.IterateTag) _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid.get(org.apache.struts.taglib.logic.IterateTag.class);
      boolean _jspx_th_logic_005fiterate_005f0_reused = false;
      try {
        _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
        _jspx_th_logic_005fiterate_005f0.setParent(null);
        // /office_manager/officemanager_atfunction_export_competence.jsp(28,4) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_logic_005fiterate_005f0.setId("empList");
        // /office_manager/officemanager_atfunction_export_competence.jsp(28,4) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_logic_005fiterate_005f0.setName("empList");
        // /office_manager/officemanager_atfunction_export_competence.jsp(28,4) name = scope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_logic_005fiterate_005f0.setScope("request");
        int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
        if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          java.lang.Object empList = null;
          if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
            out = org.apache.jasper.runtime.JspRuntimeLibrary.startBufferedBody(_jspx_page_context, _jspx_th_logic_005fiterate_005f0);
          }
          empList = (java.lang.Object) _jspx_page_context.findAttribute("empList");
          do {
            out.write("\r\n        ");

        empObj = (Object[]) empList;
        empVO = (CompetenceVO) empObj[0];
		empName = empObj[1]==null?"&nbsp;":empObj[1].toString();
        orgName = empObj[3]==null?"&nbsp;":empObj[3].toString();
		
            out.write("\r\n        <tr>\r\n            <td align=\"left\">");
            out.print(empName);
            out.write("&nbsp;</td>\r\n            <td align=\"left\">");
            out.print(orgName);
            out.write("&nbsp;</td>\r\n            <td align=\"left\">");
            out.print(empVO.getCertificateName()==null?"&nbsp;":empVO.getCertificateName());
            out.write("&nbsp;</td>\r\n            <td align=\"left\">");
            out.print(empVO.getBeginDate()==null?"&nbsp;":df.format(empVO.getBeginDate()));
            out.write("&nbsp;</td>\r\n            <td align=\"left\">");
            out.print(empVO.getEndDate()==null?"&nbsp;":df.format(empVO.getEndDate()));
            out.write("&nbsp;</td>\r\n            <td align=\"left\">");
            out.print(empVO.getAwardUnits()==null?"&nbsp;":empVO.getAwardUnits());
            out.write("&nbsp;</td>      \r\n        </tr>\r\n    ");
            int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
            empList = (java.lang.Object) _jspx_page_context.findAttribute("empList");
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
      out.write("\r\n</table>\r\n</body>\r\n</html>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
