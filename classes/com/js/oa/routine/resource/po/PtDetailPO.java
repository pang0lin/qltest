package com.js.oa.routine.resource.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class PtDetailPO implements Serializable {
  private Long id;
  
  private Float amount;
  
  private Float mcost;
  
  private Float goodsMoney;
  
  private PtMasterPO ptMaster;
  
  private String goodsId;
  
  private String goodsName;
  
  private String goodsUnit;
  
  private Integer seq;
  
  private String goodsSpecs;
  
  private String returnReason;
  
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
  
  public Float getMcost() {
    return this.mcost;
  }
  
  public void setMcost(Float mcost) {
    this.mcost = mcost;
  }
  
  public Float getGoodsMoney() {
    return this.goodsMoney;
  }
  
  public void setGoodsMoney(Float goodsMoney) {
    this.goodsMoney = goodsMoney;
  }
  
  public PtMasterPO getPtMaster() {
    return this.ptMaster;
  }
  
  public void setPtMaster(PtMasterPO ptMaster) {
    this.ptMaster = ptMaster;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof PtDetailPO))
      return false; 
    PtDetailPO castOther = (PtDetailPO)other;
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
  
  public Integer getSeq() {
    return this.seq;
  }
  
  public void setSeq(Integer seq) {
    this.seq = seq;
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
}
