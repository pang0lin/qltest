package com.js.oa.crm.po;

import java.io.Serializable;

public class CstFileHis implements Serializable {
  private long id;
  
  private String cstFileId;
  
  private String cstSellId;
  
  private String fileName;
  
  private String str1;
  
  private String str2;
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getCstSellId() {
    return this.cstSellId;
  }
  
  public void setCstSellId(String cstSellId) {
    this.cstSellId = cstSellId;
  }
  
  public String getFileName() {
    return this.fileName;
  }
  
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  
  public String getStr1() {
    return this.str1;
  }
  
  public void setStr1(String str1) {
    this.str1 = str1;
  }
  
  public String getStr2() {
    return this.str2;
  }
  
  public void setStr2(String str2) {
    this.str2 = str2;
  }
  
  public String getCstFileId() {
    return this.cstFileId;
  }
  
  public void setCstFileId(String cstFileId) {
    this.cstFileId = cstFileId;
  }
}
