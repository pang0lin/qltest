package com.js.oa.webmail.po;

import java.io.Serializable;

public class Affix implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private Long affixId;
  
  private String affixName;
  
  private String affixDisName;
  
  private String affixPath;
  
  private String mailId;
  
  private int affixSize;
  
  private String sizeStr;
  
  private String saveName;
  
  public Affix() {}
  
  public Affix(String mailId, String affixPath, String affixName, String affixDisName) {
    setMailId(mailId);
    setAffixPath(affixPath);
    setAffixName(affixName);
    setAffixDisName(affixDisName);
  }
  
  public String getMailId() {
    return this.mailId;
  }
  
  public void setMailId(String mailId) {
    this.mailId = mailId;
  }
  
  public Long getAffixId() {
    return this.affixId;
  }
  
  public void setAffixId(Long affixId) {
    this.affixId = affixId;
  }
  
  public String getAffixPath() {
    return this.affixPath;
  }
  
  public void setAffixPath(String affixPath) {
    this.affixPath = affixPath;
  }
  
  public int getAffixSize() {
    return this.affixSize;
  }
  
  public void setAffixSize(int affixSize) {
    this.affixSize = affixSize;
  }
  
  public String getAffixDisName() {
    return this.affixDisName;
  }
  
  public void setAffixDisName(String affixDisName) {
    this.affixDisName = affixDisName;
  }
  
  public String getAffixName() {
    return this.affixName;
  }
  
  public void setAffixName(String affixName) {
    this.affixName = affixName;
  }
  
  public String getSaveName() {
    return this.saveName;
  }
  
  public void setSaveName(String saveName) {
    this.saveName = saveName;
  }
  
  public String getSizeStr() {
    return this.sizeStr;
  }
  
  public void setSizeStr(String sizeStr) {
    this.sizeStr = sizeStr;
  }
}
