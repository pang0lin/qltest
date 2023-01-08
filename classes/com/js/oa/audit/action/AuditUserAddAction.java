package com.js.oa.audit.action;

import com.active.e_uc.user.po.TblJilu;
import com.active.e_uc.user.po.TblUser;
import com.active.e_uc.user.po.TblUserApp;
import com.active.e_uc.user.service.TblDepartmentBD;
import com.active.e_uc.user.service.TblUserBD;
import com.js.oa.audit.bean.AuditMsRemindEJBBean;
import com.js.oa.audit.po.AuditEmployeePO;
import com.js.oa.audit.po.AuditLog;
import com.js.oa.hr.officemanager.service.EmployeeBD;
import com.js.oa.hr.personnelmanager.service.NewEmployeeBD;
import com.js.oa.security.log.service.LogBD;
import com.js.system.service.organizationmanager.OrganizationBD;
import com.js.system.service.rightmanager.RightBD;
import com.js.system.service.rolemanager.RoleBD;
import com.js.system.service.usermanager.UserBD;
import com.js.system.util.StaticParam;
import com.js.system.vo.organizationmanager.DomainVO;
import com.js.system.vo.usermanager.EmployeeOtherInfoVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.util.FillBean;
import com.js.util.util.ReadActiveXml;
import java.io.PrintWriter;
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

public class AuditUserAddAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    DomainVO domainVO = (new OrganizationBD()).loadDomain(domainId);
    Integer userNum = (new UserBD()).getUserNum();
    userNum = Integer.valueOf("-1");
    if (userNum.intValue() >= domainVO.getUserNum().intValue())
      return actionMapping.findForward("overUserNum"); 
    String action = httpServletRequest.getParameter("action");
    String userId = httpServletRequest.getParameter("userid");
    String managerId = session.getAttribute("userId").toString();
    String browseRange = (String)session.getAttribute("browseRange");
    String sysManager = session.getAttribute("sysManager").toString();
    String managerOrgId = session.getAttribute("orgId").toString();
    String comeFlag = (httpServletRequest.getParameter("comeFlag") == null) ? "" : httpServletRequest.getParameter("comeFlag");
    httpServletRequest.setAttribute("comeFlag", comeFlag);
    EmployeeBD employeeBD = new EmployeeBD();
    RoleBD roleBD = new RoleBD();
    List roles = null;
    if ("update".equals(action)) {
      roles = roleBD.getOwnerRoles(managerId, managerOrgId, browseRange, sysManager, session.getAttribute("domainId").toString());
    } else {
      roles = roleBD.getOwnerRoles(managerId, managerOrgId, browseRange, sysManager, session.getAttribute("domainId").toString());
    } 
    httpServletRequest.setAttribute("roles", roles);
    httpServletRequest.setAttribute("userRightScope", new ArrayList());
    String managerScope = "*" + session.getAttribute("orgId") + "*";
    httpServletRequest.setAttribute("managerScope", managerScope);
    httpServletRequest.setAttribute("listDuty", employeeBD.listDuty(domainId));
    httpServletRequest.setAttribute("listStation", employeeBD.listStation(domainId));
    if ("update".equals(action)) {
      String logId = httpServletRequest.getParameter("logId");
      UserBD userBD = new UserBD();
      List<Object[]> userList = userBD.getUserInfo(logId);
      Object[] obj = userList.get(0);
      List oldRoles = null;
      if ("insert".equals(obj[33].toString()) || "update".equals(obj[33].toString())) {
        oldRoles = roleBD.getAuditUserRole(logId);
      } else {
        oldRoles = roleBD.getUserRole(obj[32].toString());
      } 
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < oldRoles.size(); i++) {
        sb.append("$");
        sb.append(oldRoles.get(i));
        sb.append("$,");
      } 
      httpServletRequest.setAttribute("oldRoles", sb.toString());
      RightBD rightBD = new RightBD();
      List userRightScope = null;
      if ("insert".equals(obj[33].toString()) || "update".equals(obj[33].toString())) {
        userRightScope = rightBD.getAuditRightScope(logId);
      } else {
        userRightScope = rightBD.getUserRightScope(obj[32].toString());
      } 
      httpServletRequest.setAttribute("userRightScope", userRightScope);
      httpServletRequest.setAttribute("empId", obj[32]);
      httpServletRequest.setAttribute("empName", obj[0]);
      httpServletRequest.setAttribute("empEnglishName", obj[1]);
      httpServletRequest.setAttribute("userAccounts", obj[2]);
      httpServletRequest.setAttribute("empSex", obj[3]);
      httpServletRequest.setAttribute("userIsSuper", obj[5]);
      httpServletRequest.setAttribute("userSuperBegin", obj[6]);
      httpServletRequest.setAttribute("userSuperEnd", obj[7]);
      httpServletRequest.setAttribute("orgId", obj[8]);
      httpServletRequest.setAttribute("orgName", obj[9]);
      httpServletRequest.setAttribute("empLeaderId", obj[10]);
      httpServletRequest.setAttribute("empLeaderName", obj[11]);
      httpServletRequest.setAttribute("browseRange", obj[12]);
      httpServletRequest.setAttribute("browseRangeName", obj[13]);
      httpServletRequest.setAttribute("userOrderCode", obj[14]);
      httpServletRequest.setAttribute("empDuty", obj[15]);
      httpServletRequest.setAttribute("keyValidate", obj[16]);
      httpServletRequest.setAttribute("keySerial", obj[17]);
      httpServletRequest.setAttribute("userSimpleName", obj[18]);
      httpServletRequest.setAttribute("mailboxSize", obj[19]);
      httpServletRequest.setAttribute("netDiskSize", obj[20]);
      httpServletRequest.setAttribute("signatureImgName", obj[21]);
      httpServletRequest.setAttribute("signatureImgSaveName", obj[22]);
      httpServletRequest.setAttribute("sidelineOrg", obj[23]);
      httpServletRequest.setAttribute("sidelineOrgName", obj[24]);
      httpServletRequest.setAttribute("deptLeader", obj[25]);
      httpServletRequest.setAttribute("empPosition", obj[26]);
      httpServletRequest.setAttribute("empPositionId", obj[27]);
      httpServletRequest.setAttribute("empPositionOtherId", obj[28]);
      httpServletRequest.setAttribute("grantRange", obj[29]);
      httpServletRequest.setAttribute("grantRangeName", obj[30]);
      httpServletRequest.setAttribute("mailPost", obj[31]);
      httpServletRequest.setAttribute("operate", obj[33]);
      httpServletRequest.setAttribute("weixinPost", obj[32]);
      return actionMapping.findForward("updates");
    } 
    if ("setInput".equals(action)) {
      String orgId = httpServletRequest.getParameter("orgId");
      httpServletResponse.setContentType("text/xml;charset=GBK");
      httpServletResponse.setHeader("Cache-Control", "no-cache");
      PrintWriter out = httpServletResponse.getWriter();
      StringBuffer xml = new StringBuffer("");
      String str = StaticParam.getOrgLeaderById(orgId);
      xml.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>\n");
      xml.append("<result>\n");
      xml.append("  <leader>" + str + "</leader>\n");
      xml.append("</result>\n");
      out.print(xml.toString());
      out.close();
      return null;
    } 
    if ("audit".equals(action)) {
      UserBD userBD = new UserBD();
      TblUserBD tblUserBD = new TblUserBD();
      TblUser tblUser = new TblUser();
      String flag = httpServletRequest.getParameter("flag");
      String logId = httpServletRequest.getParameter("logId");
      AuditEmployeePO po = userBD.getEmployeePO(logId);
      String typeString = "";
      if ("yes".equals(flag)) {
        typeString = "审核通过";
        String operate = httpServletRequest.getParameter("operate");
        if (operate.indexOf("update") >= 0) {
          EmployeeVO employeeVO = (EmployeeVO)FillBean.transformOTO(po, EmployeeVO.class);
          employeeVO.setCreatedOrg(Long.parseLong(session.getAttribute("orgId").toString()));
          String orgIds = po.getOrgId();
          String rightIds = "", rightScopeTypes = "", rightScopeScopes = "", rightScopeDsp = "";
          RightBD rightBD = new RightBD();
          List<String[]> userRightScope = rightBD.getAuditRightScope(logId);
          for (int i = 0; i < userRightScope.size(); i++) {
            String[] right = userRightScope.get(i);
            rightIds = String.valueOf(rightIds) + right[0] + ",";
            rightScopeTypes = String.valueOf(rightScopeTypes) + right[1] + ",";
            if ("".equals(right[2])) {
              rightScopeScopes = String.valueOf(rightScopeScopes) + "0,";
            } else {
              rightScopeScopes = String.valueOf(rightScopeScopes) + right[2] + ",";
            } 
            rightScopeDsp = String.valueOf(rightScopeDsp) + right[5] + "|";
          } 
          String roleIds = httpServletRequest.getParameter("userRoleId").replaceAll("\\$", "");
          String[] roleId = roleIds.split(",");
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
          if ("iactive".equals(ReadActiveXml.getReadActive().getUse()))
            try {
              String tblUserName = userBD.getUserName(Long.valueOf(employeeVO.getEmpId()));
              tblUser = tblUserBD.findTblUser(tblUserName);
              if (po.getUserPassword() != null && !"".equals(po.getUserPassword()))
                tblUser.setPassWord(po.getUserPassword()); 
              tblUser.setUserName(po.getUserAccounts());
              tblUser.setTruename(po.getEmpName());
              tblUser.setNickName(po.getEmpName());
              tblUser.setSex(po.getEmpSex());
              TblDepartmentBD tblDepartmentBD = new TblDepartmentBD();
              String sa = organizationBD.findOrgSerial(Integer.parseInt(po.getOrgId()));
              int did = tblDepartmentBD.findID(sa);
              tblUser.setDeptId(did);
              tblUserBD.updateTblUser(tblUser);
            } catch (Exception e) {
              e.printStackTrace();
            }  
          if ("update".equals(operate)) {
            userBD.update(employeeVO, orgId, rightId, rightScopeType, rightScopeScope, rightScopeDsp, roleId);
          } else {
            (new NewEmployeeBD()).update(employeeVO, new EmployeeOtherInfoVO(), orgIds, Long.valueOf(employeeVO.getEmpId()));
          } 
          String lead = (po.getDeptLeader() == null) ? "0" : po.getDeptLeader();
          organizationBD.updateRelationDept(po.getOrgId(), String.valueOf(employeeVO.getEmpId()), employeeVO.getEmpName(), lead);
          AuditLog auditLog1 = userBD.auditLog(logId);
          Long long_ = auditLog1.getSubmitEmpid();
          String userName = auditLog1.getSubmitEmpname();
          String userOrgName = StaticParam.getOrgNameByOrgId((String)auditLog1.getSubmitOrgid());
          domainId = session.getAttribute("domainId").toString();
          LogBD logBD = new LogBD();
          Date date = new Date();
          logBD.log((String)long_, userName, userOrgName, "system_user", "系统管理", 
              date, date, "2", employeeVO.getEmpName(), session.getAttribute("userIP").toString(), domainId);
        } else if ("insert".equals(operate)) {
          EmployeeVO employeeVO = (EmployeeVO)FillBean.transformOTO(po, EmployeeVO.class);
          employeeVO.setCreatedOrg(Long.parseLong(session.getAttribute("orgId").toString()));
          String orgIds = po.getOrgId();
          String rightIds = "", rightScopeTypes = "", rightScopeScopes = "", rightScopeDsp = "";
          RightBD rightBD = new RightBD();
          List<String[]> userRightScope = rightBD.getAuditRightScope(logId);
          for (int i = 0; i < userRightScope.size(); i++) {
            String[] right = userRightScope.get(i);
            rightIds = String.valueOf(rightIds) + right[0] + ",";
            rightScopeTypes = String.valueOf(rightScopeTypes) + right[1] + ",";
            if ("".equals(right[2])) {
              rightScopeScopes = String.valueOf(rightScopeScopes) + "0,";
            } else {
              rightScopeScopes = String.valueOf(rightScopeScopes) + right[2] + ",";
            } 
            rightScopeDsp = String.valueOf(rightScopeDsp) + right[5] + "|";
          } 
          String[] orgId = new String[0];
          String[] rightId = new String[0];
          String[] rightScopeType = new String[0];
          String[] rightScopeScope = new String[0];
          String roleIds = httpServletRequest.getParameter("userRoleId").replaceAll("\\$", "");
          String[] roleId = roleIds.split(",");
          orgId = new String[1];
          orgId[0] = orgIds;
          if (rightIds.indexOf(",") != -1)
            rightId = rightIds.split(","); 
          if (rightScopeTypes.indexOf(",") != -1)
            rightScopeType = rightScopeTypes.split(","); 
          if (rightScopeScopes.indexOf(",") != -1)
            rightScopeScope = rightScopeScopes.split(","); 
          OrganizationBD organizationBD = new OrganizationBD();
          if ("iactive".equals(ReadActiveXml.getReadActive().getUse())) {
            tblUser.setUserName(employeeVO.getUserAccounts());
            tblUser.setPassWord(po.getUserPassword());
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
            String sa = organizationBD.findOrgSerial(Integer.parseInt(po.getOrgId()));
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
          int result = 0;
          result = userBD.add(employeeVO, orgId, rightId, rightScopeType, rightScopeScope, rightScopeDsp, roleId);
          if (employeeVO.getDeptLeader() != null && employeeVO.getDeptLeader().equals("1"))
            organizationBD.updateRelationDept(po.getOrgId(), String.valueOf(employeeVO.getEmpId()), employeeVO.getEmpName(), po.getDeptLeader()); 
          AuditLog auditLog1 = userBD.auditLog(logId);
          Long long_ = auditLog1.getSubmitEmpid();
          String userName = auditLog1.getSubmitEmpname();
          String userOrgName = StaticParam.getOrgNameByOrgId((String)auditLog1.getSubmitOrgid());
          domainId = session.getAttribute("domainId").toString();
          LogBD logBD = new LogBD();
          Date date = new Date();
          logBD.log((String)long_, userName, userOrgName, "system_user", "系统管理", date, date, "1", employeeVO.getEmpName(), session.getAttribute("userIP").toString(), domainId);
        } else if ("delete".equals(operate)) {
          String id = (new StringBuilder(String.valueOf(po.getEmpId()))).toString();
          if (id != null && !"".equals(id)) {
            String result = "";
            String[] ids = id.split(",");
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
            result = userBD.delete(ids);
            AuditLog auditLog1 = userBD.auditLog(logId);
            Long long_ = auditLog1.getSubmitEmpid();
            String userName = auditLog1.getSubmitEmpname();
            String userOrgName = StaticParam.getOrgNameByOrgId((String)auditLog1.getSubmitOrgid());
            domainId = session.getAttribute("domainId").toString();
            LogBD logBD = new LogBD();
            Date date = new Date();
            logBD.log((String)long_, userName, userOrgName, "system_user", "系统管理", 
                date, date, "3", result, session.getAttribute("userIP").toString(), domainId);
          } else {
            return actionMapping.findForward("close");
          } 
        } else if ("disable".equals(operate)) {
          String id = (new StringBuilder(String.valueOf(po.getEmpId()))).toString();
          String result = "";
          if (id != null && !"".equals(id)) {
            String[] ids = id.split(",");
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
            result = userBD.disable(ids);
            AuditLog auditLog1 = userBD.auditLog(logId);
            Long long_ = auditLog1.getSubmitEmpid();
            String userName = auditLog1.getSubmitEmpname();
            String userOrgName = StaticParam.getOrgNameByOrgId((String)auditLog1.getSubmitOrgid());
            domainId = session.getAttribute("domainId").toString();
            LogBD logBD = new LogBD();
            Date date = new Date();
            logBD.log((String)long_, userName, userOrgName, "system_user", "系统管理", 
                date, date, "5", result, session.getAttribute("userIP").toString(), domainId);
          } else {
            return actionMapping.findForward("close");
          } 
        } else if ("recover".equals(operate)) {
          String id = (new StringBuilder(String.valueOf(po.getEmpId()))).toString();
          String result = "";
          if (id != null && !"".equals(id)) {
            String[] ids = id.split(",");
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
            result = userBD.recover(ids);
            AuditLog auditLog1 = userBD.auditLog(logId);
            Long long_ = auditLog1.getSubmitEmpid();
            String userName = auditLog1.getSubmitEmpname();
            String userOrgName = StaticParam.getOrgNameByOrgId((String)auditLog1.getSubmitOrgid());
            domainId = session.getAttribute("domainId").toString();
            LogBD logBD = new LogBD();
            Date date = new Date();
            logBD.log((String)long_, userName, userOrgName, "system_user", "系统管理", 
                date, date, "6", result, session.getAttribute("userIP").toString(), domainId);
          } else {
            return actionMapping.findForward("close");
          } 
        } else if ("rehis".equals(operate)) {
          String id = (new StringBuilder(String.valueOf(po.getEmpId()))).toString();
          if (id != null && !"".equals(id)) {
            EmployeeVO employeeVO = new EmployeeVO();
            try {
              employeeVO = userBD.getEmployeeVO(Long.valueOf(Long.parseLong(id)));
              List<Object[]> list = (new NewEmployeeBD()).selectSingle(Long.valueOf(employeeVO.getEmpId()));
              Object object = "";
              if (list.size() > 0) {
                Object[] arrayOfObject = list.get(0);
                object = arrayOfObject[1];
              } 
              if ("iactive".equals(ReadActiveXml.getReadActive().getUse())) {
                tblUser.setUserName(employeeVO.getUserAccounts());
                TblJilu tblJilu = new TblJilu();
                tblJilu = userBD.findTblJiluByUsername(employeeVO.getUserAccounts());
                tblUser.setPassWord(tblJilu.getPassWord());
                userBD.delTblJilu(tblJilu);
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
                OrganizationBD organizationBD = new OrganizationBD();
                TblDepartmentBD tblDepartmentBD = new TblDepartmentBD();
                String sa = organizationBD.findOrgSerial(Integer.parseInt((String)object));
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
                tblUserBD.addTblUser(tblUser);
              } 
            } catch (Exception e) {
              e.printStackTrace();
            } 
            employeeBD.rehis(id);
          } else {
            return actionMapping.findForward("close");
          } 
        } 
        AuditLog auditLog = userBD.auditLog(logId);
        String op = "";
        if ("update".equals(po.getOperate())) {
          op = "更新";
        } else if ("insert".equals(po.getOperate())) {
          op = "新增";
        } else if ("delete".equals(po.getOperate())) {
          op = "删除";
        } else if ("disable".equals(po.getOperate())) {
          op = "禁用";
        } else if ("recover".equals(po.getOperate())) {
          op = "恢复";
        } else if ("rehis".equals(po.getOperate())) {
          op = "恢复";
        } 
        AuditMsRemindEJBBean msRemindBeann = new AuditMsRemindEJBBean();
        msRemindBeann.auditRemind(Long.valueOf(session.getAttribute("userId").toString()), session.getAttribute("orgId").toString(), session.getAttribute("userName").toString(), 
            3, 1, new Date(), "您" + op + "的用户管理“" + po.getEmpName() + "”" + typeString + "！", "audit", Long.valueOf(logId).longValue(), "", (String)auditLog.getSubmitEmpid());
        (new UserBD()).auditEmp(logId, flag, session.getAttribute("userId").toString(), session.getAttribute("userName").toString());
      } else {
        typeString = "审核未通过";
        AuditLog auditLog = userBD.auditLog(logId);
        String op = "";
        if ("update".equals(po.getOperate())) {
          op = "更新";
        } else if ("insert".equals(po.getOperate())) {
          op = "新增";
        } else if ("delete".equals(po.getOperate())) {
          op = "删除";
        } else if ("disable".equals(po.getOperate())) {
          op = "禁用";
        } else if ("recover".equals(po.getOperate())) {
          op = "恢复";
        } else if ("rehis".equals(po.getOperate())) {
          op = "恢复";
        } 
        AuditMsRemindEJBBean msRemindBeann = new AuditMsRemindEJBBean();
        msRemindBeann.auditRemind(Long.valueOf(session.getAttribute("userId").toString()), session.getAttribute("orgId").toString(), session.getAttribute("userName").toString(), 
            3, 1, new Date(), "您" + op + "的用户管理“" + po.getEmpName() + "”" + typeString + "！", "audit", Long.valueOf(logId).longValue(), "", (String)auditLog.getSubmitEmpid());
        (new UserBD()).auditEmp(logId, flag, session.getAttribute("userId").toString(), session.getAttribute("userName").toString());
      } 
    } 
    return actionMapping.findForward("close");
  }
}
