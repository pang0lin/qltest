package com.js.oa.info.infomanager.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class InformationAccessoryPO implements Serializable {
  private Long accessoryId;
  
  private String accessoryType;
  
  private String accessoryName;
  
  private String accessorySaveName;
  
  private InformationPO information;
  
  private int accessoryIsImage;
  
  private Long domainId;
  
  public Long getAccessoryId() {
    return this.accessoryId;
  }
  
  public void setAccessoryId(Long accessoryId) {
    this.accessoryId = accessoryId;
  }
  
  public String getAccessoryType() {
    return this.accessoryType;
  }
  
  public void setAccessoryType(String accessoryType) {
    this.accessoryType = accessoryType;
  }
  
  public String getAccessoryName() {
    return this.accessoryName;
  }
  
  public void setAccessoryName(String accessoryName) {
    this.accessoryName = accessoryName;
  }
  
  public String getAccessorySaveName() {
    return this.accessorySaveName;
  }
  
  public void setAccessorySaveName(String accessorySaveName) {
    this.accessorySaveName = accessorySaveName;
  }
  
  public InformationPO getInformation() {
    return this.information;
  }
  
  public void setInformation(InformationPO information) {
    this.information = information;
  }
  
  public int getAccessoryIsImage() {
    return this.accessoryIsImage;
  }
  
  public void setAccessoryIsImage(int accessoryIsImage) {
    this.accessoryIsImage = accessoryIsImage;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof InformationAccessoryPO))
      return false; 
    InformationAccessoryPO castOther = (InformationAccessoryPO)other;
    return (new EqualsBuilder()).append(getAccessoryId(), castOther.getAccessoryId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getAccessoryId()).toHashCode();
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
}
