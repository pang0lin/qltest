package com.js.oa.audit.po;

import java.io.Serializable;
import java.util.Date;

public class AuditIpPO implements Serializable {
  private long auditIpId;
  
  private long ipId;
  
  private String ipAddressEnd;
  
  private String ipAddressBegin;
  
  private Date ipOpenBeginTime = null;
  
  private Date ipOpenEndTime = null;
  
  private byte ipIsOpen;
  
  private String ipProposer;
  
  private Date ipApplyTime = null;
  
  private String confirmEmp;
  
  private String confirmOrg;
  
  private String domainId;
  
  private Long auditLogId;
  
  private String operationType;
  
  public AuditIpPO(String ipAddressEnd, String ipAddressBegin, Date ipOpenBeginTime, Date ipOpenEndTime, byte ipIsOpen, String ipProposer, Date ipApplyTime) {
    this.ipAddressEnd = ipAddressEnd;
    this.ipAddressBegin = ipAddressBegin;
    this.ipOpenBeginTime = ipOpenBeginTime;
    this.ipOpenEndTime = ipOpenEndTime;
    this.ipIsOpen = ipIsOpen;
    this.ipProposer = ipProposer;
    this.ipApplyTime = ipApplyTime;
  }
  
  public AuditIpPO() {}
  
  public String getIpAddressEnd() {
    return this.ipAddressEnd;
  }
  
  public void setIpAddressEnd(String ipAddressEnd) {
    this.ipAddressEnd = ipAddressEnd;
  }
  
  public String getIpAddressBegin() {
    return this.ipAddressBegin;
  }
  
  public void setIpAddressBegin(String ipAddressBegin) {
    this.ipAddressBegin = ipAddressBegin;
  }
  
  public Date getIpOpenBeginTime() {
    return this.ipOpenBeginTime;
  }
  
  public void setIpOpenBeginTime(Date ipOpenBeginTime) {
    this.ipOpenBeginTime = ipOpenBeginTime;
  }
  
  public Date getIpOpenEndTime() {
    return this.ipOpenEndTime;
  }
  
  public void setIpOpenEndTime(Date ipOpenEndTime) {
    this.ipOpenEndTime = ipOpenEndTime;
  }
  
  public byte getIpIsOpen() {
    return this.ipIsOpen;
  }
  
  public void setIpIsOpen(byte ipIsOpen) {
    this.ipIsOpen = ipIsOpen;
  }
  
  public String getIpProposer() {
    return this.ipProposer;
  }
  
  public void setIpProposer(String ipProposer) {
    this.ipProposer = ipProposer;
  }
  
  public Date getIpApplyTime() {
    return this.ipApplyTime;
  }
  
  public void setIpApplyTime(Date ipApplyTime) {
    this.ipApplyTime = ipApplyTime;
  }
  
  public long getAuditIpId() {
    return this.auditIpId;
  }
  
  public void setAuditIpId(long auditIpId) {
    this.auditIpId = auditIpId;
  }
  
  public long getIpId() {
    return this.ipId;
  }
  
  public void setIpId(long ipId) {
    this.ipId = ipId;
  }
  
  public Long getAuditLogId() {
    return this.auditLogId;
  }
  
  public void setAuditLogId(Long auditLogId) {
    this.auditLogId = auditLogId;
  }
  
  public String getOperationType() {
    return this.operationType;
  }
  
  public void setOperationType(String operationType) {
    this.operationType = operationType;
  }
  
  public String getConfirmEmp() {
    return this.confirmEmp;
  }
  
  public void setConfirmEmp(String confirmEmp) {
    this.confirmEmp = confirmEmp;
  }
  
  public String getConfirmOrg() {
    return this.confirmOrg;
  }
  
  public void setConfirmOrg(String confirmOrg) {
    this.confirmOrg = confirmOrg;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
