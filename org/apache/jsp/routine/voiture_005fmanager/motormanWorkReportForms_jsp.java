/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:47:38 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.routine.voiture_005fmanager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class motormanWorkReportForms_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes = null;
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhtml;
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
    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fhtml.release();
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
      //  html:html
      org.apache.struts.taglib.html.HtmlTag _jspx_th_html_005fhtml_005f0 = (org.apache.struts.taglib.html.HtmlTag) _005fjspx_005ftagPool_005fhtml_005fhtml.get(org.apache.struts.taglib.html.HtmlTag.class);
      boolean _jspx_th_html_005fhtml_005f0_reused = false;
      try {
        _jspx_th_html_005fhtml_005f0.setPageContext(_jspx_page_context);
        _jspx_th_html_005fhtml_005f0.setParent(null);
        int _jspx_eval_html_005fhtml_005f0 = _jspx_th_html_005fhtml_005f0.doStartTag();
        if (_jspx_eval_html_005fhtml_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n\r\n<head>\r\n<link href=\"skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<link rel=stylesheet type=\"text/css\" href=\"style/cssmain.css\">\r\n<title>司机工作情况统计</title>\r\n<body leftmargin=\"0\" topmargin=\"20\" >\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td>\r\n");

String beginDate="";
String endDate="";
if(request.getParameter("beginDate")!=null)
beginDate=request.getParameter("beginDate").toString();
if(request.getParameter("endDate")!=null)
endDate=request.getParameter("endDate").toString();

java.util.Date now =new java.util.Date();
String tmpNow=(now.getYear()+1900)+"-"+(now.getMonth()+1)+"-"+now.getDate();

            out.write("\r\n<table width=\"95%\" border=\"1\" align=\"center\" cellpadding=\"2\" cellspacing=\"0\" bordercolorlight=\"#000000\" bordercolordark=\"#FFFFFF\">\r\n<caption>汇总时间:从");
            out.print(beginDate);
            out.write('到');
            out.print(endDate);
            out.write("</caption><br>\r\n<div align=\"center\"><H1>司机工作情况统计</H1></div>\r\n<div id=\"tagPrint\" align=\"right\"><!--<A href=\"javascript:void(0);\" ><span  style=\"cursor:pointer\" onclick=\"printWindow()\">打印</span></A>-->\r\n    <button class=\"btnButton2Font\" onclick=\"printWindow();\">打印</button>\r\n</div>\r\n                      <tr align=\"center\">\r\n                        <td  > 出车司机 </td>\r\n                        <td  > 实际公里数 </td>\r\n                        <td   >\r\n\t\t\t\t\t\t<font color=\"#000000\" >出车用时（小时数） </td>\r\n                        <td  > 用车次数 </td>\r\n                        <td  > 节假日出车次数 </td>\r\n\t\t\t\t\t\t<td   > 误餐补贴 </td>\r\n\t\t\t\t\t\t<td   > 其它补贴 </td>\r\n\t\t\t\t\t\t<td >\r\n\t\t\t\t\t\t<font color=\"#000000\" >国家规定节假日加班时间 </td>\r\n\r\n\t\t\t\t\t\t<td   > 周六日加班时间 </td>\r\n\t\t\t\t\t\t<td   > 平时加班时间 </td>\r\n\r\n                      </tr>\r\n\r\n\r\n                      ");

					  int listorder=0;
					  String motorman="";

					  double sendStartKilo=0;
					  double sendEndKilo=0;
					  double oneTotalKilo=0;



					  double sendStartTime=0;
					  double sendEndTime=0;
					  double oneTotalTime=0;

					  int sendCount=0;
					  int sendHolidayCount=0;
					  double overTimeHoliay=0;
					  double misMealFee=0;
					  double overTimeWeekend=0;
					  double overTime=0;
					  java.text.DecimalFormat fmt=new java.text.DecimalFormat("##.0000");

					  java.text.DecimalFormat fmt2=new java.text.DecimalFormat("0.00");
					  double otherAllowance = 0;

					  
            out.write("\r\n\t\t\t");
            //  logic:iterate
            org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_005fiterate_005f0 = (org.apache.struts.taglib.logic.IterateTag) _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid.get(org.apache.struts.taglib.logic.IterateTag.class);
            boolean _jspx_th_logic_005fiterate_005f0_reused = false;
            try {
              _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
              _jspx_th_logic_005fiterate_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
              // /routine/voiture_manager/motormanWorkReportForms.jsp(79,3) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_logic_005fiterate_005f0.setId("motormanWorkReportFormsList");
              // /routine/voiture_manager/motormanWorkReportForms.jsp(79,3) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_logic_005fiterate_005f0.setName("motormanWorkReportFormsList");
              // /routine/voiture_manager/motormanWorkReportForms.jsp(79,3) name = scope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_logic_005fiterate_005f0.setScope("request");
              int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
              if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                java.lang.Object motormanWorkReportFormsList = null;
                if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = org.apache.jasper.runtime.JspRuntimeLibrary.startBufferedBody(_jspx_page_context, _jspx_th_logic_005fiterate_005f0);
                }
                motormanWorkReportFormsList = (java.lang.Object) _jspx_page_context.findAttribute("motormanWorkReportFormsList");
                do {
                  out.write("\r\n     \t\t     \t ");

                        Object[] obj = (Object[])motormanWorkReportFormsList;
                      
                  out.write("\r\n                          ");

						  int len=0;
                          listorder=listorder+1;
                          if(obj[0]!=null){
							  motorman=obj[0].toString();
							  if(obj[1]!=null)
							  sendStartKilo=Double.parseDouble(obj[1].toString());
							  if(obj[2]!=null)
							  sendEndKilo=Double.parseDouble(obj[2].toString());
							  oneTotalKilo=sendEndKilo-sendStartKilo;



							  if(obj[3]!=null)
							  sendStartTime=Double.parseDouble(obj[3].toString());
							  if(obj[4]!=null)
							  sendEndTime=Double.parseDouble(obj[4].toString());



							  oneTotalTime=sendEndTime-sendStartTime;

							  if(obj[5]!=null)
							  sendCount=Integer.parseInt(obj[5].toString());
							  if(obj[6]!=null)
							  sendHolidayCount=Integer.parseInt(obj[6].toString());
							  if(obj[7]!=null)
							  overTimeHoliay=Double.parseDouble(obj[7].toString());
							  if(obj[8]!=null)
							  misMealFee=Double.parseDouble(obj[8].toString());
							  if(obj[9]!=null)
							  overTimeWeekend=Double.parseDouble(obj[9].toString());
							  if(obj[10]!=null)
							  overTime=Double.parseDouble(obj[10].toString());
							  if(obj[11]!=null)
							  otherAllowance =Double.parseDouble(obj[11].toString());


                          
                  out.write("\r\n                        <tr >\r\n\r\n                          <td>");
                  out.print(motorman);
                  out.write("</td>\r\n                          <td>");
                  out.print(fmt2.format(oneTotalKilo));
                  out.write("</td>\r\n\t\t\t\t\t\t  <td>");
                  out.print(fmt2.format(oneTotalTime/(1000*60*60)));
                  out.write("</td>\r\n\t\t\t\t\t\t  <td>");
                  out.print(sendCount);
                  out.write("</td>\r\n\t\t\t\t\t\t  <td>");
                  out.print(sendHolidayCount);
                  out.write("</td>\r\n\t\t\t\t\t\t  <td>");
                  out.print(fmt2.format(misMealFee));
                  out.write("</td>\r\n\t\t\t\t\t\t  <td>");
                  out.print(fmt2.format(otherAllowance));
                  out.write("</td>\r\n\t\t\t\t\t\t  <td>");
                  out.print(fmt2.format(overTimeHoliay));
                  out.write("</td>\r\n\r\n\t\t\t\t\t\t   <td>");
                  out.print(fmt2.format(overTimeWeekend));
                  out.write("</td>\r\n\t\t\t\t\t\t    <td>");
                  out.print(fmt2.format(overTime));
                  out.write("</td>\r\n                        </tr>\r\n                        ");

						 motorman="";

					   sendStartKilo=0;
					   sendEndKilo=0;
					   oneTotalKilo=0;



					   sendStartTime=0;
					   sendEndTime=0;
					   oneTotalTime=0;

					   sendCount=0;
					   sendHolidayCount=0;
					   overTimeHoliay=0;
					   misMealFee=0;
					   overTimeWeekend=0;
					   overTime=0;
						  }
						  
                  out.write("\r\n           ");
                  int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
                  motormanWorkReportFormsList = (java.lang.Object) _jspx_page_context.findAttribute("motormanWorkReportFormsList");
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
            out.write("\r\n\r\n                  </table>\r\n<script language=\"javascript\">\r\n//打印\r\nfunction printWindow(){\r\ntagPrint.style.display=\"none\";\r\nwindow.print();\r\n}\r\n</script>\r\n</td></tr></table>\r\n</body>\r\n");
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
      out.write("\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
