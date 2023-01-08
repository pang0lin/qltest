package com.js.system.action.usermanager;

import com.js.oa.hr.officemanager.service.EmployeeBD;
import com.js.oa.hr.personnelmanager.service.WorkAddressBD;
import com.js.oa.security.seamoon.service.SeaMoonService;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.organizationmanager.OrganizationBD;
import com.js.system.service.rightmanager.RightBD;
import com.js.system.service.rolemanager.RoleBD;
import com.js.system.service.usermanager.UserBD;
import com.js.system.util.StaticParam;
import com.js.system.vo.organizationmanager.DomainVO;
import com.js.util.config.SystemCommon;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ToUserAddAction extends Action {
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
    EmployeeBD employeeBD = new EmployeeBD();
    RoleBD roleBD = new RoleBD();
    List<Object[]> roles = null;
    if ("update".equals(action)) {
      roles = roleBD.getOwnerRoles(managerId, managerOrgId, browseRange, sysManager, session.getAttribute("domainId").toString());
    } else {
      roles = roleBD.getOwnerRoles(managerId, managerOrgId, browseRange, sysManager, session.getAttribute("domainId").toString());
    } 
    StringBuffer sbsBuffer = new StringBuffer();
    List<Object[]> oldRoleHistory = roleBD.getUserRoleHistory(userId);
    String rolesID = "";
    if (roles != null && roles.size() > 0)
      for (int i = 0; i < roles.size(); i++) {
        Object[] object = roles.get(i);
        rolesID = String.valueOf(rolesID) + object[0] + ",";
      }  
    if (oldRoleHistory != null && oldRoleHistory.size() > 0)
      for (int i = 0; i < oldRoleHistory.size(); i++) {
        Object[] object = oldRoleHistory.get(i);
        if (sbsBuffer.toString().indexOf("$" + object[0] + "$") < 0 && rolesID.indexOf((String)object[0]) < 0)
          roles.add(oldRoleHistory.get(i)); 
      }  
    httpServletRequest.setAttribute("roles", roles);
    httpServletRequest.setAttribute("userRightScope", new ArrayList());
    String managerScope = "*" + session.getAttribute("orgId") + "*";
    if ("1".equals(session.getAttribute("sysManager").toString())) {
      managerScope = "*0*";
    } else {
      Object[] obj = (new ManagerService()).getRightScope(session.getAttribute("userId").toString(), "00*01*02").get(0);
      String type = obj[0].toString();
      if ("4".equals(type) && 
        obj[1] != null)
        managerScope = obj[1].toString(); 
    } 
    httpServletRequest.setAttribute("managerScope", managerScope);
    if (SystemCommon.getMultiDepart() == 1) {
      httpServletRequest.setAttribute("listDuty", employeeBD.listDuty(domainId, session.getAttribute("corpId").toString()));
    } else {
      httpServletRequest.setAttribute("listDuty", employeeBD.listDuty(domainId));
    } 
    if (SystemCommon.getMultiDepart() == 1) {
      httpServletRequest.setAttribute("listStation", employeeBD.listStation(domainId, session.getAttribute("corpId").toString()));
    } else {
      httpServletRequest.setAttribute("listStation", employeeBD.listStation(domainId));
    } 
    httpServletRequest.setAttribute("listWorkAddress", (new WorkAddressBD()).list(new Long(domainId)));
    if ("update".equals(action)) {
      List oldRoles = roleBD.getUserRole(userId);
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < oldRoles.size(); i++) {
        sb.append("$");
        sb.append(oldRoles.get(i));
        sb.append("$,");
      } 
      httpServletRequest.setAttribute("oldRoles", sb.toString());
      RightBD rightBD = new RightBD();
      List userRightScope = rightBD.getUserRightScope(userId);
      httpServletRequest.setAttribute("userRightScope", userRightScope);
      UserBD userBD = new UserBD();
      List<Object[]> userList = userBD.getUserInfo(new Long(userId));
      Object[] obj = userList.get(0);
      httpServletRequest.setAttribute("empId", userId);
      httpServletRequest.setAttribute("empName", obj[0]);
      httpServletRequest.setAttribute("empEnglishName", obj[1]);
      httpServletRequest.setAttribute("empNumber", obj[34]);
      httpServletRequest.setAttribute("workAddress", obj[35]);
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
      if ("2".equals(SystemCommon.getUKey())) {
        SeaMoonService smService = new SeaMoonService();
        httpServletRequest.setAttribute("keySerial", smService.getSeeMoonSNByUserId(userId));
      } else {
        httpServletRequest.setAttribute("keySerial", obj[17]);
      } 
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
      httpServletRequest.setAttribute("weixinPost", obj[32]);
      httpServletRequest.setAttribute("dingdingPost", obj[33]);
      httpServletRequest.setAttribute("opinionRemind", obj[36]);
      return actionMapping.findForward("update");
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
    return actionMapping.findForward("success");
  }
}
