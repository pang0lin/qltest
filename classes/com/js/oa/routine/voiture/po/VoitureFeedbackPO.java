package com.js.oa.routine.voiture.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class VoitureFeedbackPO implements Serializable {
  private Long id;
  
  private Long voitureId;
  
  private String satisfaction;
  
  private String satiRemark;
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public void setSatisfaction(String satisfaction) {
    this.satisfaction = satisfaction;
  }
  
  public void setSatiRemark(String satiRemark) {
    this.satiRemark = satiRemark;
  }
  
  public void setVoitureId(Long voitureId) {
    this.voitureId = voitureId;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public String getSatisfaction() {
    return this.satisfaction;
  }
  
  public String getSatiRemark() {
    return this.satiRemark;
  }
  
  public Long getVoitureId() {
    return this.voitureId;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof VoitureFeedbackPO))
      return false; 
    VoitureFeedbackPO castOther = (VoitureFeedbackPO)other;
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
