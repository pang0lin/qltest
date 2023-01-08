package com.js.oa.eform.po;

import java.io.Serializable;
import java.util.Set;

public class TAreaTypePO implements Serializable {
  private String areaTypeName;
  
  private String areaTypeDes;
  
  private int areaTypeSort;
  
  private Set tarea = null;
  
  private Long id;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getAreaTypeName() {
    return this.areaTypeName;
  }
  
  public void setAreaTypeName(String areaTypeName) {
    this.areaTypeName = areaTypeName;
  }
  
  public String getAreaTypeDes() {
    return this.areaTypeDes;
  }
  
  public void setAreaTypeDes(String areaTypeDes) {
    this.areaTypeDes = areaTypeDes;
  }
  
  public int getAreaTypeSort() {
    return this.areaTypeSort;
  }
  
  public void setAreaTypeSort(int areaTypeSort) {
    this.areaTypeSort = areaTypeSort;
  }
  
  public Set getTarea() {
    return this.tarea;
  }
  
  public void setTarea(Set tarea) {
    this.tarea = tarea;
  }
}
