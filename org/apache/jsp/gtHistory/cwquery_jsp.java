/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:04:20 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.gtHistory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class cwquery_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

String userAcount=(String)session.getAttribute("userAccount");
String mothodAction=request.getParameter("action");
String a1=(String)session.getAttribute("skin");
String a2=(String)session.getAttribute("browserVersion");

//时间
java.util.Date startDate = new java.util.Date() ;
java.util.Date endDate = new java.util.Date() ;
String queryItem = request.getParameter("queryItem");
if(request.getParameter("queryBeginDate")!=null&&queryItem!=null&&queryItem.equals("1"))
    startDate =  new java.util.Date(String.valueOf(request.getParameter("queryBeginDate")).replace('-','/') );
if(request.getParameter("queryEndDate")!=null&&queryItem!=null&&queryItem.equals("1"))
    endDate =  new java.util.Date(String.valueOf(request.getParameter("queryEndDate")).replace('-','/') );
int beginYear = startDate.getYear()+1900 ;
int beginMonth = startDate.getMonth()+1 ;
int beginDate = startDate.getDate();
int endYear = endDate.getYear()+1900 ;
int endMonth = endDate.getMonth()+1 ;
int endDate1 = endDate.getDate();

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
            out.write("\r\n<head>\r\n<META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n<meta http-equiv=\"pragma\" content=\"no-cache\">\r\n<meta http-equiv=\"cache-control\" content=\"no-cache\">\r\n<meta http-equiv=\"expires\" content=\"0\">   \r\n<title>呈文查询</title>\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject1.css\">\r\n<script language=javascript src=\"/jsoa/public/date_picker/DateObject.js\"></script>\r\n<script language=javascript src=\"/jsoa/public/date_picker/DatePicker.js\" charset=\"GBK\"></script>\r\n<script language=javascript src=\"/jsoa/public/date_picker/editlib.js\"></script>\r\n<script language=javascript src=\"/jsoa/public/date_picker/tree.js\"></script>\r\n<SCRIPT language=javascript src=\"/jsoa/gtHistory/js/jquery.min.js\"></SCRIPT>\r\n<link href=\"/jsoa/skin/");
            out.print(session.getAttribute("skin"));
            out.write("/style-");
            out.print(session.getAttribute("browserVersion"));
            out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\r\n\r\n<script type=\"text/javascript\">\r\n\r\nvar totalCount=0;//总记录数\r\nvar queryKey=\"\";//查询关键字\r\nvar datas;//列表展示的数据\r\nvar totalRecordNum=0;//总记录数\r\nvar perPageRecordNum = 20;//每页记录条数(js中设置)\r\nvar currentPageNumber = 1;//当前页号\r\n$(document).ready(function(){\r\n\t$.ajax({\r\n\t    type: \"POST\",\r\n\t    contentType:\"application/json\",\r\n\t    url:\"http://172.22.14.240:85/Services/MobileService.asmx/ssologin?jsoncallback=?\",\r\n\t    data:{'alias':'");
            out.print(userAcount);
            out.write("'},\r\n\t    dataType:'jsonp',\r\n\t    success:function(result){            \r\n\t      if(result.ID==\"0\"){\r\n\t    \t  alert(\"查询历史数据出错，未连接成功历史系统\");\r\n\t      }else{\r\n\t    \t var methodKey=\"cwquery\";\r\n\t    \t \r\n\t    \t $.ajax({\r\n\t    \t\t    type: \"POST\",\r\n\t    \t\t    contentType:\"application/json; charset=utf-8\",\r\n\t    \t\t    url:\"http://172.22.14.240:85/Services/MobileService.asmx/\"+methodKey+\"?jsoncallback=?\",\r\n\t    \t\t    data:{'docStateId':100,'startDate':'','endDate':'','docTypeId':'','docpand':'','orderField':'','pagenumber':'0','pagesize':'20'},\r\n\t    \t\t    dataType:'jsonp',\r\n\t    \t\t    success:function(result){ \r\n\t    \t\t    \tvar dataList=dataFormat(result.data);\r\n\t    \t\t    \tdatas=dataList;\r\n\t    \t\t    \tshowDatalist(dataList);//展示列表信息\r\n\t    \t\t    \ttotalCount=result.totalcount;//总记录数\r\n\t    \t\t    \tdocument.getElementById(\"totalnum\").value=totalCount;//总记录数存到隐藏域中\r\n\t    \t\t    \ttotalRecordNum=totalCount;//总记录数赋值给变量\r\n\t    \t\t    \tgetPagenum();//计算总页数\r\n\t    \t\t    \tpageNavigation();//底部页面工具栏的显示\r\n\t    \t\t    \tdocument.getElementById(\"curPageNo\").innerHTML = currentPageNumber;\r\n");
            out.write("\t    \t\t  \t    document.getElementById(\"totalPageNo\").innerHTML = totalPageNumber;\r\n\t    \t\t  \t    pageImg();//页面按钮导航图片\r\n\t    \t\t  \t    showPagenum();//导航页码的展示\r\n\t    \t\t    }\r\n\t    \t\t});\r\n\t      }\r\n\t    }\r\n\t});\r\n\t\r\n//--------------------------------------------------\r\n\t\r\n});\r\n\t\r\nfunction dataFormat(dataList){\r\n\tvar result=[];\r\n\tfor(var i=0;i<dataList.length;i++){\r\n\t\t var temp=dataList[i];\r\n\t\t var tep=[];\r\n\t\t tep[0]=temp.DocCode;\r\n\t\t tep[1]=temp.DocName;\r\n\t\t tep[2]=temp.ReceiveCompanyName;\r\n\t\t tep[3]=temp.ReceiverName;\r\n\t\t tep[4]=temp.ReceiveDate;\r\n\t\t tep[5]=temp.CompleteDate;\r\n\t\t tep[6]=temp.Instanceid;\r\n\t\t result[i]=tep;\r\n }\r\n\t\r\n\treturn result;\r\n}\r\n\r\n//循环展示列表信息\r\nfunction showDatalist(datas){\r\n\tvar index=0;\r\n\tvar listClass=\"listTableLine1\";\r\n\tvar table=document.getElementById(\"gwtable\");\r\n\tvar htmlString=\"\";\r\n\tvar url=\"http://172.22.14.240:85/WorkFlows/InstanceDetail.aspx?InstanceId=\";\r\n\tif(null !=datas && datas.length>0){\r\n\t\tfor(var i=0;i<datas.length;i++){\r\n\t\t\tvar data=datas[i];\r\n\t\t\tindex++;\r\n\t\t\tif(index%2 != 0){\r\n");
            out.write("\t\t \t     listClass=\"listTableLine2\";\r\n\t\t \t}else{\r\n\t\t \t     listClass=\"listTableLine1\";\r\n\t\t \t}\r\n\t\t\tif(data[0]==\"\"){data[0]=\"&nbsp;\"}\r\n\t\t\tif(data[1]==\"\"){data[1]=\"&nbsp;\"}\r\n\t\t\tif(data[2]==\"\"){data[2]=\"&nbsp;\"}\r\n\t\t\tif(data[3]==\"\"){data[3]=\"&nbsp;\"}\r\n\t\t\tif(data[4]==\"\"){data[4]=\"&nbsp;\"}\r\n\t\t\tif(data[5]==\"\"){data[5]=\"&nbsp;\"}\r\n\t\t\t\r\n\t\t\tvar td1=\"<td  class=\"+listClass+ \">\"+(i+1)+\" </td>\";\r\n\t\t\tvar td2=\"<td  class=\"+listClass+\">\" + data[0]+\"</td>\";\r\n\t\t\tvar td3=\"<td  class=\"+listClass+\"><a href=\"+url+data[6]+\" target='_blank'>\"+data[1]+\"</a></td>\";\r\n\t\t\tvar td4=\"<td  class=\"+listClass+\">\"+data[2]+\"</td>\";\r\n\t\t\tvar td5=\"<td  class=\"+listClass+\">\"+data[3]+\"</td>\";\r\n\t\t\tvar td6=\"<td  class=\"+listClass+\">\"+data[4]+\"</td>\";\r\n\t\t\tvar td7=\"<td  class=\"+listClass+\">\"+data[5]+\"</td>\";\r\n\t\t\thtmlString=htmlString+ \"<tr>\"+td1 + td2+td3+td4+td5+td6+td7+\" </tr>\";\r\n\t\t}\r\n\t}\r\n\t\r\n\tif(navigator && navigator.userAgent.match(/msie/i)){ \r\n\t\tvar temp = table.ownerDocument.createElement('div'); \r\n\t\ttemp.innerHTML = '<table><tbody>' + htmlString + '</tbody></table>'; \r\n");
            out.write("\t\tif(table.tBodies.length == 0){ \r\n\t\t\tvar tbody=document.createElement(\"tbody\"); \r\n\t\t\ttable.appendChild(tbody); \r\n\t\t} \r\n\t\ttable.replaceChild(temp.firstChild.firstChild, table.tBodies[0]); \r\n\t} else { \r\n\t\ttable.innerHTML+=htmlString; \r\n\t} \r\n\t\r\n\t\r\n}\r\n//清空表中的数据\r\nfunction delteTableContent(){\r\n\tvar tb=document.getElementById(\"gwtable\");\r\n\tvar rowNum=tb.rows.length;\r\n    for (i=1;i<rowNum;i++) \r\n    {\r\n        tb.deleteRow(i);\r\n        rowNum=rowNum-1;\r\n        i=i-1;\r\n    }\r\n}\r\n</script>\r\n</head>\r\n<body class=\"MainFrameBox\" onKeyDown=\"if(event.keyCode==13) searchSubmit();\" >\r\n\r\n<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"1\" cellspacing=\"1\" class=\"searchbar\" id=\"searchbar\" style=\"display:none\">\r\n   <form name=\"queryform\" id=\"queryform\"  method=\"post\">\r\n\t  <tr>\r\n\t\t  \r\n\t\t  <td align=\"center\">模糊查询：</td>\r\n\t\t  <td>\r\n\t\t\t<input type=\"text\" class=\"inputtext\" name=\"querykey\" id=\"querykey\" />\r\n\t\t  </td>\r\n\t\t  <td>公文状态：</td>\r\n\t\t  <td>\r\n\t\t\t<select class=\"inputtext\" name=\"docstateid\" id=\"docstateid\">\r\n\t\t\t\t<option value=\"0\">拟制</option>\r\n");
            out.write("\t\t\t\t<option value=\"10\">办理中</option>\r\n\t\t\t\t<option value=\"100\" selected = \"selected\">全部</option>\r\n\t\t\t\t<option value=\"20\">办毕</option>\r\n\t\t\t\t<option value=\"30\">归档</option>\r\n\t\t\t\t<option value=\"40\">归卷</option>\r\n\t\t\t\t<option value=\"50\">撤销</option>\r\n\t\t\t\t<option value=\"60\">作废</option>\r\n\t\t\t\t<option value=\"70\">存档</option>\r\n\t\t\t</select>\r\n\t\t  </td>\r\n\t\t  <td>公文类别：</td>\r\n\t\t  <td>\r\n\t\t\t<select class=\"inputtext\" name=\"doctypeid\" id=\"doctypeid\">\r\n\t\t\t\t<option></option>\r\n\t\t\t\t<option value=\"037f68be-d9e2-4548-b1c1-86add1f63c2c\">决定</option>\r\n\t\t\t\t<option value=\"29cf786d-7298-4092-8b21-d2e3cfc38880\">公告</option>\r\n\t\t\t\t<option value=\"2aad39d0-4332-4585-8207-57d1cd35d6c5\">函</option>\r\n\t\t\t\t<option value=\"2f681696-3ed0-4634-a409-51963295c7a6\">通报</option>\r\n\t\t\t\t<option value=\"40b2ed53-2c7a-41a1-a361-b12483a5f9bd\">议案</option>\r\n\t\t\t\t<option value=\"4970fb94-5fb1-41f1-b08f-6c54fc3185f4\">批复</option>\r\n\t\t\t\t<option value=\"4972b910-1a56-4a4b-b6a0-b6437392f0f8\">省国资委收文</option>\r\n\t\t\t\t<option value=\"5372ef63-9b3a-4f92-bf56-f555a0737578\">通告</option>\r\n\t\t\t\t<option value=\"7082c84f-3bed-4335-a867-aa7c2c0d9ed5\">请示</option>\r\n");
            out.write("\t\t\t\t<option value=\"79a016b0-7d10-4143-95d6-3f83eb6c1381\">通知</option>\r\n\t\t\t\t<option value=\"971c916b-d291-442d-ae8e-c1a5c639a941\">意见</option>\r\n\t\t\t\t<option value=\"9de81754-7e92-4e85-aab0-57a854dffd59\">指示</option>\r\n\t\t\t\t<option value=\"ad859e54-5c97-4987-9dec-43d0f2791130\">纪要</option>\r\n\t\t\t\t<option value=\"c01cfbcb-e549-4392-9d67-76a07c6a8b73\">集团内部发文</option>\r\n\t\t\t\t<option value=\"ff26aa8a-c8dd-4cfd-b1de-7f8b7494f8e6\">报告</option>\r\n\t\t\t</select>\r\n\t\t  </td>\r\n\t\t  <td>拟制日期：</td>\r\n\t\t  <td colspan=\"3\">\r\n\t\t\t<script language=javascript>\r\n\t\t\t\tvar dtpDate = createDatePicker(\"queryBeginDate\",");
            out.print(beginYear);
            out.write(',');
            out.print(beginMonth);
            out.write(',');
            out.print(beginDate);
            out.write(");\r\n\t\t\t</script>\r\n\t\t\t至：\r\n\t\t\t<script language=javascript>\r\n\t\t\t\tvar dtpDate = createDatePicker(\"queryEndDate\",");
            out.print(endYear);
            out.write(',');
            out.print(endMonth);
            out.write(',');
            out.print(endDate1);
            out.write(");\r\n\t\t    </script>\r\n\t\t    <input type=\"checkbox\" name=\"queryItem\" id=\"queryItem\" style=\"cursor:pointer\">\r\n\t\t  </td>\r\n\t\t  <td  align=\"right\">\r\n\t\t  <input type=\"button\" class=\"btnButton2Font\" onClick=\"searchSubmit();\" value=\"查询\"/>\r\n\t\t  <input type=\"button\" class=\"btnButton2Font\" onClick=\"searchReset();\" value=\"重置\"/>\r\n\t\t  </td>\r\n\t </tr>\r\n   </form>\r\n</table>\r\n\r\n<table width=\"100%\" height=\"25\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"inlineBottomLine\">  \r\n  <tr>\r\n\t  <td align=\"right\" nowrap>\r\n       \t<input type=\"button\" class=\"btnButton2Font\" id=\"btnButton2FontShow\" onClick=\"showSearch();\" value=\"查询\"/>\r\n      \t<input type=\"button\" class=\"btnButton2Font\" id=\"btnButton2FontClose\" onClick=\"closeSearch();\" style=\"display:none\" value=\"关闭查询\"/>\r\n        </td>\r\n  </tr>\r\n</table>\r\n\r\n<table   id=\"gwtable\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"listTable\">\r\n<thead>\r\n\t<tr>\r\n\t\t\t<td style=\"display:none\"><input type=\"hidden\" id=\"totalnum\" /></td>\r\n\t\t\t<td width=\"5%\"  nowrap class=\"listTableHead\">序号</td>\r\n");
            out.write("\t\t\t<td width=\"20%\" class=\"listTableHead\" id=\"tdWidth\">公文文号</td>\t\t\r\n\t\t\t<td width=\"20%\" class=\"listTableHead\" id=\"tdWidth\">公文标题</td>\t\r\n\t\t\t<td width=\"15%\" class=\"listTableHead\" id=\"tdWidth\">拟制单位</td>\t\t\r\n\t\t\t<td width=\"10%\" class=\"listTableHead\" id=\"tdWidth\">拟制人</td>\r\n\t\t\t<td width=\"10%\" class=\"listTableHead\" id=\"tdWidth\">拟制日期 </td>\t\t\r\n\t\t\t<td width=\"10%\" class=\"listTableHead\" id=\"tdWidth\">办毕日期</td>\r\n\t</tr>\r\n</thead>\r\n</table>\r\n\r\n\r\n<table id=\"pagenavig\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"pagebar\" style=\"display:none\">\r\n\t<tr>\r\n\t\t<td colspan='4' align=\"right\">\r\n\t\t共<span id=\"totalnumspan\"></span>条记录&nbsp;&nbsp;&nbsp;&nbsp;\r\n          第<span id=\"curPageNo\" style=\"color:red\"></span>/<span id=\"totalPageNo\"></span>页&nbsp;&nbsp;\r\n            <a id=\"firsta\" onClick=\"javascript:goToFirstPage();\" href=\"javascript:void(0)\" style=\"cursor:hand;\"><img id=\"firstimg\" src=\"/jsoa/images/p_first.gif\"  border=\"0\" title=\"首页\"></a>\r\n            &nbsp;&nbsp;\r\n            <a id=\"beforea\" onClick=\"javascript:goToBeforePage();\" href=\"javascript:void(0)\" style=\"cursor:hand;\"><img id=\"beforeimg\" src=\"/jsoa/images/p_pre.gif\"  border=\"0\" title=\"上一页\"></a>\r\n");
            out.write("            &nbsp;&nbsp;\r\n            <span id=\"navispan\"></span>&nbsp;&nbsp;\r\n            <a id=\"nexta\" onClick=\"javascript:goToNextPage();\" href=\"javascript:void(0)\" style=\"cursor:hand;\" ><img id=\"nextimg\" src=\"/jsoa/images/p_next.gif\"  border=\"0\" title=\"下一页\"></a>\r\n            &nbsp;&nbsp;\r\n            <a id=\"lasta\" onClick=\"javascript:goToLastPage();\" href=\"javascript:void(0)\" style=\"cursor:hand;\"><img id=\"lastimg\" src=\"/jsoa/images/p_last.gif\"  border=\"0\" title=\"尾页\"></a>\r\n             &nbsp;&nbsp;\r\n           <input type=\"text\" id=\"pageNum\" name=\"pageNum\" size=\"5\"  onkeydown=\"javascript:keyDown(event);\">\r\n           <input type=\"button\" class=\"orangebtn_s\" value=\"Go\" onclick=\"javascript:goToPage();\" />\r\n         \r\n         </td>\r\n\t</tr>\r\n</table>\r\n  \r\n<script type=\"text/javascript\">\r\n\r\n//query:查询参数，num:每页开始的记录数下标，size:每页的大小\r\n  function getrecords(query,doctypeid,docstateid,startDate,endDate,num,size){\r\n\t    var methodKey=\"cwquery\";\r\n\t\tvar datas;//查询出的数据\r\n\t\tvar totalCount;//总记录数\r\n\t\t//将时间格式改成0000-00-00\r\n\t\tvar startdate = getValidDate(startDate);\r\n");
            out.write("\t\tvar enddate = getValidDate(endDate);\r\n\t\t $.ajax({\r\n \t\t    type: \"POST\",\r\n \t\t    contentType:\"application/json; charset=utf-8\",\r\n \t\t    url:\"http://172.22.14.240:85/Services/MobileService.asmx/\"+methodKey+\"?jsoncallback=?\",\r\n \t\t    data:{'docStateId':docstateid ,'docpand':query,'startDate':startdate,'endDate':enddate,'docTypeId':doctypeid,'orderField':'','pagenumber':num,'pagesize':size},\r\n \t\t    dataType:'jsonp',\r\n \t\t    success:function(result){\r\n \t\t    \tif(result.data==undefined){\r\n \t\t    \t\tresult.data=\"\";\r\n \t\t    \t}\r\n \t\t    \tvar dataList=dataFormat(result.data);\r\n \t\t    \t//datas=dataList;\r\n \t\t    \tdelteTableContent();\r\n \t\t    \tshowDatalist(dataList);\r\n \t\t    \ttotalCount=result.totalcount;\r\n \t\t    \tdocument.getElementById(\"totalnum\").value=totalCount;\r\n \t\t    \tgetPagenum();//计算总页数\r\n \t\t    \tif(num==0){\r\n \t\t    \t\tcurrentPageNumber=1;\r\n \t\t    \t}\r\n \t\t    \tpageNavigation();\r\n\t\t    \tdocument.getElementById(\"curPageNo\").innerHTML = currentPageNumber;\r\n\t\t  \t    document.getElementById(\"totalPageNo\").innerHTML = totalPageNumber;//总页码数\r\n");
            out.write("\t\t  \t    pageImg();//页面按钮导航符号\r\n\t\t  \t    showPagenum();\r\n \t\t    }\r\n \t\t});\r\n\t}\r\n\t//判断页码导航符号的显示效果\r\n\tfunction pageImg() {\r\n\t\tvar curnum = document.getElementById(\"curPageNo\").innerHTML;//当前页码\r\n\t\tvar totalnum = document.getElementById(\"totalPageNo\").innerHTML;//总页码\r\n\r\n\t\tif (curnum == 1) {\r\n\t\t\tdocument.getElementById(\"firstimg\").src = \"/jsoa/images/p_first2.gif\";\r\n\t\t\tdocument.getElementById(\"beforeimg\").src = \"/jsoa/images/p_pre2.gif\";\r\n\t\t\tdocument.getElementById(\"firsta\").removeAttribute(\"href\");\r\n\t\t\tdocument.getElementById(\"beforea\").removeAttribute(\"href\");\r\n\t\t} else {\r\n\t\t\tdocument.getElementById(\"firstimg\").src = \"/jsoa/images/p_first.gif\";\r\n\t\t\tdocument.getElementById(\"beforeimg\").src = \"/jsoa/images/p_pre.gif\";\r\n\t\t\tdocument.getElementById(\"firsta\").href = \"javascript:void(0)\";\r\n\t\t\tdocument.getElementById(\"beforea\").href = \"javascript:void(0)\";\r\n\t\t}\r\n\t\tif (curnum == totalnum) {\r\n\t\t\tdocument.getElementById(\"nextimg\").src = \"/jsoa/images/p_next2.gif\";\r\n\t\t\tdocument.getElementById(\"lastimg\").src = \"/jsoa/images/p_last2.gif\";\r\n");
            out.write("\t\t\tdocument.getElementById(\"nexta\").removeAttribute(\"href\");\r\n\t\t\tdocument.getElementById(\"lasta\").removeAttribute(\"href\");\r\n\t\t} else {\r\n\t\t\tdocument.getElementById(\"nextimg\").src = \"/jsoa/images/p_next.gif\";\r\n\t\t\tdocument.getElementById(\"lastimg\").src = \"/jsoa/images/p_last.gif\";\r\n\t\t\tdocument.getElementById(\"nexta\").href = \"javascript:void(0)\";\r\n\t\t\tdocument.getElementById(\"lasta\").href = \"javascript:void(0)\";\r\n\t\t}\r\n\t}\r\n\t//导航页码展示\r\n\tfunction showPagenum() {\r\n\t\tvar curnum = Number(document.getElementById(\"curPageNo\").innerHTML);//当前页码\r\n\t\tvar totalnum = Number(document.getElementById(\"totalPageNo\").innerHTML);//总页码\r\n\t\tvar showResult=showPage(curnum,totalnum,8);\r\n\t\tvar pageSpan = document.getElementById(\"navispan\");\r\n\t\tpageSpan.innerHTML=\"\";\r\n\t\tfor(var i=0;i<showResult.length;i++){\r\n\t\t\tif(showResult[i]==curnum){\r\n\t\t\t\tvar td=\"<font color='red'>\"+curnum+\" </font> &nbsp;\"\r\n\t\t\t}else{\r\n\t\t\t\tvar td = \"<a href='javascript:void(0)' onclick='javascript:pageJump(\"+showResult[i]+\");'>\" + showResult[i]+ \"</a>&nbsp;\";\r\n\t\t\t}\r\n\t\t\tpageSpan.innerHTML += td;\r\n");
            out.write("\t\t}\r\n\t\t\r\n\t}\r\n\r\n\tvar totalPageNumber = 0;//计算总页数\r\n\tvar startShowPage;//开始显示页记录号数\r\n\tvar endShowPage;//结束显示页记录号数\r\n\r\n\t//计算总页码数\r\n\tfunction getPagenum() {\r\n\t\ttotalRecordNum = document.getElementById(\"totalnum\").value\r\n\t\tdocument.getElementById(\"totalnumspan\").innerHTML = totalRecordNum;\r\n\t\ttotalPageNumber = Math.ceil(totalRecordNum / perPageRecordNum);//计算总页数\r\n\t}\r\n\t\r\n\t//当前页码判断\r\n\tfunction judgeCurrentnum() {\r\n\t\tif (totalRecordNum == 0) {\r\n\t\t\tcurrentPageNumber = 0;\r\n\t\t}\r\n\t}\r\n\r\n\t//回车键和点击\"GO\"图片的效果一样\r\n\tfunction keyDown(event) {\r\n\t\tevent = event ? event : window.event;\r\n\t\tif (event.keyCode == 13) {\r\n\t\t\tgoToPage();\r\n\t\t}\r\n\t}\r\n\t//页面导航的显示\r\n\tfunction pageNavigation(){\r\n\t\tif(totalRecordNum>perPageRecordNum){\r\n\t\t\tdocument.getElementById(\"pagenavig\").style.display=\"\";\r\n\t\t}else {\r\n\t\t\tdocument.getElementById(\"pagenavig\").style.display=\"none\";\r\n\t\t}\r\n\t\t\r\n\t}\r\n\t//输入数字跳到指定页\r\n\tfunction goToPage() {\r\n\t\tif (totalRecordNum != 0) {\r\n\t\t\tvar pageNo = document.getElementById(\"pageNum\").value;\r\n\t\t\t//校验页号\r\n\t\t\tif ((/[^0-9]/gi.test(pageNo)) | pageNo.length == 0) {\r\n");
            out.write("\t\t\t\talert(\"页数必须为数字!\");\r\n\t\t\t\treturn;\r\n\t\t\t} else {\r\n\t\t\t\tif ((pageNo > totalPageNumber) | (pageNo < 1)) {\r\n\t\t\t\t\talert(\"你所输入的页数超出范围！\");\r\n\t\t\t\t\treturn;\r\n\t\t\t\t}\r\n\t\t\t}\r\n\t\t\t//跳转到指定页\r\n\t\t\tstartShowPage = (pageNo - 1) * perPageRecordNum;\r\n\t\t\tendShowPage = pageNo * perPageRecordNum;\r\n\t\t\tvar querykey = document.getElementById(\"querykey\").value;//获取模糊查询的关键字\r\n\t\t\tvar docstateid = document.getElementById(\"docstateid\").value;//获取状态\r\n\t\t\tvar doctypeid = document.getElementById(\"doctypeid\").value;\r\n\t\t\tvar startDate = \"\";\r\n\t\t\tvar endDate = \"\";\r\n\t\t\tif(document.getElementById(\"queryItem\").checked){\r\n\t\t\t\tstartDate = document.getElementById(\"queryBeginDate\").value;\r\n\t\t\t\tendDate = document.getElementById(\"queryEndDate\").value;\r\n\t\t\t}\r\n\t\t\tgetrecords(querykey, doctypeid, docstateid, startDate, endDate, startShowPage, perPageRecordNum);\r\n\t\t\tcurrentPageNumber = pageNo;\r\n\t\t\tdocument.getElementById(\"totalnumspan\").innerHTML = totalRecordNum;\r\n\t\t\tdocument.getElementById(\"curPageNo\").innerHTML = currentPageNumber;\r\n\t\t\tdocument.getElementById(\"totalPageNo\").innerHTML = totalPageNumber;\r\n");
            out.write("\t\t\tdocument.getElementById(\"pageNum\").value = \"\";\r\n\t\t\tpageImg();//导航符号的显示\r\n\t\t}\r\n\t}\r\n\t//点击导航页码跳转\r\n\tfunction pageJump(pageNo){\r\n\t\t\r\n\t\tstartShowPage = (pageNo - 1) * perPageRecordNum;\r\n\t\tendShowPage = pageNo * perPageRecordNum;\r\n\t\tvar querykey = document.getElementById(\"querykey\").value;//获取模糊查询的关键字\r\n\t\tvar docstateid = document.getElementById(\"docstateid\").value;//获取状态\r\n\t\tvar doctypeid = document.getElementById(\"doctypeid\").value;\r\n\t\tvar startDate = \"\";\r\n\t\tvar endDate = \"\";\r\n\t\tif(document.getElementById(\"queryItem\").checked){\r\n\t\t\tstartDate = document.getElementById(\"queryBeginDate\").value;\r\n\t\t\tendDate = document.getElementById(\"queryEndDate\").value;\r\n\t\t}\r\n\t\tgetrecords(querykey, doctypeid, docstateid, startDate, endDate, startShowPage, perPageRecordNum);\r\n\t\tcurrentPageNumber = pageNo;\r\n\t\tdocument.getElementById(\"totalnumspan\").innerHTML = totalRecordNum;\r\n\t\tdocument.getElementById(\"curPageNo\").innerHTML = currentPageNumber;\r\n\t\tdocument.getElementById(\"totalPageNo\").innerHTML = totalPageNumber;\r\n\t\tdocument.getElementById(\"pageNum\").value = \"\";\r\n");
            out.write("\t\tpageImg();//导航符号的显示\r\n\t}\r\n\t//跳转到第一页\r\n\tfunction goToFirstPage() {\r\n\t\t//getPagenum();//获取页码数\r\n\t\tif (totalRecordNum != 0) {\r\n\t\t\tstartShowPage = 0 * perPageRecordNum;\r\n\t\t\tendShowPage = 1 * perPageRecordNum;\r\n\t\t\tcurrentPageNumber = 1;\r\n\t\t\tvar querykey = document.getElementById(\"querykey\").value;//获取模糊查询的关键字\r\n\t\t\tvar docstateid = document.getElementById(\"docstateid\").value;//获取状态\r\n\t\t\tvar doctypeid = document.getElementById(\"doctypeid\").value;\r\n\t\t\tvar startDate = \"\";\r\n\t\t\tvar endDate = \"\";\r\n\t\t\tif(document.getElementById(\"queryItem\").checked){\r\n\t\t\t\tstartDate = document.getElementById(\"queryBeginDate\").value;\r\n\t\t\t\tendDate = document.getElementById(\"queryEndDate\").value;\r\n\t\t\t}\r\n\t\t\tgetrecords(querykey, doctypeid, docstateid, startDate, endDate, startShowPage, perPageRecordNum);\r\n\r\n\t\t\tdocument.getElementById(\"totalnumspan\").innerHTML = totalRecordNum;\r\n\t\t\tdocument.getElementById(\"curPageNo\").innerHTML = currentPageNumber;\r\n\t\t\tdocument.getElementById(\"totalPageNo\").innerHTML = totalPageNumber;\r\n\t\t\tpageImg();\r\n\t\t}\r\n\t}\r\n\t\r\n\t//跳转到最后一页\r\n");
            out.write("\tfunction goToLastPage() {\r\n\t\tif (totalRecordNum != 0) {\r\n\t\t\tif (totalRecordNum % perPageRecordNum == 0)//除尽\r\n\t\t\t{\r\n\t\t\t\tvar tempVal = totalRecordNum / perPageRecordNum;\r\n\t\t\t\tstartShowPage = (tempVal - 1) * perPageRecordNum;\r\n\t\t\t\tendShowPage = tempVal * perPageRecordNum;\r\n\t\t\t} else {\r\n\t\t\t\tvar tempVal = totalRecordNum % perPageRecordNum;//得到余数,就是最后一页要显示的记录条数\r\n\t\t\t\tstartShowPage = totalRecordNum - tempVal;\r\n\t\t\t\tendShowPage = totalRecordNum;\r\n\t\t\t}\r\n\t\t\tcurrentPageNumber = totalPageNumber;\r\n\t\t\t//exeShowPage();\r\n\t\t\tvar querykey = document.getElementById(\"querykey\").value;//获取模糊查询的关键字\r\n\t\t\tvar docstateid = document.getElementById(\"docstateid\").value;//获取状态\r\n\t\t\tvar doctypeid = document.getElementById(\"doctypeid\").value;\r\n\t\t\tvar startDate = \"\";\r\n\t\t\tvar endDate = \"\";\r\n\t\t\tif(document.getElementById(\"queryItem\").checked){\r\n\t\t\t\tstartDate = document.getElementById(\"queryBeginDate\").value;\r\n\t\t\t\tendDate = document.getElementById(\"queryEndDate\").value;\r\n\t\t\t}\r\n\t\t\tgetrecords(querykey, doctypeid, docstateid, startDate, endDate, startShowPage, perPageRecordNum);\r\n");
            out.write("\r\n\t\t\tdocument.getElementById(\"totalnumspan\").innerHTML = totalRecordNum;\r\n\t\t\tdocument.getElementById(\"curPageNo\").innerHTML = currentPageNumber;\r\n\t\t\tdocument.getElementById(\"totalPageNo\").innerHTML = totalPageNumber;\r\n\t\t\tpageImg();\r\n\t\t}\r\n\t}\r\n\t\r\n\t//跳转到下一页\r\n\tfunction goToNextPage() {\r\n\t\t//getPagenum();//获取页码数\r\n\t\tif (totalRecordNum != 0) {\r\n\t\t\tif (currentPageNumber < totalPageNumber) {\r\n\t\t\t\tcurrentPageNumber = Number(currentPageNumber) + 1;\r\n\t\t\t\tstartShowPage = (currentPageNumber - 1) * perPageRecordNum;\r\n\t\t\t\tendShowPage = currentPageNumber * perPageRecordNum;\r\n\t\t\t\t//exeShowPage();\r\n\t\t\t\tvar querykey = document.getElementById(\"querykey\").value;//获取模糊查询的关键字\r\n\t\t\t\tvar docstateid = document.getElementById(\"docstateid\").value;//获取状态\r\n\t\t\t\tvar doctypeid = document.getElementById(\"doctypeid\").value;\r\n\t\t\t\tvar startDate = \"\";\r\n\t\t\t\tvar endDate = \"\";\r\n\t\t\t\tif(document.getElementById(\"queryItem\").checked){\r\n\t\t\t\t\tstartDate = document.getElementById(\"queryBeginDate\").value;\r\n\t\t\t\t\tendDate = document.getElementById(\"queryEndDate\").value;\r\n");
            out.write("\t\t\t\t}\r\n\t\t\t\tgetrecords(querykey, doctypeid, docstateid, startDate, endDate, startShowPage, perPageRecordNum);\r\n\r\n\t\t\t\tdocument.getElementById(\"totalnumspan\").innerHTML = totalRecordNum;\r\n\t\t\t\tdocument.getElementById(\"curPageNo\").innerHTML = currentPageNumber;\r\n\t\t\t\tdocument.getElementById(\"totalPageNo\").innerHTML = totalPageNumber;\r\n\t\t\t\tpageImg();\r\n\t\t\t}\r\n\t\t}\r\n\t}\r\n\t\r\n\t//跳转到前一页\r\n\tfunction goToBeforePage() {\r\n\t\tif (totalRecordNum != 0) {\r\n\t\t\tif (currentPageNumber > 1) {\r\n\t\t\t\tcurrentPageNumber = currentPageNumber - 1;\r\n\t\t\t\tstartShowPage = (currentPageNumber - 1) * perPageRecordNum;\r\n\t\t\t\tendShowPage = currentPageNumber * perPageRecordNum;\r\n\t\t\t\t// exeShowPage();\r\n\t\t\t\tvar querykey = document.getElementById(\"querykey\").value;//获取模糊查询的关键字\r\n\t\t\t\tvar docstateid = document.getElementById(\"docstateid\").value;//获取状态\r\n\t\t\t\tvar doctypeid = document.getElementById(\"doctypeid\").value;\r\n\t\t\t\tvar startDate = \"\";\r\n\t\t\t\tvar endDate = \"\";\r\n\t\t\t\tif(document.getElementById(\"queryItem\").checked){\r\n\t\t\t\t\tstartDate = document.getElementById(\"queryBeginDate\").value;\r\n");
            out.write("\t\t\t\t\tendDate = document.getElementById(\"queryEndDate\").value;\r\n\t\t\t\t}\r\n\t\t\t\r\n\t\t\t\tgetrecords(querykey, doctypeid, docstateid, startDate, endDate, startShowPage, perPageRecordNum);\r\n\t\t\t\tdocument.getElementById(\"totalnumspan\").innerHTML = totalRecordNum;\r\n\t\t\t\tdocument.getElementById(\"curPageNo\").innerHTML = currentPageNumber;\r\n\t\t\t\tdocument.getElementById(\"totalPageNo\").innerHTML = totalPageNumber;\r\n\t\t\t\tpageImg();\r\n\t\t\t}\r\n\t\t}\r\n\t}\r\n\t\r\n\t//返回链接页码数（数组）\r\n\tfunction showPage(curPage,allPage,showNum){\r\n\t\tvar showInt = [];\r\n\t\tif(allPage<=showNum){\r\n\t\t\tfor(var i=1;i<=allPage;i++){\r\n\t\t\t\tshowInt[i-1]=i;\r\n\t\t\t}\r\n\t\t}else{\r\n\t\t\tvar halfNum = showNum/2;\r\n\t\t\tfor(var i=1;i<=showNum;i++){\r\n\t\t\t\tif(curPage<=halfNum){\r\n\t\t\t\t\tshowInt[i-1]=i;\r\n\t\t\t\t}else if(curPage>=(allPage-halfNum)){\r\n\t\t\t\t\tshowInt[i-1]=allPage-showNum+i;\r\n\t\t\t\t}else{\r\n\t\t\t\t\tshowInt[i-1]=curPage-halfNum+i;\r\n\t\t\t\t}\r\n\t\t\t}\r\n\t\t}\r\n\t\treturn showInt;\r\n\t}\r\n\t\r\n</script>\r\n <script language=\"javascript\">\r\n\r\n\r\n\t\tfunction showSearch() {\r\n\t\t\tdocument.getElementById(\"searchbar\").style.display = \"\";\r\n");
            out.write("\t\t\tdocument.getElementById(\"btnButton2FontShow\").style.display = \"none\";\r\n\t\t\tdocument.getElementById(\"btnButton2FontClose\").style.display = \"\";\r\n\t\t}\r\n\t\tfunction closeSearch() {\r\n\t\t\tdocument.getElementById(\"searchbar\").style.display = \"none\";\r\n\t\t\tdocument.getElementById(\"btnButton2FontShow\").style.display = \"\";\r\n\t\t\tdocument.getElementById(\"btnButton2FontClose\").style.display = \"none\";\r\n\t\t}\r\n\r\n\t\tfunction setEnter() {\r\n\t\t\tif (document.getElementById(\"searchbar\").style.display == \"\") {\r\n\t\t\t\tsearchSubmit();\r\n\t\t\t}\r\n\t\t}\r\n\t\tfunction searchSubmit() {\r\n\t\t\tvar querykey = document.getElementById(\"querykey\").value;\r\n\t\t\tvar docstateid = document.getElementById(\"docstateid\").value;//获取状态\r\n\t\t\tvar doctypeid = document.getElementById(\"doctypeid\").value;\r\n\t\t\tvar startDate = \"\";\r\n\t\t\tvar endDate = \"\";\r\n\t\t\tif(document.getElementById(\"queryItem\").checked){\r\n\t\t\t\tstartDate = document.getElementById(\"queryBeginDate\").value;\r\n\t\t\t\tendDate = document.getElementById(\"queryEndDate\").value;\r\n\t\t\t}\r\n\t\t\tgetrecords(querykey, doctypeid, docstateid, startDate, endDate, 0, perPageRecordNum);\r\n");
            out.write("\t\t}\r\n\r\n\t\tfunction searchReset() {\r\n\t\t\tdocument.getElementById(\"querykey\").value = \"\";\r\n\t\t\tdocument.getElementById(\"docstateid\").value = \"100\";\r\n\t\t\tdocument.getElementById(\"doctypeid\").value = \"\";\r\n\t\t}\r\n\t</script> \r\n</body>\r\n");
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
