package com.js.oa.audit.service;

import com.js.oa.audit.bean.AuditManagerEJBHome;
import com.js.oa.audit.po.AuditManager;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;

public class AuditManagerBD {
  public boolean addAuditManager(AuditManager auditManager) throws Exception {
    Boolean flag = null;
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("AuditManagerEJB", "AuditManagerEJBLocal", AuditManagerEJBHome.class);
      p.put(auditManager, AuditManager.class);
      flag = (Boolean)ejbProxy.invoke("addAuditManager", p.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return flag.booleanValue();
  }
  
  public boolean delAuditManager(String id) throws Exception {
    Boolean flag = null;
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("AuditManagerEJB", "AuditManagerEJBLocal", AuditManagerEJBHome.class);
      p.put(id, String.class);
      flag = (Boolean)ejbProxy.invoke("delAuditManager", p.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return flag.booleanValue();
  }
  
  public AuditManager loadAuditManger(String id) throws Exception {
    AuditManager po = null;
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("AuditManagerEJB", "AuditManagerEJBLocal", AuditManagerEJBHome.class);
      p.put(id, String.class);
      po = (AuditManager)ejbProxy.invoke("loadAuditManger", p.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return po;
  }
  
  public boolean updateAuditManager(AuditManager auditManager) throws Exception {
    Boolean flag = null;
    ParameterGenerator p = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("AuditManagerEJB", "AuditManagerEJBLocal", AuditManagerEJBHome.class);
      p.put(auditManager, AuditManager.class);
      flag = (Boolean)ejbProxy.invoke("updateAuditManager", p.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {}
    return flag.booleanValue();
  }
}
