/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:53:26 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.message;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.Map;
import com.js.system.manager.service.*;

public final class message_005fmenu_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("com.js.system.manager.service");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.util.Map");
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

int menuIndex=0;
ManagerService managerBD = new ManagerService();
String userId = session.getAttribute("userId").toString();

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
            out.write("\r\n<HEAD>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<link href=\"/jsoa/skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script language=\"JavaScript\" src=\"/jsoa/js/MzTreeView10.js\"></script>\r\n</HEAD>\r\n<body  class=\"LeftFrame\" onload=\"OpenCloseSubMenu(0);\">\r\n<table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"outFullLine\">\r\n    <tr>\r\n\t  <td class=\"btnSubModule\" onClick=\"OpenCloseSubMenu(");
            out.print(menuIndex);
            out.write(")\" id=\"menuTitleBox");
            out.print(menuIndex);
            out.write("\">\r\n\t     <div class=\"\" id=\"menuTitle");
            out.print(menuIndex);
            out.write("\">\r\n\t\t    <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n\t\t\t\t<tr>\r\n\t\t\t\t  <td nowrap>短信</td>\r\n\t\t\t\t  <td align=\"right\" width=\"16\">\r\n\t\t\t\t  <span class=\"btnSubMenuOpen\">&nbsp;</span>\r\n\t\t\t\t  </td>\r\n\t\t\t\t</tr>\r\n\t\t\t</table>\r\n         </div></td>\r\n\t</tr>\r\n\t<tr id=\"submenuBox");
            out.print(menuIndex);
            out.write("\" valign=\"top\" style=\"display:'none';\">\r\n      <td height=\"100\" class=\"btnSubModuleBox\">\r\n\t     <script language=\"JavaScript\">\r\n\t          var tree = new MzTreeView(\"tree\");\r\n\t\t\t  var i=101;\r\n\t\t\t  tree.icons[\"level1\"] = \"square_blueS.gif\";\r\n\r\n\t\t\t  tree.setIconPath(\"\"); //可用相对路径\r\n\t\t\t  tree.nodes[\"0_1\"] = \"\";//不显示根节点\r\n\t\t\t  tree.nodes[\"1_\"+(i++)] = \"text:收信箱; icon:level1; url:/jsoa/MessageAction.do?action=receiveView;\";\r\n\t\t\t  ");
 if(managerBD.hasRightTypeName(userId,"短信", "发送")){
            out.write("\r\n\t\t\t  tree.nodes[\"1_\"+(i++)] = \"text:写短信; icon:level1; url:/jsoa/MessageAction.do?action=openAddMsg;target:_blank;\";\r\n\t\t\t  tree.nodes[\"1_\"+(i++)] = \"text:草稿箱; icon:level1; url:/jsoa/MessageAction.do?action=sendView;\";\r\n\t\t\t  tree.nodes[\"1_\"+(i++)] = \"text:发信箱; icon:level1; url:/jsoa/MessageAction.do?action=sendedView;\";\r\n\t\t\t  tree.nodes[\"1_\"+(i++)] = \"text:搜短信; icon:level1; url:/jsoa/MessageAction.do?action=searchBoxView;\";\r\n\t\t\t  ");
}
            out.write("\r\n\t\t\t  tree.nodes[\"1_\"+(i++)] = \"text:废信箱; icon:level1; url:/jsoa/MessageAction.do?action=desertedView;\";\r\n\t\t\t  ");
 if(managerBD.hasRightTypeName(userId,"短信", "设置")){
            out.write("\r\n\t\t\t  tree.nodes[\"1_\"+(i++)] = \"text:短信设置; icon:level1; url:/jsoa/MessageSettingAction.do?action=settingMessage;\";\r\n\t\t\t  ");
}
            out.write("\r\n\t\t\t  ");
 if(managerBD.hasRightTypeName(userId,"短信", "统计")){
            out.write("\r\n\t\t\t  tree.nodes[\"1_\"+(i++)] = \"text:短信统计; icon:level1; url:/jsoa/MessageAction.do?action=messageCount;\";\r\n\t\t\t  <!--tree.nodes[\"1_\"+(i++)] = \"text:剩余短信条数; icon:level1; url:/jsoa/MessageAction.do?action=countBook;\";-->\r\n\t\t\t  ");
}
            out.write("\r\n\r\n\t\t\t  tree.setTarget(\"mainFrame\");\r\n\t\t\t  document.write(tree.toString());\r\n\t\t\t </script>\r\n\t\t</td>\r\n   </tr>\r\n\r\n    <tr>\r\n    <td>&nbsp;\r\n\t</td>\r\n  </tr>\r\n\r\n</table>\r\n</BODY>\r\n");
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
      out.write("\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
