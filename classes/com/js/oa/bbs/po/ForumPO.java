package com.js.oa.bbs.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ForumPO implements Serializable {
  private long id;
  
  private long forumTopOrder;
  
  private long forumTopicId;
  
  private String forumTitle;
  
  private byte forumType;
  
  private String forumAttachName;
  
  private String forumAttachSave;
  
  private String forumAuthor;
  
  private long forumAuthorId;
  
  private String forumAuthorIp;
  
  private String forumAuthorOrg;
  
  private String forumImage;
  
  private String forumSign;
  
  private Date forumIssueTime = null;
  
  private Date forumModifyTime = null;
  
  private int forumKits;
  
  private int forumRevertNum;
  
  private ForumClassPO forumClass;
  
  private byte forumIsSoul;
  
  private String content;
  
  private byte anonymous;
  
  private String forumContent;
  
  private Long domainId;
  
  private int examinNum;
  
  private int forumNotPrint;
  
  private int forumNotUpd;
  
  private int forumNotFlow;
  
  private Date newretime = null;
  
  public Date getNewretime() {
    return this.newretime;
  }
  
  public void setNewretime(Date newretime) {
    this.newretime = newretime;
  }
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public long getForumTopicId() {
    return this.forumTopicId;
  }
  
  public void setForumTopicId(long forumTopicId) {
    this.forumTopicId = forumTopicId;
  }
  
  public String getForumTitle() {
    return this.forumTitle;
  }
  
  public void setForumTitle(String forumTitle) {
    this.forumTitle = forumTitle;
  }
  
  public byte getForumType() {
    return this.forumType;
  }
  
  public void setForumType(byte forumType) {
    this.forumType = forumType;
  }
  
  public String getForumContent() {
    return this.forumContent;
  }
  
  public void setForumContent(String forumContent) {
    this.forumContent = forumContent;
  }
  
  public String getForumAttachName() {
    return this.forumAttachName;
  }
  
  public void setForumAttachName(String forumAttachName) {
    this.forumAttachName = forumAttachName;
  }
  
  public String getForumAttachSave() {
    return this.forumAttachSave;
  }
  
  public void setForumAttachSave(String forumAttachSave) {
    this.forumAttachSave = forumAttachSave;
  }
  
  public String getForumAuthor() {
    return this.forumAuthor;
  }
  
  public void setForumAuthor(String forumAuthor) {
    this.forumAuthor = forumAuthor;
  }
  
  public long getForumAuthorId() {
    return this.forumAuthorId;
  }
  
  public void setForumAuthorId(long forumAuthorId) {
    this.forumAuthorId = forumAuthorId;
  }
  
  public String getForumAuthorIp() {
    return this.forumAuthorIp;
  }
  
  public void setForumAuthorIp(String forumAuthorIp) {
    this.forumAuthorIp = forumAuthorIp;
  }
  
  public String getForumAuthorOrg() {
    return this.forumAuthorOrg;
  }
  
  public void setForumAuthorOrg(String forumAuthorOrg) {
    this.forumAuthorOrg = forumAuthorOrg;
  }
  
  public String getForumImage() {
    return this.forumImage;
  }
  
  public void setForumImage(String forumImage) {
    this.forumImage = forumImage;
  }
  
  public String getForumSign() {
    return this.forumSign;
  }
  
  public void setForumSign(String forumSign) {
    this.forumSign = forumSign;
  }
  
  public Date getForumIssueTime() {
    return this.forumIssueTime;
  }
  
  public void setForumIssueTime(Date forumIssueTime) {
    this.forumIssueTime = forumIssueTime;
  }
  
  public Date getForumModifyTime() {
    return this.forumModifyTime;
  }
  
  public void setForumModifyTime(Date forumModifyTime) {
    this.forumModifyTime = forumModifyTime;
  }
  
  public int getForumKits() {
    return this.forumKits;
  }
  
  public void setForumKits(int forumKits) {
    this.forumKits = forumKits;
  }
  
  public int getForumRevertNum() {
    return this.forumRevertNum;
  }
  
  public void setForumRevertNum(int forumRevertNum) {
    this.forumRevertNum = forumRevertNum;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ForumPO))
      return false; 
    ForumPO castOther = (ForumPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public ForumClassPO getForumClass() {
    return this.forumClass;
  }
  
  public void setForumClass(ForumClassPO forumClass) {
    this.forumClass = forumClass;
  }
  
  public byte getForumIsSoul() {
    return this.forumIsSoul;
  }
  
  public void setForumIsSoul(byte forumIsSoul) {
    this.forumIsSoul = forumIsSoul;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public byte getAnonymous() {
    return this.anonymous;
  }
  
  public void setAnonymous(byte anonymous) {
    this.anonymous = anonymous;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public int getExaminNum() {
    return this.examinNum;
  }
  
  public long getForumTopOrder() {
    return this.forumTopOrder;
  }
  
  public int getForumNotUpd() {
    return this.forumNotUpd;
  }
  
  public int getForumNotPrint() {
    return this.forumNotPrint;
  }
  
  public int getForumNotFlow() {
    return this.forumNotFlow;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public void setExaminNum(int examinNum) {
    this.examinNum = examinNum;
  }
  
  public void setForumTopOrder(long forumTopOrder) {
    this.forumTopOrder = forumTopOrder;
  }
  
  public void setForumNotUpd(int forumNotUpd) {
    this.forumNotUpd = forumNotUpd;
  }
  
  public void setForumNotPrint(int forumNotPrint) {
    this.forumNotPrint = forumNotPrint;
  }
  
  public void setForumNotFlow(int forumNotFlow) {
    this.forumNotFlow = forumNotFlow;
  }
}
