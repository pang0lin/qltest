/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:57:57 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.public_.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.net.URLEncoder;
import com.js.oa.portal.util.RsXMLReader;
import com.js.util.config.SystemCommon;
import java.util.*;
import com.js.util.util.CharacterTool;
import com.js.system.service.messages.MessagesBD;
import java.text.*;
import com.js.system.vo.messages.MessagesVO;

public final class sysmessage_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-bean.tld", Long.valueOf(1499751390000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("java.text");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.util.util.CharacterTool");
    _jspx_imports_classes.add("java.net.URLEncoder");
    _jspx_imports_classes.add("com.js.oa.portal.util.RsXMLReader");
    _jspx_imports_classes.add("com.js.system.vo.messages.MessagesVO");
    _jspx_imports_classes.add("com.js.util.config.SystemCommon");
    _jspx_imports_classes.add("com.js.system.service.messages.MessagesBD");
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
      response.setContentType("text/xml; charset=GBK");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

/*
<root>
  <error></error>
  <usernum></usernum>
  <sound></sound>
  <items>
    <message>
       <mod></mod>
       <modcode></modcode>
       <title></title>
       <url></url>
       <urltype></urltype>
       <sender></sender>
       <sendtime></sendtime>
    </message>
  </items>
</root>
*/

StringBuffer buffer=new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
buffer.append("<root>");

if(session==null || session.getAttribute("userId")==null){
	buffer.append("<error>1</error>");
}else{
	String userAccount=session.getAttribute("userAccount").toString();
	String domainId = session.getAttribute("domainId")==null?"0":session.getAttribute("domainId").toString();
	String userId=session.getAttribute("userId").toString();
	
	//取得当前在线用户数目
	int userNum = new com.js.oa.online.service.OnlineDB().getOnlineUserNum(domainId,session.getId());
	buffer.append("<usernum>").append(userNum).append("</usernum>");
	buffer.append("<userAccount>").append(userAccount).append("</userAccount>");

	if("admin".equals(userAccount.toLowerCase())){
		buffer.append("<error>100</error>");
    }else{
		//判断用户是否在线
		
		
		buffer.append("<error>0</error>");
		
		Integer num=0;
		MessagesBD messagesBD=new MessagesBD();
		String statusType="";
		statusType=messagesBD.serchMessageStatus(userId);
		if("N".equals(statusType)){
		num=messagesBD.selectCountByUserID(userId);
		}
		
		//取各模块提醒信息
		buffer.append("<items cnum='"+num+"' >");
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
		List list=null;
		try {
			if("N".equals(statusType)){
		    list = messagesBD.selectByUserID(userId);	
			if(null!=list&&!list.isEmpty())
			{
			String empName,tag,time,content,messageType,urltype;
			MessagesVO messages = null;
			long msgID;
			for(int i=0;i<list.size();i++)
			{
				messages=(MessagesVO) list.get(i);
				
				tag=messages.getMessage_url();
				
				time=formatter.format(messages.getMessage_time()) ;
				empName=messages.getMessage_send_UserName();
				if(empName.charAt(empName.length()-1)==','){
					empName=empName.substring(0,empName.length()-1);
				}
				content=messages.getMessage_title();
				msgID=messages.getMessage_id();
								
				empName=CharacterTool.replaceXMLTags(empName);
				if(content==null){
					content="";
				}else{
					content=CharacterTool.replaceXMLTags(content).replace("&#39;", "");
					content=content.replaceAll("[\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f]", "");
				}
				if(tag==null){
					tag="";
				}else{
					tag=CharacterTool.replaceXMLTags(tag);
				}
				messageType=messages.getMessage_type();
				
				// 中国人寿消息判断
				if("chinaLife".equals(SystemCommon.getCustomerName())){
					if(!"".equals(tag) && "rsgw".equals(messageType)){	// 公文消息类型跳转到公文系统
						String detailLinkURL = RsXMLReader.getValue("rsgw", "detailLinkURL", "");
						tag = detailLinkURL + "?loginName=" + userAccount + "&isBase64=true&succeedRedirect=" + tag;
						tag = URLEncoder.encode(tag, "utf-8");
					}
				}
				
				urltype="1";
				if("Chat".equals(messageType))
				{
				urltype="0";
				}					
				 
				buffer.append("<message>");
				buffer.append("<mod>innermail</mod>")
				      .append("<modcode>innermail</modcode>")
				      .append("<messageid>").append(messages.getMessage_id()).append("</messageid>")
				      .append("<title>").append(content.replace("'","‘")).append("</title>")
				      .append("<url>")
				      .append(tag)
				      .append("</url>")
				      .append("<urltype>").append(urltype).append("</urltype>")
				      .append("<sender>").append(empName).append("</sender>")
				      .append("<showType>").append(messages.getMessage_show()).append("</showType>")
				      .append("<sendtime>").append(time).append("</sendtime>");
				
				buffer.append("</message>");
			}	
			}
		}
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		buffer.append("</items>");
    }
}
buffer.append("</root>");
out.clear();
out.print(buffer.toString());

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