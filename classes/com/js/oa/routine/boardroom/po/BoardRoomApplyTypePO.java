package com.js.oa.routine.boardroom.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class BoardRoomApplyTypePO implements Serializable {
  private Long bdroomAppTypeId;
  
  private String type;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private Set boardRoomApply = null;
  
  private String domainId;
  
  private String applyType;
  
  private String typeDefault;
  
  private String usedScopeName;
  
  private String usedScopeId;
  
  private String usedScopeOrgId;
  
  private String usedScopeGroupId;
  
  private String applyFirst;
  
  public String getApplyFirst() {
    return this.applyFirst;
  }
  
  public void setApplyFirst(String applyFirst) {
    this.applyFirst = applyFirst;
  }
  
  public String getUsedScopeName() {
    return this.usedScopeName;
  }
  
  public void setUsedScopeName(String usedScopeName) {
    this.usedScopeName = usedScopeName;
  }
  
  public String getUsedScopeId() {
    return this.usedScopeId;
  }
  
  public void setUsedScopeId(String usedScopeId) {
    this.usedScopeId = usedScopeId;
  }
  
  public String getUsedScopeOrgId() {
    return this.usedScopeOrgId;
  }
  
  public void setUsedScopeOrgId(String usedScopeOrgId) {
    this.usedScopeOrgId = usedScopeOrgId;
  }
  
  public String getUsedScopeGroupId() {
    return this.usedScopeGroupId;
  }
  
  public void setUsedScopeGroupId(String usedScopeGroupId) {
    this.usedScopeGroupId = usedScopeGroupId;
  }
  
  public BoardRoomApplyTypePO(String type, Long createdEmp, Long createdOrg, Set boardRoomApply) {
    this.type = type;
    this.createdEmp = createdEmp;
    this.createdOrg = createdOrg;
    this.boardRoomApply = boardRoomApply;
  }
  
  public BoardRoomApplyTypePO() {}
  
  public Long getBdroomAppTypeId() {
    return this.bdroomAppTypeId;
  }
  
  public void setBdroomAppTypeId(Long bdroomAppTypeId) {
    this.bdroomAppTypeId = bdroomAppTypeId;
  }
  
  public String getType() {
    return this.type;
  }
  
  public void setType(String type) {
    this.type = type;
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
  
  public Set getBoardRoomApply() {
    return this.boardRoomApply;
  }
  
  public void setBoardRoomApply(Set boardRoomApply) {
    this.boardRoomApply = boardRoomApply;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("bdroomAppTypeId", getBdroomAppTypeId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof BoardRoomApplyTypePO))
      return false; 
    BoardRoomApplyTypePO castOther = (BoardRoomApplyTypePO)other;
    return (new EqualsBuilder())
      .append(getBdroomAppTypeId(), castOther.getBdroomAppTypeId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getBdroomAppTypeId())
      .toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public String getApplyType() {
    return this.applyType;
  }
  
  public void setApplyType(String applyType) {
    this.applyType = applyType;
  }
  
  public String getTypeDefault() {
    return this.typeDefault;
  }
  
  public void setTypeDefault(String typeDefault) {
    this.typeDefault = typeDefault;
  }
}
