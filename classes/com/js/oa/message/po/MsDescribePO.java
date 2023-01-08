package com.js.oa.message.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MsDescribePO implements Serializable {
  private Long describeId;
  
  private String receiveMan;
  
  private Date sendTime = null;
  
  private String sendMan;
  
  private String msContent;
  
  private String outMobilephone;
  
  private Long sendId;
  
  private String receiveCode;
  
  private Long receiveId;
  
  public void setDescribeId(Long describeId) {
    this.describeId = describeId;
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
  
  public void setOutMobilephone(String outMobilephone) {
    this.outMobilephone = outMobilephone;
  }
  
  public void setReceiveCode(String receiveCode) {
    this.receiveCode = receiveCode;
  }
  
  public Long getDescribeId() {
    return this.describeId;
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
  
  public String getOutMobilephone() {
    return this.outMobilephone;
  }
  
  public String getReceiveCode() {
    return this.receiveCode;
  }
  
  public MsDescribePO(String receiveMan, Date sendTime, String sendMan, String msContent, String receiveCode, Long sendId, Long receiveId, String outMobilephone) {
    this.receiveMan = receiveMan;
    this.sendTime = sendTime;
    this.sendMan = sendMan;
    this.msContent = msContent;
    this.receiveCode = receiveCode;
    this.sendId = sendId;
    this.receiveId = receiveId;
    this.outMobilephone = outMobilephone;
  }
  
  public MsDescribePO() {}
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("describeId", getDescribeId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof MsDescribePO))
      return false; 
    MsDescribePO castOther = (MsDescribePO)other;
    return (new EqualsBuilder())
      .append(getDescribeId(), castOther.getDescribeId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getDescribeId())
      .toHashCode();
  }
}
