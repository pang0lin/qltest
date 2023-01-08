package com.js.oa.archives.vo;

import com.js.oa.archives.po.ArchivesFilePO;
import java.io.Serializable;
import java.util.Date;

public class ArchivesFileVO implements Serializable {
  private ArchivesFilePO archivesFilePO = null;
  
  private Long fileId;
  
  private Long dossierId;
  
  private String placeNo;
  
  private String roomNo;
  
  private String fileNo;
  
  private Long principal;
  
  private String principalName;
  
  private String fileName;
  
  private String fileKey;
  
  private Integer pageCount;
  
  private Integer copyCount;
  
  private Integer residualCount;
  
  private Integer secretLevel;
  
  private Integer saveStyle;
  
  private Date saveBeginTime = null;
  
  private Date saveEndTime = null;
  
  private String fileRemark;
  
  private Integer fileStatus;
  
  private Integer pigeonholeStatus;
  
  private Boolean maintenance;
  
  private Integer isBorrow;
  
  private String classReader;
  
  private String classReadOrg;
  
  private String classReadGroup;
  
  private String classReadName;
  
  private Integer isWaitPigeonhole;
  
  private String pigeonholeCaption;
  
  private String pigeonholeTypeName;
  
  private String pigeonholeFileName;
  
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
  
  private Date pigeonholeDate = null;
  
  private String achievePhase;
  
  private String itemClass;
  
  private String volume;
  
  private String totalLength;
  
  private String drawingNO;
  
  private String specPage;
  
  private String cooperateUnits;
  
  private Date appraisalDate = null;
  
  private String appraisalUnit;
  
  private String patentNO;
  
  private Date approveDate = null;
  
  private Date awardDate = null;
  
  private String awardUnit;
  
  private String hortationLevel;
  
  private String merit;
  
  private String technicData;
  
  private String reachLevel;
  
  private String archivesType;
  
  public ArchivesFileVO(ArchivesFilePO archivesFilePO, Long dossierId, String placeNo, String roomNo, String fileNo, Long principal, String principalName, String fileName, String fileKey, Integer pageCount, Integer copyCount, Integer residualCount, Integer secretLevel, Integer saveStyle, Date saveBeginTime, Date saveEndTime, String fileRemark, Integer fileStatus, Integer pigeonholeStatus, Integer isBorrow, String classReader, String classReadOrg, String classReadGroup, String classReadName, Integer isWaitPigeonhole, String pigeonholeCaption, String pigeonholeTypeName, String pigeonholeFileName) {
    this.archivesFilePO = archivesFilePO;
    this.dossierId = dossierId;
    this.placeNo = placeNo;
    this.roomNo = roomNo;
    this.fileNo = fileNo;
    this.principal = principal;
    this.principalName = principalName;
    this.fileName = fileName;
    this.fileKey = fileKey;
    this.pageCount = pageCount;
    this.copyCount = copyCount;
    this.residualCount = residualCount;
    this.secretLevel = secretLevel;
    this.saveStyle = saveStyle;
    this.saveBeginTime = saveBeginTime;
    this.saveEndTime = saveEndTime;
    this.fileRemark = fileRemark;
    this.fileStatus = fileStatus;
    this.pigeonholeStatus = pigeonholeStatus;
    this.isBorrow = isBorrow;
    this.classReader = classReader;
    this.classReadOrg = classReadOrg;
    this.classReadGroup = classReadGroup;
    this.classReadName = classReadName;
    this.isWaitPigeonhole = isWaitPigeonhole;
    this.pigeonholeCaption = pigeonholeCaption;
    this.pigeonholeTypeName = pigeonholeTypeName;
    this.pigeonholeFileName = pigeonholeFileName;
  }
  
  public ArchivesFileVO() {}
  
  public ArchivesFilePO getArchivesFilePO() {
    return this.archivesFilePO;
  }
  
  public void setArchivesFilePO(ArchivesFilePO archivesFilePO) {
    this.archivesFilePO = archivesFilePO;
  }
  
  public Integer getCopyCount() {
    return this.copyCount;
  }
  
  public void setCopyCount(Integer copyCount) {
    this.copyCount = copyCount;
  }
  
  public Long getDossierId() {
    return this.dossierId;
  }
  
  public void setDossierId(Long dossierId) {
    this.dossierId = dossierId;
  }
  
  public Long getFileId() {
    return this.fileId;
  }
  
  public void setFileId(Long fileId) {
    this.fileId = fileId;
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
  
  public Integer getFileStatus() {
    return this.fileStatus;
  }
  
  public void setFileStatus(Integer fileStatus) {
    this.fileStatus = fileStatus;
  }
  
  public Integer getPageCount() {
    return this.pageCount;
  }
  
  public void setPageCount(Integer pageCount) {
    this.pageCount = pageCount;
  }
  
  public Integer getPigeonholeStatus() {
    return this.pigeonholeStatus;
  }
  
  public void setPigeonholeStatus(Integer pigeonholeStatus) {
    this.pigeonholeStatus = pigeonholeStatus;
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
  
  public Date getSaveBeginTime() {
    return this.saveBeginTime;
  }
  
  public void setSaveBeginTime(Date saveBeginTime) {
    this.saveBeginTime = saveBeginTime;
  }
  
  public Date getSaveEndTime() {
    return this.saveEndTime;
  }
  
  public void setSaveEndTime(Date saveEndTime) {
    this.saveEndTime = saveEndTime;
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
  
  public Integer getIsBorrow() {
    return this.isBorrow;
  }
  
  public void setIsBorrow(Integer isBorrow) {
    this.isBorrow = isBorrow;
  }
  
  public Boolean getMaintenance() {
    return this.maintenance;
  }
  
  public void setMaintenance(Boolean maintenance) {
    this.maintenance = maintenance;
  }
  
  public Integer getResidualCount() {
    return this.residualCount;
  }
  
  public void setResidualCount(Integer residualCount) {
    this.residualCount = residualCount;
  }
  
  public ArchivesFileVO conversionPO(ArchivesFilePO archivesFilePO) {
    this.fileId = archivesFilePO.getFileId();
    this.dossierId = archivesFilePO.getDossierId();
    this.placeNo = archivesFilePO.getPlaceNo();
    this.roomNo = archivesFilePO.getRoomNo();
    this.fileNo = archivesFilePO.getFileNo();
    this.principal = archivesFilePO.getPrincipal();
    this.principalName = archivesFilePO.getPrincipalName();
    this.fileName = archivesFilePO.getFileName();
    this.fileKey = archivesFilePO.getFileKey();
    this.pageCount = archivesFilePO.getPageCount();
    this.copyCount = archivesFilePO.getCopyCount();
    this.residualCount = archivesFilePO.getResidualCount();
    this.secretLevel = archivesFilePO.getSecretLevel();
    this.saveStyle = archivesFilePO.getSaveStyle();
    this.saveBeginTime = archivesFilePO.getSaveBeginTime();
    this.saveEndTime = archivesFilePO.getSaveEndTime();
    this.fileRemark = archivesFilePO.getFileRemark();
    this.fileStatus = archivesFilePO.getFileStatus();
    this.pigeonholeStatus = archivesFilePO.getPigeonholeStatus();
    this.isBorrow = archivesFilePO.getIsBorrow();
    this.classReader = this.classReader;
    this.classReadOrg = this.classReadOrg;
    this.classReadGroup = this.classReadGroup;
    this.classReadName = this.classReadName;
    this.isWaitPigeonhole = archivesFilePO.getIsWaitPigeonhole();
    this.pigeonholeCaption = archivesFilePO.getPigeonholeCaption();
    this.pigeonholeTypeName = archivesFilePO.getPigeonholeTypeName();
    this.pigeonholeFileName = archivesFilePO.getPigeonholeFileName();
    return this;
  }
  
  public String toString() {
    return "ArchivesFileVO{archivesFilePO=" + this.archivesFilePO + "}";
  }
  
  public boolean equals(Object o) {
    if (this == o)
      return true; 
    if (!(o instanceof ArchivesFileVO))
      return false; 
    ArchivesFileVO archivesFileVO = (ArchivesFileVO)o;
    if ((this.archivesFilePO != null) ? !this.archivesFilePO.equals(archivesFileVO.archivesFilePO) : (archivesFileVO.archivesFilePO != null))
      return false; 
    if ((this.fileId != null) ? !this.fileId.equals(archivesFileVO.fileId) : (archivesFileVO.fileId != null))
      return false; 
    if ((this.dossierId != null) ? !this.dossierId.equals(archivesFileVO.dossierId) : (archivesFileVO.dossierId != null))
      return false; 
    if ((this.placeNo != null) ? !this.placeNo.equals(archivesFileVO.placeNo) : (archivesFileVO.placeNo != null))
      return false; 
    if ((this.roomNo != null) ? !this.roomNo.equals(archivesFileVO.roomNo) : (archivesFileVO.roomNo != null))
      return false; 
    if ((this.fileNo != null) ? !this.fileNo.equals(archivesFileVO.fileNo) : (archivesFileVO.fileNo != null))
      return false; 
    if ((this.principal != null) ? !this.principal.equals(archivesFileVO.principal) : (archivesFileVO.principal != null))
      return false; 
    if ((this.principalName != null) ? !this.principalName.equals(archivesFileVO.principalName) : (archivesFileVO.principalName != null))
      return false; 
    if ((this.fileName != null) ? !this.fileName.equals(archivesFileVO.fileName) : (archivesFileVO.fileName != null))
      return false; 
    if ((this.fileKey != null) ? !this.fileKey.equals(archivesFileVO.fileKey) : (archivesFileVO.fileKey != null))
      return false; 
    if ((this.pageCount != null) ? !this.pageCount.equals(archivesFileVO.pageCount) : (archivesFileVO.pageCount != null))
      return false; 
    if ((this.copyCount != null) ? !this.copyCount.equals(archivesFileVO.copyCount) : (archivesFileVO.copyCount != null))
      return false; 
    if ((this.saveStyle != null) ? !this.saveStyle.equals(archivesFileVO.saveStyle) : (archivesFileVO.saveStyle != null))
      return false; 
    if ((this.saveBeginTime != null) ? !this.saveBeginTime.equals(archivesFileVO.saveBeginTime) : (archivesFileVO.saveBeginTime != null))
      return false; 
    if ((this.saveEndTime != null) ? !this.saveEndTime.equals(archivesFileVO.saveEndTime) : (archivesFileVO.saveEndTime != null))
      return false; 
    if ((this.secretLevel != null) ? !this.secretLevel.equals(archivesFileVO.secretLevel) : (archivesFileVO.secretLevel != null))
      return false; 
    if ((this.fileRemark != null) ? !this.fileRemark.equals(archivesFileVO.fileRemark) : (archivesFileVO.fileRemark != null))
      return false; 
    if ((this.pigeonholeStatus != null) ? !this.pigeonholeStatus.equals(archivesFileVO.pigeonholeStatus) : (archivesFileVO.pigeonholeStatus != null))
      return false; 
    if ((this.isBorrow != null) ? !this.isBorrow.equals(archivesFileVO.isBorrow) : (archivesFileVO.isBorrow != null))
      return false; 
    if ((this.residualCount != null) ? !this.residualCount.equals(archivesFileVO.residualCount) : (archivesFileVO.residualCount != null))
      return false; 
    if ((this.classReader != null) ? !this.classReader.equals(archivesFileVO.classReader) : (archivesFileVO.classReader != null))
      return false; 
    if ((this.classReadOrg != null) ? !this.classReadOrg.equals(archivesFileVO.classReadOrg) : (archivesFileVO.classReadOrg != null))
      return false; 
    if ((this.classReadGroup != null) ? !this.classReadGroup.equals(archivesFileVO.classReadGroup) : (archivesFileVO.classReadGroup != null))
      return false; 
    if ((this.classReadName != null) ? !this.classReadName.equals(archivesFileVO.classReadName) : (archivesFileVO.classReadName != null))
      return false; 
    if ((this.isWaitPigeonhole != null) ? !this.isWaitPigeonhole.equals(archivesFileVO.isWaitPigeonhole) : (archivesFileVO.isWaitPigeonhole != null))
      return false; 
    if ((this.pigeonholeCaption != null) ? !this.pigeonholeCaption.equals(archivesFileVO.pigeonholeCaption) : (archivesFileVO.pigeonholeCaption != null))
      return false; 
    if ((this.pigeonholeTypeName != null) ? !this.pigeonholeTypeName.equals(archivesFileVO.pigeonholeTypeName) : (archivesFileVO.pigeonholeTypeName != null))
      return false; 
    if ((this.pigeonholeFileName != null) ? !this.pigeonholeFileName.equals(archivesFileVO.pigeonholeFileName) : (archivesFileVO.pigeonholeFileName != null))
      return false; 
    return true;
  }
  
  public int hashCode() {
    int result = this.fileId.hashCode();
    return result;
  }
  
  public String getClassReader() {
    return this.classReader;
  }
  
  public void setClassReader(String classReader) {
    this.classReader = classReader;
  }
  
  public String getClassReadGroup() {
    return this.classReadGroup;
  }
  
  public void setClassReadGroup(String classReadGroup) {
    this.classReadGroup = classReadGroup;
  }
  
  public String getClassReadName() {
    return this.classReadName;
  }
  
  public void setClassReadName(String classReadName) {
    this.classReadName = classReadName;
  }
  
  public String getClassReadOrg() {
    return this.classReadOrg;
  }
  
  public void setClassReadOrg(String classReadOrg) {
    this.classReadOrg = classReadOrg;
  }
  
  public Integer getIsWaitPigeonhole() {
    return this.isWaitPigeonhole;
  }
  
  public void setIsWaitPigeonhole(Integer isWaitPigeonhole) {
    this.isWaitPigeonhole = isWaitPigeonhole;
  }
  
  public String getPigeonholeCaption() {
    return this.pigeonholeCaption;
  }
  
  public void setPigeonholeCaption(String pigeonholeCaption) {
    this.pigeonholeCaption = pigeonholeCaption;
  }
  
  public String getPigeonholeFileName() {
    return this.pigeonholeFileName;
  }
  
  public void setPigeonholeFileName(String pigeonholeFileName) {
    this.pigeonholeFileName = pigeonholeFileName;
  }
  
  public String getPigeonholeTypeName() {
    return this.pigeonholeTypeName;
  }
  
  public void setPigeonholeTypeName(String pigeonholeTypeName) {
    this.pigeonholeTypeName = pigeonholeTypeName;
  }
  
  public String getAchievePhase() {
    return this.achievePhase;
  }
  
  public void setAchievePhase(String achievePhase) {
    this.achievePhase = achievePhase;
  }
  
  public Date getAppraisalDate() {
    return this.appraisalDate;
  }
  
  public void setAppraisalDate(Date appraisalDate) {
    this.appraisalDate = appraisalDate;
  }
  
  public String getAppraisalUnit() {
    return this.appraisalUnit;
  }
  
  public void setAppraisalUnit(String appraisalUnit) {
    this.appraisalUnit = appraisalUnit;
  }
  
  public Date getApproveDate() {
    return this.approveDate;
  }
  
  public void setApproveDate(Date approveDate) {
    this.approveDate = approveDate;
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
  
  public Date getAwardDate() {
    return this.awardDate;
  }
  
  public void setAwardDate(Date awardDate) {
    this.awardDate = awardDate;
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
  
  public Date getPigeonholeDate() {
    return this.pigeonholeDate;
  }
  
  public void setPigeonholeDate(Date pigeonholeDate) {
    this.pigeonholeDate = pigeonholeDate;
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
