/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:50:37 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.setup;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.List;
import com.js.system.menu.po.MenuSetPO;

public final class systemmanager_005fmenuset_005fnew_005fsetrole_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.util.List");
    _jspx_imports_classes.add("com.js.system.menu.po.MenuSetPO");
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

String local = session.getAttribute("org.apache.struts.action.LOCALE").toString();
String flag= request.getAttribute("flag")==null?"":request.getAttribute("flag").toString();

      out.write("\r\n\r\n<head>\r\n<title>菜单设置</title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<META HTTP-EQUIV=\"pragma\" CONTENT=\"no-cache\"> \r\n<META HTTP-EQUIV=\"Cache-Control\" CONTENT=\"no-cache, must-revalidate\"> \r\n<META HTTP-EQUIV=\"expires\" CONTENT=\"Wed, 26 Feb 1997 08:21:57 GMT\">\r\n\r\n<script language=\"JavaScript\" src=\"/jsoa/js/resource/");
      out.print(local);
      out.write("/CommonResource.js\" type=\"text/javascript\"></script>\r\n<link href=\"skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<link href=\"/jsoa/style/cssmain.css\" rel=\"stylesheet\" type=\"text/css\">\r\n</head>\r\n\r\n<body  class=\"MainFrameBox\" >\r\n<table width=\"100%\" border=0 cellpadding=\"0\" cellspacing=\"0\">\r\n<tr>\r\n<td>\r\n\t<form name=\"form1\" action=\"\" method=\"post\">\r\n\t<table width=\"100%\" height=\"25\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n\t<tr>\r\n           \r\n            <td align=\"right\" nowrap>\r\n                <button class=\"btnButton4Font\" onclick=\"batchDel('1');\">启用</button>\r\n                <button class=\"btnButton4Font\" onclick=\"batchDel('0');\">禁用</button>\r\n            </td>\r\n     </tr>\r\n        </table>\r\n\r\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"listTable outTopline\">\r\n<tr >\r\n\t<td width=\"40\" class=\"listTableHead\">\r\n\t<input type=\"checkbox\" value=\"1\" name=\"selAll\" onclick=\"selectAll(this);\"></td>\r\n\t<td width=\"15%\"  class=\"listTableHead\">菜单名称</td>\r\n\t<td   class=\"listTableHead\">用户范围</td>\r\n\t<td  width=\"8%\"  class=\"listTableHead\">显示顺序</td>\r\n\t<td width=\"8%\"  class=\"listTableHead\">是否启用</td>\r\n");
      out.write("\t<td width=\"8%\"  class=\"listTableHeadLast\">操作</td>\r\n</tr>\r\n");
     int index = 0;
		  List liseTopMenu=(List)request.getAttribute("liseTopMenu");
		   String listClass="listTableLine1";
		  for(int i=0;i<liseTopMenu.size();i++) {
			  MenuSetPO menuSetPO =null;
		    String inuse="";
		      menuSetPO=(MenuSetPO)liseTopMenu.get(i);
		     String menu_id=menuSetPO.getMenuId().toString();		      
		      inuse=menuSetPO.getInUse().toString();
		       index ++;
       listClass="listTableLine1";
        if(index%2 != 0){listClass="listTableLine2";}
		  
      out.write("\r\n   <tr >\r\n <td  width=\"8\"  class=\"");
      out.print(listClass);
      out.write("\"><input type=\"checkbox\" name=\"batchId\" value=\"");
      out.print(menu_id );
      out.write("\"></td>\r\n         <td  class=\"");
      out.print(listClass);
      out.write('"');
      out.write('>');
      out.print(menuSetPO.getMenuName());
      out.write("</td>\r\n         <td  class=\"");
      out.print(listClass);
      out.write('"');
      out.write('>');
      out.print(menuSetPO.getMenuView()==null?"":menuSetPO.getMenuView() );
      out.write("&nbsp;</td>\r\n         <td  width=\"8%\"  class=\"");
      out.print(listClass);
      out.write('"');
      out.write('>');
      out.print(menuSetPO.getMenuOrder());
      out.write("</td>\r\n\t\t<td  class=\"");
      out.print(listClass);
      out.write("\">\r\n         ");
if(inuse.equals("1")) {
      out.write('是');
}else{ 
      out.write('否');
} 
      out.write("\r\n          </td>\r\n      <td  class=\"");
      out.print(listClass);
      out.write(" listTableLineLastTD\">\r\n\r\n                    <span style=\"float:left;padding-right:3px;\">\r\n\t       &nbsp;<img style=\"cursor:pointer\" border=\"0\" src=\"images/modi.gif\" alt=\"修改\" onClick=\"modify('");
      out.print(menu_id );
      out.write("')\">\r\n\t\t\t\t\t</span>\r\n\t\t\t\t\t\t     \r\n\t\t");
if(inuse.equals("1")) {
      out.write("\t\t\t\t     \r\n\t\t\t<span style=\"float:left;padding-right:3px;\">\r\n\t\t\t&nbsp;<img  style=\"cursor:pointer\" border=\"0\" src=\"imges/noused.gif\" alt=\"禁用\" onClick=\"del('");
      out.print(menu_id );
      out.write("','0')\">\r\n\t\t\t</span>\r\n         ");
}else{
      out.write(" \r\n          <span style=\"float:left;padding-right:3px;\">\r\n\t\t\t&nbsp;<img  style=\"cursor:pointer\" border=\"0\" src=\"imges/used.gif\" alt=\"启用\" onClick=\"del('");
      out.print(menu_id );
      out.write("','1')\">\r\n\t\t\t</span>\r\n\t\t\t");
} 
      out.write("\r\n      </td>\r\n        </tr>\r\n                                    \r\n                                  \r\n      ");
} 
      out.write("                              \r\n</table>\r\n\r\n\r\n</form>\r\n</td>\r\n</tr>\r\n</table>\r\n</body>\r\n<script language=\"javascript\">\r\nvar flag=\"");
      out.print(flag);
      out.write("\";\r\nif(\"foraudit\"==flag){\r\n\talert(\"数据已提交审核管理员审核！\");\r\n}\r\n function selectAll(obj){\r\n  \r\n    var result=obj.checked;\r\n    if(document.all.batchId){\r\n       if(isNaN(document.all.batchId.length)){\r\n           document.all.batchId.checked=result;\r\n       }else{\r\n            for(var i=0;i<document.all.batchId.length;i++){\r\n                document.all.batchId[i].checked=result;\r\n            }\r\n       }\r\n    }\r\n}\r\n\r\n    function batchDel(a){\r\n      var status;\r\n      if(a==\"1\")\r\n      {\r\n      status=\"启用\";\r\n      }else\r\n      {\r\n       status=\"禁用\";\r\n      }\r\n    \r\n     var _id=getBatchIds();  \r\n      if(_id==\"\"){\r\n     alert(\"请选择要\"+status+\"的菜单！\");\r\n     return;\r\n      }\r\n     del(_id,a);\r\n \r\n    }\r\n\r\nfunction getBatchIds(){\r\n  var _id=\"\";\r\n  var obj=document.all.batchId;\r\n  if(obj){\r\n     if(obj.length){\r\n       for(var i=0;i<obj.length;i++){\r\n         if(obj[i].checked){\r\n            _id+=obj[i].value+\",\";\r\n         }\r\n       }\r\n       if(_id.length>0){\r\n          _id=_id.substring(0,_id.length-1);\r\n       }\r\n     }else{\r\n");
      out.write("       if(obj.checked){\r\n          _id+=obj.value;\r\n       }\r\n     }\r\n  }\r\n  return _id;\r\n}\r\n\r\n\r\n//删除记录\r\nfunction del(id,b) {\r\n      var status;\r\n      if(b==\"1\")\r\n      {\r\n      status=\"启用\";\r\n      }else\r\n      {\r\n       status=\"禁用\";\r\n      }\r\n      if(confirm(\"是否\"+status+\"菜单?\")){\r\n   window.location.href=((window.location.href).substring(0,(window.location.href).indexOf(\"/jsoa\")))+\"/jsoa/MenuAction.do?action=new_setrole_update&ids=\"+id+\"&inuse=\"+b ;\r\n   // parent.location.reload();\r\n    }\r\n }\r\n \r\n //修改\r\n \r\n  function modify(id)\r\n  {\r\n  \r\n  \r\n  \r\n  var Top,Left,offsetHeight,offsetWidth;\r\n    try{\r\n\t    var element=parent.document.all.content1;\r\n\t    var offsetTop = element.offsetTop; \r\n\t    var offsetLeft = element.offsetLeft; \r\n\t    offsetWidth = element.offsetWidth; \r\n\t    offsetHeight = element.offsetHeight; \r\n\t    while( element = element.offsetParent ) \r\n\t    { \r\n\t        offsetTop += element.offsetTop; \r\n\t        offsetLeft += element.offsetLeft; \r\n\t    }\r\n\t    var winTop=parent.window.screenTop;\r\n\t    var winLeft=parent.window.screenLeft; \r\n");
      out.write("\t    \r\n\t    Top=offsetTop+winTop;\r\n\t    Left=offsetLeft+winLeft;\r\n\t    \r\n\t    offsetHeight=offsetHeight-33;\r\n\t    offsetWidth=offsetWidth-10;\r\n    }catch(e){\r\n        Top=window.screenTop;\r\n        Left=window.screenLeft; \r\n        offsetHeight=document.body.offsetHeight;\r\n        offsetWidth=document.body.offsetWidth;\r\n    }\r\n      \r\n    window.open(\"/jsoa/MenuAction.do?action=new_load&modifyId=\"+id,\"new\",'TOP='+Top+',LEFT='+Left+',scrollbars=yes,status=no,resizable=yes,width='+offsetWidth+',height='+offsetHeight);\r\n  \r\n  }\r\n  \r\n  \r\n \r\n \r\n</script>\r\n");
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
