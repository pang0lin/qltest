package com.js.oa.audit.service;

import com.js.oa.audit.bean.AuditPortalEJBBean;
import com.js.oa.audit.po.AuditPortalPO;
import javax.servlet.http.HttpServletRequest;

public class AuditPortalBD {
  public Long saveAuditPortal(AuditPortalPO auditPortalPO) throws Exception {
    Long re = new Long(0L);
    AuditPortalEJBBean auditPortalEJBBean = new AuditPortalEJBBean();
    re = auditPortalEJBBean.saveAuditPortal(auditPortalPO);
    return re;
  }
  
  public AuditPortalPO loadAuditPortal(long auditPortalId) throws Exception {
    AuditPortalPO re = null;
    AuditPortalEJBBean auditPortalEJBBean = new AuditPortalEJBBean();
    re = auditPortalEJBBean.loadAuditPortal(auditPortalId);
    return re;
  }
  
  public void auditPortal(String auditPortalId, String logId, String checked, String operationType, HttpServletRequest request) throws Exception {
    AuditPortalEJBBean auditPortalEJBBean = new AuditPortalEJBBean();
    auditPortalEJBBean.auditPortal(auditPortalId, logId, checked, operationType, request);
  }
}
