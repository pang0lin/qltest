package com.js.oa.jsflow.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class TShowPO implements Serializable {
  private int showId;
  
  private String showName;
  
  private String showFlag;
  
  private Set field = null;
  
  public int getShowId() {
    return this.showId;
  }
  
  public void setShowId(int showId) {
    this.showId = showId;
  }
  
  public String getShowName() {
    return this.showName;
  }
  
  public void setShowName(String showName) {
    this.showName = showName;
  }
  
  public String getShowFlag() {
    return this.showFlag;
  }
  
  public void setShowFlag(String showFlag) {
    this.showFlag = showFlag;
  }
  
  public Set getField() {
    return this.field;
  }
  
  public void setField(Set field) {
    this.field = field;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof TShowPO))
      return false; 
    TShowPO castOther = (TShowPO)other;
    return (new EqualsBuilder()).append(getShowId(), castOther.getShowId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getShowId()).toHashCode();
  }
}
