package com.js.oa.bbs.action;

import com.js.oa.bbs.bean.ForumClassEJBBean;
import com.js.oa.bbs.po.ForumClassPO;
import com.js.oa.bbs.service.ForumClassBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.util.Accessory;
import com.js.util.util.StringSplit;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ForumClassAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
    HttpSession session = request.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    String curUserId = request.getSession(true).getAttribute("userId").toString();
    String curOrgId = request.getSession(true).getAttribute("orgId").toString();
    String orgIdString = request.getSession(true).getAttribute("orgIdString").toString();
    ForumClassActionForm forumClassActionForm = (ForumClassActionForm)actionForm;
    String action = request.getParameter("action");
    if (forumClassActionForm.getFullDay() != 1) {
      forumClassActionForm.setStartPeriod(request.getParameter(
            "startPeriod"));
      forumClassActionForm.setEndPeriod(request.getParameter("endPeriod"));
    } else {
      forumClassActionForm.setStartPeriod(null);
      forumClassActionForm.setEndPeriod(null);
    } 
    ForumClassBD bd = new ForumClassBD();
    ManagerService mbd = new ManagerService();
    if ("add".equals(action)) {
      ForumClassPO po = new ForumClassPO();
      try {
        BeanUtils.copyProperties(po, forumClassActionForm);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      po.setCreatedEmp(Long.parseLong(curUserId));
      po.setCreatedEmpName((String)request.getSession(true).getAttribute("userName"));
      po.setCreatedOrg(Long.parseLong(curOrgId));
      String userOrgGroup = forumClassActionForm.getUserOrgGroup();
      po.setClassUserId(StringSplit.splitWith(userOrgGroup, "$", "*@"));
      po.setClassUserOrg(StringSplit.splitWith(userOrgGroup, "*", "@$"));
      po.setClassUserGroup(StringSplit.splitWith(userOrgGroup, "@", "$*"));
      po.setDomainId(Long.valueOf(domainId));
      po.setClassOwnerIds("*" + forumClassActionForm.getClassOwnerId() + "*");
      po.setClassParentName(forumClassActionForm.getClassName().trim());
      po.setCheckExamin(forumClassActionForm.getCheckExamin());
      String parentId = (new StringBuilder(String.valueOf(forumClassActionForm.getClassParent()))).toString();
      String currentSortCode = forumClassActionForm.getCurrentSortCode();
      String insertSite = forumClassActionForm.getInsertSite();
      String parentSort = forumClassActionForm.getParentSort();
      String classOwnerName = (request.getParameter("classOwnerName") == null) ? "" : request.getParameter("classOwnerName");
      String classOwnerId = (request.getParameter("classOwnerId") == null) ? "0" : request.getParameter("classOwnerId");
      String classOwnerIds = classOwnerId.replace('$', '*');
      po.setClassOwnerIds(classOwnerIds);
      po.setClassOwnerName(classOwnerName);
      String BanPrint = request.getParameter("BanPrint");
      po.setBanPrint(BanPrint);
      String classEmail = request.getParameter("classEmail");
      po.setClassEmail(classEmail);
      String checkNameNum = request.getParameter("checkNameNum");
      String startPeriods = "";
      String endPeriods = "";
      if (forumClassActionForm.getFullDay() != 1) {
        int maxNum = Integer.parseInt(checkNameNum);
        for (int i = 0; i < maxNum; i++) {
          startPeriods = String.valueOf(startPeriods) + request.getParameter("startPeriod" + i) + ",";
          endPeriods = String.valueOf(endPeriods) + request.getParameter("endPeriod" + i) + ",";
        } 
        startPeriods = startPeriods.substring(0, startPeriods.length() - 1);
        endPeriods = endPeriods.substring(0, endPeriods.length() - 1);
        po.setStartPeriod(startPeriods);
        po.setEndPeriod(endPeriods);
      } 
      String message = bd.add(po, parentId, currentSortCode, insertSite, 
          parentSort);
      request.setAttribute("message", message);
      action = "see";
      if (message == null || "true".equals(message) || "false".equals(message))
        forumClassActionForm.reset(actionMapping, request); 
    } 
    if ("update".equals(action)) {
      String isproject = request.getParameter("isproject");
      String userOrgGroup = forumClassActionForm.getUserOrgGroup();
      String parentId = (new StringBuilder(String.valueOf(forumClassActionForm.getClassParent()))).toString();
      String currentSortCode = forumClassActionForm.getCurrentSortCode();
      String insertSite = forumClassActionForm.getInsertSite();
      String parentSort = forumClassActionForm.getParentSort();
      String editId = request.getParameter("editId");
      Long classOwnId = new Long(forumClassActionForm.getClassOwnerId());
      String classOwnerName = forumClassActionForm.getClassOwnerName();
      String classUserName = forumClassActionForm.getClassUserName();
      Short classLevel = new Short(forumClassActionForm.getClassLevel());
      String className = forumClassActionForm.getClassName().trim();
      String classRemark = forumClassActionForm.getClassRemark();
      String editType = forumClassActionForm.getEditType();
      String checkExamin = forumClassActionForm.getCheckExamin();
      String estopAnonymity = forumClassActionForm.getEstopAnonymity();
      String checkNameNum = request.getParameter("checkNameNum");
      String startPeriod = null;
      String endPeriod = null;
      String startPeriods = "";
      String endPeriods = "";
      Integer fullDay = new Integer(0);
      if (forumClassActionForm.getFullDay() != 1) {
        int maxNum = Integer.parseInt(checkNameNum);
        for (int i = 0; i < maxNum; i++) {
          startPeriods = String.valueOf(startPeriods) + request.getParameter("startPeriod" + i) + ",";
          endPeriods = String.valueOf(endPeriods) + request.getParameter("endPeriod" + i) + ",";
        } 
        startPeriods = startPeriods.substring(0, startPeriods.length() - 1);
        endPeriods = endPeriods.substring(0, endPeriods.length() - 1);
        startPeriod = startPeriods;
        endPeriod = endPeriods;
      } else {
        fullDay = new Integer(1);
      } 
      String classOwnerIds = forumClassActionForm.getClassOwnerIds();
      if (classOwnerIds != null)
        classOwnerIds = classOwnerIds.replace('$', '*'); 
      String banPrint = forumClassActionForm.getBanPrint();
      String classEmail = request.getParameter("classEmail");
      ForumClassEJBBean bean = new ForumClassEJBBean();
      String message = "";
      try {
        message = bean.updatechild(classEmail, banPrint, classOwnerIds, editType, 
            editId, 
            userOrgGroup, 
            classOwnId, 
            classOwnerName, 
            classUserName, 
            classLevel, 
            className, 
            classRemark, 
            parentId, 
            currentSortCode, 
            insertSite, 
            parentSort, 
            checkExamin, 
            startPeriod, 
            endPeriod, 
            fullDay, estopAnonymity, isproject);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      request.setAttribute("message", message);
      List list = bd.see(mbd.getRightFinalWhere(curUserId.toString(), curOrgId.toString(), "06*02*02", "po.createdOrg", "po.createdEmp"), domainId);
      request.setAttribute("forumClass", list);
      action = "load";
    } 
    if ("moveForum".equals(action)) {
      List list = bd.see(mbd.getRightFinalWhere(curUserId.toString(), curOrgId.toString(), "06*02*02", "po.createdOrg", "po.createdEmp"), domainId);
      request.setAttribute("forumClass", list);
      return actionMapping.findForward("move");
    } 
    if ("see".equals(action)) {
      String isproject = request.getParameter("isproject");
      request.setAttribute("isproject", isproject);
      List list = bd.see(mbd.getRightFinalWhere(curUserId.toString(), curOrgId.toString(), "06*02*02", "po.createdOrg", "po.createdEmp"), domainId);
      request.setAttribute("forumClass", list);
      return actionMapping.findForward("add");
    } 
    if ("load".equals(action)) {
      String isproject = request.getParameter("isproject");
      ForumClassPO po = bd.load(request.getParameter("editId"));
      try {
        BeanUtils.copyProperties(forumClassActionForm, po);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      forumClassActionForm.setAllForumClass((new StringBuilder(String.valueOf(po.getClassParent()))).toString());
      forumClassActionForm.setClassParent(po.getClassParent());
      forumClassActionForm.setClassParentName(po.getClassParentName());
      forumClassActionForm.setClassSort(po.getClassSort());
      forumClassActionForm.setEditSort(po.getClassSort());
      forumClassActionForm.setClassLevel(po.getClassLevel());
      request.setAttribute("classSort", po.getClassSort());
      String userOrgGroup = "";
      if (po.getClassUserId() != null)
        userOrgGroup = String.valueOf(userOrgGroup) + po.getClassUserId(); 
      if (po.getClassUserOrg() != null)
        userOrgGroup = String.valueOf(userOrgGroup) + po.getClassUserOrg(); 
      if (po.getClassUserGroup() != null)
        userOrgGroup = String.valueOf(userOrgGroup) + po.getClassUserGroup(); 
      forumClassActionForm.setUserOrgGroup(userOrgGroup);
      request.setAttribute("userOrgGroup", userOrgGroup);
      List list = bd.see(mbd.getRightFinalWhere(curUserId.toString(), curOrgId.toString(), "06*02*02", "po.createdOrg", "po.createdEmp"), domainId);
      request.setAttribute("forumClass", list);
      request.setAttribute("isproject", isproject);
      request.setAttribute("startPeriod", forumClassActionForm.getStartPeriod());
      request.setAttribute("endPeriod", forumClassActionForm.getEndPeriod());
      String banPrint = "0";
      if (po.getBanPrint() != null)
        banPrint = po.getBanPrint(); 
      request.setAttribute("banPrint", banPrint);
      forumClassActionForm.setClassOwnerIds(po.getClassOwnerIds());
      String classEmail = "";
      if (po.getClassEmail() != null)
        classEmail = po.getClassEmail(); 
      request.setAttribute("classEmail", classEmail);
      int checkNameNum = 1;
      if (po.getStartPeriod() != null && !"".equals(po.getStartPeriod())) {
        String StartPeriod = String.valueOf(po.getStartPeriod()) + ",";
        String[] StartPeriods = StartPeriod.split(",");
        checkNameNum = StartPeriods.length;
      } 
      request.setAttribute("checkNameNum", (new StringBuilder(String.valueOf(checkNameNum))).toString());
      request.setAttribute("FullDay", (new StringBuilder(String.valueOf(po.getFullDay()))).toString());
      return actionMapping.findForward("modi");
    } 
    if ("del".equals(action)) {
      String retString = bd.del(request.getParameter("id"));
      String path = request.getRealPath("/");
      path = String.valueOf(path) + "/upload/forum";
      Accessory.delAccessory(path, retString);
      action = "list";
    } 
    if ("list".equals(action)) {
      int offset = 0;
      if (request.getParameter("pager.offset") != null)
        offset = Integer.parseInt(request.getParameter("pager.offset")); 
      String queryText = forumClassActionForm.getQueryText();
      String isproject = request.getParameter("isproject");
      String isprojectWhere = "";
      if ("N".equals(isproject)) {
        isprojectWhere = " and po.relProjectId=0 and po.proClassId=0";
      } else {
        isprojectWhere = " and po.relProjectId<>0 or po.proClassId<>0";
      } 
      ForumClassEJBBean formbean = new ForumClassEJBBean();
      String forumClass = "";
      try {
        forumClass = formbean.getForumClass(Integer.valueOf(offset), queryText, "( " + mbd.getRightFinalWhere(curUserId.toString(), curOrgId.toString(), "04*10*01", "po.createdOrg", "po.createdEmp") + ") and po.domainId=" + domainId + isprojectWhere);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      request.setAttribute("forumClass", forumClass);
      Vector<String> vec = bd.list(new Integer(offset), queryText, "( po.createdEmp = " + curUserId + " or " + mbd.getRightFinalWhere(curUserId.toString(), curOrgId.toString(), "06*02*02", "po.createdOrg", "po.createdEmp") + ") and po.domainId=" + domainId + isprojectWhere);
      String recordCount = vec.get(0);
      String pageSize = vec.get(1);
      List list = (List)vec.get(2);
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", pageSize);
      request.setAttribute("pageParameters", "action,queryText,isproject");
      request.setAttribute("publicinfo", request.getParameter("publicinfo"));
      boolean addRight = mbd.hasRightTypeName(curUserId, "讨论", "维护");
      request.setAttribute("addRight", addRight ? 1 : 0);
      if (!"N".equals(isproject))
        return actionMapping.findForward("isprojectList"); 
    } 
    if ("listMenu".equals(action)) {
      String whereScopePara = mbd.getScopeFinalWhere(curUserId, curOrgId, orgIdString, "po.classUserId", "po.classUserOrg", "po.classUserGroup");
      List list = bd.listMenu(whereScopePara, curUserId, domainId);
      request.setAttribute("mylist", list);
      boolean manageRight = mbd.hasRightType(curUserId, "讨论");
      request.setAttribute("manageRight", manageRight ? 1 : 0);
      return actionMapping.findForward("listMenu");
    } 
    if ("listAll".equals(action)) {
      String moveRight = "0";
      ForumClassBD cbd = new ForumClassBD();
      boolean scopeSQL = mbd.hasRight(curUserId.toString(), "06*02*02");
      if (scopeSQL)
        moveRight = "1"; 
      request.setAttribute("moveRight", moveRight);
      String whereScopePara = "";
      String whereScopeManagerString = "";
      if (!"1".equals(moveRight)) {
        whereScopePara = mbd.getScopeFinalWhere(curUserId.toString(), 
            curOrgId.toString(), 
            orgIdString, 
            "po.classUserId", 
            "po.classUserOrg", 
            "po.classUserGroup");
        whereScopePara = " 1<>1 " + cbd.getClassIdString(curUserId.toString(), "po.id", "po.classOwnerIds like '%*" + curUserId.toString() + "*%' or " + whereScopePara);
      } else {
        whereScopePara = mbd.getScopeFinalWhere(curUserId.toString(), 
            curOrgId.toString(), 
            orgIdString, 
            "po.classUserId", 
            "po.classUserOrg", 
            "po.classUserGroup");
        whereScopePara = " 1<>1 " + cbd.getClassIdString(curUserId.toString(), "po.id", "po.classOwnerIds like '%*" + curUserId.toString() + "*%' or " + whereScopePara);
        whereScopeManagerString = mbd.getRightFinalWhere(curUserId.toString(), curOrgId.toString(), orgIdString, "讨论", "维护", "po.createdOrg", "po.createdEmp");
        whereScopeManagerString = String.valueOf(whereScopeManagerString) + cbd.getClassIdString(curUserId.toString(), "po.id");
        if (whereScopeManagerString.trim().length() > 0)
          whereScopePara = whereScopePara; 
      } 
      List list = bd.listMenu(whereScopePara, curUserId, domainId);
      request.setAttribute("list", list);
      return actionMapping.findForward("listAll");
    } 
    return actionMapping.findForward("list");
  }
}
