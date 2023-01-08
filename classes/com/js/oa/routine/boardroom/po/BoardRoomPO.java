package com.js.oa.routine.boardroom.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class BoardRoomPO implements Serializable {
  private Long boardroomId;
  
  private String name;
  
  private String depict;
  
  private String location;
  
  private String capacitance;
  
  private Integer boardroomType;
  
  private Float cost;
  
  private Integer emphasis;
  
  private String scope;
  
  private String scopeOrg;
  
  private String scopeGroup;
  
  private String scopeEmp;
  
  private String remark;
  
  private Integer limit;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private Long doorNumber;
  
  private Long controlNumber;
  
  private Set boardRoomApply = null;
  
  private Set boardRoomEquipment = null;
  
  private String domainId;
  
  private Long isVideo;
  
  private Long maxNumber;
  
  private String workaddress;
  
  private String manageOrgName;
  
  private String manageOrg;
  
  private String applyPhone;
  
  private String boardRoomLayout;
  
  private String reserveType;
  
  private String circleType;
  
  private String chargeType;
  
  private String useScopeId;
  
  private String useScope;
  
  private Integer boardroomOrder;
  
  public BoardRoomPO(String name, String depict, String location, String capacitance, Integer boardroomType, Float cost, Integer emphasis, String scope, String scopeOrg, String scopeGroup, String scopeEmp, String remark, Integer limit, Long createdEmp, Long createdOrg, Long doorNumber, Long controlNumber, Set boardRoomApply) {
    this.name = name;
    this.depict = depict;
    this.location = location;
    this.capacitance = capacitance;
    this.boardroomType = boardroomType;
    this.cost = cost;
    this.emphasis = emphasis;
    this.scope = scope;
    this.scopeOrg = scopeOrg;
    this.scopeGroup = scopeGroup;
    this.scopeEmp = scopeEmp;
    this.remark = remark;
    this.limit = limit;
    this.createdEmp = createdEmp;
    this.createdOrg = createdOrg;
    this.doorNumber = doorNumber;
    this.controlNumber = controlNumber;
    this.boardRoomApply = boardRoomApply;
  }
  
  public BoardRoomPO() {}
  
  public Long getBoardroomId() {
    return this.boardroomId;
  }
  
  public void setBoardroomId(Long boardroomId) {
    this.boardroomId = boardroomId;
  }
  
  public Integer getBoardroomType() {
    return this.boardroomType;
  }
  
  public void setBoardroomType(Integer boardroomType) {
    this.boardroomType = boardroomType;
  }
  
  public String getCapacitance() {
    return this.capacitance;
  }
  
  public void setCapacitance(String capacitance) {
    this.capacitance = capacitance;
  }
  
  public Float getCost() {
    return this.cost;
  }
  
  public void setCost(Float cost) {
    this.cost = cost;
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
  
  public String getDepict() {
    return this.depict;
  }
  
  public void setDepict(String depict) {
    this.depict = depict;
  }
  
  public Integer getEmphasis() {
    return this.emphasis;
  }
  
  public void setEmphasis(Integer emphasis) {
    this.emphasis = emphasis;
  }
  
  public Integer getLimit() {
    return this.limit;
  }
  
  public void setLimit(Integer limit) {
    this.limit = limit;
  }
  
  public String getLocation() {
    return this.location;
  }
  
  public void setLocation(String location) {
    this.location = location;
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
  
  public String getScope() {
    return this.scope;
  }
  
  public void setScope(String scope) {
    this.scope = scope;
  }
  
  public String getScopeEmp() {
    return this.scopeEmp;
  }
  
  public void setScopeEmp(String scopeEmp) {
    this.scopeEmp = scopeEmp;
  }
  
  public String getScopeGroup() {
    return this.scopeGroup;
  }
  
  public void setScopeGroup(String scopeGroup) {
    this.scopeGroup = scopeGroup;
  }
  
  public String getScopeOrg() {
    return this.scopeOrg;
  }
  
  public void setScopeOrg(String scopeOrg) {
    this.scopeOrg = scopeOrg;
  }
  
  public Set getBoardRoomApply() {
    return this.boardRoomApply;
  }
  
  public void setBoardRoomApply(Set boardRoomApply) {
    this.boardRoomApply = boardRoomApply;
  }
  
  public Long getControlNumber() {
    return this.controlNumber;
  }
  
  public void setControlNumber(Long controlNumber) {
    this.controlNumber = controlNumber;
  }
  
  public Long getDoorNumber() {
    return this.doorNumber;
  }
  
  public void setDoorNumber(Long doorNumber) {
    this.doorNumber = doorNumber;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("boardroomId", getBoardroomId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof BoardRoomPO))
      return false; 
    BoardRoomPO castOther = (BoardRoomPO)other;
    return (new EqualsBuilder())
      .append(getBoardroomId(), castOther.getBoardroomId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getBoardroomId())
      .toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public Set getBoardRoomEquipment() {
    return this.boardRoomEquipment;
  }
  
  public void setBoardRoomEquipment(Set boardRoomEquipment) {
    this.boardRoomEquipment = boardRoomEquipment;
  }
  
  public Long getIsVideo() {
    return this.isVideo;
  }
  
  public void setIsVideo(Long isVideo) {
    this.isVideo = isVideo;
  }
  
  public Long getMaxNumber() {
    return this.maxNumber;
  }
  
  public String getWorkaddress() {
    return this.workaddress;
  }
  
  public String getManageOrgName() {
    return this.manageOrgName;
  }
  
  public String getManageOrg() {
    return this.manageOrg;
  }
  
  public void setMaxNumber(Long maxNumber) {
    this.maxNumber = maxNumber;
  }
  
  public void setWorkaddress(String workaddress) {
    this.workaddress = workaddress;
  }
  
  public void setManageOrgName(String manageOrgName) {
    this.manageOrgName = manageOrgName;
  }
  
  public void setManageOrg(String manageOrg) {
    this.manageOrg = manageOrg;
  }
  
  public String getApplyPhone() {
    return this.applyPhone;
  }
  
  public void setApplyPhone(String applyPhone) {
    this.applyPhone = applyPhone;
  }
  
  public String getBoardRoomLayout() {
    return this.boardRoomLayout;
  }
  
  public void setBoardRoomLayout(String boardRoomLayout) {
    this.boardRoomLayout = boardRoomLayout;
  }
  
  public String getChargeType() {
    return this.chargeType;
  }
  
  public void setChargeType(String chargeType) {
    this.chargeType = chargeType;
  }
  
  public String getCircleType() {
    return this.circleType;
  }
  
  public void setCircleType(String circleType) {
    this.circleType = circleType;
  }
  
  public String getReserveType() {
    return this.reserveType;
  }
  
  public void setReserveType(String reserveType) {
    this.reserveType = reserveType;
  }
  
  public String getUseScopeId() {
    return this.useScopeId;
  }
  
  public void setUseScopeId(String useScopeId) {
    this.useScopeId = useScopeId;
  }
  
  public String getUseScope() {
    return this.useScope;
  }
  
  public void setUseScope(String useScope) {
    this.useScope = useScope;
  }
  
  public Integer getBoardroomOrder() {
    return this.boardroomOrder;
  }
  
  public void setBoardroomOrder(Integer boardroomOrder) {
    this.boardroomOrder = boardroomOrder;
  }
}
