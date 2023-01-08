package com.js.oa.routine.resource.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class StockPO implements Serializable {
  private Long id;
  
  private String stockName;
  
  private String stockDesci;
  
  private String stockPut;
  
  private String remark;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private Set goodsType = null;
  
  private Set drawDept = null;
  
  private String stockPutName;
  
  private String chukuShenhe;
  
  private Integer isKucun;
  
  private Integer isKucunYj;
  
  private String stockApplyExtension;
  
  private String stockApplyExtensionId;
  
  private Set stockProcess = null;
  
  private int domainid;
  
  public void setStockApplyExtension(String stockApplyExtension) {
    this.stockApplyExtension = stockApplyExtension;
  }
  
  public String getStockApplyExtension() {
    return this.stockApplyExtension;
  }
  
  public String getStockApplyExtensionId() {
    return this.stockApplyExtensionId;
  }
  
  public void setStockApplyExtensionId(String stockApplyExtensionId) {
    this.stockApplyExtensionId = stockApplyExtensionId;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getStockName() {
    return this.stockName;
  }
  
  public void setStockName(String stockName) {
    this.stockName = stockName;
  }
  
  public String getStockDesci() {
    return this.stockDesci;
  }
  
  public void setStockDesci(String stockDesci) {
    this.stockDesci = stockDesci;
  }
  
  public String getStockPut() {
    return this.stockPut;
  }
  
  public void setStockPut(String stockPut) {
    this.stockPut = stockPut;
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
    if (!(other instanceof StockPO))
      return false; 
    StockPO castOther = (StockPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public Set getGoodsType() {
    return this.goodsType;
  }
  
  public void setGoodsType(Set goodsType) {
    this.goodsType = goodsType;
  }
  
  public Set getDrawDept() {
    return this.drawDept;
  }
  
  public void setDrawDept(Set drawDept) {
    this.drawDept = drawDept;
  }
  
  public String getStockPutName() {
    return this.stockPutName;
  }
  
  public void setStockPutName(String stockPutName) {
    this.stockPutName = stockPutName;
  }
  
  public Set getStockProcess() {
    return this.stockProcess;
  }
  
  public void setStockProcess(Set stockProcess) {
    this.stockProcess = stockProcess;
  }
  
  public String getChukuShenhe() {
    return this.chukuShenhe;
  }
  
  public void setChukuShenhe(String chukuShenhe) {
    this.chukuShenhe = chukuShenhe;
  }
  
  public Integer getIsKucun() {
    return this.isKucun;
  }
  
  public void setIsKucun(Integer isKucun) {
    this.isKucun = isKucun;
  }
  
  public int getDomainid() {
    return this.domainid;
  }
  
  public void setDomainid(int domainid) {
    this.domainid = domainid;
  }
  
  public Integer getIsKucunYj() {
    return this.isKucunYj;
  }
  
  public void setIsKucunYj(Integer isKucunYj) {
    this.isKucunYj = isKucunYj;
  }
}
