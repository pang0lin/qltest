/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:50:12 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.subsidiary_005fwork;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class subsidiarywork_005fcard_005flist_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes = null;
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhtml;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fwidth_005fsrc_005fheight_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fwidth_005fsrc_005fhspace_005fheight_005falign_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005flink_0026_005fhref;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody;

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
    _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fwidth_005fsrc_005fheight_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fwidth_005fsrc_005fhspace_005fheight_005falign_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005flink_0026_005fhref = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fhtml.release();
    _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fwidth_005fsrc_005fheight_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fwidth_005fsrc_005fhspace_005fheight_005falign_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005flink_0026_005fhref.release();
    _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.release();
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
      			"../../public/jsp/error.jsp", true, 8192, true);
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

      out.write("\r\n<!--\r\n *  注释:辅助办公 最后修改\r\n * <p>Description:贺卡预览</p>\r\n * <p>Copyright: Copyright (c) 2008</p>\r\n * <p>Company: Beijing JS  C0. Ltd.</p>\r\n * \r\n * @version jsoa 1.0\r\n */\r\n -->\r\n");
      //  html:html
      org.apache.struts.taglib.html.HtmlTag _jspx_th_html_005fhtml_005f0 = (org.apache.struts.taglib.html.HtmlTag) _005fjspx_005ftagPool_005fhtml_005fhtml.get(org.apache.struts.taglib.html.HtmlTag.class);
      boolean _jspx_th_html_005fhtml_005f0_reused = false;
      try {
        _jspx_th_html_005fhtml_005f0.setPageContext(_jspx_page_context);
        _jspx_th_html_005fhtml_005f0.setParent(null);
        int _jspx_eval_html_005fhtml_005f0 = _jspx_th_html_005fhtml_005f0.doStartTag();
        if (_jspx_eval_html_005fhtml_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n<HEAD>\r\n<TITLE>图片预览</TITLE>\r\n<META http-equiv=Content-Type content=\"text/html; charset=gb2312\">\r\n<META content=\"MSHTML 6.00.2600.0\" name=GENERATOR>\r\n</HEAD>\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/style/cssmain.css\">\r\n<BODY vLink=#cc6600 aLink=#ffff00 link=#ff6600 bgColor=#ffffff\r\nbackground=\"images/bg.gif\" topMargin=20>\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td>\r\n<TABLE cellSpacing=0  align=center bgColor=#000000\r\nborder=0>\r\n  <TBODY>\r\n  <TR>\r\n    <TD>\r\n      <TABLE cellSpacing=0 cellPadding=0 width=\"100%\" bgColor=#ffffff\r\n      background=\"images/1.gif\" border=0>\r\n        <TBODY>\r\n        <TR>\r\n          <TD>&nbsp;</TD></TR>\r\n        <TR>\r\n          <TD align=middle>\r\n\t\t  ");
String img = request.getParameter("photo");
            out.write("\r\n\t\t  ");

		  String fileServer = session.getAttribute("fileServer").toString();
		  String picSrc=img;
          String src_myLove="0000";
		  if(picSrc!=null && picSrc.toString().length()>6 && picSrc.toString().substring(4,5).equals("_")){
		     src_myLove=picSrc.toString().substring(0,4);
		  }else{
		     src_myLove="0000";
		  }
		  
            out.write("\r\n\t\t  <input type=\"image\" src=\"");
            out.print(fileServer);
            out.write("/upload/");
            out.print(src_myLove );
            out.write("/subsidiarywork/");
            out.print(img);
            out.write("\"/>\r\n         \r\n\t\t\t\r\n          </TD></TR>\r\n        <TR>\r\n          <TD>&nbsp;</TD></TR>\r\n        <TR>\r\n          <TD>\r\n            <TABLE cellSpacing=0 cellPadding=0 width=549 align=center\r\n              border=0><TBODY>\r\n              <TR>\r\n                <TD background=images/dot_bg.gif></TD></TR></TBODY></TABLE></TD></TR>\r\n        <TR>\r\n          <TD noWrap height=11></TD></TR>\r\n        <TR>\r\n                <TD align=middle>　 <font color=\"#006666\">[");
            out.print(request.getAttribute("className")==null?"类别不详":(""+request.getAttribute("className")));
            out.write("] 作者：");
            out.print(request.getAttribute("author")==null?"不详":(""+request.getAttribute("author")));
            out.write(" <br>\r\n                  　</TD>\r\n              </TR>\r\n          <TR align=\"center\">\r\n                <td nowrap><table width=\"70\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n                    <tr>\r\n                      <td nowrap width=\"10\">");
            if (_jspx_meth_html_005fimg_005f0(_jspx_th_html_005fhtml_005f0, _jspx_page_context))
              return;
            out.write("</td>\r\n                      <td nowrap align=\"center\" background=\"/jsoa/images/button_bg.jpg\">");
            if (_jspx_meth_html_005fimg_005f1(_jspx_th_html_005fhtml_005f0, _jspx_page_context))
              return;
            if (_jspx_meth_html_005flink_005f0(_jspx_th_html_005fhtml_005f0, _jspx_page_context))
              return;
            out.write("</td>\r\n                      <td nowrap width=\"10\">");
            if (_jspx_meth_html_005fimg_005f2(_jspx_th_html_005fhtml_005f0, _jspx_page_context))
              return;
            out.write("</td>\r\n                    </tr>\r\n                  </table></td>\r\n              </tr>\r\n            </table></td>\r\n              </TR>\r\n        <TR>\r\n          <TD>&nbsp;</TD></TR></TBODY></TABLE>");
            if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fhtml_005f0, _jspx_page_context))
              return;
            out.write("\r\n\r\n  </TD></TR>\r\n  </TBODY></TABLE>\r\n</td></tr></table>\r\n</BODY>");
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
      out.write("\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n");
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

  private boolean _jspx_meth_html_005fimg_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fhtml_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:img
    org.apache.struts.taglib.html.ImgTag _jspx_th_html_005fimg_005f0 = (org.apache.struts.taglib.html.ImgTag) _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fwidth_005fsrc_005fheight_005fnobody.get(org.apache.struts.taglib.html.ImgTag.class);
    boolean _jspx_th_html_005fimg_005f0_reused = false;
    try {
      _jspx_th_html_005fimg_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005fimg_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
      // /subsidiary_work/subsidiarywork_card_list.jsp(79,44) name = width type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fimg_005f0.setWidth("10");
      // /subsidiary_work/subsidiarywork_card_list.jsp(79,44) name = height type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fimg_005f0.setHeight("22");
      // /subsidiary_work/subsidiarywork_card_list.jsp(79,44) name = src type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fimg_005f0.setSrc("/jsoa/images/button_left.gif");
      int _jspx_eval_html_005fimg_005f0 = _jspx_th_html_005fimg_005f0.doStartTag();
      if (_jspx_th_html_005fimg_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fwidth_005fsrc_005fheight_005fnobody.reuse(_jspx_th_html_005fimg_005f0);
      _jspx_th_html_005fimg_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fimg_005f0, _jsp_getInstanceManager(), _jspx_th_html_005fimg_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005fimg_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fhtml_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:img
    org.apache.struts.taglib.html.ImgTag _jspx_th_html_005fimg_005f1 = (org.apache.struts.taglib.html.ImgTag) _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fwidth_005fsrc_005fhspace_005fheight_005falign_005fnobody.get(org.apache.struts.taglib.html.ImgTag.class);
    boolean _jspx_th_html_005fimg_005f1_reused = false;
    try {
      _jspx_th_html_005fimg_005f1.setPageContext(_jspx_page_context);
      _jspx_th_html_005fimg_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
      // /subsidiary_work/subsidiarywork_card_list.jsp(80,88) name = width type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fimg_005f1.setWidth("4");
      // /subsidiary_work/subsidiarywork_card_list.jsp(80,88) name = height type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fimg_005f1.setHeight("8");
      // /subsidiary_work/subsidiarywork_card_list.jsp(80,88) name = align type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fimg_005f1.setAlign("absmiddle");
      // /subsidiary_work/subsidiarywork_card_list.jsp(80,88) name = hspace type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fimg_005f1.setHspace("3");
      // /subsidiary_work/subsidiarywork_card_list.jsp(80,88) name = src type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fimg_005f1.setSrc("/jsoa/images/button_dot.gif");
      int _jspx_eval_html_005fimg_005f1 = _jspx_th_html_005fimg_005f1.doStartTag();
      if (_jspx_th_html_005fimg_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fwidth_005fsrc_005fhspace_005fheight_005falign_005fnobody.reuse(_jspx_th_html_005fimg_005f1);
      _jspx_th_html_005fimg_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fimg_005f1, _jsp_getInstanceManager(), _jspx_th_html_005fimg_005f1_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005flink_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fhtml_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:link
    org.apache.struts.taglib.html.LinkTag _jspx_th_html_005flink_005f0 = (org.apache.struts.taglib.html.LinkTag) _005fjspx_005ftagPool_005fhtml_005flink_0026_005fhref.get(org.apache.struts.taglib.html.LinkTag.class);
    boolean _jspx_th_html_005flink_005f0_reused = false;
    try {
      _jspx_th_html_005flink_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005flink_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
      // /subsidiary_work/subsidiarywork_card_list.jsp(80,183) name = href type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005flink_005f0.setHref("javascript:window.close()");
      int _jspx_eval_html_005flink_005f0 = _jspx_th_html_005flink_005f0.doStartTag();
      if (_jspx_eval_html_005flink_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspx_eval_html_005flink_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = org.apache.jasper.runtime.JspRuntimeLibrary.startBufferedBody(_jspx_page_context, _jspx_th_html_005flink_005f0);
        }
        do {
          out.write(" 退出 ");
          int evalDoAfterBody = _jspx_th_html_005flink_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_html_005flink_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.popBody();
        }
      }
      if (_jspx_th_html_005flink_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005flink_0026_005fhref.reuse(_jspx_th_html_005flink_005f0);
      _jspx_th_html_005flink_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005flink_005f0, _jsp_getInstanceManager(), _jspx_th_html_005flink_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005fimg_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fhtml_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:img
    org.apache.struts.taglib.html.ImgTag _jspx_th_html_005fimg_005f2 = (org.apache.struts.taglib.html.ImgTag) _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fwidth_005fsrc_005fheight_005fnobody.get(org.apache.struts.taglib.html.ImgTag.class);
    boolean _jspx_th_html_005fimg_005f2_reused = false;
    try {
      _jspx_th_html_005fimg_005f2.setPageContext(_jspx_page_context);
      _jspx_th_html_005fimg_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
      // /subsidiary_work/subsidiarywork_card_list.jsp(81,44) name = width type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fimg_005f2.setWidth("10");
      // /subsidiary_work/subsidiarywork_card_list.jsp(81,44) name = height type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fimg_005f2.setHeight("22");
      // /subsidiary_work/subsidiarywork_card_list.jsp(81,44) name = src type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fimg_005f2.setSrc("/jsoa/images/button_right.gif");
      int _jspx_eval_html_005fimg_005f2 = _jspx_th_html_005fimg_005f2.doStartTag();
      if (_jspx_th_html_005fimg_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005fimg_0026_005fwidth_005fsrc_005fheight_005fnobody.reuse(_jspx_th_html_005fimg_005f2);
      _jspx_th_html_005fimg_005f2_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fimg_005f2, _jsp_getInstanceManager(), _jspx_th_html_005fimg_005f2_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005fhidden_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fhtml_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_005fhidden_005f0 = (org.apache.struts.taglib.html.HiddenTag) _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    boolean _jspx_th_html_005fhidden_005f0_reused = false;
    try {
      _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005fhidden_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
      // /subsidiary_work/subsidiarywork_card_list.jsp(88,46) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fhidden_005f0.setValue("4");
      // /subsidiary_work/subsidiarywork_card_list.jsp(88,46) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005fhidden_005f0.setProperty("pid");
      int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
      if (_jspx_th_html_005fhidden_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
      _jspx_th_html_005fhidden_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fhidden_005f0, _jsp_getInstanceManager(), _jspx_th_html_005fhidden_005f0_reused);
    }
    return false;
  }
}