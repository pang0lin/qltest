/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:07:57 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.util.util.*;

public final class jsflow_005fview_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("com.js.util.util");
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

      out.write("\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

String processId=request.getParameter("processId");
if(!ParameterFilter.isNumber(processId) ){
	try{
		response.sendRedirect(request.getContextPath()+"/public/jsp/inputerror.jsp");
	}catch(Exception ex){
		
	}
}

      out.write("\r\n<script language=\"javascript\">\r\n ");

	if(!"0".equals(request.getParameter("processType"))){
		if(!BrowserJudge.isMSIE(request)){
      out.write("\r\n\t\t if(!(navigator.userAgent.indexOf(\"Trident/7.0\")>=0 && navigator.userAgent.indexOf(\"MSIE 7.0\")>=0)){\r\n\t\t window.location.href=\"/jsoa/jsflow/jsflow_view10.jsp?processId=");
      out.print(request.getParameter("processId"));
      out.write("&table=");
      out.print(request.getParameter("table"));
      out.write("&record=");
      out.print(request.getParameter("record"));
      out.write("&CurActivityID=&ActivityIDStr=\";\r\n\t\t }\r\n   ");
}}
      out.write("\r\n</script>\r\n\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<html xmlns:v=\"urn:schemas-microsoft-com:vml\">\r\n<head>\r\n<link href=\"/jsoa/jsflow/flowimg.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<title>");
      out.print(request.getParameter("processName") );
      out.write("</title>\r\n<script src=\"/jsoa/jsflow/jsflow.js\"></script>\r\n<script language=\"javascript\">\r\nvar processId=\"");
      out.print(processId);
      out.write("\";\r\nvar tableId=\"0\";\r\nvar moduleId=\"0\";\r\nvar imgCanDrag=0;\r\n</script>\r\n</head>\r\n<body class='btype' style=\"background-color:#FFF;\"  onload=\"Prop.clear();\">\r\n");
if("1".equals(request.getParameter("manager"))){ 
      out.write("\r\n<div width=\"100%\" align=\"right\" style=\"margin-top:10px;margin-right:20px;\"><button onclick=\"savePosition();\" value=\"保存\">保存</button></div>\r\n");
}
      out.write("\r\n<div id=\"tool\"></div>\r\n\r\n<div id=\"group\" onselectstart=\"return false;\">\r\n</div>\r\n<script>\r\nvar g = new Group();\r\ng.init();\r\n\r\ng.setGroupArea();\r\n\r\n//var m = new Menu();\r\n//m.init();\r\nvar w = new Window();\r\nw.left = screen.availWidth - 450;\r\nw.init();\r\n\r\nvar jsonStr;\r\njsonStr = '';\r\n");



java.sql.Connection conn=null;
java.sql.Statement stmt=null;
java.sql.ResultSet rs;
int i=0,j=0;
try{
    conn=new com.js.util.util.DataSourceBase().getDataSource().getConnection();
    stmt=conn.createStatement();
    
    if("savepos".equals(request.getParameter("oprate"))){
    	//保存节点位置
    	String data=request.getParameter("data");
    	String[] posArray=data.split(";");
    	String posData;
    	String[] nodeArray;
    	for(i=0;i<posArray.length;i++){
    		posData=posArray[i];
    		if(!"".equals(posData)){
    			nodeArray=posData.split(",");
    			stmt.addBatch("update jsf_activity set positionleft="+nodeArray[1]+",positiontop="+nodeArray[2]+" where wf_activity_id="+nodeArray[0]);
    		}
    	}
    	
    	stmt.executeBatch();
    	stmt.clearBatch();
    }
    
    String sql,name,id,dealwithIds="";
    StringBuffer buffer=new StringBuffer(",");
    
    int type,activityNum=0,activityClass,activityType;
    int left,top,nickNum,workStatus; 
    String recordId=request.getParameter("record");
    String curWorkActivityId="-1",curWorkActivityIds=",";//当前节点ID
    String initWorkActivity="-1";
    String parallelCurActiId="-1";
    if(recordId!=null && !"".equals(recordId) && !"null".equals(recordId)){
    	sql="select workstatus,initactivity,workactivity,parallel_curactid from jsf_work where workprocess_id="+processId+" and worktable_id in("+request.getParameter("table")+") and workrecord_id="+recordId+" and worklistcontrol=1";
    	System.out.println("sql:"+sql);
    	rs=stmt.executeQuery(sql);
    	while(rs.next()){
    		workStatus=rs.getInt(1);
    		initWorkActivity=rs.getString(2);
    		curWorkActivityId=rs.getString(3);
    		parallelCurActiId=rs.getString(4);
    		
    		if(workStatus==101){
    			buffer.append(initWorkActivity).append(",");
    			if(!"0".equals(parallelCurActiId)){
    				buffer.append(parallelCurActiId).append(",");
    			}
    		}else if(workStatus==0){
    			if(!"0".equals(parallelCurActiId)){
    				curWorkActivityIds+=parallelCurActiId+",";
    			}else{
    				curWorkActivityIds+=curWorkActivityId+",";
    			}
    		}
    	}
    	rs.close();
    	
    	dealwithIds=buffer.toString();
    }    
        
    sql="select wf_activity_id,activityname,activitybeginend,positionleft,positiontop,nicknum,activityclass,activitytype from jsf_activity where wf_workflowprocess_id="+processId+" order by activitybeginend, wf_activity_id";
    
    
      out.write("\r\n    jsonStr+='\"nodes\":[';\r\n    ");

    i=0;
    String shape;
    rs=stmt.executeQuery(sql);
    while(rs.next()){
       id=rs.getString(1);
       name=rs.getString(2);
       type=rs.getInt(3);
       left=rs.getInt(4);
       top=rs.getInt(5);
       nickNum=rs.getInt(6);
       activityClass=rs.getInt(7);
       activityType=rs.getInt(8);
              
       if(activityClass==0){
    	   if(dealwithIds.indexOf(","+id+",")>=0){
        	   shape="img-subflow-done";
    	   }else if(curWorkActivityIds.indexOf(","+id+",")>=0){
        	   shape="img-subflow-doing";
           }else{
        	   shape="img-subflow";
           }
       }else if(activityClass==1){
    	   if(dealwithIds.indexOf(","+id+",")>=0){
        	   shape="img-done";
    	   }else if(curWorkActivityIds.indexOf(","+id+",")>=0){
        	   shape="img-doing";
           }else{
        	   shape="img";
           }
       }else if(activityClass==2){
    	   if(dealwithIds.indexOf(","+id+",")>=0){
        	   shape="img-autoflow-done";
    	   }else if(curWorkActivityIds.indexOf(","+id+",")>=0){
        	   shape="img-autoflow-doing";
           }else{
        	   shape="img-autoflow";
           }
       }else{
    	   if(dealwithIds.indexOf(","+id+",")>=0){
        	   shape="img-backflow-done";
    	   }else if(curWorkActivityIds.indexOf(","+id+",")>=0){
        	   shape="img-backflow-doing";
           }
    	   else{
        	   shape="img-backflow";
           }
       }   
       
       
       if(i!=0){
          
      out.write("\r\n          jsonStr+=',';\r\n          ");

       }
       if(type==0){
          
      out.write("\r\n          jsonStr+= '{\"id\":\"node_");
      out.print(id);
      out.write("\",\"name\":\"");
      out.print(name);
      out.write("\",\"type\":\"node\",\"activityId\":\"");
      out.print(id);
      out.write("\",\"activityClass\":\"");
      out.print(activityClass);
      out.write("\",\"activityType\":\"");
      out.print(activityType);
      out.write("\",\"shape\":\"");
      out.print(shape);
      out.write("\",\"number\":");
      out.print(id);
      out.write(",\"left\":");
      out.print(left);
      out.write(",\"top\":");
      out.print(top);
      out.write(",\"width\":75,\"height\":70,\"property\":[]}';\r\n          ");

       }else if(type==1){
    	   if(name==null || "null".equals(name)){
    		   name="开始";
    	   }
          
      out.write("\r\n          jsonStr+='{\"id\":\"node_");
      out.print(id);
      out.write("\",\"name\":\"");
      out.print(name);
      out.write("\",\"type\":\"start\",\"shape\":\"oval\",\"activityId\":\"");
      out.print(id);
      out.write("\",\"activityClass\":\"");
      out.print(activityClass);
      out.write("\",\"activityType\":\"");
      out.print(activityType);
      out.write("\",\"number\":");
      out.print(id);
      out.write(",\"left\":");
      out.print(left);
      out.write(",\"top\":");
      out.print(top);
      out.write(",\"width\":75,\"height\":55,\"property\":[]}';\r\n          ");

       }else{
          
      out.write("\r\n          jsonStr+='{\"id\":\"node_");
      out.print(id);
      out.write("\",\"name\":\"结束\",\"type\":\"end\",\"shape\":\"ovalend\",\"activityId\":\"");
      out.print(id);
      out.write("\",\"activityClass\":\"");
      out.print(activityClass);
      out.write("\",\"activityType\":\"");
      out.print(activityType);
      out.write("\",\"number\":");
      out.print(id);
      out.write(",\"left\":");
      out.print(left);
      out.write(",\"top\":");
      out.print(top);
      out.write(",\"width\":75,\"height\":55,\"property\":[]}';\r\n          ");

       }
       i++;
    }
    rs.close();
    
      out.write("\r\n    jsonStr+='],';\r\n    jsonStr+='\"lines\":[';\r\n    ");

    sql="select wf_transition_id,transitionfrom,transitionto,linetype,nicknum,transitiondescription from jsf_transition where transitionfrom in(select wf_activity_id from jsf_activity where wf_workflowprocess_id="+processId+")";
    int from,to,lineType;
    String transitionDesc;
    shape="line";
    rs=stmt.executeQuery(sql);
    while(rs.next()){
        id=rs.getString(1);
        from=rs.getInt(2);
        to=rs.getInt(3);
        
        lineType=rs.getInt(4);
        nickNum=rs.getInt(5);
        transitionDesc=rs.getString(6);
                
        if(lineType==1){
        	shape="polyline";
        }else{
        	shape="line";
        }
                
        if(transitionDesc==null || "".equals(transitionDesc)){
        	transitionDesc="";
        }
        transitionDesc=CharacterTool.escapeHTMLTags(transitionDesc);
        
        if(j!=0){
          
      out.write("\r\n          jsonStr+=',';\r\n          ");

        }
        
      out.write("\r\n        jsonStr+='{\"id\":\"line_");
      out.print(id);
      out.write("\",\"name\":\"line_");
      out.print(id);
      out.write("\",\"transId\":\"");
      out.print(id);
      out.write("\",\"transDesc\":\"");
      out.print(transitionDesc);
      out.write("\",\"type\":\"line\",\"shape\":\"");
      out.print(shape);
      out.write("\",\"number\":");
      out.print(id);
      out.write(",\"from\":\"node_");
      out.print(from);
      out.write("\",\"to\":\"node_");
      out.print(to);
      out.write("\",\"fromx\":268,\"fromy\":83,\"tox\":267.5,\"toy\":140,\"polydot\":[],\"property\":null}';\r\n        ");
        
        
        j++;
    }
    rs.close();
    
      out.write("\r\n    jsonStr+=']';\r\n    ");

    stmt.close();
    conn.close();
}catch(Exception ex){
    if(conn!=null){
      try{
        conn.close();
      }catch(Exception err){}
    }
    ex.printStackTrace();
}


      out.write("\r\n\r\njsonStr = '{\"id\":null,\"name\":null,\"count\":");
      out.print((i+j));
      out.write(",' + jsonStr + '}';\r\n\r\nvar j = JSON.decode(jsonStr);\r\ng.jsonTo(j);\r\ninitLineDescription();\r\n\r\nfunction savePosition(){\r\n\tvar newJson=g.toJson();\t\r\n\tvar newJsonObj = JSON.decode(newJson);\r\n\t\r\n\tvar jNodes=newJsonObj.nodes;\r\n\tvar nodeNum=jNodes.length;\r\n\t\r\n\tvar nodeId,nodeLeft,nodeTop,retData;\r\n\tretData=\"\";\r\n\t\r\n\tfor(var i=0;i<nodeNum;i++){\r\n\t    nodeId=jNodes[i].id;\r\n\t    nodeId=nodeId.substring(5);\r\n\t    nodeLeft=jNodes[i].left;\r\n\t    nodeTop=jNodes[i].top;\r\n\t    \r\n\t    retData+=nodeId+\",\"+nodeLeft+\",\"+nodeTop+\";\";\r\n\t}\r\n\t\r\n\tlocation.href=\"jsflow_view.jsp?oprate=savepos&manager=1&processId=");
      out.print(request.getParameter("processId"));
      out.write("&data=\"+retData;\r\n}\r\n\r\n</script>\r\n</body>\r\n</html>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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
