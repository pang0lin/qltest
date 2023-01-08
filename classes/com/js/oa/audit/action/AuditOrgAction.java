package com.js.oa.audit.action;

import com.active.e_uc.user.po.TblDepartment;
import com.active.e_uc.user.service.TblDepartmentBD;
import com.js.oa.audit.bean.AuditMsRemindEJBBean;
import com.js.oa.audit.po.AuditLog;
import com.js.oa.audit.po.OrganizationPO;
import com.js.oa.audit.service.OrganizationBD;
import com.js.oa.security.log.service.LogBD;
import com.js.system.service.organizationmanager.OrganizationBD;
import com.js.system.vo.organizationmanager.OrganizationVO;
import com.js.util.util.FillBean;
import com.js.util.util.ReadActiveXml;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.hibernate.HibernateException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AuditOrgAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    HttpSession session = httpServletRequest.getSession(true);
    String action = httpServletRequest.getParameter("action");
    String returns = httpServletRequest.getParameter("returns");
    String domainId = session.getAttribute("domainId").toString();
    String userId = session.getAttribute("userId").toString();
    String comeFlag = (httpServletRequest.getParameter("comeFlag") == null) ? "" : httpServletRequest.getParameter("comeFlag");
    httpServletRequest.setAttribute("comeFlag", comeFlag);
    AuditOrgActionForm organizationForm = (AuditOrgActionForm)actionForm;
    OrganizationBD organizationBD = new OrganizationBD();
    if ("getOrgDetail".equals(action)) {
      String logId = httpServletRequest.getParameter("logId");
      String orgId = (new OrganizationBD()).getOrgId(logId);
      String type = httpServletRequest.getParameter("type");
      String range = httpServletRequest.getParameter("range");
      OrganizationPO vo = null;
      if (logId != null && !"null".equals(logId) && !"".equals(logId)) {
        vo = organizationBD.getOrgByOrgId(logId);
        BeanUtils.copyProperties(organizationForm, vo);
      } 
      String id = String.valueOf(organizationForm.getOrgParentOrgId());
      List<OrganizationVO> list = organizationBD.getParentOrgListTemp(id, domainId, range);
      if (vo != null) {
        httpServletRequest.setAttribute("codeTemp", Integer.valueOf(vo.getOrgOrderCode()));
        httpServletRequest.setAttribute("orgIdTemp", vo.getOrgId().toString());
      } 
      httpServletRequest.setAttribute("orgList", list);
      int selectedValue = 0;
      if (list.size() > 0)
        selectedValue = ((OrganizationVO)list.get(list.size() - 1)).getOrgOrderCode(); 
      String pString = vo.getOrgNameString();
      if (pString.indexOf(".") > 0) {
        String[] strings = pString.split("\\.");
        httpServletRequest.setAttribute("orgParentOrgName", strings[strings.length - 2]);
      } else {
        httpServletRequest.setAttribute("orgParentOrgName", organizationBD.getRootOrg());
      } 
      if (vo.getOrgIdString().indexOf("orgId") >= 0) {
        String[] orgIdStrings = vo.getOrgIdString().split("\\$");
        String newIdString = "";
        for (int i = 0; i < orgIdStrings.length - 2; i++)
          newIdString = String.valueOf(newIdString) + orgIdStrings[i] + "$"; 
        vo.setOrgIdString(newIdString);
      } 
      httpServletRequest.setAttribute("operate", vo.getOperate());
      httpServletRequest.setAttribute("selectedValue", Integer.valueOf(selectedValue));
      httpServletRequest.setAttribute("parentIdString", vo.getOrgIdString());
      httpServletRequest.setAttribute("range", httpServletRequest.getParameter("range"));
      httpServletRequest.setAttribute("type", type);
      return actionMapping.findForward("orgDetail");
    } 
    if ("yes".equals(action)) {
      String operate = httpServletRequest.getParameter("operate");
      String logId = httpServletRequest.getParameter("logId");
      String empId = session.getAttribute("userId").toString();
      String empName = session.getAttribute("userName").toString();
      String op = "";
      AuditLog auditLog = organizationBD.getAuditLog(logId);
      OrganizationBD orgBD = new OrganizationBD();
      String name = "", oprType = "";
      TblDepartment tblDepartment = new TblDepartment();
      TblDepartmentBD tblDepartmentBD = new TblDepartmentBD();
      OrganizationPO vo = organizationBD.getOrgByOrgId(logId);
      if ("insert".equals(operate)) {
        op = "新增";
        OrganizationVO organizationVO = (OrganizationVO)FillBean.transformOTO(vo, OrganizationVO.class);
        String currentOrderCode = (new StringBuilder(String.valueOf(vo.getOrgOrderCode()))).toString();
        String parentIdString = organizationForm.getParentIdString();
        organizationVO.setOrgIdString(parentIdString);
        Integer sort = new Integer(organizationForm.getOrgSort());
        organizationVO.setDomainId(session.getAttribute("domainId").toString());
        if ("iactive".equals(ReadActiveXml.getReadActive().getUse())) {
          int b = (int)organizationVO.getOrgParentOrgId();
          if (b != 0) {
            String sa = orgBD.findOrgSerial(b);
            tblDepartment.setPid(tblDepartmentBD.findID(sa));
          } else {
            tblDepartment.setPid(0);
          } 
          tblDepartment.setName(organizationVO.getOrgName());
          tblDepartment.setOrgid(1);
          tblDepartment.setGrade(0);
          tblDepartment.setUrl(organizationVO.getOrgSerial());
          tblDepartment.setShowChildUser((byte)1);
          try {
            tblDepartmentBD.addDepartment(tblDepartment);
          } catch (HibernateException e) {
            e.printStackTrace();
          } 
        } 
        boolean flag = false;
        if (organizationVO.getOrgId() == null || organizationVO.getOrgId().equals("")) {
          flag = orgBD.add(organizationVO, currentOrderCode, parentIdString, sort);
        } else {
          flag = orgBD.add(organizationVO, currentOrderCode, parentIdString, sort);
        } 
        name = organizationVO.getOrgName();
        oprType = "1";
      } else if ("update".equals(operate)) {
        op = "修改";
        OrganizationVO organizationVO = (OrganizationVO)FillBean.transformOTO(vo, OrganizationVO.class);
        if ("iactive".equals(ReadActiveXml.getReadActive().getUse())) {
          String sa = orgBD.findOrgSerial(organizationVO.getOrgId().longValue());
          try {
            tblDepartment = tblDepartmentBD.findTblDepartment(sa);
            int b = (int)organizationVO.getOrgParentOrgId();
            if (b != 0) {
              String sa2 = orgBD.findOrgSerial(b);
              tblDepartment.setPid(tblDepartmentBD.findID(sa2));
            } else {
              tblDepartment.setPid(0);
            } 
            tblDepartment.setName(organizationVO.getOrgName());
            tblDepartment.setOrgid(1);
            tblDepartment.setGrade(0);
            tblDepartment.setUrl(organizationVO.getOrgSerial());
            tblDepartment.setShowChildUser((byte)1);
            tblDepartmentBD.updateDepartment(tblDepartment);
          } catch (HibernateException e) {
            e.printStackTrace();
          } 
        } 
        String currentOrderCode = organizationForm.getCurrentOrderCode();
        String parentIdString = organizationForm.getParentIdString();
        Integer sort = new Integer(organizationForm.getOrgSort());
        String hasChanged = httpServletRequest.getParameter("hasChanged");
        orgBD.update(organizationVO, currentOrderCode, parentIdString, sort, hasChanged);
        orgBD.setOrder(vo.getOrgIdString(), (new StringBuilder(String.valueOf(vo.getOrgOrderCode()))).toString(), (String)vo.getOrgId());
        name = organizationVO.getOrgName();
        oprType = "2";
      } else if ("updateRoot".equals(operate)) {
        op = "修改";
        OrganizationVO organizationVO = (OrganizationVO)FillBean.transformOTO(vo, OrganizationVO.class);
        orgBD.updateRootDept(organizationVO);
        oprType = "2";
        name = organizationVO.getOrgName();
      } else if ("jinyong-0".equals(operate)) {
        op = "禁用";
        if ("iactive".equals(ReadActiveXml.getReadActive().getUse())) {
          String sa = orgBD.findOrgSerial(organizationForm.getOrgId());
          tblDepartmentBD.delDepartment(sa);
        } 
        name = orgBD.delete(organizationForm.getOrgId(), "0");
        oprType = "3";
      } else if ("shanchu-1".equals(operate)) {
        op = "删除";
        String orgId = httpServletRequest.getParameter("orgId");
        orgBD.delete(Long.parseLong(orgId), "1");
      } else {
        op = "恢复";
        name = orgBD.reDept(organizationForm.getOrgId());
        oprType = "3";
      } 
      if (!"".equals(operate)) {
        LogBD logBD = new LogBD();
        Date date = new Date();
        logBD.log((String)auditLog.getSubmitEmpid(), auditLog.getSubmitEmpname(), (String)auditLog.getSubmitOrgid(), 
            "system_org", "系统管理", date, date, oprType, name, session.getAttribute("userIP").toString(), domainId);
      } 
      AuditMsRemindEJBBean msRemindBeann = new AuditMsRemindEJBBean();
      String typestring = "通过审核";
      msRemindBeann.auditRemind(Long.valueOf(session.getAttribute("userId").toString()), session.getAttribute("orgId").toString(), session.getAttribute("userName").toString(), 
          3, 1, new Date(), "您" + op + "的组织机构“" + vo.getOrgName() + "”" + typestring + "！", "audit", Long.valueOf(logId).longValue(), "", (String)auditLog.getSubmitEmpid());
      (new OrganizationBD()).setLog(logId, empId, empName, "1");
      return actionMapping.findForward("close");
    } 
    if ("no".equals(action)) {
      String logId = httpServletRequest.getParameter("logId");
      OrganizationPO vo = organizationBD.getOrgByOrgId(logId);
      AuditLog auditLog = organizationBD.getAuditLog(logId);
      String op = "";
      if ("update".equals(vo.getOperate()) || "updateRoot".equals(vo.getOperate())) {
        op = "修改";
      } else if ("insert".equals(vo.getOperate())) {
        op = "新增";
      } else if ("jinyong-0".equals(vo.getOperate())) {
        op = "禁用";
      } else if ("shanchu-1".equals(vo.getOperate())) {
        op = "删除";
      } else {
        op = "恢复";
      } 
      AuditMsRemindEJBBean msRemindBeann = new AuditMsRemindEJBBean();
      String typestring = "未通过审核";
      msRemindBeann.auditRemind(Long.valueOf(session.getAttribute("userId").toString()), session.getAttribute("orgId").toString(), session.getAttribute("userName").toString(), 
          3, 1, new Date(), "您" + op + "的组织机构“" + vo.getOrgName() + "”" + typestring + "！", "audit", Long.valueOf(logId).longValue(), "", (String)auditLog.getSubmitEmpid());
      String empId = session.getAttribute("userId").toString();
      String empName = session.getAttribute("userName").toString();
      (new OrganizationBD()).setLog(logId, empId, empName, "0");
      return actionMapping.findForward("close");
    } 
    if ("view".equals(action)) {
      String range = "0";
      httpServletRequest.setAttribute("range", range);
      String tag = "view";
      if (returns != null && returns.equals("1"))
        tag = "tree"; 
      return actionMapping.findForward(tag);
    } 
    return null;
  }
}
