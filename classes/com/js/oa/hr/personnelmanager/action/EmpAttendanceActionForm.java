package com.js.oa.hr.personnelmanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EmpAttendanceActionForm extends ActionForm {
  private Long id;
  
  private String yearMonth;
  
  private String year;
  
  private String month;
  
  private String planHours;
  
  private String realHours;
  
  private String delayHours;
  
  private String dbrestHours;
  
  private String officialHours;
  
  private String leaveHours;
  
  private String offworkHours;
  
  private String offtuneHours;
  
  private String uncardHours;
  
  private String lateHours;
  
  private String descriptions;
  
  private String empID;
  
  private String empName;
  
  private String empOrg;
  
  private String orgName;
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public String getDelayHours() {
    return this.delayHours;
  }
  
  public String getOfficialHours() {
    return this.officialHours;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public String getLateHours() {
    return this.lateHours;
  }
  
  public String getUncardHours() {
    return this.uncardHours;
  }
  
  public String getOffworkHours() {
    return this.offworkHours;
  }
  
  public String getEmpOrg() {
    return this.empOrg;
  }
  
  public String getPlanHours() {
    return this.planHours;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public String getEmpID() {
    return this.empID;
  }
  
  public String getRealHours() {
    return this.realHours;
  }
  
  public String getYearMonth() {
    return this.yearMonth;
  }
  
  public String getOfftuneHours() {
    return this.offtuneHours;
  }
  
  public String getDbrestHours() {
    return this.dbrestHours;
  }
  
  public String getDescriptions() {
    return this.descriptions;
  }
  
  public void setLeaveHours(String leaveHours) {
    this.leaveHours = leaveHours;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public void setDelayHours(String delayHours) {
    this.delayHours = delayHours;
  }
  
  public void setOfficialHours(String officialHours) {
    this.officialHours = officialHours;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public void setLateHours(String lateHours) {
    this.lateHours = lateHours;
  }
  
  public void setUncardHours(String uncardHours) {
    this.uncardHours = uncardHours;
  }
  
  public void setOffworkHours(String offworkHours) {
    this.offworkHours = offworkHours;
  }
  
  public void setEmpOrg(String empOrg) {
    this.empOrg = empOrg;
  }
  
  public void setPlanHours(String planHours) {
    this.planHours = planHours;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setEmpID(String empID) {
    this.empID = empID;
  }
  
  public void setRealHours(String realHours) {
    this.realHours = realHours;
  }
  
  public void setYearMonth(String yearMonth) {
    this.yearMonth = yearMonth;
  }
  
  public void setOfftuneHours(String offtuneHours) {
    this.offtuneHours = offtuneHours;
  }
  
  public void setDbrestHours(String dbrestHours) {
    this.dbrestHours = dbrestHours;
  }
  
  public void setDescriptions(String descriptions) {
    this.descriptions = descriptions;
  }
  
  public void setYear(String year) {
    this.year = year;
  }
  
  public void setMonth(String month) {
    this.month = month;
  }
  
  public String getLeaveHours() {
    return this.leaveHours;
  }
  
  public String getYear() {
    return this.year;
  }
  
  public String getMonth() {
    return this.month;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
}
