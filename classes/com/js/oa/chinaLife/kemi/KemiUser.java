package com.js.oa.chinaLife.kemi;

public class KemiUser {
  private String OrgId;
  
  private String DeptName;
  
  private String EmpCode;
  
  private String EmpName;
  
  private String RegCode;
  
  private String CardCode;
  
  private String JoinDate;
  
  private String sex;
  
  private String IdCard;
  
  private String StatusID;
  
  private String DimissionDate;
  
  public String toString() {
    String toString = "<TB><REC><OrgID>" + 
      
      getOrgId() + "</OrgID>" + 
      "<DeptName>" + getDeptName() + "</DeptName>" + 
      "<EmpCode>" + getEmpCode() + "</EmpCode>" + 
      "<EmpName>" + getEmpName() + "</EmpName>" + 
      "<RegCode>" + ((getRegCode() == null) ? "" : getRegCode()) + "</RegCode>" + 
      "<CardCode>" + ((getCardCode() == null) ? "" : getCardCode()) + "</CardCode>" + 
      "<IdCard>" + ((getIdCard() == null) ? "" : getIdCard()) + "</IdCard>" + 
      "<JoinDate>" + ((getJoinDate() == null) ? "" : getJoinDate()) + "</JoinDate>" + 
      "<sex>" + ((getSex() == null) ? "" : getSex()) + "</sex>" + 
      "<StatusID>" + ((getStatusID() == null) ? "1" : getStatusID()) + "</StatusID>" + 
      "<DimissionDate>" + ((getDimissionDate() == null) ? "" : getDimissionDate()) + "</DimissionDate>" + 
      "</REC>" + 
      "</TB>";
    return toString;
  }
  
  public String getOrgId() {
    return this.OrgId;
  }
  
  public void setOrgId(String orgId) {
    this.OrgId = orgId;
  }
  
  public String getDeptName() {
    return this.DeptName;
  }
  
  public void setDeptName(String deptName) {
    this.DeptName = deptName;
  }
  
  public String getEmpCode() {
    return this.EmpCode;
  }
  
  public void setEmpCode(String empCode) {
    this.EmpCode = empCode;
  }
  
  public String getEmpName() {
    return this.EmpName;
  }
  
  public void setEmpName(String empName) {
    this.EmpName = empName;
  }
  
  public String getRegCode() {
    return this.RegCode;
  }
  
  public void setRegCode(String regCode) {
    this.RegCode = regCode;
  }
  
  public String getCardCode() {
    return this.CardCode;
  }
  
  public void setCardCode(String cardCode) {
    this.CardCode = cardCode;
  }
  
  public String getJoinDate() {
    return this.JoinDate;
  }
  
  public void setJoinDate(String joinDate) {
    this.JoinDate = joinDate;
  }
  
  public String getSex() {
    return this.sex;
  }
  
  public void setSex(String sex) {
    this.sex = sex;
  }
  
  public String getIdCard() {
    return this.IdCard;
  }
  
  public void setIdCard(String idCard) {
    this.IdCard = idCard;
  }
  
  public String getStatusID() {
    return this.StatusID;
  }
  
  public void setStatusID(String statusID) {
    this.StatusID = statusID;
  }
  
  public String getDimissionDate() {
    return this.DimissionDate;
  }
  
  public void setDimissionDate(String dimissionDate) {
    this.DimissionDate = dimissionDate;
  }
}
