package com.js.oa.routine.resource.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class GoodsTypeActionForm extends ActionForm {
  private String stockId;
  
  private String goodsTypeName;
  
  private String remark;
  
  private String parentTypeId;
  
  private String parentTypeName;
  
  private String goodsTypePrefix;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getStockId() {
    return this.stockId;
  }
  
  public void setStockId(String stockId) {
    this.stockId = stockId;
  }
  
  public String getGoodsTypeName() {
    return this.goodsTypeName;
  }
  
  public void setGoodsTypeName(String goodsTypeName) {
    this.goodsTypeName = goodsTypeName;
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  public String getParentTypeId() {
    return this.parentTypeId;
  }
  
  public void setParentTypeId(String parentTypeId) {
    this.parentTypeId = parentTypeId;
  }
  
  public String getParentTypeName() {
    return this.parentTypeName;
  }
  
  public void setParentTypeName(String parentTypeName) {
    this.parentTypeName = parentTypeName;
  }
  
  public String getGoodsTypePrefix() {
    return this.goodsTypePrefix;
  }
  
  public void setGoodsTypePrefix(String goodsTypePrefix) {
    this.goodsTypePrefix = goodsTypePrefix;
  }
}
