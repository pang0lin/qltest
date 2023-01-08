package com.js.oa.weixin.backlog;

import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.service.WorkFlowButtonBD;
import com.js.oa.jsflow.vo.ActivityVO;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.wap.bean.WorkDealWithBean;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WeiXinBacklogAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    String action = request.getParameter("action");
    String tag = "";
    request.setAttribute("appBh", "dbsx");
    if ("list".equals(action))
      tag = list(request, session); 
    if ("openInfo".equals(action))
      tag = openInfo(request, session); 
    if ("mine".equals(action))
      tag = myList(request, session); 
    if ("selectActivity".equals(action))
      getNextActivity(request); 
    if ("selectEmp".equals(action))
      searchEmp(request); 
    return actionMapping.findForward(tag);
  }
  
  private String commitList(HttpServletRequest request, HttpSession session) {
    String keyword = (request.getParameter("keyword") != null) ? request.getParameter("keyword") : "";
    if (keyword != null && !"".equals(keyword))
      try {
        keyword = URLDecoder.decode(keyword, "utf-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }  
    String wfCurEmployeeId = session.getAttribute("userId").toString();
    String viewSql = "aaa.workFileType,aaa.workCurStep,aaa.workTitle,aaa.workDeadLine,aaa.workSubmitPerson,aaa.workSubmitTime,aaa.workType,aaa.workActivity,aaa.workTableId,aaa.workRecordId,aaa.wfWorkId,aaa.workSubmitPerson,aaa.wfSubmitEmployeeId,aaa.workAllowCancel,aaa.workProcessId,aaa.workStepCount,aaa.workMainLinkFile,aaa.workSubmitTime,aaa.workCurStep,aaa.creatorCancelLink,aaa.isStandForWork,aaa.standForUserId,aaa.standForUserName,aaa.workCreateDate,aaa.submitOrg,aaa.workDoneWithDate,aaa.emergence,aaa.initActivity,aaa.initActivityName,aaa.tranType,aaa.tranFromPersonId,aaa.processDeadlineDate,aaa.wfCurEmployeeId,aaa.relProjectId,aaa.workHangup,aaa.workDeadlineDate,aaa.stickie";
    String fromSql = " com.js.oa.jsflow.po.WFWorkPO aaa ";
    String whereSql = " where 1<2 ";
    if (request.getParameter("showDoc") != null && "doc".equals(request.getParameter("showDoc")))
      whereSql = String.valueOf(whereSql) + " and aaa.workProcessId in (" + docFlowIds() + ") "; 
    if (keyword != null && !"".equals(keyword))
      whereSql = String.valueOf(whereSql) + " and aaa.workTitle like '%" + keyword + "%' "; 
    whereSql = String.valueOf(whereSql) + " and aaa.workStatus=101  AND aaa.wfCurEmployeeId=" + 
      wfCurEmployeeId + " AND aaa.workListControl=1 AND aaa.workDelete = 0 ";
    whereSql = String.valueOf(whereSql) + " ORDER BY aaa.stickie DESC,aaa.wfWorkId DESC";
    int pageSize = SystemCommon.getPageNum();
    int offset = 0;
    if (request.getParameter("beginIndex") != null)
      offset = Integer.parseInt(request.getParameter("beginIndex")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSql, fromSql, whereSql);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    request.setAttribute("itemList", list);
    request.setAttribute("RECORD_COUNT", recordCount);
    request.setAttribute("keyword", keyword);
    request.setAttribute("action", "mine");
    return "myList";
  }
  
  private String myList(HttpServletRequest request, HttpSession session) {
    String keyword = (request.getParameter("keyword") != null) ? request.getParameter("keyword") : "";
    if (keyword != null && !"".equals(keyword))
      try {
        keyword = URLDecoder.decode(keyword, "utf-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }  
    String wfCurEmployeeId = session.getAttribute("userId").toString();
    String workStatus = (request.getParameter("workStatus") != null) ? request.getParameter("workStatus") : "1";
    String viewSql = "aaa.workFileType,aaa.workCurStep,aaa.workTitle,aaa.workDeadLine,aaa.workSubmitPerson,aaa.workSubmitTime,aaa.workType,aaa.workActivity,aaa.workTableId,aaa.workRecordId,aaa.wfWorkId,aaa.workSubmitPerson,aaa.wfSubmitEmployeeId,aaa.workAllowCancel,aaa.workProcessId,aaa.workStepCount,aaa.workMainLinkFile,aaa.workSubmitTime,aaa.workCurStep,aaa.creatorCancelLink,aaa.isStandForWork,aaa.standForUserId,aaa.standForUserName,aaa.workCreateDate,aaa.submitOrg,aaa.workDoneWithDate,aaa.emergence,aaa.initActivity,aaa.initActivityName,aaa.tranType,aaa.tranFromPersonId,aaa.processDeadlineDate,aaa.wfCurEmployeeId,aaa.relProjectId,aaa.workHangup,aaa.workDeadlineDate,aaa.stickie,emp.userAccounts ";
    String fromSql = " com.js.oa.jsflow.po.WFWorkPO aaa,com.js.system.vo.usermanager.EmployeeVO emp ";
    String whereSql = " where 1<2 and aaa.wfSubmitEmployeeId=emp.empId ";
    if (request.getParameter("showDoc") != null && "doc".equals(request.getParameter("showDoc")))
      whereSql = String.valueOf(whereSql) + " and aaa.workProcessId in (" + docFlowIds() + ") "; 
    if (keyword != null && !"".equals(keyword))
      whereSql = String.valueOf(whereSql) + " and aaa.workTitle like '%" + keyword + "%' "; 
    whereSql = String.valueOf(whereSql) + " and aaa.workStatus=" + workStatus;
    whereSql = String.valueOf(whereSql) + " AND aaa.wfSubmitEmployeeId=" + wfCurEmployeeId + " AND aaa.workListControl=1 AND aaa.workDelete = 0 ";
    whereSql = String.valueOf(whereSql) + " ORDER BY aaa.stickie DESC,aaa.wfWorkId DESC";
    int pageSize = SystemCommon.getPageNum();
    int offset = 0;
    if (request.getParameter("beginIndex") != null)
      offset = Integer.parseInt(request.getParameter("beginIndex")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSql, fromSql, whereSql);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    request.setAttribute("workStatus", workStatus);
    request.setAttribute("itemList", list);
    request.setAttribute("RECORD_COUNT", recordCount);
    request.setAttribute("keyword", keyword);
    request.setAttribute("action", "mine");
    return "myList";
  }
  
  private String list(HttpServletRequest request, HttpSession session) {
    String status = request.getParameter("status");
    String keyword = (request.getParameter("keyword") != null) ? request.getParameter("keyword") : "";
    if (keyword != null && !"".equals(keyword))
      try {
        keyword = URLDecoder.decode(keyword, "utf-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }  
    String wfCurEmployeeId = session.getAttribute("userId").toString();
    String viewSql = "aaa.workFileType, aaa.workCurStep, aaa.workTitle, aaa.workDeadLine, aaa.workSubmitPerson, aaa.workSubmitTime, aaa.workType, aaa.workActivity, aaa.workTableId, aaa.workRecordId, aaa.wfWorkId, aaa.workSubmitPerson, aaa.wfSubmitEmployeeId, aaa.workAllowCancel, aaa.workProcessId, aaa.workStepCount,aaa.workMainLinkFile, aaa.workSubmitTime, aaa.workCurStep, aaa.creatorCancelLink, aaa.isStandForWork, aaa.standForUserId, aaa.standForUserName,aaa.workCreateDate,aaa.submitOrg,aaa.workDoneWithDate,aaa.emergence,aaa.initActivity,aaa.initActivityName,aaa.tranType, aaa.tranFromPersonId, aaa.processDeadlineDate,aaa.wfCurEmployeeId,emp.userAccounts ";
    String fromSql = " com.js.oa.jsflow.po.WFWorkPO aaa,com.js.system.vo.usermanager.EmployeeVO emp ";
    String whereSql = " where 1<2 and aaa.wfSubmitEmployeeId=emp.empId ";
    if (request.getParameter("showDoc") != null && "doc".equals(request.getParameter("showDoc")))
      whereSql = String.valueOf(whereSql) + " and aaa.workProcessId in (" + docFlowIds() + ") "; 
    if (keyword != null && !"".equals(keyword))
      whereSql = String.valueOf(whereSql) + " and aaa.workTitle like '%" + keyword + "%' "; 
    if (request.getParameter("wechatShowDoc") != null && !"".equals(request.getParameter("wechatShowDoc")))
      whereSql = String.valueOf(whereSql) + " and aaa.workProcessId in (" + docFlowIdsByType(request.getParameter("wechatShowDoc")) + ") "; 
    whereSql = String.valueOf(whereSql) + " and aaa.workListControl = 1 and aaa.workDelete = 0 ";
    whereSql = String.valueOf(whereSql) + " and aaa.workStatus = " + status + " and aaa.wfCurEmployeeId = " + wfCurEmployeeId;
    whereSql = String.valueOf(whereSql) + " order by aaa.emergence desc, aaa.wfWorkId desc";
    String dataType = SystemCommon.getDatabaseType();
    if (dataType.indexOf("mysql") >= 0) {
      whereSql = whereSql.replaceFirst("where", "where (aaa.activityDelaySend is null or aaa.activityDelaySend = '' or aaa.activityDelaySend<=date_format(now(),'%Y-%c-%d %H:%i:%s')) AND ");
    } else {
      whereSql = whereSql.replaceFirst("where", "where (aaa.activityDelaySend is null or aaa.activityDelaySend = '' or TO_DATE(aaa.activityDelaySend,'yyyy-mm-dd hh24:mi:ss')<=SYSDATE) AND ");
    } 
    int pageSize = SystemCommon.getPageNum();
    int offset = 0;
    if (request.getParameter("beginIndex") != null)
      offset = Integer.parseInt(request.getParameter("beginIndex")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSql, fromSql, whereSql);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    request.setAttribute("itemList", list);
    request.setAttribute("RECORD_COUNT", recordCount);
    request.setAttribute("keyword", keyword);
    request.setAttribute("action", "list");
    request.setAttribute("status", status);
    if ("0".equals(status)) {
      request.setAttribute("cardTitle", "待办事项");
      if (request.getParameter("wechatShowDoc") != null && !"".equals(request.getParameter("wechatShowDoc"))) {
        if ("receive".equals(request.getParameter("wechatShowDoc"))) {
          request.setAttribute("cardTitle", "收文待办");
        } else {
          request.setAttribute("cardTitle", "发文待办");
        } 
        request.setAttribute("wechatShowDoc", request.getParameter("wechatShowDoc"));
      } 
    } else if ("2".equals(status)) {
      request.setAttribute("cardTitle", "待阅事项");
    } else if ("101".equals(status)) {
      request.setAttribute("from", "mine");
      request.setAttribute("cardTitle", "已办事项");
    } else if ("102".equals(status)) {
      request.setAttribute("from", "mine");
      request.setAttribute("cardTitle", "已阅事项");
    } 
    return "list";
  }
  
  private String openInfo(HttpServletRequest request, HttpSession session) {
    String workId = request.getParameter("workId");
    WorkDealWithBean bean = new WorkDealWithBean();
    Map map = bean.getWorkDealWithSimpleInfo(workId);
    request.setAttribute("itemInfo", map);
    return "openInfo";
  }
  
  private void getNextActivity(HttpServletRequest request) {
    WorkFlowBD workFlowBD = new WorkFlowBD();
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    ActivityVO activityVO = null;
    List activityList = new ArrayList();
    String curActivityId = request.getParameter("curActivityId");
    String tableId = request.getParameter("tableId");
    String recordId = request.getParameter("recordId");
    String activityClass = workFlowBD.getNextActivityClass(curActivityId, tableId, recordId, "");
    String stepType = workFlowBD.getActivityType(curActivityId, tableId, recordId);
    if (activityClass.equals("0"))
      if (!stepType.equals("0"))
        activityList = workFlowBD.getAllNextActivity(tableId, recordId, curActivityId);  
    request.setAttribute("activityList", activityList);
  }
  
  private void searchEmp(HttpServletRequest request) {
    String queryName = request.getParameter("empName");
  }
  
  private String docFlowIds() {
    WorkDealWithBean bean = new WorkDealWithBean();
    return bean.docFlowIds();
  }
  
  private String docFlowIdsByType(String type) {
    WorkDealWithBean bean = new WorkDealWithBean();
    return bean.docFlowIdsByType(type);
  }
}
