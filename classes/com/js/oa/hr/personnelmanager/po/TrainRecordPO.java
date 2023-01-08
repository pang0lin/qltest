package com.js.oa.hr.personnelmanager.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class TrainRecordPO implements Serializable {
  private Long id;
  
  private String trainName;
  
  private Date beginDate = null;
  
  private Date endDate = null;
  
  private Long trainClass;
  
  private String trainOrganizer;
  
  private String trainOrganizerID;
  
  private String trainContent;
  
  private String trainAim;
  
  private String trainAddress;
  
  private String trainActor;
  
  private String trainEmpID;
  
  private String trainOrg;
  
  private String trainGroup;
  
  private String trainEffect;
  
  private String trainResource;
  
  private Long domain;
  
  private Long createdEmpID;
  
  private Long createdOrg;
  
  public Date getBeginDate() {
    return this.beginDate;
  }
  
  public void setBeginDate(Date beginDate) {
    this.beginDate = beginDate;
  }
  
  public Long getDomain() {
    return this.domain;
  }
  
  public void setDomain(Long domain) {
    this.domain = domain;
  }
  
  public Date getEndDate() {
    return this.endDate;
  }
  
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getTrainActor() {
    return this.trainActor;
  }
  
  public void setTrainActor(String trainActor) {
    this.trainActor = trainActor;
  }
  
  public String getTrainAddress() {
    return this.trainAddress;
  }
  
  public void setTrainAddress(String trainAddress) {
    this.trainAddress = trainAddress;
  }
  
  public String getTrainAim() {
    return this.trainAim;
  }
  
  public void setTrainAim(String trainAim) {
    this.trainAim = trainAim;
  }
  
  public Long getTrainClass() {
    return this.trainClass;
  }
  
  public void setTrainClass(Long trainClass) {
    this.trainClass = trainClass;
  }
  
  public String getTrainContent() {
    return this.trainContent;
  }
  
  public void setTrainContent(String trainContent) {
    this.trainContent = trainContent;
  }
  
  public String getTrainEffect() {
    return this.trainEffect;
  }
  
  public void setTrainEffect(String trainEffect) {
    this.trainEffect = trainEffect;
  }
  
  public String getTrainEmpID() {
    return this.trainEmpID;
  }
  
  public void setTrainEmpID(String trainEmpID) {
    this.trainEmpID = trainEmpID;
  }
  
  public String getTrainGroup() {
    return this.trainGroup;
  }
  
  public void setTrainGroup(String trainGroup) {
    this.trainGroup = trainGroup;
  }
  
  public String getTrainName() {
    return this.trainName;
  }
  
  public void setTrainName(String trainName) {
    this.trainName = trainName;
  }
  
  public String getTrainOrg() {
    return this.trainOrg;
  }
  
  public void setTrainOrg(String trainOrg) {
    this.trainOrg = trainOrg;
  }
  
  public String getTrainOrganizer() {
    return this.trainOrganizer;
  }
  
  public void setTrainOrganizer(String trainOrganizer) {
    this.trainOrganizer = trainOrganizer;
  }
  
  public String getTrainOrganizerID() {
    return this.trainOrganizerID;
  }
  
  public void setTrainOrganizerID(String trainOrganizerID) {
    this.trainOrganizerID = trainOrganizerID;
  }
  
  public String getTrainResource() {
    return this.trainResource;
  }
  
  public void setTrainResource(String trainResource) {
    this.trainResource = trainResource;
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof TrainRecordPO))
      return false; 
    TrainRecordPO castOther = (TrainRecordPO)other;
    return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getId()).toHashCode();
  }
  
  public Long getCreatedEmpID() {
    return this.createdEmpID;
  }
  
  public void setCreatedEmpID(Long createdEmpID) {
    this.createdEmpID = createdEmpID;
  }
  
  public Long getCreatedOrg() {
    return this.createdOrg;
  }
  
  public void setCreatedOrg(Long createdOrg) {
    this.createdOrg = createdOrg;
  }
}
