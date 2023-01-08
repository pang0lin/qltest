package com.js.oa.jsflow.vo;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class AccessTableVO implements Serializable {
  private long id;
  
  private String displayName;
  
  private String realName;
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof AccessTableVO))
      return false; 
    AccessTableVO castOther = (AccessTableVO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public String getDisplayName() {
    return this.displayName;
  }
  
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }
  
  public String getRealName() {
    return this.realName;
  }
  
  public void setRealName(String realName) {
    this.realName = realName;
  }
}
