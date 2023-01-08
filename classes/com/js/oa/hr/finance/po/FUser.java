package com.js.oa.hr.finance.po;

import java.io.Serializable;
import java.util.Date;

public class FUser implements Serializable {
  private Long id;
  
  private String userAccounts;
  
  private String userPassword;
  
  private Long empId;
  
  private String empName;
  
  private String empNumber;
  
  private Long orgId;
  
  private String orgName;
  
  private Date createdDate = null;
  
  public FUser() {}
  
  public FUser(String userAccounts, String userPassword, Long empId, String empName, String empNumber, Long orgId, String orgName, Date createdDate) {
    this.userAccounts = userAccounts;
    this.userPassword = userPassword;
    this.empId = empId;
    this.empName = empName;
    this.empNumber = empNumber;
    this.orgId = orgId;
    this.orgName = orgName;
    this.createdDate = createdDate;
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
  
  public String getUserPassword() {
    return this.userPassword;
  }
  
  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
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
}
