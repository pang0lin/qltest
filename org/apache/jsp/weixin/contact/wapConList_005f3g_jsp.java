/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:58:17 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.weixin.contact;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.net.URLEncoder;
import java.util.*;
import com.js.util.util.DateHelper;
import com.js.wap.util.WapUtil;
import com.qq.weixin.mp.util.WeixinUserUtil;
import com.js.system.util.StaticParam;

public final class wapConList_005f3g_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("java.net.URLEncoder");
    _jspx_imports_classes.add("com.qq.weixin.mp.util.WeixinUserUtil");
    _jspx_imports_classes.add("com.js.system.util.StaticParam");
    _jspx_imports_classes.add("com.js.util.util.DateHelper");
    _jspx_imports_classes.add("com.js.wap.util.WapUtil");
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
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE html>\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

	request.setCharacterEncoding("UTF-8");
	String path = request.getContextPath();
	String action="";
	String type=request.getAttribute("type").toString();
	String keyword = "";
	String isFromQuery = "false";
	if(request.getAttribute("isFromQuery")!=null){
	   isFromQuery = request.getAttribute("isFromQuery").toString();
	}
	if(request.getAttribute("keyword")!=null){
	   keyword = request.getAttribute("keyword").toString();
	}
	String orgId = "";
	if(request.getAttribute("orgId")!=null){
	   orgId = request.getAttribute("orgId").toString();
	}
	String title="";
	if("2".equals(type)){
		action = "personalInner";
		title="内部联系人";
	}else if("1".equals(type)){
		action = "commonLinkMan";
		title="公共联系人";
	}else if("0".equals(type)){
		action="commonLinkMan";
		title="个人联系人";
	}
	List list = (List)request.getAttribute("person");
	int contactCount= Integer.parseInt(request.getAttribute("size").toString());
	int beginIndex=Integer.parseInt(request.getParameter("beginIndex")==null?"0":request.getParameter("beginIndex"));

      out.write("\r\n<html lang=\"zh-CN\">\r\n\t<HEAD>\r\n\t\t<TITLE>");
      out.print(title);
      out.write("</TITLE>\r\n\t\t<META content=\"text/html; charset=UTF-8\" http-equiv=Content-Type>\r\n\t\t<META name=viewport content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\">\r\n\t\t<META name=apple-touch-fullscreen content=YES>\r\n\t\t<META name=apple-mobile-web-app-capable content=no>\r\n\t\t<link rel=\"stylesheet\" href=\"/jsoa/css/weixin/search.css\">\r\n\t\t<link rel=\"stylesheet\" href=\"/jsoa/css/weixin/weixin_main.css\">\r\n\t\t<STYLE type=\"text/css\">\r\n\t\tspan { height: 20px; line-height: 24px;}\r\n\t\t.name { float: left}\r\n\t\t.phone { float: right;}\r\n\t\t</STYLE>\r\n\t</HEAD>\r\n\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/wap2/commonImport.jsp", out, false);
      out.write("\r\n\t<BODY onload=\"setHeader('javascript:closeWindow();', '");
      out.print(title );
      out.write("');\">\r\n\t\t<FORM id=\"searchForm\" action=\"/jsoa/weixinContactAction.do?action=personalInner&type=2&orgId=&fromQuery=true\" method=\"post\">\r\n\t\t\t<div class=\"sousuo\">\r\n\t\t\t\t<div class=\"sousuo-01\">\r\n\t\t\t\t\t<INPUT type=\"hidden\" id=\"keyword\" name=\"keyword\" />\r\n\t\t\t\t\t<INPUT type=\"text\" id=\"keys\" name=\"keys\" value=\"");
      out.print(keyword);
      out.write("\" placeholder=\"请输入姓名、手机号或单位\" />\r\n\t\t\t\t\t<div class=\"sousuo-11\" onclick=\"javascript:submitForm();\"><img src=\"images/weixin/ss.png\"></div>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t</FORM>\r\n\t\t\r\n\t\t<div class=\"list\">\r\n\t\t\t");

			if("2".equals(type)){
				String sex="";
				if(list == null || list.size()==0){//没有查询结果
					
      out.write("\r\n\t\t\t\t\t\r\n\t\t\t\t\t\t<div class=\"item\">\r\n\t\t\t\t        \t<div class=\"nodata\">没有查询到数据！</div>\r\n\t\t\t\t        </div>\r\n\t\t\t      \r\n\t\t\t\t\t");

				}else{
					for(int i=0; i<list.size(); i++){
						sex="男";
						Object[] emp = (Object[]) list.get(i);
						if("1".equals(emp[2].toString())){
					       sex = "女";
						}
						String more=path+"/weixinContactAction.do?action=innerMan&type="+type+"&empId="+emp[0]+"&beginIndex="+beginIndex;
						String url = "/jsoa/images/weixin/user-no.png";
						if(emp[5] != null && !"".equals(emp[5])){
						   url = "weixin/common/getUserAvatar.jsp?userId="+emp[5]+"&time="+System.currentTimeMillis();
						}
						
      out.write("\r\n\t\t\t\t\t\t<div class=\"item\">\r\n\t\t\t\t\t\t\t<div class=\"picture\" onclick=\"javascript:showDetail('");
      out.print(more);
      out.write("');\">\r\n\t\t\t\t\t\t\t\t<img alt=\"头像\" src=\"");
      out.print(url );
      out.write("\">\r\n\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t<div class=\"content\">\r\n\t\t\t\t\t\t\t\t<div style=\"height:25px;\">\r\n\t\t\t\t\t\t\t\t\t<SPAN onclick=\"javascript:showDetail('");
      out.print(more);
      out.write("');\"><B style=\"font-size: 14px;\">");
      out.print(emp[1]);
      out.write("</B></SPAN>\r\n\t\t\t\t\t\t\t\t\t");

									if(emp[4]!= null && !"".equals(emp[4])){
										
      out.write("\r\n\t\t\t\t\t\t\t\t\t\t<SPAN class=\"phone\" style=\"padding-top:7px;\">\r\n\t\t\t\t\t\t\t\t\t\t\t<a href=\"tel:");
      out.print(emp[4]);
      out.write("\">\r\n\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"");
      out.print(path);
      out.write("/wap2/images/dianhua.png\" style=\"width:25px;height:25px;\" />\r\n\t\t\t\t\t\t\t\t\t\t\t</a>\r\n\t\t\t\t\t\t\t\t\t\t\t<a href=\"SMS:");
      out.print(emp[4]);
      out.write("\">\r\n\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"");
      out.print(path);
      out.write("/wap2/images/duanxin.png\" style=\"width:25px;height:25px;\" />\r\n\t\t\t\t\t\t\t\t\t\t\t</a>\r\n\t\t\t\t\t\t\t\t\t\t</SPAN>\r\n\t\t\t\t\t\t\t\t\t\t");

									}
									
      out.write("\r\n\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t<div style=\"width:100%;\">\r\n\t\t\t\t\t\t\t\t");

								String org=StaticParam.getOrgByNum(emp[3].toString());
								if(org.length()>15){
									org=org.substring(0,13)+".....";
								}
								
      out.write("\r\n\t\t\t\t\t\t\t\t\t<SPAN class=\"org\"><B style=\"font-size: 14px;\">");
      out.print(org);
      out.write("</B></SPAN>\r\n\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t");

					}
				}
			}else{
				String sex = "";
				if(list == null || list.size()==0){//没有查询结果
					
      out.write("\r\n\t\t\t\t\t\t<div class=\"item\">\r\n\t\t            \t\t<div class=\"nodata\">没有查询到数据！</div>\r\n\t\t         \t\t</div>\r\n\t\t\t\t\t");

				}else{
				 	for(int i=0;list != null && i<list.size();i++){
						sex="男";
						Object[] emp=(Object[])list.get(i);
						if(emp[2].toString().equals("1")){
							sex="女";
						}
						String more = "/jsoa/weixinContactAction.do?action=linkMan&beginIndex="+beginIndex+"&id="+emp[0].toString()+"&type="+type;
						String gs = (String)emp[4];
						if(gs == null){
						   gs = "";
						}
						
      out.write("\r\n\t\t\t\t\t\t<DIV class=\"item\">\r\n\t\t\t\t\t\t\t<div class=\"name\" style=\"width: 70%;\">\r\n\t\t\t\t\t\t\t\t<div class=\"first\" style=\"width: 100%; height: 20px;\" onclick=\"javascript:showDetail('");
      out.print(more);
      out.write("');\">\r\n\t\t\t\t\t\t\t\t\t<SPAN class=\"name\" style=\"height: 20px; line-height: 20px;\"><b>");
      out.print(emp[1]);
      out.write("</b></SPAN>\r\n\t\t\t\t\t\t\t\t\t<SPAN class=\"phone\" style=\"font-size: 14px; height: 20px; line-height: 20px;\">分类：");
      out.print(emp[3].toString());
      out.write("</SPAN>\r\n\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t<div class=\"second\">\r\n\t\t\t\t\t\t\t\t\t<SPAN class=\"name\" style=\"font-size: 14px;\">");
      out.print("公司："+gs);
      out.write("</SPAN>\r\n\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t");

							if(emp[5]!= null && !"".equals(emp[5])){
								
      out.write("\r\n\t\t\t\t\t\t\t\t<DIV class=\"phone\">\r\n\t\t\t\t\t\t\t\t\t<a href=\"tel:");
      out.print(emp[5]);
      out.write("\">\r\n\t\t\t\t\t\t\t\t\t\t<img src=\"");
      out.print(path);
      out.write("/wap2/images/dianhua.png\" style=\"width:30px;height:30px;margin-top: 5px;\" />\r\n\t\t\t\t\t\t\t\t\t</a>\r\n\t\t\t\t\t\t\t\t\t<a href=\"SMS:");
      out.print(emp[5]);
      out.write("\">\t\r\n\t\t\t\t\t\t\t\t\t\t<img src=\"");
      out.print(path);
      out.write("/wap2/images/duanxin.png\" style=\"width:30px;height:30px;margin-top: 5px;\" />\r\n\t\t\t\t\t\t\t\t\t</a>\r\n\t\t\t\t\t\t\t\t</DIV>\r\n\t\t\t\t\t\t\t\t");

							}
							
      out.write("\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t");

					}
				} 
			}
			
      out.write("\r\n\t\t</div>\r\n\t\t\t\r\n\t\t");

		int recordCount = contactCount;
		int curNum = beginIndex/WapUtil.LIMITED + 1;	// 当前页数
		int totalNum = recordCount%WapUtil.LIMITED > 0 ? recordCount/WapUtil.LIMITED+1 : recordCount/WapUtil.LIMITED;	// 总页数
		int nextNum = (beginIndex / WapUtil.LIMITED) + 1;
		int nextIndex = nextNum * WapUtil.LIMITED;
		int upNum = (beginIndex / WapUtil.LIMITED) - 1;
		int upIndex = upNum * WapUtil.LIMITED;
		String upUrl = "#", nextUrl = "#", upUrlFont = "", nextUrlFont = "";
		if (recordCount > WapUtil.LIMITED) {
		
			if (upIndex >= 0){
				upUrl = path + "/weixinContactAction.do?action="+action+"&amp;type="+type+"&amp;beginIndex=" + upIndex + "&orgId="+orgId+"&fromQuery="+isFromQuery;
				if (null != keyword && !"".equals(keyword))
					upUrl += "&keyword=" + URLEncoder.encode(URLEncoder.encode(keyword, "utf-8"), "utf-8");
			} else{
				upUrl = "javascript:void(0);";
				upUrlFont = "grayFont";
			}
			if (nextIndex < recordCount){
				nextUrl = path + "/weixinContactAction.do?action="+action+"&amp;type="+type+"&amp;beginIndex=" + nextIndex + "&orgId="+orgId+"&fromQuery="+isFromQuery;
				if (null != keyword && !"".equals(keyword))
					nextUrl += "&keyword=" + URLEncoder.encode(URLEncoder.encode(keyword, "utf-8"), "utf-8");
			} else{
				nextUrl = "javascript:void(0);";
				nextUrlFont = "grayFont";
			}

			
      out.write("\r\n\t\t\t<div class=\"bottomDiv\">\r\n\t\t\t\t<a href=\"");
      out.print(upUrl );
      out.write("\">\r\n\t\t\t\t\t<div class=\"up ");
      out.print(upUrlFont );
      out.write("\">上一页</div>\r\n\t\t\t\t</a>\r\n\t\t\t\t<div class=\"page grayFont\">");
      out.print(curNum );
      out.write('/');
      out.print(totalNum );
      out.write("</div>\r\n\t\t\t\t<a href=\"");
      out.print(nextUrl );
      out.write("\">\r\n\t\t\t\t\t<div class=\"down ");
      out.print(nextUrlFont );
      out.write("\">下一页</div>\r\n\t\t\t\t</a>\r\n\t\t\t</div>\r\n\t\t\t");

		}
		
      out.write("\r\n\t\t<script>\r\n\t\tfunction gotoPage(url, flag) {\r\n\t\t\tif (flag) {\r\n\t\t\t\tlocation.href = encodeURI(url);\r\n\t\t\t}\r\n\t\t}\r\n\t\tfunction showDetail(url) {\r\n\t\t\tlocation.href = url;\r\n\t\t}\r\n\t\tfunction submitForm() {\r\n\t\t\tvar k = document.getElementById(\"keys\").value;\r\n\t\t\tif (k != '') {\r\n\t\t\t\tk = encodeURI(k);\r\n\t\t\t}\r\n\t\t\tif(\"2\"==");
      out.print(type);
      out.write("){\t// 内部联系人\r\n\t\t\t   document.getElementById(\"searchForm\").action=\"/jsoa/weixinContactAction.do?action=personalInner&type=2&orgId=&fromQuery=true\";\r\n\t\t\t} else{\t// 公共联系人或个人联系人\r\n\t\t\t   document.getElementById(\"searchForm\").action=\"/jsoa/weixinContactAction.do?action=commonLinkMan&type=\" + ");
      out.print(type);
      out.write(";\r\n\t\t\t}\r\n\t\t\tdocument.getElementById(\"keyword\").value = k;\r\n\t\t\tdocument.getElementById(\"searchForm\").submit();\r\n\t\t}\r\n\t\t</script>\r\n\t</body>\r\n</html>");
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
