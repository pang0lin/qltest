package com.js.oa.personalwork.setup.action;

import com.js.oa.personalwork.setup.service.MyInfoBD;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class StatusSetupAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
    String curUserId = request.getSession(true).getAttribute("userId").toString();
    String curOrgId = request.getSession(true).getAttribute("orgId").toString();
    String orgIdString = request.getSession(true).getAttribute("orgIdString").toString();
    String projectType = request.getParameter("projectType");
    String flag = request.getParameter("flag");
    if ("setState".equals(flag)) {
      String newState = request.getParameter("newState");
      HttpSession session = request.getSession(true);
      (new MyInfoBD()).saveEmpNewStatus(
          session.getAttribute("userId").toString(), newState);
      return null;
    } 
    if ("setDefineState".equals(flag))
      return actionMapping.findForward("setDefineState"); 
    if ("saveDefineState".equals(flag)) {
      String userDefineStatus = request.getParameter("userDefineStatus");
      HttpSession session = request.getSession(true);
      (new MyInfoBD()).saveEmpDefineStatus(
          session.getAttribute("userId").toString(), userDefineStatus);
      return actionMapping.findForward("setDefineState");
    } 
    if ("load".equals(flag)) {
      load(request);
    } else if ("update".equals(flag)) {
      update(request);
    } else if ("delete".equals(flag)) {
      delete(request);
    } 
    String forward = "load";
    if (StringUtils.isNotEmpty(projectType) && "tjgzw".equals(projectType)) {
      forward = "load_tjgzw";
      request.setAttribute("projectType", projectType);
    } 
    return actionMapping.findForward(forward);
  }
  
  private void update(HttpServletRequest request) {
    String curStatus = request.getParameter("curStatus");
    String rtxlogin = request.getParameter("rtxlogin");
    HttpSession session = request.getSession(true);
    (new MyInfoBD()).setEmpStatus(
        session.getAttribute("userId").toString(), curStatus, rtxlogin);
    request.setAttribute("success", "1");
    load(request);
  }
  
  private void delete(HttpServletRequest request) {
    String curStatus = request.getParameter("curStatusRad");
    if (!"0".equals(curStatus))
      curStatus = request.getParameter("curStatus"); 
    HttpSession session = request.getSession(true);
    (new MyInfoBD()).delEmpStatus(
        session.getAttribute("userId").toString(), curStatus);
    request.setAttribute("delete", "1");
    load(request);
  }
  
  private void load(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    Object[] result = (new MyInfoBD())
      .getEmpStatus(session.getAttribute("userId").toString());
    request.setAttribute("curStatus", result);
  }
}
