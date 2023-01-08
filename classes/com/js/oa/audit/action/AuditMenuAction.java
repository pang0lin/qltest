package com.js.oa.audit.action;

import com.js.oa.audit.po.AuditMenuPO;
import com.js.oa.audit.service.AuditMenuBD;
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

public class AuditMenuAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws NumberFormatException, Exception {
    String action = request.getParameter("action");
    AuditMenuActionForm auditMenuActionForm = (AuditMenuActionForm)actionForm;
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
      request.setAttribute("flag", flag);
      if ("detail".equals(flag))
        tag = "shenjiDetail"; 
      sql = "select po.auditMenuId,po.menuId from com.js.oa.audit.po.AuditMenuPO po where po.auditLogId=" + id;
      msList = msBD.getListByYourSQL(sql);
      if (msList != null && msList.size() != 0)
        for (int i = 0; i < msList.size(); i++) {
          Object[] obj = msList.get(i);
          id = obj[0].toString();
        }  
      AuditMenuBD auditBD = new AuditMenuBD();
      AuditMenuPO auditMenuPO = auditBD.loadAuditMenu(Long.valueOf(id).longValue());
      if (auditMenuPO != null) {
        auditMenuActionForm.setAuditMenuId(auditMenuPO.getAuditMenuId());
        auditMenuActionForm.setMenuId(auditMenuPO.getMenuId());
        auditMenuActionForm.setMenuName(auditMenuPO.getMenuName());
        auditMenuActionForm.setMenuOrder(auditMenuPO.getMenuOrder());
        auditMenuActionForm.setInUse(auditMenuPO.getInUse());
        String userOrgGroup = "";
        if (auditMenuPO.getMenuViewUser() != null)
          userOrgGroup = String.valueOf(userOrgGroup) + auditMenuPO.getMenuViewUser(); 
        if (auditMenuPO.getMenuViewOrg() != null)
          userOrgGroup = String.valueOf(userOrgGroup) + auditMenuPO.getMenuViewOrg(); 
        if (auditMenuPO.getMenuViewGroup() != null)
          userOrgGroup = String.valueOf(userOrgGroup) + auditMenuPO.getMenuViewGroup(); 
        auditMenuActionForm.setUserOrgGroup(userOrgGroup);
        auditMenuActionForm.setMenuView(auditMenuPO.getMenuView());
        auditMenuActionForm.setOperationType(auditMenuPO.getOperationType());
        auditMenuActionForm.setAuditLogId(auditMenuPO.getAuditLogId());
        String operationTypeCh = auditMenuPO.getOperationType();
        if ("insert".equals(operationTypeCh))
          operationTypeCh = "新增"; 
        if ("update".equals(operationTypeCh))
          operationTypeCh = "修改"; 
        if ("delete".equals(operationTypeCh))
          operationTypeCh = "删除"; 
        if ("enable".equals(operationTypeCh))
          operationTypeCh = "启用"; 
        if ("disable".equals(operationTypeCh))
          operationTypeCh = "禁用"; 
        request.setAttribute("operationTypeCh", operationTypeCh);
        request.setAttribute("operationType", auditMenuPO.getOperationType());
        request.setAttribute("menuId", auditMenuPO.getMenuId());
        String userName = session.getAttribute("userName").toString();
        String orgName = session.getAttribute("orgName").toString();
        LogBD logBD = new LogBD();
        Date date = new Date();
        logBD.log(userId, userName, orgName, "system_IP", "菜单审计管理", date, date, "2", "审计", session.getAttribute("userIP").toString(), domainId);
      } 
    } 
    if (action.equals("shenji")) {
      String auditMenuId = request.getParameter("auditMenuId");
      String checked = request.getParameter("checked");
      String auditLogId = request.getParameter("auditLogId");
      String operationType = request.getParameter("operationType");
      AuditMenuBD auditMenuBD = new AuditMenuBD();
      auditMenuBD.auditMenu(auditMenuId, auditLogId, checked, operationType, request);
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
