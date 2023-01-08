/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:44:18 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.iWebOffice2015;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import DBstep.iDBManager2000.*;

public final class DocumentList_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

DBstep.iDBManager2000 DbaObj = new DBstep.iDBManager2000();

	//列出所有模版
	public String GetTemplateList(String ObjType, String FileType) {
		String mTemplateList, mstr = "";
		mTemplateList = "<select name=" + ObjType + " >";
		mTemplateList = mTemplateList
				+ "<option value=''>--------不用模版--------</option>";
		String Sql = "select RecordID,Descript from Template_File where FileType='"
				+ FileType + "'"; //打开数据库
		try {
			if (DbaObj.OpenConnection()) {
				try {
					ResultSet result = DbaObj.ExecuteQuery(Sql);
					mstr = "selected";
					while (result.next()) {
						mTemplateList = mTemplateList + "<option value='"
								+ result.getString("RecordID") + "'" + mstr
								+ ">" + result.getString("Descript")
								+ "</option>";
					}
					result.close();
				} catch (SQLException sqlex) {
					System.out.println(sqlex.toString());
				}
			} else {
				System.out.println("GetTemplateList: OpenDatabase Error");
			}
		} finally {
			DbaObj.CloseConnection();
		}
		mTemplateList = mTemplateList + "</select>";
		return (mTemplateList);
	}
	/**
   * 功能或作用：格式化日期时间
   * @param DateValue 输入日期或时间
   * @param DateType 格式化 EEEE是星期, yyyy是年, MM是月, dd是日, HH是小时, mm是分钟,  ss是秒
   * @return 输出字符串
   */
  public String FormatDate(String DateValue,String DateType)
  {
    String Result;
    SimpleDateFormat formatter = new SimpleDateFormat(DateType);
    try{
      Date mDateTime = formatter.parse(DateValue);
      Result = formatter.format(mDateTime);
    }catch(Exception ex){
      Result = ex.getMessage();
    }
    if (Result.equalsIgnoreCase("1900-01-01")){
      Result = "";
    }
    return Result;
  }
	
  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("java.sql");
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("DBstep.iDBManager2000");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.util.Date");
    _jspx_imports_classes.add("java.text.SimpleDateFormat");
    _jspx_imports_classes.add("java.text.DateFormat");
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
      response.setContentType("text/html; charset=gb2312");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n");
      out.write('\r');
      out.write('\n');

  SimpleDateFormat f1 = new SimpleDateFormat("yyyyMMddHHmm");
  SimpleDateFormat f2 = new SimpleDateFormat("ss");
 
      out.write("\r\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n\t<head>\r\n\t\t<title>金格科技-iWebOffice2015智能文档中间件示例程序</title>\r\n\t\t<meta http-equiv=\"X-UA-Compatible\" content=\"IE=8\" />\r\n\t\t<link rel='stylesheet' type='text/css' href='css/iWebProduct.css' />\r\n\t\t<script language=\"javascript\">\r\n  function CheckActiveX(){ \r\n    var mObject=true;\r\n    try{\r\n      var newAct =   new ActiveXObject(\"Kinggrid.iWebOffice\");\r\n     \r\n      if(newAct == undefined ){\r\n       mObject=false;\r\n      }\r\n    }catch(e){\r\n      mObject=false;\r\n    }\r\n    newAct = null;\r\n    if(!(window.ActiveXObject||\"ActiveXObject\" in window)){\r\n    activex_install.innerHTML =\"多浏览器如果没有正常加载，查看说明\"\r\n    return true;\r\n    }\r\n\t    if(mObject){\r\n\t\t  activex_install.innerHTML = \"已经安装iWebOffice2015中间件！\";\r\n\t\t  activex_install.style.color=\"#FFFFFF\";\r\n\t    }else{\r\n\t      //控件无法加载\r\n\t      activex_install.innerHTML = \"请注意，未检测到iWebOffice2015中间件！请查看说明，并按说明的要求检查您使用的环境。\";\r\n");
      out.write("\t\t  activex_install.style.color=\"#FF0000\";\r\n\t    }\r\n   \r\n    return mObject; \r\n  }\r\n\r\n  function init(){\r\n\t  var mhtml = document.documentElement.clientHeight;\r\n\t  var mhead = document.getElementById(\"mhead\").offsetHeight; \r\n\t  var mtitle = document.getElementById(\"mtitle\").offsetHeight;\r\n\t  var mfooter = document.getElementById(\"mfooter\").offsetHeight; \r\n\t  document.getElementById('mbody').style.height = mhtml- mhead-mtitle-mfooter+\"px\";\r\n\t  \r\n\t  document.getElementById('showlist').style.height = mhtml- mhead-mtitle-mfooter-160+\"px\";\r\n\t  document.getElementById('showlist').style.width = document.getElementById('titleTable').offsetWidth;\r\n  }\r\n\t  \r\n\t  \r\n  //获取id的高度\r\n  function  getHeight(id){\r\n    return document.getElementById(id).offsetHeight; \r\n  }\r\n  \r\n  function ShowExplain(){\r\n \t window.open(\"UserExplain.html\", 'newwindow','height=300px,width=780px,top=150,left=300,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');  \r\n\r\n  }\r\n  \r\n  \r\n  function OpenNewPage(value){\r\n  \tvar mhtmlHeight = window.screen.availHeight;//获得窗口的垂直位置;\r\n");
      out.write("\tvar mhtmlWidth = window.screen.availWidth; //获得窗口的水平位置; \r\n\tvar iTop = 0; //获得窗口的垂直位置;\r\n\tvar iLeft = 0; //获得窗口的水平位置;\r\n    var values = new Array;\r\n    values = value.split(\",\");    \r\n    FileType = values[0];\r\n    FileVal  = values[1];\r\n    \r\n    if(FileType != -1){\r\n\r\n     var aa =  window.open('DocumentEdit.jsp?FileType='+FileType+'&UserName='+ encodeURI(encodeURI(username.value)),'iWebOffice2015智能文档中间件示例程序','height='+mhtmlHeight+',width='+mhtmlWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=yes,scrollbars=no,resizable=yes, location=no,status=no');  \r\n    }\r\n    document.getElementById(\"selectID\").options[0].selected = true;\r\n    \r\n  }\r\n  \r\n\r\n\r\n  function openfile(medittype,RecordID){\r\n  \tvar mhtmlHeight = window.screen.availHeight;//获得窗口的垂直位置;\r\n\tvar mhtmlWidth = window.screen.availWidth; //获得窗口的水平位置; \r\n\tvar iTop = 0; //获得窗口的垂直位置;\r\n\tvar iLeft = 0; //获得窗口的水平位置;\r\n    window.open(\"DocumentEdit.jsp?RecordID=\"+RecordID+\"&EditType=\"+medittype+\"&UserName=\"+encodeURI(encodeURI(username.value)),'iWebOffice2015智能文档中间件示例程序','height='+mhtmlHeight+',width='+mhtmlWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=yes,scrollbars=no,resizable=yes, location=no,status=no');  \r\n");
      out.write("  }\r\n  \r\n  \r\n </script>\r\n\t</head>\r\n\t<body onload=\"init()\" onresize=\"init()\"\r\n\t\tstyle=\"overflow-y: hidden; overflow-x: hidden\">\r\n\t\t<div id=\"mhead\" class=\"mhead\">\r\n\t\t\t<table id=\"header\">\r\n\t\t\t\t<tr>\r\n\t\t\t\t\t<td>\r\n\t\t\t\t\t\t<img src=\"css/logo.jpg\" />\r\n\t\t\t\t\t</td>\r\n\t\t\t\t\t<td>\r\n\t\t\t\t\t\t<span> iWebOffice2015</span> 智能文档中间件示例程序\r\n\t\t\t\t\t</td>\r\n\t\t\t\t</tr>\r\n\t\t\t</table>\r\n\t\t</div>\r\n\r\n\t\t<div id=\"mtitle\" style=\"height: 34px;\" class=\"title\">\r\n\t\t\t<table>\r\n\t\t\t\t<tr>\r\n\t\t\t\t\t<td height=\"34px\">\r\n\t\t\t\t\t   <div width=\"0px\" height=\"0px\" style=\"display:none\" > <script src=\"js/iWebOffice2015.js\"></script></div>\r\n\t\t\t\t\t\t<span id=\"activex_install\" style=\"color: #FF0000\">请注意，未检测到iWebOffice2015中间件！请查看说明，并按说明的要求检查您使用的环境。</span>\r\n\t\t\t\t\t\t<a href=\"#\" onclick=\"ShowExplain()\">[说明]</a>\r\n\t\t\t\t\t\t<div id=\"obj\">\t\t\t\t\t\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t</td>\r\n\t\t\t\t</tr>\r\n\t\t\t</table>\r\n\t\t</div>\r\n\t\t<div id=\"mbody\" style=\"text-align: center;\">\r\n\t\t\t<img id=\"loading\" src=\"css/load.gif\" alt=\"\" />\r\n\t\t\t<div id=\"loaded\"\r\n\t\t\t\tstyle=\"margin: 30px; border: 1px solid #C3ADC3; text-align: center; display: none;\">\r\n");
      out.write("\r\n\t\t\t\t<table id=\"innerTable\" border=0 cellspacing='0' cellpadding='0'\r\n\t\t\t\t\tstyle=\"height:40px;\">\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t<td align=\"left\">\r\n\t\t\t\t\t\t\t<span style=\"padding-right: 14px;\">当前编辑用户：<input type=text\r\n\t\t\t\t\t\t\t\t\tname='username' id='username' size=20  onblur=\"if(this.value.length>8){alert('用户名不能超过8个字');this.focus()}\"  value=\"体验用户");
      out.print(f2.format(new Date()));
      out.write("\"\r\n\t\t\t\t\t\t\t\t\tclass=\"InputLine\" /> </span>\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t\t<td align=\"right\" colspan=\"3\">\r\n                            <span style=\"padding-right: 25px;\">　新建文档：\r\n                            <select  id=\"selectID\" onchange=\"OpenNewPage(this.value)\">\r\n                              <option value=\"-1\" selected=\"selected\">　选择文档    ↓</option>\r\n                              <option value=\".doc,1\">　新建WORD文档</option>\r\n                              <option value=\".xls,2\">　新建EXECL文档</option>\r\n\r\n                              <option value=\".wps,1\">　新建WPS文档</option>\r\n                              <option value=\".et,2\">　新建ET文档</option>\r\n                            </select>\r\n                            </span>　　　\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t</tr>\r\n\r\n\r\n\t\t\t\t</table>\r\n\t\t\t\t<table id=\"titleTable\" cellspacing='0' cellpadding='0'\r\n\t\t\t\t\talign=\"center\" style=\"height: 42px;\">\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t<td width=\"60px\">\r\n\t\t\t\t\t\t\t编号\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t\t<td>\r\n\t\t\t\t\t\t\t主题\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t\t<td width=\"120px\">\r\n\t\t\t\t\t\t\t作者\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t\t<td width=\"115px\">\r\n");
      out.write("\t\t\t\t\t\t\t类型\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t\t<td width=\"190px\">\r\n\t\t\t\t\t\t\t保存时间\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t\t<td width=\"380px\">\r\n\t\t\t\t\t\t\t操作\r\n\t\t\t\t\t\t</td>\r\n\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t</table>\r\n\r\n\t\t\t\t<div id=\"showlist\"\r\n\t\t\t\t\tstyle=\"vertical-align: top; height: 300px; margin-right: auto; margin-left: auto;\">\r\n\t\t\t\t\t<table align=\"center\" cellspacing='0' cellpadding='0'\r\n\t\t\t\t\t\tstyle=\"height: 50px;\">\r\n\t\t\t\t\t\t");

							try {
								if (DbaObj.OpenConnection()) {
									try {
										ResultSet result = DbaObj
												.ExecuteQuery("select Status,RecordID,HtmlPath,DocumentID,Subject,Author,FileType,FileDate from Document order by DocumentID desc");
										int i = 1;
										while (result.next()) {
											String RecordID = result.getString("RecordID");
											String HTMLPath = result.getString("HtmlPath");
											if (HTMLPath == null)
												HTMLPath = "";
						
      out.write("\r\n\r\n\r\n\t\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t\t<td width=\"60px\"  class=\"TD");
      out.print((i+1)%2 );
      out.write('"');
      out.write('>');
      out.print(i++);
      out.write("</td>\r\n\t\t\t  <td class=\"TD");
      out.print((i)%2);
      out.write('"');
      out.write('>');
      out.print(result.getString("Subject"));
      out.write("</td>\r\n\t\t\t  <td width=\"100px\" class=\"TD");
      out.print((i)%2 );
      out.write('"');
      out.write('>');
      out.print(result.getString("Author"));
      out.write("</td>\r\n\t\t\t  <td width=\"100px\" class=\"TD");
      out.print((i)%2 );
      out.write('"');
      out.write('>');
      out.print(result.getString("FileType"));
      out.write("</td>\r\n\t\t\t  <td width=\"190px\" class=\"TD");
      out.print((i)%2 );
      out.write('"');
      out.write('>');
      out.print(FormatDate(result.getString("FileDate"),"yyyy-MM-dd HH:mm:ss"));
      out.write("</td>\r\n\t\t\t\t\t\t\t<td width=\"380px\" align=center class=\"TD");
      out.print((i) % 2);
      out.write("\">\r\n\t\t\t\t\t\t\t\t<a href=\"#\"\r\n\t\t\t\t\t\t\t\t\tonclick=\"openfile('0','");
      out.print(RecordID);
      out.write("')\">阅读</a>\r\n\t\t\t\t\t\t\t\t<a href=\"#\"\r\n\t\t\t\t\t\t\t\t\tonclick=\"openfile('1','");
      out.print(RecordID);
      out.write("')\">修改[无痕迹]</a>\r\n\t\t\t\t\t\t\t\t<a href=\"#\"\r\n\t\t\t\t\t\t\t\t\tonclick=\"openfile('2','");
      out.print(RecordID);
      out.write("')\">修改[有痕迹]</a>\r\n\t\t\t\t\t\t\t</td>\r\n\t\t\t\t\t\t</tr>\r\n\t\t\t\t\t\t");

							}
										result.close();
									} catch (SQLException sqlex) {
										System.out.println(sqlex.toString());
									}
								} else {
									out.println("OpenDatabase Error");
								}
							} finally {
								DbaObj.CloseConnection();
							}
						
      out.write("\r\n\t\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t\t<td style='border: none;'></td>\r\n\t\t\t\t\t\t</tr>\r\n\t\t\t\t\t</table>\r\n\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\r\n\t\t<div id=\"mfooter\">\r\n\t\t\t<table class=\"footer\">\r\n\t\t\t\t<tr>\r\n\t\t\t\t\t<td align=\"center\">\r\n\t\t\t\t\t\t江西金格科技股份有限公司 版权所有\r\n\t\t\t\t\t</td>\r\n\t\t\t\t</tr>\r\n\t\t\t</table>\r\n\t\t</div>\r\n\t</body>\r\n</html>\r\n<script language=\"javascript\" type=\"text/javascript\">\r\n var checkActiveX  = CheckActiveX();\r\n if(checkActiveX){\r\n document.getElementById('loading').style.display = \"none\";\r\n document.getElementById('loaded').style.display = \"\";\r\n }else{\r\n  document.getElementById('loading').style.display = \"none\";\r\n  document.getElementById('loaded').style.display = \"\";\r\n }\r\n\r\n</script>");
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
