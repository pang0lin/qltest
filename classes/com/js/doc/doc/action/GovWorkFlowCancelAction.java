package com.js.doc.doc.action;

import com.js.doc.doc.service.SendFileBD;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GovWorkFlowCancelAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
    String action = request.getParameter("action");
    request.setAttribute("search", request.getParameter("search"));
    request.setAttribute("workTitle", request.getParameter("workTitle"));
    if (!"deltable0".equals(action) || !"1".equals(request.getParameter("deltable00")))
      if (!"deltable0".equals(action)) {
        SendFileBD bd = new SendFileBD();
        String cancelReason = request.getParameter("cancelReason");
        String tableId = request.getParameter("tableId");
        String recordId = request.getParameter("recordId");
        String title = request.getParameter("fileTitle");
        String processName = request.getParameter("processName");
        String workId = request.getParameter("workId");
        String processId = request.getParameter("processId");
        bd.monitorRedo(recordId, tableId, processId, workId, title, 
            cancelReason);
      }  
    return actionMapping.findForward("cancelpage");
  }
}
