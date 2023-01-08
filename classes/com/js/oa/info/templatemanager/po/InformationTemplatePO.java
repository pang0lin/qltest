package com.js.oa.info.templatemanager.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class InformationTemplatePO implements Serializable {
  private long id;
  
  private String templateTitle;
  
  private Serializable templateContent = null;
  
  private String templateRemark;
  
  private long createdOrg;
  
  private long createdEmp;
  
  private String useRangeName;
  
  private String useEmp;
  
  private String useOrg;
  
  private String useGroup;
  
  private String domainId;
  
  private String module;
  
  private String ispublic;
  
  public InformationTemplatePO(String templatetitle, Serializable templatecontent, String templateremark, long createdorg, long createdemp, String userangename, String useemp, String useorg, String usegroup) {
    this.templateTitle = templatetitle;
    this.templateContent = templatecontent;
    this.templateRemark = templateremark;
    this.createdOrg = createdorg;
    this.createdEmp = createdemp;
    this.useRangeName = userangename;
    this.useEmp = useemp;
    this.useOrg = useorg;
    this.useGroup = usegroup;
  }
  
  public InformationTemplatePO() {}
  
  public long getId() {
    return this.id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getTemplateTitle() {
    return this.templateTitle;
  }
  
  public void setTemplateTitle(String templateTitle) {
    this.templateTitle = templateTitle;
  }
  
  public Serializable getTemplateContent() {
    return this.templateContent;
  }
  
  public void setTemplateContent(Serializable templateContent) {
    this.templateContent = templateContent;
  }
  
  public String getTemplateRemark() {
    return this.templateRemark;
  }
  
  public void setTemplateRemark(String templateRemark) {
    this.templateRemark = templateRemark;
  }
  
  public long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(long createdOrg) {
    this.createdOrg = createdOrg;
  }
  
  public long getCreatedEmp() {
    return this.createdEmp;
  }
  
  public void setCreatedEmp(long createdEmp) {
    this.createdEmp = createdEmp;
  }
  
  public String getUseRangeName() {
    return this.useRangeName;
  }
  
  public void setUseRangeName(String useRangeName) {
    this.useRangeName = useRangeName;
  }
  
  public String getUseEmp() {
    return this.useEmp;
  }
  
  public void setUseEmp(String useEmp) {
    this.useEmp = useEmp;
  }
  
  public String getUseOrg() {
    return this.useOrg;
  }
  
  public void setUseOrg(String useOrg) {
    this.useOrg = useOrg;
  }
  
  public String getUseGroup() {
    return this.useGroup;
  }
  
  public void setUseGroup(String useGroup) {
    this.useGroup = useGroup;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof InformationTemplatePO))
      return false; 
    InformationTemplatePO castOther = (InformationTemplatePO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public String getModule() {
    return this.module;
  }
  
  public void setModule(String module) {
    this.module = module;
  }
  
  public String getIspublic() {
    return this.ispublic;
  }
  
  public void setIspublic(String ispublic) {
    this.ispublic = ispublic;
  }
}
