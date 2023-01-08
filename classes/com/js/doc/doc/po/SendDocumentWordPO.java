package com.js.doc.doc.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class SendDocumentWordPO implements Serializable {
  private Long id;
  
  private Long processId;
  
  private Long sendDocumentNumId;
  
  private String templateId;
  
  private String userRange;
  
  private String userRangeId;
  
  private String wordName;
  
  private Long sendDocumentSeqId;
  
  private String templateName;
  
  private String processName;
  
  private String receiveUser;
  
  private String receiveOrg;
  
  private String receiveGroup;
  
  private String receiveScopeName;
  
  private String receiveScopeId;
  
  private String redHeadName;
  
  private String redHeadSaveName;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private Long corpId;
  
  public Long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(Long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public Long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(Long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public Long getCorpId() {
    return this.corpId;
  }
  
  public void setCorpId(Long corpId) {
    this.corpId = corpId;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof SendDocumentNumPO))
      return false; 
    SendDocumentWordPO castOther = (SendDocumentWordPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public Long getId() {
    return this.id;
  }
  
  public Long getProcessId() {
    return this.processId;
  }
  
  public String getTemplateId() {
    return this.templateId;
  }
  
  public String getUserRange() {
    return this.userRange;
  }
  
  public String getUserRangeId() {
    return this.userRangeId;
  }
  
  public String getWordName() {
    return this.wordName;
  }
  
  public Long getSendDocumentNumId() {
    return this.sendDocumentNumId;
  }
  
  public Long getSendDocumentSeqId() {
    return this.sendDocumentSeqId;
  }
  
  public String getTemplateName() {
    return this.templateName;
  }
  
  public String getProcessName() {
    return this.processName;
  }
  
  public String getReceiveUser() {
    return this.receiveUser;
  }
  
  public String getReceiveScopeName() {
    return this.receiveScopeName;
  }
  
  public String getReceiveScopeId() {
    return this.receiveScopeId;
  }
  
  public String getReceiveOrg() {
    return this.receiveOrg;
  }
  
  public String getReceiveGroup() {
    return this.receiveGroup;
  }
  
  public String getRedHeadSaveName() {
    return this.redHeadSaveName;
  }
  
  public String getRedHeadName() {
    return this.redHeadName;
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setProcessId(Long processId) {
    this.processId = processId;
  }
  
  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }
  
  public void setUserRange(String userRange) {
    this.userRange = userRange;
  }
  
  public void setUserRangeId(String userRangeId) {
    this.userRangeId = userRangeId;
  }
  
  public void setWordName(String wordName) {
    this.wordName = wordName;
  }
  
  public void setSendDocumentNumId(Long sendDocumentNumId) {
    this.sendDocumentNumId = sendDocumentNumId;
  }
  
  public void setSendDocumentSeqId(Long sendDocumentSeqId) {
    this.sendDocumentSeqId = sendDocumentSeqId;
  }
  
  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }
  
  public void setProcessName(String processName) {
    this.processName = processName;
  }
  
  public void setReceiveUser(String receiveUser) {
    this.receiveUser = receiveUser;
  }
  
  public void setReceiveScopeName(String receiveScopeName) {
    this.receiveScopeName = receiveScopeName;
  }
  
  public void setReceiveScopeId(String receiveScopeId) {
    this.receiveScopeId = receiveScopeId;
  }
  
  public void setReceiveOrg(String receiveOrg) {
    this.receiveOrg = receiveOrg;
  }
  
  public void setReceiveGroup(String receiveGroup) {
    this.receiveGroup = receiveGroup;
  }
  
  public void setRedHeadSaveName(String redHeadSaveName) {
    this.redHeadSaveName = redHeadSaveName;
  }
  
  public void setRedHeadName(String redHeadName) {
    this.redHeadName = redHeadName;
  }
}
