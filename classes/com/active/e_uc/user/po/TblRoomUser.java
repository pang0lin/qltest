package com.active.e_uc.user.po;

public class TblRoomUser {
  private int id;
  
  private TblUser tblUser;
  
  private int cid;
  
  private byte utp;
  
  private String startTime;
  
  private String endTiem;
  
  private int grade;
  
  private int consumeType;
  
  public int getId() {
    return this.id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public int getCid() {
    return this.cid;
  }
  
  public void setCid(int cid) {
    this.cid = cid;
  }
  
  public byte getUtp() {
    return this.utp;
  }
  
  public void setUtp(byte utp) {
    this.utp = utp;
  }
  
  public String getStartTime() {
    return this.startTime;
  }
  
  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }
  
  public String getEndTiem() {
    return this.endTiem;
  }
  
  public void setEndTiem(String endTiem) {
    this.endTiem = endTiem;
  }
  
  public int getGrade() {
    return this.grade;
  }
  
  public void setGrade(int grade) {
    this.grade = grade;
  }
  
  public int getConsumeType() {
    return this.consumeType;
  }
  
  public void setConsumeType(int consumeType) {
    this.consumeType = consumeType;
  }
  
  public TblUser getTblUser() {
    return this.tblUser;
  }
  
  public void setTblUser(TblUser tblUser) {
    this.tblUser = tblUser;
  }
}
