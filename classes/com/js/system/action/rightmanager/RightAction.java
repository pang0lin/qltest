package com.js.system.action.rightmanager;

import com.js.oa.security.log.service.LogBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.rightmanager.RightBD;
import com.js.system.service.rolemanager.RoleBD;
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

public class RightAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    RightActionForm rightActionForm = (RightActionForm)actionForm;
    String tag = "view";
    String action = httpServletRequest.getParameter("action");
    String domainId = session.getAttribute("domainId").toString();
    if (action != null && action.equals("search")) {
      String name;
      if (httpServletRequest.getAttribute("rightName") == null) {
        name = httpServletRequest.getParameter("rightName");
      } else {
        name = httpServletRequest.getAttribute("rightName").toString();
      } 
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
      int currentPage = offset / pageSize + 1;
      Page page = new Page("aaa.rightId,aaa.rightName,aaa.rightType,aaa.rightDescription,aaa.rightClass,aaa.rightCode", "com.js.system.vo.rightmanager.RightVO aaa", "where aaa.rightType like '%" + name + "%' and aaa.domainId=" + domainId + " order by aaa.rightCode");
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      String pageCount = String.valueOf(page.getPageCount());
      httpServletRequest.setAttribute("rightList", list);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
      httpServletRequest.setAttribute("pageParameters", "action,rightName");
    } else if (action != null && action.equals("scope")) {
      String rightId = httpServletRequest.getParameter("rightId");
      String rightName = httpServletRequest.getParameter("rightName");
      tag = "scope";
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
      int currentPage = offset / pageSize + 1;
      Page page = new Page("aaa.employee.empName,aaa.rightScopeType,aaa.rightScopeScope ", " com.js.system.vo.rolemanager.RightScopeVO aaa join aaa.employee emp ", " where emp.userIsDeleted=0 and emp.domainId=" + session.getAttribute("domainId") + " and aaa.right.rightId = " + rightId);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List<Object[]> list = page.getResultList();
      Object[] obj = (Object[])null;
      ManagerService managerBD = new ManagerService();
      for (int i = 0; i < list.size(); i++) {
        obj = list.get(i);
        String strObj1 = obj[1].toString();
        if (strObj1.equals("0")) {
          obj[2] = "全部";
        } else if (strObj1.equals("1")) {
          obj[2] = "本人";
        } else if (strObj1.equals("3")) {
          obj[2] = "本组织及下属组织";
        } else if (strObj1.equals("2")) {
          obj[2] = "本组织";
        } else {
          obj[2] = managerBD.getNameBYId(((String)obj[2]).trim());
        } 
        list.set(i, obj);
      } 
      String recordCount = String.valueOf(page.getRecordCount());
      httpServletRequest.setAttribute("rightId", rightId);
      httpServletRequest.setAttribute("rightName", rightName);
      httpServletRequest.setAttribute("rightScopeList", list);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
      httpServletRequest.setAttribute("pageParameters", "action,rightId,rightName");
    } else if (action != null && action.equals("modify")) {
      tag = "modify";
      String rightId = httpServletRequest.getParameter("rightId");
      RightBD rightBD = new RightBD();
      List<Object[]> rightList = rightBD.getRightInfo(rightId);
      Object[] obj = rightList.get(0);
      rightActionForm.setRightId(rightId);
      rightActionForm.setRightName((String)obj[0]);
      rightActionForm.setRightType((String)obj[1]);
      rightActionForm.setRightDescription((String)obj[2]);
      List roleIdList = rightBD.getRoleId(rightId);
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < roleIdList.size(); i++) {
        sb.append("$");
        sb.append(roleIdList.get(i));
        sb.append("$,");
      } 
      httpServletRequest.setAttribute("roleIdString", sb.toString());
      RoleBD roleBD = new RoleBD();
      List roleList = roleBD.getAllIdAndName(session.getAttribute("domainId").toString());
      httpServletRequest.setAttribute("roleList", roleList);
      String userName = session.getAttribute("userName").toString();
      String orgName = session.getAttribute("orgName").toString();
      LogBD logBD = new LogBD();
      Date date = new Date();
      logBD.log(session.getAttribute("userId").toString(), userName, orgName, "system_right", "系统管理", date, date, "2", String.valueOf(obj[1].toString()) + "-" + obj[0].toString(), session.getAttribute("userIP").toString(), domainId);
    } else if (action != null && action.equals("trueModify")) {
      tag = "close";
      String searchName = httpServletRequest.getParameter("searchName");
      String[] roleId = httpServletRequest.getParameterValues("roleId");
      String rightId = httpServletRequest.getParameter("rightId");
      RightBD rightBD = new RightBD();
      rightBD.updateRole(rightId, roleId);
      list(actionMapping, actionForm, httpServletRequest, httpServletResponse, searchName, domainId);
    } else {
      list(actionMapping, actionForm, httpServletRequest, httpServletResponse, "", domainId);
    } 
    return actionMapping.findForward(tag);
  }
  
  public void list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String searchName, String domainId) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    if (!"".equals(searchName)) {
      searchName = "where aaa.rightType like '%" + searchName + "%' and aaa.domainId=" + domainId + " order by aaa.rightId desc";
    } else {
      searchName = "where aaa.domainId=" + domainId + " order by aaa.rightCode";
    } 
    Page page = new Page("aaa.rightId,aaa.rightName,aaa.rightType,aaa.rightDescription,aaa.rightClass,aaa.rightCode", "com.js.system.vo.rightmanager.RightVO aaa", searchName);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    httpServletRequest.setAttribute("rightList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,searchName");
  }
}
