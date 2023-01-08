package com.js.oa.bjyh.action;

import com.js.oa.bjyh.bean.BjyhOpinionBean;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class OpinionShow extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    String action = request.getParameter("action");
    if ("opinionList".equals(action)) {
      String userAccount = request.getParameter("userAccount");
      BjyhOpinionBean bob = new BjyhOpinionBean();
      List opinionList = null;
      if (userAccount != null && !"".equals(userAccount) && !"null".equalsIgnoreCase(userAccount))
        opinionList = bob.getOpinionList(request, userAccount); 
      if (opinionList == null || "null".equals(opinionList) || "".equals(opinionList))
        return actionMapping.findForward("opinionNull"); 
      request.setAttribute("opinionList", opinionList);
      request.setAttribute("userAccount", userAccount);
    } 
    return actionMapping.findForward(action);
  }
}
