package com.js.oa.personalwork.person.action;

import com.js.oa.personalwork.person.po.PersonClassPO;
import com.js.oa.personalwork.person.service.PersonClassBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.Page;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PersonClassAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    PersonClassActionForm personClassActionForm = (PersonClassActionForm)actionForm;
    PersonClassBD bd = new PersonClassBD();
    String action = request.getParameter("action");
    String curUserId = request.getSession(true).getAttribute("userId").toString();
    String curOrgId = request.getSession(true).getAttribute("orgId").toString();
    String orgIdString = request.getSession(true).getAttribute("orgIdString").toString();
    if ("delBatch".equals(action)) {
      String ids = request.getParameter("ids");
      String classType = request.getParameter("userType");
      if (request.getParameter("ids") != null)
        bd.delBatch(ids, (new StringBuilder(String.valueOf(curUserId))).toString(), classType); 
      action = "list";
    } 
    if ("delAll".equals(action)) {
      String userType = request.getParameter("userType");
      String paraWhere = (new StringBuilder(String.valueOf(curUserId))).toString();
      if ("1".equals(userType)) {
        ManagerService mbd = new ManagerService();
        paraWhere = "po.empId=" + curUserId + " or " + mbd.getRightFinalWhere(curUserId, curOrgId, "08*02*02", "po.createdOrg", "po.empId");
      } 
      bd.delAll(userType, paraWhere);
      action = "list";
    } 
    if ("list".equals(action)) {
      String userType = request.getParameter("userType");
      if ("0".equals(userType)) {
        list(request, curUserId, "");
      } else {
        ManagerService mbd = new ManagerService();
        String rightWhere = "po.empId=" + curUserId + " or " + mbd.getRightFinalWhere(curUserId, curOrgId, "08*02*02", "po.createdOrg", "po.empId");
        list(request, curUserId, rightWhere);
        if (mbd.hasRight(curUserId, "08*02*02"))
          request.setAttribute("add", "1"); 
      } 
    } 
    if ("add".equals(action)) {
      long userId = Long.parseLong(String.valueOf(request.getSession(true).getAttribute("userId")));
      String userType = request.getParameter("userType");
      String id = request.getParameter("editId");
      String name = request.getParameter("className");
      String classType = request.getParameter("userType");
      long orgId = Long.parseLong(String.valueOf(request.getSession(true).getAttribute("orgId")));
      if (bd.hasSameClassName(Long.valueOf(curUserId), personClassActionForm.getClassName().trim(), session.getAttribute("domainId").toString(), classType)) {
        request.setAttribute("syncMessages", "已经存在同样的分类名称， 分类未保存！");
        return actionMapping.findForward("add");
      } 
      bd.add(personClassActionForm.getClassName().trim(), 
          personClassActionForm.getClassDescribe(), (
          new StringBuilder(String.valueOf(userId))).toString(), userType, id, name.trim(), 
          action, classType, (new StringBuilder(String.valueOf(orgId))).toString(), session.getAttribute("domainId").toString());
      Object message = request.getAttribute("message");
      if (message != null && "true".equals(message.toString())) {
        personClassActionForm.setClassName("");
        personClassActionForm.setClassDescribe("");
      } 
      personClassActionForm.reset(actionMapping, request);
      return actionMapping.findForward("add");
    } 
    if ("new".equals(action))
      return actionMapping.findForward("add"); 
    if ("load".equals(action)) {
      PersonClassPO po = bd.load(request.getParameter("editId"));
      personClassActionForm.setClassDescribe(po.getClassDescribe());
      personClassActionForm.setClassName(po.getClassName());
      return actionMapping.findForward("modi");
    } 
    if ("update".equals(action)) {
      long userId = Long.parseLong(String.valueOf(request.getSession(true)
            .getAttribute("userId")));
      String userType = request.getParameter("userType");
      String id = request.getParameter("editId");
      String name = request.getParameter("className").trim();
      String className = request.getParameter("className").trim();
      String classDescribe = request.getParameter("classDescribe");
      if (bd.update((new StringBuilder(String.valueOf(userId))).toString(), userType, id, name, action, className, classDescribe).intValue() == -1) {
        request.setAttribute("syncMessages", "已经存在同样的分类名称， 分类未保存！");
        return actionMapping.findForward("add");
      } 
      return actionMapping.findForward("modi");
    } 
    return actionMapping.findForward("list");
  }
  
  private void list(HttpServletRequest request, String curUserId, String rightWhere) {
    HttpSession session = request.getSession(true);
    List list = new ArrayList();
    try {
      int pageSize = 15;
      int offset = 0;
      if (request.getParameter("pager.offset") != null)
        offset = Integer.parseInt(request.getParameter("pager.offset")); 
      String userType = request.getParameter("userType");
      int currentPage = offset / pageSize + 1;
      String where = " where po.classType=" + userType;
      if (userType.equals("0")) {
        where = String.valueOf(where) + " and po.empId=" + curUserId;
      } else {
        where = String.valueOf(where) + " and (" + rightWhere + ")";
      } 
      Page page = new Page(
          " po.id,po.className,po.empId,po.createdOrg,po.classType,po.classDescribe ", 
          " com.js.oa.personalwork.person.po.PersonClassPO po ", 
          String.valueOf(where) + " and po.domainId=" + session.getAttribute("domainId") + " order by po.id desc");
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action,userType");
      request.setAttribute("mylist", list);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
