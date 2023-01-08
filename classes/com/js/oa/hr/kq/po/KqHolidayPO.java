package com.js.oa.hr.kq.po;

import java.util.Date;

public class KqHolidayPO {
  private long id;
  
  private long domainId;
  
  private String holidayName;
  
  private Date beginDate = null;
  
  private Date endDate = null;
  
  private String memo;
  
  private int type;
  
  private String paibanSet;
  
  private String corpId;
  
  private String reduceAnnual;
  
  private String reduceTrial;
  
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
  
  public String getHolidayName() {
    return this.holidayName;
  }
  
  public void setHolidayName(String holidayName) {
    this.holidayName = holidayName;
  }
  
  public Date getBeginDate() {
    return this.beginDate;
  }
  
  public void setBeginDate(Date beginDate) {
    this.beginDate = beginDate;
  }
  
  public Date getEndDate() {
    return this.endDate;
  }
  
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  
  public String getMemo() {
    return this.memo;
  }
  
  public void setMemo(String memo) {
    this.memo = memo;
  }
  
  public int getType() {
    return this.type;
  }
  
  public void setType(int type) {
    this.type = type;
  }
  
  public String getPaibanSet() {
    return this.paibanSet;
  }
  
  public void setPaibanSet(String paibanSet) {
    this.paibanSet = paibanSet;
  }
  
  public String getCorpId() {
    return this.corpId;
  }
  
  public void setCorpId(String corpId) {
    this.corpId = corpId;
  }
  
  public String getReduceAnnual() {
    return this.reduceAnnual;
  }
  
  public void setReduceAnnual(String reduceAnnual) {
    this.reduceAnnual = reduceAnnual;
  }
  
  public String getReduceTrial() {
    return this.reduceTrial;
  }
  
  public void setReduceTrial(String reduceTrial) {
    this.reduceTrial = reduceTrial;
  }
}
