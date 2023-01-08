package com.js.oa.scheme.worklog.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class WorkProjectTaskPO implements Serializable {
  private String task_achieve;
  
  private String task_code;
  
  private String task_description;
  
  private String task_fathercode;
  
  private String task_hour;
  
  private Long task_id;
  
  private int task_level;
  
  private String task_name;
  
  private Long task_sort;
  
  private WorkProjectPO workProject;
  
  public boolean equals(Object other) {
    if (!(other instanceof WorkProjectPO))
      return false; 
    WorkProjectTaskPO castOther = (WorkProjectTaskPO)other;
    return (new EqualsBuilder()).append(getTask_id(), castOther.getTask_id()).isEquals();
  }
  
  public String getTask_achieve() {
    return this.task_achieve;
  }
  
  public String getTask_code() {
    return this.task_code;
  }
  
  public String getTask_description() {
    return this.task_description;
  }
  
  public String getTask_fathercode() {
    return this.task_fathercode;
  }
  
  public String getTask_hour() {
    return this.task_hour;
  }
  
  public Long getTask_id() {
    return this.task_id;
  }
  
  public int getTask_level() {
    return this.task_level;
  }
  
  public String getTask_name() {
    return this.task_name;
  }
  
  public Long getTask_sort() {
    return this.task_sort;
  }
  
  public WorkProjectPO getWorkProject() {
    return this.workProject;
  }
  
  public int hashCode() {
    return (new HashCodeBuilder()).append(getTask_id()).toHashCode();
  }
  
  public void setTask_achieve(String task_achieve) {
    this.task_achieve = task_achieve;
  }
  
  public void setTask_code(String task_code) {
    this.task_code = task_code;
  }
  
  public void setTask_description(String task_description) {
    this.task_description = task_description;
  }
  
  public void setTask_fathercode(String task_fathercode) {
    this.task_fathercode = task_fathercode;
  }
  
  public void setTask_hour(String task_hour) {
    this.task_hour = task_hour;
  }
  
  public void setTask_id(Long task_id) {
    this.task_id = task_id;
  }
  
  public void setTask_level(int task_level) {
    this.task_level = task_level;
  }
  
  public void setTask_name(String task_name) {
    this.task_name = task_name;
  }
  
  public void setTask_sort(Long task_sort) {
    this.task_sort = task_sort;
  }
  
  public void setWorkProject(WorkProjectPO workProject) {
    this.workProject = workProject;
  }
}
