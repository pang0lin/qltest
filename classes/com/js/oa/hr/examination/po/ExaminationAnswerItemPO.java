package com.js.oa.hr.examination.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ExaminationAnswerItemPO implements Serializable {
  private Long id;
  
  private Long examinationID;
  
  private String examinationResult;
  
  private String myResult;
  
  private String isRight;
  
  private Long fullMark;
  
  private Long mark;
  
  private ExaminationAnswerPO examinationAnswerPO;
  
  public Long getExaminationID() {
    return this.examinationID;
  }
  
  public ExaminationAnswerPO getExaminationAnswerPO() {
    return this.examinationAnswerPO;
  }
  
  public void setExaminationAnswerPO(ExaminationAnswerPO examinationAnswerPO) {
    this.examinationAnswerPO = examinationAnswerPO;
  }
  
  public void setExaminationID(Long examinationID) {
    this.examinationID = examinationID;
  }
  
  public String getExaminationResult() {
    return this.examinationResult;
  }
  
  public void setExaminationResult(String examinationResult) {
    this.examinationResult = examinationResult;
  }
  
  public Long getFullMark() {
    return this.fullMark;
  }
  
  public void setFullMark(Long fullMark) {
    this.fullMark = fullMark;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getIsRight() {
    return this.isRight;
  }
  
  public void setIsRight(String isRight) {
    this.isRight = isRight;
  }
  
  public Long getMark() {
    return this.mark;
  }
  
  public void setMark(Long mark) {
    this.mark = mark;
  }
  
  public String getMyResult() {
    return this.myResult;
  }
  
  public void setMyResult(String myResult) {
    this.myResult = myResult;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ExaminationAnswerItemPO))
      return false; 
    ExaminationAnswerItemPO castOther = (ExaminationAnswerItemPO)other;
    return (new EqualsBuilder())
      .append(getId(), castOther.getId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getId())
      .toHashCode();
  }
}
