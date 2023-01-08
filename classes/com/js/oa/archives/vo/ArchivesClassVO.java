package com.js.oa.archives.vo;

import com.js.oa.archives.po.ArchivesClassPO;
import java.io.Serializable;

public class ArchivesClassVO implements Serializable {
  private Long classId;
  
  private String className;
  
  private Long classParentId;
  
  private Integer classLevel;
  
  private Integer classOrderCode;
  
  private String classIdString;
  
  private String classReader;
  
  private String classReadOrg;
  
  private String classReadGroup;
  
  private String classReadName;
  
  private Long createdOrg;
  
  private Long createdEmp;
  
  private Boolean maintenance;
  
  private Boolean see;
  
  public ArchivesClassVO() {}
  
  public Long getClassId() {
    return this.classId;
  }
  
  public void setClassId(Long classId) {
    this.classId = classId;
  }
  
  public String getClassName() {
    return this.className;
  }
  
  public void setClassName(String className) {
    this.className = className;
  }
  
  public Long getClassParentId() {
    return this.classParentId;
  }
  
  public void setClassParentId(Long classParentId) {
    this.classParentId = classParentId;
  }
  
  public Integer getClassLevel() {
    return this.classLevel;
  }
  
  public void setClassLevel(Integer classLevel) {
    this.classLevel = classLevel;
  }
  
  public Integer getClassOrderCode() {
    return this.classOrderCode;
  }
  
  public void setClassOrderCode(Integer classOrderCode) {
    this.classOrderCode = classOrderCode;
  }
  
  public String getClassIdString() {
    return this.classIdString;
  }
  
  public void setClassIdString(String classIdString) {
    this.classIdString = classIdString;
  }
  
  public String getClassReader() {
    return this.classReader;
  }
  
  public void setClassReader(String classReader) {
    this.classReader = classReader;
  }
  
  public String getClassReadOrg() {
    return this.classReadOrg;
  }
  
  public void setClassReadOrg(String classReadOrg) {
    this.classReadOrg = classReadOrg;
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
  
  public ArchivesClassVO(Long classId, String className, Long classParentId, Integer classLevel, Integer classOrderCode, String classIdString, String classReader, String classReadOrg, String classReadGroup, String classReadName, Long createdOrg, Long createdEmp) {
    this.classId = classId;
    this.className = className;
    this.classParentId = classParentId;
    this.classLevel = classLevel;
    this.classOrderCode = classOrderCode;
    this.classIdString = classIdString;
    this.classReader = classReader;
    this.classReadOrg = classReadOrg;
    this.classReadGroup = classReadGroup;
    this.classReadName = classReadName;
    this.createdOrg = createdOrg;
    this.createdEmp = createdEmp;
  }
  
  public boolean equals(Object o) {
    if (this == o)
      return true; 
    if (!(o instanceof ArchivesClassVO))
      return false; 
    ArchivesClassVO archivesClassVO = (ArchivesClassVO)o;
    if (!this.classId.equals(archivesClassVO.classId))
      return false; 
    if (!this.className.equals(archivesClassVO.className))
      return false; 
    if (!this.classParentId.equals(archivesClassVO.classParentId))
      return false; 
    if (!this.classLevel.equals(archivesClassVO.classLevel))
      return false; 
    if (!this.classOrderCode.equals(archivesClassVO.classOrderCode))
      return false; 
    if (!this.classIdString.equals(archivesClassVO.classIdString))
      return false; 
    if (!this.classReader.equals(archivesClassVO.classReader))
      return false; 
    if (!this.classReadOrg.equals(archivesClassVO.classReadOrg))
      return false; 
    if (!this.classReadGroup.equals(archivesClassVO.classReadGroup))
      return false; 
    if (!this.classReadName.equals(archivesClassVO.classReadName))
      return false; 
    if ((this.createdEmp != null) ? 
      !this.createdEmp.equals(archivesClassVO.createdEmp) : (
      archivesClassVO.createdEmp != null))
      return false; 
    if ((this.createdOrg != null) ? 
      !this.createdOrg.equals(archivesClassVO.createdOrg) : (
      archivesClassVO.createdOrg != null))
      return false; 
    if ((this.maintenance != null) ? 
      !this.maintenance.equals(archivesClassVO.maintenance) : (
      archivesClassVO.maintenance != null))
      return false; 
    if ((this.see != null) ? 
      !this.see.equals(archivesClassVO.see) : (
      archivesClassVO.see != null))
      return false; 
    return true;
  }
  
  public int hashCode() {
    int result = this.classId.hashCode();
    return result;
  }
  
  public ArchivesClassVO conversionPO(ArchivesClassPO archivesClassPO) {
    this.classId = archivesClassPO.getClassId();
    this.className = archivesClassPO.getClassName();
    this.classParentId = archivesClassPO.getClassParentId();
    this.classLevel = archivesClassPO.getClassLevel();
    this.classOrderCode = archivesClassPO.getClassOrderCode();
    this.classIdString = archivesClassPO.getClassIdString();
    this.classReader = archivesClassPO.getClassReader();
    this.classReadGroup = archivesClassPO.getClassReadGroup();
    this.classReadOrg = archivesClassPO.getClassReadOrg();
    this.classReadName = archivesClassPO.getClassReadName();
    this.createdEmp = archivesClassPO.getCreatedEmp();
    this.createdOrg = archivesClassPO.getCreatedOrg();
    return this;
  }
  
  public Boolean getMaintenance() {
    return this.maintenance;
  }
  
  public void setMaintenance(Boolean maintenance) {
    this.maintenance = maintenance;
  }
  
  public Boolean getSee() {
    return this.see;
  }
  
  public void setSee(Boolean see) {
    this.see = see;
  }
}
