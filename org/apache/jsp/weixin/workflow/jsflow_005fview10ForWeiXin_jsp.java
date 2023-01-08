/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:59:08 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.weixin.workflow;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.util.util.*;

public final class jsflow_005fview10ForWeiXin_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html; charset=utf-8");
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

      out.write("\r\n<!doctype html>\r\n<html>\r\n<head>\r\n<link href=\"/jsoa/jsflow/flowimg.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n<title>");
      out.print(request.getParameter("processName") );
      out.write("</title>\r\n<script src=\"/jsoa/jsflow/jsflow.js\"></script>\r\n<script language=\"javascript\">\r\nvar processId=\"");
      out.print(processId);
      out.write("\";\r\nvar tableId=\"0\";\r\nvar moduleId=\"0\";\r\nvar imgCanDrag=0;\r\n</script>\r\n</head>\r\n<body class='btype' style=\"background-color:#FFF;\"   onload=\"Prop.clear();\">\r\n<div style=\"position:absolute;width:100%;height:100%;overflow:auto;\">\r\n");
if("1".equals(request.getParameter("manager"))){ 
      out.write("\r\n<div width=\"100%\" align=\"right\" style=\"margin-top:10px;margin-right:20px;\"><button onclick=\"savePosition();\" value=\"保存\">保存</button></div>\r\n");
}
      out.write("\r\n<div id=\"tool\"></div>\r\n<canvas id=\"canvas\" width=\"8000\" height=\"450\">\r\n  <p> <br> 您的浏览器可能不支持HTML5的标签\"canvas\"，请更换成 Chrome,FireFox,IE9,IE10等浏览器</p>\r\n</canvas>\r\n</div>\r\n<script>\r\n\r\n");

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
    	sql="select workstatus,initactivity,workactivity,parallel_curactid from jsf_work where workprocess_id="+processId+" and worktable_id="+request.getParameter("table")+" and workrecord_id="+recordId+" and worklistcontrol=1";
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
    //取出相应节点
    sql="select wf_activity_id,activityname,activitybeginend,positionleft,positiontop,nicknum,activityclass,activitytype from jsf_activity where wf_workflowprocess_id="+processId+" order by activitybeginend, wf_activity_id";
    
    
      out.write("\r\n  var imageList = new Array();\r\n    ");

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
        	   shape="img-subflow-1";
    	   }else if(curWorkActivityIds.indexOf(","+id+",")>=0){
        	   shape="img-subflow-2";
           }else{
        	   shape="img-subflow";
           }
       }else if(activityClass==1){
    	   if(dealwithIds.indexOf(","+id+",")>=0){
        	   shape="img-1";
    	   }else if(curWorkActivityIds.indexOf(","+id+",")>=0){
        	   shape="img-2";
           }else{
        	   shape="img";
           }
       }else if(activityClass==2){
    	   if(dealwithIds.indexOf(","+id+",")>=0){
        	   shape="img-autoflow-1";
    	   }else if(curWorkActivityIds.indexOf(","+id+",")>=0){
        	   shape="img-autoflow-2";
           }else{
        	   shape="img-autoflow";
           }
       }else{
    	   if(dealwithIds.indexOf(","+id+",")>=0){
        	   shape="img-backflow-1";
    	   }else if(curWorkActivityIds.indexOf(","+id+",")>=0){
        	   shape="img-backflow-2";
           }
    	   else{
        	   shape="img-backflow";
           }
       }   
       
       
      out.write("\r\n       var img");
      out.print(i);
      out.write(" = new Image();\r\n\t\t");

       if(type==0){
          
      out.write("\r\n        img");
      out.print(i);
      out.write(".src =\"/jsoa/img/flow/");
      out.print(shape);
      out.write(".gif\"; \r\n        img");
      out.print(i);
      out.write(".width=\"35\";\r\n        img");
      out.print(i);
      out.write(".height=\"35\";\r\n        img");
      out.print(i);
      out.write(".title=\"");
      out.print(name);
      out.write("\";\r\n        img");
      out.print(i);
      out.write(".left=\"");
      out.print(left);
      out.write("\";\r\n        img");
      out.print(i);
      out.write(".top=\"");
      out.print(top);
      out.write("\";\r\n        img");
      out.print(i);
      out.write(".id=\"node_");
      out.print(id);
      out.write("\";\r\n        imageList[");
      out.print(i);
      out.write("]=img");
      out.print(i);
      out.write("; \r\n \r\n          ");

       }else if(type==1){
    	   if(name==null || "null".equals(name)){
    		   name="开始";
    	   }
          
      out.write("\r\n        img");
      out.print(i);
      out.write(".src =\"/jsoa/img/flow/start.gif\"; \r\n        img");
      out.print(i);
      out.write(".width=\"35\";\r\n        img");
      out.print(i);
      out.write(".height=\"35\";\r\n        img");
      out.print(i);
      out.write(".title=\"");
      out.print(name);
      out.write("\";\r\n        img");
      out.print(i);
      out.write(".left=\"");
      out.print(left);
      out.write("\";\r\n        img");
      out.print(i);
      out.write(".top=\"");
      out.print(top);
      out.write("\";\r\n        img");
      out.print(i);
      out.write(".id=\"node_");
      out.print(id);
      out.write("\";\r\n        imageList[");
      out.print(i);
      out.write("]=img");
      out.print(i);
      out.write("; \r\n          ");

       }else{
          
      out.write("\r\n        img");
      out.print(i);
      out.write(".src =\"/jsoa/img/flow/end.gif\"; \r\n        img");
      out.print(i);
      out.write(".width=\"35\";\r\n        img");
      out.print(i);
      out.write(".height=\"35\";\r\n        img");
      out.print(i);
      out.write(".title=\"结束\";\r\n        img");
      out.print(i);
      out.write(".left=\"");
      out.print(left);
      out.write("\";\r\n        img");
      out.print(i);
      out.write(".top=\"");
      out.print(top);
      out.write("\";\r\n        img");
      out.print(i);
      out.write(".id=\"node_");
      out.print(id);
      out.write("\";        \r\n        imageList[");
      out.print(i);
      out.write("]=img");
      out.print(i);
      out.write("; \r\n          ");

       }
       i++;
    }
    rs.close();
    
      out.write("\r\n  \tvar  lineList = new Array();\r\n    ");

    //取出节点的连接线
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
        transitionDesc=CharacterTool.escapeJSTags(transitionDesc);
                
        
      out.write("\r\n     \tvar line");
      out.print(j );
      out.write(" = new NodeLine();  \r\n        line");
      out.print(j );
      out.write(".lineId =\"line_");
      out.print(id);
      out.write("\";\r\n        line");
      out.print(j );
      out.write(".start_Node =\"node_");
      out.print(from);
      out.write("\";\r\n        line");
      out.print(j );
      out.write(".end_Node =\"node_");
      out.print(to);
      out.write("\";\r\n        line");
      out.print(j );
      out.write(".lineDesc =\"");
      out.print(transitionDesc);
      out.write("\";\r\n\r\n\t\tlineList[");
      out.print(j );
      out.write("] = line");
      out.print(j );
      out.write(";\r\n        ");
        
        j++;
    }
    rs.close();
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


      out.write("\r\n\t//预先加载图片\r\n\tfunction AddPicturesToPage(){\r\n\t\tvar body = document.getElementsByTagName(\"body\");\r\n\t\tvar div = document.createElement(\"div\");\r\n\t\tdiv.style.display=\"none\";\r\n\t\tfor(var j=0;j<imageList.length;j++){\r\n\t\t\tdiv.appendChild(imageList[j]);\r\n\t\t\tbody[0].appendChild(div);\r\n\t\t}\r\n\t}\r\n\t   AddPicturesToPage();\r\n \t//节点的线\r\n  \tfunction NodeLine(){\r\n  \t\tthis.lineId=\"\";\r\n  \t\tthis.start_Node=\"\";\r\n  \t\tthis.end_Node=\"\";\r\n  \t\tthis.lineDesc=\"\";\r\n\t\t}\r\n \t//定义箭头线条\r\n  \tfunction Arrow(){\r\n  \t\tthis.start_x=0;\r\n  \t\tthis.start_y=0;\r\n  \t\tthis.end_x=0;\r\n  \t\tthis.end_y=0;\r\n  \t\tthis.length=0;\t\t\t\t//两点之间的长度\r\n  \t\tthis.radii=0;\t\t\t\t//圆点的半径\r\n  \t\tthis.arrow_len=10;\t\t\t//箭头的长度\r\n  \t\tthis.color=\"#0000ff\";\r\n  \t\tthis.rotation=0;\r\n  \t\tthis.desc=\"\";\t\t\t\t//箭头的描述\r\n\t\t}\r\n\t\tArrow.prototype.draw=function(context){\r\n  \t\tcontext.save();\r\n  \t\tcontext.translate(this.start_x,this.start_y);\r\n  \t\tcontext.rotate(this.rotation);\r\n  \t\tcontext.lineWidth=1;\r\n  \t\tcontext.strokeStyle=this.color;\r\n  \t\tcontext.beginPath();\r\n  \t\tcontext.moveTo(0,0);\r\n  \t\tcontext.lineTo(0,-0.5);\r\n");
      out.write("  \t\tcontext.lineTo(-(this.length-this.radii-this.arrow_len),-0.5);\r\n  \t\tcontext.lineTo(-(this.length-this.radii-this.arrow_len),-3);\r\n  \t\tcontext.lineTo(-(this.length-this.radii),0);\r\n  \t\tcontext.lineTo(-(this.length-this.radii-this.arrow_len),3);\r\n  \t\tcontext.lineTo(-(this.length-this.radii-this.arrow_len),0.5);\r\n  \t\tcontext.lineTo(0,0.5);\r\n  \t\tcontext.closePath();\r\n  \t\tcontext.stroke();\r\n  \t\tcontext.restore();\r\n  \t\tcontext.fillStyle=\"#008000\";\r\n  \t\t\r\n  \t\tif(this.desc.length>16){\r\n  \t\tcontext.fillText(this.desc.substr(0,16),(parseInt(this.end_x)+parseInt(this.start_x))/2-80,(parseInt(this.end_y)+parseInt(this.start_y))/2);\r\n  \t\tcontext.fillText(this.desc.substr(16),(parseInt(this.end_x)+parseInt(this.start_x))/2-parseInt(this.desc.length-16)*5,((parseInt(this.end_y)+parseInt(this.start_y))/2)+15);\r\n  \t\t\r\n  \t\t}else{\r\n  \t\tcontext.fillText(this.desc,(parseInt(this.end_x)+parseInt(this.start_x))/2-parseInt(this.desc.length)*5,(parseInt(this.end_y)+parseInt(this.start_y))/2);\r\n  \t\t}\r\n \t\t};\r\n\r\n  \twindow.onload=function(){\r\n");
      out.write("  \t\tvar canvas=document.getElementById(\"canvas\");\r\n  \t\tfor(var i=0;i<imageList.length;i++)\r\n  \t\t{\r\n\t  \t\tif(canvas.height<parseInt(imageList[i].top)+40){\r\n\t  \t\t\tcanvas.height = parseInt(imageList[i].top)+120;\r\n\t  \t\t}\r\n\t  \t\tif(canvas.width<parseInt(imageList[i].left)+40){\r\n\t  \t\t\tcanvas.width = parseInt(imageList[i].left)+80;\r\n\t  \t\t}\r\n  \t\t}\t\r\n  \t\tvar context=canvas.getContext(\"2d\");\r\n  \t\tcontext.font=\"12px Arial\";\r\n  \t\tfor(var i=0;i<imageList.length;i++)\r\n  \t\t{ if(imageList[i].complete){\r\n  \t\t\tcontext.drawImage(imageList[i],imageList[i].left,imageList[i].top,imageList[i].width,imageList[i].height);}\r\n  \t\t\telse{\r\n  \t\t\tvar tempi = i;\r\n  \t\t\t\timageList[tempi].onload=function(){\r\n  \t\t\t\tcontext.drawImage(imageList[i],imageList[i].left,imageList[i].top,imageList[i].width,imageList[i].height);\r\n  \t\t\t\t}\r\n  \t\t\t}\r\n  \t\t\tif(imageList[i].title.length>6){\r\n\t  \t\t\tcontext.fillText(imageList[i].title.substr(0,6),parseInt(imageList[i].left)-16,parseInt(imageList[i].top)+50);\r\n\t  \t\t\tcontext.fillText(imageList[i].title.substr(6),parseInt(imageList[i].left)-parseInt(imageList[i].title.length-6)*5+16,parseInt(imageList[i].top)+65);\r\n");
      out.write("  \t\t\t}else{\r\n\t  \t\t\tcontext.fillText(imageList[i].title,parseInt(imageList[i].left)-parseInt(imageList[i].title.length)*5+16,parseInt(imageList[i].top)+50);\r\n  \t\t\t}\r\n  \t\t}\t\r\n  \t\tfor(var i=0; i<lineList.length; i++){\t\r\n\t\t\tvar arrow=new Arrow();\r\n\t\t\tvar start_title_length = 0;\r\n\t\t\tvar end_title_length = 0;\r\n\t  \t\tfor(var j=0;j<imageList.length;j++)\r\n\t  \t\t{\r\n\t\t\t\t//绘制线条\r\n\t  \t\t\tif(lineList[i].start_Node==imageList[j].id){\r\n\t\t\t\t\tarrow.start_x = imageList[j].left;\r\n\t\t\t\t\tarrow.start_y = imageList[j].top;\r\n\t\t\t\t\tstart_title_length = imageList[j].title.length;\r\n\t  \t\t\t}\r\n\t  \t\t\tif(lineList[i].end_Node==imageList[j].id){\r\n\t\t\t\t\tarrow.end_x = imageList[j].left;\r\n\t\t\t\t\tarrow.end_y = imageList[j].top;\r\n\t\t\t\t\tend_title_length = imageList[j].title.length;\r\n\t  \t\t\t}\r\n\t  \t\t}\r\n\t  \t\t\r\n\t  \t\t//箭头起始和终止位置\r\n\t  \t\t\r\n\t  \t\tif(Math.abs(parseInt(arrow.start_x) - parseInt(arrow.end_x))<=Math.abs(parseInt(arrow.start_y) - parseInt(arrow.end_y))){\r\n\t\t  \t\tif(parseInt(arrow.start_x)<=parseInt(arrow.end_x)){\r\n\t\t\t  \t\tif(parseInt(arrow.start_y)<=parseInt(arrow.end_y)){\r\n");
      out.write("\t\t  \t\t\t\tarrow.start_x=parseInt(arrow.start_x)+16;\r\n\t\t  \t\t\t\tif(start_title_length>6){\r\n\t\t\t  \t\t\t\tarrow.start_y=parseInt(arrow.start_y)+70;\r\n\t\t  \t\t\t\t}else{\r\n\t\t\t  \t\t\t\tarrow.start_y=parseInt(arrow.start_y)+55;\r\n\t\t  \t\t\t\t}\r\n\t\t  \t\t\t\tarrow.end_x=parseInt(arrow.end_x)+16;\t  \t\t\t\r\n\t\t  \t\t\t\t//arrow.end_y=parseInt(arrow.end_y)+16;\t  \t\t\t\r\n\t\t\t  \t\t}else{\r\n\t\t\t  \t\t\tarrow.start_x=parseInt(arrow.start_x)+16;\r\n\t\t\t  \t\t\t//arrow.start_y=parseInt(arrow.start_y)+50;\r\n\t\t  \t\t\t\tarrow.end_x=parseInt(arrow.end_x)+16;\r\n\t\t  \t\t\t\tif(end_title_length>6){\t  \t\t\t\r\n\t\t  \t\t\t\t\tarrow.end_y=parseInt(arrow.end_y)+70;\t \r\n\t\t  \t\t\t\t}else{\r\n\t\t  \t\t\t\t\tarrow.end_y=parseInt(arrow.end_y)+55;\t \r\n\t\t  \t\t\t\t} \r\n\t\t\t  \t\t}\r\n\t\t  \t\t}else{\r\n\t\t  \t\t\tif(parseInt(arrow.start_y)<=parseInt(arrow.end_y)){\r\n\t\t  \t\t\t\tarrow.start_x=parseInt(arrow.start_x)+16;\r\n\t\t\t  \t\t\t if(start_title_length>6){\r\n\t\t\t  \t\t\t\tarrow.start_y=parseInt(arrow.start_y)+70;\r\n\t\t  \t\t\t\t}else{\r\n\t\t\t  \t\t\t\tarrow.start_y=parseInt(arrow.start_y)+55;\r\n\t\t  \t\t\t\t}\r\n\t\t  \t\t\t\tarrow.end_x=parseInt(arrow.end_x)+16;\t  \t\t\t\r\n\t\t  \t\t\t\t//arrow.end_y=parseInt(arrow.end_y)+16;\t  \t\t\t\r\n");
      out.write("\t\t\t  \t\t}else{\r\n\t\t\t  \t\t\tarrow.start_x=parseInt(arrow.start_x)+16;\r\n\t\t\t  \t\t\t//arrow.start_y=parseInt(arrow.start_y)+50;\r\n\t\t  \t\t\t\tarrow.end_x=parseInt(arrow.end_x)+16;\t  \t\t\t\r\n\t\t  \t\t\t\tif(end_title_length>6){\t  \t\t\t\r\n\t\t  \t\t\t\t\tarrow.end_y=parseInt(arrow.end_y)+70;\t \r\n\t\t  \t\t\t\t}else{\r\n\t\t  \t\t\t\t\tarrow.end_y=parseInt(arrow.end_y)+55;\t \r\n\t\t  \t\t\t\t} \t  \r\n\t\t\t  \t\t}\r\n\t\t  \t\t}\r\n\t  \t\t\r\n\t  \t\t}else{\r\n\t\t  \t\tif(parseInt(arrow.start_x)<=parseInt(arrow.end_x)){\r\n\t\t\t  \t\tif(parseInt(arrow.start_y)<=parseInt(arrow.end_y)){\r\n\t\t  \t\t\t\tarrow.start_x=parseInt(arrow.start_x)+35;\r\n\t\t\t  \t\t\tarrow.start_y=parseInt(arrow.start_y)+16;\r\n\t\t  \t\t\t\t//arrow.end_x=parseInt(arrow.end_x)+16;\t  \t\t\t\r\n\t\t  \t\t\t\tarrow.end_y=parseInt(arrow.end_y)+16;\t  \t\t\t\r\n\t\t\t  \t\t}else{\r\n\t\t\t  \t\t\tarrow.start_x=parseInt(arrow.start_x)+35;\r\n\t\t\t  \t\t\tarrow.start_y=parseInt(arrow.start_y)+16;\r\n\t\t  \t\t\t\t//arrow.end_x=parseInt(arrow.end_x)+16;\t  \t\t\t\r\n\t\t  \t\t\t\tarrow.end_y=parseInt(arrow.end_y)+16;\t  \r\n\t\t\t  \t\t}\r\n\t\t  \t\t}else{\r\n\t\t  \t\t\tif(parseInt(arrow.start_y)<=parseInt(arrow.end_y)){\t\t\r\n\t\t\t  \t\t\t//arrow.start_x=parseInt(arrow.start_x)+35;\r\n");
      out.write("\t\t\t  \t\t\tarrow.start_y=parseInt(arrow.start_y)+16;\r\n\t\t  \t\t\t\tarrow.end_x=parseInt(arrow.end_x)+35;\t  \t\t\t\r\n\t\t  \t\t\t\tarrow.end_y=parseInt(arrow.end_y)+16;\t\r\n\t\t\t  \t\t}else{\r\n\t\t  \t\t\t\t//arrow.start_x=parseInt(arrow.start_x)+16;\r\n\t\t\t  \t\t\tarrow.start_y=parseInt(arrow.start_y)+16;\r\n\t\t  \t\t\t\tarrow.end_x=parseInt(arrow.end_x)+35;\t  \t\t\t\t\r\n\t\t  \t\t\t\tarrow.end_y=parseInt(arrow.end_y)+16;\t \r\n\t\t\t  \t\t}\r\n\t\t  \t\t}\r\n\t  \t\t}\t\r\n\t  \t\t\t\r\n\t\t\tarrow.radii = 0;\r\n\t\t\tarrow.desc = lineList[i].lineDesc;\r\n\t\t\tvar dy = arrow.start_y - arrow.end_y;\r\n\t\t\tvar dx = arrow.start_x - arrow.end_x;\r\n\t\t\tarrow.rotation=Math.atan2(dy,dx);\r\n\t\t\tif(dy == 0){\r\n\t\t\t\tarrow.length = Math.abs(dx);\r\n\t\t\t}else if(dx == 0){\r\n\t\t\t\tarrow.length = Math.abs(dy);\r\n\t\t\t}else{\r\n\t\t\t\tarrow.length=Math.sqrt(dx*dx+dy*dy);\r\n\t\t\t\t}\r\n\t  \t\tarrow.draw(context);\r\n  \t\t}\r\n  \t};\r\n</script>\r\n\r\n</body>\r\n</html>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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