package com.js.oa.info.infomanager.po;

import com.js.oa.info.channelmanager.po.InformationChannelPO;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class InformationPO implements Serializable {
  private Long informationId;
  
  private String informationTitle;
  
  private String informationSubTitle;
  
  private String informationHeadFile;
  
  private String informationSeal;
  
  private String informationAuthor;
  
  private String informationIssuer;
  
  private String informationIssueOrg;
  
  private String informationReader;
  
  private String informationReaderOrg;
  
  private String informationReaderGroup;
  
  private Date informationIssueTime = null;
  
  private Long informationIsCommend;
  
  private String informationVersion;
  
  private InformationChannelPO informationChannel = null;
  
  private Date informationModifyTime = null;
  
  private Set informationComment = null;
  
  private Set informationBrowser = null;
  
  private Set informationAccessory = null;
  
  private Set informationHistory = null;
  
  private String informationSummary;
  
  private int informationStatus;
  
  private int informationHead;
  
  private int informationKits;
  
  private int informationValidType;
  
  private Date validBeginTime = null;
  
  private Date validEndTime = null;
  
  private String informationMark;
  
  private String infoRedIssueTime;
  
  private String infoRedIssueOrg;
  
  private String informationKey;
  
  private Long informationIssuerId;
  
  private String informationType;
  
  private int informationCommonNum;
  
  private String informationReaderName;
  
  private Long informationHeadId;
  
  private Long informationSealId;
  
  private int infoDepaFlag;
  
  private int infoDepaFlag2;
  
  private int forbidCopy;
  
  private int transmitToWebsite;
  
  private String orderCode;
  
  private int displayTitle;
  
  private String otherChannel;
  
  private Integer titleColor;
  
  private String showSign;
  
  private String showSignName;
  
  private String informationContent;
  
  private String modifyEmp;
  
  private Integer dossierStatus;
  
  private Integer mustRead;
  
  private String comeFrom;
  
  private Integer isConf;
  
  private String documentNo;
  
  private String documentEditor;
  
  private String documentType;
  
  private Long domainId;
  
  private String issueOrgIdString;
  
  private String displayImage;
  
  private String wordDisplayType;
  
  private Long afficeHistoryDate;
  
  private Date afficheHiTime = null;
  
  private String informationIssueOrgId;
  
  private String inforModifyMen;
  
  private String informationOrISODoc;
  
  private String isoDocStatus;
  
  private String isoOldInfoId;
  
  private String isoSecretStatus;
  
  private String isoDealCategory;
  
  private String isoApplyName;
  
  private String isoApplyId;
  
  private String isoReceiveName;
  
  private String isoReceiveId;
  
  private String isoModifyReason;
  
  private String isoAmendmentPage;
  
  private String isoModifyVersion;
  
  private String inforModifyOrg;
  
  private Long twoUserId;
  
  private String isAllow;
  
  private String topTimeFrom;
  
  private String topTimeTo;
  
  private String topTimeStart;
  
  private String topTimeEnd;
  
  private Integer orderCodeTemp;
  
  private Long corpId;
  
  private String oid;
  
  private String reprocess;
  
  public String getReprocess() {
    return this.reprocess;
  }
  
  public void setReprocess(String reprocess) {
    this.reprocess = reprocess;
  }
  
  public Long getCorpId() {
    return this.corpId;
  }
  
  public void setCorpId(Long corpId) {
    this.corpId = corpId;
  }
  
  public String getIsAllow() {
    return this.isAllow;
  }
  
  public void setIsAllow(String isAllow) {
    this.isAllow = isAllow;
  }
  
  public Long getInformationId() {
    return this.informationId;
  }
  
  public void setInformationId(Long informationId) {
    this.informationId = informationId;
  }
  
  public String getInformationTitle() {
    return this.informationTitle;
  }
  
  public void setInformationTitle(String informationTitle) {
    this.informationTitle = informationTitle;
  }
  
  public String getInformationSubTitle() {
    return this.informationSubTitle;
  }
  
  public void setInformationSubTitle(String informationSubTitle) {
    this.informationSubTitle = informationSubTitle;
  }
  
  public int getInformationHead() {
    return this.informationHead;
  }
  
  public void setInformationHead(int informationHead) {
    this.informationHead = informationHead;
  }
  
  public String getInformationHeadFile() {
    return this.informationHeadFile;
  }
  
  public void setInformationHeadFile(String informationHeadFile) {
    this.informationHeadFile = informationHeadFile;
  }
  
  public String getInformationSeal() {
    return this.informationSeal;
  }
  
  public void setInformationSeal(String informationSeal) {
    this.informationSeal = informationSeal;
  }
  
  public String getInformationContent() {
    return this.informationContent;
  }
  
  public void setInformationContent(String informationContent) {
    this.informationContent = informationContent;
  }
  
  public int getInformationStatus() {
    return this.informationStatus;
  }
  
  public void setInformationStatus(int informationStatus) {
    this.informationStatus = informationStatus;
  }
  
  public String getInformationAuthor() {
    return this.informationAuthor;
  }
  
  public void setInformationAuthor(String informationAuthor) {
    this.informationAuthor = informationAuthor;
  }
  
  public String getInformationIssuer() {
    return this.informationIssuer;
  }
  
  public void setInformationIssuer(String informationIssuer) {
    this.informationIssuer = informationIssuer;
  }
  
  public String getInformationIssueOrg() {
    return this.informationIssueOrg;
  }
  
  public void setInformationIssueOrg(String informationIssueOrg) {
    this.informationIssueOrg = informationIssueOrg;
  }
  
  public String getInformationReader() {
    return this.informationReader;
  }
  
  public void setInformationReader(String informationReader) {
    this.informationReader = informationReader;
  }
  
  public String getInformationReaderOrg() {
    return this.informationReaderOrg;
  }
  
  public void setInformationReaderOrg(String informationReaderOrg) {
    this.informationReaderOrg = informationReaderOrg;
  }
  
  public String getInformationReaderGroup() {
    return this.informationReaderGroup;
  }
  
  public void setInformationReaderGroup(String informationReaderGroup) {
    this.informationReaderGroup = informationReaderGroup;
  }
  
  public Date getInformationIssueTime() {
    return this.informationIssueTime;
  }
  
  public void setInformationIssueTime(Date informationIssueTime) {
    this.informationIssueTime = informationIssueTime;
  }
  
  public Long getInformationIsCommend() {
    return this.informationIsCommend;
  }
  
  public void setInformationIsCommend(Long informationIsCommend) {
    this.informationIsCommend = informationIsCommend;
  }
  
  public int getInformationKits() {
    return this.informationKits;
  }
  
  public void setInformationKits(int informationKits) {
    this.informationKits = informationKits;
  }
  
  public String getInformationVersion() {
    return this.informationVersion;
  }
  
  public void setInformationVersion(String informationVersion) {
    this.informationVersion = informationVersion;
  }
  
  public InformationChannelPO getInformationChannel() {
    return this.informationChannel;
  }
  
  public void setInformationChannel(InformationChannelPO informationChannel) {
    this.informationChannel = informationChannel;
  }
  
  public Date getInformationModifyTime() {
    return this.informationModifyTime;
  }
  
  public void setInformationModifyTime(Date informationModifyTime) {
    this.informationModifyTime = informationModifyTime;
  }
  
  public Set getInformationComment() {
    return this.informationComment;
  }
  
  public void setInformationComment(Set informationComment) {
    this.informationComment = informationComment;
  }
  
  public Set getInformationBrowser() {
    return this.informationBrowser;
  }
  
  public void setInformationBrowser(Set informationBrowser) {
    this.informationBrowser = informationBrowser;
  }
  
  public Set getInformationAccessory() {
    return this.informationAccessory;
  }
  
  public void setInformationAccessory(Set informationAccessory) {
    this.informationAccessory = informationAccessory;
  }
  
  public Set getInformationHistory() {
    return this.informationHistory;
  }
  
  public void setInformationHistory(Set informationHistory) {
    this.informationHistory = informationHistory;
  }
  
  public String getInformationSummary() {
    return this.informationSummary;
  }
  
  public void setInformationSummary(String informationSummary) {
    this.informationSummary = informationSummary;
  }
  
  public int getInformationValidType() {
    return this.informationValidType;
  }
  
  public void setInformationValidType(int informationValidType) {
    this.informationValidType = informationValidType;
  }
  
  public Date getValidBeginTime() {
    return this.validBeginTime;
  }
  
  public void setValidBeginTime(Date validBeginTime) {
    this.validBeginTime = validBeginTime;
  }
  
  public Date getValidEndTime() {
    return this.validEndTime;
  }
  
  public void setValidEndTime(Date validEndTime) {
    this.validEndTime = validEndTime;
  }
  
  public String getInformationMark() {
    return this.informationMark;
  }
  
  public void setInformationMark(String informationMark) {
    this.informationMark = informationMark;
  }
  
  public String getInfoRedIssueTime() {
    return this.infoRedIssueTime;
  }
  
  public void setInfoRedIssueTime(String infoRedIssueTime) {
    this.infoRedIssueTime = infoRedIssueTime;
  }
  
  public String getInfoRedIssueOrg() {
    return this.infoRedIssueOrg;
  }
  
  public void setInfoRedIssueOrg(String infoRedIssueOrg) {
    this.infoRedIssueOrg = infoRedIssueOrg;
  }
  
  public String getInformationKey() {
    return this.informationKey;
  }
  
  public void setInformationKey(String informationKey) {
    this.informationKey = informationKey;
  }
  
  public Long getInformationIssuerId() {
    return this.informationIssuerId;
  }
  
  public void setInformationIssuerId(Long informationIssuerId) {
    this.informationIssuerId = informationIssuerId;
  }
  
  public String getInformationType() {
    return this.informationType;
  }
  
  public void setInformationType(String informationType) {
    this.informationType = informationType;
  }
  
  public int getInformationCommonNum() {
    return this.informationCommonNum;
  }
  
  public void setInformationCommonNum(int informationCommonNum) {
    this.informationCommonNum = informationCommonNum;
  }
  
  public String getInformationReaderName() {
    return this.informationReaderName;
  }
  
  public void setInformationReaderName(String informationReaderName) {
    this.informationReaderName = informationReaderName;
  }
  
  public Long getInformationHeadId() {
    return this.informationHeadId;
  }
  
  public void setInformationHeadId(Long informationHeadId) {
    this.informationHeadId = informationHeadId;
  }
  
  public Long getInformationSealId() {
    return this.informationSealId;
  }
  
  public void setInformationSealId(Long informationSealId) {
    this.informationSealId = informationSealId;
  }
  
  public int getInfoDepaFlag() {
    return this.infoDepaFlag;
  }
  
  public void setInfoDepaFlag(int infoDepaFlag) {
    this.infoDepaFlag = infoDepaFlag;
  }
  
  public int getInfoDepaFlag2() {
    return this.infoDepaFlag2;
  }
  
  public void setInfoDepaFlag2(int infoDepaFlag2) {
    this.infoDepaFlag2 = infoDepaFlag2;
  }
  
  public int getForbidCopy() {
    return this.forbidCopy;
  }
  
  public void setForbidCopy(int forbidCopy) {
    this.forbidCopy = forbidCopy;
  }
  
  public int getTransmitToWebsite() {
    return this.transmitToWebsite;
  }
  
  public void setTransmitToWebsite(int transmitToWebsite) {
    this.transmitToWebsite = transmitToWebsite;
  }
  
  public String getOrderCode() {
    return this.orderCode;
  }
  
  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }
  
  public int getDisplayTitle() {
    return this.displayTitle;
  }
  
  public void setDisplayTitle(int displayTitle) {
    this.displayTitle = displayTitle;
  }
  
  public String getOtherChannel() {
    return this.otherChannel;
  }
  
  public void setOtherChannel(String otherChannel) {
    this.otherChannel = otherChannel;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof InformationPO))
      return false; 
    InformationPO castOther = (InformationPO)other;
    return (new EqualsBuilder()).append(getInformationId(), castOther.getInformationId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getInformationId()).toHashCode();
  }
  
  public Integer getTitleColor() {
    return this.titleColor;
  }
  
  public void setTitleColor(Integer titleColor) {
    this.titleColor = titleColor;
  }
  
  public String getShowSign() {
    return this.showSign;
  }
  
  public void setShowSign(String showSign) {
    this.showSign = showSign;
  }
  
  public String getShowSignName() {
    return this.showSignName;
  }
  
  public void setShowSignName(String showSignName) {
    this.showSignName = showSignName;
  }
  
  public String getModifyEmp() {
    return this.modifyEmp;
  }
  
  public void setModifyEmp(String modifyEmp) {
    this.modifyEmp = modifyEmp;
  }
  
  public Integer getDossierStatus() {
    return this.dossierStatus;
  }
  
  public void setDossierStatus(Integer dossierStatus) {
    this.dossierStatus = dossierStatus;
  }
  
  public Integer getMustRead() {
    return this.mustRead;
  }
  
  public void setMustRead(Integer mustRead) {
    this.mustRead = mustRead;
  }
  
  public String getComeFrom() {
    return this.comeFrom;
  }
  
  public void setComeFrom(String comeFrom) {
    this.comeFrom = comeFrom;
  }
  
  public Integer getIsConf() {
    return this.isConf;
  }
  
  public void setIsConf(Integer isConf) {
    this.isConf = isConf;
  }
  
  public String getDocumentNo() {
    return this.documentNo;
  }
  
  public void setDocumentNo(String documentNo) {
    this.documentNo = documentNo;
  }
  
  public String getDocumentEditor() {
    return this.documentEditor;
  }
  
  public void setDocumentEditor(String documentEditor) {
    this.documentEditor = documentEditor;
  }
  
  public String getDocumentType() {
    return this.documentType;
  }
  
  public void setDocumentType(String documentType) {
    this.documentType = documentType;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public String getIssueOrgIdString() {
    return this.issueOrgIdString;
  }
  
  public String getDisplayImage() {
    return this.displayImage;
  }
  
  public void setIssueOrgIdString(String issueOrgIdString) {
    this.issueOrgIdString = issueOrgIdString;
  }
  
  public void setDisplayImage(String displayImage) {
    this.displayImage = displayImage;
  }
  
  public String getWordDisplayType() {
    return this.wordDisplayType;
  }
  
  public Long getAfficeHistoryDate() {
    return this.afficeHistoryDate;
  }
  
  public Date getAfficheHiTime() {
    return this.afficheHiTime;
  }
  
  public void setWordDisplayType(String wordDisplayType) {
    this.wordDisplayType = wordDisplayType;
  }
  
  public void setAfficeHistoryDate(Long afficeHistoryDate) {
    this.afficeHistoryDate = afficeHistoryDate;
  }
  
  public void setAfficheHiTime(Date afficheHiTime) {
    this.afficheHiTime = afficheHiTime;
  }
  
  public String getInformationIssueOrgId() {
    return this.informationIssueOrgId;
  }
  
  public String getInformationOrISODoc() {
    return this.informationOrISODoc;
  }
  
  public String getIsoOldInfoId() {
    return this.isoOldInfoId;
  }
  
  public String getIsoDocStatus() {
    return this.isoDocStatus;
  }
  
  public String getIsoSecretStatus() {
    return this.isoSecretStatus;
  }
  
  public String getIsoReceiveName() {
    return this.isoReceiveName;
  }
  
  public String getIsoReceiveId() {
    return this.isoReceiveId;
  }
  
  public String getIsoModifyReason() {
    return this.isoModifyReason;
  }
  
  public String getIsoDealCategory() {
    return this.isoDealCategory;
  }
  
  public String getIsoApplyName() {
    return this.isoApplyName;
  }
  
  public String getIsoApplyId() {
    return this.isoApplyId;
  }
  
  public String getIsoModifyVersion() {
    return this.isoModifyVersion;
  }
  
  public String getInforModifyMen() {
    return this.inforModifyMen;
  }
  
  public String getInforModifyOrg() {
    return this.inforModifyOrg;
  }
  
  public String getIsoAmendmentPage() {
    return this.isoAmendmentPage;
  }
  
  public Long getTwoUserId() {
    return this.twoUserId;
  }
  
  public void setInformationIssueOrgId(String informationIssueOrgId) {
    this.informationIssueOrgId = informationIssueOrgId;
  }
  
  public void setInformationOrISODoc(String informationOrISODoc) {
    this.informationOrISODoc = informationOrISODoc;
  }
  
  public void setIsoOldInfoId(String isoOldInfoId) {
    this.isoOldInfoId = isoOldInfoId;
  }
  
  public void setIsoDocStatus(String isoDocStatus) {
    this.isoDocStatus = isoDocStatus;
  }
  
  public void setIsoSecretStatus(String isoSecretStatus) {
    this.isoSecretStatus = isoSecretStatus;
  }
  
  public void setIsoReceiveName(String isoReceiveName) {
    this.isoReceiveName = isoReceiveName;
  }
  
  public void setIsoReceiveId(String isoReceiveId) {
    this.isoReceiveId = isoReceiveId;
  }
  
  public void setIsoModifyReason(String isoModifyReason) {
    this.isoModifyReason = isoModifyReason;
  }
  
  public void setIsoDealCategory(String isoDealCategory) {
    this.isoDealCategory = isoDealCategory;
  }
  
  public void setIsoApplyName(String isoApplyName) {
    this.isoApplyName = isoApplyName;
  }
  
  public void setIsoApplyId(String isoApplyId) {
    this.isoApplyId = isoApplyId;
  }
  
  public void setIsoModifyVersion(String isoModifyVersion) {
    this.isoModifyVersion = isoModifyVersion;
  }
  
  public void setInforModifyMen(String inforModifyMen) {
    this.inforModifyMen = inforModifyMen;
  }
  
  public void setInforModifyOrg(String inforModifyOrg) {
    this.inforModifyOrg = inforModifyOrg;
  }
  
  public void setIsoAmendmentPage(String isoAmendmentPage) {
    this.isoAmendmentPage = isoAmendmentPage;
  }
  
  public void setTwoUserId(Long twoUserId) {
    this.twoUserId = twoUserId;
  }
  
  public Integer getOrderCodeTemp() {
    return this.orderCodeTemp;
  }
  
  public void setOrderCodeTemp(Integer orderCodeTemp) {
    this.orderCodeTemp = orderCodeTemp;
  }
  
  public String getTopTimeEnd() {
    return this.topTimeEnd;
  }
  
  public void setTopTimeEnd(String topTimeEnd) {
    this.topTimeEnd = topTimeEnd;
  }
  
  public String getTopTimeFrom() {
    return this.topTimeFrom;
  }
  
  public void setTopTimeFrom(String topTimeFrom) {
    this.topTimeFrom = topTimeFrom;
  }
  
  public String getTopTimeStart() {
    return this.topTimeStart;
  }
  
  public void setTopTimeStart(String topTimeStart) {
    this.topTimeStart = topTimeStart;
  }
  
  public String getTopTimeTo() {
    return this.topTimeTo;
  }
  
  public void setTopTimeTo(String topTimeTo) {
    this.topTimeTo = topTimeTo;
  }
  
  public String getOid() {
    return this.oid;
  }
  
  public void setOid(String oid) {
    this.oid = oid;
  }
}
