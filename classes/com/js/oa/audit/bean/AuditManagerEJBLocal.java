package com.js.oa.audit.bean;

import com.js.oa.audit.po.AuditManager;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface AuditManagerEJBLocal extends EJBLocalObject {
  boolean addAuditManager(AuditManager paramAuditManager) throws HibernateException;
  
  boolean delAuditManager(String paramString) throws Exception;
  
  AuditManager loadAuditManger(String paramString) throws Exception;
  
  boolean updateAuditManager(AuditManager paramAuditManager) throws Exception;
}
