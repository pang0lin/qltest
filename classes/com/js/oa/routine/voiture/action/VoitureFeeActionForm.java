package com.js.oa.routine.voiture.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class VoitureFeeActionForm extends ActionForm {
  private String maintainTime;
  
  private String oilCost;
  
  private String oilFee;
  
  private String fixFee;
  
  private String washFee;
  
  private String taxFee;
  
  private String insureFee;
  
  private String roadFee;
  
  private String yearTicketFee;
  
  private String yearSensorFee;
  
  private String purchaseTax;
  
  private String brandFee;
  
  private String accidentFee;
  
  private String otherFee;
  
  private String remark;
  
  private String isMaintain = "0";
  
  private String monthFee;
  
  private String jsy;
  
  private String fwld;
  
  private String sylc;
  
  private String bylc;
  
  private String xslc;
  
  private String yy;
  
  private String bglhy;
  
  public String getJsy() {
    return this.jsy;
  }
  
  public void setJsy(String jsy) {
    this.jsy = jsy;
  }
  
  public String getFwld() {
    return this.fwld;
  }
  
  public void setFwld(String fwld) {
    this.fwld = fwld;
  }
  
  public String getSylc() {
    return this.sylc;
  }
  
  public void setSylc(String sylc) {
    this.sylc = sylc;
  }
  
  public String getBylc() {
    return this.bylc;
  }
  
  public void setBylc(String bylc) {
    this.bylc = bylc;
  }
  
  public String getXslc() {
    return this.xslc;
  }
  
  public void setXslc(String xslc) {
    this.xslc = xslc;
  }
  
  public String getYy() {
    return this.yy;
  }
  
  public void setYy(String yy) {
    this.yy = yy;
  }
  
  public String getBglhy() {
    return this.bglhy;
  }
  
  public void setBglhy(String bglhy) {
    this.bglhy = bglhy;
  }
  
  public void setMaintainTime(String maintainTime) {
    this.maintainTime = maintainTime;
  }
  
  public String getMaintainTime() {
    return this.maintainTime;
  }
  
  public void setOilCost(String oilCost) {
    this.oilCost = oilCost;
  }
  
  public String getOilCost() {
    return this.oilCost;
  }
  
  public void setOilFee(String oilFee) {
    this.oilFee = oilFee;
  }
  
  public String getOilFee() {
    return this.oilFee;
  }
  
  public void setFixFee(String fixFee) {
    this.fixFee = fixFee;
  }
  
  public String getFixFee() {
    return this.fixFee;
  }
  
  public void setWashFee(String washFee) {
    this.washFee = washFee;
  }
  
  public String getWashFee() {
    return this.washFee;
  }
  
  public void setTaxFee(String taxFee) {
    this.taxFee = taxFee;
  }
  
  public String getTaxFee() {
    return this.taxFee;
  }
  
  public void setInsureFee(String insureFee) {
    this.insureFee = insureFee;
  }
  
  public String getInsureFee() {
    return this.insureFee;
  }
  
  public String getRoadFee() {
    return this.roadFee;
  }
  
  public void setRoadFee(String roadFee) {
    this.roadFee = roadFee;
  }
  
  public String getYearTicketFee() {
    return this.yearTicketFee;
  }
  
  public void setYearTicketFee(String yearTicketFee) {
    this.yearTicketFee = yearTicketFee;
  }
  
  public String getYearSensorFee() {
    return this.yearSensorFee;
  }
  
  public void setYearSensorFee(String yearSensorFee) {
    this.yearSensorFee = yearSensorFee;
  }
  
  public String getPurchaseTax() {
    return this.purchaseTax;
  }
  
  public void setPurchaseTax(String purchaseTax) {
    this.purchaseTax = purchaseTax;
  }
  
  public String getBrandFee() {
    return this.brandFee;
  }
  
  public void setBrandFee(String brandFee) {
    this.brandFee = brandFee;
  }
  
  public String getAccidentFee() {
    return this.accidentFee;
  }
  
  public void setAccidentFee(String accidentFee) {
    this.accidentFee = accidentFee;
  }
  
  public String getOtherFee() {
    return this.otherFee;
  }
  
  public void setOtherFee(String otherFee) {
    this.otherFee = otherFee;
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getIsMaintain() {
    return this.isMaintain;
  }
  
  public void setIsMaintain(String isMaintain) {
    this.isMaintain = isMaintain;
  }
  
  public String getMonthFee() {
    return this.monthFee;
  }
  
  public void setMonthFee(String monthFee) {
    this.monthFee = monthFee;
  }
}
