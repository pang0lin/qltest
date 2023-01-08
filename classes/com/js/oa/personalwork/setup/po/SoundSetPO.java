package com.js.oa.personalwork.setup.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class SoundSetPO implements Serializable {
  private long id;
  
  private long empId;
  
  private String soundFileName;
  
  private String soundSaveFile;
  
  private Set soundRemind = null;
  
  private String soundName;
  
  private String domainId;
  
  public SoundSetPO(long empId, String soundfilename, String soundsavefile) {
    this.empId = empId;
    this.soundFileName = soundfilename;
    this.soundSaveFile = soundsavefile;
  }
  
  public SoundSetPO() {}
  
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
  
  public String getSoundFileName() {
    return this.soundFileName;
  }
  
  public void setSoundFileName(String soundFileName) {
    this.soundFileName = soundFileName;
  }
  
  public String getSoundSaveFile() {
    return this.soundSaveFile;
  }
  
  public void setSoundSaveFile(String soundSaveFile) {
    this.soundSaveFile = soundSaveFile;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof SoundSetPO))
      return false; 
    SoundSetPO castOther = (SoundSetPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public Set getSoundRemind() {
    return this.soundRemind;
  }
  
  public void setSoundRemind(Set soundRemind) {
    this.soundRemind = soundRemind;
  }
  
  public String getSoundName() {
    return this.soundName;
  }
  
  public void setSoundName(String soundName) {
    this.soundName = soundName;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
