package com.js.oa.message.action;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class MessageForm extends ActionForm {
  private String saveType;
  
  private Date receiveTime = null;
  
  private String msSign;
  
  private Long messageId;
  
  private String modelSend;
  
  private Long modelId;
  
  private String receiveMan;
  
  private Date sendTime = null;
  
  private String sendMan;
  
  private String msContent;
  
  private String receiveCode;
  
  private Long sendId;
  
  private Long receiveId;
  
  private Long limitCount;
  
  private String sendLimitId;
  
  private String sendLimitMan;
  
  private String sendOutMan;
  
  private Long sendOutId;
  
  private Long sumCount;
  
  private Long sendCountId;
  
  private String sendCountMan;
  
  private Long modelCountId;
  
  private String modelName;
  
  private Date countDate = null;
  
  private String msgtoid;
  
  private String msgto;
  
  private String msgtoidprivate;
  
  private String msgtoidpublic;
  
  private String msgtopublic;
  
  private String msgpublicphone;
  
  private String msgtoprivate;
  
  private String msgprivatephone;
  
  private String msgtophone;
  
  public String getSaveType() {
    return this.saveType;
  }
  
  public void setSaveType(String saveType) {
    this.saveType = saveType;
  }
  
  public void setReceiveId(Long receiveId) {
    this.receiveId = receiveId;
  }
  
  public void setSendId(Long sendId) {
    this.sendId = sendId;
  }
  
  public void setReceiveMan(String receiveMan) {
    this.receiveMan = receiveMan;
  }
  
  public void setSendMan(String sendMan) {
    this.sendMan = sendMan;
  }
  
  public void setMsContent(String msContent) {
    this.msContent = msContent;
  }
  
  public void setSendTime(Date sendTime) {
    this.sendTime = sendTime;
  }
  
  public void setMessageId(Long messageId) {
    this.messageId = messageId;
  }
  
  public void setModelId(Long modelId) {
    this.modelId = modelId;
  }
  
  public void setModelSend(String modelSend) {
    this.modelSend = modelSend;
  }
  
  public void setReceiveTime(Date receiveTime) {
    this.receiveTime = receiveTime;
  }
  
  public void setMsSign(String msSign) {
    this.msSign = msSign;
  }
  
  public Long getReceiveId() {
    return this.receiveId;
  }
  
  public Long getSendId() {
    return this.sendId;
  }
  
  public String getReceiveMan() {
    return this.receiveMan;
  }
  
  public String getSendMan() {
    return this.sendMan;
  }
  
  public String getMsContent() {
    return this.msContent;
  }
  
  public Date getSendTime() {
    return this.sendTime;
  }
  
  public Long getMessageId() {
    return this.messageId;
  }
  
  public Long getModelId() {
    return this.modelId;
  }
  
  public String getModelSend() {
    return this.modelSend;
  }
  
  public Date getReceiveTime() {
    return this.receiveTime;
  }
  
  public String getMsSign() {
    return this.msSign;
  }
  
  public void setLimitCount(Long limitCount) {
    this.limitCount = limitCount;
  }
  
  public Long getLimitCount() {
    return this.limitCount;
  }
  
  public void setSumCount(Long sumCount) {
    this.sumCount = sumCount;
  }
  
  public void setCountDate(Date countDate) {
    this.countDate = countDate;
  }
  
  public void setMsgto(String msgto) {
    this.msgto = msgto;
  }
  
  public void setMsgtoid(String msgtoid) {
    this.msgtoid = msgtoid;
  }
  
  public void setMsgtoprivate(String msgtoprivate) {
    this.msgtoprivate = msgtoprivate;
  }
  
  public void setMsgtoidprivate(String msgtoidprivate) {
    this.msgtoidprivate = msgtoidprivate;
  }
  
  public void setMsgtopublic(String msgtopublic) {
    this.msgtopublic = msgtopublic;
  }
  
  public void setMsgtoidpublic(String msgtoidpublic) {
    this.msgtoidpublic = msgtoidpublic;
  }
  
  public Long getSumCount() {
    return this.sumCount;
  }
  
  public Date getCountDate() {
    return this.countDate;
  }
  
  public String getMsgto() {
    return this.msgto;
  }
  
  public String getMsgtoid() {
    return this.msgtoid;
  }
  
  public String getMsgtoprivate() {
    return this.msgtoprivate;
  }
  
  public String getMsgtoidprivate() {
    return this.msgtoidprivate;
  }
  
  public String getMsgtopublic() {
    return this.msgtopublic;
  }
  
  public String getMsgtoidpublic() {
    return this.msgtoidpublic;
  }
  
  public void setModelCountId(Long modelCountId) {
    this.modelCountId = modelCountId;
  }
  
  public void setSendCountId(Long sendCountId) {
    this.sendCountId = sendCountId;
  }
  
  public Long getModelCountId() {
    return this.modelCountId;
  }
  
  public Long getSendCountId() {
    return this.sendCountId;
  }
  
  public void setSendLimitMan(String sendLimitMan) {
    this.sendLimitMan = sendLimitMan;
  }
  
  public String getSendLimitMan() {
    return this.sendLimitMan;
  }
  
  public void setSendOutId(Long sendOutId) {
    this.sendOutId = sendOutId;
  }
  
  public void setSendOutMan(String sendOutMan) {
    this.sendOutMan = sendOutMan;
  }
  
  public void setMsgtophone(String msgtophone) {
    this.msgtophone = msgtophone;
  }
  
  public void setMsgprivatephone(String msgprivatephone) {
    this.msgprivatephone = msgprivatephone;
  }
  
  public void setMsgpublicphone(String msgpublicphone) {
    this.msgpublicphone = msgpublicphone;
  }
  
  public void setReceiveCode(String receiveCode) {
    this.receiveCode = receiveCode;
  }
  
  public void setSendCountMan(String sendCountMan) {
    this.sendCountMan = sendCountMan;
  }
  
  public void setModelName(String modelName) {
    this.modelName = modelName;
  }
  
  public void setSendLimitId(String sendLimitId) {
    this.sendLimitId = sendLimitId;
  }
  
  public Long getSendOutId() {
    return this.sendOutId;
  }
  
  public String getSendOutMan() {
    return this.sendOutMan;
  }
  
  public String getMsgtophone() {
    return this.msgtophone;
  }
  
  public String getMsgprivatephone() {
    return this.msgprivatephone;
  }
  
  public String getMsgpublicphone() {
    return this.msgpublicphone;
  }
  
  public String getReceiveCode() {
    return this.receiveCode;
  }
  
  public String getSendCountMan() {
    return this.sendCountMan;
  }
  
  public String getModelName() {
    return this.modelName;
  }
  
  public String getSendLimitId() {
    return this.sendLimitId;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.receiveMan = null;
    this.sendTime = null;
    this.sendMan = null;
    this.msContent = null;
    this.receiveCode = null;
    this.sendId = null;
    this.receiveId = null;
    this.modelId = null;
    this.modelSend = null;
    this.receiveTime = null;
    this.msSign = null;
    this.limitCount = null;
    this.countDate = null;
    this.sumCount = null;
    this.modelCountId = null;
    this.sendCountId = null;
    this.sendLimitId = null;
    this.sendLimitMan = null;
    this.sendOutId = null;
    this.sendOutMan = null;
    this.sendCountMan = null;
    this.modelName = null;
    this.msgtoid = null;
    this.msgto = null;
    this.msgtoidprivate = null;
    this.msgtoidpublic = null;
    this.msgtopublic = null;
    this.msgtoprivate = null;
    this.msgprivatephone = null;
    this.msgtophone = null;
    this.msgpublicphone = null;
  }
}
