package com.js.oa.routine.voiture.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class VoitureTypePO implements Serializable {
  private Long id;
  
  private String name;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private Long domainId;
  
  public VoitureTypePO(String name, Long createdEmp, Long createdOrg) {
    this.name = name;
    this.createdEmp = createdEmp;
    this.createdOrg = createdOrg;
  }
  
  public VoitureTypePO() {}
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public Long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(Long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public Long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(Long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
}
