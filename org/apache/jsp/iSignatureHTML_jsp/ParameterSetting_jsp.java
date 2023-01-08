/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:54:36 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.iSignatureHTML_jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class ParameterSetting_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
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

      out.write("\r\n<html>\r\n<head>\r\n<title>金格电子签章系统――参数设置</title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gbk\">\r\n<meta HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\r\n<link REL=\"stylesheet\" href=\"Test.css\" type=\"text/css\">\r\n<script language=\"javascript\">\r\nvar mParameter = window.dialogArguments;\r\n\r\n//输入检测\r\nfunction Check(){\r\n\tif(Signature_Big.value == \"\"){\r\n\t\talert(\"字体大小不能为空！\");\r\n\t\treturn false;\r\n\t}\r\n\tif(Signature_Color.value == \"\"){\r\n\t\talert(\"字体颜色不能为空！\");\r\n\t\treturn false;\r\n\t}\r\n\tif(Signature_Font.value == \"\"){\r\n\t\talert(\"签章字体不能为空！\");\r\n\t\treturn false;\r\n\t}\r\n\treturn true;\r\n}\r\n\r\n//参数设置\r\nfunction ParameterSetting(){\r\n\tif(!Check()){\r\n\t\treturn;\r\n\t}\r\n\talert(\"参数设置成功！\");\r\n\tvar tmp = Language.value + \";\" + (AutoSign.checked?\"1\":\"0\") + \";\" + CancelOrder.value + \";\" + DefaultPassword.value + \";\" + \r\n\t\t(Signature_ShowTime.checked?\"1\":\"0\") + \";\" + Signature_InputAddvice.value + \";\" + \r\n\t\tSignature_Position.value + \";\" + Signature_Font.value + \";\" +\r\n\t\tSignature_Big.value + \";\" + Signature_Color.value + \";\" +  \r\n\t\t(Signature_Bold.checked?\"1\":\"0\")+ \";\"+ ProtectMethod.value ;\r\n");
      out.write("\twindow.returnValue = tmp;\r\n\twindow.close();\r\n\twindow.opener = null;\r\n}\r\n\r\n//关闭\r\nfunction Close()\r\n{\r\n    window.close();\r\n}\r\n\r\n//默认状态\r\nfunction DefaultSetting(){\r\n\tLanguage.value = \"1\";\r\n\tAutoSign.checked = true;\r\n\tCancelOrder.value = \"0\";\r\n\tDefaultPassword.value = \"\"; \r\n\tSignature_ShowTime.checked = false;\r\n\tSignature_InputAddvice.value = \"\"; \r\n\tSignature_Position.value = \"0\";\r\n\tSignature_Font.value = \"宋体\";\r\n\tSignature_Big.value = \"11\"; \r\n\tSignature_Color.value = \"$000000\";\r\n\tProtectMethod.value = mParameter[1];\r\n\tSignature_Bold.checked = false;    \r\n\tProtectMethod.value = \"1\";\r\n}\r\n\r\n//当前状态赋值\r\nfunction CurrentSetting(){\r\n\tDefaultSetting();\r\n\tLanguage.value = mParameter[0]==\"GBC_\"?\"1\":(mParameter[0]==\"ENG_\"?\"3\":\"2\");\r\n\tAutoSign.Checked = mParameter[1]==\"1\"?true:false;\r\n\tCancelOrder.value = mParameter[2];\r\n\tDefaultPassword.value = mParameter[3];\r\n\r\n\tif(mParameter[4] != undefined){\r\n\t\tSignature_ShowTime.checked = mParameter[4][4]==\"1\"?true:false;\r\n\t\tSignature_InputAddvice.value = mParameter[4][5];\r\n\t\tSignature_Position.value = mParameter[4][6];\r\n");
      out.write("\t\tSignature_Font.value = mParameter[4][7];\r\n\t\tSignature_Big.value = mParameter[4][8];\r\n\t\tSignature_Color.value = mParameter[4][9];\r\n\t\tSignature_Bold.checked = mParameter[4][10]==\"1\"?true:false;\r\n\t\tProtectMethod.value = mParameter[4][11];\r\n\t}\r\n}\r\n</script>\r\n</head>\r\n<body onload=\"CurrentSetting();\">\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td>\r\n  <table cellSpacing=\"0\" cellPadding=\"0\">\r\n    <tr>\r\n\t  <td height=\"40\" colspan=\"3\" align=\"center\"><strong>签章相关参数设置（可二次开发调用）</strong></td>\r\n\t</tr>\r\n\t<tr>\r\n\t  <td width=\"30\" height=\"25\" align=\"right\" valign=\"middle\">&nbsp;</td>\r\n\t  <td width=\"120\" align=\"left\" valign=\"middle\">多语言显示</td>\r\n\t  <td width=\"120\" height=\"25\" valign=\"middle\" align=\"left\">\r\n\t      <SELECT name=\"Language\"  id=\"Language\" style=\"width:80px\">\r\n              <OPTION value=\"1\">简体中文</OPTION>\r\n\t\t\t  <OPTION value=\"2\" >繁体中文</OPTION>\r\n\t\t\t  <OPTION value=\"3\" >English</OPTION>\r\n          </SELECT>\r\n\t  </td>\r\n\t</tr>\r\n\r\n\t<tr>\r\n\t  <td height=\"25\" align=\"right\" valign=\"middle\">&nbsp;</td>\r\n");
      out.write("\t  <td align=\"left\" valign=\"middle\" height=\"25\">自动数字签名</td>\r\n\t  <td valign=\"middle\" height=\"25\" align=\"left\">\r\n\t      <input type=\"checkbox\" name=\"AutoSign\" id=\"AutoSign\" checked></input>\r\n\t  </td>\r\n\t</tr>\r\n\r\n\t<tr>\r\n\t  <td width=\"30\" height=\"25\" align=\"right\" valign=\"middle\">&nbsp;</td>\r\n\t  <td width=\"120\" align=\"left\" valign=\"middle\">撤消顺序原则</td>\r\n\t  <td width=\"120\" height=\"25\" valign=\"middle\" align=\"left\">\r\n\t      <SELECT name=\"CancelOrder\"  id=\"CancelOrder\" style=\"width:120px\">\r\n              <OPTION value=\"0\">无顺序</OPTION>\r\n\t\t\t  <OPTION value=\"1\">先签章后撤消顺序</OPTION>\r\n\t\t\t  <OPTION value=\"2\">先签章先撤消顺序</OPTION>\r\n          </SELECT>\r\n\t  </td>\r\n\t</tr>\r\n\r\n\t<tr>\r\n\t  <td width=\"30\" height=\"25\" align=\"right\" valign=\"middle\">&nbsp;</td>\r\n\t  <td width=\"120\" align=\"left\" valign=\"middle\">保护表单数据</td>\r\n\t  <td width=\"120\" height=\"25\" valign=\"middle\" align=\"left\">\r\n\t      <SELECT name=\"ProtectMethod\"  id=\"ProtectMethod\" style=\"width:160px\">\r\n              <OPTION value=\"0\">不保护</OPTION>\r\n\t\t\t  <OPTION value=\"1\">保护表单数据，可操作</OPTION>\r\n");
      out.write("\t\t\t  <OPTION value=\"2\">保存表单数据，并不能操作</OPTION>\r\n          </SELECT>\r\n\t  </td>\r\n\t</tr>\r\n\r\n\t<tr>\r\n\t  <td width=\"30\" height=\"25\" align=\"right\" valign=\"middle\">&nbsp;</td>\r\n\t  <td width=\"120\" align=\"left\" valign=\"middle\">签章密码默认</td>\r\n\t  <td width=\"120\" height=\"25\" valign=\"middle\" align=\"left\">\r\n\t\t  <input type=\"password\" name=\"DefaultPassword\" id=\"DefaultPassword\" style=\"width:120px\"></input>\r\n\t  </td>\r\n\t</tr>\r\n</table>\r\n\r\n<fieldset align=\"center\" style=\"padding=8px; width:90%; border : 1px solid #E9E8E8;line-height:2.0;text-align:left;COLOR:#EE3C65;FONT-SIZE: 12px;font-family: Verdana\">\r\n<legend align=\"left\">盖章设置</legend>\r\n<table align=\"left\">\r\n\t<tr>\r\n\t  <td height=\"25\" align=\"left\" valign=\"middle\">&nbsp;</td>\r\n\t  <td align=\"left\" valign=\"middle\" height=\"25\">显示日期时间</td>\r\n\t  <td valign=\"middle\" height=\"25\" align=\"left\">\r\n\t      <input type=\"checkbox\" name=\"Signature_ShowTime\" id=\"Signature_ShowTime\" checked></input>\r\n\t  </td>\r\n\t</tr>\r\n\r\n\t<tr>\r\n\t  <td height=\"25\" align=\"left\" valign=\"middle\">&nbsp;</td>\r\n\t  <td width=\"120\" align=\"left\" valign=\"middle\">签章意见</td>\r\n");
      out.write("\t  <td width=\"120\" height=\"25\" valign=\"middle\" align=\"left\">\r\n\t\t  <input name=\"Signature_InputAddvice\" id=\"Signature_InputAddvice\" style=\"width:120px\"></input>\r\n\t  </td>\r\n\t</tr>\r\n\r\n\t<tr>\r\n\t  <td height=\"25\" align=\"left\" valign=\"middle\">&nbsp;</td>\r\n\t  <td width=\"120\" align=\"left\" valign=\"middle\">意见位置</td>\r\n\t  <td width=\"120\" height=\"25\" valign=\"middle\" align=\"left\">\r\n\t      <SELECT name=\"Signature_Position\"  id=\"Signature_Position\" style=\"width:120px\">\r\n              <OPTION value=\"0\">居中</OPTION>\r\n\t\t\t  <OPTION value=\"1\">下方</OPTION>\r\n          </SELECT>\r\n\t  </td>\r\n\t</tr>\r\n\r\n\t<tr>\r\n\t  <td height=\"25\" align=\"left\" valign=\"middle\">&nbsp;</td>\r\n\t  <td width=\"120\" align=\"left\" valign=\"middle\">意见字体</td>\r\n\t  <td width=\"120\" height=\"25\" valign=\"middle\" align=\"left\">\r\n\t  \t  <input name=\"Signature_Font\" id=\"Signature_Font\" style=\"width:120px\"></input>\r\n\t  </td>\r\n\t</tr>\r\n\r\n\t<tr>\r\n\t  <td height=\"25\" align=\"left\" valign=\"middle\">&nbsp;</td>\r\n\t  <td width=\"120\" align=\"left\" valign=\"middle\">意见字体大小</td>\r\n\t  <td width=\"120\" height=\"25\" valign=\"middle\" align=\"left\">\r\n");
      out.write("\t  \t  <input name=\"Signature_Big\" id=\"Signature_Big\" style=\"width:120px\"></input>\r\n\t  </td>\r\n\t</tr>\r\n\r\n\t<tr>\r\n\t  <td height=\"25\" align=\"left\" valign=\"middle\">&nbsp;</td>\r\n\t  <td width=\"120\" align=\"left\" valign=\"middle\">意见字体颜色</td>\r\n\t  <td width=\"120\" height=\"25\" valign=\"middle\" align=\"left\">\r\n\t  \t  <input name=\"Signature_Color\" id=\"Signature_Color\" style=\"width:120px\"></input>\r\n\t  </td>\r\n\t</tr>\r\n\r\n\t<tr>\r\n\t  <td height=\"25\" align=\"left\" valign=\"middle\">&nbsp;</td>\r\n\t  <td align=\"left\" valign=\"middle\" height=\"25\">是否使用粗体</td>\r\n\t  <td valign=\"middle\" height=\"25\" align=\"left\">\r\n\t      <input type=\"checkbox\" name=\"Signature_Bold\" id=\"Signature_Bold\"></input>\r\n\t  </td>\r\n\t</tr>\r\n</table>\r\n</fieldset>\r\n\r\n<table align=\"center\">\r\n\t<tr>\r\n\t  <td height=\"40\" colspan=\"3\" align=\"center\" valign=\"bottom\"><input type=\"button\" class=\"btnButton\" onClick=\"ParameterSetting();\" value=\"确 定\">\r\n\t  &nbsp;&nbsp;<input type=\"button\" class=\"btnButton\" onClick=\"DefaultSetting();\" value=\"默认设置\">\r\n\t  &nbsp;&nbsp;<input type=\"button\" class=\"btnButton\" onClick=\"Close();\" value=\"关闭\"></td>\r\n");
      out.write("    </tr>\r\n\t<tr>\r\n\t  <td height=\"5\" colspan=\"3\" align=\"center\"></td>\r\n    </tr>\r\n</table>\r\n</td>\r\n</tr>\r\n</table>\r\n</body>\r\n</html>");
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