package com.js.oa.routine.resource.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class StockChangePO implements Serializable {
  private Long id;
  
  private String scNo;
  
  private Date scDate = null;
  
  private Integer scSeq;
  
  private String changeType;
  
  private Long changeStock;
  
  private Long drawDept;
  
  private String goodsId;
  
  private Float changeAmount;
  
  private Float changeMoney;
  
  private Float factMoney;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getScNo() {
    return this.scNo;
  }
  
  public void setScNo(String scNo) {
    this.scNo = scNo;
  }
  
  public Date getScDate() {
    return this.scDate;
  }
  
  public void setScDate(Date scDate) {
    this.scDate = scDate;
  }
  
  public Integer getScSeq() {
    return this.scSeq;
  }
  
  public void setScSeq(Integer scSeq) {
    this.scSeq = scSeq;
  }
  
  public String getChangeType() {
    return this.changeType;
  }
  
  public void setChangeType(String changeType) {
    this.changeType = changeType;
  }
  
  public Long getChangeStock() {
    return this.changeStock;
  }
  
  public void setChangeStock(Long changeStock) {
    this.changeStock = changeStock;
  }
  
  public Long getDrawDept() {
    return this.drawDept;
  }
  
  public void setDrawDept(Long drawDept) {
    this.drawDept = drawDept;
  }
  
  public String getGoodsId() {
    return this.goodsId;
  }
  
  public void setGoodsId(String goodsId) {
    this.goodsId = goodsId;
  }
  
  public Float getChangeAmount() {
    return this.changeAmount;
  }
  
  public void setChangeAmount(Float changeAmount) {
    this.changeAmount = changeAmount;
  }
  
  public Float getChangeMoney() {
    return this.changeMoney;
  }
  
  public void setChangeMoney(Float changeMoney) {
    this.changeMoney = changeMoney;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof StockChangePO))
      return false; 
    StockChangePO castOther = (StockChangePO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public Float getFactMoney() {
    return this.factMoney;
  }
  
  public void setFactMoney(Float factMoney) {
    this.factMoney = factMoney;
  }
}
