package com.js.oa.info.infomanager.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class InformationCommentPO implements Serializable {
  private String commentContent;
  
  private String commentIssuerName;
  
  private Long commentIssuerId;
  
  private Date commentIssueTime = null;
  
  private InformationPO information;
  
  private Long commentId;
  
  private String commentIssuerOrg;
  
  private Long domainId;
  
  private Long commentParentId;
  
  private String layer;
  
  public String getCommentContent() {
    return this.commentContent;
  }
  
  public void setCommentContent(String commentContent) {
    this.commentContent = commentContent;
  }
  
  public String getCommentIssuerName() {
    return this.commentIssuerName;
  }
  
  public void setCommentIssuerName(String commentIssuerName) {
    this.commentIssuerName = commentIssuerName;
  }
  
  public Long getCommentIssuerId() {
    return this.commentIssuerId;
  }
  
  public void setCommentIssuerId(Long commentIssuerId) {
    this.commentIssuerId = commentIssuerId;
  }
  
  public Date getCommentIssueTime() {
    return this.commentIssueTime;
  }
  
  public void setCommentIssueTime(Date commentIssueTime) {
    this.commentIssueTime = commentIssueTime;
  }
  
  public InformationPO getInformation() {
    return this.information;
  }
  
  public void setInformation(InformationPO information) {
    this.information = information;
  }
  
  public Long getCommentId() {
    return this.commentId;
  }
  
  public void setCommentId(Long commentId) {
    this.commentId = commentId;
  }
  
  public String getCommentIssuerOrg() {
    return this.commentIssuerOrg;
  }
  
  public void setCommentIssuerOrg(String commentIssuerOrg) {
    this.commentIssuerOrg = commentIssuerOrg;
  }
  
  public String getLayer() {
    return this.layer;
  }
  
  public void setLayer(String layer) {
    this.layer = layer;
  }
  
  public Long getCommentParentId() {
    return this.commentParentId;
  }
  
  public void setCommentParentId(Long commentParentId) {
    this.commentParentId = commentParentId;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof InformationPO))
      return false; 
    InformationCommentPO castOther = (InformationCommentPO)other;
    return (new EqualsBuilder()).append(getCommentId(), castOther.getCommentId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getCommentId()).toHashCode();
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
}
