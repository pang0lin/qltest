/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:49:57 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.subsidiary_005fwork;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.Calendar;

public final class subsidiarywork_005fquestheme_005fView_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.util.Calendar");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fsize_005freadonly_005fproperty_005fmaxlength_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005freadonly_005fproperty_005fmaxlength_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid;

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
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fsize_005freadonly_005fproperty_005fmaxlength_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005freadonly_005fproperty_005fmaxlength_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.release();
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fsize_005freadonly_005fproperty_005fmaxlength_005fnobody.release();
    _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005freadonly_005fproperty_005fmaxlength_005fnobody.release();
    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid.release();
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

      out.write("\r\n\r\n\r\n\r\n<html>\r\n<head>\r\n<title>查阅问卷设计</title>\r\n<link href=\"/jsoa/style/cssmain.css\" rel=\"stylesheet\" type=\"text/css\">\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject2.css\">\r\n<script language=\"javascript\" src=\"/jsoa/js/checkForm.js\"></script>\r\n<script language=\"javascript\" src=\"/jsoa/js/openEndow.js\"></script>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DateObject.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DatePicker.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/editlib.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/tree.js\"></SCRIPT>\r\n<script src=\"js/js.js\" language=\"javascript\"></script>\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n</head>\r\n");


        String type = "";
        if(request.getAttribute("type") !=null){
            type = request.getAttribute("type").toString();
            }
        String grade = "";
        if(request.getAttribute("grade") !=null){
        grade = request.getAttribute("grade").toString();
        }


      out.write("\r\n\r\n<body leftmargin=\"0\" topmargin=\"0\" onload=\"load();resizeWin(600,300);\"  class=\"MainFrameBox Pupwin\">\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td>\r\n\r\n<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"docBoxNoPanel\">\r\n  <tr>\r\n    <td  valign=\"top\">\r\n\t\t<div id=\"docinfo0\" style=\"display:;\">\r\n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"1\">\r\n                            ");
      //  html:form
      org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag) _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(org.apache.struts.taglib.html.FormTag.class);
      boolean _jspx_th_html_005fform_005f0_reused = false;
      try {
        _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
        _jspx_th_html_005fform_005f0.setParent(null);
        // /subsidiary_work/subsidiarywork_questheme_View.jsp(43,28) name = action type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_html_005fform_005f0.setAction("/QuestionnaireAction.do?action=addQuestheme");
        // /subsidiary_work/subsidiarywork_questheme_View.jsp(43,28) name = method type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_html_005fform_005f0.setMethod("post");
        int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
        if (_jspx_eval_html_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n                        <tr>\r\n                          <td width=\"80\" nowrap=\"nowrap\"><p>标题&nbsp;<label class=\"mustFillcolor\">*</label>：</p></td>\r\n                          <td> ");
            if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
              return;
            out.write("</td>\r\n                        </tr>\r\n                        <tr id=\"scoreTr\">\r\n                          <td>分值：</td>\r\n                          <td>");
            if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
              return;
            out.write("</td>\r\n                        </tr>\r\n                        <tr>\r\n                          <td>类型：</td>\r\n                          <td>\r\n                              <select name=\"type\" class=\"inputtext\" disabled=\"disabled\" style=\"width:80px\">\r\n                              \t<option value=\"0\" ");
if("0".equals(type)){
            out.write("selected");
}
            out.write(">单选</option>\r\n                                <option value=\"1\" ");
if("1".equals(type)){
            out.write("selected");
}
            out.write(">多选</option>\r\n                                <option value=\"2\" ");
if("2".equals(type)){
            out.write("selected");
}
            out.write(">问答</option>\r\n                                </select>\r\n                          </td>\r\n                        </tr>\r\n                        <tr id=\"choose\">\r\n                          <td>选项：</td>\r\n                              <td id=\"tdSource\">\r\n                                <table width=\"100%\" border=\"1\" cellpadding=\"1\" cellspacing=\"0\" bordercolor=\"#000000\" id=\"tableSource\" bordercolordark=\"#E1E1E1\">\r\n                                    <tr align=\"center\">\r\n\t\t\t\t<td width=\"30%\">&nbsp;</td>\r\n\t\t\t\t<td width=\"30%\">&nbsp;</td>\r\n\t\t\t  \t</tr>\r\n\r\n                                 ");
            //  logic:iterate
            org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_005fiterate_005f0 = (org.apache.struts.taglib.logic.IterateTag) _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid.get(org.apache.struts.taglib.logic.IterateTag.class);
            boolean _jspx_th_logic_005fiterate_005f0_reused = false;
            try {
              _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
              _jspx_th_logic_005fiterate_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
              // /subsidiary_work/subsidiarywork_questheme_View.jsp(71,33) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_logic_005fiterate_005f0.setId("List");
              // /subsidiary_work/subsidiarywork_questheme_View.jsp(71,33) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_logic_005fiterate_005f0.setName("themeOptionList");
              int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
              if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                java.lang.Object List = null;
                if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = org.apache.jasper.runtime.JspRuntimeLibrary.startBufferedBody(_jspx_page_context, _jspx_th_logic_005fiterate_005f0);
                }
                List = (java.lang.Object) _jspx_page_context.findAttribute("List");
                do {
                  out.write("\r\n                                     ");

                                    	Object[] obj = (Object[]) List;
                                     
                  out.write("\r\n                                    <tr>\r\n                                    <td width=\"30%\" height=\"31\">&nbsp;答案：&nbsp;<input type=\"text\" name=\"solutionTitle\" class=\"inputtext\" value=\"");
                  out.print(obj[1]);
                  out.write("\" readonly=\"readonly\"></td>\r\n                                    <td width=\"30%\" height=\"31\">&nbsp;<span id=\"optionScoreSpan\" style=\"display:none\">分值：&nbsp;<input type=\"text\" name=\"optionScore\" class=\"inputtext\" size=\"10\" ");
if(obj[3] !=null && !"".equals(obj[3].toString())){
                  out.write(" value=\"");
                  out.print(obj[3]);
                  out.write('"');
}else{
                  out.write("&nbsp;");
}
                  out.write(" readonly=\"readonly\"></span><span id=\"pitchonSpan\" style=\"display:none\">&nbsp;<select name=\"pitchon\" disabled=\"disabled\"><option value=\"0\" ");
if(obj[2] !=null && !"".equals(obj[2].toString()) &&"0".equals(obj[2].toString())){
                  out.write("selected");
}
                  out.write(">不选中</option><option value=\"1\" ");
if(obj[2] !=null && !"".equals(obj[2].toString()) &&"1".equals(obj[2].toString())){
                  out.write("selected");
}
                  out.write(">选中</option></select></span></td>\r\n                                 </tr>\r\n                                 ");
                  int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
                  List = (java.lang.Object) _jspx_page_context.findAttribute("List");
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
                if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.popBody();
                }
              }
              if (_jspx_th_logic_005fiterate_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                return;
              }
              _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
              _jspx_th_logic_005fiterate_005f0_reused = true;
            } finally {
              org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_logic_005fiterate_005f0, _jsp_getInstanceManager(), _jspx_th_logic_005fiterate_005f0_reused);
            }
            out.write("\r\n                               </table>\r\n\t\t\t\t</td>\r\n                        </tr>\r\n                        ");
            int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
            if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
              break;
          } while (true);
        }
        if (_jspx_th_html_005fform_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return;
        }
        _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
        _jspx_th_html_005fform_005f0_reused = true;
      } finally {
        org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005fform_005f0, _jsp_getInstanceManager(), _jspx_th_html_005fform_005f0_reused);
      }
      out.write("\r\n                        </table>\r\n                </div>\r\n    </td>\r\n  </tr>\r\n</table>\r\n\r\n</td></tr></table>\r\n\r\n</body>\r\n\r\n</html>\r\n<script language=\"javascript\">\r\n<!--\r\nfunction load(){\r\n type =");
      out.print(type);
      out.write(";\r\n grade =");
      out.print(grade);
      out.write(";\r\n if(grade ==\"0\"){\r\n \tdocument.all.scoreTr.style.display = \"none\";\r\n }\r\n if(type ==\"0\"){\r\n        if(grade ==\"1\"){\r\n            for(var i=0;i<document.all.optionScoreSpan.length;i++){\r\n                document.all.optionScoreSpan[i].style.display=\"\";\r\n            }\r\n            }\r\n        }\r\nif(type ==\"1\"){\r\n        for(var i=0;i<document.all.pitchonSpan.length;i++){\r\n                document.all.pitchonSpan[i].style.display=\"\";\r\n            }\r\n\r\n        }\r\n if(type ==\"2\"){\r\n        document.all.choose.style.display=\"none\";\r\n        }\r\n\r\n\r\n}\r\nfunction onChange(value){\r\n    var grade = document.all.gradeHidden.value;\r\n    var selectedValue = value.options[value.selectedIndex].value;\r\n    var grade = document.all.gradeHidden.value;\r\n    if(selectedValue ==\"0\"){\r\n       var state = document.all.choose.style.display;\r\n\tif(state = \"none\"){\r\n            document.all.choose.style.display=\"\";\r\n            if(grade ==\"1\"){\r\n                document.all.optionScore.disabled = \"\";\r\n                document.all.pitchon.disabled = \"disabled\";\r\n");
      out.write("                }else if(grade ==\"0\"){\r\n                    document.all.optionScore.disabled = \"disabled\";\r\n                    document.all.pitchon.disabled = \"disabled\";\r\n                    }\r\n            tdSourceHTML = document.all.tdSource.innerHTML;\r\n            }else{\r\n                if(grade ==\"1\"){\r\n                document.all.optionScore.disabled = \"\";\r\n                document.all.pitchon.disabled = \"disabled\";\r\n                }else if(grade ==\"0\"){\r\n                    document.all.optionScore.disabled = \"disabled\";\r\n                    document.all.pitchon.disabled = \"disabled\";\r\n                    }\r\n            tdSourceHTML = document.all.tdSource.innerHTML;\r\n                }\r\n        }\r\n    if(selectedValue ==\"1\"){\r\n        var state = document.all.choose.style.display;\r\n        if(state = \"none\"){\r\n            document.all.choose.style.display=\"\";\r\n            if(grade ==\"1\"){\r\n                document.all.optionScore.disabled = \"disabled\";\r\n                document.all.pitchon.disabled = \"\";\r\n");
      out.write("                }else if(grade ==\"0\"){\r\n                     document.all.optionScore.disabled = \"disabled\";\r\n                     document.all.pitchon.disabled = \"\";\r\n                }\r\n\r\n            tdSourceHTML = document.all.tdSource.innerHTML;\r\n            }else{\r\n                if(grade ==\"1\"){\r\n                document.all.optionScore.disabled = \"disabled\";\r\n                document.all.pitchon.disabled = \"\";\r\n                }else if(grade ==\"0\"){\r\n                     document.all.optionScore.disabled = \"disabled\";\r\n                     document.all.pitchon.disabled = \"\";\r\n                }\r\n                tdSourceHTML = document.all.tdSource.innerHTML;\r\n                }\r\n\r\n        }\r\n    if(selectedValue ==\"2\"){\r\n        document.all.choose.style.display=\"none\";\r\n        }\r\n\r\n\r\n}\r\n\r\nfunction saveAndExit(){\r\n    var title = document.QuestionnaireActionForm.title.value;\r\n    document.QuestionnaireActionForm.saveType.value = \"saveAndExit\";\r\n    if (title ==\"\"){\r\n        alert(\"标题不得为空，必须填写。\");\r\n        document.QuestionnaireActionForm.title.focus();\r\n");
      out.write("        return;\r\n        }\r\n    if (title !=\"\"){\r\n            if(title.substring(0,1) ==\" \"){\r\n                alert(\"标题不得为空格开头，请去空格。\");\r\n                document.QuestionnaireActionForm.title.focus();\r\n                return;\r\n                }\r\n           }\r\n    document.QuestionnaireActionForm.submit();\r\n\r\n}\r\n\r\nfunction saveAndContinue(){\r\n    var title = document.QuestionnaireActionForm.title.value;\r\n    var actorName = document.QuestionnaireActionForm.actorName.value;\r\n    var examineName = document.QuestionnaireActionForm.examineName.value;\r\n    document.QuestionnaireActionForm.saveType.value = \"saveAndContinue\";\r\n    if (title ==\"\"){\r\n        alert(\"标题不得为空，必须填写。\");\r\n        document.QuestionnaireActionForm.title.focus();\r\n        return;\r\n        }\r\n    if (title !=\"\"){\r\n            if(title.substring(0,1) ==\" \"){\r\n                alert(\"标题不得为空格开头，请去空格。\");\r\n                document.QuestionnaireActionForm.title.focus();\r\n                return;\r\n                }\r\n           }\r\n    if (actorName ==\"\"){\r\n");
      out.write("        alert(\"可投票人不得为空，必须填写。\");\r\n        document.QuestionnaireActionForm.actorName.focus();\r\n        return;\r\n        }\r\n    if (examineName ==\"\"){\r\n        alert(\"可查看人不得为空，必须填写。\");\r\n        document.QuestionnaireActionForm.examineName.focus();\r\n        return;\r\n        }\r\n    document.QuestionnaireActionForm.submit();\r\n    }\r\n\r\nfunction resetMe(){\r\n    document.QuestionnaireActionForm.reset();\r\n\r\n}\r\n\r\n//-->\r\n</script>\r\n\r\n<script src=\"/jsoa/js/util.js\"></script>");
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

  private boolean _jspx_meth_html_005ftext_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f0 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fsize_005freadonly_005fproperty_005fmaxlength_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
    boolean _jspx_th_html_005ftext_005f0_reused = false;
    try {
      _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005ftext_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /subsidiary_work/subsidiarywork_questheme_View.jsp(46,31) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setProperty("title");
      // /subsidiary_work/subsidiarywork_questheme_View.jsp(46,31) name = maxlength type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setMaxlength("25");
      // /subsidiary_work/subsidiarywork_questheme_View.jsp(46,31) name = size type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setSize("30");
      // /subsidiary_work/subsidiarywork_questheme_View.jsp(46,31) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setStyleClass("inputtext");
      // /subsidiary_work/subsidiarywork_questheme_View.jsp(46,31) name = readonly type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setReadonly(true);
      // /subsidiary_work/subsidiarywork_questheme_View.jsp(46,31) name = style type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f0.setStyle("width:100%");
      int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
      if (_jspx_th_html_005ftext_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fsize_005freadonly_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
      _jspx_th_html_005ftext_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005ftext_005f0, _jsp_getInstanceManager(), _jspx_th_html_005ftext_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005ftext_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fform_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f1 = (org.apache.struts.taglib.html.TextTag) _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005freadonly_005fproperty_005fmaxlength_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
    boolean _jspx_th_html_005ftext_005f1_reused = false;
    try {
      _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
      _jspx_th_html_005ftext_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fform_005f0);
      // /subsidiary_work/subsidiarywork_questheme_View.jsp(50,30) name = styleClass type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f1.setStyleClass("inputtext");
      // /subsidiary_work/subsidiarywork_questheme_View.jsp(50,30) name = maxlength type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f1.setMaxlength("9");
      // /subsidiary_work/subsidiarywork_questheme_View.jsp(50,30) name = property type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f1.setProperty("score");
      // /subsidiary_work/subsidiarywork_questheme_View.jsp(50,30) name = size type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f1.setSize("10");
      // /subsidiary_work/subsidiarywork_questheme_View.jsp(50,30) name = readonly type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005ftext_005f1.setReadonly(true);
      int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
      if (_jspx_th_html_005ftext_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005freadonly_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
      _jspx_th_html_005ftext_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005ftext_005f1, _jsp_getInstanceManager(), _jspx_th_html_005ftext_005f1_reused);
    }
    return false;
  }
}
