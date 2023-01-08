package com.js.oa.relproject.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

public class RelProjectPO implements Serializable {
  private Long id;
  
  private String title;
  
  private String catalog;
  
  private Date startTime = null;
  
  private Date endTime = null;
  
  private String proDesc;
  
  private Integer status;
  
  private Integer rate;
  
  private Set projectActor = null;
  
  private long classId;
  
  public RelProjectPO(Long id) {
    this.id = id;
  }
  
  public RelProjectPO() {}
  
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
  
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getCatalog() {
    return this.catalog;
  }
  
  public void setCatalog(String catalog) {
    this.catalog = catalog;
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
  
  public String getProDesc() {
    return this.proDesc;
  }
  
  public void setProDesc(String proDesc) {
    this.proDesc = proDesc;
  }
  
  public Integer getStatus() {
    return this.status;
  }
  
  public void setStatus(Integer status) {
    this.status = status;
  }
  
  public Integer getRate() {
    return this.rate;
  }
  
  public void setRate(Integer rate) {
    this.rate = rate;
  }
  
  public Set getProjectActor() {
    return this.projectActor;
  }
  
  public void setProjectActor(Set projectActor) {
    this.projectActor = projectActor;
  }
  
  public long getClassId() {
    return this.classId;
  }
  
  public void setClassId(long classId) {
    this.classId = classId;
  }
}
