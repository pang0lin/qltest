/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:59:09 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.weixin.workflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.system.util.StaticParam;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.sql.*;
import java.sql.*;
import com.js.util.util.BrowserJudge;

public final class wf_005fstepanduser_005forgselfsendforweixin_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/weixin/public/onlineForWeiXin.jsp", Long.valueOf(1499751472000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("java.sql");
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.sql");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.system.util.StaticParam");
    _jspx_imports_classes.add("java.text.SimpleDateFormat");
    _jspx_imports_classes.add("com.js.util.util.BrowserJudge");
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

      out.write("\r\n\r\n  \r\n\r\n\r\n\r\n\r\n");
      out.write("\r\n<script language=\"JScript.Encode\" src=\"/jsoa/js/browinfo.js\"></script>\r\n<script language=\"JScript.Encode\" src=\"/jsoa/js/rtxint.js\"></script>\r\n<script language=\"javascript\">\r\nfunction rtxonline(a){\r\n\t RAP(a);\r\n}\r\n</script>");
      out.write("\r\n\r\n");

request.setCharacterEncoding("utf-8");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
int menuIndex=0;

DataSource ds = new com.js.util.util.DataSourceBase().getDataSource();
Connection conn=null;
Statement stmt=null;
ResultSet rs=null;
conn=ds.getConnection();
stmt=conn.createStatement();

boolean bflag=false;
if(BrowserJudge.isMSIE(request)){
bflag=true;
}



      out.write("\r\n\r\n");

//??????????????????
String processId = request.getParameter("processId");
String tableId = request.getParameter("tableId");
String recordId = request.getParameter("recordId");
String activityId = request.getParameter("activityId");//????????????
String curActivityId = request.getParameter("curActivityId");//????????????

String standForUserId = request.getParameter("standForUserId");//?????????ID

com.js.oa.jsflow.service.WorkFlowButtonBD workFlowButtonBD = new com.js.oa.jsflow.service.WorkFlowButtonBD();
com.js.oa.jsflow.service.WorkFlowBD workFlowBD = new com.js.oa.jsflow.service.WorkFlowBD();
com.js.oa.jsflow.vo.WorkVO vo = new com.js.oa.jsflow.vo.WorkVO();

//System.out.println("??????????????????????????????:"+request.getHeader("User-Agent"));
//System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"  ???????????????processId:"+processId+"    tableId:"+tableId+"   recordId:"+recordId+"   activityId(????????????):"+activityId+"   curActivityId:"+curActivityId);

//???????????????
//0 ??????????????????????????????
//1 ????????????????????????????????????????????????
//2 ????????????????????????
//3 ?????????????????????
//4 ????????????????????????????????????
String[] nextUser = workFlowBD.getProcActiUser(tableId,recordId,curActivityId);

String selectUser = "";//????????????
String selectUserName = "";//????????????
String selectUserAccounts = "";//????????????

String range = "*0*";//????????????
String show = "";//??????

//
String participantUserField = "";//???????????????????????????

//??????
boolean showSmsRemind = false;



//?????????????????????
int participantType=-1;
if(nextUser[14]!=null){
	participantType=Integer.parseInt(nextUser[14]);
}


//????????????
if(nextUser[24]!=null){
	showSmsRemind = nextUser[24].toString().equals("1")?true:false;
}

if(null != nextUser[14]){
	if(0 == participantType){
		//?????????????????????????????? 
		java.util.List leaderList = workFlowBD.getLeaderList(request.getParameter("submitPersonId"));

		for(int i=0;i<leaderList.size();i++){
			Object[] tmp = (Object[])leaderList.get(i); 
			selectUser += tmp[0]+",";
			selectUserName += tmp[1]+",";
		}
		if(selectUser.indexOf(",")!=-1){
			selectUser = selectUser.substring(0,selectUser.length()-1);
			selectUserName = selectUserName.substring(0,selectUserName.length()-1);
		}
	} else if(1 == participantType){
		//????????????????????????????????????????????????
		// show += "user";
		// ????????????????????????????????????????????????*0*???   ????????????????????????
		range = "*0*";
		show="userorggroup";
	} else if(2 == participantType){
		//?????????????????????
		java.util.List candidate = workFlowBD.getCandidate(nextUser[15],nextUser[16]);
		for(int i=0;i<candidate.size();i++){
			Object[] tmp = (Object[])candidate.get(i); 
			selectUser += tmp[0]+",";
			selectUserName += tmp[1]+",";
		}
		if(selectUser.indexOf(",")!=-1){
			selectUser = selectUser.substring(0,selectUser.length()-1);
			selectUserName = selectUserName.substring(0,selectUserName.length()-1);
		}
	} else if(3 == participantType){
		//?????????????????????
		java.util.List candidate = workFlowBD.getCandidate(nextUser[15],nextUser[16]);
		for(int i=0;i<candidate.size();i++){
			Object[] tmp = (Object[])candidate.get(i); 
			selectUser += tmp[0]+",";
			selectUserName += tmp[1]+",";
		}
		if(selectUser.indexOf(",")!=-1){
			selectUser = selectUser.substring(0,selectUser.length()-1);
			selectUserName = selectUserName.substring(0,selectUserName.length()-1);
		}
	} else if(4 == participantType){
		//????????????????????????????????????
		participantUserField = nextUser[17];
		participantUserField = workFlowButtonBD.getFieldInfoByFieldId(participantUserField);
	} else if(5 == participantType){
		//???????????????
		selectUser = request.getParameter("submitPersonId");
		selectUserName = new com.js.system.service.usermanager.UserBD().getUserNameById(request.getParameter("submitPersonId"));
	} else if(6 == participantType){
		//?????????????????? 
		//???????????????????????????????????????????????????????????????????????????????????????
		String currentUserId=session.getAttribute("userId").toString();
		
		if(standForUserId==null || "null".equals(standForUserId) || "0".equals(standForUserId)){
			String tranFromPersonId="";
			if(request.getParameter("tranFromPersonId")!=null && !"null".equals(request.getParameter("tranFromPersonId")) && !"".equals(request.getParameter("tranFromPersonId")) && !"-1".equals(request.getParameter("tranFromPersonId"))){
		        tranFromPersonId=request.getParameter("tranFromPersonId");			
			
				//????????????????????????????????????
				String tranFromPersonIdTemp=tranFromPersonId;
		        while(!"".equals(tranFromPersonId)){                	
		        	rs=stmt.executeQuery("select tranfrompersonid from jsf_work where wf_curemployee_id="+tranFromPersonId+" and initactivity="+curActivityId+" and workrecord_id="+recordId+" and worktable_id="+tableId);
		        	if(rs.next()){
		        		tranFromPersonId=rs.getString(1);
		        	}
		        	rs.close();
		        	
		        	if(tranFromPersonId==null || "null".equals(tranFromPersonId)){
		        		tranFromPersonId="";
		        	}else{
		        		tranFromPersonIdTemp=tranFromPersonId;
		        	}                	
		        }
		        tranFromPersonId=tranFromPersonIdTemp;
			}
			
			//????????????
			if(!"".equals(tranFromPersonId)){
				currentUserId = tranFromPersonId;
			}
		} else{
			//?????????????????????
			currentUserId=standForUserId;
		}
		java.util.List candidate = workFlowBD.getRoleUserIDAndName(nextUser[20], request.getParameter("submitPersonId")+";"+currentUserId);
		for(int i=0;i<candidate.size();i++){
			Object[] tmp = (Object[])candidate.get(i); 
			selectUser += tmp[0]+",";
			selectUserName += tmp[1]+",";
		}
		if(selectUser.indexOf(",")!=-1){
			selectUser = selectUser.substring(0,selectUser.length()-1);
			selectUserName = selectUserName.substring(0,selectUserName.length()-1);
		}
	} else if(7 == participantType){
		//????????????????????????????????????
		java.util.List leaderList = null;
		
		//???????????????????????????????????????????????????????????????????????????????????????
		if(standForUserId==null || "null".equals(standForUserId) || "0".equals(standForUserId)){
			String tranFromPersonId="";
			if(request.getParameter("tranFromPersonId")!=null && !"null".equals(request.getParameter("tranFromPersonId")) && !"".equals(request.getParameter("tranFromPersonId")) && !"-1".equals(request.getParameter("tranFromPersonId"))){
		        tranFromPersonId=request.getParameter("tranFromPersonId");			
			
				//????????????????????????????????????
				String tranFromPersonIdTemp=tranFromPersonId;
		        while(!"".equals(tranFromPersonId)){                	
		        	rs=stmt.executeQuery("select tranfrompersonid from jsf_work where wf_curemployee_id="+tranFromPersonId+" and initactivity="+curActivityId+" and workrecord_id="+recordId+" and worktable_id="+tableId);
		        	if(rs.next()){
		        		tranFromPersonId=rs.getString(1);
		        	}
		        	rs.close();
		        	
		        	if(tranFromPersonId==null || "null".equals(tranFromPersonId)){
		        		tranFromPersonId="";
		        	}else{
		        		tranFromPersonIdTemp=tranFromPersonId;
		        	}                	
		        }
		        
		        tranFromPersonId=tranFromPersonIdTemp;    
			}
			if(null == request.getParameter("yj")){	// ???????????????????????????????????????????????????????????????
				// ???????????????????????????ID
				String upEmpId = workFlowBD.getUpActivityUser(processId, tableId, recordId, curActivityId);
				if("".equals(upEmpId)){	// ????????????????????????????????????????????????????????????
					upEmpId = request.getParameter("submitPersonId");
				}
				leaderList = workFlowBD.getLeaderList(upEmpId);
			} else{
				//????????????
				if(!"".equals(tranFromPersonId)){
					leaderList = workFlowBD.getLeaderList(tranFromPersonId);
				}else{
					leaderList = workFlowBD.getLeaderList(session.getAttribute("userId").toString());
				}
			}
		} else{
			if(null == request.getParameter("yj")){	// ???????????????????????????????????????????????????????????????
				// ???????????????????????????ID
				String upEmpId = workFlowBD.getUpActivityUser(processId, tableId, recordId, curActivityId);
				if("".equals(upEmpId)){	// ????????????????????????????????????????????????????????????
					upEmpId = request.getParameter("submitPersonId");
				}
				leaderList = workFlowBD.getLeaderList(upEmpId);
			} else{
				//?????????????????????
				leaderList = workFlowBD.getLeaderList(standForUserId);
			}
		}
		/*
		if("0".equals(standForUserId)){
			leaderList = workFlowBD.getLeaderList(session.getAttribute("userId").toString());
		} else{
			leaderList = workFlowBD.getLeaderList(standForUserId);
		}
		*/
		for(int i=0;i<leaderList.size();i++){
			Object[] tmp = (Object[])leaderList.get(i); 
			selectUser += tmp[0]+",";
			selectUserName += tmp[1]+",";
		}
		if(selectUser.indexOf(",")!=-1){
			selectUser = selectUser.substring(0,selectUser.length()-1);
			selectUserName = selectUserName.substring(0,selectUserName.length()-1);
		}
	} else if(8 == participantType){
		//????????????????????????
		range = nextUser[23].toString();
		show="user";
		if(range != null && !range.equals("") && range.indexOf("@") >= 0){
			show+="group";
		}
		if(range != null && !range.equals("") && range.indexOf("*") >= 0){
			show+="org";
		}
	} else if(9 == participantType){
		//????????????????????????????????????????????????
		range = "*"+session.getAttribute("orgId")+"*";
		show += "user";
	} else if(10 == participantType){
		if(null == request.getParameter("yj")){	// ?????????????????????ID?????????ID
			String upEmpId = workFlowBD.getUpActivityUser(processId, tableId, recordId, curActivityId);
			if("".equals(upEmpId)){	// ????????????????????????????????????????????????????????????
				upEmpId = request.getParameter("submitPersonId");
			}
			range = "*" + StaticParam.getOrgIdByEmpId(upEmpId) + "*";
		} else{
			//????????????
			range = "*"+session.getAttribute("orgId")+"*";
		}
		show += "org";
	} else if(11 == participantType){
		//???????????????????????????
		/*vo.setProcessId(Long.valueOf(processId));
		vo.setTableId(Long.valueOf(tableId));
		vo.setRecordId(Long.valueOf(recordId));
		vo.setActivity(Long.valueOf(curActivityId));
		List candidate = workFlowButtonBD.getFrontActivityOper(vo);
		*/
		List candidate = null;
		if(null == request.getParameter("yj")){	// ?????????????????????????????????
			candidate = workFlowBD.getActivityUsers(processId, tableId, recordId, curActivityId, "0");
		} else{
			candidate = workFlowBD.getActivityUsers(processId, tableId, recordId, curActivityId, "0");
		}

		if(candidate.size() > 0){
			for(int i=0;i<candidate.size();i++){
				Object[] tmp = (Object[])candidate.get(i); 
				selectUser += tmp[0]+",";
				selectUserName += tmp[1]+",";
			}
			if(selectUser.indexOf(",")!=-1){
				selectUser = selectUser.substring(0,selectUser.length()-1);
				selectUserName = selectUserName.substring(0,selectUserName.length()-1);
			}
		} else{	// ?????????????????????????????????
			selectUser = request.getParameter("submitPersonId");
			selectUserName = StaticParam.getEmpNameByEmpId(request.getParameter("submitPersonId"));
		}
	} else if(12 == participantType){
		//????????????????????????????????????????????????
		show += "org";
	} else if(13 == participantType){
		//????????????????????????????????????????????????
		//show += "group";
		range = nextUser[23].toString();
		if(range != null){
			show = "usergroup";
		}
	} else if(14 == participantType){
		//??????????????????????????? ?????? ????????????
		List candidate = workFlowButtonBD.getLeaderByDutyLevelAndOrg(request.getParameter("submitPersonId"),nextUser[18]);
		for(int i=0;i<candidate.size();i++){
			Object[] tmp = (Object[])candidate.get(i); 
			selectUser += tmp[0]+",";
			selectUserName += tmp[1]+",";
		}
		if(selectUser.indexOf(",")!=-1){
			selectUser = selectUser.substring(0,selectUser.length()-1);
			selectUserName = selectUserName.substring(0,selectUserName.length()-1);
		}
	} else if(15 == participantType){
		//?????????????????? 
		//???????????????????????????????????????????????????????????????????????????????????????
		String currentUserId=session.getAttribute("userId").toString();
		
		if(standForUserId==null || "null".equals(standForUserId) || "0".equals(standForUserId)){
			String tranFromPersonId="";
			if(request.getParameter("tranFromPersonId")!=null && !"null".equals(request.getParameter("tranFromPersonId")) && !"".equals(request.getParameter("tranFromPersonId")) && !"-1".equals(request.getParameter("tranFromPersonId"))){
		        tranFromPersonId=request.getParameter("tranFromPersonId");			
			
				//????????????????????????????????????
				String tranFromPersonIdTemp=tranFromPersonId;
		        while(!"".equals(tranFromPersonId)){                	
		        	rs=stmt.executeQuery("select tranfrompersonid from jsf_work where wf_curemployee_id="+tranFromPersonId+" and initactivity="+curActivityId+" and workrecord_id="+recordId+" and worktable_id="+tableId);
		        	if(rs.next()){
		        		tranFromPersonId=rs.getString(1);
		        	}
		        	rs.close();
		        	
		        	if(tranFromPersonId==null || "null".equals(tranFromPersonId)){
		        		tranFromPersonId="";
		        	}else{
		        		tranFromPersonIdTemp=tranFromPersonId;
		        	}                	
		        }
		        
		        tranFromPersonId=tranFromPersonIdTemp;
			}
			
			//????????????
			if(!"".equals(tranFromPersonId)){
				currentUserId = tranFromPersonId;
			}
		} else{
			//?????????????????????
			currentUserId=standForUserId;
		}
		
		java.util.List candidate = workFlowBD.getPositionUserIDAndName(nextUser[20], request.getParameter("submitPersonId")+";"+currentUserId);
		for(int i=0;i<candidate.size();i++){
			Object[] tmp = (Object[])candidate.get(i); 
			selectUser += tmp[0]+",";
			selectUserName += tmp[1]+",";
		}
		if(selectUser.indexOf(",")!=-1){
			selectUser = selectUser.substring(0,selectUser.length()-1);
			selectUserName = selectUserName.substring(0,selectUserName.length()-1);
		}
	}
}

if(!"".equals(selectUser)){
	selectUserAccounts = new com.js.system.service.usermanager.UserBD().getUserAccountByIds(selectUser);
}


      out.write("\r\n<HTML>\r\n\t<HEAD>\r\n\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n\t\t<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t\t");

		if(bflag){
			
      out.write("<SCRIPT language=javascript src=\"/jsoa/jsflow/wf_stepanduser_tree_forweixin.js\"></SCRIPT>");

		}else{
			
      out.write("<SCRIPT language=javascript src=\"/jsoa/jsflow/wf_stepanduser_tree_o_forweixin.js\"></SCRIPT>");

		}
		
      out.write("\r\n\t\t<script type=\"text/javascript\" src=\"/jsoa/js/weixin/mobiscroll/js/jquery-1.9.1.min.js\" ></script>\r\n\t\t<script type=\"text/javascript\" src=\"/jsoa/js/weixin/wf_stepanduser_tree.js\"></script>\r\n\t\t<style type=\"text/css\">\r\n\t\t.Hidden { display: none;}\r\n\t\t.Show { display: block;}\r\n\t\t</style>\r\n\t</HEAD>\r\n\r\n\t<body>\r\n\t\t<div id=\"submenuBox");
      out.print(menuIndex);
      out.write("\" style=\"width: 100%; height: 100%; overflow: auto;\">\r\n\t\t\t<div id=\"org_list\" class=\"Hidden\"></div>\r\n\t\t</div>\r\n\t</body>\r\n</html>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n// ????????????????????????div???????????????\r\nvar w = $(window).height();\r\n$(\"#org_list\").height(w);\r\n\r\nfunction HightLight(obj){\r\n\tobj.style.color = \"red\";\r\n}\r\n\r\nfunction NomalLight(obj){\r\n\tobj.style.color = \"black\";\r\n}\r\n/*\r\n//??????????????????\r\nfunction openMainFrameAll(){\r\n\tvar topTs = document.all.topTable;\r\n\tvar topT ;\r\n\tif(topTs.length){\r\n\t\ttopT = topTs[topTs.length-1];\r\n\t} else{\r\n\t\ttopT = document.all.topTable;\r\n\t}\r\n\r\n\tvar aLength = topT.getElementsByTagName(\"a\").length;\r\n\tfor(var i=0;i<aLength;i++){\r\n\t\tif(topT.getElementsByTagName(\"a\")[i]){\r\n\t\t\ttopT.getElementsByTagName(\"a\")[i].click();\r\n\t\t}\r\n\t}\r\n}\r\n\r\nfunction openMainFrame(opValue, opText){\r\n\tvar oSelect = window.parent.document.frm2.selectObject;\r\n\tvar nLen =oSelect.length;\r\n\r\n\tvar hasOpt = false;\r\n\tfor(i=0; i<nLen; i++){\r\n\t\tvar optObj = oSelect.options[i];\r\n\t\tif(optObj.value == opValue){\r\n\t\t\thasOpt = true;\r\n\t\t}\r\n\t}\r\n\r\n\t// ??????????????????\r\n");
      out.write("\tif(!hasOpt){\r\n\t\tif(window.parent.document.frm2.participantType.value == 10){\r\n\t\t\t//??????????????????\r\n\t\t\tvar response = getParentOrgLeader(opValue);\t// wf_stepanduser_tree.js\r\n\t\t\t\t\t\t\t\r\n\t\t\tif(response!=null && response.responseText!=\"\" && response.responseXML.documentElement!=null){\r\n\t\t\t\tvar root = response.responseXML.documentElement;\r\n\t\t\t\tvar message = root.getElementsByTagName(\"manager\");\r\n\t\t\t\tfor(var i=0; i<message.length; i++){\r\n\t\t\t\t\tvar oOption = document.createElement(\"OPTION\");\r\n\t\t\t\t\toSelect.options.add(oOption);\r\n\t\t\t\t\toOption.innerText = message[i].getElementsByTagName(\"empName\")[0].firstChild.nodeValue;\r\n\t\t\t\t\toOption.value = \"$\" + message[i].getElementsByTagName(\"empId\")[0].firstChild.nodeValue + \"$\";\r\n\t\t\t\t}\r\n\t\t\t}\r\n\t\t} else{\r\n\t\t\tvar oOption = document.createElement(\"OPTION\");\r\n\t\t\toSelect.options.add(oOption);\r\n\t\t\t");

			if(bflag){
				
      out.write("oOption.innerText = opText;");

			} else{
				
      out.write("oOption.textContent = opText;");

			} 
			if(BrowserJudge.isNotPc(request)){
				
      out.write("\r\n\t\t\t\tvar showNames = window.parent.document.getElementById(\"showNames2\");\r\n\t\t\t\tvar htmlDiv = \"<div id='A\" + opValue + \"A2' name='A\" + opValue \r\n\t\t\t\t\t\t+ \"A2' title='????????????' onclick=\\\"dropNameDiv2('A\" + opValue + \"A2','\" \r\n\t\t\t\t\t\t+ opValue + \"')\\\"  style='cursor:pointer;float:left;width:100%;fontSize:12px;'>\" \r\n\t\t\t\t\t\t+opText + \"</div>\";\r\n  \t        \t\tshowNames.innerHTML += htmlDiv;\r\n\t\t\t\t");

			}
			
      out.write("\r\n\t\t\toOption.value = opValue;\t\t\t\t\r\n\t\t}\t\t\t\r\n\t}\r\n}*/\r\nwindow.parent.document.frm2.participantType.value = \"");
      out.print(participantType);
      out.write("\";\r\n");

// ????????????????????????(????????????????????????????????????????????????????????????)
if(participantType==1 || participantType==8 || participantType==2  
	|| participantType==9 || participantType==10  || participantType==6 || participantType==13 
	|| participantType==0 || participantType==7 || participantType==11 || participantType==14 || participantType==15){
	
      out.write("\r\n\t//window.parent.document.getElementById(\"middleTR2\").style.display='';\r\n   //\twindow.parent.document.getElementById(\"selTextarea2\").style.display='none';\t\r\n/*\r\n\t// ??????????????????\r\n\tvar oSelect = window.parent.document.frm2.selectObject;\r\n\t\r\n\t// ??????\r\n\tvar nLen = oSelect.length;\r\n\tfor(j=0; j<nLen; j++){\r\n\t\toSelect.remove(0);\r\n\t}*/\r\n\t");

	if(BrowserJudge.isNotPc(request)){
		
      out.write("\r\n\t\tvar showNames2 = window.parent.document.getElementById(\"showNames2\");\r\n\t\tshowNames2.innerHTML = \"\";\r\n\t\t");

	}
} else{
	// ??????textarea
	
      out.write("\r\n\tvar middleTR2 = window.parent.document.getElementById(\"middleTR2\");\r\n\tif(null != middleTR2) middleTR2.style.display = \"none\";\r\n    var selTextarea2 = window.parent.document.getElementById(\"selTextarea2\");\r\n    if(null != selTextarea2) selTextarea2.style.display = \"\";\r\n\twindow.parent.document.frm2.selRange.value = \"");
      out.print(range);
      out.write("\";\r\n\t// ??????\r\n\tvar opText = \"");
      out.print(selectUserName);
      out.write("\";\r\n\tvar opValue = \"");
      out.print(selectUser);
      out.write("\";\t\r\n\t");

	//if(participantType==1 || participantType==12 || participantType==8){
	//participantType==1 or 8 ?????????????????????????????????
	if(participantType == 12){
		//????????????????????????
		
      out.write("\r\n\t\t//parent.document.frm2.all.selTextareaTag.style.display='';\r\n\t\t//parent.document.frm2.all.selectedTxt2.cols=\"62\";\r\n\t\twindow.parent.document.getElementById(\"selectedTxt2\").style.width=\"98%\";\r\n\t\t//parent.document.frm2.all.selectedTxtId.value=\"\";\r\n\t\t//parent.document.frm2.all.selectedTxt.value=\"\";\r\n\t\t");

	} else{
		//????????????????????????????????????
		//parent.document.frm2.all.selTextareaTag.style.display='none';
		//parent.document.frm2.all.selectedTxt2.cols="68";
		if(4 == participantType){
			
      out.write("\r\n\t\t\topValue = eval(\"window.parent.parent.document.all.");
      out.print(participantUserField);
      out.write("_Id.value\");\r\n\t\t\topText = eval(\"window.parent.parent.document.all.");
      out.print(participantUserField);
      out.write("_Name.value\");\r\n\t\t\tif(opValue == \"\") opText = \"\";\r\n\t\t\t");

		}
      out.write("\r\n\t\tvar opV = opValue.split(\",\");\r\n\t\topValue = \"\";\r\n\t\tfor(var i=0; i<opV.length; i++){\r\n\t\t\tif(opV[i] != \"\"){\r\n\t\t\t\topValue += \"$\" + opV[i] + \"$\";\r\n\t\t\t}\r\n\t\t}\r\n\t\tvar selectedTxt2=window.parent.document.getElementById(\"selectedTxt2\");\r\n\t\tif(selectedTxt2 !=null){\r\n\t\t\tselectedTxt2.style.width = \"98%\";\r\n\t\t\tselectedTxt2.value = opText;\r\n\t\t}\r\n\t\tvar selectedTxtId2=window.parent.document.frm2.selectedTxtId2;\r\n\t\tif(selectedTxtId2 != null) {\r\n\t\t\tselectedTxt2Id=opValue;\r\n\t\t}\r\n\t\t");

	}
}

      out.write("\r\n</SCRIPT>\r\n\r\n");

// ????????????????????????????????????
if(participantType == 4){
	
      out.write("\r\n\t<SCRIPT LANGUAGE=\"JavaScript\">\r\n\tif(eval(\"window.parent.parent.document.all.");
      out.print(participantUserField);
      out.write("_Id\")){\r\n\t\tvar selectUser = eval(\"window.parent.parent.document.all.");
      out.print(participantUserField);
      out.write("_Id.value\");\r\n\t\tvar selectUserName = eval(\"window.parent.parent.document.all.");
      out.print(participantUserField);
      out.write("_Name.value\");\r\n\t\tif(selectUserName.indexOf(\",\") != -1){\r\n\t\t\tselectUserName = selectUserName.substring(0, selectUserName.length-1);\r\n\t\t} else{\r\n\t\t\tselectUser = \"$\" + selectUser + \"$\";\r\n\t\t}\r\n\t\t// ?????????\r\n\t\tString.prototype.replaceAll = function(s1, s2){\r\n\t\t\tvar demo=this;\r\n\t \t\twhile(demo.indexOf(\"$$\") != -1)\r\n\t \t\t\tdemo = demo.replace(s1, s2);\r\n\t\t\treturn demo;\r\n\t\t}\r\n\t\tselectUser = selectUser.replaceAll(\"$$\", \",\");\r\n\t\tselectUser = selectUser.substring(1, selectUser.length-1);\r\n\t//\tloadXML('userorggroup', 'no', '");
      out.print(show);
      out.write("', '', '");
      out.print(range);
      out.write("', selectUser, selectUserName, '');\r\n\t\tshowList('userorggroup', 'no', '");
      out.print(show);
      out.write("', '', '");
      out.print(range);
      out.write("', selectUser, selectUserName, '");
      out.print(selectUserAccounts);
      out.write("', '");
      out.print(participantType);
      out.write("', 'org_list');\r\n\t} else{\r\n\t\talert(\"?????????????????????????????????????????????\");\r\n\t}\r\n\t</SCRIPT>\r\n\t");

}else{
	
      out.write("\r\n\t<SCRIPT LANGUAGE=\"JavaScript\">\r\n\t//loadXML('userorggroup','no','");
      out.print(show);
      out.write("','','");
      out.print(range);
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(selectUser);
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(selectUserName);
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(selectUserAccounts);
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(participantType);
      out.write("');\r\n\tshowList('userorggroup', 'no', '");
      out.print(show);
      out.write("', '', '");
      out.print(range);
      out.write("', '");
      out.print(selectUser);
      out.write("', '");
      out.print(selectUserName);
      out.write("', '");
      out.print(selectUserAccounts);
      out.write("', '");
      out.print(participantType);
      out.write("', 'org_list');\r\n\t</SCRIPT>\r\n\t");

}

//	????????????????????????
if(showSmsRemind){
	
      out.write("\r\n\t<SCRIPT LANGUAGE=\"JavaScript\">\r\n\tif(window.parent.document.frm2.showSmsRemind.value == 'true'){\r\n\t\twindow.parent.document.frm2.smsDiv.style.display = \"\";\r\n\t}\r\n\t</SCRIPT>\r\n\t");

} else{
	
      out.write("\r\n\t<SCRIPT LANGUAGE=\"JavaScript\">\r\n\tif(window.parent.document.frm2.showSmsRemind.value == 'true'){\r\n\t\twindow.parent.document.frm2.smsDiv.style.display = \"none\";\r\n\t}\r\n\t</SCRIPT>\r\n\t");

}

if(participantType == -1){
	// ??????????????????
	
      out.write("\r\n\t<SCRIPT LANGUAGE=\"JavaScript\">\r\n\tif(window.parent.document.all.Panle1){\r\n\t\twindow.parent.document.all.Panle1.style.display = \"none\";\r\n\t}\r\n\t</SCRIPT>\r\n\t");

} else{
	// ????????????
	
      out.write("\r\n\t<SCRIPT LANGUAGE=\"JavaScript\">\r\n\tif(window.parent.document.all.Panle1){\r\n\t\twindow.parent.document.all.Panle1.style.display = \"\";\r\n\t}\r\n\t</SCRIPT>\r\n\t");

}

stmt.close();
conn.close();

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
