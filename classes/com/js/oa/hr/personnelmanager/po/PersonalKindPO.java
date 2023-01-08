package com.js.oa.hr.personnelmanager.po;

import java.io.Serializable;

public class PersonalKindPO implements Serializable {
  private Long kindId;
  
  private String kindName;
  
  private String kindDescription;
  
  private Integer kindSort;
  
  public String getKindDescription() {
    return this.kindDescription;
  }
  
  public Long getKindId() {
    return this.kindId;
  }
  
  public void setKindName(String kindName) {
    this.kindName = kindName;
  }
  
  public void setKindDescription(String kindDescription) {
    this.kindDescription = kindDescription;
  }
  
  public void setKindId(Long kindId) {
    this.kindId = kindId;
  }
  
  public void setKindSort(Integer kindSort) {
    this.kindSort = kindSort;
  }
  
  public String getKindName() {
    return this.kindName;
  }
  
  public Integer getKindSort() {
    return this.kindSort;
  }
}
