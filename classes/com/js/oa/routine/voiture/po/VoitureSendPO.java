package com.js.oa.routine.voiture.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

public class VoitureSendPO implements Serializable {
  private Long id;
  
  private String sendNumber;
  
  private Date sendStartDate = null;
  
  private Date sendEndDate = null;
  
  private String sendStartKilo;
  
  private String sendEndKilo;
  
  private Double keepingFee;
  
  private Double travelFee;
  
  private Long sendHolidayCount;
  
  private Long sendCount;
  
  private Double misMealFee;
  
  private String overTimeHoliDay;
  
  private String overTimeWeekend;
  
  private String overTime;
  
  private VoitureApplyPO voitureApplyPO;
  
  private VoiturePO voiturePO;
  
  private String sendStartTime;
  
  private String sendEndTime;
  
  private Long sendStartTimeTotal;
  
  private Long sendEndTimeTotal;
  
  private Double kiloPrice;
  
  private Double otherAllowance;
  
  private Long domainId;
  
  public VoitureSendPO(String sendNumber, Long applyId, Long voitureId, Date sendStartDate, String sendStartTime, Date sendEndDate, String sendEndTime, String sendStartKilo, String sendEndKilo, Double keepingFee, Double travelFee, Double kiloPrice, Long sendHolidayCount, Long sendCount, Double misMealFee, String overTimeHoliDay, String overTimeWeekend, String overTime, Double otherAllowance) {
    this.sendNumber = sendNumber;
    this.sendStartDate = sendStartDate;
    this.sendEndDate = sendEndDate;
    this.sendStartKilo = sendStartKilo;
    this.sendEndKilo = sendEndKilo;
    this.keepingFee = keepingFee;
    this.travelFee = travelFee;
    this.sendHolidayCount = sendHolidayCount;
    this.sendCount = sendCount;
    this.misMealFee = misMealFee;
    this.overTimeHoliDay = overTimeHoliDay;
    this.overTimeWeekend = overTimeWeekend;
    this.overTime = overTime;
    this.otherAllowance = otherAllowance;
  }
  
  public VoitureSendPO() {}
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getSendNumber() {
    return this.sendNumber;
  }
  
  public void setSendNumber(String sendNumber) {
    this.sendNumber = sendNumber;
  }
  
  public Date getSendStartDate() {
    return this.sendStartDate;
  }
  
  public void setSendStartDate(Date sendStartDate) {
    this.sendStartDate = sendStartDate;
  }
  
  public Date getSendEndDate() {
    return this.sendEndDate;
  }
  
  public void setSendEndDate(Date sendEndDate) {
    this.sendEndDate = sendEndDate;
  }
  
  public String getSendStartKilo() {
    return this.sendStartKilo;
  }
  
  public void setSendStartKilo(String sendStartKilo) {
    this.sendStartKilo = sendStartKilo;
  }
  
  public String getSendEndKilo() {
    return this.sendEndKilo;
  }
  
  public void setSendEndKilo(String sendEndKilo) {
    this.sendEndKilo = sendEndKilo;
  }
  
  public Double getKeepingFee() {
    return this.keepingFee;
  }
  
  public void setKeepingFee(Double keepingFee) {
    this.keepingFee = keepingFee;
  }
  
  public Double getTravelFee() {
    return this.travelFee;
  }
  
  public void setTravelFee(Double travelFee) {
    this.travelFee = travelFee;
  }
  
  public Long getSendHolidayCount() {
    return this.sendHolidayCount;
  }
  
  public void setSendHolidayCount(Long sendHolidayCount) {
    this.sendHolidayCount = sendHolidayCount;
  }
  
  public Long getSendCount() {
    return this.sendCount;
  }
  
  public void setSendCount(Long sendCount) {
    this.sendCount = sendCount;
  }
  
  public Double getMisMealFee() {
    return this.misMealFee;
  }
  
  public void setMisMealFee(Double misMealFee) {
    this.misMealFee = misMealFee;
  }
  
  public String getOverTimeHoliDay() {
    return this.overTimeHoliDay;
  }
  
  public void setOverTimeHoliDay(String overTimeHoliDay) {
    this.overTimeHoliDay = overTimeHoliDay;
  }
  
  public String getOverTimeWeekend() {
    return this.overTimeWeekend;
  }
  
  public void setOverTimeWeekend(String overTimeWeekend) {
    this.overTimeWeekend = overTimeWeekend;
  }
  
  public String getOverTime() {
    return this.overTime;
  }
  
  public void setOverTime(String overTime) {
    this.overTime = overTime;
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
  
  public VoitureApplyPO getVoitureApplyPO() {
    return this.voitureApplyPO;
  }
  
  public void setVoitureApplyPO(VoitureApplyPO voitureApplyPO) {
    this.voitureApplyPO = voitureApplyPO;
  }
  
  public String getSendStartTime() {
    return this.sendStartTime;
  }
  
  public void setSendStartTime(String sendStartTime) {
    this.sendStartTime = sendStartTime;
  }
  
  public String getSendEndTime() {
    return this.sendEndTime;
  }
  
  public void setSendEndTime(String sendEndTime) {
    this.sendEndTime = sendEndTime;
  }
  
  public Long getSendStartTimeTotal() {
    return this.sendStartTimeTotal;
  }
  
  public void setSendStartTimeTotal(Long sendStartTimeTotal) {
    this.sendStartTimeTotal = sendStartTimeTotal;
  }
  
  public Long getSendEndTimeTotal() {
    return this.sendEndTimeTotal;
  }
  
  public void setSendEndTimeTotal(Long sendEndTimeTotal) {
    this.sendEndTimeTotal = sendEndTimeTotal;
  }
  
  public Double getKiloPrice() {
    return this.kiloPrice;
  }
  
  public void setKiloPrice(Double kiloPrice) {
    this.kiloPrice = kiloPrice;
  }
  
  public Double getOtherAllowance() {
    return this.otherAllowance;
  }
  
  public void setOtherAllowance(Double otherAllowance) {
    this.otherAllowance = otherAllowance;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
}
