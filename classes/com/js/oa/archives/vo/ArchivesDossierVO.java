package com.js.oa.archives.vo;

import com.js.oa.archives.po.ArchivesDossierPO;
import java.io.Serializable;
import java.util.Date;

public class ArchivesDossierVO implements Serializable {
  private ArchivesDossierPO archivesDossierPO = null;
  
  private Long dossierId;
  
  private String generalNo;
  
  private String classNo;
  
  private String className;
  
  private String catalogNo;
  
  private String dossierNo;
  
  private String dossierName;
  
  private String dossierKey;
  
  private Integer saveStyle;
  
  private Date saveBeginTime = null;
  
  private Date saveEndTime = null;
  
  private Integer secretLevel;
  
  private Integer pageCount;
  
  private Integer copyCount;
  
  private Long principal;
  
  private String principalName;
  
  private Date pigeonholeTime = null;
  
  private Integer dossierStatus;
  
  private Boolean maintenance;
  
  private String classReader;
  
  private String classReadOrg;
  
  private String classReadGroup;
  
  private String classReadName;
  
  public ArchivesDossierVO(ArchivesDossierPO archivesDossierPO, String generalNo, String className, String classNo, String catalogNo, String dossierNo, String dossierName, String dossierKey, Integer saveStyle, Date saveBeginTime, Date saveEndTime, Integer secretLevel, Integer pageCount, Integer copyCount, Long principal, String principalName, Date pigeonholeTime, Integer dossierStatus, String classReader, String classReadOrg, String classReadGroup, String classReadName) {
    this.archivesDossierPO = archivesDossierPO;
    this.generalNo = generalNo;
    this.classNo = classNo;
    this.className = className;
    this.catalogNo = catalogNo;
    this.dossierNo = dossierNo;
    this.dossierName = dossierName;
    this.dossierKey = dossierKey;
    this.saveStyle = saveStyle;
    this.saveBeginTime = saveBeginTime;
    this.saveEndTime = saveEndTime;
    this.secretLevel = secretLevel;
    this.pageCount = pageCount;
    this.copyCount = copyCount;
    this.principal = principal;
    this.principalName = principalName;
    this.pigeonholeTime = pigeonholeTime;
    this.dossierStatus = dossierStatus;
    this.classReader = classReader;
    this.classReadOrg = classReadOrg;
    this.classReadGroup = classReadGroup;
    this.classReadName = classReadName;
  }
  
  public ArchivesDossierVO() {}
  
  public ArchivesDossierPO getArchivesDossierPO() {
    return this.archivesDossierPO;
  }
  
  public void setArchivesDossierPO(ArchivesDossierPO archivesDossierPO) {
    this.archivesDossierPO = archivesDossierPO;
  }
  
  public String getCatalogNo() {
    return this.catalogNo;
  }
  
  public String getClassNo() {
    return this.classNo;
  }
  
  public void setClassNo(String classNo) {
    this.classNo = classNo;
  }
  
  public String getClassName() {
    return this.className;
  }
  
  public void setClassName(String className) {
    this.className = className;
  }
  
  public Integer getCopyCount() {
    return this.copyCount;
  }
  
  public Long getDossierId() {
    return this.dossierId;
  }
  
  public String getDossierName() {
    return this.dossierName;
  }
  
  public String getDossierKey() {
    return this.dossierKey;
  }
  
  public void setCatalogNo(String catalogNo) {
    this.catalogNo = catalogNo;
  }
  
  public void setCopyCount(Integer copyCount) {
    this.copyCount = copyCount;
  }
  
  public void setDossierId(Long dossierId) {
    this.dossierId = dossierId;
  }
  
  public void setDossierKey(String dossierKey) {
    this.dossierKey = dossierKey;
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
  
  public void setDossierStatus(Integer dossierStatus) {
    this.dossierStatus = dossierStatus;
  }
  
  public Integer getDossierStatus() {
    return this.dossierStatus;
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
  
  public Date getPigeonholeTime() {
    return this.pigeonholeTime;
  }
  
  public void setPigeonholeTime(Date pigeonholeTime) {
    this.pigeonholeTime = pigeonholeTime;
  }
  
  public Long getPrincipal() {
    return this.principal;
  }
  
  public String getPrincipalName() {
    return this.principalName;
  }
  
  public void setPrincipal(Long principal) {
    this.principal = principal;
  }
  
  public void setPrincipalName(String principalName) {
    this.principalName = principalName;
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
  
  public Boolean getMaintenance() {
    return this.maintenance;
  }
  
  public void setMaintenance(Boolean maintenance) {
    this.maintenance = maintenance;
  }
  
  public ArchivesDossierVO conversionPO(ArchivesDossierPO archivesDossierPO) {
    this.dossierId = archivesDossierPO.getDossierId();
    this.generalNo = archivesDossierPO.getGeneralNo();
    this.classNo = archivesDossierPO.getClassNo();
    this.className = archivesDossierPO.getArchivesClass().getClassName();
    this.catalogNo = archivesDossierPO.getCatalogNo();
    this.dossierNo = archivesDossierPO.getDossierNo();
    this.dossierName = archivesDossierPO.getDossierName();
    this.dossierKey = archivesDossierPO.getDossierKey();
    this.saveStyle = archivesDossierPO.getSaveStyle();
    this.saveBeginTime = archivesDossierPO.getSaveBeginTime();
    this.saveEndTime = archivesDossierPO.getSaveEndTime();
    this.secretLevel = archivesDossierPO.getSecretLevel();
    this.pageCount = archivesDossierPO.getPageCount();
    this.copyCount = archivesDossierPO.getCopyCount();
    this.principal = archivesDossierPO.getPrincipal();
    this.principalName = archivesDossierPO.getPrincipalName();
    this.pigeonholeTime = archivesDossierPO.getPigeonholeTime();
    this.dossierStatus = archivesDossierPO.getDossierStatus();
    this.classReader = this.classReader;
    this.classReadOrg = this.classReadOrg;
    this.classReadGroup = this.classReadGroup;
    this.classReadName = this.classReadName;
    return this;
  }
  
  public String toString() {
    return "ArchivesDossierVO{archivesDossierPO=" + this.archivesDossierPO + "}";
  }
  
  public boolean equals(Object o) {
    if (this == o)
      return true; 
    if (!(o instanceof ArchivesDossierVO))
      return false; 
    ArchivesDossierVO archivesDossierVO = (ArchivesDossierVO)o;
    if ((this.archivesDossierPO != null) ? !this.archivesDossierPO.equals(archivesDossierVO.archivesDossierPO) : (archivesDossierVO.archivesDossierPO != null))
      return false; 
    if ((this.dossierId != null) ? !this.dossierId.equals(archivesDossierVO.dossierId) : (archivesDossierVO.dossierId != null))
      return false; 
    if ((this.generalNo != null) ? !this.generalNo.equals(archivesDossierVO.generalNo) : (archivesDossierVO.generalNo != null))
      return false; 
    if ((this.classNo != null) ? !this.classNo.equals(archivesDossierVO.classNo) : (archivesDossierVO.classNo != null))
      return false; 
    if ((this.className != null) ? !this.classNo.equals(archivesDossierVO.className) : (archivesDossierVO.className != null))
      return false; 
    if ((this.catalogNo != null) ? !this.catalogNo.equals(archivesDossierVO.catalogNo) : (archivesDossierVO.catalogNo != null))
      return false; 
    if ((this.dossierNo != null) ? !this.dossierNo.equals(archivesDossierVO.dossierNo) : (archivesDossierVO.dossierNo != null))
      return false; 
    if ((this.dossierName != null) ? !this.dossierName.equals(archivesDossierVO.dossierName) : (archivesDossierVO.dossierName != null))
      return false; 
    if ((this.dossierKey != null) ? !this.dossierKey.equals(archivesDossierVO.dossierKey) : (archivesDossierVO.dossierKey != null))
      return false; 
    if ((this.saveStyle != null) ? !this.saveStyle.equals(archivesDossierVO.saveStyle) : (archivesDossierVO.saveStyle != null))
      return false; 
    if ((this.saveBeginTime != null) ? !this.saveBeginTime.equals(archivesDossierVO.saveBeginTime) : (archivesDossierVO.saveBeginTime != null))
      return false; 
    if ((this.saveEndTime != null) ? !this.saveEndTime.equals(archivesDossierVO.saveEndTime) : (archivesDossierVO.saveEndTime != null))
      return false; 
    if ((this.secretLevel != null) ? !this.secretLevel.equals(archivesDossierVO.secretLevel) : (archivesDossierVO.secretLevel != null))
      return false; 
    if ((this.pageCount != null) ? !this.pageCount.equals(archivesDossierVO.pageCount) : (archivesDossierVO.pageCount != null))
      return false; 
    if ((this.copyCount != null) ? !this.copyCount.equals(archivesDossierVO.copyCount) : (archivesDossierVO.copyCount != null))
      return false; 
    if ((this.principal != null) ? !this.principal.equals(archivesDossierVO.principal) : (archivesDossierVO.principal != null))
      return false; 
    if ((this.principalName != null) ? !this.principalName.equals(archivesDossierVO.principalName) : (archivesDossierVO.principalName != null))
      return false; 
    if ((this.pigeonholeTime != null) ? !this.pigeonholeTime.equals(archivesDossierVO.pigeonholeTime) : (archivesDossierVO.pigeonholeTime != null))
      return false; 
    if ((this.dossierStatus != null) ? !this.dossierStatus.equals(archivesDossierVO.dossierStatus) : (archivesDossierVO.dossierStatus != null))
      return false; 
    if ((this.classReader != null) ? !this.classReader.equals(archivesDossierVO.classReader) : (archivesDossierVO.classReader != null))
      return false; 
    if ((this.classReadOrg != null) ? !this.classReadOrg.equals(archivesDossierVO.classReadOrg) : (archivesDossierVO.classReadOrg != null))
      return false; 
    if ((this.classReadGroup != null) ? !this.classReadGroup.equals(archivesDossierVO.classReadGroup) : (archivesDossierVO.classReadGroup != null))
      return false; 
    if ((this.classReadName != null) ? !this.classReadName.equals(archivesDossierVO.classReadName) : (archivesDossierVO.classReadName != null))
      return false; 
    return true;
  }
  
  public int hashCode() {
    int result = this.dossierId.hashCode();
    return result;
  }
  
  public void setClassReader(String classReader) {
    this.classReader = classReader;
  }
  
  public String getClassReader() {
    return this.classReader;
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
}
