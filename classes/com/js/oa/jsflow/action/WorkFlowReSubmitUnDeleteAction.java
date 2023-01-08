package com.js.oa.jsflow.action;

import com.js.oa.eform.service.CustomFormBD;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.util.ParseFormWithValue;
import com.js.oa.jsflow.util.ProcessStep;
import com.js.util.config.SystemCommon;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WorkFlowReSubmitUnDeleteAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    WorkFlowFormActionForm workFlowFormActionForm = (WorkFlowFormActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    String tag = "open";
    List<Object[]> processList = (new ProcessBD()).getProcInfo(httpServletRequest.getParameter("processId"));
    if (processList != null && processList.size() > 0) {
      Object[] obj = processList.get(0);
      httpServletRequest.setAttribute("formType", obj[23]);
      httpServletRequest.setAttribute("jspFile", obj[25]);
    } else {
      httpServletRequest.setAttribute("formType", "0");
    } 
    String tableId = httpServletRequest.getParameter("table");
    httpServletRequest.setAttribute("tableId", tableId);
    String recordId = httpServletRequest.getParameter("record");
    String processId = httpServletRequest.getParameter("processId");
    httpServletRequest.setAttribute("processId", processId);
    ProcessBD processBD = new ProcessBD();
    List noWriteField = processBD.getNoWriteField(processId);
    httpServletRequest.setAttribute("noWriteField", noWriteField);
    if ("1".equals(httpServletRequest.getParameter("moduleId"))) {
      String code = (new CustomFormBD())
        .getCode(tableId);
      if (code == null || code.toUpperCase().equals("NULL") || 
        code.trim().length() < 1) {
        String[][] realTable = (new CustomFormBD())
          .getTableIDAndName(tableId);
        httpServletRequest.setAttribute("formContent", (
            new ParseFormWithValue())
            .parseForm((realTable == null || 
              realTable.length < 1) ? "-1" : realTable[0][0], 
              recordId, "", true, 
              session.getAttribute("fileServer").toString(), 
              httpServletRequest, tableId));
      } else {
        httpServletRequest.setAttribute("formContent", "");
      } 
    } else {
      httpServletRequest.setAttribute("formContent", (new ParseFormWithValue()).parseForm(tableId, recordId, "", true, session.getAttribute("fileServer").toString(), httpServletRequest, tableId));
    } 
    String remindField = processBD.getRemindField(processId);
    httpServletRequest.setAttribute("remindField", remindField);
    String processName = httpServletRequest.getParameter("workFileType");
    httpServletRequest.setAttribute("processName", processName);
    workFlowFormActionForm.setProcessName(processName);
    String workType = httpServletRequest.getParameter("workType");
    httpServletRequest.setAttribute("processType", workType);
    if (workType.equals("0")) {
      String nextStep = (new ProcessStep()).firstStep(processId, Integer.parseInt(workType));
      if (nextStep.equals("noActivity")) {
        tag = "noActivity";
      } else {
        httpServletRequest.setAttribute("nextStep", nextStep);
      } 
    } else {
      httpServletRequest.setAttribute("nextStep", "");
    } 
    if ("0".equals(SystemCommon.getReSubmitOnDoneUseOldNumber()))
      httpServletRequest.getSession().setAttribute("automaticNumberReCalculation", "1"); 
    return actionMapping.findForward(tag);
  }
  
  private String getHandSign(HttpServletRequest httpServletRequest, String recordid, String fieldname) {
    StringBuffer comment = new StringBuffer();
    comment.append("<OBJECT name=\"" + fieldname + "\" classid=\"clsid:2294689C-9EDF-40BC-86AE-0438112CA439\" codebase=\"/jsoa/iWebRevision.jsp/iWebRevision.cab#version=6,0,0,0\" width=100% height=100%>");
    comment.append("<param name=\"weburl\" value=\"" + httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + "/jsoa/iWebRevision.jsp/iWebServer.jsp\">");
    comment.append("<param name=\"recordid\" value=\"" + recordid + "\">");
    comment.append("<param name=\"fieldname\" value=\"SendOut\">");
    comment.append("<param name=\"username\" value=\"\">");
    comment.append("<param name=\"Enabled\" value=\"0\">");
    comment.append("<param name=\"PenColor\" value=\"00000000\">");
    comment.append("<param name=\"BorderStyle\" value=\"0\">");
    comment.append("</OBJECT>");
    comment.append("<script language=javascript>document.all." + fieldname + ".LoadSignature();</script>");
    return comment.toString();
  }
}
