/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:43:29 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.kq;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class kq_005fdutyset_005fadd_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html; charset=GBK");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n<html>\r\n<head>\r\n<title>添加排班</title>\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"js/js.js\" language=\"javascript\"></script>\r\n<link rel=stylesheet type=\"text/css\" href=\"/jsoa/public/date_picker/DateObject2.css\">\r\n<script language=\"javascript\" src=\"/jsoa/js/checkForm.js\"></script>\r\n<script language=\"javascript\" src=\"/jsoa/js/openEndow.js\"></script>\r\n<script src=\"/jsoa/js/inputSelect.js\" language=\"javascript\"></script>\r\n");


response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

String grantRange=session.getAttribute("grantRange").toString();
if("".equals(grantRange)){
	grantRange="*0*";
}
if(com.js.util.config.SystemCommon.getMultiDepart()==1){
	grantRange="*"+session.getAttribute("corpId")+"*";
}

String saveType=request.getAttribute("saveType")+"";

String showTR="";
if("shenzhougaotie".equals(new com.js.util.config.SystemCommon().getCustomerName())){
	showTR="none";
}

      out.write("\r\n<script language=\"javascript\">\r\n");
if("saveandexit".equals(saveType)){
      out.write("\r\n//此处如果使用reload，在删除后，添加时，会刷新删除，导致错误\r\nopener.location.href = \"KqDutySetAction.do?action=list\";\r\nwindow.close();\r\n");
}
      out.write('\r');
      out.write('\n');
if("saveandcontinue".equals(saveType)){
      out.write("\r\nopener.location.reload();\r\n");
}
      out.write("\r\n\r\nvar selectArrayH=new Array();\r\nfor(var i=0;i<24;i++){\r\n   selectArrayH[i]=new Array();\r\n   selectArrayH[i][0]=\"\"+i;\r\n   selectArrayH[i][1]=\"\"+i;\r\n}\r\nvar selectArrayM=new Array();\r\nfor(var i=0;i<60;i++){\r\n   selectArrayM[i]=new Array();\r\n   if(i<10){\r\n       selectArrayM[i][0]=\"0\"+i;\r\n       selectArrayM[i][1]=\"0\"+i;\r\n   }else{\r\n\t   selectArrayM[i][0]=\"\"+i;\r\n\t   selectArrayM[i][1]=\"\"+i;\r\n   }\r\n}\r\n</script>\r\n</head>\r\n<body class=\"MainFrameBox Pupwin\"  >\r\n<form action=\"/jsoa/KqDutySetAction.do?action=save\"  name=\"form1\" method=\"post\" >\r\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"docBoxNoPanel\" style=\"padding-left:10px; padding-top:10px;\">\r\n     <tr>\r\n\t   <td width=\"80\" ><label class=\"mustFillcolor\" style=\"width:7px;\" > *</label>排班名称：</td>\r\n\t   <td colspan=\"2\"><input style=\"width:85%\"   type=\"text\" name=\"dutyName\"  class=\"inputText\" id=\"dutyName\"    /></td>\r\n\t   <td>&nbsp;工时：<input type=\"text\" name=\"dutyHour\" style=\"width:40px;\" value=\"1.0\" />&nbsp;天</td>\r\n    </tr>\r\n    <tr>\r\n\t   <td width=\"8%\" ><label class=\"mustFillcolor\" style=\"width:7px;\" > </label>第一次：</td>\r\n");
      out.write("\t   <td width=\"42%\" nowrap><div style=\"display:inline;float:left;\"><select name=\"dutyType1\" disabled  >\r\n\t       <option value=\"1\" selected >上班</option>\r\n\t       </select></div>\r\n\t       <input type=\"hidden\" name=\"dutyTime1\" id=\"dutyTime1\" value=\"0\">\r\n\t       <span><script type=\"text/javascript\">\r\n             \tdocument.write(inputSelect(\"20\",\"dutyTime1H\",\"00\",selectArrayH,\"setTimeShow('dutyTime1','1',24,this);\",\"2\",\"：\"));\r\n           </script></span>\r\n\t       <span><script type=\"text/javascript\">\r\n             inputSelect(\"20\",\"dutyTime1M\",\"00\",selectArrayM,\"setTimeShow('dutyTime1','1',60,this)\",\"2\",\"\",\"\");\r\n            </script></span>\r\n\t       \r\n\t       <span id=\"ifPunch1\" style=\"display:none;\"><input type=\"checkbox\" value=\"1\" onclick=\"isPunch(this);\" />必须打卡</span>\r\n\t       <input type=\"hidden\" name=\"ifPunch\" value=\"0\" />\r\n\t       <span id=\"minNum1\" style=\"display:none;\">&nbsp;\r\n\t       前<input type=\"text\" style=\"width:30px;\" name=\"minNum\" value=\"60\" >分钟,\r\n\t       后<input type=\"text\" style=\"width:30px;\" name=\"minNumDown\" value=\"60\" >分钟内打卡有效</span>\r\n");
      out.write("\t      </td>\r\n\t   <td width=\"8%\" align=\"right\" >第二次：</td>\r\n\t   <td width=\"42%\" nowrap><div style=\"display:inline;float:left;\"><select name=\"dutyType2\" disabled  >\r\n\t       <option value=\"2\" selected >下班</option>\r\n\t       </select></div>\r\n\t       <input type=\"hidden\" name=\"dutyTime2\" id=\"dutyTime2\" value=\"0\">\r\n\t       <span><script type=\"text/javascript\">\r\n             \tdocument.write(inputSelect(\"20\",\"dutyTime2H\",\"00\",selectArrayH,\"setTimeShow('dutyTime2','2',24,this);\",\"2\",\"：\"));\r\n           </script></span>\r\n\t       <span><script type=\"text/javascript\">\r\n             inputSelect(\"20\",\"dutyTime2M\",\"00\",selectArrayM,\"setTimeShow('dutyTime2','2',60,this)\",\"2\",\"\",\"\");\r\n            </script></span>\r\n\t       <span id=\"ifPunch2\" style=\"display:none;\"><input type=\"checkbox\" value=\"2\" onclick=\"isPunch(this);\" />必须打卡</span>\r\n\t       <input type=\"hidden\" name=\"ifPunch\" value=\"0\" />\r\n\t       <span id=\"minNum2\" style=\"display:none;\">&nbsp;\r\n\t       前<input type=\"text\" style=\"width:30px;\" name=\"minNumDown\" value=\"60\" >分钟,\r\n");
      out.write("\t       后<input type=\"text\" style=\"width:30px;\" name=\"minNum\" value=\"60\" >分钟内打卡有效</span>\r\n\t       </td>\r\n    </tr>\r\n       <tr style=\"display:");
      out.print(showTR);
      out.write("\">\r\n\t   <td width=\"8%\" ><label class=\"mustFillcolor\" style=\"width:7px;\" ></label>第三次：</td>\r\n\t   <td width=\"42%\" nowrap><div style=\"display:inline;float:left;\"><select name=\"dutyType3\" disabled  >\r\n\t       <option value=\"1\" selected >上班</option>\r\n\t       </select></div>\r\n\t       <input type=\"hidden\" name=\"dutyTime3\" id=\"dutyTime3\" value=\"0\">\r\n\t       <span><script type=\"text/javascript\">\r\n             \tdocument.write(inputSelect(\"20\",\"dutyTime3H\",\"00\",selectArrayH,\"setTimeShow('dutyTime3','3',24,this);\",\"2\",\"：\"));\r\n           </script></span>\r\n\t       <span><script type=\"text/javascript\">\r\n             inputSelect(\"20\",\"dutyTime3M\",\"00\",selectArrayM,\"setTimeShow('dutyTime1','3',60,this)\",\"2\",\"\",\"\");\r\n            </script></span>\r\n\t       <span id=\"ifPunch3\" style=\"display:none;\"><input type=\"checkbox\" value=\"3\" onclick=\"isPunch(this);\" />必须打卡</span>\r\n\t       <input type=\"hidden\" name=\"ifPunch\" value=\"0\" />\r\n\t       <span id=\"minNum3\" style=\"display:none;\">&nbsp;\r\n\t      前<input type=\"text\" style=\"width:30px;\" name=\"minNum\" value=\"60\" >分钟,\r\n");
      out.write("\t      后<input type=\"text\" style=\"width:30px;\" name=\"minNumDown\" value=\"60\" >分钟内打卡有效</span>\r\n\t       </td>\r\n\t   <td width=\"8%\" align=\"right\" >第四次：</td>\r\n\t   <td width=\"42%\" nowrap><div style=\"display:inline;float:left;\"><select name=\"dutyType4\" disabled  >\r\n\t       <option value=\"2\" selected >下班</option>\r\n\t       </select></div>\r\n\t       <input type=\"hidden\" name=\"dutyTime4\" id=\"dutyTime4\" value=\"0\">\r\n\t       <span><script type=\"text/javascript\">\r\n             \tdocument.write(inputSelect(\"20\",\"dutyTime4H\",\"00\",selectArrayH,\"setTimeShow('dutyTime4','4',24,this);\",\"2\",\"：\"));\r\n           </script></span>\r\n\t       <span><script type=\"text/javascript\">\r\n             inputSelect(\"20\",\"dutyTime4M\",\"00\",selectArrayM,\"setTimeShow('dutyTime4','4',60,this)\",\"2\",\"\",\"\");\r\n            </script></span>\r\n\t       <span id=\"ifPunch4\" style=\"display:none;\"><input type=\"checkbox\" value=\"4\" onclick=\"isPunch(this);\" />必须打卡</span>\r\n\t       <input type=\"hidden\" name=\"ifPunch\" value=\"0\" />\r\n\t       <span id=\"minNum4\" style=\"display:none;\">&nbsp;\r\n");
      out.write("\t       前<input type=\"text\" style=\"width:30px;\" name=\"minNumDown\" value=\"60\" >分钟,\r\n\t       后<input type=\"text\" style=\"width:30px;\" name=\"minNum\" value=\"60\" >分钟内打卡有效</span>\r\n\t       </td>\t       \r\n    </tr>  \r\n         <tr style=\"display:");
      out.print(showTR);
      out.write("\">\r\n\t   <td width=\"8%\" ><label class=\"mustFillcolor\" style=\"width:7px;\" ></label>第五次：</td>\r\n\t   <td width=\"42%\" nowrap><div style=\"display:inline;float:left;\"><select name=\"dutyType5\" disabled >\r\n\t       <option value=\"1\" selected >上班</option>\r\n\t       </select></div>\r\n\t       <input type=\"hidden\" name=\"dutyTime5\" id=\"dutyTime5\" value=\"0\">\r\n\t       <span><script type=\"text/javascript\">\r\n             \tdocument.write(inputSelect(\"20\",\"dutyTime5H\",\"00\",selectArrayH,\"setTimeShow('dutyTime5','5',24,this);\",\"2\",\"：\"));\r\n           </script></span>\r\n\t       <span><script type=\"text/javascript\">\r\n             inputSelect(\"20\",\"dutyTime5M\",\"00\",selectArrayM,\"setTimeShow('dutyTime5','5',60,this)\",\"2\",\"\",\"\");\r\n            </script></span>\r\n\t       <span id=\"ifPunch5\" style=\"display:none;\"><input type=\"checkbox\" value=\"5\" onclick=\"isPunch(this);\" />必须打卡</span>\r\n\t       <input type=\"hidden\" name=\"ifPunch\" value=\"0\" />\r\n\t       <span id=\"minNum5\" style=\"display:none;\">&nbsp;\r\n\t       前<input type=\"text\" style=\"width:30px;\" name=\"minNum\" value=\"60\" >分钟,\r\n");
      out.write("\t       后<input type=\"text\" style=\"width:30px;\" name=\"minNumDown\" value=\"60\" >分钟内打卡有效 </span>\r\n\t       </td>\r\n\t   <td width=\"8%\" align=\"right\" >第六次：</td>\r\n\t   <td width=\"42%\" nowrap><div style=\"display:inline;float:left;\"><select name=\"dutyType6\" disabled  >\r\n\t       <option value=\"2\" selected >下班</option>\r\n\t       </select></div>\r\n\t       <input type=\"hidden\" name=\"dutyTime6\" id=\"dutyTime6\" value=\"0\">\r\n\t       <span><script type=\"text/javascript\">\r\n             \tdocument.write(inputSelect(\"20\",\"dutyTime6H\",\"00\",selectArrayH,\"setTimeShow('dutyTime6','6',24,this);\",\"2\",\"：\"));\r\n           </script></span>\r\n\t       <span><script type=\"text/javascript\">\r\n             inputSelect(\"20\",\"dutyTime6M\",\"00\",selectArrayM,\"setTimeShow('dutyTime6','6',60,this)\",\"2\",\"\",\"\");\r\n            </script></span>\r\n\t       <span id=\"ifPunch6\" style=\"display:none;\"><input type=\"checkbox\" value=\"6\" onclick=\"isPunch(this);\" />必须打卡</span>\r\n\t       <input type=\"hidden\" name=\"ifPunch\" value=\"0\" />\r\n\t       <span id=\"minNum6\" style=\"display:none;\">&nbsp;\r\n");
      out.write("\t       前<input type=\"text\" style=\"width:30px;\" name=\"minNumDown\" value=\"60\" >分钟,\r\n\t       后<input type=\"text\" style=\"width:30px;\" name=\"minNum\" value=\"60\" >分钟内打卡有效</span>\r\n\t       </td>\r\n    </tr>  \r\n\t<tr>\r\n\t <td valign=\"top\"><label class=\"mustFillcolor\" style=\"width:7px;\" > </label>适用人员：</td>\r\n     <td colspan=\"3\">\r\n  \t\t  <input type=\"hidden\" id=\"grantRange\" name=\"grantRange\" value=\"");
      out.print(grantRange );
      out.write("\">\r\n\t\t  <textarea name=\"userName\" rows=\"6\" style=\"width:85%;cursor:pointer;\" class=\"inputtextarea\" readonly=\"true\" onClick=\"openEndow('userId','userName',form1.userId.value,form1.userName.value,'userorg','no','userorg',document.getElementById('grantRange').value)\" title=\"请点击选择\" /></textarea>\r\n\t\t  <input type=\"hidden\" value=\"\" name=\"userId\" >\r\n     </td>\r\n   </tr>\r\n    <tr>\r\n\t   <td width=\"80\" ><label class=\"mustFillcolor\" style=\"width:7px;\" ></label>工作日：</td>\r\n\t   <td colspan=\"3\"><input type=\"checkbox\"  value=\"1\" name=\"workday1\"  />星期日&nbsp;&nbsp;\r\n\t   <input  type=\"checkbox\"  value=\"1\"   name=\"workday2\"  />星期一&nbsp;&nbsp;\r\n\t   <input  type=\"checkbox\"  value=\"1\"   name=\"workday3\"  />星期二&nbsp;&nbsp;\r\n\t   <input  type=\"checkbox\"  value=\"1\"   name=\"workday4\"  />星期三&nbsp;&nbsp;\r\n\t   <input  type=\"checkbox\"  value=\"1\"   name=\"workday5\"  />星期四&nbsp;&nbsp;\r\n\t   <input  type=\"checkbox\"  value=\"1\"   name=\"workday6\"  />星期五&nbsp;&nbsp;\r\n\t   <input  type=\"checkbox\"  value=\"1\"   name=\"workday7\"  />星期六\r\n\t   </td>\r\n    </tr>\r\n");
      out.write("</table>\r\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"40px\">\r\n<tr>\r\n   <td colspan=\"2\">\r\n\t<input type=\"button\" class=\"btnButton4font\" style=\"cursor:pointer\" id=\"saveAndExit\" onClick=\"javascript:save('saveandexit');\" value=\"保存退出\"/>\r\n\t<input type=\"button\" class=\"btnButton4font\" style=\"cursor:pointer\"id=\"saveAndContinue\" onClick=\"javascript:save('saveandcontinue');\" value=\"保存继续\"/>\r\n\t<input type=\"button\" class=\"btnButton2font\" style=\"cursor:pointer\" onClick=\"javascript:document.form1.reset();\" value=\"重置\"/>\r\n\t<input type=\"button\" class=\"btnButton2font\" style=\"cursor:pointer\" onClick=\"javascript:window.close();\" value=\"退出\"/>\r\n  </td>\r\n</tr>\r\n</table>\r\n</form>\r\n\r\n\r\n\r\n<script language=\"javascript\">\r\nfunction setTimeShow(flag,punchFlag,num,obj){\r\n\tif(parseInt(obj.value)>num){\r\n\t\tobj.value = num-1;\r\n\t}\r\n\tvar timeStr = document.getElementById(flag+\"H\").value+\":\"+document.getElementById(flag+\"M\").value;\r\n\tif(\"00:00\"==timeStr){\r\n\t\tdocument.getElementById(flag).value=\"0\";\r\n\t\tselectPunch(punchFlag,\"0\");\r\n");
      out.write("\t}else{\r\n\t\tdocument.getElementById(flag).value=timeStr;\r\n\t\tselectPunch(punchFlag,timeStr);\r\n\t}\r\n}\r\n\r\nfunction selectPunch(flag,svalue){\r\n\t//alert(flag + \"  \"+obj.value);\r\n\tif(svalue==\"0\"){\r\n\t\tvar num = parseInt(flag);\r\n\t\tvar ifPunch = document.getElementsByName(\"ifPunch\");\r\n\t\tvar minNum = document.getElementsByName(\"minNum\");\r\n\t\tvar minNumDown = document.getElementsByName(\"minNumDown\");\r\n\t\tdocument.getElementById(\"ifPunch\"+flag).checked=\"false\";\r\n\t\tdocument.getElementById(\"ifPunch\"+flag).style.display=\"none\";\r\n\t\tdocument.getElementById(\"minNum\"+flag).style.display=\"none\";\r\n\t\tifPunch[num-1].value=\"0\";\r\n\t\tminNum[num-1].value=\"60\";\r\n\t\tminNumDown[num-1].value=\"60\";\r\n\t}else{\r\n\t\tdocument.getElementById(\"ifPunch\"+flag).style.display=\"\";\r\n\t}\r\n}\r\nfunction isPunch(obj){\r\n\tvar ifPunch = document.getElementsByName(\"ifPunch\");\r\n\tvar num = parseInt(obj.value);\r\n\tif(obj.checked){\r\n\t\t//alert(\"选中\"+num);\r\n\t\tdocument.getElementById(\"minNum\"+num).style.display=\"\";\r\n\t\tifPunch[num-1].value=\"1\";\r\n\t}else{\r\n\t\t//alert(\"未选中\"+obj.value);\r\n");
      out.write("\t\tdocument.getElementById(\"minNum\"+num).style.display=\"none\";\r\n\t\tifPunch[num-1].value=\"0\";\r\n\t\tvar minNum = document.getElementsByName(\"minNum\");\r\n\t\tminNum[num-1].value=\"60\";\r\n\t\tvar minNumDown = document.getElementsByName(\"minNumDown\");\r\n\t\tminNumDown[num-1].value=\"60\";\r\n\t}\r\n}\r\n\r\n\r\n\r\nfunction save(tag){\r\n\tif(document.getElementById(\"dutyName\").value==\"\"){\r\n\t\talert(\"排班名称不能为空！\");\r\n\t\treturn;\r\n\t}\r\n\tvar time1=document.all.dutyTime1.value==\"0\"?\"23:51\":document.all.dutyTime1.value;\r\n\tvar date1=new Date(\"2009/12/10  \"+time1);\r\n\tvar time2=document.all.dutyTime2.value==\"0\"?\"23:52\":document.all.dutyTime2.value;\r\n\tvar date2=new Date(\"2009/12/10  \"+time2);\r\n\tvar time3=document.all.dutyTime3.value==\"0\"?\"23:53\":document.all.dutyTime3.value;\r\n\tvar date3=new Date(\"2009/12/10  \"+time3);\r\n\tvar time4=document.all.dutyTime4.value==\"0\"?\"23:54\":document.all.dutyTime4.value;\r\n\tvar date4=new Date(\"2009/12/10  \"+time4);\r\n\tvar time5=document.all.dutyTime5.value==\"0\"?\"23:55\":document.all.dutyTime5.value;\r\n\tvar date5=new Date(\"2009/12/10  \"+time5);\r\n");
      out.write("\tvar time6=document.all.dutyTime6.value==\"0\"?\"23:56\":document.all.dutyTime6.value;\r\n\tvar date6=new Date(\"2009/12/10  \"+time6);\r\n    if(date1>date2||date2>date3||date3>date4||date4>date5||date5>date6){\r\n  \t    alert(\"请按时间顺序排班\");\r\n        return;\r\n    }\r\n\tif(document.all.userId.value==\"\"){\r\n\t\tif(confirm(\"使用人员未填写，如果继续操作，此排班将作为假期排班。\\n您确定继续操作吗？\")){\r\n\t\t\tdocument.all.userName.value=\"\";\r\n\t\t}else{\r\n\t\t    alert(\"请选择使用人员！\");\r\n\t\t    return;\r\n\t\t}\r\n\t}\r\n\tform1.action=\"KqDutySetAction.do?action=save&saveType=\"+tag;\r\n\tsetButtonDisabled(\"saveAndExit\",true);\r\nsetButtonDisabled(\"saveAndContinue\",true);\r\nsetTimeout(\"fobbidenBtn()\",8000);\r\n\tdocument.form1.submit();\r\n}\r\nfunction fobbidenBtn(){\r\n   \tsetButtonDisabled('saveAndExit',false);\r\n  \tsetButtonDisabled('saveAndContinue',false);\r\n}\r\n\r\n</script>\r\n</body>\r\n</html>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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