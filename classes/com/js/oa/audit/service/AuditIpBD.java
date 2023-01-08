package com.js.oa.audit.service;

import com.js.oa.audit.bean.AuditIpEJBBean;
import com.js.oa.audit.po.AuditIpPO;
import javax.servlet.http.HttpServletRequest;

public class AuditIpBD {
  public Long saveAuditIp(AuditIpPO auditIpPO) throws Exception {
    Long auditOrgGroupId = null;
    AuditIpEJBBean auditIpEJBBean = new AuditIpEJBBean();
    auditOrgGroupId = auditIpEJBBean.saveAuditIp(auditIpPO);
    return auditOrgGroupId;
  }
  
  public AuditIpPO loadAuditIp(long auditIpId) throws Exception {
    AuditIpPO re = null;
    AuditIpEJBBean auditIpEJBBean = new AuditIpEJBBean();
    re = auditIpEJBBean.loadAuditIp(auditIpId);
    return re;
  }
  
  public void auditIp(String auditIpId, String logId, String checked, String operationType, HttpServletRequest request) throws Exception {
    AuditIpEJBBean auditIpEJBBean = new AuditIpEJBBean();
    auditIpEJBBean.auditIp(auditIpId, logId, checked, operationType, request);
  }
}
