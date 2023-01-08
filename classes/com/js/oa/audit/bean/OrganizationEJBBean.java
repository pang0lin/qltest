package com.js.oa.audit.bean;

import com.js.oa.audit.po.AuditLog;
import com.js.oa.audit.po.OrganizationPO;
import com.js.util.hibernate.HibernateBase;
import java.util.Date;
import java.util.List;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Query;

public class OrganizationEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public String getOrgId(String logId) throws Exception {
    String orgId = "";
    begin();
    try {
      Query query = this.session.createQuery(
          " select aaa.orgId from  com.js.oa.audit.po.OrganizationPO aaa  where aaa.logId=" + 
          
          logId);
      List<String> list = query.list();
      if (list.size() > 0)
        orgId = list.get(0); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return orgId;
  }
  
  public void setLog(String logId, String empId, String empName, String auditStatus) throws Exception {
    begin();
    try {
      AuditLog auditLog = (AuditLog)this.session.load(AuditLog.class, Long.valueOf(logId));
      auditLog.setCheckEmpid(Long.valueOf(empId));
      auditLog.setCheckEmpname(empName);
      auditLog.setCheckTime(new Date());
      auditLog.setAuditStatus(Integer.valueOf(auditStatus));
      auditLog.setIschecked(Integer.valueOf(1));
      this.session.update(auditLog);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
  }
  
  public AuditLog getAuditLog(String logId) throws Exception {
    AuditLog auditLog = null;
    begin();
    try {
      auditLog = (AuditLog)this.session.load(AuditLog.class, Long.valueOf(logId));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
    } 
    return auditLog;
  }
  
  public OrganizationPO getOrgByOrgId(String logId) throws Exception {
    OrganizationPO organizationVO = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa from com.js.oa.audit.po.OrganizationPO aaa where aaa.logId='" + logId + "'");
      organizationVO = (OrganizationPO)query.uniqueResult();
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return organizationVO;
  }
  
  public List getParentOrgListTemp(String orgId, String domainId, String range) throws Exception {
    List list = null;
    begin();
    String sql = "";
    try {
      if (orgId != null && !"null".equals(orgId) && !"".equals(orgId)) {
        sql = "from com.js.system.vo.organizationmanager.OrganizationVO org where  org.orgParentOrgId=" + orgId + " and org.orgStatus<>4 and org.domainId=" + domainId + " ";
      } else {
        sql = "from com.js.system.vo.organizationmanager.OrganizationVO org where  org.orgParentOrgId=0 and org.orgStatus<>4 and org.domainId=" + domainId + " ";
      } 
      if (range != null && !range.equals("") && !range.equals("0"))
        range.equals("*0*"); 
      sql = String.valueOf(sql) + " order by org.orgIdString";
      Query query = this.session.createQuery(sql);
      list = query.list();
      this.session.flush();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public String getRootOrg() throws Exception {
    String org = "";
    begin();
    try {
      Query query = this.session.createQuery(
          " select aaa.orgName from  com.js.system.vo.organizationmanager.OrganizationVO aaa  where aaa.orgParentOrgId=-1");
      List<String> list = query.list();
      if (list.size() > 0)
        org = list.get(0); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return org;
  }
}
