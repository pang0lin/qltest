/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:47:09 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.routine.resource_005fmanager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import com.js.util.math.ArithUtil;

public final class tkdReport_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-logic.tld", Long.valueOf(1499751390000L));
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
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.util.math.ArithUtil");
    _jspx_imports_classes.add("java.text.DecimalFormat");
    _jspx_imports_classes.add("java.text.DateFormat");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid;

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
    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid.release();
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n<html>\r\n<head>\r\n<title>领用退库表</title>\r\n<meta http-equiv=Content-Type content=\"text/html; charset=GBK\">\r\n<meta name=ProgId content=Excel.Sheet>\r\n<meta name=Generator content=\"Microsoft Excel 9\">\r\n<style><!--\r\n.head\r\n\t{font-size:20.0pt;\r\n\tfont-weight:700;\r\n\ttext-align:center;}\r\n-->\r\n</style>\r\n<script language=\"javascript\">\r\n<!--\r\n//self.moveTo(0,0);\r\n//self.resizeTo(screen.availWidth,screen.availHeight);\r\n//-->\r\n</script>\r\n</head>\r\n<body>\r\n");

String searchType = request.getParameter("searchType");
String showTitle = searchType.equals("1")?"分品种":"分单位";
showTitle = searchType.equals("3")?"分单位费用分摊表":showTitle;
showTitle = searchType.equals("4")?"物品流向统计表":showTitle;

  Calendar now = Calendar.getInstance();
  DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.CHINESE);
  String searchStartDate = request.getParameter("searchStartDate");
  String searchEndDate = request.getParameter("searchEndDate");
  String searchStock = request.getParameter("searchStock");
  String searchGoodsType = request.getParameter("searchGoodsType");
  Vector lydw = new Vector();
  lydw.addElement("");
  //float total_money = 0, before_total = 0, unit_total = 0, ss_money = 0;
  double total_money = 0, before_total = 0, unit_total = 0, ss_money = 0;
  DecimalFormat decimalFormat = new DecimalFormat();
  decimalFormat.applyPattern("0.00");
  DecimalFormat slFormat = new DecimalFormat();
 slFormat.applyPattern("0.00");
  String goodsType = request.getAttribute("goodsType")==null?"":request.getAttribute("goodsType").toString();

      out.write("\r\n<table width=\"95%\" border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"0\">\r\n  <tr>\r\n    <td height=\"23\" colspan=\"2\" align=\"center\" class=\"head\">");
      out.print(searchStock.substring(searchStock.indexOf(",") +1));
      out.write("领用退库表</td>\r\n\t<td align=\"center\" width=\"10%\"><button  class=\"btnButton2Font\" onclick=\"JSMainWinOpen('/jsoa/ReportFormsAction.do?flag=search&searchType=");
      out.print(searchType);
      out.write("&searchStock=");
      out.print(searchStock);
      out.write("&searchGoodsType=");
      out.print(searchGoodsType);
      out.write("&searchStartDate=");
      out.print(searchStartDate);
      out.write("&searchEndDate=");
      out.print(searchEndDate);
      out.write("&export=1')\">导出</button></td>\r\n  </tr>\r\n  <tr>\r\n    <td align=\"center\"><div align=\"left\">");
      out.print(goodsType);
      out.write("&nbsp;&nbsp;&nbsp;&nbsp;日期：");
      out.print(df.format(new Date(searchStartDate)));
      out.write("&nbsp;——&nbsp;");
      out.print(df.format(new Date(searchEndDate)));
      out.write("&nbsp;&nbsp;</div></td>\r\n    <td align=\"center\" width=\"10%\">&nbsp;</td>\r\n  </tr>\r\n</table>\r\n<table width=\"95%\" border=\"1\" align=\"center\" cellpadding=\"1\" cellspacing=\"0\" bordercolorlight=\"#000000\" bordercolordark=\"#FFFFFF\">\r\n <tr>\r\n\t <td><div align=\"center\">日期</div></td>\r\n      <td><div align=\"center\">退库单号</div></td>\r\n      <td><div align=\"center\">退库部门</div></td>\r\n\t  <td><div align=\"center\">退库人</div></td>\r\n      <td><div align=\"center\">材料名称</div></td>\r\n      <td><div align=\"center\">计量单位</div></td>\r\n      <td><div align=\"center\">数量</div>      </td>\r\n      <td><div align=\"center\">单价</div></td>\r\n      <td><div align=\"center\">金额</div></td>\r\n    </tr>\r\n\r\n\t ");

	 Object[] reportObj = null;
	 double sl = 0;
	 double je = 0 ;
    
      out.write("\r\n\t ");
      //  logic:iterate
      org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_005fiterate_005f0 = (org.apache.struts.taglib.logic.IterateTag) _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid.get(org.apache.struts.taglib.logic.IterateTag.class);
      boolean _jspx_th_logic_005fiterate_005f0_reused = false;
      try {
        _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
        _jspx_th_logic_005fiterate_005f0.setParent(null);
        // /routine/resource_manager/tkdReport.jsp(79,2) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_logic_005fiterate_005f0.setId("drawGoodsList");
        // /routine/resource_manager/tkdReport.jsp(79,2) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_logic_005fiterate_005f0.setName("drawGoodsList");
        int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
        if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          java.lang.Object drawGoodsList = null;
          if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
            out = org.apache.jasper.runtime.JspRuntimeLibrary.startBufferedBody(_jspx_page_context, _jspx_th_logic_005fiterate_005f0);
          }
          drawGoodsList = (java.lang.Object) _jspx_page_context.findAttribute("drawGoodsList");
          do {
            out.write("\r\n     ");

	 reportObj = (Object[]) drawGoodsList;
     sl = ArithUtil.add(sl , Double.parseDouble(reportObj[2].toString()));
	 je = ArithUtil.add(je , Double.parseDouble(reportObj[5].toString()));
	 
            out.write("\r\n    <tr>\r\n\t\t<td>");
            out.print(df.format(reportObj[9]));
            out.write("</td>\r\n      <td>");
            out.print(reportObj[6]);
            out.write("</td>\r\n      <td>");
            out.print(reportObj[7]==null?"&nbsp;":reportObj[7]);
            out.write("&nbsp;</td>\r\n\t  <td>");
            out.print(reportObj[8]==null?"&nbsp;":reportObj[8]);
            out.write("&nbsp;</td>\r\n      <td>");
            out.print(reportObj[1]);
            out.write("</td>\r\n      <td>");
            out.print(reportObj[3]);
            out.write("</td>\r\n      <td><div align=\"right\">");
            out.print(slFormat.format(Math.abs(new Double(slFormat.format(Float.parseFloat(reportObj[2].toString()))).doubleValue())));
            out.write("</div></td>\r\n     <td><div align=\"right\">");
            out.print(decimalFormat.format(Float.parseFloat(reportObj[4].toString())));
            out.write("</div></td>\r\n      <td><div align=\"right\">");
            out.print(decimalFormat.format(Math.abs(new Double(decimalFormat.format(Float.parseFloat(reportObj[5].toString()))).doubleValue())));
            out.write("</div></td>\r\n\r\n\t  </tr>\r\n\t");
            int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
            drawGoodsList = (java.lang.Object) _jspx_page_context.findAttribute("drawGoodsList");
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
        _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
        _jspx_th_logic_005fiterate_005f0_reused = true;
      } finally {
        org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_logic_005fiterate_005f0, _jsp_getInstanceManager(), _jspx_th_logic_005fiterate_005f0_reused);
      }
      out.write("\r\n\t <tr>\r\n      <td>合计</td>\r\n      <td>&nbsp;</td>\r\n\t  <td>&nbsp;</td>\r\n      <td>&nbsp;</td>\r\n      <td>&nbsp;</td>\r\n\t  <td>&nbsp;</td>\r\n      <td><div align=\"right\">");
      out.print(slFormat.format(Math.abs(new Double(slFormat.format(sl)).doubleValue())));
      out.write("</div></td>\r\n      <td>&nbsp;</td>\r\n      <td><div align=\"right\">");
      out.print(decimalFormat.format(Math.abs(new Double(decimalFormat.format(je)).doubleValue())));
      out.write("</div></td>\r\n    </tr>\r\n</table>\r\n<table width=\"95%\" border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"0\">\r\n <tr>\r\n  <td>\r\n    制表人：");
      out.print(session.getAttribute("userName"));
      out.write("&nbsp;&nbsp;&nbsp;&nbsp;制表日期：");
      out.print(df.format(now.getTime()));
      out.write("\r\n </td>\r\n  </tr>\r\n</table>\r\n</body>\r\n</html>\r\n\r\n\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
