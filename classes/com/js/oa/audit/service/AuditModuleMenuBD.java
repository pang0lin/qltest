package com.js.oa.audit.service;

import com.js.oa.audit.bean.AuditModuleMenuEJBBean;
import com.js.oa.audit.po.AuditModuleMenuPO;
import javax.servlet.http.HttpServletRequest;

public class AuditModuleMenuBD {
  public Long saveAuditModuleMenu(AuditModuleMenuPO auditModuleMenuPO) throws Exception {
    Long re = new Long(0L);
    AuditModuleMenuEJBBean auditModuleMenuEJBBean = new AuditModuleMenuEJBBean();
    re = auditModuleMenuEJBBean.saveAuditModuleMenu(auditModuleMenuPO);
    return re;
  }
  
  public AuditModuleMenuPO loadAuditModuleMenu(long auditModuleMenuId) throws Exception {
    AuditModuleMenuPO re = null;
    AuditModuleMenuEJBBean auditModuleMenuEJBBean = new AuditModuleMenuEJBBean();
    re = auditModuleMenuEJBBean.loadAuditModuleMenu(auditModuleMenuId);
    return re;
  }
  
  public void auditModuleMenu(String auditModuleMenuId, String logId, String checked, String operationType, HttpServletRequest request) throws Exception {
    AuditModuleMenuEJBBean auditModuleMenuEJBBean = new AuditModuleMenuEJBBean();
    auditModuleMenuEJBBean.auditModuleMenu(auditModuleMenuId, logId, checked, operationType, request);
  }
}
