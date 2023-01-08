package com.js.system.action.rolemanager;

import com.js.oa.security.log.service.LogBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.organizationmanager.OrganizationBD;
import com.js.system.service.rightmanager.RightBD;
import com.js.system.service.rolemanager.RoleBD;
import com.js.system.util.ConvertIdAndName;
import com.js.system.util.EndowVO;
import com.js.system.vo.rolemanager.RoleVO;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.FillBean;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RoleAction extends Action {
  String userId = "";
  
  String orgId = "";
  
  String browseRange = "";
  
  String rightCode = "";
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    this.browseRange = session.getAttribute("browseRange").toString();
    this.userId = session.getAttribute("userId").toString();
    this.orgId = session.getAttribute("orgId").toString();
    String domainId = session.getAttribute("domainId").toString();
    this.rightCode = "00*01*02";
    if ("1".equals(session.getAttribute("sysManager").toString()))
      this.rightCode = "00*01*01"; 
    RoleActionForm roleActionForm = (RoleActionForm)actionForm;
    String tag = "view";
    String action = httpServletRequest.getParameter("action");
    if ("view".equals(action)) {
      tag = "view";
      view(httpServletRequest);
    } else if ("search".equals(action)) {
      String roleName, roleDescription, userId = session.getAttribute("userId").toString();
      String orgId = session.getAttribute("orgId").toString();
      String orgIdString = session.getAttribute("orgIdString").toString();
      if (httpServletRequest.getParameter("searchName") != null) {
        roleName = httpServletRequest.getParameter("searchName");
      } else {
        roleName = "";
      } 
      if (httpServletRequest.getParameter("searchDesc") != null) {
        roleDescription = httpServletRequest.getParameter("searchDesc");
      } else {
        roleDescription = "";
      } 
      tag = "view";
      String viewSql = "role.roleId,role.roleName,role.roleDescription";
      String fromSql = "com.js.system.vo.rolemanager.RoleVO role";
      ManagerService managerBD = new ManagerService();
      String whereSql = " where ((" + 
        managerBD.getRightWhere(userId, orgId, this.rightCode, "role.createdOrg", "role.createdEmp");
      String where = managerBD.getScopeFinalWhere(userId, orgId, orgIdString, "role.roleUseRange", "role.roleUseRange", "role.roleUseRange");
      whereSql = String.valueOf(whereSql) + ") or (" + where + "))  and role.domainId=" + session.getAttribute("domainId");
      if (!"null".equals(roleName) && !"".equals(roleName))
        whereSql = String.valueOf(whereSql) + " and role.roleName like '%" + roleName + "%'"; 
      if (!"null".equals(roleDescription) && !"".equals(roleDescription))
        whereSql = String.valueOf(whereSql) + " and role.roleDescription like '%" + roleDescription + "%'"; 
      whereSql = String.valueOf(whereSql) + " order by role.roleId desc";
      list(httpServletRequest, viewSql, fromSql, whereSql);
      roleActionForm.setSearchRoleName("");
    } else if ("add".equals(action)) {
      ManagerService managerBD = new ManagerService();
      List<Object[]> list = managerBD.getRightScope(this.userId, this.rightCode);
      Object[] obj = list.get(0);
      String type = obj[0].toString();
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
      } else if ("0".equals(type) || "2".equals(type)) {
        httpServletRequest.setAttribute("managerRange", session.getAttribute("orgId"));
      } else {
        httpServletRequest.setAttribute("managerRange", "0");
      } 
      tag = "add";
      RightBD rightBD = new RightBD();
      List rightList = rightBD.getIdTypeName(session.getAttribute("sysManager").toString(), domainId);
      httpServletRequest.setAttribute("rightList", rightList);
      httpServletRequest.setAttribute("managerScope", getManagerRange(session));
    } else if ("continue".equals(action) || "close".equals(action)) {
      String rightIdString = roleActionForm.getRightIdString();
      RoleBD roleBD = new RoleBD();
      RoleVO roleVO = (RoleVO)FillBean.transformOneToOne(actionForm, RoleVO.class);
      roleVO.setCreatedEmp((new Long(this.userId)).longValue());
      roleVO.setDomainId(session.getAttribute("domainId").toString());
      int result = 0;
      if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
        if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit()) {
          String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
              session.getAttribute("orgId").toString(), "9", "autoAudit" };
          roleBD.save(roleVO, rightIdString.split(","), log);
        } 
        result = roleBD.save(roleVO, rightIdString.split(","));
      } 
      if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0) {
        String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
            session.getAttribute("orgId").toString(), "9" };
        result = roleBD.save(roleVO, rightIdString.split(","), log);
      } 
      if (result > 0) {
        httpServletRequest.setAttribute("opResult", 
            String.valueOf(result));
        RightBD rightBD = new RightBD();
        List rightList = rightBD.getIdTypeName(session.getAttribute("sysManager").toString(), domainId);
        httpServletRequest.setAttribute("rightList", rightList);
        ManagerService managerBD = new ManagerService();
        List<Object[]> list = managerBD.getRightScope(this.userId, this.rightCode);
        Object[] obj = list.get(0);
        String type = obj[0].toString();
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
        } else if ("0".equals(type) || "2".equals(type)) {
          httpServletRequest.setAttribute("managerRange", session.getAttribute("orgId"));
        } else {
          httpServletRequest.setAttribute("managerRange", "0");
        } 
        httpServletRequest.setAttribute("managerScope", getManagerRange(session));
        tag = "add";
      } else if (action.equals("continue")) {
        tag = "add";
        httpServletRequest.setAttribute("managerScope", getManagerRange(session));
        RightBD rightBD = new RightBD();
        List rightList = rightBD.getIdTypeName(session.getAttribute("sysManager").toString(), domainId);
        httpServletRequest.setAttribute("rightList", rightList);
        roleActionForm.setRoleDescription("");
        roleActionForm.setRoleName("");
        roleActionForm.setRoleUserId("");
        roleActionForm.setRoleUserName("");
        roleActionForm.setRoleUseRange("");
        roleActionForm.setRoleUseName("");
        ManagerService managerBD = new ManagerService();
        List<Object[]> list = managerBD.getRightScope(this.userId, this.rightCode);
        Object[] obj = list.get(0);
        String type = obj[0].toString();
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
        } else if ("0".equals(type) || "2".equals(type)) {
          httpServletRequest.setAttribute("managerRange", session.getAttribute("orgId"));
        } else {
          httpServletRequest.setAttribute("managerRange", "0");
        } 
      } else {
        tag = "close";
      } 
      if (SystemCommon.getAudit() == 0) {
        String userId = session.getAttribute("userId").toString();
        String userName = session.getAttribute("userName").toString();
        String userOrgName = session.getAttribute("orgName").toString();
        LogBD logBD = new LogBD();
        Date date = new Date();
        logBD.log(userId, userName, userOrgName, "system_role", "系统管理", 
            date, date, "1", roleVO.getRoleName(), 
            session.getAttribute("userIP").toString(), domainId);
      } 
    } else if ("deleteSingle".equals(action)) {
      tag = "view";
      String singleId = httpServletRequest.getParameter("singleId");
      if (singleId != null) {
        RoleBD roleBD = new RoleBD();
        if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
          if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit()) {
            String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
                session.getAttribute("orgId").toString(), "9", "autoAudit" };
            roleBD.newSingleDelete(singleId, log);
          } 
          singleId = roleBD.newSingleDelete(singleId);
        } 
        if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0) {
          String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
              session.getAttribute("orgId").toString(), "9" };
          singleId = roleBD.newSingleDelete(singleId, log);
          httpServletRequest.setAttribute("shenhe", "shenhe");
        } 
      } 
      if (SystemCommon.getAudit() == 0) {
        String userId = session.getAttribute("userId").toString();
        String userName = session.getAttribute("userName").toString();
        String userOrgName = session.getAttribute("orgName").toString();
        LogBD logBD = new LogBD();
        Date date = new Date();
        logBD.log(userId, userName, userOrgName, "system_role", "系统管理", 
            date, date, "3", singleId, session.getAttribute("userIP").toString(), domainId);
      } 
      view(httpServletRequest);
    } else if ("batchDelete".equals(action)) {
      tag = "view";
      String[] batchDeleteId = httpServletRequest.getParameterValues("batchDeleteId");
      String names = "";
      if (batchDeleteId != null) {
        RoleBD roleBD = new RoleBD();
        if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
          if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit()) {
            String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
                session.getAttribute("orgId").toString(), "9", "autoAudit" };
            roleBD.newBatchDelete(batchDeleteId, log);
          } 
          names = roleBD.newBatchDelete(batchDeleteId);
        } 
        if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0) {
          String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
              session.getAttribute("orgId").toString(), "9" };
          names = roleBD.newBatchDelete(batchDeleteId, log);
          httpServletRequest.setAttribute("shenhe", "shenhe");
        } 
      } 
      if (SystemCommon.getAudit() == 0) {
        String userId = session.getAttribute("userId").toString();
        String userName = session.getAttribute("userName").toString();
        String userOrgName = session.getAttribute("orgName").toString();
        LogBD logBD = new LogBD();
        Date date = new Date();
        logBD.log(userId, userName, userOrgName, "system_role", "系统管理", 
            date, date, "3", names, 
            session.getAttribute("userIP").toString(), domainId);
      } 
      view(httpServletRequest);
    } else if ("deleteAll".equals(action)) {
      tag = "view";
      RoleBD roleBD = new RoleBD();
      roleBD.deleteAll();
      String userId = session.getAttribute("userId").toString();
      String userName = session.getAttribute("userName").toString();
      String userOrgName = session.getAttribute("orgName").toString();
      LogBD logBD = new LogBD();
      Date date = new Date();
      logBD.log(userId, userName, userOrgName, "system_role", "系统管理", 
          date, date, "3", "所有角色", 
          session.getAttribute("userIP").toString(), domainId);
      view(httpServletRequest);
    } else if ("modify".equals(action)) {
      tag = "modify";
      String sysRole = httpServletRequest.getParameter("sysRole");
      ManagerService managerBD = new ManagerService();
      List<Object[]> list = managerBD.getRightScope(this.userId, this.rightCode);
      Object[] obj = list.get(0);
      String type = obj[0].toString();
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
      } else if ("0".equals(type) || "2".equals(type)) {
        httpServletRequest.setAttribute("managerRange", session.getAttribute("orgId"));
      } else {
        httpServletRequest.setAttribute("managerRange", "0");
      } 
      httpServletRequest.setAttribute("managerScope", getManagerRange(session));
      String modifyId = httpServletRequest.getParameter("modifyId");
      if (!"1".equals(sysRole)) {
        RightBD rightBD = new RightBD();
        List rightList = rightBD.getIdTypeName(domainId);
        httpServletRequest.setAttribute("rightList", rightList);
      } 
      RoleBD roleBD = new RoleBD();
      List<Object[]> roleList = roleBD.getSingleRoleInfo(modifyId);
      obj = roleList.get(0);
      httpServletRequest.setAttribute("roleId", modifyId);
      RoleActionForm form = new RoleActionForm();
      form.setRoleName(obj[0].toString());
      httpServletRequest.setAttribute("sysRole", sysRole);
      httpServletRequest.setAttribute("roleName", obj[0]);
      httpServletRequest.setAttribute("roleDescription", (obj[1] == null) ? "" : obj[1].toString());
      httpServletRequest.setAttribute("createdOrg", obj[2]);
      httpServletRequest.setAttribute("roleUserId", (obj[3] == null) ? "" : obj[3].toString());
      httpServletRequest.setAttribute("roleUserName", (obj[4] == null) ? "" : obj[4].toString());
      httpServletRequest.setAttribute("roleUseRange", (obj[5] == null) ? "" : obj[5].toString());
      httpServletRequest.setAttribute("roleUseName", (obj[6] == null) ? "" : obj[6].toString());
      httpServletRequest.setAttribute("roleRangeType", (obj[7] == null) ? "" : obj[7].toString());
      httpServletRequest.setAttribute("roleRange", (obj[8] == null) ? "" : obj[8].toString());
      httpServletRequest.setAttribute("roleRangeName", (obj[9] == null) ? "" : obj[9].toString());
      StringBuffer sb = new StringBuffer();
      List<String> roleRightList = roleBD.getSingleRoleRightId(modifyId);
      for (int i = 0; i < roleRightList.size(); i++)
        sb.append("$" + roleRightList.get(i) + "$,"); 
      httpServletRequest.setAttribute("roleRightString", sb.toString());
    } else if ("trueModi".equals(action)) {
      tag = "close";
      String modifyId = httpServletRequest.getParameter("roleId");
      String roleName = roleActionForm.getRoleName();
      String roleDescription = roleActionForm.getRoleDescription();
      String rightIdString = roleActionForm.getRightIdString();
      String createdOrg = String.valueOf(roleActionForm.getCreatedOrg());
      String roleUserName = roleActionForm.getRoleUserName();
      String roleUserId = roleActionForm.getRoleUserId();
      String roleUseRange = roleActionForm.getRoleUseRange();
      String roleUseName = roleActionForm.getRoleUseName();
      ConvertIdAndName cIdAndName = new ConvertIdAndName();
      EndowVO endowVO = cIdAndName.splitId(roleActionForm.getRoleUserId());
      String empIds = endowVO.getEmpIdArray();
      RoleBD roleBD = new RoleBD();
      RoleVO roleVO = new RoleVO();
      roleVO.setRoleId(Long.parseLong(modifyId));
      roleVO.setRoleUserId(roleUserId);
      roleVO.setRoleName(roleName);
      roleVO.setRoleUserName(roleUserName);
      roleVO.setRoleDescription(roleDescription);
      roleVO.setCreatedOrg(Long.parseLong(createdOrg));
      roleVO.setRoleUseRange(roleUseRange);
      roleVO.setRoleUseName(roleUseName);
      roleVO.setRoleRangeType(roleActionForm.getRoleRangeType());
      roleVO.setRoleRange(roleActionForm.getRoleRange());
      roleVO.setRoleRangeName(roleActionForm.getRoleRangeName());
      int result = 0;
      if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
        if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit()) {
          String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
              session.getAttribute("orgId").toString(), "9", "autoAudit" };
          roleBD.newUpdate(modifyId, rightIdString.split(","), empIds, roleVO, log);
        } 
        result = roleBD.newUpdate(modifyId, rightIdString.split(","), empIds, roleVO);
      } 
      if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0) {
        String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
            session.getAttribute("orgId").toString(), "9" };
        result = roleBD.newUpdate(modifyId, rightIdString.split(","), empIds, roleVO, log);
      } 
      if (result > 0) {
        httpServletRequest.setAttribute("opResult", 
            String.valueOf(result));
        httpServletRequest.setAttribute("managerScope", getManagerRange(session));
        tag = "add";
        RightBD rightBD = new RightBD();
        List rightList = rightBD.getIdTypeName(domainId);
        httpServletRequest.setAttribute("rightList", rightList);
        ManagerService managerBD = new ManagerService();
        List<Object[]> list = managerBD.getRightScope(this.userId, this.rightCode);
        Object[] obj = list.get(0);
        String type = obj[0].toString();
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
        } else if ("0".equals(type) || "2".equals(type)) {
          httpServletRequest.setAttribute("managerRange", session.getAttribute("orgId"));
        } else {
          httpServletRequest.setAttribute("managerRange", "0");
        } 
        List<Object[]> roleList = roleBD.getSingleRoleInfo(modifyId);
        obj = roleList.get(0);
        httpServletRequest.setAttribute("roleId", modifyId);
        httpServletRequest.setAttribute("roleName", obj[0]);
        httpServletRequest.setAttribute("roleDescription", obj[1]);
        httpServletRequest.setAttribute("createdOrg", obj[2]);
        StringBuffer sb = new StringBuffer();
        List<String> roleRightList = roleBD.getSingleRoleRightId(modifyId);
        for (int i = 0; i < roleRightList.size(); i++)
          sb.append("$" + roleRightList.get(i) + "$,"); 
        httpServletRequest.setAttribute("roleRightString", sb.toString());
      } 
      httpServletRequest.setAttribute("searchName", httpServletRequest.getParameter("searchName"));
      httpServletRequest.setAttribute("searchDesc", httpServletRequest.getParameter("searchDesc"));
      if (SystemCommon.getAudit() == 0) {
        String userId = session.getAttribute("userId").toString();
        String userName = session.getAttribute("userName").toString();
        String userOrgName = session.getAttribute("orgName").toString();
        LogBD logBD = new LogBD();
        Date date = new Date();
        logBD.log(userId, userName, userOrgName, "system_role", "系统管理", 
            date, date, "2", roleName, session.getAttribute("userIP").toString(), domainId);
      } 
    } 
    if ("close".equals(tag))
      httpServletRequest.setAttribute("pager.offset", httpServletRequest.getParameter("pager.offset")); 
    return actionMapping.findForward(tag);
  }
  
  public void view(HttpServletRequest httpServletRequest) {
    String viewSql = "role.roleId,role.roleName,role.roleDescription";
    String fromSql = "com.js.system.vo.rolemanager.RoleVO role";
    ManagerService managerBD = new ManagerService();
    HttpSession session = httpServletRequest.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String whereSql = "";
    whereSql = " where ((" + managerBD.getRightWhere(userId, orgId, this.rightCode, "role.createdOrg", "role.createdEmp");
    String modiSql = whereSql;
    String where = managerBD.getScopeFinalWhere(userId, orgId, orgIdString, "role.roleUseRange", "role.roleUseRange", "role.roleUseRange");
    whereSql = String.valueOf(whereSql) + ") or (" + where + ")) and role.domainId=" + 
      session.getAttribute("domainId");
    modiSql = String.valueOf(modiSql) + ")) and  role.domainId=" + session.getAttribute("domainId");
    String searchName = httpServletRequest.getParameter("searchName");
    String searchDesc = httpServletRequest.getParameter("searchDesc");
    if (searchName != null && !"null".equals(searchName) && !"".equals(searchName)) {
      whereSql = String.valueOf(whereSql) + " and role.roleName like '%" + searchName + "%'";
      modiSql = String.valueOf(modiSql) + " and role.roleName like '%" + searchName + "%'";
    } 
    if (searchDesc != null && !"null".equals(searchDesc) && !"".equals(searchDesc)) {
      whereSql = String.valueOf(whereSql) + " and role.roleDescription like '%" + searchDesc + "%'";
      modiSql = String.valueOf(modiSql) + " and role.roleDescription like '%" + searchDesc + "%'";
    } 
    whereSql = String.valueOf(whereSql) + " order by role.roleId desc";
    if (!"1".equals(session.getAttribute("sysManager").toString())) {
      RoleBD roleBD = new RoleBD();
      modiSql = roleBD.getCanUpdateIds(modiSql);
    } else {
      modiSql = "";
    } 
    httpServletRequest.setAttribute("canUpdateIds", modiSql);
    list(httpServletRequest, viewSql, fromSql, whereSql);
  }
  
  private void list(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
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
    httpServletRequest.setAttribute("roleList", list);
    httpServletRequest.setAttribute("recordCount", String.valueOf(recordCount));
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,searchDesc,searchName");
  }
  
  private String getManagerRange(HttpSession session) {
    String managerScope = "*" + session.getAttribute("orgId") + "*";
    if ("1".equals(session.getAttribute("sysManager").toString())) {
      managerScope = "*0*";
    } else {
      Object[] obj = (new ManagerService()).getRightScope(session
          .getAttribute("userId").toString(), this.rightCode).get(0);
      String type = obj[0].toString();
      if ("4".equals(type) && 
        obj[1] != null)
        managerScope = obj[1].toString(); 
    } 
    return managerScope;
  }
}
