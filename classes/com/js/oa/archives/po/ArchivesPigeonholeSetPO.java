package com.js.oa.archives.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ArchivesPigeonholeSetPO implements Serializable {
  private Long pigeonholeSetId;
  
  private String pigeonholeSetType;
  
  private String pigeonholeSetPlace;
  
  private Integer isHold;
  
  private Integer isSendMessage;
  
  private String domainId;
  
  public ArchivesPigeonholeSetPO(String pigeonholeSetType, String pigeonholeSetPlace, Integer isHold, Integer isSendMessage) {
    this.pigeonholeSetType = pigeonholeSetType;
    this.pigeonholeSetPlace = pigeonholeSetPlace;
    this.isHold = isHold;
    this.isSendMessage = isSendMessage;
  }
  
  public ArchivesPigeonholeSetPO() {}
  
  public Long getPigeonholeSetId() {
    return this.pigeonholeSetId;
  }
  
  public void setPigeonholeSetId(Long pigeonholeSetId) {
    this.pigeonholeSetId = pigeonholeSetId;
  }
  
  public String getPigeonholeSetType() {
    return this.pigeonholeSetType;
  }
  
  public void setPigeonholeSetType(String pigeonholeSetType) {
    this.pigeonholeSetType = pigeonholeSetType;
  }
  
  public String getPigeonholeSetPlace() {
    return this.pigeonholeSetPlace;
  }
  
  public void setPigeonholeSetPlace(String pigeonholeSetPlace) {
    this.pigeonholeSetPlace = pigeonholeSetPlace;
  }
  
  public Integer getIsHold() {
    return this.isHold;
  }
  
  public void setIsHold(Integer isHold) {
    this.isHold = isHold;
  }
  
  public Integer getIsSendMessage() {
    return this.isSendMessage;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setIsSendMessage(Integer isSendMessage) {
    this.isSendMessage = isSendMessage;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("pigeonholeSetId", getPigeonholeSetId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ArchivesPigeonholeSetPO))
      return false; 
    ArchivesPigeonholeSetPO castOther = (ArchivesPigeonholeSetPO)other;
    return (new EqualsBuilder())
      .append(getPigeonholeSetId(), castOther.getPigeonholeSetId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getPigeonholeSetId())
      .toHashCode();
  }
}
