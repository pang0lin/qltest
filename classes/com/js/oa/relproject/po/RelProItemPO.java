package com.js.oa.relproject.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

public class RelProItemPO implements Serializable {
  private Long id;
  
  private Long projectId;
  
  private Date startTime = null;
  
  private Date endTime = null;
  
  private String title;
  
  private Integer itemRemind;
  
  public RelProItemPO(Long id) {
    this.id = id;
  }
  
  public RelProItemPO() {}
  
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
  
  public Date getStartTime() {
    return this.startTime;
  }
  
  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }
  
  public Date getEndTime() {
    return this.endTime;
  }
  
  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public Integer getItemRemind() {
    return this.itemRemind;
  }
  
  public void setItemRemind(Integer itemRemind) {
    this.itemRemind = itemRemind;
  }
}
