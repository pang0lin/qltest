package com.js.oa.routine.voiture.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

public class VoitureFeePO implements Serializable {
  private Long id;
  
  private Date maintainTime = null;
  
  private Double oilCost;
  
  private Double oilFee;
  
  private Double fixFee;
  
  private Double washFee;
  
  private Double taxFee;
  
  private Double insureFee;
  
  private Double roadFee;
  
  private Double yearTicketFee;
  
  private Double yearSensorFee;
  
  private Double purchaseTax;
  
  private Double brandFee;
  
  private Double accidentFee;
  
  private Double otherFee;
  
  private String remark;
  
  private VoiturePO voiturePO;
  
  private String isMaintain;
  
  private Double monthFee;
  
  private Long domainId;
  
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
  
  public VoitureFeePO(Date maintainTime, Double oilCost, Double oilFee, Double fixFee, Double washFee, Double taxFee, Double insureFee, Double roadFee, Double yearTicketFee, Double yearSensorFee, Double purchaseTax, Double brandFee, Double accidentFee, Double otherFee, String remark) {
    this.maintainTime = maintainTime;
    this.oilCost = oilCost;
    this.oilFee = oilFee;
    this.fixFee = fixFee;
    this.washFee = washFee;
    this.taxFee = taxFee;
    this.insureFee = insureFee;
    this.roadFee = roadFee;
    this.yearTicketFee = yearTicketFee;
    this.yearSensorFee = yearSensorFee;
    this.purchaseTax = purchaseTax;
    this.brandFee = brandFee;
    this.accidentFee = accidentFee;
    this.otherFee = otherFee;
    this.remark = remark;
  }
  
  public VoitureFeePO() {}
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Date getMaintainTime() {
    return this.maintainTime;
  }
  
  public void setMaintainTime(Date maintainTime) {
    this.maintainTime = maintainTime;
  }
  
  public Double getOilCost() {
    return this.oilCost;
  }
  
  public void setOilCost(Double oilCost) {
    this.oilCost = oilCost;
  }
  
  public Double getOilFee() {
    return this.oilFee;
  }
  
  public void setOilFee(Double oilFee) {
    this.oilFee = oilFee;
  }
  
  public Double getFixFee() {
    return this.fixFee;
  }
  
  public void setFixFee(Double fixFee) {
    this.fixFee = fixFee;
  }
  
  public Double getWashFee() {
    return this.washFee;
  }
  
  public void setWashFee(Double washFee) {
    this.washFee = washFee;
  }
  
  public Double getTaxFee() {
    return this.taxFee;
  }
  
  public void setTaxFee(Double taxFee) {
    this.taxFee = taxFee;
  }
  
  public Double getInsureFee() {
    return this.insureFee;
  }
  
  public void setInsureFee(Double insureFee) {
    this.insureFee = insureFee;
  }
  
  public Double getRoadFee() {
    return this.roadFee;
  }
  
  public void setRoadFee(Double roadFee) {
    this.roadFee = roadFee;
  }
  
  public Double getYearTicketFee() {
    return this.yearTicketFee;
  }
  
  public void setYearTicketFee(Double yearTicketFee) {
    this.yearTicketFee = yearTicketFee;
  }
  
  public Double getYearSensorFee() {
    return this.yearSensorFee;
  }
  
  public void setYearSensorFee(Double yearSensorFee) {
    this.yearSensorFee = yearSensorFee;
  }
  
  public Double getPurchaseTax() {
    return this.purchaseTax;
  }
  
  public void setPurchaseTax(Double purchaseTax) {
    this.purchaseTax = purchaseTax;
  }
  
  public Double getBrandFee() {
    return this.brandFee;
  }
  
  public void setBrandFee(Double brandFee) {
    this.brandFee = brandFee;
  }
  
  public Double getAccidentFee() {
    return this.accidentFee;
  }
  
  public void setAccidentFee(Double accidentFee) {
    this.accidentFee = accidentFee;
  }
  
  public Double getOtherFee() {
    return this.otherFee;
  }
  
  public void setOtherFee(Double otherFee) {
    this.otherFee = otherFee;
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public VoiturePO getVoiturePO() {
    return this.voiturePO;
  }
  
  public void setVoiturePO(VoiturePO voiturePO) {
    this.voiturePO = voiturePO;
  }
  
  public String getIsMaintain() {
    return this.isMaintain;
  }
  
  public void setIsMaintain(String isMaintain) {
    this.isMaintain = isMaintain;
  }
  
  public Double getMonthFee() {
    return this.monthFee;
  }
  
  public void setMonthFee(Double monthFee) {
    this.monthFee = monthFee;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
}
