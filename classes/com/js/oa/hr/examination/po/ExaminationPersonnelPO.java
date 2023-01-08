package com.js.oa.hr.examination.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ExaminationPersonnelPO implements Serializable {
  private Long id;
  
  private Long examManagerID;
  
  private Long empID;
  
  private String isAnswer;
  
  public Long getEmpID() {
    return this.empID;
  }
  
  public void setEmpID(Long empID) {
    this.empID = empID;
  }
  
  public Long getExamManagerID() {
    return this.examManagerID;
  }
  
  public void setExamManagerID(Long examManagerID) {
    this.examManagerID = examManagerID;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getIsAnswer() {
    return this.isAnswer;
  }
  
  public void setIsAnswer(String isAnswer) {
    this.isAnswer = isAnswer;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof ExaminationPersonnelPO))
      return false; 
    ExaminationPersonnelPO castOther = (ExaminationPersonnelPO)other;
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
