package com.js.oa.hr.kq.po;

public class KqOffsetPO {
  private long id;
  
  private long domainId;
  
  private int offsetTime;
  
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
  
  public int getOffsetTime() {
    return this.offsetTime;
  }
  
  public void setOffsetTime(int offsetTime) {
    this.offsetTime = offsetTime;
  }
}
