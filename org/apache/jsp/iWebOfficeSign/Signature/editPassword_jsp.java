/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:08:49 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.iWebOfficeSign.Signature;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.sql.ResultSet;

public final class editPassword_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("java.sql.ResultSet");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhtml;

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
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fhtml.release();
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

String signatureId=request.getParameter("signatureId");
boolean mResult=false;

DBstep.iDBManager2000 DbaObj = new DBstep.iDBManager2000();

String useName = "";
String useUserId = "";
String useOrgId = "";
String useGroupId = "";
String useId="";

if (DbaObj.OpenConnection()){
  String mSql = "select * from Signature where SignatureID = " + signatureId;
  try{
    ResultSet result=DbaObj.ExecuteQuery(mSql) ;
    if (result.next()){
		useName=result.getString("useName");
		useUserId=result.getString("useUserId");
		useOrgId=result.getString("useOrgId");
		useGroupId=result.getString("useGroupId");
		
		if(useName==null){
			useName="";
		}else{
			if(useUserId!=null){
				useId+=useUserId;
			}
			if(useOrgId!=null){
				useId+=useOrgId;
			}
			if(useGroupId!=null){
				useId+=useGroupId;
			}
		}
	}
	} catch(Exception e){
        System.out.println(e.toString());
    }
    DbaObj.CloseConnection();
}

      out.write('\r');
      out.write('\n');
      //  html:html
      org.apache.struts.taglib.html.HtmlTag _jspx_th_html_005fhtml_005f0 = (org.apache.struts.taglib.html.HtmlTag) _005fjspx_005ftagPool_005fhtml_005fhtml.get(org.apache.struts.taglib.html.HtmlTag.class);
      boolean _jspx_th_html_005fhtml_005f0_reused = false;
      try {
        _jspx_th_html_005fhtml_005f0.setPageContext(_jspx_page_context);
        _jspx_th_html_005fhtml_005f0.setParent(null);
        int _jspx_eval_html_005fhtml_005f0 = _jspx_th_html_005fhtml_005f0.doStartTag();
        if (_jspx_eval_html_005fhtml_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n<head>\r\n<title>修改签章</title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\" />\r\n<link href=\"/jsoa/skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script type=\"text/javascript\" src=\"/jsoa/js/resource/");
            out.print(session.getAttribute("org.apache.struts.action.LOCALE"));
            out.write("/CommonResource.js\" type=\"text/javascript\"></script>\r\n<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n<SCRIPT language=\"javascript\" src=\"/jsoa/js/openEndow.js\"></SCRIPT>\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject2.css\">\r\n<script type=\"text/javascript\">\r\nfunction init(){\r\n\t");

	if(request.getAttribute("update")!=null && "update"==request.getAttribute("update")){
	
            out.write("\r\n\t\tvar hhref=\"/jsoa/iWebOfficeSign/Signature/SignatureUserList.jsp\";\r\n\t\topener.location.href= hhref ;\r\n\t\twindow.close();\r\n\t");

	}
	
            out.write("\r\n}\r\n</script>\r\n</head>\r\n<body leftmargin=\"0\" scroll=\"auto\" topmargin=\"0\" class=\"MainFrameBox Pupwin\">\r\n\r\n<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"docBoxNoPanel\">\r\n<tr>\r\n<td valign=\"top\">\r\n\r\n<form name=\"frm\" action=\"/jsoa/iWebOfficeSign/Signature/savePassword.jsp\" method=\"post\">\r\n<input type=\"hidden\" name=\"signatureId\" id=\"signatureId\" value=\"");
            out.print(signatureId );
            out.write("\" />\r\n<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\"> \r\n                          <tr>\r\n                             <td width=\"60\" nowrap=\"nowrap\">旧密码：</td>\r\n                             <td>\r\n                             <input type=\"password\" name=\"oldPass\" id=\"oldPass\" value=\"\" style=\"width: 300px;\">\r\n                             </td>\r\n                          </tr>\r\n                          <tr>\r\n                             <td nowrap=\"nowrap\">新密码：</td>\r\n                             <td>\r\n                             <input type=\"password\" name=\"newPass\" id=\"newPass\" value=\"\" style=\"width: 300px;\">\r\n                             </td>\r\n                          </tr>\r\n                           <tr>\r\n                             <td nowrap=\"nowrap\">密码确认：</td>\r\n                             <td>\r\n                             <input type=\"password\" name=\"confirmPass\" id=\"confirmPass\" value=\"\" style=\"width: 300px;\">\r\n                             </td>\r\n                          </tr>\r\n                          <tr>\r\n");
            out.write("\t\t\t\t\t\t\t  <td nowrap=\"nowrap\">使用范围：</td>\r\n\t\t\t\t\t\t\t  <td>\r\n\t\t\t\t\t\t\t     <input type=\"hidden\" name=\"useId\" id=\"useId\" value=\"");
            out.print(useId);
            out.write("\"> \r\n\t\t\t\t\t\t\t     <input type=\"text\" readonly=\"true\" name=\"useName\" id=\"useName\" value=\"");
            out.print(useName);
            out.write("\" style=\"width: 300px;\" \r\n\t\t\t\t\t\t\t     onclick=\"openEndow('useId','useName',document.getElementById('useId').value,document.getElementById('useName').value,'user','no','userorggroup','");
            out.print(session.getAttribute("grantRange"));
            out.write("');\">\r\n\t\t\t\t\t\t\t  </td>\r\n\t\t\t\t\t\t  </tr>\r\n\t\t<tr>\r\n\t      <td colspan=2 height=\"45\" valign=\"bottom\">\r\n\t\t\t<input type=\"button\" class=\"btnButton4font\" onclick=\"javascript:submitForm();\" value=\"保存退出\"/><button class=\"btnButton2font\" onclick=\"window.location.reload();\">重置</button><button class=\"btnButton2font\" onclick=\"javascript:window.close()\">退出</button>\r\n\t\t  </td>\r\n       </tr>\r\n</table>\r\n</form>\r\n</td>\r\n</tr>\r\n<tr><td>&nbsp;</td></tr>\r\n</table>\r\n</body>\r\n<script language=\"javascript\">\r\nfunction save(){\r\n\tvar pass1 = document.all.newPass;\r\n\tvar pass2 = document.all.confirmPass;\r\n\tif(pass1.value != pass2.value){\r\n\t\talert(\"两次输入密码不一致，请检查！\");\r\n\t\tdocument.all.newPass.focus();\r\n\t} else{\r\n\t\tif(\"\"==pass1.value && \"\"==pass2.value){\r\n\t\t\talert(\"未填写新密码，签章原密码将保持不变！\");\r\n\t\t}\r\n\t\t//提交表单\r\n\t\tdocument.frm.submit();\r\n\t}\r\n}\r\nfunction submitForm(){\r\nvar xmlhttp;\r\n\t//开始初始化XMLHttpRequest对象\r\n\tif(window.XMLHttpRequest) { //Mozilla 浏览器\r\n\t\txmlhttp = new XMLHttpRequest();\r\n\t\tif (xmlhttp.overrideMimeType) {//设置MiME类别\r\n\t\t\txmlhttp.overrideMimeType('text/xml');\r\n");
            out.write("\t\t}\r\n\t}\r\n\telse if (window.ActiveXObject) { // IE浏览器\r\n\t\ttry {\r\n\t\t\txmlhttp = new ActiveXObject(\"Microsoft.XMLHTTP\");\r\n\t\t} catch (e) {\r\n\t\t\ttry {\r\n\t\t\t\txmlhttp = new ActiveXObject(\"MSXML2.XMLHTTP\");\r\n\t\t\t} catch (e) {\r\n\t\t\t\talert(\"您看到此信息，说明您的浏览器不支持XML解析。\\r\\n请在弹出页面中下载XML解析器并安装！\");\t\t\t\t\r\n\t\t\t}\r\n\t\t}\r\n\t}\r\n\txmlhttp.onreadystatechange = function() {\r\n\t\tif (xmlhttp.readyState == 4) {\t\r\n\t\t    if (xmlhttp.status < 400) {// only if \"OK\"\r\n\t\t    \tif(xmlhttp.responseText.indexOf(\"OK\")>=0){\r\n\t\t    \t\t//原密码正确\r\n\t\t    \t\tsave();\r\n\t\t    \t}else{\r\n\t\t    \t\talert(\"原密码输入错误，请重试！\");\r\n\t\t    \t\tdocument.all.oldPass.select();\r\n\t\t    \t}\r\n\t\t\t} \r\n\t\t}\r\n\t}\r\n\t\t\r\n\txmlhttp.open(\"GET\",((window.location.href).substring(0,(window.location.href).indexOf(\"/jsoa\")))+\"/jsoa/iWebOfficeSign/Signature/editPasswordAjax.jsp?signatureId=\"+document.all.signatureId.value+\"&password=\"+document.all.oldPass.value+\"&date=\"+new Date(),false);\r\n\txmlhttp.send();\r\n}\r\n\r\n</script>\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n");
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
