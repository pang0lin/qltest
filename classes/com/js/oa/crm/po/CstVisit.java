package com.js.oa.crm.po;

import java.io.Serializable;

public class CstVisit implements Serializable {
  private long id;
  
  private String cstId;
  
  private String cstName;
  
  private String visitType;
  
  private String visitTypeName;
  
  private String visitTitle;
  
  private String visitContent;
  
  private String visitFeedBack;
  
  private String visitTime;
  
  private String createTime;
  
  private String createUserId;
  
  private String createUserName;
  
  private String visitDesc;
  
  private String hangYe;
  
  private String hangYeName;
  
  public String getHangYe() {
    return this.hangYe;
  }
  
  public void setHangYe(String hangYe) {
    this.hangYe = hangYe;
  }
  
  public String getHangYeName() {
    return this.hangYeName;
  }
  
  public void setHangYeName(String hangYeName) {
    this.hangYeName = hangYeName;
  }
  
  public String getCreateTime() {
    return this.createTime;
  }
  
  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }
  
  public String getCreateUserId() {
    return this.createUserId;
  }
  
  public void setCreateUserId(String createUserId) {
    this.createUserId = createUserId;
  }
  
  public String getCreateUserName() {
    return this.createUserName;
  }
  
  public void setCreateUserName(String createUserName) {
    this.createUserName = createUserName;
  }
  
  public String getCstId() {
    return this.cstId;
  }
  
  public void setCstId(String cstId) {
    this.cstId = cstId;
  }
  
  public String getCstName() {
    return this.cstName;
  }
  
  public void setCstName(String cstName) {
    this.cstName = cstName;
  }
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getVisitContent() {
    return this.visitContent;
  }
  
  public void setVisitContent(String visitContent) {
    this.visitContent = visitContent;
  }
  
  public String getVisitDesc() {
    return this.visitDesc;
  }
  
  public void setVisitDesc(String visitDesc) {
    this.visitDesc = visitDesc;
  }
  
  public String getVisitFeedBack() {
    return this.visitFeedBack;
  }
  
  public void setVisitFeedBack(String visitFeedBack) {
    this.visitFeedBack = visitFeedBack;
  }
  
  public String getVisitTime() {
    return this.visitTime;
  }
  
  public void setVisitTime(String visitTime) {
    this.visitTime = visitTime;
  }
  
  public String getVisitType() {
    return this.visitType;
  }
  
  public void setVisitType(String visitType) {
    this.visitType = visitType;
  }
  
  public String getVisitTitle() {
    return this.visitTitle;
  }
  
  public void setVisitTitle(String visitTitle) {
    this.visitTitle = visitTitle;
  }
  
  public String getVisitTypeName() {
    return this.visitTypeName;
  }
  
  public void setVisitTypeName(String visitTypeName) {
    this.visitTypeName = visitTypeName;
  }
}
