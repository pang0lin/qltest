package com.js.oa.personalwork.setup.action;

import com.js.oa.personalwork.setup.po.RemindTypePO;
import com.js.oa.personalwork.setup.service.RemindSetBD;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RemindSetAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
    String curUserId = request.getSession(true).getAttribute("userId").toString();
    String curOrgId = request.getSession(true).getAttribute("orgId").toString();
    String orgIdString = request.getSession(true).getAttribute("orgIdString").toString();
    String flag = request.getParameter("flag");
    if ("load".equals(flag)) {
      load(request);
    } else if ("update".equals(flag)) {
      update(request);
    } 
    return actionMapping.findForward("load");
  }
  
  private void update(HttpServletRequest request) throws Exception {
    HttpSession session = request.getSession(true);
    String status = "";
    String[] statuses = (String[])null;
    RemindSetBD remindSetBD = new RemindSetBD();
    List<RemindTypePO> list = remindSetBD.getRemindType();
    RemindTypePO remindTypePO = new RemindTypePO();
    if (!list.isEmpty())
      for (int i = 0; i < list.size(); i++) {
        remindTypePO = list.get(i);
        statuses = request.getParameterValues(remindTypePO.getType());
        status = "";
        if (statuses != null)
          for (int j = 0; j < statuses.length; j++)
            status = String.valueOf(status) + statuses[j];  
        remindSetBD.remindSet(Long.parseLong(session.getAttribute("userId").toString()), remindTypePO.getType(), status);
      }  
    request.setAttribute("list", list);
  }
  
  private void load(HttpServletRequest request) throws Exception {
    RemindSetBD remindSetBD = new RemindSetBD();
    List list = remindSetBD.getRemindType();
    request.setAttribute("list", list);
  }
}
