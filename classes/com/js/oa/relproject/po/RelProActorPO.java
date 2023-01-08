package com.js.oa.relproject.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class RelProActorPO implements Serializable {
  private Long id;
  
  private Long projectId;
  
  private Long actorId;
  
  private String actorName;
  
  private Integer actorType;
  
  private Integer actorRole;
  
  private RelProjectPO project;
  
  public RelProActorPO(Long id) {
    this.id = id;
  }
  
  public RelProActorPO() {}
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("id", getId())
      .toString();
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Long getProjectId() {
    return this.projectId;
  }
  
  public void setProjectId(Long projectId) {
    this.projectId = projectId;
  }
  
  public Long getActorId() {
    return this.actorId;
  }
  
  public void setActorId(Long actorId) {
    this.actorId = actorId;
  }
  
  public String getActorName() {
    return this.actorName;
  }
  
  public void setActorName(String actorName) {
    this.actorName = actorName;
  }
  
  public Integer getActorType() {
    return this.actorType;
  }
  
  public void setActorType(Integer actorType) {
    this.actorType = actorType;
  }
  
  public Integer getActorRole() {
    return this.actorRole;
  }
  
  public void setActorRole(Integer actorRole) {
    this.actorRole = actorRole;
  }
  
  public RelProjectPO getProject() {
    return this.project;
  }
  
  public void setProject(RelProjectPO project) {
    this.project = project;
  }
}
