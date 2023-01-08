package com.js.oa.jsflow.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class WFTransitionRestrictionPO implements Serializable {
  private Long wfTransitionRestrictionId;
  
  private Long conditionField;
  
  private String compareValue;
  
  private String relation;
  
  private WFTransitionPO wfTransition;
  
  private String domainId;
  
  public Long getWfTransitionRestrictionId() {
    return this.wfTransitionRestrictionId;
  }
  
  public void setWfTransitionRestrictionId(Long wfTransitionRestrictionId) {
    this.wfTransitionRestrictionId = wfTransitionRestrictionId;
  }
  
  public Long getConditionField() {
    return this.conditionField;
  }
  
  public void setConditionField(Long conditionField) {
    this.conditionField = conditionField;
  }
  
  public String getCompareValue() {
    return this.compareValue;
  }
  
  public void setCompareValue(String compareValue) {
    this.compareValue = compareValue;
  }
  
  public String getRelation() {
    return this.relation;
  }
  
  public void setRelation(String relation) {
    this.relation = relation;
  }
  
  public WFTransitionPO getWfTransition() {
    return this.wfTransition;
  }
  
  public void setWfTransition(WFTransitionPO wfTransition) {
    this.wfTransition = wfTransition;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof WFTransitionRestrictionPO))
      return false; 
    WFTransitionRestrictionPO castOther = (WFTransitionRestrictionPO)other;
    return (new EqualsBuilder()).append(getWfTransitionRestrictionId(), castOther.getWfTransitionRestrictionId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getWfTransitionRestrictionId()).toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
