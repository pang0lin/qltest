package com.js.oa.routine.voiture.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class VoitureApplyActionForm extends ActionForm {
  private Long id;
  
  private String orgId;
  
  private String destination;
  
  private String reason;
  
  private String orgName;
  
  private String empName;
  
  private Long voitureId;
  
  private String empId;
  
  private String endHour;
  
  private String status;
  
  private String startHour;
  
  private String startDate;
  
  private String endDate;
  
  private String fillDate;
  
  private String startMinute;
  
  private String endMinute;
  
  private String voitureName;
  
  private String personNum;
  
  private String motorMan;
  
  private String voitureStyle = "1";
  
  private String remark;
  
  private String ycr;
  
  private String departurePlace;
  
  private String vehiclePhone;
  
  private String vehicleNum;
  
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
  
  public String getStartDate() {
    return this.startDate;
  }
  
  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }
  
  public String getStartHour() {
    return this.startHour;
  }
  
  public void setStartHour(String startHour) {
    this.startHour = startHour;
  }
  
  public String getEndDate() {
    return this.endDate;
  }
  
  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }
  
  public String getEndHour() {
    return this.endHour;
  }
  
  public void setEndHour(String endHour) {
    this.endHour = endHour;
  }
  
  public String getStatus() {
    return this.status;
  }
  
  public void setStatus(String status) {
    this.status = status;
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
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public Long getVoitureId() {
    return this.voitureId;
  }
  
  public void setVoitureId(Long voitureId) {
    this.voitureId = voitureId;
  }
  
  public String getFillDate() {
    return this.fillDate;
  }
  
  public void setFillDate(String fillDate) {
    this.fillDate = fillDate;
  }
  
  public String getStartMinute() {
    return this.startMinute;
  }
  
  public void setStartMinute(String startMinute) {
    this.startMinute = startMinute;
  }
  
  public String getEndMinute() {
    return this.endMinute;
  }
  
  public void setEndMinute(String endMinute) {
    this.endMinute = endMinute;
  }
  
  public String getVoitureName() {
    return this.voitureName;
  }
  
  public void setVoitureName(String voitureName) {
    this.voitureName = voitureName;
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
  
  public String getYcr() {
    return this.ycr;
  }
  
  public void setYcr(String ycr) {
    this.ycr = ycr;
  }
}
