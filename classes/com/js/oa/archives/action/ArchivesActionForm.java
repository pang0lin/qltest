package com.js.oa.archives.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ArchivesActionForm extends ActionForm {
  private String className;
  
  private String classReadName;
  
  private String classReadID;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private String generalNo;
  
  private String catalogNo;
  
  private String dossierNo;
  
  private String dossierName;
  
  private String dossierKey;
  
  private Integer saveStyle;
  
  private Integer secretLevel;
  
  private Integer pageCount = new Integer(1);
  
  private Integer copyCount = new Integer(1);
  
  private Long principal;
  
  private String principalName;
  
  private String placeNo;
  
  private String roomNo;
  
  private String fileNo;
  
  private String fileName;
  
  private String fileKey;
  
  private String fileRemark = " ";
  
  private Integer isBorrow;
  
  private String orgName;
  
  private Long orgId;
  
  private String borrowIntent;
  
  private Integer borrowCount = new Integer(1);
  
  private String saveType;
  
  private String state;
  
  private String serialNO;
  
  private String registrNO;
  
  private String classNO;
  
  private String microNO;
  
  private String dossierNO;
  
  private String model;
  
  private String archiveCode;
  
  private String duty;
  
  private String attendEmp;
  
  private String attendEmpName;
  
  private String pigeonholeOrg;
  
  private String pigeonholeOrgName;
  
  private String achievePhase;
  
  private String itemClass;
  
  private String volume;
  
  private String totalLength;
  
  private String drawingNO;
  
  private String specPage;
  
  private String cooperateUnits;
  
  private String appraisalUnit;
  
  private String patentNO;
  
  private String awardUnit;
  
  private String hortationLevel;
  
  private String merit;
  
  private String technicData;
  
  private String reachLevel;
  
  private String archivesType;
  
  public String getClassName() {
    return this.className;
  }
  
  public void setClassName(String className) {
    this.className = className;
  }
  
  public String getClassReadName() {
    return this.classReadName;
  }
  
  public void setClassReadName(String classReadName) {
    this.classReadName = classReadName;
  }
  
  public String getClassReadID() {
    return this.classReadID;
  }
  
  public void setClassReadID(String classReadID) {
    this.classReadID = classReadID;
  }
  
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
  
  public String getSaveType() {
    return this.saveType;
  }
  
  public void setSaveType(String saveType) {
    this.saveType = saveType;
  }
  
  public String getState() {
    return this.state;
  }
  
  public void setState(String state) {
    this.state = state;
  }
  
  public String getCatalogNo() {
    return this.catalogNo;
  }
  
  public void setCatalogNo(String catalogNo) {
    this.catalogNo = catalogNo;
  }
  
  public Integer getCopyCount() {
    return this.copyCount;
  }
  
  public void setCopyCount(Integer copyCount) {
    this.copyCount = copyCount;
  }
  
  public String getDossierKey() {
    return this.dossierKey;
  }
  
  public void setDossierKey(String dossierKey) {
    this.dossierKey = dossierKey;
  }
  
  public String getDossierName() {
    return this.dossierName;
  }
  
  public void setDossierName(String dossierName) {
    this.dossierName = dossierName;
  }
  
  public String getDossierNo() {
    return this.dossierNo;
  }
  
  public void setDossierNo(String dossierNo) {
    this.dossierNo = dossierNo;
  }
  
  public String getGeneralNo() {
    return this.generalNo;
  }
  
  public void setGeneralNo(String generalNo) {
    this.generalNo = generalNo;
  }
  
  public Integer getPageCount() {
    return this.pageCount;
  }
  
  public void setPageCount(Integer pageCount) {
    this.pageCount = pageCount;
  }
  
  public Long getPrincipal() {
    return this.principal;
  }
  
  public void setPrincipal(Long principal) {
    this.principal = principal;
  }
  
  public String getPrincipalName() {
    return this.principalName;
  }
  
  public void setPrincipalName(String principalName) {
    this.principalName = principalName;
  }
  
  public String getFileKey() {
    return this.fileKey;
  }
  
  public void setFileKey(String fileKey) {
    this.fileKey = fileKey;
  }
  
  public String getFileName() {
    return this.fileName;
  }
  
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  
  public String getFileNo() {
    return this.fileNo;
  }
  
  public void setFileNo(String fileNo) {
    this.fileNo = fileNo;
  }
  
  public String getFileRemark() {
    return this.fileRemark;
  }
  
  public void setFileRemark(String fileRemark) {
    this.fileRemark = fileRemark;
  }
  
  public String getPlaceNo() {
    return this.placeNo;
  }
  
  public void setPlaceNo(String placeNo) {
    this.placeNo = placeNo;
  }
  
  public String getRoomNo() {
    return this.roomNo;
  }
  
  public void setRoomNo(String roomNo) {
    this.roomNo = roomNo;
  }
  
  public Integer getSaveStyle() {
    return this.saveStyle;
  }
  
  public void setSaveStyle(Integer saveStyle) {
    this.saveStyle = saveStyle;
  }
  
  public Integer getSecretLevel() {
    return this.secretLevel;
  }
  
  public void setSecretLevel(Integer secretLevel) {
    this.secretLevel = secretLevel;
  }
  
  public String getOrgName() {
    return this.orgName;
  }
  
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }
  
  public String getBorrowIntent() {
    return this.borrowIntent;
  }
  
  public void setBorrowIntent(String borrowIntent) {
    this.borrowIntent = borrowIntent;
  }
  
  public Long getOrgId() {
    return this.orgId;
  }
  
  public void setOrgId(Long orgId) {
    this.orgId = orgId;
  }
  
  public Integer getIsBorrow() {
    return this.isBorrow;
  }
  
  public void setIsBorrow(Integer isBorrow) {
    this.isBorrow = isBorrow;
  }
  
  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
    this.className = null;
    this.classReadName = null;
    this.classReadID = null;
    this.createdEmp = null;
    this.createdOrg = null;
    this.saveType = null;
    this.catalogNo = null;
    this.copyCount = new Integer(1);
    this.dossierKey = null;
    this.dossierName = null;
    this.dossierNo = null;
    this.generalNo = null;
    this.pageCount = new Integer(1);
    this.principal = null;
    this.principalName = null;
    this.placeNo = null;
    this.roomNo = null;
    this.fileNo = null;
    this.fileName = null;
    this.fileKey = null;
    this.fileRemark = " ";
    this.isBorrow = new Integer(0);
    this.saveStyle = new Integer(0);
    this.secretLevel = new Integer(0);
    this.state = "1";
    this.orgName = null;
    this.orgId = null;
    this.borrowIntent = null;
    this.borrowCount = new Integer(1);
    this.serialNO = null;
    this.registrNO = null;
    this.classNO = null;
    this.microNO = null;
    this.dossierNO = null;
    this.model = null;
    this.archiveCode = null;
    this.duty = null;
    this.attendEmp = null;
    this.attendEmpName = null;
    this.pigeonholeOrg = null;
    this.pigeonholeOrgName = null;
    this.achievePhase = null;
    this.itemClass = null;
    this.volume = null;
    this.totalLength = null;
    this.drawingNO = null;
    this.specPage = null;
    this.cooperateUnits = null;
    this.appraisalUnit = null;
    this.patentNO = null;
    this.awardUnit = null;
    this.hortationLevel = null;
    this.merit = null;
    this.technicData = null;
    this.reachLevel = null;
  }
  
  public Integer getBorrowCount() {
    return this.borrowCount;
  }
  
  public void setBorrowCount(Integer borrowCount) {
    this.borrowCount = borrowCount;
  }
  
  public String getAchievePhase() {
    return this.achievePhase;
  }
  
  public void setAchievePhase(String achievePhase) {
    this.achievePhase = achievePhase;
  }
  
  public String getAppraisalUnit() {
    return this.appraisalUnit;
  }
  
  public void setAppraisalUnit(String appraisalUnit) {
    this.appraisalUnit = appraisalUnit;
  }
  
  public String getArchiveCode() {
    return this.archiveCode;
  }
  
  public void setArchiveCode(String archiveCode) {
    this.archiveCode = archiveCode;
  }
  
  public String getArchivesType() {
    return this.archivesType;
  }
  
  public void setArchivesType(String archivesType) {
    this.archivesType = archivesType;
  }
  
  public String getAttendEmp() {
    return this.attendEmp;
  }
  
  public void setAttendEmp(String attendEmp) {
    this.attendEmp = attendEmp;
  }
  
  public String getAttendEmpName() {
    return this.attendEmpName;
  }
  
  public void setAttendEmpName(String attendEmpName) {
    this.attendEmpName = attendEmpName;
  }
  
  public String getAwardUnit() {
    return this.awardUnit;
  }
  
  public void setAwardUnit(String awardUnit) {
    this.awardUnit = awardUnit;
  }
  
  public String getClassNO() {
    return this.classNO;
  }
  
  public void setClassNO(String classNO) {
    this.classNO = classNO;
  }
  
  public String getCooperateUnits() {
    return this.cooperateUnits;
  }
  
  public void setCooperateUnits(String cooperateUnits) {
    this.cooperateUnits = cooperateUnits;
  }
  
  public String getDossierNO() {
    return this.dossierNO;
  }
  
  public void setDossierNO(String dossierNO) {
    this.dossierNO = dossierNO;
  }
  
  public String getDrawingNO() {
    return this.drawingNO;
  }
  
  public void setDrawingNO(String drawingNO) {
    this.drawingNO = drawingNO;
  }
  
  public String getDuty() {
    return this.duty;
  }
  
  public void setDuty(String duty) {
    this.duty = duty;
  }
  
  public String getHortationLevel() {
    return this.hortationLevel;
  }
  
  public void setHortationLevel(String hortationLevel) {
    this.hortationLevel = hortationLevel;
  }
  
  public String getItemClass() {
    return this.itemClass;
  }
  
  public void setItemClass(String itemClass) {
    this.itemClass = itemClass;
  }
  
  public String getMerit() {
    return this.merit;
  }
  
  public void setMerit(String merit) {
    this.merit = merit;
  }
  
  public String getMicroNO() {
    return this.microNO;
  }
  
  public void setMicroNO(String microNO) {
    this.microNO = microNO;
  }
  
  public String getModel() {
    return this.model;
  }
  
  public void setModel(String model) {
    this.model = model;
  }
  
  public String getPatentNO() {
    return this.patentNO;
  }
  
  public void setPatentNO(String patentNO) {
    this.patentNO = patentNO;
  }
  
  public String getPigeonholeOrg() {
    return this.pigeonholeOrg;
  }
  
  public void setPigeonholeOrg(String pigeonholeOrg) {
    this.pigeonholeOrg = pigeonholeOrg;
  }
  
  public String getPigeonholeOrgName() {
    return this.pigeonholeOrgName;
  }
  
  public void setPigeonholeOrgName(String pigeonholeOrgName) {
    this.pigeonholeOrgName = pigeonholeOrgName;
  }
  
  public String getReachLevel() {
    return this.reachLevel;
  }
  
  public void setReachLevel(String reachLevel) {
    this.reachLevel = reachLevel;
  }
  
  public String getRegistrNO() {
    return this.registrNO;
  }
  
  public void setRegistrNO(String registrNO) {
    this.registrNO = registrNO;
  }
  
  public String getSerialNO() {
    return this.serialNO;
  }
  
  public void setSerialNO(String serialNO) {
    this.serialNO = serialNO;
  }
  
  public String getSpecPage() {
    return this.specPage;
  }
  
  public void setSpecPage(String specPage) {
    this.specPage = specPage;
  }
  
  public String getTechnicData() {
    return this.technicData;
  }
  
  public void setTechnicData(String technicData) {
    this.technicData = technicData;
  }
  
  public String getTotalLength() {
    return this.totalLength;
  }
  
  public void setTotalLength(String totalLength) {
    this.totalLength = totalLength;
  }
  
  public String getVolume() {
    return this.volume;
  }
  
  public void setVolume(String volume) {
    this.volume = volume;
  }
}
