/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:58:56 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.weixin.workflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.system.service.usermanager.UserBD;
import com.js.oa.jsflow.vo.ActivityVO;
import com.js.oa.jsflow.service.WorkFlowButtonBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import java.util.*;
import javax.sql.*;
import java.sql.*;
import com.js.util.util.BrowserJudge;

public final class wf_005fstepanduser_005forgselfsendfirstforweixin_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes.add("com.js.oa.jsflow.vo.ActivityVO");
    _jspx_imports_classes.add("com.js.oa.jsflow.service.WorkFlowButtonBD");
    _jspx_imports_classes.add("com.js.util.util.BrowserJudge");
    _jspx_imports_classes.add("com.js.system.service.usermanager.UserBD");
    _jspx_imports_classes.add("com.js.oa.jsflow.service.WorkFlowBD");
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

      out.write("\r\n\r\n\r\n\r\n \r\n\r\n\r\n\r\n\r\n");
      out.write("\r\n<script language=\"JScript.Encode\" src=\"/jsoa/js/browinfo.js\"></script>\r\n<script language=\"JScript.Encode\" src=\"/jsoa/js/rtxint.js\"></script>\r\n<script language=\"javascript\">\r\nfunction rtxonline(a){\r\n\t RAP(a);\r\n}\r\n</script>");
      out.write('\r');
      out.write('\n');

request.setCharacterEncoding("utf-8");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
int menuIndex = 0;
boolean bflag = false;
if(BrowserJudge.isMSIE(request)){
	bflag = true;
}

      out.write("\r\n\r\n");

DataSource ds = new com.js.util.util.DataSourceBase().getDataSource();
Connection conn = null;
Statement stmt = null;
ResultSet rs = null;
conn = ds.getConnection();
stmt = conn.createStatement();

      out.write("\r\n\r\n");

//参数
String selectUser = "";			// 待选人员
String selectUserName = "";		// 待选人员
String selectUserAccounts = "";	// 待选人员
String range = "*0*";			// 指定范围
String show = "";				// 显示
String transactType = "1";		// 默认多人并行

//表单字段参数
String participantUserField = "";//表单中的某个字段名


WorkFlowBD workFlowBD = new WorkFlowBD();
WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();

//com.js.oa.jsflow.vo.ActivityVO activityVO = new com.js.oa.jsflow.util.WorkflowCommon().getFirstActivity(request);

String firstActivityId = request.getParameter("firstActivityId");
ActivityVO activityVO = workFlowBD.getFirstProcActiVO(firstActivityId);

//第一步节点办理方式
transactType = activityVO.getTransactType();

//参与者类型
//0 流程发起人的上级领导
//1 由上一节点参与者从所有用户中选择
//2 从候选人员中指定
//3 指定全部办理人
//4 由表单中的某个字段值决定

//节点参与者类型
int participantType = activityVO.getPassRoundUserType();
//System.out.println("参与人类型："+participantType);

if(participantType == 3){	// 指定全部办理人
	List candidate = activityVO.getPassRoundUser();
	for(int i=0; i<candidate.size(); i++){
		Object[] tmp = (Object[]) candidate.get(i); 
		selectUser += tmp[0] + ",";
		selectUserName += tmp[1] + ",";
	}
	if(selectUser.indexOf(",") != -1){
		selectUser = selectUser.substring(0, selectUser.length()-1);
		selectUserName = selectUserName.substring(0, selectUserName.length()-1);
	}
} else if(participantType == 2){	// 从候选人中指定
	List candidate = activityVO.getPassRoundUser();
	for(int i=0; i<candidate.size(); i++){
		Object[] tmp = (Object[]) candidate.get(i); 
		selectUser += tmp[0] + ",";
		selectUserName += tmp[1] + ",";
	}
	if(selectUser.indexOf(",") != -1){
		selectUser = selectUser.substring(0, selectUser.length()-1);
		selectUserName = selectUserName.substring(0, selectUserName.length()-1);
	}
} else if(participantType == 4){	// 由表单中的某个字段值决定
	participantUserField = activityVO.getPassRoundUserField();
	participantUserField = workFlowButtonBD.getFieldInfoByFieldId(participantUserField);
} else if(participantType == 8){	// 从指定范围中选定
	range = activityVO.getPassRoundGivenOrg();
	show = "user";
	if(range!=null && !range.equals("") && range.indexOf("@")>=0){
		show += "group";
	}
	if(range!=null && !range.equals("") && range.indexOf("*")>=0){
		show += "org";
	}
} else if(participantType==9){	// 由上一节点参与者从所在组织中选择
	range = "*" + session.getAttribute("orgId") + "*";
	show += "user";
} else if(participantType == 10){	// 组织领导
	range = "*" + session.getAttribute("orgId") + "*";
	show += "org";
} else if(participantType == 13){	// 由上一节点参与者从选定的群组中选择
	//show += "group";
	range = activityVO.getPassRoundGivenOrg();
	String tmp = range;
	range = "";

	String domainId = session.getAttribute("domainId")==null ? "0" : session.getAttribute("domainId").toString();
	String userId = session.getAttribute("userId").toString();
	String orgIdString = session.getAttribute("orgIdString").toString();
	String where = "";

	if("".equals(orgIdString)){
		//组织Id为0则可以查看所以组
		where = " or 1=1";
	} else{
		orgIdString = orgIdString.substring(1, orgIdString.length()-1);
		String[] orgArr = orgIdString.split("\\$\\$");
		for(int i=0; i<orgArr.length; i++){
			where+=" or rangeorg like '%*" + orgArr[i] + "*%' ";
		}
		rs = stmt.executeQuery("select group_id from org_group where groupuserstring like '%$" + userId + "$%'");
		while(rs.next()){
			where += " or rangegroup like '%@" + rs.getString(1) + "@%' ";
		}
		rs.close();
	}
	rs = stmt.executeQuery("select group_id from org_group where " 
			+ "((rangeemp is null and rangeorg is null and rangegroup is null) or " 
			+ "(rangeemp='' and rangeorg='' and rangegroup='') or  rangeemp like '%$" 
			+ userId + "$%' " + where + ") and domain_id=" + domainId);
	while(rs.next()){
		if(tmp.indexOf("@"+rs.getString(1)+"@") != -1){
			range += "@" + rs.getString(1) + "@";
		}
	}
	rs.close();

	if(range!=null && !"".equals(range)){
		show = "usergroup";
	}
} else if(participantType == 12){	// 由上一节点参与者从所有组织中选择
	 show += "org";
} else if(participantType == 1){	// 由上一节点参与者从所有用户中选择
	// show += "user";
	// 与指定范围相同的处理（范围为全部*0*）   从指定范围中选定
	range = "*0*";
	show = "userorggroup";
} else if(participantType == 11){	// 上一节点所有参与者 
	selectUser = session.getAttribute("userId").toString();
	selectUserName = session.getAttribute("userName").toString();
} else if(participantType == 7){	// 上一节点办理人的上级领导
	List leaderList = new WorkFlowBD().getLeaderList(session.getAttribute("userId").toString());

	for(int i=0; i<leaderList.size(); i++){
		Object[] tmp = (Object[]) leaderList.get(i); 
		selectUser += tmp[0] + ",";
		selectUserName += tmp[1] + ",";
	}
	if(selectUser.indexOf(",") != -1){
		selectUser = selectUser.substring(0, selectUser.length()-1);
		selectUserName = selectUserName.substring(0, selectUserName.length()-1);
	}
} else if(participantType == 0){	// 流程发起人的上级领导 
	List leaderList = new WorkFlowBD().getLeaderList(session.getAttribute("userId").toString());

	for(int i=0; i<leaderList.size(); i++){
		Object[] tmp = (Object[]) leaderList.get(i); 
		selectUser += tmp[0] + ",";
		selectUserName += tmp[1] + ",";
	}
	if(selectUser.indexOf(",") != -1){
		selectUser = selectUser.substring(0, selectUser.length()-1);
		selectUserName = selectUserName.substring(0, selectUserName.length()-1);
	}
} else if(participantType == 5){	// 流程发起人
	selectUser = session.getAttribute("userId").toString();
	selectUserName = session.getAttribute("userName").toString();
} else if(participantType == 6){	// 从角色中指定 
	List candidate = workFlowBD.getRoleUserIDAndName(activityVO.getPartRole(), session.getAttribute("userId").toString());
	for(int i=0; i<candidate.size(); i++){
		Object[] tmp = (Object[]) candidate.get(i); 
		selectUser += tmp[0] + ",";
		selectUserName += tmp[1] + ",";
	}
	if(selectUser.indexOf(",") != -1){
		selectUser = selectUser.substring(0, selectUser.length()-1);
		selectUserName = selectUserName.substring(0, selectUserName.length()-1);
	}
} else if(participantType == 15){	// 从角色中指定 
	List candidate = workFlowBD.getPositionUserIDAndName(activityVO.getPassRole(), session.getAttribute("userId").toString());
	for(int i=0; i<candidate.size(); i++){
		Object[] tmp = (Object[]) candidate.get(i); 
		selectUser += tmp[0] + ",";
		selectUserName += tmp[1] + ",";
	}
	if(selectUser.indexOf(",") != -1){
		selectUser = selectUser.substring(0, selectUser.length()-1);
		selectUserName = selectUserName.substring(0, selectUserName.length()-1);
	}
} else if(participantType == 14){	// 流程发起人上级组织 并且 职务级别
	List candidate = workFlowButtonBD.getLeaderByDutyLevelAndOrg(session.getAttribute("userId").toString(),activityVO.getPassRole());
	for(int i=0; i<candidate.size(); i++){
		Object[] tmp = (Object[]) candidate.get(i); 
		selectUser += tmp[0] + ",";
		selectUserName += tmp[1] + ",";
	}
	if(selectUser.indexOf(",") != -1){
		selectUser = selectUser.substring(0, selectUser.length()-1);
		selectUserName = selectUserName.substring(0, selectUserName.length()-1);
	}
}

if(!"".equals(selectUser)){
	selectUserAccounts = new UserBD().getUserAccountByIds(selectUser);
}

      out.write("\r\n<HTML>\r\n\t<HEAD>\r\n\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n\t\t<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t\t<script language=\"JScript.Encode\" src=\"/jsoa/js/browinfo.js\"></script>\r\n\t\t<script language=\"JScript.Encode\" src=\"/jsoa/js/rtxint.js\"></script>\r\n\t\t<script type=\"text/javascript\" src=\"/jsoa/js/weixin/mobiscroll/js/jquery-1.9.1.min.js\" ></script>\r\n\t\t<script type=\"text/javascript\" src=\"/jsoa/js/weixin/wf_stepanduser_tree.js\"></script>\r\n\t\t");

		if(bflag){
			
      out.write("<SCRIPT language=javascript src=\"/jsoa/jsflow/wf_stepanduser_tree_forweixin.js\"></SCRIPT>");

		}else{
			
      out.write("<SCRIPT language=javascript src=\"/jsoa/jsflow/wf_stepanduser_tree_o_forweixin.js\"></SCRIPT>");

		}
		
      out.write("\r\n\t\t<style type=\"text/css\">\r\n\t\t.Hidden{ display: none;}\r\n\t\t.Show{ display: block;}\r\n\t\t</style>\r\n\t</HEAD>\r\n\r\n\t<body onload=\"showList('userorggroup', 'no', '");
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
      out.write("', 'org_list');\">\r\n\t\t<div id=\"submenuBox");
      out.print(menuIndex);
      out.write("\" style=\"width: 100%; height: 100%; overflow: auto;\">\r\n\t\t\t<div id=\"org_list\" class=\"Hidden\"></div>\r\n\t\t</div>\r\n\t</body>\r\n</HTML>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n// 设置显示人员列表div的高度像素\r\nvar w = $(window).height();\r\n$(\"#org_list\").height(w);\r\n\r\nfunction HightLight(obj){\r\n\tobj.style.color = \"red\";\r\n}\r\n\r\nfunction NomalLight(obj){\r\n\tobj.style.color = \"black\";\r\n}\r\n/*\r\n//添加所有选项\r\nfunction openMainFrameAll(){\r\n\tvar topTs = document.all.topTable;\r\n\tvar topT;\r\n\tif(topTs.length){\r\n\t\ttopT = topTs[topTs.length-1];\r\n\t} else{\r\n\t\ttopT = document.all.topTable;\r\n\t}\r\n\r\n\tvar aLength = topT.getElementsByTagName(\"a\").length;\r\n\tfor(var i=0; i<aLength; i++){\r\n\t\tif(topT.getElementsByTagName(\"a\")[i]){\r\n\t\t\ttopT.getElementsByTagName(\"a\")[i].click();\r\n\t\t}\r\n\t}\r\n}\r\n\r\nfunction openMainFrame(opValue,opText){\r\n\tvar oSelect = window.parent.document.frm2.selectObject;\r\n\tvar nLen = oSelect.length;\r\n\r\n\tvar hasOpt = false;\r\n\tfor(i=0; i<nLen; i++){\r\n\t\tvar optObj = oSelect.options[i];\r\n\t\tif(optObj.value == opValue){\r\n\t\t\thasOpt = true;\r\n\t\t}\r\n\t}\r\n\r\n\t//添加到列表框\r\n");
      out.write("\tif(!hasOpt){\r\n\t\tvar oOption = document.createElement(\"OPTION\");\r\n\t\toSelect.options.add(oOption);\r\n\t\t");

		if(bflag){
			
      out.write("oOption.innerText = opText;");

		}else{
			
      out.write("oOption.textContent = opText;");

		} 
		if(BrowserJudge.isNotPc(request)){
			
      out.write("\r\n\t\t\tvar showNames = window.parent.document.getElementById(\"showNames2\");\r\n\t\t\tvar htmlDiv=\"<div id='A\" + opValue + \"A2' name='A\" + opValue \r\n\t\t\t\t+ \"A2' title='点击移出' onclick=\\\"dropNameDiv2('A\" + opValue + \"A2','\" + opValue \r\n\t\t\t\t+ \"')\\\" style='float: left; width: 100%; fontSize: 12px;'>\" + opText + \"</div>\";\r\n  \t        showNames.innerHTML += htmlDiv;\r\n\t\t\t");

		}
		
      out.write("\r\n\t\toOption.value = opValue;\r\n\t}\r\n}*/\r\n\r\nwindow.parent.document.frm2.participantType.value = \"");
      out.print(participantType);
      out.write("\";\r\n\r\n");

// 判断页面显示方式
if(participantType==1 || participantType==8 || participantType==2 || participantType==4 
	|| participantType==9 || participantType==10 || participantType==6 || participantType==13 
	|| participantType==0 || participantType==7 || participantType==11 || participantType==14 
	|| participantType==15){
	// 候选人。表单值，本部门，组织领导，角色，所有群组13，流程发起人上级领导，上一节点办理上级领导，上一节点所有参与者
	
      out.write("\r\n\twindow.parent.document.getElementById(\"middleTR2\").style.display = '';\r\n    window.parent.document.getElementById(\"selTextarea2\").style.display = 'none';\t\r\n/*\r\n\t// 初始化默认框\r\n\tvar oSelect = window.parent.document.frm2.selectObject;\r\n\r\n\t// 删除\r\n\tvar nLen = oSelect.length;\r\n\tfor(j=0; j<nLen; j++){\r\n\t\toSelect.remove(0);\r\n\t}*/\r\n\t");

	if(BrowserJudge.isNotPc(request)){
		
      out.write("\r\n\t\tvar showNames2 = window.parent.document.getElementById(\"showNames2\");\r\n\t\tshowNames2.innerHTML = \"\";\r\n\t\t");

	}
}else{
	// 显示textarea
	
      out.write("\r\n\twindow.parent.document.getElementById(\"middleTR2\").style.display = 'none';\r\n    window.parent.document.getElementById(\"selTextarea2\").style.display = '';\r\n\twindow.parent.document.frm2.selRange.value = \"");
      out.print(range);
      out.write("\";\r\n\t// 赋值\r\n\tvar opText = \"");
      out.print(selectUserName);
      out.write("\";\r\n\tvar opValue = \"");
      out.print(selectUser);
      out.write("\";\t\r\n\r\n\t");

	//if(participantType==1 || participantType==12 || participantType==8){
	//participantType==1 or 8 的时候安装选择范围处理
	if(participantType == 12){
		//显示选择对象按钮
		
      out.write("\r\n\t\t//parent.document.frm2.all.selTextareaTag.style.display='';\r\n\t\twindow.parent.document.getElementById(\"selectedTxt2\").cols = \"62\";\r\n\t\twindow.parent.document.getElementById(\"selectedTxt2\").style.width = \"98%\";\r\n\t\t//parent.frm2.approveMode.style.width=\"85%\";\r\n\t\twindow.parent.document.frm2.selectedTxtId2.value = \"\";\r\n\t\twindow.parent.document.getElementById(\"selectedTxt2\").value = \"\";\r\n\t\t");

	} else{
		//将制定的用户带入选择人框
		
      out.write("\r\n\t\t//parent.document.frm2.all.selTextareaTag.style.display='none';\r\n\t\t//parent.document.frm2.all.selectedTxt2.cols=\"64\";\r\n\t\twindow.parent.document.getElementById(\"selectedTxt2\").style.width = \"98%\";\r\n\t\t//parent.frm2.approveMode.style.width=\"99%\";\r\n\t\tvar opV = opValue.split(\",\");\r\n\t\topValue = \"\";\r\n\t\tfor(var i=0; i<opV.length; i++){\r\n\t\t\tif(opV[i] != \"\"){\r\n\t\t\t\topValue += \"$\" + opV[i] + \"$\";\r\n\t\t\t}\r\n\t\t}\r\n\t\twindow.parent.document.frm2.selectedTxtId2.value = opValue;\r\n\t\twindow.parent.document.getElementById(\"selectedTxt2\").value = opText;\r\n\t\t");

	}
}

      out.write("\r\n</SCRIPT>\r\n");

// 由表单中的某个字段值决定
if( participantType == 4 ){
	
      out.write("\r\n\t<SCRIPT LANGUAGE=\"JavaScript\">\r\n\tif(eval(\"window.parent.parent.document.all.");
      out.print(participantUserField);
      out.write("_Id\")){\r\n\t\tvar selectUser = eval(\"window.parent.parent.document.all.");
      out.print(participantUserField);
      out.write("_Id.value\");\r\n\t\tvar selectUserName = eval(\"window.parent.parent.document.all.");
      out.print(participantUserField);
      out.write("_Name.value\");\r\n\t\tif(selectUserName.indexOf(\",\") != -1){\r\n\t\t\tselectUserName = selectUserName.substring(0, selectUserName.length-1);\r\n\t\t} else{\r\n\t\t\tselectUser = \"$\" + selectUser + \"$\";\r\n\t\t}\r\n\t\t//新节点\r\n\t\tString.prototype.replaceAll = function(s1,s2){\r\n\t\t\tvar demo = this;\r\n\t\t \twhile(demo.indexOf(\"$$\") != -1)\r\n\t\t \t\tdemo = demo.replace(s1,s2);\r\n\t\t \treturn demo;\r\n\t\t}\r\n\t\tselectUser = selectUser.replaceAll(\"$$\", \",\");\r\n\t\tselectUser = selectUser.substring(1, selectUser.length-1);\r\n\t\tloadXML('userorggroup', 'no', '");
      out.print(show);
      out.write("', '', '");
      out.print(range);
      out.write("', selectUser, selectUserName, '');\r\n\t} else{\r\n\t\talert(\"请将表单上的选人字段设为可写！\");\r\n\t}\r\n\t</SCRIPT>\r\n\t");

}

if( participantType==-1 || activityVO.getBeginEnd()==2){
	// 没有设置阅件
	
      out.write("\r\n\t<SCRIPT LANGUAGE=\"JavaScript\">\r\n\twindow.parent.document.getElementById(\"Panle1\").style.display = \"none\";\r\n\t</SCRIPT>\r\n\t");

} else{
	// 设置阅件
	
      out.write("\r\n\t<SCRIPT LANGUAGE=\"JavaScript\">\r\n\twindow.parent.document.getElementById(\"Panle1\").style.display = \"\";\r\n\t</SCRIPT>\r\n\t");

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
