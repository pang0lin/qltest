package com.js.doc.doc.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class BaseInfoPO implements Serializable {
  private String contentLevel;
  
  private String fileType;
  
  private Long id;
  
  private String keepSecretLevel;
  
  private String secretLevel;
  
  private String transactLevel;
  
  private String unitWord;
  
  private String baseUnitClass;
  
  private String topicalAreaClass;
  
  private String baseSorttopical;
  
  private String baseQueryLevel;
  
  private String openProperty;
  
  private String sendDropDownSelect1;
  
  private String sendDropDownSelect2;
  
  public boolean equals(Object other) {
    if (!(other instanceof BaseInfoPO))
      return false; 
    BaseInfoPO castOther = (BaseInfoPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public String getContentLevel() {
    return this.contentLevel;
  }
  
  public String getFileType() {
    return this.fileType;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public String getKeepSecretLevel() {
    return this.keepSecretLevel;
  }
  
  public String getSecretLevel() {
    return this.secretLevel;
  }
  
  public String getTransactLevel() {
    return this.transactLevel;
  }
  
  public String getUnitWord() {
    return this.unitWord;
  }
  
  public String getBaseSorttopical() {
    return this.baseSorttopical;
  }
  
  public String getTopicalAreaClass() {
    return this.topicalAreaClass;
  }
  
  public String getBaseUnitClass() {
    return this.baseUnitClass;
  }
  
  public String getBaseQueryLevel() {
    return this.baseQueryLevel;
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public void setContentLevel(String contentLevel) {
    this.contentLevel = contentLevel;
  }
  
  public void setFileType(String fileType) {
    this.fileType = fileType;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setKeepSecretLevel(String keepSecretLevel) {
    this.keepSecretLevel = keepSecretLevel;
  }
  
  public void setSecretLevel(String secretLevel) {
    this.secretLevel = secretLevel;
  }
  
  public void setTransactLevel(String transactLevel) {
    this.transactLevel = transactLevel;
  }
  
  public void setUnitWord(String unitWord) {
    this.unitWord = unitWord;
  }
  
  public void setBaseSorttopical(String baseSorttopical) {
    this.baseSorttopical = baseSorttopical;
  }
  
  public void setTopicalAreaClass(String topicalAreaClass) {
    this.topicalAreaClass = topicalAreaClass;
  }
  
  public void setBaseUnitClass(String baseUnitClass) {
    this.baseUnitClass = baseUnitClass;
  }
  
  public void setBaseQueryLevel(String baseQueryLevel) {
    this.baseQueryLevel = baseQueryLevel;
  }
  
  public String getOpenProperty() {
    return this.openProperty;
  }
  
  public void setOpenProperty(String openProperty) {
    this.openProperty = openProperty;
  }
  
  public String getSendDropDownSelect1() {
    return this.sendDropDownSelect1;
  }
  
  public void setSendDropDownSelect1(String sendDropDownSelect1) {
    this.sendDropDownSelect1 = sendDropDownSelect1;
  }
  
  public String getSendDropDownSelect2() {
    return this.sendDropDownSelect2;
  }
  
  public void setSendDropDownSelect2(String sendDropDownSelect2) {
    this.sendDropDownSelect2 = sendDropDownSelect2;
  }
}
