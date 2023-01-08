package com.js.oa.audit.po;

import java.io.Serializable;
import java.util.Date;

public class AuditLog implements Serializable {
  private Long logId;
  
  private Long submitEmpid;
  
  private String submitEmpname;
  
  private Long submitOrgid;
  
  private Date submitTime = null;
  
  private Long checkEmpid;
  
  private String checkEmpname;
  
  private Date checkTime = null;
  
  private Long auditModule;
  
  private Integer auditStatus;
  
  private Integer ischecked;
  
  public AuditLog() {}
  
  public AuditLog(Long submitEmpid, String submitEmpname, Long submitOrgid, Date submitTime, Long checkEmpid, String checkEmpname, Date checkTime, Long auditModule, Integer auditStatus, Integer ischecked) {
    this.submitEmpid = submitEmpid;
    this.submitEmpname = submitEmpname;
    this.submitOrgid = submitOrgid;
    this.submitTime = submitTime;
    this.checkEmpid = checkEmpid;
    this.checkEmpname = checkEmpname;
    this.checkTime = checkTime;
    this.auditModule = auditModule;
    this.auditStatus = auditStatus;
    this.ischecked = ischecked;
  }
  
  public Long getLogId() {
    return this.logId;
  }
  
  public void setLogId(Long logId) {
    this.logId = logId;
  }
  
  public Long getSubmitEmpid() {
    return this.submitEmpid;
  }
  
  public void setSubmitEmpid(Long submitEmpid) {
    this.submitEmpid = submitEmpid;
  }
  
  public String getSubmitEmpname() {
    return this.submitEmpname;
  }
  
  public void setSubmitEmpname(String submitEmpname) {
    this.submitEmpname = submitEmpname;
  }
  
  public Long getSubmitOrgid() {
    return this.submitOrgid;
  }
  
  public void setSubmitOrgid(Long submitOrgid) {
    this.submitOrgid = submitOrgid;
  }
  
  public Date getSubmitTime() {
    return this.submitTime;
  }
  
  public void setSubmitTime(Date submitTime) {
    this.submitTime = submitTime;
  }
  
  public Long getCheckEmpid() {
    return this.checkEmpid;
  }
  
  public void setCheckEmpid(Long checkEmpid) {
    this.checkEmpid = checkEmpid;
  }
  
  public String getCheckEmpname() {
    return this.checkEmpname;
  }
  
  public void setCheckEmpname(String checkEmpname) {
    this.checkEmpname = checkEmpname;
  }
  
  public Date getCheckTime() {
    return this.checkTime;
  }
  
  public void setCheckTime(Date checkTime) {
    this.checkTime = checkTime;
  }
  
  public Long getAuditModule() {
    return this.auditModule;
  }
  
  public void setAuditModule(Long auditModule) {
    this.auditModule = auditModule;
  }
  
  public Integer getAuditStatus() {
    return this.auditStatus;
  }
  
  public void setAuditStatus(Integer auditStatus) {
    this.auditStatus = auditStatus;
  }
  
  public Integer getIschecked() {
    return this.ischecked;
  }
  
  public void setIschecked(Integer ischecked) {
    this.ischecked = ischecked;
  }
}
