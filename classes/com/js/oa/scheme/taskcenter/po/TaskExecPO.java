package com.js.oa.scheme.taskcenter.po;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TaskExecPO implements Serializable {
  private Long taskExecId;
  
  private Long teDomainId;
  
  private Long execEmpId;
  
  private Integer isPrincipal;
  
  private Set taskReports = null;
  
  private TaskPO task;
  
  public TaskExecPO(Long execEmpId, Long teDomainId, Integer isPrincipal, Set taskReports, TaskPO task) {
    this.execEmpId = execEmpId;
    this.isPrincipal = isPrincipal;
    this.taskReports = taskReports;
    this.teDomainId = teDomainId;
    this.task = task;
  }
  
  public TaskExecPO() {}
  
  public TaskExecPO(Long execEmpId) {
    this.execEmpId = execEmpId;
  }
  
  public Long getTaskExecId() {
    return this.taskExecId;
  }
  
  public void setTaskExecId(Long taskExecId) {
    this.taskExecId = taskExecId;
  }
  
  public Long getTeDomainId() {
    return this.teDomainId;
  }
  
  public void setTeDomainId(Long teDomainId) {
    this.teDomainId = teDomainId;
  }
  
  public Long getExecEmpId() {
    return this.execEmpId;
  }
  
  public void setExecEmpId(Long execEmpId) {
    this.execEmpId = execEmpId;
  }
  
  public Integer getIsPrincipal() {
    return this.isPrincipal;
  }
  
  public void setIsPrincipal(Integer isPrincipal) {
    this.isPrincipal = isPrincipal;
  }
  
  public String toString() {
    return (new ToStringBuilder(this))
      .append("taskExecId", getTaskExecId())
      .toString();
  }
  
  public boolean equals(Object other) {
    if (!(other instanceof TaskExecPO))
      return false; 
    TaskExecPO castOther = (TaskExecPO)other;
    return (new EqualsBuilder())
      .append(getTaskExecId(), castOther.getTaskExecId())
      .isEquals();
  }
  
  public int hashCode() {
    return (new HashCodeBuilder())
      .append(getTaskExecId())
      .toHashCode();
  }
  
  public Set getTaskReports() {
    return this.taskReports;
  }
  
  public void setTaskReports(Set taskReports) {
    this.taskReports = taskReports;
  }
  
  public TaskPO getTask() {
    return this.task;
  }
  
  public void setTask(TaskPO task) {
    this.task = task;
  }
}
