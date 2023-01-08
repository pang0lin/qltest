package com.js.oa.audit.bean;

import com.js.oa.audit.po.AuditManager;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface AuditManagerEJB extends EJBObject {
  boolean addAuditManager(AuditManager paramAuditManager) throws HibernateException;
  
  boolean delAuditManager(String paramString) throws Exception;
  
  AuditManager loadAuditManger(String paramString) throws Exception;
  
  boolean updateAuditManager(AuditManager paramAuditManager) throws Exception;
}
