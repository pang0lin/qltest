package com.js.oa.personalwork.setup.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class OfficalDictionPO implements Serializable {
  private long id;
  
  private long empId;
  
  private String dictionContent;
  
  private byte dictionIsShare;
  
  private String empName;
  
  private String domainId;
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(long empId) {
    this.empId = empId;
  }
  
  public String getDictionContent() {
    return this.dictionContent;
  }
  
  public void setDictionContent(String dictionContent) {
    this.dictionContent = dictionContent;
  }
  
  public byte getDictionIsShare() {
    return this.dictionIsShare;
  }
  
  public void setDictionIsShare(byte dictionIsShare) {
    this.dictionIsShare = dictionIsShare;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof OfficalDictionPO))
      return false; 
    OfficalDictionPO castOther = (OfficalDictionPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
