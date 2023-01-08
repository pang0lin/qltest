package com.js.oa.hr.personnelmanager.action;

import com.active.e_uc.user.po.TblUser;
import com.active.e_uc.user.service.TblDepartmentBD;
import com.active.e_uc.user.service.TblUserBD;
import com.js.oa.hr.personnelmanager.po.EmployeeChangePO;
import com.js.oa.hr.personnelmanager.po.EmployeeChangeTypePO;
import com.js.oa.hr.personnelmanager.service.EmpChangeBD;
import com.js.oa.hr.personnelmanager.service.NewDutyBD;
import com.js.oa.hr.personnelmanager.service.NewEmployeeBD;
import com.js.system.service.organizationmanager.OrganizationBD;
import com.js.system.service.usermanager.UserBD;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.FillBean;
import com.js.util.util.ReadActiveXml;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EmpChangeAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    String action = httpServletRequest.getParameter("action");
    EmpChangeActionForm empChangeActionForm = 
      (EmpChangeActionForm)actionForm;
    EmpChangeBD empChangeBD = new EmpChangeBD();
    String saveType = httpServletRequest.getParameter("saveType");
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    if ("empChangView".equals(action)) {
      empChangList(httpServletRequest);
      List selectEmpType = empChangeBD.selectEmpType(domainId);
      httpServletRequest.setAttribute("empEmpTypeList", selectEmpType);
      return actionMapping.findForward("gotoEmpChangList");
    } 
    if ("empChangeSearch".equals(action)) {
      if ("1".equals(httpServletRequest.getParameter("export"))) {
        empChangSearchExport(httpServletRequest);
        return actionMapping.findForward("gotoEmpChangExport");
      } 
      empChangSearchList(httpServletRequest);
      List selectEmpType = empChangeBD.selectEmpType(domainId);
      httpServletRequest.setAttribute("empEmpTypeList", selectEmpType);
      return actionMapping.findForward("gotoEmpChangList");
    } 
    if ("addEmpChangeView".equals(action)) {
      List selectEmpDuty = empChangeBD.selectEmpDuty(domainId);
      List selectEmpType = empChangeBD.selectEmpType(domainId);
      httpServletRequest.setAttribute("empDutyList", selectEmpDuty);
      httpServletRequest.setAttribute("empEmpTypeList", selectEmpType);
      return actionMapping.findForward("gotoEmpChangAdd");
    } 
    if ("addEmpChange".equals(action)) {
      Date empChangeDate = new Date(httpServletRequest.getParameter("empChangeDate"));
      Long empChangeOldDuty = new Long(httpServletRequest.getParameter("empChangeOldDuty"));
      Long empChangeNewDuty = new Long(httpServletRequest.getParameter("empChangeNewDuty"));
      String empChangeChangeType = httpServletRequest.getParameter("empChangeChangeType");
      String changeEmpId = httpServletRequest.getParameter("changeEmpId");
      EmployeeChangePO empChangePO = (EmployeeChangePO)FillBean.transformOneToOne(empChangeActionForm, EmployeeChangePO.class);
      empChangePO.setDomainId(domainId);
      empChangePO.setEmpChangeDate(empChangeDate);
      empChangePO.setEmpChangeOldDuty(empChangeOldDuty);
      empChangePO.setEmpChangeEmpId(Long.valueOf(changeEmpId));
      System.out.println("----empChangeEmpId-00000--->>>>>" + changeEmpId);
      if (empChangeNewDuty.longValue() == 0L) {
        empChangePO.setEmpChangeNewDuty(empChangeOldDuty);
      } else {
        empChangePO.setEmpChangeNewDuty(empChangeNewDuty);
      } 
      empChangePO.setEmpChangeChangeType(empChangeChangeType);
      empChangePO.setDomainId(domainId);
      UserBD userBD = new UserBD();
      if ("iactive".equals(ReadActiveXml.getReadActive().getUse())) {
        TblUser tblUser = new TblUser();
        TblUserBD tblUserBD = new TblUserBD();
        String tblUserName = userBD.getUserName(empChangePO.getEmpChangeEmpId());
        tblUser = tblUserBD.findTblUser(tblUserName);
        OrganizationBD organizationBD = new OrganizationBD();
        TblDepartmentBD tblDepartmentBD = new TblDepartmentBD();
        String sa = organizationBD.findOrgSerial(empChangePO.getEmpChangeNewOrg().longValue());
        int did = tblDepartmentBD.findID(sa);
        tblUser.setDeptId(did);
        tblUserBD.updateTblUser(tblUser);
      } 
      List selectEmpDuty = empChangeBD.selectEmpDuty(domainId);
      List selectEmpType = empChangeBD.selectEmpType(domainId);
      boolean result = empChangeBD.addEmpChange(empChangePO);
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("saveAndContinue".equals(saveType)) {
        empChangeActionForm.reset(actionMapping, httpServletRequest);
        empChangeActionForm.setSaveType("saveAndContinue");
        httpServletRequest.setAttribute("empDutyList", selectEmpDuty);
        httpServletRequest.setAttribute("empEmpTypeList", selectEmpType);
        httpServletRequest.setAttribute("empName", "");
        httpServletRequest.setAttribute("oldDuty", "0");
        return actionMapping.findForward("empChange_saveAndContinue");
      } 
      if ("saveAndExit".equals(saveType)) {
        empChangeActionForm.reset(actionMapping, httpServletRequest);
        empChangeActionForm.setSaveType("saveAndExit");
        httpServletRequest.setAttribute("empDutyList", selectEmpDuty);
        httpServletRequest.setAttribute("empEmpTypeList", selectEmpType);
        return actionMapping.findForward("empChange_saveAndExit");
      } 
    } 
    if ("deleteEmpChange".equals(action)) {
      Long empChangeId = Long.valueOf("0");
      if (httpServletRequest.getParameter("empChangeId") != null)
        empChangeId = Long.valueOf(httpServletRequest.getParameter(
              "empChangeId")); 
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset"));
      boolean result = true;
      if (empChangeId.longValue() != 0L)
        empChangeBD.deleteEmpChange(empChangeId); 
      List selectEmpType = empChangeBD.selectEmpType(domainId);
      httpServletRequest.setAttribute("empEmpTypeList", selectEmpType);
      if (!result)
        return actionMapping.findForward("failure"); 
      if (offsetCopy != 0) {
        empChangSearchList(httpServletRequest);
      } else {
        empChangSearchList(httpServletRequest);
      } 
      empChangeActionForm.reset(actionMapping, httpServletRequest);
      return actionMapping.findForward("gotoEmpChangList");
    } 
    if ("deleteBatchEmpChange".equals(action)) {
      boolean result = true;
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset"));
      if (httpServletRequest.getParameter("ids") != null && 
        !"".equals(httpServletRequest.getParameter("ids"))) {
        String empChangeIds = httpServletRequest.getParameter("ids");
        result = empChangeBD.deleteBatchEmpChange(empChangeIds);
      } 
      List selectEmpType = empChangeBD.selectEmpType(domainId);
      httpServletRequest.setAttribute("empEmpTypeList", selectEmpType);
      if (!result)
        return actionMapping.findForward("failure"); 
      if (offsetCopy != 0) {
        empChangSearchList(httpServletRequest);
      } else {
        empChangSearchList(httpServletRequest);
      } 
      empChangeActionForm.reset(actionMapping, httpServletRequest);
      return actionMapping.findForward("gotoEmpChangList");
    } 
    if ("selectEmpChangeView".equals(action)) {
      Long empChangeId = Long.valueOf(httpServletRequest.getParameter(
            "empChangeId"));
      List selectEmpDuty = empChangeBD.selectEmpDuty(domainId);
      EmployeeChangePO empChangePO = empChangeBD.selectEmpChangeView(
          empChangeId);
      Date empChangeDate = empChangePO.getEmpChangeDate();
      empChangeActionForm.setEmpChangeEmpId(empChangePO.getEmpChangeEmpId());
      String empChangeEmpName = empChangeBD.selectEmpName(empChangePO
          .getEmpChangeEmpId());
      httpServletRequest.setAttribute("empName", empChangeEmpName);
      empChangeActionForm.setEmpChangeOldOrg(empChangePO
          .getEmpChangeOldOrg());
      String empChangeOldOrgName = empChangeBD.selectOrgName(empChangePO
          .getEmpChangeOldOrg());
      empChangeActionForm.setEmpChangeOldOrgName(empChangeOldOrgName);
      empChangeActionForm.setEmpChangeNewOrg(empChangePO
          .getEmpChangeNewOrg());
      String empChangeNewOrgName = empChangeBD.selectOrgName(empChangePO
          .getEmpChangeNewOrg());
      empChangeActionForm.setEmpChangeNewOrgName(empChangeNewOrgName);
      empChangeActionForm.setEmpChangeChangeType(empChangePO
          .getEmpChangeChangeType());
      Long empChangeOldDuty = empChangePO.getEmpChangeOldDuty();
      Long empChangeNewDuty = empChangePO.getEmpChangeNewDuty();
      String empChangeChangeType = empChangePO.getEmpChangeChangeType();
      List selectEmpType = empChangeBD.selectEmpType(domainId);
      httpServletRequest.setAttribute("empChangeDate", empChangeDate);
      httpServletRequest.setAttribute("empChangeOldDuty", 
          empChangeOldDuty);
      httpServletRequest.setAttribute("empChangeNewDuty", 
          empChangeNewDuty);
      httpServletRequest.setAttribute("empChangeChangeType", 
          empChangeChangeType);
      httpServletRequest.setAttribute("empDutyList", selectEmpDuty);
      httpServletRequest.setAttribute("empEmpTypeList", selectEmpType);
      httpServletRequest.setAttribute("empChangeId", 
          empChangePO.getEmpChangeId());
      return actionMapping.findForward("gotoModiEmpChange");
    } 
    if ("updateEmpChange".equals(action)) {
      Long empChangeId = Long.valueOf(httpServletRequest.getParameter(
            "empChangeId"));
      Date empChangeDate = new Date(httpServletRequest.getParameter(
            "empChangeDate"));
      Long empChangeOldDuty = new Long(httpServletRequest.getParameter(
            "empChangeOldDuty"));
      Long empChangeNewDuty = new Long(httpServletRequest.getParameter(
            "empChangeNewDuty"));
      String empChangeChangeType = httpServletRequest.getParameter(
          "empChangeChangeType");
      EmployeeChangePO empChangePO = 
        (EmployeeChangePO)FillBean.transformOneToOne(
          empChangeActionForm, EmployeeChangePO.class);
      empChangePO.setEmpChangeId(empChangeId);
      empChangePO.setEmpChangeDate(empChangeDate);
      empChangePO.setEmpChangeOldDuty(empChangeOldDuty);
      if (empChangeNewDuty.longValue() == 0L) {
        empChangePO.setEmpChangeNewDuty(empChangeOldDuty);
      } else {
        empChangePO.setEmpChangeNewDuty(empChangeNewDuty);
      } 
      if ("iactive".equals(ReadActiveXml.getReadActive().getUse())) {
        UserBD userBD = new UserBD();
        TblUser tblUser = new TblUser();
        TblUserBD tblUserBD = new TblUserBD();
        String tblUserName = userBD.getUserName(empChangePO.getEmpChangeEmpId());
        tblUser = tblUserBD.findTblUser(tblUserName);
        OrganizationBD organizationBD = new OrganizationBD();
        TblDepartmentBD tblDepartmentBD = new TblDepartmentBD();
        String sa = organizationBD.findOrgSerial(empChangePO.getEmpChangeNewOrg().longValue());
        int did = tblDepartmentBD.findID(sa);
        tblUser.setDeptId(did);
        tblUserBD.updateTblUser(tblUser);
      } 
      empChangePO.setEmpChangeChangeType(empChangeChangeType);
      empChangePO.setDomainId(domainId);
      boolean result = empChangeBD.updateEmpChange(empChangePO);
      List selectEmpDuty = empChangeBD.selectEmpDuty(domainId);
      List selectEmpType = empChangeBD.selectEmpType(domainId);
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("updateAndExit".equals(saveType)) {
        empChangeActionForm.reset(actionMapping, httpServletRequest);
        empChangeActionForm.setSaveType("updateAndExit");
        httpServletRequest.setAttribute("empDutyList", selectEmpDuty);
        httpServletRequest.setAttribute("empEmpTypeList", selectEmpType);
        return actionMapping.findForward("empChange_updateAndExit");
      } 
    } 
    if ("empChangTypeView".equals(action)) {
      empChangTypeList(httpServletRequest);
      return actionMapping.findForward("gotoEmpChangTypeList");
    } 
    if ("addEmpChangeTypeView".equals(action))
      return actionMapping.findForward("gotoEmpChangTypeAdd"); 
    if ("addEmpChangeType".equals(action)) {
      EmployeeChangeTypePO empChangeTypePO = 
        (EmployeeChangeTypePO)FillBean.transformOneToOne(empChangeActionForm, 
          EmployeeChangeTypePO.class);
      empChangeTypePO.setDomainId(domainId);
      if (empChangeBD.hasSameNameExists(empChangeTypePO.getEmpChangeType(), 
          domainId)) {
        httpServletRequest.setAttribute("errMessages", 
            "调整类型重复，请重新输入！");
      } else {
        int result = empChangeBD.addEmpChangeType(empChangeTypePO);
        if (result > 0) {
          httpServletRequest.setAttribute("opResult", 
              String.valueOf(result));
        } else {
          empChangeActionForm.setEmpChangeChangeType("");
        } 
        httpServletRequest.setAttribute("shouldClose", "1");
      } 
      if ("saveAndContinue".equals(saveType)) {
        empChangeActionForm.reset(actionMapping, httpServletRequest);
        empChangeActionForm.setSaveType("saveAndContinue");
        return actionMapping.findForward(
            "empChangeType_saveAndContinue");
      } 
      if ("saveAndExit".equals(saveType)) {
        empChangeActionForm.reset(actionMapping, httpServletRequest);
        empChangeActionForm.setSaveType("saveAndExit");
        return actionMapping.findForward("empChangeType_saveAndExit");
      } 
    } 
    if ("deleteEmpChangeType".equals(action)) {
      Long empChangeTypeId = Long.valueOf("0");
      boolean result = true;
      if (httpServletRequest.getParameter("empChangeTypeId") != null) {
        empChangeTypeId = Long.valueOf(httpServletRequest.getParameter(
              "empChangeTypeId"));
        result = empChangeBD.deleteEmpChangeType(empChangeTypeId);
      } 
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset"));
      httpServletRequest.setAttribute("deleteSuccess", new Boolean(result));
      if (offsetCopy != 0) {
        empChangTypeList(httpServletRequest, offsetCopy);
      } else {
        empChangTypeList(httpServletRequest);
      } 
      empChangeActionForm.reset(actionMapping, httpServletRequest);
      return actionMapping.findForward("gotoEmpChangTypeList");
    } 
    if ("deleteBatchEmpChangeType".equals(action)) {
      String empChangeTypeIds = httpServletRequest.getParameter("ids");
      int offsetCopy = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset"));
      boolean result = true;
      if (empChangeTypeIds != null && empChangeTypeIds.length() > 0)
        result = empChangeBD.deleteBatchEmpChangeType(
            empChangeTypeIds); 
      httpServletRequest.setAttribute("deleteSuccess", new Boolean(result));
      if (offsetCopy != 0) {
        empChangTypeList(httpServletRequest, offsetCopy);
      } else {
        empChangTypeList(httpServletRequest);
      } 
      empChangeActionForm.reset(actionMapping, httpServletRequest);
      return actionMapping.findForward("gotoEmpChangTypeList");
    } 
    if ("selectEmpChangeTypeView".equals(action)) {
      Long empChangeTypeId = Long.valueOf(httpServletRequest.getParameter(
            "empChangeTypeId"));
      EmployeeChangeTypePO empChangeTypePO = empChangeBD
        .selectEmpChangeTypeView(empChangeTypeId);
      empChangeActionForm.setEmpChangeType(empChangeTypePO
          .getEmpChangeType());
      httpServletRequest.setAttribute("empChangeTypeId", 
          empChangeTypePO.getId());
      return actionMapping.findForward("gotoModiEmpChangeType");
    } 
    if ("updateEmpChangeType".equals(action)) {
      Long empChangeTypeId = Long.valueOf(httpServletRequest.getParameter(
            "empChangeTypeId"));
      httpServletRequest.setAttribute("empChangeTypeId", empChangeTypeId);
      EmployeeChangeTypePO empChangeTypePO = 
        (EmployeeChangeTypePO)FillBean.transformOneToOne(empChangeActionForm, 
          EmployeeChangeTypePO.class);
      empChangeTypePO.setId(empChangeTypeId);
      empChangeTypePO.setDomainId(domainId);
      if (empChangeBD.hasSameNameExists(empChangeTypePO.getEmpChangeType(), 
          domainId, empChangeTypeId)) {
        httpServletRequest.setAttribute("errMessages", 
            "调整类型重复，请重新输入！");
      } else {
        int result = empChangeBD.updateEmpChangeType(empChangeTypePO);
        if (result > 0) {
          httpServletRequest.setAttribute("opResult", 
              String.valueOf(result));
        } else {
          empChangeActionForm.setEmpChangeChangeType("");
        } 
        httpServletRequest.setAttribute("shouldClose", "1");
      } 
      return actionMapping.findForward("empChangeType_updateAndExit");
    } 
    if ("getOrgAndDuty".equals(action)) {
      Long empId = empChangeActionForm
        .getEmpChangeEmpId();
      List<Object[]> list = (new NewEmployeeBD()).selectSingle(empId);
      EmployeeVO employeeVO = null;
      Object object = "";
      String orgName = "";
      if (list.size() > 0) {
        Object[] arrayOfObject = list.get(0);
        employeeVO = (EmployeeVO)arrayOfObject[0];
        object = arrayOfObject[1];
        orgName = (String)arrayOfObject[2];
      } 
      empChangeActionForm.setEmpChangeOldOrg(new Long((String)object));
      empChangeActionForm.setEmpChangeOldOrgName(orgName);
      httpServletRequest.setAttribute("oldDuty", (
          new NewDutyBD()).getDutyID(
            employeeVO.getEmpDuty(), new Long(domainId)));
      List selectEmpDuty = empChangeBD.selectEmpDuty(domainId);
      List selectEmpType = empChangeBD.selectEmpType(domainId);
      httpServletRequest.setAttribute("empDutyList", selectEmpDuty);
      httpServletRequest.setAttribute("empEmpTypeList", selectEmpType);
      httpServletRequest.setAttribute("empName", 
          httpServletRequest
          .getParameter("empChangeEmpName"));
      httpServletRequest.setAttribute("changeEmpId", empId);
      return actionMapping.findForward("gotoEmpChangAdd");
    } 
    return null;
  }
  
  public void empChangList(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String where = "";
    String databaseType = 
      SystemCommon.getDatabaseType();
    if (databaseType.indexOf("mysql") >= 0) {
      where = " where empChangePO.domainId=" + domainId + " and empChangePO.empChangeEmpId=empPO.empId and empChangePO.empChangeOldOrg=orgPO.orgId and empChangePO.empChangeNewOrg=orgPO2.orgId and empChangePO.empChangeOldDuty=dutyPO.id and empChangePO.empChangeNewDuty=dutyPO2.id and empChangePO.empChangeChangeType = empChangeTypePO.id order by empChangePO.empChangeDate desc";
    } else {
      where = " where empChangePO.domainId=" + domainId + " and empChangePO.empChangeEmpId=empPO.empId and empChangePO.empChangeOldOrg=orgPO.orgId and empChangePO.empChangeNewOrg=orgPO2.orgId and empChangePO.empChangeOldDuty=dutyPO.id and empChangePO.empChangeNewDuty=dutyPO2.id and empChangePO.empChangeChangeType = JSDB.FN_INTTOSTR(empChangeTypePO.id) order by empChangePO.empChangeDate desc";
    } 
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "empChangePO.empChangeId,empChangePO.empChangeDate,empPO.empName,orgPO.orgName,orgPO2.orgName,dutyPO.dutyName,dutyPO2.dutyName,empChangeTypePO.empChangeType", 
        "com.js.oa.hr.personnelmanager.po.EmployeeChangePO empChangePO,com.js.system.vo.usermanager.EmployeeVO empPO,com.js.system.vo.organizationmanager.OrganizationVO orgPO,com.js.system.vo.organizationmanager.OrganizationVO orgPO2,com.js.oa.hr.officemanager.po.DutyPO dutyPO,com.js.oa.hr.officemanager.po.DutyPO dutyPO2,com.js.oa.hr.personnelmanager.po.EmployeeChangeTypePO empChangeTypePO", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("personnelList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  public void empChangList(HttpServletRequest httpServletRequest, int offsetCopy) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String where = "";
    String databaseType = 
      SystemCommon.getDatabaseType();
    if (databaseType.indexOf("mysql") >= 0) {
      where = " where empChangePO.domainId=" + domainId + " and empChangePO.empChangeEmpId=empPO.empId and empChangePO.empChangeOldOrg=orgPO.orgId and empChangePO.empChangeNewOrg=orgPO2.orgId and empChangePO.empChangeOldDuty=dutyPO.id and empChangePO.empChangeNewDuty=dutyPO2.id and empChangePO.empChangeChangeType = empChangeTypePO.id order by empChangePO.empChangeDate desc";
    } else {
      where = " where empChangePO.domainId=" + domainId + " and empChangePO.empChangeEmpId=empPO.empId and empChangePO.empChangeOldOrg=orgPO.orgId and empChangePO.empChangeNewOrg=orgPO2.orgId and empChangePO.empChangeOldDuty=dutyPO.id and empChangePO.empChangeNewDuty=dutyPO2.id and empChangePO.empChangeChangeType = JSDB.FN_INTTOSTR(empChangeTypePO.id) order by empChangePO.empChangeDate desc";
    } 
    int pageSize = 15;
    int offset = offsetCopy;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "empChangePO.empChangeId,empChangePO.empChangeDate,empPO.empName,orgPO.orgName,orgPO2.orgName,dutyPO.dutyName,dutyPO2.dutyName,empChangeTypePO.empChangeType", 
        "com.js.oa.hr.personnelmanager.po.EmployeeChangePO empChangePO,com.js.system.vo.usermanager.EmployeeVO empPO,com.js.system.vo.organizationmanager.OrganizationVO orgPO,com.js.system.vo.organizationmanager.OrganizationVO orgPO2,com.js.oa.hr.officemanager.po.DutyPO dutyPO,com.js.oa.hr.officemanager.po.DutyPO dutyPO2,com.js.oa.hr.personnelmanager.po.EmployeeChangeTypePO empChangeTypePO", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("personnelList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  public void empChangSearchList(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String beginDate = String.valueOf(httpServletRequest.getParameter("beginDate")) + 
      " 00:00:00";
    String endDate = String.valueOf(httpServletRequest.getParameter("endDate")) + 
      " 23:59:59";
    String searchChangeEmp = httpServletRequest.getParameter(
        "searchChangeEmp");
    String searchChangeOldOrg = httpServletRequest.getParameter(
        "searchChangeOldOrg");
    String searchChangeNewOrg = httpServletRequest.getParameter(
        "searchChangeNewOrg");
    String searchChangeChangeType = httpServletRequest.getParameter(
        "searchChangeChangeType");
    String where = "";
    String databaseType = 
      SystemCommon.getDatabaseType();
    if (databaseType.indexOf("mysql") >= 0) {
      where = " where empChangePO.domainId=" + domainId + " and empChangePO.empChangeEmpId=empPO.empId and empChangePO.empChangeOldOrg=orgPO.orgId and empChangePO.empChangeNewOrg=orgPO2.orgId and empChangePO.empChangeOldDuty=dutyPO.id and empChangePO.empChangeNewDuty=dutyPO2.id and empChangePO.empChangeChangeType = empChangeTypePO.id and empPO.empName like '%" + 
        searchChangeEmp + "%' and orgPO.orgName like '%" + 
        searchChangeOldOrg + "%' and orgPO2.orgName like '%" + 
        searchChangeNewOrg + "%' ";
    } else {
      where = " where empChangePO.domainId=" + domainId + " and empChangePO.empChangeEmpId=empPO.empId and empChangePO.empChangeOldOrg=orgPO.orgId and empChangePO.empChangeNewOrg=orgPO2.orgId and empChangePO.empChangeOldDuty=dutyPO.id and empChangePO.empChangeNewDuty=dutyPO2.id and empChangePO.empChangeChangeType = JSDB.FN_INTTOSTR(empChangeTypePO.id) and empPO.empName like '%" + 
        searchChangeEmp + "%' and orgPO.orgName like '%" + 
        searchChangeOldOrg + "%' and orgPO2.orgName like '%" + 
        searchChangeNewOrg + "%' ";
    } 
    if (httpServletRequest.getParameter("htrq") != null) {
      if (databaseType.indexOf("mysql") >= 0) {
        where = String.valueOf(where) + " and empChangePO.empChangeDate between '" + beginDate + 
          "' and '" + endDate + "'";
      } else {
        where = String.valueOf(where) + 
          " and empChangePO.empChangeDate between JSDB.FN_STRTODATE('" + 
          beginDate + "','L') and JSDB.FN_STRTODATE('" + 
          endDate + "','L')";
      } 
      httpServletRequest.setAttribute("htrqchecked", "1");
    } 
    if (searchChangeChangeType != null && 
      !"0".equals(searchChangeChangeType) && 
      !"".equals(searchChangeChangeType))
      where = String.valueOf(where) + " and empChangePO.empChangeChangeType='" + 
        searchChangeChangeType + "'"; 
    where = String.valueOf(where) + " order by empChangePO.empChangeDate desc";
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "empChangePO.empChangeId,empChangePO.empChangeDate,empPO.empName,orgPO.orgName,orgPO2.orgName,dutyPO.dutyName,dutyPO2.dutyName,empChangeTypePO.empChangeType", 
        "com.js.oa.hr.personnelmanager.po.EmployeeChangePO empChangePO,com.js.system.vo.usermanager.EmployeeVO empPO,com.js.system.vo.organizationmanager.OrganizationVO orgPO,com.js.system.vo.organizationmanager.OrganizationVO orgPO2,com.js.oa.hr.officemanager.po.DutyPO dutyPO,com.js.oa.hr.officemanager.po.DutyPO dutyPO2,com.js.oa.hr.personnelmanager.po.EmployeeChangeTypePO empChangeTypePO", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("beginDate", beginDate);
    httpServletRequest.setAttribute("endDate", endDate);
    httpServletRequest.setAttribute("searchChangeEmp", searchChangeEmp);
    httpServletRequest.setAttribute("searchChangeOldOrg", 
        searchChangeOldOrg);
    httpServletRequest.setAttribute("searchChangeNewOrg", 
        searchChangeNewOrg);
    httpServletRequest.setAttribute("searchChangeChangeType", 
        searchChangeChangeType);
    httpServletRequest.setAttribute("personnelList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,beginDate,endDate,searchChangeEmp,searchChangeOldOrg,searchChangeNewOrg,searchChangeChangeType,htrq");
  }
  
  public void empChangTypeList(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String where = " where empChangeTypePO.domainId=" + domainId + 
      " order by empChangeTypePO.empChangeType";
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "empChangeTypePO.id,empChangeTypePO.empChangeType", 
        "com.js.oa.hr.personnelmanager.po.EmployeeChangeTypePO empChangeTypePO", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("empChangeTypelList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  public void empChangTypeList(HttpServletRequest httpServletRequest, int offsetCopy) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String where = " where empChangeTypePO.domainId=" + domainId + 
      " order by empChangeTypePO.empChangeType";
    int pageSize = 15;
    int offset = offsetCopy;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "empChangeTypePO.id,empChangeTypePO.empChangeType", 
        "com.js.oa.hr.personnelmanager.po.EmployeeChangeTypePO empChangeTypePO", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("empChangeTypelList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
  
  public void empChangSearchExport(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String beginDate = String.valueOf(httpServletRequest.getParameter("beginDate")) + 
      " 00:00:00";
    String endDate = String.valueOf(httpServletRequest.getParameter("endDate")) + 
      " 23:59:59";
    String searchChangeEmp = httpServletRequest.getParameter(
        "searchChangeEmp");
    String searchChangeOldOrg = httpServletRequest.getParameter(
        "searchChangeOldOrg");
    String searchChangeNewOrg = httpServletRequest.getParameter(
        "searchChangeNewOrg");
    String searchChangeChangeType = httpServletRequest.getParameter(
        "searchChangeChangeType");
    String exportids = httpServletRequest.getParameter("idvalues");
    String where = "";
    String databaseType = 
      SystemCommon.getDatabaseType();
    if (exportids != null && !"".equals(exportids)) {
      exportids = exportids.substring(0, exportids.length() - 1).replace("on,", "");
    } else {
      exportids = "";
    } 
    if (databaseType.indexOf("mysql") >= 0) {
      if (exportids.equals("")) {
        where = " where empChangePO.domainId=" + domainId + " and empChangePO.empChangeEmpId =empPO.empId  and empChangePO.empChangeOldOrg=orgPO.orgId and empChangePO.empChangeNewOrg=orgPO2.orgId and empChangePO.empChangeOldDuty=dutyPO.id and empChangePO.empChangeNewDuty=dutyPO2.id and empChangePO.empChangeChangeType = empChangeTypePO.id and empPO.empName like '%" + 
          searchChangeEmp + "%' and orgPO.orgName like '%" + 
          searchChangeOldOrg + "%' and orgPO2.orgName like '%" + 
          searchChangeNewOrg + "%' ";
      } else {
        where = " where empChangePO.domainId=" + domainId + " and empChangePO.empChangeEmpId =empPO.empId and empChangePO.empChangeId in (" + exportids + ") and empChangePO.empChangeOldOrg=orgPO.orgId and empChangePO.empChangeNewOrg=orgPO2.orgId and empChangePO.empChangeOldDuty=dutyPO.id and empChangePO.empChangeNewDuty=dutyPO2.id and empChangePO.empChangeChangeType = empChangeTypePO.id and empPO.empName like '%" + 
          searchChangeEmp + "%' and orgPO.orgName like '%" + 
          searchChangeOldOrg + "%' and orgPO2.orgName like '%" + 
          searchChangeNewOrg + "%' ";
      } 
    } else if (exportids.equals("")) {
      where = " where empChangePO.domainId=" + domainId + " and empChangePO.empChangeEmpId =empPO.empId and empChangePO.empChangeOldOrg=orgPO.orgId and empChangePO.empChangeNewOrg=orgPO2.orgId and empChangePO.empChangeOldDuty=dutyPO.id and empChangePO.empChangeNewDuty=dutyPO2.id and empChangePO.empChangeChangeType = JSDB.FN_INTTOSTR(empChangeTypePO.id)  and empPO.empName like '%" + 
        searchChangeEmp + "%' and orgPO.orgName like '%" + 
        searchChangeOldOrg + "%' and orgPO2.orgName like '%" + 
        searchChangeNewOrg + "%' ";
    } else {
      where = " where empChangePO.domainId=" + domainId + " and empChangePO.empChangeEmpId =empPO.empId and empChangePO.empChangeOldOrg=orgPO.orgId and empChangePO.empChangeNewOrg=orgPO2.orgId and empChangePO.empChangeOldDuty=dutyPO.id and empChangePO.empChangeNewDuty=dutyPO2.id and empChangePO.empChangeChangeType = JSDB.FN_INTTOSTR(empChangeTypePO.id) and empChangePO.empChangeId in (" + exportids + ")  and empPO.empName like '%" + 
        searchChangeEmp + "%' and orgPO.orgName like '%" + 
        searchChangeOldOrg + "%' and orgPO2.orgName like '%" + 
        searchChangeNewOrg + "%' ";
    } 
    if (httpServletRequest.getParameter("htrq") != null) {
      if (databaseType.indexOf("mysql") >= 0) {
        where = String.valueOf(where) + " and empChangePO.empChangeDate between '" + beginDate + 
          "' and '" + endDate + "'";
      } else {
        where = String.valueOf(where) + 
          " and empChangePO.empChangeDate between JSDB.FN_STRTODATE('" + 
          beginDate + "','L') and JSDB.FN_STRTODATE('" + 
          endDate + "','L')";
      } 
      httpServletRequest.setAttribute("htrqchecked", "1");
    } 
    if (searchChangeChangeType != null && 
      !"0".equals(searchChangeChangeType) && 
      !"".equals(searchChangeChangeType))
      where = String.valueOf(where) + " and empChangePO.empChangeChangeType='" + 
        searchChangeChangeType + "'"; 
    where = String.valueOf(where) + " order by empChangePO.empChangeDate desc";
    int pageSize = 999999;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(
        "empChangePO.empChangeId,empChangePO.empChangeDate,empPO.empName,orgPO.orgName,orgPO2.orgName,dutyPO.dutyName,dutyPO2.dutyName,empChangeTypePO.empChangeType", 
        "com.js.oa.hr.personnelmanager.po.EmployeeChangePO empChangePO,com.js.system.vo.usermanager.EmployeeVO empPO,com.js.system.vo.organizationmanager.OrganizationVO orgPO,com.js.system.vo.organizationmanager.OrganizationVO orgPO2,com.js.oa.hr.officemanager.po.DutyPO dutyPO,com.js.oa.hr.officemanager.po.DutyPO dutyPO2,com.js.oa.hr.personnelmanager.po.EmployeeChangeTypePO empChangeTypePO", 
        where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("beginDate", beginDate);
    httpServletRequest.setAttribute("endDate", endDate);
    httpServletRequest.setAttribute("searchChangeEmp", searchChangeEmp);
    httpServletRequest.setAttribute("searchChangeOldOrg", searchChangeOldOrg);
    httpServletRequest.setAttribute("searchChangeNewOrg", searchChangeNewOrg);
    httpServletRequest.setAttribute("searchChangeChangeType", searchChangeChangeType);
    httpServletRequest.setAttribute("personnelList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,beginDate,endDate,searchChangeEmp,searchChangeOldOrg,searchChangeNewOrg,searchChangeChangeType,htrq");
  }
}
