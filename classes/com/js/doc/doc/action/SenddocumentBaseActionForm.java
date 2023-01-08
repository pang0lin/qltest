package com.js.doc.doc.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SenddocumentBaseActionForm extends ActionForm {
  private String contentLevel;
  
  private String fileType;
  
  private String keepSecretLevel;
  
  private String secretLevel;
  
  private String transactLevel;
  
  private String unitWord;
  
  private String baseUnitClass;
  
  private String topicalAreaClass;
  
  private String baseSorttopical;
  
  private String baseQueryLevel;
  
  private Long processId;
  
  private Long sendDocumentNumId;
  
  private String templateId;
  
  private String userRange;
  
  private String userRangeId;
  
  private String wordName;
  
  private Long sendDocumentSeqId;
  
  private String processName;
  
  private String templateName;
  
  private Integer bitNum;
  
  private Integer initValue;
  
  private String keyValue;
  
  private String numFormat;
  
  private Integer numIsYear;
  
  private String numMode;
  
  private String numName;
  
  private String numNote;
  
  private String numType;
  
  private Integer seqBitNum;
  
  private Integer seqFileType;
  
  private String seqFormat;
  
  private Integer seqInitValue;
  
  private Integer seqIsUse;
  
  private Integer seqIsYear;
  
  private String seqMode;
  
  private String seqName;
  
  private String seqUnitName;
  
  private Integer tableType;
  
  private String areaType;
  
  private String sortTopical;
  
  private String attributeTopical;
  
  private String unitType;
  
  private String unitWholeName;
  
  private String unitShortName;
  
  private String redHeadName;
  
  private String redHeadSaveName;
  
  private String openProperty;
  
  private String sendDropDownSelect1;
  
  private String sendDropDownSelect2;
  
  public String getSecretLevel() {
    return this.secretLevel;
  }
  
  public String getContentLevel() {
    return this.contentLevel;
  }
  
  public String getKeepSecretLevel() {
    return this.keepSecretLevel;
  }
  
  public String getUnitWord() {
    return this.unitWord;
  }
  
  public String getTransactLevel() {
    return this.transactLevel;
  }
  
  public void setFileType(String fileType) {
    this.fileType = fileType;
  }
  
  public void setSecretLevel(String secretLevel) {
    this.secretLevel = secretLevel;
  }
  
  public void setContentLevel(String contentLevel) {
    this.contentLevel = contentLevel;
  }
  
  public void setKeepSecretLevel(String keepSecretLevel) {
    this.keepSecretLevel = keepSecretLevel;
  }
  
  public void setUnitWord(String unitWord) {
    this.unitWord = unitWord;
  }
  
  public void setTransactLevel(String transactLevel) {
    this.transactLevel = transactLevel;
  }
  
  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }
  
  public void setUserRangeId(String userRangeId) {
    this.userRangeId = userRangeId;
  }
  
  public void setWordName(String wordName) {
    this.wordName = wordName;
  }
  
  public void setUserRange(String userRange) {
    this.userRange = userRange;
  }
  
  public void setProcessId(Long processId) {
    this.processId = processId;
  }
  
  public void setSendDocumentNumId(Long sendDocumentNumId) {
    this.sendDocumentNumId = sendDocumentNumId;
  }
  
  public void setNumFormat(String numFormat) {
    this.numFormat = numFormat;
  }
  
  public void setKeyValue(String keyValue) {
    this.keyValue = keyValue;
  }
  
  public void setNumName(String numName) {
    this.numName = numName;
  }
  
  public void setBitNum(Integer bitNum) {
    this.bitNum = bitNum;
  }
  
  public void setNumIsYear(Integer numIsYear) {
    this.numIsYear = numIsYear;
  }
  
  public void setNumNote(String numNote) {
    this.numNote = numNote;
  }
  
  public void setNumMode(String numMode) {
    this.numMode = numMode;
  }
  
  public void setInitValue(Integer initValue) {
    this.initValue = initValue;
  }
  
  public void setNumType(String numType) {
    this.numType = numType;
  }
  
  public void setSeqBitNum(Integer seqBitNum) {
    this.seqBitNum = seqBitNum;
  }
  
  public void setSeqIsYear(Integer seqIsYear) {
    this.seqIsYear = seqIsYear;
  }
  
  public void setSeqMode(String seqMode) {
    this.seqMode = seqMode;
  }
  
  public void setSeqIsUse(Integer seqIsUse) {
    this.seqIsUse = seqIsUse;
  }
  
  public void setSeqName(String seqName) {
    this.seqName = seqName;
  }
  
  public void setSeqInitValue(Integer seqInitValue) {
    this.seqInitValue = seqInitValue;
  }
  
  public void setSeqUnitName(String seqUnitName) {
    this.seqUnitName = seqUnitName;
  }
  
  public void setSeqFormat(String seqFormat) {
    this.seqFormat = seqFormat;
  }
  
  public void setSeqFileType(Integer seqFileType) {
    this.seqFileType = seqFileType;
  }
  
  public void setSortTopical(String sortTopical) {
    this.sortTopical = sortTopical;
  }
  
  public void setTableType(Integer tableType) {
    this.tableType = tableType;
  }
  
  public void setAreaType(String areaType) {
    this.areaType = areaType;
  }
  
  public void setAttributeTopical(String attributeTopical) {
    this.attributeTopical = attributeTopical;
  }
  
  public void setUnitType(String unitType) {
    this.unitType = unitType;
  }
  
  public void setUnitWholeName(String unitWholeName) {
    this.unitWholeName = unitWholeName;
  }
  
  public void setUnitShortName(String unitShortName) {
    this.unitShortName = unitShortName;
  }
  
  public void setTopicalAreaClass(String topicalAreaClass) {
    this.topicalAreaClass = topicalAreaClass;
  }
  
  public void setBaseSorttopical(String baseSorttopical) {
    this.baseSorttopical = baseSorttopical;
  }
  
  public void setBaseUnitClass(String baseUnitClass) {
    this.baseUnitClass = baseUnitClass;
  }
  
  public void setBaseQueryLevel(String baseQueryLevel) {
    this.baseQueryLevel = baseQueryLevel;
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
  
  public void setRedHeadSaveName(String redHeadSaveName) {
    this.redHeadSaveName = redHeadSaveName;
  }
  
  public void setRedHeadName(String redHeadName) {
    this.redHeadName = redHeadName;
  }
  
  public String getFileType() {
    return this.fileType;
  }
  
  public String getTemplateId() {
    return this.templateId;
  }
  
  public String getUserRangeId() {
    return this.userRangeId;
  }
  
  public String getWordName() {
    return this.wordName;
  }
  
  public String getUserRange() {
    return this.userRange;
  }
  
  public Long getProcessId() {
    return this.processId;
  }
  
  public Long getSendDocumentNumId() {
    return this.sendDocumentNumId;
  }
  
  public String getNumFormat() {
    return this.numFormat;
  }
  
  public String getKeyValue() {
    return this.keyValue;
  }
  
  public String getNumName() {
    return this.numName;
  }
  
  public Integer getBitNum() {
    return this.bitNum;
  }
  
  public Integer getNumIsYear() {
    return this.numIsYear;
  }
  
  public String getNumNote() {
    return this.numNote;
  }
  
  public String getNumMode() {
    return this.numMode;
  }
  
  public Integer getInitValue() {
    return this.initValue;
  }
  
  public String getNumType() {
    return this.numType;
  }
  
  public Integer getSeqBitNum() {
    return this.seqBitNum;
  }
  
  public Integer getSeqIsYear() {
    return this.seqIsYear;
  }
  
  public String getSeqMode() {
    return this.seqMode;
  }
  
  public Integer getSeqIsUse() {
    return this.seqIsUse;
  }
  
  public String getSeqName() {
    return this.seqName;
  }
  
  public Integer getSeqInitValue() {
    return this.seqInitValue;
  }
  
  public String getSeqUnitName() {
    return this.seqUnitName;
  }
  
  public String getSeqFormat() {
    return this.seqFormat;
  }
  
  public Integer getSeqFileType() {
    return this.seqFileType;
  }
  
  public String getSortTopical() {
    return this.sortTopical;
  }
  
  public Integer getTableType() {
    return this.tableType;
  }
  
  public String getAreaType() {
    return this.areaType;
  }
  
  public String getAttributeTopical() {
    return this.attributeTopical;
  }
  
  public String getUnitType() {
    return this.unitType;
  }
  
  public String getUnitWholeName() {
    return this.unitWholeName;
  }
  
  public String getUnitShortName() {
    return this.unitShortName;
  }
  
  public String getTopicalAreaClass() {
    return this.topicalAreaClass;
  }
  
  public String getBaseSorttopical() {
    return this.baseSorttopical;
  }
  
  public String getBaseUnitClass() {
    return this.baseUnitClass;
  }
  
  public String getBaseQueryLevel() {
    return this.baseQueryLevel;
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
  
  public String getRedHeadSaveName() {
    return this.redHeadSaveName;
  }
  
  public String getRedHeadName() {
    return this.redHeadName;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getOpenProperty() {
    return this.openProperty;
  }
  
  public void setOpenProperty(String openProperty) {
    this.openProperty = openProperty;
  }
  
  public String getSendDropDownSelect1() {
    return this.sendDropDownSelect1;
  }
  
  public void setSendDropDownSelect1(String sendDropDownSelect1) {
    this.sendDropDownSelect1 = sendDropDownSelect1;
  }
  
  public String getSendDropDownSelect2() {
    return this.sendDropDownSelect2;
  }
  
  public void setSendDropDownSelect2(String sendDropDownSelect2) {
    this.sendDropDownSelect2 = sendDropDownSelect2;
  }
}
