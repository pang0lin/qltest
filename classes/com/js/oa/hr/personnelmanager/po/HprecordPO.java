package com.js.oa.hr.personnelmanager.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class HprecordPO implements Serializable {
  private Long id;
  
  private String hpTitle;
  
  private Long hpType;
  
  private String hpPersonnel;
  
  private String hpPersonnelName;
  
  private Date hpDate = null;
  
  private String hpExplain;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private Long domain;
  
  public Long getId() {
    return this.id;
  }
  
  public String getHpTitle() {
    return this.hpTitle;
  }
  
  public Long getHpType() {
    return this.hpType;
  }
  
  public String getHpPersonnel() {
    return this.hpPersonnel;
  }
  
  public String getHpPersonnelName() {
    return this.hpPersonnelName;
  }
  
  public Date getHpDate() {
    return this.hpDate;
  }
  
  public String getHpExplain() {
    return this.hpExplain;
  }
  
  public Long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public Long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public Long getDomain() {
    return this.domain;
  }
  
  public void setDomain(Long domain) {
    this.domain = domain;
  }
  
  public void setCreatedOrg(Long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public void setCreatedEmp(Long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public void setHpExplain(String hpExplain) {
    this.hpExplain = hpExplain;
  }
  
  public void setHpDate(Date hpDate) {
    this.hpDate = hpDate;
  }
  
  public void setHpPersonnelName(String hpPersonnelName) {
    this.hpPersonnelName = hpPersonnelName;
  }
  
  public void setHpPersonnel(String hpPersonnel) {
    this.hpPersonnel = hpPersonnel;
  }
  
  public void setHpType(Long hpType) {
    this.hpType = hpType;
  }
  
  public void setHpTitle(String hpTitle) {
    this.hpTitle = hpTitle;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof HprecordPO))
      return false; 
    HprecordPO castOther = (HprecordPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
}
