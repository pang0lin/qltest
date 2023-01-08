package com.js.oa.jsflow.action;

import com.js.oa.jsflow.util.ProcessStep;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WorkFlowNewIframeAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    WorkFlowFormActionForm workFlowFormActionForm = (WorkFlowFormActionForm)actionForm;
    String processId = httpServletRequest.getParameter("processId");
    String firstStep = (new ProcessStep()).firstStep(processId, 1, httpServletRequest);
    httpServletRequest.setAttribute("firstStep", firstStep);
    return actionMapping.findForward("success");
  }
}
