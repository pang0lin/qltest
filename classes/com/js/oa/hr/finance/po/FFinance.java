package com.js.oa.hr.finance.po;

import java.io.Serializable;
import java.sql.Date;

public class FFinance implements Serializable {
  private Long id;
  
  private String userAccounts;
  
  private Long empId;
  
  private String empName;
  
  private String empNumber;
  
  private Long orgId;
  
  private String orgName;
  
  private Date createdDate = null;
  
  private String content;
  
  private Long salaryType;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  public FFinance() {}
  
  public FFinance(String userAccounts, Long empId, String empName, String empNumber, Long orgId, String orgName, Date createdDate, String content, Long salaryType, Long createdEmp, Long createdOrg) {
    this.userAccounts = userAccounts;
    this.empId = empId;
    this.empName = empName;
    this.empNumber = empNumber;
    this.orgId = orgId;
    this.orgName = orgName;
    this.createdDate = createdDate;
    this.content = content;
    this.salaryType = salaryType;
    this.createdEmp = createdEmp;
    this.createdOrg = createdOrg;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getUserAccounts() {
    return this.userAccounts;
  }
  
  public void setUserAccounts(String userAccounts) {
    this.userAccounts = userAccounts;
  }
  
  public Long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(Long empId) {
    this.empId = empId;
  }
  
  public String getEmpName() {
    return this.empName;
  }
  
  public void setEmpName(String empName) {
    this.empName = empName;
  }
  
  public String getEmpNumber() {
    return this.empNumber;
  }
  
  public void setEmpNumber(String empNumber) {
    this.empNumber = empNumber;
  }
  
  public Long getOrgId() {
    return this.orgId;
  }
  
  public void setOrgId(Long orgId) {
    this.orgId = orgId;
  }
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public Date getCreatedDate() {
    return this.createdDate;
  }
  
  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public Long getSalaryType() {
    return this.salaryType;
  }
  
  public void setSalaryType(Long salaryType) {
    this.salaryType = salaryType;
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
}
