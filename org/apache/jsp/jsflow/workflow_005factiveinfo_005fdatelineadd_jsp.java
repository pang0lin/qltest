/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:07:29 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.oa.jsflow.vo.SimpleFieldVO;

public final class workflow_005factiveinfo_005fdatelineadd_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.oa.jsflow.vo.SimpleFieldVO");
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
      response.setContentType("text/html; charset=GBK");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			"/public/jsp/error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n");


java.util.List list = new com.js.oa.jsflow.service.WorkFlowBD().getSimpleField( request.getParameter("moduleId")+ "", request.getParameter("tableId"));


      out.write("\r\n<html>\r\n<head>\r\n<title>?????????????????????</title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script language=\"javascript\" src=\"../js/checkForm.js\"></script>\r\n<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n</head>\r\n\r\n<body  onload=\"resizeWin(600,300);\" class=\"MainFrameBox Pupwin\">\r\n<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"docBoxNoPanel\">\r\n<form name=\"form1\" method=\"post\" action=\"\">\r\n\t<tr>\r\n\t\t<td width=\"15%\"> ?????????</td><td width=\"35%\">\r\n\t\t\t<select name=\"condition\"  style=\"width:80%;\">\r\n\t\t\t");

                                            SimpleFieldVO fieldVO = null;
                                            for(int i = 0; i < list.size(); i ++){
                                                fieldVO = (SimpleFieldVO) list.get(i);
                                            
      out.write("\r\n                                            \t<option value=\"");
      out.print(fieldVO.getId());
      out.write('"');
      out.write('>');
      out.print(fieldVO.getDisplayName());
      out.write("</option>\r\n                                            ");

                                            }
      out.write("\r\n\t\t\t</select>                                </td>\r\n\t\t<td width=\"15%\"> ????????????</td><td width=\"35%\">\r\n\t\t\t<select name=\"operate\"  style=\"width:80%;\">\r\n                                  <option value=\"<\">??????(&lt;)</option>\r\n                                  <option value=\"<=\">?????????(&lt;=)</option>\r\n                                  <option value=\"<>\">?????????(???)</option>\r\n                                  <option value=\"=\">??????(=)</option>\r\n                                  <option value=\">\">??????(&gt;)</option>\r\n                                  <option value=\">=\">?????????(&gt;=)</option>\r\n                                </select> </td></tr>\r\n\t<tr>\r\n                              <td> ????????????</td><td>\r\n                                <input name=\"compareValue\"  type=\"text\"  style=\"width:80%;\"  class=\"inputText\"></td>\r\n\t\t\t\t\t\t\t\t<td>???????????????</td><td>\r\n                                  <select name=\"timeLimit\" style=\"width:80%;\">\r\n                                      <option value=\"43200\">0.5</option>\r\n                                      <option value=\"86400\">1</option>\r\n");
      out.write("                                      <option value=\"172800\">2</option>\r\n                                      <option value=\"259200\">3</option>\r\n                                      <option value=\"345600\">4</option>\r\n                                      <option value=\"432000\">5</option>\r\n                                      <option value=\"518400\">6</option>\r\n                                      <option value=\"604800\">7</option>\r\n                                      <option value=\"691200\">8</option>\r\n                                      <option value=\"777600\">9</option>\r\n                                      <option value=\"864000\">10</option>\r\n                                      <option value=\"950400\">11</option>\r\n                                      <option value=\"1036800\">12</option>\r\n                                      <option value=\"1123200\">13</option>\r\n                                      <option value=\"1209600\">14</option>\r\n                                      <option value=\"1296000\">15</option>\r\n");
      out.write("                                      <option value=\"1382400\">16</option>\r\n                                      <option value=\"1468800\">17</option>\r\n                                      <option value=\"1555200\">18</option>\r\n                                      <option value=\"1641600\">19</option>\r\n                                      <option value=\"1728000\">20</option>\r\n                                  </select>\r\n                                ???</td>\r\n                            </tr>\r\n                            <tr>\r\n                              <td><input type=\"checkbox\" name=\"hasMotion\" value=\"checkbox\" checked=\"checked\">\r\n                                ???????????????</td>\r\n                              <td >\r\n                                <select name=\"motionTime\" style=\"width:80%;\">\r\n                                  <option value=\"900\">15??????</option>\r\n                                  <option value=\"1800\">30??????</option>\r\n                                  <option value=\"3600\">1??????</option>\r\n                                  <option value=\"7200\">2??????</option>\r\n");
      out.write("                                  <option value=\"10800\">3??????</option>\r\n                                  <option value=\"14400\">4??????</option>\r\n                                  <option value=\"18000\">5??????</option>\r\n                                  <option value=\"21600\">6??????</option>\r\n                                  <option value=\"86400\">1???</option>\r\n                                  <option value=\"172800\">2???</option>\r\n                                  <option value=\"259200\">3???</option>\r\n                                  <option value=\"345600\">4???</option>\r\n                                  <option value=\"432000\">5???</option>\r\n                                </select></td>\r\n                            </tr>\r\n                            <tr>\r\n                              <td colspan=\"4\">\r\n                                  <!--\r\n                                <p>??????????????????????????????????????????????????????&lt;???&lt;=???&gt;???&gt;=????????????</p>\r\n                                <p>??????????????????????????????????????????????????????????????????</p>\r\n                                -->\r\n                              </td>\r\n");
      out.write("                            </tr>\r\n\t\t<tr>\r\n\t      <td colspan=4>\r\n\t\t    <button class=\"btnButton4font\" onclick=\"javascript:save('close');\">????????????</button><button class=\"btnButton4font\" onclick=\"javascript:save('continue');form1.reset();\">????????????</button><button class=\"btnButton2font\" onclick=\"javascript:form1.reset();\">??????</button><button class=\"btnButton2font\" onclick=\"javascript:window.close()\">??????</button>\r\n\t\t  </td>\r\n        </tr>  \r\n </form>\r\n</table>\r\n</body>\r\n</html>\r\n<script language=\"javascript\">\r\n<!--\r\nfunction save(type){\r\n    var pressLimit = true;\r\n    if(document.all.hasMotion.checked){\r\n        if(Math.abs(document.all.timeLimit.value) < 432000){\r\n            //????????????\r\n            var timeLimit = document.all.timeLimit.value;\r\n            //????????????\r\n            var motionTime = document.all.motionTime.value;\r\n            if(Math.abs(motionTime) > Math.abs(timeLimit)){\r\n                pressLimit = false;\r\n            }\r\n        }\r\n    }\r\n\r\n    if(textIsEmpty(document.all.compareValue)){\r\n    \talert(\"?????????????????????\");\r\n");
      out.write("    }else{\r\n        if(!pressLimit){\r\n            alert(\"???????????????????????????????????????\");\r\n        }else{\r\n            insertTable();\r\n            if(type == \"close\"){\r\n                window.close();\r\n            }\r\n        }\r\n    }\r\n}\r\n\r\nfunction insertTable(){\r\n\r\n    var conditionValue = \"\";\r\n    var conditionText = \"\";\r\n    var i = document.all.condition.options.length;\r\n    for(var j = 0; j < i; j ++){\r\n    \tif(document.all.condition.options[j].selected){\r\n           conditionValue = document.all.condition.options[j].value;\r\n           conditionText = document.all.condition.options[j].text;\r\n           break;\r\n        }\r\n    }\r\n    //alert(conditionValue);\r\n    //alert(conditionText);\r\n\r\n    var operateValue = \"\";\r\n    var operateText = \"\";\r\n    i = document.all.operate.options.length;\r\n    for(var j = 0; j < i; j ++){\r\n        if(document.all.operate.options[j].selected){\r\n            operateValue = document.all.operate.options[j].value;\r\n            operateText = document.all.operate.options[j].text;\r\n            break;\r\n");
      out.write("        }\r\n    }\r\n\r\n    var compareValue = document.all.compareValue.value;\r\n\r\n    var timeLimitValue = \"\";\r\n    var timeLimitText = \"\";\r\n    i = document.all.timeLimit.options.length;\r\n    for(var j = 0; j < i; j ++){\r\n        if(document.all.timeLimit.options[j].selected){\r\n            timeLimitValue = document.all.timeLimit.options[j].value;\r\n            timeLimitText = document.all.timeLimit.options[j].text;\r\n            break;\r\n        }\r\n    }\r\n\r\n    var motionTimeValue = \"\";\r\n    var motionTimeText = \"\";\r\n    i = document.all.motionTime.options.length;\r\n    for(var j = 0; j < i; j ++){\r\n        if(document.all.motionTime.options[j].selected){\r\n            motionTimeValue = document.all.motionTime.options[j].value;\r\n            motionTimeText = document.all.motionTime.options[j].text;\r\n            break;\r\n        }\r\n    }\r\n\r\n    var tableObj = opener.document.all.dispTable;\r\n\r\n    tableObj.insertRow();\r\n    var rowNum = tableObj.rows.length - 1;\r\n    var newNode = tableObj.rows(rowNum);\r\n    newNode.bgColor = \"#FFFFFF\";\r\n");
      out.write("    newNode.bordercolor = \"#999999\";\r\n    for(var j = 0; j < 6; j ++){\r\n        newNode.insertCell();\r\n    }\r\n    newNode.cells(0).height = \"22\";\r\n    newNode.cells(0).innerHTML = conditionText + \"<input type='hidden' name='condition' value='\" + conditionValue + \"'>\";\r\n\r\n    newNode.cells(1).align = \"center\";\r\n    newNode.cells(1).innerHTML = operateText + \"<input type='hidden' name='operate' value='\" + operateValue + \"'>\";\r\n\r\n    newNode.cells(2).innerHTML = compareValue + \"<input type='hidden' name='compareValue' value='\" + compareValue + \"'>\";\r\n\r\n    newNode.cells(3).align = \"center\";\r\n    newNode.cells(3).innerHTML = timeLimitText + \"???<input type=\\\"hidden\\\" name=\\\"timeLimit\\\" value=\\\"\" + timeLimitValue + \"\\\">\";\r\n\r\n    newNode.cells(4).align = \"center\";\r\n    if(document.all.hasMotion.checked){\r\n        newNode.cells(4).innerHTML = motionTimeText + \"<input type='hidden' name='motionTime' value='\" + motionTimeValue + \"'>\";\r\n    }else{\r\n        newNode.cells(4).innerHTML =\"<input type='hidden' name='motionTime' value='0'>\";\r\n");
      out.write("    }\r\n\r\n    newNode.cells(5).align = \"center\";\r\n    newNode.cells(5).innerHTML = \"<img src='images/del.gif' alt='??????' style='cursor:pointer' onclick='deleteRow(\" + rowNum + \")'><input type=hidden name=tableRow value=\" + rowNum + \">\";\r\n    //alert(opener.document.all.dispTable.innerHTML);\r\n    //alert(tableObj.tagName);\r\n    //alert(tableObj.rows.length);\r\n\r\n}\r\n//-->\r\n</script>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
