package com.js.oa.jsflow.vo;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ModuleVO implements Serializable {
  private int id;
  
  private String name;
  
  private int formType;
  
  private boolean singlePackage;
  
  private boolean singleProc;
  
  private boolean changeProcType;
  
  private int procType;
  
  private boolean chanTranType;
  
  private int tranType;
  
  private boolean chanRemind;
  
  private String remind;
  
  private boolean chanActiType;
  
  private int actiType;
  
  private boolean packRight;
  
  private String packRightType;
  
  private boolean procRight;
  
  private String procRightType;
  
  private boolean chanNoWrite;
  
  private String noWrite;
  
  private int actiCommType;
  
  private boolean chanActiClass;
  
  private int actiClass;
  
  private String formClassName;
  
  private String newFormMethod;
  
  private String activityFormMethod;
  
  private String completeMethod;
  
  private String domainId;
  
  public int getId() {
    return this.id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public int getFormType() {
    return this.formType;
  }
  
  public void setFormType(int formType) {
    this.formType = formType;
  }
  
  public boolean isSinglePackage() {
    return this.singlePackage;
  }
  
  public void setSinglePackage(boolean singlePackage) {
    this.singlePackage = singlePackage;
  }
  
  public boolean isSingleProc() {
    return this.singleProc;
  }
  
  public void setSingleProc(boolean singleProc) {
    this.singleProc = singleProc;
  }
  
  public boolean isChangeProcType() {
    return this.changeProcType;
  }
  
  public void setChangeProcType(boolean changeProcType) {
    this.changeProcType = changeProcType;
  }
  
  public int getProcType() {
    return this.procType;
  }
  
  public void setProcType(int procType) {
    this.procType = procType;
  }
  
  public boolean isChanTranType() {
    return this.chanTranType;
  }
  
  public void setChanTranType(boolean chanTranType) {
    this.chanTranType = chanTranType;
  }
  
  public int getTranType() {
    return this.tranType;
  }
  
  public void setTranType(int tranType) {
    this.tranType = tranType;
  }
  
  public boolean isChanRemind() {
    return this.chanRemind;
  }
  
  public void setChanRemind(boolean chanRemind) {
    this.chanRemind = chanRemind;
  }
  
  public String getRemind() {
    return this.remind;
  }
  
  public void setRemind(String remind) {
    this.remind = remind;
  }
  
  public boolean isChanActiType() {
    return this.chanActiType;
  }
  
  public void setChanActiType(boolean chanActiType) {
    this.chanActiType = chanActiType;
  }
  
  public int getActiType() {
    return this.actiType;
  }
  
  public void setActiType(int actiType) {
    this.actiType = actiType;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ModuleVO))
      return false; 
    ModuleVO castOther = (ModuleVO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public boolean isPackRight() {
    return this.packRight;
  }
  
  public void setPackRight(boolean packRight) {
    this.packRight = packRight;
  }
  
  public String getPackRightType() {
    return this.packRightType;
  }
  
  public void setPackRightType(String packRightType) {
    this.packRightType = packRightType;
  }
  
  public boolean isProcRight() {
    return this.procRight;
  }
  
  public void setProcRight(boolean procRight) {
    this.procRight = procRight;
  }
  
  public String getProcRightType() {
    return this.procRightType;
  }
  
  public void setProcRightType(String procRightType) {
    this.procRightType = procRightType;
  }
  
  public boolean isChanNoWrite() {
    return this.chanNoWrite;
  }
  
  public void setChanNoWrite(boolean chanNoWrite) {
    this.chanNoWrite = chanNoWrite;
  }
  
  public String getNoWrite() {
    return this.noWrite;
  }
  
  public void setNoWrite(String noWrite) {
    this.noWrite = noWrite;
  }
  
  public int getActiCommType() {
    return this.actiCommType;
  }
  
  public void setActiCommType(int actiCommType) {
    this.actiCommType = actiCommType;
  }
  
  public boolean isChanActiClass() {
    return this.chanActiClass;
  }
  
  public void setChanActiClass(boolean chanActiClass) {
    this.chanActiClass = chanActiClass;
  }
  
  public int getActiClass() {
    return this.actiClass;
  }
  
  public void setActiClass(int actiClass) {
    this.actiClass = actiClass;
  }
  
  public String getFormClassName() {
    return this.formClassName;
  }
  
  public void setFormClassName(String formClassName) {
    this.formClassName = formClassName;
  }
  
  public String getNewFormMethod() {
    return this.newFormMethod;
  }
  
  public void setNewFormMethod(String newFormMethod) {
    this.newFormMethod = newFormMethod;
  }
  
  public String getActivityFormMethod() {
    return this.activityFormMethod;
  }
  
  public void setActivityFormMethod(String activityFormMethod) {
    this.activityFormMethod = activityFormMethod;
  }
  
  public String getCompleteMethod() {
    return this.completeMethod;
  }
  
  public void setCompleteMethod(String completeMethod) {
    this.completeMethod = completeMethod;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
