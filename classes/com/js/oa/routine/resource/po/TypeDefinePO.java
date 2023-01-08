package com.js.oa.routine.resource.po;

import java.io.Serializable;

public class TypeDefinePO implements Serializable {
  private Long id;
  
  private String typeDefineName;
  
  private String typeDefineMode;
  
  private Long domainID;
  
  public Long getId() {
    return this.id;
  }
  
  public String getTypeDefineMode() {
    return this.typeDefineMode;
  }
  
  public void setTypeDefineMode(String typeDefineMode) {
    this.typeDefineMode = typeDefineMode;
  }
  
  public String getTypeDefineName() {
    return this.typeDefineName;
  }
  
  public void setTypeDefineName(String typeDefineName) {
    this.typeDefineName = typeDefineName;
  }
  
  public Long getDomainID() {
    return this.domainID;
  }
  
  public void setDomainID(Long domainID) {
    this.domainID = domainID;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
}
