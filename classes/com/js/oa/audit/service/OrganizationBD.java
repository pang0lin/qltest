package com.js.oa.audit.service;

import com.js.oa.audit.bean.OrganizationEJBBean;
import com.js.oa.audit.po.AuditLog;
import com.js.oa.audit.po.OrganizationPO;
import java.util.List;
import org.apache.log4j.Logger;

public class OrganizationBD {
  private static Logger logger = Logger.getLogger(OrganizationBD.class
      .getName());
  
  public String getOrgId(String logId) {
    String orgId = "";
    try {
      orgId = (new OrganizationEJBBean()).getOrgId(logId);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return orgId;
  }
  
  public void setLog(String logId, String empId, String empName, String auditStatus) {
    try {
      (new OrganizationEJBBean()).setLog(logId, empId, empName, auditStatus);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public AuditLog getAuditLog(String logId) {
    AuditLog auditLog = null;
    try {
      auditLog = (new OrganizationEJBBean()).getAuditLog(logId);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return auditLog;
  }
  
  public OrganizationPO getOrgByOrgId(String logId) throws Exception {
    OrganizationPO organizationVO = null;
    OrganizationEJBBean organizationEJBBean = new OrganizationEJBBean();
    organizationVO = organizationEJBBean.getOrgByOrgId(logId);
    return organizationVO;
  }
  
  public List getParentOrgListTemp(String orgId, String demainid, String range) throws Exception {
    OrganizationEJBBean organizationEJBBean = new OrganizationEJBBean();
    List list = organizationEJBBean.getParentOrgListTemp(orgId, demainid, range);
    return list;
  }
  
  public String getRootOrg() throws Exception {
    OrganizationEJBBean organizationEJBBean = new OrganizationEJBBean();
    return organizationEJBBean.getRootOrg();
  }
}
