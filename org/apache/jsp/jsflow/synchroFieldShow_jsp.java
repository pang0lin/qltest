/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:07:46 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import com.js.oa.jsflow.util.DataSynchro;

public final class synchroFieldShow_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.sql.ResultSet");
    _jspx_imports_classes.add("com.js.util.util.DataSourceBase");
    _jspx_imports_classes.add("com.js.oa.jsflow.util.DataSynchro");
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
      response.setContentType("text/html;charset=GBK");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n");
DataSynchro ds = new DataSynchro(); 
String tableId = request.getParameter("tableId"); //表单Id
String processId = request.getParameter("flowId");//流程Id
String nodeId = request.getParameter("fieldType");//节点Id
List tableList = ds.getTableInfo(tableId);
Map infoMap = ds.getSynchroInfo(processId, nodeId);
if(infoMap.get("executeInfo")==null){

      out.write("\r\n<hr />\r\n<!-- width=\"100%\" borderColor=\"#000000\" borderColorDark=\"#e1e1e1\" border=\"1\" cellSpacing=\"0\" cellPadding=\"1\" -->\r\n<table width=\"100%\" border=\"0\" cellSpacing=\"0\" cellPadding=\"5\">\r\n\t\r\n\t<input type=\"hidden\" name=\"toAddOrUpdate\" id=\"toAddOrUpdate\" value=\"add\" />\r\n\t<tr><td width=\"10%\">执行条件：</td>\r\n\t<td width=\"90%\"><input style=\"width:100%;\" type=\"text\" name=\"executeContent\" id=\"executeContent\" value=\"\">\r\n\t<input type=\"hidden\" name=\"dataNodeId\" id=\"dataNodeId\" value=\"");
      out.print(nodeId );
      out.write("\"></td></tr>\r\n\t<tr><td colspan=2>");
for(int i=0;i<tableList.size();i++){
	String[] tableInfo = (String[])tableList.get(i); 
	if(i==0){
      out.write("<input type=\"hidden\" name=\"mainTable\" id=\"mainTable\" value=\"");
      out.print(tableInfo[2] );
      out.write("\" />");
} 
      out.write("\r\n\t<input type=\"checkbox\" name=\"tableSynchro\" id=\"tableSynchro\" value=\"");
      out.print(tableInfo[0] );
      out.write("\" onclick=\"showTR('");
      out.print(tableInfo[0] );
      out.write("',this);\" />\r\n\t");
      out.print(tableInfo[1] );
} 
      out.write("\r\n\t</td></tr>\r\n\t");
for(int i=0;i<tableList.size();i++){ 
	String[] tableInfo = (String[])tableList.get(i); 
      out.write("\r\n\t<tr id=\"tr_");
      out.print(tableInfo[0] );
      out.write("\" style=\"display:none;\"><td colspan=2>\r\n\t<table id=\"synchroTable_");
      out.print(tableInfo[0] );
      out.write("\" width=\"100%\" borderColor=\"#eeeeee\" borderColorDark=\"#e1e1e1\" border=\"1\" cellSpacing=\"0\" cellPadding=\"1\">\r\n\t\t<tr><td colspan=9 style=\"\"><span><b>");
      out.print(tableInfo[1] );
      out.write("</b></span>\r\n\t\t<input type=\"hidden\" name=\"tableType_");
      out.print(tableInfo[0] );
      out.write("\" value=\"");
      out.print((i==0)?"0":"1" );
      out.write("\" />\r\n\t\t<input type=\"hidden\" name=\"OAtableName_");
      out.print(tableInfo[0] );
      out.write("\" value=\"");
      out.print(tableInfo[2] );
      out.write("\" />\r\n\t\t<input type=\"hidden\" name=\"OAtableId_");
      out.print(tableInfo[0] );
      out.write("\" value=\"");
      out.print(tableInfo[0] );
      out.write("\" /></td></tr>\r\n\t\t<tr><td width=\"35%\" colspan=4 nowrap=\"nowrap\">数据源：\r\n\t\t<select id=\"targetBase_");
      out.print(tableInfo[0] );
      out.write("\" name=\"targetBase_");
      out.print(tableInfo[0] );
      out.write("\">\r\n\t\t\t<option value=\"system\" selected>系统默认数据源</option>\r\n\t\t\t");
//数据库连接
			String[] userDataSourceName=com.js.util.config.SystemCommon.getUserDatasourceName();
			if(userDataSourceName!=null){
				for(String dataSourceName:userDataSourceName){
      out.write("\r\n\t\t\t\t<option value=\"");
      out.print(dataSourceName );
      out.write('"');
      out.write('>');
      out.print(dataSourceName );
      out.write("</option>\r\n\t\t\t\t");
}
			}
      out.write("\r\n\t\t</select>\r\n\t\t&nbsp;数据库表：<input type=\"text\" name=\"targetTableName_");
      out.print(tableInfo[0] );
      out.write("\" id=\"targetTableName_");
      out.print(tableInfo[0] );
      out.write("\" value=\"\" /></td>\r\n\t\t<td width=\"10%\" nowrap>\r\n\t\t<input type=\"radio\" onclick=\"setCondSpan(this,'");
      out.print(tableInfo[0] );
      out.write("')\" name=\"insertOrUpdate_");
      out.print(tableInfo[0] );
      out.write("\" value=\"0\" checked />插入\r\n\t\t<input type=\"radio\" onclick=\"setCondSpan(this,'");
      out.print(tableInfo[0] );
      out.write("')\" name=\"insertOrUpdate_");
      out.print(tableInfo[0] );
      out.write("\" value=\"1\" />更新\r\n\t\t</td>\r\n\t\t<td colspan=4 nowrap=\"nowrap\"><input type=\"text\" style=\"width:100%\" name=\"conditionContent_");
      out.print(tableInfo[0] );
      out.write("\" id=\"conditionContent_");
      out.print(tableInfo[0] );
      out.write("\" readonly=\"readonly\" value=\"插入操作时，条件不能进行设置！\" /></td></tr>\r\n\t\t<tr><td colspan=2 align=\"center\">目标数据库表字段名</td><td colspan=2 align=\"center\">对应表单字段名</td>\r\n\t\t<td colspan=4 align=\"center\">取值说明</td>\r\n\t\t<td width=\"5%\" nowrap=\"nowrap\"><input type=\"button\" class=\"btnButton2font\" onclick=\"addTableRow('synchroTable_");
      out.print(tableInfo[0] );
      out.write("','fieldInfo_");
      out.print(tableInfo[0] );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(tableInfo[0] );
      out.write("')\" value=\"新增\" /></tr>\r\n\t\t<tr><td colspan=2 nowrap=\"nowrap\"><input type=\"hidden\" name=\"synchroId_");
      out.print(tableInfo[0] );
      out.write("\" id=\"synchroId_");
      out.print(tableInfo[0] );
      out.write("\" value=\"-1\" />\r\n\t\t<input type='text' style='width:100%' name='targetFieldName_");
      out.print(tableInfo[0] );
      out.write("' id='targetFieldName_");
      out.print(tableInfo[0] );
      out.write("' value='' /></td>\r\n\t\t<td colspan=2 nowrap=\"nowrap\"><div id='selectContent'>");
      out.print(ds.getSelectHtml(tableInfo[0],"fieldInfo_"+tableInfo[0],"") );
      out.write("</div></td>\r\n\t\t<td colspan=4 nowrap=\"nowrap\"><input type='text' style='width:95%' name='dataDefaultValue_");
      out.print(tableInfo[0] );
      out.write("' id='dataDefaultValue_");
      out.print(tableInfo[0] );
      out.write("' value='' />\r\n\t\t<span style=\"cursor:pointer\" onclick=\"setDefault(this,");
      out.print(tableInfo[0] );
      out.write(");\" >设置</span></td>\r\n\t\t<td nowrap=\"nowrap\">&nbsp;<img style='cursor:pointer' border='0' src='/jsoa/images/del.gif' title='删除' onclick=\"del(this,'synchroTable_");
      out.print(tableInfo[0] );
      out.write("')\" ></td></tr>\r\n\t</table>\r\n\t</td></tr>");
} 
      out.write("\r\n</table>\r\n");
}else{ 
      out.write("\r\n<hr />\r\n<!-- width=\"100%\" borderColor=\"#000000\" borderColorDark=\"#e1e1e1\" border=\"1\" cellSpacing=\"0\" cellPadding=\"1\" -->\r\n<table width=\"100%\" border=\"0\" cellSpacing=\"0\" cellPadding=\"10\">\r\n");
//开始加载执行条件
	String[] executeInfo = (String[])infoMap.get("executeInfo");
      out.write("\r\n\t<input type=\"hidden\" name=\"toAddOrUpdate\" id=\"toAddOrUpdate\" value=\"update\" />\r\n\t<tr><td width=\"10%\" nowrap=\"nowrap\">执行条件：<input type=\"hidden\" name=\"executeId\" id=\"executeId\" value=\"");
      out.print(executeInfo[0] );
      out.write("\" /></td>\r\n\t<td width=\"90%\" nowrap=\"nowrap\"><input style=\"width:100%;\" type=\"text\" name=\"executeContent\" id=\"executeContent\" value=\"");
      out.print(executeInfo[1] );
      out.write("\">\r\n\t<input type=\"hidden\" name=\"dataNodeId\" id=\"dataNodeId\" value=\"");
      out.print(executeInfo[4] );
      out.write("\"></td></tr>\r\n\t<tr><td colspan=2>");
for(int i=0;i<tableList.size();i++){ 
	String[] tableInfo = (String[])tableList.get(i); 
	String check= "";
	if(executeInfo[5].indexOf(","+tableInfo[0]+",")>=0)check="checked";
	if(i==0){
      out.write("<input type=\"hidden\" name=\"mainTable\" id=\"mainTable\" value=\"");
      out.print(tableInfo[2] );
      out.write("\" />");
} 
      out.write("\r\n\t<input type=\"checkbox\" name=\"tableSynchro\" id=\"tableSynchro\" ");
      out.print(check );
      out.write(" value=\"");
      out.print(tableInfo[0] );
      out.write("\" onclick=\"showTR('");
      out.print(tableInfo[0] );
      out.write("',this);\" />\r\n\t");
      out.print(tableInfo[1] );
} 
      out.write("\r\n\t</td></tr>\r\n\t");
for(int i=0;i<tableList.size();i++){
	String[] tableInfo = (String[])tableList.get(i);
	if(infoMap.get("cond_"+tableInfo[0])==null){ 
      out.write("\r\n\t<tr id=\"tr_");
      out.print(tableInfo[0] );
      out.write("\" style=\"display:none;\"><td colspan=2>\r\n\t<table id=\"synchroTable_");
      out.print(tableInfo[0] );
      out.write("\" width=\"100%\" borderColor=\"#eeeeee\" borderColorDark=\"#e1e1e1\" border=\"1\" cellSpacing=\"0\" cellPadding=\"1\">\r\n\t\t<tr><td colspan=9 style=\"\"><span><b>");
      out.print(tableInfo[1] );
      out.write("</b></span>\r\n\t\t<input type=\"hidden\" name=\"tableType_");
      out.print(tableInfo[0] );
      out.write("\" value=\"");
      out.print((i==0)?"0":"1" );
      out.write("\" />\r\n\t\t<input type=\"hidden\" name=\"OAtableName_");
      out.print(tableInfo[0] );
      out.write("\" value=\"");
      out.print(tableInfo[2] );
      out.write("\" />\r\n\t\t<input type=\"hidden\" name=\"OAtableId_");
      out.print(tableInfo[0] );
      out.write("\" value=\"");
      out.print(tableInfo[0] );
      out.write("\" />\r\n\t\t<input type=\"hidden\" name=\"condId_");
      out.print(tableInfo[0] );
      out.write("\" value=\"-1\" />\r\n\t\t</td></tr>\r\n\t\t<tr><td width=\"35%\" colspan=4 nowrap=\"nowrap\">数据源：\r\n\t\t<select id=\"targetBase_");
      out.print(tableInfo[0] );
      out.write("\" name=\"targetBase_");
      out.print(tableInfo[0] );
      out.write("\">\r\n\t\t\t<option value=\"system\" selected>系统默认数据源</option>\r\n\t\t\t");
//数据库连接
			String[] userDataSourceName=com.js.util.config.SystemCommon.getUserDatasourceName();
			if(userDataSourceName!=null){
				for(String dataSourceName:userDataSourceName){
      out.write("\r\n\t\t\t\t<option value=\"");
      out.print(dataSourceName );
      out.write('"');
      out.write('>');
      out.print(dataSourceName );
      out.write("</option>\r\n\t\t\t\t");
}
			}
      out.write("\r\n\t\t</select>\r\n\t\t&nbsp;数据库表：<input type=\"text\" name=\"targetTableName_");
      out.print(tableInfo[0] );
      out.write("\" id=\"targetTableName_");
      out.print(tableInfo[0] );
      out.write("\" value=\"\" /></td>\r\n\t\t<td width=\"10%\" nowrap>\r\n\t\t<input type=\"radio\" onclick=\"setCondSpan(this,'");
      out.print(tableInfo[0] );
      out.write("')\" name=\"insertOrUpdate_");
      out.print(tableInfo[0] );
      out.write("\" value=\"0\" checked />插入\r\n\t\t<input type=\"radio\" onclick=\"setCondSpan(this,'");
      out.print(tableInfo[0] );
      out.write("')\" name=\"insertOrUpdate_");
      out.print(tableInfo[0] );
      out.write("\" value=\"1\" />更新</td>\r\n\t\t<td colspan=4 nowrap=\"nowrap\">\r\n\t\t<input type=\"text\" style=\"width:100%\" name=\"conditionContent_");
      out.print(tableInfo[0] );
      out.write("\" id=\"conditionContent_");
      out.print(tableInfo[0] );
      out.write("\" readonly=\"readonly\" value=\"插入操作时，条件不能进行设置！\" /></td></tr>\r\n\t\t<tr><td colspan=2 align=\"center\" nowrap=\"nowrap\">目标数据库表字段名</td><td colspan=2 align=\"center\" nowrap=\"nowrap\">对应表单字段名</td>\r\n\t\t<td colspan=4 align=\"center\" nowrap=\"nowrap\">取值说明</td>\r\n\t\t<td width=\"5%\" nowrap=\"nowrap\"><input type=\"button\" class=\"btnButton2font\" onclick=\"addTableRow('synchroTable_");
      out.print(tableInfo[0] );
      out.write("','fieldInfo_");
      out.print(tableInfo[0] );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(tableInfo[0] );
      out.write("')\" value=\"新增\" /></tr>\r\n\t\t<tr><td colspan=2 nowrap=\"nowrap\"><input type=\"hidden\" name=\"synchroId_");
      out.print(tableInfo[0] );
      out.write("\" id=\"synchroId_");
      out.print(tableInfo[0] );
      out.write("\" value=\"\" />\r\n\t\t<input type='text' style='width:100%' name='targetFieldName_");
      out.print(tableInfo[0] );
      out.write("' id='targetFieldName_");
      out.print(tableInfo[0] );
      out.write("' value='' /></td>\r\n\t\t<td colspan=2 nowrap=\"nowrap\"><div id='selectContent'>");
      out.print(ds.getSelectHtml(tableInfo[0],"fieldInfo_"+tableInfo[0],"") );
      out.write("</div></td>\r\n\t\t<td colspan=4 nowrap=\"nowrap\"><input type='text' style='width:95%' name='dataDefaultValue_");
      out.print(tableInfo[0] );
      out.write("' id='dataDefaultValue_");
      out.print(tableInfo[0] );
      out.write("' value='' />\r\n\t\t<span style=\"cursor:pointer\" onclick=\"setDefault(this,");
      out.print(tableInfo[0] );
      out.write(");\">设置</span></td>\r\n\t\t<td nowrap=\"nowrap\">&nbsp;<img style='cursor:pointer' border='0' src='/jsoa/images/del.gif' title='删除' onclick=\"del(this,'synchroTable_");
      out.print(tableInfo[0] );
      out.write("')\" ></td></tr>\r\n\t</table>\r\n\t</td></tr>");
}else{
	String[] condStrings = (String[])infoMap.get("cond_"+tableInfo[0]);
	
      out.write("\r\n\t<tr id=\"tr_");
      out.print(tableInfo[0] );
      out.write("\"><td colspan=2>\r\n\t<table id=\"synchroTable_");
      out.print(tableInfo[0] );
      out.write("\" width=\"100%\" borderColor=\"#eeeeee\" borderColorDark=\"#e1e1e1\" border=\"1\" cellSpacing=\"0\" cellPadding=\"1\">\r\n\t\t<tr><td colspan=9 style=\"\" nowrap=\"nowrap\"><span><b>");
      out.print(tableInfo[1] );
      out.write("</b></span>\r\n\t\t<input type=\"hidden\" name=\"tableType_");
      out.print(tableInfo[0] );
      out.write("\" value=\"");
      out.print((i==0)?"0":"1" );
      out.write("\" />\r\n\t\t<input type=\"hidden\" name=\"OAtableName_");
      out.print(tableInfo[0] );
      out.write("\" value=\"");
      out.print(condStrings[5] );
      out.write("\" />\r\n\t\t<input type=\"hidden\" name=\"OAtableId_");
      out.print(tableInfo[0] );
      out.write("\" value=\"");
      out.print(condStrings[6] );
      out.write("\" />\r\n\t\t<input type=\"hidden\" name=\"condId_");
      out.print(tableInfo[0] );
      out.write("\" value=\"");
      out.print(condStrings[0] );
      out.write("\" /></td></tr>\r\n\t\t<tr><td width=\"35%\" colspan=4 nowrap=\"nowrap\">数据源：\r\n\t\t<select id=\"targetBase_");
      out.print(tableInfo[0] );
      out.write("\" name=\"targetBase_");
      out.print(tableInfo[0] );
      out.write("\">\r\n\t\t\t<option value=\"system\" ");
      out.print("system".equals(condStrings[1])?"select":"" );
      out.write(">系统默认数据源</option>\r\n\t\t\t");
//数据库连接
			String[] userDataSourceName=com.js.util.config.SystemCommon.getUserDatasourceName();
			if(userDataSourceName!=null){
				for(String dataSourceName:userDataSourceName){
      out.write("\r\n\t\t\t\t<option value=\"");
      out.print(dataSourceName );
      out.write('"');
      out.write(' ');
      out.print(condStrings[1].equals(dataSourceName)?"selected":"" );
      out.write('>');
      out.print(dataSourceName );
      out.write("</option>\r\n\t\t\t\t");
}
			}
      out.write("\r\n\t\t</select>\r\n\t\t&nbsp;数据库表：<input type=\"text\" name=\"targetTableName_");
      out.print(tableInfo[0] );
      out.write("\" id=\"targetTableName_");
      out.print(tableInfo[0] );
      out.write("\" value=\"");
      out.print(condStrings[2] );
      out.write("\" /></td>\r\n\t\t<td width=\"10%\" nowrap>\r\n\t\t<input type=\"radio\" onclick=\"setCondSpan(this,'");
      out.print(tableInfo[0] );
      out.write("')\" name=\"insertOrUpdate_");
      out.print(tableInfo[0] );
      out.write("\" value=\"0\" ");
      out.print("0".equals(condStrings[3])?"checked":"" );
      out.write(" />插入\r\n\t\t<input type=\"radio\" onclick=\"setCondSpan(this,'");
      out.print(tableInfo[0] );
      out.write("')\" name=\"insertOrUpdate_");
      out.print(tableInfo[0] );
      out.write("\" value=\"1\" ");
      out.print("1".equals(condStrings[3])?"checked":"" );
      out.write(" />更新\r\n\t\t</td>\r\n\t\t<td colspan=4 nowrap=\"nowrap\"><input type=\"text\" style=\"width:100%\" name=\"conditionContent_");
      out.print(tableInfo[0] );
      out.write("\" id=\"conditionContent_");
      out.print(tableInfo[0] );
      out.write("\" value=\"");
      out.print(condStrings[4] );
      out.write("\" \r\n\t\t");
      out.print("0".equals(condStrings[3])?"readonly=readonly":"" );
      out.write(" /></td></tr>\r\n\t\t<tr><td colspan=2 align=\"center\" nowrap=\"nowrap\">目标数据库表字段名</td><td colspan=2 align=\"center\" nowrap=\"nowrap\">对应表单字段名</td>\r\n\t\t<td colspan=4 align=\"center\" nowrap=\"nowrap\">取值说明</td>\r\n\t\t<td width=\"5%\" nowrap=\"nowrap\"><input type=\"button\" class=\"btnButton2font\" onclick=\"addTableRow('synchroTable_");
      out.print(tableInfo[0] );
      out.write("','fieldInfo_");
      out.print(tableInfo[0] );
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(tableInfo[0] );
      out.write("')\" value=\"新增\" /></tr>\r\n\t\t");
if(infoMap.get("synchro_"+tableInfo[0])==null){ 
      out.write("\r\n\t\t<tr><td colspan=2 nowrap=\"nowrap\"><input type=\"hidden\" name=\"synchroId_");
      out.print(tableInfo[0] );
      out.write("\" id=\"synchroId_");
      out.print(tableInfo[0] );
      out.write("\" value=\"-1\" />\r\n\t\t<input type='text' style='width:100%' name='targetFieldName_");
      out.print(tableInfo[0] );
      out.write("' id='targetFieldName_");
      out.print(tableInfo[0] );
      out.write("' value='' /></td>\r\n\t\t<td colspan=2 nowrap=\"nowrap\"><div id='selectContent'>");
      out.print(ds.getSelectHtml(tableInfo[0],"fieldInfo_"+tableInfo[0],"") );
      out.write("</div></td>\r\n\t\t<td colspan=4 nowrap=\"nowrap\"><input type='text' style='width:95%' name='dataDefaultValue_");
      out.print(tableInfo[0] );
      out.write("' id='dataDefaultValue_");
      out.print(tableInfo[0] );
      out.write("' value='' />\r\n\t\t<span style=\"cursor:pointer\" onclick=\"setDefault(this,");
      out.print(tableInfo[0] );
      out.write(");\">设置</span></td>\r\n\t\t<td nowrap=\"nowrap\">&nbsp;<img style='cursor:pointer' border='0' src='/jsoa/images/del.gif' title='删除' onclick=\"del(this,'synchroTable_");
      out.print(tableInfo[0] );
      out.write("')\" ></td></tr>\r\n\t\t");
}else{
		List synchroList = (List)infoMap.get("synchro_"+tableInfo[0]);
		for(int j=0;j<synchroList.size();j++){
		String[] synchro = (String[])synchroList.get(j); 
      out.write("\r\n\t\t<tr><td colspan=2 nowrap=\"nowrap\"><input type=\"hidden\" name=\"synchroId_");
      out.print(tableInfo[0] );
      out.write("\" id=\"synchroId_");
      out.print(tableInfo[0] );
      out.write("\" value=\"");
      out.print(synchro[0] );
      out.write("\" />\r\n\t\t<input type='text' style='width:100%' name='targetFieldName_");
      out.print(tableInfo[0] );
      out.write("' id='targetFieldName_");
      out.print(tableInfo[0] );
      out.write("' value='");
      out.print(synchro[1] );
      out.write("' /></td>\r\n\t\t<td colspan=2 nowrap=\"nowrap\"><div id='selectContent'>");
      out.print(ds.getSelectHtml(tableInfo[0],"fieldInfo_"+tableInfo[0],synchro[2]) );
      out.write("</div></td>\r\n\t\t<td colspan=4 nowrap=\"nowrap\"><input type='text' style='width:95%' name='dataDefaultValue_");
      out.print(tableInfo[0] );
      out.write("' id=\"dataDefaultValue_");
      out.print(tableInfo[0] );
      out.write("\" value=\"");
      out.print(synchro[3] );
      out.write("\" />\r\n\t\t<span style=\"cursor:pointer\" onclick=\"setDefault(this,");
      out.print(tableInfo[0] );
      out.write(");\">设置</span></td>\r\n\t\t<td nowrap=\"nowrap\">&nbsp;<img style='cursor:pointer' border='0' src='/jsoa/images/del.gif' title='删除' onclick=\"del(this,'synchroTable_");
      out.print(tableInfo[0] );
      out.write("')\" ></td></tr>\r\n\t\t");
}} 
      out.write("\r\n\t</table>\r\n\t</td></tr>\r\n\t");
}} 
      out.write("\r\n</table>\r\n");
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