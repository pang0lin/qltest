/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:09:24 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.cooperate;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class co_005freopinion_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-logic.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-html.tld", Long.valueOf(1499751390000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
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

      out.write("\r\n\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

String opId=request.getParameter("opId");
java.util.List opinionList = new com.js.oa.jsflow.service.WorkFlowBD().getOffiDict(session.getAttribute("userId").toString(), session.getAttribute("domainId").toString());


      out.write("\r\n\r\n");
      //  html:html
      org.apache.struts.taglib.html.HtmlTag _jspx_th_html_005fhtml_005f0 = (org.apache.struts.taglib.html.HtmlTag) _005fjspx_005ftagPool_005fhtml_005fhtml.get(org.apache.struts.taglib.html.HtmlTag.class);
      boolean _jspx_th_html_005fhtml_005f0_reused = false;
      try {
        _jspx_th_html_005fhtml_005f0.setPageContext(_jspx_page_context);
        _jspx_th_html_005fhtml_005f0.setParent(null);
        int _jspx_eval_html_005fhtml_005f0 = _jspx_th_html_005fhtml_005f0.doStartTag();
        if (_jspx_eval_html_005fhtml_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n<head>\r\n<title>????????????</title>\r\n<link href=\"/jsoa/skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject2.css\">\r\n<style type=\"text/css\">\r\n<!--\r\n#noteDiv {\r\n\tposition:absolute;\r\n\twidth:240px;\r\n\theight:166px;\r\n\tz-index:1;\r\n\toverflow:auto;\r\n\tborder:1px solid #829FBB;\r\n\tdisplay:none;\r\n\tbackground-color:#ffffff;\r\n}\r\n\r\n#noteDiv1 {\r\n\tposition:absolute;\r\n\twidth:220px;\r\n\theight:126px;\r\n\tz-index:1;\r\n\toverflow:auto;\r\n\tborder:1px solid #829FBB;\r\n\tdisplay:none;\r\n}\r\n.divOver{\r\n    background-color:#003399;\r\n\tcolor:#FFFFFF;\r\n\tborder-bottom:1px dashed #cccccc;\r\n    width:100%;\r\n\theight:20px;\r\n\tline-height:20px;\r\n\tcursor:default;\r\n\tpadding-left:5px;\r\n}\r\n.divOut{\r\n    background-color:#ffffff;\r\n\tcolor:#000000;\r\n\tborder-bottom:1px dashed #cccccc;\r\n    width:100%;\r\n\theight:20px;\r\n\tline-height:20px;\r\n\tcursor:default;\r\n\tpadding-left:5px;\r\n}\r\n-->\r\n</style>\r\n</head>\r\n");


if(request.getAttribute("success")!=null && "1".equals(request.getAttribute("success").toString())){

            out.write("\r\n<script>\r\n   opener.location.reload();\r\n   window.close();\r\n</script>\r\n<body>\r\n</body>\r\n");
}else{ 
            out.write("\r\n<body leftmargin=\"0\" topmargin=\"0\" class=\"MainFrameBox Pupwin\" >\r\n<form name=\"frm\" action=\"/jsoa/BodyAction.do?flag=reopinion\" method=\"POST\">\r\n <input type=\"hidden\" name=\"bodyId\" value=\"\"/>\r\n <input type=\"hidden\" name=\"nodeId\" value=\"\">\r\n <input type=\"hidden\" name=\"opId\" value=\"");
            out.print(opId);
            out.write("\">\r\n\r\n<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"docBoxNoPanel\"> \r\n  <tr>\r\n     <td valign=\"top\">\r\n          <table width=\"100%\" border=\"0\" cellpadding=\"1\" cellspacing=\"0\">\r\n              <tr>\r\n                   <td width=\"10%\" nowrap=\"nowrap\" valign=\"top\">???????????????</td>\r\n                   <td width=\"80%\">\r\n                      <div align=\"right\"><a href=\"javascript:;\" onClick=\"getNote();\" onmouseout=\"hiddenNote();\" onmouseover=\"lockedNote();\" >?????????</a></div>\r\n\t\t\t\t\t\t<div id=\"noteDiv\" value=\"comment\" onmouseout=\"hiddenNote();\" onmouseover=\"lockedNote();\">\r\n\t\t\t\t\t\t\t");

							  if(opinionList!=null&&opinionList.size()>0){
								  String pcontent;
							   for(int i=0;i<opinionList.size();i++){
								  pcontent=opinionList.get(i).toString();
            out.write("\r\n\t\t\t\t\t\t\t\t  <div class=\"divOut\" onmouseover=\"lockedNote();this.className='divOver'\" onmouseout=\"this.className='divOut'\" onclick=\"setopinion(this)\">");
            out.print(pcontent);
            out.write("</div>\t\t\t\t   \r\n\t\t\t\t\t\t\t   ");
}
							}
							
            out.write("\r\n\t\t\t\t\t\t    <div align=\"right\" style=\"padding-top:5px;\"><a href=\"javascript:void(0);\"  onclick=\"JSMainWinOpenNew('/jsoa/personal_work/setup/personalwork_mywordsetup_add.jsp?isgov=isgov','','TOP=0,LEFT=0,scrollbars=yes,resizable=yes,width=600,height=300')\">[??????]</a></div>\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t<textarea style=\"display:none;\" name=\"opinion\" id=\"opinion\" rows=\"7\" cols=\"42\" style=\"width:99%\"></textarea>\r\n\t\t\t\t\t\t<IFRAME ID=\"newedit\" SRC=\"/jsoa/public/edit/ewebeditor.htm?id=opinion&style=comment&lang=");
            out.print(session.getAttribute("org.apache.struts.action.LOCALE"));
            out.write("\" FRAMEBORDER=\"0\" SCROLLING=\"no\" width=\"100%\" HEIGHT=\"150\"></IFRAME>\t\r\n                   </td>\r\n                   <td width=\"10%\" valign=\"bottom\" nowrap></td>\r\n               </tr>\r\n               <tr>\r\n                   <td height=\"26\" width=\"75\" nowrap=\"nowrap\" valign=\"top\">???????????????</td>\r\n                   <td colspan=\"2\">\r\n\t\t\t\t\t\t\t<input type=\"checkbox\" name=\"isHidden\" value=\"1\">\r\n                   </td>\r\n               </tr>\r\n               <tr style=\"display:none\">\r\n                 <td>\r\n                   <input type=\"hidden\" name=\"addDivContent\" value=\"\"> <!-- ???  ??????????????????-->\r\n                 </td>\r\n               </tr>\r\n               \r\n               <tr>\r\n                   <td height=\"36\" colspan=\"3\" valign=\"bottom\">\r\n                   <input type=\"button\" class=\"btnButton4font\" onclick=\"javascript:save();\" value=\"??????\" />\r\n                   <input type=\"button\" class=\"btnButton2font\" onclick=\"document.frm.reset();\" value=\"??????\" />\r\n                   <input type=\"button\" class=\"btnButton2font\" onclick=\"javascript:window.close()\" value=\"??????\">\r\n");
            out.write("                   </td>                   \r\n               </tr>\r\n\t\t\t\t\t\t \r\n\t        </table>\t       \r\n       </td>\r\n    </tr>\r\n</table>\r\n</form>\r\n</body>\r\n");
}
            out.write('\r');
            out.write('\n');
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
      out.write("\r\n\r\n<script>\r\n<!--\r\nfunction save(){\r\n\t\r\n\tvar html = document.getElementById(\"newedit\").contentWindow.getHTML();\r\n\tif(document.all){\r\n\t\tdocument.all.opinion.value = newedit.getHTML();\r\n\t}else{\r\n\t\tdocument.all.opinion.value = document.getElementById(\"newedit\").contentWindow.getHTML();\r\n\t}\r\n\t\r\n   if(document.frm.opinion.value==\"\"||document.frm.opinion.value==null){\r\n   alert(\"?????????????????????\");\r\n   }else{\r\n   var obj=opener.document;  \r\n   document.frm.bodyId.value=obj.getElementsByName(\"bodyId\")[0].value;   \r\n   document.frm.nodeId.value=obj.getElementsByName(\"nodeId\")[0].value;      \r\n   document.frm.submit();   \r\n   }\r\n}\r\n\r\n//-->\r\n</script>\r\n\r\n<script>\r\nfunction getEvent()\r\n {\r\n         if(document.all)return window.event;        \r\n         func=getEvent.caller;            \r\n         while(func!=null){    \r\n             var arg0=func.arguments[0];\r\n              if(arg0){\r\n                  if((arg0.constructor==Event || arg0.constructor ==MouseEvent)\r\n                      || (typeof(arg0)==\"object\" && arg0.preventDefault && arg0.stopPropagation)){   \r\n");
      out.write("                      arg0.x = arg0.pageX;\r\n                      arg0.y = arg0.pageY ;\r\n                      arg0.srcElement = arg0.target;\r\n                     return arg0;\r\n                  }\r\n              }\r\n              func=func.caller;\r\n          }\r\n          return null;\r\n\r\n } \r\nvar noteTimer=null;\r\nfunction getNote(){\r\n  var d=document.getElementById(\"noteDiv\");\r\n  var s_top=0;\r\n  if(document.getElementById(\"mainFrame\"))s_top=document.getElementById(\"mainFrame\").scrollTop;\r\n  d.style.left=getEvent().x-220;\r\n  //d.style.top=s_top+event.y-30;\r\n  d.style.top=getEvent().y;\r\n  d.style.display=\"inline\";\r\n\r\n}\r\nfunction addDivContent(){\r\n\tvar adddivcontent=document.all.addDivContent.value;\r\n\tvar comment=document.getElementById(\"noteDiv\").getAttribute(\"value\");\r\n\tdocument.getElementById(\"noteDiv\").innerHTML= \"\"+\"<div class='divOut' onmouseover='lockedNote();this.className=\\\"divOver\\\"' onmouseout='this.className=\\\"divOut\\\"' onclick=\\\"setopinion(this)\\\">\"+adddivcontent+\"<\\/div>\"+document.getElementById(\"noteDiv\").innerHTML;\r\n");
      out.write("\t//alert(document.getElementById(\"noteDiv\").innerHTML);\r\n}\r\nfunction hiddenNote(){\r\n  noteTimer=window.setTimeout(\"closeNote()\",200);\r\n}\r\nfunction closeNote(){\r\n  var d=document.getElementById(\"noteDiv\");\r\n  d.style.display=\"none\";\r\n}\r\nfunction lockedNote(){\r\n  window.clearTimeout(noteTimer);\r\n}\r\nfunction setNote(obj){\r\n\tdocument.getElementById(\"flowNote\").innerText+=obj.innerText;\r\n}\r\nfunction setopinion(obj){\r\n\tif(document.all){\r\n\t\tnewedit.setHTML(obj.innerHTML);\r\n\t}else{\r\n\t \tdocument.getElementById(\"newedit\").contentWindow.insertHTML(obj.innerHTML);\r\n\t}\r\n   document.all.opinion.value=obj.innerHTML;\r\n}\r\n</script>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
