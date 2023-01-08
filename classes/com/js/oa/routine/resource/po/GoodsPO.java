package com.js.oa.routine.resource.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class GoodsPO implements Serializable {
  private String id;
  
  private String name;
  
  private String unit;
  
  private String price;
  
  private String remark;
  
  private String createdEmp;
  
  private String createdOrg;
  
  private GoodsTypePO goodsType;
  
  private String maxamount;
  
  private String minamount;
  
  private String stockId;
  
  private int domainid;
  
  private String specs;
  
  private String model;
  
  private Integer num;
  
  private String pic;
  
  private String firstStock;
  
  private Float averagePrice;
  
  private Integer status;
  
  public Integer getStatus() {
    return this.status;
  }
  
  public void setStatus(Integer status) {
    this.status = status;
  }
  
  public String getId() {
    return this.id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getUnit() {
    return this.unit;
  }
  
  public void setUnit(String unit) {
    this.unit = unit;
  }
  
  public String getPrice() {
    return this.price;
  }
  
  public void setPrice(String price) {
    this.price = price;
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  public String getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(String createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public String getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(String createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof GoodsPO))
      return false; 
    GoodsPO castOther = (GoodsPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public GoodsTypePO getGoodsType() {
    return this.goodsType;
  }
  
  public void setGoodsType(GoodsTypePO goodsType) {
    this.goodsType = goodsType;
  }
  
  public String getMaxamount() {
    return this.maxamount;
  }
  
  public void setMaxamount(String maxamount) {
    this.maxamount = maxamount;
  }
  
  public String getMinamount() {
    return this.minamount;
  }
  
  public void setMinamount(String minamount) {
    this.minamount = minamount;
  }
  
  public String getStockId() {
    return this.stockId;
  }
  
  public void setStockId(String stockId) {
    this.stockId = stockId;
  }
  
  public int getDomainid() {
    return this.domainid;
  }
  
  public void setDomainid(int domainid) {
    this.domainid = domainid;
  }
  
  public String getSpecs() {
    return this.specs;
  }
  
  public void setSpecs(String specs) {
    this.specs = specs;
  }
  
  public String getModel() {
    return this.model;
  }
  
  public void setModel(String model) {
    this.model = model;
  }
  
  public Integer getNum() {
    return this.num;
  }
  
  public void setNum(Integer num) {
    this.num = num;
  }
  
  public String getPic() {
    return this.pic;
  }
  
  public void setPic(String pic) {
    this.pic = pic;
  }
  
  public String getFirstStock() {
    return this.firstStock;
  }
  
  public void setFirstStock(String firstStock) {
    this.firstStock = firstStock;
  }
  
  public Float getAveragePrice() {
    return this.averagePrice;
  }
  
  public void setAveragePrice(Float averagePrice) {
    this.averagePrice = averagePrice;
  }
}
