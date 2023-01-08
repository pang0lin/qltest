package com.js.oa.portal.po;

import java.io.Serializable;

public class CustomurlPO implements Serializable {
  private Long id;
  
  private String urlname;
  
  private String urlapp;
  
  public CustomurlPO(long id, String urlname, String urlapp) {
    this.id = Long.valueOf(id);
    this.urlname = urlname;
    this.urlapp = urlapp;
  }
  
  public CustomurlPO() {}
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getUrlname() {
    return this.urlname;
  }
  
  public void setUrlname(String urlname) {
    this.urlname = urlname;
  }
  
  public String getUrlapp() {
    return this.urlapp;
  }
  
  public void setUrlapp(String urlapp) {
    this.urlapp = urlapp;
  }
}
