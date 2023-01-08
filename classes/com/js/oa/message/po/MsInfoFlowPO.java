package com.js.oa.message.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MsInfoFlowPO implements Serializable {
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
  
  private String domainId;
  
  private String serial;
  
  private String serialPwd;
  
  private Long dataId;
  
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
  
  public void setReceiveCode(String receiveCode) {
    this.receiveCode = receiveCode;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public void setSerial(String serial) {
    this.serial = serial;
  }
  
  public void setSerialPwd(String serialPwd) {
    this.serialPwd = serialPwd;
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
  
  public String getReceiveCode() {
    return this.receiveCode;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public String getSerial() {
    return this.serial;
  }
  
  public String getSerialPwd() {
    return this.serialPwd;
  }
  
  public MsInfoFlowPO() {}
  
  public MsInfoFlowPO(String receiveMan, Date sendTime, String sendMan, String msContent, String receiveCode, Long sendId, Long receiveId, String modelSend, Long modelId) {
    this.receiveMan = receiveMan;
    this.sendTime = sendTime;
    this.sendMan = sendMan;
    this.msContent = msContent;
    this.receiveCode = receiveCode;
    this.sendId = sendId;
    this.receiveId = receiveId;
    this.modelId = modelId;
    this.modelSend = modelSend;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("messageId", getMessageId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof MsInfoFlowPO))
      return false; 
    MsInfoFlowPO castOther = (MsInfoFlowPO)other;
    return (new EqualsBuilder())
      .append(getMessageId(), castOther.getMessageId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getMessageId())
      .toHashCode();
  }
  
  public Long getDataId() {
    return this.dataId;
  }
  
  public void setDataId(Long dataId) {
    this.dataId = dataId;
  }
}
