package com.js.doc.doc.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GovFileReadAccePO implements Serializable {
  private Long id;
  
  private String displayName;
  
  private String saveName;
  
  private GovFileReadPO fileRead;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getDisplayName() {
    return this.displayName;
  }
  
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }
  
  public String getSaveName() {
    return this.saveName;
  }
  
  public void setSaveName(String saveName) {
    this.saveName = saveName;
  }
  
  public GovFileReadPO getFileRead() {
    return this.fileRead;
  }
  
  public void setFileRead(GovFileReadPO fileRead) {
    this.fileRead = fileRead;
  }
  
  public String toString() {
    return (new ToStringBuilder(this)).append("id", getId()).toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof GovFileReadPO))
      return false; 
    GovFileReadPO castOther = (GovFileReadPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
}
