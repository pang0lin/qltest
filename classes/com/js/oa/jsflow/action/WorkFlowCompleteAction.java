package com.js.oa.jsflow.action;

import com.js.oa.archives.service.ArchivesBD;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WorkFlowCompleteAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    WorkFlowFormActionForm workFlowFormActionForm = (WorkFlowFormActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    String tableId = httpServletRequest.getParameter("tableId");
    String recordId = httpServletRequest.getParameter("recordId");
    String workId = httpServletRequest.getParameter("workId");
    String comment = httpServletRequest.getParameter("comment");
    String updateValue = httpServletRequest.getParameter("updateValue");
    String curActivityId = httpServletRequest.getParameter("curActivityId");
    String curActivityName = httpServletRequest.getParameter("curActivityName");
    String processName = httpServletRequest.getParameter("processName");
    String submitPerson = httpServletRequest.getParameter("submitPerson");
    String remindFieldValue = httpServletRequest.getParameter("remindFieldValue");
    String curTransactType = httpServletRequest.getParameter("curTransactType");
    HttpSession httpSession = httpServletRequest.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String stepCount = httpServletRequest.getParameter("stepCount");
    WorkFlowBD workFlowBD = new WorkFlowBD();
    String tableName = workFlowBD.getTableName(tableId);
    if (!updateValue.equals("")) {
      ArrayList<String> alist = new ArrayList();
      alist.add("update " + tableName + " set " + 
          updateValue.substring(0, updateValue.length() - 1) + 
          " where " + tableName + "_id = " + recordId);
      workFlowBD.updateTable(alist);
    } 
    String[] dealPara = { 
        tableId, recordId, curActivityName, curActivityId, userId, 
        comment, "", "-1", stepCount, httpServletRequest.getParameter("isStandForWork"), 
        httpServletRequest.getParameter("standForUserId"), 
        httpServletRequest.getParameter("activityClass"), 
        "0" };
    WorkFlowBD newWorkFlowBD = new WorkFlowBD();
    newWorkFlowBD.insertDealWith(dealPara);
    String[] para = { 
        tableId, recordId, curActivityId, processName, submitPerson, 
        remindFieldValue, curTransactType, stepCount, 
        httpServletRequest.getParameter("isStandForWork"), 
        userId, 
        httpServletRequest.getParameter("standForUserId"), 
        httpServletRequest.getParameter("standForUserName"), "" };
    String tmp = newWorkFlowBD.completeWork(para, workId);
    if (tmp != null && !tmp.equals("")) {
      String[] work = newWorkFlowBD.getCurCompleteWork(tableId, recordId);
      String returnValue = (new ArchivesBD()).archivesPigeonholeSet("GZLC", (httpServletRequest.getSession(true).getAttribute("domainId") == null) ? "-1" : httpServletRequest.getSession(true).getAttribute("domainId").toString());
      if (!"".equals(returnValue) && !"-1".equals(returnValue) && (
        new ProcessBD()).getIsDossier(work[3]).intValue() == 1) {
        String myhref = 
          "/jsoa/WorkFlowProcAction.do?flowpara=1&activity=" + curActivityId + "&table=" + 
          tableId + "&record=" + 
          recordId + "&activityName=" + 
          curActivityName + 
          "&submitPersonId=" + 
          httpServletRequest.getParameter("submitPersonId") + 
          "&submitPerson=" + submitPerson + "&work=" + work[0] + 
          "&workType=" + 
          work[1] + "&processName=" + processName + 
          "&processId=" + work[3] + 
          "&workStatus=100&submitTime=" + work[2];
        httpServletRequest.setAttribute("myhref", myhref);
        return actionMapping.findForward("workflowGD");
      } 
    } 
    return actionMapping.findForward("success");
  }
}
