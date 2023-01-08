package com.js.oa.routine.voiture.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class VoitureActionForm extends ActionForm {
  private Long id;
  
  private Long voitureTypeId;
  
  private String model;
  
  private String num;
  
  private String name;
  
  private String motorMan;
  
  private Double fixedCost;
  
  private Double oilCost;
  
  private String maintainCyc;
  
  private String maintainOdograph;
  
  private String orgId;
  
  private String orgName;
  
  private String remark;
  
  private String useRangeId;
  
  private String useRangeName;
  
  private String status;
  
  private String vehicleNum;
  
  public String getVehicleNum() {
    return this.vehicleNum;
  }
  
  public void setVehicleNum(String vehicleNum) {
    this.vehicleNum = vehicleNum;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Long getVoitureTypeId() {
    return this.voitureTypeId;
  }
  
  public void setVoitureTypeId(Long voitureTypeId) {
    this.voitureTypeId = voitureTypeId;
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
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.model = "";
    this.num = "";
    this.name = "";
    this.motorMan = "";
    this.fixedCost = new Double(0.0D);
    this.oilCost = new Double(0.0D);
    this.maintainCyc = "";
    this.maintainOdograph = "";
    this.orgId = "";
    this.orgName = "";
    this.remark = "";
    this.useRangeId = "";
    this.useRangeName = "";
    this.status = "";
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
  
  public String getStatus() {
    return this.status;
  }
  
  public void setUseRangeName(String useRangeName) {
    this.useRangeName = useRangeName;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
}
