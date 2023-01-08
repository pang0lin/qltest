package com.js.system.action.usermanager;

import com.active.e_uc.user.po.TblUser;
import com.active.e_uc.user.po.TblUserApp;
import com.active.e_uc.user.service.TblDepartmentBD;
import com.active.e_uc.user.service.TblUserBD;
import com.js.ldap.MSAD;
import com.js.ldap.MSADNoCert;
import com.js.ldap.OAToAD;
import com.js.oa.hr.officemanager.service.EmployeeBD;
import com.js.oa.oacollect.bean.OACollectEJBBean;
import com.js.oa.security.log.service.LogBD;
import com.js.oa.security.seamoon.service.SeaMoonService;
import com.js.system.bean.usermanager.UserEJBBean;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.organizationmanager.OrganizationBD;
import com.js.system.service.rightmanager.RightBD;
import com.js.system.service.rolemanager.RoleBD;
import com.js.system.service.usermanager.UserBD;
import com.js.system.util.ConvertIdAndName;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.util.FillBean;
import com.js.util.util.ReadActiveXml;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.hibernate.HibernateException;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class UserAction extends Action {
  String rightCode = "";
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    String action = httpServletRequest.getParameter("action");
    String saveType = httpServletRequest.getParameter("saveType");
    UserBD userBD = new UserBD();
    UserForm userForm = (UserForm)actionForm;
    String cnName = httpServletRequest.getParameter("cnName");
    String enName = httpServletRequest.getParameter("enName");
    String orgName = httpServletRequest.getParameter("orgName");
    String isSuper = httpServletRequest.getParameter("isSuper");
    String userAccount = httpServletRequest.getParameter("userAccount");
    this.rightCode = "00*01*02";
    if ("1".equals(session.getAttribute("sysManager").toString()))
      this.rightCode = "00*01*01"; 
    if (userAccount == null || "null".equals(userAccount))
      userAccount = ""; 
    String search = "cnName=";
    if (cnName != null && !"".equals(cnName)) {
      search = String.valueOf(search) + URLEncoder.encode(cnName) + "&enName=" + enName + "&orgName=";
    } else {
      search = String.valueOf(search) + cnName + "&enName=" + enName + "&orgName=";
    } 
    if (orgName != null && !"".equals(orgName)) {
      search = String.valueOf(search) + URLEncoder.encode(orgName) + "&isSuper=" + isSuper + "&userAccount=";
    } else {
      search = String.valueOf(search) + orgName + "&isSuper=" + isSuper + "&userAccount=";
    } 
    if (userAccount != null && !"".equals(userAccount)) {
      search = String.valueOf(search) + URLEncoder.encode(userAccount);
    } else {
      search = String.valueOf(search) + userAccount;
    } 
    TblUserBD tblUserBD = new TblUserBD();
    TblUser tblUser = new TblUser();
    if ("add".equals(action)) {
      String orgIds = userForm.getOrgIds();
      String rightIds = userForm.getRightIds();
      String rightScopeTypes = userForm.getRightScopeTypes();
      String rightScopeScopes = userForm.getRightScopeScopes();
      String rightScopeDsp = userForm.getRightScopeDsp();
      String[] orgId = new String[0];
      String[] rightId = new String[0];
      String[] rightScopeType = new String[0];
      String[] rightScopeScope = new String[0];
      String[] roleId = userForm.getUserRoleId().split(",");
      orgId = new String[1];
      orgId[0] = orgIds;
      if (rightIds.indexOf(",") != -1)
        rightId = rightIds.split(","); 
      if (rightScopeTypes.indexOf(",") != -1)
        rightScopeType = rightScopeTypes.split(","); 
      if (rightScopeScopes.indexOf(",") != -1)
        rightScopeScope = rightScopeScopes.split(","); 
      EmployeeVO employeeVO = (EmployeeVO)FillBean.transformOneToOne(userForm, EmployeeVO.class);
      employeeVO.setCreatedOrg(Long.parseLong(session.getAttribute("orgId").toString()));
      byte userIsActive = 1;
      byte userIsFormalUser = 1;
      Date userSuperBegin = new Date(httpServletRequest.getParameter("userSuperBegin"));
      Date userSuperEnd = new Date(httpServletRequest.getParameter("userSuperEnd"));
      employeeVO.setUserIsActive(userIsActive);
      employeeVO.setUserIsFormalUser(Integer.valueOf("1"));
      if (employeeVO.getUserIsSuper() == 1) {
        employeeVO.setUserSuperBegin(userSuperBegin);
        employeeVO.setUserSuperEnd(userSuperEnd);
      } else {
        employeeVO.setUserSuperBegin(new Date());
        employeeVO.setUserSuperEnd(new Date());
      } 
      OrganizationBD organizationBD = new OrganizationBD();
      if (SystemCommon.getAudit() == 0)
        if ("iactive".equals(ReadActiveXml.getReadActive().getUse())) {
          tblUser.setUserName(employeeVO.getUserAccounts());
          tblUser.setPassWord(userForm.getUserPassword());
          tblUser.setType(4);
          tblUser.setIsPrimaryAdmin((byte)0);
          tblUser.setOrgId(1);
          tblUser.setAid(1);
          tblUser.setIsValid((byte)1);
          SimpleDateFormat si = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          tblUser.setStartValidDate(si.format(new Date()));
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(new Date());
          calendar.add(1, 5);
          tblUser.setEndValidDate(si.format(calendar.getTime()));
          tblUser.setNickName(employeeVO.getEmpName());
          tblUser.setSex(employeeVO.getEmpSex());
          tblUser.setMailaddr("");
          tblUser.setTelephone("");
          tblUser.setMphone("");
          tblUser.setProtocolRcv((byte)0);
          tblUser.setProtocolSend((byte)0);
          tblUser.setVerifyHid((byte)0);
          tblUser.setTruename(employeeVO.getEmpName());
          tblUser.setOccupy(0);
          tblUser.setInterest(Double.valueOf(0.0D));
          tblUser.setSafeinfo(0);
          tblUser.setShengxiao((byte)0);
          tblUser.setBloodtype((byte)0);
          tblUser.setStar((byte)0);
          tblUser.setImageindex((short)1);
          TblDepartmentBD tblDepartmentBD = new TblDepartmentBD();
          String sa = organizationBD.findOrgSerial(Integer.parseInt(userForm.getOrgIds()));
          int did = tblDepartmentBD.findID(sa);
          tblUser.setDeptId(did);
          tblUser.setGrade(0);
          tblUser.setAccountId(0);
          tblUser.setContinueService((byte)0);
          tblUser.setRole(0);
          tblUser.setDicOrder(0);
          tblUser.setTrolServerId(Integer.valueOf(0));
          tblUser.setTrolState(0);
          tblUser.setTrolIsOnline((byte)0);
          tblUser.setUserid(31914);
          try {
            tblUserBD.addTblUser(tblUser);
          } catch (HibernateException e) {
            e.printStackTrace();
          } 
        }  
      employeeVO.setDomainId(session.getAttribute("domainId").toString());
      employeeVO.setMailboxSize(httpServletRequest.getParameter("mailboxSize"));
      employeeVO.setNetDiskSize(httpServletRequest.getParameter("netDiskSize"));
      employeeVO.setWorkAddress(Long.valueOf(httpServletRequest.getParameter("workAddress")));
      employeeVO.setDeptLeader(httpServletRequest.getParameter("deptLeader"));
      String empPosition = organizationBD.findStationName(httpServletRequest.getParameter("empPositionId"));
      employeeVO.setEmpPosition(empPosition);
      int result = 0;
      if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
        if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit()) {
          String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
              session.getAttribute("orgId").toString(), "6", "autoAudit" };
          userBD.add(employeeVO, orgId, rightId, rightScopeType, rightScopeScope, rightScopeDsp, roleId, log);
        } 
        result = userBD.add(employeeVO, orgId, rightId, rightScopeType, rightScopeScope, rightScopeDsp, roleId);
        if (employeeVO.getDeptLeader().equals("1"))
          organizationBD.updateRelationDept(userForm.getOrgIds(), String.valueOf(employeeVO.getEmpId()), employeeVO.getEmpName(), "1"); 
        String userId = session.getAttribute("userId").toString();
        String userName = session.getAttribute("userName").toString();
        String userOrgName = session.getAttribute("orgName").toString();
        String domainId = session.getAttribute("domainId").toString();
        LogBD logBD = new LogBD();
        Date date = new Date();
        logBD.log(userId, userName, userOrgName, "system_user", "系统管理", date, date, "1", employeeVO.getEmpName(), session.getAttribute("userIP").toString(), domainId);
      } else {
        String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
            session.getAttribute("orgId").toString(), "6" };
        employeeVO.setEmpId(0L);
        result = userBD.add(employeeVO, orgId, rightId, rightScopeType, rightScopeScope, rightScopeDsp, roleId, log);
      } 
      if (result != 0) {
        EmployeeBD employeeBD = new EmployeeBD();
        httpServletRequest.setAttribute("listDuty", employeeBD.listDuty(session.getAttribute("domainId").toString(), 
              session.getAttribute("corpId").toString()));
        httpServletRequest.setAttribute("listStation", employeeBD.listStation(session.getAttribute("domainId").toString(), 
              session.getAttribute("corpId").toString()));
        httpServletRequest.setAttribute("opResult", new Integer(result));
        httpServletRequest.setAttribute("empName", employeeVO.getEmpName());
        httpServletRequest.setAttribute("empEnglishName", employeeVO.getEmpEnglishName());
        httpServletRequest.setAttribute("empNumber", employeeVO.getEmpNumber());
        httpServletRequest.setAttribute("userAccounts", employeeVO.getUserAccounts());
        httpServletRequest.setAttribute("empSex", new Byte(employeeVO.getEmpSex()));
        httpServletRequest.setAttribute("userIsSuper", new Byte(employeeVO.getUserIsSuper()));
        httpServletRequest.setAttribute("userSuperBegin", employeeVO.getUserSuperBegin());
        httpServletRequest.setAttribute("userSuperEnd", employeeVO.getUserSuperEnd());
        httpServletRequest.setAttribute("orgId", orgIds);
        httpServletRequest.setAttribute("orgName", httpServletRequest.getParameter("orgNames"));
        httpServletRequest.setAttribute("empLeaderId", employeeVO.getEmpLeaderId());
        httpServletRequest.setAttribute("empLeaderName", employeeVO.getEmpLeaderName());
        httpServletRequest.setAttribute("browseRange", employeeVO.getBrowseRange());
        httpServletRequest.setAttribute("browseRangeName", employeeVO.getBrowseRangeName());
        httpServletRequest.setAttribute("userOrderCode", employeeVO.getUserOrderCode());
        httpServletRequest.setAttribute("empDuty", employeeVO.getEmpDuty());
        httpServletRequest.setAttribute("userSimpleName", employeeVO.getUserSimpleName());
        httpServletRequest.setAttribute("keyValidate", employeeVO.getKeyValidate());
        httpServletRequest.setAttribute("keySerial", employeeVO.getKeySerial());
        httpServletRequest.setAttribute("mailboxSize", employeeVO.getMailboxSize());
        httpServletRequest.setAttribute("netDiskSize", employeeVO.getNetDiskSize());
        httpServletRequest.setAttribute("signatureImgName", employeeVO.getSignatureImgName());
        httpServletRequest.setAttribute("signatureImgSaveName", employeeVO.getSignatureImgSaveName());
        httpServletRequest.setAttribute("sidelineOrg", employeeVO.getSidelineOrg());
        httpServletRequest.setAttribute("sidelineOrgName", employeeVO.getSidelineOrgName());
        httpServletRequest.setAttribute("deptLeader", employeeVO.getDeptLeader());
        httpServletRequest.setAttribute("empPosition", employeeVO.getEmpPosition());
        httpServletRequest.setAttribute("empPositionId", employeeVO.getEmpPositionId());
        httpServletRequest.setAttribute("empPositionOtherId", employeeVO.getEmpPositionOtherId());
        httpServletRequest.setAttribute("grantRange", employeeVO.getGrantRange());
        httpServletRequest.setAttribute("grantRangeName", employeeVO.getGrantRangeName());
        RoleBD roleBD = new RoleBD();
        httpServletRequest.setAttribute("roles", roleBD.getOwnerRoles(session.getAttribute("userId").toString(), 
              session.getAttribute("orgId").toString(), 
              session.getAttribute("browseRange").toString(), 
              session.getAttribute("sysManager").toString(), 
              session.getAttribute("domainId").toString()));
        httpServletRequest.setAttribute("userRightScope", new ArrayList());
        return actionMapping.findForward("failure");
      } 
      if ("saveAndContinue".equals(saveType))
        return actionMapping.findForward("saveAndContinue"); 
      if ("saveAndExit".equals(saveType)) {
        String tmp = "";
        if (httpServletRequest.getParameter("cnName") != null)
          tmp = "&cnName=" + httpServletRequest.getParameter("cnName") + "&enName=" + httpServletRequest.getParameter("enName") + "&isSuper=" + httpServletRequest.getParameter("isSuper") + "&userAccount=" + httpServletRequest.getParameter("userAccount"); 
        httpServletRequest.setAttribute("parentUrl", "/jsoa/ListUserAction.do?" + search + "&status=active&pager.offset=" + httpServletRequest.getParameter("pager.offset"));
        return actionMapping.findForward("saveAndExit");
      } 
    } else {
      if ("update".equals(action)) {
        EmployeeVO employeeVO = (EmployeeVO)FillBean.transformOneToOne(userForm, EmployeeVO.class);
        employeeVO.setCreatedOrg(Long.parseLong(session.getAttribute("orgId").toString()));
        String empId = userForm.getUpdatedId();
        employeeVO.setEmpId(Long.parseLong(empId));
        byte userIsActive = 1;
        byte userIsFormalUser = 1;
        Date userSuperBegin = new Date(httpServletRequest.getParameter("userSuperBegin"));
        Date userSuperEnd = new Date(httpServletRequest.getParameter("userSuperEnd"));
        employeeVO.setUserIsActive(userIsActive);
        employeeVO.setUserIsFormalUser(Integer.valueOf("1"));
        if (employeeVO.getUserIsSuper() == 1) {
          employeeVO.setUserSuperBegin(userSuperBegin);
          employeeVO.setUserSuperEnd(userSuperEnd);
        } 
        employeeVO.setMailboxSize(httpServletRequest.getParameter("mailboxSize"));
        employeeVO.setNetDiskSize(httpServletRequest.getParameter("netDiskSize"));
        employeeVO.setWorkAddress(Long.valueOf(httpServletRequest.getParameter("workAddress")));
        String orgIds = userForm.getOrgIds();
        String rightIds = userForm.getRightIds();
        String rightScopeTypes = userForm.getRightScopeTypes();
        String rightScopeScopes = userForm.getRightScopeScopes();
        String rightScopeDsp = userForm.getRightScopeDsp();
        String[] roleId = userForm.getUserRoleId().split(",");
        String[] orgId = new String[0];
        String[] rightId = new String[0];
        String[] rightScopeType = new String[0];
        String[] rightScopeScope = new String[0];
        orgId = new String[1];
        orgId[0] = orgIds;
        if (rightIds.indexOf(",") != -1)
          rightId = rightIds.split(","); 
        if (rightScopeTypes.indexOf(",") != -1)
          rightScopeType = rightScopeTypes.split(","); 
        if (rightScopeScopes.indexOf(",") != -1)
          rightScopeScope = rightScopeScopes.split(","); 
        OrganizationBD organizationBD = new OrganizationBD();
        if (SystemCommon.getAudit() == 0 && 
          "iactive".equals(ReadActiveXml.getReadActive().getUse()))
          try {
            String tblUserName = userBD.getUserName(Long.valueOf(employeeVO.getEmpId()));
            tblUser = tblUserBD.findTblUser(tblUserName);
            if (userForm.getUserPassword() != null && !"".equals(userForm.getUserPassword()))
              tblUser.setPassWord(userForm.getUserPassword()); 
            tblUser.setUserName(userForm.getUserAccounts());
            tblUser.setTruename(userForm.getEmpName());
            tblUser.setNickName(userForm.getEmpName());
            tblUser.setSex(userForm.getEmpSex());
            TblDepartmentBD tblDepartmentBD = new TblDepartmentBD();
            String sa = organizationBD.findOrgSerial(Integer.parseInt(userForm.getOrgIds()));
            int did = tblDepartmentBD.findID(sa);
            tblUser.setDeptId(did);
            tblUserBD.updateTblUser(tblUser);
          } catch (Exception e) {
            e.printStackTrace();
          }  
        employeeVO.setDeptLeader(httpServletRequest.getParameter("deptLeader"));
        employeeVO.setOpinionRemind(httpServletRequest.getParameter("opinionRemind"));
        String empPosition = organizationBD.findStationName(httpServletRequest.getParameter("empPositionId"));
        employeeVO.setEmpPosition(empPosition);
        int result = 0;
        if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
          if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit()) {
            String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
                session.getAttribute("orgId").toString(), "6", "autoAudit" };
            result = userBD.update(employeeVO, orgId, rightId, rightScopeType, rightScopeScope, rightScopeDsp, roleId, log);
          } 
          result = userBD.update(employeeVO, orgId, rightId, rightScopeType, rightScopeScope, rightScopeDsp, roleId);
          organizationBD.updateRelationDept(userForm.getOrgIds(), String.valueOf(employeeVO.getEmpId()), employeeVO.getEmpName(), httpServletRequest.getParameter("deptLeader"));
          String userId = session.getAttribute("userId").toString();
          String userName = session.getAttribute("userName").toString();
          String userOrgName = session.getAttribute("orgName").toString();
          String domainId = session.getAttribute("domainId").toString();
          LogBD logBD = new LogBD();
          Date date = new Date();
          logBD.log(userId, userName, userOrgName, "system_user", "系统管理", 
              date, date, "2", employeeVO.getEmpName(), session.getAttribute("userIP").toString(), domainId);
        } 
        if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0) {
          String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
              session.getAttribute("orgId").toString(), "6" };
          result = userBD.update(employeeVO, orgId, rightId, rightScopeType, rightScopeScope, rightScopeDsp, roleId, log);
        } 
        if (result != 0) {
          EmployeeBD employeeBD = new EmployeeBD();
          httpServletRequest.setAttribute("listDuty", employeeBD.listDuty(session.getAttribute("domainId").toString(), 
                session.getAttribute("corpId").toString()));
          httpServletRequest.setAttribute("listStation", employeeBD.listStation(session.getAttribute("domainId").toString(), 
                session.getAttribute("corpId").toString()));
          httpServletRequest.setAttribute("opResult", new Integer(result));
          httpServletRequest.setAttribute("empId", empId);
          httpServletRequest.setAttribute("empName", employeeVO.getEmpName());
          httpServletRequest.setAttribute("empEnglishName", employeeVO.getEmpEnglishName());
          httpServletRequest.setAttribute("empNumber", employeeVO.getEmpNumber());
          httpServletRequest.setAttribute("userAccounts", employeeVO.getUserAccounts());
          httpServletRequest.setAttribute("empSex", new Byte(employeeVO.getEmpSex()));
          httpServletRequest.setAttribute("userIsSuper", new Byte(employeeVO.getUserIsSuper()));
          httpServletRequest.setAttribute("userSuperBegin", employeeVO.getUserSuperBegin());
          httpServletRequest.setAttribute("userSuperEnd", employeeVO.getUserSuperEnd());
          httpServletRequest.setAttribute("orgId", orgIds);
          httpServletRequest.setAttribute("orgName", httpServletRequest.getParameter("orgNames"));
          httpServletRequest.setAttribute("empLeaderId", employeeVO.getEmpLeaderId());
          httpServletRequest.setAttribute("empLeaderName", employeeVO.getEmpLeaderName());
          httpServletRequest.setAttribute("browseRange", employeeVO.getBrowseRange());
          httpServletRequest.setAttribute("browseRangeName", employeeVO.getBrowseRangeName());
          httpServletRequest.setAttribute("userOrderCode", employeeVO.getUserOrderCode());
          httpServletRequest.setAttribute("empDuty", employeeVO.getEmpDuty());
          httpServletRequest.setAttribute("userSimpleName", employeeVO.getUserSimpleName());
          httpServletRequest.setAttribute("keyValidate", employeeVO.getKeyValidate());
          httpServletRequest.setAttribute("keySerial", employeeVO.getKeySerial());
          httpServletRequest.setAttribute("opinionRemind", employeeVO.getOpinionRemind());
          httpServletRequest.setAttribute("mailboxSize", employeeVO.getMailboxSize());
          httpServletRequest.setAttribute("netDiskSize", employeeVO.getNetDiskSize());
          httpServletRequest.setAttribute("signatureImgName", employeeVO.getSignatureImgName());
          httpServletRequest.setAttribute("signatureImgSaveName", employeeVO.getSignatureImgSaveName());
          httpServletRequest.setAttribute("sidelineOrg", employeeVO.getSidelineOrg());
          httpServletRequest.setAttribute("sidelineOrgName", employeeVO.getSidelineOrgName());
          httpServletRequest.setAttribute("deptLeader", employeeVO.getDeptLeader());
          httpServletRequest.setAttribute("empPosition", employeeVO.getEmpPosition());
          httpServletRequest.setAttribute("grantRange", employeeVO.getGrantRange());
          httpServletRequest.setAttribute("grantRangeName", employeeVO.getGrantRangeName());
          RoleBD roleBD = new RoleBD();
          httpServletRequest.setAttribute("roles", roleBD.getOwnerRoles(session.getAttribute("userId").toString(), 
                session.getAttribute("orgId").toString(), 
                session.getAttribute("browseRange").toString(), 
                session.getAttribute("sysManager").toString(), 
                session.getAttribute("domainId").toString()));
          List oldRoles = roleBD.getUserRole(empId);
          StringBuffer sb = new StringBuffer();
          for (int i = 0; i < oldRoles.size(); i++) {
            sb.append("$");
            sb.append(oldRoles.get(i));
            sb.append("$,");
          } 
          httpServletRequest.setAttribute("oldRoles", sb.toString());
          httpServletRequest.setAttribute("userRightScope", (new RightBD()).getUserRightScope(empId));
          return actionMapping.findForward("failure");
        } 
        httpServletRequest.setAttribute("parentUrl", "/jsoa/ListUserAction.do?" + search + "&status=" + httpServletRequest.getParameter("status") + "&pager.offset=" + httpServletRequest.getParameter("pager.offset"));
        return actionMapping.findForward("saveAndExit");
      } 
      if ("delete".equals(action)) {
        String status = httpServletRequest.getParameter("status");
        String id = httpServletRequest.getParameter("id");
        if (id != null && !"".equals(id)) {
          if ("2".equals(SystemCommon.getUKey())) {
            SeaMoonService smService = new SeaMoonService();
            smService.deleteSeaMoonByUserId(id);
          } 
          String result = "";
          String[] ids = id.split(",");
          if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
            if ("iactive".equals(ReadActiveXml.getReadActive().getUse())) {
              String tblUserName = "";
              for (int i = 0; i < ids.length; i++) {
                try {
                  tblUserName = userBD.getUserName(Long.valueOf(Long.parseLong(ids[i])));
                  tblUser = tblUserBD.findTblUser(tblUserName);
                  tblUserBD.delTblUser(tblUser);
                } catch (Exception e) {
                  e.printStackTrace();
                } 
              } 
            } 
            if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit()) {
              String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
                  session.getAttribute("orgId").toString(), "6", "autoAudit" };
              result = userBD.delete(ids, log);
            } 
            result = userBD.delete(ids);
            String userId = session.getAttribute("userId").toString();
            String userName = session.getAttribute("userName").toString();
            String userOrgName = session.getAttribute("orgName").toString();
            String domainId = session.getAttribute("domainId").toString();
            LogBD logBD = new LogBD();
            Date date = new Date();
            logBD.log(userId, userName, userOrgName, "system_user", "系统管理", 
                date, date, "3", result, session.getAttribute("userIP").toString(), domainId);
          } 
          if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0) {
            String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
                session.getAttribute("orgId").toString(), "6" };
            result = userBD.delete(ids, log);
          } 
          if (!"".equals(result) && "active".equals(status))
            return new ActionForward("/ListUserAction.do?status=active&" + search + "&pager.offset=" + httpServletRequest.getParameter("pager.offset")); 
          if (!"".equals(result) && "disabled".equals(status))
            return new ActionForward("/ListUserAction.do?status=disabled&" + search + "&pager.offset=" + httpServletRequest.getParameter("pager.offset")); 
        } else {
          return actionMapping.findForward("failure");
        } 
      } else {
        if ("disable".equals(action)) {
          String id = httpServletRequest.getParameter("id");
          String result = "";
          if (id != null && !"".equals(id)) {
            String[] ids = id.split(",");
            if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
              if ("iactive".equals(ReadActiveXml.getReadActive().getUse())) {
                String tblUserName = "";
                TblUserApp tblUserApp = new TblUserApp();
                List<TblUserApp> userAppList = new ArrayList();
                for (int i = 0; i < ids.length; i++) {
                  try {
                    tblUserName = userBD.getUserName(Long.valueOf(Long.parseLong(ids[i])));
                    tblUser = tblUserBD.findTblUser(tblUserName);
                    userAppList = tblUserBD.findTblUserApp(tblUser.getId());
                    for (int k = 0; k < userAppList.size(); k++) {
                      tblUserApp = userAppList.get(k);
                      tblUserBD.delTblUserApp(tblUserApp);
                    } 
                  } catch (Exception e) {
                    e.printStackTrace();
                  } 
                } 
              } 
              if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit()) {
                String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
                    session.getAttribute("orgId").toString(), "6", "autoAudit" };
                result = userBD.disable(ids, log);
              } 
              result = userBD.disable(ids);
              String userId = session.getAttribute("userId").toString();
              String userName = session.getAttribute("userName").toString();
              String userOrgName = session.getAttribute("orgName").toString();
              String domainId = session.getAttribute("domainId").toString();
              LogBD logBD = new LogBD();
              Date date = new Date();
              logBD.log(userId, userName, userOrgName, "system_user", "系统管理", 
                  date, date, "5", result, session.getAttribute("userIP").toString(), domainId);
            } 
            if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0) {
              String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
                  session.getAttribute("orgId").toString(), "6" };
              result = userBD.disable(ids, log);
            } 
            httpServletRequest.setAttribute("Jinyongresult", result);
            if (!"".equals(result))
              return new ActionForward("/ListUserAction.do?status=active&" + search + "&pager.offset=" + httpServletRequest.getParameter("pager.offset")); 
            return new ActionForward("/ListUserAction.do?status=active&" + search + "&pager.offset=" + httpServletRequest.getParameter("pager.offset"));
          } 
          return actionMapping.findForward("failure");
        } 
        if ("recover".equals(action)) {
          String id = httpServletRequest.getParameter("id");
          String result = "";
          if (id != null && !"".equals(id)) {
            String[] ids = id.split(",");
            if (1 != SystemCommon.getAudit() || (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit())) {
              if ("iactive".equals(ReadActiveXml.getReadActive().getUse())) {
                String tblUserName = "";
                for (int i = 0; i < ids.length; i++) {
                  try {
                    tblUserName = userBD.getUserName(Long.valueOf(Long.parseLong(ids[i])));
                    tblUser = tblUserBD.findTblUser(tblUserName);
                    tblUserBD.addTblUserApp(tblUser);
                  } catch (Exception e) {
                    e.printStackTrace();
                  } 
                } 
              } 
              if (1 == SystemCommon.getAudit() && 1 == SystemCommon.getAutoAudit()) {
                String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
                    session.getAttribute("orgId").toString(), "6", "autoAudit" };
                result = userBD.recover(ids, log);
              } 
              result = userBD.recover(ids);
              String userId = session.getAttribute("userId").toString();
              String userName = session.getAttribute("userName").toString();
              String userOrgName = session.getAttribute("orgName").toString();
              String domainId = session.getAttribute("domainId").toString();
              LogBD logBD = new LogBD();
              Date date = new Date();
              logBD.log(userId, userName, userOrgName, "system_user", "系统管理", 
                  date, date, "6", result, session.getAttribute("userIP").toString(), domainId);
            } 
            if (1 == SystemCommon.getAudit() && SystemCommon.getAutoAudit() == 0) {
              String[] log = { session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), 
                  session.getAttribute("orgId").toString(), "6" };
              result = userBD.recover(ids, log);
            } 
            if (!"".equals(result))
              return new ActionForward("/ListUserAction.do?status=disabled&" + search + "&pager.offset=" + httpServletRequest.getParameter("pager.offset")); 
          } else {
            return actionMapping.findForward("failure");
          } 
        } else if ("open".equals(action)) {
          String id = httpServletRequest.getParameter("id");
          if (id != null && !"".equals(id)) {
            String[] ids = id.split(",");
            boolean result = userBD.open(ids);
            if (result)
              return new ActionForward("/ListUserAction.do?status=applied&" + search + "&pager.offset=" + httpServletRequest.getParameter("pager.offset")); 
          } else {
            return actionMapping.findForward("failure");
          } 
        } else {
          if ("getmanagername".equals(action)) {
            String userId = null;
            String ids = httpServletRequest.getParameter("managerId").toString();
            ConvertIdAndName convert = new ConvertIdAndName();
            userId = convert.splitId(ids).getEmpIdArray();
            String userName = userBD.getUserNameById(userId);
            httpServletRequest.setAttribute("managername", userName);
            return actionMapping.findForward("getmanagername");
          } 
          if ("deleteAll".equals(action)) {
            String status = httpServletRequest.getParameter("status");
            boolean result = userBD.delete(status);
            if (result && "active".equals(status))
              return actionMapping.findForward("toActiveUserList"); 
            if (result && "disabled".equals(status))
              return actionMapping.findForward("toDisabledUserList"); 
          } else if (!"currentUser".equals(action)) {
            if ("updateAD".equals(action)) {
              String to = SystemCommon.getTo();
              try {
                if ("AD".equalsIgnoreCase(to)) {
                  OAToAD msad = new OAToAD();
                  msad.insertGroup();
                  msad.createUsers();
                } else {
                  MSAD ms = new MSAD();
                  if (MSAD.getUseCert() == 0) {
                    MSADNoCert adNoCert = new MSADNoCert();
                    adNoCert.queryItem("");
                  } else {
                    ms.queryItem("");
                  } 
                } 
              } catch (Exception e) {
                e.printStackTrace();
              } 
              try {
                PrintWriter out = httpServletResponse.getWriter();
                out.println("success");
              } catch (IOException e) {
                e.printStackTrace();
              } 
              return null;
            } 
            if ("forEmpTurnover".equals(action)) {
              httpServletRequest.setAttribute("managerScope", getManagerRange(session));
              List list = null;
              httpServletRequest.setAttribute("workList", list);
              return actionMapping.findForward(action);
            } 
            if ("forEmpTurnoverPage".equals(action)) {
              String turnOverEmpsName = httpServletRequest.getParameter("turnOverEmpsName");
              String personId = httpServletRequest.getParameter("personId");
              String turnOverEmpsNameOne = httpServletRequest.getParameter("turnOverEmpsNameOne");
              String personIdOne = httpServletRequest.getParameter("personIdOne");
              String flag = httpServletRequest.getParameter("b");
              long empId = 0L;
              if (personId != null && !"".equals(personId))
                empId = Long.parseLong(personId); 
              UserEJBBean userEJBBean = new UserEJBBean();
              List list = null;
              try {
                list = userEJBBean.getUserWorkClass(Long.valueOf(empId));
              } catch (Exception e) {
                e.printStackTrace();
              } 
              httpServletRequest.setAttribute("flag", flag);
              httpServletRequest.setAttribute("turnOverEmpsName", turnOverEmpsName);
              httpServletRequest.setAttribute("personId", personId);
              httpServletRequest.setAttribute("turnOverEmpsNameOne", turnOverEmpsNameOne);
              httpServletRequest.setAttribute("personIdOne", personIdOne);
              httpServletRequest.setAttribute("managerScope", getManagerRange(session));
              httpServletRequest.setAttribute("workList", list);
              return actionMapping.findForward(action);
            } 
            if ("forEmpTurnoverUpdate".equals(action)) {
              httpServletRequest.setAttribute("managerScope", getManagerRange(session));
              String personId = httpServletRequest.getParameter("personId");
              String turnOverEmpsName = httpServletRequest.getParameter("turnOverEmpsName");
              String turnOverEmpsOne = httpServletRequest.getParameter("turnOverEmpsOne");
              String turnOverEmpsOneName = httpServletRequest.getParameter("turnOverEmpsOneName");
              String workId = httpServletRequest.getParameter("workId");
              UserEJBBean userEJBBean = new UserEJBBean();
              StringBuffer sql = new StringBuffer();
              StringBuffer sqlNode = new StringBuffer();
              sql.append("  update jsf_work set  WF_CUREMPLOYEE_ID=" + turnOverEmpsOne + "  where WORKSTATUS=0 and  WF_CUREMPLOYEE_ID=" + personId + " and WORKLISTCONTROL=1 and WORKDELETE=0  ");
              sqlNode.append(" update co_nodemember  set emp_id=" + turnOverEmpsOne + ",emp_name='" + turnOverEmpsOneName + "' where emp_id=" + personId + " and status=10  ");
              String sysString = " update sys_messages set message_toUserId=" + turnOverEmpsOne + "  where message_toUserId=" + personId + " and message_status=1 ";
              boolean b = false;
              boolean coordination = false;
              try {
                b = userEJBBean.updateEmpTurnover(sql.toString());
                if (b) {
                  coordination = userEJBBean.updateEmpTurnoverCoordination(sqlNode.toString());
                  userEJBBean.updateSysmessage(sysString);
                } 
              } catch (Exception e) {
                e.printStackTrace();
              } 
              ActionForward forward = new ActionForward();
              forward.setPath(
                  "/UserAction.do?action=forEmpTurnoverPage&b=" + b + "&personId=" + 
                  personId);
              return forward;
            } 
            if ("forAdjustOrg".equals(action)) {
              EmployeeBD employeeBD = new EmployeeBD();
              httpServletRequest.setAttribute("managerScope", getManagerRange(session));
              httpServletRequest.setAttribute("listDuty", employeeBD.listDuty(session.getAttribute("domainId").toString(), 
                    session.getAttribute("corpId").toString()));
              httpServletRequest.setAttribute("listStation", employeeBD.listStation(session.getAttribute("domainId").toString(), 
                    session.getAttribute("corpId").toString()));
              return actionMapping.findForward(action);
            } 
            if ("adjustorg".equals(action)) {
              try {
                String adjustEmps = httpServletRequest.getParameter("adjustEmps");
                String adjustOrg = httpServletRequest.getParameter("adjustOrg");
                String stationId = httpServletRequest.getParameter("stationId");
                String dutyId = httpServletRequest.getParameter("dutyId");
                adjustEmps = adjustEmps.replace("$$", ",").replace("$", "");
                OACollectEJBBean bean = new OACollectEJBBean();
                String sql = "";
                String tempSql = "";
                String flag = "";
                if (!"".equals(adjustOrg)) {
                  sql = "UPDATE org_organization_user t SET t.org_id=" + adjustOrg + " WHERE t.emp_id IN (" + adjustEmps + ")";
                  tempSql = "SELECT po.orgManagerEmpId, po.orgManagerEmpName FROM com.js.system.vo.organizationmanager.OrganizationVO po WHERE po.orgId = " + adjustOrg;
                  List<Object[]> list = bean.getListByYourSQL(tempSql);
                  Object[] orgManager = list.get(0);
                  String orgManagerId = (orgManager[0] == null) ? "" : orgManager[0].toString();
                  String orgManagerName = (orgManager[1] == null) ? "" : orgManager[1].toString();
                  tempSql = "UPDATE org_employee SET EMPLEADERID='" + orgManagerId + "', EMPLEADERNAME = '" + orgManagerName + "' WHERE emp_id IN (" + adjustEmps + ")";
                  if (bean.updateByYourYuanShengSql(sql) && bean.updateByYourYuanShengSql(tempSql))
                    flag = String.valueOf(flag) + "，1"; 
                } 
                if (!"".equals(stationId)) {
                  sql = "UPDATE org_employee t SET t.empPositionId=" + stationId + " WHERE t.emp_id IN (" + adjustEmps + ")";
                  if (bean.updateByYourYuanShengSql(sql))
                    flag = String.valueOf(flag) + "，2"; 
                } 
                if (!"".equals(dutyId)) {
                  sql = "UPDATE org_employee t SET t.empDuty='" + dutyId + "' WHERE t.emp_id IN (" + adjustEmps + ")";
                  if (bean.updateByYourYuanShengSql(sql))
                    flag = String.valueOf(flag) + "，3"; 
                } 
                EmployeeBD employeeBD = new EmployeeBD();
                httpServletRequest.setAttribute("adjustFlag", (flag.length() == 0) ? "" : flag.substring(1));
                httpServletRequest.setAttribute("listDuty", employeeBD.listDuty(session.getAttribute("domainId").toString(), 
                      session.getAttribute("corpId").toString()));
                httpServletRequest.setAttribute("listStation", employeeBD.listStation(session.getAttribute("domainId").toString(), 
                      session.getAttribute("corpId").toString()));
              } catch (Exception e) {
                e.printStackTrace();
              } 
              return actionMapping.findForward("forAdjustOrg");
            } 
          } 
        } 
      } 
    } 
    return actionMapping.findForward("success");
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
