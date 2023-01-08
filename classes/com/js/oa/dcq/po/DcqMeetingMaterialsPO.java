package com.js.oa.dcq.po;

import java.io.Serializable;
import java.util.Date;

public class DcqMeetingMaterialsPO implements Serializable {
  private Long MaterialsId;
  
  private String dataName;
  
  private Date beginTime = null;
  
  private String readType;
  
  private String dataID;
  
  private String oid;
  
  private String meetingSubject;
  
  private String meetingBatch;
  
  private String meetingType;
  
  private Date registerDate = null;
  
  private String register;
  
  private String registerID;
  
  private String remark;
  
  private String mark;
  
  public Long getMaterialsId() {
    return this.MaterialsId;
  }
  
  public void setMaterialsId(Long materialsId) {
    this.MaterialsId = materialsId;
  }
  
  public String getDataName() {
    return this.dataName;
  }
  
  public void setDataName(String dataName) {
    this.dataName = dataName;
  }
  
  public Date getBeginTime() {
    return this.beginTime;
  }
  
  public void setBeginTime(Date beginTime) {
    this.beginTime = beginTime;
  }
  
  public String getReadType() {
    return this.readType;
  }
  
  public void setReadType(String readType) {
    this.readType = readType;
  }
  
  public String getDataID() {
    return this.dataID;
  }
  
  public void setDataID(String dataID) {
    this.dataID = dataID;
  }
  
  public String getOid() {
    return this.oid;
  }
  
  public void setOid(String oid) {
    this.oid = oid;
  }
  
  public String getMeetingSubject() {
    return this.meetingSubject;
  }
  
  public void setMeetingSubject(String meetingSubject) {
    this.meetingSubject = meetingSubject;
  }
  
  public String getMeetingBatch() {
    return this.meetingBatch;
  }
  
  public void setMeetingBatch(String meetingBatch) {
    this.meetingBatch = meetingBatch;
  }
  
  public String getMeetingType() {
    return this.meetingType;
  }
  
  public void setMeetingType(String meetingType) {
    this.meetingType = meetingType;
  }
  
  public Date getRegisterDate() {
    return this.registerDate;
  }
  
  public void setRegisterDate(Date registerDate) {
    this.registerDate = registerDate;
  }
  
  public String getRegister() {
    return this.register;
  }
  
  public void setRegister(String register) {
    this.register = register;
  }
  
  public String getRegisterID() {
    return this.registerID;
  }
  
  public void setRegisterID(String registerID) {
    this.registerID = registerID;
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  public String getMark() {
    return this.mark;
  }
  
  public void setMark(String mark) {
    this.mark = mark;
  }
}
