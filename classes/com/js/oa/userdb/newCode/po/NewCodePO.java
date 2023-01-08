package com.js.oa.userdb.newCode.po;

public class NewCodePO {
  private long codeId;
  
  private String codeName;
  
  private String codeContent;
  
  private int codeStatus;
  
  public long getCodeId() {
    return this.codeId;
  }
  
  public void setCodeId(long codeId) {
    this.codeId = codeId;
  }
  
  public String getCodeName() {
    return this.codeName;
  }
  
  public void setCodeName(String codeName) {
    this.codeName = codeName;
  }
  
  public String getCodeContent() {
    return this.codeContent;
  }
  
  public void setCodeContent(String codeContent) {
    this.codeContent = codeContent;
  }
  
  public int getCodeStatus() {
    return this.codeStatus;
  }
  
  public void setCodeStatus(int codeStatus) {
    this.codeStatus = codeStatus;
  }
}
