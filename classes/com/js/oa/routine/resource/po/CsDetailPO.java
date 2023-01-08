package com.js.oa.routine.resource.po;

import java.io.Serializable;

public class CsDetailPO implements Serializable {
  private Long id;
  
  private Integer seq;
  
  private String goodsId;
  
  private Float accAmount;
  
  private Float factAmount;
  
  private Float plAmount;
  
  private CsMasterPO csMaster;
  
  private String goodsName;
  
  private String goodsUnit;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Integer getSeq() {
    return this.seq;
  }
  
  public void setSeq(Integer seq) {
    this.seq = seq;
  }
  
  public String getGoodsId() {
    return this.goodsId;
  }
  
  public void setGoodsId(String goodsId) {
    this.goodsId = goodsId;
  }
  
  public Float getAccAmount() {
    return this.accAmount;
  }
  
  public void setAccAmount(Float accAmount) {
    this.accAmount = accAmount;
  }
  
  public Float getFactAmount() {
    return this.factAmount;
  }
  
  public void setFactAmount(Float factAmount) {
    this.factAmount = factAmount;
  }
  
  public Float getPlAmount() {
    return this.plAmount;
  }
  
  public void setPlAmount(Float plAmount) {
    this.plAmount = plAmount;
  }
  
  public CsMasterPO getCsMaster() {
    return this.csMaster;
  }
  
  public void setCsMaster(CsMasterPO csMaster) {
    this.csMaster = csMaster;
  }
  
  public String getGoodsName() {
    return this.goodsName;
  }
  
  public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
  }
  
  public String getGoodsUnit() {
    return this.goodsUnit;
  }
  
  public void setGoodsUnit(String goodsUnit) {
    this.goodsUnit = goodsUnit;
  }
}
