package com.js.oa.message.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MsInfoListPO implements Serializable {
  private Long listId;
  
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
  
  private Integer isViewed;
  
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
  
  public void setListId(Long listId) {
    this.listId = listId;
  }
  
  public void setReceiveTime(Date receiveTime) {
    this.receiveTime = receiveTime;
  }
  
  public void setMsSign(String msSign) {
    this.msSign = msSign;
  }
  
  public void setReceiveCode(String receiveCode) {
    this.receiveCode = receiveCode;
  }
  
  public void setIsViewed(Integer isViewed) {
    this.isViewed = isViewed;
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
  
  public Long getListId() {
    return this.listId;
  }
  
  public Date getReceiveTime() {
    return this.receiveTime;
  }
  
  public String getMsSign() {
    return this.msSign;
  }
  
  public String getReceiveCode() {
    return this.receiveCode;
  }
  
  public Integer getIsViewed() {
    return this.isViewed;
  }
  
  public MsInfoListPO() {}
  
  public MsInfoListPO(String receiveMan, Date sendTime, String sendMan, String msContent, String receiveCode, Long sendId, Long receiveId, String modelSend, Long modelId, Date receiveTime, String msSign) {
    this.receiveMan = receiveMan;
    this.sendTime = sendTime;
    this.sendMan = sendMan;
    this.msContent = msContent;
    this.receiveCode = receiveCode;
    this.sendId = sendId;
    this.receiveId = receiveId;
    this.modelId = modelId;
    this.modelSend = modelSend;
    this.receiveTime = receiveTime;
    this.msSign = msSign;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("listId", getListId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof MsInfoListPO))
      return false; 
    MsInfoListPO castOther = (MsInfoListPO)other;
    return (new EqualsBuilder())
      .append(getListId(), castOther.getListId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getListId())
      .toHashCode();
  }
}
