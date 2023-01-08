package com.js.oa.info.infomanager.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class InforHistoryAccessoryPO implements Serializable {
  private Long accessoryId;
  
  private String accessoryName;
  
  private String accessoryType;
  
  private String accessorySaveName;
  
  private InformationHistoryPO informationHistory;
  
  private int accessoryIsImage;
  
  private Long domainId;
  
  public Long getAccessoryId() {
    return this.accessoryId;
  }
  
  public void setAccessoryId(Long accessoryId) {
    this.accessoryId = accessoryId;
  }
  
  public String getAccessoryName() {
    return this.accessoryName;
  }
  
  public void setAccessoryName(String accessoryName) {
    this.accessoryName = accessoryName;
  }
  
  public String getAccessoryType() {
    return this.accessoryType;
  }
  
  public void setAccessoryType(String accessoryType) {
    this.accessoryType = accessoryType;
  }
  
  public String getAccessorySaveName() {
    return this.accessorySaveName;
  }
  
  public void setAccessorySaveName(String accessorySaveName) {
    this.accessorySaveName = accessorySaveName;
  }
  
  public InformationHistoryPO getInformationHistory() {
    return this.informationHistory;
  }
  
  public void setInformationHistory(InformationHistoryPO informationHistory) {
    this.informationHistory = informationHistory;
  }
  
  public int getAccessoryIsImage() {
    return this.accessoryIsImage;
  }
  
  public void setAccessoryIsImage(int accessoryIsImage) {
    this.accessoryIsImage = accessoryIsImage;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof InforHistoryAccessoryPO))
      return false; 
    InforHistoryAccessoryPO castOther = (InforHistoryAccessoryPO)other;
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
