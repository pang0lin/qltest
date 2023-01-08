package com.js.system.action.rolemanager;

import com.js.system.manager.service.ManagerService;
import com.js.system.service.rolemanager.RoleBD;
import com.js.system.vo.rolemanager.HandRoleVO;
import com.js.util.page.Page;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class HandRoleAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    HandRoleActionForm handRoleActionForm = (HandRoleActionForm)actionForm;
    String tag = "view";
    String action = httpServletRequest.getParameter("action");
    if (action == null)
      action = "view"; 
    if (action.equals("view")) {
      tag = "view";
      list(httpServletRequest);
    } else if (action.equals("add")) {
      tag = "add";
    } else if (action.equals("continue") || action.equals("close")) {
      HandRoleVO handRoleVO = new HandRoleVO();
      handRoleVO.setRoleDeliverId(Long.parseLong(handRoleActionForm.getRoleDeliverId()));
      handRoleVO.setRoleDeliverName(handRoleActionForm.getRoleDeliverName());
      handRoleVO.setRoleRecieveId(Long.parseLong(handRoleActionForm.getRoleRecieveId()));
      handRoleVO.setRoleRecieveName(handRoleActionForm.getRoleRecieveName());
      handRoleVO.setRoleHandTransactor(handRoleActionForm.getRoleHandTransactor());
      handRoleVO.setRoleHandDate(new Date(handRoleActionForm.getRoleHandDate()));
      handRoleVO.setCreatedEmp(session.getAttribute("userId").toString());
      handRoleVO.setCreatedOrg(session.getAttribute("orgId").toString());
      RoleBD roleBD = new RoleBD();
      roleBD.addHandRole(handRoleVO);
      handRoleActionForm.reset(actionMapping, httpServletRequest);
      if (action.equals("continue")) {
        tag = "add";
      } else {
        tag = "close";
      } 
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest httpServletRequest) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    HttpSession session = httpServletRequest.getSession(true);
    String sqlWhere = "";
    if (!"1".equals(session.getAttribute("sysManager").toString())) {
      ManagerService managerBD = new ManagerService();
      sqlWhere = " where " + managerBD.getRightWhere(session.getAttribute(
            "userId").toString(), 
          session.getAttribute("orgId").toString(), 
          session.getAttribute("orgIdString").toString(), 
          "系统管理", 
          "普通管理员", 
          "aaa.createdOrg", 
          "aaa.createdEmp");
    } 
    sqlWhere = String.valueOf(sqlWhere) + " order by aaa.handRoleId desc";
    Page page = new Page("aaa.handRoleId,aaa.roleDeliverName,aaa.roleRecieveName,aaa.roleHandDate,aaa.roleHandTransactor", 
        "com.js.system.vo.rolemanager.HandRoleVO aaa", 
        sqlWhere);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    List list = page.getResultList();
    httpServletRequest.setAttribute("handRoleList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
}
