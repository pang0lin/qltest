package com.js.oa.relproject.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class RelProCatalogPO implements Serializable {
  private Long id;
  
  private String title;
  
  public RelProCatalogPO(Long id) {
    this.id = id;
  }
  
  public RelProCatalogPO() {}
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
}
