package com.js.oa.archives.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ArchivesFilePO implements Serializable {
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
  
  private String classReader;
  
  private String classReadOrg;
  
  private String classReadGroup;
  
  private String classReadName;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private Long domainId;
  
  private Integer isBorrow;
  
  private Integer isWaitPigeonhole;
  
  private String pigeonholeCaption;
  
  private String pigeonholeTypeName;
  
  private String pigeonholeFileName;
  
  private ArchivesDossierPO archivesDossier;
  
  private Set archivesFileAccessory = null;
  
  private Set archivesBorrow = null;
  
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
  
  public ArchivesFilePO(ArchivesDossierPO archivesDossier, Long dossierId, String placeNo, String roomNo, String fileNo, Long principal, String principalName, String fileName, String fileKey, Integer pageCount, Integer copyCount, Integer residualCount, Integer secretLevel, Integer saveStyle, Date saveBeginTime, Date saveEndTime, String fileRemark, Integer fileStatus, Integer pigeonholeStatus, String classReader, String classReadOrg, String classReadGroup, String classReadName, Long createdEmp, Long createdOrg, Integer isBorrow, Integer isWaitPigeonhole, String pigeonholeCaption, String pigeonholeTypeName, String pigeonholeFileName, Set archivesFileAccessory, Set archivesBorrow) {
    this.archivesDossier = archivesDossier;
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
    this.createdEmp = createdEmp;
    this.createdOrg = createdOrg;
    this.archivesFileAccessory = archivesFileAccessory;
    this.archivesBorrow = archivesBorrow;
    this.isWaitPigeonhole = isWaitPigeonhole;
    this.pigeonholeCaption = pigeonholeCaption;
    this.pigeonholeTypeName = pigeonholeTypeName;
    this.pigeonholeFileName = pigeonholeFileName;
  }
  
  public ArchivesFilePO() {}
  
  public Long getFileId() {
    return this.fileId;
  }
  
  public void setFileId(Long fileId) {
    this.fileId = fileId;
  }
  
  public Long getDossierId() {
    return this.dossierId;
  }
  
  public void setDossierId(Long dossierId) {
    this.dossierId = dossierId;
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
  
  public String getFileNo() {
    return this.fileNo;
  }
  
  public void setFileNo(String fileNo) {
    this.fileNo = fileNo;
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
  
  public String getFileName() {
    return this.fileName;
  }
  
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  
  public String getFileKey() {
    return this.fileKey;
  }
  
  public void setFileKey(String fileKey) {
    this.fileKey = fileKey;
  }
  
  public Integer getPageCount() {
    return this.pageCount;
  }
  
  public void setPageCount(Integer pageCount) {
    this.pageCount = pageCount;
  }
  
  public Integer getCopyCount() {
    return this.copyCount;
  }
  
  public void setCopyCount(Integer copyCount) {
    this.copyCount = copyCount;
  }
  
  public Integer getSecretLevel() {
    return this.secretLevel;
  }
  
  public void setSecretLevel(Integer secretLevel) {
    this.secretLevel = secretLevel;
  }
  
  public Integer getSaveStyle() {
    return this.saveStyle;
  }
  
  public void setSaveStyle(Integer saveStyle) {
    this.saveStyle = saveStyle;
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
  
  public Integer getPigeonholeStatus() {
    return this.pigeonholeStatus;
  }
  
  public void setPigeonholeStatus(Integer pigeonholeStatus) {
    this.pigeonholeStatus = pigeonholeStatus;
  }
  
  public Integer getIsBorrow() {
    return this.isBorrow;
  }
  
  public void setIsBorrow(Integer isBorrow) {
    this.isBorrow = isBorrow;
  }
  
  public ArchivesDossierPO getArchivesDossier() {
    return this.archivesDossier;
  }
  
  public void setArchivesDossier(ArchivesDossierPO archivesDossier) {
    this.archivesDossier = archivesDossier;
  }
  
  public Integer getResidualCount() {
    return this.residualCount;
  }
  
  public void setResidualCount(Integer residualCount) {
    this.residualCount = residualCount;
  }
  
  public void setArchivesFileAccessory(Set archivesFileAccessory) {
    this.archivesFileAccessory = archivesFileAccessory;
  }
  
  public Set getArchivesFileAccessory() {
    return this.archivesFileAccessory;
  }
  
  public void setArchivesBorrow(Set archivesBorrow) {
    this.archivesBorrow = archivesBorrow;
  }
  
  public Set getArchivesBorrow() {
    return this.archivesBorrow;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("fileId", getFileId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ArchivesFilePO))
      return false; 
    ArchivesFilePO castOther = (ArchivesFilePO)other;
    return (new EqualsBuilder())
      .append(getFileId(), castOther.getFileId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getFileId())
      .toHashCode();
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
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
}
