/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:45:28 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.js.cooperate;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class cooperatebtn_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {


private static HashMap buttons = null;
private static HashMap getAllButtons(){
	//格式：文本,说明,图标,是否有效,宽度,动作	
	if(buttons==null){
		buttons=new HashMap();
		buttons.put("Save","保存草稿,保存草稿,/jsoa/images/toolbar/save.gif,true,10,js:jsSaveDraft()");
		buttons.put("Send","发送,发送,/jsoa/images/toolbar/send.gif,true,10,js:jsSend()");
		buttons.put("Dealwith","处理,处理,/jsoa/images/toolbar/send.gif,true,10,js:jsDealwith()");
		buttons.put("DrawFlow","流程图,流程图,/jsoa/images/toolbar/readtext.gif,true,10,js:jsDrawFlow()");
		buttons.put("AddPerson","增加办理人,增加办理人,/jsoa/images/toolbar/addperson.gif,true,10,js:jsAddPerson()");
		buttons.put("ModifyBody","修改正文,修改正文,/jsoa/images/toolbar/writetext.gif,true,10,js:jsModifyBody()");
		buttons.put("AppendBody","正文补充,正文补充,/jsoa/images/toolbar/imptext.gif,true,10,js:jsAppendBody()");
		buttons.put("RecordList","处理记录,处理记录,/jsoa/images/toolbar/code.gif,true,10,js:jsRecordList()");
		buttons.put("PrintDoc","打印,打印,/jsoa/images/toolbar/print.gif,true,10,js:jsPrintDoc()");
		buttons.put("Close","退出,退出,/jsoa/images/toolbar/close.gif,true,10,js:jsClose()");
		buttons.put("overBody","终止,终止,/jsoa/images/toolbar/overBody.png,true,10,js:jsEnd()");
		buttons.put("press","催办,催办,/jsoa/images/toolbar/wait.gif,true,10,js:jsPressToDo()");
		//增加转办
		buttons.put("TurnToDo","转办,转办,/jsoa/images/toolbar/addperson.gif,true,10,js:jsTurnToDo()");
		
		if("1".equals(com.js.util.config.SystemCommon.getUseArchives())&& !"rws".equalsIgnoreCase(com.js.util.config.SystemCommon.getCustomerName())){
			buttons.put("CoToArchives","归档,归档,/jsoa/images/guidang2.gif,true,10,js:guidang()");
		}
		if("rws".equalsIgnoreCase(com.js.util.config.SystemCommon.getCustomerName())){
		    //buttons.put("PreGD", "预归档,预归档,/jsoa/images/yuguidang.gif,true,10,js:cmdPreGD()");  //人卫社用
		    //buttons.put("CheHuiGD", "撤销归档,撤销归档,/jsoa/images/chehui.gif,true,10,js:cmdCheHuiGD()");  //人卫社用
		    //buttons.put("RWSGD", "归档,归档,/jsoa/images/guidang.gif,true,10,js:guidang()");  //人卫社用
		}
	}
	return buttons;
}

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
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

      out.write("\r\n\r\n");
      out.write('\r');
      out.write('\n');

String localcmd = session.getAttribute("org.apache.struts.action.LOCALE").toString();

      out.write("\r\n\r\n");

String strButton=request.getParameter("button");
if(strButton!=null && strButton.trim().length()>0){
	String[] cmdButtons=strButton.split(",");
	HashMap allButtons = getAllButtons();
	//格式：文本,说明,图标,是否有效,宽度,动作	
	
	

      out.write("\r\n\r\nvar cmdClassData=new Object();\r\ncmdClassData.classPannel\t\t\t= \"cmdMenuPanel\";\r\ncmdClassData.classItemPannel\t\t= \"cmdMenuItemPanel\";\r\ncmdClassData.classID\t\t\t\t= \"cmdMenu\";\r\ncmdClassData.classOver\t\t\t\t= \"cmdMenuOver\";\r\ncmdClassData.classDown\t\t\t\t= \"cmdMenuOver\";\r\ncmdClassData.classDisabled\t\t\t= \"cmdMenuDisabled\";\r\ncmdClassData.classDisabledOver\t\t= \"cmdMenuDisabled\";\r\ncmdClassData.classIcon              = \"cmdButtonIco\";\r\ncmdClassData.Separator              = \"\";\r\ncmdClassData.subExt                 = \"cmdButtonExt\";\r\ncmdClassData.classItem\t\t\t\t= \"cmdMenuItem\";\r\ncmdClassData.classItemOver\t\t\t= \"cmdMenuItemOver\";\r\ncmdClassData.classItemDown\t\t\t= \"cmdMenuItemOver\";\r\ncmdClassData.classItemDisabled\t\t= \"cmdMenuItemDisabled\";\r\ncmdClassData.classItemDisabledOver\t= \"cmdMenuItemDisabled\"\r\ncmdClassData.classItemSeparator\t\t= \"cmdMenuItemSeparator\";\r\ncmdClassData.classItemLine\t\t\t= \"cmdMenuItemLine\";\r\ncmdClassData.classItemArrow\t\t\t= \"cmdMenuItemArrow\";\r\ncmdClassData.classItemArrowOver\t\t= \"cmdMenuItemArrow\";\r\n\r\nvar docWidth=0;\r\nfunction loadToolbar(obj){\r\n");
      out.write("\tvar sWidth=document.body.offsetWidth;\r\n\r\n    document.getElementById(obj).innerHTML=\"\";\r\n\tvar menu = new pullXMenu(obj,cmdClassData);\r\n\tvar buttons=new Array();\r\n\t");

	int num=0;
	for(int i=0;i<cmdButtons.length;i++){
		if(allButtons.containsKey(cmdButtons[i])){
	
      out.write("\r\n\t\t  buttons[");
      out.print(num++);
      out.write(']');
      out.write('=');
      out.write('"');
      out.print(allButtons.get(cmdButtons[i]));
      out.write("\";\r\n\t");

		}
	}

      out.write("\r\n\tvar maxSize=10;\r\n\r\n\tif(sWidth<=300){\r\n\t\tmaxSize=2;\r\n\t}else if(sWidth<=400){\r\n\t\tmaxSize=3;\r\n\t}else if(sWidth<=500){\r\n\t\tmaxSize=4;\r\n\t}else if(sWidth<=600){\r\n\t\tmaxSize=5;\r\n\t}else if(sWidth<=700){\r\n\t\tmaxSize=6;\r\n\t}else if(sWidth<=800){\r\n\t\tmaxSize=8;\r\n\t}else if(sWidth<=900){\r\n\t\tmaxSize=9;\r\n\t}else if(sWidth<=1000){\r\n\t\tmaxSize=10;\r\n\t}else if(sWidth<=1100){\r\n\t\tmaxSize=11;\r\n\t}else if(sWidth<=1200){\r\n\t\tmaxSize=12;\r\n\t}else if(sWidth>=1200){\r\n\t\tmaxSize=13;\r\n\t}else{\r\n\t\tmaxSize=1;\r\n\t}\r\n\tvar i=0\r\n\tfor(i=0;i<maxSize;i++){\r\n\t    if(i>=buttons.length)break;\r\n\t    var but=buttons[i].split(\",\");\r\n\t\teval(\"var button\"+i+\"=menu.createMenu(\\\"<SAPN title='\" + but[1] + \"'><img src='\" + but[2] + \"' align='top' class='\" + cmdClassData.classIcon + \"'>\" + but[0] + \"\\\",\" + but[3] + \",\" + but[4] + \",'\" + but[5] + \"')\");\r\n\r\n\t}\r\n\tif(buttons.length>i+1){\r\n\t    var k=i;\r\n\t\teval(\"var button\"+k+\"=menu.createMenu(\\\"<span title='更多'><img src='/jsoa/images/toolbar/more.gif' align='top' class='\" + cmdClassData.classIcon + \"' style='width:21px;'></span>\\\",true,140)\");\r\n");
      out.write("\r\n\r\n\t\tfor(var j=i;j<buttons.length;j++){\r\n\t    \tvar but=buttons[j].split(\",\");\r\n\t\t\teval(\"var button\"+(j+1)+\"=button\"+k+\".createMenuItem('\" + but[0] + \"','\" + but[5] + \"',\" + but[3] + \",\" + but[4] + \",'\" + but[2] + \"')\");\r\n\t\t}\r\n\t}else{\r\n\t\tfor(var j=i;j<buttons.length;j++){\r\n\t\t\tvar but=buttons[j].split(\",\");\r\n\t\t\teval(\"var button\"+i+\"=menu.createMenu(\\\"<SAPN title='\" + but[1] + \"'><img src='\" + but[2] + \"' align='top' class='\" + cmdClassData.classIcon + \"'>\" + but[0] + \"\\\",\" + but[3] + \",\" + but[4] + \",'\" + but[5] + \"')\");\r\n\t\t}\r\n\r\n\t}\r\n}\r\n");

}

      out.write("\r\n\r\n");
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