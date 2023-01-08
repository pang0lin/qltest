package com.js.oa.audit.service;

import com.js.oa.audit.bean.AuditOrgGroupEJBBean;
import com.js.oa.audit.po.AuditOrgGroup;
import javax.servlet.http.HttpServletRequest;

public class AuditOrgGroupBD {
  public Long saveAuditOrgGroup(AuditOrgGroup auditOrgGroup) throws Exception {
    Long re = new Long(0L);
    AuditOrgGroupEJBBean auditOrgGroupEJBBean = new AuditOrgGroupEJBBean();
    re = auditOrgGroupEJBBean.saveAuditOrgGroup(auditOrgGroup);
    return re;
  }
  
  public AuditOrgGroup loadAuditOrgGroup(long auditOrgGroupId) throws Exception {
    AuditOrgGroup re = null;
    AuditOrgGroupEJBBean auditOrgGroupEJBBean = new AuditOrgGroupEJBBean();
    re = auditOrgGroupEJBBean.loadAuditOrgGroup(auditOrgGroupId);
    return re;
  }
  
  public void auditOrgGroup(String auditOrgGroupId, String logId, String checked, String operationType, HttpServletRequest request) throws Exception {
    AuditOrgGroupEJBBean auditOrgGroupEJBBean = new AuditOrgGroupEJBBean();
    auditOrgGroupEJBBean.auditOrgGroup(auditOrgGroupId, logId, checked, operationType, request);
  }
}
