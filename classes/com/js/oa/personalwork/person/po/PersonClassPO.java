package com.js.oa.personalwork.person.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PersonClassPO implements Serializable {
  private long id;
  
  private long empId;
  
  private String className;
  
  private String classDescribe;
  
  private Set linkmen = null;
  
  private byte classType;
  
  private long createdOrg;
  
  private String domainId;
  
  public PersonClassPO(Set linkmen, long empId, String classname, String classdescribe) {
    this.empId = empId;
    this.linkmen = linkmen;
  }
  
  public PersonClassPO() {}
  
  public PersonClassPO(long empId, String classname) {
    this.empId = empId;
    this.className = classname;
  }
  
  public long getEmpId() {
    return this.empId;
  }
  
  public void setEmpId(long empId) {
    this.empId = empId;
  }
  
  public String getClassName() {
    return this.className;
  }
  
  public void setClassName(String className) {
    this.className = className;
  }
  
  public String getClassDescribe() {
    return this.classDescribe;
  }
  
  public void setClassDescribe(String classDescribe) {
    this.classDescribe = classDescribe;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("Id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof PersonClassPO))
      return false; 
    PersonClassPO castOther = (PersonClassPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public Set getLinkmen() {
    return this.linkmen;
  }
  
  public void setLinkmen(Set linkmen) {
    this.linkmen = linkmen;
  }
  
  public byte getClassType() {
    return this.classType;
  }
  
  public void setClassType(byte classType) {
    this.classType = classType;
  }
  
  public long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
