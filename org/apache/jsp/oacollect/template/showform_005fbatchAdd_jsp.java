/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:03:58 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.oacollect.template;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class showform_005fbatchAdd_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write('\r');
      out.write('\n');

request.setCharacterEncoding("GBK");
com.js.oa.eform.service.CustomFormBD cst = new com.js.oa.eform.service.CustomFormBD();
com.js.oa.userdb.service.CustomDatabaseBD userDB=new com.js.oa.userdb.service.CustomDatabaseBD();
String scriptappend = "";
String domainId = session.getAttribute("domainId")==null?"0":session.getAttribute("domainId").toString();
String parent_fromDraft="draftForm".equals(request.getParameter("action"))?"1":"0";
if("1".equals(request.getParameter("resubmit"))){
	parent_fromDraft="1";
}
String hideTableHTML = "";
String showTableHTML = "";
java.util.List boxFieldList=userDB.getOnchangeMethodInfo(pageId,domainId);

//??????????????????????????????????????????
String hide_Field =request.getAttribute("hideField")==null?"":request.getAttribute("hideField").toString();

      out.write("\r\n\r\n<style type=\"text/css\">\r\n    .flowInput{\r\n    \tbehavior:url(/jsoa/js/validate.htc);\r\n  \t\t/*border:1px solid #E8E8E8;*/\r\n    }\r\n    .flowInputRed{\r\n    \tbehavior:url(/jsoa/js/formvalidate.htc);\r\n  \t\t/*border:1px solid #E8E8E8;*/\r\n    }\r\n\r\n    .flowInputenter{\r\n  \t\tborder:1px solid blue;\r\n    }\r\n\t.mustwrite{\r\n  \t\tborder:1px solid red;\r\n    }\r\n\t.divnowrap\r\n\t{\r\n      meizz:expression(this.noWrap=true);\r\n\t}\r\n</style>\r\n<link rel=\"stylesheet\" type=\"text/css\" href=\"/jsoa/eform/ext/resources/css/ext-all-jiusi.css\"/>\r\n<script type=\"text/javascript\" src=\"/jsoa/eform/ext/adapter/ext/ext-base.js\"></script>\r\n<script type=\"text/javascript\" src=\"/jsoa/eform/ext/ext-all.js\"></script>\r\n<script type=\"text/javascript\" src=\"/jsoa/eform/js/ajax.js\"></script>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n<!--\r\n  var currentRow = null;\r\n  var tblTR = new Array();\r\n  var tblTRHTML = new Array();\r\n  ");

  //?????????????????????????????????
  
  //???????????????????????????????????????
  
      out.write("\r\n  \r\n  Ext.onReady(function(){\r\n\t//Ext.QuickTips.init();\r\n\t");

	String[] fieldObj;
	String hide_field_readonly=","+hide_Field+",";
	for(int fi=0;fi<boxFieldList.size();fi++){
		fieldObj=(String[])boxFieldList.get(fi);
		if("1".equals(fieldObj[2])){
			//??????????????????
			if (hide_field_readonly.indexOf(","+fieldObj[0]+",") >= 0 || hide_field_readonly.indexOf(fieldObj[1]) >= 0) {
				//?????????????????????????????????????????????
				continue;
			}else{
			
	
      out.write("\r\n\ttry{\r\n\t\tvar cb");
      out.print(fi);
      out.write(" = new Ext.form.ComboBox({\r\n\t\t\ttypeAhead: true,\r\n\t\t\ttriggerAction: 'all',\r\n\t\t\ttransform:'");
      out.print(fieldObj[1]);
      out.write("',\r\n\t\t\twidth:'200',\r\n\t\t\tforceSelection:true,\r\n\t\t\t//listClass:'inputText',\r\n\t\t\tminListWidth:205+2+2,\t\t\t\r\n\t\t\tlisteners:{\r\n\t        \tselect:{\r\n\t\t\t\t\tfn:function(combo, record, index){\r\n\t\t\t\t\t\t//???????????????onchange??????\r\n\t\t\t\t\t\t");
if(!"0".equals(fieldObj[3])){
      out.write("\r\n\t\t\t\t\t\t\t");
      out.print(fieldObj[1]);
      out.write("_onchange(combo);\r\n\t\t\t\t\t\t");
}
      out.write("\r\n\t\t\t\t\t}\r\n\t        \t}\r\n\t\t\t}\r\n\t    });\r\n\t\tcb");
      out.print(fi);
      out.write(".el.setStyle({\r\n\t\t\twidth:'252px',\r\n\t\t\ttop:'0.1px'\r\n\t\t});\r\n\t}catch(ee){\r\n\t}\r\n\t\r\n\t");
    }
	   }
	}
      out.write("\r\n});\r\n\r\n//????????????onchange??????\r\n");

for(int fi=0;fi<boxFieldList.size();fi++){
	fieldObj=(String[])boxFieldList.get(fi);
	if(!"0".equals(fieldObj[3])){
		//onchange fangfa

      out.write("\r\nfunction ");
      out.print(fieldObj[1]);
      out.write("_onchange(obj){\r\n    var curIndex=0;\r\n    var curObj=document.getElementsByName(obj.name);\r\n    for(var i=0;i<curObj.length;i++){\r\n    \tif(curObj[i]==obj){\r\n    \t\tcurIndex=i;\r\n    \t}\r\n    }\r\n    \r\n\t");

	if("1".equals(fieldObj[3])||"3".equals(fieldObj[3])){
		
      out.write("\r\n\t\t//????????????\r\n\t\tvar selValue=obj.value;\r\n\t\tvar submitPersonObj=document.getElementsByName(\"submitPersonId\");\r\n\t\tvar submitPersonId=\"\";\r\n\t\tif(submitPersonObj.length>0){\r\n\t\t\tsubmitPersonId=submitPersonObj[0].value;\r\n\t\t}\r\n\t\tJSRequest.sendPOST(((window.location.href).substring(0,(window.location.href).indexOf(\"/jsoa\")))+\"/jsoa/eform/fetchdata.jsp?curIndex=\"+curIndex+\"&fieldId=");
      out.print(fieldObj[0]);
      out.write("&submitPersonId=\"+submitPersonId+\"&selValue=\"+selValue,\"\",showFormSubData);\r\n\t\t");

	}
	if("2".equals(fieldObj[3])||"3".equals(fieldObj[3])){
		out.print(fieldObj[5]);
	}
	
      out.write("\r\n}\r\n");
  }
	//?????????????????????????????????
	if("450".equals(fieldObj[7])){
		
      out.write("\r\nfunction ");
      out.print(fieldObj[1]);
      out.write("_enter(obj){\r\n    \t\r\n\tvar curIndex=0;\r\n    var curObj=document.getElementsByName(obj.name);\r\n    for(var i=0;i<curObj.length;i++){\r\n    \tif(curObj[i]==obj){\r\n    \t\tcurIndex=i;\r\n    \t}\r\n    }\r\n    var inputValueObj=document.getElementsByName(\"");
      out.print(fieldObj[1]);
      out.write("_temp\");\r\n    \r\n\tvar inputValue=inputValueObj[curIndex].value;\t\r\n\t\r\n\tvar submitPersonObj=document.getElementsByName(\"submitPersonId\");\r\n\tvar submitPersonId=\"\";\r\n\tif(submitPersonObj.length>0){\r\n\t\tsubmitPersonId=submitPersonObj[0].value;\r\n\t}\r\n\t\r\n\tJSMainWinOpen2(\"/jsoa/FormDataFilterAction.do?curIndex=\"+curIndex+\"&fieldId=");
      out.print(fieldObj[0]);
      out.write("&fieldName=");
      out.print(fieldObj[1]);
      out.write("&submitPersonId=\"+submitPersonId+\"&inputValue=\"+inputValue,\"\",\"\");\r\n}\r\n\t\t");

	}
}
      out.write("\r\n\r\ndocument.onkeydown=listenKeyDown;\r\n\r\nfunction listenKeyDown(){\r\n\tvar event = arguments[0] ||window.event;\r\n\tif(event.keyCode==13){\r\n\t\t//?????????enter\r\n\t\ttry{\r\n\t\t    var obj=event.srcElement;\r\n\t\t\tvar objname=event.srcElement.name;\t\r\n\t\t\t\r\n\t\t\tvar curIndex=0;\r\n\t\t    var curObj=document.getElementsByName(objname);\r\n\t\t    for(var i=0;i<curObj.length;i++){\r\n\t\t    \tif(curObj[i]==obj){\r\n\t\t    \t\tcurIndex=i;\r\n\t\t    \t}\r\n\t\t    }\r\n    \r\n\t\t\tobjname=objname.substring(0,objname.length-5);\r\n\t\t\tobjname=objname+\"_btn\";\t\t\t\r\n\t\t\t\r\n\t\t\tobj=document.getElementsByName(objname);\r\n\t\t\tobj[curIndex].click();\r\n\t\t\t\r\n\t\t\t//eval(objname+\"_enter(\"+document.getElementsByName(objname)[0]+\")\");\r\n\t\t}catch(e){\r\n\t\t\t//alert(e);\r\n\t\t}\r\n\t}\r\n}\r\n\r\nfunction showFormSubData(response){\r\n\t//?????????????????????????????????????????????\r\n\t//alert(response.responseText);\r\n    //alert(response.responseXML.documentElement);\r\n\tif(response!=null && response.responseText!=\"\" && response.responseXML.documentElement!=null){\r\n    \tvar root=response.responseXML.documentElement;\r\n    \t\r\n    \t//?????????????????????????????????????????????\r\n");
      out.write("    \tvar curIndex=root.getElementsByTagName(\"curIndex\")[0].firstChild.nodeValue;\r\n    \t    \t\r\n    \tvar datas= root.getElementsByTagName(\"data\");\r\n    \tvar formfield,fetchdata,content,fieldTagName;\r\n    \tvar subdatas,datavalue,datatext;\r\n    \tvar oprObj;\r\n    \tfor(var i=0;i<datas.length;i++){\r\n    \t\tformfield=datas[i].getElementsByTagName(\"formfield\")[0].firstChild.nodeValue;\r\n    \t\tfetchdata=datas[i].getElementsByTagName(\"fetchdata\")[0].firstChild.nodeValue;\r\n    \t\t\r\n    \t\t//alert(formfield);\r\n    \t\t//alert(fetchdata);    \t\t\r\n    \t\t\r\n    \t\t\r\n    \t\tif(fetchdata==\"0\"){\r\n    \t\t    oprObj=document.getElementsByName(formfield)[curIndex]; \r\n    \t\t    oprObj.value=\"\";     \t\t   \r\n    \t\t    if(datas[i].getElementsByTagName(\"content\")[0].firstChild!=null){    \t\t       \t\t    \r\n    \t\t\t\tcontent=datas[i].getElementsByTagName(\"content\")[0].firstChild.nodeValue;\r\n    \t\t\t\toprObj.value=content;\r\n    \t\t\t}    \t\t\t    \t\t\t     \t\t\t\t\t\r\n    \t\t\t\r\n    \t\t}else if(fetchdata==\"2\"){\r\n    \t\t\tsubdatas=datas[i].getElementsByTagName(\"content\")[0].getElementsByTagName(\"subdata\");\r\n");
      out.write("    \t\t\t//??????????????????    \t\t\t\r\n    \t\t\t//oprObj=eval(\"document.all.\"+formfield);\r\n    \t\t\toprObj=document.getElementsByName(formfield)[curIndex];    \t\t\t\r\n    \t\t\t\r\n    \t\t\tfieldTagName=oprObj.tagName;    \t\t\t\r\n    \t\t\tif(fieldTagName==\"INPUT\" || fieldTagName==\"TEXTAREA\"){\r\n    \t\t\t\t//???????????? \r\n    \t\t\t\toprObj.value=\"\";   \r\n    \t\t\t\tif(subdatas[0].getElementsByTagName(\"datatext\")[0].firstChild!=null){\t\t\t\t\r\n\t    \t\t\t\tdatavalue=subdatas[0].getElementsByTagName(\"datatext\")[0].firstChild.nodeValue; \r\n\t    \t\t\t\toprObj.value=datavalue;\r\n    \t\t\t\t}\r\n    \t\t\t}else if(fieldTagName==\"SELECT\"){\r\n    \t\t\t\t//???????????????option    \t\t\t\t\r\n    \t\t\t\tjs_deleteAllOptions(oprObj);    \t\t\t\t\r\n    \t\t\t\t//??????option\r\n    \t\t\t\tfor(var j=0;j<subdatas.length;j++){\r\n    \t\t\t\t    try{\r\n\t    \t\t\t\t\tdatavalue=subdatas[j].getElementsByTagName(\"datavalue\")[0].firstChild.nodeValue;\r\n\t    \t\t\t\t\tdatatext=subdatas[j].getElementsByTagName(\"datatext\")[0].firstChild.nodeValue;\r\n\t    \t\t\t\t\tjs_addOption(oprObj,datavalue,datatext);\r\n    \t\t\t\t\t}catch(e){\r\n    \t\t\t\t\t}\r\n    \t\t\t\t}\r\n    \t\t\t\toprObj.onchange();    \t\t\t\t\r\n");
      out.write("    \t\t\t}\r\n    \t\t}\r\n    \t\t\r\n    \t}\r\n    }\r\n}\r\n\r\nfunction js_deleteAllOptions(obj){\r\n\twhile(length!=0){ \r\n?????? \tvar length=obj.options.length;\r\n?????? \tfor(var i=0;i<length;i++)\r\n?????? \t\tobj.options.remove(i);\r\n?????? \tlength=length/2;\r\n?????? }\r\n}\r\nfunction js_addOption(obj,optionValue,optionText){\r\n\tvar newOption=new Option(optionText,optionValue); \r\n\tobj.options[obj.length]=newOption; \r\n} \r\n\r\n//-->\r\n</SCRIPT>\r\n<script language=javascript src=\"/jsoa/eform/js/form.js\"></script>\r\n<input type=\"hidden\" name=\"user_Id\" id=\"user_Id\" value=\"");
      out.print(session.getAttribute("userId"));
      out.write("\"/>\r\n<input type=\"hidden\" name=\"user_Name\" id=\"user_Name\" value=\"");
      out.print(session.getAttribute("userName"));
      out.write("\"/>\r\n<input type=\"hidden\" name=\"user_Account\" id=\"user_Account\" value=\"");
      out.print(session.getAttribute("userAccount"));
      out.write("\"/>\r\n<input type=\"hidden\" name=\"org_Name\" id=\"org_Name\" value=\"");
      out.print(session.getAttribute("orgName"));
      out.write("\"/>\r\n<input type=\"hidden\" name=\"org_ID\" id=\"org_ID\" value=\"");
      out.print(session.getAttribute("orgId"));
      out.write("\"/>\r\n<input type=\"hidden\" name=\"group_ID\" id=\"group_ID\" value=\"");
      out.print(session.getAttribute("groupId"));
      out.write("\"/>\r\n<input type=\"hidden\" name=\"Hide_Field\" id=\"Hide_Field\" value=\"");
      out.print(request.getAttribute("hideField"));
      out.write("\"/>\r\n<input type=\"hidden\" name=\"Page_Id\" id=\"Page_Id\" value=\"");
      out.print(pageId);
      out.write("\"/>\r\n<input type=\"hidden\" name=\"Info_Id\" id=\"Info_Id\" value=\"");
      out.print(request.getAttribute("infoId"));
      out.write("\"/>\r\n<input type=\"hidden\" name=\"work_Status\" id=\"work_Status\" value=\"");
      out.print(request.getParameter("workStatus"));
      out.write("\"/>\r\n<input type=\"hidden\" name=\"parent_fromDraft\" id=\"parent_fromDraft\" value=\"");
      out.print(parent_fromDraft);
      out.write("\">\r\n<input type=\"hidden\" name=\"totalValueSet\" id=\"totalValueSet\" value=\"\" />\r\n<!-- ??? -->\r\n");
      out.print(cst.getComputeFieldHTML(pageId));
      out.write("\r\n<div id=\"formHTML\">\r\n<!-- ???????????????????????? -->\r\n");
      out.print(cst.getCodeByBatChAdd(pageId));
      out.write("\r\n</div>\r\n\r\n\r\n<script language=\"javascript\">\r\n");

String allCoumputFieldStr=cst.getMainAndForeignComputeFields(pageId);
String tableName=cst.getTable(pageId);
  //??????????????????????????????
	if(request.getAttribute("infoId")==null || request.getAttribute("infoId").toString().toUpperCase().equals("NULL") || request.getAttribute("infoId").toString().trim().length()<1){
		//?????????????????????(??????)
		//System.out.println("????????????.........................................................");
		
		// ??????????????????????????????????????????????????????????????????????????????????????????
		String pareFormId=request.getParameter("pareTableId");
		boolean isNewForm=true;
		if(pareFormId!=null || !"null".equals(pareFormId) || !"".equals(pareFormId)){
		  //???????????????ID??????????????????ID?????????????????????ID??????????????????????????????????????????????????????
		  isNewForm=cst.isNewForm(pareFormId,pageId);
		}
		
		if(isNewForm){
		   //?????????
		
			//out.println("??????????????????.........................................................");
		    //????????????????????????????????????????????????(??????)
			String[][] fieldArr =cst.getPageField(pageId);
			if(fieldArr!=null){
	            //??????????????????????????????????????????????????????
				for(int i=0;i<fieldArr.length;i++){
				//System.out.println("a:"+fieldArr[i][0]+"         b:"+fieldArr[i][1]);
					if(fieldArr[i][0]!=null && (!fieldArr[i][0].trim().equals(""))){
						out.print(cst.getHTMLBatchAdd(fieldArr[i][0],hide_Field,domainId,fieldArr[i][1],request,allCoumputFieldStr));
					}
				}
 			//?????????????????????????????????????????????????????????
 			 
              scriptappend = "currentRow = document.getElementsByName('"+tableName+"TR')[document.getElementsByName('"+tableName+"TR').length-1];setAbsolute(currentRow);";
              String agent= request.getHeader("USER-AGENT");   
              if (null != agent && -1 != agent.indexOf("MSIE")) { //???????????????
                out.print("document.getElementById('"+tableName+"DIV').childNodes[0].onclick=setDelRowsValue;");//????????????div????????????????????????????????????????????????????????????????????????
              }else{
                out.print("document.getElementById('"+tableName+"DIV').childNodes[1].onclick=setDelRowsValue;");
              }
			}
     }
    
	}

      out.write("\r\n\r\n//alert(WorkFlowFormActionForm.computeField.value);\r\n</script>\r\n\r\n");

	//???????????????????????????
  //out.print(cst.getForeignComputeFieldHTML(pageId));
  //out.print(hideTableHTML+showTableHTML);  

      out.write("\r\n\r\n<script language=javascript src=\"/jsoa/eform/datetime/time.js\"></script>\r\n<script language=\"javascript\">\r\n\r\nif(document.all.wf_ua){\r\n    if(document.all.wf_ua.length){\r\n    }else{\r\n        eval(\"document.all.\" + document.all.wf_ua.value + \".value='");
      out.print(session.getAttribute("userAccount"));
      out.write("'\");\r\n    }\r\n}\r\n</script>\r\n\r\n<DIV id=adddelrow_div style=\"BORDER: #0a246a 1px solid; VISIBILITY: hidden; z-index:1000; WIDTH: 30px; POSITION: absolute\">\r\n<TABLE height=\"100%\" cellSpacing=0 cellPadding=0 width=\"100%\" border=0>\r\n  <TBODY>\r\n  <TR>\r\n    <TD onmouseover=\"this.style.borderRiht='1px #0A246A solid'\"\r\n    onmouseout=\"this.style.borderRiht=''\" align=middle><SPAN id=delrow_div\r\n      title=?????????????????? style=\"CURSOR: pointer\"><IMG height=12\r\n      src=\"/jsoa/eform/images/delarrow.gif\" width=12 onclick='delRow()' align=absMiddle></SPAN> </TD>\r\n    <TD onmouseover=\"this.style.borderLeft='1px #0A246A solid'\"\r\n    onmouseout=\"this.style.borderLeft=''\" align=middle><SPAN id=addrow_div\r\n      title=?????????????????? style=\"CURSOR: pointer\"><IMG height=12\r\n      src=\"/jsoa/eform/images/addarrow.gif\" width=12 onclick='addRow()' align=absMiddle></SPAN>\r\n  </TD></TR></TBODY></TABLE></DIV>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n<!--\r\n//document.body.attachEvent(\"onload\",function(){});\r\n//alert(document.body.readyState);\r\nsetNoWrap();\r\ninitRow();\r\n");
      out.write("//-->\r\n</SCRIPT>\r\n<script >\r\n\t");
//=scriptappend
      out.write("\r\n        ");
/*
        	if(scriptappend.length()>0){
                	out.print("document.body.attachEvent('onload',function(){setAbsolute(currentRow);});");
                }*/
        
      out.write("\r\n        //alert(document.body.onload);\r\n\r\n</script>\r\n<!-- <script src=\"/jsoa/js/util.js\"></script>-->");
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
