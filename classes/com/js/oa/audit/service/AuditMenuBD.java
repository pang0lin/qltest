package com.js.oa.audit.service;

import com.js.oa.audit.bean.AuditMenuEJBBean;
import com.js.oa.audit.po.AuditMenuPO;
import javax.servlet.http.HttpServletRequest;

public class AuditMenuBD {
  public Long saveAuditMenu(AuditMenuPO auditMenuPO) throws Exception {
    AuditMenuEJBBean auditMenuEJBBean = new AuditMenuEJBBean();
    Long re = auditMenuEJBBean.saveAuditMenu(auditMenuPO);
    return re;
  }
  
  public AuditMenuPO loadAuditMenu(long auditMenuId) throws Exception {
    AuditMenuPO re = null;
    AuditMenuEJBBean auditMenuEJBBean = new AuditMenuEJBBean();
    re = auditMenuEJBBean.loadAuditMenu(auditMenuId);
    return re;
  }
  
  public void auditMenu(String auditMenuId, String logId, String checked, String operationType, HttpServletRequest request) throws Exception {
    AuditMenuEJBBean auditMenuEJBBean = new AuditMenuEJBBean();
    auditMenuEJBBean.auditMenu(auditMenuId, logId, checked, operationType, request);
  }
}
