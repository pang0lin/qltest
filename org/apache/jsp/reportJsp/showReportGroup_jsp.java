/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:54:33 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.reportJsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.io.*;
import java.util.*;
import com.runqian.report4.usermodel.Context;
import com.runqian.report4.util.ReportUtils;
import com.runqian.report4.model.ReportDefine;
import com.runqian.report4.usermodel.ReportGroup;
import com.runqian.report4.usermodel.SubReportConfig;

public final class showReportGroup_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/WEB-INF/runqianReport4.tld", Long.valueOf(1499751390000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("java.io");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.runqian.report4.usermodel.ReportGroup");
    _jspx_imports_classes.add("com.runqian.report4.model.ReportDefine");
    _jspx_imports_classes.add("com.runqian.report4.usermodel.Context");
    _jspx_imports_classes.add("com.runqian.report4.util.ReportUtils");
    _jspx_imports_classes.add("com.runqian.report4.usermodel.SubReportConfig");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005freport_005fparam_0026_005fparams_005fparamFileName_005fneedSubmit_005fname_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005freport_005fgroup_0026_005fparams_005fisInput_005fgroupFileName_005ffuncBarLocation_005fexceptionPage_005fnobody;

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
    _005fjspx_005ftagPool_005freport_005fparam_0026_005fparams_005fparamFileName_005fneedSubmit_005fname_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005freport_005fgroup_0026_005fparams_005fisInput_005fgroupFileName_005ffuncBarLocation_005fexceptionPage_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005freport_005fparam_0026_005fparams_005fparamFileName_005fneedSubmit_005fname_005fnobody.release();
    _005fjspx_005ftagPool_005freport_005fgroup_0026_005fparams_005fisInput_005fgroupFileName_005ffuncBarLocation_005fexceptionPage_005fnobody.release();
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n<html>\r\n<body topmargin=0 leftmargin=0 rightmargin=0 bottomMargin=0>\r\n");

	request.setCharacterEncoding( "GBK" );
	String report = request.getParameter( "rpg" );
	String reportFileHome=Context.getInitCtx().getMainDir();
	StringBuffer param=new StringBuffer();
	
	//??????????????????????????????
	int iTmp = 0;
	if( (iTmp = report.lastIndexOf(".rpg")) <= 0 ){
		report = report + ".rpg";
		iTmp = 0;
	}
	//????????????????????????
	String isinput="";
	String reportPath = request.getRealPath("/reportFiles/"+report);
	ReportGroup rg=null;
			rg = ReportUtils.readReportGroup(reportPath);
			ReportDefine rd =(ReportDefine) rg.getReportMetaData().getSubReportConfig(0).getSubReportDefine();
			byte urltype=rg.getReportMetaData().getSubReportConfig(0).getURLType();
			if(rd==null){
			   if(urltype== SubReportConfig.TYPE_ABSOLUTE){
				   String url = rg.getReportMetaData().getSubReportConfig(0).getURL();
				   ReportDefine rd2 = (ReportDefine)ReportUtils.read(url);
				   System.out.println(rd2);
				   if(rd2.getInput()==ReportDefine.INPUT_NORMAL){
						isinput="yes";
						System.out.print(isinput);
					}else{
						isinput="no";
						System.out.print(isinput);
					}
				  
			   }else{
				  String url =   application.getRealPath("/reportFiles/"+rg.getReportMetaData().getSubReportConfig(0).getURL()) ;
                   ReportDefine rd2 = (ReportDefine)ReportUtils.read(url);
				   System.out.println(rd2);
				   if(rd2.getInput()==ReportDefine.INPUT_NORMAL){
						isinput="yes";
						System.out.print(isinput);
					}else{
						isinput="no";
						System.out.print(isinput);
					}
			   }
			}else{
				if(rd.getInput()==ReportDefine.INPUT_NORMAL){
					isinput="yes";
					System.out.print(isinput);
				}else{
					isinput="no";
					System.out.print(isinput);
				}
			}

	Enumeration paramNames = request.getParameterNames();
	if(paramNames!=null){
		while(paramNames.hasMoreElements()){
			String paramName = (String) paramNames.nextElement();
			String paramValue=request.getParameter(paramName);
			if(paramValue!=null){
				//???????????????name=value;name2=value2;.....?????????
				param.append(paramName).append("=").append(paramValue).append(";");
			}
		}
	}

	//???????????????????????????????????????????????????????????????
	String paramFile = report.substring(0,iTmp)+"_arg.raq";
	File f=new File(application.getRealPath(reportFileHome+ File.separator +paramFile));


      out.write('\r');
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "grouptoolbar.jsp", out, false);
      out.write("\r\n<table id=rpt align=center><tr><td>\r\n");
	//????????????????????????????????????????????????
	if( f.exists() ) {
	
      out.write("\r\n\t<table id=param_tbl><tr><td>\r\n\t\t");
      //  report:param
      com.runqian.report4.tag.ParamTag _jspx_th_report_005fparam_005f0 = (com.runqian.report4.tag.ParamTag) _005fjspx_005ftagPool_005freport_005fparam_0026_005fparams_005fparamFileName_005fneedSubmit_005fname_005fnobody.get(com.runqian.report4.tag.ParamTag.class);
      boolean _jspx_th_report_005fparam_005f0_reused = false;
      try {
        _jspx_th_report_005fparam_005f0.setPageContext(_jspx_page_context);
        _jspx_th_report_005fparam_005f0.setParent(null);
        // /reportJsp/showReportGroup.jsp(90,2) name = name type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_report_005fparam_005f0.setName("form1");
        // /reportJsp/showReportGroup.jsp(90,2) name = paramFileName type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_report_005fparam_005f0.setParamFileName(paramFile);
        // /reportJsp/showReportGroup.jsp(90,2) name = needSubmit type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_report_005fparam_005f0.setNeedSubmit("no");
        // /reportJsp/showReportGroup.jsp(90,2) name = params type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_report_005fparam_005f0.setParams(param.toString());
        int _jspx_eval_report_005fparam_005f0 = _jspx_th_report_005fparam_005f0.doStartTag();
        if (_jspx_th_report_005fparam_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return;
        }
        _005fjspx_005ftagPool_005freport_005fparam_0026_005fparams_005fparamFileName_005fneedSubmit_005fname_005fnobody.reuse(_jspx_th_report_005fparam_005f0);
        _jspx_th_report_005fparam_005f0_reused = true;
      } finally {
        org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_report_005fparam_005f0, _jsp_getInstanceManager(), _jspx_th_report_005fparam_005f0_reused);
      }
      out.write("\r\n\t</td>\r\n\t<td><a href=\"javascript:_submit( form1 )\"><img src=\"../images/query.jpg\" border=no style=\"vertical-align:middle\"></a></td>\r\n\t</tr></table>\r\n\t");
 }

      out.write("\r\n<table align=center>\r\n\t<tr><td>\r\n\t\t");
      //  report:group
      com.runqian.report4.tag.GroupTag _jspx_th_report_005fgroup_005f0 = (com.runqian.report4.tag.GroupTag) _005fjspx_005ftagPool_005freport_005fgroup_0026_005fparams_005fisInput_005fgroupFileName_005ffuncBarLocation_005fexceptionPage_005fnobody.get(com.runqian.report4.tag.GroupTag.class);
      boolean _jspx_th_report_005fgroup_005f0_reused = false;
      try {
        _jspx_th_report_005fgroup_005f0.setPageContext(_jspx_page_context);
        _jspx_th_report_005fgroup_005f0.setParent(null);
        // /reportJsp/showReportGroup.jsp(102,2) name = groupFileName type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_report_005fgroup_005f0.setGroupFileName(report);
        // /reportJsp/showReportGroup.jsp(102,2) name = funcBarLocation type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_report_005fgroup_005f0.setFuncBarLocation("top");
        // /reportJsp/showReportGroup.jsp(102,2) name = exceptionPage type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_report_005fgroup_005f0.setExceptionPage("/reportJsp/myError2.jsp");
        // /reportJsp/showReportGroup.jsp(102,2) name = isInput type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_report_005fgroup_005f0.setIsInput(isinput);
        // /reportJsp/showReportGroup.jsp(102,2) name = params type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_report_005fgroup_005f0.setParams(param.toString());
        int _jspx_eval_report_005fgroup_005f0 = _jspx_th_report_005fgroup_005f0.doStartTag();
        if (_jspx_th_report_005fgroup_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return;
        }
        _005fjspx_005ftagPool_005freport_005fgroup_0026_005fparams_005fisInput_005fgroupFileName_005ffuncBarLocation_005fexceptionPage_005fnobody.reuse(_jspx_th_report_005fgroup_005f0);
        _jspx_th_report_005fgroup_005f0_reused = true;
      } finally {
        org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_report_005fgroup_005f0, _jsp_getInstanceManager(), _jspx_th_report_005fgroup_005f0_reused);
      }
      out.write("\r\n\t</td></tr>\r\n</table>\r\n\r\n</body>\r\n</html>\r\n");
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
