package com.js.oa.routine.resource.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class GoodsStockPO implements Serializable {
  private Long id;
  
  private String goodsId;
  
  private Long stockId;
  
  private Float amount;
  
  private Float stockMoney;
  
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
  
  public Long getStockId() {
    return this.stockId;
  }
  
  public void setStockId(Long stockId) {
    this.stockId = stockId;
  }
  
  public Float getAmount() {
    return this.amount;
  }
  
  public void setAmount(Float amount) {
    this.amount = amount;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof GoodsStockPO))
      return false; 
    GoodsStockPO castOther = (GoodsStockPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public Float getStockMoney() {
    return this.stockMoney;
  }
  
  public void setStockMoney(Float stockMoney) {
    this.stockMoney = stockMoney;
  }
}
