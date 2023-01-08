package com.js.oa.jsflow.vo;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class SimpleFieldVO implements Serializable {
  private long id;
  
  private String realName;
  
  private String displayName;
  
  private boolean remind;
  
  private String poField;
  
  private boolean canModify;
  
  private boolean canIdea;
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getRealName() {
    return this.realName;
  }
  
  public void setRealName(String realName) {
    this.realName = realName;
  }
  
  public String getDisplayName() {
    return this.displayName;
  }
  
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }
  
  public boolean isRemind() {
    return this.remind;
  }
  
  public void setRemind(boolean remind) {
    this.remind = remind;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof SimpleFieldVO))
      return false; 
    SimpleFieldVO castOther = (SimpleFieldVO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public String getPoField() {
    return this.poField;
  }
  
  public void setPoField(String poField) {
    this.poField = poField;
  }
  
  public boolean isCanModify() {
    return this.canModify;
  }
  
  public void setCanModify(boolean canModify) {
    this.canModify = canModify;
  }
  
  public boolean isCanIdea() {
    return this.canIdea;
  }
  
  public void setCanIdea(boolean canIdea) {
    this.canIdea = canIdea;
  }
}
