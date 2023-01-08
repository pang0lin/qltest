package com.js.oa.message.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class MessageSettingActionForm extends ActionForm {
  private Long limitId;
  
  private String sendLimitId;
  
  private String sendLimitMan;
  
  private Long limitCount;
  
  private Long monthCount;
  
  private Long dayCount;
  
  private Long outMoId;
  
  private String sendOutMan;
  
  private String sendOutId;
  
  private String modelSend;
  
  private String modelId;
  
  private String reday1;
  
  private String reday7;
  
  private String reday3;
  
  private String reday9;
  
  private String reday8;
  
  private String reday10;
  
  private String reday11;
  
  private String reday6;
  
  private String reday5;
  
  private String reday4;
  
  private String reday2;
  
  private String content;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest servletRequest) {}
  
  public void setLimitId(Long limitId) {
    this.limitId = limitId;
  }
  
  public void setLimitCount(Long limitCount) {
    this.limitCount = limitCount;
  }
  
  public void setSendLimitId(String sendLimitId) {
    this.sendLimitId = sendLimitId;
  }
  
  public void setSendLimitMan(String sendLimitMan) {
    this.sendLimitMan = sendLimitMan;
  }
  
  public Long getLimitId() {
    return this.limitId;
  }
  
  public Long getLimitCount() {
    return this.limitCount;
  }
  
  public String getSendLimitId() {
    return this.sendLimitId;
  }
  
  public String getSendLimitMan() {
    return this.sendLimitMan;
  }
  
  public void setOutMoId(Long outMoId) {
    this.outMoId = outMoId;
  }
  
  public void setSendOutId(String sendOutId) {
    this.sendOutId = sendOutId;
  }
  
  public void setSendOutMan(String sendOutMan) {
    this.sendOutMan = sendOutMan;
  }
  
  public Long getOutMoId() {
    return this.outMoId;
  }
  
  public String getSendOutId() {
    return this.sendOutId;
  }
  
  public String getSendOutMan() {
    return this.sendOutMan;
  }
  
  public void setModelId(String modelId) {
    this.modelId = modelId;
  }
  
  public void setModelSend(String modelSend) {
    this.modelSend = modelSend;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public void setReday1(String reday1) {
    this.reday1 = reday1;
  }
  
  public void setReday2(String reday2) {
    this.reday2 = reday2;
  }
  
  public void setReday3(String reday3) {
    this.reday3 = reday3;
  }
  
  public void setReday4(String reday4) {
    this.reday4 = reday4;
  }
  
  public void setReday5(String reday5) {
    this.reday5 = reday5;
  }
  
  public void setReday6(String reday6) {
    this.reday6 = reday6;
  }
  
  public void setReday7(String reday7) {
    this.reday7 = reday7;
  }
  
  public void setReday9(String reday9) {
    this.reday9 = reday9;
  }
  
  public void setReday8(String reday8) {
    this.reday8 = reday8;
  }
  
  public void setReday10(String reday10) {
    this.reday10 = reday10;
  }
  
  public void setReday11(String reday11) {
    this.reday11 = reday11;
  }
  
  public void setDayCount(Long dayCount) {
    this.dayCount = dayCount;
  }
  
  public void setMonthCount(Long monthCount) {
    this.monthCount = monthCount;
  }
  
  public String getModelId() {
    return this.modelId;
  }
  
  public String getModelSend() {
    return this.modelSend;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public String getReday1() {
    return this.reday1;
  }
  
  public String getReday2() {
    return this.reday2;
  }
  
  public String getReday3() {
    return this.reday3;
  }
  
  public String getReday4() {
    return this.reday4;
  }
  
  public String getReday5() {
    return this.reday5;
  }
  
  public String getReday6() {
    return this.reday6;
  }
  
  public String getReday7() {
    return this.reday7;
  }
  
  public String getReday9() {
    return this.reday9;
  }
  
  public String getReday8() {
    return this.reday8;
  }
  
  public String getReday10() {
    return this.reday10;
  }
  
  public String getReday11() {
    return this.reday11;
  }
  
  public Long getDayCount() {
    return this.dayCount;
  }
  
  public Long getMonthCount() {
    return this.monthCount;
  }
}
