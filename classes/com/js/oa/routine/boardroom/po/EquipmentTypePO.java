package com.js.oa.routine.boardroom.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class EquipmentTypePO implements Serializable {
  private Long equipmentSortId;
  
  private String name;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private Set equipment = null;
  
  private String domainId;
  
  private String corpId;
  
  public String getCorpId() {
    return this.corpId;
  }
  
  public void setCorpId(String corpId) {
    this.corpId = corpId;
  }
  
  public EquipmentTypePO(String name, Long createdEmp, Long createdOrg, Set equipment) {
    this.name = name;
    this.createdEmp = createdEmp;
    this.createdOrg = createdOrg;
    this.equipment = equipment;
  }
  
  public EquipmentTypePO() {}
  
  public Long getEquipmentSortId() {
    return this.equipmentSortId;
  }
  
  public void setEquipmentSortId(Long equipmentSortId) {
    this.equipmentSortId = equipmentSortId;
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
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public Set getEquipment() {
    return this.equipment;
  }
  
  public void setEquipment(Set equipment) {
    this.equipment = equipment;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("equipmentSortId", getEquipmentSortId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof EquipmentTypePO))
      return false; 
    EquipmentTypePO castOther = (EquipmentTypePO)other;
    return (new EqualsBuilder())
      .append(getEquipmentSortId(), castOther.getEquipmentSortId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getEquipmentSortId())
      .toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
