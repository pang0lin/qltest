package com.js.doc.doc.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ReceiveDocumentBaseActionForm extends ActionForm {
  private String receiveFileType;
  
  private String pigeonholeType;
  
  private String decumentKind;
  
  private String receiveSecretLevel;
  
  private String urgencyLevel;
  
  private String keepTerm;
  
  private String seqNameR;
  
  private String seqProceNameR;
  
  private String seqProceId;
  
  private String seqBitNumR;
  
  private String seqIsYearR;
  
  private String seqInitValueR;
  
  private String seqFormatR;
  
  private String seqModeR;
  
  private String seqfigR;
  
  private String seqIsName;
  
  private String seqType;
  
  private String receiveDropDownSelect1;
  
  private String receiveDropDownSelect2;
  
  public String getPigeonholeType() {
    return this.pigeonholeType;
  }
  
  public String getUrgencyLevel() {
    return this.urgencyLevel;
  }
  
  public String getReceiveFileType() {
    return this.receiveFileType;
  }
  
  public void setReceiveSecretLevel(String receiveSecretLevel) {
    this.receiveSecretLevel = receiveSecretLevel;
  }
  
  public void setPigeonholeType(String pigeonholeType) {
    this.pigeonholeType = pigeonholeType;
  }
  
  public void setUrgencyLevel(String urgencyLevel) {
    this.urgencyLevel = urgencyLevel;
  }
  
  public void setReceiveFileType(String receiveFileType) {
    this.receiveFileType = receiveFileType;
  }
  
  public void setKeepTerm(String keepTerm) {
    this.keepTerm = keepTerm;
  }
  
  public void setDecumentKind(String decumentKind) {
    this.decumentKind = decumentKind;
  }
  
  public void setSeqModeR(String seqModeR) {
    this.seqModeR = seqModeR;
  }
  
  public void setSeqIsYearR(String seqIsYearR) {
    this.seqIsYearR = seqIsYearR;
  }
  
  public void setSeqfigR(String seqfigR) {
    this.seqfigR = seqfigR;
  }
  
  public void setSeqProceNameR(String seqProceNameR) {
    this.seqProceNameR = seqProceNameR;
  }
  
  public void setSeqNameR(String seqNameR) {
    this.seqNameR = seqNameR;
  }
  
  public void setSeqInitValueR(String seqInitValueR) {
    this.seqInitValueR = seqInitValueR;
  }
  
  public void setSeqBitNumR(String seqBitNumR) {
    this.seqBitNumR = seqBitNumR;
  }
  
  public void setSeqProceId(String seqProceId) {
    this.seqProceId = seqProceId;
  }
  
  public void setSeqFormatR(String seqFormatR) {
    this.seqFormatR = seqFormatR;
  }
  
  public void setSeqIsName(String seqIsName) {
    this.seqIsName = seqIsName;
  }
  
  public void setSeqType(String seqType) {
    this.seqType = seqType;
  }
  
  public String getReceiveSecretLevel() {
    return this.receiveSecretLevel;
  }
  
  public String getKeepTerm() {
    return this.keepTerm;
  }
  
  public String getDecumentKind() {
    return this.decumentKind;
  }
  
  public String getSeqModeR() {
    return this.seqModeR;
  }
  
  public String getSeqIsYearR() {
    return this.seqIsYearR;
  }
  
  public String getSeqfigR() {
    return this.seqfigR;
  }
  
  public String getSeqProceNameR() {
    return this.seqProceNameR;
  }
  
  public String getSeqNameR() {
    return this.seqNameR;
  }
  
  public String getSeqInitValueR() {
    return this.seqInitValueR;
  }
  
  public String getSeqBitNumR() {
    return this.seqBitNumR;
  }
  
  public String getSeqProceId() {
    return this.seqProceId;
  }
  
  public String getSeqFormatR() {
    return this.seqFormatR;
  }
  
  public String getSeqIsName() {
    return this.seqIsName;
  }
  
  public String getSeqType() {
    return this.seqType;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {}
  
  public String getReceiveDropDownSelect1() {
    return this.receiveDropDownSelect1;
  }
  
  public void setReceiveDropDownSelect1(String receiveDropDownSelect1) {
    this.receiveDropDownSelect1 = receiveDropDownSelect1;
  }
  
  public String getReceiveDropDownSelect2() {
    return this.receiveDropDownSelect2;
  }
  
  public void setReceiveDropDownSelect2(String receiveDropDownSelect2) {
    this.receiveDropDownSelect2 = receiveDropDownSelect2;
  }
}
