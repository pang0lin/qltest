/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:47:27 UTC
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

public final class applicationReportFormsSearch_jsp extends org.apache.jasper.runtime.HttpJspBase
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

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname;
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
    _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.release();
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

String searchType = request.getParameter("searchType");
String stockName = request.getAttribute("stockName")==null?"":request.getAttribute("stockName").toString();
String domainId = session.getAttribute("domainId").toString();
String deptId = request.getAttribute("deptId")==null?"":request.getAttribute("deptId").toString();
String deptName = request.getAttribute("deptName")==null?"":request.getAttribute("deptName").toString();
String goodsType = request.getAttribute("goodsType")==null?"":request.getAttribute("goodsType").toString();
String searchCheckFlag = request.getParameter("searchCheckFlag");

String searchStock = request.getParameter("searchStock");
String searchGoodsname = request.getParameter("searchGoodsname");
String searchGoodsType = request.getParameter("searchGoodsType");

String searchStartDate = request.getAttribute("startDate").toString();
String searchEndDate = request.getAttribute("endDate").toString();

String ly_person = request.getParameter("ly_person");

DecimalFormat decimalFormat = new DecimalFormat();
decimalFormat.applyPattern("0.00");
DecimalFormat slFormat = new DecimalFormat();
 slFormat.applyPattern("0.00");

      out.write("\r\n<html>\r\n<head>\r\n<title>办公用品申请统计表</title>\r\n<meta http-equiv=Content-Type content=\"text/html; charset=GBK\">\r\n<meta name=ProgId content=Excel.Sheet>\r\n<meta name=Generator content=\"Microsoft Excel 9\">\r\n<style><!--\r\n.head\r\n\t{font-size:20.0pt;\r\n\tfont-weight:700;\r\n\ttext-align:center;}\r\n-->\r\n</style>\r\n<script language=\"javascript\">\r\n<!--\r\n//self.moveTo(0,0);\r\n//self.resizeTo(screen.availWidth,screen.availHeight);\r\n//-->\r\n</script>\r\n</head>\r\n<body>\r\n<table width=\"95%\" border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"0\">\r\n  <tr>\r\n    <td height=\"23\" align=\"center\" class=\"head\">办公用品申请统计表</td>\r\n\t<td align=\"center\" width=\"10%\"><button  class=\"btnButton2Font\" onclick=\"JSMainWinOpen('/jsoa/ReportFormsAction.do?flag=search&searchType=");
      out.print(searchType);
      out.write("&searchStock=");
      out.print(searchStock);
      out.write("&searchGoodsType=");
      out.print(searchGoodsType);
      out.write("&searchStartDate=");
      out.print(searchStartDate);
      out.write("&searchEndDate=");
      out.print(searchEndDate);
      out.write("&searchGoodsname=");
      out.print(searchGoodsname);
      out.write("&searchCheckFlag=");
      out.print(searchCheckFlag);
      out.write("&applyOrg=");
      out.print(deptId);
      out.write("&applyOrgName=");
      out.print(deptName);
      out.write("&ly_person=");
      out.print(ly_person);
      out.write("&export=1')\">导出</button></td>\r\n  </tr>\r\n</table>\r\n  ");

  Calendar now = Calendar.getInstance();
  DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.CHINESE);

  //Vector lydw = new Vector();
 // lydw.addElement("");
 // float total_money = 0, before_total = 0, unit_total = 0, ss_money = 0;
 // DecimalFormat decimalFormat = new DecimalFormat("#.00");


 String sign = "";
 if(searchCheckFlag.equals("0")){
    sign="审核未领用";
 }else if(searchCheckFlag.equals("N")){
    sign="待审核";
 }else if(searchCheckFlag.equals("Y")){
    sign="已审核";
 }else if(searchCheckFlag.equals("B")){
    sign="退回";
 }else{
    sign="已领用";
 }
  
      out.write("\r\n  <table width=\"95%\" border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"0\">\r\n  <tr>\r\n    <td align=\"center\">");
      out.print(goodsType);
      out.write("&nbsp;&nbsp;&nbsp;&nbsp;日期：");
      out.print(df.format(new Date(searchStartDate)));
      out.write("&nbsp;——&nbsp;");
      out.print(df.format(new Date(searchEndDate)));
      out.write("&nbsp;&nbsp;</td>\r\n\t<td></td><td>仓库:");
      out.print(stockName);
      out.write("</td><td>领用单位:");
      out.print(deptName);
      out.write("</td>\r\n  </tr>\r\n</table>\r\n<table width=\"95%\" border=\"1\" align=\"center\" cellpadding=\"1\" cellspacing=\"0\" bordercolorlight=\"#000000\" bordercolordark=\"#FFFFFF\">\r\n\r\n  <tr align=\"center\">\r\n    <td nowrap>出库日期</td>\r\n    <td nowrap>申请人</td>\r\n    <td nowrap>单据号</td>\r\n    <td nowrap>物品代码</td>\r\n    <td nowrap>物品名称</td>\r\n    <td nowrap>数量</td>\r\n    <td nowrap>单位</td>\r\n\t<td nowrap>单价(元)</td>\r\n\t<td nowrap>金额(元)</td>\r\n  </tr>\r\n  ");
double sumMoney = 0;
      out.write("\r\n  ");
Object[] reportObj = null;
      out.write("\r\n  ");
      //  logic:present
      org.apache.struts.taglib.logic.PresentTag _jspx_th_logic_005fpresent_005f0 = (org.apache.struts.taglib.logic.PresentTag) _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.get(org.apache.struts.taglib.logic.PresentTag.class);
      boolean _jspx_th_logic_005fpresent_005f0_reused = false;
      try {
        _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
        _jspx_th_logic_005fpresent_005f0.setParent(null);
        // /routine/resource_manager/applicationReportFormsSearch.jsp(102,2) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_logic_005fpresent_005f0.setName("drawGoodsList");
        int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
        if (_jspx_eval_logic_005fpresent_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n\r\n  ");
            //  logic:iterate
            org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_005fiterate_005f0 = (org.apache.struts.taglib.logic.IterateTag) _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid.get(org.apache.struts.taglib.logic.IterateTag.class);
            boolean _jspx_th_logic_005fiterate_005f0_reused = false;
            try {
              _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
              _jspx_th_logic_005fiterate_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fpresent_005f0);
              // /routine/resource_manager/applicationReportFormsSearch.jsp(104,2) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_logic_005fiterate_005f0.setId("drawGoodsList");
              // /routine/resource_manager/applicationReportFormsSearch.jsp(104,2) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_logic_005fiterate_005f0.setName("drawGoodsList");
              int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
              if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                java.lang.Object drawGoodsList = null;
                if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = org.apache.jasper.runtime.JspRuntimeLibrary.startBufferedBody(_jspx_page_context, _jspx_th_logic_005fiterate_005f0);
                }
                drawGoodsList = (java.lang.Object) _jspx_page_context.findAttribute("drawGoodsList");
                do {
                  out.write("\r\n  ");
reportObj = (Object[]) drawGoodsList;
                  out.write("\r\n  <tr>\r\n    <td>");
                  out.print(df.format(reportObj[8]));
                  out.write("</td>\r\n    <td>");
                  out.print(reportObj[6]);
                  out.write("</td>\r\n    <td>");
                  out.print(reportObj[7]);
                  out.write("</td>\r\n    <td>");
                  out.print(reportObj[0].toString().substring(domainId.length()+1,reportObj[0].toString().length()));
                  out.write("</td>\r\n    <td>");
                  out.print(reportObj[1]);
                  out.write("</td>\r\n    <td>");
                  out.print(slFormat.format(Double.parseDouble(reportObj[2].toString())));
                  out.write("</td>\r\n    <td>");
                  out.print(reportObj[3]);
                  out.write("</td>\r\n\t<td>");
                  out.print(decimalFormat.format(Double.parseDouble(reportObj[4].toString())));
                  out.write("</td>\r\n\t<td>");
                  out.print(decimalFormat.format(Double.parseDouble(reportObj[5].toString())));
                  out.write("</td>\r\n\t");
sumMoney = ArithUtil.add(sumMoney , Double.parseDouble(reportObj[5].toString()));
                  out.write("\r\n  </tr>\r\n  ");
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
            out.write("\r\n  ");
            int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
            if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
              break;
          } while (true);
        }
        if (_jspx_th_logic_005fpresent_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return;
        }
        _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.reuse(_jspx_th_logic_005fpresent_005f0);
        _jspx_th_logic_005fpresent_005f0_reused = true;
      } finally {
        org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_logic_005fpresent_005f0, _jsp_getInstanceManager(), _jspx_th_logic_005fpresent_005f0_reused);
      }
      out.write("\r\n\r\n  <tr align=\"center\">\r\n\t<td>合计：</td><td colspan=8 align=\"right\">");
      out.print(decimalFormat.format(sumMoney));
      out.write("</td>\r\n  </tr>\r\n</table>\r\n<table width=\"95%\" border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"0\">\r\n <tr>\r\n    <td>制表人：");
      out.print(session.getAttribute("userName"));
      out.write("&nbsp;&nbsp;&nbsp;&nbsp;制表日期：");
      out.print(df.format(now.getTime()));
      out.write("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;领用出库状态：");
      out.print(sign);
      out.write("</td>\r\n  </tr>\r\n</table>\r\n<!--\r\n<table width=\"95%\" border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"1\">\r\n        <tr>\r\n          <td> <FORM METHOD=POST ACTION=\"res_sum_list.jsp?page=1&sum_type=1&stock=100000070|||测试仓库&bg_date=1900/1/1&end_date=2004/11/30\">\r\n\t\t\t   <div>第<font color=\"#0000FF\">1/1</font>页&nbsp;&nbsp;共<font color=\"#0000FF\">1</font>条记录&nbsp;&nbsp;\r\n                每页显示记录条数\r\n<INPUT TYPE=\"text\" NAME=\"shownumber\" value=\"40\" size=\"5\"><INPUT TYPE=\"submit\" value=\"刷新\">\r\n\r\n              </div></FORM>\r\n          </td>\r\n        </tr>\r\n      </table>\r\n-->\r\n</body>\r\n</html>\r\n\r\n\r\n<script src=\"/jsoa/js/util.js\"></script>");
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