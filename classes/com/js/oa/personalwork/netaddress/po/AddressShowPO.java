package com.js.oa.personalwork.netaddress.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AddressShowPO implements Serializable {
  private long id;
  
  private long empId;
  
  private String netAddress;
  
  private long netAddressId;
  
  private String netAddressURL;
  
  private String domainId;
  
  public AddressShowPO(long empId, String netaddress, long netAddressId) {
    this.empId = empId;
    this.netAddress = netaddress;
    this.netAddressId = netAddressId;
  }
  
  public AddressShowPO() {}
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof AddressShowPO))
      return false; 
    AddressShowPO castOther = (AddressShowPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public String getNetAddress() {
    return this.netAddress;
  }
  
  public void setNetAddress(String netAddress) {
    this.netAddress = netAddress;
  }
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
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
  
  public void setNetAddressId(long netAddressId) {
    this.netAddressId = netAddressId;
  }
  
  public String getNetAddressURL() {
    return this.netAddressURL;
  }
  
  public void setNetAddressURL(String netAddressURL) {
    this.netAddressURL = netAddressURL;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
