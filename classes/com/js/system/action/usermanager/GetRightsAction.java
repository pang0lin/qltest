package com.js.system.action.usermanager;

import com.js.system.service.rolemanager.RoleBD;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GetRightsAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    if (httpServletRequest.getParameterValues("rightIdSend") != null) {
      httpServletRequest.setAttribute("oldRightId", httpServletRequest.getParameterValues("rightIdSend"));
      httpServletRequest.setAttribute("oldRightType", httpServletRequest.getParameterValues("rightTypeSend"));
      httpServletRequest.setAttribute("oldRightScope", httpServletRequest.getParameterValues("rightScopeSend"));
      httpServletRequest.setAttribute("oldRightScopeDsp", httpServletRequest.getParameterValues("rightScopeDspSend"));
    } else {
      httpServletRequest.setAttribute("oldRightId", httpServletRequest.getParameterValues("rightId"));
      httpServletRequest.setAttribute("oldRightType", httpServletRequest.getParameterValues("rightType"));
      httpServletRequest.setAttribute("oldRightScope", httpServletRequest.getParameterValues("rightScope"));
      httpServletRequest.setAttribute("oldRightScopeDsp", httpServletRequest.getParameterValues("rightScopeDsp"));
    } 
    String ids = ((GetRightsForm)actionForm).getRoleIds();
    if (!"".equals(ids)) {
      String roleIds = ids.substring(0, ids.length() - 1);
      RoleBD roleBD = new RoleBD();
      Map map = roleBD.getDistinctRights(roleIds);
      List rights = (List)map.get("rightList");
      List rightClass = (List)map.get("rightClass");
      httpServletRequest.setAttribute("rightList", rights);
      httpServletRequest.setAttribute("rightClass", rightClass);
    } else {
      List rights = new ArrayList();
      httpServletRequest.setAttribute("rights", rights);
    } 
    if (httpServletRequest.getParameter("flag") == null)
      return actionMapping.findForward("success"); 
    return actionMapping.findForward("audit");
  }
}
