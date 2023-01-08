package com.js.oa.jsflow.action;

import com.js.oa.jsflow.service.WorkFlowBD;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WorkFlowUntreadAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    String comment = String.valueOf(httpServletRequest.getParameter("comment")) + "&nbsp;&nbsp;（退回）";
    String curActivityId = httpServletRequest.getParameter("curActivityId");
    String curActivityName = httpServletRequest.getParameter("curActivityName");
    String processName = httpServletRequest.getParameter("processName");
    String tableId = httpServletRequest.getParameter("tableId");
    String recordId = httpServletRequest.getParameter("recordId");
    String workId = httpServletRequest.getParameter("workId");
    String submitPerson = httpServletRequest.getParameter("submitPerson");
    String stepCount = httpServletRequest.getParameter("stepCount");
    HttpSession httpSession = httpServletRequest.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String userName = httpSession.getAttribute("userName").toString();
    WorkFlowBD workFlowBD = new WorkFlowBD();
    String[] para = { tableId, recordId, curActivityName, curActivityId, userId, comment, "", "0", stepCount };
    workFlowBD.insertDealWith(para);
    String remindField = httpServletRequest.getParameter("remindField");
    StringBuffer remindFieldValue = new StringBuffer();
    if (remindField != null && !remindField.equals("")) {
      String[] remindFieldArray = ("S" + remindField + "S").split("SS");
      for (int j = 0; j < remindFieldArray.length; j++) {
        if (!remindFieldArray[j].equals(""))
          if (httpServletRequest.getParameter("remindText_" + remindFieldArray[j]) != null) {
            String remindStr = httpServletRequest.getParameter("remindText_" + remindFieldArray[j]);
            if (remindStr.endsWith(";"))
              remindStr = remindStr.substring(0, remindStr.length() - 1); 
            String[] remindValue = remindStr.split(";");
            for (int i = 0; i < remindValue.length; i++) {
              if (remindValue[i].substring(0, remindValue[i].indexOf("/")).equals(httpServletRequest.getParameter(remindFieldArray[j])))
                remindFieldValue.append(remindValue[i].substring(remindValue[i].indexOf("/") + 1)); 
            } 
          } else if (httpServletRequest.getParameter("remindCheckBox_" + remindFieldArray[j]) != null) {
            String remindStr = httpServletRequest.getParameter("remindCheckBox_" + remindFieldArray[j]);
            if (remindStr.endsWith(";"))
              remindStr = remindStr.substring(0, remindStr.length() - 1); 
            String[] remindValue = remindStr.split(";");
            String tmpValue = httpServletRequest.getParameter(remindFieldArray[j]);
            if (tmpValue.endsWith(","))
              tmpValue = tmpValue.substring(0, tmpValue.length() - 1); 
            String[] tmp = { tmpValue };
            if (tmpValue.indexOf(",") >= 0)
              tmp = tmpValue.split(","); 
            for (int i = 0; i < remindValue.length; i++) {
              for (int m = 0; m < tmp.length; m++) {
                if (tmp[m].equals(remindValue[i].substring(0, remindValue[i].indexOf("/")))) {
                  remindFieldValue.append(remindValue[i].substring(remindValue[i].indexOf("/") + 1));
                  break;
                } 
              } 
            } 
          } else {
            remindFieldValue.append(httpServletRequest.getParameter(remindFieldArray[j]));
          }  
      } 
    } 
    int k = (String.valueOf(submitPerson) + "的" + remindFieldValue.toString() + processName + "已被您退回！").length() - 50;
    if (k > 0)
      remindFieldValue.substring(0, remindFieldValue.length() - k); 
    ArrayList<String> alist = new ArrayList();
    alist.add("update JSDB.JSF_WORK set workStatus = -1, workTitle = '您的" + 
        remindFieldValue.toString() + processName + "已被" + userName + 
        "退回！',workCancelReason = '" + comment + "' where workstartflag = 1 and worktable_id = " + 
        tableId + " and workrecord_id = " + recordId);
    alist.add("update JSDB.JSF_WORK set workStatus = 101, workTitle = '" + submitPerson + 
        "的" + remindFieldValue.toString() + processName + 
        "已被您退回！',workAllowCancel = 0 where wf_work_id = " + workId);
    alist.add(" delete from JSDB.JSF_WORK where ( workReadMarker = 0 or workListControl = 0 )  and worktable_id = " + 
        tableId + " and workrecord_id = " + 
        recordId + " and wf_work_id <> " + workId + " and workstartflag <> 1");
    alist.add("update JSDB.JSF_WORK set workAllowCancel = 0 where workTable_id = " + tableId + 
        " and workRecord_id = " + recordId + " and workStepCount = " + (Integer.parseInt(stepCount) - 1));
    workFlowBD.updateTable(alist);
    return actionMapping.findForward("success");
  }
}
