package com.js.oa.zky.action;

import com.js.oa.zky.service.ZkyCountBD;
import com.js.system.util.StaticParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ZkyCountAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    String action = request.getParameter("action");
    if ("count".equals(action)) {
      HttpSession session = request.getSession();
      String gh = StaticParam.getEmpNumberByEmpId((String)session.getAttribute("userId"));
      request.setAttribute("countData", (new ZkyCountBD()).getCountData(gh));
    } 
    return actionMapping.findForward(action);
  }
}
