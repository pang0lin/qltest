package com.js.oa.personalwork.netaddress.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AddressClassPO implements Serializable {
  private long id;
  
  private long empId;
  
  private String className;
  
  private Set addresses = null;
  
  private String domainId;
  
  private int classIsShare;
  
  public AddressClassPO(long empId, String classname, Set addresses, int classIsShare) {
    this.empId = empId;
    this.className = classname;
    this.addresses = addresses;
    this.classIsShare = classIsShare;
  }
  
  public AddressClassPO() {}
  
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
  
  public String getClassName() {
    return this.className;
  }
  
  public void setClassName(String className) {
    this.className = className;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof AddressClassPO))
      return false; 
    AddressClassPO castOther = (AddressClassPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public Set getAddresses() {
    return this.addresses;
  }
  
  public void setAddresses(Set addresses) {
    this.addresses = addresses;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public int getClassIsShare() {
    return this.classIsShare;
  }
  
  public void setClassIsShare(int classIsShare) {
    this.classIsShare = classIsShare;
  }
}
