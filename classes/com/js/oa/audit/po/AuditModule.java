package com.js.oa.audit.po;

import java.io.Serializable;

public class AuditModule implements Serializable {
  private Long moduleId;
  
  private String moduleName;
  
  private String url;
  
  public String getUrl() {
    return this.url;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
  
  public AuditModule() {}
  
  public AuditModule(String moduleName) {
    this.moduleName = moduleName;
  }
  
  public Long getModuleId() {
    return this.moduleId;
  }
  
  public void setModuleId(Long moduleId) {
    this.moduleId = moduleId;
  }
  
  public String getModuleName() {
    return this.moduleName;
  }
  
  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }
}
