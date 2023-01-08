package com.js.system.action.groupmanager;

import com.js.oa.security.log.service.LogBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.groupmanager.GroupBD;
import com.js.system.service.organizationmanager.OrganizationBD;
import com.js.system.util.ConvertIdAndName;
import com.js.system.util.EndowVO;
import com.js.system.vo.groupmanager.GroupVO;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.FillBean;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GroupAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    HttpSession session = httpServletRequest.getSession(true);
    GroupActionForm groupActionForm = (GroupActionForm)actionForm;
    GroupBD groupBD = new GroupBD();
    String action = httpServletRequest.getParameter("action");
    String userId = session.getAttribute("userId").toString();
    String domainId = session.getAttribute("domainId").toString();
    String rightName = "1".equals(session.getAttribute("sysManager").toString()) ? "00*01*01" : "00*01*02";
    String groupType = httpServletRequest.getParameter("groupType");
    String orgIdString = session.getAttribute("orgIdString").toString();
    if (action == null)
      action = groupActionForm.getAction(); 
    String tag = "";
    if (action.equals("view")) {
      tag = "view";
      if (groupType != null && groupType.equals("1"))
        tag = "forward"; 
      list(httpServletRequest);
    } else if (action.equals("add")) {
      ManagerService managerBD = new ManagerService();
      List<Object[]> list = managerBD.getRightScope(userId, rightName);
      Object[] obj = (list == null || list.size() < 1) ? null : list.get(0);
      String type = "";
      if (obj != null && obj.length > 1)
        type = obj[0].toString(); 
      if ("4".equals(type)) {
        String range = (String)obj[1];
        if (range.indexOf("**") > 0) {
          list = (new OrganizationBD()).getNameAndId(range);
          httpServletRequest.setAttribute("multiRange", "1");
          httpServletRequest.setAttribute("managerRange", list);
        } else if (range.equals("0")) {
          httpServletRequest.setAttribute(
              "managerRange", "0");
        } else {
          httpServletRequest.setAttribute("managerRange", 
              range.substring(1, range.length() - 1));
        } 
      } else if ("0".equals(type)) {
        httpServletRequest.setAttribute("managerRange", session.getAttribute("orgId"));
      } else {
        httpServletRequest.setAttribute("managerRange", "0");
      } 
      httpServletRequest.setAttribute("groupRange", getGroupRange(httpServletRequest));
      tag = "add";
    } else if (action.equals("continue") || action.equals("close")) {
      ConvertIdAndName cIdAndName = new ConvertIdAndName();
      EndowVO endowVO = cIdAndName.splitId(groupActionForm.getGroupUserString());
      String strId = endowVO.getEmpIdArray();
      GroupVO groupVO = (GroupVO)FillBean.transformOneToOne(actionForm, GroupVO.class);
      groupVO.setRangeName(httpServletRequest.getParameter("rangeName"));
      groupVO.setRangeEmp(httpServletRequest.getParameter("rangeEmp"));
      groupVO.setRangeOrg(httpServletRequest.getParameter("rangeOrg"));
      groupVO.setRangeGroup(httpServletRequest.getParameter("rangeGroup"));
      groupVO.setCreatedEmp(session.getAttribute("userId").toString());
      groupVO.setDomainId(session.getAttribute("domainId").toString());
      groupVO.setGroupType(groupType);
      groupVO.setOrgIdString(orgIdString);
      int result = groupBD.add(groupVO, strId.split(","), httpServletRequest);
      String userName = session.getAttribute("userName").toString();
      String orgName = session.getAttribute("orgName").toString();
      LogBD logBD = new LogBD();
      Date date = new Date();
      logBD.log(userId, userName, orgName, "system_group", "系统管理", date, date, "1", groupVO.getGroupName(), session.getAttribute("userIP").toString(), domainId);
      if (result > 0) {
        httpServletRequest.setAttribute("opResult", String.valueOf(result));
        ManagerService managerBD = new ManagerService();
        List<Object[]> list = managerBD.getRightScope(userId, rightName);
        Object[] obj = (list == null || list.size() < 1) ? null : list.get(0);
        String type = "";
        if (obj != null && obj.length > 1)
          type = obj[0].toString(); 
        if ("4".equals(type)) {
          String range = (String)obj[1];
          if (range.indexOf("**") > 0) {
            list = (new OrganizationBD()).getNameAndId(range);
            httpServletRequest.setAttribute("multiRange", "1");
            httpServletRequest.setAttribute("managerRange", list);
          } else if (range.equals("0")) {
            httpServletRequest.setAttribute(
                "managerRange", "0");
          } else {
            httpServletRequest.setAttribute("managerRange", 
                range.substring(1, range.length() - 1));
          } 
        } else if ("0".equals(type)) {
          httpServletRequest.setAttribute("managerRange", 
              session.getAttribute("orgId"));
        } else {
          httpServletRequest.setAttribute("managerRange", "0");
        } 
        httpServletRequest.setAttribute("createdOrg", groupVO.getCreatedOrg());
        httpServletRequest.setAttribute("groupOrder", groupVO.getGroupOrder());
        httpServletRequest.setAttribute("groupRange", getGroupRange(httpServletRequest));
        tag = "continue";
      } else {
        groupActionForm.setGroupName("");
        groupActionForm.setGroupUserString("");
        groupActionForm.setGroupUserNames("");
        if (action.equals("continue")) {
          tag = "continue";
          ManagerService managerBD = new ManagerService();
          List<Object[]> list = managerBD.getRightScope(userId, rightName);
          Object[] obj = (list == null || list.size() < 1) ? null : list.get(0);
          String type = "";
          if (obj != null && obj.length > 1)
            type = obj[0].toString(); 
          if ("4".equals(type)) {
            String range = (String)obj[1];
            if (range.indexOf("**") > 0) {
              list = (new OrganizationBD()).getNameAndId(range);
              httpServletRequest.setAttribute("multiRange", "1");
              httpServletRequest.setAttribute("managerRange", 
                  list);
            } else if (range.equals("0")) {
              httpServletRequest
                .setAttribute(
                  "managerRange", "0");
            } else {
              httpServletRequest.setAttribute("managerRange", 
                  range.substring(1, range.length() - 1));
            } 
          } else if ("0".equals(type)) {
            httpServletRequest.setAttribute("managerRange", 
                session.getAttribute("orgId"));
          } else {
            httpServletRequest.setAttribute("managerRange", "0");
          } 
          httpServletRequest.setAttribute("createdOrg", groupVO.getCreatedOrg());
          httpServletRequest.setAttribute("groupRange", getGroupRange(httpServletRequest));
        } else {
          tag = "close";
          httpServletRequest.setAttribute("parentUrl", "/jsoa/GroupAction.do?action=view&pager.offset=" + httpServletRequest.getParameter("pager.offset") + "&groupType=" + groupType);
        } 
      } 
      if (1 == SystemCommon.getAudit() && "0".equals(groupType))
        httpServletRequest.setAttribute("flag", "foraudit"); 
    } else if (action.equals("del")) {
      tag = "view";
      String[] ids = { "" };
      if (httpServletRequest.getParameterValues("batchDel") != null) {
        ids = httpServletRequest.getParameterValues("batchDel");
      } else {
        ids[0] = httpServletRequest.getParameter("id");
      } 
      String names = groupBD.del(ids, httpServletRequest);
      if (1 == SystemCommon.getAudit() && "0".equals(groupType))
        httpServletRequest.setAttribute("flag", "foraudit"); 
      String userName = session.getAttribute("userName").toString();
      String orgName = session.getAttribute("orgName").toString();
      LogBD logBD = new LogBD();
      Date date = new Date();
      logBD.log(userId, userName, orgName, "system_group", "系统管理", date, date, "3", names, session.getAttribute("userIP").toString(), domainId);
      if (groupType == null || groupType.equals("0"))
        list(httpServletRequest); 
      if (groupType != null && groupType.equals("1"))
        tag = "forward"; 
    } else if (action.equals("delAll")) {
      groupBD.delAll();
      tag = "view";
      list(httpServletRequest);
    } else if (action.equals("modify")) {
      tag = "modify";
      ManagerService managerBD = new ManagerService();
      List<Object[]> list = managerBD.getRightScope(userId, rightName);
      Object[] obj = (list == null || list.size() < 1) ? null : list.get(0);
      String type = "";
      if (obj != null && obj.length > 1)
        type = obj[0].toString(); 
      if ("4".equals(type)) {
        String range = (String)obj[1];
        if (range.indexOf("**") > 0) {
          list = (new OrganizationBD()).getNameAndId(range);
          httpServletRequest.setAttribute("multiRange", "1");
          httpServletRequest.setAttribute("managerRange", list);
        } else if (range.equals("0")) {
          httpServletRequest.setAttribute(
              "managerRange", "0");
        } else {
          httpServletRequest.setAttribute("managerRange", 
              range.substring(1, range.length() - 1));
        } 
      } else if ("0".equals(type)) {
        httpServletRequest.setAttribute("managerRange", 
            session.getAttribute("orgId"));
      } else {
        httpServletRequest.setAttribute("managerRange", "0");
      } 
      String id = httpServletRequest.getParameter("id");
      list = groupBD.selectSingle(id);
      obj = list.get(0);
      httpServletRequest.setAttribute("groupId", id);
      httpServletRequest.setAttribute("groupName", obj[0]);
      if (obj[1] == null) {
        httpServletRequest.setAttribute("groupUserString", "");
      } else {
        httpServletRequest.setAttribute("groupUserString", obj[1]);
      } 
      httpServletRequest.setAttribute("groupUserName", obj[2]);
      httpServletRequest.setAttribute("createdOrg", obj[3]);
      httpServletRequest.setAttribute("pager.offset", httpServletRequest.getParameter("pager.offset"));
      httpServletRequest.setAttribute("rangeName", (obj[4] == null) ? "" : obj[4]);
      httpServletRequest.setAttribute("rangeEmp", (obj[5] == null) ? "" : obj[5]);
      httpServletRequest.setAttribute("rangeOrg", (obj[6] == null) ? "" : obj[6]);
      httpServletRequest.setAttribute("rangeGroup", (obj[7] == null) ? "" : obj[7]);
      httpServletRequest.setAttribute("groupOrder", (obj[8] == null) ? "1000" : obj[8]);
      httpServletRequest.setAttribute("groupRange", getGroupRange(httpServletRequest));
      String userName = session.getAttribute("userName").toString();
      String orgName = session.getAttribute("orgName").toString();
      LogBD logBD = new LogBD();
      Date date = new Date();
      logBD.log(userId, userName, orgName, "system_group", "系统管理", date, date, "2", obj[0].toString(), session.getAttribute("userIP").toString(), domainId);
    } else if (action.equals("modifyClose")) {
      tag = "close";
      httpServletRequest.setAttribute("parentUrl", "/jsoa/GroupAction.do?action=view&pager.offset=" + httpServletRequest.getParameter("pager.offset") + "&groupType=" + groupType);
      String groupId = groupActionForm.getGroupId();
      String groupName = groupActionForm.getGroupName();
      String groupUserString = groupActionForm.getGroupUserString();
      String groupUserNames = groupActionForm.getGroupUserNames();
      String createdOrg = groupActionForm.getCreatedOrg();
      String groupOrder = groupActionForm.getGroupOrder();
      ConvertIdAndName cIdAndName = new ConvertIdAndName();
      EndowVO endowVO = cIdAndName.splitId(groupUserString);
      String strId = endowVO.getEmpIdArray();
      int result = groupBD.update(groupId, groupName, groupUserString, strId.split(","), groupUserNames, createdOrg, httpServletRequest.getParameter("rangeName"), httpServletRequest.getParameter("rangeEmp"), httpServletRequest.getParameter("rangeOrg"), httpServletRequest.getParameter("rangeGroup"), groupType, groupOrder, httpServletRequest);
      if (result > 0) {
        httpServletRequest.setAttribute("opResult", String.valueOf(result));
        tag = "modify";
        httpServletRequest.setAttribute("groupId", groupId);
        httpServletRequest.setAttribute("groupName", groupName);
        httpServletRequest.setAttribute("groupUserString", groupUserString);
        httpServletRequest.setAttribute("groupUserName", groupUserNames);
        httpServletRequest.setAttribute("createdOrg", createdOrg);
        httpServletRequest.setAttribute("groupOrder", groupOrder);
        ManagerService managerBD = new ManagerService();
        List<Object[]> list = managerBD.getRightScope(userId, rightName);
        Object[] obj = (list == null || list.size() < 1) ? null : list.get(0);
        String type = "";
        if (obj != null && obj.length > 1)
          type = obj[0].toString(); 
        if ("4".equals(type)) {
          String range = (String)obj[1];
          if (range.indexOf("**") > 0) {
            list = (new OrganizationBD()).getNameAndId(range);
            httpServletRequest.setAttribute("multiRange", "1");
            httpServletRequest.setAttribute("managerRange", list);
          } else if (range.equals("0")) {
            httpServletRequest.setAttribute(
                "managerRange", "0");
          } else {
            httpServletRequest.setAttribute("managerRange", 
                range.substring(1, range.length() - 1));
          } 
        } else if ("0".equals(type)) {
          httpServletRequest.setAttribute("managerRange", 
              session.getAttribute("orgId"));
        } else {
          httpServletRequest.setAttribute("managerRange", "0");
        } 
      } 
      if (1 == SystemCommon.getAudit() && "0".equals(groupType))
        httpServletRequest.setAttribute("flag", "foraudit"); 
    } else if (action.equals("privateGroup")) {
      list(httpServletRequest);
      tag = "privateGroup";
    } else {
      if (action.equals("saveAsPrivateGroup")) {
        String PId = httpServletRequest.getParameter("PId");
        String PName = httpServletRequest.getParameter("PName");
        String groupName = httpServletRequest.getParameter("groupName");
        StringBuffer xml = new StringBuffer(1024);
        Integer i = groupBD.saveAsGroup(PName, PId, groupName, domainId, session.getAttribute("orgId").toString(), userId);
        httpServletResponse.setContentType("text/xml;charset=GBK");
        PrintWriter out = httpServletResponse.getWriter();
        xml.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>\n");
        xml.append("<result>\n");
        xml.append("  <flag>" + i + "</flag>\n");
        xml.append("</result>\n");
        out.print(xml.toString());
        out.close();
        return null;
      } 
      if (action.equals("checkName")) {
        String name = httpServletRequest.getParameter("name");
        StringBuffer xml = new StringBuffer(1024);
        httpServletResponse.setContentType("text/xml;charset=GBK");
        PrintWriter out = httpServletResponse.getWriter();
        List list = groupBD.checkGroupByName(name, userId);
        int i = 0;
        if (list != null && list.size() > 0)
          i = 1; 
        xml.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>\n");
        xml.append("<result>\n");
        xml.append("  <name>" + i + "</name>\n");
        xml.append("</result>\n");
        out.print(xml.toString());
        out.close();
        return null;
      } 
    } 
    httpServletRequest.setAttribute("searchName", httpServletRequest.getParameter("searchName"));
    httpServletRequest.setAttribute("searchUser", httpServletRequest.getParameter("searchUser"));
    httpServletRequest.setAttribute("pager.offset", httpServletRequest.getParameter("pager.offset"));
    httpServletRequest.setAttribute("groupType", httpServletRequest.getParameter("groupType"));
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null && !"null".equals(httpServletRequest.getParameter("pager.offset")) && !"".equals(httpServletRequest.getParameter("pager.offset")))
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String whereSql = "";
    HttpSession session = httpServletRequest.getSession(true);
    String managerWhere = "";
    if ("1".equals(session.getAttribute("sysManager").toString())) {
      managerWhere = "1=1";
    } else {
      ManagerService managerBD = new ManagerService();
      managerWhere = managerBD.getRightFinalWhere(session.getAttribute("userId").toString(), 
          session.getAttribute("orgId").toString(), 
          "00*01*02", 
          "aaa.createdOrg", 
          "aaa.createdEmp");
    } 
    String groupType = httpServletRequest.getParameter("groupType");
    if ("1".equals(session.getAttribute("sysManager").toString())) {
      whereSql = " where aaa.createdEmp=emp.empId and aaa.domainId=" + httpSession.getAttribute("domainId");
    } else {
      whereSql = " where aaa.createdEmp=emp.empId and aaa.domainId=" + httpSession.getAttribute("domainId");
    } 
    if ("1".equals(groupType)) {
      whereSql = String.valueOf(whereSql) + " and aaa.groupType=1 and emp.empId=" + session.getAttribute("userId");
    } else if (managerWhere != null && managerWhere.trim().length() > 0) {
      whereSql = String.valueOf(whereSql) + " and aaa.groupType=0 and ((" + managerWhere + ")" + " or emp.empId=" + session.getAttribute("userId") + ")";
    } else {
      whereSql = String.valueOf(whereSql) + " and aaa.groupType=0 and emp.empId=" + session.getAttribute("userId");
    } 
    String searchName = httpServletRequest.getParameter("searchName");
    String searchUser = httpServletRequest.getParameter("searchUser");
    if (searchName != null && !"".equals(searchName) && !"".equals(searchName))
      whereSql = String.valueOf(whereSql) + " and aaa.groupName like '%" + searchName + "%'"; 
    if (searchUser != null && !"".equals(searchUser) && !"".equals(searchUser))
      whereSql = String.valueOf(whereSql) + " and aaa.groupUserNames like '%" + searchUser + "%'"; 
    whereSql = String.valueOf(whereSql) + " order by aaa.groupOrder,aaa.groupId desc";
    Page page = new Page("aaa.groupId,aaa.groupName,aaa.groupUserNames,emp.empName", 
        "com.js.system.vo.groupmanager.GroupVO aaa , com.js.system.vo.usermanager.EmployeeVO emp", 
        whereSql);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    int recordCount = page.getRecordCount();
    if (offset >= recordCount) {
      offset = (recordCount - pageSize) / pageSize;
      currentPage = offset + 1;
      offset *= pageSize;
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      recordCount = page.getRecordCount();
      httpServletRequest.setAttribute("pager.offset", String.valueOf(offset));
      httpServletRequest.setAttribute("pager.realCurrent", String.valueOf(currentPage));
    } 
    httpServletRequest.setAttribute("groupList", list);
    httpServletRequest.setAttribute("recordCount", String.valueOf(recordCount));
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,groupType,searchName,searchUser");
  }
  
  public String getGroupRange(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    ManagerService managerBD = new ManagerService();
    String range = "0";
    String rightCode = "00*01*02";
    if ("1".equals(session.getAttribute("sysManager").toString()))
      rightCode = "00*01*01"; 
    if (rightCode.equals("00*01*01")) {
      range = "0";
    } else {
      List<Object[]> list = managerBD.getRightScope(session.getAttribute("userId").toString(), rightCode);
      if (list != null && list.size() > 0) {
        Object[] obj = list.get(0);
        String scopeType = obj[0].toString();
        if ("0".equals(scopeType)) {
          range = "0";
        } else if (!"1".equals(scopeType)) {
          if ("2".equals(scopeType)) {
            range = "*" + session.getAttribute("orgId") + "*";
          } else if ("3".equals(scopeType)) {
            range = "*" + session.getAttribute("orgId") + "*";
          } else if ("4".equals(scopeType)) {
            range = obj[1].toString();
          } 
        } 
      } 
    } 
    return range;
  }
}
