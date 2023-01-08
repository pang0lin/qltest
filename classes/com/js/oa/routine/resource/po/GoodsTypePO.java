package com.js.oa.routine.resource.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class GoodsTypePO implements Serializable {
  private Long id;
  
  private String name;
  
  private String remark;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private Set stock = null;
  
  private Set goods = null;
  
  private Long parentid;
  
  private String parentname;
  
  private int domainid;
  
  private Long stockId;
  
  private String goodsTypePrefix;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  public Long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(Long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public Long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(Long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof GoodsTypePO))
      return false; 
    GoodsTypePO castOther = (GoodsTypePO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public Set getStock() {
    return this.stock;
  }
  
  public void setStock(Set stock) {
    this.stock = stock;
  }
  
  public Set getGoods() {
    return this.goods;
  }
  
  public void setGoods(Set goods) {
    this.goods = goods;
  }
  
  public Long getParentid() {
    return this.parentid;
  }
  
  public void setParentid(Long parentid) {
    this.parentid = parentid;
  }
  
  public String getParentname() {
    return this.parentname;
  }
  
  public void setParentname(String parentname) {
    this.parentname = parentname;
  }
  
  public int getDomainid() {
    return this.domainid;
  }
  
  public Long getStockId() {
    return this.stockId;
  }
  
  public void setDomainid(int domainid) {
    this.domainid = domainid;
  }
  
  public void setStockId(Long stockId) {
    this.stockId = stockId;
  }
  
  public String getGoodsTypePrefix() {
    return this.goodsTypePrefix;
  }
  
  public void setGoodsTypePrefix(String goodsTypePrefix) {
    this.goodsTypePrefix = goodsTypePrefix;
  }
}
