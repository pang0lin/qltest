package com.js.oa.jsflow.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class WFPressPO implements Serializable {
  private Long wfPressId;
  
  private Long conditionField;
  
  private String pressRelation;
  
  private String compareValue;
  
  private WFActivityPO wfActivity;
  
  private int pressMotionTime;
  
  private int motionTime;
  
  private String domainId;
  
  public Long getWfPressId() {
    return this.wfPressId;
  }
  
  public void setWfPressId(Long wfPressId) {
    this.wfPressId = wfPressId;
  }
  
  public Long getConditionField() {
    return this.conditionField;
  }
  
  public void setConditionField(Long conditionField) {
    this.conditionField = conditionField;
  }
  
  public String getPressRelation() {
    return this.pressRelation;
  }
  
  public void setPressRelation(String pressRelation) {
    this.pressRelation = pressRelation;
  }
  
  public String getCompareValue() {
    return this.compareValue;
  }
  
  public void setCompareValue(String compareValue) {
    this.compareValue = compareValue;
  }
  
  public int getMotionTime() {
    return this.motionTime;
  }
  
  public void setMotionTime(int motionTime) {
    this.motionTime = motionTime;
  }
  
  public WFActivityPO getWfActivity() {
    return this.wfActivity;
  }
  
  public void setWfActivity(WFActivityPO wfActivity) {
    this.wfActivity = wfActivity;
  }
  
  public int getPressMotionTime() {
    return this.pressMotionTime;
  }
  
  public void setPressMotionTime(int pressMotionTime) {
    this.pressMotionTime = pressMotionTime;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof WFPressPO))
      return false; 
    WFPressPO castOther = (WFPressPO)other;
    return (new EqualsBuilder()).append(getWfPressId(), castOther.getWfPressId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getWfPressId()).toHashCode();
  }
  
  public String getDomainId() {
    return this.domainId;
  }
  
  public void setDomainId(String domainId) {
    this.domainId = domainId;
  }
}
