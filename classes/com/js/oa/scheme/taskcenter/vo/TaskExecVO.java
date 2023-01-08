package com.js.oa.scheme.taskcenter.vo;

import java.io.Serializable;

public class TaskExecVO implements Serializable {
  private Long taskExecId;
  
  private Long teDomainId;
  
  private Long taskId;
  
  private Long execEmpId;
  
  private Integer isPrincipal;
  
  public TaskExecVO() {}
  
  public TaskExecVO(Long taskExecId, Long teDomainId, Long taskId, Long execEmpId, Integer principal) {
    this.taskExecId = taskExecId;
    this.taskId = taskId;
    this.execEmpId = execEmpId;
    this.isPrincipal = principal;
    this.teDomainId = teDomainId;
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
  
  public Long getTaskId() {
    return this.taskId;
  }
  
  public void setTaskId(Long taskId) {
    this.taskId = taskId;
  }
  
  public Long getExecEmpId() {
    return this.execEmpId;
  }
  
  public void setExecEmpId(Long execEmpId) {
    this.execEmpId = execEmpId;
  }
  
  public Integer getPrincipal() {
    return this.isPrincipal;
  }
  
  public void setPrincipal(Integer principal) {
    this.isPrincipal = principal;
  }
  
  public boolean equals(Object o) {
    if (this == o)
      return true; 
    if (!(o instanceof TaskExecVO))
      return false; 
    TaskExecVO taskExecVO = (TaskExecVO)o;
    if ((this.execEmpId != null) ? !this.execEmpId.equals(taskExecVO.execEmpId) : (taskExecVO.execEmpId != null))
      return false; 
    if ((this.isPrincipal != null) ? !this.isPrincipal.equals(taskExecVO.isPrincipal) : (taskExecVO.isPrincipal != null))
      return false; 
    if (!this.taskExecId.equals(taskExecVO.taskExecId))
      return false; 
    if ((this.taskId != null) ? !this.taskId.equals(taskExecVO.taskId) : (taskExecVO.taskId != null))
      return false; 
    if ((this.teDomainId != null) ? !this.teDomainId.equals(taskExecVO.teDomainId) : (taskExecVO.teDomainId != null))
      return false; 
    return true;
  }
  
  public int hashCode() {
    int result = this.taskExecId.hashCode();
    result = 29 * result + ((this.taskId != null) ? this.taskId.hashCode() : 0);
    result = 29 * result + ((this.execEmpId != null) ? this.execEmpId.hashCode() : 0);
    result = 29 * result + ((this.isPrincipal != null) ? this.isPrincipal.hashCode() : 0);
    result = 29 * result + ((this.teDomainId != null) ? this.teDomainId.hashCode() : 0);
    return result;
  }
  
  public String toString() {
    return "TaskExecVO{taskExecId=" + 
      this.taskExecId + 
      ", teDomainId=" + this.teDomainId + 
      ", taskId=" + this.taskId + 
      ", execEmpId=" + this.execEmpId + 
      ", isPrincipal=" + this.isPrincipal + 
      "}";
  }
}
