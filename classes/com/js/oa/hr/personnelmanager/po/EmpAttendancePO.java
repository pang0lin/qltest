package com.js.oa.hr.personnelmanager.po;

import com.js.system.vo.usermanager.EmployeeVO;
import java.io.Serializable;

public class EmpAttendancePO implements Serializable {
  private Long id;
  
  private EmployeeVO emp = null;
  
  private Long empOrg;
  
  private String empName;
  
  private String yearMonth;
  
  private Float planHours;
  
  private Float realHours;
  
  private Float delayHours;
  
  private Float dbrestHours;
  
  private Float officialHours;
  
  private Float leaveHours;
  
  private Float offworkHours;
  
  private Float offtuneHours;
  
  private Float uncardHours;
  
  private Float lateHours;
  
  private String descriptions;
  
  public Float getDelayHours() {
    return this.delayHours;
  }
  
  public Float getOfficialHours() {
    return this.officialHours;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public Float getLateHours() {
    return this.lateHours;
  }
  
  public EmployeeVO getEmp() {
    return this.emp;
  }
  
  public Float getOffworkHours() {
    return this.offworkHours;
  }
  
  public Float getUncardHours() {
    return this.uncardHours;
  }
  
  public Long getEmpOrg() {
    return this.empOrg;
  }
  
  public Float getPlanHours() {
    return this.planHours;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public Float getRealHours() {
    return this.realHours;
  }
  
  public String getYearMonth() {
    return this.yearMonth;
  }
  
  public Float getOfftuneHours() {
    return this.offtuneHours;
  }
  
  public Float getDbrestHours() {
    return this.dbrestHours;
  }
  
  public Float getLeaveHours() {
    return this.leaveHours;
  }
  
  public void setDescriptions(String descriptions) {
    this.descriptions = descriptions;
  }
  
  public void setDelayHours(Float delayHours) {
    this.delayHours = delayHours;
  }
  
  public void setOfficialHours(Float officialHours) {
    this.officialHours = officialHours;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public void setLateHours(Float lateHours) {
    this.lateHours = lateHours;
  }
  
  public void setEmp(EmployeeVO emp) {
    this.emp = emp;
  }
  
  public void setOffworkHours(Float offworkHours) {
    this.offworkHours = offworkHours;
  }
  
  public void setUncardHours(Float uncardHours) {
    this.uncardHours = uncardHours;
  }
  
  public void setEmpOrg(Long empOrg) {
    this.empOrg = empOrg;
  }
  
  public void setPlanHours(Float planHours) {
    this.planHours = planHours;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setRealHours(Float realHours) {
    this.realHours = realHours;
  }
  
  public void setYearMonth(String yearMonth) {
    this.yearMonth = yearMonth;
  }
  
  public void setOfftuneHours(Float offtuneHours) {
    this.offtuneHours = offtuneHours;
  }
  
  public void setDbrestHours(Float dbrestHours) {
    this.dbrestHours = dbrestHours;
  }
  
  public void setLeaveHours(Float leaveHours) {
    this.leaveHours = leaveHours;
  }
  
  public String getDescriptions() {
    return this.descriptions;
  }
}
