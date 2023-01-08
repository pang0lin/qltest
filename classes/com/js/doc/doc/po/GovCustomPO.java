package com.js.doc.doc.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class GovCustomPO implements Serializable {
  private Long id;
  
  private String govFormName;
  
  private String govFormContent;
  
  private Long govFormId;
  
  private Long domainId;
  
  private String govPrintFormContent;
  
  private String govCheckField;
  
  private int govFormType;
  
  private Long createOrg;
  
  private Long createEmp;
  
  private String mustword;
  
  public boolean equals(Object other) {
    if (!(other instanceof GovCustomPO))
      return false; 
    GovCustomPO castOther = (GovCustomPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public String getMustword() {
    return this.mustword;
  }
  
  public void setMustword(String mustword) {
    this.mustword = mustword;
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public Long getId() {
    return this.id;
  }
  
  public String getGovFormName() {
    return this.govFormName;
  }
  
  public Long getGovFormId() {
    return this.govFormId;
  }
  
  public String getGovFormContent() {
    return this.govFormContent;
  }
  
  public void setDomainId(Long domainId) {
    this.domainId = domainId;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setGovFormName(String govFormName) {
    this.govFormName = govFormName;
  }
  
  public void setGovFormId(Long govFormId) {
    this.govFormId = govFormId;
  }
  
  public void setGovFormContent(String govFormContent) {
    this.govFormContent = govFormContent;
  }
  
  public void setGovPrintFormContent(String govPrintFormContent) {
    this.govPrintFormContent = govPrintFormContent;
  }
  
  public void setGovFormType(int govFormType) {
    this.govFormType = govFormType;
  }
  
  public void setGovCheckField(String govCheckField) {
    this.govCheckField = govCheckField;
  }
  
  public void setCreateOrg(Long createOrg) {
    this.createOrg = createOrg;
  }
  
  public void setCreateEmp(Long createEmp) {
    this.createEmp = createEmp;
  }
  
  public Long getDomainId() {
    return this.domainId;
  }
  
  public String getGovPrintFormContent() {
    return this.govPrintFormContent;
  }
  
  public int getGovFormType() {
    return this.govFormType;
  }
  
  public String getGovCheckField() {
    return this.govCheckField;
  }
  
  public Long getCreateOrg() {
    return this.createOrg;
  }
  
  public Long getCreateEmp() {
    return this.createEmp;
  }
}
