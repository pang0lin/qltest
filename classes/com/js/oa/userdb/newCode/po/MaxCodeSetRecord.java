package com.js.oa.userdb.newCode.po;

public class MaxCodeSetRecord {
  private long codeId;
  
  private long codeSetId;
  
  private String dateFormat;
  
  private int num;
  
  private int isUsered;
  
  public long getCodeId() {
    return this.codeId;
  }
  
  public void setCodeId(long codeId) {
    this.codeId = codeId;
  }
  
  public long getCodeSetId() {
    return this.codeSetId;
  }
  
  public void setCodeSetId(long codeSetId) {
    this.codeSetId = codeSetId;
  }
  
  public int getNum() {
    return this.num;
  }
  
  public void setNum(int num) {
    this.num = num;
  }
  
  public String getDateFormat() {
    return this.dateFormat;
  }
  
  public void setDateFormat(String dateFormat) {
    this.dateFormat = dateFormat;
  }
  
  public int getIsUsered() {
    return this.isUsered;
  }
  
  public void setIsUsered(int isUsered) {
    this.isUsered = isUsered;
  }
}
