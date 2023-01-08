package com.js.oa.crm.po;

import java.io.Serializable;

public class CstLinkThing implements Serializable {
  private long id;
  
  private String cstId;
  
  private String importThing;
  
  private String time;
  
  private String descThing;
  
  public String getCstId() {
    return this.cstId;
  }
  
  public void setCstId(String cstId) {
    this.cstId = cstId;
  }
  
  public String getDescThing() {
    return this.descThing;
  }
  
  public void setDescThing(String descThing) {
    this.descThing = descThing;
  }
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getImportThing() {
    return this.importThing;
  }
  
  public void setImportThing(String importThing) {
    this.importThing = importThing;
  }
  
  public String getTime() {
    return this.time;
  }
  
  public void setTime(String time) {
    this.time = time;
  }
}
