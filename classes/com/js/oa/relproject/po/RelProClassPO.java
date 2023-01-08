package com.js.oa.relproject.po;

public class RelProClassPO {
  private long id;
  
  private String name;
  
  private long createEmp;
  
  private String createEmpName;
  
  private long createOrg;
  
  private String createOrgName;
  
  private Integer classSortCode;
  
  private String classSort;
  
  private long parentId;
  
  private short level;
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public long getCreateEmp() {
    return this.createEmp;
  }
  
  public void setCreateEmp(long createEmp) {
    this.createEmp = createEmp;
  }
  
  public long getCreateOrg() {
    return this.createOrg;
  }
  
  public void setCreateOrg(long createOrg) {
    this.createOrg = createOrg;
  }
  
  public long getParentId() {
    return this.parentId;
  }
  
  public void setParentId(long parentId) {
    this.parentId = parentId;
  }
  
  public short getLevel() {
    return this.level;
  }
  
  public String getCreateEmpName() {
    return this.createEmpName;
  }
  
  public void setCreateEmpName(String createEmpName) {
    this.createEmpName = createEmpName;
  }
  
  public String getCreateOrgName() {
    return this.createOrgName;
  }
  
  public void setCreateOrgName(String createOrgName) {
    this.createOrgName = createOrgName;
  }
  
  public String getClassSort() {
    return this.classSort;
  }
  
  public void setClassSort(String classSort) {
    this.classSort = classSort;
  }
  
  public Integer getClassSortCode() {
    return this.classSortCode;
  }
  
  public void setClassSortCode(Integer classSortCode) {
    this.classSortCode = classSortCode;
  }
  
  public void setLevel(short level) {
    this.level = level;
  }
}
