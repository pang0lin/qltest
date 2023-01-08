package com.js.oa.scheme.worklog.vo;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class WorkLogCommentVO implements Serializable {
  private String commentContent;
  
  private String commentIssuerName;
  
  private Long commentIssuerId;
  
  private Date commentIssueTime = null;
  
  private WorkLogVO worklog;
  
  private Long commentId;
  
  private String commentIssuerOrg;
  
  private Long domainId;
  
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
  
  public WorkLogVO getWorklog() {
    return this.worklog;
  }
  
  public void setWorklog(WorkLogVO worklog) {
    this.worklog = worklog;
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
  
  public boolean equals(Object other) {
    if (!(other instanceof WorkLogCommentVO))
      return false; 
    WorkLogCommentVO castOther = (WorkLogCommentVO)other;
    return (new EqualsBuilder()).append(getCommentId(), 
        castOther.getCommentId()).isEquals();
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
