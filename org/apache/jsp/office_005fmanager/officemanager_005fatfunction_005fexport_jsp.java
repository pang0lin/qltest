/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 10:02:00 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.office_005fmanager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.system.vo.usermanager.EmployeeOtherInfoVO;
import java.text.DateFormat;
import java.util.*;
import com.js.oa.hr.personnelmanager.service.PersonalKindBD;

public final class officemanager_005fatfunction_005fexport_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/WEB-INF/tag-lib/struts-logic.tld", Long.valueOf(1499751390000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.system.vo.usermanager.EmployeeVO");
    _jspx_imports_classes.add("com.js.oa.hr.personnelmanager.service.PersonalKindBD");
    _jspx_imports_classes.add("java.text.DateFormat");
    _jspx_imports_classes.add("com.js.system.vo.usermanager.EmployeeOtherInfoVO");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid;

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
    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid.release();
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
      response.setContentType("application/vnd.ms-excel;charset=GBK");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n<html>\r\n<meta http-equiv=\"Content-Type\" content=\"application/vnd.ms-excel; charset=GBK\">\r\n");

response.setHeader("Content-disposition", "attachment;filename="+new String("员工信息".getBytes("GBK"),"iso8859-1")+".xls");

List list = new PersonalKindBD().list();

      out.write("\r\n<head>\r\n    <title>导出</title>\r\n</head>\r\n<body>\r\n<table border=\"1\">\r\n    <tr>\r\n        <td align=\"center\"><b>工号</b></td>\r\n        <td align=\"center\"><b>组织</b></td>\r\n        <td align=\"center\"><b>姓名</b></td>\r\n\t\t<td align=\"center\"><b>帐号</b></td>\r\n        <td align=\"center\"><b>工段</b></td>\r\n\t\t<td align=\"center\"><b>人员性质</b></td>\r\n        <td align=\"center\"><b>身份</b></td>\r\n        <td align=\"center\"><b>民族</b></td>\r\n        <td align=\"center\"><b>籍贯</b></td>\r\n        <td align=\"center\"><b>政治面貌</b></td>\r\n        <td align=\"center\"><b>出生年月</b></td>\r\n        <td align=\"center\"><b>性别</b></td>\r\n        <td align=\"center\"><b>学历</b></td>\r\n        <td align=\"center\"><b>参加工作日期</b></td>\r\n        <td align=\"center\"><b>进公司日期</b></td>\r\n        <td align=\"center\"><b>岗位</b></td>\r\n        <td align=\"center\"><b>职务级别</b></td>\r\n        <td align=\"center\"><b>入党日期</b></td>\r\n        <td align=\"center\"><b>在职状态</b></td>\r\n        <td align=\"center\"><b>用工性质</b></td>\r\n        <td align=\"center\"><b>身份证号</b></td>\r\n       \r\n        \r\n        <td align=\"center\"><b>个人特长和爱好</b></td>\r\n");
      out.write("        <td align=\"center\"><b>家庭住址</b></td>\r\n        <td align=\"center\"><b>住宅电话</b></td>\r\n        <td align=\"center\"><b>手机号码</b></td>\r\n        <td align=\"center\"><b>E-Mail</b></td>\r\n        <td align=\"center\"><b>家庭信息</b></td>\r\n        <td align=\"center\"><b>曾用名</b></td>\r\n        <td align=\"center\"><b>出生地</b></td>\r\n        <td align=\"center\"><b>户口所在地</b></td>\r\n        <td align=\"center\"><b>婚姻状况</b></td>\r\n        <td align=\"center\"><b>健康状况</b></td>\r\n        <td align=\"center\"><b>全日制最高学历</b></td>\r\n        <td align=\"center\"><b>全日制最高学位</b></td>\r\n        <td align=\"center\"><b>全日制毕业院校系</b></td>\r\n        <td align=\"center\"><b>全日制专业</b></td>\r\n        <td align=\"center\"><b>在职教育最高学历</b></td>\r\n        <td align=\"center\"><b>在职教育最高学位</b></td>\r\n        <td align=\"center\"><b>在职教育毕业院校系</b></td>\r\n        <td align=\"center\"><b>在职教育专业</b></td>\r\n        <td align=\"center\"><b>专业技术职称</b></td>\r\n        <td align=\"center\"><b>职业资格</b></td>\r\n        <td align=\"center\"><b>任现职时间</b></td>\r\n        <td align=\"center\"><b>任现级时间</b></td>\r\n        \r\n");
      out.write("    </tr>\r\n    ");
EmployeeVO empVO = null;
    Object[] empObj = null;
    String orgName = "";
    DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.CHINESE);
      out.write("\r\n    ");
      //  logic:iterate
      org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_005fiterate_005f0 = (org.apache.struts.taglib.logic.IterateTag) _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid.get(org.apache.struts.taglib.logic.IterateTag.class);
      boolean _jspx_th_logic_005fiterate_005f0_reused = false;
      try {
        _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
        _jspx_th_logic_005fiterate_005f0.setParent(null);
        // /office_manager/officemanager_atfunction_export.jsp(73,4) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_logic_005fiterate_005f0.setId("empList");
        // /office_manager/officemanager_atfunction_export.jsp(73,4) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_logic_005fiterate_005f0.setName("empList");
        // /office_manager/officemanager_atfunction_export.jsp(73,4) name = scope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_logic_005fiterate_005f0.setScope("request");
        int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
        if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          java.lang.Object empList = null;
          if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
            out = org.apache.jasper.runtime.JspRuntimeLibrary.startBufferedBody(_jspx_page_context, _jspx_th_logic_005fiterate_005f0);
          }
          empList = (java.lang.Object) _jspx_page_context.findAttribute("empList");
          do {
            out.write("\r\n        ");

        empObj = (Object[]) empList;
        empVO = (EmployeeVO) empObj[0];
        orgName = empObj[1]==null?"&nbsp;":empObj[1].toString();
        EmployeeOtherInfoVO other = (EmployeeOtherInfoVO)empObj[2];
		String personKindName = "";
		if(list != null && list.size() > 0){
			for(int i=0; i<list.size(); i++){
				Object[] o = (Object[])list.get(i);
				if(empVO.getPersonalKind()!=null && o[0].toString().equals(empVO.getPersonalKind().toString())){
					personKindName = o[1]!=null?o[1].toString():"";
					break;
				}
			}
		}
		
            out.write("\r\n        <tr>\r\n            <td align=\"left\">");
            out.print(empVO.getEmpNumber()==null?"&nbsp;":empVO.getEmpNumber());
            out.write("&nbsp;</td>\r\n            <td align=\"left\">");
            out.print(orgName);
            out.write("&nbsp;</td>\r\n            <td align=\"left\">");
            out.print(empVO.getEmpName());
            out.write("&nbsp;</td>\r\n\t\t\t<td align=\"left\">");
            out.print(empVO.getUserAccounts()!=null?empVO.getUserAccounts():"");
            out.write("&nbsp;</td>\r\n            <td align=\"left\">");
            out.print(empVO.getSection()==null?"&nbsp;":empVO.getSection());
            out.write("&nbsp;</td>\r\n\t\t\t<td align=\"left\">");
            out.print(personKindName);
            out.write("&nbsp;</td>\r\n            <td align=\"left\">");
            out.print(empVO.getDignity()==null?"&nbsp;":empVO.getDignity());
            out.write("&nbsp;</td>\r\n            <td align=\"left\">");
            out.print(empVO.getEmpNation()==null?"&nbsp;":empVO.getEmpNation());
            out.write("&nbsp;</td>\r\n            <td align=\"left\">");
            out.print(empVO.getEmpNativePlace()==null?"&nbsp;":empVO.getEmpNativePlace());
            out.write("&nbsp;</td>\r\n            <td align=\"left\">");
            out.print(empVO.getEmpPolity()==null || empVO.getEmpPolity().equals("0")?"&nbsp;":empVO.getEmpPolity());
            out.write("&nbsp;</td>\r\n            <td align=\"left\">");
            out.print(empVO.getEmpBirth()==null?"&nbsp;":df.format(empVO.getEmpBirth()));
            out.write("&nbsp;</td>\r\n            <td align=\"left\">");
            out.print(empVO.getEmpSex()==0?"男":"女");
            out.write("&nbsp;</td>\r\n            <td align=\"left\">");
            out.print((empVO.getEmpStudyExperience() == null || empVO.getEmpStudyExperience().equals("0"))?"&nbsp;":empVO.getEmpStudyExperience());
            out.write("&nbsp;</td>\r\n           <td align=\"left\">");
            out.print(empVO.getEmpFireDate() == null?"&nbsp;":df.format(empVO.getEmpFireDate()));
            out.write("&nbsp;</td>\r\n            <td align=\"left\">");
            out.print(empVO.getIntoCompanyDate() == null?"&nbsp;":df.format(empVO.getIntoCompanyDate()));
            out.write("&nbsp;</td>\r\n            <td align=\"left\">");
            out.print((empVO.getEmpPosition() == null || empVO.getEmpPosition().equals("0"))?"&nbsp;":empVO.getEmpPosition());
            out.write("&nbsp;</td>\r\n            <td align=\"left\">");
            out.print((empVO.getEmpDuty() == null || empVO.getEmpDuty().equals("0"))?"&nbsp;":empVO.getEmpDuty());
            out.write("&nbsp;</td>\r\n            <td align=\"left\">");
            out.print(empVO.getPartyDate() == null?"&nbsp;":empVO.getPartyDate());
            out.write("&nbsp;</td>\r\n            <td align=\"left\">");
            out.print((empVO.getJobStatus() == null || "".equals(empVO.getJobStatus().trim()))?"正式":empVO.getJobStatus());
            out.write("&nbsp;</td>\r\n            <td align=\"left\">");
            out.print(empVO.getEmpResumeNum() == null?"&nbsp;":empVO.getEmpResumeNum());
            out.write("&nbsp;</td>\r\n            <td align=\"left\">");
            out.print(("0".equals(empVO.getEmpIdCard())||empVO.getEmpIdCard()==null)?"&nbsp;":(empVO.getEmpIdCard()+""));
            out.write("&nbsp;</td>\r\n              \r\n              \r\n            <td align=\"left\">");
            out.print(empVO.getEmpInterest() == null?"&nbsp;":empVO.getEmpInterest());
            out.write("</td>\r\n            <td align=\"left\">");
            out.print(empVO.getEmpAddress() == null?"&nbsp;":empVO.getEmpAddress());
            out.write("</td>\r\n            <td align=\"left\">");
            out.print(empVO.getEmpPhone() == null?"&nbsp;":empVO.getEmpPhone());
            out.write("</td>\r\n            <td align=\"left\">");
            out.print(empVO.getEmpMobilePhone() == null?"&nbsp;":empVO.getEmpMobilePhone());
            out.write("</td>\r\n            <td align=\"left\">");
            out.print(empVO.getEmpEmail() == null?"&nbsp;":empVO.getEmpEmail());
            out.write("</td>\r\n            <td align=\"left\">");
            out.print(empVO.getFamilyMember() == null?"&nbsp;":empVO.getFamilyMember());
            out.write("</td>\r\n            <td align=\"left\">");
            out.print(other.getCym() == null?"&nbsp;":other.getCym());
            out.write("</td>\r\n            <td align=\"left\">");
            out.print(other.getCsd() == null?"&nbsp;":other.getCsd());
            out.write("</td>\r\n            <td align=\"left\">");
            out.print(other.getHkszd() == null?"&nbsp;":other.getHkszd());
            out.write("</td>\r\n            <td align=\"left\">");
            out.print(other.getHyzk() == null?"&nbsp;":other.getHyzk());
            out.write("</td>\r\n            <td align=\"left\">");
            out.print(other.getJkzk() == null?"&nbsp;":other.getJkzk());
            out.write("</td>\r\n            <td align=\"left\">");
            out.print(other.getQrz_zgxl() == null?"&nbsp;":other.getQrz_zgxl());
            out.write("</td>\r\n            <td align=\"left\">");
            out.print(other.getQrz_zgxw() == null?"&nbsp;":other.getQrz_zgxw());
            out.write("</td>\r\n            <td align=\"left\">");
            out.print(other.getQrz_byyxx() == null?"&nbsp;":other.getQrz_byyxx());
            out.write("</td>\r\n            <td align=\"left\">");
            out.print(other.getQrz_zy() == null?"&nbsp;":other.getQrz_zy());
            out.write("</td>\r\n            <td align=\"left\">");
            out.print(other.getZzjy_zgxl() == null?"&nbsp;":other.getZzjy_zgxl());
            out.write("</td>\r\n            <td align=\"left\">");
            out.print(other.getZzjy_zgxw() == null?"&nbsp;":other.getZzjy_zgxw());
            out.write("</td>\r\n            <td align=\"left\">");
            out.print(other.getZzjy_byyxx() == null?"&nbsp;":other.getZzjy_byyxx());
            out.write("</td>\r\n            <td align=\"left\">");
            out.print(other.getZzjy_zy() == null?"&nbsp;":other.getZzjy_zy());
            out.write("</td>\r\n            <td align=\"left\">");
            out.print(other.getZyjszc() == null?"&nbsp;":other.getZyjszc());
            out.write("</td>\r\n            <td align=\"left\">");
            out.print(other.getZyzg() == null?"&nbsp;":other.getZyzg());
            out.write("</td>\r\n            <td align=\"left\">");
            out.print(other.getRxzsj() == null?"&nbsp;":other.getRxzsj());
            out.write("</td>\r\n            <td align=\"left\">");
            out.print(other.getRxjsj() == null?"&nbsp;":other.getRxjsj());
            out.write("</td>\r\n        </tr>\r\n    ");
            int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
            empList = (java.lang.Object) _jspx_page_context.findAttribute("empList");
            if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
              break;
          } while (true);
          if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
            out = _jspx_page_context.popBody();
          }
        }
        if (_jspx_th_logic_005fiterate_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return;
        }
        _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
        _jspx_th_logic_005fiterate_005f0_reused = true;
      } finally {
        org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_logic_005fiterate_005f0, _jsp_getInstanceManager(), _jspx_th_logic_005fiterate_005f0_reused);
      }
      out.write("\r\n</table>\r\n</body>\r\n</html>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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