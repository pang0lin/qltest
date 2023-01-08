package com.js.oa.audit.bean;

import com.js.oa.audit.po.AuditManager;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.StringSplit;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.HibernateException;

public class AuditManagerEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public boolean addAuditManager(AuditManager auditManager) throws HibernateException {
    begin();
    boolean result = true;
    try {
      if (auditManager.getEmpIds() != null && !"".equals(auditManager.getEmpIds())) {
        String empIds = StringSplit.splitWith(auditManager.getEmpIds(), "$", "*@");
        if (empIds != null && !"".equals(empIds) && empIds.length() > 2 && 
          auditManager.getEmpNames() != null && !"".equals(auditManager.getEmpNames()) && 
          auditManager.getEmpNames().length() > 2) {
          empIds = empIds.substring(1, empIds.length() - 1);
          String empNames = auditManager.getEmpNames();
          empNames = empNames.substring(0, empNames.length() - 1);
          String[] empIdsArr = empIds.split("\\$\\$");
          String[] empNamesArr = empNames.split("\\,");
          for (int i = 0; i < empIdsArr.length; i++) {
            AuditManager savePO = new AuditManager();
            savePO.setEmpId(Long.valueOf(empIdsArr[i]));
            savePO.setEmpName(empNamesArr[i]);
            int rightScopeType = auditManager.getRightScopeType().intValue();
            savePO.setRightScopeType(Integer.valueOf(rightScopeType));
            if (rightScopeType == 0) {
              savePO.setRightScopeScope("");
              savePO.setRightScope("本人");
            } 
            if (rightScopeType == 1) {
              savePO.setRightScopeScope("");
              savePO.setRightScope("本部门");
            } 
            if (rightScopeType == 2) {
              savePO.setRightScopeScope("");
              savePO.setRightScope("本部门及下属部门");
            } 
            if (rightScopeType == 3) {
              savePO.setRightScopeScope("");
              savePO.setRightScope("全单位");
            } 
            if (rightScopeType == 4) {
              savePO.setRightScopeScope(auditManager.getRightScopeScope());
              savePO.setRightScope(auditManager.getRightScope());
            } 
            savePO.setCreatedEmp(Long.valueOf("-99"));
            savePO.setCreatedEmpName("审计管理员");
            savePO.setCreatedOrg(Long.valueOf("0"));
            this.session.save(savePO);
            this.session.flush();
          } 
        } 
      } 
      this.session.flush();
    } catch (Exception x) {
      x.printStackTrace();
      result = false;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public boolean delAuditManager(String id) throws Exception {
    boolean result = true;
    begin();
    try {
      this.session.delete("from com.js.oa.audit.po.AuditManager po where po.managerId=" + 
          id);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      result = false;
      throw e;
    } finally {
      this.session.close();
      result = false;
    } 
    return result;
  }
  
  public AuditManager loadAuditManger(String id) throws Exception {
    AuditManager po = null;
    begin();
    try {
      po = (AuditManager)this.session.load(AuditManager.class, 
          new Long(id));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return po;
  }
  
  public boolean updateAuditManager(AuditManager auditManager) throws Exception {
    boolean result = true;
    begin();
    try {
      int rightScopeType = auditManager.getRightScopeType().intValue();
      if (rightScopeType == 0) {
        auditManager.setRightScopeScope("");
        auditManager.setRightScope("本人");
      } 
      if (rightScopeType == 1) {
        auditManager.setRightScopeScope("");
        auditManager.setRightScope("本部门");
      } 
      if (rightScopeType == 2) {
        auditManager.setRightScopeScope("");
        auditManager.setRightScope("本部门及下属部门");
      } 
      if (rightScopeType == 3) {
        auditManager.setRightScopeScope("");
        auditManager.setRightScope("全单位");
      } 
      this.session.update(auditManager);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      result = false;
      throw e;
    } finally {
      this.session.close();
      result = false;
    } 
    return result;
  }
}
