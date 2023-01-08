package com.js.oa.routine.voiture.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class VoitureSendActionForm extends ActionForm {
  private Long id;
  
  private String sendNumber;
  
  private Long applyId;
  
  private Long voitureId;
  
  private String sendStartHour;
  
  private String sendEndHour;
  
  private String sendStartKilo = "0";
  
  private String sendEndKilo = "0";
  
  private Double keepingFee;
  
  private Double travelFee;
  
  private Long sendHolidayCount;
  
  private Long sendCount = new Long(1L);
  
  private Double misMealFee = new Double(0.0D);
  
  private String overTimeHoliday = "0.00";
  
  private String overTimeWeekend = "0.00";
  
  private String overTime = "0.00";
  
  private String sendStartMinute;
  
  private String sendEndMinute;
  
  private String sendEndDate;
  
  private String sendStartDate;
  
  private Double kiloPrice;
  
  private String applyorgId;
  
  private String applydestination;
  
  private String applyreason;
  
  private String applyorgName;
  
  private String applyempName;
  
  private String applyempId;
  
  private String applyendHour;
  
  private String applystatus;
  
  private String applystartHour;
  
  private String applystartDate;
  
  private String applyendDate;
  
  private String applyfillDate;
  
  private String applystartMinute;
  
  private String applyendMinute;
  
  private String applyvoitureName;
  
  private String applypersonNum;
  
  private String applymotorMan;
  
  private String applyvoitureStyle = "1";
  
  private String applyremark;
  
  private String applyycr;
  
  private Double otherAllowance = new Double(0.0D);
  
  private String satisfaction;
  
  private String satiRemark;
  
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
  
  public Long getApplyId() {
    return this.applyId;
  }
  
  public void setApplyId(Long applyId) {
    this.applyId = applyId;
  }
  
  public Long getVoitureId() {
    return this.voitureId;
  }
  
  public void setVoitureId(Long voitureId) {
    this.voitureId = voitureId;
  }
  
  public String getSendStartDate() {
    return this.sendStartDate;
  }
  
  public void setSendStartDate(String sendStartDate) {
    this.sendStartDate = sendStartDate;
  }
  
  public String getSendStartHour() {
    return this.sendStartHour;
  }
  
  public void setSendStartHour(String sendStartHour) {
    this.sendStartHour = sendStartHour;
  }
  
  public String getSendEndDate() {
    return this.sendEndDate;
  }
  
  public void setSendEndDate(String sendEndDate) {
    this.sendEndDate = sendEndDate;
  }
  
  public String getSendEndHour() {
    return this.sendEndHour;
  }
  
  public void setSendEndHour(String sendEndHour) {
    this.sendEndHour = sendEndHour;
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
  
  public String getOverTimeHoliday() {
    return this.overTimeHoliday;
  }
  
  public void setOverTimeHoliday(String overTimeHoliday) {
    this.overTimeHoliday = overTimeHoliday;
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
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getSendStartMinute() {
    return this.sendStartMinute;
  }
  
  public void setSendStartMinute(String sendStartMinute) {
    this.sendStartMinute = sendStartMinute;
  }
  
  public String getSendEndMinute() {
    return this.sendEndMinute;
  }
  
  public void setSendEndMinute(String sendEndMinute) {
    this.sendEndMinute = sendEndMinute;
  }
  
  public Double getKiloPrice() {
    return this.kiloPrice;
  }
  
  public void setKiloPrice(Double kiloPrice) {
    this.kiloPrice = kiloPrice;
  }
  
  public String getApplydestination() {
    return this.applydestination;
  }
  
  public void setApplydestination(String applydestination) {
    this.applydestination = applydestination;
  }
  
  public String getApplyempId() {
    return this.applyempId;
  }
  
  public void setApplyempId(String applyempId) {
    this.applyempId = applyempId;
  }
  
  public String getApplyempName() {
    return this.applyempName;
  }
  
  public void setApplyempName(String applyempName) {
    this.applyempName = applyempName;
  }
  
  public String getApplyendDate() {
    return this.applyendDate;
  }
  
  public void setApplyendDate(String applyendDate) {
    this.applyendDate = applyendDate;
  }
  
  public String getApplyendHour() {
    return this.applyendHour;
  }
  
  public void setApplyendHour(String applyendHour) {
    this.applyendHour = applyendHour;
  }
  
  public String getApplyendMinute() {
    return this.applyendMinute;
  }
  
  public void setApplyendMinute(String applyendMinute) {
    this.applyendMinute = applyendMinute;
  }
  
  public String getApplyfillDate() {
    return this.applyfillDate;
  }
  
  public void setApplyfillDate(String applyfillDate) {
    this.applyfillDate = applyfillDate;
  }
  
  public String getApplymotorMan() {
    return this.applymotorMan;
  }
  
  public void setApplymotorMan(String applymotorMan) {
    this.applymotorMan = applymotorMan;
  }
  
  public String getApplyorgId() {
    return this.applyorgId;
  }
  
  public void setApplyorgId(String applyorgId) {
    this.applyorgId = applyorgId;
  }
  
  public String getApplyorgName() {
    return this.applyorgName;
  }
  
  public void setApplyorgName(String applyorgName) {
    this.applyorgName = applyorgName;
  }
  
  public String getApplypersonNum() {
    return this.applypersonNum;
  }
  
  public void setApplypersonNum(String applypersonNum) {
    this.applypersonNum = applypersonNum;
  }
  
  public String getApplyreason() {
    return this.applyreason;
  }
  
  public void setApplyreason(String applyreason) {
    this.applyreason = applyreason;
  }
  
  public String getApplyremark() {
    return this.applyremark;
  }
  
  public void setApplyremark(String applyremark) {
    this.applyremark = applyremark;
  }
  
  public String getApplystartDate() {
    return this.applystartDate;
  }
  
  public void setApplystartDate(String applystartDate) {
    this.applystartDate = applystartDate;
  }
  
  public String getApplystartHour() {
    return this.applystartHour;
  }
  
  public void setApplystartHour(String applystartHour) {
    this.applystartHour = applystartHour;
  }
  
  public String getApplystartMinute() {
    return this.applystartMinute;
  }
  
  public void setApplystartMinute(String applystartMinute) {
    this.applystartMinute = applystartMinute;
  }
  
  public String getApplystatus() {
    return this.applystatus;
  }
  
  public void setApplystatus(String applystatus) {
    this.applystatus = applystatus;
  }
  
  public String getApplyvoitureName() {
    return this.applyvoitureName;
  }
  
  public void setApplyvoitureName(String applyvoitureName) {
    this.applyvoitureName = applyvoitureName;
  }
  
  public String getApplyvoitureStyle() {
    return this.applyvoitureStyle;
  }
  
  public void setApplyvoitureStyle(String applyvoitureStyle) {
    this.applyvoitureStyle = applyvoitureStyle;
  }
  
  public Double getOtherAllowance() {
    return this.otherAllowance;
  }
  
  public String getSatiRemark() {
    return this.satiRemark;
  }
  
  public String getSatisfaction() {
    return this.satisfaction;
  }
  
  public void setOtherAllowance(Double otherAllowance) {
    this.otherAllowance = otherAllowance;
  }
  
  public void setSatiRemark(String satiRemark) {
    this.satiRemark = satiRemark;
  }
  
  public void setSatisfaction(String satisfaction) {
    this.satisfaction = satisfaction;
  }
  
  public String getApplyycr() {
    return this.applyycr;
  }
  
  public void setApplyycr(String applyycr) {
    this.applyycr = applyycr;
  }
}
