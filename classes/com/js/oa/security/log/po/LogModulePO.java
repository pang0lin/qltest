package com.js.oa.security.log.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class LogModulePO implements Serializable {
  private Long moduleId;
  
  private String moduleSerial;
  
  private String moduleName;
  
  private String moduleLog;
  
  private String domainId;
  
  private String moduleLevel;
  
  private String parentSerial;
  
  public LogModulePO(Long moduleId, String moduleSerial, String moduleName, String moduleLog, String domainId) {
    this.moduleId = moduleId;
    this.moduleSerial = moduleSerial;
    this.moduleName = moduleName;
    this.moduleLog = moduleLog;
    this.domainId = domainId;
  }
  
  public LogModulePO(Long moduleId, String doaminId) {
    this.moduleId = moduleId;
    this.domainId = doaminId;
  }
  
  public LogModulePO() {}
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getModuleId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof LogModulePO))
      return false; 
    LogModulePO castOther = (LogModulePO)other;
    return (new EqualsBuilder())
      .append(getModuleId(), castOther.getModuleId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getModuleId())
      .toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public Long getModuleId() {
    return this.moduleId;
  }
  
  public String getModuleLog() {
    return this.moduleLog;
  }
  
  public String getModuleName() {
    return this.moduleName;
  }
  
  public String getModuleSerial() {
    return this.moduleSerial;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public void setModuleId(Long moduleId) {
    this.moduleId = moduleId;
  }
  
  public void setModuleLog(String moduleLog) {
    this.moduleLog = moduleLog;
  }
  
  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }
  
  public void setModuleSerial(String moduleSerial) {
    this.moduleSerial = moduleSerial;
  }
  
  public String getModuleLevel() {
    return this.moduleLevel;
  }
  
  public void setModuleLevel(String moduleLevel) {
    this.moduleLevel = moduleLevel;
  }
  
  public String getParentSerial() {
    return this.parentSerial;
  }
  
  public void setParentSerial(String parentSerial) {
    this.parentSerial = parentSerial;
  }
}
