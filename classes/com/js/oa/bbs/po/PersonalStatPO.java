package com.js.oa.bbs.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PersonalStatPO implements Serializable {
  private long id;
  
  private long empId;
  
  private int forumNum;
  
  private Long domainId;
  
  public PersonalStatPO(long empId, int forumnum) {
    this.empId = empId;
    this.forumNum = forumnum;
  }
  
  public PersonalStatPO() {}
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(long empId) {
    this.empId = empId;
  }
  
  public int getForumNum() {
    return this.forumNum;
  }
  
  public void setForumNum(int forumNum) {
    this.forumNum = forumNum;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof PersonalStatPO))
      return false; 
    PersonalStatPO castOther = (PersonalStatPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
}
