package com.js.cooperate.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TemplatePO implements Serializable {
  private Long id;
  
  private String title;
  
  private String content;
  
  private String type;
  
  private String useEmpId;
  
  private String useOrgId;
  
  private String useGroupId;
  
  private String useRange;
  
  private Long createdEmp;
  
  private Long createdOrg;
  
  public TemplatePO(String title, Long createdEmp, Long createdOrg) {
    this.title = title;
    this.createdEmp = createdEmp;
    this.createdOrg = createdOrg;
  }
  
  public TemplatePO() {}
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(Long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public Long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(Long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public String getType() {
    return this.type;
  }
  
  public void setType(String type) {
    this.type = type;
  }
  
  public String getUseEmpId() {
    return this.useEmpId;
  }
  
  public void setUseEmpId(String useEmpId) {
    this.useEmpId = useEmpId;
  }
  
  public String getUseOrgId() {
    return this.useOrgId;
  }
  
  public void setUseOrgId(String useOrgId) {
    this.useOrgId = useOrgId;
  }
  
  public String getUseGroupId() {
    return this.useGroupId;
  }
  
  public void setUseGroupId(String useGroupId) {
    this.useGroupId = useGroupId;
  }
  
  public String getUseRange() {
    return this.useRange;
  }
  
  public void setUseRange(String useRange) {
    this.useRange = useRange;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
}
