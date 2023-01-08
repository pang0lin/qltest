/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:54:25 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.tjgzw;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.List;
import com.js.oa.tjgzw.bean.TjgzwBean;
import com.js.oa.tjgzw.utils.DateTime;
import com.js.lang.Resource;
import com.js.util.config.SystemCommon;

public final class week_005flunch_005fadd_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("java.util.List");
    _jspx_imports_classes.add("com.js.lang.Resource");
    _jspx_imports_classes.add("com.js.util.config.SystemCommon");
    _jspx_imports_classes.add("com.js.oa.tjgzw.bean.TjgzwBean");
    _jspx_imports_classes.add("com.js.oa.tjgzw.utils.DateTime");
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

	String bath = request.getContextPath();
	String local = session.getAttribute(
			"org.apache.struts.action.LOCALE").toString();
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
	response.setCharacterEncoding("GBK");

      out.write("\r\n\r\n\r\n");

	DateTime dt = new DateTime();

	//默认 当前周
	String monday = dt.getMondayOfweek();
	String sunday = dt.getSundayOfWeek();
	String weekNum = dt.getZhouNumByTime(monday);
	
	String[] weeksTime = dt.getAllWeeksByTime(monday);
	
	
	
	
	
	//默认 下周
	String nextMonday = dt.getNextMonday();
	String nextSunday = dt.getNextSunday();
	String nextWeekNum = dt.getZhouNumByTime(nextMonday);

	String tag = request.getParameter("tag");
	String type = request.getParameter("type");
	String mondayPara = request.getParameter("monday");

	if (tag != null && tag.equals("1")) {//切换 周了
		if (type.equals("pre")) {//上周
			monday = dt.getPreviousMondayByTime(mondayPara);
			sunday = dt.getPreviousSundayByTime(mondayPara);
			weekNum = dt.getZhouNumByTime(monday);
			
			weeksTime = dt.getAllWeeksByTime(monday);
			 
			nextMonday = mondayPara;
			nextSunday = dt.getSundayByTime(mondayPara);
			nextWeekNum = dt.getZhouNumByTime(mondayPara);
		}
		if (type.equals("next")) {//下周
			monday = dt.getNextMondayByTime(mondayPara);
			sunday = dt.getNextSundayByTime(mondayPara);
			weekNum = dt.getZhouNumByTime(monday);
			
			weeksTime = dt.getAllWeeksByTime(monday);

			nextMonday = dt.getNextMondayByTime(monday);
			nextSunday = dt.getNextSundayByTime(sunday);
			nextWeekNum = dt.getZhouNumByTime(nextMonday);

		}
	}
    
	
	    //获取时间段截取字符段
	    String s=weeksTime[6];
	    String time=s.substring(0,4);

 	 TjgzwBean tjgzwBean = new TjgzwBean();
 	
 	/* tjgzwBean.getLunchInfoWeek(time,dt.getZhouNumByTime2(monday)); */
	//拿取object内容
    Object[] object=(Object[])tjgzwBean.getLunchInfoWeek(time,dt.getZhouNumByTime2(monday));
     System.out.println(object+"****");
    
     //拿取object里的午餐内容
     
     
     
     List<String[]> list=( List<String[]>)object[1];
     


      out.write("\r\n<html>\r\n<head>\r\n<title></title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\r\n<script src=\"");
      out.print(bath );
      out.write("/js/jquery-1.4.2.min.js\"></script>\r\n\r\n<style>  \r\n.datalist{  \r\n    border:1px solid #0035a3;   /* 表格边框 */  \r\n    font-family:Arial;  \r\n    border-collapse:collapse;   /* 边框重叠 */  \r\n    background-color:#eaf5ff;   /* 表格背景色 */  \r\n    font-size:14px;  \r\n}  \r\n.datalist caption{  \r\n    padding-bottom:5px;  \r\n    font:bold 1.4em;  \r\n    text-align:left;  \r\n}  \r\n.datalist th{  \r\n    border:1px solid #0035a3;   /* 行名称边框 */  \r\n    background-color:#4bacff;   /* 行名称背景色 */  \r\n    color:#FFFFFF;              /* 行名称颜色 */  \r\n    font-weight:bold;  \r\n    padding-top:4px; padding-bottom:4px;  \r\n    padding-left:12px; padding-right:12px;  \r\n    text-align:center;  \r\n}  \r\n.datalist td{  \r\n    border:1px solid #0035a3;   /* 单元格边框 */  \r\n    text-align:left;  \r\n    padding-top:4px; padding-bottom:4px;  \r\n    padding-left:10px; padding-right:10px;  \r\n}  \r\n.datalist tr.altrow{  \r\n    background-color:#c7e5ff;   /* 隔行变色 */  \r\n}  \r\n.txtcss1 {\r\n\ttext-align: left;  /* 用于文字右对齐 */\r\n} \r\n</style> \r\n<script language=\"javascript\" type=\"text/javascript\">\r\n");
      out.write("//保存继续\r\nfunction saveContinue() {\r\n\r\n}\r\n\r\n//保存退出\r\nfunction saveClose() {\r\n\r\n\r\n    \r\n}\r\n//提交\r\nfunction save(){\r\n\tvar mondaylch =  document.getElementById(\"mondaylch\").value;\r\n\tvar tuesdaylch =  document.getElementById(\"tuesdaylch\").value;\r\n\tvar wednesdaylch =  document.getElementById(\"wednesdaylch\").value;\r\n\tvar thursdaylch =  document.getElementById(\"thursdaylch\").value;\r\n\tvar fridaylch =  document.getElementById(\"fridaylch\").value;\r\n\tvar saterdaylch =  document.getElementById(\"saterdaylch\").value;\r\n\tvar sundaylch =  document.getElementById(\"sundaylch\").value;\r\n\t//不允许 都为空 \r\n\tif($.trim(mondaylch)==''&&$.trim(tuesdaylch)==''&&$.trim(wednesdaylch)==''&&$.trim(thursdaylch)==''&&$.trim(fridaylch)==''&&$.trim(saterdaylch)==''&&$.trim(sundaylch)==''){\r\n\t\talert(\"亲至少添加一个啊！\");\r\n\t\treturn false;\r\n\t}\r\n\t$(\"#form1\").submit();\r\n\t\r\n}\r\n\r\n\r\n//重置\r\nfunction resetter() {\r\n\t document.getElementById(\"mondaylch\").value='';\r\n\t document.getElementById(\"tuesdaylch\").value='';\r\n\t document.getElementById(\"wednesdaylch\").value='';\r\n\t document.getElementById(\"thursdaylch\").value='';\r\n");
      out.write("\t document.getElementById(\"fridaylch\").value='';\r\n\t document.getElementById(\"saterdaylch\").value='';\r\n\t document.getElementById(\"sundaylch\").value='';\r\n\t window.location.reload();\r\n\t\r\n}\r\n\r\nfunction toNext(){\r\n\tvar href=\"");
      out.print(bath);
      out.write("/tjgzw/week_lunch_add.jsp?tag=1&type=next&monday=");
      out.print(monday);
      out.write("\";\r\n\twindow.location.href = href;\r\n}\r\nfunction toPre(){\r\n\r\n\tvar href=\"");
      out.print(bath);
      out.write("/tjgzw/week_lunch_add.jsp?tag=1&type=pre&monday=");
      out.print(monday);
      out.write("\";\r\n\twindow.location.href = href;\r\n}\r\n\r\n</script>\r\n<body leftmargin=\"0\" topmargin=\"0\">\r\n<form action=\"/jsoa/TjgzwAction.do?type=addlunch\" method=\"post\" id=\"form1\">\r\n<div style=\"height: 90%;\">\r\n\t<div style=\"text-align: center;margin-top: 20px;\">\r\n\t\t<a onclick=\"toPre()\" href=\"javascript:void(0)\"><font color=\"#0099FF\"\r\n\t\t\tstyle=\"font-size:20px;\">上一周</font>\r\n\t\t</a>\r\n\t\t<label style=\"font-size:20px;\">");
      out.print("(" + monday + "-" + sunday + ">>" + weekNum + ")");
      out.write("</label>\r\n\t\t<a onclick=\"toNext()\" href=\"javascript:void(0)\"><font color=\"#0099FF\"\r\n\t\t\tstyle=\"font-size:20px;\">下一周</font>\r\n\t\t</a>\r\n\t</div>\r\n\t<div style=\"text-align: center;margin-top: 20px;\">\r\n\t\r\n\t\t\t<input type=\"hidden\" name=\"weekstartdate\" value=\"");
      out.print(weeksTime[0]);
      out.write("\"/><!-- 周开始日期 -->\r\n\t\t\t<input type=\"hidden\" name=\"weeksenddate\" value=\"");
      out.print(weeksTime[6]);
      out.write("\"/> <!-- 周结束日期 -->\r\n\t       <input type=\"hidden\"    name=\"id\" value=\"");
      out.print(object[0]==null?"":object[0]);
      out.write("\"/> \r\n\t       \r\n\t     ");
      out.write("\r\n\t\t\t\r\n\t<table class=\"datalist\" align=\"center\" style=\"width: 100%\">\r\n\t\t<tr>\r\n\t\t\t<td align=\"center\" style=\"width: 20%;text-align: center;\">日期</td>\r\n\t\t    <td align=\"center\" style=\"text-align: center;\">午餐</td>\r\n\t\t</tr>\r\n\t\t<tr>\r\n\t    \t<td>周一（<span>");
      out.print(weeksTime[0]);
      out.write("</span>)</td>\r\n\t\t\t<td>\r\n\t\t\t   <textarea  cols=\"4\" rows=\"4\" style=\"width: 100%\"  name=\"mondaylch\" id=\"mondaylch\">");
      out.print(object[0]!=null ? list.get(0)[1] : "暂无午餐信息" );
      out.write(" </textarea>\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\t    <tr>\r\n\t    \t<td>周二（<span>");
      out.print(weeksTime[1]);
      out.write("</span>）</td>\r\n\t\t\t<td>\r\n\t\t\t   <textarea  cols=\"4\" rows=\"4\" style=\"width: 100%\" name=\"tuesdaylch\"  id=\"tuesdaylch\">");
      out.print(object[0]!=null ? list.get(1)[1] : "暂无午餐信息"  );
      out.write("</textarea>\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\t\t<tr>\r\n\t    \t<td>周三（<span>");
      out.print(weeksTime[2]);
      out.write("</span>）</td>\r\n\t\t\t<td>\r\n\t\t\t   <textarea  cols=\"4\"  rows=\"4\" style=\"width: 100%\" name=\"wednesdaylch\" id=\"wednesdaylch\">");
      out.print(object[0]!=null ? list.get(2)[1] : "暂无午餐信息"  );
      out.write("</textarea>\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\t\t<tr>\r\n\t    \t<td>周四（<span>");
      out.print(weeksTime[3]);
      out.write("</span>）</td>\r\n\t\t\t<td>\r\n\t\t\t   <textarea  cols=\"4\"  rows=\"4\" style=\"width: 100%\" name=\"thursdaylch\" id=\"thursdaylch\">");
      out.print(object[0]!=null ? list.get(3)[1] : "暂无午餐信息"  );
      out.write("</textarea>\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\t\t<tr>\r\n\t    \t<td>周五（<span>");
      out.print(weeksTime[4]);
      out.write("</span>）</td>\r\n\t\t\t<td>\r\n\t\t\t   <textarea  cols=\"4\" rows=\"4\"  style=\"width: 100%\" name=\"fridaylch\" id=\"fridaylch\">");
      out.print(object[0]!=null ? list.get(4)[1] : "暂无午餐信息"  );
      out.write("</textarea>\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\t\t\r\n\t\t<tr style=\"display: none;\">\r\n\t    \t<td>周六（<span>");
      out.print(weeksTime[5]);
      out.write("</span>）</td>\r\n\t\t\t<td>\r\n\t\t\t   <textarea  cols=\"4\"  rows=\"4\" style=\"width: 100%\" name=\"saterdaylch\" id=\"saterdaylch\">");
      out.print(object[0]!=null ? list.get(5)[1] : "暂无午餐信息" );
      out.write("</textarea>\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\t\t<tr style=\"display: none;\">\r\n\t    \t<td>周日（<span>");
      out.print(weeksTime[6]);
      out.write("</span>)</td>\r\n\t\t\t<td>\r\n\t\t\t   <textarea  cols=\"4\"  rows=\"4\" style=\"width: 100%\" name=\"sundaylch\" id=\"sundaylch\">");
      out.print(object[0]!=null ? list.get(6)[1] : "暂无午餐信息"  );
      out.write("</textarea>\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\t\t\r\n\t</table>\r\n</div>\r\n<div align=\"left\" style=\"margin-left: 20px;margin-top: 10px\">\r\n\t<input type=\"button\" value=\"保存\" onclick=\"save()\"/>\r\n    <input type=\"button\" value=\"重置\" onclick=\"resetter()\"/>\r\n    ");
      out.write("\r\n</div>\r\n</div>\r\n</form>\r\n</body>\r\n</html>\r\n");
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