package com.js.oa.audit.po;

import com.js.system.vo.organizationmanager.OrganizationVO;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class OrganizationPO implements Serializable {
  private Long auditOrgId;
  
  private Long orgId;
  
  private long orgParentOrgId;
  
  private String orgManagerEmpId;
  
  private String orgManagerEmpName;
  
  private String orgName;
  
  private int orgOrderCode;
  
  private Date orgFoundDate = null;
  
  private String orgDescripte;
  
  private String orgIdString;
  
  private Integer rtxDeptId;
  
  private Integer rtxDeptPId;
  
  private int orgLevel;
  
  private int orgHasJunior;
  
  private int orgStatus;
  
  private int orgHasChannel;
  
  private String orgNameString;
  
  private String orgSimpleName;
  
  private String orgSerial;
  
  private String orgBanner;
  
  private Set booksBuy = null;
  
  private Set booksDetail = null;
  
  private String domainId;
  
  private String orgType;
  
  private String orgChannelType;
  
  private String orgChannelUrl;
  
  private String guid;
  
  private String logId;
  
  private String operate;
  
  public Long getAuditOrgId() {
    return this.auditOrgId;
  }
  
  public void setAuditOrgId(Long auditOrgId) {
    this.auditOrgId = auditOrgId;
  }
  
  public String getLogId() {
    return this.logId;
  }
  
  public void setLogId(String logId) {
    this.logId = logId;
  }
  
  public String getOperate() {
    return this.operate;
  }
  
  public void setOperate(String operate) {
    this.operate = operate;
  }
  
  public String getGuid() {
    return this.guid;
  }
  
  public void setGuid(String guid) {
    this.guid = guid;
  }
  
  public OrganizationPO(Long orgId, long orgParentOrgId, String orgManagerEmpId, String orgName, int orgOrderCode, Date orgFoundDate, String orgDescripte, int orgLevel, int orgHasJunior, int orgStatus) {
    this.orgId = orgId;
    this.orgParentOrgId = orgParentOrgId;
    this.orgManagerEmpId = orgManagerEmpId;
    this.orgName = orgName;
    this.orgOrderCode = orgOrderCode;
    this.orgFoundDate = orgFoundDate;
    this.orgDescripte = orgDescripte;
    this.orgLevel = orgLevel;
    this.orgHasJunior = orgHasJunior;
    this.orgStatus = orgStatus;
  }
  
  public void setOrgStatus(int orgStatus) {
    this.orgStatus = orgStatus;
  }
  
  public int getOrgStatus() {
    return this.orgStatus;
  }
  
  public void setOrgHasJunior(int orgHasJunior) {
    this.orgHasJunior = orgHasJunior;
  }
  
  public int getOrgHasJunior() {
    return this.orgHasJunior;
  }
  
  public OrganizationPO() {}
  
  public OrganizationPO(Long orgId, String orgName) {
    this.orgId = orgId;
    this.orgName = orgName;
  }
  
  public Long getOrgId() {
    return this.orgId;
  }
  
  public void setOrgId(Long orgId) {
    this.orgId = orgId;
  }
  
  public long getOrgParentOrgId() {
    return this.orgParentOrgId;
  }
  
  public void setOrgParentOrgId(long orgParentOrgId) {
    this.orgParentOrgId = orgParentOrgId;
  }
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public int getOrgOrderCode() {
    return this.orgOrderCode;
  }
  
  public void setOrgOrderCode(int orgOrderCode) {
    this.orgOrderCode = orgOrderCode;
  }
  
  public Date getOrgFoundDate() {
    return this.orgFoundDate;
  }
  
  public void setOrgFoundDate(Date orgFoundDate) {
    this.orgFoundDate = orgFoundDate;
  }
  
  public String getOrgDescripte() {
    return this.orgDescripte;
  }
  
  public void setOrgDescripte(String orgDescripte) {
    this.orgDescripte = orgDescripte;
  }
  
  public int getOrgLevel() {
    return this.orgLevel;
  }
  
  public void setOrgLevel(int orgLevel) {
    this.orgLevel = orgLevel;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("orgId", getOrgId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof OrganizationVO))
      return false; 
    OrganizationVO castOther = (OrganizationVO)other;
    return (new EqualsBuilder())
      .append(getOrgId(), castOther.getOrgId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getOrgId())
      .toHashCode();
  }
  
  public String getOrgManagerEmpId() {
    return this.orgManagerEmpId;
  }
  
  public void setOrgManagerEmpId(String orgManagerEmpId) {
    this.orgManagerEmpId = orgManagerEmpId;
  }
  
  public String getOrgIdString() {
    return this.orgIdString;
  }
  
  public void setOrgIdString(String orgIdString) {
    this.orgIdString = orgIdString;
  }
  
  public int getOrgHasChannel() {
    return this.orgHasChannel;
  }
  
  public void setOrgHasChannel(int orgHasChannel) {
    this.orgHasChannel = orgHasChannel;
  }
  
  public String getOrgManagerEmpName() {
    return this.orgManagerEmpName;
  }
  
  public void setOrgManagerEmpName(String orgManagerEmpName) {
    this.orgManagerEmpName = orgManagerEmpName;
  }
  
  public String getOrgNameString() {
    return this.orgNameString;
  }
  
  public void setOrgNameString(String orgNameString) {
    this.orgNameString = orgNameString;
  }
  
  public String getOrgSimpleName() {
    return this.orgSimpleName;
  }
  
  public void setOrgSimpleName(String orgSimpleName) {
    this.orgSimpleName = orgSimpleName;
  }
  
  public String getOrgSerial() {
    return this.orgSerial;
  }
  
  public void setOrgSerial(String orgSerial) {
    this.orgSerial = orgSerial;
  }
  
  public String getOrgBanner() {
    return this.orgBanner;
  }
  
  public void setOrgBanner(String orgBanner) {
    this.orgBanner = orgBanner;
  }
  
  public Set getBooksBuy() {
    return this.booksBuy;
  }
  
  public void setBooksBuy(Set booksBuy) {
    this.booksBuy = booksBuy;
  }
  
  public Set getBooksDetail() {
    return this.booksDetail;
  }
  
  public void setBooksDetail(Set booksDetail) {
    this.booksDetail = booksDetail;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public String getOrgType() {
    return this.orgType;
  }
  
  public String getOrgChannelUrl() {
    return this.orgChannelUrl;
  }
  
  public String getOrgChannelType() {
    return this.orgChannelType;
  }
  
  public void setOrgType(String orgType) {
    this.orgType = orgType;
  }
  
  public void setOrgChannelUrl(String orgChannelUrl) {
    this.orgChannelUrl = orgChannelUrl;
  }
  
  public void setOrgChannelType(String orgChannelType) {
    this.orgChannelType = orgChannelType;
  }
  
  public Integer getRtxDeptId() {
    return this.rtxDeptId;
  }
  
  public void setRtxDeptId(Integer rtxDeptId) {
    this.rtxDeptId = rtxDeptId;
  }
  
  public Integer getRtxDeptPId() {
    return this.rtxDeptPId;
  }
  
  public void setRtxDeptPId(Integer rtxDeptPId) {
    this.rtxDeptPId = rtxDeptPId;
  }
}
