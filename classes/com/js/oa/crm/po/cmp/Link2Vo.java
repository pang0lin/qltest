package com.js.oa.crm.po.cmp;

public class Link2Vo {
  private Long id;
  
  private String value;
  
  public Link2Vo(Long id, String value) {
    this.id = id;
    this.value = value;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getValue() {
    return this.value;
  }
  
  public void setValue(String value) {
    this.value = value;
  }
}
