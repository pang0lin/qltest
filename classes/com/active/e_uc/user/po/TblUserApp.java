package com.active.e_uc.user.po;

public class TblUserApp {
  private long id;
  
  private int app;
  
  private long func;
  
  private TblUser tblUser;
  
  public int getApp() {
    return this.app;
  }
  
  public void setApp(int app) {
    this.app = app;
  }
  
  public TblUser getTblUser() {
    return this.tblUser;
  }
  
  public void setTblUser(TblUser tblUser) {
    this.tblUser = tblUser;
  }
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public long getFunc() {
    return this.func;
  }
  
  public void setFunc(long func) {
    this.func = func;
  }
}
