/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:59:56 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.weixin.workflow.eform;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class draftform_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html; charset=utf-8");
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

request.setCharacterEncoding("utf-8");
com.js.oa.eform.service.CustomFormBD cst = new com.js.oa.eform.service.CustomFormBD();
com.js.oa.userdb.service.CustomDatabaseBD userDB=new com.js.oa.userdb.service.CustomDatabaseBD();

String scriptappend = "";
String domainId = session.getAttribute("domainId")==null?"0":session.getAttribute("domainId").toString();
String hideTableHTML = "";
String showTableHTML = "";
java.util.List boxFieldList=userDB.getOnchangeMethodInfo(pageId,domainId);

      out.write("\r\n\r\n<style type=\"text/css\">\r\n    .flowInput{\r\n    \tbehavior:url(/jsoa/js/validate.htc);\r\n  \t\tborder:1px solid #E8E8E8;\r\n    }\r\n\r\n    .flowInputenter{\r\n  \t\tborder:1px solid blue;\r\n    }\r\n\t.mustwrite{\r\n  \t\tborder:1px solid red;\r\n    }\r\n\t.divnowrap\r\n\t{\r\n      meizz:expression(this.noWrap=true);\r\n\t}\r\n</style>\r\n<link rel=\"stylesheet\" type=\"text/css\" href=\"/jsoa/eform/ext/resources/css/ext-all.css\"/>\r\n<script type=\"text/javascript\" src=\"/jsoa/eform/ext/adapter/ext/ext-base.js\"></script>\r\n<script type=\"text/javascript\" src=\"/jsoa/eform/ext/ext-all.js\"></script>\r\n<script type=\"text/javascript\" src=\"/jsoa/eform/js/ajax.js\"></script>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n<!--\r\n  var currentRow = null;\r\n  var tblTR = new Array();\r\n  var tblTRHTML = new Array();\r\n  ");

  //取设置了检索的字段信息
  
  //取设置了提取数据的字段信息
  
      out.write("\r\n  \r\n  Ext.onReady(function(){\r\n\t//Ext.QuickTips.init();\r\n\t");

	String[] fieldObj;
	for(int fi=0;fi<boxFieldList.size();fi++){
		fieldObj=(String[])boxFieldList.get(fi);
		if("1".equals(fieldObj[2])){
			//需要输入检索
	
      out.write("\r\n\tvar cb");
      out.print(fi);
      out.write(" = new Ext.form.ComboBox({\r\n\t\ttypeAhead: true,\r\n\t\ttriggerAction: 'all',\r\n\t\ttransform:'");
      out.print(fieldObj[1]);
      out.write("',\r\n\t\twidth:'200',\r\n\t\tforceSelection:true,\r\n\t\t//listClass:'inputText',\r\n\t\tminListWidth:205+2+2,\r\n\t\tlisteners:{\r\n        \tselect:{\r\n\t\t\t\tfn:function(combo, record, index){\r\n\t\t\t\t\t//判断是否有onchange方法\r\n\t\t\t\t\t");
if(!"0".equals(fieldObj[3])){
      out.write("\r\n\t\t\t\t\t\t");
      out.print(fieldObj[1]);
      out.write("_onchange();\r\n\t\t\t\t\t");
}
      out.write("\r\n\t\t\t\t}\r\n        \t}\r\n\t\t}\r\n    });\r\n\tcb");
      out.print(fi);
      out.write(".el.setStyle({\r\n\t\twidth:'252px',\r\n\t\ttop:'0.1px'\r\n\t});\r\n\t\r\n\t");
}
	}
      out.write("\r\n});\r\n\r\n//构造字段onchange事件\r\n");

for(int fi=0;fi<boxFieldList.size();fi++){
	fieldObj=(String[])boxFieldList.get(fi);
	if(!"0".equals(fieldObj[3])){
		//onchange fangfa

      out.write("\r\nfunction ");
      out.print(fieldObj[1]);
      out.write("_onchange(obj){\r\n    var curIndex=0;\r\n    var curObj=document.getElementsByName(obj.name);\r\n    for(var i=0;i<curObj.length;i++){\r\n    \tif(curObj[i]==obj){\r\n    \t\tcurIndex=i;\r\n    \t}\r\n    }\r\n    \r\n\t");

	if("1".equals(fieldObj[3])||"3".equals(fieldObj[3])){
		
      out.write("\r\n\t\t//提取数据\r\n\t\tvar selValue=obj.value;\r\n\t\tJSRequest.sendPOST(\"/jsoa/eform/fetchdata.jsp?curIndex=\"+curIndex+\"&fieldId=");
      out.print(fieldObj[0]);
      out.write("&selValue=\"+selValue,\"\",showFormSubData);\r\n\t\t");

	}
	if("2".equals(fieldObj[3])||"3".equals(fieldObj[3])){
		out.print(fieldObj[5]);
	}
	
      out.write("\r\n}\r\n");
  }
	//输入检索的回车触发事件
	if("450".equals(fieldObj[7])){
		
      out.write("\r\nfunction ");
      out.print(fieldObj[1]);
      out.write("_enter(){\r\n\tvar inputValue=document.all.");
      out.print(fieldObj[1]);
      out.write("_temp.value;\t\r\n\tJSMainWinOpen2(\"/jsoa/FormDataFilterAction.do?fieldId=");
      out.print(fieldObj[0]);
      out.write("&fieldName=");
      out.print(fieldObj[1]);
      out.write("&inputValue=\"+inputValue,\"\",\"\");\r\n}\r\n\t\t");

	}
}
      out.write("\r\n\r\ndocument.onkeydown=listenKeyDown;\r\n\r\nfunction listenKeyDown(){\r\nvar event = arguments[0] ||window.event;\r\n\tif(event.keyCode==13){\r\n\t\t//按下了enter\r\n\t\ttry{\r\n\t\t\tvar objname=event.srcElement.name;\t\t\t\r\n\t\t\tobjname=objname.substring(0,objname.length-5);\r\n\t\t\teval(objname+\"_enter()\");\r\n\t\t}catch(e){\r\n\t\t}\r\n\t}\r\n}\r\n\r\nfunction showFormSubData(response){\r\n\t//将取回的数据显示在正确的表单中\r\n\t//alert(response.responseText);\r\n    //alert(response.responseXML.documentElement);\r\n\tif(response!=null && response.responseText!=\"\" && response.responseXML.documentElement!=null){\r\n    \tvar root=response.responseXML.documentElement;\r\n    \t\r\n    \t//如是字表多行时需确定本行的位置\r\n    \tvar curIndex=root.getElementsByTagName(\"curIndex\")[0].firstChild.nodeValue;\r\n    \t    \t\r\n    \tvar datas= root.getElementsByTagName(\"data\");\r\n    \tvar formfield,fetchdata,content,fieldTagName;\r\n    \tvar subdatas,datavalue,datatext;\r\n    \tvar oprObj;\r\n    \tfor(var i=0;i<datas.length;i++){\r\n    \t\tformfield=datas[i].getElementsByTagName(\"formfield\")[0].firstChild.nodeValue;\r\n    \t\tfetchdata=datas[i].getElementsByTagName(\"fetchdata\")[0].firstChild.nodeValue;\r\n");
      out.write("    \t\t\r\n    \t\t//alert(formfield);\r\n    \t\t//alert(fetchdata);\r\n    \t\t\r\n    \t\tif(fetchdata==\"0\"){\r\n    \t\t\tcontent=datas[i].getElementsByTagName(\"content\")[0].firstChild.nodeValue;\r\n    \t\t\teval(\"document.all.\"+formfield+\".value='\"+content+\"'\");\r\n    \t\t}else if(fetchdata==\"2\"){\r\n    \t\t\tsubdatas=datas[i].getElementsByTagName(\"content\")[0].getElementsByTagName(\"subdata\");\r\n    \t\t\t//下拉列表形式    \t\t\t\r\n    \t\t\t//oprObj=eval(\"document.all.\"+formfield);\r\n    \t\t\toprObj=document.getElementsByName(formfield)[curIndex];\r\n    \t\t\tfieldTagName=oprObj.tagName;    \t\t\t\r\n    \t\t\tif(fieldTagName==\"INPUT\" || fieldTagName==\"TEXTAREA\"){\r\n    \t\t\t\t//普通输入    \t\t\t\t\r\n    \t\t\t\tdatavalue=subdatas[0].getElementsByTagName(\"datatext\")[0].firstChild.nodeValue;  \r\n    \t\t\t\toprObj.value=datavalue;\r\n    \t\t\t}else if(fieldTagName==\"SELECT\"){\r\n    \t\t\t\t//删除原来的option    \t\t\t\t\r\n    \t\t\t\tjs_deleteAllOptions(oprObj);    \t\t\t\t\r\n    \t\t\t\t//新增option\r\n    \t\t\t\tfor(var j=0;j<subdatas.length;j++){\r\n    \t\t\t\t\tdatavalue=subdatas[j].getElementsByTagName(\"datavalue\")[0].firstChild.nodeValue;\r\n");
      out.write("    \t\t\t\t\tdatatext=subdatas[j].getElementsByTagName(\"datatext\")[0].firstChild.nodeValue;\r\n    \t\t\t\t\tjs_addOption(oprObj,datavalue,datatext);\r\n    \t\t\t\t}\r\n    \t\t\t}\r\n    \t\t}\r\n    \t\t\r\n    \t}\r\n    }\r\n}\r\n\r\nfunction js_deleteAllOptions(obj){\r\n\twhile(length!=0){ \r\n　　 \tvar length=obj.options.length;\r\n　　 \tfor(var i=0;i<length;i++)\r\n　　 \t\tobj.options.remove(i);\r\n　　 \tlength=length/2;\r\n　　 }\r\n}\r\nfunction js_addOption(obj,optionValue,optionText){\r\n\tvar newOption=new Option(optionText,optionValue); \r\n\tobj.options[obj.length]=newOption; \r\n} \r\n\r\n//-->\r\n</SCRIPT>\r\n<script language=javascript src=\"/jsoa/eform/js/form.js\"></script>\r\n<input type=\"hidden\" name=\"user_Id\" value=\"");
      out.print(session.getAttribute("userId"));
      out.write("\"/>\r\n<input type=\"hidden\" name=\"user_Name\" value=\"");
      out.print(session.getAttribute("userName"));
      out.write("\"/>\r\n<input type=\"hidden\" name=\"user_Account\" value=\"");
      out.print(session.getAttribute("userAccount"));
      out.write("\"/>\r\n<input type=\"hidden\" name=\"org_Name\" value=\"");
      out.print(session.getAttribute("orgName"));
      out.write("\"/>\r\n<input type=\"hidden\" name=\"org_ID\" value=\"");
      out.print(session.getAttribute("orgId"));
      out.write("\"/>\r\n<input type=\"hidden\" name=\"group_ID\" value=\"");
      out.print(session.getAttribute("groupId"));
      out.write("\"/>\r\n<input type=\"hidden\" name=\"Hide_Field\" value=\"");
      out.print(request.getAttribute("hideField"));
      out.write("\"/>\r\n<input type=\"hidden\" name=\"Page_Id\" value=\"");
      out.print(pageId);
      out.write("\"/>\r\n<input type=\"hidden\" name=\"Info_Id\" value=\"");
      out.print(request.getAttribute("infoId"));
      out.write("\"/>\r\n<input type=\"hidden\" name=\"work_Status\" value=\"");
      out.print(request.getParameter("workStatus"));
      out.write("\"/>\r\n<input type=\"hidden\" name=\"fromDraft\" value=\"1\"/>\r\n\r\n");
      out.print(cst.getComputeFieldHTML(pageId));
      out.write("\r\n\r\n<div id=\"formHTML\">\r\n<!-- 读取表单设计模板 -->\r\n");
      out.print(cst.getCode(pageId));
      out.write("\r\n</div>\r\n\r\n\r\n<script language=\"javascript\">\r\n");

	//读取隐藏字段（不可编辑字段）
  String hide_Field =request.getAttribute("hideField")==null?"":request.getAttribute("hideField").toString();
  //判断当前表单是否新增
	if(request.getAttribute("infoId")==null || request.getAttribute("infoId").toString().toUpperCase().equals("NULL") || request.getAttribute("infoId").toString().trim().length()<1){
		//初始化显示界面(新增)
		//System.out.println("新增表单.........................................................");
		
		// 如果是发起子流程则如果子流程中的表单与主表单相同则将数据继承
		String pareFormId=request.getParameter("pareTableId");
		boolean isNewForm=true;
		if(pareFormId!=null || !"null".equals(pareFormId) || !"".equals(pareFormId)){
		  //取得父表单ID则比较父表单ID是否与当前表单ID相同，如果相同的话，则继承主表单数据
		  isNewForm=cst.isNewForm(pareFormId,pageId);
		}
		
		if(isNewForm){
		   //新表单
		
			//out.println("新增空白表单.........................................................");
		    //取得表单关联的所有字段信息的数据
			String[][] fieldArr =cst.getPageField(pageId);
			if(fieldArr!=null){
	            //遍历所有字段，获得字段的页面显示脚本
				for(int i=0;i<fieldArr.length;i++){
				//System.out.println("a:"+fieldArr[i][0]+"         b:"+fieldArr[i][1]);
				
					if(fieldArr[i][0]!=null && (!fieldArr[i][0].trim().equals(""))){
						out.print(cst.getHTML(fieldArr[i][0],hide_Field,domainId,fieldArr[i][1]));
					}
				}
			}
				
           //获得表单关联的所有子表字段
           String[][] forFieldArr =cst.getForeignField(pageId);
				if(forFieldArr!=null){
		        //遍历所有字段，获得字段的页面显示脚本
						for(int i=0;i<forFieldArr.length;i++){
							if(forFieldArr[i][2]!=null && (!forFieldArr[i][2].trim().equals(""))){
								out.print(cst.getHTML(forFieldArr[i][2],hide_Field,domainId,forFieldArr[i][3]));
							}
					  }
           }
         
         //start
         //获得表单关联的所有子表数组
         String[] tables = cst.getForeignTable(pageId);
         if (tables != null && tables.length > 0) {
              //遍历所有子表
              for (int i = 0; i < tables.length; i++) {
                  if (tables[i] == null || tables[i].trim().length() < 1)continue;
                  if(i==0){//将第一个子表设置为增加行、删除行图标所在的焦点
                  	scriptappend = "currentRow = document.getElementsByName('"+tables[i]+"TR')[document.getElementsByName('"+tables[i]+"TR').length-1];setAbsolute(currentRow);";
                  }
                  //设置统计字段
                  String[][] tlFlds = cst.getTotalFieldByTableName(tables[i],domainId);
                  if(tlFlds !=null && tlFlds.length>0){
                  	out.print("addTotalRow('"+tables[i]+"');");
                          String tlfld = "";
                          for(int p=0;p<tlFlds.length;p++){
                          	  tlfld +=  tlFlds[p][0]+",";
                                    out.print("document.getElementById('"+tables[i]+"TOTALTD').innerHTML+='"+tlFlds[p][1]+":<label id="+tlFlds[p][0]+"totallabel></label>';");
                          }
                          out.print("document.getElementById('"+tables[i]+"TOTALTD').innerHTML+='<input type=hidden id=totalField value="+tlfld+">';");
                          out.print("setTotalValue();");
                  }
              }
          }
     }else{
     	  //子流程表单与主流程表单用的是同一张数据表，取主流程中的数据填充表单
     	  
     	  pageId=pareFormId;
     	  
     	  //当前表单为编辑表单（表单已有对应的数据）
		 //System.out.println("新增子流程的继承主流程数据.........................................................");
     String forHideField = "";//字表不可编辑字段
     
     if("1".equals(request.getParameter("flag"))) hide_Field="ALL";
     
     
		 //所有字段不可编辑，该种情况表示在自定义模块中点击链接查看信息
     String[][] fieldArr = cst.getPageField(pageId);//读取表单那关联的所有主表字段信息
     if(fieldArr!=null){
				 for(int i=0;i<fieldArr.length;i++){
				    //遍历所有主表字段
				    //System.out.println(fieldArr[i][0]+"   :"+fieldArr[i][1]);
						if(fieldArr[i][0]!=null && (!fieldArr[i][0].trim().equals(""))){
		            //获得主表字段的显示脚本
							  out.print(cst.getEditHTML(fieldArr[i][0],request.getParameter("pareRecordId"),pageId,hide_Field,domainId,fieldArr[i][1]));
						}
				 }
		 }
		 
 		 
     //start
     //获得表单那关联的所有子表
     String[] tables = cst.getForeignTable(pageId);
     //System.out.println("talbe:"+tables.length);
     if (tables != null && tables.length > 0) {
          for (int i = 0; i < tables.length; i++) {
              //遍历所有子表
              if (tables[i] == null || tables[i].trim().length() < 1)continue;
              //提取字表关联的所有字段
              String[][] forFields = cst.getForeignFieldList(pageId,tables[i],domainId);
              //System.out.println("forFields:"+forFields.length);
              //拼装SQL
              String sql = "";//查询子表关联到表单中的所有字段值的SQL语句
              if(forFields!=null && forFields.length>0){
				           boolean hideTable = true;//子表是否有可写字段
                   for(int k=0;k<forFields.length;k++){
                       //遍历所有字段
                       sql += forFields[k][5]+",";//将字段添加到数据查询的SQL中
											 if(forHideField.indexOf(forFields[k][5])<0){
											    //如果该子表中有可编辑字段，则该表可编辑
													hideTable = false;
											 }
                   }
                   //生成子表是否可编辑的HTML元素，用于数据更新、增加删除行的控制
                   if(hideTable){
                            hideTableHTML += "<input type=hidden name=hideTable id=hideTable value="+tables[i]+">";
                    }else{
                            showTableHTML += "<input type=hidden name=showTable id=showTable value="+tables[i]+">";
                    }
                    sql = sql.substring(0,sql.length()-1);
              }
              //System.out.println("sql:"+sql);
              //提取子表数据
              String[][] data = null;
              if(sql.length()>1){
                  	sql = "select " + sql + "," + tables[i] +"_id from " + tables[i] +" where " + tables[i]+"_FOREIGNKEY="+request.getParameter("pareRecordId");
              	data = cst.loadDataBySQL(sql,String.valueOf(forFields.length+1));
              }else{
              	continue;
              }
              //设置页面子表行数
	            if(data!=null && data.length>0){
	                	for(int n=0;n<data.length-1;n++){//遍历所有数据，解析数据行，根据记录数量增加行
	                        	String script = "currentRow = document.getElementsByName('"+tables[i]+"TR')[document.getElementsByName('"+tables[i]+"TR').length-1];addRow();";
	                        	out.print(script);
	                    }
	                    for(int n=0;n<data.length;n++){//遍历数据，解析数据在表单上的展示
	                            //解析数据
	                            for(int m=0;m<forFields.length;m++){//遍历字段，生成该字段及其值展示在表单上的脚本
	                                out.print(cst.getForeignEditHTML(reuquest,forFields[m][5],data[n][m],forFields,"",m,String.valueOf(n),forFields[m][0]));
	                            }
	                            String script = "document.getElementsByName('"+forFields[0][0]+"-"+forFields[0][5]+"')["+String.valueOf(n)+"].innerHTML+='<input type=hidden id="+tables[i]+"id name="+tables[i]+"id value="+data[n][forFields.length]+">';";
	                           out.print(script);
	                    }
	            }else{//该子表没有数据，隐藏模板中初始化的第一个数据行
	                for(int m=0;m<forFields.length;m++){
	                        out.print(cst.getForeignEditHTML(reuquest,forFields[m][5],"",forFields,(("0".equals(request.getParameter("workStatus"))||"-1".equals(request.getParameter("workStatus"))||"-2".equals(request.getParameter("workStatus")))?hide_Field+forHideField:"ALL"),m,"0",forFields[m][0]));
	                }
	                out.print("document.getElementsByName('"+tables[i]+"TR')[0].style.display='none';");
	
	            }
              //设置统计字段
              String[][] tlFlds = cst.getTotalFieldByTableName(tables[i],domainId);
              //System.out.println("333333333tlflds:"+tlFlds);
              
              if(tlFlds !=null && tlFlds.length>0){
                  	//增加统计字段显示行
              	    out.print("addTotalRow('"+tables[i]+"');");
                    String tlfld = "";
                    for(int p=0;p<tlFlds.length;p++){//遍历统计字段，设置对应的统计字段表单元素
                    	  tlfld +=  tlFlds[p][0]+",";
                              out.print("document.getElementById('"+tables[i]+"TOTALTD').innerHTML+='"+tlFlds[p][1]+":<label id="+tlFlds[p][0]+"totallabel></label>';");
                    }
                    out.print("document.getElementById('"+tables[i]+"TOTALTD').innerHTML+='<input type=hidden id=totalField value="+tlfld+">';");
                    out.print("setTotalValue();");
              }
          }
      }//end
      
      
      
     }
    
	}else{
		 //当前表单为编辑表单（表单已有对应的数据）
     String forHideField = "";//字表不可编辑字段
     if(request.getParameter("processId")!=null){
          //表单在流程中调用
          com.js.oa.jsflow.service.WorkFlowBD workFlowBD = new com.js.oa.jsflow.service.WorkFlowBD();
          //取读字段
        	String writeFieldString = "";//可写字段串 $字段名称$$字段名称$
        	if("0".equals(request.getParameter("workStatus"))){
        	    //是待办文件
        	    //取当前节点的可写字段List
              java.util.List list = workFlowBD.getRWList(request.getParameter("activity"), request.getParameter("table"), request.getParameter("record"), "1");
              String[] str = null;
              for(int i = 0; i < list.size(); i ++){//遍历可写字段，将可写字段转化为字符串形式
                	str = (String[]) list.get(i);
                	if(str[0].equals("1")){//可写字段
                    	writeFieldString += "$" + str[1] + "$";
               	 	}
            	}
              //表单中关联的所有子表字段信息
              String[][] forFieldArr = new com.js.oa.eform.service.CustomFormBD().getForeignField(pageId);
              if(forFieldArr!=null){
              		for(int i=0;i<forFieldArr.length;i++){
              		    //遍历左右子表字段，如果字段不在可写字段中，则字段为不可编辑字段
                  		if(writeFieldString.indexOf(forFieldArr[i][2])<0){
                          		forHideField += forFieldArr[i][2]+",";
                  		}
             		  }
        		  }
        	}
       
     }

		 if("1".equals(request.getParameter("flag"))) hide_Field="ALL";
		 //所有字段不可编辑，该种情况表示在自定义模块中点击链接查看信息
     String[][] fieldArr = cst.getPageField(pageId);//读取表单那关联的所有主表字段信息
     if(fieldArr!=null){
				 for(int i=0;i<fieldArr.length;i++){
				    //遍历所有主表字段
						if(fieldArr[i][0]!=null && (!fieldArr[i][0].trim().equals(""))){
		                      //获得主表字段的显示脚本
							  out.print(cst.getEditHTML(fieldArr[i][0],request.getAttribute("infoId").toString(),pageId,hide_Field,domainId,fieldArr[i][1]));
						}
				 }
		 }
		 
     //start
     //获得表单那关联的所有子表
     String[] tables = cst.getForeignTable(pageId);
     if (tables != null && tables.length > 0) {
          for (int i = 0; i < tables.length; i++) {
              //遍历所有子表
              if (tables[i] == null || tables[i].trim().length() < 1)continue;
              //提取字表关联的所有字段
              String[][] forFields = cst.getForeignFieldList(pageId,tables[i],domainId);
              //拼装SQL
              String sql = "";//查询子表关联到表单中的所有字段值的SQL语句
              if(forFields!=null && forFields.length>0){
				           boolean hideTable = true;//子表是否有可写字段
                   for(int k=0;k<forFields.length;k++){
                       //遍历所有字段
                       sql += forFields[k][5]+",";//将字段添加到数据查询的SQL中
											 if(forHideField.indexOf(forFields[k][5])<0){
											    //如果该子表中有可编辑字段，则该表可编辑
													hideTable = false;
											 }
                   }
                   //生成子表是否可编辑的HTML元素，用于数据更新、增加删除行的控制
                   if(hideTable){
                            hideTableHTML += "<input type=hidden name=hideTable id=hideTable value="+tables[i]+">";
                    }else{
                            showTableHTML += "<input type=hidden name=showTable id=showTable value="+tables[i]+">";
                    }
                    sql = sql.substring(0,sql.length()-1);
              }
              //提取子表数据
              String[][] data = null;
              if(sql.length()>1){
                  	sql = "select " + sql + "," + tables[i] +"_id from " + tables[i] +" where " + tables[i]+"_FOREIGNKEY="+request.getAttribute("infoId").toString();
              	data = cst.loadDataBySQL(sql,String.valueOf(forFields.length+1));
              }else{
              	continue;
              }
              //设置页面子表行数
	            if(data!=null && data.length>0){
	                	for(int n=0;n<data.length-1;n++){//遍历所有数据，解析数据行，根据记录数量增加行
	                        	String script = "currentRow = document.getElementsByName('"+tables[i]+"TR')[document.getElementsByName('"+tables[i]+"TR').length-1];addRow();";
	                        	out.print(script);
	                    }
	                    for(int n=0;n<data.length;n++){//遍历数据，解析数据在表单上的展示
	                            //解析数据
	                            for(int m=0;m<forFields.length;m++){//遍历字段，生成该字段及其值展示在表单上的脚本
	                                out.print(cst.getForeignEditHTML(reuquest,forFields[m][5],data[n][m],forFields,(("0".equals(request.getParameter("workStatus"))||"-1".equals(request.getParameter("workStatus"))||"-2".equals(request.getParameter("workStatus")))?hide_Field+forHideField:"ALL"),m,String.valueOf(n),forFields[m][0]));
	                            }
	                            String script = "document.getElementsByName('"+forFields[0][0]+"-"+forFields[0][5]+"')["+String.valueOf(n)+"].innerHTML+='<input type=hidden id="+tables[i]+"id name="+tables[i]+"id value="+data[n][forFields.length]+">';";
	                           out.print(script);
	                    }
	            }else{//该子表没有数据，隐藏模板中初始化的第一个数据行
	                for(int m=0;m<forFields.length;m++){
	                        out.print(cst.getForeignEditHTML(reuquest,forFields[m][5],"",forFields,(("0".equals(request.getParameter("workStatus"))||"-1".equals(request.getParameter("workStatus"))||"-2".equals(request.getParameter("workStatus")))?hide_Field+forHideField:"ALL"),m,"0",forFields[m][0]));
	                }
	                out.print("document.getElementsByName('"+tables[i]+"TR')[0].style.display='none';");
	
	            }
              //设置统计字段
              String[][] tlFlds = cst.getTotalFieldByTableName(tables[i],domainId);
              if(tlFlds !=null && tlFlds.length>0){
                  	//增加统计字段显示行
              	    out.print("addTotalRow('"+tables[i]+"');");
                    String tlfld = "";
                    for(int p=0;p<tlFlds.length;p++){//遍历统计字段，设置对应的统计字段表单元素
                    	  tlfld +=  tlFlds[p][0]+",";
                              out.print("document.getElementById('"+tables[i]+"TOTALTD').innerHTML+='"+tlFlds[p][1]+":<label id="+tlFlds[p][0]+"totallabel></label>';");
                    }
                    out.print("document.getElementById('"+tables[i]+"TOTALTD').innerHTML+='<input type=hidden id=totalField value="+tlfld+">';");
                    out.print("setTotalValue();");
              }
          }
      }//end
 }

      out.write("\r\n\r\n//alert(WorkFlowFormActionForm.computeField.value);\r\n</script>\r\n\r\n");

	//子表的计算字段设置
  out.print(cst.getForeignComputeFieldHTML(pageId));
  out.print(hideTableHTML+showTableHTML);

      out.write("\r\n\r\n<script language=javascript src=\"/jsoa/eform/datetime/time.js\"></script>\r\n<script language=\"javascript\">\r\n\r\nif(document.all.wf_ua){\r\n    if(document.all.wf_ua.length){\r\n    }else{\r\n        eval(\"document.all.\" + document.all.wf_ua.value + \".value='");
      out.print(session.getAttribute("userAccount"));
      out.write("'\");\r\n    }\r\n}\r\n</script>\r\n\r\n<DIV id=adddelrow_div\r\nstyle=\"BORDER-RIGHT: #0a246a 1px solid; BORDER-TOP: #0a246a 1px solid; VISIBILITY: hidden; BORDER-LEFT: #0a246a 1px solid; WIDTH: 30px; BORDER-BOTTOM: #0a246a 1px solid; POSITION: absolute\">\r\n<TABLE height=\"100%\" cellSpacing=0 cellPadding=0 width=\"100%\" border=0>\r\n  <TBODY>\r\n  <TR>\r\n    <TD onmouseover=\"this.style.borderRiht='1px #0A246A solid'\"\r\n    onmouseout=\"this.style.borderRiht=''\" align=middle><SPAN id=delrow_div\r\n      title=点击删除记录 style=\"CURSOR: hand\"><IMG height=12\r\n      src=\"/jsoa/eform/images/delarrow.gif\" width=12 onclick='delRow()' align=absMiddle></SPAN> </TD>\r\n    <TD onmouseover=\"this.style.borderLeft='1px #0A246A solid'\"\r\n    onmouseout=\"this.style.borderLeft=''\" align=middle><SPAN id=addrow_div\r\n      title=点击添加记录 style=\"CURSOR: hand\"><IMG height=12\r\n      src=\"/jsoa/eform/images/addarrow.gif\" width=12 onclick='addRow()' align=absMiddle></SPAN>\r\n  </TD></TR></TBODY></TABLE></DIV>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n<!--\r\n//document.body.attachEvent(\"onload\",function(){});\r\n");
      out.write("//alert(document.body.readyState);\r\nsetNoWrap();\r\ninitRow();\r\n//-->\r\n</SCRIPT>\r\n<script >\r\n\t");
//=scriptappend
      out.write("\r\n        ");
/*
        	if(scriptappend.length()>0){
                	out.print("document.body.attachEvent('onload',function(){setAbsolute(currentRow);});");
                }*/
        
      out.write("\r\n        //alert(document.body.onload);\r\n</script>\r\n<!-- <script src=\"/jsoa/js/util.js\"></script>-->");
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
