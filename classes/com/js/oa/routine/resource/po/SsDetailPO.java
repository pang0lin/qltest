package com.js.oa.routine.resource.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class SsDetailPO implements Serializable {
  private Long id;
  
  private Float amount;
  
  private Float price;
  
  private Float goodsMoney;
  
  private SsMasterPO ssMaster;
  
  private String goodsId;
  
  private String goodsName;
  
  private String goodsUnit;
  
  private String goodsSpecs;
  
  private String returnReason;
  
  private Float factPrice;
  
  private Float factMoney;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getGoodsId() {
    return this.goodsId;
  }
  
  public void setGoodsId(String goodsId) {
    this.goodsId = goodsId;
  }
  
  public Float getAmount() {
    return this.amount;
  }
  
  public void setAmount(Float amount) {
    this.amount = amount;
  }
  
  public Float getPrice() {
    return this.price;
  }
  
  public void setPrice(Float price) {
    this.price = price;
  }
  
  public Float getGoodsMoney() {
    return this.goodsMoney;
  }
  
  public void setGoodsMoney(Float goodsMoney) {
    this.goodsMoney = goodsMoney;
  }
  
  public SsMasterPO getSsMaster() {
    return this.ssMaster;
  }
  
  public void setSsMaster(SsMasterPO ssMaster) {
    this.ssMaster = ssMaster;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof SsDetailPO))
      return false; 
    SsDetailPO castOther = (SsDetailPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
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
  
  public String getGoodsSpecs() {
    return this.goodsSpecs;
  }
  
  public void setGoodsSpecs(String goodsSpecs) {
    this.goodsSpecs = goodsSpecs;
  }
  
  public String getReturnReason() {
    return this.returnReason;
  }
  
  public void setReturnReason(String returnReason) {
    this.returnReason = returnReason;
  }
  
  public Float getFactMoney() {
    return this.factMoney;
  }
  
  public void setFactMoney(Float factMoney) {
    this.factMoney = factMoney;
  }
  
  public Float getFactPrice() {
    return this.factPrice;
  }
  
  public void setFactPrice(Float factPrice) {
    this.factPrice = factPrice;
  }
}
