package com.js.system.action.logomanager;

import org.apache.struts.action.ActionForm;

public class LogoActionForm extends ActionForm {
  private Long logoId;
  
  private String logoName;
  
  private String logoPath;
  
  private String bakString;
  
  private String bakString1;
  
  public String getBakString() {
    return this.bakString;
  }
  
  public void setBakString(String bakString) {
    this.bakString = bakString;
  }
  
  public String getBakString1() {
    return this.bakString1;
  }
  
  public void setBakString1(String bakString1) {
    this.bakString1 = bakString1;
  }
  
  public Long getLogoId() {
    return this.logoId;
  }
  
  public void setLogoId(Long logoId) {
    this.logoId = logoId;
  }
  
  public String getLogoName() {
    return this.logoName;
  }
  
  public void setLogoName(String logoName) {
    this.logoName = logoName;
  }
  
  public String getLogoPath() {
    return this.logoPath;
  }
  
  public void setLogoPath(String logoPath) {
    this.logoPath = logoPath;
  }
}
