/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:04:28 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.doc.form;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class showform_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;


request.setCharacterEncoding("GBK");
com.js.oa.eform.service.CustomFormBD cst = new com.js.oa.eform.service.CustomFormBD();
String scriptappend = "";
String domainId = session.getAttribute("domainId")==null?"0":session.getAttribute("domainId").toString();
String hideTableHTML = "";
String showTableHTML = "";

      out.write("\r\n\r\n<style type=\"text/css\">\r\n    .flowInput{\r\n  \t\tborder:1px solid #E8E8E8;\r\n    }\r\n\r\n    .flowInputenter{\r\n  \t\tborder:1px solid blue;\r\n    }\r\n\t.mustwrite{\r\n  \t\tborder:1px solid red;\r\n    }\r\n\t.divnowrap\r\n\t{\r\n      meizz:expression(this.noWrap=true);\r\n\t}\r\n</style>\r\n\r\n  <SCRIPT LANGUAGE=\"JavaScript\">\r\n  <!--\r\n  var currentRow = null;\r\n  var tblTR = new Array();\r\n  var tblTRHTML = new Array();\r\n  //-->\r\n  </SCRIPT>\r\n  <script language=javascript src=\"/jsoa/custom_form/js/form.js\"></script>\r\n<input type=\"hidden\" name=\"user_Id\" value=\"");
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
      out.write("\"/>\r\n");

//È¡´Ë±íµ¥µÄÎ¨Ò»×Ö¶Î
String onlyField=cst.getOnlyField(pageId);

      out.write("\r\n<input type=\"hidden\" name=\"page_only_field\" value=\"");
      out.print(onlyField);
      out.write("\"/>\r\n");
      out.print(cst.getComputeFieldHTML(pageId));
      out.write("\r\n\r\n<div id=\"formHTML\">\r\n<!-- ¶ÁÈ¡±íµ¥Éè¼ÆÄ£°å -->\r\n");
      out.print(cst.getCode(pageId));
      out.write("\r\n</div>\r\n\r\n\r\n<script language=\"javascript\">\r\n");

	//¶ÁÈ¡Òþ²Ø×Ö¶Î£¨²»¿É±à¼­×Ö¶Î£©
  String hide_Field =request.getAttribute("hideField")==null?"":request.getAttribute("hideField").toString();
  //ÅÐ¶Ïµ±Ç°±íµ¥ÊÇ·ñÐÂÔö
	if(request.getAttribute("infoId")==null || request.getAttribute("infoId").toString().toUpperCase().equals("NULL") || request.getAttribute("infoId").toString().trim().length()<1){
		//³õÊ¼»¯ÏÔÊ¾½çÃæ(ÐÂÔö)
		//System.out.println("ÐÂÔö±íµ¥.........................................................");

		//2008-08-12 dierzhang Èç¹ûÊÇ·¢Æð×ÓÁ÷³ÌÔòÈç¹û×ÓÁ÷³ÌÖÐµÄ±íµ¥ÓëÖ÷±íµ¥ÏàÍ¬Ôò½«Êý¾Ý¼Ì³Ð
		String pareFormId=request.getParameter("pareTableId");
		boolean isNewForm=true;
		if(pareFormId!=null || !"null".equals(pareFormId) || !"".equals(pareFormId)){
		  //È¡µÃ¸¸±íµ¥IDÔò±È½Ï¸¸±íµ¥IDÊÇ·ñÓëµ±Ç°±íµ¥IDÏàÍ¬£¬Èç¹ûÏàÍ¬µÄ»°£¬Ôò¼Ì³ÐÖ÷±íµ¥Êý¾Ý
		  isNewForm=cst.isNewForm(pareFormId,pageId);
		}

		//if(isNewForm){
		   //ÐÂ±íµ¥

				//String[] fieldArr =cst.getField(pageId);
				//out.println("ÐÂÔö±íµ¥.........................................................");
		    //È¡µÃ±íµ¥¹ØÁªµÄËùÓÐ×Ö¶ÎÐÅÏ¢µÄÊý¾Ý
				String[][] fieldArr =cst.getPageField(pageId);
				if(fieldArr!=null){
		      //±éÀúËùÓÐ×Ö¶Î£¬»ñµÃ×Ö¶ÎµÄÒ³ÃæÏÔÊ¾½Å±¾
					for(int i=0;i<fieldArr.length;i++){
					//System.out.println("a:"+fieldArr[i][0]+"         b:"+fieldArr[i][1]);

						if(fieldArr[i][0]!=null && (!fieldArr[i][0].trim().equals(""))){
							out.print(cst.getHTML(fieldArr[i][0],hide_Field,domainId,fieldArr[i][1]));
						}
					}
				}

        //»ñµÃ±íµ¥¹ØÁªµÄËùÓÐ×Ó±í×Ö¶Î
        String[][] forFieldArr =cst.getForeignField(pageId);
				if(forFieldArr!=null){
		        //±éÀúËùÓÐ×Ö¶Î£¬»ñµÃ×Ö¶ÎµÄÒ³ÃæÏÔÊ¾½Å±¾
						for(int i=0;i<forFieldArr.length;i++){
							if(forFieldArr[i][2]!=null && (!forFieldArr[i][2].trim().equals(""))){
								out.print(cst.getHTML(forFieldArr[i][2],hide_Field,domainId,forFieldArr[i][3]));
							}
					  }
         }

         //start
         //»ñµÃ±íµ¥¹ØÁªµÄËùÓÐ×Ó±íÊý×é
         String[] tables = cst.getForeignTable(pageId);
         if (tables != null && tables.length > 0) {
              //±éÀúËùÓÐ×Ó±í
              for (int i = 0; i < tables.length; i++) {
                  if (tables[i] == null || tables[i].trim().length() < 1)continue;
                  if(i==0){//½«µÚÒ»¸ö×Ó±íÉèÖÃÎªÔö¼ÓÐÐ¡¢É¾³ýÐÐÍ¼±êËùÔÚµÄ½¹µã
                  	scriptappend = "currentRow = document.getElementsByName('"+tables[i]+"TR')[document.getElementsByName('"+tables[i]+"TR').length-1];setAbsolute(currentRow);";
                  }
                  //ÉèÖÃÍ³¼Æ×Ö¶Î
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
     //}else{
     	if(!isNewForm){
     	  //×ÓÁ÷³Ì±íµ¥ÓëÖ÷Á÷³Ì±íµ¥ÓÃµÄÊÇÍ¬Ò»ÕÅÊý¾Ý±í£¬È¡Ö÷Á÷³ÌÖÐµÄÊý¾ÝÌî³ä±íµ¥

     	  pageId=pareFormId;

     	  //µ±Ç°±íµ¥Îª±à¼­±íµ¥£¨±íµ¥ÒÑÓÐ¶ÔÓ¦µÄÊý¾Ý£©
		 //System.out.println("ÐÂÔö×ÓÁ÷³ÌµÄ¼Ì³ÐÖ÷Á÷³ÌÊý¾Ý.........................................................");
     String forHideField = "";//×Ö±í²»¿É±à¼­×Ö¶Î

     if("1".equals(request.getParameter("flag"))) hide_Field="ALL";


		 //ËùÓÐ×Ö¶Î²»¿É±à¼­£¬¸ÃÖÖÇé¿ö±íÊ¾ÔÚ×Ô¶¨ÒåÄ£¿éÖÐµã»÷Á´½Ó²é¿´ÐÅÏ¢
     fieldArr = cst.getPageField(pageId);//¶ÁÈ¡±íµ¥ÄÇ¹ØÁªµÄËùÓÐÖ÷±í×Ö¶ÎÐÅÏ¢
     if(fieldArr!=null){
				 for(int i=0;i<fieldArr.length;i++){
				    //±éÀúËùÓÐÖ÷±í×Ö¶Î
				    //System.out.println(fieldArr[i][0]+"   :"+fieldArr[i][1]);
						if(fieldArr[i][0]!=null && (!fieldArr[i][0].trim().equals(""))){
		            //»ñµÃÖ÷±í×Ö¶ÎµÄÏÔÊ¾½Å±¾
							  out.print(cst.getEditHTML(fieldArr[i][0],request.getParameter("pareRecordId"),pageId,hide_Field,domainId,fieldArr[i][1]));
						}
				 }
		 }


     //start
     //»ñµÃ±íµ¥ÄÇ¹ØÁªµÄËùÓÐ×Ó±í
     tables = cst.getForeignTable(pageId);
     //System.out.println("talbe:"+tables.length);
     if (tables != null && tables.length > 0) {
          for (int i = 0; i < tables.length; i++) {
              //±éÀúËùÓÐ×Ó±í
              if (tables[i] == null || tables[i].trim().length() < 1)continue;
              //ÌáÈ¡×Ö±í¹ØÁªµÄËùÓÐ×Ö¶Î
              String[][] forFields = cst.getForeignFieldList(pageId,tables[i],domainId);
              //System.out.println("forFields:"+forFields.length);
              //Æ´×°SQL
              String sql = "";//²éÑ¯×Ó±í¹ØÁªµ½±íµ¥ÖÐµÄËùÓÐ×Ö¶ÎÖµµÄSQLÓï¾ä
              if(forFields!=null && forFields.length>0){
				           boolean hideTable = true;//×Ó±íÊÇ·ñÓÐ¿ÉÐ´×Ö¶Î
                   for(int k=0;k<forFields.length;k++){
                       //±éÀúËùÓÐ×Ö¶Î
                       sql += forFields[k][5]+",";//½«×Ö¶ÎÌí¼Óµ½Êý¾Ý²éÑ¯µÄSQLÖÐ
											 if(forHideField.indexOf(forFields[k][5])<0){
											    //Èç¹û¸Ã×Ó±íÖÐÓÐ¿É±à¼­×Ö¶Î£¬Ôò¸Ã±í¿É±à¼­
													hideTable = false;
											 }
                   }
                   //Éú³É×Ó±íÊÇ·ñ¿É±à¼­µÄHTMLÔªËØ£¬ÓÃÓÚÊý¾Ý¸üÐÂ¡¢Ôö¼ÓÉ¾³ýÐÐµÄ¿ØÖÆ
                   if(hideTable){
                            hideTableHTML += "<input type=hidden name=hideTable id=hideTable value="+tables[i]+">";
                    }else{
                            showTableHTML += "<input type=hidden name=showTable id=showTable value="+tables[i]+">";
                    }
                    sql = sql.substring(0,sql.length()-1);
              }
              //System.out.println("sql:"+sql);
              //ÌáÈ¡×Ó±íÊý¾Ý
              String[][] data = null;
              if(sql.length()>1){
                  	sql = "select " + sql + "," + tables[i] +"_id from " + tables[i] +" where " + tables[i]+"_FOREIGNKEY="+request.getParameter("pareRecordId");
              	data = cst.loadDataBySQL(sql,String.valueOf(forFields.length+1));
              }else{
              	continue;
              }
              //ÉèÖÃÒ³Ãæ×Ó±íÐÐÊý
	            if(data!=null && data.length>0){
	                	for(int n=0;n<data.length-1;n++){//±éÀúËùÓÐÊý¾Ý£¬½âÎöÊý¾ÝÐÐ£¬¸ù¾Ý¼ÇÂ¼ÊýÁ¿Ôö¼ÓÐÐ
	                        	String script = "currentRow = document.getElementsByName('"+tables[i]+"TR')[document.getElementsByName('"+tables[i]+"TR').length-1];addRow();";
	                        	out.print(script);
	                    }
	                    for(int n=0;n<data.length;n++){//±éÀúÊý¾Ý£¬½âÎöÊý¾ÝÔÚ±íµ¥ÉÏµÄÕ¹Ê¾
	                            //½âÎöÊý¾Ý
	                            for(int m=0;m<forFields.length;m++){//±éÀú×Ö¶Î£¬Éú³É¸Ã×Ö¶Î¼°ÆäÖµÕ¹Ê¾ÔÚ±íµ¥ÉÏµÄ½Å±¾
	                                out.print(reuquest,cst.getForeignEditHTML(forFields[m][5],data[n][m],forFields,"",m,String.valueOf(n),forFields[m][0]));
	                            }
	                            String script = "document.getElementsByName('"+forFields[0][0]+"-"+forFields[0][5]+"')["+String.valueOf(n)+"].innerHTML+='<input type=hidden id="+tables[i]+"id name="+tables[i]+"id value="+data[n][forFields.length]+">';";
	                           out.print(script);
	                    }
	            }else{//¸Ã×Ó±íÃ»ÓÐÊý¾Ý£¬Òþ²ØÄ£°åÖÐ³õÊ¼»¯µÄµÚÒ»¸öÊý¾ÝÐÐ
	                for(int m=0;m<forFields.length;m++){
	                        out.print(reuquest,cst.getForeignEditHTML(forFields[m][5],"",forFields,(("0".equals(request.getParameter("workStatus"))||"-1".equals(request.getParameter("workStatus"))||"-2".equals(request.getParameter("workStatus")))?hide_Field+forHideField:"ALL"),m,"0",forFields[m][0]));
	                }
	                out.print("document.getElementsByName('"+tables[i]+"TR')[0].style.display='none';");

	            }
              //ÉèÖÃÍ³¼Æ×Ö¶Î
              String[][] tlFlds = cst.getTotalFieldByTableName(tables[i],domainId);
              if(tlFlds !=null && tlFlds.length>0){
                  	//Ôö¼ÓÍ³¼Æ×Ö¶ÎÏÔÊ¾ÐÐ
              	    out.print("addTotalRow('"+tables[i]+"');");
                    String tlfld = "";
                    for(int p=0;p<tlFlds.length;p++){//±éÀúÍ³¼Æ×Ö¶Î£¬ÉèÖÃ¶ÔÓ¦µÄÍ³¼Æ×Ö¶Î±íµ¥ÔªËØ
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
		 //µ±Ç°±íµ¥Îª±à¼­±íµ¥£¨±íµ¥ÒÑÓÐ¶ÔÓ¦µÄÊý¾Ý£©
		 //System.out.println("±à¼­±íµ¥.........................................................");
     String forHideField = "";//×Ö±í²»¿É±à¼­×Ö¶Î
     if(request.getParameter("processId")!=null){
          //±íµ¥ÔÚÁ÷³ÌÖÐµ÷ÓÃ
          com.js.oa.jsflow.service.WorkFlowBD workFlowBD = new com.js.oa.jsflow.service.WorkFlowBD();
          //È¡¶Á×Ö¶Î
        	String writeFieldString = "";//¿ÉÐ´×Ö¶Î´® $×Ö¶ÎÃû³Æ$$×Ö¶ÎÃû³Æ$
        	if("0".equals(request.getParameter("workStatus"))){
        	    //ÊÇ´ý°ìÎÄ¼þ
        	    //È¡µ±Ç°»î¶¯µÄ¿ÉÐ´×Ö¶ÎList
              java.util.List list = workFlowBD.getRWList(request.getParameter("activity"), request.getParameter("table"), request.getParameter("record"), "1");
              String[] str = null;
              for(int i = 0; i < list.size(); i ++){//±éÀú¿ÉÐ´×Ö¶Î£¬½«¿ÉÐ´×Ö¶Î×ª»¯Îª×Ö·û´®ÐÎÊ½
                	str = (String[]) list.get(i);
                	if(str[0].equals("1")){//¿ÉÐ´×Ö¶Î
                    	writeFieldString += "$" + str[1] + "$";
               	 	}
            	}
              //±íµ¥ÖÐ¹ØÁªµÄËùÓÐ×Ó±í×Ö¶ÎÐÅÏ¢
              String[][] forFieldArr = new com.js.oa.eform.service.CustomFormBD().getForeignField(pageId);
              if(forFieldArr!=null){
              		for(int i=0;i<forFieldArr.length;i++){
              		    //±éÀú×óÓÒ×Ó±í×Ö¶Î£¬Èç¹û×Ö¶Î²»ÔÚ¿ÉÐ´×Ö¶ÎÖÐ£¬Ôò×Ö¶ÎÎª²»¿É±à¼­×Ö¶Î
                  		if(writeFieldString.indexOf(forFieldArr[i][2])<0){
                          		forHideField += forFieldArr[i][2]+",";
                  		}
             		  }
        		  }
        	}

     }

		 if("1".equals(request.getParameter("flag"))) hide_Field="ALL";
		 //ËùÓÐ×Ö¶Î²»¿É±à¼­£¬¸ÃÖÖÇé¿ö±íÊ¾ÔÚ×Ô¶¨ÒåÄ£¿éÖÐµã»÷Á´½Ó²é¿´ÐÅÏ¢
     String[][] fieldArr = cst.getPageField(pageId);//¶ÁÈ¡±íµ¥ÄÇ¹ØÁªµÄËùÓÐÖ÷±í×Ö¶ÎÐÅÏ¢
     if(fieldArr!=null){
				 for(int i=0;i<fieldArr.length;i++){
				    //±éÀúËùÓÐÖ÷±í×Ö¶Î
						if(fieldArr[i][0]!=null && (!fieldArr[i][0].trim().equals(""))){
		            //»ñµÃÖ÷±í×Ö¶ÎµÄÏÔÊ¾½Å±¾
							  out.print(cst.getEditHTML(fieldArr[i][0],request.getAttribute("infoId").toString(),pageId,hide_Field,domainId,fieldArr[i][1]));
						}
				 }
		 }

     //start
     //»ñµÃ±íµ¥ÄÇ¹ØÁªµÄËùÓÐ×Ó±í
     String[] tables = cst.getForeignTable(pageId);
     if (tables != null && tables.length > 0) {
          for (int i = 0; i < tables.length; i++) {
              //±éÀúËùÓÐ×Ó±í
              if (tables[i] == null || tables[i].trim().length() < 1)continue;
              //ÌáÈ¡×Ö±í¹ØÁªµÄËùÓÐ×Ö¶Î
              String[][] forFields = cst.getForeignFieldList(pageId,tables[i],domainId);
              //Æ´×°SQL
              String sql = "";//²éÑ¯×Ó±í¹ØÁªµ½±íµ¥ÖÐµÄËùÓÐ×Ö¶ÎÖµµÄSQLÓï¾ä
              if(forFields!=null && forFields.length>0){
				           boolean hideTable = true;//×Ó±íÊÇ·ñÓÐ¿ÉÐ´×Ö¶Î
                   for(int k=0;k<forFields.length;k++){
                       //±éÀúËùÓÐ×Ö¶Î
                       sql += forFields[k][5]+",";//½«×Ö¶ÎÌí¼Óµ½Êý¾Ý²éÑ¯µÄSQLÖÐ
											 if(forHideField.indexOf(forFields[k][5])<0){
											    //Èç¹û¸Ã×Ó±íÖÐÓÐ¿É±à¼­×Ö¶Î£¬Ôò¸Ã±í¿É±à¼­
													hideTable = false;
											 }
                   }
                   //Éú³É×Ó±íÊÇ·ñ¿É±à¼­µÄHTMLÔªËØ£¬ÓÃÓÚÊý¾Ý¸üÐÂ¡¢Ôö¼ÓÉ¾³ýÐÐµÄ¿ØÖÆ
                   if(hideTable){
                            hideTableHTML += "<input type=hidden name=hideTable id=hideTable value="+tables[i]+">";
                    }else{
                            showTableHTML += "<input type=hidden name=showTable id=showTable value="+tables[i]+">";
                    }
                    sql = sql.substring(0,sql.length()-1);
              }
              //ÌáÈ¡×Ó±íÊý¾Ý
              String[][] data = null;
              if(sql.length()>1){
                  	sql = "select " + sql + "," + tables[i] +"_id from " + tables[i] +" where " + tables[i]+"_FOREIGNKEY="+request.getAttribute("infoId").toString();
              	data = cst.loadDataBySQL(sql,String.valueOf(forFields.length+1));
              }else{
              	continue;
              }
              //ÉèÖÃÒ³Ãæ×Ó±íÐÐÊý
	            if(data!=null && data.length>0){
	                	for(int n=0;n<data.length-1;n++){//±éÀúËùÓÐÊý¾Ý£¬½âÎöÊý¾ÝÐÐ£¬¸ù¾Ý¼ÇÂ¼ÊýÁ¿Ôö¼ÓÐÐ
	                        	String script = "currentRow = document.getElementsByName('"+tables[i]+"TR')[document.getElementsByName('"+tables[i]+"TR').length-1];addRow();";
	                        	out.print(script);
	                    }
	                    for(int n=0;n<data.length;n++){//±éÀúÊý¾Ý£¬½âÎöÊý¾ÝÔÚ±íµ¥ÉÏµÄÕ¹Ê¾
	                            //½âÎöÊý¾Ý
	                            for(int m=0;m<forFields.length;m++){//±éÀú×Ö¶Î£¬Éú³É¸Ã×Ö¶Î¼°ÆäÖµÕ¹Ê¾ÔÚ±íµ¥ÉÏµÄ½Å±¾
	                                out.print(reuquest,cst.getForeignEditHTML(reuquest,forFields[m][5],data[n][m],forFields,(("0".equals(request.getParameter("workStatus"))||"-1".equals(request.getParameter("workStatus"))||"-2".equals(request.getParameter("workStatus")))?hide_Field+forHideField:"ALL"),m,String.valueOf(n),forFields[m][0]));
	                            }
	                            String script = "document.getElementsByName('"+forFields[0][0]+"-"+forFields[0][5]+"')["+String.valueOf(n)+"].innerHTML+='<input type=hidden id="+tables[i]+"id name="+tables[i]+"id value="+data[n][forFields.length]+">';";
	                           out.print(script);
	                    }
	            }else{//¸Ã×Ó±íÃ»ÓÐÊý¾Ý£¬Òþ²ØÄ£°åÖÐ³õÊ¼»¯µÄµÚÒ»¸öÊý¾ÝÐÐ
	                for(int m=0;m<forFields.length;m++){
	                        out.print(reuquest,cst.getForeignEditHTML(reuquest,forFields[m][5],"",forFields,(("0".equals(request.getParameter("workStatus"))||"-1".equals(request.getParameter("workStatus"))||"-2".equals(request.getParameter("workStatus")))?hide_Field+forHideField:"ALL"),m,"0",forFields[m][0]));
	                }
	                out.print("document.getElementsByName('"+tables[i]+"TR')[0].style.display='none';");

	            }
              //ÉèÖÃÍ³¼Æ×Ö¶Î
              String[][] tlFlds = cst.getTotalFieldByTableName(tables[i],domainId);
              if(tlFlds !=null && tlFlds.length>0){
                  	//Ôö¼ÓÍ³¼Æ×Ö¶ÎÏÔÊ¾ÐÐ
              	    out.print("addTotalRow('"+tables[i]+"');");
                    String tlfld = "";
                    for(int p=0;p<tlFlds.length;p++){//±éÀúÍ³¼Æ×Ö¶Î£¬ÉèÖÃ¶ÔÓ¦µÄÍ³¼Æ×Ö¶Î±íµ¥ÔªËØ
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

	//×Ó±íµÄ¼ÆËã×Ö¶ÎÉèÖÃ
  out.print(cst.getForeignComputeFieldHTML(pageId));
	out.print(hideTableHTML+showTableHTML);

      out.write("\r\n\r\n<script language=javascript src=\"/jsoa/eform/datetime/time.js\"></script>\r\n<script language=\"javascript\">\r\n\r\nif(document.all.wf_ua){\r\n    if(document.all.wf_ua.length){\r\n    }else{\r\n        eval(\"document.all.\" + document.all.wf_ua.value + \".value='");
      out.print(session.getAttribute("userAccount"));
      out.write("'\");\r\n    }\r\n}\r\n</script>\r\n\r\n<DIV id=adddelrow_div\r\nstyle=\"BORDER-RIGHT: #0a246a 1px solid; BORDER-TOP: #0a246a 1px solid; VISIBILITY: hidden; BORDER-LEFT: #0a246a 1px solid; WIDTH: 30px; BORDER-BOTTOM: #0a246a 1px solid; POSITION: absolute\">\r\n<TABLE height=\"100%\" cellSpacing=0 cellPadding=0 width=\"100%\" border=0>\r\n  <TBODY>\r\n  <TR>\r\n    <TD onmouseover=\"this.style.borderRiht='1px #0A246A solid'\"\r\n    onmouseout=\"this.style.borderRiht=''\" align=middle><SPAN id=delrow_div\r\n      title=µã»÷É¾³ý¼ÇÂ¼ style=\"CURSOR: hand\"><IMG height=12\r\n      src=\"/jsoa/custom_form/images/delarrow.gif\" width=12 onclick='delRow()' align=absMiddle></SPAN> </TD>\r\n    <TD onmouseover=\"this.style.borderLeft='1px #0A246A solid'\"\r\n    onmouseout=\"this.style.borderLeft=''\" align=middle><SPAN id=addrow_div\r\n      title=µã»÷Ìí¼Ó¼ÇÂ¼ style=\"CURSOR: hand\"><IMG height=12\r\n      src=\"/jsoa/custom_form/images/addarrow.gif\" width=12 onclick='addRow()' align=absMiddle></SPAN>\r\n  </TD></TR></TBODY></TABLE></DIV>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n<!--\r\n");
      out.write("//document.body.attachEvent(\"onload\",function(){});\r\n//alert(document.body.readyState);\r\nsetNoWrap();\r\ninitRow();\r\n//-->\r\n</SCRIPT>\r\n<script >\r\n\t");
//=scriptappend
      out.write("\r\n        ");
/*
        	if(scriptappend.length()>0){
                	out.print("document.body.attachEvent('onload',function(){setAbsolute(currentRow);});");
                }*/
        
      out.write("\r\n        //alert(document.body.onload);\r\n\r\nfunction compute_filed_auto(){\r\n\tif(document.all.computeField) {\r\n        //ÓÐ¼ÆËã×Ö¶Î\r\n        if(document.all.computeField.length) {\r\n            //²»Ö¹Ò»¸ö¼ÆËã×Ö¶Î\r\n            for(var i = 0; i < document.all.computeField.length.value; i ++){\r\n                //setCompleteField(document.all.computeFieldValue[i], document.all.computeField[i].value);\r\n\t\t\t\tvar computeField = document.all.computeField[i].value;\r\n                var computeFieldValue = document.all.computeFieldValue[i].value;\r\n\t\t\t\teval(\"document.all.\"+computeField).value = getComputeValue(computeFieldValue);\r\n\t\t\t\tif(eval(\"document.all.\"+computeField+\"_cmp\")){\r\n\t\t\t\t\teval(\"document.all.\"+computeField+\"_cmp\").value = getComputeValue(computeFieldValue);\r\n\t\t\t\t}\r\n            }\r\n        }else{\r\n            //Ö»ÓÐÒ»¸ö¼ÆËã×Ö¶Î\r\n            //setCompleteField(document.all.computeFieldValue, document.all.computeField.value);\r\n\t\t\tvar computeField = document.all.computeField.value;\r\n\t\t\tvar computeFieldValue = document.all.computeFieldValue.value;\r\n");
      out.write("\t\t\teval(\"document.all.\"+computeField).value = getComputeValue(computeFieldValue);\r\n\t\t\tif(eval(\"document.all.\"+computeField+\"_cmp\")){\r\n\t\t\t\teval(\"document.all.\"+computeField+\"_cmp\").value = getComputeValue(computeFieldValue);\r\n\t\t\t}\r\n        }\r\n    }\r\n}\r\n</script>\r\n");
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
