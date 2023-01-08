/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat8
 * Generated at: 2023-01-06 09:40:12 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.scheme.task;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.js.lang.Resource;
import com.js.oa.scheme.taskcenter.service.TaskBD;
import com.js.oa.scheme.taskcenter.vo.TaskVO;
import com.js.oa.scheme.taskcenter.vo.TaskReportVO;
import java.util.List;
import java.util.Map;

public final class workmanager_005ftasksearch_005fexport_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.js.lang.Resource");
    _jspx_imports_classes.add("java.util.List");
    _jspx_imports_classes.add("com.js.oa.scheme.taskcenter.service.TaskBD");
    _jspx_imports_classes.add("java.util.Map");
    _jspx_imports_classes.add("com.js.oa.scheme.taskcenter.vo.TaskReportVO");
    _jspx_imports_classes.add("com.js.oa.scheme.taskcenter.vo.TaskVO");
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");

	String local = session.getAttribute("org.apache.struts.action.LOCALE").toString();
	Long domainId = session.getAttribute("domainId") != null ? Long.valueOf(session.getAttribute("domainId").toString()) : Long.valueOf("0");
	Long userId = new Long(session.getAttribute("userId").toString());
	TaskBD taskBD = new TaskBD();
	
	String[] taskStatusArray = new String[]{Resource.getValue(local, "personalwork", "taskcenter.notstart"),//
	Resource.getValue(local, "personalwork", "taskcenter.ongoing"),//
	Resource.getValue(local, "personalwork", "taskcenter.finished"),//
	Resource.getValue(local, "personalwork", "taskcenter.delay"),//
	Resource.getValue(local, "personalwork", "taskcenter.canceled")//
	}; 
	String[] taskPriorityArray = new String[]{Resource.getValue(local, "personalwork", "taskcenter.normal"),//
	Resource.getValue(local, "personalwork", "taskcenter.quick"),//
	Resource.getValue(local, "personalwork", "taskcenter.immediately")//
	}; 

	response.setHeader("Content-disposition", "attachment;filename=Task.xls");

      out.write("\r\n\r\n<html>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">\r\n<head>\r\n<title>");
      out.print(Resource.getValue(local, "personalwork", "taskcenter.export"));
      out.write("</title>\r\n</head>\r\n<body>\r\n\t<table border=\"1\">\r\n\t\t");

			Object[] obj = null;
			int index = 0;
			String type = request.getParameter("type") == null ? "org" : request.getParameter("type");
		
      out.write("\r\n\r\n\t\t<tr>\r\n\t\t\t<td align=\"center\"><b>序号</b></td>\r\n\t\t\t<td align=\"center\"><b>");
      out.print(Resource.getValue(local, "personalwork", "taskcenter.tasktype"));
      out.write("</b>\r\n\t\t\t</td>\r\n\t\t\t<td align=\"center\"><b>");
      out.print(Resource.getValue(local, "personalwork", "taskcenter.taskname"));
      out.write("</b>\r\n\t\t\t</td>\r\n\t\t\t<td align=\"center\"><b>");
      out.print(Resource.getValue(local, "personalwork", "taskcenter.taskdescription"));
      out.write("</b>\r\n\t\t\t</td>\r\n\t\t\t<td align=\"center\"><b>");
      out.print(Resource.getValue(local, "personalwork", "taskcenter.startdate"));
      out.write("</b>\r\n\t\t\t</td>\r\n\t\t\t<td align=\"center\"><b>");
      out.print(Resource.getValue(local, "personalwork", "taskcenter.enddate"));
      out.write("</b>\r\n\t\t\t</td>\r\n\t\t\t<td align=\"center\"><b>");
      out.print(Resource.getValue(local, "personalwork", "taskcenter.finishdate"));
      out.write("</b>\r\n\t\t\t</td>\r\n\t\t\t<td align=\"center\"><b>");
      out.print(Resource.getValue(local, "personalwork", "taskcenter.status"));
      out.write("</b>\r\n\t\t\t</td>\r\n\t\t\t<td align=\"center\"><b>");
      out.print(Resource.getValue(local, "personalwork", "taskcenter.finishedrate"));
      out.write("</b>\r\n\t\t\t</td>\r\n\t\t\t<td align=\"center\"><b>");
      out.print(Resource.getValue(local, "personalwork", "taskcenter.principal"));
      out.write("</b>\r\n\t\t\t</td>\r\n\t\t\t<td align=\"center\"><b>");
      out.print(Resource.getValue(local, "personalwork", "taskcenter.participant"));
      out.write("</b>\r\n\t\t\t</td>\r\n\t\t\t<td align=\"center\"><b>");
      out.print(Resource.getValue(local, "personalwork", "taskcenter.supervisor"));
      out.write("</b>\r\n\t\t\t</td>\r\n\t\t\t<td align=\"center\"><b>");
      out.print(Resource.getValue(local, "personalwork", "taskcenter.hurrydegree"));
      out.write("</b>\r\n\t\t\t</td>\r\n\t\t\t<td align=\"center\"><b>");
      out.print(Resource.getValue(local, "personalwork", "taskcenter.reportstatus"));
      out.write("</b>\r\n\t\t\t</td>\r\n\t\t\t<td align=\"center\"><b>相关项目</b>\r\n\t\t\t</td>\r\n\t\t\t<!--<td align=\"center\"><b>相关会议</b>\r\n\t\t\t</td>-->\r\n\t\t</tr>\r\n\r\n\t\t");
      //  logic:iterate
      org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_005fiterate_005f0 = (org.apache.struts.taglib.logic.IterateTag) _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005fid.get(org.apache.struts.taglib.logic.IterateTag.class);
      boolean _jspx_th_logic_005fiterate_005f0_reused = false;
      try {
        _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
        _jspx_th_logic_005fiterate_005f0.setParent(null);
        // /scheme/task/workmanager_tasksearch_export.jsp(76,2) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_logic_005fiterate_005f0.setId("allTask");
        // /scheme/task/workmanager_tasksearch_export.jsp(76,2) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_logic_005fiterate_005f0.setName("allTask");
        // /scheme/task/workmanager_tasksearch_export.jsp(76,2) name = scope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_logic_005fiterate_005f0.setScope("request");
        int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
        if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          java.lang.Object allTask = null;
          if (_jspx_eval_logic_005fiterate_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
            out = org.apache.jasper.runtime.JspRuntimeLibrary.startBufferedBody(_jspx_page_context, _jspx_th_logic_005fiterate_005f0);
          }
          allTask = (java.lang.Object) _jspx_page_context.findAttribute("allTask");
          do {
            out.write("\r\n\t\t\t");

				obj = (Object[]) allTask;
					Long taskId = new Long(obj[0].toString());
					TaskVO taskVO = taskBD.selectSettleSingleTask(userId, taskId, domainId);
					Map map2 = taskBD.selectTaskReport(userId, taskId, domainId);
					List list2 =null;
					if(map2!=null )
					{
					     list2 = (List) map2.get("taskReportList");
					}
					
			
            out.write("\r\n\t\t\t<tr>\r\n\t\t\t\t<td align=\"left\" height=\"27\">");
            out.print(++index);
            out.write("</td>\r\n\t\t\t\t<td align=\"left\">");
            out.print(obj[1]);
            out.write("</td>\r\n\t\t\t\t<!--任务分类-->\r\n\t\t\t\t<td align=\"left\">");
            out.print(taskVO.getTaskTitle());
            out.write("</td>\r\n\t\t\t\t<!--任务名称-->\r\n\t\t    \t<td align=\"left\">");
            out.print(taskVO.getTaskDescription()==null ? "" : taskVO.getTaskDescription());
            out.write("</td>\r\n\t\t\t\t<!--任务描述-->\r\n\t\t\t\t<td align=\"left\">");
            out.print(taskVO.getTaskBeginTimeFormat());
            out.write("</td>\r\n\t\t\t\t<!--开始时间-->\r\n\t\t\t\t<td align=\"left\">");
            out.print(taskVO.getTaskEndTimeFormat());
            out.write("</td>\r\n\t\t\t\t<!--截止时间-->\r\n\t\t\t\t<td align=\"left\">");
            out.print(taskVO.getTaskRealEndTimeFormat()==null?"" : taskVO.getTaskRealEndTimeFormat());
            out.write("</td>\r\n\t\t\t\t<!--完成时间-->\r\n\t\t\t\t<td align=\"left\">");
            out.print(taskStatusArray[taskVO.getTaskStatus()]);
            out.write("</td>\r\n\t\t\t\t<!--任务状态-->\r\n\t\t\t\t<td align=\"left\">");
            out.print(taskVO.getTaskFinishRate());
            out.write("%</td>\r\n\t\t\t\t<!--完成率-->\r\n\t\t\t\t<td align=\"left\">");
            out.print(taskVO.getTaskPrincipalName());
            out.write("</td>\r\n\t\t\t\t<!--负责人-->\r\n\t\t\t\t<td align=\"left\">");
            out.print(taskVO.getTaskJoinedEmpName()==null?"":taskVO.getTaskJoinedEmpName());
            out.write("</td>\r\n\t\t\t\t<!--参与人员-->\r\n\t\t\t\t<td align=\"left\">");
            out.print(taskVO.getTaskCheckerName()==null?"":taskVO.getTaskCheckerName());
            out.write("</td>\r\n\t\t\t\t<!--考核人-->\r\n\t\t\t\t<td align=\"left\">");
            out.print(taskPriorityArray[taskVO.getTaskPriority()]);
            out.write("</td>\r\n\t\t\t\t<!--紧急程度-->\r\n\t\t\t\t<td align=\"left\">\r\n\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\"\r\n\t\t\t\t\t\tstyle=\"border-left:0px;border-right:0px;\">\r\n\r\n\t\t\t\t\t\t");

						if(list2 != null)
							for (Object tm : list2) {
							TaskReportVO rm = (TaskReportVO)tm;
						
            out.write("\r\n\t\t\t\t\t\t<tr bgcolor=\"#CCCCCC\">\r\n\t\t\t\t\t\t\t");

								Integer fr = rm.getFinishRate();
										if (fr != null) {
							
            out.write("\r\n\t\t\t\t\t\t\t<td height=\"23\" style=\"border-right:0px\">");
            out.print(rm.getEmpName());
            out.write("&nbsp;\r\n\t\t\t\t\t\t\t\t<bean:message bundle=\"personalwork\" key=\"taskcenter.at\" />&nbsp;\r\n\t\t\t\t\t\t\t\t");
            out.print(rm.getReportTimeFormat());
            out.write("&nbsp; <font color=\"red\"><bean:message\r\n\t\t\t\t\t\t\t\t\t\tbundle=\"personalwork\" key=\"taskcenter.finish2\" />： ");
            out.print(fr);
            out.write("%</font>\r\n\t\t\t\t\t\t\t</td>\r\n\t\t\t\t\t\t\t");

								} else {
							
            out.write("\r\n\t\t\t\t\t\t\t<td height=\"23\" style=\"border-right:0px\">");
            out.print(rm.getEmpName());
            out.write("&nbsp;\r\n\t\t\t\t\t\t\t\t<bean:message bundle=\"personalwork\" key=\"taskcenter.at\" />&nbsp;\r\n\t\t\t\t\t\t\t\t");
            out.print(rm.getReportTimeFormat());
            out.write("&nbsp;</td>\r\n\t\t\t\t\t\t\t");

								}
							
            out.write("\r\n\t\t\t\t\t\t</tr>\r\n\t\t\t\t\t\t<tr bgcolor=\"#FFFFFF\">\r\n\t\t\t\t\t\t\t<td height=\"20\" colspan=\"4\" style=\"border-right:0px\"><bean:message\r\n\t\t\t\t\t\t\t\t\tbundle=\"personalwork\" key=\"taskcenter.content\" />：");
            out.print(rm.getReportInfo());
            out.write("\r\n\t\t\t\t\t\t\t</td>\r\n\t\t\t\t\t\t</tr>\r\n\t\t\t\t\t\t");

							}
						
            out.write("\r\n\t\t\t\t\t</table></td>\r\n\t\t\t\t<!--汇报情况-->\r\n\t\t\t\t<td align=\"left\">\r\n\t\t\t\t");

				String relProjectId=taskVO.getRelProjectId()==null?"-1":taskVO.getRelProjectId().toString();
			         com.js.oa.relproject.bean.RelProjectBean rpBean=new com.js.oa.relproject.bean.RelProjectBean();
			         java.util.List projectList=rpBean.getActiveProject(session.getAttribute("userId").toString(),session.getAttribute("orgId").toString(),session.getAttribute("orgIdString").toString());
			         if(projectList!=null && projectList.size()>0){
			        	 
			        	 for(int i=0;i<projectList.size();i++){
			        		 obj=(Object[])projectList.get(i);
			        		 if(relProjectId.equals(obj[0].toString())){
			        		 out.print(obj[1]);
			        		 break;
			        		 } 
			        	}
			        }
			        
			        
				
            out.write("\r\n\t\t\t\t</td>\r\n\t\t\t\t<!--相关项目-->\r\n\t\t\t\t<!--<td align=\"left\">\r\n\t\t\t\t</td>-->\r\n\t\t\t\t<!--相关会议-->\r\n\r\n\t\t\t</tr>\r\n\t\t");
            int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
            allTask = (java.lang.Object) _jspx_page_context.findAttribute("allTask");
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
      out.write("\r\n\r\n\r\n\t</table>\r\n</body>\r\n</html>\r\n<script src=\"/jsoa/js/util.js\"></script>");
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