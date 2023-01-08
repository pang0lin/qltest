package com.js.oa.personalwork.netaddress.action;

import com.js.oa.bbs.bean.ForumEJBBean;
import com.js.oa.bbs.service.ForumBD;
import com.js.oa.personalwork.netaddress.po.AddressClassPO;
import com.js.oa.personalwork.netaddress.service.AddressClassBD;
import com.js.system.manager.bean.ManagerEJBBean;
import com.js.system.util.StaticParam;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AddressClassAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    Long curUserId = new Long(request.getSession(true).getAttribute("userId").toString());
    HttpSession session = request.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "" : session.getAttribute("domainId").toString();
    AddressClassActionForm addressClassActionForm = (AddressClassActionForm)actionForm;
    AddressClassBD bd = new AddressClassBD();
    String action = request.getParameter("action");
    if ("delBatch".equals(action)) {
      if (request.getParameter("ids") != null)
        bd.delBatch(request.getParameter("ids"), (String)curUserId); 
      action = "list";
    } 
    if ("delAll".equals(action)) {
      bd.delAll((String)curUserId);
      action = "list";
    } 
    if ("add".equals(action)) {
      Boolean result = bd.add(addressClassActionForm.getClassName().trim(), Integer.valueOf(addressClassActionForm.getClassIsShare()), action, request.getParameter("id"), (String)curUserId, domainId);
      String message = result.toString();
      if (result == Boolean.FALSE)
        request.setAttribute("message", "false"); 
      if (!"false".equals(message))
        addressClassActionForm.setClassName(""); 
      return actionMapping.findForward("add");
    } 
    if ("load".equals(action)) {
      AddressClassPO addressClassPO = bd.load(request.getParameter("id"));
      addressClassActionForm.setClassIsShare(addressClassPO.getClassIsShare());
      addressClassActionForm.setClassName(addressClassPO.getClassName());
      request.setAttribute("classIsShare", Integer.valueOf(addressClassPO.getClassIsShare()));
      return actionMapping.findForward("modi");
    } 
    if ("update".equals(action)) {
      Boolean result = bd.update(request.getParameter("className").trim(), Integer.valueOf(addressClassActionForm.getClassIsShare()), action, request.getParameter("id"), (String)curUserId);
      String message = result.toString();
      if (result == Boolean.FALSE)
        request.setAttribute("message", "false"); 
      return actionMapping.findForward("modi");
    } 
    if ("list".equals(action))
      try {
        list(request);
      } catch (Exception e) {
        e.printStackTrace();
      }  
    return actionMapping.findForward("list");
  }
  
  public void list(HttpServletRequest request) throws Exception {
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    long userId = Long.parseLong(String.valueOf(request.getSession(true).getAttribute("userId")));
    Page page = new Page(
        " po.id,po.className,po.classIsShare,po.empId", 
        " com.js.oa.personalwork.netaddress.po.AddressClassPO po ", 
        " where 1=1 order by po.id desc");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List<Object[]> list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String canModiAndDelIds = "";
    ManagerEJBBean meb = new ManagerEJBBean();
    String curUserId = request.getSession(true).getAttribute("userId").toString();
    String curOrgIds = StaticParam.getOrgIdByEmpId(curUserId);
    String rightCodeString = "08*04*01";
    String rightScopeString = "";
    List<Object[]> scopeList = meb.getRightScope(curUserId, rightCodeString);
    if (scopeList.size() > 0) {
      Object[] obj = scopeList.get(0);
      rightScopeString = obj[0].toString();
    } 
    Object[] objects = new Object[0];
    String createEmpId = "";
    ForumBD fb = new ForumBD();
    ForumEJBBean feb = new ForumEJBBean();
    if ("1".equals(rightScopeString)) {
      for (int i = 0; i < list.size(); i++) {
        objects = list.get(i);
        createEmpId = objects[3].toString();
        if (curUserId.equals(createEmpId))
          canModiAndDelIds = String.valueOf(canModiAndDelIds) + objects[0].toString() + ","; 
      } 
    } else if ("2".equals(rightScopeString)) {
      for (int i = 0; i < list.size(); i++) {
        objects = list.get(i);
        createEmpId = objects[3].toString();
        String createEmpOrgIds = StaticParam.getOrgIdByEmpId(createEmpId);
        String relationType = fb.getOrgRelationType(curOrgIds.split(","), createEmpOrgIds.split(","));
        if ("selfOrg".equals(relationType) || "subOrg".equals(relationType))
          canModiAndDelIds = String.valueOf(canModiAndDelIds) + objects[0].toString() + ","; 
      } 
    } else if ("3".equals(rightScopeString)) {
      for (int i = 0; i < list.size(); i++) {
        objects = list.get(i);
        createEmpId = objects[3].toString();
        String createEmpOrgIds = StaticParam.getOrgIdByEmpId(createEmpId);
        String relationType = fb.getOrgRelationType(curOrgIds.split(","), createEmpOrgIds.split(","));
        if ("selfOrg".equals(relationType))
          canModiAndDelIds = String.valueOf(canModiAndDelIds) + objects[0].toString() + ","; 
      } 
    } else if ("4".equals(rightScopeString)) {
      for (int i = 0; i < list.size(); i++) {
        objects = list.get(i);
        createEmpId = objects[3].toString();
        String createEmpOrgIds = StaticParam.getOrgIdByEmpId(createEmpId);
        String customScopeString = feb.getCustomScope(curUserId, "08*04*01", "0");
        if (customScopeString.length() > 0) {
          String orgsString = "";
          String usersString = "";
          if (customScopeString.indexOf("-") != 0)
            orgsString = customScopeString.split("-")[0]; 
          if (customScopeString.indexOf("-") != customScopeString.length() - 1)
            if ((customScopeString.split("-")).length > 1) {
              usersString = customScopeString.split("-")[1];
            } else {
              usersString = customScopeString.split("-")[0];
            }  
          if (usersString != null && !"".equals(usersString)) {
            usersString = usersString.replaceAll("\\$\\$", ",").replaceAll("\\$", ",");
            String[] usersArray = usersString.split(",");
            for (int j = 0; j < usersArray.length; j++) {
              if (createEmpId.equals(usersArray[j]))
                canModiAndDelIds = String.valueOf(canModiAndDelIds) + objects[0].toString() + ","; 
            } 
          } 
          String[] createEmpOrgIdsArray = createEmpOrgIds.split(",");
          if (orgsString != null && !"".equals(orgsString)) {
            orgsString = orgsString.replaceAll("\\*\\*", ",").replaceAll("\\*", ",");
            String[] orgsArray = orgsString.split(",");
            String scopeOrgStringTemp = "";
            String createOrgStringTemp = "";
            for (int k = 0; k < orgsArray.length; k++) {
              scopeOrgStringTemp = orgsArray[k];
              for (int m = 0; m < createEmpOrgIdsArray.length; m++) {
                createOrgStringTemp = createEmpOrgIdsArray[m];
                if (scopeOrgStringTemp.equals(createOrgStringTemp))
                  canModiAndDelIds = String.valueOf(canModiAndDelIds) + objects[0].toString() + ","; 
              } 
            } 
          } 
        } 
      } 
    } else {
      for (int i = 0; i < list.size(); i++) {
        objects = list.get(i);
        if (!"0".equals(objects[3].toString()))
          canModiAndDelIds = String.valueOf(canModiAndDelIds) + objects[0].toString() + ","; 
      } 
    } 
    if (canModiAndDelIds.length() > 0)
      canModiAndDelIds = canModiAndDelIds.substring(0, canModiAndDelIds.lastIndexOf(",")); 
    request.setAttribute("mylist", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action");
    request.setAttribute("canModiAndDelIds", canModiAndDelIds);
  }
}
