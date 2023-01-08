package com.js.oa.routine.resource.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class StockCheckActionForm extends ActionForm {
  private String csMasterId;
  
  private String csMan;
  
  private String stock;
  
  private String remark;
  
  private String makeMan;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getCsMasterId() {
    return this.csMasterId;
  }
  
  public void setCsMasterId(String csMasterId) {
    this.csMasterId = csMasterId;
  }
  
  public String getCsMan() {
    return this.csMan;
  }
  
  public void setCsMan(String csMan) {
    this.csMan = csMan;
  }
  
  public String getStock() {
    return this.stock;
  }
  
  public void setStock(String stock) {
    this.stock = stock;
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  public String getMakeMan() {
    return this.makeMan;
  }
  
  public void setMakeMan(String makeMan) {
    this.makeMan = makeMan;
  }
}
