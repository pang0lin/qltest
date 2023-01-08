package com.js.oa.jsflow.action;

import com.js.oa.eform.service.CustomFormBD;
import com.js.oa.jsflow.bean.WorkFlowEJBBean;
import com.js.oa.jsflow.bean.WorkFlowEJBBeanForRWS;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.jsflow.vo.ActivityVO;
import com.js.oa.userdb.util.DbOpt;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WorkFlowProcAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    WorkFlowFormActionForm workFlowFormActionForm = (WorkFlowFormActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    String userId = session.getAttribute("userId").toString();
    httpServletRequest.setAttribute("gd", "1");
    String isRWS = httpServletRequest.getParameter("isRWS");
    if (isRWS == null || "null".equalsIgnoreCase(isRWS))
      isRWS = ""; 
    List<Object[]> processList = (new ProcessBD()).getProcInfo(httpServletRequest.getParameter("processId"));
    if (processList != null && processList.size() > 0) {
      Object[] obj = processList.get(0);
      httpServletRequest.setAttribute("formType", obj[23]);
      httpServletRequest.setAttribute("jspFile", obj[25]);
    } else {
      httpServletRequest.setAttribute("formType", "0");
    } 
    httpServletRequest.setAttribute("search", httpServletRequest.getParameter("search"));
    String workTitle = "";
    try {
      workTitle = URLDecoder.decode(httpServletRequest.getParameter("workTitle"), "GBK");
    } catch (Exception exception) {}
    httpServletRequest.setAttribute("workTitle", workTitle);
    WorkFlowBD workFlowBD = new WorkFlowBD();
    String workId = httpServletRequest.getParameter("work");
    httpServletRequest.setAttribute("workId", workId);
    String workIsParallel = "0";
    String workParallelId = "0";
    String workParallelStep = "0";
    String workPrarllelCurActId = "0";
    String curActivityIsBranch = "0";
    String[] workParallelInfo = (new WorkFlowCommonBD()).getWorkParallelInfo(workId);
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
    httpServletRequest.setAttribute("workType", workType);
    String processId = httpServletRequest.getParameter("processId");
    httpServletRequest.setAttribute("stepCount", httpServletRequest.getParameter("stepCount"));
    String activityId = httpServletRequest.getParameter("activity");
    httpServletRequest.setAttribute("activityId", activityId);
    String initActivity = httpServletRequest.getParameter("initActivity");
    httpServletRequest.setAttribute("initActivity", initActivity);
    ActivityVO activityPO = null;
    String formId = "";
    if ("y".equals(httpServletRequest.getParameter("fromDossierData"))) {
      formId = workFlowBD.getProcessMainFormId(processId);
    } else if (initActivity == null || "null".equals(initActivity) || "".equals(initActivity)) {
      if ("-1".equals(httpServletRequest.getParameter("workStatus")) || "1".equals(httpServletRequest.getParameter("workStatus")) || "100".equals(httpServletRequest.getParameter("workStatus"))) {
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
    String activityName = httpServletRequest.getParameter("activityName");
    httpServletRequest.setAttribute("activityName", activityName);
    String tableId = httpServletRequest.getParameter("table");
    httpServletRequest.setAttribute("tableId", tableId);
    String recordId = httpServletRequest.getParameter("record");
    httpServletRequest.setAttribute("recordId", recordId);
    if ("1".equals(isRWS)) {
      DbOpt db = new DbOpt();
      try {
        String tableName = db.executeQueryToStr("select area_table from tarea where page_id=" + tableId + " and area_name='form1'");
        if (WorkFlowEJBBeanForRWS.canBeYuGuiDang(tableName, recordId)) {
          httpServletRequest.setAttribute("preGD", "1");
        } else {
          httpServletRequest.setAttribute("preGD", "0");
        } 
        if (WorkFlowEJBBeanForRWS.canBeGuiDang(tableName, recordId)) {
          httpServletRequest.setAttribute("canBeGuiDang", "1");
        } else {
          httpServletRequest.setAttribute("canBeGuiDang", "0");
        } 
        if (WorkFlowEJBBeanForRWS.canBeCheHuiGuiDang(tableName, recordId)) {
          httpServletRequest.setAttribute("canBeCheHuiGuiDang", "1");
        } else {
          httpServletRequest.setAttribute("canBeCheHuiGuiDang", "0");
        } 
        db.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } else {
      httpServletRequest.setAttribute("preGD", "0");
      httpServletRequest.setAttribute("canBeGuiDang", "0");
      httpServletRequest.setAttribute("canBeCheHuiGuiDang", "0");
    } 
    String processName = httpServletRequest.getParameter("processName");
    httpServletRequest.setAttribute("processName", processName);
    ProcessBD processBD = new ProcessBD();
    String remindField = processBD.getRemindField(processId);
    httpServletRequest.setAttribute("remindField", remindField);
    httpServletRequest.setAttribute("isStandForWork", httpServletRequest.getParameter("isStandForWork"));
    httpServletRequest.setAttribute("standForUserId", httpServletRequest.getParameter("standForUserId"));
    httpServletRequest.setAttribute("standForUserName", httpServletRequest.getParameter("standForUserName"));
    int workStatus = workFlowBD.getWorkStatusByWorkId(httpServletRequest.getParameter("work"));
    String submitPerson = httpServletRequest.getParameter("submitPerson");
    httpServletRequest.setAttribute("submitPerson", submitPerson);
    String submitPersonId = httpServletRequest.getParameter("submitPersonId");
    httpServletRequest.setAttribute("submitPersonId", submitPersonId);
    String submitTime = httpServletRequest.getParameter("submitTime");
    if (submitTime != null) {
      if (submitTime.indexOf(".") > 0)
        submitTime = submitTime.substring(0, submitTime.length() - 5); 
      httpServletRequest.setAttribute("submitTime", submitTime);
    } 
    workFlowFormActionForm.setProcessName(processName);
    try {
      if ("2".equals(httpServletRequest.getParameter("workStatus"))) {
        WorkFlowEJBBean wfb = new WorkFlowEJBBean();
        String PassRoundCommFieldType = wfb.getProceedActiVOPactivity(httpServletRequest.getParameter("table"), httpServletRequest.getParameter("record"), initActivity, "1");
        httpServletRequest.setAttribute("commFieldType", PassRoundCommFieldType);
      } 
      if (!"0".equals(activityId) && activityId != null)
        if ("2".equals(httpServletRequest.getParameter("workStatus"))) {
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
      if (workType.equals("1")) {
        List<String[]> list;
        if (!"0".equals(workPrarllelCurActId)) {
          list = workFlowBD.getRWList(workPrarllelCurActId, tableId, recordId, "1");
        } else {
          list = workFlowBD.getRWList(activityId, tableId, recordId, "1");
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
    String[] fieldArr = (new CustomFormBD()).getAllTableField(tableId);
    if (fieldArr != null)
      for (int i = 0; i < fieldArr.length; i++) {
        if (writeFieldString.indexOf("$" + fieldArr[i] + "$") < 0)
          hideField.append(fieldArr[i]).append(","); 
      }  
    httpServletRequest.setAttribute("hideField", hideField.toString());
    httpServletRequest.setAttribute("formContent", "");
    String hangup = workFlowBD.getHangup(processId, recordId, tableId, processName);
    httpServletRequest.setAttribute("hangup", hangup);
    httpServletRequest.setAttribute("send", httpServletRequest.getParameter("send"));
    return actionMapping.findForward("success");
  }
}
