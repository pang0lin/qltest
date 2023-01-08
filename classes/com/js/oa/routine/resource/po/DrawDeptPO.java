package com.js.oa.routine.resource.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class DrawDeptPO implements Serializable {
  private Long id;
  
  private String name;
  
  private String remark;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private StockPO stock;
  
  private int domainid;
  
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
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
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
  
  public boolean equals(Object other) {
    if (!(other instanceof DrawDeptPO))
      return false; 
    DrawDeptPO castOther = (DrawDeptPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public StockPO getStock() {
    return this.stock;
  }
  
  public void setStock(StockPO stock) {
    this.stock = stock;
  }
  
  public int getDomainid() {
    return this.domainid;
  }
  
  public void setDomainid(int domainid) {
    this.domainid = domainid;
  }
}
