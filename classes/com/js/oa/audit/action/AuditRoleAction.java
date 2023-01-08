package com.js.oa.audit.action;

import com.js.oa.audit.bean.AuditMsRemindEJBBean;
import com.js.oa.audit.po.AuditLog;
import com.js.oa.audit.po.RolePO;
import com.js.oa.security.log.service.LogBD;
import com.js.system.service.rightmanager.RightBD;
import com.js.system.service.rolemanager.RoleBD;
import com.js.system.util.StaticParam;
import com.js.system.vo.rolemanager.RoleVO;
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

public class AuditRoleAction extends Action {
  String userId = "";
  
  String orgId = "";
  
  String browseRange = "";
  
  String rightCode = "";
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    HttpSession session = httpServletRequest.getSession(true);
    this.browseRange = session.getAttribute("browseRange").toString();
    this.userId = session.getAttribute("userId").toString();
    this.orgId = session.getAttribute("orgId").toString();
    String comeFlag = (httpServletRequest.getParameter("comeFlag") == null) ? "" : httpServletRequest.getParameter("comeFlag");
    httpServletRequest.setAttribute("comeFlag", comeFlag);
    String domainId = session.getAttribute("domainId").toString();
    this.rightCode = "00*01*02";
    if ("1".equals(session.getAttribute("sysManager").toString()))
      this.rightCode = "00*01*01"; 
    AuditRoleActionForm roleActionForm = (AuditRoleActionForm)actionForm;
    String tag = "view";
    String action = httpServletRequest.getParameter("action");
    String flag = action;
    if ("modify".equals(action)) {
      tag = "modify";
      String sysRole = httpServletRequest.getParameter("sysRole");
      Object[] obj = (Object[])null;
      httpServletRequest.setAttribute("managerScope", getManagerRange(session));
      String logId = httpServletRequest.getParameter("logId");
      if (!"1".equals(sysRole)) {
        RightBD rightBD = new RightBD();
        List rightList = rightBD.getIdTypeName(domainId);
        httpServletRequest.setAttribute("rightList", rightList);
      } 
      RoleBD roleBD = new RoleBD();
      List<Object[]> roleList = roleBD.getSingleRoleInfo(Long.valueOf(logId));
      if (roleList.size() <= 0)
        httpServletRequest.setAttribute("jieguo", "shanchu"); 
      obj = roleList.get(0);
      httpServletRequest.setAttribute("logId", logId);
      httpServletRequest.setAttribute("operate", obj[11].toString());
      httpServletRequest.setAttribute("roleId", obj[10].toString());
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
      List<String> roleRightList = null;
      if ("add".equals(obj[11].toString()) || "update".equals(obj[11].toString())) {
        roleRightList = roleBD.getSingleRoleRightId(Long.valueOf(logId));
      } else {
        roleRightList = roleBD.getSingleRoleRightId(obj[10].toString());
      } 
      for (int i = 0; i < roleRightList.size(); i++)
        sb.append("$" + roleRightList.get(i) + "$,"); 
      httpServletRequest.setAttribute("roleRightString", sb.toString());
    } else {
      if ("yes".equals(action)) {
        String logId = httpServletRequest.getParameter("logId");
        String operate = httpServletRequest.getParameter("operate");
        RoleBD roleBD = new RoleBD();
        AuditLog auditLog = roleBD.auditLog(logId);
        if ("update".equals(operate)) {
          RolePO rolePO = roleBD.getRolePO(logId);
          RoleVO roleVO = new RoleVO();
          roleVO = (RoleVO)FillBean.transformOTO(rolePO, RoleVO.class);
          roleVO.setRoleId(rolePO.getRoleId());
          roleVO.setRoleUserId((rolePO.getRoleUserId() == null) ? "" : rolePO.getRoleUserId());
          roleVO.setRoleUserName((rolePO.getRoleUserName() == null) ? "" : rolePO.getRoleUserName());
          roleVO.setRoleDescription((rolePO.getRoleDescription() == null) ? "" : rolePO.getRoleDescription());
          roleVO.setCreatedOrg(rolePO.getCreatedOrg());
          roleVO.setRoleUseRange((rolePO.getRoleUseRange() == null) ? "" : rolePO.getRoleUseRange());
          roleVO.setRoleUseName((rolePO.getRoleUseName() == null) ? "" : rolePO.getRoleUseName());
          roleVO.setRoleRangeType((rolePO.getRoleRangeType() == null) ? "" : rolePO.getRoleRangeType());
          roleVO.setRoleRange((rolePO.getRoleRange() == null) ? "" : rolePO.getRoleRange());
          roleVO.setRoleRangeName((rolePO.getRoleRangeName() == null) ? "" : rolePO.getRoleRangeName());
          String rightIdString = roleBD.getRightId(logId);
          String empIds = roleBD.getEmpId(logId);
          roleBD.newUpdate((new StringBuilder(String.valueOf(rolePO.getRoleId()))).toString(), rightIdString.split(","), empIds, roleVO);
          Long long_ = auditLog.getSubmitEmpid();
          String userName = auditLog.getSubmitEmpname();
          String userOrgName = StaticParam.getOrgNameByOrgId((String)auditLog.getSubmitOrgid());
          LogBD logBD = new LogBD();
          Date date = new Date();
          logBD.log((String)long_, userName, userOrgName, "system_role", "系统管理", 
              date, date, "2", rolePO.getRoleName(), session.getAttribute("userIP").toString(), domainId);
        } else if ("add".equals(operate)) {
          String rightIdString = roleBD.getRightId(logId);
          RolePO rolePO = roleBD.getRolePO(logId);
          RoleVO roleVO = (RoleVO)FillBean.transformOTO(rolePO, RoleVO.class);
          roleVO.setRoleUserId((rolePO.getRoleUserId() == null) ? "" : rolePO.getRoleUserId());
          roleVO.setRoleUserName((rolePO.getRoleUserName() == null) ? "" : rolePO.getRoleUserName());
          roleVO.setRoleDescription((rolePO.getRoleDescription() == null) ? "" : rolePO.getRoleDescription());
          roleVO.setCreatedOrg(rolePO.getCreatedOrg());
          roleVO.setRoleUseRange((rolePO.getRoleUseRange() == null) ? "" : rolePO.getRoleUseRange());
          roleVO.setRoleUseName((rolePO.getRoleUseName() == null) ? "" : rolePO.getRoleUseName());
          roleVO.setRoleRangeType((rolePO.getRoleRangeType() == null) ? "" : rolePO.getRoleRangeType());
          roleVO.setRoleRange((rolePO.getRoleRange() == null) ? "" : rolePO.getRoleRange());
          roleVO.setRoleRangeName((rolePO.getRoleRangeName() == null) ? "" : rolePO.getRoleRangeName());
          roleBD.save(roleVO, rightIdString.split(","));
          Long long_ = auditLog.getSubmitEmpid();
          String userName = auditLog.getSubmitEmpname();
          String userOrgName = StaticParam.getOrgNameByOrgId((String)auditLog.getSubmitOrgid());
          LogBD logBD = new LogBD();
          Date date = new Date();
          logBD.log((String)long_, userName, userOrgName, "system_role", "系统管理", 
              date, date, "1", roleVO.getRoleName(), 
              session.getAttribute("userIP").toString(), domainId);
        } else if ("delete".equals(operate)) {
          RolePO rolePO = roleBD.getRolePO(logId);
          roleBD.newSingleDelete((new StringBuilder(String.valueOf(rolePO.getRoleId()))).toString());
          Long long_ = auditLog.getSubmitEmpid();
          String userName = auditLog.getSubmitEmpname();
          String userOrgName = StaticParam.getOrgNameByOrgId((String)auditLog.getSubmitOrgid());
          LogBD logBD = new LogBD();
          Date date = new Date();
          logBD.log((String)long_, userName, userOrgName, "system_role", "系统管理", 
              date, date, "3", rolePO.getRoleName(), session.getAttribute("userIP").toString(), domainId);
        } 
        RolePO po = roleBD.getRolePO(logId);
        String op = "";
        if ("update".equals(operate)) {
          op = "修改";
        } else if ("add".equals(operate)) {
          op = "新增";
        } else if ("delete".equals(operate)) {
          op = "删除";
        } 
        String typeString = "审核通过";
        AuditMsRemindEJBBean msRemindBeann = new AuditMsRemindEJBBean();
        msRemindBeann.auditRemind(Long.valueOf(session.getAttribute("userId").toString()), session.getAttribute("orgId").toString(), session.getAttribute("userName").toString(), 
            3, 1, new Date(), "您" + op + "的角色“" + po.getRoleName() + "”" + typeString + "！", "audit", Long.valueOf(logId).longValue(), "", (String)auditLog.getSubmitEmpid());
        (new RoleBD()).auditRole(logId, "yes", session.getAttribute("userId").toString(), session.getAttribute("userName").toString());
        return actionMapping.findForward("close");
      } 
      if ("no".equals(action)) {
        String logId = httpServletRequest.getParameter("logId");
        RoleBD roleBD = new RoleBD();
        AuditLog auditLog = roleBD.auditLog(logId);
        RolePO po = roleBD.getRolePO(logId);
        String op = "";
        if ("update".equals(po.getOperate())) {
          op = "修改";
        } else if ("add".equals(po.getOperate())) {
          op = "新增";
        } else if ("delete".equals(po.getOperate())) {
          op = "删除";
        } 
        String typeString = "未审核通过";
        AuditMsRemindEJBBean msRemindBeann = new AuditMsRemindEJBBean();
        msRemindBeann.auditRemind(Long.valueOf(session.getAttribute("userId").toString()), session.getAttribute("orgId").toString(), session.getAttribute("userName").toString(), 
            3, 1, new Date(), "您" + op + "的角色“" + po.getRoleName() + "”" + typeString + "！", "audit", Long.valueOf(logId).longValue(), "", (String)auditLog.getSubmitEmpid());
        (new RoleBD()).auditRole(logId, "no", session.getAttribute("userId").toString(), session.getAttribute("userName").toString());
        return actionMapping.findForward("close");
      } 
    } 
    return actionMapping.findForward(tag);
  }
  
  private String getManagerRange(HttpSession session) {
    String managerScope = "*" + session.getAttribute("orgId") + "*";
    return managerScope;
  }
}
