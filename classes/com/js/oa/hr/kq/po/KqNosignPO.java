package com.js.oa.hr.kq.po;

public class KqNosignPO {
  private long id;
  
  private long domainId;
  
  private String nosignName;
  
  private String userIds;
  
  private String userNames;
  
  private String nosignType;
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(long domainId) {
    this.domainId = domainId;
  }
  
  public String getNosignName() {
    return this.nosignName;
  }
  
  public void setNosignName(String nosignName) {
    this.nosignName = nosignName;
  }
  
  public String getUserIds() {
    return this.userIds;
  }
  
  public void setUserIds(String userIds) {
    this.userIds = userIds;
  }
  
  public String getUserNames() {
    return this.userNames;
  }
  
  public void setUserNames(String userNames) {
    this.userNames = userNames;
  }
  
  public String getNosignType() {
    return this.nosignType;
  }
  
  public void setNosignType(String nosignType) {
    this.nosignType = nosignType;
  }
}
