/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:45:24 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.netdisk;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import com.js.lang.Resource;

public final class netdisk_005fmodishared_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.lang.Resource");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhtml;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody;

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
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005fhtml.release();
    _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.release();
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

  String flag="";
  if(request.getAttribute("flag")!=null){
    flag=request.getAttribute("flag").toString();
  }

  String sharedType="";
  if(request.getAttribute("sharedType")!=null){
    sharedType=request.getAttribute("sharedType").toString();
  }
    String shareName="";
  if(request.getAttribute("shareName")!=null){
    shareName=request.getAttribute("shareName").toString();
  }
    String emp="";
  if(request.getAttribute("emp")!=null){
    emp=request.getAttribute("emp").toString();
  }
    String org="";
  if(request.getAttribute("org")!=null){
    org=request.getAttribute("org").toString();
  }
   String group="";
  if(request.getAttribute("group")!=null){
    group=request.getAttribute("group").toString();
  }

   String id="";
  if(request.getAttribute("id")!=null){
    id=request.getAttribute("id").toString();
  }
  String total="";
  total=emp+org+group;
  String local = session.getAttribute("org.apache.struts.action.LOCALE").toString();
  
  /******************xup update**********************/
  String shareNameRR="";
  String shareRRID="";
  if(request.getAttribute("shareNameRR")!=null){
    shareNameRR=request.getAttribute("shareNameRR").toString();
  }if(request.getAttribute("shareRRID")!=null){
    shareRRID=request.getAttribute("shareRRID").toString();
  }
String range = session.getAttribute("browseRange")+"";
Date newDate = new java.util.Date();

String year = Integer.toString(newDate.getYear()+1900);
String month = Integer.toString(newDate.getMonth()+1);
String day = Integer.toString(newDate.getDate());
//读
String rs_year=year,re_year=year;
String rs_month=month,re_month=month;
String rs_day=day,re_day=day;
String rs_hour="00",re_hour="00";
String rs_mini="00",re_mini="00";
//写
String ws_year=year,we_year=year;
String ws_month=month,we_month=month;
String ws_day=day,we_day=day;
String ws_hour="00",we_hour="00";
String ws_mini="00",we_mini="00";

String readTimeFrom="";
if(request.getAttribute("readTimeFrom")!=null){
  readTimeFrom=request.getAttribute("readTimeFrom").toString();
  readTimeFrom=readTimeFrom.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
  rs_year=readTimeFrom.substring(0, 4);
  rs_month=readTimeFrom.substring(4, 6);
  rs_day=readTimeFrom.substring(6, 8);
  rs_hour=readTimeFrom.substring(8, 10);
  rs_mini=readTimeFrom.substring(10, 12);
}

String readTimeTo="";
if(request.getAttribute("readTimeTo")!=null){
  readTimeTo=request.getAttribute("readTimeTo").toString();
  readTimeTo=readTimeTo.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
  re_year=readTimeTo.substring(0, 4);
  re_month=readTimeTo.substring(4, 6);
  re_day=readTimeTo.substring(6, 8);
  re_hour=readTimeTo.substring(8, 10);
  re_mini=readTimeTo.substring(10, 12);
}

String writeTimeFrom="";
if(request.getAttribute("writeTimeFrom")!=null){
  writeTimeFrom=request.getAttribute("writeTimeFrom").toString();
  writeTimeFrom=writeTimeFrom.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
  ws_year=writeTimeFrom.substring(0, 4);
  ws_month=writeTimeFrom.substring(4, 6);
  ws_day=writeTimeFrom.substring(6, 8);
  ws_hour=writeTimeFrom.substring(8, 10);
  ws_mini=writeTimeFrom.substring(10, 12);
}

String writeTimeTo="";
if(request.getAttribute("writeTimeTo")!=null){
  writeTimeTo=request.getAttribute("writeTimeTo").toString();
  writeTimeTo=writeTimeTo.replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
  we_year=writeTimeTo.substring(0, 4);
  we_month=writeTimeTo.substring(4, 6);
  we_day=writeTimeTo.substring(6, 8);
  we_hour=writeTimeTo.substring(8, 10);
  we_mini=writeTimeTo.substring(10, 12);
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
            out.write("\r\n<head>\r\n<title>");
            if (_jspx_meth_bean_005fmessage_005f0(_jspx_th_html_005fhtml_005f0, _jspx_page_context))
              return;
            out.write("</title>\r\n<link href=\"/jsoa/skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n<script language=\"javascript\" src=\"/jsoa/js/checkForm.js\"></script>\r\n<SCRIPT language=\"javascript\" src=\"/jsoa/js/openEndow.js\"></SCRIPT>\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject1.css\">\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DateObject.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/DatePicker.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/editlib.js\"></SCRIPT>\r\n<SCRIPT language=javascript src=\"/jsoa/public/date_picker/tree.js\"></SCRIPT>\r\n\r\n</head>\r\n<body leftmargin=\"0\" topmargin=\"0\"  class=\"MainFrameBox Pupwin\" onLoad=\"init();resizeWin(620,250);\">\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td>\r\n<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"docBoxNoPanel\">\r\n  <tr>\r\n    <td><table width=\"700\">\r\n      <form name=\"form1\" action=\"/jsoa/NetdiskAction.do?action=shared\" method=\"post\">\r\n");
            out.write("        <input type=\"hidden\" name=\"copyitem\" value=\"");
            out.print(request.getAttribute("id"));
            out.write("\">\r\n         <input type=\"hidden\" name=\"folder\" value=\"");
            out.print(request.getParameter("folder"));
            out.write("\">\r\n        <tr>\r\n    \t <td colspan=\"4\" style=\"font-size:13px; \">\r\n    \t  <b>只读共享</b>    \t </td>\r\n    \t</tr>\r\n        <tr colspan=\"4\">\r\n          <td colspan=\"3\">对象&nbsp;<label class=\"mustFillcolor\">*</label>：\r\n            <input type=\"hidden\" name=\"informationReaderId\" value=\"");
            out.print(total);
            out.write("\">\r\n                <input name=\"informationReaderName\" style=\"cursor:pointer\" readonly=\"true\" size=\"72\" value=\"");
            out.print(shareName);
            out.write("\" class=\"inputTextarea\" title=\"请点击选择\"  onClick=\"selectPersionFromOrg('','informationReaderId','informationReaderName',document.all.informationReaderId.value,document.all.informationReaderName.value,'orgPerson','no','_js_','");
            out.print(range );
            out.write("','yes','userorggroup');\">\r\n            </td>\r\n        </tr>\r\n                <tr>\r\n    \t <td height=\"30\" colspan=\"4\" valign=\"bottom\">\r\n\t\t\t时限&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;\r\n\t\t\t<script type=\"text/javascript\">\r\n                var dtpDate = createDatePicker(\"read_start_date\",");
            out.print(rs_year);
            out.write(',');
            out.print(rs_month);
            out.write(',');
            out.print(rs_day);
            out.write(");\r\n            </script> \r\n            <select name=\"readHourStart\">\r\n             ");

             for(int i=0;i<24;i++){ 
               if(i<10){
             
            out.write("\r\n              <option value=\"0");
            out.print(i);
            out.write('"');
            out.write(' ');
if(rs_hour.equals("0"+i)){
            out.write("selected ");
} 
            out.write('>');
            out.write('0');
            out.print(i);
            out.write("</option>\r\n             ");
}else{ 
            out.write("\r\n              <option value=\"");
            out.print(i);
            out.write('"');
            out.write(' ');
if(rs_hour.equals(String.valueOf(i))){
            out.write("selected ");
} 
            out.write('>');
            out.print(i);
            out.write("</option>\r\n             ");
}} 
            out.write("\r\n            </select>&nbsp;时\r\n            <select name=\"readMiniStart\">\r\n            ");

             for(int i=0;i<60;i++){ 
               if(i<10){
             
            out.write("\r\n              <option value=\"0");
            out.print(i);
            out.write('"');
            out.write(' ');
if(rs_mini.equals("0"+i)){
            out.write("selected ");
} 
            out.write('>');
            out.write('0');
            out.print(i);
            out.write("</option>\r\n             ");
}else{ 
            out.write("\r\n              <option value=\"");
            out.print(i);
            out.write('"');
            out.write(' ');
if(rs_mini.equals(String.valueOf(i))){
            out.write("selected ");
} 
            out.write('>');
            out.print(i);
            out.write("</option>\r\n             ");
}} 
            out.write("\r\n            </select>&nbsp;分\r\n\t\t\t—\r\n            <script language=javascript>\r\n\t\t\t    var dtpDate = createDatePicker(\"read_end_date\",");
            out.print(re_year);
            out.write(',');
            out.print(re_month);
            out.write(',');
            out.print(re_day);
            out.write(");\r\n\t\t    </script>\r\n\t\t                <select name=\"readHourEnd\">\r\n             ");

             for(int i=0;i<24;i++){ 
               if(i<10){
             
            out.write("\r\n              <option value=\"0");
            out.print(i);
            out.write('"');
            out.write(' ');
if(re_hour.equals("0"+i)){
            out.write("selected ");
} 
            out.write('>');
            out.write('0');
            out.print(i);
            out.write("</option>\r\n             ");
}else{ 
            out.write("\r\n              <option value=\"");
            out.print(i);
            out.write('"');
            out.write(' ');
if(re_hour.equals(String.valueOf(i))){
            out.write("selected ");
} 
            out.write('>');
            out.print(i);
            out.write("</option>\r\n             ");
}} 
            out.write("\r\n            </select>&nbsp;时\r\n            <select name=\"readMiniEnd\">\r\n            ");

             for(int i=0;i<60;i++){ 
               if(i<10){
             
            out.write("\r\n              <option value=\"0");
            out.print(i);
            out.write('"');
            out.write(' ');
if(re_mini.equals("0"+i)){
            out.write("selected ");
} 
            out.write('>');
            out.write('0');
            out.print(i);
            out.write("</option>\r\n             ");
}else{ 
            out.write("\r\n              <option value=\"");
            out.print(i);
            out.write('"');
            out.write(' ');
if(re_mini.equals(String.valueOf(i))){
            out.write("selected ");
} 
            out.write('>');
            out.print(i);
            out.write("</option>\r\n             ");
}} 
            out.write("\r\n            </select>&nbsp;分\t\t</td>\r\n        </tr>\r\n        <tr><td style=\"border-bottom:2px solid #ccc \">&nbsp;</td></tr>\r\n        <tr>\r\n    \t <td colspan=\"4\" style=\"font-size:13px; \"> <br/><b> 读写共享</b>    \t </td>\r\n    \t</tr>\r\n        \r\n        <tr colspan=\"4\">\r\n          <td colspan=\"3\">对象&nbsp;<label class=\"mustFillcolor\">*</label>：\r\n            <input type=\"hidden\" name=\"informationReaderWriterId\" value=\"");
            out.print(shareRRID);
            out.write("\">\r\n                <input name=\"informationReaderWriterName\" style=\"cursor:pointer\" title=\"请点击选择\" readonly=\"true\" size=\"72\" value=\"");
            out.print(shareNameRR);
            out.write("\" class=\"inputTextarea\" onClick=\"selectPersionFromOrg('','informationReaderWriterId','informationReaderWriterName',document.all.informationReaderWriterId.value,document.all.informationReaderWriterName.value,'orgPerson','no','_js_','");
            out.print(range );
            out.write("','yes','userorggroup');\">\r\n           </td>\r\n        </tr>\r\n                <tr>\r\n    \t <td height=\"30\" colspan=\"4\" valign=\"bottom\">\r\n\t\t\t时限:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\r\n\t\t\t<script language=javascript>\r\n                var dtpDate = createDatePicker(\"write_start_date\",");
            out.print(ws_year);
            out.write(',');
            out.print(ws_month);
            out.write(',');
            out.print(ws_day);
            out.write(");\r\n            </script> \r\n                        <select name=\"writeHourStart\">\r\n             ");

             for(int i=0;i<24;i++){ 
               if(i<10){
             
            out.write("\r\n              <option value=\"0");
            out.print(i);
            out.write('"');
            out.write(' ');
if(ws_hour.equals("0"+i)){
            out.write("selected ");
} 
            out.write('>');
            out.write('0');
            out.print(i);
            out.write("</option>\r\n             ");
}else{ 
            out.write("\r\n              <option value=\"");
            out.print(i);
            out.write('"');
            out.write(' ');
if(ws_hour.equals(String.valueOf(i))){
            out.write("selected ");
} 
            out.write('>');
            out.print(i);
            out.write("</option>\r\n             ");
}} 
            out.write("\r\n            </select>&nbsp;时\r\n            <select name=\"writeMiniStart\">\r\n            ");

             for(int i=0;i<60;i++){ 
               if(i<10){
             
            out.write("\r\n              <option value=\"0");
            out.print(i);
            out.write('"');
            out.write(' ');
if(ws_mini.equals("0"+i)){
            out.write("selected ");
} 
            out.write('>');
            out.write('0');
            out.print(i);
            out.write("</option>\r\n             ");
}else{ 
            out.write("\r\n              <option value=\"");
            out.print(i);
            out.write('"');
            out.write(' ');
if(ws_mini.equals(String.valueOf(i))){
            out.write("selected ");
} 
            out.write('>');
            out.print(i);
            out.write("</option>\r\n             ");
}} 
            out.write("\r\n            </select>&nbsp;分\r\n            —\r\n            <script language=javascript>\r\n\t\t\t    var dtpDate = createDatePicker(\"write_end_date\",");
            out.print(we_year);
            out.write(',');
            out.print(we_month);
            out.write(',');
            out.print(we_day);
            out.write(");\r\n\t\t    </script>\r\n\t\t                <select name=\"writeHourEnd\">\r\n             ");

             for(int i=0;i<24;i++){ 
               if(i<10){
             
            out.write("\r\n              <option value=\"0");
            out.print(i);
            out.write('"');
            out.write(' ');
if(we_hour.equals("0"+i)){
            out.write("selected ");
} 
            out.write('>');
            out.write('0');
            out.print(i);
            out.write("</option>\r\n             ");
}else{ 
            out.write("\r\n              <option value=\"");
            out.print(i);
            out.write('"');
            out.write(' ');
if(we_hour.equals(String.valueOf(i))){
            out.write("selected ");
} 
            out.write('>');
            out.print(i);
            out.write("</option>\r\n             ");
}} 
            out.write("\r\n            </select>&nbsp;时\r\n            <select name=\"writeMiniEnd\">\r\n            ");

             for(int i=0;i<60;i++){ 
               if(i<10){
             
            out.write("\r\n              <option value=\"0");
            out.print(i);
            out.write('"');
            out.write(' ');
if(we_mini.equals("0"+i)){
            out.write("selected ");
} 
            out.write('>');
            out.write('0');
            out.print(i);
            out.write("</option>\r\n             ");
}else{ 
            out.write("\r\n              <option value=\"");
            out.print(i);
            out.write('"');
            out.write(' ');
if(we_mini.equals(String.valueOf(i))){
            out.write("selected ");
} 
            out.write('>');
            out.print(i);
            out.write("</option>\r\n             ");
}} 
            out.write("\r\n            </select>&nbsp;分\t\t</td>\r\n        </tr>\r\n        \r\n        <tr>\r\n          <td height=\"40\" valign=\"bottom\"><input type=\"button\" class=\"btnButton4font\" style=\"cursor:pointer\" onClick=\"save();\" value=\"保存\" />\r\n");
            out.write("\r\n            <input type=\"button\" class=\"btnButton2font\" style=\"cursor:pointer\" onClick=\"window.close();\" value=\"取消\" /></td>\r\n        </tr>\r\n        \r\n      </form>\r\n    </table></td>\r\n  </tr>\r\n</table>\r\n\r\n</td>\r\n</tr>\r\n</table>\r\n</body>\r\n");
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
      out.write("\r\n<script language=\"javascript\">\r\nfunction save(){\r\n  var fl=document.all.informationReaderId.value;\r\n  var f2=document.all.informationReaderWriterId.value;\r\n  if(fl==\"\"&&f2==\"\"){\r\n    alert(\"请选择共享对象！\");\r\n\treturn false;\r\n  }else{\r\n    if(document.all.informationReaderId.value==\"\"){\r\n        document.all.informationReaderName.value=\"\";\r\n    }if(document.all.informationReaderWriterId.value==\"\"){\r\n        document.all.informationReaderWriterName.value=\"\";\r\n    }\r\n    var rs=document.all.read_start_date.value.replaceAll(\"/\",\"-\")+\" \";\r\n    var rsmonth=rs.substring(rs.indexOf(\"-\")+1,rs.lastIndexOf(\"-\"));\r\n    var rsmini=rs.substring(rs.lastIndexOf(\"-\")+1,rs.length);\r\n    if(rsmonth<10)rsmonth=\"0\"+rsmonth;\r\n    if(rsmini<10)rsmini=\"0\"+rsmini;\r\n    var rsTemp=rs.substring(0,4)+\"-\"+rsmonth+\"-\"+rsmini;\r\n    rsTemp+=document.all.readHourStart.value+\":\"+document.all.readMiniStart.value+\":00\";\r\n    \r\n    var re=document.all.read_end_date.value.replaceAll(\"/\",\"-\")+\" \";\r\n    var remonth=re.substring(re.indexOf(\"-\")+1,re.lastIndexOf(\"-\"));\r\n");
      out.write("    var remini=re.substring(re.lastIndexOf(\"-\")+1,re.length);\r\n    if(remonth<10)remonth=\"0\"+remonth;\r\n    if(remini<10)remini=\"0\"+remini;\r\n    var reTemp=re.substring(0,4)+\"-\"+remonth+\"-\"+remini;\r\n    reTemp+=document.all.readHourEnd.value+\":\"+document.all.readMiniEnd.value+\":00\";\r\n       if(rsTemp>reTemp){\r\n          alert('读共享开始日期不能大于结束日期！');\r\n          return false;\r\n        }\r\n  \r\n    var ws=document.all.write_start_date.value.replaceAll(\"/\",\"-\")+\" \";\r\n    var wsmonth=ws.substring(ws.indexOf(\"-\")+1,ws.lastIndexOf(\"-\"));\r\n    var wsmini=ws.substring(ws.lastIndexOf(\"-\")+1,ws.length);\r\n    if(wsmonth<10)wsmonth=\"0\"+wsmonth;\r\n    if(wsmini<10)wsmini=\"0\"+wsmini;\r\n    var wsTemp=ws.substring(0,4)+\"-\"+wsmonth+\"-\"+wsmini;\r\n    wsTemp+=document.all.writeHourStart.value+\":\"+document.all.writeMiniStart.value+\":00\";\r\n    \r\n    \r\n    var we=document.all.write_end_date.value.replaceAll(\"/\",\"-\")+\" \";\r\n    var wemonth=we.substring(we.indexOf(\"-\")+1,we.lastIndexOf(\"-\"));\r\n    var wemini=we.substring(we.lastIndexOf(\"-\")+1,we.length);\r\n");
      out.write("    if(wemonth<10)wemonth=\"0\"+wemonth;\r\n    if(wemini<10)wemini=\"0\"+wemini;\r\n    var weTemp=we.substring(0,4)+\"-\"+wemonth+\"-\"+wemini;\r\n    weTemp+=document.all.writeHourEnd.value+\":\"+document.all.writeMiniEnd.value+\":00\";\r\n\r\n    if(wsTemp>weTemp){\r\n      alert('写共享开始日期不能大于结束日期！');\r\n      return false;\r\n    }\r\n    form1.submit();\r\n  }\r\n}\r\n\r\nfunction init(){\r\n  var flag=\"");
      out.print(flag);
      out.write("\";\r\n  if(flag==\"reload\"){\r\n\twindow.opener.location.reload();\r\n\twindow.close();\r\n  }\r\n}\r\n\r\nfunction resme(){\r\n  document.all.sharetype[0].checked=true;\r\n  document.all.informationReaderName.value=\"\";\r\n  document.all.informationReaderGroup.value=\"\";\r\n  document.all.informationReaderOrg.value=\"\";\r\n  document.all.informationReader.value=\"\";\r\n  document.all.informationReaderId.value=\"\";\r\n\r\n}\r\nString.prototype.replaceAll  = function(s1,s2){    \r\n    return this.replace(new RegExp(s1,\"g\"),s2);    \r\n  } \r\n\r\n</script>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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

  private boolean _jspx_meth_bean_005fmessage_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_005fhtml_005f0, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  bean:message
    org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f0 = (org.apache.struts.taglib.bean.MessageTag) _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
    boolean _jspx_th_bean_005fmessage_005f0_reused = false;
    try {
      _jspx_th_bean_005fmessage_005f0.setPageContext(_jspx_page_context);
      _jspx_th_bean_005fmessage_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_005fhtml_005f0);
      // /netdisk/netdisk_modishared.jsp(120,7) name = bundle type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f0.setBundle("personalwork");
      // /netdisk/netdisk_modishared.jsp(120,7) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_bean_005fmessage_005f0.setKey("webdisk.sharesetup");
      int _jspx_eval_bean_005fmessage_005f0 = _jspx_th_bean_005fmessage_005f0.doStartTag();
      if (_jspx_th_bean_005fmessage_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fbundle_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0);
      _jspx_th_bean_005fmessage_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fmessage_005f0, _jsp_getInstanceManager(), _jspx_th_bean_005fmessage_005f0_reused);
    }
    return false;
  }
}