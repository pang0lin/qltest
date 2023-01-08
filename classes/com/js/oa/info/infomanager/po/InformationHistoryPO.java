package com.js.oa.info.infomanager.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class InformationHistoryPO implements Serializable {
  private Long historyId;
  
  private String historyTitle;
  
  private String historySubTitle;
  
  private Long historyIssuerId;
  
  private String historyIssuerName;
  
  private Date historyTime = null;
  
  private InformationPO information;
  
  private Set inforHistoryAccessory = null;
  
  private String historyVersion;
  
  private String historySummary;
  
  private String historyIssueOrg;
  
  private String historyKey;
  
  private int historyHead;
  
  private String historyHeadFile;
  
  private String historyRedIssueTime;
  
  private String historyRedIssueOrg;
  
  private String historySeal;
  
  private String historyMark;
  
  private String historyAuthor;
  
  private String historyContent;
  
  private Long domainId;
  
  private String hisDisplayImage;
  
  private String isoDealCategory;
  
  private String isoAmendmentPage;
  
  private String isoModifyReason;
  
  public void setHistoryIssueOrg(String historyIssueOrg) {
    this.historyIssueOrg = historyIssueOrg;
  }
  
  public String getHistoryIssueOrg() {
    return this.historyIssueOrg;
  }
  
  public Long getHistoryId() {
    return this.historyId;
  }
  
  public void setHistoryId(Long historyId) {
    this.historyId = historyId;
  }
  
  public String getHistoryTitle() {
    return this.historyTitle;
  }
  
  public void setHistoryTitle(String historyTitle) {
    this.historyTitle = historyTitle;
  }
  
  public String getHistorySubTitle() {
    return this.historySubTitle;
  }
  
  public void setHistorySubTitle(String historySubTitle) {
    this.historySubTitle = historySubTitle;
  }
  
  public String getHistoryContent() {
    return this.historyContent;
  }
  
  public void setHistoryContent(String historyContent) {
    this.historyContent = historyContent;
  }
  
  public Long getHistoryIssuerId() {
    return this.historyIssuerId;
  }
  
  public void setHistoryIssuerId(Long historyIssuerId) {
    this.historyIssuerId = historyIssuerId;
  }
  
  public String getHistoryIssuerName() {
    return this.historyIssuerName;
  }
  
  public void setHistoryIssuerName(String historyIssuerName) {
    this.historyIssuerName = historyIssuerName;
  }
  
  public Date getHistoryTime() {
    return this.historyTime;
  }
  
  public void setHistoryTime(Date historyTime) {
    this.historyTime = historyTime;
  }
  
  public InformationPO getInformation() {
    return this.information;
  }
  
  public void setInformation(InformationPO information) {
    this.information = information;
  }
  
  public Set getInforHistoryAccessory() {
    return this.inforHistoryAccessory;
  }
  
  public void setInforHistoryAccessory(Set inforHistoryAccessory) {
    this.inforHistoryAccessory = inforHistoryAccessory;
  }
  
  public String getHistoryVersion() {
    return this.historyVersion;
  }
  
  public void setHistoryVersion(String historyVersion) {
    this.historyVersion = historyVersion;
  }
  
  public String getHistorySummary() {
    return this.historySummary;
  }
  
  public void setHistorySummary(String historySummary) {
    this.historySummary = historySummary;
  }
  
  public String getHistoryKey() {
    return this.historyKey;
  }
  
  public void setHistoryKey(String historyKey) {
    this.historyKey = historyKey;
  }
  
  public int getHistoryHead() {
    return this.historyHead;
  }
  
  public void setHistoryHead(int historyHead) {
    this.historyHead = historyHead;
  }
  
  public String getHistoryHeadFile() {
    return this.historyHeadFile;
  }
  
  public void setHistoryHeadFile(String historyHeadFile) {
    this.historyHeadFile = historyHeadFile;
  }
  
  public String getHistoryRedIssueTime() {
    return this.historyRedIssueTime;
  }
  
  public void setHistoryRedIssueTime(String historyRedIssueTime) {
    this.historyRedIssueTime = historyRedIssueTime;
  }
  
  public String getHistoryRedIssueOrg() {
    return this.historyRedIssueOrg;
  }
  
  public void setHistoryRedIssueOrg(String historyRedIssueOrg) {
    this.historyRedIssueOrg = historyRedIssueOrg;
  }
  
  public String getHistorySeal() {
    return this.historySeal;
  }
  
  public void setHistorySeal(String historySeal) {
    this.historySeal = historySeal;
  }
  
  public String getHistoryMark() {
    return this.historyMark;
  }
  
  public void setHistoryMark(String historyMark) {
    this.historyMark = historyMark;
  }
  
  public String getHistoryAuthor() {
    return this.historyAuthor;
  }
  
  public void setHistoryAuthor(String historyAuthor) {
    this.historyAuthor = historyAuthor;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof InformationPO))
      return false; 
    InformationHistoryPO castOther = (InformationHistoryPO)other;
    return (new EqualsBuilder()).append(getHistoryId(), castOther.getHistoryId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getHistoryId()).toHashCode();
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public String getHisDisplayImage() {
    return this.hisDisplayImage;
  }
  
  public String getIsoModifyReason() {
    return this.isoModifyReason;
  }
  
  public String getIsoDealCategory() {
    return this.isoDealCategory;
  }
  
  public String getIsoAmendmentPage() {
    return this.isoAmendmentPage;
  }
  
  public void setHisDisplayImage(String hisDisplayImage) {
    this.hisDisplayImage = hisDisplayImage;
  }
  
  public void setIsoModifyReason(String isoModifyReason) {
    this.isoModifyReason = isoModifyReason;
  }
  
  public void setIsoDealCategory(String isoDealCategory) {
    this.isoDealCategory = isoDealCategory;
  }
  
  public void setIsoAmendmentPage(String isoAmendmentPage) {
    this.isoAmendmentPage = isoAmendmentPage;
  }
}
