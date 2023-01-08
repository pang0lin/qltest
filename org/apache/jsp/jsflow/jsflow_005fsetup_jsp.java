/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:06:50 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.util.util.CharacterTool;

public final class jsflow_005fsetup_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.util.util.CharacterTool");
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
String processName=request.getParameter("processName");
String tableId=request.getParameter("tableId");
String moduleId=request.getParameter("moduleId");

      out.write("\r\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n<html xmlns:v=\"urn:schemas-microsoft-com:vml\">\r\n<head>\r\n<link href=\"/jsoa/jsflow/flowimg.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<title>");
      out.print(processName );
      out.write("</title>\r\n\r\n<script src=\"/jsoa/js/util.js\"></script>\r\n<script src=\"/jsoa/jsflow/jsflow.js\"></script>\r\n<script language=\"javascript\">\r\nvar processId=\"");
      out.print(processId);
      out.write("\";\r\nvar tableId=\"");
      out.print(tableId);
      out.write("\";\r\nvar moduleId=\"");
      out.print(moduleId);
      out.write("\";\r\nvar imgCanDrag=1;\r\n</script>\r\n</head>\r\n<body class='btype' style=\"background-color:#FFF;\"  onload=\"Prop.clear();document.getElementById('group').focus();\">\r\n<div id=\"tool\"></div>\r\n<div id=\"group\" onselectstart=\"return false;\">\r\n</div>\r\n\r\n<script>\r\n//流程图\r\nvar g = new Group();\r\ng.init();\r\ng.setGroupArea();\r\n//菜单栏\r\nvar m = new Menu();\r\nm.init();\r\n\r\nvar w = new Window();\r\nw.left = screen.availWidth - 450;\r\nw.init();\r\n\r\nvar jsonStr;\r\njsonStr = '';\r\n");


java.sql.Connection conn=null;
java.sql.Statement stmt=null;
java.sql.ResultSet rs;
int i=0,j=0,objNum=0;
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
    
    String sql,name,id;    
    int type,activityNum=0,activityClass,activityType;
    int left,top,nickNum; 
    
    //查询除开始结束节点有没有用户定义的节点
    sql="select count(*) from jsf_activity where wf_workflowprocess_id="+processId+" and activitybeginend=0";
    rs=stmt.executeQuery(sql);
    if(rs.next()){
    	activityNum=rs.getInt(1);
    }
    rs.close();
    
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
       
       if(nickNum>objNum){
    	   objNum=nickNum;
       }
       if(activityClass==0){
    	   shape="img-subflow";
       }else if(activityClass==1){
    	   shape="img";
       }else if(activityClass==2){
    	   shape="img-autoflow";
       }else{
    	   shape="img-backflow";
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
    	   
    	   if(activityNum==0){
    		   left=60;
    		   top=200;
    	   }
    	   if(name==null || "null".equals(name)){
    		   name="开始";
    	   }
          
      out.write("\r\n          jsonStr+='{\"id\":\"node_");
      out.print(id);
      out.write("\",\"name\":\"");
      out.print(name);
      out.write("\",\"type\":\"start\",\"activityId\":\"");
      out.print(id);
      out.write("\",\"activityClass\":\"");
      out.print(activityClass);
      out.write("\",\"activityType\":\"");
      out.print(activityType);
      out.write("\",\"shape\":\"oval\",\"number\":");
      out.print(id);
      out.write(",\"left\":");
      out.print(left);
      out.write(",\"top\":");
      out.print(top);
      out.write(",\"width\":75,\"height\":55,\"property\":[]}';\r\n          ");

       }else{
    	   if(activityNum==0){
    		   left=600;
    		   top=200;
    	   }
          
      out.write("\r\n          jsonStr+='{\"id\":\"node_");
      out.print(id);
      out.write("\",\"name\":\"结束\",\"type\":\"end\",\"activityId\":\"");
      out.print(id);
      out.write("\",\"activityClass\":\"");
      out.print(activityClass);
      out.write("\",\"activityType\":\"");
      out.print(activityType);
      out.write("\",\"shape\":\"ovalend\",\"number\":");
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
        
        if(nickNum>objNum){
     	   objNum=nickNum;
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
      out.print(objNum);
      out.write(",' + jsonStr + '}';\r\nvar j = JSON.decode(jsonStr);\r\ng.jsonTo(j);\r\n\r\ninitLineDescription();\r\n</script>\r\n</body>\r\n</html>");
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
