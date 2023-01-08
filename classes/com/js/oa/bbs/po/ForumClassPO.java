package com.js.oa.bbs.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ForumClassPO implements Serializable {
  private String classEmail;
  
  private long id;
  
  private String banPrint;
  
  private String className;
  
  private short classLevel;
  
  private String classOwnerName;
  
  private String classUserName;
  
  private String classUserId;
  
  private String classUserOrg;
  
  private String classUserGroup;
  
  private String classSort;
  
  private long classParent;
  
  private Date classDate = null;
  
  private long createdOrg;
  
  private long createdEmp;
  
  private String classRemark;
  
  private Set forum = null;
  
  private byte classHasJunior;
  
  private long classOwnerId;
  
  private int classSortCode;
  
  private String createdEmpName;
  
  private String classOwnerIds;
  
  private String classParentName;
  
  private String checkExamin;
  
  private Long domainId;
  
  private String startPeriod;
  
  private String endPeriod;
  
  private int fullDay;
  
  private String estopAnonymity;
  
  private Long relProjectId;
  
  private Long proClassId;
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getClassName() {
    return this.className;
  }
  
  public void setClassName(String className) {
    this.className = className;
  }
  
  public short getClassLevel() {
    return this.classLevel;
  }
  
  public void setClassLevel(short classLevel) {
    this.classLevel = classLevel;
  }
  
  public String getClassOwnerName() {
    return this.classOwnerName;
  }
  
  public void setClassOwnerName(String classOwnerName) {
    this.classOwnerName = classOwnerName;
  }
  
  public long getClassOwnerId() {
    return this.classOwnerId;
  }
  
  public void setClassOwnerId(long classOwnerId) {
    this.classOwnerId = classOwnerId;
  }
  
  public String getClassUserName() {
    return this.classUserName;
  }
  
  public void setClassUserName(String classUserName) {
    this.classUserName = classUserName;
  }
  
  public String getClassUserId() {
    return this.classUserId;
  }
  
  public void setClassUserId(String classUserId) {
    this.classUserId = classUserId;
  }
  
  public String getClassUserOrg() {
    return this.classUserOrg;
  }
  
  public void setClassUserOrg(String classUserOrg) {
    this.classUserOrg = classUserOrg;
  }
  
  public String getClassUserGroup() {
    return this.classUserGroup;
  }
  
  public void setClassUserGroup(String classUserGroup) {
    this.classUserGroup = classUserGroup;
  }
  
  public int getClassSortCode() {
    return this.classSortCode;
  }
  
  public void setClassSortCode(int classSortCode) {
    this.classSortCode = classSortCode;
  }
  
  public String getClassSort() {
    return this.classSort;
  }
  
  public void setClassSort(String classSort) {
    this.classSort = classSort;
  }
  
  public long getClassParent() {
    return this.classParent;
  }
  
  public void setClassParent(long classParent) {
    this.classParent = classParent;
  }
  
  public Date getClassDate() {
    return this.classDate;
  }
  
  public void setClassDate(Date classDate) {
    this.classDate = classDate;
  }
  
  public long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public String getClassRemark() {
    return this.classRemark;
  }
  
  public void setClassRemark(String classRemark) {
    this.classRemark = classRemark;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ForumClassPO))
      return false; 
    ForumClassPO castOther = (ForumClassPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public Set getForum() {
    return this.forum;
  }
  
  public void setForum(Set forum) {
    this.forum = forum;
  }
  
  public byte getClassHasJunior() {
    return this.classHasJunior;
  }
  
  public void setClassHasJunior(byte classHasJunior) {
    this.classHasJunior = classHasJunior;
  }
  
  public String getCreatedEmpName() {
    return this.createdEmpName;
  }
  
  public void setCreatedEmpName(String createdEmpName) {
    this.createdEmpName = createdEmpName;
  }
  
  public String getClassOwnerIds() {
    return this.classOwnerIds;
  }
  
  public void setClassOwnerIds(String classOwnerIds) {
    this.classOwnerIds = classOwnerIds;
  }
  
  public String getClassParentName() {
    return this.classParentName;
  }
  
  public void setClassParentName(String classParentName) {
    this.classParentName = classParentName;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public String getCheckExamin() {
    return this.checkExamin;
  }
  
  public String getEndPeriod() {
    return this.endPeriod;
  }
  
  public int getFullDay() {
    return this.fullDay;
  }
  
  public String getStartPeriod() {
    return this.startPeriod;
  }
  
  public String getBanPrint() {
    return this.banPrint;
  }
  
  public String getClassEmail() {
    return this.classEmail;
  }
  
  public String getEstopAnonymity() {
    return this.estopAnonymity;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public void setCheckExamin(String checkExamin) {
    this.checkExamin = checkExamin;
  }
  
  public void setEndPeriod(String endPeriod) {
    this.endPeriod = endPeriod;
  }
  
  public void setFullDay(int fullDay) {
    this.fullDay = fullDay;
  }
  
  public void setStartPeriod(String startPeriod) {
    this.startPeriod = startPeriod;
  }
  
  public void setBanPrint(String banPrint) {
    this.banPrint = banPrint;
  }
  
  public void setClassEmail(String classEmail) {
    this.classEmail = classEmail;
  }
  
  public void setEstopAnonymity(String estopAnonymity) {
    this.estopAnonymity = estopAnonymity;
  }
  
  public Long getRelProjectId() {
    return this.relProjectId;
  }
  
  public void setRelProjectId(Long relProjectId) {
    this.relProjectId = relProjectId;
  }
  
  public Long getProClassId() {
    return this.proClassId;
  }
  
  public void setProClassId(Long proClassId) {
    this.proClassId = proClassId;
  }
}
