package com.js.oa.hr.personnelmanager.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class TrainClassPO implements Serializable {
  private Long id;
  
  private String trainName;
  
  private String trainDescribe;
  
  private String trainContent;
  
  private String trainAim;
  
  private Long domain;
  
  public Long getDomain() {
    return this.domain;
  }
  
  public void setDomain(Long domain) {
    this.domain = domain;
  }
  
  public Long getId() {
    return this.id;
  }
  
  private void setId(Long id) {
    this.id = id;
  }
  
  public String getTrainAim() {
    return this.trainAim;
  }
  
  public void setTrainAim(String trainAim) {
    this.trainAim = trainAim;
  }
  
  public String getTrainContent() {
    return this.trainContent;
  }
  
  public void setTrainContent(String trainContent) {
    this.trainContent = trainContent;
  }
  
  public String getTrainDescribe() {
    return this.trainDescribe;
  }
  
  public void setTrainDescribe(String trainDescribe) {
    this.trainDescribe = trainDescribe;
  }
  
  public String getTrainName() {
    return this.trainName;
  }
  
  public void setTrainName(String trainName) {
    this.trainName = trainName;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof TrainClassPO))
      return false; 
    TrainClassPO castOther = (TrainClassPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
}
