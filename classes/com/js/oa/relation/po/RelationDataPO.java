package com.js.oa.relation.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class RelationDataPO implements Serializable {
  private Long dataId;
  
  private String moduleType;
  
  private String moduleSubId;
  
  private String infoId;
  
  private String relationObjectType;
  
  private String relationInfoName;
  
  private String relationSubId;
  
  private String relationInfoId;
  
  private String relationInfoHref;
  
  private String domainId;
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getDataId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof RelationDataPO))
      return false; 
    RelationDataPO castOther = (RelationDataPO)other;
    return (new EqualsBuilder())
      .append(getDataId(), castOther.getDataId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getDataId())
      .toHashCode();
  }
  
  public Long getDataId() {
    return this.dataId;
  }
  
  public void setDataId(Long dataId) {
    this.dataId = dataId;
  }
  
  public String getModuleType() {
    return this.moduleType;
  }
  
  public void setModuleType(String moduleType) {
    this.moduleType = moduleType;
  }
  
  public String getModuleSubId() {
    return this.moduleSubId;
  }
  
  public void setModuleSubId(String moduleSubId) {
    this.moduleSubId = moduleSubId;
  }
  
  public String getInfoId() {
    return this.infoId;
  }
  
  public void setInfoId(String infoId) {
    this.infoId = infoId;
  }
  
  public String getRelationObjectType() {
    return this.relationObjectType;
  }
  
  public void setRelationObjectType(String relationObjectType) {
    this.relationObjectType = relationObjectType;
  }
  
  public String getRelationInfoName() {
    return this.relationInfoName;
  }
  
  public void setRelationInfoName(String relationInfoName) {
    this.relationInfoName = relationInfoName;
  }
  
  public String getRelationInfoId() {
    return this.relationInfoId;
  }
  
  public void setRelationInfoId(String relationInfoId) {
    this.relationInfoId = relationInfoId;
  }
  
  public String getRelationInfoHref() {
    return this.relationInfoHref;
  }
  
  public void setRelationInfoHref(String relationInfoHref) {
    this.relationInfoHref = relationInfoHref;
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public String getRelationSubId() {
    return this.relationSubId;
  }
  
  public void setRelationSubId(String relationSubId) {
    this.relationSubId = relationSubId;
  }
}
