/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:45:01 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.relproject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import com.js.oa.relproject.po.*;
import com.js.oa.relproject.bean.*;

public final class pro_005fclass_005fmodify_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(3);
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-logic.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-html.tld", Long.valueOf(1499751390000L));
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-bean.tld", Long.valueOf(1499751390000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("com.js.oa.relproject.bean");
    _jspx_imports_packages.add("com.js.oa.relproject.po");
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
 RelProClassPO po=(RelProClassPO)request.getAttribute("po");
   List<Object> list=(List<Object>)request.getAttribute("list");
   String contun=request.getAttribute("contun")==null?"no":request.getAttribute("contun").toString();
   RelProClassBean relProClassBean=new RelProClassBean();
   Object[] obj1=relProClassBean.searchByParentId(po.getParentId(),po.getClassSortCode(),po.getId());
   String sortType=obj1[0].toString();
  String otherSortCode=obj1[1].toString();
  
  

      out.write("\r\n<head>\r\n<META HTTP-EQUIV=\"pragma\" CONTENT=\"no-cache\"> \r\n<META HTTP-EQUIV=\"Cache-Control\" CONTENT=\"no-cache, must-revalidate\"> \r\n<META HTTP-EQUIV=\"expires\" CONTENT=\"Wed, 26 Feb 1997 08:21:57 GMT\">\r\n<title>修改项目分类</title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<link href=\"/jsoa/skin/");
      out.print(session.getAttribute("skin"));
      out.write("/style-");
      out.print(session.getAttribute("browserVersion"));
      out.write(".css\" rel=\"stylesheet\" type=\"text/css\" />\r\n<script language=javascript src=\"/jsoa/js/js.js\"></script>\r\n<SCRIPT language=javascript src=\"/jsoa/js/openEndow.js\"></SCRIPT>\r\n</head>\r\n\r\n<body leftmargin=\"0\" topmargin=\"0\"  onload=\"init()\"  class=\"MainFrameBox Pupwin\">\r\n<form action=\"/jsoa/RelProClassAction.do?action=update\" name=\"form1\"  method=\"post\">\r\n<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"docBoxNoPanel\" style=\"border:0px #000000 solid;\">\r\n <tr>\r\n    <td valign=\"top\">\r\n\t\r\n\t   <table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"1\">\r\n        \r\n           <tr>\r\n               <td width=\"20%\">分类名称&nbsp;<label class=\"MustFillColor\">*</label>：</td>\r\n                <td align=\"left\">\r\n\t\t\t\t  <input type=text name=\"name\" id=\"name\" value=\"");
      out.print(po.getName());
      out.write("\" style=\"width:100%\"></td>\r\n\t\t\t\t   <input type=\"hidden\" class=\"inputText\" name=\"proClassId\" value=\"");
      out.print(po.getId() );
      out.write("\">\r\n           <tr>\r\n             <tr>\r\n               <td>所属分类：</td>\r\n                <td align=\"left\">\r\n\t\t\t\t  <select name=\"parentId\" id=\"parentId\" onchange=\"getSortCode()\" style=\"width:200px\" > \r\n\t\t\t\t  <option value=\"-1\">顶级分类</option>\r\n\t\t\t\t  ");

				  if(list!=null){
				     String classOrt;
				     for(int i=0;i<list.size();i++){
				        Object [] obj=(Object [])list.get(i);
				    String selected = Long.valueOf(obj[0].toString())==po.getParentId() ? "selected":"";
				   
      out.write("\r\n\t\t\t\t   <option value=\"");
      out.print(obj[0] );
      out.write('"');
      out.write(' ');
      out.print(selected);
      out.write(' ');
      out.write('>');
for( int j = 0; j < Integer.parseInt( obj[2].toString() ) - 1; j ++ ){
				out.print("&nbsp;&nbsp;");
			}
      out.print(obj[1]);
      out.write("</option>\r\n\t\t\t\t  ");
}} 
      out.write("\r\n\t\t\t\t  </select>\r\n\t\t\t\t  \r\n\t\t\t\t  </td>\r\n           </tr>\r\n           <tr >\r\n               <td>分类排序：</td>\r\n                <td align=\"left\">\r\n\t\t\t\t <select name=\"sortCode\"  id=\"sortCode\" style=\"width:200px\" > \r\n\t\t\t\t  <option value=\"-1\">==请选择==</option> \r\n\t\t\t\t </select>\r\n\t\t\t\t  <input type=\"radio\" name=\"insertSite\" value=\"0\"  />前\r\n\t\t\t\t  <input type=\"radio\" name=\"insertSite\" value=\"1\"  />后\r\n\t\t\t\t  </td>\r\n           </tr>\r\n           \r\n\t  </table>\r\n\t  \r\n\t  <table>\r\n\t\t<tr>\r\n\t\t  <td height=\"60\">\r\n\t\t     <input type=\"button\"  class=\"btnButton4font\" style=\"cursor:pointer\" onClick=\"saveAndExit()\" value=\"确定\"/>\r\n\t\t     <input type=\"button\" class=\"btnButton4font\" style=\"cursor:pointer\" onClick=\"javascript:resetMe()\" value=\"重置\"/>\r\n\t\t    <input type=\"button\" class=\"btnButton4font\" style=\"cursor:pointer\" onClick=\"javascript:window.close();\" value=\"退出\"/>\r\n  \r\n\t\t  </td>\r\n\t    </tr>\r\n\t  </table>\r\n\t  \r\n\t</td>\r\n  </tr>\r\n</table>\r\n</form>\r\n</body>\r\n\r\n<script language=\"JavaScript\">\r\n<!--\r\n\r\n\r\nfunction init(){\r\n\r\n   var ce=\"");
      out.print(contun);
      out.write("\";\r\n   if(\"yes\"==ce){\r\n    window.opener.location.reload();\r\n    window.close();\r\n }else{\r\n  getSortCode();\r\n }\r\n\r\n}\r\n \r\n     var xmlhttp;\r\nfunction getSortCode(){\r\n    \r\n   var parentid1=document.getElementById(\"parentId\").value;\r\n   var poparentid=\"");
      out.print( po.getParentId() );
      out.write("\";\r\n    if (window.XMLHttpRequest) {\r\n        xmlhttp = new XMLHttpRequest();\r\n        if (xmlhttp.overrideMimeType) {\r\n            xmlhttp.overrideMimeType(\"text/xml\");\r\n        }\r\n    } else if (window.ActiveXObject) {\r\n        var activexName = [\"MSXML2.XMLHTTP\",\"Microsoft.XMLHTTP\"];\r\n        for (var i = 0; i < activexName.length; i++) {\r\n            try{\r\n                xmlhttp = new ActiveXObject(activexName[i]);\r\n                break;\r\n            } catch(e){\r\n            }\r\n        }\r\n    }\r\n      xmlhttp.open(\"post\",\"/jsoa/public/jsp/getproclasssortcode.jsp?parentid=\"+parentid1+\"&id=");
      out.print(po.getId());
      out.write("\",false);\r\n      xmlhttp.send();\r\n    if (xmlhttp.readyState == 4) {\r\n        if (xmlhttp.status == 200) {\r\n        var root=xmlhttp.responseXML.documentElement;\r\n        var items=root.getElementsByTagName(\"items\")[0];\r\n        var sort=items.getElementsByTagName(\"sort\");\r\n        var sortText = \"\";\r\n         var sortCodeSelect=document.getElementById(\"sortCode\");\r\n         sortCodeSelect.innerHTML=\"\";\r\n         \r\n         if(parentid1!=poparentid){\r\n             document.getElementsByName(\"insertSite\")[0].checked = true ;  \r\n           \r\n           }\r\n       \r\n       \r\n         \r\n         if(sort.length>0){\r\n         for (var z=0; z<sort.length; z++) {\r\n        \r\n         var name=sort[z].getElementsByTagName(\"name\")[0].firstChild.nodeValue;\r\n         var sortcode=sort[z].getElementsByTagName(\"sortcode\")[0].firstChild.nodeValue;\r\n         var y=document.createElement('option');\r\n           y.text=name;\r\n           y.value=sortcode;\r\n            if(parentid1==poparentid){\r\n           \r\n                 if(\"2\"==\"");
      out.print(sortType);
      out.write("\")\r\n                  {\r\n                    document.getElementsByName(\"insertSite\")[1].checked = true ;  \r\n                  \r\n                  }else{\r\n                  \r\n                  \r\n                   document.getElementsByName(\"insertSite\")[0].checked = true ;  \r\n                  \r\n                  }\r\n           \r\n             if(sortcode==\"");
      out.print(otherSortCode);
      out.write("\"){\r\n             \r\n             y.selected = true ; \r\n             \r\n             }\r\n           \r\n           \r\n           }\r\n           \r\n           \r\n           \r\n           \r\n           try\r\n            {\r\n              sortCodeSelect.add(y,null); // standards compliant\r\n            }\r\n           catch(ex)\r\n                {\r\n               sortCodeSelect.add(y); // IE only\r\n                 }\r\n\r\n             }\r\n         }else{\r\n         \r\n             var y=document.createElement('option');\r\n            \r\n             y.text=\"==请选择==\";\r\n             y.value=\"-1\";\r\n           try\r\n               {\r\n              sortCodeSelect.add(y,null); // standards compliant\r\n                }\r\n           catch(ex)\r\n                {\r\n               sortCodeSelect.add(y); // IE only\r\n                 }\r\n              document.getElementsByName(\"insertSite\")[0].checked = true ;  \r\n            \r\n\r\n          }\r\n\r\n        } else {\r\n            alert(\"出错了！\");\r\n        }\r\n    }\r\n     \r\n\r\n}\r\n\r\n  \r\n\r\n\r\nfunction saveAndExit(){\r\n");
      out.write("\r\n  if(checkForm()){\r\n   \r\n     document.form1.submit();\r\n  }\r\n  \r\n}\r\nfunction resetMe(){\r\n  \r\n\twindow.location.href=\"/jsoa/RelProClassAction.do?action=load&proClassId=");
      out.print(po.getId());
      out.write("\";\r\n}\r\nfunction checkForm(){\r\n  \r\n        if(document.getElementById(\"name\").value.length<1)\r\n        {\r\n           alert(\"分类名称不能为空！\");\r\n           return false;\r\n        \r\n        }\r\n             if(document.getElementById(\"name\").value.length>50){\r\n      alert(\"项目分类名称不能大于50个字！\");\r\n      document.getElementById(\"name\").focus();\r\n      return false;\r\n   }\r\n        \r\n   return true;\r\n}\r\n\r\n//-->\r\n</script>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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