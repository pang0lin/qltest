package com.js.oa.archives.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ArchivesClassPO implements Serializable {
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
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  private Set archivesDossier = null;
  
  private String domainId;
  
  public ArchivesClassPO(String className, Long classParentId, Integer classLevel, Integer classOrderCode, String classIdString, String classReader, String classReadOrg, String classReadGroup, String classReadName, Long createdEmp, Long createdOrg, Set archivesDossier) {
    this.className = className;
    this.classParentId = classParentId;
    this.classLevel = classLevel;
    this.classOrderCode = classOrderCode;
    this.classIdString = classIdString;
    this.classReader = classReader;
    this.classReadOrg = classReadOrg;
    this.classReadGroup = classReadGroup;
    this.classReadName = classReadName;
    this.createdEmp = createdEmp;
    this.createdOrg = createdOrg;
    this.archivesDossier = archivesDossier;
  }
  
  public ArchivesClassPO() {}
  
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
  
  public void setclassReader(String classReader) {
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
  
  public Set getArchivesDossier() {
    return this.archivesDossier;
  }
  
  public void setArchivesDossier(Set archivesDossier) {
    this.archivesDossier = archivesDossier;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("classId", getClassId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ArchivesClassPO))
      return false; 
    ArchivesClassPO castOther = (ArchivesClassPO)other;
    return (new EqualsBuilder())
      .append(getClassId(), castOther.getClassId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getClassId())
      .toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
