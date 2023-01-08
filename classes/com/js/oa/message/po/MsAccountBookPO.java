package com.js.oa.message.po;

public class MsAccountBookPO {
  private String bookCount;
  
  private String bookMoney;
  
  private String bookTime;
  
  private Long domainId;
  
  private Long bookId;
  
  public void setBookCount(String bookCount) {
    this.bookCount = bookCount;
  }
  
  public void setBookMoney(String bookMoney) {
    this.bookMoney = bookMoney;
  }
  
  public void setBookTime(String bookTime) {
    this.bookTime = bookTime;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public void setBookId(Long bookId) {
    this.bookId = bookId;
  }
  
  public String getBookCount() {
    return this.bookCount;
  }
  
  public String getBookMoney() {
    return this.bookMoney;
  }
  
  public String getBookTime() {
    return this.bookTime;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public Long getBookId() {
    return this.bookId;
  }
}
