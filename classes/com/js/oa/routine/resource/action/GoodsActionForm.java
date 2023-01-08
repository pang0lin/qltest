package com.js.oa.routine.resource.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GoodsActionForm extends ActionForm {
  private String id;
  
  private String goodsType;
  
  private String name;
  
  private String price;
  
  private String remark;
  
  private String unit;
  
  private String maxamount;
  
  private String minamount;
  
  private String stockId;
  
  private String specs;
  
  private String model;
  
  private Integer num;
  
  private String firstStock;
  
  public String getStockId() {
    return this.stockId;
  }
  
  public void setStockId(String stockId) {
    this.stockId = stockId;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.id = null;
    this.name = null;
    this.unit = null;
    this.price = null;
    this.remark = null;
    this.maxamount = null;
    this.minamount = null;
    this.specs = null;
  }
  
  public String getId() {
    return this.id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getGoodsType() {
    return this.goodsType;
  }
  
  public void setGoodsType(String goodsType) {
    this.goodsType = goodsType;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
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
  
  public String getUnit() {
    return this.unit;
  }
  
  public void setUnit(String unit) {
    this.unit = unit;
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
  
  public String getFirstStock() {
    return this.firstStock;
  }
  
  public void setFirstStock(String firstStock) {
    this.firstStock = firstStock;
  }
}
