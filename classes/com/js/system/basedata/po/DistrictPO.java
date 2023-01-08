package com.js.system.basedata.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class DistrictPO implements Serializable {
  private Long id;
  
  private String districtName;
  
  private String postalCode;
  
  private String phoneCode;
  
  private Long parentId;
  
  private String idString;
  
  public DistrictPO(String districtName, String postalCode, String phoneCode, Long parentId, String idString) {
    this.districtName = districtName;
    this.postalCode = postalCode;
    this.phoneCode = phoneCode;
    this.parentId = parentId;
    this.idString = idString;
  }
  
  public DistrictPO() {}
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof DistrictPO))
      return false; 
    DistrictPO castOther = (DistrictPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public String getDistrictName() {
    return this.districtName;
  }
  
  public String getIdString() {
    return this.idString;
  }
  
  public Long getParentId() {
    return this.parentId;
  }
  
  public String getPhoneCode() {
    return this.phoneCode;
  }
  
  public String getPostalCode() {
    return this.postalCode;
  }
  
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }
  
  public void setPhoneCode(String phoneCode) {
    this.phoneCode = phoneCode;
  }
  
  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }
  
  public void setIdString(String idString) {
    this.idString = idString;
  }
  
  public void setDistrictName(String districtName) {
    this.districtName = districtName;
  }
}
