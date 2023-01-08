package com.js.system.vo.logomanager;

import java.io.Serializable;

public class LogoVO implements Serializable {
  private Long logoId;
  
  private String logoName;
  
  private String logoPath;
  
  private String bakString;
  
  private String bakString1;
  
  private String companyName;
  
  private String isdisplayLogo;
  
  private String isdisplayCompanyName;
  
  private String companyColor;
  
  private String logoType;
  
  private String orgId;
  
  public String getCompanyName() {
    return this.companyName;
  }
  
  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }
  
  public String getIsdisplayLogo() {
    return this.isdisplayLogo;
  }
  
  public void setIsdisplayLogo(String isdisplayLogo) {
    this.isdisplayLogo = isdisplayLogo;
  }
  
  public String getIsdisplayCompanyName() {
    return this.isdisplayCompanyName;
  }
  
  public void setIsdisplayCompanyName(String isdisplayCompanyName) {
    this.isdisplayCompanyName = isdisplayCompanyName;
  }
  
  public String getCompanyColor() {
    return this.companyColor;
  }
  
  public void setCompanyColor(String companyColor) {
    this.companyColor = companyColor;
  }
  
  public String getLogoType() {
    return this.logoType;
  }
  
  public void setLogoType(String logoType) {
    this.logoType = logoType;
  }
  
  public String getOrgId() {
    return this.orgId;
  }
  
  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }
  
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
