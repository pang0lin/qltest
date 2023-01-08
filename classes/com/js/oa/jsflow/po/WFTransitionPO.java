package com.js.oa.jsflow.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class WFTransitionPO implements Serializable {
  private Long wfTransitionId;
  
  private String transitionName;
  
  private String transitionDescription;
  
  private WFActivityPO transitionFrom;
  
  private WFActivityPO transitionTo;
  
  private Set wfTransitionRestriction = null;
  
  private String domainId;
  
  private String expression;
  
  private String defaultActivity;
  
  public Long getWfTransitionId() {
    return this.wfTransitionId;
  }
  
  public void setWfTransitionId(Long wfTransitionId) {
    this.wfTransitionId = wfTransitionId;
  }
  
  public String getTransitionName() {
    return this.transitionName;
  }
  
  public void setTransitionName(String transitionName) {
    this.transitionName = transitionName;
  }
  
  public String getTransitionDescription() {
    return this.transitionDescription;
  }
  
  public void setTransitionDescription(String transitionDescription) {
    this.transitionDescription = transitionDescription;
  }
  
  public WFActivityPO getTransitionFrom() {
    return this.transitionFrom;
  }
  
  public void setTransitionFrom(WFActivityPO transitionFrom) {
    this.transitionFrom = transitionFrom;
  }
  
  public WFActivityPO getTransitionTo() {
    return this.transitionTo;
  }
  
  public void setTransitionTo(WFActivityPO transitionTo) {
    this.transitionTo = transitionTo;
  }
  
  public Set getWfTransitionRestriction() {
    return this.wfTransitionRestriction;
  }
  
  public void setWfTransitionRestriction(Set wfTransitionRestriction) {
    this.wfTransitionRestriction = wfTransitionRestriction;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof WFTransitionPO))
      return false; 
    WFTransitionPO castOther = (WFTransitionPO)other;
    return (new EqualsBuilder()).append(getWfTransitionId(), castOther.getWfTransitionId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getWfTransitionId()).toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
  
  public String getExpression() {
    return this.expression;
  }
  
  public void setExpression(String expression) {
    this.expression = expression;
  }
  
  public String getDefaultActivity() {
    return this.defaultActivity;
  }
  
  public void setDefaultActivity(String defaultActivity) {
    this.defaultActivity = defaultActivity;
  }
}
