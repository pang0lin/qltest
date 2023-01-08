package com.js.oa.info.isodoc.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class IsoDeallogPO implements Serializable {
  private Long id;
  
  private Long informationId;
  
  private String startUser;
  
  private String endUser;
  
  private String dealDate;
  
  private String dealType;
  
  private String inforversion;
  
  private String infodealType;
  
  public Long getId() {
    return this.id;
  }
  
  public String getStartUser() {
    return this.startUser;
  }
  
  public String getDealDate() {
    return this.dealDate;
  }
  
  public String getEndUser() {
    return this.endUser;
  }
  
  public void setDealType(String dealType) {
    this.dealType = dealType;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setStartUser(String startUser) {
    this.startUser = startUser;
  }
  
  public void setDealDate(String dealDate) {
    this.dealDate = dealDate;
  }
  
  public void setEndUser(String endUser) {
    this.endUser = endUser;
  }
  
  public void setInformationId(Long informationId) {
    this.informationId = informationId;
  }
  
  public void setInforversion(String inforversion) {
    this.inforversion = inforversion;
  }
  
  public void setInfodealType(String infodealType) {
    this.infodealType = infodealType;
  }
  
  public String getDealType() {
    return this.dealType;
  }
  
  public Long getInformationId() {
    return this.informationId;
  }
  
  public String getInforversion() {
    return this.inforversion;
  }
  
  public String getInfodealType() {
    return this.infodealType;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof IsoDeallogPO))
      return false; 
    IsoDeallogPO castOther = (IsoDeallogPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
}
