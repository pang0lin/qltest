/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:58:56 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.weixin.news;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.oa.weixin.common.bean.WeiXinBean;
import com.js.oa.weixin.common.util.MobileUtils;
import com.main.TestMain;
import java.util.*;
import java.text.SimpleDateFormat;

public final class weixin_005fwapinfo_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("com.js.oa.weixin.common.bean.WeiXinBean");
    _jspx_imports_classes.add("com.main.TestMain");
    _jspx_imports_classes.add("java.text.SimpleDateFormat");
    _jspx_imports_classes.add("com.js.oa.weixin.common.util.MobileUtils");
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");

String path = request.getContextPath();
request.setCharacterEncoding("UTF-8");
String conType = request.getParameter("conType");
//String beginIndex = request.getParameter("beginIndex").toString();
String title_html = "内部新闻";
String url = path + "/innerNewsAction.do?method=getDetailContent";

String readId = null!=request.getAttribute("readId") ? request.getAttribute("readId").toString() : "";
WeiXinBean weixinBean = new WeiXinBean();
//int guanzhu=weixinBean.searchYesOrNo(readId,"Info");
String liulan = weixinBean.getInfoReaderNum(readId);
List commentList = (List) request.getAttribute("commentList");
String isAllow = (String) request.getAttribute("isAllow");
if(isAllow == null){
   isAllow = "0";
}
String loginType = null==session.getAttribute("loginType") ? "wap" : session.getAttribute("loginType").toString();

      out.write("\r\n<!DOCTYPE html>\r\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/wap2/commonImport.jsp", out, false);
      out.write("\r\n<HTML>\r\n\t<HEAD>\r\n\t\t<TITLE>");
      out.print(title_html );
      out.write("</TITLE>\r\n\t\t<META content=\"text/html; charset=UTF-8\" http-equiv=Content-Type>\r\n\t\t<META name=viewport content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\">\r\n\t\t<META name=apple-touch-fullscreen content=YES>\r\n\t\t<META name=apple-mobile-web-app-capable content=no>\r\n\t\t<link rel=\"stylesheet\" href=\"/jsoa/css/weixin/weixin_zsgl.css\">\r\n\t\t<style>\r\n\t\t.reply_bar{\r\n\t\t    background: #fff;\r\n\t\t    color: #8f8e93;\r\n\t\t    font-weight: 700;\r\n\t\t    border-bottom: 1px solid #ccc;\r\n\t\t    padding: 10px;\r\n\t\t}\r\n\t\t</style>\r\n\t\t<script type=\"text/javascript\">\r\n\t\t");

		if("1".equals(request.getAttribute("flag")+"")){
			if("weixin".equals(loginType)){
				
      out.write("closeWindow();");

			} else if("wap".equals(loginType)){
				
      out.write("\r\n\t\t\t\t//loadURL();\r\n\t\t\t\twindow.location.href=\"/jsoa/innerNewsAction.do?method=getDetailContent\";\r\n\t\t\t\t");

			}
		}
		
      out.write("\r\n\t\t</script>\r\n\t</HEAD>\r\n\t<body onload=\"setHeader('javascript:closeWindow()', '内部新闻');\" class=\"wapcss\">\r\n\t\t<div class=\"zsglform\" ");
if("0".equals(isAllow)) {
      out.write(" style=\"padding-bottom: 0px;\" ");
}else{
      out.write(" style=\"padding-bottom: 60px;\" ");
}
      out.write(">\r\n\t\t\t");

         	Map map = (Map) request.getAttribute("contentMap");
        	
      out.write("\r\n            <div class=\"Zsglitem\">\r\n\t\t        <div class=\"zsgltitle\">\r\n\t\t        \t<b>");
      out.print(map.get("title")!=null?TestMain.filter((String)map.get("title")):"" );
      out.write("</b>\r\n\t\t\t\t</div>\t\r\n\t\t\t</div>\r\n\t\t\t\r\n\t\t    <div class=\"title_bar\">\r\n\t\t        <table width=\"98%\">\r\n\t       \t\t   <tr>\r\n\t       \t\t    <td class=\"zsgltitle_bar\" >");
      out.print(map.get("sendTime").toString().substring(5,16) );
      out.write("&nbsp;&nbsp;");
      out.print(map.get("orgName") );
      out.write("&nbsp;&nbsp;");
      out.print(map.get("sendUser") );
      out.write("</td>\r\n\t       \t\t    <td><img alt=\"图片\"  style=\"width:20px;height: 20px;\" border='0' src='/jsoa/images/weixin/guanzhu.png'/></td>\r\n\t       \t\t    <td style=\"color: #999999;\">");
      out.print(liulan );
      out.write("</td>\r\n\t       \t\t   </tr>\r\n\t       \t    </table>  \r\n\t\t    </div>\r\n\t\t    <div class=\"zsgllineitem\"></div>\r\n            <div class=\"zsglContent\" style=\"overflow: auto;\" >\r\n\t\t\t\t");
      out.print(MobileUtils.changeImg(map.get("content") + "") );
      out.write("\r\n\t\t \t</div>\r\n\t\t \t\r\n\t\t\t");

			if(map.get("filelist")!=null && !"null".equals(map.get("filelist"))){
				
      out.write("\r\n\t\t\t\t<div class=\"zsgllineitem\"></div>\r\n\t\t\t\t");
 
				List filelist = (List) map.get("filelist");
				if(filelist.size() != 0){
					
      out.write("\r\n\t\t\t\t\t<div class=\"Zsglitemfujian\" >\r\n        \t\t\t\t<div class=\"zsgl_fujian\">附件：\r\n\t\t\t     \t\t\t");

							for(int i=0; i<filelist.size(); i++){
								Map filemap = (Map) filelist.get(i);
								String src = "0000";
								if(filemap.get("accsave").toString()!=null && 
										filemap.get("accsave").toString().length()>6 && 
										filemap.get("accsave").toString().substring(4,5).equals("_")){
									src = filemap.get("accsave").toString().substring(0, 4);
								}else{
									src = "0000";
								}
					 			
								if(i==0) {
									
      out.write("<br/>");

								}
								
      out.write("\r\n\t\t\t\t\t\t\t\t<a style=\"text-decoration: none;\" onclick=\"javascript:showHtmlObject('");
      out.print(filemap.get("accsave").toString() );
      out.write("','0','information');\" href=\"javascript:void(0);\">\r\n\t\t\t\t\t\t\t\t\t");
      out.print(filemap.get("accname").toString() );
      out.write("\r\n\t\t\t\t\t\t\t\t</a>\r\n\t\t\t\t\t\t\t\t<br/>\r\n\t\t\t\t\t\t\t\t");

							}
			     			
      out.write("\r\n\t\t     \t\t\t</div>\r\n\t\t     \t\t</div>\r\n\t\t\t\t\t");

				}
			}
			
			if(commentList!=null&&commentList.size()>0){
				
      out.write(" \r\n\t\t\t\t<div class=\"zsgllineitem\"></div>\r\n\t\t\t\t<div class=\"reply_bar\" >\r\n\t\t\t\t\t<div>评论(");
      out.print(Integer.parseInt(commentList.size()+"") );
      out.write(")</div>\r\n\t\t\t\t</div>\r\n\t\t\t\t");

			}
			
      out.write("\r\n\t\t\t<div class=\"listcomment\">\r\n\t\t\t\t");

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				if(commentList != null){
					Object[] obj = null;
					for(int index=0; index<commentList.size(); index++){
						obj = (Object[]) commentList.get(index);
						String userAccount = com.js.system.util.StaticParam.getUserAccountsByEmpId(obj[5] + "");
						
      out.write("\r\n\t\t\t\t\t\t");
 if(obj[6]==null) {//obj[6]是上级评论id
      out.write("\r\n\t\t\t\t\t\t<div class=\"item\">\r\n\t\t\t\t\t   \t\t\r\n\t\t\t\t\t\t\t\t<div class=\"picture\">\r\n\t\t\t\t\t\t\t\t\t<img class=\"picshow\" src='/jsoa/weixin/common/getUserAvatar.jsp?userId=");
      out.print(userAccount);
      out.write("' />\r\n\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t<div class=\"content\">\r\n\t\t\t\t\t\t\t\t\t<div class=\"first\">\r\n\t\t\t\t\t\t\t\t\t\t<div class=\"title\">");
      out.print(obj[1]);
      out.write("</div>\r\n\t\t\t\t\t\t\t\t\t\t<div class=\"time\">");
      out.print(obj[2].toString().substring(5,16));
      out.write("</div>\r\n\t\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t\t<div class=\"second\">");
      out.print(obj[3]+"");
      out.write("</div>\r\n\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t");

									if(index<commentList.size()-1){
										Object []nextObj = (Object[])commentList.get(index+1);
										if(nextObj[6]==null){															
								
      out.write("\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t");

										}
									}
						}
						
      out.write("\r\n\t\t\t\t\t\t\r\n\t\t\t\t\t\t");

							if(obj[6]!=null){
						
      out.write("\r\n\t\t\t\t\t\t\t\t");
      out.write("<div class=\"item\" style=\"width: 90%; float: right; clear: left; border: 0; padding-top: 5px; padding-bottom: 5px;\">\r\n\t\t\t\t\t\t\t\t\t\t\t<div class=\"picture\">\r\n\t\t\t\t\t\t\t\t\t\t\t\t<img style=\"width:30px; height:30px;\" src=\"/jsoa/weixin/common/getUserAvatar.jsp?userId=");
      out.print(userAccount);
      out.write("\">\r\n\t\t\t\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t\t\t\t<div class=\"content\">\r\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"first\">\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"title\">");
      out.print(obj[1]);
      out.write("</div>\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"time\">");
      out.print(obj[2].toString().substring(5,16));
      out.write("</div>\r\n\t\t\t\t\t\t\t\t\t\t\t \t</div>\r\n\t\t\t\t\t\t\t\t\t\t\t \t<div class=\"second\">");
      out.print(obj[3]+"");
      out.write("</div>\r\n\t\t\t\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t\t");

									if(index<commentList.size()-1){
										Object []nextObj = (Object[])commentList.get(index+1);
										if(nextObj[6]==null){															
									
      out.write("\r\n\t\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t        \r\n\t\t        \t\t");

										}
									}
						}
		        		
      out.write("\t\r\n\t\t\t\t");

				}
			}
				
      out.write("\t\r\n\t\t\t </div>\r\n\t\t \t");

	  		if("1".equals(isAllow)){
				
      out.write("\r\n\t\t\t\t<!-- 按钮 -->\r\n\t\t\t\t<div class=\"footer\">\r\n      \t  \t\t\t<div class=\"buttons\">\r\n\t    \t\t\t<!-- 回复 -->\r\n\t\t\t\t\t\t<form style=\"width: 100%; height: 100%;\" name=\"form1\" id=\"form1\" action=\"");
      out.print(path);
      out.write("/innerNewsAction.do?method=speak\" method=\"post\">\r\n\t\t\t\t\t\t\t<div class=\"input\">\r\n\t                   \t\t\t<input type=\"hidden\" name=\"readId\" id=\"readId\" value=\"");
      out.print(readId);
      out.write("\">\r\n\t\t\t\t\t\t\t\t<input type=\"text\" id=\"contents\" name=\"contents\"/>\r\n\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t<div class=\"replyDiv\">\r\n\t\t\t\t\t\t\t\t<div class=\"reply\" onclick=\"javascript:tijiaoForm();\">评论</div>\r\n\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t</form>\r\n\t\t  \t\t\t</div>\r\n\t  \t\t\t</div>\r\n\t \t\t\t");

	 		}
	 		
      out.write("\r\n\t \t\t<div id=\"closeWindow\" style=\"display: none;\"></div>\r\n\t\t</div>\r\n\t\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/weixin/common/weixin_messageReminders.jsp", out, false);
      out.write("\r\n\t\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/weixin/workflow/daibanflow/weixinshowhtml.jsp", out, false);
      out.write("\r\n\t\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/weixin/common/weixinshowimage.jsp", out, false);
      out.write(" \r\n\t\t<script type=\"text/javascript\">\r\n\t\t//关注\r\n\t\tfunction attention(){\r\n\t\t\t//setremind('");
      out.print(Long.parseLong(session.getAttribute("userId").toString()));
      out.write('\'');
      out.write(',');
      out.print(readId);
      out.write(",'Info','add');//操作sys_remind表\r\n\t\t}\r\n\t\t\r\n\t\tfunction tijiaoForm(){\r\n\t\t   var content = $.trim($(\"#contents\").val());\r\n\t\t   if(content==null || content==\"\" || content.length>400){\r\n\t\t\t   weixinMessageReminder(\"alert\", \"提示：\", \"评论不能为空且字符长度不能大于400!\", \"\");\r\n\t\t\t\treturn;\r\n\t\t\t}else{\r\n\t\t\t\t$(\"#contents\").val(filterEmoji(content))\r\n\t\t\t\tdocument.form1.submit();\r\n\t\t\t}\r\n\t\t}\r\n\t\tvar clientWidth = document.body.clientWidth;\r\n\t\tsetTimeout(FormatImagesSize(clientWidth),30);\r\n\t\tFormatImagesSize(windowWidthImage);\r\n\t\tfunction FormatImagesSize(w){\r\n\t\t  var e=new Image();\r\n\t\t  for(i=0;i<document.images.length;i++){\r\n\t\t    if (document.images[i]){\r\n\t\t\t  if(document.images[i].resize!=0){\r\n\t\t        e.src=document.images[i].src;\r\n\t\t        //alert(\"e.width：----\"+e.width+\"-------------w：----\"+w+\"-----------e.src：-----\"+e.src);\r\n\t\t        if (e.width>w) {\r\n\t\t\t\t  var pic=document.images[i];\r\n\t\t\t\t  pic.style.width=\"100%\";\r\n\t\t\t\t  pic.style.height=\"\";\r\n\t\t          pic.title=\"点击放大\";\r\n\t\t          pic.style.cursor=\"hand\";\r\n\t\t         if(e.src.indexOf(\"/guanzhu.png\")<0){\r\n");
      out.write("\t\t\t          pic.onclick=function(){\r\n\t\t\t             showImageFromat(this.src);\r\n\t\t\t          }\r\n\t\t\t      }    \r\n\t\t\t\t}else{\r\n\t\t          var pic=document.images[i];\r\n\t\t          pic.title=\"点击放大\";\r\n\t\t          if(e.width==0){\r\n\t\t            pic.style.width=w;\r\n\t\t          }\r\n\t\t          pic.style.cursor=\"hand\";\r\n\t\t          if(e.src.indexOf(\"/guanzhu.png\")<0){\r\n\t\t            pic.onclick=function(){\r\n\t\t               showImageFromat(this.src);\r\n\t\t            }\r\n\t\t          }\r\n\t\t          \r\n\t\t        }\r\n\t\t\t  }\r\n\t\t    }\r\n\t\t  }\r\n\t\t} \r\n\t\t/**\r\n\t\t* 用于替换Emoji表情为空格，保留其他文本内容\r\n\t\t* @version 1.0 snapshot\r\n\t\t* @param {string} str 需要过滤的文本\r\n\t\t**/\r\n\t\tfunction filterEmoji(str){\r\n\t\t\t//替换三字节的Emoji表情\r\n\t\t\tstr = str.replace(/[\\u00AE-\\u2BFF]/g,\" \");\r\n\t\t\t//替换四字节的Emoji表情\r\n\t\t    return str.replace(/\\uD83C[\\uDC00-\\uDFFF]|\\uD83D[\\uDC00-\\uDFFF]|\\uD83E[\\uDC00-\\uDFFF]/g, \" \");\r\n\t\t}\r\n\t\t</script>\r\n\t</body>\r\n</html>");
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
