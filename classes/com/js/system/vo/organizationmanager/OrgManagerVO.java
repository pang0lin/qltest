package com.js.system.vo.organizationmanager;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class OrgManagerVO implements Serializable {
  private Long id;
  
  private String name;
  
  private String password;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("Id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof OrgManagerVO))
      return false; 
    OrgManagerVO castOther = (OrgManagerVO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getPassword() {
    return this.password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
}
