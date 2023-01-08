package com.js.oa.info.infomanager.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class InformationActionForm extends ActionForm {
  private int channelId;
  
  private String informationTitle;
  
  private String informationSubTitle;
  
  private String informationSummary;
  
  private String informationHead;
  
  private String informationAuthor;
  
  private String informationHeadFile;
  
  private String informationSeal;
  
  private String informationVaildType;
  
  private String informationType;
  
  private String informationContent;
  
  private String informationReader;
  
  private String informationReaderOrg;
  
  private String informationReaderGroup;
  
  private String validBeginTime;
  
  private String validEndTime;
  
  private String channelName;
  
  private String informationModifyTime;
  
  private String informationVersion;
  
  private String informationIssuer;
  
  private String informationIssueOrg;
  
  private String informationIssueTime;
  
  private String informationMark;
  
  private String infoRedIssueTime;
  
  private String infoRedIssueOrg;
  
  private String informationKey;
  
  private String informationIssuerId;
  
  private String keywordType;
  
  private String keyword;
  
  private String searchChannel;
  
  private String searchDate;
  
  private String searchBeginDate;
  
  private String searchEndDate;
  
  private String searchIssuerName;
  
  private String informationReaderName;
  
  private String wordDisplayType;
  
  private String isAffiche;
  
  private String afficheType;
  
  private String documentEditor;
  
  private String comeFrom;
  
  private String documentNo;
  
  private String dossierSearchDate;
  
  private String security;
  
  private String isoDealCategory;
  
  private String isoApplyName;
  
  private String isoApplyId;
  
  private String isoReceiveName;
  
  private String isoReceiveId;
  
  private String isoModifyReason;
  
  private String isoAmendmentPage;
  
  private String isoModifyVersion;
  
  private String isAllow;
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.informationTitle = "";
    this.informationSubTitle = "";
    this.informationSummary = "";
    this.informationAuthor = "";
    this.informationReader = "";
    this.informationReaderOrg = "";
    this.informationReaderGroup = "";
    this.informationReaderName = "";
    this.informationReader = "";
    this.informationReaderOrg = "";
    this.informationReaderGroup = "";
    this.documentEditor = "";
    this.comeFrom = "";
    this.documentNo = "";
  }
  
  public int getChannelId() {
    return this.channelId;
  }
  
  public void setChannelId(int channelId) {
    this.channelId = channelId;
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
  
  public String getInformationSummary() {
    return this.informationSummary;
  }
  
  public void setInformationSummary(String informationSummary) {
    this.informationSummary = informationSummary;
  }
  
  public String getInformationHead() {
    return this.informationHead;
  }
  
  public void setInformationHead(String informationHead) {
    this.informationHead = informationHead;
  }
  
  public String getInformationAuthor() {
    return this.informationAuthor;
  }
  
  public void setInformationAuthor(String informationAuthor) {
    this.informationAuthor = informationAuthor;
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
  
  public String getInformationVaildType() {
    return this.informationVaildType;
  }
  
  public void setInformationVaildType(String informationVaildType) {
    this.informationVaildType = informationVaildType;
  }
  
  public String getValidBeginTime() {
    return this.validBeginTime;
  }
  
  public void setValidBeginTime(String validBeginTime) {
    this.validBeginTime = validBeginTime;
  }
  
  public String getValidEndTime() {
    return this.validEndTime;
  }
  
  public void setValidEndTime(String validEndTime) {
    this.validEndTime = validEndTime;
  }
  
  public String getInformationType() {
    return this.informationType;
  }
  
  public void setInformationType(String informationType) {
    this.informationType = informationType;
  }
  
  public String getInformationContent() {
    return this.informationContent;
  }
  
  public void setInformationContent(String informationContent) {
    this.informationContent = informationContent;
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
  
  public String getChannelName() {
    return this.channelName;
  }
  
  public void setChannelName(String channelName) {
    this.channelName = channelName;
  }
  
  public String getInformationModifyTime() {
    return this.informationModifyTime;
  }
  
  public void setInformationModifyTime(String informationModifyTime) {
    this.informationModifyTime = informationModifyTime;
  }
  
  public String getInformationVersion() {
    return this.informationVersion;
  }
  
  public void setInformationVersion(String informationVersion) {
    this.informationVersion = informationVersion;
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
  
  public String getInformationIssueTime() {
    return this.informationIssueTime;
  }
  
  public void setInformationIssueTime(String informationIssueTime) {
    this.informationIssueTime = informationIssueTime;
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
  
  public String getInformationIssuerId() {
    return this.informationIssuerId;
  }
  
  public void setInformationIssuerId(String informationIssuerId) {
    this.informationIssuerId = informationIssuerId;
  }
  
  public String getKeywordType() {
    return this.keywordType;
  }
  
  public void setKeywordType(String keywordType) {
    this.keywordType = keywordType;
  }
  
  public String getKeyword() {
    return this.keyword;
  }
  
  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }
  
  public String getSearchChannel() {
    return this.searchChannel;
  }
  
  public void setSearchChannel(String searchChannel) {
    this.searchChannel = searchChannel;
  }
  
  public String getSearchDate() {
    return this.searchDate;
  }
  
  public void setSearchDate(String searchDate) {
    this.searchDate = searchDate;
  }
  
  public String getSearchBeginDate() {
    return this.searchBeginDate;
  }
  
  public void setSearchBeginDate(String searchBeginDate) {
    this.searchBeginDate = searchBeginDate;
  }
  
  public String getSearchEndDate() {
    return this.searchEndDate;
  }
  
  public void setSearchEndDate(String searchEndDate) {
    this.searchEndDate = searchEndDate;
  }
  
  public String getSearchIssuerName() {
    return this.searchIssuerName;
  }
  
  public void setSearchIssuerName(String searchIssuerName) {
    this.searchIssuerName = searchIssuerName;
  }
  
  public String getInformationReaderName() {
    return this.informationReaderName;
  }
  
  public void setInformationReaderName(String informationReaderName) {
    this.informationReaderName = informationReaderName;
  }
  
  public String getWordDisplayType() {
    return this.wordDisplayType;
  }
  
  public String getAfficheType() {
    return this.afficheType;
  }
  
  public String getIsAffiche() {
    return this.isAffiche;
  }
  
  public void setWordDisplayType(String wordDisplayType) {
    this.wordDisplayType = wordDisplayType;
  }
  
  public void setAfficheType(String afficheType) {
    this.afficheType = afficheType;
  }
  
  public void setIsAffiche(String isAffiche) {
    this.isAffiche = isAffiche;
  }
  
  public String getDocumentEditor() {
    return this.documentEditor;
  }
  
  public void setDocumentEditor(String documentEditor) {
    this.documentEditor = documentEditor;
  }
  
  public String getComeFrom() {
    return this.comeFrom;
  }
  
  public void setComeFrom(String comeFrom) {
    this.comeFrom = comeFrom;
  }
  
  public String getDocumentNo() {
    return this.documentNo;
  }
  
  public String getDossierSearchDate() {
    return this.dossierSearchDate;
  }
  
  public String getSecurity() {
    return this.security;
  }
  
  public String getIsoReceiveName() {
    return this.isoReceiveName;
  }
  
  public String getIsoReceiveId() {
    return this.isoReceiveId;
  }
  
  public String getIsoModifyVersion() {
    return this.isoModifyVersion;
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
  
  public String getIsoAmendmentPage() {
    return this.isoAmendmentPage;
  }
  
  public void setDocumentNo(String documentNo) {
    this.documentNo = documentNo;
  }
  
  public void setSecurity(String security) {
    this.security = security;
  }
  
  public void setDossierSearchDate(String dossierSearchDate) {
    this.dossierSearchDate = dossierSearchDate;
  }
  
  public void setIsoReceiveName(String isoReceiveName) {
    this.isoReceiveName = isoReceiveName;
  }
  
  public void setIsoReceiveId(String isoReceiveId) {
    this.isoReceiveId = isoReceiveId;
  }
  
  public void setIsoModifyVersion(String isoModifyVersion) {
    this.isoModifyVersion = isoModifyVersion;
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
  
  public void setIsoAmendmentPage(String isoAmendmentPage) {
    this.isoAmendmentPage = isoAmendmentPage;
  }
  
  public String getIsAllow() {
    return this.isAllow;
  }
  
  public void setIsAllow(String isAllow) {
    this.isAllow = isAllow;
  }
}
