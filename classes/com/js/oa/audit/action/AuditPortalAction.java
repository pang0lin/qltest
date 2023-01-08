package com.js.oa.audit.action;

import com.js.oa.audit.po.AuditPortalPO;
import com.js.oa.audit.service.AuditPortalBD;
import com.js.oa.message.service.MsManageBD;
import com.js.oa.security.log.service.LogBD;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AuditPortalAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws NumberFormatException, Exception {
    String action = request.getParameter("action");
    AuditPortalActionForm auditPortalActionForm = (AuditPortalActionForm)actionForm;
    HttpSession session = request.getSession(true);
    String tag = "";
    String userId = session.getAttribute("userId").toString();
    String domainId = session.getAttribute("domainId").toString();
    if (action.equals("forshenji")) {
      tag = "shenji";
      String id = request.getParameter("id");
      String flag = request.getParameter("flag");
      MsManageBD msBD = new MsManageBD();
      List<Object[]> msList = null;
      String sql = "select po.logId,po.ischecked from com.js.oa.audit.po.AuditLog po where po.logId=" + id;
      msList = msBD.getListByYourSQL(sql);
      if (msList != null && msList.size() != 0)
        for (int i = 0; i < msList.size(); i++) {
          Object[] obj = msList.get(i);
          String ischecked = obj[1].toString();
          if ("1".equals(ischecked))
            tag = "shenjiDetail"; 
        }  
      if ("detail".equals(flag))
        tag = "shenjiDetail"; 
      request.setAttribute("flag", flag);
      sql = "select po.auditPortalId,po.portalId from com.js.oa.audit.po.AuditPortalPO po where po.auditLogId=" + id;
      msList = msBD.getListByYourSQL(sql);
      if (msList != null && msList.size() != 0)
        for (int i = 0; i < msList.size(); i++) {
          Object[] obj = msList.get(i);
          id = obj[0].toString();
        }  
      AuditPortalBD auditBD = new AuditPortalBD();
      AuditPortalPO auditPortalPO = auditBD.loadAuditPortal(Long.valueOf(id).longValue());
      if (auditPortalPO != null) {
        auditPortalActionForm.setAuditPortalId(auditPortalPO.getAuditPortalId());
        auditPortalActionForm.setPortalId(auditPortalPO.getPortalId());
        auditPortalActionForm.setLayoutName(auditPortalPO.getLayoutName());
        auditPortalActionForm.setViewRangeName(auditPortalPO.getViewRangeName());
        auditPortalActionForm.setXmlFile(auditPortalPO.getXmlFile());
        auditPortalActionForm.setMyColumn(auditPortalPO.getMyColumn());
        auditPortalActionForm.setDomainId(auditPortalPO.getDomainId());
        auditPortalActionForm.setMenuName(auditPortalPO.getMenuName());
        auditPortalActionForm.setShowOrder(auditPortalPO.getShowOrder());
        auditPortalActionForm.setMenuType(auditPortalPO.getMenuType());
        auditPortalActionForm.setViewOrg(auditPortalPO.getViewOrg());
        auditPortalActionForm.setViewUser(auditPortalPO.getViewUser());
        auditPortalActionForm.setViewGroup(auditPortalPO.getViewGroup());
        auditPortalActionForm.setCreatedEmp(auditPortalPO.getCreatedEmp());
        auditPortalActionForm.setCreatedOrg(auditPortalPO.getCreatedOrg());
        auditPortalActionForm.setIsUrl(auditPortalPO.getIsUrl());
        auditPortalActionForm.setUrl(auditPortalPO.getUrl());
        auditPortalActionForm.setOperationType(auditPortalPO.getOperationType());
        auditPortalActionForm.setAuditLogId(auditPortalPO.getAuditLogId());
        String userOrgGroup = "";
        if (auditPortalPO.getViewUser() != null)
          userOrgGroup = String.valueOf(userOrgGroup) + auditPortalPO.getViewUser(); 
        if (auditPortalPO.getViewOrg() != null)
          userOrgGroup = String.valueOf(userOrgGroup) + auditPortalPO.getViewOrg(); 
        if (auditPortalPO.getViewGroup() != null)
          userOrgGroup = String.valueOf(userOrgGroup) + auditPortalPO.getViewGroup(); 
        auditPortalActionForm.setUserOrgGroup(userOrgGroup);
        String operationTypeCh = auditPortalPO.getOperationType();
        if ("insert".equals(operationTypeCh))
          operationTypeCh = "新增"; 
        if ("update".equals(operationTypeCh))
          operationTypeCh = "修改"; 
        if ("delete".equals(operationTypeCh))
          operationTypeCh = "删除"; 
        request.setAttribute("operationTypeCh", operationTypeCh);
        request.setAttribute("operationType", auditPortalPO.getOperationType());
        request.setAttribute("portalId", auditPortalPO.getPortalId());
        String userName = session.getAttribute("userName").toString();
        String orgName = session.getAttribute("orgName").toString();
        LogBD logBD = new LogBD();
        Date date = new Date();
        logBD.log(userId, userName, orgName, "system_IP", "门户审计管理", date, date, "2", "审计", session.getAttribute("userIP").toString(), domainId);
      } 
    } 
    if (action.equals("shenji")) {
      String auditPortalId = request.getParameter("auditPortalId");
      String checked = request.getParameter("checked");
      String auditLogId = request.getParameter("auditLogId");
      String operationType = request.getParameter("operationType");
      AuditPortalBD auditPortalBD = new AuditPortalBD();
      auditPortalBD.auditPortal(auditPortalId, auditLogId, checked, operationType, request);
      tag = "close";
      request.setAttribute("done", "done");
      String flag = request.getParameter("flag");
      if (flag != null && "fromRemind".equals(flag))
        flag = "fromRemind2"; 
      request.setAttribute("flag", flag);
    } 
    return actionMapping.findForward(tag);
  }
}
