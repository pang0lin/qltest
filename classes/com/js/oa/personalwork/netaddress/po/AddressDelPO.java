package com.js.oa.personalwork.netaddress.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AddressDelPO implements Serializable {
  private long empId;
  
  private long netAddressId;
  
  private String id;
  
  public AddressDelPO(long empId, long netAddressId) {
    this.empId = empId;
    this.netAddressId = netAddressId;
  }
  
  public AddressDelPO() {}
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof AddressDelPO))
      return false; 
    AddressDelPO castOther = (AddressDelPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public void setEmpId(long empId) {
    this.empId = empId;
  }
  
  public long getEmpId() {
    return this.empId;
  }
  
  public long getNetAddressId() {
    return this.netAddressId;
  }
  
  public String getId() {
    return this.id;
  }
  
  public void setNetAddressId(long netAddressId) {
    this.netAddressId = netAddressId;
  }
  
  public void setId(String id) {
    this.id = id;
  }
}
