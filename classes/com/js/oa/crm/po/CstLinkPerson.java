package com.js.oa.crm.po;

import java.io.Serializable;

public class CstLinkPerson implements Serializable {
  private long id;
  
  private String cstId;
  
  private String name;
  
  private String zhiwu;
  
  private String tele;
  
  private String email;
  
  private String tele1;
  
  private String techang;
  
  public String getTechang() {
    return this.techang;
  }
  
  public void setTechang(String techang) {
    this.techang = techang;
  }
  
  public String getTele1() {
    return this.tele1;
  }
  
  public void setTele1(String tele1) {
    this.tele1 = tele1;
  }
  
  public String getCstId() {
    return this.cstId;
  }
  
  public void setCstId(String cstId) {
    this.cstId = cstId;
  }
  
  public String getEmail() {
    return this.email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getTele() {
    return this.tele;
  }
  
  public void setTele(String tele) {
    this.tele = tele;
  }
  
  public String getZhiwu() {
    return this.zhiwu;
  }
  
  public void setZhiwu(String zhiwu) {
    this.zhiwu = zhiwu;
  }
}
