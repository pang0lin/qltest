package com.js.oa.routine.resource.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class StockActionForm extends ActionForm {
  private String stockName;
  
  private String stockPut;
  
  private String stockPutName;
  
  private String stockDesci;
  
  private String remark;
  
  private String stockApplyExtension;
  
  private String stockApplyExtensionId;
  
  private Integer chukuShenhe;
  
  private Integer isKucun;
  
  private Integer isKucunYj;
  
  private String shenhe1;
  
  private String shenhe2;
  
  private String shenhe3;
  
  private String shenhe4;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.chukuShenhe = new Integer(1);
    this.isKucun = new Integer(1);
  }
  
  public String getStockName() {
    return this.stockName;
  }
  
  public void setStockName(String stockName) {
    this.stockName = stockName;
  }
  
  public String getStockPut() {
    return this.stockPut;
  }
  
  public void setStockPut(String stockPut) {
    this.stockPut = stockPut;
  }
  
  public String getStockPutName() {
    return this.stockPutName;
  }
  
  public void setStockPutName(String stockPutName) {
    this.stockPutName = stockPutName;
  }
  
  public String getStockDesci() {
    return this.stockDesci;
  }
  
  public void setStockDesci(String stockDesci) {
    this.stockDesci = stockDesci;
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  public void setStockApplyExtension(String stockApplyExtension) {
    this.stockApplyExtension = stockApplyExtension;
  }
  
  public String getStockApplyExtension() {
    return this.stockApplyExtension;
  }
  
  public void setStockApplyExtensionId(String stockApplyExtensionId) {
    this.stockApplyExtensionId = stockApplyExtensionId;
  }
  
  public String getStockApplyExtensionId() {
    return this.stockApplyExtensionId;
  }
  
  public Integer getChukuShenhe() {
    return this.chukuShenhe;
  }
  
  public void setChukuShenhe(Integer chukuShenhe) {
    this.chukuShenhe = chukuShenhe;
  }
  
  public Integer getIsKucun() {
    return this.isKucun;
  }
  
  public String getShenhe4() {
    return this.shenhe4;
  }
  
  public String getShenhe3() {
    return this.shenhe3;
  }
  
  public String getShenhe2() {
    return this.shenhe2;
  }
  
  public String getShenhe1() {
    return this.shenhe1;
  }
  
  public void setIsKucun(Integer isKucun) {
    this.isKucun = isKucun;
  }
  
  public void setShenhe4(String shenhe4) {
    this.shenhe4 = shenhe4;
  }
  
  public void setShenhe3(String shenhe3) {
    this.shenhe3 = shenhe3;
  }
  
  public void setShenhe2(String shenhe2) {
    this.shenhe2 = shenhe2;
  }
  
  public void setShenhe1(String shenhe1) {
    this.shenhe1 = shenhe1;
  }
  
  public Integer getIsKucunYj() {
    return this.isKucunYj;
  }
  
  public void setIsKucunYj(Integer isKucunYj) {
    this.isKucunYj = isKucunYj;
  }
}
