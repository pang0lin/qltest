package com.js.oa.archives.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ArchivesDossierPO implements Serializable {
  private Long dossierId;
  
  private String generalNo;
  
  private String classNo;
  
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
  
  private String classReader;
  
  private String classReadOrg;
  
  private String classReadGroup;
  
  private String classReadName;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private Integer dossierStatus;
  
  private ArchivesClassPO archivesClass;
  
  private Set archivesFile = null;
  
  private Set archivesDossierAccessory = null;
  
  public ArchivesDossierPO(ArchivesClassPO archivesClass, String generalNo, String classNo, String catalogNo, String dossierNo, String dossierName, String dossierKey, Integer saveStyle, Date saveBeginTime, Date saveEndTime, Integer secretLevel, Integer pageCount, Integer copyCount, Long principal, String principalName, Date pigeonholeTime, String classReader, String classReadOrg, String classReadGroup, String classReadName, Long createdEmp, Long createdOrg, Integer dossierStatus, Set archivesFile, Set archivesDossierAccessory) {
    this.archivesClass = archivesClass;
    this.generalNo = generalNo;
    this.classNo = classNo;
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
    this.createdEmp = createdEmp;
    this.createdOrg = createdOrg;
    this.archivesFile = archivesFile;
    this.archivesDossierAccessory = archivesDossierAccessory;
  }
  
  public ArchivesDossierPO() {}
  
  public String getCatalogNo() {
    return this.catalogNo;
  }
  
  public String getClassNo() {
    return this.classNo;
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
  
  public void setClassNo(String classNo) {
    this.classNo = classNo;
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
  
  public ArchivesClassPO getArchivesClass() {
    return this.archivesClass;
  }
  
  public void setArchivesClass(ArchivesClassPO archivesClass) {
    this.archivesClass = archivesClass;
  }
  
  public Set getArchivesFile() {
    return this.archivesFile;
  }
  
  public void setArchivesFile(Set archivesFile) {
    this.archivesFile = archivesFile;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("dossierId", getDossierId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ArchivesDossierPO))
      return false; 
    ArchivesDossierPO castOther = (ArchivesDossierPO)other;
    return (new EqualsBuilder())
      .append(getDossierId(), castOther.getDossierId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getDossierId())
      .toHashCode();
  }
  
  public Set getArchivesDossierAccessory() {
    return this.archivesDossierAccessory;
  }
  
  public void setArchivesDossierAccessory(Set archivesDossierAccessory) {
    this.archivesDossierAccessory = archivesDossierAccessory;
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
}
