package com.js.oa.personalwork.setup.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class SoundRemindPO implements Serializable {
  private long id;
  
  private long empId;
  
  private byte remindType;
  
  private SoundSetPO soundSet;
  
  private String domainId;
  
  public SoundRemindPO(long empId, byte remindtype, long soundId) {
    this.empId = empId;
    this.remindType = remindtype;
  }
  
  public SoundRemindPO() {}
  
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
  
  public byte getRemindType() {
    return this.remindType;
  }
  
  public void setRemindType(byte remindType) {
    this.remindType = remindType;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof SoundRemindPO))
      return false; 
    SoundRemindPO castOther = (SoundRemindPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public SoundSetPO getSoundSet() {
    return this.soundSet;
  }
  
  public void setSoundSet(SoundSetPO soundSet) {
    this.soundSet = soundSet;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
