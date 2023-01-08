package com.js.doc.doc.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GovSendFileCheckWithWorkFlowPO implements Serializable {
  private long id;
  
  private String sendFileCheckComeUnit;
  
  private String sendFileCheckFileNumber;
  
  private Date sendFileCheckReceiveDate = null;
  
  private String sendFileCheckTitle;
  
  private String sendFileCheckWriterComment;
  
  private String sendFileCheckFinishDate;
  
  private long createdOrg;
  
  private long createdEmp;
  
  private String sendFileCheckLeaderComment;
  
  private byte canEdit;
  
  private String field1;
  
  private String field2;
  
  private String field3;
  
  private String field4;
  
  private String field5;
  
  private String field6;
  
  private String field7;
  
  private String field8;
  
  private String field9;
  
  private String field10;
  
  private Set accessory = null;
  
  private String submitPerson;
  
  private Date submitTime = null;
  
  private Long receiveFileId;
  
  private String receiveFileLink;
  
  private Integer thirdDossier;
  
  private String goldGridId;
  
  private String transactStatus;
  
  private String domainId;
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getSendFileCheckComeUnit() {
    return this.sendFileCheckComeUnit;
  }
  
  public void setSendFileCheckComeUnit(String sendFileCheckComeUnit) {
    this.sendFileCheckComeUnit = sendFileCheckComeUnit;
  }
  
  public String getSendFileCheckFileNumber() {
    return this.sendFileCheckFileNumber;
  }
  
  public void setSendFileCheckFileNumber(String sendFileCheckFileNumber) {
    this.sendFileCheckFileNumber = sendFileCheckFileNumber;
  }
  
  public Date getSendFileCheckReceiveDate() {
    return this.sendFileCheckReceiveDate;
  }
  
  public void setSendFileCheckReceiveDate(Date sendFileCheckReceiveDate) {
    this.sendFileCheckReceiveDate = sendFileCheckReceiveDate;
  }
  
  public String getSendFileCheckTitle() {
    return this.sendFileCheckTitle;
  }
  
  public void setSendFileCheckTitle(String sendFileCheckTitle) {
    this.sendFileCheckTitle = sendFileCheckTitle;
  }
  
  public String getSendFileCheckWriterComment() {
    return this.sendFileCheckWriterComment;
  }
  
  public void setSendFileCheckWriterComment(String sendFileCheckWriterComment) {
    this.sendFileCheckWriterComment = sendFileCheckWriterComment;
  }
  
  public String getSendFileCheckFinishDate() {
    return this.sendFileCheckFinishDate;
  }
  
  public void setSendFileCheckFinishDate(String sendFileCheckFinishDate) {
    this.sendFileCheckFinishDate = sendFileCheckFinishDate;
  }
  
  public long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof GovSendFileCheckWithWorkFlowPO))
      return false; 
    GovSendFileCheckWithWorkFlowPO castOther = (GovSendFileCheckWithWorkFlowPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public String getSendFileCheckLeaderComment() {
    return this.sendFileCheckLeaderComment;
  }
  
  public void setSendFileCheckLeaderComment(String sendFileCheckLeaderComment) {
    this.sendFileCheckLeaderComment = sendFileCheckLeaderComment;
  }
  
  public byte getCanEdit() {
    return this.canEdit;
  }
  
  public void setCanEdit(byte canEdit) {
    this.canEdit = canEdit;
  }
  
  public String getField1() {
    return this.field1;
  }
  
  public void setField1(String field1) {
    this.field1 = field1;
  }
  
  public String getField2() {
    return this.field2;
  }
  
  public void setField2(String field2) {
    this.field2 = field2;
  }
  
  public String getField3() {
    return this.field3;
  }
  
  public void setField3(String field3) {
    this.field3 = field3;
  }
  
  public String getField4() {
    return this.field4;
  }
  
  public void setField4(String field4) {
    this.field4 = field4;
  }
  
  public String getField5() {
    return this.field5;
  }
  
  public void setField5(String field5) {
    this.field5 = field5;
  }
  
  public String getField6() {
    return this.field6;
  }
  
  public void setField6(String field6) {
    this.field6 = field6;
  }
  
  public String getField7() {
    return this.field7;
  }
  
  public void setField7(String field7) {
    this.field7 = field7;
  }
  
  public String getField8() {
    return this.field8;
  }
  
  public void setField8(String field8) {
    this.field8 = field8;
  }
  
  public String getField9() {
    return this.field9;
  }
  
  public void setField9(String field9) {
    this.field9 = field9;
  }
  
  public String getField10() {
    return this.field10;
  }
  
  public void setField10(String field10) {
    this.field10 = field10;
  }
  
  public Set getAccessory() {
    return this.accessory;
  }
  
  public void setAccessory(Set accessory) {
    this.accessory = accessory;
  }
  
  public String getSubmitPerson() {
    return this.submitPerson;
  }
  
  public void setSubmitPerson(String submitPerson) {
    this.submitPerson = submitPerson;
  }
  
  public Date getSubmitTime() {
    return this.submitTime;
  }
  
  public void setSubmitTime(Date submitTime) {
    this.submitTime = submitTime;
  }
  
  public Long getReceiveFileId() {
    return this.receiveFileId;
  }
  
  public void setReceiveFileId(Long receiveFileId) {
    this.receiveFileId = receiveFileId;
  }
  
  public String getReceiveFileLink() {
    return this.receiveFileLink;
  }
  
  public void setReceiveFileLink(String receiveFileLink) {
    this.receiveFileLink = receiveFileLink;
  }
  
  public Integer getThirdDossier() {
    return this.thirdDossier;
  }
  
  public void setThirdDossier(Integer thirdDossier) {
    this.thirdDossier = thirdDossier;
  }
  
  public String getGoldGridId() {
    return this.goldGridId;
  }
  
  public void setGoldGridId(String goldGridId) {
    this.goldGridId = goldGridId;
  }
  
  public String getTransactStatus() {
    return this.transactStatus;
  }
  
  public void setTransactStatus(String transactStatus) {
    this.transactStatus = transactStatus;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
