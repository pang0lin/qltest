package com.active.e_uc.user.po;

public class TblDepartment {
  private int id;
  
  private int pid;
  
  private String name;
  
  private int orgid;
  
  private int grade;
  
  private String url;
  
  private byte showChildUser;
  
  public int getId() {
    return this.id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public int getOrgid() {
    return this.orgid;
  }
  
  public void setOrgid(int orgid) {
    this.orgid = orgid;
  }
  
  public int getGrade() {
    return this.grade;
  }
  
  public void setGrade(int grade) {
    this.grade = grade;
  }
  
  public String getUrl() {
    return this.url;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
  
  public byte getShowChildUser() {
    return this.showChildUser;
  }
  
  public void setShowChildUser(byte showChildUser) {
    this.showChildUser = showChildUser;
  }
  
  public int getPid() {
    return this.pid;
  }
  
  public void setPid(int pid) {
    this.pid = pid;
  }
}
