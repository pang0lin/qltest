package com.js.wap.action;

import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.service.WorkFlowButtonBD;
import com.js.oa.jsflow.vo.ActivityVO;
import com.js.util.page.Page;
import com.js.wap.bean.WorkDealWithBean;
import com.js.wap.util.WapUtil;
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

public class WorkDealWithAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    String action = request.getParameter("action");
    if ("list".equals(action)) {
      list(request);
    } else if ("openInfo".equals(action)) {
      openInfo(request);
    } else if ("selectActivity".equals(action)) {
      getNextActivity(request);
    } else if ("selectEmp".equals(action)) {
      searchEmp(request);
    } 
    String version = (String)session.getAttribute("wapVersion");
    if ("3G".equals(version))
      action = String.valueOf(action) + "_3g"; 
    return actionMapping.findForward(action);
  }
  
  private void list(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String status = request.getParameter("status");
    String wfCurEmployeeId = session.getAttribute("userId").toString();
    String viewSql = "aaa.workFileType, aaa.workCurStep, aaa.workTitle, aaa.workDeadLine, aaa.workSubmitPerson, aaa.workSubmitTime, aaa.workType, aaa.workActivity, aaa.workTableId, aaa.workRecordId, aaa.wfWorkId, aaa.workSubmitPerson, aaa.wfSubmitEmployeeId, aaa.workAllowCancel, aaa.workProcessId, aaa.workStepCount,aaa.workMainLinkFile, aaa.workSubmitTime, aaa.workCurStep, aaa.creatorCancelLink, aaa.isStandForWork, aaa.standForUserId, aaa.standForUserName,aaa.workCreateDate,aaa.submitOrg,aaa.workDoneWithDate,aaa.emergence,aaa.initActivity,aaa.initActivityName,aaa.tranType, aaa.tranFromPersonId, aaa.processDeadlineDate,aaa.wfCurEmployeeId ";
    String fromSql = " com.js.oa.jsflow.po.WFWorkPO aaa ";
    String whereSql = " where aaa.workStatus = " + status + " and aaa.wfCurEmployeeId = " + wfCurEmployeeId + 
      " and aaa.workListControl = 1 and aaa.workDelete = 0 ";
    if (request.getParameter("showDoc") != null && "doc".equals(request.getParameter("showDoc")))
      whereSql = String.valueOf(whereSql) + " and aaa.workProcessId in (" + docFlowIds() + ") "; 
    whereSql = String.valueOf(whereSql) + "order by aaa.emergence desc, aaa.wfWorkId desc";
    int pageSize = WapUtil.LIMITED;
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
  }
  
  private String openInfo(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String workId = request.getParameter("workId");
    WorkDealWithBean bean = new WorkDealWithBean();
    Map map = bean.getWorkDealWithSimpleInfo(workId);
    request.setAttribute("itemInfo", map);
    return null;
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
}
