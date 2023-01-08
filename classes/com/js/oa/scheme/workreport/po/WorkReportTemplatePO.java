package com.js.oa.scheme.workreport.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class WorkReportTemplatePO implements Serializable {
  private long id;
  
  private long templateDomainId;
  
  private String templateName;
  
  private String templateDescript;
  
  private long createdEmp;
  
  private long createdOrg;
  
  private String templateUseEmp;
  
  private String templateUseOrg;
  
  private String templateUseGroup;
  
  private String createdEmpName;
  
  private String templateUseRange;
  
  private String content;
  
  private String templateContent;
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public long getTemplateDomainId() {
    return this.templateDomainId;
  }
  
  public void setTemplateDomainId(long templateDomainId) {
    this.templateDomainId = templateDomainId;
  }
  
  public String getTemplateName() {
    return this.templateName;
  }
  
  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }
  
  public String getTemplateContent() {
    return this.templateContent;
  }
  
  public void setTemplateContent(String templateContent) {
    this.templateContent = templateContent;
  }
  
  public String getTemplateDescript() {
    return this.templateDescript;
  }
  
  public void setTemplateDescript(String templateDescript) {
    this.templateDescript = templateDescript;
  }
  
  public long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public String getTemplateUseEmp() {
    return this.templateUseEmp;
  }
  
  public void setTemplateUseEmp(String templateUseEmp) {
    this.templateUseEmp = templateUseEmp;
  }
  
  public String getTemplateUseOrg() {
    return this.templateUseOrg;
  }
  
  public void setTemplateUseOrg(String templateUseOrg) {
    this.templateUseOrg = templateUseOrg;
  }
  
  public String getTemplateUseGroup() {
    return this.templateUseGroup;
  }
  
  public void setTemplateUseGroup(String templateUseGroup) {
    this.templateUseGroup = templateUseGroup;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof WorkReportTemplatePO))
      return false; 
    WorkReportTemplatePO castOther = (WorkReportTemplatePO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public String getCreatedEmpName() {
    return this.createdEmpName;
  }
  
  public void setCreatedEmpName(String createdEmpName) {
    this.createdEmpName = createdEmpName;
  }
  
  public String getTemplateUseRange() {
    return this.templateUseRange;
  }
  
  public void setTemplateUseRange(String templateUseRange) {
    this.templateUseRange = templateUseRange;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
}
