/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:45:41 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.module;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.util.util.DataSourceBase;
import com.js.oa.module.service.ModuleMenuService;
import com.js.oa.module.vo.ListItem;
import java.sql.*;
import java.util.*;

public final class menu_005fset_005fwhere_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("java.sql");
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.oa.module.service.ModuleMenuService");
    _jspx_imports_classes.add("com.js.util.util.DataSourceBase");
    _jspx_imports_classes.add("com.js.oa.module.vo.ListItem");
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
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");

String tableId = request.getParameter("tableId");
Connection conn=null;
ModuleMenuService bd = new ModuleMenuService();
List allField=bd.getTableFieldsByIdCont("0",tableId);


      out.write("\r\n<html>\r\n<head>\r\n<title>设置条件</title>\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject2.css\">\r\n</head>\r\n<body leftmargin=\"0\" scroll=\"auto\" topmargin=\"0\" class=\"MainFrameBox Pupwin\" onload=\"LoadType2();\"  >\r\n\r\n<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"docBoxNoPanel\">\r\n<tr>\r\n<td valign=\"top\">\r\n\r\n  <form  name=\"frm\" action=\"/AssociateAddAction.do\" method=\"POST\">\r\n     <input type=\"hidden\" name=\"tableName\" value=\"");
      out.print(request.getParameter("tableName"));
      out.write("\">\r\n     <input type=\"hidden\" id=\"menuDefQueryCondition\" name=\"menuDefQueryCondition\" value=\"\">\r\n     <input type=\"hidden\" id=\"menuDefQueryOrder\" name=\"menuDefQueryOrder\" value=\"\">\r\n\r\n     <table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\"> \r\n         <tr>\r\n            <td width=\"60\" nowrap=\"nowrap\" valign=top>列表条件：</td>            \r\n            <td colspan=\"4\">\r\n                    <select id=\"mainTblField1\">\r\n                            <option value=\"-1\">--请选择--</option>\r\n\t\t\t      ");

				  if (allField != null && allField.size() > 0) {
				    for(int i = 0; i < allField.size(); i++) {
				      ListItem item = (ListItem)allField.get(i);
				
      out.write("\r\n\t\t\t\t    <option value=\"");
      out.print(item.getId());
      out.write('"');
      out.write('>');
      out.print(item.getName());
      out.write("</option>\r\n\t\t\t\t");
}}
      out.write("\r\n                        </select>\r\n                        <select id=\"mainoper1\">\r\n                        <option value=\">\">></option>\r\n                        <option value=\"&lt;\">&lt;</option>\r\n                        <option value=\">=\">>=</option>\r\n                        <option value=\"&lt;=\">&lt;=</option>\r\n                        <option value=\"=\">=</option>\r\n                        <option value=\"&lt;&gt;\">&lt;&gt;</option>\r\n                        <option value=\"like\">like</option>\r\n                        </select>\r\n                        <input type=\"text\" size=\"5\" id=\"mainval1\" Class=\"inputtext\"/>\r\n                        <select id=\"mainuion\"><option value=\"-1\">关系</option><option\r\n                        value=\"and\">并且</option><option value=\"or\">或者</option></select>\r\n                        <select id=\"mainTblField2\">\r\n                            <option value=\"--请选择--\">--请选择--</option>\r\n                ");

				  if (allField != null && allField.size() > 0) {
				    for(int i = 0; i < allField.size(); i++) {
				      ListItem item = (ListItem)allField.get(i);
				
      out.write("\r\n\t\t\t\t    <option value=\"");
      out.print(item.getId());
      out.write('"');
      out.write('>');
      out.print(item.getName());
      out.write("</option>\r\n\t\t\t\t");
}}
      out.write("\r\n                        </select>\r\n                        <select id=\"mainoper2\">\r\n                        <option value=\">\">></option>\r\n                        <option value=\"&lt;\">&lt;</option>\r\n                        <option value=\">=\">>=</option>\r\n                        <option value=\"&lt;=\">&lt;=</option>\r\n                        <option value=\"=\">=</option>\r\n                        <option value=\"&lt;&gt;\">&lt;&gt;</option>\r\n                        <option value=\"like\">like</option>\r\n                        </select>\r\n                        <input type=\"text\" size=\"5\" Class=\"inputtext\" id=\"mainval2\"/>\r\n                        <br/>\r\n                        <div id=\"isShow\" name=\"isShow\" style=\"display:none;\">\r\n                        <input type=\"button\" id=\"constrainA\" href=\"#\" onclick=\"javascript:showFilter('1','1');\" class=\"btnButton2font\" value=\"重设\"/>\r\n                        <input type=\"button\" id=\"constrainA\" href=\"#\" onclick=\"javascript:showFilter('0','1');\" class=\"btnButton2font\" value=\"追加(AND)\" />\r\n");
      out.write("                        <input type=\"button\" id=\"constrainA\" href=\"#\" onclick=\"javascript:showFilter('0','0');\" class=\"btnButton2font\" value=\"追加(OR)\" />\r\n                        </div><div id=\"isShows\" name=\"isShows\" style=\"display:inline;\">\r\n                        <input type=\"button\" id=\"constrainA\" href=\"#\" onclick=\"javascript:showFilter('0','1');\" class=\"btnButton2font\" value=\"确定\" />\r\n                        </div>\r\n                        <input type=\"button\" id=\"constrainB\" href=\"#\" onclick=\"javascript:qingchu();\" class=\"btnButton2font\" value=\"清除\" />                       \r\n               \r\n              </td>\r\n          </tr>\r\n          <tr>\r\n                 <td colspan=\"6\" align=\"right\">\r\n                 <span style=\"display:none\"><select id=\"subTblField1Shadow\"></select></span>\r\n                 <span style=\"display:none\"><select id=\"subTblField2Shadow\"></select></span>\r\n                 <span style=\"display:none\"><select id=\"mainTblField1Shadow\"></select></span>\r\n                 <span style=\"display:none\"><select id=\"mainTblField2Shadow\"></select></span>\r\n");
      out.write("                 </td>\r\n         </tr>\r\n         <tr>\r\n                 <td>&nbsp;</td>\r\n                 <td valign=\"top\" align=\"left\" colspan=\"4\"><span id=\"filterSpan\"\r\n                 style=\"display:\"><textarea id=\"filterStr\" cols=\"78\"\r\n                 rows=\"3\" class=\"inputtextarea\" readonly ></textarea></span></td>\r\n         </tr>\r\n         <tr>\r\n            <td width=\"60\" nowrap=\"nowrap\" valign=top>列表排序：</td>\r\n            <td colspan=\"4\">\r\n            <input type=\"radio\" id=\"defOrder1\" value=\"-1\" checked=\"checked\" onclick=\"changeDef(0);\"/>默认\r\n            <input type=\"radio\" id=\"defOrder2\" value=\"0\" onclick=\"changeDef(1);\"/>定义\r\n            </td>\r\n         </tr>  \r\n         <tr id=\"firField\"  style=\"display:none;\">\r\n                        <td>&nbsp;&nbsp;</td>\r\n                        <td colspan=\"4\">\r\n                            <span id=\"fSpan1\" style=\"display:\">\r\n                            <input type=\"checkbox\" id=\"field1\" onclick=\"\"/>字段一\r\n                            <select id=\"fieldName1\">\r\n                             ");

				  if (allField != null && allField.size() > 0) {
				    for(int i = 0; i < allField.size(); i++) {
				      ListItem item = (ListItem)allField.get(i);
				
      out.write("\r\n\t\t\t\t    <option value=\"");
      out.print(item.getId());
      out.write('"');
      out.write('>');
      out.print(item.getName());
      out.write("</option>\r\n\t\t\t\t");
}}
      out.write("\r\n                            </select>\r\n                            <input type=\"checkbox\" id=\"field1Desc1\" value=\"DESC\" onclick=\"\"/>降序&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\r\n                            </span>\r\n                        </td>                       \r\n                       \r\n                </tr>\r\n                <tr id=\"secField\"  style=\"display:none;\">  \r\n                        <td>&nbsp;&nbsp;</td>                    \r\n                        <td colspan=\"4\">\r\n                            <span id=\"fSpan2\" style=\"display:\">\r\n                            <input type=\"checkbox\" id=\"field2\" onclick=\"\"/>字段二\r\n                            <select id=\"fieldName2\">\r\n                             ");

				  if (allField != null && allField.size() > 0) {
				    for(int i = 0; i < allField.size(); i++) {
				      ListItem item = (ListItem)allField.get(i);
				
      out.write("\r\n\t\t\t\t    <option value=\"");
      out.print(item.getId());
      out.write('"');
      out.write('>');
      out.print(item.getName());
      out.write("</option>\r\n\t\t\t\t");
}}
      out.write("\r\n                            </select>\r\n                            <input type=\"checkbox\" id=\"field1Desc2\" value=\"DESC\" onclick=\"\"/>降序&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\r\n                            </span>\r\n                        </td>\r\n                        \r\n                        \r\n                </tr>\r\n                <tr id=\"thirdField\" style=\"display:none;\">                        \r\n                        <td>&nbsp;&nbsp;</td> \r\n                        <td colspan=\"4\">\r\n                            <span id=\"fSpan3\" style=\"display:\">\r\n                            <input type=\"checkbox\" id=\"field3\" onclick=\"\"/>字段三\r\n                            <select id=\"fieldName3\">\r\n                             ");

				  if (allField != null && allField.size() > 0) {
				    for(int i = 0; i < allField.size(); i++) {
				      ListItem item = (ListItem)allField.get(i);
				
      out.write("\r\n\t\t\t\t    <option value=\"");
      out.print(item.getId());
      out.write('"');
      out.write('>');
      out.print(item.getName());
      out.write("</option>\r\n\t\t\t\t");
}}
      out.write("\r\n                            </select>\r\n                            <input type=\"checkbox\" id=\"field1Desc3\" value=\"DESC\" onclick=\"\"/>降序\r\n                            <input type=\"button\" onclick=\"javascript:showOrder();\" class=\"btnButton2font\" value=\"确定\" />\r\n                            <span style=\"display:none\"><select id=\"fieldName3Shadow\"></select></span>\r\n                            </span>\r\n                        </td>\r\n                </tr>\r\n                <tr id=\"orderSpan\" style=\"display:none;\">\r\n                        <td>&nbsp;&nbsp;</td>\r\n                        <td valign=\"top\" colspan=\"4\">                           \r\n                                <textarea id=\"orderStr\" cols=\"78\" rows=\"3\" Class=\"inputtextarea\"></textarea>                           \r\n                        </td>\r\n                </tr>                           \r\n                         \r\n\t\t<tr>\r\n\t      <td colspan=5 height=\"45\" valign=\"bottom\">\r\n\t\t\t<input type=\"button\" class=\"btnButton4font\" onclick=\"javascript:save();\" value=\"保存退出\" />\r\n");
      out.write("\t\t\t<input type=\"button\" class=\"btnButton2font\" onclick=\"window.location.reload();\" value=\"重置\" />\r\n\t\t\t<input type=\"button\" class=\"btnButton2font\" onclick=\"javascript:window.close()\" value=\"退出\" />\r\n\t\t  </td>\r\n        </tr>\r\n\t\t\t  \t\t  \r\n     </table>\r\n  </form>\r\n</td>\r\n</tr>\r\n<tr><td>&nbsp;</td></tr>\r\n</table>\r\n</body>\r\n</html>\r\n<script language=\"javascript\">\r\nfunction qingchu(){\r\n    document.getElementById(\"menuDefQueryCondition\").value = \"\";\r\n    document.getElementById(\"filterStr\").value = \"\"\r\n}\r\nfunction LoadType2(){\r\n\tvar filterStr=opener.document.all.menuDefQueryCondition.value;\r\n\tif(filterStr!=\"\"){\r\n\t   document.getElementById(\"isShow\").style.display='inline';\r\n\t   document.getElementById(\"isShows\").style.display='none';\r\n\t}\r\n\tvar orderStr=opener.document.all.menuDefQueryOrder.value;\r\n\tvar allWhere=opener.document.all.filterStr.value;\r\n\t\r\n\tif(allWhere==opener.document.all.filterStr.title){\r\n\t\tallWhere=\"\";\r\n\t}\r\n\t\r\n\tdocument.all.menuDefQueryCondition.value=filterStr;\r\n\tdocument.all.menuDefQueryOrder.value=orderStr;\t\r\n");
      out.write("\tif(allWhere.indexOf(\"ORDER BY\")>=0){\r\n\t\tfilterStr=allWhere.substring(0,allWhere.indexOf(\"ORDER BY\"));\r\n\t\torderStr=allWhere.substring(allWhere.indexOf(\"ORDER BY\"));\r\n\t}else{\r\n\t\tfilterStr=allWhere;\r\n\t\torderStr=\"\";\r\n\t}\t\r\n\tif(filterStr.charAt(filterStr.length-1)==\" \"){\r\n\t\tfilterStr=filterStr.substring(0,filterStr.length-1);\r\n\t}\r\n\tdocument.all.filterStr.value=filterStr;\r\n\tdocument.all.orderStr.value=orderStr;\r\n\t\r\n\tif(orderStr!=\"\"){\r\n\t\tdocument.all.defOrder2.checked=true;\r\n\t\tchangeDef(1);\r\n\t\t\r\n\t    var orderDir;\r\n        var targetField;\r\n\t\torderStr=opener.document.all.menuDefQueryOrder.value;\r\n\t\tvar oEArray = orderStr.split(\",\");\r\n\t\tif (oEArray) {//GET SAPERATE ORDERS\r\n            var subOeArray;\r\n   \t\t\tfor(var i = 0; i < oEArray.length; i++) {\r\n      \t\t\tsubOeArray = oEArray[i].split(\" \");//GET EACH ORDER EXPRESSTION\r\n      \t\t\t//alert(subOeArray.length);\r\n      \t\t\tif (subOeArray) {\r\n        \t\t\tvar botOeArray;\r\n        \t\t\tfor(var j = 0; j < subOeArray.length; j++) {\r\n          \t\t\t\t//alert(subOeArray[j]);\r\n          \t\t\t\tif (subOeArray[j].indexOf(\".\") > 0) {//MATCH THE ORDER FIELD\r\n");
      out.write("            \t\t\t\t//alert(subOeArray[j]);\r\n            \t\t\t\tvar target = subOeArray[j].split(\".\");\r\n            \t\t\t\t//alert(target[1]);\r\n            \t\t\t\ttargetField = target[1];\r\n          \t\t\t\t}\r\n          \t\t\t\tif (subOeArray[j].indexOf(\"C\") > 0) {\r\n            \t\t\t\t//alert(subOeArray[j]);\r\n            \t\t\t\torderDir = subOeArray[j];\r\n            \t\t\t\t//alert(orderDir);\r\n          \t\t\t\t}\r\n        \t\t\t}\r\n        \t\t\t//matchField(i, targetField, orderDir);\r\n      \t\t\t}\r\n      \t\t\tmatchField(i, targetField, orderDir);\r\n    \t\t}\r\n  \t\t}\r\n\t}\r\n}\r\n\r\n\r\nfunction matchField(zOrder, targetField, orderDir) {  \r\n  switch(zOrder) {\r\n    case 0:\r\n    \tvar sel = document.getElementById(\"fieldName1\");\r\n    \t\r\n        document.getElementById(\"defOrder1\").checked = false;\r\n        for(var i = 0; i < sel.options.length; i++) {\r\n          if (sel.options[i].value == targetField) {\r\n          \tsel.options[i].selected = true;\r\n                  break;\r\n          }\r\n        }\r\n    \tdocument.getElementById(\"field1\").checked = true;\r\n        if (orderDir == \"DESC\")\r\n");
      out.write("        \tdocument.getElementById(\"field1Desc1\").checked = true;\r\n        else\r\n            document.getElementById(\"field1Desc1\").checked = false;\r\n        \r\n    \tbreak;\r\n    case 1:\r\n        var sel = document.getElementById(\"fieldName2\");        \r\n        document.getElementById(\"defOrder1\").checked = false;\r\n        for(var i = 0; i < sel.options.length; i++) {\r\n          if (sel.options[i].value == targetField) {\r\n          \tsel.options[i].selected = true;\r\n                  break;\r\n          }\r\n        }\r\n    \tdocument.getElementById(\"field2\").checked = true;\r\n        if (orderDir == \"DESC\")\r\n        \tdocument.getElementById(\"field1Desc2\").checked = true;\r\n        else\r\n             document.getElementById(\"field1Desc2\").checked = false;\r\n        break;\r\n    case 2:\r\n        var sel = document.getElementById(\"fieldName3\");\r\n        document.getElementById(\"defOrder1\").checked = false;\r\n        for(var i = 0; i < sel.options.length; i++) {\r\n          if (sel.options[i].value == targetField) {\r\n          \tsel.options[i].selected = true;\r\n");
      out.write("                  break;\r\n          }\r\n        }\r\n    \tdocument.getElementById(\"field3\").checked = true;\r\n        if (orderDir == \"DESC\")\r\n        \tdocument.getElementById(\"field1Desc3\").checked = true;\r\n        else\r\n            document.getElementById(\"field1Desc3\").checked = false;\r\n        break;\r\n  }\r\n}\r\nfunction save(){\r\n\topener.document.all.menuDefQueryCondition.value=document.all.menuDefQueryCondition.value;\t\r\n\topener.document.all.menuDefQueryOrder.value=document.all.menuDefQueryOrder.value;\r\n\topener.document.all.filterStr.value=document.all.filterStr.value+\" \"+document.all.orderStr.value;\r\n\twindow.close();\r\n}\r\n\r\nfunction filterCancle2() {\r\n  //document.getElementById(\"filterStr\").value = '';\r\n  //document.getElementById(\"filterSpan\").style.display = 'none';\r\n  //$(\"menuDefQueryCondition\").value = '';\r\n  $(\"mainTblField1\").selectedIndex = 0;\r\n  $(\"mainoper1\").selectedIndex = 0;\r\n  $(\"mainval1\").value = '';\r\n  $(\"mainuion\").selectedIndex = 0;\r\n  $(\"mainTblField2\").selectedIndex = 0;\r\n  $(\"mainoper2\").selectedIndex = 0;\r\n");
      out.write("  $(\"mainval2\").value = '';\r\n}\r\n\r\nfunction showFilter(flag,operFlag) {\r\n\tif(document.getElementById(\"isShows\").style.display=='inline'){\r\n\t    document.getElementById(\"isShow\").style.display='inline';\r\n\t    document.getElementById(\"isShows\").style.display='none';\r\n\t}\r\n  if (document.getElementById(\"mainTblField1\").value == \"--请选择--\") {\r\n    alert(\"请选择表过滤条件!\");\r\n    return false;\r\n  }\r\n  if (document.getElementById(\"mainTblField1\").value != \"--请选择--\" &&\r\n      document.getElementById(\"mainval1\").value == '') {\r\n    alert(\"请选择过滤条件值!\");\r\n    return false;\r\n  }\r\n  if (document.getElementById(\"mainval2\").value == '' &&\r\n      document.getElementById(\"mainTblField2\").value != \"--请选择--\") {\r\n    alert(\"请选择过滤条件值!\");\r\n    return false;\r\n  }\r\n  if (document.getElementById(\"mainval2\").value != '' &&\r\n      document.getElementById(\"mainTblField2\").value == \"--请选择--\") {\r\n      alert(\"请选择过滤条件!\");\r\n  }\r\n    if (flag == '1') {\r\n           document.getElementById(\"menuDefQueryCondition\").value = \"\";\r\n           document.getElementById(\"filterStr\").value = \"\"\r\n");
      out.write("    }\r\n  if (document.getElementById(\"filterStr\").value != '' &&\r\n      document.all.menuDefQueryCondition.value != ''){\r\n\t  if(operFlag=='1'){\r\n\t\t  document.getElementById(\"filterStr\").value += ' and ' + concateFilter(flag,operFlag);\r\n\t  }else{\r\n\t\t  document.getElementById(\"filterStr\").value += ' or ' + concateFilter(flag,operFlag);\r\n\t  }\r\n  }else{\r\n     document.getElementById(\"filterStr\").value = concateFilter(flag);\r\n  }\r\n  filterCancle2();\r\n}\r\n\r\nfunction concateFilter(flag,operFlag) {\r\n  \tvar filterStr = \"\";        \r\n\tvar mainT = opener.document.getElementById(\"menuListTableMapSel\");\r\n    var mainF1 = document.getElementById(\"mainTblField1\");\r\n    var mainO1 = document.getElementById(\"mainoper1\");\r\n    var mainF2 = document.getElementById(\"mainTblField2\");\r\n    var mainO2 = document.getElementById(\"mainoper2\");\r\n\r\n    if (mainT.value != \"--请选择--\") {         \r\n          filterStr += \"( \" + document.getElementById(\"mainTblField1\").value + ' ' + document.getElementById(\"mainoper1\").value + ' ' + ((document.getElementById(\"mainoper1\").value == 'like') ? \"'%\" + document.getElementById(\"mainval1\").value + \"%'\" : document.getElementById(\"mainval1\").value);\r\n");
      out.write("          \r\n          if (document.getElementById('mainuion').value != \"-1\")\r\n            filterStr += ' ' + document.getElementById('mainuion').value + ' ' +  document.getElementById(\"mainTblField2\").value + ' ' + document.getElementById(\"mainoper2\").value + ' ' + ((document.getElementById(\"mainoper2\").value == 'like') ? \"'%\" + document.getElementById(\"mainval2\").value + \"%'\" : document.getElementById(\"mainval2\").value);\r\n    }        \r\n    filterStr += \" ) \";\r\n    if (document.all.menuDefQueryCondition.value.length > 0){\r\n    \t if(operFlag=='1'){\r\n           document.all.menuDefQueryCondition.value += \" and \" + filterStr;\r\n    \t }else{\r\n    \t\t document.all.menuDefQueryCondition.value += \" or \" + filterStr;\r\n    \t }\r\n    }else{\r\n            document.all.menuDefQueryCondition.value += filterStr;\r\n    }\r\n    filterStr = \"\";       \r\n\r\n    if (mainT.value != \"--请选择--\") {\r\n           filterStr += \" ( \" + mainT.options[mainT.selectedIndex].text + '.' + mainF1.options[mainF1.selectedIndex].text + ' ' + mainO1.options[mainO1.selectedIndex].text + ' ' + document.getElementById(\"mainval1\").value;\r\n");
      out.write("           if (document.getElementById('mainuion').value != \"-1\")\r\n             filterStr += ' ' + document.getElementById('mainuion').value + ' ' + mainT.options[mainT.selectedIndex].text + '.' + mainF2.options[mainF2.selectedIndex].text + ' ' + mainO2.options[mainO2.selectedIndex].text + ' ' + document.getElementById(\"mainval2\").value;\r\n   }\r\n   filterStr += \" ) \";\r\n\r\n   return filterStr;\r\n}\r\n\r\nfunction showOrder() {\r\n\r\n    if (document.getElementById(\"defOrder1\").checked) {\r\n       document.getElementById(\"orderStr\").value = '';\r\n       document.getElementById(\"orderSpan\").style.display = \"none\";\r\n       return;\r\n    }\r\n\r\n    document.getElementById(\"orderStr\").value = '';\r\n    if (document.getElementById(\"orderStr\").value != '')\r\n      document.getElementById(\"orderStr\").value += ' and ' + concateOrder();\r\n    else\r\n      document.getElementById(\"orderStr\").value = concateOrder();\r\n    document.getElementById(\"orderSpan\").style.display = \"\";\r\n    document.getElementById(\"orderStr\").focus();\r\n}\r\n\r\nfunction concateOrder() {\r\n");
      out.write("  document.all.menuDefQueryOrder.value = \"\";\r\n\r\n  var orderStr = \"\";\r\n  var selT =  opener.document.getElementById(\"menuListTableMapSel\");\r\n  var selF1 = document.getElementById(\"fieldName1\");\r\n  var selF2 = document.getElementById(\"fieldName2\");\r\n  var selF3 = document.getElementById(\"fieldName3\");\r\n\r\n  if ( document.getElementById(\"field1\").checked) {\r\n    if (orderStr.indexOf('D') > 0) {\r\n    \tdocument.all.menuDefQueryOrder.value += \", \" + opener.document.getElementById(\"menuListTableName\").value + '.' + document.getElementById(\"fieldName1\").value + ' ' + ((document.getElementById(\"field1Desc1\").checked) ? document.getElementById(\"field1Desc1\").value : 'ASC');    \r\n    \torderStr += \", \" + selT.options[selT.selectedIndex].text + '.' + selF1.options[selF1.selectedIndex].text + ' ' + ((document.getElementById(\"field1Desc1\").checked) ? document.getElementById(\"field1Desc1\").value : 'ASC');\r\n    } else {\r\n    \tdocument.all.menuDefQueryOrder.value += \"ORDER BY \" + opener.document.getElementById(\"menuListTableName\").value + '.' + document.getElementById(\"fieldName1\").value + ' ' + ((document.getElementById(\"field1Desc1\").checked) ? document.getElementById(\"field1Desc1\").value : 'ASC');    \r\n");
      out.write("    \torderStr += \"ORDER BY \" + selT.options[selT.selectedIndex].text + '.' + selF1.options[selF1.selectedIndex].text + ' ' + ((document.getElementById(\"field1Desc1\").checked) ? document.getElementById(\"field1Desc1\").value : 'ASC');\r\n    }\r\n  }\r\n\r\n  if ( document.getElementById(\"field2\").checked) {\r\n    if (orderStr.indexOf(\"D\") > 0) {\r\n    \tdocument.all.menuDefQueryOrder.value += \", \" + opener.document.getElementById(\"menuListTableName\").value + '.' + document.getElementById(\"fieldName2\").value + ' ' + ((document.getElementById(\"field1Desc2\").checked) ? document.getElementById(\"field1Desc2\").value : 'ASC');   \r\n    \torderStr += \", \" + selT.options[selT.selectedIndex].text + '.' + selF2.options[selF2.selectedIndex].text + ' ' + ((document.getElementById(\"field1Desc2\").checked) ? document.getElementById(\"field1Desc2\").value : 'ASC');\r\n    } else {\r\n    \tdocument.all.menuDefQueryOrder.value += \"ORDER BY \" + opener.document.getElementById(\"menuListTableName\").value + '.' + document.getElementById(\"fieldName2\").value + ' ' + ((document.getElementById(\"field1Desc2\").checked) ? document.getElementById(\"field1Desc2\").value : 'ASC');   \r\n");
      out.write("    \torderStr += \"ORDER BY \" + selT.options[selT.selectedIndex].text + '.' + selF2.options[selF2.selectedIndex].text + ' ' + ((document.getElementById(\"field1Desc2\").checked) ? document.getElementById(\"field1Desc2\").value : 'ASC');\r\n    }\r\n  }\r\n\r\n  if ( document.getElementById(\"field3\").checked) {\r\n    if (orderStr.indexOf(\"D\") > 0) {\r\n    \tdocument.all.menuDefQueryOrder.value += \", \" + opener.document.getElementById(\"menuListTableName\").value + '.' + document.getElementById(\"fieldName3\").value + ' ' + ((document.getElementById(\"field1Desc3\").checked) ? document.getElementById(\"field1Desc3\").value : 'ASC');\r\n    \torderStr += \", \" + selT.options[selT.selectedIndex].text + '.' + selF2.options[selF2.selectedIndex].text + ' ' + ((document.getElementById(\"field1Desc3\").checked) ? document.getElementById(\"field1Desc3\").value : 'ASC');\r\n    } else {\r\n    \tdocument.all.menuDefQueryOrder.value += \"ORDER BY \" + opener.document.getElementById(\"menuListTableName\").value + '.' + document.getElementById(\"fieldName3\").value + ' ' + ((document.getElementById(\"field1Desc3\").checked) ? document.getElementById(\"field1Desc3\").value : 'ASC');   \r\n");
      out.write("    \torderStr += \"ORDER BY \" + selT.options[selT.selectedIndex].text + '.' + selF2.options[selF2.selectedIndex].text + ' ' + ((document.getElementById(\"field1Desc3\").checked) ? document.getElementById(\"field1Desc3\").value : 'ASC');\r\n    }\r\n  }\r\n   return orderStr;\r\n}\r\nfunction changeDef(flag) {\r\n   if(flag==0){\r\n   document.getElementById(\"field1\").checked = false;\r\n   document.getElementById(\"field2\").checked = false;\r\n   document.getElementById(\"field3\").checked = false;\r\n\r\n   document.getElementById(\"defOrder1\").checked = true;\r\n   document.getElementById(\"defOrder2\").checked = false;\r\n   if (document.getElementById(\"field1Desc2\").checked)\r\n      document.getElementById(\"field1Desc2\").checked = false;\r\n   if (document.getElementById(\"field1Desc1\").checked)\r\n      document.getElementById(\"field1Desc1\").checked = false;\r\n   if (document.getElementById(\"field1Desc3\").checked)\r\n      document.getElementById(\"field1Desc3\").checked = false;\r\n\r\n   document.all.menuDefQueryOrder.value = '';\r\n   document.all.fieldName1.selectedIndex = 0;\r\n");
      out.write("   document.all.fieldName2.selectedIndex = 0;\r\n   document.all.fieldName3.selectedIndex = 0;\r\n   document.all.fSpan1.style.display = 'none';\r\n   document.all.fSpan2.style.display = 'none';\r\n   document.all.fSpan3.style.display = 'none';\r\n   document.all.firField.style.display = 'none';\r\n   document.all.secField.style.display = 'none';\r\n   document.all.thirdField.style.display = 'none';\r\n\r\n   document.getElementById(\"orderStr\").value = '';\r\n   document.getElementById(\"orderSpan\").style.display = \"none\";\r\n   }else{\r\n   document.all.fSpan1.style.display = '';\r\n   document.all.fSpan2.style.display = '';\r\n   document.all.fSpan3.style.display = '';\r\n   document.all.firField.style.display = '';\r\n   document.all.secField.style.display = '';\r\n   document.all.thirdField.style.display = '';\r\n   document.getElementById(\"defOrder1\").checked = false;\r\n   document.getElementById(\"defOrder2\").checked = true;\r\n   document.getElementById(\"orderSpan\").style.display = \"\";\r\n   }\r\n}\r\n</script>\r\n");
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
