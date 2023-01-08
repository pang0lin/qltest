/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:46:53 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.wap2.workitem;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.cooperate.po.*;
import java.util.*;
import com.main.TestMain;
import com.js.wap.util.WapUtil;
import com.js.util.util.HTMLEncoding;

public final class coop_005fdealwith_005f3g_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("com.js.cooperate.po");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.util.util.HTMLEncoding");
    _jspx_imports_classes.add("com.main.TestMain");
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\r\n");

request.setCharacterEncoding("UTF-8");
String conType=request.getParameter("conType");

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String filePath="/jsoa";
if(com.js.util.config.SystemCommon.getUseClusterServer()==1){
	 filePath=com.js.util.config.SystemCommon.getClusterServerUrl();
}

BodyPO bpo=(BodyPO)request.getAttribute("bodyInfo");
String bodyId=(String)request.getAttribute("bodyId");
String memberId=(String)request.getAttribute("memberId");
String status=(String)request.getAttribute("status");
List opinion=(List)request.getAttribute("opinion");
List bodyEx=(List)request.getAttribute("bodyEx");
List subOpinion=(List)request.getAttribute("subOpinion");
String posterId=bpo.getPosterId();
String nodeId=request.getParameter("nodeId");
String curMemberId=request.getParameter("memberId");
String fromStatus=request.getParameter("fromStatus");
String relProjectName=(String)request.getAttribute("relProjectName");
Object[] obj;
String curUserId=(String)session.getAttribute(WapUtil.EMP_ID);
String comeFrom=(String)request.getAttribute("comeFrom");
String type=(String)request.getAttribute("type");
String beginIndex=request.getAttribute("beginIndex")==null?"0":request.getAttribute("beginIndex").toString();
//处理页面跳转方向
String url="";
String title_html="协同工作";
if(type!=null&&type.equals("workdealwith")){
    title_html="待办事项";
	url=path+"/WorkDealWithAction.do?action=list&status=0&beginIndex="+beginIndex;	
}else{
	if(status.equals("202and1002")||bodyEx!=null && bodyEx.size()>0){
	url=path+"/WapCoopAction.do?action=list&status=202and1002&beginIndex="+beginIndex;
    }else{
	url=path+"/WapCoopAction.do?action=list&status=10&beginIndex="+beginIndex;
	} 
}

      out.write("\r\n<HTML xmlns=\"http://www.w3.org/1999/xhtml\">\r\n<HEAD>\r\n<TITLE>");
      out.print(title_html );
      out.write("</TITLE>\r\n<META content=\"text/html; charset=UTF-8\" http-equiv=Content-Type>\r\n<META name=viewport content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\">\r\n<META name=apple-touch-fullscreen content=YES>\r\n<META name=apple-mobile-web-app-capable content=no>\r\n<LINK rel=apple-touch-icon href=\"");
      out.print(path);
      out.write("/wap2/images/iphone.jpg\">\r\n<LINK rel=stylesheet type=text/css href=\"");
      out.print(path);
      out.write("/wap2/css/main_3g.css\">\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path);
      out.write("/wap2/js/cookie.js\"></SCRIPT>\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path);
      out.write("/wap2/js/util.js\"></SCRIPT>\r\n<SCRIPT type=application/x-javascript src=\"");
      out.print(path);
      out.write("/wap2/js/frost.js\"></SCRIPT>\r\n\r\n<SCRIPT language=JavaScript>\r\n<!--\r\nfunction init(){\r\n    //...\r\n    var backUrl = \"");
      out.print(path);
      out.write("/login.jsp?action=logout\";\r\n    setTimeout(scrollTo, 100, 0, 1);\r\n}\r\n\r\nfunction gotoPage(url, flag){\r\n    if(flag){\r\n        location.href=encodeURI(url);\r\n    }\r\n}\r\n\r\nfunction findParent(node, localName)\r\n{\r\n\twhile (node && (node.nodeType != 1 || node.localName.toLowerCase() != localName))\r\n\t\tnode = node.parentNode;\r\n\treturn node;\r\n}\r\n\r\nfunction checkOnlineUser(){\r\n\tvar url = encodeURI(\"");
      out.print(path);
      out.write("/common/checkOnlineUser.jsp\");\r\n\tvar resulthtml = XmlHttpHelper.transmit(false, \"get\", \"text\", url, null, null);\r\n\t//alert(resulthtml);\r\n\tvar result = resulthtml.replace(/\\r|\\n/gm,'');//alert(result);\r\n\treturn result;\r\n}\r\n\r\nfunction displaySearch(flag){\r\n    if(flag==1){\r\n        document.all.dd.style.display=\"none\";\r\n        document.all.search.style.display=\"\";\r\n    }else{\r\n        document.all.dd.style.display=\"\";\r\n        document.all.search.style.display=\"none\";\r\n    }\r\n}\r\nfunction changeComment(){\r\n\tvar content=document.getElementById(\"commentSelect\").value;\r\n\tdocument.getElementById(\"reply_content\").value=content;\r\n}\r\n//-->\r\n</SCRIPT>\r\n\r\n<META name=GENERATOR content=\"MSHTML 8.00.6001.19154\">\r\n</HEAD>\r\n\r\n<body onload=\"init();\">\r\n\t<div class=\"main\">\r\n\t\t<div id=\"top\">\r\n\t\t\t<span id=\"lp2\"><div class=\"btn_3\"><a href=\"");
      out.print(url );
      out.write("\">返回</a></div></span>\r\n\t\t\t<span id=\"title2\"></span>\r\n\t\t</div>\r\n\t\t<div class=\"f_1\">");
      out.print(bpo.getTitle() );
      out.write("</div>\r\n\t\t");

			String postTime=bpo.getPostTime().toString();
			if(postTime.lastIndexOf(":")>0){
				postTime=postTime.substring(0,postTime.lastIndexOf(":"));
			}
		
      out.write("\r\n\t\t<div class=\"box_2\">\r\n\r\n\t\t\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">发起人：</div>\r\n\t\t\t\t<div class=\"t2\">");
      out.print(bpo.getPosterName() );
      out.write("</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t\t\t</div>\r\n\t\t\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">发起时间：</div>\r\n\t\t\t\t<div class=\"t2\">");
      out.print(postTime );
      out.write("</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t\t\t</div>\r\n\t\t\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">重要程度：</div>\r\n\t\t\t\t<div class=\"t2\">\r\n\t\t\t\t");
int level=bpo.getLevel().intValue();
          		  if(level==10){
		              out.print("一般");
		          }else if(level==20){
		              out.print("重要");
		          }else if(level==30){
		              out.print("非常重要");
		          }
      out.write("\r\n\t\t\t\t</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t\t\t</div>\r\n\t\t\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">办理期限：</div>\r\n\t\t\t\t<div class=\"t2\">\r\n\t\t\t\t");

				if(bpo.getHasTerm().intValue()==1){
				Date term=bpo.getTerm();
				
      out.write("\r\n\t\t\t\t");
      out.print(term.getYear()+1900 );
      out.write('-');
      out.print(term.getMonth()+1 );
      out.write('-');
      out.print(term.getDate() );
      out.write("\r\n\t\t\t\t");
}else{
					 out.print("无");
			    }
      out.write("\r\n\t\t\t\t</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t\t\t</div>\r\n\t\t\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">相关项目：</div>\r\n\t\t\t\t<div class=\"t2\">\r\n\t\t\t\t");
if(request.getAttribute("relProjectName")==null){
      out.write("无\r\n                ");
}else{
      out.write("\r\n                ");
      out.print(request.getAttribute("relProjectName") );
      out.write(" \r\n                ");
}
      out.write("\r\n\t\t\t\t</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t\t\t</div>\r\n\t\t\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">正文:</div>\r\n\t\t\t\t");

				
				String uploadHtml=bpo.getContent();
				
				
      out.write("\r\n\t\t\t\t\r\n\t\t\t\t<div class=\"t2\">");
      out.print(TestMain.filter(uploadHtml) );
      out.write("</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t\t\t</div>\r\n\t\t\t");

			//显示正文补充
			if(bodyEx!=null && bodyEx.size()>0){			
			
      out.write("\r\n\t\t\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">正文补充：</div>\r\n\t\t\t\t<div class=\"t2\">\r\n\t\t\t\t");

				for(int i=0;i<bodyEx.size();i++){
	               	 obj=(Object[])bodyEx.get(i);
	               	 postTime=obj[1].toString();
	               	 if(postTime.lastIndexOf(":")>0){
	               		 postTime=postTime.substring(0,postTime.lastIndexOf(":"));
	               	 }
	            
      out.write("\r\n\t            <DIV style=\"BORDER-TOP: #cde7ff 1px dashed; OVERFLOW: hidden; HEIGHT: 1px\"></DIV>\r\n\t\t\t\t<div class=\"user\">");
      out.print(postTime );
      out.write(' ');
      out.print(obj[2] );
      out.write("</div>\r\n\t\t\t\t<P class=user_wd><strong class=\"span\">[");
      out.print(obj[2] );
      out.write("]</strong>");
      out.print(TestMain.filter(obj[0].toString()));
      out.write("</P>\r\n\t\t\t\r\n\t            ");

				}
	            
      out.write("\r\n\t\t\t\t</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t\t\t</div>\r\n\t\t\t");
}
			List attach=(List)request.getAttribute("bodyAttach");
			if(attach.size()>0){
      out.write("\t\t\t\r\n\t\t\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">附件：</div>\r\n\t\t\t\t<div class=\"t2\">\r\n\t\t\t\t");
Object[] aObjects=null;
				for(int i=0;i<attach.size();i++){
					aObjects=(Object[])attach.get(i);
					//out.println(aObjects[1].toString());
					String src="0000";
					if(aObjects[2].toString()!=null && aObjects[2].toString().length()>6 && aObjects[2].toString().substring(4,5).equals("_")){
						src=aObjects[2].toString().substring(0,4);
					}else{
							src="0000";
					}
				
      out.write("\r\n\t\t\t\t<a href=\"");
      out.print(filePath);
      out.write("/upload/");
      out.print(src);
      out.write("/cooperate/");
      out.print(aObjects[2]+"" );
      out.write('"');
      out.write('>');
      out.print(aObjects[1]+"" );
      out.write("</a><br />\r\n\t\t\t\t");
 }
      out.write("\r\n\t\t\t\t</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t\t\t</div>");
} 
      out.write("\r\n\t\t\t\r\n\t\t\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">办理意见</div>\r\n\t\t\t\t<div class=\"t2\">\r\n\t\t\t\t");

				//显示办理意见
				String opinionId,memberName,opinionContent,isHidden,empId;
				for(int i=0;i<opinion.size();i++){
				        obj=(Object[])opinion.get(i);
				        opinionId=obj[0]+"";
				        isHidden=obj[1]+"";
				        postTime=obj[3]+"";
				        if(obj[4]==null){
				        	opinionContent="";
				        }else{
				        	opinionContent=obj[4].toString();				       
				        }
				        empId=obj[5]+"";
				        //隐藏意见只能有发起者和本人看到
				        if("1".equals(isHidden)){
				        if(posterId.equals(curUserId) || empId.equals(curUserId)){
				            opinionContent="[隐藏]"+opinionContent;
				        }else{
				             opinionContent="[隐藏]";
				        }
				        }
				        if(postTime.indexOf(".")>0){
				            postTime=postTime.substring(0,postTime.indexOf("."));
				        }
				                                     	
				
      out.write("\r\n\t\t\t\t<DIV style=\"BORDER-TOP: #cde7ff 1px dashed; OVERFLOW: hidden; HEIGHT: 1px\"></DIV>\r\n\t\t\t\t\t<div class=\"user\">");
      out.print(postTime );
      out.write(' ');
      out.print(obj[2] );
      out.write("</div>\r\n\t\t\t\t\t<P class=user_wd>");
      out.print(HTMLEncoding.htmlEncoding(opinionContent));
      out.write("</P>\r\n\t\t\t\t");
 
				}
				
      out.write("\r\n\t\t\t\t</div>\r\n\t\t\t\t<div class=\"clear\"></div>\r\n\t\t\t</div>\r\n\t\t\t\r\n\t\t\t");
if(status.equals("10")){ 
      out.write("\r\n\t\t\t<form name=\"form1\" action=\"");
      out.print(path);
      out.write("/wap2/workitem/dealwithAccess_3g.jsp?bodyId=");
      out.print(bodyId );
      out.write("&memberId=");
      out.print(memberId );
      out.write("&type=");
      out.print(type );
      out.write("&beginIndex=");
      out.print(beginIndex );
      out.write("\" method=\"post\">\r\n\t\t\t<input type=\"hidden\" name=\"comeFrom\" value=\"");
      out.print(comeFrom );
      out.write("\"/>\r\n\t\t\t<div class=\"list_2\">\r\n\t\t\t\t<div class=\"t1\">办理意见：</div>\r\n\t\t\t\t<div class=\"t2\">\r\n\t\t\t\t");

				java.util.List opinionList = new com.js.oa.jsflow.service.WorkFlowBD().getOffiDict(session.getAttribute("userId").toString(), session.getAttribute("domainId").toString());
				if(opinionList!=null){
				
      out.write("\r\n\t\t\t\t<select name=\"commentSelect\" id=\"commentSelect\" onchange=\"changeComment()\">\r\n\t\t\t\t\t<option value=\"\">请选择常用语</option>\r\n\t\t\t\t\t");

					String pcontent;
					for(int li=0;li<opinionList.size();li++){
						pcontent=opinionList.get(li).toString();
						
      out.write("\r\n\t\t\t\t\t<option value=\"");
      out.print(pcontent);
      out.write('"');
      out.write('>');
      out.print(pcontent);
      out.write("</option>\r\n\t\t\t\t\t");
} 
      out.write("\r\n\t\t\t\t</select>\r\n\t\t\t\t<br>\r\n\t\t\t\t");
} 
      out.write("\r\n\t\t\t\t<TEXTAREA name=reply_content id=reply_content maxlength=\"1000\"></TEXTAREA>\r\n\t\t\t    </div>\r\n\t\t\t    <div class=\"clear\"></div>\r\n\t\t\t</div>\r\n\t\t\t<div class=\"list_2\" align=\"center\">\r\n\t\t\t<input type=\"button\" class=\"button2\" value=\"办理\" onclick=\"javascript:document.form1.submit();\" />\r\n\t\t\t\t</div></p>\r\n\t\t\t    <div class=\"clear\"></div>\r\n\t\t\t</div>\r\n\r\n\t\t\t</form>\r\n\t\t\t");
} 
      out.write("\r\n\t\t\t\t\t\r\n\r\n\t\t</div>\r\n\t\t<div class=\"height1\"></div>\r\n\t</div>\r\n</body>\r\n</html>\r\n");
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