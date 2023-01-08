package com.js.oa.routine.boardroom.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class EquipmentPO implements Serializable {
  private Long equipmentId;
  
  private EquipmentTypePO equipmentType;
  
  private String name;
  
  private String code;
  
  private Float cost;
  
  private Integer status;
  
  private String manageDept;
  
  private String manageDeptName;
  
  private String remark;
  
  private Set equipmentApply = null;
  
  private String domainId;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private String standard;
  
  private String storeManID;
  
  private String storeManName;
  
  private String corpId;
  
  public EquipmentPO(EquipmentTypePO equipmentType, String name, String code, Float cost, Integer status, String manageDept, String manageDeptName, String remark, Set equipmentApply) {
    this.equipmentType = equipmentType;
    this.name = name;
    this.code = code;
    this.cost = cost;
    this.status = status;
    this.manageDept = manageDept;
    this.manageDeptName = manageDeptName;
    this.remark = remark;
    this.equipmentApply = equipmentApply;
  }
  
  public String getCorpId() {
    return this.corpId;
  }
  
  public void setCorpId(String corpId) {
    this.corpId = corpId;
  }
  
  public EquipmentPO() {}
  
  public String getCode() {
    return this.code;
  }
  
  public void setCode(String code) {
    this.code = code;
  }
  
  public Float getCost() {
    return this.cost;
  }
  
  public void setCost(Float cost) {
    this.cost = cost;
  }
  
  public Long getEquipmentId() {
    return this.equipmentId;
  }
  
  public void setEquipmentId(Long equipmentId) {
    this.equipmentId = equipmentId;
  }
  
  public EquipmentTypePO getEquipmentType() {
    return this.equipmentType;
  }
  
  public void setEquipmentType(EquipmentTypePO equipmentType) {
    this.equipmentType = equipmentType;
  }
  
  public String getManageDept() {
    return this.manageDept;
  }
  
  public void setManageDept(String manageDept) {
    this.manageDept = manageDept;
  }
  
  public String getManageDeptName() {
    return this.manageDeptName;
  }
  
  public void setManageDeptName(String manageDeptName) {
    this.manageDeptName = manageDeptName;
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
  
  public Integer getStatus() {
    return this.status;
  }
  
  public void setStatus(Integer status) {
    this.status = status;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("equipmentId", getEquipmentId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof EquipmentPO))
      return false; 
    EquipmentPO castOther = (EquipmentPO)other;
    return (new EqualsBuilder())
      .append(getEquipmentId(), castOther.getEquipmentId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getEquipmentId())
      .toHashCode();
  }
  
  public Set getEquipmentApply() {
    return this.equipmentApply;
  }
  
  public void setEquipmentApply(Set equipmentApply) {
    this.equipmentApply = equipmentApply;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
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
  
  public String getStandard() {
    return this.standard;
  }
  
  public void setStandard(String standard) {
    this.standard = standard;
  }
  
  public String getStoreManID() {
    return this.storeManID;
  }
  
  public void setStoreManID(String storeManID) {
    this.storeManID = storeManID;
  }
  
  public String getStoreManName() {
    return this.storeManName;
  }
  
  public void setStoreManName(String storeManName) {
    this.storeManName = storeManName;
  }
}
