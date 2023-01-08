package com.js.oa.security.ip.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class IPPO implements Serializable {
  private long id;
  
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
  
  public IPPO(String ipAddressEnd, String ipAddressBegin, Date ipOpenBeginTime, Date ipOpenEndTime, byte ipIsOpen, String ipProposer, Date ipApplyTime) {
    this.ipAddressEnd = ipAddressEnd;
    this.ipAddressBegin = ipAddressBegin;
    this.ipOpenBeginTime = ipOpenBeginTime;
    this.ipOpenEndTime = ipOpenEndTime;
    this.ipIsOpen = ipIsOpen;
    this.ipProposer = ipProposer;
    this.ipApplyTime = ipApplyTime;
  }
  
  public IPPO() {}
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
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
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof IPPO))
      return false; 
    IPPO castOther = (IPPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
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
