package com.js.oa.hr.personnelmanager.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class StationPO implements Serializable {
  private String name;
  
  private Long id;
  
  private String domainId;
  
  private String describe;
  
  private Long corpId;
  
  private String no;
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof StationPO))
      return false; 
    StationPO castOther = (StationPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public String getDescribe() {
    return this.describe;
  }
  
  public void setDescribe(String describe) {
    this.describe = describe;
  }
  
  public Long getCorpId() {
    return this.corpId;
  }
  
  public void setCorpId(Long corpId) {
    this.corpId = corpId;
  }
  
  public String getNo() {
    return this.no;
  }
  
  public void setNo(String no) {
    this.no = no;
  }
}
