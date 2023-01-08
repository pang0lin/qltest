package com.js.oa.haier.po;

import java.io.Serializable;

public class ERPStockPO implements Serializable {
  private Long id;
  
  private String ERPth;
  
  private String ERPMC;
  
  private Long ZCKKC;
  
  private Long SHKC;
  
  private Long WFL;
  
  private Long KYKC;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getERPth() {
    return this.ERPth;
  }
  
  public void setERPth(String eRPth) {
    this.ERPth = eRPth;
  }
  
  public String getERPMC() {
    return this.ERPMC;
  }
  
  public void setERPMC(String eRPMC) {
    this.ERPMC = eRPMC;
  }
  
  public Long getZCKKC() {
    return this.ZCKKC;
  }
  
  public void setZCKKC(Long zCKKC) {
    this.ZCKKC = zCKKC;
  }
  
  public Long getSHKC() {
    return this.SHKC;
  }
  
  public void setSHKC(Long sHKC) {
    this.SHKC = sHKC;
  }
  
  public Long getWFL() {
    return this.WFL;
  }
  
  public void setWFL(Long wFL) {
    this.WFL = wFL;
  }
  
  public Long getKYKC() {
    return this.KYKC;
  }
  
  public void setKYKC(Long kYKC) {
    this.KYKC = kYKC;
  }
}
