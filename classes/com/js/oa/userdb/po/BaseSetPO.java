package com.js.oa.userdb.po;

public class BaseSetPO {
  public Long baseId;
  
  public String baseName;
  
  public String baseKey;
  
  public String baseValue;
  
  public String baseType;
  
  public String baseParent;
  
  public String baseContent;
  
  public Long baseUser;
  
  public Long baseOrg;
  
  public int baseOrder;
  
  public int getBaseOrder() {
    return this.baseOrder;
  }
  
  public void setBaseOrder(int baseOrder) {
    this.baseOrder = baseOrder;
  }
  
  public Long getBaseUser() {
    return this.baseUser;
  }
  
  public void setBaseUser(Long baseUser) {
    this.baseUser = baseUser;
  }
  
  public Long getBaseOrg() {
    return this.baseOrg;
  }
  
  public void setBaseOrg(Long baseOrg) {
    this.baseOrg = baseOrg;
  }
  
  public Long getBaseId() {
    return this.baseId;
  }
  
  public void setBaseId(Long baseId) {
    this.baseId = baseId;
  }
  
  public String getBaseName() {
    return this.baseName;
  }
  
  public void setBaseName(String baseName) {
    this.baseName = baseName;
  }
  
  public String getBaseKey() {
    return this.baseKey;
  }
  
  public void setBaseKey(String baseKey) {
    this.baseKey = baseKey;
  }
  
  public String getBaseValue() {
    return this.baseValue;
  }
  
  public void setBaseValue(String baseValue) {
    this.baseValue = baseValue;
  }
  
  public String getBaseType() {
    return this.baseType;
  }
  
  public void setBaseType(String baseType) {
    this.baseType = baseType;
  }
  
  public String getBaseParent() {
    return this.baseParent;
  }
  
  public void setBaseParent(String baseParent) {
    this.baseParent = baseParent;
  }
  
  public String getBaseContent() {
    return this.baseContent;
  }
  
  public void setBaseContent(String baseContent) {
    this.baseContent = baseContent;
  }
}
