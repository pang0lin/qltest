/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:04:35 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.doc.doc;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class senddocument_005fchangeNum_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

      out.write('\r');
      out.write('\n');
      out.write(' ');

 String sendFileCode= request.getAttribute("sendFileCode")==null?"":request.getAttribute("sendFileCode")+"";
 String sendFileYear= request.getAttribute("sendFileYear")==null?"":request.getAttribute("sendFileYear")+"";
 String sendFileMaxNumber= request.getAttribute("sendFileMaxNumber") ==null?"":request.getAttribute("sendFileMaxNumber")+"";

 String redHeadId= request.getAttribute("redHeadId")==null?"": request.getAttribute("redHeadId")+"";
       
	   int  numNum=0;
       int  zeroNum=0;
	   String  numId="";//文号Id
       String  sendWord="";//机关代字
       String  temId=""; //模板Id
       String  num_f1="";//文号前部分
       String  num_f2="";//文号中部分(序号)
       String  num_f3="";//文号后部分
	   String  seqId="";
	   String  seqstr="";
	   String  seqfig="";
	   String  changeNumType="modi";
	   String initValue = "0";
    
   if(request.getAttribute("wordmap")!=null){
    java.util.Map map=(java.util.Map) request.getAttribute("wordmap");
	 if(map!=null){
	  numId=map.get("numId")==null?"":map.get("numId").toString();
      sendWord=map.get("sendWord")==null?"":map.get("sendWord").toString();
      temId=map.get("temId")==null?"":map.get("temId").toString();
      num_f1=map.get("num_f1")==null?"":map.get("num_f1").toString();
      num_f2=map.get("num_f2")==null?"":map.get("num_f2").toString();
      num_f3=map.get("num_f3")==null?"":map.get("num_f3").toString();
	  seqId=map.get("seqId")==null?"":map.get("seqId").toString();
	  seqstr=map.get("seqstr")==null?"":map.get("seqstr").toString();
	  seqfig=map.get("seqfig")==null?"":map.get("seqfig").toString();
      changeNumType=map.get("changeNumType")==null?"modi":map.get("changeNumType").toString();
	  zeroNum=  Integer.parseInt(map.get("zeroNum")==null?"0":map.get("zeroNum").toString());
	  numNum=Integer.parseInt(map.get("numNum")==null?"0":map.get("numNum").toString());
	  initValue = map.get("initValue")==null?"0":map.get("initValue")+"";
	 }
   }
  
  int oldNumNum=num_f2.length();

 String totalNum=num_f1+num_f2+num_f3;
//System.out.println(numNum + "   "+initValue);
//山东国投不需要补0
if(!"shandongguotou".equals(com.js.util.config.SystemCommon.getCustomerName())){
	if(num_f2.length()<numNum){
		int c = numNum-num_f2.length();
		for(int i=0;i<c;i++){
			num_f2 = "0"+num_f2;
		}
	}
}

 
      out.write("\r\n<head>\r\n<title>设置文号</title>\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script src=\"/jsoa/js/js.js\" language=\"javascript\"></script>\r\n<SCRIPT language=\"javascript\" src=\"/jsoa/doc/js/trim.js\"></SCRIPT>\r\n<script  src=\"/jsoa/js/checkText.js\"  language=\"javascript\" ></script>\r\n<script language=\"javascript\" src=\"js/openEndow.js\"></script>\r\n</head>\r\n\r\n<body scroll=\"no\">\r\n<table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" id=\"pup_main\">\r\n\r\n  <tr>\r\n    <td align=\"center\" valign=\"top\">\r\n\t\t<div id=\"pup_content\">\r\n\t\t  <table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n            <tr>\r\n            \r\n              <td valign=\"center\" align=\"left\" height=\"35\">&nbsp;&nbsp;设置文号序号：</label></td>\r\n            </tr>\r\n            <tr>\r\n              <td valign=\"top\" align=\"center\" height=\"25\">\r\n                ");
out.print(num_f1);
      out.write("<input type=\"text\"   class=\"inputText\" name=\"numfig\"  id=\"xuhao\"  value=\"");
      out.print(num_f2 );
      out.write("\" style=\"width:80px\" >");
out.print(num_f3);
      out.write("\r\n              </td>\r\n            </tr>\r\n\t\t\t <tr><td>&nbsp;</td><td>&nbsp;</td></tr>\r\n          </table>\r\n\t\t</div>\r\n\t</td>\r\n  </tr>\r\n  <tr>\r\n    <td valign=\"top\" height=\"40\" align=\"right\"><label>\r\n      <input type=\"button\" class=\"btnButton4font\" onclick=\"saveClose();\" value=\"保存退出\"/>\r\n\t\t     <button class=\"btnButton2font\" onClick=\"closer();\">退出</button>&nbsp;&nbsp;\r\n    </label></td>\r\n  </tr>\r\n</table>\r\n</body>\r\n</html>\r\n<script language=\"javascript\">\r\n\r\nvar  biaoji=0;\r\n \r\n ");
if(changeNumType.equals("add")){
 
   out.println("biaoji=1");
 }
      out.write("\r\n\r\n ");
if(numId!=null&&!numId.equals("")&&num_f2!=null&&!num_f2.equals("")){
	   
	   out.println("document.getElementById(\"xuhao\").readOnly=false;");
	   
	   }else{
	   out.println("document.getElementById(\"xuhao\").readOnly=true;");
	   
	 }
      out.write("\r\n\r\n\r\n\r\n\r\nfunction saveClose(){\r\n\tvar val = document.getElementById(\"xuhao\").value;\r\n\tif(val == \"\"){\r\n\t   alert(\"序号值不能为空！\");\r\n\t   return  false;\r\n\t  }\r\n\t");
//山东国投不需要2位
	if(!"shandongguotou".equals(com.js.util.config.SystemCommon.getCustomerName())){
      out.write("\r\n\tif(val.length!=");
      out.print(numNum );
      out.write("){\r\n\t\talert(\"序号值必须为");
      out.print(numNum );
      out.write("位！\");\r\n\t\treturn;\r\n\t}\r\n\t");
}
      out.write("\r\n\r\n         result=checkNumber(document.all.numfig,\"序号值\",999999);\r\n\t\t if(result==false)\r\n\t\t return false;\r\n\t\t \r\n\r\n\t\t var oldZeroNum=");
      out.print(zeroNum);
      out.write(";\r\n\t\t var nowNum=document.all.numfig.value;\r\n\t\t var nowNumNum=nowNum.length;\r\n\t\t var oldNum=\"");
      out.print(num_f2);
      out.write("\";\r\n\t\t var oldNumNum=oldNum.length;\r\n\t\t var num_f1=\"");
      out.print(num_f1);
      out.write("\";\r\n\r\n\r\n\t\t/* if(oldNumNum>nowNumNum){\r\n\t\t  for(var i=0;i<(oldNumNum-nowNumNum);i++){\r\n\t\t   \r\n\t\t   num_f1=num_f1+\"0\";\r\n\t\t  }\r\n\t\t \r\n\t\t }\r\n\t\r\n\r\n\t\t if(oldNumNum<nowNumNum){\r\n\t\t\t\r\n\t\t\tfor(var j=0, k=0; j<(nowNumNum-oldNumNum)&&k<oldZeroNum; j++,k++){               \r\n\t\t\t   num_f1=num_f1.substring(0,num_f1.length-1);\r\n\t\t\t}\r\n\t\t   \r\n\t\t \r\n\t\t }*/\r\n\r\n\r\n\t\t\r\n\r\n\t\t\r\n\r\n\r\n    \r\n\tif(typeof(eval(\"opener.window.document.all.field1\"))!= \"undefined\"){\r\n\t   opener.window.document.all.field1.value =num_f1 ; \r\n\r\n  }\r\n    if(typeof(eval(\"opener.window.document.all.field2\"))!= \"undefined\"){\r\n\t  opener.window.document.all.field2.value = document.all.numfig.value;\r\n      \r\n  }\r\n    if(typeof(eval(\"opener.window.document.all.field6\"))!= \"undefined\"){\r\n\t  opener.window.document.all.field6.value = \"");
      out.print(num_f3);
      out.write("\" ; \r\n  }\r\n  \r\n \r\n\r\n  if(typeof(eval(\"opener.window.document.all.sendFilePoNumId\"))!= \"undefined\"){\r\n\t   opener.window.document.all.sendFilePoNumId.value = \"");
      out.print(numId);
      out.write("\" ; \r\n  }\r\n\r\n \r\n\r\n   if(typeof(eval(\"opener.window.document.all.sendSeqId\"))!= \"undefined\"&&biaoji==1){\r\n\t  opener.window.document.all.sendSeqId.value = \"");
      out.print(seqId);
      out.write("\" ; \r\n  }\r\n\r\n   if(typeof(eval(\"opener.window.document.all.sendSeqfig\"))!= \"undefined\"&&biaoji==1){\r\n\t   opener.window.document.all.sendSeqfig.value = \"");
      out.print(seqfig);
      out.write("\" ; \r\n  }\r\n\r\n\r\n if(typeof(eval(\"opener.window.document.all.zjkySeq\"))!= \"undefined\"&&biaoji==1){\r\n\t   opener.window.document.all.zjkySeq.value = \"");
      out.print(seqstr);
      out.write("\" ; \r\n  }\r\n var totalNum=num_f1+document.all.numfig.value+\"");
      out.print(num_f3);
      out.write("\";\r\n\r\n if(typeof(eval(\"opener.window.document.all.documentSendFileByteNumber\"))!= \"undefined\"){\r\n\t   opener.window.document.all.documentSendFileByteNumber.value = totalNum ; \r\n  }\r\n\r\n window.close();\r\n\r\n\r\n}\r\n\r\n//关闭\r\nfunction closer() {\r\n    window.close();\r\n}\r\n\r\n\r\n\r\n</script>\r\n<script  src=\"/jsoa/js/checkText.js\"  language=\"javascript\" ></script>\r\n<SCRIPT language=\"javascript\" src=\"/jsoa/js/openEndow.js\"></SCRIPT>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
