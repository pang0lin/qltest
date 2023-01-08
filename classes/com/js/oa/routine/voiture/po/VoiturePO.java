package com.js.oa.routine.voiture.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

public class VoiturePO implements Serializable {
  private Long id;
  
  private String model;
  
  private String num;
  
  private String name;
  
  private String motorMan;
  
  private Double fixedCost;
  
  private Double oilCost;
  
  private String maintainCyc;
  
  private String maintainOdograph;
  
  private String orgId;
  
  private int isDelete;
  
  private String orgName;
  
  private Double kiloPrice;
  
  private String remark;
  
  private Set voitureFeePO = null;
  
  private Set voitureSendPO = null;
  
  private VoitureTypePO voitureTypePO;
  
  private Set voitureMaintainPO = null;
  
  private String useRangeId;
  
  private String useRangeName;
  
  private String status;
  
  private Long domainId;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private String vehicleNum;
  
  public String getVehicleNum() {
    return this.vehicleNum;
  }
  
  public void setVehicleNum(String vehicleNum) {
    this.vehicleNum = vehicleNum;
  }
  
  public VoiturePO(VoitureTypePO voitureTypePO, String model, String num, String name, String motorMan, Double fixedCost, Double oilCost, String maintainCyc, String maintainOdograph, String orgId, String orgName, String remark, String status) {
    this.voitureTypePO = voitureTypePO;
    this.model = model;
    this.num = num;
    this.name = name;
    this.motorMan = motorMan;
    this.fixedCost = fixedCost;
    this.oilCost = oilCost;
    this.maintainCyc = maintainCyc;
    this.maintainOdograph = maintainOdograph;
    this.orgId = orgId;
    this.orgName = orgName;
    this.remark = remark;
    this.status = status;
  }
  
  public VoiturePO() {}
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public VoitureTypePO getVoitureTypePO() {
    return this.voitureTypePO;
  }
  
  public void setVoitureTypePO(VoitureTypePO voitureTypePO) {
    this.voitureTypePO = voitureTypePO;
  }
  
  public String getModel() {
    return this.model;
  }
  
  public void setModel(String model) {
    this.model = model;
  }
  
  public String getNum() {
    return this.num;
  }
  
  public void setNum(String num) {
    this.num = num;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getMotorMan() {
    return this.motorMan;
  }
  
  public void setMotorMan(String motorMan) {
    this.motorMan = motorMan;
  }
  
  public Double getFixedCost() {
    return this.fixedCost;
  }
  
  public void setFixedCost(Double fixedCost) {
    this.fixedCost = fixedCost;
  }
  
  public Double getOilCost() {
    return this.oilCost;
  }
  
  public void setOilCost(Double oilCost) {
    this.oilCost = oilCost;
  }
  
  public String getMaintainCyc() {
    return this.maintainCyc;
  }
  
  public void setMaintainCyc(String maintainCyc) {
    this.maintainCyc = maintainCyc;
  }
  
  public String getMaintainOdograph() {
    return this.maintainOdograph;
  }
  
  public void setMaintainOdograph(String maintainOdograph) {
    this.maintainOdograph = maintainOdograph;
  }
  
  public String getOrgId() {
    return this.orgId;
  }
  
  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  public Double getKiloPrice() {
    return this.kiloPrice;
  }
  
  public void setKiloPrice(Double kiloPrice) {
    this.kiloPrice = kiloPrice;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public Set getVoitureFeePO() {
    return this.voitureFeePO;
  }
  
  public void setVoitureFeePO(Set voitureFeePO) {
    this.voitureFeePO = voitureFeePO;
  }
  
  public Set getVoitureSendPO() {
    return this.voitureSendPO;
  }
  
  public void setVoitureSendPO(Set voitureSend) {
    this.voitureSendPO = voitureSend;
  }
  
  public Set getVoitureMaintainPO() {
    return this.voitureMaintainPO;
  }
  
  public void setVoitureMaintainPO(Set voitureMaintainPO) {
    this.voitureMaintainPO = voitureMaintainPO;
  }
  
  public String getUseRangeId() {
    return this.useRangeId;
  }
  
  public void setUseRangeId(String useRangeId) {
    this.useRangeId = useRangeId;
  }
  
  public String getUseRangeName() {
    return this.useRangeName;
  }
  
  public void setUseRangeName(String useRangeName) {
    this.useRangeName = useRangeName;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public String getStatus() {
    return this.status;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public Long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public Long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(Long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public void setCreatedOrg(Long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public int getIsDelete() {
    return this.isDelete;
  }
  
  public void setIsDelete(int isDelete) {
    this.isDelete = isDelete;
  }
}
