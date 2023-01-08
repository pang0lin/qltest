package com.js.oa.hr.examination.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ExaminationSelfTestItemPO implements Serializable {
  private Long itemID;
  
  private Long examinationID;
  
  private String examinationStyle;
  
  private String examinationNO;
  
  private String examinationNO2;
  
  private String examinationResult;
  
  private String myResult;
  
  private String isRight;
  
  private ExaminationSelfTestPO examinationSelfTestPO;
  
  public Long getExaminationID() {
    return this.examinationID;
  }
  
  public void setExaminationID(Long examinationID) {
    this.examinationID = examinationID;
  }
  
  public String getExaminationNO() {
    return this.examinationNO;
  }
  
  public void setExaminationNO(String examinationNO) {
    this.examinationNO = examinationNO;
  }
  
  public String getExaminationNO2() {
    return this.examinationNO2;
  }
  
  public void setExaminationNO2(String examinationNO2) {
    this.examinationNO2 = examinationNO2;
  }
  
  public String getExaminationResult() {
    return this.examinationResult;
  }
  
  public void setExaminationResult(String examinationResult) {
    this.examinationResult = examinationResult;
  }
  
  public ExaminationSelfTestPO getExaminationSelfTestPO() {
    return this.examinationSelfTestPO;
  }
  
  public void setExaminationSelfTestPO(ExaminationSelfTestPO examinationSelfTestPO) {
    this.examinationSelfTestPO = examinationSelfTestPO;
  }
  
  public String getExaminationStyle() {
    return this.examinationStyle;
  }
  
  public void setExaminationStyle(String examinationStyle) {
    this.examinationStyle = examinationStyle;
  }
  
  public String getIsRight() {
    return this.isRight;
  }
  
  public void setIsRight(String isRight) {
    this.isRight = isRight;
  }
  
  public Long getItemID() {
    return this.itemID;
  }
  
  public void setItemID(Long itemID) {
    this.itemID = itemID;
  }
  
  public String getMyResult() {
    return this.myResult;
  }
  
  public void setMyResult(String myResult) {
    this.myResult = myResult;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getItemID())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ExaminationSelfTestItemPO))
      return false; 
    ExaminationSelfTestItemPO castOther = (ExaminationSelfTestItemPO)other;
    return (new EqualsBuilder())
      .append(getItemID(), castOther.getItemID())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getItemID())
      .toHashCode();
  }
}
