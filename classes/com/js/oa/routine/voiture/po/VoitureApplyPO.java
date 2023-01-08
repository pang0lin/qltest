package com.js.oa.routine.voiture.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

public class VoitureApplyPO implements Serializable {
  private Long id;
  
  private String orgId;
  
  private String destination;
  
  private String reason;
  
  private Date startDate = null;
  
  private Date endDate = null;
  
  private String orgName;
  
  private String empName;
  
  private Long voitureId;
  
  private String empId;
  
  private String status;
  
  private Date fillDate = null;
  
  private Set voitureSendPO = null;
  
  private String endTime;
  
  private String startTime;
  
  private String personNum;
  
  private String motorMan;
  
  private String voitureStyle;
  
  private String remark;
  
  private String ycr;
  
  private Long domainId;
  
  private String departurePlace;
  
  private String vehiclePhone;
  
  private String vehicleNum;
  
  private String carPoolId;
  
  public String getCarPoolId() {
    return this.carPoolId;
  }
  
  public void setCarPoolId(String carPoolId) {
    this.carPoolId = carPoolId;
  }
  
  public String getDeparturePlace() {
    return this.departurePlace;
  }
  
  public void setDeparturePlace(String departurePlace) {
    this.departurePlace = departurePlace;
  }
  
  public String getVehiclePhone() {
    return this.vehiclePhone;
  }
  
  public void setVehiclePhone(String vehiclePhone) {
    this.vehiclePhone = vehiclePhone;
  }
  
  public String getVehicleNum() {
    return this.vehicleNum;
  }
  
  public void setVehicleNum(String vehicleNum) {
    this.vehicleNum = vehicleNum;
  }
  
  public VoitureApplyPO(String orgId, String empId, String destination, String reason, Date startDate, String startTime, Date endDate, String endTime, String status) {
    this.orgId = orgId;
    this.empId = empId;
    this.destination = destination;
    this.reason = reason;
    this.startDate = startDate;
    this.endDate = endDate;
    this.status = status;
  }
  
  public VoitureApplyPO() {}
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getOrgId() {
    return this.orgId;
  }
  
  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }
  
  public String getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(String empId) {
    this.empId = empId;
  }
  
  public String getDestination() {
    return this.destination;
  }
  
  public void setDestination(String destination) {
    this.destination = destination;
  }
  
  public String getReason() {
    return this.reason;
  }
  
  public void setReason(String reason) {
    this.reason = reason;
  }
  
  public Date getStartDate() {
    return this.startDate;
  }
  
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  
  public Date getEndDate() {
    return this.endDate;
  }
  
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  
  public String getStatus() {
    return this.status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public Long getVoitureId() {
    return this.voitureId;
  }
  
  public void setVoitureId(Long voitureId) {
    this.voitureId = voitureId;
  }
  
  public Date getFillDate() {
    return this.fillDate;
  }
  
  public void setFillDate(Date fillDate) {
    this.fillDate = fillDate;
  }
  
  public Set getVoitureSendPO() {
    return this.voitureSendPO;
  }
  
  public void setVoitureSendPO(Set voitureSendPO) {
    this.voitureSendPO = voitureSendPO;
  }
  
  public String getStartTime() {
    return this.startTime;
  }
  
  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }
  
  public String getEndTime() {
    return this.endTime;
  }
  
  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }
  
  public String getPersonNum() {
    return this.personNum;
  }
  
  public void setPersonNum(String personNum) {
    this.personNum = personNum;
  }
  
  public String getMotorMan() {
    return this.motorMan;
  }
  
  public void setMotorMan(String motorMan) {
    this.motorMan = motorMan;
  }
  
  public String getVoitureStyle() {
    return this.voitureStyle;
  }
  
  public void setVoitureStyle(String voitureStyle) {
    this.voitureStyle = voitureStyle;
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public String getYcr() {
    return this.ycr;
  }
  
  public void setYcr(String ycr) {
    this.ycr = ycr;
  }
}
