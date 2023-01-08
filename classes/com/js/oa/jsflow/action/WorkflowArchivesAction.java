package com.js.oa.jsflow.action;

import com.js.oa.jsflow.bean.WorkflowBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WorkflowArchivesAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    String action = request.getParameter("action");
    String tag = "";
    boolean result = false;
    if ("save".equals(action)) {
      String workflowTable = request.getParameter("workflowTable");
      String archivesTable = request.getParameter("archivesTable");
      String[] archivesColumns = request.getParameterValues("archivesColumn");
      String[] workflowColumns = request.getParameterValues("workflowColumn");
      WorkflowBean bean = new WorkflowBean();
      result = bean.insertColumn(workflowTable, archivesTable, archivesColumns, workflowColumns);
      if (result) {
        request.setAttribute("message", "0");
      } else {
        request.setAttribute("message", "1");
      } 
      tag = "save";
    } 
    return actionMapping.findForward(tag);
  }
}
