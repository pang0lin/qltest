/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:00:30 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.weixin.ltgg;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.main.TestMain;
import com.js.oa.bbs.po.ForumPO;
import com.js.wap.util.WapUtil;
import com.js.system.manager.service.ManagerService;
import java.util.List;
import java.text.SimpleDateFormat;

public final class ltggInfo_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

public String getCont(Object o) {
		String cont = "";
		if (o != null && !"".equals(o.toString())
				&& !"null".equals(o.toString())) {
			cont = o.toString();
		}
		return cont;
	}
  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.util.List");
    _jspx_imports_classes.add("com.main.TestMain");
    _jspx_imports_classes.add("com.js.wap.util.WapUtil");
    _jspx_imports_classes.add("com.js.oa.bbs.po.ForumPO");
    _jspx_imports_classes.add("java.text.SimpleDateFormat");
    _jspx_imports_classes.add("com.js.system.manager.service.ManagerService");
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

      out.write("\r\n\r\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n\r\n\r\n\r\n\r\n");

	request.setCharacterEncoding("UTF-8");
	String path = request.getContextPath();
	String flag=(String)request.getAttribute("flag");
	Long userId = Long.valueOf(session.getAttribute("userId")+"");
	boolean isAnonymous = new ManagerService().hasRight(userId+"", "06*01*01");
	//?????????????????????APP????????????
	String loginType = null==session.getAttribute("loginType") ? "wap" : session.getAttribute("loginType").toString();
	int beginIndex=Integer.parseInt(request.getParameter("beginIndex")==null?"0":request.getParameter("beginIndex"));
	String type=request.getAttribute("type").toString();
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	int begin=0;
	String title="";
	ForumPO forumPO=new ForumPO();
	List<ForumPO> list=null;
	String style = "";
	if("2".equals(type)){
		title="??????";
		forumPO=(ForumPO)request.getAttribute("forum");
	}else{
	    begin=Integer.valueOf(request.getAttribute("begin").toString());
		title="??????";
		list=(List<ForumPO>)request.getAttribute("list");
		forumPO = (ForumPO) request.getAttribute("forumPO");
	}
	if(2 == forumPO.getForumType()){	// ???????????????????????????
		style = "style=\"display: none;\"";
	}
	String url="";
	String operate=request.getAttribute("operate")==null?"":request.getAttribute("operate").toString();
	System.out.println("operate:"+operate);
	if("bbsList".equals(operate)){
		url="/jsoa/weiXinLtggAction.do?action=bbsList";
	}
	if("seeList".equals(operate)){
		url="/jsoa/weiXinLtggAction.do?action=seeList";
	}

      out.write("\r\n<!DOCTYPE html>\r\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/wap2/commonImport.jsp", out, false);
      out.write("\r\n<html>\r\n\t<head>\r\n\t\t<TITLE>");
      out.print(title);
      out.write("????????????</TITLE>\r\n\t\t<META content=\"text/html; charset=UTF-8\" http-equiv=Content-Type>\r\n\t\t<META name=viewport content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\">\r\n\t\t<META name=apple-touch-fullscreen content=YES>\r\n\t\t<META name=apple-mobile-web-app-capable content=no>\r\n\t\t<link rel=\"stylesheet\" href=\"/jsoa/css/weixin/search.css\">\r\n\t\t<link rel=\"stylesheet\" href=\"/jsoa/css/weixin/weixin_zsgl.css\">\r\n\t\r\n\r\n\t\t<style>\r\n\t\t.reply_bar {\r\n\t\t    background: #fff;\r\n\t\t    color: #8f8e93;\r\n\t\t    font-weight: 700;\r\n\t\t    border-bottom: 1px solid #ccc;\r\n\t\t    padding:10px;\r\n\t\t   \r\n\t\t}\r\n\t\t</style>\r\n\t\t<script type=\"text/javascript\">\r\n\t\t");

		if("wap".equals(loginType) && null!=request.getAttribute("flag") && "1".equals(request.getAttribute("flag").toString())){
				if(!"".equals(url)){
		
      out.write("\r\n\t\t\t\twindow.location.href=\"");
      out.print(url);
      out.write("\";\r\n\t\t");
	}else{
      out.write("\r\n\t\t\t\tloadURL('ltgg');\r\n\t\t");
	}
		}
		
      out.write("\r\n\t\t</script>\r\n\t</head>\r\n\t\r\n\t<body onload=\"setHeader('javascript:closeWindow();', '");
      out.print(title);
      out.write("????????????');\" class=\"wapcss\">\r\n\t\t<div class=\"zsglform\" ");
if("weixin".equals(loginType)) {
      out.write(" style=\"padding-bottom: 60px;\" ");
}
      out.write(">\r\n\t\t\t\t");

				String id = "";
				String classId = "";
				if("bbs".equals(type)){
					id = request.getAttribute("id").toString();
					classId=request.getAttribute("classId").toString();
					int size=Integer.valueOf(request.getAttribute("size").toString());
					
      out.write("\r\n\t\t\t\t\t<!-- ????????????start -->\r\n\t\t\t\t\t<div class=\"Zsglitem\">\r\n\t\t\t\t\t\t<div class=\"zsgltitle\">\r\n\t\t\t\t\t\t\t\t<b>");
      out.print(forumPO.getForumTitle()!=null?forumPO.getForumTitle():"");
      out.write("</b>\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t<div class=\"title_bar\">\r\n\t\t\t\t        <table width=\"98%\">\r\n\t\t\t       \t\t   <tr>\r\n\t\t\t       \t\t   ");

			       		   String ForumAuthor=forumPO.getForumAuthor();
			       		   String niming=forumPO.getAnonymous()+"";
			       		   if("1".equals(niming)){
			       			ForumAuthor="??????";
			       		   }
			       		   
      out.write("\r\n\t\t\t       \t\t    <td class=\"zsgltitle_bar\" >");
      out.print(ForumAuthor);
      out.write("&nbsp;");
      out.print("????????????"+forumPO.getForumIssueTime().toString().substring(5,16) );
      out.write("</td>\r\n\t\t\t       \t\t    <td><a style=\"text-decoration: none;\"  href=\"javascript:\" ><img alt=\"??????\"  style=\"width:20px;height: 20px;\" border='0' src='/jsoa/images/weixin/pinglun.png'/></a></td>\r\n\t\t\t       \t\t    <td style=\"color: #999999;\">");
      out.print(Integer.parseInt(request.getAttribute("size")+"") );
      out.write("</td>\r\n\t\t\t       \t\t   </tr>\r\n\t\t\t       \t    </table>  \r\n\t\t\t\t    </div>\r\n\t\t\t\t\t<div class=\"zsgllineitem\"></div>\r\n\t\t\t\t\t<div class=\"zsglContent\" style=\"overflow-x:auto\">\r\n\t\t\t\t\t\t");
      out.print(TestMain.filter(getCont(forumPO.getForumContent())));
      out.write("\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t");
if(forumPO.getForumAttachName()==null||(""+forumPO.getForumAttachName()).equals("")){ 
					
					} else {
					  String[] realFileArray = (""+forumPO.getForumAttachName()).split("\\|");
					  String[] saveFileArray = (""+forumPO.getForumAttachSave()).split("\\|");
				      if(realFileArray!=null&&realFileArray.length>0){
				      
      out.write("\r\n\t\t\t\t      <div class=\"zsgllineitem\"></div>\r\n\t\t\t\t      <div class=\"Zsglitemfujian\" >\r\n        \t\t\t\t<div class=\"zsgl_fujian\">?????????\r\n\t\t\t\t      ");

				       for(int k=0;k<realFileArray.length;k++){
				          String docSaveName=saveFileArray[k];
				     
      out.write("\r\n\t\t\t\t\t            ");
if(k==0) {
      out.write("<br/>");
} 
      out.write("\r\n\t\t\t\t\t\t\t\t<a style=\"text-decoration: none;\" href=\"javascript:showHtmlObject('");
      out.print(docSaveName);
      out.write("','0','forum');\">\r\n\t\t\t\t\t\t\t\t");
      out.print(realFileArray[k]);
      out.write("\r\n\t\t\t\t\t\t\t\t</a><br/>\r\n\t               ");
}
      out.write("\r\n\t               </div>\r\n        \t\t   </div>\r\n\t               ");
}} 
      out.write("\t\r\n\t\r\n\t\t\t\t\t<!-- ?????? -->\r\n\t\t\t\t\t<div class=\"zsgllineitem\"></div>\r\n\t\t\t\t\t<div class=\"reply_bar\" >\r\n\t\t\t\t\t\t<div>??????(");
      out.print(Integer.parseInt(request.getAttribute("size")+"") );
      out.write(")</div>\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t<div class=\"listcomment\">\r\n\t\t\t\t\t");

					for(int i=0;i<list.size();i++){
						forumPO=list.get(i);
						String forumName="";
						if(forumPO.getAnonymous()==0){
					    	forumName = forumPO.getForumAuthor();
						}else if(isAnonymous){
							forumName=forumPO.getForumAuthor()+"(??????)";
						}else{
							forumName="(??????)";
						}
						String userAccount=com.js.system.util.StaticParam.getUserAccountsByEmpId(forumPO.getForumAuthorId()+"");
						
						
      out.write("\r\n\t\t\t\t\t\t <div class=\"item\">\r\n\t\t\t\t\t\t\t  <div class=\"picture\">\r\n\t\t\t\t\t\t\t\t<img class=\"picshow\"\r\n\t\t\t\t\t\t\t\t\tsrc='/jsoa/weixin/common/getUserAvatar.jsp?userId=");
      out.print(userAccount);
      out.write("' />\r\n\t\t\t\t\t\t\t  </div>\r\n\t\t\t\t\t\t\t  <div class=\"content\">\r\n\t\t\t\t\t\t\t\t<div class=\"first\">\r\n\t\t\t\t\t\t\t\t\t<div class=\"title\">");
      out.print(forumName);
      out.write("</div>\r\n\t\t\t\t\t\t\t\t\t<div class=\"time\">");
      out.print(sdf.format(forumPO.getForumIssueTime()).toString().substring(5, 16));
      out.write("</div>\r\n\t\t\t\t\t\t\t \t</div>\r\n\t\t\t\t\t\t\t \t<div class=\"second\">");
      out.print(getCont(forumPO.getForumContent()));
      out.write("</div>\r\n\t\t\t\t\t\t     </div>\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t");
}
      out.write("\r\n\r\n\t\t\t\t<!-- ?????? -->\r\n\t\t");

		int curNum = beginIndex/WapUtil.LIMITED + 1;	// ????????????
		int totalNum = size%WapUtil.LIMITED > 0 ? size/WapUtil.LIMITED+1 : size/WapUtil.LIMITED;	// ?????????
		
		int upNum = (beginIndex/WapUtil.LIMITED) - 1;
		int upIndex = upNum * WapUtil.LIMITED;
		int nextNum = (beginIndex/WapUtil.LIMITED) + 1;
		int nextIndex = nextNum * WapUtil.LIMITED;
		
        
		String upUrl = "#", nextUrl = "#", upUrlFont = "", nextUrlFont = "";
		if (size > WapUtil.LIMITED) {
			if (upIndex >= 0){
				upUrl = path + "/weiXinLtggAction.do?action=bbsInfo&amp;forumId=" + id + "&amp;beginIndex=" + upIndex + "&amp;begin=" + upIndex;
				
			} else{
				upUrl = "javascript:void(0);";
				upUrlFont = "grayFont";
			}
			if (nextIndex < size){
				nextUrl = path + "/weiXinLtggAction.do?action=bbsInfo&amp;forumId=" + id + "&amp;beginIndex=" + nextIndex + "&amp;begin=" + nextIndex;
				
			} else{
				nextUrl = "javascript:void(0);";
				nextUrlFont = "grayFont";
			}
			
      out.write("\r\n\t\t\t<div class=\"bottomDiv\">\r\n\t\t\t\t<a href=\"");
      out.print(upUrl );
      out.write("\">\r\n\t\t\t\t\t<div class=\"up ");
      out.print(upUrlFont );
      out.write("\">?????????</div>\r\n\t\t\t\t</a>\r\n\t\t\t\t<div class=\"page grayFont\">");
      out.print(curNum );
      out.write('/');
      out.print(totalNum );
      out.write("</div>\r\n\t\t\t\t<a href=\"");
      out.print(nextUrl );
      out.write("\">\r\n\t\t\t\t\t<div class=\"down ");
      out.print(nextUrlFont );
      out.write("\">?????????</div>\r\n\t\t\t\t</a>\r\n\t\t\t</div>\r\n\t\t\t");

			}
		   }
		
      out.write("\r\n\t\t\t</div>\r\n\t\t</div>\r\n\r\n\t\t\r\n\t\t\t\r\n\t\t   <div class=\"footer\" ");
      out.print(style );
      out.write(">\r\n\t      \t  <div class=\"buttons\">\r\n\t\t    \t\t<!-- ?????? -->\r\n\t\t\t\t\t<form style=\"width: 100%; height: 100%;\" name=\"form1\" id=\"form1\" action=\"\" method=\"post\">\r\n\t\t\t\t\t\t<div class=\"input\">\r\n\t\t                   \t<input type=\"hidden\" name=\"forumId\" value=\"");
      out.print(id );
      out.write("\" />\r\n\t\t                   \t<input type=\"hidden\" name=\"flag\" value=\"");
      out.print(flag );
      out.write("\" />\r\n\t\t\t                <input type=\"hidden\" name=\"classId\" value=\"");
      out.print(classId );
      out.write("\" />\r\n\t\t\t                <input type=\"hidden\" name=\"title1\" id=\"title1\" value=\"Re:");
      out.print(request.getAttribute("title").toString());
      out.write("\" />\r\n\t\t\t\t\t\t\t<input type=\"text\" id=\"content1\" name=\"content1\"/>\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t<div class=\"replyDiv\">\r\n\t\t\t\t\t\t\t<div class=\"reply\" onclick=\"javascript:saveClose();\">??????</div>\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t</form>\r\n\t\t\t  </div>\r\n\t\t  </div>\t\r\n\t\t\t\r\n\t\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/weixin/common/weixin_messageReminders.jsp", out, false);
      out.write("\r\n\t\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/weixin/workflow/daibanflow/weixinshowhtml.jsp", out, false);
      out.write("\r\n\t\t<script type=\"text/javascript\">\r\n\t\t");

		if("1".equals(flag)){
			String isCheckExam = request.getAttribute("isCheckExam").toString();
			String isClassOwner = request.getAttribute("isClassOwner").toString();
			if("weixin".equals(loginType)){
				if("1".equals(isCheckExam) && !"1".equals(isClassOwner)){
					
      out.write("\r\n\t\t\t\t\tweixinMessageReminder(\"alert\", \"?????????\", \"?????????????????????????????????????????????\", \"closeWindow();\");\r\n\t\t\t\t\t");

				} else{
					
      out.write("closeWindow();");

				}
			} else if("wap".equals(loginType)){
				if("1".equals(isCheckExam) && !"1".equals(isClassOwner)){
					
      out.write("\r\n\t\t\t\t\tweixinMessageReminder(\"alert\", \"?????????\", \"?????????????????????????????????????????????\", \"loadURL();\");\r\n\t\t\t\t\t");

				} else{
					
      out.write("loadURL('ltgg');");

				}
			}
			
		}
		
      out.write("\r\n\t\tfunction saveClose(){\r\n\t\t\tvar title = document.getElementById(\"title1\").value;\r\n\t\t\tvar content = $.trim($(\"#content1\").val());\r\n\t\t\tif(content == \"\"){\r\n\t\t\t\tweixinMessageReminder(\"alert\", \"?????????\", \"??????????????????\", \"\");\r\n\t\t\t}else{\r\n\t\t\t\tvar type = \"\";\r\n\t\t\t\tvar isCheckExam = \"");
      out.print(request.getAttribute("isCheckExam").toString());
      out.write("\";\r\n\t\t\t\tvar isClassOwner = \"");
      out.print(request.getAttribute("isClassOwner").toString());
      out.write("\";\r\n\t\t\t\tif(isCheckExam==\"1\" && isClassOwner!=\"1\"){\r\n\t\t\t\t\ttype = \"&types=1\";\r\n\t\t\t\t}\r\n\t\t\t\tdocument.getElementById(\"form1\").action = \"");
      out.print(path);
      out.write("/weiXinLtggAction.do?action=reply&beginIndex=");
      out.print(beginIndex);
      out.write("&begin=");
      out.print(begin);
      out.write("\" + \r\n\t\t\t\t\t\t\t\"&title=\" + encodeURI(encodeURI(title)) + \"&content=\" + encodeURI(encodeURI(content)) + type+\"&operate=");
      out.print(operate);
      out.write("\";\r\n\t\t\t\tdocument.getElementById(\"form1\").submit();\r\n\t\t\t}\r\n\t\t}\r\n\r\n\t\tfunction gotoPage(url, flag){\r\n\t\t    if(flag){\r\n\t\t        location.href=encodeURI(url);\r\n\t\t    }\r\n\t\t}\r\n\t\t</script>\r\n\t</body>\r\n</html>\r\n");
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
