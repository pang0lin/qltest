package com.js.oa.audit.service;

import com.js.oa.audit.bean.AuditLogEJBBean;
import com.js.oa.audit.po.AuditLog;

public class AuditLogBD {
  public Long saveAuditLog(AuditLog auditLog) throws Exception {
    Long re = null;
    AuditLogEJBBean auditLogEJBBean = new AuditLogEJBBean();
    re = auditLogEJBBean.saveAuditLog(auditLog);
    return re;
  }
}
