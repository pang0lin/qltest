/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:03:36 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.messageWall;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.text.SimpleDateFormat;
import java.util.*;
import com.js.oa.messageWall.po.MessageWallPO;

public final class messageWall_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.text.SimpleDateFormat");
    _jspx_imports_classes.add("com.js.oa.messageWall.po.MessageWallPO");
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

      out.write("\r\n\r\n\r\n\r\n");

	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
	
 
      out.write("\r\n<html>\r\n  <head>\r\n    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n    <title>留言墙</title>\r\n    <!-- \r\n\t<link rel=\"stylesheet\" href=\"/jsoa/css/messageWall/reset.css\"/>\r\n\t -->\r\n\t<link rel=\"stylesheet\" href=\"/jsoa/css/messageWall/index.css\"/>\r\n\t<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject1.css\">\r\n\t<script type=\"text/javascript\" src=\"/jsoa/js/messageWall/jquery-1.10.2.js\"></script>\r\n\t<script type=\"text/javascript\" src=\"/jsoa/js/messageWall/jquery-ui-1.10.2.custom.js\"></script>\r\n\t<script type=\"text/javascript\" src=\"/jsoa/js/messageWall/index.js\" charset=\"GBK\"></script>\r\n\t<style type=\"text/css\">\r\n\t.hidden{\r\n\t\tdisplay: none;\r\n\t\twidth: 100%;\r\n\t\tmargin-bottom : 20px;\r\n\t\tfloat: right;\r\n\t\ttext-align: right;\r\n\t\toverflow: hidden;\r\n\t}\r\n\t/*留言内容*/\r\n\t.c{\r\n\t\tdisplay: block;\r\n\t\toverflow: hidden;\r\n\t\twidth: 130px;\r\n\t\theight: 105px;\r\n\t\ttext-align: left;\r\n\t\tborder-top: 1px solid;\r\n\t\tpadding-top: 5px;\r\n\t\twhite-space: pre-line;\r\n\t}\r\n\t.n{\r\n\t\ttext-align: left;\r\n\t\tpadding-left: 10px;\r\n\t\tfloat: left;\r\n\t\twidth: 140px;\r\n\t}\r\n\t.t{\r\n\t\tdisplay: inline;\r\n\t\ttext-align: center;\r\n\t}\r\n\t</style>\r\n\t<script type=\"text/javascript\">\r\n\t//修改留言内容\r\n\tfunction modify(id){\r\n\t\tvar reg=new RegExp(\"<BR>\",\"gi\");\r\n\t\tvar reg2=new RegExp(\"&nbsp;\",\"gi\");\r\n");
      out.write("\t\tvar text = $(\"#\" + id).html();\r\n\t\tvar $write = $(\"#write\"),\r\n\t\t\t$mask = $(\".mask\"),\r\n        \t$popBox = $(\".pop-box\");\r\n        $(\"#num\").val(id);\r\n        text = text.replace(reg,\"\\n\");\r\n        text = text.replace(reg2,\" \");\r\n\t\t$write.val(text);\r\n\t\t//$mask.fadeIn();\r\n\t\t//$popBox.animate({top: \"50%\"});\r\n\t\t$mask.show();\r\n\t\t$popBox.show();\r\n\t}\r\n\t\r\n\t//鼠标进入div时，显示删除按钮\r\n\tfunction mousein(id){\r\n\t\t$(\"#\" + id).show();\r\n\t}\r\n\t//鼠标进入div时，当前div显示在最上层\r\n\tfunction cur(id){\r\n\t\t$(\"#\" + id).css({\"z-index\":\"1000\"});\r\n\t}\r\n\t//鼠标离开div时，隐藏删除按钮\r\n\tfunction mouseout(id){\r\n\t\t$(\"#\" + id).hide();\r\n\t}\r\n\t//鼠标离开div时，div恢复到原来位置\r\n\tfunction out(id){\r\n\t\t$(\"#\" + id).css({\"z-index\":\"99\"});\r\n\t}\r\n\t//点击删除按钮时，删除数据，隐藏div\r\n\tfunction del(id){\r\n\t\tif(confirm(\"确认要删除留言吗？\")){\r\n\t\t\t$.post(\"/jsoa/messageWallAction.do?method=delete\", {\r\n\t\t\t\tmessagewallId : id\r\n\t\t\t}, function(returnData){\r\n\t\t\t\tif(\"0\" != returnData){//删除成功\r\n\t\t\t\t\t$(\"#\" + id).hide();\r\n\t\t\t\t}\r\n\t\t\t}, \"text\");\r\n\t\t}\r\n\t}\r\n\t\r\n\t\r\n\t//窗口大小改变事件\r\n\twindow.onresize = function(){\r\n\t\tgetScreenSize();\r\n\t}\r\n\t\r\n\tfunction showSearch(obj){\r\n");
      out.write("\t\tif(document.getElementById(\"searchbar\").style.display==\"none\"){\r\n\t\t\tdocument.getElementById(\"searchbar\").style.display=\"\";\r\n\t\t\tobj.value=\"关闭查询\";\r\n\t\t}else{\r\n\t\t\tdocument.getElementById(\"searchbar\").style.display=\"none\";\r\n\t\t\tobj.value=\"查询\";\r\n\t\t}\r\n\t}\r\n\tfunction search(){\r\n\t\t$(\"#search\").submit();\r\n\t}\r\n\tfunction unsearch(){\r\n\t\twindow.location.href = \"/jsoa/messageWallAction.do?method=list\";\r\n\t}\r\n\tfunction key(code){\r\n\t\tif(code==13) search();\r\n\t}\r\n\t</script>\r\n\t<SCRIPT type=\"text/javascript\" src=\"/jsoa/public/date_picker/DateObject.js\"></SCRIPT>\r\n\t<SCRIPT type=\"text/javascript\" src=\"/jsoa/public/date_picker/DatePicker.js\"></SCRIPT>\r\n\t<SCRIPT type=\"text/javascript\" src=\"/jsoa/public/date_picker/editlib.js\"></SCRIPT>\r\n\t<SCRIPT type=\"text/javascript\" src=\"/jsoa/public/date_picker/tree.js\"></SCRIPT>\r\n  </head>\r\n  ");

	//当前用户是否有维护权限
	boolean hasAddRight = (null!=request.getAttribute("hasAddRight") && "yes".equals(request.getAttribute("hasAddRight"))) ? true : false;
	boolean isAdmin = (null!=request.getAttribute("isAdmin") && "yes".equals(request.getAttribute("isAdmin"))) ? true : false;
	long userId = null!=session.getAttribute("userId") ? Long.parseLong(session.getAttribute("userId").toString()) : 0;
 	boolean isQuery = null != request.getParameter("type");
 	
 	//日期
 	java.util.Date searchStartDate=new Date();//搜索起始日期

    int firstYear = searchStartDate.getYear()+1900;
    int firstMonth = searchStartDate.getMonth();
    int firstDay = searchStartDate.getDate();
    int nowYear = firstYear;
    int nowMonth = firstMonth+1;
    int nowDay = firstDay;
   	String searchName = null!=request.getAttribute("searchName") ? request.getAttribute("searchName").toString() : "";
    String searchContent = null!=request.getAttribute("searchContent") ? request.getAttribute("searchContent").toString() : "";
    if(isQuery){//查询时将日期赋值为上次查询的起止日期
		firstYear = null!=request.getAttribute("sy") ? Integer.parseInt(request.getAttribute("sy").toString()) : firstYear;
		firstMonth = null!=request.getAttribute("sm") ? Integer.parseInt(request.getAttribute("sm").toString()) : firstYear;
		firstDay = null!=request.getAttribute("sd") ? Integer.parseInt(request.getAttribute("sd").toString()) : firstYear;
		nowYear = null!=request.getAttribute("ey") ? Integer.parseInt(request.getAttribute("ey").toString()) : firstYear;
		nowMonth = null!=request.getAttribute("em") ? Integer.parseInt(request.getAttribute("em").toString()) : firstYear;
		nowDay = null!=request.getAttribute("ed") ? Integer.parseInt(request.getAttribute("ed").toString()) : firstYear;
    }
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
   
      out.write("\r\n  <body onload=\"getScreenSize()\">\r\n  \t<input type=\"hidden\" id=\"empName\" value=\"");
      out.print(session.getAttribute("userName") );
      out.write("\">\r\n  \t<input type=\"hidden\" id=\"isAdmin\" value=\"");
      out.print(request.getAttribute("isAdmin") );
      out.write("\">\r\n  \t<center>\r\n  \t\t<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n\t\t\t<tr>\r\n\t\t\t\t<td>\r\n\t\t\t\t\t<form id=\"search\" method=\"post\" action=\"/jsoa/messageWallAction.do?method=list\">\r\n\t\t\t\t\t\t<input id=\"type\" name=\"type\" value=\"query\" type=\"hidden\">\r\n\t\t\t\t\t\t<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"1\" cellspacing=\"1\" class=\"searchbar\" id=\"searchbar\" style=\"display:none\">\r\n\t\t\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t\t    <td align=\"right\" >\r\n\t\t\t\t\t\t\t    \t留言人：<input type=\"text\" class=\"inputtext\" id=\"searchName\" name=\"searchName\" maxlength=\"20\" style=\"width:120px;\"  value=\"");
      out.print(searchName );
      out.write("\"/>\r\n\t\t\t\t\t\t\t    \t&nbsp;&nbsp;&nbsp;&nbsp;\r\n\t\t\t\t\t\t\t\t\t留言内容：<input type=\"text\" class=\"inputtext\" id=\"searchContent\" name=\"searchContent\" maxlength=\"20\" style=\"width:240px;\"  value=\"");
      out.print(searchContent );
      out.write("\">\r\n\t\t\t\t\t\t\t\t\t&nbsp;&nbsp;&nbsp;&nbsp;\r\n\t\t\t\t\t\t\t\t\t留言日期： \r\n\t\t\t\t\t\t\t\t\t<script language=\"javascript\">var dtpDate = createDatePicker(\"searchStartDate\",");
      out.print(firstYear);
      out.write(',');
      out.print(firstMonth);
      out.write(',');
      out.print(firstDay);
      out.write(");</script>\r\n\t\t\t\t\t\t\t\t\t&nbsp;至&nbsp;\r\n\t\t\t\t\t\t\t\t\t<script language=\"javascript\">var dtpDate = createDatePicker(\"searchEndDate\",");
      out.print(nowYear);
      out.write(',');
      out.print(nowMonth);
      out.write(',');
      out.print(nowDay);
      out.write(");</script>\r\n\t\t\t\t\t\t\t    \t<input type=\"checkbox\" id=\"selectTime\" name=\"selectTime\" value=\"select\" ");
if(null!=(request.getAttribute("selectTime"))) out.print("checked='checked'"); 
      out.write(" >\r\n\t\t\t\t\t\t\t    \t<input type=\"button\" class=\"btnButton2font\" onClick=\"search();\" value=\"查询\"/>\r\n\t\t\t\t\t\t\t\t\t<input type=\"button\" onclick=\"unsearch()\" class=\"btnButton2font\" value=\"重置\"/>\r\n\t\t\t\t\t\t\t    </td>\r\n\t\t\t\t\t\t    </tr>\r\n\t\t\t\t\t\t</table>\r\n\t\t\t\t\t</form>\r\n\t\t\t\t</td>\r\n\t\t\t</tr>\r\n\t\t\t<tr>\r\n\t\t\t\t<td>\r\n\t\t\t\t\t<table width=\"100%\" height=\"25\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"inlineBottomLine\">\r\n\t\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t\t<td align=\"right\">\r\n\t\t\t\t\t\t  \t");

						  	if(hasAddRight){
						  		
      out.write("\r\n\t\t\t\t\t\t  \t\t<input id=\"add\" type=\"button\" class=\"btnButton2Font\" value=\"我要留言\"/>\r\n\t\t\t\t\t\t  \t\t");

						  	}
						  	 
      out.write("\r\n\t\t\t\t\t\t  \t\t<input type=\"button\" class=\"btnButton2Font\" id=\"btnButton2FontShow\" onClick=\"showSearch(this);\" value=\"查询\"/>\r\n\t\t\t\t\t\t\t</td> \r\n\t\t\t\t\t    </tr>\r\n\t\t\t\t\t</table>\r\n\t\t\t\t</td>\r\n\t\t\t</tr>\r\n\t\t</table>\r\n\t\t");

	 	//
	 	if(isQuery){
	 		
      out.write("\r\n\t \t\t<script type=\"text/javascript\">\r\n\t \t\t$(\"#btnButton2FontShow\").click();\r\n\t \t\t</script>\r\n\t \t\t");

	 	}
		 
      out.write("\r\n    \t<div id=\"box\" class=\"box\">\r\n    \t");

    		//添加留言
    		if(false){
    			
      out.write("\r\n\t\t\t\t<div class=\"clickMe\">我要留言</div>\r\n    \t\t\t");

    		}
    	 
      out.write("\r\n\t\t\t<div id=\"content\" class=\"content\">\r\n\t\t\t");

			//留言墙信息
			List<MessageWallPO> mws = (List<MessageWallPO>) request.getAttribute("list");
			if(null!=mws && mws.size()>0){
				long id = 0;
				for(MessageWallPO m : mws){
					id = m.getMessagewallId();
					int type = (int) (Math.random() * 5) + 1;
					
      out.write("\r\n\t\t\t\t\t<div id=\"a_");
      out.print(id );
      out.write("\" onmouseover=\"");
/*删除按钮*/if(isAdmin){
      out.write("mousein('d_");
      out.print(id );
      out.write('\'');
      out.write(')');
      out.write(';');
} 
      out.write("cur('a_");
      out.print(id );
      out.write("')\" \r\n\t\t\t\t\t\t\tonmouseout=\"");
/*删除按钮*/if(isAdmin){
      out.write("mouseout('d_");
      out.print(id );
      out.write('\'');
      out.write(')');
      out.write(';');
} 
      out.write("out('a_");
      out.print(id );
      out.write("')\" class=\"note-");
      out.print(type );
      out.write("\">\r\n\t\t\t\t\t\t<!-- 留言人姓名及留言时间 -->\r\n\t\t\t\t\t\t<span>\r\n\t\t\t\t\t\t\t<font style=\"float: left; font-family: '宋体';\" size=\"-1\">\r\n\t\t\t\t\t\t\t\t<span id=\"n_");
      out.print(id );
      out.write("\" class=\"n\">");
      out.print(m.getMessagewallEmpname() );
      out.write("</span>\r\n\t\t\t\t\t\t\t\t<span id=\"t_");
      out.print(id );
      out.write("\" class=\"t\">");
      out.print(m.getMessagewallTime().toString().substring(0, 19) );
      out.write("</span>\r\n\t\t\t\t\t\t\t</font>\r\n\t\t\t\t\t\t</span>\r\n\t\t\t\t\t\t\r\n\t\t\t\t\t\t<!-- 留言内容 -->\r\n\t\t\t\t\t\t<span id=\"c_");
      out.print(id );
      out.write("\" class=\"c\" ");
/*双击修改*/if(m.getMessagewallEmpid() == userId) {
      out.write("ondblclick=\"modify('c_");
      out.print(id );
      out.write('\'');
      out.write(')');
      out.write('"');
} 
      out.write(" title=\"");
      out.print(m.getMessagewallContent() );
      out.write('"');
      out.write('>');
      out.print(com.js.util.util.CharacterTool.escapeHTMLTags(m.getMessagewallContent()) );
      out.write("</span>\r\n\t\t\t\t\t\t\r\n\t\t\t\t\t\t<!-- 删除 -->\r\n\t\t\t\t\t\t");

						//删除
						if(isAdmin){
						
      out.write("\r\n\t\t\t\t\t\t<span id=\"d_");
      out.print(id );
      out.write("\" class=\"hidden\">\r\n\t\t\t\t\t\t\t<img onclick=\"del('a_");
      out.print(id );
      out.write("')\" title=\"删除\" alt=\"删除\" src=\"/jsoa/images/messageWall/delete.png\">\r\n\t\t\t\t\t\t</span>\r\n\t\t\t\t\t\t");

						}
						 
      out.write("\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t");

				}
			}
			 
      out.write("\r\n\t\t\t</div>\r\n\t\t</div>\r\n\r\n        <!-- 遮罩层 -->\r\n        <div id=\"mask\" class=\"mask\"></div>\r\n        <div id=\"messageDiv\" class=\"pop-box\">\r\n        \t<input type=\"hidden\" id=\"num\">\r\n        \t<p>&nbsp;</p>\r\n            <h1>留言内容</h1>\r\n            <textarea id=\"write\" maxlength=\"1000\"></textarea>\r\n            <div id=\"button\" class=\"button\">\r\n\t            <div class=\"u-sure\" id=\"uSure\">确定</div>\r\n\t            <div class=\"u-quit\" id=\"uQuit\">取消</div>\r\n            </div>\r\n        </div>\r\n\t</center>\r\n  </body>\r\n  <script type=\"text/javascript\">\r\n  \t$(\"#searchName\").keydown(function(event){\r\n  \t\tkey(event.keyCode);\r\n  \t});\r\n  \t$(\"#searchContent\").keydown(function(event){\r\n  \t\tkey(event.keyCode);\r\n  \t});\r\n  </script>\r\n</html>");
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
