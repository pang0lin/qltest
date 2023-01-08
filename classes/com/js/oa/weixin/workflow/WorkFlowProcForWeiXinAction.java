package com.js.oa.weixin.workflow;

import com.js.oa.eform.service.CustomFormBD;
import com.js.oa.jsflow.action.WorkFlowFormActionForm;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.jsflow.vo.ActivityVO;
import java.net.URLDecoder;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WorkFlowProcForWeiXinAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    String processId = httpServletRequest.getParameter("processId");
    if (processId == null || "".equals(processId)) {
      httpServletRequest.setAttribute("failure", "workflow");
      return actionMapping.findForward("failure");
    } 
    String search = httpServletRequest.getParameter("search");
    String workTitle = httpServletRequest.getParameter("workTitle");
    String work = httpServletRequest.getParameter("work");
    String workIsParallel = "0";
    String workParallelId = "0";
    String workParallelStep = "0";
    String workPrarllelCurActId = "0";
    String curActivityIsBranch = "0";
    String[] workParallelInfo = (new WorkFlowCommonBD()).getWorkParallelInfo(work);
    if (workParallelInfo != null) {
      workIsParallel = workParallelInfo[0];
      workParallelId = workParallelInfo[1];
      workParallelStep = workParallelInfo[2];
      workPrarllelCurActId = workParallelInfo[3];
      curActivityIsBranch = workParallelInfo[4];
    } 
    httpServletRequest.setAttribute("workIsParallel", workIsParallel);
    httpServletRequest.setAttribute("workParallelId", workParallelId);
    httpServletRequest.setAttribute("workParallelStep", workParallelStep);
    httpServletRequest.setAttribute("workPrarllelCurActId", workPrarllelCurActId);
    httpServletRequest.setAttribute("curActivityIsBranch", curActivityIsBranch);
    String workType = httpServletRequest.getParameter("workType");
    String stepCount = httpServletRequest.getParameter("stepCount");
    String activityId = httpServletRequest.getParameter("activity");
    String backto = httpServletRequest.getParameter("backto");
    String initActivity = httpServletRequest.getParameter("initActivity");
    String fromDossierData = httpServletRequest.getParameter("fromDossierData");
    String workStatusStr = httpServletRequest.getParameter("workStatus");
    String table = httpServletRequest.getParameter("table");
    String record = httpServletRequest.getParameter("record");
    String activityName = httpServletRequest.getParameter("activityName");
    String isStandForWork = httpServletRequest.getParameter("isStandForWork");
    String standForUserId = httpServletRequest.getParameter("standForUserId");
    String standForUserName = httpServletRequest.getParameter("standForUserName");
    String submitPerson = httpServletRequest.getParameter("submitPerson");
    String submitPersonId = httpServletRequest.getParameter("submitPersonId");
    String submitTime = httpServletRequest.getParameter("submitTime");
    try {
      if (processId != null && !"".equals(processId))
        processId = URLDecoder.decode(processId, "utf-8"); 
      if (search != null && !"".equals(search))
        search = URLDecoder.decode(search, "utf-8"); 
      if (workTitle != null && !"".equals(workTitle))
        workTitle = URLDecoder.decode(workTitle, "utf-8"); 
      if (work != null && !"".equals(work))
        work = URLDecoder.decode(work, "utf-8"); 
      if (workType != null && !"".equals(workType))
        workType = URLDecoder.decode(workType, "utf-8"); 
      if (stepCount != null && !"".equals(stepCount))
        stepCount = URLDecoder.decode(stepCount, "utf-8"); 
      if (activityId != null && !"".equals(activityId))
        activityId = URLDecoder.decode(activityId, "utf-8"); 
      if (backto != null && !"".equals(backto))
        backto = URLDecoder.decode(backto, "utf-8"); 
      if (initActivity != null && !"".equals(initActivity))
        initActivity = URLDecoder.decode(initActivity, "utf-8"); 
      if (fromDossierData != null && !"".equals(fromDossierData))
        fromDossierData = URLDecoder.decode(fromDossierData, "utf-8"); 
      if (workStatusStr != null && !"".equals(workStatusStr))
        workStatusStr = URLDecoder.decode(workStatusStr, "utf-8"); 
      if (table != null && !"".equals(table))
        table = URLDecoder.decode(table, "utf-8"); 
      if (record != null && !"".equals(record))
        record = URLDecoder.decode(record, "utf-8"); 
      if (activityName != null && !"".equals(activityName))
        activityName = URLDecoder.decode(URLDecoder.decode(activityName, "utf-8"), "utf-8"); 
      if (isStandForWork != null && !"".equals(isStandForWork))
        isStandForWork = URLDecoder.decode(isStandForWork, "utf-8"); 
      if (standForUserId != null && !"".equals(standForUserId))
        standForUserId = URLDecoder.decode(standForUserId, "utf-8"); 
      if (standForUserName != null && !"".equals(standForUserName))
        standForUserName = URLDecoder.decode(standForUserName, "utf-8"); 
      if (submitPerson != null && !"".equals(submitPerson))
        submitPerson = URLDecoder.decode(submitPerson, "utf-8"); 
      if (submitPersonId != null && !"".equals(submitPersonId))
        submitPersonId = URLDecoder.decode(submitPersonId, "utf-8"); 
      if (submitTime != null && !"".equals(submitTime))
        submitTime = URLDecoder.decode(submitTime, "utf-8"); 
    } catch (Exception exception) {}
    WorkFlowFormActionForm workFlowFormActionForm = (WorkFlowFormActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    String userId = session.getAttribute("userId").toString();
    httpServletRequest.setAttribute("gd", "1");
    List<Object[]> processList = (new ProcessBD()).getProcInfo(processId);
    if (processList != null && processList.size() > 0) {
      Object[] obj = processList.get(0);
      httpServletRequest.setAttribute("formType", obj[23]);
      httpServletRequest.setAttribute("jspFile", obj[25]);
    } else {
      httpServletRequest.setAttribute("formType", "0");
    } 
    httpServletRequest.setAttribute("search", search);
    httpServletRequest.setAttribute("workTitle", workTitle);
    WorkFlowBD workFlowBD = new WorkFlowBD();
    httpServletRequest.setAttribute("workId", work);
    httpServletRequest.setAttribute("workType", workType);
    httpServletRequest.setAttribute("stepCount", stepCount);
    httpServletRequest.setAttribute("activityId", activityId);
    httpServletRequest.setAttribute("backto", backto);
    httpServletRequest.setAttribute("initActivity", initActivity);
    ActivityVO activityPO = null;
    String formId = "";
    if ("y".equals(fromDossierData)) {
      formId = workFlowBD.getProcessMainFormId(processId);
    } else if (initActivity == null || "null".equals(initActivity) || "".equals(initActivity)) {
      if ("1".equals(workStatusStr) || "100".equals(workStatusStr)) {
        formId = workFlowBD.getProcessFirstFormId(processId);
      } else {
        formId = workFlowBD.getProcessMainFormId(processId);
      } 
    } else {
      try {
        if (!"0".equals(initActivity) && initActivity != null && !"null".equals(initActivity)) {
          if (!"0".equals(workPrarllelCurActId)) {
            activityPO = workFlowBD.getProceedActiVO(httpServletRequest.getParameter("table"), httpServletRequest.getParameter("record"), workPrarllelCurActId, "1");
          } else {
            activityPO = workFlowBD.getProceedActiVO(httpServletRequest.getParameter("table"), httpServletRequest.getParameter("record"), initActivity, "1");
          } 
          formId = (activityPO.getFormId() == null || activityPO.getFormId().equals("") || 
            activityPO.getFormId().toUpperCase().equals("NULL")) ? "" : activityPO.getFormId();
          httpServletRequest.setAttribute("formId", formId);
          httpServletRequest.setAttribute("activitydescription", activityPO.getActivityDescription());
        } 
      } catch (Exception ee) {
        formId = "";
      } 
      if ("".equals(formId))
        if (!"0".equals(workPrarllelCurActId)) {
          formId = workFlowBD.getFormIdByActivityId(workPrarllelCurActId);
        } else {
          formId = workFlowBD.getFormIdByActivityId(initActivity);
        }  
    } 
    httpServletRequest.setAttribute("formId", formId);
    if (activityPO != null) {
      httpServletRequest.setAttribute("exeScript", activityPO.getExeScript());
      httpServletRequest.setAttribute("beforeSubmit", activityPO.getBeforeSubmit());
    } 
    httpServletRequest.setAttribute("activityName", activityName);
    httpServletRequest.setAttribute("tableId", table);
    httpServletRequest.setAttribute("recordId", record);
    ProcessBD processBD = new ProcessBD();
    String processName = "";
    List<Object> procInfo = processBD.getProcInfo(processId);
    processName = (String)((Object[])procInfo.get(0))[1];
    httpServletRequest.setAttribute("processName", processName);
    String remindField = processBD.getRemindField(processId);
    httpServletRequest.setAttribute("remindField", remindField);
    httpServletRequest.setAttribute("isStandForWork", httpServletRequest.getParameter("isStandForWork"));
    httpServletRequest.setAttribute("standForUserId", httpServletRequest.getParameter("standForUserId"));
    httpServletRequest.setAttribute("standForUserName", httpServletRequest.getParameter("standForUserName"));
    int workStatus = workFlowBD.getWorkStatusByWorkId(work);
    httpServletRequest.setAttribute("submitPerson", submitPerson);
    httpServletRequest.setAttribute("submitPersonId", submitPersonId);
    if (submitTime != null) {
      if (submitTime.indexOf(".") > 0)
        submitTime = submitTime.substring(0, submitTime.length() - 5); 
      httpServletRequest.setAttribute("submitTime", submitTime);
    } 
    workFlowFormActionForm.setProcessName(processName);
    try {
      if (!"0".equals(activityId) && activityId != null)
        if ("2".equals(workStatusStr)) {
          httpServletRequest.setAttribute("curCommField", (activityPO.getPassRoundCommField() == null) ? null : activityPO.getPassRoundCommField());
          httpServletRequest.setAttribute("curPassRoundCommField", (activityPO.getPassRoundCommField() == null) ? null : activityPO.getPassRoundCommField());
          httpServletRequest.setAttribute("commFieldType", (activityPO.getPassRoundCommFieldType() == null) ? null : activityPO.getPassRoundCommFieldType());
        } else {
          httpServletRequest.setAttribute("curCommField", (activityPO.getActiCommField() == null) ? null : activityPO.getActiCommField());
          httpServletRequest.setAttribute("commFieldType", (activityPO.getActiCommFieldType() == null) ? null : activityPO.getActiCommFieldType());
        }  
    } catch (Exception exception) {}
    String writeFieldString = "";
    StringBuffer canModifyField = new StringBuffer();
    if (workStatus == 0)
      if ("1".equals(workType)) {
        List<String[]> list;
        if (!"0".equals(workPrarllelCurActId)) {
          list = workFlowBD.getRWList(workPrarllelCurActId, table, record, "1");
        } else {
          list = workFlowBD.getRWList(activityId, table, record, "1");
        } 
        String[] str = (String[])null;
        for (int i = 0; i < list.size(); i++) {
          str = list.get(i);
          if (str[0].equals("1")) {
            writeFieldString = String.valueOf(writeFieldString) + "$" + str[1] + "$";
            canModifyField.append("<input type=\"hidden\" name=\"canModifyField\" value=\"" + str[1] + "\">");
          } 
        } 
        httpServletRequest.setAttribute("RWFieldList", list);
      }  
    StringBuffer hideField = new StringBuffer();
    String[] fieldArr = (new CustomFormBD()).getAllTableField(table);
    if (fieldArr != null)
      for (int i = 0; i < fieldArr.length; i++) {
        if (writeFieldString.indexOf("$" + fieldArr[i] + "$") < 0)
          hideField.append(fieldArr[i]).append(","); 
      }  
    httpServletRequest.setAttribute("hideField", hideField.toString());
    httpServletRequest.setAttribute("formContent", "");
    String hangup = workFlowBD.getHangup(processId, record, table, processName);
    httpServletRequest.setAttribute("hangup", hangup);
    return actionMapping.findForward("success");
  }
}
