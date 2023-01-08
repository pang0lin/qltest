package com.js.oa.jsflow.action;

import com.js.oa.jsflow.service.WorkFlowBD;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WorkFlowPassRoundAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    FileDealWithActionForm fileDealWithActionForm = (FileDealWithActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    HttpSession httpSession = httpServletRequest.getSession(true);
    String workId = httpServletRequest.getParameter("workId");
    String[] dealPara = { httpServletRequest.getParameter("tableId"), 
        httpServletRequest.getParameter("recordId"), 
        httpServletRequest.getParameter("curActivityName"), 
        httpServletRequest.getParameter("curActivityId"), 
        httpSession.getAttribute("userId").toString(), 
        httpServletRequest.getParameter("comment"), 
        httpServletRequest.getParameter("stepCount"), (
        new Date()).toLocaleString(), "" };
    WorkFlowBD workFlowBD = new WorkFlowBD();
    workFlowBD.insertPassRoundDeal(dealPara);
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
            for (int k = 0; k < remindValue.length; k++) {
              if (remindValue[k].substring(0, remindValue[k].indexOf("/")).equals(httpServletRequest.getParameter(remindFieldArray[j])))
                remindFieldValue.append(remindValue[k].substring(remindValue[k].indexOf("/") + 1)); 
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
            for (int k = 0; k < remindValue.length; k++) {
              for (int m = 0; m < tmp.length; m++) {
                if (tmp[m].equals(remindValue[k].substring(0, remindValue[k].indexOf("/")))) {
                  remindFieldValue.append(remindValue[k].substring(remindValue[k].indexOf("/") + 1));
                  break;
                } 
              } 
            } 
          } else {
            remindFieldValue.append(httpServletRequest.getParameter(remindFieldArray[j]));
          }  
      } 
    } 
    if (remindFieldValue.toString().equals("null"))
      remindFieldValue = new StringBuffer(); 
    String[] workPara = { workId, remindFieldValue.toString(), "/jsoa/WorkFlowProcAction.do?flowpara=1" };
    workFlowBD.operPassRoundWork(workPara);
    return actionMapping.findForward("success");
  }
}
